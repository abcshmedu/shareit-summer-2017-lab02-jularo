package edu.hm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import edu.hm.shareit.businessLayer.IMediaService;
import edu.hm.shareit.businessLayer.MediaService;
import edu.hm.shareit.persistence.IMediaDAO;
import edu.hm.shareit.persistence.MediaDAO;

/**
 * Context Listener to enable usage of google guice together with jersey.
 *
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public class ShareitServletContextListener extends GuiceServletContextListener {

    private static final Injector injector = Guice.createInjector(new ServletModule() {
        @Override
        protected void configureServlets() {
            bind(IMediaService.class).to(MediaService.class);
            bind(IMediaDAO.class).to(MediaDAO.class);
        }
    });

    @Override
    protected Injector getInjector() {
        return injector;
    }

    /**
     * This method is only required for the HK2-Guice-Bridge in the Application class.
     *
     * @return Injector instance.
     */
    static Injector getInjectorInstance() {
        return injector;
    }

}
