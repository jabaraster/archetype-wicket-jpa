#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.rest;

import jabara.general.Sort;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ${package}.entity.EUser;
import ${package}.entity.EUser_;
import ${package}.service.IUserService;

/**
 *
 */
@Path("user")
public class UserResource {

    private final IUserService userService;

    /**
     * @param pUserService -
     */
    @Inject
    public UserResource(final IUserService pUserService) {
        this.userService = pUserService;
    }

    /**
     * @return ユーザ情報全件.
     */
    @Path("all")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<EUser> getAll() {
        return this.userService.getAll(Sort.asc(EUser_.userId.getName()));
    }
}
