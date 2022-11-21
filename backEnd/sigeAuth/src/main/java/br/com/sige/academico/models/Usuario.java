package br.com.sige.academico.models;

import br.com.sige.academico.util.Pbkdf2Hash;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuario", schema = "auth")
public class Usuario extends  Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String username;

    private String password;

    private String salt;

    @Transient
    private String nome;

    @Transient
    private String email;

    @ManyToMany
    @JoinTable(
            name ="usuario_permissao",
            schema = "auth",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao")
    )
    private Set<Permissao> listPermissao;

    public Integer getId() {
        return this.idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(String password){
        Pbkdf2Hash pbkd = new Pbkdf2Hash().initialize(Pbkdf2Hash.parametersDefault);
        return pbkd.verify(this.password,pbkd.generate(password, this.salt));
    }

    public String[] getArrayPermissoes(){
        String[] array = new String[this.listPermissao.size()+1];
        int c = 0;
        for (Permissao p : this.listPermissao) {
            array[c++] = p.getNome();
        }
        array[c] = "user";
        return array;
    }

    public Set<Permissao> getListPermissao() {
        return listPermissao;
    }

    public void setListPermissao(Set<Permissao> listPermissao) {
        this.listPermissao = listPermissao;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
