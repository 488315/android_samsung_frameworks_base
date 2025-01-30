package com.android.server.enterprise.proxy;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.IpConfiguration;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.ProxyInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.proxy.EnterpriseProxyConstants;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.net.IProxyCallback;
import com.android.net.IProxyPortListener;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.utils.NetworkUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class LocalProxyManager {
    public static volatile ProxyProperties sDefaultProxy;
    public static volatile ProxyProperties sGlobalProxy;
    public static volatile LocalProxyManager sInstance;
    public static volatile ProxyInfo sLocalProxyInfo;
    public IProxyCallback mCallbackService;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public IProxyCredentialsCallback mLastCredentialsCallback;
    public Bundle mLastCredentialsResponse;
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
    public boolean DBG = true;
    public String mConnectedWifiSsid = null;
    public ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.enterprise.proxy.LocalProxyManager.4
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            WifiInfo connectionInfo;
            super.onAvailable(network);
            if (LocalProxyManager.sWifiProxyInfoMapCache.isEmpty() || LocalProxyManager.this.mContext == null || (connectionInfo = LocalProxyManager.this.getWifiManager().getConnectionInfo()) == null) {
                return;
            }
            String replace = connectionInfo.getSSID().replace("\"", "");
            ProxyProperties proxyProperties = (ProxyProperties) LocalProxyManager.sWifiProxyInfoMapCache.get(replace);
            if (proxyProperties == null) {
                return;
            }
            synchronized (LocalProxyManager.mProxyLock) {
                LocalProxyManager.sDefaultProxy = proxyProperties;
            }
            if (LocalProxyManager.getDefaultProxy() != null) {
                LocalProxyManager.this.mConnectedWifiSsid = replace;
                LocalProxyManager.this.handleLocalProxyServer();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            super.onLost(network);
            ProxyProperties defaultProxy = LocalProxyManager.getDefaultProxy();
            if (defaultProxy != null) {
                if (LocalProxyManager.this.isDirectProxy(defaultProxy)) {
                    LocalProxyManager localProxyManager = LocalProxyManager.this;
                    localProxyManager.updateProxyInWifiConfig(localProxyManager.mConnectedWifiSsid, true, NetworkUtils.convertToProxyInfo((ProxyProperties) LocalProxyManager.sWifiProxyInfoMapCache.get(LocalProxyManager.this.mConnectedWifiSsid)));
                    LocalProxyManager.this.mConnectedWifiSsid = null;
                }
                synchronized (LocalProxyManager.mProxyLock) {
                    LocalProxyManager.sDefaultProxy = null;
                }
                LocalProxyManager.this.handleLocalProxyServer();
            }
        }
    };

    public final boolean shouldClearProxyValues(boolean z, boolean z2) {
        return (z || z2) ? false : true;
    }

    public static LocalProxyManager getInstance(Context context) {
        LocalProxyManager localProxyManager = sInstance;
        if (localProxyManager == null) {
            synchronized (sLocalProxyManagerLock) {
                localProxyManager = sInstance;
                if (localProxyManager == null) {
                    localProxyManager = new LocalProxyManager(context);
                    sInstance = localProxyManager;
                }
            }
        }
        return localProxyManager;
    }

    public LocalProxyManager(Context context) {
        Log.i("LocalProxyManager", "Initializing LocalProxyManager");
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    public final ConnectivityManager getConnectivityManagerService() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        }
        return this.mConnectivityManager;
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        }
        return this.mWifiManager;
    }

    public static ProxyProperties getGlobalProxy() {
        ProxyProperties proxyProperties;
        synchronized (mProxyLock) {
            proxyProperties = sGlobalProxy;
        }
        return proxyProperties;
    }

    public static ProxyProperties getDefaultProxy() {
        ProxyProperties proxyProperties;
        synchronized (mProxyLock) {
            proxyProperties = sDefaultProxy;
        }
        return proxyProperties;
    }

    /* renamed from: unbindLocalProxyServer, reason: merged with bridge method [inline-methods] */
    public final synchronized void lambda$stopLocalProxyServer$5() {
        if (this.mProxyConnection != null) {
            Log.i("LocalProxyManager", "Stopping enterprise proxy server");
            clearProxyServerCache();
            clearNotificationDialog();
            Context context = this.mContext;
            if (context != null) {
                context.unbindService(this.mProxyConnection);
            }
            this.mProxyConnection = null;
            this.mCallbackService = null;
            synchronized (mProxyLock) {
                sLocalProxyInfo = null;
                sIsLocalProxyServerRunning = false;
            }
        }
    }

    /* renamed from: bindToLocalProxyServer, reason: merged with bridge method [inline-methods] */
    public final synchronized void lambda$startLocalProxyServer$4() {
        Log.i("LocalProxyManager", "Starting enterprise proxy server");
        if (this.mContext == null) {
            Log.e("LocalProxyManager", "No context for binding");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.android.proxyhandler", "com.android.proxyhandler.ProxyService");
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.server.enterprise.proxy.LocalProxyManager.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                LocalProxyManager.this.mCallbackService = IProxyCallback.Stub.asInterface(iBinder);
                if (LocalProxyManager.this.mCallbackService != null) {
                    try {
                        LocalProxyManager.this.mCallbackService.getProxyPort(new IProxyPortListener.Stub() { // from class: com.android.server.enterprise.proxy.LocalProxyManager.1.1
                            public void setProxyPort(int i) {
                                if (i != -1) {
                                    if (LocalProxyManager.this.DBG) {
                                        Log.d("LocalProxyManager", "Proxy bound at port " + i);
                                    }
                                    LocalProxyManager.sIsLocalProxyServerRunning = true;
                                    LocalProxyManager.this.setLocalProxyPort(i);
                                    return;
                                }
                                Log.e("LocalProxyManager", "Received invalid port from Local Proxy, proxy will not be operational");
                            }
                        });
                    } catch (RemoteException e) {
                        Log.e("LocalProxyManager", "Failed to get proxy callback instance");
                        e.printStackTrace();
                    }
                }
            }
        };
        this.mProxyConnection = serviceConnection;
        this.mContext.bindService(intent, serviceConnection, 1073741829);
    }

    public final synchronized void bindProxyService() {
        Intent intent = new Intent();
        intent.setClassName("com.android.proxyhandler", "com.android.proxyhandler.ProxyService");
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.server.enterprise.proxy.LocalProxyManager.2
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                LocalProxyManager.this.mCallbackService = IProxyCallback.Stub.asInterface(iBinder);
                if (LocalProxyManager.this.mCallbackService != null) {
                    LocalProxyManager.this.executePendingOperations();
                }
            }
        };
        this.mProxyConnection = serviceConnection;
        this.mContext.bindService(intent, serviceConnection, 1073741829);
    }

    public final synchronized void setLocalProxyPort(int i) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Set local proxy port " + i);
        }
        synchronized (mProxyLock) {
            sLocalProxyInfo = ProxyInfo.buildPacProxy(Uri.EMPTY, i);
        }
        updateGlobalProxyValues();
        updateWifiProxyValues();
    }

    public ProxyInfo getLocalProxyInfo() {
        return sLocalProxyInfo;
    }

    public final void updateGlobalProxyValues() {
        if (isDirectProxy(getGlobalProxy())) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Updating enterprise global proxy values");
            }
            setGlobalProxy();
        }
    }

    public final void updateWifiProxyValues() {
        if (isDirectProxy(getDefaultProxy())) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Updating enterprise wifi proxy values");
            }
            updateProxyInWifiConfig(this.mConnectedWifiSsid, true, sLocalProxyInfo);
        }
    }

    public final synchronized void setGlobalProxy() {
        if (getConnectivityManagerService() != null) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Updating enterprise global proxy value");
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda16
                public final void runOrThrow() {
                    LocalProxyManager.this.lambda$setGlobalProxy$0();
                }
            });
            clearProxyServerCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGlobalProxy$0() {
        this.mConnectivityManager.setGlobalProxy(sLocalProxyInfo);
    }

    public final void setGlobalProxyPac() {
        final ProxyInfo convertToProxyInfo = NetworkUtils.convertToProxyInfo(getGlobalProxy());
        if (convertToProxyInfo == null || Uri.EMPTY.equals(convertToProxyInfo.getPacFileUrl())) {
            return;
        }
        if (this.DBG) {
            Log.d("LocalProxyManager", "Set global PAC proxy");
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$setGlobalProxyPac$1(convertToProxyInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGlobalProxyPac$1(ProxyInfo proxyInfo) {
        getConnectivityManagerService().setGlobalProxy(proxyInfo);
        clearProxyServerCache();
    }

    public final synchronized void updateProxyInWifiConfig(final String str, final boolean z, final ProxyInfo proxyInfo) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda14
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$updateProxyInWifiConfig$2(str, z, proxyInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateProxyInWifiConfig$2(String str, final boolean z, ProxyInfo proxyInfo) {
        for (WifiConfiguration wifiConfiguration : getWifiManager().getConfiguredNetworks()) {
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID) && str.equals(wifiConfiguration.SSID.replace("\"", "")) && sWifiProxyInfoMapCache.get(str) != null) {
                getWifiManager().save(setWifiConfigProxyProperties(wifiConfiguration, z, proxyInfo), new WifiManager.ActionListener() { // from class: com.android.server.enterprise.proxy.LocalProxyManager.3
                    public void onSuccess() {
                        Log.d("LocalProxyManager", "Wifi proxy updated successfully");
                        if (z) {
                            LocalProxyManager.this.clearProxyServerCache();
                        }
                    }

                    public void onFailure(int i) {
                        Log.d("LocalProxyManager", "Fail to update wifi proxy - reason: " + i);
                    }
                });
                return;
            }
        }
    }

    public final WifiConfiguration setWifiConfigProxyProperties(WifiConfiguration wifiConfiguration, boolean z, ProxyInfo proxyInfo) {
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        IpConfiguration.ProxySettings proxySettings = z ? IpConfiguration.ProxySettings.STATIC : IpConfiguration.ProxySettings.NONE;
        if (!z) {
            proxyInfo = null;
        }
        ipConfiguration.setProxySettings(proxySettings);
        ipConfiguration.setHttpProxy(proxyInfo);
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        return wifiConfiguration;
    }

    public final void removeDefaultProxyFromWifiNetwork(final List list) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$removeDefaultProxyFromWifiNetwork$3(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDefaultProxyFromWifiNetwork$3(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!TextUtils.isEmpty(str)) {
                updateProxyInWifiConfig(str, false, null);
            }
        }
    }

    public final void executePendingOperations() {
        if (sPendinOperationsList.isEmpty()) {
            return;
        }
        for (String str : sPendinOperationsList) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Execute pending operation: " + str);
            }
            str.hashCode();
            if (str.equals("clearCache")) {
                clearProxyServerCache();
                sPendinOperationsList.remove(str);
            } else if (str.equals("setProxyCredentials")) {
                setProxyCredentials(this.mLastCredentialsResponse, this.mLastCredentialsCallback);
                sPendinOperationsList.remove(str);
            }
        }
    }

    public final void handleNetworkCallback() {
        if (!sIsNetworkCallbackRunning && !sWifiProxyInfoMapCache.isEmpty()) {
            registerNetworkCallback();
        } else if (sIsNetworkCallbackRunning && sWifiProxyInfoMapCache.isEmpty()) {
            unregisterNetworkCallback();
        }
    }

    public final void handleLocalProxyServer() {
        boolean isDirectProxy = isDirectProxy(getGlobalProxy());
        boolean isDirectProxy2 = isDirectProxy(getDefaultProxy());
        if (isPacProxy(getGlobalProxy())) {
            setGlobalProxyPac();
            return;
        }
        if (shouldStartLocalProxyServer(isDirectProxy, isDirectProxy2)) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Starting enterprise local proxy");
            }
            startLocalProxyServer();
            return;
        }
        if (shouldStopLocalProxyServer()) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Stopping enterprise local proxy");
            }
            stopLocalProxyServer();
        } else if (shouldUpdateWifiProxyValues(isDirectProxy2)) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Updating proxy values");
            }
            updateWifiProxyValues();
        } else if (shouldClearProxyValues(isDirectProxy, isDirectProxy2)) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Clearing proxy values");
            }
            clearProxyServerCache();
        }
    }

    public final boolean shouldStartLocalProxyServer(boolean z, boolean z2) {
        return !sIsLocalProxyServerRunning && (z || z2);
    }

    public final boolean shouldStopLocalProxyServer() {
        return sIsLocalProxyServerRunning && !isAnyEnterpriseProxySet();
    }

    public final boolean shouldUpdateWifiProxyValues(boolean z) {
        return sIsLocalProxyServerRunning && z;
    }

    public final boolean isDirectProxy(ProxyProperties proxyProperties) {
        return proxyProperties != null && TextUtils.isEmpty(proxyProperties.getPacFileUrl());
    }

    public final boolean isPacProxy(ProxyProperties proxyProperties) {
        return (proxyProperties == null || TextUtils.isEmpty(proxyProperties.getPacFileUrl())) ? false : true;
    }

    public final void startLocalProxyServer() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda9
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$startLocalProxyServer$4();
            }
        });
    }

    public final void stopLocalProxyServer() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$stopLocalProxyServer$5();
            }
        });
    }

    public final List readAuthConfigFromProxyCache(String str, int i) {
        ProxyProperties defaultProxy = getDefaultProxy();
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy != null) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Reading auth config for global proxy " + str + " port " + i);
            }
            return getAuthConfigOrNull(globalProxy, str, i);
        }
        if (defaultProxy == null) {
            return null;
        }
        if (this.DBG) {
            Log.d("LocalProxyManager", "Reading auth config for default proxy " + str + " port " + i);
        }
        return getAuthConfigOrNull(defaultProxy, str, i);
    }

    public final List getAuthConfigOrNull(ProxyProperties proxyProperties, String str, int i) {
        if (proxyProperties == null || proxyProperties.getAuthConfigList().isEmpty()) {
            return null;
        }
        if (!(!TextUtils.isEmpty(proxyProperties.getHostname()) && proxyProperties.getHostname().equals(str) && proxyProperties.getPortNumber() == i) && TextUtils.isEmpty(proxyProperties.getPacFileUrl())) {
            return null;
        }
        return proxyProperties.getAuthConfigList();
    }

    public final synchronized void registerNetworkCallback() {
        if (sIsNetworkCallbackRunning) {
            return;
        }
        if (this.DBG) {
            Log.d("LocalProxyManager", "Register proxy network callback for wifi");
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$registerNetworkCallback$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerNetworkCallback$6() {
        getConnectivityManagerService().registerNetworkCallback(new NetworkRequest.Builder().addTransportType(1).addCapability(12).build(), this.mNetworkCallback);
        synchronized (mNetworkCallbackLock) {
            sIsNetworkCallbackRunning = true;
        }
    }

    public final synchronized void unregisterNetworkCallback() {
        if (sIsNetworkCallbackRunning) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Unregister proxy network callback for wifi");
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda5
                public final void runOrThrow() {
                    LocalProxyManager.this.lambda$unregisterNetworkCallback$7();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterNetworkCallback$7() {
        getConnectivityManagerService().unregisterNetworkCallback(this.mNetworkCallback);
        synchronized (mNetworkCallbackLock) {
            sIsNetworkCallbackRunning = false;
        }
        handleLocalProxyServer();
    }

    public final boolean isAnyEnterpriseProxySet() {
        return (getGlobalProxy() == null && getDefaultProxy() == null) ? false : true;
    }

    public final void clearWifiProxyCache() {
        ArrayList arrayList = new ArrayList();
        for (String str : sWifiProxyInfoMapCache.keySet()) {
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        synchronized (mProxyLock) {
            sWifiProxyInfoMapCache.clear();
            sDefaultProxy = null;
        }
        removeDefaultProxyFromWifiNetwork(arrayList);
    }

    public final void updateWifiProxyCache(Map map) {
        map.forEach(new BiConsumer() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LocalProxyManager.lambda$updateWifiProxyCache$8((String) obj, (ProxyProperties) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$updateWifiProxyCache$8(String str, ProxyProperties proxyProperties) {
        if (str == null) {
            synchronized (mProxyLock) {
                sWifiProxyInfoMapCache.remove(str);
            }
        } else {
            synchronized (mProxyLock) {
                sWifiProxyInfoMapCache.put(str, proxyProperties);
            }
        }
    }

    public final String getProxyCredentialsForSsid(String str) {
        StringBuilder sb = new StringBuilder();
        AuthConfig authConfig = (AuthConfig) sWifiBackCompatCredentialsMapCache.get(str);
        if (authConfig != null && !TextUtils.isEmpty(authConfig.getUsername()) && !TextUtils.isEmpty(authConfig.getPassword())) {
            sb.append(authConfig.getUsername());
            sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            sb.append(authConfig.getPassword());
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0051, code lost:
    
        r0 = r4[8];
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getAppUidFromTcpFile(String str, int i) {
        String hexString = Integer.toHexString(i);
        String str2 = null;
        try {
            FileReader fileReader = new FileReader(str);
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                int i2 = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String str3 = readLine + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
                    i2++;
                    if (i2 > 1) {
                        String[] split = str3.split(" +");
                        int indexOf = split[2].indexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
                        String str4 = split[2];
                        if (hexString.equalsIgnoreCase(str4.substring(indexOf + 1, str4.length()))) {
                            break;
                        }
                    }
                }
                fileReader.close();
            } catch (Throwable th) {
                try {
                    fileReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.e("LocalProxyManager", "File " + str + " not found.");
            e.printStackTrace();
        } catch (IOException e2) {
            Log.e("LocalProxyManager", "Failed to read " + str);
            e2.printStackTrace();
        }
        return str2;
    }

    public void dumpEnterpriseProxyCache(final PrintWriter printWriter) {
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy != null) {
            printWriter.println("\nEnforced GlobalProxy:");
            printWriter.println("Host: " + globalProxy.getHostname());
            printWriter.println("Port: " + globalProxy.getPortNumber());
            printWriter.println("PAC file url: " + globalProxy.getPacFileUrl());
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        ProxyProperties defaultProxy = getDefaultProxy();
        if (defaultProxy != null) {
            printWriter.println("Enforced WifiProxy:");
            printWriter.println("Host: " + defaultProxy.getHostname());
            printWriter.println("Port: " + defaultProxy.getPortNumber());
            printWriter.println("PAC file url: " + defaultProxy.getPacFileUrl());
            printWriter.println("\n\n");
        }
        if (sWifiProxyInfoMapCache == null || sWifiProxyInfoMapCache.isEmpty()) {
            return;
        }
        sWifiProxyInfoMapCache.forEach(new BiConsumer() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LocalProxyManager.lambda$dumpEnterpriseProxyCache$9(printWriter, (String) obj, (ProxyProperties) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$dumpEnterpriseProxyCache$9(PrintWriter printWriter, String str, ProxyProperties proxyProperties) {
        if (str == null || proxyProperties == null) {
            return;
        }
        printWriter.println("WifiProxy for ssid=" + str);
        printWriter.println("Host: " + proxyProperties.getHostname());
        printWriter.println("Port: " + proxyProperties.getPortNumber());
        printWriter.println("PAC file url: " + proxyProperties.getPacFileUrl());
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }

    public void updateGlobalProxy(ProxyProperties proxyProperties) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Updating global proxy");
        }
        synchronized (mProxyLock) {
            sGlobalProxy = proxyProperties;
        }
        handleLocalProxyServer();
    }

    public void updateWifiProxy(Map map) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Updating wifi proxy");
        }
        if (map == null) {
            clearWifiProxyCache();
        } else {
            updateWifiProxyCache(map);
        }
        handleNetworkCallback();
    }

    public void updateWifiBackCompatCredentialsCache(Map map) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Updating backwards wifi proxy cache");
        }
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach(new BiConsumer() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LocalProxyManager.lambda$updateWifiBackCompatCredentialsCache$10((String) obj, (AuthConfig) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$updateWifiBackCompatCredentialsCache$10(String str, AuthConfig authConfig) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        sWifiBackCompatCredentialsMapCache.put(str, authConfig);
    }

    public void removeWifiProxy(String str) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Removing wifi proxy for ssid " + str);
        }
        synchronized (mProxyLock) {
            sWifiProxyInfoMapCache.remove(str);
        }
    }

    public ProxyProperties getCurrentAppliedProxy() {
        ProxyProperties defaultProxy = getDefaultProxy();
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy != null) {
            if (this.DBG) {
                Log.d("LocalProxyManager", "Get current applied global proxy");
            }
            return globalProxy;
        }
        if (defaultProxy == null) {
            return null;
        }
        if (!this.DBG) {
            return defaultProxy;
        }
        Log.d("LocalProxyManager", "Get current applied default proxy");
        return defaultProxy;
    }

    public String getProxyCredentials(String str, int i) {
        List<AuthConfig> readAuthConfigFromProxyCache = readAuthConfigFromProxyCache(str, i);
        String str2 = null;
        if (readAuthConfigFromProxyCache != null && !readAuthConfigFromProxyCache.isEmpty()) {
            for (AuthConfig authConfig : readAuthConfigFromProxyCache) {
                String host = authConfig.getHost();
                int port = authConfig.getPort();
                String username = authConfig.getUsername();
                String password = authConfig.getPassword();
                if (host.equals(str) && port == i) {
                    return username + XmlUtils.STRING_ARRAY_SEPARATOR + password;
                }
                if (host.equals(AuthConfig.ANY_HOST) && port == AuthConfig.ANY_PORT) {
                    str2 = username + XmlUtils.STRING_ARRAY_SEPARATOR + password;
                }
            }
            return str2;
        }
        String proxyCredentialsForSsid = getProxyCredentialsForSsid(this.mConnectedWifiSsid);
        if (TextUtils.isEmpty(proxyCredentialsForSsid)) {
            return null;
        }
        return proxyCredentialsForSsid;
    }

    public ProxyProperties getGlobalProxyCache() {
        return getGlobalProxy();
    }

    public void clearProxyServerCache() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$clearProxyServerCache$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearProxyServerCache$11() {
        IProxyCallback iProxyCallback = this.mCallbackService;
        if (iProxyCallback == null) {
            sPendinOperationsList.add("clearCache");
            bindProxyService();
            return;
        }
        try {
            iProxyCallback.clearProxyServerCache();
            this.mCallbackService.setEnterpriseProxy(isAnyEnterpriseProxySet());
        } catch (RemoteException e) {
            Log.e("LocalProxyManager", "Failed to clear proxy server cache");
            e.printStackTrace();
        }
    }

    public List getAppUidBrowserList() {
        final ArrayList arrayList = new ArrayList();
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$getAppUidBrowserList$12(arrayList);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAppUidBrowserList$12(List list) {
        ApplicationInfo applicationInfo;
        Iterator it = EnterpriseProxyConstants.LOCAL_ENTERPRISE_PROXY_WHITELIST.iterator();
        while (it.hasNext()) {
            try {
                PackageInfo packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo((String) it.next(), 128, 0);
                if (packageInfo != null && (applicationInfo = packageInfo.applicationInfo) != null) {
                    list.add(String.valueOf(applicationInfo.uid));
                }
            } catch (Exception e) {
                Log.e("LocalProxyManager", "Failed to get browser uid list");
                e.printStackTrace();
            }
        }
    }

    public void refreshCredentialsDialogFails() {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Refresh proxy credentials dialog");
        }
        if (this.mContext == null) {
            return;
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$refreshCredentialsDialogFails$13();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshCredentialsDialogFails$13() {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.PROXY_REFRESH_CREDENTIALS_DIALOG_INTERNAL");
        intent.setPackage(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME);
        this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }

    public void showCredentialsDialogNotification(final String str) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Show credentials dialog notification");
        }
        if (this.mContext == null) {
            return;
        }
        if (this.mNotificationManager != null) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda13
                public final void runOrThrow() {
                    LocalProxyManager.this.lambda$showCredentialsDialogNotification$14(str);
                }
            });
        } else {
            Log.d("LocalProxyManager", "NotificationManager is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCredentialsDialogNotification$14(String str) {
        Intent intent = new Intent();
        intent.addFlags(1350565888);
        intent.setClassName(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, "com.samsung.android.mdm.EnterpriseProxyAuthDialog");
        intent.putExtra("proxy", str);
        this.mNotificationManager.notifyAsUser("LocalProxyManager", 993, new Notification.Builder(this.mContext, SystemNotificationChannels.NETWORK_ALERTS).setContentTitle(this.mContext.getResources().getString(17042127)).setSmallIcon(R.drawable.stat_sys_warning).setOngoing(true).setOnlyAlertOnce(true).setContentIntent(PendingIntent.getActivity(this.mContext, 0, intent, 335544320)).build(), UserHandle.ALL);
    }

    public void clearNotificationDialog() {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Clear notification dialog");
        }
        if (this.mNotificationManager != null) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    LocalProxyManager.this.lambda$clearNotificationDialog$15();
                }
            });
        } else {
            Log.d("LocalProxyManager", "NotificationManager is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearNotificationDialog$15() {
        this.mNotificationManager.cancelAsUser("LocalProxyManager", 993, UserHandle.ALL);
    }

    public void setProxyCredentials(final Bundle bundle, final IProxyCredentialsCallback iProxyCredentialsCallback) {
        if (this.DBG) {
            Log.d("LocalProxyManager", "Set proxy credentials callback to proxy server");
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda10
            public final void runOrThrow() {
                LocalProxyManager.this.lambda$setProxyCredentials$16(iProxyCredentialsCallback, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProxyCredentials$16(IProxyCredentialsCallback iProxyCredentialsCallback, Bundle bundle) {
        IProxyCallback iProxyCallback = this.mCallbackService;
        if (iProxyCallback == null) {
            this.mLastCredentialsCallback = iProxyCredentialsCallback;
            this.mLastCredentialsResponse = bundle;
            sPendinOperationsList.add("setProxyCredentials");
            bindProxyService();
            return;
        }
        try {
            iProxyCallback.onCredentialsReceived(bundle, iProxyCredentialsCallback);
        } catch (RemoteException e) {
            Log.e("LocalProxyManager", "Faield to set received credentials");
            e.printStackTrace();
        }
    }

    public ProxyProperties getProxyForSsid(String str) {
        return (ProxyProperties) sWifiProxyInfoMapCache.get(str);
    }

    public String getAppUidFromSocketPortNumber(int i) {
        String appUidFromTcpFile = getAppUidFromTcpFile("/proc/net/tcp", i);
        return appUidFromTcpFile == null ? getAppUidFromTcpFile("/proc/net/tcp6", i) : appUidFromTcpFile;
    }
}
