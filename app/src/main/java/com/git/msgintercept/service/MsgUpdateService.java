package com.git.msgintercept.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.git.msgintercept.internet.ApiSevice;
import com.git.msgintercept.internet.HttpUtils;
import com.git.msgintercept.internet.RxObserver;
import com.git.msgintercept.internet.bean.BaseResponse;
import com.git.msgintercept.internet.request.MsgUpdateRequest;
import com.git.msgintercept.utils.Config;
import com.git.msgintercept.utils.ToastUtils;

/**
 * Created by zero on 2017/12/13.
 * Describe: function
 */

public class MsgUpdateService extends IntentService {

    public MsgUpdateService() {
        super("MsgUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String content = intent.getStringExtra("content");
        final String sender = intent.getStringExtra("sender");
        final String sendTime = intent.getStringExtra("sendTime");

        MsgUpdateRequest msgUpdateRequest = new MsgUpdateRequest();
        msgUpdateRequest.setContent(content);
        msgUpdateRequest.setKeyWord(Config.getKeyWord());
        msgUpdateRequest.setSender(sender);
        msgUpdateRequest.setSendTime(sendTime);
        msgUpdateRequest.setTimestamp(System.currentTimeMillis());

        HttpUtils.getOnlineCookieRetrofit().create(ApiSevice.class).updateOrder(msgUpdateRequest)
                .subscribe(new RxObserver<BaseResponse>() {
                               @Override
                               public void _onNext(BaseResponse baseResponse) {
                                   super._onNext(baseResponse);
                                   ToastUtils.showLongToastSafe(baseResponse.toString());
                               }

                               @Override
                               public void _onError(String msg) {
                                   super._onError(msg);
                                   ToastUtils.showLongToastSafe(msg);
                               }
                           }
                );
    }
}
