package com.samsung.android.knoxguard.service;

import android.R;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.RemoteLockInfo;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.IKnoxGuardManager;
import com.samsung.android.knoxguard.service.receiver.AlarmReceiver;
import com.samsung.android.knoxguard.service.receiver.IntentRelayReceiver;
import com.samsung.android.knoxguard.service.receiver.SystemSeReceiver;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;
import com.samsung.android.knoxguard.service.utils.Utils;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class KnoxGuardSeService extends IKnoxGuardManager.Stub {
    public static Context mContext;
    public static ILockSettings mLockSettingsService;
    public static boolean mSkipPin;
    public static boolean mSkipSupport;
    public static final String TAG = "KG." + KnoxGuardSeService.class.getSimpleName();
    public static String mPreFix = "knox.guard";
    public static List mActionList = null;
    public static IntentRelayReceiver intentRelayReceiver = null;
    public static SystemSeReceiver userPresentReceiver = null;
    public static String mSettedInterface = null;
    public static int mFailureCount = -1;
    public static KgErrWrapper mTAError = null;
    public static String mClientName = null;
    public static String mPhoneNumber = null;
    public static String mEmailAddress = null;
    public static String mMessage = null;
    public static int mTAVersion = -1;
    public static Bundle mBundle = null;
    public static int mClientHealth = 1;
    public static int mLockResult = 0;
    public static RemoteLockInfo mRetryRemoteLockInfo = null;
    public static AppOpsManager.OnOpChangedInternalListener opListener = new AppOpsManager.OnOpChangedInternalListener() { // from class: com.samsung.android.knoxguard.service.KnoxGuardSeService.1
        public void onOpChanged(int i, String str) {
            if (KnoxCustomManagerService.KG_PKG_NAME.equals(str) && Constants.PROTECTED_APP_OPS_LIST.contains(Integer.valueOf(i))) {
                try {
                    IntegritySeUtil.enableAppOpIfNotAllowed(KnoxGuardSeService.mContext.getPackageManager().getPackageInfo(KnoxCustomManagerService.KG_PKG_NAME, 0), (AppOpsManager) KnoxGuardSeService.mContext.getSystemService("appops"), i);
                } catch (Throwable th) {
                    Slog.e(KnoxGuardSeService.TAG, "Error - appOps : " + th.getMessage());
                }
            }
        }
    };
    public static IRemoteLockMonitorCallback mRemoteLockMonitorCallback = new IRemoteLockMonitorCallback.Stub() { // from class: com.samsung.android.knoxguard.service.KnoxGuardSeService.2
        public void changeRemoteLockState(RemoteLockInfo remoteLockInfo) {
            Slog.d(KnoxGuardSeService.TAG, "changeRemoteLockState data = " + remoteLockInfo.lockType);
        }

        public int checkRemoteLockPassword(byte[] bArr) {
            Slog.i(KnoxGuardSeService.TAG, "checkRemoteLockPassword");
            try {
                KnoxGuardSeService.setFailureCount(KnoxGuardSeService.getIntResult(KnoxGuardNative.verifyHOTPPinRefactor(new String(bArr, StandardCharsets.UTF_8))));
                if (KnoxGuardSeService.mFailureCount == 0) {
                    Slog.i(KnoxGuardSeService.TAG, "[HOTP] pin is correct!");
                    Utils.setKGSystemProperty();
                    KnoxGuardSeService.unregisterUserPresentReceiver();
                    KnoxGuardSeService.unlockCompleted();
                } else {
                    Slog.e(KnoxGuardSeService.TAG, "[HOTP] pin is wrong!!! current failure count (" + KnoxGuardSeService.mFailureCount + ")");
                    KnoxGuardSeService.setRemoteLockToLockscreen(true);
                }
            } catch (Throwable th) {
                th.printStackTrace();
                Slog.e(KnoxGuardSeService.TAG, "[HOTP] verify pin error");
            }
            return KnoxGuardSeService.mFailureCount;
        }
    };
    public INetworkManagementService mNetworkManagementService = null;
    public ConnectivityManager mConnectivityManagerService = null;

    public static long getLockoutDelayTime(int i) {
        if (i == 6) {
            return 60000L;
        }
        if (i == 7) {
            return BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        }
        if (i == 8) {
            return 900000L;
        }
        if (i == 9) {
            return ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        }
        if (i >= 10) {
            return BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        }
        return 0L;
    }

    public int getKGServiceVersion() {
        return 170000001;
    }

    public KnoxGuardSeService(Context context) {
        if (!Utils.isSupportKGOnSEC()) {
            throw new UnsupportedOperationException("KnoxGuard is unsupported");
        }
        setContext(context);
        int stateAndSetToKGSystemProperty = Utils.getStateAndSetToKGSystemProperty();
        registerReceiver(mContext);
        registerAlarmReceiver(mContext);
        registerUserPresentReceiverIfLocked(stateAndSetToKGSystemProperty);
        IntegritySeUtil.setInitialState(mContext, stateAndSetToKGSystemProperty, opListener);
    }

    public final void registerReceiver(Context context) {
        Slog.i(TAG, "call registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        SystemSeReceiver systemSeReceiver = new SystemSeReceiver();
        if (Utils.isChinaDevice()) {
            intentFilter.addAction("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE");
            intentFilter.addAction("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
            registerReceiver(context, systemSeReceiver, intentFilter);
        } else {
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart(KnoxCustomManagerService.KG_PKG_NAME, 0);
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            registerReceiver(context, systemSeReceiver, intentFilter);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter2.setPriority(100000001);
        registerReceiver(context, systemSeReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addDataScheme("package");
        intentFilter3.addDataSchemeSpecificPart("com.android.systemui", 0);
        intentFilter3.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter3.setPriority(100000001);
        registerReceiver(context, systemSeReceiver, intentFilter3);
    }

    public static void registerUserPresentReceiverIfLocked(int i) {
        if (i == 3) {
            registerUserPresentReceiver();
        }
    }

    public static void registerUserPresentReceiver() {
        Slog.i(TAG, "call registerUserPresentReceiver");
        unregisterUserPresentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        userPresentReceiver = new SystemSeReceiver();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.setPriority(100000001);
        mContext.registerReceiverForAllUsers(userPresentReceiver, intentFilter, null, null);
    }

    public final void registerAlarmReceiver(Context context) {
        Slog.i(TAG, "call registerAlarmReceiver");
        if (Utils.needClientHealthCheck()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.knoxguard.CLIENT_HEALTH_CHECK");
            intentFilter.addAction("com.samsung.android.knoxguard.RETRY_LOCK");
            registerReceiver(context, new AlarmReceiver(), intentFilter, 4);
        }
    }

    public static void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i) {
        if (MaintenanceModeManager.isInMaintenanceMode()) {
            context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null, i);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter, i);
        }
    }

    public static void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (MaintenanceModeManager.isInMaintenanceMode()) {
            context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service;
        Slog.i(TAG, "call getNetworkManagementService");
        if (this.mNetworkManagementService == null && (service = ServiceManager.getService("network_management")) != null) {
            this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkManagementService;
    }

    public final ConnectivityManager getConnectivityManagerService() {
        Slog.i(TAG, "call getConnectivityManagerService");
        if (ServiceManager.getService("connectivity") != null && this.mConnectivityManagerService == null) {
            this.mConnectivityManagerService = (ConnectivityManager) mContext.getSystemService("connectivity");
        }
        return this.mConnectivityManagerService;
    }

    public void callKGsv() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call Knox Guard sv");
    }

    public void registerIntent(String str, List list) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        unRegisterIntent();
        setIntentRelayReceiver(new IntentRelayReceiver());
        IntentFilter intentFilter = new IntentFilter();
        setPreFix(str);
        setActionList(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        registerReceiver(mContext, intentRelayReceiver, intentFilter);
        Slog.i(TAG, "KG registerIntent");
    }

    public void unRegisterIntent() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        setActionList(null);
        IntentRelayReceiver intentRelayReceiver2 = intentRelayReceiver;
        if (intentRelayReceiver2 != null) {
            try {
                mContext.unregisterReceiver(intentRelayReceiver2);
                setIntentRelayReceiver(null);
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
        Slog.i(TAG, "KG unRegisterIntent");
    }

    public static void unregisterUserPresentReceiver() {
        Slog.i(TAG, "call unregisterUserPresentReceiver");
        SystemSeReceiver systemSeReceiver = userPresentReceiver;
        if (systemSeReceiver != null) {
            try {
                mContext.unregisterReceiver(systemSeReceiver);
                userPresentReceiver = null;
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }

    public boolean setAdminRemovable(boolean z) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setAdminRemovable");
        return false;
    }

    public boolean allowFirmwareRecovery(boolean z) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowFirmwareRecovery");
        return false;
    }

    public boolean allowOTAUpgrade(boolean z) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowOTAUpgrade");
        return false;
    }

    public boolean allowSafeMode(boolean z) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowSafeMode");
        return false;
    }

    public boolean addPackagesToForceStopBlockList(List list) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call addPackagesToForceStopBlockList");
        return false;
    }

    public boolean addPackagesToClearCacheBlockList(List list) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call addPackagesToClearCacheBlockList");
        return false;
    }

    public boolean setApplicationUninstallationDisabled(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setApplicationUninstallationDisabled");
        return false;
    }

    public void setAirplaneMode(boolean z) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setAirplaneMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((ConnectivityManager) mContext.getSystemService("connectivity")).setAirplaneMode(z);
            } catch (Exception e) {
                Slog.w(TAG, "Exception : " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setActiveAdmin(ComponentName componentName) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setActiveAdmin");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) mContext.getSystemService("device_policy");
        if (devicePolicyManager == null || devicePolicyManager.isAdminActive(componentName)) {
            return;
        }
        devicePolicyManager.setActiveAdmin(componentName, false);
    }

    public void removeActiveAdmin(ComponentName componentName) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call removeActiveAdmin");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) mContext.getSystemService("device_policy");
        if (devicePolicyManager == null || !devicePolicyManager.isAdminActive(componentName)) {
            return;
        }
        devicePolicyManager.removeActiveAdmin(componentName);
    }

    public void setRuntimePermission(String str, String str2, UserHandle userHandle) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRuntimePermission");
        PackageManager packageManager = mContext.getPackageManager();
        if (packageManager != null) {
            packageManager.grantRuntimePermission(str, str2, userHandle);
        }
    }

    public void revokeRuntimePermission(String str, String str2, UserHandle userHandle) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call revokeRuntimePermission");
        PackageManager packageManager = mContext.getPackageManager();
        if (packageManager != null) {
            packageManager.revokeRuntimePermission(str, str2, userHandle);
        }
    }

    public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRemoteLockToLockscreen");
        Utils.setRemoteLockToLockscreen(mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle);
    }

    public void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRemoteLockToLockscreen with skipSupportContainer");
        Utils.setRemoteLockToLockscreen(mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, z4);
    }

    public boolean isSkipSupportContainerSupported() {
        Slog.i(TAG, "call isSkipSupportContainerSupported");
        return Utils.isSkipSupportContainerSupported();
    }

    public String getPBAUniqueNumber() {
        String str;
        String str2;
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call getPBAUniqueNumber");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Utils.isExistFile("/sys/class/sec/ufs/un")) {
                str = Utils.getTextFromFile("/sys/class/sec/ufs/un");
            } else if (Utils.isExistFile("/sys/class/scsi_host/host0/unique_number")) {
                str = Utils.getTextFromFile("/sys/class/scsi_host/host0/unique_number");
            } else if (Utils.isExistFile("/sys/class/sec/mmc/un")) {
                str = Utils.getTextFromFile("/sys/class/sec/mmc/un");
            } else if (Utils.isExistFile("/sys/block/mmcblk0/device/unique_number")) {
                str = Utils.getTextFromFile("/sys/block/mmcblk0/device/unique_number");
            } else {
                if (!Utils.isExistFile("/sys/block/mmcblk0/device/cid")) {
                    return "";
                }
                String textFromFile = Utils.getTextFromFile("/sys/block/mmcblk0/device/cid");
                String textFromFile2 = Utils.getTextFromFile("/sys/block/mmcblk0/device/name");
                String str3 = "c";
                if (textFromFile != null) {
                    String substring = textFromFile.substring(0, 2);
                    if (textFromFile2 != null) {
                        if (substring.equalsIgnoreCase("15")) {
                            str2 = textFromFile2.substring(0, 2);
                        } else {
                            if (!substring.equalsIgnoreCase("02") && !substring.equalsIgnoreCase("45")) {
                                if (!substring.equalsIgnoreCase("11") && !substring.equalsIgnoreCase("90")) {
                                    if (substring.equalsIgnoreCase("FE")) {
                                        str2 = textFromFile2.substring(4, 6);
                                    }
                                }
                                str2 = textFromFile2.substring(1, 3);
                            }
                            str2 = textFromFile2.substring(3, 5);
                        }
                        str = (((str3 + str2) + textFromFile.substring(18, 20)) + textFromFile.substring(20, 28)) + textFromFile.substring(28, 30);
                    }
                    str2 = "";
                    str = (((str3 + str2) + textFromFile.substring(18, 20)) + textFromFile.substring(20, 28)) + textFromFile.substring(28, 30);
                } else {
                    str = str3;
                }
            }
            return str != null ? str.toUpperCase() : "";
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isVpnExceptionRequired() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = Constants.strState;
            String stringSystemProperty = Utils.getStringSystemProperty("knox.kg.state", strArr[5]);
            if (getTAVersion() >= 3) {
                z = Utils.isOtpBitFusedWithActiveAndStateIsNotCompleted(stringSystemProperty);
            } else {
                if (!stringSystemProperty.equals(strArr[2]) && !stringSystemProperty.equals(strArr[3]) && !stringSystemProperty.equals(strArr[5]) && !Utils.isOtpBitFusedWithActive()) {
                    z = false;
                }
                z = true;
            }
            Slog.i(TAG, "call isVpnExceptionRequired, state : " + stringSystemProperty + " , result : " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean showInstallmentStatus() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = Constants.strState;
            String stringSystemProperty = Utils.getStringSystemProperty("knox.kg.state", strArr[5]);
            if (getTAVersion() >= 3) {
                z = Utils.isOtpBitFusedWithActiveAndStateIsNotCompleted(stringSystemProperty);
            } else {
                if (!stringSystemProperty.equals(strArr[2]) && !stringSystemProperty.equals(strArr[3]) && !Utils.isOtpBitFusedWithActive()) {
                    z = false;
                }
                z = true;
            }
            Slog.i(TAG, "call showInstallmentStatus, state : " + stringSystemProperty + " , result : " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldBlockCustomRom() {
        boolean z;
        String[] strArr = Constants.strState;
        String stringSystemProperty = Utils.getStringSystemProperty("knox.kg.state", strArr[5]);
        if (getTAVersion() >= 3) {
            z = Utils.isOtpBitFusedWithActiveAndStateIsNotCompleted(stringSystemProperty);
        } else {
            z = stringSystemProperty.equals(strArr[0]) || stringSystemProperty.equals(strArr[2]) || stringSystemProperty.equals(strArr[3]) || Utils.isOtpBitFusedWithActive();
        }
        Slog.i(TAG, "call shouldBlockCustomRom, state : " + stringSystemProperty + " , result : " + z);
        return z;
    }

    public void setKnoxGuardExemptRule(boolean z) {
        LinkProperties linkProperties;
        String str = TAG;
        Slog.i(str, "call setKnoxGuardExemptRule");
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        int callingUid = Binder.getCallingUid();
        long j = -1;
        try {
            try {
                j = Binder.clearCallingIdentity();
                resetKnoxGuardExemptRule(callingUid);
                linkProperties = getConnectivityManagerService().getLinkProperties(getConnectivityManagerService().getActiveNetwork());
            } catch (Exception e) {
                Slog.w(TAG, "Exception : " + e.getMessage());
            }
            if (linkProperties == null) {
                Slog.d(str, "setKnoxGuardExemptRule - There is no active network");
                return;
            }
            Slog.i(str, "setKnoxGuardExemptRule - " + z + ", " + linkProperties.getInterfaceName() + ", " + callingUid);
            if (z) {
                getNetworkManagementService().setKnoxGuardExemptRule(true, linkProperties.getInterfaceName(), callingUid);
                setSettedInterface(linkProperties.getInterfaceName());
            }
        } finally {
            Binder.restoreCallingIdentity(-1L);
        }
    }

    public final void resetKnoxGuardExemptRule(int i) {
        String str = TAG;
        Slog.i(str, "call resetKnoxGuardExemptRule");
        try {
            if (mSettedInterface != null) {
                Slog.i(str, "clearKnoxGuardExemptRule - " + mSettedInterface);
                getNetworkManagementService().setKnoxGuardExemptRule(false, mSettedInterface, i);
                setSettedInterface(null);
            }
        } catch (Exception e) {
            Slog.w(TAG, "Exception : " + e.getMessage());
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str = TAG;
        Slog.i(str, "call dump");
        if (DumpUtils.checkDumpPermission(mContext, str, printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            printWriter.println(str + " state: ");
            try {
                Cursor query = mContext.getContentResolver().query(Constants.KG_LOG_URI, null, null, null, null);
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            printWriter.println(query.getString(query.getColumnIndex("data")));
                        } finally {
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void bindToLockScreen() {
        bindAndSetToLockScreen();
    }

    public static void bindAndSetToLockScreen() {
        Slog.i(TAG, "bindAndSetToLockScreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            mLockSettingsService.registerRemoteLockCallback(3, mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void unbindFromLockScreen() {
        Slog.i(TAG, "unbindFromLockScreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            mLockSettingsService.unregisterRemoteLockCallback(3, mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen(false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void initializeFailureCount() {
        try {
            mFailureCount = Integer.parseInt(getStringResult(KnoxGuardNative.getTAInfo(3)));
        } catch (Throwable th) {
            Slog.e(TAG, "initializeFailureCount error : " + th.getMessage());
        }
        Slog.d(TAG, "mFailureCount : " + mFailureCount);
    }

    public static void setUserPresentReceiverEnabled(boolean z) {
        if (z) {
            registerUserPresentReceiver();
        } else {
            unregisterUserPresentReceiver();
        }
    }

    public static void setRemoteLockToLockscreen(boolean z) {
        String string;
        String format;
        String str = TAG;
        Slog.i(str, "setRemoteLockToLockscreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            if (mFailureCount < 0) {
                initializeFailureCount();
            }
            long lockoutDelayTime = getLockoutDelayTime(mFailureCount);
            Slog.i(str, "kgvDelayTime " + lockoutDelayTime);
            KGLockscreenInfo kGLockObject = getKGLockObject();
            if (kGLockObject != null) {
                setClientName(kGLockObject.getClientName());
                setPhoneNumber(kGLockObject.getPhoneNumber());
                setEmailAddress(kGLockObject.getEmailAddress());
                setMessage(kGLockObject.getMessage());
                setSkipPin(kGLockObject.getSkipPin());
                setSkipSupport(kGLockObject.getSkipSupport());
                setBundle(kGLockObject.getBundle());
            }
            if (Utils.isTabletDevice()) {
                string = mContext.getString(R.string.notification_title_abusive_bg_apps);
                format = String.format(mContext.getString(R.string.notification_reply_button_accessibility), "Device Services");
            } else {
                string = mContext.getString(R.string.notification_title);
                format = String.format(mContext.getString(R.string.notification_phishing_alert_content_description), "Device Services");
            }
            RemoteLockInfo.Builder builder = new RemoteLockInfo.Builder(3, z);
            String str2 = mClientName;
            if (str2 != null && !str2.equalsIgnoreCase("")) {
                string = mClientName;
            }
            RemoteLockInfo.Builder clientName = builder.setClientName(string);
            String str3 = mPhoneNumber;
            if (str3 == null) {
                str3 = "";
            }
            RemoteLockInfo.Builder phoneNumber = clientName.setPhoneNumber(str3);
            String str4 = mEmailAddress;
            if (str4 == null) {
                str4 = "";
            }
            RemoteLockInfo.Builder emailAddress = phoneNumber.setEmailAddress(str4);
            String str5 = mMessage;
            if (str5 != null && !str5.equalsIgnoreCase("")) {
                format = mMessage;
            }
            RemoteLockInfo build = emailAddress.setMessage(format).setAllowFailCount(1).setLockTimeOut(lockoutDelayTime).setBlockCount(0).setSkipPinContainer(mSkipPin).setSkipSupportContainer(mSkipSupport).setBundle(mBundle).build();
            boolean performLockscreen = Utils.performLockscreen(mLockSettingsService, build, z);
            if (!z || performLockscreen) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Utils.startRetryLockAlarm(mContext, build);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static KGLockscreenInfo getKGLockObject() {
        String str = TAG;
        Slog.i(str, "getKGVaultData");
        try {
            byte[] byteArrayResult = getByteArrayResult(KnoxGuardNative.getLockObjectRefactor());
            if (byteArrayResult == null) {
                return null;
            }
            if (byteArrayResult.length == 0) {
                Slog.w(str, "No data");
                return null;
            }
            return (KGLockscreenInfo) deserialize(byteArrayResult, KGLockscreenInfo.class);
        } catch (Throwable th) {
            Slog.e(TAG, "getKGVaultData error: " + th);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0052, code lost:
    
        if (r5 == 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0066, code lost:
    
        if (r5 == 0) goto L46;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r5v16, types: [java.io.ObjectInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object deserialize(byte[] bArr, Class cls) {
        Object obj = null;
        if (bArr != 0) {
            ByteArrayInputStream length = bArr.length;
            try {
                if (length != 0) {
                    try {
                        length = new ByteArrayInputStream(bArr);
                        try {
                            bArr = new ObjectInputStream(length);
                            try {
                                obj = bArr.readObject();
                                try {
                                    length.close();
                                } catch (IOException unused) {
                                    length = TAG;
                                    Slog.e((String) length, "Deserialize failed IO exception");
                                }
                            } catch (IOException e) {
                                e = e;
                                e.printStackTrace();
                                length = length;
                                if (length != 0) {
                                    try {
                                        length.close();
                                        length = length;
                                    } catch (IOException unused2) {
                                        String str = TAG;
                                        Slog.e(str, "Deserialize failed IO exception");
                                        length = str;
                                    }
                                }
                            } catch (ClassNotFoundException e2) {
                                e = e2;
                                e.printStackTrace();
                                length = length;
                                if (length != 0) {
                                    try {
                                        length.close();
                                        length = length;
                                    } catch (IOException unused3) {
                                        String str2 = TAG;
                                        Slog.e(str2, "Deserialize failed IO exception");
                                        length = str2;
                                    }
                                }
                            }
                        } catch (IOException e3) {
                            e = e3;
                            bArr = 0;
                        } catch (ClassNotFoundException e4) {
                            e = e4;
                            bArr = 0;
                        } catch (Throwable th) {
                            th = th;
                            bArr = 0;
                            if (length != 0) {
                                try {
                                    length.close();
                                } catch (IOException unused4) {
                                    Slog.e(TAG, "Deserialize failed IO exception");
                                }
                            }
                            if (bArr == 0) {
                                throw th;
                            }
                            try {
                                bArr.close();
                                throw th;
                            } catch (IOException unused5) {
                                Slog.e(TAG, "Deserialize inputstream failed IO exception");
                                throw th;
                            }
                        }
                    } catch (IOException e5) {
                        e = e5;
                        bArr = 0;
                        length = 0;
                    } catch (ClassNotFoundException e6) {
                        e = e6;
                        bArr = 0;
                        length = 0;
                    } catch (Throwable th2) {
                        length = 0;
                        th = th2;
                        bArr = 0;
                    }
                    try {
                        bArr.close();
                    } catch (IOException unused6) {
                        Slog.e(TAG, "Deserialize inputstream failed IO exception");
                    }
                    return obj;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return null;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void setPreFix(String str) {
        mPreFix = str;
    }

    public static void setActionList(List list) {
        mActionList = list;
    }

    public static void setIntentRelayReceiver(IntentRelayReceiver intentRelayReceiver2) {
        intentRelayReceiver = intentRelayReceiver2;
    }

    public static void setSettedInterface(String str) {
        mSettedInterface = str;
    }

    public static void setMessage(String str) {
        mMessage = str;
    }

    public static void setSkipPin(boolean z) {
        mSkipPin = z;
    }

    public static void setClientName(String str) {
        mClientName = str;
    }

    public static void setPhoneNumber(String str) {
        mPhoneNumber = str;
    }

    public static void setSkipSupport(boolean z) {
        mSkipSupport = z;
    }

    public static void setEmailAddress(String str) {
        mEmailAddress = str;
    }

    public static void setTaError(KgErrWrapper kgErrWrapper) {
        mTAError = kgErrWrapper;
    }

    public static void setFailureCount(int i) {
        mFailureCount = i;
    }

    class KGLockscreenInfo implements Serializable {
        private String mClientName;
        private String mCustomAppName;
        private String mCustomAppPackageName;
        private String mEmailAddress;
        private String mMessage;
        private String mPhoneNumber;
        private boolean mSkipPin;
        private boolean mSkipSupport;

        public KGLockscreenInfo(String str, String str2, String str3, String str4, boolean z, boolean z2, Bundle bundle) {
            setClientName(str);
            setMessage(str4);
            setPhoneNumber(str2);
            setEmailAddress(str3);
            setSkipPin(z);
            setSkipSupport(z2);
            setBundle(bundle);
        }

        public String getClientName() {
            return this.mClientName;
        }

        public void setClientName(String str) {
            this.mClientName = str;
        }

        public String getMessage() {
            return this.mMessage;
        }

        public void setMessage(String str) {
            this.mMessage = str;
        }

        public String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public void setPhoneNumber(String str) {
            this.mPhoneNumber = str;
        }

        public String getEmailAddress() {
            return this.mEmailAddress;
        }

        public void setEmailAddress(String str) {
            this.mEmailAddress = str;
        }

        public boolean getSkipSupport() {
            return this.mSkipSupport;
        }

        public void setSkipSupport(boolean z) {
            this.mSkipSupport = z;
        }

        public boolean getSkipPin() {
            return this.mSkipPin;
        }

        public void setSkipPin(boolean z) {
            this.mSkipPin = z;
        }

        public Bundle getBundle() {
            Bundle bundle = new Bundle();
            bundle.putCharSequence("customer_package_name", this.mCustomAppPackageName);
            bundle.putCharSequence("customer_app_name", this.mCustomAppName);
            return bundle;
        }

        public void setBundle(Bundle bundle) {
            if (bundle == null) {
                this.mCustomAppPackageName = null;
                this.mCustomAppName = null;
                return;
            }
            CharSequence charSequence = bundle.getCharSequence("customer_package_name");
            if (charSequence != null) {
                this.mCustomAppPackageName = charSequence.toString();
            } else {
                this.mCustomAppPackageName = null;
            }
            CharSequence charSequence2 = bundle.getCharSequence("customer_app_name");
            if (charSequence2 != null) {
                this.mCustomAppName = charSequence2.toString();
            } else {
                this.mCustomAppName = null;
            }
        }
    }

    public static void unlockCompleted() {
        Slog.d(TAG, "onUnlockedByPasscode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        IntentRelayManager.sendRequestedIntent(mContext, "com.samsung.kgclient.android.intent.action.MANUAL_UNLOCK", null);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public int getTAState() {
        return getTAStateSetError(false);
    }

    public int getTAStateSetError(boolean z) {
        int intResult = getIntResult(KnoxGuardNative.getTAStateRefactor(), z);
        Slog.d(TAG, "getTAState : " + intResult);
        if (intResult < 0 || intResult > 5) {
            return 5;
        }
        return intResult;
    }

    public int verifyHOTPPin(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "verifyHOTPPin");
        setFailureCount(getIntResult(KnoxGuardNative.verifyHOTPPinRefactor(str)));
        return mFailureCount;
    }

    public int verifyHOTPsecret(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "verifyHOTPsecret");
        return getIntResult(KnoxGuardNative.verifyHOTPsecretRefactor(str));
    }

    public String getKGPolicy() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getKGPolicy");
        return getStringResult(KnoxGuardNative.getKGPolicyRefactor());
    }

    public int verifyHOTPDHChallenge(String str, String str2, String str3) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        int intResult = getIntResult(KnoxGuardNative.verifyHotpDHChallengeRefactor(str, str2, str3));
        Slog.d(TAG, "verifyHOTPDHChallenge result : " + intResult);
        Utils.setKGSystemProperty();
        return intResult;
    }

    public int verifyCompleteToken(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        int intResult = getIntResult(KnoxGuardNative.verifyCompleteTokenRefactor(str));
        Slog.d(TAG, "verifyCompleteToken result : " + intResult);
        Utils.setKGSystemProperty();
        if (intResult == 0) {
            unbindFromLockScreen();
        }
        return intResult;
    }

    public String generateHotpDHRequest() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "generateHotpDHRequest");
        return getStringResult(KnoxGuardNative.generateHotpDHRequestRefactor());
    }

    public String getHotpChallenge() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getHotpChallenge");
        return getStringResult(KnoxGuardNative.getHotpChallengeRefactor());
    }

    public String verifyRegistrationInfo(String str, String str2) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "verifyRegistrationInfo");
        String stringResult = getStringResult(KnoxGuardNative.verifyRegistrationInfoRefactor(str, str2));
        Utils.setKGSystemProperty();
        return stringResult;
    }

    public String verifyPolicy(String str, String str2) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "verifyPolicy");
        return getStringResult(KnoxGuardNative.verifyPolicyRefactor(str, str2));
    }

    public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "lockScreen");
        setClientName(str2);
        setPhoneNumber(str3);
        setEmailAddress(str4);
        setMessage(str5);
        setSkipPin(z);
        setSkipSupport(z2);
        setBundle(bundle);
        int intResult = getIntResult(KnoxGuardNative.lockScreenRefactor(str, str2, str3, str4, str5, z, z2, bundle));
        Utils.setKGSystemProperty();
        bindAndSetToLockScreen();
        return intResult;
    }

    public int unlockScreen() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "unlockScreen");
        unbindFromLockScreen();
        int intResult = getIntResult(KnoxGuardNative.unlockScreenRefactor());
        setFailureCount(0);
        Utils.setKGSystemProperty();
        return intResult;
    }

    public String getLockAction() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getLockAction");
        return getStringResult(KnoxGuardNative.getLockActionRefactor());
    }

    public String getClientData() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getClientData");
        return getStringResult(KnoxGuardNative.getClientDataRefactor());
    }

    public int setClientData(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "setClientData");
        return getIntResult(KnoxGuardNative.setClientDataRefactor(str));
    }

    public String getKGID() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getKGID");
        return getStringResult(KnoxGuardNative.getKGIDRefactor());
    }

    public int resetRPMB() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "resetRPMB");
        int intResult = getIntResult(KnoxGuardNative.resetRPMBRefactor(null));
        Utils.setKGSystemProperty();
        return intResult;
    }

    public int resetRPMB2(String str) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "resetRPMB2");
        return getIntResult(KnoxGuardNative.resetRPMBRefactor(str));
    }

    public int setCheckingState() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "setCheckingState");
        int intResult = getIntResult(KnoxGuardNative.userCheckingRefactor());
        Utils.setKGSystemProperty();
        return intResult;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String verifyKgRot() {
        boolean z;
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        String str = TAG;
        Slog.d(str, "verifyKgRot");
        int callingUid = Binder.getCallingUid();
        String nameForUid = mContext.getPackageManager().getNameForUid(callingUid);
        Slog.d(str, "caller: " + nameForUid + " (" + callingUid + ")");
        String makeRotReturn = makeRotReturn(0, 0);
        if (TextUtils.isEmpty(nameForUid) || !KnoxCustomManagerService.KG_PKG_NAME.equals(nameForUid)) {
            makeRotReturn = makeRotReturn(6000, 0);
        } else if (!IntegritySeUtil.checkSignatures(mContext)) {
            makeRotReturn = makeRotReturn(6002, 0);
        } else if (!IntegritySeUtil.isEnabled(mContext)) {
            makeRotReturn = makeRotReturn(6001, 0);
        } else {
            z = true;
            if (z) {
                try {
                    makeRotReturn = getStringResult(KnoxGuardNative.verifyKgRotRefactor());
                } catch (Exception e) {
                    String makeRotReturn2 = makeRotReturn(6003, 0);
                    Slog.e(TAG, "Error verifyKgRot - ", e);
                    makeRotReturn = makeRotReturn2;
                }
            }
            Slog.d(TAG, "RoT: " + makeRotReturn);
            return makeRotReturn;
        }
        z = false;
        if (z) {
        }
        Slog.d(TAG, "RoT: " + makeRotReturn);
        return makeRotReturn;
    }

    public String getStringSystemProperty(String str, String str2) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        return Utils.getStringSystemProperty(str, str2);
    }

    public final String makeRotReturn(int i, int i2) {
        return String.format("<%d>:<%d>", Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public int getTAError() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        return getTaErrorCode();
    }

    public static int getTaErrorCode() {
        KgErrWrapper kgErrWrapper = mTAError;
        if (kgErrWrapper != null) {
            return kgErrWrapper.err;
        }
        return 0;
    }

    public static int getIntResult(KgErrWrapper kgErrWrapper) {
        return getIntResult(kgErrWrapper, true);
    }

    public static int getIntResult(KgErrWrapper kgErrWrapper, boolean z) {
        if (z) {
            setTaError(kgErrWrapper);
        }
        if (kgErrWrapper == null) {
            return -1;
        }
        int i = kgErrWrapper.err;
        if (i == 0 || i == 771) {
            return kgErrWrapper.result;
        }
        return -1;
    }

    public static String getStringResult(KgErrWrapper kgErrWrapper) {
        setTaError(kgErrWrapper);
        KgErrWrapper kgErrWrapper2 = mTAError;
        if (kgErrWrapper2 == null || kgErrWrapper2.err != 0) {
            return null;
        }
        return kgErrWrapper2.getStr();
    }

    public static byte[] getByteArrayResult(KgErrWrapper kgErrWrapper) {
        return getByteArrayResult(kgErrWrapper, false);
    }

    public static byte[] getByteArrayResult(KgErrWrapper kgErrWrapper, boolean z) {
        if (z) {
            setTaError(kgErrWrapper);
        }
        if (kgErrWrapper == null || kgErrWrapper.err != 0) {
            return null;
        }
        return kgErrWrapper.data;
    }

    public String getNonce(String str, String str2) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        String str3 = TAG;
        Slog.d(str3, "getNonce");
        if (str == null || str2 == null) {
            Slog.i(str3, "getNonce null parameter!");
            return null;
        }
        return getStringResult(KnoxGuardNative.getNonceRefactor(str, str2));
    }

    public String getTAInfo(int i) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getTAInfo: infoFlag : " + i);
        return getStringResult(KnoxGuardNative.getTAInfo(i));
    }

    public static int getTAVersion() {
        int i = mTAVersion;
        if (-1 != i) {
            return i;
        }
        try {
            String stringResult = getStringResult(KnoxGuardNative.getTAInfo(1));
            setTAVersion(Integer.valueOf(stringResult).intValue());
            Slog.d(TAG, "getTAVersion : " + stringResult);
        } catch (Throwable th) {
            Slog.e(TAG, "TA version not converted to int: " + th.getMessage());
        }
        return mTAVersion;
    }

    public static void setTAVersion(int i) {
        mTAVersion = i;
    }

    public int provisionCert(String str, String str2, String str3, String str4) {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        String str5 = TAG;
        Slog.d(str5, "provisionCert");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            Slog.e(str5, "provisionCert failed: input null");
            return -1;
        }
        return getIntResult(KnoxGuardNative.provisionCert(str, str2, str3, str4));
    }

    public static int getClientHealth() {
        Slog.d(TAG, "getClientHealth : " + mClientHealth);
        return mClientHealth;
    }

    public static void setClientHealth(int i) {
        Slog.d(TAG, "setClientHealth : " + mClientHealth + " -> " + i);
        mClientHealth = i;
    }

    public void setClientHealthOK() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "setClientHealthOK");
        int tAState = KnoxGuardNative.getTAState();
        if (mClientHealth == 2 && tAState != 3 && IntegritySeUtil.checkKGClientIntegrity(mContext, tAState).isOk) {
            setRemoteLockToLockscreen(false);
        }
        setClientHealth(1);
    }

    public static void setRetryRemoteLockInfo(RemoteLockInfo remoteLockInfo) {
        Slog.d(TAG, "setRetryRemoteLockInfo : " + remoteLockInfo);
        mRetryRemoteLockInfo = remoteLockInfo;
    }

    public static RemoteLockInfo getRemoteLockInfoForRetry() {
        Slog.d(TAG, "getRemoteLockInfoForRetry : " + mRetryRemoteLockInfo);
        return mRetryRemoteLockInfo;
    }

    public static void setLockResult(boolean z) {
        mLockResult = z ? 1 : 2;
    }

    public Bundle getKGServiceInfo() {
        Utils.checkPermission(mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.d(TAG, "getKGServiceInfo");
        IntegritySeUtil.IntegritySeResult checkKGClientIntegrity = IntegritySeUtil.checkKGClientIntegrity(mContext, KnoxGuardNative.getTAState());
        Bundle bundle = new Bundle();
        bundle.putInt("client_health", mClientHealth);
        bundle.putInt("lock_result", mLockResult);
        bundle.putString("integrity_result", IntegritySeUtil.getFailedIntegrityResult(checkKGClientIntegrity));
        bundle.putBoolean("is_maintenance_mode_supported", true);
        return bundle;
    }
}
