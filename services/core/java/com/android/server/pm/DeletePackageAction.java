package com.android.server.pm;

import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DeletePackageAction {
    public final PackageSetting mDeletingPs;
    public final PackageSetting mDisabledPs;
    public final int mFlags;
    public final PackageRemovedInfo mRemovedInfo;
    public final UserHandle mUser;

    public DeletePackageAction(
            int i,
            UserHandle userHandle,
            PackageRemovedInfo packageRemovedInfo,
            PackageSetting packageSetting,
            PackageSetting packageSetting2) {
        this.mDeletingPs = packageSetting;
        this.mDisabledPs = packageSetting2;
        this.mRemovedInfo = packageRemovedInfo;
        this.mFlags = i;
        this.mUser = userHandle;
    }
}
