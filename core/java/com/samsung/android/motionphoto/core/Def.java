package com.samsung.android.motionphoto.core;

/* loaded from: classes5.dex */
public class Def {
    public static final int MP_EVENT_ERROR = 3002;
    public static final int MP_EVENT_INFO = 3001;
    public static final int MP_EVENT_START = 3000;
    public static final String MP_LEGACY_NATIVE_LIB = "apex_jni.media.samsung";
    public static final String MP_NATIVE_LIB = "motionphoto_jni.media.samsung";

    public static String getMPCoreVersion() {
        return BuildConfig.MP_CORE_VERSION;
    }
}
