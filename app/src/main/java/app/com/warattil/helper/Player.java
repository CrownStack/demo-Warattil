package app.com.warattil.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;
import app.com.warattil.R;

public class Player {

    private AudioManager mAudioManager;
    private Context mContext;
    private String mUrl;
    private SeekBar mSeekbar;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler;
    private TextView textViewTotalTime;
    private ImageView imagePlayPause;
    private boolean isPlaybackComplete;
    private boolean isShuffle = false;


    public Player(Context context, String url, SeekBar seekBar, TextView textViewTotalTime, ImageView imagePlayPause){
        mContext = context;
        mUrl = url;
        mSeekbar = seekBar;
        this.imagePlayPause = imagePlayPause;
        this.textViewTotalTime = textViewTotalTime;
        mAudioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        preparePlayer();
        mMediaPlayer.setOnPreparedListener(onPreparedListener);
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
        if (this.imagePlayPause != null) this.imagePlayPause.setClickable(false);

        setCallListener();
    }


    private void setCallListener() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                if(null == mMediaPlayer) return;

                if(state == TelephonyManager.CALL_STATE_RINGING) {
                        mMediaPlayer.pause();
                }
                if(state == TelephonyManager.CALL_STATE_OFFHOOK || state == TelephonyManager.CALL_STATE_IDLE) {
                        mMediaPlayer.start();
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void preparePlayer() {
        int result = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Uri uri = Uri.parse(mUrl);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(mContext, uri);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mSeekbar != null) {
                mHandler = new Handler();
            }
            try {
                mMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
            mMediaPlayer.seekTo(0);
        if (mSeekbar != null) {
            mSeekbar.setMax(mMediaPlayer.getDuration());
            mSeekbar.setProgress(0);
            mSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        }
        if (imagePlayPause != null) {
            imagePlayPause.setClickable(true);
            imagePlayPause.setImageResource(R.drawable.ic_pause);
        }
        if (mHandler != null) mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    public void repeat() {
        if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
        if (mSeekbar != null) {
            mSeekbar.setMax(mMediaPlayer.getDuration());
            mSeekbar.setProgress(0);
            mSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        }
        if (imagePlayPause != null) {
            imagePlayPause.setClickable(true);
            imagePlayPause.setImageResource(R.drawable.ic_pause);
        }
        if (mHandler != null) mHandler.postDelayed(mUpdateTimeTask, 100);
            mMediaPlayer.setLooping(true);
    }

    public void stop() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (mHandler != null) mHandler.removeCallbacks(mUpdateTimeTask);
            if (mAudioManager != null) mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    public void togglePlay() {

        if (null == mMediaPlayer) {preparePlayer(); return;}


        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
            imagePlayPause.setImageResource(R.drawable.ic_play);
        }
        else {
            mMediaPlayer.start();
            imagePlayPause.setImageResource(R.drawable.ic_pause);
            if (isPlaybackComplete) {
                isPlaybackComplete = false;
                if (mHandler != null)
                    mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mMediaPlayer.getDuration();
            int currentDuration = mMediaPlayer.getCurrentPosition();
            mSeekbar.setProgress(currentDuration);
            if (totalDuration > currentDuration) mHandler.postDelayed(this, 100);
        }
    };

    public void seekTo(int time) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.seekTo(time);
        else mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
    }

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mMediaPlayer != null) textViewTotalTime.setText(getTimeString(mMediaPlayer.getDuration()));
            if (imagePlayPause != null) imagePlayPause.setClickable(true);
               start();
        }
    };

    public String getTimeString(long duration) {
        int minutes = (int) Math.floor(duration / 1000 / 60);
        int seconds = (int) ((duration / 1000) - (minutes * 60));
        return minutes + ":" + String.format("%02d", seconds);
    }

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mSeekbar != null) {
                mSeekbar.setMax(mp.getDuration());
                mSeekbar.setProgress(0);
                if (mHandler != null) mHandler.removeCallbacks(mUpdateTimeTask);
                imagePlayPause.setImageResource(R.drawable.ic_play);
                mAudioManager.abandonAudioFocus(audioFocusChangeListener);
                isPlaybackComplete = true;
            }
        }
    };

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser)
                seekTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
            }
        }
    };
}
