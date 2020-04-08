package banco.pichincha.diferimiento.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecaptchaResponseEntity {

    String score;        
    boolean success;         
}
