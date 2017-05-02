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

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private Button buttonBack, buttonNext;
    private TextView textViewSetting, textViewLanguage, textViewReciter;
    private RadioButton radioButtonEnglish, radioButtonArabic, radioButtonSheikh, radioButtonNourallah;
    private RadioGroup radioGroupLanguage, radioGroupReciter;
    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;

    public static final String LANGUAGE_PREFERENCES = "LanguagePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        sharedPref();
    }

    private void initView() {
        buttonBack = (Button) findViewById(R.id.button_back);
        buttonNext = (Button) findViewById(R.id.button_next);

        textViewSetting = (TextView) findViewById(R.id.text_view_settings);
        textViewLanguage = (TextView) findViewById(R.id.text_view_language);
        textViewReciter = (TextView) findViewById(R.id.text_view_reciter);

        radioGroupLanguage = (RadioGroup) findViewById(R.id.radio_group_language);
        radioGroupReciter = (RadioGroup) findViewById(R.id.radio_group_reciter);

        radioButtonEnglish = (RadioButton) findViewById(R.id.radio_button_english);
        radioButtonArabic = (RadioButton) findViewById(R.id.radio_button_arabic);
        radioButtonSheikh = (RadioButton) findViewById(R.id.radio_button_sheikh);
        radioButtonNourallah = (RadioButton) findViewById(R.id.radio_button_nourallah);

        FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, textViewSetting,textViewLanguage, textViewReciter);
        FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, buttonBack, buttonNext);
        FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, radioButtonEnglish, radioButtonArabic, radioButtonSheikh, radioButtonNourallah);

        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        radioGroupLanguage.setOnCheckedChangeListener(this);
        radioGroupReciter.setOnCheckedChangeListener(this);
    }

    public void sharedPref() {
        mSharedPreferences = getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void onClick(View v) {

        switch (v.getId()){

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
                editor.putString(getString(R.string.language), getString(R.string.english));
                editor.commit();
                break;

            case R.id.radio_button_arabic:
                editor.putString(getString(R.string.language), getString(R.string.arabic));
                editor.commit();
                break;

            case R.id.radio_button_sheikh:
                editor.putString(getString(R.string.reciter), getString(R.string.sheikh));
                editor.commit();
                break;

            case R.id.radio_button_nourallah:
                editor.putString(getString(R.string.reciter), getString(R.string.nourallah));
                editor.commit();
                break;
        }
    }
}
