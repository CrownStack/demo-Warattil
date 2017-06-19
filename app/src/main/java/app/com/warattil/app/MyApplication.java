package app.com.warattil.app;

import com.google.firebase.analytics.FirebaseAnalytics;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private static MyApplication sInstance;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
