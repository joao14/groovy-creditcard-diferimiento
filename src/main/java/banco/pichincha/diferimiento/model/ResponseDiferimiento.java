package banco.pichincha.diferimiento.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDiferimiento {

    @JsonPropertyOrder("1")
    @JsonAlias("tkn")
    @JsonProperty("tkn")
    String tkn;

    @JsonPropertyOrder("2")
    @JsonAlias("hash")
    @JsonProperty("hash")
    String hash;

    @JsonPropertyOrder("3")
    @JsonAlias("names")
    @JsonProperty("names")
    String names;

    @JsonPropertyOrder("4")
    @JsonAlias("lastnames")
    @JsonProperty("lastnames")
    String lastnames;

    @JsonPropertyOrder("5")
    @JsonAlias("email")
    @JsonProperty("email")
    String email;

    @JsonPropertyOrder("6")
    @JsonAlias("phone")
    @JsonProperty("phone")
    String phone;
}
