package com.android.server.wm;

import android.content.pm.UserInfo;

import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;

public final class ActivityEmbeddedController extends PackagesChange {
    public final PackageFeatureUserChange mUserChange;
    public UserManagerInternal mUserManagerInternal;

    public ActivityEmbeddedController(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        this.mUserChange =
                new PackageFeatureUserChange(
                        1024,
                        PackageFeatureUserChangePersister.EMBED_ACTIVITY_DIRECTORY,
                        "EmbedActivityPackageSetting");
    }

    public final int findTargetUserId(int i) {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal =
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        UserInfo userInfo = this.mUserManagerInternal.getUserInfo(i);
        return (userInfo == null || !(userInfo.isProfile() || userInfo.isDualAppProfile()))
                ? i
                : this.mUserManagerInternal.getProfileParentId(userInfo.id);
    }

    public final int getEnabled(int i, String str) {
        Integer num = (Integer) this.mUserChange.getValue(findTargetUserId(i), str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }
}
