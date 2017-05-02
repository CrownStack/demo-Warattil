package app.com.warattil.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import app.com.warattil.model.SurahBean;

public class DBAdapter extends SQLiteOpenHelper {

    public static final String DB_NAME = "Quran2.sqlite";
    public static final String DB_LOCATION = "/data/data/app.com.warattil/databases/";
    public static final int DB_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<SurahBean> getSurahList() {
        List<SurahBean> surahList = new ArrayList<>();
        SurahBean surahBean = null;
        openDatabase();

        Cursor cursor = mDatabase.rawQuery(" SELECT * FROM SURAH ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            surahBean = new SurahBean(cursor.getString(0), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            surahList.add(surahBean);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return surahList;
    }
}