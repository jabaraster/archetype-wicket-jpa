/**
 * 
 */
package jabara.aaa.entity;

import jabara.jpa.entity.EntityBase;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jabaraster
 */
public class EEmployee extends EntityBase<EEmployee> {
    private static final long serialVersionUID    = 2435541265149393556L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_CODE = 20;
    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_NAME = 60;

    /**
     * 
     */
    @Column(nullable = false, length = MAX_CHAR_COUNT_CODE)
    @Size(min = 1, max = MAX_CHAR_COUNT_CODE)
    @NotNull
    protected String          code;

    /**
     * 
     */
    @Column(nullable = false, length = MAX_CHAR_COUNT_NAME * 3)
    @Size(min = 1, max = MAX_CHAR_COUNT_NAME)
    @NotNull
    protected String          name;

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param pCode the code to set
     */
    public void setCode(final String pCode) {
        this.code = pCode;
    }

    /**
     * @param pName the name to set
     */
    public void setName(final String pName) {
        this.name = pName;
    }
}
