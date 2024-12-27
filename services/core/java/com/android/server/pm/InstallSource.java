package com.android.server.pm;

import com.android.internal.util.Preconditions;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class InstallSource {
    public static final InstallSource EMPTY = new InstallSource(-1, 0, null, null, null, null, null, null, false, false);
    public static final InstallSource EMPTY_ORPHANED = new InstallSource(-1, 0, null, null, null, null, null, null, true, false);
    public final String mInitiatingPackageName;
    public final PackageSignatures mInitiatingPackageSignatures;
    public final String mInstallerAttributionTag;
    public final String mInstallerPackageName;
    public final int mInstallerPackageUid;
    public final boolean mIsInitiatingPackageUninstalled;
    public final boolean mIsOrphaned;
    public final String mOriginatingPackageName;
    public final int mPackageSource;
    public final String mUpdateOwnerPackageName;

    public InstallSource(int i, int i2, PackageSignatures packageSignatures, String str, String str2, String str3, String str4, String str5, boolean z, boolean z2) {
        if (str == null) {
            Preconditions.checkArgument(packageSignatures == null);
            Preconditions.checkArgument(!z2);
        }
        this.mInitiatingPackageName = str;
        this.mOriginatingPackageName = str2;
        this.mInstallerPackageName = str3;
        this.mInstallerPackageUid = i;
        this.mUpdateOwnerPackageName = str4;
        this.mInstallerAttributionTag = str5;
        this.mIsOrphaned = z;
        this.mIsInitiatingPackageUninstalled = z2;
        this.mInitiatingPackageSignatures = packageSignatures;
        this.mPackageSource = i2;
    }

    public static InstallSource create(String str, String str2, String str3, int i, String str4, String str5, int i2, boolean z, boolean z2) {
        return createInternal(i, i2, null, intern(str), intern(str2), intern(str3), intern(str4), str5, z, z2);
    }

    public static InstallSource createInternal(int i, int i2, PackageSignatures packageSignatures, String str, String str2, String str3, String str4, String str5, boolean z, boolean z2) {
        return (str == null && str2 == null && str3 == null && str4 == null && packageSignatures == null && !z2 && i2 == 0) ? z ? EMPTY_ORPHANED : EMPTY : new InstallSource(i, i2, packageSignatures, str, str2, str3, str4, str5, z, z2);
    }

    public static String intern(String str) {
        if (str == null) {
            return null;
        }
        return str.intern();
    }

    public final InstallSource setUpdateOwnerPackageName(String str) {
        if (Objects.equals(str, this.mUpdateOwnerPackageName)) {
            return this;
        }
        String intern = intern(str);
        boolean z = this.mIsInitiatingPackageUninstalled;
        PackageSignatures packageSignatures = this.mInitiatingPackageSignatures;
        String str2 = this.mInitiatingPackageName;
        String str3 = this.mOriginatingPackageName;
        String str4 = this.mInstallerPackageName;
        return createInternal(this.mInstallerPackageUid, this.mPackageSource, packageSignatures, str2, str3, str4, intern, this.mInstallerAttributionTag, this.mIsOrphaned, z);
    }
}
