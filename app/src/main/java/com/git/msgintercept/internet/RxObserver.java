package com.git.msgintercept.internet;

import com.git.msgintercept.internet.bean.BaseResponse;
import com.git.msgintercept.utils.ProgressDialogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zero on 2017/11/30.
 * Describe: function
 */

public class RxObserver<T extends BaseResponse> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
        _onSubscribe(d);
    }

    @Override
    public void onComplete() {
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        ProgressDialogUtil.dismiss();
        e.printStackTrace();
        _onError(e.toString());

    }


    @Override
    public void onNext(T t) {
        if (t.getCode() == BaseResponse.CODE_SUCCESS) {
            _onNext(t);
        } else {//除了返回200之外，其他的都当成错误统一处理
            _onError(t.getMessage());
        }
    }

    public void _onSubscribe(Disposable d) {
    }

    ;

    public void _onNext(T t) {
    }

    public void _onError(String msg) {
    }
}
