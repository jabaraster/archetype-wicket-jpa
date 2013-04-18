#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui;

import ${package}.model.DI;
import ${package}.web.ui.page.TopPage;
import ${package}.web.ui.page.LoginPage;
import ${package}.web.ui.page.LogoutPage;
import ${package}.web.ui.page.RestrictedPageBase;
import jabara.wicket.LoginPageInstantiationAuthorizer;
import jabara.wicket.MarkupIdForceOutputer;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

/**
 *
 */
public class WicketApplication extends WebApplication {

    private static final String ENC = "UTF-8"; //${symbol_dollar}NON-NLS-1${symbol_dollar}

    /**
     * @see org.apache.wicket.Application${symbol_pound}getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return TopPage.class;
    }

    /**
     * @see org.apache.wicket.protocol.http.WebApplication${symbol_pound}newSession(org.apache.wicket.request.Request, org.apache.wicket.request.Response)
     */
    @Override
    public Session newSession(final Request pRequest, @SuppressWarnings("unused") final Response pResponse) {
        return new AppSession(pRequest);
    }

    /**
     * @see org.apache.wicket.protocol.http.WebApplication${symbol_pound}init()
     */
    @Override
    protected void init() {
        super.init();

        mountPages();
        initializeEncoding();
        initializeInjection();
        initializeSecurity();
        initializeOther();
    }

    private void initializeEncoding() {
        getMarkupSettings().setDefaultMarkupEncoding(ENC);
        getRequestCycleSettings().setResponseRequestEncoding(getMarkupSettings().getDefaultMarkupEncoding());
    }

    private void initializeInjection() {
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, DI.getInjector()));
    }

    private void initializeOther() {
        getComponentInstantiationListeners().add(new MarkupIdForceOutputer());
    }

    private void initializeSecurity() {
        getSecuritySettings().setAuthorizationStrategy(new LoginPageInstantiationAuthorizer() {

            @Override
            protected Class<? extends Page> getFirstPageType() {
                return TopPage.class;
            }

            @Override
            protected Class<? extends Page> getLoginPageType() {
                return LoginPage.class;
            }

            @Override
            protected Class<? extends Page> getRestictedPageType() {
                return RestrictedPageBase.class;
            }

            @Override
            protected boolean isAuthenticated() {
                final AppSession session = AppSession.get();
                return session.isAuthenticated();
            }
        });
    }

    private void mountPages() {
        this.mountPage("login", LoginPage.class); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        this.mountPage("logout", LogoutPage.class); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }
}
