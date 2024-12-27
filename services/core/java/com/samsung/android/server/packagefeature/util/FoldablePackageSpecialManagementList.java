package com.samsung.android.server.packagefeature.util;

import android.text.TextUtils;

import com.samsung.android.server.packagefeature.PackageFeatureData;

import java.util.Map;

public class FoldablePackageSpecialManagementList extends PackageSpecialManagementList {
    @Override // com.samsung.android.server.packagefeature.util.PackageSpecialManagementList,
              // com.samsung.android.server.packagefeature.PackageFeatureCallback
    public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
        PackageFeatureData packageFeatureData2 = new PackageFeatureData();
        for (Map.Entry entry : packageFeatureData.entrySet()) {
            String str = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                packageFeatureData2.put((String) entry.getKey(), str);
            }
        }
        super.onPackageFeatureDataChanged(packageFeatureData2);
    }
}
