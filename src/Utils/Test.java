package Utils;

import Entity.AidPeopleEntity;
import Entity.DeviceEntity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.PacketTooBigException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by killeryuan on 2016/11/15.
 */
public class Test {

        public static final String url = "jdbc:mysql://127.0.0.1/student";
        public static final String name = "com.mysql.jdbc.Driver";
        public static final String user = "root";
        public static final String password = "200424";

        public Connection conn = null;
        public Statement pst = null;

        public void test() {

            try {
                Class.forName(name);//指定连接类型
                conn = DriverManager.getConnection(url, user, password);//获取连接
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("test.txt"))));
                String message = br.readLine() +"";
                while (message != null) {
                 //   System.out.println(message);
                    StringTokenizer st = new StringTokenizer(message, ",");
                    String[] mes = new String[st.countTokens()];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        mes[i] = st.nextToken();
                        System.out.print(mes[i]+"   ");
                        i++;
                    }
                    System.out.println();
                    String sql = "INSERT INTO student_info VALUES ("+mes[0]+",'"+mes[1]+"','"+mes[2]+"','"+mes[3]+"','"+mes[4].substring(mes[4].length()-4,mes[4].length())+"'" +
                            ",'"+mes[5]+"','"+mes[6]+"','"+mes[7].substring(0,3)+"','"+mes[7]+"'" +
                            ",'"+mes[4].substring(mes[4].length()-4,mes[4].length()).substring(0,2)+"')";
                    pst = conn.createStatement();//准备执行语句
                    pst.execute(sql);
                    message = br.readLine();
                }
            }catch ( Exception e){
                e.printStackTrace();
            }
    }

    public void  getJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DeviceEntity> list = new ArrayList<>();
        list.add(new DeviceEntity());
        System.out.print(objectMapper.writeValueAsString(list));

    }

    public static void main(String[] args) throws JsonProcessingException, IOException{
      //  new Test().test();
//        new Test().getJson();
    }
}
