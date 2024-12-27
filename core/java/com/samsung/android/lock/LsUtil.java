package com.samsung.android.lock;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes6.dex */
public class LsUtil {
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final String EOL = "\n";
    private static final int HASH_LENGTH = 5;
    private static final String SEPARATOR = " ";
    private static final String TAG = "LsUtil";

    public static String makeLog(String msg) {
        return getTimeForLog() + SEPARATOR + msg;
    }

    public static String gethashStr(byte[] in) {
        if (in == null) {
            return "null";
        }
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(in);
            return Arrays.toString(Arrays.copyOf(sh.digest(), 5));
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "gethashStr() failed. " + e);
            return "null";
        }
    }

    public static boolean isShipBuild() {
        return "true".equals(SystemProperties.get("ro.product_ship", "false"));
    }

    public static boolean isDevBuild() {
        return Build.IS_USERDEBUG || Build.IS_ENG;
    }

    public static String getTimeForLog() {
        return getTimeForLog(System.currentTimeMillis());
    }

    public static String getTimeForLog(long timestamp) {
        Object date = new Date(timestamp);
        Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String getTimeForSummary(long timestamp) {
        Object date = new Date(timestamp);
        Format dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String getTimeForFilename(long timestamp) {
        Object date = new Date(timestamp);
        Format dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String timestampToString(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }
}
