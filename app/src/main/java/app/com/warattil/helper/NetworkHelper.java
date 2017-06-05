package app.com.warattil.helper;


import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkHelper {

    public static ConnectivityManager sConnectivityManager;

    public static boolean isOn(final Context context) {
    if( sConnectivityManager == null)
        sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return sConnectivityManager.getActiveNetworkInfo() != null
                && sConnectivityManager.getActiveNetworkInfo().isAvailable()
                && sConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
