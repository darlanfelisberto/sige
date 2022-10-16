package br.com.sige.academico.beans;

import br.com.sige.academico.dao.CursoDAO;
import br.com.sige.academico.models.Curso;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/cursos")
public class CursoBean {

    @Inject
    CursoDAO cursoDAO;


    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listAllCursos")
    public Response listAllCursos(){
        return Response.ok(this.cursoDAO.listAll(),MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("add")
    public void addCurso(){
        Curso c = new Curso();
        c.setNome(UUID.randomUUID().toString());

        this.cursoDAO.persist(c);
        System.out.println(c.getId());
    }

}
