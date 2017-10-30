package com.rumofuture.nemolite.app;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class NemoApplication extends Application {

    private static final String APPLICATION_ID = "75b6a15a8791635241707418e52dcb90";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, APPLICATION_ID);
    }
}
