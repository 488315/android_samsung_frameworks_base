package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.samsung.android.wifi.SemWifiConfiguration;
import java.time.Clock;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StandardNetworkDetailsTracker extends NetworkDetailsTracker {
    public final StandardWifiEntry mChosenEntry;
    public final StandardWifiEntry.StandardWifiEntryKey mKey;

    public StandardNetworkDetailsTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, String str) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, str);
    }

    public final void conditionallyUpdateConfig() {
        final int i = 1;
        List list = (List) this.mWifiManager.getPrivilegedConfiguredNetworks().stream().filter(new StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(this, 1)).collect(Collectors.toList());
        StandardWifiEntry standardWifiEntry = this.mChosenEntry;
        standardWifiEntry.updateConfig(list);
        final int i2 = 0;
        standardWifiEntry.semUpdateSemWifiConfig((Map) this.mSemWifiManager.getConfiguredNetworks().stream().collect(Collectors.toMap(new Function() { // from class: com.android.wifitrackerlib.StandardNetworkDetailsTracker$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return ((SemWifiConfiguration) obj).configKey;
                    default:
                        return (SemWifiConfiguration) obj;
                }
            }
        }, new Function() { // from class: com.android.wifitrackerlib.StandardNetworkDetailsTracker$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        return ((SemWifiConfiguration) obj).configKey;
                    default:
                        return (SemWifiConfiguration) obj;
                }
            }
        })));
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        WifiManager wifiManager = this.mWifiManager;
        int wifiState = wifiManager.getWifiState();
        StandardWifiEntry standardWifiEntry = this.mChosenEntry;
        if (wifiState == 1) {
            standardWifiEntry.updateScanResultInfo(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(wifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        standardWifiEntry.updateScanResultInfo((List) scanResultUpdater.getScanResults(j).stream().filter(new StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(this, 0)).collect(Collectors.toList()));
    }

    @Override // com.android.wifitrackerlib.NetworkDetailsTracker
    public final WifiEntry getWifiEntry() {
        return this.mChosenEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateConfig();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        updateStartInfo();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
    }

    public final void updateStartInfo() {
        conditionallyUpdateScanResults(true);
        conditionallyUpdateConfig();
        SubscriptionManager.getDefaultDataSubscriptionId();
        WifiManager wifiManager = this.mWifiManager;
        Network currentNetwork = wifiManager.getCurrentNetwork();
        if (currentNetwork != null) {
            ConnectivityManager connectivityManager = this.mConnectivityManager;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(wifiManager.getConnectionInfo()).build());
            }
            LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
    }

    public StandardNetworkDetailsTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, String str) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, "StandardNetworkDetailsTracker");
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(str);
        this.mKey = standardWifiEntryKey;
        if (standardWifiEntryKey.mIsNetworkRequest) {
            this.mChosenEntry = new NetworkRequestEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, this.mWifiManager, false);
        } else {
            this.mChosenEntry = new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, this.mWifiManager, false);
        }
        updateStartInfo();
    }
}
