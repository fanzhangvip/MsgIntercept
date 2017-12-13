package com.git.msgintercept.internet;

import com.git.msgintercept.internet.bean.BaseResponse;
import com.git.msgintercept.internet.request.MsgUpdateRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zero on 2017/12/13.
 * Describe: function
 */

public interface ApiSevice {

    @POST("/update_message")
    Observable<BaseResponse> updateOrder(@Body MsgUpdateRequest request);
}
