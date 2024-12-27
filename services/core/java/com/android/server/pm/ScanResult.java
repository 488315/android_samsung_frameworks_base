package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class ScanResult {
    public final List mDynamicSharedLibraryInfos;
    public final boolean mExistingSettingCopied;
    public final PackageSetting mPkgSetting;
    public final ScanRequest mRequest;
    public final SharedLibraryInfo mSdkSharedLibraryInfo;
    public final SharedLibraryInfo mStaticSharedLibraryInfo;

    public ScanResult(
            ScanRequest scanRequest,
            PackageSetting packageSetting,
            boolean z,
            SharedLibraryInfo sharedLibraryInfo,
            SharedLibraryInfo sharedLibraryInfo2,
            List list) {
        this.mRequest = scanRequest;
        this.mPkgSetting = packageSetting;
        this.mExistingSettingCopied = z;
        this.mSdkSharedLibraryInfo = sharedLibraryInfo;
        this.mStaticSharedLibraryInfo = sharedLibraryInfo2;
        this.mDynamicSharedLibraryInfos = list;
    }
}
