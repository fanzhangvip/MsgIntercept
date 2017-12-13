package com.git.msgintercept.internet;

import android.support.v7.app.AppCompatActivity;

import com.git.msgintercept.base.BaseActivity;
import com.git.msgintercept.utils.ProgressDialogUtil;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunnex on 2017/11/21.
 */

public class RxHelper<T> {

    public ObservableTransformer<T, T> io_main(final BaseActivity context) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                ProgressDialogUtil.showProgress(context, "加载中...");
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.<T, ActivityEvent>bindUntilEvent(context.lifecycle(), ActivityEvent.DESTROY));
            }
        };
    }


    public ObservableTransformer io_main(final BaseActivity context, final boolean showProgress) {


        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (showProgress) {
                                    ProgressDialogUtil.showProgress(context, "加载中...");
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.<T, ActivityEvent>bindUntilEvent(context.lifecycle(), ActivityEvent.DESTROY));
            }
        };

    }
}
