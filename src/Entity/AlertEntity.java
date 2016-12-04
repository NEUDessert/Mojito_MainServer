package Entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by killeryuan on 2016/10/23.
 */
@Entity
@Table(name = "alert", schema = "mojito", catalog = "")
public class AlertEntity {
    private int alertId;
    private String phoneNumber;
    private String location;
    private String isCheck;
    private String alertLocX;
    private String alertLocY;
    private String name;
    private String type;
    private Integer deviceId;
    private Long alertTime;
    private String isRes;



    @Id
    @Column(name = "alert_id")
    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    @Basic
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertEntity that = (AlertEntity) o;

        if (alertId != that.alertId) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alertId;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "is_check")
    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    @Basic
    @Column(name = "alert_locX")
    public String getAlertLocX() {
        return alertLocX;
    }

    public void setAlertLocX(String alertLocX) {
        this.alertLocX = alertLocX;
    }

    @Basic
    @Column(name = "alert_locY")
    public String getAlertLocY() {
        return alertLocY;
    }

    public void setAlertLocY(String alertLocY) {
        this.alertLocY = alertLocY;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "device_id")
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "alert_time")
    public Long getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Long alertTime) {
        this.alertTime = alertTime;
    }

    @Basic
    @Column(name = "is_res")
    public String getIsRes() {
        return isRes;
    }

    public void setIsRes(String isRes) {
        this.isRes = isRes;
    }
}
