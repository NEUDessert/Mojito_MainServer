package Entity;

import javax.persistence.*;

/**
 * Created by killeryuan on 2016/10/24.
 */
@Entity
@Table(name = "device", schema = "mojito", catalog = "")
public class DeviceEntity {
    private int deviceId;
    private String locX;
    private String locY;
    private Integer institutionId;
    private String isOk;
    private String location;

    @Id
    @Column(name = "deviceId")
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        if (deviceId != that.deviceId) return false;
        if (locX != null ? !locX.equals(that.locX) : that.locX != null) return false;
        if (locY != null ? !locY.equals(that.locY) : that.locY != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceId;
        result = 31 * result + (locX != null ? locX.hashCode() : 0);
        result = 31 * result + (locY != null ? locY.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "institution_id")
    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    @Basic
    @Column(name = "is_ok")
    public String getIsOk() {
        return isOk;
    }

    public void setIsOk(String isOk) {
        this.isOk = isOk;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
