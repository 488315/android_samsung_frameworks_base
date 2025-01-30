package com.android.server.pm.pkg.mutate;

import android.util.ArraySet;

/* loaded from: classes3.dex */
public interface PackageStateWrite {
    void onChanged();

    PackageStateWrite setCategoryOverride(int i);

    PackageStateWrite setHiddenUntilInstalled(boolean z);

    PackageStateWrite setInstaller(String str, int i);

    PackageStateWrite setLoadingCompletedTime(long j);

    PackageStateWrite setLoadingProgress(float f);

    PackageStateWrite setMimeGroup(String str, ArraySet arraySet);

    PackageStateWrite setOverrideSeInfo(String str);

    PackageStateWrite setRequiredForSystemUser(boolean z);

    PackageStateWrite setUpdateAvailable(boolean z);

    PackageStateWrite setUpdateOwner(String str);

    PackageUserStateWrite userState(int i);
}
