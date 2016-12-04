package Service;

import Entity.AlertEntity;
import Entity.JpushAliasEntity;
import Entity.MedicalRecordEntity;
import Entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Created by killeryuan on 2016/10/19.
 */
public interface IUserManager {
    public int login(HttpServletRequest request, UserEntity userEntity);

    public int register(HttpServletRequest request, UserEntity userEntity);

    public int finishedMedical_record(HttpServletRequest request, MedicalRecordEntity medicalRecordEntity);

    public int contactsLogin(HttpServletRequest request, String phoneNumber, String custodyCode);

    public int addAlert(HttpServletRequest request, AlertEntity alertEntity);

    public int echoAED(AlertEntity alertEntity);

    public int setAlias(HttpServletRequest request, JpushAliasEntity jpushAliasEntity);

    public int sendCritical(HttpServletRequest request, String location) throws ParseException;

    public int getRealName(UserEntity userEntity);

    public String getAllAlert(HttpServletRequest request) throws JsonProcessingException;

    public int updateLocation(String locX, String locY, HttpServletRequest request);

    public String getBasicInfo(HttpServletRequest request);

    public double[] getLocation(HttpServletRequest request);

    public int getHeartRate(HttpServletRequest request);

    public int updateHeartRate(HttpServletRequest request, String heartRate);


}