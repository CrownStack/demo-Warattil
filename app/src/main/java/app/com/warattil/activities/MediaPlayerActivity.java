package app.com.warattil.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeUnit;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import app.com.warattil.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerActivity extends AppCompatActivity implements Constants{

    @BindView(R.id.image_view_back) ImageView imageViewBack;
    @BindView(R.id.image_view_setting) ImageView imageViewSetting;
    @BindView(R.id.image_view_shuffle) ImageView imageViewShuffle;
    @BindView(R.id.image_view_reverse) ImageView imageViewReverse;
    @BindView(R.id.image_view_play) ImageView imageViewPlay;
    @BindView(R.id.image_view_pause) ImageView imageViewPause;
    @BindView(R.id.image_view_forward) ImageView imageViewForward;
    @BindView(R.id.image_view_repeat) ImageView imageViewRepeat;
    @BindView(R.id.seek_bar) SeekBar seekBar;
    @BindView(R.id.text_view_time) TextView textViewTime;
    @BindView(R.id.text_view_title) TextView textViewTitle;
    @BindView(R.id.text_view_song) TextView textViewSong;

    MediaPlayer mediaPlayer;
    Handler handler;
    Runnable runnable;
    private boolean mIsPlay = false;
    private String mMusicTitle ;
    private String mReciter;
    private long mDuration ;
    private String mFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);
        applyTypeface();
        getMusicDetails();

        handler = new Handler();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        seekBar.setMax(mediaPlayer.getDuration()/1000);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mIsPlay = true;
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if(input) {
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getMusicDetails() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            mMusicTitle = bundle.getString("songName");
            mReciter = bundle.getString("reciter");
            mFileName = bundle.getString("fileName");
            Log.e("Details: ", mMusicTitle + "\n " + mReciter + " \n" + mFileName);
            textViewSong.setText(mMusicTitle);
            if(mReciter.equals(PREF_RECITER_NOURALLAH)) textViewTitle.setText(getString(R.string.nourallah));
            else textViewTitle.setText(getString(R.string.sheikh));
        }
        textViewTime.setText(mediaPlayer.getDuration()/1000);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() + PRAYER_DIR_PATH +"/" + mFileName));
    }

    @OnClick(R.id.image_view_play)
    void playMusic() {
        if(!mediaPlayer.isPlaying() && mIsPlay == true) {
            playCycle();
            mediaPlayer.start();
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.VISIBLE);
            mIsPlay = true;
        } else {
            mediaPlayer.pause();
            imageViewPlay.setVisibility(View.VISIBLE);
            imageViewPause.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.image_view_pause)
    void pauseMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imageViewPause.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.image_view_forward)
    void nextMusic(View view) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(mFileName + 1));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playCycle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if(mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                    Log.e("playCycle", "playCycle");
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        playCycle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }

    private void applyTypeface() {
        FontHelper.setFontFace(textViewTime, textViewTitle, textViewSong);
    }

    @OnClick(R.id.image_view_back)
    void songList() {
        startActivity(new Intent(this, SongListActivity.class));

    }

    @OnClick(R.id.image_view_setting)
    void clickSetting() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
