package com.android.server.wm;

import com.samsung.android.server.packagefeature.PackageFeatureUserChange;

public final /* synthetic */ class DisplayCutoutController$$ExternalSyntheticLambda0
        implements PackageFeatureUserChange.DumpInterface {
    @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
    public final String valueToString(String str, int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        return intValue != 0
                ? intValue != 1
                        ? intValue != 2 ? Integer.toString(intValue) : "HideCameraCutout"
                        : "OverlapWithTheCameraCutout"
                : "AppDefault";
    }
}
