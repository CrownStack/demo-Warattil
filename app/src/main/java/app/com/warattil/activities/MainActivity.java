package app.com.warattil.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import app.com.warattil.R;
import app.com.warattil.adapter.SurahAdapter;
import app.com.warattil.font.FontHelper;
import app.com.warattil.model.Surah;
import app.com.warattil.permission.PermissionClass;
import app.com.warattil.utils.AppPreference;
import app.com.warattil.utils.Constants;
import app.com.warattil.utils.GetDetailAsync;
import app.com.warattil.utils.IResponseListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Constants {

    @BindView(R.id.recycle_view_surah) RecyclerView recyclerViewSurah;
    @BindView(R.id.edit_text_search) EditText editTextSearch;
    @BindView(R.id.image_view_setting) ImageView imageViewSetting;
    private final List<Surah> surahs = new ArrayList<>();
    private String mLanguageType;
    private SurahAdapter mAdapter;
    private String mReciterType;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    final int REQUEST_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fetchData();
        retrievePreference();
        PermissionClass permission = new PermissionClass(this);
        if(!permission.checkPermission(permissions)) {
            permission.requestPermission(REQUEST_PERMISSION_CODE, permissions);
        } else {
            initView();
        }
    }

    @OnClick(R.id.image_view_setting)
    void clickSetting(View view) {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }

    private void fetchData() {
        GetDetailAsync detailAsync = new GetDetailAsync(MainActivity.this, new IResponseListener() {
            @Override
            public void success(List<Surah> success) {
                surahs.addAll(success);
            }
        });
        detailAsync.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                }
            }
        }
    }

    private void initView() {
        FontHelper.setFontFace(editTextSearch);
        recyclerViewSurah.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSurah.setLayoutManager(mLayoutManager);
        mAdapter = new SurahAdapter(MainActivity.this, mLanguageType, surahs);
        recyclerViewSurah.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
            public void afterTextChanged(Editable filterString) {
                filter(filterString.toString());
            }
        });
    }

    private void filter(String filterString) {
        List<Surah> filters = new ArrayList<>();
        for(Surah surah : surahs) {
            if(surah.getTitleEnglish().contains(filterString)
                    || surah.getTitleArabic().contains(filterString)
                    || String.valueOf(surah.getId()).contains(filterString)) {
                filters.add(surah);
            }
        }
        mAdapter.updateList(filters);
    }

    private void retrievePreference() {
        mLanguageType = AppPreference.getAppPreference(MainActivity.this).getString(PREF_LANGUAGE);
        mReciterType = AppPreference.getAppPreference(MainActivity.this).getString(PREF_RECITER);
    }
}
