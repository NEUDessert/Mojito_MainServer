package Entity;

/**
 * Created by killeryuan on 2016/10/24.
 */
public class AEDmsg {
    private String haveMsg;
    private String phoneNumber;
    private double distance;
    private int deviceId;

    public AEDmsg(String haveMsg, String phoneNumber, double distance, int deviceId) {
        this.haveMsg = haveMsg;
        this.phoneNumber = phoneNumber;
        this.distance = distance;
        this.deviceId = deviceId;
    }

    public String getHaveMsg() {
        return haveMsg;
    }

    public void setHaveMsg(String haveMsg) {
        this.haveMsg = haveMsg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
