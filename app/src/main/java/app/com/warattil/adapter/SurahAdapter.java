package app.com.warattil.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.com.warattil.BuildConfig;
import app.com.warattil.R;
import app.com.warattil.activities.MediaPlayerActivity;
import app.com.warattil.font.FontHelper;
import app.com.warattil.helper.Message;
import app.com.warattil.helper.NetworkHelper;
import app.com.warattil.model.Surah;
import app.com.warattil.utils.Constants;
import app.com.warattil.utils.DownloadingTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> implements Constants {

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(mSurahs.get(position), position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_language) TextView mTextViewLanguage;
        @BindView(R.id.image_view_downlaod) ImageView image_view_downlaod;
        @BindView(R.id.ll_item)  LinearLayout mLinearLayout;
        int position;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, mTextViewLanguage);
        }

        public void bindData(Surah surah, int position) {
            this.position = position;
            String id = String.valueOf(surah.getId());

            boolean isArabic = mLanguageType.equals(PREF_LANGUAGE_ARABIC);
            mTextViewLanguage.setGravity(isArabic ? Gravity.RIGHT : Gravity.START);
            mTextViewLanguage.setText(id + " - " + (isArabic ? surah.getTitleArabic() : surah.getTitleEnglish()));

            if (surah.isDownloaded()) image_view_downlaod.setImageResource(R.drawable.ic_play);
            else image_view_downlaod.setImageResource(R.drawable.ic_download);

        }

        @OnClick(R.id.ll_item) void clickItem() {
                Surah surah = mSurahs.get(position);

                boolean isSheikh = mReciterType.equals(PREF_RECITER_SHEIKH);
                String prayerUrl = (isSheikh ? surah.getFirstReciter() : surah.getSecondReciter());
                String hostUrl = (isSheikh ? BuildConfig.HOST_URL : BuildConfig.HOST_URL_TWO);

                if (!surah.isDownloaded()) {
                    if(NetworkHelper.isOn(mContext)) DownloadingTask.getInstance(mContext).startDownload(hostUrl, prayerUrl, surah.getId());
                    else Message.message(mContext, mContext.getString(R.string.check_connection));
                } else {
                    String mLanguage;
                    boolean isArabic = mLanguageType.equals(PREF_LANGUAGE_ARABIC);
                    mLanguage = (isArabic ? surah.getTitleArabic() : surah.getTitleEnglish());

                    Intent mediaIntent = new Intent(mContext, MediaPlayerActivity.class);
                    mediaIntent.putExtra("songName", mLanguage);
                    mediaIntent.putExtra("reciter", mReciterType);
                    mediaIntent.putExtra("fileName", prayerUrl);
                    mediaIntent.putExtra("DownloadedList", (Serializable) mSurahs);
                    mContext.startActivity(mediaIntent);
                }
        }
    }

    public void updateList(List<Surah> list) {
        mSurahs = list;
        notifyDataSetChanged();
    }
}
