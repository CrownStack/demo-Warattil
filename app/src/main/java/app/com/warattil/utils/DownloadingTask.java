package app.com.warattil.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import java.io.File;

public class DownloadingTask implements Constants {

    private DownloadManager mDownloadManager = null;
    private static DownloadingTask sDownloadingTask;
    private Context context;

    private DownloadingTask(Context mContext){
        File mDownloadFilePath = new File(Environment.getExternalStorageDirectory() + DOWNLOAD_BASE_PATH);
        if (!mDownloadFilePath.exists()) mDownloadFilePath.mkdirs();
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = mContext;
    }

    public static DownloadingTask getInstance(Context mContext) {
        if(sDownloadingTask == null) sDownloadingTask = new DownloadingTask(mContext);
        return sDownloadingTask;
    }

    public void startFirstDownload(String hostUrl, final String url, final int id) {
        final long enqueue ;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(hostUrl + url))
                .setTitle(url)
                .setDestinationInExternalPublicDir(PRAYER_DIR_PATH, url);
        enqueue = mDownloadManager.enqueue(request);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = mDownloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            Intent downloadIntent = new Intent();
                            downloadIntent.setAction(DOWNLOADED_ACTION);
                            downloadIntent.putExtra(DOWNLOAD_ID, url);
                            context.sendBroadcast(downloadIntent);
                        }
                    }
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    public static boolean checkIsDownload(String filename){
        File file = new File(Environment.getExternalStorageDirectory() + PRAYER_DIR_PATH +"/" + filename);
        if(file.exists())
            return true;
        return false;
    }
}
