package br.com.sige.academico.oidc.util;

import br.com.sige.academico.models.AuthLogin;
import br.com.sige.academico.oidc.OpenIdConstant;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.sige.academico.oidc.OpenIdConstant.*;
import static br.com.sige.academico.oidc.OpenIdConstant.NONCE;
import static java.util.UUID.randomUUID;

@RequestScoped
public class OidcTokenUtil {

    public static final int expira = 1000 * 60 * 200;
    public static final String ISSUR = "http://localhost:8081/auth/realms/sige";

    @Inject OidcKeysUtil oidcKeysUtil;

    public String createIdToken(AuthLogin authLogin, String nonce) {
        Date now = new Date();

        JWTClaimsSet.Builder jstClaimsBuilder =
                new JWTClaimsSet.Builder()
                        .issuer(ISSUR)
                        .subject(authLogin.getUsuario().getId().toString())
                        .audience(List.of(authLogin.getCliente().getNome()))
                        .expirationTime(new Date(now.getTime() + expira))
                        .notBeforeTime(now)
                        .issueTime(now)
                        .jwtID(randomUUID().toString())
                        .claim(CODE, authLogin.getState())
                        .claim(PREFERRED_USERNAME, authLogin.getUsuario().getUsername())
                        .claim(GROUPS, authLogin.getUsuario().getArrayPermissoes());

        if (nonce != null) {
            jstClaimsBuilder.claim(NONCE, nonce);
        }

        JWSSigner signer = null;
        SignedJWT signedJWT = null;
        try {
            signer = new RSASSASigner(oidcKeysUtil.pairRsaKey);

            signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(oidcKeysUtil.pairRsaKey.getKeyID()).build(),
                    jstClaimsBuilder.build()
            );

            signedJWT.sign(signer);


        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return signedJWT.serialize();
    }

    public String createAccessToken(AuthLogin authLogin,String nonce) {
        //header https://www.rfc-editor.org/rfc/rfc7515.txt
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(oidcKeysUtil.pairRsaKey.getKeyID())//inform qual chave foi usada na assinatura
                .type(JOSEObjectType.JWT)
                .build();

        Date now = new Date();

        JWTClaimsSet.Builder jstClaimsBuilder = new JWTClaimsSet.Builder()
                .issuer(ISSUR)
                .subject(authLogin.getUsuario().getId().toString())//id do usuario na api
                .audience(List.of(authLogin.getCliente().getNome()))
                .expirationTime(new Date(now.getTime() + expira))
                .notBeforeTime(now)
                .issueTime(now)
                .jwtID(randomUUID().toString())
                .claim(CODE, authLogin.getCode())
                .claim(GROUPS, authLogin.getUsuario().getArrayPermissoes());

        if(nonce != null){
            jstClaimsBuilder.claim(NONCE, nonce);
        }

        addRolesForWildflyOidcClient(jstClaimsBuilder, authLogin);

        jstClaimsBuilder.claim(HeaderParameterNames.TYPE, BEARER_TYPE);

        jstClaimsBuilder.claim(AUTHORIZED_PARTY, authLogin.getCliente().getNome());
        jstClaimsBuilder.claim(SCOPE, OPENID_SCOPE);
        jstClaimsBuilder.claim(SID, authLogin.getState());
        jstClaimsBuilder.claim(EMAIL_VERIFIED, false);
        jstClaimsBuilder.claim(NAME, "darlan da silva");
        jstClaimsBuilder.claim(PREFERRED_USERNAME, authLogin.getUsuario().getUsername());
        jstClaimsBuilder.claim(LOCALE, "pt-BR");
        jstClaimsBuilder.claim(GIVEN_NAME, "darlan");
        jstClaimsBuilder.claim(FAMILY_NAME, "da silva");
        jstClaimsBuilder.claim(EMAIL, "darlan.felisberto@gmail.com");

        JWSSigner signer = null;
        SignedJWT signedJWT = null;
        try {
            signer = new RSASSASigner(oidcKeysUtil.pairRsaKey);

            signedJWT = new SignedJWT(header, jstClaimsBuilder.build());

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addRolesForWildflyOidcClient(JWTClaimsSet.Builder jstClaimsBuilder,AuthLogin authLogin){
        Map<String, Object> roles = new HashMap<>();
        roles.put("roles", authLogin.getUsuario().getArrayPermissoes());
        jstClaimsBuilder.claim("realm_access", roles);
    }

    public Response.ResponseBuilder createResponse(AuthLogin authLogin, String redirectUri) throws URISyntaxException {
       return switch (authLogin.getCliente().getResponseType()){
            case CODE -> this.createCodeResponse(authLogin,redirectUri);
            case TOKEN -> this.createTokenResponse(authLogin,redirectUri);
            default -> null;
        };
    }

    public Response.ResponseBuilder createCodeResponse(AuthLogin authLogin, String redirectUri) throws URISyntaxException {
        Response.ResponseBuilder rb = Response.ok();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        jsonBuilder.add(SESSION_STATE, authLogin.getState().toString());
        jsonBuilder.add(IDENTITY_TOKEN, this.createIdToken(authLogin,null));
        jsonBuilder.add(ACCESS_TOKEN, this.createAccessToken(authLogin,null));
        jsonBuilder.add(TOKEN_TYPE, OpenIdConstant.BEARER_TYPE);
        jsonBuilder.add(EXPIRES_IN, OidcTokenUtil.expira);

        //https://openid.net/specs/openid-connect-core-1_0.html#rfc.section.3.1.3.3
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        return rb.location(authLogin.getCliente().urlRedirecionamento(redirectUri))
                .entity(jsonBuilder.build())
                .cacheControl(cc);
    }

    public Response.ResponseBuilder createTokenResponse(AuthLogin authLogin, String redirectUri) throws URISyntaxException {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        String paramUrl = "?" + SESSION_STATE + "=" + authLogin.getState().toString()
                + "&" + ACCESS_TOKEN + "=" +this.createAccessToken(authLogin,null)
                + "&" + TOKEN_TYPE + "=" + OpenIdConstant.BEARER_TYPE
                + "&" + EXPIRES_IN + "=" + OidcTokenUtil.expira;

        //https://openid.net/specs/openid-connect-core-1_0.html#rfc.section.3.1.3.3
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        return Response.seeOther(authLogin.getCliente().urlRedirecionamento(redirectUri + paramUrl))
                .cacheControl(cc);
    }

    public Response.ResponseBuilder createUrlRedirectCodeResponse(AuthLogin authLogin, String redirectUri) throws URISyntaxException {
        //https://openid.net/specs/openid-connect-core-1_0.html#rfc.section.3.1.3.3
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        return Response.seeOther(authLogin.getCliente().urlRedirecionamento(redirectUri + authLogin.queryParam()))
                .cacheControl(cc);
    }
}
