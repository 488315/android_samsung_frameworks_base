package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.EnforcingAdmin;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class RestrictedLockUtilsInternal extends RestrictedLockUtils {
    public static final boolean DEBUG = Log.isLoggable("RestrictedLockUtils", 3);
    static Proxy sProxy = new Proxy();

    class Proxy {
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced(Context context, String str, int i) {
        DevicePolicyManager devicePolicyManager;
        ComponentName deviceOwnerComponentOnAnyUser;
        RestrictionPolicy restrictionPolicy;
        DevicePolicyManager devicePolicyManager2 = (DevicePolicyManager) context.getSystemService("device_policy");
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (devicePolicyManager2 != null) {
            UserManager userManager = UserManager.get(context);
            UserHandle userHandleOf = UserHandle.of(i);
            List userRestrictionSources = userManager.getUserRestrictionSources(str, userHandleOf);
            if (!userRestrictionSources.isEmpty()) {
                int size = userRestrictionSources.size();
                if (size > 1) {
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = new RestrictedLockUtils.EnforcedAdmin();
                    enforcedAdmin2.enforcedRestriction = str;
                    enforcedAdmin2.user = userHandleOf;
                    if (DEBUG) {
                        StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(size, "Multiple (", ") enforcing users for restriction '", str, "' on user ");
                        sbM.append(userHandleOf);
                        sbM.append("; returning default admin (");
                        sbM.append(enforcedAdmin2);
                        sbM.append(")");
                        Log.d("RestrictedLockUtils", sbM.toString());
                    }
                    return enforcedAdmin2;
                }
                UserManager.EnforcingUser enforcingUser = (UserManager.EnforcingUser) userRestrictionSources.get(0);
                if (enforcingUser.getUserRestrictionSource() != 1) {
                    EnforcingAdmin enforcingAdmin = devicePolicyManager2.getEnforcingAdmin(i, str);
                    if (enforcingAdmin != null) {
                        return new RestrictedLockUtils.EnforcedAdmin(enforcingAdmin.getComponentName(), str, enforcingAdmin.getUserHandle());
                    }
                    UserHandle userHandle = enforcingUser.getUserHandle();
                    if (userHandle != null && (devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy")) != null) {
                        try {
                            ComponentName profileOwner = ((DevicePolicyManager) context.createPackageContextAsUser(context.getPackageName(), 0, userHandle).getSystemService(DevicePolicyManager.class)).getProfileOwner();
                            if (profileOwner != null) {
                                enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(profileOwner, str, userHandle);
                            } else if (Objects.equals(devicePolicyManager.getDeviceOwnerUser(), userHandle) && (deviceOwnerComponentOnAnyUser = devicePolicyManager.getDeviceOwnerComponentOnAnyUser()) != null) {
                                enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(deviceOwnerComponentOnAnyUser, str, userHandle);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    if (enforcedAdmin != null) {
                        return enforcedAdmin;
                    }
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin3 = new RestrictedLockUtils.EnforcedAdmin();
                    enforcedAdmin3.enforcedRestriction = str;
                    return enforcedAdmin3;
                }
                EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
                if (enterpriseDeviceManager != null && (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy()) != null && restrictionPolicy.checkIfRestrictionWasSetByKC(str)) {
                    return new RestrictedLockUtils.EnforcedAdmin(RestrictionPolicy.KC_COMPONENT_NAME, str, new UserHandle(0));
                }
            }
        }
        return null;
    }

    public static boolean hasBaseUserRestriction(Context context, String str, int i) {
        return ((UserManager) context.getSystemService("user")).hasBaseUserRestriction(str, UserHandle.of(i));
    }
}
