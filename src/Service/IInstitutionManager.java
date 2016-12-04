package Service;

import Entity.AidPeopleEntity;
import Entity.AlertEntity;
import Entity.DeviceEntity;
import Entity.InstitutionEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by killeryuan on 2016/11/19.
 */
public interface IInstitutionManager {
    public String register(InstitutionEntity institutionEntity);
    public int instLogin(String institutionId, String password,  HttpServletRequest request);
    public int register(AidPeopleEntity aidPeopleEntity,  HttpServletRequest request);
    public int aidLogin( String aidPhoneNumber,String password,  HttpServletRequest request);
    public List<AidPeopleEntity> getAidAllPeople( HttpServletRequest request);
    public List<DeviceEntity> getDevice( HttpServletRequest request);
    public int addDevice(DeviceEntity deviceEntity, HttpServletRequest request);
    public List<AlertEntity> getAllAlert( HttpServletRequest request);
    public AidPeopleEntity getAidPeople( HttpServletRequest request);
    public int confirmPassword( String password, HttpServletRequest request);
    public int changePassword(String password, HttpServletRequest request);
    public int changeInstitution( int institutionId, HttpServletRequest request);
    public void test();
}
