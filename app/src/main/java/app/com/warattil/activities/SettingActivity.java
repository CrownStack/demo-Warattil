package app.com.warattil.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.com.warattil.R;
import app.com.warattil.font.FontHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.button_back) Button buttonBack;
    @BindView(R.id.button_next) Button buttonNext;

    @BindView(R.id.text_view_settings) TextView textViewSetting;
    @BindView(R.id.text_view_language) TextView textViewLanguage;
    @BindView(R.id.text_view_reciter) TextView textViewReciter;

    @BindView(R.id.radio_group_language) RadioGroup radioGroupLanguage;
    @BindView(R.id.radio_group_reciter) RadioGroup radioGroupReciter;

    @BindView(R.id.radio_button_english) RadioButton radioButtonEnglish;
    @BindView(R.id.radio_button_arabic) RadioButton radioButtonArabic;
    @BindView(R.id.radio_button_sheikh) RadioButton radioButtonSheikh;
    @BindView(R.id.radio_button_nourallah) RadioButton radioButtonNourallah;

    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        initView();
        sharedPref();
    }

    private void initView() {
        FontHelper.setFontFace(textViewSetting,textViewLanguage, textViewReciter);
        FontHelper.setFontFace(buttonBack, buttonNext);
        FontHelper.setFontFace(radioButtonEnglish, radioButtonArabic, radioButtonSheikh, radioButtonNourallah);

        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        radioGroupLanguage.setOnCheckedChangeListener(this);
        radioGroupReciter.setOnCheckedChangeListener(this);
    }

    private void sharedPref() {
        SharedPreferences mSharedPreferences = getSharedPreferences(getString(R.string.LANGUAGE_PREFERENCES), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_back:
                break;

            case R.id.button_next:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch(checkedId){

            case R.id.radio_button_english:
                mEditor.putString(getString(R.string.language), getString(R.string.english));
                mEditor.commit();
                break;

            case R.id.radio_button_arabic:
                mEditor.putString(getString(R.string.language), getString(R.string.arabic));
                mEditor.commit();
                break;

            case R.id.radio_button_sheikh:
                mEditor.putString(getString(R.string.reciter), getString(R.string.sheikh));
                mEditor.commit();
                break;

            case R.id.radio_button_nourallah:
                mEditor.putString(getString(R.string.reciter), getString(R.string.nourallah));
                mEditor.commit();
                break;
        }
    }
}
