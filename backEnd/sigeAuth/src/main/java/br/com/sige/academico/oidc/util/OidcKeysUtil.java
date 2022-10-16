package br.com.sige.academico.oidc.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@ApplicationScoped
public class OidcKeysUtil {

    static final int SIZE_RSA_KEY = 3072;
    static final String NAME_KEY_PAIR = "/pairKeyJWK.json";
    static RSAKey pairRsaKey;
    static String PATH;

    static {
        readKey();
    }

    public void generateKeys() {
        RSAKey jwk2 = null;
        try {
            jwk2 = new RSAKeyGenerator(SIZE_RSA_KEY)
                    .algorithm(JWSAlgorithm.RS256)
                    .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                    .keyID(UUID.randomUUID().toString()) // give the key a unique ID
                    .generate();

        } catch (JOSEException e1) {
            e1.printStackTrace();
        }
    }

    static void readKey() {
        //https://connect2id.com/products/nimbus-jose-jwt/examples/jwk-generation
        String path = OidcKeysUtil.class.getResource("").getPath();

        PATH = path.replace(OidcKeysUtil.class.getPackageName().replaceAll("\\.", File.separator) + File.separator, "");
        try {
            FileInputStream f = new FileInputStream(PATH + NAME_KEY_PAIR);
            String result = new String(f.readAllBytes());
            f.close();

            pairRsaKey = RSAKey.parse(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JWKSet getPairKey(){
        return new JWKSet(pairRsaKey);
    }

}
