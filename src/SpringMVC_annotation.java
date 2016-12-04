import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by killeryuan on 2016/11/22.
 */
public class SpringMVC_annotation {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream(new File("E:\\IDEAWorkSpace\\Mojito\\src\\Controller\\UserController.java"))));
        BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( new File("annotation.md"))));
        String contain = br.readLine();
        while (contain!=null){
            if(contain.contains("@RequestMapping")){
                StringTokenizer st = new StringTokenizer(contain,"\"");
                String resu[] = new String[3];
                int i = 0;
                while (st.hasMoreTokens()){
                    resu[i] = st.nextToken();
                    //   System.out.println(resu[i]);
                    i++;
                }
                bw.write("* "+resu[1]);
                bw.flush();
                bw.newLine();
            }
            contain =  br.readLine();
        }
        br.close();
        bw.close();
    }
}
