package banco.pichincha.diferimiento.util;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

public class ApiResponse implements Serializable {

    private String message;
    private String code;
    private HttpStatus status;
    private Date date;
    private String type;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(String message, String code, HttpStatus status, Date date, String type, Object data) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.date = date;
        this.type= type;
        this.data=data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
