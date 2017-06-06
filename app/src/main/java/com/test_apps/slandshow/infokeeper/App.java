package com.test_apps.slandshow.infokeeper;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.balram.locker.view.AppLocker;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppLocker.getInstance().enableAppLock(this);
    }
}