package com.git.msgintercept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.git.msgintercept.base.BaseActivity;
import com.git.msgintercept.utils.Config;

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
        input.setText(Config.getKeyWord());
        baseurl.setText(Config.getBaseUrl());
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
