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
import android.content.pm.IPackageManager;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0;
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
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.core.ucmRetParcelable;
import com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.ucm.ucmservice.appletmanage.UcmServiceAppletHelper;
import com.samsung.ucm.ucmservice.keystore.UcmSignHelper;
import com.samsung.ucm.ucmservice.keystore.UcmSignHelperFactory;
import com.samsung.ucm.ucmservice.security.UcmSecurityHelper;
import com.sec.esecomm.EsecommAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

public final class CredentialManagerService extends IUcmService.Stub {
    public static final boolean DBG = UcmServiceUtil.isDebug();
    public final UcmServiceAppletHelper mAppletHelper;
    public final Object mAppletsInfoLock;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public boolean mEmergencyEnabled;
    public final AnonymousClass1 mHandler;
    public final boolean mIsFbeEnabled;
    public boolean mIsVoldCompleteNotified;
    public final LockPatternUtils mLockPatternUtils;
    public NotificationManager mNotificationManager;
    public final AnonymousClass2 mOnNotiRemoveBroadcastReceiver;
    public final AnonymousClass2 mPackageRemovedReceiver;
    public final HashMap mPersistentAppletInfo;
    public final AtomicFile mPersistentAppletInfoFile;
    public final SemPersonaManager mPersona;
    public SemPersonaManager mPersonaManager;
    public final IPackageManager mPm;
    public final PolicyManager mPolicyManager;
    public final AnonymousClass2 mRefreshReceiver;
    public final UcmSecurityHelper mSecurityHelper;
    public final UcmSignHelperFactory mSignHelperFactory;
    public ICredentialManagerServiceSystemUICallback mSystemUICallback;
    public UniversalCredentialManagerService mUCMMDMService;
    public UcmErcomSpecific mUcmErcomSpecific;
    public final UcmServiceAgentManager mUcmServiceAgentManager;

    public final class AppletProperties {
        public final int adminId;
        public final byte[] aid;
        public final String appletLocation;
        public final String pluginName;

        public AppletProperties(String str, String str2, int i, byte[] bArr) {
            this.appletLocation = str;
            this.aid = bArr;
            this.pluginName = str2;
            this.adminId = i;
        }
    }

    public final class Injector {
        public UcmServiceAppletHelper mAppletHelper;
        public final Context mContext;
        public final PolicyManager mPolicyManager;
        public UcmSecurityHelper mSecurityHelper;
        public UcmServiceAgentManager mUcmServiceAgentManager;

        public Injector(Context context) {
            this.mContext = context;
            PolicyManager policyManager = new PolicyManager();
            policyManager.mUCMService = null;
            policyManager.hiddenPluginPackages =
                    new ArrayList(
                            Arrays.asList(
                                    "com.sec.smartcard.manager", "com.samsung.ucs.agent.boot"));
            policyManager.mContext = context;
            this.mPolicyManager = policyManager;
            this.mUcmServiceAgentManager = null;
        }
    }

    public final class UcmErcomSpecific {
        public UcmAgentWrapper mAgent;

        public UcmErcomSpecific(UcmAgentWrapper ucmAgentWrapper) {
            this.mAgent = ucmAgentWrapper;
        }

        public final ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
            int i;
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "getDekForVoldInternalKey ", str, "UcmService_ercom");
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            if (!credentialManagerService.mIsFbeEnabled) {
                UcmAgentWrapper oDEAgent = credentialManagerService.getODEAgent();
                this.mAgent = oDEAgent;
                return credentialManagerService.unwrapDek(str, bArr, oDEAgent);
            }
            byte[] deriveCEKey = EsecommAdapter.getEsecommAdapter().deriveCEKey(bArr);
            if (deriveCEKey == null) {
                Log.i("UcmService_ercom", "getDekForVoldInternalKey. deriveCEKey returns empty");
                i = 18;
            } else {
                i = 0;
            }
            credentialManagerService.getClass();
            return new ucmRetParcelable(i, deriveCEKey);
        }
    }

    public final class _DekData {
        public final int errorCode;
        public byte[] dek = null;
        public byte[] wrappedDek = null;

        public _DekData(int i) {
            this.errorCode = i;
        }
    }

    /* renamed from: -$$Nest$mrefreshAgentList, reason: not valid java name */
    public static void m1240$$Nest$mrefreshAgentList(
            CredentialManagerService credentialManagerService) {
        credentialManagerService.mUcmServiceAgentManager.refreshAgentList();
        if (((ArrayList) credentialManagerService.mUcmServiceAgentManager.getActiveAgentList())
                .isEmpty()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        Iterator it =
                ((ArrayList) credentialManagerService.mUcmServiceAgentManager.getActiveAgentList())
                        .iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (!"com.samsung.ucs.agent.ese".equals(ucmAgentWrapper.info.packageName)) {
                intentFilter.addDataSchemeSpecificPart(ucmAgentWrapper.info.packageName, 0);
            }
        }
        credentialManagerService.mContext.registerReceiverAsUser(
                credentialManagerService.mPackageRemovedReceiver,
                UserHandle.ALL,
                intentFilter,
                null,
                credentialManagerService.mHandler);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b  */
    /* renamed from: -$$Nest$mupdateMDMPolicies, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1241$$Nest$mupdateMDMPolicies(
            com.samsung.ucm.ucmservice.CredentialManagerService r6, int r7) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.CredentialManagerService.m1241$$Nest$mupdateMDMPolicies(com.samsung.ucm.ucmservice.CredentialManagerService,"
                    + " int):void");
    }

    /* renamed from: -$$Nest$mwritePersistentAppletsInfoLocked, reason: not valid java name */
    public static void m1242$$Nest$mwritePersistentAppletsInfoLocked(
            CredentialManagerService credentialManagerService) {
        credentialManagerService.getClass();
        Log.i("UcmService", "writePersistentAppletsInfoLocked is called...");
        try {
            FileOutputStream startWrite =
                    credentialManagerService.mPersistentAppletInfoFile.startWrite();
            try {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, "utf-8");
                fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
                fastXmlSerializer.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag((String) null, "applets");
                for (Map.Entry entry : credentialManagerService.mPersistentAppletInfo.entrySet()) {
                    String str = (String) entry.getKey();
                    AppletProperties appletProperties = (AppletProperties) entry.getValue();
                    Log.i("UcmService", "Persistent  key-" + str);
                    fastXmlSerializer.startTag((String) null, "applet");
                    fastXmlSerializer.attribute(
                            (String) null, "appletLocation", appletProperties.appletLocation);
                    fastXmlSerializer.attribute(
                            (String) null, "pluginName", appletProperties.pluginName);
                    fastXmlSerializer.attribute(
                            (String) null, "adminId", Integer.toString(appletProperties.adminId));
                    byte[] bArr = appletProperties.aid;
                    if (bArr != null) {
                        fastXmlSerializer.attribute(
                                (String) null, "aid", convertByteToString(bArr));
                    }
                    fastXmlSerializer.endTag((String) null, "applet");
                }
                fastXmlSerializer.endTag((String) null, "applets");
                fastXmlSerializer.endDocument();
                credentialManagerService.mPersistentAppletInfoFile.finishWrite(startWrite);
                if (startWrite != null) {
                    startWrite.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        }
    }

    public CredentialManagerService(Context context) {
        this(new Injector(context));
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.ucm.ucmservice.CredentialManagerService$2] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.samsung.ucm.ucmservice.CredentialManagerService$1] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.samsung.ucm.ucmservice.CredentialManagerService$2] */
    public CredentialManagerService(Injector injector) {
        UcmSignHelperFactory ucmSignHelperFactory;
        this.mSystemUICallback = null;
        this.mPersistentAppletInfoFile =
                new AtomicFile(new File(Environment.getDataSystemDirectory(), "appletsConfig.xml"));
        this.mUCMMDMService = null;
        this.mLockPatternUtils = null;
        this.mEdmStorageProvider = null;
        this.mPersona = null;
        this.mPersistentAppletInfo = new HashMap();
        Object obj = new Object();
        this.mAppletsInfoLock = obj;
        IPackageManager packageManager = AppGlobals.getPackageManager();
        this.mPm = packageManager;
        this.mIsFbeEnabled = false;
        this.mIsVoldCompleteNotified = false;
        this.mEmergencyEnabled = false;
        final int i = 2;
        this.mPackageRemovedReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
                    public final /* synthetic */ CredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                int intExtra = intent.getIntExtra("USER_ID", -1);
                                Log.i(
                                        "UcmService",
                                        "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                                String stringExtra = intent.getStringExtra("CS_NAME");
                                CredentialManagerService credentialManagerService = this.this$0;
                                credentialManagerService.getClass();
                                try {
                                    PasswordPolicy passwordPolicy =
                                            (PasswordPolicy)
                                                    ServiceManager.getService("password_policy");
                                    if (passwordPolicy != null
                                            && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                            intExtra)
                                                    > 0) {
                                        passwordPolicy.setPwdChangeRequestedForUser(0, intExtra);
                                    }
                                    UserHandle userHandle = new UserHandle(intExtra);
                                    Log.i(
                                            "UcmService",
                                            "enforceLockType called for userID : " + intExtra);
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            KnoxCustomManagerService.SETTING_PKG_NAME,
                                            "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
                                    Log.i("UcmService", " csName : " + stringExtra);
                                    intent2.putExtra(
                                            "lockscreen.ucscredentialstoragename", stringExtra);
                                    intent2.addFlags(268435456);
                                    intent2.addFlags(4194304);
                                    intent2.addFlags(8388608);
                                    credentialManagerService.mContext.startActivityAsUser(
                                            intent2, userHandle);
                                    break;
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                    return;
                                }
                                break;
                            case 1:
                                if (CredentialManagerService.DBG) {
                                    Log.i("UcmService", "onReceive " + intent.getAction());
                                }
                                if (this.this$0.mIsFbeEnabled
                                        || !UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                                    CredentialManagerService credentialManagerService2 =
                                            this.this$0;
                                    if (credentialManagerService2.mIsVoldCompleteNotified) {
                                        credentialManagerService2.showODEProgressNotification();
                                        break;
                                    }
                                } else {
                                    this.this$0.showODEProgressNotification();
                                    EFSProperties.ODEProperties loadODEConfig =
                                            EFSProperties.loadODEConfig();
                                    try {
                                        if (loadODEConfig.version > 1
                                                && loadODEConfig.defaultLanguage != null) {
                                            loadODEConfig.defaultLanguage = null;
                                            Log.i(
                                                    "UcmService",
                                                    "checkUcmOdeDefaultLanguage. remove default"
                                                        + " language");
                                            if (!EFSProperties.saveODEConfig(loadODEConfig)) {
                                                Log.i(
                                                        "UcmService",
                                                        "checkUcmOdeDefaultLanguage. failed to save"
                                                            + " ode prop");
                                                break;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e2,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                Log.i("UcmService", "onReceive " + intent.getAction());
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                if (intExtra2 != -1) {
                                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                            UserHandle.getUserId(intExtra2),
                                            intExtra2,
                                            "Package update in userId-",
                                            " and uid-",
                                            "UcmService");
                                    CredentialManagerService credentialManagerService3 =
                                            this.this$0;
                                    if (!((ArrayList)
                                                    credentialManagerService3
                                                            .mUcmServiceAgentManager
                                                            .getActiveAgentList())
                                            .isEmpty()) {
                                        Iterator it =
                                                ((ArrayList)
                                                                credentialManagerService3
                                                                        .mUcmServiceAgentManager
                                                                        .getActiveAgentList())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((UcmAgentWrapper) it.next()).info.serviceUid
                                                    == intExtra2) {
                                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                                        intExtra2,
                                                        "it is active plugin uid : ",
                                                        "UcmService");
                                                sendEmptyMessage(1);
                                                break;
                                            }
                                        }
                                        break;
                                    } else {
                                        Log.i("UcmService", "No active agent exist");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                String action = intent.getAction();
                                Log.i(
                                        "UcmService",
                                        "inside mRefreshReceiver onReceive : " + action);
                                if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if (!SemEmergencyManager.isEmergencyMode(
                                                this.this$0.mContext)) {
                                            if (this.this$0.mEmergencyEnabled) {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM enabled -> disabled");
                                                CredentialManagerService
                                                        .m1240$$Nest$mrefreshAgentList(this.this$0);
                                                this.this$0.mEmergencyEnabled = false;
                                                break;
                                            }
                                        } else {
                                            CredentialManagerService credentialManagerService4 =
                                                    this.this$0;
                                            if (!credentialManagerService4.mEmergencyEnabled) {
                                                credentialManagerService4.mEmergencyEnabled = true;
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM disabled -> enabled");
                                                break;
                                            } else {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM is enabled nothing to do");
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.i(
                                                "UcmService",
                                                "onReceive : ACTION_LOCKED_BOOT_COMPLETED");
                                        this.this$0.mAppletHelper.checkToRunLccmScript();
                                        break;
                                    }
                                } else {
                                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                                    try {
                                        sendEmptyMessage(1);
                                    } catch (Exception e3) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e3,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                    }
                                    this.this$0.mAppletHelper.checkToRunLccmScript();
                                    break;
                                }
                                break;
                            default:
                                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                                this.this$0.showEnforcedLockTypeNotificationIntenal(
                                        intent.getIntExtra("USER_ID", -1),
                                        intent.getStringExtra("CS_NAME"));
                                break;
                        }
                    }
                };
        final int i2 = 3;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
                    public final /* synthetic */ CredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                int intExtra = intent.getIntExtra("USER_ID", -1);
                                Log.i(
                                        "UcmService",
                                        "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                                String stringExtra = intent.getStringExtra("CS_NAME");
                                CredentialManagerService credentialManagerService = this.this$0;
                                credentialManagerService.getClass();
                                try {
                                    PasswordPolicy passwordPolicy =
                                            (PasswordPolicy)
                                                    ServiceManager.getService("password_policy");
                                    if (passwordPolicy != null
                                            && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                            intExtra)
                                                    > 0) {
                                        passwordPolicy.setPwdChangeRequestedForUser(0, intExtra);
                                    }
                                    UserHandle userHandle = new UserHandle(intExtra);
                                    Log.i(
                                            "UcmService",
                                            "enforceLockType called for userID : " + intExtra);
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            KnoxCustomManagerService.SETTING_PKG_NAME,
                                            "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
                                    Log.i("UcmService", " csName : " + stringExtra);
                                    intent2.putExtra(
                                            "lockscreen.ucscredentialstoragename", stringExtra);
                                    intent2.addFlags(268435456);
                                    intent2.addFlags(4194304);
                                    intent2.addFlags(8388608);
                                    credentialManagerService.mContext.startActivityAsUser(
                                            intent2, userHandle);
                                    break;
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                    return;
                                }
                                break;
                            case 1:
                                if (CredentialManagerService.DBG) {
                                    Log.i("UcmService", "onReceive " + intent.getAction());
                                }
                                if (this.this$0.mIsFbeEnabled
                                        || !UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                                    CredentialManagerService credentialManagerService2 =
                                            this.this$0;
                                    if (credentialManagerService2.mIsVoldCompleteNotified) {
                                        credentialManagerService2.showODEProgressNotification();
                                        break;
                                    }
                                } else {
                                    this.this$0.showODEProgressNotification();
                                    EFSProperties.ODEProperties loadODEConfig =
                                            EFSProperties.loadODEConfig();
                                    try {
                                        if (loadODEConfig.version > 1
                                                && loadODEConfig.defaultLanguage != null) {
                                            loadODEConfig.defaultLanguage = null;
                                            Log.i(
                                                    "UcmService",
                                                    "checkUcmOdeDefaultLanguage. remove default"
                                                        + " language");
                                            if (!EFSProperties.saveODEConfig(loadODEConfig)) {
                                                Log.i(
                                                        "UcmService",
                                                        "checkUcmOdeDefaultLanguage. failed to save"
                                                            + " ode prop");
                                                break;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e2,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                Log.i("UcmService", "onReceive " + intent.getAction());
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                if (intExtra2 != -1) {
                                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                            UserHandle.getUserId(intExtra2),
                                            intExtra2,
                                            "Package update in userId-",
                                            " and uid-",
                                            "UcmService");
                                    CredentialManagerService credentialManagerService3 =
                                            this.this$0;
                                    if (!((ArrayList)
                                                    credentialManagerService3
                                                            .mUcmServiceAgentManager
                                                            .getActiveAgentList())
                                            .isEmpty()) {
                                        Iterator it =
                                                ((ArrayList)
                                                                credentialManagerService3
                                                                        .mUcmServiceAgentManager
                                                                        .getActiveAgentList())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((UcmAgentWrapper) it.next()).info.serviceUid
                                                    == intExtra2) {
                                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                                        intExtra2,
                                                        "it is active plugin uid : ",
                                                        "UcmService");
                                                sendEmptyMessage(1);
                                                break;
                                            }
                                        }
                                        break;
                                    } else {
                                        Log.i("UcmService", "No active agent exist");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                String action = intent.getAction();
                                Log.i(
                                        "UcmService",
                                        "inside mRefreshReceiver onReceive : " + action);
                                if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if (!SemEmergencyManager.isEmergencyMode(
                                                this.this$0.mContext)) {
                                            if (this.this$0.mEmergencyEnabled) {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM enabled -> disabled");
                                                CredentialManagerService
                                                        .m1240$$Nest$mrefreshAgentList(this.this$0);
                                                this.this$0.mEmergencyEnabled = false;
                                                break;
                                            }
                                        } else {
                                            CredentialManagerService credentialManagerService4 =
                                                    this.this$0;
                                            if (!credentialManagerService4.mEmergencyEnabled) {
                                                credentialManagerService4.mEmergencyEnabled = true;
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM disabled -> enabled");
                                                break;
                                            } else {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM is enabled nothing to do");
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.i(
                                                "UcmService",
                                                "onReceive : ACTION_LOCKED_BOOT_COMPLETED");
                                        this.this$0.mAppletHelper.checkToRunLccmScript();
                                        break;
                                    }
                                } else {
                                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                                    try {
                                        sendEmptyMessage(1);
                                    } catch (Exception e3) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e3,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                    }
                                    this.this$0.mAppletHelper.checkToRunLccmScript();
                                    break;
                                }
                                break;
                            default:
                                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                                this.this$0.showEnforcedLockTypeNotificationIntenal(
                                        intent.getIntExtra("USER_ID", -1),
                                        intent.getStringExtra("CS_NAME"));
                                break;
                        }
                    }
                };
        this.mPersonaManager = null;
        this.mUcmErcomSpecific = null;
        final int i3 = 4;
        this.mOnNotiRemoveBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
                    public final /* synthetic */ CredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i3) {
                            case 0:
                                int intExtra = intent.getIntExtra("USER_ID", -1);
                                Log.i(
                                        "UcmService",
                                        "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                                String stringExtra = intent.getStringExtra("CS_NAME");
                                CredentialManagerService credentialManagerService = this.this$0;
                                credentialManagerService.getClass();
                                try {
                                    PasswordPolicy passwordPolicy =
                                            (PasswordPolicy)
                                                    ServiceManager.getService("password_policy");
                                    if (passwordPolicy != null
                                            && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                            intExtra)
                                                    > 0) {
                                        passwordPolicy.setPwdChangeRequestedForUser(0, intExtra);
                                    }
                                    UserHandle userHandle = new UserHandle(intExtra);
                                    Log.i(
                                            "UcmService",
                                            "enforceLockType called for userID : " + intExtra);
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            KnoxCustomManagerService.SETTING_PKG_NAME,
                                            "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
                                    Log.i("UcmService", " csName : " + stringExtra);
                                    intent2.putExtra(
                                            "lockscreen.ucscredentialstoragename", stringExtra);
                                    intent2.addFlags(268435456);
                                    intent2.addFlags(4194304);
                                    intent2.addFlags(8388608);
                                    credentialManagerService.mContext.startActivityAsUser(
                                            intent2, userHandle);
                                    break;
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                    return;
                                }
                                break;
                            case 1:
                                if (CredentialManagerService.DBG) {
                                    Log.i("UcmService", "onReceive " + intent.getAction());
                                }
                                if (this.this$0.mIsFbeEnabled
                                        || !UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                                    CredentialManagerService credentialManagerService2 =
                                            this.this$0;
                                    if (credentialManagerService2.mIsVoldCompleteNotified) {
                                        credentialManagerService2.showODEProgressNotification();
                                        break;
                                    }
                                } else {
                                    this.this$0.showODEProgressNotification();
                                    EFSProperties.ODEProperties loadODEConfig =
                                            EFSProperties.loadODEConfig();
                                    try {
                                        if (loadODEConfig.version > 1
                                                && loadODEConfig.defaultLanguage != null) {
                                            loadODEConfig.defaultLanguage = null;
                                            Log.i(
                                                    "UcmService",
                                                    "checkUcmOdeDefaultLanguage. remove default"
                                                        + " language");
                                            if (!EFSProperties.saveODEConfig(loadODEConfig)) {
                                                Log.i(
                                                        "UcmService",
                                                        "checkUcmOdeDefaultLanguage. failed to save"
                                                            + " ode prop");
                                                break;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e2,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                Log.i("UcmService", "onReceive " + intent.getAction());
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                if (intExtra2 != -1) {
                                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                            UserHandle.getUserId(intExtra2),
                                            intExtra2,
                                            "Package update in userId-",
                                            " and uid-",
                                            "UcmService");
                                    CredentialManagerService credentialManagerService3 =
                                            this.this$0;
                                    if (!((ArrayList)
                                                    credentialManagerService3
                                                            .mUcmServiceAgentManager
                                                            .getActiveAgentList())
                                            .isEmpty()) {
                                        Iterator it =
                                                ((ArrayList)
                                                                credentialManagerService3
                                                                        .mUcmServiceAgentManager
                                                                        .getActiveAgentList())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((UcmAgentWrapper) it.next()).info.serviceUid
                                                    == intExtra2) {
                                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                                        intExtra2,
                                                        "it is active plugin uid : ",
                                                        "UcmService");
                                                sendEmptyMessage(1);
                                                break;
                                            }
                                        }
                                        break;
                                    } else {
                                        Log.i("UcmService", "No active agent exist");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                String action = intent.getAction();
                                Log.i(
                                        "UcmService",
                                        "inside mRefreshReceiver onReceive : " + action);
                                if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if (!SemEmergencyManager.isEmergencyMode(
                                                this.this$0.mContext)) {
                                            if (this.this$0.mEmergencyEnabled) {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM enabled -> disabled");
                                                CredentialManagerService
                                                        .m1240$$Nest$mrefreshAgentList(this.this$0);
                                                this.this$0.mEmergencyEnabled = false;
                                                break;
                                            }
                                        } else {
                                            CredentialManagerService credentialManagerService4 =
                                                    this.this$0;
                                            if (!credentialManagerService4.mEmergencyEnabled) {
                                                credentialManagerService4.mEmergencyEnabled = true;
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM disabled -> enabled");
                                                break;
                                            } else {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM is enabled nothing to do");
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.i(
                                                "UcmService",
                                                "onReceive : ACTION_LOCKED_BOOT_COMPLETED");
                                        this.this$0.mAppletHelper.checkToRunLccmScript();
                                        break;
                                    }
                                } else {
                                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                                    try {
                                        sendEmptyMessage(1);
                                    } catch (Exception e3) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e3,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                    }
                                    this.this$0.mAppletHelper.checkToRunLccmScript();
                                    break;
                                }
                                break;
                            default:
                                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                                this.this$0.showEnforcedLockTypeNotificationIntenal(
                                        intent.getIntExtra("USER_ID", -1),
                                        intent.getStringExtra("CS_NAME"));
                                break;
                        }
                    }
                };
        Context applicationContext = injector.mContext.getApplicationContext();
        this.mContext = applicationContext;
        injector.mContext.getApplicationContext();
        synchronized (UcmSignHelperFactory.class) {
            try {
                if (UcmSignHelperFactory.sInstance == null) {
                    synchronized (UcmSignHelperFactory.class) {
                        try {
                            if (UcmSignHelperFactory.sInstance == null) {
                                UcmSignHelperFactory.sInstance = new UcmSignHelperFactory();
                            }
                        } finally {
                        }
                    }
                }
                ucmSignHelperFactory = UcmSignHelperFactory.sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mSignHelperFactory = ucmSignHelperFactory;
        UcmServiceODE.updateOdeStatus();
        this.mPolicyManager = injector.mPolicyManager;
        this.mLockPatternUtils = new LockPatternUtils(applicationContext);
        this.mEdmStorageProvider = new EdmStorageProvider(applicationContext);
        if (injector.mSecurityHelper == null) {
            Context context = injector.mContext;
            UcmSecurityHelper ucmSecurityHelper = new UcmSecurityHelper();
            ucmSecurityHelper.mPm = AppGlobals.getPackageManager();
            ucmSecurityHelper.mContext = context;
            injector.mSecurityHelper = ucmSecurityHelper;
        }
        UcmSecurityHelper ucmSecurityHelper2 = injector.mSecurityHelper;
        this.mSecurityHelper = ucmSecurityHelper2;
        if (injector.mUcmServiceAgentManager == null) {
            Context context2 = injector.mContext;
            UcmServiceAgentManager ucmServiceAgentManager = new UcmServiceAgentManager();
            ucmServiceAgentManager.mActiveAgentList = new ArrayList();
            ucmServiceAgentManager.mNeedToBindESE = false;
            ucmServiceAgentManager.mContext = context2;
            ucmServiceAgentManager.mSecurityHelper = ucmSecurityHelper2;
            injector.mUcmServiceAgentManager = ucmServiceAgentManager;
        }
        this.mUcmServiceAgentManager = injector.mUcmServiceAgentManager;
        if (injector.mAppletHelper == null) {
            injector.mAppletHelper =
                    new UcmServiceAppletHelper(
                            applicationContext, packageManager, ucmSecurityHelper2);
        }
        this.mAppletHelper = injector.mAppletHelper;
        boolean equals = SystemProperties.get("ro.crypto.type", "unknown").equals("file");
        this.mIsFbeEnabled = equals;
        this.mHandler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                        "CredentialManagerServiceThread")
                                .getLooper()) { // from class:
                    // com.samsung.ucm.ucmservice.CredentialManagerService.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        String[] strArr;
                        int i4 = message.what;
                        if (i4 == 1) {
                            CredentialManagerService.m1240$$Nest$mrefreshAgentList(
                                    CredentialManagerService.this);
                            int i5 = message.arg1;
                            if (i5 != 0) {
                                CredentialManagerService credentialManagerService =
                                        CredentialManagerService.this;
                                credentialManagerService.getClass();
                                Log.i("UcmService", "sendRefreshFinishIntent : " + i5);
                                int userId = UserHandle.getUserId(i5);
                                Log.i(
                                        "UcmService",
                                        "sendRefreshFinishIntent calling user Id : " + userId);
                                Intent intent =
                                        new Intent(
                                                "com.samsung.android.knox.intent.action.UCM_REFRESH_AGENT_DONE");
                                try {
                                    strArr = credentialManagerService.mPm.getPackagesForUid(i5);
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
                                        Log.i(
                                                "UcmService",
                                                "calling package is eampty, so continue...");
                                    } else {
                                        intent.setPackage(str);
                                        credentialManagerService.mContext.sendBroadcastAsUser(
                                                intent, new UserHandle(userId));
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        if (i4 != 4) {
                            if (i4 != 5) {
                                if (i4 != 6) {
                                    return;
                                }
                                CredentialManagerService.this.showODEProgressNotification();
                                return;
                            } else {
                                Log.i("UcmService", "MSG_REFRESH_APPLET_INFO is called...");
                                synchronized (CredentialManagerService.this.mAppletsInfoLock) {
                                    CredentialManagerService
                                            .m1242$$Nest$mwritePersistentAppletsInfoLocked(
                                                    CredentialManagerService.this);
                                    CredentialManagerService.this.readPersistentAppletsInfoLocked();
                                }
                                return;
                            }
                        }
                        Bundle data = message.getData();
                        String string = data.getString("packageName");
                        String string2 = data.getString(Constants.JSON_CLIENT_DATA_STATUS);
                        int i6 = data.getInt("errorCode");
                        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "MSG_PACKAGE_LICENSE_UPDATE packageName-",
                                        string,
                                        ",status-",
                                        string2,
                                        ", errorCode-"),
                                i6,
                                "UcmService");
                        if (string2 != null && string2.equals("success") && i6 == 0) {
                            data.putInt("event", 1);
                            try {
                                CredentialManagerService.this
                                        .getUCMMDMService()
                                        .notifyLicenseStatus(1, string);
                                Log.i("UcmService", "  notifyLicenseStatus Activate status- false");
                            } catch (Exception e2) {
                                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                        .m(
                                                e2,
                                                new StringBuilder("The exception occurs "),
                                                "UcmService");
                            }
                        } else {
                            if (i6 != 203 && i6 != 700 && i6 != 701) {
                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                        i6, "skip network error case: ", "UcmService");
                                return;
                            }
                            data.putInt("event", 2);
                            try {
                                CredentialManagerService.this
                                        .getUCMMDMService()
                                        .notifyLicenseStatus(2, string);
                                Log.i("UcmService", "  notifyLicenseStatus expire status- false");
                            } catch (Exception e3) {
                                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                        .m(
                                                e3,
                                                new StringBuilder("The exception occurs "),
                                                "UcmService");
                            }
                        }
                        CredentialManagerService.m1240$$Nest$mrefreshAgentList(
                                CredentialManagerService.this);
                    }
                };
        final int i4 = 0;
        applicationContext.registerReceiverAsUser(
                new BroadcastReceiver(
                        this) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
                    public final /* synthetic */ CredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i4) {
                            case 0:
                                int intExtra = intent.getIntExtra("USER_ID", -1);
                                Log.i(
                                        "UcmService",
                                        "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                                String stringExtra = intent.getStringExtra("CS_NAME");
                                CredentialManagerService credentialManagerService = this.this$0;
                                credentialManagerService.getClass();
                                try {
                                    PasswordPolicy passwordPolicy =
                                            (PasswordPolicy)
                                                    ServiceManager.getService("password_policy");
                                    if (passwordPolicy != null
                                            && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                            intExtra)
                                                    > 0) {
                                        passwordPolicy.setPwdChangeRequestedForUser(0, intExtra);
                                    }
                                    UserHandle userHandle = new UserHandle(intExtra);
                                    Log.i(
                                            "UcmService",
                                            "enforceLockType called for userID : " + intExtra);
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            KnoxCustomManagerService.SETTING_PKG_NAME,
                                            "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
                                    Log.i("UcmService", " csName : " + stringExtra);
                                    intent2.putExtra(
                                            "lockscreen.ucscredentialstoragename", stringExtra);
                                    intent2.addFlags(268435456);
                                    intent2.addFlags(4194304);
                                    intent2.addFlags(8388608);
                                    credentialManagerService.mContext.startActivityAsUser(
                                            intent2, userHandle);
                                    break;
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                    return;
                                }
                                break;
                            case 1:
                                if (CredentialManagerService.DBG) {
                                    Log.i("UcmService", "onReceive " + intent.getAction());
                                }
                                if (this.this$0.mIsFbeEnabled
                                        || !UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                                    CredentialManagerService credentialManagerService2 =
                                            this.this$0;
                                    if (credentialManagerService2.mIsVoldCompleteNotified) {
                                        credentialManagerService2.showODEProgressNotification();
                                        break;
                                    }
                                } else {
                                    this.this$0.showODEProgressNotification();
                                    EFSProperties.ODEProperties loadODEConfig =
                                            EFSProperties.loadODEConfig();
                                    try {
                                        if (loadODEConfig.version > 1
                                                && loadODEConfig.defaultLanguage != null) {
                                            loadODEConfig.defaultLanguage = null;
                                            Log.i(
                                                    "UcmService",
                                                    "checkUcmOdeDefaultLanguage. remove default"
                                                        + " language");
                                            if (!EFSProperties.saveODEConfig(loadODEConfig)) {
                                                Log.i(
                                                        "UcmService",
                                                        "checkUcmOdeDefaultLanguage. failed to save"
                                                            + " ode prop");
                                                break;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e2,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                Log.i("UcmService", "onReceive " + intent.getAction());
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                if (intExtra2 != -1) {
                                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                            UserHandle.getUserId(intExtra2),
                                            intExtra2,
                                            "Package update in userId-",
                                            " and uid-",
                                            "UcmService");
                                    CredentialManagerService credentialManagerService3 =
                                            this.this$0;
                                    if (!((ArrayList)
                                                    credentialManagerService3
                                                            .mUcmServiceAgentManager
                                                            .getActiveAgentList())
                                            .isEmpty()) {
                                        Iterator it =
                                                ((ArrayList)
                                                                credentialManagerService3
                                                                        .mUcmServiceAgentManager
                                                                        .getActiveAgentList())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((UcmAgentWrapper) it.next()).info.serviceUid
                                                    == intExtra2) {
                                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                                        intExtra2,
                                                        "it is active plugin uid : ",
                                                        "UcmService");
                                                sendEmptyMessage(1);
                                                break;
                                            }
                                        }
                                        break;
                                    } else {
                                        Log.i("UcmService", "No active agent exist");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                String action = intent.getAction();
                                Log.i(
                                        "UcmService",
                                        "inside mRefreshReceiver onReceive : " + action);
                                if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if (!SemEmergencyManager.isEmergencyMode(
                                                this.this$0.mContext)) {
                                            if (this.this$0.mEmergencyEnabled) {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM enabled -> disabled");
                                                CredentialManagerService
                                                        .m1240$$Nest$mrefreshAgentList(this.this$0);
                                                this.this$0.mEmergencyEnabled = false;
                                                break;
                                            }
                                        } else {
                                            CredentialManagerService credentialManagerService4 =
                                                    this.this$0;
                                            if (!credentialManagerService4.mEmergencyEnabled) {
                                                credentialManagerService4.mEmergencyEnabled = true;
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM disabled -> enabled");
                                                break;
                                            } else {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM is enabled nothing to do");
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.i(
                                                "UcmService",
                                                "onReceive : ACTION_LOCKED_BOOT_COMPLETED");
                                        this.this$0.mAppletHelper.checkToRunLccmScript();
                                        break;
                                    }
                                } else {
                                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                                    try {
                                        sendEmptyMessage(1);
                                    } catch (Exception e3) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e3,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                    }
                                    this.this$0.mAppletHelper.checkToRunLccmScript();
                                    break;
                                }
                                break;
                            default:
                                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                                this.this$0.showEnforcedLockTypeNotificationIntenal(
                                        intent.getIntExtra("USER_ID", -1),
                                        intent.getStringExtra("CS_NAME"));
                                break;
                        }
                    }
                },
                UserHandle.ALL,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE"),
                "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
                null,
                2);
        applicationContext.registerReceiver(
                broadcastReceiver,
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "android.intent.action.BOOT_COMPLETED",
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "com.samsung.intent.action.EMERGENCY_STATE_CHANGED"),
                null,
                null,
                2);
        final int i5 = 1;
        applicationContext.registerReceiver(
                new BroadcastReceiver(
                        this) { // from class: com.samsung.ucm.ucmservice.CredentialManagerService.2
                    public final /* synthetic */ CredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i5) {
                            case 0:
                                int intExtra = intent.getIntExtra("USER_ID", -1);
                                Log.i(
                                        "UcmService",
                                        "onReceive : ACTION_ENFORCE_LOCKTYPE : " + intExtra);
                                String stringExtra = intent.getStringExtra("CS_NAME");
                                CredentialManagerService credentialManagerService = this.this$0;
                                credentialManagerService.getClass();
                                try {
                                    PasswordPolicy passwordPolicy =
                                            (PasswordPolicy)
                                                    ServiceManager.getService("password_policy");
                                    if (passwordPolicy != null
                                            && passwordPolicy.isChangeRequestedAsUserFromDb(
                                                            intExtra)
                                                    > 0) {
                                        passwordPolicy.setPwdChangeRequestedForUser(0, intExtra);
                                    }
                                    UserHandle userHandle = new UserHandle(intExtra);
                                    Log.i(
                                            "UcmService",
                                            "enforceLockType called for userID : " + intExtra);
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            KnoxCustomManagerService.SETTING_PKG_NAME,
                                            "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
                                    Log.i("UcmService", " csName : " + stringExtra);
                                    intent2.putExtra(
                                            "lockscreen.ucscredentialstoragename", stringExtra);
                                    intent2.addFlags(268435456);
                                    intent2.addFlags(4194304);
                                    intent2.addFlags(8388608);
                                    credentialManagerService.mContext.startActivityAsUser(
                                            intent2, userHandle);
                                    break;
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                    return;
                                }
                                break;
                            case 1:
                                if (CredentialManagerService.DBG) {
                                    Log.i("UcmService", "onReceive " + intent.getAction());
                                }
                                if (this.this$0.mIsFbeEnabled
                                        || !UcmServiceODE.isUCMODEEnabledWithPropFile()) {
                                    CredentialManagerService credentialManagerService2 =
                                            this.this$0;
                                    if (credentialManagerService2.mIsVoldCompleteNotified) {
                                        credentialManagerService2.showODEProgressNotification();
                                        break;
                                    }
                                } else {
                                    this.this$0.showODEProgressNotification();
                                    EFSProperties.ODEProperties loadODEConfig =
                                            EFSProperties.loadODEConfig();
                                    try {
                                        if (loadODEConfig.version > 1
                                                && loadODEConfig.defaultLanguage != null) {
                                            loadODEConfig.defaultLanguage = null;
                                            Log.i(
                                                    "UcmService",
                                                    "checkUcmOdeDefaultLanguage. remove default"
                                                        + " language");
                                            if (!EFSProperties.saveODEConfig(loadODEConfig)) {
                                                Log.i(
                                                        "UcmService",
                                                        "checkUcmOdeDefaultLanguage. failed to save"
                                                            + " ode prop");
                                                break;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e2,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                Log.i("UcmService", "onReceive " + intent.getAction());
                                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                                if (intExtra2 != -1) {
                                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                            UserHandle.getUserId(intExtra2),
                                            intExtra2,
                                            "Package update in userId-",
                                            " and uid-",
                                            "UcmService");
                                    CredentialManagerService credentialManagerService3 =
                                            this.this$0;
                                    if (!((ArrayList)
                                                    credentialManagerService3
                                                            .mUcmServiceAgentManager
                                                            .getActiveAgentList())
                                            .isEmpty()) {
                                        Iterator it =
                                                ((ArrayList)
                                                                credentialManagerService3
                                                                        .mUcmServiceAgentManager
                                                                        .getActiveAgentList())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((UcmAgentWrapper) it.next()).info.serviceUid
                                                    == intExtra2) {
                                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                                        intExtra2,
                                                        "it is active plugin uid : ",
                                                        "UcmService");
                                                sendEmptyMessage(1);
                                                break;
                                            }
                                        }
                                        break;
                                    } else {
                                        Log.i("UcmService", "No active agent exist");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                String action = intent.getAction();
                                Log.i(
                                        "UcmService",
                                        "inside mRefreshReceiver onReceive : " + action);
                                if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                                    if (!"android.intent.action.LOCKED_BOOT_COMPLETED"
                                            .equals(action)) {
                                        if (!SemEmergencyManager.isEmergencyMode(
                                                this.this$0.mContext)) {
                                            if (this.this$0.mEmergencyEnabled) {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM enabled -> disabled");
                                                CredentialManagerService
                                                        .m1240$$Nest$mrefreshAgentList(this.this$0);
                                                this.this$0.mEmergencyEnabled = false;
                                                break;
                                            }
                                        } else {
                                            CredentialManagerService credentialManagerService4 =
                                                    this.this$0;
                                            if (!credentialManagerService4.mEmergencyEnabled) {
                                                credentialManagerService4.mEmergencyEnabled = true;
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM disabled -> enabled");
                                                break;
                                            } else {
                                                Log.i(
                                                        "UcmService",
                                                        "Already UPSM is enabled nothing to do");
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.i(
                                                "UcmService",
                                                "onReceive : ACTION_LOCKED_BOOT_COMPLETED");
                                        this.this$0.mAppletHelper.checkToRunLccmScript();
                                        break;
                                    }
                                } else {
                                    Log.i("UcmService", "onReceive : ACTION_BOOT_COMPLETED");
                                    try {
                                        sendEmptyMessage(1);
                                    } catch (Exception e3) {
                                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                .m(
                                                        e3,
                                                        new StringBuilder("The exception occurs "),
                                                        "UcmService");
                                    }
                                    this.this$0.mAppletHelper.checkToRunLccmScript();
                                    break;
                                }
                                break;
                            default:
                                Log.i("UcmService", "mOnNotiRemoveBroadcastReceiver ");
                                this.this$0.showEnforcedLockTypeNotificationIntenal(
                                        intent.getIntExtra("USER_ID", -1),
                                        intent.getStringExtra("CS_NAME"));
                                break;
                        }
                    }
                },
                new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        if (SemEmergencyManager.getInstance(applicationContext) != null
                && SemEmergencyManager.isEmergencyMode(applicationContext)) {
            Log.i("UcmService", "Already Emergency Status");
            this.mEmergencyEnabled = true;
        }
        synchronized (obj) {
            readPersistentAppletsInfoLocked();
        }
        if (equals) {
            Log.i("UcmService", "fbe is enabled");
            if (SystemProperties.get("persist.security.ucs", "none").equals("none")
                    && UcmServiceODE.getOdeStatus() == 0
                    && UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_wpc_dar") != 1) {
                deleteODEConfigInFileIfExist();
            }
            updateKeyguardConfig(UserHandle.getUserId(Binder.getCallingUid()));
        } else {
            Log.i("UcmService", "fbe is not enabled");
            if (SystemProperties.get("ro.crypto.state", "none").equals("unencrypted")
                    || SystemProperties.get("vold.decrypt", "none")
                            .equals("trigger_restart_framework")
                    || SystemProperties.get("vold.decrypt", "none").equals("trigger_reset_main")) {
                if (SystemProperties.get("persist.security.ucs", "none").equals("none")
                        && UcmServiceODE.getOdeStatus() == 0
                        && UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_wpc_dar") != 1) {
                    deleteODEConfigInFileIfExist();
                }
                updateKeyguardConfig(UserHandle.getUserId(Binder.getCallingUid()));
            }
        }
        try {
            if (this.mPersona == null) {
                this.mPersona = (SemPersonaManager) this.mContext.getSystemService("persona");
            }
            if (this.mPersona != null) {
                Log.i("UcmService", "registerPersonaObserver is called...");
                if (this.mPersona == null) {
                    this.mPersona = (SemPersonaManager) this.mContext.getSystemService("persona");
                }
                this.mPersona.registerSystemPersonaObserver(
                        new ISystemPersonaObserver
                                .Stub() { // from class:
                                          // com.samsung.ucm.ucmservice.CredentialManagerService.6
                            public final void onKnoxContainerLaunch(int i6) {}

                            public final void onPersonaActive(int i6) {}

                            public final void onRemovePersona(int i6) {}

                            public final void onResetPersona(int i6) {}

                            public final void onStateChange(
                                    int i6,
                                    SemPersonaState semPersonaState,
                                    SemPersonaState semPersonaState2) {
                                Log.i(
                                        "UcmService",
                                        "inside onstatechange "
                                                + i6
                                                + " new "
                                                + semPersonaState2
                                                + " old "
                                                + semPersonaState);
                                try {
                                    if (semPersonaState2.equals(SemPersonaState.DELETING)) {
                                        CredentialManagerService.m1241$$Nest$mupdateMDMPolicies(
                                                CredentialManagerService.this, i6);
                                    }
                                } catch (Exception e) {
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e,
                                                    new StringBuilder("The exception occurs "),
                                                    "UcmService");
                                }
                            }
                        });
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
        SystemProperties.set("security.ucm_version", "1.11");
    }

    public static void applyMetaData(int i, String str, Bundle bundle) {
        if (bundle.containsKey(str)) {
            return;
        }
        bundle.putInt(str, i);
    }

    public static boolean compareCallingPkg(byte[] bArr, byte[] bArr2) {
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
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("The exception occurs "), "UcmService");
                    return z;
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static String convertByteToString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (bArr == null) {
            return null;
        }
        for (byte b : bArr) {
            stringBuffer.append(String.format("%02X", Byte.valueOf(b)));
        }
        String stringBuffer2 = stringBuffer.toString();
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "convertByteToString result - ", stringBuffer2, "UcmService");
        return stringBuffer2;
    }

    public static void deleteODEConfigInFileIfExist() {
        Log.i("UcmService", "deleteODEConfigInFileIfExist");
        File file = new File("/efs/sec_efs", "odeConfig");
        if (!file.exists()) {
            Log.i("UcmService", "ODE config file does not exist");
        } else {
            Log.i("UcmService", "ODE config file exist so delete it");
            file.delete();
        }
    }

    public static void displayToastFromAgentResponse(final Context context, Bundle bundle) {
        final String string;
        if (!bundle.containsKey("toastmessageresponse")
                || (string = bundle.getString("toastmessageresponse", "")) == null
                || string.isEmpty()) {
            return;
        }
        try {
            Log.i("UcmService", "displayToastFromAgentResponse: ".concat(string));
            new Handler(Looper.getMainLooper())
                    .post(
                            new Runnable() { // from class:
                                             // com.samsung.ucm.ucmservice.CredentialManagerService.7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Toast.makeText(context, string, 1).show();
                                }
                            });
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("displayToastFromAgentResponse: Exception "),
                    "UcmService");
        }
    }

    public static CredentialStorage generateCS(String str, String str2) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = UniversalCredentialUtil.getSource(str);
        credentialStorage.packageName = str2;
        return credentialStorage;
    }

    public static Bundle getAgentInfoBundle(UcmAgentWrapper ucmAgentWrapper) {
        Bundle bundle = new Bundle();
        UcmAgentWrapper.AgentInfo agentInfo = ucmAgentWrapper.info;
        if (agentInfo != null) {
            String str = agentInfo.id;
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
            bundle.putBoolean(
                    "isSupportChangePinWithPassword", agentInfo.isSupportChangePinWithPassword);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0173 A[Catch: IOException -> 0x016f, TryCatch #6 {IOException -> 0x016f, blocks: (B:99:0x016b, B:90:0x0173, B:92:0x0178), top: B:98:0x016b }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0178 A[Catch: IOException -> 0x016f, TRY_LEAVE, TryCatch #6 {IOException -> 0x016f, blocks: (B:99:0x016b, B:90:0x0173, B:92:0x0178), top: B:98:0x016b }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x016b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v28, types: [java.io.InputStream, java.security.DigestInputStream] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getDigestOfBytes(byte[] r8) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.CredentialManagerService.getDigestOfBytes(byte[]):byte[]");
    }

    public static Bundle getErrorParameterBundle(int i) {
        return SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "errorresponse");
    }

    public static String getKeyguardStorageOwnerForCurrentUser(int i) {
        FileInputStream openRead;
        Log.i("UcmService", "getKeyguardStorageOwnerForCurrentUser : " + i);
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(Environment.getUserSystemDirectory(i), "ucm_keyguardconfig.xml"));
        String str = null;
        if (!atomicFile.getBaseFile().exists()) {
            Log.i("UcmService", "isFileExist : not exist");
            return null;
        }
        try {
            openRead = atomicFile.openRead();
        } catch (FileNotFoundException e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
        } catch (IOException e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
        } catch (XmlPullParserException e3) {
            Log.i("UcmService", "The exception occurs " + e3.getMessage());
        }
        try {
            XmlPullParser newPullParser = Xml.newPullParser();
            newPullParser.setInput(openRead, null);
            for (int eventType = newPullParser.getEventType();
                    eventType != 2 && eventType != 1;
                    eventType = newPullParser.next()) {}
            if ("keyguard".equals(newPullParser.getName())) {
                int next = newPullParser.next();
                String str2 = null;
                do {
                    if (next == 2) {
                        try {
                            if (newPullParser.getDepth() == 2
                                    && "vendor".equals(newPullParser.getName())) {
                                str2 = newPullParser.getAttributeValue(null, "owner");
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
            return str;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String getODEVendorName() {
        byte[] bArr = EFSProperties.loadODEConfig().csName;
        String str = "";
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        try {
            String str2 = new String(bArr, "UTF-8");
            try {
                Log.i("UcmService", "ODE Vendor Name : ".concat(str2));
                return str2;
            } catch (UnsupportedEncodingException e) {
                e = e;
                str = str2;
                Log.i("UcmService", "The exception occurs " + e.getMessage());
                return str;
            }
        } catch (UnsupportedEncodingException e2) {
            e = e2;
        }
    }

    public static ucmRetParcelable getResponseParcel(int i) {
        return new ucmRetParcelable(i, (byte[]) null);
    }

    public static ucmRetParcelable getResponseParcel(Bundle bundle) {
        return new ucmRetParcelable(
                bundle.getInt("errorresponse"), bundle.getByteArray("bytearrayresponse"));
    }

    public static String getStoragePkgname(String str) {
        String[] split;
        if (str == null || (split = str.split(":")) == null || split.length <= 0) {
            return null;
        }
        return split[0];
    }

    public static int getUid0FromUid(int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        int i3 = 0;
        for (int i4 = i2; i4 > 0; i4 /= 10) {
            i3++;
        }
        int i5 = 0;
        for (int i6 = i; i6 > 0; i6 /= 10) {
            i5++;
        }
        int i7 = 1;
        for (int i8 = 0; i8 < i5 - i3; i8++) {
            i7 *= 10;
        }
        return i % (i7 * i2);
    }

    public final Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "APDUCommand : ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService()
                    .enforceSecurityPermission(
                            new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle APDUCommand =
                iUcmAgentService != null ? iUcmAgentService.APDUCommand(bArr, bundle) : null;
        if (APDUCommand != null) {
            return APDUCommand;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle3 = new Bundle();
        bundle3.putInt("errorresponse", 13);
        return bundle3;
    }

    public final boolean applyMDMPolicies(int i, int i2, String str, int i3, boolean z) {
        long j;
        boolean z2;
        boolean z3;
        boolean z4;
        String storagePkgname = getStoragePkgname(str);
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "applyMDMPolicies adminUid-", ", userId-", ", pluginPkg-");
        m.append(storagePkgname);
        m.append(", apply-");
        m.append(z);
        Log.i("UcmService", m.toString());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(storagePkgname);
                j = clearCallingIdentity;
                if (i2 == 0 || i2 >= 10) {
                    try {
                        EnterpriseDeviceManager enterpriseDeviceManager =
                                new EnterpriseDeviceManager(
                                        this.mContext, new ContextInfo(i), (Handler) null);
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
                            Log.i(
                                    "UcmService",
                                    "applyMDMPolicies isContainer ["
                                            + z3
                                            + "] isBYODContainer ["
                                            + z2
                                            + "]");
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
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies admin pkg -" + str2);
                                            arrayList2.add(str2);
                                            i4++;
                                            length = i5;
                                        }
                                    }
                                    arrayList2.addAll(arrayList);
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies addPackagesToForceStopBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .addPackagesToForceStopBlackList(
                                                                    arrayList2));
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies addPackagesToClearCacheBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .addPackagesToClearCacheBlackList(
                                                                    arrayList2));
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies addPackagesToClearDataBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .addPackagesToClearDataBlackList(
                                                                    arrayList2));
                                    if (!z3 && packagesForUid != null) {
                                        for (String str3 : packagesForUid) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies setAdminRemovable status-"
                                                            + enterpriseDeviceManager
                                                                    .setAdminRemovable(
                                                                            false, str3));
                                            enterpriseDeviceManager
                                                    .getApplicationPolicy()
                                                    .setApplicationUninstallationDisabled(str3);
                                        }
                                    }
                                    Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        enterpriseDeviceManager
                                                .getApplicationPolicy()
                                                .setApplicationUninstallationDisabled(
                                                        (String) it.next());
                                    }
                                }
                                if (z3 && i3 != -1) {
                                    Log.i("UcmService", "applyMDMPolicies inside container logic");
                                    ArrayList arrayList3 = new ArrayList();
                                    EnterpriseDeviceManager enterpriseDeviceManager2 =
                                            new EnterpriseDeviceManager(
                                                    this.mContext,
                                                    new ContextInfo(i, i2),
                                                    (Handler) null);
                                    KnoxContainerManager knoxContainerManager =
                                            EnterpriseKnoxManager.getInstance()
                                                    .getKnoxContainerManager(
                                                            this.mContext, new ContextInfo(i, i2));
                                    if (knoxContainerManager != null) {
                                        String[] packagesForUid2 = this.mPm.getPackagesForUid(i3);
                                        if (packagesForUid2 != null) {
                                            for (String str4 : packagesForUid2) {
                                                Log.i(
                                                        "UcmService",
                                                        "applyMDMPolicies disable uninstall pkg -"
                                                                + str4);
                                                knoxContainerManager
                                                        .getApplicationPolicy()
                                                        .setApplicationUninstallationDisabled(str4);
                                                arrayList3.add(str4);
                                            }
                                        }
                                        if (arrayList3.size() > 0) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " addPackagesToForceStopBlackList status-"
                                                            + knoxContainerManager
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToForceStopBlackList(
                                                                            arrayList3));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " addPackagesToClearCacheBlackList"
                                                        + " status-"
                                                            + knoxContainerManager
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToClearCacheBlackList(
                                                                            arrayList3));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " addPackagesToClearDataBlackList status-"
                                                            + knoxContainerManager
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToClearDataBlackList(
                                                                            arrayList3));
                                        }
                                        if (arrayList.size() > 0) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " addPackagesToForceStopBlackList for"
                                                        + " plugin. status-"
                                                            + enterpriseDeviceManager2
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToForceStopBlackList(
                                                                            arrayList));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " addPackagesToClearCacheBlackList for"
                                                        + " plugin. status-"
                                                            + enterpriseDeviceManager2
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToClearCacheBlackList(
                                                                            arrayList));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " addPackagesToClearDataBlackList for"
                                                        + " plugin. status-"
                                                            + enterpriseDeviceManager2
                                                                    .getApplicationPolicy()
                                                                    .addPackagesToClearDataBlackList(
                                                                            arrayList));
                                            Iterator it2 = arrayList.iterator();
                                            while (it2.hasNext()) {
                                                String str5 = (String) it2.next();
                                                Log.i(
                                                        "UcmService",
                                                        "applyMDMPolicies disable uninstall pkg -"
                                                                + str5);
                                                enterpriseDeviceManager2
                                                        .getApplicationPolicy()
                                                        .setApplicationUninstallationDisabled(str5);
                                            }
                                        }
                                    }
                                }
                            } else {
                                boolean isPluginUsedInOtherUser =
                                        isPluginUsedInOtherUser(i2, i, str, false);
                                Log.i(
                                        "UcmService",
                                        "applyMDMPolicies ["
                                                + str
                                                + "] isPluginUsedInOtherUser ["
                                                + isPluginUsedInOtherUser
                                                + "]");
                                if (!z2) {
                                    ArrayList arrayList4 = new ArrayList();
                                    String[] packagesForUid3 = this.mPm.getPackagesForUid(i);
                                    if (!z3 && packagesForUid3 != null) {
                                        int i6 = 0;
                                        for (int length2 = packagesForUid3.length;
                                                i6 < length2;
                                                length2 = length2) {
                                            String str6 = packagesForUid3[i6];
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies admin pkg -" + str6);
                                            arrayList4.add(str6);
                                            i6++;
                                        }
                                    }
                                    if (!isPluginUsedInOtherUser) {
                                        arrayList4.addAll(arrayList);
                                    }
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies removePackagesFromForceStopBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .removePackagesFromForceStopBlackList(
                                                                    arrayList4));
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies removePackagesFromClearCacheBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .removePackagesFromClearCacheBlackList(
                                                                    arrayList4));
                                    Log.i(
                                            "UcmService",
                                            "applyMDMPolicies removePackagesFromClearDataBlackList"
                                                + " status-"
                                                    + enterpriseDeviceManager
                                                            .getApplicationPolicy()
                                                            .removePackagesFromClearDataBlackList(
                                                                    arrayList4));
                                    if (!z3 && packagesForUid3 != null) {
                                        for (String str7 : packagesForUid3) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies setAdminRemovable status-"
                                                            + enterpriseDeviceManager
                                                                    .setAdminRemovable(true, str7));
                                            enterpriseDeviceManager
                                                    .getApplicationPolicy()
                                                    .setApplicationUninstallationEnabled(str7);
                                        }
                                    }
                                    if (!isPluginUsedInOtherUser) {
                                        Iterator it3 = arrayList.iterator();
                                        while (it3.hasNext()) {
                                            enterpriseDeviceManager
                                                    .getApplicationPolicy()
                                                    .setApplicationUninstallationEnabled(
                                                            (String) it3.next());
                                        }
                                    }
                                }
                                if (z3 && i3 != -1) {
                                    Log.i("UcmService", "applyMDMPolicies inside container logic");
                                    ArrayList arrayList5 = new ArrayList();
                                    EnterpriseDeviceManager enterpriseDeviceManager3 =
                                            new EnterpriseDeviceManager(
                                                    this.mContext,
                                                    new ContextInfo(getUid0FromUid(i, i2)),
                                                    (Handler) null);
                                    KnoxContainerManager knoxContainerManager2 =
                                            EnterpriseKnoxManager.getInstance()
                                                    .getKnoxContainerManager(
                                                            this.mContext, new ContextInfo(i, i2));
                                    String[] packagesForUid4 = this.mPm.getPackagesForUid(i3);
                                    if (knoxContainerManager2 != null) {
                                        if (packagesForUid4 != null) {
                                            for (String str8 : packagesForUid4) {
                                                Log.i(
                                                        "UcmService",
                                                        "applyMDMPolicies enable uninstall pkg -"
                                                                + str8);
                                                knoxContainerManager2
                                                        .getApplicationPolicy()
                                                        .setApplicationUninstallationEnabled(str8);
                                                arrayList5.add(str8);
                                            }
                                        }
                                        if (arrayList5.size() > 0) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " removePackagesFromForceStopBlackList"
                                                        + " status-"
                                                            + knoxContainerManager2
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromForceStopBlackList(
                                                                            arrayList5));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " removePackagesFromClearCacheBlackList"
                                                        + " status-"
                                                            + knoxContainerManager2
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromClearCacheBlackList(
                                                                            arrayList5));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies inside container"
                                                        + " removePackagesFromClearDataBlackList"
                                                        + " status-"
                                                            + knoxContainerManager2
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromClearDataBlackList(
                                                                            arrayList5));
                                        }
                                        if (arrayList.size() > 0 && !isPluginUsedInOtherUser) {
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " removePackagesFromForceStopBlackList"
                                                        + " for plugin status-"
                                                            + enterpriseDeviceManager3
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromForceStopBlackList(
                                                                            arrayList));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " removePackagesFromClearCacheBlackList"
                                                        + " for plugin status-"
                                                            + enterpriseDeviceManager3
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromClearCacheBlackList(
                                                                            arrayList));
                                            Log.i(
                                                    "UcmService",
                                                    "applyMDMPolicies"
                                                        + " removePackagesFromClearDataBlackList"
                                                        + " for plugin status-"
                                                            + enterpriseDeviceManager3
                                                                    .getApplicationPolicy()
                                                                    .removePackagesFromClearDataBlackList(
                                                                            arrayList));
                                            Iterator it4 = arrayList.iterator();
                                            while (it4.hasNext()) {
                                                String str9 = (String) it4.next();
                                                Log.i(
                                                        "UcmService",
                                                        "applyMDMPolicies enable uninstall pkg -"
                                                                + str9);
                                                enterpriseDeviceManager3
                                                        .getApplicationPolicy()
                                                        .setApplicationUninstallationEnabled(str9);
                                            }
                                        }
                                    }
                                }
                            }
                            z4 = true;
                            Binder.restoreCallingIdentity(j);
                            return z4;
                        }
                    } catch (Exception e) {
                        e = e;
                        Log.i("UcmService", "The exception occurs " + e.getMessage());
                        Binder.restoreCallingIdentity(j);
                        return false;
                    }
                }
                z4 = false;
                Binder.restoreCallingIdentity(j);
                return z4;
            } catch (Exception e2) {
                e = e2;
                j = clearCallingIdentity;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            Throwable th22 = th;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th22;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Bundle changePin(String str, String str2, String str3) {
        Bundle changePin;
        int i;
        StringBuilder sb;
        Bundle keyguardPasswordUpdated;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "changePin : ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService()
                    .enforceSecurityPermission(
                            new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (activeAgent.info.isSupportChangePinWithPassword) {
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            if (UcmServiceUtil.isOrganizationOwnedProfile(this.mContext)) {
                userId = UcmServiceUtil.getOrganizationOwnedProfileUserId();
            }
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    userId, "User ID ", "=> changePinWithPassword", "UcmService");
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            changePin =
                    iUcmAgentService != null
                            ? iUcmAgentService.changePinWithPassword(str2, str3)
                            : null;
            if (changePin != null) {
                int i2 = changePin.getInt("errorresponse", -1);
                Log.d("UcmService", "getOldPasswordFromResponse");
                LockscreenCredential createSmartcardPassword =
                        LockscreenCredential.createSmartcardPassword(
                                changePin.getByteArray("bytearrayresponse2"));
                Log.d("UcmService", "getNewPasswordFromResponse");
                LockscreenCredential createSmartcardPassword2 =
                        LockscreenCredential.createSmartcardPassword(
                                changePin.getByteArray("bytearrayresponse"));
                if (i2 == 0
                        && !createSmartcardPassword2.isNone()
                        && !createSmartcardPassword.isNone()
                        && !createSmartcardPassword.equals(createSmartcardPassword2)) {
                    Log.d("UcmService", "LockPatternUtils to update Password");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    boolean z = false;
                    z = false;
                    try {
                        try {
                            boolean lockCredential =
                                    this.mLockPatternUtils.setLockCredential(
                                            createSmartcardPassword2,
                                            createSmartcardPassword,
                                            userId);
                            IUcmAgentService iUcmAgentService2 = activeAgent.mUcmAgentService;
                            keyguardPasswordUpdated =
                                    iUcmAgentService2 != null
                                            ? iUcmAgentService2.keyguardPasswordUpdated(
                                                    lockCredential)
                                            : 0;
                        } catch (Exception e) {
                            Log.e("UcmService", "The exception occurs " + e.getMessage());
                            IUcmAgentService iUcmAgentService3 = activeAgent.mUcmAgentService;
                            Bundle keyguardPasswordUpdated2 =
                                    iUcmAgentService3 != null
                                            ? iUcmAgentService3.keyguardPasswordUpdated(false)
                                            : 0;
                            if (keyguardPasswordUpdated2 == 0) {
                                Log.i("UcmService", "ERROR: Null Response received from agent");
                                Bundle bundle2 = new Bundle();
                                bundle2.putInt("errorresponse", 13);
                                return bundle2;
                            }
                            i = keyguardPasswordUpdated2.getInt("errorresponse", -1);
                            sb =
                                    new StringBuilder(
                                            "keyguardPasswordUpdated response from plugin: error"
                                                + " code = ");
                            z = keyguardPasswordUpdated2;
                        }
                        if (keyguardPasswordUpdated == 0) {
                            Log.i("UcmService", "ERROR: Null Response received from agent");
                            Bundle bundle3 = new Bundle();
                            bundle3.putInt("errorresponse", 13);
                            return bundle3;
                        }
                        i = keyguardPasswordUpdated.getInt("errorresponse", -1);
                        sb =
                                new StringBuilder(
                                        "keyguardPasswordUpdated response from plugin: error code ="
                                            + " ");
                        z = keyguardPasswordUpdated;
                        sb.append(i);
                        Log.i("UcmService", sb.toString());
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        IUcmAgentService iUcmAgentService4 = activeAgent.mUcmAgentService;
                        Bundle keyguardPasswordUpdated3 =
                                iUcmAgentService4 != null
                                        ? iUcmAgentService4.keyguardPasswordUpdated(z)
                                        : null;
                        if (keyguardPasswordUpdated3 == null) {
                            Log.i("UcmService", "ERROR: Null Response received from agent");
                            Bundle bundle4 = new Bundle();
                            bundle4.putInt("errorresponse", 13);
                            return bundle4;
                        }
                        Log.i(
                                "UcmService",
                                "keyguardPasswordUpdated response from plugin: error code = "
                                        + keyguardPasswordUpdated3.getInt("errorresponse", -1));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            }
        } else {
            Log.d("UcmService", "Change Pin only");
            IUcmAgentService iUcmAgentService5 = activeAgent.mUcmAgentService;
            changePin = iUcmAgentService5 != null ? iUcmAgentService5.changePin(str2, str3) : null;
        }
        if (changePin == null) {
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle5 = new Bundle();
            bundle5.putInt("errorresponse", 13);
            return bundle5;
        }
        if (changePin.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1) == 133) {
            Log.i("UcmService", "state is changed to blocked");
            sendStateChangeBroadcast(str);
        }
        displayToastFromAgentResponse(this.mContext, changePin);
        return changePin;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x012d A[Catch: UnsupportedEncodingException -> 0x0125, TryCatch #3 {UnsupportedEncodingException -> 0x0125, blocks: (B:38:0x010b, B:40:0x011f, B:43:0x0127, B:45:0x012d, B:48:0x0142, B:50:0x014c, B:52:0x0161), top: B:37:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0142 A[Catch: UnsupportedEncodingException -> 0x0125, TryCatch #3 {UnsupportedEncodingException -> 0x0125, blocks: (B:38:0x010b, B:40:0x011f, B:43:0x0127, B:45:0x012d, B:48:0x0142, B:50:0x014c, B:52:0x0161), top: B:37:0x010b }] */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean configureKeyguardSettings(int r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 593
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.CredentialManagerService.configureKeyguardSettings(int,"
                    + " java.lang.String):boolean");
    }

    public final int configureODESettings(String str, Bundle bundle, String str2) {
        String str3;
        char c;
        char c2;
        char c3;
        String str4;
        Locale locale;
        String str5;
        String str6 = "UcmService";
        Log.i("UcmService", "configureODESettings");
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int uid = ucmUri.getUid();
        String source = ucmUri.getSource();
        Log.i("UcmService", "configureODESettings uriuid-" + uid);
        char c4 = 0;
        char c5 = 2;
        if (UcmServiceODE.getOdeStatus() == 2) {
            Log.i("UcmService", "device is encrypted with UCS so cannot change configuration");
            return 201327104;
        }
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig.cofiguratorPkg != null) {
            Log.i("UcmService", "configureODESettings Validating current ODE setting configurator");
            byte[] configuratorPkg = getConfiguratorPkg(uid);
            byte[] configuratorSignature = getConfiguratorSignature(uid);
            if (configuratorPkg != null) {
                if (compareCallingPkg(loadODEConfig.cofiguratorPkg, configuratorPkg)
                        && Arrays.equals(loadODEConfig.cofiguratorSign, configuratorSignature)) {
                    Log.i(
                            "UcmService",
                            "configureODESettings valid caller is changing ODE configuration...");
                } else {
                    Log.i(
                            "UcmService",
                            "configureODESettings invalid caller is trying to change ODE"
                                + " configuration. Error...");
                }
            }
            return 201327360;
        }
        char c6 = 269;
        if ("reset".equals(source)) {
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
        int i = 14;
        if (((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).isEmpty()) {
            return 14;
        }
        String source2 = UniversalCredentialUtil.getSource(str);
        if (source2 != null && !source2.isEmpty()) {
            Iterator it =
                    ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
            while (it.hasNext()) {
                UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
                if (ucmAgentWrapper != null) {
                    UcmAgentWrapper.AgentInfo agentInfo = ucmAgentWrapper.info;
                    if (agentInfo != null) {
                        String str7 = agentInfo.id;
                        if (str7 == null) {
                            i = 14;
                        } else if (str7.equals(source2)) {
                            Log.i(str6, "Find UcmAgentWrapper");
                            UcmAgentWrapper.AgentInfo agentInfo2 = ucmAgentWrapper.info;
                            if (!agentInfo2.isODESupport) {
                                Log.i(str6, "This agent dose not support ODE");
                                return 3;
                            }
                            byte[] bArr = agentInfo2.AID;
                            if (bArr == null || bArr.length == 0) {
                                Log.i(str6, "AID is empty. save default AID");
                                bArr = new byte[10];
                                bArr[c4] = 49;
                                bArr[1] = 50;
                                bArr[c5] = 51;
                                bArr[3] = 52;
                                bArr[4] = 53;
                                bArr[5] = 97;
                                bArr[6] = 98;
                                bArr[7] = 99;
                                bArr[8] = 100;
                                bArr[9] = 101;
                            } else if (bArr.length < 5 || bArr.length > 16) {
                                Log.i(str6, "AID range is not proper");
                                return 4;
                            }
                            String str8 = ucmAgentWrapper.info.storageType;
                            AppletProperties appletInfo = getAppletInfo(source2);
                            if (appletInfo != null && (str5 = appletInfo.appletLocation) != null) {
                                str8 = str5;
                            }
                            int storageTypeIndex =
                                    EFSProperties.ODEProperties.getStorageTypeIndex(str8);
                            if (storageTypeIndex < 0) {
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "UCM does not support this storage type : ", str8, str6);
                                return 4;
                            }
                            String str9 = ucmAgentWrapper.info.enabledSCP;
                            int sCPTypeIndex = EFSProperties.ODEProperties.getSCPTypeIndex(str9);
                            if (!TextUtils.isEmpty(str9)) {
                                if (sCPTypeIndex < 0) {
                                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                            "UCM does not support this SCP type : ", str9, str6);
                                    return 4;
                                }
                                str9.equalsIgnoreCase("NONE");
                            }
                            if (this.mIsFbeEnabled) {
                                try {
                                    int saveTempOdeKey = saveTempOdeKey(str);
                                    if (saveTempOdeKey != 0) {
                                        Log.i(
                                                str6,
                                                "failed saveTempOdeKey. [" + saveTempOdeKey + "]");
                                        return saveTempOdeKey;
                                    }
                                } catch (Exception e) {
                                    Log.i(str6, "Exception" + e.getMessage());
                                    Log.i(str6, "failed to store ODE key");
                                    return 269;
                                }
                            }
                            SystemProperties.set("persist.security.ucs", "1");
                            SystemProperties.set("persist.security.ucs.csname", str);
                            UcmAgentWrapper.AgentInfo agentInfo3 = ucmAgentWrapper.info;
                            int i2 = agentInfo3.enabledWrap;
                            int i3 = agentInfo3.pinMinLength;
                            int i4 = agentInfo3.pinMaxLength;
                            int i5 = agentInfo3.authMode;
                            int i6 = agentInfo3.authMaxCnt;
                            int i7 = agentInfo3.pukMinLength;
                            int i8 = agentInfo3.pukMaxLength;
                            try {
                                byte[] bytes = agentInfo3.agentId.getBytes("UTF-8");
                                if (bytes == null || bytes.length == 0) {
                                    Log.i(str6, "csName is empty");
                                    return 4;
                                }
                                EFSProperties.ODEProperties loadODEConfig2 =
                                        EFSProperties.loadODEConfig();
                                String str10 = str6;
                                loadODEConfig2.enabledUCSInODE = 1;
                                loadODEConfig2.AID = bArr;
                                loadODEConfig2.storageType = storageTypeIndex;
                                loadODEConfig2.enabledSCP = sCPTypeIndex;
                                loadODEConfig2.enabledWrap = i2;
                                loadODEConfig2.pinMinLength = i3;
                                loadODEConfig2.pinMaxLength = i4;
                                loadODEConfig2.authMode = i5;
                                loadODEConfig2.authMaxCnt = i6;
                                loadODEConfig2.pukMinLength = i7;
                                loadODEConfig2.pukMaxLength = i8;
                                loadODEConfig2.csName = bytes;
                                loadODEConfig2.cofiguratorPkg = getConfiguratorPkg(uid);
                                loadODEConfig2.cofiguratorSign = getConfiguratorSignature(uid);
                                loadODEConfig2.pluginSignatureHash =
                                        getDigestOfBytes(str2.getBytes());
                                loadODEConfig2.version = 2;
                                try {
                                    LocaleList locales =
                                            this.mContext
                                                    .getResources()
                                                    .getConfiguration()
                                                    .getLocales();
                                    if (locales != null
                                            && locales.size() > 0
                                            && (locale = locales.get(0)) != null) {
                                        String language = locale.getLanguage();
                                        if (!TextUtils.isEmpty(language)) {
                                            loadODEConfig2.defaultLanguage = language.getBytes();
                                        }
                                    }
                                    str4 = str10;
                                } catch (Exception e2) {
                                    str4 = str10;
                                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                            .m(
                                                    e2,
                                                    new StringBuilder("The exception occurs "),
                                                    str4);
                                }
                                if (true != EFSProperties.saveODEConfig(loadODEConfig2)) {
                                    Log.i(str4, "configureODESettings. failed to save ode config");
                                    return 269;
                                }
                                UcmServiceUtil.saveDataToFile$1(
                                        "/efs/sec_efs/ucm_ode_mode", "1".getBytes());
                                return 0;
                            } catch (UnsupportedEncodingException e3) {
                                Log.i(str6, "The exception occurs " + e3.getMessage());
                                return 4;
                            }
                        }
                    }
                    char c7 = c4;
                    str3 = str6;
                    c = c5;
                    c2 = c6;
                    c3 = c7;
                    i = 14;
                } else {
                    char c8 = c4;
                    str3 = str6;
                    c = c5;
                    c2 = c6;
                    c3 = c8;
                }
                char c9 = c2;
                c5 = c;
                str6 = str3;
                c4 = c3;
                c6 = c9;
            }
        }
        return i;
    }

    public final int configureWPCDARFlag(String str, String str2) {
        String source;
        UcmAgentWrapper.AgentInfo agentInfo;
        String str3;
        String str4;
        Log.i("UcmService", "configureWPCDARFlag");
        this.mSecurityHelper.checkCallerPermissionFor("configureWPCDARFlag");
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int uid = ucmUri.getUid();
        String source2 = ucmUri.getSource();
        Log.i("UcmService", "configureWPCDARFlag uriuid-" + uid);
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig.cofiguratorPkg != null) {
            Log.i(
                    "UcmService",
                    "configureWPCDARFlag Validating current WPC DAR setting configurator");
            byte[] configuratorPkg = getConfiguratorPkg(uid);
            byte[] configuratorSignature = getConfiguratorSignature(uid);
            if (configuratorPkg != null) {
                if (compareCallingPkg(loadODEConfig.cofiguratorPkg, configuratorPkg)
                        && Arrays.equals(loadODEConfig.cofiguratorSign, configuratorSignature)) {
                    Log.i(
                            "UcmService",
                            "configureWPCDARFlag valid caller is changing ODE configuration...");
                } else {
                    Log.i(
                            "UcmService",
                            "configureWPCDARFlag invalid caller is trying to change ODE"
                                + " configuration. Error...");
                }
            }
            return 201327360;
        }
        if ("reset".equals(source2)) {
            Log.i("UcmService", "disable configureWPCDARFlag in UCS");
            SystemProperties.set("persist.security.ucs", "");
            SystemProperties.set("persist.security.ucs.csname", "");
            return EFSProperties.deleteODEConfig() ? 0 : 269;
        }
        if (str2 == null || str2.isEmpty()) {
            Log.e("UcmService", "plugin signature is null");
            return 201327104;
        }
        if (str.isEmpty()) {
            Log.e("UcmService", "uri is empty");
            return 16;
        }
        if (!((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).isEmpty()
                && (source = UniversalCredentialUtil.getSource(str)) != null
                && !source.isEmpty()) {
            Iterator it =
                    ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
            while (it.hasNext()) {
                UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
                if (ucmAgentWrapper != null
                        && (agentInfo = ucmAgentWrapper.info) != null
                        && (str3 = agentInfo.id) != null
                        && str3.equals(source)) {
                    Log.i("UcmService", "Find UcmAgentWrapper");
                    UcmAgentWrapper.AgentInfo agentInfo2 = ucmAgentWrapper.info;
                    if (!agentInfo2.isODESupport) {
                        Log.e("UcmService", "This agent dose not support ODE");
                        return 3;
                    }
                    byte[] bArr = agentInfo2.AID;
                    if (bArr == null || bArr.length == 0) {
                        Log.i("UcmService", "AID is empty. save default AID");
                        bArr = new byte[] {49, 50, 51, 52, 53, 97, 98, 99, 100, 101};
                    } else if (bArr.length < 5 || bArr.length > 16) {
                        Log.e("UcmService", "AID range is not proper");
                        return 4;
                    }
                    String str5 = ucmAgentWrapper.info.storageType;
                    AppletProperties appletInfo = getAppletInfo(source);
                    if (appletInfo != null && (str4 = appletInfo.appletLocation) != null) {
                        str5 = str4;
                    }
                    int storageTypeIndex = EFSProperties.ODEProperties.getStorageTypeIndex(str5);
                    if (storageTypeIndex < 0) {
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                "UCM does not support this storage type : ", str5, "UcmService");
                        return 4;
                    }
                    String str6 = ucmAgentWrapper.info.enabledSCP;
                    int sCPTypeIndex = EFSProperties.ODEProperties.getSCPTypeIndex(str6);
                    if (!TextUtils.isEmpty(str6)) {
                        if (sCPTypeIndex < 0) {
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    "UCM does not support this SCP type : ", str6, "UcmService");
                            return 4;
                        }
                        str6.equalsIgnoreCase("NONE");
                    }
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
                            Log.e("UcmService", "csName is empty");
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
                        if (!EFSProperties.saveODEConfig(loadODEConfig2)) {
                            Log.e("UcmService", "configureWPCDARFlag. failed to save ode config");
                            return 269;
                        }
                        UcmServiceUtil.saveDataToFile$1("/efs/sec_efs/ucm_wpc_dar", "1".getBytes());
                        SystemProperties.set("persist.sys.knox.UCM_WPC", "true");
                        return 0;
                    } catch (UnsupportedEncodingException e) {
                        Log.e("UcmService", "The exception occurs " + e.getMessage());
                        return 4;
                    }
                }
            }
        }
        return 14;
    }

    public final Bundle containsAlias(String str, int i) {
        this.mSecurityHelper.getClass();
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
        int i2 = uid == -1 ? callingUid : uid;
        int userId = UserHandle.getUserId(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            i2,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
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
            String rawAlias = ucmUri.getRawAlias();
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle containsAlias =
                    iUcmAgentService != null
                            ? iUcmAgentService.containsAlias(rawAlias, i, callingUid)
                            : null;
            if (containsAlias != null) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        containsAlias.getInt("errorresponse"),
                        "containsAlias Response from plugin:  error code = ",
                        "UcmService");
                return containsAlias;
            }
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ucmRetParcelable decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("decrypt ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i = resourceId;
        Bundle bundle3 = bundle == null ? new Bundle() : bundle;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle bundle4 = bundle3;
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", "WARNING!!!! decrypt is NOT allowed for URI = " + str);
                bundle2.putInt("errorresponse", 15);
                return getResponseParcel(bundle2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle4.putInt("callerUid", callingUid);
            bundle4.putInt("user_id", userId);
            bundle4.putInt("ownerUid", callingUid);
            bundle4.putInt("resource", i);
            Log.i("UcmService", "decrypt KEY_RESOURCE_ID= " + bundle4.getInt("resource", -2));
            Log.i("UcmService", "decrypt KEY_USER_ID= " + bundle4.getInt("user_id", -2));
            Log.i("UcmService", "decrypt KEY_CALLER_UID= " + bundle4.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            String rawAlias = ucmUri.getRawAlias();
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle decrypt =
                    iUcmAgentService != null
                            ? iUcmAgentService.decrypt(rawAlias, bArr, str2, bundle4)
                            : null;
            if (decrypt == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle5 = new Bundle();
                bundle5.putInt("errorresponse", 13);
                return getResponseParcel(bundle5);
            }
            byte[] byteArray = decrypt.getByteArray("bytearrayresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    decrypt.getInt("errorresponse"),
                    "decrypt Response from plugin:  error code = ",
                    "UcmService");
            if (byteArray != null) {
                return getResponseParcel(decrypt);
            }
            Log.i("UcmService", "ERROR: Empty data received for decrypt");
            decrypt.putInt("errorresponse", 13);
            return getResponseParcel(decrypt);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle delete(String str) {
        this.mSecurityHelper.getClass();
        Bundle deleteInternal = deleteInternal(0, str, false);
        if (deleteInternal != null) {
            Log.i(
                    "UcmService",
                    "UCMERRORTESTING: @CredentialManagerService delete Response from plugin with"
                        + " error code = "
                            + deleteInternal.getInt("errorresponse"));
        } else {
            Log.i(
                    "UcmService",
                    "UCMERRORTESTING: @CredentialManagerService delete Response from plugin is"
                        + " null");
        }
        return deleteInternal;
    }

    public final Bundle deleteCertificate(String str, int i) {
        this.mSecurityHelper.checkCallerPermissionFor("deleteCertificate");
        Bundle deleteInternal = deleteInternal(i, str, true);
        if (deleteInternal == null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 13);
            return bundle;
        }
        int i2 = deleteInternal.getInt("errorresponse");
        Log.i("UcmService", "deleteCertificate Response:  error code = " + i2);
        Log.i(
                "UcmService",
                "UCMERRORTESTING: @CredentialManagerService deleteCertificate Response from plugin"
                    + " with error code = "
                        + i2);
        return deleteInternal;
    }

    public final Bundle deleteInternal(int i, String str, boolean z) {
        Log.i("UcmService", "deleteInternal " + str);
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "deleteInternal : NULL agent for uri ", str, "UcmService");
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = z ? i : UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i2 = resourceId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i3 = userId;
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            z,
                            ucmUri.getRawAlias())
                    == 0) {
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
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", i3);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", i2);
            Log.i("UcmService", "delete KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "delete KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "delete KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            if (iUcmAgentService != null) {
                return iUcmAgentService.delete(rawAlias, bundle2);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void disableAutoFactoryReset() {
        try {
            Log.i("UcmService", "disableAutoFactoryReset");
            int i =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), "auto_swipe_main_user", 0);
            boolean z = DBG;
            if (z) {
                Log.i("UcmService", "AUTO_SWIPE_MAIN_USER current : " + i);
            }
            boolean putInt =
                    Settings.Secure.putInt(
                            this.mContext.getContentResolver(), "auto_swipe_main_user", 0);
            if (z) {
                Log.i("UcmService", "Settings.Secure.putInt : " + putInt);
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
    }

    public final ucmRetParcelable encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("encrypt ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i = resourceId;
        Bundle bundle3 = bundle == null ? new Bundle() : bundle;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle bundle4 = bundle3;
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", "WARNING!!!! decrypt is NOT allowed for URI = " + str);
                bundle2.putInt("errorresponse", 15);
                return getResponseParcel(bundle2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle4.putInt("callerUid", callingUid);
            bundle4.putInt("user_id", userId);
            bundle4.putInt("ownerUid", callingUid);
            bundle4.putInt("resource", i);
            Log.i("UcmService", "encrypt KEY_RESOURCE_ID= " + bundle4.getInt("resource", -2));
            Log.i("UcmService", "encrypt KEY_USER_ID= " + bundle4.getInt("user_id", -2));
            Log.i("UcmService", "encrypt KEY_CALLER_UID= " + bundle4.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            String rawAlias = ucmUri.getRawAlias();
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle encrypt =
                    iUcmAgentService != null
                            ? iUcmAgentService.encrypt(rawAlias, bArr, str2, bundle4)
                            : null;
            if (encrypt == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle5 = new Bundle();
                bundle5.putInt("errorresponse", 13);
                return getResponseParcel(bundle5);
            }
            byte[] byteArray = encrypt.getByteArray("bytearrayresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    encrypt.getInt("errorresponse"),
                    "encrypt Response from plugin:  error code = ",
                    "UcmService");
            if (byteArray != null) {
                return getResponseParcel(encrypt);
            }
            Log.i("UcmService", "ERROR: Empty data received for encrypt");
            encrypt.putInt("errorresponse", 13);
            return getResponseParcel(encrypt);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ucmRetParcelable generateDek(String str) {
        Log.i("UcmService", "generateDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("generateDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            UcmAgentWrapper activeAgent =
                    true == "boot_test".equals(str)
                            ? getActiveAgent(
                                    "com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                            : getActiveAgent(UniversalCredentialUtil.getSource(str));
            if (activeAgent == null) {
                Log.i(
                        "UcmService",
                        "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle generateDek = iUcmAgentService != null ? iUcmAgentService.generateDek() : null;
            if (generateDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i(
                    "UcmService",
                    "generateDek response from plugin:  error code = "
                            + generateDek.getInt("errorresponse"));
            return getResponseParcel(generateDek);
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public final Bundle generateKey(String str, String str2, int i, Bundle bundle) {
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "generateKey(", str, ", ", str2, ", ");
        m.append(i);
        m.append(")");
        Log.i("UcmService", m.toString());
        this.mSecurityHelper.getClass();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "generateKey: NULL agent for uri ", str, "UcmService");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        bundle.putInt("callerUid", callingUid);
        bundle.putInt("user_id", userId);
        bundle.putInt("ownerUid", callingUid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            true,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", "WARNING!!! importKey is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "generateKey: KEY_RESOURCE_ID = " + bundle.getInt("resource", -2));
            Log.i("UcmService", "generateKey: KEY_USER_ID     = " + bundle.getInt("user_id", -2));
            Log.i("UcmService", "generateKey: KEY_CALLER_UID  = " + bundle.getInt("callerUid", -2));
            Log.i(
                    "UcmService",
                    "generateKey: KEY_ALGORITHM   = " + bundle.getString("algorithm", "AES"));
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle generateKey =
                    iUcmAgentService != null
                            ? iUcmAgentService.generateKey(rawAlias, str2, i, bundle)
                            : null;
            if (generateKey != null) {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService generateKey. Response from"
                            + " plugin with error code = "
                                + generateKey.getInt("errorresponse"));
            } else {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService generateKey. Response from"
                            + " plugin is NULL");
            }
            return generateKey;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "generateKeyPair ", str, "UcmService");
        Bundle generateKeyPairMain = generateKeyPairMain(str, str2, i, bundle, false);
        if (generateKeyPairMain != null) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    generateKeyPairMain.getInt("errorresponse"),
                    "generateKeyPair Response:  error code = ",
                    "UcmService");
            return generateKeyPairMain;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putByteArray("bytearrayresponse", null);
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public final Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "generateKeyPairInternal ", str, "UcmService");
        this.mSecurityHelper.checkCallerPermissionFor("generateKeyPairInternal");
        Bundle generateKeyPairMain = generateKeyPairMain(str, str2, i, bundle, true);
        if (generateKeyPairMain != null) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    generateKeyPairMain.getInt("errorresponse"),
                    "generateKeyPairInternal Response:  error code = ",
                    "UcmService");
            return generateKeyPairMain;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putByteArray("bytearrayresponse", null);
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public final Bundle generateKeyPairMain(
            String str, String str2, int i, Bundle bundle, boolean z) {
        int i2;
        int i3;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "generateKeyPairMain ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "generateKeyPairMain : NULL agent for uri ", str, "UcmService");
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i4 = resourceId;
        if (z) {
            i2 = i4;
            i3 = userId;
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PolicyManager policyManager = this.mPolicyManager;
                UcmAgentWrapper activeAgent2 = getActiveAgent(ucmUri.getSource());
                String rawAlias = ucmUri.getRawAlias();
                i2 = i4;
                i3 = userId;
                if (policyManager.isSEStorageAccessAllowed(
                                activeAgent2, userId, callingUid, false, rawAlias)
                        == 0) {
                    Log.i(
                            "UcmService",
                            "WARNING!!!! generateKeyPairMain is NOT allowed for URI = " + str);
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
        bundle3.putInt("user_id", i3);
        bundle3.putInt("ownerUid", callingUid);
        bundle3.putInt("resource", i2);
        bundle3.putBundle("extraArgs", bundle);
        Log.i(
                "UcmService",
                "generateKeyPairMain KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
        Log.i("UcmService", "generateKeyPairMain KEY_USER_ID= " + bundle3.getInt("user_id", -2));
        Log.i(
                "UcmService",
                "generateKeyPairMain KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putByteArray("bytearrayresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        String rawAlias2 = UniversalCredentialUtil.getRawAlias(str);
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.generateKeyPair(rawAlias2, str2, i, bundle3);
        }
        return null;
    }

    public final Bundle generateKeyguardPassword(int i, String str, Bundle bundle) {
        Log.i("UcmService", "generateKeyguardPassword " + str + ", userId-" + i);
        if (this.mSecurityHelper.isCallerSystemUI() || this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.getClass();
        } else {
            this.mSecurityHelper.checkCallerPermissionFor("generateKeyguardPassword");
        }
        Bundle bundle2 = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle2.putInt("errorresponse", 16);
            return bundle2;
        }
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle generateKeyguardPassword =
                iUcmAgentService != null
                        ? iUcmAgentService.generateKeyguardPassword(i, bundle)
                        : null;
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
                    generateKeyguardPassword.putByteArray(
                            "bytearrayresponse", string.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.i("UcmService", "The exception occurs " + e.getMessage());
                }
            }
        }
        DirEncryptService$$ExternalSyntheticOutline0.m(
                generateKeyguardPassword.getInt("errorresponse"),
                "generateKeyguardPassword Response from plugin:  error code = ",
                "UcmService");
        return generateKeyguardPassword;
    }

    public final Bundle generateSecureRandom(String str, int i, byte[] bArr) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "generateSecureRandom ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "generateSecureRandom : NULL agent for uri ", str, "UcmService");
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
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! generateSecureRandom is NOT allowed for URI = " + str);
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", userId);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", resourceId);
            Log.i(
                    "UcmService",
                    "generateSecureRandom KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i(
                    "UcmService",
                    "generateSecureRandom KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i(
                    "UcmService",
                    "generateSecureRandom KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putByteArray("bytearrayresponse", null);
                bundle.putInt("errorresponse", 14);
                return bundle;
            }
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle generateSecureRandom =
                    iUcmAgentService != null
                            ? iUcmAgentService.generateSecureRandom(i, bArr, bundle2)
                            : null;
            if (generateSecureRandom == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putByteArray("bytearrayresponse", null);
                bundle3.putInt("errorresponse", 14);
                return bundle3;
            }
            byte[] byteArray = generateSecureRandom.getByteArray("bytearrayresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    generateSecureRandom.getInt("errorresponse"),
                    "generateSecureRandom Response from plugin:  error code = ",
                    "UcmService");
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

    public final ucmRetParcelable generateWrappedDek(String str) {
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
                ucmAgentWrapper =
                        true == "wpc_test".equals(str)
                                ? getODEAgent()
                                : getActiveAgent(UniversalCredentialUtil.getSource(str));
            }
            if (ucmAgentWrapper == null) {
                Log.i(
                        "UcmService",
                        "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            IUcmAgentService iUcmAgentService = ucmAgentWrapper.mUcmAgentService;
            Bundle generateWrappedDek =
                    iUcmAgentService != null ? iUcmAgentService.generateWrappedDek() : null;
            if (generateWrappedDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i(
                    "UcmService",
                    "generateWrappedDek response from plugin:  error code = "
                            + generateWrappedDek.getInt("errorresponse"));
            return getResponseParcel(generateWrappedDek);
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public final UcmAgentWrapper getActiveAgent(String str) {
        this.mSecurityHelper.getClass();
        if (str == null) {
            return null;
        }
        Log.i("UcmService", "finding active agent ".concat(str));
        Iterator it = ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (str.equals(ucmAgentWrapper.info.id)) {
                Log.i("UcmService", "found active agent " + ucmAgentWrapper.componentName);
                return ucmAgentWrapper;
            }
        }
        return null;
    }

    public final UcmAgentWrapper getActiveAgentFromUri(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return getActiveAgent(UniversalCredentialUtil.getSource(str));
    }

    public final Bundle getAdminConfigureBundleFromCs(int i, int i2, String str) {
        this.mSecurityHelper.checkCallerPermissionFor("getAdminConfigureBundleFromCs");
        Bundle bundle = new Bundle();
        Log.i("UcmService", "getAdminConfigureBundleFromCs " + str);
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putBundle("bundleresponse", null);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()), i2, ucmUri.getUid(), true, null)
                    == 0) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! getAdminConfigureBundleFromCs is NOT allowed for URI = "
                                + str);
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
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle credentialStoragePluginConfiguration =
                    iUcmAgentService != null
                            ? iUcmAgentService.getCredentialStoragePluginConfiguration(i)
                            : null;
            if (credentialStoragePluginConfiguration != null) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        credentialStoragePluginConfiguration.getInt("errorresponse"),
                        "getCredentialStoragePluginConfiguration Response from plugin: error code ="
                            + " ",
                        "UcmService");
                return credentialStoragePluginConfiguration;
            }
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putBundle("bundleresponse", null);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle getAgentInfo(String str) {
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            return null;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        int i = (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid;
        int userId = UserHandle.getUserId(i);
        if (UcmServiceUtil.isOrganizationOwnedProfile(this.mContext)) {
            userId = UcmServiceUtil.getOrganizationOwnedProfileUserId();
        }
        int i2 = userId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()), i2, i, false, null)
                    != 0) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return getAgentInfoBundle(activeAgent);
            }
            Log.i("UcmService", "WARNING!!!! getAgentInfo is NOT allowed for URI = " + str);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final AppletProperties getAppletInfo(String str) {
        AppletProperties appletProperties;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getAppletInfo is called for pluginName-", str, "UcmService");
        if (!this.mPersistentAppletInfo.containsKey(str)
                || (appletProperties = (AppletProperties) this.mPersistentAppletInfo.get(str))
                        == null) {
            return null;
        }
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                new StringBuilder("getAppletInfo pluginName-"),
                appletProperties.pluginName,
                "UcmService");
        return appletProperties;
    }

    public final ucmRetParcelable getCertificateChain(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getCertificateChain ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        if (callingUid == 1016 && UcmServiceUtil.isOrganizationOwnedProfile(this.mContext)) {
            userId = UcmServiceUtil.getOrganizationOwnedProfileUserId();
        }
        int i = userId;
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i2 = resourceId;
        Bundle bundle2 = new Bundle();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            i,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! getCertificateChain is NOT allowed for URI = " + str);
                bundle.putInt("errorresponse", 15);
                return getResponseParcel(bundle);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            bundle2.putInt("callerUid", callingUid);
            bundle2.putInt("user_id", i);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", i2);
            Log.i(
                    "UcmService",
                    "getCertificateChain KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i(
                    "UcmService",
                    "getCertificateChain KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i(
                    "UcmService",
                    "getCertificateChain KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            String rawAlias = ucmUri.getRawAlias();
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle certificateChain =
                    iUcmAgentService != null
                            ? iUcmAgentService.getCertificateChain(rawAlias, bundle2)
                            : null;
            if (certificateChain == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putInt("errorresponse", 13);
                return getResponseParcel(bundle3);
            }
            byte[] byteArray = certificateChain.getByteArray("bytearrayresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    certificateChain.getInt("errorresponse"),
                    "getCertificateChain Response from plugin:  error code = ",
                    "UcmService");
            if (byteArray != null) {
                return getResponseParcel(certificateChain);
            }
            Log.i("UcmService", "ERROR: Empty data received for getCertificateChain");
            certificateChain.putInt("errorresponse", 13);
            return getResponseParcel(certificateChain);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final byte[] getConfiguratorPkg(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "getConfiguratorPkg is called for adminId-", "UcmService");
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
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
            PackageManagerAdapter packageManagerAdapter =
                    PackageManagerAdapter.getInstance(this.mContext);
            if (packagesForUid != null) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= packagesForUid.length) {
                        break;
                    }
                    Log.i("UcmService", "packageName -" + packagesForUid[i3]);
                    String str = packagesForUid[i3];
                    int userId = UserHandle.getUserId(callingUid);
                    packageManagerAdapter.getClass();
                    PackageInfo packageInfo = PackageManagerAdapter.getPackageInfo(64, userId, str);
                    if (packageInfo == null
                            || (signatureArr = packageInfo.signatures) == null
                            || signatureArr.length <= 0) {
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
        return bArr;
    }

    public final Bundle getCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putBundle("bundleresponse", null);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (activeAgent.isServiceBound()) {
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle credentialStorageProperty =
                    iUcmAgentService != null
                            ? iUcmAgentService.getCredentialStorageProperty(i, i2, bundle)
                            : null;
            Log.i("UcmService", "getCredentialStorageProperty Response from plugin");
            return credentialStorageProperty;
        }
        Log.i("UcmService", "agent is not bound");
        bundle2.putBundle("bundleresponse", null);
        bundle2.putInt("errorresponse", 14);
        return bundle2;
    }

    public final ucmRetParcelable getDek(String str) {
        Log.i("UcmService", "getDek " + str);
        Bundle bundle = new Bundle();
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDek");
            if (str == null || true == "".equals(str)) {
                Log.i("UcmService", "uri is empty");
                bundle.putInt("errorresponse", 16);
                return getResponseParcel(bundle);
            }
            Log.i("UcmService", "Checking uri : ".concat(str));
            UcmAgentWrapper activeAgent =
                    true == "boot_test".equals(str)
                            ? getActiveAgent(
                                    "com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                            : getActiveAgent(UniversalCredentialUtil.getSource(str));
            if (activeAgent == null) {
                Log.i(
                        "UcmService",
                        "no agent found for Source ="
                            + " com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 14);
                return getResponseParcel(bundle2);
            }
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle dek = iUcmAgentService != null ? iUcmAgentService.getDek() : null;
            if (dek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putInt("errorresponse", 13);
                return getResponseParcel(bundle3);
            }
            Log.i(
                    "UcmService",
                    "getDek Response from plugin:  error code = " + dek.getInt("errorresponse"));
            return getResponseParcel(dek);
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public final ucmRetParcelable getDekForVold(String str, byte[] bArr) {
        byte[] bArr2;
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                "getDekForVold [", str, "]", "UcmService");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDekForVold");
            UcmErcomSpecific odeVendorSpecific = getOdeVendorSpecific();
            odeVendorSpecific.getClass();
            Log.i("UcmService_ercom", "getDekForVold " + str);
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            UcmAgentWrapper oDEAgent = credentialManagerService.getODEAgent();
            odeVendorSpecific.mAgent = oDEAgent;
            ucmRetParcelable unwrapDek = credentialManagerService.unwrapDek(str, bArr, oDEAgent);
            if (unwrapDek.mResult == 0 && (bArr2 = unwrapDek.mData) != null && bArr2.length != 0) {
                return odeVendorSpecific.getDekForVoldInternalKey(str, bArr2);
            }
            Log.i("UcmService_ercom", "getDekForVold. unwrapDek failed");
            return unwrapDek;
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public final ucmRetParcelable getDekForVoldInternalKey(String str, byte[] bArr) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                "getDekForVoldInternalKey [", str, "]", "UcmService");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getDekForVoldInternalKey");
            if (bArr != null && bArr.length != 0) {
                return getOdeVendorSpecific().getDekForVoldInternalKey(str, bArr);
            }
            Log.i("UcmService", "getDekForVoldInternalKey. key is empty");
            return getResponseParcel(16);
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public final String getDetailErrorMessage(String str, int i) {
        Log.i("UcmService", "getDetailErrorMessage uri : " + str + ", errorCode : " + i);
        this.mSecurityHelper.getClass();
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
        try {
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            if (iUcmAgentService != null) {
                return iUcmAgentService.getDetailErrorMessage(i);
            }
            return null;
        } catch (AbstractMethodError unused) {
            Log.d("UcmAgentWrapper", "this plugin does not support getDetailErrorMessage API");
            return null;
        }
    }

    public final Bundle getInfo(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("getInfo : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (getUCMMDMService() != null) {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = UniversalCredentialUtil.getSource(str);
            credentialStorage.packageName = activeAgent.info.packageName;
            getUCMMDMService()
                    .enforceSecurityPermission(
                            new ContextInfo(Binder.getCallingUid()), credentialStorage);
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle info = iUcmAgentService != null ? iUcmAgentService.getInfo() : null;
        if (info != null) {
            return info;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle2 = new Bundle();
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public final Bundle getKeyType(String str) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m("getKeyType(", str, ")", "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "getKeyType: NULL agent for uri ", str, "UcmService");
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putBoolean("booleanresponse", false);
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("callerUid", callingUid);
        bundle2.putInt("user_id", userId);
        bundle2.putInt("ownerUid", callingUid);
        bundle2.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            true,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", "WARNING!!! getKeyType is NOT allowed for URI = " + str);
                bundle.putBoolean("booleanresponse", false);
                bundle.putInt("errorresponse", 15);
                return bundle;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "getKeyType: KEY_RESOURCE_ID = " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "getKeyType: KEY_USER_ID     = " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "getKeyType: KEY_CALLER_UID  = " + bundle2.getInt("callerUid", -2));
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle keyType =
                    iUcmAgentService != null
                            ? iUcmAgentService.getKeyType(rawAlias, bundle2)
                            : null;
            if (keyType != null) {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService getKeyType. Response from"
                            + " plugin with error code = "
                                + keyType.getInt("errorresponse"));
            } else {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService getKeyType. Response from"
                            + " plugin is NULL");
            }
            return keyType;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle getKeyguardPinCurrentRetryCount(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getKeyguardPinCurrentRetryCount : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinCurrentRetryCount =
                iUcmAgentService != null
                        ? iUcmAgentService.getKeyguardPinCurrentRetryCount()
                        : null;
        if (keyguardPinCurrentRetryCount != null) {
            return keyguardPinCurrentRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle getKeyguardPinMaximumLength(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getKeyguardPinMaximumLength : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMaximumLength =
                iUcmAgentService != null ? iUcmAgentService.getKeyguardPinMaximumLength() : null;
        if (keyguardPinMaximumLength != null) {
            return keyguardPinMaximumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle getKeyguardPinMaximumRetryCount(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getKeyguardPinMaximumRetryCount : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMaximumRetryCount =
                iUcmAgentService != null
                        ? iUcmAgentService.getKeyguardPinMaximumRetryCount()
                        : null;
        if (keyguardPinMaximumRetryCount != null) {
            return keyguardPinMaximumRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle getKeyguardPinMinimumLength(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getKeyguardPinMinimumLength : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMinimumLength =
                iUcmAgentService != null ? iUcmAgentService.getKeyguardPinMinimumLength() : null;
        if (keyguardPinMinimumLength != null) {
            return keyguardPinMinimumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final String getKeyguardStorageForCurrentUser(int i) {
        FileInputStream openRead;
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "getKeyguardStorageForCurrentUser : ", "UcmService");
        this.mSecurityHelper.getClass();
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(Environment.getUserSystemDirectory(i), "ucm_keyguardconfig.xml"));
        String str = null;
        if (!atomicFile.getBaseFile().exists()) {
            return null;
        }
        Log.i("UcmService", "getKeyguardStorageForCurrentUser, isFileExist : exist");
        try {
            openRead = atomicFile.openRead();
        } catch (FileNotFoundException e) {
            Log.e(
                    "UcmService",
                    "getKeyguardStorageForCurrentUser, The exception occurs " + e.getMessage());
        } catch (IOException e2) {
            Log.e(
                    "UcmService",
                    "getKeyguardStorageForCurrentUser, The exception occurs " + e2.getMessage());
        } catch (XmlPullParserException e3) {
            Log.e(
                    "UcmService",
                    "getKeyguardStorageForCurrentUser, The exception occurs " + e3.getMessage());
        }
        try {
            XmlPullParser newPullParser = Xml.newPullParser();
            newPullParser.setInput(openRead, null);
            for (int eventType = newPullParser.getEventType();
                    eventType != 2 && eventType != 1;
                    eventType = newPullParser.next()) {}
            if ("keyguard".equals(newPullParser.getName())) {
                int next = newPullParser.next();
                String str2 = null;
                do {
                    if (next == 2) {
                        try {
                            if (newPullParser.getDepth() == 2
                                    && "vendor".equals(newPullParser.getName())) {
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
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "getKeyguardStorageForCurrentUser : ", str, "UcmService");
            return str;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final UcmAgentWrapper getODEAgent() {
        UcmAgentWrapper.AgentInfo agentInfo;
        if (DBG) {
            Log.i("UcmService", "getODEAgent");
        }
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (loadODEConfig.csName == null) {
            Log.i("UcmService", "Failed to load ODE properties");
            return null;
        }
        Iterator it = ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper != null
                    && (agentInfo = ucmAgentWrapper.info) != null
                    && agentInfo.agentId != null) {
                try {
                    if (new String(loadODEConfig.csName, "UTF-8")
                            .equals(ucmAgentWrapper.info.agentId)) {
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

    public final ucmRetParcelable getODEConfigurationForVold(String str) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                "getODEConfigurationForVold [", str, "]", "UcmService");
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
                i = ((String) it.next()).length() + i + 1;
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
            return new ucmRetParcelable(0, bArr);
        } catch (Exception e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
            return getResponseParcel(15);
        }
    }

    public final Bundle getODESettingsConfiguration() {
        Log.i("UcmService", "getODESettingsConfiguration");
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle = new Bundle();
        EFSProperties.ODEProperties loadODEConfig = EFSProperties.loadODEConfig();
        if (UcmServiceODE.getOdeStatus() == 2) {
            Log.i("UcmService", "Device is encrypted as UCM");
            bundle.putBoolean("odeEnabled", true);
        }
        byte[] bArr = loadODEConfig.csName;
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    String str = new String(bArr, "UTF-8");
                    Log.i("UcmService", "agentId : ".concat(str));
                    bundle.putString("id", str);
                }
            } catch (UnsupportedEncodingException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
            }
        }
        bundle.putByteArray("odeSignature", loadODEConfig.pluginSignatureHash);
        bundle.putByteArray("aid", loadODEConfig.AID);
        int i = loadODEConfig.storageType;
        if (i >= 0 && i < 10) {
            bundle.putString("storagetype", EFSProperties.STORAGE_TYPES[i]);
        }
        int i2 = loadODEConfig.enabledSCP;
        if (i2 >= 0 && i2 < 4) {
            bundle.putString("scptype", EFSProperties.SCP_TYPES[i2]);
        }
        return bundle;
    }

    public final ucmRetParcelable getOdeKey(String str, byte[] bArr) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m("getOdeKey [", str, "]", "UcmService");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("getOdeKey");
            if (bArr == null || bArr.length == 0) {
                Log.i("UcmService", "getOdeKey. wrappedKey is empty");
                return getResponseParcel(16);
            }
            try {
                byte[] oDEKey = EsecommAdapter.getEsecommAdapter().getODEKey(bArr);
                return oDEKey == null ? getResponseParcel(13) : new ucmRetParcelable(0, oDEKey);
            } catch (Exception e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
                return getResponseParcel(24);
            }
        } catch (Exception e2) {
            Log.i("UcmService", "The exception occurs " + e2.getMessage());
            return getResponseParcel(15);
        }
    }

    public final UcmErcomSpecific getOdeVendorSpecific() {
        if (this.mUcmErcomSpecific == null) {
            this.mUcmErcomSpecific = new UcmErcomSpecific(getODEAgent());
        }
        return this.mUcmErcomSpecific;
    }

    public final Bundle getStatus(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getStatus : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        if (str == null || true == "".equals(str)) {
            Log.i("UcmService", "csName is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        getActiveAgent(UniversalCredentialUtil.getSource(str));
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (activeAgent.info == null) {
            Log.i(
                    "UcmService",
                    "no agent info found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle status = iUcmAgentService != null ? iUcmAgentService.getStatus() : null;
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
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "values get from agent : ", " ", " ");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i3, i4, " ", " ", m);
        ServiceKeeper$$ExternalSyntheticOutline0.m(i5, i6, " ", " ", m);
        m.append(i7);
        m.append(" ");
        m.append(i8);
        Log.i("UcmService", m.toString());
        applyMetaData(i3, "maxAuthCnt", status);
        applyMetaData(i5, "maxPinLength", status);
        applyMetaData(i4, "minPinLength", status);
        applyMetaData(i6, "authMode", status);
        applyMetaData(i7, "minPukLength", status);
        applyMetaData(i8, "maxPukLength", status);
        displayToastFromAgentResponse(this.mContext, status);
        return status;
    }

    public final synchronized UniversalCredentialManagerService getUCMMDMService() {
        try {
            if (this.mUCMMDMService == null) {
                this.mUCMMDMService =
                        (UniversalCredentialManagerService)
                                ServiceManager.getService("knox_ucsm_policy");
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mUCMMDMService;
    }

    public final boolean grantKeyChainAccess(String str, int i) {
        Log.i("UcmService", "grantKeyChainAccess " + str + " for " + i);
        this.mSecurityHelper.getClass();
        if (!this.mSecurityHelper.isSystemCaller()) {
            throw new IllegalStateException("");
        }
        if (UniversalCredentialUtil.isKeyChainUri(str)) {
            return true;
        }
        Log.i("UcmService", "Not Keychain URI");
        return false;
    }

    public final Bundle importKey(String str, Bundle bundle) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m("importKey(", str, ")", "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "importKey: NULL agent for uri ", str, "UcmService");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        bundle.putInt("callerUid", callingUid);
        bundle.putInt("user_id", userId);
        bundle.putInt("ownerUid", callingUid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            true,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", "WARNING!!! importKey is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("UcmService", "importKey: KEY_RESOURCE_ID = " + bundle.getInt("resource", -2));
            Log.i("UcmService", "importKey: KEY_USER_ID     = " + bundle.getInt("user_id", -2));
            Log.i("UcmService", "importKey: KEY_CALLER_UID  = " + bundle.getInt("callerUid", -2));
            Log.i(
                    "UcmService",
                    "importKey: KEY_ALGORITHM   = " + bundle.getString("algorithm", "AES"));
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle importKey =
                    iUcmAgentService != null ? iUcmAgentService.importKey(rawAlias, bundle) : null;
            if (importKey != null) {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService importKey. Response from plugin"
                            + " with error code = "
                                + importKey.getInt("errorresponse"));
            } else {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService importKey. Response from plugin"
                            + " is NULL");
            }
            return importKey;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        this.mSecurityHelper.getClass();
        Bundle importKeyPairInternal = importKeyPairInternal(str, bArr, bArr2, bundle, false);
        if (importKeyPairInternal != null) {
            Log.i(
                    "UcmService",
                    "UCMERRORTESTING: @CredentialManagerService importKeyPair Response from plugin"
                        + " with error code = "
                            + importKeyPairInternal.getInt("errorresponse"));
        } else {
            Log.i(
                    "UcmService",
                    "UCMERRORTESTING: @CredentialManagerService importKeyPair Response from plugin"
                        + " is null");
        }
        return importKeyPairInternal;
    }

    public final Bundle importKeyPairInternal(
            String str, byte[] bArr, byte[] bArr2, Bundle bundle, boolean z) {
        String str2;
        Log.i("UcmService", "importKeyPairInternal " + str);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "importKeyPairInternal : NULL agent for uri ", str, "UcmService");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent= " + activeAgent.info.id);
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        if (uid == -1 || callingUid == uid || callingUid != 1000) {
            uid = callingUid;
        }
        int userId = UserHandle.getUserId(uid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putBoolean("ismdm", z);
        if (z) {
            str2 = "WARNING!!!! importKeyPairInternal is NOT allowed for URI = ";
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
            str2 = "WARNING!!!! importKeyPairInternal is NOT allowed for URI = ";
            bundle3.putInt("callerUid", uid);
            bundle3.putInt("ownerUid", uid);
            bundle3.putInt("resource", resourceId);
            bundle3.putInt("user_id", userId);
            bundle3.putString("algorithm", bundle.getString("algorithm", "RSA"));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            uid,
                            z,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i("UcmService", str2 + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i(
                    "UcmService",
                    "importKeyPairInternal KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i(
                    "UcmService",
                    "importKeyPairInternal KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i(
                    "UcmService",
                    "importKeyPairInternal KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            Log.i(
                    "UcmService",
                    "importKeyPairInternal KEY_ALGORITHM= "
                            + bundle3.getString("algorithm", "RSA"));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            if (iUcmAgentService != null) {
                return iUcmAgentService.importKeyPair(rawAlias, bArr, bArr2, bundle3);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle initKeyguardPin(String str, String str2, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "initKeyguardPin : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        if (str2 == null || true == "".equals(str2)) {
            return getErrorParameterBundle(16);
        }
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService()
                .enforceSecurityPermission(
                        new ContextInfo(Binder.getCallingUid()),
                        generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle initKeyguardPin =
                iUcmAgentService != null ? iUcmAgentService.initKeyguardPin(str2, bundle) : null;
        if (initKeyguardPin != null) {
            return initKeyguardPin;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle installCertificate(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
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
        Log.i(
                "UcmService",
                "UCMERRORTESTING: @CredentialManagerService installCertificate Response from plugin"
                    + " with error code = "
                        + i);
        return importKeyPairInternal;
    }

    public final Bundle installCertificateIfSupported(
            String str, byte[] bArr, String str2, Bundle bundle) {
        Log.i("UcmService", "installCertificateIfSupported()");
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "getKeyType: NULL agent for uri ", str, "UcmService");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        Log.i("UcmService", "agent = " + activeAgent.info.id);
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        bundle.putInt("callerUid", callingUid);
        bundle.putInt("user_id", userId);
        bundle.putInt("ownerUid", callingUid);
        bundle.putInt("resource", resourceId);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            true,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i(
                        "UcmService",
                        "WARNING!!! installCertificateIfSupported is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i(
                    "UcmService",
                    "installCertificateIfSupported: KEY_RESOURCE_ID = "
                            + bundle.getInt("resource", -2));
            Log.i(
                    "UcmService",
                    "installCertificateIfSupported: KEY_USER_ID     = "
                            + bundle.getInt("user_id", -2));
            Log.i(
                    "UcmService",
                    "installCertificateIfSupported: KEY_CALLER_UID  = "
                            + bundle.getInt("callerUid", -2));
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle installCertificateIfSupported =
                    iUcmAgentService != null
                            ? iUcmAgentService.installCertificateIfSupported(
                                    rawAlias, bArr, str2, bundle)
                            : null;
            if (installCertificateIfSupported != null) {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService installCertificateIfSupported."
                            + " Response from plugin with error code = "
                                + installCertificateIfSupported.getInt("errorresponse"));
            } else {
                Log.i(
                        "UcmService",
                        "UCMERRORTESTING: @CredentialManagerService installCertificateIfSupported."
                            + " Response from plugin is NULL");
            }
            return installCertificateIfSupported;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isKeyChainGranted(String str, int i) {
        return true;
    }

    public final boolean isPluginUsedInOtherUser(int i, int i2, String str, boolean z) {
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
                            String keyguardStorageForCurrentUser =
                                    getKeyguardStorageForCurrentUser(i3);
                            if (keyguardStorageForCurrentUser != null
                                    && !keyguardStorageForCurrentUser.isEmpty()
                                    && !keyguardStorageForCurrentUser.equalsIgnoreCase("none")) {
                                if (keyguardStorageForCurrentUser.equals(str)) {
                                    Log.i("UcmService", "this plugin is used in other user");
                                    if (!z) {
                                        Log.i(
                                                "UcmService",
                                                "skip check configurator. pluginUsedInOtherUser");
                                        return true;
                                    }
                                    try {
                                        if (i2
                                                == Integer.parseInt(
                                                        getKeyguardStorageOwnerForCurrentUser(
                                                                userInfo.id))) {
                                            Log.i(
                                                    "UcmService",
                                                    "And it is enabled by same configurator");
                                            return true;
                                        }
                                    } catch (NumberFormatException unused) {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }
                            Log.i("UcmService", "UCM keyguard is not enabled");
                        }
                    }
                }
            } catch (Exception e) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), "UcmService");
            }
        }
        return false;
    }

    public final boolean isUserCertificatesExistInUCS() {
        Log.i("UcmService", "isUserCertificatesExistInUCS called");
        this.mSecurityHelper.checkCallerPermissionFor("isUserCertificatesExistInUCS");
        Bundle bundle = new Bundle();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        bundle.putInt("callerUid", 1000);
        bundle.putInt("user_id", userId);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("removable_user_certificates_list", true);
        bundle.putBundle("extraArgs", bundle2);
        Log.i("UcmService", "Iteration has started....");
        Iterator it = ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
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
                        String[] strArr =
                                this.mPolicyManager.getallAliasesforUserId(userId, ucmAgentWrapper);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        ArrayList arrayList = new ArrayList();
                        bundle.putInt("resource", 1);
                        Bundle saw = ucmAgentWrapper.saw(bundle);
                        String[] stringArray =
                                saw != null ? saw.getStringArray("stringarrayresponse") : null;
                        if (stringArray != null) {
                            Collections.addAll(arrayList, stringArray);
                        }
                        if (strArr != null) {
                            for (int i = 0; i < strArr.length; i++) {
                                if (arrayList.contains(strArr[i])) {
                                    arrayList.remove(strArr[i]);
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            return true;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        bundle.putInt("resource", 3);
                        Bundle saw2 = ucmAgentWrapper.saw(bundle);
                        String[] stringArray2 =
                                saw2 != null ? saw2.getStringArray("stringarrayresponse") : null;
                        if (stringArray2 != null) {
                            Collections.addAll(arrayList2, stringArray2);
                        }
                        if (strArr != null) {
                            for (int i2 = 0; i2 < strArr.length; i2++) {
                                if (arrayList2.contains(strArr[i2])) {
                                    arrayList2.remove(strArr[i2]);
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

    public final Bundle[] listAllProviders() {
        this.mSecurityHelper.checkCallerPermissionFor("listAllProviders");
        return listExposedProvidersInternal(true);
    }

    public final Bundle[] listExposedProvidersInternal(boolean z) {
        Bundle[] bundleArr;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).size() == 0) {
            Log.i("UcmService", "listProvidersInternal:No activeAgent");
            bundleArr = null;
        } else {
            Log.i(
                    "UcmService",
                    "listProvidersInternal "
                            + ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).size()
                            + " for "
                            + callingUid
                            + " and ismdmcaller-"
                            + z);
            ArrayList arrayList = new ArrayList();
            Iterator it =
                    ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
            while (it.hasNext()) {
                UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
                if (ucmAgentWrapper != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    if (!z) {
                        try {
                            if (this.mPolicyManager.isSEStorageAccessAllowed(
                                            ucmAgentWrapper, userId, callingUid, z, null)
                                    == 0) {
                                Log.i(
                                        "UcmService",
                                        "WARNING!!!! access NOT allowed for "
                                                + ucmAgentWrapper.info.id);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    arrayList.add(getAgentInfoBundle(ucmAgentWrapper));
                }
            }
            Log.i("UcmService", "listProviders filtered " + arrayList.size());
            bundleArr = (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
        }
        ArrayList arrayList2 = new ArrayList(Arrays.asList(bundleArr));
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            Bundle bundle = (Bundle) it2.next();
            String string = bundle.getString("uniqueId");
            if (string == null || string.equals("")) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! null/empty ID returned for agent bundle. Skipping agent.");
            } else if (this.mPolicyManager.isCSobscure(getActiveAgent(string))) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! Obscure CS agent bundle. Skipping agent : ".concat(string));
            } else {
                arrayList3.add(bundle);
            }
        }
        return (Bundle[]) arrayList3.toArray(new Bundle[arrayList3.size()]);
    }

    public final Bundle[] listProviders() {
        this.mSecurityHelper.getClass();
        return listExposedProvidersInternal(false);
    }

    public final ucmRetParcelable mac(String str, byte[] bArr, String str2) {
        Log.i("UcmService", "mac " + str);
        Bundle bundle = new Bundle();
        if (bArr == null) {
            bundle.putInt("errorresponse", 4);
            return getResponseParcel(bundle);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "mac: NULL agent for URI: ", str, "UcmService");
            bundle.putByteArray("bytearrayresponse", null);
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i = resourceId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
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
            bundle2.putInt("user_id", userId);
            bundle2.putInt("ownerUid", callingUid);
            bundle2.putInt("resource", i);
            Log.i("UcmService", "mac KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
            Log.i("UcmService", "mac KEY_USER_ID= " + bundle2.getInt("user_id", -2));
            Log.i("UcmService", "mac KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle mac =
                    iUcmAgentService != null
                            ? iUcmAgentService.mac(rawAlias, bArr, str2, bundle2)
                            : null;
            if (mac == null) {
                Log.i("UcmService", "ERROR: Null response received from agent");
                Bundle bundle3 = new Bundle();
                bundle3.putByteArray("bytearrayresponse", null);
                bundle3.putInt("errorresponse", 14);
                return getResponseParcel(bundle3);
            }
            byte[] byteArray = mac.getByteArray("bytearrayresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    mac.getInt("errorresponse"),
                    "mac Response from plugin:  error code = ",
                    "UcmService");
            if (byteArray != null) {
                return getResponseParcel(mac);
            }
            Log.i("UcmService", "ERROR: Empty data received for mac");
            mac.putByteArray("bytearrayresponse", null);
            mac.putInt("errorresponse", 13);
            return getResponseParcel(mac);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle notifyChangeToPlugin(String str, int i, Bundle bundle) {
        boolean z;
        boolean z2;
        int i2;
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "notifyChangeToPlugin event ", "UcmService");
        this.mSecurityHelper.checkCallerPermissionFor("notifyChangeToPlugin");
        Bundle bundle2 = new Bundle();
        if (str != null) {
            Log.i("UcmService", "notifyChangeToPlugin for : ".concat(str));
            UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
            if (activeAgent == null) {
                Log.i(
                        "UcmService",
                        "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            if (activeAgent.isServiceBound()) {
                IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
                if ((iUcmAgentService != null ? iUcmAgentService.notifyChange(i, bundle) : -1)
                        == 0) {
                    bundle2.putBoolean("booleanresponse", true);
                    bundle2.putInt("errorresponse", 0);
                    return bundle2;
                }
                z = false;
                bundle2.putInt("errorresponse", 18);
            } else {
                z = false;
                bundle2.putInt("errorresponse", 14);
                Log.i("UcmService", "agent is not bound");
            }
            bundle2.putBoolean("booleanresponse", z);
            return bundle2;
        }
        Iterator it = ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper != null) {
                if (ucmAgentWrapper.isServiceBound()) {
                    CredentialStorage credentialStorage = new CredentialStorage();
                    credentialStorage.name = ucmAgentWrapper.info.id;
                    if (ucmAgentWrapper.componentName.getPackageName() != null) {
                        credentialStorage.packageName =
                                ucmAgentWrapper.componentName.getPackageName();
                        StringBuilder sb = new StringBuilder("checkIfNotify for cs Name = ");
                        sb.append(credentialStorage.name);
                        sb.append(" Package name = ");
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                sb, credentialStorage.packageName, "UcmService");
                        if (ucmAgentWrapper.info.enforceManagement) {
                            Log.i("UcmService", "notifying to managed plugin");
                            Iterator it2 =
                                    ((UserManager) this.mContext.getSystemService("user"))
                                            .getUsers()
                                            .iterator();
                            z2 = false;
                            while (it2.hasNext()) {
                                int i3 = ((UserInfo) it2.next()).id;
                                DirEncryptService$$ExternalSyntheticOutline0.m(
                                        i3, "checkIfNotify: Valid userid - ", "UcmService");
                                z2 = this.mPolicyManager.isStorageEnabled(i3, credentialStorage);
                                if (z2) {
                                    break;
                                }
                            }
                        } else {
                            Log.i("UcmService", "notifying to unmanaged plugin");
                            z2 = true;
                        }
                    } else {
                        Log.i("UcmService", "Package name for CS found NULL. Cannot notify.");
                        z2 = false;
                    }
                    if (z2) {
                        IUcmAgentService iUcmAgentService2 = ucmAgentWrapper.mUcmAgentService;
                        i2 =
                                iUcmAgentService2 != null
                                        ? iUcmAgentService2.notifyChange(i, bundle)
                                        : -1;
                        Log.i(
                                "UcmService",
                                "activeAgent "
                                        + ucmAgentWrapper.info.id
                                        + " notify status - "
                                        + i2);
                    } else {
                        Log.i(
                                "UcmService",
                                "activeAgent " + ucmAgentWrapper.info.id + " not notified");
                        i2 = 0;
                    }
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i2, "activeAgent status-", "UcmService");
                    if (!z3 && i2 != 0) {
                        bundle2.putInt("errorresponse", 18);
                        z3 = true;
                    }
                } else if (17 == i) {
                    Log.i("UcmService", "EVENT_BOOT_COMPLETED, triggerNotifyChange");
                    Queue queue = ucmAgentWrapper.mEventBoxQueue;
                    UcmAgentWrapper.EventBox eventBox = new UcmAgentWrapper.EventBox();
                    eventBox.eventId = i;
                    eventBox.eventData = bundle;
                    ((LinkedList) queue).offer(eventBox);
                } else {
                    bundle2.putInt("errorresponse", 14);
                    Log.i("UcmService", "agentService is null");
                    z3 = true;
                }
            }
        }
        if (!z3) {
            bundle2.putInt("errorresponse", 0);
        }
        bundle2.putBoolean("booleanresponse", true);
        return bundle2;
    }

    public final boolean notifyLicenseStatus(String str, String str2, int i) {
        this.mSecurityHelper.checkCallerPermissionFor("notifyLicenseStatus");
        Log.i(
                "UcmService",
                "notifyLicenseStatus packageName " + str + ",status-" + str2 + ", errorCode-" + i);
        Message obtainMessage = obtainMessage(4);
        int callingUid = Binder.getCallingUid();
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putString(Constants.JSON_CLIENT_DATA_STATUS, str2);
        bundle.putInt("errorCode", i);
        obtainMessage.setData(bundle);
        UcmServiceAgentManager ucmServiceAgentManager = this.mUcmServiceAgentManager;
        int userId = UserHandle.getUserId(callingUid);
        if (!ucmServiceAgentManager.mNeedToBindESE
                && UcmServiceAgentManager.checkESEPermission(userId, str)) {
            ucmServiceAgentManager.mNeedToBindESE = true;
        }
        sendMessage(obtainMessage);
        return true;
    }

    public final void notifyPluginResult(Bundle bundle) {
        boolean z;
        boolean z2;
        FileOutputStream fileOutputStream;
        if (!getUCMMDMService().isCallerPackageManaged()) {
            throw new SecurityException("caller is not managed from EMM");
        }
        try {
            String nameForUid = this.mPm.getNameForUid(Binder.getCallingUid());
            boolean z3 = false;
            if (bundle.getInt("status_code", 0) == 38) {
                UniversalCredentialManagerService uCMMDMService = getUCMMDMService();
                uCMMDMService.getClass();
                Log.i(UniversalCredentialManagerService.TAG, "deleteAllDatabaseContent()");
                String[] strArr = {"storagePackageName"};
                String[] strArr2 = {nameForUid};
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "Database compromised, table delete result=",
                        "UcmService",
                        uCMMDMService.mEdmStorageProvider.deleteDataByFields(
                                        "UniversalCredentialWhiteListTable", strArr, strArr2)
                                & uCMMDMService.mEdmStorageProvider.deleteDataByFields(
                                        "UniversalCredentialCertificateTable", strArr, strArr2));
            }
            UcmServiceAppletHelper ucmServiceAppletHelper = this.mAppletHelper;
            ucmServiceAppletHelper.getClass();
            if ("00000001".equals(bundle.getString("RESPONSE_DATA", ""))) {
                if (((ArrayList) ucmServiceAppletHelper.mConfigAppletRequestIds)
                        .contains(Integer.valueOf(bundle.getInt("request_id", -1)))) {
                    if (bundle.containsKey("bytearrayresponse")) {
                        byte[] byteArray = bundle.getByteArray("bytearrayresponse");
                        int i = bundle.getInt("adminUid", -1);
                        if (byteArray != null && i != -1) {
                            EFSProperties.log("saveAppletDeletionLccmScript");
                            try {
                                fileOutputStream =
                                        new FileOutputStream(
                                                new File(
                                                        "/efs/sec_efs",
                                                        "ucm_delete_applet_lccmscript"));
                            } catch (Exception e) {
                                e.printStackTrace();
                                z = false;
                            }
                            try {
                                fileOutputStream.write(byteArray);
                                fileOutputStream.close();
                                z = true;
                                EFSProperties.log("savePluginName");
                                try {
                                    fileOutputStream =
                                            new FileOutputStream(
                                                    new File(
                                                            "/efs/sec_efs",
                                                            "ucm_applet_pluginpackagename"));
                                    try {
                                        fileOutputStream.write(
                                                String.valueOf(nameForUid)
                                                        .getBytes(StandardCharsets.UTF_8));
                                        fileOutputStream.close();
                                        z2 = true;
                                    } finally {
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    z2 = false;
                                }
                                String signatureHash =
                                        UcmServiceAppletHelper.getSignatureHash(
                                                ucmServiceAppletHelper.getPackageInfo(
                                                        UserHandle.getUserId(
                                                                Binder.getCallingUid()),
                                                        nameForUid));
                                EFSProperties.log("savePluginSigHash");
                                if (signatureHash.isEmpty()) {
                                    EFSProperties.log("hash is empty");
                                } else {
                                    try {
                                        fileOutputStream =
                                                new FileOutputStream(
                                                        new File(
                                                                "/efs/sec_efs",
                                                                "ucm_applet_plugin_hash_of_signature"));
                                        try {
                                            fileOutputStream.write(
                                                    signatureHash.getBytes(StandardCharsets.UTF_8));
                                            fileOutputStream.close();
                                            z3 = true;
                                        } finally {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th) {
                                                th.addSuppressed(th);
                                            }
                                        }
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                Log.i(
                                        "UcmServiceAppletHelper",
                                        "onAppletNotify. result of saving applet status, lccm ["
                                                + z
                                                + "], packageName ["
                                                + z2
                                                + "]");
                                if (UcmServiceAppletHelper.DBG) {
                                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                            "onAppletNotify. SignatureHash: ",
                                            "UcmServiceAppletHelper",
                                            z3);
                                }
                            } finally {
                            }
                        }
                    } else if (new File("/efs/sec_efs", "ucm_applet_pluginpackagename").exists()
                            || new File("/efs/sec_efs", "ucm_delete_applet_lccmscript").exists()) {
                        EFSProperties.clearAppletInfo();
                    }
                    ArrayList arrayList =
                            (ArrayList) ucmServiceAppletHelper.mConfigAppletRequestIds;
                    arrayList.remove(
                            arrayList.indexOf(Integer.valueOf(bundle.getInt("request_id", -1))));
                }
            }
            getUCMMDMService().notifyUCMConfigStatus(bundle);
        } catch (RemoteException e4) {
            Log.e(
                    "UcmService",
                    "notifyPluginResult. RemoteException retrieving package caller uid",
                    e4);
        }
    }

    public final ucmRetParcelable notifyVoldComplete(String str, byte[] bArr) {
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                "notifyVoldComplete [", str, "]", "UcmService");
        try {
            this.mSecurityHelper.checkCallerPermissionFor("notifyVoldComplete");
            sendEmptyMessage(6);
            this.mIsVoldCompleteNotified = true;
            return new ucmRetParcelable(0, new byte[] {111, 107});
        } catch (Exception e) {
            Log.i("UcmService", "The exception occurs " + e.getMessage());
            return getResponseParcel(15);
        }
    }

    public final void readPersistentAppletsInfoLocked() {
        int i;
        byte[] bArr;
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
                String str = null;
                newPullParser.setInput(openRead, null);
                int eventType = newPullParser.getEventType();
                while (true) {
                    i = 2;
                    if (eventType == 2 || eventType == 1) {
                        break;
                    } else {
                        eventType = newPullParser.next();
                    }
                }
                if ("applets".equals(newPullParser.getName())) {
                    int next = newPullParser.next();
                    while (true) {
                        if (next == i) {
                            if (newPullParser.getDepth() == i
                                    && "applet".equals(newPullParser.getName())) {
                                String attributeValue =
                                        newPullParser.getAttributeValue(str, "pluginName");
                                String attributeValue2 =
                                        newPullParser.getAttributeValue(str, "appletLocation");
                                String attributeValue3 =
                                        newPullParser.getAttributeValue(str, "adminId");
                                String attributeValue4 =
                                        newPullParser.getAttributeValue(str, "aid");
                                int parseInt = Integer.parseInt(attributeValue3);
                                if (attributeValue4 != null) {
                                    int length = attributeValue4.length();
                                    bArr = new byte[length / 2];
                                    for (int i2 = 0; i2 < length; i2 += 2) {
                                        bArr[i2 / 2] =
                                                (byte)
                                                        (Character.digit(
                                                                        attributeValue4.charAt(
                                                                                i2 + 1),
                                                                        16)
                                                                + (Character.digit(
                                                                                attributeValue4
                                                                                        .charAt(i2),
                                                                                16)
                                                                        << 4));
                                    }
                                } else {
                                    bArr = null;
                                }
                                AppletProperties appletProperties =
                                        new AppletProperties(
                                                attributeValue2, attributeValue, parseInt, bArr);
                                if (!this.mPersistentAppletInfo.containsKey(attributeValue)) {
                                    this.mPersistentAppletInfo.put(
                                            attributeValue, appletProperties);
                                }
                            }
                        }
                        next = newPullParser.next();
                        if (next == 1) {
                            break;
                        }
                        str = null;
                        i = 2;
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
        for (Map.Entry entry : this.mPersistentAppletInfo.entrySet()) {
            String str2 = (String) entry.getKey();
            AppletProperties appletProperties2 = (AppletProperties) entry.getValue();
            Log.i("UcmService", "PersistentApplet  key-" + str2);
            Log.i("UcmService", "PersistentApplet  pluginName-" + appletProperties2.pluginName);
            Log.i(
                    "UcmService",
                    "PersistentApplet  AID-" + convertByteToString(appletProperties2.aid));
            Log.i(
                    "UcmService",
                    "PersistentApplet  appletLocation-" + appletProperties2.appletLocation);
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder("PersistentApplet  adminId-"),
                    appletProperties2.adminId,
                    "UcmService");
        }
    }

    public final void registerSystemUICallback(
            ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback) {
        this.mSystemUICallback = iCredentialManagerServiceSystemUICallback;
        String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(0);
        ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback2 =
                this.mSystemUICallback;
        if (iCredentialManagerServiceSystemUICallback2 != null) {
            try {
                iCredentialManagerServiceSystemUICallback2.setUCMKeyguardVendor(
                        keyguardStorageForCurrentUser);
            } catch (Exception e) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), "UcmService");
            }
        }
    }

    public final void removeEnforcedLockTypeNotification(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "removeEnforcedLockTypeNotification : ", "UcmService");
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
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

    public final int removeODESettings() {
        Log.i("UcmService", "removeODESettings");
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        boolean z = true;
        if (UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_wpc_dar") == 1) {
            Log.i("UcmService", "disable ODE WPC in UCS");
            SystemProperties.set("persist.sys.knox.UCM_WPC", "false");
            if (EFSProperties.deleteODEConfig()) {
                File file = new File("/efs/sec_efs", "ucm_wpc_dar");
                if (file.exists() && !(z = file.delete())) {
                    EFSProperties.log("failed to delete the existing ODE flag");
                }
            } else {
                z = false;
            }
            if (!z) {
                return 269;
            }
            Log.i("UcmService", "removeUCMWPCNotification ");
            getUCMMDMService()
                    .enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
            NotificationManager notificationManager =
                    (NotificationManager) this.mContext.getSystemService("notification");
            if (notificationManager == null) {
                Log.i("UcmService", "Failed to get NotificationManager");
            } else {
                notificationManager.cancel(9000);
            }
        }
        return 0;
    }

    public final void resetNonMdmCertificates() {
        Log.i("UcmService", "resetNonMdmCertificates called");
        this.mSecurityHelper.checkCallerPermissionFor("resetNonMdmCertificates");
        Bundle bundle = new Bundle();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        bundle.putInt("callerUid", 1000);
        bundle.putInt("user_id", userId);
        Log.i("UcmService", "Iteration has started....");
        Iterator it = ((ArrayList) this.mUcmServiceAgentManager.getActiveAgentList()).iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper != null) {
                if (!ucmAgentWrapper.isServiceBound() || ucmAgentWrapper.info.isReadOnly) {
                    Log.i("UcmService", "agent is not bound or not ready");
                } else if (this.mPolicyManager.isCSobscure(ucmAgentWrapper)) {
                    Log.i("UcmService", "activeAgent is CSobscure");
                } else {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    bundle.putInt("resource", 1);
                    Bundle saw = ucmAgentWrapper.saw(bundle);
                    String[] stringArray =
                            saw != null ? saw.getStringArray("stringarrayresponse") : null;
                    if (stringArray != null) {
                        Collections.addAll(arrayList, stringArray);
                    }
                    bundle.putInt("resource", 3);
                    Bundle saw2 = ucmAgentWrapper.saw(bundle);
                    String[] stringArray2 =
                            saw2 != null ? saw2.getStringArray("stringarrayresponse") : null;
                    if (stringArray2 != null) {
                        Collections.addAll(arrayList2, stringArray2);
                    }
                    if (arrayList.isEmpty() && arrayList2.isEmpty()) {
                        Log.i(
                                "UcmService",
                                "agentAliasesListKeychain.isEmpty() &&"
                                    + " agentAliasesListWiFi.isEmpty() .... continue");
                    } else {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            String[] strArr =
                                    this.mPolicyManager.getallAliasesforUserId(
                                            userId, ucmAgentWrapper);
                            if (strArr != null) {
                                for (int i = 0; i < strArr.length; i++) {
                                    if (arrayList.contains(strArr[i])) {
                                        arrayList.remove(strArr[i]);
                                    } else if (arrayList2.contains(strArr[i])) {
                                        arrayList2.remove(strArr[i]);
                                    }
                                }
                            }
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                String str = (String) it2.next();
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "request to delete KEYCHAIN for alias: ",
                                        str,
                                        "UcmService");
                                bundle.putInt("resource", 1);
                                IUcmAgentService iUcmAgentService =
                                        ucmAgentWrapper.mUcmAgentService;
                                if (iUcmAgentService != null) {
                                    iUcmAgentService.delete(str, bundle);
                                }
                            }
                            Iterator it3 = arrayList2.iterator();
                            while (it3.hasNext()) {
                                String str2 = (String) it3.next();
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "request to delete WIFI for alias: ", str2, "UcmService");
                                bundle.putInt("resource", 3);
                                IUcmAgentService iUcmAgentService2 =
                                        ucmAgentWrapper.mUcmAgentService;
                                if (iUcmAgentService2 != null) {
                                    iUcmAgentService2.delete(str2, bundle);
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
        }
    }

    public final Bundle resetUid(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("resetUid ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        int i2 = (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid;
        int userId = UserHandle.getUserId(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()), userId, i2, false, null)
                    == 0) {
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
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle resetUid = iUcmAgentService != null ? iUcmAgentService.resetUid(i) : null;
            if (resetUid != null) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        resetUid.getInt("errorresponse"),
                        "resetUid Response from plugin:  error code = ",
                        "UcmService");
                return resetUid;
            }
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle resetUser(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("resetUser ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        int i2 = (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid;
        int userId = UserHandle.getUserId(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()), userId, i2, false, null)
                    == 0) {
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
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle resetUser = iUcmAgentService != null ? iUcmAgentService.resetUser(i) : null;
            if (resetUser != null) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        resetUser.getInt("errorresponse"),
                        "resetUser Response from plugin:  error code = ",
                        "UcmService");
                return resetUser;
            }
            Log.i("UcmService", "ERROR: Null Response received from agent");
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("booleanresponse", false);
            bundle2.putInt("errorresponse", 13);
            return bundle2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int saveTempOdeKey(String str) {
        _DekData _dekdata;
        try {
            EsecommAdapter esecommAdapter = EsecommAdapter.getEsecommAdapter();
            UcmErcomSpecific odeVendorSpecific = getOdeVendorSpecific();
            odeVendorSpecific.getClass();
            Log.i("UcmService_ercom", "getDeks " + str);
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            UcmAgentWrapper oDEAgent = credentialManagerService.getODEAgent();
            odeVendorSpecific.mAgent = oDEAgent;
            ucmRetParcelable generateWrappedDek =
                    credentialManagerService.generateWrappedDek(str, oDEAgent);
            int i = generateWrappedDek.mResult;
            if (i != 0) {
                _dekdata = new _DekData(i);
            } else {
                byte[] bArr = generateWrappedDek.mData;
                if (bArr == null || bArr.length == 0) {
                    Log.i("UcmService_ercom", "getDeks. generateWrappedDek return empty");
                    _dekdata = new _DekData(18);
                } else {
                    ucmRetParcelable unwrapDek =
                            credentialManagerService.unwrapDek(str, bArr, odeVendorSpecific.mAgent);
                    int i2 = unwrapDek.mResult;
                    if (i2 != 0) {
                        _dekdata = new _DekData(i2);
                    } else {
                        byte[] bArr2 = unwrapDek.mData;
                        if (bArr2 == null || bArr2.length == 0) {
                            Log.i("UcmService_ercom", "getDeks. unwrapDek return empty");
                            _dekdata = new _DekData(18);
                        } else {
                            _DekData _dekdata2 = new _DekData(0);
                            _dekdata2.wrappedDek = bArr;
                            _dekdata2.dek = bArr2;
                            _dekdata = _dekdata2;
                        }
                    }
                }
            }
            int i3 = _dekdata.errorCode;
            if (i3 != 0) {
                return i3;
            }
            byte[] bArr3 = _dekdata.wrappedDek;
            byte[] bArr4 = _dekdata.dek;
            if (bArr4 == null || bArr3 == null) {
                Log.i("UcmService", "ERROR: key is null");
                return 18;
            }
            byte[] saveODEKey = esecommAdapter.saveODEKey(bArr4);
            if (saveODEKey == null) {
                Log.i("UcmService", "ERROR: failed to wrap dek");
                return 18;
            }
            byte[] saveODEKey2 = esecommAdapter.saveODEKey(bArr3);
            if (saveODEKey2 != null) {
                return (UcmServiceUtil.saveDataToFile("ucm_ode_key", saveODEKey)
                                && UcmServiceUtil.saveDataToFile("ucm_ode_key2", saveODEKey2))
                        ? 0
                        : 18;
            }
            Log.i("UcmService", "ERROR: failed to wrap pluginWrappedK0");
            return 18;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
            return 24;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:215:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x05ce A[LOOP:9: B:223:0x05c8->B:225:0x05ce, LOOP_END] */
    /* JADX WARN: Type inference failed for: r1v24, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle saw(java.lang.String r33, int r34) {
        /*
            Method dump skipped, instructions count: 1545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.CredentialManagerService.saw(java.lang.String,"
                    + " int):android.os.Bundle");
    }

    public final Bundle sawInternal(String str, int i, int i2) {
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "sawInternal() ", str, "; userId = ", "; resourceType=");
        m.append(i2);
        Log.i("UcmService", m.toString());
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
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder("sawInternal() agentAliases.length="),
                    stringArray.length,
                    "UcmService");
            for (String str2 : stringArray) {
                Log.i("UcmService", "sawInternal() agentAliases=" + str2);
            }
        }
        return saw;
    }

    public final void sendStateChangeBroadcast(String str) {
        Intent intent = new Intent("com.samsung.ucs.ucsservice.stateblocked");
        String source = UniversalCredentialUtil.getSource(str);
        intent.putExtra("UCS_STATE", 133);
        intent.putExtra("UCS_CSNAME", source);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "Broadcast CSNAME ", source, "UcmService");
        this.mContext.sendBroadcastAsUser(intent, UserHandle.OWNER);
        try {
            if (this.mPersonaManager == null) {
                this.mPersonaManager =
                        (SemPersonaManager) this.mContext.getSystemService("persona");
            }
            for (Integer num : this.mPersonaManager.getKnoxIds(true)) {
                Log.i("KnoxKeyguardReceiver", "send personaId : " + num);
                intent.setComponent(
                        new ComponentName(
                                "com.samsung.knox.kss",
                                "com.samsung.knox.kss.KnoxKeyguardReceiver"));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(num.intValue()));
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "KnoxKeyguardReceiver");
        }
    }

    public final void sendUCMKeyguardIntent(int i, String str, boolean z) {
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i, "sendUCMKeyguardIntent set - ", ", userId-", ", storage-", z),
                str,
                "UcmService");
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:109:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0202 A[Catch: Exception -> 0x0225, TryCatch #1 {Exception -> 0x0225, blocks: (B:44:0x015c, B:51:0x01e7, B:53:0x0202, B:55:0x0208, B:57:0x0210, B:58:0x0257, B:65:0x0229, B:67:0x0231, B:68:0x0236, B:70:0x0243), top: B:43:0x015c }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x025e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle setAdminConfigureBundleForCs(
            int r24, int r25, java.lang.String r26, android.os.Bundle r27, int r28) {
        /*
            Method dump skipped, instructions count: 802
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.CredentialManagerService.setAdminConfigureBundleForCs(int,"
                    + " int, java.lang.String, android.os.Bundle, int):android.os.Bundle");
    }

    public final Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setCertificateChain ", str, "UcmService");
        this.mSecurityHelper.getClass();
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
        int userId = UserHandle.getUserId(callingUid);
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            userId,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
                Log.i(
                        "UcmService",
                        "WARNING!!!! setCertificateChain is NOT allowed for URI = " + str);
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 15);
                return bundle2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("callerUid", callingUid);
            bundle3.putInt("user_id", userId);
            bundle3.putInt("ownerUid", callingUid);
            bundle3.putInt("resource", resourceId);
            bundle3.putBundle("extraArgs", bundle);
            Log.i(
                    "UcmService",
                    "setCertificateChain KEY_RESOURCE_ID= " + bundle3.getInt("resource", -2));
            Log.i(
                    "UcmService",
                    "setCertificateChain KEY_USER_ID= " + bundle3.getInt("user_id", -2));
            Log.i(
                    "UcmService",
                    "setCertificateChain KEY_CALLER_UID= " + bundle3.getInt("callerUid", -2));
            if (!activeAgent.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle2.putBoolean("booleanresponse", false);
                bundle2.putInt("errorresponse", 14);
                return bundle2;
            }
            String rawAlias = UniversalCredentialUtil.getRawAlias(str);
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle certificateChain =
                    iUcmAgentService != null
                            ? iUcmAgentService.setCertificateChain(rawAlias, bArr, bundle)
                            : null;
            if (certificateChain == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle4 = new Bundle();
                bundle4.putBoolean("booleanresponse", false);
                bundle4.putInt("errorresponse", 14);
                return bundle4;
            }
            boolean z = certificateChain.getBoolean("booleanresponse");
            int i = certificateChain.getInt("errorresponse");
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "setCertificateChain Response from plugin:  error code = ", "UcmService");
            if (!z && i == 0) {
                Log.i("UcmService", "ERROR: Empty data received for setCertificateChain");
                certificateChain.putBoolean("booleanresponse", false);
                certificateChain.putInt("errorresponse", 13);
            }
            return certificateChain;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle setCredentialStorageProperty(int i, String str, Bundle bundle, int i2) {
        this.mSecurityHelper.getClass();
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        Bundle bundle2 = new Bundle();
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            bundle2.putInt("intresponse", -1);
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (activeAgent.isServiceBound()) {
            IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
            Bundle credentialStorageProperty =
                    iUcmAgentService != null
                            ? iUcmAgentService.setCredentialStorageProperty(i, i2, bundle)
                            : null;
            Log.i("UcmService", "setCredentialStorageProperty Response from plugin");
            return credentialStorageProperty;
        }
        Log.i("UcmService", "agent is not bound");
        bundle2.putInt("intresponse", -1);
        bundle2.putInt("errorresponse", 14);
        return bundle2;
    }

    public final Bundle setKeyguardPinMaximumLength(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setKeyguardPinMaximumLength : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService()
                .enforceSecurityPermission(
                        new ContextInfo(Binder.getCallingUid()),
                        generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMaximumLength =
                iUcmAgentService != null ? iUcmAgentService.setKeyguardPinMaximumLength(i) : null;
        if (keyguardPinMaximumLength != null) {
            return keyguardPinMaximumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle setKeyguardPinMaximumRetryCount(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setKeyguardPinMaximumRetryCount : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService()
                .enforceSecurityPermission(
                        new ContextInfo(Binder.getCallingUid()),
                        generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMaximumRetryCount =
                iUcmAgentService != null
                        ? iUcmAgentService.setKeyguardPinMaximumRetryCount(i)
                        : null;
        if (keyguardPinMaximumRetryCount != null) {
            return keyguardPinMaximumRetryCount;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final Bundle setKeyguardPinMinimumLength(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setKeyguardPinMinimumLength : ", str, "UcmService");
        this.mSecurityHelper.getClass();
        UcmAgentWrapper activeAgentFromUri = getActiveAgentFromUri(str);
        if (activeAgentFromUri == null || !activeAgentFromUri.isServiceBound()) {
            return getErrorParameterBundle(14);
        }
        getUCMMDMService()
                .enforceSecurityPermission(
                        new ContextInfo(Binder.getCallingUid()),
                        generateCS(str, activeAgentFromUri.info.packageName));
        if (!activeAgentFromUri.info.supportPinConfiguration) {
            Log.i("UcmService", "Agent does not support this api");
            return getErrorParameterBundle(3);
        }
        IUcmAgentService iUcmAgentService = activeAgentFromUri.mUcmAgentService;
        Bundle keyguardPinMinimumLength =
                iUcmAgentService != null ? iUcmAgentService.setKeyguardPinMinimumLength(i) : null;
        if (keyguardPinMinimumLength != null) {
            return keyguardPinMinimumLength;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        return getErrorParameterBundle(13);
    }

    public final boolean setKeyguardStorageForCurrentUser(int i, String str, String str2) {
        Log.i("UcmService", "setKeyguardStorageForCurrentUser called : " + str);
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(Environment.getUserSystemDirectory(i), "ucm_keyguardconfig.xml"));
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, "utf-8");
                fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
                fastXmlSerializer.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag((String) null, "keyguard");
                fastXmlSerializer.startTag((String) null, "vendor");
                fastXmlSerializer.attribute((String) null, "name", str);
                fastXmlSerializer.attribute((String) null, "owner", str2);
                fastXmlSerializer.endTag((String) null, "keyguard");
                fastXmlSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
                if ("none".equals(str)) {
                    SystemProperties.set("persist.keyguard.ucs", "false");
                    updateSystemUIMonitor(null);
                } else {
                    SystemProperties.set("persist.keyguard.ucs", "true");
                    updateSystemUIMonitor(str);
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

    public final Bundle setState(String str, int i) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setState : ", str, "UcmService");
        this.mSecurityHelper.checkCallerPermissionFor("setState");
        Bundle bundle = new Bundle();
        if (str == null) {
            Log.i("UcmService", "uri is empty");
            bundle.putInt("errorresponse", 16);
            return bundle;
        }
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle state = iUcmAgentService != null ? iUcmAgentService.setState(i) : null;
        if (state != null) {
            return state;
        }
        Log.i("UcmService", "ERROR: Null Response received from agent");
        Bundle bundle2 = new Bundle();
        bundle2.putInt("errorresponse", 13);
        return bundle2;
    }

    public final void showEnforcedLockTypeNotification(int i, String str) {
        Log.i("UcmService", "showEnforcedLockTypeNotification : " + i);
        getUCMMDMService().enforceSecurityPermission(new ContextInfo(Binder.getCallingUid()), null);
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        this.mNotificationManager = notificationManager;
        if (notificationManager == null) {
            Log.i("UcmService", "Failed to get NotificationManager");
            return;
        }
        notificationManager.createNotificationChannel(
                new NotificationChannel(
                        "UCM_KEYGUARD_NOTIFICATION", "UCM Keyguard Configuration Notification", 4));
        try {
            this.mContext.unregisterReceiver(this.mOnNotiRemoveBroadcastReceiver);
        } catch (Exception unused) {
        }
        this.mContext.registerReceiverAsUser(
                this.mOnNotiRemoveBroadcastReceiver,
                UserHandle.ALL,
                new IntentFilter(
                        "com.samsung.android.knox.intent.action.ACTION_REMOVE_NOTIFICATION"),
                null,
                null,
                2);
        UserHandle userHandle = new UserHandle(i);
        Log.i("UcmService", "enforceLockType called for userID : " + i);
        Intent intent = new Intent();
        intent.setClassName(
                KnoxCustomManagerService.SETTING_PKG_NAME,
                "com.samsung.android.settings.knox.ConfirmUCMLockPassword");
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(" csName : ", str, "UcmService");
        intent.putExtra("lockscreen.ucscredentialstoragename", str);
        intent.addFlags(268435456);
        intent.addFlags(4194304);
        intent.addFlags(8388608);
        this.mContext.startActivityAsUser(intent, userHandle);
        showEnforcedLockTypeNotificationIntenal(i, str);
    }

    public final void showEnforcedLockTypeNotificationIntenal(int i, String str) {
        int lastIndexOf;
        try {
            Intent intent =
                    new Intent("com.samsung.android.knox.intent.action.ACTION_ENFORCE_LOCKTYPE");
            intent.putExtra("CS_NAME", str);
            intent.putExtra("USER_ID", i);
            PendingIntent broadcast =
                    PendingIntent.getBroadcast(this.mContext, i, intent, 201326592);
            String str2 = "";
            if (str != null && !str.equals("") && (lastIndexOf = str.lastIndexOf(":")) != -1) {
                str2 = str.substring(lastIndexOf + 1, str.length());
            }
            Intent intent2 =
                    new Intent("com.samsung.android.knox.intent.action.ACTION_REMOVE_NOTIFICATION");
            intent2.putExtra("CS_NAME", str);
            intent2.putExtra("USER_ID", i);
            this.mNotificationManager.notify(
                    i + 8000,
                    new Notification.Builder(this.mContext, "UCM_KEYGUARD_NOTIFICATION")
                            .setContentIntent(broadcast)
                            .setDeleteIntent(
                                    PendingIntent.getBroadcast(
                                            this.mContext, i, intent2, 201326592))
                            .setSmallIcon(R.drawable.ic_dialog_alert)
                            .setContentTitle(str2)
                            .setContentText(this.mContext.getString(17043271))
                            .setOngoing(true)
                            .build());
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
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
            NotificationManager notificationManager =
                    (NotificationManager) this.mContext.getSystemService("notification");
            if (notificationManager == null) {
                Log.i("UcmService", "Failed to get Notification Manager");
                return;
            }
            notificationManager.createNotificationChannel(
                    new NotificationChannel(
                            "UCM_ODE_NOTIFICATION", "UCM ODE Progress Notification", 4));
            Notification.Builder contentTitle =
                    new Notification.Builder(this.mContext, "UCM_ODE_NOTIFICATION")
                            .setContentTitle(getODEVendorName());
            Log.i("UcmService", "ODE Progress is done");
            notificationManager.notify(
                    9000,
                    contentTitle
                            .setSmallIcon(R.drawable.ic_dialog_info)
                            .setContentText(this.mContext.getString(17043270))
                            .build());
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService");
        }
    }

    public final ucmRetParcelable sign(String str, byte[] bArr, String str2) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("sign ", str, "UcmService");
        this.mSecurityHelper.getClass();
        Bundle bundle = new Bundle();
        if (bArr == null) {
            bundle.putInt("errorresponse", 4);
            return getResponseParcel(bundle);
        }
        UcmAgentWrapper activeAgent = getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "sign : NULL agent for uri ", str, "UcmService");
            bundle.putByteArray("bytearrayresponse", null);
            bundle.putInt("errorresponse", 14);
            return getResponseParcel(bundle);
        }
        UniversalCredentialUtil.UcmUri ucmUri = new UniversalCredentialUtil.UcmUri(str);
        this.mSecurityHelper.getClass();
        int callingUid = UcmSecurityHelper.getCallingUid(ucmUri);
        int userId = UserHandle.getUserId(callingUid);
        if (callingUid == 1016 && UcmServiceUtil.isOrganizationOwnedProfile(this.mContext)) {
            userId = UcmServiceUtil.getOrganizationOwnedProfileUserId();
        }
        int i = userId;
        int resourceId = ucmUri.getResourceId();
        if (resourceId == -1) {
            resourceId = 1;
        }
        int i2 = resourceId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mPolicyManager.isSEStorageAccessAllowed(
                            getActiveAgent(ucmUri.getSource()),
                            i,
                            callingUid,
                            false,
                            ucmUri.getRawAlias())
                    == 0) {
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
                UcmSignHelperFactory ucmSignHelperFactory = this.mSignHelperFactory;
                boolean z = activeAgent.info.supportSign;
                ucmSignHelperFactory.getClass();
                UcmSignHelper ucmSignHelperFactory2 = UcmSignHelperFactory.getInstance(str2, z);
                boolean isEncryptFunction = ucmSignHelperFactory2.isEncryptFunction();
                String processAlgorithm = ucmSignHelperFactory2.getProcessAlgorithm();
                byte[] processInput = ucmSignHelperFactory2.processInput(bArr);
                if (processInput == null) {
                    Log.e("UcmService", "signHelper.processInput fail.");
                    return getResponseParcel(4);
                }
                Bundle bundle2 = new Bundle();
                bundle2.putInt("callerUid", callingUid);
                bundle2.putInt("user_id", i);
                bundle2.putInt("ownerUid", callingUid);
                bundle2.putInt("resource", i2);
                Log.i("UcmService", "sign KEY_RESOURCE_ID= " + bundle2.getInt("resource", -2));
                Log.i("UcmService", "sign KEY_USER_ID= " + bundle2.getInt("user_id", -2));
                Log.i("UcmService", "sign KEY_CALLER_UID= " + bundle2.getInt("callerUid", -2));
                String rawAlias = UniversalCredentialUtil.getRawAlias(str);
                IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
                Bundle sign =
                        iUcmAgentService != null
                                ? iUcmAgentService.sign(
                                        rawAlias,
                                        processInput,
                                        processAlgorithm,
                                        isEncryptFunction,
                                        bundle2)
                                : null;
                if (sign == null) {
                    Log.i("UcmService", "ERROR: Null Response received from agent");
                    Bundle bundle3 = new Bundle();
                    bundle3.putByteArray("bytearrayresponse", null);
                    bundle3.putInt("errorresponse", 14);
                    return getResponseParcel(bundle3);
                }
                byte[] byteArray = sign.getByteArray("bytearrayresponse");
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        sign.getInt("errorresponse"),
                        "sign Response from plugin:  error code = ",
                        "UcmService");
                if (byteArray != null) {
                    return getResponseParcel(sign);
                }
                Log.i("UcmService", "ERROR: Empty data received for sign");
                sign.putByteArray("bytearrayresponse", null);
                sign.putInt("errorresponse", 13);
                return getResponseParcel(sign);
            } catch (NoSuchAlgorithmException e) {
                Log.i("UcmService", "The exception occurs " + e.getMessage());
                return getResponseParcel(2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void systemReady() {
        String packageName;
        Log.i("UcmService", "systemReady is called...");
        UcmServiceAgentManager ucmServiceAgentManager = this.mUcmServiceAgentManager;
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        ucmServiceAgentManager.getClass();
        if (edmStorageProvider != null) {
            ArrayList adminUidList = edmStorageProvider.getAdminUidList();
            if (!adminUidList.isEmpty()) {
                Iterator it = adminUidList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Integer num = (Integer) it.next();
                    if (num != null) {
                        int intValue = num.intValue();
                        Log.i("UcmService.UcmAgentManager", "check eSE Permission : " + intValue);
                        ComponentName componentNameForUid =
                                edmStorageProvider.getComponentNameForUid(intValue);
                        if (componentNameForUid != null
                                && (packageName = componentNameForUid.getPackageName()) != null
                                && UcmServiceAgentManager.checkESEPermission(
                                        UserHandle.getUserId(intValue), packageName)) {
                            Log.i("UcmService.UcmAgentManager", "ESE Permission exist");
                            ucmServiceAgentManager.mNeedToBindESE = true;
                            break;
                        }
                    }
                }
            }
        }
        sendEmptyMessage(1);
        if (this.mIsFbeEnabled) {
            return;
        }
        showODEProgressNotification();
    }

    public final ucmRetParcelable unwrapDek(String str, byte[] bArr) {
        return unwrapDek(str, bArr, null);
    }

    public final ucmRetParcelable unwrapDek(
            String str, byte[] bArr, UcmAgentWrapper ucmAgentWrapper) {
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
                ucmAgentWrapper =
                        true == "boot_test".equals(str)
                                ? getActiveAgent(
                                        "com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                                : getActiveAgent(UniversalCredentialUtil.getSource(str));
            }
            if (ucmAgentWrapper == null) {
                Log.i(
                        "UcmService",
                        "no agent found for Source ="
                            + " com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            if (!ucmAgentWrapper.isServiceBound()) {
                Log.i("UcmService", "agent is not bound");
                bundle.putInt("errorresponse", 14);
                return getResponseParcel(bundle);
            }
            IUcmAgentService iUcmAgentService = ucmAgentWrapper.mUcmAgentService;
            Bundle unwrapDek = iUcmAgentService != null ? iUcmAgentService.unwrapDek(bArr) : null;
            if (unwrapDek == null) {
                Log.i("UcmService", "ERROR: Null Response received from agent");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("errorresponse", 13);
                return getResponseParcel(bundle2);
            }
            Log.i(
                    "UcmService",
                    "unwrapDek Response from plugin:  error code = "
                            + unwrapDek.getInt("errorresponse"));
            return getResponseParcel(unwrapDek);
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception"), "UcmService");
            bundle.putInt("errorresponse", 15);
            return getResponseParcel(bundle);
        }
    }

    public final void updateAgentList() {
        this.mSecurityHelper.getClass();
        int callingUid = Binder.getCallingUid();
        Log.i("UcmService", "updateAgentList : " + callingUid);
        Message message = new Message();
        message.what = 1;
        message.arg1 = callingUid;
        sendMessage(message);
    }

    public final void updateKeyguardConfig(int i) {
        Log.i("UcmService", "updateKeyguardConfig");
        try {
            String keyguardStorageForCurrentUser = getKeyguardStorageForCurrentUser(i);
            if (keyguardStorageForCurrentUser != null) {
                if (keyguardStorageForCurrentUser.equals("none")) {}
            }
            Log.i("UcmService", "remove useless keyguard config file");
            File file = new File("/efs/sec_efs", "keyguardConfig");
            if (file.exists()) {
                Log.i("UcmService", "keyguard config file exist so delete it");
                file.delete();
            } else {
                Log.i("UcmService", "keyguard config file does not exist");
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception"), "UcmService");
        }
    }

    public final void updateSystemUIMonitor(String str) {
        ICredentialManagerServiceSystemUICallback iCredentialManagerServiceSystemUICallback =
                this.mSystemUICallback;
        if (iCredentialManagerServiceSystemUICallback != null) {
            try {
                iCredentialManagerServiceSystemUICallback.setUCMKeyguardVendor(str);
            } catch (Exception e) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), "UcmService");
            }
        }
    }

    public final Bundle verifyPin(int i, String str, String str2, Bundle bundle) {
        Log.i("UcmService", "verifyPin : " + str + ", userId-" + i);
        if (this.mSecurityHelper.isCallerSystemUI() || this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.getClass();
        } else {
            this.mSecurityHelper.checkCallerPermissionFor("verifyPin");
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
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle2.putInt("errorresponse", 14);
            return bundle2;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle verifyPin =
                iUcmAgentService != null ? iUcmAgentService.verifyPin(i, str2, bundle) : null;
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
                sendStateChangeBroadcast(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        displayToastFromAgentResponse(this.mContext, verifyPin);
        return verifyPin;
    }

    public final Bundle verifyPuk(String str, String str2, String str3) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "verifyPuk : ", str, "UcmService");
        if (this.mSecurityHelper.isCallerSystemUI() || this.mSecurityHelper.isSystemCaller()) {
            this.mSecurityHelper.getClass();
        } else {
            this.mSecurityHelper.checkCallerPermissionFor("verifyPuk");
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
        UcmAgentWrapper activeAgent =
                true == "boot_test".equals(str)
                        ? getActiveAgent("com.samsung.ucs.agent.boot:com.samsung.ucs.agent.boot")
                        : getActiveAgent(UniversalCredentialUtil.getSource(str));
        if (activeAgent == null) {
            Log.i(
                    "UcmService",
                    "no agent found for Source = " + UniversalCredentialUtil.getSource(str));
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        if (!activeAgent.isServiceBound()) {
            Log.i("UcmService", "agent is not bound");
            bundle.putInt("errorresponse", 14);
            return bundle;
        }
        IUcmAgentService iUcmAgentService = activeAgent.mUcmAgentService;
        Bundle verifyPuk = iUcmAgentService != null ? iUcmAgentService.verifyPuk(str2, str3) : null;
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
}
