package com.android.systemui.coverlauncher.utils;

import kotlin.jvm.internal.Intrinsics;

public final class CoverLauncherPackageInfo {
    public final String packageName;
    public final int profileId;

    public CoverLauncherPackageInfo(String str, int i) {
        this.packageName = str;
        this.profileId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoverLauncherPackageInfo)) {
            return false;
        }
        CoverLauncherPackageInfo coverLauncherPackageInfo = (CoverLauncherPackageInfo) obj;
        return Intrinsics.areEqual(this.packageName, coverLauncherPackageInfo.packageName) && this.profileId == coverLauncherPackageInfo.profileId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.profileId) + (this.packageName.hashCode() * 31);
    }

    public final String toString() {
        return "mPackageName=" + this.packageName + ", mProfileId=" + this.profileId;
    }
}
