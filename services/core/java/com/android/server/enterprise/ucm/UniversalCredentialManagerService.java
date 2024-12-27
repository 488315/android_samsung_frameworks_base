package com.android.server.enterprise.ucm;

import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
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

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.ucm.configurator.CACertificateInfo;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.core.jcajce.UcmKeystoreProvider;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import com.samsung.ucm.ucmservice.UcmServiceUtil;

import java.io.File;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class UniversalCredentialManagerService extends IUniversalCredentialManager.Stub
        implements EnterpriseServiceCallback {
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public boolean mExistCert;
    public boolean mExistWhitelist;
    public final AnonymousClass2 mPackageRemovedReceiver;
    public final PackageManager mPm;
    public final RequestIdGenerator mRIdGenerator;
    public final AnonymousClass2 mSystemReceiver;
    public final UCSMHandler mUCSMHandler;
    public static final boolean DBG = UcmServiceUtil.isDebug();
    public static final String TAG = "UniversalCredentialManagerService";
    public static Context sContext = null;
    public static final List systemPlugin =
            Arrays.asList(
                    "com.samsung.ucs.agent.boot",
                    "com.samsung.ucs.agent.ese",
                    "com.sec.smartcard.manager");
    public static IUcmService mUcseService = null;
    public EnterpriseDeviceManager mEDM = null;
    public final List adminIds = new ArrayList();
    public final HashMap expiredAdmins = new HashMap();
    public final HashMap activePluginsCache = new HashMap();
    public final HashMap whitelistedAppsCache = new HashMap();
    public final HashMap exemptedAppsCache = new HashMap();
    public UniversalCredentialUtil mUniversalCredentialUtil = null;
    public KeyguardManager mKgm = null;
    public boolean mIsSystemReceiverRegistered = false;

    public final class RequestIdGenerator {
        public int fraction;
        public Random random;
    }

    public final class UCSMHandler extends Handler {
        public UCSMHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = message.what;
            r9 = false;
            boolean z2 = false;
            int i2 = 0;
            UniversalCredentialManagerService universalCredentialManagerService =
                    UniversalCredentialManagerService.this;
            switch (i) {
                case 1:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_CLEAN_USER_INFO block started****");
                    int i3 = message.arg1;
                    Log.i(UniversalCredentialManagerService.TAG, "userId - " + i3);
                    IUcmService ucmService$1 = UniversalCredentialManagerService.getUcmService$1();
                    if (ucmService$1 != null) {
                        Log.i(
                                UniversalCredentialManagerService.TAG,
                                "notifyChangeToPlugin is called for user removed...");
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putInt("userId", i3);
                            ucmService$1.notifyChangeToPlugin((String) null, 11, bundle);
                            ucmService$1.removeEnforcedLockTypeNotification(i3);
                        } catch (Exception e) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e,
                                            new StringBuilder("The exception occurs "),
                                            UniversalCredentialManagerService.TAG);
                        }
                    }
                    UniversalCredentialManagerService.m544$$Nest$mperformUserCleanup(
                            universalCredentialManagerService, i3);
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_CLEAN_USER_INFO block ended****");
                    break;
                case 2:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_ADMINS block started****");
                    try {
                        List m535$$Nest$mgetAllAdmins =
                                UniversalCredentialManagerService.m535$$Nest$mgetAllAdmins(
                                        universalCredentialManagerService);
                        if (universalCredentialManagerService.adminIds != null) {
                            ((ArrayList) universalCredentialManagerService.adminIds)
                                    .addAll(m535$$Nest$mgetAllAdmins);
                            if (((ArrayList) universalCredentialManagerService.adminIds).size()
                                    > 0) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "adminIds size- "
                                                + ((ArrayList)
                                                                universalCredentialManagerService
                                                                        .adminIds)
                                                        .size());
                            }
                        }
                    } catch (Exception e2) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e2,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_ADMINS block ended****");
                    universalCredentialManagerService.mUCSMHandler.sendMessage(
                            universalCredentialManagerService.mUCSMHandler.obtainMessage(4));
                    break;
                case 3:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_CLEAN_INFO block started****");
                    try {
                        int[] iArr = (int[]) message.obj;
                        if (iArr != null && iArr.length > 0) {
                            for (int i4 : iArr) {
                                Integer valueOf = Integer.valueOf(i4);
                                int userId = UserHandle.getUserId(i4);
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "uid - " + valueOf + ", userId-" + userId);
                                if (((ArrayList) universalCredentialManagerService.adminIds)
                                        .contains(valueOf)) {
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "UCS admin uninstall. Start cleaning....");
                                    UniversalCredentialManagerService
                                            .m539$$Nest$mnotifyAdminUninstall(
                                                    universalCredentialManagerService, i4);
                                    UniversalCredentialManagerService
                                            .m541$$Nest$mperformAdminCleanup(
                                                    universalCredentialManagerService, i4);
                                    ((ArrayList) universalCredentialManagerService.adminIds)
                                            .remove(valueOf);
                                }
                                if (universalCredentialManagerService.activePluginsCache
                                        .containsKey(valueOf)) {
                                    String str =
                                            (String)
                                                    universalCredentialManagerService
                                                            .activePluginsCache.get(valueOf);
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "Active plugin is removed. Perform clean up for uid-"
                                                    + valueOf
                                                    + ", pluginPkg-"
                                                    + str);
                                    UniversalCredentialManagerService
                                            .m540$$Nest$mnotifyPluginIsUninstalled(
                                                    universalCredentialManagerService, str);
                                    universalCredentialManagerService.activePluginsCache.remove(
                                            valueOf);
                                    UniversalCredentialManagerService
                                            .m543$$Nest$mperformStorageCleanup(
                                                    universalCredentialManagerService, str);
                                }
                                if (universalCredentialManagerService.whitelistedAppsCache
                                        .containsKey(valueOf)) {
                                    String str2 =
                                            (String)
                                                    universalCredentialManagerService
                                                            .whitelistedAppsCache.get(valueOf);
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "Calling performWhitelistAppCleanup for userId-"
                                                    + userId
                                                    + ", packageName-"
                                                    + str2);
                                    UniversalCredentialManagerService
                                            .m545$$Nest$mperformWhitelistAppCleanup(
                                                    universalCredentialManagerService,
                                                    userId,
                                                    str2);
                                    universalCredentialManagerService.whitelistedAppsCache.remove(
                                            valueOf);
                                }
                                if (universalCredentialManagerService.exemptedAppsCache.containsKey(
                                        valueOf)) {
                                    String str3 =
                                            (String)
                                                    universalCredentialManagerService
                                                            .exemptedAppsCache.get(valueOf);
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "Calling performExemptedAppCleanup for userId-"
                                                    + userId
                                                    + ", packageName-"
                                                    + str3);
                                    UniversalCredentialManagerService
                                            .m542$$Nest$mperformExemptedAppCleanup(
                                                    universalCredentialManagerService,
                                                    userId,
                                                    str3);
                                    universalCredentialManagerService.exemptedAppsCache.remove(
                                            valueOf);
                                }
                                IUcmService ucmService$12 =
                                        UniversalCredentialManagerService.getUcmService$1();
                                if (ucmService$12 != null) {
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "notifyChangeToPlugin is called for package"
                                                + " uninstalled...");
                                    try {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putInt("userId", userId);
                                        bundle2.putInt("packageUid", i4);
                                        ucmService$12.notifyChangeToPlugin(
                                                (String) null, 12, bundle2);
                                    } catch (Exception e3) {
                                        Log.i(
                                                UniversalCredentialManagerService.TAG,
                                                "The exception occurs " + e3.getMessage());
                                    }
                                }
                            }
                        }
                        Log.i(
                                UniversalCredentialManagerService.TAG,
                                "****MSG_CLEAN_INFO block ended****");
                        break;
                    } catch (Exception e4) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e4,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                        return;
                    }
                case 4:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_PLUGINS block started****");
                    try {
                        ArrayList activePlugin =
                                universalCredentialManagerService.getActivePlugin();
                        if (activePlugin.isEmpty()) {
                            Log.i(UniversalCredentialManagerService.TAG, "No active plugin found");
                        } else {
                            Iterator it = activePlugin.iterator();
                            while (it.hasNext()) {
                                ContentValues contentValues = (ContentValues) it.next();
                                String asString = contentValues.getAsString("storagePackageName");
                                Integer asInteger = contentValues.getAsInteger("appUid");
                                if (asString != null && asInteger != null) {
                                    int intValue = asInteger.intValue();
                                    if (intValue != 1000 && intValue != 0) {
                                        try {
                                            if (!universalCredentialManagerService
                                                    .activePluginsCache.containsKey(asInteger)) {
                                                universalCredentialManagerService.activePluginsCache
                                                        .put(asInteger, asString);
                                                Log.i(
                                                        UniversalCredentialManagerService.TAG,
                                                        "Caching plugin app id-"
                                                                + intValue
                                                                + ", packageName-"
                                                                + asString);
                                            }
                                        } catch (Exception e5) {
                                            Log.i(
                                                    UniversalCredentialManagerService.TAG,
                                                    "The exception occurs " + e5.getMessage());
                                        }
                                    }
                                }
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "parsing error, continue...");
                            }
                            for (Map.Entry entry :
                                    universalCredentialManagerService.activePluginsCache
                                            .entrySet()) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "Plugin ID = "
                                                + ((Integer) entry.getKey())
                                                + ", Plugin package = "
                                                + ((String) entry.getValue()));
                            }
                        }
                    } catch (Exception e6) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e6,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_PLUGINS block ended****");
                    universalCredentialManagerService.mUCSMHandler.sendMessage(
                            universalCredentialManagerService.mUCSMHandler.obtainMessage(5));
                    break;
                case 5:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_WHITELIST_AND_EXEMPT_APPS block started****");
                    try {
                        ArrayList m538$$Nest$mgetAllWhitelistedApps =
                                UniversalCredentialManagerService.m538$$Nest$mgetAllWhitelistedApps(
                                        universalCredentialManagerService);
                        if (m538$$Nest$mgetAllWhitelistedApps.size() > 0) {
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "getAllWhitelistedApps - Size-"
                                            + m538$$Nest$mgetAllWhitelistedApps.size());
                            Iterator it2 = m538$$Nest$mgetAllWhitelistedApps.iterator();
                            while (it2.hasNext()) {
                                ContentValues contentValues2 = (ContentValues) it2.next();
                                String asString2 = contentValues2.getAsString("appPackage");
                                Integer asInteger2 = contentValues2.getAsInteger("appUid");
                                if (asString2 != null && asInteger2 != null) {
                                    int intValue2 = asInteger2.intValue();
                                    try {
                                        if (!asString2.equals("*")
                                                && intValue2 != 1000
                                                && intValue2 != 0
                                                && !universalCredentialManagerService
                                                        .whitelistedAppsCache.containsKey(
                                                        asInteger2)) {
                                            universalCredentialManagerService.whitelistedAppsCache
                                                    .put(asInteger2, asString2);
                                            Log.i(
                                                    UniversalCredentialManagerService.TAG,
                                                    "Caching WhiteList app id-"
                                                            + intValue2
                                                            + ", packageName-"
                                                            + asString2);
                                        }
                                    } catch (Exception e7) {
                                        Log.i(
                                                UniversalCredentialManagerService.TAG,
                                                "The exception occurs " + e7.getMessage());
                                    }
                                }
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "parsing error, continue...");
                            }
                            for (Map.Entry entry2 :
                                    universalCredentialManagerService.whitelistedAppsCache
                                            .entrySet()) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "WHITELIST App UID = "
                                                + ((Integer) entry2.getKey())
                                                + ", App package = "
                                                + ((String) entry2.getValue()));
                            }
                        } else {
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "getAllWhitelistedApps DB is empty...");
                        }
                        ArrayList m536$$Nest$mgetAllExemptedApps =
                                UniversalCredentialManagerService.m536$$Nest$mgetAllExemptedApps(
                                        universalCredentialManagerService);
                        if (m536$$Nest$mgetAllExemptedApps.size() > 0) {
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "getAllExemptedApps - Size-"
                                            + m536$$Nest$mgetAllExemptedApps.size());
                            Iterator it3 = m536$$Nest$mgetAllExemptedApps.iterator();
                            while (it3.hasNext()) {
                                ContentValues contentValues3 = (ContentValues) it3.next();
                                if (contentValues3 == null) {
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "value is null, continue...");
                                } else {
                                    String asString3 = contentValues3.getAsString("appPackage");
                                    Integer asInteger3 = contentValues3.getAsInteger("appUid");
                                    if (asString3 != null && asInteger3 != null) {
                                        int intValue3 = asInteger3.intValue();
                                        try {
                                            if (!asString3.equals("com.samsung.knox.virtual.wifi")
                                                    && intValue3 != 1000
                                                    && intValue3 != 0
                                                    && !universalCredentialManagerService
                                                            .exemptedAppsCache.containsKey(
                                                            asInteger3)) {
                                                universalCredentialManagerService.exemptedAppsCache
                                                        .put(asInteger3, asString3);
                                                Log.i(
                                                        UniversalCredentialManagerService.TAG,
                                                        "Caching Exempted app id-"
                                                                + intValue3
                                                                + ", packageName-"
                                                                + asString3);
                                            }
                                        } catch (Exception e8) {
                                            Log.i(
                                                    UniversalCredentialManagerService.TAG,
                                                    "The exception occurs " + e8.getMessage());
                                        }
                                    }
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "parsing error, continue...");
                                }
                            }
                            for (Map.Entry entry3 :
                                    universalCredentialManagerService.exemptedAppsCache
                                            .entrySet()) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "EXEPMT-> App UID = "
                                                + ((Integer) entry3.getKey())
                                                + ", App package = "
                                                + ((String) entry3.getValue()));
                            }
                        } else {
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "getAllExemptedApps DB is empty...");
                        }
                    } catch (Exception e9) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e9,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOAD_WHITELIST_AND_EXEMPT_APPS block ended****");
                    break;
                case 6:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_SYNC_UP_DATA block started****");
                    try {
                        Bundle bundle3 = new Bundle();
                        IUcmService ucmService$13 =
                                UniversalCredentialManagerService.getUcmService$1();
                        if (ucmService$13 != null) {
                            ucmService$13.notifyChangeToPlugin((String) null, 17, bundle3);
                            ArrayList arrayList =
                                    (ArrayList)
                                            UniversalCredentialManagerService
                                                    .m537$$Nest$mgetAllUsers(
                                                            universalCredentialManagerService);
                            if (arrayList.size() > 0) {
                                Iterator it4 =
                                        ((UserManager)
                                                        universalCredentialManagerService.mContext
                                                                .getSystemService("user"))
                                                .getUsers()
                                                .iterator();
                                while (it4.hasNext()) {
                                    int i5 = ((UserInfo) it4.next()).id;
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "Valid userid-" + i5);
                                    if (arrayList.contains(Integer.valueOf(i5))) {
                                        Log.i(
                                                UniversalCredentialManagerService.TAG,
                                                "Found userid on cache-" + i5);
                                        arrayList.remove(Integer.valueOf(i5));
                                    }
                                }
                                Iterator it5 = arrayList.iterator();
                                while (it5.hasNext()) {
                                    Integer num = (Integer) it5.next();
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "InValid userid-" + num);
                                    Message obtainMessage =
                                            universalCredentialManagerService.mUCSMHandler
                                                    .obtainMessage(1);
                                    obtainMessage.arg1 = num.intValue();
                                    universalCredentialManagerService.mUCSMHandler.sendMessage(
                                            obtainMessage);
                                }
                            }
                        }
                    } catch (Exception e10) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e10,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    ArrayList arrayList2 = new ArrayList();
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    try {
                        Iterator it6 =
                                ((ArrayList) universalCredentialManagerService.adminIds).iterator();
                        while (it6.hasNext()) {
                            Integer num2 = (Integer) it6.next();
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "MSG_SYNC_UP_DATA adminId-" + num2);
                            if (universalCredentialManagerService.mPm.getPackagesForUid(
                                            num2.intValue())
                                    != null) {
                                if (packageManager.checkUidPermission(
                                                        "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT",
                                                        num2.intValue())
                                                != 0
                                        && packageManager.checkUidPermission(
                                                        "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT",
                                                        num2.intValue())
                                                != 0
                                        && packageManager.checkUidPermission(
                                                        "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
                                                        num2.intValue())
                                                != 0) {
                                    Log.i(
                                            UniversalCredentialManagerService.TAG,
                                            "  Admin don't has UCS permission...");
                                    universalCredentialManagerService.processAdminLicenseExpiry(
                                            num2.intValue());
                                }
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "  Admin has valid permission. Processing further...");
                            } else if (!arrayList2.contains(num2)) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "MSG_SYNC_UP_DATA remove adminid : " + num2);
                                arrayList2.add(num2);
                            }
                        }
                    } catch (Exception e11) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e11,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    try {
                        for (Map.Entry entry4 :
                                universalCredentialManagerService.activePluginsCache.entrySet()) {
                            Integer num3 = (Integer) entry4.getKey();
                            String str4 = (String) entry4.getValue();
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "MSG_SYNC_UP_DATA plugin id -" + num3);
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "MSG_SYNC_UP_DATA plugin package -" + str4);
                            if (universalCredentialManagerService.mPm.getPackagesForUid(
                                                    num3.intValue())
                                            == null
                                    && !arrayList2.contains(num3)) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "MSG_SYNC_UP_DATA remove plugin : " + num3);
                                arrayList2.add(num3);
                            }
                        }
                    } catch (Exception e12) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e12,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    try {
                        Iterator it7 =
                                universalCredentialManagerService
                                        .exemptedAppsCache
                                        .entrySet()
                                        .iterator();
                        while (it7.hasNext()) {
                            Integer num4 = (Integer) ((Map.Entry) it7.next()).getKey();
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "MSG_SYNC_UP_DATA exempt app id -" + num4);
                            if (universalCredentialManagerService.mPm.getPackagesForUid(
                                                    num4.intValue())
                                            == null
                                    && !arrayList2.contains(num4)) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "MSG_SYNC_UP_DATA remove exempt app : " + num4);
                                arrayList2.add(num4);
                            }
                        }
                    } catch (Exception e13) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e13,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    try {
                        Iterator it8 =
                                universalCredentialManagerService
                                        .whitelistedAppsCache
                                        .entrySet()
                                        .iterator();
                        while (it8.hasNext()) {
                            Integer num5 = (Integer) ((Map.Entry) it8.next()).getKey();
                            Log.i(
                                    UniversalCredentialManagerService.TAG,
                                    "MSG_SYNC_UP_DATA Whitelist app id -" + num5);
                            if (universalCredentialManagerService.mPm.getPackagesForUid(
                                                    num5.intValue())
                                            == null
                                    && !arrayList2.contains(num5)) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "MSG_SYNC_UP_DATA remove Whitelist app : " + num5);
                                arrayList2.add(num5);
                            }
                        }
                    } catch (Exception e14) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e14,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    if (arrayList2.size() > 0) {
                        int[] iArr2 = new int[arrayList2.size()];
                        Iterator it9 = arrayList2.iterator();
                        while (it9.hasNext()) {
                            Integer num6 = (Integer) it9.next();
                            if (num6 == null) {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "id is null, continue...");
                            } else {
                                Log.i(
                                        UniversalCredentialManagerService.TAG,
                                        "adding clean app id-" + num6);
                                iArr2[i2] = num6.intValue();
                                i2++;
                            }
                        }
                        Message obtainMessage2 =
                                universalCredentialManagerService.mUCSMHandler.obtainMessage(3);
                        obtainMessage2.obj = iArr2;
                        Log.i(
                                UniversalCredentialManagerService.TAG,
                                "MSG_SYNC_UP_DATA calling MSG_CLEAN_INFO...");
                        universalCredentialManagerService.mUCSMHandler.sendMessage(obtainMessage2);
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_SYNC_UP_DATA block ended****");
                    break;
                case 7:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOCK_STATUS_UPDATE block started****");
                    IUcmService ucmService$14 = UniversalCredentialManagerService.getUcmService$1();
                    if (ucmService$14 != null) {
                        Log.i(
                                UniversalCredentialManagerService.TAG,
                                "notifyChangeToPlugin is called for Lock status update...");
                        boolean isKeyguardLocked =
                                ((KeyguardManager)
                                                universalCredentialManagerService.mContext
                                                        .getSystemService("keyguard"))
                                        .isKeyguardLocked();
                        try {
                            Bundle bundle4 = new Bundle();
                            bundle4.putInt("userId", 0);
                            if (isKeyguardLocked) {
                                ucmService$14.notifyChangeToPlugin((String) null, 15, bundle4);
                            } else {
                                ucmService$14.notifyChangeToPlugin((String) null, 16, bundle4);
                            }
                        } catch (Exception e15) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e15,
                                            new StringBuilder("notifyChangeToPlugin Exception "),
                                            UniversalCredentialManagerService.TAG);
                        }
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_LOCK_STATUS_UPDATE block ended****");
                    break;
                case 9:
                    IUcmService ucmService$15 = UniversalCredentialManagerService.getUcmService$1();
                    if (ucmService$15 != null) {
                        int i6 = message.arg1;
                        int i7 = message.arg2;
                        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                                i6,
                                i7,
                                "notifyChangeToPlugin is called for container Lock status update"
                                    + " containerId-",
                                ", status-",
                                UniversalCredentialManagerService.TAG);
                        try {
                            Bundle bundle5 = new Bundle();
                            bundle5.putInt("userId", i6);
                            if (i7 != 1) {
                                ucmService$15.notifyChangeToPlugin((String) null, 20, bundle5);
                            } else {
                                ucmService$15.notifyChangeToPlugin((String) null, 21, bundle5);
                            }
                            break;
                        } catch (Exception e16) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e16,
                                            new StringBuilder("The exception occurs "),
                                            UniversalCredentialManagerService.TAG);
                            return;
                        }
                    }
                    break;
                case 10:
                    String[] strArr = {"adminUid"};
                    String[] strArr2 = {String.valueOf(message.arg1)};
                    try {
                        z =
                                universalCredentialManagerService.mEdmStorageProvider
                                        .deleteDataByFields(
                                                "UniversalCredentialEnforcedLockTypeTable",
                                                strArr,
                                                strArr2);
                    } catch (Exception e17) {
                        if (UniversalCredentialManagerService.DBG) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e17,
                                            new StringBuilder(
                                                    "performPreAdminCleanup - Exception delete"
                                                        + " locktype"),
                                            UniversalCredentialManagerService.TAG);
                        }
                        z = false;
                    }
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "performPreAdminCleanup - Enforce Lock Type status- ",
                            UniversalCredentialManagerService.TAG,
                            z);
                    try {
                        z2 =
                                universalCredentialManagerService.mEdmStorageProvider
                                        .deleteDataByFields(
                                                "UniversalCredentialWhiteListTable",
                                                strArr,
                                                strArr2);
                    } catch (Exception e18) {
                        if (UniversalCredentialManagerService.DBG) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e18,
                                            new StringBuilder(
                                                    "performPreAdminCleanup - Exception delete"
                                                        + " whitelist"),
                                            UniversalCredentialManagerService.TAG);
                        }
                    }
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "performPreAdminCleanup - White List status - ",
                            UniversalCredentialManagerService.TAG,
                            z2);
                    break;
                case 11:
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_SHUTDOWN block started****");
                    try {
                        Bundle bundle6 = new Bundle();
                        IUcmService ucmService$16 =
                                UniversalCredentialManagerService.getUcmService$1();
                        if (ucmService$16 != null) {
                            ucmService$16.notifyChangeToPlugin((String) null, 22, bundle6);
                        }
                    } catch (Exception e19) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(
                                        e19,
                                        new StringBuilder("The exception occurs "),
                                        UniversalCredentialManagerService.TAG);
                    }
                    Log.i(
                            UniversalCredentialManagerService.TAG,
                            "****MSG_SHUTDOWN block ended****");
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mgetAllAdmins, reason: not valid java name */
    public static List m535$$Nest$mgetAllAdmins(
            UniversalCredentialManagerService universalCredentialManagerService) {
        universalCredentialManagerService.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it =
                universalCredentialManagerService
                        .mEdmStorageProvider
                        .getDataByFields(
                                "UniversalCredentialInfoTable",
                                null,
                                null,
                                new String[] {"adminUid"})
                        .iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues == null) {
                Log.i(TAG, "value is null, continue...");
            } else {
                Integer asInteger = contentValues.getAsInteger("adminUid");
                if (asInteger == null) {
                    Log.i(TAG, "parsing error, continue...");
                } else if (!arrayList.contains(asInteger)) {
                    arrayList.add(asInteger);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: -$$Nest$mgetAllExemptedApps, reason: not valid java name */
    public static ArrayList m536$$Nest$mgetAllExemptedApps(
            UniversalCredentialManagerService universalCredentialManagerService) {
        return universalCredentialManagerService.mEdmStorageProvider.getDataByFields(
                "UniversalCredentialExemptTable",
                null,
                null,
                new String[] {"appUid", "appPackage"});
    }

    /* renamed from: -$$Nest$mgetAllUsers, reason: not valid java name */
    public static List m537$$Nest$mgetAllUsers(
            UniversalCredentialManagerService universalCredentialManagerService) {
        universalCredentialManagerService.getClass();
        Log.i(TAG, "getAllUsers() is called...");
        ArrayList arrayList = new ArrayList();
        try {
            String[] strArr = {"userId"};
            Iterator it =
                    universalCredentialManagerService
                            .mEdmStorageProvider
                            .getDataByFields(
                                    "UniversalCredentialCertificateTable", null, null, strArr)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger = contentValues.getAsInteger("userId");
                    if (asInteger == null) {
                        Log.i(TAG, "parsing error, continue...");
                    } else if (!arrayList.contains(asInteger)) {
                        arrayList.add(asInteger);
                    }
                }
            }
            Iterator it2 =
                    universalCredentialManagerService
                            .mEdmStorageProvider
                            .getDataByFields(
                                    "UniversalCredentialWhiteListTable", null, null, strArr)
                            .iterator();
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                if (contentValues2 == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger2 = contentValues2.getAsInteger("userId");
                    if (asInteger2 == null) {
                        Log.i(TAG, "parsing error, continue...");
                    } else if (!arrayList.contains(asInteger2)) {
                        arrayList.add(asInteger2);
                    }
                }
            }
            Iterator it3 =
                    universalCredentialManagerService
                            .mEdmStorageProvider
                            .getDataByFields(
                                    "UniversalCredentialDefaultInstallTable", null, null, strArr)
                            .iterator();
            while (it3.hasNext()) {
                ContentValues contentValues3 = (ContentValues) it3.next();
                if (contentValues3 == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger3 = contentValues3.getAsInteger("userId");
                    if (asInteger3 == null) {
                        Log.i(TAG, "parsing error, continue...");
                    } else if (!arrayList.contains(asInteger3)) {
                        arrayList.add(asInteger3);
                    }
                }
            }
            Iterator it4 =
                    universalCredentialManagerService
                            .mEdmStorageProvider
                            .getDataByFields("UniversalCredentialExemptTable", null, null, strArr)
                            .iterator();
            while (it4.hasNext()) {
                ContentValues contentValues4 = (ContentValues) it4.next();
                if (contentValues4 == null) {
                    Log.i(TAG, "value is null, continue...");
                } else {
                    Integer asInteger4 = contentValues4.getAsInteger("userId");
                    if (asInteger4 == null) {
                        Log.i(TAG, "parsing error, continue...");
                    } else if (!arrayList.contains(asInteger4)) {
                        arrayList.add(asInteger4);
                    }
                }
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
        }
        return arrayList;
    }

    /* renamed from: -$$Nest$mgetAllWhitelistedApps, reason: not valid java name */
    public static ArrayList m538$$Nest$mgetAllWhitelistedApps(
            UniversalCredentialManagerService universalCredentialManagerService) {
        return universalCredentialManagerService.mEdmStorageProvider.getDataByFields(
                "UniversalCredentialWhiteListTable",
                null,
                null,
                new String[] {"appUid", "appPackage"});
    }

    /* renamed from: -$$Nest$mnotifyAdminUninstall, reason: not valid java name */
    public static void m539$$Nest$mnotifyAdminUninstall(
            UniversalCredentialManagerService universalCredentialManagerService, int i) {
        universalCredentialManagerService.getClass();
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "notifyAdminUninstall -> adminUid-", TAG);
        try {
            ArrayList dataByFields =
                    universalCredentialManagerService.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialInfoTable",
                            new String[] {"adminUid"},
                            new String[] {String.valueOf(i)},
                            new String[] {"userId", "storageName", "storagePackageName"});
            if (dataByFields.size() > 0) {
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
                            Log.i(
                                    TAG,
                                    "notifyAdminUninstall - userId-"
                                            + intValue
                                            + ", csName-"
                                            + asString
                                            + ", csPackage-"
                                            + asString2);
                            CredentialStorage credentialStorage = new CredentialStorage();
                            credentialStorage.name = asString;
                            credentialStorage.packageName = asString2;
                            universalCredentialManagerService.notifyToPlugin(
                                    i, credentialStorage, intValue);
                        }
                        Log.i(TAG, "invalid parameters, continue...");
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
    }

    /* renamed from: -$$Nest$mnotifyPluginIsUninstalled, reason: not valid java name */
    public static void m540$$Nest$mnotifyPluginIsUninstalled(
            UniversalCredentialManagerService universalCredentialManagerService, String str) {
        ArrayList arrayList =
                (ArrayList) universalCredentialManagerService.getAdminIdRelatedToStorage(str);
        if (arrayList.size() == 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "No admin found related to package : ", str, TAG);
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            String str2 = TAG;
            Log.i(str2, "notifyPluginIsUninstalled to " + num);
            String[] packagesForUid =
                    universalCredentialManagerService.mPm.getPackagesForUid(num.intValue());
            if (packagesForUid == null) {
                Log.i(str2, "cannot find admin package name of uid : " + num);
            } else {
                for (String str3 : packagesForUid) {
                    if (str3 == null) {
                        Log.i(TAG, "adminPkg is null, so continue...");
                    } else {
                        String str4 = TAG;
                        Log.i(str4, "Sending event update to package ".concat(str3));
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.knox.intent.action.UCM_NOTIFY_EVENT");
                        intent.setPackage(str3);
                        Bundle bundle = new Bundle();
                        bundle.putInt("event_id", 1);
                        bundle.putString("package_name", str);
                        intent.putExtras(bundle);
                        IPackageManager packageManager = AppGlobals.getPackageManager();
                        try {
                            if (packageManager.checkPermission(
                                            "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT",
                                            str,
                                            UserHandle.getUserId(num.intValue()))
                                    == 0) {
                                universalCredentialManagerService.mContext.sendBroadcastAsUser(
                                        intent,
                                        new UserHandle(UserHandle.getUserId(num.intValue())),
                                        "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
                            } else if (packageManager.checkPermission(
                                            "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
                                            str,
                                            UserHandle.getUserId(num.intValue()))
                                    == 0) {
                                universalCredentialManagerService.mContext.sendBroadcastAsUser(
                                        intent,
                                        new UserHandle(UserHandle.getUserId(num.intValue())),
                                        "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
                            } else {
                                Log.i(str4, "admin does not have proper UCM permission");
                            }
                            Log.i(str4, "notifyPluginIsUninstalled done");
                        } catch (Exception e) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(e, new StringBuilder("The exception occurs "), TAG);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mperformAdminCleanup, reason: not valid java name */
    public static void m541$$Nest$mperformAdminCleanup(
            UniversalCredentialManagerService universalCredentialManagerService, int i) {
        boolean z;
        universalCredentialManagerService.getClass();
        String[] strArr = {"adminUid"};
        String[] strArr2 = {String.valueOf(i)};
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup Clean certificate status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup WhiteList APP status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e3, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup Default Install status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e4, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup Certificate info status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e5, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup - Exempt apps status- ", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e6, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup - Enforce Lock Type status- ", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e7, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performAdminCleanup - Enable Lock Type status- ", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCACertificateTable", strArr, strArr2);
        } catch (Exception e8) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e8, new StringBuilder("The exception occurs "), TAG);
            }
        }
        Log.i(TAG, "performAdminCleanup - CA Cert status- " + z);
        universalCredentialManagerService.mExistCert =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialCertificateTable");
        universalCredentialManagerService.mExistWhitelist =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialWhiteListTable");
        universalCredentialManagerService.updateUcmCryptoProp();
    }

    /* renamed from: -$$Nest$mperformExemptedAppCleanup, reason: not valid java name */
    public static void m542$$Nest$mperformExemptedAppCleanup(
            UniversalCredentialManagerService universalCredentialManagerService,
            int i,
            String str) {
        boolean z;
        universalCredentialManagerService.getClass();
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialExemptTable",
                            new String[] {"userId", "appPackage"},
                            new String[] {String.valueOf(i), str});
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performExemptedAppCleanup Exempted App status-", TAG, z);
    }

    /* renamed from: -$$Nest$mperformStorageCleanup, reason: not valid java name */
    public static void m543$$Nest$mperformStorageCleanup(
            UniversalCredentialManagerService universalCredentialManagerService, String str) {
        boolean z;
        universalCredentialManagerService.getClass();
        String[] strArr = {"storagePackageName"};
        String[] strArr2 = {str};
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performStorageCleanup Clean certificate status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performStorageCleanup WhiteList APP status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e3, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performStorageCleanup Default Install status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e4, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performStorageCleanup Certificate info status-", TAG, z);
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e5, new StringBuilder("The exception occurs "), TAG);
            }
        }
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e6, new StringBuilder("The exception occurs "), TAG);
            }
        }
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e7, new StringBuilder("The exception occurs "), TAG);
            }
        }
        universalCredentialManagerService.mExistCert =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialCertificateTable");
        universalCredentialManagerService.mExistWhitelist =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialWhiteListTable");
        universalCredentialManagerService.updateUcmCryptoProp();
    }

    /* renamed from: -$$Nest$mperformUserCleanup, reason: not valid java name */
    public static void m544$$Nest$mperformUserCleanup(
            UniversalCredentialManagerService universalCredentialManagerService, int i) {
        boolean z;
        universalCredentialManagerService.getClass();
        String[] strArr = {"userId"};
        String[] strArr2 = {String.valueOf(i)};
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performUserCleanup Clean certificate status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performUserCleanup WhiteList APP status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e3, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performUserCleanup Default Install status-", TAG, z);
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialInfoTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e4, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performUserCleanup Certificate info status-", TAG, z);
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e5) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e5, new StringBuilder("The exception occurs "), TAG);
            }
        }
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialCACertificateTable", strArr, strArr2);
        } catch (Exception e6) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e6, new StringBuilder("The exception occurs "), TAG);
            }
        }
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialEnforcedLockTypeTable", strArr, strArr2);
        } catch (Exception e7) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e7, new StringBuilder("The exception occurs "), TAG);
            }
        }
        try {
            universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                    "UniversalCredentialEnabledLockTypeTable", strArr, strArr2);
        } catch (Exception e8) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e8, new StringBuilder("The exception occurs "), TAG);
            }
        }
        universalCredentialManagerService.mExistCert =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialCertificateTable");
        universalCredentialManagerService.mExistWhitelist =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialWhiteListTable");
        universalCredentialManagerService.updateUcmCryptoProp();
    }

    /* renamed from: -$$Nest$mperformWhitelistAppCleanup, reason: not valid java name */
    public static void m545$$Nest$mperformWhitelistAppCleanup(
            UniversalCredentialManagerService universalCredentialManagerService,
            int i,
            String str) {
        boolean z;
        universalCredentialManagerService.getClass();
        try {
            z =
                    universalCredentialManagerService.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialWhiteListTable",
                            new String[] {"userId", "appPackage"},
                            new String[] {String.valueOf(i), str});
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        Log.i(TAG, "performWhitelistAPpCleanup WhiteList APP status-" + z);
        universalCredentialManagerService.mExistWhitelist =
                universalCredentialManagerService.checkCountFromEdmDB(
                        "UniversalCredentialWhiteListTable");
        universalCredentialManagerService.updateUcmCryptoProp();
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.enterprise.ucm.UniversalCredentialManagerService$2] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.enterprise.ucm.UniversalCredentialManagerService$2] */
    public UniversalCredentialManagerService(Context context) {
        this.mContext = null;
        this.mEdmStorageProvider = null;
        this.mUCSMHandler = null;
        this.mRIdGenerator = null;
        this.mPm = null;
        this.mExistCert = false;
        this.mExistWhitelist = false;
        final int i = 0;
        this.mSystemReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.enterprise.ucm.UniversalCredentialManagerService.2
                    public final /* synthetic */ UniversalCredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String keyguardStorageForCurrentUser;
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                String str = UniversalCredentialManagerService.TAG;
                                Log.i(str, "inside mBReciever onReceive : " + action);
                                if (!action.equals("android.intent.action.USER_REMOVED")) {
                                    if (!action.equals(
                                            "android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                        if (!action.equals(
                                                "android.intent.action.ACTION_SHUTDOWN")) {
                                            if (!action.equals("android.intent.action.SCREEN_ON")
                                                    && !action.equals(
                                                            "android.intent.action.SCREEN_OFF")
                                                    && !action.equals(
                                                            "android.intent.action.USER_PRESENT")) {
                                                if (action.equals(
                                                        "android.intent.action.DEVICE_LOCKED_CHANGED")) {
                                                    int intExtra =
                                                            intent.getIntExtra(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                    UniversalCredentialManagerService
                                                            universalCredentialManagerService =
                                                                    this.this$0;
                                                    if (universalCredentialManagerService.mKgm
                                                            == null) {
                                                        universalCredentialManagerService.mKgm =
                                                                (KeyguardManager)
                                                                        universalCredentialManagerService
                                                                                .mContext
                                                                                .getSystemService(
                                                                                        "keyguard");
                                                    }
                                                    boolean isDeviceLocked =
                                                            universalCredentialManagerService.mKgm
                                                                    .isDeviceLocked(intExtra);
                                                    Log.i(
                                                            str,
                                                            "mLockEventReceiver. userId ["
                                                                    + intExtra
                                                                    + "] isDeviceLocked ["
                                                                    + isDeviceLocked
                                                                    + "]");
                                                    Message obtainMessage =
                                                            this.this$0.mUCSMHandler.obtainMessage(
                                                                    9);
                                                    obtainMessage.arg1 = intExtra;
                                                    obtainMessage.arg2 = !isDeviceLocked ? 1 : 0;
                                                    this.this$0.mUCSMHandler.sendMessage(
                                                            obtainMessage);
                                                    break;
                                                }
                                            } else {
                                                this.this$0.mUCSMHandler.sendMessage(
                                                        this.this$0.mUCSMHandler.obtainMessage(7));
                                                break;
                                            }
                                        } else {
                                            this.this$0.mUCSMHandler.sendMessage(
                                                    this.this$0.mUCSMHandler.obtainMessage(11));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mUCSMHandler.sendMessage(
                                                this.this$0.mUCSMHandler.obtainMessage(6));
                                        UniversalCredentialManagerService
                                                universalCredentialManagerService2 = this.this$0;
                                        universalCredentialManagerService2.getClass();
                                        Log.i(str, "showEnforcedLockTypeNotificationForAllUser ");
                                        Iterator it =
                                                ((UserManager)
                                                                universalCredentialManagerService2
                                                                        .mContext.getSystemService(
                                                                        "user"))
                                                        .getUsers()
                                                        .iterator();
                                        while (it.hasNext()) {
                                            int i2 = ((UserInfo) it.next()).id;
                                            try {
                                                CredentialStorage enforcedCredentialStorageFromDb =
                                                        universalCredentialManagerService2
                                                                .getEnforcedCredentialStorageFromDb(
                                                                        i2);
                                                IUcmService ucmService$1 =
                                                        UniversalCredentialManagerService
                                                                .getUcmService$1();
                                                if (enforcedCredentialStorageFromDb != null
                                                        && ucmService$1 != null) {
                                                    if (UniversalCredentialManagerService.DBG) {
                                                        Log.i(
                                                                UniversalCredentialManagerService
                                                                        .TAG,
                                                                "showEnforcedLockTypeNotificationForAllUser"
                                                                    + " userId: "
                                                                        + i2
                                                                        + ", cs.name: "
                                                                        + enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                    if (!enforcedCredentialStorageFromDb.name
                                                            .equalsIgnoreCase(
                                                                    ucmService$1
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    i2))) {
                                                        ucmService$1
                                                                .showEnforcedLockTypeNotification(
                                                                        i2,
                                                                        enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                        .m(
                                                                e,
                                                                new StringBuilder(
                                                                        "The exception occurs "),
                                                                UniversalCredentialManagerService
                                                                        .TAG);
                                            }
                                        }
                                        this.this$0.getClass();
                                        try {
                                            IUcmService ucmService$12 =
                                                    UniversalCredentialManagerService
                                                            .getUcmService$1();
                                            if (ucmService$12 != null
                                                    && (keyguardStorageForCurrentUser =
                                                                    ucmService$12
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    0))
                                                            != null
                                                    && !keyguardStorageForCurrentUser.isEmpty()
                                                    && !"none"
                                                            .equalsIgnoreCase(
                                                                    keyguardStorageForCurrentUser)) {
                                                SystemProperties.set(
                                                        "persist.keyguard.ucs", "true");
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                    .m(
                                                            e2,
                                                            new StringBuilder(
                                                                    "The exception occurs "),
                                                            UniversalCredentialManagerService.TAG);
                                            return;
                                        }
                                    }
                                } else {
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    DirEncryptService$$ExternalSyntheticOutline0.m(
                                            intExtra2, "ACTION_USER_REMOVED UserHandle : ", str);
                                    Message obtainMessage2 =
                                            this.this$0.mUCSMHandler.obtainMessage(1);
                                    obtainMessage2.arg1 = intExtra2;
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage2);
                                    break;
                                }
                                break;
                            case 1:
                                Message obtainMessage3 = this.this$0.mUCSMHandler.obtainMessage(3);
                                int intExtra3 = intent.getIntExtra("android.intent.extra.UID", -1);
                                boolean booleanExtra =
                                        intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false);
                                obtainMessage3.obj = new int[] {intExtra3};
                                String str2 = UniversalCredentialManagerService.TAG;
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "ACTION_PACKAGE_REMOVED : replacingApp -",
                                        str2,
                                        booleanExtra);
                                if (!booleanExtra) {
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage3);
                                    break;
                                } else {
                                    Log.i(
                                            str2,
                                            "ACTION_PACKAGE_REMOVED : No need to cleanup db entries"
                                                + " for app update");
                                    break;
                                }
                            default:
                                String str3 = UniversalCredentialManagerService.TAG;
                                Log.i(str3, "UcsReceiver intent " + intent.getAction());
                                if (intent.getAction()
                                        .equals(
                                                "com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS")) {
                                    if (intent.getExtras() == null) {
                                        Log.i(
                                                str3,
                                                "UcsReceiver no extras received from plugin....");
                                        break;
                                    } else {
                                        Bundle extras = intent.getExtras();
                                        if (extras == null) {
                                            Log.i(
                                                    str3,
                                                    "UcsReceiver no bundle extras received from"
                                                        + " plugin");
                                            break;
                                        } else {
                                            this.this$0.notifyUCMConfigStatus(extras);
                                            break;
                                        }
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mPackageRemovedReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.enterprise.ucm.UniversalCredentialManagerService.2
                    public final /* synthetic */ UniversalCredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String keyguardStorageForCurrentUser;
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                String str = UniversalCredentialManagerService.TAG;
                                Log.i(str, "inside mBReciever onReceive : " + action);
                                if (!action.equals("android.intent.action.USER_REMOVED")) {
                                    if (!action.equals(
                                            "android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                        if (!action.equals(
                                                "android.intent.action.ACTION_SHUTDOWN")) {
                                            if (!action.equals("android.intent.action.SCREEN_ON")
                                                    && !action.equals(
                                                            "android.intent.action.SCREEN_OFF")
                                                    && !action.equals(
                                                            "android.intent.action.USER_PRESENT")) {
                                                if (action.equals(
                                                        "android.intent.action.DEVICE_LOCKED_CHANGED")) {
                                                    int intExtra =
                                                            intent.getIntExtra(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                    UniversalCredentialManagerService
                                                            universalCredentialManagerService =
                                                                    this.this$0;
                                                    if (universalCredentialManagerService.mKgm
                                                            == null) {
                                                        universalCredentialManagerService.mKgm =
                                                                (KeyguardManager)
                                                                        universalCredentialManagerService
                                                                                .mContext
                                                                                .getSystemService(
                                                                                        "keyguard");
                                                    }
                                                    boolean isDeviceLocked =
                                                            universalCredentialManagerService.mKgm
                                                                    .isDeviceLocked(intExtra);
                                                    Log.i(
                                                            str,
                                                            "mLockEventReceiver. userId ["
                                                                    + intExtra
                                                                    + "] isDeviceLocked ["
                                                                    + isDeviceLocked
                                                                    + "]");
                                                    Message obtainMessage =
                                                            this.this$0.mUCSMHandler.obtainMessage(
                                                                    9);
                                                    obtainMessage.arg1 = intExtra;
                                                    obtainMessage.arg2 = !isDeviceLocked ? 1 : 0;
                                                    this.this$0.mUCSMHandler.sendMessage(
                                                            obtainMessage);
                                                    break;
                                                }
                                            } else {
                                                this.this$0.mUCSMHandler.sendMessage(
                                                        this.this$0.mUCSMHandler.obtainMessage(7));
                                                break;
                                            }
                                        } else {
                                            this.this$0.mUCSMHandler.sendMessage(
                                                    this.this$0.mUCSMHandler.obtainMessage(11));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mUCSMHandler.sendMessage(
                                                this.this$0.mUCSMHandler.obtainMessage(6));
                                        UniversalCredentialManagerService
                                                universalCredentialManagerService2 = this.this$0;
                                        universalCredentialManagerService2.getClass();
                                        Log.i(str, "showEnforcedLockTypeNotificationForAllUser ");
                                        Iterator it =
                                                ((UserManager)
                                                                universalCredentialManagerService2
                                                                        .mContext.getSystemService(
                                                                        "user"))
                                                        .getUsers()
                                                        .iterator();
                                        while (it.hasNext()) {
                                            int i22 = ((UserInfo) it.next()).id;
                                            try {
                                                CredentialStorage enforcedCredentialStorageFromDb =
                                                        universalCredentialManagerService2
                                                                .getEnforcedCredentialStorageFromDb(
                                                                        i22);
                                                IUcmService ucmService$1 =
                                                        UniversalCredentialManagerService
                                                                .getUcmService$1();
                                                if (enforcedCredentialStorageFromDb != null
                                                        && ucmService$1 != null) {
                                                    if (UniversalCredentialManagerService.DBG) {
                                                        Log.i(
                                                                UniversalCredentialManagerService
                                                                        .TAG,
                                                                "showEnforcedLockTypeNotificationForAllUser"
                                                                    + " userId: "
                                                                        + i22
                                                                        + ", cs.name: "
                                                                        + enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                    if (!enforcedCredentialStorageFromDb.name
                                                            .equalsIgnoreCase(
                                                                    ucmService$1
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    i22))) {
                                                        ucmService$1
                                                                .showEnforcedLockTypeNotification(
                                                                        i22,
                                                                        enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                        .m(
                                                                e,
                                                                new StringBuilder(
                                                                        "The exception occurs "),
                                                                UniversalCredentialManagerService
                                                                        .TAG);
                                            }
                                        }
                                        this.this$0.getClass();
                                        try {
                                            IUcmService ucmService$12 =
                                                    UniversalCredentialManagerService
                                                            .getUcmService$1();
                                            if (ucmService$12 != null
                                                    && (keyguardStorageForCurrentUser =
                                                                    ucmService$12
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    0))
                                                            != null
                                                    && !keyguardStorageForCurrentUser.isEmpty()
                                                    && !"none"
                                                            .equalsIgnoreCase(
                                                                    keyguardStorageForCurrentUser)) {
                                                SystemProperties.set(
                                                        "persist.keyguard.ucs", "true");
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                    .m(
                                                            e2,
                                                            new StringBuilder(
                                                                    "The exception occurs "),
                                                            UniversalCredentialManagerService.TAG);
                                            return;
                                        }
                                    }
                                } else {
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    DirEncryptService$$ExternalSyntheticOutline0.m(
                                            intExtra2, "ACTION_USER_REMOVED UserHandle : ", str);
                                    Message obtainMessage2 =
                                            this.this$0.mUCSMHandler.obtainMessage(1);
                                    obtainMessage2.arg1 = intExtra2;
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage2);
                                    break;
                                }
                                break;
                            case 1:
                                Message obtainMessage3 = this.this$0.mUCSMHandler.obtainMessage(3);
                                int intExtra3 = intent.getIntExtra("android.intent.extra.UID", -1);
                                boolean booleanExtra =
                                        intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false);
                                obtainMessage3.obj = new int[] {intExtra3};
                                String str2 = UniversalCredentialManagerService.TAG;
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "ACTION_PACKAGE_REMOVED : replacingApp -",
                                        str2,
                                        booleanExtra);
                                if (!booleanExtra) {
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage3);
                                    break;
                                } else {
                                    Log.i(
                                            str2,
                                            "ACTION_PACKAGE_REMOVED : No need to cleanup db entries"
                                                + " for app update");
                                    break;
                                }
                            default:
                                String str3 = UniversalCredentialManagerService.TAG;
                                Log.i(str3, "UcsReceiver intent " + intent.getAction());
                                if (intent.getAction()
                                        .equals(
                                                "com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS")) {
                                    if (intent.getExtras() == null) {
                                        Log.i(
                                                str3,
                                                "UcsReceiver no extras received from plugin....");
                                        break;
                                    } else {
                                        Bundle extras = intent.getExtras();
                                        if (extras == null) {
                                            Log.i(
                                                    str3,
                                                    "UcsReceiver no bundle extras received from"
                                                        + " plugin");
                                            break;
                                        } else {
                                            this.this$0.notifyUCMConfigStatus(extras);
                                            break;
                                        }
                                    }
                                }
                                break;
                        }
                    }
                };
        if (DBG) {
            Log.i(TAG, "Constructor");
        }
        this.mContext = context;
        sContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        UCSMHandler uCSMHandler = new UCSMHandler();
        this.mUCSMHandler = uCSMHandler;
        RequestIdGenerator requestIdGenerator = new RequestIdGenerator();
        requestIdGenerator.fraction = 0;
        requestIdGenerator.random = new Random();
        this.mRIdGenerator = requestIdGenerator;
        this.mPm = context.getPackageManager();
        if (!getActivePlugin().isEmpty()) {
            registerReceiver();
        }
        final int i3 = 2;
        context.registerReceiver(
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.enterprise.ucm.UniversalCredentialManagerService.2
                    public final /* synthetic */ UniversalCredentialManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String keyguardStorageForCurrentUser;
                        switch (i3) {
                            case 0:
                                String action = intent.getAction();
                                String str = UniversalCredentialManagerService.TAG;
                                Log.i(str, "inside mBReciever onReceive : " + action);
                                if (!action.equals("android.intent.action.USER_REMOVED")) {
                                    if (!action.equals(
                                            "android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                        if (!action.equals(
                                                "android.intent.action.ACTION_SHUTDOWN")) {
                                            if (!action.equals("android.intent.action.SCREEN_ON")
                                                    && !action.equals(
                                                            "android.intent.action.SCREEN_OFF")
                                                    && !action.equals(
                                                            "android.intent.action.USER_PRESENT")) {
                                                if (action.equals(
                                                        "android.intent.action.DEVICE_LOCKED_CHANGED")) {
                                                    int intExtra =
                                                            intent.getIntExtra(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                    UniversalCredentialManagerService
                                                            universalCredentialManagerService =
                                                                    this.this$0;
                                                    if (universalCredentialManagerService.mKgm
                                                            == null) {
                                                        universalCredentialManagerService.mKgm =
                                                                (KeyguardManager)
                                                                        universalCredentialManagerService
                                                                                .mContext
                                                                                .getSystemService(
                                                                                        "keyguard");
                                                    }
                                                    boolean isDeviceLocked =
                                                            universalCredentialManagerService.mKgm
                                                                    .isDeviceLocked(intExtra);
                                                    Log.i(
                                                            str,
                                                            "mLockEventReceiver. userId ["
                                                                    + intExtra
                                                                    + "] isDeviceLocked ["
                                                                    + isDeviceLocked
                                                                    + "]");
                                                    Message obtainMessage =
                                                            this.this$0.mUCSMHandler.obtainMessage(
                                                                    9);
                                                    obtainMessage.arg1 = intExtra;
                                                    obtainMessage.arg2 = !isDeviceLocked ? 1 : 0;
                                                    this.this$0.mUCSMHandler.sendMessage(
                                                            obtainMessage);
                                                    break;
                                                }
                                            } else {
                                                this.this$0.mUCSMHandler.sendMessage(
                                                        this.this$0.mUCSMHandler.obtainMessage(7));
                                                break;
                                            }
                                        } else {
                                            this.this$0.mUCSMHandler.sendMessage(
                                                    this.this$0.mUCSMHandler.obtainMessage(11));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mUCSMHandler.sendMessage(
                                                this.this$0.mUCSMHandler.obtainMessage(6));
                                        UniversalCredentialManagerService
                                                universalCredentialManagerService2 = this.this$0;
                                        universalCredentialManagerService2.getClass();
                                        Log.i(str, "showEnforcedLockTypeNotificationForAllUser ");
                                        Iterator it =
                                                ((UserManager)
                                                                universalCredentialManagerService2
                                                                        .mContext.getSystemService(
                                                                        "user"))
                                                        .getUsers()
                                                        .iterator();
                                        while (it.hasNext()) {
                                            int i22 = ((UserInfo) it.next()).id;
                                            try {
                                                CredentialStorage enforcedCredentialStorageFromDb =
                                                        universalCredentialManagerService2
                                                                .getEnforcedCredentialStorageFromDb(
                                                                        i22);
                                                IUcmService ucmService$1 =
                                                        UniversalCredentialManagerService
                                                                .getUcmService$1();
                                                if (enforcedCredentialStorageFromDb != null
                                                        && ucmService$1 != null) {
                                                    if (UniversalCredentialManagerService.DBG) {
                                                        Log.i(
                                                                UniversalCredentialManagerService
                                                                        .TAG,
                                                                "showEnforcedLockTypeNotificationForAllUser"
                                                                    + " userId: "
                                                                        + i22
                                                                        + ", cs.name: "
                                                                        + enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                    if (!enforcedCredentialStorageFromDb.name
                                                            .equalsIgnoreCase(
                                                                    ucmService$1
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    i22))) {
                                                        ucmService$1
                                                                .showEnforcedLockTypeNotification(
                                                                        i22,
                                                                        enforcedCredentialStorageFromDb
                                                                                .name);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                        .m(
                                                                e,
                                                                new StringBuilder(
                                                                        "The exception occurs "),
                                                                UniversalCredentialManagerService
                                                                        .TAG);
                                            }
                                        }
                                        this.this$0.getClass();
                                        try {
                                            IUcmService ucmService$12 =
                                                    UniversalCredentialManagerService
                                                            .getUcmService$1();
                                            if (ucmService$12 != null
                                                    && (keyguardStorageForCurrentUser =
                                                                    ucmService$12
                                                                            .getKeyguardStorageForCurrentUser(
                                                                                    0))
                                                            != null
                                                    && !keyguardStorageForCurrentUser.isEmpty()
                                                    && !"none"
                                                            .equalsIgnoreCase(
                                                                    keyguardStorageForCurrentUser)) {
                                                SystemProperties.set(
                                                        "persist.keyguard.ucs", "true");
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                                    .m(
                                                            e2,
                                                            new StringBuilder(
                                                                    "The exception occurs "),
                                                            UniversalCredentialManagerService.TAG);
                                            return;
                                        }
                                    }
                                } else {
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    DirEncryptService$$ExternalSyntheticOutline0.m(
                                            intExtra2, "ACTION_USER_REMOVED UserHandle : ", str);
                                    Message obtainMessage2 =
                                            this.this$0.mUCSMHandler.obtainMessage(1);
                                    obtainMessage2.arg1 = intExtra2;
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage2);
                                    break;
                                }
                                break;
                            case 1:
                                Message obtainMessage3 = this.this$0.mUCSMHandler.obtainMessage(3);
                                int intExtra3 = intent.getIntExtra("android.intent.extra.UID", -1);
                                boolean booleanExtra =
                                        intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false);
                                obtainMessage3.obj = new int[] {intExtra3};
                                String str2 = UniversalCredentialManagerService.TAG;
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                        "ACTION_PACKAGE_REMOVED : replacingApp -",
                                        str2,
                                        booleanExtra);
                                if (!booleanExtra) {
                                    this.this$0.mUCSMHandler.sendMessage(obtainMessage3);
                                    break;
                                } else {
                                    Log.i(
                                            str2,
                                            "ACTION_PACKAGE_REMOVED : No need to cleanup db entries"
                                                + " for app update");
                                    break;
                                }
                            default:
                                String str3 = UniversalCredentialManagerService.TAG;
                                Log.i(str3, "UcsReceiver intent " + intent.getAction());
                                if (intent.getAction()
                                        .equals(
                                                "com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS")) {
                                    if (intent.getExtras() == null) {
                                        Log.i(
                                                str3,
                                                "UcsReceiver no extras received from plugin....");
                                        break;
                                    } else {
                                        Bundle extras = intent.getExtras();
                                        if (extras == null) {
                                            Log.i(
                                                    str3,
                                                    "UcsReceiver no bundle extras received from"
                                                        + " plugin");
                                            break;
                                        } else {
                                            this.this$0.notifyUCMConfigStatus(extras);
                                            break;
                                        }
                                    }
                                }
                                break;
                        }
                    }
                },
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS"),
                "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE",
                null,
                2);
        uCSMHandler.sendMessage(uCSMHandler.obtainMessage(2));
        File file = new File(new File(Environment.getDataDirectory(), "system"), "ucm_ca_cert");
        File file2 = new File("/efs/sec_efs/ucm_ca_cert");
        if (!file.exists() && !file.mkdirs()) {
            Log.i(TAG, "Error!!! Cannot create root directory: " + file.getAbsolutePath());
        }
        if (!file2.exists() && !file2.mkdirs()) {
            Log.i(
                    TAG,
                    "Error!!! Cannot create root ODE CA cert directory: "
                            + file2.getAbsolutePath());
        }
        uCSMHandler.sendMessage(uCSMHandler.obtainMessage(8));
        if ("false".equals(SystemProperties.get("persist.security.ucmcrypto", "false"))) {
            this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
            this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
            updateUcmCryptoProp();
        }
    }

    public static void checkCallerPermissionFor(String str) {
        String str2 = TAG;
        Log.i(str2, "checkCallerPermissionFor is called for method-".concat(str));
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        sContext,
                        "UniversalCredentialManagerService",
                        str)
                != 0) {
            SecurityException securityException =
                    new SecurityException(
                            "Security Exception Occurred while pid["
                                    + Binder.getCallingPid()
                                    + "] with uid["
                                    + Binder.getCallingUid()
                                    + "] trying to access methodName ["
                                    + str
                                    + "] in [UniversalCredentialManagerService] service");
            if (!DBG) {
                throw securityException;
            }
            Log.i(str2, "The exception occurs " + securityException.getMessage());
            throw securityException;
        }
    }

    public static void checkKnoxCorePermission(ContextInfo contextInfo) {
        if (contextInfo == null || contextInfo.mCallerUid != 5250) {
            if (contextInfo != null) {
                Log.e(
                        TAG,
                        "checkKnoxCorePermission : caller does not have valid UCM permission :"
                            + " callerId - "
                                + contextInfo.mCallerUid);
            } else {
                Log.e(TAG, "checkKnoxCorePermission : caller does not have valid UCM permission");
            }
            throw new SecurityException("callerId is not proper");
        }
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

    public static String convertSignatureToString(Signature[] signatureArr) {
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return "";
        }
    }

    public static Signature[] convertStringToSignature(String str) {
        Signature[] signatureArr;
        if (str != null && !str.equals("")) {
            if (DBG) {
                Log.i(TAG, "convertStringToSignature providerList sig:".concat(str));
            }
            String[] split = TextUtils.split(str, ",");
            if (split == null || split.length <= 0) {
                signatureArr = null;
            } else {
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                        new StringBuilder("convertStringToSignature providerList sigStrings:"),
                        split.length,
                        TAG);
                signatureArr = new Signature[split.length];
                for (int i = 0; i < split.length; i++) {
                    String str2 = split[i];
                    if (str2 != null && str2.length() > 0) {
                        boolean z = DBG;
                        if (z) {
                            Log.i(
                                    TAG,
                                    "convertStringToSignature creating signatures : ----"
                                            + split[i]
                                            + "----");
                        }
                        try {
                            Signature signature = new Signature(split[i]);
                            if (z) {
                                Log.i(TAG, "convertStringToSignature signature :" + signature);
                            }
                            signatureArr[i] = signature;
                        } catch (Exception e) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(e, new StringBuilder("The exception occurs "), TAG);
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

    public static KnoxAnalyticsData getKAData(String str) {
        return new KnoxAnalyticsData("KNOX_UCM", 2, "API:".concat(str));
    }

    public static KnoxAnalyticsData getKAData(String str, String str2) {
        KnoxAnalyticsData kAData = getKAData(str);
        kAData.setProperty("csPackageName", str2);
        return kAData;
    }

    public static int getReturnvalue(Bundle bundle) {
        return bundle.getInt("errorresponse", -1) == 0
                ? bundle.getInt("intresponse", 0)
                : bundle.getInt("errorresponse", -1);
    }

    public static Bundle getStatusErrorBundle(int i) {
        return SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "status_code");
    }

    public static synchronized IUcmService getUcmService$1() {
        IUcmService iUcmService;
        synchronized (UniversalCredentialManagerService.class) {
            try {
                if (mUcseService == null) {
                    mUcseService =
                            IUcmService.Stub.asInterface(
                                    ServiceManager.getService("com.samsung.ucs.ucsservice"));
                }
                iUcmService = mUcseService;
            } catch (Throwable th) {
                throw th;
            }
        }
        return iUcmService;
    }

    public static String getValidString(String str) {
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0128 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0130 A[Catch: Exception -> 0x013c, TryCatch #0 {Exception -> 0x013c, blocks: (B:21:0x012a, B:23:0x0130, B:25:0x0136, B:27:0x0141), top: B:20:0x012a }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int installCertificateInProvider(
            com.samsung.android.knox.ucm.configurator.CredentialStorage r6,
            byte[] r7,
            java.lang.String r8,
            java.lang.String r9,
            android.os.Bundle r10,
            int r11,
            int r12,
            boolean r13,
            boolean r14) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.installCertificateInProvider(com.samsung.android.knox.ucm.configurator.CredentialStorage,"
                    + " byte[], java.lang.String, java.lang.String, android.os.Bundle, int, int,"
                    + " boolean, boolean):int");
    }

    public static boolean isSignatureInvalid(AppIdentity appIdentity, PackageInfo packageInfo) {
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

    public static boolean isValidAccessType(int i) {
        boolean z = i == 103 || i == 104 || i == 107;
        Log.i(TAG, "isValidAccessType type-" + i + " and status-" + z);
        return z;
    }

    public static boolean isValidAuthType(int i) {
        boolean z = true;
        if (i != 100 && i != 105) {
            z = false;
        }
        Log.i(TAG, "isValidAuthType type-" + i + " and status-" + z);
        return z;
    }

    public static boolean isValidExemptType(int i) {
        boolean z = i == 106;
        Log.i(TAG, "isValidExemptType type-" + i + " and status-" + z);
        return z;
    }

    public static boolean isValidParam(CredentialStorage credentialStorage) {
        return (credentialStorage == null
                        || TextUtils.isEmpty(credentialStorage.name)
                        || TextUtils.isEmpty(credentialStorage.packageName))
                ? false
                : true;
    }

    public static void validateContextInfo(ContextInfo contextInfo) {
        if (contextInfo == null) {
            Log.e(TAG, "contextInfo is null");
            throw new SecurityException("Input parameter is not proper");
        }
        int callingUid = Binder.getCallingUid();
        if (contextInfo.mCallerUid == callingUid
                && contextInfo.mContainerId == UserHandle.getUserId(callingUid)) {
            return;
        }
        Log.e(TAG, "Invalid contextInfo");
        throw new SecurityException("Input parameter is not proper");
    }

    public static boolean validateSignature(int i, String str, String str2) {
        String str3 = TAG;
        Log.i(str3, "validateSignature : packageName-" + str + ", userId: " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        if (str != null) {
            try {
                if (str2 != null) {
                    try {
                        PackageInfo packageInfo =
                                AppGlobals.getPackageManager().getPackageInfo(str, 64L, i);
                        if (packageInfo != null) {
                            Signature[] convertStringToSignature = convertStringToSignature(str2);
                            if (convertStringToSignature == null) {
                                Log.i(str3, "validateSignature passed String signature is invalid");
                            }
                            if (compareSignatures(
                                    packageInfo.signatures, convertStringToSignature)) {
                                Log.i(str3, "Package is installed, and signature matched...");
                                z = true;
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                    }
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final boolean addCredentialStorageLockType(
            int i, CredentialStorage credentialStorage, int i2) {
        boolean z;
        String str = credentialStorage.name;
        String str2 = credentialStorage.packageName;
        if (DBG) {
            String str3 = TAG;
            Log.i(str3, "addCredentialStorageLockType is called...");
            StringBuilder sb = new StringBuilder("addCredentialStorageLockType adminUid - ");
            sb.append(i);
            sb.append(" ContainerId - ");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(
                    i2, ", Storage Name- ", str, ", Storage Package name - ", sb);
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str2, str3);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            String str4 = credentialStorage.manufacturer;
            if (str4 != null && str4.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z =
                    this.mEdmStorageProvider.putValuesNoUpdate(
                            "UniversalCredentialEnabledLockTypeTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "addCredentialStorageLockType retcode-", TAG, z);
        return z;
    }

    public final boolean addOrUpdateDefaultInstallStorage(
            int i, CredentialStorage credentialStorage, int i2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "addOrUpdateDefaultInstallStorage is called...");
        }
        boolean z2 = false;
        if (credentialStorage == null) {
            if (z) {
                Log.i(TAG, "addOrUpdateDefaultInstallStorage - Invalid Arguments");
            }
            return false;
        }
        if (z) {
            String str = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "addOrUpdateDefaultInstallStorage adminUid - ",
                            " ContainerId - ",
                            ", Storage Name- ");
            m.append(credentialStorage.name);
            m.append(", Storage Package name - ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, credentialStorage.packageName, str);
        }
        try {
            boolean deleteDataByFields =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable",
                            new String[] {"adminUid", "userId"},
                            new String[] {String.valueOf(i), String.valueOf(i2)});
            Log.i(TAG, "addOrUpdateDefaultInstallStorage oldResult-" + deleteDataByFields);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            String str2 = credentialStorage.manufacturer;
            if (str2 != null && str2.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z2 =
                    this.mEdmStorageProvider.putValuesNoUpdate(
                            "UniversalCredentialDefaultInstallTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "addOrUpdateDefaultInstallStorage retcode-", TAG, z2);
        return z2;
    }

    public final boolean addOrUpdateEnforcedCredentialStorageLockType(
            int i, CredentialStorage credentialStorage, int i2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType is called...");
        }
        boolean z2 = false;
        if (credentialStorage == null) {
            if (z) {
                Log.i(TAG, "addOrUpdateEnforcedCredentialStorageLockType - Invalid Arguments");
            }
            return false;
        }
        if (z) {
            String str = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "addOrUpdateEnforcedCredentialStorageLockType adminUid - ",
                            " ContainerId - ",
                            ", Storage Name- ");
            m.append(credentialStorage.name);
            m.append(", Storage Package name - ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, credentialStorage.packageName, str);
        }
        try {
            boolean deleteDataByFields =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialEnforcedLockTypeTable",
                            new String[] {"adminUid", "userId"},
                            new String[] {String.valueOf(i), String.valueOf(i2)});
            Log.i(
                    TAG,
                    "addOrUpdateEnforcedCredentialStorageLockType oldResult - "
                            + deleteDataByFields);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            String str2 = credentialStorage.manufacturer;
            if (str2 != null && str2.length() > 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
            }
            z2 =
                    this.mEdmStorageProvider.putValuesNoUpdate(
                            "UniversalCredentialEnforcedLockTypeTable", contentValues);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "addOrUpdateEnforcedCredentialStorageLockType retcode-", TAG, z2);
        return z2;
    }

    public final boolean addOrUpdateSecureStorageConfig(
            int i, int i2, CredentialStorage credentialStorage, boolean z) {
        boolean z2;
        boolean z3 = DBG;
        if (z3) {
            Log.i(TAG, "addOrUpdateSecureStorageConfig is called...");
        }
        if (z3) {
            String str = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "addOrUpdateSecureStorageConfig adminUid - ",
                            " ContainerId - ",
                            ", Storage Name- ");
            m.append(credentialStorage.name);
            m.append(", Storage Package name - ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, credentialStorage.packageName, str);
        }
        boolean z4 = false;
        if (z) {
            if (z3) {
                Log.i(TAG, "addOrUpdateSecureStorageConfig - enabling CS...");
            }
            ContentValues contentValues = new ContentValues();
            Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                    i, contentValues, "adminUid", i2, "userId");
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            try {
                int packageUidAsUser =
                        this.mPm.getPackageUidAsUser(credentialStorage.packageName, i2);
                contentValues.put("appUid", Integer.valueOf(packageUidAsUser));
                if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues)
                        > 0) {
                    ContentValues contentValues2 = new ContentValues();
                    String str2 = credentialStorage.manufacturer;
                    if (str2 != null && str2.length() > 0) {
                        contentValues2.put("storageManufacture", credentialStorage.manufacturer);
                    }
                    z2 =
                            this.mEdmStorageProvider.putValues(
                                    "UniversalCredentialInfoTable", contentValues2, contentValues);
                } else {
                    String str3 = credentialStorage.manufacturer;
                    if (str3 != null && str3.length() > 0) {
                        contentValues.put("storageManufacture", credentialStorage.manufacturer);
                    }
                    boolean putValuesNoUpdate =
                            this.mEdmStorageProvider.putValuesNoUpdate(
                                    "UniversalCredentialInfoTable", contentValues);
                    if (!((ArrayList) this.adminIds).contains(Integer.valueOf(i))) {
                        ((ArrayList) this.adminIds).add(Integer.valueOf(i));
                    }
                    z2 = putValuesNoUpdate;
                }
                if (!this.activePluginsCache.containsKey(Integer.valueOf(packageUidAsUser))) {
                    Log.i(
                            TAG,
                            "addOrUpdateSecureStorageConfig - adding new plugin in cache pluginUid-"
                                    + packageUidAsUser
                                    + ",pkg-"
                                    + credentialStorage.packageName);
                    this.activePluginsCache.put(
                            Integer.valueOf(packageUidAsUser), credentialStorage.packageName);
                }
                z4 = z2;
            } catch (Exception e) {
                if (DBG) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("The exception occurs "), TAG);
                }
            }
        } else if (isCredentialStorageManagedInternal(
                i, i2, credentialStorage.name, credentialStorage.packageName)) {
            Log.i(TAG, "addOrUpdateSecureStorageConfig - Removing Credential Storage for Admin");
            try {
                z4 =
                        this.mEdmStorageProvider.deleteDataByFields(
                                "UniversalCredentialInfoTable",
                                new String[] {
                                    "adminUid", "userId", "storageName", "storagePackageName"
                                },
                                new String[] {
                                    String.valueOf(i),
                                    String.valueOf(i2),
                                    credentialStorage.name,
                                    credentialStorage.packageName
                                });
                if (z4) {
                    notifyToPlugin(i, credentialStorage, i2);
                    performCredentialStorageCleanup(i, credentialStorage, i2);
                }
            } catch (Exception e2) {
                if (DBG) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e2, new StringBuilder("The exception occurs "), TAG);
                }
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "addOrUpdateSecureStorageConfig retcode-", TAG, z4);
        return z4;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0052 A[Catch: all -> 0x006c, Exception -> 0x006f, TryCatch #0 {Exception -> 0x006f, blocks: (B:14:0x004e, B:16:0x0052, B:17:0x0071, B:19:0x0075, B:24:0x0083, B:26:0x0089, B:29:0x0096, B:32:0x00a3, B:35:0x00b0, B:37:0x00b6, B:41:0x00c9), top: B:13:0x004e, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0089 A[Catch: all -> 0x006c, Exception -> 0x006f, TRY_LEAVE, TryCatch #0 {Exception -> 0x006f, blocks: (B:14:0x004e, B:16:0x0052, B:17:0x0071, B:19:0x0075, B:24:0x0083, B:26:0x0089, B:29:0x0096, B:32:0x00a3, B:35:0x00b0, B:37:0x00b6, B:41:0x00c9), top: B:13:0x004e, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0096 A[Catch: all -> 0x006c, Exception -> 0x006f, TRY_ENTER, TryCatch #0 {Exception -> 0x006f, blocks: (B:14:0x004e, B:16:0x0052, B:17:0x0071, B:19:0x0075, B:24:0x0083, B:26:0x0089, B:29:0x0096, B:32:0x00a3, B:35:0x00b0, B:37:0x00b6, B:41:0x00c9), top: B:13:0x004e, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int addPackagesToExemptList(
            com.samsung.android.knox.ContextInfo r13,
            com.samsung.android.knox.ucm.configurator.CredentialStorage r14,
            int r15,
            java.util.List r16) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.addPackagesToExemptList(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage, int,"
                    + " java.util.List):int");
    }

    public final int addPackagesToWhiteList(
            ContextInfo contextInfo,
            CredentialStorage credentialStorage,
            List list,
            Bundle bundle) {
        boolean z;
        String str = TAG;
        Log.i(str, "addPackagesToWhiteList is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (isCallerDelegated(userId, i, credentialStorage, 107)) {
            Log.i(str, "addPackagesToWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        return addPackagesToWhiteListMain(i, userId, credentialStorage, list, bundle, z);
    }

    public final int addPackagesToWhiteListInternal(
            int i, int i2, CredentialStorage credentialStorage, List list, Bundle bundle) {
        Log.i(TAG, "addPackagesToWhiteListInternal is called....");
        checkCallerPermissionFor("addPackagesToWhiteListInternal");
        return addPackagesToWhiteListMain(i, i2, credentialStorage, list, bundle, false);
    }

    public final int addPackagesToWhiteListMain(
            int i,
            int i2,
            CredentialStorage credentialStorage,
            List list,
            Bundle bundle,
            boolean z) {
        String str;
        Log.i(TAG, "addPackagesToWhiteListMain is called....");
        try {
            KnoxAnalytics.log(getKAData("addPackagesToWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isValidParam(credentialStorage) && list != null && bundle != null) {
                    boolean z2 = DBG;
                    if (z2) {
                        Log.i(
                                TAG,
                                "addPackagesToWhiteListMain is called for Caller UID-"
                                        + i
                                        + " mContainerId "
                                        + i2);
                    }
                    String str2 = credentialStorage.signature;
                    if (str2 != null
                            && !validateSignature(i2, credentialStorage.packageName, str2)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -18;
                    }
                    if (!isPluginActive(credentialStorage)) {
                        Log.i(TAG, "Plugin is not active");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -13;
                    }
                    if (true
                                    != isCredentialStorageManagedInternal(
                                            i,
                                            i2,
                                            credentialStorage.name,
                                            credentialStorage.packageName)
                            && !z) {
                        if (z2) {
                            Log.i(TAG, "addPackagesToWhiteListMain return false..");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -12;
                    }
                    int i3 = bundle.getInt("access_type", -1);
                    if (!isValidAccessType(i3)) {
                        if (z2) {
                            Log.i(TAG, "addPackagesToWhiteListMain not passed valid access_type");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -15;
                    }
                    if (i3 == 104) {
                        String string = bundle.getString("alias");
                        String str3 = TAG;
                        Log.i(str3, "addPackagesToWhiteListMain alias-" + string);
                        if (TextUtils.isEmpty(string)) {
                            if (z2) {
                                Log.i(
                                        str3,
                                        "addPackagesToWhiteListMain alias name not provided for"
                                            + " Certificate access_type");
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -16;
                        }
                        str = string;
                        if (true
                                != checkCredentialStorageAliasForAdmin(
                                        i,
                                        i2,
                                        credentialStorage.name,
                                        credentialStorage.packageName,
                                        string)) {
                            if (z2) {
                                Log.i(str3, "- alias not exist for credential storage...");
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -14;
                        }
                    } else {
                        str = null;
                    }
                    if (z) {
                        if (i3 == 103) {
                            Log.i(
                                    TAG,
                                    "addPackagesToWhiteListMain Delegated app can't WhiteList"
                                        + " storage.. error");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -30;
                        }
                        if (i3 == 107) {
                            Log.i(
                                    TAG,
                                    "addPackagesToWhiteListMain Delegated app can't further"
                                        + " delegate.. error");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -29;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            AppIdentity appIdentity = (AppIdentity) it.next();
                            if (z && isPackageDelegated(appIdentity.getPackageName())) {
                                Log.i(
                                        TAG,
                                        "addPackagesToWhiteListMain ..app is already delegated by"
                                            + " other app.. error");
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return -29;
                            }
                        }
                    }
                    int insertOrUpdateWhiteListPackages =
                            insertOrUpdateWhiteListPackages(
                                    credentialStorage, list, i, i2, i3, str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return insertOrUpdateWhiteListPackages;
                }
                if (DBG) {
                    Log.i(TAG, "addPackagesToWhiteListMain - Invalid Arguments");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -11;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int changeKeyguardPin(
            ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) {
        Log.i(TAG, "changeKeyguardPin is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, true);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("changeKeyguardPin", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "changeKeyguardPin is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.changePin(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build(),
                                str,
                                str2));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int checkCS(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        try {
            if (isValidParam(credentialStorage)) {
                return !isCredentialStorageManagedInternal(
                                contextInfo.mCallerUid,
                                contextInfo.mContainerId,
                                credentialStorage.name,
                                credentialStorage.packageName)
                        ? -12
                        : 0;
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public final int checkContext(
            ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
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

    public final boolean checkCountFromEdmDB(String str) {
        if (!"UniversalCredentialWhiteListTable".equals(str)
                && !"UniversalCredentialCertificateTable".equals(str)) {
            Log.i(TAG, "Input param is undefined flag, can't update flag");
            return false;
        }
        try {
            return this.mEdmStorageProvider.getCount(str, null) > 0;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean checkCredentialStorageAliasForAdmin(
            int i, int i2, String str, String str2, String str3) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "checkCredentialStorageAliasForAdmin");
        }
        if (z) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "AdminId - ", ", UserId - ", ", storageName - "),
                    str,
                    TAG);
        }
        try {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = str;
            credentialStorage.packageName = str2;
            String[] aliasesInternal = getAliasesInternal(i, credentialStorage, i2);
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
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        return false;
    }

    public final boolean checkCredentialStorageEnabledForLockTypebyAdmin(
            int i, CredentialStorage credentialStorage, int i2) {
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
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialEnabledLockTypeTable", contentValues)
                    <= 0) {
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean checkCredentialStorageLockTypeEnforced(int i, String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "checkCredentialStorageLockTypeEnforced");
        }
        if (z) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i,
                            "checkCredentialStorageLockTypeEnforced UserId - ",
                            ", storageName - ",
                            str,
                            " and storagePackageName-"),
                    str2,
                    TAG);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialEnforcedLockTypeTable", contentValues)
                    <= 0) {
                return false;
            }
            if (!z) {
                return true;
            }
            Log.i(TAG, "checkCredentialStorageLockTypeEnforced Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean checkCredentialStorageLockTypeEnforcedForAdmin(
            int i, int i2, String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "checkCredentialStorageLockTypeEnforcedForAdmin");
        }
        if (z) {
            String str3 = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "AdminId - ", ", UserId - ", ", storageName - ");
            m.append(str);
            m.append(" and ");
            Log.i(str3, m.toString());
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
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialEnforcedLockTypeTable", contentValues)
                    <= 0) {
                return false;
            }
            if (!z) {
                return true;
            }
            Log.i(TAG, "checkCredentialStorageLockTypeEnforcedForAdmin Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean checkDefaultInstallCredentialStorageExists(
            int i, String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "checkDefaultInstallCredentialStorageExists");
        }
        if (z) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i,
                            "checkDefaultInstallCredentialStorageExists UserId - ",
                            ", storageName - ",
                            str,
                            " and storagePackageName-"),
                    str2,
                    TAG);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", str);
            contentValues.put("storagePackageName", str2);
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialDefaultInstallTable", contentValues)
                    <= 0) {
                return false;
            }
            if (!z) {
                return true;
            }
            Log.i(TAG, "checkDefaultInstallCredentialStorageExists Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean checkDefaultInstallCredentialStorageExistsForAdmin(
            int i, int i2, String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "checkDefaultInstallCredentialStorageExistsForAdmin");
        }
        if (z) {
            String str3 = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "AdminId - ", ", UserId - ", ", storageName - ");
            m.append(str);
            m.append(" and ");
            Log.i(str3, m.toString());
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
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialDefaultInstallTable", contentValues)
                    <= 0) {
                return false;
            }
            if (!z) {
                return true;
            }
            Log.i(TAG, "checkDefaultInstallCredentialStorageExistsForAdmin Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00bc, code lost:

       android.util.Log.i(r14, "getDelegatorUid delegator found..");
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c2, code lost:

       r7 = r12;
    */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0104 A[Catch: Exception -> 0x0122, TRY_ENTER, TryCatch #2 {Exception -> 0x0122, blocks: (B:37:0x00f6, B:40:0x0104, B:43:0x010c, B:45:0x0112, B:47:0x011c, B:50:0x0124, B:52:0x012e, B:56:0x0134, B:58:0x013e, B:60:0x0148), top: B:36:0x00f6 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0134 A[Catch: Exception -> 0x0122, TryCatch #2 {Exception -> 0x0122, blocks: (B:37:0x00f6, B:40:0x0104, B:43:0x010c, B:45:0x0112, B:47:0x011c, B:50:0x0124, B:52:0x012e, B:56:0x0134, B:58:0x013e, B:60:0x0148), top: B:36:0x00f6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkDelegatorPermission(
            int r11,
            int r12,
            com.samsung.android.knox.ucm.configurator.CredentialStorage r13,
            java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.checkDelegatorPermission(int,"
                    + " int, com.samsung.android.knox.ucm.configurator.CredentialStorage,"
                    + " java.lang.String):boolean");
    }

    public final int clearWhiteList(
            ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        boolean z;
        boolean z2;
        String str;
        int i;
        String str2;
        String str3 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str3, "clearWhiteList is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str3, "clearWhiteList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("clearWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        if (isCallerDelegated(userId, i2, credentialStorage, 107)) {
            Log.i(TAG, "clearWhiteList caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i2 = contextInfo.mCallerUid;
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = DBG;
                if (z2) {
                    Log.i(
                            TAG,
                            "clearWhiteList is called for Caller UID-"
                                    + i2
                                    + " mContainerId "
                                    + userId);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true
                            != isCredentialStorageManagedInternal(
                                    i2,
                                    userId,
                                    credentialStorage.name,
                                    credentialStorage.packageName)
                    && !z) {
                if (z2) {
                    Log.i(TAG, "clearWhiteList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            int i3 = bundle.getInt("access_type", -1);
            if (!isValidAccessType(i3)) {
                if (z2) {
                    Log.i(TAG, "clearWhiteList not passed valid access_type");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            if (i3 == 104) {
                String string = bundle.getString("alias");
                String str4 = TAG;
                Log.i(str4, "clearWhiteList alias-" + string);
                if (TextUtils.isEmpty(string)) {
                    if (z2) {
                        Log.i(
                                str4,
                                "clearWhiteList alias name not provided for Certificate"
                                    + " access_type");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -16;
                }
                i = i3;
                if (true
                        != checkCredentialStorageAliasForAdmin(
                                i2,
                                userId,
                                credentialStorage.name,
                                credentialStorage.packageName,
                                string)) {
                    if (z2) {
                        Log.i(str4, "clearWhiteList - alias not exist for credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
                str2 = string;
            } else {
                i = i3;
                str2 = null;
            }
            if (true == clearWhiteListPackages(i2, userId, i, credentialStorage, str2)) {
                this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
                updateUcmCryptoProp();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean clearWhiteListPackages(
            int i, int i2, int i3, CredentialStorage credentialStorage, String str) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "clearWhiteListPackages is called...");
        }
        if (z) {
            String str2 = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "adminId - ", " ContainerId - ", " Storage name - ");
            m.append(credentialStorage.name);
            m.append(" Storage Package - ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, credentialStorage.packageName, str2);
        }
        boolean z2 = false;
        if (str == null) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i3, "clearWhiteListPackages access_type-", TAG);
            try {
                z2 =
                        this.mEdmStorageProvider.deleteDataByFields(
                                "UniversalCredentialWhiteListTable",
                                new String[] {
                                    "adminUid",
                                    "userId",
                                    "storageName",
                                    "storagePackageName",
                                    "accessType"
                                },
                                new String[] {
                                    String.valueOf(i),
                                    String.valueOf(i2),
                                    credentialStorage.name,
                                    credentialStorage.packageName,
                                    String.valueOf(i3)
                                });
            } catch (Exception e) {
                if (DBG) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("clearWhiteListPackages - Exception delete"), TAG);
                }
            }
        } else {
            Log.i(TAG, "removeWhiteListPackages access_type-" + i3 + " and alias-" + str);
            try {
                z2 =
                        this.mEdmStorageProvider.deleteDataByFields(
                                "UniversalCredentialWhiteListTable",
                                new String[] {
                                    "adminUid",
                                    "userId",
                                    "storageName",
                                    "storagePackageName",
                                    "accessType",
                                    "alias"
                                },
                                new String[] {
                                    String.valueOf(i),
                                    String.valueOf(i2),
                                    credentialStorage.name,
                                    credentialStorage.packageName,
                                    String.valueOf(i3),
                                    str
                                });
            } catch (Exception e2) {
                if (DBG) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e2,
                            new StringBuilder("clearWhiteListPackages - Exception delete"),
                            TAG);
                }
            }
        }
        Log.i(TAG, "clearWhiteListPackages retcode-" + z2);
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
        return z2;
    }

    public final int configureCredentialStorageForODESettings(
            ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        boolean z;
        String build;
        String str = TAG;
        Log.i(str, "configureCredentialStorageForODESettings is called....");
        validateContextInfo(contextInfo);
        enforceActiveAdminPermission(contextInfo);
        if (!isValidParam(credentialStorage)) {
            if (!DBG) {
                return -11;
            }
            Log.i(str, "configureCredentialStorageForODESettings - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(
                    getKAData(
                            "configureCredentialStorageForODESettings",
                            credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = DBG;
                if (z) {
                    Log.i(
                            TAG,
                            "configureCredentialStorageForODESettings is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + userId);
                }
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (userId != 0) {
                Log.i(TAG, "This API is only valid for User 0");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
            if (credentialStorage != null) {
                build =
                        new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                .setResourceId(4)
                                .setUid(i)
                                .build();
                String str2 = credentialStorage.signature;
                if (str2 != null
                        && !validateSignature(userId, credentialStorage.packageName, str2)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Storage is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                if (true
                        != isCredentialStorageManagedInternal(
                                i, userId, credentialStorage.name, credentialStorage.packageName)) {
                    if (z) {
                        Log.i(TAG, "configureCredentialStorageForODESettings return false..");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -12;
                }
            } else {
                build =
                        new UniversalCredentialUtil.UcmUriBuilder("reset")
                                .setResourceId(4)
                                .setUid(i)
                                .build();
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                Log.i(
                        TAG,
                        "configureCredentialStorageForODESettings is called for plugin"
                            + " unmanaged...");
                int configureODESettings =
                        ucmService$1.configureODESettings(
                                build,
                                bundle,
                                credentialStorage != null ? credentialStorage.signature : null);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return configureODESettings;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int configureCredentialStoragePlugin(
            ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        return configureCredentialStoragePlugin(contextInfo, credentialStorage, bundle, true);
    }

    public final int configureCredentialStoragePlugin(
            ContextInfo contextInfo,
            CredentialStorage credentialStorage,
            Bundle bundle,
            boolean z) {
        String str;
        Bundle bundle2 = bundle;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo,
                credentialStorage,
                str2,
                "configureCredentialStoragePlugin is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str2, "configureCredentialStoragePlugin - Invalid Arguments");
            return -11;
        }
        if (z) {
            if (bundle2 != null
                    && bundle2.containsKey("applet_location")
                    && bundle2.getString("applet_location") == null) {
                enforceActiveAdminPermission(contextInfo);
            } else {
                enforceSecurityPermission(contextInfo, credentialStorage);
            }
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(str2, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            boolean z2 = DBG;
            if (z2) {
                Log.i(
                        str2,
                        "configureCredentialStoragePlugin is called for Caller UID-"
                                + i
                                + " userId "
                                + userId);
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z2) {
                    Log.i(str2, "configureCredentialStoragePlugin return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                if (credentialStorage.name.equalsIgnoreCase(
                                "com.samsung.ucs.agent.ese:eSE Credential Storage")
                        && credentialStorage.packageName.equalsIgnoreCase(
                                "com.samsung.ucs.agent.ese")) {
                    Log.i(str2, "Adding install flag for ESE applet");
                    if (bundle2 == null) {
                        bundle2 = new Bundle();
                    }
                    bundle2.putInt("installAppletUsingLCCM", 1);
                }
                Bundle bundle3 = bundle2;
                getEnforcedCredentialStorageFromDb(userId);
                Log.i(str2, "configureCredentialStoragePlugin - pass to agent...");
                String build =
                        new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                .setResourceId(4)
                                .setUid(i)
                                .build();
                RequestIdGenerator requestIdGenerator = this.mRIdGenerator;
                int i2 = requestIdGenerator.fraction + 1;
                requestIdGenerator.fraction = i2;
                if (i2 > 10) {
                    requestIdGenerator.fraction = 1;
                }
                int nextInt =
                        (requestIdGenerator.random.nextInt(90001) + 10000)
                                * requestIdGenerator.fraction;
                Bundle adminConfigureBundleForCs =
                        ucmService$1.setAdminConfigureBundleForCs(
                                i, userId, build, bundle3, nextInt);
                int i3 =
                        adminConfigureBundleForCs != null
                                ? adminConfigureBundleForCs.getInt("intresponse", -1)
                                : -1;
                int i4 =
                        adminConfigureBundleForCs != null
                                ? adminConfigureBundleForCs.getInt("errorresponse", -1)
                                : -1;
                Log.i(
                        str2,
                        "configureCredentialStoragePlugin - requestId -"
                                + nextInt
                                + " and retCode-"
                                + i3);
                return i3 == 0 ? nextInt : i4;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int configureWpcDar(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "configureWpcDar")) {
            return -11;
        }
        checkKnoxCorePermission(contextInfo);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        boolean z = DBG;
        if (z) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, userId, "configureWpcDar is called for Caller UID-", " mContainerId ", str2);
        }
        if (userId != 0) {
            Log.e(str2, "This API is only valid for User 0");
            return -1;
        }
        try {
            str = credentialStorage.signature;
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
        }
        if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
            return -18;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.e(str2, "Storage is not active");
            return -13;
        }
        if (!isCredentialStorageManagedInternal(
                i, userId, credentialStorage.name, credentialStorage.packageName)) {
            if (!z) {
                return -12;
            }
            Log.e(str2, "configureWpcDar return false..");
            return -12;
        }
        IUcmService ucmService$1 = getUcmService$1();
        if (ucmService$1 != null) {
            String build =
                    new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                            .setResourceId(4)
                            .setUid(i)
                            .build();
            Log.i(str2, "configureWPCDARFlag is called for plugin...");
            return ucmService$1.configureWPCDARFlag(build, credentialStorage.signature);
        }
        return -1;
    }

    public final int deleteCACertificate(ContextInfo contextInfo, String str) {
        Log.i(TAG, "deleteCACertificate is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("deleteCACertificate"));
            return -1;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
            return -1;
        }
    }

    public final int deleteCertificate(
            ContextInfo contextInfo, CredentialStorage credentialStorage, String str) {
        String str2 = TAG;
        Log.i(str2, "deleteCertificate is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (isCallerDelegated(userId, i, credentialStorage, 107)) {
            Log.i(str2, "deleteCertificate caller is valid delegated app...");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
        }
        return deleteCertificateMain(i, userId, credentialStorage, str);
    }

    public final int deleteCertificateInternal(
            int i, int i2, CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "deleteCertificateInternal is called....");
        checkCallerPermissionFor("deleteCertificateInternal");
        return deleteCertificateMain(i, i2, credentialStorage, str);
    }

    public final int deleteCertificateMain(
            int i, int i2, CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "deleteCertificateMain is called....");
        try {
            KnoxAnalytics.log(getKAData("deleteCertificate", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (isValidParam(credentialStorage) && str != null && str.length() != 0) {
                boolean z = DBG;
                if (z) {
                    Log.i(
                            TAG,
                            "deleteCertificateMain is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + i2);
                }
                String str2 = credentialStorage.signature;
                if (str2 != null && !validateSignature(i2, credentialStorage.packageName, str2)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Plugin is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                String validString = getValidString(str);
                if (z) {
                    Log.i(TAG, "deleteCertificateMain userId-" + i2 + " and adminId-" + i);
                }
                if (true
                        != checkCredentialStorageAliasForAdmin(
                                i,
                                i2,
                                credentialStorage.name,
                                credentialStorage.packageName,
                                validString)) {
                    if (z) {
                        Log.i(TAG, "- alias not exist for credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
                int removeCertificatefromProvider =
                        removeCertificatefromProvider(
                                i2,
                                i,
                                credentialStorage.name,
                                credentialStorage.packageName,
                                validString);
                if (removeCertificatefromProvider != 0) {
                    if (z) {
                        Log.i(TAG, "deleteCertificateMain - removeCertificatefromProvider failed");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return removeCertificatefromProvider;
                }
                if (true == deleteCertificateUsingAdminId(i, i2, credentialStorage, validString)) {
                    if (z) {
                        Log.i(TAG, "- certificate deleted successfully...");
                    }
                    this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
                    updateUcmCryptoProp();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
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
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean deleteCertificateUsingAdminId(
            int i, int i2, CredentialStorage credentialStorage, String str) {
        if (DBG) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "deleteCertificateUsingAdminId is called for alias-", str, TAG);
        }
        boolean z = false;
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCertificateTable",
                            new String[] {
                                "adminUid", "userId", "storageName", "storagePackageName", "alias"
                            },
                            new String[] {
                                String.valueOf(i),
                                String.valueOf(i2),
                                credentialStorage.name,
                                credentialStorage.packageName,
                                str
                            });
            String str2 = TAG;
            Log.i(str2, "deleteCertificateUsingAdminId is successful for alias-" + str);
            if (z) {
                Log.i(
                        str2,
                        "deleteCertificateUsingAdminId remove whitelist status-"
                                + clearWhiteListPackages(i, i2, 104, credentialStorage, str));
            }
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("deleteCertificateUsingAdminId - Exception"), TAG);
            }
        }
        return z;
    }

    public final int enableCredentialStorageForLockType(
            ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        boolean z2;
        Log.i(TAG, "enableCredentialStorageForLockType is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            KnoxAnalyticsData kAData =
                    getKAData("enableCredentialStorageForLockType", credentialStorage.packageName);
            kAData.setProperty("enable", z);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        String str = credentialStorage.signature;
        if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
            return -18;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return -13;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                z2 = DBG;
                if (z2) {
                    Log.i(
                            TAG,
                            "enableCredentialStorageForLockType is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + userId);
                }
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (!isValidParam(credentialStorage)) {
                if (z2) {
                    Log.i(
                            TAG,
                            "enableCredentialStorageForLockType Invalid credential storage object"
                                + " passed...");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z2) {
                    Log.i(TAG, "enableCredentialStorageForLockType return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            Provider credentialStorageProvider =
                    getCredentialStorageProvider(
                            credentialStorage.name, credentialStorage.packageName);
            if (credentialStorageProvider == null) {
                if (z2) {
                    Log.i(
                            TAG,
                            "enableCredentialStorageForLockType No matching managed Credential"
                                + " Storage found in Provider list");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            String property = credentialStorageProvider.getProperty("isGeneratePasswordAvailable");
            String str2 = TAG;
            Log.i(
                    str2,
                    "enableCredentialStorageForLockType isGeneratePasswordAvailable-" + property);
            if ("false".equals(property)) {
                if (z2) {
                    Log.i(
                            str2,
                            "enableCredentialStorageForLockType Generate Password not supported by"
                                + " Provider");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -27;
            }
            if (z2) {
                Log.i(
                        str2,
                        "enableCredentialStorageForLockType Generate Password supported by"
                            + " Provider");
            }
            i2 = enableCredentialStorageForLockTypeInternal(i, userId, credentialStorage, z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int enableCredentialStorageForLockTypeInternal(
            int i, int i2, CredentialStorage credentialStorage, boolean z) {
        try {
            if (checkCredentialStorageEnabledForLockTypebyAdmin(-1, credentialStorage, i2)) {
                if (checkCredentialStorageEnabledForLockTypebyAdmin(i, credentialStorage, i2)) {
                    if (z) {
                        Log.i(
                                TAG,
                                "enableCredentialStorageForLockTypeInternal record already"
                                    + " exist...");
                        return 0;
                    }
                    if (removeCredentialStorageLockType(i, credentialStorage, i2)) {
                        return 0;
                    }
                } else if (z) {
                    Log.i(
                            TAG,
                            "enableCredentialStorageForLockTypeInternal record already exist for"
                                + " another admin. Enabling for current Admin too...");
                    if (addCredentialStorageLockType(i, credentialStorage, i2)) {
                        return 0;
                    }
                } else if (DBG) {
                    Log.i(
                            TAG,
                            "enableCredentialStorageForLockTypeInternal Credential storage not"
                                + " enabled as a Lock type by this admin..");
                }
            } else if (z) {
                Log.i(
                        TAG,
                        "enableCredentialStorageForLockTypeInternal enabling lock type for current"
                            + " Admin ...");
                if (addCredentialStorageLockType(i, credentialStorage, i2)) {
                    return 0;
                }
            } else if (DBG) {
                Log.i(
                        TAG,
                        "enableCredentialStorageForLockTypeInternal Credential storage not enabled"
                            + " as a Lock type by this admin..");
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
        }
        return -1;
    }

    public final void enforceActiveAdminPermission(ContextInfo contextInfo) {
        Log.i(TAG, "Enforcing UCM PRIVILEGED permission");
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT");
        arrayList.add("com.samsung.android.knox.permission.KNOX_UCM_MGMT");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, arrayList);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:(18:90|91|(1:93)|(1:9)|10|11|(2:13|(2:19|(2:21|22))(1:17))|23|24|25|(1:27)|28|(2:30|(3:(1:33)|34|35)(2:36|(2:38|(3:(1:41)|42|43)(1:(1:45)))(3:(1:63)|64|65)))(1:(2:67|(2:69|(3:(1:72)|73|74)))(3:(1:76)|77|78))|46|47|(2:57|(1:61))(1:(1:51)(2:54|(1:56)))|52|53)|24|25|(0)|28|(0)(0)|46|47|(0)|57|(1:61)|52|53) */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00a1, code lost:

       r11 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01a2, code lost:

       android.util.Log.i(com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG, "The exception occurs " + r11.getMessage());
    */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0084 A[Catch: all -> 0x009e, Exception -> 0x00a1, TryCatch #2 {Exception -> 0x00a1, blocks: (B:25:0x0080, B:27:0x0084, B:28:0x00a4, B:30:0x00aa, B:33:0x00b6, B:36:0x00c4, B:38:0x00ce, B:41:0x00f1, B:45:0x00ff, B:46:0x0134, B:51:0x0145, B:54:0x0161, B:56:0x0167, B:61:0x0175, B:63:0x0108, B:67:0x0116, B:69:0x011c, B:72:0x0128, B:76:0x0196), top: B:24:0x0080, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00aa A[Catch: all -> 0x009e, Exception -> 0x00a1, TryCatch #2 {Exception -> 0x00a1, blocks: (B:25:0x0080, B:27:0x0084, B:28:0x00a4, B:30:0x00aa, B:33:0x00b6, B:36:0x00c4, B:38:0x00ce, B:41:0x00f1, B:45:0x00ff, B:46:0x0134, B:51:0x0145, B:54:0x0161, B:56:0x0167, B:61:0x0175, B:63:0x0108, B:67:0x0116, B:69:0x011c, B:72:0x0128, B:76:0x0196), top: B:24:0x0080, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0114  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int enforceCredentialStorageAsLockType(
            com.samsung.android.knox.ContextInfo r12,
            com.samsung.android.knox.ucm.configurator.CredentialStorage r13,
            android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.enforceCredentialStorageAsLockType(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage,"
                    + " android.os.Bundle):int");
    }

    public final int enforceCredentialStorageAsLockTypeInternal(
            int i, CredentialStorage credentialStorage, int i2) {
        try {
            if (credentialStorage == null) {
                String str = TAG;
                Log.i(
                        str,
                        "enforceCredentialStorageAsLockTypeInternal cs is null so removing admin"
                            + " entry...");
                boolean deleteDataByFields =
                        this.mEdmStorageProvider.deleteDataByFields(
                                "UniversalCredentialEnforcedLockTypeTable",
                                new String[] {"adminUid", "userId"},
                                new String[] {String.valueOf(i), String.valueOf(i2)});
                Log.i(
                        str,
                        "enforceCredentialStorageAsLockTypeInternal result-" + deleteDataByFields);
                return deleteDataByFields ? 0 : -1;
            }
            if (!checkCredentialStorageLockTypeEnforced(
                    i2, credentialStorage.name, credentialStorage.packageName)) {
                return addOrUpdateEnforcedCredentialStorageLockType(i, credentialStorage, i2)
                        ? 0
                        : -1;
            }
            if (checkCredentialStorageLockTypeEnforcedForAdmin(
                    i, i2, credentialStorage.name, credentialStorage.packageName)) {
                Log.i(TAG, "enforceCredentialStorageAsLockTypeInternal record already exist...");
                return 10;
            }
            if (checkCredentialStorageLockTypeEnforcedForAdmin(i, i2, null, null)) {
                Log.i(
                        TAG,
                        "enforceCredentialStorageAsLockTypeInternal Another Credential storage is"
                            + " already configured by some other admin");
                return addOrUpdateEnforcedCredentialStorageLockType(i, credentialStorage, i2)
                        ? 0
                        : -1;
            }
            Log.i(
                    TAG,
                    "enforceCredentialStorageAsLockTypeInternal Credential storage is configured by"
                        + " some other admin");
            return -10;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final void enforceSecurityPermission(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        if (this.mEDM.isCallerValidKPU(contextInfo)) {
            enforceActiveAdminPermission(contextInfo);
            return;
        }
        ArrayList m =
                PortStatus_1_1$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
        if (credentialStorage != null) {
            String str = credentialStorage.name;
            String str2 = credentialStorage.packageName;
            if (str == null || str2 == null) {
                Log.i(TAG, "Input parameter is not proper");
                throw new SecurityException("Input parameter is not proper");
            }
            if (isSystemStorage(str2)) {
                Log.i(TAG, "Enforcing ESE permission");
                m.add("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT");
            } else {
                Log.i(TAG, "Enforcing OTHER permission");
                m.add("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
            }
        } else {
            Log.i(TAG, "Check if caller has some UCM permission...");
            m.add("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT");
            m.add("com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
        }
        int i = contextInfo.mCallerUid;
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforcePermissionByContext(contextInfo, m);
        if (contextInfo.mCallerUid != i) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "enforceSecurityPermission : oldCallerId = ", " newCallerId = "),
                    contextInfo.mCallerUid,
                    TAG);
        }
        Log.i(TAG, "enforceSecurityPermission : caller has valid UCM permission");
    }

    public final boolean existAliasInternal(
            int i, CredentialStorage credentialStorage, String str) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "existAliasInternal");
        }
        String[] certificateAliasesAsUser = getCertificateAliasesAsUser(i, credentialStorage);
        if (certificateAliasesAsUser == null) {
            if (z) {
                Log.i(TAG, "storedAliases is emapty");
            }
            return false;
        }
        for (String str2 : certificateAliasesAsUser) {
            if (str2 != null) {
                if (true == str2.equals(str)) {
                    if (DBG) {
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                "exist alias : ", str, TAG);
                    }
                    return true;
                }
            } else if (DBG) {
                Log.i(TAG, "dbAlias is null");
            }
        }
        return false;
    }

    public final ArrayList getActivePlugin() {
        Log.i(TAG, "getActivePlugin ..");
        try {
            return this.mEdmStorageProvider.getDataByFields(
                    "UniversalCredentialInfoTable",
                    null,
                    null,
                    new String[] {"storagePackageName", "appUid"});
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            return new ArrayList();
        }
    }

    public final int getAdminForEnforcedCredentialStorageAsUser(int i) {
        String str = TAG;
        Log.i(str, "getAdminForEnforcedCredentialStorageAsUser is called....");
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "getAdminForEnforcedCredentialStorageAsUser is called for userId ", str);
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

    public final int getAdminForEnforcedCredentialStorageFromDb(int i) {
        ArrayList dataByFields =
                this.mEdmStorageProvider.getDataByFields(
                        "UniversalCredentialEnforcedLockTypeTable",
                        new String[] {"userId"},
                        new String[] {String.valueOf(i)},
                        new String[] {"adminUid"});
        if (dataByFields.size() <= 0) {
            return -1;
        }
        ContentValues contentValues = (ContentValues) dataByFields.get(0);
        if (contentValues == null) {
            Log.i(TAG, "parsing object error");
            return -1;
        }
        Integer asInteger = contentValues.getAsInteger("adminUid");
        if (asInteger != null) {
            return asInteger.intValue();
        }
        Log.i(TAG, "parsing integer error");
        return -1;
    }

    public final List getAdminIdRelatedToStorage(String str) {
        Log.i(TAG, "getAdminIdRelatedToStorage stroragePackage-" + str);
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialInfoTable",
                            new String[] {"storagePackageName"},
                            new String[] {str},
                            new String[] {"adminUid"});
            if (dataByFields.size() > 0) {
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
                            arrayList.add(asInteger);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        return arrayList;
    }

    public final List getAdminIdRelatedToStorageAsUser(int i, CredentialStorage credentialStorage) {
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialInfoTable",
                            new String[] {"userId", "storageName", "storagePackageName"},
                            new String[] {
                                String.valueOf(i),
                                credentialStorage.name,
                                credentialStorage.packageName
                            },
                            new String[] {"adminUid"});
            if (dataByFields.size() > 0) {
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
                            arrayList.add(asInteger);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        return arrayList;
    }

    public final String[] getAliases(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "getAliases is called....")) {
            if (DBG) {
                Log.i(str, "getAliases - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(getKAData("getAliases", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (isCallerDelegated(userId, i, credentialStorage, 107)) {
            Log.i(TAG, "getAliases caller is valid delegated app...");
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
        }
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, userId, "getAliases is called for Caller UID-", " mContainerId ", TAG);
        }
        String str2 = credentialStorage.signature;
        if (str2 != null && !validateSignature(userId, credentialStorage.packageName, str2)) {
            return null;
        }
        if (isPluginActive(credentialStorage)) {
            return getAliasesInternal(i, credentialStorage, userId);
        }
        Log.i(TAG, "Plugin is not active");
        return null;
    }

    public final String[] getAliasesInternal(int i, CredentialStorage credentialStorage, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String[] strArr = null;
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialCertificateTable",
                                new String[] {
                                    "adminUid", "userId", "storageName", "storagePackageName"
                                },
                                new String[] {
                                    String.valueOf(i),
                                    String.valueOf(i2),
                                    credentialStorage.name,
                                    credentialStorage.packageName
                                },
                                new String[] {"alias"});
                if (dataByFields.size() > 0) {
                    strArr = new String[dataByFields.size()];
                    Iterator it = dataByFields.iterator();
                    int i3 = 0;
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

    public final String[] getAllCertificateAliases(CredentialStorage credentialStorage) {
        checkCallerPermissionFor("getAllCertificateAliases");
        String str = TAG;
        Log.i(str, "getAllCertificateAliases is called....");
        String[] strArr = null;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(str, "getAllCertificateAliases - Invalid Arguments");
            }
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialCertificateTable",
                                new String[] {"storageName", "storagePackageName"},
                                new String[] {
                                    credentialStorage.name, credentialStorage.packageName
                                },
                                new String[] {"adminUid", "alias"});
                if (dataByFields.size() > 0) {
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

    public final int getAuthType(ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "getAuthType is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str, "getAuthType - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(getKAData("getAuthType", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String str2 = credentialStorage.signature;
                if (str2 != null
                        && !validateSignature(userId, credentialStorage.packageName, str2)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -18;
                }
                if (!isPluginActive(credentialStorage)) {
                    Log.i(TAG, "Storage is not active");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -13;
                }
                boolean z = DBG;
                if (z) {
                    Log.i(
                            TAG,
                            "getAuthType is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + userId);
                }
                if (true
                        == isCredentialStorageManagedInternal(
                                i, userId, credentialStorage.name, credentialStorage.packageName)) {
                    return getStorageAuthenticationType(userId, credentialStorage);
                }
                if (z) {
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

    public final CredentialStorage[] getAvailableCredentialStorages(ContextInfo contextInfo) {
        return getAvailableCredentialStorages(contextInfo, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.ucm.configurator.CredentialStorage[]
            getAvailableCredentialStorages(com.samsung.android.knox.ContextInfo r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 566
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.getAvailableCredentialStorages(com.samsung.android.knox.ContextInfo,"
                    + " boolean):com.samsung.android.knox.ucm.configurator.CredentialStorage[]");
    }

    public final CACertificateInfo getCACertificate(ContextInfo contextInfo, String str) {
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("getCACertificate"));
            return null;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
            return null;
        }
    }

    public final String[] getCACertificateAliases(ContextInfo contextInfo, Bundle bundle) {
        Log.i(TAG, "getCACertificateAliases is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0188  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] getCertificateAliases(
            int r19, com.samsung.android.knox.ucm.configurator.CredentialStorage r20) {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.getCertificateAliases(int,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage):java.lang.String[]");
    }

    public final String[] getCertificateAliasesAsUser(int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("getCertificateAliasesAsUser");
        String str = TAG;
        Log.i(str, "getCertificateAliasesAsUser is called....");
        String[] strArr = null;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(str, "getCertificateAliasesAsUser - Invalid Arguments");
            }
            return null;
        }
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "getCertificateAliasesAsUser is called for mContainerId ", str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialCertificateTable",
                                new String[] {"userId", "storageName", "storagePackageName"},
                                new String[] {
                                    String.valueOf(i),
                                    credentialStorage.name,
                                    credentialStorage.packageName
                                },
                                new String[] {"adminUid", "alias"});
                if (dataByFields.size() > 0) {
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
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return strArr;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final Bundle getCredentialStoragePluginConfiguration(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo,
                credentialStorage,
                str2,
                "getCredentialStoragePluginConfiguration is called....")) {
            if (DBG) {
                Log.i(str2, "getCredentialStoragePluginConfiguration - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(
                    getKAData(
                            "getCredentialStoragePluginConfiguration",
                            credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                return null;
            }
            boolean z = DBG;
            if (z) {
                Log.i(
                        TAG,
                        "getCredentialStoragePluginConfiguration is called for Caller UID-"
                                + i
                                + " userId "
                                + userId);
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z) {
                    Log.i(TAG, "getCredentialStoragePluginConfiguration return null");
                }
                return null;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                Log.i(TAG, "getCredentialStoragePluginConfiguration - pass to agent...");
                return ucmService$1.getAdminConfigureBundleFromCs(
                        i,
                        userId,
                        new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                .setResourceId(4)
                                .setUid(i)
                                .build());
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bundle getCredentialStorageProperty(
            ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "getPackageSetting is called....")) {
            if (DBG) {
                Log.i(str2, "getCredentialStorageProperty - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("getCredentialStorageProperty", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                return null;
            }
            boolean z = DBG;
            if (z) {
                Log.i(
                        TAG,
                        "getCredentialStorageProperty is called for mCallerUid- "
                                + i
                                + " user- "
                                + userId);
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z) {
                    Log.i(TAG, "setPackageSetting return false..");
                }
                return null;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                Log.i(TAG, "getCredentialStorageProperty - pass to agent...");
                return ucmService$1.getCredentialStorageProperty(
                        i,
                        new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                .setResourceId(4)
                                .setUid(i)
                                .build(),
                        bundle,
                        userId);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Provider getCredentialStorageProvider(String str, String str2) {
        try {
            if (this.mUniversalCredentialUtil == null) {
                this.mUniversalCredentialUtil = UniversalCredentialUtil.getInstance();
            }
            if (this.mUniversalCredentialUtil == null) {
                Log.i(
                        TAG,
                        "getCredentialStorageProperties - UniversalCredentialUtil service is"
                            + " null.... ");
                return null;
            }
            Log.i(TAG, "getCredentialStorageProperties name-" + str + " and pkg-" + str2);
            Provider[] managedProviders = getManagedProviders();
            if (managedProviders == null || managedProviders.length <= 0) {
                return null;
            }
            for (Provider provider : managedProviders) {
                String name = provider.getName();
                String property = provider.getProperty("packageName");
                if (name != null && property != null) {
                    if (property.equals(str2)) {
                        if (str != null && !name.equals(str)) {}
                        Log.i(TAG, "getCredentialStorageProperties match found...");
                        return provider;
                    }
                    continue;
                }
                Log.i(TAG, "CredentialStorage or AGENT_PACKAGENAME is null");
            }
            return null;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return null;
        }
    }

    public final CredentialStorage[] getCredentialStorages(ContextInfo contextInfo, String str) {
        String str2 = TAG;
        Log.i(str2, "getCredentialStorages is called....");
        validateContextInfo(contextInfo);
        if (str == null || str.length() == 0) {
            if (DBG) {
                Log.i(str2, "getCredentialStorages - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalyticsData kAData = getKAData("getCredentialStorages");
            kAData.setProperty("packageName", str);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        HashMap hashMap = new HashMap();
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, userId, "getCredentialStorages is called for Caller UID-", " userId ", TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialWhiteListTable",
                                new String[] {"adminUid", "userId"},
                                new String[] {String.valueOf(i), String.valueOf(userId)},
                                new String[] {
                                    "storageName",
                                    "storagePackageName",
                                    "storageManufacture",
                                    "appPackage"
                                });
                if (dataByFields.size() > 0) {
                    Log.i(TAG, "getCredentialStorages - Size-" + dataByFields.size());
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        String asString = contentValues.getAsString("appPackage");
                        String str3 = TAG;
                        Log.i(str3, "getCredentialStorages dbPackage-" + asString);
                        if (asString != null) {
                            if (!asString.equalsIgnoreCase(str)
                                    && !asString.equalsIgnoreCase("*")) {}
                            Log.i(str3, "getCredentialStorages match found...");
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
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
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
    }

    public final CredentialStorage getDefaultInstallStorage(ContextInfo contextInfo) {
        String str = TAG;
        Log.i(str, "getDefaultInstallStorage is called....");
        validateContextInfo(contextInfo);
        CredentialStorage credentialStorage = null;
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i,
                    userId,
                    "getDefaultInstallStorage is called for Caller UID-",
                    " userId ",
                    str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialDefaultInstallTable",
                                new String[] {"userId"},
                                new String[] {String.valueOf(userId)},
                                new String[] {
                                    "storageName", "storagePackageName", "storageManufacture"
                                });
                if (dataByFields.size() > 0) {
                    CredentialStorage credentialStorage2 = new CredentialStorage();
                    try {
                        credentialStorage2.name =
                                ((ContentValues) dataByFields.get(0)).getAsString("storageName");
                        credentialStorage2.packageName =
                                ((ContentValues) dataByFields.get(0))
                                        .getAsString("storagePackageName");
                        credentialStorage2.manufacturer =
                                ((ContentValues) dataByFields.get(0))
                                        .getAsString("storageManufacture");
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

    public final CredentialStorage getDefaultInstallStorageAsUser(int i) {
        checkCallerPermissionFor("getDefaultInstallStorageAsUser");
        String str = TAG;
        Log.i(str, "getDefaultInstallStorageAsUser is called....");
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "getDefaultInstallStorageAsUser is called for userId ", str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        CredentialStorage credentialStorage = null;
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialDefaultInstallTable",
                                new String[] {"userId"},
                                new String[] {String.valueOf(i)},
                                new String[] {
                                    "storageName", "storagePackageName", "storageManufacture"
                                });
                if (dataByFields.size() > 0) {
                    CredentialStorage credentialStorage2 = new CredentialStorage();
                    try {
                        credentialStorage2.name =
                                ((ContentValues) dataByFields.get(0)).getAsString("storageName");
                        credentialStorage2.packageName =
                                ((ContentValues) dataByFields.get(0))
                                        .getAsString("storagePackageName");
                        credentialStorage2.manufacturer =
                                ((ContentValues) dataByFields.get(0))
                                        .getAsString("storageManufacture");
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

    public final CredentialStorage getEnforcedCredentialStorageForLockType(
            ContextInfo contextInfo) {
        String str = TAG;
        Log.i(str, "getEnforcedCredentialStorageForLockType is called....");
        validateContextInfo(contextInfo);
        CredentialStorage credentialStorage = null;
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i,
                    userId,
                    "getEnforcedCredentialStorageForLockType is called for Caller UID-",
                    " mContainerId ",
                    str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                credentialStorage = getEnforcedCredentialStorageFromDb(userId);
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

    public final CredentialStorage getEnforcedCredentialStorageForLockTypeAsUser(int i) {
        checkCallerPermissionFor("getEnforcedCredentialStorageForLockTypeAsUser");
        String str = TAG;
        Log.i(str, "getEnforcedCredentialStorageForLockTypeAsUser is called....");
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "getEnforcedCredentialStorageForLockTypeAsUser is called for userId ", str);
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

    public final CredentialStorage getEnforcedCredentialStorageFromDb(int i) {
        ArrayList dataByFields =
                this.mEdmStorageProvider.getDataByFields(
                        "UniversalCredentialEnforcedLockTypeTable",
                        new String[] {"userId"},
                        new String[] {String.valueOf(i)},
                        new String[] {"storageName", "storagePackageName", "storageManufacture"});
        if (dataByFields.size() <= 0) {
            return null;
        }
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ((ContentValues) dataByFields.get(0)).getAsString("storageName");
        credentialStorage.packageName =
                ((ContentValues) dataByFields.get(0)).getAsString("storagePackageName");
        credentialStorage.manufacturer =
                ((ContentValues) dataByFields.get(0)).getAsString("storageManufacture");
        return credentialStorage;
    }

    public final int getKeyguardPinCurrentRetryCount(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinCurrentRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("getKeyguardPinCurrentRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "getKeyguardPinCurrentRetryCount is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.getKeyguardPinCurrentRetryCount(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build()));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int getKeyguardPinMaximumLength(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMaximumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("getKeyguardPinMaximumLength", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "getKeyguardPinMaximumLength is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.getKeyguardPinMaximumLength(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build()));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int getKeyguardPinMaximumRetryCount(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMaximumRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("getKeyguardPinMaximumRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "getKeyguardPinMaximumRetryCount is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.getKeyguardPinMaximumRetryCount(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build()));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int getKeyguardPinMinimumLength(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "getKeyguardPinMinimumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, false);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("getKeyguardPinMinimumLength", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "getKeyguardPinMinimumLength is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.getKeyguardPinMinimumLength(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build()));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final Provider[] getManagedProviders() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IUcmService ucmService$1 = getUcmService$1();
                if (ucmService$1 != null) {
                    Bundle[] listAllProviders = ucmService$1.listAllProviders();
                    if (listAllProviders == null || listAllProviders.length == 0) {
                        if (DBG) {
                            Log.i(TAG, "Provider list is empty");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : listAllProviders) {
                        if (this.mUniversalCredentialUtil == null) {
                            this.mUniversalCredentialUtil = UniversalCredentialUtil.getInstance();
                        }
                        String string = bundle.getString("uniqueId");
                        if (string == null) {
                            Log.i(TAG, "NULL agent ID name Returned for bundle");
                        } else {
                            arrayList.add(new UcmKeystoreProvider(string, bundle));
                        }
                    }
                    Provider[] providerArr =
                            (Provider[]) arrayList.toArray(new Provider[arrayList.size()]);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return providerArr;
                }
            } catch (Exception e) {
                Log.w(TAG, "The exception occurs " + e.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final Bundle getODESettingsConfiguration(ContextInfo contextInfo) {
        String str = TAG;
        Log.i(str, "getODESettingsConfiguration is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, null);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(
                            str,
                            "getODESettingsConfiguration is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + userId);
                }
                IUcmService ucmService$1 = getUcmService$1();
                if (ucmService$1 != null) {
                    Log.i(str, "getODESettingsConfiguration is called for plugin unmanaged...");
                    Bundle oDESettingsConfiguration = ucmService$1.getODESettingsConfiguration();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return oDESettingsConfiguration;
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final List getPackagesFromExemptList(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        boolean z;
        String str;
        String str2 = TAG;
        ArrayList arrayList = null;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "getPackagesFromExemptList is called....")) {
            if (DBG) {
                Log.i(str2, "getPackagesFromExemptList - Invalid Arguments");
            }
            return null;
        }
        try {
            KnoxAnalyticsData kAData =
                    getKAData("getPackagesFromExemptList", credentialStorage.packageName);
            kAData.setProperty("authType", i == 106 ? "AUTH" : "OTHER");
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = DBG;
                if (z) {
                    Log.i(
                            TAG,
                            "getPackagesFromExemptList is called for Caller UID-"
                                    + i2
                                    + " mContainerId "
                                    + userId
                                    + ",type-"
                                    + i);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                e = e2;
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i2, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z) {
                    Log.i(TAG, "getPackagesFromExemptList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            if (!isValidExemptType(i)) {
                Log.i(TAG, "getPackagesFromExemptList - Invalid Exempt Type...");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialExemptTable",
                            new String[] {
                                "adminUid",
                                "userId",
                                "storageName",
                                "storagePackageName",
                                "exemptType"
                            },
                            new String[] {
                                String.valueOf(i2),
                                String.valueOf(userId),
                                credentialStorage.name,
                                credentialStorage.packageName,
                                String.valueOf(i)
                            },
                            new String[] {"appPackage", "appSignature"});
            if (dataByFields.size() > 0) {
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
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return arrayList;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final List getPackagesFromExemptListAsUser(
            int i, CredentialStorage credentialStorage, int i2) {
        String str = TAG;
        Log.i(str, "getPackagesFromExemptListAsUser is called....");
        ArrayList arrayList = null;
        try {
            if (!isValidParam(credentialStorage)) {
                if (DBG) {
                    Log.i(str, "getPackagesFromExemptListAsUser - Invalid Arguments");
                }
                return null;
            }
            if (DBG) {
                Log.i(
                        str,
                        "getPackagesFromExemptListAsUser is called for Container-"
                                + i
                                + ",type-"
                                + i2);
            }
            if (!isValidExemptType(i2)) {
                Log.i(str, "getPackagesFromExemptListAsUser - Invalid Exempt Type...");
                return null;
            }
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialExemptTable",
                            new String[] {
                                "userId", "storageName", "storagePackageName", "exemptType"
                            },
                            new String[] {
                                String.valueOf(i),
                                credentialStorage.name,
                                credentialStorage.packageName,
                                String.valueOf(i2)
                            },
                            new String[] {"adminUid", "appPackage", "appSignature"});
            if (dataByFields.size() <= 0) {
                return null;
            }
            Log.i(str, "getPackagesFromExemptListAsUser - Size-" + dataByFields.size());
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
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
                return arrayList;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x01e0 A[Catch: all -> 0x008a, TRY_LEAVE, TryCatch #3 {all -> 0x008a, blocks: (B:11:0x006c, B:75:0x0070, B:13:0x0090, B:15:0x0094, B:20:0x00a0, B:22:0x00a6, B:25:0x00b1, B:29:0x00bf, B:32:0x00cb, B:35:0x00da, B:40:0x00ea, B:43:0x0109, B:46:0x0113, B:49:0x0132, B:53:0x0148, B:55:0x0187, B:57:0x019f, B:58:0x01a3, B:60:0x01a9, B:68:0x01dc, B:70:0x01e0, B:71:0x01d1), top: B:10:0x006c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getPackagesFromWhiteList(
            com.samsung.android.knox.ContextInfo r21,
            com.samsung.android.knox.ucm.configurator.CredentialStorage r22,
            android.os.Bundle r23) {
        /*
            Method dump skipped, instructions count: 524
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.getPackagesFromWhiteList(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage,"
                    + " android.os.Bundle):java.util.List");
    }

    public final List getPackagesFromWhiteListAsUser(
            int i, CredentialStorage credentialStorage, Bundle bundle) {
        String str = TAG;
        Log.i(str, "getPackagesFromWhiteListAsUser is called....");
        if (isValidParam(credentialStorage) && bundle != null) {
            if (DBG) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "getPackagesFromWhiteListAsUser is called for mContainerId ", str);
            }
            return getPackagesFromWhiteListInternal(i, credentialStorage, bundle);
        }
        if (!DBG) {
            return null;
        }
        Log.i(str, "getPackagesFromWhiteListAsUser - Invalid Arguments");
        return null;
    }

    public final List getPackagesFromWhiteListInternal(
            int i, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        String str2 = TAG;
        Log.i(str2, "getPackagesFromWhiteListInternal is called....");
        ArrayList arrayList = null;
        try {
            int i2 = bundle.getInt("access_type", -1);
            if (!isValidAccessType(i2)) {
                if (DBG) {
                    Log.i(str2, "getPackagesFromWhiteListInternal not passed valid access_type");
                }
                return null;
            }
            if (i2 == 104) {
                str = bundle.getString("alias");
                Log.i(str2, "getPackagesFromWhiteListInternal alias-" + str);
                if (TextUtils.isEmpty(str)) {
                    if (DBG) {
                        Log.i(
                                str2,
                                "getPackagesFromWhiteListInternal alias name not provided for"
                                    + " Certificate access_type");
                    }
                    return null;
                }
            } else {
                str = null;
            }
            StringBuilder sb = new StringBuilder("getPackagesFromWhiteListInternal mContainerId-");
            sb.append(i);
            sb.append(",name-");
            sb.append(credentialStorage != null ? credentialStorage.name : "");
            sb.append(",package-");
            sb.append(credentialStorage != null ? credentialStorage.packageName : "");
            sb.append(", accessType-");
            sb.append(i2);
            Log.i(str2, sb.toString());
            ArrayList dataByFields =
                    str == null
                            ? credentialStorage != null
                                    ? this.mEdmStorageProvider.getDataByFields(
                                            "UniversalCredentialWhiteListTable",
                                            new String[] {
                                                "userId",
                                                "storageName",
                                                "storagePackageName",
                                                "accessType"
                                            },
                                            new String[] {
                                                String.valueOf(i),
                                                credentialStorage.name,
                                                credentialStorage.packageName,
                                                String.valueOf(i2)
                                            },
                                            new String[] {"adminUid", "appPackage", "appSignature"})
                                    : this.mEdmStorageProvider.getDataByFields(
                                            "UniversalCredentialWhiteListTable",
                                            new String[] {"userId", "accessType"},
                                            new String[] {String.valueOf(i), String.valueOf(i2)},
                                            new String[] {"adminUid", "appPackage", "appSignature"})
                            : this.mEdmStorageProvider.getDataByFields(
                                    "UniversalCredentialWhiteListTable",
                                    new String[] {
                                        "userId",
                                        "storageName",
                                        "storagePackageName",
                                        "accessType",
                                        "alias"
                                    },
                                    new String[] {
                                        String.valueOf(i),
                                        credentialStorage.name,
                                        credentialStorage.packageName,
                                        String.valueOf(i2),
                                        str
                                    },
                                    new String[] {"adminUid", "appPackage", "appSignature"});
            if (dataByFields.size() <= 0) {
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
                        String str3 = TAG;
                        Log.i(
                                str3,
                                "getPackagesFromWhiteListInternal APP PKG-"
                                        + contentValues.getAsString("appPackage"));
                        if (DBG) {
                            Log.i(
                                    str3,
                                    "getPackagesFromWhiteListInternal APP PKG-"
                                            + contentValues.getAsString("appSignature"));
                        }
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        if (asInteger == null) {
                            Log.i(str3, "parsing error, continue...");
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
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
                return arrayList;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0098, code lost:

       android.util.Log.i(com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG, "getStorageAuthenticationType - found the strictest value...");
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a0, code lost:

       r3 = r8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getStorageAuthenticationType(
            int r8, com.samsung.android.knox.ucm.configurator.CredentialStorage r9) {
        /*
            r7 = this;
            java.lang.String r0 = "storageAuthType"
            java.lang.String r1 = "getStorageAuthenticationType"
            checkCallerPermissionFor(r1)
            java.lang.String r1 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG
            java.lang.String r2 = "getStorageAuthenticationType is called...."
            android.util.Log.i(r1, r2)
            boolean r2 = isValidParam(r9)
            if (r2 != 0) goto L23
            boolean r7 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.DBG
            if (r7 == 0) goto L21
            java.lang.String r7 = "getStorageAuthenticationType - Invalid Arguments"
            android.util.Log.i(r1, r7)
        L21:
            r7 = -1
            return r7
        L23:
            boolean r2 = r7.isPluginActive(r9)
            if (r2 != 0) goto L31
            java.lang.String r7 = "Plugin is not active"
            android.util.Log.i(r1, r7)
            r7 = -13
            return r7
        L31:
            long r1 = android.os.Binder.clearCallingIdentity()
            r3 = 105(0x69, float:1.47E-43)
            java.lang.String r4 = "userId"
            java.lang.String r5 = "storageName"
            java.lang.String r6 = "storagePackageName"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5, r6}     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r5 = r9.name     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r9 = r9.packageName     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String[] r8 = new java.lang.String[]{r8, r5, r9}     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String[] r9 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            com.android.server.enterprise.storage.EdmStorageProvider r7 = r7.mEdmStorageProvider     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r5 = "UniversalCredentialInfoTable"
            java.util.ArrayList r7 = r7.getDataByFields(r5, r4, r8, r9)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            int r8 = r7.size()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            if (r8 <= 0) goto La1
            java.util.Iterator r7 = r7.iterator()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
        L66:
            boolean r8 = r7.hasNext()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            if (r8 == 0) goto La1
            java.lang.Object r8 = r7.next()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            android.content.ContentValues r8 = (android.content.ContentValues) r8     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            if (r8 != 0) goto L81
            java.lang.String r8 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r9 = "value is null, continue..."
            android.util.Log.i(r8, r9)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            goto L66
        L7d:
            r7 = move-exception
            goto Lc5
        L7f:
            r7 = move-exception
            goto La5
        L81:
            java.lang.Integer r8 = r8.getAsInteger(r0)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            if (r8 != 0) goto L90
            java.lang.String r8 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r9 = "parsing error, continue..."
            android.util.Log.i(r8, r9)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            goto L66
        L90:
            int r8 = r8.intValue()     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            r9 = 100
            if (r8 != r9) goto L66
            java.lang.String r7 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            java.lang.String r9 = "getStorageAuthenticationType - found the strictest value..."
            android.util.Log.i(r7, r9)     // Catch: java.lang.Throwable -> L7d java.lang.Exception -> L7f
            r3 = r8
        La1:
            android.os.Binder.restoreCallingIdentity(r1)
            goto Lc4
        La5:
            boolean r8 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.DBG     // Catch: java.lang.Throwable -> L7d
            if (r8 == 0) goto La1
            java.lang.String r8 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG     // Catch: java.lang.Throwable -> L7d
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d
            r9.<init>()     // Catch: java.lang.Throwable -> L7d
            java.lang.String r0 = "The exception occurs "
            r9.append(r0)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> L7d
            r9.append(r7)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r7 = r9.toString()     // Catch: java.lang.Throwable -> L7d
            android.util.Log.i(r8, r7)     // Catch: java.lang.Throwable -> L7d
            goto La1
        Lc4:
            return r3
        Lc5:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.getStorageAuthenticationType(int,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage):int");
    }

    public final String[] getSupportedAlgorithms(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Set<Provider.Service> services;
        String str = TAG;
        String[] strArr = null;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "getSupportedAlgorithms is called....")) {
            if (DBG) {
                Log.i(str, "getSupportedAlgorithms - Invalid Arguments");
            }
            return null;
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        enforceSecurityPermission(contextInfo, credentialStorage);
        try {
            KnoxAnalytics.log(getKAData("getSupportedAlgorithms", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, userId, "getSupportedAlgorithms is called for Caller UID-", " userId ", TAG);
        }
        String str2 = credentialStorage.signature;
        if (str2 != null && !validateSignature(userId, credentialStorage.packageName, str2)) {
            return null;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(TAG, "Plugin is not active");
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (this.mUniversalCredentialUtil == null) {
                    this.mUniversalCredentialUtil = UniversalCredentialUtil.getInstance();
                }
                if (this.mUniversalCredentialUtil != null) {
                    Provider[] managedProviders = getManagedProviders();
                    if (managedProviders == null || managedProviders.length <= 0) {
                        Log.i(
                                TAG,
                                "getSupportedAlgorithmsInternal - UniversalCredentialUtil service"
                                    + " returns no providers... ");
                    } else {
                        for (Provider provider : managedProviders) {
                            String name = provider.getName();
                            String property = provider.getProperty("packageName");
                            if (name != null
                                    && name.equals(credentialStorage.name)
                                    && property != null
                                    && property.equals(credentialStorage.packageName)
                                    && (services = provider.getServices()) != null
                                    && services.size() > 0) {
                                strArr = new String[services.size()];
                                Iterator<Provider.Service> it = services.iterator();
                                int i2 = 0;
                                while (it.hasNext()) {
                                    strArr[i2] = it.next().getAlgorithm();
                                    i2++;
                                }
                            }
                        }
                    }
                } else {
                    Log.i(
                            TAG,
                            "getSupportedAlgorithmsInternal - UniversalCredentialUtil service is"
                                + " null.... ");
                }
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return strArr;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final ArrayList getWhitelistedData(
            int i, int i2, int i3, CredentialStorage credentialStorage, String str) {
        if (str == null) {
            return this.mEdmStorageProvider.getDataByFields(
                    "UniversalCredentialWhiteListTable",
                    new String[] {
                        "adminUid", "userId", "storageName", "storagePackageName", "accessType"
                    },
                    new String[] {
                        String.valueOf(i),
                        String.valueOf(i2),
                        credentialStorage.name,
                        credentialStorage.packageName,
                        String.valueOf(i3)
                    },
                    new String[] {"appPackage", "appSignature"});
        }
        return this.mEdmStorageProvider.getDataByFields(
                "UniversalCredentialWhiteListTable",
                new String[] {
                    "adminUid", "userId", "storageName", "storagePackageName", "accessType", "alias"
                },
                new String[] {
                    String.valueOf(i),
                    String.valueOf(i2),
                    credentialStorage.name,
                    credentialStorage.packageName,
                    String.valueOf(i3),
                    str
                },
                new String[] {"appPackage", "appSignature"});
    }

    public final String[] getWifiCertificateAliasesAsUser(
            int i, CredentialStorage credentialStorage) {
        String[] strArr;
        String str = TAG;
        Log.i(str, "getWifiCertificateAliasesAsUser is called....");
        checkCallerPermissionFor("getWifiCertificateAliasesAsUser");
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(str, "getWifiCertificateAliasesAsUser - Invalid Arguments");
            }
            return null;
        }
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "getWifiCertificateAliasesAsUser is called for mContainerId ", str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList dataByFields =
                        this.mEdmStorageProvider.getDataByFields(
                                "UniversalCredentialCertificateTable",
                                new String[] {"userId", "storageName", "storagePackageName"},
                                new String[] {
                                    String.valueOf(i),
                                    credentialStorage.name,
                                    credentialStorage.packageName
                                },
                                new String[] {"adminUid", "alias", "wifi"});
                if (dataByFields.size() > 0) {
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
                                Log.i(
                                        TAG,
                                        "getWifiCertificateAliasesAsUser - isWifi :" + intValue2);
                                if (isAdminLicenseActive(intValue, credentialStorage)
                                        && intValue2 == 1) {
                                    arrayList.add(asString);
                                }
                            }
                            Log.i(TAG, "parsing error, continue...");
                        }
                    }
                    strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                } else {
                    strArr = null;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
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
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int initKeyguardPin(
            ContextInfo contextInfo,
            CredentialStorage credentialStorage,
            String str,
            Bundle bundle) {
        Log.i(TAG, "initKeyguardPin is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, true);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(getKAData("initKeyguardPin", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "initKeyguardPin is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.initKeyguardPin(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i)
                                        .build(),
                                str,
                                bundle));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final Bundle initPluginForWpc(ContextInfo contextInfo, String str) {
        CredentialStorage credentialStorage;
        String str2 = TAG;
        Log.i(str2, "initPluginForWpc is called....");
        validateContextInfo(contextInfo);
        if (TextUtils.isEmpty(str)) {
            Log.e(str2, "initPluginForWpc - Invalid Arguments");
            return getStatusErrorBundle(-11);
        }
        checkKnoxCorePermission(contextInfo);
        Log.i(str2, "initPluginForWpc : caller has valid UCM permission");
        CredentialStorage[] availableCredentialStorages =
                getAvailableCredentialStorages(contextInfo, false);
        if (availableCredentialStorages == null) {
            Log.e(str2, "No credential storages found for UCM WPC DAR.");
        } else {
            int length = availableCredentialStorages.length;
            for (int i = 0; i < length; i++) {
                credentialStorage = availableCredentialStorages[i];
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        new StringBuilder("cs.packageName -"), credentialStorage.name, TAG);
                if (str.equals(credentialStorage.packageName)) {
                    break;
                }
            }
        }
        credentialStorage = null;
        if (credentialStorage == null) {
            Log.e(TAG, "No credential storages found for packageName" + str);
            return getStatusErrorBundle(-1);
        }
        String str3 = TAG;
        StringBuilder sb = new StringBuilder("Selected CS for UCM WPC DAR");
        sb.append(credentialStorage.name);
        sb.append(" and pkg-");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb, credentialStorage.packageName, str3);
        boolean z = true;
        try {
            if (manageCredentialStorage(contextInfo, credentialStorage, true, false) != 0) {
                return getStatusErrorBundle(-1);
            }
            Provider credentialStorageProvider =
                    getCredentialStorageProvider(null, credentialStorage.packageName);
            if (credentialStorageProvider == null) {
                Log.e(str3, "getCredentialStorageForWpc. csProvider is null");
                return getStatusErrorBundle(-1);
            }
            String property = credentialStorageProvider.getProperty("storageType");
            Bundle bundle = new Bundle();
            bundle.putParcelable("credential_storage", credentialStorage);
            if ("eSE".equalsIgnoreCase(property)) {
                try {
                    try {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("applet_location", null);
                        int configureCredentialStoragePlugin =
                                configureCredentialStoragePlugin(
                                        contextInfo, credentialStorage, bundle2, false);
                        if (configureCredentialStoragePlugin < 10000) {
                            Log.e(str3, "getCredentialStorageForWpc. failed to install applet");
                            Bundle statusErrorBundle = getStatusErrorBundle(-1);
                            try {
                                Log.e(str3, "Failed to install applet, unmanage Plugin");
                                manageCredentialStorage(
                                        contextInfo, credentialStorage, false, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return statusErrorBundle;
                        }
                        try {
                            bundle.putInt("status_code", 0);
                            bundle.putInt("request_id", configureCredentialStoragePlugin);
                        } catch (Exception e2) {
                            e = e2;
                            z = false;
                            e.printStackTrace();
                            String str4 = TAG;
                            Log.e(
                                    str4,
                                    "Exception in configureCredentialStoragePlugin in UCM DAR WPC"
                                            + e);
                            if (z) {
                                try {
                                    Log.e(str4, "Failed to install applet, unmanage Plugin");
                                    manageCredentialStorage(
                                            contextInfo, credentialStorage, false, false);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            return getStatusErrorBundle(-1);
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            if (z) {
                                try {
                                    Log.e(TAG, "Failed to install applet, unmanage Plugin");
                                    manageCredentialStorage(
                                            contextInfo, credentialStorage, false, false);
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            } else {
                bundle.putInt("status_code", 0);
            }
            return bundle;
        } catch (Exception e6) {
            Log.e(TAG, "Exception in manageCredentialStorage in UCM DAR WPC" + e6);
            e6.printStackTrace();
            return getStatusErrorBundle(-1);
        }
    }

    public final boolean insertOrUpdateCertificateProfile(
            CredentialStorage credentialStorage, int i, int i2, String str, boolean z) {
        boolean z2;
        boolean z3 = DBG;
        if (z3) {
            Log.i(TAG, "insertOrUpdateCertificateProfile is called...");
        }
        boolean z4 = false;
        if (credentialStorage == null) {
            if (z3) {
                Log.i(TAG, "insertOrUpdateCertificateProfile - Invalid Arguments");
            }
            return false;
        }
        if (z3) {
            String str2 = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "InstallerId - ", " ContainerId - ", " and alias-");
            m.append(str);
            m.append(", storage name -");
            m.append(credentialStorage.name);
            m.append(", storage package - ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, credentialStorage.packageName, str2);
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
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialCertificateTable", contentValues)
                    == 0) {
                contentValues.put("storageManufacture", credentialStorage.manufacturer);
                z2 =
                        this.mEdmStorageProvider.putValuesNoUpdate(
                                "UniversalCredentialCertificateTable", contentValues);
            } else {
                Log.i(TAG, "insertOrUpdateCertificateProfile - record already exist..");
                z2 = true;
            }
            z4 = z2;
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("retcode-", TAG, z4);
        return z4;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public final int insertOrUpdateExemptPackages(
            CredentialStorage credentialStorage, List list, int i, int i2, int i3) {
        int i4;
        PackageInfo packageInfo;
        Iterator it;
        PackageInfo packageInfo2;
        int i5;
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "insertOrUpdateExemptPackages is called...");
        }
        if (z) {
            String str = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "adminId - ", " ContainerId - ", " Storage name - ");
            m.append(credentialStorage.name);
            m.append(" Storage Package - ");
            m.append(credentialStorage.packageName);
            m.append(", exemptType-");
            m.append(i3);
            Log.i(str, m.toString());
        }
        Log.i(TAG, "insertOrUpdateExemptPackages - Exempt app size -" + list.size());
        IPackageManager packageManager = AppGlobals.getPackageManager();
        Iterator it2 = list.iterator();
        int i6 = -1;
        while (it2.hasNext()) {
            AppIdentity appIdentity = (AppIdentity) it2.next();
            Log.i(TAG, "insertOrUpdateExemptPackages - pkg : " + appIdentity.getPackageName());
            if (appIdentity.getPackageName() != null
                    && appIdentity.getPackageName().length() != 0) {
                try {
                    packageInfo =
                            packageManager.getPackageInfo(appIdentity.getPackageName(), 64L, i2);
                } catch (Exception e) {
                    Log.i(TAG, Log.getStackTraceString(e));
                    packageInfo = null;
                }
                PackageInfo packageInfo3 = packageInfo;
                String str2 = TAG;
                Log.i(str2, "Package Info: " + packageInfo3);
                boolean z2 = packageInfo3 != null;
                if (appIdentity.getSignature() != null
                        && appIdentity.getSignature().length() > 0
                        && z2) {
                    Signature[] convertStringToSignature =
                            convertStringToSignature(appIdentity.getSignature());
                    if (convertStringToSignature == null) {
                        Log.i(
                                str2,
                                "UniversalCredentialManagerPolicy passed String signature is"
                                    + " invalid");
                    } else if (!compareSignatures(
                            packageInfo3.signatures, convertStringToSignature)) {
                        Log.i(
                                str2,
                                "Package is installed, and signature doesn't match. So return"
                                    + " falure");
                    }
                    i4 = -18;
                    break;
                }
                ContentValues contentValues = new ContentValues();
                Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                        i, contentValues, "adminUid", i2, "userId");
                contentValues.put("storageName", credentialStorage.name);
                contentValues.put("storagePackageName", credentialStorage.packageName);
                IPackageManager iPackageManager = packageManager;
                contentValues.put("exemptType", Integer.valueOf(i3));
                if (z2) {
                    it = it2;
                    try {
                        contentValues.put(
                                "appUid", Integer.valueOf(packageInfo3.applicationInfo.uid));
                    } catch (Exception e2) {
                        packageInfo2 = packageInfo3;
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(e2, new StringBuilder("The exception occurs "), TAG);
                    }
                } else {
                    it = it2;
                }
                packageInfo2 = packageInfo3;
                contentValues.put("appPackage", appIdentity.getPackageName());
                if (appIdentity.getSignature() != null) {
                    contentValues.put("appSignature", appIdentity.getSignature());
                }
                String str3 = credentialStorage.manufacturer;
                if (str3 != null) {
                    contentValues.put("storageManufacture", str3);
                }
                ContentValues contentValues2 = new ContentValues();
                Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                        i, contentValues2, "adminUid", i2, "userId");
                contentValues2.put("storageName", credentialStorage.name);
                contentValues2.put("storagePackageName", credentialStorage.packageName);
                contentValues2.put("appPackage", appIdentity.getPackageName());
                contentValues2.put("exemptType", Integer.valueOf(i3));
                try {
                } catch (Exception e3) {
                    if (DBG) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(e3, new StringBuilder("The exception occurs "), TAG);
                    }
                    i5 = -1;
                }
                if (!this.mEdmStorageProvider.putValues(
                        "UniversalCredentialExemptTable", contentValues, contentValues2)) {
                    if (DBG) {
                        Log.i(TAG, "insertOrUpdateExemptPackages - DB operation failed");
                    }
                    i4 = -1;
                    break;
                }
                i5 = 0;
                if (z2) {
                    try {
                        int i7 = packageInfo2.applicationInfo.uid;
                        if (!this.exemptedAppsCache.containsKey(Integer.valueOf(i7))) {
                            this.exemptedAppsCache.put(
                                    Integer.valueOf(i7), appIdentity.getPackageName());
                            Log.i(
                                    TAG,
                                    "Caching Exempt app id-"
                                            + i7
                                            + ", packageName-"
                                            + appIdentity.getPackageName());
                        }
                    } catch (Exception e4) {
                        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                .m(e4, new StringBuilder("The exception occurs "), TAG);
                    }
                }
                i6 = i5;
                packageManager = iPackageManager;
                it2 = it;
            }
        }
        i4 = i6;
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i4, "insertOrUpdateExemptPackages retcode-", TAG);
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01f8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0200  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int insertOrUpdateWhiteListPackages(
            com.samsung.android.knox.ucm.configurator.CredentialStorage r26,
            java.util.List r27,
            int r28,
            int r29,
            int r30,
            java.lang.String r31) {
        /*
            Method dump skipped, instructions count: 877
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.insertOrUpdateWhiteListPackages(com.samsung.android.knox.ucm.configurator.CredentialStorage,"
                    + " java.util.List, int, int, int, java.lang.String):int");
    }

    public final int installCACertificate(
            ContextInfo contextInfo, byte[] bArr, String str, Bundle bundle) {
        Log.i(TAG, "installCACertificate is deprecated from Knox 3.10, not supported anymore.");
        validateContextInfo(contextInfo);
        try {
            KnoxAnalytics.log(getKAData("installCACertificate"));
            return -1;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
            return -1;
        }
    }

    public final int installCertificate(
            ContextInfo contextInfo,
            CredentialStorage credentialStorage,
            byte[] bArr,
            String str,
            String str2,
            Bundle bundle) {
        boolean z;
        String str3 = TAG;
        Log.i(str3, "installCertificate is called....");
        validateContextInfo(contextInfo);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (isCallerDelegated(userId, i, credentialStorage, 107)) {
            Log.i(str3, "installCertificate caller is valid delegated app...");
            z = true;
        } else {
            enforceSecurityPermission(contextInfo, credentialStorage);
            i = contextInfo.mCallerUid;
            z = false;
        }
        return installCertificateMain(
                i, userId, credentialStorage, bArr, str, str2, bundle, false, false, z);
    }

    public final int installCertificateInternal(
            int i,
            int i2,
            CredentialStorage credentialStorage,
            byte[] bArr,
            String str,
            Bundle bundle,
            boolean z) {
        Log.i(TAG, "installCertificateInternal is called....");
        checkCallerPermissionFor("installCertificateInternal");
        return installCertificateMain(
                i,
                i2,
                credentialStorage,
                bArr,
                str,
                bundle != null ? bundle.getString("ucm_privatekey_password") : null,
                bundle,
                true,
                z,
                false);
    }

    public final int installCertificateMain(
            int i,
            int i2,
            CredentialStorage credentialStorage,
            byte[] bArr,
            String str,
            String str2,
            Bundle bundle,
            boolean z,
            boolean z2,
            boolean z3) {
        int i3;
        String str3 = TAG;
        Log.i(str3, "installCertificateMain is called....");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return i3;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            i3 = -1;
        }
        if (!isValidParam(credentialStorage)
                || bArr == null
                || str == null
                || (!z && str2 == null)) {
            if (DBG) {
                Log.i(str3, "installCertificateMain - Invalid Arguments");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -11;
        }
        boolean z4 = DBG;
        if (z4) {
            Log.i(
                    str3,
                    "installCertificateMain is called for Caller UID-"
                            + i
                            + " mContainerId "
                            + i2
                            + ", renew-"
                            + z2);
        }
        String str4 = credentialStorage.signature;
        if (str4 != null && !validateSignature(i2, credentialStorage.packageName, str4)) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -18;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(str3, "Plugin is not active");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -13;
        }
        if (true
                        != isCredentialStorageManagedInternal(
                                i, i2, credentialStorage.name, credentialStorage.packageName)
                && !z3) {
            if (z4) {
                Log.i(str3, "installCertificateMain return false..");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -12;
        }
        if (true == existAliasInternal(i2, credentialStorage, str)) {
            if (z4) {
                Log.i(str3, "alias already exist for credential storage...");
            }
            if (!z2) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -20;
            }
            if (z4) {
                Log.i(str3, "It is special renew certificate flow...");
            }
        } else if (z2) {
            if (z4) {
                Log.i(str3, "installCertificateMain invalid renew flow...");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -14;
        }
        Log.i(
                str3,
                "installCertificateMain storageOption-"
                        + (bundle != null ? bundle.getInt("ese_storage_option", -1) : -1));
        boolean z5 = bundle != null ? bundle.getBoolean("allow_wifi", false) : false;
        try {
            KnoxAnalyticsData kAData =
                    getKAData("installCertificate", credentialStorage.packageName);
            kAData.setProperty("certType", z5 ? "WIFI" : "VPN");
            KnoxAnalytics.log(kAData);
        } catch (Exception e3) {
            Log.e(TAG, "Exception = " + Log.getStackTraceString(e3));
        }
        if (z5 && i2 >= 10) {
            Log.i(TAG, "installCertificateMain wifi cert can't be installed for container");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        }
        i3 = -1;
        int installCertificateInProvider =
                installCertificateInProvider(
                        credentialStorage, bArr, str, str2, bundle, i2, i, z, z2);
        if (installCertificateInProvider != 0) {
            if (DBG) {
                Log.i(TAG, "installCertificateInProvider failed...");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return installCertificateInProvider;
        }
        if (true == insertOrUpdateCertificateProfile(credentialStorage, i, i2, str, z5)) {
            this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
            updateUcmCryptoProp();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return i3;
    }

    public final boolean isAccessAllowed(
            int i, CredentialStorage credentialStorage, Bundle bundle) {
        int i2;
        boolean z;
        List list;
        PackageInfo packageInfo;
        String str = TAG;
        Log.i(str, "isAccessAllowed is called....");
        checkCallerPermissionFor("isAccessAllowed");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        int i3 = 0;
        z2 = false;
        try {
            if (bundle != null) {
                try {
                    i2 = bundle.getInt("userId", -1);
                } catch (Exception e) {
                    e = e;
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z2;
                }
            } else {
                i2 = -1;
            }
            Log.i(str, "isAccessAllowed is called....userId-" + i2 + " and packageUid-" + i);
            if (i2 == -1) {
                i2 = UserHandle.getUserId(i);
            }
            List<AppIdentity> packagesFromWhiteListAsUser =
                    getPackagesFromWhiteListAsUser(i2, credentialStorage, bundle);
            if (packagesFromWhiteListAsUser == null || packagesFromWhiteListAsUser.size() <= 0) {
                Log.i(str, "getPackagesFromWhiteListAsUser returned empty/null whitelist");
            } else {
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
                                String str2 = packagesForUid[i3];
                                for (AppIdentity appIdentity : packagesFromWhiteListAsUser) {
                                    String packageName2 = appIdentity.getPackageName();
                                    String signature = appIdentity.getSignature();
                                    String str3 = TAG;
                                    StringBuilder sb = new StringBuilder();
                                    list = packagesFromWhiteListAsUser;
                                    sb.append("isAccessAllowed pkgName-");
                                    sb.append(str2);
                                    sb.append(" and DB packageName-");
                                    sb.append(packageName2);
                                    Log.i(str3, sb.toString());
                                    if (str2 != null
                                            && packageName2 != null
                                            && packageName2.equals(str2)) {
                                        if (signature != null) {
                                            Log.i(
                                                    str3,
                                                    "isAccessAllowed package matched. Now matching"
                                                        + " signature....");
                                            Signature[] convertStringToSignature =
                                                    convertStringToSignature(signature);
                                            if (convertStringToSignature == null) {
                                                Log.i(
                                                        str3,
                                                        "isAccessAllowed - failed to convert"
                                                            + " signature from db.");
                                            }
                                            try {
                                                packageInfo =
                                                        packageManager.getPackageInfo(
                                                                str2, 64L, UserHandle.getUserId(i));
                                            } catch (Exception e2) {
                                                Log.i(
                                                        TAG,
                                                        "The exception occurs " + e2.getMessage());
                                                packageInfo = null;
                                            }
                                            if (convertStringToSignature == null
                                                    || packageInfo == null
                                                    || !compareSignatures(
                                                            packageInfo.signatures,
                                                            convertStringToSignature)) {
                                                Log.i(
                                                        TAG,
                                                        "isAccessAllowed signature mismatch"
                                                            + " happened...Ignoring package");
                                            } else {
                                                Log.i(
                                                        TAG,
                                                        "isAccessAllowed match found with signature"
                                                            + " matching...");
                                            }
                                        } else {
                                            Log.i(str3, "isAccessAllowed match found ...");
                                        }
                                        z = true;
                                        break;
                                    }
                                    packagesFromWhiteListAsUser = list;
                                }
                                list = packagesFromWhiteListAsUser;
                                if (z) {
                                    break;
                                }
                                i3++;
                                packagesFromWhiteListAsUser = list;
                            }
                        } else {
                            Log.i(TAG, "isAccessAllowed no packages related to uid-" + i);
                        }
                    } catch (Exception e3) {
                        e = e3;
                        z2 = z;
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return z2;
                    }
                }
                z2 = z;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isAdminLicenseActive(int i, CredentialStorage credentialStorage) {
        String str = TAG;
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "isAdminLicenseActive Test adminId-", str);
        try {
            if (!this.expiredAdmins.containsKey(Integer.valueOf(i))) {
                Log.i(str, "isAdminLicenseActive - admin License is active");
                return true;
            }
            Log.i(str, "isAdminLicenseActive - adminId " + i + " is expired admin");
            List list = (List) this.expiredAdmins.get(Integer.valueOf(i));
            if (list == null || !list.contains(credentialStorage.name)) {
                return true;
            }
            Log.i(str, "isAdminLicenseActive - found the storage...");
            if (isSystemStorage(credentialStorage.packageName)) {
                Log.i(str, "isAdminLicenseActive - Storage is system. Blocking access");
            } else {
                Provider credentialStorageProvider =
                        getCredentialStorageProvider(
                                credentialStorage.name, credentialStorage.packageName);
                if (credentialStorageProvider == null) {
                    return true;
                }
                String property = credentialStorageProvider.getProperty("enforceManagement");
                Log.i(str, "isAdminLicenseActive - enforceMgt :" + property);
                if (!"true".equals(property)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return true;
        }
    }

    public final boolean isAllowed(int i, int i2) {
        String str = TAG;
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                i, i2, "isAllowed: adminUid - ", ", userId-", str);
        boolean z = true;
        try {
        } catch (Exception e) {
            e = e;
            z = false;
        }
        if (i2 < 10) {
            if (UserHandle.getUserId(i) == 0) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "isAllowed status-", TAG, z);
                return z;
            }
            Log.i(str, "isAllowed: caller app is not in user 0");
            z = false;
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("isAllowed status-", TAG, z);
            return z;
        }
        int userId = UserHandle.getUserId(i);
        Log.i(str, "isAllowed: callerUserId - " + userId);
        try {
        } catch (Exception e2) {
            e = e2;
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("isAllowed status-", TAG, z);
            return z;
        }
        if (userId == i2) {
            Log.i(str, "isAllowed: Caller is inside container. match found....");
        } else {
            Log.i(str, "isAllowed: Check if caller is owner of container");
            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i2);
            Log.i(str, "isAllowed container ownerUid - " + mUMContainerOwnerUid);
            if (mUMContainerOwnerUid != i) {
                Log.i(str, "isAllowed: no match found....");
                z = false;
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "isAllowed status-", TAG, z);
                return z;
            }
            Log.i(str, "isAllowed: match found....");
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("isAllowed status-", TAG, z);
        return z;
    }

    public final boolean isCallerDelegated(
            int i, int i2, CredentialStorage credentialStorage, int i3) {
        String str = TAG;
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                i2, i, "isCallerDelegated is called callerUid-", ", userId-", str);
        boolean z = false;
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("access_type", i3);
            List<AppIdentity> packagesFromWhiteListInternal =
                    getPackagesFromWhiteListInternal(i, credentialStorage, bundle);
            if (packagesFromWhiteListInternal == null
                    || packagesFromWhiteListInternal.size() <= 0) {
                Log.i(str, "isCallerDelegated Caller is not delegated app...");
            } else {
                String[] packagesForUid = this.mPm.getPackagesForUid(i2);
                if (packagesForUid != null && packagesForUid.length > 0) {
                    for (AppIdentity appIdentity : packagesFromWhiteListInternal) {
                        String str2 = TAG;
                        Log.i(str2, "isCallerDelegated package- " + appIdentity.getPackageName());
                        if (Arrays.asList(packagesForUid).contains(appIdentity.getPackageName())) {
                            Log.i(str2, "isCallerDelegated Caller is delegated app...");
                            if (checkDelegatorPermission(
                                    i, i3, credentialStorage, appIdentity.getPackageName())) {
                                z = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isCallerDelegated status ", TAG, z);
        return z;
    }

    public final boolean isCallerDelegated(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        String str = TAG;
        if (UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "isCallerDelegated is called....")) {
            return isCallerDelegated(
                    contextInfo.mContainerId, contextInfo.mCallerUid, credentialStorage, i);
        }
        Log.i(str, "cxtInfo is null");
        return false;
    }

    public final boolean isCallerPackageManaged() {
        String str = TAG;
        Log.i(str, "isCallerPackageManaged, Start");
        try {
            int callingUid = Binder.getCallingUid();
            String nameForUid = this.mPm.getNameForUid(callingUid);
            Log.i(
                    str,
                    "isCallerPackageManaged, callingUid: "
                            + callingUid
                            + ", packageName: "
                            + nameForUid);
            if (callingUid != -1 && nameForUid != null) {
                ContentValues m =
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                "storagePackageName", nameForUid);
                m.put("appUid", Integer.valueOf(callingUid));
                try {
                    if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", m) > 0) {
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

    public final boolean isCredentialStorageEnabledForLockType(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        Log.i(TAG, "isCredentialStorageEnabledForLockType is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, null);
        try {
            KnoxAnalytics.log(
                    getKAData(
                            "isCredentialStorageEnabledForLockType",
                            credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        boolean z = DBG;
        if (z) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i,
                    userId,
                    "isCredentialStorageEnabledForLockType is called for Caller UID - ",
                    ", mContainerId ",
                    TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isValidParam(credentialStorage)) {
                    if (checkCredentialStorageEnabledForLockTypebyAdmin(
                            i, credentialStorage, userId)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } else if (z) {
                    Log.i(
                            TAG,
                            "isCredentialStorageEnabledForLockType Invalid credential storage"
                                + " object passed...");
                }
            } catch (Exception e2) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isCredentialStorageEnabledForLockTypeAsUser(
            int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageEnabledForLockTypeAsUser");
        String str = TAG;
        Log.i(str, "isCredentialStorageEnabledForLockTypeAsUser is called....");
        boolean z = DBG;
        if (z) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "isCredentialStorageEnabledForLockTypeAsUser is called for userId ", str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (isValidParam(credentialStorage)) {
                    if (checkCredentialStorageEnabledForLockTypebyAdmin(-1, credentialStorage, i)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } else if (z) {
                    Log.i(
                            str,
                            "isCredentialStorageEnabledForLockTypeAsUser Invalid credential storage"
                                + " object passed...");
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "The exception occurs " + e.getMessage());
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isCredentialStorageLocked(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str = TAG;
        boolean z = false;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "isCredentialStorageLocked is called....")) {
            if (DBG) {
                Log.i(str, "isCredentialStorageLocked - Invalid Arguments");
            }
            return false;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        String str2 = credentialStorage.signature;
        if (str2 != null && !validateSignature(userId, credentialStorage.packageName, str2)) {
            return false;
        }
        if (!isPluginActive(credentialStorage)) {
            Log.i(str, "Plugin is not active");
            return true;
        }
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i,
                    userId,
                    "isCredentialStorageLocked is called for Caller UID-",
                    " userId ",
                    str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = isCredentialStorageLockedAsUser(userId, credentialStorage);
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isCredentialStorageLockedAsUser(
            int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageLockedAsUser");
        String str = TAG;
        Log.i(str, "isCredentialStorageLockedAsUser is called....");
        boolean z = false;
        if (!isValidParam(credentialStorage)) {
            if (DBG) {
                Log.i(str, "isCredentialStorageLockedAsUser - Invalid Arguments");
            }
            return false;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("isCredentialStorageLocked", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "isCredentialStorageLockedAsUser is called for userId-", TAG);
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(i));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, (Integer) 1);
            if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues)
                    > 0) {
                z = true;
            }
        } catch (Exception e2) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("isCredentialStorageLockedAsUser - Exception"), TAG);
            }
        }
        if (DBG) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "isCredentialStorageLockedAsUser - isLocked : ", TAG, z);
        }
        return z;
    }

    public final boolean isCredentialStorageManaged(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str, "isCredentialStorageManaged is called....")) {
            if (DBG) {
                Log.i(str, "isCredentialStorageManaged - Invalid Arguments");
            }
            return false;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (DBG) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i,
                    userId,
                    "isCredentialStorageManaged is called for Caller UID-",
                    " userId ",
                    str);
        }
        String str2 = credentialStorage.signature;
        if (str2 == null || validateSignature(userId, credentialStorage.packageName, str2)) {
            return isCredentialStorageManagedInternal(
                    i, userId, credentialStorage.name, credentialStorage.packageName);
        }
        return false;
    }

    public final boolean isCredentialStorageManagedAsUser(
            int i, CredentialStorage credentialStorage) {
        checkCallerPermissionFor("isCredentialStorageManagedAsUser");
        String str = TAG;
        Log.i(str, "isCredentialStorageManagedAsUser is called....");
        if (isValidParam(credentialStorage)) {
            if (DBG) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "isCredentialStorageManagedAsUser is called for ContainerId-", str);
            }
            return isCredentialStorageManagedInternal(
                    -1, i, credentialStorage.name, credentialStorage.packageName);
        }
        if (!DBG) {
            return false;
        }
        Log.i(str, "isCredentialStorageManagedAsUser - Invalid Arguments");
        return false;
    }

    public final boolean isCredentialStorageManagedInternal(
            int i, int i2, String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "isCredentialStorageManagedInternal");
        }
        if (z) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i2, "UserId - ", ", storageName - ", str, " and storagePackageName-"),
                    str2,
                    TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                if (i != -1) {
                    contentValues.put("adminUid", Integer.valueOf(i));
                }
                contentValues.put("userId", Integer.valueOf(i2));
                contentValues.put("storageName", str);
                contentValues.put("storagePackageName", str2);
                if (this.mEdmStorageProvider.getCount("UniversalCredentialInfoTable", contentValues)
                        > 0) {
                    z2 = true;
                }
            } catch (Exception e) {
                if (DBG) {
                    Log.i(TAG, "isCredentialStorageManagedInternal - Exception" + e.getMessage());
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (DBG) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "isCredentialStorageManagedInternal - status : ", TAG, z2);
            }
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isPackageDelegated(String str) {
        String str2 = TAG;
        Log.i(str2, "isPackageDelegated is called....");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("appPackage", str);
            contentValues.put("accessType", (Integer) 107);
            if (this.mEdmStorageProvider.getCount(
                            "UniversalCredentialWhiteListTable", contentValues)
                    <= 0) {
                return false;
            }
            if (!DBG) {
                return true;
            }
            Log.i(str2, "isPackageDelegated Exists");
            return true;
        } catch (Exception e) {
            if (!DBG) {
                return false;
            }
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
    }

    public final boolean isPackageFromExemptList(
            int i, CredentialStorage credentialStorage, int i2) {
        PackageInfo packageInfo;
        checkCallerPermissionFor("isPackageFromExemptList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        z = false;
        z = false;
        try {
            try {
                List<AppIdentity> packagesFromExemptListAsUser =
                        getPackagesFromExemptListAsUser(
                                UserHandle.getUserId(i), credentialStorage, i2);
                if (packagesFromExemptListAsUser == null
                        || packagesFromExemptListAsUser.size() <= 0) {
                    Log.i(TAG, "isPackageFromExemptList returned empty/null whitelist");
                } else {
                    String[] packagesForUid = this.mPm.getPackagesForUid(i);
                    if (i == 1010) {
                        packagesForUid = new String[] {"com.samsung.knox.virtual.wifi"};
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
                                    String str2 = TAG;
                                    Log.i(
                                            str2,
                                            "isPackageFromExemptList pkgName-"
                                                    + str
                                                    + " and DB packageName-"
                                                    + packageName);
                                    if (str != null
                                            && packageName != null
                                            && packageName.equals(str)) {
                                        if (signature != null) {
                                            Log.i(
                                                    str2,
                                                    "isPackageFromExemptList package matched. Now"
                                                        + " matching signature....");
                                            Signature[] convertStringToSignature =
                                                    convertStringToSignature(signature);
                                            if (convertStringToSignature == null) {
                                                Log.i(
                                                        str2,
                                                        "isPackageFromExemptList - failed to"
                                                            + " convert signature from db.");
                                            }
                                            try {
                                                packageInfo =
                                                        packageManager.getPackageInfo(
                                                                str, 64L, UserHandle.getUserId(i));
                                            } catch (Exception e) {
                                                Log.i(
                                                        TAG,
                                                        "isPackageFromExemptList exception - " + e);
                                                packageInfo = null;
                                            }
                                            if (convertStringToSignature == null
                                                    || packageInfo == null
                                                    || !compareSignatures(
                                                            packageInfo.signatures,
                                                            convertStringToSignature)) {
                                                Log.i(
                                                        TAG,
                                                        "isPackageFromExemptList signature mismatch"
                                                            + " happened...Ignoring package");
                                            } else {
                                                Log.i(
                                                        TAG,
                                                        "isPackageFromExemptList match found with"
                                                            + " signature matching...");
                                            }
                                        } else {
                                            Log.i(str2, "isPackageFromExemptList match found ...");
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
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return z;
                            }
                        }
                        z = z2;
                    }
                }
            } catch (Exception e3) {
                e = e3;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isPluginActive(CredentialStorage credentialStorage) {
        if (getCredentialStorageProvider(credentialStorage.name, credentialStorage.packageName)
                == null) {
            return false;
        }
        Log.i(TAG, "Plugin is active...");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSystemStorage(java.lang.String r7) {
        /*
            r6 = this;
            java.util.List r0 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.systemPlugin
            boolean r0 = r0.contains(r7)
            r1 = 0
            if (r0 == 0) goto L44
            long r2 = android.os.Binder.clearCallingIdentity()
            r0 = 1
            android.content.pm.PackageManager r4 = r6.mPm     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            r5 = 64
            android.content.pm.PackageInfo r7 = r4.getPackageInfo(r7, r5)     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            android.content.Context r6 = r6.mContext     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            android.content.pm.PackageManager r6 = r6.getPackageManager()     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            java.lang.String r4 = "android"
            android.content.pm.PackageInfo r6 = r6.getPackageInfo(r4, r5)     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            if (r7 == 0) goto L34
            android.content.pm.Signature[] r7 = r7.signatures     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            if (r7 == 0) goto L34
            android.content.pm.Signature[] r6 = r6.signatures     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            boolean r6 = compareSignatures(r6, r7)     // Catch: java.lang.Throwable -> L32 android.content.pm.PackageManager.NameNotFoundException -> L3d
            if (r6 == 0) goto L34
            r6 = r0
            goto L35
        L32:
            r6 = move-exception
            goto L39
        L34:
            r6 = r1
        L35:
            android.os.Binder.restoreCallingIdentity(r2)
            goto L41
        L39:
            android.os.Binder.restoreCallingIdentity(r2)
            throw r6
        L3d:
            android.os.Binder.restoreCallingIdentity(r2)
            r6 = r1
        L41:
            if (r6 == 0) goto L44
            return r0
        L44:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.isSystemStorage(java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0181 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0193 A[Catch: Exception -> 0x003d, LOOP:2: B:59:0x018e->B:61:0x0193, LOOP_END, TryCatch #2 {Exception -> 0x003d, blocks: (B:3:0x0013, B:5:0x0017, B:6:0x0040, B:8:0x004a, B:10:0x006b, B:12:0x0074, B:15:0x0086, B:17:0x008c, B:19:0x009a, B:21:0x009f, B:23:0x00b7, B:25:0x00ba, B:27:0x00be, B:29:0x00c8, B:31:0x00d0, B:33:0x00da, B:35:0x00e0, B:37:0x00e4, B:39:0x00ea, B:41:0x0107, B:42:0x011e, B:44:0x012a, B:45:0x0130, B:51:0x017d, B:54:0x0183, B:56:0x0187, B:58:0x018a, B:59:0x018e, B:61:0x0193, B:63:0x019e, B:66:0x01bf, B:69:0x01c7, B:80:0x01d2, B:88:0x0148, B:85:0x0164, B:95:0x01db), top: B:2:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x019e A[EDGE_INSN: B:62:0x019e->B:63:0x019e BREAK  A[LOOP:2: B:59:0x018e->B:61:0x0193], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01bd A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isValidCredentialStorage(
            int r18, com.samsung.android.knox.ucm.configurator.CredentialStorage r19) {
        /*
            Method dump skipped, instructions count: 547
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.isValidCredentialStorage(int,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage):int");
    }

    public final int lockCredentialStorage(
            ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        boolean z2;
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "lockCredentialStorage is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str2, "lockCredentialStorage - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalyticsData kAData =
                    getKAData("lockCredentialStorage", credentialStorage.packageName);
            kAData.setProperty("enable", z);
            KnoxAnalytics.log(kAData);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = DBG;
                if (z2) {
                    Log.i(
                            TAG,
                            "lockCredentialStorage is called for Caller UID-"
                                    + i
                                    + " userId "
                                    + userId);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z2) {
                    Log.i(TAG, "lockCredentialStorage return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            if (lockCredentialStorageInternal(i, userId, credentialStorage, z)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean lockCredentialStorageInternal(
            int i, int i2, CredentialStorage credentialStorage, boolean z) {
        boolean z2 = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Constants.JSON_CLIENT_DATA_STATUS, Integer.valueOf(z ? 1 : 0));
            z2 =
                    this.mEdmStorageProvider.putValues(
                            "UniversalCredentialInfoTable", contentValues2, contentValues);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder(
                                "lockCredentialStorageInternal - Exception"
                                    + " lockCredentialStorageInternal"),
                        TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "lockCredentialStorageInternal retcode-", TAG, z2);
        return z2;
    }

    public final int manageCredentialStorage(
            ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z) {
        return manageCredentialStorage(contextInfo, credentialStorage, z, true);
    }

    public final int manageCredentialStorage(
            ContextInfo contextInfo, CredentialStorage credentialStorage, boolean z, boolean z2) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "manageCredentialStorage is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str2, "manageCredentialStorage - Invalid Arguments");
            return -11;
        }
        if (z2) {
            enforceSecurityPermission(contextInfo, credentialStorage);
        }
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        boolean z3 = DBG;
        if (z3) {
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    str2,
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            userId,
                            "manageCredentialStorage is called for Caller UID-",
                            " userId ",
                            ", enable- "),
                    z);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (z) {
                if (isAllowed(i, userId)) {
                    int isValidCredentialStorage = isValidCredentialStorage(i, credentialStorage);
                    if (isValidCredentialStorage != 0) {
                        return isValidCredentialStorage;
                    }
                }
                return -1;
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z3) {
                    Log.i(str2, "configureCredentialStorageInternal return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                String keyguardStorageForCurrentUser =
                        ucmService$1.getKeyguardStorageForCurrentUser(userId);
                Log.i(
                        str2,
                        "configureCredentialStorageInternal keyguardCSName -"
                                + keyguardStorageForCurrentUser
                                + " and CS name -"
                                + credentialStorage.name);
                if (keyguardStorageForCurrentUser != null
                        && keyguardStorageForCurrentUser.length() > 0
                        && credentialStorage.name.equalsIgnoreCase(keyguardStorageForCurrentUser)) {
                    Log.i(
                            str2,
                            "configureCredentialStorageInternal : Keyguard is setup with CS. Can't"
                                + " unmanaged it.");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -26;
                }
            }
            if (addOrUpdateSecureStorageConfig(i, userId, credentialStorage, z)) {
                if (!this.mIsSystemReceiverRegistered) {
                    registerReceiver();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean notifyLicenseStatus(int i, String str) {
        checkCallerPermissionFor("notifyLicenseStatus");
        String str2 = TAG;
        Log.i(str2, "notifyLicenseStatus : event-" + i + ", packageName-" + str);
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
                                Log.i(
                                        TAG,
                                        "admin license has renewed, admin-"
                                                + num
                                                + ", packageName-"
                                                + str);
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
                IUcmService ucmService$1 = getUcmService$1();
                if (ucmService$1 != null) {
                    Log.i(TAG, "notifyChangeToPlugin is called for EVENT_LICENSE_ACTIVATE...");
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("adminUid", i3);
                        Iterator it2 =
                                ((List) this.expiredAdmins.get(Integer.valueOf(i3))).iterator();
                        while (it2.hasNext()) {
                            ucmService$1.notifyChangeToPlugin(
                                    new UniversalCredentialUtil.UcmUriBuilder((String) it2.next())
                                            .build(),
                                    14,
                                    bundle);
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
            ArrayList arrayList = (ArrayList) getAdminIdRelatedToStorage(str);
            if (arrayList.size() > 0) {
                if (packageManager.checkPermission(
                                "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE",
                                str,
                                UserHandle.getUserId(callingUid))
                        == 0) {
                    Log.i(str2, "Plugin still have permission. Ignoring notification to MDM.");
                    return false;
                }
                if (packageManager.checkPermission(
                                        "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT",
                                        str,
                                        UserHandle.getUserId(callingUid))
                                != 0
                        && packageManager.checkPermission(
                                        "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT",
                                        str,
                                        UserHandle.getUserId(callingUid))
                                != 0
                        && packageManager.checkPermission(
                                        "com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT",
                                        str,
                                        UserHandle.getUserId(callingUid))
                                != 0
                        && packageManager.checkPermission(
                                        "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
                                        str,
                                        UserHandle.getUserId(callingUid))
                                != 0) {
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        Integer num2 = (Integer) it3.next();
                        String str3 = TAG;
                        Log.i(str3, "notifyLicenseStatus expire to " + num2);
                        String[] packagesForUid2 = this.mPm.getPackagesForUid(num2.intValue());
                        if (packagesForUid2 == null) {
                            Log.i(str3, "adminPkg is null, so continue...");
                        } else {
                            int length2 = packagesForUid2.length;
                            int i5 = i2;
                            while (i5 < length2) {
                                String str4 = packagesForUid2[i5];
                                String str5 = TAG;
                                Log.i(str5, "Sending event update to package " + str4);
                                Intent intent =
                                        new Intent(
                                                "com.samsung.android.knox.intent.action.UCM_NOTIFY_EVENT");
                                intent.setPackage(str4);
                                Bundle bundle2 = new Bundle();
                                Iterator it4 = it3;
                                bundle2.putInt("event_id", 2);
                                bundle2.putString("package_name", str);
                                intent.putExtras(bundle2);
                                try {
                                } catch (Exception e2) {
                                    Log.i(TAG, "The exception occurs " + e2.getMessage());
                                }
                                if (packageManager.checkPermission(
                                                "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT",
                                                str,
                                                UserHandle.getUserId(num2.intValue()))
                                        == 0) {
                                    this.mContext.sendBroadcastAsUser(
                                            intent,
                                            new UserHandle(UserHandle.getUserId(num2.intValue())),
                                            "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT");
                                } else if (packageManager.checkPermission(
                                                "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
                                                str,
                                                UserHandle.getUserId(num2.intValue()))
                                        == 0) {
                                    this.mContext.sendBroadcastAsUser(
                                            intent,
                                            new UserHandle(UserHandle.getUserId(num2.intValue())),
                                            "com.samsung.android.knox.permission.KNOX_UCM_MGMT");
                                } else {
                                    Log.i(str5, "admin does not have proper UCM permission");
                                    i5++;
                                    it3 = it4;
                                    i2 = 0;
                                }
                                Log.i(str5, "notifyLicenseStatus expire done");
                                i5++;
                                it3 = it4;
                                i2 = 0;
                            }
                        }
                    }
                }
                Log.i(str2, "caller still have permission. Ignoring it");
                return false;
            }
            Log.i(str2, "No admin found related to package...");
            Iterator it5 = ((ArrayList) this.adminIds).iterator();
            while (it5.hasNext()) {
                Integer num3 = (Integer) it5.next();
                String[] packagesForUid3 = this.mPm.getPackagesForUid(num3.intValue());
                if (packagesForUid3 != null && packagesForUid3.length > 0) {
                    int length3 = packagesForUid3.length;
                    int i6 = 0;
                    while (true) {
                        if (i6 >= length3) {
                            break;
                        }
                        if (packagesForUid3[i6].equals(str)) {
                            Log.i(
                                    TAG,
                                    "admin license has expired, admin-"
                                            + num3
                                            + ", packageName-"
                                            + str);
                            processAdminLicenseExpiry(num3.intValue());
                            break;
                        }
                        i6++;
                    }
                }
            }
            return false;
        } catch (Exception e3) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e3, new StringBuilder("The exception occurs "), TAG);
            return false;
        }
        RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                e3, new StringBuilder("The exception occurs "), TAG);
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    public final void notifyToPlugin(int i, CredentialStorage credentialStorage, int i2) {
        String str = TAG;
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                i, i2, "notifyToPlugin eventId-10, adminUid-", ", userId-", str);
        try {
            Bundle bundle = new Bundle();
            String build =
                    new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).build();
            bundle.putInt("adminUid", i);
            bundle.putInt("userId", i2);
            String[] aliasesInternal = getAliasesInternal(i, credentialStorage, i2);
            if (aliasesInternal != null && aliasesInternal.length > 0) {
                bundle.putStringArray("aliases", aliasesInternal);
            }
            processPackagesForPlugin(i, i2, credentialStorage, bundle);
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                Log.i(str, "notifyChangeToPlugin is called for plugin unmanaged...");
                ucmService$1.notifyChangeToPlugin(build, 10, bundle);
            }
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
        }
    }

    public final void notifyUCMConfigStatus(int i, String str, Bundle bundle) {
        UserHandle userHandle = new UserHandle(UserHandle.getUserId(i));
        if (notifyUCMConfigStatusByPermission(
                        str,
                        bundle,
                        userHandle,
                        "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT")
                || notifyUCMConfigStatusByPermission(
                        str,
                        bundle,
                        userHandle,
                        "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT")
                || notifyUCMConfigStatusByPermission(
                        str,
                        bundle,
                        userHandle,
                        "com.samsung.android.knox.permission.KNOX_UCM_MGMT")
                || 5250 != i) {
            return;
        }
        Context context = this.mContext;
        Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_CONFIG_STATUS");
        intent.setPackage(str);
        intent.putExtras(bundle);
        context.sendBroadcastAsUser(intent, userHandle);
    }

    public final void notifyUCMConfigStatus(Bundle bundle) {
        String[] packagesForUid;
        String str = TAG;
        Log.i(str, "notifyUCMConfigStatus");
        if (!isCallerPackageManaged()) {
            checkCallerPermissionFor("notifyUCMConfigStatus");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int i = bundle.getInt("request_id", 0);
                int i2 = bundle.getInt("adminUid", 0);
                int i3 = bundle.getInt("status_code", -1);
                Log.i(str, "notifyUCMConfigStatus requestId -" + i + ", adminUid-" + i2);
                if (i2 != 0
                        && i != 0
                        && i3 != -1
                        && (packagesForUid = this.mPm.getPackagesForUid(i2)) != null) {
                    for (String str2 : packagesForUid) {
                        Log.i(TAG, "Sending config update to package " + str2);
                        try {
                            notifyUCMConfigStatus(i2, str2, bundle);
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

    public final boolean notifyUCMConfigStatusByPermission(
            String str, Bundle bundle, UserHandle userHandle, String str2) {
        try {
            if (AppGlobals.getPackageManager()
                            .checkPermission(str2, str, userHandle.getIdentifier())
                    != 0) {
                return false;
            }
            Log.i(TAG, "Package has UCM permission. : ".concat(str2));
            Context context = this.mContext;
            Intent intent = new Intent("com.samsung.android.knox.intent.action.UCM_CONFIG_STATUS");
            intent.setPackage(str);
            intent.putExtras(bundle);
            context.sendBroadcastAsUser(intent, userHandle, str2);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(i, "onAdminRemoved - ", TAG);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        if (DBG) {
            DirEncryptService$$ExternalSyntheticOutline0.m(i, "onPreAdminRemoval - ", TAG);
        }
        Message obtainMessage = this.mUCSMHandler.obtainMessage(10);
        obtainMessage.arg1 = i;
        this.mUCSMHandler.sendMessage(obtainMessage);
    }

    public final void performCredentialStorageCleanup(
            int i, CredentialStorage credentialStorage, int i2) {
        boolean z;
        String[] strArr = {"adminUid", "userId", "storageName", "storagePackageName"};
        String[] strArr2 = {
            String.valueOf(i),
            String.valueOf(i2),
            credentialStorage.name,
            credentialStorage.packageName
        };
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialCertificateTable", strArr, strArr2);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performCredentialStorageCleanup Clean certificate status-", TAG, z);
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialWhiteListTable", strArr, strArr2);
        } catch (Exception e2) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performCredentialStorageCleanup WhiteList APP status-", TAG, z);
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable", strArr, strArr2);
        } catch (Exception e3) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e3, new StringBuilder("The exception occurs "), TAG);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "performCredentialStorageCleanup Default Install status-", TAG, z);
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialExemptTable", strArr, strArr2);
        } catch (Exception e4) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e4, new StringBuilder("The exception occurs "), TAG);
            }
        }
        Log.i(TAG, "performCredentialStorageCleanup Default Install status-" + z);
        this.mExistCert = checkCountFromEdmDB("UniversalCredentialCertificateTable");
        this.mExistWhitelist = checkCountFromEdmDB("UniversalCredentialWhiteListTable");
        updateUcmCryptoProp();
    }

    public final void processAdminLicenseExpiry(int i) {
        Log.i(TAG, "getStoragesRelatedToAdminId adminId-" + i);
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "UniversalCredentialInfoTable",
                            new String[] {"adminUid"},
                            new String[] {String.valueOf(i)},
                            new String[] {"storageName"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ContentValues) it.next()).getAsString("storageName"));
                }
            }
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
        }
        if (this.expiredAdmins.containsKey(Integer.valueOf(i)) || arrayList.size() <= 0) {
            return;
        }
        this.expiredAdmins.put(Integer.valueOf(i), arrayList);
        String str = TAG;
        Log.i(str, "processAdminLicenseExpiry expired admin-" + i);
        IUcmService ucmService$1 = getUcmService$1();
        if (ucmService$1 != null) {
            Log.i(str, "processAdminLicenseExpiry is called for license expiry...");
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("adminUid", i);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ucmService$1.notifyChangeToPlugin(
                            new UniversalCredentialUtil.UcmUriBuilder((String) it2.next()).build(),
                            13,
                            bundle);
                }
            } catch (Exception e2) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e2, new StringBuilder("The exception occurs "), TAG);
            }
        }
    }

    public final void processPackagesForPlugin(
            int i, int i2, CredentialStorage credentialStorage, Bundle bundle) {
        PackageInfo packageInfo;
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "processPackagesForPlugin - adminUid", ", userId-", ", Storage -"),
                credentialStorage.name,
                TAG);
        try {
            List adminIdRelatedToStorageAsUser =
                    getAdminIdRelatedToStorageAsUser(i2, credentialStorage);
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) adminIdRelatedToStorageAsUser).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num == null) {
                    Log.i(TAG, "storageAdmin is null, continue...");
                } else {
                    String str = TAG;
                    Log.i(str, "adminUid related to storage is - " + num);
                    if (num.intValue() == i) {
                        Log.i(str, "Ignoring current adminUid  - " + num);
                    } else {
                        ArrayList whitelistedData =
                                getWhitelistedData(
                                        num.intValue(), i2, 103, credentialStorage, null);
                        if (whitelistedData.size() > 0) {
                            Iterator it2 = whitelistedData.iterator();
                            while (it2.hasNext()) {
                                String asString =
                                        ((ContentValues) it2.next()).getAsString("appPackage");
                                if (!arrayList.contains(asString)) {
                                    Log.i(
                                            TAG,
                                            "Adding app in whitelistPkgsByOtherAdmin -" + asString);
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
                ArrayList arrayList2 = new ArrayList();
                IPackageManager packageManager = AppGlobals.getPackageManager();
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    String str2 = (String) it3.next();
                    Log.i(TAG, "Allowed pkg - " + str2);
                    try {
                        packageInfo = packageManager.getPackageInfo(str2, 64L, i2);
                    } catch (Exception e) {
                        Log.i(TAG, "The exception occurs " + e.getMessage());
                        packageInfo = null;
                    }
                    if (packageInfo == null) {
                        Log.i(TAG, "pkgInfo is null");
                    } else {
                        int packageUid = this.mPm.getPackageUid(str2, i2);
                        if (!arrayList2.contains(Integer.valueOf(packageUid))) {
                            arrayList2.add(Integer.valueOf(packageUid));
                        }
                    }
                }
                int[] iArr = new int[arrayList2.size()];
                Iterator it4 = arrayList2.iterator();
                int i3 = 0;
                while (it4.hasNext()) {
                    Integer num2 = (Integer) it4.next();
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
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
        }
    }

    public final void registerReceiver() {
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.USER_REMOVED",
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "android.intent.action.ACTION_SHUTDOWN",
                        "android.intent.action.SCREEN_ON",
                        "android.intent.action.SCREEN_OFF");
        m.addAction("android.intent.action.USER_PRESENT");
        m.addAction("android.intent.action.DEVICE_LOCKED_CHANGED");
        Context context = this.mContext;
        AnonymousClass2 anonymousClass2 = this.mSystemReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass2, userHandle, m, null, null, 2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(
                this.mPackageRemovedReceiver, userHandle, intentFilter, null, null);
        this.mIsSystemReceiverRegistered = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int removeCertificatefromProvider(
            int r9, int r10, java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            r8 = this;
            java.lang.String r0 = "wifi"
            java.lang.String r1 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG
            java.lang.String r2 = "removeCertificatefromProvider is called for userId-"
            java.lang.String r3 = " and adminId-"
            java.lang.String r4 = " and alias-"
            java.lang.StringBuilder r2 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r9, r10, r2, r3, r4)
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r2, r13, r1)
            r1 = 0
            r2 = -1
            java.lang.String r3 = "storageName"
            java.lang.String r4 = "storagePackageName"
            java.lang.String r5 = "alias"
            java.lang.String r6 = "userId"
            java.lang.String r7 = "adminUid"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5, r6, r7}     // Catch: java.lang.Exception -> L64
            java.lang.String r4 = java.lang.Integer.toString(r9)     // Catch: java.lang.Exception -> L64
            java.lang.String r5 = java.lang.Integer.toString(r10)     // Catch: java.lang.Exception -> L64
            java.lang.String[] r12 = new java.lang.String[]{r11, r12, r13, r4, r5}     // Catch: java.lang.Exception -> L64
            java.lang.String[] r4 = new java.lang.String[]{r0}     // Catch: java.lang.Exception -> L64
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r8.mEdmStorageProvider     // Catch: java.lang.Exception -> L64
            java.lang.String r5 = "UniversalCredentialCertificateTable"
            java.util.ArrayList r8 = r8.getDataByFields(r5, r3, r12, r4)     // Catch: java.lang.Exception -> L64
            int r12 = r8.size()     // Catch: java.lang.Exception -> L64
            r3 = 1
            if (r12 <= 0) goto L68
            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Exception -> L64
            r12 = r3
        L4a:
            boolean r4 = r8.hasNext()     // Catch: java.lang.Exception -> L64
            if (r4 == 0) goto L67
            java.lang.Object r4 = r8.next()     // Catch: java.lang.Exception -> L64
            android.content.ContentValues r4 = (android.content.ContentValues) r4     // Catch: java.lang.Exception -> L64
            java.lang.Integer r4 = r4.getAsInteger(r0)     // Catch: java.lang.Exception -> L64
            if (r4 == 0) goto L4a
            int r4 = r4.intValue()     // Catch: java.lang.Exception -> L64
            if (r4 != r3) goto L4a
            r12 = 3
            goto L4a
        L64:
            r8 = move-exception
            r10 = r1
            goto Lc8
        L67:
            r3 = r12
        L68:
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = new com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder     // Catch: java.lang.Exception -> L64
            r8.<init>(r11)     // Catch: java.lang.Exception -> L64
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setResourceId(r3)     // Catch: java.lang.Exception -> L64
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setUid(r10)     // Catch: java.lang.Exception -> L64
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setAlias(r13)     // Catch: java.lang.Exception -> L64
            java.lang.String r8 = r8.build()     // Catch: java.lang.Exception -> L64
            com.samsung.android.knox.ucm.core.IUcmService r10 = getUcmService$1()     // Catch: java.lang.Exception -> L64
            if (r10 == 0) goto Lc6
            android.os.Bundle r9 = r10.deleteCertificate(r8, r9)     // Catch: java.lang.Exception -> L64
            if (r9 == 0) goto L91
            java.lang.String r10 = "booleanresponse"
            boolean r10 = r9.getBoolean(r10, r1)     // Catch: java.lang.Exception -> L64
            goto L92
        L91:
            r10 = r1
        L92:
            if (r9 == 0) goto L9e
            java.lang.String r11 = "errorresponse"
            int r2 = r9.getInt(r11, r2)     // Catch: java.lang.Exception -> L9c
            goto L9e
        L9c:
            r8 = move-exception
            goto Lc8
        L9e:
            java.lang.String r9 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG     // Catch: java.lang.Exception -> L9c
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L9c
            r11.<init>()     // Catch: java.lang.Exception -> L9c
            java.lang.String r12 = "removeCertificatefromProvider : "
            r11.append(r12)     // Catch: java.lang.Exception -> L9c
            r11.append(r10)     // Catch: java.lang.Exception -> L9c
            java.lang.String r12 = "="
            r11.append(r12)     // Catch: java.lang.Exception -> L9c
            r11.append(r10)     // Catch: java.lang.Exception -> L9c
            java.lang.String r12 = "; csUri="
            r11.append(r12)     // Catch: java.lang.Exception -> L9c
            r11.append(r8)     // Catch: java.lang.Exception -> L9c
            java.lang.String r8 = r11.toString()     // Catch: java.lang.Exception -> L9c
            android.util.Log.i(r9, r8)     // Catch: java.lang.Exception -> L9c
            goto Ld4
        Lc6:
            r10 = r1
            goto Ld4
        Lc8:
            java.lang.String r9 = com.android.server.enterprise.ucm.UniversalCredentialManagerService.TAG
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "The exception occurs "
            r11.<init>(r12)
            com.android.server.enterprise.RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(r8, r11, r9)
        Ld4:
            if (r10 == 0) goto Ld7
            goto Ld8
        Ld7:
            r1 = r2
        Ld8:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.removeCertificatefromProvider(int,"
                    + " int, java.lang.String, java.lang.String, java.lang.String):int");
    }

    public final boolean removeCredentialStorageLockType(
            int i, CredentialStorage credentialStorage, int i2) {
        boolean z;
        String str = credentialStorage.name;
        String str2 = credentialStorage.packageName;
        if (DBG) {
            String str3 = TAG;
            Log.i(str3, "removeCredentialStorageLockType is called...");
            StringBuilder sb = new StringBuilder("removeCredentialStorageLockType adminUid - ");
            sb.append(i);
            sb.append(" ContainerId - ");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(
                    i2, ", Storage Name- ", str, ", Storage Package name - ", sb);
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str2, str3);
        }
        try {
            z =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialEnabledLockTypeTable",
                            new String[] {
                                "adminUid", "userId", "storageName", "storagePackageName"
                            },
                            new String[] {String.valueOf(i), String.valueOf(i2), str, str2});
            Log.i(TAG, "removeCredentialStorageLockType result - " + z);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "removeCredentialStorageLockType retcode-", TAG, z);
        return z;
    }

    public final boolean removeExemptPackages(
            CredentialStorage credentialStorage, List list, int i, int i2, int i3) {
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "removeExemptPackages is called...");
        }
        if (z) {
            String str = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "adminId - ", " ContainerId - ", " Storage name - ");
            m.append(credentialStorage.name);
            m.append(" Storage Package - ");
            m.append(credentialStorage.packageName);
            m.append(", type-");
            m.append(i3);
            Log.i(str, m.toString());
        }
        boolean z2 = false;
        if (list == null || list.size() <= 0) {
            Log.i(TAG, "removeExemptPackages clearing all packages....");
            try {
                z2 =
                        this.mEdmStorageProvider.deleteDataByFields(
                                "UniversalCredentialExemptTable",
                                new String[] {
                                    "adminUid",
                                    "userId",
                                    "storageName",
                                    "storagePackageName",
                                    "exemptType"
                                },
                                new String[] {
                                    String.valueOf(i),
                                    String.valueOf(i2),
                                    credentialStorage.name,
                                    credentialStorage.packageName,
                                    String.valueOf(i3)
                                });
            } catch (Exception e) {
                if (DBG) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("The exception occurs "), TAG);
                }
            }
        } else {
            Log.i(TAG, "removeExemptPackages - WhiteList app size -" + list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                AppIdentity appIdentity = (AppIdentity) it.next();
                String str2 = TAG;
                Log.i(str2, "removeExemptPackages - pkg : " + appIdentity.getPackageName());
                if (appIdentity.getPackageName() != null) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i3, "removeExemptPackages exempt type-", str2);
                    try {
                        z2 =
                                this.mEdmStorageProvider.deleteDataByFields(
                                        "UniversalCredentialExemptTable",
                                        new String[] {
                                            "adminUid",
                                            "userId",
                                            "storageName",
                                            "storagePackageName",
                                            "exemptType",
                                            "appPackage"
                                        },
                                        new String[] {
                                            String.valueOf(i),
                                            String.valueOf(i2),
                                            credentialStorage.name,
                                            credentialStorage.packageName,
                                            String.valueOf(i3),
                                            appIdentity.getPackageName()
                                        });
                        if (!z2) {
                            Log.i(str2, "removeExemptPackages - failed to remove record...");
                            break;
                        }
                        continue;
                    } catch (Exception e2) {
                        if (DBG) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(
                                            e2,
                                            new StringBuilder(
                                                    "removeExemptPackages - Exception delete"),
                                            TAG);
                        }
                    }
                }
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "removeExemptPackages retcode-", TAG, z2);
        return z2;
    }

    public final int removeODESettingsForWPC() {
        String str = TAG;
        Log.i(str, "removeODEConfigForWPC is called....");
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            throw new SecurityException("removeODESettingsForWPC is called by not-proper caller");
        }
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (DBG) {
                    Log.i(
                            str,
                            "removeODEConfigForWPC is called for Caller UID-"
                                    + callingUid
                                    + " mContainerId "
                                    + userId);
                }
                IUcmService ucmService$1 = getUcmService$1();
                if (ucmService$1 != null) {
                    Log.i(str, "removeODEConfigForWPC is called for plugin unmanaged...");
                    int removeODESettings = ucmService$1.removeODESettings();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return removeODESettings;
                }
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005e A[Catch: all -> 0x0078, Exception -> 0x007b, TryCatch #1 {Exception -> 0x007b, blocks: (B:19:0x005a, B:21:0x005e, B:22:0x007d, B:24:0x0081, B:28:0x008f, B:30:0x0095, B:33:0x00a2, B:36:0x00af, B:39:0x00bd, B:41:0x00c3, B:45:0x00d7), top: B:18:0x005a, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0095 A[Catch: all -> 0x0078, Exception -> 0x007b, TRY_LEAVE, TryCatch #1 {Exception -> 0x007b, blocks: (B:19:0x005a, B:21:0x005e, B:22:0x007d, B:24:0x0081, B:28:0x008f, B:30:0x0095, B:33:0x00a2, B:36:0x00af, B:39:0x00bd, B:41:0x00c3, B:45:0x00d7), top: B:18:0x005a, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a2 A[Catch: all -> 0x0078, Exception -> 0x007b, TRY_ENTER, TryCatch #1 {Exception -> 0x007b, blocks: (B:19:0x005a, B:21:0x005e, B:22:0x007d, B:24:0x0081, B:28:0x008f, B:30:0x0095, B:33:0x00a2, B:36:0x00af, B:39:0x00bd, B:41:0x00c3, B:45:0x00d7), top: B:18:0x005a, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int removePackagesFromExemptList(
            com.samsung.android.knox.ContextInfo r13,
            com.samsung.android.knox.ucm.configurator.CredentialStorage r14,
            int r15,
            java.util.List r16) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.ucm.UniversalCredentialManagerService.removePackagesFromExemptList(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.ucm.configurator.CredentialStorage, int,"
                    + " java.util.List):int");
    }

    public final int removePackagesFromWhiteList(
            ContextInfo contextInfo,
            CredentialStorage credentialStorage,
            List list,
            Bundle bundle) {
        boolean z;
        boolean z2;
        String str;
        int i;
        String str2;
        String str3 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                        contextInfo,
                        credentialStorage,
                        str3,
                        "removePackagesFromWhiteList is called....")
                || list == null) {
            if (!DBG) {
                return -11;
            }
            Log.i(str3, "removePackagesFromWhiteList - Invalid Arguments");
            return -11;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("removePackagesFromWhiteList", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        if (isCallerDelegated(userId, i2, credentialStorage, 107)) {
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
                z2 = DBG;
                if (z2) {
                    Log.i(
                            TAG,
                            "removePackagesFromWhiteList is called for Caller UID-"
                                    + i2
                                    + " userId "
                                    + userId);
                }
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Plugin is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            if (true
                            != isCredentialStorageManagedInternal(
                                    i2,
                                    userId,
                                    credentialStorage.name,
                                    credentialStorage.packageName)
                    && !z) {
                if (z2) {
                    Log.i(TAG, "removePackagesFromWhiteList return false..");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -12;
            }
            int i3 = bundle.getInt("access_type", -1);
            if (!isValidAccessType(i3)) {
                if (z2) {
                    Log.i(TAG, "removePackagesFromWhiteList not passed valid access_type");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            if (i3 == 104) {
                String string = bundle.getString("alias");
                String str4 = TAG;
                Log.i(str4, "removePackagesFromWhiteList alias-" + string);
                if (TextUtils.isEmpty(string)) {
                    if (z2) {
                        Log.i(
                                str4,
                                "removePackagesFromWhiteList alias name not provided for"
                                    + " Certificate access_type");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -16;
                }
                str2 = string;
                i = i3;
                if (true
                        != checkCredentialStorageAliasForAdmin(
                                i2,
                                userId,
                                credentialStorage.name,
                                credentialStorage.packageName,
                                str2)) {
                    if (z2) {
                        Log.i(
                                str4,
                                "removePackagesFromWhiteList - alias not exist for credential"
                                    + " storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -14;
                }
            } else {
                i = i3;
                str2 = null;
            }
            if (true == removeWhiteListPackages(credentialStorage, list, i2, userId, i, str2)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeWhiteListPackages(
            CredentialStorage credentialStorage, List list, int i, int i2, int i3, String str) {
        String str2;
        CredentialStorage credentialStorage2 = credentialStorage;
        int i4 = i3;
        String str3 = str;
        boolean z = DBG;
        if (z) {
            Log.i(TAG, "removeWhiteListPackages is called...");
        }
        if (z) {
            String str4 = TAG;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "adminId - ", " ContainerId - ", " Storage name - ");
            m.append(credentialStorage2.name);
            m.append(" Storage Package - ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i4, credentialStorage2.packageName, ", accessType-", ", alias-", m);
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str3, str4);
        }
        Log.i(TAG, "removeWhiteListPackages - WhiteList app size -" + list.size());
        Iterator it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            AppIdentity appIdentity = (AppIdentity) it.next();
            String str5 = TAG;
            Log.i(str5, "removeWhiteListPackages - pkg : " + appIdentity.getPackageName());
            if (appIdentity.getPackageName() != null) {
                if (str3 == null) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i4, "removeWhiteListPackages access_type-", str5);
                    try {
                        z2 =
                                this.mEdmStorageProvider.deleteDataByFields(
                                        "UniversalCredentialWhiteListTable",
                                        new String[] {
                                            "adminUid",
                                            "userId",
                                            "storageName",
                                            "storagePackageName",
                                            "accessType",
                                            "appPackage"
                                        },
                                        new String[] {
                                            String.valueOf(i),
                                            String.valueOf(i2),
                                            credentialStorage2.name,
                                            credentialStorage2.packageName,
                                            String.valueOf(i3),
                                            appIdentity.getPackageName()
                                        });
                        if (!z2) {
                            Log.i(str5, "removeWhiteListPackages - failed to remove record...");
                            break;
                        }
                    } catch (Exception e) {
                        if (DBG) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(e, new StringBuilder("The exception occurs "), TAG);
                        }
                    }
                } else {
                    Log.i(str5, "removeWhiteListPackages access_type-" + i4 + " and alias-" + str3);
                    str2 = "UniversalCredentialWhiteListTable";
                    try {
                        z2 =
                                this.mEdmStorageProvider.deleteDataByFields(
                                        str2,
                                        new String[] {
                                            "adminUid",
                                            "userId",
                                            "storageName",
                                            "storagePackageName",
                                            "accessType",
                                            "alias",
                                            "appPackage"
                                        },
                                        new String[] {
                                            String.valueOf(i),
                                            String.valueOf(i2),
                                            credentialStorage2.name,
                                            credentialStorage2.packageName,
                                            String.valueOf(i3),
                                            str,
                                            appIdentity.getPackageName()
                                        });
                        if (!z2) {
                            Log.i(str5, "removeWhiteListPackages - failed to remove record...");
                            break;
                        }
                    } catch (Exception e2) {
                        if (DBG) {
                            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0
                                    .m(e2, new StringBuilder("The exception occurs "), TAG);
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
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "removeWhiteListPackages retcode-", TAG, z2);
        return z2;
    }

    public final int setAuthType(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "setAuthType is called....")) {
            if (!DBG) {
                return -11;
            }
            Log.i(str2, "setAuthType - Invalid Arguments");
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
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -18;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(TAG, "Storage is not active");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
            boolean z = DBG;
            if (z) {
                Log.i(
                        TAG,
                        "setAuthType is called for Caller UID-"
                                + i2
                                + " mContainerId "
                                + contextInfo.mContainerId);
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i2, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z) {
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
            if (setAuthTypeInternal(i2, userId, credentialStorage, i)) {
                Log.i(TAG, "setAuthTypeInternal is successful");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setAuthTypeInternal(
            int i, int i2, CredentialStorage credentialStorage, int i3) {
        boolean z;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(i2));
            contentValues.put("storageName", credentialStorage.name);
            contentValues.put("storagePackageName", credentialStorage.packageName);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("storageAuthType", Integer.valueOf(i3));
            z =
                    this.mEdmStorageProvider.putValues(
                            "UniversalCredentialInfoTable", contentValues2, contentValues);
        } catch (Exception e) {
            if (DBG) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("The exception occurs "), TAG);
            }
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setAuthTypeInternal retcode-", TAG, z);
        return z;
    }

    public final Bundle setCredentialStorageProperty(
            ContextInfo contextInfo, CredentialStorage credentialStorage, Bundle bundle) {
        String str;
        String str2 = TAG;
        if (!UniversalCredentialManagerService$$ExternalSyntheticOutline0.m(
                contextInfo, credentialStorage, str2, "setPackageSetting is called....")) {
            if (DBG) {
                Log.i(str2, "setCredentialStorageProperty - Invalid Arguments");
            }
            return null;
        }
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = credentialStorage.signature;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
            if (str != null && !validateSignature(userId, credentialStorage.packageName, str)) {
                return null;
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(str2, "Plugin is not active..");
                return null;
            }
            boolean z = DBG;
            if (z) {
                Log.i(
                        str2,
                        "setCredentialStorageProperty is called for Caller UID-"
                                + i
                                + " mContainerId "
                                + userId);
            }
            if (true
                    != isCredentialStorageManagedInternal(
                            i, userId, credentialStorage.name, credentialStorage.packageName)) {
                if (z) {
                    Log.i(str2, "setCredentialStorageProperty return false..");
                }
                return null;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                Log.i(str2, "setCredentialStorageProperty - pass to agent...");
                return ucmService$1.setCredentialStorageProperty(
                        i,
                        new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                .setResourceId(4)
                                .setUid(i)
                                .build(),
                        bundle,
                        userId);
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setDefaultInstallStorage(
            ContextInfo contextInfo, CredentialStorage credentialStorage) {
        String str = TAG;
        Log.i(str, "setDefaultInstallStorage is called....");
        validateContextInfo(contextInfo);
        enforceSecurityPermission(contextInfo, credentialStorage);
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (credentialStorage != null) {
            String str2 = credentialStorage.signature;
            if (str2 != null) {
                if (!validateSignature(
                        contextInfo.mContainerId, credentialStorage.packageName, str2)) {
                    return -18;
                }
            }
            if (!isPluginActive(credentialStorage)) {
                Log.i(str, "Plugin is not active");
                return -13;
            }
        }
        try {
            if (credentialStorage != null) {
                KnoxAnalytics.log(
                        getKAData("setDefaultInstallStorage", credentialStorage.packageName));
            } else {
                KnoxAnalytics.log(getKAData("setDefaultInstallStorage", "null"));
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean z = DBG;
                if (z) {
                    Log.i(
                            TAG,
                            "setDefaultInstallStorage is called for Caller UID-"
                                    + i
                                    + " mContainerId "
                                    + userId);
                }
                if (userId >= 10) {
                    int mUMContainerOwnerUid =
                            this.mEdmStorageProvider.getMUMContainerOwnerUid(userId);
                    String str3 = TAG;
                    Log.i(
                            str3,
                            "setDefaultInstallStorage container ownerUid - "
                                    + mUMContainerOwnerUid);
                    if (mUMContainerOwnerUid != i) {
                        Log.i(
                                str3,
                                "setDefaultInstallStorage callerUid - "
                                        + i
                                        + " is not owner of container. Request fail...");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -24;
                    }
                }
                if (isValidParam(credentialStorage)) {
                    if (true
                            != isCredentialStorageManagedInternal(
                                    i,
                                    userId,
                                    credentialStorage.name,
                                    credentialStorage.packageName)) {
                        if (z) {
                            Log.i(TAG, "setDefaultInstallStorage return false..");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -12;
                    }
                } else if (!checkDefaultInstallCredentialStorageExistsForAdmin(
                        i, userId, null, null)) {
                    if (z) {
                        Log.i(
                                TAG,
                                "setDefaultInstallStorage MDM don't own any credential storage...");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -12;
                }
                int defaultInstallStorageInternal =
                        setDefaultInstallStorageInternal(i, credentialStorage, userId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return defaultInstallStorageInternal;
            } catch (Exception e2) {
                Log.i(TAG, "The exception occurs " + e2.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setDefaultInstallStorageInternal(
            int i, CredentialStorage credentialStorage, int i2) {
        try {
            if (isValidParam(credentialStorage)) {
                if (!checkDefaultInstallCredentialStorageExists(
                        i2, credentialStorage.name, credentialStorage.packageName)) {
                    return addOrUpdateDefaultInstallStorage(i, credentialStorage, i2) ? 0 : -1;
                }
                if (checkDefaultInstallCredentialStorageExistsForAdmin(
                        i, i2, credentialStorage.name, credentialStorage.packageName)) {
                    Log.i(TAG, "configureSecureStorageInternal record already exist...");
                    return 0;
                }
                Log.i(
                        TAG,
                        "setDefaultInstallStorageInternal Credential storage is configured by some"
                            + " other admin");
                return -10;
            }
            String str = TAG;
            Log.i(str, "setDefaultInstallStorageInternal cs is null so removing admin entry...");
            boolean deleteDataByFields =
                    this.mEdmStorageProvider.deleteDataByFields(
                            "UniversalCredentialDefaultInstallTable",
                            new String[] {"adminUid", "userId"},
                            new String[] {String.valueOf(i), String.valueOf(i2)});
            Log.i(str, "setDefaultInstallStorageInternal result-" + deleteDataByFields);
            return deleteDataByFields ? 0 : -1;
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int setKeyguardPinMaximumLength(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMaximumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, true);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("setKeyguardPinMaximumLength", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "setKeyguardPinMaximumLength is called for Caller UID-"
                                + i2
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.setKeyguardPinMaximumLength(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i2)
                                        .build(),
                                i));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int setKeyguardPinMaximumRetryCount(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMaximumRetryCount is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, true);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("setKeyguardPinMaximumRetryCount", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "setKeyguardPinMaximumRetryCount is called for Caller UID-"
                                + i2
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.setKeyguardPinMaximumRetryCount(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i2)
                                        .build(),
                                i));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int setKeyguardPinMinimumLength(
            ContextInfo contextInfo, CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "setKeyguardPinMinimumLength is called....");
        validateContextInfo(contextInfo);
        int checkContext = checkContext(contextInfo, credentialStorage, true);
        if (checkContext != 0) {
            return checkContext;
        }
        try {
            KnoxAnalytics.log(
                    getKAData("setKeyguardPinMinimumLength", credentialStorage.packageName));
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception = "), TAG);
        }
        int i2 = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i2);
        try {
            if (DBG) {
                Log.i(
                        TAG,
                        "setKeyguardPinMinimumLength is called for Caller UID-"
                                + i2
                                + " mContainerId "
                                + userId);
            }
            int checkCS = checkCS(contextInfo, credentialStorage);
            if (checkCS != 0) {
                return checkCS;
            }
            IUcmService ucmService$1 = getUcmService$1();
            if (ucmService$1 != null) {
                return getReturnvalue(
                        ucmService$1.setKeyguardPinMinimumLength(
                                new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                                        .setResourceId(4)
                                        .setUid(i2)
                                        .build(),
                                i));
            }
            return -1;
        } catch (Exception e2) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

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
}
