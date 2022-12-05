//package br.com.sige.academico.dao;
//
//import io.quarkus.hibernate.orm.PersistenceUnitExtension;
//
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.UriInfo;
//
//@RequestScoped
//@PersistenceUnitExtension
//public class TenantResolver  implements io.quarkus.hibernate.orm.runtime.tenant.TenantResolver {
//
//    @Context
//    UriInfo uriInfo;
//
//    @Override
//    public String getDefaultTenantId() {
//        return "sige";
//    }
//
//    @Override
//    public String resolveTenantId() {
//        return "sige";
//    }
//}
