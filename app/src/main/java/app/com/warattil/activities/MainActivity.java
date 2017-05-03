package app.com.warattil.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import app.com.warattil.R;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    public static final String LANGUAGE_PREFERENCES = "LanguagePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.text_view_result);
        SharedPreferences _PREFS = getSharedPreferences(LANGUAGE_PREFERENCES, MODE_PRIVATE);
        String storedLanguage = _PREFS.getString(getString(R.string.language), null);
        String storedReciter = _PREFS.getString(getString(R.string.reciter), null);

        if(storedLanguage != null || storedReciter != null) {
            String languageType = _PREFS.getString(getString(R.string.language), null);
            String reciterType  = _PREFS.getString(getString(R.string.reciter), null);
            textViewResult.setText(getString(R.string.language) + ": " + languageType + " \n"
                                 + getString(R.string.reciter)  + ": " + reciterType);
        }
    }
}
