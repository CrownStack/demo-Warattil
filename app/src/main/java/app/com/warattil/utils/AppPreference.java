package app.com.warattil.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String APP_SHARED_PREFERENCE = "Apps_Preferences";
    private static SharedPreferences sSharedPreferences;
    private static AppPreference sAppPreference;
    private SharedPreferences.Editor mEditor;

    private AppPreference(Context mContext) {
        sSharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        mEditor = sSharedPreferences.edit();
    }

    public static AppPreference getAppPreference(Context mContext) {
        if (sAppPreference == null) sAppPreference = new AppPreference(mContext);

        return sAppPreference;
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }
    
    public String getString(String key) {
       return sSharedPreferences.getString(key, "");
    }



}
