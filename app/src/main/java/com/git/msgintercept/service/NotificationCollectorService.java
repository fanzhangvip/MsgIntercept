package com.git.msgintercept.service;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.git.msgintercept.receive.MsgInterceptRecv;

/**
 * Created by zero on 2017/11/21.
 * Describe: function
 */

public class NotificationCollectorService extends NotificationListenerService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        sendMyAction();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        sendMyAction();
    }

    private void sendMyAction() {
        Intent intent = new Intent(MsgInterceptRecv.MSG_ACTION);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }
}
