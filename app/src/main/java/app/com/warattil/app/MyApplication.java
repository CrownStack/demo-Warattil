package app.com.warattil.app;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
