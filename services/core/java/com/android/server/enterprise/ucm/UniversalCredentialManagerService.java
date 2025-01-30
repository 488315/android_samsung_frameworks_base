package com.android.server.enterprise.ucm;

import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.ServiceKeeper;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.ucm.configurator.CACertificateInfo;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.core.jcajce.UcmKeystoreProvider;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* loaded from: classes2.dex */
public class UniversalCredentialManagerService extends IUniversalCredentialManager.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public boolean mExistCert;
    public boolean mExistWhitelist;
    public PackageManager mPm;
    public RequestIdGenerator mRIdGenerator;
    public UCSMHandler mUCSMHandler;
    public File ucsCertLocation;
    public File ucsODECertLocation;
    public static boolean DBG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static String TAG = "UniversalCredentialManagerService";
    public static Context sContext = null;
    public static final List systemPlugin = Arrays.asList("com.samsung.ucs.agent.boot", "com.samsung.ucs.agent.ese", "com.sec.smartcard.manager");
    public static IUcmService mUcseService = null;
    public EnterpriseDeviceManager mEDM = null;
    public List adminIds = new ArrayList();
    public HashMap expiredAdmins = new HashMap();
    public HashMap activePluginsCache = new HashMap();
    public HashMap whitelistedAppsCache = new HashMap();
    public HashMap exemptedAppsCache = new HashMap();
    public UniversalCredentialUtil mUniversalCredentialUtil = null;
    public SemPersonaManager mPersona = null;
    public KeyguardManager mKgm = null;
    public boolean mIsSystemReceiverRegistered = false;
    public BroadcastReceiver mSystemReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.ucm.UniversalCredentialManagerService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(UniversalCredentialManagerService.TAG, "inside mBReciever onReceive : " + action);
            if (action.equals("android.intent.action.USER_REMOVED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                Log.i(UniversalCredentialManagerService.TAG, "ACTION_USER_REMOVED UserHandle : " + intExtra);
                Message obtainMessage = UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(1);
                obtainMessage.arg1 = intExtra;
                UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(obtainMessage);
                return;
            }
            if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(6));
                UniversalCredentialManagerService.this.showEnforcedLockTypeNotificationForAllUser();
                UniversalCredentialManagerService.this.setKeyguardProperty();
                return;
            }
            if (action.equals("android.intent.action.SCREEN_ON") || action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.USER_PRESENT")) {
                UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(7));
                return;
            }
            if (action.equals("android.intent.action.DEVICE_LOCKED_CHANGED")) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                boolean isDeviceLocked = UniversalCredentialManagerService.this.getKeyguardManager().isDeviceLocked(intExtra2);
                Log.i(UniversalCredentialManagerService.TAG, "mLockEventReceiver. userId [" + intExtra2 + "] isDeviceLocked [" + isDeviceLocked + "]");
                Message obtainMessage2 = UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(9);
                obtainMessage2.arg1 = intExtra2;
                obtainMessage2.arg2 = !isDeviceLocked ? 1 : 0;
                UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(obtainMessage2);
            }
        }
    };
    public BroadcastReceiver mPackageRemovedReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.ucm.UniversalCredentialManagerService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Message obtainMessage = UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(3);
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            obtainMessage.obj = new int[]{intExtra};
            Log.i(UniversalCredentialManagerService.TAG, "ACTION_PACKAGE_REMOVED : replacingApp -" + booleanExtra);
            if (!booleanExtra) {
                UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(obtainMessage);
            } else {
                Log.i(UniversalCredentialManagerService.TAG, "ACTION_PACKAGE_REMOVED : No need to cleanup db entries for app update");
            }
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public static KnoxAnalyticsData getKAData(String str) {
        return new KnoxAnalyticsData("KNOX_UCM", 2, "API:" + str);
    }

    public static KnoxAnalyticsData getKAData(String str, String str2) {
        KnoxAnalyticsData kAData = getKAData(str);
        kAData.setProperty("csPackageName", str2);
        return kAData;
    }

    public UniversalCredentialManagerService(Context context) {
        this.mContext = null;
        this.mEdmStorageProvider = null;
        this.mUCSMHandler = null;
        this.mRIdGenerator = null;
        this.mPm = null;
        this.ucsCertLocation = null;
        this.ucsODECertLocation = null;
        this.mExistCert = false;
        this.mExistWhitelist = false;
        if (DBG) {
            Log.i(TAG, "Constructor");
        }
        this.mContext = context;
        sContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mUCSMHandler = new UCSMHandler();
        this.mRIdGenerator = new RequestIdGenerator();
        this.mPm = this.mContext.getPackageManager();
        if (!getActivePlugin().isEmpty()) {
            registerReceiver();
        }
        UcsReceiver ucsReceiver = new UcsReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS");
        this.mContext.registerReceiver(ucsReceiver, intentFilter, "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE", null);
        this.mUCSMHandler.sendMessage(this.mUCSMHandler.obtainMessage(2));
        this.ucsCertLocation = new File(new File(Environment.getDataDirectory(), "system"), "ucm_ca_cert");
        this.ucsODECertLocation = new File("/efs/sec_efs/ucm_ca_cert");
        if (!this.ucsCertLocation.exists() && !this.ucsCertLocation.mkdirs()) {
            Log.i(TAG, "Error!!! Cannot create root directory: " + this.ucsCertLocation.getAbsolutePath());
        }
        if (!this.ucsODECertLocation.exists() && !this.ucsODECertLocation.mkdirs()) {
            Log.i(TAG, "Error!!! Cannot create root ODE CA cert directory: " + this.ucsODECertLocation.getAbsolutePath());
        }
        this.mUCSMHandler.sendMessage(this.mUCSMHandler.obtainMessage(8));
        if ("false".equals(SystemProperties.get("persist.security.ucmcrypto", "false"))) {
            this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
            this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
            updateUcmCryptoProp();
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public static synchronized IUcmService getUcmService() {
        IUcmService iUcmService;
        synchronized (UniversalCredentialManagerService.class) {
            if (mUcseService == null) {
                mUcseService = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
            }
            iUcmService = mUcseService;
        }
        return iUcmService;
    }

    public final UniversalCredentialUtil getUniversalCredentialUtil() {
        if (this.mUniversalCredentialUtil == null) {
            this.mUniversalCredentialUtil = UniversalCredentialUtil.getInstance();
        }
        return this.mUniversalCredentialUtil;
    }

    public final void updateUcmCryptoProp() {
        boolean z = SystemProperties.getBoolean("persist.security.ucmcrypto", false);
        boolean z2 = SystemProperties.getBoolean("security.ucmcrypto", false);
        if (!this.mExistCert && !this.mExistWhitelist) {
            if (z) {
                SystemProperties.set("persist.security.ucmcrypto", "false");
                SystemProperties.set("security.ucmcrypto", "false");
                UcmKeyStoreHelper.updateUcmProvider(false);
                return;
            }
            return;
        }
        if (z || z2) {
            return;
        }
        SystemProperties.set("persist.security.ucmcrypto", "true");
        SystemProperties.set("security.ucmcrypto", "true");
        UcmKeyStoreHelper.updateUcmProvider(true);
    }

    public final boolean checkCountFromEdmDB(String str) {
        if (!"UniversalCredentialWhiteListTable".equals(str) && !"UniversalCredentialCertificateTable".equals(str)) {
            Log.i(TAG, "Input param is undefined flag, can't update flag");
            return false;
        }
        try {
            return this.mEdmStorageProvider.getCount(str, null) > 0;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    /* renamed from: com.android.server.enterprise.ucm.UniversalCredentialManagerService$1 */
    public class C14981 extends ISystemPersonaObserver.Stub {
        public final /* synthetic */ UniversalCredentialManagerService this$0;

        public void onKnoxContainerLaunch(int i) {
        }

        public void onPersonaActive(int i) {
        }

        public void onRemovePersona(int i) {
        }

        public void onResetPersona(int i) {
        }

        public void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2) {
            Log.i(UniversalCredentialManagerService.TAG, " inside onstatechange " + i + " new " + semPersonaState2 + " old " + semPersonaState);
            Message obtainMessage = this.this$0.mUCSMHandler.obtainMessage(9);
            obtainMessage.arg1 = i;
            if (semPersonaState2.equals(SemPersonaState.ACTIVE)) {
                obtainMessage.arg2 = 1;
            } else {
                obtainMessage.arg2 = 0;
            }
            this.this$0.mUCSMHandler.sendMessage(obtainMessage);
        }
    }

    public final KeyguardManager getKeyguardManager() {
        if (this.mKgm == null) {
            this.mKgm = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }
        return this.mKgm;
    }

    public class UcsReceiver extends BroadcastReceiver {
        public UcsReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(UniversalCredentialManagerService.TAG, "UcsReceiver intent " + intent.getAction());
            if (intent.getAction().equals("com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS")) {
                if (intent.getExtras() != null) {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        UniversalCredentialManagerService.this.notifyUCMConfigStatus(extras);
                        return;
                    } else {
                        Log.i(UniversalCredentialManagerService.TAG, "UcsReceiver no bundle extras received from plugin");
                        return;
                    }
                }
                Log.i(UniversalCredentialManagerService.TAG, "UcsReceiver no extras received from plugin....");
            }
        }
    }

    public void notifyUCMConfigStatus(Bundle bundle) {
        String[] packagesForUid;
        Log.i(TAG, "notifyUCMConfigStatus");
        if (!isCallerPackageManaged()) {
            checkCallerPermissionFor("notifyUCMConfigStatus");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int i = bundle.getInt("request_id", 0);
                int i2 = bundle.getInt("adminUid", 0);
                int i3 = bundle.getInt("status_code", -1);
                Log.i(TAG, "notifyUCMConfigStatus requestId -" + i + ", adminUid-" + i2);
                if (i2 != 0 && i != 0 && i3 != -1 && (packagesForUid = this.mPm.getPackagesForUid(i2)) != null) {
                    for (String str : packagesForUid) {
                        Log.i(TAG, "Sending config update to package " + str);
                        try {
                            notifyUCMConfigStatus(str, bundle, new UserHandle(UserHandle.getUserId(i2)));
                        } catch (Exception e) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                    }
                }
            } catch (Exception unused) {
                Log.e(TAG, "notifyUCMConfigStatus failed");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCallerPackageManaged() {
        Log.i(TAG, "isCallerPackageManaged, Start");
        try {
            int callingUid = Binder.getCallingUid();
            String nameForUid = this.mPm.getNameForUid(callingUid);
            Log.i(TAG, "isCallerPackageManaged, callingUid: " + callingUid + ", packageName: " + nameForUid);
            if (callingUid != -1 && nameForUid != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("storagePackageName", nameForUid);
                contentValues.put("appUid", Integer.valueOf(callingUid));
                try {
                    if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                        return true;
                    }
                } catch (Exception unused) {
                    Log.e(TAG, "cannot find information");
                }
            }
            return false;
        } catch (Exception unused2) {
            Log.e(TAG, "cannot get packageName");
            return false;
        }
    }

    public final void notifyUCMConfigStatus(String str, Bundle bundle, UserHandle userHandle) {
        if (notifyUCMConfigStatusByPermission(str, bundle, userHandle, "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT") || notifyUCMConfigStatusByPermission(str, bundle, userHandle, "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT")) {
            return;
        }
        notifyUCMConfigStatusByPermission(str, bundle, userHandle, "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
    }

    public final boolean notifyUCMConfigStatusByPermission(String str, Bundle bundle, UserHandle userHandle, String str2) {
        try {
            if (AppGlobals.getPackageManager().checkPermission(str2, str, userHandle.getIdentifier()) != 0) {
                return false;
            }
            Log.i(TAG, "Package has UCM permission. : " + str2);
            Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_CONFIG_STATUS");
            intent.setPackage(str);
            intent.putExtras(bundle);
            this.mContext.sendBroadcastAsUser(intent, userHandle, str2);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enforceSecurityPermission(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_MGMT");
        if (credentialStorage != null) {
            String str = credentialStorage.name;
            String str2 = credentialStorage.packageName;
            if (str != null && str2 != null) {
                if (isSystemStorage(str2)) {
                    Log.i(TAG, "Enforcing ESE permission");
                    arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT");
                } else {
                    Log.i(TAG, "Enforcing OTHER permission");
                    arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
                }
            } else {
                Log.i(TAG, "Input parameter is not proper");
                throw new SecurityException("Input parameter is not proper");
            }
        } else {
            Log.i(TAG, "Check if caller has some UCM permission...");
            arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT");
            arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
        }
        int i = contextInfo.mCallerUid;
        getEDM().enforcePermissionByContext(contextInfo, arrayList);
        if (contextInfo.mCallerUid != i) {
            Log.i(TAG, "enforceSecurityPermission : oldCallerId = " + i + " newCallerId = " + contextInfo.mCallerUid);
        }
        Log.i(TAG, "enforceSecurityPermission : caller has valid UCM permission");
    }

    public void enforceActiveAdminPermission(ContextInfo contextInfo) {
        Log.i(TAG, "Enforcing UCM PRIVILEGED permission");
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT");
        arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_MGMT");
        getEDM().enforceActiveAdminPermissionByContext(contextInfo, arrayList);
    }

    public final boolean isSystemStorage(String str) {
        return systemPlugin.contains(str) && isSystemApp(str);
    }

    public final boolean isSystemApp(String str) {
        Signature[] signatureArr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            PackageInfo packageInfo = this.mPm.getPackageInfo(str, 64);
            PackageInfo packageInfo2 = this.mContext.getPackageManager().getPackageInfo("android", 64);
            if (packageInfo != null && (signatureArr = packageInfo.signatures) != null) {
                if (compareSignatures(packageInfo2.signatures, signatureArr)) {
                    z = true;
                }
            }
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean validateSignature(String str, String str2, int i) {
        Log.i(TAG, "validateSignature : packageName-" + str + ", userId: " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        if (str != null) {
            try {
                if (str2 != null) {
                    try {
                        PackageInfo packageInfo = AppGlobals.getPackageManager().getPackageInfo(str, 64L, i);
                        if (packageInfo != null) {
                            Signature[] convertStringToSignature = convertStringToSignature(str2);
                            if (convertStringToSignature == null) {
                                Log.i(TAG, "validateSignature passed String signature is invalid");
                            }
                            if (compareSignatures(packageInfo.signatures, convertStringToSignature)) {
                                Log.i(TAG, "Package is installed, and signature matched...");
                                z = true;
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.DEVICE_LOCKED_CHANGED");
        this.mContext.registerReceiverAsUser(this.mSystemReceiver, UserHandle.ALL, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageRemovedReceiver, UserHandle.ALL, intentFilter2, null, null);
        this.mIsSystemReceiverRegistered = true;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        if (DBG) {
            Log.i(TAG, "onPreAdminRemoval - " + i);
        }
        Message obtainMessage = this.mUCSMHandler.obtainMessage(10);
        obtainMessage.arg1 = i;
        this.mUCSMHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        if (DBG) {
            Log.i(TAG, "onAdminRemoved - " + i);
        }
    }

    public class UCSMHandler extends Handler {
        public UCSMHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            r7 = false;
            boolean z2 = false;
            int i = 0;
            switch (message.what) {
                case 1:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_CLEAN_USER_INFO block started****");
                    int i2 = message.arg1;
                    Log.i(UniversalCredentialManagerService.TAG, "userId - " + i2);
                    IUcmService ucmService = UniversalCredentialManagerService.getUcmService();
                    if (ucmService != null) {
                        Log.i(UniversalCredentialManagerService.TAG, "notifyChangeToPlugin is called for user removed...");
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putInt("userId", i2);
                            ucmService.notifyChangeToPlugin((String) null, 11, bundle);
                            ucmService.removeEnforcedLockTypeNotification(i2);
                        } catch (Exception e) {
                            Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e.getMessage());
                        }
                    }
                    UniversalCredentialManagerService.this.performUserCleanup(i2);
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_CLEAN_USER_INFO block ended****");
                    break;
                case 2:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_ADMINS block started****");
                    try {
                        List allAdmins = UniversalCredentialManagerService.this.getAllAdmins();
                        if (UniversalCredentialManagerService.this.adminIds != null) {
                            UniversalCredentialManagerService.this.adminIds.addAll(allAdmins);
                            if (UniversalCredentialManagerService.this.adminIds.size() > 0) {
                                Log.i(UniversalCredentialManagerService.TAG, "adminIds size- " + UniversalCredentialManagerService.this.adminIds.size());
                            }
                        }
                    } catch (Exception e2) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e2.getMessage());
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_ADMINS block ended****");
                    UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(4));
                    break;
                case 3:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_CLEAN_INFO block started****");
                    try {
                        int[] iArr = (int[]) message.obj;
                        if (iArr != null && iArr.length > 0) {
                            for (int i3 : iArr) {
                                Integer valueOf = Integer.valueOf(i3);
                                int userId = UserHandle.getUserId(valueOf.intValue());
                                Log.i(UniversalCredentialManagerService.TAG, "uid - " + valueOf + ", userId-" + userId);
                                if (UniversalCredentialManagerService.this.adminIds.contains(valueOf)) {
                                    Log.i(UniversalCredentialManagerService.TAG, "UCS admin uninstall. Start cleaning....");
                                    UniversalCredentialManagerService.this.notifyAdminUninstall(valueOf.intValue());
                                    UniversalCredentialManagerService.this.performAdminCleanup(valueOf.intValue());
                                    UniversalCredentialManagerService.this.adminIds.remove(valueOf);
                                }
                                if (UniversalCredentialManagerService.this.activePluginsCache.containsKey(valueOf)) {
                                    String str = (String) UniversalCredentialManagerService.this.activePluginsCache.get(valueOf);
                                    Log.i(UniversalCredentialManagerService.TAG, "Active plugin is removed. Perform clean up for uid-" + valueOf + ", pluginPkg-" + str);
                                    UniversalCredentialManagerService.this.notifyPluginIsUninstalled(str);
                                    UniversalCredentialManagerService.this.activePluginsCache.remove(valueOf);
                                    UniversalCredentialManagerService.this.performStorageCleanup(str);
                                }
                                if (UniversalCredentialManagerService.this.whitelistedAppsCache.containsKey(valueOf)) {
                                    String str2 = (String) UniversalCredentialManagerService.this.whitelistedAppsCache.get(valueOf);
                                    Log.i(UniversalCredentialManagerService.TAG, "Calling performWhitelistAppCleanup for userId-" + userId + ", packageName-" + str2);
                                    UniversalCredentialManagerService.this.performWhitelistAppCleanup(userId, str2);
                                    UniversalCredentialManagerService.this.whitelistedAppsCache.remove(valueOf);
                                }
                                if (UniversalCredentialManagerService.this.exemptedAppsCache.containsKey(valueOf)) {
                                    String str3 = (String) UniversalCredentialManagerService.this.exemptedAppsCache.get(valueOf);
                                    Log.i(UniversalCredentialManagerService.TAG, "Calling performExemptedAppCleanup for userId-" + userId + ", packageName-" + str3);
                                    UniversalCredentialManagerService.this.performExemptedAppCleanup(userId, str3);
                                    UniversalCredentialManagerService.this.exemptedAppsCache.remove(valueOf);
                                }
                                IUcmService ucmService2 = UniversalCredentialManagerService.getUcmService();
                                if (ucmService2 != null) {
                                    Log.i(UniversalCredentialManagerService.TAG, "notifyChangeToPlugin is called for package uninstalled...");
                                    try {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putInt("userId", userId);
                                        bundle2.putInt("packageUid", valueOf.intValue());
                                        ucmService2.notifyChangeToPlugin((String) null, 12, bundle2);
                                    } catch (Exception e3) {
                                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e3.getMessage());
                                    }
                                }
                            }
                        }
                        Log.i(UniversalCredentialManagerService.TAG, "****MSG_CLEAN_INFO block ended****");
                        break;
                    } catch (Exception e4) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e4.getMessage());
                        return;
                    }
                    break;
                case 4:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_PLUGINS block started****");
                    try {
                        ArrayList activePlugin = UniversalCredentialManagerService.this.getActivePlugin();
                        if (!activePlugin.isEmpty()) {
                            Iterator it = activePlugin.iterator();
                            while (it.hasNext()) {
                                ContentValues contentValues = (ContentValues) it.next();
                                String asString = contentValues.getAsString("storagePackageName");
                                Integer asInteger = contentValues.getAsInteger("appUid");
                                if (asString != null && asInteger != null) {
                                    int intValue = asInteger.intValue();
                                    if (intValue != 1000 && intValue != 0) {
                                        try {
                                            if (!UniversalCredentialManagerService.this.activePluginsCache.containsKey(Integer.valueOf(intValue))) {
                                                UniversalCredentialManagerService.this.activePluginsCache.put(Integer.valueOf(intValue), asString);
                                                Log.i(UniversalCredentialManagerService.TAG, "Caching plugin app id-" + intValue + ", packageName-" + asString);
                                            }
                                        } catch (Exception e5) {
                                            Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e5.getMessage());
                                        }
                                    }
                                }
                                Log.i(UniversalCredentialManagerService.TAG, "parsing error, continue...");
                            }
                            for (Map.Entry entry : UniversalCredentialManagerService.this.activePluginsCache.entrySet()) {
                                Log.i(UniversalCredentialManagerService.TAG, "Plugin ID = " + ((Integer) entry.getKey()) + ", Plugin package = " + ((String) entry.getValue()));
                            }
                        } else {
                            Log.i(UniversalCredentialManagerService.TAG, "No active plugin found");
                        }
                    } catch (Exception e6) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e6.getMessage());
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_PLUGINS block ended****");
                    UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(5));
                    break;
                case 5:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_WHITELIST_AND_EXEMPT_APPS block started****");
                    try {
                        ArrayList allWhitelistedApps = UniversalCredentialManagerService.this.getAllWhitelistedApps();
                        if (allWhitelistedApps != null && allWhitelistedApps.size() > 0) {
                            Log.i(UniversalCredentialManagerService.TAG, "getAllWhitelistedApps - Size-" + allWhitelistedApps.size());
                            Iterator it2 = allWhitelistedApps.iterator();
                            while (it2.hasNext()) {
                                ContentValues contentValues2 = (ContentValues) it2.next();
                                String asString2 = contentValues2.getAsString("appPackage");
                                Integer asInteger2 = contentValues2.getAsInteger("appUid");
                                if (asString2 != null && asInteger2 != null) {
                                    int intValue2 = asInteger2.intValue();
                                    try {
                                        if (!asString2.equals("*") && intValue2 != 1000 && intValue2 != 0 && !UniversalCredentialManagerService.this.whitelistedAppsCache.containsKey(Integer.valueOf(intValue2))) {
                                            UniversalCredentialManagerService.this.whitelistedAppsCache.put(Integer.valueOf(intValue2), asString2);
                                            Log.i(UniversalCredentialManagerService.TAG, "Caching WhiteList app id-" + intValue2 + ", packageName-" + asString2);
                                        }
                                    } catch (Exception e7) {
                                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e7.getMessage());
                                    }
                                }
                                Log.i(UniversalCredentialManagerService.TAG, "parsing error, continue...");
                            }
                            for (Map.Entry entry2 : UniversalCredentialManagerService.this.whitelistedAppsCache.entrySet()) {
                                Log.i(UniversalCredentialManagerService.TAG, "WHITELIST App UID = " + ((Integer) entry2.getKey()) + ", App package = " + ((String) entry2.getValue()));
                            }
                        } else {
                            Log.i(UniversalCredentialManagerService.TAG, "getAllWhitelistedApps DB is empty...");
                        }
                        ArrayList allExemptedApps = UniversalCredentialManagerService.this.getAllExemptedApps();
                        if (allExemptedApps != null && allExemptedApps.size() > 0) {
                            Log.i(UniversalCredentialManagerService.TAG, "getAllExemptedApps - Size-" + allExemptedApps.size());
                            Iterator it3 = allExemptedApps.iterator();
                            while (it3.hasNext()) {
                                ContentValues contentValues3 = (ContentValues) it3.next();
                                if (contentValues3 == null) {
                                    Log.i(UniversalCredentialManagerService.TAG, "value is null, continue...");
                                } else {
                                    String asString3 = contentValues3.getAsString("appPackage");
                                    Integer asInteger3 = contentValues3.getAsInteger("appUid");
                                    if (asString3 != null && asInteger3 != null) {
                                        int intValue3 = asInteger3.intValue();
                                        try {
                                            if (!asString3.equals("com.samsung.knox.virtual.wifi") && intValue3 != 1000 && intValue3 != 0 && !UniversalCredentialManagerService.this.exemptedAppsCache.containsKey(Integer.valueOf(intValue3))) {
                                                UniversalCredentialManagerService.this.exemptedAppsCache.put(Integer.valueOf(intValue3), asString3);
                                                Log.i(UniversalCredentialManagerService.TAG, "Caching Exempted app id-" + intValue3 + ", packageName-" + asString3);
                                            }
                                        } catch (Exception e8) {
                                            Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e8.getMessage());
                                        }
                                    }
                                    Log.i(UniversalCredentialManagerService.TAG, "parsing error, continue...");
                                }
                            }
                            for (Map.Entry entry3 : UniversalCredentialManagerService.this.exemptedAppsCache.entrySet()) {
                                Log.i(UniversalCredentialManagerService.TAG, "EXEPMT-> App UID = " + ((Integer) entry3.getKey()) + ", App package = " + ((String) entry3.getValue()));
                            }
                        } else {
                            Log.i(UniversalCredentialManagerService.TAG, "getAllExemptedApps DB is empty...");
                        }
                    } catch (Exception e9) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e9.getMessage());
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOAD_WHITELIST_AND_EXEMPT_APPS block ended****");
                    break;
                case 6:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_SYNC_UP_DATA block started****");
                    try {
                        Bundle bundle3 = new Bundle();
                        IUcmService ucmService3 = UniversalCredentialManagerService.getUcmService();
                        if (ucmService3 != null) {
                            ucmService3.notifyChangeToPlugin((String) null, 17, bundle3);
                            List<Integer> allUsers = UniversalCredentialManagerService.this.getAllUsers();
                            if (allUsers != null && allUsers.size() > 0) {
                                Iterator it4 = ((UserManager) UniversalCredentialManagerService.this.mContext.getSystemService("user")).getUsers().iterator();
                                while (it4.hasNext()) {
                                    int i4 = ((UserInfo) it4.next()).id;
                                    Log.i(UniversalCredentialManagerService.TAG, "Valid userid-" + i4);
                                    if (allUsers.contains(Integer.valueOf(i4))) {
                                        Log.i(UniversalCredentialManagerService.TAG, "Found userid on cache-" + i4);
                                        allUsers.remove(Integer.valueOf(i4));
                                    }
                                }
                                for (Integer num : allUsers) {
                                    Log.i(UniversalCredentialManagerService.TAG, "InValid userid-" + num);
                                    Message obtainMessage = UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(1);
                                    obtainMessage.arg1 = num.intValue();
                                    UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(obtainMessage);
                                }
                            }
                        }
                    } catch (Exception e10) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e10.getMessage());
                    }
                    ArrayList<Integer> arrayList = new ArrayList();
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    try {
                        for (Integer num2 : UniversalCredentialManagerService.this.adminIds) {
                            Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA adminId-" + num2);
                            if (UniversalCredentialManagerService.this.mPm.getPackagesForUid(num2.intValue()) == null) {
                                if (!arrayList.contains(num2)) {
                                    Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA remove adminid : " + num2);
                                    arrayList.add(num2);
                                }
                            } else {
                                if (packageManager.checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT", num2.intValue()) != 0 && packageManager.checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", num2.intValue()) != 0 && packageManager.checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_MGMT", num2.intValue()) != 0) {
                                    Log.i(UniversalCredentialManagerService.TAG, "  Admin don't has UCS permission...");
                                    UniversalCredentialManagerService.this.processAdminLicenseExpiry(num2.intValue());
                                }
                                Log.i(UniversalCredentialManagerService.TAG, "  Admin has valid permission. Processing further...");
                            }
                        }
                    } catch (Exception e11) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e11.getMessage());
                    }
                    try {
                        for (Map.Entry entry4 : UniversalCredentialManagerService.this.activePluginsCache.entrySet()) {
                            Integer num3 = (Integer) entry4.getKey();
                            String str4 = (String) entry4.getValue();
                            Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA plugin id -" + num3);
                            Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA plugin package -" + str4);
                            if (UniversalCredentialManagerService.this.mPm.getPackagesForUid(num3.intValue()) == null && !arrayList.contains(num3)) {
                                Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA remove plugin : " + num3);
                                arrayList.add(num3);
                            }
                        }
                    } catch (Exception e12) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e12.getMessage());
                    }
                    try {
                        Iterator it5 = UniversalCredentialManagerService.this.exemptedAppsCache.entrySet().iterator();
                        while (it5.hasNext()) {
                            Integer num4 = (Integer) ((Map.Entry) it5.next()).getKey();
                            Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA exempt app id -" + num4);
                            if (UniversalCredentialManagerService.this.mPm.getPackagesForUid(num4.intValue()) == null && !arrayList.contains(num4)) {
                                Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA remove exempt app : " + num4);
                                arrayList.add(num4);
                            }
                        }
                    } catch (Exception e13) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e13.getMessage());
                    }
                    try {
                        Iterator it6 = UniversalCredentialManagerService.this.whitelistedAppsCache.entrySet().iterator();
                        while (it6.hasNext()) {
                            Integer num5 = (Integer) ((Map.Entry) it6.next()).getKey();
                            Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA Whitelist app id -" + num5);
                            if (UniversalCredentialManagerService.this.mPm.getPackagesForUid(num5.intValue()) == null && !arrayList.contains(num5)) {
                                Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA remove Whitelist app : " + num5);
                                arrayList.add(num5);
                            }
                        }
                    } catch (Exception e14) {
                        Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e14.getMessage());
                    }
                    if (arrayList.size() > 0) {
                        int[] iArr2 = new int[arrayList.size()];
                        for (Integer num6 : arrayList) {
                            if (num6 == null) {
                                Log.i(UniversalCredentialManagerService.TAG, "id is null, continue...");
                            } else {
                                Log.i(UniversalCredentialManagerService.TAG, "adding clean app id-" + num6);
                                iArr2[i] = num6.intValue();
                                i++;
                            }
                        }
                        Message obtainMessage2 = UniversalCredentialManagerService.this.mUCSMHandler.obtainMessage(3);
                        obtainMessage2.obj = iArr2;
                        Log.i(UniversalCredentialManagerService.TAG, "MSG_SYNC_UP_DATA calling MSG_CLEAN_INFO...");
                        UniversalCredentialManagerService.this.mUCSMHandler.sendMessage(obtainMessage2);
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_SYNC_UP_DATA block ended****");
                    break;
                case 7:
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOCK_STATUS_UPDATE block started****");
                    IUcmService ucmService4 = UniversalCredentialManagerService.getUcmService();
                    if (ucmService4 != null) {
                        Log.i(UniversalCredentialManagerService.TAG, "notifyChangeToPlugin is called for Lock status update...");
                        boolean isKeyguardLocked = ((KeyguardManager) UniversalCredentialManagerService.this.mContext.getSystemService("keyguard")).isKeyguardLocked();
                        try {
                            Bundle bundle4 = new Bundle();
                            bundle4.putInt("userId", 0);
                            if (isKeyguardLocked) {
                                ucmService4.notifyChangeToPlugin((String) null, 15, bundle4);
                            } else {
                                ucmService4.notifyChangeToPlugin((String) null, 16, bundle4);
                            }
                        } catch (Exception e15) {
                            Log.i(UniversalCredentialManagerService.TAG, "notifyChangeToPlugin Exception " + e15.getMessage());
                        }
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "****MSG_LOCK_STATUS_UPDATE block ended****");
                    break;
                case 9:
                    IUcmService ucmService5 = UniversalCredentialManagerService.getUcmService();
                    if (ucmService5 != null) {
                        int i5 = message.arg1;
                        int i6 = message.arg2;
                        Log.i(UniversalCredentialManagerService.TAG, "notifyChangeToPlugin is called for container Lock status update containerId-" + i5 + ", status-" + i6);
                        try {
                            Bundle bundle5 = new Bundle();
                            bundle5.putInt("userId", i5);
                            if (i6 != 1) {
                                ucmService5.notifyChangeToPlugin((String) null, 20, bundle5);
                            } else {
                                ucmService5.notifyChangeToPlugin((String) null, 21, bundle5);
                            }
                            break;
                        } catch (Exception e16) {
                            Log.i(UniversalCredentialManagerService.TAG, "The exception occurs " + e16.getMessage());
                            return;
                        }
                    }
                    break;
                case 10:
                    String[] strArr = {"adminUid"};
                    String[] strArr2 = {String.valueOf(message.arg1)};
                    try {
                        z = UniversalCredentialManagerService.this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
                    } catch (Exception e17) {
                        if (UniversalCredentialManagerService.DBG) {
                            Log.i(UniversalCredentialManagerService.TAG, "performPreAdminCleanup - Exception delete locktype" + e17.getMessage());
                        }
                        z = false;
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "performPreAdminCleanup - Enforce Lock Type status- " + z);
                    try {
                        z2 = UniversalCredentialManagerService.this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2);
                    } catch (Exception e18) {
                        if (UniversalCredentialManagerService.DBG) {
                            Log.i(UniversalCredentialManagerService.TAG, "performPreAdminCleanup - Exception delete whitelist" + e18.getMessage());
                        }
                    }
                    Log.i(UniversalCredentialManagerService.TAG, "performPreAdminCleanup - White List status - " + z2);
                    break;
            }
        }
    }

    public final void notifyPluginIsUninstalled(String str) {
        List<Integer> adminIdRelatedToStorage = getAdminIdRelatedToStorage(str);
        if (adminIdRelatedToStorage == null || adminIdRelatedToStorage.size() == 0) {
            Log.i(TAG, "No admin found related to package : " + str);
            return;
        }
        for (Integer num : adminIdRelatedToStorage) {
            Log.i(TAG, "notifyPluginIsUninstalled to " + num);
            String[] packagesForUid = this.mPm.getPackagesForUid(num.intValue());
            if (packagesForUid == null) {
                Log.i(TAG, "cannot find admin package name of uid : " + num);
            } else {
                for (String str2 : packagesForUid) {
                    if (str2 == null) {
                        Log.i(TAG, "adminPkg is null, so continue...");
                    } else {
                        Log.i(TAG, "Sending event update to package " + str2);
                        Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_NOTIFY_EVENT");
                        intent.setPackage(str2);
                        Bundle bundle = new Bundle();
                        bundle.putInt("event_id", 1);
                        bundle.putString("package_name", str);
                        intent.putExtras(bundle);
                        IPackageManager packageManager = AppGlobals.getPackageManager();
                        try {
                            if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", str, UserHandle.getUserId(num.intValue())) == 0) {
                                this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num.intValue())), "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
                            } else if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_MGMT", str, UserHandle.getUserId(num.intValue())) == 0) {
                                this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num.intValue())), "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
                            } else {
                                Log.i(TAG, "admin does not have proper UCM permission");
                            }
                            Log.i(TAG, "notifyPluginIsUninstalled done");
                        } catch (Exception e) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static int checkCallerPermissionFor(String str) {
        Log.i(TAG, "checkCallerPermissionFor is called for method-" + str);
        if (ServiceKeeper.isAuthorized(sContext, Binder.getCallingPid(), Binder.getCallingUid(), "UniversalCredentialManagerService", str) == 0) {
            return 0;
        }
        SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [UniversalCredentialManagerService] service");
        if (DBG) {
            Log.i(TAG, "The exception occurs " + securityException.getMessage());
            throw securityException;
        }
        throw securityException;
    }

    public class RequestIdGenerator {
        public int fraction = 0;
        public Random random = new Random();

        public int getNextContainerRequestId() {
            int i = this.fraction + 1;
            this.fraction = i;
            if (i > 10) {
                this.fraction = 1;
            }
            return this.fraction * randInt(10000, 100000);
        }

        public int randInt(int i, int i2) {
            return this.random.nextInt((i2 - i) + 1) + i;
        }
    }

    public CredentialStorage[] getAvailableCredentialStorages(ContextInfo contextInfo) {
        boolean z;
        Log.i(TAG, "getAvailableCredentialStorages is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        int i3 = 107;
        if (isCallerDelegated(i2, i, null, 107)) {
            Log.i(TAG, "getAvailableCredentialStorages caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, null);
            i = contextInfo.mCallerUid;
            z = false;
        }
        if (DBG) {
            Log.i(TAG, "getAvailableCredentialStorages is called for Caller UID-" + i + " mContainerId " + i2);
        }
        CredentialStorage[] availableCredentialStorageInternal = getAvailableCredentialStorageInternal();
        if (availableCredentialStorageInternal == null) {
            if (DBG) {
                Log.i(TAG, "getAvailableCredentialStorageInternal return null");
            }
            return null;
        }
        if (!z) {
            return availableCredentialStorageInternal;
        }
        Log.i(TAG, "getAvailableCredentialStorages - Delegated caller..");
        String[] packagesForUid = this.mPm.getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length > 0) {
            ArrayList arrayList = new ArrayList();
            int length = packagesForUid.length;
            int i4 = 0;
            while (i4 < length) {
                CredentialStorage[] delegatedStorages = getDelegatedStorages(i2, i3, packagesForUid[i4]);
                if (delegatedStorages != null && delegatedStorages.length > 0) {
                    for (CredentialStorage credentialStorage : delegatedStorages) {
                        if (!arrayList.contains(credentialStorage.name)) {
                            arrayList.add(credentialStorage.name);
                            Log.i(TAG, "getAvailableCredentialStorages - Addding whitelisted storage -" + credentialStorage.name);
                        }
                    }
                }
                i4++;
                i3 = 107;
            }
            ArrayList arrayList2 = new ArrayList();
            for (CredentialStorage credentialStorage2 : availableCredentialStorageInternal) {
                if (arrayList.contains(credentialStorage2.name)) {
                    arrayList2.add(credentialStorage2);
                    Log.i(TAG, "getAvailableCredentialStorages - Final filtered storage -" + credentialStorage2.name);
                }
            }
            if (arrayList2.size() > 0) {
                return (CredentialStorage[]) arrayList2.toArray(new CredentialStorage[arrayList2.size()]);
            }
            Log.i(TAG, "returning null");
        }
        return null;
    }

    public final Provider[] getManagedProviders() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IUcmService ucmService = getUcmService();
                if (ucmService != null) {
                    Bundle[] listAllProviders = ucmService.listAllProviders();
                    if (listAllProviders != null && listAllProviders.length != 0) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : listAllProviders) {
                            getUniversalCredentialUtil();
                            String string = bundle.getString("uniqueId");
                            if (string == null) {
                                Log.i(TAG, "NULL agent ID name Returned for bundle");
                            } else {
                                arrayList.add(new UcmKeystoreProvider(string, bundle));
                            }
                        }
                        return (Provider[]) arrayList.toArray(new Provider[arrayList.size()]);
                    }
                    if (DBG) {
                        Log.i(TAG, "Provider list is empty");
                    }
                    return null;
                }
            } catch (Exception e) {
                Log.w(TAG, "The exception occurs " + e.getMessage());
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final CredentialStorage[] getAvailableCredentialStorageInternal() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getUniversalCredentialUtil() != null) {
                    Provider[] managedProviders = getManagedProviders();
                    if (managedProviders != null && managedProviders.length > 0) {
                        CredentialStorage[] credentialStorageArr = new CredentialStorage[managedProviders.length];
                        int i = 0;
                        for (Provider provider : managedProviders) {
                            CredentialStorage credentialStorage = new CredentialStorage();
                            credentialStorage.name = provider.getName();
                            credentialStorage.packageName = provider.getProperty("packageName");
                            credentialStorage.manufacturer = provider.getProperty("vendorId");
                            PackageInfo packageInfo = this.mPm.getPackageInfo(credentialStorage.packageName, 64);
                            if (packageInfo != null) {
                                credentialStorage.signature = convertSignatureToString(packageInfo.signatures);
                            }
                            credentialStorageArr[i] = credentialStorage;
                            i++;
                        }
                        return credentialStorageArr;
                    }
                    Log.i(TAG, "getAvailableCredentialStorage - UniversalCredentialUtil service returns no providers... ");
                } else {
                    Log.i(TAG, "getAvailableCredentialStorage - UniversalCredentialUtil service is null.... ");
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPluginActive(CredentialStorage credentialStorage) {
        if (getCredentialStorageProvider(credentialStorage) == null) {
            return false;
        }
        Log.i(TAG, "Plugin is active...");
        return true;
    }

    public final Provider getCredentialStorageProvider(CredentialStorage credentialStorage) {
        try {
            if (getUniversalCredentialUtil() != null) {
                Log.i(TAG, "getCredentialStorageProperties name-" + credentialStorage.name + " and pkg-" + credentialStorage.packageName);
                Provider[] managedProviders = getManagedProviders();
                if (managedProviders == null || managedProviders.length <= 0) {
                    return null;
                }
                for (Provider provider : managedProviders) {
                    String name = provider.getName();
                    String property = provider.getProperty("packageName");
                    if (name != null && property != null) {
                        if (name.equals(credentialStorage.name) && property.equals(credentialStorage.packageName)) {
                            Log.i(TAG, "getCredentialStorageProperties match found...");
                            return provider;
                        }
                    }
                    Log.i(TAG, "CredentialStorage or AGENT_PACKAGENAME is null");
                }
                return null;
            }
            Log.i(TAG, "getCredentialStorageProperties - UniversalCredentialUtil service is null.... ");
            return null;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return null;
        }
    }

    public int configureCredentialStoragePlugin(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        Log.i(TAG, "configureCredentialStoragePlugin is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "configureCredentialStoragePlugin - Invalid Arguments");
            return -11;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (DBG) {
                Log.i(TAG, "configureCredentialStoragePlugin is called for Caller UID-" + i + " mContainerId " + i2);
            }
            if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "configureCredentialStoragePlugin return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                if (credentialStorage.name.equalsIgnoreCase("com.samsung.ucs.agent.ese:eSE Credential Storage") && credentialStorage.packageName.equalsIgnoreCase("com.samsung.ucs.agent.ese")) {
                    Log.i(TAG, "Adding install flag for ESE applet");
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    bundle.putInt("installAppletUsingLCCM", 1);
                }
                Log.i(TAG, "configureCredentialStoragePlugin - pass to agent...");
                String build = new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i).build();
                int nextContainerRequestId = this.mRIdGenerator.getNextContainerRequestId();
                Bundle adminConfigureBundleForCs = ucmService.setAdminConfigureBundleForCs(i, i2, build, bundle, nextContainerRequestId);
                int i3 = adminConfigureBundleForCs != null ? adminConfigureBundleForCs.getInt("intresponse", -1) : -1;
                int i4 = adminConfigureBundleForCs != null ? adminConfigureBundleForCs.getInt("errorresponse", -1) : -1;
                Log.i(TAG, "configureCredentialStoragePlugin - requestId -" + nextContainerRequestId + " and retCode-" + i3);
                return i3 == 0 ? nextContainerRequestId : i4;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle getCredentialStoragePluginConfiguration(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str;
        Log.i(TAG, "getCredentialStoragePluginConfiguration is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getCredentialStoragePluginConfiguration - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(getKAData("getCredentialStoragePluginConfiguration", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
        }
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return null;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return null;
        }
        if (DBG) {
            Log.i(TAG, "getCredentialStoragePluginConfiguration is called for Caller UID-" + i + " mContainerId " + i2);
        }
        if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName)) {
            if (DBG) {
                Log.i(TAG, "getCredentialStoragePluginConfiguration return null");
            }
            return null;
        }
        IUcmService ucmService = getUcmService();
        if (ucmService != null) {
            Log.i(TAG, "getCredentialStoragePluginConfiguration - pass to agent...");
            return ucmService.getAdminConfigureBundleFromCs(i, i2, new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i).build());
        }
        return null;
    }

    public int manageCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        Log.i(TAG, "manageCredentialStorage is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "manageCredentialStorage - Invalid Arguments");
            return -11;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (DBG) {
            Log.i(TAG, "manageCredentialStorage is called for Caller UID-" + i + " mContainerId " + i2 + ", enable- " + z);
        }
        return configureCredentialStorageInternal(i, i2, credentialStorage, z);
    }

    public final int configureCredentialStorageInternal(int i, int i2, CredentialStorage credentialStorage, boolean z) {
        String str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (z) {
                if (!isAllowed(i, i2)) {
                    return -1;
                }
                int isValidCredentialStorage = isValidCredentialStorage(credentialStorage, i);
                if (isValidCredentialStorage != 0) {
                    return isValidCredentialStorage;
                }
            } else {
                if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                    if (DBG) {
                        Log.i(TAG, "configureCredentialStorageInternal return false..");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -12;
                }
                IUcmService ucmService = getUcmService();
                if (ucmService != null) {
                    String keyguardStorageForCurrentUser = ucmService.getKeyguardStorageForCurrentUser(i2);
                    Log.i(TAG, "configureCredentialStorageInternal keyguardCSName -" + keyguardStorageForCurrentUser + " and CS name -" + credentialStorage.name);
                    if (keyguardStorageForCurrentUser != null && keyguardStorageForCurrentUser.length() > 0 && credentialStorage.name.equalsIgnoreCase(keyguardStorageForCurrentUser)) {
                        Log.i(TAG, "configureCredentialStorageInternal : Keyguard is setup with CS. Can't unmanaged it.");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -26;
                    }
                }
            }
            if (addOrUpdateSecureStorageConfig(i, i2, credentialStorage, z)) {
                if (!this.mIsSystemReceiverRegistered) {
                    registerReceiver();
                }
                return 0;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAllowed(int i, int i2) {
        Log.i(TAG, "isAllowed: adminUid - " + i + ", userId-" + i2);
        boolean z = true;
        try {
        } catch (Exception e) {
            e = e;
            z = false;
        }
        if (i2 >= 10) {
            int userId = UserHandle.getUserId(i);
            Log.i(TAG, "isAllowed: callerUserId - " + userId);
            try {
            } catch (Exception e2) {
                e = e2;
                Log.i(TAG, "The exception occurs " + e.getMessage());
                Log.i(TAG, "isAllowed status-" + z);
                return z;
            }
            if (userId == i2) {
                Log.i(TAG, "isAllowed: Caller is inside container. match found....");
            } else {
                Log.i(TAG, "isAllowed: Check if caller is owner of container");
                int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i2);
                Log.i(TAG, "isAllowed container ownerUid - " + mUMContainerOwnerUid);
                if (mUMContainerOwnerUid == i) {
                    Log.i(TAG, "isAllowed: match found....");
                } else {
                    Log.i(TAG, "isAllowed: no match found....");
                    z = false;
                    Log.i(TAG, "isAllowed status-" + z);
                    return z;
                }
            }
            Log.i(TAG, "isAllowed status-" + z);
            return z;
        }
        if (UserHandle.getUserId(i) != 0) {
            Log.i(TAG, "isAllowed: caller app is not in user 0");
            z = false;
            Log.i(TAG, "isAllowed status-" + z);
            return z;
        }
        Log.i(TAG, "isAllowed status-" + z);
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0177 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0189 A[Catch: Exception -> 0x020b, LOOP:2: B:59:0x0184->B:61:0x0189, LOOP_END, TryCatch #1 {Exception -> 0x020b, blocks: (B:3:0x000a, B:5:0x000e, B:6:0x0038, B:8:0x003e, B:10:0x0063, B:12:0x006d, B:15:0x0086, B:17:0x008c, B:19:0x009a, B:21:0x009f, B:23:0x00b7, B:25:0x00ba, B:27:0x00be, B:29:0x00c8, B:34:0x00ce, B:36:0x00d8, B:38:0x00de, B:40:0x00e2, B:42:0x00e8, B:44:0x0105, B:45:0x011d, B:47:0x0129, B:48:0x0130, B:51:0x0173, B:54:0x0179, B:56:0x017d, B:58:0x0180, B:59:0x0184, B:61:0x0189, B:63:0x0194, B:66:0x01b4, B:69:0x01bc, B:71:0x01c4, B:78:0x0140, B:75:0x015a, B:80:0x01cc, B:50:0x0134), top: B:2:0x000a, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0194 A[EDGE_INSN: B:62:0x0194->B:63:0x0194 BREAK  A[LOOP:2: B:59:0x0184->B:61:0x0189], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b2 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int isValidCredentialStorage(CredentialStorage credentialStorage, int i) {
        boolean z;
        String str;
        String str2;
        PackageInfo packageInfo;
        Signature[] signatureArr;
        String[] strArr;
        int i2;
        Signature[] signatureArr2;
        UniversalCredentialManagerService universalCredentialManagerService = this;
        try {
            if (DBG) {
                Log.i(TAG, "UniversalCredentialManagerService.isValidCredentialStorage  cs.name-" + credentialStorage.name + ",cs.packageName-" + credentialStorage.packageName + "and callerId-" + i);
            }
            Provider credentialStorageProvider = getCredentialStorageProvider(credentialStorage);
            if (credentialStorageProvider != null) {
                String property = credentialStorageProvider.getProperty("isManageable");
                Log.i(TAG, "isValidCredentialStorage isManageable-" + property);
                if (!"true".equals(property)) {
                    return -22;
                }
                String property2 = credentialStorageProvider.getProperty("configuratorList");
                if (DBG) {
                    Log.i(TAG, "isValidCredentialStorage configuratorList-" + property2);
                }
                int i3 = 0;
                if (property2 == null || property2.length() <= 0) {
                    return 0;
                }
                String[] split = property2.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                String[] packagesForUid = universalCredentialManagerService.mPm.getPackagesForUid(i);
                if (packagesForUid != null) {
                    int length = packagesForUid.length;
                    int i4 = 0;
                    z = false;
                    while (i4 < length) {
                        String str3 = packagesForUid[i4];
                        Log.i(TAG, "isValidCredentialStorage callerPkg-" + str3);
                        if (split != null && split.length > 0) {
                            int length2 = split.length;
                            int i5 = i3;
                            while (i5 < length2) {
                                String[] split2 = split[i5].split("@");
                                if (split2 != null && (str = split2[i3]) != null && str.trim().contains(str3)) {
                                    if (split2.length == 2 && (str2 = split2[1]) != null && str2.length() > 0) {
                                        Log.i(TAG, "isValidCredentialStorage callerPkg-" + str3 + " match found. Now Matching signature...");
                                        if (DBG) {
                                            Log.i(TAG, "isValidCredentialStorage - config sign-" + split2[1]);
                                        }
                                        Signature[] convertStringToSignature = universalCredentialManagerService.convertStringToSignature(split2[1].trim());
                                        if (convertStringToSignature == null) {
                                            Log.i(TAG, "isValidCredentialStorage - failed to convert signature from db.");
                                        }
                                        try {
                                            packageInfo = AppGlobals.getPackageManager().getPackageInfo(str3, 64L, UserHandle.getUserId(i));
                                        } catch (RemoteException e) {
                                            Log.i(TAG, "The exception occurs " + e.getMessage());
                                            packageInfo = null;
                                            if (DBG) {
                                                strArr = new String[signatureArr.length];
                                                i2 = 0;
                                                while (true) {
                                                    signatureArr2 = packageInfo.signatures;
                                                    if (i2 < signatureArr2.length) {
                                                    }
                                                    strArr[i2] = signatureArr2[i2].toCharsString();
                                                    i2++;
                                                }
                                                String join = TextUtils.join(",", strArr);
                                                Log.i(TAG, "isValidCredentialStorage caller sign-" + join);
                                            }
                                            if (convertStringToSignature == null) {
                                            }
                                            Log.i(TAG, "isValidCredentialStorage signature didn't matched");
                                            i5++;
                                            universalCredentialManagerService = this;
                                            i3 = 0;
                                        } catch (Exception e2) {
                                            Log.i(TAG, "The exception occurs " + e2.getMessage());
                                            packageInfo = null;
                                            if (DBG) {
                                            }
                                            if (convertStringToSignature == null) {
                                            }
                                            Log.i(TAG, "isValidCredentialStorage signature didn't matched");
                                            i5++;
                                            universalCredentialManagerService = this;
                                            i3 = 0;
                                        }
                                        if (DBG && packageInfo != null && (signatureArr = packageInfo.signatures) != null && signatureArr.length > 0) {
                                            strArr = new String[signatureArr.length];
                                            i2 = 0;
                                            while (true) {
                                                signatureArr2 = packageInfo.signatures;
                                                if (i2 < signatureArr2.length) {
                                                    break;
                                                }
                                                strArr[i2] = signatureArr2[i2].toCharsString();
                                                i2++;
                                            }
                                            String join2 = TextUtils.join(",", strArr);
                                            Log.i(TAG, "isValidCredentialStorage caller sign-" + join2);
                                        }
                                        if (convertStringToSignature == null && packageInfo != null && compareSignatures(packageInfo.signatures, convertStringToSignature)) {
                                            Log.i(TAG, "isValidCredentialStorage signature match found");
                                        } else {
                                            Log.i(TAG, "isValidCredentialStorage signature didn't matched");
                                        }
                                    } else {
                                        Log.i(TAG, "isValidCredentialStorage callerPkg-" + str3 + " is allowed to configure storage...");
                                    }
                                    z = true;
                                    break;
                                }
                                i5++;
                                universalCredentialManagerService = this;
                                i3 = 0;
                            }
                        }
                        if (z) {
                            break;
                        }
                        i4++;
                        universalCredentialManagerService = this;
                        i3 = 0;
                    }
                } else {
                    z = false;
                }
                return z ? 0 : -23;
            }
        } catch (Exception e3) {
            Log.i(TAG, "The exception occurs " + e3.getMessage());
        }
        return -13;
    }

    public boolean isCredentialStorageManaged(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "isCredentialStorageManaged is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "isCredentialStorageManaged - Invalid Arguments");
            }
            return false;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (DBG) {
            Log.i(TAG, "isCredentialStorageManaged is called for Caller UID-" + i + " mContainerId " + i2);
        }
        String str = credentialStorage.signature;
        if (str == null || validateSignature(credentialStorage.packageName, str, 0)) {
            return isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName);
        }
        return false;
    }

    public int lockCredentialStorage(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        String str;
        Log.i(TAG, "lockCredentialStorage is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "lockCredentialStorage - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("lockCredentialStorage", credentialStorage.packageName);
            kAData.setProperty("enable", z);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "lockCredentialStorage is called for Caller UID-" + i + " mContainerId " + i2);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "lockCredentialStorage return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            if (lockCredentialStorageInternal(i, i2, credentialStorage, z)) {
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean lockCredentialStorageInternal(int i, int i2, CredentialStorage credentialStorage, boolean z) {
        boolean z2 = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("status", Integer.valueOf(z ? 1 : 0));
            z2 = this.mEdmStorageProvider.putValues("UniversalCredentialInfoTable", contentValues2, contentValues);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "lockCredentialStorageInternal - Exception lockCredentialStorageInternal" + e.getMessage());
            }
        }
        Log.i(TAG, "lockCredentialStorageInternal retcode-" + z2);
        return z2;
    }

    public boolean isCredentialStorageLocked(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "isCredentialStorageLocked is called....");
        validateContextInfo(contextInfo);
        boolean z = false;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "isCredentialStorageLocked - Invalid Arguments");
            }
            return false;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mContainerId;
        String str = credentialStorage.signature;
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return false;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return true;
        }
        if (DBG) {
            Log.i(TAG, "isCredentialStorageLocked is called for Caller UID-" + contextInfo.mCallerUid + " mContainerId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = isCredentialStorageLockedAsUser(i, credentialStorage);
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCredentialStorageLockedAsUser(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageLockedAsUser");
        Log.i(TAG, "isCredentialStorageLockedAsUser is called....");
        boolean z = false;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "isCredentialStorageLockedAsUser - Invalid Arguments");
            }
            return false;
        }
        try {
            KnoxAnalytics.log(getKAData("isCredentialStorageLocked", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        if (DBG) {
            Log.i(TAG, "isCredentialStorageLockedAsUser is called for userId-" + i);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            contentValues.put("status", (Integer) 1);
            if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                z = true;
            }
        } catch (Exception e2) {
            if (DBG) {
                Log.i(TAG, "isCredentialStorageLockedAsUser - Exception" + e2.getMessage());
            }
        }
        if (DBG) {
            Log.i(TAG, "isCredentialStorageLockedAsUser - isLocked : " + z);
        }
        return z;
    }

    public final boolean isValidAuthType(int i) {
        boolean z = true;
        if (i != 100 && i != 105) {
            z = false;
        }
        Log.i(TAG, "isValidAuthType type-" + i + " and status-" + z);
        return z;
    }

    public final boolean addOrUpdateSecureStorageConfig(int i, int i2, CredentialStorage credentialStorage, boolean z) {
        boolean z2;
        if (DBG) {
            Log.i(TAG, "addOrUpdateSecureStorageConfig is called...");
        }
        if (DBG) {
            Log.i(TAG, "addOrUpdateSecureStorageConfig adminUid - " + i + " ContainerId - " + i2 + ", Storage Name- " + credentialStorage.name + ", Storage Package name - " + credentialStorage.packageName);
        }
        boolean z3 = false;
        if (!z) {
            if (isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                Log.i(TAG, "addOrUpdateSecureStorageConfig - Removing Credential Storage for Admin");
                try {
                    z3 = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialInfoTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName});
                    if (z3) {
                        notifyToPlugin(10, i, i2, credentialStorage);
                        performCredentialStorageCleanup(i, i2, credentialStorage);
                    }
                } catch (Exception e) {
                    if (DBG) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                    }
                }
            }
        } else {
            if (DBG) {
                Log.i(TAG, "addOrUpdateSecureStorageConfig - enabling CS...");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            try {
                int packageUid = this.mPm.getPackageUid(credentialStorage.packageName, 0);
                contentValues.put("appUid", Integer.valueOf(packageUid));
                if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                    ContentValues contentValues2 = new ContentValues();
                    String str = credentialStorage.manufacturer;
                    if (str != null && str.length() > 0) {
                        contentValues2.put("storageManufacture", credentialStorage.manufacturer);
                    }
                    z2 = this.mEdmStorageProvider.putValues("UniversalCredentialInfoTable", contentValues2, contentValues);
                } else {
                    String str2 = credentialStorage.manufacturer;
                    if (str2 != null && str2.length() > 0) {
                        contentValues.put("storageManufacture", credentialStorage.manufacturer);
                    }
                    boolean putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("UniversalCredentialInfoTable", contentValues);
                    if (!this.adminIds.contains(Integer.valueOf(i))) {
                        this.adminIds.add(Integer.valueOf(i));
                    }
                    z2 = putValuesNoUpdate;
                }
                if (!this.activePluginsCache.containsKey(Integer.valueOf(packageUid))) {
                    Log.i(TAG, "addOrUpdateSecureStorageConfig - adding new plugin in cache pluginUid-" + packageUid + ",pkg-" + credentialStorage.packageName);
                    this.activePluginsCache.put(Integer.valueOf(packageUid), credentialStorage.packageName);
                }
                z3 = z2;
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                }
            }
        }
        Log.i(TAG, "addOrUpdateSecureStorageConfig retcode-" + z3);
        return z3;
    }

    public boolean isCredentialStorageManagedAsUser(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageManagedAsUser");
        Log.i(TAG, "isCredentialStorageManagedAsUser is called....");
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "isCredentialStorageManagedAsUser - Invalid Arguments");
            return false;
        }
        if (DBG) {
            Log.i(TAG, "isCredentialStorageManagedAsUser is called for ContainerId-" + i);
        }
        return isCredentialStorageManagedInternal(-1, i, credentialStorage.name, credentialStorage.packageName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a3, code lost:
    
        android.util.Log.i(com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG, "getStorageAuthenticationType - found the strictest value...");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00aa, code lost:
    
        r4 = r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getStorageAuthenticationType(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("getStorageAuthenticationType");
        Log.i(TAG, "getStorageAuthenticationType is called....");
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -1;
            }
            Log.i(TAG, "getStorageAuthenticationType - Invalid Arguments");
            return -1;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return -13;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = 105;
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", new String[]{"userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName}, new String[]{"storageAuthType"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    Iterator it = dataByFields.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ContentValues contentValues = (ContentValues) it.next();
                        if (contentValues == null) {
                            Log.i(TAG, "value is null, continue...");
                        } else {
                            Integer asInteger = contentValues.getAsInteger("storageAuthType");
                            if (asInteger == null) {
                                Log.i(TAG, "parsing error, continue...");
                            } else {
                                int intValue = asInteger.intValue();
                                if (intValue == 100) {
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCallerDelegated(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "isCallerDelegated is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            Log.i(TAG, "cxtInfo is null");
            return false;
        }
        return isCallerDelegated(contextInfo.mContainerId, contextInfo.mCallerUid, credentialStorage, i);
    }

    public final boolean isCallerDelegated(int i, int i2, CredentialStorage credentialStorage, int i3) {
        Log.i(TAG, "isCallerDelegated is called callerUid-" + i2 + ", userId-" + i);
        boolean z = false;
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("access_type", i3);
            List<AppIdentity> packagesFromWhiteListInternal = getPackagesFromWhiteListInternal(i, credentialStorage, bundle);
            if (packagesFromWhiteListInternal != null && packagesFromWhiteListInternal.size() > 0) {
                String[] packagesForUid = this.mPm.getPackagesForUid(i2);
                if (packagesForUid != null && packagesForUid.length > 0) {
                    for (AppIdentity appIdentity : packagesFromWhiteListInternal) {
                        Log.i(TAG, "isCallerDelegated package- " + appIdentity.getPackageName());
                        if (Arrays.asList(packagesForUid).contains(appIdentity.getPackageName())) {
                            Log.i(TAG, "isCallerDelegated Caller is delegated app...");
                            if (checkDelegatorPermission(i, i3, appIdentity.getPackageName(), credentialStorage)) {
                                z = true;
                                break;
                            }
                        }
                    }
                }
            } else {
                Log.i(TAG, "isCallerDelegated Caller is not delegated app...");
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
        Log.i(TAG, "isCallerDelegated status " + z);
        return z;
    }

    public final boolean checkDelegatorPermission(int i, int i2, String str, CredentialStorage credentialStorage) {
        int delegatorUid = getDelegatorUid(i, i2, str, credentialStorage);
        Log.i(TAG, "checkDelegatorPermission delegatorUid - " + delegatorUid);
        try {
            Log.i(TAG, "Check if caller has some UCM permission...");
            if (credentialStorage != null) {
                String str2 = credentialStorage.name;
                String str3 = credentialStorage.packageName;
                if (str2 != null && str3 != null) {
                    if (isSystemStorage(str3) && AppGlobals.getPackageManager().checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT", delegatorUid) == 0) {
                        Log.i(TAG, "  delegator has valid ESE permission... Processing further...");
                        return true;
                    }
                    if (AppGlobals.getPackageManager().checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", delegatorUid) == 0) {
                        Log.i(TAG, "  delegator has valid other permission... Processing further...");
                        return true;
                    }
                }
            } else if (AppGlobals.getPackageManager().checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT", delegatorUid) == 0 || AppGlobals.getPackageManager().checkUidPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", delegatorUid) == 0) {
                Log.i(TAG, "  delegator has valid permission... Processing further...");
                return true;
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
        Log.i(TAG, "checkDelegatorPermission delegator doesnt have valid permission...");
        return false;
    }

    public int addPackagesToWhiteListInternal(int i, int i2, CredentialStorage credentialStorage, List list, Bundle bundle) {
        Log.i(TAG, "addPackagesToWhiteListInternal is called....");
        checkCallerPermissionFor("addPackagesToWhiteListInternal");
        return addPackagesToWhiteListMain(i, i2, credentialStorage, list, bundle, false);
    }

    public int addPackagesToWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List list, Bundle bundle) {
        boolean z;
        Log.i(TAG, "addPackagesToWhiteList is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (isCallerDelegated(i2, i, credentialStorage, 107)) {
            Log.i(TAG, "addPackagesToWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        return addPackagesToWhiteListMain(i, i2, credentialStorage, list, bundle, z);
    }

    public final int addPackagesToWhiteListMain(int i, int i2, CredentialStorage credentialStorage, List list, Bundle bundle, boolean z) {
        String str;
        Log.i(TAG, "addPackagesToWhiteListMain is called....");
        try {
            KnoxAnalytics.log(getKAData("addPackagesToWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isValidParam(credentialStorage) && list != null && bundle != null) {
                if (DBG) {
                    Log.i(TAG, "addPackagesToWhiteListMain is called for Caller UID-" + i + " mContainerId " + i2);
                }
                String str2 = credentialStorage.signature;
                if (str2 != null && !validateSignature(credentialStorage.packageName, str2, 0)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Plugin is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName) && !z) {
                    if (DBG) {
                        Log.i(TAG, "addPackagesToWhiteListMain return false..");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -12;
                }
                int i3 = bundle.getInt("access_type", -1);
                if (!isValidAccessType(i3)) {
                    if (DBG) {
                        Log.i(TAG, "addPackagesToWhiteListMain not passed valid access_type");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -15;
                }
                if (i3 == 104) {
                    String string = bundle.getString("alias");
                    Log.i(TAG, "addPackagesToWhiteListMain alias-" + string);
                    if (TextUtils.isEmpty(string)) {
                        if (DBG) {
                            Log.i(TAG, "addPackagesToWhiteListMain alias name not provided for Certificate access_type");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -16;
                    }
                    str = string;
                    if (true != checkCredentialStorageAliasForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName, string)) {
                        if (DBG) {
                            Log.i(TAG, "- alias not exist for credential storage...");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -14;
                    }
                } else {
                    str = null;
                }
                if (z) {
                    if (i3 == 103) {
                        Log.i(TAG, "addPackagesToWhiteListMain Delegated app can't WhiteList storage.. error");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -30;
                    }
                    if (i3 == 107) {
                        Log.i(TAG, "addPackagesToWhiteListMain Delegated app can't further delegate.. error");
                        return -29;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        AppIdentity appIdentity = (AppIdentity) it.next();
                        if (z && isPackageDelegated(appIdentity.getPackageName(), 107)) {
                            Log.i(TAG, "addPackagesToWhiteListMain ..app is already delegated by other app.. error");
                            return -29;
                        }
                    }
                }
                return insertOrUpdateWhiteListPackages(credentialStorage, list, i, i2, bundle, i3, str);
            }
            if (DBG) {
                Log.i(TAG, "addPackagesToWhiteListMain - Invalid Arguments");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -11;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isValidAccessType(int i) {
        boolean z = i == 103 || i == 104 || i == 107;
        Log.i(TAG, "isValidAccessType type-" + i + " and status-" + z);
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0218 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x031f  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int insertOrUpdateWhiteListPackages(CredentialStorage credentialStorage, List list, int i, int i2, Bundle bundle, int i3, String str) {
        int i4;
        String str2;
        String str3;
        IPackageManager iPackageManager;
        String str4;
        PackageInfo packageInfo;
        boolean z;
        String str5;
        boolean z2;
        PackageInfo packageInfo2;
        String str6;
        int i5;
        String str7;
        int i6;
        CredentialStorage credentialStorage2 = credentialStorage;
        String str8 = str;
        if (DBG) {
            Log.i(TAG, "insertOrUpdateWhiteListPackages is called...");
        }
        if (DBG) {
            Log.i(TAG, "adminId - " + i + " ContainerId - " + i2 + " Storage name - " + credentialStorage2.name + " Storage Package - " + credentialStorage2.packageName);
        }
        Log.i(TAG, "insertOrUpdateWhiteListPackages access_type-" + i3);
        String packageName = ((AppIdentity) list.get(0)).getPackageName();
        boolean z3 = packageName != null && packageName.equals("*");
        Log.i(TAG, "insertOrUpdateWhiteListPackages - allowAllApp -" + z3);
        String str9 = "storageManufacture";
        String str10 = "UniversalCredentialWhiteListTable";
        String str11 = "The exception occurs ";
        int i7 = -1;
        if (true == z3) {
            Log.i(TAG, "insertOrUpdateWhiteListPackages - inside Allow all packages");
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage2.name);
            contentValues.put("storagePackageName", credentialStorage2.packageName);
            String str12 = credentialStorage2.manufacturer;
            if (str12 != null) {
                contentValues.put("storageManufacture", str12);
            }
            contentValues.put("accessType", Integer.valueOf(i3));
            if (str8 != null) {
                contentValues.put("alias", str8);
            }
            contentValues.put("appPackage", "*");
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", Integer.valueOf(i));
            contentValues2.put("userId", Integer.valueOf(i2));
            contentValues2.put("storageName", credentialStorage2.name);
            contentValues2.put("storagePackageName", credentialStorage2.packageName);
            contentValues2.put("accessType", Integer.valueOf(i3));
            if (str8 != null) {
                contentValues2.put("alias", str8);
            }
            contentValues2.put("appPackage", "*");
            try {
                i7 = this.mEdmStorageProvider.putValues("UniversalCredentialWhiteListTable", contentValues, contentValues2) ? 0 : -1;
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "insertOrUpdateWhiteListPackages - Exception allowAllPackages" + e.getMessage());
                }
            }
            str2 = "UniversalCredentialWhiteListTable";
        } else {
            Log.i(TAG, "insertOrUpdateWhiteListPackages - inside else block...");
            Log.i(TAG, "insertOrUpdateWhiteListPackages - WhiteList app size -" + list.size());
            IPackageManager packageManager = AppGlobals.getPackageManager();
            Iterator it = list.iterator();
            i4 = -1;
            while (true) {
                if (!it.hasNext()) {
                    str2 = str10;
                    break;
                }
                AppIdentity appIdentity = (AppIdentity) it.next();
                int i8 = i4;
                String str13 = TAG;
                Iterator it2 = it;
                StringBuilder sb = new StringBuilder();
                String str14 = str10;
                sb.append("insertOrUpdateWhiteListPackages - pkg : ");
                sb.append(appIdentity.getPackageName());
                Log.i(str13, sb.toString());
                if (appIdentity.getPackageName() == null || appIdentity.getPackageName().length() == 0) {
                    i4 = i8;
                    it = it2;
                    str10 = str14;
                } else {
                    try {
                        str3 = str9;
                    } catch (Exception e2) {
                        e = e2;
                        str3 = str9;
                    }
                    try {
                        packageInfo = packageManager.getPackageInfo(appIdentity.getPackageName(), 64L, i2);
                        iPackageManager = packageManager;
                        str4 = str11;
                    } catch (Exception e3) {
                        e = e3;
                        String str15 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        iPackageManager = packageManager;
                        str4 = str11;
                        sb2.append(str4);
                        sb2.append(e.getMessage());
                        Log.i(str15, sb2.toString());
                        packageInfo = null;
                        Log.i(TAG, "Package Info: " + packageInfo);
                        if (packageInfo == null) {
                        }
                        if (!isSignatureInvalid(appIdentity, packageInfo, z)) {
                        }
                    }
                    Log.i(TAG, "Package Info: " + packageInfo);
                    z = packageInfo == null;
                    if (!isSignatureInvalid(appIdentity, packageInfo, z)) {
                        i4 = -18;
                        str2 = str14;
                        break;
                    }
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("adminUid", Integer.valueOf(i));
                    contentValues3.put("userId", Integer.valueOf(i2));
                    contentValues3.put("storageName", credentialStorage2.name);
                    contentValues3.put("storagePackageName", credentialStorage2.packageName);
                    contentValues3.put("accessType", Integer.valueOf(i3));
                    if (str8 != null) {
                        contentValues3.put("alias", str8);
                    }
                    contentValues3.put("appPackage", appIdentity.getPackageName());
                    if (z) {
                        z2 = z;
                        try {
                            contentValues3.put("appUid", Integer.valueOf(packageInfo.applicationInfo.uid));
                            str5 = str4;
                        } catch (Exception e4) {
                            String str16 = TAG;
                            packageInfo2 = packageInfo;
                            StringBuilder sb3 = new StringBuilder();
                            str5 = str4;
                            sb3.append("insertOrUpdateWhiteListPackages getting appuid - Exception");
                            sb3.append(e4.getMessage());
                            Log.i(str16, sb3.toString());
                        }
                    } else {
                        str5 = str4;
                        z2 = z;
                    }
                    packageInfo2 = packageInfo;
                    if (appIdentity.getSignature() != null) {
                        contentValues3.put("appSignature", appIdentity.getSignature());
                    }
                    String str17 = credentialStorage2.manufacturer;
                    String str18 = str3;
                    if (str17 != null) {
                        contentValues3.put(str18, str17);
                    }
                    ContentValues contentValues4 = new ContentValues();
                    contentValues4.put("adminUid", Integer.valueOf(i));
                    contentValues4.put("userId", Integer.valueOf(i2));
                    contentValues4.put("storageName", credentialStorage2.name);
                    contentValues4.put("storagePackageName", credentialStorage2.packageName);
                    contentValues4.put("appPackage", appIdentity.getPackageName());
                    contentValues4.put("accessType", Integer.valueOf(i3));
                    if (str8 != null) {
                        contentValues4.put("alias", str8);
                    }
                    try {
                        str2 = str14;
                    } catch (Exception e5) {
                        e = e5;
                        str2 = str14;
                    }
                    try {
                    } catch (Exception e6) {
                        e = e6;
                        if (DBG) {
                        }
                        i5 = -1;
                        if (z2) {
                        }
                        str8 = str;
                        i4 = i5;
                        str10 = str2;
                        str9 = str7;
                        it = it2;
                        packageManager = iPackageManager;
                        str11 = str6;
                        credentialStorage2 = credentialStorage;
                    }
                    if (!this.mEdmStorageProvider.putValues(str2, contentValues3, contentValues4)) {
                        try {
                            if (!DBG) {
                                break;
                            }
                            Log.i(TAG, "insertOrUpdateWhiteListPackages - DB operation failed");
                            break;
                        } catch (Exception e7) {
                            e = e7;
                            if (DBG) {
                                str6 = str5;
                            } else {
                                String str19 = TAG;
                                StringBuilder sb4 = new StringBuilder();
                                str6 = str5;
                                sb4.append(str6);
                                sb4.append(e.getMessage());
                                Log.i(str19, sb4.toString());
                            }
                            i5 = -1;
                            if (z2) {
                            }
                            str8 = str;
                            i4 = i5;
                            str10 = str2;
                            str9 = str7;
                            it = it2;
                            packageManager = iPackageManager;
                            str11 = str6;
                            credentialStorage2 = credentialStorage;
                        }
                    } else {
                        str6 = str5;
                        i5 = 0;
                        if (z2) {
                            try {
                                i6 = packageInfo2.applicationInfo.uid;
                                str7 = str18;
                            } catch (Exception e8) {
                                e = e8;
                                str7 = str18;
                            }
                            try {
                                if (!this.whitelistedAppsCache.containsKey(Integer.valueOf(i6))) {
                                    this.whitelistedAppsCache.put(Integer.valueOf(i6), appIdentity.getPackageName());
                                    Log.i(TAG, "Caching WhiteList app id-" + i6 + ", packageName-" + appIdentity.getPackageName());
                                }
                            } catch (Exception e9) {
                                e = e9;
                                Log.i(TAG, str6 + e.getMessage());
                                str8 = str;
                                i4 = i5;
                                str10 = str2;
                                str9 = str7;
                                it = it2;
                                packageManager = iPackageManager;
                                str11 = str6;
                                credentialStorage2 = credentialStorage;
                            }
                        } else {
                            str7 = str18;
                        }
                        str8 = str;
                        i4 = i5;
                        str10 = str2;
                        str9 = str7;
                        it = it2;
                        packageManager = iPackageManager;
                        str11 = str6;
                        credentialStorage2 = credentialStorage;
                    }
                }
            }
            Log.i(TAG, "insertOrUpdateWhiteListPackages retcode-" + i4);
            this.mExistWhitelist = checkCountFromEdmDB(str2);
            updateUcmCryptoProp();
            return i4;
        }
        i4 = i7;
        Log.i(TAG, "insertOrUpdateWhiteListPackages retcode-" + i4);
        this.mExistWhitelist = checkCountFromEdmDB(str2);
        updateUcmCryptoProp();
        return i4;
    }

    public final boolean isSignatureInvalid(AppIdentity appIdentity, PackageInfo packageInfo, boolean z) {
        int i = KnoxInternalFeature.KNOX_CONFIG_VERSION;
        boolean z2 = appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0;
        if (i < EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_7_1.ordinal() + 3) {
            if (z2 && z) {
                return isSignatureInvalid(appIdentity, packageInfo);
            }
        } else {
            if (!z2) {
                Log.e(TAG, "UniversalCredentialManagerPolicy passed empty String signature");
                return true;
            }
            if (z) {
                return isSignatureInvalid(appIdentity, packageInfo);
            }
        }
        return false;
    }

    public final boolean isSignatureInvalid(AppIdentity appIdentity, PackageInfo packageInfo) {
        Signature[] convertStringToSignature = convertStringToSignature(appIdentity.getSignature());
        if (convertStringToSignature == null) {
            Log.e(TAG, "UniversalCredentialManagerPolicy passed String signature is invalid");
            return true;
        }
        if (compareSignatures(packageInfo.signatures, convertStringToSignature)) {
            return false;
        }
        Log.e(TAG, "Package is installed, and signature doesn't match. So return falure");
        return true;
    }

    public int removePackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, List list, Bundle bundle) {
        boolean z;
        String str;
        int i;
        String str2;
        Log.i(TAG, "removePackagesFromWhiteList is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage) || list == null) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "removePackagesFromWhiteList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("removePackagesFromWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i2 = contextInfo.mCallerUid;
        int i3 = contextInfo.mContainerId;
        if (isCallerDelegated(i3, i2, credentialStorage, 107)) {
            Log.i(TAG, "removePackagesFromWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i2 = contextInfo.mCallerUid;
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "removePackagesFromWhiteList is called for Caller UID-" + i2 + " mContainerId " + i3);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i2, i3, credentialStorage.name, credentialStorage.packageName) && !z) {
                if (DBG) {
                    Log.i(TAG, "removePackagesFromWhiteList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            int i4 = bundle.getInt("access_type", -1);
            if (!isValidAccessType(i4)) {
                if (DBG) {
                    Log.i(TAG, "removePackagesFromWhiteList not passed valid access_type");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            if (i4 == 104) {
                String string = bundle.getString("alias");
                Log.i(TAG, "removePackagesFromWhiteList alias-" + string);
                if (TextUtils.isEmpty(string)) {
                    if (DBG) {
                        Log.i(TAG, "removePackagesFromWhiteList alias name not provided for Certificate access_type");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -16;
                }
                str2 = string;
                i = i4;
                if (true != checkCredentialStorageAliasForAdmin(i2, i3, credentialStorage.name, credentialStorage.packageName, str2)) {
                    if (DBG) {
                        Log.i(TAG, "removePackagesFromWhiteList - alias not exist for credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
            } else {
                i = i4;
                str2 = null;
            }
            if (true == removeWhiteListPackages(credentialStorage, list, i2, i3, bundle, i, str2)) {
                return 0;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeWhiteListPackages(CredentialStorage credentialStorage, List list, int i, int i2, Bundle bundle, int i3, String str) {
        String str2;
        CredentialStorage credentialStorage2 = credentialStorage;
        int i4 = i3;
        String str3 = str;
        if (DBG) {
            Log.i(TAG, "removeWhiteListPackages is called...");
        }
        if (DBG) {
            Log.i(TAG, "adminId - " + i + " ContainerId - " + i2 + " Storage name - " + credentialStorage2.name + " Storage Package - " + credentialStorage2.packageName + ", accessType-" + i4 + ", alias-" + str3);
        }
        Log.i(TAG, "removeWhiteListPackages - WhiteList app size -" + list.size());
        Iterator it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            AppIdentity appIdentity = (AppIdentity) it.next();
            Log.i(TAG, "removeWhiteListPackages - pkg : " + appIdentity.getPackageName());
            if (appIdentity.getPackageName() != null) {
                if (str3 == null) {
                    Log.i(TAG, "removeWhiteListPackages access_type-" + i4);
                    try {
                        z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType", "appPackage"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage2.name, credentialStorage2.packageName, String.valueOf(i3), appIdentity.getPackageName()});
                        if (!z) {
                            Log.i(TAG, "removeWhiteListPackages - failed to remove record...");
                            break;
                        }
                    } catch (Exception e) {
                        if (DBG) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                    }
                } else {
                    Log.i(TAG, "removeWhiteListPackages access_type-" + i4 + " and alias-" + str3);
                    str2 = "UniversalCredentialWhiteListTable";
                    try {
                        z = this.mEdmStorageProvider.deleteDataByFields(str2, new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType", "alias", "appPackage"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage2.name, credentialStorage2.packageName, String.valueOf(i3), str, appIdentity.getPackageName()});
                        if (!z) {
                            Log.i(TAG, "removeWhiteListPackages - failed to remove record...");
                            break;
                        }
                    } catch (Exception e2) {
                        if (DBG) {
                            Log.i(TAG, "The exception occurs " + e2.getMessage());
                        }
                    }
                }
                credentialStorage2 = credentialStorage;
                i4 = i3;
                str3 = str;
            }
        }
        str2 = "UniversalCredentialWhiteListTable";
        this.mExistWhitelist = checkCountFromEdmDB(str2);
        updateUcmCryptoProp();
        Log.i(TAG, "removeWhiteListPackages retcode-" + z);
        return z;
    }

    public List getPackagesFromWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        boolean z;
        String str;
        String str2;
        Log.i(TAG, "getPackagesFromWhiteList is called....");
        validateContextInfo(contextInfo);
        ArrayList arrayList = null;
        if (!isValidParam(credentialStorage) || bundle == null) {
            if (DBG) {
                Log.i(TAG, "getPackagesFromWhiteList - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(getKAData("getPackagesFromWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
        if (isCallerDelegated(i2, i, credentialStorage, 107)) {
            Log.i(TAG, "getPackagesFromWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromWhiteList is called for Caller UID-" + i + " mContainerId " + i2);
                }
                str = credentialStorage.signature;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return null;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return null;
        }
        if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName) && !z) {
            if (DBG) {
                Log.i(TAG, "getPackagesFromWhiteList return false..");
            }
            return null;
        }
        int i3 = bundle.getInt("access_type", -1);
        if (!isValidAccessType(i3)) {
            if (DBG) {
                Log.i(TAG, "getPackagesFromWhiteList not passed valid access_type");
            }
            return null;
        }
        if (i3 == 104) {
            String string = bundle.getString("alias");
            Log.i(TAG, "getPackagesFromWhiteList alias-" + string);
            if (TextUtils.isEmpty(string)) {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromWhiteList alias name not provided for Certificate access_type");
                }
                return null;
            }
            if (true != checkCredentialStorageAliasForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName, string)) {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromWhiteList - alias not exist for credential storage...");
                }
                return null;
            }
            str2 = string;
        } else {
            str2 = null;
        }
        Log.i(TAG, "getPackagesFromWhiteList cxtInfo.mContainerId-" + i2 + ",name-" + credentialStorage.name + ",package-" + credentialStorage.packageName + ", accessType-" + i3);
        ArrayList whitelistedData = getWhitelistedData(i, i2, credentialStorage, i3, str2);
        if (whitelistedData == null || whitelistedData.size() <= 0) {
            Log.i(TAG, "getPackagesFromWhiteList DB is empty...");
        } else {
            Log.i(TAG, "getPackagesFromWhiteList - Size-" + whitelistedData.size());
            ArrayList arrayList2 = new ArrayList();
            try {
                Iterator it = whitelistedData.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    AppIdentity appIdentity = new AppIdentity();
                    appIdentity.setPackageName(contentValues.getAsString("appPackage"));
                    appIdentity.setSignature(contentValues.getAsString("appSignature"));
                    arrayList2.add(appIdentity);
                }
                arrayList = arrayList2;
            } catch (Exception e3) {
                e = e3;
                arrayList = arrayList2;
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
                return arrayList;
            }
        }
        return arrayList;
    }

    public final ArrayList getAllWhitelistedApps() {
        return this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", null, null, new String[]{"appUid", "appPackage"});
    }

    public final ArrayList getAllExemptedApps() {
        return this.mEdmStorageProvider.getDataByFields("UniversalCredentialExemptTable", null, null, new String[]{"appUid", "appPackage"});
    }

    public final ArrayList getWhitelistedData(int i, int i2, CredentialStorage credentialStorage, int i3, String str) {
        if (str == null) {
            return this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3)}, new String[]{"appPackage", "appSignature"});
        }
        return this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType", "alias"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3), str}, new String[]{"appPackage", "appSignature"});
    }

    public final void processPackagesForPlugin(int i, int i2, CredentialStorage credentialStorage, Bundle bundle) {
        PackageInfo packageInfo;
        Log.i(TAG, "processPackagesForPlugin - adminUid" + i + ", userId-" + i2 + ", Storage -" + credentialStorage.name);
        try {
            List<Integer> adminIdRelatedToStorageAsUser = getAdminIdRelatedToStorageAsUser(i2, credentialStorage);
            ArrayList<String> arrayList = new ArrayList();
            for (Integer num : adminIdRelatedToStorageAsUser) {
                if (num == null) {
                    Log.i(TAG, "storageAdmin is null, continue...");
                } else {
                    Log.i(TAG, "adminUid related to storage is - " + num);
                    if (num.intValue() == i) {
                        Log.i(TAG, "Ignoring current adminUid  - " + num);
                    } else {
                        ArrayList whitelistedData = getWhitelistedData(num.intValue(), i2, credentialStorage, 103, null);
                        if (whitelistedData != null && whitelistedData.size() > 0) {
                            Iterator it = whitelistedData.iterator();
                            while (it.hasNext()) {
                                String asString = ((ContentValues) it.next()).getAsString("appPackage");
                                if (!arrayList.contains(asString)) {
                                    Log.i(TAG, "Adding app in whitelistPkgsByOtherAdmin -" + asString);
                                    arrayList.add(asString);
                                }
                            }
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                Log.i(TAG, "Blocking all packages...");
                bundle.putInt("package_access_type", 2);
                return;
            }
            if (arrayList.size() > 0) {
                if (arrayList.contains("*")) {
                    Log.i(TAG, "Allowing all packages...");
                    bundle.putInt("package_access_type", 1);
                    return;
                }
                ArrayList<Integer> arrayList2 = new ArrayList();
                IPackageManager packageManager = AppGlobals.getPackageManager();
                for (String str : arrayList) {
                    Log.i(TAG, "Allowed pkg - " + str);
                    try {
                        packageInfo = packageManager.getPackageInfo(str, 64L, i2);
                    } catch (Exception e) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                        packageInfo = null;
                    }
                    if (packageInfo == null) {
                        Log.i(TAG, "pkgInfo is null");
                    } else {
                        int packageUid = this.mPm.getPackageUid(str, i2);
                        if (!arrayList2.contains(Integer.valueOf(packageUid))) {
                            arrayList2.add(Integer.valueOf(packageUid));
                        }
                    }
                }
                int[] iArr = new int[arrayList2.size()];
                int i3 = 0;
                for (Integer num2 : arrayList2) {
                    if (num2 == null) {
                        Log.i(TAG, "id is null, continue...");
                    } else {
                        Log.i(TAG, "adding id-" + num2);
                        iArr[i3] = num2.intValue();
                        i3++;
                    }
                }
                bundle.putInt("package_access_type", 3);
                bundle.putIntArray("allowed_packages", iArr);
            }
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
        }
    }

    public boolean isAccessAllowed(int i, CredentialStorage credentialStorage, Bundle bundle) {
        int i2;
        boolean z;
        boolean z2;
        List list;
        PackageInfo packageInfo;
        Log.i(TAG, "isAccessAllowed is called....");
        checkCallerPermissionFor("isAccessAllowed");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = false;
        int i3 = 0;
        z3 = false;
        if (bundle != null) {
            try {
                try {
                    i2 = bundle.getInt("userId", -1);
                } catch (Exception e) {
                    e = e;
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                    return z3;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            i2 = -1;
        }
        Log.i(TAG, "isAccessAllowed is called....userId-" + i2 + " and packageUid-" + i);
        if (i2 == -1) {
            i2 = UserHandle.getUserId(i);
        }
        List<AppIdentity> packagesFromWhiteListAsUser = getPackagesFromWhiteListAsUser(i2, credentialStorage, bundle);
        if (packagesFromWhiteListAsUser != null && packagesFromWhiteListAsUser.size() > 0) {
            Iterator it = packagesFromWhiteListAsUser.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                String packageName = ((AppIdentity) it.next()).getPackageName();
                if (packageName != null && packageName.equals("*")) {
                    Log.i(TAG, "isAccessAllowed All packages are allowed...");
                    z = true;
                    break;
                }
            }
            if (!z) {
                try {
                    String[] packagesForUid = this.mPm.getPackagesForUid(i);
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    if (packagesForUid != null) {
                        int length = packagesForUid.length;
                        while (i3 < length) {
                            try {
                                String str = packagesForUid[i3];
                                for (AppIdentity appIdentity : packagesFromWhiteListAsUser) {
                                    String packageName2 = appIdentity.getPackageName();
                                    String signature = appIdentity.getSignature();
                                    String str2 = TAG;
                                    list = packagesFromWhiteListAsUser;
                                    StringBuilder sb = new StringBuilder();
                                    z2 = z;
                                    try {
                                        sb.append("isAccessAllowed pkgName-");
                                        sb.append(str);
                                        sb.append(" and DB packageName-");
                                        sb.append(packageName2);
                                        Log.i(str2, sb.toString());
                                        if (str != null && packageName2 != null && packageName2.equals(str)) {
                                            if (signature != null) {
                                                Log.i(TAG, "isAccessAllowed package matched. Now matching signature....");
                                                Signature[] convertStringToSignature = convertStringToSignature(signature);
                                                if (convertStringToSignature == null) {
                                                    Log.i(TAG, "isAccessAllowed - failed to convert signature from db.");
                                                }
                                                try {
                                                    packageInfo = packageManager.getPackageInfo(str, 64L, UserHandle.getUserId(i));
                                                } catch (Exception e2) {
                                                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                                                    packageInfo = null;
                                                }
                                                if (convertStringToSignature != null && packageInfo != null && compareSignatures(packageInfo.signatures, convertStringToSignature)) {
                                                    Log.i(TAG, "isAccessAllowed match found with signature matching...");
                                                } else {
                                                    Log.i(TAG, "isAccessAllowed signature mismatch happened...Ignoring package");
                                                }
                                            } else {
                                                Log.i(TAG, "isAccessAllowed match found ...");
                                            }
                                            z = true;
                                            break;
                                        }
                                        packagesFromWhiteListAsUser = list;
                                        z = z2;
                                    } catch (Exception e3) {
                                        e = e3;
                                        z3 = z2;
                                        Log.i(TAG, "The exception occurs " + e.getMessage());
                                        return z3;
                                    }
                                }
                                list = packagesFromWhiteListAsUser;
                                if (!z) {
                                    i3++;
                                    packagesFromWhiteListAsUser = list;
                                }
                            } catch (Exception e4) {
                                e = e4;
                                z2 = z;
                            }
                        }
                    } else {
                        Log.i(TAG, "isAccessAllowed no packages related to uid-" + i);
                    }
                    break;
                } catch (Exception e5) {
                    e = e5;
                    z3 = z;
                }
            }
            z3 = z;
        } else {
            Log.i(TAG, "getPackagesFromWhiteListAsUser returned empty/null whitelist");
        }
        return z3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00da, code lost:
    
        android.util.Log.i(com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG, "getDelegatorUid delegator found..");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e1, code lost:
    
        r11 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDelegatorUid(int i, int i2, String str, CredentialStorage credentialStorage) {
        ArrayList dataByFields;
        int i3 = -1;
        try {
            if (credentialStorage != null) {
                dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "storageName", "storagePackageName", "accessType", "appPackage"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName, String.valueOf(i2), str}, new String[]{"adminUid"});
            } else {
                dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "accessType", "appPackage"}, new String[]{String.valueOf(i), String.valueOf(i2), str}, new String[]{"adminUid"});
            }
            if (dataByFields != null && dataByFields.size() > 0) {
                Log.i(TAG, "getDelegatorUid - Size-" + dataByFields.size());
                Iterator it = dataByFields.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        if (asInteger == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            int intValue = asInteger.intValue();
                            Log.i(TAG, "getDelegatorUid adminId -" + intValue);
                            if (isAdminLicenseActive(intValue, credentialStorage)) {
                                try {
                                    break;
                                } catch (Exception e) {
                                    e = e;
                                    i3 = intValue;
                                    Log.i(TAG, "The exception occurs " + e.getMessage());
                                    Log.i(TAG, "getDelegatorUid adminUid - " + i3);
                                    return i3;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        Log.i(TAG, "getDelegatorUid adminUid - " + i3);
        return i3;
    }

    public final CredentialStorage[] getDelegatedStorages(int i, int i2, String str) {
        CredentialStorage[] credentialStorageArr = null;
        try {
            int i3 = 0;
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "accessType", "appPackage"}, new String[]{String.valueOf(i), String.valueOf(i2), str}, new String[]{"storageName", "storagePackageName"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Log.i(TAG, "getDelegatedStorages - Size-" + dataByFields.size());
                credentialStorageArr = new CredentialStorage[dataByFields.size()];
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("storageName");
                    String asString2 = contentValues.getAsString("storagePackageName");
                    Log.i(TAG, "getDelegatedStorages storageName -" + asString + ", storagePackage-" + asString2);
                    CredentialStorage credentialStorage = new CredentialStorage();
                    credentialStorage.name = asString;
                    credentialStorage.packageName = asString2;
                    credentialStorageArr[i3] = credentialStorage;
                    i3++;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
        return credentialStorageArr;
    }

    public final boolean isPackageDelegated(String str, int i) {
        Log.i(TAG, "isPackageDelegated is called....");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("appPackage", str);
            contentValues.put("accessType", Integer.valueOf(i));
            if (this.mEdmStorageProvider.getCount("UniversalCredentialWhiteListTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "isPackageDelegated Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final List getPackagesFromWhiteListAsUser(int i, CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "getPackagesFromWhiteListAsUser is called....");
        if (!isValidParam(credentialStorage) || bundle == null) {
            if (!DBG) {
                return null;
            }
            Log.i(TAG, "getPackagesFromWhiteListAsUser - Invalid Arguments");
            return null;
        }
        if (DBG) {
            Log.i(TAG, "getPackagesFromWhiteListAsUser is called for mContainerId " + i);
        }
        return getPackagesFromWhiteListInternal(i, credentialStorage, bundle);
    }

    public final List getPackagesFromWhiteListInternal(int i, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        ArrayList dataByFields;
        Log.i(TAG, "getPackagesFromWhiteListInternal is called....");
        ArrayList arrayList = null;
        try {
            int i2 = bundle.getInt("access_type", -1);
            try {
                if (!isValidAccessType(i2)) {
                    if (DBG) {
                        Log.i(TAG, "getPackagesFromWhiteListInternal not passed valid access_type");
                    }
                    return null;
                }
                if (i2 == 104) {
                    str = bundle.getString("alias");
                    Log.i(TAG, "getPackagesFromWhiteListInternal alias-" + str);
                    if (TextUtils.isEmpty(str)) {
                        if (DBG) {
                            Log.i(TAG, "getPackagesFromWhiteListInternal alias name not provided for Certificate access_type");
                        }
                        return null;
                    }
                } else {
                    str = null;
                }
                String str2 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("getPackagesFromWhiteListInternal mContainerId-");
                sb.append(i);
                sb.append(",name-");
                sb.append(credentialStorage != null ? credentialStorage.name : "");
                sb.append(",package-");
                sb.append(credentialStorage != null ? credentialStorage.packageName : "");
                sb.append(", accessType-");
                sb.append(i2);
                Log.i(str2, sb.toString());
                if (str != null) {
                    dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "storageName", "storagePackageName", "accessType", "alias"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName, String.valueOf(i2), str}, new String[]{"adminUid", "appPackage", "appSignature"});
                } else if (credentialStorage != null) {
                    dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "storageName", "storagePackageName", "accessType"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName, String.valueOf(i2)}, new String[]{"adminUid", "appPackage", "appSignature"});
                } else {
                    dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "accessType"}, new String[]{String.valueOf(i), String.valueOf(i2)}, new String[]{"adminUid", "appPackage", "appSignature"});
                }
                if (dataByFields == null || dataByFields.size() <= 0) {
                    return null;
                }
                ArrayList arrayList2 = new ArrayList();
                try {
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        if (contentValues == null) {
                            Log.i(TAG, "value is null, continue...");
                        } else {
                            AppIdentity appIdentity = new AppIdentity();
                            appIdentity.setPackageName(contentValues.getAsString("appPackage"));
                            appIdentity.setSignature(contentValues.getAsString("appSignature"));
                            Log.i(TAG, "getPackagesFromWhiteListInternal APP PKG-" + contentValues.getAsString("appPackage"));
                            if (DBG) {
                                Log.i(TAG, "getPackagesFromWhiteListInternal APP PKG-" + contentValues.getAsString("appSignature"));
                            }
                            Integer asInteger = contentValues.getAsInteger("adminUid");
                            if (asInteger == null) {
                                Log.i(TAG, "parsing error, continue...");
                            } else if (isAdminLicenseActive(asInteger.intValue(), credentialStorage)) {
                                arrayList2.add(appIdentity);
                            }
                        }
                    }
                    return arrayList2;
                } catch (Exception e) {
                    e = e;
                    arrayList = arrayList2;
                    if (!DBG) {
                        return arrayList;
                    }
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                    return arrayList;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            arrayList = null;
        }
    }

    public int clearWhiteList(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        boolean z;
        String str;
        String str2;
        Log.i(TAG, "clearWhiteList is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "clearWhiteList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("clearWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (isCallerDelegated(i2, i, credentialStorage, 107)) {
            Log.i(TAG, "clearWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "clearWhiteList is called for Caller UID-" + i + " mContainerId " + i2);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName) && !z) {
                if (DBG) {
                    Log.i(TAG, "clearWhiteList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            int i3 = bundle.getInt("access_type", -1);
            if (!isValidAccessType(i3)) {
                if (DBG) {
                    Log.i(TAG, "clearWhiteList not passed valid access_type");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            if (i3 == 104) {
                String string = bundle.getString("alias");
                Log.i(TAG, "clearWhiteList alias-" + string);
                if (TextUtils.isEmpty(string)) {
                    if (DBG) {
                        Log.i(TAG, "clearWhiteList alias name not provided for Certificate access_type");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -16;
                }
                if (true != checkCredentialStorageAliasForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName, string)) {
                    if (DBG) {
                        Log.i(TAG, "clearWhiteList - alias not exist for credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
                str2 = string;
            } else {
                str2 = null;
            }
            if (true == clearWhiteListPackages(credentialStorage, i, i2, i3, str2)) {
                this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
                updateUcmCryptoProp();
                return 0;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean clearWhiteListPackages(CredentialStorage credentialStorage, int i, int i2, int i3, String str) {
        if (DBG) {
            Log.i(TAG, "clearWhiteListPackages is called...");
        }
        if (DBG) {
            Log.i(TAG, "adminId - " + i + " ContainerId - " + i2 + " Storage name - " + credentialStorage.name + " Storage Package - " + credentialStorage.packageName);
        }
        boolean z = false;
        if (str == null) {
            Log.i(TAG, "clearWhiteListPackages access_type-" + i3);
            try {
                z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3)});
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "clearWhiteListPackages - Exception delete" + e.getMessage());
                }
            }
        } else {
            Log.i(TAG, "removeWhiteListPackages access_type-" + i3 + " and alias-" + str);
            try {
                z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "accessType", "alias"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3), str});
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "clearWhiteListPackages - Exception delete" + e2.getMessage());
                }
            }
        }
        Log.i(TAG, "clearWhiteListPackages retcode-" + z);
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
        return z;
    }

    public CredentialStorage[] getCredentialStorages(ContextInfo contextInfo, String str) {
        Log.i(TAG, "getCredentialStorages is called....");
        validateContextInfo(contextInfo);
        if (str == null || str.length() == 0) {
            if (!DBG) {
                return null;
            }
            Log.i(TAG, "getCredentialStorages - Invalid Arguments");
            return null;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("getCredentialStorages");
            kAData.setProperty("packageName", str);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        HashMap hashMap = new HashMap();
        if (DBG) {
            Log.i(TAG, "getCredentialStorages is called for Caller UID-" + i + " mContainerId " + i2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"adminUid", "userId"}, new String[]{String.valueOf(i), String.valueOf(i2)}, new String[]{"storageName", "storagePackageName", "storageManufacture", "appPackage"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    Log.i(TAG, "getCredentialStorages - Size-" + dataByFields.size());
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        String asString = contentValues.getAsString("appPackage");
                        Log.i(TAG, "getCredentialStorages dbPackage-" + asString);
                        if (asString != null && (asString.equalsIgnoreCase(str) || asString.equalsIgnoreCase("*"))) {
                            Log.i(TAG, "getCredentialStorages match found...");
                            String asString2 = contentValues.getAsString("storageName");
                            String asString3 = contentValues.getAsString("storagePackageName");
                            String asString4 = contentValues.getAsString("storageManufacture");
                            if (!hashMap.containsKey(asString2 + "_" + asString3)) {
                                CredentialStorage credentialStorage = new CredentialStorage();
                                credentialStorage.name = asString2;
                                credentialStorage.packageName = asString3;
                                credentialStorage.manufacturer = asString4;
                                hashMap.put(asString2 + "_" + asString3, credentialStorage);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (hashMap.size() <= 0) {
                return null;
            }
            Log.i(TAG, "getCredentialStorages - hashList.size()" + hashMap.size());
            return (CredentialStorage[]) hashMap.values().toArray(new CredentialStorage[0]);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x01b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String[] getCertificateAliases(int i, CredentialStorage credentialStorage) {
        String[] strArr;
        int i2;
        ArrayList dataByFields;
        String str = "appPackage";
        checkCallerPermissionFor("getCertificateAliases");
        Log.i(TAG, "getCertificateAliases is called....");
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getCertificateAliases - Invalid Arguments");
            }
            return null;
        }
        int userId = UserHandle.getUserId(i);
        if (DBG) {
            Log.i(TAG, "getCertificateAliases is called for mContainerId " + userId);
        }
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "storageName", "storagePackageName", "accessType"}, new String[]{String.valueOf(userId), credentialStorage.name, credentialStorage.packageName, String.valueOf(104)}, new String[]{"adminUid", "alias", "appPackage"});
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                strArr = null;
            }
            if (dataByFields != null && dataByFields.size() > 0) {
                String[] packagesForUid = this.mPm.getPackagesForUid(i);
                if (packagesForUid != null) {
                    int length = packagesForUid.length;
                    for (i2 = 0; i2 < length; i2++) {
                        String str2 = packagesForUid[i2];
                        Log.i(TAG, "getCertificateAliases pkgName-" + str2);
                        Iterator it = dataByFields.iterator();
                        while (it.hasNext()) {
                            ContentValues contentValues = (ContentValues) it.next();
                            if (contentValues == null) {
                                Log.i(TAG, "value is null, continue...");
                            } else {
                                String asString = contentValues.getAsString(str);
                                int i3 = length;
                                String str3 = TAG;
                                String str4 = str;
                                StringBuilder sb = new StringBuilder();
                                ArrayList arrayList2 = dataByFields;
                                sb.append("getCertificateAliases dbPackage-");
                                sb.append(asString);
                                Log.i(str3, sb.toString());
                                if (asString != null && (asString.equalsIgnoreCase(str2) || asString.equalsIgnoreCase("*"))) {
                                    String asString2 = contentValues.getAsString("alias");
                                    Log.i(TAG, "getCertificateAliases alias found alias-" + asString2);
                                    Integer asInteger = contentValues.getAsInteger("adminUid");
                                    if (asInteger == null) {
                                        Log.i(TAG, "parsing error, continue...");
                                    } else if (isAdminLicenseActive(asInteger.intValue(), credentialStorage) && !arrayList.contains(asString2)) {
                                        arrayList.add(asString2);
                                    }
                                }
                                length = i3;
                                str = str4;
                                dataByFields = arrayList2;
                            }
                        }
                    }
                    strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    if (strArr != null) {
                        Log.i(TAG, "getCertificateAliases - aliases size -" + strArr.length);
                    }
                    return strArr;
                }
                Log.i(TAG, "getCertificateAliases no packages related to uid-" + i);
            } else {
                Log.i(TAG, "getCertificateAliases - No record found...");
            }
            strArr = null;
            if (strArr != null) {
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCredentialStorageEnabledForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "isCredentialStorageEnabledForLockType is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, null);
        try {
            KnoxAnalytics.log(getKAData("isCredentialStorageEnabledForLockType", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (DBG) {
            Log.i(TAG, "isCredentialStorageEnabledForLockType is called for Caller UID - " + i + ", mContainerId " + i2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isValidParam(credentialStorage)) {
                    if (checkCredentialStorageEnabledForLockTypebyAdmin(i, i2, credentialStorage)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } else if (DBG) {
                    Log.i(TAG, "isCredentialStorageEnabledForLockType Invalid credential storage object passed...");
                }
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isCredentialStorageEnabledForLockTypeAsUser(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageEnabledForLockTypeAsUser");
        Log.i(TAG, "isCredentialStorageEnabledForLockTypeAsUser is called....");
        if (DBG) {
            Log.i(TAG, "isCredentialStorageEnabledForLockTypeAsUser is called for userId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isValidParam(credentialStorage)) {
                    if (checkCredentialStorageEnabledForLockTypebyAdmin(-1, i, credentialStorage)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } else if (DBG) {
                    Log.i(TAG, "isCredentialStorageEnabledForLockTypeAsUser Invalid credential storage object passed...");
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int enableCredentialStorageForLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        Log.i(TAG, "enableCredentialStorageForLockType is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, credentialStorage);
        try {
            KnoxAnalyticsData kAData = getKAData("enableCredentialStorageForLockType", credentialStorage.packageName);
            kAData.setProperty("enable", z);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        String str = credentialStorage.signature;
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return -18;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return -13;
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i3 = -1;
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockType is called for Caller UID-" + i2 + " mContainerId " + i);
                }
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (!isValidParam(credentialStorage)) {
                if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockType Invalid credential storage object passed...");
                }
                return -1;
            }
            if (true != isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockType return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            Provider credentialStorageProvider = getCredentialStorageProvider(credentialStorage);
            if (credentialStorageProvider == null) {
                if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockType No matching managed Credential Storage found in Provider list");
                }
                return -13;
            }
            String property = credentialStorageProvider.getProperty("isGeneratePasswordAvailable");
            Log.i(TAG, "enableCredentialStorageForLockType isGeneratePasswordAvailable-" + property);
            if ("false".equals(property)) {
                if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockType Generate Password not supported by Provider");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -27;
            }
            if (DBG) {
                Log.i(TAG, "enableCredentialStorageForLockType Generate Password supported by Provider");
            }
            i3 = enableCredentialStorageForLockTypeInternal(i2, i, credentialStorage, z);
            return i3;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int enableCredentialStorageForLockTypeInternal(int i, int i2, CredentialStorage credentialStorage, boolean z) {
        try {
            if (checkCredentialStorageEnabledForLockTypebyAdmin(-1, i2, credentialStorage)) {
                if (checkCredentialStorageEnabledForLockTypebyAdmin(i, i2, credentialStorage)) {
                    if (z) {
                        Log.i(TAG, "enableCredentialStorageForLockTypeInternal record already exist...");
                        return 0;
                    }
                    if (removeCredentialStorageLockType(i, i2, credentialStorage)) {
                        return 0;
                    }
                } else if (z) {
                    Log.i(TAG, "enableCredentialStorageForLockTypeInternal record already exist for another admin. Enabling for current Admin too...");
                    if (addCredentialStorageLockType(i, i2, credentialStorage)) {
                        return 0;
                    }
                } else if (DBG) {
                    Log.i(TAG, "enableCredentialStorageForLockTypeInternal Credential storage not enabled as a Lock type by this admin..");
                }
            } else if (z) {
                Log.i(TAG, "enableCredentialStorageForLockTypeInternal enabling lock type for current Admin ...");
                if (addCredentialStorageLockType(i, i2, credentialStorage)) {
                    return 0;
                }
            } else if (DBG) {
                Log.i(TAG, "enableCredentialStorageForLockTypeInternal Credential storage not enabled as a Lock type by this admin..");
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
        return -1;
    }

    public final boolean checkCredentialStorageEnabledForLockTypebyAdmin(int i, int i2, CredentialStorage credentialStorage) {
        String str = credentialStorage.name;
        String str2 = credentialStorage.packageName;
        try {
            ContentValues contentValues = new ContentValues();
            if (i != -1) {
                contentValues.put("adminUid", Integer.valueOf(i));
            }
            contentValues.put("userId", Integer.valueOf(i2));
            if (str != null) {
                contentValues.put("storageName", str);
            }
            if (str2 != null) {
                contentValues.put("storagePackageName", str2);
            }
            if (this.mEdmStorageProvider.getCount("UniversalCredentialEnabledLockTypeTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "checkCredentialStorageEnabledForLockTypebyAdmin Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final boolean addCredentialStorageLockType(int i, int i2, CredentialStorage credentialStorage) {
        boolean z;
        String str = credentialStorage.name;
        String str2 = credentialStorage.packageName;
        if (DBG) {
            Log.i(TAG, "addCredentialStorageLockType is called...");
            Log.i(TAG, "addCredentialStorageLockType adminUid - " + i + " ContainerId - " + i2 + ", Storage Name- " + str + ", Storage Package name - " + str2);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            String str3 = credentialStorage.manufacturer;
            if (str3 != null && str3.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z = this.mEdmStorageProvider.putValuesNoUpdate("UniversalCredentialEnabledLockTypeTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "addCredentialStorageLockType retcode-" + z);
        return z;
    }

    public final boolean removeCredentialStorageLockType(int i, int i2, CredentialStorage credentialStorage) {
        String str = credentialStorage.name;
        String str2 = credentialStorage.packageName;
        if (DBG) {
            Log.i(TAG, "removeCredentialStorageLockType is called...");
            Log.i(TAG, "removeCredentialStorageLockType adminUid - " + i + " ContainerId - " + i2 + ", Storage Name- " + str + ", Storage Package name - " + str2);
        }
        boolean z = false;
        try {
            boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnabledLockTypeTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), String.valueOf(i2), str, str2});
            Log.i(TAG, "removeCredentialStorageLockType result - " + deleteDataByFields);
            z = deleteDataByFields;
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        Log.i(TAG, "removeCredentialStorageLockType retcode-" + z);
        return z;
    }

    public final CredentialStorage getEnforcedCredentialStorageFromDb(int i) {
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialEnforcedLockTypeTable", new String[]{"userId"}, new String[]{String.valueOf(i)}, new String[]{"storageName", "storagePackageName", "storageManufacture"});
        if (dataByFields == null || dataByFields.size() <= 0) {
            return null;
        }
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ((ContentValues) dataByFields.get(0)).getAsString("storageName");
        credentialStorage.packageName = ((ContentValues) dataByFields.get(0)).getAsString("storagePackageName");
        credentialStorage.manufacturer = ((ContentValues) dataByFields.get(0)).getAsString("storageManufacture");
        return credentialStorage;
    }

    public final int getAdminForEnforcedCredentialStorageFromDb(int i) {
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialEnforcedLockTypeTable", new String[]{"userId"}, new String[]{String.valueOf(i)}, new String[]{"adminUid"});
        if (dataByFields == null || dataByFields.size() <= 0) {
            return -1;
        }
        ContentValues contentValues = (ContentValues) dataByFields.get(0);
        if (contentValues == null) {
            Log.i(TAG, "parsing object error");
            return -1;
        }
        Integer asInteger = contentValues.getAsInteger("adminUid");
        if (asInteger == null) {
            Log.i(TAG, "parsing integer error");
            return -1;
        }
        return asInteger.intValue();
    }

    public CredentialStorage getEnforcedCredentialStorageForLockType(ContextInfo contextInfo) {
        Log.i(TAG, "getEnforcedCredentialStorageForLockType is called....");
        validateContextInfo(contextInfo);
        CredentialStorage credentialStorage = null;
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (DBG) {
            Log.i(TAG, "getEnforcedCredentialStorageForLockType is called for Caller UID-" + i + " mContainerId " + i2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                credentialStorage = getEnforcedCredentialStorageFromDb(i2);
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            return credentialStorage;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public CredentialStorage getEnforcedCredentialStorageForLockTypeAsUser(int i) {
        checkCallerPermissionFor("getEnforcedCredentialStorageForLockTypeAsUser");
        Log.i(TAG, "getEnforcedCredentialStorageForLockTypeAsUser is called....");
        if (DBG) {
            Log.i(TAG, "getEnforcedCredentialStorageForLockTypeAsUser is called for userId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return getEnforcedCredentialStorageFromDb(i);
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getAdminForEnforcedCredentialStorageAsUser(int i) {
        Log.i(TAG, "getAdminForEnforcedCredentialStorageAsUser is called....");
        if (DBG) {
            Log.i(TAG, "getAdminForEnforcedCredentialStorageAsUser is called for userId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return getAdminForEnforcedCredentialStorageFromDb(i);
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int enforceCredentialStorageAsLockType(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "enforceCredentialStorageAsLockType is called....");
        validateContextInfo(contextInfo);
        boolean z = bundle != null ? bundle.getBoolean("enforce_lock_type_direct_set", false) : false;
        try {
            KnoxAnalyticsData kAData = getKAData("enforceCredentialStorageAsLockType", credentialStorage.packageName);
            kAData.setProperty("bundleProp", !bundle.isEmpty());
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        if (credentialStorage != null) {
            String str = credentialStorage.signature;
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                return -13;
            }
        }
        int i = contextInfo.mContainerId;
        if (i == 0) {
            enforceActiveAdminPermission(contextInfo);
            Log.i(TAG, "enforceCredentialStorageAsLockType - Caller Must be Active Admin");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
        }
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i3 = -1;
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "enforceCredentialStorageAsLockType is called for Caller UID-" + i2 + " mContainerId " + i);
                }
                if (isValidParam(credentialStorage)) {
                    if (!isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                        if (DBG) {
                            Log.i(TAG, "enforceCredentialStorageAsLockType return false..");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -12;
                    }
                    Provider credentialStorageProvider = getCredentialStorageProvider(credentialStorage);
                    if (credentialStorageProvider == null) {
                        if (DBG) {
                            Log.i(TAG, "enforceCredentialStorageAsLockType No matching managed Credential Storage found in Provider list");
                        }
                        return -13;
                    }
                    String property = credentialStorageProvider.getProperty("isGeneratePasswordAvailable");
                    Log.i(TAG, "enforceCredentialStorageAsLockType isGeneratePasswordAvailable-" + property);
                    if ("false".equals(property)) {
                        if (DBG) {
                            Log.i(TAG, "enforceCredentialStorageAsLockType Generate Password not supported by Provider");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -27;
                    }
                    if (DBG) {
                        Log.i(TAG, "enforceCredentialStorageAsLockType Generate Password supported by Provider");
                    }
                } else {
                    if (credentialStorage != null) {
                        if (DBG) {
                            Log.i(TAG, "enforceCredentialStorageAsLockType Invalid credential storage object passed...");
                        }
                        return -1;
                    }
                    IUcmService ucmService = getUcmService();
                    if (ucmService != null) {
                        ucmService.removeEnforcedLockTypeNotification(i);
                        if (!checkCredentialStorageLockTypeEnforcedForAdmin(i2, i, null, null)) {
                            if (DBG) {
                                Log.i(TAG, "enforceCredentialStorageAsLockType Admin did not enforce any credential storage as Lock Type for this user");
                            }
                            return -1;
                        }
                    }
                }
                i3 = enforceCredentialStorageAsLockTypeInternal(i2, i, credentialStorage);
                if (i3 != 0 || credentialStorage == null) {
                    if (i3 == 10 && credentialStorage != null && z) {
                        Intent intent = new Intent("com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE");
                        intent.putExtra("CS_NAME", credentialStorage.name);
                        intent.putExtra("USER_ID", i);
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(i)));
                    }
                } else if (z) {
                    Intent intent2 = new Intent("com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE");
                    intent2.putExtra("CS_NAME", credentialStorage.name);
                    intent2.putExtra("USER_ID", i);
                    this.mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.getUserId(i)));
                } else {
                    IUcmService ucmService2 = getUcmService();
                    if (ucmService2 != null) {
                        ucmService2.showEnforcedLockTypeNotification(i, credentialStorage.name);
                    }
                }
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            return i3;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int enforceCredentialStorageAsLockTypeInternal(int i, int i2, CredentialStorage credentialStorage) {
        try {
            if (credentialStorage == null) {
                Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal cs is null so removing admin entry...");
                boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", new String[]{"adminUid", "userId"}, new String[]{String.valueOf(i), String.valueOf(i2)});
                Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal result-" + deleteDataByFields);
                return deleteDataByFields ? 0 : -1;
            }
            if (!checkCredentialStorageLockTypeEnforced(i2, credentialStorage.name, credentialStorage.packageName)) {
                return addOrUpdateEnforcedCredentialStorageLockType(i, i2, credentialStorage) ? 0 : -1;
            }
            if (checkCredentialStorageLockTypeEnforcedForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal record already exist...");
                return 10;
            }
            if (!checkCredentialStorageLockTypeEnforcedForAdmin(i, i2, null, null)) {
                Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal Credential storage is configured by some other admin");
                return -10;
            }
            Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal Another Credential storage is already configured by some other admin");
            return addOrUpdateEnforcedCredentialStorageLockType(i, i2, credentialStorage) ? 0 : -1;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return -1;
        }
    }

    public final boolean addOrUpdateEnforcedCredentialStorageLockType(int i, int i2, CredentialStorage credentialStorage) {
        if (DBG) {
            Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType is called...");
        }
        boolean z = false;
        if (credentialStorage == null) {
            if (DBG) {
                Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType - Invalid Arguments");
            }
            return false;
        }
        if (DBG) {
            Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType adminUid - " + i + " ContainerId - " + i2 + ", Storage Name- " + credentialStorage.name + ", Storage Package name - " + credentialStorage.packageName);
        }
        try {
            boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", new String[]{"adminUid", "userId"}, new String[]{String.valueOf(i), String.valueOf(i2)});
            Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType oldResult - " + deleteDataByFields);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            String str = credentialStorage.manufacturer;
            if (str != null && str.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z = this.mEdmStorageProvider.putValuesNoUpdate("UniversalCredentialEnforcedLockTypeTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType retcode-" + z);
        return z;
    }

    public final boolean checkCredentialStorageLockTypeEnforced(int i, String str, String str2) {
        if (DBG) {
            Log.i(TAG, "checkCredentialStorageLockTypeEnforced");
        }
        if (DBG) {
            Log.i(TAG, "checkCredentialStorageLockTypeEnforced UserId - " + i + ", storageName - " + str + " and storagePackageName-" + str2);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            if (this.mEdmStorageProvider.getCount("UniversalCredentialEnforcedLockTypeTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "checkCredentialStorageLockTypeEnforced Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final boolean checkCredentialStorageLockTypeEnforcedForAdmin(int i, int i2, String str, String str2) {
        if (DBG) {
            Log.i(TAG, "checkCredentialStorageLockTypeEnforcedForAdmin");
        }
        if (DBG) {
            Log.i(TAG, "AdminId - " + i + ", UserId - " + i2 + ", storageName - " + str + " and ");
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            if (str != null) {
                contentValues.put("storageName", str);
            }
            if (str2 != null) {
                contentValues.put("storagePackageName", str2);
            }
            if (this.mEdmStorageProvider.getCount("UniversalCredentialEnforcedLockTypeTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "checkCredentialStorageLockTypeEnforcedForAdmin Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public CredentialStorage getDefaultInstallStorage(ContextInfo contextInfo) {
        Log.i(TAG, "getDefaultInstallStorage is called....");
        validateContextInfo(contextInfo);
        CredentialStorage credentialStorage = null;
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (DBG) {
            Log.i(TAG, "getDefaultInstallStorage is called for Caller UID-" + i + " mContainerId " + i2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialDefaultInstallTable", new String[]{"userId"}, new String[]{String.valueOf(i2)}, new String[]{"storageName", "storagePackageName", "storageManufacture"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    CredentialStorage credentialStorage2 = new CredentialStorage();
                    try {
                        credentialStorage2.name = ((ContentValues) dataByFields.get(0)).getAsString("storageName");
                        credentialStorage2.packageName = ((ContentValues) dataByFields.get(0)).getAsString("storagePackageName");
                        credentialStorage2.manufacturer = ((ContentValues) dataByFields.get(0)).getAsString("storageManufacture");
                        credentialStorage = credentialStorage2;
                    } catch (Exception e) {
                        e = e;
                        credentialStorage = credentialStorage2;
                        if (DBG) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                        return credentialStorage;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            return credentialStorage;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public CredentialStorage getDefaultInstallStorageAsUser(int i) {
        checkCallerPermissionFor("getDefaultInstallStorageAsUser");
        Log.i(TAG, "getDefaultInstallStorageAsUser is called....");
        if (DBG) {
            Log.i(TAG, "getDefaultInstallStorageAsUser is called for userId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        CredentialStorage credentialStorage = null;
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialDefaultInstallTable", new String[]{"userId"}, new String[]{String.valueOf(i)}, new String[]{"storageName", "storagePackageName", "storageManufacture"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    CredentialStorage credentialStorage2 = new CredentialStorage();
                    try {
                        credentialStorage2.name = ((ContentValues) dataByFields.get(0)).getAsString("storageName");
                        credentialStorage2.packageName = ((ContentValues) dataByFields.get(0)).getAsString("storagePackageName");
                        credentialStorage2.manufacturer = ((ContentValues) dataByFields.get(0)).getAsString("storageManufacture");
                        credentialStorage = credentialStorage2;
                    } catch (Exception e) {
                        e = e;
                        credentialStorage = credentialStorage2;
                        if (DBG) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                        return credentialStorage;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            return credentialStorage;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setDefaultInstallStorage(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "setDefaultInstallStorage is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, credentialStorage);
        if (credentialStorage != null) {
            String str = credentialStorage.signature;
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                return -13;
            }
        }
        try {
            if (credentialStorage != null) {
                KnoxAnalytics.log(getKAData("setDefaultInstallStorage", credentialStorage.packageName));
            } else {
                KnoxAnalytics.log(getKAData("setDefaultInstallStorage", "null"));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "setDefaultInstallStorage is called for Caller UID-" + i2 + " mContainerId " + i);
                }
                if (i >= 10) {
                    int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
                    Log.i(TAG, "setDefaultInstallStorage container ownerUid - " + mUMContainerOwnerUid);
                    if (mUMContainerOwnerUid != i2) {
                        Log.i(TAG, "setDefaultInstallStorage callerUid - " + i2 + " is not owner of container. Request fail...");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -24;
                    }
                }
                if (isValidParam(credentialStorage)) {
                    if (true != isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                        if (DBG) {
                            Log.i(TAG, "setDefaultInstallStorage return false..");
                        }
                        return -12;
                    }
                } else if (!checkDefaultInstallCredentialStorageExistsForAdmin(i2, i, null, null)) {
                    if (DBG) {
                        Log.i(TAG, "setDefaultInstallStorage MDM don't own any credential storage...");
                    }
                    return -12;
                }
                return setDefaultInstallStorageInternal(i2, i, credentialStorage);
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setDefaultInstallStorageInternal(int i, int i2, CredentialStorage credentialStorage) {
        try {
            if (!isValidParam(credentialStorage)) {
                Log.i(TAG, "setDefaultInstallStorageInternal cs is null so removing admin entry...");
                boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", new String[]{"adminUid", "userId"}, new String[]{String.valueOf(i), String.valueOf(i2)});
                Log.i(TAG, "setDefaultInstallStorageInternal result-" + deleteDataByFields);
                return deleteDataByFields ? 0 : -1;
            }
            if (!checkDefaultInstallCredentialStorageExists(i2, credentialStorage.name, credentialStorage.packageName)) {
                return addOrUpdateDefaultInstallStorage(i, i2, credentialStorage) ? 0 : -1;
            }
            if (checkDefaultInstallCredentialStorageExistsForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName)) {
                Log.i(TAG, "configureSecureStorageInternal record already exist...");
                return 0;
            }
            Log.i(TAG, "setDefaultInstallStorageInternal Credential storage is configured by some other admin");
            return -10;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return -1;
        }
    }

    public final boolean addOrUpdateDefaultInstallStorage(int i, int i2, CredentialStorage credentialStorage) {
        if (DBG) {
            Log.i(TAG, "addOrUpdateDefaultInstallStorage is called...");
        }
        boolean z = false;
        if (credentialStorage == null) {
            if (DBG) {
                Log.i(TAG, "addOrUpdateDefaultInstallStorage - Invalid Arguments");
            }
            return false;
        }
        if (DBG) {
            Log.i(TAG, "addOrUpdateDefaultInstallStorage adminUid - " + i + " ContainerId - " + i2 + ", Storage Name- " + credentialStorage.name + ", Storage Package name - " + credentialStorage.packageName);
        }
        try {
            boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", new String[]{"adminUid", "userId"}, new String[]{String.valueOf(i), String.valueOf(i2)});
            Log.i(TAG, "addOrUpdateDefaultInstallStorage oldResult-" + deleteDataByFields);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            String str = credentialStorage.manufacturer;
            if (str != null && str.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z = this.mEdmStorageProvider.putValuesNoUpdate("UniversalCredentialDefaultInstallTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        Log.i(TAG, "addOrUpdateDefaultInstallStorage retcode-" + z);
        return z;
    }

    public int installCACertificate(ContextInfo contextInfo, byte[] bArr, String str, Bundle bundle) {
        Log.i(TAG, "installCACertificate is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("installCACertificate"));
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
            return -1;
        }
    }

    public int deleteCACertificate(ContextInfo contextInfo, String str) {
        Log.i(TAG, "deleteCACertificate is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("deleteCACertificate"));
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
            return -1;
        }
    }

    public boolean deleteCertificatesForStoragePackage(String str) {
        Log.i(TAG, "deleteAllDatabaseContent()");
        String[] strArr = {"storagePackageName"};
        String[] strArr2 = {str};
        return this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2) & this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", strArr, strArr2);
    }

    public String[] getCACertificateAliases(ContextInfo contextInfo, Bundle bundle) {
        Log.i(TAG, "getCACertificateAliases is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        return null;
    }

    public CACertificateInfo getCACertificate(ContextInfo contextInfo, String str) {
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("getCACertificate"));
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
            return null;
        }
    }

    public int installCertificateInternal(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, Bundle bundle, boolean z) {
        Log.i(TAG, "installCertificateInternal is called....");
        checkCallerPermissionFor("installCertificateInternal");
        return installCertificateMain(i, i2, credentialStorage, bArr, str, bundle != null ? bundle.getString("ucm_privatekey_password") : null, bundle, true, z, false);
    }

    public int installCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle) {
        boolean z;
        Log.i(TAG, "installCertificate is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (isCallerDelegated(userId, i, credentialStorage, 107)) {
            Log.i(TAG, "installCertificate caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        return installCertificateMain(i, userId, credentialStorage, bArr, str, str2, bundle, false, false, z);
    }

    public final int installCertificateMain(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3) {
        int i3;
        Log.i(TAG, "installCertificateMain is called....");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            e = e;
            i3 = -1;
        }
        try {
        } catch (Exception e2) {
            e = e2;
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return i3;
        }
        if (isValidParam(credentialStorage) && bArr != null && str != null && (z || str2 != null)) {
            if (DBG) {
                Log.i(TAG, "installCertificateMain is called for Caller UID-" + i + " mContainerId " + i2 + ", renew-" + z2);
            }
            String str3 = credentialStorage.signature;
            if (str3 != null && !validateSignature(credentialStorage.packageName, str3, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i, i2, credentialStorage.name, credentialStorage.packageName) && !z3) {
                if (DBG) {
                    Log.i(TAG, "installCertificateMain return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            if (true == existAliasInternal(i2, credentialStorage, str)) {
                if (DBG) {
                    Log.i(TAG, "alias already exist for credential storage...");
                }
                if (!z2) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -20;
                }
                if (DBG) {
                    Log.i(TAG, "It is special renew certificate flow...");
                }
            } else if (z2) {
                if (DBG) {
                    Log.i(TAG, "installCertificateMain invalid renew flow...");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -14;
            }
            int i4 = bundle != null ? bundle.getInt("ese_storage_option", -1) : -1;
            Log.i(TAG, "installCertificateMain storageOption-" + i4);
            boolean z4 = bundle != null ? bundle.getBoolean("allow_wifi", false) : false;
            try {
                KnoxAnalyticsData kAData = getKAData("installCertificate", credentialStorage.packageName);
                kAData.setProperty("certType", z4 ? "WIFI" : "VPN");
                KnoxAnalytics.log(kAData);
            } catch (Exception e3) {
                Log.e(TAG, "Exception = " + Log.getStackTraceString(e3));
            }
            if (z4 && i2 >= 10) {
                Log.i(TAG, "installCertificateMain wifi cert can't be installed for container");
                return -1;
            }
            i3 = -1;
            int installCertificateInProvider = installCertificateInProvider(credentialStorage, bArr, str, str2, bundle, i2, i, z, z2);
            if (installCertificateInProvider != 0) {
                if (DBG) {
                    Log.i(TAG, "installCertificateInProvider failed...");
                }
                return installCertificateInProvider;
            }
            if (true == insertOrUpdateCertificateProfile(credentialStorage, i, i2, str, z4)) {
                this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
                updateUcmCryptoProp();
                return 0;
            }
            return i3;
        }
        if (DBG) {
            Log.i(TAG, "installCertificateMain - Invalid Arguments");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return -11;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x007c, code lost:
    
        if (r9.length() == 0) goto L10;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0162 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x016a A[Catch: Exception -> 0x0183, TryCatch #3 {Exception -> 0x0183, blocks: (B:21:0x0164, B:23:0x016a, B:25:0x0170), top: B:20:0x0164 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int installCertificateInProvider(CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle, int i, int i2, boolean z, boolean z2) {
        int i3;
        IUcmService ucmService;
        Log.i(TAG, "installCertificateInProvider is called for userId-" + i + " and adminId-" + i2 + ", systemCalling-" + z);
        if (bundle == null) {
            bundle = new Bundle();
        }
        Log.i(TAG, "installCertificateInProvider setting extra values userId-" + i + " and adminId-" + i2);
        bundle.putInt("user_id", i);
        bundle.putInt("admin_id", i2);
        String build = new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(1).setUid(i2).setAlias(str).build();
        int i4 = -1;
        try {
        } catch (Exception e) {
            e = e;
        }
        if (z) {
            if (str2 != null) {
                try {
                } catch (Exception e2) {
                    e = e2;
                    z2 = false;
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                    i3 = -1;
                    if (!z2) {
                    }
                    Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
                    if (z2) {
                    }
                }
            }
            Log.i(TAG, "Inside systemCalling block...");
            if (bArr != null) {
                Log.i(TAG, "Calling installCertificate in UCS");
                bundle.putBoolean("renew", z2);
                byte[] byteArray = bundle.getByteArray("private_key");
                IUcmService ucmService2 = getUcmService();
                if (ucmService2 != null) {
                    Bundle installCertificate = ucmService2.installCertificate(build, byteArray, bArr, bundle);
                    z2 = installCertificate != null ? installCertificate.getBoolean("booleanresponse", false) : false;
                    if (installCertificate != null) {
                        i3 = installCertificate.getInt("errorresponse", -1);
                        if (!z2 && i3 == -1) {
                            try {
                                ucmService = getUcmService();
                                if (ucmService != null) {
                                    Bundle installCertificateIfSupported = ucmService.installCertificateIfSupported(build, bArr, str2, bundle);
                                    boolean z3 = installCertificateIfSupported != null ? installCertificateIfSupported.getBoolean("booleanresponse", false) : false;
                                    if (installCertificateIfSupported != null) {
                                        try {
                                            i4 = installCertificateIfSupported.getInt("errorresponse", -1);
                                        } catch (Exception e3) {
                                            e = e3;
                                            z2 = z3;
                                            Log.i(TAG, "The exception occurs " + e.getMessage());
                                            Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
                                            if (z2) {
                                            }
                                        }
                                    }
                                    z2 = z3;
                                    i3 = i4;
                                }
                            } catch (Exception e4) {
                                e = e4;
                            }
                        }
                        Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
                        if (z2) {
                            return i3;
                        }
                        return 0;
                    }
                    i3 = -1;
                    if (!z2) {
                        ucmService = getUcmService();
                        if (ucmService != null) {
                        }
                    }
                    Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
                    if (z2) {
                    }
                }
            } else {
                Log.i(TAG, "Certificate is null...");
            }
            z2 = false;
            i3 = -1;
            if (!z2) {
            }
            Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
            if (z2) {
            }
        }
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        keyStore.load(byteArrayInputStream, str2.toCharArray());
        byteArrayInputStream.close();
        Enumeration<String> aliases = keyStore.aliases();
        while (true) {
            if (!aliases.hasMoreElements()) {
                break;
            }
            String nextElement = aliases.nextElement();
            Key key = keyStore.getKey(nextElement, str2.toCharArray());
            if (key instanceof PrivateKey) {
                Certificate[] certificateChain = keyStore.getCertificateChain(nextElement);
                if (certificateChain != null && certificateChain.length != 0) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (Certificate certificate : certificateChain) {
                        byteArrayOutputStream.write(certificate.getEncoded());
                    }
                    byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                    bundle.putString("algorithm", key.getAlgorithm());
                    IUcmService ucmService3 = getUcmService();
                    if (ucmService3 != null) {
                        Bundle installCertificate2 = ucmService3.installCertificate(build, key.getEncoded(), byteArray2, bundle);
                        z2 = installCertificate2 != null ? installCertificate2.getBoolean("booleanresponse", false) : false;
                        if (installCertificate2 != null) {
                            i3 = installCertificate2.getInt("errorresponse", -1);
                        }
                    }
                }
                Log.i(TAG, "getCertificateChain empty");
                return -1;
            }
        }
        z2 = false;
        i3 = -1;
        if (!z2) {
        }
        Log.i(TAG, "status -" + z2 + " and errorCode-" + i3);
        if (z2) {
        }
    }

    public final boolean insertOrUpdateCertificateProfile(CredentialStorage credentialStorage, int i, int i2, String str, boolean z) {
        if (DBG) {
            Log.i(TAG, "insertOrUpdateCertificateProfile is called...");
        }
        boolean z2 = false;
        if (credentialStorage == null) {
            if (DBG) {
                Log.i(TAG, "insertOrUpdateCertificateProfile - Invalid Arguments");
            }
            return false;
        }
        if (DBG) {
            Log.i(TAG, "InstallerId - " + i + " ContainerId - " + i2 + " and alias-" + str + ", storage name -" + credentialStorage.name + ", storage package - " + credentialStorage.packageName);
        }
        String validString = getValidString(str);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            contentValues.put("alias", validString);
            contentValues.put("wifi", Integer.valueOf(z ? 1 : 0));
            if (this.mEdmStorageProvider.getCount("UniversalCredentialCertificateTable", contentValues) == 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
                z2 = this.mEdmStorageProvider.putValuesNoUpdate("UniversalCredentialCertificateTable", contentValues);
            } else {
                Log.i(TAG, "insertOrUpdateCertificateProfile - record already exist..");
                z2 = true;
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        Log.i(TAG, "retcode-" + z2);
        return z2;
    }

    public int deleteCertificateInternal(int i, int i2, CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "deleteCertificateInternal is called....");
        checkCallerPermissionFor("deleteCertificateInternal");
        return deleteCertificateMain(i, i2, credentialStorage, str);
    }

    public int deleteCertificate(ContextInfo contextInfo, CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "deleteCertificate is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (isCallerDelegated(i2, i, credentialStorage, 107)) {
            Log.i(TAG, "deleteCertificate caller is valid delegated app...");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
        }
        return deleteCertificateMain(i, i2, credentialStorage, str);
    }

    public final int deleteCertificateMain(int i, int i2, CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "deleteCertificateMain is called....");
        try {
            KnoxAnalytics.log(getKAData("deleteCertificate", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (isValidParam(credentialStorage) && str != null && str.length() != 0) {
                if (DBG) {
                    Log.i(TAG, "deleteCertificateMain is called for Caller UID-" + i + " mContainerId " + i2);
                }
                String str2 = credentialStorage.signature;
                if (str2 != null && !validateSignature(credentialStorage.packageName, str2, 0)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Plugin is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                String validString = getValidString(str);
                if (DBG) {
                    Log.i(TAG, "deleteCertificateMain userId-" + i2 + " and adminId-" + i);
                }
                if (true != checkCredentialStorageAliasForAdmin(i, i2, credentialStorage.name, credentialStorage.packageName, validString)) {
                    if (DBG) {
                        Log.i(TAG, "- alias not exist for credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
                int removeCertificatefromProvider = removeCertificatefromProvider(credentialStorage.name, credentialStorage.packageName, validString, i2, i);
                if (removeCertificatefromProvider != 0) {
                    if (DBG) {
                        Log.i(TAG, "deleteCertificateMain - removeCertificatefromProvider failed");
                    }
                    return removeCertificatefromProvider;
                }
                if (true == deleteCertificateUsingAdminId(i, i2, credentialStorage, validString)) {
                    if (DBG) {
                        Log.i(TAG, "- certificate deleted successfully...");
                    }
                    this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
                    updateUcmCryptoProp();
                    return 0;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
            if (DBG) {
                Log.i(TAG, "deleteCertificateMain - Invalid Arguments");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -11;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean deleteCertificateUsingAdminId(int i, int i2, CredentialStorage credentialStorage, String str) {
        if (DBG) {
            Log.i(TAG, "deleteCertificateUsingAdminId is called for alias-" + str);
        }
        boolean z = false;
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "alias"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, str});
            Log.i(TAG, "deleteCertificateUsingAdminId is successful for alias-" + str);
            if (z) {
                boolean clearWhiteListPackages = clearWhiteListPackages(credentialStorage, i, i2, 104, str);
                Log.i(TAG, "deleteCertificateUsingAdminId remove whitelist status-" + clearWhiteListPackages);
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "deleteCertificateUsingAdminId - Exception" + e.getMessage());
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int removeCertificatefromProvider(String str, String str2, String str3, int i, int i2) {
        boolean z;
        Log.i(TAG, "removeCertificatefromProvider is called for userId-" + i + " and adminId-" + i2 + " and alias-" + str3);
        int i3 = -1;
        try {
            int i4 = 1;
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", new String[]{"storageName", "storagePackageName", "alias", "userId", "adminUid"}, new String[]{str, str2, str3, Integer.toString(i), Integer.toString(i2)}, new String[]{"wifi"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                int i5 = 1;
                while (it.hasNext()) {
                    Integer asInteger = ((ContentValues) it.next()).getAsInteger("wifi");
                    if (asInteger != null && asInteger.intValue() == 1) {
                        i5 = 3;
                    }
                }
                i4 = i5;
            }
            String build = new UniversalCredentialUtil.UcmUriBuilder(str).setResourceId(i4).setUid(i2).setAlias(str3).build();
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                Bundle deleteCertificate = ucmService.deleteCertificate(build, i);
                z = deleteCertificate != null ? deleteCertificate.getBoolean("booleanresponse", false) : false;
                if (deleteCertificate != null) {
                    try {
                        i3 = deleteCertificate.getInt("errorresponse", -1);
                    } catch (Exception e) {
                        e = e;
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                        if (z) {
                        }
                    }
                }
                Log.i(TAG, "removeCertificatefromProvider : " + z + "=" + z + "; csUri=" + build);
            } else {
                z = false;
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
        if (z) {
            return i3;
        }
        return 0;
    }

    public String[] getAliases(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getAliases is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getAliases - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(getKAData("getAliases", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        if (isCallerDelegated(i2, i, credentialStorage, 107)) {
            Log.i(TAG, "getAliases caller is valid delegated app...");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
        }
        if (DBG) {
            Log.i(TAG, "getAliases is called for Caller UID-" + i + " mContainerId " + i2);
        }
        String str = credentialStorage.signature;
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return null;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return null;
        }
        return getAliasesInternal(i, i2, credentialStorage);
    }

    public final String[] getAliasesInternal(int i, int i2, CredentialStorage credentialStorage) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String[] strArr = null;
        try {
            try {
                int i3 = 0;
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName}, new String[]{"alias"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    strArr = new String[dataByFields.size()];
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        strArr[i3] = ((ContentValues) it.next()).getAsString("alias");
                        i3++;
                    }
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] getCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("getCertificateAliasesAsUser");
        Log.i(TAG, "getCertificateAliasesAsUser is called....");
        String[] strArr = null;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getCertificateAliasesAsUser - Invalid Arguments");
            }
            return null;
        }
        if (DBG) {
            Log.i(TAG, "getCertificateAliasesAsUser is called for mContainerId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", new String[]{"userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName}, new String[]{"adminUid", "alias"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        if (contentValues == null) {
                            Log.i(TAG, "value is null, continue...");
                        } else {
                            String asString = contentValues.getAsString("alias");
                            if (contentValues.getAsInteger("adminUid") == null) {
                                Log.i(TAG, "parsing error, continue...");
                            } else {
                                arrayList.add(asString);
                            }
                        }
                    }
                    strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] getWifiCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) {
        String[] strArr;
        boolean z;
        Log.i(TAG, "getWifiCertificateAliasesAsUser is called....");
        checkCallerPermissionFor("getWifiCertificateAliasesAsUser");
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getWifiCertificateAliasesAsUser - Invalid Arguments");
            }
            return null;
        }
        if (DBG) {
            Log.i(TAG, "getWifiCertificateAliasesAsUser is called for mContainerId " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean z2 = true;
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", new String[]{"userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName}, new String[]{"adminUid", "alias", "wifi"});
                if (dataByFields == null || dataByFields.size() <= 0) {
                    strArr = null;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        if (contentValues == null) {
                            Log.i(TAG, "value is null, continue...");
                        } else {
                            String asString = contentValues.getAsString("alias");
                            Integer asInteger = contentValues.getAsInteger("adminUid");
                            Integer asInteger2 = contentValues.getAsInteger("wifi");
                            if (asInteger != null && asInteger2 != null) {
                                int intValue = asInteger.intValue();
                                int intValue2 = asInteger2.intValue();
                                Log.i(TAG, "getWifiCertificateAliasesAsUser - isWifi :" + intValue2);
                                if (isAdminLicenseActive(intValue, credentialStorage)) {
                                    z = true;
                                    if (intValue2 == 1) {
                                        arrayList.add(asString);
                                    }
                                } else {
                                    z = true;
                                }
                                z2 = z;
                            }
                            z = z2;
                            Log.i(TAG, "parsing error, continue...");
                            z2 = z;
                        }
                    }
                    strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                strArr = null;
            }
            this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
            updateUcmCryptoProp();
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] getAllCertificateAliases(CredentialStorage credentialStorage) {
        checkCallerPermissionFor("getAllCertificateAliases");
        Log.i(TAG, "getAllCertificateAliases is called....");
        String[] strArr = null;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getAllCertificateAliases - Invalid Arguments");
            }
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", new String[]{"storageName", "storagePackageName"}, new String[]{credentialStorage.name, credentialStorage.packageName}, new String[]{"adminUid", "alias"});
                if (dataByFields != null && dataByFields.size() > 0) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        String asString = ((ContentValues) it.next()).getAsString("alias");
                        if (!arrayList.contains(asString)) {
                            arrayList.add(asString);
                        }
                    }
                    strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
            updateUcmCryptoProp();
            return strArr;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public String[] getSupportedAlgorithms(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getSupportedAlgorithms is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getSupportedAlgorithms - Invalid Arguments");
            }
            return null;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        try {
            KnoxAnalytics.log(getKAData("getSupportedAlgorithms", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        if (DBG) {
            Log.i(TAG, "getSupportedAlgorithms is called for Caller UID-" + contextInfo.mCallerUid + " mContainerId " + contextInfo.mContainerId);
        }
        String str = credentialStorage.signature;
        if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
            return null;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return null;
        }
        return getSupportedAlgorithmsInternal(credentialStorage);
    }

    public final String[] getSupportedAlgorithmsInternal(CredentialStorage credentialStorage) {
        Set<Provider.Service> services;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String[] strArr = null;
        try {
            try {
                if (getUniversalCredentialUtil() != null) {
                    Provider[] managedProviders = getManagedProviders();
                    if (managedProviders != null && managedProviders.length > 0) {
                        for (Provider provider : managedProviders) {
                            String name = provider.getName();
                            String property = provider.getProperty("packageName");
                            if (name != null && name.equals(credentialStorage.name) && property != null && property.equals(credentialStorage.packageName) && (services = provider.getServices()) != null && services.size() > 0) {
                                strArr = new String[services.size()];
                                Iterator<Provider.Service> it = services.iterator();
                                int i = 0;
                                while (it.hasNext()) {
                                    strArr[i] = it.next().getAlgorithm();
                                    i++;
                                }
                            }
                        }
                    } else {
                        Log.i(TAG, "getSupportedAlgorithmsInternal - UniversalCredentialUtil service returns no providers... ");
                    }
                } else {
                    Log.i(TAG, "getSupportedAlgorithmsInternal - UniversalCredentialUtil service is null.... ");
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle setCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        Log.i(TAG, "setPackageSetting is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "setCredentialStorageProperty - Invalid Arguments");
            }
            return null;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active..");
                return null;
            }
            if (DBG) {
                Log.i(TAG, "setCredentialStorageProperty is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            if (true != isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "setCredentialStorageProperty return false..");
                }
                return null;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                Log.i(TAG, "setCredentialStorageProperty - pass to agent...");
                return ucmService.setCredentialStorageProperty(i2, new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build(), bundle, i);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle getCredentialStorageProperty(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        Log.i(TAG, "getPackageSetting is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getCredentialStorageProperty - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(getKAData("getCredentialStorageProperty", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                return null;
            }
            if (DBG) {
                Log.i(TAG, "getCredentialStorageProperty is called for mCallerUid- " + i2 + " user- " + i);
            }
            if (true != isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "setPackageSetting return false..");
                }
                return null;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                Log.i(TAG, "getCredentialStorageProperty - pass to agent...");
                return ucmService.getCredentialStorageProperty(i2, new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build(), bundle, i);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        String str;
        Log.i(TAG, "setAuthType is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "setAuthType - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("setAuthType", credentialStorage.packageName);
            if (i == 100) {
                kAData.setProperty("authType", "LOCK");
            } else if (i == 105) {
                kAData.setProperty("authType", "NONE");
            }
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mContainerId;
        int i3 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (DBG) {
                Log.i(TAG, "setAuthType is called for Caller UID-" + i3 + " mContainerId " + contextInfo.mContainerId);
            }
            if (true != isCredentialStorageManagedInternal(i3, i2, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "setAuthType return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            if (!isValidAuthType(i)) {
                Log.i(TAG, "setAuthType - Invalid AUTH Type...");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -17;
            }
            if (setAuthTypeInternal(i3, i2, credentialStorage, i)) {
                Log.i(TAG, "setAuthTypeInternal is successful");
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setAuthTypeInternal(int i, int i2, CredentialStorage credentialStorage, int i3) {
        boolean z;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("storageAuthType", Integer.valueOf(i3));
            z = this.mEdmStorageProvider.putValues("UniversalCredentialInfoTable", contentValues2, contentValues);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "setAuthTypeInternal retcode-" + z);
        return z;
    }

    public int getAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getAuthType is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "getAuthType - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("getAuthType", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String str = credentialStorage.signature;
                if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Storage is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                if (DBG) {
                    Log.i(TAG, "getAuthType is called for Caller UID-" + i2 + " mContainerId " + i);
                }
                if (true == isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                    return getStorageAuthenticationType(i, credentialStorage);
                }
                if (DBG) {
                    Log.i(TAG, "getAuthType return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isValidExemptType(int i) {
        boolean z = i == 106;
        Log.i(TAG, "isValidExemptType type-" + i + " and status-" + z);
        return z;
    }

    public int addPackagesToExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List list) {
        Log.i(TAG, "addPackagesToExemptList is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage) || list == null) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "addPackagesToExemptList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("addPackagesToExemptList", credentialStorage.packageName);
            kAData.setProperty("authType", i == 106 ? "AUTH" : "OTHER");
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mCallerUid;
        int i3 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "addPackagesToExemptList is called for Caller UID-" + i2 + " mContainerId " + i3);
                }
                String str = credentialStorage.signature;
                if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Storage is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                if (true != isCredentialStorageManagedInternal(i2, i3, credentialStorage.name, credentialStorage.packageName)) {
                    if (DBG) {
                        Log.i(TAG, "addPackagesToExemptList return false..");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -12;
                }
                if (isValidExemptType(i)) {
                    return insertOrUpdateExemptPackages(credentialStorage, list, i2, i3, i);
                }
                Log.i(TAG, "addPackagesToExemptList - Invalid Exempt Type...");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -21;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int insertOrUpdateExemptPackages(CredentialStorage credentialStorage, List list, int i, int i2, int i3) {
        int i4;
        PackageInfo packageInfo;
        int i5;
        int i6 = i2;
        if (DBG) {
            Log.i(TAG, "insertOrUpdateExemptPackages is called...");
        }
        if (DBG) {
            Log.i(TAG, "adminId - " + i + " ContainerId - " + i6 + " Storage name - " + credentialStorage.name + " Storage Package - " + credentialStorage.packageName + ", exemptType-" + i3);
        }
        Log.i(TAG, "insertOrUpdateExemptPackages - Exempt app size -" + list.size());
        IPackageManager packageManager = AppGlobals.getPackageManager();
        Iterator it = list.iterator();
        int i7 = -1;
        while (it.hasNext()) {
            AppIdentity appIdentity = (AppIdentity) it.next();
            Log.i(TAG, "insertOrUpdateExemptPackages - pkg : " + appIdentity.getPackageName());
            if (appIdentity.getPackageName() != null && appIdentity.getPackageName().length() != 0) {
                try {
                    packageInfo = packageManager.getPackageInfo(appIdentity.getPackageName(), 64L, i6);
                } catch (Exception e) {
                    Log.i(TAG, Log.getStackTraceString(e));
                    packageInfo = null;
                }
                PackageInfo packageInfo2 = packageInfo;
                Log.i(TAG, "Package Info: " + packageInfo2);
                boolean z = packageInfo2 != null;
                if (appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0 && z) {
                    Signature[] convertStringToSignature = convertStringToSignature(appIdentity.getSignature());
                    if (convertStringToSignature == null) {
                        Log.i(TAG, "UniversalCredentialManagerPolicy passed String signature is invalid");
                    } else if (!compareSignatures(packageInfo2.signatures, convertStringToSignature)) {
                        Log.i(TAG, "Package is installed, and signature doesn't match. So return falure");
                    }
                    i4 = -18;
                    break;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(i));
                contentValues.put("userId", Integer.valueOf(i2));
                contentValues.put("storageName", credentialStorage.name);
                contentValues.put("storagePackageName", credentialStorage.packageName);
                contentValues.put("exemptType", Integer.valueOf(i3));
                IPackageManager iPackageManager = packageManager;
                if (z) {
                    try {
                        contentValues.put("appUid", Integer.valueOf(packageInfo2.applicationInfo.uid));
                    } catch (Exception e2) {
                        Log.i(TAG, "The exception occurs " + e2.getMessage());
                    }
                }
                contentValues.put("appPackage", appIdentity.getPackageName());
                if (appIdentity.getSignature() != null) {
                    contentValues.put("appSignature", appIdentity.getSignature());
                }
                String str = credentialStorage.manufacturer;
                if (str != null) {
                    contentValues.put("storageManufacture", str);
                }
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("adminUid", Integer.valueOf(i));
                contentValues2.put("userId", Integer.valueOf(i2));
                contentValues2.put("storageName", credentialStorage.name);
                contentValues2.put("storagePackageName", credentialStorage.packageName);
                contentValues2.put("appPackage", appIdentity.getPackageName());
                contentValues2.put("exemptType", Integer.valueOf(i3));
                try {
                } catch (Exception e3) {
                    if (DBG) {
                        Log.i(TAG, "The exception occurs " + e3.getMessage());
                    }
                    i5 = -1;
                }
                if (!this.mEdmStorageProvider.putValues("UniversalCredentialExemptTable", contentValues, contentValues2)) {
                    if (DBG) {
                        Log.i(TAG, "insertOrUpdateExemptPackages - DB operation failed");
                    }
                    i4 = -1;
                    break;
                }
                i5 = 0;
                if (z) {
                    try {
                        int i8 = packageInfo2.applicationInfo.uid;
                        if (!this.exemptedAppsCache.containsKey(Integer.valueOf(i8))) {
                            this.exemptedAppsCache.put(Integer.valueOf(i8), appIdentity.getPackageName());
                            Log.i(TAG, "Caching Exempt app id-" + i8 + ", packageName-" + appIdentity.getPackageName());
                        }
                    } catch (Exception e4) {
                        Log.i(TAG, "The exception occurs " + e4.getMessage());
                    }
                }
                i6 = i2;
                i7 = i5;
                packageManager = iPackageManager;
            }
        }
        i4 = i7;
        Log.i(TAG, "insertOrUpdateExemptPackages retcode-" + i4);
        return i4;
    }

    public int removePackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i, List list) {
        String str;
        Log.i(TAG, "removePackagesFromExemptList is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "removePackagesFromExemptList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("removePackagesFromExemptList", credentialStorage.packageName);
            kAData.setProperty("authType", i == 106 ? "AUTH" : "OTHER");
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mCallerUid;
        int i3 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "removePackagesFromExemptList is called for Caller UID-" + i2 + " mContainerId " + i3);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i2, i3, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "removePackagesFromExemptList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            if (!isValidExemptType(i)) {
                Log.i(TAG, "removePackagesFromExemptList - Invalid Exempt Type...");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -21;
            }
            if (removeExemptPackages(credentialStorage, list, i2, i3, i)) {
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeExemptPackages(CredentialStorage credentialStorage, List list, int i, int i2, int i3) {
        if (DBG) {
            Log.i(TAG, "removeExemptPackages is called...");
        }
        if (DBG) {
            Log.i(TAG, "adminId - " + i + " ContainerId - " + i2 + " Storage name - " + credentialStorage.name + " Storage Package - " + credentialStorage.packageName + ", type-" + i3);
        }
        boolean z = false;
        if (list != null && list.size() > 0) {
            Log.i(TAG, "removeExemptPackages - WhiteList app size -" + list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                AppIdentity appIdentity = (AppIdentity) it.next();
                Log.i(TAG, "removeExemptPackages - pkg : " + appIdentity.getPackageName());
                if (appIdentity.getPackageName() != null) {
                    Log.i(TAG, "removeExemptPackages exempt type-" + i3);
                    try {
                        z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "exemptType", "appPackage"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3), appIdentity.getPackageName()});
                        if (!z) {
                            Log.i(TAG, "removeExemptPackages - failed to remove record...");
                            break;
                        }
                        continue;
                    } catch (Exception e) {
                        if (DBG) {
                            Log.i(TAG, "removeExemptPackages - Exception delete" + e.getMessage());
                        }
                    }
                }
            }
        } else {
            Log.i(TAG, "removeExemptPackages clearing all packages....");
            try {
                z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "exemptType"}, new String[]{String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName, String.valueOf(i3)});
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                }
            }
        }
        Log.i(TAG, "removeExemptPackages retcode-" + z);
        return z;
    }

    public List getPackagesFromExemptList(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        String str;
        Log.i(TAG, "getPackagesFromExemptList is called....");
        validateContextInfo(contextInfo);
        ArrayList arrayList = null;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(TAG, "getPackagesFromExemptList - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("getPackagesFromExemptList", credentialStorage.packageName);
            kAData.setProperty("authType", i == 106 ? "AUTH" : "OTHER");
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mCallerUid;
        int i3 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromExemptList is called for Caller UID-" + i2 + " mContainerId " + i3 + ",type-" + i);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                e = e2;
            }
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                return null;
            }
            if (true != isCredentialStorageManagedInternal(i2, i3, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromExemptList return false..");
                }
                return null;
            }
            if (!isValidExemptType(i)) {
                Log.i(TAG, "getPackagesFromExemptList - Invalid Exempt Type...");
                return null;
            }
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialExemptTable", new String[]{"adminUid", "userId", "storageName", "storagePackageName", "exemptType"}, new String[]{String.valueOf(i2), String.valueOf(i3), credentialStorage.name, credentialStorage.packageName, String.valueOf(i)}, new String[]{"appPackage", "appSignature"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Log.i(TAG, "getPackagesFromExemptList - Size-" + dataByFields.size());
                ArrayList arrayList2 = new ArrayList();
                try {
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        AppIdentity appIdentity = new AppIdentity();
                        appIdentity.setPackageName(contentValues.getAsString("appPackage"));
                        appIdentity.setSignature(contentValues.getAsString("appSignature"));
                        arrayList2.add(appIdentity);
                    }
                    arrayList = arrayList2;
                } catch (Exception e3) {
                    e = e3;
                    arrayList = arrayList2;
                    if (DBG) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                    }
                    return arrayList;
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getPackagesFromExemptListAsUser(int i, CredentialStorage credentialStorage, int i2) {
        Log.i(TAG, "getPackagesFromExemptListAsUser is called....");
        ArrayList arrayList = null;
        try {
            if (!isValidParam(credentialStorage)) {
                if (DBG) {
                    Log.i(TAG, "getPackagesFromExemptListAsUser - Invalid Arguments");
                }
                return null;
            }
            if (DBG) {
                Log.i(TAG, "getPackagesFromExemptListAsUser is called for Container-" + i + ",type-" + i2);
            }
            if (!isValidExemptType(i2)) {
                Log.i(TAG, "getPackagesFromExemptListAsUser - Invalid Exempt Type...");
                return null;
            }
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialExemptTable", new String[]{"userId", "storageName", "storagePackageName", "exemptType"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName, String.valueOf(i2)}, new String[]{"adminUid", "appPackage", "appSignature"});
            if (dataByFields == null || dataByFields.size() <= 0) {
                return null;
            }
            Log.i(TAG, "getPackagesFromExemptListAsUser - Size-" + dataByFields.size());
            ArrayList arrayList2 = new ArrayList();
            try {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues == null) {
                        Log.i(TAG, "value is null, continue....");
                    } else {
                        AppIdentity appIdentity = new AppIdentity();
                        appIdentity.setPackageName(contentValues.getAsString("appPackage"));
                        appIdentity.setSignature(contentValues.getAsString("appSignature"));
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        if (asInteger == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else if (isAdminLicenseActive(asInteger.intValue(), credentialStorage)) {
                            arrayList2.add(appIdentity);
                        }
                    }
                }
                return arrayList2;
            } catch (Exception e) {
                e = e;
                arrayList = arrayList2;
                if (!DBG) {
                    return arrayList;
                }
                Log.i(TAG, "The exception occurs " + e.getMessage());
                return arrayList;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public boolean isPackageFromExemptList(int i, CredentialStorage credentialStorage, int i2) {
        PackageInfo packageInfo;
        checkCallerPermissionFor("isPackageFromExemptList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        z = false;
        z = false;
        try {
            try {
                List<AppIdentity> packagesFromExemptListAsUser = getPackagesFromExemptListAsUser(UserHandle.getUserId(i), credentialStorage, i2);
                if (packagesFromExemptListAsUser != null && packagesFromExemptListAsUser.size() > 0) {
                    String[] packagesForUid = this.mPm.getPackagesForUid(i);
                    if (i == 1010) {
                        packagesForUid = new String[]{"com.samsung.knox.virtual.wifi"};
                        Log.i(TAG, "isPackageFromExemptList WIFI special block...");
                    }
                    String[] strArr = packagesForUid;
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    if (strArr != null) {
                        boolean z2 = false;
                        for (String str : strArr) {
                            try {
                                for (AppIdentity appIdentity : packagesFromExemptListAsUser) {
                                    String packageName = appIdentity.getPackageName();
                                    String signature = appIdentity.getSignature();
                                    Log.i(TAG, "isPackageFromExemptList pkgName-" + str + " and DB packageName-" + packageName);
                                    if (str != null && packageName != null && packageName.equals(str)) {
                                        if (signature != null) {
                                            Log.i(TAG, "isPackageFromExemptList package matched. Now matching signature....");
                                            Signature[] convertStringToSignature = convertStringToSignature(signature);
                                            if (convertStringToSignature == null) {
                                                Log.i(TAG, "isPackageFromExemptList - failed to convert signature from db.");
                                            }
                                            try {
                                                packageInfo = packageManager.getPackageInfo(str, 64L, UserHandle.getUserId(i));
                                            } catch (Exception e) {
                                                Log.i(TAG, "isPackageFromExemptList exception - " + e);
                                                packageInfo = null;
                                            }
                                            if (convertStringToSignature != null && packageInfo != null && compareSignatures(packageInfo.signatures, convertStringToSignature)) {
                                                Log.i(TAG, "isPackageFromExemptList match found with signature matching...");
                                            } else {
                                                Log.i(TAG, "isPackageFromExemptList signature mismatch happened...Ignoring package");
                                            }
                                        } else {
                                            Log.i(TAG, "isPackageFromExemptList match found ...");
                                        }
                                        z2 = true;
                                        break;
                                    }
                                }
                                if (z2) {
                                    break;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                z = z2;
                                Log.i(TAG, "The exception occurs " + e.getMessage());
                                return z;
                            }
                        }
                        z = z2;
                    }
                } else {
                    Log.i(TAG, "isPackageFromExemptList returned empty/null whitelist");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e3) {
            e = e3;
        }
        return z;
    }

    public boolean notifyLicenseStatus(int i, String str) {
        checkCallerPermissionFor("notifyLicenseStatus");
        Log.i(TAG, "notifyLicenseStatus : event-" + i + ", packageName-" + str);
        IPackageManager packageManager = AppGlobals.getPackageManager();
        int callingUid = Binder.getCallingUid();
        int i2 = 0;
        try {
            if (i == 1) {
                Iterator it = this.expiredAdmins.entrySet().iterator();
                int i3 = -1;
                while (it.hasNext()) {
                    Integer num = (Integer) ((Map.Entry) it.next()).getKey();
                    Log.i(TAG, "EVENT_LICENSE_ACTIVATE -> expiredAdmins Admin ID = " + num);
                    String[] packagesForUid = this.mPm.getPackagesForUid(num.intValue());
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        int length = packagesForUid.length;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length) {
                                break;
                            }
                            if (packagesForUid[i4].equals(str)) {
                                Log.i(TAG, "admin license has renewed, admin-" + num + ", packageName-" + str);
                                i3 = num.intValue();
                                break;
                            }
                            i4++;
                        }
                    }
                }
                if (i3 == -1) {
                    return false;
                }
                IUcmService ucmService = getUcmService();
                if (ucmService != null) {
                    Log.i(TAG, "notifyChangeToPlugin is called for EVENT_LICENSE_ACTIVATE...");
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("adminUid", i3);
                        Iterator it2 = ((List) this.expiredAdmins.get(Integer.valueOf(i3))).iterator();
                        while (it2.hasNext()) {
                            ucmService.notifyChangeToPlugin(new UniversalCredentialUtil.UcmUriBuilder((String) it2.next()).build(), 14, bundle);
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "notifyChangeToPlugin Exception " + e);
                    }
                }
                this.expiredAdmins.remove(Integer.valueOf(i3));
                return false;
            }
            if (i != 2) {
                return false;
            }
            List<Integer> adminIdRelatedToStorage = getAdminIdRelatedToStorage(str);
            if (adminIdRelatedToStorage != null && adminIdRelatedToStorage.size() > 0) {
                if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE", str, UserHandle.getUserId(callingUid)) == 0) {
                    Log.i(TAG, "Plugin still have permission. Ignoring notification to MDM.");
                    return false;
                }
                if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT", str, UserHandle.getUserId(callingUid)) != 0 && packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", str, UserHandle.getUserId(callingUid)) != 0 && packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT", str, UserHandle.getUserId(callingUid)) != 0 && packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_MGMT", str, UserHandle.getUserId(callingUid)) != 0) {
                    for (Integer num2 : adminIdRelatedToStorage) {
                        Log.i(TAG, "notifyLicenseStatus expire to " + num2);
                        String[] packagesForUid2 = this.mPm.getPackagesForUid(num2.intValue());
                        if (packagesForUid2 == null) {
                            Log.i(TAG, "adminPkg is null, so continue...");
                        } else {
                            int length2 = packagesForUid2.length;
                            int i5 = i2;
                            while (i5 < length2) {
                                String str2 = packagesForUid2[i5];
                                Log.i(TAG, "Sending event update to package " + str2);
                                Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_NOTIFY_EVENT");
                                intent.setPackage(str2);
                                Bundle bundle2 = new Bundle();
                                bundle2.putInt("event_id", 2);
                                bundle2.putString("package_name", str);
                                intent.putExtras(bundle2);
                                try {
                                } catch (Exception e2) {
                                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                                }
                                if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT", str, UserHandle.getUserId(num2.intValue())) == 0) {
                                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num2.intValue())), "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
                                } else if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_MGMT", str, UserHandle.getUserId(num2.intValue())) == 0) {
                                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num2.intValue())), "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
                                } else {
                                    Log.i(TAG, "admin does not have proper UCM permission");
                                    i5++;
                                    i2 = 0;
                                }
                                Log.i(TAG, "notifyLicenseStatus expire done");
                                i5++;
                                i2 = 0;
                            }
                        }
                    }
                }
                Log.i(TAG, "caller still have permission. Ignoring it");
                return false;
            }
            Log.i(TAG, "No admin found related to package...");
            for (Integer num3 : this.adminIds) {
                String[] packagesForUid3 = this.mPm.getPackagesForUid(num3.intValue());
                if (packagesForUid3 != null && packagesForUid3.length > 0) {
                    int length3 = packagesForUid3.length;
                    int i6 = 0;
                    while (true) {
                        if (i6 >= length3) {
                            break;
                        }
                        if (packagesForUid3[i6].equals(str)) {
                            Log.i(TAG, "admin license has expired, admin-" + num3 + ", packageName-" + str);
                            processAdminLicenseExpiry(num3.intValue());
                            break;
                        }
                        i6++;
                    }
                }
            }
            return false;
        } catch (Exception e3) {
            Log.i(TAG, "The exception occurs " + e3.getMessage());
            return false;
        }
        Log.i(TAG, "The exception occurs " + e3.getMessage());
        return false;
    }

    public final void processAdminLicenseExpiry(int i) {
        List storagesRelatedToAdminId = getStoragesRelatedToAdminId(i);
        if (this.expiredAdmins.containsKey(Integer.valueOf(i)) || storagesRelatedToAdminId.size() <= 0) {
            return;
        }
        this.expiredAdmins.put(Integer.valueOf(i), storagesRelatedToAdminId);
        Log.i(TAG, "processAdminLicenseExpiry expired admin-" + i);
        IUcmService ucmService = getUcmService();
        if (ucmService != null) {
            Log.i(TAG, "processAdminLicenseExpiry is called for license expiry...");
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("adminUid", i);
                Iterator it = storagesRelatedToAdminId.iterator();
                while (it.hasNext()) {
                    ucmService.notifyChangeToPlugin(new UniversalCredentialUtil.UcmUriBuilder((String) it.next()).build(), 13, bundle);
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
    }

    public final boolean isAdminLicenseActive(int i, CredentialStorage credentialStorage) {
        Log.i(TAG, "isAdminLicenseActive Test adminId-" + i);
        try {
            if (this.expiredAdmins.containsKey(Integer.valueOf(i))) {
                Log.i(TAG, "isAdminLicenseActive - adminId " + i + " is expired admin");
                List list = (List) this.expiredAdmins.get(Integer.valueOf(i));
                if (list == null || !list.contains(credentialStorage.name)) {
                    return true;
                }
                Log.i(TAG, "isAdminLicenseActive - found the storage...");
                if (isSystemStorage(credentialStorage.packageName)) {
                    Log.i(TAG, "isAdminLicenseActive - Storage is system. Blocking access");
                } else {
                    Provider credentialStorageProvider = getCredentialStorageProvider(credentialStorage);
                    if (credentialStorageProvider == null) {
                        return true;
                    }
                    String property = credentialStorageProvider.getProperty("enforceManagement");
                    Log.i(TAG, "isAdminLicenseActive - enforceMgt :" + property);
                    if (!"true".equals(property)) {
                        return true;
                    }
                }
                return false;
            }
            Log.i(TAG, "isAdminLicenseActive - admin License is active");
            return true;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return true;
        }
    }

    public final List getAdminIdRelatedToStorage(String str) {
        Log.i(TAG, "getAdminIdRelatedToStorage stroragePackage-" + str);
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", new String[]{"storagePackageName"}, new String[]{str}, new String[]{"adminUid"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        if (asInteger == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            arrayList.add(Integer.valueOf(asInteger.intValue()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        return arrayList;
    }

    public final List getStoragesRelatedToAdminId(int i) {
        Log.i(TAG, "getStoragesRelatedToAdminId adminId-" + i);
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", new String[]{"adminUid"}, new String[]{String.valueOf(i)}, new String[]{"storageName"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ContentValues) it.next()).getAsString("storageName"));
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        return arrayList;
    }

    public final List getAllAdmins() {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", null, null, new String[]{"adminUid"});
        if (dataByFields != null) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger = contentValues.getAsInteger("adminUid");
                    if (asInteger == null) {
                        Log.i(TAG, "parsing error, continue...");
                    } else {
                        int intValue = asInteger.intValue();
                        if (!arrayList.contains(Integer.valueOf(intValue))) {
                            arrayList.add(Integer.valueOf(intValue));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final ArrayList getActivePlugin() {
        Log.i(TAG, "getActivePlugin ..");
        try {
            return this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", null, null, new String[]{"storagePackageName", "appUid"});
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return new ArrayList();
        }
    }

    public final List getAllUsers() {
        Log.i(TAG, "getAllUsers() is called...");
        ArrayList arrayList = new ArrayList();
        try {
            String[] strArr = {"userId"};
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialCertificateTable", null, null, strArr);
            if (dataByFields != null) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger = contentValues.getAsInteger("userId");
                        if (asInteger == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            int intValue = asInteger.intValue();
                            if (!arrayList.contains(Integer.valueOf(intValue))) {
                                arrayList.add(Integer.valueOf(intValue));
                            }
                        }
                    }
                }
            }
            ArrayList dataByFields2 = this.mEdmStorageProvider.getDataByFields("UniversalCredentialWhiteListTable", null, null, strArr);
            if (dataByFields2 != null) {
                Iterator it2 = dataByFields2.iterator();
                while (it2.hasNext()) {
                    ContentValues contentValues2 = (ContentValues) it2.next();
                    if (contentValues2 == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger2 = contentValues2.getAsInteger("userId");
                        if (asInteger2 == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            int intValue2 = asInteger2.intValue();
                            if (!arrayList.contains(Integer.valueOf(intValue2))) {
                                arrayList.add(Integer.valueOf(intValue2));
                            }
                        }
                    }
                }
            }
            ArrayList dataByFields3 = this.mEdmStorageProvider.getDataByFields("UniversalCredentialDefaultInstallTable", null, null, strArr);
            if (dataByFields3 != null) {
                Iterator it3 = dataByFields3.iterator();
                while (it3.hasNext()) {
                    ContentValues contentValues3 = (ContentValues) it3.next();
                    if (contentValues3 == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger3 = contentValues3.getAsInteger("userId");
                        if (asInteger3 == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            int intValue3 = asInteger3.intValue();
                            if (!arrayList.contains(Integer.valueOf(intValue3))) {
                                arrayList.add(Integer.valueOf(intValue3));
                            }
                        }
                    }
                }
            }
            ArrayList dataByFields4 = this.mEdmStorageProvider.getDataByFields("UniversalCredentialExemptTable", null, null, strArr);
            if (dataByFields4 != null) {
                Iterator it4 = dataByFields4.iterator();
                while (it4.hasNext()) {
                    ContentValues contentValues4 = (ContentValues) it4.next();
                    if (contentValues4 == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger4 = contentValues4.getAsInteger("userId");
                        if (asInteger4 == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            int intValue4 = asInteger4.intValue();
                            if (!arrayList.contains(Integer.valueOf(intValue4))) {
                                arrayList.add(Integer.valueOf(intValue4));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
        return arrayList;
    }

    public final String getValidString(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return null;
        }
    }

    public final boolean checkDefaultInstallCredentialStorageExists(int i, String str, String str2) {
        if (DBG) {
            Log.i(TAG, "checkDefaultInstallCredentialStorageExists");
        }
        if (DBG) {
            Log.i(TAG, "checkDefaultInstallCredentialStorageExists UserId - " + i + ", storageName - " + str + " and storagePackageName-" + str2);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            if (this.mEdmStorageProvider.getCount("UniversalCredentialDefaultInstallTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "checkDefaultInstallCredentialStorageExists Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final boolean checkDefaultInstallCredentialStorageExistsForAdmin(int i, int i2, String str, String str2) {
        if (DBG) {
            Log.i(TAG, "checkDefaultInstallCredentialStorageExistsForAdmin");
        }
        if (DBG) {
            Log.i(TAG, "AdminId - " + i + ", UserId - " + i2 + ", storageName - " + str + " and ");
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            if (str != null) {
                contentValues.put("storageName", str);
            }
            if (str2 != null) {
                contentValues.put("storagePackageName", str2);
            }
            if (this.mEdmStorageProvider.getCount("UniversalCredentialDefaultInstallTable", contentValues) <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(TAG, "checkDefaultInstallCredentialStorageExistsForAdmin Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final boolean isCredentialStorageManagedInternal(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        return isCredentialStorageManagedInternal(contextInfo.mCallerUid, contextInfo.mContainerId, credentialStorage.name, credentialStorage.packageName);
    }

    public final boolean isCredentialStorageManagedInternal(int i, int i2, String str, String str2) {
        if (DBG) {
            Log.i(TAG, "isCredentialStorageManagedInternal");
        }
        if (DBG) {
            Log.i(TAG, "UserId - " + i2 + ", storageName - " + str + " and storagePackageName-" + str2);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                if (i != -1) {
                    contentValues.put("adminUid", Integer.valueOf(i));
                }
                contentValues.put("userId", Integer.valueOf(i2));
                contentValues.put("storageName", str);
                contentValues.put("storagePackageName", str2);
                if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                    z = true;
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "isCredentialStorageManagedInternal - Exception" + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (DBG) {
                Log.i(TAG, "isCredentialStorageManagedInternal - status : " + z);
            }
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean existAliasInternal(int i, CredentialStorage credentialStorage, String str) {
        if (DBG) {
            Log.i(TAG, "existAliasInternal");
        }
        String[] certificateAliasesAsUser = getCertificateAliasesAsUser(i, credentialStorage);
        if (certificateAliasesAsUser == null) {
            if (DBG) {
                Log.i(TAG, "storedAliases is emapty");
            }
            return false;
        }
        for (String str2 : certificateAliasesAsUser) {
            if (str2 == null) {
                if (DBG) {
                    Log.i(TAG, "dbAlias is null");
                }
            } else if (true == str2.equals(str)) {
                if (DBG) {
                    Log.i(TAG, "exist alias : " + str);
                }
                return true;
            }
        }
        return false;
    }

    public final boolean checkCredentialStorageAliasForAdmin(int i, int i2, String str, String str2, String str3) {
        if (DBG) {
            Log.i(TAG, "checkCredentialStorageAliasForAdmin");
        }
        if (DBG) {
            Log.i(TAG, "AdminId - " + i + ", UserId - " + i2 + ", storageName - " + str);
        }
        try {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = str;
            credentialStorage.packageName = str2;
            String[] aliasesInternal = getAliasesInternal(i, i2, credentialStorage);
            if (aliasesInternal != null) {
                for (String str4 : aliasesInternal) {
                    if (str4.equalsIgnoreCase(str3)) {
                        Log.i(TAG, "checkCredentialStorageAliasForAdmin Alias exist...");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        return false;
    }

    public final List getAdminIdRelatedToStorageAsUser(int i, CredentialStorage credentialStorage) {
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", new String[]{"userId", "storageName", "storagePackageName"}, new String[]{String.valueOf(i), credentialStorage.name, credentialStorage.packageName}, new String[]{"adminUid"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues == null) {
                        Log.i(TAG, "value is null, continue...");
                    } else {
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        if (asInteger == null) {
                            Log.i(TAG, "parsing error, continue...");
                        } else {
                            arrayList.add(Integer.valueOf(asInteger.intValue()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        return arrayList;
    }

    public static boolean compareSignatures(Signature[] signatureArr, Signature[] signatureArr2) {
        if (signatureArr == null) {
            Log.i(TAG, "Signature s1 is null");
            return false;
        }
        if (signatureArr2 == null) {
            Log.i(TAG, "Signature s2 is null");
            return false;
        }
        HashSet hashSet = new HashSet();
        for (Signature signature : signatureArr) {
            hashSet.add(signature);
        }
        HashSet hashSet2 = new HashSet();
        for (Signature signature2 : signatureArr2) {
            hashSet2.add(signature2);
        }
        if (hashSet.equals(hashSet2)) {
            Log.i(TAG, "Signature match");
            return true;
        }
        Log.i(TAG, "Signature doesn't match");
        return false;
    }

    public final String convertSignatureToString(Signature[] signatureArr) {
        if (signatureArr == null) {
            return "";
        }
        try {
            if (signatureArr.length <= 0) {
                return "";
            }
            String[] strArr = new String[signatureArr.length];
            for (int i = 0; i < signatureArr.length; i++) {
                strArr[i] = signatureArr[i].toCharsString();
            }
            return TextUtils.join(",", strArr);
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
            return "";
        }
    }

    public final Signature[] convertStringToSignature(String str) {
        Signature[] signatureArr;
        if (str != null && !str.equals("")) {
            if (DBG) {
                Log.i(TAG, "convertStringToSignature providerList sig:" + str);
            }
            String[] split = TextUtils.split(str, ",");
            if (split == null || split.length <= 0) {
                signatureArr = null;
            } else {
                Log.i(TAG, "convertStringToSignature providerList sigStrings:" + split.length);
                signatureArr = new Signature[split.length];
                for (int i = 0; i < split.length; i++) {
                    String str2 = split[i];
                    if (str2 != null && str2.length() > 0) {
                        if (DBG) {
                            Log.i(TAG, "convertStringToSignature creating signatures : ----" + split[i] + "----");
                        }
                        try {
                            Signature signature = new Signature(split[i]);
                            if (DBG) {
                                Log.i(TAG, "convertStringToSignature signature :" + signature);
                            }
                            signatureArr[i] = signature;
                        } catch (Exception e) {
                            Log.i(TAG, "The exception occurs " + e.getMessage());
                        }
                    }
                }
            }
            if (signatureArr != null && signatureArr.length > 0) {
                Log.i(TAG, "convertStringToSignature SUCCESS");
                return signatureArr;
            }
        }
        return null;
    }

    public final void notifyToPlugin(int i, int i2, int i3, CredentialStorage credentialStorage) {
        Log.i(TAG, "notifyToPlugin eventId-" + i + ", adminUid-" + i2 + ", userId-" + i3);
        try {
            Bundle bundle = new Bundle();
            String build = new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).build();
            if (i != 10) {
                return;
            }
            bundle.putInt("adminUid", i2);
            bundle.putInt("userId", i3);
            String[] aliasesInternal = getAliasesInternal(i2, i3, credentialStorage);
            if (aliasesInternal != null && aliasesInternal.length > 0) {
                bundle.putStringArray("aliases", aliasesInternal);
            }
            processPackagesForPlugin(i2, i3, credentialStorage, bundle);
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                Log.i(TAG, "notifyChangeToPlugin is called for plugin unmanaged...");
                ucmService.notifyChangeToPlugin(build, 10, bundle);
            }
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
    }

    public final void notifyAdminUninstall(int i) {
        Log.i(TAG, "notifyAdminUninstall -> adminUid-" + i);
        try {
            ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("UniversalCredentialInfoTable", new String[]{"adminUid"}, new String[]{String.valueOf(i)}, new String[]{"userId", "storageName", "storagePackageName"});
            if (dataByFields == null || dataByFields.size() <= 0) {
                return;
            }
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger = contentValues.getAsInteger("userId");
                    String asString = contentValues.getAsString("storageName");
                    String asString2 = contentValues.getAsString("storagePackageName");
                    if (asInteger != null && asString != null && asString2 != null) {
                        int intValue = asInteger.intValue();
                        Log.i(TAG, "notifyAdminUninstall - userId-" + intValue + ", csName-" + asString + ", csPackage-" + asString2);
                        CredentialStorage credentialStorage = new CredentialStorage();
                        credentialStorage.name = asString;
                        credentialStorage.packageName = asString2;
                        notifyToPlugin(10, i, intValue, credentialStorage);
                    }
                    Log.i(TAG, "invalid parameters, continue...");
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
    }

    public final void performCredentialStorageCleanup(int i, int i2, CredentialStorage credentialStorage) {
        boolean z;
        String[] strArr = {"adminUid", "userId", "storageName", "storagePackageName"};
        String[] strArr2 = {String.valueOf(i), String.valueOf(i2), credentialStorage.name, credentialStorage.packageName};
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performCredentialStorageCleanup Clean certificate status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
        }
        Log.i(TAG, "performCredentialStorageCleanup WhiteList APP status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e3.getMessage());
            }
        }
        Log.i(TAG, "performCredentialStorageCleanup Default Install status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e4.getMessage());
            }
        }
        Log.i(TAG, "performCredentialStorageCleanup Default Install status-" + z);
        this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final void performWhitelistAppCleanup(int i, String str) {
        boolean z;
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", new String[]{"userId", "appPackage"}, new String[]{String.valueOf(i), str});
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performWhitelistAPpCleanup WhiteList APP status-" + z);
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final void performExemptedAppCleanup(int i, String str) {
        boolean z;
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", new String[]{"userId", "appPackage"}, new String[]{String.valueOf(i), str});
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performExemptedAppCleanup Exempted App status-" + z);
    }

    public final void performStorageCleanup(String str) {
        boolean z;
        String[] strArr = {"storagePackageName"};
        String[] strArr2 = {str};
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performStorageCleanup Clean certificate status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
        }
        Log.i(TAG, "performStorageCleanup WhiteList APP status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e3.getMessage());
            }
        }
        Log.i(TAG, "performStorageCleanup Default Install status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e4.getMessage());
            }
        }
        Log.i(TAG, "performStorageCleanup Certificate info status-" + z);
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e5.getMessage());
            }
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e6.getMessage());
            }
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e7.getMessage());
            }
        }
        this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final void performUserCleanup(int i) {
        boolean z;
        String[] strArr = {"userId"};
        String[] strArr2 = {String.valueOf(i)};
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performUserCleanup Clean certificate status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
        }
        Log.i(TAG, "performUserCleanup WhiteList APP status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e3.getMessage());
            }
        }
        Log.i(TAG, "performUserCleanup Default Install status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e4.getMessage());
            }
        }
        Log.i(TAG, "performUserCleanup Certificate info status-" + z);
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e5.getMessage());
            }
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCACertificateTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e6.getMessage());
            }
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e7.getMessage());
            }
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e8) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e8.getMessage());
            }
        }
        this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final void performAdminCleanup(int i) {
        boolean z;
        String[] strArr = {"adminUid"};
        String[] strArr2 = {String.valueOf(i)};
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            z = false;
        }
        Log.i(TAG, "performAdminCleanup Clean certificate status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup WhiteList APP status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e3.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup Default Install status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e4.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup Certificate info status-" + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e5.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup - Exempt apps status- " + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e6.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup - Enforce Lock Type status- " + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e7.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup - Enable Lock Type status- " + z);
        try {
            z = this.mEdmStorageProvider.deleteDataByFields("UniversalCredentialCACertificateTable", strArr, strArr2);
        } catch (Exception e8) {
            if (DBG) {
                Log.i(TAG, "The exception occurs " + e8.getMessage());
            }
        }
        Log.i(TAG, "performAdminCleanup - CA Cert status- " + z);
        this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final boolean isValidParam(CredentialStorage credentialStorage) {
        return (credentialStorage == null || TextUtils.isEmpty(credentialStorage.name) || TextUtils.isEmpty(credentialStorage.packageName)) ? false : true;
    }

    public int configureCredentialStorageForODESettings(ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String build;
        Log.i(TAG, "configureCredentialStorageForODESettings is called....");
        validateContextInfo(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(TAG, "configureCredentialStorageForODESettings - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("configureCredentialStorageForODESettings", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        if (i == 0) {
            enforceActiveAdminPermission(contextInfo);
            Log.i(TAG, "enforceCredentialStorageAsLockType - Caller Must be Active Admin");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
        }
        int i2 = contextInfo.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "configureCredentialStorageForODESettings is called for Caller UID-" + i2 + " mContainerId " + i);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
        }
        if (i != 0) {
            Log.i(TAG, "This API is only valid for User 0");
            return -1;
        }
        if (credentialStorage != null) {
            build = new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build();
            String str = credentialStorage.signature;
            if (str != null && !validateSignature(credentialStorage.packageName, str, 0)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true != isCredentialStorageManagedInternal(i2, i, credentialStorage.name, credentialStorage.packageName)) {
                if (DBG) {
                    Log.i(TAG, "configureCredentialStorageForODESettings return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
        } else {
            build = new UniversalCredentialUtil.UcmUriBuilder("reset").setResourceId(4).setUid(i2).build();
        }
        IUcmService ucmService = getUcmService();
        if (ucmService != null) {
            Log.i(TAG, "configureCredentialStorageForODESettings is called for plugin unmanaged...");
            return ucmService.configureODESettings(build, bundle, credentialStorage != null ? credentialStorage.signature : null);
        }
        return -1;
    }

    public Bundle getODESettingsConfiguration(ContextInfo contextInfo) {
        Log.i(TAG, "getODESettingsConfiguration is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(TAG, "getODESettingsConfiguration is called for Caller UID-" + i + " mContainerId " + i2);
                }
                IUcmService ucmService = getUcmService();
                if (ucmService != null) {
                    Log.i(TAG, "getODESettingsConfiguration is called for plugin unmanaged...");
                    return ucmService.getODESettingsConfiguration();
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showEnforcedLockTypeNotificationForAllUser() {
        Log.i(TAG, "showEnforcedLockTypeNotificationForAllUser ");
        Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            try {
                CredentialStorage enforcedCredentialStorageFromDb = getEnforcedCredentialStorageFromDb(i);
                IUcmService ucmService = getUcmService();
                if (enforcedCredentialStorageFromDb != null && ucmService != null) {
                    if (DBG) {
                        Log.i(TAG, "showEnforcedLockTypeNotificationForAllUser userId: " + i + ", cs.name: " + enforcedCredentialStorageFromDb.name);
                    }
                    if (!enforcedCredentialStorageFromDb.name.equalsIgnoreCase(ucmService.getKeyguardStorageForCurrentUser(i))) {
                        ucmService.showEnforcedLockTypeNotification(i, enforcedCredentialStorageFromDb.name);
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
    }

    public final void setKeyguardProperty() {
        String keyguardStorageForCurrentUser;
        try {
            IUcmService ucmService = getUcmService();
            if (ucmService == null || (keyguardStorageForCurrentUser = ucmService.getKeyguardStorageForCurrentUser(0)) == null || keyguardStorageForCurrentUser.isEmpty() || "none".equalsIgnoreCase(keyguardStorageForCurrentUser)) {
                return;
            }
            SystemProperties.set("persist.keyguard.ucs", "true");
        } catch (Exception e) {
            Log.i(TAG, "The exception occurs " + e.getMessage());
        }
    }

    public int initKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, Bundle bundle) {
        Log.i(TAG, "initKeyguardPin is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("initKeyguardPin", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "initKeyguardPin is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.initKeyguardPin(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build(), str, bundle));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int setKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMaximumRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("setKeyguardPinMaximumRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i2 = contextInfo.mContainerId;
        int i3 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "setKeyguardPinMaximumRetryCount is called for Caller UID-" + i3 + " mContainerId " + i2);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.setKeyguardPinMaximumRetryCount(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i3).build(), i));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int setKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMinimumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("setKeyguardPinMinimumLength", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i2 = contextInfo.mContainerId;
        int i3 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "setKeyguardPinMinimumLength is called for Caller UID-" + i3 + " mContainerId " + i2);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.setKeyguardPinMinimumLength(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i3).build(), i));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int setKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMaximumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("setKeyguardPinMaximumLength", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i2 = contextInfo.mContainerId;
        int i3 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "setKeyguardPinMaximumLength is called for Caller UID-" + i3 + " mContainerId " + i2);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.setKeyguardPinMaximumLength(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i3).build(), i));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int getKeyguardPinMaximumRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMaximumRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("getKeyguardPinMaximumRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "getKeyguardPinMaximumRetryCount is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.getKeyguardPinMaximumRetryCount(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build()));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int getKeyguardPinCurrentRetryCount(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinCurrentRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("getKeyguardPinCurrentRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "getKeyguardPinCurrentRetryCount is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.getKeyguardPinCurrentRetryCount(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build()));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int getKeyguardPinMinimumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMinimumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("getKeyguardPinMinimumLength", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "getKeyguardPinMinimumLength is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.getKeyguardPinMinimumLength(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build()));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int getKeyguardPinMaximumLength(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMaximumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("getKeyguardPinMaximumLength", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "getKeyguardPinMaximumLength is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.getKeyguardPinMaximumLength(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build()));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public int changeKeyguardPin(ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) {
        Log.i(TAG, "changeKeyguardPin is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("changeKeyguardPin", credentialStorage.packageName));
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e));
        }
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        try {
            if (DBG) {
                Log.i(TAG, "changeKeyguardPin is called for Caller UID-" + i2 + " mContainerId " + i);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService = getUcmService();
            if (ucmService != null) {
                return getReturnvalue(ucmService.changePin(new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(4).setUid(i2).build(), str, str2, false));
            }
            return -1;
        } catch (Exception e2) {
            Log.i(TAG, "The exception occurs " + e2.getMessage());
            return -1;
        }
    }

    public final int checkContext(ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        if (z) {
            Log.i(TAG, "Caller Must be Active Admin");
            enforceActiveAdminPermission(contextInfo);
        }
        if (isPluginActive(credentialStorage)) {
            return 0;
        }
        Log.i(TAG, "Plugin is not active");
        return -13;
    }

    public final int checkContext(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        return checkContext(contextInfo, credentialStorage, true);
    }

    public final int checkCS(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        try {
            if (isValidParam(credentialStorage)) {
                return !isCredentialStorageManagedInternal(contextInfo, credentialStorage) ? -12 : 0;
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public final void validateContextInfo(ContextInfo contextInfo) {
        if (contextInfo == null) {
            Log.e(TAG, "contextInfo is null");
            throw new SecurityException("Input parameter is not proper");
        }
        int callingUid = Binder.getCallingUid();
        if (contextInfo.mCallerUid == callingUid && contextInfo.mContainerId == UserHandle.getUserId(callingUid)) {
            return;
        }
        Log.e(TAG, "Invalid contextInfo");
        throw new SecurityException("Input parameter is not proper");
    }

    public final int getReturnvalue(Bundle bundle) {
        if (bundle.getInt("errorresponse", -1) == 0) {
            return bundle.getInt("intresponse", 0);
        }
        return bundle.getInt("errorresponse", -1);
    }
}
