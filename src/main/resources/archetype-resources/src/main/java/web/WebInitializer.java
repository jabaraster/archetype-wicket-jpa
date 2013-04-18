#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import ${package}.model.DI;
import ${package}.web.rest.RestApplication;
import ${package}.web.ui.WicketApplication;
import jabara.servlet.RequestDumpFilter;
import jabara.servlet.ResponseDumpFilter;
import jabara.servlet.UTF8EncodingFilter;

import java.util.EnumSet;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.eclipse.jetty.servlets.GzipFilter;

import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 *
 */
@WebListener
public class WebInitializer implements ServletContextListener {

    /**
     * 
     */
    public static final String PATH_UI = "/ui/";  //${symbol_dollar}NON-NLS-1${symbol_dollar}
    /**
     * 
     */
    public static final String PATH_REST   = "/rest/"; //${symbol_dollar}NON-NLS-1${symbol_dollar}
    /**
     * 
     */
    public static final String PATH_ROOT   = "/";     //${symbol_dollar}NON-NLS-1${symbol_dollar}

    private static final char  WILD_CARD   = '*';

    /**
     * @see javax.servlet.ServletContextListener${symbol_pound}contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(@SuppressWarnings("unused") final ServletContextEvent pSce) {
        // 処理なし
    }

    /**
     * @see javax.servlet.ServletContextListener${symbol_pound}contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent pSce) {
        final ServletContext servletContext = pSce.getServletContext();

        initializeJersey(servletContext);
        initializeWicket(servletContext);

        initializeRoutingFilter(servletContext);

        initializeGzipFilter(servletContext);

        // Filterは後に登録したものがより早く適用される.
        // このためWicketFilterが処理するリクエストにもDumpFilterを適用するには
        // WicketFilterより後にDumpFilterを登録するようにする.
        // initializeDumpFilter(servletContext);

        initializeEncodingFilter(servletContext);

        initializeOther();
    }

    private static FilterRegistration.Dynamic addFiter(final ServletContext pServletContext, final Class<? extends Filter> pFilterType) {
        return pServletContext.addFilter(pFilterType.getName(), pFilterType);
    }

    private static ServletRegistration.Dynamic addServlet(final ServletContext pContext, final Class<? extends Servlet> pServletType) {
        return pContext.addServlet(pServletType.getName(), pServletType);
    }

    private static void initializeDumpFilter(final ServletContext pServletContext) {
        final String path = PATH_ROOT + WILD_CARD;
        addFiter(pServletContext, RequestDumpFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, path);
        addFiter(pServletContext, ResponseDumpFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, path);
    }

    private static void initializeEncodingFilter(final ServletContext pServletContext) {
        addFiter(pServletContext, UTF8EncodingFilter.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false,
                PATH_ROOT + WILD_CARD);
    }

    @SuppressWarnings("nls")
    private static void initializeGzipFilter(final ServletContext pServletContext) {
        final Dynamic filter = addFiter(pServletContext, GzipFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, PATH_ROOT + WILD_CARD);
        // filter.setInitParameter("minGzipSize", Integer.toString(40));
        filter.setInitParameter("mimeTypes" //
                , "text/html" //
                        + ",text/plain" //
                        + ",text/xml" //
                        + ",text/css" //
                        + ",application/json" //
                        + ",application/xhtml+xml" //
                        + ",application/javascript" //
                        + ",application/x-javascript" //
                        + ",image/svg+xml" //
        );
    }

    private static void initializeJersey(final ServletContext pServletContext) {
        final ServletRegistration.Dynamic jerseyServlet = addServlet(pServletContext, ServletContainer.class);
        jerseyServlet.setInitParameter(ServletContainer.APPLICATION_CONFIG_CLASS, RestApplication.class.getName());
        jerseyServlet.addMapping(PATH_REST + WILD_CARD);
    }

    private static void initializeOther() {
        DI.get(EntityManagerFactory.class).createEntityManager();
    }

    private static void initializeRoutingFilter(final ServletContext pServletContext) {
        addFiter(pServletContext, RoutingFilter.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, PATH_ROOT + WILD_CARD);
    }

    private static void initializeWicket(final ServletContext pServletContext) {
        final String path = PATH_UI + WILD_CARD;
        final FilterRegistration.Dynamic filter = addFiter(pServletContext, WicketFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, path);
        filter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, path);
        filter.setInitParameter(WicketFilter.APP_FACT_PARAM, F.class.getName());
    }

    /**
     * 本来はprivateでいいのだが、そうするとWicketがエラーを投げるのでやむなくpublicにしています.
     * 
     * @author ${groupId}
     */
    public static final class F implements IWebApplicationFactory {

        /**
         * @see org.apache.wicket.protocol.http.IWebApplicationFactory${symbol_pound}createApplication(org.apache.wicket.protocol.http.WicketFilter)
         */
        @Override
        public WebApplication createApplication(@SuppressWarnings("unused") final WicketFilter pFilter) {
            return new WicketApplication();
        }

        /**
         * @see org.apache.wicket.protocol.http.IWebApplicationFactory${symbol_pound}destroy(org.apache.wicket.protocol.http.WicketFilter)
         */
        @Override
        public void destroy(@SuppressWarnings("unused") final WicketFilter pFilter) {
            // 処理なし
        }
    }

}
