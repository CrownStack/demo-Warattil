package app.com.warattil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {

    public static final String DB_NAME = "Quran2.sqlite";
    public static final String DB_LOCATION = "/data/data/com.crownstack.surah/databases/";
    private static final int DB_VERSION = 1;

    private final Context mContext;
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
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
}