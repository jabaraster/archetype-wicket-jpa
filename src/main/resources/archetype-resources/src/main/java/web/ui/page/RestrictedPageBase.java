#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.wicket.ComponentCssHeaderItem;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import ${package}.Environment;
import ${package}.web.ui.AppSession;
import ${package}.web.ui.component.BodyCssHeaderItem;

/**
 *
 */
public abstract class RestrictedPageBase extends WebPageBase {
    private static final long       serialVersionUID = -7167986041931382061L;

    private Label                   applicationNameInHeader;
    private Label                   loginUserIdInHeader;
    private BookmarkablePageLink<?> goLogout;

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
        this.add(getApplicationNameInHeader());
        this.add(getLoginUserIdInHeader());
        this.add(getGoLogout());
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(BodyCssHeaderItem.get());
        pResponse.render(ComponentCssHeaderItem.forType(RestrictedPageBase.class));
    }

    /**
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return -
     */
    protected Label getApplicationNameInHeader() {
        if (this.applicationNameInHeader == null) {
            this.applicationNameInHeader = new Label("applicationNameInHeader", Environment.getApplicationName()); //$NON-NLS-1$
        }
        return this.applicationNameInHeader;
    }

    /**
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return -
     */
    protected Link<?> getGoLogout() {
        if (this.goLogout == null) {
            this.goLogout = new BookmarkablePageLink<Object>("goLogout", LogoutPage.class); //$NON-NLS-1$
        }
        return this.goLogout;
    }

    /**
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return -
     */
    protected Label getLoginUserIdInHeader() {
        if (this.loginUserIdInHeader == null) {
            this.loginUserIdInHeader = new Label("loginUserIdInHeader", AppSession.get().getLoginUser().getUserId()); //$NON-NLS-1$
        }
        return this.loginUserIdInHeader;
    }
}
