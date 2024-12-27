package com.android.server.recoverysystem;

import android.apex.CompressedApexInfo;
import android.ota.nano.OtaPackageMetadata;

import java.util.function.Function;

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
