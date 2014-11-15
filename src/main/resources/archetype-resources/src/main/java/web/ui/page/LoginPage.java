#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui.page;

import ${package}.model.FailAuthentication;
import ${package}.web.ui.component.AppPanel;

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
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.StringValue;

/**
 * 
 */
@SuppressWarnings("synthetic-access")
public class LoginPage extends WebPageBase {

    private final Handler handler = new Handler();

    /**
     * @see ${package}.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(LoginPage.class));
        pResponse.render(JavaScriptHeaderItem.forScript(JavaScriptUtil.getFocusScript(getPassword()), null));
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#createRightPanel(java.lang.String)
     */
    @Override
    protected Panel createRightPanel(final String pId) {
        return new P(pId);
    }

    /**
     * @see ${package}.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly(getString("pageTitle")); //$NON-NLS-1$
    }

    private Form<?> getForm() {
        return ((P) getRightPanel()).getForm();
    }

    private PasswordTextField getPassword() {
        return ((P) getRightPanel()).getPassword();
    }

    private TextField<String> getUserId() {
        return ((P) getRightPanel()).getUserId();
    }

    private class Handler implements Serializable {

        private final ErrorClassAppender errorClassAppender = new ErrorClassAppender(Models.readOnly("error")); //$NON-NLS-1$

        void onSubmitterError(final AjaxRequestTarget pTarget) {
            this.errorClassAppender.addErrorClass(getForm());
            addInputComponents(pTarget);
        }

        void tryLogin(final AjaxRequestTarget pTarget) {
            try {
                getSession().login(getUserId().getModelObject(), getPassword().getModelObject());
                final StringValue url = getPageParameters().get("u"); //$NON-NLS-1$
                if (!url.isEmpty() && !url.isNull()) {
                    setResponsePage(new RedirectPage(url.toString()));
                } else {
                    setResponsePage(getApplication().getHomePage());
                }
            } catch (final FailAuthentication e) {
                error(getString("message.failLogin")); //$NON-NLS-1$
                this.errorClassAppender.addErrorClass(getForm());
                addInputComponents(pTarget);
                pTarget.appendJavaScript(JavaScriptUtil.getFocusScript(getPassword()));
            }
        }

        private void addInputComponents(final AjaxRequestTarget pTarget) {
            pTarget.add(getPassword());
        }
    }

    private class P extends AppPanel {

        private StatelessForm<?>  form;
        private TextField<String> UserId;
        private PasswordTextField password;
        private AjaxButton        submitter;

        public P(final String pId) {
            super(pId);
            this.add(getForm());
        }

        private StatelessForm<?> getForm() {
            if (this.form == null) {
                this.form = new StatelessForm<>(id());
                this.form.add(getUserId());
                this.form.add(getPassword());
                this.form.add(getSubmitter());
            }
            return this.form;
        }

        private PasswordTextField getPassword() {
            if (this.password == null) {
                this.password = new PasswordTextField(id(), Models.of(Empty.STRING));
            }
            return this.password;
        }

        @SuppressWarnings("serial")
        private AjaxButton getSubmitter() {
            if (this.submitter == null) {
                this.submitter = new IndicatingAjaxButton(id()) {
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
            if (this.UserId == null) {
                this.UserId = new TextField<String>(id(), Models.of(Empty.STRING), String.class);
            }
            return this.UserId;
        }
    }
}
