package ${package}.web.rest;

import ${package}.entity.EEmployee;
import ${package}.entity.EEmployee_;
import ${package}.service.IEmployeeService;
import jabara.general.Sort;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("employee")
public class EmployeeResource {

    private final IEmployeeService employeeService;

    /**
     * @param pEmployeeService
     */
    @Inject
    public EmployeeResource(final IEmployeeService pEmployeeService) {
        this.employeeService = pEmployeeService;
    }

    /**
     * @return 従業員情報全件.
     */
    @Path("all")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<EEmployee> getAll() {
        return this.employeeService.getAll(Sort.asc(EEmployee_.code.getName()));
    }
}
