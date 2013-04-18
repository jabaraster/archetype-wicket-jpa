#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.general.ArgUtil;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;

/**
 *
 */
public final class JavaScriptUtil {

    private static final Logger _logger             = Logger.getLogger(JavaScriptUtil.class);

    /**
     * 
     */
    public static final String  COMMON_JS_FILE_PATH = "App.js";                              //${symbol_dollar}NON-NLS-1${symbol_dollar}

    private JavaScriptUtil() {
        // 処理なし
    }

    /**
     * @param pTag フォーカスを当てる対象のタグ. <br>
     *            {@link Component${symbol_pound}setOutputMarkupId(boolean)}にtrueをセットしていることが前提.
     * @return pTagにフォーカスを当てるJavaScriptコード.
     */
    @SuppressWarnings("nls")
    public static String getFocusScript(final Component pTag) {
        ArgUtil.checkNull(pTag, "pTag"); //${symbol_dollar}NON-NLS-1${symbol_dollar}

        if (!pTag.getOutputMarkupId()) {
            _logger.warn(pTag.getId() + "(型：" + pTag.getClass().getName() + ") のoutputMarkupIdプロパティがfalseであるため、"
                    + JavaScriptUtil.class.getSimpleName() + "${symbol_pound}getFocusScript()は正常に動作しません.");
        }
        return "App.focus('" + pTag.getMarkupId() + "');"; //${symbol_dollar}NON-NLS-1${symbol_dollar} //${symbol_dollar}NON-NLS-2${symbol_dollar}
    }

}
