package banco.pichincha.diferimiento.repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
Servicio necesario para validar recaptcha de google
@cvega

 */
public interface IRecaptchaService {
    public Boolean valid(HttpServletRequest requestServlet, HttpServletResponse responseServlet) ;
}
