package app.com.warattil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import app.com.warattil.model.Surah;
import app.com.warattil.utils.DownloadingTask;

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
        String language ;
        final Surah surah = mSurahs.get(position);
        String id = String.valueOf(surah.getId());
        if(mLanguageType.equals("PREF_LANGUAGE_ARABIC")) {
            language = surah.getTitleArabic();
            holder.mTextViewLanguage.setGravity(Gravity.RIGHT);
            holder.mTextViewLanguage.setText(id + " - " +language);
        } else {
            holder.mTextViewLanguage.setGravity(Gravity.START);
            language = surah.getTitleEnglish();
            holder.mTextViewLanguage.setText(id + " - " + language);
        }
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciterType.equals("PREF_RECITER_SHEIKH")) {
                    DownloadingTask.getInstance(mContext).startFirstDownload(surah.getFirstReciter());
                } else if(mReciterType.equals("PREF_RECITER_NOURALLAH")) {
                    DownloadingTask.getInstance(mContext).startSecondDownload(surah.getSecondReciter());
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewLanguage;
        private final ImageView mImageViewDownload;
        private final LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mTextViewLanguage = (TextView) view.findViewById(R.id.text_view_item_language);
            mImageViewDownload = (ImageView) view.findViewById(R.id.image_view_downlaod);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_item);
            FontHelper.setFontFace(mTextViewLanguage);
        }
    }

    public void updateList(List<Surah> list) {
        mSurahs = list;
        notifyDataSetChanged();
    }
}
