#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.web.ui.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author ${groupId}
 */
public class IndexPage extends RestrictedPageBase {
    private static final long serialVersionUID = -4965903336608758671L;

    /**
     * 
     */
    public IndexPage() {
    }

    /**
     * @see ${package}.web.ui.page.CoralWebPageBase${symbol_pound}getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

}
