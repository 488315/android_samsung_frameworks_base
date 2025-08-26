package com.android.wifitrackerlib;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import androidx.core.os.BuildCompat;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda1(BaseWifiTracker baseWifiTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = baseWifiTracker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BaseWifiTracker.AnonymousClass7 anonymousClass7;
        boolean zIsSupportedQoSProvider;
        BaseWifiTracker.AnonymousClass7 anonymousClass72;
        boolean z = false;
        int i = this.$r8$classId;
        final BaseWifiTracker baseWifiTracker = this.f$0;
        switch (i) {
            case 0:
                WifiTrackerInjector wifiTrackerInjector = baseWifiTracker.mInjector;
                BaseWifiTracker.AnonymousClass8 anonymousClass8 = baseWifiTracker.mWifiStateChangedListener;
                if (anonymousClass8 != null) {
                    try {
                        wifiTrackerInjector.getClass();
                        baseWifiTracker.mWifiManager.removeWifiStateChangedListener(anonymousClass8);
                    } catch (IllegalArgumentException unused) {
                    }
                }
                baseWifiTracker.mContext.unregisterReceiver(baseWifiTracker.mBroadcastReceiver);
                baseWifiTracker.mConnectivityManager.unregisterNetworkCallback(baseWifiTracker.mNetworkCallback);
                baseWifiTracker.mConnectivityManager.unregisterNetworkCallback(baseWifiTracker.mDefaultNetworkCallback);
                baseWifiTracker.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(baseWifiTracker.mConnectivityDiagnosticsCallback);
                SharedConnectivityManager sharedConnectivityManager = baseWifiTracker.mSharedConnectivityManager;
                if (sharedConnectivityManager != null && (anonymousClass7 = baseWifiTracker.mSharedConnectivityCallback) != null) {
                    int i2 = BuildCompat.$r8$clinit;
                    if (!sharedConnectivityManager.unregisterCallback(anonymousClass7)) {
                        Log.e(baseWifiTracker.mTag, "onStop: unregisterCallback failed");
                    }
                }
                baseWifiTracker.mIsInitialized = false;
                SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
                SemWifiEntryFlags.isShowBandSummaryOn = -1;
                break;
            default:
                if (!baseWifiTracker.mIsInitialized) {
                    IntentFilter intentFilter = new IntentFilter();
                    BaseWifiTracker.AnonymousClass8 anonymousClass82 = baseWifiTracker.mWifiStateChangedListener;
                    if (anonymousClass82 != null) {
                        baseWifiTracker.mInjector.getClass();
                        baseWifiTracker.mWifiManager.addWifiStateChangedListener(new Executor() { // from class: com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda3
                            @Override // java.util.concurrent.Executor
                            public final void execute(Runnable runnable) {
                                baseWifiTracker.mWorkerHandler.post(runnable);
                            }
                        }, anonymousClass82);
                        anonymousClass82.onWifiStateChanged();
                    } else {
                        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                    }
                    if (!baseWifiTracker.mIsScanningDisabled) {
                        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
                    }
                    intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
                    intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                    if (BaseWifiTracker.sVerboseLogging) {
                        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
                    }
                    intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                    intentFilter.setPriority(1000);
                    Context context = baseWifiTracker.mContext;
                    BaseWifiTracker.AnonymousClass1 anonymousClass1 = baseWifiTracker.mBroadcastReceiver;
                    Handler handler = baseWifiTracker.mWorkerHandler;
                    context.registerReceiver(anonymousClass1, intentFilter, null, handler);
                    baseWifiTracker.mConnectivityManager.registerNetworkCallback(baseWifiTracker.mNetworkRequest, baseWifiTracker.mNetworkCallback, handler);
                    baseWifiTracker.mConnectivityManager.registerDefaultNetworkCallback(baseWifiTracker.mDefaultNetworkCallback, handler);
                    baseWifiTracker.mConnectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(baseWifiTracker.mNetworkRequest, baseWifiTracker.mConnectivityDiagnosticsExecutor, baseWifiTracker.mConnectivityDiagnosticsCallback);
                    SharedConnectivityManager sharedConnectivityManager2 = baseWifiTracker.mSharedConnectivityManager;
                    if (sharedConnectivityManager2 != null && (anonymousClass72 = baseWifiTracker.mSharedConnectivityCallback) != null) {
                        int i3 = BuildCompat.$r8$clinit;
                        sharedConnectivityManager2.registerCallback(baseWifiTracker.mSharedConnectivityExecutor, anonymousClass72);
                    }
                    WifiQoSScoredCache wifiQoSScoredCache = baseWifiTracker.mQoSScoredCache;
                    boolean z2 = Settings.Global.getInt(wifiQoSScoredCache.mContext.getContentResolver(), "sem_wifi_network_rating_scorer_enabled_labs", 0) == 1;
                    SemWifiManager semWifiManager = wifiQoSScoredCache.mSemWifiManager;
                    if (semWifiManager == null) {
                        Log.i("WifiTracker.WifiWifiQoSScoreCache", "SemWifiManager: null");
                        zIsSupportedQoSProvider = false;
                    } else {
                        zIsSupportedQoSProvider = semWifiManager.isSupportedQoSProvider();
                    }
                    if (zIsSupportedQoSProvider) {
                        Context context2 = wifiQoSScoredCache.mContext;
                        int i4 = SemWifiUtils.$r8$clinit;
                        if (!((SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING")) ? SemEmergencyManager.isEmergencyMode(context2) : false) && z2) {
                            z = true;
                        }
                    }
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("Network Score Enabling Check ", "WifiTracker.WifiWifiQoSScoreCache", z);
                    baseWifiTracker.mNetworkScoringUiEnabled = z;
                    baseWifiTracker.handleOnStart();
                    baseWifiTracker.mIsInitialized = true;
                    break;
                }
                break;
        }
    }
}
