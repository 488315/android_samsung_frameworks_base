package com.android.server.enterprise.restriction;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyCache;
import android.app.admin.DevicePolicyManager;
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
import android.content.SharedPreferences;
import android.content.SyncAdapterType;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.debug.IAdbManager;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.INetworkPolicyManager;
import android.net.NetworkPolicy;
import android.net.TetheringManager;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
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
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;

import com.android.internal.app.LocalePicker;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.StorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.VpnInfoPolicy;
import com.android.server.location.gnss.hal.GnssNative;

import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import com.samsung.android.knox.localservice.RestrictionPolicyInternal;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.security.mdf.MdfService.MdfPolicy;
import com.samsung.android.security.mdf.MdfUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class RestrictionPolicy extends IRestrictionPolicy.Stub
        implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static final String[] excludedAdminList = {
        "com.sec.enterprise.knox.cloudmdm.smdms",
        "com.sec.sprextension.phoneinfo",
        "com.samsung.klmsagent",
        "com.samsung.android.knox.containercore",
        "com.samsung.android.kgclient"
    };
    public final boolean debug;
    public ApplicationPolicy mAppPolicy;
    public final AudioManager mAudioManager;
    public final AnonymousClass1 mBootReceiver;
    public int mCallCount;
    public ConstrainedModeInternal mConstrainedState;
    public final Context mContext;
    public DevicePolicyManager mDpm;
    public EnterpriseDeviceManager mEDM;
    public IEnterpriseDeviceManager mEdmService;
    public final EdmStorageProvider mEdmStorageProvider;
    public final AnonymousClass1 mFotaReceiver;
    public final AnonymousClass3 mHandler;
    public KeyCodeMediatorImpl mKeyCodeMediator;
    public final RestrictionPolicyCache mRestrictionCache;
    public final AnonymousClass4 mStorageListener;
    public SubscriptionManager mSubscriptionManager;
    public AnonymousClass5 mSubscriptionsChangedListener;
    public TelecomManager mTelecomManager;
    public TelephonyManager mTelephonyManager;
    public final UserManager mUserManager;
    public VpnInfoPolicy mVpnPolicy;
    public StorageManager mStorageManager = null;
    public boolean preAdminRemoval_SDCardWrite = false;
    public boolean isLockScreenWidgetsAllowedCache = true;
    public boolean isLockScreenShortcutsAllowedCache = true;
    public boolean isSafeModeAllowedCache = true;
    public boolean mUsbSyncFlag = false;
    public IStatusBarService mStatusBarService = null;
    public final IBinder mToken = new Binder();
    public Set mUserRestrictionEnforcedByKC = new ArraySet();
    public int mKcUid = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends RestrictionPolicyInternal {
        public LocalService() {}

        public final boolean isScreenCaptureEnabledEx(int i, boolean z) {
            return RestrictionPolicy.this.isScreenCaptureEnabledEx(i, z);
        }
    }

    /* renamed from: -$$Nest$mupdateUSBMode, reason: not valid java name */
    public static void m521$$Nest$mupdateUSBMode(RestrictionPolicy restrictionPolicy) {
        if (restrictionPolicy.isUsbDebuggingEnabled(null)
                || Settings.Secure.getInt(
                                restrictionPolicy.mContext.getContentResolver(), "adb_enabled", 0)
                        != 1) {
            return;
        }
        Settings.Secure.putInt(restrictionPolicy.mContext.getContentResolver(), "adb_enabled", 0);
    }

    static {
        try {
            System.loadLibrary("android_servers");
        } catch (UnsatisfiedLinkError unused) {
            Log.d("RestrictionPolicy", "Unable to Load Library android_servers");
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.enterprise.restriction.RestrictionPolicy$3] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.server.enterprise.restriction.RestrictionPolicy$4] */
    public RestrictionPolicy(Context context) {
        this.mAppPolicy = null;
        this.mVpnPolicy = null;
        boolean z = true;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.restriction.RestrictionPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i2 = 0;
                        switch (i) {
                            case 0:
                                Slog.d("RestrictionPolicy", "FOTAReceiver: onReceive");
                                String action = intent.getAction();
                                if (action.equals("sec.fota.intent.MDM_REGISTER_RESULT")) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    KnoxCustomManagerService.SPCM_KEY_RESULT, 0);
                                    if (intExtra == 0 || intExtra == 1) {
                                        Slog.d(
                                                "RestrictionPolicy",
                                                "action:sec.fota.intent.MDM_REGISTER_RESULT"
                                                    + " success");
                                    } else {
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "FOTAReceiver:"
                                                    + " action:sec.fota.intent.MDM_REGISTER_RESULT"
                                                    + " failure(",
                                                ")",
                                                "RestrictionPolicy");
                                        RestrictionPolicy restrictionPolicy = this;
                                        String[] strArr = RestrictionPolicy.excludedAdminList;
                                        restrictionPolicy.clearSelectiveFota();
                                        i2 = 1;
                                    }
                                    RestrictionPolicy restrictionPolicy2 = this;
                                    String[] strArr2 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy2.sendSeletiveFotaResult(i2);
                                    return;
                                }
                                if (action.equals("com.xdm.intent.UPDATE_RESULT")) {
                                    if (this.getAllowedFOTAInfo(null) == null) {
                                        Slog.d(
                                                "RestrictionPolicy",
                                                "action:com.xdm.intent.UPDATE_RESULT ignore");
                                        return;
                                    }
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                            KnoxCustomManagerService
                                                                    .SPCM_KEY_RESULT,
                                                            0)
                                                    + 5;
                                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            intExtra2,
                                            "action:com.xdm.intent.UPDATE_RESULT = ",
                                            "RestrictionPolicy");
                                    if (intExtra2 > 8 || intExtra2 < 5) {
                                        return;
                                    }
                                    this.sendSeletiveFotaResult(intExtra2);
                                    return;
                                }
                                if (action.equals("sec.fota.intent.MDM_UNREGISTER")) {
                                    Slog.d(
                                            "RestrictionPolicy",
                                            "FOTAReceiver: action:sec.fota.intent.MDM_UNREGISTER");
                                    RestrictionPolicy restrictionPolicy3 = this;
                                    String[] strArr3 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy3.clearSelectiveFota();
                                    String[] strArr4 = {"com.wssyncmldm", "com.samsung.utagent"};
                                    while (i2 < 2) {
                                        Intent intent2 = new Intent("sec.fota.intent.MDM_REGISTER");
                                        intent2.setPackage(strArr4[i2]);
                                        this.mContext.sendBroadcast(intent2);
                                        i2++;
                                    }
                                    return;
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                Log.d("RestrictionPolicy", action2);
                                if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    try {
                                        RestrictionPolicy.m521$$Nest$mupdateUSBMode(this);
                                    } catch (Exception unused) {
                                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                                    }
                                    RestrictionPolicy restrictionPolicy4 = this;
                                    restrictionPolicy4.setStatusBarExpansionSystemUI(
                                            0,
                                            restrictionPolicy4.isStatusBarExpansionAllowedAsUser(
                                                    false, 0));
                                    RestrictionPolicy restrictionPolicy5 = this;
                                    restrictionPolicy5.updateUsbStorageStatebyIntent(
                                            restrictionPolicy5.isUsbHostStorageAllowed(
                                                    null, false));
                                    String databaseUpgradeValue =
                                            this.mEdmStorageProvider.getDatabaseUpgradeValue(
                                                    "PlatformSoftwareVersion");
                                    String str =
                                            SystemProperties.get("ro.build.fingerprint", "unknown");
                                    String str2 = str.equalsIgnoreCase("unknown") ? null : str;
                                    if (databaseUpgradeValue == null
                                            || (str2 != null
                                                    && !str2.equals(databaseUpgradeValue))) {
                                        Slog.d("RestrictionPolicy", "isFirmwareChanged : true");
                                        this.updateNonMarketAppOnUserManager();
                                    }
                                    this.notifyChanges(-1);
                                    if (this.isAirplaneModeAllowed(false)) {
                                        return;
                                    }
                                    this.turnOffAirPlaneMode();
                                    return;
                                }
                                if (action2.equals(
                                        "android.intent.action.ACTION_POWER_DISCONNECTED")) {
                                    try {
                                        RestrictionPolicy.m521$$Nest$mupdateUSBMode(this);
                                        return;
                                    } catch (Exception unused2) {
                                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                                        return;
                                    }
                                }
                                if (action2.equals(
                                                "edm.intent.action.internal.sec.DEFAULT_NETWORK_POLICY_APPLIED")
                                        && (!this.isBackgroundDataEnabled(null)
                                                || !this.isDataSavingAllowed())) {
                                    try {
                                        this.applyBackgroundDataRestriction();
                                        return;
                                    } catch (Exception unused3) {
                                        Slog.w("RestrictionPolicy", "Network Policy update failed");
                                        return;
                                    }
                                }
                                if ("android.intent.action.USER_SWITCHED".equals(action2)) {
                                    int intExtra3 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "RestrictionPolicy",
                                            "Intent.ACTION_USER_SWITCHED occurred!! action:"
                                                    + action2
                                                    + " userId="
                                                    + intExtra3);
                                    StringBuilder sb = new StringBuilder("");
                                    RestrictionPolicy restrictionPolicy6 = this;
                                    String[] strArr5 = RestrictionPolicy.excludedAdminList;
                                    sb.append(
                                            restrictionPolicy6.isScreenCaptureEnabled(
                                                            intExtra3, false)
                                                    ? 1
                                                    : 0);
                                    Utils.writePropertyValue(
                                            "screenCaptureEnabled",
                                            sb.toString(),
                                            "/data/system/enterprise.conf");
                                    return;
                                }
                                if ("android.intent.action.USER_ADDED".equals(action2)) {
                                    int intExtra4 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    RestrictionPolicy restrictionPolicy7 = this;
                                    String[] strArr6 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy7.mRestrictionCache.load(intExtra4);
                                    restrictionPolicy7.notifyChanges(intExtra4);
                                    return;
                                }
                                if (!"android.intent.action.USER_REMOVED".equals(action2)) {
                                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                                            .equals(action2)) {
                                        int intExtra5 =
                                                intent.getIntExtra(
                                                        "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                                        0);
                                        RestrictionPolicy restrictionPolicy8 = this;
                                        String[] strArr7 = RestrictionPolicy.excludedAdminList;
                                        restrictionPolicy8.updateSystemUIMonitor$7(intExtra5);
                                        return;
                                    }
                                    return;
                                }
                                int intExtra6 =
                                        intent.getIntExtra("android.intent.extra.user_handle", 0);
                                RestrictionPolicyCache restrictionPolicyCache =
                                        this.mRestrictionCache;
                                restrictionPolicyCache.mLock.writeLock().lock();
                                try {
                                    restrictionPolicyCache.mCache.remove(
                                            Integer.valueOf(intExtra6));
                                    RestrictionPolicyCache.ApplyingAdmins applyingAdmins =
                                            restrictionPolicyCache.mApplyingAdmins;
                                    ((HashMap) applyingAdmins.admins)
                                            .remove(Integer.valueOf(intExtra6));
                                    ((HashMap) applyingAdmins.adminInfoMap)
                                            .entrySet()
                                            .removeIf(
                                                    new RestrictionPolicyCache$ApplyingAdmins$$ExternalSyntheticLambda0(
                                                            intExtra6, 1));
                                    restrictionPolicyCache.mCameraDisabledAdmin.remove(
                                            Integer.valueOf(intExtra6));
                                    return;
                                } finally {
                                    restrictionPolicyCache.mLock.writeLock().unlock();
                                }
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.restriction.RestrictionPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i22 = 0;
                        switch (i2) {
                            case 0:
                                Slog.d("RestrictionPolicy", "FOTAReceiver: onReceive");
                                String action = intent.getAction();
                                if (action.equals("sec.fota.intent.MDM_REGISTER_RESULT")) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    KnoxCustomManagerService.SPCM_KEY_RESULT, 0);
                                    if (intExtra == 0 || intExtra == 1) {
                                        Slog.d(
                                                "RestrictionPolicy",
                                                "action:sec.fota.intent.MDM_REGISTER_RESULT"
                                                    + " success");
                                    } else {
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                intExtra,
                                                "FOTAReceiver:"
                                                    + " action:sec.fota.intent.MDM_REGISTER_RESULT"
                                                    + " failure(",
                                                ")",
                                                "RestrictionPolicy");
                                        RestrictionPolicy restrictionPolicy = this;
                                        String[] strArr = RestrictionPolicy.excludedAdminList;
                                        restrictionPolicy.clearSelectiveFota();
                                        i22 = 1;
                                    }
                                    RestrictionPolicy restrictionPolicy2 = this;
                                    String[] strArr2 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy2.sendSeletiveFotaResult(i22);
                                    return;
                                }
                                if (action.equals("com.xdm.intent.UPDATE_RESULT")) {
                                    if (this.getAllowedFOTAInfo(null) == null) {
                                        Slog.d(
                                                "RestrictionPolicy",
                                                "action:com.xdm.intent.UPDATE_RESULT ignore");
                                        return;
                                    }
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                            KnoxCustomManagerService
                                                                    .SPCM_KEY_RESULT,
                                                            0)
                                                    + 5;
                                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            intExtra2,
                                            "action:com.xdm.intent.UPDATE_RESULT = ",
                                            "RestrictionPolicy");
                                    if (intExtra2 > 8 || intExtra2 < 5) {
                                        return;
                                    }
                                    this.sendSeletiveFotaResult(intExtra2);
                                    return;
                                }
                                if (action.equals("sec.fota.intent.MDM_UNREGISTER")) {
                                    Slog.d(
                                            "RestrictionPolicy",
                                            "FOTAReceiver: action:sec.fota.intent.MDM_UNREGISTER");
                                    RestrictionPolicy restrictionPolicy3 = this;
                                    String[] strArr3 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy3.clearSelectiveFota();
                                    String[] strArr4 = {"com.wssyncmldm", "com.samsung.utagent"};
                                    while (i22 < 2) {
                                        Intent intent2 = new Intent("sec.fota.intent.MDM_REGISTER");
                                        intent2.setPackage(strArr4[i22]);
                                        this.mContext.sendBroadcast(intent2);
                                        i22++;
                                    }
                                    return;
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                Log.d("RestrictionPolicy", action2);
                                if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    try {
                                        RestrictionPolicy.m521$$Nest$mupdateUSBMode(this);
                                    } catch (Exception unused) {
                                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                                    }
                                    RestrictionPolicy restrictionPolicy4 = this;
                                    restrictionPolicy4.setStatusBarExpansionSystemUI(
                                            0,
                                            restrictionPolicy4.isStatusBarExpansionAllowedAsUser(
                                                    false, 0));
                                    RestrictionPolicy restrictionPolicy5 = this;
                                    restrictionPolicy5.updateUsbStorageStatebyIntent(
                                            restrictionPolicy5.isUsbHostStorageAllowed(
                                                    null, false));
                                    String databaseUpgradeValue =
                                            this.mEdmStorageProvider.getDatabaseUpgradeValue(
                                                    "PlatformSoftwareVersion");
                                    String str =
                                            SystemProperties.get("ro.build.fingerprint", "unknown");
                                    String str2 = str.equalsIgnoreCase("unknown") ? null : str;
                                    if (databaseUpgradeValue == null
                                            || (str2 != null
                                                    && !str2.equals(databaseUpgradeValue))) {
                                        Slog.d("RestrictionPolicy", "isFirmwareChanged : true");
                                        this.updateNonMarketAppOnUserManager();
                                    }
                                    this.notifyChanges(-1);
                                    if (this.isAirplaneModeAllowed(false)) {
                                        return;
                                    }
                                    this.turnOffAirPlaneMode();
                                    return;
                                }
                                if (action2.equals(
                                        "android.intent.action.ACTION_POWER_DISCONNECTED")) {
                                    try {
                                        RestrictionPolicy.m521$$Nest$mupdateUSBMode(this);
                                        return;
                                    } catch (Exception unused2) {
                                        Slog.w("RestrictionPolicy", "updateUsbMode failed");
                                        return;
                                    }
                                }
                                if (action2.equals(
                                                "edm.intent.action.internal.sec.DEFAULT_NETWORK_POLICY_APPLIED")
                                        && (!this.isBackgroundDataEnabled(null)
                                                || !this.isDataSavingAllowed())) {
                                    try {
                                        this.applyBackgroundDataRestriction();
                                        return;
                                    } catch (Exception unused3) {
                                        Slog.w("RestrictionPolicy", "Network Policy update failed");
                                        return;
                                    }
                                }
                                if ("android.intent.action.USER_SWITCHED".equals(action2)) {
                                    int intExtra3 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "RestrictionPolicy",
                                            "Intent.ACTION_USER_SWITCHED occurred!! action:"
                                                    + action2
                                                    + " userId="
                                                    + intExtra3);
                                    StringBuilder sb = new StringBuilder("");
                                    RestrictionPolicy restrictionPolicy6 = this;
                                    String[] strArr5 = RestrictionPolicy.excludedAdminList;
                                    sb.append(
                                            restrictionPolicy6.isScreenCaptureEnabled(
                                                            intExtra3, false)
                                                    ? 1
                                                    : 0);
                                    Utils.writePropertyValue(
                                            "screenCaptureEnabled",
                                            sb.toString(),
                                            "/data/system/enterprise.conf");
                                    return;
                                }
                                if ("android.intent.action.USER_ADDED".equals(action2)) {
                                    int intExtra4 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    RestrictionPolicy restrictionPolicy7 = this;
                                    String[] strArr6 = RestrictionPolicy.excludedAdminList;
                                    restrictionPolicy7.mRestrictionCache.load(intExtra4);
                                    restrictionPolicy7.notifyChanges(intExtra4);
                                    return;
                                }
                                if (!"android.intent.action.USER_REMOVED".equals(action2)) {
                                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                                            .equals(action2)) {
                                        int intExtra5 =
                                                intent.getIntExtra(
                                                        "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                                        0);
                                        RestrictionPolicy restrictionPolicy8 = this;
                                        String[] strArr7 = RestrictionPolicy.excludedAdminList;
                                        restrictionPolicy8.updateSystemUIMonitor$7(intExtra5);
                                        return;
                                    }
                                    return;
                                }
                                int intExtra6 =
                                        intent.getIntExtra("android.intent.extra.user_handle", 0);
                                RestrictionPolicyCache restrictionPolicyCache =
                                        this.mRestrictionCache;
                                restrictionPolicyCache.mLock.writeLock().lock();
                                try {
                                    restrictionPolicyCache.mCache.remove(
                                            Integer.valueOf(intExtra6));
                                    RestrictionPolicyCache.ApplyingAdmins applyingAdmins =
                                            restrictionPolicyCache.mApplyingAdmins;
                                    ((HashMap) applyingAdmins.admins)
                                            .remove(Integer.valueOf(intExtra6));
                                    ((HashMap) applyingAdmins.adminInfoMap)
                                            .entrySet()
                                            .removeIf(
                                                    new RestrictionPolicyCache$ApplyingAdmins$$ExternalSyntheticLambda0(
                                                            intExtra6, 1));
                                    restrictionPolicyCache.mCameraDisabledAdmin.remove(
                                            Integer.valueOf(intExtra6));
                                    return;
                                } finally {
                                    restrictionPolicyCache.mLock.writeLock().unlock();
                                }
                        }
                    }
                };
        this.mEDM = null;
        this.mHandler =
                new Handler() { // from class:
                    // com.android.server.enterprise.restriction.RestrictionPolicy.3
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1) {
                            return;
                        }
                        RestrictionPolicy.this.mContext.startService(
                                new Intent("com.android.voicerecorder.HIDENOTIFICATION")
                                        .setComponent(
                                                new ComponentName(
                                                        "com.sec.android.app.voicerecorder.service",
                                                        "com.sec.android.app.voicerecorder.service.VoiceRecorderService")));
                    }
                };
        this.mStorageListener =
                new StorageEventListener() { // from class:
                                             // com.android.server.enterprise.restriction.RestrictionPolicy.4
                    public final void onStorageStateChanged(String str, String str2, String str3) {
                        StringBuilder m =
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "path = ", str, ", oldState = ", str2, ", newState = ");
                        m.append(str3);
                        Log.d("RestrictionPolicy", m.toString());
                        if (str2.equals("ejecting") && str3.equals("unmounted")) {
                            RestrictionPolicy restrictionPolicy = RestrictionPolicy.this;
                            String[] strArr = RestrictionPolicy.excludedAdminList;
                            restrictionPolicy.mountSdCard();
                        }
                        if (str2.equals("checking") && str3.equals("mounted")) {
                            RestrictionPolicy restrictionPolicy2 = RestrictionPolicy.this;
                            if (restrictionPolicy2.mStorageManager == null) {
                                restrictionPolicy2.mStorageManager =
                                        (StorageManager)
                                                restrictionPolicy2.mContext.getSystemService(
                                                        "storage");
                            }
                            restrictionPolicy2.mStorageManager.unregisterListener(
                                    RestrictionPolicy.this.mStorageListener);
                            if (RestrictionPolicy.this.isSDCardWriteAllowed(null)) {
                                Log.d(
                                        "RestrictionPolicy",
                                        "SDCard Remounted with ReadWrite permission");
                            } else {
                                Log.d(
                                        "RestrictionPolicy",
                                        "SDCard Remounted with Readonly permission");
                            }
                        }
                        Log.d("RestrictionPolicy", "--onStorageStateChanged");
                    }
                };
        String str = Build.TYPE;
        if (!str.equals("eng") && !str.equals("userdebug")) {
            z = false;
        }
        this.debug = z;
        this.mCallCount = 0;
        this.mSubscriptionManager = null;
        this.mTelecomManager = null;
        this.mTelephonyManager = null;
        this.mDpm = null;
        Objects.requireNonNull(context);
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mRestrictionCache = new RestrictionPolicyCache(context, edmStorageProvider);
        this.mAppPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        this.mVpnPolicy = (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "android.intent.action.ACTION_POWER_DISCONNECTED",
                        "edm.intent.action.internal.sec.DEFAULT_NETWORK_POLICY_APPLIED",
                        "android.intent.action.USER_SWITCHED",
                        "android.intent.action.USER_ADDED");
        m.addAction("android.intent.action.USER_REMOVED");
        m.addAction(
                "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("sec.fota.intent.MDM_REGISTER_RESULT");
        intentFilter.addAction("com.xdm.intent.UPDATE_RESULT");
        intentFilter.addAction("sec.fota.intent.MDM_UNREGISTER");
        context.registerReceiver(
                broadcastReceiver,
                intentFilter,
                "com.sec.android.fotaclient.permission.FOTA",
                null,
                2);
        context.registerReceiver(broadcastReceiver2, m, 2);
        this.mUserManager = (UserManager) context.getSystemService("user");
        IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        if (isHomeKeyEnabledInDb()) {
            return;
        }
        setHomeKeyVisibilityOnNavi(false);
    }

    public static String getExternalSdCardPath() {
        ((StorageManagerAdapter)
                        ((IStorageManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IStorageManagerAdapter.class)))
                .getClass();
        for (StorageVolume storageVolume : StorageManagerAdapter.mStorageManager.getVolumeList()) {
            if (storageVolume.getSubSystem().equals("sd")) {
                return storageVolume.getPath();
            }
        }
        return null;
    }

    public static IPersonaManagerAdapter getPersonaManagerAdapter$5() {
        return (IPersonaManagerAdapter)
                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
    }

    public static String getRestrictionList(final int i) {
        ArrayList arrayList = new ArrayList(Arrays.asList(RestrictionPolicy.USBInterface.values()));
        arrayList.remove(RestrictionPolicy.USBInterface.OFF);
        arrayList.remove(RestrictionPolicy.USBInterface.ABL);
        return (String)
                arrayList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.server.enterprise.restriction.RestrictionPolicy$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i2 = i;
                                        String[] strArr = RestrictionPolicy.excludedAdminList;
                                        return (i2
                                                        & ((RestrictionPolicy.USBInterface) obj)
                                                                .getValue())
                                                > 0;
                                    }
                                })
                        .map(new RestrictionPolicy$$ExternalSyntheticLambda3())
                        .collect(Collectors.joining(":"));
    }

    public static int getTopActivityUserId() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i =
                        ((PersonaManagerAdapter) getPersonaManagerAdapter$5())
                                .getFocusedUserWithActivityManager();
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

    public static int getUsbDriverExceptionList() {
        try {
            String trim =
                    FileUtils.readTextFile(
                                    new File("sys/class/usb_notify/usb_control/whitelist_for_mdm"),
                                    0,
                                    null)
                            .trim();
            if (trim.length() <= 2) {
                return RestrictionPolicy.USBInterface.OFF.getValue();
            }
            int i = 0;
            for (String str : trim.split(":")) {
                try {
                    i |= RestrictionPolicy.USBInterface.valueOf(str).getValue();
                } catch (Exception unused) {
                    Log.w("RestrictionPolicy", "input wrong value.");
                }
            }
            return i;
        } catch (Exception unused2) {
            return RestrictionPolicy.USBInterface.OFF.getValue();
        }
    }

    public static boolean isExistEFSFile(String str) {
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

    public static boolean isUCMKeyguardEnabled() {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("RestrictionPolicy", "Failed to get UCM Service");
            return false;
        }
        try {
            String keyguardStorageForCurrentUser = asInterface.getKeyguardStorageForCurrentUser(0);
            if (keyguardStorageForCurrentUser != null
                    && !keyguardStorageForCurrentUser.isEmpty()
                    && !keyguardStorageForCurrentUser.equalsIgnoreCase("none")) {
                Log.d("RestrictionPolicy", "UCM Keyguard is enabled");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x004a, code lost:

       if (r11 == null) goto L22;
    */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void manageEFSFile(java.lang.String r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.manageEFSFile(java.lang.String,"
                    + " boolean):void");
    }

    private native byte[] readParamData();

    public static boolean writeData(boolean z) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 5250
                && Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        byte[] bArr = new byte[32];
        new SecureRandom().nextBytes(bArr);
        if (z) {
            bArr[7] = 7;
            bArr[30] = 1;
            bArr[31] = 5;
        } else {
            bArr[7] = 8;
            bArr[30] = 2;
            bArr[31] = 6;
        }
        Slog.d("RestrictionPolicy", "writeData : index - 7 value - " + Arrays.toString(bArr));
        return writeParamData(bArr);
    }

    private static native boolean writeParamData(byte[] bArr);

    public final boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceCaller =
                EnterpriseAccessController.enforceCaller(contextInfo, "RESTRICTION_ADMIN");
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null && str.length() > 0) {
                    sb.append(str.concat(","));
                }
            }
        }
        return this.mEdmStorageProvider.putString(
                enforceCaller.mCallerUid,
                0,
                "RESTRICTION",
                "preventAdminActivationWhiteList",
                sb.toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.util.ArrayList] */
    public final boolean adjustPreferredSim(List list) {
        SubscriptionInfo subscriptionInfo;
        SubscriptionManager subScriptionManager = getSubScriptionManager();
        if (list == 0) {
            list = new ArrayList();
            for (SubscriptionInfo subscriptionInfo2 :
                    subScriptionManager.getAllSubscriptionInfoList()) {
                if (!subscriptionInfo2.isEmbedded()
                        && !subscriptionInfo2.areUiccApplicationsEnabled()) {
                    list.add(subscriptionInfo2);
                    if (this.debug) {
                        Log.d(
                                "MultiSimPolicy",
                                "adjustPreferredSim disabledSubInfo.add : " + subscriptionInfo2);
                    }
                }
            }
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                subScriptionManager.getActiveSubscriptionInfoList();
        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        while (true) {
            if (!it.hasNext()) {
                subscriptionInfo = null;
                break;
            }
            subscriptionInfo = it.next();
            if (!subscriptionInfo.isEmbedded() && subscriptionInfo.areUiccApplicationsEnabled()) {
                break;
            }
        }
        if (this.debug) {
            Log.d("MultiSimPolicy", "adjustPreferredSim availableSubInfo(P) : " + subscriptionInfo);
        }
        if (subscriptionInfo == null) {
            Iterator<SubscriptionInfo> it2 = activeSubscriptionInfoList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                SubscriptionInfo next = it2.next();
                if (next.isEmbedded() && next.getSimSlotIndex() >= 0) {
                    if (this.debug) {
                        Log.d("MultiSimPolicy", "adjustPreferredSim availableSubInfo(E) : " + next);
                    }
                    subscriptionInfo = next;
                }
            }
        }
        boolean z = false;
        if (subscriptionInfo == null) {
            if (this.debug) {
                Log.d(
                        "MultiSimPolicy",
                        "adjustPreferredSim availableSubInfo == null. returns false");
            }
            return false;
        }
        for (SubscriptionInfo subscriptionInfo3 : list) {
            if (SubscriptionManager.getDefaultSmsSubscriptionId()
                    == subscriptionInfo3.getSubscriptionId()) {
                if (this.debug) {
                    Log.d("MultiSimPolicy", "adjustPreferredSim prev sms : " + subscriptionInfo3);
                }
                subScriptionManager.setDefaultSmsSubId(subscriptionInfo.getSubscriptionId());
                z = true;
            }
        }
        for (SubscriptionInfo subscriptionInfo4 : list) {
            if (SubscriptionManager.getDefaultDataSubscriptionId()
                    == subscriptionInfo4.getSubscriptionId()) {
                if (this.debug) {
                    Log.d("MultiSimPolicy", "adjustPreferredSim prev data : " + subscriptionInfo4);
                }
                subScriptionManager.setDefaultDataSubId(subscriptionInfo.getSubscriptionId());
                z = true;
            }
        }
        for (SubscriptionInfo subscriptionInfo5 : list) {
            if (SubscriptionManager.getDefaultVoiceSubscriptionId()
                    == subscriptionInfo5.getSubscriptionId()) {
                if (this.debug) {
                    Log.d("MultiSimPolicy", "adjustPreferredSim prev call : " + subscriptionInfo5);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int subscriptionId = subscriptionInfo.getSubscriptionId();
                    ((TelecomManager) this.mContext.getSystemService(TelecomManager.class))
                            .semSetUserSelectedOutgoingPhoneAccount(
                                    getPhoneAccountForSubId(subscriptionId));
                    getSubScriptionManager().setDefaultVoiceSubId(subscriptionId);
                    if (this.debug) {
                        Log.d(
                                "MultiSimPolicy",
                                "setPreferredCallSetting getSimSlotIndex : "
                                        + subscriptionInfo.getSimSlotIndex());
                    }
                    Settings.System.putInt(
                            this.mContext.getContentResolver(),
                            "prefered_voice_call",
                            subscriptionInfo.getSimSlotIndex());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = true;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
        return z;
    }

    public final boolean allowActivationLock(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowActivationLock");
        this.mRestrictionCache.update(
                "allowActivationLock",
                134217728L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "allowAirplaneMode : allow = ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowAirplaneMode");
        this.mRestrictionCache.update(
                "allowAirplaneMode",
                128L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            boolean isAirplaneModeAllowed = isAirplaneModeAllowed(false);
            if (!isAirplaneModeAllowed) {
                turnOffAirPlaneMode();
            }
            setAirplaneModeAllowedSystemUI(0, isAirplaneModeAllowed);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has allowed airplane mode."
                                        : "Admin %d has disallowed airplane mode.",
                                Integer.valueOf(
                                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowAirplaneMode : ", "RestrictionPolicy", putBoolean);
        return putBoolean;
    }

    public final boolean allowAudioRecord(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION", enforceHwPermission.mCallerUid, z, 0, "allowAudioRecording");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowAudioRecord : ", "RestrictionPolicy", z);
        this.mRestrictionCache.update(
                "allowAudioRecording",
                1L,
                true,
                UserHandle.getUserId(enforceHwPermission.mCallerUid),
                Integer.valueOf(enforceHwPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            if (isAudioRecordAllowed(enforceHwPermission, false)) {
                this.mAudioManager.setParameters("g_knox_audiorecord_allowed=true");
            } else {
                this.mAudioManager.setParameters("g_knox_audiorecord_allowed=false");
            }
        }
        return putBoolean;
    }

    public final boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (!z) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManagerNative.getDefault().setProcessLimit(-1);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Fail getting ActivityManager "), "RestrictionPolicy");
                z2 = false;
            }
        }
        z2 = true;
        if (z2) {
            z2 &=
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            z,
                            0,
                            "limitOfBackgroundProcess");
            this.mRestrictionCache.update(
                    "limitOfBackgroundProcess",
                    16384L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowBackgroundProcessLimit : ", "RestrictionPolicy", z);
        return z2;
    }

    public final boolean allowClipboardShare(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowClipboardShare");
        this.mRestrictionCache.update(
                "allowClipboardShare",
                131072L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
        StringBuilder sb = new StringBuilder("allowClipboardShare : ");
        sb.append(z);
        sb.append(", ret = ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("RestrictionPolicy", sb, putBoolean);
        return putBoolean;
    }

    public final boolean allowDataSaving(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowDataSaving");
        if (putBoolean) {
            this.mRestrictionCache.update(
                    "allowDataSaving",
                    576460752303423488L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
            applyBackgroundDataRestriction();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has allowed data saving."
                                        : "Admin %d has disallowed data saving.",
                                Integer.valueOf(
                                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowDataSaver : ", "RestrictionPolicy", putBoolean);
        return putBoolean;
    }

    public final boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (z) {
            z2 = true;
        } else {
            DeveloperModeSettings developerModeSettings = new DeveloperModeSettings(this.mContext);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("persist.bluetooth.btsnoopenable", Boolean.toString(false));
                boolean resetMockLocationApps = developerModeSettings.resetMockLocationApps();
                try {
                    ActivityManagerNative.getDefault().setDebugApp((String) null, false, true);
                    z3 = true;
                } catch (RemoteException unused) {
                    z3 = false;
                }
                boolean z7 = z3 & resetMockLocationApps;
                ((WifiManager) developerModeSettings.mContext.getSystemService("wifi"))
                        .setVerboseLoggingEnabled(false);
                try {
                    IAdbManager asInterface =
                            IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
                    boolean z8 = SystemProperties.getBoolean("ro.adb.secure", false);
                    boolean equals =
                            "trigger_restart_min_framework"
                                    .equals(SystemProperties.get("vold.decrypt"));
                    if (z8 && !equals) {
                        asInterface.clearDebuggingKeys();
                    }
                    z4 = true;
                } catch (RemoteException e) {
                    Log.e("DeveloperModeSettings", "Unable to clear adb keys", e);
                    z4 = false;
                }
                boolean resetDrawingOptions = z4 & z7 & DeveloperModeSettings.resetDrawingOptions();
                Settings.Global.putInt(
                        developerModeSettings.mContext.getContentResolver(), "debug.force_rtl", 0);
                SystemProperties.set("debug.force_rtl", "0");
                LocalePicker.updateLocales(
                        developerModeSettings
                                .mContext
                                .getResources()
                                .getConfiguration()
                                .getLocales());
                boolean resetWindowManagerOptions =
                        DeveloperModeSettings.resetWindowManagerOptions() & resetDrawingOptions;
                Settings.Global.putInt(
                        developerModeSettings.mContext.getContentResolver(), "show_processes", 0);
                developerModeSettings.mContext.stopService(
                        new Intent()
                                .setClassName(
                                        Constants.SYSTEMUI_PACKAGE_NAME,
                                        "com.android.systemui.LoadAverageService"));
                try {
                    ActivityManagerNative.getDefault().setProcessLimit(-1);
                    z5 = true;
                } catch (RemoteException e2) {
                    Log.w(
                            "DeveloperModeSettings",
                            "resetAppProcessLimitOptions: RemoteException ex -> "
                                    + e2.getMessage());
                    z5 = false;
                }
                boolean z9 = resetWindowManagerOptions & z5;
                try {
                    ActivityManagerNative.getDefault().setAlwaysFinish(false);
                    z6 = true;
                } catch (RemoteException e3) {
                    Log.w(
                            "DeveloperModeSettings",
                            "resetImmediatelyDestroyActivitiesOptions: RemoteException ex -> "
                                    + e3.getMessage());
                    z6 = false;
                }
                boolean z10 = z6 & z9;
                boolean z11 = true;
                for (Map.Entry entry :
                        ((HashMap) DeveloperModeSettings.SYSTEM_SETTINGS_DEFAULT).entrySet()) {
                    z11 &=
                            Settings.System.putString(
                                    developerModeSettings.mContext.getContentResolver(),
                                    (String) entry.getKey(),
                                    (String) entry.getValue());
                }
                boolean z12 = z10 & z11;
                boolean z13 = true;
                for (Map.Entry entry2 :
                        ((HashMap) DeveloperModeSettings.GLOBAL_SETTINGS_DEFAULT).entrySet()) {
                    z13 &=
                            Settings.Global.putString(
                                    developerModeSettings.mContext.getContentResolver(),
                                    (String) entry2.getKey(),
                                    (String) entry2.getValue());
                }
                boolean z14 = z12 & z13;
                boolean z15 = true;
                for (Map.Entry entry3 :
                        ((HashMap) DeveloperModeSettings.SECURE_SETTINGS_DEFAULT).entrySet()) {
                    z15 &=
                            Settings.Secure.putString(
                                    developerModeSettings.mContext.getContentResolver(),
                                    (String) entry3.getKey(),
                                    (String) entry3.getValue());
                }
                boolean z16 = z14 & z15;
                DeveloperModeSettings.resetSystemProperties();
                new DeveloperModeSettings.SystemPropPoker().execute(new Void[0]);
                ApplicationRestrictionsManager applicationRestrictionsManager =
                        ApplicationRestrictionsManager.getInstance(developerModeSettings.mContext);
                if (applicationRestrictionsManager != null
                        && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                    try {
                        ActivityManagerNative.getDefault()
                                .forceStopPackage(
                                        KnoxCustomManagerService.SETTING_PKG_NAME,
                                        developerModeSettings.mContext.getUserId());
                    } catch (RemoteException e4) {
                        Log.w(
                                "DeveloperModeSettings",
                                "killSettings: RemoteException ex -> " + e4.getMessage());
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Slog.d("DeveloperModeSettings", "allowDeveloperMode: false");
                z2 = z16;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowDeveloperMode");
        this.mRestrictionCache.update(
                "allowDeveloperMode",
                64L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "RestrictionPolicy",
                String.format(
                        z
                                ? "Admin %d has allowed developer mode."
                                : "Admin %d has disallowed developer mode.",
                        Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        return putBoolean && z2;
    }

    public final boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceAdvancedRestrictionPermission =
                enforceAdvancedRestrictionPermission(contextInfo);
        int i = enforceAdvancedRestrictionPermission.mCallerUid;
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceAdvancedRestrictionPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                boolean putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceAdvancedRestrictionPermission.mCallerUid,
                                z,
                                0,
                                "allowFaceRecognitionEvenCameraBlocked");
                try {
                    this.mRestrictionCache.update(
                            "allowFaceRecognitionEvenCameraBlocked",
                            2305843009213693952L,
                            false,
                            callingOrCurrentUserId,
                            Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid),
                            Boolean.valueOf(z));
                    if (putBoolean) {
                        setFaceRecognitionEvenCameraBlockedAllowedSystemUI(
                                0,
                                isFaceRecognitionAllowedEvenCameraBlocked(
                                        enforceAdvancedRestrictionPermission));
                        AuditLog.logEventAsUser(
                                callingOrCurrentUserId,
                                z ? 34 : 35,
                                new Object[] {
                                    Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid)
                                });
                    }
                    return putBoolean;
                } catch (Exception e) {
                    e = e;
                    z2 = putBoolean;
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean allowFactoryReset(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean extract = this.mRestrictionCache.extract(0, 2251799813685248L, true);
        Slog.d("RestrictionPolicy", "allowFactoryReset : oldState - " + extract + ", allow - " + z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "factoryresetallowed");
        this.mRestrictionCache.update(
                "factoryresetallowed",
                2251799813685248L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            if (extract == this.mRestrictionCache.extract(0, 2251799813685248L, true)) {
                Slog.d("RestrictionPolicy", "allowFactoryReset : do not need to update");
                return true;
            }
            manageEFSFile("/efs/MDM/FactoryReset", z);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowFactoryReset : ", "RestrictionPolicy", putBoolean);
        return putBoolean;
    }

    public final boolean allowFastEncryption(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowFastEncryption");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                " allowFastEncryption : ", "RestrictionPolicy", z);
        this.mRestrictionCache.update(
                "allowFastEncryption",
                16L,
                false,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission$1 =
                enforceOwnerOnlyAndAdvancedRestrictionPermission$1(contextInfo);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                " allowFirmwareAutoUpdate : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid,
                        z,
                        0,
                        "allowFirmwareAutoUpdate");
        this.mRestrictionCache.update(
                "allowFirmwareAutoUpdate",
                67108864L,
                false,
                0,
                Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "SOFTWARE_UPDATE_WIFI_ONLY2",
                    isFirmwareAutoUpdateAllowed(
                                    enforceOwnerOnlyAndAdvancedRestrictionPermission$1, false)
                            ? 1
                            : 0);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean;
    }

    public final boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean extract = this.mRestrictionCache.extract(0, 16777216L, true);
        Slog.d(
                "RestrictionPolicy",
                "allowFirmwareRecovery :oldState - " + extract + ", allow - " + z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "firmwarerecoveryallowed");
        this.mRestrictionCache.update(
                "firmwarerecoveryallowed",
                16777216L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            if (extract == this.mRestrictionCache.extract(0, 16777216L, true)) {
                Slog.d("RestrictionPolicy", "allowFirmwareRecovery : do not need to update");
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!writeData(z)) {
                    Slog.d("RestrictionPolicy", "allowFirmwareRecovery : write fail");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowFirmwareRecovery : ", "RestrictionPolicy", putBoolean);
        return putBoolean;
    }

    public final boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) {
        int i;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        if (!z && isGoogleAccountsAutoSyncAllowed(enforceRestrictionPermission)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Account[] accountsByTypeAsUser =
                    AccountManager.get(this.mContext)
                            .getAccountsByTypeAsUser("com.google", new UserHandle(userId));
            if (accountsByTypeAsUser.length != 0) {
                IContentService contentService = ContentResolver.getContentService();
                try {
                    SyncAdapterType[] syncAdapterTypesAsUser =
                            contentService.getSyncAdapterTypesAsUser(userId);
                    for (Account account : accountsByTypeAsUser) {
                        for (SyncAdapterType syncAdapterType : syncAdapterTypesAsUser) {
                            if ("com.google".equals(syncAdapterType.accountType)
                                    && contentService.getSyncAutomaticallyAsUser(
                                            account, syncAdapterType.authority, userId)) {
                                contentService.setSyncAutomaticallyAsUser(
                                        account, syncAdapterType.authority, false, userId);
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.w("RestrictionPolicy", "Failed to get content service " + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowGoogleAccountsAutoSync");
        if (putBoolean) {
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                i = userId;
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has enabled Autosync of Google account."
                                        : "Admin %d has disabled Autosync of Google account.",
                                Integer.valueOf(enforceRestrictionPermission.mCallerUid)),
                        userId);
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
                throw th;
            }
        } else {
            i = userId;
        }
        this.mRestrictionCache.update(
                "allowGoogleAccountsAutoSync",
                33554432L,
                true,
                i,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "googleCrashReportEnabled");
        this.mRestrictionCache.update(
                "googleCrashReportEnabled",
                36028797018963968L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(
                        "allowGoogleCrashReport : allow=", " callingUid=", z),
                enforceRestrictionPermission.mCallerUid,
                "RestrictionPolicy");
        return putBoolean;
    }

    public final boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceAdvancedRestrictionPermission =
                enforceAdvancedRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceAdvancedRestrictionPermission.mCallerUid);
        Slog.d("RestrictionPolicy", "allowIntelligenceOnlineProcessing(" + userId + ") : " + z);
        if (isIntelligenceOnlineProcessingAllowed(enforceAdvancedRestrictionPermission) && !z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    z2 =
                            Settings.System.putIntForUser(
                                    this.mContext.getContentResolver(),
                                    "prevent_online_processing",
                                    1,
                                    userId);
                } catch (Exception e) {
                    Log.e("RestrictionPolicy", "turnOffOnlineProcessing() failed. ", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z2 = false;
                }
                if (!z2) {
                    return false;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!this.mEdmStorageProvider.putBoolean(
                "RESTRICTION",
                enforceAdvancedRestrictionPermission.mCallerUid,
                z,
                0,
                "allowIntelligenceOnlineProcessing")) {
            return false;
        }
        this.mRestrictionCache.update(
                "allowIntelligenceOnlineProcessing",
                35184372088832L,
                true,
                UserHandle.getUserId(enforceAdvancedRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return true;
    }

    public final boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean z2 = true;
        if (!z) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManagerNative.getDefault().setAlwaysFinish(z);
                if (Settings.System.getInt(
                                this.mContext.getContentResolver(), "always_finish_activities", 0)
                        != 0) {
                    z2 = false;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (RemoteException e) {
                Slog.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                z2 = false;
            }
        }
        if (!z2) {
            return z2;
        }
        boolean putBoolean =
                z2
                        & this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                                z,
                                0,
                                "allowKeepActivities");
        this.mRestrictionCache.update(
                "allowKeepActivities",
                32768L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceAdvancedRestrictionPermission =
                enforceAdvancedRestrictionPermission(contextInfo);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "allowLocalContactStorage : ", "RestrictionPolicy", z);
        int i = enforceAdvancedRestrictionPermission.mCallerUid;
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceAdvancedRestrictionPermission);
        boolean z2 = false;
        try {
            boolean putBoolean =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceAdvancedRestrictionPermission.mCallerUid,
                            z,
                            0,
                            "allowLocalContactStorage");
            try {
                this.mRestrictionCache.update(
                        "allowLocalContactStorage",
                        4611686018427387904L,
                        true,
                        callingOrCurrentUserId,
                        Integer.valueOf(enforceAdvancedRestrictionPermission.mCallerUid),
                        Boolean.valueOf(z));
                if (putBoolean) {
                    SecContentProviderUtil.notifyPolicyChangesAsUser(
                            this.mContext,
                            Uri.parse(
                                    "content://com.sec.knox.provider/RestrictionPolicy/isLocalContactStorageAllowed"),
                            callingOrCurrentUserId);
                }
                Log.d(
                        "RestrictionPolicy",
                        "Allow local contact storage state input DB : " + putBoolean);
                return putBoolean;
            } catch (Exception e) {
                e = e;
                z2 = putBoolean;
                Log.w("RestrictionPolicy", "Allow local contact storage state write fail");
                e.printStackTrace();
                return z2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
            } catch (InvalidParameterException e) {
                Log.d(
                        "RestrictionPolicy",
                        "allowLockScreenView() failed: INVALID PARAMETER INPUT",
                        e);
            } catch (Exception e2) {
                Log.e(
                        "RestrictionPolicy",
                        "allowLockScreenView() failed: unexpected exception",
                        e2);
            }
            if (i != 1 && i != 2) {
                throw new InvalidParameterException();
            }
            boolean lockScreenViewProperty =
                    isLockScreenViewAllowed(enforceOwnerOnlyAndRestrictionPermission, i)
                            ? setLockScreenViewProperty(i, z)
                            : false;
            try {
                i2 =
                        this.mEdmStorageProvider.getInt(
                                enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                                0,
                                "RESTRICTION",
                                "allowLockScreenViews");
            } catch (SettingNotFoundException unused) {
                Log.d(
                        "RestrictionPolicy",
                        "allowLockScreenView() failed: cannot get value from edmstorageprovider.");
                i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            z2 =
                    this.mEdmStorageProvider.putInt(
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            0,
                            true == z ? i2 | i : (~i) & i2,
                            "RESTRICTION",
                            "allowLockScreenViews");
            if (z2
                    && !lockScreenViewProperty
                    && isLockScreenViewAllowed(enforceOwnerOnlyAndRestrictionPermission, i)) {
                setLockScreenViewProperty(i, z);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "allowOTAUpgrade : " + z);
        boolean isOTAUpgradeAllowed = isOTAUpgradeAllowed(null);
        this.mEdmStorageProvider.putBoolean(
                "RESTRICTION",
                enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                z,
                0,
                "OTAUpgradeEnabled");
        this.mRestrictionCache.update(
                "OTAUpgradeEnabled",
                18014398509481984L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        boolean isOTAUpgradeAllowed2 = isOTAUpgradeAllowed(null);
        if (isOTAUpgradeAllowed2 == isOTAUpgradeAllowed || isOTAUpgradeAllowed) {
            return true;
        }
        if (this.mAppPolicy == null) {
            this.mAppPolicy =
                    (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        this.mAppPolicy.setApplicationState(
                enforceOwnerOnlyAndRestrictionPermission,
                "com.sec.android.fotaclient",
                isOTAUpgradeAllowed2);
        if (this.mAppPolicy == null) {
            this.mAppPolicy =
                    (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        this.mAppPolicy.setApplicationState(
                enforceOwnerOnlyAndRestrictionPermission, "com.wssyncmldm", isOTAUpgradeAllowed2);
        if (this.mAppPolicy == null) {
            this.mAppPolicy =
                    (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        this.mAppPolicy.setApplicationState(
                enforceOwnerOnlyAndRestrictionPermission, "com.ws.dm", isOTAUpgradeAllowed2);
        return true;
    }

    public final boolean allowPowerOff(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        if (callingOrCurrentUserId != 0) {
            Log.w("RestrictionPolicy", "Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION", enforceHwPermission.mCallerUid, z, 0, "powerOffAllowed");
        this.mRestrictionCache.update(
                "powerOffAllowed",
                2048L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceHwPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            manageEFSFile("efs/MDM/PowerOff", z);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean z2 = !z;
            try {
                MaintenanceModeUtils.setDisallowedSetting(z2);
                Log.i("RestrictionPolicy", "setMaintenanceModeDisallowedSetting - value = " + z2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public final boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowPowerSavingMode");
        this.mRestrictionCache.update(
                "allowPowerSavingMode",
                1152921504606846976L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowPowerSavingMode : ", "RestrictionPolicy", z);
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (getCurrentPowerSavingMode() == 1) {
                        Slog.d("RestrictionPolicy", "allowPowerSavingMode : Power Saving Mode off");
                        Settings.System.putInt(
                                this.mContext.getContentResolver(),
                                KnoxCustomManagerService.POWER_SAVING_SWITCH,
                                0);
                        Settings.Global.putInt(this.mContext.getContentResolver(), "low_power", 0);
                    } else if (getCurrentPowerSavingMode() != 0) {
                        Slog.d("RestrictionPolicy", "allowPowerSavingMode : send intent to PMS");
                        Intent intent =
                                new Intent(
                                        "com.samsung.intent.action.EMERGENCY_START_SERVICE_BY_ORDER");
                        intent.putExtra("enabled", false);
                        intent.putExtra("flag", 512);
                        intent.putExtra("skipdialog", true);
                        intent.addFlags(268435456);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT);
                    }
                } catch (Exception e) {
                    Log.e("RestrictionPolicy", "allowPowerSavingMode() failed. ", e);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return putBoolean;
    }

    public final boolean allowSDCardMove(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)) {
            return false;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                " allowSDCardMove : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowSDCardMove");
        this.mRestrictionCache.update(
                "allowSDCardMove",
                536870912L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean isSDCardWriteAllowed = isSDCardWriteAllowed(null);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowSDCardWrite : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "sdCardWriteEnabled");
        this.mRestrictionCache.update(
                "sdCardWriteEnabled",
                34359738368L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (isSDCardWriteAllowed(null) != isSDCardWriteAllowed) {
                applySdCardWriteAccessPolicy();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean;
    }

    public final boolean allowSVoice(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean isSVoiceAllowed = isSVoiceAllowed(enforceRestrictionPermission, false);
        if (!z && isSVoiceAllowed) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    "wake_up_lock_screen",
                    0,
                    callingOrCurrentUserId);
            this.mEdmStorageProvider.putGenericValueAsUser(
                    callingOrCurrentUserId,
                    "voiceControl",
                    Integer.toString(
                            Settings.System.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "voice_input_control",
                                    0,
                                    callingOrCurrentUserId)));
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    "voice_input_control",
                    0,
                    callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowSVoice");
        this.mRestrictionCache.update(
                "allowSVoice",
                32L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (z) {
            rollBackSVoice(callingOrCurrentUserId);
        }
        if (putBoolean && isSVoiceAllowed && !z) {
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            ActivityManager activityManager =
                    (ActivityManager) this.mContext.getSystemService("activity");
            List<ActivityManager.RecentTaskInfo> recentTasks =
                    activityManager.getRecentTasks(12, 0);
            if (!recentTasks.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                    ComponentName component = recentTaskInfo.baseIntent.getComponent();
                    if (component != null) {
                        String packageName = component.getPackageName();
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                "packageName ", packageName, "RestrictionPolicy");
                        if (packageName != null
                                && (packageName.equals("com.vlingo.midas")
                                        || packageName.equals(
                                                "com.samsung.voiceserviceplatform"))) {
                            activityManager.semRemoveTask(recentTaskInfo.persistentId, 0);
                            try {
                                ActivityManagerNative.getDefault()
                                        .forceStopPackage(packageName, callingOrCurrentUserId);
                                break;
                            } catch (RemoteException e) {
                                ActivityManagerService$$ExternalSyntheticOutline0.m(
                                        e,
                                        new StringBuilder("Fail getting ActivityManager "),
                                        "RestrictionPolicy");
                                z2 = false;
                            }
                        }
                    }
                }
            }
            z2 = putBoolean;
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            putBoolean = z2;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m("allowSVoice : ", "RestrictionPolicy", z);
        if (putBoolean) {
            long clearCallingIdentity3 = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has allowed SVoice."
                                        : "Admin %d has disallowed SVoice.",
                                Integer.valueOf(enforceRestrictionPermission.mCallerUid)),
                        UserHandle.getUserId(enforceRestrictionPermission.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity3);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity3);
                throw th;
            }
        }
        return putBoolean;
    }

    public final boolean allowSafeMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowSafeMode : ", "RestrictionPolicy", z);
        if (!z && isSafeModeAllowed(null)) {
            setSafeModeProperty(z);
        }
        try {
            z2 =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            z,
                            0,
                            "allowSafeMode");
        } catch (Exception e) {
            Log.e("RestrictionPolicy", "allowSafeMode() : failed with error.", e);
            z2 = false;
        }
        if ((!z2 && !z && getUserRestrictionSafeMode())
                || (z2 && z && !getUserRestrictionSafeMode())) {
            setSafeModeProperty(isSafeModeAllowed(null));
        }
        return z2;
    }

    public final boolean allowScreenPinning(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        if (SemPersonaManager.isKnoxId(userId)) {
            return false;
        }
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(), "lock_to_app_enabled", 0, userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "screenPinningAllowed");
        this.mRestrictionCache.update(
                "screenPinningAllowed",
                144115188075855872L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean
                && !z
                && ((ActivityManager) this.mContext.getSystemService("activity"))
                        .isInLockTaskMode()) {
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

    public final boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) {
        PasswordPolicy passwordPolicy;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)) {
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowSettingsChanges");
        this.mRestrictionCache.update(
                "allowSettingsChanges",
                8L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowSettingsChanges : ", "RestrictionPolicy", z);
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityManager activityManager =
                        (ActivityManager) this.mContext.getSystemService("activity");
                List<ActivityManager.RecentTaskInfo> recentTasks =
                        activityManager.getRecentTasks(12, 0);
                if (!z && !recentTasks.isEmpty()) {
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                        ComponentName component = recentTaskInfo.baseIntent.getComponent();
                        if (component != null) {
                            String packageName = component.getPackageName();
                            Log.w("RestrictionPolicy", "packageName " + packageName);
                            if (packageName != null
                                    && packageName.equals(KnoxCustomManagerService.SETTING_PKG_NAME)
                                    && (passwordPolicy =
                                                    (PasswordPolicy)
                                                            EnterpriseService.getPolicyService(
                                                                    "password_policy"))
                                            != null
                                    && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                    callingOrCurrentUserId)
                                            == 0) {
                                activityManager.semRemoveTask(recentTaskInfo.persistentId, 0);
                                try {
                                    ActivityManagerNative.getDefault()
                                            .forceStopPackage(
                                                    KnoxCustomManagerService.SETTING_PKG_NAME,
                                                    callingOrCurrentUserId);
                                } catch (RemoteException e) {
                                    Log.e(
                                            "RestrictionPolicy",
                                            "Fail getting ActivityManager " + e.getMessage());
                                    putBoolean = false;
                                }
                            }
                        }
                    }
                }
                this.mContext.sendBroadcastAsUser(
                        new Intent(
                                        "com.samsung.android.knox.intent.action.ALLOW_SETTINGS_CHANGES_INTERNAL")
                                .addFlags(16777216),
                        new UserHandle(callingOrCurrentUserId));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                setSettingsChangeSystemUI(
                        callingOrCurrentUserId,
                        isSettingsChangesAllowed(enforceRestrictionPermission, false));
                SecContentProviderUtil.notifyPolicyChangesAllUser(
                        this.mContext,
                        Uri.parse(
                                "content://com.sec.knox.provider/RestrictionPolicy3/isSettingsChangesAllowed"));
                SecContentProviderUtil.notifyPolicyChangesAllUser(
                        this.mContext,
                        Uri.parse(
                                "content://com.sec.knox.provider/RestrictionPolicy/isSettingsChangesAllowed"));
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return putBoolean;
    }

    public final boolean allowShareList(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowShareList");
        this.mRestrictionCache.update(
                "allowShareList",
                1048576L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "allowShareList : allow=", " containerId=", z);
        m.append(enforceRestrictionPermission.mContainerId);
        m.append(" = callingUid=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                m, enforceRestrictionPermission.mCallerUid, "RestrictionPolicy");
        return putBoolean;
    }

    public final boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "smartClipAllowed");
        this.mRestrictionCache.update(
                "smartClipAllowed",
                72057594037927936L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        if (callingOrCurrentUserId != 0) {
            Log.w("RestrictionPolicy", "Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "statusBarExpansionEnabled");
        if (putBoolean) {
            this.mRestrictionCache.update(
                    "statusBarExpansionEnabled",
                    256L,
                    true,
                    callingOrCurrentUserId,
                    Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
            setStatusBarExpansionSystemUI(
                    callingOrCurrentUserId,
                    isStatusBarExpansionAllowedAsUser(false, callingOrCurrentUserId));
        }
        Log.d("RestrictionPolicy", "allowStatusBarExpansion : " + z + ", ret = " + putBoolean);
        return putBoolean;
    }

    public final boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceRestrictionPermission);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowStopSystemApp");
        this.mRestrictionCache.update(
                "allowStopSystemApp",
                4096L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowStopSystemApp : ", "RestrictionPolicy", z);
        return putBoolean;
    }

    public final boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isUsbHostStorageAllowed =
                isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowUsbHostStorage");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowUsbHostStorage : ", "RestrictionPolicy", z);
        this.mRestrictionCache.update(
                "allowUsbHostStorage",
                524288L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has allowed USB Host Storage."
                                    : "Admin %d has disallowed USB Host Storage.",
                            Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        if (putBoolean) {
            try {
                if (!z) {
                    try {
                        StorageManager storageManager =
                                (StorageManager) this.mContext.getSystemService("storage");
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
                                if ("mounted".equals(storageManager.getVolumeState(path))
                                        && mountService != null) {
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
            if (!updateUsbStorageStatebyIntent(
                    isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false))) {
                Log.d(
                        "RestrictionPolicy",
                        "allowUsbHostStorage() : failed to call updateUsbStorageStatebyIntent()");
            }
            if (!isUsbHostStorageAllowed
                    && isUsbHostStorageAllowed(enforceOwnerOnlyAndRestrictionPermission, false)) {
                Log.d(
                        "RestrictionPolicy",
                        "allowUsbHostStorage set true(false -> true). Sync UsbInterface Exception");
                int usbExceptionList = getUsbExceptionList();
                Log.d("RestrictionPolicy", "syncUsbExceptionList.");
                if (usbExceptionList == -1) {
                    Log.w("RestrictionPolicy", "syncUsbExceptionList. -1 -> return");
                } else {
                    try {
                        if (new File("sys/class/usb_notify/usb_control/whitelist_for_mdm")
                                .exists()) {
                            int usbDriverExceptionList = getUsbDriverExceptionList();
                            Log.d(
                                    "RestrictionPolicy",
                                    "UsbInterface Exception mask USB Driver = None( 0x"
                                            + Integer.toHexString(usbDriverExceptionList)
                                            + " )");
                            if (usbExceptionList == usbDriverExceptionList) {
                                Log.d(
                                        "RestrictionPolicy",
                                        "UsbExceptionList already applied : 0x"
                                                + Integer.toHexString(usbExceptionList));
                            } else {
                                setUsbExceptionListOnDriver(usbExceptionList);
                            }
                        } else {
                            Log.d("RestrictionPolicy", "usbDriverFile.exists : false");
                        }
                    } catch (Exception unused) {
                        Log.d("RestrictionPolicy", "usbDriverFile read fail");
                    }
                }
            }
        }
        return putBoolean;
    }

    public final boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowMobileDataLimit");
        this.mRestrictionCache.update(
                "allowMobileDataLimit",
                65536L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (z || !putBoolean) {
            return putBoolean;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            INetworkPolicyManager asInterface =
                    INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
            if (asInterface == null) {
                throw new NullPointerException("networkPolicyService is null");
            }
            ArrayList arrayList = new ArrayList();
            NetworkPolicy[] networkPolicies =
                    asInterface.getNetworkPolicies(this.mContext.getOpPackageName());
            if (networkPolicies != null && networkPolicies.length > 0) {
                for (NetworkPolicy networkPolicy : networkPolicies) {
                    networkPolicy.limitBytes = -1L;
                    arrayList.add(networkPolicy);
                }
            }
            if (arrayList.size() > 0) {
                asInterface.setNetworkPolicies(
                        (NetworkPolicy[]) arrayList.toArray(new NetworkPolicy[arrayList.size()]));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return putBoolean;
        } catch (Exception e) {
            e.printStackTrace();
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("RestrictionPolicy.allowUserMobileDataLimit() exception : "),
                    "RestrictionPolicy");
            return false;
        }
    }

    public final boolean allowVideoRecord(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION", enforceHwPermission.mCallerUid, z, 0, "allowVideoRecording");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowVideoRecord : ", "RestrictionPolicy", z);
        this.mRestrictionCache.update(
                "allowVideoRecording",
                2L,
                true,
                UserHandle.getUserId(enforceHwPermission.mCallerUid),
                Integer.valueOf(enforceHwPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean allowVpn(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "nativeVpnAllowed");
        this.mRestrictionCache.update(
                "nativeVpnAllowed",
                9007199254740992L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean && !z) {
            if (this.mVpnPolicy == null) {
                this.mVpnPolicy = (VpnInfoPolicy) EnterpriseService.getPolicyService("vpn_policy");
            }
            this.mVpnPolicy.disconnect();
        }
        Slog.d("RestrictionPolicy", "allowVpn():set :" + z + "ret:" + putBoolean);
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z ? "Admin %d has enabled Vpn." : "Admin %d has disabled Vpn.",
                                Integer.valueOf(
                                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return putBoolean;
    }

    public final boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "wallpaperEnabled");
        this.mRestrictionCache.update(
                "wallpaperEnabled",
                17179869184L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "allowWallpaperChange : ", "RestrictionPolicy", z);
        return putBoolean;
    }

    public final boolean allowWifiDirect(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        Log.i(
                "RestrictionPolicy",
                "allowWifiDirect - caller uid: "
                        + enforceOwnerOnlyAndRestrictionPermission.mCallerUid
                        + ", allow: "
                        + z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowWifiDirect");
        if (!putBoolean) {
            Log.e("RestrictionPolicy", "allowWifiDirect - fail to store value to database");
            return putBoolean;
        }
        this.mRestrictionCache.update(
                "allowWifiDirect",
                8192L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    z ? 57 : 58,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)
                    });
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setWifiDirectUserRestriction(!isWifiDirectAllowed(null, false));
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void applyBackgroundDataRestriction() {
        INetworkPolicyManager asInterface;
        List users;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.w("RestrictionPolicy", "Applying Background Data Policy");
                asInterface =
                        INetworkPolicyManager.Stub.asInterface(
                                ServiceManager.getService("netpolicy"));
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
                    asInterface.setNetworkPolicies(
                            (NetworkPolicy[])
                                    arrayList.toArray(new NetworkPolicy[arrayList.size()]));
                }
            } else if (!isDataSavingAllowed) {
                asInterface.setRestrictBackground(false);
                PackageManager packageManager = this.mContext.getPackageManager();
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                if (userManager != null && (users = userManager.getUsers()) != null) {
                    Iterator it = users.iterator();
                    while (it.hasNext()) {
                        Iterator it2 =
                                packageManager
                                        .getInstalledPackagesAsUser(64, ((UserInfo) it.next()).id)
                                        .iterator();
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
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void applySdCardWriteAccessPolicy() {
        String externalSdCardPath;
        Utils.writePropertyValue(
                "sdCardWriteAccessBlocked",
                isSDCardWriteAllowed(null) ? "0" : "1",
                "/data/system/enterprise.conf");
        ((StorageManagerAdapter)
                        ((IStorageManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IStorageManagerAdapter.class)))
                .getClass();
        if (StorageManagerAdapter.mStorageManager.getVolumeList().length < 2
                || !isSdCardEnabled(null)) {
            return;
        }
        if (this.mStorageManager == null) {
            this.mStorageManager = (StorageManager) this.mContext.getSystemService("storage");
        }
        this.mStorageManager.registerListener(this.mStorageListener);
        IStorageManager mountService = getMountService();
        if (mountService == null || (externalSdCardPath = getExternalSdCardPath()) == null) {
            return;
        }
        try {
            mountService.unmountVolume(externalSdCardPath, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean checkAdminActivationEnabled(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            if (isNewAdminActivationEnabledInternal(i, false)
                    || isPackageOnExclusionList(getUidForAdminActivation(i), str)
                    || packageManager.checkPermission(
                                    "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION",
                                    str,
                                    i)
                            == 0) {
                return true;
            }
            RestrictionToastManager.show(17042601);
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean checkIfRestrictionWasSetByKC(String str) {
        if (Binder.getCallingUid() == Process.myUid()) {
            return this.mUserRestrictionEnforcedByKC.contains(str);
        }
        return false;
    }

    public final boolean checkMultiSimPolicyPermission(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "checkMultiSimPolicyPermission uid ", "MultiSimPolicy");
        try {
            int i2 = enforceRestrictionPermission(new ContextInfo(i)).mCallerUid;
            Log.d(
                    "MultiSimPolicy",
                    "checkMultiSimPolicyPermission enforceRestrictionPermission uid " + i2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    try {
                        PackageManager packageManager = this.mContext.getPackageManager();
                        for (String str : packageManager.getPackagesForUid(i2)) {
                            if (str == null
                                    || packageManager.checkPermission(
                                                    "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT",
                                                    str)
                                            != 0) {
                                Log.d(
                                        "MultiSimPolicy",
                                        "checkPermission RESTRICTION_PERMISSION returns false");
                                SharedPreferences.Editor edit =
                                        PreferenceManager.getDefaultSharedPreferences(this.mContext)
                                                .edit();
                                edit.putString("knox_multisim_policy", "");
                                edit.apply();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return false;
                            }
                            Log.d(
                                    "MultiSimPolicy",
                                    "checkPermission RESTRICTION_PERMISSION returns true");
                        }
                        String str2 = SystemProperties.get("ro.organization_owned");
                        if (!"true".equals(str2)) {
                            Log.d(
                                    "MultiSimPolicy",
                                    "checkMultiSimPolicyPermission organizationOwnedDevice "
                                            + str2);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                        String packageNameForUid$6 = getPackageNameForUid$6(i2);
                        if (packageNameForUid$6 == null) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                        ComponentName deviceOwnerComponentOnAnyUser =
                                getDevicePolicyManager$2().getDeviceOwnerComponentOnAnyUser();
                        boolean z =
                                deviceOwnerComponentOnAnyUser != null
                                        && packageNameForUid$6.equals(
                                                deviceOwnerComponentOnAnyUser.getPackageName());
                        ComponentName profileOwnerAsUser =
                                getDevicePolicyManager$2()
                                        .getProfileOwnerAsUser(UserHandle.getUserId(i2));
                        boolean z2 =
                                profileOwnerAsUser != null
                                        && packageNameForUid$6.equals(
                                                profileOwnerAsUser.getPackageName());
                        Log.d("MultiSimPolicy", "checkMultiSimPolicyPermission isDeviceOwner " + z);
                        Log.d(
                                "MultiSimPolicy",
                                "checkMultiSimPolicyPermission isProfileOwner " + z2);
                        if (!z && !z2) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Log.d("MultiSimPolicy", "checkMultiSimPolicyPermission returns true ");
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public final boolean checkPackageSource(int i, String str) {
        String packageNameForUid$6;
        return (str == null
                        || (packageNameForUid$6 =
                                        getPackageNameForUid$6(getUidForAdminInstallation(i)))
                                == null
                        || !packageNameForUid$6.equals(str))
                ? false
                : true;
    }

    public final boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
        return this.mEdmStorageProvider.putString(
                getEDM$25()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_APP_MGMT")))
                        .mCallerUid,
                0,
                "RESTRICTION",
                "preventAdminActivationWhiteList",
                "");
    }

    public final boolean clearSelectiveFota() {
        int delete = this.mEdmStorageProvider.delete("SelectiveFotaTable", null);
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                delete, "clearSelectiveFota : return( ", " )", "RestrictionPolicy");
        return delete >= 0;
    }

    public final boolean disableConstrainedState(ContextInfo contextInfo) {
        return this.mConstrainedState.disableConstrainedState(
                getEDM$25()
                        .enforceOwnerOnlyAndActiveAdminPermission(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")))
                        .mCallerUid);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Restriction Policy");
            return;
        }
        StringBuilder sb = new StringBuilder("[Restriction Policy]");
        sb.append(System.lineSeparator());
        RestrictionPolicyCache restrictionPolicyCache = this.mRestrictionCache;
        RestrictionPolicyCache.ApplyingAdmins applyingAdmins =
                restrictionPolicyCache.mApplyingAdmins;
        StringBuilder sb2 = new StringBuilder();
        restrictionPolicyCache.mLock.readLock().lock();
        try {
            sb2.append("[Admin Info : ");
            StringBuilder sb3 = new StringBuilder();
            Map map = applyingAdmins.adminInfoMap;
            if (map != null && !((HashMap) map).isEmpty()) {
                sb3.append(applyingAdmins.adminInfoMap.toString());
            }
            sb2.append(sb3.toString());
            sb2.append("]");
            sb2.append(System.lineSeparator());
            Iterator it = restrictionPolicyCache.mCache.keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                sb2.append("[Restrictions applied for user : ");
                sb2.append(intValue);
                sb2.append("]");
                sb2.append(System.lineSeparator());
                long longValue = restrictionPolicyCache.getCachedPolicies(intValue).longValue();
                for (Map.Entry entry :
                        ((HashMap) RestrictionPolicyCache.MASK_AND_COLUMN_NAME).entrySet()) {
                    Long l = (Long) entry.getKey();
                    long longValue2 = l.longValue();
                    sb2.append("   ");
                    sb2.append((String) entry.getValue());
                    sb2.append(": ");
                    long j = longValue & longValue2;
                    sb2.append(j == longValue2);
                    if (j != (l.longValue() & 6917529011461554159L)) {
                        sb2.append("(Enforced) ");
                    }
                    sb2.append(applyingAdmins.dump(intValue, longValue2));
                    sb2.append(System.lineSeparator());
                }
            }
            restrictionPolicyCache.mLock.readLock().unlock();
            sb.append(sb2.toString());
            sb.append("   UsbExceptionList: 0x" + Integer.toHexString(getUsbExceptionList()));
            sb.append(System.lineSeparator());
            printWriter.write(sb.toString());
        } catch (Throwable th) {
            restrictionPolicyCache.mLock.readLock().unlock();
            throw th;
        }
    }

    public final boolean enableConstrainedState(
            ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
        return this.mConstrainedState.enableConstrainedState(
                getEDM$25()
                        .enforceOwnerOnlyAndActiveAdminPermission(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")))
                        .mCallerUid,
                str,
                str2,
                str3,
                str4,
                i);
    }

    public final boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission$1 =
                enforceOwnerOnlyAndAdvancedRestrictionPermission$1(contextInfo);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "enableODETrustedBootVerification  : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid,
                        z,
                        0,
                        "ODETrustedBootVerification");
        this.mRestrictionCache.update(
                "ODETrustedBootVerification",
                2147483648L,
                false,
                0,
                Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid);
        Slog.d("RestrictionPolicy", "enableGearPolicy() : userId = " + userId + ", enable = " + z);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        if (SemPersonaManager.isKnoxId(userId)) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    userId,
                    "enableGearPolicy() : this api doesn't support multi-user. userId = ",
                    "RestrictionPolicy");
            return false;
        }
        if (i != 1) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i,
                    "enableGearPolicy() : this api called unsupport device. device = ",
                    "RestrictionPolicy");
            return false;
        }
        try {
            i2 =
                    this.mEdmStorageProvider.getInt(
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            0,
                            "RESTRICTION",
                            "wearablePolicyEnabled");
        } catch (SettingNotFoundException unused) {
            i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
        }
        if (!this.mEdmStorageProvider.putInt(
                enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                0,
                true == z ? (~i) & i2 : i | i2,
                "RESTRICTION",
                "wearablePolicyEnabled")) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mContext.sendBroadcastAsUser(
                        new Intent(
                                        "com.samsung.android.knox.intent.action.GEARPOLICY_ENABLE_INTERNAL")
                                .addFlags(16777216),
                        new UserHandle(userId));
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "sendIntentGearManagerforUpdate() fas failed.", e);
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ContextInfo enforceAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM$25()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public final ContextInfo enforceHwPermission(ContextInfo contextInfo) {
        return getEDM$25()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
    }

    public final ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission$1(
            ContextInfo contextInfo) {
        return getEDM$25()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public final ContextInfo enforceOwnerOnlyAndRestrictionPermission(ContextInfo contextInfo) {
        return getEDM$25()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndTetheringPermission(ContextInfo contextInfo) {
        return getEDM$25()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    public final ContextInfo enforceRestrictionPermission(ContextInfo contextInfo) {
        return getEDM$25()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT")));
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x005d, code lost:

       if (r5 != null) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:

       r5.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:

       android.util.Log.i("RestrictionPolicy", "evaluateAndMigrateWifiRelatedPolicyAPIs - END");
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006f, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006c, code lost:

       if (r5 == null) goto L28;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void evaluateAndMigrateWifiRelatedPolicyAPIs() {
        /*
            r9 = this;
            java.lang.String r0 = "evaluateAndMigrateWifiRelatedPolicyAPIs - END"
            int r1 = android.os.Binder.getCallingPid()
            int r2 = android.os.Process.myPid()
            if (r1 != r2) goto L79
            java.lang.String r1 = "RestrictionPolicy"
            java.lang.String r2 = "evaluateAndMigrateWifiRelatedPolicyAPIs - START"
            android.util.Log.i(r1, r2)
            java.lang.String r2 = "wifiTetheringEnabled"
            java.lang.String r3 = "allowWifiDirect"
            java.lang.String[] r4 = new java.lang.String[]{r2, r3}
            r5 = 0
            com.android.server.enterprise.storage.EdmStorageProvider r6 = r9.mEdmStorageProvider     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            java.lang.String r7 = "RESTRICTION"
            android.database.Cursor r5 = r6.getCursor(r7, r4, r5)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            if (r5 == 0) goto L5d
            int r4 = r5.getCount()     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            if (r4 <= 0) goto L5d
            r4 = 0
            r6 = r4
        L31:
            boolean r7 = r5.moveToNext()     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            r8 = 1
            if (r7 == 0) goto L53
            int r7 = r5.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            int r7 = r5.getInt(r7)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            if (r7 != 0) goto L43
            r4 = r8
        L43:
            int r7 = r5.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            int r7 = r5.getInt(r7)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            if (r7 != 0) goto L31
            r6 = r8
            goto L31
        L4f:
            r9 = move-exception
            goto L70
        L51:
            r9 = move-exception
            goto L66
        L53:
            if (r4 == 0) goto L58
            r9.setWifiTetheringUserRestriction(r8)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
        L58:
            if (r6 == 0) goto L5d
            r9.setWifiDirectUserRestriction(r8)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
        L5d:
            if (r5 == 0) goto L62
        L5f:
            r5.close()
        L62:
            android.util.Log.i(r1, r0)
            goto L6f
        L66:
            java.lang.String r2 = "evaluateAndMigrateWifiRelatedPolicyAPIs"
            android.util.Log.e(r1, r2, r9)     // Catch: java.lang.Throwable -> L4f
            if (r5 == 0) goto L62
            goto L5f
        L6f:
            return
        L70:
            if (r5 == 0) goto L75
            r5.close()
        L75:
            android.util.Log.i(r1, r0)
            throw r9
        L79:
            java.lang.SecurityException r9 = new java.lang.SecurityException
            java.lang.String r0 = "Can only be called by system process"
            r9.<init>(r0)
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.evaluateAndMigrateWifiRelatedPolicyAPIs():void");
    }

    public final List getAllowedFOTAInfo(ContextInfo contextInfo) {
        Log.d("RestrictionPolicy", "getAllowedFOTAInfo");
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", (Integer) 0);
        ArrayList arrayList2 =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesList(
                                "SelectiveFotaTable",
                                new String[] {"adminUid", "corpid", "version"},
                                contentValues);
        if (arrayList2.isEmpty()) {
            Log.d("RestrictionPolicy", "getAllowedFOTAInfo: cursor is null");
        } else {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2 != null && contentValues2.size() > 0) {
                    arrayList.add(contentValues2.getAsString("corpid"));
                    arrayList.add(contentValues2.getAsString("version"));
                    Slog.d(
                            "RestrictionPolicy",
                            "getAllowedFOTAInfo : corpID = "
                                    + contentValues2.getAsString("corpid")
                                    + " version = "
                                    + contentValues2.getAsString("version"));
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public final String getAllowedFOTAVersion(ContextInfo contextInfo) {
        List allowedFOTAInfo = getAllowedFOTAInfo(contextInfo);
        if (allowedFOTAInfo == null || allowedFOTAInfo.isEmpty()) {
            return null;
        }
        return (String) allowedFOTAInfo.get(1);
    }

    public final int getCCModeState(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 1073741824L, false);
        int updateMdfStatus =
                isCCModeSupported(contextInfo, false) ? MdfUtils.updateMdfStatus() : -1;
        Slog.d(
                "RestrictionPolicy",
                "getCCModeState : mdm ret = " + extract + ", MdfUtils ret = : " + updateMdfStatus);
        return updateMdfStatus;
    }

    public final int getConstrainedState() {
        return this.mConstrainedState.getConstrainedState();
    }

    public final int getCurrentPowerSavingMode() {
        int i = 0;
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "ultra_powersaving_mode", 0)
                != 0) {
            i = 2;
        } else if (z) {
            i = 1;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "getCurrentPowerSavingMode : ", "RestrictionPolicy");
        return i;
    }

    public final DevicePolicyManager getDevicePolicyManager$2() {
        if (this.mDpm == null) {
            this.mDpm = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }
        return this.mDpm;
    }

    public final EnterpriseDeviceManager getEDM$25() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final String getKcActionDisabledText() {
        return this.mContext.getString(R.string.capability_desc_canCaptureFingerprintGestures);
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

    public final String getMultiSimPolicy() {
        try {
            return PreferenceManager.getDefaultSharedPreferences(this.mContext)
                    .getString("knox_multisim_policy", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final List getNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$25()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_APP_MGMT")));
        ArrayList arrayList = new ArrayList();
        String string =
                this.mEdmStorageProvider.getString(
                        enforceActiveAdminPermissionByContext.mCallerUid,
                        0,
                        "RESTRICTION",
                        "preventAdminActivationWhiteList");
        if (TextUtils.isEmpty(string)) {
            arrayList.add("");
        } else {
            arrayList.addAll(Arrays.asList(string.split(",")));
        }
        return arrayList;
    }

    public final String getPackageNameForUid$6(int i) {
        String string;
        if (i == 1000
                || (string = this.mEdmStorageProvider.getString(i, 0, "ADMIN_INFO", "adminName"))
                        == null) {
            return null;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
        return unflattenFromString == null ? string : unflattenFromString.getPackageName();
    }

    public final PhoneAccountHandle getPhoneAccountForSubId(int i) {
        if (this.mTelecomManager == null) {
            this.mTelecomManager =
                    (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
        }
        List<PhoneAccountHandle> callCapablePhoneAccounts =
                this.mTelecomManager.getCallCapablePhoneAccounts();
        if (callCapablePhoneAccounts == null || callCapablePhoneAccounts.isEmpty()) {
            return null;
        }
        for (PhoneAccountHandle phoneAccountHandle : callCapablePhoneAccounts) {
            if (this.mTelecomManager == null) {
                this.mTelecomManager =
                        (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
            }
            PhoneAccount phoneAccount = this.mTelecomManager.getPhoneAccount(phoneAccountHandle);
            if (phoneAccount != null) {
                if (this.mTelephonyManager == null) {
                    this.mTelephonyManager =
                            (TelephonyManager)
                                    this.mContext.getSystemService(TelephonyManager.class);
                }
                if (i == this.mTelephonyManager.getSubIdForPhoneAccount(phoneAccount)) {
                    return phoneAccountHandle;
                }
            }
        }
        return null;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final Set getRestrictedKeyCodes() {
        if (isHomeKeyEnabled(null, false)) {
            return null;
        }
        return new HashSet(Arrays.asList(3));
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final String getServiceName() {
        return "RestrictionPolicy";
    }

    public final SubscriptionManager getSubScriptionManager() {
        if (this.mSubscriptionManager == null) {
            this.mSubscriptionManager =
                    (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        }
        return this.mSubscriptionManager;
    }

    public final int getUidForAdminActivation(int i) {
        Iterator it =
                this.mEdmStorageProvider
                        .getIntListAsUser(0, i, "RESTRICTION", "adminUid")
                        .iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            try {
                if (this.mEdmStorageProvider.getBoolean(
                        num.intValue(), 0, "RESTRICTION", "preventAdminActivation")) {
                    return num.intValue();
                }
            } catch (SettingNotFoundException e) {
                Slog.v("RestrictionPolicy", "Admin not found on database ", e);
            }
        }
        return -1;
    }

    public final int getUidForAdminInstallation(int i) {
        Iterator it =
                this.mEdmStorageProvider
                        .getIntListAsUser(0, i, "RESTRICTION", "adminUid")
                        .iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            try {
                if (this.mEdmStorageProvider.getBoolean(
                        num.intValue(), 0, "RESTRICTION", "preventAdminInstallation")) {
                    return num.intValue();
                }
            } catch (SettingNotFoundException e) {
                Slog.v("RestrictionPolicy", "Admin not found on database ", e);
            }
        }
        return -1;
    }

    public final int getUsbExceptionList() {
        Log.d("RestrictionPolicy", "getUsbExceptionList.");
        try {
            ArrayList arrayList = (ArrayList) getUsbInterfaceExceptionMaskListInDb();
            int i = -1;
            if (arrayList.isEmpty()) {
                return -1;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                i &= ((Integer) it.next()).intValue();
            }
            Log.d(
                    "RestrictionPolicy",
                    "UsbInterface Exception mask MDM DB = 0x" + Integer.toHexString(i));
            File file = new File("sys/class/usb_notify/usb_control/whitelist_for_mdm");
            if (file.exists()) {
                Log.d(
                        "RestrictionPolicy",
                        "UsbInterface Exception mask USB Driver = "
                                + FileUtils.readTextFile(file, 0, null).trim());
            }
            return i;
        } catch (Exception e) {
            Log.w("RestrictionPolicy", "Failed get DB exception mask", e);
            return -1000;
        }
    }

    public final List getUsbInterfaceExceptionMaskListInDb() {
        Integer asInteger;
        List valuesList =
                this.mEdmStorageProvider.getValuesList(
                        "RESTRICTION", new String[] {"UsbExceptionMask"}, null);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) valuesList).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues != null
                    && (asInteger = contentValues.getAsInteger("UsbExceptionMask")) != null
                    && asInteger.intValue() > -1) {
                arrayList.add(asInteger);
            }
        }
        return arrayList;
    }

    public final boolean getUserRestrictionSafeMode() {
        PackageManagerAdapter.getInstance(this.mContext);
        boolean z = false;
        try {
            z =
                    ((UserManager) PackageManagerAdapter.mContext.getSystemService("user"))
                            .getUserRestrictions(new UserHandle(0))
                            .getBoolean("no_safe_boot", false);
        } catch (Exception e) {
            Log.d("PackageManagerAdapter", "getUserRestrictions() failed. (0, no_safe_boot)", e);
        }
        if (z) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "getUserRestrictions(0, no_safe_boot) = ", "PackageManagerAdapter", z);
        }
        return z;
    }

    public final boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 134217728L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17042590);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isActivationLockAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isAdminEnforcementAllowed(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String packageNameForUid$6 = getPackageNameForUid$6(i);
        try {
            try {
                if (this.mEdmService == null) {
                    this.mEdmService =
                            IEnterpriseDeviceManager.Stub.asInterface(
                                    ServiceManager.getService("enterprise_policy"));
                }
                IEnterpriseDeviceManager iEnterpriseDeviceManager = this.mEdmService;
                if (iEnterpriseDeviceManager != null) {
                    for (EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo :
                            iEnterpriseDeviceManager.getActiveAdminsInfo(i2)) {
                        Slog.d(
                                "RestrictionPolicy",
                                " preventAdminActivation packageName : "
                                        + enterpriseDeviceAdminInfo.getPackageName());
                        if (!enterpriseDeviceAdminInfo.getPackageName().equals(packageNameForUid$6)
                                && !isPackageOnExclusionList(
                                        i, enterpriseDeviceAdminInfo.getPackageName())) {
                            try {
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            if (AppGlobals.getPackageManager()
                                            .checkPermission(
                                                    "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION",
                                                    enterpriseDeviceAdminInfo.getPackageName(),
                                                    i2)
                                    != 0) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return false;
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                Log.w(
                        "RestrictionPolicy",
                        "RestrictionPolicy.preventAdminInstallation exception : ",
                        e2);
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAirplaneModeAllowed(boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 128L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.call_notification_answer_video_action);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isAirplaneModeAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract = this.mRestrictionCache.extract(callingOrCurrentUserId, 1L, true);
        Slog.d(
                "RestrictionPolicy",
                "userId: " + callingOrCurrentUserId + " isAudioRecordAllowed : " + extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042604);
        }
        return extract;
    }

    public final boolean isBackgroundDataEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 1125899906842624L, true);
        Slog.w("RestrictionPolicy", "isBackgroundDataEnabled : " + extract);
        return extract;
    }

    public final boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 16384L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isBackgroundProcessLimitAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isBackupAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 17592186044416L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.config_defaultDockManagerPackageName);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isBackupAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 549755813888L, true);
        boolean z = (extract && getEDM$25().isRestrictedByConstrainedState(16)) ? false : extract;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isBluetoothTetheringEnabled : ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 1073741824L, false);
        boolean isMdfEnforced = MdfUtils.isMdfEnforced();
        StringBuilder sb = new StringBuilder(" isCCModeEnabled:");
        sb.append(SystemProperties.get("security.mdf"));
        sb.append("    mdm ret1 :");
        sb.append(extract);
        sb.append("   ret :");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("RestrictionPolicy", sb, isMdfEnforced);
        return isMdfEnforced;
    }

    public final boolean isCCModeSupported(ContextInfo contextInfo, boolean z) {
        String str = SystemProperties.get("ro.security.mdf.ux");
        boolean z2 = str != null && "Enabled".equals(str);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isCCModeSupported : ", "RestrictionPolicy", z2);
        if (z && !z2) {
            RestrictionToastManager.show(17042605);
        }
        return z2;
    }

    public final boolean isCameraEnabled(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Long l =
                (Long)
                        this.mRestrictionCache.mCameraDisabledAdmin.get(
                                Integer.valueOf(Utils.getCallingOrCurrentUserId(contextInfo)));
        boolean z2 = false;
        if (l.longValue() != -1) {
            if (l.longValue() == 0) {
                if (!this.mRestrictionCache.extract(0, 68719476736L, true)) {
                    if (contextInfo.mContainerId > 0 && this.mAppPolicy != null) {
                        try {
                            Long l2 = (Long) this.mRestrictionCache.mCameraDisabledAdmin.get(0);
                            if (l2.longValue() > 0) {
                                int intValue = (contextInfo.mContainerId * 100000) + l2.intValue();
                                Log.d(
                                        "RestrictionPolicy",
                                        "checking for camera in ApplicationPolicy when camera"
                                            + " disabled in user 0 ");
                                z2 =
                                        this.mAppPolicy.isCameraAllowlistedApp(
                                                contextInfo.mCallerUid, intValue);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Slog.d(
                            "RestrictionPolicy",
                            "isCameraEnabledEx isCameraEnabledEx with OWNER return false");
                } else if (getEDM$25().isRestrictedByConstrainedState(1)) {
                    Slog.d(
                            "RestrictionPolicy",
                            "isCameraEnabledEx isRestrictedByConstrainedState return true");
                } else {
                    z2 = this.mRestrictionCache.extract(callingOrCurrentUserId, 68719476736L, true);
                }
            } else if (getEDM$25().isRestrictedByConstrainedState(1)) {
                Slog.d(
                        "RestrictionPolicy",
                        "isCameraEnabledEx isRestrictedByConstrainedState return true");
            } else {
                if (this.mAppPolicy != null) {
                    try {
                        Log.d("RestrictionPolicy", "checking for camera in ApplicationPolicy");
                        z2 =
                                this.mAppPolicy.isCameraAllowlistedApp(
                                        contextInfo.mCallerUid, l.intValue());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                z2 = true;
            }
        }
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_isoImagePath);
        }
        StringBuilder m =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        callingOrCurrentUserId,
                        "isCameraEnabled ret(",
                        ") userId(",
                        ") cxtInfo.mCallerUid(",
                        z2);
        m.append(contextInfo.mCallerUid);
        m.append(") cxtInfo.mContainerId(");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                m, contextInfo.mContainerId, ")", "RestrictionPolicy");
        return z2;
    }

    public final boolean isCellularDataAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 70368744177664L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isCellularDataAllowed: ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) {
        return isClipboardAllowedAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isClipboardAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(i, 512L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.config_usbConfirmActivity);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isClipboardAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isClipboardShareAllowed(ContextInfo contextInfo) {
        return isClipboardShareAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isClipboardShareAllowedAsUser(int i) {
        boolean extract = this.mRestrictionCache.extract(i, 131072L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isClipboardShareAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isDataSavingAllowed() {
        boolean extract = this.mRestrictionCache.extract(0, 576460752303423488L, true);
        Slog.w("RestrictionPolicy", "isDataSavingAllowed : " + extract);
        return extract;
    }

    public final boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 64L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17042606);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isDeveloperModeAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(
                Utils.getCallingOrCurrentUserId(contextInfo), 2305843009213693952L, false);
    }

    public final boolean isFactoryResetAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 2251799813685248L, true);
        boolean isExistEFSFile = isExistEFSFile("/efs/MDM/FactoryReset");
        Slog.d(
                "RestrictionPolicy",
                "isFactoryResetAllowed(): isExistEFSFile - " + isExistEFSFile + " DB - " + extract);
        if (isExistEFSFile == extract) {
            Slog.d(
                    "RestrictionPolicy",
                    "isFactoryResetAllowed(): need to sync DB(" + extract + ") and efs");
            manageEFSFile("/efs/MDM/FactoryReset", extract);
        }
        return extract;
    }

    public final boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 16L, false);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isFastEncryptionAllowed : ", "RestrictionPolicy", extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042593);
        }
        return extract;
    }

    public final boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 67108864L, false);
        if (z && !extract) {
            RestrictionToastManager.show(17042594);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isFirmwareAutoUpdateAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    /* JADX WARN: Finally extract failed */
    public final boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2 = true;
        boolean extract = this.mRestrictionCache.extract(0, 16777216L, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        byte[] readParamData = readParamData();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (readParamData == null) {
            Slog.d("RestrictionPolicy", "readParamData : fail to read");
        } else {
            Slog.d("RestrictionPolicy", "readParamData : " + Arrays.toString(readParamData));
            if (readParamData[30] == 2 && readParamData[31] == 6 && readParamData[7] == 8) {
                z2 = false;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "readParamData : index - 7 ret - ", "RestrictionPolicy", z2);
        }
        Slog.d(
                "RestrictionPolicy",
                "isFirmwareRecoveryAllowed(): ret -  " + extract + " param - " + z2);
        if (z2 != extract) {
            Slog.d(
                    "RestrictionPolicy",
                    "isFirmwareRecoveryAllowed(): need to sync DB(" + extract + ") and param");
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                writeData(extract);
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
                throw th;
            }
        }
        return extract;
    }

    public final boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) {
        return isGoogleAccountsAutoSyncAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        return this.mRestrictionCache.extract(i, 33554432L, true);
    }

    public final boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) {
        return isGoogleCrashReportAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isGoogleCrashReportAllowedAsUser(int i) {
        boolean extract = this.mRestrictionCache.extract(i, 36028797018963968L, true);
        Slog.d(
                "RestrictionPolicy",
                "isGoogleCrashReportAllowed : ret=" + extract + " userId =" + i);
        return extract;
    }

    public final boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 268435456L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17042595);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isHeadphoneEnabled : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 4503599627370496L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.minute);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isHomeKeyEnabled :", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isHomeKeyEnabledInDb() {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "RESTRICTION", "homeKeyEnabled")
                        .iterator();
        while (it.hasNext()) {
            if (!((Boolean) it.next()).booleanValue()) {
                Slog.d("RestrictionPolicy", "isHomeKeyEnabledInDb : false");
                return false;
            }
        }
        Slog.d("RestrictionPolicy", "isHomeKeyEnabledInDb : true");
        return true;
    }

    public final boolean isIntelligenceOnlineProcessingAllowed(int i) {
        boolean extract = this.mRestrictionCache.extract(i, 35184372088832L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isIntelligenceOnlineProcessingAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) {
        return isIntelligenceOnlineProcessingAllowed(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 288230376151711744L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.orgTypeWork);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isIrisCameraEnabledAsUser : ", "RestrictionPolicy", extract);
        return extract;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final boolean isKeyCodeInputAllowed(int i) {
        if (i == 3) {
            return isHomeKeyEnabled(null, false);
        }
        return true;
    }

    public final boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(0, 32768L, true);
    }

    public final boolean isKnoxDelegationEnabled(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(
                Utils.getCallingOrCurrentUserId(contextInfo), Long.MIN_VALUE, false);
    }

    public final boolean isLocalContactStorageAllowed(ContextInfo contextInfo) {
        Log.d("RestrictionPolicy", "isLocalContactStorageAllowed");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract =
                this.mRestrictionCache.extract(callingOrCurrentUserId, 4611686018427387904L, true);
        Log.d(
                "RestrictionPolicy",
                "isLocalContactStorageAllowed(" + callingOrCurrentUserId + ") : " + extract);
        return extract;
    }

    public final boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 8388608L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isLockScreenEnabled : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) {
        boolean z = false;
        if (i != 1 && i != 2) {
            try {
                throw new InvalidParameterException();
            } catch (InvalidParameterException e) {
                e = e;
                Log.d(
                        "RestrictionPolicy",
                        "isLockScreenViewAllowed() failed: INVALID PARAMETER INPUT",
                        e);
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "isLockScreenViewAllowed : ", "RestrictionPolicy", z);
                return z;
            } catch (Exception e2) {
                e = e2;
                Log.e(
                        "RestrictionPolicy",
                        "isLockScreenViewAllowed() failed: unexpected exception",
                        e);
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "isLockScreenViewAllowed : ", "RestrictionPolicy", z);
                return z;
            }
        }
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getIntListAsUser(0, 0, "RESTRICTION", "allowLockScreenViews")
                            .iterator();
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
            Log.d(
                    "RestrictionPolicy",
                    "isLockScreenViewAllowed() failed: INVALID PARAMETER INPUT",
                    e);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isLockScreenViewAllowed : ", "RestrictionPolicy", z);
            return z;
        } catch (Exception e4) {
            e = e4;
            z = true;
            Log.e("RestrictionPolicy", "isLockScreenViewAllowed() failed: unexpected exception", e);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isLockScreenViewAllowed : ", "RestrictionPolicy", z);
            return z;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isLockScreenViewAllowed : ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) {
        return isMicrophoneEnabledAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isMicrophoneEnabledAsUser(boolean z, int i) {
        boolean z2;
        if (i != 0) {
            z2 = this.mRestrictionCache.extract(0, 4L, true);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isMicrophoneEnabledAsUser (owner) : ", "RestrictionPolicy", z2);
        } else {
            z2 = true;
        }
        if (z2) {
            z2 = this.mRestrictionCache.extract(i, 4L, true);
            Slog.d("RestrictionPolicy", "userId: " + i + ", isMicrophoneEnabledAsUser : " + z2);
        }
        if (z && !z2) {
            RestrictionToastManager.show(R.string.policydesc_setGlobalProxy);
            AnonymousClass3 anonymousClass3 = this.mHandler;
            anonymousClass3.sendMessageDelayed(anonymousClass3.obtainMessage(1), 5000L);
        }
        return z2;
    }

    public final boolean isMockLocationEnabled(ContextInfo contextInfo) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 8796093022208L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isMockLocationEnabled : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (callingOrCurrentUserId != -1) {
            return isNewAdminActivationEnabledInternal(callingOrCurrentUserId, z);
        }
        Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            z2 = isNewAdminActivationEnabledInternal(((UserInfo) it.next()).id, z);
            if (!z2) {
                return z2;
            }
        }
        return z2;
    }

    public final boolean isNewAdminActivationEnabledInternal(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(i, 8589934592L, false);
        if (z && extract) {
            RestrictionToastManager.show(17042601);
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "RestrictionPolicy",
                new StringBuilder("isNewAdminActivationEnabledInternal : "),
                !extract);
        return !extract;
    }

    public final boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) {
        return isNewAdminInstallationEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public final boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) {
        if (i != -1) {
            return isNewAdminInstallationEnabledInternal(i, z);
        }
        Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
        boolean z2 = true;
        while (it.hasNext()
                && (z2 = isNewAdminInstallationEnabledInternal(((UserInfo) it.next()).id, z))) {}
        return z2;
    }

    public final boolean isNewAdminInstallationEnabledInternal(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(i, 4294967296L, false);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isNewAdminInstallationEnabledInternal : ", "RestrictionPolicy", extract);
        if (z && extract) {
            RestrictionToastManager.show(17042602);
        }
        return !extract;
    }

    public final boolean isNonMarketAppAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 281474976710656L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isNonMarketAppAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) {
        return isNonTrustedAppInstallBlockedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isNonTrustedAppInstallBlockedAsUser(int i) {
        return this.mRestrictionCache.extract(i, 4194304L, false);
    }

    public final boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 2147483648L, false);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isODETruestedBootVerfiicationEnabled : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isOTAUpgradeAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 18014398509481984L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isOTAUpgradeAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isPackageOnExclusionList(int i, String str) {
        if (Arrays.asList(excludedAdminList).contains(str)) {
            return true;
        }
        String string =
                this.mEdmStorageProvider.getString(
                        i, 0, "RESTRICTION", "preventAdminActivationWhiteList");
        if (!TextUtils.isEmpty(string)) {
            for (String str2 : string.split(",")) {
                if (!TextUtils.isEmpty(str2) && str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 2048L, true);
        if (isExistEFSFile("efs/MDM/PowerOff") == extract) {
            Slog.d(
                    "RestrictionPolicy",
                    "isPowerOffAllowed(): need to sync DB(" + extract + ") and efs");
            manageEFSFile("efs/MDM/PowerOff", extract);
        }
        if (z && !extract) {
            RestrictionToastManager.show(R.string.mediasize_japanese_jis_b5);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isPowerOffAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isPowerSavingModeAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(0, 1152921504606846976L, true);
    }

    public final boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 536870912L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17042597);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isSDCardMoveAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isSDCardWriteAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 34359738368L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isSDCardWriteAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) {
        return isSVoiceAllowedAsUser(z, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isSVoiceAllowedAsUser(boolean z, int i) {
        Binder.restoreCallingIdentity(Binder.clearCallingIdentity());
        boolean extract = this.mRestrictionCache.extract(i, 32L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17043191);
        }
        Slog.d("RestrictionPolicy", "isSVoiceAllowedAsUser, userId " + i + " : " + extract);
        return extract;
    }

    public final boolean isSafeModeAllowed(ContextInfo contextInfo) {
        boolean z;
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "RESTRICTION", "allowSafeMode")
                            .iterator();
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
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isSafeModeAllowed : ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean isScreenCaptureEnabled(int i, boolean z) {
        return isScreenCaptureEnabledEx(i, z);
    }

    public final boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) {
        return isScreenCaptureEnabled(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public final boolean isScreenCaptureEnabledEx(int i, boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
            if (SemPersonaManager.isAppSeparationUserId(i)) {
                i = 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
            if (SemPersonaManager.isKnoxId(i)) {
                getPersonaManagerAdapter$5().getClass();
                z2 = DevicePolicyCache.getInstance().isScreenCaptureAllowed(i);
            } else {
                boolean extract = this.mRestrictionCache.extract(i, 1099511627776L, true);
                z2 = (extract && getEDM$25().isRestrictedByConstrainedState(64)) ? false : extract;
            }
            if (z && !z2) {
                RestrictionToastManager.show(17042725);
            }
            if (!z2) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i,
                        "isScreenCaptureEnabledEx() : screencapture has disabled in user ",
                        "RestrictionPolicy");
            }
            Slog.d("RestrictionPolicy", "isScreenCaptureEnabled : ret=" + z2 + " userId=" + i);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isScreenCaptureEnabledInternal(boolean z) {
        return isScreenCaptureEnabled(getTopActivityUserId(), z);
    }

    public final boolean isScreenPinningAllowed(ContextInfo contextInfo) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 144115188075855872L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isScreenPinningAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isSdCardEnabled(ContextInfo contextInfo) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String externalStorageState = Environment.getExternalStorageState();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (externalStorageState == null || getEDM$25().isRestrictedByConstrainedState(2)) {
                return false;
            }
            boolean extract = this.mRestrictionCache.extract(0, 140737488355328L, true);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isSdCardEnabled : ", "RestrictionPolicy", extract);
            return extract;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId;
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(":");
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                callingOrCurrentUserId = ActivityManager.getCurrentUser();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return isSettingsChangesAllowedAsUser(z, callingOrCurrentUserId);
            }
        }
        callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        return isSettingsChangesAllowedAsUser(z, callingOrCurrentUserId);
    }

    public final boolean isSettingsChangesAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(i, 8L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.capability_desc_canCaptureFingerprintGestures);
        }
        Slog.d(
                "RestrictionPolicy",
                "isSettingsChangesAllowedAsUser, userId " + i + " : " + extract);
        return extract;
    }

    public final boolean isShareListAllowed(ContextInfo contextInfo, boolean z) {
        return isShareListAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo), z);
    }

    public final boolean isShareListAllowedAsUser(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(i, 1048576L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17043019);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "isShareListAllowed : userId(", ")", "RestrictionPolicy");
        return extract;
    }

    public final boolean isSmartClipModeAllowed(ContextInfo contextInfo) {
        return isSmartClipModeAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo), false);
    }

    public final boolean isSmartClipModeAllowedAsUser(int i, boolean z) {
        boolean extract = this.mRestrictionCache.extract(i, 72057594037927936L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17043085);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isSmartClipModeAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isSmartClipModeAllowedInternal(boolean z) {
        return isSmartClipModeAllowedAsUser(getTopActivityUserId(), z);
    }

    public final boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) {
        return isStatusBarExpansionAllowedAsUser(z, 0);
    }

    public final boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) {
        boolean extract = this.mRestrictionCache.extract(0, 256L, true);
        Log.d(
                "RestrictionPolicy",
                "isStatusBarExpansionAllowedAsUser : " + extract + ", userId = 0");
        return extract;
    }

    public final boolean isStopSystemAppAllowed(ContextInfo contextInfo) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 4096L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isStopSystemAppAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isTetheringEnabled(ContextInfo contextInfo) {
        boolean isUsbTetheringEnabled = isUsbTetheringEnabled(null);
        boolean isWifiTetheringEnabled = isWifiTetheringEnabled(null);
        boolean isBluetoothTetheringEnabled = isBluetoothTetheringEnabled(null);
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "RestrictionPolicy",
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "isTetheringEnabled : ret1 = ",
                        isUsbTetheringEnabled,
                        " ret2 = ",
                        isWifiTetheringEnabled,
                        " ret3 = "),
                isBluetoothTetheringEnabled);
        return isUsbTetheringEnabled || isWifiTetheringEnabled || isBluetoothTetheringEnabled;
    }

    public final boolean isUsbDebuggingEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 2199023255552L, true);
        boolean z = (extract && getEDM$25().isRestrictedByConstrainedState(32)) ? false : extract;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isUsbDebuggingEnabled : ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 524288L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17043422);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isUsbHostStorageAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public final boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public final boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) {
        boolean extract =
                getEDM$25().isRestrictedByConstrainedState(4)
                        ? false
                        : this.mRestrictionCache.extract(0, 562949953421312L, true);
        if (z && !extract) {
            RestrictionToastManager.show(R.string.permlab_preferredPaymentInfo);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isUsbMediaPlayerAvailable : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isUsbTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 137438953472L, true);
        boolean z = (extract && getEDM$25().isRestrictedByConstrainedState(16)) ? false : extract;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isUsbTetheringEnabled : ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = contextInfo.mContainerId;
        boolean extract = this.mRestrictionCache.extract(callingOrCurrentUserId, 2097152L, false);
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "isUseSecureKeypadEnabled:", " cxtInfo.mContainerId:", extract);
        m.append(contextInfo.mContainerId);
        m.append(" userid:");
        m.append(callingOrCurrentUserId);
        Slog.d("RestrictionPolicy", m.toString());
        return extract;
    }

    public final boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) {
        return this.mRestrictionCache.extract(0, 65536L, true);
    }

    public final boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean extract = this.mRestrictionCache.extract(callingOrCurrentUserId, 2L, true);
        Slog.d(
                "RestrictionPolicy",
                "userId: " + callingOrCurrentUserId + " isVideoRecordAllowed : " + extract);
        if (z && !extract) {
            RestrictionToastManager.show(17042613);
        }
        return extract;
    }

    public final boolean isVpnAllowed(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 9007199254740992L, true);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isVpnAllowed():ret:", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract =
                this.mRestrictionCache.extract(
                        Utils.getCallingOrCurrentUserId(contextInfo), 17179869184L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17043484);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isWallpaperChangeAllowed : ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) {
        boolean z = true;
        boolean z2 = false;
        try {
        } catch (InvalidParameterException unused) {
            Slog.w(
                    "RestrictionPolicy",
                    "isWearablePolicyEnabled() failed: INVALID PARAMETER INPUT");
        } catch (Exception unused2) {
            Slog.w("RestrictionPolicy", "isWearablePolicyEnabled() failed.");
        }
        if (i != 1) {
            throw new InvalidParameterException();
        }
        Iterator it =
                this.mEdmStorageProvider
                        .getIntListAsUser(0, 0, "RESTRICTION", "wearablePolicyEnabled")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (i != (((Integer) it.next()).intValue() & i)) {
                break;
            }
        }
        z2 = z;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isWearablePolicyEnabled() : ", "RestrictionPolicy", z2);
        return z2;
    }

    public final boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) {
        boolean extract = this.mRestrictionCache.extract(0, 8192L, true);
        if (z && !extract) {
            RestrictionToastManager.show(17042603);
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isWifiDirectAllowed: ", "RestrictionPolicy", extract);
        return extract;
    }

    public final boolean isWifiTetheringEnabled(ContextInfo contextInfo) {
        boolean extract = this.mRestrictionCache.extract(0, 274877906944L, true);
        boolean z = (extract && getEDM$25().isRestrictedByConstrainedState(16)) ? false : extract;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isWifiTetheringEnabled: ", "RestrictionPolicy", z);
        return z;
    }

    public final boolean mountSdCard() {
        IStorageManager mountService = getMountService();
        if (mountService == null) {
            return false;
        }
        try {
            String externalSdCardPath = getExternalSdCardPath();
            if (externalSdCardPath == null) {
                Log.d(
                        "RestrictionPolicy",
                        "mountSdCard() : fail to mount media because path is null.");
                return false;
            }
            int mountVolume = mountService.mountVolume(externalSdCardPath);
            if (mountVolume == 0) {
                return true;
            }
            Log.d(
                    "RestrictionPolicy",
                    "mountSdCard() : Unable to mount media - error code : " + mountVolume);
            return false;
        } catch (RemoteException e) {
            Log.e("RestrictionPolicy", "mountSdCard() : failed by RemoteException.", e);
            return false;
        } catch (Exception e2) {
            Log.e("RestrictionPolicy", "mountSdCard() : failed by Exception.", e2);
            return false;
        }
    }

    public final void notifyChanges(int i) {
        if (i != -1) {
            updateScreenCaptureDisabledInWindowManager(i, !isScreenCaptureEnabled(i, false));
        } else {
            List users = this.mUserManager.getUsers(true);
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                updateScreenCaptureDisabledInWindowManager(
                        ((UserInfo) users.get(i2)).id, !isScreenCaptureEnabled(r4, false));
            }
        }
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isCameraEnabled"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider/RestrictionPolicy3/isSettingsChangesAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider/RestrictionPolicy/isSettingsChangesAllowed"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isCameraEnabled"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isMicEnabled"));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
        if (this.mKcUid == -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    "adminName",
                    com.samsung.android.knox.restriction.RestrictionPolicy.KC_COMPONENT_NAME
                            .flattenToString());
            this.mKcUid = this.mEdmStorageProvider.getInt(contentValues, "ADMIN_INFO", "adminUid");
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (!z) {
            int userId = UserHandle.getUserId(i);
            this.mRestrictionCache.load(userId);
            notifyChanges(userId);
        }
        ContextInfo contextInfo = new ContextInfo(i);
        isFactoryResetAllowed(contextInfo);
        isFirmwareRecoveryAllowed(contextInfo, false);
        Utils.writePropertyValue(
                "microphoneEnabled",
                "" + (isMicrophoneEnabled(new ContextInfo(i), false) ? 1 : 0),
                "/data/system/enterprise.conf");
        Utils.writePropertyValue(
                "screenCaptureEnabled",
                "" + (isScreenCaptureEnabled(new ContextInfo(i), false) ? 1 : 0),
                "/data/system/enterprise.conf");
        if (isSDCardWriteAllowed(null) != this.preAdminRemoval_SDCardWrite) {
            applySdCardWriteAccessPolicy();
            this.preAdminRemoval_SDCardWrite = false;
        }
        rollBackSVoice(UserHandle.getUserId(i));
        setAllowedFOTAVersion(new ContextInfo(Binder.getCallingUid()), null, null, false);
        updateUsbStorageStatebyIntent(
                isUsbHostStorageAllowed(new ContextInfo(Binder.getCallingUid()), false));
        if (!this.isLockScreenWidgetsAllowedCache && isLockScreenViewAllowed(null, 1)) {
            setLockScreenViewProperty(1, true);
            this.isLockScreenWidgetsAllowedCache = true;
        }
        if (!this.isLockScreenShortcutsAllowedCache && isLockScreenViewAllowed(null, 2)) {
            setLockScreenViewProperty(2, true);
            this.isLockScreenShortcutsAllowedCache = true;
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor$7(callingOrCurrentUserId);
        }
        if (!this.isSafeModeAllowedCache
                && isSafeModeAllowed(null)
                && callingOrCurrentUserId == 0
                && !getUserRestrictionSafeMode()) {
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
            Iterator it = ((ArraySet) this.mUserRestrictionEnforcedByKC).iterator();
            while (it.hasNext()) {
                this.mUserManager.setUserRestriction((String) it.next(), false);
            }
            this.mUserRestrictionEnforcedByKC = new ArraySet();
            this.mKcUid = -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a3, code lost:

       if (r10 == null) goto L39;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00e5 A[Catch: Exception -> 0x0122, TryCatch #2 {Exception -> 0x0122, blocks: (B:13:0x00c0, B:15:0x00e5, B:16:0x00f1, B:18:0x00f7, B:20:0x00fd, B:21:0x0102, B:25:0x0100, B:26:0x00ec), top: B:12:0x00c0 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ec A[Catch: Exception -> 0x0122, TryCatch #2 {Exception -> 0x0122, blocks: (B:13:0x00c0, B:15:0x00e5, B:16:0x00f1, B:18:0x00f7, B:20:0x00fd, B:21:0x0102, B:25:0x0100, B:26:0x00ec), top: B:12:0x00c0 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x012a  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v3 */
    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPreAdminRemoval(int r17) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.onPreAdminRemoval(int):void");
    }

    public final boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceCaller =
                EnterpriseAccessController.enforceCaller(contextInfo, "RESTRICTION_ADMIN");
        boolean z2 = false;
        boolean isNewAdminActivationEnabled = isNewAdminActivationEnabled(enforceCaller, false);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        if (isNewAdminActivationEnabled) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    " preventAdminActivation : ", "RestrictionPolicy", z);
            try {
                if (!isAdminEnforcementAllowed(enforceCaller.mCallerUid, callingOrCurrentUserId)) {
                    return false;
                }
                z2 =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceCaller.mCallerUid,
                                z,
                                0,
                                "preventAdminActivation");
            } catch (Exception unused) {
                Log.w("RestrictionPolicy", "RestrictionPolicy.preventAdminActivation exception : ");
            }
        } else {
            int uidForAdminActivation = getUidForAdminActivation(callingOrCurrentUserId);
            int i = enforceCaller.mCallerUid;
            if (i == uidForAdminActivation) {
                z2 =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION", i, z, 0, "preventAdminActivation");
            }
        }
        this.mRestrictionCache.update(
                "preventAdminActivation",
                8589934592L,
                false,
                callingOrCurrentUserId,
                Integer.valueOf(enforceCaller.mCallerUid),
                Boolean.valueOf(z));
        return z2;
    }

    public final boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceCaller =
                EnterpriseAccessController.enforceCaller(contextInfo, "RESTRICTION_ADMIN");
        boolean z2 = false;
        boolean isNewAdminInstallationEnabled = isNewAdminInstallationEnabled(enforceCaller, false);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        if (isNewAdminInstallationEnabled) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    " preventAdminInstallation : ", "RestrictionPolicy", z);
            if (!isAdminEnforcementAllowed(enforceCaller.mCallerUid, callingOrCurrentUserId)) {
                return false;
            }
            z2 =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceCaller.mCallerUid,
                            z,
                            0,
                            "preventAdminInstallation");
        } else {
            int uidForAdminInstallation = getUidForAdminInstallation(callingOrCurrentUserId);
            int i = enforceCaller.mCallerUid;
            if (i == uidForAdminInstallation) {
                z2 =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION", i, z, 0, "preventAdminInstallation");
            }
        }
        this.mRestrictionCache.update(
                "preventAdminInstallation",
                4294967296L,
                false,
                callingOrCurrentUserId,
                Integer.valueOf(enforceCaller.mCallerUid),
                Boolean.valueOf(z));
        return z2;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.enterprise.restriction.RestrictionPolicy$5] */
    public final void registerSubscriptionCallback() {
        if (this.mSubscriptionsChangedListener != null) {
            return;
        }
        SubscriptionManager subScriptionManager = getSubScriptionManager();
        this.mSubscriptionsChangedListener =
                new SubscriptionManager.OnSubscriptionsChangedListener(
                        getLooper()) { // from class:
                                       // com.android.server.enterprise.restriction.RestrictionPolicy.5
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public final void onSubscriptionsChanged() {
                        Log.d("MultiSimPolicy", "onSubscriptionsChanged");
                        RestrictionPolicy restrictionPolicy = RestrictionPolicy.this;
                        if (restrictionPolicy.debug) {
                            restrictionPolicy.getClass();
                            Log.d("MultiSimPolicy", "showSubscriptionInfos");
                            for (SubscriptionInfo subscriptionInfo :
                                    restrictionPolicy
                                            .getSubScriptionManager()
                                            .getAllSubscriptionInfoList()) {
                                if (restrictionPolicy.debug) {
                                    Log.d("MultiSimPolicy", subscriptionInfo.toString());
                                }
                            }
                            restrictionPolicy.registerSubscriptionCallback();
                        }
                        final RestrictionPolicy restrictionPolicy2 = RestrictionPolicy.this;
                        restrictionPolicy2.getClass();
                        Log.d("MultiSimPolicy", "onSubscriptionsChanged");
                        restrictionPolicy2.mCallCount++;
                        try {
                            new Thread() { // from class:
                                           // com.android.server.enterprise.restriction.RestrictionPolicy.6
                                @Override // java.lang.Thread, java.lang.Runnable
                                public final void run() {
                                    try {
                                        String str = SystemProperties.get("sim-delay");
                                        int parseInt = str != null ? Integer.parseInt(str) : 2000;
                                        if (RestrictionPolicy.this.debug) {
                                            Log.d("MultiSimPolicy", "delay ms : " + parseInt);
                                        }
                                        Thread.sleep(parseInt);
                                    } catch (Exception unused) {
                                    }
                                    if (RestrictionPolicy.this.debug) {
                                        GestureWakeup$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("delayed "),
                                                RestrictionPolicy.this.mCallCount,
                                                "MultiSimPolicy");
                                    }
                                    RestrictionPolicy restrictionPolicy3 = RestrictionPolicy.this;
                                    int i = restrictionPolicy3.mCallCount - 1;
                                    restrictionPolicy3.mCallCount = i;
                                    if (i == 0) {
                                        try {
                                            String multiSimPolicy =
                                                    restrictionPolicy3.getMultiSimPolicy();
                                            if (multiSimPolicy.equals("")) {
                                                return;
                                            }
                                            JSONObject jSONObject = new JSONObject(multiSimPolicy);
                                            int i2 = jSONObject.getInt("maxP");
                                            int i3 = jSONObject.getInt("maxE");
                                            JSONArray jSONArray =
                                                    jSONObject.getJSONArray("mccMncArray");
                                            String[] strArr = new String[jSONArray.length()];
                                            for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                                                strArr[i4] =
                                                        jSONArray
                                                                .getJSONObject(i4)
                                                                .getString("mccMnc");
                                            }
                                            JSONArray jSONArray2 =
                                                    jSONObject.getJSONArray("numberArray");
                                            String[] strArr2 = new String[jSONArray2.length()];
                                            for (int i5 = 0; i5 < jSONArray2.length(); i5++) {
                                                strArr2[i5] =
                                                        jSONArray2
                                                                .getJSONObject(i5)
                                                                .getString("number");
                                            }
                                            JSONArray jSONArray3 =
                                                    jSONObject.getJSONArray("iccIdArray");
                                            String[] strArr3 = new String[jSONArray3.length()];
                                            for (int i6 = 0; i6 < jSONArray3.length(); i6++) {
                                                strArr3[i6] =
                                                        jSONArray3
                                                                .getJSONObject(i6)
                                                                .getString("iccId");
                                            }
                                            restrictionPolicy3.setMultiSimPolicy(
                                                    i2, i3, strArr, strArr2, strArr3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AnonymousClass3 anonymousClass3 = this.mHandler;
            Objects.requireNonNull(anonymousClass3);
            subScriptionManager.addOnSubscriptionsChangedListener(
                    new ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1(anonymousClass3),
                    this.mSubscriptionsChangedListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void rollBackSVoice(int i) {
        String genericValueAsUser;
        if (!isSVoiceAllowedAsUser(false, i)
                || (genericValueAsUser =
                                this.mEdmStorageProvider.getGenericValueAsUser(i, "voiceControl"))
                        == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(),
                "voice_input_control",
                Integer.parseInt(genericValueAsUser),
                i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void sendSeletiveFotaResult(int i) {
        Log.d("RestrictionPolicy", "sendSeletiveFotaResult: result = " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Intent intent =
                new Intent("com.samsung.android.knox.intent.action.UPDATE_FOTA_VERSION_RESULT");
        intent.putExtra("com.samsung.android.knox.intent.extra.UPDATE_FOTA_VERSION_STATUS", i);
        this.mContext.sendBroadcastAsUser(
                intent,
                UserHandle.ALL,
                "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT");
        Binder.restoreCallingIdentity(clearCallingIdentity);
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

    public final boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) {
        Log.w("RestrictionPolicy", "MiscPolicy.setAllowNonMarketApps");
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setAllowNonMarketApps : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "allowNonMarketApp");
        this.mRestrictionCache.update(
                "allowNonMarketApp",
                281474976710656L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        updateNonMarketAppOnUserManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has allowed installation of non-Google-Play"
                                          + " application."
                                    : "Admin %d has disallowed installation of non-Google-Play"
                                          + " application.",
                            Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setAllowedFOTAVersion(
            com.samsung.android.knox.ContextInfo r18,
            java.lang.String r19,
            android.os.Bundle r20,
            boolean r21) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.setAllowedFOTAVersion(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, android.os.Bundle, boolean):boolean");
    }

    public final boolean setBackgroundData(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "backgroundDataEnabled");
        if (putBoolean) {
            this.mRestrictionCache.update(
                    "backgroundDataEnabled",
                    1125899906842624L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
            if (z && isDataSavingAllowed()) {
                try {
                    INetworkPolicyManager asInterface =
                            INetworkPolicyManager.Stub.asInterface(
                                    ServiceManager.getService("netpolicy"));
                    if (asInterface == null) {
                        throw new NullPointerException("networkPolicyService is null");
                    }
                    if (asInterface.getRestrictBackground()) {
                        asInterface.setRestrictBackground(true);
                        Slog.w("RestrictionPolicy", "Applied Background Data Removal Policy");
                    }
                } catch (Exception e) {
                    WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                            e, "setBackgroundData EX: ", "RestrictionPolicy");
                }
            } else {
                applyBackgroundDataRestriction();
            }
        }
        Slog.w("RestrictionPolicy", "setBackgroundData : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public final boolean setBackup(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!z) {
                z2 = false;
                try {
                    IBackupManager asInterface =
                            IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
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
                z2 &=
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                                z,
                                0,
                                "backupEnabled");
                this.mRestrictionCache.update(
                        "backupEnabled",
                        17592186044416L,
                        true,
                        0,
                        Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                        Boolean.valueOf(z));
            }
            return z2;
        } finally {
            Slog.w("RestrictionPolicy", "setBackup : " + z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndTetheringPermission =
                enforceOwnerOnlyAndTetheringPermission(contextInfo);
        boolean z2 = false;
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setBluetoothTethering due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z2 =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndTetheringPermission.mCallerUid,
                            z,
                            0,
                            "bluetoothTetheringEnabled");
            this.mRestrictionCache.update(
                    "bluetoothTetheringEnabled",
                    549755813888L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid),
                    Boolean.valueOf(z));
            Slog.w("RestrictionPolicy", "setBluetoothTethering : " + z);
            if (z2) {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has enabled bluetooth Tethering."
                                        : "Admin %d has disabled bluetooth Tethering.",
                                Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid));
                if (!z) {
                    ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                            .stopTethering(2);
                }
            }
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("setBluetoothTethering Ex:"), "RestrictionPolicy");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Slog.d("RestrictionPolicy", "setBluetoothTethering : " + z);
        return z2;
    }

    public final boolean setCCMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission$1 =
                enforceOwnerOnlyAndAdvancedRestrictionPermission$1(contextInfo);
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndAdvancedRestrictionPermission$1);
        boolean z2 = false;
        Log.d(
                "RestrictionPolicy",
                "setCCMode() : "
                        + z
                        + " and current MDM state:"
                        + isCCModeEnabled(
                                enforceOwnerOnlyAndAdvancedRestrictionPermission$1, false));
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
                    Log.d(
                            "RestrictionPolicy",
                            "setCCMode() : securtyManagerService is not available.");
                }
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setCCMode(): failed to set CCMode with exception", e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "setCCMode() result : ", "RestrictionPolicy");
            if (i == 0) {
                boolean putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid,
                                z,
                                0,
                                "setCCMode");
                this.mRestrictionCache.update(
                        "setCCMode",
                        1073741824L,
                        false,
                        0,
                        Integer.valueOf(
                                enforceOwnerOnlyAndAdvancedRestrictionPermission$1.mCallerUid),
                        Boolean.valueOf(z));
                if (!putBoolean) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "setCCMode() : failed to update enterprise db. enable = ",
                                    ", state=",
                                    z);
                    m.append(
                            isCCModeEnabled(
                                    enforceOwnerOnlyAndAdvancedRestrictionPermission$1, false));
                    Log.d("RestrictionPolicy", m.toString());
                }
                z2 = putBoolean;
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i,
                        "setCCMode() : failed. securtyManagerService has failed to enforce CCMode."
                            + " result = ",
                        "RestrictionPolicy");
            }
            int cCModeState = getCCModeState(enforceOwnerOnlyAndAdvancedRestrictionPermission$1);
            if (cCModeState == 2 || cCModeState == 4) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "RestrictionPolicy",
                            String.format(
                                    z
                                            ? "Admin %d has requested to enable CCMode."
                                            : "Admin %d has requested to disable CCMode.",
                                    Integer.valueOf(
                                            enforceOwnerOnlyAndAdvancedRestrictionPermission$1
                                                    .mCallerUid)),
                            callingOrCurrentUserId);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                }
            }
            return z2;
        } finally {
        }
    }

    public final boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) {
        boolean z2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            callingUid,
                            callingPid,
                            "setCCModeOnlyForCallerSystem() caller uid : ",
                            " caller pid : ",
                            " Process.mypid() : ");
            m.append(Process.myPid());
            Log.d("RestrictionPolicy", m.toString());
            throw new SecurityException("Caller is not a system process..");
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d(
                "RestrictionPolicy",
                "setCCMode() : "
                        + z
                        + " and current MDM state:"
                        + isCCModeEnabled(contextInfo, false));
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
            Binder.restoreCallingIdentity(clearCallingIdentity);
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "setCCMode() result : ", "RestrictionPolicy");
            if (i == 0) {
                z2 =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION", contextInfo.mCallerUid, z, 0, "setCCMode");
                this.mRestrictionCache.update(
                        "setCCMode",
                        1073741824L,
                        false,
                        0,
                        Integer.valueOf(contextInfo.mCallerUid),
                        Boolean.valueOf(z));
                if (!z2) {
                    StringBuilder m2 =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "setCCMode() : failed to update enterprise db. enable = ",
                                    ", state=",
                                    z);
                    m2.append(isCCModeEnabled(contextInfo, false));
                    Log.d("RestrictionPolicy", m2.toString());
                }
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i,
                        "setCCMode() : failed. MdfPolicy has failed to enforce CCMode. result = ",
                        "RestrictionPolicy");
                z2 = false;
            }
            int cCModeState = getCCModeState(contextInfo);
            if (cCModeState == 2 || cCModeState == 4) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "RestrictionPolicy",
                            String.format(
                                    z
                                            ? "Admin %d has requested to enable CCMode."
                                            : "Admin %d has requested to disable CCMode.",
                                    Integer.valueOf(contextInfo.mCallerUid)),
                            callingOrCurrentUserId);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                }
            }
            return z2;
        } finally {
        }
    }

    public final boolean setCamera(ContextInfo contextInfo, boolean z) {
        boolean z2;
        boolean putBoolean;
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceHwPermission.mCallerUid,
                                z,
                                0,
                                "cameraEnabled");
                Slog.d("RestrictionPolicy", "setCamera : " + z);
                this.mRestrictionCache.updateCameraDisabledAdmin(callingOrCurrentUserId);
                z2 = false;
            } catch (RemoteException e) {
                e = e;
                z2 = false;
            } catch (Exception unused) {
                z2 = false;
            }
            try {
                this.mRestrictionCache.update(
                        "cameraEnabled",
                        68719476736L,
                        true,
                        callingOrCurrentUserId,
                        Integer.valueOf(enforceHwPermission.mCallerUid),
                        Boolean.valueOf(z));
                if (putBoolean && !z) {
                    ActivityManagerNative.getDefault()
                            .forceStopPackage("com.sec.android.app.camera", callingOrCurrentUserId);
                    List containers = KnoxContainerManager.getContainers(enforceHwPermission);
                    if (containers != null) {
                        Iterator it = containers.iterator();
                        while (it.hasNext()) {
                            ActivityManagerNative.getDefault()
                                    .forceStopPackage(
                                            "com.sec.android.app.camera",
                                            ((Integer) it.next()).intValue());
                        }
                    }
                }
                if (putBoolean) {
                    setCameraAllowedSystemUI(0, isCameraEnabled(enforceHwPermission, false));
                    AuditLog.logEventAsUser(
                            callingOrCurrentUserId,
                            z ? 34 : 35,
                            new Object[] {Integer.valueOf(enforceHwPermission.mCallerUid)});
                    SecContentProviderUtil.notifyPolicyChangesAllUser(
                            this.mContext,
                            Uri.parse(
                                    "content://com.sec.knox.provider/RestrictionPolicy1/isCameraEnabled"));
                    SecContentProviderUtil.notifyPolicyChangesAllUser(
                            this.mContext,
                            Uri.parse(
                                    "content://com.sec.knox.provider/RestrictionPolicy/isCameraEnabled"));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return putBoolean;
            } catch (RemoteException e2) {
                e = e2;
                Log.e("RestrictionPolicy", "Fail getting ActivityManager " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z2;
            } catch (Exception unused2) {
                Slog.w("RestrictionPolicy", "is EDMStorageProvider running?");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z2;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
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

    public final boolean setCellularData(ContextInfo contextInfo, boolean z) {
        boolean z2;
        TelephonyManager telephonyManager;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndRestrictionPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setCellularData due to Ethernet only mode");
            return false;
        }
        Log.i("RestrictionPolicy", "setCellularData() : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = true;
        try {
            z2 =
                    this.mEdmStorageProvider.getBoolean(
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            0,
                            "RESTRICTION",
                            "cellularDataEnabled");
        } catch (Exception unused) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setCellularData() : fail to get admin policy value = "),
                    enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                    "RestrictionPolicy");
            z2 = true;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "cellularDataEnabled");
        if (z) {
            z3 = putBoolean;
        } else {
            try {
                telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (telephonyManager == null) {
                Log.i("RestrictionPolicy", "setCellularData() : Failed to get Telephony Manager");
                z3 = false;
            } else if (telephonyManager.getDataEnabled()) {
                telephonyManager.setDataEnabled(false);
            } else {
                Log.i("RestrictionPolicy", "setCellularData() : mobile data has already disabled");
            }
        }
        if (z3) {
            this.mRestrictionCache.update(
                    "cellularDataEnabled",
                    70368744177664L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
        } else {
            z3 &=
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            z2,
                            0,
                            "cellularDataEnabled");
        }
        if (z3) {
            setCellularDataAllowedSystemUI(0, isCellularDataAllowed(null));
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has enabled cellular data."
                                    : "Admin %d has disabled cellular data.",
                            Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Slog.d("RestrictionPolicy", "setCellularData() : " + z + ", ret = " + z3);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z3;
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

    public final boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "clipboardEnabled");
        this.mRestrictionCache.update(
                "clipboardEnabled",
                512L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
        StringBuilder sb = new StringBuilder("setClipboardEnabled : ");
        sb.append(z);
        sb.append(", ret = ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("RestrictionPolicy", sb, putBoolean);
        return putBoolean;
    }

    public final void setFaceRecognitionEvenCameraBlockedAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext)
                        .setFaceRecognitionEvenCameraBlockedAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e(
                        "RestrictionPolicy",
                        "setFaceRecognitionEvenCameraBlockedAllowedSystemUI() failed. ",
                        e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setHeadphoneState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        boolean z2 = false;
        boolean isHeadphoneEnabled =
                isHeadphoneEnabled(enforceOwnerOnlyAndRestrictionPermission, false);
        try {
            Slog.d(
                    "RestrictionPolicy",
                    "setHeadphoneState : "
                            + z
                            + " old state : "
                            + isHeadphoneEnabled
                            + " WiredHeadsetOn : "
                            + this.mAudioManager.isWiredHeadsetOn());
            if (z) {
                if (this.mAudioManager.isWiredHeadsetOn() && !isHeadphoneEnabled) {
                    this.mAudioManager.setWiredDeviceConnectionState(4, 1, "", "h2w");
                    this.mAudioManager.setWiredDeviceConnectionState(8, 1, "", "h2w");
                }
            } else if (this.mAudioManager.isWiredHeadsetOn()) {
                this.mAudioManager.setWiredDeviceConnectionState(4, 0, "", "h2w");
                this.mAudioManager.setWiredDeviceConnectionState(8, 0, "", "h2w");
            }
            z2 =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            z,
                            0,
                            "setHeadphoneState");
            this.mRestrictionCache.update(
                    "setHeadphoneState",
                    268435456L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
            return z2;
        } catch (Exception unused) {
            Log.w("RestrictionPolicy", "RestrictionPolicy.setHeadphoneState() exception : ");
            return z2;
        }
    }

    public final boolean setHomeKeyState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        if (Utils.getCallingOrCurrentUserId(enforceRestrictionPermission) != 0) {
            Log.w("RestrictionPolicy", "setHomeKeyState() : Failed. Caller is not owner");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "homeKeyEnabled");
        this.mRestrictionCache.update(
                "homeKeyEnabled",
                4503599627370496L,
                true,
                UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (z == isHomeKeyEnabled(enforceRestrictionPermission, false)) {
            setHomeKeyVisibilityOnNavi(z);
        }
        KeyCodeMediatorImpl keyCodeMediatorImpl = this.mKeyCodeMediator;
        if (keyCodeMediatorImpl == null) {
            Log.e(
                    "RestrictionPolicy",
                    "mKeyCodeMediator must not be null! This will cause problems on hardware key"
                        + " restriction.");
        } else {
            keyCodeMediatorImpl.update(3);
        }
        return putBoolean;
    }

    public final void setHomeKeyVisibilityOnNavi(boolean z) {
        IStatusBarService iStatusBarService;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mStatusBarService == null) {
                synchronized (this) {
                    try {
                        if (this.mStatusBarService == null) {
                            IStatusBarService asInterface =
                                    IStatusBarService.Stub.asInterface(
                                            ServiceManager.getService("statusbar"));
                            this.mStatusBarService = asInterface;
                            if (asInterface == null) {
                                Log.d("RestrictionPolicy", "warning: no STATUS_BAR_SERVICE");
                            }
                        }
                        iStatusBarService = this.mStatusBarService;
                    } finally {
                    }
                }
                this.mStatusBarService = iStatusBarService;
            }
            IStatusBarService iStatusBarService2 = this.mStatusBarService;
            if (iStatusBarService2 != null) {
                if (z) {
                    iStatusBarService2.disable(0, this.mToken, "RestrictionPolicy");
                } else {
                    iStatusBarService2.disable(2097152, this.mToken, "RestrictionPolicy");
                }
            }
        } catch (Exception unused) {
            Log.d("RestrictionPolicy", "setHomeAndRecentKey was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean setIrisCameraState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setIrisCameraState : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION", enforceHwPermission.mCallerUid, z, 0, "iriscameraEnabled");
        this.mRestrictionCache.update(
                "iriscameraEnabled",
                288230376151711744L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceHwPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    public final boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d(
                "RestrictionPolicy",
                "setKnoxDelegationEnabled : " + z + " with userId : " + callingOrCurrentUserId);
        if (callingOrCurrentUserId != 0) {
            return false;
        }
        try {
            KpuHelper kpuHelper = KpuHelper.getInstance(this.mContext);
            kpuHelper.getClass();
            if (!kpuHelper.isUidValidKpu(
                    Binder.getCallingUid(), contextInfo.mContainerId, contextInfo.mParent)) {
                return false;
            }
            boolean putBoolean =
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION", contextInfo.mCallerUid, z, 0, "knox_delegation");
            Log.d("RestrictionPolicy", "setKnoxDelegationEnabled result : " + putBoolean);
            this.mRestrictionCache.update(
                    "knox_delegation",
                    Long.MIN_VALUE,
                    false,
                    callingOrCurrentUserId,
                    Integer.valueOf(contextInfo.mCallerUid),
                    Boolean.valueOf(z));
            return putBoolean;
        } catch (Exception unused) {
            Slog.w("RestrictionPolicy", "is EDMStorageProvider running?");
            return false;
        }
    }

    public final boolean setLockScreenState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        if (SemPersonaManager.isKnoxId(userId)) {
            return false;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                " setLockScreenState : ", "RestrictionPolicy", z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "lockScreenEnabled");
        this.mRestrictionCache.update(
                "lockScreenEnabled",
                8388608L,
                true,
                userId,
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logEventAsUser(
                        userId,
                        z ? 50 : 51,
                        new Object[] {Integer.valueOf(enforceRestrictionPermission.mCallerUid)});
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public final boolean setLockScreenViewProperty(int i, boolean z) {
        if ((i & 1) != 1) {
            if ((i & 2) == 2) {
                return Settings.System.putInt(
                                this.mContext.getContentResolver(),
                                "lock_screen_shortcut",
                                z ? 1 : 0)
                        & Settings.System.putInt(
                                this.mContext.getContentResolver(),
                                "set_shortcuts_mode",
                                z ? 1 : 0);
            }
            return false;
        }
        boolean putInt =
                Settings.System.putInt(
                                this.mContext.getContentResolver(),
                                "kg_multiple_lockscreen",
                                z ? 1 : 0)
                        & Settings.System.putInt(
                                this.mContext.getContentResolver(),
                                "kg_enable_camera_widget",
                                z ? 1 : 0);
        if (z) {
            return putInt;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent =
                    new Intent(
                            "com.samsung.android.knox.intent.action.KNOX_FACE_WIDGET_OFF_INTERNAL");
            intent.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
            this.mContext.sendBroadcast(intent);
            return putInt;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final void setMediator(KeyCodeMediatorImpl keyCodeMediatorImpl) {
        if (this.mKeyCodeMediator == null) {
            this.mKeyCodeMediator = keyCodeMediatorImpl;
            ((HashSet) keyCodeMediatorImpl.mRestrictionCallbacks).add(this);
        }
    }

    public final boolean setMicrophoneState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceHwPermission = enforceHwPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceHwPermission);
        if (isMicrophoneEnabled(enforceHwPermission, false) && !z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    "wake_up_lock_screen",
                    0,
                    callingOrCurrentUserId);
            Settings.System.getIntForUser(
                    this.mContext.getContentResolver(),
                    "voice_input_control",
                    0,
                    callingOrCurrentUserId);
            try {
                ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
                if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)) {
                    this.mContext.sendBroadcastAsUser(
                            new Intent("com.samsung.media.save_fmrecording_only"),
                            new UserHandle(callingOrCurrentUserId));
                    this.mContext.sendBroadcastAsUser(
                            new Intent("com.sec.android.app.voicerecorder.rec_save"),
                            new UserHandle(callingOrCurrentUserId));
                    this.mContext.sendBroadcastAsUser(
                            new Intent("com.sec.android.app.voicenote.rec_save"),
                            new UserHandle(callingOrCurrentUserId),
                            "com.sec.android.app.voicenote.Controller");
                } else {
                    new ArrayList();
                    Iterator it = Utils.getAllUsers(this.mContext).iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        Slog.d("TAG", "IF = FALSE(isValidKnoxId) i:" + num + " enable:" + z);
                        Iterator it2 = it;
                        this.mContext.sendBroadcastAsUser(
                                new Intent("com.samsung.media.save_fmrecording_only"),
                                new UserHandle(num.intValue()));
                        this.mContext.sendBroadcastAsUser(
                                new Intent("com.sec.android.app.voicerecorder.rec_save"),
                                new UserHandle(num.intValue()));
                        this.mContext.sendBroadcastAsUser(
                                new Intent("com.sec.android.app.voicenote.rec_save"),
                                new UserHandle(num.intValue()),
                                "com.sec.android.app.voicenote.Controller");
                        it = it2;
                    }
                }
            } catch (Exception e) {
                Slog.e("RestrictionPolicy", "setMicrophoneState Broadcast error ");
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION", enforceHwPermission.mCallerUid, z, 0, "microphoneEnabled");
        if (putBoolean) {
            Utils.writePropertyValue(
                    "microphoneEnabled",
                    "" + (isMicrophoneEnabled(enforceHwPermission, false) ? 1 : 0),
                    "/data/system/enterprise.conf");
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                AuditLog.logEventAsUser(
                        callingOrCurrentUserId,
                        z ? 38 : 39,
                        new Object[] {Integer.valueOf(enforceHwPermission.mCallerUid)});
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            }
        }
        this.mRestrictionCache.update(
                "microphoneEnabled",
                4L,
                true,
                callingOrCurrentUserId,
                Integer.valueOf(enforceHwPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean) {
            if (isMicrophoneEnabled(enforceHwPermission, false)) {
                this.mAudioManager.setParameters("g_knox_microphone_allowed=true");
            } else {
                this.mAudioManager.setParameters("g_knox_microphone_allowed=false");
            }
            SecContentProviderUtil.notifyPolicyChangesAllUser(
                    this.mContext,
                    Uri.parse("content://com.sec.knox.provider/RestrictionPolicy/isMicEnabled"));
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setMicrophoneState : ", "RestrictionPolicy", z);
        return putBoolean;
    }

    public final boolean setMockLocation(ContextInfo contextInfo, boolean z) {
        boolean z2;
        boolean resetMockLocationApps;
        String str;
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission =
                getEDM$25()
                        .enforceOwnerOnlyAndActiveAdminPermission(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_LOCATION")));
        int userId = UserHandle.getUserId(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z) {
            resetMockLocationApps = true;
        } else {
            try {
                resetMockLocationApps =
                        new DeveloperModeSettings(this.mContext).resetMockLocationApps();
                ApplicationRestrictionsManager applicationRestrictionsManager =
                        ApplicationRestrictionsManager.getInstance(this.mContext);
                if (applicationRestrictionsManager != null
                        && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                    try {
                        ActivityManagerNative.getDefault()
                                .forceStopPackage(
                                        KnoxCustomManagerService.SETTING_PKG_NAME,
                                        this.mContext.getUserId());
                    } catch (RemoteException e) {
                        Log.w(
                                "RestrictionPolicy",
                                "killSettings: RemoteException ex -> " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                z2 = false;
            }
        }
        z2 = resetMockLocationApps;
        if (z2) {
            z2 &=
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndActiveAdminPermission.mCallerUid,
                            z,
                            0,
                            "mockLocationEnabled");
            str = "RestrictionPolicy";
            this.mRestrictionCache.update(
                    "mockLocationEnabled",
                    8796093022208L,
                    true,
                    userId,
                    Integer.valueOf(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid),
                    Boolean.valueOf(z));
        } else {
            str = "RestrictionPolicy";
        }
        Slog.w(str, "setMockLocationState : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final void setMultiSimEnabled(SubscriptionInfo subscriptionInfo) {
        if (!subscriptionInfo.isEmbedded()
                ? subscriptionInfo.areUiccApplicationsEnabled()
                : subscriptionInfo.getSimSlotIndex() >= 0) {
            RestrictionToastManager.show(
                    this.mContext.getText(R.string.permdesc_nearby_wifi_devices).toString());
        }
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        boolean isEmbedded = subscriptionInfo.isEmbedded();
        int portIndex = subscriptionInfo.getPortIndex();
        Log.d(
                "MultiSimPolicy",
                "setMultiSimEnabled : id : "
                        + subscriptionId
                        + " isEmbedded : "
                        + isEmbedded
                        + " port : "
                        + portIndex
                        + " enable : false");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isEmbedded) {
                    ((EuiccManager) this.mContext.getSystemService("euicc"))
                            .switchToSubscription(
                                    -1,
                                    portIndex,
                                    PendingIntent.getBroadcast(
                                            this.mContext,
                                            0,
                                            new Intent(
                                                    "com.samsung.android.knox.intent.action.POC_ESIM"),
                                            67108864));
                } else {
                    getSubScriptionManager().setSubscriptionEnabled(subscriptionId, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02e8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x033e A[Catch: Exception -> 0x0370, TRY_LEAVE, TryCatch #1 {Exception -> 0x0370, blocks: (B:142:0x033a, B:144:0x033e), top: B:141:0x033a }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0378 A[Catch: Exception -> 0x03a9, TRY_LEAVE, TryCatch #11 {Exception -> 0x03a9, blocks: (B:147:0x0374, B:149:0x0378), top: B:146:0x0374 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x04d6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0455  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMultiSimPolicy(
            int r30,
            int r31,
            java.lang.String[] r32,
            java.lang.String[] r33,
            java.lang.String[] r34) {
        /*
            Method dump skipped, instructions count: 1277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.setMultiSimPolicy(int,"
                    + " int, java.lang.String[], java.lang.String[], java.lang.String[]):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0134 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMultiSimPolicy(
            com.samsung.android.knox.ContextInfo r17,
            int r18,
            int r19,
            java.lang.String[] r20,
            java.lang.String[] r21,
            java.lang.String[] r22) {
        /*
            Method dump skipped, instructions count: 546
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.setMultiSimPolicy(com.samsung.android.knox.ContextInfo,"
                    + " int, int, java.lang.String[], java.lang.String[],"
                    + " java.lang.String[]):void");
    }

    public final boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$25()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceActiveAdminPermissionByContext);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceActiveAdminPermissionByContext.mCallerUid,
                        z,
                        0,
                        "blockNonTrustedApp");
        this.mRestrictionCache.update(
                "blockNonTrustedApp",
                4194304L,
                false,
                callingOrCurrentUserId,
                Integer.valueOf(enforceActiveAdminPermissionByContext.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setSafeModeProperty(boolean r9) {
        /*
            r8 = this;
            java.lang.String r0 = "safe_boot_disallowed"
            java.lang.String r1 = "RestrictionPolicy"
            java.lang.String r2 = "setSafeModeProperty() : already applied. = "
            long r3 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            android.content.Context r6 = r8.mContext     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            int r6 = android.provider.Settings.Global.getInt(r6, r0, r5)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            if (r9 == 0) goto L1b
            if (r6 != 0) goto L20
        L1b:
            r7 = 1
            if (r9 != 0) goto L31
            if (r6 == r7) goto L31
        L20:
            android.content.Context r8 = r8.mContext     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            r2 = r9 ^ 1
            boolean r7 = android.provider.Settings.Global.putInt(r8, r0, r2)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            goto L40
        L2d:
            r8 = move-exception
            goto L59
        L2f:
            r8 = move-exception
            goto L46
        L31:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L44
            r8.<init>(r2)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L44
            r8.append(r6)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L44
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L44
            android.util.Log.d(r1, r8)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L44
        L40:
            android.os.Binder.restoreCallingIdentity(r3)
            goto L50
        L44:
            r8 = move-exception
            r5 = r7
        L46:
            java.lang.String r0 = "setSafeModeProperty() failed."
            android.util.Log.e(r1, r0, r8)     // Catch: java.lang.Throwable -> L2d
            android.os.Binder.restoreCallingIdentity(r3)
            r7 = r5
        L50:
            if (r7 != 0) goto L58
            java.lang.String r8 = "setSafeModeProperty() failed, allow = "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r8, r1, r9)
        L58:
            return
        L59:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.setSafeModeProperty(boolean):void");
    }

    public final boolean setScreenCapture(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceRestrictionPermission.mCallerUid);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$5()).getClass();
        boolean z3 = true;
        boolean z4 = false;
        if (!SemPersonaManager.isKnoxId(userId)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceRestrictionPermission.mCallerUid,
                                z,
                                0,
                                "screenCaptureEnabled");
                try {
                    Slog.d(
                            "RestrictionPolicy",
                            "setScreenCapture : enable="
                                    + z
                                    + " callingUid="
                                    + enforceRestrictionPermission.mCallerUid);
                    this.mRestrictionCache.update(
                            "screenCaptureEnabled",
                            1099511627776L,
                            true,
                            UserHandle.getUserId(enforceRestrictionPermission.mCallerUid),
                            Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                            Boolean.valueOf(z));
                    if (putBoolean && enforceRestrictionPermission.mContainerId == 0) {
                        Utils.writePropertyValue(
                                "screenCaptureEnabled",
                                ""
                                        + (isScreenCaptureEnabled(
                                                        enforceRestrictionPermission, false)
                                                ? 1
                                                : 0),
                                "/data/system/enterprise.conf");
                    }
                    if (putBoolean) {
                        updateScreenCaptureDisabledInWindowManager(
                                userId, !isScreenCaptureEnabled(userId, false));
                    }
                    z2 = putBoolean;
                } catch (Exception unused) {
                    z4 = putBoolean;
                    Slog.e("RestrictionPolicy", "setScreenCapture error");
                    z2 = z4;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z2;
                }
            } catch (Exception unused2) {
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z2;
        }
        getPersonaManagerAdapter$5().getClass();
        try {
            ComponentName componentNameForUid =
                    this.mEdmStorageProvider.getComponentNameForUid(
                            this.mEdmStorageProvider.getMUMContainerOwnerUid(userId));
            IDevicePolicyManager asInterface =
                    IDevicePolicyManager.Stub.asInterface(
                            ServiceManager.getService("device_policy"));
            if (asInterface == null || componentNameForUid == null) {
                return false;
            }
            asInterface.setScreenCaptureDisabled(
                    componentNameForUid, componentNameForUid.getPackageName(), !z, false);
            Slog.d(
                    "RestrictionPolicy",
                    "setScreenCapture: enable="
                            + z
                            + " callingUid="
                            + enforceRestrictionPermission.mCallerUid);
            try {
                updateScreenCaptureDisabledInWindowManager(
                        userId, !isScreenCaptureEnabled(userId, false));
                return true;
            } catch (Exception e) {
                e = e;
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "setScreenCapture failed : result ", "RestrictionPolicy");
                return z3;
            }
        } catch (Exception e2) {
            e = e2;
            z3 = false;
        }
    }

    public final boolean setSdCardState(ContextInfo contextInfo, boolean z) {
        String externalSdCardPath;
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission =
                getEDM$25()
                        .enforceOwnerOnlyAndActiveAdminPermission(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String externalStorageState = Environment.getExternalStorageState();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            boolean z2 = false;
            if (externalStorageState == null) {
                return false;
            }
            Slog.d("RestrictionPolicy", "setSdCardState : " + z);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "RESTRICTION",
                                enforceOwnerOnlyAndActiveAdminPermission.mCallerUid,
                                z,
                                0,
                                "sdCardEnabled");
                this.mRestrictionCache.update(
                        "sdCardEnabled",
                        140737488355328L,
                        true,
                        0,
                        Integer.valueOf(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid),
                        Boolean.valueOf(z));
                if (z) {
                    mountSdCard();
                } else {
                    Slog.d("RestrictionPolicy", "SdCard unmount : ");
                    IStorageManager mountService = getMountService();
                    if (mountService != null
                            && (externalSdCardPath = getExternalSdCardPath()) != null) {
                        try {
                            mountService.unmountVolume(externalSdCardPath, true, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                z2 = putBoolean;
            } catch (Exception unused) {
            }
            if (z2) {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "RestrictionPolicy",
                        String.format(
                                z
                                        ? "Admin %d has enabled access to external SDCard."
                                        : "Admin %d has disabled access to external SDCard.",
                                Integer.valueOf(
                                        enforceOwnerOnlyAndActiveAdminPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid));
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
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
        String[] packagesForUid =
                this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        Log.d(
                "RestrictionPolicy",
                "setStatusBarExpansionSystemUI() package(UID) : "
                        + packagesForUid[0]
                        + "("
                        + Binder.getCallingUid()
                        + ")");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext)
                        .setStatusBarExpansionAllowedAsUser(i, packagesForUid[0], z);
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "setStatusBarExpansionSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setTethering(ContextInfo contextInfo, boolean z) {
        boolean usbTethering = setUsbTethering(contextInfo, z);
        boolean wifiTethering = setWifiTethering(contextInfo, z);
        boolean bluetoothTethering = setBluetoothTethering(contextInfo, z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setTethering : ", "RestrictionPolicy", z);
        return usbTethering && wifiTethering && bluetoothTethering;
    }

    public final boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                Settings.Global.putString(this.mContext.getContentResolver(), "adb_enabled", "0");
                Settings.Global.putString(
                        this.mContext.getContentResolver(), "adb_wifi_enabled", "0");
            } catch (Exception unused) {
                z2 = false;
            }
        }
        z2 = true;
        if (z2) {
            z2 &=
                    this.mEdmStorageProvider.putBoolean(
                            "RESTRICTION",
                            enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                            z,
                            0,
                            "usbDebuggingEnabled");
            this.mRestrictionCache.update(
                    "usbDebuggingEnabled",
                    2199023255552L,
                    true,
                    0,
                    Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                    Boolean.valueOf(z));
        }
        if (z2) {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has enabled USB debugging."
                                    : "Admin %d has disabled USB debugging.",
                            Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Slog.w("RestrictionPolicy", "setUSBDebugging : " + z);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean setUsbExceptionList(ContextInfo contextInfo, int i) {
        Log.d(
                "RestrictionPolicy",
                "setUsbExceptionList. exception mask : 0x" + Integer.toHexString(i));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!((UsbManager) this.mContext.getSystemService("usb")).isSupportDexRestrict()) {
                Log.w("RestrictionPolicy", "isSupportDexRestrict is false");
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int i2 = enforceOwnerOnlyAndRestrictionPermission(contextInfo).mCallerUid;
            if (i > RestrictionPolicy.USBInterface.OFF.getValue()) {
                Log.w(
                        "RestrictionPolicy",
                        "Exception mask is over max value. Max : 131071(USBInterface.OFF)");
                return false;
            }
            try {
                boolean updateUsbInterfaceExceptionMaskInDb =
                        updateUsbInterfaceExceptionMaskInDb(i, i2);
                Log.d(
                        "RestrictionPolicy",
                        "Usb Exception mask input DB : " + updateUsbInterfaceExceptionMaskInDb);
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setUsbExceptionListOnDriver(int r10) {
        /*
            r9 = this;
            java.lang.String r0 = "Usb Exception mask input USB Driver : "
            java.lang.String r1 = "set UsbInterface Exception : "
            long r2 = android.os.Binder.clearCallingIdentity()
            android.content.Context r4 = r9.mContext     // Catch: java.lang.Throwable -> L22
            java.lang.String r5 = "usb"
            java.lang.Object r4 = r4.getSystemService(r5)     // Catch: java.lang.Throwable -> L22
            android.hardware.usb.UsbManager r4 = (android.hardware.usb.UsbManager) r4     // Catch: java.lang.Throwable -> L22
            r5 = -1
            java.lang.String r6 = "RestrictionPolicy"
            if (r4 != 0) goto L25
            java.lang.String r9 = "UsbManager is null"
            android.util.Log.e(r6, r9)     // Catch: java.lang.Throwable -> L22
            android.os.Binder.restoreCallingIdentity(r2)
            return r5
        L22:
            r9 = move-exception
            goto L9c
        L25:
            boolean r7 = r4.isSupportDexRestrict()     // Catch: java.lang.Throwable -> L22
            if (r7 != 0) goto L35
            java.lang.String r9 = "isSupportDexRestrict is false"
            android.util.Log.w(r6, r9)     // Catch: java.lang.Throwable -> L22
            android.os.Binder.restoreCallingIdentity(r2)
            return r5
        L35:
            r7 = 0
            r8 = 0
            boolean r9 = r9.isUsbHostStorageAllowed(r7, r8)     // Catch: java.lang.Throwable -> L22
            if (r9 == 0) goto L92
            r9 = 1
            if (r10 == r5) goto L72
            com.samsung.android.knox.restriction.RestrictionPolicy$USBInterface r5 = com.samsung.android.knox.restriction.RestrictionPolicy.USBInterface.OFF     // Catch: java.lang.Throwable -> L22
            int r5 = r5.getValue()     // Catch: java.lang.Throwable -> L22
            if (r10 != r5) goto L49
            goto L72
        L49:
            com.samsung.android.knox.restriction.RestrictionPolicy$USBInterface r5 = com.samsung.android.knox.restriction.RestrictionPolicy.USBInterface.ABL     // Catch: java.lang.Throwable -> L22
            int r7 = r5.getValue()     // Catch: java.lang.Throwable -> L22
            if (r10 != r7) goto L5a
            java.lang.String r10 = r5.toString()     // Catch: java.lang.Throwable -> L22
            int r10 = r4.restrictUsbHostInterface(r9, r10)     // Catch: java.lang.Throwable -> L22
            goto L7c
        L5a:
            java.lang.String r10 = getRestrictionList(r10)     // Catch: java.lang.Throwable -> L22
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L22
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L22
            r5.append(r10)     // Catch: java.lang.Throwable -> L22
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L22
            android.util.Log.d(r6, r1)     // Catch: java.lang.Throwable -> L22
            int r10 = r4.restrictUsbHostInterface(r9, r10)     // Catch: java.lang.Throwable -> L22
            goto L7c
        L72:
            com.samsung.android.knox.restriction.RestrictionPolicy$USBInterface r10 = com.samsung.android.knox.restriction.RestrictionPolicy.USBInterface.OFF     // Catch: java.lang.Throwable -> L22
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L22
            int r10 = r4.restrictUsbHostInterface(r8, r10)     // Catch: java.lang.Throwable -> L22
        L7c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L22
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L22
            if (r10 != 0) goto L84
            r8 = r9
        L84:
            r1.append(r8)     // Catch: java.lang.Throwable -> L22
            java.lang.String r9 = r1.toString()     // Catch: java.lang.Throwable -> L22
            android.util.Log.d(r6, r9)     // Catch: java.lang.Throwable -> L22
            android.os.Binder.restoreCallingIdentity(r2)
            return r10
        L92:
            java.lang.String r9 = "isUsbHostStorageAllowed is false. pass set UsbInterface Exception"
            android.util.Log.d(r6, r9)     // Catch: java.lang.Throwable -> L22
            android.os.Binder.restoreCallingIdentity(r2)
            return r5
        L9c:
            android.os.Binder.restoreCallingIdentity(r2)
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicy.setUsbExceptionListOnDriver(int):int");
    }

    public final boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) {
        enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        return false;
    }

    public final boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) {
        enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        return false;
    }

    public final boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRestrictionPermission =
                enforceOwnerOnlyAndRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "usbMediaPlayerEnabled");
        this.mRestrictionCache.update(
                "usbMediaPlayerEnabled",
                562949953421312L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        if (putBoolean && !z) {
            try {
                Intent intent =
                        new Intent("com.samsung.android.knox.intent.action.MTP_DISABLED_INTERNAL");
                intent.addFlags(536870912);
                this.mContext.sendBroadcast(intent);
                Intent intent2 = new Intent("android.hardware.usb.action.USB_STATE");
                intent2.addFlags(536870912);
                intent2.putExtra("mtp", false);
                intent2.putExtra("unlocked", z);
                intent2.putExtra("ptp", false);
                this.mContext.sendStickyBroadcastAsUser(intent2, UserHandle.ALL);
            } catch (Exception unused) {
                Slog.w("RestrictionPolicy", "Error in RestrictionPolicy after setting MTP policy");
            }
        }
        Slog.w("RestrictionPolicy", "setUSBMediaPlayerAvailability: " + z);
        if (putBoolean) {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has enabled USB Media Player (MTP)."
                                    : "Admin %d has disabled USB Media Player (MTP).",
                            Integer.valueOf(enforceOwnerOnlyAndRestrictionPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndRestrictionPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public final boolean setUsbTethering(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndTetheringPermission =
                enforceOwnerOnlyAndTetheringPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.d("RestrictionPolicy", "failed to setUsbTethering due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z
                && (((UsbManager) this.mContext.getSystemService("usb")).getCurrentFunctions() & 32)
                        != 0) {
            ((TetheringManager) this.mContext.getSystemService(TetheringManager.class))
                    .stopTethering(1);
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndTetheringPermission.mCallerUid,
                        z,
                        0,
                        "usbTetheringEnabled");
        this.mRestrictionCache.update(
                "usbTetheringEnabled",
                137438953472L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid),
                Boolean.valueOf(z));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setUsbTethering : ", "RestrictionPolicy", z);
        if (putBoolean) {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "RestrictionPolicy",
                    String.format(
                            z
                                    ? "Admin %d has enabled USB Tethering setting."
                                    : "Admin %d has disabled USB Tethering setting.",
                            Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)),
                    UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public final boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceRestrictionPermission = enforceRestrictionPermission(contextInfo);
        Slog.d("RestrictionPolicy", "setUseSecureKeypad : " + z);
        Slog.d(
                "RestrictionPolicy",
                "setUseSecureKeypad containerID : " + enforceRestrictionPermission.mContainerId);
        IPersonaManagerAdapter personaManagerAdapter$5 = getPersonaManagerAdapter$5();
        int i = enforceRestrictionPermission.mContainerId;
        ((PersonaManagerAdapter) personaManagerAdapter$5).getClass();
        if (!SemPersonaManager.isSamsungWorkspace(i)) {
            Slog.d("RestrictionPolicy", "setUseSecureKeypad fails. PO not supported.");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceRestrictionPermission.mCallerUid,
                        z,
                        0,
                        "useSecureKeypad");
        this.mRestrictionCache.update(
                "useSecureKeypad",
                2097152L,
                false,
                Utils.getCallingOrCurrentUserId(enforceRestrictionPermission),
                Integer.valueOf(enforceRestrictionPermission.mCallerUid),
                Boolean.valueOf(z));
        return putBoolean;
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

    public final boolean setWifiTethering(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndTetheringPermission =
                enforceOwnerOnlyAndTetheringPermission(contextInfo);
        Log.i(
                "RestrictionPolicy",
                "setWifiTethering - caller uid: "
                        + enforceOwnerOnlyAndTetheringPermission.mCallerUid
                        + ", enable: "
                        + z);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndTetheringPermission.mCallerUid) {
            Log.e("RestrictionPolicy", "failed to setWifiTethering due to Ethernet only mode");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "RESTRICTION",
                        enforceOwnerOnlyAndTetheringPermission.mCallerUid,
                        z,
                        0,
                        "wifiTetheringEnabled");
        if (!putBoolean) {
            Log.e("RestrictionPolicy", "setWifiTethering - fail to store value to database");
            return putBoolean;
        }
        this.mRestrictionCache.update(
                "wifiTetheringEnabled",
                274877906944L,
                true,
                0,
                Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid),
                Boolean.valueOf(z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndTetheringPermission.mCallerUid),
                    z ? 55 : 56,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndTetheringPermission.mCallerUid)
                    });
            setWifiTetheringAllowedSystemUI(0, isWifiTetheringEnabled(null));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setWifiTetheringUserRestriction(!isWifiTetheringEnabled(null));
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
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

    public final void setWifiTetheringUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManager.setUserRestriction("no_wifi_tethering", z);
            Log.i("RestrictionPolicy", "setWifiTetheringUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showRestrictionToast(String str) {
        RestrictionToastManager.show(str);
    }

    public final boolean showToastIfIntelligenceOnlineProcessingDisallowed(int i) {
        List list;
        if (isIntelligenceOnlineProcessingAllowed(i)) {
            list = Collections.emptyList();
        } else {
            RestrictionPolicyCache restrictionPolicyCache = this.mRestrictionCache;
            restrictionPolicyCache.getClass();
            ArrayList arrayList = new ArrayList();
            RestrictionPolicyCache.ApplyingAdmins applyingAdmins =
                    restrictionPolicyCache.mApplyingAdmins;
            if (((HashMap) applyingAdmins.admins).get(Integer.valueOf(i)) == null) {
                Log.d("RestrictionPolicy", "no admin data present for userId " + i);
                list = Collections.emptyList();
            } else {
                for (Integer num :
                        (Set)
                                ((Map) ((HashMap) applyingAdmins.admins).get(Integer.valueOf(i)))
                                        .getOrDefault(35184372088832L, null)) {
                    String str = (String) ((HashMap) applyingAdmins.adminInfoMap).get(num);
                    if (str != null) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                try {
                                    String charSequence =
                                            restrictionPolicyCache
                                                    .mPackageManager
                                                    .getApplicationLabel(
                                                            restrictionPolicyCache.mPackageManager
                                                                    .getApplicationInfoAsUser(
                                                                            str,
                                                                            0,
                                                                            UserHandle.getUserId(
                                                                                    num
                                                                                            .intValue())))
                                                    .toString();
                                    if (charSequence != null && !charSequence.isEmpty()) {
                                        arrayList.add(charSequence);
                                    }
                                } catch (PackageManager.NameNotFoundException unused) {
                                    Log.e(
                                            "RestrictionPolicy",
                                            String.format(
                                                    "Package(%s) name not found for user %d",
                                                    str, Integer.valueOf(i)));
                                }
                            } catch (Exception unused2) {
                                Log.e(
                                        "RestrictionPolicy",
                                        String.format(
                                                "Admin(%s) app label not found for user %d",
                                                str, Integer.valueOf(i)));
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                list = arrayList;
            }
            Slog.d("RestrictionPolicy", "getIntelligenceOnlineProcessingAdminsListAsUser: " + list);
        }
        if (list.isEmpty()) {
            return false;
        }
        if (list.size() == 1) {
            RestrictionToastManager.show(
                    String.format(this.mContext.getString(17042474), list.get(0)));
        } else {
            RestrictionToastManager.show(17042785);
        }
        return true;
    }

    public final void storeRestrictionsValuesByKC() {
        ContentValues contentValues = new ContentValues();
        StringJoiner stringJoiner = new StringJoiner(";");
        Iterator it = ((ArraySet) this.mUserRestrictionEnforcedByKC).iterator();
        while (it.hasNext()) {
            stringJoiner.add((String) it.next());
        }
        contentValues.put("value", stringJoiner.toString());
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("name", "userRestrictionsSetByKc");
        this.mEdmStorageProvider.put("generic", contentValues, contentValues2);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        StorageVolume[] volumeList;
        this.mRestrictionCache.load(-1);
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService("storage");
        if (storageManager != null
                && (volumeList = storageManager.getVolumeList()) != null
                && volumeList.length > 1) {
            for (StorageVolume storageVolume : volumeList) {
                if (storageVolume != null && storageVolume.allowMassStorage()) {
                    break;
                }
            }
        }
        Collection arrayList = new ArrayList();
        String string =
                this.mEdmStorageProvider.getString(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                "name", "userRestrictionsSetByKc"),
                        "generic",
                        "value");
        if (!TextUtils.isEmpty(string)) {
            Log.d("RestrictionPolicy", "Restrictions Aplied by KC: " + string);
            arrayList = Arrays.asList(string.split(";"));
        }
        this.mUserRestrictionEnforcedByKC = new ArraySet(arrayList);
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminName",
                com.samsung.android.knox.restriction.RestrictionPolicy.KC_COMPONENT_NAME
                        .flattenToString());
        this.mKcUid = this.mEdmStorageProvider.getInt(contentValues, "ADMIN_INFO", "adminUid");
        LocalServices.addService(RestrictionPolicyInternal.class, new LocalService());
        Slog.d("RestrictionPolicy", "systemReady()");
    }

    public final void systemReady(int i) {
        Log.d("RestrictionPolicy", "systemReady() : mCurrentPhase = " + i);
        registerSubscriptionCallback();
        if (i == 500) {
            this.mConstrainedState =
                    (ConstrainedModeInternal)
                            LocalServices.getService(ConstrainedModeInternal.class);
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

    public final void turnOffAirPlaneMode() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                        .setAirplaneMode(false);
            } catch (Exception unused) {
                Log.w("RestrictionPolicy", "turnOffAirPlaneMode() failed. ");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateNonMarketAppOnUserManager() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                StringBuilder sb =
                        new StringBuilder(
                                "UserManager.DISALLOW_NON_MARKET_APP_BY_KNOX will be set as ");
                sb.append(!isNonMarketAppAllowed(null));
                Slog.d("RestrictionPolicy", sb.toString());
                this.mUserManager.setUserRestriction(
                        "no_non_market_app_by_knox",
                        !isNonMarketAppAllowed(null),
                        UserHandle.OWNER);
            } catch (SecurityException e) {
                throw new SecurityException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateRestrictionCacheForWpcod() {
        Log.d("RestrictionPolicy", "updateRestrictionCacheForWpcod() called..");
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        try {
            this.mRestrictionCache.update("backupEnabled", 17592186044416L, true, 0, null, null);
            this.mRestrictionCache.update("clipboardEnabled", 512L, true, 0, null, null);
            this.mRestrictionCache.update(
                    "factoryresetallowed", 2251799813685248L, true, 0, null, null);
            this.mRestrictionCache.update("allowClipboardShare", 131072L, true, 0, null, null);
            this.mRestrictionCache.update(
                    "allowGoogleAccountsAutoSync", 33554432L, true, 0, null, null);
            SecContentProviderUtil.notifyPolicyChangesAllUser(
                    this.mContext,
                    Uri.parse(
                            "content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed"));
            SecContentProviderUtil.notifyPolicyChangesAllUser(
                    this.mContext,
                    Uri.parse(
                            "content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed"));
            manageEFSFile("/efs/MDM/FactoryReset", true);
        } catch (Exception e) {
            e.printStackTrace();
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("updateRestrictionCacheForWpcod() error: "),
                    "RestrictionPolicy");
        }
    }

    public final void updateScreenCaptureDisabledInWindowManager(int i, boolean z) {
        Log.i(
                "RestrictionPolicy",
                "updateScreenCaptureDisabledInWindowManager() userId = " + i + ", disabled = " + z);
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider/RestrictionPolicy3/isScreenCaptureEnabled"));
        BackgroundThread.getHandler().post(new RestrictionPolicy$$ExternalSyntheticLambda1());
    }

    public final void updateSystemUIMonitor$7(int i) {
        setSettingsChangeSystemUI(i, isSettingsChangesAllowedAsUser(false, i));
        setStatusBarExpansionSystemUI(i, isStatusBarExpansionAllowedAsUser(false, i));
        setAirplaneModeAllowedSystemUI(i, isAirplaneModeAllowed(false));
        setCellularDataAllowedSystemUI(i, isCellularDataAllowed(null));
        setWifiTetheringAllowedSystemUI(i, isWifiTetheringEnabled(null));
        setCameraAllowedSystemUI(i, this.mRestrictionCache.extract(i, 68719476736L, true));
        setFaceRecognitionEvenCameraBlockedAllowedSystemUI(
                i, isFaceRecognitionAllowedEvenCameraBlocked(null));
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [long] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    public final boolean updateUsbStorageStatebyIntent(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL");
                intent.putExtra("reason", !z ? 1 : 0);
                this.mContext.sendBroadcast(intent);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = true;
            } catch (Exception e) {
                Log.e("RestrictionPolicy", "updateUsbStorageStatebyIntent() fas failed.", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            clearCallingIdentity = "updateUsbStorageStatebyIntent() allow = ";
            Slog.d(
                    "RestrictionPolicy",
                    "updateUsbStorageStatebyIntent() allow = " + z + ", ret = " + z2);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateUserRestrictionsByKC(String str, boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_USERS") == 0
                && this.mKcUid != -1) {
            if (z) {
                if (Binder.getCallingUid() == this.mKcUid) {
                    ((ArraySet) this.mUserRestrictionEnforcedByKC).add(str);
                    storeRestrictionsValuesByKC();
                    return;
                }
                return;
            }
            if (((ArraySet) this.mUserRestrictionEnforcedByKC).contains(str)) {
                ((ArraySet) this.mUserRestrictionEnforcedByKC).remove(str);
                storeRestrictionsValuesByKC();
            }
        }
    }
}
