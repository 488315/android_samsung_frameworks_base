package com.android.wifitrackerlib;

import android.content.Context;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KnownNetworkEntry extends StandardWifiEntry {
    public final KnownNetwork mKnownNetworkData;
    public final SharedConnectivityManager mSharedConnectivityManager;

    public KnownNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, KnownNetwork knownNetwork) {
        super(wifiTrackerInjector, context, handler, standardWifiEntryKey, wifiManager, false);
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mKnownNetworkData = knownNetwork;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(final WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.connectKnownNetwork(this.mKnownNetworkData);
            return;
        }
        if (connectCallback != null) {
            final int i = 1;
            this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.KnownNetworkEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            ((KnownNetworkEntry) connectCallback).mConnectCallback.onConnectResult(2);
                            break;
                        default:
                            ((WifiEntry.ConnectCallback) connectCallback).onConnectResult(2);
                            break;
                    }
                }
            });
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

    public KnownNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, List<WifiConfiguration> list, List<ScanResult> list2, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, KnownNetwork knownNetwork) {
        super(wifiTrackerInjector, context, handler, standardWifiEntryKey, list, list2, wifiManager, false);
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mKnownNetworkData = knownNetwork;
    }
}
