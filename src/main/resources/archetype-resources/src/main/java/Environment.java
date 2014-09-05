#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import jabara.general.EnvironmentUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;

/**
 * @author jabaraster
 */
public final class Environment {
    /**
     * 
     */
    public static final String   APPLICATION_NAME                         = "${artifactId}";                                                                 //$NON-NLS-1$

    private static final String  PARAM_PREFIX                             = APPLICATION_NAME + "_";                                                        //$NON-NLS-1$

    /**
     * 
     */
    public static final String   PARAM_HIBERNATE_HBM2DDL_AUTO             = PARAM_PREFIX + "hibernateHbm2ddlAuto";                                         //$NON-NLS-1$
    /**
     * 
     */
    public static final String   DEFAULT_HIBERNATE_HBM2DDL_AUTO           = "none";                                                                        //$NON-NLS-1$
    /**
     * 
     */
    public static final String   PARAM_H2_DATABASE_PATH                   = PARAM_PREFIX + "h2DatabasePath";                                               //$NON-NLS-1$
    /**
     * デフォルト値はカレントディレクトリ以下のtarget/db/<アプリケーション名>です. <br>
     * 即ち、以下のコードでパスを組み立てています. <br>
     * <code>
     * Paths.get(".").toAbsolutePath().normalize() + "/target/db/" + Environment.APPLICATION_NAME
     * </code>
     */
    public static final String   DEFAULT_H2_DATABASE_PATH                 = Paths.get(".").toAbsolutePath().normalize() + "/target/db/" + APPLICATION_NAME; //$NON-NLS-1$ //$NON-NLS-2$
    /**
     * 
     */
    public static final String   PARAM_DB_NAME_SUFFIX                     = PARAM_PREFIX + "dbNameSuffix";                                                 //$NON-NLS-1$
    /**
     * 
     */
    public static final String   PARAM_HIBERNATE_CONNECTION_POOL_MIN_SIZE = PARAM_PREFIX + "hibernateConnectionPoolMinSize";                               //$NON-NLS-1$
    /**
     * 
     */
    public static final int      DEFAULT_CONNECTION_POOL_MIN_SIZE         = 10;
    /**
     * 
     */
    public static final String   PARAM_HIBERNATE_CONNECTION_POOL_MAX_SIZE = PARAM_PREFIX + "hibernateConnectionPoolMaxSize";                               //$NON-NLS-1$
    /**
     * 
     */
    public static final int      DEFAULT_CONNECTION_POOL_MAX_SIZE         = 40;
    /**
     * 
     */
    public static final String   PARAM_GZIP_COMPRESS_ENABLED              = PARAM_PREFIX + "gzipCompressEnabled";                                          //$NON-NLS-1$
    /**
     * 
     */
    public static final boolean DEFAULT_GZIP_COMPRESS_ENABLED            = false;
    /**
     * 
     */
    public static final String   PARAM_GZIP_COMPRESS_TARGET_MIN_SIZE      = PARAM_PREFIX + "gzipCompressTargetMinSize";                                    //$NON-NLS-1$
    /**
     * GZIP対象となるContent-Lengthの最小値のデフォルト.
     */
    public static final int      DEFAULT_GZIP_COMPRESS_TARGET_MIN_SIZE    = 1024 * 50 /* 50KB */;

    private Environment() {
        // 処理なし
    }

    /**
     * @param pType -
     * @throws IllegalAccessException -
     */
    public static void dumpConstantDeclarations(final Class<?> pType) throws IllegalAccessException {
        for (final Field field : pType.getFields()) {
            final int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                continue;
            }
            if (!Modifier.isFinal(modifiers)) {
                continue;
            }
            System.out.println(field.getName() + "\t" + field.getType().getName() + "\t" + field.get(null)); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * @return アプリケーション名.
     * @see #APPLICATION_NAME
     */
    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    /**
     * @return DB接続プールの最大サイズ.
     * @see #PARAM_HIBERNATE_CONNECTION_POOL_MAX_SIZE
     * @see #DEFAULT_CONNECTION_POOL_MAX_SIZE
     */
    @SuppressWarnings("boxing")
    public static int getConnectionPoolMaxSize() {
        return Integer.parseInt(EnvironmentUtil.getString(PARAM_HIBERNATE_CONNECTION_POOL_MAX_SIZE, DEFAULT_CONNECTION_POOL_MAX_SIZE));
    }

    /**
     * @return DB接続プールの最少サイズ.
     * @see #PARAM_HIBERNATE_CONNECTION_POOL_MIN_SIZE
     * @see #DEFAULT_CONNECTION_POOL_MIN_SIZE
     */
    @SuppressWarnings("boxing")
    public static int getConnectionPoolMinSize() {
        return Integer.parseInt(EnvironmentUtil.getString(PARAM_HIBERNATE_CONNECTION_POOL_MIN_SIZE, DEFAULT_CONNECTION_POOL_MIN_SIZE));
    }

    /**
     * @return -
     * @see #PARAM_DB_NAME_SUFFIX
     */
    public static String getDbNameSuffix() {
        final String value = EnvironmentUtil.getString(PARAM_DB_NAME_SUFFIX, null);
        return value == null ? "" : "_" + value; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @return GZIP圧縮の対象となるコンテンツの最少サイズ. 単位はバイト. <br>
     *         この数値以上の長さのコンテンツがGZIP圧縮対象となります. <br>
     *         このメソッドは{@link #isGzipFilterEnabled()}がtrueを返す状況でないと意味を持ちません. <br>
     * @see #PARAM_GZIP_COMPRESS_TARGET_MIN_SIZE
     * @see #DEFAULT_GZIP_COMPRESS_TARGET_MIN_SIZE
     * @see #isGzipFilterEnabled()
     */
    @SuppressWarnings("boxing")
    public static int getGzipCompressTargetMinSize() {
        return Integer.parseInt(EnvironmentUtil.getString(PARAM_GZIP_COMPRESS_TARGET_MIN_SIZE, DEFAULT_GZIP_COMPRESS_TARGET_MIN_SIZE));
    }

    /**
     * @return -
     * @see #PARAM_H2_DATABASE_PATH
     * @see #DEFAULT_H2_DATABASE_PATH
     */
    public static String getH2DatabasePath() {
        return EnvironmentUtil.getString(PARAM_H2_DATABASE_PATH, DEFAULT_H2_DATABASE_PATH);
    }

    /**
     * @return -
     * @see #PARAM_HIBERNATE_HBM2DDL_AUTO
     * @see #DEFAULT_HIBERNATE_HBM2DDL_AUTO
     */
    public static String getHbm2DdlAuto() {
        return EnvironmentUtil.getString(PARAM_HIBERNATE_HBM2DDL_AUTO, DEFAULT_HIBERNATE_HBM2DDL_AUTO);
    }

    /**
     * @return -
     * @see #PARAM_GZIP_COMPRESS_ENABLED
     * @see #DEFAULT_GZIP_COMPRESS_ENABLED
     */
    @SuppressWarnings("boxing")
    public static boolean isGzipFilterEnabled() {
        return Boolean.parseBoolean(EnvironmentUtil.getString(PARAM_GZIP_COMPRESS_ENABLED, DEFAULT_GZIP_COMPRESS_ENABLED));
    }

    /**
     * @param pArgs 起動引数.
     * @throws IllegalAccessException -
     */
    public static void main(final String[] pArgs) throws IllegalAccessException {
        dumpConstantDeclarations(Environment.class);
    }
}
