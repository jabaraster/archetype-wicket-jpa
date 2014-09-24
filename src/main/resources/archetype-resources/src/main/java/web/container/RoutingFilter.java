#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.container;

import jabara.servlet.routing.IRouter;
import jabara.servlet.routing.RouterBase;
import jabara.servlet.routing.RoutingFilterBase;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Singleton
public class RoutingFilter extends RoutingFilterBase {

    /**
     * @see jabara.servlet.routing.RoutingFilterBase#createRouter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected IRouter createRouter(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        return new Router(pRequest, pResponse);
    }

    private static class Router extends RouterBase {

        Router(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
            super(pRequest, pResponse);
        }

        @Override
        protected String getTopPagePath() {
            return WebPaths.PATH_ROOT;
        }

        @Override
        protected void routingCore() throws Exception {
            redirectIfMatch(WebPaths.PATH_ROOT, WebPaths.PATH_UI);
        }
    }

}
