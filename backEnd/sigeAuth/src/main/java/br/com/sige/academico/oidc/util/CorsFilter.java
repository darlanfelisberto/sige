//package br.com.sige.academico.oidc.util;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Provider
//public class CorsFilter implements ContainerResponseFilter {
//
//    @Override
//    public void filter(ContainerRequestContext requestContext,
//                       ContainerResponseContext responseContext) throws IOException {
//        Map<String,String> h = new HashMap<>();
//        h.put("Access-Control-Allow-Origin", "*");
//        h.put("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
//        h.put("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
//
//        responseContext.getHeaders().putAll(h);
//    }
//}