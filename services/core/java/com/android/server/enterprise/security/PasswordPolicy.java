package com.android.server.enterprise.security;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
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
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
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
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.LockPatternUtilsAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.common.KeyCodeMediator;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SdpManagerImpl;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
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

/* loaded from: classes2.dex */
public class PasswordPolicy extends IPasswordPolicy.Stub implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static final int[] BIOMETRIC_AUTHENTICATION_TYPES = {1, 4};
    public ActivationMonitor mActivationMonitor;
    public final SemDesktopModeManager.DesktopModeBlocker mBlocker;
    public BroadcastReceiver mBroadCastReceiver;
    public List mCallersWhitelist;
    public Context mContext;
    public DevicePolicyManager mDpm;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public KeyCodeMediator mKeyCodeMediator;
    public EnterpriseLicenseService mLicenseService;
    public final LocalService mLocalService;
    public IPersonaManagerAdapter mPersonaManagerAdapter;
    public PasswordPolicyCache mPolicyCache;
    public final BroadcastReceiver mReceiver;
    public final IDevicePolicyManager mService;
    public IStatusBarService mStatusBarService;
    public TelephonyManager mTelManager;
    public IBinder mToken;
    public UserManager mUserManager;

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public String getServiceName() {
        return "PasswordPolicy";
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public IDevicePolicyManager getDpmInstance() {
            return IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }

        public IPersonaManagerAdapter getPersonaManagerAdapterInstance() {
            return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
        }

        public DevicePolicyManager getDevicePolicyManager() {
            return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }

        public UserManager getUserManager() {
            return (UserManager) this.mContext.getSystemService("user");
        }

        public EnterpriseDeviceManager getEDM() {
            return EnterpriseDeviceManager.getInstance(this.mContext);
        }

        public long binderClearCallingIdentity() {
            return Binder.clearCallingIdentity();
        }

        public void binderRestoreCallingIdentity(long j) {
            Binder.restoreCallingIdentity(j);
        }

        public void binderWithCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
            Binder.withCleanCallingIdentity(throwingRunnable);
        }

        public final Object binderWithCleanCallingIdentity(FunctionalUtils.ThrowingSupplier throwingSupplier) {
            return Binder.withCleanCallingIdentity(throwingSupplier);
        }
    }

    public PasswordPolicy(Context context) {
        this(new Injector(context));
    }

    public PasswordPolicy(Injector injector) {
        this.mActivationMonitor = null;
        this.mLicenseService = null;
        this.mCallersWhitelist = new ArrayList() { // from class: com.android.server.enterprise.security.PasswordPolicy.1
            {
                add("com.samsung.android.knox.containercore");
            }
        };
        this.mBlocker = new SemDesktopModeManager.DesktopModeBlocker() { // from class: com.android.server.enterprise.security.PasswordPolicy.2
            public String onBlocked() {
                return PasswordPolicy.this.mContext.getString(R.string.gadget_host_error_inflating);
            }
        };
        this.mStatusBarService = null;
        this.mToken = new Binder();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.security.PasswordPolicy.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                    PasswordPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mEDM = null;
        this.mBroadCastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.security.PasswordPolicy.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                int sendingUserId = getSendingUserId();
                long binderClearCallingIdentity = PasswordPolicy.this.mInjector.binderClearCallingIdentity();
                int currentUser = ActivityManager.getCurrentUser();
                if ("com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL".equals(action)) {
                    PasswordPolicy.this.enforcePwdChangeIfNeededOnTimeout(sendingUserId);
                } else if ("android.intent.action.USER_STARTED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra >= 0) {
                        PasswordPolicy.this.enforcePwdChangeIfNeededOnStart(intExtra);
                    }
                } else if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra2 >= 0) {
                        PasswordPolicy.this.enforcePwdChangeIfNeededOnSwitch(intExtra2);
                    }
                } else if ("android.intent.action.PHONE_STATE".equals(action)) {
                    if (PasswordPolicy.this.mTelManager.getCallState() == 0) {
                        PasswordPolicy.this.enforcePwdChangeIfNeededAfterCall(currentUser);
                    }
                } else if ("com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL".equals(action)) {
                    Log.i("PasswordPolicy", "Received ACTION_PASSWORD_EXPIRING_NOTIFICATION_INTERNAL intent");
                    long longExtra = intent.getLongExtra("expiration", -1L);
                    if (longExtra == -1 || longExtra > System.currentTimeMillis()) {
                        Log.i("PasswordPolicy", "In grace period or failed to get " + longExtra);
                        PasswordPolicy.this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                        return;
                    }
                    Log.i("PasswordPolicy", "Password expired already so launching password screen");
                    if (PasswordPolicy.this.mPersonaManagerAdapter.isValidKnoxId(sendingUserId)) {
                        try {
                            ActivityManagerNative.getDefault().forceStopPackage("com.android.settings", sendingUserId);
                        } catch (RemoteException unused) {
                            Log.d("PasswordPolicy", "forceStopPackage failed");
                        }
                    }
                    PasswordPolicy.this.enforcePwdChangeForUser(0, sendingUserId);
                }
                PasswordPolicy.this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        };
        this.mInjector = injector;
        this.mContext = injector.mContext;
        this.mEdmStorageProvider = injector.getStorageProvider();
        this.mService = injector.getDpmInstance();
        this.mDpm = injector.getDevicePolicyManager();
        this.mUserManager = injector.getUserManager();
        this.mPersonaManagerAdapter = injector.getPersonaManagerAdapterInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiverAsUser(this.mBroadCastReceiver, UserHandle.ALL, intentFilter, null, null);
        this.mContext.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"));
        this.mTelManager = (TelephonyManager) this.mContext.getSystemService("phone");
        Log.d("PasswordPolicy", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.server.enterprise.security.PasswordPolicy.3
                public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    if (semDesktopModeState.state == 20 && semDesktopModeState.enabled == 3) {
                        Log.d("PasswordPolicy", "listner - Dex Enabling");
                        if (PasswordPolicy.this.isChangeRequestedAsUser(0) != 0) {
                            PasswordPolicy.this.registerDexBlocker();
                        }
                    }
                    if (semDesktopModeState.state == 0 && semDesktopModeState.enabled == 2) {
                        Log.d("PasswordPolicy", "listener - Dex Disabled");
                        if (PasswordPolicy.this.isChangeRequestedAsUser(0) != 0) {
                            PasswordPolicy.this.changePasswordAsUserInternal(0);
                        }
                    }
                }
            });
        }
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        LocalServices.addService(PasswordPolicyInternal.class, localService);
        initializePolicyCache();
        this.mActivationMonitor = new ActivationMonitor();
    }

    public final void initializePolicyCache() {
        this.mPolicyCache = PasswordPolicyCache.getInstance();
        updatePolicyCache();
    }

    public final void updatePolicyCache() {
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            PasswordPolicyCache passwordPolicyCache = this.mPolicyCache;
            int i = userInfo.id;
            passwordPolicyCache.setChangeRequestedAsUser(i, isChangeRequestedAsUserFromDb(i));
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = this.mInjector.getEDM();
        }
        return this.mEDM;
    }

    public final synchronized IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            this.mStatusBarService = asInterface;
            if (asInterface == null) {
                Log.d("PasswordPolicy", "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mStatusBarService;
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final ContextInfo enforceOwnerOnlyAndSecurityPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final ContextInfo checkPackageCallerOrEnforcePermission(ContextInfo contextInfo, String str) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        return (nameForUid == null || !nameForUid.equals(str)) ? enforceSecurityPermission(contextInfo) : contextInfo;
    }

    public final ContextInfo enforceOnlySecurityPermission(ContextInfo contextInfo) {
        return getEDM().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final ContextInfo enforceDoPoOnlySecurityPermissionByContext(ContextInfo contextInfo) {
        return getEDM().enforceDoPoOnlyPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY")));
    }

    public final void checkPackageCallerOrEnforceSystemUser(String str) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        if ((nameForUid == null || !nameForUid.equals(str)) && !this.mCallersWhitelist.contains(nameForUid)) {
            enforceSystemUser();
        }
    }

    public boolean setPasswordLockDelay(ContextInfo contextInfo, int i) {
        ContextInfo enforceOwnerOnlyAndSecurityPermission = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        if ("2.0".equals(this.mPersonaManagerAdapter.getKnoxInfo().getString("version")) && this.mPersonaManagerAdapter.isValidKnoxId(enforceOwnerOnlyAndSecurityPermission.mContainerId)) {
            Log.d("PasswordPolicy", "setPasswordLockDelay() failed. because not supported in Knox 2.0");
            return false;
        }
        if (i < -1) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("unlockDelay", Integer.valueOf(i));
        boolean putValues = this.mEdmStorageProvider.putValues(enforceOwnerOnlyAndSecurityPermission.mCallerUid, enforceOwnerOnlyAndSecurityPermission.mContainerId, "PASSWORD", contentValues);
        if (putValues) {
            setPasswordLockDelaySystemUI(Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndSecurityPermission), getPasswordLockDelay(enforceOwnerOnlyAndSecurityPermission.mContainerId));
        }
        return putValues;
    }

    public final int getPasswordLockDelay(int i) {
        ArrayList intList = this.mEdmStorageProvider.getIntList(i, "PASSWORD", "unlockDelay");
        Iterator it = getAllOneLockedChildUsers(i).iterator();
        while (it.hasNext()) {
            intList.addAll(this.mEdmStorageProvider.getIntList(((Integer) it.next()).intValue(), "PASSWORD", "unlockDelay"));
        }
        Iterator it2 = intList.iterator();
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

    public int getPasswordLockDelay(ContextInfo contextInfo) {
        return getPasswordLockDelay(contextInfo.mContainerId);
    }

    public boolean setRequiredPasswordPattern(ContextInfo contextInfo, final String str) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (str == null || str.length() == 0 || !checkRegex(str)) {
            return false;
        }
        boolean putString = this.mEdmStorageProvider.putString(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordRequiredPattern", str);
        if (putString) {
            if (getCurrentPasswordOwner(enforceSecurityPermission) != enforceSecurityPermission.mCallerUid) {
                removeOwnerFromStack(enforceSecurityPermission);
                addOwnerToStack(enforceSecurityPermission);
            }
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda5
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setRequiredPasswordPattern$0(i, str, callingOrCurrentUserId);
                }
            });
        }
        return putString;
    }

    public static /* synthetic */ void lambda$setRequiredPasswordPattern$0(int i, String str, int i2) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password required pattern to %s", Integer.valueOf(i), str), i2);
    }

    public final boolean checkRegex(String str) {
        try {
            Pattern.compile(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean deleteAllRestrictions(ContextInfo contextInfo) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (this.mEdmStorageProvider.getString(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordRequiredPattern") == null) {
            return true;
        }
        boolean putString = this.mEdmStorageProvider.putString(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordRequiredPattern", null);
        if (!putString) {
            return putString;
        }
        if (getCurrentPasswordOwner(enforceSecurityPermission) == enforceSecurityPermission.mCallerUid) {
            ChooseNewPasswordOwner(enforceSecurityPermission);
            return putString;
        }
        removeOwnerFromStack(enforceSecurityPermission);
        return putString;
    }

    public String getRequiredPwdPatternRestrictions(ContextInfo contextInfo, boolean z) {
        ContextInfo checkPackageCallerOrEnforcePermission = checkPackageCallerOrEnforcePermission(contextInfo, "android.uid.system:1000");
        List allOneLockedChildUsers = getAllOneLockedChildUsers(Utils.getCallingOrCurrentUserId(checkPackageCallerOrEnforcePermission));
        ArrayList arrayList = new ArrayList();
        Iterator it = allOneLockedChildUsers.iterator();
        while (it.hasNext()) {
            arrayList.addAll(this.mEdmStorageProvider.getStringListAsUser("PASSWORD", "passwordRequiredPattern", ((Integer) it.next()).intValue()));
        }
        if (allOneLockedChildUsers.size() != 0) {
            if (arrayList.size() == 0) {
                return null;
            }
            return (String) arrayList.get(0);
        }
        if (z) {
            int currentPasswordOwner = getCurrentPasswordOwner(checkPackageCallerOrEnforcePermission);
            if (currentPasswordOwner != -1) {
                return this.mEdmStorageProvider.getString(currentPasswordOwner, checkPackageCallerOrEnforcePermission.mContainerId, "PASSWORD", "passwordRequiredPattern");
            }
            return null;
        }
        return this.mEdmStorageProvider.getString(checkPackageCallerOrEnforcePermission.mCallerUid, checkPackageCallerOrEnforcePermission.mContainerId, "PASSWORD", "passwordRequiredPattern");
    }

    public boolean hasForbiddenNumericSequence(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        return containsForbiddenNumericSequence(contextInfo, str);
    }

    public boolean hasForbiddenCharacterSequence(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        return containsForbiddenCharacterSequence(contextInfo, str);
    }

    public boolean hasForbiddenStringDistance(ContextInfo contextInfo, String str, String str2) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        return containsForbiddenStringDistance(contextInfo, str, str2);
    }

    public boolean hasForbiddenData(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        return containsForbiddenData(contextInfo, str);
    }

    public boolean hasMaxRepeatedCharacters(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        return containsMaxRepeatedCharacters(contextInfo, str);
    }

    public boolean isPasswordPatternMatched(ContextInfo contextInfo, String str) {
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        String requiredPwdPatternRestrictions = getRequiredPwdPatternRestrictions(contextInfo, true);
        if (requiredPwdPatternRestrictions != null) {
            return Pattern.compile(requiredPwdPatternRestrictions).matcher(str).matches();
        }
        return true;
    }

    public final void enforceSystemUser() {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) != 5250 && UserHandle.getAppId(callingUid) != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
    }

    public boolean setPasswordChangeTimeout(ContextInfo contextInfo, int i) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (i < 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("passwordChangeTimeout", Integer.valueOf(i));
        return this.mEdmStorageProvider.putValues(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", contentValues);
    }

    public int getPasswordChangeTimeout(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordChangeTimeout", callingOrCurrentUserId);
        Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordChangeTimeout", ((Integer) it.next()).intValue()));
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

    public boolean enforcePwdChange(ContextInfo contextInfo) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        if (((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda43
            public final Object getOrThrow() {
                Integer lambda$enforcePwdChange$1;
                lambda$enforcePwdChange$1 = PasswordPolicy.this.lambda$enforcePwdChange$1(callingOrCurrentUserId);
                return lambda$enforcePwdChange$1;
            }
        })).intValue() == 458752) {
            Log.d("PasswordPolicy", "enforcePwdChange declined because Lock Quality set to Smartcard for user = " + callingOrCurrentUserId);
            return false;
        }
        return enforcePwdChangeForUser(enforceSecurityPermission.mContainerId, callingOrCurrentUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$enforcePwdChange$1(int i) {
        Integer valueOf = Integer.valueOf(new LockPatternUtils(this.mContext).getActivePasswordQuality(i));
        Log.d("PasswordPolicy", "UCS enabled for user = " + i);
        Log.d("PasswordPolicy", "current quality = " + valueOf + ", SMART CARD Quality = 458752");
        return valueOf;
    }

    public final boolean enforcePwdChangeForUser(int i, int i2) {
        boolean z;
        SDPLog.m38d(String.format("Enforce password change policy applied for user %d by %d", Integer.valueOf(i), Integer.valueOf(i2)));
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            z = true;
        } catch (Exception e) {
            Log.e("PasswordPolicy", "Exception during password enforcement: " + e.getMessage());
            e.printStackTrace();
            z = false;
        }
        if (isPersona(i2)) {
            setPwdChangeRequestedForUser(i, 1, i2);
            postPwdResetEventToPersona(i2);
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            return true;
        }
        setPwdChangeRequestedForUser(i, 1, i2);
        this.mPersonaManagerAdapter.postPwdChangeNotificationForDeviceOwner(i2);
        boolean hasPassword = hasPassword(i2);
        int currentUser = ActivityManager.getCurrentUser();
        if (!hasPassword) {
            setPwdChangeRequestedForUser(i, 3, i2);
            if (this.mTelManager.getCallState() != 0 && i2 == currentUser) {
                setPwdChangeRequestedForUser(i, -4, i2);
            }
            changePasswordAsUser(i2);
        } else {
            if (this.mTelManager.getCallState() != 0 && i2 == currentUser) {
                setPwdChangeRequestedForUser(i, -2, i2);
            }
            changePasswordAsUser(i2);
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return z;
    }

    public final boolean hasPassword(int i) {
        Context createContextAsUser;
        return (isPersona(i) || (createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i)) == null || new LockPatternUtilsAdapter(createContextAsUser).getActivePasswordQuality(i) <= 0) ? false : true;
    }

    public final void enforcePwdChangeIfNeededOnStart(int i) {
        int i2;
        int isChangeRequestedAsUser = isChangeRequestedAsUser(i);
        if (isChangeRequestedAsUser != -4) {
            if (isChangeRequestedAsUser != -3) {
                if (isChangeRequestedAsUser == -2) {
                    i2 = 1;
                } else if (isChangeRequestedAsUser != -1) {
                    i2 = 0;
                }
            }
            i2 = 2;
        } else {
            i2 = 3;
        }
        if (i2 != 0) {
            setPwdChangeRequestedForUser(0, i2, i);
        }
    }

    public final void enforcePwdChangeIfNeededOnSwitch(int i) {
        int isChangeRequestedAsUser = isChangeRequestedAsUser(i);
        boolean hasPassword = hasPassword(i);
        if (isChangeRequestedAsUser <= 0 || hasPassword) {
            return;
        }
        changePasswordAsUser(i);
    }

    public final void enforcePwdChangeIfNeededOnTimeout(int i) {
        if (isChangeRequestedAsUser(i) == -1) {
            int currentUser = ActivityManager.getCurrentUser();
            if (this.mTelManager.getCallState() == 0 || i != currentUser) {
                setPwdChangeRequestedForUser(0, 2, i);
                changePasswordAsUser(i);
            } else {
                setPwdChangeRequestedForUser(0, -3, i);
            }
        }
    }

    public final void enforcePwdChangeIfNeededAfterCall(int i) {
        int isChangeRequestedAsUser = isChangeRequestedAsUser(i);
        int i2 = isChangeRequestedAsUser != -4 ? isChangeRequestedAsUser != -3 ? isChangeRequestedAsUser != -2 ? 0 : 1 : 2 : 3;
        if (i2 > 0) {
            setPwdChangeRequestedForUser(0, i2, i);
            changePasswordAsUser(i);
        }
    }

    public final void changePasswordAsUser(int i) {
        if (i == 0) {
            if (Utils.isDexActivated(this.mContext)) {
                registerDexBlocker();
                Log.d("PasswordPolicy", "show pw change window after dex is disabled");
            } else {
                changePasswordAsUserInternal(i);
                registerDexBlocker();
                Log.d("PasswordPolicy", "show pw change window immediately");
            }
            setHomeAndRecentKey(1);
            return;
        }
        changePasswordAsUserInternal(i);
    }

    public final void changePasswordAsUserInternal(int i) {
        try {
            if (isPersona(i)) {
                postPwdResetEventToPersona(i);
                return;
            }
            if (ActivityManager.getCurrentUser() == i) {
                UserHandle userHandle = new UserHandle(i);
                if (!hasPassword(i)) {
                    Intent intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.password.ChooseLockGeneric");
                    intent.addFlags(268435456);
                    intent.addFlags(4194304);
                    intent.addFlags(8388608);
                    intent.putExtra("lockscreen.password_isenforced", true);
                    this.mContext.startActivityAsUser(intent, userHandle);
                    return;
                }
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL"), userHandle);
            }
        } catch (Exception e) {
            Log.e("PasswordPolicy", "handled expected Exception in changePasswordAsUser().", e);
        }
    }

    public final boolean postPwdResetEventToPersona(int i) {
        SdpManagerImpl sdpManager;
        boolean z = isChangeRequestedAsUser(i) < 1;
        boolean z2 = isChangeRequestedAsUser(i) >= 1;
        if (z && z2) {
            Log.d("PasswordPolicy", "postPwdResetEventToPersona :: Already enforced request pending...");
            return false;
        }
        final Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda52
            public final void runOrThrow() {
                PasswordPolicy.lambda$postPwdResetEventToPersona$2(bundle);
            }
        });
        SDPLog.m43i("Enforce Password Change requested for user " + i);
        DarManagerService darManagerService = (DarManagerService) ServiceManager.getService("dar");
        if (darManagerService != null && (sdpManager = darManagerService.getSdpManager()) != null) {
            sdpManager.handleEnforcePwdChange(i);
        }
        return true;
    }

    public static /* synthetic */ void lambda$postPwdResetEventToPersona$2(Bundle bundle) {
        ContainerProxy.sendEvent("knox.container.proxy.EVENT_LOCK_TIMEOUT", bundle);
        ContainerProxy.sendCommand("knox.container.proxy.COMMAND_ENFORCE_PASSWORD", bundle);
    }

    public final boolean isPersona(int i) {
        if (i != 0) {
            return this.mPersonaManagerAdapter.isValidKnoxId(i);
        }
        return false;
    }

    public boolean setPwdChangeRequested(ContextInfo contextInfo, int i) {
        return setPwdChangeRequestedForUser(contextInfo.mContainerId, i, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public synchronized boolean setPwdChangeRequestedForUser(int i, int i2, final int i3) {
        boolean z;
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        z = false;
        try {
            boolean putGenericValueAsUser = this.mEdmStorageProvider.putGenericValueAsUser("passwordChangeRequested", Integer.toString(i2), i3);
            if (isDualDarDoEnabled() && i2 == 1) {
                putGenericValueAsUser = putGenericValueAsUser && setPwdChangeRequestedForInner(i2);
            }
            if (this.mEDM == null) {
                getEDM();
            }
            if (putGenericValueAsUser) {
                int isChangeRequestedAsUser = isChangeRequestedAsUser(i3);
                this.mPolicyCache.setChangeRequestedAsUser(i3, isChangeRequestedAsUser);
                setPwdChangeRequestedSystemUI(i3, isChangeRequestedAsUser);
                if (i3 == 0 && (i2 == 0 || i2 == -1)) {
                    unRegisterDexBlocker();
                    if (!isDualDarDoEnabled()) {
                        setHomeAndRecentKey(i2);
                    }
                }
            }
            if (i2 == 1) {
                new LockPatternUtils(this.mContext).requireStrongAuth(2, i3);
            }
            if (!this.mEDM.getRestrictionPolicy().isSettingsChangesAllowed(false)) {
                this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda9
                    public final void runOrThrow() {
                        PasswordPolicy.this.lambda$setPwdChangeRequestedForUser$3(i3);
                    }
                });
            }
            z = putGenericValueAsUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPwdChangeRequestedForUser$3(int i) {
        IActivityManager iActivityManager = ActivityManagerNative.getDefault();
        List<ActivityManager.RecentTaskInfo> list = iActivityManager.getRecentTasks(12, 0, i).getList();
        if (list.isEmpty()) {
            return;
        }
        for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
            ComponentName component = recentTaskInfo.baseIntent.getComponent();
            if (component != null) {
                String packageName = component.getPackageName();
                Log.w("PasswordPolicy", "packageName " + packageName);
                if (packageName != null && packageName.equals("com.android.settings")) {
                    iActivityManager.forceStopPackage("com.android.settings", i);
                    iActivityManager.removeTask(recentTaskInfo.persistentId);
                }
            }
        }
    }

    public synchronized boolean setPwdChangeRequestedForInner(int i) {
        boolean z;
        checkPackageCallerOrEnforceSystemUser("android.uid.system:1000");
        try {
            z = this.mEdmStorageProvider.putGenericValueAsUser("passwordChangeRequested", Integer.toString(i), getInnerAuthUserIdForDualDarDo());
            if (i == 0) {
                setHomeAndRecentKey(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        return z;
    }

    public final boolean isDualDarDoEnabled() {
        return DualDarManager.isOnDeviceOwnerEnabled();
    }

    public final int getInnerAuthUserIdForDualDarDo() {
        if (isDualDarDoEnabled()) {
            return new LockPatternUtils(this.mContext).getLockPatternUtilForDualDarDo().getInnerAuthUserForDo();
        }
        return -1;
    }

    public final void setHomeAndRecentKey(int i) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            if (this.mStatusBarService == null) {
                this.mStatusBarService = getStatusBarService();
            }
            IStatusBarService iStatusBarService = this.mStatusBarService;
            if (iStatusBarService != null) {
                if (i > 0) {
                    iStatusBarService.disable(18874368, this.mToken, "PasswordPolicy");
                } else {
                    iStatusBarService.disable(0, this.mToken, "PasswordPolicy");
                }
            }
            KeyCodeMediator keyCodeMediator = this.mKeyCodeMediator;
            if (keyCodeMediator == null) {
                Log.e("PasswordPolicy", "mKeyCodeMediator must not be null! This will cause problems on hardware key restriction.");
            } else {
                keyCodeMediator.update(3);
                this.mKeyCodeMediator.update(1001);
                this.mKeyCodeMediator.update(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
            }
        } catch (Exception unused) {
            Log.d("PasswordPolicy", "setHomeAndRecentKey was failed");
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }

    public final int getUserIdByPackageNameOrUid(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        if (this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) {
            return contextInfo.mContainerId;
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui") || Process.myPid() == Binder.getCallingPid()) {
                return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda21
                    public final Object getOrThrow() {
                        Integer lambda$getUserIdByPackageNameOrUid$4;
                        lambda$getUserIdByPackageNameOrUid$4 = PasswordPolicy.lambda$getUserIdByPackageNameOrUid$4();
                        return lambda$getUserIdByPackageNameOrUid$4;
                    }
                })).intValue();
            }
        }
        return UserHandle.getUserId(contextInfo.mCallerUid);
    }

    public static /* synthetic */ Integer lambda$getUserIdByPackageNameOrUid$4() {
        return Integer.valueOf(ActivityManager.getCurrentUser());
    }

    public int isChangeRequested(ContextInfo contextInfo) {
        return isChangeRequestedAsUser(getUserIdByPackageNameOrUid(contextInfo));
    }

    public int isChangeRequestedForInner() {
        return isChangeRequestedAsUser(getInnerAuthUserIdForDualDarDo());
    }

    public int isChangeRequestedAsUser(int i) {
        return isChangeRequestedAsUserFromDb(i);
    }

    public final int isChangeRequestedAsUserFromDb(int i) {
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("passwordChangeRequested", i);
        if (genericValueAsUser != null) {
            return Integer.parseInt(genericValueAsUser);
        }
        return 0;
    }

    public boolean setMaximumFailedPasswordsForDisable(ContextInfo contextInfo, final int i) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordAttemptDeviceDisable", i);
        if (putInt) {
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i2 = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda41
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setMaximumFailedPasswordsForDisable$5(i2, i, callingOrCurrentUserId);
                }
            });
            setMaximumFailedPasswordsForDisableSystemUI(callingOrCurrentUserId, getMaximumFailedPasswordsForDisable(enforceSecurityPermission));
        }
        return putInt;
    }

    public static /* synthetic */ void lambda$setMaximumFailedPasswordsForDisable$5(int i, int i2, int i3) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed maximum failed passwords for disable to %d", Integer.valueOf(i), Integer.valueOf(i2)), i3);
    }

    public int getMaximumFailedPasswordsForDisable(ContextInfo contextInfo) {
        return getMaximumFailedPasswordsForDisable(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public int getMaximumFailedPasswordsForDisable(int i) {
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordAttemptDeviceDisable", i);
        String str = SystemProperties.get("ro.organization_owned");
        if (str != null && str.equals("true")) {
            Iterator it = getAllOneLockedChildUsers(i).iterator();
            while (it.hasNext()) {
                intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordAttemptDeviceDisable", ((Integer) it.next()).intValue()));
            }
        }
        Iterator it2 = intListAsUser.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (i2 == 0 || (intValue != 0 && i2 > intValue)) {
                i2 = intValue;
            }
        }
        return i2;
    }

    public boolean setMaximumNumericSequenceLength(ContextInfo contextInfo, final int i) {
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordMaximumNumericSequenceLength", i);
        if (putInt) {
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i2 = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda22
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setMaximumNumericSequenceLength$6(i2, i, callingOrCurrentUserId);
                }
            });
        }
        return putInt;
    }

    public static /* synthetic */ void lambda$setMaximumNumericSequenceLength$6(int i, int i2, int i3) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password maximum numeric sequence to %d", Integer.valueOf(i), Integer.valueOf(i2)), i3);
    }

    public int getMaximumNumericSequenceLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordMaximumNumericSequenceLength", callingOrCurrentUserId);
        Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordMaximumNumericSequenceLength", ((Integer) it.next()).intValue()));
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

    public final boolean containsForbiddenNumericSequence(ContextInfo contextInfo, String str) {
        int maximumNumericSequenceLength = getMaximumNumericSequenceLength(contextInfo);
        if (maximumNumericSequenceLength != 0 && maximumNumericSequenceLength < 16) {
            Matcher matcher = Pattern.compile("\\d(?=\\d{" + maximumNumericSequenceLength + ",})").matcher(str);
            while (matcher.find()) {
                int start = matcher.start();
                char charAt = str.charAt(start);
                int charAt2 = str.charAt(start + 1) - charAt;
                char c = charAt2 == 0 ? (char) 0 : charAt2 > 0 ? (char) 1 : (char) 65535;
                StringBuilder sb = new StringBuilder(maximumNumericSequenceLength + 5);
                sb.append('\\');
                sb.append('Q');
                sb.append(charAt);
                for (int i = 0; i < maximumNumericSequenceLength; i++) {
                    charAt = (char) (charAt + c);
                    sb.append(charAt);
                }
                sb.append('\\');
                sb.append('E');
                if (str.substring(start, start + maximumNumericSequenceLength + 1).matches(sb.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setForbiddenStrings(ContextInfo contextInfo, List list) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
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
            final String sb2 = sb.toString();
            z = this.mEdmStorageProvider.putString(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordForbiddenStrings", sb2);
            if (z) {
                final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
                final int i = enforceSecurityPermission.mCallerUid;
                this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda50
                    public final void runOrThrow() {
                        PasswordPolicy.lambda$setForbiddenStrings$7(i, sb2, callingOrCurrentUserId);
                    }
                });
            }
        } catch (Exception unused) {
            Log.w("PasswordPolicy", "setForbiddenStrings() failed.");
        }
        return z;
    }

    public static /* synthetic */ void lambda$setForbiddenStrings$7(int i, String str, int i2) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password forbidden strings to %s", Integer.valueOf(i), str), i2);
    }

    public List getForbiddenStrings(ContextInfo contextInfo, boolean z) {
        ContextInfo checkPackageCallerOrEnforcePermission = checkPackageCallerOrEnforcePermission(contextInfo, "android.uid.system:1000");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(checkPackageCallerOrEnforcePermission);
        if (z) {
            List stringListAsUser = this.mEdmStorageProvider.getStringListAsUser("PASSWORD", "passwordForbiddenStrings", callingOrCurrentUserId);
            Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
            while (it.hasNext()) {
                stringListAsUser.addAll(this.mEdmStorageProvider.getStringListAsUser("PASSWORD", "passwordForbiddenStrings", ((Integer) it.next()).intValue()));
            }
            if (stringListAsUser == null || stringListAsUser.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it2 = stringListAsUser.iterator();
            while (it2.hasNext()) {
                arrayList.addAll(new ArrayList(Arrays.asList(((String) it2.next()).split(" "))));
            }
            return arrayList;
        }
        String string = this.mEdmStorageProvider.getString(checkPackageCallerOrEnforcePermission.mCallerUid, "PASSWORD", "passwordForbiddenStrings");
        if (string != null) {
            return new ArrayList(Arrays.asList(string.split(" ")));
        }
        return null;
    }

    public final boolean containsForbiddenData(ContextInfo contextInfo, String str) {
        List<String> forbiddenStrings = getForbiddenStrings(contextInfo, true);
        if (forbiddenStrings != null && forbiddenStrings.size() != 0) {
            for (String str2 : forbiddenStrings) {
                if (str2.length() > 0 && str.contains(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setMaximumCharacterOccurrences(ContextInfo contextInfo, final int i) {
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordMaximumCharacterOccurences", i);
        if (putInt) {
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i2 = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda48
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setMaximumCharacterOccurrences$8(i2, i, callingOrCurrentUserId);
                }
            });
        }
        return putInt;
    }

    public static /* synthetic */ void lambda$setMaximumCharacterOccurrences$8(int i, int i2, int i3) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password maximum character occurrences to %d", Integer.valueOf(i), Integer.valueOf(i2)), i3);
    }

    public int getMaximumCharacterOccurences(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordMaximumCharacterOccurences", callingOrCurrentUserId);
        Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordMaximumCharacterOccurences", ((Integer) it.next()).intValue()));
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

    public final boolean containsMaxRepeatedCharacters(ContextInfo contextInfo, String str) {
        int maximumCharacterOccurences = getMaximumCharacterOccurences(contextInfo);
        if (maximumCharacterOccurences == 0) {
            return false;
        }
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
        return false;
    }

    public final int getCurrentPasswordOwner(ContextInfo contextInfo) {
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("passwordPatternOwner", Utils.getCallingOrCurrentUserId(contextInfo));
        if (genericValueAsUser == null) {
            return -1;
        }
        Integer valueOf = Integer.valueOf(Integer.parseInt(genericValueAsUser));
        Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == valueOf.intValue()) {
                return valueOf.intValue();
            }
        }
        return ChooseNewPasswordOwner(contextInfo);
    }

    public final int ChooseNewPasswordOwner(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList adminUidList = this.mEdmStorageProvider.getAdminUidList();
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("passwordOwnerHistory", callingOrCurrentUserId);
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
            if (!arrayList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    sb.append(((Integer) it2.next()).intValue() + ",");
                }
                this.mEdmStorageProvider.putGenericValueAsUser("passwordOwnerHistory", sb.substring(0, sb.length() - 1), callingOrCurrentUserId);
                this.mEdmStorageProvider.putGenericValueAsUser("passwordPatternOwner", String.valueOf(i2), callingOrCurrentUserId);
            } else {
                this.mEdmStorageProvider.putGenericValueAsUser("passwordOwnerHistory", null, callingOrCurrentUserId);
                this.mEdmStorageProvider.putGenericValueAsUser("passwordPatternOwner", null, callingOrCurrentUserId);
            }
        }
        return i2;
    }

    public final void removeOwnerFromStack(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("passwordOwnerHistory", callingOrCurrentUserId);
        if (genericValueAsUser != null) {
            String[] split = genericValueAsUser.split(",");
            StringBuilder sb = new StringBuilder();
            for (String str : split) {
                Integer valueOf = Integer.valueOf(Integer.parseInt(str));
                if (valueOf.intValue() != contextInfo.mCallerUid) {
                    sb.append(valueOf + ",");
                }
            }
            String sb2 = sb.toString();
            this.mEdmStorageProvider.putGenericValueAsUser("passwordOwnerHistory", sb2.length() == 0 ? null : sb2.substring(0, sb2.length() - 1), callingOrCurrentUserId);
        }
    }

    public final void addOwnerToStack(ContextInfo contextInfo) {
        String valueOf;
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        this.mEdmStorageProvider.putGenericValueAsUser("passwordPatternOwner", String.valueOf(contextInfo.mCallerUid), callingOrCurrentUserId);
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("passwordOwnerHistory", callingOrCurrentUserId);
        if (genericValueAsUser != null) {
            valueOf = genericValueAsUser + "," + String.valueOf(contextInfo.mCallerUid);
        } else {
            valueOf = String.valueOf(contextInfo.mCallerUid);
        }
        this.mEdmStorageProvider.putGenericValueAsUser("passwordOwnerHistory", valueOf, callingOrCurrentUserId);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public Set getRestrictedKeyCodes() {
        if (isChangeRequestedAsUser(0) > 0) {
            return new HashSet(Arrays.asList(3, 1001, Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED)));
        }
        return null;
    }

    public boolean setScreenLockPatternVisibilityEnabled(ContextInfo contextInfo, final boolean z) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceSecurityPermission.mCallerUid, "PASSWORD", "screenLockPatternVisibility", z);
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda8
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setScreenLockPatternVisibilityEnabled$9(z, callingOrCurrentUserId);
            }
        });
        return putBoolean;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenLockPatternVisibilityEnabled$9(boolean z, int i) {
        new LockPatternUtils(this.mContext).setVisiblePatternEnabled(z, i);
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (!userManager.getUserInfo(i).isManagedProfile() || new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(i)) {
            return;
        }
        new LockPatternUtils(this.mContext).setVisiblePatternEnabled(z, userManager.getProfileParent(i).getUserHandle().getIdentifier());
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
        return !(i == 3 || i == 187 || i == 1001) || isChangeRequestedAsUser(0) <= 0;
    }

    public boolean isScreenLockPatternVisibilityEnabled(ContextInfo contextInfo) {
        return isScreenLockPatternVisibilityEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "screenLockPatternVisibility", i);
        Iterator it = getAllOneLockedChildUsers(i).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "screenLockPatternVisibility", ((Integer) it.next()).intValue()));
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

    public boolean setMaximumCharacterSequenceLength(ContextInfo contextInfo, final int i) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordMaximumCharacterSequenceLength", i);
        if (putInt) {
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i2 = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda47
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setMaximumCharacterSequenceLength$10(i2, i, callingOrCurrentUserId);
                }
            });
        }
        return putInt;
    }

    public static /* synthetic */ void lambda$setMaximumCharacterSequenceLength$10(int i, int i2, int i3) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password maximum character sequence length to %d", Integer.valueOf(i), Integer.valueOf(i2)), i3);
    }

    public int getMaximumCharacterSequenceLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordMaximumCharacterSequenceLength", callingOrCurrentUserId);
        Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordMaximumCharacterSequenceLength", ((Integer) it.next()).intValue()));
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

    public boolean setMinimumCharacterChangeLength(ContextInfo contextInfo, final int i) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (i < 0) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength", i);
        if (putInt) {
            final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
            final int i2 = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda39
                public final void runOrThrow() {
                    PasswordPolicy.lambda$setMinimumCharacterChangeLength$11(i2, i, callingOrCurrentUserId);
                }
            });
        }
        return putInt;
    }

    public static /* synthetic */ void lambda$setMinimumCharacterChangeLength$11(int i, int i2, int i3) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has changed password minimum number of changed characters to %d", Integer.valueOf(i), Integer.valueOf(i2)), i3);
    }

    public int getMinimumCharacterChangeLength(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength", callingOrCurrentUserId);
        Iterator it = getAllOneLockedChildUsers(callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser(contextInfo.mContainerId, "PASSWORD", "passwordMinimumCharacterChangeUpdatingPasswordLength", ((Integer) it.next()).intValue()));
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

    public final int computeLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2) {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, charSequence.length() + 1, charSequence2.length() + 1);
        for (int i = 0; i <= charSequence.length(); i++) {
            iArr[i][0] = i;
        }
        for (int i2 = 0; i2 <= charSequence2.length(); i2++) {
            iArr[0][i2] = i2;
        }
        for (int i3 = 1; i3 <= charSequence.length(); i3++) {
            for (int i4 = 1; i4 <= charSequence2.length(); i4++) {
                int[] iArr2 = iArr[i3];
                int i5 = i3 - 1;
                int i6 = i4 - 1;
                iArr2[i4] = Math.min(Math.min(iArr[i5][i4] + 1, iArr2[i6] + 1), iArr[i5][i6] + (charSequence.charAt(i5) == charSequence2.charAt(i6) ? 0 : 1));
            }
        }
        return iArr[charSequence.length()][charSequence2.length()];
    }

    public final boolean containsForbiddenStringDistance(ContextInfo contextInfo, String str, String str2) {
        int minimumCharacterChangeLength;
        return (str2 == null || (minimumCharacterChangeLength = getMinimumCharacterChangeLength(contextInfo)) == 0 || computeLevenshteinDistance(str2, str) >= minimumCharacterChangeLength) ? false : true;
    }

    public final boolean containsForbiddenCharacterSequence(ContextInfo contextInfo, String str) {
        boolean z;
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
                    sb.append('\\');
                    sb.append('Q');
                    sb.append(charAt);
                    for (int i = 0; i < maximumCharacterSequenceLength; i++) {
                        charAt = (char) (charAt + c);
                        if (!Character.isAlphabetic(str.charAt(start + i + 1)) || !Character.isAlphabetic(charAt)) {
                            z = true;
                            break;
                        }
                        sb.append(charAt);
                    }
                    z = false;
                    if (z) {
                        continue;
                    } else {
                        sb.append('\\');
                        sb.append('E');
                        if (str.substring(start, start + maximumCharacterSequenceLength + 1).matches(sb.toString())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean setPasswordVisibilityEnabled(ContextInfo contextInfo, final boolean z) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda37
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setPasswordVisibilityEnabled$12(callingOrCurrentUserId, z);
            }
        });
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordVisibilityEnabled", z);
        if (putBoolean) {
            setPasswordVisibilityEnabledSystemUI(callingOrCurrentUserId, z);
        }
        return putBoolean;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordVisibilityEnabled$12(int i, boolean z) {
        if (isPasswordVisibilityEnabledAsUser(i) && !z && !isPersona(i)) {
            Log.d("PasswordPolicy", "do not putIntForUser");
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "show_password", 0, i);
        }
        if (!((UserManager) this.mContext.getSystemService("user")).getUserInfo(i).isManagedProfile() || new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(i)) {
            return;
        }
        Log.d("PasswordPolicy", "!hasSeparateChallenge");
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "show_password", 0, i);
    }

    public boolean isPasswordVisibilityEnabled(ContextInfo contextInfo) {
        return isPasswordVisibilityEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isPasswordVisibilityEnabledAsUser(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "passwordVisibilityEnabled", i);
        Iterator it = getAllOneLockedChildUsers(i).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "passwordVisibilityEnabled", ((Integer) it.next()).intValue()));
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

    public boolean excludeExternalStorageForFailedPasswordsWipe(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndSecurityPermission = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndSecurityPermission);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndSecurityPermission.mCallerUid, "PASSWORD", "excludeExternalStorageWipe", z);
        if (putBoolean) {
            excludeExternalStorageForFailedPasswordsWipeSystemUI(callingOrCurrentUserId, isExternalStorageForFailedPasswordsWipeExcluded(enforceOwnerOnlyAndSecurityPermission));
        }
        return putBoolean;
    }

    public boolean isExternalStorageForFailedPasswordsWipeExcluded(ContextInfo contextInfo) {
        return isExternalStorageForFailedPasswordsWipeExcluded(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isExternalStorageForFailedPasswordsWipeExcluded(int i) {
        ArrayList booleanListAsUser = this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "excludeExternalStorageWipe", i);
        Iterator it = getAllOneLockedChildUsers(i).iterator();
        while (it.hasNext()) {
            booleanListAsUser.addAll(this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "excludeExternalStorageWipe", ((Integer) it.next()).intValue()));
        }
        if (booleanListAsUser == null || booleanListAsUser.size() == 0) {
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

    public boolean lock(ContextInfo contextInfo) {
        int mUMContainerOwnerUid;
        enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        int i = contextInfo.mContainerId;
        if (i == 0) {
            mUMContainerOwnerUid = contextInfo.mCallerUid;
        } else {
            mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
        }
        try {
            if (this.mService.isProfileOwnerOfOrganizationOwnedDeviceMDM(contextInfo.mContainerId)) {
                i = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("PasswordPolicy", "lock is called for user : " + contextInfo.mContainerId + ", ownerUid - " + mUMContainerOwnerUid);
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has successfully locked Workspace", Integer.valueOf(contextInfo.mCallerUid)), UserHandle.getUserId(contextInfo.mCallerUid));
        setAdminLockEnabledSystemUI(i, true, false);
        final Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        bundle.putInt("knox.container.proxy.EXTRA_CONTAINER_OWNER", mUMContainerOwnerUid);
        return ((Bundle) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda34
            public final Object getOrThrow() {
                Bundle lambda$lock$13;
                lambda$lock$13 = PasswordPolicy.lambda$lock$13(bundle);
                return lambda$lock$13;
            }
        })).getInt("android.intent.extra.RETURN_RESULT") == 0;
    }

    public static /* synthetic */ Bundle lambda$lock$13(Bundle bundle) {
        return ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
    }

    public boolean unlock(ContextInfo contextInfo) {
        enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        final int i = contextInfo.mContainerId;
        Log.d("PasswordPolicy", "unlock is called for user : " + contextInfo.mContainerId + ", caller uid - " + contextInfo.mCallerUid);
        try {
            if (this.mService.isProfileOwnerOfOrganizationOwnedDeviceMDM(contextInfo.mContainerId)) {
                i = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        final int i2 = contextInfo.mCallerUid;
        return ((Bundle) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda46
            public final Object getOrThrow() {
                Bundle lambda$unlock$14;
                lambda$unlock$14 = PasswordPolicy.this.lambda$unlock$14(i2, i, bundle);
                return lambda$unlock$14;
            }
        })).getInt("android.intent.extra.RETURN_RESULT") == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Bundle lambda$unlock$14(int i, int i2, Bundle bundle) {
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format("Admin %d has successfully unlocked Workspace", Integer.valueOf(i)), UserHandle.getUserId(i));
        setAdminLockEnabledSystemUI(i2, false, false);
        return ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_UNLOCK", bundle);
    }

    public final boolean fingerprintAvailable(final Context context) {
        return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda16
            public final Object getOrThrow() {
                Boolean lambda$fingerprintAvailable$15;
                lambda$fingerprintAvailable$15 = PasswordPolicy.lambda$fingerprintAvailable$15(context);
                return lambda$fingerprintAvailable$15;
            }
        })).booleanValue();
    }

    public static /* synthetic */ Boolean lambda$fingerprintAvailable$15(Context context) {
        return Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.fingerprint") && ((FingerprintManager) context.getSystemService(FingerprintManager.class)).isHardwareDetected());
    }

    public final boolean irisAvailable(final Context context) {
        return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda4
            public final Object getOrThrow() {
                Boolean lambda$irisAvailable$16;
                lambda$irisAvailable$16 = PasswordPolicy.lambda$irisAvailable$16(context);
                return lambda$irisAvailable$16;
            }
        })).booleanValue();
    }

    public static /* synthetic */ Boolean lambda$irisAvailable$16(Context context) {
        return Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.biometrics.iris"));
    }

    public boolean setMultifactorAuthenticationEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceDoPoOnlySecurityPermissionByContext = enforceDoPoOnlySecurityPermissionByContext(contextInfo);
        if (!fingerprintAvailable(this.mContext) && !irisAvailable(this.mContext)) {
            Log.d("PasswordPolicy", "setMultifactorAuthenticationEnabled: two-factor authentication not available");
            return false;
        }
        Log.d("PasswordPolicy", "setMultifactorAuthenticationEnabled is called for user : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId + ", caller uid - " + enforceDoPoOnlySecurityPermissionByContext.mCallerUid + ", enable - " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceDoPoOnlySecurityPermissionByContext.mCallerUid, "PASSWORD", "multifactorAuthEnabled", z);
        if (putBoolean) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceDoPoOnlySecurityPermissionByContext);
            setMultifactorAuthenticationEnabledSystemUI(callingOrCurrentUserId, z);
            boolean z2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", 0, callingOrCurrentUserId) == 1;
            if (!z2 && z) {
                Log.d("PasswordPolicy", "EnforcePwdChange is called for user as Multifcator needs to be enforced for : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId);
                enforcePwdChange(enforceDoPoOnlySecurityPermissionByContext);
            }
            if (z2 && !z && callingOrCurrentUserId == 0 && (this.mPersonaManagerAdapter.isDoEnabled(callingOrCurrentUserId) || this.mPersonaManagerAdapter.isOrganizationOwnedDeviceWithManagedProfile())) {
                Log.d("PasswordPolicy", "EnforcePwdChange is called for DO case as Multifcator needs to be removed for : " + enforceDoPoOnlySecurityPermissionByContext.mContainerId);
                enforcePwdChange(enforceDoPoOnlySecurityPermissionByContext);
            }
        }
        return putBoolean;
    }

    public boolean isMultifactorAuthenticationEnabled(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d("PasswordPolicy", "isMultifactorAuthenticationEnabled is called for user : " + callingOrCurrentUserId + ", caller uid - " + contextInfo.mCallerUid);
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "multifactorAuthEnabled", callingOrCurrentUserId).iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMultifactorAuthenticationEnabled(int i) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser("PASSWORD", "multifactorAuthEnabled", i).iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public int getSuperLockState(int i) {
        UserManager userManager = this.mUserManager;
        UserInfo userInfo = userManager != null ? userManager.getUserInfo(i) : null;
        if (userInfo == null) {
            return 0;
        }
        boolean isAdminLocked = userInfo.isAdminLocked();
        return userInfo.isLicenseLocked() ? (isAdminLocked ? 1 : 0) + 2 : isAdminLocked ? 1 : 0;
    }

    public void setPasswordQuality(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda13
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordQuality$17(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordQuality$17(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordQualityMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordQuality(ContextInfo contextInfo, final ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        if (this.mService != null) {
            num = (Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda20
                public final Object getOrThrow() {
                    Integer lambda$getPasswordQuality$18;
                    lambda$getPasswordQuality$18 = PasswordPolicy.this.lambda$getPasswordQuality$18(componentName, i);
                    return lambda$getPasswordQuality$18;
                }
            });
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getPasswordQuality$18(ComponentName componentName, int i) {
        return Integer.valueOf(this.mService.getPasswordQuality(componentName, UserHandle.getUserId(i), false));
    }

    public void setPasswordMinimumLength(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda18
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumLength$19(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumLength$19(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumLengthMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLength(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumLength failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumLength failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumUpperCase(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda42
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumUpperCase$20(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumUpperCase$20(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumUpperCaseMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumUpperCase(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumUpperCase failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumUpperCase failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumLowerCase(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda27
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumLowerCase$21(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumLowerCase$21(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumLowerCaseMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        Integer num = 0;
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLowerCase(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumLowerCase failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumLowerCase failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumLetters(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda44
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumLetters$22(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumLetters$22(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumLettersMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumLetters(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumLetters failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumLetters failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumNumeric(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda35
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumNumeric$23(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumNumeric$23(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumNumericMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumNumeric(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumNumeric failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumNumeric failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumSymbols(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda29
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumSymbols$24(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumSymbols$24(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumSymbolsMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumSymbols(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumSymbols failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumSymbols failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordMinimumNonLetter(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda36
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordMinimumNonLetter$25(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordMinimumNonLetter$25(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordMinimumNonLetterMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordMinimumNonLetter(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordMinimumNonLetter failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordMinimumNonLetter failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordHistoryLength(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda30
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordHistoryLength$26(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordHistoryLength$26(ComponentName componentName, int i, int i2) {
        try {
            this.mService.setPasswordHistoryLengthMDM(componentName, i, UserHandle.getUserId(i2));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public int getPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                num = Integer.valueOf(iDevicePolicyManager.getPasswordHistoryLength(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordHistoryLength failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordHistoryLength failed " + e2);
        }
        return num.intValue();
    }

    public void setPasswordExpirationTimeout(ContextInfo contextInfo, final ComponentName componentName, final long j) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda25
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setPasswordExpirationTimeout$27(componentName, j, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordExpirationTimeout$27(ComponentName componentName, long j, int i) {
        try {
            this.mService.setPasswordExpirationTimeoutMDM(componentName, j, UserHandle.getUserId(i));
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }

    public long getPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                return iDevicePolicyManager.getPasswordExpirationTimeout(componentName, UserHandle.getUserId(i), false);
            }
            return 0L;
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordExpirationTimeout failed " + e);
            return 0L;
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordExpirationTimeout failed " + e2);
            return 0L;
        }
    }

    public long getPasswordExpiration(ContextInfo contextInfo, ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Long l = 0L;
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            if (iDevicePolicyManager != null) {
                l = Long.valueOf(iDevicePolicyManager.getPasswordExpiration(componentName, UserHandle.getUserId(i), false));
            }
        } catch (RemoteException e) {
            Log.e("PasswordPolicy", "getPasswordExpiration failed " + e);
        } catch (Exception e2) {
            Log.e("PasswordPolicy", "getPasswordExpiration failed " + e2);
        }
        return l.longValue();
    }

    public boolean isActivePasswordSufficient(ContextInfo contextInfo) {
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda7
                public final Object getOrThrow() {
                    Boolean lambda$isActivePasswordSufficient$28;
                    lambda$isActivePasswordSufficient$28 = PasswordPolicy.this.lambda$isActivePasswordSufficient$28(i);
                    return lambda$isActivePasswordSufficient$28;
                }
            });
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isActivePasswordSufficient$28(int i) {
        return Boolean.valueOf(this.mService.isActivePasswordSufficient((String) null, UserHandle.getUserId(i), false));
    }

    public int getCurrentFailedPasswordAttempts(ContextInfo contextInfo) {
        Integer num = -1;
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            num = (Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda26
                public final Object getOrThrow() {
                    Integer lambda$getCurrentFailedPasswordAttempts$29;
                    lambda$getCurrentFailedPasswordAttempts$29 = PasswordPolicy.this.lambda$getCurrentFailedPasswordAttempts$29(i);
                    return lambda$getCurrentFailedPasswordAttempts$29;
                }
            });
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getCurrentFailedPasswordAttempts$29(int i) {
        return Integer.valueOf(this.mService.getCurrentFailedPasswordAttempts((String) null, UserHandle.getUserId(i), false));
    }

    public int getCurrentFailedPasswordAttemptsInternal(ContextInfo contextInfo) {
        Integer num = -1;
        final int i = enforceOnlySecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            num = (Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda6
                public final Object getOrThrow() {
                    Integer lambda$getCurrentFailedPasswordAttemptsInternal$30;
                    lambda$getCurrentFailedPasswordAttemptsInternal$30 = PasswordPolicy.this.lambda$getCurrentFailedPasswordAttemptsInternal$30(i);
                    return lambda$getCurrentFailedPasswordAttemptsInternal$30;
                }
            });
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getCurrentFailedPasswordAttemptsInternal$30(int i) {
        return Integer.valueOf(this.mService.getCurrentFailedPasswordAttempts((String) null, UserHandle.getUserId(i), false));
    }

    public void setMaximumFailedPasswordsForWipe(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i2 = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda40
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setMaximumFailedPasswordsForWipe$31(componentName, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMaximumFailedPasswordsForWipe$31(ComponentName componentName, int i, int i2) {
        this.mService.setMaximumFailedPasswordsForWipeMDM(componentName, i, UserHandle.getUserId(i2));
    }

    public int getMaximumFailedPasswordsForWipe(ContextInfo contextInfo, final ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        Integer num = 0;
        if (this.mService != null) {
            num = (Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda31
                public final Object getOrThrow() {
                    Integer lambda$getMaximumFailedPasswordsForWipe$32;
                    lambda$getMaximumFailedPasswordsForWipe$32 = PasswordPolicy.this.lambda$getMaximumFailedPasswordsForWipe$32(componentName, i);
                    return lambda$getMaximumFailedPasswordsForWipe$32;
                }
            });
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getMaximumFailedPasswordsForWipe$32(ComponentName componentName, int i) {
        return Integer.valueOf(this.mService.getMaximumFailedPasswordsForWipe(componentName, UserHandle.getUserId(i), false));
    }

    public boolean resetPassword(ContextInfo contextInfo, String str, int i) {
        throw new SecurityException("resetPassword is deprecated, use resetPasswordWithToken()");
    }

    public boolean resetPasswordWithToken(ContextInfo contextInfo, final ComponentName componentName, final String str, final byte[] bArr, final int i) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i2 = enforceSecurityPermission.mCallerUid;
            bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda17
                public final Object getOrThrow() {
                    Boolean lambda$resetPasswordWithToken$33;
                    lambda$resetPasswordWithToken$33 = PasswordPolicy.this.lambda$resetPasswordWithToken$33(i2, componentName, str, bArr, i);
                    return lambda$resetPasswordWithToken$33;
                }
            });
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$resetPasswordWithToken$33(int i, ComponentName componentName, String str, byte[] bArr, int i2) {
        int userId = UserHandle.getUserId(i);
        if (new LockPatternUtils(this.mContext).getActivePasswordQuality(userId) == 458752) {
            Log.d("PasswordPolicy", "resetPassword declined because Lock Quality set to Smartcard for user = " + userId);
            return Boolean.FALSE;
        }
        return Boolean.valueOf(this.mService.resetPasswordWithTokenMDM(componentName, str, bArr, i2, userId));
    }

    public boolean setResetPasswordToken(ContextInfo contextInfo, final ComponentName componentName, final byte[] bArr) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda14
                public final Object getOrThrow() {
                    Boolean lambda$setResetPasswordToken$34;
                    lambda$setResetPasswordToken$34 = PasswordPolicy.this.lambda$setResetPasswordToken$34(i, componentName, bArr);
                    return lambda$setResetPasswordToken$34;
                }
            });
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$setResetPasswordToken$34(int i, ComponentName componentName, byte[] bArr) {
        return Boolean.valueOf(this.mService.setResetPasswordTokenMDM(componentName, bArr, UserHandle.getUserId(i)));
    }

    public boolean clearResetPasswordToken(ContextInfo contextInfo, final ComponentName componentName) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda10
                public final Object getOrThrow() {
                    Boolean lambda$clearResetPasswordToken$35;
                    lambda$clearResetPasswordToken$35 = PasswordPolicy.this.lambda$clearResetPasswordToken$35(i, componentName);
                    return lambda$clearResetPasswordToken$35;
                }
            });
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$clearResetPasswordToken$35(int i, ComponentName componentName) {
        return Boolean.valueOf(this.mService.clearResetPasswordTokenMDM(componentName, UserHandle.getUserId(i)));
    }

    public boolean isResetPasswordTokenActive(ContextInfo contextInfo, final ComponentName componentName) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Boolean bool = Boolean.FALSE;
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda32
                public final Object getOrThrow() {
                    Boolean lambda$isResetPasswordTokenActive$36;
                    lambda$isResetPasswordTokenActive$36 = PasswordPolicy.this.lambda$isResetPasswordTokenActive$36(i, componentName);
                    return lambda$isResetPasswordTokenActive$36;
                }
            });
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isResetPasswordTokenActive$36(int i, ComponentName componentName) {
        return Boolean.valueOf(this.mService.isResetPasswordTokenActiveMDM(componentName, UserHandle.getUserId(i)));
    }

    public void setMaximumTimeToLock(ContextInfo contextInfo, final ComponentName componentName, final long j) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda33
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setMaximumTimeToLock$37(componentName, j, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMaximumTimeToLock$37(ComponentName componentName, long j, int i) {
        this.mService.setMaximumTimeToLockMDM(componentName, j, UserHandle.getUserId(i));
    }

    public long getMaximumTimeToLock(ContextInfo contextInfo, final ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Long l = 0L;
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            l = (Long) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda23
                public final Object getOrThrow() {
                    Long lambda$getMaximumTimeToLock$38;
                    lambda$getMaximumTimeToLock$38 = PasswordPolicy.this.lambda$getMaximumTimeToLock$38(componentName, i);
                    return lambda$getMaximumTimeToLock$38;
                }
            });
        }
        return l.longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Long lambda$getMaximumTimeToLock$38(ComponentName componentName, int i) {
        return Long.valueOf(this.mService.getMaximumTimeToLock(componentName, UserHandle.getUserId(i), false));
    }

    public final void removeBiometricAuthentication(final int i, final int i2) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$removeBiometricAuthentication$39(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeBiometricAuthentication$39(int i, int i2) {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
        Log.d("PasswordPolicy", "removeBiometricAuthentication()");
        if ((i & 1) != 0) {
            setBiometricState(i2, lockPatternUtils, 1, 0);
        }
        if ((i & 4) != 0) {
            setBiometricState(i2, lockPatternUtils, 256, 0);
        }
    }

    public final void setBiometricState(int i, LockPatternUtils lockPatternUtils, int i2, int i3) {
        lockPatternUtils.setBiometricState(i2, i3, i);
    }

    public boolean setBiometricAuthenticationEnabled(ContextInfo contextInfo, int i, boolean z) {
        int i2;
        long binderClearCallingIdentity;
        int i3;
        if (i < 0) {
            return false;
        }
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        try {
            i2 = this.mEdmStorageProvider.getInt(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordBioAuthEnabled");
        } catch (Exception unused) {
            i2 = 255;
        }
        if (i2 >= 0) {
            i4 = i2;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceSecurityPermission.mCallerUid, "PASSWORD", "passwordBioAuthEnabled", z ? i4 | i : i4 & (~i));
        if (putInt) {
            if (!z) {
                removeBiometricAuthentication(i, callingOrCurrentUserId);
                binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
                try {
                    UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                    if (userManager.getUserInfo(callingOrCurrentUserId).isManagedProfile() && !new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(callingOrCurrentUserId)) {
                        removeBiometricAuthentication(i, userManager.getProfileParent(callingOrCurrentUserId).getUserHandle().getIdentifier());
                    }
                    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                } finally {
                    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                }
            }
            binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
            if ((i & 2) != 0) {
                try {
                    i3 = 1;
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format(z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_IRIS" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_IRIS", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                i3 = 1;
            }
            if ((i & 1) != 0) {
                int myPid = Process.myPid();
                String str = z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FINGERPRINT" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FINGERPRINT";
                Object[] objArr = new Object[i3];
                objArr[0] = Integer.valueOf(enforceSecurityPermission.mCallerUid);
                AuditLog.logAsUser(5, 1, true, myPid, "PasswordPolicy", String.format(str, objArr), callingOrCurrentUserId);
            }
            if ((i & 4) != 0) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "PasswordPolicy", String.format(z ? "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FACE" : "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FACE", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
            }
        }
        return putInt;
    }

    public boolean isMDMDisabledFP(int i) {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordBioAuthEnabled", i).iterator();
        boolean z = false;
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

    public boolean isBiometricAuthenticationEnabled(ContextInfo contextInfo, int i) {
        return isBiometricAuthenticationEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo), i);
    }

    public boolean isBiometricAuthenticationEnabledAsUser(int i, int i2) {
        if (!isValidBioAuth(i2)) {
            Log.w("PasswordPolicy", "isBiometricAuthenticationEnabledAsUser: bioAuth is wrong value : " + i2 + ", userId = " + i);
            return false;
        }
        ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordBioAuthEnabled", i);
        Iterator it = getAllOneLockedChildUsers(i).iterator();
        while (it.hasNext()) {
            intListAsUser.addAll(this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordBioAuthEnabled", ((Integer) it.next()).intValue()));
        }
        Iterator it2 = intListAsUser.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            Integer num = (Integer) it2.next();
            if (num.intValue() >= 0) {
                if ((num.intValue() & i2) != i2) {
                    Log.i("PasswordPolicy", "isBiometricAuthenticationEnabledAsUser(): disallowed, " + i2 + ", userId = " + i);
                    return false;
                }
                z = true;
            }
        }
        if (z) {
            Log.d("PasswordPolicy", "isBiometricAuthenticationEnabledAsUser: return true (hasValue) " + i2);
            return true;
        }
        if (i2 == 1 && !this.mPersonaManagerAdapter.isValidKnoxId(i)) {
            return true;
        }
        if (i2 == 2 && !this.mPersonaManagerAdapter.isValidKnoxId(i)) {
            return true;
        }
        if (i2 == 4 && !this.mPersonaManagerAdapter.isValidKnoxId(i)) {
            Log.d("PasswordPolicy", "isBiometricAuthenticationEnabledAsUser(FACE): return true ");
            return true;
        }
        Log.d("PasswordPolicy", "isBiometricAuthenticationEnabledAsUser(): allowed as default, " + i2 + ", userId = " + i);
        return true;
    }

    public final boolean isValidBioAuth(int i) {
        if (i == 1) {
            return true;
        }
        if (i != 0 && i >= 0) {
            while (i % 2 == 0) {
                i /= 2;
            }
            if (i == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordTableExist(ContextInfo contextInfo) {
        return !this.mEdmStorageProvider.getIntListAsUser("PASSWORD", "passwordBioAuthEnabled", Utils.getCallingOrCurrentUserId(contextInfo)).isEmpty();
    }

    public Map getSupportedBiometricAuthentications(ContextInfo contextInfo) {
        PackageManager packageManager = this.mContext.getPackageManager();
        HashMap hashMap = new HashMap();
        if (packageManager != null && packageManager.hasSystemFeature("android.hardware.fingerprint")) {
            hashMap.put(1, "Fingerprint");
        }
        hashMap.put(4, "Face");
        return hashMap;
    }

    public void setKeyguardDisabledFeatures(ContextInfo contextInfo, final ComponentName componentName, final int i) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (i == 0 || i == 16) {
            if (this.mService != null) {
                final int i2 = enforceSecurityPermission.mCallerUid;
                this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda24
                    public final void runOrThrow() {
                        PasswordPolicy.this.lambda$setKeyguardDisabledFeatures$40(componentName, i, i2);
                    }
                });
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid features " + i + " for container");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setKeyguardDisabledFeatures$40(ComponentName componentName, int i, int i2) {
        this.mService.setKeyguardDisabledFeaturesMDM(componentName, i, UserHandle.getUserId(i2));
    }

    public int getKeyguardDisabledFeatures(ContextInfo contextInfo, final ComponentName componentName) {
        getEDM().enforceComponentCheck(contextInfo, componentName);
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        Integer num = 0;
        if (this.mService != null) {
            final int i = enforceSecurityPermission.mCallerUid;
            num = (Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda19
                public final Object getOrThrow() {
                    Integer lambda$getKeyguardDisabledFeatures$41;
                    lambda$getKeyguardDisabledFeatures$41 = PasswordPolicy.this.lambda$getKeyguardDisabledFeatures$41(componentName, i);
                    return lambda$getKeyguardDisabledFeatures$41;
                }
            });
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getKeyguardDisabledFeatures$41(ComponentName componentName, int i) {
        return Integer.valueOf(this.mService.getKeyguardDisabledFeatures(componentName, UserHandle.getUserId(i), false));
    }

    public int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i) {
        int i2;
        try {
            i2 = Integer.parseInt(this.mEdmStorageProvider.getGenericValueAsUser("keyguardDisabledFeatures", i));
        } catch (Exception e) {
            Log.w("PasswordPolicy", "getKeyguardDisabledFeatures() failed");
            e.printStackTrace();
            i2 = 0;
        }
        Log.d("PasswordPolicy", "getKeyguardDisabledFeatures() " + i2);
        return i2;
    }

    public void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2) {
        enforceSystemUser();
        if (i != 0 && (i & 16) == 0) {
            Log.w("PasswordPolicy", "setKeyguardDisabledFeatures() which not supported " + i);
            return;
        }
        try {
            if (this.mEdmStorageProvider.putGenericValueAsUser("keyguardDisabledFeatures", String.valueOf(1), i2)) {
                Log.d("PasswordPolicy", "setKeyguardDisabledFeatures() true");
            } else {
                Log.d("PasswordPolicy", "setKeyguardDisabledFeatures() false");
            }
        } catch (Exception e) {
            Log.w("PasswordPolicy", "setKeyguardDisabledFeatures() failed");
            e.printStackTrace();
        }
    }

    public void reboot(ContextInfo contextInfo, String str) {
        boolean z;
        Log.i("PasswordPolicy", "reboot() called, userId = " + Utils.getCallingOrCurrentUserId(enforceSecurityPermission(contextInfo)));
        try {
            z = this.mService.rebootMDM(str);
        } catch (Exception e) {
            Log.e("PasswordPolicy", "reboot() has failed. ", e);
            z = false;
        }
        if (z) {
            RestrictionToastManager.show(R.string.biometric_error_generic);
        }
    }

    public final void updateSystemUIMonitor(int i) {
        if (new LockPatternUtils(this.mContext).isSeparateProfileChallengeEnabled(i)) {
            i = ((UserManager) this.mContext.getSystemService("user")).getProfileParent(i).getUserHandle().getIdentifier();
        }
        setPasswordLockDelaySystemUI(i, getPasswordLockDelay(i));
        setPwdChangeRequestedSystemUI(i, isChangeRequestedAsUser(i));
        initMaximumFailedPasswordsForDisableSystemUI(i);
        excludeExternalStorageForFailedPasswordsWipeSystemUI(i, isExternalStorageForFailedPasswordsWipeExcluded(i));
        setMultifactorAuthenticationEnabledSystemUI(i, isMultifactorAuthenticationEnabled(i));
        setPasswordVisibilityEnabledSystemUI(i, isPasswordVisibilityEnabledAsUser(i));
        int superLockState = getSuperLockState(i);
        setAdminLockEnabledSystemUI(i, (superLockState & 1) != 0, (superLockState & 2) != 0);
    }

    public final void initMaximumFailedPasswordsForDisableSystemUI(int i) {
        setMaximumFailedPasswordsForDisableSystemUI(i, getMaximumFailedPasswordsForDisable(i));
        String str = SystemProperties.get("ro.organization_owned");
        if (str == null || !str.equals("false")) {
            return;
        }
        for (Integer num : getAllOneLockedChildUsers(i)) {
            setMaximumFailedPasswordsForDisableSystemUI(num.intValue(), getMaximumFailedPasswordsForDisable(num.intValue()));
        }
    }

    public final void setPasswordLockDelaySystemUI(final int i, final int i2) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setPasswordLockDelaySystemUI$42(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordLockDelaySystemUI$42(int i, int i2) {
        SystemUIAdapter.getInstance(this.mContext).setPasswordLockDelayAsUser(i, i2);
    }

    public final void setPwdChangeRequestedSystemUI(final int i, final int i2) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setPwdChangeRequestedSystemUI$43(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPwdChangeRequestedSystemUI$43(int i, int i2) {
        SystemUIAdapter.getInstance(this.mContext).setPwdChangeRequestedAsUser(i, i2);
    }

    public final void setMaximumFailedPasswordsForDisableSystemUI(final int i, final int i2) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setMaximumFailedPasswordsForDisableSystemUI$44(i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMaximumFailedPasswordsForDisableSystemUI$44(int i, int i2) {
        SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(this.mContext);
        if (i > 0) {
            systemUIAdapter.setMaximumFailedPasswordsForDisableAsUser(i2, i, getPkgNameforMaximumFailedAttemptforDisable(i));
        } else {
            systemUIAdapter.setMaximumFailedPasswordsForDisableAsUser(i2, i, null);
        }
    }

    public final void excludeExternalStorageForFailedPasswordsWipeSystemUI(final int i, final boolean z) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda38
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$excludeExternalStorageForFailedPasswordsWipeSystemUI$45(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$excludeExternalStorageForFailedPasswordsWipeSystemUI$45(int i, boolean z) {
        SystemUIAdapter.getInstance(this.mContext).excludeExternalStorageForFailedPasswordsWipeAsUser(i, z);
    }

    public final void setMultifactorAuthenticationEnabledSystemUI(final int i, final boolean z) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda45
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setMultifactorAuthenticationEnabledSystemUI$46(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMultifactorAuthenticationEnabledSystemUI$46(int i, boolean z) {
        SystemUIAdapter.getInstance(this.mContext).setMultifactorAuthEnabled(i, z);
    }

    public final void setPasswordVisibilityEnabledSystemUI(final int i, final boolean z) {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda49
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$setPasswordVisibilityEnabledSystemUI$47(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPasswordVisibilityEnabledSystemUI$47(int i, boolean z) {
        SystemUIAdapter.getInstance(this.mContext).setPasswordVisibilityEnabledAsUser(i, z);
    }

    public final void setAdminLockEnabledSystemUI(final int i, final boolean z, final boolean z2) {
        if (!z2 || isDualDarLicenseLockedCase(i)) {
            if (getPersonaManagerAdapter().isKnoxId(i)) {
                Log.d("PasswordPolicy", "return : this is Knox user");
                return;
            }
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda11
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setAdminLockEnabledSystemUI$48(i, z, z2);
                }
            });
            if (z2) {
                try {
                    if (this.mUserManager.isUserUnlocked()) {
                        Log.d("PasswordPolicy", "validateLicenses() called");
                        this.mLicenseService.validateLicenses();
                    }
                } catch (Exception e) {
                    Log.e("PasswordPolicy", "validateLicenses() failed. ", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAdminLockEnabledSystemUI$48(int i, boolean z, boolean z2) {
        SystemUIAdapter.getInstance(this.mContext).setAdminLockEnabled(i, z, z2);
    }

    public final IPersonaManagerAdapter getPersonaManagerAdapter() {
        return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }

    public final boolean isDualDarLicenseLockedCase(int i) {
        if (getPersonaManagerAdapter().isDoEnabled(i) && getPersonaManagerAdapter().isDarDualEncryptionEnabled(i)) {
            return true;
        }
        if (!this.mDpm.isOrganizationOwnedDeviceWithManagedProfile()) {
            return false;
        }
        Iterator it = this.mUserManager.getProfiles(i).iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            if (getPersonaManagerAdapter().isKnoxId(identifier) && getPersonaManagerAdapter().isDarDualEncryptionEnabled(identifier)) {
                try {
                    if (this.mService.isProfileOwnerOfOrganizationOwnedDeviceMDM(identifier)) {
                        return true;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean addRequiredPasswordPattern(ContextInfo contextInfo, String str) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (str == null || str.length() == 0) {
            return false;
        }
        String string = this.mEdmStorageProvider.getString(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordRequiredPattern");
        if (string != null) {
            str = string + "|" + str;
        }
        return this.mEdmStorageProvider.putString(enforceSecurityPermission.mCallerUid, enforceSecurityPermission.mContainerId, "PASSWORD", "passwordRequiredPattern", str);
    }

    public final List getAllOneLockedChildUsers(int i) {
        UserManager userManager;
        ArrayList arrayList = new ArrayList();
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        Log.d("PasswordPolicy", "userHandle " + i);
        try {
            try {
                userManager = (UserManager) this.mContext.getSystemService("user");
            } catch (Exception e) {
                Log.e("PasswordPolicy", "getAllOneLockedChildUsers() failed. ", e);
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
            return arrayList;
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void notifyPasswordPolicyOneLockChanged(boolean z, final int i) {
        updateSystemUIMonitor(i);
        if (z) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda28
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$notifyPasswordPolicyOneLockChanged$49(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPasswordPolicyOneLockChanged$49(int i) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        for (int i2 : BIOMETRIC_AUTHENTICATION_TYPES) {
            if (!isBiometricAuthenticationEnabledAsUser(i, i2)) {
                removeBiometricAuthentication(i2, userManager.getProfileParent(i).getUserHandle().getIdentifier());
            }
        }
    }

    public final void registerDexBlocker() {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda53
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$registerDexBlocker$50();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDexBlocker$50() {
        ((SemDesktopModeManager) this.mContext.getApplicationContext().getSystemService("desktopmode")).registerBlocker(this.mBlocker);
        Log.d("PasswordPolicy", "registerDexBlocker was registered");
    }

    public final void unRegisterDexBlocker() {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda51
            public final void runOrThrow() {
                PasswordPolicy.this.lambda$unRegisterDexBlocker$51();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unRegisterDexBlocker$51() {
        ((SemDesktopModeManager) this.mContext.getApplicationContext().getSystemService("desktopmode")).unregisterBlocker(this.mBlocker);
        Log.d("PasswordPolicy", "registerDexBlocker was unregistered");
    }

    public boolean isClearLockAllowed() {
        Boolean bool = (Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda15
            public final Object getOrThrow() {
                Boolean lambda$isClearLockAllowed$52;
                lambda$isClearLockAllowed$52 = PasswordPolicy.this.lambda$isClearLockAllowed$52();
                return lambda$isClearLockAllowed$52;
            }
        });
        Log.d("PasswordPolicy", "isClearLockAllowed - true");
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isClearLockAllowed$52() {
        ComponentName deviceOwnerComponentOnCallingUser;
        Boolean bool = Boolean.TRUE;
        if (this.mDpm == null || this.mService == null) {
            return bool;
        }
        if (this.mPersonaManagerAdapter.isDoEnabled(0) && (deviceOwnerComponentOnCallingUser = this.mDpm.getDeviceOwnerComponentOnCallingUser()) != null && (this.mDpm.getPasswordQuality(deviceOwnerComponentOnCallingUser, 0) > 0 || this.mDpm.getPasswordMinimumLength(deviceOwnerComponentOnCallingUser, 0) > 0)) {
            Log.d("PasswordPolicy", "isClearLockAllowed - false due to DO and pwd policy");
            bool = Boolean.FALSE;
        }
        UserManager userManager = this.mUserManager;
        if (userManager != null) {
            List users = userManager.getUsers();
            List allOneLockedChildUsers = getAllOneLockedChildUsers(0);
            for (int i = 0; i < users.size(); i++) {
                UserInfo userInfo = (UserInfo) users.get(i);
                if (userInfo.isManagedProfile()) {
                    ComponentName profileOwnerAsUser = this.mDpm.getProfileOwnerAsUser(userInfo.id);
                    if (profileOwnerAsUser != null && (this.mService.getPasswordQuality(profileOwnerAsUser, userInfo.id, true) > 0 || this.mService.getPasswordMinimumLength(profileOwnerAsUser, userInfo.id, true) > 0)) {
                        Log.d("PasswordPolicy", "isClearLockAllowed - false due to PO and parent pwd policy");
                        bool = Boolean.FALSE;
                    }
                    if (allOneLockedChildUsers.contains(Integer.valueOf(userInfo.id)) && profileOwnerAsUser != null && (this.mDpm.getPasswordQuality(profileOwnerAsUser, userInfo.id) > 0 || this.mDpm.getPasswordMinimumLength(profileOwnerAsUser, userInfo.id) > 0)) {
                        Log.d("PasswordPolicy", "isClearLockAllowed - false due to PO and one lock");
                        bool = Boolean.FALSE;
                    }
                }
            }
        }
        if (getMaximumFailedPasswordsForDisable(0) <= 0) {
            return bool;
        }
        Log.d("PasswordPolicy", "isClearLockAllowed - false due to FailedPasswordsForDisable policy");
        return Boolean.FALSE;
    }

    public final String getPkgNameforMaximumFailedAttemptforDisable(int i) {
        return this.mContext.getPackageManager().getNameForUid(this.mEdmStorageProvider.getAdminByField("PASSWORD", "passwordAttemptDeviceDisable", Integer.toString(i)));
    }

    class LocalService extends PasswordPolicyInternal {
        public LocalService() {
        }

        public int isChangeRequestedAsUser(int i) {
            return PasswordPolicy.this.mPolicyCache.isChangeRequestedAsUser(i);
        }
    }

    public class ActivationMonitor implements IActivationKlmElmObserver {
        public ActivationMonitor() {
            getLicenseService();
            if (PasswordPolicy.this.mLicenseService != null) {
                PasswordPolicy.this.mLicenseService.addElmKlmObserver(this);
            }
        }

        public final void getLicenseService() {
            if (PasswordPolicy.this.mLicenseService == null) {
                PasswordPolicy.this.mLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
            }
        }

        public final boolean isDeviceOwnerPackage(String str) {
            ComponentName deviceOwnerComponentOnAnyUser = PasswordPolicy.this.mDpm.getDeviceOwnerComponentOnAnyUser();
            return (str == null || deviceOwnerComponentOnAnyUser == null || !str.equals(deviceOwnerComponentOnAnyUser.getPackageName())) ? false : true;
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateKlm(String str, LicenseResult licenseResult) {
            Log.d("PasswordPolicy", "onUpdateKlm is called");
            try {
                if (PasswordPolicy.this.mPersonaManagerAdapter.isDoEnabled(0) && licenseResult.isSuccess() && licenseResult.getType() == LicenseResult.Type.KLM_VALIDATION && PasswordPolicy.this.mLicenseService != null && isDeviceOwnerPackage(str)) {
                    boolean isServiceAvailable = PasswordPolicy.this.mLicenseService.isServiceAvailable(str, "com.samsung.android.knox.permission.KNOX_CONTAINER");
                    Log.d("PasswordPolicy", "onUpdateKlm - isServiceAvailable : " + isServiceAvailable);
                    if (!isServiceAvailable || PasswordPolicy.this.mUserManager.getUserInfo(0).isAdminLocked()) {
                        return;
                    }
                    PasswordPolicy.this.setAdminLockEnabledSystemUI(0, false, false);
                }
            } catch (Exception e) {
                Log.e("PasswordPolicy", "onUpdateKlm() failed ", e);
            }
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateElm(String str, LicenseResult licenseResult) {
            Log.d("PasswordPolicy", "onUpdateElm is called");
            try {
                if (PasswordPolicy.this.mPersonaManagerAdapter.isDoEnabled(0) && licenseResult.isSuccess() && licenseResult.getType() == LicenseResult.Type.ELM_VALIDATION && PasswordPolicy.this.mLicenseService != null && isDeviceOwnerPackage(str)) {
                    boolean isServiceAvailable = PasswordPolicy.this.mLicenseService.isServiceAvailable(str, "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                    Log.d("PasswordPolicy", "onUpdateElm - isServiceAvailable : " + isServiceAvailable);
                    if (!isServiceAvailable || PasswordPolicy.this.mUserManager.getUserInfo(0).isAdminLocked()) {
                        return;
                    }
                    PasswordPolicy.this.setAdminLockEnabledSystemUI(0, false, false);
                }
            } catch (Exception e) {
                Log.e("PasswordPolicy", "onUpdateElm() failed ", e);
            }
        }
    }

    public void setTrustAgentConfiguration(ContextInfo contextInfo, final ComponentName componentName, final ComponentName componentName2, final PersistableBundle persistableBundle) {
        Log.d("PasswordPolicy", "setTrustAgentConfiguration");
        final int i = enforceSecurityPermission(contextInfo).mCallerUid;
        if (this.mService != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    PasswordPolicy.this.lambda$setTrustAgentConfiguration$53(i, componentName, componentName2, persistableBundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTrustAgentConfiguration$53(int i, ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        try {
            this.mService.setTrustAgentConfigurationMDM(UserHandle.getUserId(i), componentName, componentName2, persistableBundle);
        } catch (RemoteException e) {
            Log.w("PasswordPolicy", "Failed talking with device policy service", e);
        }
    }
}
