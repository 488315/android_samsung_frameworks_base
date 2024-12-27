package com.android.server.accessibility;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.appwidget.AppWidgetManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.view.inputmethod.InputMethodInfo;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.settingslib.RestrictedLockUtils;

import libcore.util.EmptyArray;

import java.util.List;
import java.util.Set;

public final class AccessibilitySecurityPolicy {
    public static final int OWN_PROCESS_ID = Process.myPid();
    public final AccessibilityUserManager mAccessibilityUserManager;
    public AccessibilityWindowManager mAccessibilityWindowManager;
    public final AppOpsManager mAppOpsManager;
    public AppWidgetManagerInternal mAppWidgetService;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final PackageManagerInternal mPackageManagerInternal;
    public final PolicyWarningUIController mPolicyWarningUIController;
    public final UserManager mUserManager;
    public final ArraySet mNonA11yCategoryServices = new ArraySet();
    public int mCurrentUserId = -10000;
    public boolean mSendNonA11yToolNotificationEnabled = false;

    public interface AccessibilityUserManager {}

    public AccessibilitySecurityPolicy(
            PolicyWarningUIController policyWarningUIController,
            Context context,
            AccessibilityUserManager accessibilityUserManager,
            PackageManagerInternal packageManagerInternal) {
        this.mContext = context;
        this.mAccessibilityUserManager = accessibilityUserManager;
        this.mPackageManager = context.getPackageManager();
        this.mPackageManagerInternal = packageManagerInternal;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mPolicyWarningUIController = policyWarningUIController;
    }

    public static boolean canControlMagnification(
            AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        return (abstractAccessibilityServiceConnection.getCapabilities() & 16) != 0;
    }

    public static boolean isCallerInteractingAcrossUsers(int i) {
        return Binder.getCallingPid() == Process.myPid()
                || Binder.getCallingUid() == 2000
                || i == -2
                || i == -3;
    }

    public final int canEnableDisableInputMethod(
            String str,
            AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        boolean z;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        String packageName = abstractAccessibilityServiceConnection.mComponentName.getPackageName();
        int callingUserId = UserHandle.getCallingUserId();
        List<InputMethodInfo> inputMethodListAsUser =
                InputMethodManagerInternal.get().getInputMethodListAsUser(callingUserId);
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = null;
        if (inputMethodListAsUser != null) {
            for (InputMethodInfo inputMethodInfo : inputMethodListAsUser) {
                if (inputMethodInfo.getId().equals(str)) {
                    break;
                }
            }
        }
        inputMethodInfo = null;
        if (inputMethodInfo == null || !inputMethodInfo.getPackageName().equals(packageName)) {
            throw new SecurityException(
                    "The input method is in a different package with the accessibility service");
        }
        Context context = this.mContext;
        String packageName2 = inputMethodInfo.getPackageName();
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager != null) {
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                    RestrictedLockUtils.getProfileOrDeviceOwner(
                            context, callingUserId == -10000 ? null : UserHandle.of(callingUserId));
            boolean isInputMethodPermittedByAdmin =
                    profileOrDeviceOwner != null
                            ? devicePolicyManager.isInputMethodPermittedByAdmin(
                                    profileOrDeviceOwner.component, packageName2, callingUserId)
                            : true;
            int managedProfileId =
                    RestrictedLockUtilsInternal.getManagedProfileId(context, callingUserId);
            if (managedProfileId != -10000) {
                enforcedAdmin =
                        RestrictedLockUtils.getProfileOrDeviceOwner(
                                context,
                                managedProfileId == -10000
                                        ? null
                                        : UserHandle.of(managedProfileId));
                z =
                        (enforcedAdmin == null
                                        || !devicePolicyManager
                                                .isOrganizationOwnedDeviceWithManagedProfile())
                                ? true
                                : devicePolicyManager
                                        .getParentProfileInstance(
                                                UserManager.get(context)
                                                        .getUserInfo(managedProfileId))
                                        .isInputMethodPermittedByAdmin(
                                                enforcedAdmin.component,
                                                packageName2,
                                                managedProfileId);
            } else {
                z = true;
                enforcedAdmin = null;
            }
            if (!isInputMethodPermittedByAdmin && !z) {
                enforcedAdmin2 = RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
            } else if (!isInputMethodPermittedByAdmin) {
                enforcedAdmin2 = profileOrDeviceOwner;
            } else if (!z) {
                enforcedAdmin2 = enforcedAdmin;
            }
        }
        return enforcedAdmin2 != null ? 1 : 0;
    }

    public final boolean canGetAccessibilityNodeInfoLocked(
            int i,
            AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection,
            int i2) {
        return (abstractAccessibilityServiceConnection.getCapabilities() & 1) != 0
                && isRetrievalAllowingWindowLocked(i, i2);
    }

    public final boolean checkAccessibilityAccess(
            AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        String packageName = abstractAccessibilityServiceConnection.mComponentName.getPackageName();
        ResolveInfo resolveInfo =
                abstractAccessibilityServiceConnection.getServiceInfo().getResolveInfo();
        if (resolveInfo == null) {
            return true;
        }
        int i = resolveInfo.serviceInfo.applicationInfo.uid;
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = abstractAccessibilityServiceConnection.mAttributionTag;
        try {
            if (OWN_PROCESS_ID == callingPid) {
                return this.mAppOpsManager.noteOpNoThrow(
                                "android:access_accessibility", i, packageName, str, null)
                        == 0;
            }
            return this.mAppOpsManager.noteOp(
                            "android:access_accessibility", i, packageName, str, null)
                    == 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void checkForAccessibilityPermissionOrRole() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY")
                == 0) {
            return;
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            RoleManager roleManager =
                    (RoleManager) this.mContext.getSystemService(RoleManager.class);
            if (roleManager != null) {
                List roleHoldersAsUser =
                        roleManager.getRoleHoldersAsUser(
                                "android.app.role.COMPANION_DEVICE_APP_STREAMING",
                                UserHandle.getUserHandleForUid(callingUid));
                String[] packagesForUid = this.mPackageManager.getPackagesForUid(callingUid);
                if (packagesForUid != null) {
                    for (String str : packagesForUid) {
                        if (roleHoldersAsUser.contains(str)) {
                            return;
                        }
                    }
                }
            }
            throw new SecurityException(
                    "Cannot register a proxy for a device without the"
                        + " android.app.role.COMPANION_DEVICE_APP_STREAMING role or the"
                        + " MANAGE_ACCESSIBILITY permission.");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String[] computeValidReportedPackages(int i, String str) {
        ArraySet hostedWidgetPackages;
        if (UserHandle.getAppId(i) == 1000) {
            return EmptyArray.STRING;
        }
        String[] strArr = {str};
        AppWidgetManagerInternal appWidgetManagerInternal = this.mAppWidgetService;
        if (appWidgetManagerInternal == null
                || (hostedWidgetPackages = appWidgetManagerInternal.getHostedWidgetPackages(i))
                        == null
                || hostedWidgetPackages.isEmpty()) {
            return strArr;
        }
        String[] strArr2 = new String[hostedWidgetPackages.size() + 1];
        int i2 = 0;
        System.arraycopy(strArr, 0, strArr2, 0, 1);
        int size = hostedWidgetPackages.size();
        while (i2 < size) {
            int i3 = 1 + i2;
            strArr2[i3] = (String) hostedWidgetPackages.valueAt(i2);
            i2 = i3;
        }
        return strArr2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isRetrievalAllowingWindowLocked(int r9, int r10) {
        /*
            r8 = this;
            int r0 = android.os.Binder.getCallingUid()
            r1 = 1
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r0 != r2) goto La
            return r1
        La:
            int r0 = android.os.Binder.getCallingUid()
            int r0 = android.os.UserHandle.getAppId(r0)
            if (r0 != r2) goto L15
            return r1
        L15:
            int r0 = android.os.Binder.getCallingUid()
            r2 = 2000(0x7d0, float:2.803E-42)
            r3 = 0
            if (r0 != r2) goto L70
            long r4 = android.os.Binder.clearCallingIdentity()
            com.android.server.accessibility.AccessibilityWindowManager r0 = r8.mAccessibilityWindowManager     // Catch: java.lang.Throwable -> L6b
            android.os.IBinder r0 = r0.getWindowTokenForUserAndWindowIdLocked(r9, r10)     // Catch: java.lang.Throwable -> L6b
            if (r0 != 0) goto L2f
        L2a:
            android.os.Binder.restoreCallingIdentity(r4)
            r0 = r3
            goto L68
        L2f:
            com.android.server.accessibility.AccessibilityWindowManager r2 = r8.mAccessibilityWindowManager     // Catch: java.lang.Throwable -> L6b
            boolean r6 = r2.traceWMEnabled()     // Catch: java.lang.Throwable -> L6b
            if (r6 == 0) goto L4c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6b
            java.lang.String r7 = "token="
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L6b
            r6.append(r0)     // Catch: java.lang.Throwable -> L6b
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L6b
            java.lang.String r7 = "getWindowOwnerUserId"
            r2.logTraceWM(r7, r6)     // Catch: java.lang.Throwable -> L6b
        L4c:
            com.android.server.wm.WindowManagerInternal r2 = r2.mWindowManagerInternal     // Catch: java.lang.Throwable -> L6b
            int r0 = r2.getWindowOwnerUserId(r0)     // Catch: java.lang.Throwable -> L6b
            r2 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r0 != r2) goto L57
            goto L2a
        L57:
            android.os.UserManager r2 = r8.mUserManager     // Catch: java.lang.Throwable -> L6b
            java.lang.String r6 = "no_debugging_features"
            android.os.UserHandle r0 = android.os.UserHandle.of(r0)     // Catch: java.lang.Throwable -> L6b
            boolean r0 = r2.hasUserRestriction(r6, r0)     // Catch: java.lang.Throwable -> L6b
            r0 = r0 ^ r1
            android.os.Binder.restoreCallingIdentity(r4)
        L68:
            if (r0 != 0) goto L70
            return r3
        L6b:
            r8 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)
            throw r8
        L70:
            com.android.server.accessibility.AccessibilityWindowManager r0 = r8.mAccessibilityWindowManager
            int r0 = r0.resolveParentWindowIdLocked(r10)
            com.android.server.accessibility.AccessibilityWindowManager r2 = r8.mAccessibilityWindowManager
            int r9 = r2.getActiveWindowId(r9)
            if (r0 != r9) goto L7f
            return r1
        L7f:
            com.android.server.accessibility.AccessibilityWindowManager r8 = r8.mAccessibilityWindowManager
            android.view.accessibility.AccessibilityWindowInfo r8 = r8.findA11yWindowInfoByIdLocked(r10)
            if (r8 == 0) goto L88
            goto L89
        L88:
            r1 = r3
        L89:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.AccessibilitySecurityPolicy.isRetrievalAllowingWindowLocked(int,"
                    + " int):boolean");
    }

    public final void onSwitchUserLocked(int i, Set set) {
        if (this.mCurrentUserId == i) {
            return;
        }
        ArraySet arraySet = new ArraySet(set);
        PolicyWarningUIController policyWarningUIController = this.mPolicyWarningUIController;
        policyWarningUIController.getClass();
        Message obtainMessage =
                PooledLambda.obtainMessage(
                        new PolicyWarningUIController$$ExternalSyntheticLambda2(
                                3, policyWarningUIController),
                        Integer.valueOf(i),
                        arraySet);
        Handler handler = policyWarningUIController.mMainHandler;
        handler.sendMessage(obtainMessage);
        for (int i2 = 0; i2 < this.mNonA11yCategoryServices.size(); i2++) {
            int i3 = this.mCurrentUserId;
            handler.sendMessage(
                    PooledLambda.obtainMessage(
                            new PolicyWarningUIController$$ExternalSyntheticLambda2(
                                    1, policyWarningUIController),
                            Integer.valueOf(i3),
                            (ComponentName) this.mNonA11yCategoryServices.valueAt(i2)));
        }
        this.mNonA11yCategoryServices.clear();
        this.mCurrentUserId = i;
    }

    public final int resolveCallingUserIdEnforcingPermissionsLocked(int i) {
        int callingUid = Binder.getCallingUid();
        int i2 = ((AccessibilityManagerService) this.mAccessibilityUserManager).mCurrentUserId;
        if (callingUid == 0 || callingUid == 1000 || callingUid == 2000) {
            return (i == -2 || i == -3) ? i2 : resolveProfileParentLocked(i);
        }
        int userId = UserHandle.getUserId(callingUid);
        if (userId == i) {
            return resolveProfileParentLocked(i);
        }
        if (resolveProfileParentLocked(userId) == i2 && (i == -2 || i == -3)) {
            return i2;
        }
        if (this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0
                || this.mContext.checkCallingPermission(
                                "android.permission.INTERACT_ACROSS_USERS_FULL")
                        == 0) {
            return (i == -2 || i == -3) ? i2 : resolveProfileParentLocked(i);
        }
        throw new SecurityException(
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        userId,
                        i,
                        "Call from user ",
                        " as user ",
                        " without permission INTERACT_ACROSS_USERS or INTERACT_ACROSS_USERS_FULL"
                            + " not allowed."));
    }

    public final int resolveProfileParentLocked(int i) {
        if (i != ((AccessibilityManagerService) this.mAccessibilityUserManager).mCurrentUserId) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    return profileParent.getUserHandle().getIdentifier();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return i;
    }

    public final String resolveValidReportedPackageLocked(
            CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            return null;
        }
        if (i == 1000) {
            return charSequence.toString();
        }
        String charSequence2 = charSequence.toString();
        int uid = UserHandle.getUid(i2, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPackageManagerInternal.isSameApp(
                    uid, UserHandle.getUserId(uid), 4194304L, charSequence2)) {
                return charSequence.toString();
            }
            AppWidgetManagerInternal appWidgetManagerInternal = this.mAppWidgetService;
            if ((appWidgetManagerInternal == null
                            || !ArrayUtils.contains(
                                    appWidgetManagerInternal.getHostedWidgetPackages(uid),
                                    charSequence2))
                    && this.mContext.checkPermission(
                                    "android.permission.ACT_AS_PACKAGE_FOR_ACCESSIBILITY", i3, uid)
                            != 0) {
                String[] packagesForUid = this.mPackageManager.getPackagesForUid(uid);
                if (ArrayUtils.isEmpty(packagesForUid)) {
                    return null;
                }
                return packagesForUid[0];
            }
            return charSequence.toString();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSendingNonA11yToolNotificationLocked(boolean z) {
        if (z == this.mSendNonA11yToolNotificationEnabled) {
            return;
        }
        this.mSendNonA11yToolNotificationEnabled = z;
        PolicyWarningUIController policyWarningUIController = this.mPolicyWarningUIController;
        policyWarningUIController.getClass();
        Message obtainMessage =
                PooledLambda.obtainMessage(
                        new PolicyWarningUIController$$ExternalSyntheticLambda0(
                                0, policyWarningUIController),
                        Boolean.valueOf(z));
        Handler handler = policyWarningUIController.mMainHandler;
        handler.sendMessage(obtainMessage);
        if (z) {
            for (int i = 0; i < this.mNonA11yCategoryServices.size(); i++) {
                handler.sendMessage(
                        PooledLambda.obtainMessage(
                                new PolicyWarningUIController$$ExternalSyntheticLambda2(
                                        0, policyWarningUIController),
                                Integer.valueOf(this.mCurrentUserId),
                                (ComponentName) this.mNonA11yCategoryServices.valueAt(i)));
            }
        }
    }
}
