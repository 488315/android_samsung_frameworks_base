package com.android.internal.pm;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.util.ArrayMap;

/* loaded from: classes5.dex */
public final class RoSystemFeatures {
    public static Boolean maybeHasFeature(String str, int i) {
        return null;
    }

    public static boolean hasFeatureAutomotive(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_AUTOMOTIVE);
    }

    public static boolean hasFeatureEmbedded(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_EMBEDDED);
    }

    public static boolean hasFeatureLeanback(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_LEANBACK);
    }

    public static boolean hasFeaturePc(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_PC);
    }

    public static boolean hasFeatureTelevision(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_TELEVISION);
    }

    public static boolean hasFeatureWatch(Context context) {
        return hasFeatureFallback(context, PackageManager.FEATURE_WATCH);
    }

    private static boolean hasFeatureFallback(Context context, String str) {
        return context.getPackageManager().hasSystemFeature(str);
    }

    public static ArrayMap<String, FeatureInfo> getReadOnlySystemEnabledFeatures() {
        return new ArrayMap<>(0);
    }
}
