#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.service;

import ${package}.service.impl.TimeServiceImpl;

import java.util.Date;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(TimeServiceImpl.class)
public interface ITimeService {

    /**
     * @return このメソッドを呼び出した時点のシステム時刻.
     */
    Date getNow();
}
