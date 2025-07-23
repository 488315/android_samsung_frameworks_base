package com.android.systemui.coverlauncher.utils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
