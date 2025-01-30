package com.android.server.enterprise.restriction;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyCache;
import android.app.admin.IDevicePolicyManager;
import android.app.backup.IBackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncAdapterType;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.INetworkPolicyManager;
import android.net.NetworkPolicy;
import android.net.TetheringManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.KeyCodeMediator;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.VpnInfoPolicy;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.gnss.hal.GnssNative;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import com.samsung.android.knox.localservice.RestrictionPolicyInternal;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.security.mdf.MdfService.MdfPolicy;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class RestrictionPolicy extends IRestrictionPolicy.Stub implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static final String[] excludedAdminList = {"com.sec.enterprise.knox.cloudmdm.smdms", "com.sec.sprextension.phoneinfo", "com.samsung.klmsagent", "com.samsung.android.knox.containercore", KnoxCustomManagerService.KG_PKG_NAME};
    public boolean isLockScreenShortcutsAllowedCache;
    public boolean isLockScreenWidgetsAllowedCache;
    public boolean isSafeModeAllowedCache;
    public ApplicationPolicy mAppPolicy;
    public final AudioManager mAudioManager;
    public BroadcastReceiver mBootReceiver;
    public ConstrainedModeInternal mConstrainedState;
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public IEnterpriseDeviceManager mEdmService;
    public EdmStorageProvider mEdmStorageProvider;
    public BroadcastReceiver mFotaReceiver;
    public Handler mHandler;
    public final Injector mInjector;
    public boolean mIsUsbMassStorageAvailable;
    public int mKcUid;
    public KeyCodeMediator mKeyCodeMediator;
    public RestrictionPolicyCache mRestrictionCache;
    public IStatusBarService mStatusBarService;
    public StorageEventListener mStorageListener;
    public StorageManager mStorageManager;
    public IBinder mToken;
    public boolean mUsbSyncFlag;
    public UserManager mUserManager;
    public Set mUserRestrictionEnforcedByKC;
    public VpnInfoPolicy mVpnPolicy;
    public IWindowManager mWindowManager;
    public boolean preAdminRemoval_SDCardWrite;

    private native byte[] readParamData();

    private static native boolean writeParamData(byte[] bArr);

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public String getServiceName() {
        return "RestrictionPolicy";
    }

    public boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public final boolean isValidDevice(int i) {
        return i == 1;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    static {
        try {
            System.loadLibrary("android_servers");
        } catch (UnsatisfiedLinkError unused) {
            Log.d("RestrictionPolicy", "Unable to Load Library android_servers");
        }
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getEDMStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }
    }

    public RestrictionPolicy(Context context) {
        this(new Injector(context));
    }

    public RestrictionPolicy(Injector injector) {
        this.mAppPolicy = null;
        this.mVpnPolicy = null;
        this.mStorageManager = null;
        this.preAdminRemoval_SDCardWrite = false;
        this.mIsUsbMassStorageAvailable = false;
        this.isLockScreenWidgetsAllowedCache = true;
        this.isLockScreenShortcutsAllowedCache = true;
        this.isSafeModeAllowedCache = true;
        this.mUsbSyncFlag = false;
        this.mStatusBarService = null;
        this.mToken = new Binder();
        this.mUserRestrictionEnforcedByKC = new ArraySet();
        this.mKcUid = -1;
        this.mFotaReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Slog.d("RestrictionPolicy", "FOTAReceiver: onReceive");
                String action = intent.getAction();
                int i = 0;
                if (action.equals("sec.fota.intent.MDM_REGISTER_RESULT")) {
                    int intExtra = intent.getIntExtra(KnoxCustomManagerService.SPCM_KEY_RESULT, 0);
                    if (intExtra != 0 && intExtra != 1) {
                        Slog.d("RestrictionPolicy", "FOTAReceiver: action:sec.fota.intent.MDM_REGISTER_RESULT failure(" + intExtra + ")");
                        RestrictionPolicy.this.clearSelectiveFota();
                        i = 1;
                    } else {
                        Slog.d("RestrictionPolicy", "action:sec.fota.intent.MDM_REGISTER_RESULT success");
                    }
                    RestrictionPolicy.this.sendSeletiveFotaResult(i);
                    return;
                }
                if (action.equals("com.xdm.intent.UPDATE_RESULT")) {
                    if (RestrictionPolicy.this.getAllowedFOTAInfo(null) == null) {
                        Slog.d("RestrictionPolicy", "action:com.xdm.intent.UPDATE_RESULT ignore");
                        return;
                    }
                    int intExtra2 = intent.getIntExtra(KnoxCustomManagerService.SPCM_KEY_RESULT, 0) + 5;
                    Slog.d("RestrictionPolicy", "action:com.xdm.intent.UPDATE_RESULT = " + intExtra2);
                    if (intExtra2 > 8 || intExtra2 < 5) {
                        return;
                    }
                    RestrictionPolicy.this.sendSeletiveFotaResult(intExtra2);
                    return;
                }
                if (action.equals("sec.fota.intent.MDM_UNREGISTER")) {
                    Slog.d("RestrictionPolicy", "FOTAReceiver: action:sec.fota.intent.MDM_UNREGISTER");
                    RestrictionPolicy.this.clearSelectiveFota();
                    String[] strArr = {"com.wssyncmldm", "com.samsung.utagent"};
                    while (i < 2) {
                        Intent intent2 = new Intent("sec.fota.intent.MDM_REGISTER");
                        intent2.setPackage(strArr[i]);
                        RestrictionPolicy.this.mContext.sendBroadcast(intent2);
                        i++;
                    }
                }
            }
        };
        this.mBootReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("RestrictionPolicy", action);
                if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                    try {
                        RestrictionPolicy.this.updateUSBMode();
                    } catch (Exception unused) {
                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                    }
                    RestrictionPolicy restrictionPolicy = RestrictionPolicy.this;
                    restrictionPolicy.setStatusBarExpansionSystemUI(0, restrictionPolicy.isStatusBarExpansionAllowedAsUser(false, 0));
                    RestrictionPolicy restrictionPolicy2 = RestrictionPolicy.this;
                    restrictionPolicy2.updateUsbStorageStatebyIntent(restrictionPolicy2.isUsbHostStorageAllowed(null, false));
                    if (RestrictionPolicy.this.isFirmwareChanged()) {
                        RestrictionPolicy.this.migrateDisallowNonMarketAppOnUserRestrictions();
                    }
                    RestrictionPolicy.this.notifyChanges(-1);
                    if (RestrictionPolicy.this.isAirplaneModeAllowed(false)) {
                        return;
                    }
                    RestrictionPolicy.this.turnOffAirPlaneMode();
                    return;
                }
                if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    try {
                        RestrictionPolicy.this.updateUSBMode();
                        return;
                    } catch (Exception unused2) {
                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                        return;
                    }
                }
                if (action.equals("edm.intent.action.internal.sec.DEFAULT_NETWORK_POLICY_APPLIED") && (!RestrictionPolicy.this.isBackgroundDataEnabled(null) || !RestrictionPolicy.this.isDataSavingAllowed())) {
                    try {
                        RestrictionPolicy.this.applyBackgroundDataRestriction();
                        return;
                    } catch (Exception unused3) {
                        Slog.w("RestrictionPolicy", "Network Policy update failed");
                        return;
                    }
                }
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    Slog.d("RestrictionPolicy", "Intent.ACTION_USER_SWITCHED occurred!! action:" + action + " userId=" + intExtra);
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(RestrictionPolicy.this.isScreenCaptureEnabled(intExtra, false) ? 1 : 0);
                    Utils.writePropertyValue("screenCaptureEnabled", sb.toString(), "/data/system/enterprise.conf");
                    return;
                }
                if ("android.intent.action.USER_ADDED".equals(action)) {
                    RestrictionPolicy.this.onUserAdded(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    RestrictionPolicy.this.onUserRemoved(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(action)) {
                    RestrictionPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                }
            }
        };
        this.mEDM = null;
        this.mHandler = new Handler() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                RestrictionPolicy.this.mContext.startService(new Intent("com.android.voicerecorder.HIDENOTIFICATION").setComponent(new ComponentName("com.sec.android.app.voicerecorder.service", "com.sec.android.app.voicerecorder.service.VoiceRecorderService")));
            }
        };
        this.mStorageListener = new StorageEventListener() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy.4
            public void onStorageStateChanged(String str, String str2, String str3) {
                Log.d("RestrictionPolicy", "path = " + str + ", oldState = " + str2 + ", newState = " + str3);
                if (str2.equals("ejecting") && str3.equals("unmounted")) {
                    RestrictionPolicy.this.mountSdCard();
                }
                if (str2.equals("checking") && str3.equals("mounted")) {
                    RestrictionPolicy.this.getStorageManager().unregisterListener(RestrictionPolicy.this.mStorageListener);
                    if (!RestrictionPolicy.this.isSDCardWriteAllowed(null)) {
                        Log.d("RestrictionPolicy", "SDCard Remounted with Readonly permission");
                    } else {
                        Log.d("RestrictionPolicy", "SDCard Remounted with ReadWrite permission");
                    }
                }
                Log.d("RestrictionPolicy", "--onStorageStateChanged");
            }
        };
        this.mInjector = injector;
        Context context = injector.mContext;
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mEdmStorageProvider = injector.getEDMStorageProvider();
        this.mRestrictionCache = new RestrictionPolicyCache(this.mContext, this.mEdmStorageProvider);
        this.mAppPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        this.mVpnPolicy = (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("edm.intent.action.internal.sec.DEFAULT_NETWORK_POLICY_APPLIED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("sec.fota.intent.MDM_REGISTER_RESULT");
        intentFilter2.addAction("com.xdm.intent.UPDATE_RESULT");
        intentFilter2.addAction("sec.fota.intent.MDM_UNREGISTER");
        this.mContext.registerReceiver(this.mFotaReceiver, intentFilter2, "com.sec.android.fotaclient.permission.FOTA", null);
        this.mContext.registerReceiver(this.mBootReceiver, intentFilter);
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        if (isHomeKeyEnabledInDb()) {
            return;
        }
        setHomeKeyVisibilityOnNavi(false);
    }

    public final IEnterpriseDeviceManager getService() {
        if (this.mEdmService == null) {
            this.mEdmService = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }
        return this.mEdmService;
    }

    public final ApplicationPolicy getApplicationPolicy() {
        if (this.mAppPolicy == null) {
            this.mAppPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        return this.mAppPolicy;
    }

    public final VpnInfoPolicy getVpnInfoPolicy() {
        if (this.mVpnPolicy == null) {
            this.mVpnPolicy = (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
        }
        return this.mVpnPolicy;
    }

    public final boolean isFirmwareChanged() {
        String databaseUpgradeValue = this.mEdmStorageProvider.getDatabaseUpgradeValue("PlatformSoftwareVersion");
        String str = SystemProperties.get("ro.build.fingerprint", "unknown");
        if (str.equalsIgnoreCase("unknown")) {
            str = null;
        }
        if (databaseUpgradeValue != null && (str == null || str.equals(databaseUpgradeValue))) {
            return false;
        }
        Slog.d("RestrictionPolicy", "isFirmwareChanged : true");
        return true;
    }

    public final void migrateDisallowNonMarketAppOnUserRestrictions() {
        updateNonMarketAppOnUserManager();
    }

    public final void onUserAdded(int i) {
        loadRestrictionCacheAndNotifyChanges(i);
    }

    public final synchronized IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            this.mStatusBarService = asInterface;
            if (asInterface == null) {
                Log.d("RestrictionPolicy", "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mStatusBarService;
    }

    public final void onUserRemoved(int i) {
        this.mRestrictionCache.clearCache(i);
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceHwPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
    }

    public final ContextInfo enforceOwnerOnlyAndHwPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
    }

    public final ContextInfo enforceOwnerOnlyAndTetheringPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndLocationPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_LOCATION")));
    }

    public final ContextInfo enforceRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public final ContextInfo enforceAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public final ContextInfo enforceOldAdvancedRestrictionOrNewApplicationPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceCertificatePermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
    }

    public boolean setCamera(ContextInfo contextInfo, boolean z) {
        String str;
        boolean z2;
        String str2;
        boolean putBoolean;
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    try {
                        putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "cameraEnabled", z);
                        Slog.d("RestrictionPolicy", "setCamera : " + z);
                        this.mRestrictionCache.updateCameraDisabledAdmin("cameraEnabled", 68719476736L, true, callingOrCurrentUserId);
                        str2 = "RestrictionPolicy";
                        z2 = false;
                    } catch (RemoteException e) {
                        e = e;
                        str2 = "RestrictionPolicy";
                        z2 = false;
                    }
                } catch (Exception unused) {
                    str2 = "RestrictionPolicy";
                    z2 = false;
                }
            } catch (RemoteException e2) {
                e = e2;
                str = "RestrictionPolicy";
                z2 = false;
            }
            try {
                this.mRestrictionCache.update("cameraEnabled", 68719476736L, true, callingOrCurrentUserId, Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
                if (putBoolean && !z) {
                    ActivityManagerNative.getDefault().forceStopPackage("com.sec.android.app.camera", callingOrCurrentUserId);
                    List containers = KnoxContainerManager.getContainers(enforceHwPermission);
                    if (containers != null) {
                        Iterator it = containers.iterator();
                        while (it.hasNext()) {
                            ActivityManagerNative.getDefault().forceStopPackage("com.sec.android.app.camera", ((Integer) it.next()).intValue());
                        }
                    }
                }
                if (putBoolean) {
                    setCameraAllowedSystemUI(0, isCameraEnabled(enforceHwPermission, false));
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed camera." : "Admin %d has disallowed camera.", Integer.valueOf(enforceHwPermission.mCallerUid)), callingOrCurrentUserId);
                    SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isCameraEnabled"));
                    SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isCameraEnabled"));
                }
                return putBoolean;
            } catch (RemoteException e3) {
                e = e3;
                str = str2;
                Log.e(str, "Fail getting ActivityManager " + e.getMessage());
                return z2;
            } catch (Exception unused2) {
                Slog.w(str2, "is EDMStorageProvider running?");
                return z2;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCameraEnabled(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean isCameraEnabledEx = isCameraEnabledEx(callingOrCurrentUserId, contextInfo);
        if (z && !isCameraEnabledEx) {
            RestrictionToastManager.show(R.string.config_gnssLocationProviderPackageName);
        }
        Slog.d("RestrictionPolicy", "isCameraEnabled ret(" + isCameraEnabledEx + ") userId(" + callingOrCurrentUserId + ") cxtInfo.mCallerUid(" + contextInfo.mCallerUid + ") cxtInfo.mContainerId(" + contextInfo.mContainerId + ")");
        return isCameraEnabledEx;
    }

    public final boolean isCameraEnabledEx(int i, ContextInfo contextInfo) {
        Long isCameraDisabledByMultipleAdmin = isCameraDisabledByMultipleAdmin(contextInfo);
        if (isCameraDisabledByMultipleAdmin.longValue() == -1) {
            return false;
        }
        if (isCameraDisabledByMultipleAdmin.longValue() == 0) {
            if (!isCameraEnabledAsUser(0)) {
                if (contextInfo.mContainerId > 0 && this.mAppPolicy != null) {
                    try {
                        Long cameraDisabledAdmin = this.mRestrictionCache.getCameraDisabledAdmin(0);
                        if (cameraDisabledAdmin.longValue() > 0) {
                            int intValue = (contextInfo.mContainerId * 100000) + cameraDisabledAdmin.intValue();
                            Log.d("RestrictionPolicy", "checking for camera in ApplicationPolicy when camera disabled in user 0 ");
                            return this.mAppPolicy.isCameraAllowlistedApp(contextInfo.mCallerUid, intValue);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Slog.d("RestrictionPolicy", "isCameraEnabledEx isCameraEnabledEx with OWNER return false");
                return false;
            }
            if (getEDM().isRestrictedByConstrainedState(1)) {
                Slog.d("RestrictionPolicy", "isCameraEnabledEx isRestrictedByConstrainedState return true");
                return false;
            }
            return isCameraEnabledAsUser(i);
        }
        if (getEDM().isRestrictedByConstrainedState(1)) {
            Slog.d("RestrictionPolicy", "isCameraEnabledEx isRestrictedByConstrainedState return true");
            return false;
        }
        if (this.mAppPolicy != null) {
            try {
                Log.d("RestrictionPolicy", "checking for camera in ApplicationPolicy");
                return this.mAppPolicy.isCameraAllowlistedApp(contextInfo.mCallerUid, isCameraDisabledByMultipleAdmin.intValue());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    public final boolean isCameraEnabledAsUser(int i) {
        return this.mRestrictionCache.extract(68719476736L, true, i);
    }

    public boolean setIrisCameraState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        Slog.d("RestrictionPolicy", "setIrisCameraState : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "iriscameraEnabled", z);
        this.mRestrictionCache.update("iriscameraEnabled", 288230376151711744L, true, callingOrCurrentUserId, Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) {
        return isIrisCameraEnabledAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isIrisCameraEnabledAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(288230376151711744L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.mmiErrorNotSupported);
        }
        Slog.d("RestrictionPolicy", "isIrisCameraEnabledAsUser : " + extract);
        return extract;
    }

    public boolean setMicrophoneState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        if (isMicrophoneEnabled(enforceHwPermission, false) && !z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "wake_up_lock_screen", 0, callingOrCurrentUserId);
            Settings.System.getIntForUser(this.mContext.getContentResolver(), "voice_input_control", 0, callingOrCurrentUserId);
            try {
                if (getPersonaManagerAdapter().isValidKnoxId(callingOrCurrentUserId)) {
                    this.mContext.sendBroadcastAsUser(new Intent("com.samsung.media.save_fmrecording_only"), new UserHandle(callingOrCurrentUserId));
                    this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.app.voicerecorder.rec_save"), new UserHandle(callingOrCurrentUserId));
                    this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.app.voicenote.rec_save"), new UserHandle(callingOrCurrentUserId), "com.sec.android.app.voicenote.Controller");
                } else {
                    new ArrayList();
                    Iterator it = Utils.getAllUsers(this.mContext).iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        Slog.d("TAG", "IF = FALSE(isValidKnoxId) i:" + num + " enable:" + z);
                        Iterator it2 = it;
                        this.mContext.sendBroadcastAsUser(new Intent("com.samsung.media.save_fmrecording_only"), new UserHandle(num.intValue()));
                        this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.app.voicerecorder.rec_save"), new UserHandle(num.intValue()));
                        this.mContext.sendBroadcastAsUser(new Intent("com.sec.android.app.voicenote.rec_save"), new UserHandle(num.intValue()), "com.sec.android.app.voicenote.Controller");
                        it = it2;
                    }
                }
            } catch (Exception e) {
                Slog.e("RestrictionPolicy", "setMicrophoneState Broadcast error ");
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "microphoneEnabled", z);
        if (putBoolean) {
            Utils.writePropertyValue("microphoneEnabled", "" + (isMicrophoneEnabled(enforceHwPermission, false) ? 1 : 0), "/data/system/enterprise.conf");
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed microphone." : "Admin %d has disallowed microphone.", Integer.valueOf(enforceHwPermission.mCallerUid)), callingOrCurrentUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            }
        }
        this.mRestrictionCache.update("microphoneEnabled", 4L, true, callingOrCurrentUserId, Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            if (!isMicrophoneEnabled(enforceHwPermission, false)) {
                this.mAudioManager.setParameters("g_knox_microphone_allowed=false");
            } else {
                this.mAudioManager.setParameters("g_knox_microphone_allowed=true");
            }
        }
        Slog.d("RestrictionPolicy", "setMicrophoneState : " + z);
        return putBoolean;
    }

    public boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) {
        return isMicrophoneEnabledAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isMicrophoneEnabledAsUser(boolean z, int i) {
        boolean z2;
        if (i != 0) {
            z2 = this.mRestrictionCache.extract(4L, true, 0);
            Slog.d("RestrictionPolicy", "isMicrophoneEnabledAsUser (owner) : " + z2);
        } else {
            z2 = true;
        }
        if (z2) {
            z2 = this.mRestrictionCache.extract(4L, true, i);
            Slog.d("RestrictionPolicy", "userId: " + i + ", isMicrophoneEnabledAsUser : " + z2);
        }
        if (z && !z2) {
            RestrictionToastManager.show(R.string.permlab_invokeCarrierSetup);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(1), 5000L);
        }
        return z2;
    }

    public Long isCameraDisabledByMultipleAdmin(ContextInfo contextInfo) {
        return this.mRestrictionCache.getCameraDisabledAdmin(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setTethering(ContextInfo contextInfo, boolean z) {
        boolean usbTethering = setUsbTethering(contextInfo, z);
        boolean wifiTethering = setWifiTethering(contextInfo, z);
        boolean bluetoothTethering = setBluetoothTethering(contextInfo, z);
        Slog.d("RestrictionPolicy", "setTethering : " + z);
        return usbTethering && wifiTethering && bluetoothTethering;
    }

    public boolean isTetheringEnabled(ContextInfo contextInfo) {
        boolean isUsbTetheringEnabled = isUsbTetheringEnabled(null);
        boolean isWifiTetheringEnabled = isWifiTetheringEnabled(null);
        boolean isBluetoothTetheringEnabled = isBluetoothTetheringEnabled(null);
        Slog.d("RestrictionPolicy", "isTetheringEnabled : ret1 = " + isUsbTetheringEnabled + " ret2 = " + isWifiTetheringEnabled + " ret3 = " + isBluetoothTetheringEnabled);
        return isUsbTetheringEnabled || isWifiTetheringEnabled || isBluetoothTetheringEnabled;
    }

    public boolean setUsbTethering(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndTetheringPermission = enforceOwnerOnlyAndTetheringPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setUsbTethering due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z && (((UsbManager) this.mContext.getSystemService("usb")).getCurrentFunctions() & 32) != 0) {
            ((TetheringManager) this.mContext.getSystemService(TetheringManager.class)).stopTethering(1);
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndTetheringPermission.mCallerUid, "RESTRICTION", "usbTetheringEnabled", z);
        this.mRestrictionCache.update("usbTetheringEnabled", 137438953472L, true, 0, Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "setUsbTethering : " + z);
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled USB Tethering setting." : "Admin %d has disabled USB Tethering setting.", Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public boolean isUsbTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(137438953472L, true, 0);
        boolean z = (extract && getEDM().isRestrictedByConstrainedState(16)) ? false : extract;
        Slog.d("RestrictionPolicy", "isUsbTetheringEnabled : " + z);
        return z;
    }

    public boolean setWifiTethering(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndTetheringPermission = enforceOwnerOnlyAndTetheringPermission(contextInfo);
        Log.i("RestrictionPolicy", "setWifiTethering - caller uid: " + enforceOwnerOnlyAndTetheringPermission.mCallerUid + ", enable: " + z);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.e("RestrictionPolicy", "failed to setWifiTethering due to Ethernet only mode");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndTetheringPermission.mCallerUid, "RESTRICTION", "wifiTetheringEnabled", z);
        if (!putBoolean) {
            Log.e("RestrictionPolicy", "setWifiTethering - fail to store value to database");
            return putBoolean;
        }
        this.mRestrictionCache.update("wifiTetheringEnabled", 274877906944L, true, 0, Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid), Boolean.valueOf(z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled Wifi Tethering setting." : "Admin %d has disabled Wifi Tethering setting.", Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid));
            setWifiTetheringAllowedSystemUI(0, isWifiTetheringEnabled(null));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setWifiTetheringUserRestriction(!isWifiTetheringEnabled(null));
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setWifiTetheringUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManager.setUserRestriction("no_wifi_tethering", z);
            Log.i("RestrictionPolicy", "setWifiTetheringUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWifiTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(274877906944L, true, 0);
        boolean z = (extract && getEDM().isRestrictedByConstrainedState(16)) ? false : extract;
        Log.i("RestrictionPolicy", "isWifiTetheringEnabled: " + z);
        return z;
    }

    public boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndTetheringPermission = enforceOwnerOnlyAndTetheringPermission(contextInfo);
        boolean z3 = false;
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setBluetoothTethering due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndTetheringPermission.mCallerUid, "RESTRICTION", "bluetoothTetheringEnabled", z);
        } catch (Exception e) {
            e = e;
        }
        try {
            this.mRestrictionCache.update("bluetoothTetheringEnabled", 549755813888L, true, 0, Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid), Boolean.valueOf(z));
            Slog.w("RestrictionPolicy", "setBluetoothTethering : " + z);
            if (z2) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled bluetooth Tethering." : "Admin %d has disabled bluetooth Tethering.", Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid));
                if (!z) {
                    ((ConnectivityManager) this.mContext.getSystemService("connectivity")).stopTethering(2);
                }
            }
        } catch (Exception e2) {
            e = e2;
            z3 = z2;
            Slog.w("RestrictionPolicy", "setBluetoothTethering Ex:" + e.getMessage());
            z2 = z3;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.d("RestrictionPolicy", "setBluetoothTethering : " + z);
            return z2;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Slog.d("RestrictionPolicy", "setBluetoothTethering : " + z);
        return z2;
    }

    public boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(549755813888L, true, 0);
        boolean z = (extract && getEDM().isRestrictedByConstrainedState(16)) ? false : extract;
        Slog.d("RestrictionPolicy", "isBluetoothTetheringEnabled : " + z);
        return z;
    }

    public boolean setScreenCapture(ContextInfo contextInfo, boolean z) {
        boolean z2;
        boolean putBoolean;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        boolean z3 = true;
        if (!getPersonaManagerAdapter().isKnoxId(userId) || getPersonaManagerAdapter().isLegacyClId(userId)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "screenCaptureEnabled", z);
            } catch (Exception unused) {
            }
            try {
                Slog.d("RestrictionPolicy", "setScreenCapture : enable=" + z + " callingUid=" + enforceRestrictionPermission.mCallerUid);
                this.mRestrictionCache.update("screenCaptureEnabled", 1099511627776L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
                if (putBoolean && enforceRestrictionPermission.mContainerId == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(isScreenCaptureEnabled(enforceRestrictionPermission, false) ? 1 : 0);
                    Utils.writePropertyValue("screenCaptureEnabled", sb.toString(), "/data/system/enterprise.conf");
                }
                if (putBoolean) {
                    if (isScreenCaptureEnabled(userId, false)) {
                        z3 = false;
                    }
                    updateScreenCaptureDisabledInWindowManager(userId, z3);
                }
                z2 = putBoolean;
            } catch (Exception unused2) {
                r9 = putBoolean;
                Slog.e("RestrictionPolicy", "setScreenCapture error");
                z2 = r9;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z2;
        }
        try {
            ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(this.mEdmStorageProvider.getMUMContainerOwnerUid(userId));
            IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
            if (asInterface == null || componentNameForUid == null) {
                return false;
            }
            asInterface.setScreenCaptureDisabled(componentNameForUid, componentNameForUid.getPackageName(), !z, false);
            Slog.d("RestrictionPolicy", "setScreenCapture: enable=" + z + " callingUid=" + enforceRestrictionPermission.mCallerUid);
            try {
                updateScreenCaptureDisabledInWindowManager(userId, isScreenCaptureEnabled(userId, false) ? false : true);
                return true;
            } catch (Exception e) {
                e = e;
                Slog.e("RestrictionPolicy", "setScreenCapture failed : result " + e);
                return z3;
            }
        } catch (Exception e2) {
            e = e2;
            z3 = false;
        }
    }

    public final void updateScreenCaptureDisabledInWindowManager(int i, boolean z) {
        Log.i("RestrictionPolicy", "updateScreenCaptureDisabledInWindowManager() userId = " + i + ", disabled = " + z);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3/isScreenCaptureEnabled"));
        updateSetScreenCapture();
    }

    public final void updateSetScreenCapture() {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RestrictionPolicy.lambda$updateSetScreenCapture$0();
            }
        });
    }

    public static /* synthetic */ void lambda$updateSetScreenCapture$0() {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).refreshScreenCaptureDisabled();
        } catch (RemoteException e) {
            Log.e("RestrictionPolicy", "Unable to notify WindowManager.", e);
        }
    }

    public boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) {
        return isScreenCaptureEnabled(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public boolean isScreenCaptureEnabledInternal(boolean z) {
        return isScreenCaptureEnabled(getTopActivityUserId(), z);
    }

    public boolean isScreenCaptureEnabledEx(int i, boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (getPersonaManagerAdapter().isAppSeparationUserId(i)) {
                i = 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!getPersonaManagerAdapter().isKnoxId(i) || getPersonaManagerAdapter().isLegacyClId(i)) {
                boolean extract = this.mRestrictionCache.extract(1099511627776L, true, i);
                z2 = (extract && getEDM().isRestrictedByConstrainedState(64)) ? false : extract;
            } else {
                z2 = DevicePolicyCache.getInstance().isScreenCaptureAllowed(i);
            }
            if (z && !z2) {
                RestrictionToastManager.show(17042526);
            }
            if (!z2) {
                Log.i("RestrictionPolicy", "isScreenCaptureEnabledEx() : screencapture has disabled in user " + i);
            }
            Slog.d("RestrictionPolicy", "isScreenCaptureEnabled : ret=" + z2 + " userId=" + i);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isScreenCaptureEnabled(int i, boolean z) {
        return isScreenCaptureEnabledEx(i, z);
    }

    public boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                Settings.Global.putString(this.mContext.getContentResolver(), "adb_enabled", "0");
                Settings.Global.putString(this.mContext.getContentResolver(), "adb_wifi_enabled", "0");
            } catch (Exception unused) {
                z2 = false;
            }
        }
        z2 = true;
        if (z2) {
            z2 &= this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "usbDebuggingEnabled", z);
            this.mRestrictionCache.update("usbDebuggingEnabled", 2199023255552L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        }
        if (z2) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled USB debugging." : "Admin %d has disabled USB debugging.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Slog.w("RestrictionPolicy", "setUSBDebugging : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean isUsbDebuggingEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(2199023255552L, true, 0);
        boolean z = (extract && getEDM().isRestrictedByConstrainedState(32)) ? false : extract;
        Slog.d("RestrictionPolicy", "isUsbDebuggingEnabled : " + z);
        return z;
    }

    public boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) {
        enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        return false;
    }

    public boolean setMockLocation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndLocationPermission = enforceOwnerOnlyAndLocationPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceOwnerOnlyAndLocationPermission.mCallerUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = true;
        if (!z) {
            try {
                z2 = true & new DeveloperModeSettings(this.mContext).resetMockLocationApps();
                ApplicationRestrictionsManager applicationRestrictionsManager = ApplicationRestrictionsManager.getInstance(this.mContext);
                if (applicationRestrictionsManager != null && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                    try {
                        ActivityManagerNative.getDefault().forceStopPackage("com.android.settings", this.mContext.getUserId());
                    } catch (RemoteException e) {
                        Log.w("RestrictionPolicy", "killSettings: RemoteException ex -> " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                z2 = false;
            }
        }
        if (z2) {
            boolean putBoolean = z2 & this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndLocationPermission.mCallerUid, "RESTRICTION", "mockLocationEnabled", z);
            this.mRestrictionCache.update("mockLocationEnabled", 8796093022208L, true, userId, Integer.valueOf(enforceOwnerOnlyAndLocationPermission.mCallerUid), Boolean.valueOf(z));
            z2 = putBoolean;
        }
        Slog.w("RestrictionPolicy", "setMockLocationState : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean isMockLocationEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(8796093022208L, true, Utils.getCallingOrCurrentUserId(contextInfo));
        Slog.d("RestrictionPolicy", "isMockLocationEnabled : " + extract);
        return extract;
    }

    public boolean setBackup(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!z) {
                z2 = false;
                try {
                    IBackupManager asInterface = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
                    if (asInterface == null) {
                        Log.w("RestrictionPolicy", "Failed to get BackupManager");
                        return false;
                    }
                    if (asInterface.isBackupEnabled()) {
                        asInterface.setBackupEnabled(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            z2 = true;
            if (z2) {
                z2 &= this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "backupEnabled", z);
                this.mRestrictionCache.update("backupEnabled", 17592186044416L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            }
            return z2;
        } finally {
            Slog.w("RestrictionPolicy", "setBackup : " + z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isBackupAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(17592186044416L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.config_defaultCaptivePortalLoginPackageName);
        }
        Slog.d("RestrictionPolicy", "isBackupAllowed : " + extract);
        return extract;
    }

    public boolean setCellularData(ContextInfo contextInfo, boolean z) {
        boolean z2;
        TelephonyManager telephonyManager;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndRestrictionPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setCellularData due to Ethernet only mode");
            return false;
        }
        Log.i("RestrictionPolicy", "setCellularData() : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = true;
        try {
            z2 = this.mEdmStorageProvider.getBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "cellularDataEnabled");
        } catch (Exception unused) {
            Log.i("RestrictionPolicy", "setCellularData() : fail to get admin policy value = " + enforceOwnerOnlyAndRestrictionPermission.mCallerUid);
            z2 = true;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "cellularDataEnabled", z) & true;
        if (z) {
            z3 = putBoolean;
        } else {
            try {
                telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (telephonyManager != null) {
                if (telephonyManager.getDataEnabled()) {
                    telephonyManager.setDataEnabled(false);
                } else {
                    Log.i("RestrictionPolicy", "setCellularData() : mobile data has already disabled");
                }
            } else {
                Log.i("RestrictionPolicy", "setCellularData() : Failed to get Telephony Manager");
                z3 = false;
            }
        }
        if (!z3) {
            z3 &= this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "cellularDataEnabled", z2);
        } else {
            this.mRestrictionCache.update("cellularDataEnabled", 70368744177664L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        }
        if (z3) {
            setCellularDataAllowedSystemUI(0, isCellularDataAllowed(null));
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled cellular data." : "Admin %d has disabled cellular data.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Slog.d("RestrictionPolicy", "setCellularData() : " + z + ", ret = " + z3);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z3;
    }

    public boolean isCellularDataAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(70368744177664L, true, 0);
        Slog.d("RestrictionPolicy", "isCellularDataAllowed: " + extract);
        return extract;
    }

    public boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) {
        PasswordPolicy passwordPolicy;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        if (getPersonaManagerAdapter().isValidKnoxId(callingOrCurrentUserId)) {
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowSettingsChanges", z);
        this.mRestrictionCache.update("allowSettingsChanges", 8L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowSettingsChanges : " + z);
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
                List<ActivityManager.RecentTaskInfo> recentTasks = activityManager.getRecentTasks(12, 0);
                if (!z && !recentTasks.isEmpty()) {
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                        ComponentName component = recentTaskInfo.baseIntent.getComponent();
                        if (component != null) {
                            String packageName = component.getPackageName();
                            Log.w("RestrictionPolicy", "packageName " + packageName);
                            if (packageName != null && packageName.equals("com.android.settings") && (passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy")) != null && passwordPolicy.isChangeRequestedAsUser(callingOrCurrentUserId) == 0) {
                                activityManager.semRemoveTask(recentTaskInfo.persistentId, 0);
                                try {
                                    ActivityManagerNative.getDefault().forceStopPackage("com.android.settings", callingOrCurrentUserId);
                                } catch (RemoteException e) {
                                    Log.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                                    putBoolean = false;
                                }
                            }
                        }
                    }
                }
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ALLOW_SETTINGS_CHANGES_INTERNAL").addFlags(16777216), new UserHandle(callingOrCurrentUserId));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                setSettingsChangeSystemUI(callingOrCurrentUserId, isSettingsChangesAllowed(enforceRestrictionPermission, false));
                SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3/isSettingsChangesAllowed"));
                SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isSettingsChangesAllowed"));
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return putBoolean;
    }

    public boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) {
        return isSettingsChangesAllowedAsUser(z, getUserIdByPackageNameOrUid(contextInfo));
    }

    public boolean isSettingsChangesAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(8L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.bugreport_title);
        }
        Slog.d("RestrictionPolicy", "isSettingsChangesAllowedAsUser, userId " + i + " : " + extract);
        return extract;
    }

    public boolean setSdCardState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndHwPermission = enforceOwnerOnlyAndHwPermission(contextInfo);
        boolean z2 = false;
        if (getSDCardState() == null) {
            return false;
        }
        Slog.d("RestrictionPolicy", "setSdCardState : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndHwPermission.mCallerUid, "RESTRICTION", "sdCardEnabled", z) & true;
            this.mRestrictionCache.update("sdCardEnabled", 140737488355328L, true, 0, Integer.valueOf(enforceOwnerOnlyAndHwPermission.mCallerUid), Boolean.valueOf(z));
            if (!z) {
                Slog.d("RestrictionPolicy", "SdCard unmount : ");
                unmountSdCard(true);
            } else {
                mountSdCard();
            }
            z2 = putBoolean;
        } catch (Exception unused) {
        }
        if (z2) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled access to external SDCard." : "Admin %d has disabled access to external SDCard.", Integer.valueOf(enforceOwnerOnlyAndHwPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndHwPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public String getSDCardState() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Environment.getExternalStorageState();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSdCardEnabled(ContextInfo contextInfo) {
        if (getSDCardState() == null || getEDM().isRestrictedByConstrainedState(2)) {
            return false;
        }
        boolean extract = this.mRestrictionCache.extract(140737488355328L, true, 0);
        Slog.d("RestrictionPolicy", "isSdCardEnabled : " + extract);
        return extract;
    }

    public boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "clipboardEnabled", z);
        this.mRestrictionCache.update("clipboardEnabled", 512L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
        Slog.d("RestrictionPolicy", "setClipboardEnabled : " + z + ", ret = " + putBoolean);
        return putBoolean;
    }

    public boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) {
        return isClipboardAllowedAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isClipboardAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(512L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.config_sharedConnectivityServicePackage);
        }
        Slog.d("RestrictionPolicy", "isClipboardAllowed : " + extract);
        return extract;
    }

    public boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) {
        Log.w("RestrictionPolicy", "MiscPolicy.setAllowNonMarketApps");
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "setAllowNonMarketApps : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowNonMarketApp", z) & true;
        this.mRestrictionCache.update("allowNonMarketApp", 281474976710656L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        updateNonMarketAppOnUserManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed installation of non-Google-Play application." : "Admin %d has disallowed installation of non-Google-Play application.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            return putBoolean;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateNonMarketAppOnUserManager() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("UserManager.DISALLOW_NON_MARKET_APP_BY_KNOX will be set as ");
                boolean z = true;
                sb.append(!isNonMarketAppAllowed(null));
                Slog.d("RestrictionPolicy", sb.toString());
                UserManager userManager = this.mUserManager;
                if (isNonMarketAppAllowed(null)) {
                    z = false;
                }
                userManager.setUserRestriction("no_non_market_app_by_knox", z, UserHandle.OWNER);
            } catch (SecurityException e) {
                throw new SecurityException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) {
        enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        return false;
    }

    public boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "usbMediaPlayerEnabled", z) & true;
        this.mRestrictionCache.update("usbMediaPlayerEnabled", 562949953421312L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean && !z) {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.MTP_DISABLED_INTERNAL");
                intent.addFlags(536870912);
                intent.setPackage("com.samsung.android.mtp");
                this.mContext.sendBroadcast(intent);
                Intent intent2 = new Intent("android.hardware.usb.action.USB_STATE");
                intent2.addFlags(536870912);
                intent2.putExtra("mtp", false);
                intent2.putExtra("ptp", false);
                this.mContext.sendStickyBroadcastAsUser(intent2, UserHandle.ALL);
            } catch (Exception unused) {
                Slog.w("RestrictionPolicy", "Error in RestrictionPolicy after setting MTP policy");
            }
        }
        Slog.w("RestrictionPolicy", "setUSBMediaPlayerAvailability: " + z);
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled USB Media Player (MTP)." : "Admin %d has disabled USB Media Player (MTP).", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) {
        boolean extract = getEDM().isRestrictedByConstrainedState(4) ? false : this.mRestrictionCache.extract(562949953421312L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.permgroupdesc_location);
        }
        Slog.d("RestrictionPolicy", "isUsbMediaPlayerAvailable : " + extract);
        return extract;
    }

    public final void updateUSBMode() {
        if (isUsbDebuggingEnabled(null) || Settings.Secure.getInt(this.mContext.getContentResolver(), "adb_enabled", 0) != 1) {
            return;
        }
        Settings.Secure.putInt(this.mContext.getContentResolver(), "adb_enabled", 0);
    }

    public boolean isNonMarketAppAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(281474976710656L, true, 0);
        Slog.d("RestrictionPolicy", "isNonMarketAppAllowed : " + extract);
        return extract;
    }

    public boolean setBackgroundData(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "backgroundDataEnabled", z);
        if (putBoolean) {
            this.mRestrictionCache.update("backgroundDataEnabled", 1125899906842624L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            if (z && isDataSavingAllowed()) {
                try {
                    INetworkPolicyManager asInterface = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
                    if (asInterface == null) {
                        throw new NullPointerException("networkPolicyService is null");
                    }
                    if (asInterface.getRestrictBackground()) {
                        asInterface.setRestrictBackground(true);
                        Slog.w("RestrictionPolicy", "Applied Background Data Removal Policy");
                    }
                } catch (Exception e) {
                    Slog.w("RestrictionPolicy", "setBackgroundData EX: " + e);
                }
            } else {
                applyBackgroundDataRestriction();
            }
        }
        Slog.w("RestrictionPolicy", "setBackgroundData : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public void applyBackgroundDataRestriction() {
        INetworkPolicyManager asInterface;
        List users;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.w("RestrictionPolicy", "Applying Background Data Policy");
                asInterface = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
            } catch (Exception e) {
                Slog.w("RestrictionPolicy", "setBackgroundData EX: " + e);
            }
            if (asInterface == null) {
                throw new NullPointerException("networkPolicyService is null");
            }
            boolean isDataSavingAllowed = isDataSavingAllowed();
            if (isDataSavingAllowed && !isBackgroundDataEnabled(null)) {
                asInterface.setRestrictBackground(true);
                ArrayList arrayList = new ArrayList();
                if (arrayList.size() > 0) {
                    asInterface.setNetworkPolicies((NetworkPolicy[]) arrayList.toArray(new NetworkPolicy[arrayList.size()]));
                }
            } else if (!isDataSavingAllowed) {
                asInterface.setRestrictBackground(false);
                PackageManager packageManager = this.mContext.getPackageManager();
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                if (userManager != null && (users = userManager.getUsers()) != null) {
                    Iterator it = users.iterator();
                    while (it.hasNext()) {
                        Iterator it2 = packageManager.getInstalledPackagesAsUser(64, ((UserInfo) it.next()).id).iterator();
                        while (it2.hasNext()) {
                            int i = ((PackageInfo) it2.next()).applicationInfo.uid;
                            if (UserHandle.isApp(i)) {
                                asInterface.setUidPolicy(i, 0);
                            }
                        }
                    }
                }
            }
            Slog.w("RestrictionPolicy", "Applied Background Data Policy");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isBackgroundDataEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(1125899906842624L, true, 0);
        Slog.w("RestrictionPolicy", "isBackgroundDataEnabled : " + extract);
        return extract;
    }

    public boolean allowFactoryReset(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean isFactoryResetAllowedFromDB = isFactoryResetAllowedFromDB(enforceOwnerOnlyAndRestrictionPermission);
        Slog.d("RestrictionPolicy", "allowFactoryReset : oldState - " + isFactoryResetAllowedFromDB + ", allow - " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "factoryresetallowed", z);
        this.mRestrictionCache.update("factoryresetallowed", 2251799813685248L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            if (isFactoryResetAllowedFromDB == isFactoryResetAllowedFromDB(enforceOwnerOnlyAndRestrictionPermission)) {
                Slog.d("RestrictionPolicy", "allowFactoryReset : do not need to update");
                return true;
            }
            manageEFSFile(z, "/efs/MDM/FactoryReset");
        }
        Slog.d("RestrictionPolicy", "allowFactoryReset : " + putBoolean);
        return putBoolean;
    }

    public boolean isFactoryResetAllowed(ContextInfo contextInfo) {
        boolean isFactoryResetAllowedFromDB = isFactoryResetAllowedFromDB(contextInfo);
        boolean isExistEFSFile = isExistEFSFile("/efs/MDM/FactoryReset");
        Slog.d("RestrictionPolicy", "isFactoryResetAllowed(): isExistEFSFile - " + isExistEFSFile + " DB - " + isFactoryResetAllowedFromDB);
        if (isExistEFSFile == isFactoryResetAllowedFromDB) {
            Slog.d("RestrictionPolicy", "isFactoryResetAllowed(): need to sync DB(" + isFactoryResetAllowedFromDB + ") and efs");
            manageEFSFile(isFactoryResetAllowedFromDB, "/efs/MDM/FactoryReset");
        }
        return isFactoryResetAllowedFromDB;
    }

    public final boolean isFactoryResetAllowedFromDB(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(2251799813685248L, true, 0);
    }

    public boolean setHomeKeyState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        if (Utils.getCallingOrCurrentUserId(enforceRestrictionPermission) != 0) {
            Log.w("RestrictionPolicy", "setHomeKeyState() : Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "homeKeyEnabled", z);
        this.mRestrictionCache.update("homeKeyEnabled", 4503599627370496L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (z == isHomeKeyEnabled(enforceRestrictionPermission, false)) {
            setHomeKeyVisibilityOnNavi(z);
        }
        updateKeyCodeHomeState();
        return putBoolean;
    }

    public final void setHomeKeyVisibilityOnNavi(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mStatusBarService == null) {
                this.mStatusBarService = getStatusBarService();
            }
            IStatusBarService iStatusBarService = this.mStatusBarService;
            if (iStatusBarService != null) {
                if (!z) {
                    iStatusBarService.disable(2097152, this.mToken, "RestrictionPolicy");
                } else {
                    iStatusBarService.disable(0, this.mToken, "RestrictionPolicy");
                }
            }
        } catch (Exception unused) {
            Log.d("RestrictionPolicy", "setHomeAndRecentKey was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void updateKeyCodeHomeState() {
        KeyCodeMediator keyCodeMediator = this.mKeyCodeMediator;
        if (keyCodeMediator == null) {
            Log.e("RestrictionPolicy", "mKeyCodeMediator must not be null! This will cause problems on hardware key restriction.");
        } else {
            keyCodeMediator.update(3);
        }
    }

    public final boolean isHomeKeyEnabledInDb() {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser("RESTRICTION", "homeKeyEnabled", 0).iterator();
        while (it.hasNext()) {
            if (!((Boolean) it.next()).booleanValue()) {
                Slog.d("RestrictionPolicy", "isHomeKeyEnabledInDb : false");
                return false;
            }
        }
        Slog.d("RestrictionPolicy", "isHomeKeyEnabledInDb : true");
        return true;
    }

    public boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(4503599627370496L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.mediasize_na_index_4x6);
        }
        Slog.d("RestrictionPolicy", "isHomeKeyEnabled :" + extract);
        return extract;
    }

    public final int getTopActivityUserId() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = getPersonaManagerAdapter().getFocusedUserWithActivityManager();
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "getTopActivityUserId() failed", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = 0;
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean allowVpn(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "nativeVpnAllowed", z);
        this.mRestrictionCache.update("nativeVpnAllowed", 9007199254740992L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean && !z) {
            getVpnInfoPolicy().disconnectActiveVpnConnections();
        }
        Slog.d("RestrictionPolicy", "allowVpn():set :" + z + "ret:" + putBoolean);
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled Vpn." : "Admin %d has disabled Vpn.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public boolean isVpnAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(9007199254740992L, true, 0);
        Slog.d("RestrictionPolicy", "isVpnAllowed():ret:" + extract);
        return extract;
    }

    public boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "allowOTAUpgrade : " + z);
        boolean isOTAUpgradeAllowed = isOTAUpgradeAllowed(null);
        this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "OTAUpgradeEnabled", z);
        this.mRestrictionCache.update("OTAUpgradeEnabled", 18014398509481984L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        boolean isOTAUpgradeAllowed2 = isOTAUpgradeAllowed(null);
        if (isOTAUpgradeAllowed2 == isOTAUpgradeAllowed || isOTAUpgradeAllowed) {
            return true;
        }
        getApplicationPolicy().setApplicationState(enforceOwnerOnlyAndRestrictionPermission, "com.sec.android.fotaclient", isOTAUpgradeAllowed2);
        getApplicationPolicy().setApplicationState(enforceOwnerOnlyAndRestrictionPermission, "com.wssyncmldm", isOTAUpgradeAllowed2);
        getApplicationPolicy().setApplicationState(enforceOwnerOnlyAndRestrictionPermission, "com.ws.dm", isOTAUpgradeAllowed2);
        return true;
    }

    public boolean isOTAUpgradeAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(18014398509481984L, true, 0);
        Slog.d("RestrictionPolicy", "isOTAUpgradeAllowed : " + extract);
        return extract;
    }

    public boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "googleCrashReportEnabled", z);
        this.mRestrictionCache.update("googleCrashReportEnabled", 36028797018963968L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowGoogleCrashReport : allow=" + z + " callingUid=" + enforceRestrictionPermission.mCallerUid);
        return putBoolean;
    }

    public boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) {
        return isGoogleCrashReportAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isGoogleCrashReportAllowedAsUser(int i) {
        boolean extract = this.mRestrictionCache.extract(36028797018963968L, true, i);
        Slog.d("RestrictionPolicy", "isGoogleCrashReportAllowed : ret=" + extract + " userId =" + i);
        return extract;
    }

    public StorageManager getStorageManager() {
        if (this.mStorageManager == null) {
            this.mStorageManager = (StorageManager) this.mContext.getSystemService("storage");
        }
        return this.mStorageManager;
    }

    public final IStorageManagerAdapter getStorageAdapter() {
        return (IStorageManagerAdapter) AdapterRegistry.getAdapter(IStorageManagerAdapter.class);
    }

    public boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean isSDCardWriteAllowed = isSDCardWriteAllowed(null);
        Slog.d("RestrictionPolicy", "allowSDCardWrite : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "sdCardWriteEnabled", z);
        this.mRestrictionCache.update("sdCardWriteEnabled", 34359738368L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (isSDCardWriteAllowed(null) != isSDCardWriteAllowed) {
                applySdCardWriteAccessPolicy();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean;
    }

    public boolean isSDCardWriteAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(34359738368L, true, 0);
        Slog.d("RestrictionPolicy", "isSDCardWriteAllowed : " + extract);
        return extract;
    }

    public final void applySdCardWriteAccessPolicy() {
        Utils.writePropertyValue("sdCardWriteAccessBlocked", isSDCardWriteAllowed(null) ? "0" : "1", "/data/system/enterprise.conf");
        if (isExternalSdCardPresent() && isSdCardEnabled(null)) {
            getStorageManager().registerListener(this.mStorageListener);
            unmountSdCard(true);
        }
    }

    public final synchronized IStorageManager getMountService() {
        IStorageManager iStorageManager;
        try {
            iStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
        } catch (Exception e) {
            e.printStackTrace();
            iStorageManager = null;
        }
        return iStorageManager;
    }

    public final boolean unmountSdCard(boolean z) {
        String externalSdCardPath;
        IStorageManager mountService = getMountService();
        if (mountService == null || (externalSdCardPath = getExternalSdCardPath()) == null) {
            return false;
        }
        try {
            mountService.unmountVolume(externalSdCardPath, z, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isExternalSdCardPresent() {
        return getStorageAdapter().getVolumeList().length >= 2;
    }

    public final String getExternalSdCardPath() {
        for (StorageVolume storageVolume : getStorageAdapter().getVolumeList()) {
            if (storageVolume.getSubSystem().equals("sd")) {
                return storageVolume.getPath();
            }
        }
        return null;
    }

    public final boolean mountSdCard() {
        IStorageManager mountService = getMountService();
        if (mountService == null) {
            return false;
        }
        try {
            String externalSdCardPath = getExternalSdCardPath();
            if (externalSdCardPath == null) {
                Log.d("RestrictionPolicy", "mountSdCard() : fail to mount media because path is null.");
                return false;
            }
            int mountVolume = mountService.mountVolume(externalSdCardPath);
            if (mountVolume == 0) {
                return true;
            }
            Log.d("RestrictionPolicy", "mountSdCard() : Unable to mount media - error code : " + mountVolume);
            return false;
        } catch (RemoteException e) {
            Log.e("RestrictionPolicy", "mountSdCard() : failed by RemoteException.", e);
            return false;
        } catch (Exception e2) {
            Log.e("RestrictionPolicy", "mountSdCard() : failed by Exception.", e2);
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
        if (this.mKcUid == -1) {
            this.mKcUid = getKcAdminUid();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (!z) {
            loadRestrictionCacheAndNotifyChanges(UserHandle.getUserId(i));
        }
        ContextInfo contextInfo = new ContextInfo(i);
        isFactoryResetAllowed(contextInfo);
        isFirmwareRecoveryAllowed(contextInfo, false);
        Utils.writePropertyValue("microphoneEnabled", "" + (isMicrophoneEnabled(new ContextInfo(i), false) ? 1 : 0), "/data/system/enterprise.conf");
        Utils.writePropertyValue("screenCaptureEnabled", "" + (isScreenCaptureEnabled(new ContextInfo(i), false) ? 1 : 0), "/data/system/enterprise.conf");
        if (isSDCardWriteAllowed(null) != this.preAdminRemoval_SDCardWrite) {
            applySdCardWriteAccessPolicy();
            this.preAdminRemoval_SDCardWrite = false;
        }
        rollBackSVoice(UserHandle.getUserId(i));
        setAllowedFOTAVersion(new ContextInfo(Binder.getCallingUid()), null, null, false);
        updateUsbStorageStatebyIntent(isUsbHostStorageAllowed(new ContextInfo(Binder.getCallingUid()), false));
        if (!this.isLockScreenWidgetsAllowedCache && isLockScreenViewAllowed(null, 1)) {
            setLockScreenViewProperty(1, true);
            this.isLockScreenWidgetsAllowedCache = true;
        }
        if (!this.isLockScreenShortcutsAllowedCache && isLockScreenViewAllowed(null, 2)) {
            setLockScreenViewProperty(2, true);
            this.isLockScreenShortcutsAllowedCache = true;
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
        if (!this.isSafeModeAllowedCache && isSafeModeAllowed(null) && callingOrCurrentUserId == 0 && !getUserRestrictionSafeMode(0)) {
            setSafeModeProperty(true);
        }
        if (this.mUsbSyncFlag) {
            setUsbExceptionListOnDriver(getUsbExceptionList());
            this.mUsbSyncFlag = false;
        }
        updateNonMarketAppOnUserManager();
        if (isHomeKeyEnabledInDb()) {
            setHomeKeyVisibilityOnNavi(true);
        }
        if (this.mKcUid == i) {
            Iterator it = this.mUserRestrictionEnforcedByKC.iterator();
            while (it.hasNext()) {
                this.mUserManager.setUserRestriction((String) it.next(), false);
            }
            this.mUserRestrictionEnforcedByKC = new ArraySet();
            this.mKcUid = -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x006b, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0068, code lost:
    
        if (r5 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void evaluateAndMigrateWifiRelatedPolicyAPIs() {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system process");
        }
        Log.i("RestrictionPolicy", "evaluateAndMigrateWifiRelatedPolicyAPIs - START");
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("RESTRICTION", new String[]{"wifiTetheringEnabled", "allowWifiDirect"}, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    boolean z = false;
                    boolean z2 = false;
                    while (cursor.moveToNext()) {
                        if (cursor.getInt(cursor.getColumnIndex("wifiTetheringEnabled")) == 0) {
                            z = true;
                        }
                        if (cursor.getInt(cursor.getColumnIndex("allowWifiDirect")) == 0) {
                            z2 = true;
                        }
                    }
                    if (z) {
                        setWifiTetheringUserRestriction(true);
                    }
                    if (z2) {
                        setWifiDirectUserRestriction(true);
                    }
                }
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "evaluateAndMigrateWifiRelatedPolicyAPIs", e);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            Log.i("RestrictionPolicy", "evaluateAndMigrateWifiRelatedPolicyAPIs - END");
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public Set getRestrictedKeyCodes() {
        if (isHomeKeyEnabled(null, false)) {
            return null;
        }
        return new HashSet(Arrays.asList(3));
    }

    public final void loadRestrictionCacheAndNotifyChanges(int i) {
        this.mRestrictionCache.load(i);
        notifyChanges(i);
    }

    public final void notifyChanges(int i) {
        if (i != -1) {
            updateScreenCaptureDisabledInWindowManager(i, !isScreenCaptureEnabled(i, false));
        } else {
            List users = this.mUserManager.getUsers(true);
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                updateScreenCaptureDisabledInWindowManager(((UserInfo) users.get(i2)).id, !isScreenCaptureEnabled(r4, false));
            }
        }
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isCameraEnabled"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3/isSettingsChangesAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isSettingsChangesAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isCameraEnabled"));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        StorageVolume[] volumeList;
        this.mRestrictionCache.load(-1);
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService("storage");
        if (storageManager != null && (volumeList = storageManager.getVolumeList()) != null && volumeList.length > 1) {
            int length = volumeList.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    StorageVolume storageVolume = volumeList[i];
                    if (storageVolume != null && storageVolume.allowMassStorage()) {
                        this.mIsUsbMassStorageAvailable = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
        }
        this.mUserRestrictionEnforcedByKC = new ArraySet(getUserRestrictionsApplied());
        this.mKcUid = getKcAdminUid();
        LocalServices.addService(RestrictionPolicyInternal.class, new LocalService());
        Slog.d("RestrictionPolicy", "systemReady()");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        resetWifiRelatedPolicyAPIs(i);
        this.preAdminRemoval_SDCardWrite = isSDCardWriteAllowed(null);
        this.isLockScreenWidgetsAllowedCache = isLockScreenViewAllowed(null, 1);
        this.isLockScreenShortcutsAllowedCache = isLockScreenViewAllowed(null, 2);
        this.isSafeModeAllowedCache = isSafeModeAllowed(null);
        try {
            Integer asInteger = this.mEdmStorageProvider.getValues(i, "RESTRICTION", new String[]{"UsbExceptionMask"}).getAsInteger("UsbExceptionMask");
            if (asInteger != null && asInteger.intValue() == -1) {
                this.mUsbSyncFlag = false;
            } else {
                this.mUsbSyncFlag = true;
            }
            Log.d("RestrictionPolicy", "onPreAdminRemoval - Exception mask : " + asInteger + " | Sync : " + this.mUsbSyncFlag);
        } catch (Exception unused) {
            Log.e("RestrictionPolicy", "Read DB Error");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0098, code lost:
    
        if (0 == 0) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetWifiRelatedPolicyAPIs(int i) {
        Log.i("RestrictionPolicy", "resetWifiRelatedPolicyAPIs - START");
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("RESTRICTION", new String[]{"adminUid", "wifiTetheringEnabled", "allowWifiDirect"}, null, null);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "resetWifiRelatedPolicyAPIs", e);
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int i2 = 0;
                int i3 = 0;
                do {
                    int i4 = cursor.getInt(cursor.getColumnIndex("adminUid"));
                    if (cursor.getInt(cursor.getColumnIndex("wifiTetheringEnabled")) == 0) {
                        if (i2 == 0) {
                            i2 = i4;
                        } else if (i2 != -1) {
                            i2 = -1;
                        }
                    }
                    if (cursor.getInt(cursor.getColumnIndex("allowWifiDirect")) == 0) {
                        if (i3 == 0) {
                            i3 = i4;
                        } else if (i3 != -1) {
                            i3 = -1;
                        }
                    }
                } while (cursor.moveToNext());
                if (i2 == i) {
                    setWifiTetheringUserRestriction(false);
                }
                if (i3 == i) {
                    setWifiDirectUserRestriction(false);
                }
                cursor.close();
                Log.i("RestrictionPolicy", "resetWifiRelatedPolicyAPIs - END");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("resetWifiRelatedPolicyAPIs - ");
            sb.append(cursor == null ? "Cursor is null" : "Cursor is empty");
            Log.i("RestrictionPolicy", sb.toString());
            if (cursor != null) {
                cursor.close();
            }
            Log.i("RestrictionPolicy", "resetWifiRelatedPolicyAPIs - END");
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            Log.i("RestrictionPolicy", "resetWifiRelatedPolicyAPIs - END");
            throw th;
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeCallback
    public void setMediator(KeyCodeMediator keyCodeMediator) {
        if (this.mKeyCodeMediator == null) {
            this.mKeyCodeMediator = keyCodeMediator;
            keyCodeMediator.registerCallback(this);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public boolean isKeyCodeInputAllowed(int i) {
        if (i == 3) {
            return isHomeKeyEnabled(null, false);
        }
        return true;
    }

    public boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "wallpaperEnabled", z);
        this.mRestrictionCache.update("wallpaperEnabled", 17179869184L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowWallpaperChange : " + z);
        return putBoolean;
    }

    public boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(17179869184L, true, Utils.getCallingOrCurrentUserId(contextInfo));
        if (z && !extract) {
            RestrictionToastManager.show(17043262);
        }
        Slog.d("RestrictionPolicy", "isWallpaperChangeAllowed : " + extract);
        return extract;
    }

    public boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        if (callingOrCurrentUserId != 0) {
            Log.w("RestrictionPolicy", "Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "statusBarExpansionEnabled", z);
        if (putBoolean) {
            this.mRestrictionCache.update("statusBarExpansionEnabled", 256L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            setStatusBarExpansionSystemUI(callingOrCurrentUserId, isStatusBarExpansionAllowedAsUser(false, callingOrCurrentUserId));
        }
        Log.d("RestrictionPolicy", "allowStatusBarExpansion : " + z + ", ret = " + putBoolean);
        return putBoolean;
    }

    public boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) {
        return isStatusBarExpansionAllowedAsUser(z, 0);
    }

    public boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(256L, true, 0);
        Log.d("RestrictionPolicy", "isStatusBarExpansionAllowedAsUser : " + extract + ", userId = 0");
        return extract;
    }

    public boolean allowPowerOff(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        if (callingOrCurrentUserId != 0) {
            Log.w("RestrictionPolicy", "Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "powerOffAllowed", z);
        this.mRestrictionCache.update("powerOffAllowed", 2048L, true, callingOrCurrentUserId, Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            manageEFSFile(z, "efs/MDM/PowerOff");
            setPowerOffUserRestriction(z);
        }
        return putBoolean;
    }

    public boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(2048L, true, 0);
        if (isExistEFSFile("efs/MDM/PowerOff") == extract) {
            Slog.d("RestrictionPolicy", "isPowerOffAllowed(): need to sync DB(" + extract + ") and efs");
            manageEFSFile(extract, "efs/MDM/PowerOff");
        }
        if (z && !extract) {
            RestrictionToastManager.show(R.string.mediasize_chinese_prc_3);
        }
        Slog.d("RestrictionPolicy", "isPowerOffAllowed : " + extract);
        return extract;
    }

    public final void setPowerOffUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = true;
        try {
            MaintenanceModeUtils.setDisallowedSetting(!z);
            StringBuilder sb = new StringBuilder();
            sb.append("setMaintenanceModeDisallowedSetting - value = ");
            if (z) {
                z2 = false;
            }
            sb.append(z2);
            Log.i("RestrictionPolicy", sb.toString());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean allowAudioRecord(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "allowAudioRecording", z);
        Slog.d("RestrictionPolicy", "allowAudioRecord : " + z);
        this.mRestrictionCache.update("allowAudioRecording", 1L, true, UserHandle.getUserId(enforceHwPermission.mCallerUid), Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            if (!isAudioRecordAllowed(enforceHwPermission, false)) {
                this.mAudioManager.setParameters("g_knox_audiorecord_allowed=false");
            } else {
                this.mAudioManager.setParameters("g_knox_audiorecord_allowed=true");
            }
        }
        return putBoolean;
    }

    public boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract = this.mRestrictionCache.extract(1L, true, callingOrCurrentUserId);
        Slog.d("RestrictionPolicy", "userId: " + callingOrCurrentUserId + " isAudioRecordAllowed : " + extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042446);
        }
        return extract;
    }

    public boolean allowVideoRecord(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceHwPermission.mCallerUid, "RESTRICTION", "allowVideoRecording", z);
        Slog.d("RestrictionPolicy", "allowVideoRecord : " + z);
        this.mRestrictionCache.update("allowVideoRecording", 2L, true, UserHandle.getUserId(enforceHwPermission.mCallerUid), Integer.valueOf(enforceHwPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract = this.mRestrictionCache.extract(2L, true, callingOrCurrentUserId);
        Slog.d("RestrictionPolicy", "userId: " + callingOrCurrentUserId + " isVideoRecordAllowed : " + extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042455);
        }
        return extract;
    }

    public boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowStopSystemApp", z);
        this.mRestrictionCache.update("allowStopSystemApp", 4096L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowStopSystemApp : " + z);
        return putBoolean;
    }

    public boolean isStopSystemAppAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(4096L, true, Utils.getCallingOrCurrentUserId(contextInfo));
        Slog.d("RestrictionPolicy", "isStopSystemAppAllowed : " + extract);
        return extract;
    }

    public boolean allowWifiDirect(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Log.i("RestrictionPolicy", "allowWifiDirect - caller uid: " + enforceOwnerOnlyAndRestrictionPermission.mCallerUid + ", allow: " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowWifiDirect", z);
        if (!putBoolean) {
            Log.e("RestrictionPolicy", "allowWifiDirect - fail to store value to database");
            return putBoolean;
        }
        this.mRestrictionCache.update("allowWifiDirect", 8192L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, putBoolean, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled Wifi Direct." : "Admin %d has disabled Wifi Direct.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setWifiDirectUserRestriction(!isWifiDirectAllowed(null, false));
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setWifiDirectUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManager.setUserRestriction("no_wifi_direct", z);
            Log.i("RestrictionPolicy", "setWifiDirectUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(8192L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17042445);
        }
        Log.i("RestrictionPolicy", "isWifiDirectAllowed: " + extract);
        return extract;
    }

    public boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (!z) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManagerNative.getDefault().setProcessLimit(-1);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (RemoteException e) {
                Log.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                z2 = false;
            }
        }
        z2 = true;
        if (z2) {
            z2 &= this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "limitOfBackgroundProcess", z);
            this.mRestrictionCache.update("limitOfBackgroundProcess", 16384L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        }
        Slog.d("RestrictionPolicy", "allowBackgroundProcessLimit : " + z);
        return z2;
    }

    public boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(16384L, true, 0);
        Slog.d("RestrictionPolicy", "isBackgroundProcessLimitAllowed : " + extract);
        return extract;
    }

    public boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean z2 = true;
        if (!z) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManagerNative.getDefault().setAlwaysFinish(z);
                z2 = true & (Settings.System.getInt(this.mContext.getContentResolver(), "always_finish_activities", 0) == 0);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (RemoteException e) {
                Slog.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                z2 = false;
            }
        }
        if (!z2) {
            return z2;
        }
        boolean putBoolean = z2 & this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowKeepActivities", z);
        this.mRestrictionCache.update("allowKeepActivities", 32768L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(32768L, true, 0);
    }

    public boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowMobileDataLimit", z) & true;
        this.mRestrictionCache.update("allowMobileDataLimit", 65536L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (z || !putBoolean) {
            return putBoolean;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            INetworkPolicyManager asInterface = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
            if (asInterface == null) {
                throw new NullPointerException("networkPolicyService is null");
            }
            ArrayList arrayList = new ArrayList();
            NetworkPolicy[] networkPolicies = asInterface.getNetworkPolicies(this.mContext.getOpPackageName());
            if (networkPolicies != null && networkPolicies.length > 0) {
                for (NetworkPolicy networkPolicy : networkPolicies) {
                    networkPolicy.limitBytes = -1L;
                    arrayList.add(networkPolicy);
                }
            }
            if (arrayList.size() > 0) {
                asInterface.setNetworkPolicies((NetworkPolicy[]) arrayList.toArray(new NetworkPolicy[arrayList.size()]));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return putBoolean;
        } catch (Exception e) {
            e.printStackTrace();
            Slog.w("RestrictionPolicy", "RestrictionPolicy.allowUserMobileDataLimit() exception : " + e.getMessage());
            return false;
        }
    }

    public boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(65536L, true, 0);
    }

    public boolean allowClipboardShare(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowClipboardShare", z);
        this.mRestrictionCache.update("allowClipboardShare", 131072L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
        Slog.d("RestrictionPolicy", "allowClipboardShare : " + z + ", ret = " + putBoolean);
        return putBoolean;
    }

    public boolean isClipboardShareAllowed(ContextInfo contextInfo) {
        return isClipboardShareAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isClipboardShareAllowedAsUser(int i) {
        boolean extract = this.mRestrictionCache.extract(131072L, true, i);
        Slog.d("RestrictionPolicy", "isClipboardShareAllowed : " + extract);
        return extract;
    }

    public final void rollBackSVoice(int i) {
        String genericValueAsUser;
        if (!isSVoiceAllowedAsUser(false, i) || (genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("voiceControl", i)) == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "voice_input_control", Integer.parseInt(genericValueAsUser), i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public boolean allowSVoice(ContextInfo contextInfo, boolean z) {
        long clearCallingIdentity;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean isSVoiceAllowed = isSVoiceAllowed(enforceRestrictionPermission, false);
        if (!z && isSVoiceAllowed) {
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "wake_up_lock_screen", 0, callingOrCurrentUserId);
            this.mEdmStorageProvider.putGenericValueAsUser("voiceControl", Integer.toString(Settings.System.getIntForUser(this.mContext.getContentResolver(), "voice_input_control", 0, callingOrCurrentUserId)), callingOrCurrentUserId);
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "voice_input_control", 0, callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity2);
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowSVoice", z);
        this.mRestrictionCache.update("allowSVoice", 32L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (z) {
            rollBackSVoice(callingOrCurrentUserId);
        }
        if (putBoolean && isSVoiceAllowed && !z) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            List<ActivityManager.RecentTaskInfo> recentTasks = activityManager.getRecentTasks(12, 0);
            if (!recentTasks.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                    ComponentName component = recentTaskInfo.baseIntent.getComponent();
                    if (component != null) {
                        String packageName = component.getPackageName();
                        Log.w("RestrictionPolicy", "packageName " + packageName);
                        if (packageName != null && (packageName.equals("com.vlingo.midas") || packageName.equals("com.samsung.voiceserviceplatform"))) {
                            activityManager.semRemoveTask(recentTaskInfo.persistentId, 0);
                            try {
                                ActivityManagerNative.getDefault().forceStopPackage(packageName, callingOrCurrentUserId);
                                break;
                            } catch (RemoteException e) {
                                Log.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                                putBoolean = false;
                            }
                        }
                    }
                }
            }
        }
        Slog.d("RestrictionPolicy", "allowSVoice : " + z);
        if (putBoolean) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed SVoice." : "Admin %d has disallowed SVoice.", Integer.valueOf(enforceRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceRestrictionPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public boolean isSVoiceAllowedAsUser(boolean z, int i) {
        Binder.restoreCallingIdentity(Binder.clearCallingIdentity());
        boolean extract = this.mRestrictionCache.extract(32L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(17042976);
        }
        Slog.d("RestrictionPolicy", "isSVoiceAllowedAsUser, userId " + i + " : " + extract);
        return extract;
    }

    public boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) {
        return isSVoiceAllowedAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isUsbHostStorageAllowed = isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowUsbHostStorage", z) & true;
        Slog.d("RestrictionPolicy", "allowUsbHostStorage : " + z);
        this.mRestrictionCache.update("allowUsbHostStorage", 524288L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed USB Host Storage." : "Admin %d has disallowed USB Host Storage.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        if (putBoolean) {
            try {
                if (!z) {
                    try {
                        StorageManager storageManager = (StorageManager) this.mContext.getSystemService("storage");
                        if (storageManager == null) {
                            Log.w("RestrictionPolicy", "Failed to get StorageManager");
                            return false;
                        }
                        StorageVolume[] volumeList = storageManager.getVolumeList();
                        IStorageManager mountService = getMountService();
                        for (StorageVolume storageVolume : volumeList) {
                            String subSystem = storageVolume.getSubSystem();
                            if (subSystem != null && subSystem.equals("usb")) {
                                String path = storageVolume.getPath();
                                if ("mounted".equals(storageManager.getVolumeState(path)) && mountService != null) {
                                    mountService.unmountVolume(path, true, false);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        putBoolean = false;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (putBoolean) {
            if (!updateUsbStorageStatebyIntent(isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false))) {
                Log.d("RestrictionPolicy", "allowUsbHostStorage() : failed to call updateUsbStorageStatebyIntent()");
            }
            if (!isUsbHostStorageAllowed && isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false)) {
                Log.d("RestrictionPolicy", "allowUsbHostStorage set true(false -> true). Sync UsbInterface Exception");
                syncUsbExceptionList(getUsbExceptionList());
            }
        }
        return putBoolean;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [long] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    public final boolean updateUsbStorageStatebyIntent(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL");
                intent.putExtra("reason", z ? 0 : 1);
                this.mContext.sendBroadcast(intent);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = true;
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "updateUsbStorageStatebyIntent() fas failed.", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            StringBuilder sb = new StringBuilder();
            clearCallingIdentity = "updateUsbStorageStatebyIntent() allow = ";
            sb.append("updateUsbStorageStatebyIntent() allow = ");
            sb.append(z);
            sb.append(", ret = ");
            sb.append(z2);
            Slog.d("RestrictionPolicy", sb.toString());
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(524288L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17043202);
        }
        Slog.d("RestrictionPolicy", "isUsbHostStorageAllowed : " + extract);
        return extract;
    }

    public boolean allowShareList(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowShareList", z);
        this.mRestrictionCache.update("allowShareList", 1048576L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowShareList : allow=" + z + " containerId=" + enforceRestrictionPermission.mContainerId + " = callingUid=" + enforceRestrictionPermission.mCallerUid);
        return putBoolean;
    }

    public boolean isShareListAllowed(ContextInfo contextInfo, boolean z) {
        return isShareListAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public boolean isShareListAllowedAsUser(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(1048576L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(17042810);
        }
        Slog.d("RestrictionPolicy", "isShareListAllowed : userId(" + i + ")");
        return extract;
    }

    public boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "setUseSecureKeypad : " + z);
        Slog.d("RestrictionPolicy", "setUseSecureKeypad containerID : " + enforceRestrictionPermission.mContainerId);
        if (!getPersonaManagerAdapter().isSamsungWorkspace(enforceRestrictionPermission.mContainerId)) {
            Slog.d("RestrictionPolicy", "setUseSecureKeypad fails. PO not supported.");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "useSecureKeypad", z);
        this.mRestrictionCache.update("useSecureKeypad", 2097152L, false, Utils.getCallingOrCurrentUserId(enforceRestrictionPermission), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public final IPersonaManagerAdapter getPersonaManagerAdapter() {
        return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }

    public boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = contextInfo.mContainerId;
        boolean extract = this.mRestrictionCache.extract(2097152L, false, callingOrCurrentUserId);
        Slog.d("RestrictionPolicy", "isUseSecureKeypadEnabled:" + extract + " cxtInfo.mContainerId:" + contextInfo.mContainerId + " userid:" + callingOrCurrentUserId);
        return extract;
    }

    public boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceCertificatePermission = enforceCertificatePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCertificatePermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceCertificatePermission.mCallerUid, "RESTRICTION", "blockNonTrustedApp", z);
        this.mRestrictionCache.update("blockNonTrustedApp", 4194304L, false, callingOrCurrentUserId, Integer.valueOf(enforceCertificatePermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) {
        return isNonTrustedAppInstallBlockedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isNonTrustedAppInstallBlockedAsUser(int i) {
        return this.mRestrictionCache.extract(4194304L, false, i);
    }

    public boolean allowSafeMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "allowSafeMode : " + z);
        if (!z && isSafeModeAllowed(null)) {
            setSafeModeProperty(z);
        }
        try {
            z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowSafeMode", z);
        } catch (Exception e) {
            Log.e("RestrictionPolicy", "allowSafeMode() : failed with error.", e);
            z2 = false;
        }
        if ((!z2 && !z && getUserRestrictionSafeMode(0)) || (z2 && z && !getUserRestrictionSafeMode(0))) {
            setSafeModeProperty(isSafeModeAllowed(null));
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean setSafeModeProperty(boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = false;
        try {
            try {
                int i = Settings.Global.getInt(this.mContext.getContentResolver(), "safe_boot_disallowed", 0);
                z2 = true;
                if ((z && i != 0) || (!z && i != 1)) {
                    z2 = Settings.Global.putInt(this.mContext.getContentResolver(), "safe_boot_disallowed", z ? 0 : 1);
                } else {
                    try {
                        Log.d("RestrictionPolicy", "setSafeModeProperty() : already applied. = " + i);
                    } catch (Exception e) {
                        e = e;
                        z3 = true;
                        Log.e("RestrictionPolicy", "setSafeModeProperty() failed.", e);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        z2 = z3;
                        if (!z2) {
                        }
                        return z2;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            if (!z2) {
                Log.d("RestrictionPolicy", "setSafeModeProperty() failed, allow = " + z);
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean getUserRestrictionSafeMode(int i) {
        PackageManagerAdapter.getInstance(this.mContext);
        return PackageManagerAdapter.getUserRestrictions(i, "no_safe_boot", false);
    }

    public boolean isSafeModeAllowed(ContextInfo contextInfo) {
        boolean z;
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("RESTRICTION", "allowSafeMode").iterator();
            while (it.hasNext()) {
                z = ((Boolean) it.next()).booleanValue();
                if (!z) {
                    break;
                }
            }
        } catch (Exception unused) {
            Slog.d("RestrictionPolicy", "isSafeModeAllowed() failed");
        }
        z = true;
        if (!z) {
            Log.d("RestrictionPolicy", "isSafeModeAllowed() - not allowed ");
        }
        Slog.d("RestrictionPolicy", "isSafeModeAllowed : " + z);
        return z;
    }

    public final boolean setLockScreenViewProperty(int i, boolean z) {
        if ((i & 1) != 1) {
            if ((i & 2) != 2) {
                return false;
            }
            boolean putInt = Settings.System.putInt(this.mContext.getContentResolver(), "lock_screen_shortcut", z ? 1 : 0) & Settings.System.putInt(this.mContext.getContentResolver(), "set_shortcuts_mode", z ? 1 : 0);
            if (z) {
                return putInt;
            }
            Settings.System.putInt(this.mContext.getContentResolver(), "lockscreen_show_shortcut", 0);
            return putInt;
        }
        boolean putInt2 = Settings.System.putInt(this.mContext.getContentResolver(), "kg_multiple_lockscreen", z ? 1 : 0) & Settings.System.putInt(this.mContext.getContentResolver(), "kg_enable_camera_widget", z ? 1 : 0);
        if (z) {
            return putInt2;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.KNOX_FACE_WIDGET_OFF_INTERNAL");
            intent.setPackage("com.android.settings");
            this.mContext.sendBroadcast(intent);
            return putInt2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                try {
                } catch (Exception e) {
                    Log.e("RestrictionPolicy", "allowLockScreenView() failed: unexpected exception", e);
                }
            } catch (InvalidParameterException e2) {
                Log.d("RestrictionPolicy", "allowLockScreenView() failed: INVALID PARAMETER INPUT", e2);
            }
            if (i != 1 && i != 2) {
                throw new InvalidParameterException();
            }
            boolean lockScreenViewProperty = isLockScreenViewAllowed(enforceOwnerOnlyAndRestrictionPermission, i) ? setLockScreenViewProperty(i, z) : false;
            try {
                i2 = this.mEdmStorageProvider.getInt(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowLockScreenViews");
            } catch (SettingNotFoundException unused) {
                Log.d("RestrictionPolicy", "allowLockScreenView() failed: cannot get value from edmstorageprovider.");
                i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            z2 = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowLockScreenViews", true == z ? i2 | i : (~i) & i2);
            if (z2 && !lockScreenViewProperty && isLockScreenViewAllowed(enforceOwnerOnlyAndRestrictionPermission, i)) {
                setLockScreenViewProperty(i, z);
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) {
        boolean z = false;
        if (i != 1 && i != 2) {
            try {
                throw new InvalidParameterException();
            } catch (InvalidParameterException e) {
                e = e;
                Log.d("RestrictionPolicy", "isLockScreenViewAllowed() failed: INVALID PARAMETER INPUT", e);
                Slog.d("RestrictionPolicy", "isLockScreenViewAllowed : " + z);
                return z;
            } catch (Exception e2) {
                e = e2;
                Log.e("RestrictionPolicy", "isLockScreenViewAllowed() failed: unexpected exception", e);
                Slog.d("RestrictionPolicy", "isLockScreenViewAllowed : " + z);
                return z;
            }
        }
        try {
            Iterator it = this.mEdmStorageProvider.getIntList("RESTRICTION", "allowLockScreenViews").iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Integer num = (Integer) it.next();
                if (num.intValue() >= 0 && i != (num.intValue() & i)) {
                    break;
                }
            }
        } catch (InvalidParameterException e3) {
            e = e3;
            z = true;
            Log.d("RestrictionPolicy", "isLockScreenViewAllowed() failed: INVALID PARAMETER INPUT", e);
            Slog.d("RestrictionPolicy", "isLockScreenViewAllowed : " + z);
            return z;
        } catch (Exception e4) {
            e = e4;
            z = true;
            Log.e("RestrictionPolicy", "isLockScreenViewAllowed() failed: unexpected exception", e);
            Slog.d("RestrictionPolicy", "isLockScreenViewAllowed : " + z);
            return z;
        }
        Slog.d("RestrictionPolicy", "isLockScreenViewAllowed : " + z);
        return z;
    }

    public final int getUserIdByPackageNameOrUid(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                int currentUser = ActivityManager.getCurrentUser();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return currentUser;
            }
        }
        return Utils.getCallingOrCurrentUserId(contextInfo);
    }

    public boolean setLockScreenState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        if (getPersonaManagerAdapter().isValidKnoxId(userId)) {
            return false;
        }
        Slog.d("RestrictionPolicy", " setLockScreenState : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "lockScreenEnabled", z);
        this.mRestrictionCache.update("lockScreenEnabled", 8388608L, true, userId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, z, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has changed lock screen state to enabled" : "Admin %d has changed lock screen state to disabled", Integer.valueOf(enforceRestrictionPermission.mCallerUid)), userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(8388608L, true, Utils.getCallingOrCurrentUserId(contextInfo));
        Slog.d("RestrictionPolicy", "isLockScreenEnabled : " + extract);
        return extract;
    }

    public boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean isFirmwareRecoveryAllowedFromDB = isFirmwareRecoveryAllowedFromDB(enforceOwnerOnlyAndRestrictionPermission);
        Slog.d("RestrictionPolicy", "allowFirmwareRecovery :oldState - " + isFirmwareRecoveryAllowedFromDB + ", allow - " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "firmwarerecoveryallowed", z);
        this.mRestrictionCache.update("firmwarerecoveryallowed", 16777216L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            if (isFirmwareRecoveryAllowedFromDB == isFirmwareRecoveryAllowedFromDB(enforceOwnerOnlyAndRestrictionPermission)) {
                Slog.d("RestrictionPolicy", "allowFirmwareRecovery : do not need to update");
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!writeData(7, z)) {
                    Slog.d("RestrictionPolicy", "allowFirmwareRecovery : write fail");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Slog.d("RestrictionPolicy", "allowFirmwareRecovery : " + putBoolean);
        return putBoolean;
    }

    public boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) {
        boolean isFirmwareRecoveryAllowedFromDB = isFirmwareRecoveryAllowedFromDB(contextInfo);
        boolean readData = readData(7);
        Slog.d("RestrictionPolicy", "isFirmwareRecoveryAllowed(): ret -  " + isFirmwareRecoveryAllowedFromDB + " param - " + readData);
        if (readData != isFirmwareRecoveryAllowedFromDB) {
            Slog.d("RestrictionPolicy", "isFirmwareRecoveryAllowed(): need to sync DB(" + isFirmwareRecoveryAllowedFromDB + ") and param");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                writeData(7, isFirmwareRecoveryAllowedFromDB);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return isFirmwareRecoveryAllowedFromDB;
    }

    public final boolean isFirmwareRecoveryAllowedFromDB(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(16777216L, true, 0);
    }

    public final boolean readData(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        byte[] readParamData = readParamData();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        boolean z = true;
        if (readParamData == null) {
            Slog.d("RestrictionPolicy", "readParamData : fail to read");
            return true;
        }
        Slog.d("RestrictionPolicy", "readParamData : " + Arrays.toString(readParamData));
        if (readParamData[30] == 2 && readParamData[31] == 6 && readParamData[i] == 8) {
            z = false;
        }
        Slog.d("RestrictionPolicy", "readParamData : index - " + i + " ret - " + z);
        return z;
    }

    public static void enforceSystemUser() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 5250 && Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
    }

    public static boolean writeData(int i, boolean z) {
        enforceSystemUser();
        byte[] bArr = new byte[32];
        new SecureRandom().nextBytes(bArr);
        if (!z) {
            bArr[i] = 8;
            bArr[30] = 2;
            bArr[31] = 6;
        } else {
            bArr[i] = 7;
            bArr[30] = 1;
            bArr[31] = 5;
        }
        Slog.d("RestrictionPolicy", "writeData : index - " + i + " value - " + Arrays.toString(bArr));
        return writeParamData(bArr);
    }

    public boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean resetDeveloperModeOptions = !z ? new DeveloperModeSettings(this.mContext).resetDeveloperModeOptions() : true;
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowDeveloperMode", z);
        this.mRestrictionCache.update("allowDeveloperMode", 64L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed developer mode." : "Admin %d has disallowed developer mode.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        return putBoolean && resetDeveloperModeOptions;
    }

    public boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(64L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17042448);
        }
        Slog.d("RestrictionPolicy", "isDeveloperModeAllowed : " + extract);
        return extract;
    }

    public boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Log.i("RestrictionPolicy", "allowAirplaneMode : allow = " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowAirplaneMode", z);
        this.mRestrictionCache.update("allowAirplaneMode", 128L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            boolean isAirplaneModeAllowed = isAirplaneModeAllowed(false);
            if (!isAirplaneModeAllowed) {
                turnOffAirPlaneMode();
            }
            setAirplaneModeAllowedSystemUI(0, isAirplaneModeAllowed);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed airplane mode." : "Admin %d has disallowed airplane mode.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Slog.d("RestrictionPolicy", "allowAirplaneMode : " + putBoolean);
        return putBoolean;
    }

    public boolean isAirplaneModeAllowed(boolean z) {
        boolean extract = this.mRestrictionCache.extract(128L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.bugreport_option_full_summary);
        }
        Slog.d("RestrictionPolicy", "isAirplaneModeAllowed : " + extract);
        return extract;
    }

    public final void turnOffAirPlaneMode() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((ConnectivityManager) this.mContext.getSystemService("connectivity")).setAirplaneMode(false);
            } catch (Exception unused) {
                Log.w("RestrictionPolicy", "turnOffAirPlaneMode() failed. ");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceAdvancedRestrictionPermission = enforceAdvancedRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceAdvancedRestrictionPermission.mCallerUid);
        Slog.d("RestrictionPolicy", String.format("allowIntelligenceOnlineProcessing(%s) : %b", Integer.valueOf(userId), Boolean.valueOf(z)));
        if ((isIntelligenceOnlineProcessingAllowed(enforceAdvancedRestrictionPermission) && !z && !turnOffOnlineProcessing(userId)) || !this.mEdmStorageProvider.putBoolean(enforceAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "allowIntelligenceOnlineProcessing", z)) {
            return false;
        }
        this.mRestrictionCache.update("allowIntelligenceOnlineProcessing", 35184372088832L, true, UserHandle.getUserId(enforceAdvancedRestrictionPermission.mCallerUid), Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return true;
    }

    public boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) {
        return isIntelligenceOnlineProcessingAllowed(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isIntelligenceOnlineProcessingAllowed(int i) {
        boolean extract = this.mRestrictionCache.extract(35184372088832L, true, i);
        Slog.d("RestrictionPolicy", "isIntelligenceOnlineProcessingAllowed : " + extract);
        return extract;
    }

    public boolean showToastIfIntelligenceOnlineProcessingDisallowed(int i) {
        List intelligenceOnlineProcessingAdminsListAsUser = getIntelligenceOnlineProcessingAdminsListAsUser(i);
        if (intelligenceOnlineProcessingAdminsListAsUser.isEmpty()) {
            return false;
        }
        if (intelligenceOnlineProcessingAdminsListAsUser.size() == 1) {
            RestrictionToastManager.show(String.format(this.mContext.getString(17042332), intelligenceOnlineProcessingAdminsListAsUser.get(0)));
        } else {
            RestrictionToastManager.show(17042586);
        }
        return true;
    }

    public final List getIntelligenceOnlineProcessingAdminsListAsUser(int i) {
        if (isIntelligenceOnlineProcessingAllowed(i)) {
            return Collections.emptyList();
        }
        List adminAppLabelListAsUserForMask = this.mRestrictionCache.getAdminAppLabelListAsUserForMask(35184372088832L, i);
        Slog.d("RestrictionPolicy", "getIntelligenceOnlineProcessingAdminsListAsUser: " + adminAppLabelListAsUserForMask);
        return adminAppLabelListAsUserForMask;
    }

    public boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        if (!z && isGoogleAccountsAutoSyncAllowed(enforceRestrictionPermission)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Account[] accountsByTypeAsUser = AccountManager.get(this.mContext).getAccountsByTypeAsUser("com.google", new UserHandle(userId));
            if (accountsByTypeAsUser.length != 0) {
                IContentService contentService = ContentResolver.getContentService();
                try {
                    SyncAdapterType[] syncAdapterTypesAsUser = contentService.getSyncAdapterTypesAsUser(userId);
                    int length = accountsByTypeAsUser.length;
                    for (int i = 0; i < length; i++) {
                        Account account = accountsByTypeAsUser[i];
                        int length2 = syncAdapterTypesAsUser.length;
                        int i2 = 0;
                        while (i2 < length2) {
                            SyncAdapterType syncAdapterType = syncAdapterTypesAsUser[i2];
                            Account[] accountArr = accountsByTypeAsUser;
                            if ("com.google".equals(syncAdapterType.accountType) && contentService.getSyncAutomaticallyAsUser(account, syncAdapterType.authority, userId)) {
                                contentService.setSyncAutomaticallyAsUser(account, syncAdapterType.authority, false, userId);
                            }
                            i2++;
                            accountsByTypeAsUser = accountArr;
                        }
                    }
                } catch (RemoteException e) {
                    Log.w("RestrictionPolicy", "Failed to get content service " + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowGoogleAccountsAutoSync", z);
        if (putBoolean) {
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has enabled Autosync of Google account." : "Admin %d has disabled Autosync of Google account.", Integer.valueOf(enforceRestrictionPermission.mCallerUid)), userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            }
        }
        this.mRestrictionCache.update("allowGoogleAccountsAutoSync", 33554432L, true, userId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) {
        return isGoogleAccountsAutoSyncAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        return this.mRestrictionCache.extract(33554432L, true, i);
    }

    public boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", " allowFirmwareAutoUpdate : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "allowFirmwareAutoUpdate", z);
        this.mRestrictionCache.update("allowFirmwareAutoUpdate", 67108864L, false, 0, Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putInt(this.mContext.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", isFirmwareAutoUpdateAllowed(enforceOwnerOnlyAndAdvancedRestrictionPermission, false) ? 1 : 0);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean;
    }

    public boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(67108864L, false, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17042436);
        }
        Slog.d("RestrictionPolicy", "isFirmwareAutoUpdateAllowed : " + extract);
        return extract;
    }

    public boolean allowActivationLock(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowActivationLock", z);
        this.mRestrictionCache.update("allowActivationLock", 134217728L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(134217728L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17042432);
        }
        Slog.d("RestrictionPolicy", "isActivationLockAllowed : " + extract);
        return extract;
    }

    public boolean setHeadphoneState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean z2 = false;
        boolean isHeadphoneEnabled = isHeadphoneEnabled(enforceOwnerOnlyAndRestrictionPermission, false);
        try {
            Slog.d("RestrictionPolicy", "setHeadphoneState : " + z + " old state : " + isHeadphoneEnabled + " WiredHeadsetOn : " + this.mAudioManager.isWiredHeadsetOn());
            if (z) {
                if (this.mAudioManager.isWiredHeadsetOn() && !isHeadphoneEnabled) {
                    this.mAudioManager.setWiredDeviceConnectionState(4, 1, "", "h2w");
                    this.mAudioManager.setWiredDeviceConnectionState(8, 1, "", "h2w");
                }
            } else if (this.mAudioManager.isWiredHeadsetOn()) {
                this.mAudioManager.setWiredDeviceConnectionState(4, 0, "", "h2w");
                this.mAudioManager.setWiredDeviceConnectionState(8, 0, "", "h2w");
            }
            z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "setHeadphoneState", z);
            this.mRestrictionCache.update("setHeadphoneState", 268435456L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            return z2;
        } catch (Exception unused) {
            Log.w("RestrictionPolicy", "RestrictionPolicy.setHeadphoneState() exception : ");
            return z2;
        }
    }

    public boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(268435456L, true, 0);
        if (z && !extract) {
            RestrictionToastManager.show(17042437);
        }
        Slog.d("RestrictionPolicy", "isHeadphoneEnabled : " + extract);
        return extract;
    }

    public boolean allowSDCardMove(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        if (getPersonaManagerAdapter().isValidKnoxId(callingOrCurrentUserId)) {
            return false;
        }
        Slog.d("RestrictionPolicy", " allowSDCardMove : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "allowSDCardMove", z);
        this.mRestrictionCache.update("allowSDCardMove", 536870912L, true, callingOrCurrentUserId, Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(536870912L, true, Utils.getCallingOrCurrentUserId(contextInfo));
        if (z && !extract) {
            RestrictionToastManager.show(17042439);
        }
        Slog.d("RestrictionPolicy", "isSDCardMoveAllowed : " + extract);
        return extract;
    }

    public boolean allowFastEncryption(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowFastEncryption", z);
        Slog.d("RestrictionPolicy", " allowFastEncryption : " + z);
        this.mRestrictionCache.update("allowFastEncryption", 16L, false, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(16L, false, 0);
        Slog.d("RestrictionPolicy", "isFastEncryptionAllowed : " + extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042435);
        }
        return extract;
    }

    public boolean setCCMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndAdvancedRestrictionPermission);
        Log.d("RestrictionPolicy", "setCCMode() : " + z + " and current MDM state:" + isCCModeEnabled(enforceOwnerOnlyAndAdvancedRestrictionPermission, false));
        if (isUCMKeyguardEnabled()) {
            Log.d("RestrictionPolicy", "UCM Keyguard is enabled, so cannot set CC mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        MdfPolicy mdfPolicy = MdfPolicy.getInstance(this.mContext);
        int i = -1;
        try {
            try {
                if (mdfPolicy != null) {
                    i = mdfPolicy.enableCCMode(z);
                } else {
                    Log.d("RestrictionPolicy", "setCCMode() : securtyManagerService is not available.");
                }
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setCCMode(): failed to set CCMode with exception", e);
            }
            Slog.d("RestrictionPolicy", "setCCMode() result : " + i);
            if (i == 0) {
                z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "setCCMode", z);
                this.mRestrictionCache.update("setCCMode", 1073741824L, false, 0, Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
                if (!z2) {
                    Log.d("RestrictionPolicy", "setCCMode() : failed to update enterprise db. enable = " + z + ", state=" + isCCModeEnabled(enforceOwnerOnlyAndAdvancedRestrictionPermission, false));
                }
            } else {
                Log.d("RestrictionPolicy", "setCCMode() : failed. securtyManagerService has failed to enforce CCMode. result = " + i);
                z2 = false;
            }
            int cCModeState = getCCModeState(enforceOwnerOnlyAndAdvancedRestrictionPermission);
            if (cCModeState == 2 || cCModeState == 4) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has requested to enable CCMode." : "Admin %d has requested to disable CCMode.", Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid)), callingOrCurrentUserId);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return z2;
        } catch (Throwable th) {
            throw th;
        }
    }

    public boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) {
        boolean z2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            Log.d("RestrictionPolicy", "setCCModeOnlyForCallerSystem() caller uid : " + callingUid + " caller pid : " + callingPid + " Process.mypid() : " + Process.myPid());
            throw new SecurityException("Caller is not a system process..");
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d("RestrictionPolicy", "setCCMode() : " + z + " and current MDM state:" + isCCModeEnabled(contextInfo, false));
        if (isUCMKeyguardEnabled()) {
            Log.d("RestrictionPolicy", "UCM Keyguard is enabled, so cannot set CC mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        MdfPolicy mdfPolicy = MdfPolicy.getInstance(this.mContext);
        int i = -1;
        try {
            try {
                if (mdfPolicy != null) {
                    i = mdfPolicy.enableCCMode(z);
                } else {
                    Log.d("RestrictionPolicy", "setCCMode() : MdfPolicy is not available.");
                }
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setCCMode(): failed to set CCMode with exception", e);
            }
            Slog.d("RestrictionPolicy", "setCCMode() result : " + i);
            if (i == 0) {
                z2 = this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "RESTRICTION", "setCCMode", z);
                this.mRestrictionCache.update("setCCMode", 1073741824L, false, 0, Integer.valueOf(contextInfo.mCallerUid), Boolean.valueOf(z));
                if (!z2) {
                    Log.d("RestrictionPolicy", "setCCMode() : failed to update enterprise db. enable = " + z + ", state=" + isCCModeEnabled(contextInfo, false));
                }
            } else {
                Log.d("RestrictionPolicy", "setCCMode() : failed. MdfPolicy has failed to enforce CCMode. result = " + i);
                z2 = false;
            }
            int cCModeState = getCCModeState(contextInfo);
            if (cCModeState == 2 || cCModeState == 4) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has requested to enable CCMode." : "Admin %d has requested to disable CCMode.", Integer.valueOf(contextInfo.mCallerUid)), callingOrCurrentUserId);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return z2;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean isUCMKeyguardEnabled() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("RestrictionPolicy", "Failed to get UCM Service");
            return false;
        }
        try {
            String keyguardStorageForCurrentUser = asInterface.getKeyguardStorageForCurrentUser(0);
            if (keyguardStorageForCurrentUser != null && !keyguardStorageForCurrentUser.isEmpty() && !keyguardStorageForCurrentUser.equalsIgnoreCase("none")) {
                Log.d("RestrictionPolicy", "UCM Keyguard is enabled");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(1073741824L, false, 0);
        boolean isMdfEnforced = MdfUtils.isMdfEnforced();
        Slog.d("RestrictionPolicy", " isCCModeEnabled:" + SystemProperties.get("security.mdf") + "    mdm ret1 :" + extract + "   ret :" + isMdfEnforced);
        return isMdfEnforced;
    }

    public boolean isCCModeSupported(ContextInfo contextInfo, boolean z) {
        String str = SystemProperties.get("ro.security.mdf.ux");
        boolean z2 = str != null && "Enabled".equals(str);
        Slog.d("RestrictionPolicy", "isCCModeSupported : " + z2);
        if (z && !z2) {
            RestrictionToastManager.show(17042447);
        }
        return z2;
    }

    public boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "enableODETrustedBootVerification  : " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "ODETrustedBootVerification", z);
        this.mRestrictionCache.update("ODETrustedBootVerification", 2147483648L, false, 0, Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(2147483648L, false, 0);
        Slog.d("RestrictionPolicy", "isODETruestedBootVerfiicationEnabled : " + extract);
        return extract;
    }

    public boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOldAdvancedRestrictionOrNewApplicationPermission = enforceOldAdvancedRestrictionOrNewApplicationPermission(contextInfo);
        boolean z2 = false;
        boolean isNewAdminInstallationEnabled = isNewAdminInstallationEnabled(enforceOldAdvancedRestrictionOrNewApplicationPermission, false);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOldAdvancedRestrictionOrNewApplicationPermission);
        if (isNewAdminInstallationEnabled) {
            Slog.d("RestrictionPolicy", " preventAdminInstallation : " + z);
            if (!isAdminEnforcementAllowed(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, callingOrCurrentUserId)) {
                return false;
            }
            z2 = this.mEdmStorageProvider.putBoolean(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, "RESTRICTION", "preventAdminInstallation", z);
        } else {
            int uidForAdminInstallation = getUidForAdminInstallation(callingOrCurrentUserId);
            int i = enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid;
            if (i == uidForAdminInstallation) {
                z2 = this.mEdmStorageProvider.putBoolean(i, "RESTRICTION", "preventAdminInstallation", z);
            }
        }
        this.mRestrictionCache.update("preventAdminInstallation", 4294967296L, false, callingOrCurrentUserId, Integer.valueOf(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid), Boolean.valueOf(z));
        return z2;
    }

    public boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) {
        return isNewAdminInstallationEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) {
        if (i == -1) {
            Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
            boolean z2 = true;
            while (it.hasNext() && (z2 = isNewAdminInstallationEnabledInternal(((UserInfo) it.next()).id, z))) {
            }
            return z2;
        }
        return isNewAdminInstallationEnabledInternal(i, z);
    }

    public void showRestrictionToast(String str) {
        RestrictionToastManager.show(str);
    }

    public final boolean isNewAdminInstallationEnabledInternal(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(4294967296L, false, i);
        Slog.d("RestrictionPolicy", "isNewAdminInstallationEnabledInternal : " + extract);
        if (z && extract) {
            RestrictionToastManager.show(17042444);
        }
        return !extract;
    }

    public boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOldAdvancedRestrictionOrNewApplicationPermission = enforceOldAdvancedRestrictionOrNewApplicationPermission(contextInfo);
        boolean z2 = false;
        boolean isNewAdminActivationEnabled = isNewAdminActivationEnabled(enforceOldAdvancedRestrictionOrNewApplicationPermission, false);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOldAdvancedRestrictionOrNewApplicationPermission);
        if (isNewAdminActivationEnabled) {
            Slog.d("RestrictionPolicy", " preventAdminActivation : " + z);
            try {
                if (!isAdminEnforcementAllowed(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, callingOrCurrentUserId)) {
                    return false;
                }
                z2 = this.mEdmStorageProvider.putBoolean(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, "RESTRICTION", "preventAdminActivation", z);
            } catch (Exception unused) {
                Log.w("RestrictionPolicy", "RestrictionPolicy.preventAdminActivation exception : ");
            }
        } else {
            int uidForAdminActivation = getUidForAdminActivation(callingOrCurrentUserId);
            int i = enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid;
            if (i == uidForAdminActivation) {
                z2 = this.mEdmStorageProvider.putBoolean(i, "RESTRICTION", "preventAdminActivation", z);
            }
        }
        this.mRestrictionCache.update("preventAdminActivation", 8589934592L, false, callingOrCurrentUserId, Integer.valueOf(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid), Boolean.valueOf(z));
        return z2;
    }

    public final int getUidForAdminActivation(int i) {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser("RESTRICTION", "adminUid", i).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            try {
                if (this.mEdmStorageProvider.getBoolean(num.intValue(), "RESTRICTION", "preventAdminActivation")) {
                    return num.intValue();
                }
            } catch (SettingNotFoundException e) {
                Slog.v("RestrictionPolicy", "Admin not found on database ", e);
            }
        }
        return -1;
    }

    public final int getUidForAdminInstallation(int i) {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser("RESTRICTION", "adminUid", i).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            try {
                if (this.mEdmStorageProvider.getBoolean(num.intValue(), "RESTRICTION", "preventAdminInstallation")) {
                    return num.intValue();
                }
            } catch (SettingNotFoundException e) {
                Slog.v("RestrictionPolicy", "Admin not found on database ", e);
            }
        }
        return -1;
    }

    public boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) {
        return isNewAdminActivationEnabled(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public boolean isNewAdminActivationEnabled(int i, boolean z) {
        if (i == -1) {
            Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
            boolean z2 = true;
            while (it.hasNext() && (z2 = isNewAdminActivationEnabledInternal(((UserInfo) it.next()).id, z))) {
            }
            return z2;
        }
        return isNewAdminActivationEnabledInternal(i, z);
    }

    public final boolean isNewAdminActivationEnabledInternal(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(8589934592L, false, i);
        if (z && extract) {
            RestrictionToastManager.show(17042443);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("isNewAdminActivationEnabledInternal : ");
        sb.append(!extract);
        Slog.d("RestrictionPolicy", sb.toString());
        return !extract;
    }

    public final boolean isNewAdminActivationEnabledInternal(int i) {
        return isNewAdminActivationEnabledInternal(i, false);
    }

    public boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOldAdvancedRestrictionOrNewApplicationPermission = enforceOldAdvancedRestrictionOrNewApplicationPermission(contextInfo);
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null && str.length() > 0) {
                    sb.append(str + ",");
                }
            }
        }
        return this.mEdmStorageProvider.putString(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, "RESTRICTION", "preventAdminActivationWhiteList", sb.toString());
    }

    public boolean isAdminEnforcementAllowed(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String packageNameForUid = getPackageNameForUid(i);
        try {
            try {
                if (getService() != null) {
                    for (EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo : this.mEdmService.getActiveAdminsInfo(i2)) {
                        Slog.d("RestrictionPolicy", " preventAdminActivation packageName : " + enterpriseDeviceAdminInfo.getPackageName());
                        if (!enterpriseDeviceAdminInfo.getPackageName().equals(packageNameForUid) && !isPackageOnExclusionList(i, enterpriseDeviceAdminInfo.getPackageName()) && !hasKnoxInternalExceptionPermission(enterpriseDeviceAdminInfo.getPackageName(), i2)) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                Log.w("RestrictionPolicy", "RestrictionPolicy.preventAdminInstallation exception : ", e);
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasKnoxInternalExceptionPermission(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isPackageOnExclusionList(int i, String str) {
        if (Arrays.asList(excludedAdminList).contains(str)) {
            return true;
        }
        String string = this.mEdmStorageProvider.getString(i, "RESTRICTION", "preventAdminActivationWhiteList");
        if (!TextUtils.isEmpty(string)) {
            for (String str2 : string.split(",")) {
                if (!TextUtils.isEmpty(str2) && str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
        return addNewAdminActivationAppWhiteList(contextInfo, null);
    }

    public List getNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceOldAdvancedRestrictionOrNewApplicationPermission = enforceOldAdvancedRestrictionOrNewApplicationPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        String string = this.mEdmStorageProvider.getString(enforceOldAdvancedRestrictionOrNewApplicationPermission.mCallerUid, "RESTRICTION", "preventAdminActivationWhiteList");
        if (TextUtils.isEmpty(string)) {
            arrayList.add("");
        } else {
            arrayList.addAll(Arrays.asList(string.split(",")));
        }
        return arrayList;
    }

    public final String getPackageNameForUid(int i) {
        String string;
        if (i == 1000 || (string = this.mEdmStorageProvider.getString(i, "ADMIN_INFO", "adminName")) == null) {
            return null;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
        return unflattenFromString == null ? string : unflattenFromString.getPackageName();
    }

    public boolean checkAdminActivationEnabled(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            if (isNewAdminActivationEnabledInternal(i) || checkPackageWhiteList(i, str) || packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0) {
                return true;
            }
            RestrictionToastManager.show(17042443);
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPackageWhiteList(int i, String str) {
        return isPackageOnExclusionList(getUidForAdminActivation(i), str);
    }

    public boolean checkPackageSource(int i, String str) {
        String packageNameForUid;
        return (str == null || (packageNameForUid = getPackageNameForUid(getUidForAdminInstallation(i))) == null || !packageNameForUid.equals(str)) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean manageEFSFile(boolean z, String str) {
        boolean createEFSFile;
        FileOutputStream fileOutputStream;
        Log.d("RestrictionPolicy", "manageEFSFile");
        File file = new File("/efs/MDM");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!file.isDirectory()) {
            if (file.mkdirs()) {
                boolean readable = file.setReadable(true, false);
                boolean writable = file.setWritable(true, true);
                FileOutputStream fileOutputStream2 = null;
                try {
                    try {
                        try {
                            fileOutputStream = new FileOutputStream(file);
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (IOException unused) {
                }
                try {
                    fileOutputStream.getFD().sync();
                    fileOutputStream.close();
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream2 = fileOutputStream;
                    Log.e("RestrictionPolicy", "Exception : ", e);
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    Log.d("RestrictionPolicy", "manageEFSFile: setReadable - " + readable + " setWritable - " + writable);
                    File file2 = new File(str);
                    if (!z) {
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return createEFSFile;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
                Log.d("RestrictionPolicy", "manageEFSFile: setReadable - " + readable + " setWritable - " + writable);
            } else {
                Log.d("RestrictionPolicy", "manageEFSFile : mkdirs fail");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } else {
            file.setWritable(false, false);
            file.setWritable(true, true);
        }
        File file22 = new File(str);
        if (!z) {
            createEFSFile = deleteEFSFile(file22);
        } else {
            createEFSFile = createEFSFile(file22);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return createEFSFile;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.String] */
    public final boolean createEFSFile(File file) {
        boolean readable;
        boolean writable;
        FileOutputStream fileOutputStream;
        Log.d("RestrictionPolicy", "createEFSFile");
        boolean z = false;
        if (file == null) {
            Log.d("RestrictionPolicy", "createEFSFile : efsFile fail");
            return false;
        }
        if (file.exists()) {
            Log.d("RestrictionPolicy", "createEFSFile : efsFile exist");
            return true;
        }
        FileOutputStream fileOutputStream2 = null;
        fileOutputStream2 = null;
        try {
            try {
                try {
                    z = file.createNewFile();
                    readable = file.setReadable(true, true);
                    writable = file.setWritable(true, true);
                    fileOutputStream = new FileOutputStream(file);
                } catch (IOException unused) {
                }
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.getFD().sync();
            StringBuilder sb = new StringBuilder();
            sb.append("createEFSFile: setReadable - ");
            sb.append(readable);
            ?? r1 = " setWritable - ";
            sb.append(" setWritable - ");
            sb.append(writable);
            Log.d("RestrictionPolicy", sb.toString());
            fileOutputStream.close();
            fileOutputStream2 = r1;
        } catch (IOException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            Log.e("RestrictionPolicy", "Exception : ", e);
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
                fileOutputStream2 = fileOutputStream2;
            }
            Log.d("RestrictionPolicy", "createEFSFile : create " + z);
            return z;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
        Log.d("RestrictionPolicy", "createEFSFile : create " + z);
        return z;
    }

    public final boolean deleteEFSFile(File file) {
        Log.d("RestrictionPolicy", "createEFSFile");
        if (file == null) {
            Log.d("RestrictionPolicy", "deleteEFSFile : efsFile fail");
            return false;
        }
        if (!file.exists()) {
            Log.d("RestrictionPolicy", "deleteEFSFile : efsFile exist");
            return true;
        }
        boolean delete = file.delete();
        Log.d("RestrictionPolicy", "deleteEFSFile : delete " + delete);
        return delete;
    }

    public final boolean isExistEFSFile(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean exists = new File(str).exists();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.d("RestrictionPolicy", "isExistEFSFile : " + exists);
            return exists;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isSmartClipModeAllowed(ContextInfo contextInfo) {
        return isSmartClipModeAllowedAsUser(false, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isSmartClipModeAllowedInternal(boolean z) {
        return isSmartClipModeAllowedAsUser(z, getTopActivityUserId());
    }

    public final boolean isSmartClipModeAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(72057594037927936L, true, i);
        if (z && !extract) {
            RestrictionToastManager.show(17042871);
        }
        Slog.d("RestrictionPolicy", "isSmartClipModeAllowed : " + extract);
        return extract;
    }

    public boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "smartClipAllowed", z);
        this.mRestrictionCache.update("smartClipAllowed", 72057594037927936L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        return putBoolean;
    }

    public int getCCModeState(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(1073741824L, false, 0);
        int updateMdfStatus = isCCModeSupported(contextInfo, false) ? MdfUtils.updateMdfStatus() : -1;
        Slog.d("RestrictionPolicy", "getCCModeState : mdm ret = " + extract + ", MdfUtils ret = : " + updateMdfStatus);
        return updateMdfStatus;
    }

    public boolean isScreenPinningAllowed(ContextInfo contextInfo) {
        return isScreenPinningAllowedAsUser(false, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isScreenPinningAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(144115188075855872L, true, i);
        Slog.d("RestrictionPolicy", "isScreenPinningAllowed : " + extract);
        return extract;
    }

    public boolean allowScreenPinning(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        if (getPersonaManagerAdapter().isValidKnoxId(userId)) {
            return false;
        }
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "lock_to_app_enabled", 0, userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceRestrictionPermission.mCallerUid, "RESTRICTION", "screenPinningAllowed", z);
        this.mRestrictionCache.update("screenPinningAllowed", 144115188075855872L, true, UserHandle.getUserId(enforceRestrictionPermission.mCallerUid), Integer.valueOf(enforceRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        if (putBoolean && !z && ((ActivityManager) this.mContext.getSystemService("activity")).isInLockTaskMode()) {
            try {
                ActivityTaskManager.getService().stopSystemLockTaskMode();
            } catch (RemoteException unused) {
                Log.e("RestrictionPolicy", "failed taking activity manager service");
            } catch (SecurityException unused2) {
                Log.e("RestrictionPolicy", "trying to un pin app from different user, just ignore");
            }
        }
        return putBoolean;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) {
        String str2;
        int i;
        int statusSelectiveFota;
        boolean clearSelectiveFota;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (bundle != null) {
            str2 = bundle.getString("update_fota_corpid");
            if (str2 == null || str2.isEmpty()) {
                Log.d("RestrictionPolicy", "setAllowedFOTAVersion : ERROR_UPDATE_FOTA_INVALID_PARAMETER(corpID = null) ");
            } else {
                Slog.d("RestrictionPolicy", "setAllowedFOTAVersion : corpID =  " + str2);
                if (str != null) {
                    Log.d("RestrictionPolicy", "setAllowedFOTAVersion : version =  " + str);
                    int indexOf = str.indexOf("/");
                    int indexOf2 = str.indexOf("/", indexOf + 1);
                    if (indexOf == -1 || indexOf2 == -1) {
                        Log.d("RestrictionPolicy", "setAllowedFOTAVersion : ERROR_UPDATE_FOTA_INVALID_PARAMETER(version) ");
                    }
                }
                i = 0;
                Slog.d("RestrictionPolicy", "setAllowedFOTAVersion : caller =  " + enforceOwnerOnlyAndRestrictionPermission.mCallerUid);
                statusSelectiveFota = getStatusSelectiveFota(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, str2, str);
                if (statusSelectiveFota != 1 && ((str == null || str.isEmpty()) && (str2 == null || str2.isEmpty()))) {
                    if (z) {
                        sendSeletiveFotaResult(0);
                    }
                    return true;
                }
                int i2 = 4;
                if (statusSelectiveFota != 2 || statusSelectiveFota == 3) {
                    if ((str != null || str.isEmpty()) && (str2 == null || str2.isEmpty())) {
                        i = 0;
                    }
                } else if (statusSelectiveFota == 4) {
                    i = 2;
                }
                if (i != 0) {
                    if ((str == null || str.isEmpty()) && bundle == null) {
                        clearSelectiveFota = clearSelectiveFota();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
                        contentValues.put("version", str);
                        contentValues.put("corpid", str2);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
                        clearSelectiveFota = this.mEdmStorageProvider.put("SelectiveFotaTable", contentValues, contentValues2);
                        Log.d("RestrictionPolicy", "setAllowedFOTAVersion : result = " + clearSelectiveFota);
                    }
                    if (clearSelectiveFota) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        Log.d("RestrictionPolicy", "setAllowedFOTAVersion : sendBroadcast : sec.fota.intent.MDM_REGISTER");
                        String[] strArr = {"com.wssyncmldm", "com.samsung.utagent"};
                        for (int i3 = 0; i3 < 2; i3++) {
                            Intent intent = new Intent("sec.fota.intent.MDM_REGISTER");
                            intent.setPackage(strArr[i3]);
                            this.mContext.sendBroadcast(intent);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } else {
                    i2 = i;
                }
                if (z) {
                    sendSeletiveFotaResult(i2);
                }
                return true;
            }
        } else {
            Log.d("RestrictionPolicy", "setAllowedFOTAVersion : ERROR_UPDATE_FOTA_INVALID_PARAMETER(bundle = null) ");
            str2 = null;
        }
        i = 3;
        Slog.d("RestrictionPolicy", "setAllowedFOTAVersion : caller =  " + enforceOwnerOnlyAndRestrictionPermission.mCallerUid);
        statusSelectiveFota = getStatusSelectiveFota(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, str2, str);
        if (statusSelectiveFota != 1) {
        }
        int i22 = 4;
        if (statusSelectiveFota != 2) {
        }
        if (str != null) {
        }
        i = 0;
        if (i != 0) {
        }
        if (z) {
        }
        return true;
    }

    public List getAllowedFOTAInfo(ContextInfo contextInfo) {
        Log.d("RestrictionPolicy", "getAllowedFOTAInfo");
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", (Integer) 0);
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("SelectiveFotaTable", new String[]{"adminUid", "corpid", "version"}, contentValues);
        if (valuesList != null && !valuesList.isEmpty()) {
            for (ContentValues contentValues2 : valuesList) {
                if (contentValues2 != null && contentValues2.size() > 0) {
                    arrayList.add(contentValues2.getAsString("corpid"));
                    arrayList.add(contentValues2.getAsString("version"));
                    Slog.d("RestrictionPolicy", "getAllowedFOTAInfo : corpID = " + contentValues2.getAsString("corpid") + " version = " + contentValues2.getAsString("version"));
                }
            }
        } else {
            Log.d("RestrictionPolicy", "getAllowedFOTAInfo: cursor is null");
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public String getAllowedFOTAVersion(ContextInfo contextInfo) {
        List allowedFOTAInfo = getAllowedFOTAInfo(contextInfo);
        if (allowedFOTAInfo == null || allowedFOTAInfo.isEmpty()) {
            return null;
        }
        return (String) allowedFOTAInfo.get(1);
    }

    public final int getStatusSelectiveFota(int i, String str, String str2) {
        int i2;
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", (Integer) 0);
        List valuesList = this.mEdmStorageProvider.getValuesList("SelectiveFotaTable", new String[]{"adminUid", "corpid", "version"}, contentValues);
        if (valuesList != null && !valuesList.isEmpty()) {
            Iterator it = valuesList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i2 = 4;
                    break;
                }
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2 != null && contentValues2.size() > 0) {
                    Integer asInteger = contentValues2.getAsInteger("adminUid");
                    int intValue = asInteger != null ? asInteger.intValue() : -1;
                    Log.d("RestrictionPolicy", "getStatusSelectiveFota : enabled amdin = " + intValue);
                    if (i == intValue) {
                        Slog.d("RestrictionPolicy", "getStatusSelectiveFota : old corpID = " + contentValues2.getAsString("corpid"));
                        if (str == null || !str.equals(contentValues2.getAsString("corpid"))) {
                            i2 = 3;
                        } else {
                            Slog.d("RestrictionPolicy", "getStatusSelectiveFota : same corpID");
                            i2 = 2;
                        }
                    }
                }
            }
        } else {
            i2 = 1;
        }
        Log.d("RestrictionPolicy", "getStatusSelectiveFota : return( " + i2 + " )");
        return i2;
    }

    public final boolean clearSelectiveFota() {
        int delete = this.mEdmStorageProvider.delete("SelectiveFotaTable", null);
        Log.d("RestrictionPolicy", "clearSelectiveFota : return( " + delete + " )");
        return delete >= 0;
    }

    public boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) {
        boolean z = false;
        try {
        } catch (InvalidParameterException unused) {
            Slog.w("RestrictionPolicy", "isWearablePolicyEnabled() failed: INVALID PARAMETER INPUT");
        } catch (Exception unused2) {
            Slog.w("RestrictionPolicy", "isWearablePolicyEnabled() failed.");
        }
        if (!isValidDevice(i)) {
            throw new InvalidParameterException();
        }
        Iterator it = this.mEdmStorageProvider.getIntList("RESTRICTION", "wearablePolicyEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (i != (((Integer) it.next()).intValue() & i)) {
                z = true;
                break;
            }
        }
        Log.d("RestrictionPolicy", "isWearablePolicyEnabled() : " + z);
        return z;
    }

    public boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid);
        Slog.d("RestrictionPolicy", "enableGearPolicy() : userId = " + userId + ", enable = " + z);
        if (getPersonaManagerAdapter().isValidKnoxId(userId)) {
            Log.d("RestrictionPolicy", "enableGearPolicy() : this api doesn't support multi-user. userId = " + userId);
            return false;
        }
        if (!isValidDevice(i)) {
            Log.d("RestrictionPolicy", "enableGearPolicy() : this api called unsupport device. device = " + i);
            return false;
        }
        try {
            i2 = this.mEdmStorageProvider.getInt(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "wearablePolicyEnabled");
        } catch (SettingNotFoundException unused) {
            i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
        }
        if (!this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "wearablePolicyEnabled", true == z ? (~i) & i2 : i | i2)) {
            return false;
        }
        sendIntentGearManagerforUpdate(userId);
        return true;
    }

    public final void sendIntentGearManagerforUpdate(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.GEARPOLICY_ENABLE_INTERNAL").addFlags(16777216), new UserHandle(i));
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "sendIntentGearManagerforUpdate() fas failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendSeletiveFotaResult(int i) {
        Log.d("RestrictionPolicy", "sendSeletiveFotaResult: result = " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.UPDATE_FOTA_VERSION_RESULT");
        intent.putExtra("com.samsung.android.knox.intent.extra.UPDATE_FOTA_VERSION_STATUS", i);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT");
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void updateSystemUIMonitor(int i) {
        setSettingsChangeSystemUI(i, isSettingsChangesAllowedAsUser(false, i));
        setStatusBarExpansionSystemUI(i, isStatusBarExpansionAllowedAsUser(false, i));
        setAirplaneModeAllowedSystemUI(i, isAirplaneModeAllowed(false));
        setCellularDataAllowedSystemUI(i, isCellularDataAllowed(null));
        setWifiTetheringAllowedSystemUI(i, isWifiTetheringEnabled(null));
        setCameraAllowedSystemUI(i, isCameraEnabledAsUser(i));
        setFaceRecognitionEvenCameraBlockedAllowedSystemUI(i, isFaceRecognitionAllowedEvenCameraBlocked(null));
    }

    public final void setSettingsChangeSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setSettingsChangeAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setSettingsChangeSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setStatusBarExpansionSystemUI(int i, boolean z) {
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        Log.d("RestrictionPolicy", "setStatusBarExpansionSystemUI() package(UID) : " + packagesForUid[0] + "(" + Binder.getCallingUid() + ")");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setStatusBarExpansionAllowedAsUser(i, z, packagesForUid[0]);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setStatusBarExpansionSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setAirplaneModeAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setAirplaneModeAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setAirplaneModeAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean turnOffOnlineProcessing(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return Settings.System.putIntForUser(this.mContext.getContentResolver(), "prevent_online_processing", 1, i);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "turnOffOnlineProcessing() failed. ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setCellularDataAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setCellularDataAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setCellularDataAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setWifiTetheringAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setWifiTetheringAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setWifiTetheringAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setCameraAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setCameraAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setCameraAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setFaceRecognitionEvenCameraBlockedAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setFaceRecognitionEvenCameraBlockedAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setFaceRecognitionEvenCameraBlockedAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getCurrentPowerSavingMode() {
        int i = 0;
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "ultra_powersaving_mode", 0) != 0) {
            i = 2;
        } else if (z) {
            i = 1;
        }
        Log.d("RestrictionPolicy", "getCurrentPowerSavingMode : " + i);
        return i;
    }

    public boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowPowerSavingMode", z);
        this.mRestrictionCache.update("allowPowerSavingMode", 1152921504606846976L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
        Slog.d("RestrictionPolicy", "allowPowerSavingMode : " + z);
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (getCurrentPowerSavingMode() == 1) {
                        Slog.d("RestrictionPolicy", "allowPowerSavingMode : Power Saving Mode off");
                        Settings.System.putInt(this.mContext.getContentResolver(), KnoxCustomManagerService.POWER_SAVING_SWITCH, 0);
                        Settings.Global.putInt(this.mContext.getContentResolver(), "low_power", 0);
                    } else if (getCurrentPowerSavingMode() != 0) {
                        Slog.d("RestrictionPolicy", "allowPowerSavingMode : send intent to PMS");
                        Intent intent = new Intent("com.samsung.intent.action.EMERGENCY_START_SERVICE_BY_ORDER");
                        intent.putExtra("enabled", false);
                        intent.putExtra("flag", 512);
                        intent.putExtra("skipdialog", true);
                        intent.addFlags(268435456);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT);
                    }
                } catch (Exception e) {
                    Log.e("RestrictionPolicy", "allowPowerSavingMode() failed. ", e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public boolean isPowerSavingModeAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(1152921504606846976L, true, 0);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Restriction Policy");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[Restriction Policy]");
        sb.append(System.lineSeparator());
        sb.append(this.mRestrictionCache.dump());
        sb.append("   UsbExceptionList: 0x" + Integer.toHexString(getUsbExceptionList()));
        sb.append(System.lineSeparator());
        printWriter.write(sb.toString());
    }

    public boolean allowDataSaving(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission = enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRestrictionPermission.mCallerUid, "RESTRICTION", "allowDataSaving", z);
        if (putBoolean) {
            this.mRestrictionCache.update("allowDataSaving", 576460752303423488L, true, 0, Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            applyBackgroundDataRestriction();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed data saving." : "Admin %d has disallowed data saving.", Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Slog.d("RestrictionPolicy", "allowDataSaver : " + putBoolean);
        return putBoolean;
    }

    public boolean isDataSavingAllowed() {
        boolean extract = this.mRestrictionCache.extract(576460752303423488L, true, 0);
        Slog.w("RestrictionPolicy", "isDataSavingAllowed : " + extract);
        return extract;
    }

    public boolean setUsbExceptionList(ContextInfo contextInfo, int i) {
        Log.d("RestrictionPolicy", "setUsbExceptionList. exception mask : 0x" + Integer.toHexString(i));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!((UsbManager) this.mContext.getSystemService("usb")).isSupportDexRestrict()) {
                Log.w("RestrictionPolicy", "isSupportDexRestrict is false");
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int i2 = enforceOwnerOnlyAndRestrictionPermission(contextInfo).mCallerUid;
            if (i > RestrictionPolicy.USBInterface.OFF.getValue()) {
                Log.w("RestrictionPolicy", "Exception mask is over max value. Max : 131071(USBInterface.OFF)");
                return false;
            }
            try {
                boolean updateUsbInterfaceExceptionMaskInDb = updateUsbInterfaceExceptionMaskInDb(i, i2);
                Log.d("RestrictionPolicy", "Usb Exception mask input DB : " + updateUsbInterfaceExceptionMaskInDb);
                setUsbExceptionListOnDriver(getUsbExceptionList());
                return updateUsbInterfaceExceptionMaskInDb;
            } catch (Exception e) {
                Log.w("RestrictionPolicy", "UsbInterface Exception mask insert fail", e);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateUsbInterfaceExceptionMaskInDb(int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i2));
        int count = this.mEdmStorageProvider.getCount("RESTRICTION", contentValues);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("UsbExceptionMask", Integer.valueOf(i));
        if (count > 0) {
            return this.mEdmStorageProvider.putValues("RESTRICTION", contentValues2, contentValues);
        }
        contentValues2.put("adminUid", Integer.valueOf(i2));
        return this.mEdmStorageProvider.putValuesNoUpdate("RESTRICTION", contentValues2);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setUsbExceptionListOnDriver(int i) {
        int restrictUsbHostInterface;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
            if (usbManager == null) {
                Log.e("RestrictionPolicy", "UsbManager is null");
                return -1;
            }
            if (!usbManager.isSupportDexRestrict()) {
                Log.w("RestrictionPolicy", "isSupportDexRestrict is false");
                return -1;
            }
            if (!isUsbHostStorageAllowed(null, false)) {
                Log.d("RestrictionPolicy", "isUsbHostStorageAllowed is false. pass set UsbInterface Exception");
                return -1;
            }
            if (i != -1 && i != RestrictionPolicy.USBInterface.OFF.getValue()) {
                if (i == RestrictionPolicy.USBInterface.ABL.getValue()) {
                    restrictUsbHostInterface = usbManager.restrictUsbHostInterface(true, RestrictionPolicy.USBInterface.ABL.toString());
                } else {
                    String restrictionList = getRestrictionList(i);
                    Log.d("RestrictionPolicy", "set UsbInterface Exception : " + restrictionList);
                    restrictUsbHostInterface = usbManager.restrictUsbHostInterface(true, restrictionList);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Usb Exception mask input USB Driver : ");
                sb.append(restrictUsbHostInterface == 0);
                Log.d("RestrictionPolicy", sb.toString());
                return restrictUsbHostInterface;
            }
            restrictUsbHostInterface = usbManager.restrictUsbHostInterface(false, RestrictionPolicy.USBInterface.OFF.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Usb Exception mask input USB Driver : ");
            sb2.append(restrictUsbHostInterface == 0);
            Log.d("RestrictionPolicy", sb2.toString());
            return restrictUsbHostInterface;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getRestrictionList(final int i) {
        ArrayList arrayList = new ArrayList(Arrays.asList(RestrictionPolicy.USBInterface.values()));
        arrayList.remove(RestrictionPolicy.USBInterface.OFF);
        arrayList.remove(RestrictionPolicy.USBInterface.ABL);
        return (String) arrayList.stream().filter(new Predicate() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getRestrictionList$1;
                lambda$getRestrictionList$1 = RestrictionPolicy.lambda$getRestrictionList$1(i, (RestrictionPolicy.USBInterface) obj);
                return lambda$getRestrictionList$1;
            }
        }).map(new Function() { // from class: com.android.server.enterprise.restriction.RestrictionPolicy$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String uSBInterface;
                uSBInterface = ((RestrictionPolicy.USBInterface) obj).toString();
                return uSBInterface;
            }
        }).collect(Collectors.joining(XmlUtils.STRING_ARRAY_SEPARATOR));
    }

    public static /* synthetic */ boolean lambda$getRestrictionList$1(int i, RestrictionPolicy.USBInterface uSBInterface) {
        return (i & uSBInterface.getValue()) > 0;
    }

    public final int syncUsbExceptionList(int i) {
        Log.d("RestrictionPolicy", "syncUsbExceptionList.");
        if (i == -1) {
            Log.w("RestrictionPolicy", "syncUsbExceptionList. -1 -> return");
            return 0;
        }
        try {
            if (new File("sys/class/usb_notify/usb_control/whitelist_for_mdm").exists()) {
                int usbDriverExceptionList = getUsbDriverExceptionList();
                Log.d("RestrictionPolicy", "UsbInterface Exception mask USB Driver = None( 0x" + Integer.toHexString(usbDriverExceptionList) + " )");
                if (i == usbDriverExceptionList) {
                    Log.d("RestrictionPolicy", "UsbExceptionList already applied : 0x" + Integer.toHexString(i));
                    return 0;
                }
                return setUsbExceptionListOnDriver(i);
            }
            Log.d("RestrictionPolicy", "usbDriverFile.exists : false");
            return 0;
        } catch (Exception unused) {
            Log.d("RestrictionPolicy", "usbDriverFile read fail");
            return -1;
        }
    }

    public final int getUsbDriverExceptionList() {
        try {
            String trim = FileUtils.readTextFile(new File("sys/class/usb_notify/usb_control/whitelist_for_mdm"), 0, null).trim();
            if (trim.length() > 2) {
                int i = 0;
                for (String str : trim.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    try {
                        i |= RestrictionPolicy.USBInterface.valueOf(str).getValue();
                    } catch (Exception unused) {
                        Log.w("RestrictionPolicy", "input wrong value.");
                    }
                }
                return i;
            }
            return RestrictionPolicy.USBInterface.OFF.getValue();
        } catch (Exception unused2) {
            return RestrictionPolicy.USBInterface.OFF.getValue();
        }
    }

    public int getUsbExceptionList() {
        Log.d("RestrictionPolicy", "getUsbExceptionList.");
        try {
            List usbInterfaceExceptionMaskListInDb = getUsbInterfaceExceptionMaskListInDb();
            int i = -1;
            if (usbInterfaceExceptionMaskListInDb.isEmpty()) {
                return -1;
            }
            Iterator it = usbInterfaceExceptionMaskListInDb.iterator();
            while (it.hasNext()) {
                i &= ((Integer) it.next()).intValue();
            }
            Log.d("RestrictionPolicy", "UsbInterface Exception mask MDM DB = 0x" + Integer.toHexString(i));
            File file = new File("sys/class/usb_notify/usb_control/whitelist_for_mdm");
            if (file.exists()) {
                Log.d("RestrictionPolicy", "UsbInterface Exception mask USB Driver = " + FileUtils.readTextFile(file, 0, null).trim());
            }
            return i;
        } catch (Exception e) {
            Log.w("RestrictionPolicy", "Failed get DB exception mask", e);
            return -1000;
        }
    }

    public final List getUsbInterfaceExceptionMaskListInDb() {
        Integer asInteger;
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("RESTRICTION", new String[]{"UsbExceptionMask"}, null);
        ArrayList arrayList = new ArrayList();
        for (ContentValues contentValues : valuesList) {
            if (contentValues != null && (asInteger = contentValues.getAsInteger("UsbExceptionMask")) != null && asInteger.intValue() > -1) {
                arrayList.add(asInteger);
            }
        }
        return arrayList;
    }

    public void systemReady(int i) {
        Log.d("RestrictionPolicy", "systemReady() : mCurrentPhase = " + i);
        if (i == 500) {
            this.mConstrainedState = (ConstrainedModeInternal) LocalServices.getService(ConstrainedModeInternal.class);
            return;
        }
        if (i != 550) {
            return;
        }
        int usbExceptionList = getUsbExceptionList();
        if (usbExceptionList == -1) {
            usbExceptionList = RestrictionPolicy.USBInterface.OFF.getValue();
        }
        if (usbExceptionList != getUsbDriverExceptionList()) {
            setUsbExceptionListOnDriver(getUsbExceptionList());
        }
    }

    public boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceAdvancedRestrictionPermission = enforceAdvancedRestrictionPermission(contextInfo);
        int i = enforceAdvancedRestrictionPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAdvancedRestrictionPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "allowFaceRecognitionEvenCameraBlocked", z);
                try {
                    this.mRestrictionCache.update("allowFaceRecognitionEvenCameraBlocked", 2305843009213693952L, false, callingOrCurrentUserId, Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
                    if (putBoolean) {
                        setFaceRecognitionEvenCameraBlockedAllowedSystemUI(0, isFaceRecognitionAllowedEvenCameraBlocked(enforceAdvancedRestrictionPermission));
                        AuditLog.logAsUser(5, 1, true, Process.myPid(), "RestrictionPolicy", String.format(z ? "Admin %d has allowed camera." : "Admin %d has disallowed camera.", Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid)), callingOrCurrentUserId);
                    }
                    return putBoolean;
                } catch (Exception e) {
                    e = e;
                    z2 = putBoolean;
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(2305843009213693952L, false, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) {
        boolean putBoolean;
        ContextInfo enforceAdvancedRestrictionPermission = enforceAdvancedRestrictionPermission(contextInfo);
        Log.d("RestrictionPolicy", "allowLocalContactStorage : " + z);
        int i = enforceAdvancedRestrictionPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAdvancedRestrictionPermission);
        boolean z2 = false;
        try {
            putBoolean = this.mEdmStorageProvider.putBoolean(enforceAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "allowLocalContactStorage", z);
        } catch (Exception e) {
            e = e;
        }
        try {
            this.mRestrictionCache.update("allowLocalContactStorage", 4611686018427387904L, true, callingOrCurrentUserId, Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid), Boolean.valueOf(z));
            if (putBoolean) {
                SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isLocalContactStorageAllowed"), callingOrCurrentUserId);
            }
            Log.d("RestrictionPolicy", "Allow local contact storage state input DB : " + putBoolean);
            return putBoolean;
        } catch (Exception e2) {
            e = e2;
            z2 = putBoolean;
            Log.w("RestrictionPolicy", "Allow local contact storage state write fail");
            e.printStackTrace();
            return z2;
        }
    }

    public boolean isLocalContactStorageAllowed(ContextInfo contextInfo) {
        Log.d("RestrictionPolicy", "isLocalContactStorageAllowed");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract = this.mRestrictionCache.extract(4611686018427387904L, true, callingOrCurrentUserId);
        Log.d("RestrictionPolicy", "isLocalContactStorageAllowed(" + callingOrCurrentUserId + ") : " + extract);
        return extract;
    }

    public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
        return this.mConstrainedState.enableConstrainedState(getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid, str, str2, str3, str4, i);
    }

    public boolean disableConstrainedState(ContextInfo contextInfo) {
        return this.mConstrainedState.disableConstrainedState(getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid);
    }

    public int getConstrainedState() {
        return this.mConstrainedState.getConstrainedState();
    }

    public boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d("RestrictionPolicy", "setKnoxDelegationEnabled : " + z + " with userId : " + callingOrCurrentUserId);
        if (callingOrCurrentUserId != 0) {
            return false;
        }
        try {
            if (!KpuHelper.getInstance(this.mContext).isCallerValidKpu(contextInfo)) {
                return false;
            }
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "RESTRICTION", "knox_delegation", z);
            Log.d("RestrictionPolicy", "setKnoxDelegationEnabled result : " + putBoolean);
            this.mRestrictionCache.update("knox_delegation", Long.MIN_VALUE, false, callingOrCurrentUserId, Integer.valueOf(contextInfo.mCallerUid), Boolean.valueOf(z));
            return putBoolean;
        } catch (Exception unused) {
            Slog.w("RestrictionPolicy", "is EDMStorageProvider running?");
            return false;
        }
    }

    public boolean isKnoxDelegationEnabled(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(Long.MIN_VALUE, false, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public void updateRestrictionCacheForWpcod() {
        Log.d("RestrictionPolicy", "updateRestrictionCacheForWpcod() called..");
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        try {
            this.mRestrictionCache.update("backupEnabled", 17592186044416L, true, 0, null, null);
            this.mRestrictionCache.update("clipboardEnabled", 512L, true, 0, null, null);
            this.mRestrictionCache.update("factoryresetallowed", 2251799813685248L, true, 0, null, null);
            this.mRestrictionCache.update("allowClipboardShare", 131072L, true, 0, null, null);
            this.mRestrictionCache.update("allowGoogleAccountsAutoSync", 33554432L, true, 0, null, null);
            SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
            SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
            manageEFSFile(true, "/efs/MDM/FactoryReset");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RestrictionPolicy", "updateRestrictionCacheForWpcod() error: " + e.getMessage());
        }
    }

    public final class LocalService extends RestrictionPolicyInternal {
        public LocalService() {
        }

        public boolean isScreenCaptureEnabledEx(int i, boolean z) {
            return RestrictionPolicy.this.isScreenCaptureEnabledEx(i, z);
        }
    }

    public void updateUserRestrictionsByKC(String str, boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_USERS") == 0 && this.mKcUid != -1) {
            if (z) {
                if (Binder.getCallingUid() == this.mKcUid) {
                    this.mUserRestrictionEnforcedByKC.add(str);
                    storeRestrictionsValuesByKC();
                    return;
                }
                return;
            }
            if (this.mUserRestrictionEnforcedByKC.contains(str)) {
                this.mUserRestrictionEnforcedByKC.remove(str);
                storeRestrictionsValuesByKC();
            }
        }
    }

    public boolean checkIfRestrictionWasSetByKC(String str) {
        if (Binder.getCallingUid() == Process.myUid()) {
            return this.mUserRestrictionEnforcedByKC.contains(str);
        }
        return false;
    }

    public final boolean storeRestrictionsValuesByKC() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", getRestrictionsStringMode());
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("name", "userRestrictionsSetByKc");
        return this.mEdmStorageProvider.put("generic", contentValues, contentValues2);
    }

    public final List getUserRestrictionsApplied() {
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "userRestrictionsSetByKc");
        String string = this.mEdmStorageProvider.getString("generic", "value", contentValues);
        if (TextUtils.isEmpty(string)) {
            return arrayList;
        }
        Log.d("RestrictionPolicy", "Restrictions Aplied by KC: " + string);
        return Arrays.asList(string.split(KnoxVpnFirewallHelper.DELIMITER));
    }

    public final int getKcAdminUid() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminName", com.samsung.android.knox.restriction.RestrictionPolicy.KC_COMPONENT_NAME.flattenToString());
        return this.mEdmStorageProvider.getInt("ADMIN_INFO", "adminUid", contentValues);
    }

    public final String getRestrictionsStringMode() {
        StringJoiner stringJoiner = new StringJoiner(KnoxVpnFirewallHelper.DELIMITER);
        Iterator it = this.mUserRestrictionEnforcedByKC.iterator();
        while (it.hasNext()) {
            stringJoiner.add((String) it.next());
        }
        return stringJoiner.toString();
    }

    public String getKcActionDisabledText() {
        return this.mContext.getString(R.string.bugreport_title);
    }
}
