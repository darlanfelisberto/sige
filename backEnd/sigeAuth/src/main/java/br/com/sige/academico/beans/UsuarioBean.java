package br.com.sige.academico.beans;

import br.com.sige.academico.dao.UsuarioDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
public class UsuarioBean {

    @Inject UsuarioDAO usuarioDAO;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listUsuarios/{pri}/{ult}")
    public Response listUsuarios(@PathParam("pri") Integer pri, @PathParam("ult") Integer ult){

        return null;
    }
}
