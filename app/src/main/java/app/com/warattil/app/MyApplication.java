package app.com.warattil.app;

import android.app.Application;

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

    public void writeToPreference(String key, String value) {

    }
}
