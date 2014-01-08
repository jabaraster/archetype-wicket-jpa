#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.general.Empty;
import jabara.wicket.CssUtil;
import jabara.wicket.ErrorClassAppender;
import jabara.wicket.JavaScriptUtil;
import jabara.wicket.Models;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.StringValue;

import ${package}.Environment;
import ${package}.model.FailAuthentication;
import ${package}.web.ui.AppSession;

/**
 * 
 */
@SuppressWarnings("synthetic-access")
public class LoginPage extends WebPageBase {

    private final Handler     handler          = new Handler();

    private Label             applicationName;
    private StatelessForm<?>  form;
    private TextField<String> userId;
    private PasswordTextField password;
    private AjaxButton        submitter;

    /**
     * 
     */
    public LoginPage() {
        this.add(getApplicationName());
        this.add(getForm());
    }

    /**
     * @see sandbox.quickstart.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        CssUtil.addComponentCssReference(pResponse, LoginPage.class);
        JavaScriptUtil.addFocusScript(pResponse, getUserId());
    }

    /**
     * @see sandbox.quickstart.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly(getString("pageTitle")); //$NON-NLS-1$
    }

    StatelessForm<?> getForm() {
        if (this.form == null) {
            this.form = new StatelessForm<>("form"); //$NON-NLS-1$
            this.form.add(getUserId());
            this.form.add(getPassword());
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    PasswordTextField getPassword() {
        if (this.password == null) {
            this.password = new PasswordTextField("password", Models.of(Empty.STRING)); //$NON-NLS-1$
        }
        return this.password;
    }

    @SuppressWarnings("serial")
    AjaxButton getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new IndicatingAjaxButton("submitter") { //$NON-NLS-1$
                @Override
                protected void onError(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    LoginPage.this.handler.onSubmitterError(pTarget);
                }

                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    LoginPage.this.handler.tryLogin(pTarget);
                }
            };
        }
        return this.submitter;
    }

    TextField<String> getUserId() {
        if (this.userId == null) {
            this.userId = new TextField<>("userId", Models.of(Empty.STRING)); //$NON-NLS-1$
            this.userId.setRequired(true);
        }
        return this.userId;
    }

    private Label getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = new Label("applicationName", Environment.getApplicationName()); //$NON-NLS-1$
        }
        return this.applicationName;
    }

    private class Handler implements Serializable {
        private static final long        serialVersionUID   = 6317461189636878176L;

        private final ErrorClassAppender errorClassAppender = new ErrorClassAppender();

        void onSubmitterError(final AjaxRequestTarget pTarget) {
            this.errorClassAppender.addErrorClass(getForm());
            pTarget.add(getUserId());
            pTarget.add(getPassword());
        }

        void tryLogin(final AjaxRequestTarget pTarget) {
            try {
                AppSession.get().login(getUserId().getModelObject(), getPassword().getModelObject());
                final StringValue url = getPageParameters().get("u"); //$NON-NLS-1$
                if (!url.isEmpty() && !url.isNull()) {
                    setResponsePage(new RedirectPage(url.toString()));
                } else {
                    setResponsePage(getApplication().getHomePage());
                }
            } catch (final FailAuthentication e) {
                error(getString("message.failLogin")); //$NON-NLS-1$
                this.errorClassAppender.addErrorClass(getForm());
                pTarget.add(getUserId());
                pTarget.add(getPassword());
            }
        }
    }
}
