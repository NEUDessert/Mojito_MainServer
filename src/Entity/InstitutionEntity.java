package Entity;

import javax.persistence.*;

/**
 * Created by killeryuan on 2016/11/19.
 */
@Entity
@Table(name = "institution", schema = "mojito", catalog = "")
public class InstitutionEntity {
    private int institutionId;
    private String institutionName;
    private String institutionLocX;
    private String institutionLocY;
    private String managerPhone;
    private String password;

    @Id
    @Column(name = "institution_id")
    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    @Basic
    @Column(name = "institution_name")
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Basic
    @Column(name = "institution_locX")
    public String getInstitutionLocX() {
        return institutionLocX;
    }

    public void setInstitutionLocX(String institutionLocX) {
        this.institutionLocX = institutionLocX;
    }

    @Basic
    @Column(name = "institution_locY")
    public String getInstitutionLocY() {
        return institutionLocY;
    }

    public void setInstitutionLocY(String institutionLocY) {
        this.institutionLocY = institutionLocY;
    }

    @Basic
    @Column(name = "managerPhone")
    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstitutionEntity that = (InstitutionEntity) o;

        if (institutionId != that.institutionId) return false;
        if (institutionName != null ? !institutionName.equals(that.institutionName) : that.institutionName != null)
            return false;
        if (institutionLocX != null ? !institutionLocX.equals(that.institutionLocX) : that.institutionLocX != null)
            return false;
        if (institutionLocY != null ? !institutionLocY.equals(that.institutionLocY) : that.institutionLocY != null)
            return false;
        if (managerPhone != null ? !managerPhone.equals(that.managerPhone) : that.managerPhone != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = institutionId;
        result = 31 * result + (institutionName != null ? institutionName.hashCode() : 0);
        result = 31 * result + (institutionLocX != null ? institutionLocX.hashCode() : 0);
        result = 31 * result + (institutionLocY != null ? institutionLocY.hashCode() : 0);
        result = 31 * result + (managerPhone != null ? managerPhone.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
