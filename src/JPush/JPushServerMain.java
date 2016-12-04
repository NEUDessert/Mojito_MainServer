package JPush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Created by killeryuan on 2016/10/21.
 */
public class JPushServerMain {

    private static String appKey = "f9fafe5c1b8bbd2316c6fdb6";
    private static String MasterSecret = "346ef028b1668790763d23b5";


//
//    JPushClient jpushClient = new JPushClient(DevSecret, DevKey, 3);
//
//    // For push, all you need do is to build PushPayload object.
//    PushPayload payload = buildPushObject_all_all_alert();
//
//    try {
//        PushResult result = jpushClient.sendPush(payload);
//        LOG.info("Got result - " + result);
//
//    } catch (APIConnectionException e) {
//        // Connection error, should retry later
//        LOG.error("Connection error, should retry later", e);
//
//    } catch (APIRequestException e) {
//        // Should review the error, and fix the request
//        LOG.error("Should review the error, and fix the request", e);
//        LOG.info("HTTP Status: " + e.getStatus());
//        LOG.info("Error Code: " + e.getErrorCode());
//        LOG.info("Error Message: " + e.getErrorMessage());
//    }

    public static void main(String args[]){
            send("15734003447","jpush推送测试");
    }

    public static void send(String alias, String alert){
        try {
            JPushClient jpushClient = new JPushClient(MasterSecret, appKey);


            PushPayload payload =   buildPushObject_all_alias_alert(alias,alert);

                PushResult result = jpushClient.sendPush(payload);
//                LOG.info("Got result - " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
//                LOG.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
//                LOG.error("Should review the error, and fix the request", e);
//                LOG.info("HTTP Status: " + e.getStatus());
//                LOG.info("Error Code: " + e.getErrorCode());
//                LOG.info("Error Message: " + e.getErrorMessage());
            }


        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_all_all_alert();
//
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            LOG.info("Got result - " + result);
//
//        } catch (APIConnectionException e) {
//            // Connection error, should retry later
//            LOG.error("Connection error, should retry later", e);
//
//        } catch (APIRequestException e) {
//            // Should review the error, and fix the request
//            LOG.error("Should review the error, and fix the request", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
//        }

    }
    //快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
    public static PushPayload buildPushObject_all_all_alert(String ALERT) {
        return PushPayload.alertAll(ALERT);
    }

    //构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
    public static PushPayload buildPushObject_all_alias_alert(String alias, String ALERT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
    //构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String ALERT, String TITLE) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    //构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的并集，推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，
    // 通知声音为 "happy"，并且附加字段 from = "JPush"；
    // 消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”
    // （如果不显式设置的话，Library 会默认指定为开发）
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String ALERT, String MSG_CONTENT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }


}
