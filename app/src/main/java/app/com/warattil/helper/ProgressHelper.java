package app.com.warattil.helper;


import android.app.ProgressDialog;
import android.content.Context;

import app.com.warattil.R;

public class ProgressHelper {

    private static ProgressDialog sProgressDialog;

    public static void start(Context context) {

        if(context != null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setCancelable(false);
            sProgressDialog.setMessage(context.getString(R.string.pleaseWait));
            sProgressDialog.setCanceledOnTouchOutside(false);
            sProgressDialog.show();
        }
    }

    public static void stop() {
        if(sProgressDialog != null) {
            sProgressDialog.dismiss();
        }
    }
}
