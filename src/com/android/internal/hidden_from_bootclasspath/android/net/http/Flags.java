package com.android.internal.hidden_from_bootclasspath.android.net.http;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_PRELOAD_HTTPENGINE_IN_ZYGOTE = "android.net.http.preload_httpengine_in_zygote";
    public static final String FLAG_PRELOAD_HTTPENGINE_JAVA_IMPL_CLASSES = "android.net.http.preload_httpengine_java_impl_classes";
    public static final String FLAG_PRELOAD_HTTPENGINE_SHARED_LIBRARY = "android.net.http.preload_httpengine_shared_library";

    public static boolean preloadHttpengineInZygote() {
        return FEATURE_FLAGS.preloadHttpengineInZygote();
    }

    public static boolean preloadHttpengineJavaImplClasses() {
        return FEATURE_FLAGS.preloadHttpengineJavaImplClasses();
    }

    public static boolean preloadHttpengineSharedLibrary() {
        return FEATURE_FLAGS.preloadHttpengineSharedLibrary();
    }
}
