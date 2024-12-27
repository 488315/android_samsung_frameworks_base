package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;

import java.util.List;

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
