package com.rumofuture.nemolite.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rumofuture.nemolite.app.manager.NemoActivityManager;

public class NemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NemoActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NemoActivityManager.removeActivity(this);
    }
}
