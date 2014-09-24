#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.container;

import ${package}.Environment;

/**
 * @author jabaraster
 */
public final class WebPaths {

    private static final String WILD_CARD                  = "*";                  //$NON-NLS-1$

    /**
     * 
     */
    public static final String  PATH_ROOT                  = "/";                  //$NON-NLS-1$

    /**
     * 
     */
    public static final String  URL_PATTERN_GZIP_FILTER    = PATH_ROOT + WILD_CARD;
    /**
     * 
     */
    public static final String  URL_PATTERN_JAX_RS         = "/api/" + WILD_CARD; //$NON-NLS-1$

    /**
     * 
     */
    public static final String  URL_PATTERN_ROUTING_FILTER = PATH_ROOT + WILD_CARD;
    /**
     * 
     */
    public static final String  PATH_UI                    = "/ui/";               //$NON-NLS-1$
    /**
     * 
     */
    public static final String  URL_PATTERN_WICKET         = PATH_UI + WILD_CARD;

    private WebPaths() {
        // 処理なし
    }

    /**
     * @param pArgs 起動引数.
     * @throws IllegalAccessException -
     */
    public static void main(final String[] pArgs) throws IllegalAccessException {
        Environment.dumpConstantDeclarations(WebPaths.class);
    }
}
