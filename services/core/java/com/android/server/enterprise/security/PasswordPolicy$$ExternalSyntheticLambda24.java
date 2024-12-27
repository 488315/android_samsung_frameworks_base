package com.android.server.enterprise.security;

import android.content.ComponentName;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;

import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.List;

public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda24
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda24(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final Object getOrThrow() {
        ComponentName deviceOwnerComponentOnCallingUser;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PasswordPolicy passwordPolicy = (PasswordPolicy) obj;
                Boolean bool = Boolean.TRUE;
                if (passwordPolicy.mDpm == null || passwordPolicy.mService == null) {
                    return bool;
                }
                ((PersonaManagerAdapter) passwordPolicy.mPersonaManagerAdapter).getClass();
                if (SemPersonaManager.isDoEnabled(0)
                        && (deviceOwnerComponentOnCallingUser =
                                        passwordPolicy.mDpm.getDeviceOwnerComponentOnCallingUser())
                                != null
                        && (passwordPolicy.mDpm.getPasswordQuality(
                                                deviceOwnerComponentOnCallingUser, 0)
                                        > 0
                                || passwordPolicy.mDpm.getPasswordMinimumLength(
                                                deviceOwnerComponentOnCallingUser, 0)
                                        > 0)) {
                    Log.d("PasswordPolicy", "isClearLockAllowed - false due to DO and pwd policy");
                    bool = Boolean.FALSE;
                }
                UserManager userManager = passwordPolicy.mUserManager;
                if (userManager != null) {
                    List users = userManager.getUsers();
                    List allOneLockedChildUsers = passwordPolicy.getAllOneLockedChildUsers(0);
                    for (int i2 = 0; i2 < users.size(); i2++) {
                        UserInfo userInfo = (UserInfo) users.get(i2);
                        if (userInfo.isManagedProfile()) {
                            ComponentName profileOwnerAsUser =
                                    passwordPolicy.mDpm.getProfileOwnerAsUser(userInfo.id);
                            if (profileOwnerAsUser != null
                                    && (passwordPolicy.mService.getPasswordQuality(
                                                            profileOwnerAsUser, userInfo.id, true)
                                                    > 0
                                            || passwordPolicy.mService.getPasswordMinimumLength(
                                                            profileOwnerAsUser, userInfo.id, true)
                                                    > 0)) {
                                Log.d(
                                        "PasswordPolicy",
                                        "isClearLockAllowed - false due to PO and parent pwd"
                                            + " policy");
                                bool = Boolean.FALSE;
                            }
                            if (((ArrayList) allOneLockedChildUsers)
                                            .contains(Integer.valueOf(userInfo.id))
                                    && profileOwnerAsUser != null
                                    && (passwordPolicy.mDpm.getPasswordQuality(
                                                            profileOwnerAsUser, userInfo.id)
                                                    > 0
                                            || passwordPolicy.mDpm.getPasswordMinimumLength(
                                                            profileOwnerAsUser, userInfo.id)
                                                    > 0)) {
                                Log.d(
                                        "PasswordPolicy",
                                        "isClearLockAllowed - false due to PO and one lock");
                                bool = Boolean.FALSE;
                            }
                        }
                    }
                }
                if (passwordPolicy.getMaximumFailedPasswordsForDisable(0) <= 0) {
                    return bool;
                }
                Log.d(
                        "PasswordPolicy",
                        "isClearLockAllowed - false due to FailedPasswordsForDisable policy");
                return Boolean.FALSE;
            default:
                return ContainerProxy.sendPolicyUpdate(
                        "knox.container.proxy.POLICY_ADMIN_LOCK", (Bundle) obj);
        }
    }
}
