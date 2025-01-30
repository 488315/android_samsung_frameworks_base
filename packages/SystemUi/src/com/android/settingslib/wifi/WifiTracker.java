package com.android.settingslib.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes.dex */
public class WifiTracker implements LifecycleObserver, OnStart, OnStop, OnDestroy {
    static final long MAX_SCAN_RESULT_AGE_MILLIS = 15000;
    public static boolean sVerboseLogging;
    public final AtomicBoolean mConnected;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final IntentFilter mFilter;
    public final List mInternalAccessPoints;
    public WifiInfo mLastInfo;
    public NetworkInfo mLastNetworkInfo;
    public boolean mLastScanSucceeded;
    public final WifiListenerExecutor mListener;
    public final Object mLock;
    public long mMaxSpeedLabelScoreCacheAge;
    public WifiTrackerNetworkCallback mNetworkCallback;
    public final NetworkRequest mNetworkRequest;
    public final NetworkScoreManager mNetworkScoreManager;
    public boolean mNetworkScoringUiEnabled;
    final BroadcastReceiver mReceiver;
    public boolean mRegistered;
    public final Set mRequestedScores;
    public final HashMap mScanResultCache;
    Scanner mScanner;
    public WifiNetworkScoreCache mScoreCache;
    public boolean mStaleScanResults;
    public final WifiManager mWifiManager;
    Handler mWorkHandler;
    public HandlerThread mWorkThread;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Scanner extends Handler {
        public int mRetry = 0;

        public Scanner() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (WifiTracker.this.mWifiManager.startScan()) {
                this.mRetry = 0;
            } else {
                int i = this.mRetry + 1;
                this.mRetry = i;
                if (i >= 3) {
                    this.mRetry = 0;
                    Context context = WifiTracker.this.mContext;
                    if (context != null) {
                        Toast.makeText(context, R.string.wifi_fail_to_scan, 1).show();
                        return;
                    }
                    return;
                }
            }
            sendEmptyMessageDelayed(0, 10000L);
        }

        public boolean isScanning() {
            return hasMessages(0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface WifiListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class WifiListenerExecutor implements WifiListener {
        public final WifiListener mDelegatee;

        public WifiListenerExecutor(WifiListener wifiListener) {
            this.mDelegatee = wifiListener;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiTrackerNetworkCallback extends ConnectivityManager.NetworkCallback {
        public /* synthetic */ WifiTrackerNetworkCallback(WifiTracker wifiTracker, int i) {
            this();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            if (network.equals(WifiTracker.this.mWifiManager.getCurrentNetwork())) {
                WifiTracker.m369$$Nest$mupdateNetworkInfo(WifiTracker.this, null);
            }
        }

        private WifiTrackerNetworkCallback() {
        }
    }

    /* renamed from: -$$Nest$mupdateNetworkInfo, reason: not valid java name */
    public static void m369$$Nest$mupdateNetworkInfo(WifiTracker wifiTracker, NetworkInfo networkInfo) {
        WifiManager wifiManager = wifiTracker.mWifiManager;
        boolean z = false;
        if (!(wifiManager != null && wifiManager.isWifiEnabled())) {
            synchronized (wifiTracker.mLock) {
                if (!((ArrayList) wifiTracker.mInternalAccessPoints).isEmpty()) {
                    ((ArrayList) wifiTracker.mInternalAccessPoints).clear();
                    if (!wifiTracker.mStaleScanResults) {
                        WifiListenerExecutor wifiListenerExecutor = wifiTracker.mListener;
                        WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                        Objects.requireNonNull(wifiListener);
                        ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener, 1)));
                    }
                }
            }
            return;
        }
        if (networkInfo != null) {
            wifiTracker.mLastNetworkInfo = networkInfo;
            if (Log.isLoggable("WifiTracker", 3)) {
                Log.d("WifiTracker", "mLastNetworkInfo set: " + wifiTracker.mLastNetworkInfo);
            }
            if (networkInfo.isConnected() != wifiTracker.mConnected.getAndSet(networkInfo.isConnected())) {
                WifiListenerExecutor wifiListenerExecutor2 = wifiTracker.mListener;
                WifiListener wifiListener2 = wifiListenerExecutor2.mDelegatee;
                Objects.requireNonNull(wifiListener2);
                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor2, "Invoking onConnectedChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener2, 0)));
            }
        }
        wifiTracker.mLastInfo = wifiTracker.mWifiManager.getConnectionInfo();
        if (Log.isLoggable("WifiTracker", 3)) {
            Log.d("WifiTracker", "mLastInfo set as: " + wifiTracker.mLastInfo);
        }
        WifiInfo wifiInfo = wifiTracker.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? wifiTracker.getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), wifiTracker.mWifiManager.getConfiguredNetworks()) : null;
        synchronized (wifiTracker.mLock) {
            boolean z2 = false;
            for (int size = ((ArrayList) wifiTracker.mInternalAccessPoints).size() - 1; size >= 0; size--) {
                AccessPoint accessPoint = (AccessPoint) ((ArrayList) wifiTracker.mInternalAccessPoints).get(size);
                boolean isActive = accessPoint.isActive();
                if (accessPoint.update(wifiConfigurationForNetworkId, wifiTracker.mLastInfo, wifiTracker.mLastNetworkInfo)) {
                    if (isActive != accessPoint.isActive()) {
                        z = true;
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (accessPoint.update(wifiTracker.mScoreCache, wifiTracker.mNetworkScoringUiEnabled, wifiTracker.mMaxSpeedLabelScoreCacheAge)) {
                    z = true;
                    z2 = true;
                }
            }
            if (z) {
                Collections.sort(wifiTracker.mInternalAccessPoints);
            }
            if (z2 && !wifiTracker.mStaleScanResults) {
                WifiListenerExecutor wifiListenerExecutor3 = wifiTracker.mListener;
                WifiListener wifiListener3 = wifiListenerExecutor3.mDelegatee;
                Objects.requireNonNull(wifiListener3);
                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor3, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener3, 1)));
            }
        }
    }

    @Deprecated
    public WifiTracker(Context context, WifiListener wifiListener, boolean z, boolean z2) {
        this(context, wifiListener, (WifiManager) context.getSystemService(WifiManager.class), (ConnectivityManager) context.getSystemService(ConnectivityManager.class), (NetworkScoreManager) context.getSystemService(NetworkScoreManager.class), newIntentFilter());
    }

    public static AccessPoint getCachedByKey(String str, List list) {
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            AccessPoint accessPoint = (AccessPoint) listIterator.next();
            if (accessPoint.mKey.equals(str)) {
                listIterator.remove();
                return accessPoint;
            }
        }
        return null;
    }

    public static boolean isVerboseLoggingEnabled() {
        return sVerboseLogging;
    }

    public static IntentFilter newIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.NETWORK_IDS_CHANGED");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        intentFilter.addAction("android.net.wifi.LINK_CONFIGURATION_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        return intentFilter;
    }

    public final void fetchScansAndConfigsAndUpdateAccessPoints() {
        ArrayList arrayList;
        boolean z;
        List<ScanResult> scanResults = this.mWifiManager.getScanResults();
        if (scanResults == null) {
            arrayList = null;
        } else {
            WifiManager wifiManager = this.mWifiManager;
            boolean isEnhancedOpenSupported = wifiManager.isEnhancedOpenSupported();
            boolean isWpa3SaeSupported = wifiManager.isWpa3SaeSupported();
            boolean isWpa3SuiteBSupported = wifiManager.isWpa3SuiteBSupported();
            arrayList = new ArrayList();
            for (ScanResult scanResult : scanResults) {
                if (scanResult.capabilities.contains("PSK")) {
                    arrayList.add(scanResult);
                } else if (!scanResult.capabilities.contains("SUITE_B_192") || isWpa3SuiteBSupported) {
                    if (!scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE) || isWpa3SaeSupported) {
                        if (!scanResult.capabilities.contains("OWE") || isEnhancedOpenSupported) {
                            arrayList.add(scanResult);
                        }
                    }
                }
            }
        }
        if (isVerboseLoggingEnabled()) {
            Log.i("WifiTracker", "Fetched scan results: " + arrayList);
        }
        List configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        WifiInfo wifiInfo = this.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), configuredNetworks) : null;
        synchronized (this.mLock) {
            try {
                ArrayMap updateScanResultCache = updateScanResultCache(arrayList);
                List<AccessPoint> arrayList2 = new ArrayList<>(this.mInternalAccessPoints);
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                for (Map.Entry entry : updateScanResultCache.entrySet()) {
                    Iterator it = ((List) entry.getValue()).iterator();
                    while (it.hasNext()) {
                        NetworkKey createFromScanResult = NetworkKey.createFromScanResult((ScanResult) it.next());
                        if (createFromScanResult != null && !((ArraySet) this.mRequestedScores).contains(createFromScanResult)) {
                            arrayList4.add(createFromScanResult);
                        }
                    }
                    List list = (List) entry.getValue();
                    ScanResult scanResult2 = (ScanResult) list.get(0);
                    Context context = this.mContext;
                    int i = AccessPoint.$r8$clinit;
                    final AccessPoint cachedByKey = getCachedByKey(AccessPoint.getKey(AccessPoint.getSecurity(context, scanResult2), scanResult2.SSID, scanResult2.BSSID), arrayList2);
                    if (cachedByKey == null) {
                        cachedByKey = new AccessPoint(context, list);
                    } else {
                        cachedByKey.setScanResults(list);
                    }
                    List list2 = (List) configuredNetworks.stream().filter(new Predicate() { // from class: com.android.settingslib.wifi.WifiTracker$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return AccessPoint.this.matches((WifiConfiguration) obj);
                        }
                    }).collect(Collectors.toList());
                    int size = list2.size();
                    if (size == 0) {
                        cachedByKey.update(null);
                    } else if (size == 1) {
                        cachedByKey.update((WifiConfiguration) list2.get(0));
                    } else {
                        Optional findFirst = list2.stream().filter(new WifiTracker$$ExternalSyntheticLambda1()).findFirst();
                        if (findFirst.isPresent()) {
                            cachedByKey.update((WifiConfiguration) findFirst.get());
                        } else {
                            cachedByKey.update((WifiConfiguration) list2.get(0));
                        }
                    }
                    arrayList3.add(cachedByKey);
                }
                ArrayList arrayList5 = new ArrayList(this.mScanResultCache.values());
                arrayList3.addAll(updatePasspointAccessPoints(this.mWifiManager.getAllMatchingWifiConfigs(arrayList5), arrayList2));
                arrayList3.addAll(updateOsuAccessPoints(this.mWifiManager.getMatchingOsuProviders(arrayList5), arrayList2));
                if (this.mLastInfo != null && this.mLastNetworkInfo != null) {
                    Iterator it2 = arrayList3.iterator();
                    while (it2.hasNext()) {
                        ((AccessPoint) it2.next()).update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                    }
                }
                if (arrayList3.isEmpty() && wifiConfigurationForNetworkId != null) {
                    AccessPoint accessPoint = new AccessPoint(this.mContext, wifiConfigurationForNetworkId);
                    accessPoint.update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                    arrayList3.add(accessPoint);
                    arrayList4.add(NetworkKey.createFromWifiInfo(this.mLastInfo));
                }
                requestScoresForNetworkKeys(arrayList4);
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    ((AccessPoint) it3.next()).update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge);
                }
                Collections.sort(arrayList3);
                if (Log.isLoggable("WifiTracker", 3)) {
                    Log.d("WifiTracker", "------ Dumping AccessPoints that were not seen on this scan ------");
                    Iterator it4 = ((ArrayList) this.mInternalAccessPoints).iterator();
                    while (it4.hasNext()) {
                        String title = ((AccessPoint) it4.next()).getTitle();
                        Iterator it5 = arrayList3.iterator();
                        while (true) {
                            if (!it5.hasNext()) {
                                z = false;
                                break;
                            }
                            AccessPoint accessPoint2 = (AccessPoint) it5.next();
                            if (accessPoint2.getTitle() != null && accessPoint2.getTitle().equals(title)) {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            Log.d("WifiTracker", "Did not find " + title + " in this scan");
                        }
                    }
                    Log.d("WifiTracker", "---- Done dumping AccessPoints that were not seen on this scan ----");
                }
                ((ArrayList) this.mInternalAccessPoints).clear();
                ((ArrayList) this.mInternalAccessPoints).addAll(arrayList3);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mStaleScanResults) {
            return;
        }
        WifiListenerExecutor wifiListenerExecutor = this.mListener;
        WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
        Objects.requireNonNull(wifiListener);
        ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener, 1)));
    }

    public void forceUpdate() {
        WifiManager wifiManager = this.mWifiManager;
        this.mLastInfo = wifiManager.getConnectionInfo();
        this.mLastNetworkInfo = this.mConnectivityManager.getNetworkInfo(wifiManager.getCurrentNetwork());
        fetchScansAndConfigsAndUpdateAccessPoints();
    }

    public final WifiConfiguration getWifiConfigurationForNetworkId(int i, List list) {
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
            if (this.mLastInfo != null && i == wifiConfiguration.networkId) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        this.mWorkThread.quit();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        forceUpdate();
        this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mScoreCache, 2);
        int i = 0;
        this.mNetworkScoringUiEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "network_scoring_ui_enabled", 0) == 1;
        this.mMaxSpeedLabelScoreCacheAge = Settings.Global.getLong(this.mContext.getContentResolver(), "speed_label_cache_eviction_age_millis", 1200000L);
        synchronized (this.mLock) {
            try {
                if (this.mScanner == null) {
                    this.mScanner = new Scanner();
                }
                WifiManager wifiManager = this.mWifiManager;
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    Scanner scanner = this.mScanner;
                    if (isVerboseLoggingEnabled()) {
                        scanner.getClass();
                        Log.d("WifiTracker", "Scanner resume");
                    }
                    if (!scanner.hasMessages(0)) {
                        scanner.sendEmptyMessage(0);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mRegistered) {
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, null, this.mWorkHandler, 2);
        WifiTrackerNetworkCallback wifiTrackerNetworkCallback = new WifiTrackerNetworkCallback(this, i);
        this.mNetworkCallback = wifiTrackerNetworkCallback;
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, wifiTrackerNetworkCallback, this.mWorkHandler);
        this.mRegistered = true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mRegistered = false;
        }
        this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mScoreCache);
        synchronized (this.mLock) {
            ((ArraySet) this.mRequestedScores).clear();
        }
        synchronized (this.mLock) {
            try {
                Scanner scanner = this.mScanner;
                if (scanner != null) {
                    if (isVerboseLoggingEnabled()) {
                        Log.d("WifiTracker", "Scanner pause");
                    }
                    scanner.mRetry = 0;
                    scanner.removeMessages(0);
                    this.mScanner = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mStaleScanResults = true;
        this.mWorkHandler.removeCallbacksAndMessages(null);
    }

    public final void requestScoresForNetworkKeys(Collection collection) {
        ArrayList arrayList = (ArrayList) collection;
        if (arrayList.isEmpty()) {
            return;
        }
        if (Log.isLoggable("WifiTracker", 3)) {
            Log.d("WifiTracker", "Requesting scores for Network Keys: " + collection);
        }
        this.mNetworkScoreManager.requestScores((NetworkKey[]) arrayList.toArray(new NetworkKey[arrayList.size()]));
        synchronized (this.mLock) {
            ((ArraySet) this.mRequestedScores).addAll(collection);
        }
    }

    public void setWorkThread(HandlerThread handlerThread) {
        this.mWorkThread = handlerThread;
        this.mWorkHandler = new Handler(handlerThread.getLooper());
        this.mScoreCache = new WifiNetworkScoreCache(this.mContext, new WifiNetworkScoreCache.CacheListener(this.mWorkHandler) { // from class: com.android.settingslib.wifi.WifiTracker.1
            public final void networkCacheUpdated(List list) {
                WifiTracker wifiTracker = WifiTracker.this;
                if (wifiTracker.mRegistered) {
                    synchronized (wifiTracker.mLock) {
                        boolean z = false;
                        for (int i = 0; i < ((ArrayList) wifiTracker.mInternalAccessPoints).size(); i++) {
                            if (((AccessPoint) ((ArrayList) wifiTracker.mInternalAccessPoints).get(i)).update(wifiTracker.mScoreCache, wifiTracker.mNetworkScoringUiEnabled, wifiTracker.mMaxSpeedLabelScoreCacheAge)) {
                                z = true;
                            }
                        }
                        if (z) {
                            Collections.sort(wifiTracker.mInternalAccessPoints);
                            if (!wifiTracker.mStaleScanResults) {
                                WifiListenerExecutor wifiListenerExecutor = wifiTracker.mListener;
                                WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                                Objects.requireNonNull(wifiListener);
                                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener, 1)));
                            }
                        }
                    }
                }
            }
        });
    }

    public List<AccessPoint> updateOsuAccessPoints(Map<OsuProvider, List<ScanResult>> map, List<AccessPoint> list) {
        ArrayList arrayList = new ArrayList();
        Set keySet = this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(map.keySet()).keySet();
        for (OsuProvider osuProvider : map.keySet()) {
            if (!keySet.contains(osuProvider)) {
                List<ScanResult> list2 = map.get(osuProvider);
                AccessPoint cachedByKey = getCachedByKey(AccessPoint.getKey(osuProvider), list);
                if (cachedByKey == null) {
                    cachedByKey = new AccessPoint(this.mContext, osuProvider, list2);
                } else {
                    cachedByKey.setScanResults(list2);
                }
                arrayList.add(cachedByKey);
            }
        }
        return arrayList;
    }

    public List<AccessPoint> updatePasspointAccessPoints(List<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>> list, List<AccessPoint> list2) {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        for (Pair<WifiConfiguration, Map<Integer, List<ScanResult>>> pair : list) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            if (arraySet.add(wifiConfiguration.FQDN)) {
                List list3 = (List) ((Map) pair.second).get(0);
                List list4 = (List) ((Map) pair.second).get(1);
                AccessPoint cachedByKey = getCachedByKey(AccessPoint.getKey(wifiConfiguration), list2);
                if (cachedByKey == null) {
                    cachedByKey = new AccessPoint(this.mContext, wifiConfiguration, list3, list4);
                } else {
                    cachedByKey.update(wifiConfiguration);
                    cachedByKey.setScanResultsPasspoint(list3, list4);
                }
                arrayList.add(cachedByKey);
            }
        }
        return arrayList;
    }

    public final ArrayMap updateScanResultCache(List list) {
        HashMap hashMap;
        List list2;
        Iterator it = list.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            hashMap = this.mScanResultCache;
            if (!hasNext) {
                break;
            }
            ScanResult scanResult = (ScanResult) it.next();
            String str = scanResult.SSID;
            if (str != null && !str.isEmpty()) {
                hashMap.put(scanResult.BSSID, scanResult);
            }
        }
        long j = this.mLastScanSucceeded ? MAX_SCAN_RESULT_AGE_MILLIS : 30000L;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator it2 = hashMap.values().iterator();
        while (it2.hasNext()) {
            if (elapsedRealtime - (((ScanResult) it2.next()).timestamp / 1000) > j) {
                it2.remove();
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        for (ScanResult scanResult2 : hashMap.values()) {
            String str2 = scanResult2.SSID;
            if (str2 != null && str2.length() != 0 && !scanResult2.capabilities.contains("[IBSS]")) {
                int i = AccessPoint.$r8$clinit;
                String key = AccessPoint.getKey(AccessPoint.getSecurity(this.mContext, scanResult2), scanResult2.SSID, scanResult2.BSSID);
                if (arrayMap.containsKey(key)) {
                    list2 = (List) arrayMap.get(key);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayMap.put(key, arrayList);
                    list2 = arrayList;
                }
                list2.add(scanResult2);
            }
        }
        return arrayMap;
    }

    public WifiTracker(Context context, WifiListener wifiListener, Lifecycle lifecycle, boolean z, boolean z2) {
        this(context, wifiListener, (WifiManager) context.getSystemService(WifiManager.class), (ConnectivityManager) context.getSystemService(ConnectivityManager.class), (NetworkScoreManager) context.getSystemService(NetworkScoreManager.class), newIntentFilter());
        lifecycle.addObserver(this);
    }

    public WifiTracker(Context context, WifiListener wifiListener, WifiManager wifiManager, ConnectivityManager connectivityManager, NetworkScoreManager networkScoreManager, IntentFilter intentFilter) {
        boolean z = false;
        this.mConnected = new AtomicBoolean(false);
        this.mLock = new Object();
        this.mInternalAccessPoints = new ArrayList();
        this.mRequestedScores = new ArraySet();
        this.mStaleScanResults = true;
        this.mLastScanSucceeded = true;
        this.mScanResultCache = new HashMap();
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.wifi.WifiTracker.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                    if ("android.net.wifi.SCAN_RESULTS".equals(action)) {
                        WifiTracker wifiTracker = WifiTracker.this;
                        wifiTracker.mStaleScanResults = false;
                        wifiTracker.mLastScanSucceeded = intent.getBooleanExtra("resultsUpdated", true);
                        WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                        return;
                    }
                    if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action) || "android.net.wifi.LINK_CONFIGURATION_CHANGED".equals(action)) {
                        WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                        return;
                    }
                    if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                        WifiTracker.m369$$Nest$mupdateNetworkInfo(WifiTracker.this, (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO));
                        WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                        return;
                    } else {
                        if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                            WifiTracker.m369$$Nest$mupdateNetworkInfo(WifiTracker.this, null);
                            return;
                        }
                        return;
                    }
                }
                WifiTracker wifiTracker2 = WifiTracker.this;
                int intExtra = intent.getIntExtra("wifi_state", 4);
                if (WifiTracker.isVerboseLoggingEnabled()) {
                    wifiTracker2.getClass();
                    Log.d("WifiTracker", "updateWifiState: " + intExtra);
                }
                if (intExtra == 3) {
                    synchronized (wifiTracker2.mLock) {
                        Scanner scanner = wifiTracker2.mScanner;
                        if (scanner != null) {
                            if (WifiTracker.isVerboseLoggingEnabled()) {
                                Log.d("WifiTracker", "Scanner resume");
                            }
                            if (!scanner.hasMessages(0)) {
                                scanner.sendEmptyMessage(0);
                            }
                        }
                    }
                } else {
                    synchronized (wifiTracker2.mLock) {
                        if (!((ArrayList) wifiTracker2.mInternalAccessPoints).isEmpty()) {
                            ((ArrayList) wifiTracker2.mInternalAccessPoints).clear();
                            if (!wifiTracker2.mStaleScanResults) {
                                WifiListenerExecutor wifiListenerExecutor = wifiTracker2.mListener;
                                WifiListener wifiListener2 = wifiListenerExecutor.mDelegatee;
                                Objects.requireNonNull(wifiListener2);
                                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener2, 1)));
                            }
                        }
                    }
                    wifiTracker2.mLastInfo = null;
                    wifiTracker2.mLastNetworkInfo = null;
                    synchronized (wifiTracker2.mLock) {
                        try {
                            Scanner scanner2 = wifiTracker2.mScanner;
                            if (scanner2 != null) {
                                if (WifiTracker.isVerboseLoggingEnabled()) {
                                    Log.d("WifiTracker", "Scanner pause");
                                }
                                scanner2.mRetry = 0;
                                scanner2.removeMessages(0);
                            }
                        } finally {
                        }
                    }
                    wifiTracker2.mStaleScanResults = true;
                }
                WifiListenerExecutor wifiListenerExecutor2 = wifiTracker2.mListener;
                wifiListenerExecutor2.getClass();
                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor2, String.format("Invoking onWifiStateChanged callback with state %d", Integer.valueOf(intExtra)), new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1(wifiListenerExecutor2, intExtra)));
            }
        };
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mListener = new WifiListenerExecutor(wifiListener);
        this.mConnectivityManager = connectivityManager;
        if (wifiManager != null && wifiManager.isVerboseLoggingEnabled()) {
            z = true;
        }
        sVerboseLogging = z;
        this.mFilter = intentFilter;
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
        this.mNetworkScoreManager = networkScoreManager;
        HandlerThread handlerThread = new HandlerThread("WifiTracker{" + Integer.toHexString(System.identityHashCode(this)) + "}", 10);
        handlerThread.start();
        setWorkThread(handlerThread);
    }
}
