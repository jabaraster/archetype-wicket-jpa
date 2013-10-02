#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import ${package}.web.ui.AppSession;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

/**
 * 
 */
public class LogoutPage extends WebPage {
    private static final long serialVersionUID = -3810270407936165942L;

    /**
     * 
     */
    public LogoutPage() {
        AppSession.get().invalidateNow();
        throw new RestartResponseException(LoginPage.class);
    }
}
