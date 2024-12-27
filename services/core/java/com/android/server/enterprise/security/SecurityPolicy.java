package com.android.server.enterprise.security;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManagerNative;
import android.app.IUserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.AndroidKeyStoreMaintenance;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.MasterClearReceiver;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.EncryptionManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.input.KeyboardMetricsCollector;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.localservice.SecurityPolicyInternal;
import com.samsung.android.security.IDirEncryptService;
import com.samsung.android.security.SemSdCardEncryption;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class SecurityPolicy extends ISecurityPolicy.Stub
        implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static Map mBannerMap;
    public FactoryWipeReceiver factoryReceiver;
    public final AnonymousClass1 mBlocker;
    public boolean mBootCompleted;
    public final AnonymousClass3 mBootReceiver;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final SemEmergencyManager mEmergencyMgr;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final Injector mInjector;
    public KeyCodeMediatorImpl mKeyCodeMediator;
    public boolean mMediaFormatRet;
    public final HashMap mPendingGetCerificates;
    public IStatusBarService mStatusBarService;
    public final IBinder mToken;

    public final class FactoryWipeReceiver extends MasterClearReceiver {
        public FactoryWipeReceiver() {}

        @Override // com.android.server.MasterClearReceiver, android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            try {
                super.onReceive(context, intent);
                ResponseHandler responseHandler = SecurityPolicy.this.new ResponseHandler();
                responseHandler.sendMessage(responseHandler.obtainMessage(1, Boolean.TRUE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    public final class LocalService extends SecurityPolicyInternal {
        public LocalService() {}

        public final boolean isDodBannerVisibleAsUser(int i) {
            return SecurityPolicy.this.isDodBannerVisibleAsUser(i);
        }
    }

    public final class ResetKeyChain extends AsyncTask {
        public ResetKeyChain() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Boolean bool;
            int intValue = ((Integer[]) objArr)[0].intValue();
            try {
                KeyChain.KeyChainConnection bindAsUser =
                        KeyChain.bindAsUser(SecurityPolicy.this.mContext, new UserHandle(intValue));
                try {
                    try {
                        bool = Boolean.valueOf(bindAsUser.getService().reset());
                    } catch (RemoteException unused) {
                        bool = Boolean.FALSE;
                    }
                    bindAsUser.close();
                    return bool;
                } catch (Throwable th) {
                    bindAsUser.close();
                    throw th;
                }
            } catch (AssertionError unused2) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                        intValue,
                        "ResetKeyChain - is KeyChainService running for user ",
                        "?",
                        "SecurityPolicy");
                return Boolean.FALSE;
            } catch (InterruptedException unused3) {
                return Boolean.FALSE;
            }
        }
    }

    public final class ResponseHandler extends Handler {
        public ResponseHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        new StringBuilder("unknown msg type "), message.what, "SecurityPolicy");
                return;
            }
            if (!((Boolean) message.obj).booleanValue()) {
                Log.d("SecurityPolicy", "message not send from Broadcast Receiver ");
                return;
            }
            Map map = SecurityPolicy.mBannerMap;
            SecurityPolicy securityPolicy = SecurityPolicy.this;
            if (securityPolicy.factoryReceiver == null) {
                securityPolicy.factoryReceiver = securityPolicy.new FactoryWipeReceiver();
            }
            FactoryWipeReceiver factoryWipeReceiver = securityPolicy.factoryReceiver;
            securityPolicy.factoryReceiver = factoryWipeReceiver;
            securityPolicy.mContext.unregisterReceiver(factoryWipeReceiver);
            Log.d("SecurityPolicy", "successful unregister of Broadcast Receiver ");
        }
    }

    public final class UserSwitchObserver extends IUserSwitchObserver.Stub {
        public final void onBeforeUserSwitching(int i) {}

        public final void onForegroundProfileSwitch(int i) {}

        public final void onLockedBootComplete(int i) {}

        public final void onUserSwitchComplete(int i) {}

        public final void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {}
    }

    /* renamed from: -$$Nest$msaveDeviceBootMode, reason: not valid java name */
    public static void m524$$Nest$msaveDeviceBootMode(SecurityPolicy securityPolicy, boolean z) {
        securityPolicy.getClass();
        try {
            securityPolicy.mEdmStorageProvider.putGenericValueAsUser(
                    0, "deviceBootMode", Integer.toString(z ? 1 : 0));
            Log.i(
                    "SecurityPolicy",
                    "Device safe mode saved in generic table : " + Integer.toString(z ? 1 : 0));
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.enterprise.security.SecurityPolicy$1] */
    public SecurityPolicy(Context context) {
        Injector injector = new Injector(context);
        this.mMediaFormatRet = false;
        ArrayList arrayList = new ArrayList();
        this.mStatusBarService = null;
        this.mToken = new Binder();
        this.mBlocker =
                new SemDesktopModeManager
                        .DesktopModeBlocker() { // from class:
                                                // com.android.server.enterprise.security.SecurityPolicy.1
                    public final String onBlocked() {
                        return SecurityPolicy.this.mContext.getString(
                                R.string.heavy_weight_switcher_text);
                    }
                };
        this.mEmergencyMgr = null;
        this.mEDM = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.security.SecurityPolicy.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        NetworkScoreService$$ExternalSyntheticOutline0.m(
                                intExtra, "action = ", action, ", userId = ", "SecurityPolicy");
                        try {
                            if (!action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                if (action.equals(
                                        "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {}
                            }
                            SecurityPolicy.this.mBootCompleted = true;
                            ((PersonaManagerAdapter)
                                            ((IPersonaManagerAdapter)
                                                    AdapterRegistry.mAdapterHandles.get(
                                                            IPersonaManagerAdapter.class)))
                                    .getClass();
                            if (SemPersonaManager.isKnoxId(intExtra)) {
                                return;
                            }
                            if (SecurityPolicy.this.isRebootBannerEnabled(0)) {
                                if (context2.getPackageManager().isSafeMode()) {
                                    Log.i(
                                            "SecurityPolicy",
                                            "Saving Device safe mode to true in generic table");
                                    SecurityPolicy.m524$$Nest$msaveDeviceBootMode(
                                            SecurityPolicy.this, true);
                                } else {
                                    String genericValueAsUser =
                                            SecurityPolicy.this.mEdmStorageProvider
                                                    .getGenericValueAsUser(0, "deviceBootMode");
                                    if (genericValueAsUser != null
                                            && genericValueAsUser.equals("1")) {
                                        Log.i(
                                                "SecurityPolicy",
                                                "Sending broadcast:"
                                                    + " com.samsung.android.knox.intent.action.LAST_BOOT_SAFE_MODE_INTERNAL");
                                        context2.sendBroadcast(
                                                new Intent(
                                                                "com.samsung.android.knox.intent.action.LAST_BOOT_SAFE_MODE_INTERNAL")
                                                        .addFlags(16777216));
                                        SecurityPolicy.m524$$Nest$msaveDeviceBootMode(
                                                SecurityPolicy.this, false);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        this.mPendingGetCerificates = new HashMap();
        this.mInjector = injector;
        Objects.requireNonNull(context);
        this.mContext = context;
        new Handler();
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        context.registerReceiverAsUser(
                broadcastReceiver,
                UserHandle.ALL,
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL",
                        "com.samsung.intent.action.EMERGENCY_STATE_CHANGED"),
                null,
                null,
                2);
        mBannerMap = new HashMap();
        arrayList.add("com.samsung.android.email.provider");
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
        Log.d("SecurityPolicy", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(
                    new SemDesktopModeManager
                            .DesktopModeListener() { // from class:
                                                     // com.android.server.enterprise.security.SecurityPolicy.2
                        public final void onDesktopModeStateChanged(
                                SemDesktopModeState semDesktopModeState) {
                            if (semDesktopModeState.state == 20
                                    && semDesktopModeState.enabled == 3) {
                                Log.d("SecurityPolicy", "listener - Dex Enabling");
                                if (SecurityPolicy.this.isDodBannerVisibleAsUser(0)) {
                                    SecurityPolicy.this.registerDexBlocker$3();
                                }
                            }
                        }
                    });
        }
        try {
            ActivityManagerNative.getDefault()
                    .registerUserSwitchObserver(new UserSwitchObserver(), "SecurityPolicy");
        } catch (RemoteException e) {
            Slog.w("SecurityPolicy", "Exception during register UserSwitchObserver ", e);
        }
        this.mEmergencyMgr = SemEmergencyManager.getInstance(this.mInjector.mContext);
        ((EnterpriseDeviceManagerServiceImpl)
                        ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance))
                .getClass();
        if (EnterpriseDeviceManagerServiceImpl.mIsFirmwareUpgrade && isRebootBannerEnabled(0)) {
            addBannerAppToBatteryOptimizationWhitelist(new ContextInfo(), true);
        }
        LocalServices.addService(SecurityPolicyInternal.class, new LocalService());
    }

    public static void addBannerAppToBatteryOptimizationWhitelist(
            ContextInfo contextInfo, boolean z) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (z) {
            applicationPolicy.addPackageToBatteryOptimizationWhiteList(
                    contextInfo,
                    new AppIdentity(
                            KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, (String) null));
        } else {
            applicationPolicy.removePackageFromBatteryOptimizationWhiteList(
                    contextInfo,
                    new AppIdentity(
                            KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, (String) null));
        }
    }

    public static String dumpAliases(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            sb.append("{");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static String getKeystoreString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("Default");
        }
        if ((i & 2) != 0) {
            if (sb.length() != 0) {
                sb.append("/");
            }
            sb.append("Wi-Fi");
        }
        if ((i & 4) != 0) {
            if (sb.length() != 0) {
                sb.append("/");
            }
            sb.append("VPN and Apps");
        }
        if (sb.length() == 0) {
            sb.append(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
        }
        return sb.toString();
    }

    public static String getValidStr$2(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception unused) {
            Log.w("SecurityPolicy", "is string valid?");
            return null;
        }
    }

    public static String retrieveCertificateAliasFromKeyChain(
            IKeyChainService iKeyChainService, Certificate certificate) {
        if (iKeyChainService != null) {
            try {
                return iKeyChainService.getCertificateAlias(
                        Credentials.convertToPem(new Certificate[] {certificate}));
            } catch (RemoteException e) {
                Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e.toString());
            } catch (IOException e2) {
                Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e2.toString());
            } catch (CertificateEncodingException e3) {
                Log.e("SecurityPolicy", "retrieveCertificateAliasFromKeyChain: " + e3.toString());
            }
        }
        return null;
    }

    public static boolean validateKeystoreParam(int i) {
        return (i & 7) != 0 && (i | 7) == 7;
    }

    public static boolean validatePackageName$1(String str) {
        if (TextUtils.isEmpty(str) || "*".equals(str)) {
            return false;
        }
        String[] split = str.split("\\.");
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '.') {
                i++;
            }
        }
        if (i >= split.length) {
            return false;
        }
        for (String str2 : split) {
            if (!str2.matches("^[A-Za-z0-9_]+$")
                    || str2.charAt(0) == '_'
                    || (str2.charAt(0) >= '0' && str2.charAt(0) <= '9')) {
                return false;
            }
        }
        return true;
    }

    public final boolean addPackagesToCertificateWhiteList(ContextInfo contextInfo, List list) {
        boolean z;
        boolean z2;
        ContextInfo enforceCaller =
                EnterpriseAccessController.enforceCaller(
                        contextInfo, "ADD_PACKAGE_CERT_WHITE_LIST");
        int userId = UserHandle.getUserId(enforceCaller.mCallerUid);
        Context createContextAsUser =
                Utils.createContextAsUser(this.mContext, "android", 0, userId);
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = list.iterator();
            String str = null;
            String str2 = null;
            boolean z3 = true;
            while (it.hasNext()) {
                AppIdentity appIdentity = (AppIdentity) it.next();
                if (appIdentity != null) {
                    str = appIdentity.getPackageName();
                    str2 = appIdentity.getSignature();
                }
                if (validatePackageName$1(str) && !isPackageAlreadyWhiteListed(userId, str)) {
                    if (str2 == null
                            || Utils.comparePackageSignature(0, createContextAsUser, str, str2)) {
                        z = false;
                        z2 = true;
                    } else {
                        if (createContextAsUser != null) {
                            if (createContextAsUser.getPackageManager() != null) {
                                createContextAsUser.getPackageManager().getPackageInfo(str, 1);
                                z = true;
                                z2 = false;
                            }
                        }
                        Log.d(
                                "SecurityPolicy",
                                "context or PackageManager is null : returning false");
                        z = false;
                        z2 = false;
                    }
                    if (z2 || !z) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("adminUid", Integer.valueOf(enforceCaller.mCallerUid));
                        contentValues.put("packageName", str);
                        contentValues.put("signature", str2);
                        z3 &=
                                this.mEdmStorageProvider.insert(
                                                "CertificateWhiteListTable", contentValues)
                                        > 0;
                    }
                }
                z3 = false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z3;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0162 A[Catch: all -> 0x011b, TRY_ENTER, TRY_LEAVE, TryCatch #20 {all -> 0x011b, blocks: (B:35:0x0112, B:38:0x0162, B:54:0x0195, B:51:0x0191, B:52:0x0194, B:57:0x0198, B:59:0x019c, B:60:0x01a7, B:62:0x01ab, B:63:0x01b5, B:56:0x0168, B:43:0x0173, B:48:0x017c), top: B:34:0x0112, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x019c A[Catch: all -> 0x011b, TryCatch #20 {all -> 0x011b, blocks: (B:35:0x0112, B:38:0x0162, B:54:0x0195, B:51:0x0191, B:52:0x0194, B:57:0x0198, B:59:0x019c, B:60:0x01a7, B:62:0x01ab, B:63:0x01b5, B:56:0x0168, B:43:0x0173, B:48:0x017c), top: B:34:0x0112, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ab A[Catch: all -> 0x011b, TryCatch #20 {all -> 0x011b, blocks: (B:35:0x0112, B:38:0x0162, B:54:0x0195, B:51:0x0191, B:52:0x0194, B:57:0x0198, B:59:0x019c, B:60:0x01a7, B:62:0x01ab, B:63:0x01b5, B:56:0x0168, B:43:0x0173, B:48:0x017c), top: B:34:0x0112, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean deleteCertificateFromKeystore(
            com.samsung.android.knox.ContextInfo r25,
            com.samsung.android.knox.keystore.CertificateInfo r26,
            int r27) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.security.SecurityPolicy.deleteCertificateFromKeystore(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.keystore.CertificateInfo, int):boolean");
    }

    public final boolean deleteCertificateFromUserKeystore(
            ContextInfo contextInfo, CertificateInfo certificateInfo, int i) {
        return false;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SecurityPolicy");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Context context = this.mContext;
        new ArrayList();
        new Random();
        UserManager userManager = (UserManager) context.getSystemService("user");
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List users = userManager.getUsers(true);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Iterator it = users.iterator();
            while (it.hasNext()) {
                arrayList.add(
                        Integer.valueOf(((UserInfo) it.next()).getUserHandle().getIdentifier()));
            }
            sb.append("[VPN and Apps keystore]" + System.lineSeparator());
            Iterator it2 = arrayList.iterator();
            while (true) {
                List list = null;
                if (!it2.hasNext()) {
                    break;
                }
                int intValue = ((Integer) it2.next()).intValue();
                sb.append("Aliases for user ");
                sb.append(intValue);
                sb.append(": ");
                CertificateUtil.KeyChainCRUD keyChainCRUD =
                        new CertificateUtil.KeyChainCRUD(this.mContext, intValue);
                String[] listAliases = keyChainCRUD.listAliases(-1, null);
                keyChainCRUD.disconnect();
                if (listAliases != null) {
                    list = Arrays.asList(listAliases);
                }
                sb.append(dumpAliases(list));
            }
            sb.append(System.lineSeparator());
            sb.append("[Wifi keystore]" + System.lineSeparator() + "Aliases: ");
            CertificateUtil.KeyChainCRUD keyChainCRUD2 =
                    new CertificateUtil.KeyChainCRUD(this.mContext, 0);
            String[] listAliases2 = keyChainCRUD2.listAliases(1010, null);
            keyChainCRUD2.disconnect();
            sb.append(dumpAliases(listAliases2 != null ? Arrays.asList(listAliases2) : null));
            sb.append(System.lineSeparator());
            sb.append("[Default keystore]" + System.lineSeparator());
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        int intValue2 = ((Integer) it3.next()).intValue();
                        IKeyChainService service =
                                KeyChain.bindAsUser(this.mContext, new UserHandle(intValue2))
                                        .getService();
                        if (service != null) {
                            try {
                                try {
                                    sb.append("Aliases for user ");
                                    sb.append(intValue2);
                                    sb.append(": ");
                                    sb.append(dumpAliases(service.userAliases()));
                                } catch (RemoteException e) {
                                    Log.e("SecurityPolicy", "Failed to dump Default keystore " + e);
                                }
                            } finally {
                            }
                        }
                    }
                } catch (AssertionError e2) {
                    Log.e("SecurityPolicy", "Failed to dump Default keystore " + e2);
                } catch (InterruptedException e3) {
                    Log.e("SecurityPolicy", "Failed to dump Default keystore " + e3);
                }
                sb.append(System.lineSeparator());
                printWriter.write(sb.toString());
                this.mEnterpriseDumpHelper.dumpTable(
                        printWriter,
                        "SECURITY",
                        new String[] {"deviceEnrolled", "bannerText"},
                        null);
                this.mEnterpriseDumpHelper.dumpTable(
                        printWriter,
                        "generic",
                        new String[] {"dodBannerVisible", "deviceLastAccessDate", "deviceBootMode"},
                        null);
            } finally {
            }
        } finally {
        }
    }

    public final boolean enableRebootBanner(ContextInfo contextInfo, boolean z) {
        boolean enableRebootBannerInternal = enableRebootBannerInternal(contextInfo, null, z);
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (enableRebootBannerInternal) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (z) {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has enabled reboot banner.",
                                    Integer.valueOf(contextInfo.mCallerUid)),
                            userId);
                } else {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has disabled reboot banner.",
                                    Integer.valueOf(contextInfo.mCallerUid)),
                            userId);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return enableRebootBannerInternal;
    }

    public final boolean enableRebootBannerInternal(
            ContextInfo contextInfo, String str, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndSecurityPermission$2 =
                enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        int i = enforceOwnerOnlyAndSecurityPermission$2.mCallerUid;
        if (!z) {
            str = null;
        }
        try {
            this.mEdmStorageProvider.putBoolean("SECURITY", i, z, 0, "deviceEnrolled");
            this.mEdmStorageProvider.putString(i, 0, "SECURITY", "bannerText", str);
            z2 = true;
        } catch (Exception unused) {
            z2 = false;
        }
        addBannerAppToBatteryOptimizationWhitelist(enforceOwnerOnlyAndSecurityPermission$2, z);
        return z2;
    }

    public final boolean enableRebootBannerWithText(
            ContextInfo contextInfo, boolean z, String str) {
        boolean enableRebootBannerInternal = enableRebootBannerInternal(contextInfo, str, z);
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (enableRebootBannerInternal) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (z) {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has enabled reboot banner with text %s",
                                    Integer.valueOf(contextInfo.mCallerUid), str),
                            userId);
                } else {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has disabled reboot banner.",
                                    Integer.valueOf(contextInfo.mCallerUid)),
                            userId);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return enableRebootBannerInternal;
    }

    public final ContextInfo enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission(
            ContextInfo contextInfo, int i, boolean z) {
        ContextInfo adminContextIfCallerInCertWhiteList =
                getEDM$29()
                        .getAdminContextIfCallerInCertWhiteList(
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_SECURITY")));
        if (adminContextIfCallerInCertWhiteList == null) {
            return EnterpriseAccessController.enforceCaller(
                    contextInfo,
                    (i & 2) != 0
                            ? z
                                    ? "INSTALL_CERT_TO_GLOBAL_SCOPE_KEYSTORE"
                                    : "DELETE_CERT_FROM_GLOBAL_SCOPE_KEYSTORE"
                            : z
                                    ? "INSTALL_CERT_TO_USER_SCOPE_KEYSTORE"
                                    : "DELETE_CERT_FROM_USER_SCOPE_KEYSTORE");
        }
        if ((i & 2) == 0
                || UserHandle.getUserId(adminContextIfCallerInCertWhiteList.mCallerUid) == 0) {
            return adminContextIfCallerInCertWhiteList;
        }
        throw new SecurityException("Operation supported only on owner space");
    }

    public final ContextInfo enforceCertificateProvisioningPermission(ContextInfo contextInfo) {
        return getEDM$29()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_SECURITY",
                                        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING")));
    }

    public final ContextInfo enforceOnlySecurityPermission$1(ContextInfo contextInfo) {
        return getEDM$29()
                .enforcePermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final ContextInfo enforceOwnerOnlyAndCertProvisioningPermission(
            ContextInfo contextInfo) {
        return getEDM$29()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING")));
    }

    public final ContextInfo enforceOwnerOnlyAndSecurityPermission$2(ContextInfo contextInfo) {
        return getEDM$29()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final void formatExternalStorageCard() {
        try {
            ((StorageManager) this.mContext.getSystemService("storage")).wipeAdoptableDisks();
            final Object obj = new Object();
            BroadcastReceiver broadcastReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.enterprise.security.SecurityPolicy.4
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                                synchronized (obj) {
                                    try {
                                        SecurityPolicy.this.mMediaFormatRet = true;
                                        obj.notify();
                                    } catch (IllegalMonitorStateException unused) {
                                        Log.w(
                                                "SecurityPolicy",
                                                "formatStorageCard - IllegalMonitorStateException");
                                    }
                                }
                            }
                        }
                    };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
            intentFilter.addDataScheme("file");
            this.mContext.registerReceiver(broadcastReceiver, intentFilter);
            synchronized (obj) {
                try {
                    obj.wait(7000L);
                } catch (InterruptedException unused) {
                    Log.w("SecurityPolicy", "formatStorageCard - InterruptedException");
                }
            }
            this.mContext.unregisterReceiver(broadcastReceiver);
        } catch (Exception unused2) {
            Log.w("SecurityPolicy", "formatStorageCard fail");
        }
    }

    public final String[] formatSelective(
            ContextInfo contextInfo, String[] strArr, String[] strArr2) {
        return null;
    }

    public final List getCertificatesFromKeystore(ContextInfo contextInfo, int i, int i2) {
        KeyChain.KeyChainConnection bindAsUser;
        IKeyChainService service;
        ContextInfo adminContextIfCallerInCertWhiteList =
                getEDM$29()
                        .getAdminContextIfCallerInCertWhiteList(
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_SECURITY")));
        if (adminContextIfCallerInCertWhiteList == null) {
            adminContextIfCallerInCertWhiteList =
                    (i & 2) != 0
                            ? enforceOwnerOnlyAndCertProvisioningPermission(contextInfo)
                            : enforceCertificateProvisioningPermission(contextInfo);
        } else if ((i & 2) != 0
                && UserHandle.getUserId(adminContextIfCallerInCertWhiteList.mCallerUid) != 0) {
            throw new SecurityException("Operation supported only on owner space");
        }
        int userId = UserHandle.getUserId(adminContextIfCallerInCertWhiteList.mCallerUid);
        ArrayList arrayList = new ArrayList();
        if (this.mPendingGetCerificates.containsKey(Integer.valueOf(i2))) {
            arrayList.addAll((Collection) this.mPendingGetCerificates.get(Integer.valueOf(i2)));
        } else {
            if (!validateKeystoreParam(i)) {
                return null;
            }
            if ((i & 1) != 0) {
                ArrayList arrayList2 = new ArrayList();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        try {
                            bindAsUser = KeyChain.bindAsUser(this.mContext, new UserHandle(userId));
                            service = bindAsUser.getService();
                        } catch (AssertionError unused) {
                            Log.e(
                                    "SecurityPolicy",
                                    "getAndroidInstalledCertificatesAsUser - is KeyChainService"
                                        + " running for user "
                                            + userId
                                            + "?");
                        }
                    } catch (InterruptedException e) {
                        Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e);
                    }
                    try {
                        if (service != null) {
                            try {
                                Iterator it = service.userAliases().iterator();
                                while (it.hasNext()) {
                                    byte[] certificateFromTrustCredential =
                                            service.getCertificateFromTrustCredential(
                                                    (String) it.next(), false);
                                    if (certificateFromTrustCredential != null) {
                                        try {
                                            for (X509Certificate x509Certificate :
                                                    Credentials.convertFromPem(
                                                            certificateFromTrustCredential)) {
                                                if (x509Certificate != null) {
                                                    CertificateInfo certificateInfo =
                                                            new CertificateInfo();
                                                    certificateInfo.setCertificate(x509Certificate);
                                                    certificateInfo.setKeystore(1);
                                                    certificateInfo.setSystemPreloaded(false);
                                                    arrayList2.add(certificateInfo);
                                                }
                                            }
                                        } catch (IOException e2) {
                                            Log.e(
                                                    "SecurityPolicy",
                                                    "getAndroidInstalledCertificates " + e2);
                                        } catch (CertificateException e3) {
                                            Log.e(
                                                    "SecurityPolicy",
                                                    "getAndroidInstalledCertificates " + e3);
                                        }
                                    }
                                }
                            } catch (RemoteException e4) {
                                Log.e("SecurityPolicy", "getAndroidInstalledCertificates " + e4);
                            }
                        }
                        arrayList.addAll(arrayList2);
                        arrayList.addAll(getSystemCertificatesAsUser(userId, true));
                    } finally {
                        bindAsUser.close();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if ((i & 2) != 0) {
                arrayList.addAll(getNativeInstalledCertificatesAsUser(2, 0, "USRCERT_"));
                arrayList.addAll(getNativeInstalledCertificatesAsUser(2, 0, "CACERT_"));
            }
            if ((i & 4) != 0) {
                arrayList.addAll(getNativeInstalledCertificatesAsUser(4, userId, "USRCERT_"));
                arrayList.addAll(getNativeInstalledCertificatesAsUser(4, userId, "CACERT_"));
            }
        }
        if (arrayList.size() >= CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS) {
            this.mPendingGetCerificates.put(
                    Integer.valueOf(i2),
                    arrayList.subList(
                            CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS, arrayList.size()));
            return arrayList.subList(0, CertificateProvisioning.MAXIMUM_CERTIFICATE_NUMBERS);
        }
        this.mPendingGetCerificates.remove(Integer.valueOf(i2));
        return arrayList.subList(0, arrayList.size());
    }

    public final List getCertificatesFromUserKeystore(ContextInfo contextInfo, int i) {
        return null;
    }

    public final String getDeviceLastAccessDate(ContextInfo contextInfo) {
        return this.mEdmStorageProvider.getGenericValueAsUser(
                Utils.getCallingOrCurrentUserId(contextInfo), "deviceLastAccessDate");
    }

    public final EnterpriseDeviceManager getEDM$29() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return this.mEDM;
    }

    public final List getNativeInstalledCertificatesAsUser(int i, int i2, String str) {
        CertificateUtil.KeyChainCRUD keyChainCRUD;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i3 = i == 4 ? -1 : 1010;
        CertificateUtil.KeyChainCRUD keyChainCRUD2 = null;
        try {
            keyChainCRUD = new CertificateUtil.KeyChainCRUD(this.mContext, i2);
        } catch (Throwable th) {
            th = th;
        }
        try {
            String[] listAliases = keyChainCRUD.listAliases(i3, str);
            if (listAliases == null) {
                List list = Collections.EMPTY_LIST;
                keyChainCRUD.disconnect();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return list;
            }
            ArrayList arrayList = new ArrayList();
            for (String str2 : listAliases) {
                byte[] bArr = keyChainCRUD.get(i3, str2, str);
                if (bArr != null) {
                    for (X509Certificate x509Certificate : CertificateUtil.toCertificates(bArr)) {
                        if (x509Certificate != null) {
                            CertificateInfo certificateInfo = new CertificateInfo();
                            certificateInfo.setCertificate(x509Certificate);
                            certificateInfo.setKeystore(i);
                            certificateInfo.setAlias(str2);
                            if (str.equals("USRCERT_")) {
                                certificateInfo.setHasPrivateKey(true);
                            }
                            arrayList.add(certificateInfo);
                        }
                    }
                }
            }
            keyChainCRUD.disconnect();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            keyChainCRUD2 = keyChainCRUD;
            if (keyChainCRUD2 != null) {
                keyChainCRUD2.disconnect();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final List getPackagesFromCertificateWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceCertificateProvisioningPermission =
                enforceCertificateProvisioningPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminUid", Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
        List values =
                this.mEdmStorageProvider.getValues(
                        "CertificateWhiteListTable",
                        new String[] {"packageName", "signature"},
                        contentValues);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) values).iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            arrayList.add(
                    new AppIdentity(
                            contentValues2.getAsString("packageName"),
                            contentValues2.getAsString("signature")));
        }
        return arrayList;
    }

    public final String getRebootBannerText(ContextInfo contextInfo) {
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getStringListAsUser(
                                        Utils.getCallingOrCurrentUserId(contextInfo),
                                        "SECURITY",
                                        "bannerText"))
                        .iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null) {
                return str;
            }
        }
        return null;
    }

    public final boolean getRequireDeviceEncryption(
            ContextInfo contextInfo, ComponentName componentName) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        try {
            return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                    .getStorageEncryption(componentName);
        } catch (Exception e) {
            Log.w("SecurityPolicy", "getRequireDeviceEncryption Ex" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public final boolean getRequireStorageCardEncryption(
            ContextInfo contextInfo, ComponentName componentName) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .semGetRequireStorageCardEncryption(componentName, contextInfo.mParent);
            } catch (Exception e) {
                Log.w("SecurityPolicy", "getRequireStorageCardEncryption Ex" + e.getMessage());
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final Set getRestrictedKeyCodes() {
        if (isDodBannerVisibleAsUser(0)) {
            return new HashSet(
                    Arrays.asList(
                            3,
                            1001,
                            Integer.valueOf(
                                    FrameworkStatsLog
                                            .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED)));
        }
        return null;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final String getServiceName() {
        return "SecurityPolicy";
    }

    public final List getSystemCertificates(ContextInfo contextInfo) {
        return getSystemCertificatesAsUser(
                UserHandle.getUserId(
                        enforceCertificateProvisioningPermission(contextInfo).mCallerUid),
                false);
    }

    public final List getSystemCertificatesAsUser(int i, boolean z) {
        KeyChain.KeyChainConnection bindAsUser;
        IKeyChainService service;
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                bindAsUser = KeyChain.bindAsUser(this.mContext, new UserHandle(i));
                service = bindAsUser.getService();
            } catch (AssertionError unused) {
                Log.e(
                        "SecurityPolicy",
                        "getSystemCertificatesAsUser - is KeyChainService running for user "
                                + i
                                + "?");
            } catch (InterruptedException e) {
                Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e);
            }
            try {
                if (service != null) {
                    try {
                        for (String str : service.allSystemAliases()) {
                            CertificateInfo certificateInfo = new CertificateInfo();
                            byte[] certificateFromTrustCredential =
                                    service.getCertificateFromTrustCredential(str, z);
                            if (certificateFromTrustCredential != null) {
                                try {
                                    for (X509Certificate x509Certificate :
                                            Credentials.convertFromPem(
                                                    certificateFromTrustCredential)) {
                                        if (x509Certificate != null) {
                                            certificateInfo.setCertificate(x509Certificate);
                                            certificateInfo.setKeystore(1);
                                            certificateInfo.setSystemPreloaded(true);
                                            certificateInfo.setEnabled(service.containsAlias(str));
                                            arrayList.add(certificateInfo);
                                        }
                                    }
                                } catch (IOException e2) {
                                    Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e2);
                                } catch (CertificateException e3) {
                                    Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e3);
                                }
                            }
                        }
                    } catch (RemoteException e4) {
                        Log.e("SecurityPolicy", "getSystemCertificatesAsUser " + e4);
                    }
                }
                return arrayList;
            } finally {
                bindAsUser.close();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int installCertificateToKeystore(
            ContextInfo contextInfo,
            String str,
            byte[] bArr,
            String str2,
            String str3,
            int i,
            boolean z) {
        int i2;
        int i3;
        ContextInfo enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission =
                enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission(
                        contextInfo, i, true);
        int userId =
                UserHandle.getUserId(
                        enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                .mCallerUid);
        String validStr$2 = getValidStr$2(str);
        String validStr$22 = getValidStr$2(str2);
        String trim = str3 != null ? str3.trim() : str3;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (validateKeystoreParam(i)) {
                if (!z) {
                    if (validStr$2 != null) {}
                }
                if (bArr != null && bArr.length != 0 && validStr$22 != null) {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has requested to install a certificate. Keystore(s) :"
                                        + " %s, Name : %s",
                                    Integer.valueOf(
                                            enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                                    .mCallerUid),
                                    getKeystoreString(i),
                                    validStr$22),
                            userId);
                    i2 = userId;
                    i3 =
                            new CertificateUtil(this.mContext)
                                    .installAsUser(validStr$2, bArr, validStr$22, trim, i, userId);
                    new CertificateUtil(this.mContext)
                            .sendIntentToSettings(i2, this.mBootCompleted);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (!z && i3 == 0) {
                        KnoxAnalyticsData knoxAnalyticsData =
                                new KnoxAnalyticsData(
                                        "KNOX_AKS", 1, "API:installCertificateToKeystore");
                        knoxAnalyticsData.setProperty(
                                "cId",
                                enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                        .mCallerUid);
                        knoxAnalyticsData.setProperty("uId", i2);
                        knoxAnalyticsData.setProperty(
                                "pN",
                                this.mContext
                                        .getPackageManager()
                                        .getNameForUid(
                                                enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                                        .mCallerUid));
                        knoxAnalyticsData.setProperty("key", getKeystoreString(i));
                        KnoxAnalytics.log(knoxAnalyticsData);
                    }
                    return i3;
                }
            }
            i2 = userId;
            Log.d("SecurityPolicy", "installCertificateToKeystore: Invalid parameter(s)");
            i3 = -1;
            new CertificateUtil(this.mContext).sendIntentToSettings(i2, this.mBootCompleted);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!z) {
                KnoxAnalyticsData knoxAnalyticsData2 =
                        new KnoxAnalyticsData("KNOX_AKS", 1, "API:installCertificateToKeystore");
                knoxAnalyticsData2.setProperty(
                        "cId",
                        enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                .mCallerUid);
                knoxAnalyticsData2.setProperty("uId", i2);
                knoxAnalyticsData2.setProperty(
                        "pN",
                        this.mContext
                                .getPackageManager()
                                .getNameForUid(
                                        enforceAdminPermissionIfCallerInCertWhiteListOrDangerousPermission
                                                .mCallerUid));
                knoxAnalyticsData2.setProperty("key", getKeystoreString(i));
                KnoxAnalytics.log(knoxAnalyticsData2);
            }
            return i3;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean installCertificateToUserKeystore(
            ContextInfo contextInfo, String str, byte[] bArr, String str2, String str3, int i) {
        return false;
    }

    public final void installCertificateWithType(ContextInfo contextInfo, String str, byte[] bArr) {
        enforceOwnerOnlyAndCertProvisioningPermission(contextInfo);
        String validStr$2 = getValidStr$2(str);
        if (validStr$2 == null || bArr == null || bArr.length <= 0) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("android.credentials.INSTALL");
                intent.addFlags(268435456);
                intent.putExtra("senderpackagename", "SecurityPolicy");
                intent.putExtra(validStr$2, bArr);
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("SecurityPolicy", "::installCertificateWithType() : " + e.toString());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void installCertificatesFromSdCard(ContextInfo contextInfo) {
        enforceOwnerOnlyAndCertProvisioningPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("android.credentials.INSTALL");
                intent.addFlags(268435456);
                intent.putExtra("senderpackagename", "SecurityPolicy");
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("SecurityPolicy", "::installCertificatesFromSdCard() : " + e.toString());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isDodBannerVisible(ContextInfo contextInfo) {
        return isDodBannerVisibleAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isDodBannerVisibleAsUser(int i) {
        String str;
        try {
            str = this.mEdmStorageProvider.getGenericValueAsUser(i, "dodBannerVisible");
        } catch (Exception unused) {
            Log.i(
                    "SecurityPolicy",
                    "isDodBannerVisibleAsUser facing exception, return default value");
            str = null;
        }
        return str != null && str.equals("1");
    }

    public final boolean isExternalStorageEncrypted(ContextInfo contextInfo) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            EncryptionManagerAdapter.getInstance(this.mContext).getClass();
            if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
                try {
                    IDirEncryptService asInterface =
                            IDirEncryptService.Stub.asInterface(
                                    ServiceManager.getService("DirEncryptService"));
                    if (asInterface != null) {
                        z = asInterface.isSdCardEncryped();
                    }
                } catch (Exception unused) {
                }
            }
            return z;
        } catch (Exception unused2) {
            Log.w("SecurityPolicy", "is External Storage Encrypted ?");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isInternalStorageEncrypted(ContextInfo contextInfo) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int storageEncryptionStatus =
                    ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                            .getStorageEncryptionStatus();
            if (storageEncryptionStatus != 3 && storageEncryptionStatus != 5) {
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Exception unused) {
            Log.w("SecurityPolicy", "is Internal Storage Encrypted ?");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isInternalStorageEncryptedbyDefaultKey(ContextInfo contextInfo) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                            .getStorageEncryptionStatus()
                    == 4;
        } catch (Exception unused) {
            Log.w("SecurityPolicy", "is Internal Storage Encrypted by Default key?");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final boolean isKeyCodeInputAllowed(int i) {
        if (i == 3 || i == 187 || i == 1001) {
            return !isDodBannerVisibleAsUser(0);
        }
        return true;
    }

    public final boolean isPackageAlreadyWhiteListed(int i, String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("packageName", str);
        m.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "CertificateWhiteListTable", new String[] {"adminUid"}, m);
        return (arrayList.size() > 0
                        ? ((ContentValues) arrayList.get(0)).getAsInteger("adminUid").intValue()
                        : -1)
                != -1;
    }

    public final boolean isRebootBannerEnabled(int i) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(i, "SECURITY", "deviceEnrolled")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final boolean isRebootBannerEnabled(ContextInfo contextInfo) {
        return isRebootBannerEnabled(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    public final void onKeyguardLaunched() {
        enforceOnlySecurityPermission$1(new ContextInfo(Binder.getCallingUid()));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isRebootBannerEnabled(0)) {
                    startBannerService();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void registerDexBlocker$3() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((SemDesktopModeManager)
                            this.mContext.getApplicationContext().getSystemService("desktopmode"))
                    .registerBlocker(this.mBlocker);
            Log.d("SecurityPolicy", "DexBlocker was registered");
        } catch (Exception unused) {
            Log.d("SecurityPolicy", "DexBlocker was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean removeAccountsByType(ContextInfo contextInfo, String str) {
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$29()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_SECURITY")));
        boolean z = false;
        if (str == null) {
            Log.i("SecurityPolicy", "removeAccountsByType() failed - type is invalid");
            return false;
        }
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceActiveAdminPermissionByContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                AccountManager accountManager = AccountManager.get(this.mContext);
                Account[] accountsByTypeAsUser =
                        accountManager.getAccountsByTypeAsUser(
                                str, new UserHandle(callingOrCurrentUserId));
                if (accountsByTypeAsUser == null || accountsByTypeAsUser.length <= 0) {
                    Log.i(
                            "SecurityPolicy",
                            "removeAccountsByType() : there is no account for type - ".concat(str));
                } else {
                    for (Account account : accountsByTypeAsUser) {
                        Log.i("SecurityPolicy", "removeAccountsByType() account = " + account.name);
                        accountManager.removeAccountAsUser(
                                account, null, null, new UserHandle(callingOrCurrentUserId));
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = true;
            } catch (Exception e) {
                Log.e("SecurityPolicy", "removeAccountsByType() : failed. error occurs.", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            if (!z) {
                Log.e("SecurityPolicy", "removeAccountsByType() : has failed. type - ".concat(str));
            }
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removePackagesFromCertificateWhiteList(
            ContextInfo contextInfo, List list) {
        ContextInfo enforceCertificateProvisioningPermission =
                enforceCertificateProvisioningPermission(contextInfo);
        if (list == null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    "adminUid",
                    Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
            return this.mEdmStorageProvider.delete("CertificateWhiteListTable", contentValues) > 0;
        }
        if (list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        boolean z = true;
        while (it.hasNext()) {
            AppIdentity appIdentity = (AppIdentity) it.next();
            if (appIdentity == null) {
                z = false;
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(
                        "adminUid",
                        Integer.valueOf(enforceCertificateProvisioningPermission.mCallerUid));
                contentValues2.put("packageName", appIdentity.getPackageName());
                if (appIdentity.getSignature() != null) {
                    contentValues2.put("signature", appIdentity.getSignature());
                }
                z &=
                        this.mEdmStorageProvider.delete("CertificateWhiteListTable", contentValues2)
                                > 0;
            }
        }
        return z;
    }

    public final boolean resetCredentialStorage(ContextInfo contextInfo) {
        ContextInfo enforceCaller =
                EnterpriseAccessController.enforceCaller(contextInfo, "RESET_CREDENTIAL_STORAGE");
        int userId = UserHandle.getUserId(enforceCaller.mCallerUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "SecurityPolicy",
                    String.format(
                            "Admin %d has requested to clear credential storages",
                            Integer.valueOf(enforceCaller.mCallerUid)),
                    userId);
            boolean z =
                    AndroidKeyStoreMaintenance.clearNamespace(
                                    0, (long) UserHandle.getUid(userId, 1000))
                            == 0;
            if (userId == 0) {
                z &= AndroidKeyStoreMaintenance.clearNamespace(2, 102L) == 0;
            }
            try {
                z &=
                        ((Boolean)
                                        new ResetKeyChain()
                                                .execute(Integer.valueOf(userId))
                                                .get(3000L, TimeUnit.MILLISECONDS))
                                .booleanValue();
                new CertificateUtil(this.mContext)
                        .sendIntentToSettings(userId, this.mBootCompleted);
            } catch (Exception unused) {
                Log.e("SecurityPolicy", "resetCredentialStorage EX: ");
            }
            if (z) {
                KnoxAnalyticsData knoxAnalyticsData =
                        new KnoxAnalyticsData("KNOX_AKS", 1, "API:resetCredentialStorage");
                knoxAnalyticsData.setProperty("cId", enforceCaller.mCallerUid);
                knoxAnalyticsData.setProperty("uId", userId);
                knoxAnalyticsData.setProperty(
                        "pN",
                        this.mContext.getPackageManager().getNameForUid(enforceCaller.mCallerUid));
                KnoxAnalytics.log(knoxAnalyticsData);
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean retrieveAliasAndDeleteCertificate(
            Certificate certificate, String str, int i, int i2) {
        CertificateUtil.KeyChainCRUD keyChainCRUD;
        String retrieveAliasToBeDeleted =
                retrieveAliasToBeDeleted(certificate, str, "CACERT_", i, i2);
        if (retrieveAliasToBeDeleted == null) {
            retrieveAliasToBeDeleted =
                    retrieveAliasToBeDeleted(certificate, str, "USRCERT_", i, i2);
        }
        if (retrieveAliasToBeDeleted == null) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i,
                    i2,
                    "deleteCertificateFromNativeKeystoreAsUser: alias is null for keystore = ",
                    ", userId = ",
                    "SecurityPolicy");
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        CertificateUtil.KeyChainCRUD keyChainCRUD2 = null;
        try {
            keyChainCRUD = new CertificateUtil.KeyChainCRUD(this.mContext, i2);
        } catch (Throwable th) {
            th = th;
        }
        try {
            boolean deleteEntry =
                    keyChainCRUD.deleteEntry(i == 4 ? -1 : 1010, retrieveAliasToBeDeleted);
            Log.d("SecurityPolicy", "Delete state : " + deleteEntry);
            keyChainCRUD.disconnect();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return deleteEntry;
        } catch (Throwable th2) {
            th = th2;
            keyChainCRUD2 = keyChainCRUD;
            if (keyChainCRUD2 != null) {
                keyChainCRUD2.disconnect();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a9  */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String retrieveAliasToBeDeleted(
            java.security.cert.Certificate r16,
            java.lang.String r17,
            java.lang.String r18,
            int r19,
            int r20) {
        /*
            r15 = this;
            r0 = r15
            r1 = r19
            r2 = r20
            long r3 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L1f
            r6.<init>()     // Catch: java.lang.Throwable -> L1f
            boolean r7 = android.text.TextUtils.isEmpty(r17)     // Catch: java.lang.Throwable -> L1f
            r8 = 1010(0x3f2, float:1.415E-42)
            r9 = -1
            r10 = 4
            if (r7 != 0) goto L22
            r7 = r17
            r6.add(r7)     // Catch: java.lang.Throwable -> L1f
            goto L55
        L1f:
            r0 = move-exception
            goto La7
        L22:
            java.lang.String r6 = getValidStr$2(r18)     // Catch: java.lang.Throwable -> L1f
            long r11 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L1f
            if (r6 == 0) goto L45
            if (r1 != r10) goto L30
            r7 = r9
            goto L31
        L30:
            r7 = r8
        L31:
            android.content.Context r13 = r0.mContext     // Catch: java.lang.Throwable -> L40
            com.android.server.enterprise.utils.CertificateUtil$KeyChainCRUD r14 = new com.android.server.enterprise.utils.CertificateUtil$KeyChainCRUD     // Catch: java.lang.Throwable -> L40
            r14.<init>(r13, r2)     // Catch: java.lang.Throwable -> L40
            java.lang.String[] r6 = r14.listAliases(r7, r6)     // Catch: java.lang.Throwable -> L40
            r14.disconnect()     // Catch: java.lang.Throwable -> L40
            goto L46
        L40:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r11)     // Catch: java.lang.Throwable -> L1f
            throw r0     // Catch: java.lang.Throwable -> L1f
        L45:
            r6 = r5
        L46:
            android.os.Binder.restoreCallingIdentity(r11)     // Catch: java.lang.Throwable -> L1f
            if (r6 == 0) goto L50
            java.util.List r6 = java.util.Arrays.asList(r6)     // Catch: java.lang.Throwable -> L1f
            goto L55
        L50:
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L1f
            r6.<init>()     // Catch: java.lang.Throwable -> L1f
        L55:
            if (r1 != r10) goto L58
            r8 = r9
        L58:
            com.android.server.enterprise.utils.CertificateUtil$KeyChainCRUD r1 = new com.android.server.enterprise.utils.CertificateUtil$KeyChainCRUD     // Catch: java.lang.Throwable -> L1f
            android.content.Context r0 = r0.mContext     // Catch: java.lang.Throwable -> L1f
            r1.<init>(r0, r2)     // Catch: java.lang.Throwable -> L1f
            java.util.Iterator r0 = r6.iterator()     // Catch: java.lang.Throwable -> L97
        L63:
            boolean r2 = r0.hasNext()     // Catch: java.lang.Throwable -> L97
            if (r2 == 0) goto La0
            java.lang.Object r2 = r0.next()     // Catch: java.lang.Throwable -> L97
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> L97
            r6 = r18
            byte[] r7 = r1.get(r8, r2, r6)     // Catch: java.lang.Throwable -> L97
            if (r7 == 0) goto L9d
            java.util.List r7 = com.android.server.enterprise.utils.CertificateUtil.toCertificates(r7)     // Catch: java.lang.Throwable -> L97
            java.util.Iterator r7 = r7.iterator()     // Catch: java.lang.Throwable -> L97
        L7f:
            boolean r9 = r7.hasNext()     // Catch: java.lang.Throwable -> L97
            if (r9 == 0) goto L9d
            java.lang.Object r9 = r7.next()     // Catch: java.lang.Throwable -> L97
            java.security.cert.X509Certificate r9 = (java.security.cert.X509Certificate) r9     // Catch: java.lang.Throwable -> L97
            if (r9 == 0) goto L9a
            r10 = r16
            boolean r9 = r9.equals(r10)     // Catch: java.lang.Throwable -> L97
            if (r9 == 0) goto L7f
            r5 = r2
            goto L63
        L97:
            r0 = move-exception
            r5 = r1
            goto La7
        L9a:
            r10 = r16
            goto L7f
        L9d:
            r10 = r16
            goto L63
        La0:
            r1.disconnect()
            android.os.Binder.restoreCallingIdentity(r3)
            return r5
        La7:
            if (r5 == 0) goto Lac
            r5.disconnect()
        Lac:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.security.SecurityPolicy.retrieveAliasToBeDeleted(java.security.cert.Certificate,"
                    + " java.lang.String, java.lang.String, int, int):java.lang.String");
    }

    public final boolean setDeviceLastAccessDate(ContextInfo contextInfo, String str) {
        ContextInfo enforceOnlySecurityPermission$1 = enforceOnlySecurityPermission$1(contextInfo);
        if (!this.mContext
                .getPackageManager()
                .getNameForUid(enforceOnlySecurityPermission$1.mCallerUid)
                .equals(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME)) {
            return false;
        }
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceOnlySecurityPermission$1);
        try {
            this.mEdmStorageProvider.putGenericValueAsUser(
                    callingOrCurrentUserId, "deviceLastAccessDate", str);
            ((HashMap) mBannerMap).remove(Integer.valueOf(callingOrCurrentUserId));
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean setDodBannerVisibleStatus(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOnlySecurityPermission$1 = enforceOnlySecurityPermission$1(contextInfo);
        if (!this.mContext
                .getPackageManager()
                .getNameForUid(enforceOnlySecurityPermission$1.mCallerUid)
                .equals(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME)) {
            return false;
        }
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceOnlySecurityPermission$1);
        try {
            this.mEdmStorageProvider.putGenericValueAsUser(
                    callingOrCurrentUserId, "dodBannerVisible", Integer.toString(z ? 1 : 0));
            if (callingOrCurrentUserId == 0) {
                if (z) {
                    registerDexBlocker$3();
                    setHomeAndRecentKey(false);
                } else {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        ((SemDesktopModeManager)
                                        this.mContext
                                                .getApplicationContext()
                                                .getSystemService("desktopmode"))
                                .unregisterBlocker(this.mBlocker);
                        Log.d("SecurityPolicy", "DexBlocker was unregistered");
                    } catch (Exception unused) {
                        Log.d("SecurityPolicy", "DexBlocker was failed");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    setHomeAndRecentKey(true);
                }
            }
            return true;
        } catch (Exception unused2) {
            return false;
        }
    }

    public final void setExternalStorageEncryption(ContextInfo contextInfo, boolean z) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                EncryptionManagerAdapter.getInstance(this.mContext).getClass();
                if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
                    if (!z
                            && ((DevicePolicyManager)
                                            EncryptionManagerAdapter.mContext.getSystemService(
                                                    "device_policy"))
                                    .semGetRequireStorageCardEncryption(null)) {
                        Log.d(
                                "SecurityPolicy",
                                "SD Encryption enabled by some other admin cannot decrypt");
                        return;
                    } else if (z) {
                        new SemSdCardEncryption(EncryptionManagerAdapter.mContext)
                                .setSdCardEncryptionPolicy(1, -1, (String) null);
                    } else {
                        new SemSdCardEncryption(EncryptionManagerAdapter.mContext)
                                .setSdCardEncryptionPolicy(0, -1, (String) null);
                    }
                }
                if (z) {
                    AuditLog.logAsUser(
                            5,
                            1,
                            true,
                            Process.myPid(),
                            "SecurityPolicy",
                            String.format(
                                    "Admin %d has requested encryption of external storage",
                                    Integer.valueOf(contextInfo.mCallerUid)),
                            UserHandle.getUserId(contextInfo.mCallerUid));
                }
            } catch (Exception unused) {
                Log.w("SecurityPolicy", "is External Storage Encrypted?");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setHomeAndRecentKey(boolean z) {
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
                                Log.d("SecurityPolicy", "warning: no STATUS_BAR_SERVICE");
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
                    iStatusBarService2.disable(0, this.mToken, "SecurityPolicy");
                } else {
                    iStatusBarService2.disable(18874368, this.mToken, "SecurityPolicy");
                }
            }
            KeyCodeMediatorImpl keyCodeMediatorImpl = this.mKeyCodeMediator;
            if (keyCodeMediatorImpl == null) {
                Log.e(
                        "SecurityPolicy",
                        "mKeyCodeMediator must not be null! This will cause problems on hardware"
                            + " key restriction.");
            } else {
                keyCodeMediatorImpl.update(3);
                this.mKeyCodeMediator.update(1001);
                this.mKeyCodeMediator.update(
                        FrameworkStatsLog
                                .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
            }
        } catch (Exception unused) {
            Log.d("SecurityPolicy", "setHomeAndRecentKey was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void setInternalStorageEncryption(ContextInfo contextInfo, boolean z) {
        DevicePolicyManager devicePolicyManager;
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        if (this.mContext == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                devicePolicyManager =
                        (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            } catch (Exception unused) {
                Log.w("SecurityPolicy", "is Internal Storage Encrypted?");
            }
            if (!z && devicePolicyManager.getStorageEncryption(null)) {
                Log.d("SecurityPolicy", "SD Encryption enabled by some other admin cannot decrypt");
                return;
            }
            if (!z && !isInternalStorageEncrypted(contextInfo)) {
                Log.w("SecurityPolicy", "setInternalStorageEncryption : Not encrypted");
                return;
            }
            if (z && isInternalStorageEncrypted(contextInfo)) {
                Log.w(
                        "SecurityPolicy",
                        "setInternalStorageEncryption : device is already encrypted");
                return;
            }
            Log.d("SecurityPolicy", "setInternalStorageEncryption : Launching Encrption activity");
            if (z) {
                if (isInternalStorageEncryptedbyDefaultKey(contextInfo)) {
                    Intent intent = new Intent("android.app.action.START_CRYPT_INTERSTITIAL");
                    intent.addFlags(268435456);
                    this.mContext.startActivity(intent);
                } else {
                    Intent intent2 = new Intent("android.app.action.START_ENCRYPTION");
                    intent2.addFlags(268435456);
                    this.mContext.startActivity(intent2);
                }
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "SecurityPolicy",
                        String.format(
                                "Admin %d has requested encryption of internal storage",
                                Integer.valueOf(contextInfo.mCallerUid)),
                        UserHandle.getUserId(contextInfo.mCallerUid));
            }
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

    public final void setRequireDeviceEncryption(
            ContextInfo contextInfo, ComponentName componentName, boolean z) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .setStorageEncryption(componentName, z);
            } catch (Exception e) {
                Log.w("SecurityPolicy", "setRequireDeviceEncryption Ex" + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRequireStorageCardEncryption(
            ContextInfo contextInfo, ComponentName componentName, boolean z) {
        enforceOwnerOnlyAndSecurityPermission$2(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .semSetRequireStorageCardEncryption(componentName, z, contextInfo.mParent);
            } catch (Exception e) {
                Log.w("SecurityPolicy", "setRequireStorageCardEncryption Ex" + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startBannerService() {
        SemEmergencyManager semEmergencyManager = this.mEmergencyMgr;
        if (semEmergencyManager != null) {
            semEmergencyManager.isEmergencyMode();
        } else {
            Log.d("SecurityPolicy", "startBannerService() emergency service is null");
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setClassName(
                KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME,
                "com.samsung.android.mdm.DodBanner");
        boolean z = this.mContext.startServiceAsUser(intent, new UserHandle(0)) != null;
        if (z) {
            return;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "startBannerService() failed. userId = 0, ret = ", "SecurityPolicy", z);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        Log.d("SecurityPolicy", "systemReady()");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:0|1|(4:4|(3:15|16|17)|18|2)|23|24|(6:(8:55|56|57|(8:37|38|(1:42)|43|(1:45)|46|(1:48)(1:51)|49)|30|(1:33)|34|35)(2:60|61)|53|30|(1:33)|34|35)|27|(0)|37|38|(2:40|42)|43|(0)|46|(0)(0)|49|30|(0)|34|35) */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x008c, code lost:

       r1 = r5;
    */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0113 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0093 A[Catch: Exception -> 0x008c, TryCatch #1 {Exception -> 0x008c, blocks: (B:38:0x006c, B:40:0x007b, B:42:0x0088, B:43:0x008f, B:45:0x0093, B:46:0x009a, B:48:0x00cc, B:49:0x00d2), top: B:37:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cc A[Catch: Exception -> 0x008c, TryCatch #1 {Exception -> 0x008c, blocks: (B:38:0x006c, B:40:0x007b, B:42:0x0088, B:43:0x008f, B:45:0x0093, B:46:0x009a, B:48:0x00cc, B:49:0x00d2), top: B:37:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean wipeDevice(com.samsung.android.knox.ContextInfo r11, int r12) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.security.SecurityPolicy.wipeDevice(com.samsung.android.knox.ContextInfo,"
                    + " int):boolean");
    }
}
