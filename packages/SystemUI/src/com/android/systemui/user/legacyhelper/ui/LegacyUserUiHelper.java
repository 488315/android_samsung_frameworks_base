package com.android.systemui.user.legacyhelper.ui;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.systemui.R;
import com.android.systemui.user.data.source.UserRecord;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LegacyUserUiHelper {
    public static final LegacyUserUiHelper INSTANCE = new LegacyUserUiHelper();

    private LegacyUserUiHelper() {
    }

    public static final String getUserRecordName(Context context, UserRecord userRecord, boolean z, boolean z2, boolean z3) {
        INSTANCE.getClass();
        boolean z4 = userRecord.isGuest;
        Integer valueOf = (z4 && userRecord.isCurrent) ? Integer.valueOf(R.string.guest_exit_quick_settings_button) : (!z4 || userRecord.info == null) ? null : Integer.valueOf(android.R.string.mime_type_spreadsheet_ext);
        if (valueOf != null) {
            return context.getString(valueOf.intValue());
        }
        UserInfo userInfo = userRecord.info;
        if (userInfo == null) {
            return context.getString(getUserSwitcherActionTextResourceId(userRecord.isGuest, z, z2, userRecord.isAddUser, userRecord.isAddSupervisedUser, z3, userRecord.isManageUsers));
        }
        String str = userInfo.name;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    public static final int getUserSwitcherActionIconResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return (z && z4) ? R.drawable.ic_account_circle_filled : z ? R.drawable.ic_add : z2 ? R.drawable.ic_account_circle : z3 ? R.drawable.ic_add_supervised_user : z5 ? R.drawable.ic_manage_users : R.drawable.ic_avatar_user;
    }

    public static final int getUserSwitcherActionTextResourceId(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        if (!z && !z4 && !z5 && !z7) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (z && z2 && z3) {
            return R.string.guest_resetting;
        }
        if (z && z6) {
            return R.string.guest_new_guest;
        }
        if ((z && z2) || z) {
            return android.R.string.mime_type_spreadsheet_ext;
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
