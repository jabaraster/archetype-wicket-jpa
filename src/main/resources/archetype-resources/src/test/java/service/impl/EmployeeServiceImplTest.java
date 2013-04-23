/**
 * 
 */
package ${package}.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import jabara.general.Sort;
import jabara.jpa.entity.EntityBase_;
import jabara.jpa_guice.ThreadLocalEntityManagerFactoryHandler;
import ${package}.WebStarter;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author jabaraster
 */
@RunWith(Enclosed.class)
public class EmployeeServiceImplTest {

    /**
     * @author jabaraster
     */
    public static class TableIsEmpty {
        EmployeeServiceImpl sut;

        /**
         * 
         */
        @SuppressWarnings("boxing")
        @Test
        public void getAllの結果が0件() {
            final int actual = this.sut.getAll(Sort.asc(EntityBase_.id.getName())).size();
            assertThat(actual, is(0));
        }

        /**
         * @throws NamingException
         */
        @Before
        public void setUp() throws NamingException {
            WebStarter.initializeDataSource();

            final EntityManagerFactory emf = ThreadLocalEntityManagerFactoryHandler.wrap(Persistence.createEntityManagerFactory("${artifactId}")); //$NON-NLS-1$
            this.sut = new EmployeeServiceImpl(emf);
        }
    }
}
