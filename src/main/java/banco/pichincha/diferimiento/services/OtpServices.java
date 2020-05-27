package banco.pichincha.diferimiento.services;

import banco.pichincha.diferimiento.dto.DifCliente;
import banco.pichincha.diferimiento.dto.DifLogsOtp;
import banco.pichincha.diferimiento.dto.DifSolidife;
import banco.pichincha.diferimiento.exception.ApiRequestException;
import banco.pichincha.diferimiento.model.RequestSolicitudOtp;
import banco.pichincha.diferimiento.repository.IClienteDao;
import banco.pichincha.diferimiento.repository.ILogsOtpDao;
import banco.pichincha.diferimiento.repository.ISolidifeDao;
import banco.pichincha.diferimiento.util.ApiResponse;
import banco.pichincha.diferimiento.util.UtilResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class OtpServices {

    private static final String LOGGER_RESPONSE_FORMAT = "004-RES";
    private static final String LOGGER_REQUEST_FORMAT = "004-REQ";
    private static final Logger logger = LoggerFactory.getLogger(OtpServices.class);
    @Autowired
    Environment env;

    @Autowired
    private IClienteDao iClienteDao;

    @Autowired
    private ISolidifeDao iSolidifeDao;

    @Autowired
    private ILogsOtpDao iLogsOtpDao;

    RestTemplate restTemplate = new RestTemplate();


    /**
     * Funcion que permite generar un OTP
     *
     * @param hashCliente
     * @return
     */
    public ResponseEntity<ApiResponse> generateOtp(String hashCliente, HttpServletRequest req) {
        logger.info("Se realizará el proceso para generar el código Otp al cliente", LOGGER_REQUEST_FORMAT);
        String method = "generateOtp";
        ApiResponse apiResponse = null;
        DifCliente difCliente = null;
        ResponseEntity<ApiResponse> response = null;
        try {
            //Buscamos el cliente
            difCliente = iClienteDao.findByHashCliente(hashCliente);
            logger.info("Usuario GET OTP =>" + difCliente, LOGGER_REQUEST_FORMAT);
            //Validamos el cliente
            if (difCliente != null) {
                String url = env.getRequiredProperty("url.get.otp");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                //Json para envío de HTTP
                String requestJson = "{\"telefono\": " + "\"" + difCliente.getClieCelular() + "\"" + ",\"identificacion\": " + "\"" + UtilResponse.validateRequestOtpIdentificacion(difCliente.getClieIdentificacion()) + "\"" + "}";
                logger.info("JSON generate OTP => " + requestJson, method, LOGGER_REQUEST_FORMAT);
                HttpEntity<String> entity = new HttpEntity<String>(requestJson, httpHeaders);
                try {
                    response = restTemplate.exchange(url, HttpMethod.PUT, entity, new ParameterizedTypeReference<ApiResponse>() {
                    });
                    if (response.getStatusCodeValue() == 200) {
                        apiResponse = new ApiResponse(
                                "El Otp fue enviado correctamente al cliente " + difCliente.getCliePrimnomb() + " " + difCliente.getCliePrimapell(),
                                String.valueOf(HttpStatus.OK.value()),
                                HttpStatus.OK, new Date(),
                                "",
                                null);
                        logger.info("Se ha generado correctamente el otp para el cliente   \t" + difCliente.getClieIdentificacion() + " =>"
                                + response.getBody(), method, LOGGER_RESPONSE_FORMAT);
                    } else {
                        apiResponse = new ApiResponse(
                                "El Otp no fue enviado correctamente al cliente " + difCliente.getCliePrimnomb() + " " + difCliente.getCliePrimapell(),
                                String.valueOf(HttpStatus.CONFLICT.value()),
                                HttpStatus.CONFLICT, new Date(),
                                "",
                                null);
                        logger.info("No se ha generado correctamente el otp para el cliente   \t" + difCliente.getClieIdentificacion() + " =>"
                                + response.getBody(), method, LOGGER_RESPONSE_FORMAT);

                    }

                } catch (Exception exc) {
                    //Se guardara en la tabla logs otp
                    try {
                        this.saveLogsErrorOtp("G", req, difCliente, "");
                    } catch (Exception e) {
                        logger.error("Error al guardar el cliente en la tabla logs del cliente " + e.getMessage(), LOGGER_RESPONSE_FORMAT);
                    }
                    logger.error("Error en el método  :  \t" + method +
                            "\t" + exc.getMessage(), method, LOGGER_RESPONSE_FORMAT);
                    throw new ApiRequestException("No se pudo generar el Otp para cliente");

                }
            }
        } catch (ApiRequestException e) {
            logger.error("Uups, surgio un error al momento de generar el otp para el cliente \t " + difCliente.getClieIdentificacion(), e.toString(), LOGGER_RESPONSE_FORMAT);
            throw new ApiRequestException("No se pudo generar el otp");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


    /**
     * Funcion que permite validar el codigo OTP ingresado por el cliente
     *
     * @param requestSolicitudOtp
     * @return
     */
    public ResponseEntity<ApiResponse> validateOtp(RequestSolicitudOtp requestSolicitudOtp, HttpServletRequest req) {
        logger.info("Se realizará el proceso pra validar el código Otp del cliente =>" + requestSolicitudOtp.getHash(), LOGGER_REQUEST_FORMAT);
        String method = "validateOtp";
        ApiResponse apiResponse = null;
        DifCliente difCliente = null;
        ResponseEntity<ApiResponse> response = null;
        try {
            //Buscamos el cliente
            difCliente = iClienteDao.findByHashCliente(requestSolicitudOtp.getHash());
            logger.info("Usuario VALIDATE OTP => " + difCliente, LOGGER_REQUEST_FORMAT);
            //Validamos el cliente
            if (difCliente != null) {
                String url = env.getRequiredProperty("url.validate.otp");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                //Json para envío de HTTP
                String requestJson = "{\"identificacion\": " + "\"" + UtilResponse.validateRequestOtpIdentificacion(difCliente.getClieIdentificacion()) + "\"" + ",\"otp\": " + "\"" + requestSolicitudOtp.getOtp() + "\"" + "}";
                logger.info("JSON validar OTP => " + requestJson, method, LOGGER_REQUEST_FORMAT);
                HttpEntity<String> entity = new HttpEntity<String>(requestJson, httpHeaders);
                try {
                    response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResponse>() {
                    });
                    if (response.getStatusCodeValue() == 200) {
                        apiResponse = new ApiResponse(
                                "El Otp fue validado correctamente al cliente " + difCliente.getCliePrimnomb() + " " + difCliente.getCliePrimapell(),
                                String.valueOf(HttpStatus.OK.value()),
                                HttpStatus.OK, new Date(),
                                "",
                                null);
                        logger.info("Se ha validado correctamente el otp para el cliente   \t" + difCliente.getClieIdentificacion() + " =>"
                                + response.getBody(), method, LOGGER_RESPONSE_FORMAT);
                        //Se cambia el estado de la gestión a que a terminado correctamente
                        try {
                            this.changeStateSuccesfullOtp(difCliente, requestSolicitudOtp.getOtp());
                            logger.info("Se analizará las siguientes solicitudes de diferimientos flujos atadas al cliente " + difCliente.getClieIdentificacion() + " junto con las del OTP", LOGGER_REQUEST_FORMAT);
                            this.generateSolicitudesDistintosFlujos(difCliente);
                        } catch (Exception e) {
                            logger.info("Surgio un error al momento de cambiar estado de gestión OTP");
                            logger.error(e.toString());
                        }
                    } else {
                        apiResponse = new ApiResponse(
                                "El Otp no pudo ser validado correctamente al cliente " + difCliente.getCliePrimnomb() + " " + difCliente.getCliePrimapell() + " con la identificación " + difCliente.getClieIdentificacion(),
                                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                HttpStatus.BAD_REQUEST, new Date(),
                                "",
                                null);
                        logger.error("Error al validar el otp para el cliente   \t" + difCliente.getClieIdentificacion() + " =>"
                                + response.getBody(), method, LOGGER_RESPONSE_FORMAT);
                    }

                } catch (Exception exc) {
                    //Se guardara en la tabla logs otp
                    try {
                        this.saveLogsErrorOtp("V", req, difCliente, requestSolicitudOtp.getOtp());
                    } catch (Exception e) {
                        logger.error("Error al guardar los logs en la tabla clientes " + e.getMessage(), LOGGER_RESPONSE_FORMAT);
                    }
                    logger.error("Error en el método  :  \t" + method +
                            "\t" + exc.getMessage(), method, LOGGER_RESPONSE_FORMAT);
                    throw new ApiRequestException("No se pudo validar el Otp para cliente");

                }
            }
        } catch (ApiRequestException e) {
            logger.error("Uups, surgio un error al momento de validar el otp para el cliente \t " + difCliente.getClieIdentificacion(), method, LOGGER_RESPONSE_FORMAT);
            throw new ApiRequestException("No se pudo validar el otp");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    /**
     * Funcion que permite validar el cambio de estado
     *
     * @param difCliente
     * @param otp
     */
    public void changeStateSuccesfullOtp(DifCliente difCliente, String otp) {
        try {
            logger.info("Cambiando el estado de gestión del cliente " + difCliente.getClieIdentificacion());
            iSolidifeDao.changeStateGestionOtp(otp, difCliente.getClieId());
            logger.info("El cambio se realizó correctamente para seguir con la gestión normal para el cliente " + difCliente.getClieIdentificacion());
        } catch (ApiRequestException e) {
            logger.error("Uups, salgo salio mal al actualizar la gestion por OTP", e);
            throw new ApiRequestException("No se pudo generar el diferimiento");
        }
    }

    /**
     * Funcion que permite ingresar el OTP
     *
     * @param type
     * @param req
     * @param difCliente
     * @param otp
     */
    public void saveLogsErrorOtp(String type, HttpServletRequest req, DifCliente difCliente, String otp) {
        DifLogsOtp difLogsOtp = new DifLogsOtp();
        switch (type) {
            case "G"://Se envia el otp
                difLogsOtp.setLootOperacion(type);
                difLogsOtp.setClieId(difCliente);
                difLogsOtp.setLootIp(req.getRemoteAddr());
                iLogsOtpDao.save(difLogsOtp);
                break;
            case "V"://Se valida el otp
                DifLogsOtp temp = iLogsOtpDao.findByLogsCliente(difCliente.getClieId());
                if (temp != null) {
                    difLogsOtp.setLootOperacion(type);
                    difLogsOtp.setClieId(difCliente);
                    difLogsOtp.setLootIp(req.getRemoteAddr());
                    difLogsOtp.setLootErrorotp(otp);
                    difLogsOtp.setLootIntentos(String.valueOf(Integer.valueOf(temp.getLootIntentos()) + 1));
                    iLogsOtpDao.save(difLogsOtp);
                } else {
                    difLogsOtp.setLootOperacion(type);
                    difLogsOtp.setClieId(difCliente);
                    difLogsOtp.setLootIp(req.getRemoteAddr());
                    difLogsOtp.setLootErrorotp(otp);
                    difLogsOtp.setLootIntentos(String.valueOf(1));
                    iLogsOtpDao.save(difLogsOtp);
                }

                break;
            default:
                logger.info("No se reconoce el tipo de operacion para guardar en los logs");
                break;
        }
    }

    /**
     * Funcion que permite buscar el producto para gestión habitar
     *
     * @param name_producto
     * @return
     */
    private String getManagerProductHabitar(String name_producto) {
        String[] lstProductos = {"habitar", "linea", "preciso hipotecario", "fideicomiso"};
        for (String producto : lstProductos) {
            if (name_producto.toUpperCase().contains(producto.toUpperCase()))
                return "Y";
        }
        return "N";
    }


    /**
     * Descripcion:
     * Función que permite generar solicitudes de clientes atadas a una gestión de OTP exitoso
     *
     * @param difCliente
     */
    public void generateSolicitudesDistintosFlujos(DifCliente difCliente) {
        List<DifCliente> lstClienteMenores18000 = iClienteDao.findByCreditosClienteMenor18000(difCliente.getClieIdentificacion(), difCliente.getClieId());
        for (DifCliente cliente : lstClienteMenores18000) {//Gestión OTP
            this.gestionOtherSolicitudes(cliente, "0");
        }
        logger.info("El total de solicitudes menores a 18000 a procesar = " + lstClienteMenores18000.size() + " para el cliente " + difCliente.getClieIdentificacion(), LOGGER_RESPONSE_FORMAT);
        List<DifCliente> lstClienteMayores18000 = iClienteDao.findByCreditosClienteMayores18000(difCliente.getClieIdentificacion(), difCliente.getClieId());
        for (DifCliente cliente : lstClienteMayores18000) {//Gestión FVT o HABITAR
            switch (this.getManagerProductHabitar(cliente.getClieFamilia())) {
                case "Y":
                    //HABITAR
                    this.gestionOtherSolicitudes(cliente, "3");
                default:
                    //FVT
                    this.gestionOtherSolicitudes(cliente, "2");
                    break;
            }

        }
        logger.info("El total de solicitudes mayores a 18000 a procesar = " + lstClienteMayores18000.size() + " para el cliente " + difCliente.getClieIdentificacion(), LOGGER_RESPONSE_FORMAT);

    }


    /**
     * Función que permite guardar las solicitudes en la tabla solicitudes
     *
     * @param difCliente
     * @param tipo
     */
    public void gestionOtherSolicitudes(DifCliente difCliente, String tipo) {
        DifSolidife difSolidife = new DifSolidife();
        difSolidife.setSodiHashiden(difCliente.getClieHashiden());
        difSolidife.setSodiOtp("");
        difSolidife.setSodiEstado(tipo);
        difSolidife.setClieId(difCliente);
        difSolidife.setSodiBacaid(difCliente.getBacaId().getBacaId());
        iSolidifeDao.save(difSolidife);
    }
}
