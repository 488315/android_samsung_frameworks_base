package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.sec.ims.IMSParameter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SavedNetworkTracker extends BaseWifiTracker {
    public final Object mLock;
    public final Map mPasspointWifiEntryCache;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;
    public final List mSubscriptionWifiEntries;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SavedNetworkTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
    }

    public SavedNetworkTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedNetworkTrackerCallback savedNetworkTrackerCallback) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedNetworkTrackerCallback);
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(wifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        updateStandardWifiEntryScans(scanResultUpdater.getScanResults(j));
        updatePasspointWifiEntryScans(scanResultUpdater.getScanResults(j));
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        WifiManager wifiManager = this.mWifiManager;
        updateStandardWifiEntryConfigs(wifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(wifiManager.getPasspointConfigurations());
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateConnectivityReport(connectivityReport);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkLost() {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            WifiEntry wifiEntry = (WifiEntry) it.next();
            synchronized (wifiEntry) {
                wifiEntry.mDefaultNetworkCapabilities = null;
                wifiEntry.mIsDefaultNetwork = false;
                wifiEntry.notifyOnUpdated();
            }
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
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries();
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
        ((ArrayList) this.mStandardWifiEntryCache).clear();
        ((ArrayMap) this.mPasspointWifiEntryCache).clear();
        WifiManager wifiManager = this.mWifiManager;
        updateStandardWifiEntryConfigs(wifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(wifiManager.getPasspointConfigurations());
        this.mScanResultUpdater.update(wifiManager.getScanResults());
        conditionallyUpdateScanResults(true);
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
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
        updateWifiEntries();
    }

    public final void updatePasspointWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().collect(Collectors.toMap(new SavedNetworkTracker$$ExternalSyntheticLambda0(1), Function.identity()));
        ArrayMap arrayMap = (ArrayMap) this.mPasspointWifiEntryCache;
        arrayMap.entrySet().removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda2(0, map));
        for (String str : map.keySet()) {
            arrayMap.put(str, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (PasspointConfiguration) map.get(str), this.mWifiManager, true));
        }
    }

    public final void updatePasspointWifiEntryScans(List list) {
        Map map;
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        Iterator it = this.mWifiManager.getAllMatchingWifiConfigs(list).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            map = this.mPasspointWifiEntryCache;
            if (!hasNext) {
                break;
            }
            Pair pair = (Pair) it.next();
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            String uniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            ArrayMap arrayMap = (ArrayMap) map;
            if (arrayMap.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                ((PasspointWifiEntry) arrayMap.get(uniqueIdToPasspointWifiEntryKey)).updateScanResultInfo(wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1));
            }
        }
        for (PasspointWifiEntry passpointWifiEntry : ((ArrayMap) map).values()) {
            if (!treeSet.contains(passpointWifiEntry.mKey)) {
                passpointWifiEntry.updateScanResultInfo(null, null, null);
            }
        }
    }

    public final void updateStandardWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().filter(new SavedNetworkTracker$$ExternalSyntheticLambda3()).collect(Collectors.groupingBy(new SavedNetworkTracker$$ExternalSyntheticLambda0(2)));
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        arrayList.removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda2(1, map));
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : map.keySet()) {
            arrayList.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) map.get(standardWifiEntryKey), null, this.mWifiManager, true));
        }
    }

    public final void updateStandardWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Map map = (Map) list.stream().collect(Collectors.groupingBy(new SavedNetworkTracker$$ExternalSyntheticLambda0(0)));
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new Consumer() { // from class: com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateScanResultInfo((List) map.get(standardWifiEntry.mKey.mScanResultKey));
            }
        });
    }

    public final void updateWifiEntries() {
        synchronized (this.mLock) {
            ((ArrayList) this.mSavedWifiEntries).clear();
            ((ArrayList) this.mSavedWifiEntries).addAll(this.mStandardWifiEntryCache);
            List list = this.mSavedWifiEntries;
            Comparator comparator = WifiEntry.TITLE_COMPARATOR;
            Collections.sort(list, comparator);
            ((ArrayList) this.mSubscriptionWifiEntries).clear();
            ((ArrayList) this.mSubscriptionWifiEntries).addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
            Collections.sort(this.mSubscriptionWifiEntries, comparator);
            if (BaseWifiTracker.sVerboseLogging) {
                Arrays.toString(((ArrayList) this.mSavedWifiEntries).toArray());
                Arrays.toString(((ArrayList) this.mSubscriptionWifiEntries).toArray());
            }
        }
    }

    public SavedNetworkTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedNetworkTrackerCallback savedNetworkTrackerCallback) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedNetworkTrackerCallback, "SavedNetworkTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mSubscriptionWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mPasspointWifiEntryCache = new ArrayMap();
    }
}
