package app.com.warattil.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerActivity extends AppCompatActivity {

    @BindView(R.id.image_view_back) ImageView imageViewBack;
    @BindView(R.id.image_view_setting) ImageView imageViewSetting;
    @BindView(R.id.image_view_shuffle) ImageView imageViewShuffle;
    @BindView(R.id.image_view_reverse) ImageView imageViewReverse;
    @BindView(R.id.image_view_play) ImageView imageViewPlay;
    @BindView(R.id.image_view_forward) ImageView imageViewForward;
    @BindView(R.id.image_view_repeat) ImageView imageViewRepeat;
    @BindView(R.id.seek_bar) SeekBar seekBar;
    @BindView(R.id.text_view_time) TextView textViewTime;
    @BindView(R.id.text_view_title) TextView textViewTitle;
    @BindView(R.id.text_view_song) TextView textViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);
        applyTypeface();
    }

    private void applyTypeface() {
        FontHelper.setFontFace(textViewTime, textViewTitle, textViewSong);
    }

    @OnClick(R.id.image_view_setting)
    void clickSetting(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }
}
