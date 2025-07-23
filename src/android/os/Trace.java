package android.os;

import android.annotation.SystemApi;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

/* loaded from: classes3.dex */
public final class Trace {
    public static final int MAX_SECTION_NAME_LEN = 127;
    private static final String TAG = "Trace";
    public static final long TRACE_TAG_ACTIVITY_MANAGER = 64;
    public static final long TRACE_TAG_ADB = 4194304;

    @SystemApi
    public static final long TRACE_TAG_AIDL = 16777216;
    public static final long TRACE_TAG_ALWAYS = 1;
    public static final long TRACE_TAG_APP = 4096;
    public static final long TRACE_TAG_AUDIO = 256;
    public static final long TRACE_TAG_BIONIC = 65536;
    public static final long TRACE_TAG_CAMERA = 1024;
    public static final long TRACE_TAG_DALVIK = 16384;
    public static final long TRACE_TAG_DATABASE = 1048576;
    public static final long TRACE_TAG_GRAPHICS = 2;
    public static final long TRACE_TAG_HAL = 2048;
    public static final long TRACE_TAG_INPUT = 4;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final long TRACE_TAG_NETWORK = 2097152;
    public static final long TRACE_TAG_NEVER = 0;
    public static final long TRACE_TAG_NNAPI = 33554432;
    private static final long TRACE_TAG_NOT_READY = Long.MIN_VALUE;
    public static final long TRACE_TAG_PACKAGE_MANAGER = 262144;
    public static final long TRACE_TAG_POWER = 131072;
    public static final long TRACE_TAG_RESOURCES = 8192;
    public static final long TRACE_TAG_RRO = 67108864;
    public static final long TRACE_TAG_RS = 32768;
    public static final long TRACE_TAG_SYNC_MANAGER = 128;
    public static final long TRACE_TAG_SYSTEM_SERVER = 524288;
    public static final long TRACE_TAG_THERMAL = 134217728;
    public static final long TRACE_TAG_VIBRATOR = 8388608;
    public static final long TRACE_TAG_VIDEO = 512;
    public static final long TRACE_TAG_VIEW = 8;
    public static final long TRACE_TAG_WEBVIEW = 16;
    public static final long TRACE_TAG_WINDOW_MANAGER = 32;
    private static volatile long sEnabledTags = Long.MIN_VALUE;
    private static int sZygoteDebugFlags;

    @FastNative
    private static native void nativeAsyncTraceBegin(long j, String str, int i);

    @FastNative
    private static native void nativeAsyncTraceEnd(long j, String str, int i);

    @FastNative
    private static native void nativeAsyncTraceForTrackBegin(long j, String str, String str2, int i);

    @FastNative
    private static native void nativeAsyncTraceForTrackEnd(long j, String str, int i);

    @FastNative
    private static native void nativeInstant(long j, String str);

    @FastNative
    private static native void nativeInstantForTrack(long j, String str, String str2);

    @CriticalNative
    private static native boolean nativeIsTagEnabled(long j);

    private static boolean nativeIsTagEnabled$ravenwood(long j) {
        return false;
    }

    private static native void nativeSetAppTracingAllowed(boolean z);

    private static void nativeSetAppTracingAllowed$ravenwood(boolean z) {
    }

    private static native void nativeSetTracingEnabled(boolean z);

    private static void nativeSetTracingEnabled$ravenwood(boolean z) {
    }

    @FastNative
    private static native void nativeTraceBegin(long j, String str);

    @FastNative
    private static native void nativeTraceCounter(long j, String str, long j2);

    @FastNative
    private static native void nativeTraceEnd(long j);

    private Trace() {
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static boolean isTagEnabled(long j) {
        return nativeIsTagEnabled(j);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void traceCounter(long j, String str, int i) {
        if (isTagEnabled(j)) {
            nativeTraceCounter(j, str, i);
        }
    }

    public static void setAppTracingAllowed(boolean z) {
        nativeSetAppTracingAllowed(z);
    }

    public static void setTracingEnabled(boolean z, int i) {
        nativeSetTracingEnabled(z);
        sZygoteDebugFlags = i;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void traceBegin(long j, String str) {
        if (isTagEnabled(j)) {
            nativeTraceBegin(j, str);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void traceEnd(long j) {
        if (isTagEnabled(j)) {
            nativeTraceEnd(j);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void asyncTraceBegin(long j, String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceBegin(j, str, i);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void asyncTraceEnd(long j, String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceEnd(j, str, i);
        }
    }

    public static void asyncTraceForTrackBegin(long j, String str, String str2, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceForTrackBegin(j, str, str2, i);
        }
    }

    public static void asyncTraceForTrackEnd(long j, String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceForTrackEnd(j, str, i);
        }
    }

    public static void instant(long j, String str) {
        if (isTagEnabled(j)) {
            nativeInstant(j, str);
        }
    }

    public static void instantForTrack(long j, String str, String str2) {
        if (isTagEnabled(j)) {
            nativeInstantForTrack(j, str, str2);
        }
    }

    public static boolean isEnabled() {
        return isTagEnabled(4096L);
    }

    public static void beginSection(String str) {
        if (isTagEnabled(4096L)) {
            if (str.length() > 127) {
                throw new IllegalArgumentException("sectionName is too long");
            }
            nativeTraceBegin(4096L, str);
        }
    }

    public static void endSection() {
        if (isTagEnabled(4096L)) {
            nativeTraceEnd(4096L);
        }
    }

    public static void beginAsyncSection(String str, int i) {
        asyncTraceBegin(4096L, str, i);
    }

    public static void endAsyncSection(String str, int i) {
        asyncTraceEnd(4096L, str, i);
    }

    public static void setCounter(String str, long j) {
        setCounter(4096L, str, j);
    }

    public static void setCounter(long j, String str, long j2) {
        if (isTagEnabled(j)) {
            nativeTraceCounter(j, str, j2);
        }
    }

    public static void registerWithPerfetto() {
        PerfettoTrace.register(false);
        PerfettoTrace.registerCategories();
    }
}
