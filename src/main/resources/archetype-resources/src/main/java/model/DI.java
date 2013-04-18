#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import jabara.general.ArgUtil;
import jabara.jpa_guice.SinglePersistenceUnitJpaModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 *
 */
public final class DI {

    private static Injector _injector = createInjector();

    private DI() {
    }

    /**
     * @param <T>
     * @param pType
     * @return T型のオブジェクト.
     */
    public static <T> T get(final Class<T> pType) {
        ArgUtil.checkNull(pType, "pType"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        return _injector.getInstance(pType);
    }

    /**
     * @return Guiceの{@link Injector}.
     */
    public static Injector getInjector() {
        return _injector;
    }

    private static Injector createInjector() {
        final SinglePersistenceUnitJpaModule jpaModule = new SinglePersistenceUnitJpaModule("${artifactId}"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        return Guice.createInjector( //
                jpaModule //
                );
    }
}
