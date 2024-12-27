package com.android.server.enterprise.security;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.fingerprint.FingerprintManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.android.server.knox.dar.sdp.SDPLog;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.license.LicenseResult;
import com.samsung.android.knox.localservice.PasswordPolicyInternal;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordPolicy extends IPasswordPolicy.Stub implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static final int[] BIOMETRIC_AUTHENTICATION_TYPES = {1, 4};
    public final AnonymousClass2 mBlocker;
    public final AnonymousClass4 mBroadCastReceiver;
    public final List mCallersWhitelist;
    public final Context mContext;
    public final DevicePolicyManager mDpm;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public KeyCodeMediatorImpl mKeyCodeMediator;
    public EnterpriseLicenseService mLicenseService;
    public final PowerManager mPM;
    public final IPersonaManagerAdapter mPersonaManagerAdapter;
    public final PasswordPolicyCache mPolicyCache;
    public final AnonymousClass4 mReceiver;
    public final IDevicePolicyManager mService;
    public IStatusBarService mStatusBarService;
    public final TelephonyManager mTelManager;
    public final IBinder mToken;
    public final UserManager mUserManager;

    public final class ActivationMonitor implements IActivationKlmElmObserver {
        public ActivationMonitor() {
            if (PasswordPolicy.this.mLicenseService == null) {
                PasswordPolicy.this.mLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
            }
            EnterpriseLicenseService enterpriseLicenseService = PasswordPolicy.this.mLicenseService;
            if (enterpriseLicenseService != null) {
                enterpriseLicenseService.enforcePermission$1();
                ((ArrayList) enterpriseLicenseService.mKlmElmChangeList).add(this);
            }
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateElm(String str, LicenseResult licenseResult) {
            PasswordPolicy passwordPolicy = PasswordPolicy.this;
            Log.d("PasswordPolicy", "onUpdateElm is called");
            try {
                ((PersonaManagerAdapter) passwordPolicy.mPersonaManagerAdapter).getClass();
                if (SemPersonaManager.isDoEnabled(0) && licenseResult.isSuccess() && licenseResult.getType() == LicenseResult.Type.ELM_VALIDATION && passwordPolicy.mLicenseService != null) {
                    ComponentName deviceOwnerComponentOnAnyUser = passwordPolicy.mDpm.getDeviceOwnerComponentOnAnyUser();
                    if (str == null || deviceOwnerComponentOnAnyUser == null || !str.equals(deviceOwnerComponentOnAnyUser.getPackageName())) {
                        return;
                    }
                    boolean isServiceAvailable = passwordPolicy.mLicenseService.isServiceAvailable(str, "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                    Log.d("PasswordPolicy", "onUpdateElm - isServiceAvailable : " + isServiceAvailable);
                    if (!isServiceAvailable || passwordPolicy.mUserManager.getUserInfo(0).isAdminLocked()) {
                        return;
                    }
                    passwordPolicy.setAdminLockEnabledSystemUI(0, false, false);
                }
            } catch (Exception e) {
                Log.e("PasswordPolicy", "onUpdateElm() failed ", e);
            }
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateKlm(String str, LicenseResult licenseResult) {
            PasswordPolicy passwordPolicy = PasswordPolicy.this;
            Log.d("PasswordPolicy", "onUpdateKlm is called");
            try {
                ((PersonaManagerAdapter) passwordPolicy.mPersonaManagerAdapter).getClass();
                if (SemPersonaManager.isDoEnabled(0) && licenseResult.isSuccess() && licenseResult.getType() == LicenseResult.Type.KLM_VALIDATION && passwordPolicy.mLicenseService != null) {
                    ComponentName deviceOwnerComponentOnAnyUser = passwordPolicy.mDpm.getDeviceOwnerComponentOnAnyUser();
                    if (str == null || deviceOwnerComponentOnAnyUser == null || !str.equals(deviceOwnerComponentOnAnyUser.getPackageName())) {
                        return;
                    }
                    boolean isServiceAvailable = passwordPolicy.mLicenseService.isServiceAvailable(str, "com.samsung.android.knox.permission.KNOX_CONTAINER");
                    Log.d("PasswordPolicy", "onUpdateKlm - isServiceAvailable : " + isServiceAvailable);
                    if (!isServiceAvailable || passwordPolicy.mUserManager.getUserInfo(0).isAdminLocked()) {
                        return;
                    }
                    passwordPolicy.setAdminLockEnabledSystemUI(0, false, false);
                }
            } catch (Exception e) {
                Log.e("PasswordPolicy", "onUpdateKlm() failed ", e);
            }
        }
    }

    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    class LocalService extends PasswordPolicyInternal {
        public LocalService() {
        }

        public final int isChangeRequestedAsUser(int i) {
            int intValue;
            PasswordPolicyCache passwordPolicyCache = PasswordPolicy.this.mPolicyCache;
            synchronized (passwordPolicyCache.mLock) {
                if (((HashMap) passwordPolicyCache.mChangeRequested).get(Integer.valueOf(i)) == null) {
                    intValue = 0;
                } else {
                    intValue = ((Integer) ((HashMap) passwordPolicyCache.mChangeRequested).get(Integer.valueOf(i))).intValue();
                }
            }
            return intValue;
        }
    }

    public PasswordPolicy(Context context) {
        Injector injector = new Injector(context);
        this.mLicenseService = null;
        this.mCallersWhitelist = new ArrayList() { // from class: com.android.server.enterprise.security.PasswordPolicy.1
            {
                add("com.samsung.android.knox.containercore");
            }
        };
        this.mBlocker = new SemDesktopModeManager.DesktopModeBlocker() { // from class: com.android.server.enterprise.security.PasswordPolicy.2
            public final String onBlocked() {
                return PasswordPolicy.this.mContext.getString(R.string.heavy_weight_switcher_text);
            }
        };
        this.mStatusBarService = null;
        this.mToken = new Binder();
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.security.PasswordPolicy.4
            public final /* synthetic */ PasswordPolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int i2;
                switch (i) {
                    case 0:
                        if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                            this.this$0.updateSystemUIMonitor$9(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                            break;
                        }
                        break;
                    default:
                        String action = intent.getAction();
                        int sendingUserId = getSendingUserId();
                        this.this$0.mInjector.getClass();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        int currentUser = ActivityManager.getCurrentUser();
                        if ("com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL".equals(action)) {
                            PasswordPolicy passwordPolicy = this.this$0;
                            if (passwordPolicy.isChangeRequestedAsUserFromDb(sendingUserId) == -1) {
                                int currentUser2 = ActivityManager.getCurrentUser();
                                if (passwordPolicy.mTelManager.getCallState() == 0 || sendingUserId != currentUser2) {
                                    passwordPolicy.setPwdChangeRequestedForUser(2, sendingUserId);
                                    passwordPolicy.changePasswordAsUser(sendingUserId);
                                } else {
                                    passwordPolicy.setPwdChangeRequestedForUser(-3, sendingUserId);
                                }
                            }
                        } else if ("android.intent.action.USER_STARTED".equals(action)) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra >= 0) {
                                PasswordPolicy passwordPolicy2 = this.this$0;
                                int isChangeRequestedAsUserFromDb = passwordPolicy2.isChangeRequestedAsUserFromDb(intExtra);
                                if (isChangeRequestedAsUserFromDb != -4) {
                                    if (isChangeRequestedAsUserFromDb != -3) {
                                        if (isChangeRequestedAsUserFromDb == -2) {
                                            i2 = 1;
                                        } else if (isChangeRequestedAsUserFromDb != -1) {
                                            i2 = 0;
                                        }
                                    }
                                    i2 = 2;
                                } else {
                                    i2 = 3;
                                }
                                if (i2 != 0) {
                                    passwordPolicy2.setPwdChangeRequestedForUser(i2, intExtra);
                                }
                            }
                        } else if ("android.intent.action.USER_SWITCHED".equals(action)) {
                            int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra2 >= 0) {
                                PasswordPolicy passwordPolicy3 = this.this$0;
                                int isChangeRequestedAsUserFromDb2 = passwordPolicy3.isChangeRequestedAsUserFromDb(intExtra2);
                                boolean hasPassword = passwordPolicy3.hasPassword(intExtra2);
                                if (isChangeRequestedAsUserFromDb2 > 0 && !hasPassword) {
                                    passwordPolicy3.changePasswordAsUser(intExtra2);
                                }
                            }
                        } else if ("android.intent.action.PHONE_STATE".equals(action)) {
                            if (this.this$0.mTelManager.getCallState() == 0) {
                                PasswordPolicy passwordPolicy4 = this.this$0;
                                int isChangeRequestedAsUserFromDb3 = passwordPolicy4.isChangeRequestedAsUserFromDb(currentUser);
                                int i3 = isChangeRequestedAsUserFromDb3 != -4 ? isChangeRequestedAsUserFromDb3 != -3 ? isChangeRequestedAsUserFromDb3 != -2 ? 0 : 1 : 2 : 3;
                                if (i3 > 0) {
                                    passwordPolicy4.setPwdChangeRequestedForUser(i3, currentUser);
                                    passwordPolicy4.changePasswordAsUser(currentUser);
                                }
                            }
                        } else if ("com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL".equals(action)) {
                            Log.i("PasswordPolicy", "Received ACTION_PASSWORD_EXPIRING_NOTIFICATION_INTERNAL intent");
                            long longExtra = intent.getLongExtra("expiration", -1L);
                            if (longExtra == -1 || longExtra > System.currentTimeMillis()) {
                                GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m("In grace period or failed to get ", longExtra, "PasswordPolicy");
                                this.this$0.mInjector.getClass();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                break;
                            } else {
                                Log.i("PasswordPolicy", "Password expired already so launching password screen");
                                ((PersonaManagerAdapter) this.this$0.mPersonaManagerAdapter).getClass();
                                if (SemPersonaManager.isKnoxId(sendingUserId)) {
                                    try {
                                        ActivityManagerNative.getDefault().forceStopPackage(KnoxCustomManagerService.SETTING_PKG_NAME, sendingUserId);
                                    } catch (RemoteException unused) {
                                        Log.d("PasswordPolicy", "forceStopPackage failed");
                                    }
                                }
                                this.this$0.enforcePwdChangeForUser(0, sendingUserId);
                            }
                        }
                        this.this$0.mInjector.getClass();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        break;
                }
            }
        };
        this.mEDM = null;
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.security.PasswordPolicy.4
            public final /* synthetic */ PasswordPolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int i22;
                switch (i2) {
                    case 0:
                        if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                            this.this$0.updateSystemUIMonitor$9(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                            break;
                        }
                        break;
                    default:
                        String action = intent.getAction();
                        int sendingUserId = getSendingUserId();
                        this.this$0.mInjector.getClass();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        int currentUser = ActivityManager.getCurrentUser();
                        if ("com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL".equals(action)) {
                            PasswordPolicy passwordPolicy = this.this$0;
                            if (passwordPolicy.isChangeRequestedAsUserFromDb(sendingUserId) == -1) {
                                int currentUser2 = ActivityManager.getCurrentUser();
                                if (passwordPolicy.mTelManager.getCallState() == 0 || sendingUserId != currentUser2) {
                                    passwordPolicy.setPwdChangeRequestedForUser(2, sendingUserId);
                                    passwordPolicy.changePasswordAsUser(sendingUserId);
                                } else {
                                    passwordPolicy.setPwdChangeRequestedForUser(-3, sendingUserId);
                                }
                            }
                        } else if ("android.intent.action.USER_STARTED".equals(action)) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra >= 0) {
                                PasswordPolicy passwordPolicy2 = this.this$0;
                                int isChangeRequestedAsUserFromDb = passwordPolicy2.isChangeRequestedAsUserFromDb(intExtra);
                                if (isChangeRequestedAsUserFromDb != -4) {
                                    if (isChangeRequestedAsUserFromDb != -3) {
                                        if (isChangeRequestedAsUserFromDb == -2) {
                                            i22 = 1;
                                        } else if (isChangeRequestedAsUserFromDb != -1) {
                                            i22 = 0;
                                        }
                                    }
                                    i22 = 2;
                                } else {
                                    i22 = 3;
                                }
                                if (i22 != 0) {
                                    passwordPolicy2.setPwdChangeRequestedForUser(i22, intExtra);
                                }
                            }
                        } else if ("android.intent.action.USER_SWITCHED".equals(action)) {
                            int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra2 >= 0) {
                                PasswordPolicy passwordPolicy3 = this.this$0;
                                int isChangeRequestedAsUserFromDb2 = passwordPolicy3.isChangeRequestedAsUserFromDb(intExtra2);
                                boolean hasPassword = passwordPolicy3.hasPassword(intExtra2);
                                if (isChangeRequestedAsUserFromDb2 > 0 && !hasPassword) {
                                    passwordPolicy3.changePasswordAsUser(intExtra2);
                                }
                            }
                        } else if ("android.intent.action.PHONE_STATE".equals(action)) {
                            if (this.this$0.mTelManager.getCallState() == 0) {
                                PasswordPolicy passwordPolicy4 = this.this$0;
                                int isChangeRequestedAsUserFromDb3 = passwordPolicy4.isChangeRequestedAsUserFromDb(currentUser);
                                int i3 = isChangeRequestedAsUserFromDb3 != -4 ? isChangeRequestedAsUserFromDb3 != -3 ? isChangeRequestedAsUserFromDb3 != -2 ? 0 : 1 : 2 : 3;
                                if (i3 > 0) {
                                    passwordPolicy4.setPwdChangeRequestedForUser(i3, currentUser);
                                    passwordPolicy4.changePasswordAsUser(currentUser);
                                }
                            }
                        } else if ("com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL".equals(action)) {
                            Log.i("PasswordPolicy", "Received ACTION_PASSWORD_EXPIRING_NOTIFICATION_INTERNAL intent");
                            long longExtra = intent.getLongExtra("expiration", -1L);
                            if (longExtra == -1 || longExtra > System.currentTimeMillis()) {
                                GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m("In grace period or failed to get ", longExtra, "PasswordPolicy");
                                this.this$0.mInjector.getClass();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                break;
                            } else {
                                Log.i("PasswordPolicy", "Password expired already so launching password screen");
                                ((PersonaManagerAdapter) this.this$0.mPersonaManagerAdapter).getClass();
                                if (SemPersonaManager.isKnoxId(sendingUserId)) {
                                    try {
                                        ActivityManagerNative.getDefault().forceStopPackage(KnoxCustomManagerService.SETTING_PKG_NAME, sendingUserId);
                                    } catch (RemoteException unused) {
                                        Log.d("PasswordPolicy", "forceStopPackage failed");
                                    }
                                }
                                this.this$0.enforcePwdChangeForUser(0, sendingUserId);
                            }
                        }
                        this.this$0.mInjector.getClass();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        break;
                }
            }
        };
        this.mInjector = injector;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mService = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        this.mDpm = (DevicePolicyManager) context.getSystemService("device_policy");
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        this.mPM = (PowerManager) context.getSystemService("power");
        this.mPersonaManagerAdapter = (IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
        context.registerReceiverAsUser(broadcastReceiver2, UserHandle.ALL, VcnManagementService$$ExternalSyntheticOutline0.m("com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL", "android.intent.action.PHONE_STATE", "com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL", "android.intent.action.USER_STARTED", "android.intent.action.USER_SWITCHED"), null, null, 2);
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"), 2);
        this.mTelManager = (TelephonyManager) context.getSystemService("phone");
        Log.d("PasswordPolicy", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.server.enterprise.security.PasswordPolicy.3
                public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    if (semDesktopModeState.state == 20 && semDesktopModeState.enabled == 3) {
                        Log.d("PasswordPolicy", "listner - Dex Enabling");
                        if (PasswordPolicy.this.isChangeRequestedAsUserFromDb(0) != 0) {
                            PasswordPolicy passwordPolicy = PasswordPolicy.this;
                            Injector injector2 = passwordPolicy.mInjector;
                            PasswordPolicy$$ExternalSyntheticLambda51 passwordPolicy$$ExternalSyntheticLambda51 = new PasswordPolicy$$ExternalSyntheticLambda51(1, passwordPolicy);
                            injector2.getClass();
                            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda51);
                        }
                    }
                    if (semDesktopModeState.state == 0 && semDesktopModeState.enabled == 2) {
                        Log.d("PasswordPolicy", "listener - Dex Disabled");
                        if (PasswordPolicy.this.isChangeRequestedAsUserFromDb(0) != 0) {
                            PasswordPolicy.this.changePasswordAsUserInternal(0);
                        }
                    }
                }
            });
        }
        LocalServices.addService(PasswordPolicyInternal.class, new LocalService());
        this.mPolicyCache = PasswordPolicyCache.INSTANCE;
        for (UserInfo userInfo : userManager.getUsers()) {
            PasswordPolicyCache passwordPolicyCache = this.mPolicyCache;
            int i3 = userInfo.id;
            passwordPolicyCache.setChangeRequestedAsUser(i3, isChangeRequestedAsUserFromDb(i3));
        }
        new ActivationMonitor();
    }

    public static IPersonaManagerAdapter getPersonaManagerAdapter$6() {
        return (IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
    }

    public final int ChooseNewPasswordOwner(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList adminUidList = this.mEdmStorageProvider.getAdminUidList();
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory");
        int i2 = -1;
        if (genericValueAsUser != null && genericValueAsUser.length() != 0) {
            ArrayList arrayList = new ArrayList();
            for (String str : genericValueAsUser.split(",")) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str)));
            }
            arrayList.remove(arrayList.size() - 1);
            boolean z = false;
            for (int size = arrayList.size() - 1; size >= 0 && !z; size--) {
                Iterator it = adminUidList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Integer num = (Integer) it.next();
                    if (num.intValue() == ((Integer) arrayList.get(size)).intValue()) {
                        i2 = num.intValue();
                        z = true;
                        break;
                    }
                }
                if (z) {
                    break;
                }
                arrayList.remove(size);
            }
            if (arrayList.isEmpty()) {
                this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory", null);
                this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordPatternOwner", null);
            } else {
                StringBuilder sb = new StringBuilder();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    sb.append(((Integer) it2.next()).intValue() + ",");
                }
                this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory", sb.substring(0, sb.length() - 1));
                this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordPatternOwner", String.valueOf(i2));
            }
        }
        return i2;
    }

    public final boolean addRequiredPasswordPattern(ContextInfo contextInfo, String str) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (str == null || str.length() == 0) {
            return false;
        }
        String string = this.mEdmStorageProvider.getString(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", "passwordRequiredPattern");
        if (string != null) {
            str = AnyMotionDetector$$ExternalSyntheticOutline0.m(string, "|", str);
        }
        return this.mEdmStorageProvider.putString(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", "passwordRequiredPattern", str);
    }

    public final void changePasswordAsUser(int i) {
        if (i != 0) {
            changePasswordAsUserInternal(i);
            return;
        }
        if (Utils.isDexActivated(this.mContext)) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda51 passwordPolicy$$ExternalSyntheticLambda51 = new PasswordPolicy$$ExternalSyntheticLambda51(1, this);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda51);
            Log.d("PasswordPolicy", "show pw change window after dex is disabled");
        } else {
            changePasswordAsUserInternal(i);
            Injector injector2 = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda51 passwordPolicy$$ExternalSyntheticLambda512 = new PasswordPolicy$$ExternalSyntheticLambda51(1, this);
            injector2.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda512);
            Log.d("PasswordPolicy", "show pw change window immediately");
        }
        setHomeAndRecentKey(1);
    }

    public final void changePasswordAsUserInternal(int i) {
        try {
            if (isPersona(i)) {
                postPwdResetEventToPersona(i);
                return;
            }
            if (ActivityManager.getCurrentUser() == i) {
                UserHandle userHandle = new UserHandle(i);
                if (hasPassword(i)) {
                    this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL"), userHandle);
                    return;
                }
                Intent intent = new Intent();
                intent.setClassName(KnoxCustomManagerService.SETTING_PKG_NAME, "com.android.settings.password.ChooseLockGeneric");
                intent.addFlags(268435456);
                intent.addFlags(4194304);
                intent.addFlags(8388608);
                intent.putExtra("lockscreen.password_isenforced", true);
                this.mContext.startActivityAsUser(intent, userHandle);
            }
        } catch (Exception e) {
            Log.e("PasswordPolicy", "handled expected Exception in changePasswordAsUser().", e);
        }
    }

    public final void checkPackageCallerOrEnforceSystemUser() {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        if ((nameForUid == null || !nameForUid.equals("android.uid.system:1000")) && !((ArrayList) this.mCallersWhitelist).contains(nameForUid)) {
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getAppId(callingUid) != 5250 && UserHandle.getAppId(callingUid) != Process.myUid()) {
                throw new SecurityException("Can only be called by system user");
            }
        }
    }

    public final boolean clearResetPasswordToken(ContextInfo contextInfo, ComponentName componentName) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            int i = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, i, componentName, 4);
            injector.getClass();
            bool = (Boolean) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return bool.booleanValue();
    }

    public final boolean deleteAllRestrictions(ContextInfo contextInfo) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (this.mEdmStorageProvider.getString(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", "passwordRequiredPattern") == null) {
            return true;
        }
        boolean putString = this.mEdmStorageProvider.putString(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", "passwordRequiredPattern", null);
        if (!putString) {
            return putString;
        }
        if (getCurrentPasswordOwner(enforceSecurityPermission$1) == enforceSecurityPermission$1.mCallerUid) {
            ChooseNewPasswordOwner(enforceSecurityPermission$1);
            return putString;
        }
        removeOwnerFromStack(enforceSecurityPermission$1);
        return putString;
    }

    public final ContextInfo enforceDoPoOnlySecurityPermissionByContext(ContextInfo contextInfo) {
        return getEDM$28().enforceDoPoOnlyPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY")));
    }

    public final boolean enforcePwdChange(ContextInfo contextInfo) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda2 passwordPolicy$$ExternalSyntheticLambda2 = new PasswordPolicy$$ExternalSyntheticLambda2(this, callingOrCurrentUserId, 2);
        injector.getClass();
        if (((Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda2)).intValue() != 458752) {
            return enforcePwdChangeForUser(enforceSecurityPermission$1.mContainerId, callingOrCurrentUserId);
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "enforcePwdChange declined because Lock Quality set to Smartcard for user = ", "PasswordPolicy");
        return false;
    }

    public final boolean enforcePwdChangeForUser(int i, int i2) {
        boolean z;
        SDPLog.d(null, String.format("Enforce password change policy applied for user %d by %d", Integer.valueOf(i), Integer.valueOf(i2)));
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z = true;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception during password enforcement: "), "PasswordPolicy");
            z = false;
        }
        if (isPersona(i2)) {
            setPwdChangeRequestedForUser(1, i2);
            postPwdResetEventToPersona(i2);
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        setPwdChangeRequestedForUser(1, i2);
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getPersonaManager().postPwdChangeNotificationForDeviceOwner(i2);
        boolean hasPassword = hasPassword(i2);
        int currentUser = ActivityManager.getCurrentUser();
        if (hasPassword) {
            if (this.mTelManager.getCallState() != 0 && i2 == currentUser) {
                setPwdChangeRequestedForUser(-2, i2);
            }
            changePasswordAsUser(i2);
        } else {
            setPwdChangeRequestedForUser(3, i2);
            if (this.mTelManager.getCallState() != 0 && i2 == currentUser) {
                setPwdChangeRequestedForUser(-4, i2);
            }
            changePasswordAsUser(i2);
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final ContextInfo enforceSecurityPermission$1(ContextInfo contextInfo) {
        return getEDM$28().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final boolean excludeExternalStorageForFailedPasswordsWipe(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission = getEDM$28().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndActiveAdminPermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("PASSWORD", enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, z, 0, "excludeExternalStorageWipe");
        if (putBoolean) {
            boolean isExternalStorageForFailedPasswordsWipeExcluded = isExternalStorageForFailedPasswordsWipeExcluded(enforceOwnerOnlyAndActiveAdminPermission);
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda7 = new PasswordPolicy$$ExternalSyntheticLambda7(this, callingOrCurrentUserId, isExternalStorageForFailedPasswordsWipeExcluded, 4);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda7);
        }
        return putBoolean;
    }

    public final List getAllOneLockedChildUsers(int i) {
        Injector injector;
        UserManager userManager;
        ArrayList arrayList = new ArrayList();
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "userHandle ", "PasswordPolicy");
        try {
            try {
                userManager = (UserManager) this.mContext.getSystemService("user");
            } catch (Exception e) {
                Log.e("PasswordPolicy", "getAllOneLockedChildUsers() failed. ", e);
                injector = this.mInjector;
            }
            if (userManager.getUserInfo(i).isManagedProfile()) {
                Log.d("PasswordPolicy", "getAllOneLockedChildUsers - isManagedProfile() true return empty locked users");
                return arrayList;
            }
            LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
            for (UserInfo userInfo : userManager.getProfiles(i)) {
                if (userInfo.isManagedProfile()) {
                    boolean isSeparateProfileChallengeEnabled = lockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id);
                    Log.d("PasswordPolicy", "hasSeparateChallenge" + isSeparateProfileChallengeEnabled);
                    if (!isSeparateProfileChallengeEnabled) {
                        Log.d("PasswordPolicy", "addding userid to onelock users array " + userInfo.id);
                        arrayList.add(Integer.valueOf(userInfo.id));
                    }
                }
            }
            injector = this.mInjector;
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getCurrentFailedPasswordAttempts(ContextInfo contextInfo) {
        Integer num = -1;
        int i = enforceSecurityPermission$1(contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda2 passwordPolicy$$ExternalSyntheticLambda2 = new PasswordPolicy$$ExternalSyntheticLambda2(this, i, 0);
            injector.getClass();
            num = (Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda2);
        }
        return num.intValue();
    }

    public final int getCurrentFailedPasswordAttemptsInternal(ContextInfo contextInfo) {
        Integer num = -1;
        int i = getEDM$28().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY"))).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda2 passwordPolicy$$ExternalSyntheticLambda2 = new PasswordPolicy$$ExternalSyntheticLambda2(this, i, 4);
            injector.getClass();
            num = (Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda2);
        }
        return num.intValue();
    }

    public final int getCurrentPasswordOwner(ContextInfo contextInfo) {
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(Utils.getCallingOrCurrentUserId(contextInfo), "passwordPatternOwner");
        if (genericValueAsUser == null) {
            return -1;
        }
        int parseInt = Integer.parseInt(genericValueAsUser);
        Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == parseInt) {
                return parseInt;
            }
        }
        return ChooseNewPasswordOwner(contextInfo);
    }

    public final EnterpriseDeviceManager getEDM$28() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return this.mEDM;
    }

    public final List getForbiddenStrings(ContextInfo contextInfo, boolean z) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null || !nameForUid.equals("android.uid.system:1000")) {
            contextInfo = enforceSecurityPermission$1(contextInfo);
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (!z) {
            String string = this.mEdmStorageProvider.getString(contextInfo.mCallerUid, 0, "PASSWORD", "passwordForbiddenStrings");
            if (string != null) {
                return new ArrayList(Arrays.asList(string.split(" ")));
            }
            return null;
        }
        List stringListAsUser = this.mEdmStorageProvider.getStringListAsUser(callingOrCurrentUserId, "PASSWORD", "passwordForbiddenStrings");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            ((ArrayList) stringListAsUser).addAll(this.mEdmStorageProvider.getStringListAsUser(((Integer) it.next()).intValue(), "PASSWORD", "passwordForbiddenStrings"));
        }
        ArrayList arrayList = (ArrayList) stringListAsUser;
        if (arrayList.size() <= 0) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.addAll(new ArrayList(Arrays.asList(((String) it2.next()).split(" "))));
        }
        return arrayList2;
    }

    public final int getKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName) {
        ContextInfo m = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo);
        Integer num = 0;
        if (this.mService != null) {
            int i = m.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, componentName, i, 5);
            injector.getClass();
            num = (Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return num.intValue();
    }

    public final int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i) {
        int i2;
        try {
            i2 = Integer.parseInt(this.mEdmStorageProvider.getGenericValueAsUser(i, "keyguardDisabledFeatures"));
        } catch (Exception e) {
            Log.w("PasswordPolicy", "getKeyguardDisabledFeatures() failed");
            e.printStackTrace();
            i2 = 0;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "getKeyguardDisabledFeatures() ", "PasswordPolicy");
        return i2;
    }

    public final int getMaximumCharacterOccurences(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = 0;
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(0, callingOrCurrentUserId, "PASSWORD", "passwordMaximumCharacterOccurences");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(0, ((Integer) it.next()).intValue(), "PASSWORD", "passwordMaximumCharacterOccurences"));
        }
        Iterator it2 = intListAsUser.iterator();
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i == 0 || (intValue != 0 && i > intValue)) {
                i = intValue;
            }
        }
        return i;
    }

    public final int getMaximumCharacterSequenceLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, callingOrCurrentUserId, "PASSWORD", "passwordMaximumCharacterSequenceLength");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, ((Integer) it.next()).intValue(), "PASSWORD", "passwordMaximumCharacterSequenceLength"));
        }
        Iterator it2 = intListAsUser.iterator();
        int i = 0;
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i == 0 || (intValue != 0 && i > intValue)) {
                i = intValue;
            }
        }
        return i;
    }

    public final int getMaximumFailedPasswordsForDisable(int i) {
        int i2 = 0;
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(0, i, "PASSWORD", "passwordAttemptDeviceDisable");
        String str = SystemProperties.get("ro.organization_owned");
        if (str != null && str.equals("true")) {
            Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
            while (it.hasNext()) {
                intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(0, ((Integer) it.next()).intValue(), "PASSWORD", "passwordAttemptDeviceDisable"));
            }
        }
        Iterator it2 = intListAsUser.iterator();
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i2 == 0 || (intValue != 0 && i2 > intValue)) {
                i2 = intValue;
            }
        }
        return i2;
    }

    public final int getMaximumFailedPasswordsForDisable(ContextInfo contextInfo) {
        return getMaximumFailedPasswordsForDisable(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final int getMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, componentName, i, 3);
            injector.getClass();
            num = (Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return num.intValue();
    }

    public final int getMaximumNumericSequenceLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = 0;
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(0, callingOrCurrentUserId, "PASSWORD", "passwordMaximumNumericSequenceLength");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(0, ((Integer) it.next()).intValue(), "PASSWORD", "passwordMaximumNumericSequenceLength"));
        }
        Iterator it2 = intListAsUser.iterator();
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i == 0 || (intValue != 0 && i > intValue)) {
                i = intValue;
            }
        }
        return i;
    }

    public final long getMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName) {
        ContextInfo m = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo);
        Long l = 0L;
        if (this.mService != null) {
            int i = m.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, componentName, i, 0);
            injector.getClass();
            l = (Long) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return l.longValue();
    }

    public final int getMinimumCharacterChangeLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, callingOrCurrentUserId, "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, ((Integer) it.next()).intValue(), "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength"));
        }
        Iterator it2 = intListAsUser.iterator();
        int i = 0;
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i == 0 || (intValue != 0 && i < intValue)) {
                i = intValue;
            }
        }
        return i;
    }

    public final int getPasswordChangeTimeout(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, callingOrCurrentUserId, "PASSWORD", "passwordChangeTimeout");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(callingOrCurrentUserId)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, ((Integer) it.next()).intValue(), "PASSWORD", "passwordChangeTimeout"));
        }
        Iterator it2 = intListAsUser.iterator();
        int i = -1;
        while (it2.hasNext()) {
            Integer num = (Integer) it2.next();
            if (num.intValue() >= 0 && (i == -1 || num.intValue() < i)) {
                i = num.intValue();
            }
        }
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    public final long getPasswordExpiration(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Long l = 0L;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                l = Long.valueOf(iDevicePolicyManager.getPasswordExpiration(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordExpiration failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordExpiration failed ", "PasswordPolicy");
        }
        return l.longValue();
    }

    public final long getPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                return iDevicePolicyManager.getPasswordExpirationTimeout(componentName, UserHandle.getUserId(i), false);
            }
            return 0L;
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordExpirationTimeout failed ", e, "PasswordPolicy");
            return 0L;
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordExpirationTimeout failed ", "PasswordPolicy");
            return 0L;
        }
    }

    public final int getPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordHistoryLength(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordHistoryLength failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordHistoryLength failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordLockDelay(int i) {
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(i, 0, "PASSWORD", "unlockDelay");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(((Integer) it.next()).intValue(), 0, "PASSWORD", "unlockDelay"));
        }
        Iterator it2 = intListAsUser.iterator();
        int i2 = -1;
        while (it2.hasNext()) {
            Integer num = (Integer) it2.next();
            if (num.intValue() >= 0 && (i2 == -1 || num.intValue() < i2)) {
                i2 = num.intValue();
            }
        }
        if (i2 < 0) {
            return -1;
        }
        return i2;
    }

    public final int getPasswordLockDelay(ContextInfo contextInfo) {
        return getPasswordLockDelay(contextInfo.mContainerId);
    }

    public final int getPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLength(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumLength failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumLength failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLetters(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumLetters failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumLetters failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName) {
        Integer num = 0;
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLowerCase(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumLowerCase failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumLowerCase failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumNonLetter(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumNonLetter failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumNonLetter failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumNumeric(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumNumeric failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumNumeric failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumSymbols(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumSymbols failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumSymbols failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumUpperCase(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("getPasswordMinimumUpperCase failed ", e, "PasswordPolicy");
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getPasswordMinimumUpperCase failed ", "PasswordPolicy");
        }
        return num.intValue();
    }

    public final int getPasswordQuality(ContextInfo contextInfo, ComponentName componentName) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        Integer num = 0;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, componentName, i, 1);
            injector.getClass();
            num = (Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return num.intValue();
    }

    public final String getRequiredPwdPatternRestrictions(ContextInfo contextInfo, boolean z) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null || !nameForUid.equals("android.uid.system:1000")) {
            contextInfo = enforceSecurityPermission$1(contextInfo);
        }
        List allOneLockedChildUsers = getAllOneLockedChildUsers(Utils.getCallingOrCurrentUserId(contextInfo));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) allOneLockedChildUsers;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList.addAll(this.mEdmStorageProvider.getStringListAsUser(((Integer) it.next()).intValue(), "PASSWORD", "passwordRequiredPattern"));
        }
        if (arrayList2.size() != 0) {
            if (arrayList.size() == 0) {
                return null;
            }
            return (String) arrayList.get(0);
        }
        if (!z) {
            return this.mEdmStorageProvider.getString(contextInfo.mCallerUid, contextInfo.mContainerId, "PASSWORD", "passwordRequiredPattern");
        }
        int currentPasswordOwner = getCurrentPasswordOwner(contextInfo);
        if (currentPasswordOwner != -1) {
            return this.mEdmStorageProvider.getString(currentPasswordOwner, contextInfo.mContainerId, "PASSWORD", "passwordRequiredPattern");
        }
        return null;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final Set getRestrictedKeyCodes() {
        if (isChangeRequestedAsUserFromDb(0) > 0) {
            return new HashSet(Arrays.asList(3, 1001, Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED)));
        }
        return null;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final String getServiceName() {
        return "PasswordPolicy";
    }

    public final Map getSupportedBiometricAuthentications(ContextInfo contextInfo) {
        PackageManager packageManager = this.mContext.getPackageManager();
        HashMap hashMap = new HashMap();
        if (packageManager != null && packageManager.hasSystemFeature("android.hardware.fingerprint")) {
            hashMap.put(1, "Fingerprint");
        }
        hashMap.put(4, "Face");
        return hashMap;
    }

    public final boolean hasForbiddenCharacterSequence(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser();
        int maximumCharacterSequenceLength = getMaximumCharacterSequenceLength(contextInfo);
        if (maximumCharacterSequenceLength != 0 && maximumCharacterSequenceLength < 16) {
            Matcher matcher = Pattern.compile("\\w(?=\\w{" + maximumCharacterSequenceLength + ",})").matcher(str);
            while (matcher.find()) {
                int start = matcher.start();
                char charAt = str.charAt(start);
                if (Character.isAlphabetic(charAt)) {
                    int charAt2 = str.charAt(start + 1) - charAt;
                    char c = charAt2 == 0 ? (char) 0 : charAt2 > 0 ? (char) 1 : (char) 65535;
                    StringBuilder sb = new StringBuilder(maximumCharacterSequenceLength + 5);
                    sb.append("\\Q");
                    sb.append(charAt);
                    int i = 0;
                    while (true) {
                        if (i < maximumCharacterSequenceLength) {
                            charAt = (char) (charAt + c);
                            if (Character.isAlphabetic(str.charAt(start + i + 1)) && Character.isAlphabetic(charAt)) {
                                sb.append(charAt);
                                i++;
                            }
                        } else {
                            sb.append("\\E");
                            if (str.substring(start, start + maximumCharacterSequenceLength + 1).matches(sb.toString())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public final boolean hasForbiddenData(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser();
        List forbiddenStrings = getForbiddenStrings(contextInfo, true);
        if (forbiddenStrings == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) forbiddenStrings;
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2.length() > 0 && str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasForbiddenNumericSequence(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser();
        int maximumNumericSequenceLength = getMaximumNumericSequenceLength(contextInfo);
        if (maximumNumericSequenceLength != 0 && maximumNumericSequenceLength < 16) {
            Matcher matcher = Pattern.compile("\\d(?=\\d{" + maximumNumericSequenceLength + ",})").matcher(str);
            while (matcher.find()) {
                int start = matcher.start();
                char charAt = str.charAt(start);
                int charAt2 = str.charAt(start + 1) - charAt;
                char c = charAt2 == 0 ? (char) 0 : charAt2 > 0 ? (char) 1 : (char) 65535;
                StringBuilder sb = new StringBuilder(maximumNumericSequenceLength + 5);
                sb.append("\\Q");
                sb.append(charAt);
                for (int i = 0; i < maximumNumericSequenceLength; i++) {
                    charAt = (char) (charAt + c);
                    sb.append(charAt);
                }
                sb.append("\\E");
                if (str.substring(start, start + maximumNumericSequenceLength + 1).matches(sb.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasForbiddenStringDistance(ContextInfo contextInfo, String str, String str2) {
        int minimumCharacterChangeLength;
        checkPackageCallerOrEnforceSystemUser();
        if (str2 != null && (minimumCharacterChangeLength = getMinimumCharacterChangeLength(contextInfo)) != 0) {
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, str2.length() + 1, str.length() + 1);
            for (int i = 0; i <= str2.length(); i++) {
                iArr[i][0] = i;
            }
            for (int i2 = 0; i2 <= str.length(); i2++) {
                iArr[0][i2] = i2;
            }
            for (int i3 = 1; i3 <= str2.length(); i3++) {
                for (int i4 = 1; i4 <= str.length(); i4++) {
                    int[] iArr2 = iArr[i3];
                    int i5 = i3 - 1;
                    int i6 = i4 - 1;
                    iArr2[i4] = Math.min(Math.min(iArr[i5][i4] + 1, iArr2[i6] + 1), iArr[i5][i6] + (str2.charAt(i5) == str.charAt(i6) ? 0 : 1));
                }
            }
            if (iArr[str2.length()][str.length()] < minimumCharacterChangeLength) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasMaxRepeatedCharacters(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser();
        int maximumCharacterOccurences = getMaximumCharacterOccurences(contextInfo);
        if (maximumCharacterOccurences != 0) {
            HashMap hashMap = new HashMap();
            for (char c : str.toCharArray()) {
                if (hashMap.get(Character.valueOf(c)) != null) {
                    int intValue = ((Integer) hashMap.get(Character.valueOf(c))).intValue();
                    if (intValue == maximumCharacterOccurences) {
                        Log.d("PasswordPolicy", c + " : " + intValue);
                        return true;
                    }
                    hashMap.put(Character.valueOf(c), Integer.valueOf(intValue + 1));
                } else {
                    hashMap.put(Character.valueOf(c), 1);
                }
            }
        }
        return false;
    }

    public final boolean hasPassword(int i) {
        Context createContextAsUser;
        return (isPersona(i) || (createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i)) == null || new LockPatternUtils(createContextAsUser).getActivePasswordQuality(i) <= 0) ? false : true;
    }

    public final boolean isActivePasswordSufficient(ContextInfo contextInfo) {
        int i = enforceSecurityPermission$1(contextInfo).mCallerUid;
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda2 passwordPolicy$$ExternalSyntheticLambda2 = new PasswordPolicy$$ExternalSyntheticLambda2(this, i, 3);
            injector.getClass();
            bool = (Boolean) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda2);
        }
        return bool.booleanValue();
    }

    public final boolean isBiometricAuthenticationEnabled(ContextInfo contextInfo, int i) {
        return isBiometricAuthenticationEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo), i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
    
        if (r4 == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isBiometricAuthenticationEnabledAsUser(int r11, int r12) {
        /*
            r10 = this;
            java.lang.String r0 = ", userId = "
            java.lang.String r1 = "PasswordPolicy"
            r2 = 1
            r3 = 0
            if (r12 != r2) goto L9
            goto L19
        L9:
            if (r12 == 0) goto Lb8
            if (r12 >= 0) goto Lf
            goto Lb8
        Lf:
            r4 = r12
        L10:
            int r5 = r4 % 2
            if (r5 != 0) goto L17
            int r4 = r4 / 2
            goto L10
        L17:
            if (r4 != r2) goto Lb8
        L19:
            com.android.server.enterprise.storage.EdmStorageProvider r4 = r10.mEdmStorageProvider
            java.lang.String r5 = "PASSWORD"
            java.lang.String r6 = "passwordBioAuthEnabled"
            java.util.ArrayList r4 = r4.getIntListAsUser(r3, r11, r5, r6)
            java.util.List r7 = r10.getAllOneLockedChildUsers(r11)
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            java.util.Iterator r7 = r7.iterator()
        L2e:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L48
            java.lang.Object r8 = r7.next()
            java.lang.Integer r8 = (java.lang.Integer) r8
            com.android.server.enterprise.storage.EdmStorageProvider r9 = r10.mEdmStorageProvider
            int r8 = r8.intValue()
            java.util.ArrayList r8 = r9.getIntListAsUser(r3, r8, r5, r6)
            r4.addAll(r8)
            goto L2e
        L48:
            java.util.Iterator r4 = r4.iterator()
            r5 = r3
        L4d:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L70
            java.lang.Object r6 = r4.next()
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r7 = r6.intValue()
            if (r7 >= 0) goto L60
            goto L4d
        L60:
            int r5 = r6.intValue()
            r5 = r5 & r12
            if (r5 == r12) goto L6e
            java.lang.String r10 = "isBiometricAuthenticationEnabledAsUser(): disallowed, "
            com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(r12, r11, r10, r0, r1)
            return r3
        L6e:
            r5 = r2
            goto L4d
        L70:
            if (r5 == 0) goto L79
            java.lang.String r10 = "isBiometricAuthenticationEnabledAsUser: return true (hasValue) "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r12, r10, r1)
            return r2
        L79:
            if (r12 != r2) goto L89
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r3 = r10.mPersonaManagerAdapter
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r3 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r3
            r3.getClass()
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r11)
            if (r3 != 0) goto L89
            return r2
        L89:
            r3 = 2
            if (r12 != r3) goto L9a
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r3 = r10.mPersonaManagerAdapter
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r3 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r3
            r3.getClass()
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r11)
            if (r3 != 0) goto L9a
            return r2
        L9a:
            r3 = 4
            if (r12 != r3) goto Lb1
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r10 = r10.mPersonaManagerAdapter
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r10 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r10
            r10.getClass()
            boolean r10 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r11)
            if (r10 != 0) goto Lb1
            java.lang.String r10 = "isBiometricAuthenticationEnabledAsUser(FACE): return true "
            android.util.Log.d(r1, r10)
            return r2
        Lb1:
            java.lang.String r10 = "isBiometricAuthenticationEnabledAsUser(): allowed as default, "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r12, r11, r10, r0, r1)
            return r2
        Lb8:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r2 = "isBiometricAuthenticationEnabledAsUser: bioAuth is wrong value : "
            r10.<init>(r2)
            r10.append(r12)
            r10.append(r0)
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.w(r1, r10)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.security.PasswordPolicy.isBiometricAuthenticationEnabledAsUser(int, int):boolean");
    }

    public final int isChangeRequested(ContextInfo contextInfo) {
        int userId;
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        IPersonaManagerAdapter iPersonaManagerAdapter = this.mPersonaManagerAdapter;
        int i = contextInfo.mContainerId;
        ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            userId = contextInfo.mContainerId;
        } else {
            String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
            if (nameForUid != null) {
                int lastIndexOf = nameForUid.lastIndexOf(":");
                if (lastIndexOf != -1) {
                    nameForUid = nameForUid.substring(0, lastIndexOf);
                }
                if (nameForUid.equals("android.uid.systemui") || Process.myPid() == Binder.getCallingPid()) {
                    Injector injector = this.mInjector;
                    PasswordPolicy$$ExternalSyntheticLambda46 passwordPolicy$$ExternalSyntheticLambda46 = new PasswordPolicy$$ExternalSyntheticLambda46();
                    injector.getClass();
                    userId = ((Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda46)).intValue();
                }
            }
            userId = UserHandle.getUserId(contextInfo.mCallerUid);
        }
        return isChangeRequestedAsUserFromDb(userId);
    }

    public final int isChangeRequestedAsUser(int i) {
        return isChangeRequestedAsUserFromDb(i);
    }

    public final int isChangeRequestedAsUserFromDb(int i) {
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(i, "passwordChangeRequested");
        if (genericValueAsUser != null) {
            return Integer.parseInt(genericValueAsUser);
        }
        return 0;
    }

    public final int isChangeRequestedForInner() {
        return isChangeRequestedAsUserFromDb(!DualDarManager.isOnDeviceOwnerEnabled() ? -1 : new LockPatternUtils(this.mContext).getLockPatternUtilForDualDarDo().getInnerAuthUserForDo());
    }

    public final boolean isClearLockAllowed() {
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda24 passwordPolicy$$ExternalSyntheticLambda24 = new PasswordPolicy$$ExternalSyntheticLambda24(0, this);
        injector.getClass();
        Boolean bool = (Boolean) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda24);
        Log.d("PasswordPolicy", "isClearLockAllowed - true");
        return bool.booleanValue();
    }

    public final boolean isExternalStorageForFailedPasswordsWipeExcluded(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser(i, "PASSWORD", "excludeExternalStorageWipe");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser(((Integer) it.next()).intValue(), "PASSWORD", "excludeExternalStorageWipe"));
        }
        if (booleanListAsUser.size() == 0) {
            Log.i("PasswordPolicy", "isExternalStorageForFailedPasswordsWipeExcluded() : no admin enforce password policy. ");
            return false;
        }
        Iterator it2 = booleanListAsUser.iterator();
        while (it2.hasNext()) {
            boolean booleanValue = ((Boolean) it2.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isExternalStorageForFailedPasswordsWipeExcluded(ContextInfo contextInfo) {
        return isExternalStorageForFailedPasswordsWipeExcluded(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final boolean isKeyCodeInputAllowed(int i) {
        return !(i == 3 || i == 187 || i == 1001) || isChangeRequestedAsUserFromDb(0) <= 0;
    }

    public final boolean isMDMDisabledFP(int i) {
        boolean z = false;
        Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, i, "PASSWORD", "passwordBioAuthEnabled").iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() >= 0 && (num.intValue() & 1) != 1) {
                z = true;
            }
        }
        if (z) {
            Log.i("PasswordPolicy", "isMDMDisabledFP: disallowed fingerprint.");
        }
        return z;
    }

    public final boolean isMultifactorAuthenticationEnabled(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        GestureWakeup$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "isMultifactorAuthenticationEnabled is called for user : ", ", caller uid - "), contextInfo.mCallerUid, "PasswordPolicy");
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(callingOrCurrentUserId, "PASSWORD", "multifactorAuthEnabled").iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isPasswordPatternMatched(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser();
        String requiredPwdPatternRestrictions = getRequiredPwdPatternRestrictions(contextInfo, true);
        if (requiredPwdPatternRestrictions != null) {
            return Pattern.compile(requiredPwdPatternRestrictions).matcher(str).matches();
        }
        return true;
    }

    public final boolean isPasswordTableExist(ContextInfo contextInfo) {
        return !this.mEdmStorageProvider.getIntListAsUser(0, Utils.getCallingOrCurrentUserId(contextInfo), "PASSWORD", "passwordBioAuthEnabled").isEmpty();
    }

    public final boolean isPasswordVisibilityEnabled(ContextInfo contextInfo) {
        return isPasswordVisibilityEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isPasswordVisibilityEnabledAsUser(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser(i, "PASSWORD", "passwordVisibilityEnabled");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser(((Integer) it.next()).intValue(), "PASSWORD", "passwordVisibilityEnabled"));
        }
        Iterator it2 = booleanListAsUser.iterator();
        while (it2.hasNext()) {
            boolean booleanValue = ((Boolean) it2.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isPersona(int i) {
        if (i == 0) {
            return false;
        }
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
        return SemPersonaManager.isKnoxId(i);
    }

    public final boolean isResetPasswordTokenActive(ContextInfo contextInfo, ComponentName componentName) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            int i = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda9 passwordPolicy$$ExternalSyntheticLambda9 = new PasswordPolicy$$ExternalSyntheticLambda9(this, i, componentName, 2);
            injector.getClass();
            bool = (Boolean) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda9);
        }
        return bool.booleanValue();
    }

    public final boolean isScreenLockPatternVisibilityEnabled(ContextInfo contextInfo) {
        return isScreenLockPatternVisibilityEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isScreenLockPatternVisibilityEnabledAsUser(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser(i, "PASSWORD", "screenLockPatternVisibility");
        Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser(((Integer) it.next()).intValue(), "PASSWORD", "screenLockPatternVisibility"));
        }
        Iterator it2 = booleanListAsUser.iterator();
        while (it2.hasNext()) {
            boolean booleanValue = ((Boolean) it2.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean lock(ContextInfo contextInfo) {
        enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        int i = contextInfo.mContainerId;
        int mUMContainerOwnerUid = i == 0 ? contextInfo.mCallerUid : this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
        try {
            if (this.mService.isProfileOwnerOfOrganizationOwnedDeviceMDM(contextInfo.mContainerId)) {
                i = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("PasswordPolicy", "lock is called for user : " + contextInfo.mContainerId + ", ownerUid - " + mUMContainerOwnerUid);
        AuditLog.logEventAsUser(UserHandle.getUserId(contextInfo.mCallerUid), 48, new Object[]{Integer.valueOf(contextInfo.mCallerUid)});
        setAdminLockEnabledSystemUI(i, true, false);
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        bundle.putInt("knox.container.proxy.EXTRA_CONTAINER_OWNER", mUMContainerOwnerUid);
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda24 passwordPolicy$$ExternalSyntheticLambda24 = new PasswordPolicy$$ExternalSyntheticLambda24(1, bundle);
        injector.getClass();
        return ((Bundle) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda24)).getInt("android.intent.extra.RETURN_RESULT") == 0;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor$9(callingOrCurrentUserId);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final void postPwdResetEventToPersona(int i) {
        boolean z = isChangeRequestedAsUserFromDb(i) < 1;
        boolean z2 = isChangeRequestedAsUserFromDb(i) >= 1;
        if (z && z2) {
            Log.d("PasswordPolicy", "postPwdResetEventToPersona :: Already enforced request pending...");
            return;
        }
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "android.intent.extra.user_handle");
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda51 passwordPolicy$$ExternalSyntheticLambda51 = new PasswordPolicy$$ExternalSyntheticLambda51(2, m);
        injector.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda51);
        SDPLog.i("Enforce Password Change requested for user " + i);
    }

    public final void reboot(ContextInfo contextInfo, String str) {
        Injector injector;
        DirEncryptService$$ExternalSyntheticOutline0.m(Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1(contextInfo)), "reboot() called, userId = ", "PasswordPolicy");
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                PowerManager powerManager = this.mPM;
                if (powerManager != null) {
                    powerManager.reboot(str);
                    z = true;
                } else {
                    Log.e("PasswordPolicy", "failed talking with power manager");
                }
                injector = this.mInjector;
            } catch (Exception e) {
                Log.e("PasswordPolicy", "reboot() has failed. ", e);
                injector = this.mInjector;
            }
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (z) {
                RestrictionToastManager.show(R.string.bugreport_countdown);
            }
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void removeOwnerFromStack(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory");
        if (genericValueAsUser != null) {
            String[] split = genericValueAsUser.split(",");
            StringBuilder sb = new StringBuilder();
            for (String str : split) {
                int parseInt = Integer.parseInt(str);
                Integer valueOf = Integer.valueOf(parseInt);
                if (parseInt != contextInfo.mCallerUid) {
                    sb.append(valueOf + ",");
                }
            }
            String sb2 = sb.toString();
            this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory", sb2.length() == 0 ? null : DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, sb2));
        }
    }

    public final boolean resetPassword(ContextInfo contextInfo, String str, int i) {
        throw new SecurityException("resetPassword is deprecated, use resetPasswordWithToken()");
    }

    public final boolean resetPasswordWithToken(ContextInfo contextInfo, final ComponentName componentName, final String str, final byte[] bArr, final int i) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda42
                public final Object getOrThrow() {
                    PasswordPolicy passwordPolicy = PasswordPolicy.this;
                    int i3 = i2;
                    ComponentName componentName2 = componentName;
                    String str2 = str;
                    byte[] bArr2 = bArr;
                    int i4 = i;
                    passwordPolicy.getClass();
                    int userId = UserHandle.getUserId(i3);
                    if (new LockPatternUtils(passwordPolicy.mContext).getActivePasswordQuality(userId) != 458752) {
                        return Boolean.valueOf(passwordPolicy.mService.resetPasswordWithTokenMDM(componentName2, str2, bArr2, i4, userId));
                    }
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(userId, "resetPassword declined because Lock Quality set to Smartcard for user = ", "PasswordPolicy");
                    return Boolean.FALSE;
                }
            };
            injector.getClass();
            bool = (Boolean) Binder.withCleanCallingIdentity(throwingSupplier);
        }
        return bool.booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x001e, code lost:
    
        if (com.samsung.android.knox.SemPersonaManager.isDarDualEncryptionEnabled(r4) != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAdminLockEnabledSystemUI(final int r4, final boolean r5, final boolean r6) {
        /*
            r3 = this;
            if (r6 == 0) goto L8e
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isDoEnabled(r4)
            if (r0 == 0) goto L21
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isDarDualEncryptionEnabled(r4)
            if (r0 == 0) goto L21
            goto L8e
        L21:
            android.app.admin.DevicePolicyManager r0 = r3.mDpm
            boolean r0 = r0.isOrganizationOwnedDeviceWithManagedProfile()
            if (r0 == 0) goto L73
            android.os.UserManager r0 = r3.mUserManager
            java.util.List r0 = r0.getProfiles(r4)
            java.util.Iterator r0 = r0.iterator()
        L33:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L73
            java.lang.Object r1 = r0.next()
            android.content.pm.UserInfo r1 = (android.content.pm.UserInfo) r1
            android.os.UserHandle r1 = r1.getUserHandle()
            int r1 = r1.getIdentifier()
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r2 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r2 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r2
            r2.getClass()
            boolean r2 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r1)
            if (r2 == 0) goto L33
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r2 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r2 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r2
            r2.getClass()
            boolean r2 = com.samsung.android.knox.SemPersonaManager.isDarDualEncryptionEnabled(r1)
            if (r2 == 0) goto L33
            android.app.admin.IDevicePolicyManager r2 = r3.mService     // Catch: android.os.RemoteException -> L6e
            boolean r1 = r2.isProfileOwnerOfOrganizationOwnedDeviceMDM(r1)     // Catch: android.os.RemoteException -> L6e
            if (r1 == 0) goto L33
            goto L8e
        L6e:
            r1 = move-exception
            r1.printStackTrace()
            goto L33
        L73:
            android.app.admin.DevicePolicyManager r0 = r3.mDpm
            boolean r0 = r0.isOrganizationOwnedDeviceWithManagedProfile()
            if (r0 == 0) goto L89
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.getUCMDAREncryption()
            goto L8a
        L89:
            r0 = 0
        L8a:
            if (r0 == 0) goto L8d
            goto L8e
        L8d:
            return
        L8e:
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$6()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r4)
            if (r0 == 0) goto La6
            java.lang.String r3 = "PasswordPolicy"
            java.lang.String r4 = "return : this is Knox user"
            android.util.Log.d(r3, r4)
            return
        La6:
            com.android.server.enterprise.security.PasswordPolicy$Injector r0 = r3.mInjector
            com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda17 r1 = new com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda17
            r1.<init>()
            r0.getClass()
            android.os.Binder.withCleanCallingIdentity(r1)
            if (r6 == 0) goto Ld9
            android.os.UserManager r4 = r3.mUserManager     // Catch: java.lang.Exception -> Ld0
            boolean r4 = r4.isUserUnlocked()     // Catch: java.lang.Exception -> Ld0
            if (r4 == 0) goto Ld9
            java.lang.String r4 = "PasswordPolicy"
            java.lang.String r5 = "validateLicenses() called"
            android.util.Log.d(r4, r5)     // Catch: java.lang.Exception -> Ld0
            com.android.server.enterprise.license.EnterpriseLicenseService r3 = r3.mLicenseService     // Catch: java.lang.Exception -> Ld0
            monitor-enter(r3)     // Catch: java.lang.Exception -> Ld0
            r3.validateLicenses$1()     // Catch: java.lang.Throwable -> Lcd
            monitor-exit(r3)     // Catch: java.lang.Exception -> Ld0
            goto Ld9
        Lcd:
            r4 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Exception -> Ld0
            throw r4     // Catch: java.lang.Exception -> Ld0
        Ld0:
            r3 = move-exception
            java.lang.String r4 = "PasswordPolicy"
            java.lang.String r5 = "validateLicenses() failed. "
            android.util.Log.e(r4, r5, r3)
        Ld9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.security.PasswordPolicy.setAdminLockEnabledSystemUI(int, boolean, boolean):void");
    }

    public final boolean setBiometricAuthenticationEnabled(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        long clearCallingIdentity;
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
        int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        try {
            i2 = this.mEdmStorageProvider.getInt(enforceSecurityPermission$1.mCallerUid, 0, "PASSWORD", "passwordBioAuthEnabled");
        } catch (Exception unused) {
            i2 = 255;
        }
        if (i2 >= 0) {
            i3 = i2;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, 0, z ? i3 | i : (~i) & i3, "PASSWORD", "passwordBioAuthEnabled");
        if (putInt) {
            if (!z) {
                Injector injector = this.mInjector;
                PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda4 = new PasswordPolicy$$ExternalSyntheticLambda4(this, i, callingOrCurrentUserId, 1);
                injector.getClass();
                Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
                this.mInjector.getClass();
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                    if (userManager.getUserInfo(callingOrCurrentUserId).isManagedProfile() && !new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(callingOrCurrentUserId)) {
                        int identifier = userManager.getProfileParent(callingOrCurrentUserId).getUserHandle().getIdentifier();
                        Injector injector2 = this.mInjector;
                        PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda42 = new PasswordPolicy$$ExternalSyntheticLambda4(this, i, identifier, 1);
                        injector2.getClass();
                        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda42);
                    }
                    this.mInjector.getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    this.mInjector.getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            this.mInjector.getClass();
            clearCallingIdentity = Binder.clearCallingIdentity();
            if ((i & 2) != 0) {
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format(z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_IRIS" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_IRIS", Integer.valueOf(enforceSecurityPermission$1.mCallerUid)), callingOrCurrentUserId);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if ((i & 1) != 0) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format(z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FINGERPRINT" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FINGERPRINT", Integer.valueOf(enforceSecurityPermission$1.mCallerUid)), callingOrCurrentUserId);
            }
            if ((i & 4) != 0) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format(z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FACE" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FACE", Integer.valueOf(enforceSecurityPermission$1.mCallerUid)), callingOrCurrentUserId);
            }
        }
        return putInt;
    }

    public final boolean setForbiddenStrings(ContextInfo contextInfo, List list) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        boolean z = false;
        try {
            StringBuilder sb = new StringBuilder();
            if (list == null) {
                sb.append("");
            } else {
                Iterator it = new TreeSet(list).iterator();
                while (it.hasNext()) {
                    sb.append(((String) it.next()) + " ");
                }
            }
            String sb2 = sb.toString();
            z = this.mEdmStorageProvider.putString(enforceSecurityPermission$1.mCallerUid, 0, "PASSWORD", "passwordForbiddenStrings", sb2);
            if (z) {
                int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
                int i = enforceSecurityPermission$1.mCallerUid;
                Injector injector = this.mInjector;
                PasswordPolicy$$ExternalSyntheticLambda3 passwordPolicy$$ExternalSyntheticLambda3 = new PasswordPolicy$$ExternalSyntheticLambda3(i, sb2, callingOrCurrentUserId, 0);
                injector.getClass();
                Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda3);
            }
        } catch (Exception unused) {
            Log.w("PasswordPolicy", "setForbiddenStrings() failed.");
        }
        return z;
    }

    public final void setHomeAndRecentKey(int i) {
        IStatusBarService iStatusBarService;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mStatusBarService == null) {
                synchronized (this) {
                    try {
                        if (this.mStatusBarService == null) {
                            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                            this.mStatusBarService = asInterface;
                            if (asInterface == null) {
                                Log.d("PasswordPolicy", "warning: no STATUS_BAR_SERVICE");
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
                if (i > 0) {
                    iStatusBarService2.disable(18874368, this.mToken, "PasswordPolicy");
                } else {
                    iStatusBarService2.disable(0, this.mToken, "PasswordPolicy");
                }
            }
            KeyCodeMediatorImpl keyCodeMediatorImpl = this.mKeyCodeMediator;
            if (keyCodeMediatorImpl == null) {
                Log.e("PasswordPolicy", "mKeyCodeMediator must not be null! This will cause problems on hardware key restriction.");
            } else {
                keyCodeMediatorImpl.update(3);
                this.mKeyCodeMediator.update(1001);
                this.mKeyCodeMediator.update(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
            }
        } catch (Exception unused) {
            Log.d("PasswordPolicy", "setHomeAndRecentKey was failed");
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void setKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName, int i) {
        ContextInfo m = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo);
        if (i != 0 && i != 16) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Invalid features ", " for container"));
        }
        if (this.mService != null) {
            int i2 = m.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 9);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) != 5250 && UserHandle.getAppId(callingUid) != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
        if (i != 0 && (i & 16) == 0) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setKeyguardDisabledFeatures() which not supported ", "PasswordPolicy");
            return;
        }
        try {
            if (this.mEdmStorageProvider.putGenericValueAsUser(i2, "keyguardDisabledFeatures", String.valueOf(1))) {
                Log.d("PasswordPolicy", "setKeyguardDisabledFeatures() true");
            } else {
                Log.d("PasswordPolicy", "setKeyguardDisabledFeatures() false");
            }
        } catch (Exception e) {
            Log.w("PasswordPolicy", "setKeyguardDisabledFeatures() failed");
            e.printStackTrace();
        }
    }

    public final boolean setMaximumCharacterOccurrences(ContextInfo contextInfo, int i) {
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, 0, i, "PASSWORD", "passwordMaximumCharacterOccurences");
        if (putInt) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
            int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda20 passwordPolicy$$ExternalSyntheticLambda20 = new PasswordPolicy$$ExternalSyntheticLambda20(i2, i, callingOrCurrentUserId, 2);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda20);
        }
        return putInt;
    }

    public final boolean setMaximumCharacterSequenceLength(ContextInfo contextInfo, int i) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, i, "PASSWORD", "passwordMaximumCharacterSequenceLength");
        if (putInt) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
            int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda20 passwordPolicy$$ExternalSyntheticLambda20 = new PasswordPolicy$$ExternalSyntheticLambda20(i2, i, callingOrCurrentUserId, 0);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda20);
        }
        return putInt;
    }

    public final boolean setMaximumFailedPasswordsForDisable(ContextInfo contextInfo, int i) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, 0, i, "PASSWORD", "passwordAttemptDeviceDisable");
        if (putInt) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
            int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda20 passwordPolicy$$ExternalSyntheticLambda20 = new PasswordPolicy$$ExternalSyntheticLambda20(i2, i, callingOrCurrentUserId, 3);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda20);
            int maximumFailedPasswordsForDisable = getMaximumFailedPasswordsForDisable(enforceSecurityPermission$1);
            Injector injector2 = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda4 = new PasswordPolicy$$ExternalSyntheticLambda4(this, maximumFailedPasswordsForDisable, callingOrCurrentUserId, 0);
            injector2.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
        }
        return putInt;
    }

    public final void setMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 0);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final boolean setMaximumNumericSequenceLength(ContextInfo contextInfo, int i) {
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, 0, i, "PASSWORD", "passwordMaximumNumericSequenceLength");
        if (putInt) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
            int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda20 passwordPolicy$$ExternalSyntheticLambda20 = new PasswordPolicy$$ExternalSyntheticLambda20(i2, i, callingOrCurrentUserId, 4);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda20);
        }
        return putInt;
    }

    public final void setMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName, long j) {
        ContextInfo m = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo);
        if (this.mService != null) {
            int i = m.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda5 passwordPolicy$$ExternalSyntheticLambda5 = new PasswordPolicy$$ExternalSyntheticLambda5(this, componentName, j, i, 0);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda5);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final void setMediator(KeyCodeMediatorImpl keyCodeMediatorImpl) {
        if (this.mKeyCodeMediator == null) {
            this.mKeyCodeMediator = keyCodeMediatorImpl;
            ((HashSet) keyCodeMediatorImpl.mRestrictionCallbacks).add(this);
        }
    }

    public final boolean setMinimumCharacterChangeLength(ContextInfo contextInfo, int i) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, i, "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength");
        if (putInt) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
            int i2 = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda20 passwordPolicy$$ExternalSyntheticLambda20 = new PasswordPolicy$$ExternalSyntheticLambda20(i2, i, callingOrCurrentUserId, 1);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda20);
        }
        return putInt;
    }

    public final boolean setMultifactorAuthenticationEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceDoPoOnlySecurityPermissionByContext = enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceDoPoOnlySecurityPermissionByContext);
        final Context context = this.mContext;
        Injector injector = this.mInjector;
        final int i = 0;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda52
            public final Object getOrThrow() {
                int i2 = i;
                Context context2 = context;
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(context2.getPackageManager().hasSystemFeature("android.hardware.fingerprint") && ((FingerprintManager) context2.getSystemService(FingerprintManager.class)).isHardwareDetected());
                    default:
                        return Boolean.valueOf(context2.getPackageManager().hasSystemFeature("android.hardware.biometrics.iris"));
                }
            }
        };
        injector.getClass();
        if (!((Boolean) Binder.withCleanCallingIdentity(throwingSupplier)).booleanValue()) {
            final Context context2 = this.mContext;
            Injector injector2 = this.mInjector;
            final int i2 = 1;
            FunctionalUtils.ThrowingSupplier throwingSupplier2 = new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda52
                public final Object getOrThrow() {
                    int i22 = i2;
                    Context context22 = context2;
                    switch (i22) {
                        case 0:
                            return Boolean.valueOf(context22.getPackageManager().hasSystemFeature("android.hardware.fingerprint") && ((FingerprintManager) context22.getSystemService(FingerprintManager.class)).isHardwareDetected());
                        default:
                            return Boolean.valueOf(context22.getPackageManager().hasSystemFeature("android.hardware.biometrics.iris"));
                    }
                }
            };
            injector2.getClass();
            if (!((Boolean) Binder.withCleanCallingIdentity(throwingSupplier2)).booleanValue()) {
                Log.d("PasswordPolicy", "setMultifactorAuthenticationEnabled: two-factor authentication not available");
                return false;
            }
        }
        if (z) {
            Injector injector3 = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda2 passwordPolicy$$ExternalSyntheticLambda2 = new PasswordPolicy$$ExternalSyntheticLambda2(this, callingOrCurrentUserId, 1);
            injector3.getClass();
            if (((Integer) Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda2)).intValue() == 458752) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "two-factor authentication not available because Lock Quality set to Smartcard for user = ", "PasswordPolicy");
                return false;
            }
        }
        Log.d("PasswordPolicy", "setMultifactorAuthenticationEnabled is called for user : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId + ", caller uid - " + enforceDoPoOnlySecurityPermissionByContext.mCallerUid + ", enable - " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("PASSWORD", enforceDoPoOnlySecurityPermissionByContext.mCallerUid, z, 0, "multifactorAuthEnabled");
        if (putBoolean) {
            Injector injector4 = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda7 = new PasswordPolicy$$ExternalSyntheticLambda7(this, callingOrCurrentUserId, z, 0);
            injector4.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda7);
            boolean z2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", 0, callingOrCurrentUserId) == 1;
            if (!z2 && z) {
                Log.d("PasswordPolicy", "EnforcePwdChange is called for user as Multifcator needs to be enforced for : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId);
                enforcePwdChange(enforceDoPoOnlySecurityPermissionByContext);
            }
            if (z2 && !z && callingOrCurrentUserId == 0) {
                ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
                if (SemPersonaManager.isDoEnabled(callingOrCurrentUserId) || ((DevicePolicyManager) ((PersonaManagerAdapter) this.mPersonaManagerAdapter).mContext.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile()) {
                    Log.d("PasswordPolicy", "EnforcePwdChange is called for DO case as Multifcator needs to be removed for : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId);
                    enforcePwdChange(enforceDoPoOnlySecurityPermissionByContext);
                }
            }
        }
        return putBoolean;
    }

    public final boolean setPasswordChangeTimeout(ContextInfo contextInfo, int i) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (i < 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("passwordChangeTimeout", Integer.valueOf(i));
        return this.mEdmStorageProvider.putValues(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", contentValues);
    }

    public final void setPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName, long j) {
        int i = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda5 passwordPolicy$$ExternalSyntheticLambda5 = new PasswordPolicy$$ExternalSyntheticLambda5(this, componentName, j, i, 1);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda5);
        }
    }

    public final void setPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 3);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final boolean setPasswordLockDelay(ContextInfo contextInfo, int i) {
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission = getEDM$28().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
        if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
            IPersonaManagerAdapter iPersonaManagerAdapter = this.mPersonaManagerAdapter;
            int i2 = enforceOwnerOnlyAndActiveAdminPermission.mContainerId;
            ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
            if (SemPersonaManager.isKnoxId(i2)) {
                Log.d("PasswordPolicy", "setPasswordLockDelay() failed. because not supported in Knox 2.0");
                return false;
            }
        }
        if (i < -1) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("unlockDelay", Integer.valueOf(i));
        boolean putValues = this.mEdmStorageProvider.putValues(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, enforceOwnerOnlyAndActiveAdminPermission.mContainerId, "PASSWORD", contentValues);
        if (putValues) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndActiveAdminPermission);
            int passwordLockDelay = getPasswordLockDelay(enforceOwnerOnlyAndActiveAdminPermission.mContainerId);
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda4 = new PasswordPolicy$$ExternalSyntheticLambda4(this, callingOrCurrentUserId, passwordLockDelay, 3);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
        }
        return putValues;
    }

    public final void setPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 7);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 6);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 2);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 4);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 8);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 10);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 5);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final void setPasswordQuality(ContextInfo contextInfo, ComponentName componentName, int i) {
        int i2 = PasswordPolicy$$ExternalSyntheticOutline0.m(this, contextInfo, componentName, contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda6 passwordPolicy$$ExternalSyntheticLambda6 = new PasswordPolicy$$ExternalSyntheticLambda6(this, componentName, i, i2, 1);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda6);
        }
    }

    public final boolean setPasswordVisibilityEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda7 = new PasswordPolicy$$ExternalSyntheticLambda7(this, callingOrCurrentUserId, z, 2);
        injector.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda7);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("PASSWORD", enforceSecurityPermission$1.mCallerUid, z, 0, "passwordVisibilityEnabled");
        if (putBoolean) {
            Injector injector2 = this.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda72 = new PasswordPolicy$$ExternalSyntheticLambda7(this, callingOrCurrentUserId, z, 3);
            injector2.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda72);
        }
        return putBoolean;
    }

    public final boolean setPwdChangeRequested(ContextInfo contextInfo, int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i2 = contextInfo.mContainerId;
        return setPwdChangeRequestedForUser(i, callingOrCurrentUserId);
    }

    public final synchronized boolean setPwdChangeRequestedForInner(int i) {
        boolean z;
        checkPackageCallerOrEnforceSystemUser();
        try {
            z = this.mEdmStorageProvider.putGenericValueAsUser(!DualDarManager.isOnDeviceOwnerEnabled() ? -1 : new LockPatternUtils(this.mContext).getLockPatternUtilForDualDarDo().getInnerAuthUserForDo(), "passwordChangeRequested", Integer.toString(i));
            if (i == 0) {
                setHomeAndRecentKey(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        return z;
    }

    public final synchronized boolean setPwdChangeRequestedForUser(int i, int i2) {
        boolean z;
        try {
            checkPackageCallerOrEnforceSystemUser();
            z = false;
            try {
                boolean putGenericValueAsUser = this.mEdmStorageProvider.putGenericValueAsUser(i2, "passwordChangeRequested", Integer.toString(i));
                if (DualDarManager.isOnDeviceOwnerEnabled() && i == 1) {
                    putGenericValueAsUser = putGenericValueAsUser && setPwdChangeRequestedForInner(i);
                }
                if (this.mEDM == null) {
                    getEDM$28();
                }
                if (putGenericValueAsUser) {
                    int isChangeRequestedAsUserFromDb = isChangeRequestedAsUserFromDb(i2);
                    this.mPolicyCache.setChangeRequestedAsUser(i2, isChangeRequestedAsUserFromDb);
                    Injector injector = this.mInjector;
                    PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda4 = new PasswordPolicy$$ExternalSyntheticLambda4(this, i2, isChangeRequestedAsUserFromDb, 2);
                    injector.getClass();
                    Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
                    if (i2 == 0 && (i == 0 || i == -1)) {
                        Injector injector2 = this.mInjector;
                        PasswordPolicy$$ExternalSyntheticLambda51 passwordPolicy$$ExternalSyntheticLambda51 = new PasswordPolicy$$ExternalSyntheticLambda51(0, this);
                        injector2.getClass();
                        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda51);
                        if (!DualDarManager.isOnDeviceOwnerEnabled()) {
                            setHomeAndRecentKey(i);
                        }
                    }
                }
                if (i == 1) {
                    new LockPatternUtils(this.mContext).requireStrongAuth(2, i2);
                }
                if (!this.mEDM.getRestrictionPolicy().isSettingsChangesAllowed(false)) {
                    Injector injector3 = this.mInjector;
                    PasswordPolicy$$ExternalSyntheticLambda14 passwordPolicy$$ExternalSyntheticLambda14 = new PasswordPolicy$$ExternalSyntheticLambda14(this, i2, 0);
                    injector3.getClass();
                    Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda14);
                }
                z = putGenericValueAsUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            throw th;
        }
        return z;
    }

    public final boolean setRequiredPasswordPattern(ContextInfo contextInfo, String str) {
        String valueOf;
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            Pattern.compile(str);
            boolean putString = this.mEdmStorageProvider.putString(enforceSecurityPermission$1.mCallerUid, enforceSecurityPermission$1.mContainerId, "PASSWORD", "passwordRequiredPattern", str);
            if (putString) {
                if (getCurrentPasswordOwner(enforceSecurityPermission$1) != enforceSecurityPermission$1.mCallerUid) {
                    removeOwnerFromStack(enforceSecurityPermission$1);
                    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
                    this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordPatternOwner", String.valueOf(enforceSecurityPermission$1.mCallerUid));
                    String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory");
                    if (genericValueAsUser != null) {
                        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(genericValueAsUser, ",");
                        m.append(String.valueOf(enforceSecurityPermission$1.mCallerUid));
                        valueOf = m.toString();
                    } else {
                        valueOf = String.valueOf(enforceSecurityPermission$1.mCallerUid);
                    }
                    this.mEdmStorageProvider.putGenericValueAsUser(callingOrCurrentUserId, "passwordOwnerHistory", valueOf);
                }
                int callingOrCurrentUserId2 = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
                int i = enforceSecurityPermission$1.mCallerUid;
                Injector injector = this.mInjector;
                PasswordPolicy$$ExternalSyntheticLambda3 passwordPolicy$$ExternalSyntheticLambda3 = new PasswordPolicy$$ExternalSyntheticLambda3(i, str, callingOrCurrentUserId2, 1);
                injector.getClass();
                Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda3);
            }
            return putString;
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean setResetPasswordToken(ContextInfo contextInfo, final ComponentName componentName, final byte[] bArr) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i = enforceSecurityPermission$1.mCallerUid;
            Injector injector = this.mInjector;
            FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda1
                public final Object getOrThrow() {
                    PasswordPolicy passwordPolicy = PasswordPolicy.this;
                    int i2 = i;
                    ComponentName componentName2 = componentName;
                    byte[] bArr2 = bArr;
                    passwordPolicy.getClass();
                    return Boolean.valueOf(passwordPolicy.mService.setResetPasswordTokenMDM(componentName2, bArr2, UserHandle.getUserId(i2)));
                }
            };
            injector.getClass();
            bool = (Boolean) Binder.withCleanCallingIdentity(throwingSupplier);
        }
        return bool.booleanValue();
    }

    public final boolean setScreenLockPatternVisibilityEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceSecurityPermission$1 = enforceSecurityPermission$1(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("PASSWORD", enforceSecurityPermission$1.mCallerUid, z, 0, "screenLockPatternVisibility");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission$1);
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda7 = new PasswordPolicy$$ExternalSyntheticLambda7(this, z, callingOrCurrentUserId);
        injector.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda7);
        return putBoolean;
    }

    public final void setTrustAgentConfiguration(ContextInfo contextInfo, final ComponentName componentName, final ComponentName componentName2, final PersistableBundle persistableBundle) {
        Log.d("PasswordPolicy", "setTrustAgentConfiguration");
        final int i = enforceSecurityPermission$1(contextInfo).mCallerUid;
        if (this.mService != null) {
            Injector injector = this.mInjector;
            FunctionalUtils.ThrowingRunnable throwingRunnable = new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda26
                public final void runOrThrow() {
                    PasswordPolicy passwordPolicy = PasswordPolicy.this;
                    int i2 = i;
                    ComponentName componentName3 = componentName;
                    ComponentName componentName4 = componentName2;
                    PersistableBundle persistableBundle2 = persistableBundle;
                    passwordPolicy.getClass();
                    try {
                        passwordPolicy.mService.setTrustAgentConfigurationMDM(UserHandle.getUserId(i2), componentName3, componentName4, persistableBundle2);
                    } catch (RemoteException e) {
                        Log.w("PasswordPolicy", "Failed talking with device policy service", e);
                    }
                }
            };
            injector.getClass();
            Binder.withCleanCallingIdentity(throwingRunnable);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
    }

    public final boolean unlock(ContextInfo contextInfo) {
        enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        final int i = contextInfo.mContainerId;
        StringBuilder sb = new StringBuilder("unlock is called for user : ");
        sb.append(contextInfo.mContainerId);
        sb.append(", caller uid - ");
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, contextInfo.mCallerUid, "PasswordPolicy");
        try {
            if (this.mService.isProfileOwnerOfOrganizationOwnedDeviceMDM(contextInfo.mContainerId)) {
                i = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "android.intent.extra.user_handle");
        final int i2 = contextInfo.mCallerUid;
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda11
            public final Object getOrThrow() {
                PasswordPolicy passwordPolicy = PasswordPolicy.this;
                int i3 = i2;
                int i4 = i;
                Bundle bundle = m;
                passwordPolicy.getClass();
                AuditLog.logEventAsUser(UserHandle.getUserId(i3), 49, new Object[]{Integer.valueOf(i3)});
                passwordPolicy.setAdminLockEnabledSystemUI(i4, false, false);
                return ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_UNLOCK", bundle);
            }
        };
        injector.getClass();
        return ((Bundle) Binder.withCleanCallingIdentity(throwingSupplier)).getInt("android.intent.extra.RETURN_RESULT") == 0;
    }

    public final void updateSystemUIMonitor$9(int i) {
        boolean z;
        int i2;
        if (new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(i)) {
            i = ((UserManager) this.mContext.getSystemService("user")).getProfileParent(i).getUserHandle().getIdentifier();
        }
        int passwordLockDelay = getPasswordLockDelay(i);
        Injector injector = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda4 = new PasswordPolicy$$ExternalSyntheticLambda4(this, i, passwordLockDelay, 3);
        injector.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
        int isChangeRequestedAsUserFromDb = isChangeRequestedAsUserFromDb(i);
        Injector injector2 = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda42 = new PasswordPolicy$$ExternalSyntheticLambda4(this, i, isChangeRequestedAsUserFromDb, 2);
        injector2.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda42);
        int maximumFailedPasswordsForDisable = getMaximumFailedPasswordsForDisable(i);
        Injector injector3 = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda43 = new PasswordPolicy$$ExternalSyntheticLambda4(this, maximumFailedPasswordsForDisable, i, 0);
        injector3.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda43);
        String str = SystemProperties.get("ro.organization_owned");
        if (str != null && str.equals("false")) {
            Iterator it = ((ArrayList) getAllOneLockedChildUsers(i)).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int intValue = num.intValue();
                int maximumFailedPasswordsForDisable2 = getMaximumFailedPasswordsForDisable(num.intValue());
                Injector injector4 = this.mInjector;
                PasswordPolicy$$ExternalSyntheticLambda4 passwordPolicy$$ExternalSyntheticLambda44 = new PasswordPolicy$$ExternalSyntheticLambda4(this, maximumFailedPasswordsForDisable2, intValue, 0);
                injector4.getClass();
                Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda44);
            }
        }
        boolean isExternalStorageForFailedPasswordsWipeExcluded = isExternalStorageForFailedPasswordsWipeExcluded(i);
        Injector injector5 = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda7 = new PasswordPolicy$$ExternalSyntheticLambda7(this, i, isExternalStorageForFailedPasswordsWipeExcluded, 4);
        injector5.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda7);
        Iterator it2 = this.mEdmStorageProvider.getBooleanListAsUser(i, "PASSWORD", "multifactorAuthEnabled").iterator();
        while (true) {
            if (!it2.hasNext()) {
                z = false;
                break;
            } else if (((Boolean) it2.next()).booleanValue()) {
                z = true;
                break;
            }
        }
        Injector injector6 = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda72 = new PasswordPolicy$$ExternalSyntheticLambda7(this, i, z, 0);
        injector6.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda72);
        boolean isPasswordVisibilityEnabledAsUser = isPasswordVisibilityEnabledAsUser(i);
        Injector injector7 = this.mInjector;
        PasswordPolicy$$ExternalSyntheticLambda7 passwordPolicy$$ExternalSyntheticLambda73 = new PasswordPolicy$$ExternalSyntheticLambda7(this, i, isPasswordVisibilityEnabledAsUser, 3);
        injector7.getClass();
        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda73);
        UserManager userManager = this.mUserManager;
        UserInfo userInfo = userManager != null ? userManager.getUserInfo(i) : null;
        if (userInfo != null) {
            ?? isAdminLocked = userInfo.isAdminLocked();
            i2 = isAdminLocked;
            if (userInfo.isLicenseLocked()) {
                i2 = isAdminLocked + 2;
            }
        } else {
            i2 = 0;
        }
        setAdminLockEnabledSystemUI(i, (i2 & 1) != 0, (i2 & 2) != 0);
    }
}
