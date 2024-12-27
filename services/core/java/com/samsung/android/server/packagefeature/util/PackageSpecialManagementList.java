package com.samsung.android.server.packagefeature.util;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;

import java.util.ArrayList;

public class PackageSpecialManagementList extends ArrayList implements PackageFeatureCallback {
    public PackageSpecialManagementList(PackageFeature packageFeature) {
        packageFeature.registerCallback(this);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection,
              // java.util.List
    public final boolean contains(Object obj) {
        boolean contains;
        if (!(obj instanceof String)) {
            return false;
        }
        synchronized (this) {
            contains = super.contains(obj);
        }
        return contains;
    }

    public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
        synchronized (this) {
            clear();
            addAll(packageFeatureData.keySet());
        }
    }
}
