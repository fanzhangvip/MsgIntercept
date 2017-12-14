package com.git.msgintercept;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.git.msgintercept.base.BaseActivity;
import com.git.msgintercept.receive.MsgInterceptRecv;
import com.git.msgintercept.service.JobScheduleService;
import com.git.msgintercept.utils.Config;
import com.git.msgintercept.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zero on 2017/12/13.
 * Describe: function
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.baseurl)
    EditText baseurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestAllManifestPermissions(this);
        Utils.isNotificationListenerEnabled(this);
        Utils.requestIgnoreBatteryOptimizations(this);
        input.setText(Config.getKeyWord());
        baseurl.setText(Config.getBaseUrl());
        sendMyAction();
    }

    private void sendMyAction() {
        Intent intent = new Intent(MsgInterceptRecv.MSG_ACTION);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

        Intent jobIntent = new Intent(MainActivity.this, JobScheduleService.class);
        startService(jobIntent);
    }

    private void requestAllManifestPermissions(final AppCompatActivity compatActivity) {
        PermissionsManager.getInstance()
                .requestAllManifestPermissionsIfNecessary(compatActivity,
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {

                            }

                            @Override
                            public void onDenied(String permission) {

                            }
                        });
    }

    @OnClick(R.id.confirm)
    public void onViewClicked() {
        Config.setKeyWord(input.getText().toString().trim());
        Config.setBaseUrl(baseurl.getText().toString().trim());
    }
}
