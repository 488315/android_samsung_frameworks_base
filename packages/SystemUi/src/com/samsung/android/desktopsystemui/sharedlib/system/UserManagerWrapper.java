package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.os.UserHandle;
import android.os.UserManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class UserManagerWrapper {
    private static final UserManagerWrapper sInstance = new UserManagerWrapper();
    private static final UserManager mUserManager = (UserManager) AppGlobals.getInitialApplication().getSystemService("user");

    private UserManagerWrapper() {
    }

    public static UserManagerWrapper getInstance() {
        return sInstance;
    }

    public boolean hasUserRestriction(int i) {
        return mUserManager.hasUserRestriction("no_share_location", UserHandle.semOf(i));
    }

    public boolean isSecondaryOrGuestUser(int i) {
        String str = mUserManager.getUserInfo(i).userType;
        return "android.os.usertype.full.SECONDARY".equals(str) || UserManager.isUserTypeGuest(str);
    }
}
