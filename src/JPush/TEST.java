package JPush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

/**
 * Created by killeryuan on 2016/10/21.
 */
public class TEST {

    private static String appKey = "f9fafe5c1b8bbd2316c6fdb6";
    private static String MasterSecret = "346ef028b1668790763d23b5";


    public static String TimeStamp2Date(String timestampString, String formats){
        Long timestamp = Long.parseLong(timestampString);
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;
    }

    public static void main(String args[]) {

        System.out.println(System.currentTimeMillis());

        System.out.println(TimeStamp2Date("1477119697920" ,"yyyy-MM-dd HH:mm:ss"));

        try {
            JPushClient jpushClient = new JPushClient(MasterSecret, appKey);
            TagAliasResult result = jpushClient.getDeviceTagAlias("wangzhiyuan");

//            LOG.info(result.alias);
//            LOG.info(result.tags.toString());
        } catch (APIConnectionException e)

        {
            //    LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            //    LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //    LOG.info("HTTP Status: " + e.getStatus());
            //    LOG.info("Error Code: " + e.getErrorCode());
            //    LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

}
