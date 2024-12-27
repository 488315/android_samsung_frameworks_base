package com.android.server.recoverysystem;

import android.apex.CompressedApexInfo;
import android.ota.nano.OtaPackageMetadata;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecoverySystemService$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OtaPackageMetadata.ApexInfo apexInfo = (OtaPackageMetadata.ApexInfo) obj;
        CompressedApexInfo compressedApexInfo = new CompressedApexInfo();
        compressedApexInfo.moduleName = apexInfo.packageName;
        compressedApexInfo.decompressedSize = apexInfo.decompressedSize;
        compressedApexInfo.versionCode = apexInfo.version;
        return compressedApexInfo;
    }
}
