#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.ui;

import ${package}.model.FailAuthentication;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * 
 */
public class AppSession extends WebSession {
    private static final long   serialVersionUID = -5522467353190211133L;

    private final AtomicBoolean authenticated    = new AtomicBoolean(false);

    /**
     * @param pRequest
     */
    public AppSession(final Request pRequest) {
        super(pRequest);
    }

    /**
     * @see org.apache.wicket.protocol.http.WebSession${symbol_pound}invalidate()
     */
    @Override
    public void invalidate() {
        super.invalidate();
        invalidateHttpSession();
    }

    /**
     * @see org.apache.wicket.Session${symbol_pound}invalidateNow()
     */
    @Override
    public void invalidateNow() {
        super.invalidateNow();
        invalidateHttpSession();
    }

    /**
     * @return 認証済みあればtrue.
     */
    public boolean isAuthenticated() {
        return this.authenticated.get();
    }

    /**
     * @param pUser
     * @param pPassword
     * @throws FailAuthentication 認証NGの場合にスローして下さい.
     */
    public void login(final String pUser, final String pPassword) throws FailAuthentication {

        // TODO 実際のログイン処理を実装して下さい.

        if ("ng".equals(pUser)) { //${symbol_dollar}NON-NLS-1${symbol_dollar}
            throw FailAuthentication.INSTANCE;
        }
        this.authenticated.set(true);
    }

    /**
     * @return 現在のコンテキスト中の{@link AppSession}.
     */
    public static AppSession get() {
        return (AppSession) Session.get();
    }

    private static void invalidateHttpSession() {
        // Memcahcedによるセッション管理を行なっていると、Session.get()ではセッションが破棄されない.
        ((HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest()).getSession().invalidate();
    }
}
