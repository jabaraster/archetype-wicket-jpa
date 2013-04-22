#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import jabara.jetty.ServerStarter;

import javax.naming.NamingException;

import org.eclipse.jetty.plus.jndi.Resource;
import org.h2.jdbcx.JdbcDataSource;

/**
 * 
 */
public class WebStarter {
    /**
     * @throws NamingException
     */
    @SuppressWarnings({ "unused", "nls" })
    public static void initializeDataSource() throws NamingException {
        // H2Databaseの場合
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:target/db/db");
        dataSource.setUser("sa");

        // PostgreSQLの場合
        // final PGPoolingDataSource dataSource = new PGPoolingDataSource();
        // dataSource.setDatabaseName("Aac");
        // dataSource.setUser("postgres");
        // dataSource.setPassword("postgres");
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
