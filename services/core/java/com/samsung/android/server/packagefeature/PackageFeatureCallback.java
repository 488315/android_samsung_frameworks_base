package com.samsung.android.server.packagefeature;

import java.util.function.Function;

public interface PackageFeatureCallback {
    void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData);

    default void onUnformattedPackageFeatureFileChanged(String str, Function function) {}
}
