package br.com.sige.academico.dao;

import br.com.sige.academico.models.AuthLogin;
import br.com.sige.academico.models.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import java.util.UUID;

@RequestScoped
public class AuthLoginDAO extends Dao<AuthLogin>{

    public AuthLogin findAuthLoginByCode(String code){
        try {
            return (AuthLogin) this.em.createQuery("""
                    select a from AuthLogin a 
                    join fetch a.cliente c
                    join fetch a.usuario u
                    left join fetch u.listPermissao 
                    where a.usado = false and a.code = :code
                """).setParameter("code",UUID.fromString(code))
                    .getSingleResult();
        }catch (NoResultException e){}

        return null;
    }

}
