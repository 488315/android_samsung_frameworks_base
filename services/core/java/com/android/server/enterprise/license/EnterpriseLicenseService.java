package com.android.server.enterprise.license;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.service.EventQueue;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.license.ActivationInfo;
import com.samsung.android.knox.license.Error;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.ILicenseResultCallback;
import com.samsung.android.knox.license.LicenseAgentDbContract;
import com.samsung.android.knox.license.LicenseInfo;
import com.samsung.android.knox.license.LicenseResult;
import com.samsung.android.knox.license.RightsObject;
import com.samsung.android.knox.ucm.core.IUcmService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EnterpriseLicenseService extends IEnterpriseLicense.Stub
        implements EnterpriseServiceCallback, IDeviceProfileObserver {
    public static final int MY_PID = Process.myPid();
    public static EdmStorageProvider mEdmStorageProvider;
    public static IPackageManager mPMS;
    public final Context mContext;
    public final Map mElmPkgRecords;
    public final List mKlmElmChangeList;
    public final Map mKlmPkgRecords;
    public final AnonymousClass1 mPackageRemovedReceiver;
    public IUcmService mUcmeService = null;
    public final List samsungSpecialPackages =
            new ArrayList(
                    List.of(
                            "com.sec.enterprise.knox.cloudmdm.smdms",
                            "com.sec.knox.kccagent",
                            "com.sec.knox.klat.agent"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.license.EnterpriseLicenseService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getData() == null) {
                Log.e("EnterpriseLicenseService", "intent or getData are null. Skip.");
                return;
            }
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            if (EnterpriseLicenseService.this.isPackageInstalled(schemeSpecificPart)) {
                return;
            }
            if (EnterpriseLicenseService.this.getInstanceId(schemeSpecificPart) != null) {
                EnterpriseLicenseService.this.resetLicenseByAdmin(schemeSpecificPart);
            }
            new Thread(
                            new EnterpriseLicenseService$$ExternalSyntheticLambda0(
                                    this,
                                    AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                            "packageName", schemeSpecificPart),
                                    4))
                    .start();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LicenseResultRecord implements IBinder.DeathRecipient {
        public final ILicenseResultCallback callback;
        public final String key;
        public final String licenseKey;
        public final Map recordMap;

        public LicenseResultRecord(String str, ILicenseResultCallback iLicenseResultCallback) {
            this.licenseKey = str;
            this.callback = iLicenseResultCallback;
        }

        public LicenseResultRecord(
                String str, String str2, ILicenseResultCallback iLicenseResultCallback, Map map) {
            this.key = str;
            this.licenseKey = str2;
            this.callback = iLicenseResultCallback;
            this.recordMap = map;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.e("EnterpriseLicenseService", "binderDied() key = " + this.key);
            EnterpriseLicenseService enterpriseLicenseService = EnterpriseLicenseService.this;
            String str = this.key;
            Map map = this.recordMap;
            enterpriseLicenseService.getClass();
            EnterpriseLicenseService.unregisterLicenseResultRecord(str, map);
        }
    }

    public EnterpriseLicenseService(Context context, IPackageManager iPackageManager) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        Objects.requireNonNull(context);
        this.mContext = context;
        mPMS = iPackageManager;
        mEdmStorageProvider = new EdmStorageProvider(context);
        this.mElmPkgRecords = new ConcurrentHashMap();
        this.mKlmPkgRecords = new ConcurrentHashMap();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiverAsUser(anonymousClass1, UserHandle.ALL, intentFilter, null, null);
        this.mKlmElmChangeList = new ArrayList();
        DeviceProfileListener deviceProfileListener = new DeviceProfileListener(context);
        Log.d("[EnterpriseLicenseService] DeviceProfileListener", "registerObserver()");
        ((ArrayList) deviceProfileListener.mObservers).add(this);
    }

    public static String getMaskedKlm(String str) {
        if (str == null) {
            return "";
        }
        String str2 = str.split("#")[0];
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] split = str2.split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        return split.length < 6
                ? ""
                : getMaskedText(
                        String.join(
                                PackageManagerShellCommandDataLoader.STDIN_PATH,
                                split[0],
                                split[1],
                                split[2],
                                split[3],
                                split[4],
                                split[5]));
    }

    public static String getMaskedText(String str) {
        if (TextUtils.isEmpty(str) || str.length() <= 24) {
            return str;
        }
        int length = str.length() - 24;
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str.substring(0, 12));
        m.append(new String(new char[length]).replace("\u0000", "*"));
        StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(m.toString());
        m2.append(str.substring(str.length() - 12));
        return m2.toString();
    }

    public static List getPermissions(String str) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) != 1000) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            callingUid, "Caller ", " is not SYSTEM_SERVICE OR SYSTEM APP"));
        }
        List list = null;
        if (str != null && !str.trim().isEmpty()) {
            try {
                EdmStorageProvider edmStorageProvider = mEdmStorageProvider;
                edmStorageProvider.getClass();
                ContentValues contentValues = new ContentValues();
                contentValues.put("pkgName", str);
                byte[] blob = edmStorageProvider.getBlob(contentValues, "LICENSE", "rightsObject");
                if (blob != null) {
                    RightsObject rightsObject = (RightsObject) Utils.deserializeObject(blob);
                    Log.d("EnterpriseLicenseService", "getPermissions() - deserializeObject");
                    if (rightsObject != null) {
                        list = rightsObject.getPermissions();
                    } else {
                        Log.w("EnterpriseLicenseService", "ro is null!!");
                    }
                }
            } catch (Exception unused) {
                Log.w("EnterpriseLicenseService", "getPermissions() failed");
            }
        }
        return list;
    }

    public static boolean isLicenseLocked(int i) {
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        int attributes = SemPersonaManager.getAttributes(i);
        if (attributes == -1) {
            return false;
        }
        boolean z = (attributes & 16) > 0;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isLicenseLocked : ", "EnterpriseLicenseService", z);
        return z;
    }

    public static boolean resetELMInfo(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("rightsObject", (byte[]) null);
        return mEdmStorageProvider.putValues(
                "LICENSE",
                contentValues,
                AccountManagerService$$ExternalSyntheticOutline0.m("pkgName", str));
    }

    public static void unregisterLicenseResultRecord(String str, Map map) {
        ILicenseResultCallback iLicenseResultCallback;
        Log.d("EnterpriseLicenseService", "unregisterLicenseResultRecord() for " + str);
        if (!map.containsKey(str)) {
            StorageManagerService$$ExternalSyntheticOutline0.m(
                    "license record not found for ", str, "EnterpriseLicenseService");
            return;
        }
        LicenseResultRecord licenseResultRecord = (LicenseResultRecord) map.get(str);
        if (licenseResultRecord != null
                && (iLicenseResultCallback = licenseResultRecord.callback) != null) {
            iLicenseResultCallback.asBinder().unlinkToDeath(licenseResultRecord, 0);
            Log.d("EnterpriseLicenseService", "DeathRecipient unlinked from " + str);
        }
        map.remove(str);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:3|4|5|(1:6)|(3:39|40|(2:42|(4:44|45|46|(7:48|49|50|51|52|53|54)(7:67|58|(2:60|61)|62|63|64|65))(3:71|10|(7:21|22|23|24|(2:26|(3:30|(1:32)|33))(1:36)|34|35)(6:14|15|16|17|18|19))))|8|9|10|(1:12)|21|22|23|24|(0)(0)|34|35) */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0143, code lost:

       r0 = e;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0160 A[Catch: all -> 0x0098, TRY_ENTER, TryCatch #3 {, blocks: (B:4:0x000b, B:52:0x0093, B:63:0x00d9, B:23:0x014e, B:26:0x0160, B:28:0x016c, B:30:0x016f, B:32:0x0174, B:36:0x018c, B:17:0x013e, B:75:0x019f, B:76:0x01a2, B:40:0x0032, B:42:0x0040, B:44:0x0050, B:46:0x0069, B:48:0x007c, B:51:0x0090, B:57:0x00aa, B:58:0x00af, B:60:0x00b4, B:38:0x0153, B:12:0x00e9, B:14:0x00f3, B:16:0x0127, B:22:0x0147), top: B:3:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x018c A[Catch: all -> 0x0098, TRY_LEAVE, TryCatch #3 {, blocks: (B:4:0x000b, B:52:0x0093, B:63:0x00d9, B:23:0x014e, B:26:0x0160, B:28:0x016c, B:30:0x016f, B:32:0x0174, B:36:0x018c, B:17:0x013e, B:75:0x019f, B:76:0x01a2, B:40:0x0032, B:42:0x0040, B:44:0x0050, B:46:0x0069, B:48:0x007c, B:51:0x0090, B:57:0x00aa, B:58:0x00af, B:60:0x00b4, B:38:0x0153, B:12:0x00e9, B:14:0x00f3, B:16:0x0127, B:22:0x0147), top: B:3:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b4 A[Catch: all -> 0x009b, Exception -> 0x00d4, TRY_LEAVE, TryCatch #4 {all -> 0x009b, blocks: (B:40:0x0032, B:42:0x0040, B:44:0x0050, B:46:0x0069, B:48:0x007c, B:51:0x0090, B:57:0x00aa, B:58:0x00af, B:60:0x00b4, B:38:0x0153, B:12:0x00e9, B:14:0x00f3, B:16:0x0127, B:22:0x0147), top: B:39:0x0032, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void activateKnoxLicense(
            com.samsung.android.knox.ContextInfo r21,
            java.lang.String r22,
            java.lang.String r23,
            com.samsung.android.knox.license.ILicenseResultCallback r24) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.activateKnoxLicense(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, java.lang.String,"
                    + " com.samsung.android.knox.license.ILicenseResultCallback):void");
    }

    public final synchronized void activateKnoxLicenseForUMC(
            ContextInfo contextInfo, String str, String str2) {
        activateKnoxLicense(contextInfo, str, str2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0161 A[Catch: all -> 0x0091, TRY_ENTER, TryCatch #1 {, blocks: (B:4:0x000b, B:54:0x008c, B:66:0x00c4, B:30:0x0144, B:10:0x0150, B:12:0x0161, B:14:0x016d, B:16:0x0170, B:18:0x0175, B:23:0x0188, B:77:0x0198, B:78:0x019b, B:41:0x0034, B:44:0x0040, B:46:0x0050, B:48:0x0069, B:50:0x007a, B:53:0x0089, B:59:0x009e, B:60:0x00a1, B:62:0x00a6, B:25:0x00cf, B:29:0x00f5, B:33:0x00de, B:35:0x00ef, B:9:0x0149, B:39:0x0154), top: B:3:0x000b, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0188 A[Catch: all -> 0x0091, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x000b, B:54:0x008c, B:66:0x00c4, B:30:0x0144, B:10:0x0150, B:12:0x0161, B:14:0x016d, B:16:0x0170, B:18:0x0175, B:23:0x0188, B:77:0x0198, B:78:0x019b, B:41:0x0034, B:44:0x0040, B:46:0x0050, B:48:0x0069, B:50:0x007a, B:53:0x0089, B:59:0x009e, B:60:0x00a1, B:62:0x00a6, B:25:0x00cf, B:29:0x00f5, B:33:0x00de, B:35:0x00ef, B:9:0x0149, B:39:0x0154), top: B:3:0x000b, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a6 A[Catch: all -> 0x0094, Exception -> 0x00bf, TRY_LEAVE, TryCatch #0 {Exception -> 0x00bf, blocks: (B:41:0x0034, B:44:0x0040, B:46:0x0050, B:59:0x009e, B:60:0x00a1, B:62:0x00a6), top: B:40:0x0034 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void activateLicense(
            com.samsung.android.knox.ContextInfo r20,
            java.lang.String r21,
            java.lang.String r22,
            java.lang.String r23,
            com.samsung.android.knox.license.ILicenseResultCallback r24) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.activateLicense(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, java.lang.String, java.lang.String,"
                    + " com.samsung.android.knox.license.ILicenseResultCallback):void");
    }

    public final synchronized void activateLicenseForUMC(
            ContextInfo contextInfo, String str, String str2, String str3) {
        activateLicense(contextInfo, str, str2, str3, null);
    }

    public final Bundle callLicenseAgent(String str, String str2, Bundle bundle) {
        Log.d("EnterpriseLicenseService", "callLicenseAgent() - ".concat(str));
        try {
            return this.mContext
                    .getContentResolver()
                    .call(LicenseAgentDbContract.DB_URI, str, str2, bundle);
        } catch (Exception e) {
            switch (str) {
                case "ELMRegistrationInternal":
                    sendElmResult(
                            "fail",
                            301,
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.LICENSE_DATA_UUID"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.LICENSE_DATA_REQUEST_PACKAGENAME"),
                            null,
                            -1,
                            new ArrayList(),
                            new ArrayList(),
                            null);
                    break;
                case "KLMDeactivationInternal":
                    sendKlmResult(
                            "fail",
                            301,
                            802,
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.LICENSE_DATA_UUID"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME"),
                            -1,
                            new ArrayList(),
                            new ArrayList(),
                            null);
                    break;
                case "KLMRegistrationInternal":
                    sendKlmResult(
                            "fail",
                            301,
                            800,
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.LICENSE_DATA_UUID"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME"),
                            bundle.getString(
                                    "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME"),
                            -1,
                            new ArrayList(),
                            new ArrayList(),
                            null);
                    break;
            }
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Exception calling KLMSAgent: "),
                    "EnterpriseLicenseService");
            return null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:3|4|5|6|(3:39|40|(2:42|(2:44|(4:46|47|48|(7:50|51|52|53|54|55|56)(7:69|60|(2:62|63)|64|65|66|67))(3:73|10|(7:21|22|23|24|(2:26|(3:30|(1:32)|33))(1:36)|34|35)(6:14|15|16|17|18|19)))(3:74|75|76)))|8|9|10|(1:12)|21|22|23|24|(0)(0)|34|35) */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0150, code lost:

       r0 = e;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x016e A[Catch: all -> 0x009c, TRY_ENTER, TryCatch #3 {, blocks: (B:4:0x000c, B:54:0x0097, B:65:0x00dd, B:23:0x015b, B:26:0x016e, B:28:0x017a, B:30:0x017d, B:32:0x0182, B:36:0x019a, B:17:0x014b, B:80:0x01ad, B:81:0x01b0, B:40:0x0034, B:42:0x0042, B:44:0x004e, B:46:0x0054, B:48:0x006d, B:50:0x0080, B:53:0x0094, B:59:0x00ae, B:60:0x00b3, B:62:0x00b8, B:38:0x0160, B:12:0x00f6, B:14:0x0100, B:16:0x0134, B:22:0x0154, B:75:0x00ec, B:76:0x00f1), top: B:3:0x000c, inners: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x019a A[Catch: all -> 0x009c, TRY_LEAVE, TryCatch #3 {, blocks: (B:4:0x000c, B:54:0x0097, B:65:0x00dd, B:23:0x015b, B:26:0x016e, B:28:0x017a, B:30:0x017d, B:32:0x0182, B:36:0x019a, B:17:0x014b, B:80:0x01ad, B:81:0x01b0, B:40:0x0034, B:42:0x0042, B:44:0x004e, B:46:0x0054, B:48:0x006d, B:50:0x0080, B:53:0x0094, B:59:0x00ae, B:60:0x00b3, B:62:0x00b8, B:38:0x0160, B:12:0x00f6, B:14:0x0100, B:16:0x0134, B:22:0x0154, B:75:0x00ec, B:76:0x00f1), top: B:3:0x000c, inners: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b8 A[Catch: all -> 0x009f, Exception -> 0x00d8, TRY_LEAVE, TryCatch #1 {Exception -> 0x00d8, blocks: (B:59:0x00ae, B:60:0x00b3, B:62:0x00b8, B:12:0x00f6, B:14:0x0100, B:75:0x00ec, B:76:0x00f1), top: B:6:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void deActivateKnoxLicense(
            com.samsung.android.knox.ContextInfo r21,
            java.lang.String r22,
            java.lang.String r23,
            com.samsung.android.knox.license.ILicenseResultCallback r24) {
        /*
            Method dump skipped, instructions count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.deActivateKnoxLicense(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, java.lang.String,"
                    + " com.samsung.android.knox.license.ILicenseResultCallback):void");
    }

    public final boolean deleteAllApiCallData() {
        enforcePermission$1();
        try {
            return LicenseLogService.mEdmStorageProvider.deleteDataByFields(
                    "LICENSE_LOG", null, null);
        } catch (Exception e) {
            Log.w("EnterpriseLicenseService", "deleteAllApiCallData() failed");
            e.printStackTrace();
            return false;
        }
    }

    public final boolean deleteApiCallData(String str, String str2, Error error) {
        enforcePermission$1();
        if (str2 != null && !str2.trim().isEmpty()) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("instanceId", str2);
                ContentValues value =
                        mEdmStorageProvider.getValue(contentValues, "LICENSE", "pkgName");
                if (value == null) {
                    Log.w("EnterpriseLicenseService", "deleteApiCallData(): result is null");
                    return false;
                }
                String asString = value.getAsString("pkgName");
                if (asString == null) {
                    Log.w("EnterpriseLicenseService", "deleteApiCallData(): Record does not exist");
                    return false;
                }
                return LicenseLogService.mEdmStorageProvider.deleteDataByFields(
                        "LICENSE_LOG", new String[] {"pkgName"}, new String[] {asString});
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "deleteApiCallData() failed");
                e.printStackTrace();
            }
        }
        return false;
    }

    public final boolean deleteApiCallDataByAdmin(String str) {
        enforcePermission$1();
        if (str != null && !str.trim().isEmpty()) {
            try {
                return LicenseLogService.mEdmStorageProvider.deleteDataByFields(
                        "LICENSE_LOG", new String[] {"pkgName"}, new String[] {str});
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "deleteApiCallDataByAdmin() failed");
                e.printStackTrace();
            }
        }
        return false;
    }

    public final boolean deleteLicense(String str) {
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (str != null) {
                try {
                } catch (Exception e) {
                    Log.w("EnterpriseLicenseService", "deleteLicense() failed");
                    e.printStackTrace();
                }
                if (!str.trim().isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("instanceId", str);
                    ContentValues value =
                            mEdmStorageProvider.getValue(contentValues, "LICENSE", "pkgName");
                    if (value == null) {
                        Log.w("EnterpriseLicenseService", "deleteLicense(): result is null");
                        return false;
                    }
                    String asString = value.getAsString("pkgName");
                    if (asString == null) {
                        Log.w(
                                "EnterpriseLicenseService",
                                "deleteLicense(): pkgName is null, Record does not exist");
                        return false;
                    }
                    z =
                            mEdmStorageProvider.deleteDataByFields(
                                    "LICENSE", new String[] {"pkgName"}, new String[] {asString});
                    if (z) {
                        mPMS.setLicensePermissionsForMDM(asString);
                    }
                    return z;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean deleteLicenseByAdmin(String str) {
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (str != null) {
                try {
                } catch (Exception unused) {
                    Log.e("EnterpriseLicenseService", "deleteLicenseByAdmin() failed");
                }
                if (!str.trim().isEmpty()) {
                    String instanceId = getInstanceId(str);
                    z =
                            mEdmStorageProvider.deleteDataByFields(
                                    "LICENSE", new String[] {"pkgName"}, new String[] {str});
                    if (z) {
                        if (isPackageInstalled(str)) {
                            mPMS.setLicensePermissionsForMDM(str);
                        }
                        try {
                            if (Integer.parseInt(instanceId) > -1) {
                                Log.d("EnterpriseLicenseService", "isElmKey(True)");
                                Log.d(
                                        "EnterpriseLicenseService",
                                        "deleteLicenseByAdmin(): notify ELM observers");
                                notifyElmObservers(
                                        str,
                                        new LicenseResult(
                                                str,
                                                LicenseResult.Status.SUCCESS,
                                                0,
                                                LicenseResult.Type.ELM_DEACTIVATION,
                                                (ArrayList) null,
                                                (String) null));
                            }
                        } catch (NumberFormatException unused2) {
                            Log.e("EnterpriseLicenseService", "isElmKey(False)");
                        }
                    }
                    return z;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enforcePermission$1() {
        if (Binder.getCallingPid() == MY_PID) {
            return;
        }
        try {
            this.mContext.enforceCallingPermission(
                    "com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL", null);
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                message =
                        message.concat(
                                ",com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL");
            }
            throw new SecurityException(message);
        }
    }

    public final List getAllLicenseActivationsInfos() {
        ArrayList arrayList = new ArrayList();
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle callLicenseAgent = callLicenseAgent("getAllActivations", null, null);
            if (callLicenseAgent != null) {
                arrayList =
                        callLicenseAgent.getParcelableArrayList(
                                KnoxCustomManagerService.SPCM_KEY_RESULT);
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final LicenseInfo[] getAllLicenseInfo() {
        enforcePermission$1();
        try {
            ArrayList arrayList =
                    (ArrayList)
                            mEdmStorageProvider.getValuesList(
                                    "LICENSE",
                                    new String[] {"pkgName", "instanceId", "pkgVersion"},
                                    null);
            if (!arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    arrayList2.add(
                            new LicenseInfo(
                                    contentValues.getAsString("pkgName"),
                                    contentValues.getAsString("instanceId"),
                                    contentValues.getAsString("pkgVersion")));
                }
                if (arrayList2.size() > 0) {
                    return (LicenseInfo[]) arrayList2.toArray(new LicenseInfo[arrayList2.size()]);
                }
            }
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfo() failed");
        }
        return null;
    }

    public final Bundle getApiCallData(String str) {
        enforcePermission$1();
        if (str != null && !str.trim().isEmpty()) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("instanceId", str);
                ContentValues value =
                        mEdmStorageProvider.getValue(contentValues, "LICENSE", "pkgName");
                if (value == null) {
                    Log.w(
                            "EnterpriseLicenseService",
                            "getApiCallData(): result is null, Record does not exist");
                    return null;
                }
                String asString = value.getAsString("pkgName");
                if (asString != null) {
                    return LicenseLog.getLog(asString);
                }
                Log.w(
                        "EnterpriseLicenseService",
                        "getApiCallData(): pkgName is null, Record does not exist");
                return null;
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "getApiCallData() failed");
                e.printStackTrace();
            }
        }
        return null;
    }

    public final Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str) {
        try {
            this.mContext.enforceCallingPermission(
                    "com.samsung.android.knox.permission.KNOX_LICENSE_LOG", null);
            if (str != null && !str.trim().isEmpty()) {
                try {
                    return LicenseLog.getLog(str);
                } catch (Exception unused) {
                    Log.w("EnterpriseLicenseService", "getApiCallDataByAdmin() failed");
                }
            }
            return null;
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                message = message.concat(",com.samsung.android.knox.permission.KNOX_LICENSE_LOG");
            }
            throw new SecurityException(message);
        }
    }

    public final List getELMPermissions(String str) {
        try {
            return getPermissions(str);
        } catch (SecurityException e) {
            Log.w("EnterpriseLicenseService", "getELMPermissions() failed: " + e.getMessage());
            return null;
        }
    }

    public final String getInstanceId(String str) {
        enforcePermission$1();
        String str2 = null;
        if (str != null && !str.trim().isEmpty()) {
            try {
                ArrayList arrayList =
                        (ArrayList)
                                mEdmStorageProvider.getValuesList(
                                        "LICENSE", new String[] {"pkgName", "instanceId"}, null);
                if (!arrayList.isEmpty()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        String asString = contentValues.getAsString("pkgName");
                        if (asString != null && asString.equals(str)) {
                            str2 = contentValues.getAsString("instanceId");
                        }
                    }
                }
            } catch (Exception unused) {
                Log.w("EnterpriseLicenseService", "getInstanceId() failed");
            }
        }
        return str2;
    }

    public final ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str) {
        String nameForUid =
                this.mContext
                        .getPackageManager()
                        .getNameForUid(Utils.getCallingOrUserUid(contextInfo));
        if (str != null) {
            enforcePermission$1();
        } else {
            str = nameForUid;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle callLicenseAgent = callLicenseAgent("getActivations", str, null);
            return callLicenseAgent != null
                    ? (ActivationInfo)
                            callLicenseAgent.getParcelable(KnoxCustomManagerService.SPCM_KEY_RESULT)
                    : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final LicenseInfo getLicenseInfo(String str) {
        enforcePermission$1();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            ArrayList arrayList =
                    (ArrayList)
                            mEdmStorageProvider.getValuesList(
                                    "LICENSE",
                                    new String[] {"pkgName", "instanceId", "pkgVersion"},
                                    null);
            if (arrayList.isEmpty()) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("instanceId");
                if (asString != null && asString.equals(str)) {
                    return new LicenseInfo(
                            contentValues.getAsString("pkgName"),
                            asString,
                            contentValues.getAsString("pkgVersion"));
                }
            }
            return null;
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfo() failed");
            return null;
        }
    }

    public final LicenseInfo getLicenseInfoByAdmin(String str) {
        enforcePermission$1();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            ArrayList arrayList =
                    (ArrayList)
                            mEdmStorageProvider.getValuesList(
                                    "LICENSE",
                                    new String[] {"pkgName", "instanceId", "pkgVersion"},
                                    null);
            if (arrayList.isEmpty()) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("pkgName");
                if (asString != null && asString.equals(str)) {
                    return new LicenseInfo(
                            str,
                            contentValues.getAsString("instanceId"),
                            contentValues.getAsString("pkgVersion"));
                }
            }
            return null;
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfoByAdmin() failed");
            return null;
        }
    }

    public final String getPackageName(int i, int i2) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        if (nameForUid == null || !nameForUid.contains(":")) {
            return nameForUid;
        }
        ActivityManager activityManager =
                (ActivityManager) this.mContext.getSystemService("activity");
        String packageFromAppProcesses =
                activityManager != null ? activityManager.getPackageFromAppProcesses(i2) : "";
        return TextUtils.isEmpty(packageFromAppProcesses) ? nameForUid : packageFromAppProcesses;
    }

    public final RightsObject getRightsObject(String str) {
        RightsObject rightsObject;
        Exception e;
        byte[] blob;
        enforcePermission$1();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            EdmStorageProvider edmStorageProvider = mEdmStorageProvider;
            edmStorageProvider.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("instanceId", str);
            blob = edmStorageProvider.getBlob(contentValues, "LICENSE", "rightsObject");
        } catch (Exception e2) {
            rightsObject = null;
            e = e2;
        }
        if (blob == null) {
            return null;
        }
        rightsObject = (RightsObject) Utils.deserializeObject(blob);
        try {
            Log.d("EnterpriseLicenseService", "getRightsObject() - deserializeObject");
        } catch (Exception e3) {
            e = e3;
            Log.w("EnterpriseLicenseService", "getRightsObject() failed");
            e.printStackTrace();
            return rightsObject;
        }
        return rightsObject;
    }

    public final RightsObject getRightsObjectByAdmin(String str) {
        RightsObject rightsObject;
        Exception e;
        byte[] blob;
        enforcePermission$1();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            EdmStorageProvider edmStorageProvider = mEdmStorageProvider;
            edmStorageProvider.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pkgName", str);
            blob = edmStorageProvider.getBlob(contentValues, "LICENSE", "rightsObject");
        } catch (Exception e2) {
            rightsObject = null;
            e = e2;
        }
        if (blob == null) {
            return null;
        }
        rightsObject = (RightsObject) Utils.deserializeObject(blob);
        try {
            Log.d("EnterpriseLicenseService", "getRightsObjectByAdmin() - deserializeObject");
        } catch (Exception e3) {
            e = e3;
            Log.w("EnterpriseLicenseService", "getRightsObjectByAdmin() failed");
            e.printStackTrace();
            return rightsObject;
        }
        return rightsObject;
    }

    public final synchronized IUcmService getUcmService() {
        try {
            if (this.mUcmeService == null) {
                this.mUcmeService =
                        IUcmService.Stub.asInterface(
                                ServiceManager.getService("com.samsung.ucs.ucsservice"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mUcmeService;
    }

    public final boolean isActionAllowedForAnotherPackage(String str, int i, String[] strArr) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Arrays.asList(strArr).contains(str)) {
            Log.d(
                    "EnterpriseLicenseService",
                    "Request allowed from callerSharedPackages to targetPackageName");
            return true;
        }
        for (String str2 : strArr) {
            if (isCallerAllowedToPerformActionForAnotherPackage(i, str2)) {
                Log.d(
                        "EnterpriseLicenseService",
                        "Request allowed by platform signature or license permission");
                return true;
            }
        }
        return false;
    }

    public final boolean isCallerAllowedToPerformActionForAnotherPackage(int i, String str) {
        try {
            if (((ArrayList) this.samsungSpecialPackages).contains(str)) {
                try {
                    return AppGlobals.getPackageManager().checkSignatures("android", str, i) == 0;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            KpuHelper.getInstance(this.mContext).getClass();
            if (!"com.samsung.android.knox.kpu".equals(str)
                    && !KpuHelper.isKpuPermissionGranted(i, str)) {
                try {
                    if (AppGlobals.getPackageManager().checkSignatures("android", str, i) != 0) {
                        return false;
                    }
                    try {
                        return AppGlobals.getPackageManager()
                                        .checkPermission(
                                                "com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL",
                                                str,
                                                i)
                                == 0;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                    return false;
                }
            }
            return KpuHelper.getInstance(this.mContext).isKpuPlatformSigned(i, str);
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
        e4.printStackTrace();
        return false;
    }

    public final boolean isEulaBypassAllowed(String str) {
        Log.d("EnterpriseLicenseService", "isEulaBypassAllowed");
        enforcePermission$1();
        try {
            ArrayList arrayList =
                    (ArrayList)
                            mEdmStorageProvider.getValuesListAsUser(
                                    0, 0, "KNOX_CUSTOM", new String[] {"mamPackageName"});
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String asString = ((ContentValues) it.next()).getAsString("mamPackageName");
                    if (asString != null) {
                        for (String str2 : asString.split(";")) {
                            if (str2.equals(str)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w("EnterpriseLicenseService", "isEulaBypassAllowed() failed");
            e.printStackTrace();
        }
        return false;
    }

    public final boolean isPackageInstalled(String str) {
        Log.d("EnterpriseLicenseService", "isPackageInstalled()");
        for (UserInfo userInfo :
                ((UserManager) this.mContext.getSystemService("user")).getUsers()) {
            try {
                this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, userInfo.id);
                Log.d(
                        "EnterpriseLicenseService",
                        "isPackageInstalled() - " + str + " found in user " + userInfo.id);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "isPackageInstalled() - ", str, " not found in user "),
                        userInfo.id,
                        "EnterpriseLicenseService");
            }
        }
        return false;
    }

    public final boolean isServiceAvailable(String str, String str2) {
        Log.d("EnterpriseLicenseService", "isServiceAvailable");
        if (str2 == null || str2.isEmpty()) {
            Log.d("EnterpriseLicenseService", "serviceName is null");
            return false;
        }
        if (str != null) {
            try {
            } catch (Exception unused) {
                Log.w("EnterpriseLicenseService", "check Service did not find permission");
            }
            if (!str.isEmpty()) {
                EdmStorageProvider edmStorageProvider = mEdmStorageProvider;
                edmStorageProvider.getClass();
                ContentValues contentValues = new ContentValues();
                contentValues.put("pkgName", str);
                RightsObject rightsObject =
                        (RightsObject)
                                Utils.deserializeObject(
                                        edmStorageProvider.getBlob(
                                                contentValues, "LICENSE", "rightsObject"));
                return rightsObject != null && rightsObject.getPermissions().contains(str2);
            }
        }
        ArrayList arrayList =
                (ArrayList)
                        mEdmStorageProvider.getValuesList(
                                "LICENSE", new String[] {"rightsObject"}, null);
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                RightsObject rightsObject2 =
                        (RightsObject)
                                Utils.deserializeObject(
                                        ((ContentValues) it.next()).getAsByteArray("rightsObject"));
                if (rightsObject2 != null && rightsObject2.getPermissions().contains(str2)) {
                    return true;
                }
            }
        }
    }

    public final void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
        if (str == null || contextInfo == null || LicenseLogService.mHandler == null) {
            return;
        }
        try {
            int appId = UserHandle.getAppId(Binder.getCallingUid());
            if (appId >= 10000 && appId <= 19999) {
                Message obtainMessage = LicenseLogService.mHandler.obtainMessage(2);
                Bundle bundle = new Bundle();
                bundle.putString("apiName", str);
                bundle.putInt("adminUid", contextInfo.mCallerUid);
                bundle.putBoolean(EventQueue.API_USAGE_GET_KEY, z);
                bundle.putBoolean("isOldNamespace", z2);
                bundle.putInt("profileUserId", contextInfo.mContainerId);
                bundle.putBoolean("parent", contextInfo.mParent);
                bundle.putInt("dalessCallerPackage", contextInfo.mDALessCallerUid);
                obtainMessage.setData(bundle);
                LicenseLogService.mHandler.sendMessage(obtainMessage);
            }
        } catch (Exception e) {
            Log.w("LicenseLogService", "log() failed");
            Slog.w("LicenseLogService", "log() failed", e);
        }
    }

    public final void notifyContainerLicenseStatus(String str) {
        Iterator it = ((ArrayList) this.mKlmElmChangeList).iterator();
        while (it.hasNext()) {
            ((IActivationKlmElmObserver) it.next()).onUpdateContainerLicenseStatus(str);
        }
    }

    public final void notifyElmObservers(String str, LicenseResult licenseResult) {
        Iterator it = ((ArrayList) this.mKlmElmChangeList).iterator();
        while (it.hasNext()) {
            ((IActivationKlmElmObserver) it.next()).onUpdateElm(str, licenseResult);
        }
    }

    public final void notifyKlmObservers(String str, LicenseResult licenseResult) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = ((ArrayList) this.mKlmElmChangeList).iterator();
            while (it.hasNext()) {
                ((IActivationKlmElmObserver) it.next()).onUpdateKlm(str, licenseResult);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onUserStarting(int i) {
        updateAdminPermissions();
    }

    public final boolean processKnoxLicenseResponse(
            String str,
            String str2,
            String str3,
            String str4,
            Error error,
            int i,
            String str5,
            RightsObject rightsObject,
            int i2) {
        ArrayList arrayList;
        boolean z;
        ArrayList arrayList2;
        enforcePermission$1();
        String str6 = "EnterpriseLicenseService";
        Log.d("EnterpriseLicenseService", "processKnoxLicenseResponse()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str7 = null;
        try {
            try {
                if (rightsObject != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("pkgName", str2);
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("rightsObject", Utils.serializeObject(rightsObject));
                    if (mEdmStorageProvider.getCount("LICENSE", contentValues) > 0) {
                        z = mEdmStorageProvider.putValues("LICENSE", contentValues2, contentValues);
                    } else {
                        contentValues2.put("instanceId", "-1");
                        contentValues2.put("pkgVersion", str3);
                        contentValues2.put("pkgName", str2);
                        z = mEdmStorageProvider.putValuesNoUpdate("LICENSE", contentValues2);
                    }
                    if (z) {
                        Log.d(
                                "EnterpriseLicenseService",
                                "processKnoxLicenseResponse(): License Table update.");
                        Log.d(
                                "EnterpriseLicenseService",
                                "result setLicensePermissionForMDM("
                                        + str2
                                        + "): "
                                        + mPMS.setLicensePermissionsForMDM(str2));
                        arrayList2 = new ArrayList(mPMS.getPackageGrantedPermissionsForMDM(str2));
                        int i3 = EnterpriseDeviceManagerService.$r8$clinit;
                        ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance)
                                .startDeferredServicesIfNeeded();
                    } else {
                        arrayList2 = null;
                    }
                    arrayList = arrayList2;
                } else {
                    Log.w("EnterpriseLicenseService", "processKnoxLicenseResponse().RO is null");
                    arrayList = null;
                    z = false;
                }
                ArrayList arrayList3 = z ? (ArrayList) getPermissions(str2) : new ArrayList();
                if (str != null) {
                    if (i != 801 && ((ConcurrentHashMap) this.mKlmPkgRecords).containsKey(str)) {
                        str7 =
                                ((LicenseResultRecord)
                                                ((ConcurrentHashMap) this.mKlmPkgRecords).get(str))
                                        .licenseKey;
                    } else if (i != 801) {
                        Log.w(
                                "EnterpriseLicenseService",
                                "klm activation record not found for "
                                        + str
                                        + " and package "
                                        + str2);
                    }
                }
                try {
                    LicenseResult licenseResult =
                            new LicenseResult(
                                    str2,
                                    str4,
                                    error.getErrorCode(),
                                    LicenseResult.Type.fromKlmStatus(i),
                                    arrayList,
                                    getMaskedKlm(str7));
                    str6 = "EnterpriseLicenseService";
                    sendKlmResult(
                            str4,
                            error.getErrorCode(),
                            i,
                            str,
                            str2,
                            str5,
                            i2,
                            arrayList,
                            arrayList3,
                            null);
                    IUcmService ucmService = getUcmService();
                    if (ucmService != null) {
                        ucmService.notifyLicenseStatus(str2, str4, error.getErrorCode());
                    }
                    Log.d(str6, "processKnoxLicenseResponse: " + error.getErrorCode());
                    if (error.getErrorCode() != 501) {
                        notifyContainerLicenseStatus(str2);
                    }
                    notifyKlmObservers(str2, licenseResult);
                    sendDeviceRegistrationIntentToKpmAgent(str4, str2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (Exception e) {
                    e = e;
                    str6 = "EnterpriseLicenseService";
                    Log.w(str6, "processKnoxLicenseResponse() failed");
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:(1:33)(2:117|(2:120|121)(16:119|35|36|(5:102|103|104|105|106)|38|(1:(1:41)(13:60|61|62|63|64|(1:66)|68|(3:70|(1:72)|73)(1:83)|(2:75|(1:77)(1:78))|79|80|81|82))|100|64|(0)|68|(0)(0)|(0)|79|80|81|82))|35|36|(0)|38|(0)|100|64|(0)|68|(0)(0)|(0)|79|80|81|82) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01a6, code lost:

       r0 = e;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0059 A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x033f A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0355 A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01eb A[Catch: all -> 0x01c3, Exception -> 0x01c7, TRY_LEAVE, TryCatch #2 {all -> 0x01c3, blocks: (B:86:0x0292, B:63:0x01bf, B:64:0x01ce, B:66:0x01eb), top: B:13:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01f5 A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x020e A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02a4 A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02bd A[Catch: all -> 0x004c, TryCatch #8 {all -> 0x004c, blocks: (B:4:0x0029, B:6:0x003a, B:9:0x0041, B:12:0x004f, B:15:0x0059, B:17:0x0064, B:19:0x006c, B:21:0x0076, B:22:0x0085, B:23:0x009e, B:46:0x0338, B:48:0x033f, B:50:0x0345, B:53:0x0355, B:55:0x035f, B:56:0x036e, B:57:0x0387, B:58:0x03d1, B:87:0x029d, B:89:0x02a4, B:91:0x02aa, B:94:0x02bd, B:96:0x02c7, B:97:0x02d6, B:98:0x02ef, B:80:0x0283, B:68:0x01ee, B:70:0x01f5, B:72:0x01fb, B:75:0x020e, B:77:0x0218, B:78:0x0227, B:79:0x0240), top: B:3:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02b4  */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v17 */
    /* JADX WARN: Type inference failed for: r15v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r15v19 */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean processLicenseActivationResponse(
            java.lang.String r24,
            java.lang.String r25,
            java.lang.String r26,
            java.lang.String r27,
            java.lang.String r28,
            com.samsung.android.knox.license.RightsObject r29,
            com.samsung.android.knox.license.Error r30,
            java.lang.String r31,
            java.lang.String r32,
            int r33) {
        /*
            Method dump skipped, instructions count: 980
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.processLicenseActivationResponse(java.lang.String,"
                    + " java.lang.String, java.lang.String, java.lang.String, java.lang.String,"
                    + " com.samsung.android.knox.license.RightsObject,"
                    + " com.samsung.android.knox.license.Error, java.lang.String, java.lang.String,"
                    + " int):boolean");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:(10:(1:(1:41)(22:58|59|60|61|62|63|(9:67|68|69|70|71|(1:74)(0)|77|64|65)|144|145|(2:79|(2:81|(1:85)))|(1:87)|89|90|91|92|93|(2:(1:96)|97)(1:104)|98|99|(1:101)|102|103))|91|92|93|(0)(0)|98|99|(0)|102|103)|61|62|63|(2:64|65)|144|145|(0)|(0)|89|90) */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x02d8, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02d9, code lost:

       r12 = r29;
       r16 = r11;
       r15 = r18;
       r11 = r19;
       r5 = r20;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x02e3, code lost:

       r2 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0188 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0218 A[Catch: all -> 0x01d7, Exception -> 0x01e8, TryCatch #10 {Exception -> 0x01e8, blocks: (B:71:0x019a, B:74:0x01b0, B:77:0x01b7, B:79:0x0218, B:81:0x022c, B:83:0x0240, B:87:0x024a, B:90:0x024d, B:92:0x0255), top: B:70:0x019a }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x024a A[Catch: all -> 0x01d7, Exception -> 0x01e8, TRY_LEAVE, TryCatch #10 {Exception -> 0x01e8, blocks: (B:71:0x019a, B:74:0x01b0, B:77:0x01b7, B:79:0x0218, B:81:0x022c, B:83:0x0240, B:87:0x024a, B:90:0x024d, B:92:0x0255), top: B:70:0x019a }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x025f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean processLicenseValidationResult(
            java.lang.String r26,
            com.samsung.android.knox.license.RightsObject r27,
            com.samsung.android.knox.license.Error r28,
            java.lang.String r29,
            java.lang.String r30,
            java.lang.String r31,
            java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 1087
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.processLicenseValidationResult(java.lang.String,"
                    + " com.samsung.android.knox.license.RightsObject,"
                    + " com.samsung.android.knox.license.Error, java.lang.String, java.lang.String,"
                    + " java.lang.String, java.lang.String):boolean");
    }

    public final void registerLicenseResultRecord(
            String str, String str2, ILicenseResultCallback iLicenseResultCallback, Map map) {
        Log.d("EnterpriseLicenseService", "registerLicenseResultRecord() for " + str);
        LicenseResultRecord licenseResultRecord =
                new LicenseResultRecord(str, str2, iLicenseResultCallback, map);
        if (iLicenseResultCallback != null) {
            try {
                iLicenseResultCallback.asBinder().linkToDeath(licenseResultRecord, 0);
                Log.d("EnterpriseLicenseService", "DeathRecipient successfully linked to " + str);
            } catch (RemoteException e) {
                Log.e("EnterpriseLicenseService", e.getMessage());
            }
        }
        map.put(str, licenseResultRecord);
    }

    public final boolean resetLicense(String str) {
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (str != null) {
                try {
                } catch (Exception e) {
                    Log.w("EnterpriseLicenseService", "resetLicense() failed");
                    e.printStackTrace();
                }
                if (!str.trim().isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("instanceId", str);
                    ContentValues value =
                            mEdmStorageProvider.getValue(contentValues, "LICENSE", "pkgName");
                    if (value == null) {
                        Log.w("EnterpriseLicenseService", "resetLicense(): result is null");
                        return false;
                    }
                    String asString = value.getAsString("pkgName");
                    if (asString == null) {
                        Log.w(
                                "EnterpriseLicenseService",
                                "resetLicense(): pkgName is null, Record does not exist");
                        return false;
                    }
                    z = resetELMInfo(asString);
                    if (z) {
                        mPMS.setLicensePermissionsForMDM(asString);
                    }
                    return z;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean resetLicenseByAdmin(String str) {
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        if (str != null) {
            try {
                try {
                } catch (Exception unused) {
                    Log.w("EnterpriseLicenseService", "resetLicenseByAdmin() failed");
                }
                if (!str.trim().isEmpty()) {
                    z = resetELMInfo(str);
                    if (z && isPackageInstalled(str)) {
                        mPMS.setLicensePermissionsForMDM(str);
                    }
                    return z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public final void sendDeviceRegistrationIntentToKpmAgent(String str, String str2) {
        Log.d(
                "EnterpriseLicenseService",
                "sendDeviceRegistrationIntentToKpmAgent : status : " + str);
        if ("success".equals(str)) {
            Intent intent =
                    new Intent(
                            "com.samsung.android.knox.intent.action.DEVICE_REGISTRATION_INTERNAL");
            intent.putExtra("packageName", str2);
            intent.setPackage("com.samsung.android.knox.attestation");
            intent.addFlags(32);
            this.mContext.sendBroadcastAsUser(
                    intent,
                    UserHandle.ALL,
                    "com.samsung.android.knox.permission.KNOX_DEVICE_REGISTRATION_REQUEST_INTENT_INTERNAL");
        }
    }

    public final void sendElmResult(
            int i, String str, String str2, String str3, LicenseResultRecord licenseResultRecord) {
        sendElmResult(
                "fail",
                i,
                str,
                str2,
                str3,
                null,
                -1,
                new ArrayList(),
                new ArrayList(),
                licenseResultRecord);
    }

    public final void sendElmResult(
            String str,
            int i,
            String str2,
            String str3,
            String str4,
            String str5,
            int i2,
            ArrayList arrayList,
            ArrayList arrayList2,
            LicenseResultRecord licenseResultRecord) {
        LicenseResultRecord licenseResultRecord2;
        boolean z;
        if (licenseResultRecord != null || str2 == null) {
            licenseResultRecord2 = licenseResultRecord;
        } else {
            LicenseResultRecord licenseResultRecord3 =
                    (LicenseResultRecord) ((ConcurrentHashMap) this.mElmPkgRecords).get(str2);
            if (licenseResultRecord3 != null) {
                unregisterLicenseResultRecord(str2, this.mElmPkgRecords);
            } else {
                Log.e(
                        "EnterpriseLicenseService",
                        "ELM Record not found. Caller died or race condition for ".concat(str2));
            }
            licenseResultRecord2 = licenseResultRecord3;
        }
        if (licenseResultRecord2 != null && licenseResultRecord2.callback != null) {
            z = true;
            try {
                licenseResultRecord2.callback.onLicenseResult(
                        new LicenseResult(
                                str3,
                                str,
                                i,
                                LicenseResult.Type.fromElmStatus(800),
                                arrayList,
                                licenseResultRecord2.licenseKey));
                Log.i("EnterpriseLicenseService", "ELM result sent by callback to " + str3);
            } catch (DeadObjectException e) {
                Log.e("EnterpriseLicenseService", "DeadObjectException in sendElmResult", e);
            } catch (RemoteException e2) {
                Log.e("EnterpriseLicenseService", "RemoteException in sendElmResult", e2);
            }
        }
        z = false;
        if (licenseResultRecord2 == null || licenseResultRecord2.callback == null || z) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", i);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE", 800);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP", str5);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ATTESTATION_STATUS", i2);
            intent.putStringArrayListExtra(
                    "com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS", arrayList);
            if (arrayList2 != null) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Permissions", arrayList2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.LICENSE_DATA_LICENSE_PERMISSIONS",
                        bundle);
            }
            if (str4 != null && !str4.equals(str3)) {
                intent.setPackage(str4);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.i("EnterpriseLicenseService", "ELM result sent by Intent to ".concat(str4));
            }
            intent.setPackage(str3);
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "ELM result sent by Intent to ", str3, "EnterpriseLicenseService");
        }
    }

    public final void sendKlmResult(
            int i,
            int i2,
            String str,
            String str2,
            String str3,
            LicenseResultRecord licenseResultRecord) {
        sendKlmResult(
                "fail",
                i,
                i2,
                str,
                str2,
                str3,
                -1,
                new ArrayList(),
                new ArrayList(),
                licenseResultRecord);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendKlmResult(
            java.lang.String r19,
            int r20,
            int r21,
            java.lang.String r22,
            java.lang.String r23,
            java.lang.String r24,
            int r25,
            java.util.ArrayList r26,
            java.util.ArrayList r27,
            com.android.server.enterprise.license.EnterpriseLicenseService.LicenseResultRecord
                    r28) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService.sendKlmResult(java.lang.String,"
                    + " int, int, java.lang.String, java.lang.String, java.lang.String, int,"
                    + " java.util.ArrayList, java.util.ArrayList,"
                    + " com.android.server.enterprise.license.EnterpriseLicenseService$LicenseResultRecord):void");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    public final void updateAdminPermissions() {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        try {
            ArrayList arrayList =
                    (ArrayList)
                            mEdmStorageProvider.getValuesList(
                                    "LICENSE", new String[] {"pkgName"}, null);
            if (arrayList.isEmpty()) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("pkgName");
                if (asString != null) {
                    Log.w("EnterpriseLicenseService", "updateAdminPermissions() :" + asString);
                    try {
                        mPMS.setLicensePermissionsForMDM(asString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e2) {
            Log.w("EnterpriseLicenseService", "updateAdminPermissions() failed");
            e2.printStackTrace();
        }
    }

    public final synchronized void validateLicenses$1() {
        enforcePermission$1();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("EnterpriseLicenseService", "validateLicenses to ".concat("all packages"));
            Bundle bundle = new Bundle();
            bundle.putString(
                    "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME", null);
            new Thread(new EnterpriseLicenseService$$ExternalSyntheticLambda0(this, bundle, 3))
                    .start();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
