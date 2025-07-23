package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserActionsUtil {
    public static final UserActionsUtil INSTANCE = new UserActionsUtil();

    private UserActionsUtil() {
    }

    public static boolean canAddMoreUsers(UserManager userManager, UserRepository userRepository, boolean z, boolean z2, String str) {
        if (!z || !z2) {
            return false;
        }
        UserInfo selectedUserInfo = ((UserRepositoryImpl) userRepository).getSelectedUserInfo();
        return (selectedUserInfo.isAdmin() || selectedUserInfo.id == 0) && !userManager.hasUserRestrictionForUser("no_add_user", UserHandle.of(selectedUserInfo.id)) && !userManager.hasUserRestrictionForUser("no_add_user", UserHandle.SYSTEM) && userManager.canAddMoreUsers(str);
    }
}
