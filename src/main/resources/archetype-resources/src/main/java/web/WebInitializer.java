#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.wicket.util.IProvider;

import ${package}.service.IUserService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * 
 */
@WebListener
public class WebInitializer extends GuiceServletContextListener {

    private Injector injector;

    /**
     * @see com.google.inject.servlet.GuiceServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent pServletContextEvent) {
        super.contextInitialized(pServletContextEvent);

        final ServletContext servletContext = pServletContextEvent.getServletContext();

        addFilter(servletContext, GuiceFilter.class) //
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*"); //$NON-NLS-1$
    }

    /**
     * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
     */
    @Override
    protected Injector getInjector() {
        if (this.injector == null) {
            this.injector = createInjector();
            initializeDatabase();
        }
        return this.injector;
    }

    private Injector createInjector() {
        return Guice.createInjector(new WebModule(new IProvider<Injector>() {
            @SuppressWarnings("synthetic-access")
            @Override
            public Injector get() {
                return WebInitializer.this.injector;
            }
        }));
    }

    private void initializeDatabase() {
        this.injector.getInstance(IUserService.class).insertAdministratorIfNotExists();
    }

    private static Dynamic addFilter(final ServletContext pServletContext, final Class<? extends Filter> pFilterType) {
        return pServletContext.addFilter(pFilterType.getName(), pFilterType);
    }
}
