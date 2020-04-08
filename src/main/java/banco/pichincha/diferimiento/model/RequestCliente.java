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
public class RequestCliente {


    @JsonPropertyOrder("1")
    @JsonAlias("nombres")
    @JsonProperty("nombres")
    String nombres;

    @JsonPropertyOrder("2")
    @JsonAlias("apellidos")
    @JsonProperty("apellidos")
    String apellidos;

    @JsonPropertyOrder("3")
    @JsonAlias("identificacion")
    @JsonProperty("identificacion")
    String identificacion;

    @JsonPropertyOrder("4")
    @JsonAlias("telefono")
    @JsonProperty("telefono")
    String telefono;

    @JsonPropertyOrder("5")
    @JsonAlias("email")
    @JsonProperty("email")
    String email;



}
