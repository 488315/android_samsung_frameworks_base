package com.android.server.pm;

import android.os.UserHandle;

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
