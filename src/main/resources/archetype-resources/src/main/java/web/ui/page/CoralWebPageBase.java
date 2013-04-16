#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.web.ui.page;

import ${package}.CoralEnv;
import jabara.general.ArgUtil;

import org.apache.wicket.Page;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author ${groupId}
 */
public abstract class CoralWebPageBase extends WebPage {
    private static final long serialVersionUID = 9011478021815065944L;

    private Label             titleLabel;

    /**
     * 
     */
    protected CoralWebPageBase() {
        this(new PageParameters());
    }

    /**
     * @param pParameters
     */
    protected CoralWebPageBase(final PageParameters pParameters) {
        super(pParameters);
        this.add(getTitleLabel());
    }

    /**
     * @see org.apache.wicket.Component${symbol_pound}renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        renderCommonHead(pResponse);
    }

    /**
     * @return HTMLのtitleタグの内容
     */
    protected abstract IModel<String> getTitleLabelModel();

    @SuppressWarnings({ "nls", "serial" })
    private Label getTitleLabel() {
        if (this.titleLabel == null) {
            this.titleLabel = new Label("titleLabel", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return getTitleLabelModel().getObject() + " - " + CoralEnv.getApplicationName();
                }
            });
        }
        return this.titleLabel;
    }

    /**
     * @param pResponse
     */
    public static void addJQueryJavaSriptReference(final IHeaderResponse pResponse) {
        ArgUtil.checkNull(pResponse, "pResponse"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(CoralWebPageBase.class, "jquery-1.8.3.min.js"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    /**
     * @param pResponse 書き込み用レスポンス.
     * @param pPageType CSSファイルの基準となるページクラス.
     */
    public static void addPageCssReference(final IHeaderResponse pResponse, final Class<? extends Page> pPageType) {
        ArgUtil.checkNull(pResponse, "pResponse"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        ArgUtil.checkNull(pPageType, "pPageType"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(pPageType, pPageType.getSimpleName() + ".css"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    /**
     * @param pResponse 書き込み用レスポンス.
     * @param pPageType jsファイルの基準となるページクラス.
     */
    public static void addPageJavaScriptReference(final IHeaderResponse pResponse, final Class<? extends Page> pPageType) {
        ArgUtil.checkNull(pResponse, "pResponse"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        ArgUtil.checkNull(pPageType, "pPageType"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(pPageType, pPageType.getSimpleName() + ".js"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    /**
     * @param pResponse 全ての画面に共通して必要なheadタグ内容を出力します.
     */
    public static void renderCommonHead(final IHeaderResponse pResponse) {
        ArgUtil.checkNull(pResponse, "pResponse"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(CoralWebPageBase.class, "fonts/icomoon/style.css"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(CoralWebPageBase.class, "bootstrap/css/bootstrap.min.css"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(CoralWebPageBase.class, "Coral.css"))); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        pResponse.render(JavaScriptHeaderItem
                .forReference(new JavaScriptResourceReference(CoralWebPageBase.class, JavaScriptUtil.COMMON_JS_FILE_PATH)));
    }

}
