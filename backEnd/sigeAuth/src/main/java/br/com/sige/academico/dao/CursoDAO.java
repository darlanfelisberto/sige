package br.com.sige.academico.dao;

import br.com.sige.academico.models.Curso;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class CursoDAO extends Dao<Curso>{

    public List<Curso> listAll(){
        return this.em.createQuery("""
            select c from Curso c
        """).getResultList();
    }

}
