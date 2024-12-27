package com.samsung.android.server.packagefeature.util;

import android.text.TextUtils;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class FoldablePackageSpecialManagementList extends PackageSpecialManagementList {
    @Override // com.samsung.android.server.packagefeature.util.PackageSpecialManagementList, com.samsung.android.server.packagefeature.PackageFeatureCallback
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
