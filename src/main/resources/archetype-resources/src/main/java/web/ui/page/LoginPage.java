#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.general.Empty;
import ${package}.web.ui.AppSession;
import ${package}.web.ui.FailAuthentication;
import jabara.wicket.ErrorClassAppender;

import java.io.Serializable;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 */
@SuppressWarnings("synthetic-access")
public class LoginPage extends WebPageBase {
    private static final long serialVersionUID = 1925170327965147328L;

    private final Handler     handler          = new Handler();

    private FeedbackPanel     feedback;
    private StatelessForm<?>  form;
    private TextField<String> user;
    private PasswordTextField password;
    private Button            submitter;

    /**
     * 
     */
    public LoginPage() {
        this.add(getFeedback());
        this.add(getForm());
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase${symbol_pound}renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        addPageCssReference(pResponse, getPageClass());
        pResponse.render(OnDomReadyHeaderItem.forScript(JavaScriptUtil.getFocusScript(getUser())));
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase${symbol_pound}getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of(getString("pageTitle")); //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

    private FeedbackPanel getFeedback() {
        if (this.feedback == null) {
            this.feedback = new FeedbackPanel("feedback"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        }
        return this.feedback;
    }

    private StatelessForm<?> getForm() {
        if (this.form == null) {
            this.form = new StatelessForm<Object>("form"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
            this.form.add(getUser());
            this.form.add(getPassword());
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    private PasswordTextField getPassword() {
        if (this.password == null) {
            this.password = new PasswordTextField("password", Model.of(Empty.STRING)); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        }
        return this.password;
    }

    @SuppressWarnings("serial")
    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //${symbol_dollar}NON-NLS-1${symbol_dollar}
                @Override
                public void onError() {
                    LoginPage.this.handler.onSubmitterError();
                }

                @Override
                public void onSubmit() {
                    LoginPage.this.handler.tryLogin();
                }
            };
        }
        return this.submitter;
    }

    private TextField<String> getUser() {
        if (this.user == null) {
            this.user = new TextField<String>("user", Model.of(Empty.STRING)); //${symbol_dollar}NON-NLS-1${symbol_dollar}
            this.user.setRequired(true);
        }
        return this.user;
    }

    private class Handler implements Serializable {
        private static final long        serialVersionUID   = 6317461189636878176L;

        private final ErrorClassAppender errorClassAppender = new ErrorClassAppender(Model.of("error")); //${symbol_dollar}NON-NLS-1${symbol_dollar}

        private void onSubmitterError() {
            this.errorClassAppender.addErrorClass(getForm());
        }

        private void tryLogin() {
            try {
                AppSession.get().login(getUser().getModelObject(), getPassword().getModelObject());
                setResponsePage(getApplication().getHomePage());
            } catch (final FailAuthentication e) {
                error(getString("message.failLogin")); //${symbol_dollar}NON-NLS-1${symbol_dollar}
                this.errorClassAppender.addErrorClass(getForm());
            }
        }
    }

}
