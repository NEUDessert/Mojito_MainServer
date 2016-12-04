

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Test{

    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( new File("C:\\Users\\killeryuan\\Desktop\\DS_EX\\yichang4.txt"))));
        String data = br.readLine();
        long changeTime = 2;
        double time = 0;
        double electrocardio = 0;
        ArrayList<HeartData> list = new ArrayList<>();
        while (data!=null){
            long changeStart = System.currentTimeMillis();
            StringTokenizer st = new StringTokenizer(data, ",");
            if(st.hasMoreTokens())
                time = Double.parseDouble(st.nextToken());
            if(st.hasMoreTokens())
                electrocardio = Double.parseDouble(st.nextToken());
            list.add(new HeartData((long) (time*1000),electrocardio));
            long changeEnd = System.currentTimeMillis();
            changeTime += -changeStart + changeEnd;
            data = br.readLine();
        }
        br.close();

        test3("C:\\Users\\killeryuan\\Desktop\\DS_EX\\newyichang4.txt");
    }
    static  void test() throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( new File("C:\\Users\\killeryuan\\Desktop\\DS_EX\\yichang4.txt"))));
        String data = br.readLine();
        long changeTime = 2;
        long time = 0;
        double electrocardio = 0;
        ArrayList<HeartData> list = new ArrayList<>();
        while (data!=null){
            long changeStart = System.currentTimeMillis();
            StringTokenizer st = new StringTokenizer(data, ",");
            if(st.hasMoreTokens())
                time = Long.parseLong(st.nextToken());
            if(st.hasMoreTokens())
                electrocardio = Double.parseDouble(st.nextToken());
            list.add(new HeartData(time,electrocardio));
            long changeEnd = System.currentTimeMillis();
            changeTime += -changeStart + changeEnd;
            data = br.readLine();
//            if(data.equals("end"))
//                break;
        }
        br.close();
        for (int i = 0; i < list.size()-1; i++) {
            if((list.get(i).getTime()-list.get(i+1).getTime())!=-1){
                System.out.println(list.get(i).getTime());
            }
        }
    }

    static void test1(List<HeartData> list) throws IOException{
        BufferedWriter brData = new BufferedWriter ( new OutputStreamWriter( new FileOutputStream( new File("C:\\Users\\killeryuan\\Desktop\\DS_EX\\2016-11-23-20-59-53_ok_ok.txt"))));
        int sum = 0;
        HeartData temp = new HeartData(0,0);
        for (int i = 0; i <list.size() ; i++) {
            HeartData h1 = list.get(i) ;
            if(temp.getTime()==h1.getTime()){
                continue;
            } else {
                if(temp.getTime()==0){
                    brData.write(h1.getTime()+","+h1.getElectrocardio());
                    brData.newLine();
                    temp = h1;
                    sum+=1;
                }else {
                    int count = 1;
                    long timeD_value = h1.getTime() - temp.getTime();
                    double aveElectrocardioValue = (double) (h1.getElectrocardio() - temp.getElectrocardio()) / (double) (timeD_value);
                    for (int j = (int) temp.getTime(); j < (int)h1.getTime(); j++) {
                        brData.write(temp.getTime() + count + "," + (temp.getElectrocardio() + (int)(count * aveElectrocardioValue)));
                        brData.newLine();
                        brData.flush();
                        sum+=1;
                        count++;
                    }
                    temp = h1;
                }
            }
        }
        System.out.println(list.size());
        System.out.println(sum);
        System.out.println(System.currentTimeMillis());
        double aaaa = 1479906084286.0;
        double bbbb = 1479905993140.0;
        System.out.println(aaaa - bbbb);
        //  test();
    }
// 恢复成一毫秒一个数据,并存入文件中
    static void test2(List<HeartData> list) throws IOException{
        BufferedWriter brData = new BufferedWriter ( new OutputStreamWriter( new FileOutputStream( new File("C:\\Users\\killeryuan\\Desktop\\DS_EX\\newyichang4.txt"))));
        int sum = 0;
        HeartData temp = new HeartData(0,0);
        for (int i = 0; i <list.size() ; i++) {
            HeartData h1 = list.get(i) ;
            if(temp.getTime()==h1.getTime()){
                continue;
            } else {
                if(temp.getTime()==0){
                    brData.write(h1.getTime()+","+h1.getElectrocardio());
                    brData.newLine();
                    temp = h1;
                    sum+=1;
                }else {
                    int count = 1;
                    long timeD_value = h1.getTime() - temp.getTime();
                    double aveElectrocardioValue = (double) (h1.getElectrocardio() - temp.getElectrocardio()) / (double) (timeD_value);
                    for (int j = (int) temp.getTime(); j < (int)h1.getTime(); j++) {
                        brData.write(temp.getTime() + count + "," + (temp.getElectrocardio() + (double) (count * aveElectrocardioValue)));
                        brData.newLine();
                        brData.flush();
                        sum+=1;
                        count++;
                    }
                    temp = h1;
                }
            }
        }
        System.out.println(list.size());
        System.out.println(sum);
        System.out.println(System.currentTimeMillis());
        double aaaa = 1479906084286.0;
        double bbbb = 1479905993140.0;
        System.out.println(aaaa - bbbb);
          test();
    }
    // path 绝对路径
    static void test3(String path) throws IOException{
        BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( new File(path))));
        String message = br.readLine();
        List<HeartData> list = new ArrayList<>();
        while(message!=null){
            StringTokenizer st = new StringTokenizer(message, ",");
            long time = 0;
            double heart = 0;
            if(st.hasMoreTokens()){
                time = Long.parseLong(st.nextToken());
            }
            if(st.hasMoreTokens()){
                heart = Double.parseDouble(st.nextToken());
            }
            list.add(new HeartData(time, heart));
            message = br.readLine();
        }
        double datas[][] = new double[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            datas[i][0] = (double) (list.get(i).getTime());
            datas[i][1] = list.get(i).getElectrocardio();
        }
        Socket socket = new Socket("192.168.50.181",8099);
        System.out.println(123);
        DataOutputStream dos = new DataOutputStream( socket.getOutputStream());
        DataInputStream dis = new DataInputStream( socket.getInputStream());
        dos.writeInt(list.size());
        dos.flush();
        for (int i = 0; i <list.size() ; i++) {
            dos.writeDouble(list.get(i).getElectrocardio());
            dos.flush();
        }
        double te = dis.readDouble();
        System.out.println(te);
        socket.close();
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