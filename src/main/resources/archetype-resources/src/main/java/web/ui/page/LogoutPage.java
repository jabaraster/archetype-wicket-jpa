#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

/**
 * 
 */
public class LogoutPage extends WebPage {

    /**
     * 
     */
    public LogoutPage() {
        getSession().invalidateNow();
        throw new RestartResponseException(LoginPage.class);
    }
}
