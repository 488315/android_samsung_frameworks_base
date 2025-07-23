package com.android.systemui.user.legacyhelper.ui;

import android.content.Context;
import android.content.pm.UserInfo;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.user.data.source.UserRecord;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LegacyUserUiHelper {
    public static final LegacyUserUiHelper INSTANCE = new LegacyUserUiHelper();

    private LegacyUserUiHelper() {
    }

    public static final String getUserRecordName(Context context, UserRecord userRecord, boolean z, boolean z2) {
        INSTANCE.getClass();
        boolean z3 = userRecord.isGuest;
        Integer valueOf = (z3 && userRecord.isCurrent) ? Integer.valueOf(R.string.guest_exit_quick_settings_button) : (!z3 || userRecord.info == null) ? null : Integer.valueOf(android.R.string.network_available_sign_in_detailed);
        if (valueOf != null) {
            return context.getString(valueOf.intValue());
        }
        UserInfo userInfo = userRecord.info;
        if (userInfo == null) {
            return context.getString(getUserSwitcherActionTextResourceId(userRecord.isGuest, z, z2, userRecord.isAddUser, userRecord.isAddSupervisedUser, userRecord.isSignOut, false, userRecord.isManageUsers));
        }
        String str = userInfo.name;
        if (str != null) {
            return str;
        }
        Log.i("LegacyUserUiHelper", "Expected display name for: " + userInfo);
        return "";
    }

    public static final int getUserSwitcherActionIconResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        return (z && z5) ? R.drawable.ic_account_circle_filled : z ? R.drawable.ic_add : z2 ? R.drawable.ic_account_circle : z3 ? R.drawable.ic_add_supervised_user : z4 ? android.R.drawable.ic_media_route_connecting_holo_light : z6 ? R.drawable.ic_manage_users : R.drawable.ic_avatar_user;
    }

    public static final int getUserSwitcherActionTextResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        if (!z && !z4 && !z5 && !z8 && !z6) {
            throw new IllegalStateException("Check failed.");
        }
        if (z && z2 && z3) {
            return R.string.guest_resetting;
        }
        if (z && z7) {
            return R.string.guest_new_guest;
        }
        if ((z && z2) || z) {
            return android.R.string.network_available_sign_in_detailed;
        }
        if (z4) {
            return R.string.user_add_user;
        }
        if (z5) {
            return R.string.add_user_supervised;
        }
        if (z6) {
            return android.R.string.midnight;
        }
        if (z8) {
            return R.string.manage_users;
        }
        throw new IllegalStateException("This should never happen!");
    }
}
