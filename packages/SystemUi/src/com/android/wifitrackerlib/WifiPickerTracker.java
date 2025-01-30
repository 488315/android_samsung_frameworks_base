package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.os.Debug;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.samsung.android.wifi.SemEasySetupWifiScanSettings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.EasySetupUtils;
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
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WifiPickerTracker extends BaseWifiTracker {
    public final List mActiveWifiEntries;
    public final List mAutoHotspotEntries;
    public final AtomicBoolean mConnected;
    public final List mEasySetupCandidateEntries;
    public final List mEasySetupEntries;
    public final EasySetupUtils mEasySetupUtils;
    public final List mHotspotNetworkEntryCache;
    public final boolean mIsSettingSupportEasySetup;
    public final boolean mIsSettingsTracker;
    public boolean mIsSupportEasySetup;
    public final List mKnownNetworkEntryCache;
    public WifiInfo mLastWifiInfo;
    public final WifiPickerTrackerCallback mListener;
    public final Object mLock;
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
    public final List mWifiEntries;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SemWifiPickerTrackerCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface WifiPickerTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
    }

    public WifiPickerTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback);
    }

    public final void clearAllWifiEntries() {
        ((ArrayList) this.mStandardWifiEntryCache).clear();
        ((ArrayList) this.mSuggestedWifiEntryCache).clear();
        ((ArrayMap) this.mPasspointWifiEntryCache).clear();
        ((ArrayMap) this.mOsuWifiEntryCache).clear();
        this.mNetworkRequestEntry = null;
        this.mMergedCarrierEntry = null;
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updateSuggestedWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans(Collections.emptyList());
            updateOsuWifiEntryScans(Collections.emptyList());
            updateNetworkRequestEntryScans(Collections.emptyList());
            Collections.emptyList();
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            synchronized (scanResultUpdater.mLock) {
                ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).clear();
            }
            return;
        }
        long j = this.mMaxScanAgeMillis;
        if (z) {
            this.mScanResultUpdater.update(this.mWifiManager.getScanResults(), this.mLastWifiInfo);
        } else {
            j += this.mScanIntervalMillis;
        }
        List scanResults = this.mScanResultUpdater.getScanResults(j);
        updateStandardWifiEntryScans(scanResults);
        updateSuggestedWifiEntryScans(scanResults);
        updatePasspointWifiEntryScans(scanResults);
        updateOsuWifiEntryScans(scanResults);
        updateNetworkRequestEntryScans(scanResults);
    }

    public final void createConnectedNetworkRequestEntry(WifiInfo wifiInfo) {
        ArrayList arrayList = new ArrayList();
        if (wifiInfo != null) {
            int i = 0;
            while (true) {
                ArrayMap arrayMap = this.mNetworkRequestConfigCache;
                if (i >= arrayMap.size()) {
                    break;
                }
                List list = (List) arrayMap.valueAt(i);
                if (!list.isEmpty() && ((WifiConfiguration) list.get(0)).networkId == wifiInfo.getNetworkId()) {
                    arrayList.addAll(list);
                    break;
                }
                i++;
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) arrayList.get(0));
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null || !networkRequestEntry.mKey.equals(standardWifiEntryKey)) {
            NetworkRequestEntry networkRequestEntry2 = new NetworkRequestEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, this.mWifiManager, false);
            this.mNetworkRequestEntry = networkRequestEntry2;
            networkRequestEntry2.updateConfig(arrayList);
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            updateNetworkRequestEntryScans(scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
        }
    }

    public final PasspointWifiEntry createConnectedPasspointWifiEntry(WifiInfo wifiInfo) {
        WifiConfiguration wifiConfiguration = (WifiConfiguration) this.mPasspointWifiConfigCache.get(wifiInfo.getNetworkId());
        if (wifiConfiguration == null) {
            return null;
        }
        if (((ArrayMap) this.mPasspointWifiEntryCache).containsKey(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()))) {
            return null;
        }
        PasspointConfiguration passpointConfiguration = (PasspointConfiguration) ((ArrayMap) this.mPasspointConfigCache).get(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()));
        return passpointConfiguration != null ? new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, passpointConfiguration, this.mWifiManager, false) : new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager, false);
    }

    public final StandardWifiEntry createConnectedStandardWifiEntry(WifiInfo wifiInfo) {
        final int networkId = wifiInfo.getNetworkId();
        for (List list : ((ArrayMap) this.mStandardWifiConfigCache).values()) {
            if (list.stream().map(new WifiPickerTracker$$ExternalSyntheticLambda1(8)).filter(new Predicate() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Integer) obj).intValue() == networkId;
                }
            }).count() != 0) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list.get(0), true);
                Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
                while (it.hasNext()) {
                    if (standardWifiEntryKey.equals(((StandardWifiEntry) it.next()).mKey)) {
                        return null;
                    }
                }
                return new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, list, null, this.mWifiManager, false);
            }
        }
        return null;
    }

    public final StandardWifiEntry createConnectedSuggestedWifiEntry(WifiInfo wifiInfo) {
        int networkId = wifiInfo.getNetworkId();
        for (List list : ((ArrayMap) this.mSuggestedConfigCache).values()) {
            if (!list.isEmpty() && ((WifiConfiguration) list.get(0)).networkId == networkId) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list.get(0), true);
                Iterator it = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
                while (it.hasNext()) {
                    if (standardWifiEntryKey.equals(((StandardWifiEntry) it.next()).mKey)) {
                        return null;
                    }
                }
                return new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, list, null, this.mWifiManager, false);
            }
        }
        return null;
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mSuggestedWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        arrayList.addAll(((ArrayMap) this.mOsuWifiEntryCache).values());
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

    public final WifiEntry getConnectedWifiEntry() {
        synchronized (this.mLock) {
            if (((ArrayList) this.mActiveWifiEntries).isEmpty()) {
                return null;
            }
            WifiEntry wifiEntry = (WifiEntry) ((ArrayList) this.mActiveWifiEntries).get(0);
            if (wifiEntry.isPrimaryNetwork()) {
                return wifiEntry;
            }
            return null;
        }
    }

    public final Comparator getWifiComparator(Context context) {
        if (!SemWifiEntryFlags.isWifiDeveloperOptionOn(this.mContext)) {
            return WifiEntry.WIFI_PICKER_COMPARATOR;
        }
        int i = SemWifiUtils.$r8$clinit;
        int i2 = Settings.Global.getInt(context.getContentResolver(), "sec_wifi_developer_sorting_style", 3);
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? WifiEntry.WIFI_PICKER_COMPARATOR : WifiEntry.WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY : WifiEntry.WIFI_PICKER_COMPARATOR_RSSI : WifiEntry.WIFI_PICKER_COMPARATOR_ALPHABETICAL;
    }

    public final List getWifiEntries() {
        List list;
        synchronized (this.mLock) {
            list = (List) this.mWifiEntries.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda11(this, 1)).collect(Collectors.toList());
        }
        return list;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        HashMap hashMap = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
            hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        WifiManager wifiManager = this.mWifiManager;
        updateWifiConfigurations(hashMap, wifiManager.getPrivilegedConfiguredNetworks());
        updatePasspointConfigurations(hashMap, wifiManager.getPasspointConfigurations());
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        List scanResults = scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis);
        updateStandardWifiEntryScans(scanResults);
        updateNetworkRequestEntryScans(scanResults);
        updatePasspointWifiEntryScans(scanResults);
        updateOsuWifiEntryScans(scanResults);
        Handler handler = this.mMainHandler;
        int i = 0;
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda6(wifiPickerTrackerCallback, i));
        }
        int i2 = 2;
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda6(wifiPickerTrackerCallback, i2));
        }
        updateWifiEntries("config", false);
        intent.getBooleanExtra("multipleChanges", false);
        intent.getIntExtra("changeReason", 2);
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
    public final void handleDefaultSubscriptionChanged(int i) {
        if (i != -1) {
            MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
            if (mergedCarrierEntry != null && i == mergedCarrierEntry.mSubscriptionId) {
                return;
            }
            this.mMergedCarrierEntry = new MergedCarrierEntry(this.mWorkerHandler, this.mWifiManager, false, this.mContext, i);
            WifiManager wifiManager = this.mWifiManager;
            Network currentNetwork = wifiManager.getCurrentNetwork();
            if (currentNetwork != null) {
                ConnectivityManager connectivityManager = this.mConnectivityManager;
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
                if (networkCapabilities != null) {
                    this.mMergedCarrierEntry.onNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(wifiManager.getConnectionInfo()).build());
                }
                LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);
                if (linkProperties != null) {
                    this.mMergedCarrierEntry.updateLinkProperties(currentNetwork, linkProperties);
                }
            }
        } else if (this.mMergedCarrierEntry == null) {
            return;
        } else {
            this.mMergedCarrierEntry = null;
        }
        notifyOnWifiEntriesChanged$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateLinkProperties(network, linkProperties);
        }
        Log.d("WifiPickerTracker", "handleLinkPropertiesChanged");
        notifyOnWifiEntriesChanged$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        PasspointWifiEntry createConnectedPasspointWifiEntry;
        StandardWifiEntry createConnectedSuggestedWifiEntry;
        StandardWifiEntry createConnectedStandardWifiEntry;
        if (this.mNetworkRequestConfigCache.size() + this.mPasspointWifiConfigCache.size() + ((ArrayMap) this.mSuggestedConfigCache).size() + ((ArrayMap) this.mStandardWifiConfigCache).size() == 0) {
            HashMap hashMap = new HashMap();
            for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
                hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
            }
            updateWifiConfigurations(hashMap, this.mWifiManager.getPrivilegedConfiguredNetworks());
        }
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        WifiInfo wifiInfo = Utils.getWifiInfo(networkCapabilities);
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (wifiInfo != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp() && (createConnectedStandardWifiEntry = createConnectedStandardWifiEntry(wifiInfo)) != null) {
            createConnectedStandardWifiEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            createConnectedStandardWifiEntry.forceUpdateNetworkInfo(wifiInfo, connectivityManager.getNetworkInfo(network));
            Log.d("WifiPickerTracker", "No scanResult for current network. Create entry - capabilities");
            ((ArrayList) this.mStandardWifiEntryCache).add(createConnectedStandardWifiEntry);
        }
        WifiInfo wifiInfo2 = Utils.getWifiInfo(networkCapabilities);
        if (wifiInfo2 != null && !wifiInfo2.isPasspointAp() && !wifiInfo2.isOsuAp() && (createConnectedSuggestedWifiEntry = createConnectedSuggestedWifiEntry(wifiInfo2)) != null) {
            createConnectedSuggestedWifiEntry.forceUpdateNetworkInfo(wifiInfo2, connectivityManager.getNetworkInfo(network));
            createConnectedSuggestedWifiEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            ((ArrayList) this.mSuggestedWifiEntryCache).add(createConnectedSuggestedWifiEntry);
        }
        WifiInfo wifiInfo3 = Utils.getWifiInfo(networkCapabilities);
        if (wifiInfo3 != null && wifiInfo3.isPasspointAp() && (createConnectedPasspointWifiEntry = createConnectedPasspointWifiEntry(wifiInfo3)) != null) {
            createConnectedPasspointWifiEntry.forceUpdateNetworkInfo(wifiInfo3, connectivityManager.getNetworkInfo(network));
            createConnectedPasspointWifiEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            ((ArrayMap) this.mPasspointWifiEntryCache).put(createConnectedPasspointWifiEntry.mKey, createConnectedPasspointWifiEntry);
        }
        WifiInfo wifiInfo4 = Utils.getWifiInfo(networkCapabilities);
        createConnectedNetworkRequestEntry(wifiInfo4);
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null && wifiInfo4 != null) {
            networkRequestEntry.forceUpdateNetworkInfo(wifiInfo4, connectivityManager.getNetworkInfo(network));
            this.mNetworkRequestEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries("capabilities", false);
        Log.d("WifiPickerTracker", "handleNetworkCapabilitiesChanged");
        notifyOnWifiEntriesChanged$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null && networkRequestEntry.getConnectedState() == 0) {
            this.mNetworkRequestEntry = null;
        }
        updateWifiEntries("lost", false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        PasspointWifiEntry createConnectedPasspointWifiEntry;
        StandardWifiEntry createConnectedSuggestedWifiEntry;
        StandardWifiEntry createConnectedStandardWifiEntry;
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
        if (!connectionInfo.isPasspointAp() && !connectionInfo.isOsuAp() && (createConnectedStandardWifiEntry = createConnectedStandardWifiEntry(connectionInfo)) != null) {
            createConnectedStandardWifiEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
            ((ArrayList) this.mStandardWifiEntryCache).add(createConnectedStandardWifiEntry);
            Log.d("WifiPickerTracker", "No scanResult for current network. Create entry - networkState");
        }
        if (!connectionInfo.isPasspointAp() && !connectionInfo.isOsuAp() && (createConnectedSuggestedWifiEntry = createConnectedSuggestedWifiEntry(connectionInfo)) != null) {
            createConnectedSuggestedWifiEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
            ((ArrayList) this.mSuggestedWifiEntryCache).add(createConnectedSuggestedWifiEntry);
        }
        if (connectionInfo.isPasspointAp() && (createConnectedPasspointWifiEntry = createConnectedPasspointWifiEntry(connectionInfo)) != null) {
            createConnectedPasspointWifiEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
            ((ArrayMap) this.mPasspointWifiEntryCache).put(createConnectedPasspointWifiEntry.mKey, createConnectedPasspointWifiEntry);
        }
        createConnectedNetworkRequestEntry(connectionInfo);
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        updateWifiEntries("networkstate", false);
        networkInfo.isConnected();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        List<ScanResult> list;
        List list2;
        clearAllWifiEntries();
        HashMap hashMap = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration : this.mSemWifiManager.getConfiguredNetworks()) {
            hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        boolean z = this.mIsSettingsTracker;
        WifiManager wifiManager = this.mWifiManager;
        if (z) {
            updateWifiConfigurations(hashMap, wifiManager.getPrivilegedConfiguredNetworks());
        } else {
            updateWifiConfigurations(hashMap, wifiManager.getConfiguredNetworks());
        }
        int i = 0;
        boolean z2 = z && this.mIsSettingSupportEasySetup && Settings.Global.getInt(this.mContext.getContentResolver(), "safe_wifi", 0) == 0;
        this.mIsSupportEasySetup = z2;
        if (z2) {
            Map easySetupScanSettings = this.mEasySetupUtils.mSemWifiManager.getEasySetupScanSettings();
            if (easySetupScanSettings.size() != 0) {
                ArrayList arrayList = (ArrayList) this.mSemEasySetupScanSettings;
                arrayList.clear();
                for (SemEasySetupWifiScanSettings semEasySetupWifiScanSettings : easySetupScanSettings.values()) {
                    if (semEasySetupWifiScanSettings != null && semEasySetupWifiScanSettings.pendingIntentForSettings != null && (list2 = semEasySetupWifiScanSettings.ssidPatterns) != null && !list2.isEmpty()) {
                        arrayList.add(semEasySetupWifiScanSettings);
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("set EasySetup filter - minRssi : "), semEasySetupWifiScanSettings.minRssi, "WifiPickerTracker");
                    }
                }
            }
        }
        updatePasspointConfigurations(hashMap, wifiManager.getPasspointConfigurations());
        this.mLastWifiInfo = wifiManager.getConnectionInfo();
        try {
            list = wifiManager.getScanResults();
        } catch (Exception e) {
            Log.e("WifiPickerTracker", "failed to getScanResults", e);
            list = null;
        }
        if (list != null) {
            this.mScanResultUpdater.update(list, this.mLastWifiInfo);
        }
        conditionallyUpdateScanResults(true);
        handleDefaultSubscriptionChanged(SubscriptionManager.getDefaultDataSubscriptionId());
        Network currentNetwork = wifiManager.getCurrentNetwork();
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(currentNetwork);
        if (networkInfo != null) {
            updateForceNetworkInfo(wifiManager.getConnectionInfo(), networkInfo);
        }
        if (currentNetwork != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(wifiManager.getConnectionInfo()).build());
            }
            LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
        Handler handler = this.mMainHandler;
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda6(wifiPickerTrackerCallback, i));
        }
        if (wifiPickerTrackerCallback != null) {
            handler.post(new WifiPickerTracker$$ExternalSyntheticLambda6(wifiPickerTrackerCallback, 2));
        }
        updateWifiEntries("force", false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleQosScoreCacheUpdated() {
        WifiQoSScoredCache wifiQoSScoredCache;
        Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            wifiQoSScoredCache = this.mQoSScoredCache;
            if (!hasNext) {
                break;
            } else {
                ((StandardWifiEntry) it.next()).semUpdateScores(wifiQoSScoredCache);
            }
        }
        Iterator it2 = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
        while (it2.hasNext()) {
            ((StandardWifiEntry) it2.next()).semUpdateScores(wifiQoSScoredCache);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        boolean booleanExtra = intent.getBooleanExtra("resultsUpdated", true);
        handleQosScoreCacheUpdated();
        WifiManager wifiManager = this.mWifiManager;
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(wifiManager.getCurrentNetwork());
        if (networkInfo != null) {
            updateForceNetworkInfo(wifiManager.getConnectionInfo(), networkInfo);
        }
        conditionallyUpdateScanResults(booleanExtra);
        updateWifiEntries("scan", booleanExtra);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        if (this.mWifiState == 4) {
            this.mWifiState = this.mWifiManager.getWifiState();
        }
        if (this.mWifiState == 1) {
            clearAllWifiEntries();
        }
        updateWifiEntries(ImsProfile.PDN_WIFI, false);
    }

    public final void notifyOnWifiEntriesChanged$1() {
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            this.mMainHandler.post(new WifiPickerTracker$$ExternalSyntheticLambda6(wifiPickerTrackerCallback, 1));
        }
    }

    public final void updateForceNetworkInfo(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        Iterator it2 = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
        while (it2.hasNext()) {
            ((WifiEntry) it2.next()).forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        Iterator it3 = ((ArrayMap) this.mPasspointWifiEntryCache).values().iterator();
        while (it3.hasNext()) {
            ((WifiEntry) it3.next()).forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        Iterator it4 = ((ArrayMap) this.mOsuWifiEntryCache).values().iterator();
        while (it4.hasNext()) {
            ((WifiEntry) it4.next()).forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.forceUpdateNetworkInfo(wifiInfo, networkInfo);
        }
        if (networkInfo.isConnected() != this.mConnected.getAndSet(networkInfo.isConnected())) {
            networkInfo.isConnected();
        }
    }

    public final void updateNetworkRequestEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null) {
            return;
        }
        final StandardWifiEntry.ScanResultKey scanResultKey = networkRequestEntry.mKey.mScanResultKey;
        final int i = 0;
        this.mNetworkRequestEntry.updateScanResultInfo((List) list.stream().filter(new Predicate() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                switch (i) {
                    case 0:
                        return scanResultKey.equals(new StandardWifiEntry.ScanResultKey((ScanResult) obj));
                    default:
                        return ((KnownNetworkEntry) obj).mKey.mScanResultKey.equals(scanResultKey);
                }
            }
        }).collect(Collectors.toList()));
    }

    public final void updateOsuWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        WifiManager wifiManager = this.mWifiManager;
        Map matchingOsuProviders = wifiManager.getMatchingOsuProviders(list);
        Map matchingPasspointConfigsForOsuProviders = wifiManager.getMatchingPasspointConfigsForOsuProviders(matchingOsuProviders.keySet());
        ArrayMap arrayMap = (ArrayMap) this.mOsuWifiEntryCache;
        for (OsuWifiEntry osuWifiEntry : arrayMap.values()) {
            osuWifiEntry.updateScanResultInfo((List) matchingOsuProviders.remove(osuWifiEntry.mOsuProvider));
        }
        for (OsuProvider osuProvider : matchingOsuProviders.keySet()) {
            OsuWifiEntry osuWifiEntry2 = new OsuWifiEntry(this.mInjector, this.mContext, this.mMainHandler, osuProvider, this.mWifiManager, false);
            osuWifiEntry2.updateScanResultInfo((List) matchingOsuProviders.get(osuProvider));
            arrayMap.put(OsuWifiEntry.osuProviderToOsuWifiEntryKey(osuProvider), osuWifiEntry2);
        }
        arrayMap.values().forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(this, matchingPasspointConfigsForOsuProviders, 1));
        arrayMap.entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(14));
        arrayMap.entrySet().forEach(new WifiPickerTracker$$ExternalSyntheticLambda5(this, 3));
    }

    public final void updatePasspointConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ArrayMap arrayMap = (ArrayMap) this.mPasspointConfigCache;
        arrayMap.clear();
        arrayMap.putAll((Map) list.stream().collect(Collectors.toMap(new WifiPickerTracker$$ExternalSyntheticLambda1(9), Function.identity())));
        ((ArrayMap) this.mPasspointWifiEntryCache).entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda8(this, map, 1));
    }

    public final void updatePasspointWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        Iterator it = this.mWifiManager.getAllMatchingWifiConfigs(list).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            Map map = this.mPasspointWifiEntryCache;
            int i = 0;
            if (!hasNext) {
                ArrayMap arrayMap = (ArrayMap) map;
                arrayMap.entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda4(i, treeSet));
                arrayMap.entrySet().forEach(new WifiPickerTracker$$ExternalSyntheticLambda5(this, i));
                return;
            }
            Pair pair = (Pair) it.next();
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            List list2 = (List) ((Map) pair.second).get(0);
            List list3 = (List) ((Map) pair.second).get(1);
            if ("Vendor Hotspot2.0 Profile".equals(wifiConfiguration.providerFriendlyName)) {
                Log.d("WifiPickerTracker", "updatePasspointAccessPoints, Do not add if it is not matched with ANQP");
            } else {
                String uniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
                treeSet.add(uniqueIdToPasspointWifiEntryKey);
                ArrayMap arrayMap2 = (ArrayMap) map;
                if (!arrayMap2.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                    if (wifiConfiguration.fromWifiNetworkSuggestion) {
                        arrayMap2.put(uniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager, false));
                    } else {
                        ArrayMap arrayMap3 = (ArrayMap) this.mPasspointConfigCache;
                        if (arrayMap3.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                            arrayMap2.put(uniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (PasspointConfiguration) arrayMap3.get(uniqueIdToPasspointWifiEntryKey), this.mWifiManager, false));
                        }
                    }
                }
                ((PasspointWifiEntry) arrayMap2.get(uniqueIdToPasspointWifiEntryKey)).updateScanResultInfo(wifiConfiguration, list2, list3);
            }
        }
    }

    public final void updateStandardWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Map map = (Map) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(3)).collect(Collectors.groupingBy(new WifiPickerTracker$$ExternalSyntheticLambda1(5)));
        final ArraySet arraySet = new ArraySet(map.keySet());
        final int i = 1;
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        arrayList.forEach(new Consumer() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        Set set = arraySet;
                        Map map2 = map;
                        KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                        StandardWifiEntry.ScanResultKey scanResultKey = knownNetworkEntry.mKey.mScanResultKey;
                        set.remove(scanResultKey);
                        knownNetworkEntry.updateScanResultInfo((List) map2.get(scanResultKey));
                        break;
                    case 1:
                        Set set2 = arraySet;
                        Map map3 = map;
                        StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                        StandardWifiEntry.ScanResultKey scanResultKey2 = standardWifiEntry.mKey.mScanResultKey;
                        set2.remove(scanResultKey2);
                        standardWifiEntry.updateScanResultInfo((List) map3.get(scanResultKey2));
                        break;
                    default:
                        Set set3 = arraySet;
                        Map map4 = map;
                        HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) obj;
                        Long valueOf = Long.valueOf(hotspotNetworkEntry.mKey.mDeviceId);
                        set3.remove(valueOf);
                        hotspotNetworkEntry.updateHotspotNetworkData((HotspotNetwork) map4.get(valueOf));
                        break;
                }
            }
        });
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            StandardWifiEntry.ScanResultKey scanResultKey = (StandardWifiEntry.ScanResultKey) it.next();
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(scanResultKey, true);
            arrayList.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) ((ArrayMap) this.mStandardWifiConfigCache).get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false));
        }
        arrayList.removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(4));
        arrayList.forEach(new WifiPickerTracker$$ExternalSyntheticLambda5(this, i));
    }

    public final void updateSuggestedWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Set set = (Set) this.mWifiManager.getWifiConfigForMatchedNetworkSuggestionsSharedWithUser(list).stream().map(new WifiPickerTracker$$ExternalSyntheticLambda1(6)).collect(Collectors.toSet());
        final Map map = (Map) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(5)).collect(Collectors.groupingBy(new WifiPickerTracker$$ExternalSyntheticLambda1(7)));
        final ArraySet arraySet = new ArraySet();
        ArrayList arrayList = (ArrayList) this.mSuggestedWifiEntryCache;
        arrayList.forEach(new Consumer() { // from class: com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Set set2 = arraySet;
                Map map2 = map;
                Set set3 = set;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardWifiEntry.mKey;
                set2.add(standardWifiEntryKey);
                standardWifiEntry.updateScanResultInfo((List) map2.get(standardWifiEntryKey.mScanResultKey));
                boolean contains = set3.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = contains;
                }
            }
        });
        ArrayMap arrayMap = (ArrayMap) this.mSuggestedConfigCache;
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : arrayMap.keySet()) {
            StandardWifiEntry.ScanResultKey scanResultKey = standardWifiEntryKey.mScanResultKey;
            if (!arraySet.contains(standardWifiEntryKey) && map.containsKey(scanResultKey)) {
                StandardWifiEntry standardWifiEntry = new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) arrayMap.get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false);
                boolean contains = set.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = contains;
                }
                arrayList.add(standardWifiEntry);
            }
        }
        arrayList.removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(6));
        arrayList.forEach(new WifiPickerTracker$$ExternalSyntheticLambda5(this, 2));
    }

    public final void updateWifiConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ArrayMap arrayMap = (ArrayMap) this.mStandardWifiConfigCache;
        arrayMap.clear();
        ArrayMap arrayMap2 = (ArrayMap) this.mSuggestedConfigCache;
        arrayMap2.clear();
        ArrayMap arrayMap3 = this.mNetworkRequestConfigCache;
        arrayMap3.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
            if (!wifiConfiguration.carrierMerged) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(wifiConfiguration, true);
                if (wifiConfiguration.isPasspoint()) {
                    this.mPasspointWifiConfigCache.put(wifiConfiguration.networkId, wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSuggestion) {
                    if (!arrayMap2.containsKey(standardWifiEntryKey)) {
                        arrayMap2.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) arrayMap2.get(standardWifiEntryKey)).add(wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSpecifier) {
                    if (!arrayMap3.containsKey(standardWifiEntryKey)) {
                        arrayMap3.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) arrayMap3.get(standardWifiEntryKey)).add(wifiConfiguration);
                } else {
                    if (!arrayMap.containsKey(standardWifiEntryKey)) {
                        arrayMap.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) arrayMap.get(standardWifiEntryKey)).add(wifiConfiguration);
                }
            }
        }
        arrayMap.values().stream().flatMap(new WifiPickerTracker$$ExternalSyntheticLambda1(3)).filter(new WifiPickerTracker$$ExternalSyntheticLambda0(2)).map(new WifiPickerTracker$$ExternalSyntheticLambda1(4)).distinct().count();
        int i = 0;
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(this, map, i));
        ((ArrayList) this.mSuggestedWifiEntryCache).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda8(this, map, i));
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        updateSuggestedWifiEntryScans(scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.updateConfig((List) arrayMap3.get(networkRequestEntry.mKey));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:140:0x02be A[Catch: all -> 0x03fd, TryCatch #2 {, blocks: (B:4:0x000f, B:6:0x003b, B:7:0x0042, B:8:0x005c, B:10:0x0062, B:13:0x006c, B:18:0x0076, B:19:0x00cd, B:21:0x00d3, B:23:0x00e1, B:24:0x00ee, B:26:0x00f4, B:28:0x0102, B:29:0x010f, B:31:0x0115, B:34:0x0121, B:39:0x0127, B:41:0x012b, B:42:0x0134, B:46:0x013d, B:47:0x0145, B:49:0x014b, B:50:0x0157, B:52:0x015d, B:54:0x016a, B:55:0x0172, B:57:0x0178, B:60:0x0189, B:63:0x018f, B:66:0x0199, B:69:0x019f, B:70:0x01a8, B:80:0x01b4, B:98:0x01b7, B:99:0x01b8, B:100:0x01c0, B:102:0x01c6, B:105:0x01da, B:107:0x01e0, B:110:0x01eb, B:113:0x01f6, B:116:0x0201, B:133:0x0209, B:135:0x02ae, B:138:0x02b3, B:140:0x02be, B:141:0x02cd, B:143:0x02d3, B:145:0x02fc, B:146:0x0305, B:148:0x030b, B:150:0x0315, B:153:0x0342, B:155:0x034c, B:156:0x0372, B:158:0x0378, B:160:0x03a1, B:162:0x03a8, B:72:0x01a9, B:73:0x01b0, B:44:0x0135, B:45:0x013c), top: B:3:0x000f, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWifiEntries(String str, boolean z) {
        boolean z2;
        Log.d("WifiPickerTracker", "updateWifiEntries ".concat(str));
        synchronized (this.mLock) {
            ((ArrayList) this.mActiveWifiEntries).clear();
            ((ArrayList) this.mActiveWifiEntries).addAll(this.mStandardWifiEntryCache);
            ((ArrayList) this.mActiveWifiEntries).addAll(this.mSuggestedWifiEntryCache);
            ((ArrayList) this.mActiveWifiEntries).addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
            NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
            if (networkRequestEntry != null) {
                ((ArrayList) this.mActiveWifiEntries).add(networkRequestEntry);
            }
            ((ArrayList) this.mActiveWifiEntries).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda0(7));
            ArraySet arraySet = new ArraySet();
            Iterator it = ((ArrayList) this.mActiveWifiEntries).iterator();
            while (it.hasNext()) {
                WifiEntry wifiEntry = (WifiEntry) it.next();
                if (wifiEntry instanceof HotspotNetworkEntry) {
                    arraySet.add(((HotspotNetworkEntry) wifiEntry).mKey.mScanResultKey);
                }
            }
            ((ArrayList) this.mActiveWifiEntries).removeIf(new WifiPickerTracker$$ExternalSyntheticLambda4(1 == true ? 1 : 0, arraySet));
            ((ArrayList) this.mActiveWifiEntries).sort(getWifiComparator(this.mContext));
            ((ArrayList) this.mWifiEntries).clear();
            int i = 10;
            Set set = (Set) this.mSuggestedWifiEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda11(this, 0)).map(new WifiPickerTracker$$ExternalSyntheticLambda1(i)).collect(Collectors.toSet());
            ArraySet arraySet2 = new ArraySet();
            Iterator it2 = ((ArrayMap) this.mPasspointWifiEntryCache).values().iterator();
            while (it2.hasNext()) {
                arraySet2.addAll(((PasspointWifiEntry) it2.next()).getAllUtf8Ssids());
            }
            ArraySet arraySet3 = new ArraySet();
            Iterator it3 = ((ArrayList) this.mKnownNetworkEntryCache).iterator();
            while (it3.hasNext()) {
                arraySet3.add(((KnownNetworkEntry) it3.next()).mKey.mScanResultKey);
            }
            ArraySet arraySet4 = new ArraySet();
            Iterator it4 = ((ArrayList) this.mHotspotNetworkEntryCache).iterator();
            while (it4.hasNext()) {
                HotspotNetworkEntry.HotspotNetworkEntryKey hotspotNetworkEntryKey = ((HotspotNetworkEntry) it4.next()).mKey;
                if (!hotspotNetworkEntryKey.mIsVirtualEntry) {
                    arraySet4.add(hotspotNetworkEntryKey.mScanResultKey);
                }
            }
            if (this.mIsSupportEasySetup) {
                ((ArrayList) this.mEasySetupCandidateEntries).clear();
                synchronized (this.mLockEasySetup) {
                    ((ArrayList) this.mEasySetupEntries).clear();
                }
                Iterator it5 = ((ArrayList) this.mSemEasySetupScanSettings).iterator();
                while (it5.hasNext()) {
                    SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = (SemEasySetupWifiScanSettings) it5.next();
                    for (String str2 : semEasySetupWifiScanSettings.ssidPatterns) {
                        if (TextUtils.isEmpty(str2)) {
                            break;
                        }
                        Iterator it6 = ((ArrayList) this.mStandardWifiEntryCache).iterator();
                        while (it6.hasNext()) {
                            StandardWifiEntry standardWifiEntry = (StandardWifiEntry) it6.next();
                            if (!((ArrayList) this.mActiveWifiEntries).contains(standardWifiEntry) && standardWifiEntry.getSsid() != null && standardWifiEntry.getSsid().matches(str2) && standardWifiEntry.mRssi >= semEasySetupWifiScanSettings.minRssi) {
                                ((ArrayList) this.mEasySetupCandidateEntries).add(standardWifiEntry);
                                synchronized (this.mLockEasySetup) {
                                    ((ArrayList) this.mEasySetupEntries).add(standardWifiEntry);
                                }
                            }
                        }
                    }
                }
            }
            Iterator it7 = ((ArrayList) this.mStandardWifiEntryCache).iterator();
            while (it7.hasNext()) {
                StandardWifiEntry standardWifiEntry2 = (StandardWifiEntry) it7.next();
                standardWifiEntry2.updateAdminRestrictions();
                if (!((ArrayList) this.mActiveWifiEntries).contains(standardWifiEntry2) && (standardWifiEntry2.isSaved() || (!set.contains(standardWifiEntry2.mKey.mScanResultKey) && !arraySet2.contains(standardWifiEntry2.getSsid())))) {
                    if (!((ArrayList) this.mEasySetupCandidateEntries).contains(standardWifiEntry2)) {
                        ((ArrayList) this.mWifiEntries).add(standardWifiEntry2);
                    }
                }
            }
            ((ArrayList) this.mWifiEntries).addAll((Collection) this.mSuggestedWifiEntryCache.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(8)).collect(Collectors.toList()));
            ((ArrayList) this.mWifiEntries).addAll((Collection) ((ArrayMap) this.mPasspointWifiEntryCache).values().stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(9)).collect(Collectors.toList()));
            ((ArrayList) this.mWifiEntries).addAll((Collection) ((ArrayMap) this.mOsuWifiEntryCache).values().stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(i)).collect(Collectors.toList()));
            ((ArrayList) this.mWifiEntries).addAll((Collection) Collections.emptyList().stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda0(11)).collect(Collectors.toList()));
            Collections.sort(this.mWifiEntries, getWifiComparator(this.mContext));
            if (!Debug.semIsProductDev() && !BaseWifiTracker.sVerboseLogging) {
                z2 = Log.isLoggable("WifiPickerTracker", 3);
                if (z2) {
                    Log.d("WifiPickerTracker", "------ Dumping WifiEntries that were seen top of 5 scan ------");
                    Iterator it8 = ((ArrayList) this.mActiveWifiEntries).iterator();
                    while (it8.hasNext()) {
                        WifiEntry wifiEntry2 = (WifiEntry) it8.next();
                        Log.d("WifiPickerTracker", "Connected WifiEntry: " + wifiEntry2 + " - " + wifiEntry2.getSummary(true));
                    }
                    Iterator it9 = ((ArrayList) this.mWifiEntries).iterator();
                    int i2 = 1;
                    while (it9.hasNext()) {
                        WifiEntry wifiEntry3 = (WifiEntry) it9.next();
                        if (i2 > 5) {
                            break;
                        }
                        Log.d("WifiPickerTracker", "Entry " + i2 + " : " + wifiEntry3 + " - " + wifiEntry3.getSummary(true));
                        i2++;
                    }
                    if (((ArrayList) this.mEasySetupCandidateEntries).size() > 0) {
                        Log.d("WifiPickerTracker", "Easy setup networks ----- " + ((ArrayList) this.mEasySetupCandidateEntries).size());
                        Iterator it10 = ((ArrayList) this.mEasySetupCandidateEntries).iterator();
                        while (it10.hasNext()) {
                            WifiEntry wifiEntry4 = (WifiEntry) it10.next();
                            Log.d("WifiPickerTracker", "Entry : " + wifiEntry4 + " - " + wifiEntry4.getSummary(true));
                        }
                    }
                    Log.d("WifiPickerTracker", "---- Done dumping WifiEntries ----");
                }
            }
            z2 = true;
            if (z2) {
            }
        }
        boolean z3 = UserHandle.myUserId() == 0;
        if (z3) {
            List wifiApBleScanDetail = this.mSemWifiManager.getWifiApBleScanDetail();
            StringBuilder sb = new StringBuilder("update AutoHotspot Entries() : bleAccessPoints -> ");
            sb.append(wifiApBleScanDetail != null ? Integer.valueOf(wifiApBleScanDetail.size()) : "null");
            Log.d("WifiPickerTracker", sb.toString());
            synchronized (this.mLockAutoHotspot) {
                if (wifiApBleScanDetail != null) {
                    ((ArrayList) this.mAutoHotspotEntries).clear();
                    ((ArrayList) this.mAutoHotspotEntries).addAll(wifiApBleScanDetail);
                }
            }
        } else {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isPrimaryUser: ", z3, "WifiPickerTracker");
        }
        notifyOnWifiEntriesChanged$1();
    }

    public WifiPickerTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, null, false);
    }

    public WifiPickerTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback, SemWifiPickerTrackerCallback semWifiPickerTrackerCallback, boolean z) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, semWifiPickerTrackerCallback, z);
    }

    public WifiPickerTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback, SemWifiPickerTrackerCallback semWifiPickerTrackerCallback, boolean z) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, "WifiPickerTracker");
        this.mIsSettingSupportEasySetup = true;
        this.mEasySetupCandidateEntries = new ArrayList();
        this.mEasySetupEntries = new ArrayList();
        this.mSemEasySetupScanSettings = new ArrayList();
        this.mLock = new Object();
        this.mLockEasySetup = new Object();
        this.mLockAutoHotspot = new Object();
        this.mActiveWifiEntries = new ArrayList();
        this.mWifiEntries = new ArrayList();
        this.mAutoHotspotEntries = new ArrayList();
        this.mStandardWifiConfigCache = new ArrayMap();
        this.mSuggestedConfigCache = new ArrayMap();
        this.mNetworkRequestConfigCache = new ArrayMap();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mSuggestedWifiEntryCache = new ArrayList();
        this.mPasspointConfigCache = new ArrayMap();
        this.mPasspointWifiConfigCache = new SparseArray();
        this.mPasspointWifiEntryCache = new ArrayMap();
        this.mOsuWifiEntryCache = new ArrayMap();
        new ArrayList();
        this.mKnownNetworkEntryCache = new ArrayList();
        new ArrayList();
        this.mHotspotNetworkEntryCache = new ArrayList();
        this.mConnected = new AtomicBoolean(false);
        this.mIsSettingsTracker = z;
        this.mListener = wifiPickerTrackerCallback;
        this.mSemFilter = new SemWifiEntryFilter(context);
        SemWifiEntryFlags.isWpa3SaeSupported = -1;
        SemWifiEntryFlags.isWpa3OweSupported = -1;
        SemWifiEntryFlags.isWpa3SuiteBSupported = -1;
        SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
        SemWifiEntryFlags.isShowBandSummaryOn = -1;
        SemWifiEntryFlags.sVendor = null;
        this.mEasySetupUtils = new EasySetupUtils(context);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void semNotifyScanStateChanged() {
    }
}
