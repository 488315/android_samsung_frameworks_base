package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;
import java.util.List;

/* loaded from: classes3.dex */
final class ScanResult {
    public final List mChangedAbiCodePath;
    public final List mDynamicSharedLibraryInfos;
    public final boolean mExistingSettingCopied;
    public final PackageSetting mPkgSetting;
    public final int mPreviousAppId = -1;
    public final ScanRequest mRequest;
    public final SharedLibraryInfo mSdkSharedLibraryInfo;
    public final SharedLibraryInfo mStaticSharedLibraryInfo;

    public ScanResult(ScanRequest scanRequest, PackageSetting packageSetting, List list, boolean z, int i, SharedLibraryInfo sharedLibraryInfo, SharedLibraryInfo sharedLibraryInfo2, List list2) {
        this.mRequest = scanRequest;
        this.mPkgSetting = packageSetting;
        this.mChangedAbiCodePath = list;
        this.mExistingSettingCopied = z;
        this.mSdkSharedLibraryInfo = sharedLibraryInfo;
        this.mStaticSharedLibraryInfo = sharedLibraryInfo2;
        this.mDynamicSharedLibraryInfos = list2;
    }
}
