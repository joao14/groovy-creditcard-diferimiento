package banco.pichincha.diferimiento.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecaptchaRequestEntity {

    String secret;
    String response;
    String remoteip;
}
