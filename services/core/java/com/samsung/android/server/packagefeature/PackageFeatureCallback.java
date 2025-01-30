package com.samsung.android.server.packagefeature;

import java.util.function.Function;

/* loaded from: classes2.dex */
public interface PackageFeatureCallback {
    void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData);

    default void onUnformattedPackageFeatureFileChanged(String str, Function function) {
    }
}
