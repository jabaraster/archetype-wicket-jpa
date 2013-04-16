#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.web.rest;

import jabara.jax_rs.JsonMessageBodyReaderWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * @author ${groupId}
 */
public class CoralRestApplication extends Application {

    /**
     * @see javax.ws.rs.core.Application${symbol_pound}getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { //
                JsonMessageBodyReaderWriter.class // JSONをきれいに返すにはこのクラスが必要.
                }));
    }

    /**
     * @see javax.ws.rs.core.Application${symbol_pound}getSingletons()
     */
    @Override
    public Set<Object> getSingletons() {
        return new HashSet<Object>(Arrays.asList(new Object[] { //
                new TimeResource() //
                }));
    }
}
