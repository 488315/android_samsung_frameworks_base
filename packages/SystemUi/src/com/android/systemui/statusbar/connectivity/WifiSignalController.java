package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Html;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.android.systemui.R;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiSignalController extends SignalController {
    public final Handler mBgHandler;
    public final SignalIcon$MobileIconGroup mCarrierMergedWifiIconGroup;
    public final boolean mHasMobileDataFeature;
    public final SignalIcon$IconGroup mUnmergedWifiIconGroup;
    public final WifiManager mWifiManager;
    public final WifiStatusTracker mWifiTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiTrafficStateCallback implements WifiManager.TrafficStateCallback {
        public /* synthetic */ WifiTrafficStateCallback(WifiSignalController wifiSignalController, int i) {
            this();
        }

        public final void onStateChanged(int i) {
            WifiSignalController.this.setActivity(i);
        }

        private WifiTrafficStateCallback() {
        }
    }

    public WifiSignalController(Context context, boolean z, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl, WifiManager wifiManager, WifiStatusTrackerFactory wifiStatusTrackerFactory, Handler handler) {
        super("WifiSignalController", context, 1, callbackHandler, networkControllerImpl);
        SignalIcon$IconGroup signalIcon$IconGroup = WifiIcons.UNMERGED_WIFI;
        this.mUnmergedWifiIconGroup = signalIcon$IconGroup;
        this.mCarrierMergedWifiIconGroup = TelephonyIcons.CARRIER_MERGED_WIFI;
        this.mBgHandler = handler;
        this.mWifiManager = wifiManager;
        int i = 0;
        WifiSignalController$$ExternalSyntheticLambda0 wifiSignalController$$ExternalSyntheticLambda0 = new WifiSignalController$$ExternalSyntheticLambda0(this, 0);
        wifiStatusTrackerFactory.getClass();
        WifiStatusTracker wifiStatusTracker = new WifiStatusTracker(wifiStatusTrackerFactory.mContext, wifiStatusTrackerFactory.mWifiManager, wifiStatusTrackerFactory.mNetworkScoreManager, wifiStatusTrackerFactory.mConnectivityManager, wifiSignalController$$ExternalSyntheticLambda0, wifiStatusTrackerFactory.mMainHandler, handler);
        this.mWifiTracker = wifiStatusTracker;
        WifiNetworkScoreCache wifiNetworkScoreCache = wifiStatusTracker.mWifiNetworkScoreCache;
        wifiStatusTracker.mNetworkScoreManager.registerNetworkScoreCache(1, wifiNetworkScoreCache, 1);
        wifiNetworkScoreCache.registerListener(wifiStatusTracker.mCacheListener);
        ConnectivityManager connectivityManager = wifiStatusTracker.mConnectivityManager;
        NetworkRequest networkRequest = wifiStatusTracker.mNetworkRequest;
        WifiStatusTracker.C09451 c09451 = wifiStatusTracker.mNetworkCallback;
        Handler handler2 = wifiStatusTracker.mHandler;
        connectivityManager.registerNetworkCallback(networkRequest, c09451, handler2);
        connectivityManager.registerDefaultNetworkCallback(wifiStatusTracker.mDefaultNetworkCallback, handler2);
        this.mHasMobileDataFeature = z;
        if (wifiManager != null) {
            wifiManager.registerTrafficStateCallback(context.getMainExecutor(), new WifiTrafficStateCallback(this, i));
        }
        WifiState wifiState = (WifiState) this.mCurrentState;
        ((WifiState) this.mLastState).iconGroup = signalIcon$IconGroup;
        wifiState.iconGroup = signalIcon$IconGroup;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final ConnectivityState cleanState() {
        return new WifiState();
    }

    public final void copyWifiStates() {
        Preconditions.checkState(this.mBgHandler.getLooper().isCurrentThread());
        ConnectivityState connectivityState = this.mCurrentState;
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        ((WifiState) connectivityState).enabled = wifiStatusTracker.enabled;
        ((WifiState) connectivityState).isDefault = wifiStatusTracker.isDefaultNetwork;
        ((WifiState) connectivityState).connected = wifiStatusTracker.connected;
        ((WifiState) connectivityState).ssid = wifiStatusTracker.ssid;
        ((WifiState) connectivityState).rssi = wifiStatusTracker.rssi;
        ((WifiState) connectivityState).level = wifiStatusTracker.level;
        ((WifiState) connectivityState).statusLabel = wifiStatusTracker.statusLabel;
        ((WifiState) connectivityState).isCarrierMerged = wifiStatusTracker.isCarrierMerged;
        ((WifiState) connectivityState).subId = wifiStatusTracker.subId;
        ((WifiState) connectivityState).iconGroup = ((WifiState) connectivityState).isCarrierMerged ? this.mCarrierMergedWifiIconGroup : this.mUnmergedWifiIconGroup;
    }

    public final void doInBackground(Runnable runnable) {
        Thread currentThread = Thread.currentThread();
        Handler handler = this.mBgHandler;
        if (currentThread != handler.getLooper().getThread()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final void dump(PrintWriter printWriter) {
        String[] strArr;
        super.dump(printWriter);
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        wifiStatusTracker.getClass();
        printWriter.println("  - WiFi Network History ------");
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = wifiStatusTracker.mHistory;
            if (i >= 32) {
                break;
            }
            if (strArr[i] != null) {
                i2++;
            }
            i++;
        }
        int i3 = wifiStatusTracker.mHistoryIndex + 32;
        while (true) {
            i3--;
            if (i3 < (wifiStatusTracker.mHistoryIndex + 32) - i2) {
                dumpTableData(printWriter);
                return;
            }
            StringBuilder sb = new StringBuilder("  Previous WiFiNetwork(");
            sb.append((wifiStatusTracker.mHistoryIndex + 32) - i3);
            sb.append("): ");
            KeyboardUI$$ExternalSyntheticOutline0.m134m(sb, strArr[i3 & 31], printWriter);
        }
    }

    public final int getCurrentIconIdForCarrierWifi() {
        WifiState wifiState = (WifiState) this.mCurrentState;
        int i = wifiState.level;
        int maxSignalLevel = this.mWifiManager.getMaxSignalLevel() + 1;
        boolean z = !wifiState.isDefaultConnectionValidated;
        if (wifiState.connected) {
            int i2 = SignalDrawable.$r8$clinit;
            return (maxSignalLevel << 8) | ((z ? 2 : 0) << 16) | i;
        }
        if (!wifiState.enabled) {
            return 0;
        }
        int i3 = SignalDrawable.$r8$clinit;
        return (maxSignalLevel << 8) | 131072 | 0;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final void notifyListeners(SignalCallback signalCallback) {
        IconState iconState;
        int i;
        ConnectivityState connectivityState = this.mCurrentState;
        boolean z = ((WifiState) connectivityState).isCarrierMerged;
        Context context = this.mContext;
        if (!z) {
            WifiState wifiState = (WifiState) connectivityState;
            boolean z2 = wifiState.enabled && ((wifiState.connected && wifiState.inetCondition == 1) || !this.mHasMobileDataFeature || wifiState.isDefault || context.getResources().getBoolean(R.bool.config_showWifiIndicatorWhenEnabled));
            String str = wifiState.connected ? wifiState.ssid : null;
            boolean z3 = z2 && wifiState.ssid != null;
            String charSequence = getTextIfExists(getContentDescription()).toString();
            if (wifiState.inetCondition == 0) {
                charSequence = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.data_connection_no_internet, AbstractC0000x2c234b15.m2m(charSequence, ","));
            }
            signalCallback.setWifiIndicators(new WifiIndicators(wifiState.enabled, new IconState(z2, getCurrentIconId(), charSequence), new IconState(wifiState.connected, this.mWifiTracker.isCaptivePortal ? R.drawable.ic_qs_wifi_disconnected : connectivityState.connected ? connectivityState.iconGroup.qsIcons[connectivityState.inetCondition][connectivityState.level] : connectivityState.enabled ? connectivityState.iconGroup.qsDiscState : connectivityState.iconGroup.qsNullState, charSequence), z3 && wifiState.activityIn, z3 && wifiState.activityOut, str, wifiState.isTransient, wifiState.statusLabel, wifiState.inetCondition));
            return;
        }
        boolean z4 = ((WifiState) connectivityState).isDefault;
        NetworkControllerImpl networkControllerImpl = this.mNetworkController;
        if (z4 || !(!networkControllerImpl.mAirplaneMode)) {
            String charSequence2 = getTextIfExists(getContentDescription()).toString();
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = this.mCarrierMergedWifiIconGroup;
            CharSequence textIfExists = getTextIfExists(signalIcon$MobileIconGroup.dataContentDescription);
            WifiState wifiState2 = (WifiState) connectivityState;
            String string = wifiState2.inetCondition == 0 ? context.getString(R.string.data_connection_no_internet) : Html.fromHtml(textIfExists.toString(), 0).toString();
            boolean z5 = wifiState2.enabled && wifiState2.connected && wifiState2.isDefault;
            IconState iconState2 = new IconState(z5, getCurrentIconIdForCarrierWifi(), charSequence2);
            int i2 = signalIcon$MobileIconGroup.dataType;
            int i3 = z5 ? i2 : 0;
            if (z5) {
                i = i2;
                iconState = new IconState(wifiState2.connected, getCurrentIconIdForCarrierWifi(), charSequence2);
            } else {
                iconState = null;
                i = 0;
            }
            MobileSignalController controllerWithSubId = networkControllerImpl.getControllerWithSubId(wifiState2.subId);
            signalCallback.setMobileDataIndicators(new MobileDataIndicators(iconState2, iconState, i3, i, wifiState2.activityIn, wifiState2.activityOut, string, textIfExists, controllerWithSubId != null ? controllerWithSubId.mPhone.getSimOperatorName() : "", wifiState2.subId, false, true));
        }
    }

    public void setActivity(int i) {
        ConnectivityState connectivityState = this.mCurrentState;
        ((WifiState) connectivityState).activityIn = i == 3 || i == 1;
        ((WifiState) connectivityState).activityOut = i == 3 || i == 2;
        notifyListenersIfNecessary();
    }
}
