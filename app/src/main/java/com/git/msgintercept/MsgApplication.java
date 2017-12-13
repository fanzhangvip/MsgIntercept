package com.git.msgintercept;

import android.app.Application;

import com.git.msgintercept.utils.Config;
import com.git.msgintercept.utils.Utils;

/**
 * Created by zero on 2017/12/13.
 * Describe: function
 */

public class MsgApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Config.init(this);
    }
}
