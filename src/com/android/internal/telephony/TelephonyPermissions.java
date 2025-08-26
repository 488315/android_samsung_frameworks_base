package com.android.internal.telephony;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.UserHandle;
import android.permission.LegacyPermissionManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class TelephonyPermissions {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "TelephonyPermissions";
    private static final String PROPERTY_DEVICE_IDENTIFIER_ACCESS_RESTRICTIONS_DISABLED = "device_identifier_access_restrictions_disabled";
    private static final Map<String, Set<String>> sReportedDeviceIDPackages = new HashMap();

    private TelephonyPermissions() {
    }

    public static boolean checkCallingOrSelfReadPhoneState(Context context, int i, String str, String str2, String str3) {
        return checkReadPhoneState(context, i, Binder.getCallingPid(), Binder.getCallingUid(), str, str2, str3);
    }

    public static boolean checkCallingOrSelfReadPhoneStateNoThrow(Context context, int i, String str, String str2, String str3) {
        try {
            return checkCallingOrSelfReadPhoneState(context, i, str, str2, str3);
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean checkInternetPermissionNoThrow(Context context, String str) {
        try {
            context.enforcePermission(Manifest.permission.INTERNET, Binder.getCallingPid(), Binder.getCallingUid(), str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean checkCallingOrSelfReadNonDangerousPhoneStateNoThrow(Context context, String str) {
        try {
            context.enforcePermission(Manifest.permission.READ_BASIC_PHONE_STATE, Binder.getCallingPid(), Binder.getCallingUid(), str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean checkReadPhoneState(Context context, int i, int i2, int i3, String str, String str2, String str3) {
        try {
            try {
                context.enforcePermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE, i2, i3, str3);
                return true;
            } catch (SecurityException e) {
                if (SubscriptionManager.isValidSubscriptionId(i)) {
                    enforceCarrierPrivilege(context, i, i3, str3);
                    return true;
                }
                throw e;
            }
        } catch (SecurityException unused) {
            context.enforcePermission(Manifest.permission.READ_PHONE_STATE, i2, i3, str3);
            return ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).noteOpNoThrow(AppOpsManager.OPSTR_READ_PHONE_STATE, i3, str, str2, (String) null) == 0;
        }
    }

    public static boolean checkCarrierPrivilegeForSubId(Context context, int i) {
        return SubscriptionManager.isValidSubscriptionId(i) && getCarrierPrivilegeStatus(context, i, Binder.getCallingUid()) == 1;
    }

    public static boolean checkReadPhoneStateOnAnyActiveSub(Context context, int i, int i2, String str, String str2, String str3) {
        try {
            try {
                context.enforcePermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE, i, i2, str3);
                return true;
            } catch (SecurityException unused) {
                return checkCarrierPrivilegeForAnySubId(context, i2);
            }
        } catch (SecurityException unused2) {
            context.enforcePermission(Manifest.permission.READ_PHONE_STATE, i, i2, str3);
            return ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).noteOpNoThrow(AppOpsManager.OPSTR_READ_PHONE_STATE, i2, str, str2, (String) null) == 0;
        }
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(Context context, String str, String str2, String str3) {
        return checkCallingOrSelfReadDeviceIdentifiers(context, -1, str, str2, str3);
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(Context context, int i, String str, String str2, String str3) {
        if (checkCallingOrSelfUseIccAuthWithDeviceIdentifier(context, str, str2, str3)) {
            return true;
        }
        return checkPrivilegedReadPermissionOrCarrierPrivilegePermission(context, i, str, str2, str3, true, true);
    }

    public static boolean checkCallingOrSelfReadSubscriberIdentifiers(Context context, int i, String str, String str2, String str3) {
        return checkCallingOrSelfReadSubscriberIdentifiers(context, i, str, str2, str3, true);
    }

    public static boolean checkCallingOrSelfReadSubscriberIdentifiers(Context context, int i, String str, String str2, String str3, boolean z) {
        if (checkCallingOrSelfUseIccAuthWithDeviceIdentifier(context, str, str2, str3)) {
            return true;
        }
        return checkPrivilegedReadPermissionOrCarrierPrivilegePermission(context, i, str, str2, str3, false, z);
    }

    private static void throwSecurityExceptionAsUidDoesNotHaveAccess(String str, int i) {
        throw new SecurityException(str + ": The uid " + i + " does not meet the requirements to access device identifiers.");
    }

    private static boolean checkPrivilegedReadPermissionOrCarrierPrivilegePermission(Context context, int i, String str, String str2, String str3, boolean z, boolean z2) {
        String str4;
        String str5;
        int i2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (checkCarrierPrivilegeForSubId(context, i)) {
            return true;
        }
        if (z && checkCarrierPrivilegeForAnySubId(context, callingUid)) {
            return true;
        }
        try {
            int iCheckDeviceIdentifierAccess = ((LegacyPermissionManager) context.getSystemService(Context.LEGACY_PERMISSION_SERVICE)).checkDeviceIdentifierAccess(str, str3, str2, callingPid, callingUid);
            str4 = str;
            str5 = str3;
            i2 = callingUid;
            if (iCheckDeviceIdentifierAccess == 0) {
                return true;
            }
        } catch (SecurityException unused) {
            str4 = str;
            str5 = str3;
            i2 = callingUid;
            throwSecurityExceptionAsUidDoesNotHaveAccess(str5, i2);
        }
        if (z2) {
            return reportAccessDeniedToReadIdentifiers(context, i, callingPid, i2, str4, str5);
        }
        return false;
    }

    private static boolean reportAccessDeniedToReadIdentifiers(Context context, int i, int i2, int i3, String str, String str2) {
        ApplicationInfo applicationInfoAsUser;
        Set<String> hashSet;
        try {
            applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(str, 0, UserHandle.getUserHandleForUid(i3));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "Exception caught obtaining package info for package " + str, e);
            applicationInfoAsUser = null;
        }
        Map<String, Set<String>> map = sReportedDeviceIDPackages;
        boolean zContainsKey = map.containsKey(str);
        if (!zContainsKey || !map.get(str).contains(str2)) {
            if (!zContainsKey) {
                hashSet = new HashSet<>();
                map.put(str, hashSet);
            } else {
                hashSet = map.get(str);
            }
            hashSet.add(str2);
            TelephonyCommonStatsLog.write(172, str, str2, false, false);
        }
        Log.w(LOG_TAG, "reportAccessDeniedToReadIdentifiers:" + str + ":" + str2 + ":" + i);
        if (applicationInfoAsUser != null && applicationInfoAsUser.targetSdkVersion < 29 && (context.checkPermission(Manifest.permission.READ_PHONE_STATE, i2, i3) == 0 || checkCarrierPrivilegeForSubId(context, i))) {
            return false;
        }
        throwSecurityExceptionAsUidDoesNotHaveAccess(str2, i3);
        return true;
    }

    public static boolean checkCallingOrSelfUseIccAuthWithDeviceIdentifier(Context context, String str, String str2, String str3) {
        if (str == null) {
            return false;
        }
        int iNoteOpNoThrow = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).noteOpNoThrow(AppOpsManager.OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER, Binder.getCallingUid(), str, str2, str3);
        if (iNoteOpNoThrow != 0) {
            if (iNoteOpNoThrow == 3) {
                return context.checkCallingOrSelfPermission(Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER) == 0;
            }
            if (iNoteOpNoThrow != 4) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkReadCallLog(Context context, int i, int i2, int i3, String str, String str2) {
        if (context.checkPermission(Manifest.permission.READ_CALL_LOG, i2, i3) == 0) {
            return ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).noteOpNoThrow(AppOpsManager.OPSTR_READ_CALL_LOG, i3, str, str2, (String) null) == 0;
        }
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        enforceCarrierPrivilege(context, i, i3, "readCallLog");
        return true;
    }

    public static boolean checkCallingOrSelfReadPhoneNumber(Context context, int i, String str, String str2, String str3) {
        return checkReadPhoneNumber(context, i, Binder.getCallingPid(), Binder.getCallingUid(), str, str2, str3);
    }

    public static boolean checkReadPhoneNumber(Context context, int i, int i2, int i3, String str, String str2, String str3) {
        int iCheckPhoneNumberAccess = ((LegacyPermissionManager) context.getSystemService(Context.LEGACY_PERMISSION_SERVICE)).checkPhoneNumberAccess(str, str3, str2, i2, i3);
        if (iCheckPhoneNumberAccess == 0) {
            return true;
        }
        if (SubscriptionManager.isValidSubscriptionId(i) && getCarrierPrivilegeStatus(context, i, i3) == 1) {
            return true;
        }
        if (iCheckPhoneNumberAccess == 1) {
            return false;
        }
        throw new SecurityException(str3 + ": Neither user " + i3 + " nor current process has android.permission.READ_PHONE_STATE, android.permission.READ_SMS, or android.permission.READ_PHONE_NUMBERS");
    }

    public static void enforceCallingOrSelfModifyPermissionOrCarrierPrivilege(Context context, int i, String str) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.MODIFY_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static boolean checkLastKnownCellIdAccessPermission(Context context) {
        return context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_LAST_KNOWN_CELL_ID) == 0;
    }

    public static void enforceCallingOrSelfReadPhoneStatePermissionOrCarrierPrivilege(Context context, int i, String str) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfReadPrivilegedPhoneStatePermissionOrCarrierPrivilege(Context context, int i, String str) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfReadPrecisePhoneStatePermissionOrCarrierPrivilege(Context context, int i, String str) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE) == 0 || context.checkCallingOrSelfPermission(Manifest.permission.READ_PRECISE_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfCarrierPrivilege(Context context, int i, String str) {
        enforceCarrierPrivilege(context, i, Binder.getCallingUid(), str);
    }

    private static void enforceCarrierPrivilege(Context context, int i, int i2, String str) {
        if (getCarrierPrivilegeStatus(context, i, i2) != 1) {
            throw new SecurityException(str);
        }
    }

    private static boolean checkCarrierPrivilegeForAnySubId(Context context, int i) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int[] completeActiveSubscriptionIdList = subscriptionManager.getCompleteActiveSubscriptionIdList();
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            for (int i2 : completeActiveSubscriptionIdList) {
                if (getCarrierPrivilegeStatus(context, i2, i) == 1) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    private static int getCarrierPrivilegeStatus(Context context, int i, int i2) {
        if (isSystemOrPhone(i2)) {
            return 1;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i).getCarrierPrivilegeStatus(i2);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static void enforceAnyPermissionGranted(Context context, int i, String str, String... strArr) {
        if (strArr.length == 0) {
            return;
        }
        for (String str2 : strArr) {
            if (context.checkCallingOrSelfPermission(str2) == 0) {
                return;
            }
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(": Neither user ");
        sb.append(i);
        sb.append(" nor current process has ");
        sb.append(strArr[0]);
        for (int i2 = 1; i2 < strArr.length; i2++) {
            sb.append(" or ");
            sb.append(strArr[i2]);
        }
        throw new SecurityException(sb.toString());
    }

    public static void enforceAnyPermissionGrantedOrCarrierPrivileges(Context context, int i, int i2, String str, String... strArr) {
        enforceAnyPermissionGrantedOrCarrierPrivileges(context, i, i2, false, str, strArr);
    }

    public static void enforceAnyPermissionGrantedOrCarrierPrivileges(Context context, int i, int i2, boolean z, String str, String... strArr) {
        if (strArr.length == 0) {
            return;
        }
        for (String str2 : strArr) {
            if (context.checkCallingOrSelfPermission(str2) == 0) {
                return;
            }
        }
        if (z) {
            if (checkCarrierPrivilegeForAnySubId(context, i2)) {
                return;
            }
        } else if (checkCarrierPrivilegeForSubId(context, i)) {
            return;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(": Neither user ");
        sb.append(i2);
        sb.append(" nor current process has ");
        sb.append(strArr[0]);
        for (int i3 = 1; i3 < strArr.length; i3++) {
            sb.append(" or ");
            sb.append(strArr[i3]);
        }
        sb.append(" or carrier privileges. subId=" + i + ", allowCarrierPrivilegeOnAnySub=" + z);
        throw new SecurityException(sb.toString());
    }

    public static void enforceShellOnly(int i, String str) {
        if (UserHandle.isSameApp(i, 2000) || UserHandle.isSameApp(i, 0)) {
            return;
        }
        throw new SecurityException(str + ": Only shell user can call it");
    }

    public static int getTargetSdk(Context context, String str) {
        try {
            ApplicationInfo applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(str, 0, UserHandle.getUserHandleForUid(Binder.getCallingUid()));
            if (applicationInfoAsUser != null) {
                return applicationInfoAsUser.targetSdkVersion;
            }
            return Integer.MAX_VALUE;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(LOG_TAG, "Failed to get package info for pkg=" + str + ", uid=" + Binder.getCallingUid());
            return Integer.MAX_VALUE;
        }
    }

    public static boolean checkSubscriptionAssociatedWithUser(Context context, int i, UserHandle userHandle, String str) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (telephonyManager != null) {
                try {
                    if (telephonyManager.isEmergencyNumber(str)) {
                        Log.d(LOG_TAG, "checkSubscriptionAssociatedWithUser: destAddr is emergency number");
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                        return true;
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Cannot verify if destAddr is an emergency number: " + e);
                }
            }
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return checkSubscriptionAssociatedWithUser(context, i, userHandle);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    public static boolean checkSubscriptionAssociatedWithUser(Context context, int i, UserHandle userHandle) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        if (subscriptionManager != null) {
            try {
                if (!subscriptionManager.isSubscriptionAssociatedWithUser(i, userHandle)) {
                    Log.e(LOG_TAG, "User[User ID:" + userHandle.getIdentifier() + "] is not associated with Subscription ID:" + i);
                    return false;
                }
            } catch (IllegalArgumentException unused) {
                Log.e(LOG_TAG, "Subscription[Subscription ID:" + i + "] has no records on device");
                return false;
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
        Binder.restoreCallingIdentity(jClearCallingIdentity);
        return true;
    }

    public static boolean isSystemOrPhone(int i) {
        return UserHandle.isSameApp(i, 1000) || UserHandle.isSameApp(i, 1001);
    }

    public static boolean isRootOrShell(int i) {
        return UserHandle.isSameApp(i, 0) || UserHandle.isSameApp(i, 2000);
    }
}
