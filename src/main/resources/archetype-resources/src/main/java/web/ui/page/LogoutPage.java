#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.wicket.ComponentCssHeaderItem;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 */
public class LogoutPage extends WebPageBase {
    private static final long serialVersionUID         = -3810270407936165942L;

    private static final int  REFRESH_INTERVAL_MINUTES = 5;

    /**
     * 
     */
    public LogoutPage() {
        this.add(new BookmarkablePageLink<Object>("goLogin", LoginPage.class)); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    /**
     * @see org.apache.wicket.Component${symbol_pound}renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(LoginPage.class));
        pResponse.render(OnDomReadyHeaderItem.forScript("countDown(" + REFRESH_INTERVAL_MINUTES + ")")); //${symbol_dollar}NON-NLS-1${symbol_dollar} //${symbol_dollar}NON-NLS-2${symbol_dollar}
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase${symbol_pound}getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of(getString("pageTitle")); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    /**
     * @see org.apache.wicket.markup.html.WebPage${symbol_pound}onAfterRender()
     */
    @Override
    protected void onAfterRender() {
        super.onAfterRender();
        Session.get().invalidate();
    }
}
