package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public final class Rlog {
    private static final boolean SHIP_BUILD;
    private static final boolean USER_BUILD = Build.IS_USER;

    static {
        boolean z = true;
        if (!SystemProperties.getBoolean("ro.product_ship", true) && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
            z = false;
        }
        SHIP_BUILD = z;
    }

    private Rlog() {
    }

    public static int v(String str, String str2) {
        return Log.println_native(1, 2, str, str2);
    }

    public static int v(String str, String str2, Throwable th) {
        return Log.println_native(1, 2, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int d(String str, String str2) {
        return Log.println_native(1, 3, str, str2);
    }

    public static int d(String str, String str2, Throwable th) {
        return Log.println_native(1, 3, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int i(String str, String str2) {
        return Log.println_native(1, 4, str, str2);
    }

    public static int i(String str, String str2, Throwable th) {
        return Log.println_native(1, 4, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int w(String str, String str2) {
        return Log.println_native(1, 5, str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        return Log.println_native(1, 5, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int w(String str, Throwable th) {
        return Log.println_native(1, 5, str, Log.getStackTraceString(th));
    }

    public static int e(String str, String str2) {
        return Log.println_native(1, 6, str, str2);
    }

    public static int e(String str, String str2, Throwable th) {
        return Log.println_native(1, 6, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int println(int i, String str, String str2) {
        return Log.println_native(1, i, str, str2);
    }

    public static boolean isLoggable(String str, int i) {
        return Log.isLoggable(str, i);
    }

    public static String pii(String str, Object obj) {
        String valueOf = String.valueOf(obj);
        if (obj == null || TextUtils.isEmpty(valueOf) || (!SHIP_BUILD && isLoggable(str, 2))) {
            return valueOf;
        }
        return NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String pii(boolean z, Object obj) {
        String valueOf = String.valueOf(obj);
        if (obj == null || TextUtils.isEmpty(valueOf) || (!SHIP_BUILD && z)) {
            return valueOf;
        }
        return NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static String secureHash(byte[] bArr) {
        if (USER_BUILD || SHIP_BUILD) {
            return "****";
        }
        try {
            return Base64.encodeToString(MessageDigest.getInstance("SHA-1").digest(bArr), 11);
        } catch (NoSuchAlgorithmException unused) {
            return "####";
        }
    }
}
