package Entity;

import javax.persistence.*;

/**
 * Created by killeryuan on 2016/11/19.
 */
@Entity
@Table(name = "aid_people", schema = "mojito", catalog = "")
public class AidPeopleEntity {
    private int peopleAidId;
    private int institutionId;
    private String aidNumber;
    private String password;
    private String name;
    private String state;

//    public void setInstitutionId(Integer institutionId) {
//        this.institutionId = institutionId;
//    }

    @Basic
    @Column(name = "aid_number")
    public String getAidPhoneNumber() {
        return aidNumber;
    }

    public void setAidPhoneNumber(String aidNumber) {
        this.aidNumber = aidNumber;
    }


//    public String getAidPhoneNumber() {
//        return aidNumber;
//    }
//
//    public void setAidPhoneNumber(String aidNumber) {
//        this.aidNumber = aidNumber;
//    }

    @Id
    @Column(name = "people_aid_id")
    public int getPeopleAidId() {
        return peopleAidId;
    }

    public void setPeopleAidId(int peopleAidId) {
        this.peopleAidId = peopleAidId;
    }

    @Basic
    @Column(name = "institution_id")
    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }


    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AidPeopleEntity that = (AidPeopleEntity) o;

        if (peopleAidId != that.peopleAidId) return false;
        if (institutionId != that.institutionId) return false;
        if (aidNumber != null ? !aidNumber.equals(that.aidNumber) : that.aidNumber != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = peopleAidId;
        result = 31 * result + institutionId;
        result = 31 * result + (aidNumber != null ? aidNumber.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
