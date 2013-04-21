/**
 * 
 */
package jabara.sandbox.aaa.web.rest;

import jabara.general.Sort;
import jabara.sandbox.aaa.entity.EEmployee;
import jabara.sandbox.aaa.entity.EEmployee_;
import jabara.sandbox.aaa.model.DI;
import jabara.sandbox.aaa.service.IEmployeeService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author jabaraster
 */
@Path("employee")
public class EmployeeResource {

    private final IEmployeeService employeeService;

    /**
     * 
     */
    public EmployeeResource() {
        this.employeeService = DI.get(IEmployeeService.class);
    }

    /**
     * @return 従業員情報全件.
     */
    @Path("index")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<EEmployee> getAll() {
        return this.employeeService.getAll(Sort.asc(EEmployee_.code.getName()));
    }
}
