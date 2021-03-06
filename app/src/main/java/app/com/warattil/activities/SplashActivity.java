package app.com.warattil.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import app.com.warattil.R;


public class SplashActivity extends AppCompatActivity {

    private ImageView mImageViewSplash;
    private static final long FIRST_SPLASH_TIME = 2000;
    private static final long SECOND_SPLASH_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageViewSplash = (ImageView) findViewById(R.id.image_view_splash);
        mImageViewSplash.postDelayed(new Runnable() {
            @Override
            public void run() {

                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.splash_second);
                mImageViewSplash.setBackground(drawable); // this method require min sdk 16
                nextActivity();

            }
        }, FIRST_SPLASH_TIME);
    }

    private void nextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                finish();
            }
        }, SECOND_SPLASH_TIME);
    }
}
