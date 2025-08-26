package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.text.BidiFormatter;
import com.android.systemui.R;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class KnownNetworkEntry extends StandardWifiEntry {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final KnownNetwork mKnownNetworkData;
    public final SharedConnectivityManager mSharedConnectivityManager;

    public KnownNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, KnownNetwork knownNetwork) {
        super(wifiTrackerInjector, handler, standardWifiEntryKey, wifiManager, false);
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mKnownNetworkData = knownNetwork;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.connectKnownNetwork(this.mKnownNetworkData);
        } else {
            if (connectCallback != null) {
                this.mCallbackHandler.post(new KnownNetworkEntry$$ExternalSyntheticLambda0(connectCallback, 1));
            }
        }
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            return Objects.equals(this.mKey.mScanResultKey, new StandardWifiEntry.StandardWifiEntryKey(new StandardWifiEntry.ScanResultKey(WifiInfo.sanitizeSsid(wifiInfo.getSSID()), Collections.singletonList(Integer.valueOf(wifiInfo.getCurrentSecurityType()))), false).mScanResultKey);
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        return this.mContext.getString(R.string.wifitrackerlib_known_network_summary, BidiFormatter.getInstance().unicodeWrap(this.mKnownNetworkData.getNetworkProviderInfo().getDeviceName()));
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSaved() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSuggestion() {
        return false;
    }

    public KnownNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, List<WifiConfiguration> list, List<ScanResult> list2, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, KnownNetwork knownNetwork) throws IllegalArgumentException {
        super(wifiTrackerInjector, handler, standardWifiEntryKey, list, list2, wifiManager, false);
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mKnownNetworkData = knownNetwork;
    }
}
