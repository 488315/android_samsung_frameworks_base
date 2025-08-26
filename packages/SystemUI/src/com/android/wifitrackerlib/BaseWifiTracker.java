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
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.BuildCompat;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class BaseWifiTracker {
    public static boolean sVerboseLogging;
    public final ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final WifiTrackerInjector mInjector;
    public final BaseWifiTrackerCallback mListener;
    public final Handler mMainHandler;
    public boolean mNetworkScoringUiEnabled;
    public final PowerManager mPowerManager;
    public final WifiQoSScoredCache mQoSScoredCache;
    public final long mScanIntervalMillis;
    public final ScanResultUpdater mScanResultUpdater;
    public final Scanner mScanner;
    public final SemWifiManager mSemWifiManager;
    public AnonymousClass7 mSharedConnectivityCallback;
    public final SharedConnectivityManager mSharedConnectivityManager;
    public final String mTag;
    public final WifiManager mWifiManager;
    public final AnonymousClass8 mWifiStateChangedListener;
    public final Handler mWorkerHandler;
    public int mWifiState = 4;
    public boolean mIsInitialized = false;
    public boolean mIsScanningDisabled = false;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.wifitrackerlib.BaseWifiTracker.1
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
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                String str2 = baseWifiTracker.mTag;
                int i2 = baseWifiTracker.mWifiState;
                Scanner scanner = baseWifiTracker.mScanner;
                boolean z = i2 == 3;
                boolean z2 = scanner.mIsWifiEnabled;
                scanner.mIsWifiEnabled = z;
                if (z != z2) {
                    if (!z) {
                        scanner.stopScanning();
                    } else if (scanner.shouldScan()) {
                        Log.i(BaseWifiTracker.this.mTag, "Scanning started");
                        scanner.scanLoop();
                    }
                }
                BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
                BaseWifiTrackerCallback baseWifiTrackerCallback = baseWifiTracker2.mListener;
                if (baseWifiTrackerCallback != null) {
                    baseWifiTracker2.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda4(baseWifiTrackerCallback, 0));
                }
                BaseWifiTracker.this.handleWifiStateChangedAction();
                return;
            }
            if (!"android.net.wifi.SCAN_RESULTS".equals(action)) {
                if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action)) {
                    BaseWifiTracker.this.handleConfiguredNetworksChangedAction(intent);
                    return;
                }
                if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                    BaseWifiTracker.this.handleNetworkStateChangedAction(intent);
                    return;
                } else if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                    BaseWifiTracker.this.handleRssiChangedAction();
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
                    HashMap map = new HashMap();
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        NetworkKey networkKey = (NetworkKey) it.next();
                        map.put(networkKey.wifiKey.bssid, networkKey);
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
                            if (hashSet.contains(map.get(str3))) {
                                Map map2 = (Map) qoSScores.get(str3);
                                WifiScoredNetwork wifiScoredNetwork = new WifiScoredNetwork(str3, ((Integer) map2.get("networkType")).intValue(), new int[]{((Integer) map2.get("levelMax-2")).intValue(), ((Integer) map2.get("levelMax-1")).intValue(), ((Integer) map2.get("levelMax")).intValue()});
                                if (!TextUtils.isEmpty(str3) && wifiScoredNetwork.networkType != 3) {
                                    synchronized (wifiQoSScoredCache.mLock) {
                                        try {
                                            ((HashMap) wifiQoSScoredCache.mCache).put(str3, wifiScoredNetwork);
                                            if (Debug.semIsProductDev() ? true : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                                                LogUtils logUtils = wifiQoSScoredCache.mLog;
                                                String string = wifiScoredNetwork.toString();
                                                if (logUtils.isProductDev) {
                                                    Log.d("WifiTracker.WifiWifiQoSScoreCache", logUtils.getPrintableLog(string));
                                                }
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    wifiQoSScoredCache.mUpdated = true;
                                }
                                hashSet.remove(map.get(str3));
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
                                public AnonymousClass1() {
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
            BaseWifiTracker.this.getClass();
        }
    };
    public final WifiTrackerLifecycleObserver mLifecycleObserver = new WifiTrackerLifecycleObserver();
    public final NetworkRequest mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
    public final AnonymousClass2 mNetworkCallback = new ConnectivityManager.NetworkCallback(1 == true ? 1 : 0) { // from class: com.android.wifitrackerlib.BaseWifiTracker.2
        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            Log.i(BaseWifiTracker.this.mTag, "NetworkCallback : onCapabilitiesChanged");
            BaseWifiTracker.this.handleNetworkCapabilitiesChanged(network, networkCapabilities);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            Log.i(BaseWifiTracker.this.mTag, "NetworkCallback : onLinkPropertiesChanged");
            BaseWifiTracker.this.handleLinkPropertiesChanged(network, linkProperties);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            Log.i(BaseWifiTracker.this.mTag, "NetworkCallback : onLost");
            BaseWifiTracker.this.handleNetworkLost(network);
        }
    };
    public final AnonymousClass3 mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback(1 == true ? 1 : 0) { // from class: com.android.wifitrackerlib.BaseWifiTracker.3
        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            Log.i(BaseWifiTracker.this.mTag, "DefaultNetworkCallback : onCapabilitiesChanged");
            BaseWifiTracker.this.handleDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            Log.i(BaseWifiTracker.this.mTag, "DefaultNetworkCallback : onLost");
            BaseWifiTracker.this.handleDefaultNetworkLost();
        }
    };
    public final AnonymousClass4 mConnectivityDiagnosticsCallback = new ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback() { // from class: com.android.wifitrackerlib.BaseWifiTracker.4
        @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
        public final void onConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
            BaseWifiTracker.this.handleConnectivityReportAvailable(connectivityReport);
        }
    };
    public final AnonymousClass5 mConnectivityDiagnosticsExecutor = new Executor() { // from class: com.android.wifitrackerlib.BaseWifiTracker.5
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            BaseWifiTracker.this.mWorkerHandler.post(runnable);
        }
    };
    public final AnonymousClass6 mSharedConnectivityExecutor = new Executor() { // from class: com.android.wifitrackerlib.BaseWifiTracker.6
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            BaseWifiTracker.this.mWorkerHandler.post(runnable);
        }
    };

    /* renamed from: com.android.wifitrackerlib.BaseWifiTracker$8, reason: invalid class name */
    public class AnonymousClass8 implements WifiManager.WifiStateChangedListener {
        public AnonymousClass8() {
        }

        public final void onWifiStateChanged() {
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            baseWifiTracker.mWifiState = baseWifiTracker.mWifiManager.getWifiState();
            BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
            Scanner scanner = baseWifiTracker2.mScanner;
            boolean z = baseWifiTracker2.mWifiState == 3;
            boolean z2 = scanner.mIsWifiEnabled;
            scanner.mIsWifiEnabled = z;
            if (z != z2) {
                if (!z) {
                    scanner.stopScanning();
                } else if (scanner.shouldScan()) {
                    Log.i(BaseWifiTracker.this.mTag, "Scanning started");
                    scanner.scanLoop();
                }
            }
            BaseWifiTracker baseWifiTracker3 = BaseWifiTracker.this;
            BaseWifiTrackerCallback baseWifiTrackerCallback = baseWifiTracker3.mListener;
            if (baseWifiTrackerCallback != null) {
                baseWifiTracker3.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda4(baseWifiTrackerCallback, 0));
            }
            BaseWifiTracker.this.handleWifiStateChangedAction();
        }
    }

    public class Scanner extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mIsStartedState;
        public boolean mIsWifiEnabled;

        public /* synthetic */ Scanner(BaseWifiTracker baseWifiTracker, Looper looper, int i) {
            this(looper);
        }

        public final void scanLoop() {
            boolean zShouldScan = shouldScan();
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            if (!zShouldScan) {
                Log.e(baseWifiTracker.mTag, "Scan loop called even though we shouldn't be scanning! mIsWifiEnabled=" + this.mIsWifiEnabled + " mIsStartedState=" + this.mIsStartedState + " PowerManager.isInteractive()=" + baseWifiTracker.mPowerManager.isInteractive());
                return;
            }
            if ("WifiPickerTracker".equals(baseWifiTracker.mTag)) {
                Log.d(baseWifiTracker.mTag, "Starting BLE scan for AutoHotspot");
                baseWifiTracker.mSemWifiManager.wifiApBleClientRole(true);
            }
            removeCallbacksAndMessages(null);
            baseWifiTracker.mSemWifiManager.startScan();
            BaseWifiTrackerCallback baseWifiTrackerCallback = baseWifiTracker.mListener;
            if (baseWifiTrackerCallback != null) {
                baseWifiTracker.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda4(baseWifiTrackerCallback, 1));
            }
            postDelayed(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(this, 2), baseWifiTracker.mScanIntervalMillis);
        }

        public final boolean shouldScan() {
            if (!this.mIsWifiEnabled || !this.mIsStartedState) {
                return false;
            }
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            return !baseWifiTracker.mIsScanningDisabled && baseWifiTracker.mPowerManager.isInteractive();
        }

        public final void stopScanning() {
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            Log.i(baseWifiTracker.mTag, "Scanning stopped");
            removeCallbacksAndMessages(null);
            if ("WifiPickerTracker".equals(baseWifiTracker.mTag)) {
                Log.d(baseWifiTracker.mTag, "Stopping BLE scan for AutoHotspot");
                baseWifiTracker.mSemWifiManager.wifiApBleClientRole(false);
            }
        }

        private Scanner(Looper looper) {
            super(looper);
            this.mIsStartedState = false;
            this.mIsWifiEnabled = false;
            new AnonymousClass1();
        }

        /* renamed from: com.android.wifitrackerlib.BaseWifiTracker$Scanner$1, reason: invalid class name */
        public class AnonymousClass1 implements WifiScanner.ScanListener {
            public AnonymousClass1() {
            }

            public final void onFailure(final int i, String str) {
                BaseWifiTracker.this.mWorkerHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseWifiTracker.Scanner.AnonymousClass1 anonymousClass1 = this.f$0;
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
                BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(1, this, scanDataArr));
            }

            public final void onFullResult(ScanResult scanResult) {
            }

            public final void onPeriodChanged(int i) {
            }

            public final void onSuccess() {
            }
        }
    }

    public class WifiTrackerLifecycleObserver implements LifecycleObserver {
        public WifiTrackerLifecycleObserver() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            AnonymousClass7 anonymousClass7;
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            WifiTrackerInjector wifiTrackerInjector = baseWifiTracker.mInjector;
            AnonymousClass8 anonymousClass8 = baseWifiTracker.mWifiStateChangedListener;
            if (anonymousClass8 != null) {
                try {
                    wifiTrackerInjector.getClass();
                    baseWifiTracker.mWifiManager.removeWifiStateChangedListener(anonymousClass8);
                } catch (IllegalArgumentException unused) {
                    return;
                }
            }
            baseWifiTracker.mContext.unregisterReceiver(baseWifiTracker.mBroadcastReceiver);
            baseWifiTracker.mConnectivityManager.unregisterNetworkCallback(baseWifiTracker.mNetworkCallback);
            baseWifiTracker.mConnectivityManager.unregisterNetworkCallback(baseWifiTracker.mDefaultNetworkCallback);
            baseWifiTracker.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(baseWifiTracker.mConnectivityDiagnosticsCallback);
            SharedConnectivityManager sharedConnectivityManager = baseWifiTracker.mSharedConnectivityManager;
            if (sharedConnectivityManager != null && (anonymousClass7 = baseWifiTracker.mSharedConnectivityCallback) != null) {
                int i = BuildCompat.$r8$clinit;
                if (!sharedConnectivityManager.unregisterCallback(anonymousClass7)) {
                    Log.e(baseWifiTracker.mTag, "onDestroyed: unregisterCallback failed");
                }
            }
            if (baseWifiTracker.mSharedConnectivityCallback != null) {
                baseWifiTracker.mSharedConnectivityCallback = null;
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            BaseWifiTracker.this.onStart();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            BaseWifiTracker.this.onStop();
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wifitrackerlib.BaseWifiTracker$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.wifitrackerlib.BaseWifiTracker$2] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.wifitrackerlib.BaseWifiTracker$3] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wifitrackerlib.BaseWifiTracker$4] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.wifitrackerlib.BaseWifiTracker$5] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wifitrackerlib.BaseWifiTracker$6] */
    /* JADX WARN: Type inference failed for: r8v17, types: [com.android.wifitrackerlib.BaseWifiTracker$7] */
    public BaseWifiTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, BaseWifiTrackerCallback baseWifiTrackerCallback, String str) {
        int i = 0;
        this.mSharedConnectivityManager = null;
        this.mSharedConnectivityCallback = null;
        this.mInjector = wifiTrackerInjector;
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mConnectivityManager = connectivityManager;
        this.mConnectivityDiagnosticsManager = (ConnectivityDiagnosticsManager) context.getSystemService(ConnectivityDiagnosticsManager.class);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        if (wifiTrackerInjector.isSharedConnectivityFeatureEnabled()) {
            int i2 = BuildCompat.$r8$clinit;
            this.mSharedConnectivityManager = (SharedConnectivityManager) context.getSystemService(SharedConnectivityManager.class);
            this.mSharedConnectivityCallback = new SharedConnectivityClientCallback() { // from class: com.android.wifitrackerlib.BaseWifiTracker.7
                public final void onHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
                    BaseWifiTracker.this.handleHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
                }

                public final void onHotspotNetworksUpdated(List list) {
                    BaseWifiTracker.this.handleHotspotNetworksUpdated(list);
                }

                public final void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
                    BaseWifiTracker.this.handleKnownNetworkConnectionStatusChanged(knownNetworkConnectionStatus);
                }

                public final void onKnownNetworksUpdated(List list) {
                    BaseWifiTracker.this.handleKnownNetworksUpdated(list);
                }

                public final void onRegisterCallbackFailed(Exception exc) {
                    BaseWifiTracker.this.getClass();
                }

                public final void onServiceConnected() {
                    BaseWifiTracker.this.handleServiceConnected();
                }

                public final void onServiceDisconnected() {
                    BaseWifiTracker.this.handleServiceDisconnected();
                }

                public final void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState sharedConnectivitySettingsState) {
                    BaseWifiTracker.this.getClass();
                }
            };
        }
        this.mWifiStateChangedListener = new AnonymousClass8();
        this.mMainHandler = handler;
        this.mWorkerHandler = handler2;
        this.mScanIntervalMillis = j2;
        this.mListener = baseWifiTrackerCallback;
        sVerboseLogging = !wifiTrackerInjector.mVerboseLoggingDisabledOverride && wifiTrackerInjector.mWifiManager.isVerboseLoggingEnabled();
        this.mQoSScoredCache = new WifiQoSScoredCache(context, new WifiQoSScoredCache.SemCacheListener(handler2) { // from class: com.android.wifitrackerlib.BaseWifiTracker.9
            @Override // com.samsung.android.wifitrackerlib.WifiQoSScoredCache.SemCacheListener
            public final void networkCacheUpdated() {
                BaseWifiTracker.this.handleQosScoreCacheUpdated();
            }
        });
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(SemWifiManager.class);
        this.mTag = str;
        this.mScanResultUpdater = new ScanResultUpdater(clock, j, context);
        this.mScanner = new Scanner(this, handler2.getLooper(), i);
        if (lifecycle != null) {
            handler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(i, this, lifecycle));
        }
    }

    public final void onStart() {
        Scanner scanner = this.mScanner;
        scanner.mIsStartedState = true;
        BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 1));
        this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(this, 1));
        SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin = -1;
    }

    public final void onStop() {
        Scanner scanner = this.mScanner;
        scanner.mIsStartedState = false;
        BaseWifiTracker.this.mWorkerHandler.post(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 0));
        this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(this, 0));
    }

    public interface BaseWifiTrackerCallback {
        void onWifiStateChanged();

        default void onScanRequested() {
        }
    }

    public void handleConfiguredNetworksChangedAction(Intent intent) {
    }

    public void handleConnectivityReportAvailable(ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
    }

    public void handleDefaultSubscriptionChanged(int i) {
    }

    public void handleHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
    }

    public void handleHotspotNetworksUpdated(List list) {
    }

    public void handleKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
    }

    public void handleKnownNetworksUpdated(List list) {
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

    public void handleRssiChangedAction() {
    }

    public void handleServiceConnected() {
    }

    public void handleServiceDisconnected() {
    }

    public void handleWifiStateChangedAction() {
    }

    public void handleDefaultNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
    }

    public void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
    }

    public void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
    }
}
