package com.android.server.am;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.provider.Settings;
import android.util.ArrayMap;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public abstract class ActivityManagerUtils {
  public static Integer sAndroidIdHash;
  public static final ArrayMap sHashCache = new ArrayMap();
  public static String sInjectedAndroidId;

  public static void injectAndroidIdForTest(String str) {
    sInjectedAndroidId = str;
    sAndroidIdHash = null;
  }

  public static int getAndroidIdHash() {
    if (sAndroidIdHash == null) {
      ContentResolver contentResolver = ActivityThread.currentApplication().getContentResolver();
      String stringForUser =
          Settings.Secure.getStringForUser(
              contentResolver, "android_id", contentResolver.getUserId());
      String str = sInjectedAndroidId;
      if (str != null) {
        stringForUser = str;
      }
      sAndroidIdHash = Integer.valueOf(getUnsignedHashUnCached(stringForUser));
    }
    return sAndroidIdHash.intValue();
  }

  public static int getUnsignedHashCached(String str) {
    ArrayMap arrayMap = sHashCache;
    synchronized (arrayMap) {
      Integer num = (Integer) arrayMap.get(str);
      if (num != null) {
        return num.intValue();
      }
      int unsignedHashUnCached = getUnsignedHashUnCached(str);
      arrayMap.put(str.intern(), Integer.valueOf(unsignedHashUnCached));
      return unsignedHashUnCached;
    }
  }

  public static int getUnsignedHashUnCached(String str) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.update(str.getBytes());
      return unsignedIntFromBytes(messageDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static int unsignedIntFromBytes(byte[] bArr) {
    return (extractByte(bArr, 3)
            | extractByte(bArr, 0)
            | extractByte(bArr, 1)
            | extractByte(bArr, 2))
        & Integer.MAX_VALUE;
  }

  public static int extractByte(byte[] bArr, int i) {
    return (bArr[i] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) << (i * 8);
  }

  public static boolean shouldSamplePackageForAtom(String str, float f) {
    if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return false;
    }
    return f >= 1.0f
        || ((double) (getUnsignedHashCached(str) ^ getAndroidIdHash())) / 2.147483647E9d
            <= ((double) f);
  }

  public static int hashComponentNameForAtom(String str) {
    return getUnsignedHashUnCached(str) ^ getAndroidIdHash();
  }

  public static void logUnsafeIntentEvent(int i, int i2, Intent intent, String str, boolean z) {
    FrameworkStatsLog.write(
        FrameworkStatsLog.UNSAFE_INTENT_EVENT_REPORTED,
        i,
        i2,
        intent.getComponent() == null ? null : intent.getComponent().flattenToString(),
        intent.getPackage(),
        intent.getAction(),
        intent.getCategories() == null
            ? new String[0]
            : (String[])
                intent
                    .getCategories()
                    .toArray(
                        new IntFunction() { // from class:
                                            // com.android.server.am.ActivityManagerUtils$$ExternalSyntheticLambda0
                          @Override // java.util.function.IntFunction
                          public final Object apply(int i3) {
                            String[] lambda$logUnsafeIntentEvent$0;
                            lambda$logUnsafeIntentEvent$0 =
                                ActivityManagerUtils.lambda$logUnsafeIntentEvent$0(i3);
                            return lambda$logUnsafeIntentEvent$0;
                          }
                        }),
        str,
        intent.getScheme(),
        z);
  }

  public static /* synthetic */ String[] lambda$logUnsafeIntentEvent$0(int i) {
    return new String[i];
  }
}
