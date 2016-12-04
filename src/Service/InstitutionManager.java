package Service;

import DAO.IUserDAO;
import Entity.AidPeopleEntity;
import Entity.AlertEntity;
import Entity.DeviceEntity;
import Entity.InstitutionEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static Controller.UserController.list;

/**
 * Created by killeryuan on 2016/11/19.
 */
public class InstitutionManager implements IInstitutionManager {

    private IUserDAO userDAO;
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public String register(InstitutionEntity institutionEntity){
        int result = userDAO.register(institutionEntity);
        if(result==0) {
            String a = result + "";
            int length = a.length();
            String temp = "";
            for (int i = 9 - length; i > 0; i--) {
                temp += "0";
            }
            return "{\"error\":\"0\",\"id\":\""+temp + a+"\"}";
        } else {
            return "{\"error\":\"1\"}";
        }
    }
    public int instLogin(String institutionId, String password, HttpServletRequest request) {
        int result = userDAO.instLogin(institutionId, password);
        if(result==0){
            request.getSession(true).setAttribute("institutionId", institutionId);
        }
        return result;
    }
    public int register(AidPeopleEntity aidPeopleEntity, HttpServletRequest request){
        int result = userDAO.register(aidPeopleEntity);
        if(result==0){
            request.getSession(true).setAttribute("aidNumber", aidPeopleEntity.getAidPhoneNumber());
        }
        return result;
    }
    public int aidLogin(String aidPhoneNumber, String password,  HttpServletRequest request){
        int result = userDAO.aidLogin(aidPhoneNumber, password);
        if(result==0){
            request.getSession(true).setAttribute("aidNumber", aidPhoneNumber);
        }
        return result;
    }
    public List<AidPeopleEntity> getAidAllPeople( HttpServletRequest request){
        Integer institutionId = (Integer)(request.getSession(true).getAttribute("institutionId"));
        List<AidPeopleEntity> list = userDAO.getAidPeople(institutionId);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(null);
        }
        return list;
    }
    public List<DeviceEntity> getDevice( HttpServletRequest request){
        Integer institutionId = (Integer)(request.getSession(true).getAttribute("institutionId"));
        return userDAO.getDevice(institutionId);

    }
    public int addDevice(DeviceEntity deviceEntity, HttpServletRequest request){
        Integer institutionId = Integer.parseInt((String)(request.getSession(true).getAttribute("institutionId")));
        deviceEntity.setInstitutionId(institutionId);
        return userDAO.addDevice(deviceEntity);
    }
    public List<AlertEntity> getAllAlert( HttpServletRequest request){
        Integer institutionId = (Integer)(request.getSession(true).getAttribute("institutionId"));
        return userDAO.getAllAlert(institutionId);
    }
    public AidPeopleEntity getAidPeople( HttpServletRequest request){
        String aidPhoneNumber = (String)(request.getSession(true).getAttribute("aidPhoneNumber"));
        return userDAO.getAidPeople(aidPhoneNumber);
    }
    public int confirmPassword( String password, HttpServletRequest request){
        String aidPhoneNumber = (String)(request.getSession(true).getAttribute("aidPhoneNumber"));
        return userDAO.confirmPassword(aidPhoneNumber, password);
    }
    public int changePassword(String password, HttpServletRequest request){
        String aidPhoneNumber = (String)(request.getSession(true).getAttribute("aidPhoneNumber"));
        return userDAO.changePassword(aidPhoneNumber, password);
    }
    public int changeInstitution( int institutionId, HttpServletRequest request){
        String aidPhoneNumber = (String)(request.getSession(true).getAttribute("aidPhoneNumber"));
        return userDAO.changeInstitution(aidPhoneNumber, institutionId);
    }





    public void test(){
        DeviceEntity deviceEntity = new DeviceEntity();
//        deviceEntity.setDeviceId(123);;
        deviceEntity.setLocX("12.12");
        deviceEntity.setLocY("13.13");
        deviceEntity.setInstitutionId(123);

       // userDAO.create(deviceEntity,);

    }
}
