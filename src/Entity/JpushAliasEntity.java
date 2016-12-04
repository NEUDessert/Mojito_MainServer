package Entity;

import javax.persistence.*;

/**
 * Created by killeryuan on 2016/10/23.
 */
@Entity
@Table(name = "jpush_alias", schema = "mojito", catalog = "")
public class JpushAliasEntity {
    private int jpushId;
    private String alias;
    private String phoneNumber;

    @Id
    @Column(name = "jpush_id")
    public int getJpushId() {
        return jpushId;
    }

    public void setJpushId(int jpushId) {
        this.jpushId = jpushId;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpushAliasEntity that = (JpushAliasEntity) o;

        if (jpushId != that.jpushId) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jpushId;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
