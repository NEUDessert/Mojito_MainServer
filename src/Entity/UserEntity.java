package Entity;

import javax.persistence.*;

/**
 * Created by killeryuan on 2016/10/22.
 */
@Entity
@Table(name = "user", schema = "mojito", catalog = "")
public class UserEntity {
    private String phoneNumber;
    private String name;
    private String email;
    private String password;
    private String custodyCode;
    private String locX;
    private String locY;

    @Id
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        UserEntity that = (UserEntity) o;

        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "custodyCode")
    public String getCustodyCode() {
        return custodyCode;
    }

    public void setCustodyCode(String custodyCode) {
        this.custodyCode = custodyCode;
    }

    @Basic
    @Column(name = "locX")
    public String getLocX() {
        return locX;
    }

    public void setLocX(String locX) {
        this.locX = locX;
    }

    @Basic
    @Column(name = "locY")
    public String getLocY() {
        return locY;
    }

    public void setLocY(String locY) {
        this.locY = locY;
    }
}
