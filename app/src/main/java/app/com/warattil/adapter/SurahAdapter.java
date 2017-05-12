package app.com.warattil.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.com.warattil.BuildConfig;
import app.com.warattil.R;
import app.com.warattil.activities.MediaPlayerActivity;
import app.com.warattil.font.FontHelper;
import app.com.warattil.model.Surah;
import app.com.warattil.utils.DownloadingTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static app.com.warattil.utils.Constants.PRAYER_DIR_PATH;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {

    private List<Surah> mSurahs = new ArrayList<>();
    private final String mLanguageType;
    private final String mReciterType;
    private final Context mContext;

    public SurahAdapter(Context context, String languageType, String reciterType, List<Surah> surahs) {
        this.mContext = context;
        this.mLanguageType = languageType;
        this.mReciterType = reciterType;
        this.mSurahs = surahs;
    }

    @Override
    public int getItemCount() {
        return mSurahs.size();
    }

    @Override
    public SurahAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(mSurahs.get(position),position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_language) TextView mTextViewLanguage;
        @BindView(R.id.image_view_downlaod) ImageView image_view_downlaod;
        @BindView(R.id.ll_item)  LinearLayout mLinearLayout;
        int position;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            FontHelper.setFontFace(mTextViewLanguage);
        }

        public void bindData(Surah surah, int position) {
            this.position = position;
            String id = String.valueOf(surah.getId());
            if(mLanguageType.equals("PREF_LANGUAGE_ARABIC")) {
                mTextViewLanguage.setGravity(Gravity.RIGHT);
                mTextViewLanguage.setText(id + " - " + surah.getTitleArabic());
                if (surah.isDownloaded()) image_view_downlaod.setImageResource(R.drawable.ic_play);
                else image_view_downlaod.setImageResource(R.drawable.ic_download);

            } else {
                mTextViewLanguage.setGravity(Gravity.START);
                mTextViewLanguage.setText(id + " - " + surah.getTitleEnglish());
                if (surah.isDownloaded()) image_view_downlaod.setImageResource(R.drawable.ic_play);
                else image_view_downlaod.setImageResource(R.drawable.ic_download);
            }
        }

        @OnClick(R.id.ll_item) void clickItem(View view) {
            Surah surah = mSurahs.get(position);
            String prayerUrl = null;
            String hostUrl = null;

            if (mReciterType.equals("PREF_RECITER_SHEIKH")) {
                prayerUrl = surah.getFirstReciter();
                hostUrl = BuildConfig.HOST_URL;
            } else if (mReciterType.equals("PREF_RECITER_NOURALLAH")) {
                prayerUrl = surah.getSecondReciter();
                hostUrl = BuildConfig.HOST_URL_TWO;
            }
            if (!surah.isDownloaded()) {
                DownloadingTask.getInstance(mContext).startFirstDownload(hostUrl, prayerUrl, surah.getId());
            } else {
                String mLanguage;
                if (mLanguageType.equals("PREF_LANGUAGE_ARABIC")) {
                    mLanguage = surah.getTitleArabic();
                } else {
                    mLanguage = surah.getTitleEnglish();
                }
                Intent mediaIntent = new Intent(mContext, MediaPlayerActivity.class);
                mediaIntent.putExtra("songName", mLanguage);
                mediaIntent.putExtra("reciter", mReciterType);
                mediaIntent.putExtra("fileName", prayerUrl);
                mContext.startActivity(mediaIntent);
            }
        }
    }

    public void updateList(List<Surah> list) {
        mSurahs = list;
        notifyDataSetChanged();
    }
}
