package app.com.warattil.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.com.warattil.database.DBAdapter;
import app.com.warattil.model.Surah;

public class DatabaseHelper {

    private final DBAdapter mDbAdapter ;

    public DatabaseHelper(Context context) {
        this.mDbAdapter = new DBAdapter(context);
    }

    public List<Surah> getSurahList() {
        List<Surah> surahList = new ArrayList<>();
        mDbAdapter.openDatabase();
        SQLiteDatabase mDatabase = mDbAdapter.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery(" SELECT * FROM SURAH ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah surah;
            surah = new Surah(cursor.getString(0), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            surahList.add(surah);
            cursor.moveToNext();
        }
        cursor.close();
        mDbAdapter.closeDatabase();

        return surahList;
    }
}
