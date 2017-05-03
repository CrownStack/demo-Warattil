package app.com.warattil.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.adapter.SurahAdapter;
import app.com.warattil.model.SurahBean;
import app.com.warattil.utils.GetDetailAsync;
import app.com.warattil.utils.IResponseListener;

public class MainActivity extends AppCompatActivity {

    private List<SurahBean> mBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();
        initView();
        retrievePreference();
    }

    protected void fetchData() {
        GetDetailAsync detailAsync = new GetDetailAsync(MainActivity.this, new IResponseListener() {
            @Override
            public void success(List<SurahBean> success) {
                mBeanList.addAll(success);
            }
        });

        detailAsync.execute();
    }

    private void initView() {
        RecyclerView recyclerViewSurah = (RecyclerView) findViewById(R.id.recycle_view_surah);
        recyclerViewSurah.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSurah.setLayoutManager(mLayoutManager);
        SurahAdapter adapter = new SurahAdapter(MainActivity.this, mBeanList);
        recyclerViewSurah.setAdapter(adapter);
    }

    public void retrievePreference() {
        SharedPreferences _PREFS = getSharedPreferences(getString(R.string.LANGUAGE_PREFERENCES), MODE_PRIVATE);
        String storedLanguage = _PREFS.getString(getString(R.string.language), null);
        String storedReciter = _PREFS.getString(getString(R.string.reciter), null);

        if(storedLanguage != null || storedReciter != null) {
            String languageType = _PREFS.getString(getString(R.string.language), null);
            String reciterType  = _PREFS.getString(getString(R.string.reciter), null);
        }
    }
}
