package com.android.systemui.user.legacyhelper.p034ui;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.systemui.R;
import com.android.systemui.user.data.source.UserRecord;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LegacyUserUiHelper {
    public static final LegacyUserUiHelper INSTANCE = new LegacyUserUiHelper();

    private LegacyUserUiHelper() {
    }

    public static final String getUserRecordName(Context context, UserRecord userRecord, boolean z, boolean z2, boolean z3) {
        INSTANCE.getClass();
        UserInfo userInfo = userRecord.info;
        boolean z4 = userRecord.isGuest;
        Integer valueOf = (z4 && userRecord.isCurrent) ? Integer.valueOf(R.string.guest_exit_quick_settings_button) : (!z4 || userInfo == null) ? null : Integer.valueOf(android.R.string.mediasize_japanese_l);
        return valueOf != null ? context.getString(valueOf.intValue()) : userInfo != null ? userInfo.name : context.getString(getUserSwitcherActionTextResourceId(userRecord.isGuest, z, z2, userRecord.isAddUser, userRecord.isAddSupervisedUser, z3, userRecord.isManageUsers));
    }

    public static final int getUserSwitcherActionIconResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return (z && z4) ? R.drawable.ic_account_circle_filled : z ? R.drawable.ic_add : z2 ? R.drawable.ic_account_circle : z3 ? R.drawable.ic_add_supervised_user : z5 ? R.drawable.ic_manage_users : R.drawable.ic_avatar_user;
    }

    public static final int getUserSwitcherActionTextResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        if (!(z || z4 || z5 || z7)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (z && z2 && z3) {
            return R.string.guest_resetting;
        }
        if (z && z6) {
            return R.string.guest_new_guest;
        }
        if ((z && z2) || z) {
            return android.R.string.mediasize_japanese_l;
        }
        if (z4) {
            return R.string.user_add_user;
        }
        if (z5) {
            return R.string.add_user_supervised;
        }
        if (z7) {
            return R.string.manage_users;
        }
        throw new IllegalStateException("This should never happen!".toString());
    }
}
