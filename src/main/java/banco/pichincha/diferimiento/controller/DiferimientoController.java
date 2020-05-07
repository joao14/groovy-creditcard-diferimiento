package banco.pichincha.diferimiento.controller;


import banco.pichincha.diferimiento.exception.ApiRequestException;
import banco.pichincha.diferimiento.model.RequestCliente;
import banco.pichincha.diferimiento.model.RequestSolicitudOtp;
import banco.pichincha.diferimiento.services.DiferimientoServices;
import banco.pichincha.diferimiento.services.EncryptService;
import banco.pichincha.diferimiento.services.OtpServices;
import banco.pichincha.diferimiento.services.RecaptchaService;
import banco.pichincha.diferimiento.util.ApiResponse;
import banco.pichincha.diferimiento.util.UtilResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/diferimiento/v1/")
public class DiferimientoController {

    private static final String LOGGER_RESPONSE_FORMAT = "001-RES";
    private static final String LOGGER_REQUEST_FORMAT = "001-REQ";
    private static final Logger logger = LoggerFactory.getLogger(DiferimientoController.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DiferimientoServices diferimientoServices;

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private OtpServices otpServices;

    @Autowired
    private EncryptService encryptService;


    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDiferimiento(@RequestBody RequestCliente requestCliente, HttpServletRequest request, HttpServletResponse respo) {
        logger.info("Iniciando el diferimiento del cliente para créditos o tarjetas desde la siguiente IP : " + request.getRemoteAddr(), LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = new ApiResponse();
        try {
            if (!recaptchaService.valid(request, respo)) {
                throw new ApiRequestException("La Captcha no ha podido ser validado :(");
            }
            String cliente_request = mapper.writeValueAsString(requestCliente);
            logger.info("JSON  [] => " + cliente_request, LOGGER_REQUEST_FORMAT);
            logger.info("1.- Desencriptando la información del request ", LOGGER_REQUEST_FORMAT);
            requestCliente = encryptService.getObject(requestCliente);
            //Validamos la cédula con digitos para completar si es de longitud 14
            requestCliente.setIdentificacion(UtilResponse.validateRequestIdentificacion(requestCliente.getIdentificacion()));
            //Imprimiendo el nuevo objeto
            String cliente = mapper.writeValueAsString(requestCliente);
            logger.info("JSON DESENCRIPTADO [] => " + cliente, LOGGER_REQUEST_FORMAT);
            logger.info("2.- Empezando el proceso de diferimiento ", LOGGER_REQUEST_FORMAT);
            ResponseEntity<ApiResponse> resp = diferimientoServices.createDiferimiento(requestCliente, request);
            if (resp.getStatusCodeValue() == 200) {
                apiResponse = new ApiResponse(resp.getBody().getMessage(), String.valueOf(resp.getStatusCodeValue()),
                        resp.getStatusCode(), new Date(), resp.getBody().getType(), resp.getBody().getData());
            }

        } catch (ApiRequestException e) {
            logger.error(
                    " Error al momento del diferimiento : " + e + "en el metodo createDiferimiento");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT, new Date(), "", null);
        } catch (Exception e) {
            logger.error(
                    " Error al momento de generar el diferimiento de deuda del cliente : " + e + "en el metodo createDiferimiento");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST, new Date(), "", null);
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }


}
