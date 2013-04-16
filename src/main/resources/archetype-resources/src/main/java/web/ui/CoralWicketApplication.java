#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.web.ui;

import ${package}.model.DI;
import ${package}.web.ui.page.IndexPage;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author ${groupId}
 */
public class CoralWicketApplication extends WebApplication {

    private static final String ENC = "UTF-8"; //${symbol_dollar}NON-NLS-1${symbol_dollar}

    /**
     * @see org.apache.wicket.Application${symbol_pound}getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
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
        getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
            @Override
            public void onInstantiation(final Component pComponent) {
                pComponent.setOutputMarkupId(true);
            }
        });
    }

    private void initializeSecurity() {
        // TODO implementation.
    }

    private void mountPages() {
        // TODO will add page.
    }
}
