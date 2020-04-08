package banco.pichincha.diferimiento.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSolicitudOtp{

    @JsonPropertyOrder("1")
    @JsonAlias("hash")
    @JsonProperty("hash")
    private String hash;

    @JsonPropertyOrder("2")
    @JsonAlias("otp")
    @JsonProperty("otp")
    private String otp;


}
