#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.rest;

import ${package}.model.DI;
import ${package}.service.ITimeService;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("time")
public class TimeResource {

    private final ITimeService timeService;

    /**
     * 
     */
    public TimeResource() {
        this.timeService = DI.get(ITimeService.class);
    }

    /**
     * @return 現在時刻.
     */
    @Path("now")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Time getNow() {
        return new Time(this.timeService.getNow().toString());
    }

    /**
     * 
     * @author ${groupId}
     */
    public static class Time implements Serializable {
        private static final long serialVersionUID = -3531751856339387124L;

        private String            now;

        /**
         * 
         */
        public Time() {
            //
        }

        /**
         * @param pNow 現在時刻
         */
        public Time(final String pNow) {
            this.now = pNow;
        }

        /**
         * @return the now
         */
        public String getNow() {
            return this.now;
        }

        /**
         * @param pNow the now to set
         */
        public void setNow(final String pNow) {
            this.now = pNow;
        }
    }
}
