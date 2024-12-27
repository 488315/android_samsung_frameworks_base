package com.android.server.enterprise.proxy;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.IpConfiguration;
import android.net.Network;
import android.net.ProxyInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.net.IProxyCallback;
import com.android.net.IProxyPortListener;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.utils.NetworkUtils;

import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class LocalProxyManager {
    public static volatile ProxyProperties sDefaultProxy;
    public static volatile ProxyProperties sGlobalProxy;
    public static volatile LocalProxyManager sInstance;
    public static volatile ProxyInfo sLocalProxyInfo;
    public IProxyCallback mCallbackService;
    public String mConnectedWifiSsid;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public IProxyCredentialsCallback mLastCredentialsCallback;
    public Bundle mLastCredentialsResponse;
    public AnonymousClass4 mNetworkCallback;
    public NotificationManager mNotificationManager;
    public ServiceConnection mProxyConnection;
    public WifiManager mWifiManager;
    public static final Object sLocalProxyManagerLock = new Object();
    public static final Object mProxyLock = new Object();
    public static volatile Map sWifiProxyInfoMapCache = new ArrayMap();
    public static volatile Map sWifiBackCompatCredentialsMapCache = new ArrayMap();
    public static volatile boolean sIsLocalProxyServerRunning = false;
    public static volatile boolean sIsNetworkCallbackRunning = false;
    public static final Object mNetworkCallbackLock = new Object();
    public static volatile List sPendinOperationsList = new ArrayList();

    /* renamed from: com.android.server.enterprise.proxy.LocalProxyManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ LocalProxyManager this$0;

        public /* synthetic */ AnonymousClass1(LocalProxyManager localProxyManager, int i) {
            this.$r8$classId = i;
            this.this$0 = localProxyManager;
        }

        private final void
                onServiceDisconnected$com$android$server$enterprise$proxy$LocalProxyManager$1(
                        ComponentName componentName) {}

        private final void
                onServiceDisconnected$com$android$server$enterprise$proxy$LocalProxyManager$2(
                        ComponentName componentName) {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mCallbackService = IProxyCallback.Stub.asInterface(iBinder);
                    IProxyCallback iProxyCallback = this.this$0.mCallbackService;
                    if (iProxyCallback != null) {
                        try {
                            iProxyCallback.getProxyPort(
                                    new IProxyPortListener.Stub() { // from class:
                                        // com.android.server.enterprise.proxy.LocalProxyManager.1.1
                                        public final void setProxyPort(int i) {
                                            if (i == -1) {
                                                Log.e(
                                                        "LocalProxyManager",
                                                        "Received invalid port from Local Proxy,"
                                                            + " proxy will not be operational");
                                                return;
                                            }
                                            LocalProxyManager localProxyManager =
                                                    AnonymousClass1.this.this$0;
                                            LocalProxyManager localProxyManager2 =
                                                    LocalProxyManager.sInstance;
                                            localProxyManager.getClass();
                                            Log.d("LocalProxyManager", "Proxy bound at port " + i);
                                            LocalProxyManager.sIsLocalProxyServerRunning = true;
                                            LocalProxyManager localProxyManager3 =
                                                    AnonymousClass1.this.this$0;
                                            synchronized (localProxyManager3) {
                                                Log.d(
                                                        "LocalProxyManager",
                                                        "Set local proxy port " + i);
                                                synchronized (LocalProxyManager.mProxyLock) {
                                                    LocalProxyManager.sLocalProxyInfo =
                                                            ProxyInfo.buildPacProxy(Uri.EMPTY, i);
                                                }
                                                localProxyManager3.updateGlobalProxyValues();
                                                if (LocalProxyManager.isDirectProxy(
                                                        LocalProxyManager.getDefaultProxy())) {
                                                    Log.d(
                                                            "LocalProxyManager",
                                                            "Updating enterprise wifi proxy"
                                                                + " values");
                                                    localProxyManager3.updateProxyInWifiConfig(
                                                            localProxyManager3.mConnectedWifiSsid,
                                                            true,
                                                            LocalProxyManager.sLocalProxyInfo);
                                                }
                                            }
                                        }
                                    });
                            break;
                        } catch (RemoteException e) {
                            Log.e("LocalProxyManager", "Failed to get proxy callback instance");
                            e.printStackTrace();
                            return;
                        }
                    }
                    break;
                default:
                    this.this$0.mCallbackService = IProxyCallback.Stub.asInterface(iBinder);
                    LocalProxyManager localProxyManager = this.this$0;
                    if (localProxyManager.mCallbackService != null
                            && !((ArrayList) LocalProxyManager.sPendinOperationsList).isEmpty()) {
                        Iterator it =
                                ((ArrayList) LocalProxyManager.sPendinOperationsList).iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            Log.d("LocalProxyManager", "Execute pending operation: " + str);
                            str.getClass();
                            if (str.equals("clearCache")) {
                                localProxyManager.clearProxyServerCache();
                                ((ArrayList) LocalProxyManager.sPendinOperationsList).remove(str);
                            } else if (str.equals("setProxyCredentials")) {
                                Bundle bundle = localProxyManager.mLastCredentialsResponse;
                                IProxyCredentialsCallback iProxyCredentialsCallback =
                                        localProxyManager.mLastCredentialsCallback;
                                Log.d(
                                        "LocalProxyManager",
                                        "Set proxy credentials callback to proxy server");
                                Binder.withCleanCallingIdentity(
                                        new LocalProxyManager$$ExternalSyntheticLambda7(
                                                localProxyManager,
                                                iProxyCredentialsCallback,
                                                bundle));
                                ((ArrayList) LocalProxyManager.sPendinOperationsList).remove(str);
                            }
                        }
                        break;
                    }
                    break;
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            int i = this.$r8$classId;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:

       r1 = r5[8];
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getAppUidFromTcpFile(int r10, java.lang.String r11) {
        /*
            java.lang.String r0 = "LocalProxyManager"
            java.lang.String r10 = java.lang.Integer.toHexString(r10)
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.io.IOException -> L58 java.io.FileNotFoundException -> L5a
            r2.<init>(r11)     // Catch: java.io.IOException -> L58 java.io.FileNotFoundException -> L5a
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L52
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L52
            r4 = 0
        L12:
            java.lang.String r5 = r3.readLine()     // Catch: java.lang.Throwable -> L52
            if (r5 == 0) goto L54
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L52
            r6.<init>()     // Catch: java.lang.Throwable -> L52
            r6.append(r5)     // Catch: java.lang.Throwable -> L52
            java.lang.String r5 = "\n"
            r6.append(r5)     // Catch: java.lang.Throwable -> L52
            java.lang.String r5 = r6.toString()     // Catch: java.lang.Throwable -> L52
            r6 = 1
            int r4 = r4 + r6
            if (r4 <= r6) goto L12
            java.lang.String r7 = " +"
            java.lang.String[] r5 = r5.split(r7)     // Catch: java.lang.Throwable -> L52
            r7 = 2
            r8 = r5[r7]     // Catch: java.lang.Throwable -> L52
            java.lang.String r9 = ":"
            int r8 = r8.indexOf(r9)     // Catch: java.lang.Throwable -> L52
            r7 = r5[r7]     // Catch: java.lang.Throwable -> L52
            int r8 = r8 + r6
            int r6 = r7.length()     // Catch: java.lang.Throwable -> L52
            java.lang.String r6 = r7.substring(r8, r6)     // Catch: java.lang.Throwable -> L52
            boolean r6 = r10.equalsIgnoreCase(r6)     // Catch: java.lang.Throwable -> L52
            if (r6 == 0) goto L12
            r10 = 8
            r1 = r5[r10]     // Catch: java.lang.Throwable -> L52
            goto L54
        L52:
            r10 = move-exception
            goto L5c
        L54:
            r2.close()     // Catch: java.io.IOException -> L58 java.io.FileNotFoundException -> L5a
            goto L8b
        L58:
            r10 = move-exception
            goto L65
        L5a:
            r10 = move-exception
            goto L72
        L5c:
            r2.close()     // Catch: java.lang.Throwable -> L60
            goto L64
        L60:
            r2 = move-exception
            r10.addSuppressed(r2)     // Catch: java.io.IOException -> L58 java.io.FileNotFoundException -> L5a
        L64:
            throw r10     // Catch: java.io.IOException -> L58 java.io.FileNotFoundException -> L5a
        L65:
            java.lang.String r2 = "Failed to read "
            java.lang.String r11 = r2.concat(r11)
            android.util.Log.e(r0, r11)
            r10.printStackTrace()
            goto L8b
        L72:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "File "
            r2.<init>(r3)
            r2.append(r11)
            java.lang.String r11 = " not found."
            r2.append(r11)
            java.lang.String r11 = r2.toString()
            android.util.Log.e(r0, r11)
            r10.printStackTrace()
        L8b:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.proxy.LocalProxyManager.getAppUidFromTcpFile(int,"
                    + " java.lang.String):java.lang.String");
    }

    public static List getAuthConfigOrNull(ProxyProperties proxyProperties, String str, int i) {
        if (proxyProperties.getAuthConfigList().isEmpty()) {
            return null;
        }
        if (!(!TextUtils.isEmpty(proxyProperties.getHostname())
                        && proxyProperties.getHostname().equals(str)
                        && proxyProperties.getPortNumber() == i)
                && TextUtils.isEmpty(proxyProperties.getPacFileUrl())) {
            return null;
        }
        return proxyProperties.getAuthConfigList();
    }

    public static ProxyProperties getDefaultProxy() {
        ProxyProperties proxyProperties;
        synchronized (mProxyLock) {
            proxyProperties = sDefaultProxy;
        }
        return proxyProperties;
    }

    public static ProxyProperties getGlobalProxy() {
        ProxyProperties proxyProperties;
        synchronized (mProxyLock) {
            proxyProperties = sGlobalProxy;
        }
        return proxyProperties;
    }

    public static LocalProxyManager getInstance(Context context) {
        LocalProxyManager localProxyManager = sInstance;
        if (localProxyManager == null) {
            synchronized (sLocalProxyManagerLock) {
                try {
                    localProxyManager = sInstance;
                    if (localProxyManager == null) {
                        localProxyManager = new LocalProxyManager();
                        localProxyManager.mConnectedWifiSsid = null;
                        localProxyManager.mNetworkCallback =
                                new ConnectivityManager
                                        .NetworkCallback() { // from class:
                                                             // com.android.server.enterprise.proxy.LocalProxyManager.4
                                    @Override // android.net.ConnectivityManager.NetworkCallback
                                    public final void onAvailable(Network network) {
                                        WifiInfo connectionInfo;
                                        super.onAvailable(network);
                                        if (((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache)
                                                .isEmpty()) {
                                            return;
                                        }
                                        LocalProxyManager localProxyManager2 =
                                                LocalProxyManager.this;
                                        if (localProxyManager2.mContext == null
                                                || (connectionInfo =
                                                                localProxyManager2
                                                                        .getWifiManager()
                                                                        .getConnectionInfo())
                                                        == null) {
                                            return;
                                        }
                                        String replace = connectionInfo.getSSID().replace("\"", "");
                                        ProxyProperties proxyProperties =
                                                (ProxyProperties)
                                                        ((ArrayMap)
                                                                        LocalProxyManager
                                                                                .sWifiProxyInfoMapCache)
                                                                .get(replace);
                                        if (proxyProperties == null) {
                                            return;
                                        }
                                        synchronized (LocalProxyManager.mProxyLock) {
                                            LocalProxyManager.sDefaultProxy = proxyProperties;
                                        }
                                        if (LocalProxyManager.getDefaultProxy() != null) {
                                            LocalProxyManager localProxyManager3 =
                                                    LocalProxyManager.this;
                                            localProxyManager3.mConnectedWifiSsid = replace;
                                            localProxyManager3.handleLocalProxyServer();
                                        }
                                    }

                                    @Override // android.net.ConnectivityManager.NetworkCallback
                                    public final void onLost(Network network) {
                                        super.onLost(network);
                                        ProxyProperties defaultProxy =
                                                LocalProxyManager.getDefaultProxy();
                                        if (defaultProxy != null) {
                                            LocalProxyManager.this.getClass();
                                            if (LocalProxyManager.isDirectProxy(defaultProxy)) {
                                                LocalProxyManager localProxyManager2 =
                                                        LocalProxyManager.this;
                                                localProxyManager2.updateProxyInWifiConfig(
                                                        localProxyManager2.mConnectedWifiSsid,
                                                        true,
                                                        NetworkUtils.convertToProxyInfo(
                                                                (ProxyProperties)
                                                                        ((ArrayMap)
                                                                                        LocalProxyManager
                                                                                                .sWifiProxyInfoMapCache)
                                                                                .get(
                                                                                        LocalProxyManager
                                                                                                .this
                                                                                                .mConnectedWifiSsid)));
                                                LocalProxyManager.this.mConnectedWifiSsid = null;
                                            }
                                            synchronized (LocalProxyManager.mProxyLock) {
                                                LocalProxyManager.sDefaultProxy = null;
                                            }
                                            LocalProxyManager.this.handleLocalProxyServer();
                                        }
                                    }
                                };
                        Log.i("LocalProxyManager", "Initializing LocalProxyManager");
                        localProxyManager.mContext = context;
                        localProxyManager.mNotificationManager =
                                (NotificationManager)
                                        context.getSystemService(NotificationManager.class);
                        sInstance = localProxyManager;
                    }
                } finally {
                }
            }
        }
        return localProxyManager;
    }

    public static boolean isDirectProxy(ProxyProperties proxyProperties) {
        return proxyProperties != null && TextUtils.isEmpty(proxyProperties.getPacFileUrl());
    }

    public final synchronized void bindProxyService() {
        Intent intent = new Intent();
        intent.setClassName("com.android.proxyhandler", "com.android.proxyhandler.ProxyService");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 1);
        this.mProxyConnection = anonymousClass1;
        this.mContext.bindService(intent, anonymousClass1, 1073741829);
    }

    public final void clearProxyServerCache() {
        Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda2(this, 1));
    }

    public final ConnectivityManager getConnectivityManagerService() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager =
                    (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        }
        return this.mConnectivityManager;
    }

    public final String getProxyCredentials(int i, String str) {
        List<AuthConfig> list;
        ProxyProperties defaultProxy = getDefaultProxy();
        ProxyProperties globalProxy = getGlobalProxy();
        String str2 = null;
        if (globalProxy != null) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "Reading auth config for global proxy ", str, " port ", "LocalProxyManager");
            list = getAuthConfigOrNull(globalProxy, str, i);
        } else if (defaultProxy != null) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i,
                    "Reading auth config for default proxy ",
                    str,
                    " port ",
                    "LocalProxyManager");
            list = getAuthConfigOrNull(defaultProxy, str, i);
        } else {
            list = null;
        }
        if (list == null || list.isEmpty()) {
            String str3 = this.mConnectedWifiSsid;
            StringBuilder sb = new StringBuilder();
            AuthConfig authConfig =
                    (AuthConfig) ((ArrayMap) sWifiBackCompatCredentialsMapCache).get(str3);
            if (authConfig != null
                    && !TextUtils.isEmpty(authConfig.getUsername())
                    && !TextUtils.isEmpty(authConfig.getPassword())) {
                sb.append(authConfig.getUsername());
                sb.append(":");
                sb.append(authConfig.getPassword());
            }
            String sb2 = sb.toString();
            if (TextUtils.isEmpty(sb2)) {
                return null;
            }
            return sb2;
        }
        for (AuthConfig authConfig2 : list) {
            String host = authConfig2.getHost();
            int port = authConfig2.getPort();
            String username = authConfig2.getUsername();
            String password = authConfig2.getPassword();
            if (host.equals(str) && port == i) {
                return AnyMotionDetector$$ExternalSyntheticOutline0.m(username, ":", password);
            }
            if (host.equals(AuthConfig.ANY_HOST) && port == AuthConfig.ANY_PORT) {
                str2 = AnyMotionDetector$$ExternalSyntheticOutline0.m(username, ":", password);
            }
        }
        return str2;
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        }
        return this.mWifiManager;
    }

    public final void handleLocalProxyServer() {
        boolean isDirectProxy = isDirectProxy(getGlobalProxy());
        boolean isDirectProxy2 = isDirectProxy(getDefaultProxy());
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy != null && !TextUtils.isEmpty(globalProxy.getPacFileUrl())) {
            ProxyInfo convertToProxyInfo = NetworkUtils.convertToProxyInfo(getGlobalProxy());
            if (convertToProxyInfo == null
                    || Uri.EMPTY.equals(convertToProxyInfo.getPacFileUrl())) {
                return;
            }
            Log.d("LocalProxyManager", "Set global PAC proxy");
            Binder.withCleanCallingIdentity(
                    new LocalProxyManager$$ExternalSyntheticLambda5(this, convertToProxyInfo, 0));
            return;
        }
        if (!sIsLocalProxyServerRunning && (isDirectProxy || isDirectProxy2)) {
            Log.d("LocalProxyManager", "Starting enterprise local proxy");
            Binder.withCleanCallingIdentity(
                    new LocalProxyManager$$ExternalSyntheticLambda2(this, 2));
            return;
        }
        if (sIsLocalProxyServerRunning && getGlobalProxy() == null && getDefaultProxy() == null) {
            Log.d("LocalProxyManager", "Stopping enterprise local proxy");
            Binder.withCleanCallingIdentity(
                    new LocalProxyManager$$ExternalSyntheticLambda2(this, 6));
            return;
        }
        if (sIsLocalProxyServerRunning && isDirectProxy2) {
            Log.d("LocalProxyManager", "Updating proxy values");
            if (isDirectProxy(getDefaultProxy())) {
                Log.d("LocalProxyManager", "Updating enterprise wifi proxy values");
                updateProxyInWifiConfig(this.mConnectedWifiSsid, true, sLocalProxyInfo);
                return;
            }
            return;
        }
        if (isDirectProxy || isDirectProxy2) {
            return;
        }
        Log.d("LocalProxyManager", "Clearing proxy values");
        clearProxyServerCache();
    }

    public final void updateGlobalProxy(ProxyProperties proxyProperties) {
        Log.d("LocalProxyManager", "Updating global proxy");
        synchronized (mProxyLock) {
            sGlobalProxy = proxyProperties;
        }
        handleLocalProxyServer();
    }

    public final void updateGlobalProxyValues() {
        if (isDirectProxy(getGlobalProxy())) {
            Log.d("LocalProxyManager", "Updating enterprise global proxy values");
            synchronized (this) {
                if (getConnectivityManagerService() != null) {
                    Log.d("LocalProxyManager", "Updating enterprise global proxy value");
                    Binder.withCleanCallingIdentity(
                            new LocalProxyManager$$ExternalSyntheticLambda2(this, 3));
                    clearProxyServerCache();
                }
            }
        }
    }

    public final synchronized void updateProxyInWifiConfig(
            final String str, final boolean z, final ProxyInfo proxyInfo) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Binder.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda13
                    public final void runOrThrow() {
                        final LocalProxyManager localProxyManager = LocalProxyManager.this;
                        String str2 = str;
                        final boolean z2 = z;
                        ProxyInfo proxyInfo2 = proxyInfo;
                        for (WifiConfiguration wifiConfiguration :
                                localProxyManager.getWifiManager().getConfiguredNetworks()) {
                            if (wifiConfiguration != null
                                    && !TextUtils.isEmpty(wifiConfiguration.SSID)
                                    && str2.equals(wifiConfiguration.SSID.replace("\"", ""))
                                    && ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache)
                                                    .get(str2)
                                            != null) {
                                WifiManager wifiManager = localProxyManager.getWifiManager();
                                IpConfiguration ipConfiguration =
                                        wifiConfiguration.getIpConfiguration();
                                IpConfiguration.ProxySettings proxySettings =
                                        z2
                                                ? IpConfiguration.ProxySettings.STATIC
                                                : IpConfiguration.ProxySettings.NONE;
                                if (!z2) {
                                    proxyInfo2 = null;
                                }
                                ipConfiguration.setProxySettings(proxySettings);
                                ipConfiguration.setHttpProxy(proxyInfo2);
                                wifiConfiguration.setIpConfiguration(ipConfiguration);
                                wifiManager.save(
                                        wifiConfiguration,
                                        new WifiManager.ActionListener() { // from class:
                                            // com.android.server.enterprise.proxy.LocalProxyManager.3
                                            public final void onFailure(int i) {
                                                NetworkScorerAppManager$$ExternalSyntheticOutline0
                                                        .m(
                                                                i,
                                                                "Fail to update wifi proxy -"
                                                                    + " reason: ",
                                                                "LocalProxyManager");
                                            }

                                            public final void onSuccess() {
                                                Log.d(
                                                        "LocalProxyManager",
                                                        "Wifi proxy updated successfully");
                                                if (z2) {
                                                    LocalProxyManager.this.clearProxyServerCache();
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                });
    }

    public final void updateWifiProxy(Map map) {
        Log.d("LocalProxyManager", "Updating wifi proxy");
        if (map == null) {
            ArrayList arrayList = new ArrayList();
            for (String str : ((ArrayMap) sWifiProxyInfoMapCache).keySet()) {
                if (!TextUtils.isEmpty(str)) {
                    arrayList.add(str);
                }
            }
            synchronized (mProxyLock) {
                ((ArrayMap) sWifiProxyInfoMapCache).clear();
                sDefaultProxy = null;
            }
            Binder.withCleanCallingIdentity(
                    new LocalProxyManager$$ExternalSyntheticLambda0(this, arrayList, 0));
        } else {
            map.forEach(new LocalProxyManager$$ExternalSyntheticLambda1(0));
        }
        if (!sIsNetworkCallbackRunning && !((ArrayMap) sWifiProxyInfoMapCache).isEmpty()) {
            synchronized (this) {
                if (sIsNetworkCallbackRunning) {
                    return;
                }
                Log.d("LocalProxyManager", "Register proxy network callback for wifi");
                Binder.withCleanCallingIdentity(
                        new LocalProxyManager$$ExternalSyntheticLambda2(this, 4));
                return;
            }
        }
        if (sIsNetworkCallbackRunning && ((ArrayMap) sWifiProxyInfoMapCache).isEmpty()) {
            synchronized (this) {
                if (sIsNetworkCallbackRunning) {
                    Log.d("LocalProxyManager", "Unregister proxy network callback for wifi");
                    Binder.withCleanCallingIdentity(
                            new LocalProxyManager$$ExternalSyntheticLambda2(this, 0));
                }
            }
        }
    }
}
