#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import ${package}.Environment;
import ${package}.web.rest.RestApplication;
import ${package}.web.ui.WicketApplication;
import jabara.jpa.util.SystemPropertyToPostgreJpaPropertiesParser;
import jabara.jpa_guice.SinglePersistenceUnitJpaModule;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.util.IProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * 
 */
@WebListener
public class WebInitializer extends GuiceServletContextListener {
    /**
     * 
     */
    public static final String PATH_UI   = "/ui/";  //$NON-NLS-1$

    /**
     * 
     */
    public static final String PATH_REST = "/rest/"; //$NON-NLS-1$
    /**
     * 
     */
    public static final String PATH_ROOT = "/";     //$NON-NLS-1$
    /**
     * 
     */
    public static final char   WILD_CARD = '*';

    private Injector           injector;

    /**
     * @see com.google.inject.servlet.GuiceServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent pServletContextEvent) {
        super.contextInitialized(pServletContextEvent);
        addFilter(pServletContextEvent.getServletContext(), GuiceFilter.class) //
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, PATH_ROOT + WILD_CARD);
    }

    /**
     * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
     */
    @Override
    protected Injector getInjector() {
        this.injector = createInjector();
        return this.injector;
    }

    private Injector createInjector() {
        return Guice.createInjector(new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                install(new SinglePersistenceUnitJpaModule( //
                        Environment.getApplicationName() //
                        , new SystemPropertyToPostgreJpaPropertiesParser() //
                ));
                initializeJersey();
                initializeWicket();
            }

            private void initializeJersey() {
                final Map<String, String> params = new HashMap<String, String>();
                params.put(ServletContainer.APPLICATION_CONFIG_CLASS, RestApplication.class.getName());
                serve(PATH_REST + WILD_CARD).with(GuiceContainer.class, params);
            }

            private void initializeWicket() {
                final String path = PATH_UI + WILD_CARD;
                final Map<String, String> params = new HashMap<String, String>();
                params.put(WicketFilter.FILTER_MAPPING_PARAM, path);

                // 一般的には"applicationClassName"というキーに対してアプリケーションクラス名を登録するのですが
                // "applicationClassName"という値を持つ定数が、どうもWicketからは提供されていないようです.
                // しかしWicketFilter.APP_FACT_PARAMならば提供されているので、
                // アプリケーションクラスを返すファクトリクラスを作って、それを指定するようにしています.
                params.put(WicketFilter.APP_FACT_PARAM, F.class.getName());

                filter(path).through(new E(new IProvider<Injector>() {

                    @SuppressWarnings("synthetic-access")
                    @Override
                    public Injector get() {
                        return WebInitializer.this.injector;
                    }
                }), params);
            }
        });
    }

    private static Dynamic addFilter(final ServletContext pServletContext, final Class<? extends Filter> pFilterType) {
        return pServletContext.addFilter(pFilterType.getName(), pFilterType);
    }

    /**
     * 
     */
    public static class F implements IWebApplicationFactory {

        /**
         * @see org.apache.wicket.protocol.http.IWebApplicationFactory#createApplication(org.apache.wicket.protocol.http.WicketFilter)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public WebApplication createApplication(final WicketFilter pFilter) {
            return new WicketApplication(((E) pFilter).injectorProvider);
        }

        /**
         * @see org.apache.wicket.protocol.http.IWebApplicationFactory#destroy(org.apache.wicket.protocol.http.WicketFilter)
         */
        @Override
        public void destroy(@SuppressWarnings("unused") final WicketFilter pFilter) {
            // 処理なし
        }
    }

    /**
     * 
     */
    @Singleton
    private static class E extends WicketFilter {
        private final IProvider<Injector> injectorProvider;

        E(final IProvider<Injector> pInjectorProvider) {
            this.injectorProvider = pInjectorProvider;
        }
    }
}
