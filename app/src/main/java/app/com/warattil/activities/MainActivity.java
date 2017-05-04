package app.com.warattil.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.adapter.SurahAdapter;
import app.com.warattil.font.FontHelper;
import app.com.warattil.model.SurahBean;
import app.com.warattil.utils.GetDetailAsync;
import app.com.warattil.utils.IResponseListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view_surah) RecyclerView recyclerViewSurah;
    @BindView(R.id.button_previous) Button buttonPrevious;
    @BindView(R.id.button_play) Button buttonPlay;
    @BindView(R.id.button_next) Button buttonNext;
    @BindView(R.id.button_setting) Button buttonSetting;

    @BindView(R.id.edit_text_search) EditText editTextSearch;

    private final List<SurahBean> mBeanList = new ArrayList<>();

    private String mLanguageType;

    private SurahAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fetchData();
        retrievePreference();
        initView();
    }

    private void fetchData() {
        GetDetailAsync detailAsync = new GetDetailAsync(MainActivity.this, new IResponseListener() {
            @Override
            public void success(List<SurahBean> success) {
                mBeanList.addAll(success);
            }
        });

        detailAsync.execute();
    }

    private void initView() {
        FontHelper.setFontFace(editTextSearch);
        recyclerViewSurah.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSurah.setLayoutManager(mLayoutManager);
        adapter = new SurahAdapter(MainActivity.this, mLanguageType,  mBeanList);
        recyclerViewSurah.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchFilter();
    }

    private void searchFilter() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        List<SurahBean> temp = new ArrayList<>();
        for(SurahBean bean : mBeanList) {
            if(bean.getTitleEnglish().contains(text)) {
                temp.add(bean);
            }
        }
        adapter.updateList(temp);
    }

    private void retrievePreference() {
        SharedPreferences _PREFS = getSharedPreferences(getString(R.string.LANGUAGE_PREFERENCES), MODE_PRIVATE);
        String storedLanguage = _PREFS.getString(getString(R.string.language), null);
        String storedReciter = _PREFS.getString(getString(R.string.reciter), null);

        if(storedLanguage != null || storedReciter != null) {
             mLanguageType = _PREFS.getString(getString(R.string.language), null);
            String reciterType  = _PREFS.getString(getString(R.string.reciter), null);
        }
    }
}
