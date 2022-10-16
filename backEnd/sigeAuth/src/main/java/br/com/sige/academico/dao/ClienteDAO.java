package br.com.sige.academico.dao;

import br.com.sige.academico.models.Cliente;
import br.com.sige.academico.models.Curso;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class ClienteDAO extends Dao<Cliente>{

    public Cliente findClienteByName(String name){
        return (Cliente) this.em.createQuery("""
                            select c from Cliente c where c.nome = :name
                """).setParameter("name",name)
                .getSingleResult();
    }

}
