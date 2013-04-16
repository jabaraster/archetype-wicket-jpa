#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import jabara.jetty.ServerStarter;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.eclipse.jetty.plus.jndi.Resource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * 
 */

/**
 * 
 * @author ${groupId}
 */
public class CoralWebStarter {
    /**
     * @throws NamingException
     */
    @SuppressWarnings({ "unused", "nls" })
    public static void initializeDataSource() throws NamingException {
        final PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("coral");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        new Resource("jdbc/Coral", dataSource);
    }

    /**
     * @param pArgs 起動引数.
     * @throws NamingException
     * @throws SQLException
     */
    public static void main(final String[] pArgs) throws NamingException, SQLException {
        initializeDataSource();
        final ServerStarter server = new ServerStarter();
        server.start();
    }
}
