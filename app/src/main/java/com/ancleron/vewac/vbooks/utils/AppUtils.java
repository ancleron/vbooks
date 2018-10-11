package com.ancleron.vewac.vbooks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import com.ancleron.vewac.vbooks.base.PreferencesManager;

public class AppUtils {

    static PreferencesManager myPref;
    public static String TAG_PERMISSION_CAMERA = "CAMERA";
    public static String TAG_PERMISSION_STORAGE = "STORAGE";
    public static String TAG_PERMISSION_LOCATION = "LOCATION";
    public static String TAG_PERMISSION_CONTACTS = "CONTACTS";
    public static String TAG_PERMISSION_CALL = "CALL";

    // Shared Preferences reference
    static SharedPreferences pref;
    // Editor reference for Shared preferences
    private static SharedPreferences.Editor editor;
    // Shared pref mode
    private static int PRIVATE_MODE = 0;

    public static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null) {
            NetworkInfo result = connec.getActiveNetworkInfo();
            if (result != null && result.isConnectedOrConnecting())
                return true;
            else
                return false;
        }
        return false;
    }

    public static void getVersionNo(Context context, TextView textView) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            textView.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String base64EncodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


}
