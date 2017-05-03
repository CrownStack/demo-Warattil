package app.com.warattil.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import app.com.warattil.helper.Message;
import app.com.warattil.model.SurahBean;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {

    private List<SurahBean> mSurahBeans = new ArrayList<SurahBean>();
    private Context mContext;

    public SurahAdapter(Context context, List<SurahBean> beanList) {
        this.mContext = context;
        this.mSurahBeans = beanList;
    }

    @Override
    public int getItemCount() {
        return mSurahBeans.size();
    }

    @Override
    public SurahAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SurahBean surahBean;
        surahBean = mSurahBeans.get(position);
        String language = surahBean.getTitleEnglish();
        int id = surahBean.getId();
         holder.mTextViewLanguage.setText(id + " " + language);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MediaPlayerActivity.class));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewLanguage;
        private LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mTextViewLanguage = (TextView) view.findViewById(R.id.text_view_item_language);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_item);
            FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, mTextViewLanguage);
        }
    }
}
