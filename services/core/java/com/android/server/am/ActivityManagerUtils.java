package com.android.server.am;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.provider.Settings;
import android.util.ArrayMap;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ActivityManagerUtils {
    public static Integer sAndroidIdHash;
    public static final ArrayMap sHashCache = new ArrayMap();
    public static String sInjectedAndroidId;

    public static int extractByte(int i, byte[] bArr) {
        return (bArr[i] & 255) << (i * 8);
    }

    public static int getAndroidIdHash() {
        if (sAndroidIdHash == null) {
            ContentResolver contentResolver =
                    ActivityThread.currentApplication().getContentResolver();
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
            try {
                Integer num = (Integer) arrayMap.get(str);
                if (num != null) {
                    return num.intValue();
                }
                int unsignedHashUnCached = getUnsignedHashUnCached(str);
                arrayMap.put(str.intern(), Integer.valueOf(unsignedHashUnCached));
                return unsignedHashUnCached;
            } catch (Throwable th) {
                throw th;
            }
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

    public static void injectAndroidIdForTest(String str) {
        sInjectedAndroidId = str;
        sAndroidIdHash = null;
    }

    public static boolean shouldSamplePackageForAtom(float f, String str) {
        if (f <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return false;
        }
        return f >= 1.0f
                || ((double) (getUnsignedHashCached(str) ^ getAndroidIdHash())) / 2.147483647E9d
                        <= ((double) f);
    }

    public static int unsignedIntFromBytes(byte[] bArr) {
        return (extractByte(3, bArr)
                        | extractByte(0, bArr)
                        | extractByte(1, bArr)
                        | extractByte(2, bArr))
                & Integer.MAX_VALUE;
    }
}
