#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import ${package}.web.ui.component.AppPanel;

import jabara.wicket.ComponentCssHeaderItem;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *
 */
public abstract class RestrictedPageBase extends WebPageBase {

    /**
     * 
     */
    protected RestrictedPageBase() {
        this(new PageParameters());
    }

    /**
     * @param pParameters -
     */
    protected RestrictedPageBase(final PageParameters pParameters) {
        super(pParameters);
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(RestrictedPageBase.class));
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#createRightPanel(java.lang.String)
     */
    @Override
    protected Panel createRightPanel(final String pId) {
        return new P(pId);
    }

    /**
     * @return -
     */
    @SuppressWarnings("synthetic-access")
    protected Link<?> getGoLogout() {
        return ((P) getRightPanel()).getGoLogout();
    }

    private static class P extends AppPanel {

        private Link<?>           goLogout;

        public P(final String pId) {
            super(pId);
            this.add(getGoLogout());
        }

        private Link<?> getGoLogout() {
            if (this.goLogout == null) {
                this.goLogout = new BookmarkablePageLink<>(id(), LogoutPage.class);
            }
            return this.goLogout;
        }

    }
}
