package com.cortica.interview;

import com.cortica.interview.db.ImageEntity;
import com.cortica.interview.health.BasicHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.Path;
import java.util.Map;

public class CorticaImageFactoryApplication extends Application<CorticaImageFactoryConfiguration> {

    public static final HibernateBundle<CorticaImageFactoryConfiguration> HIBERNATE_BUNDLE =
            new HibernateBundle<CorticaImageFactoryConfiguration>(ImageEntity.class) {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(CorticaImageFactoryConfiguration corticaImageFactoryConfiguration) {
                    return corticaImageFactoryConfiguration.getDataSourceFactory();
                }
            };

    public static void main(final String[] args) throws Exception {
        new CorticaImageFactoryApplication().run(args);
    }

    @Override
    public String getName() {
        return "CorticaImageFactory";
    }

    @Override
    public void initialize(final Bootstrap<CorticaImageFactoryConfiguration> bootstrap) {
        bootstrap.addBundle(HIBERNATE_BUNDLE);
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets", "/assets"));
    }

    @Override
    public void run(final CorticaImageFactoryConfiguration configuration,
                    final Environment environment) {
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration", configuration);
        parent.registerShutdownHook();
        parent.start();

        //the real main app context has a link to the parent context
        ctx.setParent(parent);
        ctx.register(SpringConfiguration.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        //final ImageDao imageDao = new ImageDao(HIBERNATE_BUNDLE.getSessionFactory());
        //environment.jersey().register(new ImageResource(imageDao));
        environment.healthChecks().register("basicHealthCheck", new BasicHealthCheck());

        environment.servlets().addServletListeners(new ContextLoaderListener(ctx));
    }
}
