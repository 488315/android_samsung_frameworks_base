package com.samsung.ucm.ucmservice;

import android.R;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.IPackageManager;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.ApduMessage;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.core.ucmRetParcelable;
import com.samsung.ucm.ucmservice.EFSProperties;
import com.samsung.ucm.ucmservice.UcmAgentWrapper;
import com.samsung.ucm.ucmservice.keystore.UcmSignHelper;
import com.samsung.ucm.ucmservice.keystore.UcmSignHelperFactory;
import com.samsung.ucm.ucmservice.security.UcmSecurityHelper;
import com.samsung.ucm.ucmservice.util.EsePluginDelegationHelper;
import com.sec.esecomm.EsecommAdapter;
import com.sec.esecomm.TARequest;
import com.sec.esecomm.TAResponse;
import com.skms.android.agent.CcmInterface;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class CredentialManagerService extends IUcmService.Stub {
    public static boolean DBG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static Context sContext = null;
    public final Object mAppletsInfoLock;
    public List mConfigAppletRequestIds;
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public boolean mEmergencyEnabled;
    public EsePluginDelegationHelper mEsePluginDelegationHelper;
    public Handler mHandler;
    public HandlerThread mHandlerthread;
    public boolean mIsFbeEnabled;
    public boolean mIsVoldCompleteNotified;
    public LockPatternUtils mLockPatternUtils;
    public NotificationManager mNotificationManager;
    public final ServiceConnection mOnLccmConnection;
    public BroadcastReceiver mOnNotiRemoveBroadcastReceiver;
    public BroadcastReceiver mPackageRemovedReceiver;
    public final HashMap mPersistentAppletInfo;
    public final AtomicFile mPersistentAppletInfoFile;
    public SemPersonaManager mPersona;
    public SemPersonaManager mPersonaManager;
    public IPackageManager mPm;
    public PolicyManager mPolicyManager;
    public BroadcastReceiver mRefreshReceiver;
    public UcmSecurityHelper mSecurityHelper;
    public final UcmSignHelperFactory mSignHelperFactory;
    public ICredentialManagerServiceSystemUICallback mSystemUICallback;
    public UniversalCredentialManagerService mUCMMDMService;
    public UcmErcomSpecific mUcmErcomSpecific;
    public UcmServiceAgentManager mUcmServiceAgentManager;

    public interface UcmVendorSpecific {
        ucmRetParcelable getDekForVold(String str, byte[] bArr);

        ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr);

        _DekData getDeks(String str);
    }

    public static native int registerHALService();

    public boolean isKeyChainGranted(String str, int i) {
        return true;
    }

    public final synchronized UniversalCredentialManagerService getUCMMDMService() {
        if (this.mUCMMDMService == null) {
            this.mUCMMDMService = (UniversalCredentialManagerService) ServiceManager.getService("knox_ucsm_policy");
        }
        return this.mUCMMDMService;
    }

    public CredentialManagerService(Context context) {
        this(new Injector(context));
    }

    public CredentialManagerService(Injector injector) {
        this.mSystemUICallback = null;
        this.mPersistentAppletInfoFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "appletsConfig.xml"));
        this.mUCMMDMService = null;
        this.mLockPatternUtils = null;
        this.mEdmStorageProvider = null;
        this.mPersona = null;
        this.mPersistentAppletInfo = new HashMap();
        Object obj = new Object();
        this.mAppletsInfoLock = obj;
        this.mPm = AppGlobals.getPackageManager();
        this.mIsFbeEnabled = false;
        this.mIsVoldCompleteNotified = false;
        this.mEmergencyEnabled = false;
        this.mOnLccmConnection = new ServiceConnection() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.4
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                int i;
                CcmInterface asInterface = CcmInterface.Stub.asInterface(iBinder);
                if (asInterface != null && EFSProperties.isAppletDeletionLccmScriptExist()) {
                    byte[] appletDeletionLccmScript = EFSProperties.getAppletDeletionLccmScript();
                    if (appletDeletionLccmScript != null) {
                        try {
                            i = asInterface.handleCcm(appletDeletionLccmScript, appletDeletionLccmScript.length);
                        } catch (RemoteException e) {
                            Log.e("UcmService", "handleCcm: Exception " + e.getMessage());
                            e.printStackTrace();
                            i = -1;
                        }
                        if (i == 0) {
                            Log.i("UcmService", "handleCcmRet: clearAppletInfo");
                            EFSProperties.clearAppletInfo();
                        }
                        Log.i("UcmService", "handleCcmRet: " + i);
                        return;
                    }
                    return;
                }
                Log.e("UcmService", "ccmInterface = null");
            }
        };
        this.mConfigAppletRequestIds = new ArrayList();
        this.mPackageRemovedReceiver = new BroadcastReceiver() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i("UcmService", "onReceive " + intent.getAction());
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra != -1) {
                    int userId = UserHandle.getUserId(intExtra);
                    Log.i("UcmService", "Package update in userId-" + userId + " and uid-" + intExtra);
                    if (userId == 0 && CredentialManagerService.this.isActivePlugin(intExtra)) {
                        CredentialManagerService.this.mHandler.sendEmptyMessage(1);
                    }
                }
            }
        };
        this.mRefreshReceiver = new BroadcastReceiver() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.i("UcmService", "inside mRefreshReceiver onReceive : " + action);
                if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                    try {
                        CredentialManagerService.this.refreshAgents();
                    } catch (Exception e) {
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                    }
                    try {
                        Log.i("UcmService", "registerHALService : " + CredentialManagerService.registerHALService());
                    } catch (Exception e2) {
                        Log.i("UcmService", "The exception occurs " + e2.getMessage());
                    }
                    try {
                        CredentialManagerService.this.runSefTestForEseCommTA();
                        Log.i("UcmService", "runSefTestForEseCommTA executed.");
                    } catch (Exception e3) {
                        Log.i("UcmService", "The exception occurs " + e3.getMessage());
                    }
                    try {
                        if (CredentialManagerService.this.isAppletPluginExist() || CredentialManagerService.this.isUcmDarEnabled()) {
                            return;
                        }
                        CredentialManagerService.this.runLccmScript();
                        return;
                    } catch (Exception e4) {
                        Log.e("UcmService", "The exception occurs " + e4.getMessage());
                        return;
                    }
                }
                if (SemEmergencyManager.isEmergencyMode(CredentialManagerService.this.mContext)) {
                    if (CredentialManagerService.this.mEmergencyEnabled) {
                        Log.i("UcmService", "Already UPSM is enabled nothing to do");
                        return;
                    } else {
                        CredentialManagerService.this.mEmergencyEnabled = true;
                        Log.i("UcmService", "Already UPSM disabled -> enabled");
                        return;
                    }
                }
                if (CredentialManagerService.this.mEmergencyEnabled) {
                    Log.i("UcmService", "Already UPSM enabled -> disabled");
                    CredentialManagerService.this.refreshAgentList(null);
                    CredentialManagerService.this.mEmergencyEnabled = false;
                }
            }
        };
        this.mPersonaManager = null;
        this.mUcmErcomSpecific = null;
        this.mOnNotiRemoveBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                String stringExtra = intent.getStringExtra("CS_NAME");
                CredentialManagerService.this.showEnforcedLockTypeNotificationIntenal(intent.getIntExtra("USER_ID", -1), stringExtra);
            }
        };
        this.mContext = injector.getApplicationContext();
        sContext = injector.getApplicationContext();
        this.mSignHelperFactory = injector.getUcmSignHelperFactory();
        UcmServiceODE.updateOdeStatus();
        this.mPolicyManager = injector.getPolicyManager();
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mEdmStorageProvider = new EdmStorageProvider(this.mContext);
        UcmSecurityHelper ucmSecurityHelper = injector.getUcmSecurityHelper();
        this.mSecurityHelper = ucmSecurityHelper;
        this.mUcmServiceAgentManager = injector.getUcmServiceAgentManager(ucmSecurityHelper);
        this.mEsePluginDelegationHelper = new EsePluginDelegationHelper();
        this.mIsFbeEnabled = SystemProperties.get("ro.crypto.type", "unknown").equals("file");
        HandlerThread handlerThread = new HandlerThread("CredentialManagerServiceThread");
        this.mHandlerthread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerthread.getLooper()) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    CredentialManagerService.this.refreshAgentList(null);
                    int i2 = message.arg1;
                    if (i2 != 0) {
                        CredentialManagerService.this.sendRefreshFinishIntent(i2);
                        return;
                    }
                    return;
                }
                if (i != 4) {
                    if (i != 5) {
                        if (i != 6) {
                            return;
                        }
                        CredentialManagerService.this.showODEProgressNotification();
                        return;
                    } else {
                        Log.i("UcmService", "MSG_REFRESH_APPLET_INFO is called...");
                        synchronized (CredentialManagerService.this.mAppletsInfoLock) {
                            CredentialManagerService.this.writePersistentAppletsInfoLocked();
                            CredentialManagerService.this.readPersistentAppletsInfoLocked();
                        }
                        return;
                    }
                }
                Bundle data = message.getData();
                String string = data.getString("packageName");
                String string2 = data.getString("status");
                int i3 = data.getInt("errorCode");
                Log.i("UcmService", "MSG_PACKAGE_LICENSE_UPDATE packageName-" + string + ",status-" + string2 + ", errorCode-" + i3);
                if (string2 != null && string2.equals("success") && i3 == 0) {
                    data.putInt("event", 1);
                    try {
                        Log.i("UcmService", "  notifyLicenseStatus Activate status- " + CredentialManagerService.this.getUCMMDMService().notifyLicenseStatus(1, string));
                    } catch (Exception e) {
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                    }
                } else if (i3 == 203 || i3 == 700 || i3 == 701) {
                    data.putInt("event", 2);
                    try {
                        Log.i("UcmService", "  notifyLicenseStatus expire status- " + CredentialManagerService.this.getUCMMDMService().notifyLicenseStatus(2, string));
                    } catch (Exception e2) {
                        Log.i("UcmService", "The exception occurs " + e2.getMessage());
                    }
                } else {
                    Log.i("UcmService", "skip network error case: " + i3);
                    return;
                }
                CredentialManagerService.this.refreshAgentList(data);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra("USER_ID", -1);
                Log.i("UcmService", "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                CredentialManagerService.this.enforceLockType(intExtra, intent.getStringExtra("CS_NAME"));
            }
        }, intentFilter, "com.samsung.android.knox.permission.KNOX_UCM_MGMT", null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        this.mContext.registerReceiver(this.mRefreshReceiver, intentFilter2, null, null);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (CredentialManagerService.DBG) {
                    Log.i("UcmService", "onReceive " + intent.getAction());
                }
                if (!CredentialManagerService.this.mIsFbeEnabled && UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                    CredentialManagerService.this.showODEProgressNotification();
                    EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
                    if (loadODEConfig != null) {
                        try {
                            if (loadODEConfig.version <= 1 || loadODEConfig.defaultLanguage == null) {
                                return;
                            }
                            loadODEConfig.defaultLanguage = null;
                            Log.i("UcmService", "checkUcmOdeDefaultLanguage. remove default language");
                            if (EFSProperties.saveODEConfig(loadODEConfig)) {
                                return;
                            }
                            Log.i("UcmService", "checkUcmOdeDefaultLanguage. failed to save ode prop");
                            return;
                        } catch (Exception e) {
                            Log.i("UcmService", "The exception occurs " + e.getMessage());
                            return;
                        }
                    }
                    return;
                }
                if (CredentialManagerService.this.mIsVoldCompleteNotified) {
                    CredentialManagerService.this.showODEProgressNotification();
                }
            }
        }, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        if (SemEmergencyManager.getInstance(this.mContext) != null && SemEmergencyManager.isEmergencyMode(this.mContext)) {
            Log.i("UcmService", "Already Emergency Status");
            this.mEmergencyEnabled = true;
        }
        synchronized (obj) {
            readPersistentAppletsInfoLocked();
        }
        if (this.mIsFbeEnabled) {
            Log.i("UcmService", "fbe is enabled");
            if (SystemProperties.get("persist.security.ucs", "none").equals("none") && UcmServiceODE.getOdeStatus() == 0) {
                deleteODEConfigInFileIfExist();
            }
            updateKeyguardConfig(getuseridforuid(Binder.getCallingUid()));
        } else {
            Log.i("UcmService", "fbe is not enabled");
            if (SystemProperties.get("ro.crypto.state", "none").equals("unencrypted") || SystemProperties.get("vold.decrypt", "none").equals("trigger_restart_framework") || SystemProperties.get("vold.decrypt", "none").equals("trigger_reset_main")) {
                if (SystemProperties.get("persist.security.ucs", "none").equals("none") && UcmServiceODE.getOdeStatus() == 0) {
                    deleteODEConfigInFileIfExist();
                }
                updateKeyguardConfig(getuseridforuid(Binder.getCallingUid()));
            }
        }
        registerPersonaObserver();
        SystemProperties.set("security.ucm_version", "1.11");
    }

    public final boolean isAppletPluginExist() {
        if (EFSProperties.isPluginUidStored()) {
            if (!EFSProperties.isAppletDeletionLccmScriptExist()) {
                EFSProperties.deleteStoredPluginUid();
                return false;
            }
            try {
                return this.mPm.getPackagesForUid(EFSProperties.getStoredPluginUid()) != null;
            } catch (RemoteException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
        return false;
    }

    public final boolean isUcmDarEnabled() {
        return UcmServiceODE.getOdeStatus() > 0;
    }

    public final void runLccmScript() {
        if (!this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.checkCallerPermissionFor("runLccmScript");
        }
        if (!EFSProperties.isAppletDeletionLccmScriptExist()) {
            Log.i("UcmService", "script is null");
        } else {
            new Thread(new Runnable() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CredentialManagerService.this.lambda$runLccmScript$0();
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLccmScript$0() {
        Intent intent = new Intent("com.skms.android.agent.CcmService").setPackage("com.skms.android.agent");
        int i = 0;
        do {
            try {
                boolean bindService = this.mContext.bindService(intent, this.mOnLccmConnection, 1);
                i++;
                Log.i("UcmService", "bindCcmService() bound = ; tries =" + i);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (bindService) {
                    return;
                }
            } catch (Exception e2) {
                Log.e("UcmService", "bindCcmService() exception " + e2.getMessage());
                e2.printStackTrace();
                return;
            }
        } while (i <= 3);
    }

    public void notifyPluginResult(Bundle bundle) {
        if (!getUCMMDMService().isCallerPackageManaged()) {
            throw new SecurityException("caller is not managed from EMM");
        }
        boolean z = false;
        if (bundle.getInt("status_code", 0) == 38) {
            try {
                z = getUCMMDMService().deleteCertificatesForStoragePackage(this.mPm.getNameForUid(Binder.getCallingUid()));
            } catch (RemoteException e) {
                Log.e("UcmService", "RemoteException retrieving package caller uid", e);
            }
            Log.i("UcmService", "Database compromised, table delete result=" + z);
        }
        if (bundle.getString("RESPONSE_DATA", "").equals("00000001") && this.mConfigAppletRequestIds.contains(Integer.valueOf(bundle.getInt("request_id", -1)))) {
            if (bundle.containsKey("bytearrayresponse")) {
                byte[] byteArray = bundle.getByteArray("bytearrayresponse");
                int i = bundle.getInt("adminUid", -1);
                if (byteArray != null && i != -1) {
                    Log.i("UcmService", "updateAppletStatus: lccmResult: " + EFSProperties.saveAppletDeletionLccmScript(byteArray) + ", storeUidResult: " + EFSProperties.savePluginUid(Binder.getCallingUid()));
                }
            } else if (EFSProperties.isPluginUidStored() || EFSProperties.isAppletDeletionLccmScriptExist()) {
                EFSProperties.clearAppletInfo();
            }
            List list = this.mConfigAppletRequestIds;
            list.remove(list.indexOf(Integer.valueOf(bundle.getInt("request_id", -1))));
        }
        getUCMMDMService().notifyUCMConfigStatus(bundle);
    }

    public class Injector {
        public final Context mContext;
        public final PolicyManager mPolicyManager;
        public UcmSecurityHelper mSecurityHelper;
        public UcmServiceAgentManager mUcmServiceAgentManager = null;

        public Injector(Context context) {
            this.mContext = context;
            this.mPolicyManager = new PolicyManager(context);
        }

        public Context getApplicationContext() {
            return this.mContext.getApplicationContext();
        }

        public PolicyManager getPolicyManager() {
            return this.mPolicyManager;
        }

        public UcmServiceAgentManager getUcmServiceAgentManager(UcmSecurityHelper ucmSecurityHelper) {
            if (this.mUcmServiceAgentManager == null) {
                this.mUcmServiceAgentManager = new UcmServiceAgentManager(this.mContext, ucmSecurityHelper);
            }
            return this.mUcmServiceAgentManager;
        }

        public UcmSignHelperFactory getUcmSignHelperFactory() {
            return UcmSignHelperFactory.getInstance();
        }

        public UcmSecurityHelper getUcmSecurityHelper() {
            if (this.mSecurityHelper == null) {
                this.mSecurityHelper = new UcmSecurityHelper(this.mContext);
            }
            return this.mSecurityHelper;
        }
    }

    public void systemReady() {
        Log.i("UcmService", "systemReady is called...");
        this.mUcmServiceAgentManager.systemReady(this.mEdmStorageProvider);
        this.mHandler.sendEmptyMessage(1);
        if (this.mIsFbeEnabled) {
            return;
        }
        showODEProgressNotification();
    }

    public final void registerPackageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (!"com.samsung.ucs.agent.ese".equals(ucmAgentWrapper.info.packageName)) {
                intentFilter.addDataSchemeSpecificPart(ucmAgentWrapper.info.packageName, 0);
            }
        }
        this.mContext.registerReceiver(this.mPackageRemovedReceiver, intentFilter, null, this.mHandler);
    }

    public final boolean isGoodBinaryForEseCommTASelfTest() {
        return "1".equals(SystemProperties.get("ro.config.tima", "0")) && "eng".equals(SystemProperties.get("ro.build.type")) && "false".equals(SystemProperties.get("ro.product_ship"));
    }

    public final boolean isGoodDeviceForEseCommTA() {
        Log.i("UcmService", "isGoodDeviceForEseCommTA: false");
        return false;
    }

    public final boolean isGoodConfigForEseCommTA() {
        boolean equals = "1".equals(SystemProperties.get("nfc.product.support.ese", "0"));
        boolean isUCMODEEnabledWithPropFile = UcmServiceODE.isUCMODEEnabledWithPropFile();
        boolean z = false;
        if (!isUCMODEEnabledWithPropFile && (equals || this.mIsFbeEnabled)) {
            z = true;
        }
        Log.i("UcmService", "isGoodConfigForEseCommTA: " + z + " <- " + equals + "/" + isUCMODEEnabledWithPropFile + "/" + this.mIsFbeEnabled);
        return z;
    }

    public final void runSefTestForEseCommTA() {
        if (!isGoodBinaryForEseCommTASelfTest()) {
            Log.i("UcmService", "runSefTestForEseCommTA: isGoodBinaryForEseCommTASelfTest: false");
            return;
        }
        if (!isGoodDeviceForEseCommTA()) {
            Log.i("UcmService", "runSefTestForEseCommTA: isGoodDeviceForEseCommTA: false");
            return;
        }
        if (!isGoodConfigForEseCommTA()) {
            Log.i("UcmService", "runSefTestForEseCommTA: isGoodConfigForEseCommTA: false");
            return;
        }
        try {
            new Thread() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.6
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
                        int nextInt = new Random().nextInt(2000000000);
                        Log.i("UcmService", "SelfTestForEse:SH:" + nextInt);
                        int tzAccessTest = esecommAdapter.tzAccessTest();
                        int i = tzAccessTest >= 0 ? 0 : -1;
                        Log.i("UcmService", "runSefTestForEseCommTA: result: " + tzAccessTest);
                        Log.i("UcmService", "SelfTestForEse:EH:" + nextInt + XmlUtils.STRING_ARRAY_SEPARATOR + i);
                    } catch (Exception e) {
                        Log.i("UcmService", "Exception" + e.getMessage());
                    }
                }
            }.start();
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public final boolean isActivePlugin(int i) {
        if (this.mUcmServiceAgentManager.getActiveAgentList().isEmpty()) {
            Log.i("UcmService", "No active agent exist");
            return false;
        }
        Iterator it = this.mUcmServiceAgentManager.getActiveAgentList().iterator();
        while (it.hasNext()) {
            if (((UcmAgentWrapper) it.next()).info.serviceUid == i) {
                Log.i("UcmService", "it is active plugin uid : " + i);
                return true;
            }
        }
        return false;
    }

    public final SemPersonaManager getPersonaService() {
        if (this.mPersona == null) {
            this.mPersona = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.mPersona;
    }

    public final void registerPersonaObserver() {
        try {
            if (getPersonaService() != null) {
                Log.i("UcmService", "registerPersonaObserver is called...");
                getPersonaService().registerSystemPersonaObserver(new ISystemPersonaObserver.Stub() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.8
                    public void onKnoxContainerLaunch(int i) {
                    }

                    public void onPersonaActive(int i) {
                    }

                    public void onRemovePersona(int i) {
                    }

                    public void onResetPersona(int i) {
                    }

                    public void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2) {
                        Log.i("UcmService", "inside onstatechange " + i + " new " + semPersonaState2 + " old " + semPersonaState);
                        try {
                            if (semPersonaState2.equals(SemPersonaState.DELETING)) {
                                CredentialManagerService.this.updateMDMPolicies(i);
                            }
                        } catch (Exception e) {
                            Log.i("UcmService", "The exception occurs " + e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public final boolean isValidVendorName(String str) {
        return (str == null || str.isEmpty() || str.equalsIgnoreCase("none")) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMDMPolicies(int i) {
        int parseInt;
        Log.i("UcmService", "updateMDMPolicies userId : " + i);
        String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i);
        if (!isValidVendorName(keyguardStorageForCurrentUser)) {
            Log.i("UcmService", "UCM keyguard is not enabled");
            return;
        }
        Log.i("UcmService", "UCM keyguard is enabled : " + keyguardStorageForCurrentUser);
        String keyguardStorageOwnerForCurrentUser = getKeyguardStorageOwnerForCurrentUser(i);
        if (keyguardStorageOwnerForCurrentUser != null) {
            try {
                parseInt = Integer.parseInt(keyguardStorageOwnerForCurrentUser);
            } catch (NumberFormatException e) {
                Log.i("UcmService", "NumberFormatException : " + e.getMessage());
            }
            if (parseInt != -1) {
                Log.i("UcmService", "UCM Keyguard parsing failed");
                return;
            } else {
                if (isPluginUsedInOtherUser(keyguardStorageForCurrentUser, i, parseInt)) {
                    return;
                }
                removeMDMPolicies(getStoragePkgname(keyguardStorageForCurrentUser), parseInt);
                return;
            }
        }
        parseInt = -1;
        if (parseInt != -1) {
        }
    }

    public final void removeMDMPolicies(String str, int i) {
        Log.i("UcmService", "removeMDMPolicies");
        if (this.mContext == null || str == null || str.isEmpty() || i == -1) {
            Log.i("UcmService", "invalid argument");
            return;
        }
        Log.i("UcmService", "PackageName : " + str + ", admin UID : " + i);
        ApplicationPolicy applicationPolicy = new EnterpriseDeviceManager(this.mContext, new ContextInfo(i), (Handler) null).getApplicationPolicy();
        if (applicationPolicy == null) {
            Log.i("UcmService", "Failed to get APP Policy");
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        Log.i("UcmService", "removeMDMPolicies removePackagesFromForceStopBlackList status-" + applicationPolicy.removePackagesFromForceStopBlackList(arrayList));
        Log.i("UcmService", "removeMDMPolicies removePackagesFromClearCacheBlackList status-" + applicationPolicy.removePackagesFromClearCacheBlackList(arrayList));
        Log.i("UcmService", "removeMDMPolicies removePackagesFromClearDataBlackList status-" + applicationPolicy.removePackagesFromClearDataBlackList(arrayList));
        applicationPolicy.setApplicationUninstallationEnabled(str);
    }

    public final boolean isPluginUsedInOtherUser(String str, int i, int i2) {
        return isPluginUsedInOtherUser(str, i, i2, true);
    }

    public final boolean isPluginUsedInOtherUser(String str, int i, int i2, boolean z) {
        List<UserInfo> users;
        Log.i("UcmService", "isPluginUsedInOtherUser");
        if (str != null && i != -1 && i2 != -1) {
            try {
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                if (userManager == null || (users = userManager.getUsers()) == null) {
                    return false;
                }
                for (UserInfo userInfo : users) {
                    if (userInfo != null) {
                        Log.i("UcmService", "UCM keyguard check " + userInfo.id);
                        int i3 = userInfo.id;
                        if (i == i3) {
                            Log.i("UcmService", "skip current user");
                        } else {
                            String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i3);
                            if (!isValidVendorName(keyguardStorageForCurrentUser)) {
                                Log.i("UcmService", "UCM keyguard is not enabled");
                            } else if (keyguardStorageForCurrentUser.equals(str)) {
                                Log.i("UcmService", "this plugin is used in other user");
                                if (!z) {
                                    Log.i("UcmService", "skip check configurator. pluginUsedInOtherUser");
                                    return true;
                                }
                                try {
                                    if (i2 == Integer.parseInt(getKeyguardStorageOwnerForCurrentUser(userInfo.id))) {
                                        Log.i("UcmService", "And it is enabled by same configurator");
                                        return true;
                                    }
                                } catch (NumberFormatException unused) {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
        return false;
    }

    public void refreshAgents() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final void refreshAgentList(Bundle bundle) {
        this.mUcmServiceAgentManager.refreshAgentList(null);
        if (this.mUcmServiceAgentManager.getActiveAgentList().isEmpty()) {
            return;
        }
        registerPackageReceiver();
    }

    public final boolean isValidFormFactor(String str) {
        boolean z = false;
        if (str != null) {
            switch (str) {
                case "SD":
                case "SD1":
                case "SIM":
                case "eSE":
                case "SIM1":
                case "SIM2":
                case "eSE1":
                case "reset":
                    z = true;
                    break;
            }
        }
        Log.i("UcmService", "isValidFormFactor type-" + str + " and status-" + z);
        return z;
    }

    public final AppletProperties getAppletInfo(String str) {
        AppletProperties appletProperties;
        Log.i("UcmService", "getAppletInfo is called for pluginName-" + str);
        if (!this.mPersistentAppletInfo.containsKey(str) || (appletProperties = (AppletProperties) this.mPersistentAppletInfo.get(str)) == null) {
            return null;
        }
        Log.i("UcmService", "getAppletInfo pluginName-" + appletProperties.pluginName);
        return appletProperties;
    }

    public final boolean processAdminConfigRequest(int i, String str, Bundle bundle) {
        Log.i("UcmService", "processAdminConfigRequest is called...adminId-" + i + ", uri-" + str);
        if (bundle == null) {
            return true;
        }
        try {
            String source = UniversalCredentialUtil.getSource(str);
            String string = bundle.getString("applet_location");
            byte[] byteArray = bundle.getByteArray("applet_id");
            Log.i("UcmService", "processAdminConfigRequest is called...appletLocation-" + string + ", pluginName-" + source);
            if (!isValidFormFactor(string)) {
                if (string == null) {
                    return true;
                }
                Log.i("UcmService", "processAdminConfigRequest not valid form factor");
                return false;
            }
            if (string.equals("reset")) {
                if (this.mPersistentAppletInfo.containsKey(source)) {
                    this.mPersistentAppletInfo.remove(source);
                    Log.i("UcmService", "processAdminConfigRequest removed pluginName-" + source);
                }
            } else {
                if (this.mPersistentAppletInfo.containsKey(source)) {
                    this.mPersistentAppletInfo.remove(source);
                }
                AppletProperties appletProperties = new AppletProperties(string, byteArray, source, i);
                if (!this.mPersistentAppletInfo.containsKey(source)) {
                    Log.i("UcmService", "processAdminConfigRequest added pluginName-" + source);
                    this.mPersistentAppletInfo.put(source, appletProperties);
                }
            }
            this.mHandler.sendEmptyMessage(5);
            return true;
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return true;
        }
    }

    public class AppletProperties {
        public int adminId;
        public byte[] aid;
        public String appletLocation;
        public String pluginName;

        public AppletProperties(String str, byte[] bArr, String str2, int i) {
            this.appletLocation = str;
            this.aid = bArr;
            this.pluginName = str2;
            this.adminId = i;
        }
    }

    public final String convertByteToString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (bArr == null) {
            return null;
        }
        for (byte b : bArr) {
            stringBuffer.append(String.format("%02X", Byte.valueOf(b)));
        }
        String stringBuffer2 = stringBuffer.toString();
        Log.i("UcmService", "convertByteToString result - " + stringBuffer2);
        return stringBuffer2;
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public final void writePersistentAppletsInfoLocked() {
        Log.i("UcmService", "writePersistentAppletsInfoLocked is called...");
        try {
            FileOutputStream startWrite = this.mPersistentAppletInfoFile.startWrite();
            try {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, "utf-8");
                fastXmlSerializer.startDocument(null, Boolean.TRUE);
                fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag(null, "applets");
                for (Map.Entry entry : this.mPersistentAppletInfo.entrySet()) {
                    String str = (String) entry.getKey();
                    AppletProperties appletProperties = (AppletProperties) entry.getValue();
                    Log.i("UcmService", "Persistent  key-" + str);
                    fastXmlSerializer.startTag(null, "applet");
                    fastXmlSerializer.attribute(null, "appletLocation", appletProperties.appletLocation);
                    fastXmlSerializer.attribute(null, "pluginName", appletProperties.pluginName);
                    fastXmlSerializer.attribute(null, "adminId", Integer.toString(appletProperties.adminId));
                    byte[] bArr = appletProperties.aid;
                    if (bArr != null) {
                        fastXmlSerializer.attribute(null, "aid", convertByteToString(bArr));
                    }
                    fastXmlSerializer.endTag(null, "applet");
                }
                fastXmlSerializer.endTag(null, "applets");
                fastXmlSerializer.endDocument();
                this.mPersistentAppletInfoFile.finishWrite(startWrite);
                if (startWrite != null) {
                    startWrite.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public final void readPersistentAppletsInfoLocked() {
        Log.i("UcmService", "readPersistentAppletsInfoLocked is called...");
        if (!this.mPersistentAppletInfoFile.getBaseFile().exists()) {
            Log.i("UcmService", "mPersistentAppletInfoFile not exist...");
            return;
        }
        this.mPersistentAppletInfo.clear();
        try {
            FileInputStream openRead = this.mPersistentAppletInfoFile.openRead();
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openRead, null);
                for (int eventType = newPullParser.getEventType(); eventType != 2 && eventType != 1; eventType = newPullParser.next()) {
                }
                if ("applets".equals(newPullParser.getName())) {
                    int next = newPullParser.next();
                    do {
                        if (next == 2) {
                            if (newPullParser.getDepth() == 2 && "applet".equals(newPullParser.getName())) {
                                String attributeValue = newPullParser.getAttributeValue(null, "pluginName");
                                String attributeValue2 = newPullParser.getAttributeValue(null, "appletLocation");
                                String attributeValue3 = newPullParser.getAttributeValue(null, "adminId");
                                String attributeValue4 = newPullParser.getAttributeValue(null, "aid");
                                AppletProperties appletProperties = new AppletProperties(attributeValue2, attributeValue4 != null ? hexStringToByteArray(attributeValue4) : null, attributeValue, Integer.parseInt(attributeValue3));
                                if (!this.mPersistentAppletInfo.containsKey(attributeValue)) {
                                    this.mPersistentAppletInfo.put(attributeValue, appletProperties);
                                }
                            }
                        }
                        next = newPullParser.next();
                    } while (next != 1);
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
        for (Map.Entry entry : this.mPersistentAppletInfo.entrySet()) {
            String str = (String) entry.getKey();
            AppletProperties appletProperties2 = (AppletProperties) entry.getValue();
            Log.i("UcmService", "PersistentApplet  key-" + str);
            Log.i("UcmService", "PersistentApplet  pluginName-" + appletProperties2.pluginName);
            Log.i("UcmService", "PersistentApplet  AID-" + convertByteToString(appletProperties2.aid));
            Log.i("UcmService", "PersistentApplet  appletLocation-" + appletProperties2.appletLocation);
            Log.i("UcmService", "PersistentApplet  adminId-" + appletProperties2.adminId);
        }
    }

    public final boolean checkIfNotify(UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.f1778id;
        boolean z = false;
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
            Log.i("UcmService", "checkIfNotify for cs Name = " + credentialStorage.name + " Package name = " + credentialStorage.packageName);
            if (ucmAgentWrapper.info.enforceManagement) {
                Log.i("UcmService", "notifying to managed plugin");
                Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
                while (it.hasNext()) {
                    int i = ((UserInfo) it.next()).id;
                    Log.i("UcmService", "checkIfNotify: Valid userid - " + i);
                    z = this.mPolicyManager.isStorageEnabled(i, credentialStorage);
                    if (z) {
                        break;
                    }
                }
                return z;
            }
            Log.i("UcmService", "notifying to unmanaged plugin");
            return true;
        }
        Log.i("UcmService", "Package name for CS found NULL. Cannot notify.");
        return false;
    }

    public Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) {
        int i2;
        Log.i("UcmService", "notifyChangeToPlugin event " + i);
        this.mSecurityHelper.checkCallerPermissionFor("notifyChangeToPlugin");
        Bundle bundle2 = new Bundle();
        if (str == null) {
            boolean z = false;
            for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
                if (ucmAgentWrapper != null) {
                    if (ucmAgentWrapper.isServiceBound()) {
                        if (checkIfNotify(ucmAgentWrapper)) {
                            i2 = ucmAgentWrapper.notifyChange(i, bundle);
                            Log.i("UcmService", "activeAgent " + ucmAgentWrapper.info.f1778id + " notify status - " + i2);
                        } else {
                            Log.i("UcmService", "activeAgent " + ucmAgentWrapper.info.f1778id + " not notified");
                            i2 = 0;
                        }
                        Log.i("UcmService", "activeAgent status-" + i2);
                        if (!z && i2 != 0) {
                            bundle2.putInt("errorresponse", 18);
                            z = true;
                        }
                    } else if (17 == i) {
                        Log.i("UcmService", "EVENT_BOOT_COMPLETED, triggerNotifyChange");
                        ucmAgentWrapper.triggerNotifyChange(i, bundle);
                    } else {
                        bundle2.putInt("errorresponse", 14);
                        Log.i("UcmService", "agentService is null");
                        z = true;
                    }
                }
            }
            if (!z) {
                bundle2.putInt("errorresponse", 0);
            }
            bundle2.putBoolean("booleanresponse", true);
            return bundle2;
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (activeAgent.isServiceBound()) {
            if (activeAgent.notifyChange(i, bundle) == 0) {
                bundle2.putBoolean("booleanresponse", true);
                bundle2.putInt("errorresponse", 0);
                return bundle2;
            }
            bundle2.putInt("errorresponse", 18);
        } else {
            bundle2.putInt("errorresponse", 14);
            Log.i("UcmService", "agent is not bound");
        }
        bundle2.putBoolean("booleanresponse", false);
        return bundle2;
    }

    public void resetNonMdmCertificates() {
        Log.i("UcmService", "resetNonMdmCertificates called");
        this.mSecurityHelper.checkCallerPermissionFor("resetNonMdmCertificates");
        Bundle bundle = new Bundle();
        int i = getuseridforuid(Binder.getCallingUid());
        bundle.putInt("callerUid", 1000);
        bundle.putInt("user_id", i);
        Log.i("UcmService", "Iteration has started....");
        for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (ucmAgentWrapper != null) {
                if (!ucmAgentWrapper.isServiceBound() || ucmAgentWrapper.info.isReadOnly) {
                    Log.i("UcmService", "agent is not bound or not ready");
                } else if (this.mPolicyManager.isCSobscure(ucmAgentWrapper)) {
                    Log.i("UcmService", "activeAgent is CSobscure");
                } else {
                    ArrayList<String> arrayList = new ArrayList();
                    ArrayList<String> arrayList2 = new ArrayList();
                    bundle.putInt("resource", 1);
                    Bundle saw = ucmAgentWrapper.saw(bundle);
                    String[] stringArray = saw != null ? saw.getStringArray("stringarrayresponse") : null;
                    if (stringArray != null) {
                        Collections.addAll(arrayList, stringArray);
                    }
                    bundle.putInt("resource", 3);
                    Bundle saw2 = ucmAgentWrapper.saw(bundle);
                    String[] stringArray2 = saw2 != null ? saw2.getStringArray("stringarrayresponse") : null;
                    if (stringArray2 != null) {
                        Collections.addAll(arrayList2, stringArray2);
                    }
                    if (arrayList.isEmpty() && arrayList2.isEmpty()) {
                        Log.i("UcmService", "agentAliasesListKeychain.isEmpty() && agentAliasesListWiFi.isEmpty() .... continue");
                    } else {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            String[] strArr = this.mPolicyManager.getallAliasesforUserId(i, ucmAgentWrapper);
                            if (strArr != null) {
                                for (int i2 = 0; i2 < strArr.length; i2++) {
                                    if (arrayList.contains(strArr[i2])) {
                                        arrayList.remove(strArr[i2]);
                                    } else if (arrayList2.contains(strArr[i2])) {
                                        arrayList2.remove(strArr[i2]);
                                    }
                                }
                            }
                            for (String str : arrayList) {
                                Log.i("UcmService", "request to delete KEYCHAIN for alias: " + str);
                                bundle.putInt("resource", 1);
                                ucmAgentWrapper.delete(str, bundle);
                            }
                            for (String str2 : arrayList2) {
                                Log.i("UcmService", "request to delete WIFI for alias: " + str2);
                                bundle.putInt("resource", 3);
                                ucmAgentWrapper.delete(str2, bundle);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
        }
    }

    public boolean isUserCertificatesExistInUCS() {
        Log.i("UcmService", "isUserCertificatesExistInUCS called");
        this.mSecurityHelper.checkCallerPermissionFor("isUserCertificatesExistInUCS");
        Bundle bundle = new Bundle();
        int i = getuseridforuid(Binder.getCallingUid());
        bundle.putInt("callerUid", 1000);
        bundle.putInt("user_id", i);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("removable_user_certificates_list", true);
        bundle.putBundle("extraArgs", bundle2);
        Log.i("UcmService", "Iteration has started....");
        Iterator it = this.mUcmServiceAgentManager.getActiveAgentList().iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper != null) {
                if (!ucmAgentWrapper.isServiceBound() || ucmAgentWrapper.info.isReadOnly) {
                    Log.i("UcmService", "agent is not bound or not ready");
                } else if (this.mPolicyManager.isCSobscure(ucmAgentWrapper)) {
                    Log.i("UcmService", "activeAgent is CSobscure");
                } else {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        String[] strArr = this.mPolicyManager.getallAliasesforUserId(i, ucmAgentWrapper);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        ArrayList arrayList = new ArrayList();
                        bundle.putInt("resource", 1);
                        Bundle saw = ucmAgentWrapper.saw(bundle);
                        String[] stringArray = saw != null ? saw.getStringArray("stringarrayresponse") : null;
                        if (stringArray != null) {
                            Collections.addAll(arrayList, stringArray);
                        }
                        if (strArr != null) {
                            for (int i2 = 0; i2 < strArr.length; i2++) {
                                if (arrayList.contains(strArr[i2])) {
                                    arrayList.remove(strArr[i2]);
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            return true;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        bundle.putInt("resource", 3);
                        Bundle saw2 = ucmAgentWrapper.saw(bundle);
                        String[] stringArray2 = saw2 != null ? saw2.getStringArray("stringarrayresponse") : null;
                        if (stringArray2 != null) {
                            Collections.addAll(arrayList2, stringArray2);
                        }
                        if (strArr != null) {
                            for (int i3 = 0; i3 < strArr.length; i3++) {
                                if (arrayList2.contains(strArr[i3])) {
                                    arrayList2.remove(strArr[i3]);
                                }
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            return true;
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            }
        }
    }

    public boolean notifyLicenseStatus(String str, String str2, int i) {
        this.mSecurityHelper.checkCallerPermissionFor("notifyLicenseStatus");
        Log.i("UcmService", "notifyLicenseStatus packageName " + str + ",status-" + str2 + ", errorCode-" + i);
        Message obtainMessage = this.mHandler.obtainMessage(4);
        int callingUid = Binder.getCallingUid();
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putString("status", str2);
        bundle.putInt("errorCode", i);
        obtainMessage.setData(bundle);
        this.mUcmServiceAgentManager.checkESE(str, UserHandle.getUserId(callingUid));
        this.mHandler.sendMessage(obtainMessage);
        return true;
    }

    public ApduMessage createSecureChannel(int i, Bundle bundle) {
        Log.i("UcmService", "createSecureChannel is deprecated from Knox 3.10, not supported anymore.");
        return null;
    }

    public ApduMessage processMessage(int i, byte[] bArr) {
        Log.i("UcmService", "processMessage is deprecated from Knox 3.10, not supported anymore.");
        return null;
    }

    public int destroySecureChannel() {
        Log.i("UcmService", "destroySecureChannel is deprecated from Knox 3.10, not supported anymore.");
        return 1;
    }

    public ucmRetParcelable generateDek(String str) {
        return generateDek(str, null);
    }

    public final ucmRetParcelable generateDek(String str, UcmAgentWrapper ucmAgentWrapper) {
        Log.i("UcmService", "generateDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("generateDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            if (ucmAgentWrapper == null) {
                if (true == "boot_test".equals(str)) {
                    ucmAgentWrapper = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                } else {
                    ucmAgentWrapper = getActiveAgent(UniversalCredentialUtil.getSource(str));
                }
            }
            if (ucmAgentWrapper == null) {
                Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            Bundle generateDek = ucmAgentWrapper.generateDek();
            if (generateDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i("UcmService", "generateDek response from plugin:  error code = " + generateDek.getInt("errorresponse"));
            return getResponseParcel(generateDek);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public ucmRetParcelable generateWrappedDek(String str) {
        return generateWrappedDek(str, null);
    }

    public final ucmRetParcelable generateWrappedDek(String str, UcmAgentWrapper ucmAgentWrapper) {
        Log.i("UcmService", "generateWrappedDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("generateWrappedDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            if (ucmAgentWrapper == null) {
                if (true == "boot_test".equals(str)) {
                    ucmAgentWrapper = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                } else {
                    ucmAgentWrapper = getActiveAgent(UniversalCredentialUtil.getSource(str));
                }
            }
            if (ucmAgentWrapper == null) {
                Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            Bundle generateWrappedDek = ucmAgentWrapper.generateWrappedDek();
            if (generateWrappedDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i("UcmService", "generateWrappedDek response from plugin:  error code = " + generateWrappedDek.getInt("errorresponse"));
            return getResponseParcel(generateWrappedDek);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public ucmRetParcelable getDek(String str) {
        return getDek(str, null);
    }

    public final ucmRetParcelable getDek(String str, UcmAgentWrapper ucmAgentWrapper) {
        Log.i("UcmService", "getDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            Log.i("UcmService", "Checking uri : " + str);
            if (ucmAgentWrapper == null) {
                if (true == "boot_test".equals(str)) {
                    ucmAgentWrapper = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                } else {
                    ucmAgentWrapper = getActiveAgent(UniversalCredentialUtil.getSource(str));
                }
            }
            if (ucmAgentWrapper == null) {
                Log.i("UcmService", "no agent found for Source = com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            Bundle dek = ucmAgentWrapper.getDek();
            if (dek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putInt("errorresponse", 13);
                return getResponseParcel(bundle3);
            }
            Log.i("UcmService", "getDek Response from plugin:  error code = " + dek.getInt("errorresponse"));
            return getResponseParcel(dek);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public ucmRetParcelable unwrapDek(String str, byte[] bArr) {
        return unwrapDek(str, bArr, null);
    }

    public final ucmRetParcelable unwrapDek(String str, byte[] bArr, UcmAgentWrapper ucmAgentWrapper) {
        Log.i("UcmService", "unwrapDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("unwrapDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            if (ucmAgentWrapper == null) {
                if (true == "boot_test".equals(str)) {
                    ucmAgentWrapper = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                } else {
                    ucmAgentWrapper = getActiveAgent(UniversalCredentialUtil.getSource(str));
                }
            }
            if (ucmAgentWrapper == null) {
                Log.i("UcmService", "no agent found for Source = com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            Bundle unwrapDek = ucmAgentWrapper.unwrapDek(bArr);
            if (unwrapDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i("UcmService", "unwrapDek Response from plugin:  error code = " + unwrapDek.getInt("errorresponse"));
            return getResponseParcel(unwrapDek);
        } catch (Exception e) {
            Log.i("UcmService", "Exception" + e.getMessage());
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public Bundle generateKeyguardPassword(int i, String str, Bundle bundle) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "generateKeyguardPassword " + str + ", userId-" + i);
        if (!this.mSecurityHelper.isCallerSystemUI() && !this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.checkCallerPermissionFor("generateKeyguardPassword");
        } else {
            this.mSecurityHelper.checkDeviceIntegrity();
        }
        Bundle bundle2 = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle generateKeyguardPassword = activeAgent.generateKeyguardPassword(i, bundle);
        if (generateKeyguardPassword == null) {
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle3 = new Bundle();
            bundle3.putInt("errorresponse", 13);
            return bundle3;
        }
        byte[] byteArray = generateKeyguardPassword.getByteArray("bytearrayresponse");
        String string = generateKeyguardPassword.getString("stringresponse");
        if (byteArray == null) {
            Log.i("UcmService", "generateKeyguardPassword. byte is null.");
            if (string != null) {
                Log.i("UcmService", "generateKeyguardPassword. byte is null. fill bytes from str");
                try {
                    generateKeyguardPassword.putByteArray("bytearrayresponse", string.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                }
            }
        }
        Log.i("UcmService", "generateKeyguardPassword Response from plugin:  error code = " + generateKeyguardPassword.getInt("errorresponse"));
        return generateKeyguardPassword;
    }

    public final int getuseridforuid(int i) {
        return UserHandle.getUserId(i);
    }

    public ucmRetParcelable getCertificateChain(String str) {
        Log.i("UcmService", "getCertificateChain " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "getCertificateChain new userid-" + i);
            } else {
                Log.i("UcmService", "getCertificateChain user id id not valid. Keeping same userid");
            }
        }
        Bundle bundle2 = new Bundle();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! getCertificateChain is NOT allowed for URI = " + str);
                bundle.putInt("errorresponse", 15);
                return getResponseParcel(bundle);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", i);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", resourceId);
            Log.i("UcmService", "getCertificateChain KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "getCertificateChain KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "getCertificateChain KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            Bundle certificateChain = activeAgent.getCertificateChain(ucmUri.getRawAlias(), bundle2);
            if (certificateChain == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putInt("errorresponse", 13);
                return getResponseParcel(bundle3);
            }
            byte[] byteArray = certificateChain.getByteArray("bytearrayresponse");
            Log.i("UcmService", "getCertificateChain Response from plugin:  error code = " + certificateChain.getInt("errorresponse"));
            if (byteArray == null) {
                Log.i("UcmService", "ERROR: Empty data received for getCertificateChain");
                certificateChain.putInt("errorresponse", 13);
                return getResponseParcel(certificateChain);
            }
            return getResponseParcel(certificateChain);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.i("UcmService", "decrypt " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        if (bArr == null) {
            bundle2.putInt("errorresponse", 4);
            return getResponseParcel(bundle2);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putInt("errorresponse", 14);
            return getResponseParcel(bundle2);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "Decrypt new userid-" + i);
            } else {
                Log.i("UcmService", "Decrypt. Keeping same userid" + i);
            }
        }
        Bundle bundle3 = bundle == null ? new Bundle() : bundle;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! decrypt is NOT allowed for URI = " + str);
                bundle2.putInt("errorresponse", 15);
                return getResponseParcel(bundle2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle3.putInt("callerUid", callingUid);
            bundle3.putInt("user_id", i);
            bundle3.putInt("ownerUid", callingUid);
            bundle3.putInt("resource", resourceId);
            Log.i("UcmService", "decrypt KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i("UcmService", "decrypt KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i("UcmService", "decrypt KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            Bundle decrypt = activeAgent.decrypt(ucmUri.getRawAlias(), bArr, str2, bundle3);
            if (decrypt == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle4 = new Bundle();
                bundle4.putInt("errorresponse", 13);
                return getResponseParcel(bundle4);
            }
            byte[] byteArray = decrypt.getByteArray("bytearrayresponse");
            Log.i("UcmService", "decrypt Response from plugin:  error code = " + decrypt.getInt("errorresponse"));
            if (byteArray == null) {
                Log.i("UcmService", "ERROR: Empty data received for decrypt");
                decrypt.putInt("errorresponse", 13);
                return getResponseParcel(decrypt);
            }
            return getResponseParcel(decrypt);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.i("UcmService", "encrypt " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        if (bArr == null) {
            bundle2.putInt("errorresponse", 4);
            return getResponseParcel(bundle2);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putInt("errorresponse", 14);
            return getResponseParcel(bundle2);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "Encrypt new userid-" + i);
            } else {
                Log.i("UcmService", "Encrypt. Keeping same userid" + i);
            }
        }
        Bundle bundle3 = bundle == null ? new Bundle() : bundle;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! decrypt is NOT allowed for URI = " + str);
                bundle2.putInt("errorresponse", 15);
                return getResponseParcel(bundle2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle3.putInt("callerUid", callingUid);
            bundle3.putInt("user_id", i);
            bundle3.putInt("ownerUid", callingUid);
            bundle3.putInt("resource", resourceId);
            Log.i("UcmService", "encrypt KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i("UcmService", "encrypt KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i("UcmService", "encrypt KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            Bundle encrypt = activeAgent.encrypt(ucmUri.getRawAlias(), bArr, str2, bundle3);
            if (encrypt == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle4 = new Bundle();
                bundle4.putInt("errorresponse", 13);
                return getResponseParcel(bundle4);
            }
            byte[] byteArray = encrypt.getByteArray("bytearrayresponse");
            Log.i("UcmService", "encrypt Response from plugin:  error code = " + encrypt.getInt("errorresponse"));
            if (byteArray == null) {
                Log.i("UcmService", "ERROR: Empty data received for encrypt");
                encrypt.putInt("errorresponse", 13);
                return getResponseParcel(encrypt);
            }
            return getResponseParcel(encrypt);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:213:0x0627  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x064a A[LOOP:9: B:221:0x0644->B:223:0x064a, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Bundle saw(String str, int i) {
        long clearCallingIdentity;
        Iterator it;
        int i2;
        String str2;
        ArrayList arrayList;
        Iterator it2;
        String str3;
        int i3;
        Bundle bundle;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        Bundle bundle2;
        String str9;
        List asList;
        ArrayList arrayList2;
        List list;
        String[] wifiCertificateAliasesAsUser;
        String str10;
        Log.i("UcmService", "saw " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle3 = new Bundle();
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        int i4 = getuseridforuid(callingUid);
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || SemPersonaManager.isKnoxId(i4))) {
            callingUid = uid;
        }
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        Bundle bundle4 = new Bundle();
        bundle4.putInt("callerUid", callingUid);
        bundle4.putInt("user_id", i4);
        bundle4.putInt("ownerUid", callingUid);
        bundle4.putInt("resource", resourceId);
        bundle4.putInt("entryType", i);
        Log.i("UcmService", "saw KEY_RESOURCE_ID= " + bundle4.getInt("resource", -2));
        Log.i("UcmService", "saw KEY_USER_ID= " + bundle4.getInt("user_id", -2));
        Log.i("UcmService", "saw KEY_CALLER_UID= " + bundle4.getInt("callerUid", -2));
        int i5 = 15;
        String str11 = "WARNING!!!! saw access NOT allowed for ";
        String str12 = "ERROR: Null Response received from activeAgent";
        String str13 = ",  error code = ";
        Bundle bundle5 = bundle4;
        String str14 = "UCMERRORTESTING: @CredentialManagerService saw Response from plugin: ";
        String str15 = "activeAgent is not bound";
        List list2 = null;
        String str16 = "stringarrayresponse";
        String str17 = "errorresponse";
        if (!UniversalCredentialUtil.isKeyChainUri(str) || !ucmUri.getSource().equals("KNOX")) {
            UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
            if (activeAgent == null) {
                bundle3.putInt(str17, 14);
                return bundle3;
            }
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", str15);
                bundle3.putInt(str17, 14);
                return bundle3;
            }
            Bundle saw = activeAgent.saw(bundle5);
            if (saw == null) {
                Log.i("UcmService", "ERROR: Null Response received from activeAgent");
                Bundle bundle6 = new Bundle();
                bundle6.putInt(str17, 13);
                return bundle6;
            }
            String[] stringArray = saw.getStringArray(str16);
            int i6 = saw.getInt(str17, 0);
            Log.i("UcmService", "saw Response from plugin:  error code = " + i6);
            Log.i("UcmService", str14 + activeAgent.info.f1778id + ",  error code = " + i6);
            if (resourceId == 2) {
                saw.putStringArray(str16, stringArray);
                saw.putInt(str17, i6);
                return saw;
            }
            if (resourceId != 3) {
                saw.putInt(str17, i6);
                return saw;
            }
            if (this.mPolicyManager.isCSobscure(activeAgent)) {
                saw.putStringArray(str16, stringArray);
                saw.putInt(str17, i6);
                return saw;
            }
            new ArrayList();
            new ArrayList();
            List arrayList3 = new ArrayList();
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mPolicyManager.isSEStorageAccessAllowed(activeAgent, i4, callingUid, false, null) == 0) {
                    Log.i("UcmService", "WARNING!!!! saw access NOT allowed for " + activeAgent.info.f1778id);
                    saw.putInt(str17, 15);
                    return saw;
                }
                if (stringArray == null) {
                    saw.putInt(str17, i6);
                    return saw;
                }
                String[] strArr = this.mPolicyManager.getallAliasesforUid(callingUid, activeAgent);
                List asList2 = strArr == null ? null : Arrays.asList(strArr);
                String[] strArr2 = this.mPolicyManager.getallAliasesforCS(activeAgent);
                if (strArr2 != null && strArr2.length != 0) {
                    if (strArr != null && strArr.length != 0) {
                        for (String str18 : strArr2) {
                            if (!this.mPolicyManager.containsCaseInsensitive(str18, asList2)) {
                                arrayList3.add(str18);
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        List asList3 = Arrays.asList(stringArray);
                        if (arrayList3 != null && arrayList3.size() != 0) {
                            for (i2 = 0; i2 < arrayList3.size(); i2++) {
                                String str19 = (String) arrayList3.get(i2);
                                if (this.mPolicyManager.containsCaseInsensitive(str19, asList3)) {
                                    asList3.remove(str19);
                                }
                            }
                        }
                        ArrayList arrayList4 = new ArrayList();
                        it = asList3.iterator();
                        while (it.hasNext()) {
                            arrayList4.add(new UniversalCredentialUtil.UcmUriBuilder(activeAgent.info.f1778id).setResourceId(1).setUid(callingUid).setAlias((String) it.next()).build());
                        }
                        saw.putStringArray(str16, (String[]) arrayList4.toArray(new String[arrayList4.size()]));
                        saw.putInt(str17, i6);
                        return saw;
                    }
                    list2 = Arrays.asList(strArr2);
                }
                arrayList3 = list2;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                List asList32 = Arrays.asList(stringArray);
                if (arrayList3 != null) {
                    while (i2 < arrayList3.size()) {
                    }
                }
                ArrayList arrayList42 = new ArrayList();
                it = asList32.iterator();
                while (it.hasNext()) {
                }
                saw.putStringArray(str16, (String[]) arrayList42.toArray(new String[arrayList42.size()]));
                saw.putInt(str17, i6);
                return saw;
            } finally {
            }
        }
        ArrayList arrayList5 = new ArrayList();
        Log.i("UcmService", "Iteration has started.... uid-" + callingUid);
        Iterator it3 = this.mUcmServiceAgentManager.getActiveAgentList().iterator();
        boolean z = false;
        while (it3.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it3.next();
            if (ucmAgentWrapper == null) {
                str2 = str14;
                arrayList = arrayList5;
                it2 = it3;
                str3 = str11;
                i3 = i5;
                bundle = bundle5;
                str4 = str15;
                str5 = str16;
                str6 = str17;
            } else {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String str20 = str11;
                    it2 = it3;
                    i3 = i5;
                    str2 = str14;
                    arrayList = arrayList5;
                    bundle = bundle5;
                    String str21 = str15;
                    str5 = str16;
                    str6 = str17;
                    if (this.mPolicyManager.isSEStorageAccessAllowed(ucmAgentWrapper, i4, callingUid, false, null) == 0) {
                        Log.i("UcmService", str20 + ucmAgentWrapper.info.f1778id);
                        bundle3.putInt(str6, i3);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        str17 = str6;
                        i5 = i3;
                        str11 = str20;
                        it3 = it2;
                        bundle5 = bundle;
                        str15 = str21;
                    } else {
                        str3 = str20;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (ucmAgentWrapper.isServiceBound()) {
                            str4 = str21;
                            if (this.mPolicyManager.isCSobscure(ucmAgentWrapper)) {
                                Log.i("UcmService", "skipping obscure activeAgent");
                            } else {
                                Bundle saw2 = ucmAgentWrapper.saw(bundle);
                                if (saw2 == null) {
                                    Log.i("UcmService", str12);
                                    bundle3 = new Bundle();
                                    bundle3.putInt(str6, 13);
                                    str17 = str6;
                                    i5 = i3;
                                    str15 = str4;
                                    bundle5 = bundle;
                                    str11 = str3;
                                    it3 = it2;
                                } else {
                                    String[] stringArray2 = saw2.getStringArray(str5);
                                    Log.i("UcmService", str2 + ucmAgentWrapper.info.f1778id + str13 + saw2.getInt(str6));
                                    if (stringArray2 == null) {
                                        arrayList2 = arrayList;
                                        str8 = str4;
                                        bundle2 = bundle;
                                        str7 = str13;
                                        str9 = str12;
                                    } else {
                                        Log.i("UcmService", "Agent alias size -" + stringArray2.length);
                                        int length = stringArray2.length;
                                        int i7 = 0;
                                        while (i7 < length) {
                                            Log.i("UcmService", "Agent alias-" + stringArray2[i7]);
                                            i7++;
                                            length = length;
                                            str13 = str13;
                                        }
                                        str7 = str13;
                                        new ArrayList();
                                        new ArrayList();
                                        List arrayList6 = new ArrayList();
                                        clearCallingIdentity = Binder.clearCallingIdentity();
                                        try {
                                            String[] strArr3 = this.mPolicyManager.getallAliasesforUid(callingUid, ucmAgentWrapper);
                                            if (resourceId != 3 || callingUid != 1000 || (wifiCertificateAliasesAsUser = this.mPolicyManager.getWifiCertificateAliasesAsUser(0, ucmAgentWrapper)) == null || wifiCertificateAliasesAsUser.length <= 0) {
                                                str8 = str4;
                                                bundle2 = bundle;
                                                str9 = str12;
                                            } else {
                                                ArrayList arrayList7 = new ArrayList();
                                                str9 = str12;
                                                int length2 = wifiCertificateAliasesAsUser.length;
                                                bundle2 = bundle;
                                                int i8 = 0;
                                                while (i8 < length2) {
                                                    int i9 = length2;
                                                    String str22 = wifiCertificateAliasesAsUser[i8];
                                                    String[] strArr4 = wifiCertificateAliasesAsUser;
                                                    if (this.mPolicyManager.containsCaseInsensitive(str22, arrayList7)) {
                                                        str10 = str4;
                                                    } else {
                                                        StringBuilder sb = new StringBuilder();
                                                        str10 = str4;
                                                        sb.append("saw : Found wifi certAlias-");
                                                        sb.append(str22);
                                                        Log.i("UcmService", sb.toString());
                                                        arrayList7.add(str22);
                                                    }
                                                    i8++;
                                                    length2 = i9;
                                                    wifiCertificateAliasesAsUser = strArr4;
                                                    str4 = str10;
                                                }
                                                str8 = str4;
                                                if (strArr3 != null && strArr3.length > 0) {
                                                    for (String str23 : strArr3) {
                                                        if (!this.mPolicyManager.containsCaseInsensitive(str23, arrayList7)) {
                                                            arrayList7.add(str23);
                                                        }
                                                    }
                                                }
                                                strArr3 = (String[]) arrayList7.toArray(new String[arrayList7.size()]);
                                            }
                                            if (strArr3 == null) {
                                                Log.i("UcmService", "saw : mdmallowedaliasesList for uid " + callingUid + " is null");
                                                asList = null;
                                            } else {
                                                asList = Arrays.asList(strArr3);
                                                Log.i("UcmService", "saw : mdmallowedaliasesList for uid " + callingUid + " is = " + asList.toString());
                                            }
                                            String[] strArr5 = this.mPolicyManager.getallAliasesforCS(ucmAgentWrapper);
                                            if (strArr5 == null || strArr5.length == 0) {
                                                Log.i("UcmService", "saw : mdmunauthorizedaliasesList for user " + i4 + " is = null ");
                                                arrayList6 = null;
                                            } else if (strArr3 == null || strArr3.length == 0) {
                                                arrayList6 = Arrays.asList(strArr5);
                                                Log.i("UcmService", "saw : mdmunauthorizedaliasesList for user " + i4 + " is = " + arrayList6.toString());
                                            } else {
                                                int length3 = strArr5.length;
                                                int i10 = 0;
                                                while (i10 < length3) {
                                                    String str24 = strArr5[i10];
                                                    if (this.mPolicyManager.containsCaseInsensitive(str24, asList)) {
                                                        list = arrayList6;
                                                        Log.i("UcmService", "saw : mdmunauthorizedaliasesList unchanged, access allowed for " + str24);
                                                    } else {
                                                        arrayList6.add(str24);
                                                        StringBuilder sb2 = new StringBuilder();
                                                        list = arrayList6;
                                                        sb2.append("saw : new alias added to mdmunauthorizedaliasesList : ");
                                                        sb2.append(str24);
                                                        Log.i("UcmService", sb2.toString());
                                                    }
                                                    i10++;
                                                    arrayList6 = list;
                                                }
                                            }
                                            List arrayList8 = new ArrayList(Arrays.asList(stringArray2));
                                            Log.i("UcmService", "saw : Initial agent alias list for agent " + ucmAgentWrapper.info.f1778id + " is " + arrayList8.toString());
                                            if (arrayList6 != null && arrayList6.size() != 0) {
                                                for (int i11 = 0; i11 < arrayList6.size(); i11++) {
                                                    String str25 = (String) arrayList6.get(i11);
                                                    if (this.mPolicyManager.containsCaseInsensitive(str25, arrayList8)) {
                                                        arrayList8.remove(str25);
                                                        Log.i("UcmService", "saw : Removing from agent alias list : alias name = " + str25);
                                                    }
                                                }
                                            }
                                            Iterator it4 = arrayList8.iterator();
                                            while (it4.hasNext()) {
                                                String build = new UniversalCredentialUtil.UcmUriBuilder(ucmAgentWrapper.info.f1778id).setResourceId(resourceId).setUid(callingUid).setAlias((String) it4.next()).build();
                                                arrayList.add(build);
                                                Log.i("UcmService", "saw : Adding alias uri to finalaliasesList : " + build);
                                            }
                                            arrayList2 = arrayList;
                                        } finally {
                                        }
                                    }
                                    bundle3 = saw2;
                                    str17 = str6;
                                    str16 = str5;
                                    arrayList5 = arrayList2;
                                    str12 = str9;
                                    str13 = str7;
                                    it3 = it2;
                                    str11 = str3;
                                    bundle5 = bundle2;
                                    str14 = str2;
                                    str15 = str8;
                                    i5 = 15;
                                }
                            }
                        } else {
                            Log.i("UcmService", str21);
                            bundle3.putInt(str6, 14);
                            str17 = str6;
                            i5 = i3;
                            str15 = str21;
                            str11 = str3;
                            it3 = it2;
                            bundle5 = bundle;
                        }
                    }
                    str16 = str5;
                    z = true;
                    arrayList5 = arrayList;
                    str14 = str2;
                } finally {
                }
            }
            str17 = str6;
            i5 = i3;
            str15 = str4;
            str11 = str3;
            it3 = it2;
            bundle5 = bundle;
            str16 = str5;
            arrayList5 = arrayList;
            str14 = str2;
        }
        ArrayList arrayList9 = arrayList5;
        String str26 = str17;
        bundle3.putStringArray(str16, (String[]) arrayList9.toArray(new String[arrayList9.size()]));
        if (!z) {
            bundle3.putInt(str26, 0);
        }
        return bundle3;
    }

    public Bundle sawInternal(String str, int i, int i2) {
        Log.i("UcmService", "sawInternal() " + str + "; userId = " + i + "; resourceType=" + i2);
        Bundle bundle = new Bundle();
        this.mSecurityHelper.checkCallerPermissionFor("sawInternal");
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "sawInternal(): activeAgent == null");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (this.mPolicyManager.isCSobscure(activeAgent)) {
            Log.i("UcmService", "activeAgent is CSobscure");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("callerUid", 1000);
        bundle2.putInt("user_id", i);
        bundle2.putInt("resource", i2);
        Bundle saw = activeAgent.saw(bundle2);
        if (saw == null) {
            Log.i("UcmService", "ERROR: Null Response received from activeAgent");
            Bundle bundle3 = new Bundle();
            bundle3.putInt("errorresponse", 13);
            return bundle3;
        }
        String[] stringArray = saw.getStringArray("stringarrayresponse");
        if (stringArray != null) {
            Log.i("UcmService", "sawInternal() agentAliases.length=" + stringArray.length);
            for (String str2 : stringArray) {
                Log.i("UcmService", "sawInternal() agentAliases=" + str2);
            }
        }
        return saw;
    }

    public Bundle getAgentInfo(String str) {
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            return null;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        int i = (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid;
        int i2 = getuseridforuid(i);
        ucmUri.getResourceId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, i, false, null) == 0) {
                Log.i("UcmService", "WARNING!!!! getAgentInfo is NOT allowed for URI = " + str);
                return null;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return getAgentInfoBundle(activeAgent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle getAgentInfoBundle(UcmAgentWrapper ucmAgentWrapper) {
        Bundle bundle = new Bundle();
        UcmAgentWrapper.AgentInfo agentInfo = ucmAgentWrapper.info;
        if (agentInfo != null) {
            String str = agentInfo.f1778id;
            if (str != null) {
                bundle.putString("uniqueId", str);
            }
            String str2 = agentInfo.agentId;
            if (str2 != null) {
                bundle.putString("id", str2);
            }
            String str3 = agentInfo.summary;
            if (str3 != null) {
                bundle.putString("summary", str3);
            }
            String str4 = agentInfo.title;
            if (str4 != null) {
                bundle.putString(KnoxCustomManagerService.SHORTCUT_TITLE, str4);
            }
            String str5 = agentInfo.vendorId;
            if (str5 != null) {
                bundle.putString("vendorId", str5);
            }
            String str6 = agentInfo.storageType;
            if (str6 != null) {
                bundle.putString("storageType", str6);
            }
            bundle.putBoolean("isDetachable", agentInfo.isDetachable);
            bundle.putBoolean("reqUserVerification", agentInfo.reqUserVerification);
            bundle.putBoolean("isHardwareBacked", agentInfo.isHardwareBacked);
            bundle.putBoolean("isReadOnly", agentInfo.isReadOnly);
            bundle.putBoolean("isManageable", agentInfo.isManageable);
            bundle.putBoolean("enforceManagement", agentInfo.enforceManagement);
            bundle.putBoolean("isSupportChangePin", agentInfo.isSupportChangePin);
            bundle.putBoolean("isSupportChangePinWithPassword", agentInfo.isSupportChangePinWithPassword);
            bundle.putBoolean("isSupportBiometricForUCM", agentInfo.isSupportBiometricForUCM);
            bundle.putBoolean("isPUKSupported", agentInfo.isPUKSupported);
            bundle.putBoolean("isGeneratePasswordAvailable", agentInfo.isGeneratePasswordAvailable);
            bundle.putBoolean("isODESupport", false);
            String str7 = agentInfo.configuratorList;
            if (str7 != null) {
                bundle.putString("configuratorList", str7);
            }
        }
        ComponentName componentName = ucmAgentWrapper.componentName;
        if (componentName != null && componentName.getPackageName() != null) {
            bundle.putString("packageName", ucmAgentWrapper.componentName.getPackageName());
        }
        return bundle;
    }

    public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
        Log.i("UcmService", "setCertificateChain " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && callingUid == 1000) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! setCertificateChain is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("callerUid", callingUid);
            bundle3.putInt("user_id", i);
            bundle3.putInt("ownerUid", callingUid);
            bundle3.putInt("resource", resourceId);
            bundle3.putBundle("extraArgs", bundle);
            Log.i("UcmService", "setCertificateChain KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i("UcmService", "setCertificateChain KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i("UcmService", "setCertificateChain KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            Bundle certificateChain = activeAgent.setCertificateChain(UniversalCredentialUtil.getRawAlias(str), bArr, bundle);
            if (certificateChain == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle4 = new Bundle();
                bundle4.putBoolean("booleanresponse", false);
                bundle4.putInt("errorresponse", 14);
                return bundle4;
            }
            boolean z = certificateChain.getBoolean("booleanresponse");
            int i2 = certificateChain.getInt("errorresponse");
            Log.i("UcmService", "setCertificateChain Response from plugin:  error code = " + i2);
            if (!z && i2 == 0) {
                Log.i("UcmService", "ERROR: Empty data received for setCertificateChain");
                certificateChain.putBoolean("booleanresponse", false);
                certificateChain.putInt("errorresponse", 13);
            }
            return certificateChain;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle importKeyPairInternal = importKeyPairInternal(str, bArr, bArr2, bundle, false);
        if (importKeyPairInternal != null) {
            Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService importKeyPair Response from plugin with error code = " + importKeyPairInternal.getInt("errorresponse"));
        } else {
            Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService importKeyPair Response from plugin is null");
        }
        return importKeyPairInternal;
    }

    public Bundle importKey(String str, Bundle bundle) {
        Log.i("UcmService", "importKey(" + str + ")");
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "importKey: NULL agent for uri " + str);
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.f1778id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "generateKey new userid-" + i);
            } else {
                Log.i("UcmService", "generateKey. Keeping same userid" + i);
            }
        }
        bundle.putInt("callerUid", callingUid);
        bundle.putInt("user_id", i);
        bundle.putInt("ownerUid", callingUid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, true, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!! importKey is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "importKey: KEY_RESOURCE_ID = " + bundle.getInt("resource", -2));
            Log.i("UcmService", "importKey: KEY_USER_ID     = " + bundle.getInt("user_id", -2));
            Log.i("UcmService", "importKey: KEY_CALLER_UID  = " + bundle.getInt("callerUid", -2));
            Log.i("UcmService", "importKey: KEY_ALGORITHM   = " + bundle.getString("algorithm", "AES"));
            Bundle importKey = activeAgent.importKey(UniversalCredentialUtil.getRawAlias(str), bundle);
            if (importKey != null) {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService importKey. Response from plugin with error code = " + importKey.getInt("errorresponse"));
            } else {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService importKey. Response from plugin is NULL");
            }
            return importKey;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle getKeyType(String str) {
        Log.i("UcmService", "getKeyType(" + str + ")");
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "getKeyType: NULL agent for uri " + str);
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.f1778id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "generateKey new userid-" + i);
            } else {
                Log.i("UcmService", "generateKey. Keeping same userid" + i);
            }
        }
        int i2 = i;
        Bundle bundle2 = new Bundle();
        bundle2.putInt("callerUid", callingUid);
        bundle2.putInt("user_id", i2);
        bundle2.putInt("ownerUid", callingUid);
        bundle2.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, true, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!! getKeyType is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "getKeyType: KEY_RESOURCE_ID = " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "getKeyType: KEY_USER_ID     = " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "getKeyType: KEY_CALLER_UID  = " + bundle2.getInt("callerUid", -2));
            Bundle keyType = activeAgent.getKeyType(UniversalCredentialUtil.getRawAlias(str), bundle2);
            if (keyType != null) {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService getKeyType. Response from plugin with error code = " + keyType.getInt("errorresponse"));
            } else {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService getKeyType. Response from plugin is NULL");
            }
            return keyType;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        this.mSecurityHelper.checkCallerPermissionFor("installCertificate");
        Bundle importKeyPairInternal = importKeyPairInternal(str, bArr, bArr2, bundle, true);
        if (importKeyPairInternal == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        }
        int i = importKeyPairInternal.getInt("errorresponse");
        Log.i("UcmService", "installCertificate Response:  error code = " + i);
        Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService installCertificate Response from plugin with error code = " + i);
        return importKeyPairInternal;
    }

    public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.i("UcmService", "installCertificateIfSupported()");
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "getKeyType: NULL agent for uri " + str);
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.f1778id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid == -1 || callingUid == uid || (callingUid != 1000 && !this.mSecurityHelper.isCallFromSystem(callingUid))) {
            uid = callingUid;
        }
        int i = getuseridforuid(uid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "installCertificateIfSupported new userid-" + i);
            } else {
                Log.i("UcmService", "installCertificateIfSupported. Keeping same userid" + i);
            }
        }
        bundle.putInt("callerUid", uid);
        bundle.putInt("user_id", i);
        bundle.putInt("ownerUid", uid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, uid, true, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!! installCertificateIfSupported is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "installCertificateIfSupported: KEY_RESOURCE_ID = " + bundle.getInt("resource", -2));
            Log.i("UcmService", "installCertificateIfSupported: KEY_USER_ID     = " + bundle.getInt("user_id", -2));
            Log.i("UcmService", "installCertificateIfSupported: KEY_CALLER_UID  = " + bundle.getInt("callerUid", -2));
            Bundle installCertificateIfSupported = activeAgent.installCertificateIfSupported(UniversalCredentialUtil.getRawAlias(str), bArr, str2, bundle);
            if (installCertificateIfSupported != null) {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService installCertificateIfSupported. Response from plugin with error code = " + installCertificateIfSupported.getInt("errorresponse"));
            } else {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService installCertificateIfSupported. Response from plugin is NULL");
            }
            return installCertificateIfSupported;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle importKeyPairInternal(String str, byte[] bArr, byte[] bArr2, Bundle bundle, boolean z) {
        Log.i("UcmService", "importKeyPairInternal " + str);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "importKeyPairInternal : NULL agent for uri " + str);
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent= " + activeAgent.info.f1778id);
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid == -1 || callingUid == uid || callingUid != 1000) {
            uid = callingUid;
        }
        int i = getuseridforuid(uid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putBoolean("ismdm", z);
        if (z) {
            bundle3.putInt("callerUid", bundle.getInt("admin_id", 0));
            bundle3.putInt("ownerUid", bundle.getInt("admin_id", 0));
            bundle3.putBoolean("renew", bundle.getBoolean("renew", false));
            if (bundle.getBoolean("allow_wifi", false)) {
                bundle3.putInt("resource", 3);
            } else {
                bundle3.putInt("resource", 1);
            }
            Log.i("UcmService", "userd id from MDM = " + bundle.getInt("user_id", 0));
            bundle3.putInt("user_id", bundle.getInt("user_id", 0));
            bundle3.putBundle("extraArgs", bundle);
            bundle3.putString("algorithm", bundle.getString("algorithm", "RSA"));
        } else {
            bundle3.putInt("callerUid", uid);
            bundle3.putInt("ownerUid", uid);
            bundle3.putInt("resource", resourceId);
            bundle3.putInt("user_id", i);
            bundle3.putString("algorithm", bundle.getString("algorithm", "RSA"));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, uid, z, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! importKeyPairInternal is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "importKeyPairInternal KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i("UcmService", "importKeyPairInternal KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i("UcmService", "importKeyPairInternal KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            Log.i("UcmService", "importKeyPairInternal KEY_ALGORITHM= " + bundle3.getString("algorithm", "RSA"));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            return activeAgent.importKeyPair(UniversalCredentialUtil.getRawAlias(str), bArr, bArr2, bundle3);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle delete(String str) {
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle deleteInternal = deleteInternal(str, false, 0);
        if (deleteInternal != null) {
            Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService delete Response from plugin with error code = " + deleteInternal.getInt("errorresponse"));
        } else {
            Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService delete Response from plugin is null");
        }
        return deleteInternal;
    }

    public Bundle deleteCertificate(String str, int i) {
        this.mSecurityHelper.checkCallerPermissionFor("deleteCertificate");
        Bundle deleteInternal = deleteInternal(str, true, i);
        if (deleteInternal == null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 13);
            return bundle;
        }
        int i2 = deleteInternal.getInt("errorresponse");
        Log.i("UcmService", "deleteCertificate Response:  error code = " + i2);
        Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService deleteCertificate Response from plugin with error code = " + i2);
        return deleteInternal;
    }

    public final Bundle deleteInternal(String str, boolean z, int i) {
        Log.i("UcmService", "deleteInternal " + str);
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "deleteInternal : NULL agent for uri " + str);
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        int i2 = (uid == -1 || callingUid == uid || !(callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) ? callingUid : uid;
        int i3 = z ? i : getuseridforuid(i2);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i4 = resourceId;
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i3 = ucmUri.getUserId();
                Log.i("UcmService", "Delete new userid-" + i3);
            } else {
                Log.i("UcmService", "Delete. Keeping same userid" + i3);
            }
        }
        int i5 = i3;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i6 = i2;
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i5, i2, z, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! deleteInternal is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle2 = new Bundle();
            if (z) {
                bundle2.putBoolean("ismdm", z);
            }
            bundle2.putInt("callerUid", i6);
            bundle2.putInt("user_id", i5);
            bundle2.putInt("ownerUid", i6);
            bundle2.putInt("resource", i4);
            Log.i("UcmService", "delete KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "delete KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "delete KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            return activeAgent.delete(UniversalCredentialUtil.getRawAlias(str), bundle2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) {
        Log.i("UcmService", "generateKeyPairInternal " + str);
        this.mSecurityHelper.checkCallerPermissionFor("generateKeyPairInternal");
        Bundle generateKeyPairMain = generateKeyPairMain(str, str2, i, bundle, true);
        if (generateKeyPairMain == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        }
        Log.i("UcmService", "generateKeyPairInternal Response:  error code = " + generateKeyPairMain.getInt("errorresponse"));
        return generateKeyPairMain;
    }

    public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
        Log.i("UcmService", "generateKeyPair " + str);
        Bundle generateKeyPairMain = generateKeyPairMain(str, str2, i, bundle, false);
        if (generateKeyPairMain == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        }
        Log.i("UcmService", "generateKeyPair Response:  error code = " + generateKeyPairMain.getInt("errorresponse"));
        return generateKeyPairMain;
    }

    public Bundle generateKey(String str, String str2, int i, Bundle bundle) {
        Log.i("UcmService", "generateKey(" + str + ", " + str2 + ", " + i + ")");
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "generateKey: NULL agent for uri " + str);
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.f1778id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i2 = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                int userId = ucmUri.getUserId();
                Log.i("UcmService", "generateKey new userid-" + userId);
                i2 = userId;
            } else {
                Log.i("UcmService", "generateKey. Keeping same userid" + i2);
            }
        }
        bundle.putInt("callerUid", callingUid);
        bundle.putInt("user_id", i2);
        bundle.putInt("ownerUid", callingUid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, true, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!! importKey is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "generateKey: KEY_RESOURCE_ID = " + bundle.getInt("resource", -2));
            Log.i("UcmService", "generateKey: KEY_USER_ID     = " + bundle.getInt("user_id", -2));
            Log.i("UcmService", "generateKey: KEY_CALLER_UID  = " + bundle.getInt("callerUid", -2));
            Log.i("UcmService", "generateKey: KEY_ALGORITHM   = " + bundle.getString("algorithm", "AES"));
            Bundle generateKey = activeAgent.generateKey(UniversalCredentialUtil.getRawAlias(str), str2, i, bundle);
            if (generateKey != null) {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService generateKey. Response from plugin with error code = " + generateKey.getInt("errorresponse"));
            } else {
                Log.i("UcmService", "UCMERRORTESTING: @CredentialManagerService generateKey. Response from plugin is NULL");
            }
            return generateKey;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle generateKeyPairMain(String str, String str2, int i, Bundle bundle, boolean z) {
        Log.i("UcmService", "generateKeyPairMain " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "generateKeyPairMain : NULL agent for uri " + str);
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i2 = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i2 = ucmUri.getUserId();
                Log.i("UcmService", "generateKeyPairMain new userid-" + i2);
            } else {
                Log.i("UcmService", "generateKeyPairMain. Keeping same userid" + i2);
            }
        }
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, false, ucmUri.getRawAlias()) == 0) {
                    Log.i("UcmService", "WARNING!!!! generateKeyPairMain is NOT allowed for URI = " + str);
                    bundle2.putByteArray("bytearrayresponse", null);
                    bundle2.putInt("errorresponse", 15);
                    return bundle2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Bundle bundle3 = new Bundle();
        bundle3.putInt("callerUid", callingUid);
        bundle3.putInt("user_id", i2);
        bundle3.putInt("ownerUid", callingUid);
        bundle3.putInt("resource", resourceId);
        bundle3.putBundle("extraArgs", bundle);
        Log.i("UcmService", "generateKeyPairMain KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
        Log.i("UcmService", "generateKeyPairMain KEY_USER_ID= " + bundle3.getInt("user_id", -2));
        Log.i("UcmService", "generateKeyPairMain KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        return activeAgent.generateKeyPair(UniversalCredentialUtil.getRawAlias(str), str2, i, bundle3);
    }

    public final ucmRetParcelable getResponseParcel(Bundle bundle) {
        return new ucmRetParcelable(bundle.getInt("errorresponse"), bundle.getByteArray("bytearrayresponse"));
    }

    public final ucmRetParcelable getResponseParcel(int i) {
        return new ucmRetParcelable(i, (byte[]) null);
    }

    public final ucmRetParcelable getResponseParcel(int i, byte[] bArr) {
        return new ucmRetParcelable(i, bArr);
    }

    public ucmRetParcelable sign(String str, byte[] bArr, String str2) {
        Log.i("UcmService", "sign " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        if (bArr == null) {
            bundle.putInt("errorresponse", 4);
            return getResponseParcel(bundle);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "sign : NULL agent for uri " + str);
            bundle.putByteArray("bytearrayresponse", null);
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "sign new userid-" + i);
            } else {
                Log.i("UcmService", "sign user id id not valid. Keeping same userid");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! sign is NOT allowed for URI = " + str);
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 15);
                return getResponseParcel(bundle);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            try {
                UcmSignHelper ucmSignHelperFactory = this.mSignHelperFactory.getInstance(str2, activeAgent.info.supportSign);
                boolean isEncryptFunction = ucmSignHelperFactory.isEncryptFunction();
                String processAlgorithm = ucmSignHelperFactory.getProcessAlgorithm();
                byte[] processInput = ucmSignHelperFactory.processInput(bArr);
                if (processInput == null) {
                    Log.e("UcmService", "signHelper.processInput fail.");
                    return getResponseParcel(4);
                }
                Bundle bundle2 = new Bundle();
                bundle2.putInt("callerUid", callingUid);
                bundle2.putInt("user_id", i);
                bundle2.putInt("ownerUid", callingUid);
                bundle2.putInt("resource", resourceId);
                Log.i("UcmService", "sign KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
                Log.i("UcmService", "sign KEY_USER_ID= " + bundle2.getInt("user_id", -2));
                Log.i("UcmService", "sign KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
                Bundle sign = activeAgent.sign(UniversalCredentialUtil.getRawAlias(str), processInput, processAlgorithm, isEncryptFunction, bundle2);
                if (sign == null) {
                    Log.i("UcmService", "ERROR: Null Response received from agent");
                    Bundle bundle3 = new Bundle();
                    bundle3.putByteArray("bytearrayresponse", null);
                    bundle3.putInt("errorresponse", 14);
                    return getResponseParcel(bundle3);
                }
                byte[] byteArray = sign.getByteArray("bytearrayresponse");
                Log.i("UcmService", "sign Response from plugin:  error code = " + sign.getInt("errorresponse"));
                if (byteArray == null) {
                    Log.i("UcmService", "ERROR: Empty data received for sign");
                    sign.putByteArray("bytearrayresponse", null);
                    sign.putInt("errorresponse", 13);
                    return getResponseParcel(sign);
                }
                return getResponseParcel(sign);
            } catch (NoSuchAlgorithmException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
                return getResponseParcel(2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ucmRetParcelable mac(String str, byte[] bArr, String str2) {
        Log.i("UcmService", "mac " + str);
        Bundle bundle = new Bundle();
        if (bArr == null) {
            bundle.putInt("errorresponse", 4);
            return getResponseParcel(bundle);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "mac: NULL agent for URI: " + str);
            bundle.putByteArray("bytearrayresponse", null);
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && (callingUid == 1000 || this.mSecurityHelper.isCallFromSystem(callingUid))) {
            callingUid = uid;
        }
        int i = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        if (this.mSecurityHelper.isCallFromSystem(Binder.getCallingUid())) {
            if (ucmUri.getUserId() != -1) {
                i = ucmUri.getUserId();
                Log.i("UcmService", "mac new userid-" + i);
            } else {
                Log.i("UcmService", "mac user id id not valid. Keeping same userid");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! mac is NOT allowed for URI = " + str);
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 15);
                return getResponseParcel(bundle);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", i);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", resourceId);
            Log.i("UcmService", "mac KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "mac KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "mac KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            Bundle mac = activeAgent.mac(UniversalCredentialUtil.getRawAlias(str), bArr, str2, bundle2);
            if (mac == null) {
                Log.i("UcmService", "ERROR: Null response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putByteArray("bytearrayresponse", null);
                bundle3.putInt("errorresponse", 14);
                return getResponseParcel(bundle3);
            }
            byte[] byteArray = mac.getByteArray("bytearrayresponse");
            Log.i("UcmService", "mac Response from plugin:  error code = " + mac.getInt("errorresponse"));
            if (byteArray == null) {
                Log.i("UcmService", "ERROR: Empty data received for mac");
                mac.putByteArray("bytearrayresponse", null);
                mac.putInt("errorresponse", 13);
                return getResponseParcel(mac);
            }
            return getResponseParcel(mac);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle[] listProviders() {
        this.mSecurityHelper.checkDeviceIntegrity();
        return listExposedProvidersInternal(false);
    }

    public Bundle[] listAllProviders() {
        this.mSecurityHelper.checkCallerPermissionFor("listAllProviders");
        return listExposedProvidersInternal(true);
    }

    public final Bundle[] listExposedProvidersInternal(boolean z) {
        ArrayList arrayList = new ArrayList(Arrays.asList(listProvidersInternal(z)));
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Bundle bundle = (Bundle) it.next();
            String string = bundle.getString("uniqueId");
            if (string == null || string.equals("")) {
                Log.i("UcmService", "WARNING!!!! null/empty ID returned for agent bundle. Skipping agent.");
            } else if (this.mPolicyManager.isCSobscure(getActiveAgent(string))) {
                Log.i("UcmService", "WARNING!!!! Obscure CS agent bundle. Skipping agent : " + string);
            } else {
                arrayList2.add(bundle);
            }
        }
        return (Bundle[]) arrayList2.toArray(new Bundle[arrayList2.size()]);
    }

    public final Bundle[] listProvidersInternal(boolean z) {
        int callingUid = Binder.getCallingUid();
        int i = getuseridforuid(callingUid);
        if (this.mUcmServiceAgentManager.getActiveAgentList().size() == 0) {
            Log.i("UcmService", "listProvidersInternal:No activeAgent");
            return null;
        }
        Log.i("UcmService", "listProvidersInternal " + this.mUcmServiceAgentManager.getActiveAgentList().size() + " for " + callingUid + " and ismdmcaller-" + z);
        ArrayList arrayList = new ArrayList();
        for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (ucmAgentWrapper != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (!z) {
                    try {
                        if (this.mPolicyManager.isSEStorageAccessAllowed(ucmAgentWrapper, i, callingUid, z, null) == 0) {
                            Log.i("UcmService", "WARNING!!!! access NOT allowed for " + ucmAgentWrapper.info.f1778id);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                arrayList.add(getAgentInfoBundle(ucmAgentWrapper));
            }
        }
        Log.i("UcmService", "listProviders filtered " + arrayList.size());
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    public boolean grantKeyChainAccess(String str, int i) {
        Log.i("UcmService", "grantKeyChainAccess " + str + " for " + i);
        this.mSecurityHelper.checkDeviceIntegrity();
        this.mSecurityHelper.checkSystemCaller();
        if (UniversalCredentialUtil.isKeyChainUri(str)) {
            return true;
        }
        Log.i("UcmService", "Not Keychain URI");
        return false;
    }

    public Bundle generateSecureRandom(String str, int i, byte[] bArr) {
        Log.i("UcmService", "generateSecureRandom " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "generateSecureRandom : NULL agent for uri " + str);
            bundle.putByteArray("bytearrayresponse", null);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && callingUid == 1000) {
            callingUid = uid;
        }
        int i2 = getuseridforuid(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! generateSecureRandom is NOT allowed for URI = " + str);
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", i2);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", resourceId);
            Log.i("UcmService", "generateSecureRandom KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "generateSecureRandom KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "generateSecureRandom KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            Bundle generateSecureRandom = activeAgent.generateSecureRandom(i, bArr, bundle2);
            if (generateSecureRandom == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putByteArray("bytearrayresponse", null);
                bundle3.putInt("errorresponse", 14);
                return bundle3;
            }
            byte[] byteArray = generateSecureRandom.getByteArray("bytearrayresponse");
            Log.i("UcmService", "generateSecureRandom Response from plugin:  error code = " + generateSecureRandom.getInt("errorresponse"));
            if (byteArray != null) {
                return generateSecureRandom;
            }
            Log.i("UcmService", "ERROR: Empty data received for generateSecureRandom");
            Bundle bundle4 = new Bundle();
            bundle4.putByteArray("bytearrayresponse", null);
            bundle4.putInt("errorresponse", 13);
            return bundle4;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
        this.mSecurityHelper.checkDeviceIntegrity();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putInt("intresponse", -1);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("intresponse", -1);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle credentialStorageProperty = activeAgent.setCredentialStorageProperty(i, i2, bundle);
        Log.i("UcmService", "setCredentialStorageProperty Response from plugin");
        return credentialStorageProperty;
    }

    public Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
        this.mSecurityHelper.checkDeviceIntegrity();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putBundle("bundleresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBundle("bundleresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle credentialStorageProperty = activeAgent.getCredentialStorageProperty(i, i2, bundle);
        Log.i("UcmService", "getCredentialStorageProperty Response from plugin");
        return credentialStorageProperty;
    }

    public Bundle setAdminConfigureBundleForCs(int i, int i2, String str, Bundle bundle, int i3) {
        this.mSecurityHelper.checkCallerPermissionFor("setAdminConfigureBundleForCs");
        Bundle bundle2 = new Bundle();
        Log.i("UcmService", "setAdminConfigureBundleForCs " + str);
        if (bundle == null) {
            Log.i("UcmService", "setAdminConfigureBundleForCs : Bundle is null");
            bundle2.putInt("intresponse", -1);
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("intresponse", -1);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
            int callingUid = Binder.getCallingUid();
            int uid = ucmUri.getUid();
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid, true, null) == 0) {
                Log.i("UcmService", "WARNING!!!! setAdminConfigureBundleForCs is NOT allowed for URI = " + str);
                bundle2.putInt("intresponse", -1);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (bundle.getString("applet_location") != null) {
                if (true == checkPluginUsed(i2, str)) {
                    Log.i("UcmService", "the plugin already is used");
                    bundle2.putInt("intresponse", -1);
                    bundle2.putInt("errorresponse", 34);
                    return bundle2;
                }
                if (!processAdminConfigRequest(i, str, bundle)) {
                    bundle2.putInt("intresponse", -1);
                    bundle2.putInt("errorresponse", 25);
                    return bundle2;
                }
            }
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putInt("intresponse", -1);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            bundle.putString("applet_ese_chip_vendor", "");
            Bundle configureCredentialStoragePlugin = activeAgent.configureCredentialStoragePlugin(i, bundle, i3);
            if (configureCredentialStoragePlugin == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putInt("intresponse", -1);
                bundle3.putInt("errorresponse", 13);
                return bundle3;
            }
            int i4 = configureCredentialStoragePlugin.getInt("errorresponse");
            Log.i("UcmService", "setPackageSetting Response from plugin:  error code = " + i4);
            if (i4 == 0) {
                this.mConfigAppletRequestIds.add(Integer.valueOf(i3));
            }
            return configureCredentialStoragePlugin;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean checkPluginUsed(int i, String str) {
        Log.i("UcmService", "checkPluginUsed for the uri : " + str);
        Log.i("UcmService", "checkPluginUsed for the uri : " + i);
        String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i);
        String source = UniversalCredentialUtil.getSource(str);
        if (keyguardStorageForCurrentUser != null && !keyguardStorageForCurrentUser.equals("none") && source != null && keyguardStorageForCurrentUser.equals(source)) {
            Log.i("UcmService", "This plugin is already used in keyguard");
            return true;
        }
        String str2 = SystemProperties.get("persist.security.ucs.csname", "None");
        if (str2 == null || str == null || !str2.equals(str)) {
            return false;
        }
        Log.i("UcmService", "This plugin is already used in ODE");
        return true;
    }

    public Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) {
        this.mSecurityHelper.checkCallerPermissionFor("getAdminConfigureBundleFromCs");
        Bundle bundle = new Bundle();
        Log.i("UcmService", "getAdminConfigureBundleFromCs " + str);
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putBundle("bundleresponse", null);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, ucmUri.getUid(), true, null) == 0) {
                Log.i("UcmService", "WARNING!!!! getAdminConfigureBundleFromCs is NOT allowed for URI = " + str);
                bundle.putBundle("bundleresponse", null);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBundle("bundleresponse", null);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            Bundle credentialStoragePluginConfiguration = activeAgent.getCredentialStoragePluginConfiguration(i);
            if (credentialStoragePluginConfiguration == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("bundleresponse", null);
                bundle2.putInt("errorresponse", 13);
                return bundle2;
            }
            Log.i("UcmService", "getCredentialStoragePluginConfiguration Response from plugin: error code = " + credentialStoragePluginConfiguration.getInt("errorresponse"));
            return credentialStoragePluginConfiguration;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendStateChangeBroadcast(int i, String str) {
        Intent intent = new Intent("com.samsung.ucs.ucsservice.stateblocked");
        String source = UniversalCredentialUtil.getSource(str);
        intent.putExtra("UCS_STATE", i);
        intent.putExtra("UCS_CSNAME", source);
        Log.i("UcmService", "Broadcast CSNAME " + source);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.OWNER);
        try {
            if (this.mPersonaManager == null) {
                this.mPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
            }
            for (Integer num : this.mPersonaManager.getKnoxIds(true)) {
                Log.i("KnoxKeyguardReceiver", "send personaId : " + num);
                intent.setComponent(new ComponentName("com.samsung.knox.kss", "com.samsung.knox.kss.KnoxKeyguardReceiver"));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(num.intValue()));
            }
        } catch (Exception e) {
            Log.i("KnoxKeyguardReceiver", "The exception occurs " + e.getMessage());
        }
    }

    public Bundle verifyPin(int i, String str, String str2, Bundle bundle) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "verifyPin : " + str + ", userId-" + i);
        if (!this.mSecurityHelper.isCallerSystemUI() && !this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.checkCallerPermissionFor("verifyPin");
        } else {
            this.mSecurityHelper.checkDeviceIntegrity();
        }
        Bundle bundle2 = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        if (str2 == null || true == "".equals(str2)) {
            Log.i("UcmService", "pin is empty");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle verifyPin = activeAgent.verifyPin(i, str2, bundle);
        if (verifyPin == null) {
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle3 = new Bundle();
            bundle3.putInt("errorresponse", 13);
            return bundle3;
        }
        int i2 = verifyPin.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        int i3 = verifyPin.getInt("remainCnt", -1);
        Log.i("UcmService", "LOCK_STATE : " + i2);
        Log.i("UcmService", "REMAIN_COUNT : " + i3);
        if (i2 == 133) {
            Log.i("UcmService", "state changed to blocked");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                sendStateChangeBroadcast(133, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        displayToastFromAgentResponse(this.mContext, verifyPin);
        return verifyPin;
    }

    public Bundle verifyPuk(String str, String str2, String str3) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "verifyPuk : " + str);
        if (!this.mSecurityHelper.isCallerSystemUI() && !this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.checkCallerPermissionFor("verifyPuk");
        } else {
            this.mSecurityHelper.checkDeviceIntegrity();
        }
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (str3 == null || true == "".equals(str3)) {
            Log.i("UcmService", "pin is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Bundle verifyPuk = activeAgent.verifyPuk(str2, str3);
        if (verifyPuk == null) {
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        }
        int i = verifyPuk.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        int i2 = verifyPuk.getInt("remainCnt", -1);
        Log.i("UcmService", "LOCK_STATE : " + i);
        Log.i("UcmService", "REMAIN_COUNT : " + i2);
        return verifyPuk;
    }

    public Bundle changePin(String str, String str2, String str3, boolean z) {
        UcmAgentWrapper activeAgent;
        Bundle changePin;
        Log.i("UcmService", "changePin : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (str2 == null || true == "".equals(str2)) {
            Log.i("UcmService", "oldPin is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (str3 == null || true == "".equals(str3)) {
            Log.i("UcmService", "newPin is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!z) {
            changePin = activeAgent.changePin(str2, str3);
        } else if (activeAgent.info.isSupportChangePinWithPassword) {
            changePin = activeAgent.changePinWithPassword(str2, str3);
        } else {
            changePin = activeAgent.changePin(str2, str3);
        }
        if (changePin == null) {
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        }
        if (changePin.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1) == 133) {
            Log.i("UcmService", "state is changed to blocked");
            sendStateChangeBroadcast(133, str);
        }
        displayToastFromAgentResponse(this.mContext, changePin);
        return changePin;
    }

    public final void displayToastFromAgentResponse(final Context context, Bundle bundle) {
        final String string;
        if (!bundle.containsKey("toastmessageresponse") || (string = bundle.getString("toastmessageresponse", "")) == null || string.isEmpty()) {
            return;
        }
        try {
            Log.i("UcmService", "displayToastFromAgentResponse: " + string);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.9
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(context, string, 1).show();
                }
            });
        } catch (Exception e) {
            Log.e("UcmService", "displayToastFromAgentResponse: Exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    public final Bundle getErrorParameterBundle(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("errorresponse", i);
        return bundle;
    }

    public final CredentialStorage generateCS(String str, String str2) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = UniversalCredentialUtil.getSource(str);
        credentialStorage.packageName = str2;
        return credentialStorage;
    }

    public final boolean isValidUri(String str) {
        return (str == null || "".equals(str)) ? false : true;
    }

    public final UcmAgentWrapper getActiveAgentFromUri(String str) {
        if (isValidUri(str)) {
            return getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        return null;
    }

    public Bundle initKeyguardPin(String str, String str2, Bundle bundle) {
        Log.i("UcmService", "initKeyguardPin : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        if (str2 == null || true == "".equals(str2)) {
            return getErrorParameterBundle(16);
        }
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle initKeyguardPin = activeAgentFromUri.initKeyguardPin(str2, bundle);
        if (initKeyguardPin != null) {
            return initKeyguardPin;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle setKeyguardPinMaximumRetryCount(String str, int i) {
        Log.i("UcmService", "setKeyguardPinMaximumRetryCount : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMaximumRetryCount = activeAgentFromUri.setKeyguardPinMaximumRetryCount(i);
        if (keyguardPinMaximumRetryCount != null) {
            return keyguardPinMaximumRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle setKeyguardPinMinimumLength(String str, int i) {
        Log.i("UcmService", "setKeyguardPinMinimumLength : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMinimumLength = activeAgentFromUri.setKeyguardPinMinimumLength(i);
        if (keyguardPinMinimumLength != null) {
            return keyguardPinMinimumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle setKeyguardPinMaximumLength(String str, int i) {
        Log.i("UcmService", "setKeyguardPinMaximumLength : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMaximumLength = activeAgentFromUri.setKeyguardPinMaximumLength(i);
        if (keyguardPinMaximumLength != null) {
            return keyguardPinMaximumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle getKeyguardPinMaximumRetryCount(String str) {
        Log.i("UcmService", "getKeyguardPinMaximumRetryCount : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMaximumRetryCount = activeAgentFromUri.getKeyguardPinMaximumRetryCount();
        if (keyguardPinMaximumRetryCount != null) {
            return keyguardPinMaximumRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle getKeyguardPinCurrentRetryCount(String str) {
        Log.i("UcmService", "getKeyguardPinCurrentRetryCount : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinCurrentRetryCount = activeAgentFromUri.getKeyguardPinCurrentRetryCount();
        if (keyguardPinCurrentRetryCount != null) {
            return keyguardPinCurrentRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle getKeyguardPinMinimumLength(String str) {
        Log.i("UcmService", "getKeyguardPinMinimumLength : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMinimumLength = activeAgentFromUri.getKeyguardPinMinimumLength();
        if (keyguardPinMinimumLength != null) {
            return keyguardPinMinimumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle getKeyguardPinMaximumLength(String str) {
        Log.i("UcmService", "getKeyguardPinMaximumLength : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        Bundle keyguardPinMaximumLength = activeAgentFromUri.getKeyguardPinMaximumLength();
        if (keyguardPinMaximumLength != null) {
            return keyguardPinMaximumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public Bundle setState(String str, int i) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "setState : " + str);
        this.mSecurityHelper.checkCallerPermissionFor("setState");
        Bundle bundle = new Bundle();
        if (str == null) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Bundle state = activeAgent.setState(i);
        if (state != null) {
            return state;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle2 = new Bundle();
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "APDUCommand : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle2 = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        if (bArr == null) {
            Log.i("UcmService", "apdu is null");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle APDUCommand = activeAgent.APDUCommand(bArr, bundle);
        if (APDUCommand != null) {
            return APDUCommand;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle3 = new Bundle();
        bundle3.putInt("errorresponse", 13);
        return bundle3;
    }

    public Bundle getInfo(String str) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "getInfo : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Bundle info = activeAgent.getInfo();
        if (info != null) {
            return info;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle2 = new Bundle();
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public String getKeyguardStorageForCurrentUser(int i) {
        Log.i("UcmService", "getKeyguardStorageForCurrentUser : " + i);
        this.mSecurityHelper.checkDeviceIntegrity();
        AtomicFile keyguardInfoFile = getKeyguardInfoFile(i);
        String str = null;
        if (!keyguardInfoFile.getBaseFile().exists()) {
            return null;
        }
        Log.i("UcmService", "getKeyguardStorageForCurrentUser, isFileExist : exist");
        try {
            FileInputStream openRead = keyguardInfoFile.openRead();
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openRead, null);
                for (int eventType = newPullParser.getEventType(); eventType != 2 && eventType != 1; eventType = newPullParser.next()) {
                }
                if ("keyguard".equals(newPullParser.getName())) {
                    int next = newPullParser.next();
                    String str2 = null;
                    do {
                        if (next == 2) {
                            try {
                                if (newPullParser.getDepth() == 2 && "vendor".equals(newPullParser.getName())) {
                                    str2 = newPullParser.getAttributeValue(null, "name");
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (openRead != null) {
                                    try {
                                        openRead.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        }
                        next = newPullParser.next();
                    } while (next != 1);
                    str = str2;
                }
                if (openRead != null) {
                    openRead.close();
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (FileNotFoundException e) {
            Log.e("UcmService", "getKeyguardStorageForCurrentUser, The exception occurs " + e.getMessage());
        } catch (IOException e2) {
            Log.e("UcmService", "getKeyguardStorageForCurrentUser, The exception occurs " + e2.getMessage());
        } catch (XmlPullParserException e3) {
            Log.e("UcmService", "getKeyguardStorageForCurrentUser, The exception occurs " + e3.getMessage());
        }
        Log.i("UcmService", "getKeyguardStorageForCurrentUser : " + str);
        return str;
    }

    public final String getKeyguardStorageOwnerForCurrentUser(int i) {
        Log.i("UcmService", "getKeyguardStorageOwnerForCurrentUser : " + i);
        AtomicFile keyguardInfoFile = getKeyguardInfoFile(i);
        String str = null;
        if (!keyguardInfoFile.getBaseFile().exists()) {
            Log.i("UcmService", "isFileExist : not exist");
            return null;
        }
        try {
            FileInputStream openRead = keyguardInfoFile.openRead();
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openRead, null);
                for (int eventType = newPullParser.getEventType(); eventType != 2 && eventType != 1; eventType = newPullParser.next()) {
                }
                if ("keyguard".equals(newPullParser.getName())) {
                    int next = newPullParser.next();
                    String str2 = null;
                    do {
                        if (next == 2) {
                            try {
                                if (newPullParser.getDepth() == 2 && "vendor".equals(newPullParser.getName())) {
                                    str2 = newPullParser.getAttributeValue(null, "owner");
                                }
                            } catch (Throwable th) {
                                th = th;
                                str = str2;
                                if (openRead != null) {
                                    try {
                                        openRead.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        }
                        next = newPullParser.next();
                    } while (next != 1);
                    str = str2;
                }
                if (openRead != null) {
                    openRead.close();
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (FileNotFoundException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        } catch (IOException e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
        } catch (XmlPullParserException e3) {
            Log.i("UcmService", "The exception occurs " + e3.getMessage());
        }
        return str;
    }

    public final boolean applyMDMPolicies(boolean z, int i, int i2, String str, int i3) {
        long j;
        boolean z2;
        boolean z3;
        boolean z4;
        String storagePkgname = getStoragePkgname(str);
        Log.i("UcmService", "applyMDMPolicies adminUid-" + i + ", userId-" + i2 + ", pluginPkg-" + storagePkgname + ", apply-" + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(storagePkgname);
                try {
                    if (i2 != 0 && i2 < 10) {
                        j = clearCallingIdentity;
                        z4 = false;
                        Binder.restoreCallingIdentity(j);
                        return z4;
                    }
                    EnterpriseDeviceManager enterpriseDeviceManager = new EnterpriseDeviceManager(this.mContext, new ContextInfo(i), (Handler) null);
                    if (i != 0 && storagePkgname != null) {
                        if (i2 >= 10) {
                            int userId = UserHandle.getUserId(i);
                            Log.i("UcmService", "applyMDMPolicies adminUserId -" + userId);
                            z2 = userId == i2;
                            z3 = true;
                        } else {
                            z2 = false;
                            z3 = false;
                        }
                        Log.i("UcmService", "applyMDMPolicies isContainer [" + z3 + "] isBYODContainer [" + z2 + "]");
                        if (z) {
                            if (!z2) {
                                ArrayList arrayList2 = new ArrayList();
                                String[] packagesForUid = this.mPm.getPackagesForUid(i);
                                if (!z3 && packagesForUid != null) {
                                    int length = packagesForUid.length;
                                    int i4 = 0;
                                    while (i4 < length) {
                                        int i5 = length;
                                        String str2 = packagesForUid[i4];
                                        Log.i("UcmService", "applyMDMPolicies admin pkg -" + str2);
                                        arrayList2.add(str2);
                                        i4++;
                                        length = i5;
                                    }
                                }
                                arrayList2.addAll(arrayList);
                                Log.i("UcmService", "applyMDMPolicies addPackagesToForceStopBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().addPackagesToForceStopBlackList(arrayList2));
                                Log.i("UcmService", "applyMDMPolicies addPackagesToClearCacheBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().addPackagesToClearCacheBlackList(arrayList2));
                                Log.i("UcmService", "applyMDMPolicies addPackagesToClearDataBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().addPackagesToClearDataBlackList(arrayList2));
                                if (!z3 && packagesForUid != null) {
                                    for (String str3 : packagesForUid) {
                                        Log.i("UcmService", "applyMDMPolicies setAdminRemovable status-" + enterpriseDeviceManager.setAdminRemovable(false, str3));
                                        enterpriseDeviceManager.getApplicationPolicy().setApplicationUninstallationDisabled(str3);
                                    }
                                }
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    enterpriseDeviceManager.getApplicationPolicy().setApplicationUninstallationDisabled((String) it.next());
                                }
                            }
                            if (z3 && i3 != -1) {
                                Log.i("UcmService", "applyMDMPolicies inside container logic");
                                ArrayList arrayList3 = new ArrayList();
                                EnterpriseDeviceManager enterpriseDeviceManager2 = new EnterpriseDeviceManager(this.mContext, new ContextInfo(getUid0FromUid(i, i2)), (Handler) null);
                                KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(i, i2));
                                if (knoxContainerManager != null) {
                                    String[] packagesForUid2 = this.mPm.getPackagesForUid(i3);
                                    if (packagesForUid2 != null) {
                                        for (String str4 : packagesForUid2) {
                                            Log.i("UcmService", "applyMDMPolicies disable uninstall pkg -" + str4);
                                            knoxContainerManager.getApplicationPolicy().setApplicationUninstallationDisabled(str4);
                                            arrayList3.add(str4);
                                        }
                                    }
                                    if (arrayList3.size() > 0) {
                                        Log.i("UcmService", "applyMDMPolicies inside container addPackagesToForceStopBlackList status-" + knoxContainerManager.getApplicationPolicy().addPackagesToForceStopBlackList(arrayList3));
                                        Log.i("UcmService", "applyMDMPolicies inside container addPackagesToClearCacheBlackList status-" + knoxContainerManager.getApplicationPolicy().addPackagesToClearCacheBlackList(arrayList3));
                                        Log.i("UcmService", "applyMDMPolicies inside container addPackagesToClearDataBlackList status-" + knoxContainerManager.getApplicationPolicy().addPackagesToClearDataBlackList(arrayList3));
                                    }
                                    if (arrayList.size() > 0) {
                                        Log.i("UcmService", "applyMDMPolicies addPackagesToForceStopBlackList for plugin. status-" + enterpriseDeviceManager2.getApplicationPolicy().addPackagesToForceStopBlackList(arrayList));
                                        Log.i("UcmService", "applyMDMPolicies addPackagesToClearCacheBlackList for plugin. status-" + enterpriseDeviceManager2.getApplicationPolicy().addPackagesToClearCacheBlackList(arrayList));
                                        Log.i("UcmService", "applyMDMPolicies addPackagesToClearDataBlackList for plugin. status-" + enterpriseDeviceManager2.getApplicationPolicy().addPackagesToClearDataBlackList(arrayList));
                                        Iterator it2 = arrayList.iterator();
                                        while (it2.hasNext()) {
                                            String str5 = (String) it2.next();
                                            Log.i("UcmService", "applyMDMPolicies disable uninstall pkg -" + str5);
                                            enterpriseDeviceManager2.getApplicationPolicy().setApplicationUninstallationDisabled(str5);
                                        }
                                    }
                                }
                            }
                        } else {
                            boolean isPluginUsedInOtherUser = isPluginUsedInOtherUser(str, i2, i, false);
                            Log.i("UcmService", "applyMDMPolicies [" + str + "] isPluginUsedInOtherUser [" + isPluginUsedInOtherUser + "]");
                            if (!z2) {
                                ArrayList arrayList4 = new ArrayList();
                                String[] packagesForUid3 = this.mPm.getPackagesForUid(i);
                                if (!z3 && packagesForUid3 != null) {
                                    int length2 = packagesForUid3.length;
                                    int i6 = 0;
                                    while (i6 < length2) {
                                        int i7 = length2;
                                        String str6 = packagesForUid3[i6];
                                        Log.i("UcmService", "applyMDMPolicies admin pkg -" + str6);
                                        arrayList4.add(str6);
                                        i6++;
                                        length2 = i7;
                                    }
                                }
                                if (!isPluginUsedInOtherUser) {
                                    arrayList4.addAll(arrayList);
                                }
                                Log.i("UcmService", "applyMDMPolicies removePackagesFromForceStopBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().removePackagesFromForceStopBlackList(arrayList4));
                                Log.i("UcmService", "applyMDMPolicies removePackagesFromClearCacheBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().removePackagesFromClearCacheBlackList(arrayList4));
                                Log.i("UcmService", "applyMDMPolicies removePackagesFromClearDataBlackList status-" + enterpriseDeviceManager.getApplicationPolicy().removePackagesFromClearDataBlackList(arrayList4));
                                if (!z3 && packagesForUid3 != null) {
                                    for (String str7 : packagesForUid3) {
                                        Log.i("UcmService", "applyMDMPolicies setAdminRemovable status-" + enterpriseDeviceManager.setAdminRemovable(true, str7));
                                        enterpriseDeviceManager.getApplicationPolicy().setApplicationUninstallationEnabled(str7);
                                    }
                                }
                                if (!isPluginUsedInOtherUser) {
                                    Iterator it3 = arrayList.iterator();
                                    while (it3.hasNext()) {
                                        enterpriseDeviceManager.getApplicationPolicy().setApplicationUninstallationEnabled((String) it3.next());
                                    }
                                }
                            }
                            if (z3 && i3 != -1) {
                                Log.i("UcmService", "applyMDMPolicies inside container logic");
                                ArrayList arrayList5 = new ArrayList();
                                EnterpriseDeviceManager enterpriseDeviceManager3 = new EnterpriseDeviceManager(this.mContext, new ContextInfo(getUid0FromUid(i, i2)), (Handler) null);
                                KnoxContainerManager knoxContainerManager2 = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(i, i2));
                                String[] packagesForUid4 = this.mPm.getPackagesForUid(i3);
                                if (knoxContainerManager2 != null) {
                                    if (packagesForUid4 != null) {
                                        for (String str8 : packagesForUid4) {
                                            Log.i("UcmService", "applyMDMPolicies enable uninstall pkg -" + str8);
                                            knoxContainerManager2.getApplicationPolicy().setApplicationUninstallationEnabled(str8);
                                            arrayList5.add(str8);
                                        }
                                    }
                                    if (arrayList5.size() > 0) {
                                        Log.i("UcmService", "applyMDMPolicies inside container removePackagesFromForceStopBlackList status-" + knoxContainerManager2.getApplicationPolicy().removePackagesFromForceStopBlackList(arrayList5));
                                        Log.i("UcmService", "applyMDMPolicies inside container removePackagesFromClearCacheBlackList status-" + knoxContainerManager2.getApplicationPolicy().removePackagesFromClearCacheBlackList(arrayList5));
                                        Log.i("UcmService", "applyMDMPolicies inside container removePackagesFromClearDataBlackList status-" + knoxContainerManager2.getApplicationPolicy().removePackagesFromClearDataBlackList(arrayList5));
                                    }
                                    if (arrayList.size() > 0 && !isPluginUsedInOtherUser) {
                                        Log.i("UcmService", "applyMDMPolicies removePackagesFromForceStopBlackList for plugin status-" + enterpriseDeviceManager3.getApplicationPolicy().removePackagesFromForceStopBlackList(arrayList));
                                        Log.i("UcmService", "applyMDMPolicies removePackagesFromClearCacheBlackList for plugin status-" + enterpriseDeviceManager3.getApplicationPolicy().removePackagesFromClearCacheBlackList(arrayList));
                                        Log.i("UcmService", "applyMDMPolicies removePackagesFromClearDataBlackList for plugin status-" + enterpriseDeviceManager3.getApplicationPolicy().removePackagesFromClearDataBlackList(arrayList));
                                        Iterator it4 = arrayList.iterator();
                                        while (it4.hasNext()) {
                                            String str9 = (String) it4.next();
                                            Log.i("UcmService", "applyMDMPolicies enable uninstall pkg -" + str9);
                                            enterpriseDeviceManager3.getApplicationPolicy().setApplicationUninstallationEnabled(str9);
                                        }
                                    }
                                }
                            }
                        }
                        z4 = true;
                        Binder.restoreCallingIdentity(j);
                        return z4;
                    }
                    z4 = false;
                    Binder.restoreCallingIdentity(j);
                    return z4;
                } catch (Exception e) {
                    e = e;
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                    Binder.restoreCallingIdentity(j);
                    return false;
                }
                j = clearCallingIdentity;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        } catch (Exception e2) {
            e = e2;
            j = clearCallingIdentity;
        } catch (Throwable th3) {
            th = th3;
            Throwable th22 = th;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th22;
        }
    }

    public final boolean setKeyguardStorageForCurrentUser(int i, String str, String str2) {
        Log.i("UcmService", "setKeyguardStorageForCurrentUser called : " + str);
        AtomicFile keyguardInfoFile = getKeyguardInfoFile(i);
        try {
            FileOutputStream startWrite = keyguardInfoFile.startWrite();
            try {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, "utf-8");
                fastXmlSerializer.startDocument(null, Boolean.TRUE);
                fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag(null, "keyguard");
                fastXmlSerializer.startTag(null, "vendor");
                fastXmlSerializer.attribute(null, "name", str);
                fastXmlSerializer.attribute(null, "owner", str2);
                fastXmlSerializer.endTag(null, "keyguard");
                fastXmlSerializer.endDocument();
                keyguardInfoFile.finishWrite(startWrite);
                if (i == 0) {
                    if ("none".equals(str)) {
                        SystemProperties.set("persist.keyguard.ucs", "false");
                        updateSystemUIMonitor(null);
                    } else {
                        SystemProperties.set("persist.keyguard.ucs", "true");
                        updateSystemUIMonitor(str);
                    }
                }
                if (startWrite != null) {
                    startWrite.close();
                }
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return false;
        }
    }

    public final AtomicFile getKeyguardInfoFile(int i) {
        return new AtomicFile(new File(Environment.getUserSystemDirectory(i), "ucm_keyguardconfig.xml"));
    }

    public final String getStoragePkgname(String str) {
        String[] split;
        if (str == null || (split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR)) == null || split.length <= 0) {
            return null;
        }
        return split[0];
    }

    public final void sendUCMKeyguardIntent(boolean z, int i, String str) {
        Log.i("UcmService", "sendUCMKeyguardIntent set - " + z + ", userId-" + i + ", storage-" + str);
        try {
            Intent intent = new Intent();
            if (z) {
                intent.setAction("com.samsung.android.knox.intent.action.UCM_KEYGUARD_SET");
            } else {
                intent.setAction("com.samsung.android.knox.intent.action.UCM_KEYGUARD_UNSET");
            }
            Bundle bundle = new Bundle();
            bundle.putInt("userId", i);
            bundle.putString("package_name", str);
            intent.putExtras(bundle);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            Log.i("UcmService", "sendUCMKeyguardIntent intent sent to all...");
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0136  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean configureKeyguardSettings(int i, String str) {
        long clearCallingIdentity;
        UcmAgentWrapper ucmAgentWrapper;
        String str2;
        String str3;
        Log.i("UcmService", "configureKeyguardSettings userId-" + i);
        if (!this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.checkCallerPermissionFor("configureKeyguardSettings");
        } else {
            this.mSecurityHelper.checkDeviceIntegrity();
        }
        int i2 = 10;
        if (str == null || str.isEmpty()) {
            Log.i("UcmService", "disable keyguard in UCS");
            String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i);
            String storagePkgname = getStoragePkgname(keyguardStorageForCurrentUser);
            String keyguardStorageOwnerForCurrentUser = getKeyguardStorageOwnerForCurrentUser(i);
            Log.i("UcmService", "storageName - " + keyguardStorageForCurrentUser + ", onwerId-" + keyguardStorageOwnerForCurrentUser);
            boolean keyguardStorageForCurrentUser2 = setKeyguardStorageForCurrentUser(i, "none", "none");
            if (keyguardStorageForCurrentUser2) {
                sendUCMKeyguardIntent(false, i, storagePkgname);
            }
            if (i == 0) {
                try {
                    applyMDMPolicies(false, Integer.parseInt(keyguardStorageOwnerForCurrentUser), i, keyguardStorageForCurrentUser, -1);
                } catch (Exception e) {
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                }
                return EFSProperties.deleteKeyguardConfig() && keyguardStorageForCurrentUser2;
            }
            if (i >= 10) {
                try {
                    int parseInt = Integer.parseInt(keyguardStorageOwnerForCurrentUser);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        applyMDMPolicies(false, this.mEdmStorageProvider.getMUMContainerOwnerUid(i), i, keyguardStorageForCurrentUser, parseInt);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } finally {
                    }
                } catch (Exception e2) {
                    Log.i("UcmService", "The exception occurs " + e2.getMessage());
                }
            }
            return keyguardStorageForCurrentUser2;
        }
        String source = UniversalCredentialUtil.getSource(str);
        boolean z = false;
        for (UcmAgentWrapper ucmAgentWrapper2 : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (ucmAgentWrapper2.info.f1778id.equals(source) && ucmAgentWrapper2.info.isGeneratePasswordAvailable) {
                int adminForEnforcedCredentialStorageAsUser = getUCMMDMService().getAdminForEnforcedCredentialStorageAsUser(i);
                Log.i("UcmService", "configuratorId - " + adminForEnforcedCredentialStorageAsUser);
                if (adminForEnforcedCredentialStorageAsUser == -1) {
                    Log.i("UcmService", "enforced null");
                    return false;
                }
                boolean keyguardStorageForCurrentUser3 = setKeyguardStorageForCurrentUser(i, source, adminForEnforcedCredentialStorageAsUser + "");
                if (keyguardStorageForCurrentUser3) {
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            String storagePkgname2 = getStoragePkgname(source);
                            if (i == 0) {
                                ucmAgentWrapper = ucmAgentWrapper2;
                                try {
                                    keyguardStorageForCurrentUser3 = applyMDMPolicies(true, adminForEnforcedCredentialStorageAsUser, i, source, -1);
                                    str3 = storagePkgname2;
                                } catch (Exception e3) {
                                    e = e3;
                                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                                    if (i == 0) {
                                    }
                                    z = keyguardStorageForCurrentUser3;
                                    i2 = 10;
                                }
                            } else {
                                ucmAgentWrapper = ucmAgentWrapper2;
                                if (i >= i2) {
                                    str3 = storagePkgname2;
                                    keyguardStorageForCurrentUser3 = applyMDMPolicies(true, this.mEdmStorageProvider.getMUMContainerOwnerUid(i), i, source, adminForEnforcedCredentialStorageAsUser);
                                } else {
                                    str3 = storagePkgname2;
                                }
                            }
                            if (1 == this.mLockPatternUtils.getBiometricState(1, i)) {
                                this.mLockPatternUtils.setBiometricState(1, 0, i);
                            }
                            if (1 == this.mLockPatternUtils.getBiometricState(256, i)) {
                                this.mLockPatternUtils.setBiometricState(256, 0, i);
                            }
                            sendUCMKeyguardIntent(true, i, str3);
                            if (i == 0) {
                                disableAutoFactoryReset();
                            }
                        } catch (Exception e4) {
                            e = e4;
                            ucmAgentWrapper = ucmAgentWrapper2;
                        }
                    } finally {
                    }
                } else {
                    ucmAgentWrapper = ucmAgentWrapper2;
                }
                if (i == 0) {
                    UcmAgentWrapper ucmAgentWrapper3 = ucmAgentWrapper;
                    try {
                        byte[] bytes = ucmAgentWrapper3.info.agentId.getBytes("UTF-8");
                        String str4 = ucmAgentWrapper3.info.storageType;
                        AppletProperties appletInfo = getAppletInfo(source);
                        if (appletInfo != null && (str2 = appletInfo.appletLocation) != null) {
                            str4 = str2;
                        }
                        int storageTypeIndex = EFSProperties.ODEProperties.getStorageTypeIndex(str4);
                        if (storageTypeIndex < 0) {
                            Log.i("UcmService", "UCM does not support this storage type for KG : " + str4);
                            return false;
                        }
                        String str5 = ucmAgentWrapper3.info.enabledSCP;
                        int sCPTypeIndex = EFSProperties.ODEProperties.getSCPTypeIndex(str5);
                        if (sCPTypeIndex < 0) {
                            Log.i("UcmService", "UCM does not support this SCP type : " + str5);
                            return false;
                        }
                        UcmAgentWrapper.AgentInfo agentInfo = ucmAgentWrapper3.info;
                        return EFSProperties.saveKeyguardConfig(new EFSProperties.KeyguardProperties(bytes, agentInfo.AID, storageTypeIndex, sCPTypeIndex, agentInfo.pinMinLength, agentInfo.pinMaxLength, agentInfo.pukMinLength, agentInfo.pukMaxLength)) && keyguardStorageForCurrentUser3;
                    } catch (UnsupportedEncodingException e5) {
                        Log.i("UcmService", "The exception occurs " + e5.getMessage());
                    }
                }
                z = keyguardStorageForCurrentUser3;
            }
            i2 = 10;
        }
        Log.i("UcmService", "configureKeyguardSettings return : " + z);
        return z;
    }

    public final void disableAutoFactoryReset() {
        try {
            Log.i("UcmService", "disableAutoFactoryReset");
            int i = Settings.Global.getInt(this.mContext.getContentResolver(), "auto_swipe_main_user", 0);
            if (DBG) {
                Log.i("UcmService", "AUTO_SWIPE_MAIN_USER current : " + i);
            }
            boolean putInt = Settings.Secure.putInt(this.mContext.getContentResolver(), "auto_swipe_main_user", 0);
            if (DBG) {
                Log.i("UcmService", "Settings.Secure.putInt : " + putInt);
            }
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public int configureODESettings(String str, Bundle bundle, String str2) {
        String source;
        UcmAgentWrapper.AgentInfo agentInfo;
        String str3;
        Locale locale;
        String str4;
        Log.i("UcmService", "configureODESettings");
        this.mSecurityHelper.checkDeviceIntegrity();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int uid = ucmUri.getUid();
        String source2 = ucmUri.getSource();
        Log.i("UcmService", "configureODESettings uriuid-" + uid);
        if (UcmServiceODE.isUcmOdeEnabled()) {
            Log.i("UcmService", "device is encrypted with UCS so cannot change configuration");
            return 201327104;
        }
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig.cofiguratorPkg != null) {
            Log.i("UcmService", "configureODESettings Validating current ODE setting configurator");
            byte[] configuratorPkg = getConfiguratorPkg(uid);
            byte[] configuratorSignature = getConfiguratorSignature(uid);
            if (configuratorPkg != null) {
                if (!compareCallingPkg(loadODEConfig.cofiguratorPkg, configuratorPkg) || !Arrays.equals(loadODEConfig.cofiguratorSign, configuratorSignature)) {
                    Log.i("UcmService", "configureODESettings invalid caller is trying to change ODE configuration. Error...");
                } else {
                    Log.i("UcmService", "configureODESettings valid caller is changing ODE configuration...");
                }
            }
            return 201327360;
        }
        boolean z = false;
        boolean z2 = true;
        if ("reset".equals(source2)) {
            Log.i("UcmService", "disable configureODESettings in UCS");
            SystemProperties.set("persist.security.ucs", "");
            SystemProperties.set("persist.security.ucs.csname", "");
            return true == EFSProperties.deleteODEConfig() ? 0 : 269;
        }
        if (str2 == null || str2.isEmpty()) {
            Log.i("UcmService", "plugin signature is null");
            return 201327104;
        }
        if (true == str.isEmpty()) {
            Log.i("UcmService", "uri is empty");
            return 16;
        }
        if (!this.mUcmServiceAgentManager.getActiveAgentList().isEmpty() && (source = UniversalCredentialUtil.getSource(str)) != null && !source.isEmpty()) {
            for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
                if (ucmAgentWrapper != null && (agentInfo = ucmAgentWrapper.info) != null) {
                    String str5 = agentInfo.f1778id;
                    if (str5 == null) {
                        continue;
                    } else if (str5.equals(source)) {
                        Log.i("UcmService", "Find UcmAgentWrapper");
                        UcmAgentWrapper.AgentInfo agentInfo2 = ucmAgentWrapper.info;
                        if (!agentInfo2.isODESupport) {
                            Log.i("UcmService", "This agent dose not support ODE");
                            return 3;
                        }
                        byte[] bArr = agentInfo2.AID;
                        if (bArr == null || bArr.length == 0) {
                            Log.i("UcmService", "AID is empty. save default AID");
                            bArr = new byte[]{49, 50, 51, 52, 53, 97, 98, 99, 100, 101};
                        } else if (bArr.length < 5 || bArr.length > 16) {
                            Log.i("UcmService", "AID range is not proper");
                            return 4;
                        }
                        String str6 = ucmAgentWrapper.info.storageType;
                        AppletProperties appletInfo = getAppletInfo(source);
                        if (appletInfo != null && (str4 = appletInfo.appletLocation) != null) {
                            str6 = str4;
                        }
                        int storageTypeIndex = EFSProperties.ODEProperties.getStorageTypeIndex(str6);
                        if (storageTypeIndex < 0) {
                            Log.i("UcmService", "UCM does not support this storage type : " + str6);
                            return 4;
                        }
                        String str7 = ucmAgentWrapper.info.enabledSCP;
                        int sCPTypeIndex = EFSProperties.ODEProperties.getSCPTypeIndex(str7);
                        if (!TextUtils.isEmpty(str7)) {
                            if (sCPTypeIndex < 0) {
                                Log.i("UcmService", "UCM does not support this SCP type : " + str7);
                                return 4;
                            }
                            str7.equalsIgnoreCase("NONE");
                        }
                        if (this.mIsFbeEnabled) {
                            try {
                                int saveTempOdeKey = saveTempOdeKey(ucmAgentWrapper, str);
                                if (saveTempOdeKey != 0) {
                                    Log.i("UcmService", "failed saveTempOdeKey. [" + saveTempOdeKey + "]");
                                    return saveTempOdeKey;
                                }
                            } catch (Exception e) {
                                Log.i("UcmService", "Exception" + e.getMessage());
                                Log.i("UcmService", "failed to store ODE key");
                                return 269;
                            }
                        }
                        SystemProperties.set("persist.security.ucs", "1");
                        SystemProperties.set("persist.security.ucs.csname", str);
                        UcmAgentWrapper.AgentInfo agentInfo3 = ucmAgentWrapper.info;
                        int i = agentInfo3.enabledWrap;
                        int i2 = agentInfo3.pinMinLength;
                        int i3 = agentInfo3.pinMaxLength;
                        int i4 = agentInfo3.authMode;
                        int i5 = agentInfo3.authMaxCnt;
                        int i6 = agentInfo3.pukMinLength;
                        int i7 = agentInfo3.pukMaxLength;
                        try {
                            byte[] bytes = agentInfo3.agentId.getBytes("UTF-8");
                            if (bytes == null || bytes.length == 0) {
                                Log.i("UcmService", "csName is empty");
                                return 4;
                            }
                            EFSProperties.ODEProperties loadODEConfig2 = EFSProperties.loadODEConfig();
                            loadODEConfig2.enabledUCSInODE = 1;
                            loadODEConfig2.AID = bArr;
                            loadODEConfig2.storageType = storageTypeIndex;
                            loadODEConfig2.enabledSCP = sCPTypeIndex;
                            loadODEConfig2.enabledWrap = i;
                            loadODEConfig2.pinMinLength = i2;
                            loadODEConfig2.pinMaxLength = i3;
                            loadODEConfig2.authMode = i4;
                            loadODEConfig2.authMaxCnt = i5;
                            loadODEConfig2.pukMinLength = i6;
                            loadODEConfig2.pukMaxLength = i7;
                            loadODEConfig2.csName = bytes;
                            loadODEConfig2.cofiguratorPkg = getConfiguratorPkg(uid);
                            loadODEConfig2.cofiguratorSign = getConfiguratorSignature(uid);
                            loadODEConfig2.pluginSignatureHash = getDigestOfBytes(str2.getBytes());
                            loadODEConfig2.version = 2;
                            try {
                                LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
                                if (locales != null && locales.size() > 0 && (locale = locales.get(0)) != null) {
                                    String language = locale.getLanguage();
                                    if (!TextUtils.isEmpty(language)) {
                                        loadODEConfig2.defaultLanguage = language.getBytes();
                                    }
                                }
                                str3 = "UcmService";
                            } catch (Exception e2) {
                                str3 = "UcmService";
                                Log.i(str3, "The exception occurs " + e2.getMessage());
                            }
                            if (true != EFSProperties.saveODEConfig(loadODEConfig2)) {
                                Log.i(str3, "configureODESettings. failed to save ode config");
                                return 269;
                            }
                            UcmServiceUtil.saveDataToFile("1".getBytes(), "/efs/sec_efs/ucm_ode_mode");
                            return 0;
                        } catch (UnsupportedEncodingException e3) {
                            Log.i("UcmService", "The exception occurs " + e3.getMessage());
                            return 4;
                        }
                    }
                }
                z2 = z2;
                z = z;
            }
        }
        return 14;
    }

    public final int saveTempOdeKey(UcmAgentWrapper ucmAgentWrapper, String str) {
        try {
            EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
            _DekData deks = getOdeVendorSpecific(str).getDeks(str);
            int i = deks.errorCode;
            if (i != 0) {
                return i;
            }
            byte[] bArr = deks.wrappedDek;
            byte[] bArr2 = deks.dek;
            if (bArr2 == null || bArr == null) {
                Log.i("UcmService", "ERROR: key is null");
                return 18;
            }
            byte[] saveODEKey = esecommAdapter.saveODEKey(bArr2);
            if (saveODEKey == null) {
                Log.i("UcmService", "ERROR: failed to wrap dek");
                return 18;
            }
            byte[] saveODEKey2 = esecommAdapter.saveODEKey(bArr);
            if (saveODEKey2 != null) {
                return (UcmServiceUtil.saveDataToFile(saveODEKey, "/efs/sec_efs/tz_esecomm", "ucm_ode_key") && UcmServiceUtil.saveDataToFile(saveODEKey2, "/efs/sec_efs/tz_esecomm", "ucm_ode_key2")) ? 0 : 18;
            }
            Log.i("UcmService", "ERROR: failed to wrap pluginWrappedK0");
            return 18;
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return 24;
        }
    }

    public final boolean compareCallingPkg(byte[] bArr, byte[] bArr2) {
        Log.i("UcmService", "compareCallingPkg is called...");
        boolean z = false;
        try {
            String[] split = new String(bArr, "UTF-8").split(",");
            boolean z2 = false;
            for (String str : new String(bArr2, "UTF-8").split(",")) {
                try {
                    Log.i("UcmService", "compareCallingPkg pkg - " + str);
                    int length = split.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            String str2 = split[i];
                            Log.i("UcmService", "compareCallingPkg cachePkg -" + str2);
                            if (str2.equals(str)) {
                                z2 = true;
                                Log.i("UcmService", "compareCallingPkg match found...");
                                break;
                            }
                            i++;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    z = z2;
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                    return z;
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final byte[] getConfiguratorPkg(int i) {
        Log.i("UcmService", "getConfiguratorPkg is called for adminId-" + i);
        byte[] bArr = null;
        try {
            String[] packagesForUid = this.mPm.getPackagesForUid(i);
            if (packagesForUid != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (int i2 = 0; i2 < packagesForUid.length; i2++) {
                    Log.i("UcmService", "packageName -" + packagesForUid[i2]);
                    byteArrayOutputStream.write(packagesForUid[i2].getBytes("UTF-8"));
                    if (i2 < packagesForUid.length - 1) {
                        byteArrayOutputStream.write(44);
                    }
                }
                byteArrayOutputStream.flush();
                bArr = byteArrayOutputStream.toByteArray();
                if (bArr != null) {
                    Log.i("UcmService", "data size -" + bArr.length);
                }
            }
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
        return bArr;
    }

    public final byte[] getConfiguratorSignature(int i) {
        Signature[] signatureArr;
        Log.i("UcmService", "getConfiguratorSignature is called for adminId-" + i);
        int callingUid = Binder.getCallingUid();
        byte[] bArr = null;
        try {
            String[] packagesForUid = this.mPm.getPackagesForUid(i);
            PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
            if (packagesForUid != null) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= packagesForUid.length) {
                        break;
                    }
                    Log.i("UcmService", "packageName -" + packagesForUid[i3]);
                    PackageInfo packageInfo = packageManagerAdapter.getPackageInfo(packagesForUid[i3], 64, UserHandle.getUserId(callingUid));
                    if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length <= 0) {
                        i3++;
                    } else {
                        Log.i("UcmService", "Found signature...");
                        String[] strArr = new String[packageInfo.signatures.length];
                        while (true) {
                            Signature[] signatureArr2 = packageInfo.signatures;
                            if (i2 >= signatureArr2.length) {
                                break;
                            }
                            strArr[i2] = signatureArr2[i2].toCharsString();
                            i2++;
                        }
                        bArr = getDigestOfBytes(TextUtils.join(",", strArr).getBytes("UTF-8"));
                    }
                }
                if (bArr != null) {
                    Log.i("UcmService", "data size -" + bArr.length);
                }
            }
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
        return bArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:90:0x016e A[Catch: IOException -> 0x016a, TryCatch #18 {IOException -> 0x016a, blocks: (B:99:0x0166, B:90:0x016e, B:92:0x0173), top: B:98:0x0166 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0173 A[Catch: IOException -> 0x016a, TRY_LEAVE, TryCatch #18 {IOException -> 0x016a, blocks: (B:99:0x0166, B:90:0x016e, B:92:0x0173), top: B:98:0x0166 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0166 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v24, types: [java.io.InputStream, java.security.DigestInputStream] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] getDigestOfBytes(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        StringBuilder sb;
        MessageDigest messageDigest;
        try {
            try {
                messageDigest = MessageDigest.getInstance("SHA256");
                byteArrayInputStream = new ByteArrayInputStream(bArr);
            } catch (Throwable th) {
                th = th;
            }
            try {
                bArr = new DigestInputStream(byteArrayInputStream, messageDigest);
            } catch (FileNotFoundException e) {
                e = e;
                bArr = 0;
                bufferedInputStream2 = null;
            } catch (IOException e2) {
                e = e2;
                bArr = 0;
                bufferedInputStream2 = null;
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                bArr = 0;
                bufferedInputStream2 = null;
            } catch (Exception e4) {
                e = e4;
                bArr = 0;
                bufferedInputStream2 = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = null;
                th = th;
                bArr = bufferedInputStream;
                if (bArr != 0) {
                    try {
                        bArr.close();
                    } catch (IOException e5) {
                        Log.i("UcmService", "The exception occurs " + e5.getMessage());
                        throw th;
                    }
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                throw th;
            }
            try {
                bufferedInputStream2 = new BufferedInputStream(bArr);
                do {
                    try {
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                        if (bArr != 0) {
                            try {
                                bArr.close();
                            } catch (IOException e7) {
                                e = e7;
                                sb = new StringBuilder();
                                sb.append("The exception occurs ");
                                sb.append(e.getMessage());
                                Log.i("UcmService", sb.toString());
                                return null;
                            }
                        }
                        if (bufferedInputStream2 != null) {
                            bufferedInputStream2.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        return null;
                    } catch (IOException e8) {
                        e = e8;
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                        if (bArr != 0) {
                            try {
                                bArr.close();
                            } catch (IOException e9) {
                                e = e9;
                                sb = new StringBuilder();
                                sb.append("The exception occurs ");
                                sb.append(e.getMessage());
                                Log.i("UcmService", sb.toString());
                                return null;
                            }
                        }
                        if (bufferedInputStream2 != null) {
                            bufferedInputStream2.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        return null;
                    } catch (NoSuchAlgorithmException e10) {
                        e = e10;
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                        if (bArr != 0) {
                            try {
                                bArr.close();
                            } catch (IOException e11) {
                                e = e11;
                                sb = new StringBuilder();
                                sb.append("The exception occurs ");
                                sb.append(e.getMessage());
                                Log.i("UcmService", sb.toString());
                                return null;
                            }
                        }
                        if (bufferedInputStream2 != null) {
                            bufferedInputStream2.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        return null;
                    } catch (Exception e12) {
                        e = e12;
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                        if (bArr != 0) {
                            try {
                                bArr.close();
                            } catch (IOException e13) {
                                e = e13;
                                sb = new StringBuilder();
                                sb.append("The exception occurs ");
                                sb.append(e.getMessage());
                                Log.i("UcmService", sb.toString());
                                return null;
                            }
                        }
                        if (bufferedInputStream2 != null) {
                            bufferedInputStream2.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        return null;
                    }
                } while (bufferedInputStream2.read() != -1);
                byte[] digest = messageDigest.digest();
                try {
                    bArr.close();
                    bufferedInputStream2.close();
                    byteArrayInputStream.close();
                } catch (IOException e14) {
                    Log.i("UcmService", "The exception occurs " + e14.getMessage());
                }
                return digest;
            } catch (FileNotFoundException e15) {
                e = e15;
                bufferedInputStream2 = null;
            } catch (IOException e16) {
                e = e16;
                bufferedInputStream2 = null;
            } catch (NoSuchAlgorithmException e17) {
                e = e17;
                bufferedInputStream2 = null;
            } catch (Exception e18) {
                e = e18;
                bufferedInputStream2 = null;
            } catch (Throwable th3) {
                bufferedInputStream = null;
                th = th3;
                if (bArr != 0) {
                }
                if (bufferedInputStream != null) {
                }
                if (byteArrayInputStream != null) {
                }
                throw th;
            }
        } catch (FileNotFoundException e19) {
            e = e19;
            bArr = 0;
            byteArrayInputStream = null;
            bufferedInputStream2 = null;
        } catch (IOException e20) {
            e = e20;
            bArr = 0;
            byteArrayInputStream = null;
            bufferedInputStream2 = null;
        } catch (NoSuchAlgorithmException e21) {
            e = e21;
            bArr = 0;
            byteArrayInputStream = null;
            bufferedInputStream2 = null;
        } catch (Exception e22) {
            e = e22;
            bArr = 0;
            byteArrayInputStream = null;
            bufferedInputStream2 = null;
        } catch (Throwable th4) {
            th = th4;
            byteArrayInputStream = null;
            bufferedInputStream = null;
        }
    }

    public Bundle getODESettingsConfiguration() {
        Log.i("UcmService", "getODESettingsConfiguration");
        this.mSecurityHelper.checkDeviceIntegrity();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle = new Bundle();
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig == null) {
            return bundle;
        }
        if (UcmServiceODE.isUcmOdeEnabled()) {
            Log.i("UcmService", "Device is encrypted as UCM");
            bundle.putBoolean("odeEnabled", true);
        }
        byte[] bArr = loadODEConfig.csName;
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    String str = new String(bArr, "UTF-8");
                    Log.i("UcmService", "agentId : " + str);
                    bundle.putString("id", str);
                }
            } catch (UnsupportedEncodingException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
        bundle.putByteArray("odeSignature", loadODEConfig.pluginSignatureHash);
        bundle.putByteArray("aid", loadODEConfig.AID);
        if (isVaildStorageType(loadODEConfig.storageType)) {
            bundle.putString("storagetype", EFSProperties.STORAGE_TYPES[loadODEConfig.storageType]);
        }
        if (isVaildSCPType(loadODEConfig.enabledSCP)) {
            bundle.putString("scptype", EFSProperties.SCP_TYPES[loadODEConfig.enabledSCP]);
        }
        return bundle;
    }

    public final boolean isVaildStorageType(int i) {
        return i >= 0 && i < EFSProperties.STORAGE_TYPES.length;
    }

    public final boolean isVaildSCPType(int i) {
        return i >= 0 && i < EFSProperties.SCP_TYPES.length;
    }

    public final void deleteODEConfigInFileIfExist() {
        Log.i("UcmService", "deleteODEConfigInFileIfExist");
        File file = new File("/efs/sec_efs", "odeConfig");
        if (!file.exists()) {
            Log.i("UcmService", "ODE config file does not exist");
        } else {
            Log.i("UcmService", "ODE config file exist so delete it");
            file.delete();
        }
    }

    public String getDetailErrorMessage(String str, int i) {
        Log.i("UcmService", "getDetailErrorMessage uri : " + str + ", errorCode : " + i);
        this.mSecurityHelper.checkDeviceIntegrity();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is not proper");
            return null;
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i("UcmService", "Cannot find agent");
            return null;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            return null;
        }
        return activeAgent.getDetailErrorMessage(i);
    }

    public void updateAgentList() {
        this.mSecurityHelper.checkDeviceIntegrity();
        int callingUid = Binder.getCallingUid();
        Log.i("UcmService", "updateAgentList : " + callingUid);
        Message message = new Message();
        message.what = 1;
        message.arg1 = callingUid;
        this.mHandler.sendMessage(message);
    }

    public Bundle getStatus(String str) {
        UcmAgentWrapper activeAgent;
        Log.i("UcmService", "getStatus : " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "csName is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (true == "boot_test".equals(str)) {
            activeAgent = getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
        } else {
            activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        }
        if (activeAgent == null) {
            Log.i("UcmService", "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (activeAgent.info == null) {
            Log.i("UcmService", "no agent info found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Bundle status = activeAgent.getStatus();
        if (status == null) {
            Bundle bundle3 = new Bundle();
            Log.i("UcmService", "ERROR: Null Response received from agent");
            bundle3.putInt("errorresponse", 13);
            return bundle3;
        }
        Log.i("UcmService", "getStatus success");
        int i = status.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        int i2 = status.getInt("remainCnt", -1);
        UcmAgentWrapper.AgentInfo agentInfo = activeAgent.info;
        int i3 = agentInfo.authMaxCnt;
        int i4 = status.getInt("minPinLength", agentInfo.pinMinLength);
        int i5 = status.getInt("maxPinLength", activeAgent.info.pinMaxLength);
        UcmAgentWrapper.AgentInfo agentInfo2 = activeAgent.info;
        int i6 = agentInfo2.authMode;
        int i7 = agentInfo2.pukMinLength;
        int i8 = agentInfo2.pukMaxLength;
        Log.i("UcmService", "values get from agent : " + i + " " + i2 + " " + i3 + " " + i4 + " " + i5 + " " + i6 + " " + i7 + " " + i8);
        applyMetaData(status, "maxAuthCnt", i3);
        applyMetaData(status, "maxPinLength", i5);
        applyMetaData(status, "minPinLength", i4);
        applyMetaData(status, "authMode", i6);
        applyMetaData(status, "minPukLength", i7);
        applyMetaData(status, "maxPukLength", i8);
        displayToastFromAgentResponse(this.mContext, status);
        return status;
    }

    public final void applyMetaData(Bundle bundle, String str, int i) {
        if (bundle.containsKey(str)) {
            return;
        }
        bundle.putInt(str, i);
    }

    public Bundle resetUser(String str, int i) {
        Log.i("UcmService", "resetUser " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && callingUid == 1000) {
            callingUid = uid;
        }
        int i2 = getuseridforuid(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, false, null) == 0) {
                Log.i("UcmService", "WARNING!!!! resetUser is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            Bundle resetUser = activeAgent.resetUser(i);
            if (resetUser == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 13);
                return bundle2;
            }
            Log.i("UcmService", "resetUser Response from plugin:  error code = " + resetUser.getInt("errorresponse"));
            return resetUser;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle resetUid(String str, int i) {
        Log.i("UcmService", "resetUid " + str);
        this.mSecurityHelper.checkDeviceIntegrity();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid != -1 && callingUid != uid && callingUid == 1000) {
            callingUid = uid;
        }
        int i2 = getuseridforuid(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, callingUid, false, null) == 0) {
                Log.i("UcmService", "WARNING!!!! resetUid is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            Bundle resetUid = activeAgent.resetUid(i);
            if (resetUid == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 13);
                return bundle2;
            }
            Log.i("UcmService", "resetUid Response from plugin:  error code = " + resetUid.getInt("errorresponse"));
            return resetUid;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle containsAlias(String str, int i) {
        this.mSecurityHelper.checkDeviceIntegrity();
        int callingUid = Binder.getCallingUid();
        Bundle bundle = new Bundle();
        Log.i("UcmService", "containsAlias " + str);
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int uid = ucmUri.getUid();
        if (uid == -1) {
            uid = callingUid;
        }
        int i2 = getuseridforuid(uid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(getActiveAgent(ucmUri.getSource()), i2, uid, false, ucmUri.getRawAlias()) == 0) {
                Log.i("UcmService", "WARNING!!!! containsAlias is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            Bundle containsAlias = activeAgent.containsAlias(ucmUri.getRawAlias(), i, callingUid);
            if (containsAlias == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 13);
                return bundle2;
            }
            Log.i("UcmService", "containsAlias Response from plugin:  error code = " + containsAlias.getInt("errorresponse"));
            return containsAlias;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public UcmAgentWrapper getActiveAgent(String str) {
        this.mSecurityHelper.checkDeviceIntegrity();
        if (str == null) {
            return null;
        }
        Log.i("UcmService", "finding active agent " + str);
        for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (str.equals(ucmAgentWrapper.info.f1778id)) {
                Log.i("UcmService", "found active agent " + ucmAgentWrapper.componentName);
                return ucmAgentWrapper;
            }
        }
        return null;
    }

    public ucmRetParcelable getOdeKey(String str, byte[] bArr) {
        Log.i("UcmService", "getOdeKey [" + str + "]");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getOdeKey");
            if (bArr == null || bArr.length == 0) {
                Log.i("UcmService", "getOdeKey. wrappedKey is empty");
                return getResponseParcel(16);
            }
            try {
                byte[] oDEKey = EsecommAdapter.getEsecommAdapter().getODEKey(bArr);
                if (oDEKey == null) {
                    return getResponseParcel(13);
                }
                return getResponseParcel(0, oDEKey);
            } catch (Exception e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
                return getResponseParcel(24);
            }
        } catch (Exception e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
            return getResponseParcel(15);
        }
    }

    public ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) {
        Log.i("UcmService", "notifyVoldComplete [" + str + "]");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("notifyVoldComplete");
            this.mHandler.sendEmptyMessage(6);
            this.mIsVoldCompleteNotified = true;
            return getResponseParcel(0, new byte[]{111, 107});
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public ucmRetParcelable getDekForVold(String str, byte[] bArr) {
        Log.i("UcmService", "getDekForVold [" + str + "]");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDekForVold");
            return getOdeVendorSpecific(str).getDekForVold(str, bArr);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
        Log.i("UcmService", "getDekForVoldInternalKey [" + str + "]");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDekForVoldInternalKey");
            if (bArr == null || bArr.length == 0) {
                Log.i("UcmService", "getDekForVoldInternalKey. key is empty");
                return getResponseParcel(16);
            }
            return getOdeVendorSpecific(str).getDekForVoldInternalKey(str, bArr);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public ucmRetParcelable getODEConfigurationForVold(String str) {
        Log.i("UcmService", "getODEConfigurationForVold [" + str + "]");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getODEConfigurationForVold");
            EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
            ArrayList arrayList = new ArrayList();
            if (loadODEConfig.enabledWrap == 1) {
                arrayList.add("enabledWrap");
            }
            Iterator it = arrayList.iterator();
            int i = 0;
            while (it.hasNext()) {
                i = i + ((String) it.next()).length() + 1;
            }
            byte[] bArr = new byte[i];
            try {
                Iterator it2 = arrayList.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    bArr[i2] = (byte) str2.length();
                    int i3 = i2 + 1;
                    System.arraycopy(str2.getBytes("UTF-8"), 0, bArr, i3, str2.length());
                    i2 = i3 + str2.length();
                }
            } catch (UnsupportedEncodingException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
            return getResponseParcel(0, bArr);
        } catch (Exception e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
            return getResponseParcel(15);
        }
    }

    public final UcmVendorSpecific getOdeVendorSpecific(String str) {
        if (this.mUcmErcomSpecific == null) {
            this.mUcmErcomSpecific = new UcmErcomSpecific(getODEAgent());
        }
        return this.mUcmErcomSpecific;
    }

    public class _DekData {
        public int errorCode;
        public byte[] dek = null;
        public byte[] wrappedDek = null;

        public _DekData(int i) {
            this.errorCode = i;
        }
    }

    public class UcmErcomSpecific implements UcmVendorSpecific {
        public UcmAgentWrapper mAgent;

        public UcmErcomSpecific(UcmAgentWrapper ucmAgentWrapper) {
            this.mAgent = ucmAgentWrapper;
        }

        @Override // com.samsung.ucm.ucmservice.CredentialManagerService.UcmVendorSpecific
        public _DekData getDeks(String str) {
            Log.i("UcmService_ercom", "getDeks " + str);
            UcmAgentWrapper oDEAgent = CredentialManagerService.this.getODEAgent();
            this.mAgent = oDEAgent;
            ucmRetParcelable generateWrappedDek = CredentialManagerService.this.generateWrappedDek(str, oDEAgent);
            int i = generateWrappedDek.mResult;
            if (i != 0) {
                return new _DekData(i);
            }
            byte[] bArr = generateWrappedDek.mData;
            if (bArr == null || bArr.length == 0) {
                Log.i("UcmService_ercom", "getDeks. generateWrappedDek return empty");
                return new _DekData(18);
            }
            ucmRetParcelable unwrapDek = CredentialManagerService.this.unwrapDek(str, bArr, this.mAgent);
            int i2 = unwrapDek.mResult;
            if (i2 != 0) {
                return new _DekData(i2);
            }
            byte[] bArr2 = unwrapDek.mData;
            if (bArr2 == null || bArr2.length == 0) {
                Log.i("UcmService_ercom", "getDeks. unwrapDek return empty");
                return new _DekData(18);
            }
            _DekData _dekdata = new _DekData(0);
            _dekdata.wrappedDek = bArr;
            _dekdata.dek = unwrapDek.mData;
            return _dekdata;
        }

        @Override // com.samsung.ucm.ucmservice.CredentialManagerService.UcmVendorSpecific
        public ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
            int i;
            Log.i("UcmService_ercom", "getDekForVoldInternalKey " + str);
            if (CredentialManagerService.this.mIsFbeEnabled) {
                byte[] deriveCEKey = EsecommAdapter.getEsecommAdapter().deriveCEKey(bArr);
                if (deriveCEKey == null) {
                    Log.i("UcmService_ercom", "getDekForVoldInternalKey. deriveCEKey returns empty");
                    i = 18;
                } else {
                    i = 0;
                }
                return CredentialManagerService.this.getResponseParcel(i, deriveCEKey);
            }
            UcmAgentWrapper oDEAgent = CredentialManagerService.this.getODEAgent();
            this.mAgent = oDEAgent;
            return CredentialManagerService.this.unwrapDek(str, bArr, oDEAgent);
        }

        @Override // com.samsung.ucm.ucmservice.CredentialManagerService.UcmVendorSpecific
        public ucmRetParcelable getDekForVold(String str, byte[] bArr) {
            byte[] bArr2;
            Log.i("UcmService_ercom", "getDekForVold " + str);
            UcmAgentWrapper oDEAgent = CredentialManagerService.this.getODEAgent();
            this.mAgent = oDEAgent;
            ucmRetParcelable unwrapDek = CredentialManagerService.this.unwrapDek(str, bArr, oDEAgent);
            if (unwrapDek.mResult != 0 || (bArr2 = unwrapDek.mData) == null || bArr2.length == 0) {
                Log.i("UcmService_ercom", "getDekForVold. unwrapDek failed");
                return unwrapDek;
            }
            return getDekForVoldInternalKey(str, bArr2);
        }
    }

    public final UcmAgentWrapper getODEAgent() {
        UcmAgentWrapper.AgentInfo agentInfo;
        if (DBG) {
            Log.i("UcmService", "getODEAgent");
        }
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig == null || loadODEConfig.csName == null) {
            Log.i("UcmService", "Failed to load ODE properties");
            return null;
        }
        for (UcmAgentWrapper ucmAgentWrapper : this.mUcmServiceAgentManager.getActiveAgentList()) {
            if (ucmAgentWrapper != null && (agentInfo = ucmAgentWrapper.info) != null && agentInfo.agentId != null) {
                try {
                    if (new String(loadODEConfig.csName, "UTF-8").equals(ucmAgentWrapper.info.agentId)) {
                        if (DBG) {
                            Log.i("UcmService", "find agent for ODE");
                        }
                        return ucmAgentWrapper;
                    }
                    continue;
                } catch (UnsupportedEncodingException e) {
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                }
            }
        }
        return null;
    }

    public final void updateKeyguardConfig(int i) {
        Log.i("UcmService", "updateKeyguardConfig");
        try {
            String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i);
            if (keyguardStorageForCurrentUser == null || keyguardStorageForCurrentUser.equals("none")) {
                Log.i("UcmService", "remove useless keyguard config file");
                File file = new File("/efs/sec_efs", "keyguardConfig");
                if (!file.exists()) {
                    Log.i("UcmService", "keyguard config file does not exist");
                } else {
                    Log.i("UcmService", "keyguard config file exist so delete it");
                    file.delete();
                }
            }
        } catch (Exception e) {
            Log.i("UcmService", "Exception" + e.getMessage());
        }
    }

    public void showEnforcedLockTypeNotification(int i, String str) {
        Log.i("UcmService", "showEnforcedLockTypeNotification : " + i);
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        this.mNotificationManager = notificationManager;
        if (notificationManager == null) {
            Log.i("UcmService", "Failed to get NotificationManager");
            return;
        }
        notificationManager.createNotificationChannel(new NotificationChannel("UCM_KEYGUARD_NOTIFICATION", "UCM Keyguard Configuration Notification", 4));
        try {
            this.mContext.unregisterReceiver(this.mOnNotiRemoveBroadcastReceiver);
        } catch (Exception unused) {
        }
        this.mContext.registerReceiver(this.mOnNotiRemoveBroadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.ACTION_REMOVE_NOTIFICATION"));
        showEnforcedLockTypeNotificationIntenal(i, str);
    }

    public final void showEnforcedLockTypeNotificationIntenal(int i, String str) {
        int lastIndexOf;
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE");
            intent.putExtra("CS_NAME", str);
            intent.putExtra("USER_ID", i);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, i, intent, 201326592);
            String str2 = "";
            if (str != null && !str.equals("") && (lastIndexOf = str.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR)) != -1) {
                str2 = str.substring(lastIndexOf + 1, str.length());
            }
            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.ACTION_REMOVE_NOTIFICATION");
            intent2.putExtra("CS_NAME", str);
            intent2.putExtra("USER_ID", i);
            this.mNotificationManager.notify(i + 8000, new Notification.Builder(this.mContext, "UCM_KEYGUARD_NOTIFICATION").setContentIntent(broadcast).setDeleteIntent(PendingIntent.getBroadcast(this.mContext, i, intent2, 201326592)).setSmallIcon(R.drawable.ic_dialog_alert).setContentTitle(str2).setContentText(this.mContext.getString(17043056)).setOngoing(true).build());
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public void removeEnforcedLockTypeNotification(int i) {
        Log.i("UcmService", "removeEnforcedLockTypeNotification : " + i);
        this.mSecurityHelper.checkDeviceIntegrity();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            Log.i("UcmService", "Failed to get NotificationManager");
            return;
        }
        notificationManager.cancel(i + 8000);
        try {
            this.mContext.unregisterReceiver(this.mOnNotiRemoveBroadcastReceiver);
        } catch (Exception unused) {
        }
    }

    public final void enforceLockType(int i, String str) {
        try {
            PasswordPolicy passwordPolicy = (PasswordPolicy) ServiceManager.getService("password_policy");
            if (passwordPolicy != null && passwordPolicy.isChangeRequestedAsUser(i) > 0) {
                passwordPolicy.setPwdChangeRequestedForUser(i, 0, i);
            }
            UserHandle userHandle = new UserHandle(i);
            Log.i("UcmService", "enforceLockType called for userID : " + i);
            Intent intent = new Intent();
            intent.setClassName("com.android.settings", "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
            Log.i("UcmService", " csName : " + str);
            intent.putExtra("lockscreen.ucscredentialstoragename", str);
            intent.addFlags(268435456);
            intent.addFlags(4194304);
            intent.addFlags(8388608);
            this.mContext.startActivityAsUser(intent, userHandle);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public final void showODEProgressNotification() {
        try {
            if (!UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                if (DBG) {
                    Log.i("UcmService", "UCM ODE is not enabled");
                    return;
                }
                return;
            }
            Log.i("UcmService", "showODEProgressNotification");
            NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
            if (notificationManager == null) {
                Log.i("UcmService", "Failed to get Notification Manager");
                return;
            }
            notificationManager.createNotificationChannel(new NotificationChannel("UCM_ODE_NOTIFICATION", "UCM ODE Progress Notification", 4));
            Notification.Builder contentTitle = new Notification.Builder(this.mContext, "UCM_ODE_NOTIFICATION").setContentTitle(getODEVendorName());
            Log.i("UcmService", "ODE Progress is done");
            notificationManager.notify(9000, contentTitle.setSmallIcon(R.drawable.ic_dialog_info).setContentText(this.mContext.getString(17043055)).build());
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public final String getODEVendorName() {
        byte[] bArr;
        String str;
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        String str2 = "";
        if (loadODEConfig == null || (bArr = loadODEConfig.csName) == null || bArr.length <= 0) {
            return "";
        }
        try {
            str = new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e = e;
        }
        try {
            Log.i("UcmService", "ODE Vendor Name : " + str);
            return str;
        } catch (UnsupportedEncodingException e2) {
            e = e2;
            str2 = str;
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return str2;
        }
    }

    public final void updateSystemUIMonitor(String str) {
        ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback = this.mSystemUICallback;
        if (iCredentialManagerServiceSystemUICallback != null) {
            try {
                iCredentialManagerServiceSystemUICallback.setUCMKeyguardVendor(str);
            } catch (Exception e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
    }

    public final void updateSystemUIMonitor() {
        String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(0);
        ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback = this.mSystemUICallback;
        if (iCredentialManagerServiceSystemUICallback != null) {
            try {
                iCredentialManagerServiceSystemUICallback.setUCMKeyguardVendor(keyguardStorageForCurrentUser);
            } catch (Exception e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
    }

    public void registerSystemUICallback(ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) {
        this.mSystemUICallback = iCredentialManagerServiceSystemUICallback;
        updateSystemUIMonitor();
    }

    public final void sendRefreshFinishIntent(int i) {
        String[] strArr;
        Log.i("UcmService", "sendRefreshFinishIntent : " + i);
        int i2 = getuseridforuid(i);
        Log.i("UcmService", "sendRefreshFinishIntent calling user Id : " + i2);
        Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_REFRESH_AGENT_DONE");
        try {
            strArr = this.mPm.getPackagesForUid(i);
        } catch (RemoteException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            strArr = null;
        }
        if (strArr == null) {
            Log.i("UcmService", "Failed to find callingUid package");
            return;
        }
        for (String str : strArr) {
            if (str == null) {
                Log.i("UcmService", "calling package is eampty, so continue...");
            } else {
                intent.setPackage(str);
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2));
            }
        }
    }

    public final int getIntLen(int i) {
        int i2 = 0;
        while (i > 0) {
            i2++;
            i /= 10;
        }
        return i2;
    }

    public final int getUid0FromUid(int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        int intLen = getIntLen(i2);
        int i3 = 1;
        for (int i4 = 0; i4 < getIntLen(i) - intLen; i4++) {
            i3 *= 10;
        }
        return i % (i3 * i2);
    }

    public Bundle delegateLoadTa(boolean z) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateLoadTa");
        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
        if (esecommAdapter == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("errorresponse", 13);
            bundle.putInt("intresponse", -1);
            return bundle;
        }
        int loadTa = esecommAdapter.loadTa(z);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("intresponse", loadTa);
        bundle2.putInt("errorresponse", 0);
        return bundle2;
    }

    public Bundle delegateUnloadTa() {
        this.mSecurityHelper.checkCallerPermissionFor("delegateUnloadTa");
        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
        if (esecommAdapter == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("errorresponse", 13);
            bundle.putInt("intresponse", -1);
            return bundle;
        }
        int unloadTa = esecommAdapter.unloadTa();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("intresponse", unloadTa);
        bundle2.putInt("errorresponse", 0);
        return bundle2;
    }

    public Bundle delegateGetTaProfile() {
        this.mSecurityHelper.checkCallerPermissionFor("delegateGetTaProfile");
        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
        if (esecommAdapter == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("errorresponse", 13);
            bundle.putByteArray("bytearrayresponse", null);
            return bundle;
        }
        byte[] taProfile = esecommAdapter.getTaProfile();
        if (taProfile == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 13);
            bundle2.putByteArray("bytearrayresponse", null);
            return bundle2;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putByteArray("bytearrayresponse", taProfile);
        bundle3.putInt("errorresponse", 0);
        return bundle3;
    }

    public Bundle delegateWrapSessionKey(byte[] bArr) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateWrapSessionKey");
        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
        if (esecommAdapter == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("errorresponse", 13);
            bundle.putByteArray("bytearrayresponse", null);
            return bundle;
        }
        byte[] wrapSessionKey = esecommAdapter.wrapSessionKey(bArr);
        if (wrapSessionKey == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 13);
            bundle2.putByteArray("bytearrayresponse", null);
            return bundle2;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putByteArray("bytearrayresponse", wrapSessionKey);
        bundle3.putInt("errorresponse", 0);
        return bundle3;
    }

    public Bundle delegateProcessTACommand(Bundle bundle) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateProcessTACommand");
        EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
        if (esecommAdapter == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 13);
            bundle2.putByteArray("tadata", null);
            return bundle2;
        }
        TAResponse processTACommand = esecommAdapter.processTACommand(new TARequest(bundle.getInt("tacommandid"), bundle.getInt("tamagicnumber"), bundle.getInt("taversion"), bundle.getInt("tadatalength"), bundle.getByteArray("tadata")));
        if (processTACommand == null) {
            Bundle bundle3 = new Bundle();
            bundle3.putInt("errorresponse", 13);
            bundle3.putByteArray("tadata", null);
            return bundle3;
        }
        Bundle bundle4 = new Bundle();
        bundle4.putInt("errorresponse", 0);
        bundle4.putInt("tacommandid", bundle.getInt("tacommandid"));
        bundle4.putInt("tamagicnumber", bundle.getInt("tamagicnumber"));
        bundle4.putInt("taversion", bundle.getInt("taversion"));
        bundle4.putInt("tadatalength", processTACommand.getData() != null ? processTACommand.getData().length : -1);
        bundle4.putByteArray("tadata", processTACommand.getData());
        bundle4.putInt("taerrorcode", processTACommand.getErrCode());
        bundle4.putString("taerrordescription", processTACommand.getErrDescription());
        return bundle4;
    }

    public byte[] delegateReadFile(String str) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateReadFile");
        return this.mEsePluginDelegationHelper.readFromFile(str);
    }

    public boolean delegateSaveFile(String str, byte[] bArr) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateSaveFile");
        return this.mEsePluginDelegationHelper.saveToFile(str, bArr);
    }

    public boolean delegateDeleteFile(String str) {
        this.mSecurityHelper.checkCallerPermissionFor("delegateDeleteFile");
        return this.mEsePluginDelegationHelper.deleteFile(str);
    }
}
