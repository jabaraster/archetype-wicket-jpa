#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

import ${package}.web.ui.AppSession;

/**
 * 
 */
public class LogoutPage extends WebPage {

    /**
     * 
     */
    public LogoutPage() {
        AppSession.get().invalidateNow();
        throw new RestartResponseException(LoginPage.class);
    }
}
