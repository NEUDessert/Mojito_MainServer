package Controller;

import Entity.*;
import Service.IUserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by killeryuan on 2016/10/19.
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final String matlabHost = "192.168.0.165";

    public static ArrayList<AEDmsg> list = new ArrayList<>();

    public static  int i = 1;
    String[] usualResults = {"{\"error\":\"0\"}",
            "{\"error\":\"1\"}",
            "{\"error\":\"2\"}"};

    @Resource(name = "userManager")
    private IUserManager userManager;
    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @RequestMapping("login")
    public void login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{

        int result = userManager.login(request,userEntity);
        String name = result==0?userEntity.getName():"";
        String[] logins = {"{\"error\":\"0\",\"name\":\""+name+"\",\"custodyCode\":\""+userEntity.getCustodyCode()+"\"}",
                           "{\"error\":\"1\"}",
                           "{\"error\":\"2\"}"};
        response.getWriter().println(logins[result]);
//        request.setAttribute("user",logins[result]);
//        return "index";
    }

    @RequestMapping("register")
    public void register(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String time = System.currentTimeMillis()+"";
        String custodyCode = time.substring(time.length()-11,time.length());
        userEntity.setCustodyCode(custodyCode);
        int result = userManager.register(request, userEntity);
        String name = result==0?userEntity.getName():"";
        String[] registers = {"{\"error\":\"0\",\"name\":\""+name+"\",\"custodyCode\":\""+custodyCode+"\"}",
                "{\"error\":\"1\",\"message\":\"手机号或者邮箱已经被使用\"}",
                "{\"error\":\"2\"}"};
        response.getWriter().println(registers[result]);
    }

    @RequestMapping("finishedMR")
    public void finishedMR (MedicalRecordEntity medicalRecordEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println(objectMapper.writeValueAsString(medicalRecordEntity));
        try {
            response.getWriter().println(usualResults[userManager.finishedMedical_record(request, medicalRecordEntity)]);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("contactsLogin")
    public void contactsLogin(String phoneNumber, String custodyCode, HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println(request.getHeader("Content-Length"));
        response.getWriter().println(usualResults[userManager.contactsLogin(request, phoneNumber, custodyCode)]);
    }



    @RequestMapping("addAlert")
    public void allAlert(AlertEntity alertEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println(usualResults[userManager.addAlert(request,alertEntity)]);
    }

    @RequestMapping("echoAED")
    public void echoAED( HttpServletResponse response, String deviceId) throws  IOException {
        // 接收 AED发来的 deviceId
        int deviceIdInt = Integer.parseInt(deviceId);
        String json = null;
        // 判断轮询发送请求的AED的deviceId 与在等待队列中的deviceId是否一致
        for (int j = 0; j <list.size() ; j++) {
            if(list.get(j).getDeviceId()==deviceIdInt){
                //将等待队列中的对象转换成json
                DecimalFormat df = new DecimalFormat("0.00");
                list.get(j).setDistance( Double.parseDouble(df.format((list.get(j).getDistance()))));
                json = objectMapper.writeValueAsString(list.get(j));
                list.remove(j);
            }
        }
        System.out.println(json);
        // 根据json的值确定返回的值
        json = json==null?"{\"haveMsg\":\"0\"}":json;
        response.getWriter().println(json);
    }

    @RequestMapping("setAlias")
    public void setAlias(JpushAliasEntity jpushAliasEntity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println(usualResults[userManager.setAlias(request,jpushAliasEntity)]);
    }

    @RequestMapping("getRealName")
    public void getRealName(String custodyCode, HttpServletRequest request, HttpServletResponse response) throws IOException{
        UserEntity userEntity = new UserEntity();
        userEntity.setCustodyCode(custodyCode);
        int result = userManager.getRealName(userEntity);
        if(result==0){
            response.getWriter().println("{\"name\":\""+userEntity.getName()+"\",\"locX\":\"123.426881\",\"locY\":\"41.655687\"}");
        }
    }


    // 监护人请求

    /**
     *  获取被监护人的基本信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("getBasicInfo")
    public void getBasicInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println(userManager.getBasicInfo(request));
    }

    /**
     *  监护端获取被监护人的所有地址信息
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("getLocation")
    public void getLocation(HttpServletResponse response, HttpServletRequest request) throws IOException{
        double[] temp = userManager.getLocation(request);
        if (temp==null){
            response.getWriter().println("{\"error\":\"1\"}");
        } else {
            response.getWriter().println("{\"error\":\"0\",\"locX\":\""+temp[0]+"\",\"locY\":\""+temp[1]+"\"}");
        }
    }

    /**
     *  监护段拿到所有的报警信息
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("getAllAlert")
    public void getAllAlert(HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.getWriter().println(userManager.getAllAlert(request));
    }

    /**
     * 监护人退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("cLogout")
    public void cLogout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession(true).removeAttribute("phoneNumber");
        response.getWriter().println(usualResults[0]);
    }

    @RequestMapping("getHeartRate")
    public void getHeartRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int heartRate = userManager.getHeartRate(request);
        response.getWriter().println("{\"heartRate\":\""+heartRate+"\"}");
        System.out.println(heartRate);
    }


// --------------------------




    /**
     * 报警器测试
     * @param deviceId
     * @param response
     * @throws IOException
     */
    @RequestMapping("test")
    public void test(String deviceId, HttpServletResponse response) throws IOException{
        System.out.println(deviceId);
        System.out.println(deviceId);
        if(i%5==0) {
            response.getWriter().println("{\"haveMsg\":\"1\",\"phoneNumber\":\"18842569699\",\"distance\":\"5\"}");
            i++;
        }else {
            response.getWriter().println("{\"haveMsg\":\"0\"}");
            i++;
        }
    }


    //
    @RequestMapping("getPhoneNumber")
    public void getPhoneNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String number = (String)request.getSession(true).getAttribute("phoneNumber");
        response.getWriter().println("{\"phoneNumber\":\""+number+"\"}");
    }


// --------------------------


    //  使用者请求
    static int in = 0;

    /**
     *  上传心电数据
     * @param data
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("dataBlueTooth")
    public void test(String data, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        if(data!=null) {
            // response.getWriter().println(data);
            System.out.println(data);
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\killeryuan\\Desktop\\DS_EX\\data\\test" + in + ".txt"))));
//            bw.write(data);
//            bw.flush();
//            bw.close();
            in++;
            StringTokenizer st = new StringTokenizer(data, "\n");
            System.out.println(st.countTokens());
            List<HeartData> list = new ArrayList<>();
            while (st.hasMoreTokens()) {
                StringTokenizer sts = new StringTokenizer(st.nextToken(), ",");
                long time = 0;
                double electrocardio = 0;
                if (sts.hasMoreTokens())
                    time = Long.parseLong(sts.nextToken());
                if (sts.hasMoreTokens())
                    electrocardio = Double.parseDouble(sts.nextToken());
                list.add(new HeartData((long) time, electrocardio));
            }
            //   int sum = 0;
            HeartData temp = new HeartData(0, 0);
            List<Double> changedData = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                HeartData h1 = list.get(i);
                if (temp.getTime() == h1.getTime()) {
                    continue;
                } else {
                    if (temp.getTime() == 0) {
                        changedData.add(h1.getElectrocardio());
                        temp = h1;
                    } else {
                        int count = 1;
                        long timeD_value = h1.getTime() - temp.getTime();
                        double aveElectrocardioValue = (double) (h1.getElectrocardio() - temp.getElectrocardio()) / (double) (timeD_value);
                        for (int j = (int) temp.getTime(); j < (int) h1.getTime(); j++) {
                            changedData.add((temp.getElectrocardio() + (double) (count * aveElectrocardioValue)));
                            count++;
                        }
                        temp = h1;
                    }
                }
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream( new File(""))));
                Socket socket = new Socket(matlabHost, 8099);
                System.out.println("已经和xxlianjieshang ");
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                dos.writeInt(list.size());
                dos.flush();
                for (int i = 0; i < list.size(); i++) {
                    dos.writeDouble(list.get(i).getElectrocardio());
                    dos.flush();
                }

                double te = dis.readDouble();
                if (te == 2) {
                    System.out.println("警告");
                    response.getWriter().println("{\"error\":\"2\"}");
                    //   userManager.echoAED()
                    String phoneNumber = (String) (request.getSession(true).getAttribute("phoneNumber"));
                    double[] location = userManager.userGetLocation(phoneNumber);
                    AlertEntity alert = new AlertEntity();
                    alert.setAlertLocX("" + location[0]);
                    alert.setAlertLocY("" + location[1]);
                    alert.setPhoneNumber(phoneNumber);
                    alert.setType("危险");
                    alert.setAlertTime(System.currentTimeMillis());
                    userManager.sendCritical(alert);
                } else if (te == 1) {
                    response.getWriter().println("{\"error\":\"1\"}");
                    System.out.println("异常");
                    String phoneNumber = (String) (request.getSession(true).getAttribute("phoneNumber"));
                    double[] location = userManager.userGetLocation(phoneNumber);
                    AlertEntity alert = new AlertEntity();
                    alert.setAlertLocX("" + location[0]);
                    alert.setAlertLocY("" + location[1]);
                    alert.setPhoneNumber(phoneNumber);
                    alert.setType("异常");
                    alert.setAlertTime(System.currentTimeMillis());
                    userManager.addAlert(alert);
                    //  alert.set
                    response.getWriter().println("{\"error\":\"1\"}");
                } else if (te == 0) {
                    response.getWriter().println("{\"error\":\"0\"}");
                }

                socket.close();
            }catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("{\"error\":\"0\"}");

            }
        }
    }

    /**
     *  更新用户位置信息
     * @param locX
     * @param locY
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("updateLocation")
    public void updateLocation(String locX, String locY, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(locX+"  "+locY);

        response.getWriter().println(usualResults[userManager.updateLocation(locX, locY, request)]);
    }

    @RequestMapping("updateHeartRate")
        public void updateHeartRate(String heartRate, HttpServletRequest request, HttpServletResponse response){
        int resu = userManager.updateHeartRate(request, heartRate);
    }









}

class HeartData{
    private long time;
    private double electrocardio;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getElectrocardio() {
        return electrocardio;
    }

    public void setElectrocardio(double electrocardio) {
        this.electrocardio = electrocardio;
    }

    public HeartData(long time, double electrocardio) {
        this.time = time;
        this.electrocardio = electrocardio;
    }
    public String toString(){
        return time + "  "+electrocardio;
    }

}

