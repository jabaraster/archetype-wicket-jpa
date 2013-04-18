#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import jabara.jetty.ServerStarter;

import javax.naming.NamingException;

import org.eclipse.jetty.plus.jndi.Resource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * 
 */
public class WebStarter {
    /**
     * @throws NamingException
     */
    @SuppressWarnings({ "unused", "nls" })
    public static void initializeDataSource() throws NamingException {
        final PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("${artifactId}");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        new Resource("jdbc/${artifactId}", dataSource);
    }

    /**
     * @param pArgs 起動引数.
     * @throws NamingException
     */
    public static void main(final String[] pArgs) throws NamingException {
        initializeDataSource();
        final ServerStarter server = new ServerStarter();
        server.start();
        // new MemcachedSessionServerStarter().start();
    }
}
