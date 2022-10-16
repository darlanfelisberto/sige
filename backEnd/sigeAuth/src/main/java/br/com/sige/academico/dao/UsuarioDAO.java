package br.com.sige.academico.dao;

import br.com.sige.academico.models.Cliente;
import br.com.sige.academico.models.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class UsuarioDAO extends Dao<Usuario>{

    public Usuario findUsuarioByUsername(String username){
        try {
            return (Usuario) this.em.createQuery("""
                    select u from Usuario u where u.username = :username
                """).setParameter("username",username)
                    .getSingleResult();
        }catch (NoResultException e){}

        return null;
    }

}
