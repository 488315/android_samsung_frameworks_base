package com.android.systemui.coverlauncher.utils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
