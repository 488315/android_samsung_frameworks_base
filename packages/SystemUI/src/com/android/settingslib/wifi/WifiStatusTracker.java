package com.android.settingslib.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.ScoredNetwork;
import android.net.TransportInfo;
import android.net.vcn.VcnTransportInfo;
import android.net.vcn.VcnUtils;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import com.android.systemui.R;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class WifiStatusTracker {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public boolean connected;
    public boolean enabled;
    public boolean isCaptivePortal;
    public boolean isCarrierMerged;
    public boolean isDefaultNetwork;
    public int level;
    public final AnonymousClass3 mCacheListener;
    public final Runnable mCallback;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final AnonymousClass2 mDefaultNetworkCallback;
    public NetworkCapabilities mDefaultNetworkCapabilities;
    public final Handler mHandler;
    public final String[] mHistory;
    public int mHistoryIndex;
    public final Handler mMainThreadHandler;
    public final AnonymousClass1 mNetworkCallback;
    public final NetworkRequest mNetworkRequest;
    public final NetworkScoreManager mNetworkScoreManager;
    public final Set mNetworks;
    public int mPrimaryNetworkId;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public final WifiNetworkScoreCache mWifiNetworkScoreCache;
    public int rssi;
    public String ssid;
    public String statusLabel;
    public int subId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$1, reason: invalid class name */
    public class AnonymousClass1 extends ConnectivityManager.NetworkCallback {
        public AnonymousClass1(int i) {
            super(i);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiStatusTracker wifiStatusTracker = WifiStatusTracker.this;
            SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
            WifiInfo mainOrUnderlyingWifiInfo = wifiStatusTracker.getMainOrUnderlyingWifiInfo(networkCapabilities);
            WifiStatusTracker.this.getClass();
            if (networkCapabilities != null && (mainOrUnderlyingWifiInfo != null || networkCapabilities.hasTransport(1))) {
                String str = WifiStatusTracker.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onCapabilitiesChanged: network=" + network + ",networkCapabilities=" + networkCapabilities;
                WifiStatusTracker wifiStatusTracker2 = WifiStatusTracker.this;
                int i = wifiStatusTracker2.mHistoryIndex;
                wifiStatusTracker2.mHistory[i] = str;
                wifiStatusTracker2.mHistoryIndex = (i + 1) % 32;
            }
            if (mainOrUnderlyingWifiInfo == null) {
                return;
            }
            if (!mainOrUnderlyingWifiInfo.isPrimary()) {
                if (((HashSet) WifiStatusTracker.this.mNetworks).contains(Integer.valueOf(network.getNetId()))) {
                    ((HashSet) WifiStatusTracker.this.mNetworks).remove(Integer.valueOf(network.getNetId()));
                    return;
                }
                return;
            }
            if (!((HashSet) WifiStatusTracker.this.mNetworks).contains(Integer.valueOf(network.getNetId()))) {
                ((HashSet) WifiStatusTracker.this.mNetworks).add(Integer.valueOf(network.getNetId()));
            }
            WifiStatusTracker.this.mPrimaryNetworkId = network.getNetId();
            WifiStatusTracker.m993$$Nest$mupdateWifiInfo(WifiStatusTracker.this, mainOrUnderlyingWifiInfo);
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$1$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            String str = WifiStatusTracker.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onLost: network=" + network;
            WifiStatusTracker wifiStatusTracker = WifiStatusTracker.this;
            int i = wifiStatusTracker.mHistoryIndex;
            wifiStatusTracker.mHistory[i] = str;
            wifiStatusTracker.mHistoryIndex = (i + 1) % 32;
            if (((HashSet) wifiStatusTracker.mNetworks).contains(Integer.valueOf(network.getNetId()))) {
                ((HashSet) WifiStatusTracker.this.mNetworks).remove(Integer.valueOf(network.getNetId()));
            }
            int netId = network.getNetId();
            WifiStatusTracker wifiStatusTracker2 = WifiStatusTracker.this;
            if (netId != wifiStatusTracker2.mPrimaryNetworkId) {
                return;
            }
            WifiStatusTracker.m993$$Nest$mupdateWifiInfo(wifiStatusTracker2, null);
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$1$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$2, reason: invalid class name */
    public class AnonymousClass2 extends ConnectivityManager.NetworkCallback {
        public AnonymousClass2(int i) {
            super(i);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiStatusTracker wifiStatusTracker = WifiStatusTracker.this;
            SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
            wifiStatusTracker.getClass();
            WifiStatusTracker wifiStatusTracker2 = WifiStatusTracker.this;
            wifiStatusTracker2.mDefaultNetworkCapabilities = networkCapabilities;
            wifiStatusTracker2.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$2$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            WifiStatusTracker wifiStatusTracker = WifiStatusTracker.this;
            SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
            wifiStatusTracker.getClass();
            WifiStatusTracker wifiStatusTracker2 = WifiStatusTracker.this;
            wifiStatusTracker2.mDefaultNetworkCapabilities = null;
            wifiStatusTracker2.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$2$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$3, reason: invalid class name */
    public class AnonymousClass3 extends WifiNetworkScoreCache.CacheListener {
        public AnonymousClass3(Handler handler) {
            super(handler);
        }

        public final void networkCacheUpdated(List list) {
            WifiStatusTracker wifiStatusTracker = WifiStatusTracker.this;
            SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
            wifiStatusTracker.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* renamed from: -$$Nest$mupdateWifiInfo, reason: not valid java name */
    public static void m993$$Nest$mupdateWifiInfo(WifiStatusTracker wifiStatusTracker, WifiInfo wifiInfo) {
        wifiStatusTracker.updateWifiState();
        wifiStatusTracker.connected = wifiInfo != null;
        wifiStatusTracker.mWifiInfo = wifiInfo;
        String str = null;
        wifiStatusTracker.ssid = null;
        if (wifiInfo != null) {
            if (wifiInfo.isPasspointAp() || wifiStatusTracker.mWifiInfo.isOsuAp()) {
                wifiStatusTracker.ssid = wifiStatusTracker.mWifiInfo.getPasspointProviderFriendlyName();
            } else {
                String ssid = wifiStatusTracker.mWifiInfo.getSSID();
                if (ssid != null && !"<unknown ssid>".equals(ssid)) {
                    str = ssid;
                }
                wifiStatusTracker.ssid = str;
            }
            wifiStatusTracker.isCarrierMerged = wifiStatusTracker.mWifiInfo.isCarrierMerged();
            wifiStatusTracker.subId = wifiStatusTracker.mWifiInfo.getSubscriptionId();
            wifiStatusTracker.updateRssi(wifiStatusTracker.mWifiInfo.getRssi());
            NetworkKey createFromWifiInfo = NetworkKey.createFromWifiInfo(wifiStatusTracker.mWifiInfo);
            if (wifiStatusTracker.mWifiNetworkScoreCache.getScoredNetwork(createFromWifiInfo) == null) {
                wifiStatusTracker.mNetworkScoreManager.requestScores(new NetworkKey[]{createFromWifiInfo});
            }
        }
    }

    public WifiStatusTracker(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Runnable runnable) {
        this(context, wifiManager, networkScoreManager, connectivityManager, runnable, null, null);
    }

    public final WifiInfo getMainOrUnderlyingWifiInfo(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        WifiInfo mainWifiInfo = getMainWifiInfo(networkCapabilities);
        if (mainWifiInfo != null) {
            return mainWifiInfo;
        }
        if (!networkCapabilities.hasTransport(0)) {
            return mainWifiInfo;
        }
        List underlyingNetworks = networkCapabilities.getUnderlyingNetworks();
        if (underlyingNetworks == null) {
            return null;
        }
        Iterator it = underlyingNetworks.iterator();
        while (it.hasNext()) {
            WifiInfo mainWifiInfo2 = getMainWifiInfo(this.mConnectivityManager.getNetworkCapabilities((Network) it.next()));
            if (mainWifiInfo2 != null) {
                return mainWifiInfo2;
            }
        }
        return null;
    }

    public final WifiInfo getMainWifiInfo(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        if (!networkCapabilities.hasTransport(1) && !networkCapabilities.hasTransport(0)) {
            return null;
        }
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (transportInfo instanceof VcnTransportInfo) {
            return VcnUtils.getWifiInfoFromVcnCaps(this.mConnectivityManager, networkCapabilities);
        }
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        return null;
    }

    public final void postResults() {
        this.mCallback.run();
    }

    public final void updateRssi(int i) {
        this.rssi = i;
        this.level = i <= -89 ? 0 : (i <= -89 || i > -83) ? (i <= -83 || i > -75) ? (i <= -75 || i > -64) ? 4 : 3 : 2 : 1;
    }

    public final void updateStatusLabel() {
        String speedLabel;
        NetworkCapabilities networkCapabilities;
        if (this.mWifiManager == null) {
            return;
        }
        NetworkCapabilities networkCapabilities2 = this.mDefaultNetworkCapabilities;
        boolean z = networkCapabilities2 != null && (getMainOrUnderlyingWifiInfo(networkCapabilities2) != null || networkCapabilities2.hasTransport(1));
        this.isDefaultNetwork = z;
        NetworkCapabilities networkCapabilities3 = z ? this.mDefaultNetworkCapabilities : this.mConnectivityManager.getNetworkCapabilities(this.mWifiManager.getCurrentNetwork());
        this.isCaptivePortal = false;
        if (networkCapabilities3 != null) {
            if (networkCapabilities3.hasCapability(17)) {
                this.statusLabel = this.mContext.getString(R.string.wifi_status_sign_in_required);
                this.isCaptivePortal = true;
                return;
            }
            if (networkCapabilities3.hasCapability(24)) {
                this.statusLabel = this.mContext.getString(R.string.wifi_limited_connection);
                return;
            }
            if (!networkCapabilities3.hasCapability(16)) {
                Settings.Global.getString(this.mContext.getContentResolver(), "private_dns_mode");
                if (networkCapabilities3.isPrivateDnsBroken()) {
                    this.statusLabel = this.mContext.getString(R.string.private_dns_broken);
                    return;
                } else {
                    this.statusLabel = this.mContext.getString(R.string.wifi_status_no_internet);
                    return;
                }
            }
            if (!this.isDefaultNetwork && (networkCapabilities = this.mDefaultNetworkCapabilities) != null && networkCapabilities.hasTransport(0)) {
                this.statusLabel = this.mContext.getString(R.string.wifi_connected_low_quality);
                return;
            }
        }
        ScoredNetwork scoredNetwork = this.mWifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(this.mWifiInfo));
        if (scoredNetwork == null) {
            speedLabel = null;
        } else {
            Context context = this.mContext;
            int i = this.rssi;
            int i2 = AccessPoint.$r8$clinit;
            int calculateBadge = scoredNetwork.calculateBadge(i);
            speedLabel = AccessPoint.getSpeedLabel(calculateBadge >= 5 ? calculateBadge < 7 ? 5 : calculateBadge < 15 ? 10 : calculateBadge < 25 ? 20 : 30 : 0, context);
        }
        this.statusLabel = speedLabel;
    }

    public final void updateWifiState() {
        this.enabled = this.mWifiManager.getWifiState() == 3;
    }

    public WifiStatusTracker(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Runnable runnable, Handler handler, Handler handler2) {
        this.mNetworks = new HashSet();
        this.mHistory = new String[32];
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).addTransportType(0).build();
        this.mNetworkCallback = new AnonymousClass1(1);
        this.mDefaultNetworkCallback = new AnonymousClass2(1);
        this.mDefaultNetworkCapabilities = null;
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mWifiNetworkScoreCache = new WifiNetworkScoreCache(context);
        this.mNetworkScoreManager = networkScoreManager;
        this.mConnectivityManager = connectivityManager;
        this.mCallback = runnable;
        if (handler2 == null) {
            HandlerThread handlerThread = new HandlerThread("WifiStatusTrackerHandler");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        } else {
            this.mHandler = handler2;
        }
        this.mMainThreadHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
        this.mCacheListener = new AnonymousClass3(this.mHandler);
    }
}
