package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.samsung.android.wifi.SemEasySetupWifiScanSettings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.EasySetupUtils;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes3.dex */
public class WifiPickerTracker extends BaseWifiTracker {
    public List mActiveWifiEntries;
    public final List mAutoHotspotEntries;
    public final AtomicBoolean mConnected;
    public boolean mConnectedNetworkEverUpdated;
    public WifiEntry mConnectedWifiEntry;
    public final List mEasySetupCandidateEntries;
    public final List mEasySetupEntries;
    public final EasySetupUtils mEasySetupUtils;
    public final List mHotspotNetworkDataCache;
    public final List mHotspotNetworkEntryCache;
    public final boolean mIsSettingSupportEasySetup;
    public final boolean mIsSettingsTracker;
    public boolean mIsSupportEasySetup;
    public final List mKnownNetworkDataCache;
    public final List mKnownNetworkEntryCache;
    public WifiInfo mLastWifiInfo;
    public final WifiPickerTrackerCallback mListener;
    public final Object mLockAutoHotspot;
    public final Object mLockEasySetup;
    public MergedCarrierEntry mMergedCarrierEntry;
    public final ArrayMap mNetworkRequestConfigCache;
    public NetworkRequestEntry mNetworkRequestEntry;
    public final Map mOsuWifiEntryCache;
    public final Map mPasspointConfigCache;
    public final SparseArray mPasspointWifiConfigCache;
    public final Map mPasspointWifiEntryCache;
    public final List mSemEasySetupScanSettings;
    public final SemWifiEntryFilter mSemFilter;
    public final Map mStandardWifiConfigCache;
    public final List mStandardWifiEntryCache;
    public final Map mSuggestedConfigCache;
    public final List mSuggestedWifiEntryCache;
    public List mWifiEntries;

    public interface SemWifiPickerTrackerCallback {
    }

    public interface WifiPickerTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onWifiEntriesChanged();

        default void onWifiEntriesChanged(int i) {
            onWifiEntriesChanged();
        }
    }

    public WifiPickerTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, null, false);
    }

    public final void conditionallyCreateConnectedWifiEntry(WifiInfo wifiInfo) {
        WifiConfiguration wifiConfiguration;
        PasspointWifiEntry passpointWifiEntry;
        if (wifiInfo != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            final int networkId = wifiInfo.getNetworkId();
            Iterator it = ((ArrayMap) this.mStandardWifiConfigCache).values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                List list = (List) it.next();
                if (list.stream().map(new WifiPickerTracker$$ExternalSyntheticLambda1(5)).filter(new Predicate() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Integer) obj).intValue() == networkId;
                    }
                }).count() != 0) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list.get(0), true);
                    ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            ((ArrayList) this.mStandardWifiEntryCache).add(new StandardWifiEntry(this.mInjector, this.mMainHandler, standardWifiEntryKey, list, null, this.mWifiManager, false));
                            break;
                        }
                        Object obj = arrayList.get(i);
                        i++;
                        if (standardWifiEntryKey.equals(((StandardWifiEntry) obj).mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            int networkId2 = wifiInfo.getNetworkId();
            Iterator it2 = ((ArrayMap) this.mSuggestedConfigCache).values().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                List list2 = (List) it2.next();
                if (!list2.isEmpty() && ((WifiConfiguration) list2.get(0)).networkId == networkId2) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey2 = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list2.get(0), true);
                    ArrayList arrayList2 = (ArrayList) this.mSuggestedWifiEntryCache;
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size2) {
                            ((ArrayList) this.mSuggestedWifiEntryCache).add(new StandardWifiEntry(this.mInjector, this.mMainHandler, standardWifiEntryKey2, list2, null, this.mWifiManager, false));
                            break;
                        }
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        if (standardWifiEntryKey2.equals(((StandardWifiEntry) obj2).mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo != null && wifiInfo.isPasspointAp() && (wifiConfiguration = (WifiConfiguration) this.mPasspointWifiConfigCache.get(wifiInfo.getNetworkId())) != null) {
            if (!((ArrayMap) this.mPasspointWifiEntryCache).containsKey(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()))) {
                PasspointConfiguration passpointConfiguration = (PasspointConfiguration) ((ArrayMap) this.mPasspointConfigCache).get(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()));
                if (passpointConfiguration != null) {
                    passpointWifiEntry = new PasspointWifiEntry(this.mInjector, this.mMainHandler, passpointConfiguration, this.mWifiManager, false);
                } else {
                    passpointWifiEntry = new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager, false);
                }
                ((ArrayMap) this.mPasspointWifiEntryCache).put(passpointWifiEntry.mKey, passpointWifiEntry);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        if (wifiInfo != null) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.mNetworkRequestConfigCache.size()) {
                    break;
                }
                List list3 = (List) this.mNetworkRequestConfigCache.valueAt(i3);
                if (!list3.isEmpty() && ((WifiConfiguration) list3.get(0)).networkId == wifiInfo.getNetworkId()) {
                    arrayList3.addAll(list3);
                    break;
                }
                i3++;
            }
        }
        if (arrayList3.isEmpty()) {
            return;
        }
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey3 = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) arrayList3.get(0));
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null || !networkRequestEntry.mKey.equals(standardWifiEntryKey3)) {
            NetworkRequestEntry networkRequestEntry2 = new NetworkRequestEntry(this.mInjector, this.mMainHandler, standardWifiEntryKey3, this.mWifiManager, false);
            this.mNetworkRequestEntry = networkRequestEntry2;
            networkRequestEntry2.updateConfig(arrayList3);
            updateNetworkRequestEntryScans(this.mScanResultUpdater.getScanResults());
        }
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        int i;
        if (this.mWifiManager.getWifiState() == 1) {
            List list = Collections.EMPTY_LIST;
            updateStandardWifiEntryScans(list);
            updateSuggestedWifiEntryScans(list);
            updatePasspointWifiEntryScans(list);
            updateOsuWifiEntryScans(list);
            if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
                int i2 = BuildCompat.$r8$clinit;
                ((ArrayList) this.mKnownNetworkEntryCache).clear();
                ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            }
            updateNetworkRequestEntryScans(list);
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            synchronized (scanResultUpdater.mLock) {
                ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).clear();
            }
            return;
        }
        if (z) {
            this.mLastWifiInfo = this.mWifiManager.getConnectionInfo();
            ScanResultUpdater scanResultUpdater2 = this.mScanResultUpdater;
            List<ScanResult> scanResults = this.mWifiManager.getScanResults();
            WifiInfo wifiInfo = this.mLastWifiInfo;
            synchronized (scanResultUpdater2.mLock) {
                try {
                    scanResultUpdater2.mSemFilter.updateRssiFilter();
                    for (ScanResult scanResult : scanResults) {
                        if (!TextUtils.isEmpty(scanResult.SSID)) {
                            Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                            ScanResult scanResult2 = (ScanResult) ((ArrayMap) scanResultUpdater2.mScanResultsBySsidAndBssid).get(pair);
                            if (scanResult2 == null || scanResult2.timestamp < scanResult.timestamp) {
                                SemWifiEntryFilter semWifiEntryFilter = scanResultUpdater2.mSemFilter;
                                semWifiEntryFilter.getClass();
                                int i3 = scanResult.level;
                                if (i3 < semWifiEntryFilter.mWeakSignalRssi || ((i = scanResult.frequency) > 5000 && i < 6000 && i3 < semWifiEntryFilter.mWeakSignalRssi5Ghz)) {
                                    if (wifiInfo == null || !TextUtils.equals(wifiInfo.getBSSID(), scanResult.BSSID)) {
                                        List list2 = Utils.defaultSsidList;
                                        if (!SystemProperties.getBoolean("ro.product_ship", true)) {
                                            LogUtils logUtils = scanResultUpdater2.mLog;
                                            String str = "filtered scan item: " + scanResult.toString();
                                            if (logUtils.isProductDev) {
                                                Log.d("WifiTracker.ScanResultUpdater", logUtils.getPrintableLog(str));
                                            }
                                        }
                                    } else {
                                        Log.d("WifiTracker.ScanResultUpdater", "it's weak signal network " + wifiInfo.getSSID());
                                    }
                                }
                                ((ArrayMap) scanResultUpdater2.mScanResultsBySsidAndBssid).put(pair, scanResult);
                            }
                        }
                    }
                    ((ArrayMap) scanResultUpdater2.mScanResultsBySsidAndBssid).entrySet().removeIf(new ScanResultUpdater$$ExternalSyntheticLambda0(scanResultUpdater2, z ? scanResultUpdater2.mMaxScanAgeMillis : 300000L, 0));
                } finally {
                }
            }
        }
        List scanResults2 = this.mScanResultUpdater.getScanResults();
        updateStandardWifiEntryScans(scanResults2);
        updateSuggestedWifiEntryScans(scanResults2);
        updatePasspointWifiEntryScans(scanResults2);
        updateOsuWifiEntryScans(scanResults2);
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            int i4 = BuildCompat.$r8$clinit;
            updateKnownNetworkEntryScans(scanResults2);
            updateHotspotNetworkEntries();
        }
        updateNetworkRequestEntryScans(scanResults2);
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mSuggestedWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        arrayList.addAll(((ArrayMap) this.mOsuWifiEntryCache).values());
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            arrayList.addAll(this.mKnownNetworkEntryCache);
            arrayList.addAll(this.mHotspotNetworkEntryCache);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            arrayList.add(networkRequestEntry);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            arrayList.add(mergedCarrierEntry);
        }
        return arrayList;
    }

    public final MergedCarrierEntry getMergedCarrierEntry() {
        int defaultDataSubscriptionId;
        if (!this.mIsInitialized && this.mMergedCarrierEntry == null && (defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId()) != -1) {
            this.mMergedCarrierEntry = new MergedCarrierEntry(this.mInjector, this.mWorkerHandler, this.mWifiManager, false, defaultDataSubscriptionId);
        }
        return this.mMergedCarrierEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        HashMap map = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
            map.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        updateWifiConfigurationsInternal(map);
        updatePasspointConfigurations(map, this.mWifiManager.getPasspointConfigurations());
        if (this.mConnectedNetworkEverUpdated) {
            conditionallyUpdateScanResults(false);
            Handler handler = this.mMainHandler;
            WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
            if (wifiPickerTrackerCallback != null) {
                handler.post(new WifiPickerTracker$$ExternalSyntheticLambda29(wifiPickerTrackerCallback));
            }
            if (wifiPickerTrackerCallback != null) {
                handler.post(new WifiPickerTracker$$ExternalSyntheticLambda29(wifiPickerTrackerCallback));
            }
            updateWifiEntries(0, "config");
        } else {
            Log.d("WifiPickerTracker", "Skip to update by CONFIGURED_NETWORK_CHANGED");
        }
        if (this.mConnectedNetworkEverUpdated) {
            intent.getBooleanExtra("multipleChanges", false);
            intent.getIntExtra("changeReason", 2);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            WifiEntry wifiEntry = (WifiEntry) obj;
            synchronized (wifiEntry) {
                if (connectivityReport.getNetwork().equals(wifiEntry.mNetwork)) {
                    wifiEntry.mConnectivityReport = connectivityReport;
                    wifiEntry.notifyOnUpdated();
                }
            }
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            WifiEntry wifiEntry = (WifiEntry) obj;
            synchronized (wifiEntry) {
                wifiEntry.mDefaultNetwork = network;
                wifiEntry.mDefaultNetworkCapabilities = networkCapabilities;
                wifiEntry.notifyOnUpdated();
            }
        }
        notifyOnWifiEntriesChanged(0);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkLost() {
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            WifiEntry wifiEntry = (WifiEntry) obj;
            synchronized (wifiEntry) {
                wifiEntry.mDefaultNetwork = null;
                wifiEntry.mDefaultNetworkCapabilities = null;
                wifiEntry.notifyOnUpdated();
            }
        }
        notifyOnWifiEntriesChanged(0);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultSubscriptionChanged(int i) {
        if (i != -1) {
            MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
            if (mergedCarrierEntry != null && i == mergedCarrierEntry.mSubscriptionId) {
                return;
            }
            WifiManager wifiManager = this.mWifiManager;
            this.mMergedCarrierEntry = new MergedCarrierEntry(this.mInjector, this.mWorkerHandler, wifiManager, false, i);
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null) {
                NetworkCapabilities networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(currentNetwork);
                if (networkCapabilities != null) {
                    this.mMergedCarrierEntry.onNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(this.mWifiManager.getConnectionInfo()).build());
                }
                LinkProperties linkProperties = this.mConnectivityManager.getLinkProperties(currentNetwork);
                if (linkProperties != null) {
                    this.mMergedCarrierEntry.updateLinkProperties(currentNetwork, linkProperties);
                }
            }
        } else if (this.mMergedCarrierEntry == null) {
            return;
        } else {
            this.mMergedCarrierEntry = null;
        }
        notifyOnWifiEntriesChanged(0);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
        this.mHotspotNetworkEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda11(hotspotNetworkConnectionStatus, 0)).forEach(new WifiPickerTracker$$ExternalSyntheticLambda10(hotspotNetworkConnectionStatus));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleHotspotNetworksUpdated(List list) {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mHotspotNetworkDataCache).clear();
            Log.i("WifiPickerTracker", "onHotspotNetworksUpdated(), networks:" + list);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Log.i("WifiPickerTracker", "network:" + ((HotspotNetwork) it.next()));
            }
            ((ArrayList) this.mHotspotNetworkDataCache).addAll(list);
            updateHotspotNetworkEntries();
            updateWifiEntries(0, "hotspot");
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
        this.mKnownNetworkEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda9(new StandardWifiEntry.ScanResultKey(knownNetworkConnectionStatus.getKnownNetwork().getSsid(), new ArrayList(knownNetworkConnectionStatus.getKnownNetwork().getSecurityTypes())), 0)).forEach(new WifiPickerTracker$$ExternalSyntheticLambda10(knownNetworkConnectionStatus));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleKnownNetworksUpdated(List list) {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mKnownNetworkDataCache).clear();
            ((ArrayList) this.mKnownNetworkDataCache).addAll(list);
            updateKnownNetworkEntryScans(this.mScanResultUpdater.getScanResults());
            updateWifiEntries(0, "known");
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).updateLinkProperties(network, linkProperties);
        }
        Log.d("WifiPickerTracker", "handleLinkPropertiesChanged");
        notifyOnWifiEntriesChanged(0);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        if (this.mNetworkRequestConfigCache.size() + this.mPasspointWifiConfigCache.size() + ((ArrayMap) this.mSuggestedConfigCache).size() + ((ArrayMap) this.mStandardWifiConfigCache).size() == 0) {
            HashMap map = new HashMap();
            for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
                map.put(semWifiConfiguration.configKey, semWifiConfiguration);
            }
            updateWifiConfigurationsInternal(map);
        }
        List list = Utils.defaultSsidList;
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        conditionallyCreateConnectedWifiEntry(transportInfo instanceof WifiInfo ? (WifiInfo) transportInfo : null);
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries(0, "capabilities");
        Log.d("WifiPickerTracker", "handleNetworkCapabilitiesChanged");
        notifyOnWifiEntriesChanged(0);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).onNetworkLost(network);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null && networkRequestEntry.getConnectedState() == 0) {
            this.mNetworkRequestEntry = null;
        }
        updateWifiEntries(0, "lost");
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo != null) {
            conditionallyCreateConnectedWifiEntry(connectionInfo);
        }
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
        updateWifiEntries(0, "networkstate " + networkInfo.getState() + " netId: " + (connectionInfo != null ? connectionInfo.getNetworkId() : -1));
        networkInfo.isConnected();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        List list;
        HashMap map = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
            map.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        boolean z = this.mIsSettingsTracker;
        if (z) {
            updateWifiConfigurationsInternal(map);
        } else {
            updateWifiConfigurations(map, this.mWifiManager.getConfiguredNetworks());
        }
        updatePasspointConfigurations(map, this.mWifiManager.getPasspointConfigurations());
        boolean z2 = z && this.mIsSettingSupportEasySetup && Settings.Global.getInt(this.mContext.getContentResolver(), "safe_wifi", 0) == 0;
        this.mIsSupportEasySetup = z2;
        if (z2) {
            Map easySetupScanSettings = this.mEasySetupUtils.mSemWifiManager.getEasySetupScanSettings();
            if (easySetupScanSettings.size() != 0) {
                ((ArrayList) this.mSemEasySetupScanSettings).clear();
                for (SemEasySetupWifiScanSettings semEasySetupWifiScanSettings : easySetupScanSettings.values()) {
                    if (semEasySetupWifiScanSettings != null && semEasySetupWifiScanSettings.pendingIntentForSettings != null && (list = semEasySetupWifiScanSettings.ssidPatterns) != null && !list.isEmpty()) {
                        ((ArrayList) this.mSemEasySetupScanSettings).add(semEasySetupWifiScanSettings);
                        RecyclerView$$ExternalSyntheticOutline0.m(semEasySetupWifiScanSettings.minRssi, "WifiPickerTracker", new StringBuilder("set EasySetup filter - minRssi : "));
                    }
                }
            }
        }
        conditionallyUpdateScanResults(true);
        handleDefaultSubscriptionChanged(SubscriptionManager.getDefaultDataSubscriptionId());
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).clearConnectionInfo(false);
        }
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        updateForceNetworkInfo(currentNetwork);
        if (currentNetwork != null) {
            NetworkCapabilities networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(this.mWifiManager.getConnectionInfo()).build());
            }
            LinkProperties linkProperties = this.mConnectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
        Handler handler = this.mMainHandler;
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda29(wifiPickerTrackerCallback));
        }
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda29(wifiPickerTrackerCallback));
        }
        updateWifiEntries(0, "force");
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleQosScoreCacheUpdated() {
        WifiQoSScoredCache wifiQoSScoredCache;
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            wifiQoSScoredCache = this.mQoSScoredCache;
            if (i2 >= size) {
                break;
            }
            Object obj = arrayList.get(i2);
            i2++;
            ((StandardWifiEntry) obj).semUpdateScores(wifiQoSScoredCache);
        }
        ArrayList arrayList2 = (ArrayList) this.mSuggestedWifiEntryCache;
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            ((StandardWifiEntry) obj2).semUpdateScores(wifiQoSScoredCache);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleRssiChangedAction() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        ArrayList arrayList = (ArrayList) getAllWifiEntries();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WifiEntry) obj).onPrimaryWifiInfoChanged(connectionInfo, null);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        intent.getBooleanExtra("resultsUpdated", true);
        handleQosScoreCacheUpdated();
        updateForceNetworkInfo(this.mWifiManager.getCurrentNetwork());
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries(1, "scan");
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleServiceConnected() {
        if (!this.mInjector.isSharedConnectivityFeatureEnabled() || this.mSharedConnectivityManager == null) {
            return;
        }
        ((ArrayList) this.mKnownNetworkDataCache).clear();
        List knownNetworks = this.mSharedConnectivityManager.getKnownNetworks();
        if (knownNetworks != null) {
            ((ArrayList) this.mKnownNetworkDataCache).addAll(knownNetworks);
        }
        ((ArrayList) this.mHotspotNetworkDataCache).clear();
        List hotspotNetworks = this.mSharedConnectivityManager.getHotspotNetworks();
        if (hotspotNetworks != null) {
            ((ArrayList) this.mHotspotNetworkDataCache).addAll(hotspotNetworks);
        }
        updateKnownNetworkEntryScans(this.mScanResultUpdater.getScanResults());
        updateHotspotNetworkEntries();
        HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = this.mSharedConnectivityManager.getHotspotNetworkConnectionStatus();
        if (hotspotNetworkConnectionStatus != null) {
            handleHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
        }
        updateWifiEntries(0, "service_connected");
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleServiceDisconnected() {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mKnownNetworkDataCache).clear();
            ((ArrayList) this.mHotspotNetworkDataCache).clear();
            ((ArrayList) this.mKnownNetworkEntryCache).clear();
            ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            updateWifiEntries(0, "service_disconnect");
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        if (this.mWifiState == 4) {
            this.mWifiState = this.mWifiManager.getWifiState();
        }
        if (this.mWifiState == 1) {
            ((ArrayList) this.mStandardWifiEntryCache).clear();
            ((ArrayList) this.mSuggestedWifiEntryCache).clear();
            ((ArrayMap) this.mPasspointWifiEntryCache).clear();
            ((ArrayMap) this.mOsuWifiEntryCache).clear();
            if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
                ((ArrayList) this.mKnownNetworkEntryCache).clear();
                ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            }
            this.mNetworkRequestEntry = null;
            this.mConnectedNetworkEverUpdated = false;
        }
        updateWifiEntries(0, ImsProfile.PDN_WIFI);
    }

    public final void notifyOnWifiEntriesChanged(final int i) {
        if (this.mListener != null) {
            this.mMainHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    WifiPickerTracker wifiPickerTracker = this.f$0;
                    wifiPickerTracker.mListener.onWifiEntriesChanged(i);
                }
            });
        }
    }

    public final void updateForceNetworkInfo(Network network) {
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(network);
        if (networkInfo == null) {
            return;
        }
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((WifiEntry) obj).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        ArrayList arrayList2 = (ArrayList) this.mSuggestedWifiEntryCache;
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            ((WifiEntry) obj2).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        Iterator it = ((ArrayMap) this.mPasspointWifiEntryCache).values().iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        Iterator it2 = ((ArrayMap) this.mOsuWifiEntryCache).values().iterator();
        while (it2.hasNext()) {
            ((WifiEntry) it2.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ArrayList arrayList3 = (ArrayList) this.mKnownNetworkEntryCache;
            int size3 = arrayList3.size();
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = arrayList3.get(i4);
                i4++;
                ((WifiEntry) obj3).forceUpdateNetworkInfo(connectionInfo, networkInfo);
            }
            ArrayList arrayList4 = (ArrayList) this.mHotspotNetworkEntryCache;
            int size4 = arrayList4.size();
            while (i < size4) {
                Object obj4 = arrayList4.get(i);
                i++;
                ((WifiEntry) obj4).forceUpdateNetworkInfo(connectionInfo, networkInfo);
            }
        }
        if (networkInfo.isConnected() != this.mConnected.getAndSet(networkInfo.isConnected())) {
            networkInfo.isConnected();
        }
    }

    public final void updateHotspotNetworkEntries() {
        NetworkCapabilities networkCapabilities;
        Map map = (Map) this.mHotspotNetworkDataCache.stream().collect(Collectors.toMap(new WifiPickerTracker$$ExternalSyntheticLambda1(2), new WifiPickerTracker$$ExternalSyntheticLambda1(3), new WifiPickerTracker$$ExternalSyntheticLambda4(1)));
        ArraySet arraySet = new ArraySet(map.keySet());
        ((ArrayList) this.mHotspotNetworkEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda15(2, arraySet));
        ((ArrayList) this.mHotspotNetworkEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(arraySet, map, 1));
        Network network = null;
        NetworkCapabilities networkCapabilities2 = null;
        if (arraySet.isEmpty()) {
            networkCapabilities = null;
        } else {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null && (networkCapabilities2 = this.mConnectivityManager.getNetworkCapabilities(currentNetwork)) != null) {
                networkCapabilities2 = new NetworkCapabilities.Builder(networkCapabilities2).setTransportInfo(this.mWifiManager.getConnectionInfo()).build();
            }
            NetworkCapabilities networkCapabilities3 = networkCapabilities2;
            network = currentNetwork;
            networkCapabilities = networkCapabilities3;
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            HotspotNetworkEntry hotspotNetworkEntry = new HotspotNetworkEntry(this.mInjector, this.mContext, this.mMainHandler, this.mWifiManager, this.mSharedConnectivityManager, (HotspotNetwork) map.get((Long) it.next()));
            if (network != null && networkCapabilities != null) {
                hotspotNetworkEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            }
            ((ArrayList) this.mHotspotNetworkEntryCache).add(hotspotNetworkEntry);
        }
    }

    public final void updateKnownNetworkEntryScans(List list) {
        NetworkCapabilities networkCapabilities;
        final Map map = (Map) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(0)).collect(Collectors.groupingBy(new WifiPickerTracker$$ExternalSyntheticLambda1(0)));
        final Map map2 = (Map) this.mKnownNetworkDataCache.stream().collect(Collectors.toMap(new WifiPickerTracker$$ExternalSyntheticLambda1(4), new WifiPickerTracker$$ExternalSyntheticLambda1(8), new WifiPickerTracker$$ExternalSyntheticLambda4(0)));
        final int i = 0;
        ((ArrayList) this.mKnownNetworkEntryCache).removeIf(new Predicate() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i2 = i;
                Map map3 = map2;
                switch (i2) {
                    case 0:
                        return !map3.keySet().contains(((KnownNetworkEntry) obj).mKey.mScanResultKey);
                    default:
                        return map3.containsKey((StandardWifiEntry.ScanResultKey) obj);
                }
            }
        });
        Stream stream = map2.keySet().stream();
        Objects.requireNonNull(map);
        final int i2 = 1;
        Set<StandardWifiEntry.ScanResultKey> set = (Set) stream.filter(new Predicate() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i22 = i2;
                Map map3 = map;
                switch (i22) {
                    case 0:
                        return !map3.keySet().contains(((KnownNetworkEntry) obj).mKey.mScanResultKey);
                    default:
                        return map3.containsKey((StandardWifiEntry.ScanResultKey) obj);
                }
            }
        }).collect(Collectors.toSet());
        ((ArrayList) this.mKnownNetworkEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(set, map, 0));
        Network network = null;
        NetworkCapabilities networkCapabilities2 = null;
        if (set.isEmpty()) {
            networkCapabilities = null;
        } else {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null && (networkCapabilities2 = this.mConnectivityManager.getNetworkCapabilities(currentNetwork)) != null) {
                networkCapabilities2 = new NetworkCapabilities.Builder(networkCapabilities2).setTransportInfo(this.mWifiManager.getConnectionInfo()).build();
            }
            NetworkCapabilities networkCapabilities3 = networkCapabilities2;
            network = currentNetwork;
            networkCapabilities = networkCapabilities3;
        }
        for (StandardWifiEntry.ScanResultKey scanResultKey : set) {
            KnownNetworkEntry knownNetworkEntry = new KnownNetworkEntry(this.mInjector, this.mMainHandler, new StandardWifiEntry.StandardWifiEntryKey(scanResultKey, true), null, (List) map.get(scanResultKey), this.mWifiManager, this.mSharedConnectivityManager, (KnownNetwork) map2.get(scanResultKey));
            if (network != null && networkCapabilities != null) {
                knownNetworkEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            }
            ((ArrayList) this.mKnownNetworkEntryCache).add(knownNetworkEntry);
        }
        ((ArrayList) this.mKnownNetworkEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(13));
    }

    public final void updateNetworkRequestEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null) {
            return;
        }
        this.mNetworkRequestEntry.updateScanResultInfo((List) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda9(networkRequestEntry.mKey.mScanResultKey, 1)).collect(Collectors.toList()));
    }

    public final void updateOsuWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        Map matchingOsuProviders = this.mWifiManager.getMatchingOsuProviders(list);
        Map matchingPasspointConfigsForOsuProviders = this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(matchingOsuProviders.keySet());
        for (OsuWifiEntry osuWifiEntry : ((ArrayMap) this.mOsuWifiEntryCache).values()) {
            osuWifiEntry.updateScanResultInfo((List) matchingOsuProviders.remove(osuWifiEntry.mOsuProvider));
        }
        for (OsuProvider osuProvider : matchingOsuProviders.keySet()) {
            OsuWifiEntry osuWifiEntry2 = new OsuWifiEntry(this.mInjector, this.mMainHandler, osuProvider, this.mWifiManager, false);
            osuWifiEntry2.updateScanResultInfo((List) matchingOsuProviders.get(osuProvider));
            ((ArrayMap) this.mOsuWifiEntryCache).put(OsuWifiEntry.osuProviderToOsuWifiEntryKey(osuProvider), osuWifiEntry2);
        }
        ((ArrayMap) this.mOsuWifiEntryCache).values().forEach(new WifiPickerTracker$$ExternalSyntheticLambda39(this, matchingPasspointConfigsForOsuProviders, 0));
        ((ArrayMap) this.mOsuWifiEntryCache).entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(9));
        ((ArrayMap) this.mOsuWifiEntryCache).entrySet().forEach(new WifiPickerTracker$$ExternalSyntheticLambda38(this, 1));
    }

    public final void updatePasspointConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ((ArrayMap) this.mPasspointConfigCache).clear();
        ((ArrayMap) this.mPasspointConfigCache).putAll((Map) list.stream().collect(Collectors.toMap(new WifiPickerTracker$$ExternalSyntheticLambda1(6), Function.identity())));
        ((ArrayMap) this.mPasspointWifiEntryCache).entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda33(this, map, 0));
    }

    public final void updatePasspointWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            int i = 0;
            List<ScanResult> list2 = (List) ((Map) pair.second).get(0);
            List<ScanResult> list3 = (List) ((Map) pair.second).get(1);
            if ("Vendor Hotspot2.0 Profile".equals(wifiConfiguration.providerFriendlyName)) {
                Log.d("WifiPickerTracker", "updatePasspointAccessPoints, Do not add if it is not matched with ANQP");
            } else {
                String strUniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
                treeSet.add(strUniqueIdToPasspointWifiEntryKey);
                if (!((ArrayMap) this.mPasspointWifiEntryCache).containsKey(strUniqueIdToPasspointWifiEntryKey)) {
                    if (wifiConfiguration.fromWifiNetworkSuggestion) {
                        ((ArrayMap) this.mPasspointWifiEntryCache).put(strUniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager, false));
                    } else if (((ArrayMap) this.mPasspointConfigCache).containsKey(strUniqueIdToPasspointWifiEntryKey)) {
                        ((ArrayMap) this.mPasspointWifiEntryCache).put(strUniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mMainHandler, (PasspointConfiguration) ((ArrayMap) this.mPasspointConfigCache).get(strUniqueIdToPasspointWifiEntryKey), this.mWifiManager, false));
                    } else {
                        continue;
                    }
                }
                PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((ArrayMap) this.mPasspointWifiEntryCache).get(strUniqueIdToPasspointWifiEntryKey);
                synchronized (passpointWifiEntry) {
                    try {
                        passpointWifiEntry.mWifiConfig = wifiConfiguration;
                        ((ArrayList) passpointWifiEntry.mCurrentHomeScanResults).clear();
                        ((ArrayList) passpointWifiEntry.mCurrentRoamingScanResults).clear();
                        if (list2 != null) {
                            ((ArrayList) passpointWifiEntry.mCurrentHomeScanResults).addAll(list2);
                        }
                        if (list3 != null) {
                            ((ArrayList) passpointWifiEntry.mCurrentRoamingScanResults).addAll(list3);
                        }
                        if (passpointWifiEntry.mWifiConfig != null) {
                            SemWifiEntryFlags semWifiEntryFlags = passpointWifiEntry.mSemFlags;
                            semWifiEntryFlags.wifiStandard = 0;
                            semWifiEntryFlags.has6EStandard = false;
                            semWifiEntryFlags.staCount = -1;
                            passpointWifiEntry.updateSecurityTypes();
                            ArrayList arrayList = new ArrayList();
                            if (list2 != null && !list2.isEmpty()) {
                                arrayList.addAll(list2);
                                for (ScanResult scanResult : list2) {
                                    passpointWifiEntry.semUpdateFlags(scanResult);
                                    int i2 = scanResult.frequency;
                                    if (i < i2) {
                                        passpointWifiEntry.setBand(i2);
                                        passpointWifiEntry.mFrequency = scanResult.frequency;
                                        i = i2;
                                    }
                                }
                            } else if (list3 != null && !list3.isEmpty()) {
                                arrayList.addAll(list3);
                                for (ScanResult scanResult2 : list3) {
                                    passpointWifiEntry.semUpdateFlags(scanResult2);
                                    int i3 = scanResult2.frequency;
                                    if (i < i3) {
                                        passpointWifiEntry.setBand(i3);
                                        passpointWifiEntry.mFrequency = i3;
                                        i = i3;
                                    }
                                }
                            }
                            ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(arrayList);
                            if (bestScanResultByLevel != null) {
                                passpointWifiEntry.mWifiConfig.SSID = "\"" + bestScanResultByLevel.SSID + "\"";
                                passpointWifiEntry.updateBestRssi(bestScanResultByLevel);
                                passpointWifiEntry.mBssid = bestScanResultByLevel.BSSID;
                            }
                            if (passpointWifiEntry.getConnectedState() == 0) {
                                int iCalculateSignalLevel = bestScanResultByLevel != null ? SemWifiUtils.calculateSignalLevel(passpointWifiEntry.mRssi) : -1;
                                passpointWifiEntry.mScanResultLevel = iCalculateSignalLevel;
                                if (iCalculateSignalLevel == -1) {
                                    passpointWifiEntry.mRssi = -127;
                                }
                            }
                        } else {
                            passpointWifiEntry.mScanResultLevel = -1;
                        }
                        passpointWifiEntry.notifyOnUpdated();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        ((ArrayMap) this.mPasspointWifiEntryCache).entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda15(3, treeSet));
        ((ArrayMap) this.mPasspointWifiEntryCache).entrySet().forEach(new WifiPickerTracker$$ExternalSyntheticLambda38(this, 2));
    }

    public final void updateStandardWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        Map map = (Map) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(11)).collect(Collectors.groupingBy(new WifiPickerTracker$$ExternalSyntheticLambda1(0)));
        ArraySet arraySet = new ArraySet(map.keySet());
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(arraySet, map, 2));
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            StandardWifiEntry.ScanResultKey scanResultKey = (StandardWifiEntry.ScanResultKey) it.next();
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(scanResultKey, true);
            ((ArrayList) this.mStandardWifiEntryCache).add(new StandardWifiEntry(this.mInjector, this.mMainHandler, standardWifiEntryKey, (List) ((ArrayMap) this.mStandardWifiConfigCache).get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false));
        }
        ((ArrayList) this.mStandardWifiEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(12));
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda38(this, 3));
    }

    public final void updateSuggestedWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Set set = (Set) this.mWifiManager.getWifiConfigForMatchedNetworkSuggestionsSharedWithUser(list).stream().map(new WifiPickerTracker$$ExternalSyntheticLambda1(7)).collect(Collectors.toSet());
        final Map map = (Map) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(7)).collect(Collectors.groupingBy(new WifiPickerTracker$$ExternalSyntheticLambda1(0)));
        final ArraySet arraySet = new ArraySet();
        ((ArrayList) this.mSuggestedWifiEntryCache).forEach(new Consumer() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda36
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Set set2 = arraySet;
                Map map2 = map;
                Set set3 = set;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardWifiEntry.mKey;
                ((ArraySet) set2).add(standardWifiEntryKey);
                standardWifiEntry.updateScanResultInfo((List) map2.get(standardWifiEntryKey.mScanResultKey));
                boolean zContains = set3.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = zContains;
                }
            }
        });
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : ((ArrayMap) this.mSuggestedConfigCache).keySet()) {
            StandardWifiEntry.ScanResultKey scanResultKey = standardWifiEntryKey.mScanResultKey;
            if (!arraySet.contains(standardWifiEntryKey) && map.containsKey(scanResultKey)) {
                StandardWifiEntry standardWifiEntry = new StandardWifiEntry(this.mInjector, this.mMainHandler, standardWifiEntryKey, (List) ((ArrayMap) this.mSuggestedConfigCache).get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false);
                boolean zContains = set.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = zContains;
                }
                ((ArrayList) this.mSuggestedWifiEntryCache).add(standardWifiEntry);
            }
        }
        ((ArrayList) this.mSuggestedWifiEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(8));
        ((ArrayList) this.mSuggestedWifiEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda38(this, 0));
    }

    public final void updateWifiConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ((ArrayMap) this.mStandardWifiConfigCache).clear();
        ((ArrayMap) this.mSuggestedConfigCache).clear();
        this.mNetworkRequestConfigCache.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
            if (!wifiConfiguration.carrierMerged) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(wifiConfiguration, true);
                if (wifiConfiguration.isPasspoint()) {
                    this.mPasspointWifiConfigCache.put(wifiConfiguration.networkId, wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSuggestion) {
                    if (!((ArrayMap) this.mSuggestedConfigCache).containsKey(standardWifiEntryKey)) {
                        ((ArrayMap) this.mSuggestedConfigCache).put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) ((ArrayMap) this.mSuggestedConfigCache).get(standardWifiEntryKey)).add(wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSpecifier) {
                    if (!this.mNetworkRequestConfigCache.containsKey(standardWifiEntryKey)) {
                        this.mNetworkRequestConfigCache.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) this.mNetworkRequestConfigCache.get(standardWifiEntryKey)).add(wifiConfiguration);
                } else {
                    if (!((ArrayMap) this.mStandardWifiConfigCache).containsKey(standardWifiEntryKey)) {
                        ((ArrayMap) this.mStandardWifiConfigCache).put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) ((ArrayMap) this.mStandardWifiConfigCache).get(standardWifiEntryKey)).add(wifiConfiguration);
                }
            }
        }
        ((ArrayMap) this.mStandardWifiConfigCache).values().stream().flatMap(new StandardWifiEntry$$ExternalSyntheticLambda0()).filter(new WifiPickerTracker$$ExternalSyntheticLambda0(10)).map(new WifiPickerTracker$$ExternalSyntheticLambda1(9)).distinct().count();
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda39(this, map, 1));
        ((ArrayList) this.mSuggestedWifiEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda33(this, map, 1));
        updateSuggestedWifiEntryScans(this.mScanResultUpdater.getScanResults());
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.updateConfig((List) this.mNetworkRequestConfigCache.get(networkRequestEntry.mKey));
        }
    }

    public final void updateWifiConfigurationsInternal(Map map) {
        if (this.mContext.checkSelfPermission("android.permission.READ_WIFI_CREDENTIAL") == 0) {
            updateWifiConfigurations(map, this.mWifiManager.getPrivilegedConfiguredNetworks());
        } else {
            updateWifiConfigurations(map, this.mWifiManager.getConfiguredNetworks());
        }
    }

    public final void updateWifiEntries(int i, String str) {
        Comparator comparator;
        boolean z;
        boolean z2;
        String str2;
        ArraySet arraySet;
        int i2 = 0;
        int i3 = 1;
        Log.d("WifiPickerTracker", "updateWifiEntries " + str);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mSuggestedWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            arrayList.addAll(this.mHotspotNetworkEntryCache);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            arrayList.add(networkRequestEntry);
        }
        arrayList.removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(1));
        ArraySet arraySet2 = new ArraySet();
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            WifiEntry wifiEntry = (WifiEntry) obj;
            if (wifiEntry instanceof HotspotNetworkEntry) {
                arraySet2.add(((HotspotNetworkEntry) wifiEntry).mKey.mScanResultKey);
            }
        }
        arrayList.removeIf(new WifiPickerTracker$$ExternalSyntheticLambda15(0, arraySet2));
        arrayList.sort(WifiEntry.WIFI_PICKER_COMPARATOR);
        Set set = (Set) this.mSuggestedWifiEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda11(arrayList, 1)).map(new WifiPickerTracker$$ExternalSyntheticLambda1(1)).collect(Collectors.toSet());
        ArraySet arraySet3 = new ArraySet();
        for (PasspointWifiEntry passpointWifiEntry : ((ArrayMap) this.mPasspointWifiEntryCache).values()) {
            synchronized (passpointWifiEntry) {
                try {
                    arraySet = new ArraySet();
                    ArrayList arrayList3 = (ArrayList) passpointWifiEntry.mCurrentHomeScanResults;
                    int size2 = arrayList3.size();
                    int i5 = i2;
                    while (i5 < size2) {
                        Object obj2 = arrayList3.get(i5);
                        i5++;
                        arraySet.add(((ScanResult) obj2).SSID);
                    }
                    ArrayList arrayList4 = (ArrayList) passpointWifiEntry.mCurrentRoamingScanResults;
                    int size3 = arrayList4.size();
                    int i6 = 0;
                    while (i6 < size3) {
                        Object obj3 = arrayList4.get(i6);
                        i6++;
                        arraySet.add(((ScanResult) obj3).SSID);
                    }
                } finally {
                }
            }
            arraySet3.addAll((Collection) arraySet);
            i2 = 0;
        }
        ArraySet arraySet4 = new ArraySet();
        ArrayList arrayList5 = (ArrayList) this.mKnownNetworkEntryCache;
        int size4 = arrayList5.size();
        int i7 = 0;
        while (i7 < size4) {
            Object obj4 = arrayList5.get(i7);
            i7++;
            arraySet4.add(((KnownNetworkEntry) obj4).mKey.mScanResultKey);
        }
        ArraySet arraySet5 = new ArraySet();
        ArrayList arrayList6 = (ArrayList) this.mHotspotNetworkEntryCache;
        int size5 = arrayList6.size();
        int i8 = 0;
        while (i8 < size5) {
            Object obj5 = arrayList6.get(i8);
            i8++;
            HotspotNetworkEntry.HotspotNetworkEntryKey hotspotNetworkEntryKey = ((HotspotNetworkEntry) obj5).mKey;
            if (!hotspotNetworkEntryKey.mIsVirtualEntry) {
                arraySet5.add(hotspotNetworkEntryKey.mScanResultKey);
            }
        }
        if (this.mIsSupportEasySetup) {
            ((ArrayList) this.mEasySetupCandidateEntries).clear();
            synchronized (this.mLockEasySetup) {
                ((ArrayList) this.mEasySetupEntries).clear();
            }
            ArrayList arrayList7 = (ArrayList) this.mSemEasySetupScanSettings;
            int size6 = arrayList7.size();
            int i9 = 0;
            while (i9 < size6) {
                Object obj6 = arrayList7.get(i9);
                i9 += i3;
                SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = (SemEasySetupWifiScanSettings) obj6;
                for (String str3 : semEasySetupWifiScanSettings.ssidPatterns) {
                    if (TextUtils.isEmpty(str3)) {
                        break;
                    }
                    int i10 = i3;
                    ArrayList arrayList8 = (ArrayList) this.mStandardWifiEntryCache;
                    int size7 = arrayList8.size();
                    int i11 = 0;
                    while (i11 < size7) {
                        Object obj7 = arrayList8.get(i11);
                        int i12 = i11 + 1;
                        int i13 = size7;
                        StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj7;
                        if (((ArrayList) this.mActiveWifiEntries).contains(standardWifiEntry)) {
                            size7 = i13;
                            i11 = i12;
                        } else {
                            if (standardWifiEntry.getSsid() == null || !standardWifiEntry.getSsid().matches(str3)) {
                                str2 = str3;
                            } else {
                                str2 = str3;
                                if (standardWifiEntry.mRssi >= semEasySetupWifiScanSettings.minRssi) {
                                    ((ArrayList) this.mEasySetupCandidateEntries).add(standardWifiEntry);
                                    synchronized (this.mLockEasySetup) {
                                        ((ArrayList) this.mEasySetupEntries).add(standardWifiEntry);
                                    }
                                }
                            }
                            size7 = i13;
                            i11 = i12;
                            str3 = str2;
                        }
                    }
                    i3 = i10;
                }
                i3 = i3;
            }
        }
        int i14 = i3;
        ArraySet arraySet6 = new ArraySet();
        ArrayList arrayList9 = (ArrayList) this.mStandardWifiEntryCache;
        int size8 = arrayList9.size();
        int i15 = 0;
        while (i15 < size8) {
            Object obj8 = arrayList9.get(i15);
            i15++;
            StandardWifiEntry standardWifiEntry2 = (StandardWifiEntry) obj8;
            standardWifiEntry2.updateAdminRestrictions();
            if (!arrayList.contains(standardWifiEntry2)) {
                if (standardWifiEntry2.isSaved()) {
                    arraySet6.add(standardWifiEntry2.mKey.mScanResultKey);
                } else if (!set.contains(standardWifiEntry2.mKey.mScanResultKey) && !arraySet3.contains(standardWifiEntry2.getSsid()) && (!this.mInjector.isSharedConnectivityFeatureEnabled() || !arraySet4.contains(standardWifiEntry2.mKey.mScanResultKey))) {
                }
                if (!this.mInjector.isSharedConnectivityFeatureEnabled() || !arraySet5.contains(standardWifiEntry2.mKey.mScanResultKey)) {
                    if (!((ArrayList) this.mEasySetupCandidateEntries).contains(standardWifiEntry2)) {
                        arrayList2.add(standardWifiEntry2);
                    }
                }
            }
        }
        arrayList2.addAll((Collection) this.mSuggestedWifiEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(3)).collect(Collectors.toList()));
        arrayList2.addAll((Collection) ((ArrayMap) this.mPasspointWifiEntryCache).values().stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(4)).collect(Collectors.toList()));
        arrayList2.addAll((Collection) ((ArrayMap) this.mOsuWifiEntryCache).values().stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(5)).collect(Collectors.toList()));
        arrayList2.addAll((Collection) Collections.EMPTY_LIST.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(6)).collect(Collectors.toList()));
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            arrayList2.addAll((Collection) this.mKnownNetworkEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda15(i14, arraySet6)).collect(Collectors.toList()));
            arrayList2.addAll((Collection) this.mHotspotNetworkEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(2)).collect(Collectors.toList()));
        }
        Context context = this.mContext;
        if (SemWifiEntryFlags.isWifiDeveloperOptionOn(context)) {
            int i16 = SemWifiUtils.$r8$clinit;
            int i17 = Settings.Global.getInt(context.getContentResolver(), "sec_wifi_developer_sorting_style", 3);
            comparator = i17 != 0 ? i17 != 1 ? i17 != 2 ? WifiEntry.WIFI_PICKER_COMPARATOR : WifiEntry.WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY : WifiEntry.WIFI_PICKER_COMPARATOR_RSSI : WifiEntry.WIFI_PICKER_COMPARATOR_ALPHABETICAL;
        } else {
            comparator = WifiEntry.WIFI_PICKER_COMPARATOR;
        }
        Collections.sort(arrayList2, comparator);
        List list = Utils.defaultSsidList;
        if ((!SystemProperties.getBoolean("ro.product_ship", true) || BaseWifiTracker.sVerboseLogging) ? true : Log.isLoggable("WifiPickerTracker", 3)) {
            Log.d("WifiPickerTracker", "------ Dumping WifiEntries that were seen top of 5 scan ------");
            int size9 = arrayList.size();
            int i18 = 0;
            while (i18 < size9) {
                Object obj9 = arrayList.get(i18);
                i18++;
                WifiEntry wifiEntry2 = (WifiEntry) obj9;
                Log.d("WifiPickerTracker", "Connected WifiEntry: " + wifiEntry2 + " - " + wifiEntry2.getSummary(true));
            }
            int i19 = 1;
            int size10 = arrayList2.size();
            int i20 = 1;
            int i21 = 0;
            while (i21 < size10) {
                Object obj10 = arrayList2.get(i21);
                i21 += i19;
                WifiEntry wifiEntry3 = (WifiEntry) obj10;
                if (i20 > 5) {
                    break;
                }
                Log.d("WifiPickerTracker", "Entry " + i20 + " : " + wifiEntry3 + " - " + wifiEntry3.getSummary(true));
                i20++;
                i19 = 1;
            }
            if (((ArrayList) this.mEasySetupCandidateEntries).size() > 0) {
                Log.d("WifiPickerTracker", "Easy setup networks ----- " + ((ArrayList) this.mEasySetupCandidateEntries).size());
                ArrayList arrayList10 = (ArrayList) this.mEasySetupCandidateEntries;
                int size11 = arrayList10.size();
                int i22 = 0;
                while (i22 < size11) {
                    Object obj11 = arrayList10.get(i22);
                    i22++;
                    WifiEntry wifiEntry4 = (WifiEntry) obj11;
                    Log.d("WifiPickerTracker", "Entry : " + wifiEntry4 + " - " + wifiEntry4.getSummary(true));
                }
            }
            Log.d("WifiPickerTracker", "---- Done dumping WifiEntries ----");
        }
        WifiEntry wifiEntry5 = null;
        if (arrayList.isEmpty()) {
            z = false;
            z2 = true;
        } else {
            z = false;
            WifiEntry wifiEntry6 = (WifiEntry) arrayList.get(0);
            if (wifiEntry6.isPrimaryNetwork()) {
                wifiEntry5 = wifiEntry6;
            } else {
                Log.d("WifiPickerTracker", "Connected " + wifiEntry6.getTitle() + "is not primary.");
            }
            z2 = true;
            this.mConnectedNetworkEverUpdated = true;
        }
        this.mConnectedWifiEntry = wifiEntry5;
        this.mActiveWifiEntries = arrayList;
        this.mWifiEntries = arrayList2;
        if (UserHandle.myUserId() != 0) {
            z2 = z;
        }
        if (z2) {
            List wifiApBleScanDetail = this.mSemWifiManager.getWifiApBleScanDetail();
            StringBuilder sb = new StringBuilder("update AutoHotspot Entries() : bleAccessPoints -> ");
            sb.append(wifiApBleScanDetail != null ? Integer.valueOf(wifiApBleScanDetail.size()) : "null");
            Log.d("WifiPickerTracker", sb.toString());
            synchronized (this.mLockAutoHotspot) {
                if (wifiApBleScanDetail != null) {
                    try {
                        ((ArrayList) this.mAutoHotspotEntries).clear();
                        ((ArrayList) this.mAutoHotspotEntries).addAll(wifiApBleScanDetail);
                    } finally {
                    }
                }
            }
        } else {
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isPrimaryUser: ", "WifiPickerTracker", z2);
        }
        notifyOnWifiEntriesChanged(i);
    }

    public WifiPickerTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        this(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, null, false);
    }

    public WifiPickerTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback, SemWifiPickerTrackerCallback semWifiPickerTrackerCallback, boolean z) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, semWifiPickerTrackerCallback, z);
    }

    public WifiPickerTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback, SemWifiPickerTrackerCallback semWifiPickerTrackerCallback, boolean z) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, "WifiPickerTracker");
        this.mConnected = new AtomicBoolean(false);
        this.mIsSettingSupportEasySetup = true;
        this.mEasySetupCandidateEntries = new ArrayList();
        this.mEasySetupEntries = new ArrayList();
        this.mSemEasySetupScanSettings = new ArrayList();
        this.mLockEasySetup = new Object();
        this.mLockAutoHotspot = new Object();
        this.mAutoHotspotEntries = new ArrayList();
        this.mActiveWifiEntries = new ArrayList();
        this.mWifiEntries = new ArrayList();
        this.mStandardWifiConfigCache = new ArrayMap();
        this.mSuggestedConfigCache = new ArrayMap();
        this.mNetworkRequestConfigCache = new ArrayMap();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mSuggestedWifiEntryCache = new ArrayList();
        this.mPasspointConfigCache = new ArrayMap();
        this.mPasspointWifiConfigCache = new SparseArray();
        this.mPasspointWifiEntryCache = new ArrayMap();
        this.mOsuWifiEntryCache = new ArrayMap();
        this.mKnownNetworkDataCache = new ArrayList();
        this.mKnownNetworkEntryCache = new ArrayList();
        this.mHotspotNetworkDataCache = new ArrayList();
        this.mHotspotNetworkEntryCache = new ArrayList();
        this.mListener = wifiPickerTrackerCallback;
        this.mIsSettingsTracker = z;
        this.mSemFilter = new SemWifiEntryFilter(context);
        SemWifiEntryFlags.isWpa3SaeSupported = -1;
        SemWifiEntryFlags.isWpa3OweSupported = -1;
        SemWifiEntryFlags.isWpa3SuiteBSupported = -1;
        SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
        SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin = -1;
        SemWifiEntryFlags.isShowBandSummaryOn = -1;
        SemWifiEntryFlags.isWepAllowed = -1;
        this.mEasySetupUtils = new EasySetupUtils(context);
    }
}
