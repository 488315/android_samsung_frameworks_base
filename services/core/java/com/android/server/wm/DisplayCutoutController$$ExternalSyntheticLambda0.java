package com.android.server.wm;

import com.samsung.android.server.packagefeature.PackageFeatureUserChange;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayCutoutController$$ExternalSyntheticLambda0 implements PackageFeatureUserChange.DumpInterface {
    @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
    public final String valueToString(String str, int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        return intValue != 0 ? intValue != 1 ? intValue != 2 ? Integer.toString(intValue) : "HideCameraCutout" : "OverlapWithTheCameraCutout" : "AppDefault";
    }
}
