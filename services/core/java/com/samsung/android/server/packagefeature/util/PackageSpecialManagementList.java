package com.samsung.android.server.packagefeature.util;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PackageSpecialManagementList extends ArrayList implements PackageFeatureCallback {
    public PackageSpecialManagementList(PackageFeature packageFeature) {
        packageFeature.registerCallback(this);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
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
