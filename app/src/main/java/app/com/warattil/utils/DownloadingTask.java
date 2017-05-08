package app.com.warattil.utils;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import app.com.warattil.BuildConfig;

public class DownloadingTask implements Constants {

    private DownloadManager mDownloadManager = null;
    private static DownloadingTask sDownloadingTask;


    private DownloadingTask(Context mContext){
        File mDownloadFilePath = new File(Environment.getExternalStorageDirectory() + DOWNLOAD_BASE_PATH);
        if (!mDownloadFilePath.exists()) mDownloadFilePath.mkdirs();

        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadingTask getInstance(Context mContext) {
        if(sDownloadingTask == null) sDownloadingTask = new DownloadingTask(mContext);
        return sDownloadingTask;
    }

    public void startDownload(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(BuildConfig.HOST_URL + url))
                .setTitle(url)
                .setDestinationInExternalPublicDir(PRAYER_DIR_PATH, url + FILE_EXTENSION);
        mDownloadManager.enqueue(request);
    }
}