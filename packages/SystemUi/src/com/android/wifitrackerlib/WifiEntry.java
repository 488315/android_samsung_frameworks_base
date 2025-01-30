package com.android.wifitrackerlib;

import android.net.ConnectivityDiagnosticsManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Handler;
import android.util.Log;
import androidx.core.util.Preconditions;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifi.SemOpBrandingLoader;
import com.samsung.android.wifi.SemWifiConfiguration;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WifiEntry {
    public static final Comparator TITLE_COMPARATOR;
    public static final Comparator WIFI_PICKER_COMPARATOR;
    public static final Comparator WIFI_PICKER_COMPARATOR_ALPHABETICAL;
    public static final Comparator WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY;
    public static final Comparator WIFI_PICKER_COMPARATOR_RSSI;
    public int mBand;
    public String mBssid;
    public final Handler mCallbackHandler;
    public ConnectCallback mConnectCallback;
    public ConnectedInfo mConnectedInfo;
    public ConnectivityDiagnosticsManager.ConnectivityReport mConnectivityReport;
    public NetworkCapabilities mDefaultNetworkCapabilities;
    public int mFrequency;
    public boolean mIsDefaultNetwork;
    public WifiEntryCallback mListener;
    public final LogUtils mLog;
    public Network mNetwork;
    public NetworkCapabilities mNetworkCapabilities;
    public NetworkInfo mNetworkInfo;
    public int mRssi;
    public final SemWifiEntryFlags mSemFlags;
    public int mSpeed;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public int mLevel = -1;
    public boolean mCalledConnect = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wifitrackerlib.WifiEntry$1 */
    public abstract /* synthetic */ class AbstractC37661 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConnectActionListener implements WifiManager.ActionListener {
        public ConnectActionListener() {
        }

        public final void onFailure(final int i) {
            WifiEntry.this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.WifiEntry$ConnectActionListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiEntry.ConnectActionListener connectActionListener = WifiEntry.ConnectActionListener.this;
                    int i2 = i;
                    WifiEntry.ConnectCallback connectCallback = WifiEntry.this.mConnectCallback;
                    if (connectCallback != null) {
                        connectCallback.onConnectResult(i2 == 0 ? 4 : 2);
                    }
                }
            });
        }

        public final void onSuccess() {
            WifiEntry wifiEntry;
            synchronized (WifiEntry.this) {
                wifiEntry = WifiEntry.this;
                wifiEntry.mCalledConnect = true;
            }
            wifiEntry.mCallbackHandler.postDelayed(new WifiEntry$$ExternalSyntheticLambda1(this, 3), 10000L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ConnectCallback {
        void onConnectResult(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface WifiEntryCallback {
        void onUpdated();
    }

    static {
        Comparator thenComparing = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda0(1)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(9)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(10)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(11)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(12)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(13));
        WifiEntry$$ExternalSyntheticLambda0 wifiEntry$$ExternalSyntheticLambda0 = new WifiEntry$$ExternalSyntheticLambda0(14);
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        WIFI_PICKER_COMPARATOR = thenComparing.thenComparing(wifiEntry$$ExternalSyntheticLambda0, comparator).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(15));
        WIFI_PICKER_COMPARATOR_ALPHABETICAL = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda0(16)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(17), comparator);
        WIFI_PICKER_COMPARATOR_RSSI = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda0(2)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(3)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(4));
        WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda0(5)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(6)).thenComparing(new WifiEntry$$ExternalSyntheticLambda0(7));
        TITLE_COMPARATOR = Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda0(8), comparator);
    }

    public WifiEntry(Handler handler, WifiManager wifiManager, boolean z) {
        Optional.empty();
        this.mSpeed = 0;
        this.mRssi = -127;
        this.mFrequency = 0;
        this.mBssid = "";
        Preconditions.checkNotNull(handler, "Cannot construct with null handler!");
        Preconditions.checkNotNull(wifiManager, "Cannot construct with null WifiManager!");
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

    public boolean canSignIn() {
        return false;
    }

    public final boolean checkWifi6EStandard(int i, int i2) {
        int i3 = SemWifiUtils.$r8$clinit;
        if (i == 5935 || (i >= 5955 && i <= 7115)) {
            if (i2 >= 6) {
                return true;
            }
            Log.e("WifiEntry.", "invalid Wi-Fi 6E network " + getKey() + " standard:" + i2);
        }
        return false;
    }

    public boolean connectionInfoMatches(WifiInfo wifiInfo) {
        return false;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WifiEntry) {
            return getKey().equals(((WifiEntry) obj).getKey());
        }
        return false;
    }

    public final synchronized void forceUpdateNetworkInfo(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (!connectionInfoMatches(wifiInfo)) {
            if (this.mNetworkInfo != null) {
                this.mNetworkInfo = null;
            }
        } else {
            this.mNetworkInfo = networkInfo;
            this.mWifiInfo = wifiInfo;
            notifyOnUpdated();
        }
    }

    public synchronized int getConnectedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo == null) {
            return 0;
        }
        switch (AbstractC37661.$SwitchMap$android$net$NetworkInfo$DetailedState[networkInfo.getDetailedState().ordinal()]) {
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

    public List getSecurityTypes() {
        return Collections.emptyList();
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

    public final boolean hasInternetAccess() {
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        return networkCapabilities != null && networkCapabilities.hasCapability(16);
    }

    public final int hashCode() {
        return getKey().hashCode();
    }

    public boolean isAutoJoinEnabled() {
        return false;
    }

    public final boolean isLowQuality() {
        NetworkCapabilities networkCapabilities;
        return isPrimaryNetwork() && (networkCapabilities = this.mNetworkCapabilities) != null && this.mDefaultNetworkCapabilities != null && networkCapabilities.hasCapability(16) && this.mDefaultNetworkCapabilities.hasTransport(0) && !this.mDefaultNetworkCapabilities.hasTransport(4);
    }

    public final boolean isPrimaryNetwork() {
        WifiInfo wifiInfo;
        if (getConnectedState() == 0) {
            return false;
        }
        return this.mNetworkInfo != null || ((wifiInfo = this.mWifiInfo) != null && wifiInfo.isPrimary());
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
            this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda1(this, 0));
        }
    }

    public final synchronized void onDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        onNetworkCapabilitiesChanged(network, networkCapabilities);
        this.mDefaultNetworkCapabilities = networkCapabilities;
        this.mIsDefaultNetwork = network.equals(this.mNetwork);
        notifyOnUpdated();
    }

    public synchronized void onNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        WifiInfo wifiInfo = Utils.getWifiInfo(networkCapabilities);
        if (wifiInfo == null) {
            return;
        }
        if (!connectionInfoMatches(wifiInfo)) {
            if (network.equals(this.mNetwork)) {
                onNetworkLost(network);
            }
            return;
        }
        this.mWifiInfo = wifiInfo;
        this.mNetwork = network;
        this.mNetworkCapabilities = networkCapabilities;
        int rssi = wifiInfo.getRssi();
        this.mRssi = rssi;
        if (rssi != -127) {
            this.mLevel = SemWifiUtils.calculateSignalLevel(rssi);
        }
        if (getConnectedState() == 2) {
            if (this.mCalledConnect) {
                this.mCalledConnect = false;
                this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda1(this, 1));
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
        notifyOnUpdated();
    }

    public final synchronized void onNetworkLost(Network network) {
        if (network.equals(this.mNetwork)) {
            this.mWifiInfo = null;
            this.mNetworkInfo = null;
            this.mNetworkCapabilities = null;
            this.mConnectedInfo = null;
            this.mConnectivityReport = null;
            this.mIsDefaultNetwork = false;
            updateSecurityTypes();
            notifyOnUpdated();
        }
    }

    public final synchronized void onPrimaryWifiInfoChanged(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (connectionInfoMatches(wifiInfo)) {
            this.mNetworkInfo = networkInfo;
            this.mWifiInfo = wifiInfo;
            notifyOnUpdated();
        } else {
            if (this.mNetworkInfo != null) {
                this.mNetworkInfo = null;
                notifyOnUpdated();
            }
        }
    }

    public void onUpdated() {
        notifyOnUpdated();
    }

    public final void semUpdateFlags(SemWifiConfiguration semWifiConfiguration) {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (semWifiConfiguration != null) {
            semWifiConfiguration.isLockDown();
            semWifiEntryFlags.getClass();
            semWifiConfiguration.isCaptivePortal();
            semWifiEntryFlags.semConfig = semWifiConfiguration;
        } else {
            semWifiEntryFlags.getClass();
        }
        notifyOnUpdated();
    }

    public final void semUpdateQoSInformation() {
        int i;
        int i2 = this.mSpeed;
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int i3 = semWifiEntryFlags.networkType;
        Map map = semWifiEntryFlags.qosScoredNetworkCache;
        if (((HashMap) map).isEmpty()) {
            return;
        }
        Iterator it = ((HashMap) map).values().iterator();
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiScoredNetwork wifiScoredNetwork = (WifiScoredNetwork) it.next();
            int i6 = this.mRssi;
            int[] iArr = wifiScoredNetwork.levels;
            int i7 = i6 >= -55 ? iArr[2] : (i6 >= -55 || i6 < -65) ? (i6 >= -65 || i6 < -75) ? -1 : iArr[0] : iArr[1];
            i = (i7 != 10 || i6 > -71) ? i7 : 5;
            if (i > 0) {
                i4++;
                i5 += i;
            }
        }
        int i8 = i4 == 0 ? 0 : i5 / i4;
        for (WifiScoredNetwork wifiScoredNetwork2 : ((HashMap) map).values()) {
            if (wifiScoredNetwork2.bssid.equals(this.mBssid)) {
                semWifiEntryFlags.networkType = wifiScoredNetwork2.networkType;
            }
        }
        if (i8 != 0) {
            Log.i("WifiEntry.", String.format("%s generated fallback speed is: %d networkType: %s", getSsid(), Integer.valueOf(i8), Integer.valueOf(semWifiEntryFlags.networkType)));
        }
        int i9 = SemWifiUtils.$r8$clinit;
        if (i8 == 0) {
            i = 0;
        } else if (i8 >= 7) {
            i = i8 < 15 ? 10 : i8 < 25 ? 20 : 30;
        }
        this.mSpeed = i;
        if ((i2 == i && i3 == semWifiEntryFlags.networkType) ? false : true) {
            Log.i("WifiEntry.", String.format("%s: Set speed to %d and NetworkType %s. set by Network Samsung QoS.", getSsid(), Integer.valueOf(this.mSpeed), Integer.valueOf(semWifiEntryFlags.networkType)));
        }
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
        return (getConnectedState() == 0 || this.mConnectivityReport == null || (hasInternetAccess() && !isLowQuality()) || canSignIn() || !isPrimaryNetwork()) ? false : true;
    }

    public final String toString() {
        ConnectedInfo connectedInfo;
        ConnectedInfo connectedInfo2;
        StringBuilder sb = new StringBuilder();
        sb.append(getKey());
        sb.append(",title:");
        sb.append(getTitle());
        sb.append(",summary:");
        sb.append(getSummary(true));
        sb.append(",isSaved:");
        sb.append(isSaved());
        sb.append(",isSubscription:");
        sb.append(isSubscription());
        sb.append(",isSuggestion:");
        sb.append(isSuggestion());
        sb.append(",level:");
        sb.append(this.mLevel);
        sb.append(shouldShowXLevelIcon() ? "X" : "");
        sb.append(",security:");
        sb.append(getSecurityTypes());
        sb.append(",connected:");
        sb.append(getConnectedState() == 2 ? "true" : "false");
        sb.append(",connectedInfo:");
        synchronized (this) {
            connectedInfo = (getConnectedState() == 2 && (connectedInfo2 = this.mConnectedInfo) != null) ? new ConnectedInfo(connectedInfo2) : null;
        }
        sb.append(connectedInfo);
        sb.append(",hasInternet:");
        sb.append(hasInternetAccess());
        sb.append(",isDefault:");
        sb.append(this.mIsDefaultNetwork);
        sb.append(",isPrimary:");
        sb.append(isPrimaryNetwork());
        return sb.toString();
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

    public final synchronized void updateConnectivityReport(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        if (connectivityReport.getNetwork().equals(this.mNetwork)) {
            this.mConnectivityReport = connectivityReport;
            notifyOnUpdated();
        }
    }

    public final synchronized void updateLinkProperties(Network network, LinkProperties linkProperties) {
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
            this.mConnectedInfo.dnsServers = (List) linkProperties.getDnsServers().stream().map(new WifiEntry$$ExternalSyntheticLambda0(0)).collect(Collectors.toList());
            notifyOnUpdated();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConnectedInfo {
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

    public final void semUpdateFlags(PasspointConfiguration passpointConfiguration) {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (passpointConfiguration != null) {
            HomeSp homeSp = passpointConfiguration.getHomeSp();
            semWifiEntryFlags.isOpenRoamingNetwork = "samsung.openroaming.net".equals(homeSp.getFqdn()) && "OpenRoaming".equals(homeSp.getFriendlyName());
        }
        semWifiEntryFlags.passpointConfiguration = passpointConfiguration;
    }

    public final void semUpdateFlags(ScanResult scanResult) {
        if (scanResult == null) {
            return;
        }
        int wifiStandard = scanResult.getWifiStandard();
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int max = Math.max(semWifiEntryFlags.wifiStandard, wifiStandard);
        semWifiEntryFlags.wifiStandard = max;
        if (!semWifiEntryFlags.has6EStandard) {
            semWifiEntryFlags.has6EStandard = checkWifi6EStandard(scanResult.frequency, max);
        }
        for (ScanResult.InformationElement informationElement : scanResult.getInformationElements()) {
            int id = informationElement.getId();
            int i = 0;
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
                int remaining = informationElement.getBytes().remaining();
                byte[] bArr = new byte[remaining];
                if (remaining > 8 || remaining < 1) {
                    return;
                }
                try {
                    ByteBuffer order = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    while (i < remaining && (order.get() & Byte.MAX_VALUE) != 123) {
                        i++;
                    }
                } catch (BufferUnderflowException unused2) {
                    Log.e("WifiEntry.", logUtils.getPrintableLog(scanResult.BSSID) + " BufferUnderflowException ie:" + id);
                }
            } else if (id == 221) {
                try {
                    ByteBuffer order2 = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    int i2 = order2.getInt();
                    if ((16777215 & i2) == 3282432) {
                        if ((i2 >>> 24) == 128) {
                            semWifiEntryFlags.isSamsungMobileHotspot = true;
                        }
                    } else if (SemOpBrandingLoader.SemVendor.KTT == SemWifiEntryFlags.getOpBranding() && scanResult.frequency > 4900 && i2 == 297998080) {
                        int remaining2 = order2.remaining();
                        byte[] bArr2 = new byte[remaining2];
                        order2.get(bArr2);
                        if (remaining2 > 24 && bArr2[24] == 1) {
                            i = 1;
                        }
                        if (i != 0) {
                            semWifiEntryFlags.hasVHTVSICapabilities = true;
                        }
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
                int remaining3 = informationElement.getBytes().remaining();
                byte[] bArr3 = new byte[remaining3];
                if (remaining3 > 8 || remaining3 < 1) {
                    return;
                }
                try {
                    int i3 = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).get() & 32;
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
