package banco.pichincha.diferimiento.services;

import banco.pichincha.diferimiento.model.RecaptchaResponseEntity;
import banco.pichincha.diferimiento.repository.IRecaptchaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RecaptchaService implements IRecaptchaService {

    private static final String LOGGER_RESPONSE_FORMAT = "001-RES";
    private static final String LOGGER_RESQUEST_FORMAT = "001-REQ";
    private static final String LOGGER_ERROR_FORMAT = "001-ERROR";
    private static final String LOGGER_GOOGLE_REQUEST_FORMAT = "004-REQ";
    private static final String LOGGER_GOOGLE_RESPONSE_FORMAT = "004-RES";        
    private static final Logger logger = LoggerFactory.getLogger(RecaptchaService.class);
    @Autowired
    private Environment env;
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Boolean valid(HttpServletRequest requestServlet, HttpServletResponse responseServlet) {
        String json = "";
        String log;
        //String guid = requestServlet.getHeader("guid");
        String method = "recaptcha-incremento";
        String token = requestServlet.getHeader("recaptcha-token");
        float score = Float.parseFloat(env.getRequiredProperty("google.recaptcha.minScore"));
        //String urls = env.getProperty("google.recaptcha.urlsExcluded");
        //System.out.println(urls);
        log = String.join("-", LOGGER_RESQUEST_FORMAT, method, "{}");
        logger.debug(log, json);

        if (requestServlet.getMethod().equals("OPTIONS")) {
            responseServlet.setStatus(HttpStatus.OK.value());
            log = String.join("-", LOGGER_RESPONSE_FORMAT, method, "OPTIONS", "{}");
            logger.error(log, true);
            return true;
        }

        /*if (urls != null) {
            logger.info(urls);
            String[] arrayUrl = urls.split(",");
            for (String url : arrayUrl) {
                if (requestServlet.getRequestURI().contains(url.trim())) {
                    responseServlet.setStatus(HttpStatus.OK.value());
                    log = String.join("-", LOGGER_RESPONSE_FORMAT, method, " Exclude", "{}", "{}");
                    logger.error(log, url, true);
                    return true;
                }
            }
        }*/

        try {
            responseServlet.setStatus(HttpStatus.FORBIDDEN.value());
            String url = env.getRequiredProperty("google.recaptcha.url");
            logger.info(url);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("secret", env.getRequiredProperty("google.recaptcha.secret"));
            map.add("response", token);             
            

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            json = new ObjectMapper().writeValueAsString(request);
            log = String.join("-", LOGGER_GOOGLE_RESPONSE_FORMAT, method, "{}");
            logger.debug(log, json);

            ResponseEntity<RecaptchaResponseEntity> response = restTemplate.postForEntity(url, request, RecaptchaResponseEntity.class);
            json = new ObjectMapper().writeValueAsString(response.getBody());
            log = String.join("-", LOGGER_GOOGLE_REQUEST_FORMAT, method, "{}");
            logger.debug(log, json);    
                       
            if (response.getBody() != null &&  
                    response.getBody().getScore() != null &&
                    response.getBody().isSuccess() &&  
                    Float.parseFloat(response.getBody().getScore()) >= score
            ) {            	
                responseServlet.setStatus(HttpStatus.OK.value());
                log = String.join("-", LOGGER_RESPONSE_FORMAT, method, "{}");
                logger.debug(log, true);
                return true;
            }

        } catch (Exception e) {
            log = String.join("-", LOGGER_ERROR_FORMAT, method, "{}");
            logger.error(log, e, false);
            return false;
        }
        log = String.join("-", LOGGER_RESPONSE_FORMAT, method, "{}");
        logger.info(log, false);
        return false;

    }
}
