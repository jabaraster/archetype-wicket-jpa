package ${package}.service;

import ${package}.entity.EEmployee;
import ${package}.service.impl.EmployeeServiceImpl;
import jabara.general.Sort;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * 
 */
@ImplementedBy(EmployeeServiceImpl.class)
public interface IEmployeeService {

    /**
     * @param pSort ソート条件.
     * @return 全件.
     */
    List<EEmployee> getAll(Sort pSort);
}
