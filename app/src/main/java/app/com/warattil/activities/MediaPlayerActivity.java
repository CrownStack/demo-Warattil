package app.com.warattil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import app.com.warattil.helper.Player;
import app.com.warattil.model.Surah;
import app.com.warattil.utils.AppPreference;
import app.com.warattil.utils.Constants;
import app.com.warattil.utils.DownloadingTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerActivity extends AppCompatActivity implements Constants {

    @BindView(R.id.image_view_setting) ImageView imageViewSetting;
    @BindView(R.id.image_view_reverse) ImageView imageViewReverse;
    @BindView(R.id.image_view_play_pause) ImageView imageViewPlayPause;
    @BindView(R.id.image_view_forward) ImageView imageViewForward;
    @BindView(R.id.image_view_repeat) ImageView imageViewRepeat;
    @BindView(R.id.seek_bar) SeekBar seekBar;
    @BindView(R.id.text_view_time) TextView textViewTime;
    @BindView(R.id.text_view_title) TextView textViewTitle;
    @BindView(R.id.text_view_song) TextView textViewSong;


    private String mReciter;
    private String mFileName;
    private String mUrl;
    private Player player;
    private String mLanguageType;

    private ArrayList<Surah> mSurahs;
    private ArrayList<String> mSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);
        retrieveLanguage();
        applyTypeface();
        getMusicDetails();
    }

    private void retrieveLanguage() {
        mLanguageType = AppPreference.getAppPreference(MediaPlayerActivity.this).getString(PREF_LANGUAGE);
    }

    @SuppressWarnings("unchecked")
    private void getMusicDetails() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mSurahs = (ArrayList<Surah>) getIntent().getSerializableExtra("DownloadedList");
            String mMusicTitle = bundle.getString("songName");
            mReciter = bundle.getString("reciter");
            mFileName = bundle.getString("fileName");
            File file = new File(Environment.getExternalStorageDirectory() + PRAYER_DIR_PATH +"/" + mFileName);
            mUrl = file.getAbsolutePath();
            textViewSong.setText(mMusicTitle);
            if(mReciter.equals(PREF_RECITER_NOURALLAH)) textViewTitle.setText(getString(R.string.nourallah));
            else textViewTitle.setText(getString(R.string.sheikh));
            player = new Player(this, mUrl, seekBar, textViewTime, imageViewPlayPause);
        }
    }

    @OnClick(R.id.image_view_play_pause)
    void playMusic() {
        player.togglePlay();
    }

    @OnClick(R.id.image_view_forward)
    void playNext() {
        getDownloadedList();
        if (mSongList.contains(mFileName)) {
            int index = mSongList.indexOf(mFileName);
            if (index < mSongList.size() - 1) {
                index = index + 1;
                playPreviousAndNextSong(index);
            } else {
                index = 0;
                playPreviousAndNextSong(index);
            }
        }
    }

    @OnClick(R.id.image_view_reverse)
    void playPrevious() {
        getDownloadedList();
        if(mSongList.contains(mFileName)) {
            int index = mSongList.indexOf(mFileName);
            if (index > 0) {
                index = index - 1;
                playPreviousAndNextSong(index);
            } else {
                index = mSongList.size() - 1;
                playPreviousAndNextSong(index);
            }
        }
    }

    @OnClick(R.id.image_view_repeat)
    void playRepeat() {
        player.repeat();
    }

    private void getDownloadedList() {
        mSongList = new ArrayList<>();
        for (int j = 0; j < mSurahs.size(); j++) {
            if (mReciter.equals(PREF_RECITER_SHEIKH)) {
                if (DownloadingTask.checkIsDownload(mSurahs.get(j).getFirstReciter())) {
                    mSongList.add(mSurahs.get(j).getFirstReciter());
                }
            } else if (mReciter.equals(PREF_RECITER_NOURALLAH)) {
                if (DownloadingTask.checkIsDownload(mSurahs.get(j).getSecondReciter())) {
                    mSongList.add(mSurahs.get(j).getSecondReciter());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    private void applyTypeface() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textViewTime, textViewTitle, textViewSong);
    }

    @OnClick(R.id.image_view_setting)
    void clickSetting() {
        startActivity(new Intent(this, SettingActivity.class));
        MediaPlayerActivity.this.finish();
    }

    private void playPreviousAndNextSong(int index) {
        mFileName = mSongList.get(index);
        File file = new File(Environment.getExternalStorageDirectory() + PRAYER_DIR_PATH + "/" + mFileName);
        mUrl = file.getAbsolutePath();
        player.stop();
        player = new Player(this, mUrl, seekBar, textViewTime, imageViewPlayPause);
        songTitleForPreviousForward(index);
    }

    private void songTitleForPreviousForward(int index) {
        ArrayList<String>  mArrayListLanguage = new ArrayList<>();
            for(int position = 0; position < mSurahs.size(); position++) {
                if (mReciter.equals(PREF_RECITER_SHEIKH)) {
                    if (mLanguageType.equals(PREF_LANGUAGE_ENGLISH)) {
                        if(mSongList.contains(mSurahs.get(position).getFirstReciter())) {
                            mArrayListLanguage.add(mSurahs.get(position).getTitleEnglish());
                        }
                    } else {
                        if (mSongList.contains(mSurahs.get(position).getFirstReciter())) {
                            mArrayListLanguage.add(mSurahs.get(position).getTitleArabic());
                        }
                    }

                } else {
                    if (mLanguageType.equals(PREF_LANGUAGE_ENGLISH)) {
                        if (mSongList.contains(mSurahs.get(position).getSecondReciter())) {
                            mArrayListLanguage.add(mSurahs.get(position).getTitleEnglish());
                        }
                    } else {
                        if (mSongList.contains(mSurahs.get(position).getSecondReciter())) {
                            mArrayListLanguage.add(mSurahs.get(position).getTitleArabic());
                        }
                    }
                }
            }
            textViewSong.setText(mArrayListLanguage.get(index));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) MediaPlayerActivity.this.finish();

        return super.onKeyDown(keyCode, event);
    }

}
