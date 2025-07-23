package com.android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.SystemProperties;
import android.telecom.PhoneAccount;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.android.internal.telephony.util.TelephonyUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public final class Rlog {
    private static final boolean SHIP_BUILD;
    private static final boolean USER_BUILD = TelephonyUtils.IS_USER;

    static {
        boolean z = true;
        if (!SystemProperties.getBoolean("ro.product_ship", true) && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
            z = false;
        }
        SHIP_BUILD = z;
    }

    private Rlog() {
    }

    private static int log(int i, String str, String str2) {
        return Log.logToRadioBuffer(i, str, str2);
    }

    public static int v(String str, String str2) {
        return log(2, str, str2);
    }

    public static int v(String str, String str2, Throwable th) {
        return log(2, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int d(String str, String str2) {
        return log(3, str, str2);
    }

    public static int d(String str, String str2, Throwable th) {
        return log(3, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int i(String str, String str2) {
        return log(4, str, str2);
    }

    public static int i(String str, String str2, Throwable th) {
        return log(4, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int w(String str, String str2) {
        return log(5, str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        return log(5, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int w(String str, Throwable th) {
        return log(5, str, Log.getStackTraceString(th));
    }

    public static int e(String str, String str2) {
        return log(6, str, str2);
    }

    public static int e(String str, String str2, Throwable th) {
        return log(6, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int println(int i, String str, String str2) {
        return log(i, str, str2);
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

    public static String piiHandle(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof Uri) {
            Uri uri = (Uri) obj;
            String scheme = uri.getScheme();
            if (!TextUtils.isEmpty(scheme)) {
                sb.append(scheme);
                sb.append(":");
            }
            String schemeSpecificPart = uri.getSchemeSpecificPart();
            if (PhoneAccount.SCHEME_TEL.equals(scheme)) {
                obfuscatePhoneNumber(sb, schemeSpecificPart);
            } else if ("sip".equals(scheme)) {
                for (int i = 0; i < schemeSpecificPart.length(); i++) {
                    char charAt = schemeSpecificPart.charAt(i);
                    if (charAt != '@' && charAt != '.') {
                        charAt = '*';
                    }
                    sb.append(charAt);
                }
            } else {
                sb.append("***");
            }
        } else if (obj instanceof String) {
            obfuscatePhoneNumber(sb, (String) obj);
        }
        return sb.toString();
    }

    private static void obfuscatePhoneNumber(StringBuilder sb, String str) {
        int dialableCount = getDialableCount(str) - ((USER_BUILD || SHIP_BUILD) ? 0 : 2);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            boolean isDialable = PhoneNumberUtils.isDialable(charAt);
            if (isDialable) {
                dialableCount--;
            }
            sb.append((!isDialable || dialableCount < 0) ? Character.valueOf(charAt) : "*");
        }
    }

    private static int getDialableCount(String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (PhoneNumberUtils.isDialable(c)) {
                i++;
            }
        }
        return i;
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
