package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.TransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class WifiEntry {
    public static final Comparator WIFI_PICKER_COMPARATOR;
    public static final Comparator WIFI_PICKER_COMPARATOR_ALPHABETICAL;
    public static final Comparator WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY;
    public static final Comparator WIFI_PICKER_COMPARATOR_RSSI;
    public int mBand;
    public final Handler mCallbackHandler;
    public ConnectCallback mConnectCallback;
    public ConnectedInfo mConnectedInfo;
    public ConnectivityDiagnosticsManager.ConnectivityReport mConnectivityReport;
    public final Context mContext;
    public Network mDefaultNetwork;
    public NetworkCapabilities mDefaultNetworkCapabilities;
    public final WifiTrackerInjector mInjector;
    public Network mLastNetwork;
    public WifiEntryCallback mListener;
    public final LogUtils mLog;
    public Network mNetwork;
    public NetworkCapabilities mNetworkCapabilities;
    public NetworkInfo mNetworkInfo;
    public final SemWifiEntryFlags mSemFlags;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public int mRssi = -127;
    public int mSpeed = 0;
    public int mFrequency = 0;
    public String mBssid = "";
    public int mWifiInfoLevel = -1;
    public int mScanResultLevel = -1;
    public boolean mCalledConnect = false;

    /* renamed from: com.android.wifitrackerlib.WifiEntry$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState;

        static {
            int[] iArr = new int[NetworkInfo.DetailedState.values().length];
            $SwitchMap$android$net$NetworkInfo$DetailedState = iArr;
            try {
                iArr[NetworkInfo.DetailedState.SCANNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CONNECTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.AUTHENTICATING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.OBTAINING_IPADDR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.VERIFYING_POOR_LINK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CAPTIVE_PORTAL_CHECK.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CONNECTED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public class ConnectActionListener implements WifiManager.ActionListener {
        public ConnectActionListener() {
        }

        public final void onFailure(final int i) {
            WifiEntry.this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.WifiEntry$ConnectActionListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiEntry.ConnectActionListener connectActionListener = this.f$0;
                    int i2 = i;
                    WifiEntry.ConnectCallback connectCallback = WifiEntry.this.mConnectCallback;
                    if (connectCallback != null) {
                        connectCallback.onConnectResult(i2 == 0 ? 4 : 2);
                    }
                }
            });
        }

        public final void onSuccess() {
            synchronized (WifiEntry.this) {
                WifiEntry.this.mCalledConnect = true;
            }
        }
    }

    public interface ConnectCallback {
        void onConnectResult(int i);
    }

    public interface WifiEntryCallback {
        void onUpdated();
    }

    static {
        Comparator comparatorThenComparing = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda1(0)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(15)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(1)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(2)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(3)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(4));
        WifiEntry$$ExternalSyntheticLambda1 wifiEntry$$ExternalSyntheticLambda1 = new WifiEntry$$ExternalSyntheticLambda1(8);
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        WIFI_PICKER_COMPARATOR = comparatorThenComparing.thenComparing(wifiEntry$$ExternalSyntheticLambda1, comparator).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(5));
        Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda1(8), comparator);
        WIFI_PICKER_COMPARATOR_ALPHABETICAL = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda1(6)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(8), comparator);
        WIFI_PICKER_COMPARATOR_RSSI = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda1(9)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(10)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(11));
        WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda1(12)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(13)).thenComparing(new WifiEntry$$ExternalSyntheticLambda1(14));
    }

    public WifiEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        Optional.empty();
        Preconditions.checkNotNull(wifiTrackerInjector, "Cannot construct with null injector!");
        Preconditions.checkNotNull(handler, "Cannot construct with null handler!");
        Preconditions.checkNotNull(wifiManager, "Cannot construct with null WifiManager!");
        this.mInjector = wifiTrackerInjector;
        this.mContext = wifiTrackerInjector.mContext;
        this.mCallbackHandler = handler;
        this.mWifiManager = wifiManager;
        this.mSemFlags = new SemWifiEntryFlags();
        this.mLog = new LogUtils();
    }

    public boolean canConnect() {
        return false;
    }

    public boolean canDisconnect() {
        return false;
    }

    public boolean canSetAutoJoinEnabled() {
        return false;
    }

    public boolean canShare() {
        return false;
    }

    public boolean canSignIn() {
        return false;
    }

    public final boolean checkWifi6EStandard(int i, int i2) {
        int i3 = SemWifiUtils.$r8$clinit;
        if (i < 5925 || i > 7125) {
            return false;
        }
        if (i2 >= 6) {
            return true;
        }
        Log.e("WifiEntry.", "invalid Wi-Fi 6E network " + getKey() + " standard:" + i2);
        return false;
    }

    public final synchronized void clearConnectionInfo(boolean z) {
        updateWifiInfo(null);
        this.mNetwork = null;
        this.mLastNetwork = null;
        this.mNetworkInfo = null;
        this.mNetworkCapabilities = null;
        this.mConnectivityReport = null;
        if (z) {
            notifyOnUpdated();
        }
    }

    public boolean connectionInfoMatches(WifiInfo wifiInfo) {
        return false;
    }

    public final boolean doesUnderlyingNetworkMatch(NetworkCapabilities networkCapabilities, int i) {
        if (i > 5) {
            Log.e("WifiEntry.", "Underlying network depth greater than max depth of 5");
            return false;
        }
        if (networkCapabilities == null) {
            return false;
        }
        int i2 = BuildCompat.$r8$clinit;
        List underlyingNetworks = networkCapabilities.getUnderlyingNetworks();
        if (underlyingNetworks == null) {
            return false;
        }
        if (underlyingNetworks.contains(this.mNetwork)) {
            return true;
        }
        ConnectivityManager connectivityManager = this.mInjector.mConnectivityManager;
        if (connectivityManager == null) {
            Log.wtf("WifiEntry.", "ConnectivityManager is null!");
            return false;
        }
        Iterator it = underlyingNetworks.iterator();
        while (it.hasNext()) {
            if (doesUnderlyingNetworkMatch(connectivityManager.getNetworkCapabilities((Network) it.next()), i + 1)) {
                return true;
            }
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WifiEntry) {
            return getKey().equals(((WifiEntry) obj).getKey());
        }
        return false;
    }

    public final synchronized void forceUpdateNetworkInfo(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (wifiInfo != null) {
            if (connectionInfoMatches(wifiInfo)) {
                this.mNetworkInfo = networkInfo;
                this.mWifiInfo = wifiInfo;
                notifyOnUpdated();
                return;
            }
        }
        if (this.mNetworkInfo != null) {
            this.mNetworkInfo = null;
        }
    }

    public synchronized int getConnectedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo == null) {
            return 0;
        }
        switch (AnonymousClass1.$SwitchMap$android$net$NetworkInfo$DetailedState[networkInfo.getDetailedState().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 1;
            case 7:
                return 2;
            default:
                return 0;
        }
    }

    public String getKey() {
        return "";
    }

    public int getLevel() {
        int i = this.mWifiInfoLevel;
        return i != -1 ? i : this.mScanResultLevel;
    }

    public String getNetworkSelectionDescription() {
        return "";
    }

    public String getScanResultDescription() {
        return "";
    }

    public int getSecurity() {
        switch (Utils.getSingleSecurityTypeFromMultipleSecurityTypes(getSecurityTypes())) {
        }
        return 3;
    }

    public String getSecurityString() {
        return "";
    }

    public List getSecurityTypes() {
        return Collections.EMPTY_LIST;
    }

    public String getSsid() {
        return null;
    }

    public String getStandardString() {
        return "";
    }

    public String getSummary(boolean z) {
        return "";
    }

    public String getTitle() {
        return "";
    }

    public WifiConfiguration getWifiConfiguration() {
        return null;
    }

    public boolean hasAdminRestrictions() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean hasInternetAccess() {
        boolean z;
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        if (networkCapabilities != null) {
            z = networkCapabilities.hasCapability(16);
        }
        return z;
    }

    public final int hashCode() {
        return getKey().hashCode();
    }

    public boolean isAutoJoinEnabled() {
        return false;
    }

    public final synchronized boolean isDefaultNetwork() {
        Network network = this.mNetwork;
        if (network != null && network.equals(this.mDefaultNetwork)) {
            return true;
        }
        Network network2 = this.mLastNetwork;
        if (network2 != null && network2.equals(this.mDefaultNetwork)) {
            return true;
        }
        return doesUnderlyingNetworkMatch(this.mDefaultNetworkCapabilities, 0);
    }

    public boolean isExpired() {
        return false;
    }

    public final synchronized boolean isLowQuality() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        NetworkCapabilities networkCapabilities2;
        z = false;
        if (isPrimaryNetwork() && hasInternetAccess() && !isDefaultNetwork() && (networkCapabilities = this.mNetworkCapabilities) != null && networkCapabilities.hasCapability(12) && (networkCapabilities2 = this.mDefaultNetworkCapabilities) != null && networkCapabilities2.hasTransport(0) && !this.mDefaultNetworkCapabilities.hasTransport(4)) {
            if (this.mDefaultNetworkCapabilities.hasCapability(13)) {
                z = true;
            }
        }
        return z;
    }

    public boolean isMetered() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean isPrimaryNetwork() {
        boolean z = false;
        if (getConnectedState() == 0) {
            return false;
        }
        if (this.mNetworkInfo != null) {
            z = true;
        } else {
            WifiInfo wifiInfo = this.mWifiInfo;
            if (wifiInfo != null) {
                if (wifiInfo.isPrimary()) {
                }
            }
        }
        return z;
    }

    public boolean isSaved() {
        return false;
    }

    public boolean isSubscription() {
        return false;
    }

    public boolean isSuggestion() {
        return false;
    }

    public final void notifyOnUpdated() {
        if (this.mListener != null) {
            this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public synchronized void onNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        List list = Utils.defaultSsidList;
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        WifiInfo wifiInfo = transportInfo instanceof WifiInfo ? (WifiInfo) transportInfo : null;
        if (wifiInfo == null) {
            return;
        }
        if (!connectionInfoMatches(wifiInfo)) {
            onNetworkLost(network);
            return;
        }
        Network network2 = this.mNetwork;
        if (network2 != network) {
            this.mLastNetwork = network2;
            this.mNetwork = network;
        }
        this.mNetworkCapabilities = networkCapabilities;
        updateWifiInfo(wifiInfo);
        notifyOnUpdated();
    }

    public final synchronized void onNetworkLost(Network network) {
        try {
            if (network.equals(this.mNetwork)) {
                clearConnectionInfo(true);
            } else if (network.equals(this.mLastNetwork)) {
                this.mLastNetwork = null;
                notifyOnUpdated();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void onPrimaryWifiInfoChanged(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (wifiInfo != null) {
            try {
                if (connectionInfoMatches(wifiInfo)) {
                    if (networkInfo != null) {
                        this.mNetworkInfo = networkInfo;
                    }
                    updateWifiInfo(wifiInfo);
                    notifyOnUpdated();
                    return;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mNetworkInfo != null) {
            this.mNetworkInfo = null;
            notifyOnUpdated();
        }
    }

    public void onUpdated() {
        notifyOnUpdated();
    }

    public final boolean semIsEphemeral() {
        NetworkInfo networkInfo;
        WifiInfo wifiInfo = this.mWifiInfo;
        return (wifiInfo == null || !wifiInfo.isEphemeral() || (networkInfo = this.mNetworkInfo) == null || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) ? false : true;
    }

    public final void semUpdateFlags(PasspointConfiguration passpointConfiguration) {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (passpointConfiguration != null) {
            HomeSp homeSp = passpointConfiguration.getHomeSp();
            semWifiEntryFlags.isOpenRoamingNetwork = "samsung.openroaming.net".equals(homeSp.getFqdn()) && "OpenRoaming".equals(homeSp.getFriendlyName());
        }
        semWifiEntryFlags.passpointConfiguration = passpointConfiguration;
    }

    public final void semUpdateQoSInformation() {
        int i = this.mSpeed;
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int i2 = semWifiEntryFlags.networkType;
        if (((HashMap) semWifiEntryFlags.qosScoredNetworkCache).isEmpty()) {
            return;
        }
        Iterator it = ((HashMap) semWifiEntryFlags.qosScoredNetworkCache).values().iterator();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiScoredNetwork wifiScoredNetwork = (WifiScoredNetwork) it.next();
            int i5 = this.mRssi;
            int[] iArr = wifiScoredNetwork.levels;
            int i6 = i5 >= -55 ? iArr[2] : (i5 >= -55 || i5 < -65) ? (i5 >= -65 || i5 < -75) ? -1 : iArr[0] : iArr[1];
            int i7 = (i6 != 10 || i5 > -71) ? i6 : 5;
            if (i7 > 0) {
                i3++;
                i4 += i7;
            }
        }
        int i8 = i3 == 0 ? 0 : i4 / i3;
        for (WifiScoredNetwork wifiScoredNetwork2 : ((HashMap) semWifiEntryFlags.qosScoredNetworkCache).values()) {
            if (wifiScoredNetwork2.bssid.equals(this.mBssid)) {
                semWifiEntryFlags.networkType = wifiScoredNetwork2.networkType;
            }
        }
        if (i8 != 0) {
            Log.i("WifiEntry.", String.format("%s generated fallback speed is: %d networkType: %s", getSsid(), Integer.valueOf(i8), Integer.valueOf(semWifiEntryFlags.networkType)));
        }
        int i9 = SemWifiUtils.$r8$clinit;
        int i10 = i8 != 0 ? i8 < 7 ? 5 : i8 < 15 ? 10 : i8 < 25 ? 20 : 30 : 0;
        this.mSpeed = i10;
        if (i == i10 && i2 == semWifiEntryFlags.networkType) {
            return;
        }
        Log.i("WifiEntry.", String.format("%s: Set speed to %d and NetworkType %s. set by Network Samsung QoS.", getSsid(), Integer.valueOf(this.mSpeed), Integer.valueOf(semWifiEntryFlags.networkType)));
    }

    public final void setBand(int i) {
        this.mBand = 0;
        if (i >= 4900 && i < 5900) {
            this.mBand = 1;
            return;
        }
        if (i >= 5925 && i < 7125) {
            this.mBand = 2;
        } else {
            if (i < 58320 || i >= 70200) {
                return;
            }
            this.mBand = 3;
        }
    }

    public boolean shouldEditBeforeConnect() {
        return false;
    }

    public final boolean shouldShowXLevelIcon() {
        if (getConnectedState() == 0 || this.mConnectivityReport == null) {
            return false;
        }
        return (!hasInternetAccess() || isLowQuality()) && !canSignIn() && isPrimaryNetwork();
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add(getClass().getSimpleName());
        stringJoiner.add(getTitle());
        stringJoiner.add(getSummary(true));
        StringBuilder sb = new StringBuilder("Level:");
        sb.append(getLevel());
        sb.append(shouldShowXLevelIcon() ? "!" : "");
        stringJoiner.add(sb.toString());
        String securityString = getSecurityString();
        if (!TextUtils.isEmpty(securityString)) {
            stringJoiner.add(securityString);
        }
        int connectedState = getConnectedState();
        if (connectedState == 2) {
            stringJoiner.add("Connected");
        } else if (connectedState == 1) {
            stringJoiner.add("Connecting...");
        }
        if (hasInternetAccess()) {
            stringJoiner.add("Internet");
        }
        if (isDefaultNetwork()) {
            stringJoiner.add(SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT);
        }
        if (isPrimaryNetwork()) {
            stringJoiner.add("Primary");
        }
        if (isLowQuality()) {
            stringJoiner.add("LowQuality");
        }
        if (isSaved()) {
            stringJoiner.add("Saved");
        }
        if (isSubscription()) {
            stringJoiner.add("Subscription");
        }
        if (isSuggestion()) {
            stringJoiner.add("Suggestion");
        }
        if (isMetered()) {
            stringJoiner.add("Metered");
        }
        if ((isSaved() || isSuggestion() || isSubscription()) && !isAutoJoinEnabled()) {
            stringJoiner.add("AutoJoinDisabled");
        }
        if (isExpired()) {
            stringJoiner.add("Expired");
        }
        if (canSignIn()) {
            stringJoiner.add("SignIn");
        }
        if (shouldEditBeforeConnect()) {
            stringJoiner.add("EditBeforeConnect");
        }
        if (hasAdminRestrictions()) {
            stringJoiner.add("AdminRestricted");
        }
        return stringJoiner.toString();
    }

    public final void updateBestRssi(ScanResult scanResult) {
        int i;
        int i2 = scanResult.level;
        if (i2 == -127 || (i = this.mRssi) == -127) {
            this.mRssi = i2;
        } else {
            this.mRssi = (i + i2) / 2;
        }
    }

    public final synchronized void updateLinkProperties(Network network, LinkProperties linkProperties) {
        try {
            if (network.equals(this.mNetwork)) {
                if (this.mConnectedInfo == null) {
                    this.mConnectedInfo = new ConnectedInfo();
                }
                ArrayList arrayList = new ArrayList();
                for (LinkAddress linkAddress : linkProperties.getLinkAddresses()) {
                    if (linkAddress.getAddress() instanceof Inet4Address) {
                        ConnectedInfo connectedInfo = this.mConnectedInfo;
                        linkAddress.getAddress().getHostAddress();
                        connectedInfo.getClass();
                        try {
                            InetAddress byAddress = InetAddress.getByAddress(new byte[]{-1, -1, -1, -1});
                            ConnectedInfo connectedInfo2 = this.mConnectedInfo;
                            Utils.getNetworkPart(byAddress, linkAddress.getPrefixLength()).getHostAddress();
                            connectedInfo2.getClass();
                        } catch (IllegalArgumentException | UnknownHostException unused) {
                        }
                    } else if (linkAddress.getAddress() instanceof Inet6Address) {
                        arrayList.add(linkAddress.getAddress().getHostAddress());
                    }
                }
                this.mConnectedInfo.ipv6Addresses = arrayList;
                Iterator<RouteInfo> it = linkProperties.getRoutes().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RouteInfo next = it.next();
                    if (next.isDefaultRoute() && (next.getDestination().getAddress() instanceof Inet4Address) && next.hasGateway()) {
                        ConnectedInfo connectedInfo3 = this.mConnectedInfo;
                        next.getGateway().getHostAddress();
                        connectedInfo3.getClass();
                        break;
                    }
                }
                this.mConnectedInfo.dnsServers = (List) linkProperties.getDnsServers().stream().map(new WifiEntry$$ExternalSyntheticLambda1(7)).collect(Collectors.toList());
                notifyOnUpdated();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void updateWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            this.mWifiInfo = null;
            this.mConnectedInfo = null;
            this.mWifiInfoLevel = -1;
            updateSecurityTypes();
            return;
        }
        this.mWifiInfo = wifiInfo;
        int rssi = wifiInfo.getRssi();
        this.mRssi = rssi;
        if (rssi != -127) {
            this.mWifiInfoLevel = SemWifiUtils.calculateSignalLevel(rssi);
        }
        if (getConnectedState() == 2) {
            if (this.mCalledConnect) {
                this.mCalledConnect = false;
                this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda0(this, 1));
            }
            if (this.mConnectedInfo == null) {
                this.mConnectedInfo = new ConnectedInfo();
            }
            ConnectedInfo connectedInfo = this.mConnectedInfo;
            this.mWifiInfo.getFrequency();
            connectedInfo.getClass();
            ConnectedInfo connectedInfo2 = this.mConnectedInfo;
            this.mWifiInfo.getLinkSpeed();
            connectedInfo2.getClass();
            ConnectedInfo connectedInfo3 = this.mConnectedInfo;
            this.mWifiInfo.getWifiStandard();
            connectedInfo3.getClass();
        }
        updateSecurityTypes();
    }

    public class ConnectedInfo {
        public List dnsServers;
        public List ipv6Addresses;

        public ConnectedInfo() {
            this.dnsServers = new ArrayList();
            this.ipv6Addresses = new ArrayList();
        }

        public ConnectedInfo(ConnectedInfo connectedInfo) {
            this.dnsServers = new ArrayList();
            this.ipv6Addresses = new ArrayList();
            connectedInfo.getClass();
            this.dnsServers = new ArrayList(this.dnsServers);
            this.ipv6Addresses = new ArrayList(connectedInfo.ipv6Addresses);
        }
    }

    public final void semUpdateFlags(ScanResult scanResult) {
        if (scanResult == null) {
            return;
        }
        int wifiStandard = scanResult.getWifiStandard();
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int iMax = Math.max(semWifiEntryFlags.wifiStandard, wifiStandard);
        semWifiEntryFlags.wifiStandard = iMax;
        if (!semWifiEntryFlags.has6EStandard) {
            semWifiEntryFlags.has6EStandard = checkWifi6EStandard(scanResult.frequency, iMax);
        }
        for (ScanResult.InformationElement informationElement : scanResult.getInformationElements()) {
            int id = informationElement.getId();
            LogUtils logUtils = this.mLog;
            if (id == 11) {
                try {
                    semWifiEntryFlags.staCount = Math.max(semWifiEntryFlags.staCount, 0) + (informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).getShort() & 65535);
                } catch (BufferUnderflowException unused) {
                    Log.e("WifiEntry.", logUtils.getPrintableLog(scanResult.BSSID) + " BufferUnderflowException ie:" + id);
                }
            } else if (id == 50) {
                if (informationElement.getBytes() == null) {
                    return;
                }
                int iRemaining = informationElement.getBytes().remaining();
                byte[] bArr = new byte[iRemaining];
                if (iRemaining > 8 || iRemaining < 1) {
                    return;
                }
                try {
                    ByteBuffer byteBufferOrder = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    for (int i = 0; i < iRemaining && (byteBufferOrder.get() & Byte.MAX_VALUE) != 123; i++) {
                    }
                } catch (BufferUnderflowException unused2) {
                    Log.e("WifiEntry.", logUtils.getPrintableLog(scanResult.BSSID) + " BufferUnderflowException ie:" + id);
                }
            } else if (id == 221) {
                try {
                    int i2 = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).getInt();
                    if ((16777215 & i2) == 3282432 && (i2 >>> 24) == 128) {
                        semWifiEntryFlags.isSamsungMobileHotspot = true;
                    }
                } catch (BufferUnderflowException unused3) {
                    Log.e("WifiEntry.", logUtils.getPrintableLog(scanResult.BSSID) + " BufferUnderflowException ie:" + id);
                }
            } else if (id != 244) {
                continue;
            } else {
                if (informationElement.getBytes() == null) {
                    return;
                }
                int iRemaining2 = informationElement.getBytes().remaining();
                byte[] bArr2 = new byte[iRemaining2];
                if (iRemaining2 > 8 || iRemaining2 < 1) {
                    return;
                }
                try {
                    informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).get();
                } catch (BufferUnderflowException unused4) {
                    Log.e("WifiEntry.", logUtils.getPrintableLog(scanResult.BSSID) + " BufferUnderflowException ie:" + id);
                }
            }
        }
    }

    public void connect(ConnectCallback connectCallback) {
    }

    public void updateSecurityTypes() {
    }
}
