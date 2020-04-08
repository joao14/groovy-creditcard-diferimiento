package banco.pichincha.diferimiento.util;

public class UtilResponse {

	/**
	 * Funcion que permite enmascarar el telefono
	 * @param telefono
	 * @return
	 */
	public static String enmascarar_telefono(String telefono) {
		String telefonoEnma="";
		if(telefono!=null && !telefono.isEmpty()){
			String view = telefono.substring(telefono.length() - 4, telefono.length());
			String caracteres = "";
			for (int i = 0; i < telefono.length() - 4; i++) {
				caracteres = caracteres + "*";
			}

			telefonoEnma=caracteres+view;
		}else{
			telefonoEnma="";
		}
		return telefonoEnma;
	}

	/**
	 * Funcion que permite enmascarar el correo y validarlo
	 *
	 * @param email
	 * @return
	 */
	public static String enmascarar_email(String email) {
		String emailEnmas="";
		if(email!=null && !email.isEmpty()){
			// Valida como se visualizara el correo
			String[] email_ = email.split("@");
			String inicio = "", fin = "", newEma_ = "", char_ = "";
			if (email_[0].length() > 4) {
				inicio = email_[0].substring(0, 2);
				fin = email_[0].substring(Integer.valueOf(email_[0].length()) - 2, Integer.valueOf(email_[0].length()));
				for (int i = 2; i < email_[0].length() - 2; i++) {
					char_ = char_ + "*";
				}
				newEma_ = inicio + char_ + fin + "@" + email_[1];
			} else {
				inicio = email_[0].substring(0, 1);
				for (int i = 1; i < email_[0].length(); i++) {
					char_ = char_ + "*";
				}
				newEma_ = inicio + char_ + "@" + email_[1];
			}
			emailEnmas=newEma_;

		}else{
			emailEnmas="";
		}

		return emailEnmas;
	}

	/**
	 * Funcion quqe permite enmascarar el telefono
	 *
	 * @param numero
	 * @return
	 */
	public static String enmascarar_cedula(String numero) {
		String inicio = numero.substring(0, 2);
		String fin = numero.substring(numero.length() - 2, numero.length());
		String character = "";
		for (int i = 2; i < numero.length() - 2; i++) {
			character = character + "*";
		}
		return inicio + "" + character + "" + fin;
	}

	/**
	 * Funcion para nuevas identificaciones
	 * 
	 * @param identificacion
	 * @return
	 */

	public String validateIdentificacion(String identificacion) {
		String new_identificacion = "";
		if (identificacion.length() > 10) {
			new_identificacion = identificacion.substring(4, 14);
		} else {
			new_identificacion = identificacion;
		}
		return new_identificacion;
	}

	/**
	 * Funcion que permite enviar el hash nuevo
	 * 
	 * @param hash
	 * @return
	 */
	public String validandoHash(String hash) {
		String new_hash = "";
		if (hash.contains("&")) {
			String[] hash_array = hash.split("&");
			new_hash = hash_array[0];
		} else {
			new_hash = hash;
		}
		return new_hash;
	}

	/**
	 * Funcion que permite autocompletar con ceros la identificacion
	 * @param identificacion
	 * @return
	 */
	public static String validateRequestIdentificacion(String identificacion){
		String new_="";
		if(identificacion != null && !identificacion.isEmpty()){
			String numero="";
			for(int i=0; i< (14 -(identificacion.length())) ; i++){
				numero=numero +"0";
			}
			new_=numero+identificacion;
		}
		return new_;
	}

	/**
	 * Funcion que permite obtener una identificacion correcta para el envio de OTP para clientes
	 * @param identificacion
	 * @return
	 */
	public static String validateRequestOtpIdentificacion(String identificacion){
		String new_="";
		if(identificacion != null && !identificacion.isEmpty()){
			new_=identificacion.substring(identificacion.length()-10,identificacion.length());
		}
		return new_;
	}

}
