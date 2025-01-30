package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;
import com.sec.ims.IMSParameter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SemSavedNetworkTracker extends BaseWifiTracker {
    public final Object mLock;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SavedNetworkTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
    }

    public SemSavedNetworkTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedNetworkTrackerCallback savedNetworkTrackerCallback, NetworkFilter networkFilter) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedNetworkTrackerCallback, networkFilter);
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateLinkProperties(network, linkProperties);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries();
        throw null;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries();
        throw null;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        arrayList.clear();
        WifiManager wifiManager = this.mWifiManager;
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        Preconditions.checkNotNull(configuredNetworks, "Config list should not be null!");
        final Map map = (Map) configuredNetworks.stream().filter(new SemSavedNetworkTracker$$ExternalSyntheticLambda0()).collect(Collectors.groupingBy(new SemSavedNetworkTracker$$ExternalSyntheticLambda1()));
        arrayList.removeIf(new Predicate() { // from class: com.samsung.android.wifitrackerlib.SemSavedNetworkTracker$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) map.remove(standardWifiEntry.mKey));
                return !standardWifiEntry.isSaved();
            }
        });
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (StandardWifiEntry.StandardWifiEntryKey) entry.getKey(), (List) map.get(entry.getKey()), null, this.mWifiManager, true));
        }
        Network currentNetwork = wifiManager.getCurrentNetwork();
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (currentNetwork != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(wifiManager.getConnectionInfo()).build());
                throw null;
            }
            LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(currentNetwork);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        updateWifiEntries();
        throw null;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        updateWifiEntries();
        throw null;
    }

    public final void updateWifiEntries() {
        synchronized (this.mLock) {
            ((ArrayList) this.mSavedWifiEntries).clear();
            ((ArrayList) this.mSavedWifiEntries).addAll(this.mStandardWifiEntryCache);
            throw null;
        }
    }

    public SemSavedNetworkTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedNetworkTrackerCallback savedNetworkTrackerCallback, NetworkFilter networkFilter) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedNetworkTrackerCallback, "SemSavedNetworkTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
    }
}
