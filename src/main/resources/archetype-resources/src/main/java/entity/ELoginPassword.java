/**
 * 
 */
package ${package}.entity;

import jabara.general.Empty;
import jabara.general.ExceptionUtil;
import jabara.jpa.entity.EntityBase;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author jabaraster
 */
@Entity
public class ELoginPassword extends EntityBase<ELoginPassword> {

    private static final Charset ENCODING         = StandardCharsets.UTF_8;

    /**
     * 
     */
    @Column(nullable = false)
    protected byte[]             password         = {};
    /**
     * 
     */
    @OneToOne
    @JoinColumn(nullable = false)
    protected EUser              user;

    /**
     * @param pPassword -
     * @return -
     */
    public boolean equal(final String pPassword) {
        return Arrays.equals(this.password, digest(pPassword));
    }

    /**
     * @return userを返す.
     */
    public EUser getUser() {
        return this.user;
    }

    /**
     * @param pPassword -
     */
    public void setPassword(final String pPassword) {
        this.password = digest(pPassword);
    }

    /**
     * @param pUser userを設定.
     */
    public void setUser(final EUser pUser) {
        this.user = pUser;
    }

    private static byte[] digest(final String pValue) {
        final byte[] value = pValue == null ? Empty.BYTE_ARRAY : pValue.getBytes(ENCODING);
        return getMessageDigest().digest(value);
    }

    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5"); //$NON-NLS-1$
        } catch (final NoSuchAlgorithmException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }
}
