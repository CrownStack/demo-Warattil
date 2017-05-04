package app.com.warattil.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button_back) Button buttonBack;
    @BindView(R.id.button_setting) Button buttonSetting;
    @BindView(R.id.button_shuffle) Button buttonShuffle;
    @BindView(R.id.button_previous) Button buttonPrevious;
    @BindView(R.id.button_play) Button buttonPlay;
    @BindView(R.id.button_next) Button buttonNext;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @BindView(R.id.text_view_time) TextView textViewTime;
    @BindView(R.id.text_view_title) TextView textViewTitle;
    @BindView(R.id.text_view_song) TextView textViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        buttonBack.setOnClickListener(this);
        buttonSetting.setOnClickListener(this);
        buttonShuffle.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        FontHelper.setFontFace(buttonBack, buttonSetting, buttonShuffle, buttonPrevious, buttonPlay, buttonNext);
        FontHelper.setFontFace(textViewTime, textViewTitle, textViewSong);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_back:

                break;

            case R.id.button_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;

            case R.id.button_shuffle:
                break;

            case R.id.button_previous:
                break;

            case R.id.button_play:
                break;

            case R.id.button_next:
                break;
        }
    }
}
