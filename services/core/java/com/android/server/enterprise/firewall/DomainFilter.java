package com.android.server.enterprise.firewall;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.net.IDomainFilterEventListener;
import com.android.internal.net.IOemNetd;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda2;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DomainFilter {
    public static final int MAX_LIST_SIZE_IN_BYTES = IBinder.getSuggestedMaxIpcSizeBytes();
    public static ConnectivityManager mConnectivityService;
    public final AnonymousClass1 mBootFilterReceiver;
    public final AnonymousClass5 mCaptivePortalNetworkCallback;
    public final Context mContext;
    public EnforceDnsManager mDnsManager;
    public final DnsResolverAdapter mDnsResolverAdapter;
    public final Map mDomainFilterBrokenRules;
    public DomainFilterEventListener mDomainFilterEventListener;
    public final DomainFilterNapCommon mDomainFilterNapCommon;
    public final Set mDomainReportCache;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Object mExceptionListLock;
    public final FirewallRulesApplier mFirewallRulesApplier;
    public final Injector mInjector;
    public boolean mIsExceptionListApplied;
    public boolean mIsKGExceptionApplied;
    public final Object mKGExceptionListLock;
    public final KnoxNetIdManager mNetIdManager;
    public INetd mNetdService;
    public final NetworkManagementHandler mNetworkHandler;
    public final List mNetworkIdList;
    public final Object mNetworkIdListLock;
    public INetworkManagementService mNetworkService;
    public IOemNetd mOemNetdService;
    public final AnonymousClass1 mPackageFilterReceiver;
    public final BlockReport[] mReportCache;
    public int mReportCacheIndex;
    public final String mServer;
    public final AnonymousClass1 mUserFilterReceiver;
    public final Map mUserIdMap;
    public final Object mUserIdMapLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.DomainFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DomainFilter this$0;

        public /* synthetic */ AnonymousClass1(DomainFilter domainFilter, int i) {
            this.$r8$classId = i;
            this.this$0 = domainFilter;
        }

        private final void onReceive$com$android$server$enterprise$firewall$DomainFilter$2(
                Context context, Intent intent) {
            List list;
            int i = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
            Log.i("DomainFilter", "onReceive() - " + intent);
            if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                DomainFilter domainFilter = this.this$0;
                domainFilter.getClass();
                Log.i("DomainFilter", "onUserRemoved()");
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra != -1) {
                    Log.i("DomainFilter", "onUserRemoved() - userId = " + intExtra);
                    Integer valueOf = Integer.valueOf(intExtra);
                    synchronized (domainFilter.mUserIdMapLock) {
                        ((HashMap) domainFilter.mUserIdMap).remove(valueOf);
                    }
                    if (((HashSet) domainFilter.mDomainReportCache).contains(valueOf)) {
                        ((HashSet) domainFilter.mDomainReportCache).remove(valueOf);
                        domainFilter.sendToCache(
                                2, Integer.toString(intExtra), null, null, null, null);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                DomainFilter domainFilter2 = this.this$0;
                domainFilter2.getClass();
                Log.i("DomainFilter", "onUserAdded()");
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra2 != -1) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            intExtra2, "onUserAdded() - userId = ", "DomainFilter");
                    synchronized (domainFilter2.mUserIdMapLock) {
                        try {
                            if (((Map)
                                            ((HashMap) domainFilter2.mUserIdMap)
                                                    .get(Integer.valueOf(intExtra2)))
                                    == null) {
                                HashMap hashMap = new HashMap();
                                list =
                                        domainFilter2
                                                .mContext
                                                .getPackageManager()
                                                .getInstalledPackagesAsUser(64, intExtra2);
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    ApplicationInfo applicationInfo =
                                            ((PackageInfo) it.next()).applicationInfo;
                                    hashMap.put(
                                            applicationInfo.packageName,
                                            Integer.valueOf(applicationInfo.uid));
                                }
                                ((HashMap) domainFilter2.mUserIdMap)
                                        .put(Integer.valueOf(intExtra2), hashMap);
                            } else {
                                list = null;
                            }
                        } finally {
                        }
                    }
                    synchronized (domainFilter2.mExceptionListLock) {
                        try {
                            if (domainFilter2.mIsExceptionListApplied) {
                                if (list != null && !list.isEmpty()) {
                                    domainFilter2.processPackageExceptionList(list);
                                    domainFilter2.processPlatformPackageExceptionList(list);
                                }
                                synchronized (domainFilter2.mKGExceptionListLock) {
                                    try {
                                        if (domainFilter2.mIsKGExceptionApplied) {
                                            if (list != null && !list.isEmpty()) {
                                                domainFilter2.processKGPackageException(list);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Map map;
            String str;
            switch (this.$r8$classId) {
                case 0:
                    int i = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                    Log.i("DomainFilter", "onReceive() - " + intent);
                    if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                        this.this$0.packageAdded(intent);
                        return;
                    }
                    if (!"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                        if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                            DomainFilter domainFilter = this.this$0;
                            domainFilter.getClass();
                            int intExtra =
                                    intent.getIntExtra("android.intent.extra.user_handle", -1);
                            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                            if (intExtra == -1 || schemeSpecificPart == null) {
                                Log.i(
                                        "DomainFilter",
                                        "packageReplaced() - Received invalid user id or package"
                                            + " name, can't retrieve application info");
                                return;
                            }
                            synchronized (domainFilter.mUserIdMapLock) {
                                map =
                                        (Map)
                                                ((HashMap) domainFilter.mUserIdMap)
                                                        .get(Integer.valueOf(intExtra));
                            }
                            if (map == null || !map.containsKey(schemeSpecificPart)) {
                                domainFilter.packageAdded(intent);
                                return;
                            }
                            int intValue = ((Integer) map.get(schemeSpecificPart)).intValue();
                            int applicationUid =
                                    domainFilter.getApplicationUid(intExtra, schemeSpecificPart);
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    StorageManagerService$$ExternalSyntheticOutline0.m(
                                            intValue,
                                            "packageReplaced() - packageName: ",
                                            schemeSpecificPart,
                                            " oldUid: ",
                                            " newUid:"),
                                    applicationUid,
                                    "DomainFilter");
                            if (applicationUid == -1) {
                                Log.i(
                                        "DomainFilter",
                                        "ackageReplaced() - Failed to retrieve app info");
                                return;
                            }
                            synchronized (domainFilter.mUserIdMapLock) {
                                ((Map)
                                                ((HashMap) domainFilter.mUserIdMap)
                                                        .get(Integer.valueOf(intExtra)))
                                        .put(schemeSpecificPart, Integer.valueOf(applicationUid));
                            }
                            if (intValue != applicationUid) {
                                domainFilter.sendToCache(
                                        3,
                                        Integer.toString(intValue),
                                        Integer.toString(applicationUid),
                                        null,
                                        null,
                                        null);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    DomainFilter domainFilter2 = this.this$0;
                    domainFilter2.getClass();
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                    Log.i(
                            "DomainFilter",
                            "packageRemoved() - packageName: "
                                    + schemeSpecificPart2
                                    + " uid: "
                                    + intExtra2);
                    if (intExtra2 == -1 || schemeSpecificPart2 == null) {
                        return;
                    }
                    synchronized (domainFilter2.mUserIdMapLock) {
                        try {
                            Map map2 =
                                    (Map)
                                            ((HashMap) domainFilter2.mUserIdMap)
                                                    .get(Integer.valueOf(intExtra2));
                            if (map2 == null || !map2.containsKey(schemeSpecificPart2)) {
                                str = null;
                            } else {
                                str = ((Integer) map2.get(schemeSpecificPart2)).toString();
                                map2.remove(schemeSpecificPart2);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (str != null) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(
                                EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra2),
                                "#SelectClause#");
                        contentValues.put("packageName", schemeSpecificPart2);
                        ArrayList arrayList =
                                (ArrayList)
                                        domainFilter2.mEdmStorageProvider.getValues(
                                                "DomainFilterTable",
                                                new String[] {"networkId"},
                                                contentValues);
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        ContentValues contentValues2 = (ContentValues) arrayList.get(0);
                        Integer asInteger = contentValues2.getAsInteger("networkId");
                        int intValue2 = asInteger != null ? asInteger.intValue() : -1;
                        if (intValue2 != -1) {
                            domainFilter2.mNetworkHandler.sendMessage(
                                    Message.obtain(
                                            domainFilter2.mNetworkHandler,
                                            0,
                                            Integer.valueOf(intValue2)));
                            contentValues2.put("networkId", (Integer) (-1));
                            domainFilter2.mEdmStorageProvider.put(
                                    "DomainFilterTable", contentValues2, contentValues);
                        }
                        domainFilter2.sendToCache(
                                2, str, null, null, null, String.valueOf(intValue2));
                        return;
                    }
                    return;
                case 1:
                    onReceive$com$android$server$enterprise$firewall$DomainFilter$2(
                            context, intent);
                    return;
                default:
                    int i2 = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                    Log.i("DomainFilter", "onReceive() - " + intent);
                    if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(intent.getAction())
                            || "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL"
                                    .equals(intent.getAction())) {
                        new Thread(new AnonymousClass4(1, this)).start();
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.DomainFilter$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass4(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Iterator it;
            String str;
            int i;
            boolean isDomainFilterReportEnabledAsUser;
            List<UserInfo> users;
            boolean z;
            boolean z2 = true;
            switch (this.$r8$classId) {
                case 0:
                    DomainFilter domainFilter = (DomainFilter) this.this$0;
                    domainFilter.getClass();
                    Log.d("DomainFilter", "initDaemonCache()");
                    EdmStorageProvider edmStorageProvider = domainFilter.mEdmStorageProvider;
                    ArrayList arrayList =
                            (ArrayList)
                                    edmStorageProvider.getValues("DomainFilterTable", null, null);
                    if (arrayList.isEmpty()) {
                        Log.i("DomainFilter", "initDaemonCache() - No rules found in db");
                    } else {
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            ContentValues contentValues = (ContentValues) it2.next();
                            List listFromDb =
                                    FirewallUtils.getListFromDb(
                                            contentValues, "blacklist", edmStorageProvider);
                            List listFromDb2 =
                                    FirewallUtils.getListFromDb(
                                            contentValues, "whitelist", edmStorageProvider);
                            String asString = contentValues.getAsString("dns1");
                            if (((ArrayList) listFromDb).isEmpty()
                                    && ((ArrayList) listFromDb2).isEmpty()
                                    && TextUtils.isEmpty(asString)) {
                                it = it2;
                            } else {
                                Integer asInteger = contentValues.getAsInteger("adminUid");
                                int intValue = asInteger != null ? asInteger.intValue() : -1;
                                int userId = UserHandle.getUserId(intValue);
                                String asString2 = contentValues.getAsString("packageName");
                                if ("*".equals(asString2)) {
                                    str = Integer.toString(userId);
                                    it = it2;
                                } else {
                                    int applicationUid =
                                            domainFilter.getApplicationUid(userId, asString2);
                                    it = it2;
                                    if (applicationUid != -1) {
                                        String asString3 = contentValues.getAsString("signature");
                                        if (TextUtils.isEmpty(asString3)
                                                || domainFilter.validatePkgSignature(
                                                        userId, asString2, asString3)) {
                                            str = Integer.toString(applicationUid);
                                        } else {
                                            Log.i(
                                                    "DomainFilter",
                                                    "initDaemonCache() - app signature mismatch");
                                        }
                                    } else {
                                        str = null;
                                    }
                                }
                                if (str == null) {
                                    continue;
                                } else {
                                    if (asString != null) {
                                        i =
                                                domainFilter.setupNetworkDns(
                                                        asString,
                                                        contentValues.getAsString("dns2"));
                                        if (i != -1) {
                                            contentValues.put("networkId", Integer.valueOf(i));
                                            ContentValues contentValues2 = new ContentValues();
                                            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                                                    intValue,
                                                    contentValues2,
                                                    "adminUid",
                                                    "packageName",
                                                    asString2);
                                            edmStorageProvider.put(
                                                    "DomainFilterTable",
                                                    contentValues,
                                                    contentValues2);
                                        }
                                    } else {
                                        i = -1;
                                    }
                                    UserManager userManager =
                                            (UserManager)
                                                    domainFilter.mContext.getSystemService("user");
                                    if (userManager != null) {
                                        long clearCallingIdentity = Binder.clearCallingIdentity();
                                        try {
                                            List users2 = userManager.getUsers();
                                            if (users2 != null) {
                                                Iterator it3 = users2.iterator();
                                                while (it3.hasNext()
                                                        && (!(isDomainFilterReportEnabledAsUser =
                                                                        domainFilter
                                                                                .isDomainFilterReportEnabledAsUser(
                                                                                        ((UserInfo)
                                                                                                        it3
                                                                                                                .next())
                                                                                                .id))
                                                                || !domainFilter
                                                                        .registerDomainFilterListener(
                                                                                isDomainFilterReportEnabledAsUser))) {}
                                            }
                                        } finally {
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                        }
                                    }
                                    domainFilter.sendToCache(
                                            0,
                                            str,
                                            null,
                                            listFromDb,
                                            listFromDb2,
                                            String.valueOf(i));
                                }
                            }
                            it2 = it;
                        }
                        DomainFilterNapCommon domainFilterNapCommon =
                                domainFilter.mDomainFilterNapCommon;
                        domainFilterNapCommon.setDomainFilterEnabled(true);
                        if (domainFilter.isDomainFilterOnIptablesEnabledAtAll()) {
                            domainFilterNapCommon.setDomainFilterOnIptablesEnabled(true);
                        }
                    }
                    DomainFilter domainFilter2 = (DomainFilter) this.this$0;
                    domainFilter2.sendToCache(5, domainFilter2.mServer, null, null, null, null);
                    DomainFilter domainFilter3 = (DomainFilter) this.this$0;
                    domainFilter3.sendToCache(
                            4,
                            String.valueOf(
                                    domainFilter3.getApplicationUid(
                                            0, "com.google.android.captiveportallogin")),
                            null,
                            null,
                            null,
                            null);
                    return;
                default:
                    DomainFilter domainFilter4 = ((AnonymousClass1) this.this$0).this$0;
                    int i2 = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                    domainFilter4.getClass();
                    Log.i("DomainFilter", "initUserIdMap() - Initializing UserID Mapping");
                    PackageManager packageManager = domainFilter4.mContext.getPackageManager();
                    UserManager userManager2 =
                            (UserManager) domainFilter4.mContext.getSystemService("user");
                    if (userManager2 == null || (users = userManager2.getUsers()) == null) {
                        return;
                    }
                    synchronized (domainFilter4.mExceptionListLock) {
                        try {
                            if (domainFilter4.mIsExceptionListApplied
                                    || !domainFilter4.hasAnyRuleInDatabase()) {
                                z = false;
                            } else {
                                domainFilter4.mIsExceptionListApplied = true;
                                z = true;
                            }
                        } finally {
                        }
                    }
                    synchronized (domainFilter4.mKGExceptionListLock) {
                        try {
                            if (!domainFilter4.mIsKGExceptionApplied
                                    && domainFilter4.hasAnyRuleInDatabase()
                                    && FirewallUtils.isKGExemptRuleRequired()) {
                                domainFilter4.mIsKGExceptionApplied = true;
                            } else {
                                z2 = false;
                            }
                        } finally {
                        }
                    }
                    ArrayList arrayList2 = new ArrayList();
                    synchronized (domainFilter4.mUserIdMapLock) {
                        try {
                            for (UserInfo userInfo : users) {
                                HashMap hashMap = new HashMap();
                                List installedPackagesAsUser =
                                        packageManager.getInstalledPackagesAsUser(64, userInfo.id);
                                Iterator it4 = installedPackagesAsUser.iterator();
                                while (it4.hasNext()) {
                                    ApplicationInfo applicationInfo =
                                            ((PackageInfo) it4.next()).applicationInfo;
                                    hashMap.put(
                                            applicationInfo.packageName,
                                            Integer.valueOf(applicationInfo.uid));
                                }
                                ((HashMap) domainFilter4.mUserIdMap)
                                        .put(Integer.valueOf(userInfo.id), hashMap);
                                arrayList2.addAll(installedPackagesAsUser);
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        domainFilter4.processPackageExceptionList(arrayList2);
                        domainFilter4.processPlatformPackageExceptionList(arrayList2);
                    }
                    if (z2) {
                        domainFilter4.processKGPackageException(arrayList2);
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlockReport {
        public String mTimeStamp;
        public String mUid;
        public String mUrl;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DomainFilterEventListener extends Binder
            implements IDomainFilterEventListener {
        public DomainFilterEventListener() {
            attachInterface(this, "com.android.internal.net.IDomainFilterEventListener");
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            int i3;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.internal.net.IDomainFilterEventListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.internal.net.IDomainFilterEventListener");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            long readLong = parcel.readLong();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            DomainFilter domainFilter = DomainFilter.this;
            domainFilter.getClass();
            String str = null;
            if (readInt2 < 0) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        readInt2,
                        "getPackageFromRunningProcesses() - Invalid PID provided: ",
                        "DomainFilter");
            } else if (readInt < 0) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        readInt,
                        "getPackageFromRunningProcesses() - Invalid UID provided: ",
                        "DomainFilter");
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ActivityManager activityManager =
                            (ActivityManager) domainFilter.mContext.getSystemService("activity");
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                            activityManager != null
                                    ? activityManager.getRunningAppProcesses()
                                    : null;
                    if (runningAppProcesses != null) {
                        Iterator<ActivityManager.RunningAppProcessInfo> it =
                                runningAppProcesses.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ActivityManager.RunningAppProcessInfo next = it.next();
                            if (readInt2 == next.pid) {
                                String[] strArr = next.pkgList;
                                if (strArr != null) {
                                    if (strArr.length == 1) {
                                        str = strArr[0];
                                    } else {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(
                                                EdmStorageProviderBase.getAdminLUIDWhereIn(
                                                        0, UserHandle.getUserId(readInt)),
                                                "#SelectClause#");
                                        ArrayList arrayList =
                                                (ArrayList)
                                                        domainFilter.mEdmStorageProvider.getValues(
                                                                "DomainFilterTable",
                                                                new String[] {"packageName"},
                                                                contentValues);
                                        if (!arrayList.isEmpty()) {
                                            List asList = Arrays.asList(next.pkgList);
                                            Iterator it2 = arrayList.iterator();
                                            while (it2.hasNext()) {
                                                str =
                                                        ((ContentValues) it2.next())
                                                                .getAsString("packageName");
                                                if (asList.contains(str)) {
                                                    break;
                                                }
                                            }
                                        }
                                        str = next.pkgList[0];
                                    }
                                }
                            }
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if (str == null) {
                int i4 = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                Log.d("DomainFilter", "failed to find application which was blocked");
            } else {
                String valueOf = String.valueOf(readLong);
                String valueOf2 = String.valueOf(readInt);
                BlockReport blockReport = new BlockReport();
                blockReport.mTimeStamp = valueOf;
                blockReport.mUid = valueOf2;
                blockReport.mUrl = readString;
                boolean z = false;
                for (int i5 = 0; i5 < 5; i5++) {
                    BlockReport blockReport2 = DomainFilter.this.mReportCache[i5];
                    if (blockReport2 != null) {
                        String str2 = blockReport.mTimeStamp;
                        String str3 = blockReport2.mTimeStamp;
                        if ((str3 != null || str2 == null)
                                && ((str3 == null || str2 != null)
                                        && (str3 == null || str3.equals(str2)))) {
                            String str4 = blockReport.mUid;
                            String str5 = blockReport2.mUid;
                            if ((str5 != null || str4 == null)
                                    && ((str5 == null || str4 != null)
                                            && (str5 == null || str5.equals(str4)))) {
                                String str6 = blockReport.mUrl;
                                String str7 = blockReport2.mUrl;
                                if ((str7 != null || str6 == null)
                                        && ((str7 == null || str6 != null)
                                                && (str7 == null || str7.equals(str6)))) {
                                    z = true;
                                }
                            }
                        }
                    }
                }
                if (!z) {
                    DomainFilter domainFilter2 = DomainFilter.this;
                    BlockReport[] blockReportArr = domainFilter2.mReportCache;
                    int i6 = domainFilter2.mReportCacheIndex;
                    blockReportArr[i6] = blockReport;
                    int i7 = i6 + 1;
                    domainFilter2.mReportCacheIndex = i7;
                    if (i7 == 5) {
                        domainFilter2.mReportCacheIndex = 0;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo =
                            ((ActivityManager) domainFilter2.mContext.getSystemService("activity"))
                                    .getRunningTasks(1)
                                    .get(0);
                    Intent intent =
                            new Intent("com.samsung.android.knox.intent.action.BLOCKED_DOMAIN");
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_PACKAGENAME",
                            str);
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_TIMESTAMP",
                            valueOf);
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_UID", valueOf2);
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_URL", readString);
                    if (str.equals(runningTaskInfo.topActivity.getPackageName())) {
                        intent.putExtra(
                                "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_ISFOREGROUND",
                                true);
                    } else {
                        intent.putExtra(
                                "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_ISFOREGROUND",
                                false);
                    }
                    DomainFilter.this.mContext.sendBroadcastAsUser(
                            intent,
                            UserHandle.ALL,
                            "com.samsung.android.knox.permission.KNOX_FIREWALL");
                    DomainFilter domainFilter3 = DomainFilter.this;
                    domainFilter3.getClass();
                    try {
                        i3 = UserHandle.getUserId(Integer.parseInt(valueOf2));
                    } catch (NumberFormatException e) {
                        Log.e(
                                "DomainFilter",
                                "saveReportInDatabase() - Error parsing userId received from"
                                    + " daemon.",
                                e);
                        i3 = -1;
                    }
                    if (i3 != -1) {
                        if (((HashSet) domainFilter3.mDomainReportCache)
                                .contains(Integer.valueOf(i3))) {
                            ContentValues m =
                                    AccountManagerService$$ExternalSyntheticOutline0.m(
                                            "time", valueOf);
                            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                                    i3, m, "userId", "packageName", str);
                            m.put("url", readString);
                            if (domainFilter3.mEdmStorageProvider.insert(
                                            "DomainFilterReportTable", m)
                                    == -1) {
                                Log.e(
                                        "DomainFilter",
                                        "saveReportInDatabase() - Failed to save domain report in"
                                            + " database");
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkIdInfo {
        public final String mDns1;
        public final String mDns2;
        public final int mNetId;
        public int mUsageCounter = 1;

        public NetworkIdInfo(int i, String str, String str2) {
            this.mNetId = i;
            this.mDns1 = str;
            this.mDns2 = str2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{Net Id= ");
            sb.append(this.mNetId);
            sb.append(" , DNS(1)=");
            sb.append(this.mDns1);
            sb.append(" , DNS(2)=");
            sb.append(this.mDns2);
            sb.append(" , counter=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUsageCounter, sb, "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkManagementHandler extends Handler {
        public NetworkManagementHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005d  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r7) {
            /*
                Method dump skipped, instructions count: 244
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.enterprise.firewall.DomainFilter.NetworkManagementHandler.handleMessage(android.os.Message):void");
        }
    }

    /* renamed from: -$$Nest$mextractHost, reason: not valid java name */
    public static String m515$$Nest$mextractHost(DomainFilter domainFilter, String str) {
        URL url;
        domainFilter.getClass();
        if (str == null) {
            return null;
        }
        try {
            url = new URL(str);
        } catch (MalformedURLException unused) {
            Log.e("DomainFilter", "extractHost() - Invalid url ".concat(str));
            url = null;
        }
        if (url != null) {
            return url.getHost();
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.enterprise.firewall.DomainFilter$5] */
    public DomainFilter(Context context, FirewallRulesApplier firewallRulesApplier) {
        Injector injector = new Injector();
        this.mUserIdMapLock = new Object();
        this.mNetworkIdListLock = new Object();
        this.mNetIdManager = new KnoxNetIdManager();
        this.mDomainFilterEventListener = null;
        this.mReportCache = new BlockReport[5];
        this.mReportCacheIndex = 0;
        this.mIsExceptionListApplied = false;
        this.mExceptionListLock = new Object();
        this.mIsKGExceptionApplied = false;
        this.mKGExceptionListLock = new Object();
        this.mDomainFilterBrokenRules = new HashMap();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 0);
        AnonymousClass1 anonymousClass12 = new AnonymousClass1(this, 1);
        AnonymousClass1 anonymousClass13 = new AnonymousClass1(this, 2);
        this.mCaptivePortalNetworkCallback =
                new ConnectivityManager.NetworkCallback() { // from class:
                    // com.android.server.enterprise.firewall.DomainFilter.5
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(final Network network) {
                        super.onAvailable(network);
                        try {
                            NetworkCapabilities networkCapabilities =
                                    DomainFilter.this
                                            .getConnectivityService()
                                            .getNetworkCapabilities(network);
                            if (networkCapabilities == null
                                    || !networkCapabilities.hasCapability(17)) {
                                return;
                            }
                            int i = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                            Log.i("DomainFilter", "Captive portal detected");
                            if (DomainFilter.this.hasAnyRuleInDatabase()) {
                                final DomainFilter domainFilter = DomainFilter.this;
                                domainFilter.getClass();
                                new Thread(
                                                new Runnable() { // from class:
                                                    // com.android.server.enterprise.firewall.DomainFilter.6
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        URL url;
                                                        DomainFilter.this.getClass();
                                                        HttpURLConnection httpURLConnection = null;
                                                        HttpURLConnection httpURLConnection2 = null;
                                                        try {
                                                            url =
                                                                    new URL(
                                                                            "http://connectivitycheck.gstatic.com/generate_204");
                                                        } catch (MalformedURLException unused) {
                                                            Log.e(
                                                                    "DomainFilter",
                                                                    "Invalid URL"
                                                                        + " http://connectivitycheck.gstatic.com/generate_204");
                                                            url = null;
                                                        }
                                                        try {
                                                            if (url == null) {
                                                                int i2 =
                                                                        DomainFilter
                                                                                .MAX_LIST_SIZE_IN_BYTES;
                                                                Log.e(
                                                                        "DomainFilter",
                                                                        "Captive portal url is"
                                                                            + " null");
                                                                return;
                                                            }
                                                            try {
                                                                HttpURLConnection
                                                                        httpURLConnection3 =
                                                                                (HttpURLConnection)
                                                                                        network
                                                                                                .openConnection(
                                                                                                        url);
                                                                try {
                                                                    httpURLConnection3
                                                                            .setInstanceFollowRedirects(
                                                                                    false);
                                                                    httpURLConnection3
                                                                            .setConnectTimeout(
                                                                                    10000);
                                                                    httpURLConnection3
                                                                            .setReadTimeout(10000);
                                                                    httpURLConnection3.setUseCaches(
                                                                            false);
                                                                    String headerField =
                                                                            httpURLConnection3
                                                                                    .getHeaderField(
                                                                                            "Location");
                                                                    boolean isEmpty =
                                                                            TextUtils.isEmpty(
                                                                                    headerField);
                                                                    String str = headerField;
                                                                    if (!isEmpty) {
                                                                        String
                                                                                m515$$Nest$mextractHost =
                                                                                        DomainFilter
                                                                                                .m515$$Nest$mextractHost(
                                                                                                        DomainFilter
                                                                                                                .this,
                                                                                                        headerField);
                                                                        boolean isEmpty2 =
                                                                                TextUtils.isEmpty(
                                                                                        m515$$Nest$mextractHost);
                                                                        String str2 = isEmpty2;
                                                                        if (isEmpty2 == 0) {
                                                                            DomainFilter.this
                                                                                    .getClass();
                                                                            boolean
                                                                                    validateIpv4Address =
                                                                                            FirewallRuleValidator
                                                                                                    .validateIpv4Address(
                                                                                                            m515$$Nest$mextractHost);
                                                                            str2 =
                                                                                    validateIpv4Address;
                                                                            if (validateIpv4Address
                                                                                    == 0) {
                                                                                boolean
                                                                                        validateIpv6Address =
                                                                                                FirewallRuleValidator
                                                                                                        .validateIpv6Address(
                                                                                                                m515$$Nest$mextractHost);
                                                                                if (validateIpv6Address
                                                                                        != 0) {
                                                                                    str2 =
                                                                                            validateIpv6Address;
                                                                                } else {
                                                                                    DomainFilter
                                                                                            .this
                                                                                            .sendToCache(
                                                                                                    5,
                                                                                                    m515$$Nest$mextractHost,
                                                                                                    null,
                                                                                                    null,
                                                                                                    null,
                                                                                                    null);
                                                                                    str =
                                                                                            validateIpv6Address;
                                                                                }
                                                                            }
                                                                        }
                                                                        Log.e(
                                                                                "DomainFilter",
                                                                                "invalid captive"
                                                                                    + " portal"
                                                                                    + " host");
                                                                        str = str2;
                                                                    }
                                                                    httpURLConnection3.disconnect();
                                                                    httpURLConnection = str;
                                                                } catch (IOException e) {
                                                                    e = e;
                                                                    httpURLConnection2 =
                                                                            httpURLConnection3;
                                                                    int i3 =
                                                                            DomainFilter
                                                                                    .MAX_LIST_SIZE_IN_BYTES;
                                                                    Log.e(
                                                                            "DomainFilter",
                                                                            "Exception occured: "
                                                                                    + e
                                                                                            .getLocalizedMessage());
                                                                    httpURLConnection =
                                                                            httpURLConnection2;
                                                                    if (httpURLConnection2
                                                                            != null) {
                                                                        httpURLConnection2
                                                                                .disconnect();
                                                                        httpURLConnection =
                                                                                httpURLConnection2;
                                                                    }
                                                                } catch (Throwable th) {
                                                                    th = th;
                                                                    httpURLConnection =
                                                                            httpURLConnection3;
                                                                    if (httpURLConnection != null) {
                                                                        httpURLConnection
                                                                                .disconnect();
                                                                    }
                                                                    throw th;
                                                                }
                                                            } catch (IOException e2) {
                                                                e = e2;
                                                            }
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                        }
                                                    }
                                                })
                                        .start();
                            }
                        } catch (Exception e) {
                            int i2 = DomainFilter.MAX_LIST_SIZE_IN_BYTES;
                            Log.e("DomainFilter", "Exception occured: " + e.getLocalizedMessage());
                        }
                    }
                };
        this.mInjector = injector;
        Objects.requireNonNull(context);
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mFirewallRulesApplier = firewallRulesApplier;
        this.mNetworkIdList = new ArrayList();
        HandlerThread handlerThread = new HandlerThread("DomainFilterHandlerThread");
        handlerThread.start();
        this.mNetworkHandler = new NetworkManagementHandler(handlerThread.getLooper());
        this.mUserIdMap = new HashMap();
        this.mDomainFilterNapCommon = DomainFilterNapCommon.getInstance(context);
        this.mDnsResolverAdapter = DnsResolverAdapter.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass1, userHandle, intentFilter, null, null);
        context.registerReceiverAsUser(
                anonymousClass12,
                userHandle,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.USER_REMOVED", "android.intent.action.USER_ADDED"),
                null,
                null,
                2);
        context.registerReceiverAsUser(
                anonymousClass13,
                userHandle,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL"),
                null,
                null,
                2);
        String string =
                Settings.Global.getString(context.getContentResolver(), "captive_portal_server");
        this.mServer = string;
        if (string == null) {
            this.mServer = "connectivitycheck.gstatic.com";
        }
        new Thread(new AnonymousClass4(0, this)).start();
        Log.i("DomainFilter", "initReportCache()");
        this.mDomainReportCache = new HashSet();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, (Integer) 1);
        ArrayList arrayList =
                (ArrayList)
                        edmStorageProvider.getValues(
                                "DomainFilterReportStatus", null, contentValues);
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Integer asInteger = ((ContentValues) it.next()).getAsInteger("userId");
                ((HashSet) this.mDomainReportCache)
                        .add(Integer.valueOf(asInteger != null ? asInteger.intValue() : -1));
            }
        }
        ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                .registerNetworkCallback(
                        new NetworkRequest.Builder().addTransportType(1).addCapability(17).build(),
                        this.mCaptivePortalNetworkCallback);
    }

    public static void prepareDomainUrls(DomainFilterRule domainFilterRule) {
        List allowDomains = domainFilterRule.getAllowDomains();
        if (allowDomains != null && !allowDomains.isEmpty()) {
            for (int i = 0; i < allowDomains.size(); i++) {
                if (allowDomains.get(i) != null) {
                    allowDomains.set(i, ((String) allowDomains.get(i)).trim().toLowerCase());
                } else {
                    allowDomains.set(i, "");
                }
            }
            domainFilterRule.setAllowDomains(allowDomains);
        }
        List denyDomains = domainFilterRule.getDenyDomains();
        if (denyDomains == null || denyDomains.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < denyDomains.size(); i2++) {
            if (denyDomains.get(i2) != null) {
                denyDomains.set(i2, ((String) denyDomains.get(i2)).trim().toLowerCase());
            } else {
                denyDomains.set(i2, "");
            }
        }
        domainFilterRule.setDenyDomains(denyDomains);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setDomainFilterRulesProperties(
            DomainFilterRule domainFilterRule, JSONArray jSONArray) {
        int i;
        if (domainFilterRule.getApplication() == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pN", domainFilterRule.getApplication().getPackageName());
            if (domainFilterRule.getDns1() == null && domainFilterRule.getDns2() == null) {
                i = 1;
                jSONObject.put("cDNS", i);
                jSONObject.put(
                        "rlTp",
                        (!ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())
                                        || ArrayUtils.isEmpty(domainFilterRule.getDenyDomains()))
                                ? ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())
                                : 2);
                jSONArray.put(jSONObject);
            }
            i = 0;
            jSONObject.put("cDNS", i);
            jSONObject.put(
                    "rlTp",
                    (!ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())
                                    || ArrayUtils.isEmpty(domainFilterRule.getDenyDomains()))
                            ? ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())
                            : 2);
            jSONArray.put(jSONObject);
        } catch (JSONException e) {
            Log.e("DomainFilter", "Failed to put rule in json: " + e.getMessage());
        }
    }

    public static FirewallResponse validateApplicationIdentity(AppIdentity appIdentity) {
        if (appIdentity == null) {
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                    "Invalid AppIdentity object.");
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        if ("*".equals(packageName) && signature != null) {
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                    "There is no signature related to all applications.");
        }
        if (FirewallRuleValidator.validatePackageName(packageName)) {
            return null;
        }
        return new FirewallResponse(
                FirewallResponse.Result.FAILED,
                FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Invalid package name. - ", packageName));
    }

    public static boolean validateDomains(DomainFilterRule domainFilterRule) {
        if (domainFilterRule.getDenyDomains() == null
                && domainFilterRule.getAllowDomains() == null) {
            return false;
        }
        if (!ArrayUtils.isEmpty(domainFilterRule.getDenyDomains())) {
            Iterator it = domainFilterRule.getDenyDomains().iterator();
            while (it.hasNext()) {
                if (!FirewallUtils.validateDomain((String) it.next())) {
                    return false;
                }
            }
        }
        if (ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())) {
            return true;
        }
        Iterator it2 = domainFilterRule.getAllowDomains().iterator();
        while (it2.hasNext()) {
            if (!FirewallUtils.validateDomain((String) it2.next())) {
                return false;
            }
        }
        return true;
    }

    public final synchronized FirewallResponse[] addDomainFilterRules(
            ContextInfo contextInfo, List list) {
        Map map;
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z;
        int i;
        Map map2;
        int i2;
        JSONArray jSONArray;
        boolean z2;
        boolean z3;
        ContentValues contentValues;
        List list2;
        List list3;
        List list4;
        ContextInfo contextInfo2;
        boolean z4;
        int i3;
        int i4;
        Integer asInteger;
        ContextInfo contextInfo3 = contextInfo;
        synchronized (this) {
            ArrayList arrayList3 = (ArrayList) list;
            boolean z5 = true;
            int i5 = 0;
            if (arrayList3.isEmpty()) {
                Log.i("DomainFilter", "addDomainFilterRules() - No rule specified");
                return new FirewallResponse[] {
                    new FirewallResponse(
                            FirewallResponse.Result.FAILED,
                            FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                            "No rule was specified.")
                };
            }
            Log.i("DomainFilter", "addDomainFilterRules() - rules.size = " + arrayList3.size());
            int i6 = contextInfo3.mCallerUid;
            int userId = UserHandle.getUserId(i6);
            if (checkAdminInDatabase(i6) == -1) {
                Log.i(
                        "DomainFilter",
                        "addDomainFilterRules() - Another admin already have domain filter rules in"
                            + " the database");
                return new FirewallResponse[] {
                    new FirewallResponse(
                            FirewallResponse.Result.FAILED,
                            FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR,
                            "This administrator can't execute this operation because he is not the"
                                + " owner.")
                };
            }
            synchronized (this.mUserIdMapLock) {
                map = (Map) ((HashMap) this.mUserIdMap).get(Integer.valueOf(userId));
            }
            int size = arrayList3.size();
            FirewallResponse[] firewallResponseArr = new FirewallResponse[size];
            JSONArray jSONArray2 = new JSONArray();
            int i7 = 0;
            while (i7 < arrayList3.size()) {
                DomainFilterRule domainFilterRule = (DomainFilterRule) arrayList3.get(i7);
                if (domainFilterRule == null) {
                    Log.i(
                            "DomainFilter",
                            "addDomainFilterRules() - Skipping invalid rule - No rule specified");
                    firewallResponseArr[i7] =
                            new FirewallResponse(
                                    FirewallResponse.Result.FAILED,
                                    FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                                    "No rule was specified.");
                } else {
                    String dns1 = domainFilterRule.getDns1();
                    String dns2 = domainFilterRule.getDns2();
                    boolean isEmpty = TextUtils.isEmpty(dns1) ^ z5;
                    if (!isEmpty
                            && TextUtils.isEmpty(dns2)
                            && (domainFilterRule.getDenyDomains() == null
                                    || domainFilterRule.getAllowDomains() == null
                                    || (domainFilterRule.getDenyDomains().isEmpty()
                                            && domainFilterRule.getAllowDomains().isEmpty()))) {
                        Log.i(
                                "DomainFilter",
                                "addDomainFilterRules() - Skipping invalid rule - mandatory"
                                    + " parameters missing");
                        firewallResponseArr[i7] =
                                new FirewallResponse(
                                        FirewallResponse.Result.FAILED,
                                        FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                                        "No parameters provided.");
                    } else {
                        setDomainFilterRulesProperties(domainFilterRule, jSONArray2);
                        FirewallResponse validateApplicationIdentity =
                                validateApplicationIdentity(domainFilterRule.getApplication());
                        firewallResponseArr[i7] = validateApplicationIdentity;
                        if (validateApplicationIdentity != null) {
                            Log.i(
                                    "DomainFilter",
                                    "addDomainFilterRules() - Skipping invalid rule - invalid"
                                        + " Application Identity: "
                                            + firewallResponseArr[i7].getMessage());
                        } else {
                            String packageName = domainFilterRule.getApplication().getPackageName();
                            String signature = domainFilterRule.getApplication().getSignature();
                            if (TextUtils.isEmpty(signature)) {
                                arrayList2 = arrayList3;
                                z = true;
                            } else {
                                z = validatePkgSignature(userId, packageName, signature);
                                arrayList2 = arrayList3;
                            }
                            boolean verifyAppInstalled =
                                    !"*".equals(packageName)
                                            ? verifyAppInstalled(map, packageName, userId)
                                            : true;
                            if (z || !verifyAppInstalled) {
                                if ((!isEmpty && !TextUtils.isEmpty(dns2))
                                        || (isEmpty
                                                && (!FirewallRuleValidator.validateIpv4Address(dns1)
                                                        ? !FirewallRuleValidator
                                                                        .validateIpv6Address(dns1)
                                                                || (dns2 != null
                                                                        && !FirewallRuleValidator
                                                                                .validateIpv6Address(
                                                                                        dns2))
                                                        : dns2 != null
                                                                && !FirewallRuleValidator
                                                                        .validateIpv4Address(
                                                                                dns2)))) {
                                    Log.i(
                                            "DomainFilter",
                                            "addDomainFilterRules() - Skipping invalid rule -"
                                                + " invalid DNS");
                                    firewallResponseArr[i7] =
                                            new FirewallResponse(
                                                    FirewallResponse.Result.FAILED,
                                                    FirewallResponse.ErrorCode
                                                            .INVALID_PARAMETER_ERROR,
                                                    "Invalid DNS(s) provided");
                                }
                                if (validateDomains(domainFilterRule)) {
                                    i = userId;
                                    ContentValues contentValues2 = new ContentValues();
                                    map2 = map;
                                    i2 = size;
                                    contentValues2.put("adminUid", Integer.valueOf(i6));
                                    contentValues2.put("packageName", packageName);
                                    contentValues2.put("signature", signature);
                                    ContentValues contentValues3 = new ContentValues();
                                    jSONArray = jSONArray2;
                                    contentValues3.put("adminUid", Integer.valueOf(i6));
                                    contentValues3.put("packageName", packageName);
                                    List values =
                                            this.mEdmStorageProvider.getValues(
                                                    "DomainFilterTable", null, contentValues3);
                                    ContentValues contentValues4 = new ContentValues();
                                    ArrayList arrayList4 = (ArrayList) values;
                                    if (arrayList4.isEmpty()) {
                                        z2 = false;
                                    } else {
                                        contentValues4 = (ContentValues) arrayList4.get(0);
                                        z2 = true;
                                    }
                                    prepareDomainUrls(domainFilterRule);
                                    List emptyList =
                                            domainFilterRule.getDenyDomains() == null
                                                    ? Collections.emptyList()
                                                    : domainFilterRule.getDenyDomains();
                                    List emptyList2 =
                                            domainFilterRule.getAllowDomains() == null
                                                    ? Collections.emptyList()
                                                    : domainFilterRule.getAllowDomains();
                                    List arrayList5 = new ArrayList();
                                    List arrayList6 = new ArrayList();
                                    if (emptyList.isEmpty() && emptyList2.isEmpty()) {
                                        list3 = arrayList5;
                                        z3 = z2;
                                        list4 = arrayList6;
                                        contentValues = contentValues3;
                                    } else {
                                        z3 = z2;
                                        List listFromDb =
                                                FirewallUtils.getListFromDb(
                                                        contentValues4,
                                                        "whitelist",
                                                        this.mEdmStorageProvider);
                                        contentValues = contentValues3;
                                        List listFromDb2 =
                                                FirewallUtils.getListFromDb(
                                                        contentValues4,
                                                        "blacklist",
                                                        this.mEdmStorageProvider);
                                        if (listFromDb2.containsAll(emptyList)) {
                                            list2 = emptyList2;
                                            if (listFromDb.containsAll(list2) && !isEmpty) {
                                                Log.i(
                                                        "DomainFilter",
                                                        "addDomainFilterRules() - Skipping rule -"
                                                            + " This rule is already in the"
                                                            + " database");
                                                firewallResponseArr[i7] =
                                                        new FirewallResponse(
                                                                FirewallResponse.Result.NO_CHANGES,
                                                                FirewallResponse.ErrorCode.NO_ERROR,
                                                                "The specified rule is already in"
                                                                    + " the database.");
                                                contextInfo2 = contextInfo;
                                                i5 = 0;
                                                i7++;
                                                contextInfo3 = contextInfo2;
                                                userId = i;
                                                arrayList3 = arrayList2;
                                                map = map2;
                                                size = i2;
                                                jSONArray2 = jSONArray;
                                                z5 = true;
                                            }
                                        } else {
                                            list2 = emptyList2;
                                        }
                                        for (int i8 = 0; i8 < list2.size(); i8++) {
                                            String str = (String) list2.get(i8);
                                            if (emptyList.contains(str)) {
                                                emptyList.remove(str);
                                            }
                                        }
                                        list3 = listFromDb;
                                        list4 = listFromDb2;
                                    }
                                    domainFilterRule.setDenyDomains(emptyList);
                                    if (isEmpty) {
                                        if (verifyAppInstalled) {
                                            i4 = setupNetworkDns(dns1, dns2);
                                            if (i4 == -1) {
                                                Log.i(
                                                        "DomainFilter",
                                                        "addDomainFilterRules() - Skipping invalid"
                                                            + " rule - Invalid netID");
                                                firewallResponseArr[i7] =
                                                        new FirewallResponse(
                                                                FirewallResponse.Result.FAILED,
                                                                FirewallResponse.ErrorCode
                                                                        .UNEXPECTED_ERROR,
                                                                "Error occurred applying DNS(s)");
                                                contextInfo2 = contextInfo;
                                                i5 = 0;
                                                i7++;
                                                contextInfo3 = contextInfo2;
                                                userId = i;
                                                arrayList3 = arrayList2;
                                                map = map2;
                                                size = i2;
                                                jSONArray2 = jSONArray;
                                                z5 = true;
                                            } else if (!contentValues4.containsKey("networkId")
                                                    || (asInteger =
                                                                    contentValues4.getAsInteger(
                                                                            "networkId"))
                                                            == null) {
                                                z4 = verifyAppInstalled;
                                            } else {
                                                z4 = verifyAppInstalled;
                                                if (asInteger.intValue() != -1
                                                        && i4 != asInteger.intValue()) {
                                                    this.mNetworkHandler.sendMessage(
                                                            Message.obtain(
                                                                    this.mNetworkHandler,
                                                                    0,
                                                                    asInteger));
                                                }
                                            }
                                        } else {
                                            z4 = verifyAppInstalled;
                                            i4 = -1;
                                        }
                                        contentValues2.put("dns1", dns1);
                                        contentValues2.put("dns2", dns2);
                                        contentValues2.put("networkId", Integer.valueOf(i4));
                                        i3 = i4;
                                    } else {
                                        z4 = verifyAppInstalled;
                                        i3 = -1;
                                    }
                                    boolean hasDenyRuleInDatabase =
                                            this.mFirewallRulesApplier.hasDenyRuleInDatabase(
                                                    i6, packageName);
                                    ContentValues contentValues5 = contentValues;
                                    if (this.mEdmStorageProvider.put(
                                            "DomainFilterTable", contentValues2, contentValues5)) {
                                        contextInfo2 = contextInfo;
                                        if (addDomainListInDataBase(
                                                contextInfo2, domainFilterRule, list4, list3)) {
                                            if (z4) {
                                                i5 = 0;
                                                updateDaemonCache(
                                                        contextInfo2,
                                                        domainFilterRule,
                                                        0,
                                                        String.valueOf(i3));
                                            } else {
                                                i5 = 0;
                                            }
                                            processPackageExceptionListDelayed();
                                            processKGPackageExceptionDelayed();
                                            firewallResponseArr[i7] =
                                                    new FirewallResponse(
                                                            FirewallResponse.Result.SUCCESS,
                                                            FirewallResponse.ErrorCode.NO_ERROR,
                                                            "The rule(s) was successfully"
                                                                + " added/updated.");
                                            this.mDomainFilterNapCommon.setDomainFilterEnabled(
                                                    true);
                                            AppIdentity appIdentity =
                                                    new AppIdentity(packageName, signature);
                                            if (!hasDenyRuleInDatabase
                                                    && this.mFirewallRulesApplier.blockPort53(
                                                            appIdentity, contextInfo2, true)) {
                                                Log.i(
                                                        "DomainFilter",
                                                        "blockPort53() - port53 rule added"
                                                            + " successfully. Package: "
                                                                + packageName);
                                            }
                                            i7++;
                                            contextInfo3 = contextInfo2;
                                            userId = i;
                                            arrayList3 = arrayList2;
                                            map = map2;
                                            size = i2;
                                            jSONArray2 = jSONArray;
                                            z5 = true;
                                        } else {
                                            Log.i(
                                                    "DomainFilter",
                                                    "addDomainFilterRules() - Failed to add rule in"
                                                        + " the database -domains list");
                                            firewallResponseArr[i7] =
                                                    new FirewallResponse(
                                                            FirewallResponse.Result.FAILED,
                                                            FirewallResponse.ErrorCode
                                                                    .DATABASE_ERROR,
                                                            "Failed to add/update rule in the"
                                                                + " database.");
                                            if (!z3) {
                                                this.mEdmStorageProvider.delete(
                                                        "DomainFilterTable", contentValues5);
                                            }
                                            if (isEmpty) {
                                                this.mNetworkHandler.sendMessage(
                                                        Message.obtain(
                                                                this.mNetworkHandler,
                                                                0,
                                                                Integer.valueOf(i3)));
                                            }
                                            i5 = 0;
                                            i7++;
                                            contextInfo3 = contextInfo2;
                                            userId = i;
                                            arrayList3 = arrayList2;
                                            map = map2;
                                            size = i2;
                                            jSONArray2 = jSONArray;
                                            z5 = true;
                                        }
                                    } else {
                                        Log.i(
                                                "DomainFilter",
                                                "addDomainFilterRules() - Failed to add rule in the"
                                                    + " database");
                                        firewallResponseArr[i7] =
                                                new FirewallResponse(
                                                        FirewallResponse.Result.FAILED,
                                                        FirewallResponse.ErrorCode.DATABASE_ERROR,
                                                        "Failed to add/update rule in the"
                                                            + " database.");
                                        if (isEmpty) {
                                            this.mNetworkHandler.sendMessage(
                                                    Message.obtain(
                                                            this.mNetworkHandler,
                                                            0,
                                                            Integer.valueOf(i3)));
                                        }
                                        contextInfo2 = contextInfo;
                                        i5 = 0;
                                        i7++;
                                        contextInfo3 = contextInfo2;
                                        userId = i;
                                        arrayList3 = arrayList2;
                                        map = map2;
                                        size = i2;
                                        jSONArray2 = jSONArray;
                                        z5 = true;
                                    }
                                } else {
                                    Log.i(
                                            "DomainFilter",
                                            "addDomainFilterRules() - Skipping invalid rule -"
                                                + " invalid domain");
                                    firewallResponseArr[i7] =
                                            new FirewallResponse(
                                                    FirewallResponse.Result.FAILED,
                                                    FirewallResponse.ErrorCode
                                                            .INVALID_PARAMETER_ERROR,
                                                    "Invalid domain.");
                                }
                            } else {
                                Log.i(
                                        "DomainFilter",
                                        "addDomainFilterRules() - Skipping invalid rule - signature"
                                            + " mismatch");
                                firewallResponseArr[i7] =
                                        new FirewallResponse(
                                                FirewallResponse.Result.FAILED,
                                                FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                                                "Given signature does not match with the"
                                                    + " application.");
                            }
                            i = userId;
                            i2 = size;
                            map2 = map;
                            jSONArray = jSONArray2;
                            i5 = 0;
                            contextInfo2 = contextInfo3;
                            i7++;
                            contextInfo3 = contextInfo2;
                            userId = i;
                            arrayList3 = arrayList2;
                            map = map2;
                            size = i2;
                            jSONArray2 = jSONArray;
                            z5 = true;
                        }
                    }
                }
                arrayList2 = arrayList3;
                i = userId;
                i2 = size;
                map2 = map;
                jSONArray = jSONArray2;
                contextInfo2 = contextInfo3;
                i7++;
                contextInfo3 = contextInfo2;
                userId = i;
                arrayList3 = arrayList2;
                map = map2;
                size = i2;
                jSONArray2 = jSONArray;
                z5 = true;
            }
            ContextInfo contextInfo4 = contextInfo3;
            ArrayList arrayList7 = arrayList3;
            int i9 = size;
            KnoxAnalyticsData knoxAnalyticsData =
                    new KnoxAnalyticsData("KNOX_SECURE_NETWORK", 1, "addDomainFilterRules");
            knoxAnalyticsData.setUserTypeProperty(contextInfo4.mContainerId);
            knoxAnalyticsData.setProperty("rules", "\"" + jSONArray2.toString() + "\"");
            KnoxAnalytics.log(knoxAnalyticsData);
            if (isDomainFilterOnIptablesEnabled(contextInfo)) {
                ArrayList arrayList8 = new ArrayList();
                while (i5 < i9) {
                    if (firewallResponseArr[i5]
                            .getResult()
                            .equals(FirewallResponse.Result.SUCCESS)) {
                        arrayList = arrayList7;
                        arrayList8.add((DomainFilterRule) arrayList.get(i5));
                    } else {
                        arrayList = arrayList7;
                    }
                    i5++;
                    arrayList7 = arrayList;
                }
                addDomainRules(contextInfo4, arrayList8);
            }
            return firewallResponseArr;
        }
    }

    public final boolean addDomainListInDataBase(
            int i, String str, List list, String str2, List list2, List list3, Set set) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            ContentValues contentValues = new ContentValues();
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    i, contentValues, "adminUid", "packageName", str);
            contentValues.put("typeList", str2);
            contentValues.put("domain", str3);
            ContentValues contentValues2 = new ContentValues();
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    i, contentValues2, "adminUid", "packageName", str);
            contentValues2.put("domain", str3);
            if (!this.mEdmStorageProvider.put(
                    "DomainFilterListTable", contentValues, contentValues2)) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                        "addDomainListInDataBase()- Any ",
                        str2,
                        " couldn't be added",
                        "DomainFilter");
                rollbackDomainList(i, str, list2, list3, set, true);
                return false;
            }
            ((HashSet) set).add(str3);
        }
        return true;
    }

    public final boolean addDomainListInDataBase(
            ContextInfo contextInfo, DomainFilterRule domainFilterRule, List list, List list2) {
        int i = contextInfo.mCallerUid;
        String packageName = domainFilterRule.getApplication().getPackageName();
        HashSet hashSet = new HashSet();
        List denyDomains = domainFilterRule.getDenyDomains();
        List allowDomains = domainFilterRule.getAllowDomains();
        Log.i("DomainFilter", "addDomainListInDataBase()");
        boolean addDomainListInDataBase =
                addDomainListInDataBase(
                        i, packageName, denyDomains, "blacklist", list, list2, hashSet);
        if (addDomainListInDataBase) {
            addDomainListInDataBase =
                    addDomainListInDataBase(
                            i, packageName, allowDomains, "whitelist", list, list2, hashSet);
        }
        if (addDomainListInDataBase) {
            Log.i("DomainFilter", "addDomainListInDataBase()- All domains were added");
        }
        return addDomainListInDataBase;
    }

    public final synchronized FirewallResponse addDomainRules(ContextInfo contextInfo, List list) {
        Log.i("DomainFilter", "addDomainRules()");
        if (((ArrayList) list).isEmpty()) {
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The domain filter on iptables api was successfully enabled.");
        }
        return this.mFirewallRulesApplier.addDomainRules(contextInfo, list);
    }

    public final int checkAdminInDatabase(int i) {
        int userId = UserHandle.getUserId(i);
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        List stringListAsUser =
                edmStorageProvider.getStringListAsUser(userId, "DomainFilterTable", "adminUid");
        if (((ArrayList) stringListAsUser).size() == 0) {
            stringListAsUser =
                    edmStorageProvider.getStringListAsUser(
                            userId, "DomainFilterOnIptablesApiStatus", "adminUid");
            if (((ArrayList) stringListAsUser).size() == 0) {
                return 0;
            }
        }
        return Integer.parseInt((String) stringListAsUser.get(0)) == i ? 1 : -1;
    }

    public final void concatenateListAndExecuteCommand(
            int i, ArrayList arrayList, int i2, String str, List list) {
        int length = str.getBytes(StandardCharsets.UTF_8).length;
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i3 = i2 + length;
        if (list == null) {
            arrayList2.addAll(arrayList);
            arrayList2.add(str);
            arrayList2.add(null);
            executeDomainFilterCommands(arrayList2, i);
            return;
        }
        int i4 = i3;
        int i5 = 0;
        while (i5 < list.size()) {
            i4 += ((String) list.get(i5)).getBytes(StandardCharsets.UTF_8).length;
            if (i4 < 70000) {
                if (sb.length() == 0) {
                    sb.append((String) list.get(i5));
                } else {
                    sb.append(";" + ((String) list.get(i5)));
                }
                i5++;
            } else {
                Log.d("DomainFilter", "concatenateListAndExecuteCommand() - " + sb.toString());
                arrayList2.addAll(arrayList);
                arrayList2.add(str);
                arrayList2.add(sb.toString());
                sb.setLength(0);
                executeDomainFilterCommands(arrayList2, i);
                arrayList2.clear();
                i4 = i3;
            }
        }
        if (sb.length() != 0) {
            Log.d("DomainFilter", "concatenateListAndExecuteCommand() - " + sb.toString());
            arrayList2.addAll(arrayList);
            arrayList2.add(str);
            arrayList2.add(sb.toString());
            sb.setLength(0);
            executeDomainFilterCommands(arrayList2, i);
            arrayList2.clear();
        }
    }

    public final void connectNativeNetdService() {
        boolean z;
        INetd asInterface;
        try {
            this.mInjector.getClass();
            asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
            if (asInterface == null) {
                Log.w("DomainFilter", "WARNING: returning null INetd instance.");
            }
            this.mNetdService = asInterface;
        } catch (RemoteException unused) {
            z = false;
        }
        if (asInterface == null) {
            Log.e("DomainFilter", "connectNativeNetdService() - netd is null.");
            return;
        }
        z = asInterface.isAlive();
        if (z) {
            return;
        }
        Log.e(
                "DomainFilter",
                "connectNativeNetdService() - Can't connect to NativeNetdService netd");
    }

    public final synchronized FirewallResponse enableDomainFilterOnIptables(
            ContextInfo contextInfo, boolean z) {
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (checkAdminInDatabase(i) == -1) {
            Log.i(
                    "DomainFilter",
                    "enableDomainFilterOnIptables() - Another admin already have domain filter"
                        + " rules in the database");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR,
                    "This administrator can't execute this operation because he is not the owner.");
        }
        if (!z) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(userId));
            if (this.mEdmStorageProvider.delete("DomainFilterOnIptablesApiStatus", contentValues)
                    == 0) {
                Log.e(
                        "DomainFilter",
                        "enableDomainFilterOnIptables() - Failed to change DomainFilterOnIptables"
                            + " Api status on database");
                return new FirewallResponse(
                        FirewallResponse.Result.FAILED,
                        FirewallResponse.ErrorCode.DATABASE_ERROR,
                        "Failed to change DomainFilterOnIptables status on database.");
            }
            Log.d(
                    "DomainFilter",
                    "enableDomainFilterOnIptables() - DomainFilterOnIptables successfully"
                        + " disabled");
            if (!isDomainFilterOnIptablesEnabledAtAll()) {
                this.mDomainFilterNapCommon.setDomainFilterOnIptablesEnabled(false);
            }
            if (this.mFirewallRulesApplier.flushDomainChains(Integer.valueOf(userId), false)) {
                Log.i("DomainFilter", "flushDomainChains() - domain rules successfully removed");
                return new FirewallResponse(
                        FirewallResponse.Result.SUCCESS,
                        FirewallResponse.ErrorCode.NO_ERROR,
                        "The domain filter on iptables api was successfully disabled.");
            }
            Log.e("DomainFilter", "flushDomainChains() - Failed to flush domain chains");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                    "Failed to disable domain filter on iptables api.");
        }
        FirewallRulesApplier firewallRulesApplier = this.mFirewallRulesApplier;
        boolean runShellCommand =
                firewallRulesApplier.runShellCommand(
                        4,
                        "*filter\n"
                            + ":domainfilter-test -\n"
                            + "COMMIT\n"
                            + "*filter\n"
                            + "-A domainfilter-test -m domainfilter --blacklist %testing% -j"
                            + " REJECT\n"
                            + "COMMIT\n");
        firewallRulesApplier.runShellCommand(
                4,
                "*filter\n"
                    + "-D domainfilter-test -m domainfilter --blacklist %testing% -j REJECT\n"
                    + "COMMIT\n"
                    + "*filter\n"
                    + "-X domainfilter-test\n"
                    + "COMMIT\n");
        if (!runShellCommand) {
            Log.i("DomainFilter", "DomainFilterOnIptablesApi not supported.");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                    "Failed to enable domain filter on iptables api.");
        }
        boolean isDomainFilterOnIptablesEnabledAtAll = isDomainFilterOnIptablesEnabledAtAll();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        contentValues2.put("userId", Integer.valueOf(userId));
        contentValues2.put(Constants.JSON_CLIENT_DATA_STATUS, Boolean.valueOf(z));
        if (this.mEdmStorageProvider.insert("DomainFilterOnIptablesApiStatus", contentValues2)
                == -1) {
            Log.e(
                    "DomainFilter",
                    "enableDomainFilterOnIptables() - Failed to change DomainFilterOnIptables Api"
                        + " status on database");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Failed to change DomainFilterOnIptables status on database.");
        }
        if (!isDomainFilterOnIptablesEnabledAtAll) {
            if (getConnectivityService() == null) {
                Log.e(
                        "DomainFilter",
                        "flushNetworkDnsCache() - failed to retrieve ConnectivityService instance");
            } else {
                try {
                    final Network activeNetwork = mConnectivityService.getActiveNetwork();
                    if (activeNetwork != null) {
                        Binder.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingRunnable() { // from class:
                                                              // com.android.server.enterprise.firewall.DomainFilter$$ExternalSyntheticLambda0
                                    public final void runOrThrow() {
                                        DomainFilter domainFilter = DomainFilter.this;
                                        Network network = activeNetwork;
                                        DnsResolverAdapter dnsResolverAdapter =
                                                domainFilter.mDnsResolverAdapter;
                                        int netId = network.getNetId();
                                        dnsResolverAdapter.getClass();
                                        Log.d("DnsResolverAdapter", "flushNetworkCache - entered");
                                        dnsResolverAdapter.runWithExceptionHandling(
                                                new DnsResolverAdapter$$ExternalSyntheticLambda2(
                                                        netId, 1));
                                        Log.d("DnsResolverAdapter", "flushNetworkCache - exited");
                                    }
                                });
                    }
                } catch (Exception unused) {
                    Log.e("DomainFilter", "flushNetworkDnsCache() - Failed to getActiveNetwork");
                }
            }
            this.mDomainFilterNapCommon.setDomainFilterOnIptablesEnabled(true);
        }
        FirewallResponse addDomainRules =
                addDomainRules(contextInfo, getDomainFilterRules(contextInfo, null));
        Log.d(
                "DomainFilter",
                "enableDomainFilterOnIptables() - DomainFilterOnIptables Api successfully enabled");
        return addDomainRules;
    }

    public final FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) {
        Log.i("DomainFilter", "enableDomainFilterReport()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, Boolean.valueOf(z));
        contentValues.put("userId", Integer.valueOf(userId));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        if (!this.mEdmStorageProvider.put(
                "DomainFilterReportStatus", contentValues, contentValues2)) {
            Log.e(
                    "DomainFilter",
                    "enableDomainFilterReport() - Failed to change Domain Filter Report status on"
                        + " database");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Failed to change Domain Filter report status on database.");
        }
        Integer valueOf = Integer.valueOf(userId);
        if (z && !((HashSet) this.mDomainReportCache).contains(valueOf)) {
            ((HashSet) this.mDomainReportCache).add(valueOf);
        } else if (!z
                && !isDomainFilterReportEnabledAsUser(userId)
                && ((HashSet) this.mDomainReportCache).contains(valueOf)) {
            ((HashSet) this.mDomainReportCache).remove(valueOf);
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put("userId", Integer.valueOf(userId));
            this.mEdmStorageProvider.delete("DomainFilterReportTable", contentValues3);
        }
        if (z && registerDomainFilterListener(z)) {
            Log.d(
                    "DomainFilter",
                    "enableDomainFilterReport() - Domain Filter Report successfully enabled");
        } else if (!z && registerDomainFilterListener(z)) {
            Log.d(
                    "DomainFilter",
                    "enableDomainFilterReport() - Domain Filter Report successfully disabled");
        }
        return new FirewallResponse(
                FirewallResponse.Result.SUCCESS,
                FirewallResponse.ErrorCode.NO_ERROR,
                z ? "Domain Report successfully enabled." : "Domain Report successfully disabled.");
    }

    public final synchronized void executeDomainFilterCommands(ArrayList arrayList, int i) {
        IOemNetd oemNetdService;
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        for (int i2 = 1; i2 <= 3; i2++) {
            try {
                oemNetdService = getOemNetdService();
                this.mOemNetdService = oemNetdService;
            } catch (RemoteException | ServiceSpecificException unused) {
                Log.e("DomainFilter", "executeDomainFilterCommands() - attempt " + i2 + " in 3");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.e("DomainFilter", "delayTransaction() - failed to delay transaction ", e);
                }
            }
            if (oemNetdService != null) {
                oemNetdService.updateDomainFilterCache(i, strArr);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e2) {
                    Log.e("DomainFilter", "delayTransaction() - failed to delay transaction ", e2);
                }
                return;
            }
            continue;
        }
        Log.e("DomainFilter", "executeDomainFilterCommands() - Transaction failed. ");
    }

    public final int getApplicationUid(int i, String str) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                PackageManagerAdapter.getInstance(this.mContext).getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(128, i, str);
            } catch (Exception e) {
                Log.e("DomainFilter", "getApplicationUid() - exception getting package info ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                packageInfo = null;
            }
            if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
                return -1;
            }
            return applicationInfo.uid;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ConnectivityManager getConnectivityService() {
        if (mConnectivityService == null) {
            mConnectivityService =
                    (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        return mConnectivityService;
    }

    public final List getDomainFilterRules(ContextInfo contextInfo, List list) {
        Log.i("DomainFilter", "getDomainFilterRules()");
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        Iterator it =
                ((ArrayList) edmStorageProvider.getValues("DomainFilterTable", null, contentValues))
                        .iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            String asString = contentValues2.getAsString("packageName");
            String asString2 = contentValues2.getAsString("signature");
            if (list == null || list.contains(asString)) {
                arrayList.add(
                        new DomainFilterRule(
                                new AppIdentity(asString, asString2),
                                FirewallUtils.getListFromDb(
                                        contentValues2, "blacklist", edmStorageProvider),
                                FirewallUtils.getListFromDb(
                                        contentValues2, "whitelist", edmStorageProvider),
                                contentValues2.getAsString("dns1"),
                                contentValues2.getAsString("dns2")));
            }
        }
        contentValues.clear();
        return arrayList;
    }

    public final IOemNetd getOemNetdService() {
        IOemNetd asInterface;
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            connectNativeNetdService();
        }
        if (this.mOemNetdService == null && this.mNetdService != null) {
            this.mInjector.getClass();
            INetd asInterface2 = INetd.Stub.asInterface(ServiceManager.getService("netd"));
            if (asInterface2 == null) {
                Log.w("DomainFilter", "WARNING: returning null INetd instance.");
            }
            if (asInterface2 != null) {
                try {
                    asInterface = IOemNetd.Stub.asInterface(asInterface2.getOemNetd());
                } catch (RemoteException e) {
                    NetdService$$ExternalSyntheticOutline0.m(
                            "getOemNetdService() - failed to retrieve getOemNetdService instance ",
                            e,
                            "DomainFilter");
                }
                this.mOemNetdService = asInterface;
            }
            Log.w("DomainFilter", "WARNING: returning null IOemNetd instance.");
            asInterface = null;
            this.mOemNetdService = asInterface;
        }
        return this.mOemNetdService;
    }

    public final boolean hasAnyRuleInDatabase() {
        if (((ArrayList) this.mEdmStorageProvider.getValues("DomainFilterTable", null, null))
                .isEmpty()) {
            Log.i("DomainFilter", "hasAnyRuleInDatabase(): false");
            return false;
        }
        Log.i("DomainFilter", "hasAnyRuleInDatabase(): true");
        return true;
    }

    public final boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) {
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                userId, contentValues, "userId", i, "adminUid");
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getBooleanList(
                                contentValues,
                                "DomainFilterOnIptablesApiStatus",
                                Constants.JSON_CLIENT_DATA_STATUS);
        if (arrayList.isEmpty()) {
            return false;
        }
        return arrayList.contains(Boolean.TRUE);
    }

    public final boolean isDomainFilterOnIptablesEnabledAtAll() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, (Integer) 1);
        return this.mEdmStorageProvider.getCount("DomainFilterOnIptablesApiStatus", contentValues)
                > 0;
    }

    public final boolean isDomainFilterReportEnabledAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", Integer.valueOf(i));
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getBooleanList(
                                contentValues,
                                "DomainFilterReportStatus",
                                Constants.JSON_CLIENT_DATA_STATUS);
        if (arrayList.isEmpty()) {
            return false;
        }
        return arrayList.contains(Boolean.TRUE);
    }

    public final void packageAdded(Intent intent) {
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        int i = -1;
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        if (intExtra == -1 || schemeSpecificPart == null) {
            Log.i(
                    "DomainFilter",
                    "packageAdded() - Received invalid user id or package name, can't retrieve"
                        + " application info");
            return;
        }
        int applicationUid = getApplicationUid(intExtra, schemeSpecificPart);
        Log.i(
                "DomainFilter",
                "packageAdded() - packageName: " + schemeSpecificPart + ", uid: " + intExtra);
        if (applicationUid == -1) {
            Log.i("DomainFilter", "packageAdded() - Failed to retrieve app info");
            return;
        }
        synchronized (this.mUserIdMapLock) {
            try {
                Integer valueOf = Integer.valueOf(intExtra);
                if (((HashMap) this.mUserIdMap).get(valueOf) == null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(schemeSpecificPart, Integer.valueOf(applicationUid));
                    ((HashMap) this.mUserIdMap).put(valueOf, hashMap);
                } else {
                    ((Map) ((HashMap) this.mUserIdMap).get(valueOf))
                            .put(schemeSpecificPart, Integer.valueOf(applicationUid));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra), "#SelectClause#");
        contentValues.put("packageName", schemeSpecificPart);
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "DomainFilterTable", null, contentValues);
        if (arrayList.isEmpty()) {
            return;
        }
        ContentValues contentValues2 = (ContentValues) arrayList.get(0);
        String asString = contentValues2.getAsString("signature");
        if (!TextUtils.isEmpty(asString)
                && !validatePkgSignature(intExtra, schemeSpecificPart, asString)) {
            Log.i(
                    "DomainFilter",
                    "packageAdded() - Installed app's signature mismatched the one provided by"
                        + " admin.");
            return;
        }
        List listFromDb =
                FirewallUtils.getListFromDb(contentValues2, "blacklist", this.mEdmStorageProvider);
        List listFromDb2 =
                FirewallUtils.getListFromDb(contentValues2, "whitelist", this.mEdmStorageProvider);
        String asString2 = contentValues2.getAsString("dns1");
        if (((ArrayList) listFromDb).isEmpty()
                && ((ArrayList) listFromDb2).isEmpty()
                && TextUtils.isEmpty(asString2)) {
            return;
        }
        if (!TextUtils.isEmpty(asString2)) {
            int i2 = setupNetworkDns(asString2, contentValues2.getAsString("dns2"));
            if (i2 != -1) {
                contentValues2.put("networkId", String.valueOf(i2));
                this.mEdmStorageProvider.put("DomainFilterTable", contentValues2, contentValues);
            }
            i = i2;
        }
        sendToCache(
                0,
                String.valueOf(applicationUid),
                null,
                listFromDb,
                listFromDb2,
                String.valueOf(i));
    }

    public final synchronized void processKGPackageException(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if ("com.samsung.android.kgclient".equals(packageInfo.applicationInfo.packageName)) {
                sendToCache(
                        8,
                        Integer.toString(packageInfo.applicationInfo.uid),
                        null,
                        null,
                        null,
                        null);
            }
        }
    }

    public final void processKGPackageExceptionDelayed() {
        if (FirewallUtils.isKGExemptRuleRequired()) {
            synchronized (this.mKGExceptionListLock) {
                try {
                    if (this.mIsKGExceptionApplied) {
                        return;
                    }
                    this.mIsKGExceptionApplied = true;
                    UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                    if (userManager != null) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            List users = userManager.getUsers();
                            if (users != null) {
                                Iterator it = users.iterator();
                                while (it.hasNext()) {
                                    processKGPackageException(
                                            this.mContext
                                                    .getPackageManager()
                                                    .getInstalledPackagesAsUser(
                                                            64, ((UserInfo) it.next()).id));
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final synchronized void processPackageExceptionList(List list) {
        Iterator it =
                ((ArrayList) FirewallUtils.getUidsToExemptFromPackages(this.mContext, list, false))
                        .iterator();
        while (it.hasNext()) {
            sendToCache(
                    4, Integer.toString(((Integer) it.next()).intValue()), null, null, null, null);
        }
    }

    public final void processPackageExceptionListDelayed() {
        Log.i("DomainFilter", "processPackageExceptionListDelayed()");
        synchronized (this.mExceptionListLock) {
            try {
                if (this.mIsExceptionListApplied) {
                    return;
                }
                this.mIsExceptionListApplied = true;
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                if (userManager != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        List users = userManager.getUsers();
                        if (users != null) {
                            Iterator it = users.iterator();
                            while (it.hasNext()) {
                                List installedPackagesAsUser =
                                        this.mContext
                                                .getPackageManager()
                                                .getInstalledPackagesAsUser(
                                                        64, ((UserInfo) it.next()).id);
                                processPackageExceptionList(installedPackagesAsUser);
                                processPlatformPackageExceptionList(installedPackagesAsUser);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void processPlatformPackageExceptionList(List list) {
        Iterator it =
                ((ArrayList) FirewallUtils.getPlatformUidsToExemptFromPackages(list)).iterator();
        while (it.hasNext()) {
            sendToCache(
                    8, Integer.toString(((Integer) it.next()).intValue()), null, null, null, null);
        }
    }

    public final synchronized List reassembleDomainFilterBrokenRules(int i) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (DomainFilterRule domainFilterRule :
                    (List) ((HashMap) this.mDomainFilterBrokenRules).get(Integer.valueOf(i))) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        arrayList.add(domainFilterRule);
                        break;
                    }
                    DomainFilterRule domainFilterRule2 = (DomainFilterRule) it.next();
                    if (domainFilterRule2.getIpcToken() != 0
                            && domainFilterRule2.getIpcToken() == domainFilterRule.getIpcToken()) {
                        List denyDomains = domainFilterRule2.getDenyDomains();
                        List allowDomains = domainFilterRule2.getAllowDomains();
                        if (denyDomains != null) {
                            denyDomains.addAll(domainFilterRule.getDenyDomains());
                        }
                        if (allowDomains != null) {
                            allowDomains.addAll(domainFilterRule.getAllowDomains());
                        }
                        domainFilterRule2.setDenyDomains(denyDomains);
                        domainFilterRule2.setAllowDomains(allowDomains);
                    }
                }
            }
            ((HashMap) this.mDomainFilterBrokenRules).remove(Integer.valueOf(i));
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public final boolean registerDomainFilterListener(boolean z) {
        if (!z) {
            if (this.mDomainFilterEventListener == null) {
                return false;
            }
            try {
                IOemNetd oemNetdService = getOemNetdService();
                this.mOemNetdService = oemNetdService;
                if (oemNetdService == null) {
                    return false;
                }
                oemNetdService.unregisterDomainFilterEventListener();
                this.mDomainFilterEventListener = null;
                Log.d("DomainFilter", "DomainFilterEventListener unregistered successfully");
                return true;
            } catch (RemoteException | ServiceSpecificException e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "Failed to unregister domainFilterEventListener ", "DomainFilter");
                return false;
            }
        }
        if (this.mDomainFilterEventListener != null) {
            return false;
        }
        try {
            IOemNetd oemNetdService2 = getOemNetdService();
            this.mOemNetdService = oemNetdService2;
            if (oemNetdService2 == null) {
                return false;
            }
            DomainFilterEventListener domainFilterEventListener = new DomainFilterEventListener();
            this.mDomainFilterEventListener = domainFilterEventListener;
            this.mOemNetdService.registerDomainFilterEventListener(domainFilterEventListener);
            Log.d("DomainFilter", "DomainFilterEventListener registered successfully");
            return true;
        } catch (RemoteException | ServiceSpecificException e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e2, "Failed to register domainFilterEventListener ", "DomainFilter");
            return false;
        }
    }

    public final void releaseNetworks(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "DomainFilterTable", new String[] {"networkId"}, contentValues);
        if (arrayList.isEmpty()) {
            Log.i("DomainFilter", "releaseNetworks() - No rules in database");
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("networkId");
            int intValue = asInteger != null ? asInteger.intValue() : -1;
            if (intValue != -1) {
                Integer valueOf = Integer.valueOf(intValue);
                NetworkManagementHandler networkManagementHandler = this.mNetworkHandler;
                networkManagementHandler.sendMessage(
                        Message.obtain(networkManagementHandler, 0, valueOf));
            }
        }
    }

    public final synchronized FirewallResponse removeDnsForApplication(
            DomainFilterRule domainFilterRule, int i, ContentValues contentValues) {
        try {
            Log.d("DomainFilter", "removeDnsForApplication() - START");
            String asString = contentValues.getAsString("dns1");
            String asString2 = contentValues.getAsString("dns2");
            String dns1 = domainFilterRule.getDns1();
            String dns2 = domainFilterRule.getDns2();
            if (asString != null
                    && dns1.equals(asString)
                    && ((dns2 != null || asString2 == null)
                            && ((dns2 == null || asString2 != null)
                                    && (dns2 == null || dns2.equals(asString2))))) {
                Integer asInteger = contentValues.getAsInteger("networkId");
                if (asInteger != null && asInteger.intValue() != -1) {
                    this.mNetworkHandler.sendMessage(
                            Message.obtain(this.mNetworkHandler, 0, asInteger));
                    asInteger = -1;
                }
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("dns1", (String) null);
                contentValues2.put("dns2", (String) null);
                contentValues2.put("networkId", asInteger);
                ContentValues contentValues3 = new ContentValues();
                String packageName = domainFilterRule.getApplication().getPackageName();
                contentValues3.put("adminUid", Integer.valueOf(i));
                contentValues3.put("packageName", packageName);
                if (this.mEdmStorageProvider.put(
                        "DomainFilterTable", contentValues2, contentValues3)) {
                    return null;
                }
                Log.e("DomainFilter", "removeDnsForApplication() -  Database error!");
                return new FirewallResponse(
                        FirewallResponse.Result.FAILED,
                        FirewallResponse.ErrorCode.DATABASE_ERROR,
                        "Failed to remove DNS(s) from database.");
            }
            Log.e("DomainFilter", "removeDnsForApplication() - Invalid parameters!");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                    "DNS(s) provided doesn't match DNS(s) in database.");
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized FirewallResponse[] removeDomainFilterRules(
            ContextInfo contextInfo, List list) {
        FirewallResponse[] firewallResponseArr;
        int i;
        HashSet hashSet;
        boolean z;
        HashSet hashSet2;
        boolean z2;
        int i2;
        ArrayList arrayList;
        int i3;
        char c;
        boolean z3;
        ArrayList arrayList2;
        List list2 = list;
        synchronized (this) {
            try {
                StringBuilder sb = new StringBuilder("removeDomainFilterRules() - rules.size = ");
                sb.append(list2 != null ? Integer.valueOf(list.size()) : "0");
                Log.i("DomainFilter", sb.toString());
                boolean z4 = true;
                int i4 = 0;
                if (list2 != null && list.isEmpty()) {
                    Log.i("DomainFilter", "removeDomainFilterRules() - No rule specified");
                    return new FirewallResponse[] {
                        new FirewallResponse(
                                FirewallResponse.Result.FAILED,
                                FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                                "No rule was specified.")
                    };
                }
                int i5 = contextInfo.mCallerUid;
                int userId = UserHandle.getUserId(i5);
                int checkAdminInDatabase = checkAdminInDatabase(i5);
                if (checkAdminInDatabase == 0) {
                    Log.i("DomainFilter", "removeDomainFilterRules() - Database is already empty");
                    firewallResponseArr =
                            new FirewallResponse[] {
                                new FirewallResponse(
                                        FirewallResponse.Result.NO_CHANGES,
                                        FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                                        "The rules are already cleared.")
                            };
                } else {
                    char c2 = 65535;
                    if (checkAdminInDatabase == -1) {
                        Log.i(
                                "DomainFilter",
                                "removeDomainFilterRules() - Another admin is handling domainfilter"
                                    + " rules in the database");
                        firewallResponseArr =
                                new FirewallResponse[] {
                                    new FirewallResponse(
                                            FirewallResponse.Result.FAILED,
                                            FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR,
                                            "This administrator can't execute this operation"
                                                + " because he is not the owner.")
                                };
                    } else {
                        String[] strArr = null;
                        if (list2 == DomainFilterRule.CLEAR_ALL) {
                            FirewallResponse[] firewallResponseArr2 = new FirewallResponse[1];
                            releaseNetworks(i5);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("adminUid", Integer.valueOf(i5));
                            List values =
                                    this.mEdmStorageProvider.getValues(
                                            "DomainFilterTable", null, contentValues);
                            if (this.mEdmStorageProvider.delete("DomainFilterTable", contentValues)
                                    <= 0) {
                                Log.i(
                                        "DomainFilter",
                                        "removeDomainFilterRules() - Failed to clear rulesfrom the"
                                            + " database");
                                firewallResponseArr2[0] =
                                        new FirewallResponse(
                                                FirewallResponse.Result.FAILED,
                                                FirewallResponse.ErrorCode.DATABASE_ERROR,
                                                "Failed to clear rules from database.");
                            } else if (this.mEdmStorageProvider.delete(
                                            "DomainFilterListTable", contentValues)
                                    < 0) {
                                Iterator it = ((ArrayList) values).iterator();
                                while (it.hasNext()) {
                                    this.mEdmStorageProvider.put(
                                            "DomainFilterTable",
                                            (ContentValues) it.next(),
                                            contentValues);
                                }
                                Log.i(
                                        "DomainFilter",
                                        "removeDomainFilterRules() - Failed to clear rules from the"
                                            + " database (url)");
                                firewallResponseArr2[0] =
                                        new FirewallResponse(
                                                FirewallResponse.Result.FAILED,
                                                FirewallResponse.ErrorCode.DATABASE_ERROR,
                                                "Failed to clear rules from database.");
                            } else {
                                firewallResponseArr2[0] =
                                        new FirewallResponse(
                                                FirewallResponse.Result.SUCCESS,
                                                FirewallResponse.ErrorCode.NO_ERROR,
                                                "Rules successfully cleared. Admin: " + i5);
                                if (isDomainFilterOnIptablesEnabled(contextInfo)
                                        && !this.mFirewallRulesApplier.flushDomainChains(
                                                Integer.valueOf(userId), false)) {
                                    Log.e(
                                            "DomainFilter",
                                            "flushDomainChains() - Failed to flush domain chains");
                                }
                                if (!this.mFirewallRulesApplier.flushDnsPortChains(
                                        Integer.valueOf(userId))) {
                                    Log.e(
                                            "DomainFilter",
                                            "flushDnsPortChains() - Failed to flush port53 chains");
                                }
                                Log.i(
                                        "DomainFilter",
                                        "flushDomainChains() - domain rules successfully removed");
                            }
                            updateDaemonCache(contextInfo, null, 2, null);
                            firewallResponseArr = firewallResponseArr2;
                        } else {
                            int size = list.size();
                            FirewallResponse[] firewallResponseArr3 = new FirewallResponse[size];
                            ArrayList arrayList3 = new ArrayList();
                            int i6 = 0;
                            while (i6 < list.size()) {
                                DomainFilterRule domainFilterRule =
                                        (DomainFilterRule) list2.get(i6);
                                FirewallResponse validateApplicationIdentity =
                                        validateApplicationIdentity(
                                                domainFilterRule.getApplication());
                                firewallResponseArr3[i6] = validateApplicationIdentity;
                                if (validateApplicationIdentity != null) {
                                    Log.i(
                                            "DomainFilter",
                                            "removeDomainFilterRules() - Skipping invalid rule -"
                                                + " invalidApplication Identity"
                                                    + firewallResponseArr3[i6].getMessage());
                                    i2 = i6;
                                    arrayList = arrayList3;
                                    z3 = z4;
                                    c = c2;
                                    i3 = size;
                                } else {
                                    String packageName =
                                            domainFilterRule.getApplication().getPackageName();
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put("adminUid", Integer.valueOf(i5));
                                    contentValues2.put("packageName", packageName);
                                    ArrayList arrayList4 =
                                            (ArrayList)
                                                    this.mEdmStorageProvider.getValues(
                                                            "DomainFilterTable",
                                                            strArr,
                                                            contentValues2);
                                    if (arrayList4.isEmpty()) {
                                        Log.i(
                                                "DomainFilter",
                                                "removeDomainFilterRules() - Skipping rule - This"
                                                    + " rule is not in the database");
                                        firewallResponseArr3[i6] =
                                                new FirewallResponse(
                                                        FirewallResponse.Result.NO_CHANGES,
                                                        FirewallResponse.ErrorCode.NO_ERROR,
                                                        "The rule is not in the database.");
                                        i2 = i6;
                                        arrayList = arrayList3;
                                        z3 = z4;
                                        i3 = size;
                                        c = 65535;
                                    } else {
                                        ContentValues contentValues3 =
                                                (ContentValues) arrayList4.get(i4);
                                        String asString = contentValues3.getAsString("signature");
                                        String signature =
                                                domainFilterRule.getApplication().getSignature();
                                        if ((signature == null || asString != null)
                                                && ((signature != null || asString == null)
                                                        && (signature == null
                                                                || asString == null
                                                                || signature.equals(asString)))) {
                                            if (TextUtils.isEmpty(domainFilterRule.getDns1())) {
                                                i = i4;
                                            } else {
                                                FirewallResponse removeDnsForApplication =
                                                        removeDnsForApplication(
                                                                domainFilterRule,
                                                                i5,
                                                                contentValues3);
                                                firewallResponseArr3[i6] = removeDnsForApplication;
                                                if (removeDnsForApplication != null) {
                                                    Log.i(
                                                            "DomainFilter",
                                                            "removeDomainFilterRules() - Skipping"
                                                                + " rule - Failed to remove DNS");
                                                } else {
                                                    i = 1;
                                                }
                                            }
                                            List listFromDb =
                                                    FirewallUtils.getListFromDb(
                                                            contentValues3,
                                                            "blacklist",
                                                            this.mEdmStorageProvider);
                                            List listFromDb2 =
                                                    FirewallUtils.getListFromDb(
                                                            contentValues3,
                                                            "whitelist",
                                                            this.mEdmStorageProvider);
                                            prepareDomainUrls(domainFilterRule);
                                            List denyDomains = domainFilterRule.getDenyDomains();
                                            List allowDomains = domainFilterRule.getAllowDomains();
                                            if (denyDomains != null) {
                                                hashSet = new HashSet(listFromDb);
                                                z = hashSet.removeAll(denyDomains);
                                            } else {
                                                hashSet = new HashSet();
                                                if (allowDomains != null) {
                                                    arrayList3.addAll(listFromDb);
                                                }
                                                z = true;
                                            }
                                            if (allowDomains != null) {
                                                hashSet2 = new HashSet(listFromDb2);
                                                z2 = z | hashSet2.removeAll(allowDomains);
                                            } else {
                                                HashSet hashSet3 = new HashSet();
                                                if (denyDomains != null) {
                                                    arrayList3.addAll(listFromDb2);
                                                }
                                                hashSet2 = hashSet3;
                                                z2 = true;
                                            }
                                            if (z2 || i != 0) {
                                                boolean z5 =
                                                        contentValues3.getAsString("dns1") != null;
                                                if (!hashSet.isEmpty()
                                                        || !hashSet2.isEmpty()
                                                        || (i == 0 && z5)) {
                                                    i2 = i6;
                                                    arrayList = arrayList3;
                                                    i3 = size;
                                                    if (removeDomainListInDatabase(
                                                            contextInfo,
                                                            domainFilterRule,
                                                            arrayList,
                                                            listFromDb,
                                                            listFromDb2)) {
                                                        Integer asInteger =
                                                                i != 0
                                                                        ? contentValues3
                                                                                .getAsInteger(
                                                                                        "networkId")
                                                                        : null;
                                                        if (asInteger == null) {
                                                            c = 65535;
                                                            asInteger = -1;
                                                        } else {
                                                            c = 65535;
                                                        }
                                                        z3 = true;
                                                        updateDaemonCache(
                                                                contextInfo,
                                                                domainFilterRule,
                                                                1,
                                                                String.valueOf(
                                                                        asInteger.intValue()));
                                                        firewallResponseArr3[i2] =
                                                                new FirewallResponse(
                                                                        FirewallResponse.Result
                                                                                .SUCCESS,
                                                                        FirewallResponse.ErrorCode
                                                                                .NO_ERROR,
                                                                        "The rule was successfuly"
                                                                            + " removed/updated.");
                                                    } else {
                                                        Log.e(
                                                                "DomainFilter",
                                                                "removeDomainFilterRules() - Failed"
                                                                    + " to remove rule from the"
                                                                    + " database");
                                                        firewallResponseArr3[i2] =
                                                                new FirewallResponse(
                                                                        FirewallResponse.Result
                                                                                .FAILED,
                                                                        FirewallResponse.ErrorCode
                                                                                .DATABASE_ERROR,
                                                                        "Failed to remove/update"
                                                                            + " rule from the"
                                                                            + " database.");
                                                        c = 65535;
                                                        z3 = true;
                                                    }
                                                }
                                                List values2 =
                                                        this.mEdmStorageProvider.getValues(
                                                                "DomainFilterTable",
                                                                null,
                                                                contentValues2);
                                                if (this.mEdmStorageProvider.delete(
                                                                "DomainFilterTable", contentValues2)
                                                        <= 0) {
                                                    Log.e(
                                                            "DomainFilter",
                                                            "removeDomainFilterRules() - Failed to"
                                                                + " remove rule fromthe database");
                                                    firewallResponseArr3[i6] =
                                                            new FirewallResponse(
                                                                    FirewallResponse.Result.FAILED,
                                                                    FirewallResponse.ErrorCode
                                                                            .DATABASE_ERROR,
                                                                    "Failed to remove/update rule"
                                                                        + " from the database.");
                                                    i2 = i6;
                                                    arrayList2 = arrayList3;
                                                    i3 = size;
                                                } else {
                                                    i3 = size;
                                                    i2 = i6;
                                                    arrayList2 = arrayList3;
                                                    if (removeDomainListInDatabase(
                                                            contextInfo,
                                                            domainFilterRule,
                                                            arrayList3,
                                                            listFromDb,
                                                            listFromDb2)) {
                                                        Integer asInteger2 =
                                                                contentValues3.getAsInteger(
                                                                        "networkId");
                                                        if (asInteger2 != null) {
                                                            updateDaemonCache(
                                                                    contextInfo,
                                                                    domainFilterRule,
                                                                    1,
                                                                    String.valueOf(
                                                                            asInteger2.intValue()));
                                                            firewallResponseArr3[i2] =
                                                                    new FirewallResponse(
                                                                            FirewallResponse.Result
                                                                                    .SUCCESS,
                                                                            FirewallResponse
                                                                                    .ErrorCode
                                                                                    .NO_ERROR,
                                                                            "The rule was"
                                                                                + " successfuly"
                                                                                + " removed/updated.");
                                                            if (!this.mFirewallRulesApplier
                                                                            .hasDenyRuleInDatabase(
                                                                                    i5, packageName)
                                                                    && this.mFirewallRulesApplier
                                                                            .blockPort53(
                                                                                    new AppIdentity(
                                                                                            packageName,
                                                                                            signature),
                                                                                    contextInfo,
                                                                                    false)) {
                                                                Log.d(
                                                                        "DomainFilter",
                                                                        "unblocking port53 for"
                                                                            + " package = "
                                                                                + packageName);
                                                            }
                                                        } else {
                                                            Log.e(
                                                                    "DomainFilter",
                                                                    "removeDomainFilterRules() -"
                                                                        + " Failed to remove rule"
                                                                        + " from the database");
                                                            firewallResponseArr3[i2] =
                                                                    new FirewallResponse(
                                                                            FirewallResponse.Result
                                                                                    .FAILED,
                                                                            FirewallResponse
                                                                                    .ErrorCode
                                                                                    .DATABASE_ERROR,
                                                                            "Failed to"
                                                                                + " remove/update"
                                                                                + " rule from the"
                                                                                + " database.");
                                                        }
                                                    } else {
                                                        this.mEdmStorageProvider.put(
                                                                "DomainFilterTable",
                                                                (ContentValues)
                                                                        ((ArrayList) values2)
                                                                                .get(0),
                                                                contentValues2);
                                                        Log.e(
                                                                "DomainFilter",
                                                                "removeDomainFilterRules() - Failed"
                                                                    + " to remove rule fromthe"
                                                                    + " database");
                                                        firewallResponseArr3[i2] =
                                                                new FirewallResponse(
                                                                        FirewallResponse.Result
                                                                                .FAILED,
                                                                        FirewallResponse.ErrorCode
                                                                                .DATABASE_ERROR,
                                                                        "Failed to remove/update"
                                                                            + " rule from the"
                                                                            + " database.");
                                                    }
                                                }
                                                arrayList = arrayList2;
                                                c = 65535;
                                                z3 = true;
                                            } else {
                                                Log.i(
                                                        "DomainFilter",
                                                        "removeDomainFilterRules() - Skipping rule"
                                                            + " - Rule is not in the database");
                                                firewallResponseArr3[i6] =
                                                        new FirewallResponse(
                                                                FirewallResponse.Result.NO_CHANGES,
                                                                FirewallResponse.ErrorCode.NO_ERROR,
                                                                "The rule is not in the database.");
                                            }
                                        } else {
                                            Log.i(
                                                    "DomainFilter",
                                                    "removeDomainFilterRules() - Skipping invalid"
                                                        + " rule - signature mismatch");
                                            firewallResponseArr3[i6] =
                                                    new FirewallResponse(
                                                            FirewallResponse.Result.FAILED,
                                                            FirewallResponse.ErrorCode
                                                                    .INVALID_PARAMETER_ERROR,
                                                            "Signature does not match with the"
                                                                + " previous added.");
                                        }
                                        i2 = i6;
                                        arrayList = arrayList3;
                                        i3 = size;
                                        c = 65535;
                                        z3 = true;
                                    }
                                }
                                i6 = i2 + 1;
                                c2 = c;
                                z4 = z3;
                                arrayList3 = arrayList;
                                size = i3;
                                i4 = 0;
                                strArr = null;
                                list2 = list;
                            }
                            List list3 = arrayList3;
                            int i7 = size;
                            if (isDomainFilterOnIptablesEnabled(contextInfo)) {
                                ArrayList arrayList5 = new ArrayList();
                                for (int i8 = 0; i8 < i7; i8++) {
                                    if (firewallResponseArr3[i8]
                                            .getResult()
                                            .equals(FirewallResponse.Result.SUCCESS)) {
                                        arrayList5.add((DomainFilterRule) list.get(i8));
                                    }
                                }
                                removeDomainRules(contextInfo, arrayList5, list3);
                            }
                            firewallResponseArr = firewallResponseArr3;
                        }
                    }
                }
                synchronized (this.mExceptionListLock) {
                    try {
                        if (!hasAnyRuleInDatabase()) {
                            this.mIsExceptionListApplied = false;
                            this.mDomainFilterNapCommon.setDomainFilterEnabled(false);
                        }
                    } finally {
                    }
                }
                synchronized (this.mKGExceptionListLock) {
                    try {
                        if (!hasAnyRuleInDatabase()) {
                            this.mIsKGExceptionApplied = false;
                        }
                    } finally {
                    }
                }
                if (!hasAnyRuleInDatabase()) {
                    sendToCache(5, "", null, null, null, null);
                    sendToCache(5, this.mServer, null, null, null, null);
                }
                return firewallResponseArr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean removeDomainListInDatabase(
            ContextInfo contextInfo,
            DomainFilterRule domainFilterRule,
            List list,
            List list2,
            List list3) {
        int i = contextInfo.mCallerUid;
        String packageName = domainFilterRule.getApplication().getPackageName();
        if (domainFilterRule.getAllowDomains() == null
                && domainFilterRule.getDenyDomains() == null) {
            domainFilterRule.setAllowDomains(list3);
            domainFilterRule.setDenyDomains(list2);
        } else if (domainFilterRule.getAllowDomains() == null) {
            domainFilterRule.setAllowDomains(list);
        } else if (domainFilterRule.getDenyDomains() == null) {
            domainFilterRule.setDenyDomains(list);
        }
        ArrayList arrayList = new ArrayList();
        if (domainFilterRule.getAllowDomains() != null) {
            arrayList.addAll(domainFilterRule.getAllowDomains());
        }
        if (domainFilterRule.getDenyDomains() != null) {
            arrayList.addAll(domainFilterRule.getDenyDomains());
        }
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str = (String) arrayList.get(i2);
            ContentValues contentValues = new ContentValues();
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    i, contentValues, "adminUid", "packageName", packageName);
            contentValues.put("domain", str);
            if (this.mEdmStorageProvider.delete("DomainFilterListTable", contentValues) <= 0) {
                rollbackDomainList(i, packageName, list2, list3, hashSet, false);
                Log.i(
                        "DomainFilter",
                        "removeDomainListInDatabase()- Any deny domain couldn't be removed");
                return false;
            }
            hashSet.add(str);
        }
        Log.i("DomainFilter", "removeDomainListInDatabase()- All domains were removed");
        return true;
    }

    public final synchronized void removeDomainRules(
            ContextInfo contextInfo, List list, List list2) {
        Log.i("DomainFilter", "removeDomainRules()");
        if (((ArrayList) list).isEmpty()) {
            new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The domain filter on iptables api was successfully disabled.");
        } else {
            this.mFirewallRulesApplier.removeDomainRules(contextInfo, list, list2);
        }
    }

    public final void rollbackDomainList(
            int i, String str, List list, List list2, Set set, boolean z) {
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            ContentValues contentValues = new ContentValues();
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    i, contentValues, "adminUid", "packageName", str);
            contentValues.put("domain", str2);
            ContentValues contentValues2 = new ContentValues();
            if (list.contains(str2)) {
                contentValues2.put("typeList", "blacklist");
            } else if (list2.contains(str2)) {
                contentValues2.put("typeList", "whitelist");
            }
            EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
            if (!z || list.contains(str2) || list2.contains(str2)) {
                Log.i(
                        "DomainFilter",
                        "rollbackDomainList()- Rollback for all domains thatwere (Added ="
                                + z
                                + ")");
                edmStorageProvider.put("DomainFilterListTable", contentValues2, contentValues);
            } else {
                edmStorageProvider.delete("DomainFilterListTable", contentValues);
                Log.i(
                        "DomainFilter",
                        "rollbackDomainList()- Rollback for all domains that were added");
            }
        }
    }

    public final void sendToCache(
            int i, String str, String str2, List list, List list2, String str3) {
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m(str);
        if (!TextUtils.isEmpty(str2)) {
            m.add(str2);
        }
        if (2 == i || 3 == i || 4 == i || 5 == i || 8 == i) {
            executeDomainFilterCommands(m, i);
            return;
        }
        Charset charset = StandardCharsets.UTF_8;
        int length = str.getBytes(charset).length;
        if (!TextUtils.isEmpty(str2)) {
            length += str2.getBytes(charset).length;
        }
        if (i == 0 || 1 == i) {
            if (list != null && !list.isEmpty()) {
                concatenateListAndExecuteCommand(i, m, length, "BLACKLIST", list);
            }
            if (list2 != null && !list2.isEmpty()) {
                concatenateListAndExecuteCommand(i, m, length, "WHITELIST", list2);
            }
            if (TextUtils.isEmpty(str3) || str3.equals("-1")) {
                return;
            }
            m.add(str3);
            m.add(i == 0 ? "ADD" : "REMOVE");
            executeDomainFilterCommands(m, 6);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x005a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setupNetworkDns(java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.DomainFilter.setupNetworkDns(java.lang.String,"
                    + " java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDaemonCache(
            com.samsung.android.knox.ContextInfo r9,
            com.samsung.android.knox.net.firewall.DomainFilterRule r10,
            int r11,
            java.lang.String r12) {
        /*
            r8 = this;
            java.lang.String r1 = "DomainFilter"
            java.lang.String r3 = "updateDaemonCache()"
            android.util.Log.i(r1, r3)
            int r1 = r9.mCallerUid
            int r1 = android.os.UserHandle.getUserId(r1)
            r3 = 2
            if (r3 != r11) goto L20
            java.lang.String r3 = java.lang.Integer.toString(r1)
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r1 = r8
            r2 = r11
            r1.sendToCache(r2, r3, r4, r5, r6, r7)
            goto Lb1
        L20:
            if (r10 == 0) goto Lb1
            r3 = 1
            if (r11 == 0) goto L27
            if (r3 != r11) goto Lb1
        L27:
            java.lang.Object r4 = r8.mUserIdMapLock
            monitor-enter(r4)
            java.util.Map r5 = r8.mUserIdMap     // Catch: java.lang.Throwable -> Lae
            java.lang.Integer r6 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> Lae
            java.util.HashMap r5 = (java.util.HashMap) r5     // Catch: java.lang.Throwable -> Lae
            java.lang.Object r5 = r5.get(r6)     // Catch: java.lang.Throwable -> Lae
            java.util.Map r5 = (java.util.Map) r5     // Catch: java.lang.Throwable -> Lae
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lae
            com.samsung.android.knox.AppIdentity r4 = r10.getApplication()
            java.lang.String r4 = r4.getPackageName()
            java.lang.String r6 = "*"
            boolean r6 = r6.equals(r4)
            r7 = 0
            if (r6 == 0) goto L50
            java.lang.String r1 = java.lang.Integer.toString(r1)
        L4e:
            r4 = r1
            goto L66
        L50:
            boolean r1 = r5.containsKey(r4)
            if (r1 == 0) goto L65
            java.lang.Object r1 = r5.get(r4)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            java.lang.String r1 = java.lang.Integer.toString(r1)
            goto L4e
        L65:
            r4 = r7
        L66:
            if (r4 == 0) goto Lb1
            java.util.List r1 = r10.getDenyDomains()
            if (r1 == 0) goto L7c
            boolean r5 = r1.isEmpty()
            if (r5 != 0) goto L76
        L74:
            r5 = r1
            goto L86
        L76:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            goto L74
        L7c:
            if (r3 != r11) goto L85
            java.lang.String r1 = "ALL"
            java.util.ArrayList r1 = android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0.m(r1)
            goto L74
        L85:
            r5 = r7
        L86:
            java.util.List r1 = r10.getAllowDomains()
            if (r1 == 0) goto L9a
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L94
        L92:
            r6 = r1
            goto La4
        L94:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            goto L92
        L9a:
            if (r3 != r11) goto La3
            java.lang.String r1 = "ALL"
            java.util.ArrayList r1 = android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0.m(r1)
            goto L92
        La3:
            r6 = r7
        La4:
            r7 = 0
            r1 = r8
            r2 = r11
            r3 = r4
            r4 = r7
            r7 = r12
            r1.sendToCache(r2, r3, r4, r5, r6, r7)
            goto Lb1
        Lae:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lae
            throw r0
        Lb1:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.DomainFilter.updateDaemonCache(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.net.firewall.DomainFilterRule, int,"
                    + " java.lang.String):void");
    }

    public final boolean validatePkgSignature(int i, String str, String str2) {
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Utils.comparePackageSignature(0, createContextAsUser, str, str2);
        } catch (Exception e) {
            Log.e(
                    "DomainFilter",
                    "validatePkgSignature() - Fail to validate application signature.",
                    e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean verifyAppInstalled(Map map, String str, int i) {
        if (map != null && map.get(str) != null) {
            return true;
        }
        int applicationUid = getApplicationUid(i, str);
        if (applicationUid == -1) {
            return false;
        }
        Integer valueOf = Integer.valueOf(i);
        synchronized (this.mUserIdMapLock) {
            try {
                if (map == null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, Integer.valueOf(applicationUid));
                    ((HashMap) this.mUserIdMap).put(valueOf, hashMap);
                } else {
                    ((Map) ((HashMap) this.mUserIdMap).get(valueOf))
                            .put(str, Integer.valueOf(applicationUid));
                    map.put(str, Integer.valueOf(applicationUid));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
