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
import app.com.warattil.utils.AppPreference;
import app.com.warattil.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, Constants{

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
    }

    private void initView() {
        FontHelper.setFontFace(textViewSetting,textViewLanguage, textViewReciter);
        FontHelper.setFontFace(buttonBack, buttonNext);
        FontHelper.setFontFace(radioButtonEnglish, radioButtonArabic, radioButtonSheikh, radioButtonNourallah);

        radioGroupLanguage.setOnCheckedChangeListener(this);
        radioGroupReciter.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.button_next)
    void clickNext(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @OnClick(R.id.button_back)
    void clickBack(View view){
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch(checkedId){

            case R.id.radio_button_english:
                AppPreference.getAppPreference(SettingActivity.this).putString(PREF_LANGUAGE, PREF_LANGUAGE_ENGLISH);
                break;

            case R.id.radio_button_arabic:
                AppPreference.getAppPreference(SettingActivity.this).putString(PREF_LANGUAGE, PREF_LANGUAGE_ARABIC);
                break;

            case R.id.radio_button_sheikh:
                AppPreference.getAppPreference(SettingActivity.this).putString(PREF_RECITER, PREF_RECITER_SHEIKH);
                break;

            case R.id.radio_button_nourallah:
                AppPreference.getAppPreference(SettingActivity.this).putString(PREF_RECITER, PREF_RECITER_NOURALLAH);
                break;
        }
    }
}
