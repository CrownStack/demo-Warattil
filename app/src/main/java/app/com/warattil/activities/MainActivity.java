package app.com.warattil.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.database.DBAdapter;
import app.com.warattil.model.SurahBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewResult;
    private Button buttonFetch;
    private DBAdapter mDbAdapter;
    private List<SurahBean> mSurahList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.text_view_result);
        buttonFetch = (Button) findViewById(R.id.button_fetch);
        buttonFetch.setOnClickListener(this);

        retrievePreference();
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DBAdapter.DB_NAME);
            String outFileName = DBAdapter.DB_LOCATION + DBAdapter.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity ", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fetch:
            fetchDataFromDatabase();
        }
    }

    public void fetchDataFromDatabase() {
        mDbAdapter = new DBAdapter(this);
        File database = getApplicationContext().getDatabasePath(DBAdapter.DB_NAME);
        if(false == database.exists()) {
            mDbAdapter.getReadableDatabase();
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mSurahList = mDbAdapter.getSurahList();
        for(SurahBean bean : mSurahList) {
            Log.e("mSurahList", bean.getF_name());
        }
    }

    public void retrievePreference() {
        SharedPreferences _PREFS = getSharedPreferences(getString(R.string.LANGUAGE_PREFERENCES), MODE_PRIVATE);
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
