package com.android.server.accessibility;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;

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
