package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import java.time.Clock;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class HotspotNetworkDetailsTracker extends NetworkDetailsTracker {
    public final HotspotNetworkEntry mChosenEntry;

    public HotspotNetworkDetailsTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, String str) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, str);
    }

    @Override // com.android.wifitrackerlib.NetworkDetailsTracker
    public final WifiEntry getWifiEntry() {
        return this.mChosenEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        updateStartInfo();
    }

    public final void updateStartInfo() {
        SubscriptionManager.getDefaultDataSubscriptionId();
        WifiManager wifiManager = this.mWifiManager;
        Network currentNetwork = wifiManager.getCurrentNetwork();
        if (currentNetwork == null) {
            return;
        }
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

    public HotspotNetworkDetailsTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, String str) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, "HotspotNetworkDetailsTracker");
        HotspotNetworkEntry.HotspotNetworkEntryKey hotspotNetworkEntryKey = new HotspotNetworkEntry.HotspotNetworkEntryKey(str);
        if (hotspotNetworkEntryKey.mIsVirtualEntry) {
            Log.e("HotspotNetworkDetailsTracker", "Network details not relevant for virtual entry");
        }
        HotspotNetworkEntry hotspotNetworkEntry = new HotspotNetworkEntry(this.mInjector, this.mContext, this.mMainHandler, this.mWifiManager, (SharedConnectivityManager) null, hotspotNetworkEntryKey);
        this.mChosenEntry = hotspotNetworkEntry;
        updateStartInfo();
        Objects.toString(hotspotNetworkEntry);
    }
}
