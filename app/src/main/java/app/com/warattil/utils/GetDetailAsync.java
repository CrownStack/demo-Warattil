package app.com.warattil.utils;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import app.com.warattil.R;
import app.com.warattil.database.DBAdapter;
import app.com.warattil.helper.ProgressHelper;
import app.com.warattil.model.Surah;

public class GetDetailAsync extends AsyncTask<String, Void, String> {

    private final IResponseListener mResponseListener;
    private final Context mContext;

    public GetDetailAsync(Context context, IResponseListener responseListener) {
        this.mContext = context;
        this.mResponseListener = responseListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ProgressHelper.start(mContext);
    }

    @Override
    protected String doInBackground(String... params) {
        fetchDataFromDatabase();

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ProgressHelper.stop();
    }

    private void fetchDataFromDatabase() {
        DBAdapter mDbAdapter = new DBAdapter(mContext);
        File database = mContext.getDatabasePath(DBAdapter.DB_NAME);
        if(!database.exists()) {
            mDbAdapter.getReadableDatabase();
            if(copyDatabase()) {
                Log.e("success", mContext.getString(R.string.copy_success));
            } else {
                Log.e("error", mContext.getString(R.string.copy_error));

                return;
            }
        }
        List<Surah> mSurahList = mDbAdapter.getSurahList();
        mResponseListener.success(mSurahList);
    }

    private boolean copyDatabase() {
        try {
            InputStream inputStream = mContext.getAssets().open(DBAdapter.DB_NAME);
            String outFileName = DBAdapter.DB_LOCATION + DBAdapter.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("SongListActivity ", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
