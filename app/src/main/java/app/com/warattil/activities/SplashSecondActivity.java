package app.com.warattil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import app.com.warattil.R;
import app.com.warattil.utils.AppPreference;
import app.com.warattil.utils.Constants;


public class SplashSecondActivity extends AppCompatActivity implements Constants{

    private static final long SECOND_SPLASH_TIME = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_second);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                retrievePreference();
            }
        }, SECOND_SPLASH_TIME);
    }

    private void retrievePreference() {
        String storedLanguage = AppPreference.getAppPreference(SplashSecondActivity.this).getString(PREF_LANGUAGE);

        if (storedLanguage.equals(PREF_LANGUAGE_ENGLISH) || storedLanguage.equals(PREF_LANGUAGE_ARABIC) ) {
            startActivity(new Intent(getApplicationContext(), SongListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            finish();
        }
    }
}
