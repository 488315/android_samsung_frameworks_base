package com.android.server.pm.pkg;

import android.content.pm.SharedLibraryInfo;
import android.text.TextUtils;
import com.android.server.pm.PackageSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class PackageStateUnserialized {
    public boolean apkInUpdatedApex;
    public boolean hiddenUntilInstalled;
    public volatile long[] lastPackageUsageTimeInMills;
    public String mApexModuleName;
    public final PackageSetting mPackageSetting;
    public String overrideSeInfo;
    public String seInfo;
    public boolean updatedSystemApp;
    public List usesLibraryInfos = Collections.emptyList();
    public List usesLibraryFiles = Collections.emptyList();

    public final long[] lazyInitLastPackageUsageTimeInMills() {
        return new long[8];
    }

    public PackageStateUnserialized(PackageSetting packageSetting) {
        this.mPackageSetting = packageSetting;
    }

    public PackageStateUnserialized setLastPackageUsageTimeInMills(int i, long j) {
        if (i < 0 || i >= 8) {
            return this;
        }
        getLastPackageUsageTimeInMills()[i] = j;
        return this;
    }

    public long getLatestPackageUseTimeInMills() {
        long j = 0;
        for (long j2 : getLastPackageUsageTimeInMills()) {
            j = Math.max(j, j2);
        }
        return j;
    }

    public long getLatestForegroundPackageUseTimeInMills() {
        int[] iArr = {0, 2};
        long j = 0;
        for (int i = 0; i < 2; i++) {
            j = Math.max(j, getLastPackageUsageTimeInMills()[iArr[i]]);
        }
        return j;
    }

    public void updateFrom(PackageStateUnserialized packageStateUnserialized) {
        this.hiddenUntilInstalled = packageStateUnserialized.hiddenUntilInstalled;
        if (!packageStateUnserialized.usesLibraryInfos.isEmpty()) {
            this.usesLibraryInfos = new ArrayList(packageStateUnserialized.usesLibraryInfos);
        }
        if (!packageStateUnserialized.usesLibraryFiles.isEmpty()) {
            this.usesLibraryFiles = new ArrayList(packageStateUnserialized.usesLibraryFiles);
        }
        this.updatedSystemApp = packageStateUnserialized.updatedSystemApp;
        this.apkInUpdatedApex = packageStateUnserialized.apkInUpdatedApex;
        this.lastPackageUsageTimeInMills = packageStateUnserialized.lastPackageUsageTimeInMills;
        this.overrideSeInfo = packageStateUnserialized.overrideSeInfo;
        this.seInfo = packageStateUnserialized.seInfo;
        this.mApexModuleName = packageStateUnserialized.mApexModuleName;
        this.mPackageSetting.onChanged();
    }

    public List getNonNativeUsesLibraryInfos() {
        ArrayList arrayList = new ArrayList();
        this.usesLibraryInfos = getUsesLibraryInfos();
        for (int i = 0; i < this.usesLibraryInfos.size(); i++) {
            SharedLibraryWrapper sharedLibraryWrapper = (SharedLibraryWrapper) this.usesLibraryInfos.get(i);
            if (!sharedLibraryWrapper.isNative()) {
                arrayList.add(sharedLibraryWrapper.getInfo());
            }
        }
        return arrayList;
    }

    public PackageStateUnserialized setHiddenUntilInstalled(boolean z) {
        this.hiddenUntilInstalled = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setUsesLibraryInfos(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new SharedLibraryWrapper((SharedLibraryInfo) list.get(i)));
        }
        this.usesLibraryInfos = arrayList;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setUsesLibraryFiles(List list) {
        this.usesLibraryFiles = list;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setUpdatedSystemApp(boolean z) {
        this.updatedSystemApp = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setApkInUpdatedApex(boolean z) {
        this.apkInUpdatedApex = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setOverrideSeInfo(String str) {
        this.overrideSeInfo = str;
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setSeInfo(String str) {
        this.seInfo = TextUtils.safeIntern(str);
        this.mPackageSetting.onChanged();
        return this;
    }

    public PackageStateUnserialized setApexModuleName(String str) {
        this.mApexModuleName = str;
        this.mPackageSetting.onChanged();
        return this;
    }

    public boolean isHiddenUntilInstalled() {
        return this.hiddenUntilInstalled;
    }

    public List getUsesLibraryInfos() {
        return this.usesLibraryInfos;
    }

    public List getUsesLibraryFiles() {
        return this.usesLibraryFiles;
    }

    public boolean isUpdatedSystemApp() {
        return this.updatedSystemApp;
    }

    public boolean isApkInUpdatedApex() {
        return this.apkInUpdatedApex;
    }

    public long[] getLastPackageUsageTimeInMills() {
        long[] jArr = this.lastPackageUsageTimeInMills;
        if (jArr == null) {
            synchronized (this) {
                jArr = this.lastPackageUsageTimeInMills;
                if (jArr == null) {
                    jArr = lazyInitLastPackageUsageTimeInMills();
                    this.lastPackageUsageTimeInMills = jArr;
                }
            }
        }
        return jArr;
    }

    public String getOverrideSeInfo() {
        return this.overrideSeInfo;
    }

    public String getSeInfo() {
        return this.seInfo;
    }

    public String getApexModuleName() {
        return this.mApexModuleName;
    }
}
