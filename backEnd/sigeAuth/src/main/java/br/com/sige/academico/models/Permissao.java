package br.com.sige.academico.models;

import javax.persistence.*;

@Entity
@Table(name = "permissao", schema = "auth")
public class Permissao extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permissao", insertable = false)
    private Integer idPermissao;

    private String nome;

    public Permissao(){}

    public Integer getId() {
        return this.idPermissao;
    }

    public Integer getIdPermissao() {
        return idPermissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
