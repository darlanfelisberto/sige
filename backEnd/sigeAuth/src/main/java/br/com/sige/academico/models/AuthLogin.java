package br.com.sige.academico.models;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import static br.com.sige.academico.oidc.OpenIdConstant.*;

@Entity
@Table(name = "auth_login", schema = "auth")
public class AuthLogin extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auth_login", insertable = false)
    private Integer idAuthLogin;

    private UUID state;

    private UUID code;

    @Column(name = "valido_ate")
    private LocalDateTime validoAte;

    private boolean usado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public AuthLogin(){}

    public AuthLogin(UUID state, UUID code, Usuario usuario,Cliente cliente) {
        this.state = state;
        this.code = code;
        this.usuario = usuario;
        this.cliente = cliente;
        //o client tem 30 segundos para solicitar o access token apartir deste code
        this.validoAte = LocalDateTime.now().plusSeconds(30);
        this.usado = false;
    }

    public boolean aindaValido(){
        return LocalDateTime.now().isBefore(this.validoAte);
    }

    public boolean verifyHeaderAuthorization(String authorizationHeader){
        String autorization = new String(Base64.getDecoder()
                                        .decode(authorizationHeader
                                        .substring(BASIC_TYPE.length() + 1))
                                , StandardCharsets.UTF_8);
        return autorization.equals(this.cliente.getNome() + ":" + this.cliente.getSecret().toString());
    }
    public boolean verifyHeaderAuthorization(String client,String secret){
        if(!this.cliente.getNome().equals(client)){
            return false;
        }
        if(this.cliente.getSecret() != null && !this.cliente.getSecret().toString().equals(secret)){
            return false;
        }

        return true;
    }

    public String queryParam(){
        return "?" + STATE + "=" + state + "&" + CODE + "=" + getCode();
    }

    @Override
    public Integer getId() {
        return this.idAuthLogin;
    }

    public Integer getIdAuthLogin() {
        return idAuthLogin;
    }

    public void setIdAuthLogin(Integer idAuthLogin) {
        this.idAuthLogin = idAuthLogin;
    }

    public UUID getState() {
        return state;
    }

    public void setState(UUID state) {
        this.state = state;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDateTime validoAte) {
        this.validoAte = validoAte;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}
