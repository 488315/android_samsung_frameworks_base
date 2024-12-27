package com.android.server.accessibility;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RestrictedLockUtilsInternal {
    public static int getManagedProfileId(Context context, int i) {
        for (UserInfo userInfo :
                ((UserManager) context.getSystemService(UserManager.class)).getProfiles(i)) {
            if (userInfo.id != i && userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }
}
