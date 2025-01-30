package com.samsung.android.knoxguard.service;

import android.app.admin.DevicePolicyManager;
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
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.IKnoxGuardManager;
import com.samsung.android.knoxguard.service.receiver.SystemReceiver;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.IntegrityUtil;
import com.samsung.android.knoxguard.service.utils.Utils;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.IKnoxGuardVaultListener;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class KnoxGuardService extends IKnoxGuardManager.Stub {
    public Context mContext;
    public static final String TAG = "KG." + KnoxGuardService.class.getSimpleName();
    public static String mPreFix = "knox.guard";
    public static List mActionList = null;
    public static SystemReceiver mSystemReceiver = null;
    public static SystemReceiver mServiceSystemReceiver = null;
    public static String mSettedInterface = null;
    public static KnoxGuardVaultManager mKGVM = null;
    public INetworkManagementService mNetworkManagementService = null;
    public ConnectivityManager mConnectivityManagerService = null;
    public IKnoxGuardVaultListener mUnlockCompletedListener = new IKnoxGuardVaultListener() { // from class: com.samsung.android.knoxguard.service.KnoxGuardService.1
        public void onUnlockedByPasscode() {
            Slog.d(KnoxGuardService.TAG, "onUnlockedByPasscode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            IntentRelayManager.sendRequestedIntent(KnoxGuardService.this.mContext, "com.samsung.kgclient.android.intent.action.MANUAL_UNLOCK", null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    };

    public String generateHotpDHRequest() {
        return null;
    }

    public String getClientData() {
        return null;
    }

    public String getHotpChallenge() {
        return null;
    }

    public String getKGID() {
        return null;
    }

    public String getKGPolicy() {
        return null;
    }

    public Bundle getKGServiceInfo() {
        return null;
    }

    public int getKGServiceVersion() {
        return 170000001;
    }

    public String getLockAction() {
        return null;
    }

    public String getNonce(String str, String str2) {
        return null;
    }

    public int getTAError() {
        return -1;
    }

    public String getTAInfo(int i) {
        return null;
    }

    public int getTAState() {
        return -1;
    }

    public int getTAStateSetError(boolean z) {
        return -1;
    }

    public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) {
        return -1;
    }

    public int provisionCert(String str, String str2, String str3, String str4) {
        return -1;
    }

    public int resetRPMB() {
        return -1;
    }

    public int resetRPMB2(String str) {
        return -1;
    }

    public int setCheckingState() {
        return -1;
    }

    public int setClientData(String str) {
        return -1;
    }

    public void setClientHealthOK() {
    }

    public int unlockScreen() {
        return -1;
    }

    public int verifyCompleteToken(String str) {
        return -1;
    }

    public int verifyHOTPDHChallenge(String str, String str2, String str3) {
        return -1;
    }

    public int verifyHOTPPin(String str) {
        return -1;
    }

    public int verifyHOTPsecret(String str) {
        return -1;
    }

    public String verifyKgRot() {
        return null;
    }

    public String verifyPolicy(String str, String str2) {
        return null;
    }

    public String verifyRegistrationInfo(String str, String str2) {
        return null;
    }

    public KnoxGuardService(Context context) {
        if (!Utils.isSupportKGOnSEC()) {
            throw new UnsupportedOperationException("KnoxGuard is unsupported");
        }
        this.mContext = context;
        try {
            setKGVM(new KnoxGuardVaultManager(this.mContext, this.mUnlockCompletedListener));
        } catch (NoClassDefFoundError e) {
            Slog.e(TAG, e.getMessage(), e);
        }
        String rlcState = Utils.getRlcState(this.mContext);
        registerReceiver(this.mContext);
        registerReceiver(this.mContext, rlcState);
        IntegrityUtil.setInitialState(this.mContext, rlcState);
    }

    public final void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        SystemReceiver systemReceiver = new SystemReceiver();
        if (Utils.isChinaDevice()) {
            intentFilter.addAction("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE");
            intentFilter.addAction("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
            context.registerReceiver(systemReceiver, intentFilter);
        } else {
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart(KnoxCustomManagerService.KG_PKG_NAME, 0);
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            context.registerReceiver(systemReceiver, intentFilter);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter2.setPriority(100000001);
        context.registerReceiver(systemReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addDataScheme("package");
        intentFilter3.addDataSchemeSpecificPart("com.android.systemui", 0);
        intentFilter3.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter3.setPriority(100000001);
        context.registerReceiver(systemReceiver, intentFilter3);
    }

    public final void registerReceiver(Context context, String str) {
        if (str == null || !"Locked".equals(str) || Utils.isChinaDevice()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        mServiceSystemReceiver = new SystemReceiver();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.setPriority(100000001);
        context.registerReceiver(mServiceSystemReceiver, intentFilter);
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service;
        if (this.mNetworkManagementService == null && (service = ServiceManager.getService("network_management")) != null) {
            this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkManagementService;
    }

    public final ConnectivityManager getConnectivityManagerService() {
        if (ServiceManager.getService("connectivity") != null && this.mConnectivityManagerService == null) {
            this.mConnectivityManagerService = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        return this.mConnectivityManagerService;
    }

    public void callKGsv() {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call Knox Guard sv");
    }

    public void registerIntent(String str, List list) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        unRegisterIntent();
        setSystemReceiver(new SystemReceiver());
        IntentFilter intentFilter = new IntentFilter();
        setPreFix(str);
        setActionList(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        this.mContext.registerReceiver(mSystemReceiver, intentFilter);
        Slog.i(TAG, "KG registerIntent");
    }

    public void unRegisterIntent() {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        unRegisterIntent(Utils.getRlcState(this.mContext));
        setActionList(null);
        SystemReceiver systemReceiver = mSystemReceiver;
        if (systemReceiver != null) {
            try {
                this.mContext.unregisterReceiver(systemReceiver);
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
        Slog.i(TAG, "KG unRegisterIntent");
    }

    public final void unRegisterIntent(String str) {
        SystemReceiver systemReceiver;
        if ((str == null || !"Locked".equals(str)) && (systemReceiver = mServiceSystemReceiver) != null) {
            try {
                this.mContext.unregisterReceiver(systemReceiver);
                mServiceSystemReceiver = null;
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }

    public boolean setAdminRemovable(boolean z) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setAdminRemovable");
        return false;
    }

    public boolean allowFirmwareRecovery(boolean z) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowFirmwareRecovery");
        return false;
    }

    public boolean allowOTAUpgrade(boolean z) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowOTAUpgrade");
        return false;
    }

    public boolean allowSafeMode(boolean z) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call allowSafeMode");
        return false;
    }

    public boolean addPackagesToForceStopBlockList(List list) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call addPackagesToForceStopBlockList");
        return false;
    }

    public boolean addPackagesToClearCacheBlockList(List list) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call addPackagesToClearCacheBlockList");
        return false;
    }

    public boolean setApplicationUninstallationDisabled(String str) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setApplicationUninstallationDisabled");
        return false;
    }

    public void setAirplaneMode(boolean z) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setAirplaneMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((ConnectivityManager) this.mContext.getSystemService("connectivity")).setAirplaneMode(z);
            } catch (Exception e) {
                Slog.w(TAG, "Exception : " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setActiveAdmin(ComponentName componentName) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setActiveAdmin");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null || devicePolicyManager.isAdminActive(componentName)) {
            return;
        }
        devicePolicyManager.setActiveAdmin(componentName, false);
    }

    public void removeActiveAdmin(ComponentName componentName) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call removeActiveAdmin");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null || !devicePolicyManager.isAdminActive(componentName)) {
            return;
        }
        devicePolicyManager.removeActiveAdmin(componentName);
    }

    public void setRuntimePermission(String str, String str2, UserHandle userHandle) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRuntimePermission");
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager != null) {
            packageManager.grantRuntimePermission(str, str2, userHandle);
        }
    }

    public void revokeRuntimePermission(String str, String str2, UserHandle userHandle) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call revokeRuntimePermission");
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager != null) {
            packageManager.revokeRuntimePermission(str, str2, userHandle);
        }
    }

    public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRemoteLockToLockscreen");
        Utils.setRemoteLockToLockscreen(this.mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle);
    }

    public void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "call setRemoteLockToLockscreen with skipSupportContainer");
        Utils.setRemoteLockToLockscreen(this.mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, z4);
    }

    public boolean isSkipSupportContainerSupported() {
        Slog.i(TAG, "call isSkipSupportContainerSupported");
        return Utils.isSkipSupportContainerSupported();
    }

    public String getPBAUniqueNumber() {
        String str;
        String str2;
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
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
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String rlcState = Utils.getRlcState(this.mContext);
            if (!"".equalsIgnoreCase(rlcState) && !"Checking".equalsIgnoreCase(rlcState)) {
                if (!"Completed".equalsIgnoreCase(rlcState)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean showInstallmentStatus() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String rlcState = Utils.getRlcState(this.mContext);
            if (rlcState != null && !"".equalsIgnoreCase(rlcState) && !"Prenormal".equalsIgnoreCase(rlcState) && !"Checking".equalsIgnoreCase(rlcState)) {
                if (!"Completed".equalsIgnoreCase(rlcState)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean shouldBlockCustomRom() {
        String rlcState = Utils.getRlcState(this.mContext);
        return (rlcState == null || "".equalsIgnoreCase(rlcState) || "Checking".equalsIgnoreCase(rlcState) || "Completed".equalsIgnoreCase(rlcState)) ? false : true;
    }

    public void setKnoxGuardExemptRule(boolean z) {
        LinkProperties linkProperties;
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
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
                Slog.d(TAG, "setKnoxGuardExemptRule - There is no active network");
                return;
            }
            Slog.i(TAG, "setKnoxGuardExemptRule - " + z + ", " + linkProperties.getInterfaceName() + ", " + callingUid);
            if (z) {
                getNetworkManagementService().setKnoxGuardExemptRule(true, linkProperties.getInterfaceName(), callingUid);
                setSettedInterface(linkProperties.getInterfaceName());
            }
        } finally {
            Binder.restoreCallingIdentity(-1L);
        }
    }

    public final void resetKnoxGuardExemptRule(int i) {
        try {
            if (mSettedInterface != null) {
                Slog.i(TAG, "clearKnoxGuardExemptRule - " + mSettedInterface);
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
        if (DumpUtils.checkDumpPermission(this.mContext, str, printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            printWriter.println(str + " state: ");
            try {
                Cursor query = this.mContext.getContentResolver().query(Constants.KG_LOG_URI, null, null, null, null);
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
        try {
            if (mKGVM == null) {
                Slog.d(TAG, "mKGVM is null");
                setKGVM(new KnoxGuardVaultManager(this.mContext, this.mUnlockCompletedListener));
            }
            mKGVM.bindToLockScreen();
        } catch (NoClassDefFoundError e) {
            Slog.e(TAG, e.getMessage(), e);
        } catch (Throwable th) {
            Slog.e(TAG, th.getMessage(), th);
        }
    }

    public static void setKGVM(KnoxGuardVaultManager knoxGuardVaultManager) {
        mKGVM = knoxGuardVaultManager;
    }

    public static KnoxGuardVaultManager getKGVM() {
        return mKGVM;
    }

    public static void setPreFix(String str) {
        mPreFix = str;
    }

    public static void setActionList(List list) {
        mActionList = list;
    }

    public static List getActionList() {
        return mActionList;
    }

    public static void setSystemReceiver(SystemReceiver systemReceiver) {
        mSystemReceiver = systemReceiver;
    }

    public static void setSettedInterface(String str) {
        mSettedInterface = str;
    }

    public String getStringSystemProperty(String str, String str2) {
        Utils.checkPermission(this.mContext, "com.samsung.android.knoxguard.STATUS");
        return Utils.getStringSystemProperty(str, str2);
    }
}
