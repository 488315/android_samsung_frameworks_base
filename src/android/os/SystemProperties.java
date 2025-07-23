package android.os;

import android.annotation.SystemApi;
import android.util.Log;
import android.util.MutableInt;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import libcore.util.HexEncoding;

@SystemApi
/* loaded from: classes3.dex */
public class SystemProperties {
    public static final int PROP_NAME_MAX = Integer.MAX_VALUE;
    public static final int PROP_VALUE_MAX = 91;
    private static final String TAG = "SystemProperties";
    private static final boolean TRACK_KEY_ACCESS = false;
    private static final ArrayList<Runnable> sChangeCallbacks = new ArrayList<>();
    private static final HashMap<String, MutableInt> sRoReads = null;

    private static native void native_add_change_callback();

    @FastNative
    private static native long native_find(String str);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native String native_get(long j);

    @FastNative
    private static native String native_get(String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native boolean native_get_boolean(long j, boolean z);

    @FastNative
    private static native boolean native_get_boolean(String str, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native int native_get_int(long j, int i);

    @FastNative
    private static native int native_get_int(String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long native_get_long(long j, long j2);

    @FastNative
    private static native long native_get_long(String str, long j);

    private static native void native_report_sysprop_change();

    private static native void native_set(String str, String str2);

    private static void onKeyAccess(String str) {
    }

    private static String native_get(String str) {
        return native_get(str, "");
    }

    @SystemApi
    public static String get(String str) {
        return native_get(str);
    }

    @SystemApi
    public static String get(String str, String str2) {
        return native_get(str, str2);
    }

    @SystemApi
    public static int getInt(String str, int i) {
        return native_get_int(str, i);
    }

    @SystemApi
    public static long getLong(String str, long j) {
        return native_get_long(str, j);
    }

    @SystemApi
    public static boolean getBoolean(String str, boolean z) {
        return native_get_boolean(str, z);
    }

    public static void set(String str, String str2) {
        if (str2 != null && !str.startsWith("ro.") && str2.getBytes(StandardCharsets.UTF_8).length > 91) {
            throw new IllegalArgumentException("value of system property '" + str + "' is longer than 91 bytes: " + str2);
        }
        native_set(str, str2);
    }

    public static void addChangeCallback(Runnable runnable) {
        ArrayList<Runnable> arrayList = sChangeCallbacks;
        synchronized (arrayList) {
            if (arrayList.size() == 0) {
                native_add_change_callback();
            }
            arrayList.add(runnable);
        }
    }

    public static void removeChangeCallback(Runnable runnable) {
        ArrayList<Runnable> arrayList = sChangeCallbacks;
        synchronized (arrayList) {
            if (arrayList.contains(runnable)) {
                arrayList.remove(runnable);
            }
        }
    }

    private static void callChangeCallbacks() {
        ArrayList<Runnable> arrayList = sChangeCallbacks;
        synchronized (arrayList) {
            if (arrayList.size() == 0) {
                return;
            }
            ArrayList arrayList2 = new ArrayList(arrayList);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            for (int i = 0; i < arrayList2.size(); i++) {
                try {
                    try {
                        ((Runnable) arrayList2.get(i)).run();
                    } catch (Throwable th) {
                        Log.e(TAG, "Exception in SystemProperties change callback", th);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public static void clearChangeCallbacksForTest() {
        ArrayList<Runnable> arrayList = sChangeCallbacks;
        synchronized (arrayList) {
            arrayList.clear();
        }
    }

    public static void reportSyspropChanged() {
        native_report_sysprop_change();
    }

    public static String digestOf(String... strArr) {
        Arrays.sort(strArr);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            for (String str : strArr) {
                messageDigest.update((str + "=" + get(str) + ShaderAssembler.NEWLINE).getBytes(StandardCharsets.UTF_8));
            }
            return HexEncoding.encodeToString(messageDigest.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SystemProperties() {
    }

    public static Handle find(String str) {
        long native_find = native_find(str);
        if (native_find == 0) {
            return null;
        }
        return new Handle(native_find);
    }

    public static final class Handle {
        private final long mNativeHandle;

        public String get() {
            return SystemProperties.native_get(this.mNativeHandle);
        }

        public int getInt(int i) {
            return SystemProperties.native_get_int(this.mNativeHandle, i);
        }

        public long getLong(long j) {
            return SystemProperties.native_get_long(this.mNativeHandle, j);
        }

        public boolean getBoolean(boolean z) {
            return SystemProperties.native_get_boolean(this.mNativeHandle, z);
        }

        private Handle(long j) {
            this.mNativeHandle = j;
        }
    }
}
