package banco.pichincha.diferimiento.controller;

import banco.pichincha.diferimiento.exception.ApiRequestException;
import banco.pichincha.diferimiento.model.RequestSolicitudOtp;
import banco.pichincha.diferimiento.services.DiferimientoServices;
import banco.pichincha.diferimiento.services.OtpServices;
import banco.pichincha.diferimiento.services.RecaptchaService;
import banco.pichincha.diferimiento.util.ApiResponse;
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
@RequestMapping("/api/otp/v1/")
public class OtpController {

    private static final String LOGGER_RESPONSE_FORMAT = "001-RES";
    private static final String LOGGER_REQUEST_FORMAT = "001-REQ";
    private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

    @Autowired
    private DiferimientoServices diferimientoServices;

    @Autowired
    private OtpServices otpServices;

    @Autowired
    private RecaptchaService recaptchaService;


    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOtp(@RequestHeader String hash, HttpServletRequest req,
                                         HttpServletResponse respo) {
        logger.info("El cliente con el hash " + hash + " ha enviado una solicitud para generar OTP, con la IP \t"
                + req.getRemoteAddr(), LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = null;
        try {
            if (!recaptchaService.valid(req, respo)) {
                throw new ApiRequestException("La Captcha no ha podido ser validado :(");
            }
            ResponseEntity<ApiResponse> response=otpServices.generateOtp(hash,req);
            if(response.getStatusCodeValue()==200){
                apiResponse = new ApiResponse(response.getBody().getMessage(), String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK, new Date(), "", null);
            }else{
                apiResponse = new ApiResponse(response.getBody().getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST, new Date(), "", null);
            }

        }catch (ApiRequestException e) {
            logger.error("Error al momento de consultar la informacion del cliente en el envio de otp : " + e
                    + " \t en el metodo getOtp");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST, new Date(), "", null);
        }catch (Exception e) {
            logger.error(
                    "Error al momento de generar el código OTP");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT, new Date(), "", null);
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOtp(@RequestBody RequestSolicitudOtp requestSolicitudOtp, HttpServletRequest req,
                                              HttpServletResponse respo) {
        logger.info("El cliente " + requestSolicitudOtp.getHash() + " ha enviado una solicitud para validar OTP, con la IP \t"
                + req.getRemoteAddr(), LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = null;
        try {
            if (!recaptchaService.valid(req, respo)) {
                throw new ApiRequestException("La Captcha no ha podido ser validado :(");
            }
            ResponseEntity<ApiResponse> response=otpServices.validateOtp(requestSolicitudOtp,req);
            if(response.getStatusCodeValue()==200){
                apiResponse = new ApiResponse(response.getBody().getMessage(), String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK, new Date(), "", null);
            }else{
                apiResponse = new ApiResponse(response.getBody().getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST, new Date(), "", null);
            }

        }catch (ApiRequestException e) {
            logger.error("Error al momento de validar el otp del envio de otp : " + e
                    + " \t en el metodo validateOtp");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST, new Date(), "", null);
        }catch (Exception e) {
            logger.error(
                    "Error al momento de validar el código OTP");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT, new Date(), "", null);
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


    @PostMapping(value="/cmb")
    public ResponseEntity<Object> createDiferimientoCMB(@RequestHeader String hash, HttpServletRequest request){
        logger.info("Iniciando la gestión del cliente : " + hash
                + " desde la siguiente IP : " + request.getRemoteAddr(), LOGGER_REQUEST_FORMAT);
        ApiResponse apiResponse = new ApiResponse();
        try{
            ResponseEntity<ApiResponse> resp=diferimientoServices.generateCmbOut(hash);
            apiResponse = new ApiResponse(resp.getBody().getMessage(), String.valueOf(HttpStatus.OK.value()),
                    HttpStatus.OK, new Date(), resp.getBody().getType() ,resp.getBody().getData());
        }catch (Exception e) {
            logger.error(
                    " Error al momento de generar el diferimiento de deuda del cliente con el call me back : " + e + "en el metodo createDiferimientoCMB");
            apiResponse = new ApiResponse(e.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST, new Date(), "", null);
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


}
