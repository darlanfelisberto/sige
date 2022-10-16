package br.com.sige.academico.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "curso")
public class Curso extends Model{

    @Id
    @Column(name = "id_curso", insertable = true,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCurso;

    @NotNull(message = "Informe o nome do curso.")
    private String nome;

    @Override
    public Integer getId() {
        return this.idCurso;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static class Cliente {
    }
}
