package Utils; /**
 * Created by killeryuan on 2016/5/11.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    private String time;
    private static GetTime getCurrentTime;

    private GetTime() {
    }

    public static GetTime singleGetCurrentTime() {
        if(getCurrentTime == null) {
            getCurrentTime = new GetTime();
            return getCurrentTime;
        } else {
            return getCurrentTime;
        }
    }

    public String getTime() throws ParseException{
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String t = nowTime+"";
        String x = t.substring(11,13);
        this.time = sdFormatter.format(nowTime);
        StringBuilder stringBuilder = new StringBuilder(time);
        stringBuilder.replace(11,13,x);
        this.time = stringBuilder.toString();
        time = time.substring(11,time.length());
        return time;
    }

    public static void main(String args[]) throws ParseException{
        GetTime test = GetTime.singleGetCurrentTime();
        System.out.println(test.getTime());

    }
}

