package com.samsung.android.lock;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String makeLog(String str) {
        return getTimeForLog() + SEPARATOR + str;
    }

    public static String gethashStr(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return "[null]";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return Arrays.toString(Arrays.copyOf(messageDigest.digest(), 5));
        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, "gethashStr() failed. " + e);
            return "[null]";
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

    public static String getTimeForLog(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH).format((Object) new Date(j));
    }

    public static String getTimeForSummary(long j) {
        return new SimpleDateFormat("yyyyMMdd HH:mm", Locale.ENGLISH).format((Object) new Date(j));
    }

    public static String getTimeForFilename(long j) {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format((Object) new Date(j));
    }

    public static String timestampToString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(j));
    }
}
