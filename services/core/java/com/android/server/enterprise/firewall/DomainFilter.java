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
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
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
import com.android.server.enterprise.adapter.IDnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.firewall.DomainFilterReport;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DomainFilter {
    public static final int MAX_LIST_SIZE_IN_BYTES = IBinder.getSuggestedMaxIpcSizeBytes();
    public static String TAG = "DomainFilter";
    public static ConnectivityManager mConnectivityService;
    public BroadcastReceiver mBootFilterReceiver;
    public ConnectivityManager.NetworkCallback mCaptivePortalNetworkCallback;
    public Context mContext;
    public EnforceDnsManager mDnsManager;
    public IDnsResolverAdapter mDnsResolverAdapter;
    public Map mDomainFilterBrokenRules;
    public IDomainFilterEventListener mDomainFilterEventListener;
    public DomainFilterNapCommon mDomainFilterNapCommon;
    public Set mDomainReportCache;
    public EdmStorageProvider mEdmStorageProvider;
    public Object mExceptionListLock;
    public FirewallRulesApplier mFirewallRulesApplier;
    public Thread mInitDaemonCacheThread;
    public final Injector mInjector;
    public boolean mIsExceptionListApplied;
    public boolean mIsKGExceptionApplied;
    public Object mKGExceptionListLock;
    public KnoxNetIdManager mNetIdManager;
    public INetd mNetdService;
    public NetworkManagementHandler mNetworkHandler;
    public List mNetworkIdList;
    public Object mNetworkIdListLock;
    public INetworkManagementService mNetworkService;
    public IOemNetd mOemNetdService;
    public BroadcastReceiver mPackageFilterReceiver;
    public BlockReport[] mReportCache;
    public int mReportCacheIndex;
    public String mServer;
    public BroadcastReceiver mUserFilterReceiver;
    public Map mUserIdMap;
    public Object mUserIdMapLock;

    enum Operation {
        ADD,
        REMOVE,
        CLEAR,
        REPLACE,
        EXCEPTION,
        CAP_PORTAL,
        NET_ID,
        FLUSHLIBC
    }

    class Injector {
        public final Context mCtx;
        public FirewallRulesApplier mFirewallRulesApplier;

        public Injector(Context context, FirewallRulesApplier firewallRulesApplier) {
            this.mCtx = context;
            this.mFirewallRulesApplier = firewallRulesApplier;
        }

        public Context getContext() {
            return this.mCtx;
        }

        public EdmStorageProvider getEDMStorageProvider() {
            return new EdmStorageProvider(this.mCtx);
        }

        public FirewallRulesApplier getFirewallRulesApplier() {
            return this.mFirewallRulesApplier;
        }

        public ArrayList getNetworkIdInfoArray() {
            return new ArrayList();
        }

        public NetworkManagementHandler getNetworkManagementHandler(DomainFilter domainFilter, Looper looper) {
            Objects.requireNonNull(domainFilter);
            return domainFilter.new NetworkManagementHandler(looper);
        }

        public Map getUserIdMap() {
            return new HashMap();
        }

        public INetd getNetd() {
            INetd asInterface = INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
            if (asInterface == null) {
                Log.w(DomainFilter.TAG, "WARNING: returning null INetd instance.");
            }
            return asInterface;
        }

        public IOemNetd getOemNetd() {
            INetd netd = getNetd();
            if (netd != null) {
                try {
                    return IOemNetd.Stub.asInterface(netd.getOemNetd());
                } catch (RemoteException e) {
                    Log.e(DomainFilter.TAG, "getOemNetdService() - failed to retrieve getOemNetdService instance " + e);
                }
            }
            Log.w(DomainFilter.TAG, "WARNING: returning null IOemNetd instance.");
            return null;
        }

        public DomainFilterNapCommon getDomainFilterNapCommon() {
            return DomainFilterNapCommon.getInstance(this.mCtx);
        }

        public IDnsResolverAdapter getDnsResolverAdapter() {
            return DnsResolverAdapter.getInstance();
        }
    }

    public DomainFilter(Context context, FirewallRulesApplier firewallRulesApplier) {
        this(new Injector(context, firewallRulesApplier));
    }

    public DomainFilter(Injector injector) {
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
        this.mPackageFilterReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.firewall.DomainFilter.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(DomainFilter.TAG, "onReceive() - " + intent);
                if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                    DomainFilter.this.packageAdded(intent);
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    DomainFilter.this.packageRemoved(intent);
                } else if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                    DomainFilter.this.packageReplaced(intent);
                }
            }
        };
        this.mUserFilterReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.firewall.DomainFilter.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(DomainFilter.TAG, "onReceive() - " + intent);
                if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                    DomainFilter.this.onUserRemoved(intent);
                } else if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                    DomainFilter.this.onUserAdded(intent);
                }
            }
        };
        this.mBootFilterReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.firewall.DomainFilter.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(DomainFilter.TAG, "onReceive() - " + intent);
                if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(intent.getAction()) || "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL".equals(intent.getAction())) {
                    new Thread(new Runnable() { // from class: com.android.server.enterprise.firewall.DomainFilter.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DomainFilter.this.initUserIdMap();
                        }
                    }).start();
                }
            }
        };
        this.mCaptivePortalNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.enterprise.firewall.DomainFilter.5
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                try {
                    NetworkCapabilities networkCapabilities = DomainFilter.this.getConnectivityService().getNetworkCapabilities(network);
                    if (networkCapabilities == null || !networkCapabilities.hasCapability(17)) {
                        return;
                    }
                    Log.i(DomainFilter.TAG, "Captive portal detected");
                    if (DomainFilter.this.hasAnyRuleInDatabase()) {
                        DomainFilter.this.exemptCaptivePortalHostname(network);
                    }
                } catch (Exception e) {
                    Log.e(DomainFilter.TAG, "Exception occured: " + e.getLocalizedMessage());
                }
            }
        };
        this.mInjector = injector;
        Context context = injector.getContext();
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mEdmStorageProvider = injector.getEDMStorageProvider();
        this.mFirewallRulesApplier = injector.getFirewallRulesApplier();
        this.mNetworkIdList = injector.getNetworkIdInfoArray();
        HandlerThread handlerThread = new HandlerThread("DomainFilterHandlerThread");
        handlerThread.start();
        this.mNetworkHandler = injector.getNetworkManagementHandler(this, handlerThread.getLooper());
        this.mUserIdMap = injector.getUserIdMap();
        this.mDomainFilterNapCommon = injector.getDomainFilterNapCommon();
        this.mDnsResolverAdapter = injector.getDnsResolverAdapter();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageFilterReceiver, UserHandle.ALL, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        this.mContext.registerReceiverAsUser(this.mUserFilterReceiver, UserHandle.ALL, intentFilter2, null, null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter3.addAction("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL");
        this.mContext.registerReceiverAsUser(this.mBootFilterReceiver, UserHandle.ALL, intentFilter3, null, null);
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "captive_portal_server");
        this.mServer = string;
        if (string == null) {
            this.mServer = "connectivitycheck.gstatic.com";
        }
        Thread thread = new Thread(new Runnable() { // from class: com.android.server.enterprise.firewall.DomainFilter.4
            @Override // java.lang.Runnable
            public void run() {
                DomainFilter.this.initDaemonCache();
                DomainFilter.this.sendToCache(Operation.CAP_PORTAL.ordinal(), DomainFilter.this.mServer, null, null, null, null);
                DomainFilter.this.sendToCache(Operation.EXCEPTION.ordinal(), DomainFilter.this.getCaptivePortalUid(), null, null, null, null);
            }
        });
        this.mInitDaemonCacheThread = thread;
        thread.start();
        initReportCache();
        registerCaptiveNetworkCallback();
    }

    public final ConnectivityManager getConnectivityManagerService() {
        return (ConnectivityManager) this.mContext.getSystemService("connectivity");
    }

    public final void registerCaptiveNetworkCallback() {
        getConnectivityManagerService().registerNetworkCallback(new NetworkRequest.Builder().addTransportType(1).addCapability(17).build(), this.mCaptivePortalNetworkCallback);
    }

    public final void exemptCaptivePortalHostname(final Network network) {
        new Thread(new Runnable() { // from class: com.android.server.enterprise.firewall.DomainFilter.6
            @Override // java.lang.Runnable
            public void run() {
                HttpURLConnection httpURLConnection;
                URL captivePortalCheckUrl = DomainFilter.this.getCaptivePortalCheckUrl();
                if (captivePortalCheckUrl == null) {
                    Log.e(DomainFilter.TAG, "Captive portal url is null");
                    return;
                }
                HttpURLConnection httpURLConnection2 = null;
                HttpURLConnection httpURLConnection3 = null;
                try {
                    try {
                        httpURLConnection = (HttpURLConnection) network.openConnection(captivePortalCheckUrl);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e) {
                    e = e;
                }
                try {
                    httpURLConnection.setInstanceFollowRedirects(false);
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setReadTimeout(10000);
                    httpURLConnection.setUseCaches(false);
                    String headerField = httpURLConnection.getHeaderField("Location");
                    boolean isEmpty = TextUtils.isEmpty(headerField);
                    String str = headerField;
                    if (!isEmpty) {
                        String extractHost = DomainFilter.this.extractHost(headerField);
                        if (!TextUtils.isEmpty(extractHost) && !DomainFilter.this.isIpAddress(extractHost)) {
                            DomainFilter.this.addHostToCaptivePortalWhitelist(extractHost);
                            str = extractHost;
                        } else {
                            Log.e(DomainFilter.TAG, "invalid captive portal host");
                            str = "invalid captive portal host";
                        }
                    }
                    httpURLConnection.disconnect();
                    httpURLConnection2 = str;
                } catch (IOException e2) {
                    e = e2;
                    httpURLConnection3 = httpURLConnection;
                    Log.e(DomainFilter.TAG, "Exception occured: " + e.getLocalizedMessage());
                    httpURLConnection2 = httpURLConnection3;
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                        httpURLConnection2 = httpURLConnection3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection2 = httpURLConnection;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            }
        }).start();
    }

    public final URL getCaptivePortalCheckUrl() {
        return makeURL("http://connectivitycheck.gstatic.com/generate_204");
    }

    public static URL makeURL(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            Log.e(TAG, "Invalid URL " + str);
            return null;
        }
    }

    public final String getCaptivePortalUid() {
        return String.valueOf(getApplicationUid("com.google.android.captiveportallogin", 0));
    }

    public final void initUserIdMap() {
        List<UserInfo> users;
        boolean z;
        boolean z2;
        Log.i(TAG, "initUserIdMap() - Initializing UserID Mapping");
        PackageManager packageManager = this.mContext.getPackageManager();
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager == null || (users = userManager.getUsers()) == null) {
            return;
        }
        synchronized (this.mExceptionListLock) {
            z = false;
            if (this.mIsExceptionListApplied || !hasAnyRuleInDatabase()) {
                z2 = false;
            } else {
                this.mIsExceptionListApplied = true;
                z2 = true;
            }
        }
        synchronized (this.mKGExceptionListLock) {
            if (!this.mIsKGExceptionApplied && hasAnyRuleInDatabase() && FirewallUtils.isKGExemptRuleRequired()) {
                this.mIsKGExceptionApplied = true;
                z = true;
            }
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mUserIdMapLock) {
            for (UserInfo userInfo : users) {
                HashMap hashMap = new HashMap();
                List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(64, userInfo.id);
                Iterator it = installedPackagesAsUser.iterator();
                while (it.hasNext()) {
                    ApplicationInfo applicationInfo = ((PackageInfo) it.next()).applicationInfo;
                    hashMap.put(applicationInfo.packageName, Integer.valueOf(applicationInfo.uid));
                }
                this.mUserIdMap.put(Integer.valueOf(userInfo.id), hashMap);
                arrayList.addAll(installedPackagesAsUser);
            }
        }
        if (z2) {
            processPackageExceptionList(arrayList);
        }
        if (z) {
            processKGPackageException(arrayList);
        }
    }

    public final void onUserAdded(Intent intent) {
        List list;
        Log.i(TAG, "onUserAdded()");
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        if (intExtra != -1) {
            Log.i(TAG, "onUserAdded() - userId = " + intExtra);
            synchronized (this.mUserIdMapLock) {
                if (((Map) this.mUserIdMap.get(Integer.valueOf(intExtra))) == null) {
                    HashMap hashMap = new HashMap();
                    list = this.mContext.getPackageManager().getInstalledPackagesAsUser(64, intExtra);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ApplicationInfo applicationInfo = ((PackageInfo) it.next()).applicationInfo;
                        hashMap.put(applicationInfo.packageName, Integer.valueOf(applicationInfo.uid));
                    }
                    this.mUserIdMap.put(Integer.valueOf(intExtra), hashMap);
                } else {
                    list = null;
                }
            }
            synchronized (this.mExceptionListLock) {
                if (this.mIsExceptionListApplied) {
                    if (list != null && !list.isEmpty()) {
                        processPackageExceptionList(list);
                    }
                    synchronized (this.mKGExceptionListLock) {
                        if (this.mIsKGExceptionApplied) {
                            if (list == null || list.isEmpty()) {
                                return;
                            }
                            processKGPackageException(list);
                        }
                    }
                }
            }
        }
    }

    public final void onUserRemoved(Intent intent) {
        Log.i(TAG, "onUserRemoved()");
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        if (intExtra != -1) {
            Log.i(TAG, "onUserRemoved() - userId = " + intExtra);
            Integer valueOf = Integer.valueOf(intExtra);
            synchronized (this.mUserIdMapLock) {
                this.mUserIdMap.remove(valueOf);
            }
            if (this.mDomainReportCache.contains(valueOf)) {
                this.mDomainReportCache.remove(valueOf);
                sendToCache(Operation.CLEAR.ordinal(), Integer.toString(intExtra), null, null, null, null);
            }
        }
    }

    public final int getApplicationUid(String str, int i) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 128, i);
            } catch (Exception e) {
                Log.e(TAG, "getApplicationUid() - exception getting package info ", e);
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

    public final void packageAdded(Intent intent) {
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        int i = -1;
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        if (intExtra == -1 || schemeSpecificPart == null) {
            Log.i(TAG, "packageAdded() - Received invalid user id or package name, can't retrieve application info");
            return;
        }
        int applicationUid = getApplicationUid(schemeSpecificPart, intExtra);
        Log.i(TAG, "packageAdded() - packageName: " + schemeSpecificPart + ", uid: " + intExtra);
        if (applicationUid == -1) {
            Log.i(TAG, "packageAdded() - Failed to retrieve app info");
            return;
        }
        synchronized (this.mUserIdMapLock) {
            Integer valueOf = Integer.valueOf(intExtra);
            if (this.mUserIdMap.get(valueOf) == null) {
                HashMap hashMap = new HashMap();
                hashMap.put(schemeSpecificPart, Integer.valueOf(applicationUid));
                this.mUserIdMap.put(valueOf, hashMap);
            } else {
                ((Map) this.mUserIdMap.get(valueOf)).put(schemeSpecificPart, Integer.valueOf(applicationUid));
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra), "#SelectClause#");
        contentValues.put("packageName", schemeSpecificPart);
        List values = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues);
        if (values == null || values.isEmpty()) {
            return;
        }
        ContentValues contentValues2 = (ContentValues) values.get(0);
        String asString = contentValues2.getAsString("signature");
        if (TextUtils.isEmpty(asString) || validatePkgSignature(intExtra, schemeSpecificPart, asString)) {
            List listFromDb = FirewallUtils.getListFromDb(contentValues2, "blacklist", this.mEdmStorageProvider);
            List listFromDb2 = FirewallUtils.getListFromDb(contentValues2, "whitelist", this.mEdmStorageProvider);
            String asString2 = contentValues2.getAsString("dns1");
            if (listFromDb.isEmpty() && listFromDb2.isEmpty() && TextUtils.isEmpty(asString2)) {
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
            sendToCache(Operation.ADD.ordinal(), String.valueOf(applicationUid), null, listFromDb, listFromDb2, String.valueOf(i));
            return;
        }
        Log.i(TAG, "packageAdded() - Installed app's signature mismatched the one provided by admin.");
    }

    public final void packageRemoved(Intent intent) {
        String str;
        String str2;
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        Log.i(TAG, "packageRemoved() - packageName: " + schemeSpecificPart + " uid: " + intExtra);
        if (intExtra == -1 || schemeSpecificPart == null) {
            return;
        }
        synchronized (this.mUserIdMapLock) {
            Map map = (Map) this.mUserIdMap.get(Integer.valueOf(intExtra));
            if (map == null || !map.containsKey(schemeSpecificPart)) {
                str = null;
            } else {
                str = ((Integer) map.get(schemeSpecificPart)).toString();
                map.remove(schemeSpecificPart);
            }
            str2 = str;
        }
        if (str2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra), "#SelectClause#");
            contentValues.put("packageName", schemeSpecificPart);
            List values = this.mEdmStorageProvider.getValues("DomainFilterTable", new String[]{"networkId"}, contentValues);
            if (values == null || values.isEmpty()) {
                return;
            }
            ContentValues contentValues2 = (ContentValues) values.get(0);
            Integer asInteger = contentValues2.getAsInteger("networkId");
            int intValue = asInteger != null ? asInteger.intValue() : -1;
            if (intValue != -1) {
                this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, Integer.valueOf(intValue)));
                contentValues2.put("networkId", (Integer) (-1));
                this.mEdmStorageProvider.put("DomainFilterTable", contentValues2, contentValues);
            }
            sendToCache(Operation.CLEAR.ordinal(), str2, null, null, null, String.valueOf(intValue));
        }
    }

    public final void packageReplaced(Intent intent) {
        Map map;
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        if (intExtra == -1 || schemeSpecificPart == null) {
            Log.i(TAG, "packageReplaced() - Received invalid user id or package name, can't retrieve application info");
            return;
        }
        synchronized (this.mUserIdMapLock) {
            map = (Map) this.mUserIdMap.get(Integer.valueOf(intExtra));
        }
        if (map == null || !map.containsKey(schemeSpecificPart)) {
            packageAdded(intent);
            return;
        }
        int intValue = ((Integer) map.get(schemeSpecificPart)).intValue();
        int applicationUid = getApplicationUid(schemeSpecificPart, intExtra);
        Log.i(TAG, "packageReplaced() - packageName: " + schemeSpecificPart + " oldUid: " + intValue + " newUid:" + applicationUid);
        if (applicationUid == -1) {
            Log.i(TAG, "ackageReplaced() - Failed to retrieve app info");
            return;
        }
        synchronized (this.mUserIdMapLock) {
            ((Map) this.mUserIdMap.get(Integer.valueOf(intExtra))).put(schemeSpecificPart, Integer.valueOf(applicationUid));
        }
        if (intValue != applicationUid) {
            sendToCache(Operation.REPLACE.ordinal(), Integer.toString(intValue), Integer.toString(applicationUid), null, null, null);
        }
    }

    public final boolean addDomainListInDataBase(int i, String str, List list, String str2, List list2, List list3, Set set) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("packageName", str);
            contentValues.put("typeList", str2);
            contentValues.put("domain", str3);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", Integer.valueOf(i));
            contentValues2.put("packageName", str);
            contentValues2.put("domain", str3);
            if (!this.mEdmStorageProvider.put("DomainFilterListTable", contentValues, contentValues2)) {
                Log.i(TAG, "addDomainListInDataBase()- Any " + str2 + " couldn't be added");
                rollbackDomainList(i, str, list2, list3, set, true);
                return false;
            }
            set.add(str3);
        }
        return true;
    }

    public final boolean addDomainListInDataBase(ContextInfo contextInfo, DomainFilterRule domainFilterRule, List list, List list2) {
        int i = contextInfo.mCallerUid;
        String packageName = domainFilterRule.getApplication().getPackageName();
        HashSet hashSet = new HashSet();
        List denyDomains = domainFilterRule.getDenyDomains();
        List allowDomains = domainFilterRule.getAllowDomains();
        Log.i(TAG, "addDomainListInDataBase()");
        boolean addDomainListInDataBase = addDomainListInDataBase(i, packageName, denyDomains, "blacklist", list, list2, hashSet);
        if (addDomainListInDataBase) {
            addDomainListInDataBase = addDomainListInDataBase(i, packageName, allowDomains, "whitelist", list, list2, hashSet);
        }
        if (addDomainListInDataBase) {
            Log.i(TAG, "addDomainListInDataBase()- All domains were added");
        }
        return addDomainListInDataBase;
    }

    public final void rollbackDomainList(int i, String str, List list, List list2, Set set, boolean z) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("packageName", str);
            contentValues.put("domain", str2);
            ContentValues contentValues2 = new ContentValues();
            if (list.contains(str2)) {
                contentValues2.put("typeList", "blacklist");
            } else if (list2.contains(str2)) {
                contentValues2.put("typeList", "whitelist");
            }
            if (z && !list.contains(str2) && !list2.contains(str2)) {
                this.mEdmStorageProvider.delete("DomainFilterListTable", contentValues);
                Log.i(TAG, "rollbackDomainList()- Rollback for all domains that were added");
            } else {
                Log.i(TAG, "rollbackDomainList()- Rollback for all domains thatwere (Added =" + z + ")");
                this.mEdmStorageProvider.put("DomainFilterListTable", contentValues2, contentValues);
            }
        }
    }

    public synchronized void populateDomainFilterBrokenRules(ContextInfo contextInfo, List list, int i) {
        List list2 = (List) this.mDomainFilterBrokenRules.get(Integer.valueOf(i));
        if (list2 == null) {
            list2 = new ArrayList();
        }
        list2.addAll(list);
        this.mDomainFilterBrokenRules.put(Integer.valueOf(i), list2);
    }

    public final synchronized List reassembleDomainFilterBrokenRules(ContextInfo contextInfo, int i) {
        ArrayList arrayList;
        boolean z;
        arrayList = new ArrayList();
        for (DomainFilterRule domainFilterRule : (List) this.mDomainFilterBrokenRules.get(Integer.valueOf(i))) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                DomainFilterRule domainFilterRule2 = (DomainFilterRule) it.next();
                if (domainFilterRule2.getIpcToken() != 0 && domainFilterRule2.getIpcToken() == domainFilterRule.getIpcToken()) {
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
                    z = true;
                }
            }
            if (!z) {
                arrayList.add(domainFilterRule);
            }
        }
        this.mDomainFilterBrokenRules.remove(Integer.valueOf(i));
        return arrayList;
    }

    public synchronized FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) {
        return addDomainFilterRules(contextInfo, reassembleDomainFilterBrokenRules(contextInfo, i));
    }

    public synchronized FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) {
        return removeDomainFilterRules(contextInfo, reassembleDomainFilterBrokenRules(contextInfo, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x031f A[Catch: all -> 0x046a, TryCatch #0 {, blocks: (B:10:0x000b, B:13:0x0013, B:15:0x003a, B:18:0x0052, B:19:0x0054, B:23:0x0062, B:24:0x006e, B:26:0x0074, B:28:0x007c, B:31:0x03ce, B:32:0x009b, B:34:0x00aa, B:36:0x00b0, B:38:0x00b6, B:40:0x00bc, B:42:0x00c6, B:44:0x00d0, B:45:0x00e5, B:47:0x00f4, B:48:0x0112, B:50:0x0128, B:51:0x012f, B:53:0x0137, B:56:0x0141, B:58:0x0159, B:60:0x0167, B:62:0x0161, B:64:0x017d, B:66:0x0183, B:67:0x0199, B:69:0x01e1, B:71:0x01e7, B:72:0x01f2, B:74:0x01fb, B:75:0x0204, B:77:0x020a, B:78:0x0213, B:80:0x0225, B:84:0x029a, B:87:0x02a1, B:89:0x02a8, B:92:0x02c0, B:94:0x02c9, B:96:0x02d2, B:98:0x02db, B:100:0x02e1, B:101:0x02f4, B:102:0x030d, B:104:0x031f, B:106:0x0335, B:107:0x0347, B:109:0x034f, B:111:0x0365, B:113:0x036e, B:117:0x0381, B:118:0x038e, B:120:0x03b0, B:122:0x03b8, B:129:0x0235, B:131:0x0250, B:137:0x0264, B:139:0x027e, B:141:0x0284, B:143:0x0290, B:145:0x0293, B:151:0x020f, B:152:0x0200, B:157:0x03e0, B:159:0x041d, B:162:0x0427, B:164:0x0435, B:166:0x0443, B:170:0x0448, B:176:0x044f, B:5:0x0450, B:21:0x0055, B:22:0x0061), top: B:9:0x000b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0347 A[Catch: all -> 0x046a, TryCatch #0 {, blocks: (B:10:0x000b, B:13:0x0013, B:15:0x003a, B:18:0x0052, B:19:0x0054, B:23:0x0062, B:24:0x006e, B:26:0x0074, B:28:0x007c, B:31:0x03ce, B:32:0x009b, B:34:0x00aa, B:36:0x00b0, B:38:0x00b6, B:40:0x00bc, B:42:0x00c6, B:44:0x00d0, B:45:0x00e5, B:47:0x00f4, B:48:0x0112, B:50:0x0128, B:51:0x012f, B:53:0x0137, B:56:0x0141, B:58:0x0159, B:60:0x0167, B:62:0x0161, B:64:0x017d, B:66:0x0183, B:67:0x0199, B:69:0x01e1, B:71:0x01e7, B:72:0x01f2, B:74:0x01fb, B:75:0x0204, B:77:0x020a, B:78:0x0213, B:80:0x0225, B:84:0x029a, B:87:0x02a1, B:89:0x02a8, B:92:0x02c0, B:94:0x02c9, B:96:0x02d2, B:98:0x02db, B:100:0x02e1, B:101:0x02f4, B:102:0x030d, B:104:0x031f, B:106:0x0335, B:107:0x0347, B:109:0x034f, B:111:0x0365, B:113:0x036e, B:117:0x0381, B:118:0x038e, B:120:0x03b0, B:122:0x03b8, B:129:0x0235, B:131:0x0250, B:137:0x0264, B:139:0x027e, B:141:0x0284, B:143:0x0290, B:145:0x0293, B:151:0x020f, B:152:0x0200, B:157:0x03e0, B:159:0x041d, B:162:0x0427, B:164:0x0435, B:166:0x0443, B:170:0x0448, B:176:0x044f, B:5:0x0450, B:21:0x0055, B:22:0x0061), top: B:9:0x000b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0284 A[Catch: all -> 0x046a, TryCatch #0 {, blocks: (B:10:0x000b, B:13:0x0013, B:15:0x003a, B:18:0x0052, B:19:0x0054, B:23:0x0062, B:24:0x006e, B:26:0x0074, B:28:0x007c, B:31:0x03ce, B:32:0x009b, B:34:0x00aa, B:36:0x00b0, B:38:0x00b6, B:40:0x00bc, B:42:0x00c6, B:44:0x00d0, B:45:0x00e5, B:47:0x00f4, B:48:0x0112, B:50:0x0128, B:51:0x012f, B:53:0x0137, B:56:0x0141, B:58:0x0159, B:60:0x0167, B:62:0x0161, B:64:0x017d, B:66:0x0183, B:67:0x0199, B:69:0x01e1, B:71:0x01e7, B:72:0x01f2, B:74:0x01fb, B:75:0x0204, B:77:0x020a, B:78:0x0213, B:80:0x0225, B:84:0x029a, B:87:0x02a1, B:89:0x02a8, B:92:0x02c0, B:94:0x02c9, B:96:0x02d2, B:98:0x02db, B:100:0x02e1, B:101:0x02f4, B:102:0x030d, B:104:0x031f, B:106:0x0335, B:107:0x0347, B:109:0x034f, B:111:0x0365, B:113:0x036e, B:117:0x0381, B:118:0x038e, B:120:0x03b0, B:122:0x03b8, B:129:0x0235, B:131:0x0250, B:137:0x0264, B:139:0x027e, B:141:0x0284, B:143:0x0290, B:145:0x0293, B:151:0x020f, B:152:0x0200, B:157:0x03e0, B:159:0x041d, B:162:0x0427, B:164:0x0435, B:166:0x0443, B:170:0x0448, B:176:0x044f, B:5:0x0450, B:21:0x0055, B:22:0x0061), top: B:9:0x000b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x029f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, List list) {
        Map map;
        int i;
        Map map2;
        int i2;
        JSONArray jSONArray;
        boolean z;
        boolean z2;
        ContentValues contentValues;
        List list2;
        boolean z3;
        int i3;
        List list3;
        List list4;
        ContextInfo contextInfo2;
        boolean z4;
        int i4;
        ContentValues contentValues2;
        int i5;
        Integer asInteger;
        ContextInfo contextInfo3 = contextInfo;
        List list5 = list;
        synchronized (this) {
            boolean z5 = true;
            if (list5 != null) {
                if (!list.isEmpty()) {
                    Log.i(TAG, "addDomainFilterRules() - rules.size = " + list.size());
                    int i6 = contextInfo3.mCallerUid;
                    int userId = UserHandle.getUserId(i6);
                    if (checkAdminInDatabase(i6) == -1) {
                        Log.i(TAG, "addDomainFilterRules() - Another admin already have domain filter rules in the database");
                        return new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR, "This administrator can't execute this operation because he is not the owner.")};
                    }
                    synchronized (this.mUserIdMapLock) {
                        map = (Map) this.mUserIdMap.get(Integer.valueOf(userId));
                    }
                    int size = list.size();
                    FirewallResponse[] firewallResponseArr = new FirewallResponse[size];
                    JSONArray jSONArray2 = new JSONArray();
                    int i7 = 0;
                    while (i7 < list.size()) {
                        DomainFilterRule domainFilterRule = (DomainFilterRule) list5.get(i7);
                        if (domainFilterRule == null) {
                            Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - No rule specified");
                            firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "No rule was specified.");
                        } else {
                            String dns1 = domainFilterRule.getDns1();
                            String dns2 = domainFilterRule.getDns2();
                            boolean isEmpty = TextUtils.isEmpty(dns1) ^ z5;
                            if (!isEmpty && TextUtils.isEmpty(dns2) && (domainFilterRule.getDenyDomains() == null || domainFilterRule.getAllowDomains() == null || (domainFilterRule.getDenyDomains().isEmpty() && domainFilterRule.getAllowDomains().isEmpty()))) {
                                Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - mandatory parameters missing");
                                firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "No parameters provided.");
                            } else {
                                setDomainFilterRulesProperties(domainFilterRule, jSONArray2);
                                FirewallResponse validateApplicationIdentity = validateApplicationIdentity(domainFilterRule.getApplication());
                                firewallResponseArr[i7] = validateApplicationIdentity;
                                if (validateApplicationIdentity != null) {
                                    Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - invalid Application Identity: " + firewallResponseArr[i7].getMessage());
                                } else {
                                    String packageName = domainFilterRule.getApplication().getPackageName();
                                    String signature = domainFilterRule.getApplication().getSignature();
                                    boolean validatePkgSignature = !TextUtils.isEmpty(signature) ? validatePkgSignature(userId, packageName, signature) : true;
                                    boolean verifyAppInstalled = !"*".equals(packageName) ? verifyAppInstalled(map, packageName, userId) : true;
                                    if (!validatePkgSignature && verifyAppInstalled) {
                                        Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - signature mismatch");
                                        firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Given signature does not match with the application.");
                                    } else if ((!isEmpty && !TextUtils.isEmpty(dns2)) || (isEmpty && !validateDnses(dns1, dns2))) {
                                        Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - invalid DNS");
                                        firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Invalid DNS(s) provided");
                                    } else if (!validateDomains(domainFilterRule)) {
                                        Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - invalid domain");
                                        firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Invalid domain.");
                                    } else {
                                        i = userId;
                                        ContentValues contentValues3 = new ContentValues();
                                        map2 = map;
                                        i2 = size;
                                        contentValues3.put("adminUid", Integer.valueOf(i6));
                                        contentValues3.put("packageName", packageName);
                                        contentValues3.put("signature", signature);
                                        ContentValues contentValues4 = new ContentValues();
                                        jSONArray = jSONArray2;
                                        contentValues4.put("adminUid", Integer.valueOf(i6));
                                        contentValues4.put("packageName", packageName);
                                        List values = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues4);
                                        ContentValues contentValues5 = new ContentValues();
                                        if (values == null || values.isEmpty()) {
                                            z = false;
                                        } else {
                                            contentValues5 = (ContentValues) values.get(0);
                                            z = true;
                                        }
                                        prepareDomainUrls(domainFilterRule);
                                        List emptyList = domainFilterRule.getDenyDomains() == null ? Collections.emptyList() : domainFilterRule.getDenyDomains();
                                        List emptyList2 = domainFilterRule.getAllowDomains() == null ? Collections.emptyList() : domainFilterRule.getAllowDomains();
                                        List arrayList = new ArrayList();
                                        List arrayList2 = new ArrayList();
                                        if (emptyList.isEmpty() && emptyList2.isEmpty()) {
                                            list3 = arrayList;
                                            z2 = z;
                                            list4 = arrayList2;
                                            contentValues = contentValues4;
                                            domainFilterRule.setDenyDomains(emptyList);
                                            if (isEmpty) {
                                                if (verifyAppInstalled) {
                                                    i5 = setupNetworkDns(dns1, dns2);
                                                    if (i5 == -1) {
                                                        Log.i(TAG, "addDomainFilterRules() - Skipping invalid rule - Invalid netID");
                                                        firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, "Error occurred applying DNS(s)");
                                                        contextInfo2 = contextInfo;
                                                        i7++;
                                                        list5 = list;
                                                        contextInfo3 = contextInfo2;
                                                        userId = i;
                                                        map = map2;
                                                        size = i2;
                                                        jSONArray2 = jSONArray;
                                                        z5 = true;
                                                    } else if (!contentValues5.containsKey("networkId") || (asInteger = contentValues5.getAsInteger("networkId")) == null) {
                                                        z4 = verifyAppInstalled;
                                                    } else {
                                                        z4 = verifyAppInstalled;
                                                        if (asInteger.intValue() != -1 && i5 != asInteger.intValue()) {
                                                            this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, asInteger));
                                                        }
                                                    }
                                                } else {
                                                    z4 = verifyAppInstalled;
                                                    i5 = -1;
                                                }
                                                contentValues3.put("dns1", dns1);
                                                contentValues3.put("dns2", dns2);
                                                contentValues3.put("networkId", Integer.valueOf(i5));
                                                i4 = i5;
                                            } else {
                                                z4 = verifyAppInstalled;
                                                i4 = -1;
                                            }
                                            boolean hasDenyRuleInDatabase = this.mFirewallRulesApplier.hasDenyRuleInDatabase(i6, packageName);
                                            contentValues2 = contentValues;
                                            if (!this.mEdmStorageProvider.put("DomainFilterTable", contentValues3, contentValues2)) {
                                                Log.i(TAG, "addDomainFilterRules() - Failed to add rule in the database");
                                                firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to add/update rule in the database.");
                                                if (isEmpty) {
                                                    this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, Integer.valueOf(i4)));
                                                }
                                                contextInfo2 = contextInfo;
                                                i7++;
                                                list5 = list;
                                                contextInfo3 = contextInfo2;
                                                userId = i;
                                                map = map2;
                                                size = i2;
                                                jSONArray2 = jSONArray;
                                                z5 = true;
                                            } else {
                                                contextInfo2 = contextInfo;
                                                if (!addDomainListInDataBase(contextInfo2, domainFilterRule, list4, list3)) {
                                                    Log.i(TAG, "addDomainFilterRules() - Failed to add rule in the database -domains list");
                                                    firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to add/update rule in the database.");
                                                    if (!z2) {
                                                        this.mEdmStorageProvider.delete("DomainFilterTable", contentValues2);
                                                    }
                                                    if (isEmpty) {
                                                        this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, Integer.valueOf(i4)));
                                                    }
                                                } else {
                                                    if (z4) {
                                                        updateDaemonCache(contextInfo2, domainFilterRule, Operation.ADD.ordinal(), String.valueOf(i4));
                                                    }
                                                    processPackageExceptionListDelayed();
                                                    processKGPackageExceptionDelayed();
                                                    firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule(s) was successfully added/updated.");
                                                    this.mDomainFilterNapCommon.setDomainFilterEnabled(true);
                                                    AppIdentity appIdentity = new AppIdentity(packageName, signature);
                                                    if (!hasDenyRuleInDatabase && this.mFirewallRulesApplier.blockPort53(appIdentity, contextInfo2, true)) {
                                                        Log.i(TAG, "blockPort53() - port53 rule added successfully. Package: " + packageName);
                                                    }
                                                }
                                                i7++;
                                                list5 = list;
                                                contextInfo3 = contextInfo2;
                                                userId = i;
                                                map = map2;
                                                size = i2;
                                                jSONArray2 = jSONArray;
                                                z5 = true;
                                            }
                                        }
                                        z2 = z;
                                        List listFromDb = FirewallUtils.getListFromDb(contentValues5, "whitelist", this.mEdmStorageProvider);
                                        contentValues = contentValues4;
                                        List listFromDb2 = FirewallUtils.getListFromDb(contentValues5, "blacklist", this.mEdmStorageProvider);
                                        if (listFromDb2.containsAll(emptyList)) {
                                            list2 = emptyList2;
                                            if (listFromDb.containsAll(list2)) {
                                                z3 = false;
                                                if (z3 && !isEmpty) {
                                                    Log.i(TAG, "addDomainFilterRules() - Skipping rule - This rule is already in the database");
                                                    firewallResponseArr[i7] = new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "The specified rule is already in the database.");
                                                    contextInfo2 = contextInfo;
                                                    i7++;
                                                    list5 = list;
                                                    contextInfo3 = contextInfo2;
                                                    userId = i;
                                                    map = map2;
                                                    size = i2;
                                                    jSONArray2 = jSONArray;
                                                    z5 = true;
                                                } else {
                                                    for (i3 = 0; i3 < list2.size(); i3++) {
                                                        String str = (String) list2.get(i3);
                                                        if (emptyList.contains(str)) {
                                                            emptyList.remove(str);
                                                        }
                                                    }
                                                    list3 = listFromDb;
                                                    list4 = listFromDb2;
                                                    domainFilterRule.setDenyDomains(emptyList);
                                                    if (isEmpty) {
                                                    }
                                                    boolean hasDenyRuleInDatabase2 = this.mFirewallRulesApplier.hasDenyRuleInDatabase(i6, packageName);
                                                    contentValues2 = contentValues;
                                                    if (!this.mEdmStorageProvider.put("DomainFilterTable", contentValues3, contentValues2)) {
                                                    }
                                                }
                                            }
                                        } else {
                                            list2 = emptyList2;
                                        }
                                        z3 = true;
                                        if (z3) {
                                        }
                                        while (i3 < list2.size()) {
                                        }
                                        list3 = listFromDb;
                                        list4 = listFromDb2;
                                        domainFilterRule.setDenyDomains(emptyList);
                                        if (isEmpty) {
                                        }
                                        boolean hasDenyRuleInDatabase22 = this.mFirewallRulesApplier.hasDenyRuleInDatabase(i6, packageName);
                                        contentValues2 = contentValues;
                                        if (!this.mEdmStorageProvider.put("DomainFilterTable", contentValues3, contentValues2)) {
                                        }
                                    }
                                }
                            }
                        }
                        i = userId;
                        i2 = size;
                        map2 = map;
                        jSONArray = jSONArray2;
                        contextInfo2 = contextInfo3;
                        i7++;
                        list5 = list;
                        contextInfo3 = contextInfo2;
                        userId = i;
                        map = map2;
                        size = i2;
                        jSONArray2 = jSONArray;
                        z5 = true;
                    }
                    ContextInfo contextInfo4 = contextInfo3;
                    int i8 = size;
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_SECURE_NETWORK", 1, "addDomainFilterRules");
                    knoxAnalyticsData.setUserTypeProperty(contextInfo4.mContainerId);
                    knoxAnalyticsData.setProperty("rules", "\"" + jSONArray2.toString() + "\"");
                    KnoxAnalytics.log(knoxAnalyticsData);
                    if (isDomainFilterOnIptablesEnabled(contextInfo)) {
                        ArrayList arrayList3 = new ArrayList();
                        int i9 = 0;
                        while (true) {
                            int i10 = i8;
                            if (i9 >= i10) {
                                break;
                            }
                            if (firewallResponseArr[i9].getResult().equals(FirewallResponse.Result.SUCCESS)) {
                                arrayList3.add((DomainFilterRule) list.get(i9));
                            }
                            i9++;
                            i8 = i10;
                        }
                        addDomainRules(contextInfo4, arrayList3);
                    }
                    return firewallResponseArr;
                }
            }
            Log.i(TAG, "addDomainFilterRules() - No rule specified");
            return new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "No rule was specified.")};
        }
    }

    public final boolean removeDomainListInDatabase(ContextInfo contextInfo, DomainFilterRule domainFilterRule, List list, List list2, List list3) {
        int i = contextInfo.mCallerUid;
        String packageName = domainFilterRule.getApplication().getPackageName();
        if (domainFilterRule.getAllowDomains() == null && domainFilterRule.getDenyDomains() == null) {
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
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("packageName", packageName);
            contentValues.put("domain", str);
            if (this.mEdmStorageProvider.delete("DomainFilterListTable", contentValues) <= 0) {
                rollbackDomainList(i, packageName, list2, list3, hashSet, false);
                Log.i(TAG, "removeDomainListInDatabase()- Any deny domain couldn't be removed");
                return false;
            }
            hashSet.add(str);
        }
        Log.i(TAG, "removeDomainListInDatabase()- All domains were removed");
        return true;
    }

    public synchronized FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, List list) {
        FirewallResponse[] firewallResponseArr;
        int i;
        int i2;
        char c;
        int i3;
        ArrayList arrayList;
        int i4;
        HashSet hashSet;
        boolean z;
        HashSet hashSet2;
        boolean z2;
        ArrayList arrayList2;
        List list2 = list;
        synchronized (this) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("removeDomainFilterRules() - rules.size = ");
            sb.append(list2 != null ? Integer.valueOf(list.size()) : "0");
            Log.i(str, sb.toString());
            int i5 = 0;
            if (list2 != null && list.isEmpty()) {
                Log.i(TAG, "removeDomainFilterRules() - No rule specified");
                return new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "No rule was specified.")};
            }
            int i6 = contextInfo.mCallerUid;
            int userId = UserHandle.getUserId(i6);
            int checkAdminInDatabase = checkAdminInDatabase(i6);
            if (checkAdminInDatabase == 0) {
                Log.i(TAG, "removeDomainFilterRules() - Database is already empty");
                firewallResponseArr = new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "The rules are already cleared.")};
            } else {
                char c2 = 65535;
                if (checkAdminInDatabase == -1) {
                    Log.i(TAG, "removeDomainFilterRules() - Another admin is handling domainfilter rules in the database");
                    firewallResponseArr = new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR, "This administrator can't execute this operation because he is not the owner.")};
                } else {
                    String[] strArr = null;
                    if (list2 == DomainFilterRule.CLEAR_ALL) {
                        FirewallResponse[] firewallResponseArr2 = new FirewallResponse[1];
                        releaseNetworks(i6);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("adminUid", Integer.valueOf(i6));
                        List values = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues);
                        if (this.mEdmStorageProvider.delete("DomainFilterTable", contentValues) <= 0) {
                            Log.i(TAG, "removeDomainFilterRules() - Failed to clear rulesfrom the database");
                            firewallResponseArr2[0] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to clear rules from database.");
                        } else if (this.mEdmStorageProvider.delete("DomainFilterListTable", contentValues) < 0) {
                            Iterator it = values.iterator();
                            while (it.hasNext()) {
                                this.mEdmStorageProvider.put("DomainFilterTable", (ContentValues) it.next(), contentValues);
                            }
                            Log.i(TAG, "removeDomainFilterRules() - Failed to clear rules from the database (url)");
                            firewallResponseArr2[0] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to clear rules from database.");
                        } else {
                            firewallResponseArr2[0] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "Rules successfully cleared. Admin: " + i6);
                            if (isDomainFilterOnIptablesEnabled(contextInfo) && !this.mFirewallRulesApplier.flushDomainChains(Integer.valueOf(userId), false)) {
                                Log.e(TAG, "flushDomainChains() - Failed to flush domain chains");
                            }
                            if (!this.mFirewallRulesApplier.flushDnsPortChains(Integer.valueOf(userId), false, contextInfo)) {
                                Log.e(TAG, "flushDnsPortChains() - Failed to flush port53 chains");
                            }
                            Log.i(TAG, "flushDomainChains() - domain rules successfully removed");
                        }
                        updateDaemonCache(contextInfo, null, Operation.CLEAR.ordinal(), null);
                        firewallResponseArr = firewallResponseArr2;
                    } else {
                        int size = list.size();
                        FirewallResponse[] firewallResponseArr3 = new FirewallResponse[size];
                        ArrayList arrayList3 = new ArrayList();
                        int i7 = 0;
                        while (i7 < list.size()) {
                            DomainFilterRule domainFilterRule = (DomainFilterRule) list2.get(i7);
                            FirewallResponse validateApplicationIdentity = validateApplicationIdentity(domainFilterRule.getApplication());
                            firewallResponseArr3[i7] = validateApplicationIdentity;
                            if (validateApplicationIdentity != null) {
                                Log.i(TAG, "removeDomainFilterRules() - Skipping invalid rule - invalidApplication Identity" + firewallResponseArr3[i7].getMessage());
                                i = i7;
                                i2 = i6;
                                c = c2;
                                i3 = size;
                                arrayList = arrayList3;
                            } else {
                                String packageName = domainFilterRule.getApplication().getPackageName();
                                ContentValues contentValues2 = new ContentValues();
                                contentValues2.put("adminUid", Integer.valueOf(i6));
                                contentValues2.put("packageName", packageName);
                                List values2 = this.mEdmStorageProvider.getValues("DomainFilterTable", strArr, contentValues2);
                                if (values2 != null && !values2.isEmpty()) {
                                    ContentValues contentValues3 = (ContentValues) values2.get(i5);
                                    String asString = contentValues3.getAsString("signature");
                                    String signature = domainFilterRule.getApplication().getSignature();
                                    if ((signature != null && asString == null) || ((signature == null && asString != null) || (signature != null && asString != null && !signature.equals(asString)))) {
                                        Log.i(TAG, "removeDomainFilterRules() - Skipping invalid rule - signature mismatch");
                                        firewallResponseArr3[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Signature does not match with the previous added.");
                                    } else {
                                        if (TextUtils.isEmpty(domainFilterRule.getDns1())) {
                                            i4 = i5;
                                        } else {
                                            FirewallResponse removeDnsForApplication = removeDnsForApplication(domainFilterRule, i6, contentValues3);
                                            firewallResponseArr3[i7] = removeDnsForApplication;
                                            if (removeDnsForApplication != null) {
                                                Log.i(TAG, "removeDomainFilterRules() - Skipping rule - Failed to remove DNS");
                                            } else {
                                                i4 = 1;
                                            }
                                        }
                                        List listFromDb = FirewallUtils.getListFromDb(contentValues3, "blacklist", this.mEdmStorageProvider);
                                        List listFromDb2 = FirewallUtils.getListFromDb(contentValues3, "whitelist", this.mEdmStorageProvider);
                                        prepareDomainUrls(domainFilterRule);
                                        List denyDomains = domainFilterRule.getDenyDomains();
                                        List allowDomains = domainFilterRule.getAllowDomains();
                                        if (denyDomains != null) {
                                            hashSet = new HashSet(listFromDb);
                                            z = hashSet.removeAll(denyDomains) | false;
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
                                            boolean z3 = z | true;
                                            if (denyDomains != null) {
                                                arrayList3.addAll(listFromDb2);
                                            }
                                            hashSet2 = hashSet3;
                                            z2 = z3;
                                        }
                                        if (!z2 && i4 == 0) {
                                            Log.i(TAG, "removeDomainFilterRules() - Skipping rule - Rule is not in the database");
                                            firewallResponseArr3[i7] = new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "The rule is not in the database.");
                                        } else {
                                            boolean z4 = contentValues3.getAsString("dns1") != null;
                                            if (hashSet.isEmpty() && hashSet2.isEmpty() && (i4 != 0 || !z4)) {
                                                List values3 = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues2);
                                                if (this.mEdmStorageProvider.delete("DomainFilterTable", contentValues2) <= 0) {
                                                    Log.e(TAG, "removeDomainFilterRules() - Failed to remove rule fromthe database");
                                                    firewallResponseArr3[i7] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove/update rule from the database.");
                                                    i = i7;
                                                    arrayList2 = arrayList3;
                                                    i2 = i6;
                                                    i3 = size;
                                                } else {
                                                    i3 = size;
                                                    int i8 = i6;
                                                    i = i7;
                                                    arrayList2 = arrayList3;
                                                    if (!removeDomainListInDatabase(contextInfo, domainFilterRule, arrayList3, listFromDb, listFromDb2)) {
                                                        this.mEdmStorageProvider.put("DomainFilterTable", (ContentValues) values3.get(0), contentValues2);
                                                        Log.e(TAG, "removeDomainFilterRules() - Failed to remove rule fromthe database");
                                                        firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove/update rule from the database.");
                                                        i2 = i8;
                                                    } else {
                                                        Integer asInteger = contentValues3.getAsInteger("networkId");
                                                        if (asInteger != null) {
                                                            updateDaemonCache(contextInfo, domainFilterRule, Operation.REMOVE.ordinal(), String.valueOf(asInteger.intValue()));
                                                            firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule was successfuly removed/updated.");
                                                            i2 = i8;
                                                            if (!this.mFirewallRulesApplier.hasDenyRuleInDatabase(i2, packageName) && this.mFirewallRulesApplier.blockPort53(new AppIdentity(packageName, signature), contextInfo, false)) {
                                                                Log.d(TAG, "unblocking port53 for package = " + packageName);
                                                            }
                                                        } else {
                                                            i2 = i8;
                                                            Log.e(TAG, "removeDomainFilterRules() - Failed to remove rule from the database");
                                                            firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove/update rule from the database.");
                                                        }
                                                    }
                                                }
                                                arrayList = arrayList2;
                                            } else {
                                                i = i7;
                                                arrayList = arrayList3;
                                                i2 = i6;
                                                i3 = size;
                                                if (!removeDomainListInDatabase(contextInfo, domainFilterRule, arrayList, listFromDb, listFromDb2)) {
                                                    Log.e(TAG, "removeDomainFilterRules() - Failed to remove rule from the database");
                                                    firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove/update rule from the database.");
                                                } else {
                                                    Integer asInteger2 = i4 != 0 ? contentValues3.getAsInteger("networkId") : null;
                                                    if (asInteger2 == null) {
                                                        c = 65535;
                                                        asInteger2 = -1;
                                                    } else {
                                                        c = 65535;
                                                    }
                                                    updateDaemonCache(contextInfo, domainFilterRule, Operation.REMOVE.ordinal(), String.valueOf(asInteger2.intValue()));
                                                    firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule was successfuly removed/updated.");
                                                }
                                            }
                                            c = 65535;
                                        }
                                    }
                                    i = i7;
                                    arrayList = arrayList3;
                                    i2 = i6;
                                    i3 = size;
                                    c = 65535;
                                }
                                i = i7;
                                i2 = i6;
                                c = c2;
                                i3 = size;
                                arrayList = arrayList3;
                                Log.i(TAG, "removeDomainFilterRules() - Skipping rule - This rule is not in the database");
                                firewallResponseArr3[i] = new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "The rule is not in the database.");
                            }
                            i7 = i + 1;
                            i6 = i2;
                            arrayList3 = arrayList;
                            size = i3;
                            i5 = 0;
                            strArr = null;
                            list2 = list;
                            c2 = c;
                        }
                        ArrayList arrayList4 = arrayList3;
                        int i9 = size;
                        if (isDomainFilterOnIptablesEnabled(contextInfo)) {
                            ArrayList arrayList5 = new ArrayList();
                            int i10 = 0;
                            while (true) {
                                int i11 = i9;
                                if (i10 >= i11) {
                                    break;
                                }
                                if (firewallResponseArr3[i10].getResult().equals(FirewallResponse.Result.SUCCESS)) {
                                    arrayList5.add((DomainFilterRule) list.get(i10));
                                }
                                i10++;
                                i9 = i11;
                            }
                            removeDomainRules(contextInfo, arrayList5, arrayList4);
                        }
                        firewallResponseArr = firewallResponseArr3;
                    }
                }
            }
            synchronized (this.mExceptionListLock) {
                if (!hasAnyRuleInDatabase()) {
                    this.mIsExceptionListApplied = false;
                    this.mDomainFilterNapCommon.setDomainFilterEnabled(false);
                }
            }
            synchronized (this.mKGExceptionListLock) {
                if (!hasAnyRuleInDatabase()) {
                    this.mIsKGExceptionApplied = false;
                }
            }
            maybeClearCaptivePortalHosts();
            return firewallResponseArr;
        }
    }

    public List getDomainFilterRules(ContextInfo contextInfo, List list, int i) {
        if (!this.mDomainFilterBrokenRules.containsKey(Integer.valueOf(i))) {
            List domainFilterRules = getDomainFilterRules(contextInfo, list);
            if (domainFilterRules.isEmpty()) {
                return null;
            }
            this.mDomainFilterBrokenRules.put(Integer.valueOf(i), domainFilterRules);
        }
        List list2 = (List) this.mDomainFilterBrokenRules.get(Integer.valueOf(i));
        if (list2.isEmpty()) {
            this.mDomainFilterBrokenRules.remove(Integer.valueOf(i));
            return null;
        }
        List pageableRule = pageableRule(list2);
        this.mDomainFilterBrokenRules.put(Integer.valueOf(i), list2);
        return pageableRule;
    }

    public final List pageableRule(List list) {
        List list2;
        int i;
        List list3;
        int i2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        while (!list.isEmpty()) {
            DomainFilterRule domainFilterRule = (DomainFilterRule) list.get(i3);
            String dns1 = domainFilterRule.getDns1();
            int length = dns1 != null ? dns1.getBytes(StandardCharsets.UTF_8).length : i3;
            String dns2 = domainFilterRule.getDns2();
            int length2 = dns2 != null ? dns2.getBytes(StandardCharsets.UTF_8).length : i3;
            AppIdentity application = domainFilterRule.getApplication();
            int i5 = length + length2 + 8;
            if (application != null) {
                String signature = application.getSignature();
                String packageName = application.getPackageName();
                i5 = i5 + (signature != null ? signature.getBytes(StandardCharsets.UTF_8).length : i3) + (packageName != null ? packageName.getBytes(StandardCharsets.UTF_8).length : i3);
            }
            int i6 = i4 + i5;
            List denyDomains = domainFilterRule.getDenyDomains();
            List allowDomains = domainFilterRule.getAllowDomains();
            if (denyDomains.isEmpty() && allowDomains.isEmpty()) {
                i = i6;
                list3 = allowDomains;
                list2 = denyDomains;
                arrayList.add(new DomainFilterRule(application, arrayList2, arrayList3, dns1, dns2));
            } else {
                list2 = denyDomains;
                i = i6;
                list3 = allowDomains;
            }
            while (!list2.isEmpty()) {
                List list4 = list2;
                String str = (String) list4.get(0);
                int length3 = i + str.getBytes(StandardCharsets.UTF_8).length;
                if (length3 <= MAX_LIST_SIZE_IN_BYTES) {
                    arrayList2.add(str);
                    list4.remove(0);
                    i = length3;
                    list2 = list4;
                } else {
                    arrayList.add(new DomainFilterRule(application, arrayList2, arrayList3, dns1, dns2));
                    arrayList2.clear();
                    domainFilterRule.setDenyDomains(list4);
                    return arrayList;
                }
            }
            if (list3 != null) {
                while (!list3.isEmpty()) {
                    String str2 = (String) list3.get(0);
                    int length4 = i + str2.getBytes(StandardCharsets.UTF_8).length;
                    if (length4 <= MAX_LIST_SIZE_IN_BYTES) {
                        arrayList3.add(str2);
                        list3.remove(0);
                        i = length4;
                    } else {
                        arrayList.add(new DomainFilterRule(application, arrayList2, arrayList3, dns1, dns2));
                        arrayList3.clear();
                        domainFilterRule.setAllowDomains(list3);
                        return arrayList;
                    }
                }
            }
            if (!arrayList3.isEmpty() || !arrayList2.isEmpty()) {
                arrayList.add(new DomainFilterRule(application, arrayList2, arrayList3, dns1, dns2));
                arrayList2.clear();
                arrayList3.clear();
            }
            if (domainFilterRule.getDenyDomains().isEmpty() && domainFilterRule.getAllowDomains().isEmpty()) {
                i2 = 0;
                list.remove(0);
            } else {
                i2 = 0;
            }
            i3 = i2;
            i4 = i;
        }
        return arrayList;
    }

    public List getDomainFilterRules(ContextInfo contextInfo, List list) {
        Log.i(TAG, "getDomainFilterRules()");
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        for (ContentValues contentValues2 : this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues)) {
            String asString = contentValues2.getAsString("packageName");
            String asString2 = contentValues2.getAsString("signature");
            if (list == null || list.contains(asString)) {
                arrayList.add(new DomainFilterRule(new AppIdentity(asString, asString2), FirewallUtils.getListFromDb(contentValues2, "blacklist", this.mEdmStorageProvider), FirewallUtils.getListFromDb(contentValues2, "whitelist", this.mEdmStorageProvider), contentValues2.getAsString("dns1"), contentValues2.getAsString("dns2")));
            }
        }
        contentValues.clear();
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDaemonCache(ContextInfo contextInfo, DomainFilterRule domainFilterRule, int i, String str) {
        Map map;
        String str2;
        String num;
        List list;
        List allowDomains;
        List list2;
        Log.i(TAG, "updateDaemonCache()");
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (Operation.CLEAR.ordinal() == i) {
            sendToCache(i, Integer.toString(userId), null, null, null, null);
            return;
        }
        if (domainFilterRule == null) {
            return;
        }
        if (Operation.ADD.ordinal() != i && Operation.REMOVE.ordinal() != i) {
            return;
        }
        synchronized (this.mUserIdMapLock) {
            map = (Map) this.mUserIdMap.get(Integer.valueOf(userId));
        }
        String packageName = domainFilterRule.getApplication().getPackageName();
        ArrayList arrayList = null;
        if ("*".equals(packageName)) {
            num = Integer.toString(userId);
        } else {
            if (!map.containsKey(packageName)) {
                str2 = null;
                if (str2 == null) {
                    List denyDomains = domainFilterRule.getDenyDomains();
                    if (denyDomains != null) {
                        if (denyDomains.isEmpty()) {
                            denyDomains = new ArrayList();
                        }
                    } else if (Operation.REMOVE.ordinal() == i) {
                        denyDomains = new ArrayList();
                        denyDomains.add("ALL");
                    } else {
                        list = null;
                        allowDomains = domainFilterRule.getAllowDomains();
                        if (allowDomains == null) {
                            if (allowDomains.isEmpty()) {
                                arrayList = new ArrayList();
                            } else {
                                list2 = allowDomains;
                                sendToCache(i, str2, null, list, list2, str);
                                return;
                            }
                        } else if (Operation.REMOVE.ordinal() == i) {
                            arrayList = new ArrayList();
                            arrayList.add("ALL");
                        }
                        list2 = arrayList;
                        sendToCache(i, str2, null, list, list2, str);
                        return;
                    }
                    list = denyDomains;
                    allowDomains = domainFilterRule.getAllowDomains();
                    if (allowDomains == null) {
                    }
                    list2 = arrayList;
                    sendToCache(i, str2, null, list, list2, str);
                    return;
                }
                return;
            }
            num = Integer.toString(((Integer) map.get(packageName)).intValue());
        }
        str2 = num;
        if (str2 == null) {
        }
    }

    public final void initDaemonCache() {
        String num;
        Log.d(TAG, "initDaemonCache()");
        List<ContentValues> values = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, (ContentValues) null);
        if (values == null || values.isEmpty()) {
            Log.i(TAG, "initDaemonCache() - No rules found in db");
            return;
        }
        for (ContentValues contentValues : values) {
            List listFromDb = FirewallUtils.getListFromDb(contentValues, "blacklist", this.mEdmStorageProvider);
            List listFromDb2 = FirewallUtils.getListFromDb(contentValues, "whitelist", this.mEdmStorageProvider);
            String asString = contentValues.getAsString("dns1");
            if (!listFromDb.isEmpty() || !listFromDb2.isEmpty() || !TextUtils.isEmpty(asString)) {
                Integer asInteger = contentValues.getAsInteger("adminUid");
                int i = -1;
                int intValue = asInteger != null ? asInteger.intValue() : -1;
                int userId = UserHandle.getUserId(intValue);
                String asString2 = contentValues.getAsString("packageName");
                if ("*".equals(asString2)) {
                    num = Integer.toString(userId);
                } else {
                    int applicationUid = getApplicationUid(asString2, userId);
                    if (applicationUid != -1) {
                        String asString3 = contentValues.getAsString("signature");
                        if (TextUtils.isEmpty(asString3) || validatePkgSignature(userId, asString2, asString3)) {
                            num = Integer.toString(applicationUid);
                        } else {
                            Log.i(TAG, "initDaemonCache() - app signature mismatch");
                        }
                    } else {
                        num = null;
                    }
                }
                if (num != null) {
                    if (asString != null) {
                        int i2 = setupNetworkDns(asString, contentValues.getAsString("dns2"));
                        if (i2 != -1) {
                            contentValues.put("networkId", Integer.valueOf(i2));
                            ContentValues contentValues2 = new ContentValues();
                            contentValues2.put("adminUid", Integer.valueOf(intValue));
                            contentValues2.put("packageName", asString2);
                            this.mEdmStorageProvider.put("DomainFilterTable", contentValues, contentValues2);
                        }
                        i = i2;
                    }
                    initDomainFilterReportListener();
                    sendToCache(Operation.ADD.ordinal(), num, null, listFromDb, listFromDb2, String.valueOf(i));
                }
            }
        }
        this.mDomainFilterNapCommon.setDomainFilterEnabled(true);
        if (isDomainFilterOnIptablesEnabledAtAll()) {
            this.mDomainFilterNapCommon.setDomainFilterOnIptablesEnabled(true);
        }
    }

    public final void initReportCache() {
        Log.i(TAG, "initReportCache()");
        this.mDomainReportCache = new HashSet();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", (Integer) 1);
        List values = this.mEdmStorageProvider.getValues("DomainFilterReportStatus", (String[]) null, contentValues);
        if (values == null || values.isEmpty()) {
            return;
        }
        Iterator it = values.iterator();
        while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("userId");
            this.mDomainReportCache.add(Integer.valueOf(asInteger != null ? asInteger.intValue() : -1));
        }
    }

    public final void sendToCache(int i, String str, String str2, List list, List list2, String str3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
        }
        if (Operation.CLEAR.ordinal() == i || Operation.REPLACE.ordinal() == i || Operation.EXCEPTION.ordinal() == i || Operation.CAP_PORTAL.ordinal() == i) {
            executeDomainFilterCommands(i, arrayList);
            return;
        }
        int length = str.getBytes(StandardCharsets.UTF_8).length;
        if (!TextUtils.isEmpty(str2)) {
            length += str2.getBytes(StandardCharsets.UTF_8).length;
        }
        Operation operation = Operation.ADD;
        if (operation.ordinal() == i || Operation.REMOVE.ordinal() == i) {
            if (list != null && !list.isEmpty()) {
                concatenateListAndExecuteCommand(i, arrayList, length, "BLACKLIST", list);
            }
            if (list2 != null && !list2.isEmpty()) {
                concatenateListAndExecuteCommand(i, arrayList, length, "WHITELIST", list2);
            }
            if (TextUtils.isEmpty(str3) || str3.equals("-1")) {
                return;
            }
            arrayList.add(str3);
            arrayList.add(operation.ordinal() == i ? "ADD" : "REMOVE");
            executeDomainFilterCommands(Operation.NET_ID.ordinal(), arrayList);
        }
    }

    public final IOemNetd getOemNetdService() {
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            connectNativeNetdService();
        }
        if (this.mOemNetdService == null && this.mNetdService != null) {
            this.mOemNetdService = this.mInjector.getOemNetd();
        }
        return this.mOemNetdService;
    }

    public final void flushNetworkDnsCache() {
        if (getConnectivityService() == null) {
            Log.e(TAG, "flushNetworkDnsCache() - failed to retrieve ConnectivityService instance");
            return;
        }
        try {
            final Network activeNetwork = mConnectivityService.getActiveNetwork();
            if (activeNetwork != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.firewall.DomainFilter$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        DomainFilter.this.lambda$flushNetworkDnsCache$0(activeNetwork);
                    }
                });
            }
        } catch (Exception unused) {
            Log.e(TAG, "flushNetworkDnsCache() - Failed to getActiveNetwork");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flushNetworkDnsCache$0(Network network) {
        this.mDnsResolverAdapter.flushNetworkCache(network.getNetId());
    }

    public final void concatenateListAndExecuteCommand(int i, ArrayList arrayList, int i2, String str, List list) {
        int length = str.getBytes(StandardCharsets.UTF_8).length;
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i3 = i2 + length;
        if (list == null) {
            arrayList2.addAll(arrayList);
            arrayList2.add(str);
            arrayList2.add(null);
            executeDomainFilterCommands(i, arrayList2);
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
                    sb.append(KnoxVpnFirewallHelper.DELIMITER + ((String) list.get(i5)));
                }
                i5++;
            } else {
                Log.d(TAG, "concatenateListAndExecuteCommand() - " + sb.toString());
                arrayList2.addAll(arrayList);
                arrayList2.add(str);
                arrayList2.add(sb.toString());
                sb.setLength(0);
                executeDomainFilterCommands(i, arrayList2);
                arrayList2.clear();
                i4 = i3;
            }
        }
        if (sb.length() != 0) {
            Log.d(TAG, "concatenateListAndExecuteCommand() - " + sb.toString());
            arrayList2.addAll(arrayList);
            arrayList2.add(str);
            arrayList2.add(sb.toString());
            sb.setLength(0);
            executeDomainFilterCommands(i, arrayList2);
            arrayList2.clear();
        }
    }

    public final synchronized void executeDomainFilterCommands(int i, ArrayList arrayList) {
        IOemNetd oemNetdService;
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        for (int i2 = 1; i2 <= 3; i2++) {
            try {
                oemNetdService = getOemNetdService();
                this.mOemNetdService = oemNetdService;
            } catch (RemoteException | ServiceSpecificException unused) {
                Log.e(TAG, "executeDomainFilterCommands() - attempt " + i2 + " in 3");
                delayTransaction(500);
            }
            if (oemNetdService != null) {
                oemNetdService.updateDomainFilterCache(i, strArr);
                delayTransaction(20);
                return;
            }
            continue;
        }
        Log.e(TAG, "executeDomainFilterCommands() - Transaction failed. ");
    }

    public final void delayTransaction(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            Log.e(TAG, "delayTransaction() - failed to delay transaction ", e);
        }
    }

    public final int checkAdminInDatabase(int i) {
        int userId = UserHandle.getUserId(i);
        List stringListAsUser = this.mEdmStorageProvider.getStringListAsUser("DomainFilterTable", "adminUid", userId);
        if (stringListAsUser.size() == 0) {
            stringListAsUser = this.mEdmStorageProvider.getStringListAsUser("DomainFilterOnIptablesApiStatus", "adminUid", userId);
            if (stringListAsUser.size() == 0) {
                return 0;
            }
        }
        return Integer.parseInt((String) stringListAsUser.get(0)) == i ? 1 : -1;
    }

    public void adminRemoved(int i) {
        Log.i(TAG, "adminRemoved() - adminUid: " + i);
        int userId = UserHandle.getUserId(i);
        Integer valueOf = Integer.valueOf(userId);
        if (!isDomainFilterReportEnabledAsUser(userId) && this.mDomainReportCache.contains(valueOf)) {
            this.mDomainReportCache.remove(valueOf);
            clearReportForUser(userId);
        }
        maybeClearCaptivePortalHosts();
    }

    public void onPreAdminRemoval(int i) {
        Log.i(TAG, "onPreAdminRemoval(adminUid = " + String.valueOf(i) + ")");
        int userId = UserHandle.getUserId(i);
        if (checkAdminInDatabase(i) == 1) {
            sendToCache(Operation.CLEAR.ordinal(), Integer.toString(userId), null, null, null, null);
            releaseNetworks(i);
            if (isDomainFilterOnIptablesEnabled(new ContextInfo(i))) {
                this.mFirewallRulesApplier.flushDomainChains(Integer.valueOf(userId), false);
            }
        }
    }

    public final void prepareDomainUrls(DomainFilterRule domainFilterRule) {
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

    public synchronized FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z) {
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (checkAdminInDatabase(i) == -1) {
            Log.i(TAG, "enableDomainFilterOnIptables() - Another admin already have domain filter rules in the database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR, "This administrator can't execute this operation because he is not the owner.");
        }
        if (z) {
            if (!this.mFirewallRulesApplier.isDomainFilterOnIptablesSupported()) {
                Log.i(TAG, "DomainFilterOnIptablesApi not supported.");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, "Failed to enable domain filter on iptables api.");
            }
            boolean isDomainFilterOnIptablesEnabledAtAll = isDomainFilterOnIptablesEnabledAtAll();
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userId", Integer.valueOf(userId));
            contentValues.put("status", Boolean.valueOf(z));
            if (this.mEdmStorageProvider.insert("DomainFilterOnIptablesApiStatus", contentValues) == -1) {
                Log.e(TAG, "enableDomainFilterOnIptables() - Failed to change DomainFilterOnIptables Api status on database");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to change DomainFilterOnIptables status on database.");
            }
            if (!isDomainFilterOnIptablesEnabledAtAll) {
                flushNetworkDnsCache();
                this.mDomainFilterNapCommon.setDomainFilterOnIptablesEnabled(true);
            }
            FirewallResponse addDomainRules = addDomainRules(contextInfo, getDomainFilterRules(contextInfo, null));
            Log.d(TAG, "enableDomainFilterOnIptables() - DomainFilterOnIptables Api successfully enabled");
            return addDomainRules;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        contentValues2.put("userId", Integer.valueOf(userId));
        if (this.mEdmStorageProvider.delete("DomainFilterOnIptablesApiStatus", contentValues2) == 0) {
            Log.e(TAG, "enableDomainFilterOnIptables() - Failed to change DomainFilterOnIptables Api status on database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to change DomainFilterOnIptables status on database.");
        }
        Log.d(TAG, "enableDomainFilterOnIptables() - DomainFilterOnIptables successfully disabled");
        if (!isDomainFilterOnIptablesEnabledAtAll()) {
            this.mDomainFilterNapCommon.setDomainFilterOnIptablesEnabled(false);
        }
        if (!this.mFirewallRulesApplier.flushDomainChains(Integer.valueOf(userId), false)) {
            Log.e(TAG, "flushDomainChains() - Failed to flush domain chains");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, "Failed to disable domain filter on iptables api.");
        }
        Log.i(TAG, "flushDomainChains() - domain rules successfully removed");
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The domain filter on iptables api was successfully disabled.");
    }

    public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) {
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", Integer.valueOf(userId));
        contentValues.put("adminUid", Integer.valueOf(i));
        List booleanList = this.mEdmStorageProvider.getBooleanList("DomainFilterOnIptablesApiStatus", "status", contentValues);
        if (booleanList == null || booleanList.isEmpty()) {
            return false;
        }
        return booleanList.contains(Boolean.TRUE);
    }

    public final boolean isDomainFilterOnIptablesEnabledAtAll() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", (Integer) 1);
        return this.mEdmStorageProvider.getCount("DomainFilterOnIptablesApiStatus", contentValues) > 0;
    }

    public synchronized FirewallResponse addDomainRules(ContextInfo contextInfo, List list) {
        Log.i(TAG, "addDomainRules()");
        if (list != null && !list.isEmpty()) {
            return this.mFirewallRulesApplier.addDomainRules(list, contextInfo);
        }
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The domain filter on iptables api was successfully enabled.");
    }

    public synchronized FirewallResponse removeDomainRules(ContextInfo contextInfo, List list, List list2) {
        Log.i(TAG, "removeDomainRules()");
        if (list != null && !list.isEmpty()) {
            return this.mFirewallRulesApplier.removeDomainRules(list, contextInfo, list2);
        }
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The domain filter on iptables api was successfully disabled.");
    }

    public FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) {
        Log.i(TAG, "enableDomainFilterReport()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", Boolean.valueOf(z));
        contentValues.put("userId", Integer.valueOf(userId));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        if (!this.mEdmStorageProvider.put("DomainFilterReportStatus", contentValues, contentValues2)) {
            Log.e(TAG, "enableDomainFilterReport() - Failed to change Domain Filter Report status on database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to change Domain Filter report status on database.");
        }
        Integer valueOf = Integer.valueOf(userId);
        if (z && !this.mDomainReportCache.contains(valueOf)) {
            this.mDomainReportCache.add(valueOf);
        } else if (!z && !isDomainFilterReportEnabledAsUser(userId) && this.mDomainReportCache.contains(valueOf)) {
            this.mDomainReportCache.remove(valueOf);
            clearReportForUser(userId);
        }
        if (z && registerDomainFilterListener(z)) {
            Log.d(TAG, "enableDomainFilterReport() - Domain Filter Report successfully enabled");
        } else if (!z && registerDomainFilterListener(z)) {
            Log.d(TAG, "enableDomainFilterReport() - Domain Filter Report successfully disabled");
        }
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, z ? "Domain Report successfully enabled." : "Domain Report successfully disabled.");
    }

    public final void initDomainFilterReportListener() {
        boolean isDomainFilterReportEnabledAsUser;
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List users = userManager.getUsers();
                if (users != null) {
                    Iterator it = users.iterator();
                    while (it.hasNext() && (!(isDomainFilterReportEnabledAsUser = isDomainFilterReportEnabledAsUser(((UserInfo) it.next()).id)) || !registerDomainFilterListener(isDomainFilterReportEnabledAsUser))) {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean registerDomainFilterListener(boolean z) {
        if (z) {
            if (this.mDomainFilterEventListener != null) {
                return false;
            }
            try {
                IOemNetd oemNetdService = getOemNetdService();
                this.mOemNetdService = oemNetdService;
                if (oemNetdService == null) {
                    return false;
                }
                DomainFilterEventListener domainFilterEventListener = new DomainFilterEventListener();
                this.mDomainFilterEventListener = domainFilterEventListener;
                this.mOemNetdService.registerDomainFilterEventListener(domainFilterEventListener);
                Log.d(TAG, "DomainFilterEventListener registered successfully");
                return true;
            } catch (RemoteException | ServiceSpecificException e) {
                Log.e(TAG, "Failed to register domainFilterEventListener " + e);
                return false;
            }
        }
        if (this.mDomainFilterEventListener == null) {
            return false;
        }
        try {
            IOemNetd oemNetdService2 = getOemNetdService();
            this.mOemNetdService = oemNetdService2;
            if (oemNetdService2 == null) {
                return false;
            }
            oemNetdService2.unregisterDomainFilterEventListener();
            this.mDomainFilterEventListener = null;
            Log.d(TAG, "DomainFilterEventListener unregistered successfully");
            return true;
        } catch (RemoteException | ServiceSpecificException e2) {
            Log.e(TAG, "Failed to unregister domainFilterEventListener " + e2);
            return false;
        }
    }

    public boolean isDomainFilterReportEnabled(ContextInfo contextInfo) {
        return isDomainFilterReportEnabledAsUser(UserHandle.getUserId(contextInfo.mCallerUid));
    }

    public final boolean isDomainFilterReportEnabledAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", Integer.valueOf(i));
        List booleanList = this.mEdmStorageProvider.getBooleanList("DomainFilterReportStatus", "status", contentValues);
        if (booleanList == null || booleanList.isEmpty()) {
            return false;
        }
        return booleanList.contains(Boolean.TRUE);
    }

    public List getDomainFilterReport(ContextInfo contextInfo, List list) {
        Log.i(TAG, "getDomainFilterReport()");
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        ArrayList arrayList = new ArrayList();
        if (!this.mDomainReportCache.isEmpty() && this.mDomainReportCache.contains(Integer.valueOf(userId))) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", Integer.valueOf(userId));
            for (ContentValues contentValues2 : this.mEdmStorageProvider.getValues("DomainFilterReportTable", (String[]) null, contentValues)) {
                String asString = contentValues2.getAsString("packageName");
                if (list == null || list.contains(asString)) {
                    Long asLong = contentValues2.getAsLong("time");
                    arrayList.add(new DomainFilterReport(asString, asLong != null ? asLong.longValue() : 0L, contentValues2.getAsString("url")));
                }
            }
        }
        return arrayList;
    }

    public final void saveReportInDatabase(String str, String str2, String str3, String str4) {
        int i;
        try {
            i = UserHandle.getUserId(Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            Log.e(TAG, "saveReportInDatabase() - Error parsing userId received from daemon.", e);
            i = -1;
        }
        if (i == -1 || !this.mDomainReportCache.contains(Integer.valueOf(i))) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", str);
        contentValues.put("userId", Integer.valueOf(i));
        contentValues.put("packageName", str4);
        contentValues.put("url", str3);
        if (this.mEdmStorageProvider.insert("DomainFilterReportTable", contentValues) == -1) {
            Log.e(TAG, "saveReportInDatabase() - Failed to save domain report in database");
        }
    }

    public final void clearReportForUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", Integer.valueOf(i));
        this.mEdmStorageProvider.delete("DomainFilterReportTable", contentValues);
    }

    public class DomainFilterEventListener extends IDomainFilterEventListener.Stub {
        public DomainFilterEventListener() {
        }

        @Override // com.android.internal.net.IDomainFilterEventListener
        public void onDomainFilterReportEvent(int i, int i2, long j, String str) {
            String packageFromRunningProcesses = DomainFilter.this.getPackageFromRunningProcesses(i2, i);
            if (packageFromRunningProcesses == null) {
                Log.d(DomainFilter.TAG, "failed to find application which was blocked");
                return;
            }
            String valueOf = String.valueOf(j);
            String valueOf2 = String.valueOf(i);
            BlockReport blockReport = new BlockReport(valueOf, valueOf2, str);
            boolean z = false;
            for (int i3 = 0; i3 < 5; i3++) {
                if (DomainFilter.this.mReportCache[i3] != null && DomainFilter.this.mReportCache[i3].isEqual(blockReport)) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            DomainFilter.this.mReportCache[DomainFilter.this.mReportCacheIndex] = blockReport;
            DomainFilter.this.mReportCacheIndex++;
            if (DomainFilter.this.mReportCacheIndex == 5) {
                DomainFilter.this.mReportCacheIndex = 0;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = ((ActivityManager) DomainFilter.this.mContext.getSystemService("activity")).getRunningTasks(1).get(0);
            Intent intent = new Intent("com.samsung.android.knox.intent.action.BLOCKED_DOMAIN");
            intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_PACKAGENAME", packageFromRunningProcesses);
            intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_TIMESTAMP", valueOf);
            intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_UID", valueOf2);
            intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_URL", str);
            if (packageFromRunningProcesses.equals(runningTaskInfo.topActivity.getPackageName())) {
                intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_ISFOREGROUND", true);
            } else {
                intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_ISFOREGROUND", false);
            }
            DomainFilter.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_FIREWALL");
            DomainFilter.this.saveReportInDatabase(valueOf, valueOf2, str, packageFromRunningProcesses);
        }
    }

    public synchronized FirewallResponse removeDnsForApplication(DomainFilterRule domainFilterRule, int i, ContentValues contentValues) {
        Log.d(TAG, "removeDnsForApplication() - START");
        String asString = contentValues.getAsString("dns1");
        String asString2 = contentValues.getAsString("dns2");
        String dns1 = domainFilterRule.getDns1();
        String dns2 = domainFilterRule.getDns2();
        if (asString != null && dns1.equals(asString) && ((dns2 != null || asString2 == null) && ((dns2 == null || asString2 != null) && (dns2 == null || dns2.equals(asString2))))) {
            Integer asInteger = contentValues.getAsInteger("networkId");
            if (asInteger != null && asInteger.intValue() != -1) {
                this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, asInteger));
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
            if (this.mEdmStorageProvider.put("DomainFilterTable", contentValues2, contentValues3)) {
                return null;
            }
            Log.e(TAG, "removeDnsForApplication() -  Database error!");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove DNS(s) from database.");
        }
        Log.e(TAG, "removeDnsForApplication() - Invalid parameters!");
        return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "DNS(s) provided doesn't match DNS(s) in database.");
    }

    public final int setupNetworkDns(String str, String str2) {
        boolean z;
        Exception e;
        INetd iNetd;
        int matchNetworkDns = matchNetworkDns(str, str2);
        if (matchNetworkDns != -1) {
            return matchNetworkDns;
        }
        if (getNetworkManagementService() == null) {
            Log.e(TAG, "setupNetworkDns() - failed to retrieve NetworkManagementService instance");
            return -1;
        }
        if (getConnectivityService() == null) {
            Log.e(TAG, "setupNetworkDns() - failed to retrieve ConnectivityService instance");
            return -1;
        }
        boolean z2 = true;
        do {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    try {
                        matchNetworkDns = this.mNetIdManager.reserveNetId();
                        connectNativeNetdService();
                        iNetd = this.mNetdService;
                    } catch (Exception e2) {
                        z = false;
                        e = e2;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (IllegalStateException e3) {
                Log.e(TAG, "setupNetworkDns() - Failed creating new network.", e3);
            }
            if (iNetd == null) {
                return -1;
            }
            try {
                iNetd.networkCreateVpn(matchNetworkDns, false);
                this.mDnsResolverAdapter.createNetworkCache(matchNetworkDns);
                try {
                    LinkProperties linkProperties = new LinkProperties();
                    linkProperties.addDnsServer(InetAddress.getByName(str));
                    if (!TextUtils.isEmpty(str2)) {
                        linkProperties.addDnsServer(InetAddress.getByName(str2));
                    }
                    EnforceDnsManager enforceDnsManager = new EnforceDnsManager(this.mContext, this.mDnsResolverAdapter);
                    this.mDnsManager = enforceDnsManager;
                    enforceDnsManager.noteDnsServersForNetwork(matchNetworkDns, linkProperties);
                    this.mDnsManager.updateDnsUidForNetwork(matchNetworkDns, true);
                    this.mDnsManager.updateTransportsForNetwork(matchNetworkDns, new int[]{0, 1, 3});
                    this.mDnsManager.flushVmDnsCache();
                    synchronized (this.mNetworkIdListLock) {
                        this.mNetworkIdList.add(new NetworkIdInfo(matchNetworkDns, str, str2));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z2 = false;
                } catch (Exception e4) {
                    e = e4;
                    z = true;
                    if (z) {
                        Log.e(TAG, "setupNetworkDns() - Failed to set dns for network " + matchNetworkDns, e);
                        try {
                            INetd iNetd2 = this.mNetdService;
                            if (iNetd2 != null) {
                                iNetd2.networkDestroy(matchNetworkDns);
                            }
                        } catch (Exception e5) {
                            Log.e(TAG, "setupNetworkDns() - Failed to remove network", e5);
                        }
                    } else {
                        Log.e(TAG, "setupNetworkDns() - Failed to create new network with id provided.", e);
                    }
                    this.mNetIdManager.releaseNetId(matchNetworkDns);
                }
            } catch (RemoteException | ServiceSpecificException e6) {
                Log.e(TAG, "Error creating private network: " + e6.getMessage());
                this.mNetIdManager.releaseNetId(matchNetworkDns);
                return -1;
            }
        } while (z2);
        return matchNetworkDns;
    }

    public final int matchNetworkDns(String str, String str2) {
        if (this.mNetworkIdList.isEmpty()) {
            return -1;
        }
        synchronized (this.mNetworkIdListLock) {
            for (NetworkIdInfo networkIdInfo : this.mNetworkIdList) {
                if (networkIdInfo.getDns1().equals(str) && (str2 == null || networkIdInfo.hasDns2())) {
                    if (str2 != null || !networkIdInfo.hasDns2()) {
                        if (str2 == null || networkIdInfo.getDns2().equals(str2)) {
                            networkIdInfo.increaseCounter();
                            return networkIdInfo.getNetId();
                        }
                    }
                }
            }
            return -1;
        }
    }

    public final void releaseNetworkId(int i) {
        KnoxNetIdManager knoxNetIdManager;
        if (i == -1 || this.mNetworkIdList.isEmpty()) {
            Log.i(TAG, "releaseNetworkId() - No network to release");
            return;
        }
        int networkIdInfoIndex = getNetworkIdInfoIndex(i);
        if (networkIdInfoIndex == -1) {
            Log.i(TAG, "releaseNetworkId() - No network to release");
            return;
        }
        if (getNetworkManagementService() == null) {
            Log.e(TAG, "releaseNetworkId() - failed to retrieve NetworkManagementService instance");
            return;
        }
        if (getConnectivityService() == null) {
            Log.e(TAG, "releaseNetworkId() - failed to retrieve ConnectivityService instance");
            return;
        }
        synchronized (this.mNetworkIdListLock) {
            if (((NetworkIdInfo) this.mNetworkIdList.get(networkIdInfoIndex)).decreaseCounter() == 0) {
                this.mNetworkIdList.remove(networkIdInfoIndex);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        INetd iNetd = this.mNetdService;
                        if (iNetd != null) {
                            iNetd.networkDestroy(i);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        knoxNetIdManager = this.mNetIdManager;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        this.mNetIdManager.releaseNetId(i);
                        throw th;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "releaseNetworkId() - failed to remove network", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    knoxNetIdManager = this.mNetIdManager;
                }
                knoxNetIdManager.releaseNetId(i);
            }
        }
    }

    public final void releaseNetworks(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        List values = this.mEdmStorageProvider.getValues("DomainFilterTable", new String[]{"networkId"}, contentValues);
        if (values == null || values.isEmpty()) {
            Log.i(TAG, "releaseNetworks() - No rules in database");
            return;
        }
        Iterator it = values.iterator();
        while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("networkId");
            int intValue = asInteger != null ? asInteger.intValue() : -1;
            if (intValue != -1) {
                this.mNetworkHandler.sendMessage(Message.obtain(this.mNetworkHandler, 0, Integer.valueOf(intValue)));
            }
        }
    }

    public class NetworkIdInfo {
        public final String mDns1;
        public final String mDns2;
        public final int mNetId;
        public int mUsageCounter = 1;

        public NetworkIdInfo(int i, String str, String str2) {
            this.mNetId = i;
            this.mDns1 = str;
            this.mDns2 = str2;
        }

        public synchronized int increaseCounter() {
            int i;
            i = this.mUsageCounter + 1;
            this.mUsageCounter = i;
            return i;
        }

        public synchronized int decreaseCounter() {
            int i;
            i = this.mUsageCounter - 1;
            this.mUsageCounter = i;
            return i;
        }

        public String getDns1() {
            return this.mDns1;
        }

        public String getDns2() {
            return this.mDns2;
        }

        public boolean hasDns2() {
            return this.mDns2 != null;
        }

        public int getNetId() {
            return this.mNetId;
        }

        public String toString() {
            return "{Net Id= " + this.mNetId + " , DNS(1)=" + this.mDns1 + " , DNS(2)=" + this.mDns2 + " , counter=" + this.mUsageCounter + "}";
        }
    }

    public final int getNetworkIdInfoIndex(int i) {
        if (this.mNetworkIdList.isEmpty()) {
            return -1;
        }
        synchronized (this.mNetworkIdListLock) {
            for (int i2 = 0; i2 < this.mNetworkIdList.size(); i2++) {
                if (i == ((NetworkIdInfo) this.mNetworkIdList.get(i2)).getNetId()) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service;
        if (this.mNetworkService == null && (service = ServiceManager.getService("network_management")) != null) {
            this.mNetworkService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkService;
    }

    public final ConnectivityManager getConnectivityService() {
        if (mConnectivityService == null) {
            mConnectivityService = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        return mConnectivityService;
    }

    public final boolean validatePkgSignature(int i, String str, String str2) {
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Utils.comparePackageSignature(createContextAsUser, str, str2);
        } catch (Exception e) {
            Log.e(TAG, "validatePkgSignature() - Fail to validate application signature.", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean verifyAppInstalled(Map map, String str, int i) {
        if (map != null && map.get(str) != null) {
            return true;
        }
        int applicationUid = getApplicationUid(str, i);
        if (applicationUid == -1) {
            return false;
        }
        Integer valueOf = Integer.valueOf(i);
        synchronized (this.mUserIdMapLock) {
            if (map == null) {
                HashMap hashMap = new HashMap();
                hashMap.put(str, Integer.valueOf(applicationUid));
                this.mUserIdMap.put(valueOf, hashMap);
            } else {
                ((Map) this.mUserIdMap.get(valueOf)).put(str, Integer.valueOf(applicationUid));
                map.put(str, Integer.valueOf(applicationUid));
            }
        }
        return true;
    }

    public class NetworkManagementHandler extends Handler {
        public NetworkManagementHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            DomainFilter.this.releaseNetworkId(((Integer) message.obj).intValue());
        }
    }

    public final boolean validateDnses(String str, String str2) {
        if (FirewallRuleValidator.validateIpv4Address(str)) {
            return str2 == null || FirewallRuleValidator.validateIpv4Address(str2);
        }
        if (FirewallRuleValidator.validateIpv6Address(str)) {
            return str2 == null || FirewallRuleValidator.validateIpv6Address(str2);
        }
        return false;
    }

    public final boolean validateDomains(DomainFilterRule domainFilterRule) {
        if (domainFilterRule.getDenyDomains() == null && domainFilterRule.getAllowDomains() == null) {
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

    public final FirewallResponse validateApplicationIdentity(AppIdentity appIdentity) {
        if (appIdentity == null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Invalid AppIdentity object.");
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        if ("*".equals(packageName) && signature != null) {
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "There is no signature related to all applications.");
        }
        if (FirewallRuleValidator.validatePackageName(packageName)) {
            return null;
        }
        return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Invalid package name. - " + packageName);
    }

    public class BlockReport {
        public String mTimeStamp;
        public String mUid;
        public String mUrl;

        public BlockReport(String str, String str2, String str3) {
            this.mTimeStamp = str;
            this.mUid = str2;
            this.mUrl = str3;
        }

        public String getTimeStamp() {
            return this.mTimeStamp;
        }

        public String getUid() {
            return this.mUid;
        }

        public String getUrl() {
            return this.mUrl;
        }

        public boolean isEqual(BlockReport blockReport) {
            if (blockReport == null) {
                return false;
            }
            if ((this.mTimeStamp == null && blockReport.getTimeStamp() != null) || (this.mTimeStamp != null && blockReport.getTimeStamp() == null)) {
                return false;
            }
            String str = this.mTimeStamp;
            if (str != null && !str.equals(blockReport.getTimeStamp())) {
                return false;
            }
            if ((this.mUid == null && blockReport.getUid() != null) || (this.mUid != null && blockReport.getUid() == null)) {
                return false;
            }
            String str2 = this.mUid;
            if (str2 != null && !str2.equals(blockReport.getUid())) {
                return false;
            }
            if ((this.mUrl == null && blockReport.getUrl() != null) || (this.mUrl != null && blockReport.getUrl() == null)) {
                return false;
            }
            String str3 = this.mUrl;
            return str3 == null || str3.equals(blockReport.getUrl());
        }
    }

    public final String getPackageFromRunningProcesses(int i, int i2) {
        if (i < 0) {
            Log.i(TAG, "getPackageFromRunningProcesses() - Invalid PID provided: " + i);
            return null;
        }
        if (i2 < 0) {
            Log.i(TAG, "getPackageFromRunningProcesses() - Invalid UID provided: " + i2);
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager != null ? activityManager.getRunningAppProcesses() : null;
            if (runningAppProcesses != null) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningAppProcessInfo next = it.next();
                    if (i == next.pid) {
                        String[] strArr = next.pkgList;
                        if (strArr != null) {
                            if (strArr.length == 1) {
                                return strArr[0];
                            }
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, UserHandle.getUserId(i2)), "#SelectClause#");
                            List values = this.mEdmStorageProvider.getValues("DomainFilterTable", new String[]{"packageName"}, contentValues);
                            if (values != null && !values.isEmpty()) {
                                List asList = Arrays.asList(next.pkgList);
                                Iterator it2 = values.iterator();
                                while (it2.hasNext()) {
                                    String asString = ((ContentValues) it2.next()).getAsString("packageName");
                                    if (asList.contains(asString)) {
                                        return asString;
                                    }
                                }
                            }
                            return next.pkgList[0];
                        }
                    }
                }
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void processPackageExceptionListDelayed() {
        Log.i(TAG, "processPackageExceptionListDelayed()");
        synchronized (this.mExceptionListLock) {
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
                            processPackageExceptionList(this.mContext.getPackageManager().getInstalledPackagesAsUser(64, ((UserInfo) it.next()).id));
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public final synchronized void processPackageExceptionList(List list) {
        Iterator it = FirewallUtils.getUidsToExemptFromPackages(list, this.mContext, false).iterator();
        while (it.hasNext()) {
            sendToCache(Operation.EXCEPTION.ordinal(), Integer.toString(((Integer) it.next()).intValue()), null, null, null, null);
        }
    }

    public final void processKGPackageExceptionDelayed() {
        if (FirewallUtils.isKGExemptRuleRequired()) {
            synchronized (this.mKGExceptionListLock) {
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
                                processKGPackageException(this.mContext.getPackageManager().getInstalledPackagesAsUser(64, ((UserInfo) it.next()).id));
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001d, code lost:
    
        sendToCache(com.android.server.enterprise.firewall.DomainFilter.Operation.EXCEPTION.ordinal(), java.lang.Integer.toString(r0.applicationInfo.uid), null, null, null, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void processKGPackageException(List list) {
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (KnoxCustomManagerService.KG_PKG_NAME.equals(packageInfo.applicationInfo.packageName)) {
                break;
            }
        }
    }

    public final boolean hasAnyRuleInDatabase() {
        List values = this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, (ContentValues) null);
        if (values == null || values.isEmpty()) {
            Log.i(TAG, "hasAnyRuleInDatabase(): false");
            return false;
        }
        Log.i(TAG, "hasAnyRuleInDatabase(): true");
        return true;
    }

    public final void addHostToCaptivePortalWhitelist(String str) {
        sendToCache(Operation.CAP_PORTAL.ordinal(), str, null, null, null, null);
    }

    public final String extractHost(String str) {
        URL url;
        if (str == null) {
            return null;
        }
        try {
            url = new URL(str);
        } catch (MalformedURLException unused) {
            Log.e(TAG, "extractHost() - Invalid url " + str);
            url = null;
        }
        if (url != null) {
            return url.getHost();
        }
        return null;
    }

    public final boolean isIpAddress(String str) {
        return FirewallRuleValidator.validateIpv4Address(str) || FirewallRuleValidator.validateIpv6Address(str);
    }

    public final void maybeClearCaptivePortalHosts() {
        if (hasAnyRuleInDatabase()) {
            return;
        }
        Operation operation = Operation.CAP_PORTAL;
        sendToCache(operation.ordinal(), "", null, null, null, null);
        sendToCache(operation.ordinal(), this.mServer, null, null, null, null);
    }

    public String getDefaulCaptivePortalURL() {
        if (!hasAnyRuleInDatabase()) {
            return null;
        }
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "captive_portal_server");
        if (string == null) {
            string = "connectivitycheck.gstatic.com";
        }
        return "http://" + string + "/generate_204";
    }

    public final void connectNativeNetdService() {
        boolean z;
        INetd netd;
        try {
            netd = this.mInjector.getNetd();
            this.mNetdService = netd;
        } catch (RemoteException unused) {
            z = false;
        }
        if (netd == null) {
            Log.e(TAG, "connectNativeNetdService() - netd is null.");
            return;
        }
        z = netd.isAlive();
        if (z) {
            return;
        }
        Log.e(TAG, "connectNativeNetdService() - Can't connect to NativeNetdService netd");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDomainFilterRulesProperties(DomainFilterRule domainFilterRule, JSONArray jSONArray) {
        int i;
        int i2;
        if (domainFilterRule.getApplication() == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pN", domainFilterRule.getApplication().getPackageName());
            if (domainFilterRule.getDns1() == null && domainFilterRule.getDns2() == null) {
                i = 1;
                jSONObject.put("cDNS", i);
                if (!ArrayUtils.isEmpty(domainFilterRule.getAllowDomains()) || ArrayUtils.isEmpty(domainFilterRule.getDenyDomains())) {
                    i2 = ArrayUtils.isEmpty(domainFilterRule.getAllowDomains()) ? 1 : 0;
                } else {
                    i2 = 2;
                }
                jSONObject.put("rlTp", i2);
                jSONArray.put(jSONObject);
            }
            i = 0;
            jSONObject.put("cDNS", i);
            if (ArrayUtils.isEmpty(domainFilterRule.getAllowDomains())) {
            }
            i2 = ArrayUtils.isEmpty(domainFilterRule.getAllowDomains()) ? 1 : 0;
            jSONObject.put("rlTp", i2);
            jSONArray.put(jSONObject);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to put rule in json: " + e.getMessage());
        }
    }
}
