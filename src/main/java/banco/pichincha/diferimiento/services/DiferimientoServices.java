package banco.pichincha.diferimiento.services;

import banco.pichincha.diferimiento.dto.DifCliente;
import banco.pichincha.diferimiento.dto.DifLogs;
import banco.pichincha.diferimiento.dto.DifSolidife;
import banco.pichincha.diferimiento.exception.ApiRequestException;
import banco.pichincha.diferimiento.model.RequestCliente;
import banco.pichincha.diferimiento.model.ResponseDiferimiento;
import banco.pichincha.diferimiento.repository.IClienteDao;
import banco.pichincha.diferimiento.repository.ILogsDao;
import banco.pichincha.diferimiento.repository.ISolidifeDao;
import banco.pichincha.diferimiento.util.ApiResponse;
import banco.pichincha.diferimiento.util.UtilResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiferimientoServices {

    private static final String LOGGER_RESPONSE_FORMAT = "004-RES";
    private static final String LOGGER_REQUEST_FORMAT = "004-REQ";
    private static final Logger logger = LoggerFactory.getLogger(DiferimientoServices.class);
    @Autowired
    Environment env;
    @Autowired
    private IClienteDao iClienteDao;
    @Autowired
    private ILogsDao iLogsDao;
    @Autowired
    private ISolidifeDao iSolidifeDao;

    public ResponseEntity<ApiResponse> createDiferimiento(RequestCliente requestCliente, HttpServletRequest request) {
        logger.info("1.- Procesando el diferimiento de productos del cliente " + requestCliente.getIdentificacion(), LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = null;
        ResponseDiferimiento resp_ = null;
        try {
            logger.info("2.- Consultando el cliente dentro de la base de la campaña");
            //Consulta el cliente segun las campañas ingresada
            DifCliente difCliente = iClienteDao.findByCliente(requestCliente.getIdentificacion());
            logger.info("Cliente => " + difCliente, LOGGER_REQUEST_FORMAT);
            //1.- Valida si el cliente se encuentra en campaña
            if (difCliente != null) {
                logger.info("3.- Empezar proceso de gestion de diferimientos ", LOGGER_REQUEST_FORMAT);
                //Se valida si el cliente que esta en campaña y ya fué gestionado
                DifSolidife difSolidife = iSolidifeDao.findSoliDifeByClient(difCliente.getClieId());
                //2.- Validamos si el cliente tiene un clie_montcapitotal <= de 18000 seguirá el flujo de OTP
                if (Float.parseFloat(difCliente.getClieMontcapitotal()) <= Float.parseFloat(env.getRequiredProperty("data.valor.total"))) {
                    if (difSolidife == null) {
                        resp_ = this.getFlujoSolicitudClientes("-1", difCliente, request);
                        logger.warn("El cliente " + requestCliente.getIdentificacion() + " esta en proceso de realizar la gestión normal por OTP", LOGGER_RESPONSE_FORMAT);
                        apiResponse = new ApiResponse("Diferimiento en proceso con gestión OTP", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.otp"), resp_);
                    } else {
                        if (Integer.valueOf(difSolidife.getSodiEstado()) != 1 && Integer.parseInt(difSolidife.getSodiEstado()) != 0) {
                            resp_ = this.getFlujoSolicitudClientes("-2", difCliente, request);
                            logger.warn("El cliente " + requestCliente.getIdentificacion() + " está intentando realizar el diferimiento como gestión normal de OTP nuevamente.", LOGGER_RESPONSE_FORMAT);
                            apiResponse = new ApiResponse("Cliente intentando gestionar nuevamente", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.otp"), resp_);
                        } else {
                            logger.warn("El cliente " + requestCliente.getIdentificacion() + " ya realizó el diferimiento como gestión normal de OTP", LOGGER_RESPONSE_FORMAT);
                            apiResponse = new ApiResponse("Cliente gestionado anteriormente", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.proceso.final"), null);
                        }

                    }

                } else {
                    if (difSolidife == null) {
                        //3.- Valida si el prestamo es hipotecario o linea abierta o otro tipo
                        switch (difCliente.getClieFamilia().toUpperCase()) {
                            case "LINEA ABIERTA HIPOT.":
                                resp_ = this.getFlujoSolicitudClientes("3", difCliente, request);
                                logger.info("El cliente " + difCliente.getClieIdentificacion() + " realizó un diferimiento con producto LINEA ABIERTA mendiante gestión HABITAR", LOGGER_RESPONSE_FORMAT);
                                apiResponse = new ApiResponse("Diferimiento en proceso con gestión HABITAR", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.posterior"), resp_);
                                break;
                            case "PRECISO HIPOTECARIO":
                                resp_ = this.getFlujoSolicitudClientes("3", difCliente, request);
                                logger.info("El cliente " + difCliente.getClieIdentificacion() + " realizó un diferimiento con producto HIPOTECARIO mendiante gestión HABITAR", LOGGER_RESPONSE_FORMAT);
                                apiResponse = new ApiResponse("Diferimiento en proceso con gestión HABITAR", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.posterior"), resp_);
                                break;
                            default:
                                resp_ = this.getFlujoSolicitudClientes("2", difCliente, request);
                                logger.info("El cliente " + difCliente.getClieIdentificacion() + " realizó un diferimiento con producto mayor a 18000 mendiante gestión FVT", LOGGER_RESPONSE_FORMAT);
                                apiResponse = new ApiResponse("Diferimiento en proceso con gestión FVT", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.posterior"), resp_);
                                break;
                        }
                    } else {
                        logger.warn("El cliente " + requestCliente.getIdentificacion() + " ya realizó el diferimiento como gestión posterior en HABITAR O FVT", LOGGER_RESPONSE_FORMAT);
                        apiResponse = new ApiResponse("Cliente gestionado anteriormente ", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.proceso.final"), null);
                    }
                }
            } else {
                logger.info("El cliente " + requestCliente.getIdentificacion() + " no se encuentra en la campaña para los diferimientos", LOGGER_REQUEST_FORMAT);
                //Se valida si el no cliente ya fue registrado
                DifLogs difLogs = iLogsDao.findByNoCliente(requestCliente.getIdentificacion());
                if (difLogs == null) {
                    resp_ = this.getFlujoDiferimientoNoClientes(requestCliente, request);
                    logger.info("El cliente " + requestCliente.getIdentificacion() + " no a sido gestionado como no cliente y se guardará en la base de datos", LOGGER_RESPONSE_FORMAT);
                    apiResponse = new ApiResponse("Diferimiento en proceso con gestión NO CLIENTES EN BASE ", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.sinBase"), resp_);
                } else {
                    logger.warn("El cliente " + requestCliente.getIdentificacion() + "ya fue gestionado como no cliente en la base de datos", LOGGER_RESPONSE_FORMAT);
                    apiResponse = new ApiResponse("Cliente gestionado anteriormente ", String.valueOf(HttpStatus.OK.value()), HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.gestion.proceso.final"), null);
                }

            }
        } catch (ApiRequestException e) {
            logger.error("Uups, surgio un error al realizar un diferimiento de cuota para el usuario \t " + requestCliente.getIdentificacion(), e);
            throw new ApiRequestException("No se pudo generar el diferimiento");
        }

        logger.info("4.- El proceso de diferimiento a finalizado correctamente", LOGGER_REQUEST_FORMAT);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    /**
     * Funcion que permite guardar en la tabla logs los no clientes
     *
     * @param requestCliente
     * @param request
     */
    private ResponseDiferimiento getFlujoDiferimientoNoClientes(RequestCliente requestCliente, HttpServletRequest request) {
        DifLogs difLogs = new DifLogs();
        difLogs.setLogIdentificacion(requestCliente.getIdentificacion());
        difLogs.setLogNombres(requestCliente.getNombres());
        difLogs.setLogApellidos(requestCliente.getApellidos());
        difLogs.setLogTelefono(requestCliente.getTelefono());
        difLogs.setLogEmail(requestCliente.getEmail());
        difLogs.setLogIp(request.getRemoteAddr());
        this.iLogsDao.save(difLogs);

        return this.createObject(null, difLogs, "C-2", "");
    }


    /**
     * Funcion que permite guardar la solicitud de los clientes
     *
     * @param type
     * @param difCliente
     * @param request
     */
    private ResponseDiferimiento getFlujoSolicitudClientes(String type, DifCliente difCliente, HttpServletRequest request) {
        //Se guardara la solicitud
        try {
            //valida si se guardará en la tabla solicitud de diferimientos
            if (Integer.valueOf(type) != -2) {
                DifSolidife difSolidife = new DifSolidife();
                difSolidife.setSodiHashiden(difCliente.getClieHashiden());
                difSolidife.setSodiOtp("");
                difSolidife.setSodiEstado(type);
                difSolidife.setClieId(difCliente);
                difSolidife.setSodiBacaid(difCliente.getBacaId().getBacaId());
                iSolidifeDao.save(difSolidife);
            }
        } catch (Exception e) {
            logger.error("Error al guardar la solicitud en la tabla dif_diferimiento");
            logger.error(e.getMessage());
        }
        return this.createObject(difCliente, null, "C-1", type);
    }


    /**
     * Funcion que permite crear el objeto que retornará
     *
     * @param difCliente
     * @param difLogs
     * @param type
     * @return
     */
    private ResponseDiferimiento createObject(DifCliente difCliente, DifLogs difLogs, String type, String flujo) {
        ResponseDiferimiento resp = null;
        switch (type) {
            case "C-1": //Cliente
                System.out.println();
                resp = new ResponseDiferimiento(
                        Integer.parseInt(flujo) < 0 ? this.getJWTToken(difCliente.getClieIdentificacion(), difCliente.getClieEmail(), difCliente.getCliePrimnomb()) : "",
                        difCliente.getClieHashiden(),
                        difCliente.getCliePrimnomb(),
                        difCliente.getCliePrimapell(),
                        UtilResponse.enmascarar_email(difCliente.getClieEmail()),
                        UtilResponse.enmascarar_telefono(difCliente.getClieCelular())
                );
                break;
            case "C-2": //No Clientes
                resp = new ResponseDiferimiento(
                        "",
                        "",
                        difLogs.getLogNombres(),
                        difLogs.getLogApellidos(),
                        UtilResponse.enmascarar_email(difLogs.getLogEmail()),
                        UtilResponse.enmascarar_telefono(difLogs.getLogTelefono())
                );
                break;
        }
        return resp;
    }

    /**
     * Metodo que permite obtener el token
     *
     * @param identificacion
     * @param email
     * @param nombre
     * @return
     */
    private String getJWTToken(String identificacion, String email, String nombre) {
        logger.info("El cliente " + identificacion + " está gestionando un nuevo token");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts.builder().setId("softtekJWT").setSubject(identificacion).setSubject(email)
                .setSubject(nombre)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.valueOf(env.getRequiredProperty("jwt.timeout"))))// 6 minutos
                .signWith(SignatureAlgorithm.HS512, env.getRequiredProperty("jwt.secret").getBytes()).compact();
        logger.info("El token generado para el cliente " + identificacion + " \t es" + token);
        return "Bearer " + token;
    }


    /**
     * Funcion que permite cambiar el estado de la tabla solicitud en cuanto no le llegue la OTP y necesite ser gestinado
     * por el call out, finalmnete terminar con el flujo
     *
     * @param hash
     * @return
     */
    public ResponseEntity<ApiResponse> generateCmbOut(String hash) {
        logger.info("Generando el cambio de estado para que el cliente " + hash + " sea atendido con CALL OUT", LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = null;
        try {
            iSolidifeDao.changeStateGestionCmbOut(hash);
            apiResponse = new ApiResponse("El cliente realizó la gestión por el Call Out ", String.valueOf(HttpStatus.OK.value()),
                    HttpStatus.OK, new Date(), env.getRequiredProperty("data.estado.flujo.necesito.ayuda"), null);
        } catch (ApiRequestException e) {
            logger.error("Uups, surgio un error al realizar un diferimiento de cuota para el usuario \t " + hash + " en el call out", e);
            throw new ApiRequestException("No se pudo generar el diferimiento");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


}
