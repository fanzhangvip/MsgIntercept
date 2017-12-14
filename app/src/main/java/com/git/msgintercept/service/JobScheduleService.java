package com.git.msgintercept.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.git.msgintercept.receive.MsgInterceptRecv;
import com.git.msgintercept.utils.L;

/**
 * Created by zero on 2017/11/21.
 * Describe: function
 */

public class JobScheduleService extends JobService {


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JobParameters param = (JobParameters) msg.obj;
            jobFinished(param, true);
            sendMyAction();
            L.getLogger().tag("Zero").i("JobScheduleService 去启动接收短信");
            return true;
        }
    });

    private void sendMyAction() {
        Intent intent = new Intent(MsgInterceptRecv.MSG_ACTION);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    public static void startJobSchedule(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(context.getPackageName(), JobScheduleService.class.getName()))
                    .setPeriodic(10)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.cancel(1);
            jobScheduler.schedule(jobInfo);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startJobSchedule(this);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Message m = Message.obtain();
        m.obj = params;
        handler.sendMessage(m);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        handler.removeCallbacksAndMessages(null);
        return false;
    }
}

