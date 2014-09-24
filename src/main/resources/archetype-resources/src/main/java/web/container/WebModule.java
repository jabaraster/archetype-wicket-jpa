#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.container;

import jabara.general.ArgUtil;
import jabara.general.IProducer;
import jabara.general.MapUtil;
import jabara.jpa.PersistenceXmlPropertyNames;
import jabara.jpa_guice.FallbackPropertiesProducer;
import jabara.jpa_guice.SinglePersistenceUnitJpaModule;
import jabara.jpa_guice.impl.H2PropertiesProducer;
import jabara.jpa_guice.impl.HerokuPropertiesProducer;
import jabara.jpa_guice.impl.RdsPostgreSqlPropertiesProducer;

import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.util.IProvider;
import org.eclipse.jetty.servlets.GzipFilter;

import ${package}.Environment;
import ${package}.web.rest.RestApplication;
import ${package}.web.ui.WicketApplication;

import com.google.inject.Injector;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * @author jabaraster
 */
public class WebModule extends JerseyServletModule {

    private final IProvider<Injector> injectorProvider;

    /**
     * @param pInjectorProvider -
     */
    public WebModule(final IProvider<Injector> pInjectorProvider) {
        this.injectorProvider = ArgUtil.checkNull(pInjectorProvider, "pInjectorProvider"); //$NON-NLS-1$
    }

    /**
     * @see com.google.inject.servlet.ServletModule#configureServlets()
     */
    @Override
    protected void configureServlets() {
        initializeJpa();

        if (Environment.isGzipFilterEnabled()) {
            initializeGzipFilter();
        }
        initializeRoutingFilter();

        initializeJersey();
        initializeWicket();
    }

    @SuppressWarnings("nls")
    private void initializeGzipFilter() {
        filter(WebPaths.URL_PATTERN_GZIP_FILTER).through(new GzipFilter(), MapUtil.<String, String> m("mimeTypes" //
                , "text/html" //
                        + ",text/plain" //
                        + ",text/xml" //
                        + ",text/css" //
                        + ",application/json" //
                        + ",application/xhtml+xml" //
                        + ",application/javascript" //
                        + ",application/x-javascript" //
                        + ",image/svg+xml" //
                , "minGzipSize", Integer.toString(Environment.getGzipCompressTargetMinSize()) //
                ));
    }

    private void initializeJersey() {
        final Map<String, String> params = MapUtil.m(ServletContainer.APPLICATION_CONFIG_CLASS, RestApplication.class.getName());
        serve(WebPaths.URL_PATTERN_JAX_RS).with(GuiceContainer.class, params);
    }

    private void initializeJpa() {
        install(new SinglePersistenceUnitJpaModule( //
                getPersistenceUnitName() //
                , getPropertiesProducer() //
        ));
    }

    private void initializeRoutingFilter() {
        filter(WebPaths.URL_PATTERN_ROUTING_FILTER).through(RoutingFilter.class);
    }

    private void initializeWicket() {
        final Map<String, String> params = MapUtil.m( //
                WicketFilter.FILTER_MAPPING_PARAM, WebPaths.URL_PATTERN_WICKET //
                , WicketFilter.APP_FACT_PARAM, WebApplicationFactoryImpl.class.getName() //
                );
        filter(WebPaths.URL_PATTERN_WICKET).through(new ExWicketFilter(this.injectorProvider), params);
    }

    private static String getPersistenceUnitName() {
        try {
            InitialContext.doLookup("jdbc/" + Environment.getApplicationName()); //$NON-NLS-1$
            return Environment.getApplicationName() + "_WithDataSource"; //$NON-NLS-1$
        } catch (final NamingException e) {
            return Environment.getApplicationName();
        }
    }

    private static IProducer<Map<String, String>> getPropertiesProducer() {
        return new FallbackPropertiesProducer( //
                new RdsPostgreSqlPropertiesProducer() //
                , new HerokuPropertiesProducer() //
                , new H2PropertiesProducer(Environment.getH2DatabasePath() + Environment.getDbNameSuffix()) //
        ) {
            private static final long serialVersionUID = 5818072099842398463L;

            @Override
            protected Map<String, Object> processProperties(final Map<String, Object> pProperties) {
                return MapUtil.m( //
                        pProperties //
                        //
                        , PersistenceXmlPropertyNames.Hibernate.HBM2DDL_AUTO //
                        , Environment.getHbm2DdlAuto() //
                        //
                        , PersistenceXmlPropertyNames.Hibernate.C3P0_CONNECTION_POOL_MIN_SIZE //
                        , String.valueOf(Environment.getConnectionPoolMinSize()) //
                        //
                        , PersistenceXmlPropertyNames.Hibernate.C3P0_CONNECTION_POOL_MAX_SIZE //
                        , String.valueOf(Environment.getConnectionPoolMaxSize()) //
                        //
                        , "hibernate.connection.driver_class" // ログのWARNINGを消すためにこのキーをセットする. //$NON-NLS-1$
                        , pProperties.get(PersistenceXmlPropertyNames.DRIVER) //
                        );
            }
        };
    }

    /**
     * 
     */
    public static class WebApplicationFactoryImpl implements IWebApplicationFactory {

        /**
         * @see org.apache.wicket.protocol.http.IWebApplicationFactory#createApplication(org.apache.wicket.protocol.http.WicketFilter)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public WebApplication createApplication(final WicketFilter pFilter) {
            return new WicketApplication(((ExWicketFilter) pFilter).injectorProvider);
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
    private static class ExWicketFilter extends WicketFilter {
        private final IProvider<Injector> injectorProvider;

        ExWicketFilter(final IProvider<Injector> pInjectorProvider) {
            this.injectorProvider = pInjectorProvider;
        }
    }
}
