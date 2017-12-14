package com.git.msgintercept.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.git.msgintercept.service.JobScheduleService;
import com.git.msgintercept.service.MsgUpdateService;
import com.git.msgintercept.utils.Config;
import com.git.msgintercept.utils.DateFormatUtil;
import com.git.msgintercept.utils.L;
import com.git.msgintercept.utils.ToastUtils;

/**
 * Created by zero on 2017/12/13.
 * Describe: function
 */

public class MsgInterceptRecv extends BroadcastReceiver {
    private static final String TAG = "Zero";

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    public static final String MSG_ACTION = "com.git.msgintercept.MSG_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        //先判断广播消息
        final String action = intent.getAction();
        if (MSG_ACTION.equals(action)) {
            Log.i("Zero", "自定义广播为了开启监听进程");
        }
        if (ACTION_BOOT.equals(action)) {
            L.getLogger().tag("Zero").i("监听开机广播为了开启监听进程");
            Intent jobIntent = new Intent(context, JobScheduleService.class);
            context.startService(jobIntent);
        }
        if (SMS_RECEIVED_ACTION.equals(action)) {
            //获取intent参数
            Bundle bundle = intent.getExtras();
            //判断bundle内容
            if (bundle != null) {
                //取pdus内容,转换为Object[]
                Object[] pdus = (Object[]) bundle.get("pdus");
                //解析短信
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    byte[] pdu = (byte[]) pdus[i];
                    messages[i] = SmsMessage.createFromPdu(pdu);
                }
                //解析完内容后分析具体参数
                for (SmsMessage msg : messages) {
                    //获取短信内容
                    String content = msg.getMessageBody();
                    String sender = msg.getOriginatingAddress();
                    String sendTime = DateFormatUtil.format(msg.getTimestampMillis(), DateFormatUtil.YYYY_MM_DD2_HH_MM_SS);
                    //TODO:根据关键字判断,然后进统一处理
                    final String keyword = Config.getKeyWord();
                    boolean intercept = !TextUtils.isEmpty(keyword) && !TextUtils.isEmpty(content) && content.contains(keyword);
                    if (intercept) {
                        L.getLogger().tag("Zero").i("关键字: %s, 收到: %s, 内容: %s, 时间: %s", keyword, sender, content, sendTime.toString());
                        Intent processIntent = new Intent(context, MsgUpdateService.class);
                        processIntent.putExtra("content", content);
                        processIntent.putExtra("sender", sender);
                        processIntent.putExtra("sendTime", sendTime);
                        context.startService(processIntent);
                        //TODO: 对于特定的内容,取消广播
                        //abortBroadcast();
                    } else {
                    }
                }

            }
        }//if 判断广播消息结束
    }
}
