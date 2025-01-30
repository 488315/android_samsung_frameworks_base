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
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.KnoxGuardNative;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.util.Iterator;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class IntegritySeUtil {
    public static final String TAG = "KG." + IntegritySeUtil.class.getSimpleName();

    public class IntegritySeResult {
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

    public static IntegritySeResult checkKGClientIntegrity(Context context, int i) {
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
            boolean checkComponents = checkComponents(context, integritySeResult);
            Slog.d(str, "isComponentEnabled : " + checkComponents);
            if (!isValidVersion) {
                Slog.d(str, "kgclient is invalid. makes client disable");
                disableClient(context);
            }
            integritySeResult.isOk = checkSignatures & isEnabled & isValidVersion & checkComponents;
            return integritySeResult;
        } catch (Exception e) {
            Slog.e(TAG, e.getMessage());
            return integritySeResult;
        }
    }

    public static int getActiveUserId() {
        int i;
        try {
            i = ActivityManager.semGetCurrentUser();
            try {
                Slog.i(TAG, "user id is " + i);
            } catch (Throwable th) {
                th = th;
                Slog.e(TAG, "semGetCurrentUser error: " + th.getMessage());
                return i;
            }
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
        return i;
    }

    public static void enableComponents(Context context) {
        Slog.i(TAG, "enableComponents()");
        int activeUserId = getActiveUserId();
        enableComponent(context, "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.receiver.SystemIntentReceiver", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.selfupdate.SelfupdateReceiver", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.events.KGEventService", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.alarm.AlarmService", activeUserId);
        enableComponent(context, "com.samsung.android.kgclient.provider.KGProvider", activeUserId);
    }

    public static void enableComponent(Context context, String str, int i) {
        try {
            ComponentName componentName = new ComponentName(KnoxCustomManagerService.KG_PKG_NAME, str);
            if (MaintenanceModeManager.isInMaintenanceMode()) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager == null) {
                    Slog.w(TAG, "IPackageManager is null");
                    return;
                } else {
                    packageManager.setComponentEnabledSetting(componentName, 1, 1, i, (String) null);
                    return;
                }
            }
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e) {
            Slog.e(TAG, "enableComponent Exception. " + e);
        }
    }

    public static IntegritySeResult checkKGClientIntegrityAndEnableComponent(Context context, int i) {
        IntegritySeResult checkKGClientIntegrity = checkKGClientIntegrity(context, i);
        if (checkKGClientIntegrity.isOk) {
            return checkKGClientIntegrity;
        }
        enableComponents(context);
        return checkKGClientIntegrity(context, i);
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

    public static boolean checkTaIntegrity(int i) {
        try {
            return (Utils.isOtpBitFusedWithActive() && -1006 == i) ? false : true;
        } catch (Exception e) {
            Slog.e(TAG, "checkTaIntegrity Exception. " + e);
            return false;
        }
    }

    public static boolean checkAPSerialIntegrity(int i) {
        return !Utils.isSingleOtpBitFusedWithActive() || 4 == i || isAPSerialValid();
    }

    public static boolean isAPSerialValid() {
        String stringResult = KnoxGuardSeService.getStringResult(KnoxGuardNative.getTAInfo(4));
        boolean z = !TextUtils.isEmpty(stringResult) && stringResult.length() == 32 && Pattern.matches("[a-fA-F0-9]{32}", stringResult);
        Slog.d(TAG, "isAPSerialValid - " + z);
        return z;
    }

    public static boolean checkSignatures(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 64);
            String str = TAG;
            Slog.d(str, "pkgInfo : " + packageInfo.toString());
            if (packageManager.checkSignatures("android", KnoxCustomManagerService.KG_PKG_NAME) != 0) {
                Slog.i(str, "KG Client signature doesn't match with platform.");
                return false;
            }
            Slog.i(str, "KG Client signature match with platform.");
            return true;
        } catch (Exception e) {
            Slog.e(TAG, "checkKGClientIntegrity Exception. " + e);
            return false;
        }
    }

    public static boolean isSystemApp(Context context) {
        try {
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "isSystemApp NameNotFoundException : " + e);
        }
        return (context.getPackageManager().getApplicationInfo(KnoxCustomManagerService.KG_PKG_NAME, 0).flags & 1) != 0;
    }

    public static boolean isEnabled(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(KnoxCustomManagerService.KG_PKG_NAME, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "isEnabled NameNotFoundException : " + e);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f A[Catch: Exception -> 0x0055, TRY_LEAVE, TryCatch #2 {Exception -> 0x0055, blocks: (B:13:0x0049, B:15:0x004f), top: B:12:0x0049 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int testSystemUiCorrupted(Context context) {
        int i;
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (packageManager == null) {
            Slog.w(TAG, "PackageManager is null");
            return 0;
        }
        try {
            i = 1;
            if (!packageManager.getApplicationInfo("com.android.systemui", 0L, 0).enabled) {
                try {
                    packageManager.setApplicationEnabledSetting("com.android.systemui", 1, 0, 0, (String) null);
                } catch (Exception e) {
                    e = e;
                    Slog.e(TAG, "testSystemUiCorrupted : Exception in checking enabled value: " + e.getMessage());
                    if (packageManager.getApplicationHiddenSettingAsUser("com.android.systemui", 0)) {
                    }
                    Slog.w(TAG, "testSystemUiCorrupted: " + i);
                    return i;
                }
            } else {
                i = 0;
            }
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
        try {
            if (packageManager.getApplicationHiddenSettingAsUser("com.android.systemui", 0)) {
                i |= 2;
                packageManager.setApplicationHiddenSettingAsUser("com.android.systemui", false, 0);
            }
        } catch (Exception e3) {
            Slog.e(TAG, "testSystemUiCorrupted : Exception in checking hidden value: " + e3.getMessage());
        }
        Slog.w(TAG, "testSystemUiCorrupted: " + i);
        return i;
    }

    public static boolean isValidVersion(Context context) {
        return 300000000 <= getClientVersionCode(context, 300000000L);
    }

    public static long getClientVersionCode(Context context, long j) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            Slog.w(TAG, "PackageManager is null");
            return j;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 0);
            if (packageInfo != null) {
                return packageInfo.getLongVersionCode();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "Client Notfound : " + e);
        }
        return j;
    }

    public static void disableClient(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            Slog.w(TAG, "PackageManager is null");
            return;
        }
        try {
            Slog.i(TAG, "disable kgclient");
            packageManager.setApplicationEnabledSetting(KnoxCustomManagerService.KG_PKG_NAME, 2, 0);
        } catch (Exception e) {
            Slog.e(TAG, "disable exception: " + e.getMessage());
        }
    }

    public static boolean checkComponents(Context context, IntegritySeResult integritySeResult) {
        PackageInfo packageInfo;
        try {
            if (MaintenanceModeManager.isInMaintenanceMode()) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager == null) {
                    Slog.w(TAG, "IPackageManager is null");
                    return false;
                }
                packageInfo = packageManager.getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 14L, getActiveUserId());
            } else {
                PackageManager packageManager2 = context.getPackageManager();
                if (packageManager2 == null) {
                    Slog.w(TAG, "PackageManager is null");
                    return false;
                }
                packageInfo = packageManager2.getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 14);
            }
            integritySeResult.enabledAdminReceiver = hasEnabledComponent(packageInfo.receivers, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver");
            integritySeResult.enabledSystemIntentReceiver = hasEnabledComponent(packageInfo.receivers, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.receiver.SystemIntentReceiver");
            integritySeResult.enabledSelfUpdateReceiver = hasEnabledComponent(packageInfo.receivers, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.selfupdate.SelfupdateReceiver");
            integritySeResult.enabledKgEventService = hasEnabledComponent(packageInfo.services, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.events.KGEventService");
            integritySeResult.enabledAlarmService = hasEnabledComponent(packageInfo.services, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.alarm.AlarmService");
            integritySeResult.enabledKGProvider = hasEnabledComponent(packageInfo.providers, KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.provider.KGProvider");
        } catch (PackageManager.NameNotFoundException | RemoteException e) {
            Slog.e(TAG, "checkComponents error: " + e.getMessage());
        }
        return integritySeResult.enabledAdminReceiver && integritySeResult.enabledSystemIntentReceiver && integritySeResult.enabledSelfUpdateReceiver && integritySeResult.enabledKgEventService && integritySeResult.enabledAlarmService && integritySeResult.enabledKGProvider;
    }

    public static boolean hasEnabledComponent(ComponentInfo[] componentInfoArr, String str, String str2) {
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

    public static int toErrorCode(IntegritySeResult integritySeResult) {
        if (integritySeResult == null) {
            return 0;
        }
        return (integritySeResult.validSignature ? 0 : 2) | 4097 | (integritySeResult.enabled ? 0 : 4) | (integritySeResult.validVersion ? 0 : 8) | (integritySeResult.enabledAdminReceiver ? 0 : 64) | (integritySeResult.enabledSystemIntentReceiver ? 0 : 128) | (integritySeResult.enabledSelfUpdateReceiver ? 0 : 256) | (integritySeResult.enabledKgEventService ? 0 : 512) | (integritySeResult.enabledAlarmService ? 0 : 1024) | (integritySeResult.enabledKGProvider ? 0 : IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
    }

    public static void setInitialState(Context context, int i, AppOpsManager.OnOpChangedInternalListener onOpChangedInternalListener) {
        String str = TAG;
        Slog.i(str, "setInitialState, state : " + i);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Slog.e(str, "DPM is not available");
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getApplicationInfo(KnoxCustomManagerService.KG_PKG_NAME, 0);
            if (!checkSignatures(context)) {
                Slog.e(str, "KGClient is malicious, it will be locked");
                return;
            }
            String stringSystemProperty = Utils.getStringSystemProperty("ro.boot.kg.bit", "FF");
            boolean equals = "01".equals(stringSystemProperty);
            boolean z = "1".equals(stringSystemProperty) || "11".equals(stringSystemProperty);
            if (equals || (z && 4 != i)) {
                try {
                    int callingUserId = UserHandle.getCallingUserId();
                    IPackageManager packageManager2 = AppGlobals.getPackageManager();
                    if (packageManager2.getApplicationHiddenSettingAsUser(KnoxCustomManagerService.KG_PKG_NAME, callingUserId)) {
                        packageManager2.setApplicationHiddenSettingAsUser(KnoxCustomManagerService.KG_PKG_NAME, false, callingUserId);
                    }
                } catch (RemoteException e) {
                    Slog.e(TAG, "RemoteException " + e.getMessage());
                }
                ComponentName componentName = new ComponentName(KnoxCustomManagerService.KG_PKG_NAME, "com.samsung.android.kgclient.agent.KGDeviceAdminReceiver");
                try {
                    if (2 == i || 3 == i) {
                        Slog.d(TAG, "setInitialState call edm admin api for adding edm services!!!");
                        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
                        if (enterpriseDeviceManager != null) {
                            enterpriseDeviceManager.setActiveAdmin(componentName, false);
                        }
                    } else {
                        devicePolicyManager.setActiveAdmin(componentName, false);
                    }
                } catch (Exception e2) {
                    Slog.e(TAG, "com.samsung.android.kgclientsetActiveAdmin" + e2);
                }
                try {
                    AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
                    appOpsManager.startWatchingMode(-1, KnoxCustomManagerService.KG_PKG_NAME, (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
                    PackageInfo packageInfo = packageManager.getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 0);
                    Iterator it = Constants.PROTECTED_APP_OPS_LIST.iterator();
                    while (it.hasNext()) {
                        enableAppOpIfNotAllowed(packageInfo, appOpsManager, ((Integer) it.next()).intValue());
                    }
                } catch (Throwable th) {
                    Slog.e(TAG, "Error - appOps : " + th.getMessage());
                }
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Slog.e(TAG, "Client Notfound : " + e3);
        }
    }

    public static void enableAppOpIfNotAllowed(PackageInfo packageInfo, AppOpsManager appOpsManager, int i) {
        if (appOpsManager.checkOpNoThrow(i, packageInfo.applicationInfo.uid, KnoxCustomManagerService.KG_PKG_NAME) != 0) {
            Slog.i(TAG, "allow " + i);
            appOpsManager.setMode(i, packageInfo.applicationInfo.uid, KnoxCustomManagerService.KG_PKG_NAME, 0);
        }
    }
}
