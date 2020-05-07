package banco.pichincha.diferimiento.services;

import banco.pichincha.diferimiento.model.RequestCliente;
import com.google.cloud.kms.v1.*;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class EncryptService {

    private static final String LOGGER_RESPONSE_FORMAT = "004-RES";
    private static final String LOGGER_REQUEST_FORMAT = "004-REQ";
    private static final Logger logger = LoggerFactory.getLogger(EncryptService.class);

    @Autowired
    Environment env;

    public RequestCliente getObject(RequestCliente requestCliente) {
        requestCliente.setIdentificacion(decrypt(requestCliente.getIdentificacion().getBytes(), "Identificacion"));
        requestCliente.setTelefono(decrypt(requestCliente.getTelefono().getBytes(), "Telefono"));
        requestCliente.setTipo(decrypt(requestCliente.getTipo().getBytes(), "Tipo"));
        requestCliente.setEmail(decrypt(requestCliente.getEmail().getBytes(), "Email"));
        return requestCliente;
    }

    /**
     * Funcion que permite desenriptar la trama
     *
     * @param ciphertext
     * @return
     * @throws IOException
     */
    private String decrypt(byte[] ciphertext, String tipo) {
        // Create the KeyManagementServiceClient using try-with-resources to manage client cleanup.
        try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
            AsymmetricDecryptResponse responseName =
                    client.asymmetricDecrypt(env.getRequiredProperty("gcloud.endKane"),
                            ByteString.copyFrom(Base64.getDecoder().decode(ciphertext)));

            return responseName.getPlaintext().toStringUtf8();
        } catch (Exception e) {
            logger.error("Error al momento de desencriptar la trama " + e.getMessage(), LOGGER_RESPONSE_FORMAT);
        }
        return "";
    }


}
