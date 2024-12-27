package com.samsung.android.knoxguard.service.utils;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knoxguard.service.KnoxGuardNative;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;

import java.util.Iterator;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IntegritySeUtil {
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_ADMIN_RECEIVER = 64;
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_ALARM_SERVICE = 1024;
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_KG_EVENT_SERVICE = 512;
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_KG_PROVIDER = 2048;
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_SELFUPDATE_RECEIVER = 256;
    public static final int CLIENT2_INTEGRITY_COMPONENT_ERROR_SYSTEM_INTENT_RECEIVER = 128;
    public static final int CLIENT_INTEGRITY_BASE2 = 4097;
    public static final int CLIENT_INTEGRITY_ERROR_ENABLED = 4;
    public static final int CLIENT_INTEGRITY_ERROR_INVALID_VERSION = 8;
    public static final int CLIENT_INTEGRITY_ERROR_SIGNATURE = 2;
    public static final long KG2_STARTED_VERSION_CODE = 300000000;
    public static final String TAG = "KG.IntegritySeUtil";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntegritySeResult {
        public boolean isOk = false;
        public boolean validSignature = false;
        public boolean enabled = false;
        public boolean validVersion = false;
        public boolean enabledAdminReceiver = false;
        public boolean enabledSystemIntentReceiver = false;
        public boolean enabledSelfUpdateReceiver = false;
        public boolean enabledKgEventService = false;
        public boolean enabledAlarmService = false;
        public boolean enabledKGProvider = false;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TAIntegrityResult {
        public boolean isOk = false;
        public int errorCode = 0;
    }

    public static boolean checkAPSerialIntegrity(int i) {
        return !Utils.isSingleOtpBitFusedAndStateIsNotCompleted(i) || isAPSerialValid();
    }

    public static boolean checkComponentsAndEnableComponentWithFlag(
            Context context, IntegritySeResult integritySeResult, boolean z) {
        boolean isKGComponentsEnabled = isKGComponentsEnabled(context, integritySeResult);
        if (!z || isKGComponentsEnabled || !MaintenanceModeManager.isInMaintenanceMode()) {
            return isKGComponentsEnabled;
        }
        Slog.d(TAG, "Enabling components due to maintenance mode");
        enableComponents(context);
        return isKGComponentsEnabled(context, integritySeResult);
    }

    public static IntegritySeResult checkKGClientIntegrity(Context context, int i) {
        return checkKGClientIntegrityAndEnableComponentsWithFlag(context, i, false);
    }

    public static IntegritySeResult checkKGClientIntegrityAndEnableComponent(
            Context context, int i) {
        return checkKGClientIntegrityAndEnableComponentsWithFlag(context, i, true);
    }

    public static IntegritySeResult checkKGClientIntegrityAndEnableComponentsWithFlag(
            Context context, int i, boolean z) {
        String str = TAG;
        Slog.i(str, "checkKGClientIntegrity()");
        Slog.i(str, "kgState() : " + i);
        IntegritySeResult integritySeResult = new IntegritySeResult();
        if (4 == i) {
            Slog.d(str, "checkKGClientIntegrity() KG_STATE_COMPLETED. Do nothing.");
            integritySeResult.isOk = true;
            return integritySeResult;
        }
        try {
            boolean checkSignatures = checkSignatures(context);
            boolean isSystemApp = isSystemApp(context);
            boolean isEnabled = isEnabled(context);
            boolean isValidVersion = isValidVersion(context);
            Slog.d(str, "checkSignatures : " + checkSignatures);
            Slog.d(str, "isSystemApp : " + isSystemApp);
            Slog.d(str, "isEnabled : " + isEnabled);
            Slog.d(str, "isValidVersion : " + isValidVersion);
            integritySeResult.validSignature = checkSignatures;
            integritySeResult.enabled = isEnabled;
            integritySeResult.validVersion = isValidVersion;
            boolean checkComponentsAndEnableComponentWithFlag =
                    checkComponentsAndEnableComponentWithFlag(context, integritySeResult, z);
            Slog.d(str, "isComponentEnabled : " + checkComponentsAndEnableComponentWithFlag);
            if (!isValidVersion) {
                Slog.d(str, "kgclient is invalid. makes client disable");
                disableClient(context);
            }
            integritySeResult.isOk =
                    checkSignatures
                            & isEnabled
                            & isValidVersion
                            & checkComponentsAndEnableComponentWithFlag;
            return integritySeResult;
        } catch (Exception e) {
            Slog.e(TAG, e.getMessage());
            return integritySeResult;
        }
    }

    public static boolean checkSignatures(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo =
                    packageManager.getPackageInfo("com.samsung.android.kgclient", 64);
            String str = TAG;
            Slog.d(str, "pkgInfo : " + packageInfo.toString());
            if (packageManager.checkSignatures("android", "com.samsung.android.kgclient") != 0) {
                Slog.i(str, "KG Client signature doesn't match with platform.");
                return false;
            }
            Slog.i(str, "KG Client signature match with platform.");
            return true;
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(e, "checkKGClientIntegrity Exception. ", TAG);
            return false;
        }
    }

    public static void checkSystemUiIntegrity(Context context) {
        Slog.i(TAG, "checkSystemUiIntegrity()");
        int testSystemUiCorrupted = testSystemUiCorrupted(context);
        if (testSystemUiCorrupted > 0) {
            Utils.powerOff(context, testSystemUiCorrupted);
        }
    }

    public static TAIntegrityResult checkTaIntegrity(int i, int i2) {
        TAIntegrityResult tAIntegrityResult = new TAIntegrityResult();
        try {
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(e, "checkTaIntegrity Exception. ", TAG);
        }
        if (!Utils.isOtpBitFusedWithActive()
                && !Utils.isSingleOtpBitFusedAndStateIsNotCompleted(i)) {
            tAIntegrityResult.isOk = true;
            return tAIntegrityResult;
        }
        if (isTaErrorCode(i2)) {
            tAIntegrityResult.errorCode = i2;
            tAIntegrityResult.isOk = false;
        } else {
            tAIntegrityResult.isOk = true;
        }
        return tAIntegrityResult;
    }

    public static void disableClient(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            Slog.w(TAG, "PackageManager is null");
            return;
        }
        try {
            Slog.i(TAG, "disable kgclient");
            packageManager.setApplicationEnabledSetting("com.samsung.android.kgclient", 2, 0);
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("disable exception: "), TAG);
        }
    }

    public static void enableAppOpIfNotAllowed(
            PackageInfo packageInfo, AppOpsManager appOpsManager, int i) {
        if (appOpsManager.checkOpNoThrow(
                        i, packageInfo.applicationInfo.uid, "com.samsung.android.kgclient")
                != 0) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "allow ", TAG);
            appOpsManager.setMode(
                    i, packageInfo.applicationInfo.uid, "com.samsung.android.kgclient", 0);
        }
    }

    public static void enableComponent(Context context, String str, int i) {
        try {
            ComponentName componentName = new ComponentName("com.samsung.android.kgclient", str);
            if (MaintenanceModeManager.isInMaintenanceMode()) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager == null) {
                    Slog.w(TAG, "IPackageManager is null");
                    return;
                } else {
                    packageManager.setComponentEnabledSetting(
                            componentName, 1, 1, i, (String) null);
                    return;
                }
            }
            PackageManager packageManager2 = context.getPackageManager();
            if (packageManager2 == null) {
                Slog.w(TAG, "PackageManager is null");
            } else {
                packageManager2.setComponentEnabledSetting(componentName, 1, 1);
            }
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(e, "enableComponent Exception. ", TAG);
        }
    }

    public static void enableComponents(Context context) {
        Slog.i(TAG, "enableComponents()");
        int activeUserId = getActiveUserId();
        enableComponent(
                context, "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver", activeUserId);
        enableComponent(
                context,
                "com.samsung.android.kgclient.receiver.SystemIntentReceiver",
                activeUserId);
        enableComponent(
                context,
                "com.samsung.android.kgclient.selfupdate.SelfupdateReceiver",
                activeUserId);
        enableComponent(
                context, "com.samsung.android.kgclient.events.KGEventService", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.alarm.AlarmService", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.provider.KGProvider", activeUserId);
    }

    public static int getActiveUserId() {
        int i;
        try {
            i = ActivityManager.semGetCurrentUser();
        } catch (Throwable th) {
            th = th;
            i = 0;
        }
        try {
            Slog.i(TAG, "user id is " + i);
        } catch (Throwable th2) {
            th = th2;
            Slog.e(TAG, "semGetCurrentUser error: " + th.getMessage());
            return i;
        }
        return i;
    }

    public static long getClientVersionCode(Context context, long j) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            Slog.w(TAG, "PackageManager is null");
            return j;
        }
        try {
            PackageInfo packageInfo =
                    packageManager.getPackageInfo("com.samsung.android.kgclient", 0);
            if (packageInfo != null) {
                return packageInfo.getLongVersionCode();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "Client Notfound : " + e);
        }
        return j;
    }

    public static String getFailedIntegrityResult(IntegritySeResult integritySeResult) {
        StringBuilder sb = new StringBuilder();
        if (!integritySeResult.validSignature) {
            sb.append("SIGNATURE,");
        }
        if (!integritySeResult.enabled) {
            sb.append("ENABLED,");
        }
        if (!integritySeResult.validVersion) {
            sb.append("VERSION,");
        }
        if (!integritySeResult.enabledAdminReceiver) {
            sb.append("KGDeviceAdminReceiver,");
        }
        if (!integritySeResult.enabledSystemIntentReceiver) {
            sb.append("SystemIntentReceiver,");
        }
        if (!integritySeResult.enabledSelfUpdateReceiver) {
            sb.append("SelfUpdateReceiver,");
        }
        if (!integritySeResult.enabledKgEventService) {
            sb.append("KgEventService,");
        }
        if (!integritySeResult.enabledAlarmService) {
            sb.append("AlarmService,");
        }
        if (!integritySeResult.enabledKGProvider) {
            sb.append("KGProvider,");
        }
        return sb.toString();
    }

    public static String getTALockScreenErrorCode(int i) {
        if (i == -1006) {
            return Constants.ERROR_KGTA_INIT_FAILED;
        }
        if (i == 513) {
            return Constants.ERROR_KGTA_RPMB_UNAVAILABLE;
        }
        if (i == 519) {
            return Constants.ERROR_KGTA_HMAC_MISMATCH;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "not listed code : ", TAG);
        return "";
    }

    public static boolean hasEnabledComponent(
            ComponentInfo[] componentInfoArr, String str, String str2) {
        if (componentInfoArr == null) {
            return false;
        }
        for (ComponentInfo componentInfo : componentInfoArr) {
            if (componentInfo.name.equals(str2) && componentInfo.packageName.equals(str)) {
                return componentInfo.isEnabled();
            }
        }
        return false;
    }

    public static boolean isAPSerialValid() {
        String stringResult = KnoxGuardSeService.getStringResult(KnoxGuardNative.tz_getTAInfo(4));
        boolean z =
                !TextUtils.isEmpty(stringResult)
                        && stringResult.length() == 32
                        && Pattern.matches("[a-fA-F0-9]{32}", stringResult);
        DeviceIdleController$$ExternalSyntheticOutline0.m("isAPSerialValid - ", TAG, z);
        return z;
    }

    public static boolean isEnabled(Context context) {
        try {
            return context.getPackageManager()
                    .getApplicationInfo("com.samsung.android.kgclient", 0)
                    .enabled;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "isEnabled NameNotFoundException : " + e);
            return false;
        }
    }

    public static boolean isKGComponentsEnabled(
            Context context, IntegritySeResult integritySeResult) {
        PackageInfo packageInfo;
        try {
            if (MaintenanceModeManager.isInMaintenanceMode()) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager == null) {
                    Slog.w(TAG, "IPackageManager is null");
                    return false;
                }
                packageInfo =
                        packageManager.getPackageInfo(
                                "com.samsung.android.kgclient", 14L, getActiveUserId());
            } else {
                PackageManager packageManager2 = context.getPackageManager();
                if (packageManager2 == null) {
                    Slog.w(TAG, "PackageManager is null");
                    return false;
                }
                packageInfo = packageManager2.getPackageInfo("com.samsung.android.kgclient", 14);
            }
            integritySeResult.enabledAdminReceiver =
                    hasEnabledComponent(
                            packageInfo.receivers,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver");
            integritySeResult.enabledSystemIntentReceiver =
                    hasEnabledComponent(
                            packageInfo.receivers,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.receiver.SystemIntentReceiver");
            integritySeResult.enabledSelfUpdateReceiver =
                    hasEnabledComponent(
                            packageInfo.receivers,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.selfupdate.SelfupdateReceiver");
            integritySeResult.enabledKgEventService =
                    hasEnabledComponent(
                            packageInfo.services,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.events.KGEventService");
            integritySeResult.enabledAlarmService =
                    hasEnabledComponent(
                            packageInfo.services,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.alarm.AlarmService");
            integritySeResult.enabledKGProvider =
                    hasEnabledComponent(
                            packageInfo.providers,
                            "com.samsung.android.kgclient",
                            "com.samsung.android.kgclient.provider.KGProvider");
        } catch (PackageManager.NameNotFoundException | RemoteException e) {
            Slog.e(TAG, "isKGComponentsEnabled error: " + e.getMessage());
        }
        return integritySeResult.enabledAdminReceiver
                && integritySeResult.enabledSystemIntentReceiver
                && integritySeResult.enabledSelfUpdateReceiver
                && integritySeResult.enabledKgEventService
                && integritySeResult.enabledAlarmService
                && integritySeResult.enabledKGProvider;
    }

    public static boolean isSystemApp(Context context) {
        try {
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "isSystemApp NameNotFoundException : " + e);
        }
        return (context.getPackageManager()
                                .getApplicationInfo("com.samsung.android.kgclient", 0)
                                .flags
                        & 1)
                != 0;
    }

    public static boolean isTaErrorCode(int i) {
        return 513 == i || 519 == i || -1006 == i;
    }

    public static boolean isValidVersion(Context context) {
        return KG2_STARTED_VERSION_CODE <= getClientVersionCode(context, KG2_STARTED_VERSION_CODE);
    }

    public static void setInitialState(
            Context context,
            int i,
            AppOpsManager.OnOpChangedInternalListener onOpChangedInternalListener) {
        String str = TAG;
        Slog.i(str, "setInitialState, state : " + i);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Slog.e(str, "DPM is not available");
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getApplicationInfo("com.samsung.android.kgclient", 0);
            if (!checkSignatures(context)) {
                Slog.e(str, "KGClient is malicious, it will be locked");
                return;
            }
            String stringSystemProperty =
                    Utils.getStringSystemProperty(
                            Constants.KG_OTP_BIT_SYSTEM_PROPERTY, Constants.OTP_BIT_KG_UNKNOWN);
            boolean equals = Constants.OTP_BIT_KG_ENABLED.equals(stringSystemProperty);
            boolean z =
                    "1".equals(stringSystemProperty)
                            || Constants.OTP_BIT_KG_COMPLETED.equals(stringSystemProperty);
            if (equals || (z && 4 != i)) {
                try {
                    int callingUserId = UserHandle.getCallingUserId();
                    IPackageManager packageManager2 = AppGlobals.getPackageManager();
                    if (packageManager2.getApplicationHiddenSettingAsUser(
                            "com.samsung.android.kgclient", callingUserId)) {
                        packageManager2.setApplicationHiddenSettingAsUser(
                                "com.samsung.android.kgclient", false, callingUserId);
                    }
                } catch (RemoteException e) {
                    Slog.e(TAG, "RemoteException " + e.getMessage());
                }
                ComponentName componentName =
                        new ComponentName(
                                "com.samsung.android.kgclient",
                                "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver");
                try {
                    if (2 == i || 3 == i) {
                        Slog.d(
                                TAG,
                                "setInitialState call edm admin api for adding edm services!!!");
                        EnterpriseDeviceManager enterpriseDeviceManager =
                                EnterpriseDeviceManager.getInstance(context);
                        if (enterpriseDeviceManager != null) {
                            enterpriseDeviceManager.setActiveAdmin(componentName, false);
                        }
                    } else {
                        devicePolicyManager.setActiveAdmin(componentName, false);
                    }
                } catch (Exception e2) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            e2, "com.samsung.android.kgclientsetActiveAdmin", TAG);
                }
                try {
                    AppOpsManager appOpsManager =
                            (AppOpsManager) context.getSystemService("appops");
                    appOpsManager.startWatchingMode(
                            -1,
                            "com.samsung.android.kgclient",
                            (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
                    PackageInfo packageInfo =
                            packageManager.getPackageInfo("com.samsung.android.kgclient", 0);
                    Iterator it = Constants.PROTECTED_APP_OPS_LIST.iterator();
                    while (it.hasNext()) {
                        enableAppOpIfNotAllowed(
                                packageInfo, appOpsManager, ((Integer) it.next()).intValue());
                    }
                } catch (Throwable th) {
                    Slog.e(TAG, "Error - appOps : " + th.getMessage());
                }
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Slog.e(TAG, "Client Notfound : " + e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041 A[Catch: Exception -> 0x0047, TRY_LEAVE, TryCatch #2 {Exception -> 0x0047, blocks: (B:13:0x003b, B:15:0x0041), top: B:12:0x003b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int testSystemUiCorrupted(android.content.Context r9) {
        /*
            java.lang.String r9 = "com.android.systemui"
            android.content.pm.IPackageManager r6 = android.app.AppGlobals.getPackageManager()
            r7 = 0
            if (r6 != 0) goto L11
            java.lang.String r9 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.TAG
            java.lang.String r0 = "PackageManager is null"
            android.util.Slog.w(r9, r0)
            return r7
        L11:
            r0 = 0
            android.content.pm.ApplicationInfo r0 = r6.getApplicationInfo(r9, r0, r7)     // Catch: java.lang.Exception -> L2c
            boolean r0 = r0.enabled     // Catch: java.lang.Exception -> L2c
            r8 = 1
            r0 = r0 ^ r8
            if (r0 == 0) goto L2a
            java.lang.String r1 = "com.android.systemui"
            r4 = 0
            r5 = 0
            r2 = 1
            r3 = 0
            r0 = r6
            r0.setApplicationEnabledSetting(r1, r2, r3, r4, r5)     // Catch: java.lang.Exception -> L28
            goto L3b
        L28:
            r0 = move-exception
            goto L2e
        L2a:
            r8 = r7
            goto L3b
        L2c:
            r0 = move-exception
            r8 = r7
        L2e:
            java.lang.String r1 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "testSystemUiCorrupted : Exception in checking enabled value: "
            r2.<init>(r3)
            com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r0, r2, r1)
        L3b:
            boolean r0 = r6.getApplicationHiddenSettingAsUser(r9, r7)     // Catch: java.lang.Exception -> L47
            if (r0 == 0) goto L55
            r8 = r8 | 2
            r6.setApplicationHiddenSettingAsUser(r9, r7, r7)     // Catch: java.lang.Exception -> L47
            goto L55
        L47:
            r9 = move-exception
            java.lang.String r0 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "testSystemUiCorrupted : Exception in checking hidden value: "
            r1.<init>(r2)
            com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r9, r1, r0)
        L55:
            java.lang.String r9 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.TAG
            java.lang.String r0 = "testSystemUiCorrupted: "
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r8, r0, r9)
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knoxguard.service.utils.IntegritySeUtil.testSystemUiCorrupted(android.content.Context):int");
    }

    public static int toErrorCode(IntegritySeResult integritySeResult) {
        if (integritySeResult == null) {
            return 0;
        }
        return (integritySeResult.validSignature ? 0 : 2)
                | CLIENT_INTEGRITY_BASE2
                | (integritySeResult.enabled ? 0 : 4)
                | (integritySeResult.validVersion ? 0 : 8)
                | (integritySeResult.enabledAdminReceiver ? 0 : 64)
                | (integritySeResult.enabledSystemIntentReceiver ? 0 : 128)
                | (integritySeResult.enabledSelfUpdateReceiver ? 0 : 256)
                | (integritySeResult.enabledKgEventService ? 0 : 512)
                | (integritySeResult.enabledAlarmService ? 0 : 1024)
                | (integritySeResult.enabledKGProvider ? 0 : 2048);
    }
}
