package android.os;

import android.annotation.SystemApi;
import android.util.Log;
import android.util.MutableInt;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

import libcore.util.HexEncoding;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@SystemApi
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

    @FastNative
    public static native String native_get(long j);

    @FastNative
    private static native String native_get(String str, String str2);

    @CriticalNative
    public static native boolean native_get_boolean(long j, boolean z);

    @FastNative
    private static native boolean native_get_boolean(String str, boolean z);

    @CriticalNative
    public static native int native_get_int(long j, int i);

    @FastNative
    private static native int native_get_int(String str, int i);

    @CriticalNative
    public static native long native_get_long(long j, long j2);

    @FastNative
    private static native long native_get_long(String str, long j);

    private static native void native_init$ravenwood(
            Map<String, String> map,
            Predicate<String> predicate,
            Predicate<String> predicate2,
            Runnable runnable);

    private static native void native_report_sysprop_change();

    private static native void native_reset$ravenwood();

    private static native void native_set(String str, String str2);

    private static void onKeyAccess(String key) {}

    public static void init$ravenwood(
            Map<String, String> values,
            Predicate<String> keyReadablePredicate,
            Predicate<String> keyWritablePredicate) {
        native_init$ravenwood(
                values,
                keyReadablePredicate,
                keyWritablePredicate,
                new Runnable() { // from class:
                                 // android.os.SystemProperties$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemProperties.callChangeCallbacks();
                    }
                });
        synchronized (sChangeCallbacks) {
            sChangeCallbacks.clear();
        }
    }

    public static void reset$ravenwood() {
        native_reset$ravenwood();
        synchronized (sChangeCallbacks) {
            sChangeCallbacks.clear();
        }
    }

    private static String native_get(String key) {
        return native_get(key, "");
    }

    @SystemApi
    public static String get(String key) {
        return native_get(key);
    }

    @SystemApi
    public static String get(String key, String def) {
        return native_get(key, def);
    }

    @SystemApi
    public static int getInt(String key, int def) {
        return native_get_int(key, def);
    }

    @SystemApi
    public static long getLong(String key, long def) {
        return native_get_long(key, def);
    }

    @SystemApi
    public static boolean getBoolean(String key, boolean def) {
        return native_get_boolean(key, def);
    }

    public static void set(String key, String val) {
        if (val != null
                && !key.startsWith("ro.")
                && val.getBytes(StandardCharsets.UTF_8).length > 91) {
            throw new IllegalArgumentException(
                    "value of system property '" + key + "' is longer than 91 bytes: " + val);
        }
        native_set(key, val);
    }

    public static void addChangeCallback(Runnable callback) {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                native_add_change_callback();
            }
            sChangeCallbacks.add(callback);
        }
    }

    public static void removeChangeCallback(Runnable callback) {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.contains(callback)) {
                sChangeCallbacks.remove(callback);
            }
        }
    }

    public static void callChangeCallbacks() {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                return;
            }
            ArrayList<Runnable> callbacks = new ArrayList<>(sChangeCallbacks);
            long token = Binder.clearCallingIdentity();
            for (int i = 0; i < callbacks.size(); i++) {
                try {
                    try {
                        callbacks.get(i).run();
                    } catch (Throwable t) {
                        Log.e(TAG, "Exception in SystemProperties change callback", t);
                    }
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }
    }

    public static void reportSyspropChanged() {
        native_report_sysprop_change();
    }

    public static String digestOf(String... keys) {
        Arrays.sort(keys);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            for (String key : keys) {
                String item = key + "=" + get(key) + "\n";
                digest.update(item.getBytes(StandardCharsets.UTF_8));
            }
            return HexEncoding.encodeToString(digest.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SystemProperties() {}

    public static Handle find(String name) {
        long nativeHandle = native_find(name);
        if (nativeHandle == 0) {
            return null;
        }
        return new Handle(nativeHandle);
    }

    public static final class Handle {
        private final long mNativeHandle;

        public String get() {
            return SystemProperties.native_get(this.mNativeHandle);
        }

        public int getInt(int def) {
            return SystemProperties.native_get_int(this.mNativeHandle, def);
        }

        public long getLong(long def) {
            return SystemProperties.native_get_long(this.mNativeHandle, def);
        }

        public boolean getBoolean(boolean def) {
            return SystemProperties.native_get_boolean(this.mNativeHandle, def);
        }

        private Handle(long nativeHandle) {
            this.mNativeHandle = nativeHandle;
        }
    }
}
