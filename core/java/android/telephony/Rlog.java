package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public final class Rlog {
  private static final boolean USER_BUILD = Build.IS_USER;

  private Rlog() {}

  /* renamed from: v */
  public static int m83v(String tag, String msg) {
    return Log.println_native(1, 2, tag, msg);
  }

  /* renamed from: v */
  public static int m84v(String tag, String msg, Throwable tr) {
    return Log.println_native(1, 2, tag, msg + '\n' + Log.getStackTraceString(tr));
  }

  /* renamed from: d */
  public static int m77d(String tag, String msg) {
    return Log.println_native(1, 3, tag, msg);
  }

  /* renamed from: d */
  public static int m78d(String tag, String msg, Throwable tr) {
    return Log.println_native(1, 3, tag, msg + '\n' + Log.getStackTraceString(tr));
  }

  /* renamed from: i */
  public static int m81i(String tag, String msg) {
    return Log.println_native(1, 4, tag, msg);
  }

  /* renamed from: i */
  public static int m82i(String tag, String msg, Throwable tr) {
    return Log.println_native(1, 4, tag, msg + '\n' + Log.getStackTraceString(tr));
  }

  /* renamed from: w */
  public static int m85w(String tag, String msg) {
    return Log.println_native(1, 5, tag, msg);
  }

  /* renamed from: w */
  public static int m86w(String tag, String msg, Throwable tr) {
    return Log.println_native(1, 5, tag, msg + '\n' + Log.getStackTraceString(tr));
  }

  /* renamed from: w */
  public static int m87w(String tag, Throwable tr) {
    return Log.println_native(1, 5, tag, Log.getStackTraceString(tr));
  }

  /* renamed from: e */
  public static int m79e(String tag, String msg) {
    return Log.println_native(1, 6, tag, msg);
  }

  /* renamed from: e */
  public static int m80e(String tag, String msg, Throwable tr) {
    return Log.println_native(1, 6, tag, msg + '\n' + Log.getStackTraceString(tr));
  }

  public static int println(int priority, String tag, String msg) {
    return Log.println_native(1, priority, tag, msg);
  }

  public static boolean isLoggable(String tag, int level) {
    return Log.isLoggable(tag, level);
  }

  public static String pii(String tag, Object pii) {
    String val = String.valueOf(pii);
    if (pii == null || TextUtils.isEmpty(val) || isLoggable(tag, 2)) {
      return val;
    }
    return NavigationBarInflaterView.SIZE_MOD_START
        + secureHash(val.getBytes())
        + NavigationBarInflaterView.SIZE_MOD_END;
  }

  public static String pii(boolean enablePiiLogging, Object pii) {
    String val = String.valueOf(pii);
    if (pii == null || TextUtils.isEmpty(val) || enablePiiLogging) {
      return val;
    }
    return NavigationBarInflaterView.SIZE_MOD_START
        + secureHash(val.getBytes())
        + NavigationBarInflaterView.SIZE_MOD_END;
  }

  private static String secureHash(byte[] input) {
    if (USER_BUILD) {
      return "****";
    }
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      byte[] result = messageDigest.digest(input);
      return Base64.encodeToString(result, 11);
    } catch (NoSuchAlgorithmException e) {
      return "####";
    }
  }
}
