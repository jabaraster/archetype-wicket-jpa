#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import jabara.jetty_memcached.MemcachedSessionServerStarter;

/**
 * 
 */
public class WebStarter {
    /**
     * @param pArgs 起動引数.
     */
    public static void main(final String[] pArgs) {
        // final ServerStarter server = new ServerStarter();
        // server.start();
        // MemcachedをHttpSessionのストアとして使う場合.
        // 主にHerokuでの運用を意識.
        final MemcachedSessionServerStarter server = new MemcachedSessionServerStarter();
        // server.setWebPort(8082);
        server.start();
    }
}
