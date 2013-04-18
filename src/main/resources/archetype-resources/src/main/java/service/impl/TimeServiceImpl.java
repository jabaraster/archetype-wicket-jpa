#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.service.impl;

import jabara.general.ArgUtil;
import ${package}.service.ITimeService;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * @author jabaraster
 */
public class TimeServiceImpl implements ITimeService {

    private final EntityManagerFactory entityManagerFactory; // 使わないが、サンプルのために宣言する.

    /**
     * @param pEntityManagerFactory
     */
    @Inject
    public TimeServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        ArgUtil.checkNull(pEntityManagerFactory, "pEntityManagerFactory"); //${symbol_dollar}NON-NLS-1${symbol_dollar}
        this.entityManagerFactory = pEntityManagerFactory;
    }

    /**
     * @see ${package}.service.ITimeService${symbol_pound}getNow()
     */
    @Override
    public Date getNow() {
        return Calendar.getInstance().getTime();
    }
}
