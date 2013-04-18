#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

/**
 *
 */
public final class Environment {

    /**
     * @return アプリケーション名.
     */
    public static String getApplicationName() {
        return "${artifactId}"; //${symbol_dollar}NON-NLS-1${symbol_dollar}
    }

}
