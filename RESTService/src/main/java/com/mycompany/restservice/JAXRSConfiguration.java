package com.mycompany.restservice;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;


/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        return resources;
    }      

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.
            add(com.mycompany.restservice.CORSPolicy.class);
        resources.add(com.mycompany.restservice.resources.ImageResource.class);
        resources.add(com.mycompany.restservice.resources.UserResource.class);
    }   
}
