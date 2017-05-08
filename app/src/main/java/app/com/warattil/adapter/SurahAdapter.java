package app.com.warattil.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.activities.MediaPlayerActivity;
import app.com.warattil.font.FontHelper;
import app.com.warattil.model.SurahBean;
import app.com.warattil.utils.DownloadingTask;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {

    private List<SurahBean> mSurahBeans = new ArrayList<>();

    private final String mLanguageType;
    private final Context mContext;

    public SurahAdapter(Context context, String languageType, List<SurahBean> beanList) {
        this.mContext = context;
        this.mLanguageType = languageType;
        this.mSurahBeans = beanList;
    }

    @Override
    public int getItemCount() {
        return mSurahBeans.size();
    }

    @Override
    public SurahAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String language ;
        final SurahBean surahBean = mSurahBeans.get(position);
        String id = String.valueOf(surahBean.getId());
        if(mLanguageType.equals("PREF_LANGUAGE_ARABIC")) {
            language = surahBean.getTitleArabic();
            holder.mTextViewLanguage.setGravity(Gravity.RIGHT);
            holder.mTextViewLanguage.setText(id + " - " +language);
        } else {
            holder.mTextViewLanguage.setGravity(Gravity.START);
            language = surahBean.getTitleEnglish();
            holder.mTextViewLanguage.setText(id + " - " + language);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewLanguage;
        private final LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mTextViewLanguage = (TextView) view.findViewById(R.id.text_view_item_language);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_item);
            FontHelper.setFontFace(mTextViewLanguage);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mContext.startActivity(new Intent(mContext, MediaPlayerActivity.class));
//                DownloadingTask.getInstance(mContext).startDownload(surahBean.getF_name2());
                }
            });
        }

    }

    public void updateList(List<SurahBean> list) {
        mSurahBeans = list;
        notifyDataSetChanged();
    }
}
