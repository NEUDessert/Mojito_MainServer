package Service;

import Controller.UserController;
import DAO.IUserDAO;
import Entity.*;
import JPush.JPushServerMain;
import Utils.GetTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by killeryuan on 2016/10/19.
 */
public class UserManager implements IUserManager{
    private IUserDAO userDAO ;
    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     *  login
     * @param request
     * @param userEntity
     * @return
     */
    public int login(HttpServletRequest request, UserEntity userEntity){
        int result = userDAO.login(userEntity);
        if(result == 0){
            request.getSession(true).setAttribute("phoneNumber", userEntity.getPhoneNumber());
        }
        return result;
    }

    /**
     * register
     * @param request
     * @param userEntity
     * @return
     */
    public int register(HttpServletRequest request, UserEntity userEntity){
        int result = userDAO.register(userEntity);
        if(result == 0){
            request.getSession(true).setAttribute("phoneNumber", userEntity.getPhoneNumber());
        }
        return result;
    }

    /**
     * finished medical record
     * @param request
     * @param medicalRecordEntity
     * @return
     */
    public int finishedMedical_record(HttpServletRequest request,MedicalRecordEntity medicalRecordEntity){
        medicalRecordEntity.setPhonenumber ((String)(request.getSession(true).getAttribute("phoneNumber")));
        return   userDAO.finishedMedical_record(medicalRecordEntity);
    }

    /**
     *  contacts login
     * @param request
     * @param phoneNumber
     * @param custodyCode
     * @return
     */
    public int contactsLogin(HttpServletRequest request,String phoneNumber, String custodyCode){
        int result = userDAO.contactsLogin(phoneNumber,custodyCode);
        if(result==0) request.getSession(true).setAttribute("phoneNumber", phoneNumber);
        System.out.println(phoneNumber);
        return result;
    }

    /**
     * add alertMessage
     * @param request
     * @param alertEntity
     * @return
     */
    public int addAlert(HttpServletRequest request, AlertEntity alertEntity){
        alertEntity.setPhoneNumber((String)(request.getSession(true).getAttribute("phoneNumber")));
        return userDAO.addAlert(alertEntity);
    }

    /**
     * echo AED
     * @param alertEntity
     * @return
     */
    public int echoAED(AlertEntity alertEntity){
        return userDAO.echoAED(alertEntity);
    }

    /**
     *  set JPush alias
     * @param request
     * @param jpushAliasEntity
     * @return
     */
    public int setAlias(HttpServletRequest request, JpushAliasEntity jpushAliasEntity){
        jpushAliasEntity.setPhoneNumber((String)(request.getSession(true).getAttribute("phoneNumber")));
        return userDAO.setAlias(jpushAliasEntity);
    }

    /**
     *  send Critical message
     * @param request
     * @return
     */
    public int sendCritical(HttpServletRequest request, String location) throws ParseException {
        // 获取发生心脏异常用户手机号，每个用户用手机号注册，手机号每个用户都是唯一的
        String phoneNumber = (String)(request.getSession(true).getAttribute("phoneNumber"));
        // 获取发生心脏异常的用户的名字、监护人在JPush推送平台的注册的alias
        List<String[]> aliasAndName = userDAO.getCritical(phoneNumber);
        // 创建得到当前时间的对象 time
        GetTime time = GetTime.singleGetCurrentTime();
        // 将紧急情况发送给发生心脏异常的用户的所有监护人
        for (int i = 0; i <aliasAndName.size();  i++) {
            // 封装好的JPush 推送平台的推送方法
            JPushServerMain.send(aliasAndName.get(i)[0], "您的亲人" + aliasAndName.get(i)[1] + time.getTime() + "突发心脏异常");
        }
        // 从数据库中拿到所有的AED的信息
        List<DeviceEntity> list = userDAO.getAllAED();
        // 发生心脏异常的用户传上来的location 为   int,int 分别表示经纬度，将location信息中的经纬度分割开，
        StringTokenizer st = new StringTokenizer(location,",");
        String[] loca = new String[2];
        int i = 0;
        while (st.hasMoreTokens()) {
            loca[i] = st.nextToken();
        }
        // 将获得的String型经纬度转换为Double类型
        double recvLocaX = Double.parseDouble(loca[0]);
        double recvlocaY = Double.parseDouble(loca[1]);
        double distance = -1; // 设置发生心脏异常用户与最近的AED的距离，初始值为 -1
        int  deviceId =-1 ; // 离发生心脏异常用户最近的AED的 设备ID deviceId
        // 计算离发生心脏异常用户最近的AED
        for (int j = 0; j < list.size(); j++) {
            double locaX = Double.parseDouble(list.get(i).getLocX());
            double locaY = Double.parseDouble(list.get(i).getLocY());
            double temp = Math.sqrt(Math.pow((recvLocaX-locaX),2)+Math.pow((recvlocaY-locaY),2))*1000;
            // 判断此AED（AED-1）离发生心脏异常用户的距离与此前最小的值（AED-X）的大小，如果AED-1<AED-X， 则用AED-1的值代替AED-X的值成为最小值
            if(temp<distance){
                distance = temp;
                deviceId = list.get(i).getDeviceId();
            }
            // 当distance=-1时，即第一次计算距离时，执行此操作
            else if(distance==-1){
                distance = temp;
                deviceId = list.get(i).getDeviceId();
            }
        // 判断距离是否为-1或者距离大于1000时，将AED信息放到消息队列中储存
        }if (distance!=-1&&distance<1000) {
            AEDmsg msg = new AEDmsg("1", phoneNumber, distance, deviceId);
            UserController.list.add(msg);
        }
        //创建AlertEntity，待报警信息推送后存入数据库
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setLocation(location);
        alertEntity.setPhoneNumber(phoneNumber);
        //调用 addAlert 方法，将AlertEntity存入数据库
        userDAO.addAlert(alertEntity);
        return 0;
    }

    public int getRealName(UserEntity userEntity){
        return userDAO.getRealName(userEntity);
    }

    public String getAllAlert( HttpServletRequest request) throws JsonProcessingException{
        String phoneNumber = (String)(request.getSession(true).getAttribute("phoneNumber"));
        if(phoneNumber == null){
            return "{\"error\":\"2\"}";
        }
        else {
            return objectMapper.writeValueAsString(userDAO.getAllAlert(phoneNumber));
        }
    }

    public int updateLocation( String locX, String locY, HttpServletRequest request){
        String phoneNumber = (String)(request.getSession().getAttribute("phoneNumber"));
        UserEntity userEntity = new UserEntity();
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setLocX(locX);
        userEntity.setLocY(locY);

        return userDAO.updateLocation(userEntity);

    }

    public String getBasicInfo(HttpServletRequest request){
        String phoneNumber = (String)(request.getSession(true).getAttribute("phoneNumber"));
        return userDAO.getBasicInfo(phoneNumber);
    }

    public double[] getLocation(HttpServletRequest request){
        String phoneNumber = (String)request.getSession().getAttribute("phoneNumber");
        if(phoneNumber!=null){
            return userDAO.getLocation(phoneNumber);
        } else {
            return null;
        }
     }

    public int getHeartRate(HttpServletRequest request){
        String phoneNumbner = (String)request.getSession(true).getAttribute("phoneNumber");
        return userDAO.getHeartRate(phoneNumbner);
    }

    public int updateHeartRate(HttpServletRequest request, String heartRate){
        String phoneNumber = (String)request.getSession(true).getAttribute("phoneNumber");
        return userDAO.updateHeartRate(phoneNumber, heartRate);
    }




}
