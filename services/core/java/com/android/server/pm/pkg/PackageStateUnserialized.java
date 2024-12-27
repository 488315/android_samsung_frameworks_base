package com.android.server.pm.pkg;

import android.content.pm.SharedLibraryInfo;
import com.android.server.pm.PackageSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageStateUnserialized {
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

    public PackageStateUnserialized(PackageSetting packageSetting) {
        this.mPackageSetting = packageSetting;
    }

    public final long[] getLastPackageUsageTimeInMills() {
        long[] jArr = this.lastPackageUsageTimeInMills;
        if (jArr == null) {
            synchronized (this) {
                try {
                    jArr = this.lastPackageUsageTimeInMills;
                    if (jArr == null) {
                        jArr = new long[8];
                        this.lastPackageUsageTimeInMills = jArr;
                    }
                } finally {
                }
            }
        }
        return jArr;
    }

    public final long getLatestForegroundPackageUseTimeInMills() {
        int[] iArr = {0, 2};
        long j = 0;
        for (int i = 0; i < 2; i++) {
            j = Math.max(j, getLastPackageUsageTimeInMills()[iArr[i]]);
        }
        return j;
    }

    public final long getLatestPackageUseTimeInMills() {
        long j = 0;
        for (long j2 : getLastPackageUsageTimeInMills()) {
            j = Math.max(j, j2);
        }
        return j;
    }

    public final void setUpdatedSystemApp(boolean z) {
        this.updatedSystemApp = z;
        this.mPackageSetting.onChanged$2();
    }

    public final void setUsesLibraryInfos(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new SharedLibraryWrapper((SharedLibraryInfo) list.get(i)));
        }
        this.usesLibraryInfos = arrayList;
        this.mPackageSetting.onChanged$2();
    }
}
