package jabara.aaa.service.impl;

import jabara.aaa.entity.EEmployee;
import jabara.aaa.service.IEmployeeService;
import jabara.general.ArgUtil;
import jabara.general.Sort;
import jabara.jpa.JpaDaoBase;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 
 */
public class EmployeeServiceImpl extends JpaDaoBase implements IEmployeeService {
    private static final long serialVersionUID = 5771084556720067384L;

    /**
     * @param pEntityManagerFactory DBアクセス用オブジェクト.
     */
    @Inject
    public EmployeeServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        super(pEntityManagerFactory);
    }

    /**
     * @see jabara.aaa.service.IEmployeeService#getAll(jabara.general.Sort)
     */
    @Override
    public List<EEmployee> getAll(final Sort pSort) {
        ArgUtil.checkNull(pSort, "pSort"); //$NON-NLS-1$
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EEmployee> query = builder.createQuery(EEmployee.class);
        final Root<EEmployee> root = query.from(EEmployee.class);
        query.orderBy(convertOrder(Arrays.asList(pSort), builder, root));
        return em.createQuery(query).getResultList();
    }

}
