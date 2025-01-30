package com.android.settingslib.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.ScoredNetwork;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.R;
import com.samsung.android.wifi.SemOpBrandingLoader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WifiStatusTracker {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public boolean connected;
    public boolean enabled;
    public boolean isCaptivePortal;
    public boolean isCarrierMerged;
    public boolean isDefaultNetwork;
    public int level;
    public final C09473 mCacheListener;
    public final Runnable mCallback;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final C09462 mDefaultNetworkCallback;
    public NetworkCapabilities mDefaultNetworkCapabilities;
    public final Handler mHandler;
    public final String[] mHistory;
    public int mHistoryIndex;
    public final Handler mMainThreadHandler;
    public final C09451 mNetworkCallback;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$1 */
    public final class C09451 extends ConnectivityManager.NetworkCallback {
        public C09451(int i) {
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
                }
            } else {
                if (!((HashSet) WifiStatusTracker.this.mNetworks).contains(Integer.valueOf(network.getNetId()))) {
                    ((HashSet) WifiStatusTracker.this.mNetworks).add(Integer.valueOf(network.getNetId()));
                }
                WifiStatusTracker.this.mPrimaryNetworkId = network.getNetId();
                WifiStatusTracker.m368$$Nest$mupdateWifiInfo(WifiStatusTracker.this, mainOrUnderlyingWifiInfo);
                WifiStatusTracker.this.updateStatusLabel();
                WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$1$$ExternalSyntheticLambda0(this, 0));
            }
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
            WifiStatusTracker.m368$$Nest$mupdateWifiInfo(wifiStatusTracker2, null);
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new WifiStatusTracker$1$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$2 */
    public final class C09462 extends ConnectivityManager.NetworkCallback {
        public C09462(int i) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$3 */
    public final class C09473 extends WifiNetworkScoreCache.CacheListener {
        public C09473(Handler handler) {
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
    public static void m368$$Nest$mupdateWifiInfo(WifiStatusTracker wifiStatusTracker, WifiInfo wifiInfo) {
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
            if (SemOpBrandingLoader.SemVendor.KTT == SemOpBrandingLoader.getInstance().getOpBranding()) {
                updateCarrierWifi(wifiStatusTracker.mWifiInfo);
            }
        }
    }

    public WifiStatusTracker(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Runnable runnable) {
        this(context, wifiManager, networkScoreManager, connectivityManager, runnable, null, null);
    }

    public static WifiInfo getMainWifiInfo(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        boolean z = true;
        if (!networkCapabilities.hasTransport(1) && !networkCapabilities.hasTransport(0)) {
            z = false;
        }
        if (!z) {
            return null;
        }
        VcnTransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (transportInfo instanceof VcnTransportInfo) {
            return transportInfo.getWifiInfo();
        }
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        return null;
    }

    public static void updateCarrierWifi(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            Log.d("WifiStatusTracker", "updateCarrierWifi - wifiInfo is null");
            return;
        }
        List<ScanResult.InformationElement> informationElements = wifiInfo.getInformationElements();
        if (informationElements == null) {
            Log.d("WifiStatusTracker", "not exist current network's InformationElement");
            return;
        }
        for (ScanResult.InformationElement informationElement : informationElements) {
            int id = informationElement.getId();
            if (id == 221) {
                try {
                    int frequency = wifiInfo.getFrequency();
                    if (frequency >= 5160 && frequency <= 5885) {
                        ByteBuffer order = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                        if (order.getInt() == 297998080) {
                            int remaining = order.remaining();
                            byte[] bArr = new byte[remaining];
                            order.get(bArr);
                            if (remaining > 24) {
                                byte b = bArr[24];
                            }
                        }
                    }
                } catch (BufferUnderflowException unused) {
                    Log.e("WifiStatusTracker", wifiInfo.getSSID() + " BufferUnderflowException ie:" + id);
                }
            }
        }
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

    public final void postResults() {
        this.mCallback.run();
    }

    public final void updateRssi(int i) {
        this.rssi = i;
        this.level = i <= -89 ? 0 : (i <= -89 || i > -83) ? (i <= -83 || i > -75) ? (i <= -75 || i > -64) ? 4 : 3 : 2 : 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateStatusLabel() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        ScoredNetwork scoredNetwork;
        String speedLabel;
        NetworkCapabilities networkCapabilities2;
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        NetworkCapabilities networkCapabilities3 = this.mDefaultNetworkCapabilities;
        if (networkCapabilities3 != null) {
            if (getMainOrUnderlyingWifiInfo(networkCapabilities3) != null || networkCapabilities3.hasTransport(1)) {
                z = true;
                this.isDefaultNetwork = z;
                networkCapabilities = !z ? this.mDefaultNetworkCapabilities : this.mConnectivityManager.getNetworkCapabilities(wifiManager.getCurrentNetwork());
                this.isCaptivePortal = false;
                Context context = this.mContext;
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasCapability(17)) {
                        this.statusLabel = context.getString(R.string.wifi_status_sign_in_required);
                        this.isCaptivePortal = true;
                        return;
                    }
                    if (networkCapabilities.hasCapability(24)) {
                        this.statusLabel = context.getString(R.string.wifi_limited_connection);
                        return;
                    }
                    if (!networkCapabilities.hasCapability(16)) {
                        Settings.Global.getString(context.getContentResolver(), "private_dns_mode");
                        if (networkCapabilities.isPrivateDnsBroken()) {
                            this.statusLabel = context.getString(R.string.private_dns_broken);
                            return;
                        } else {
                            this.statusLabel = context.getString(R.string.wifi_status_no_internet);
                            return;
                        }
                    }
                    if (!this.isDefaultNetwork && (networkCapabilities2 = this.mDefaultNetworkCapabilities) != null && networkCapabilities2.hasTransport(0)) {
                        this.statusLabel = context.getString(R.string.wifi_connected_low_quality);
                        return;
                    }
                }
                scoredNetwork = this.mWifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(this.mWifiInfo));
                if (scoredNetwork != null) {
                    speedLabel = null;
                } else {
                    int i = this.rssi;
                    int i2 = AccessPoint.$r8$clinit;
                    int calculateBadge = scoredNetwork.calculateBadge(i);
                    speedLabel = AccessPoint.getSpeedLabel(calculateBadge >= 5 ? calculateBadge < 7 ? 5 : calculateBadge < 15 ? 10 : calculateBadge < 25 ? 20 : 30 : 0, context);
                }
                this.statusLabel = speedLabel;
            }
        }
        z = false;
        this.isDefaultNetwork = z;
        if (!z) {
        }
        this.isCaptivePortal = false;
        Context context2 = this.mContext;
        if (networkCapabilities != null) {
        }
        scoredNetwork = this.mWifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(this.mWifiInfo));
        if (scoredNetwork != null) {
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
        this.mNetworkCallback = new C09451(1);
        this.mDefaultNetworkCallback = new C09462(1);
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
        this.mCacheListener = new C09473(this.mHandler);
    }
}
