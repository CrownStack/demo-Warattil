package app.com.warattil.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import app.com.warattil.R;
import app.com.warattil.utils.AppPreference;
import app.com.warattil.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity implements Constants {

    @BindView(R.id.image_view_splash) ImageView mImageViewSplash;

    private static final long FIRST_SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        mImageViewSplash.postDelayed(new Runnable() {
            @Override
            public void run() {

                nextActivity();
            }
        }, FIRST_SPLASH_TIME);
    }

    private void nextActivity() {
       startActivity(new Intent(this, SplashSecondActivity.class));
        finish();
    }
}
