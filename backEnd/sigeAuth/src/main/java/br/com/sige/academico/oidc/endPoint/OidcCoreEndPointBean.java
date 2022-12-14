package br.com.sige.academico.oidc.endPoint;

import static br.com.sige.academico.oidc.OpenIdConstant.*;
import static java.util.UUID.randomUUID;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;

import java.net.URISyntaxException;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import br.com.sige.academico.dao.AuthLoginDAO;
import br.com.sige.academico.dao.ClienteDAO;
import br.com.sige.academico.dao.UsuarioDAO;
import br.com.sige.academico.models.AuthLogin;
import br.com.sige.academico.models.Cliente;
import br.com.sige.academico.models.Usuario;
import br.com.sige.academico.oidc.util.OidcKeysUtil;
import br.com.sige.academico.oidc.util.OidcTokenUtil;
import br.com.sige.academico.oidc.OpenIdConstant;
import br.com.sige.academico.oidc.util.ResponseTypeEnum;
import io.quarkus.qute.Template;
import io.vertx.core.http.HttpServerRequest;


@Path("/auth/realms/{realm}")
public class OidcCoreEndPointBean {

    public static final String TOKEN_END_POINT_LINK = "/token";
    public static final String WELL_KNOW_END_POINT_LINK = "/.well-known/openid-configuration";
    public static final String CERTS_END_POINT_LINK = "/certs";
    public static final String AUTH_END_POINT_LINK = "/auth";
    public static final String LOGIN_AUTH_END_POINT_LINK = "/loginAuth";
    public static final String END_SESSION_ENDPOINT_LINK = "/logout";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Inject Template formLogin;
    @Inject Template erroAuth;
    @Inject
    ClienteDAO clienteDAO;
    @Inject
    UsuarioDAO usuarioDAO;
    @Inject
    AuthLoginDAO authLoginDAO;
    @Inject
    OidcKeysUtil oidcKeysUtil;
    @Inject
    OidcTokenUtil oidcTokenUtil;

    @GET
    @Path(WELL_KNOW_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response getConfiguration2(@PathParam("realm") String name) {
        return Response.ok(WELL_KNOW).build();
    }

    @GET
    @Path(CERTS_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response getJwkFile() {
        return Response.ok(oidcKeysUtil.getPairKey().toJSONObject()).build();
    }

    @GET
    @Path(USERINFO_ENDPOINT)
    @RolesAllowed({"user"})
    @Produces(APPLICATION_JSON)
    public Response userInfoEndpoint(){
        System.out.println("user end point");
        return Response.ok("{\"teste\":1}").build();
    }

    @GET
    @Path(AUTH_END_POINT_LINK)
    @PermitAll
    public Response authEndpoint(
            @QueryParam(CLIENT_ID) String clientId, @QueryParam(SCOPE) String scope,
            @QueryParam(RESPONSE_TYPE) String responseType, @QueryParam(NONCE) String nonce,
            @QueryParam(STATE) String state, @QueryParam(REDIRECT_URI) String redirectUri) throws URISyntaxException {

//        if (!CODE.equals(responseType) || !TOKEN.equals(responseType)) {
////            return this.createJsonError("invalid_grant_type");
//            return Response.ok(erroAuth.render(), TEXT_HTML).build();
//        }

        if (!OPENID_SCOPE.equals(scope)) {
            return Response.ok(erroAuth.render(), TEXT_HTML).build();
        }

        if (state == null) {
            return Response.ok(erroAuth.render(), TEXT_HTML).build();
        }

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        return retornaLoginPage(clientId,scope,state,redirectUri,false);
    }

    public Response retornaLoginPage(String clientId,String scope,String state,String redirectUri,boolean msgErros){
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        return Response.ok(formLogin.data("clientId", clientId)
                .data("scope", scope)
                .data("state", state)
                .data("redirectUri", redirectUri)
                .data("msgError",msgErros)
                .render(), TEXT_HTML)
                .cacheControl(cc)
                .build();
    }

    public Response createJsonError(String tipo) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add(ERROR_PARAM, tipo);
        return Response.serverError().entity(jsonBuilder.build()).build();
    }

    @GET
    @Path(LOGIN_AUTH_END_POINT_LINK)
    @PermitAll
    public Response loginauth() {
        return Response.ok("<p> nada para mostrar</p>", TEXT_HTML).build();
    }

    @POST
    @Path(LOGIN_AUTH_END_POINT_LINK)
    @PermitAll
    public Response loginAuth(@FormParam(CLIENT_ID) String clientId, @FormParam(SCOPE) String scope,
                              @FormParam(RESPONSE_TYPE) String responseType, @FormParam(NONCE) String nonce,
                              @FormParam(STATE) String state, @FormParam(REDIRECT_URI) String redirectUri,
                              @FormParam("username") String username, @FormParam("password") String password
                            ) throws URISyntaxException {

        Cliente client = this.clienteDAO.findClienteByName(clientId);

        if (client == null) {
            return this.createJsonError("invalid_client_id");
        }
        Usuario user = this.usuarioDAO.findUsuarioByUsername(username);
        if (user == null || !user.authenticate(password)) {
            return retornaLoginPage(clientId,scope,state,redirectUri,true);
        }

        AuthLogin authLogin = new AuthLogin(UUID.fromString(state), randomUUID(), user, client);

        this.authLoginDAO.persist(authLogin);
        Response.ResponseBuilder rb;
        if (client.getResponseType().equals(ResponseTypeEnum.CODE)){
            rb = this.oidcTokenUtil.createUrlRedirectCodeResponse(authLogin,redirectUri);
        }else{
            rb = this.oidcTokenUtil.createTokenResponse(authLogin,redirectUri);
        }

        return rb.build();
    }


    /**
     * remover isso depois, ?? so pra teste isso
     *
     * inicio remover
     */
    @Context
    HttpServerRequest request;
    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders hh;

    public void mostra() {
        System.out.println(uriInfo.getRequestUri());

        hh.getRequestHeaders().forEach((as, valor) -> {
            System.out.println(as + ":" + valor);
        });
        uriInfo.getQueryParameters().forEach((as, valor) -> {
            System.out.println(as + ":" + valor);
        });

    }

    /**
     * fim remover
     */

    /**
     * Como n??o mantenho status dos tokens, n??o ?? necessario faze nada
     *
     * @param authorizationHeader
     * @param refresh
     * @return
     */
    @POST
    @Path(END_SESSION_ENDPOINT_LINK)
    public Response logoutEntPoint(@HeaderParam(AUTHORIZATION_HEADER) String authorizationHeader,
                                   @FormParam(REFRESH_TOKEN) String refresh){
        System.out.println(END_SESSION_ENDPOINT);
        mostra();
        //esse endpoint deve retornar um 204 nocontent
        return Response.noContent().build();
    }

    /**
     * https://connect2id.com/products/server/docs/api/token
     *
     * @param authorizationHeader
     * @param clientId
     * @param clientSecret
     * @param grantType
     * @param code
     * @param state
     * @param redirectUri
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Path(TOKEN_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response tokenEndpointSigneddsadsad(
            @HeaderParam(AUTHORIZATION_HEADER) String authorizationHeader,
            @FormParam(CLIENT_ID) String clientId, @FormParam(CLIENT_SECRET) String clientSecret,
            @FormParam(GRANT_TYPE) String grantType, @FormParam(CODE) String code,
            @FormParam(STATE) String state,
            @FormParam(REDIRECT_URI) String redirectUri) throws URISyntaxException {

        if (!AUTHORIZATION_CODE.equals(grantType)) {
            this.createJsonError("invalid_grant_type");
        }

        AuthLogin authLogin = this.authLoginDAO.findAuthLoginByCode(code);

        if (authLogin == null) {
            return createJsonError("invalid_auth_code");
        } else{
            authLogin.setUsado(true);
            if (!authLogin.aindaValido()) {
                return createJsonError("invalid_auth_code");
            }
        }

        if (authorizationHeader != null) {
            if (!authLogin.verifyHeaderAuthorization(authorizationHeader)) {
                return this.createJsonError("invalid_client_id");
            }
        } else {
            if (!authLogin.verifyHeaderAuthorization(clientId, clientSecret)) {
                return this.createJsonError("invalid_client_secret");
            }
        }

        this.authLoginDAO.merge(authLogin);

        return this.oidcTokenUtil.createResponse(authLogin,redirectUri).build();
    }

}