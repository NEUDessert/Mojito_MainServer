package Controller;

import Entity.AidPeopleEntity;
import Entity.DeviceEntity;
import Entity.InstitutionEntity;
import Service.IInstitutionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by killeryuan on 2016/11/19.
 */

@Controller
@RequestMapping("institution")
public class InstitutionController {

    String[] usualResults = {"{\"error\":\"0\"}",
            "{\"error\":\"1\"}",
            "{\"error\":\"2\"}"};

    @Resource(name= "objectMapper")
    private ObjectMapper objectMapper;
    @Resource(name = "institutionManager")
    private IInstitutionManager institutionManager;

    @RequestMapping("institutionRegister")
    public void register(InstitutionEntity institutionEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println(institutionManager.register(institutionEntity));
    }
    @RequestMapping("institutionLogin")
    public void login(String institutionId, String password, HttpServletRequest request, HttpServletResponse response)throws IOException {
        System.out.println(request.getParameterMap().keySet());
        response.getWriter().println(usualResults[institutionManager.instLogin(institutionId, password, request)]);
    }

    @RequestMapping("aidPeopleRegister")
    public void aidPeopleRegister(AidPeopleEntity aidPeopleEntity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(usualResults[institutionManager.register(aidPeopleEntity, request)]);
    }
    @RequestMapping("aidPeopleLogin")
    public void aidPeopleLogin(String aidPhoneNumber, String password,  HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        response.getWriter().println(usualResults[institutionManager.aidLogin(aidPhoneNumber, password, request)]);
    }

    @RequestMapping("getAidAllPeople")
    public void  getAidAllPeople(HttpServletResponse response,  HttpServletRequest request) throws IOException {
        response.getWriter().println(objectMapper.writeValueAsString(institutionManager.getAidAllPeople(request)));
    }
    @RequestMapping("getDevices")
    public void getDevice( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(objectMapper.writeValueAsString(institutionManager.getDevice(request)));
    }

    @RequestMapping("addDevice")
    public void addDevice(DeviceEntity deviceEntity,HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.getWriter().println(usualResults[institutionManager.addDevice(deviceEntity, request)]);
    }
    @RequestMapping("getAllAlert")
    public void getAllAlert( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(objectMapper.writeValueAsString(institutionManager.getAllAlert(request)));
    }

    @RequestMapping("getAidPeople")
    public void  getAidPeople( HttpServletRequest request ,HttpServletResponse response) throws IOException {
         response.getWriter().println(objectMapper.writeValueAsString(institutionManager.getAidPeople(request)));
    }
    @RequestMapping("confirmPassword")
    public void  confirmPassword( String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(usualResults[institutionManager.confirmPassword(password, request)]);
    }
    @RequestMapping("changePassword")
    public void  changePassword(String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(usualResults[institutionManager.changePassword(password,request)]);
    }
    @RequestMapping("changeInstitution")
    public void  changeInstitution( int institutionId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(usualResults[institutionManager.changeInstitution(institutionId, request)]);
    }

    @RequestMapping("test")
    public void test(){
        institutionManager.test();
    }
}
