#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import jabara.general.Empty;
import jabara.wicket.ComponentCssHeaderItem;
import jabara.wicket.ErrorClassAppender;
import jabara.wicket.JavaScriptUtil;
import jabara.wicket.Models;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import ${package}.Environment;
import ${package}.entity.EUser;
import ${package}.model.FailAuthentication;
import ${package}.web.ui.AppSession;

/**
 * 
 */
@SuppressWarnings("synthetic-access")
public class LoginPage extends WebPageBase {
    private static final long serialVersionUID = 1925170327965147328L;

    private final Handler     handler          = new Handler();

    private Label             defaultAdministratorUserId;
    private Label             defaultAdministratorPassword;

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
     * @see ${package}.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(LoginPage.class));
        pResponse.render(OnDomReadyHeaderItem.forScript(JavaScriptUtil.getFocusScript(getUserId())));
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly(getString("pageTitle")); //$NON-NLS-1$
    }

    private Label getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = new Label("applicationName", Environment.getApplicationName()); //$NON-NLS-1$
        }
        return this.applicationName;
    }

    private Label getDefaultAdministratorPassword() {
        if (this.defaultAdministratorPassword == null) {
            this.defaultAdministratorPassword = new Label("defaultAdministratorPassword", EUser.DEFAULT_ADMINISTRATOR_PASSWORD); //$NON-NLS-1$
        }
        return this.defaultAdministratorPassword;
    }

    private Label getDefaultAdministratorUserId() {
        if (this.defaultAdministratorUserId == null) {
            this.defaultAdministratorUserId = new Label("defaultAdministratorUserId", EUser.DEFAULT_ADMINISTRATOR_USER_ID); //$NON-NLS-1$
        }
        return this.defaultAdministratorUserId;
    }

    private StatelessForm<?> getForm() {
        if (this.form == null) {
            this.form = new StatelessForm<Object>("form"); //$NON-NLS-1$
            this.form.add(getUserId());
            this.form.add(getPassword());
            this.form.add(getSubmitter());

            // TODO $B:o=|$9$k$3$H(B.
            this.form.add(getDefaultAdministratorUserId());
            this.form.add(getDefaultAdministratorPassword());
        }
        return this.form;
    }

    private PasswordTextField getPassword() {
        if (this.password == null) {
            this.password = new PasswordTextField("password", Models.of(Empty.STRING)); //$NON-NLS-1$
        }
        return this.password;
    }

    @SuppressWarnings("serial")
    private Button getSubmitter() {
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

    private TextField<String> getUserId() {
        if (this.userId == null) {
            this.userId = new TextField<String>("userId", Models.of(Empty.STRING)); //$NON-NLS-1$
            this.userId.setRequired(true);
        }
        return this.userId;
    }

    private class Handler implements Serializable {
        private static final long        serialVersionUID   = 6317461189636878176L;

        private final ErrorClassAppender errorClassAppender = new ErrorClassAppender(Models.readOnly("error")); //$NON-NLS-1$

        void onSubmitterError(final AjaxRequestTarget pTarget) {
            this.errorClassAppender.addErrorClass(getForm());
            addInputComponents(pTarget);
        }

        void tryLogin(final AjaxRequestTarget pTarget) {
            try {
                AppSession.get().login(getUserId().getModelObject(), getPassword().getModelObject());
                setResponsePage(getApplication().getHomePage());
            } catch (final FailAuthentication e) {
                error(getString("message.failLogin")); //$NON-NLS-1$
                this.errorClassAppender.addErrorClass(getForm());
                addInputComponents(pTarget);
            }
        }

        private void addInputComponents(final AjaxRequestTarget pTarget) {
            pTarget.add(getUserId());
            pTarget.add(getPassword());
        }
    }
}
