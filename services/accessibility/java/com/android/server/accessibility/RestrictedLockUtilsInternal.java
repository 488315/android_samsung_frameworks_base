package com.android.server.accessibility;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.settingslib.RestrictedLockUtils;

/* loaded from: classes.dex */
public abstract class RestrictedLockUtilsInternal {
    public static RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed(Context context, String str, int i) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(i));
        boolean isAccessibilityServicePermittedByAdmin = profileOrDeviceOwner != null ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner.component, str, i) : true;
        int managedProfileId = getManagedProfileId(context, i);
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner2 = RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(managedProfileId));
        boolean isAccessibilityServicePermittedByAdmin2 = profileOrDeviceOwner2 != null ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner2.component, str, managedProfileId) : true;
        if (!isAccessibilityServicePermittedByAdmin && !isAccessibilityServicePermittedByAdmin2) {
            return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!isAccessibilityServicePermittedByAdmin) {
            return profileOrDeviceOwner;
        }
        if (isAccessibilityServicePermittedByAdmin2) {
            return null;
        }
        return profileOrDeviceOwner2;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfInputMethodDisallowed(Context context, String str, int i) {
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(i));
        boolean z = true;
        boolean isInputMethodPermittedByAdmin = profileOrDeviceOwner != null ? devicePolicyManager.isInputMethodPermittedByAdmin(profileOrDeviceOwner.component, str, i) : true;
        int managedProfileId = getManagedProfileId(context, i);
        if (managedProfileId != -10000) {
            enforcedAdmin = RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(managedProfileId));
            if (enforcedAdmin != null && devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
                z = devicePolicyManager.getParentProfileInstance(UserManager.get(context).getUserInfo(managedProfileId)).isInputMethodPermittedByAdmin(enforcedAdmin.component, str, managedProfileId);
            }
        } else {
            enforcedAdmin = null;
        }
        if (!isInputMethodPermittedByAdmin && !z) {
            return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!isInputMethodPermittedByAdmin) {
            return profileOrDeviceOwner;
        }
        if (z) {
            return null;
        }
        return enforcedAdmin;
    }

    public static int getManagedProfileId(Context context, int i) {
        for (UserInfo userInfo : ((UserManager) context.getSystemService(UserManager.class)).getProfiles(i)) {
            if (userInfo.id != i && userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    public static UserHandle getUserHandleOf(int i) {
        if (i == -10000) {
            return null;
        }
        return UserHandle.of(i);
    }
}
