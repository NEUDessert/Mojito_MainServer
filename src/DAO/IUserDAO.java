package DAO;

import Entity.*;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by killeryuan on 2016/10/19.
 */
public interface IUserDAO {

    public int login(UserEntity userEntity);

    public int register(UserEntity userEntity);

    public int finishedMedical_record(MedicalRecordEntity medicalRecordEntity);

    public int contactsLogin(String phoneNumber, String custodyCode);

    public int addAlert(AlertEntity alertEntity);

    public int echoAED(AlertEntity alertEntity);

    public int setAlias(JpushAliasEntity jpushAliasEntity);

    public List<MedicalRecordEntity> getCritical(String phoneNumber);

    public int getRealName(UserEntity userEntity);

    public List<DeviceEntity> getAllAED();



    public int register(InstitutionEntity institutionEntity);
    public int instLogin(String institutionId, String password);
    public int register(AidPeopleEntity aidPeopleEntity);
    public int aidLogin(String aidPhoneNumber, String password);

    public List<AidPeopleEntity> getAidPeople(Integer institutionId);
    public List<DeviceEntity> getDevice(Integer institutionId);
    public int addDevice(DeviceEntity deviceEntity);
    public List<AlertEntity> getAllAlert(Integer institutionId);
    public AidPeopleEntity getAidPeople(String aidPhoneNumber);
    public int confirmPassword(String aidPhoneNumber, String password);
    public int changePassword(String aidPhoneNumber,String password);
    public int changeInstitution(String aidPhoneNumber, int institutionId);

    public boolean create(Object Entity, Session session, List object);
    public String getBasicInfo(String phoneNumber);

    public List<AlertEntity> getAllAlert(String phoneNumber);

    public int updateLocation(UserEntity userEntity);

    public int updateLocation(String locX, String locY, String phoneNumber);

    public double[] getLocation(String phoneNumber);

    public int getHeartRate(String phoneNumber);
    public int updateHeartRate(String phoneNumber, String heartRate);

    public double[] userGetLocation(String phoneNumber);

   // public boolean create(Object Entity);



}
