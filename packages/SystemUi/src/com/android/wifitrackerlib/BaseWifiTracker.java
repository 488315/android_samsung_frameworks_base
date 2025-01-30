package com.android.wifitrackerlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BaseWifiTracker implements LifecycleObserver {
    public static boolean sVerboseLogging;
    public final C37638 mConnectivityDiagnosticsCallback;
    public final ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final C37649 mDefaultNetworkCallback;
    public final WifiTrackerInjector mInjector;
    public final BaseWifiTrackerCallback mListener;
    public final Handler mMainHandler;
    public final long mMaxScanAgeMillis;
    public final C37582 mNetworkCallback;
    public boolean mNetworkScoringUiEnabled;
    public final WifiQoSScoredCache mQoSScoredCache;
    public final long mScanIntervalMillis;
    public final ScanResultUpdater mScanResultUpdater;
    public final Scanner mScanner;
    public final SemWifiManager mSemWifiManager;
    public final String mTag;
    public final WifiManager mWifiManager;
    public WifiScanner mWifiScanner;
    public final Handler mWorkerHandler;
    public int mWifiState = 4;
    public boolean mIsInitialized = false;
    public final C37571 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.wifitrackerlib.BaseWifiTracker.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Map qoSScores;
            String action = intent.getAction();
            if (BaseWifiTracker.sVerboseLogging) {
                String str = BaseWifiTracker.this.mTag;
            }
            int i = 0;
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                BaseWifiTracker.this.mWifiState = intent.getIntExtra("wifi_state", 1);
                if (BaseWifiTracker.sVerboseLogging) {
                    String str2 = BaseWifiTracker.this.mTag;
                }
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                Scanner scanner = baseWifiTracker.mScanner;
                boolean z = baseWifiTracker.mWifiState == 3;
                boolean z2 = scanner.mIsWifiEnabled;
                scanner.mIsWifiEnabled = z;
                if (z != z2) {
                    if (z) {
                        scanner.possiblyStartScanning();
                    } else {
                        scanner.stopScanning();
                    }
                }
                BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
                BaseWifiTrackerCallback baseWifiTrackerCallback = baseWifiTracker2.mListener;
                if (baseWifiTrackerCallback != null) {
                    baseWifiTracker2.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(baseWifiTrackerCallback, 2));
                }
                BaseWifiTracker.this.handleWifiStateChangedAction();
                return;
            }
            if (!"android.net.wifi.SCAN_RESULTS".equals(action)) {
                if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action)) {
                    BaseWifiTracker.this.handleConfiguredNetworksChangedAction(intent);
                    return;
                } else if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                    BaseWifiTracker.this.handleNetworkStateChangedAction(intent);
                    return;
                } else {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                        BaseWifiTracker.this.handleDefaultSubscriptionChanged(intent.getIntExtra("subscription", -1));
                        return;
                    }
                    return;
                }
            }
            BaseWifiTracker baseWifiTracker3 = BaseWifiTracker.this;
            if (baseWifiTracker3.mNetworkScoringUiEnabled) {
                WifiQoSScoredCache wifiQoSScoredCache = baseWifiTracker3.mQoSScoredCache;
                Collection collection = (Collection) baseWifiTracker3.mWifiManager.getScanResults().stream().map(new BaseWifiTracker$1$$ExternalSyntheticLambda0()).filter(new BaseWifiTracker$1$$ExternalSyntheticLambda1()).collect(Collectors.toList());
                wifiQoSScoredCache.getClass();
                HashSet hashSet = new HashSet(collection);
                if (!hashSet.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    HashMap hashMap = new HashMap();
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        NetworkKey networkKey = (NetworkKey) it.next();
                        hashMap.put(networkKey.wifiKey.bssid, networkKey);
                        arrayList.add(networkKey.wifiKey.bssid);
                    }
                    if (arrayList.size() != 0 && (qoSScores = wifiQoSScoredCache.mSemWifiManager.getQoSScores(arrayList)) != null && !qoSScores.isEmpty()) {
                        synchronized (wifiQoSScoredCache.mLock) {
                            ((HashMap) wifiQoSScoredCache.mCache).clear();
                        }
                        wifiQoSScoredCache.mUpdated = false;
                        if (Debug.semIsProductDev() ? true : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                            Log.d("WifiTracker.WifiWifiQoSScoreCache", "------ Add scored data start -----");
                        }
                        Iterator it2 = qoSScores.entrySet().iterator();
                        while (it2.hasNext()) {
                            String str3 = (String) ((Map.Entry) it2.next()).getKey();
                            if (hashSet.contains(hashMap.get(str3))) {
                                Map map = (Map) qoSScores.get(str3);
                                WifiScoredNetwork wifiScoredNetwork = new WifiScoredNetwork(str3, ((Integer) map.get("networkType")).intValue(), new int[]{((Integer) map.get("levelMax-2")).intValue(), ((Integer) map.get("levelMax-1")).intValue(), ((Integer) map.get("levelMax")).intValue()});
                                if (!TextUtils.isEmpty(str3) && wifiScoredNetwork.networkType != 3) {
                                    synchronized (wifiQoSScoredCache.mLock) {
                                        try {
                                            ((HashMap) wifiQoSScoredCache.mCache).put(str3, wifiScoredNetwork);
                                            if (Debug.semIsProductDev() ? true : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                                                LogUtils logUtils = wifiQoSScoredCache.mLog;
                                                String wifiScoredNetwork2 = wifiScoredNetwork.toString();
                                                if (logUtils.isProductDev) {
                                                    Log.d("WifiTracker.WifiWifiQoSScoreCache", logUtils.getPrintableLog(wifiScoredNetwork2));
                                                }
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    wifiQoSScoredCache.mUpdated = true;
                                }
                                hashSet.remove(hashMap.get(str3));
                                i++;
                            }
                        }
                        if (Debug.semIsProductDev() ? true : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                            Log.d("WifiTracker.WifiWifiQoSScoreCache", "------ Add scored data end -----");
                        }
                        Log.d("WifiTracker.WifiWifiQoSScoreCache", i + " key set are removed");
                        WifiQoSScoredCache.SemCacheListener semCacheListener = wifiQoSScoredCache.mListener;
                        if (semCacheListener != null && wifiQoSScoredCache.mUpdated) {
                            semCacheListener.mHandler.post(new Runnable() { // from class: com.samsung.android.wifitrackerlib.WifiQoSScoredCache.SemCacheListener.1
                                public RunnableC47831() {
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    SemCacheListener.this.networkCacheUpdated();
                                }
                            });
                        }
                    }
                }
            }
            BaseWifiTracker.this.handleScanResultsAvailableAction(intent);
            BaseWifiTracker.this.semNotifyScanStateChanged();
        }
    };
    public final NetworkRequest mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).addTransportType(0).build();
    public final ExecutorC37593 mConnectivityDiagnosticsExecutor = new Executor() { // from class: com.android.wifitrackerlib.BaseWifiTracker.3
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            BaseWifiTracker.this.mWorkerHandler.post(runnable);
        }
    };
    public final ExecutorC37604 mSharedConnectivityExecutor = new Executor() { // from class: com.android.wifitrackerlib.BaseWifiTracker.4
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            BaseWifiTracker.this.mWorkerHandler.post(runnable);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BaseWifiTrackerCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Scanner extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final C37651 mFirstScanListener;
        public boolean mIsStartedState;
        public boolean mIsWifiEnabled;

        public /* synthetic */ Scanner(BaseWifiTracker baseWifiTracker, Looper looper, int i) {
            this(looper);
        }

        public final void possiblyStartScanning() {
            boolean z = false;
            if (this.mIsWifiEnabled && this.mIsStartedState) {
                Log.i(BaseWifiTracker.this.mTag, "Scanning started");
                String str = Build.VERSION.CODENAME;
                if (!"REL".equals(str)) {
                    Locale locale = Locale.ROOT;
                    if (str.toUpperCase(locale).compareTo("UpsideDownCake".toUpperCase(locale)) >= 0) {
                        z = true;
                    }
                }
                if (z) {
                    WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
                    scanSettings.band = 3;
                    scanSettings.setRnrSetting(1);
                    BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                    if (baseWifiTracker.mWifiScanner == null) {
                        baseWifiTracker.mWifiScanner = (WifiScanner) baseWifiTracker.mContext.getSystemService(WifiScanner.class);
                    }
                    BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
                    WifiScanner wifiScanner = baseWifiTracker2.mWifiScanner;
                    if (wifiScanner != null) {
                        wifiScanner.stopScan(this.mFirstScanListener);
                        if (BaseWifiTracker.sVerboseLogging) {
                            String str2 = BaseWifiTracker.this.mTag;
                        }
                        BaseWifiTracker.this.mWifiScanner.startScan(scanSettings, this.mFirstScanListener);
                        return;
                    }
                    Log.e(baseWifiTracker2.mTag, "Failed to retrieve WifiScanner!");
                }
                scanLoop();
            }
        }

        public final void scanLoop() {
            if (!(this.mIsWifiEnabled && this.mIsStartedState)) {
                Log.wtf(BaseWifiTracker.this.mTag, "Scan loop called even though we shouldn't be scanning! mIsWifiEnabled=" + this.mIsWifiEnabled + " mIsStartedState=" + this.mIsStartedState);
                return;
            }
            if ("WifiPickerTracker".equals(BaseWifiTracker.this.mTag)) {
                Log.d(BaseWifiTracker.this.mTag, "Starting BLE scan for AutoHotspot");
                BaseWifiTracker.this.mSemWifiManager.wifiApBleClientRole(true);
            }
            if (BaseWifiTracker.sVerboseLogging) {
                String str = BaseWifiTracker.this.mTag;
            }
            removeCallbacksAndMessages(null);
            BaseWifiTracker.this.mSemWifiManager.startScan();
            BaseWifiTracker.this.semNotifyScanStateChanged();
            postDelayed(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(this, 2), BaseWifiTracker.this.mScanIntervalMillis);
        }

        public final void stopScanning() {
            Log.i(BaseWifiTracker.this.mTag, "Scanning stopped");
            removeCallbacksAndMessages(null);
            if ("WifiPickerTracker".equals(BaseWifiTracker.this.mTag)) {
                Log.d(BaseWifiTracker.this.mTag, "Stopping BLE scan for AutoHotspot");
                BaseWifiTracker.this.mSemWifiManager.wifiApBleClientRole(false);
            }
        }

        private Scanner(Looper looper) {
            super(looper);
            this.mIsStartedState = false;
            this.mIsWifiEnabled = false;
            this.mFirstScanListener = new C37651();
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.wifitrackerlib.BaseWifiTracker$Scanner$1 */
        public final class C37651 implements WifiScanner.ScanListener {
            public C37651() {
            }

            public final void onFailure(final int i, String str) {
                BaseWifiTracker.this.mWorkerHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseWifiTracker.Scanner.C37651 c37651 = BaseWifiTracker.Scanner.C37651.this;
                        int i2 = i;
                        BaseWifiTracker.Scanner scanner = BaseWifiTracker.Scanner.this;
                        if (scanner.mIsWifiEnabled) {
                            Log.e(BaseWifiTracker.this.mTag, "Failed to scan! Reason: " + i2 + ", ");
                            BaseWifiTracker.Scanner.this.scanLoop();
                        }
                    }
                });
            }

            public final void onResults(WifiScanner.ScanData[] scanDataArr) {
                BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda1(this, scanDataArr, 0));
            }

            public final void onFullResult(ScanResult scanResult) {
            }

            public final void onPeriodChanged(int i) {
            }

            public final void onSuccess() {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wifitrackerlib.BaseWifiTracker$8] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wifitrackerlib.BaseWifiTracker$9] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.wifitrackerlib.BaseWifiTracker$1] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.wifitrackerlib.BaseWifiTracker$2] */
    /* JADX WARN: Type inference failed for: r8v8, types: [com.android.wifitrackerlib.BaseWifiTracker$3] */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.android.wifitrackerlib.BaseWifiTracker$4] */
    public BaseWifiTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, BaseWifiTrackerCallback baseWifiTrackerCallback, String str) {
        int i = 1;
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback(i) { // from class: com.android.wifitrackerlib.BaseWifiTracker.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                BaseWifiTracker.this.handleNetworkCapabilitiesChanged(network, networkCapabilities);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                BaseWifiTracker.this.handleLinkPropertiesChanged(network, linkProperties);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                BaseWifiTracker.this.handleNetworkLost(network);
            }
        };
        this.mInjector = wifiTrackerInjector;
        lifecycle.addObserver(new LifecycleObserver() { // from class: com.android.wifitrackerlib.BaseWifiTracker.6
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                ConnectivityManager connectivityManager2 = baseWifiTracker.mConnectivityManager;
                try {
                    baseWifiTracker.mContext.unregisterReceiver(baseWifiTracker.mBroadcastReceiver);
                    connectivityManager2.unregisterNetworkCallback(baseWifiTracker.mNetworkCallback);
                    connectivityManager2.unregisterNetworkCallback(baseWifiTracker.mDefaultNetworkCallback);
                    baseWifiTracker.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(baseWifiTracker.mConnectivityDiagnosticsCallback);
                } catch (IllegalArgumentException unused) {
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onStart() {
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                Scanner scanner = baseWifiTracker.mScanner;
                scanner.mIsStartedState = true;
                BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 0));
                baseWifiTracker.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(baseWifiTracker, 1));
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onStop() {
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                Scanner scanner = baseWifiTracker.mScanner;
                scanner.mIsStartedState = false;
                BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 1));
                baseWifiTracker.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(baseWifiTracker, 0));
                SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
                SemWifiEntryFlags.isShowBandSummaryOn = -1;
            }
        });
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(SemWifiManager.class);
        this.mConnectivityManager = connectivityManager;
        this.mConnectivityDiagnosticsManager = (ConnectivityDiagnosticsManager) context.getSystemService(ConnectivityDiagnosticsManager.class);
        this.mMainHandler = handler;
        this.mWorkerHandler = handler2;
        this.mMaxScanAgeMillis = j;
        this.mScanIntervalMillis = j2;
        this.mListener = baseWifiTrackerCallback;
        this.mTag = str;
        this.mScanResultUpdater = new ScanResultUpdater(clock, j + j2, context);
        this.mQoSScoredCache = new WifiQoSScoredCache(context, new WifiQoSScoredCache.SemCacheListener(handler2) { // from class: com.android.wifitrackerlib.BaseWifiTracker.7
            @Override // com.samsung.android.wifitrackerlib.WifiQoSScoredCache.SemCacheListener
            public final void networkCacheUpdated() {
                BaseWifiTracker.this.handleQosScoreCacheUpdated();
            }
        });
        this.mConnectivityDiagnosticsCallback = new ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback() { // from class: com.android.wifitrackerlib.BaseWifiTracker.8
            @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
            public final void onConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
                BaseWifiTracker.this.handleConnectivityReportAvailable(connectivityReport);
            }
        };
        this.mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback(i) { // from class: com.android.wifitrackerlib.BaseWifiTracker.9
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                BaseWifiTracker.this.handleDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                BaseWifiTracker.this.handleDefaultNetworkLost();
            }
        };
        this.mScanner = new Scanner(this, handler2.getLooper(), 0);
        sVerboseLogging = wifiManager.isVerboseLoggingEnabled();
    }

    public void handleConfiguredNetworksChangedAction(Intent intent) {
    }

    public void handleConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
    }

    public void handleDefaultSubscriptionChanged(int i) {
    }

    public void handleNetworkLost(Network network) {
    }

    public void handleNetworkStateChangedAction(Intent intent) {
    }

    public void handleScanResultsAvailableAction(Intent intent) {
    }

    public void handleDefaultNetworkLost() {
    }

    public void handleOnStart() {
    }

    public void handleQosScoreCacheUpdated() {
    }

    public void handleWifiStateChangedAction() {
    }

    public void semNotifyScanStateChanged() {
    }

    public void handleDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
    }

    public void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
    }

    public void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
    }
}
