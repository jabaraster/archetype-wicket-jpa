#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import ${package}.Environment;
import ${package}.web.ui.SharedResourceReferences;
import ${package}.web.ui.component.BodyCssHeaderItem;
import ${package}.web.ui.model.AppSession;

import jabara.general.ArgUtil;
import jabara.wicket.IconHeaderItem;
import jabara.wicket.JavaScriptUtil;
import jabara.wicket.Models;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author jabaraster
 */
public abstract class WebPageBase extends WebPage {
    private static final long                        serialVersionUID  = 3898142232302197798L;

    private static final CssResourceReference        REF_BOOTSTRAP_CSS = new CssResourceReference(WebPageBase.class, "bootstrap/css/bootstrap.css"); //$NON-NLS-1$
    private static final CssResourceReference        REF_APP_CSS       = new CssResourceReference(WebPageBase.class, "App.css");                    //$NON-NLS-1$
    private static final JavaScriptResourceReference REF_BOOTSTRAP_JS  = new JavaScriptResourceReference(WebPageBase.class,
                                                                               "bootstrap/js/bootstrap.js");                                        //$NON-NLS-1$

    private Label                                    titleLabel;
    private Link<?>                                  goTop;
    private Label                                    applicationName;
    private Panel                                    rightPanel;
    private Label                                    copyright;

    /**
     * 
     */
    protected WebPageBase() {
        this(new PageParameters());
    }

    /**
     * @param pParameters -
     */
    protected WebPageBase(final PageParameters pParameters) {
        super(pParameters);
        this.add(getTitleLabel());
        this.add(getGoTop());
        this.add(getRightPanel());
        this.add(getCopyright());
    }

    /**
     * @see org.apache.wicket.Component#getSession()
     */
    @Override
    public AppSession getSession() {
        return (AppSession) super.getSession();
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        renderCommonHead(pResponse);
    }

    /**
     * @param pId -
     * @return -
     */
    @SuppressWarnings("static-method")
    protected Panel createRightPanel(final String pId) {
        return new EmptyPanel(pId);
    }

    /**
     * @return -
     */
    protected Label getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = new Label(id(), Models.readOnly(Environment.getApplicationName()));
        }
        return this.applicationName;
    }

    /**
     * @return -
     */
    protected Label getCopyright() {
        if (this.copyright == null) {
            this.copyright = new Label(id(), Environment.getCopyright());
        }
        return this.copyright;
    }

    /**
     * @return -
     */
    protected Link<?> getGoTop() {
        if (this.goTop == null) {
            this.goTop = new BookmarkablePageLink<>(id(), getApplication().getHomePage());
            this.goTop.add(getApplicationName());
        }
        return this.goTop;
    }

    /**
     * @return -
     */
    protected Panel getRightPanel() {
        if (this.rightPanel == null) {
            this.rightPanel = this.createRightPanel(id());
        }
        return this.rightPanel;
    }

    /**
     * titleタグの中を表示するラベルです. <br>
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return titleタグの中を表示するラベル.
     */
    @SuppressWarnings({ "nls" })
    protected Label getTitleLabel() {
        if (this.titleLabel == null) {
            this.titleLabel = new Label(id(), Models.readOnly(getTitleLabelModel().getObject() + " - " + Environment.getApplicationName()));
        }
        return this.titleLabel;
    }

    /**
     * @return HTMLのtitleタグの内容
     */
    protected abstract IModel<String> getTitleLabelModel();

    /**
     * @param pResponse 全ての画面に共通して必要なheadタグ内容を出力します.
     */
    public static void renderCommonHead(final IHeaderResponse pResponse) {
        ArgUtil.checkNull(pResponse, "pResponse"); //$NON-NLS-1$

        pResponse.render(IconHeaderItem.forReference(SharedResourceReferences.getFavicon()));

        pResponse.render(CssHeaderItem.forReference(REF_BOOTSTRAP_CSS));
        pResponse.render(CssHeaderItem.forReference(REF_APP_CSS));
        pResponse.render(BodyCssHeaderItem.get());

        pResponse.render(JavaScriptHeaderItem.forReference(JavaScriptUtil.JQUERY_1_9_1_REFERENCE));
        pResponse.render(JavaScriptHeaderItem.forReference(REF_BOOTSTRAP_JS));
    }

    /**
     * WicketのComponentのidを類推します. <br>
     * このメソッドはWicketのComponentを生成するgetterの中から呼び出して下さい. <br>
     * 
     * @return このメソッドを呼び出したgetterからgetを除き、先頭文字を小文字にした文字列.
     */
    protected static String id() {
        final String getterName = new Throwable().getStackTrace()[1].getMethodName();
        if (getterName.length() < 4) {
            throw new IllegalStateException();
        }
        if (!getterName.startsWith("get")) { //$NON-NLS-1$
            throw new IllegalStateException();
        }
        final String s = getterName.substring("get".length()); //$NON-NLS-1$
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
