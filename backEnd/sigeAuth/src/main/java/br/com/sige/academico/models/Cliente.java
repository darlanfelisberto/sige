package br.com.sige.academico.models;

import javax.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;

@Entity
@Table(name = "cliente", schema = "auth")
public class Cliente extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", insertable = false)
    private Integer idCliente;

    private String nome;

    private String redirecionamento;

    private String secret;

    @Column(name = "cliente_url")
    private String clienteUrl;

    @Override
    public Integer getId() {
        return this.idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRedirecionamento() {
        return redirecionamento;
    }

    public URI urlRedirecionamento(String redirect) throws URISyntaxException {
        return new URI(getRedirecionamento() + redirect.replaceAll(getRedirecionamento(), ""));
    }

    public void setRedirecionamento(String redirecionamento) {
        this.redirecionamento = redirecionamento;
    }

    public String getClienteUrl() {
        return clienteUrl;
    }

    public void setClienteUrl(String clienteUrl) {
        this.clienteUrl = clienteUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
