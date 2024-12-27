package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.app.admin.IDevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.detail.WifiDetailAdapter;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl.AnonymousClass7;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.Utils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WifiTile extends SQSTileImpl {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final AccessPointController mAccessPointController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NetworkController mController;
    public final WifiDetailAdapter mDetailAdapter;
    public boolean mDetailListening;
    public final IDevicePolicyManager mDevicePolicyManager;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public boolean mExpectDisabled;
    public final AnonymousClass2 mFoldStateChangedListener;
    public final Handler mHandler;
    public boolean mIsSatelliteModeOn;
    public boolean mIsTransientEnabled;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PanelInteractor mPanelInteractor;
    public final WifiTileReceiver mReceiver;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    private SettingsHelper mSettingsHelper;
    public final WifiSignalCallback mSignalCallback;
    public final QSTile.BooleanState mStateBeforeClick;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public final WifiTileReceiver mSubscreenWifiTileReceiver;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CallbackInfo {
        public boolean connected;
        public boolean enabled;
        public int inetCondition;
        public boolean isTransient;
        public String ssid;
        public String statusLabel;
        public String wifiSignalContentDescription;
        public int wifiSignalIconId;

        public final void copyTo(CallbackInfo callbackInfo) {
            callbackInfo.enabled = this.enabled;
            callbackInfo.connected = this.connected;
            callbackInfo.wifiSignalIconId = this.wifiSignalIconId;
            callbackInfo.ssid = this.ssid;
            callbackInfo.wifiSignalContentDescription = this.wifiSignalContentDescription;
            callbackInfo.isTransient = this.isTransient;
            callbackInfo.statusLabel = this.statusLabel;
            callbackInfo.isTransient = this.isTransient;
            callbackInfo.inetCondition = this.inetCondition;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallbackInfo[enabled=");
            sb.append(this.enabled);
            sb.append(",connected=");
            sb.append(this.connected);
            sb.append(",wifiSignalIconId=");
            sb.append(this.wifiSignalIconId);
            sb.append(",ssid=");
            sb.append(this.ssid);
            sb.append(",wifiSignalContentDescription=");
            sb.append(this.wifiSignalContentDescription);
            sb.append(",isTransient=");
            sb.append(this.isTransient);
            sb.append(",statusLabel=");
            sb.append(this.statusLabel);
            sb.append(",inetCondition=");
            return Anchor$$ExternalSyntheticOutline0.m(this.inetCondition, ",wifiTestReported=false]", sb);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WifiSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo = new CallbackInfo();

        public WifiSignalCallback() {
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            WifiTile wifiTile;
            WifiTile wifiTile2 = WifiTile.this;
            Intent intent = WifiTile.WIFI_SETTINGS;
            if (wifiTile2.DEBUG) {
                Log.d(wifiTile2.TAG, "setWifiIndicators: " + wifiIndicators);
            }
            synchronized (this.mInfo) {
                try {
                    CallbackInfo callbackInfo = this.mInfo;
                    callbackInfo.enabled = wifiIndicators.enabled;
                    callbackInfo.ssid = wifiIndicators.description;
                    callbackInfo.isTransient = wifiIndicators.isTransient;
                    callbackInfo.statusLabel = wifiIndicators.statusLabel;
                    callbackInfo.inetCondition = wifiIndicators.inetCondition;
                    IconState iconState = wifiIndicators.qsIcon;
                    if (iconState != null) {
                        callbackInfo.connected = iconState.visible;
                        callbackInfo.wifiSignalIconId = iconState.icon;
                        callbackInfo.wifiSignalContentDescription = iconState.contentDescription;
                    } else {
                        callbackInfo.connected = false;
                        callbackInfo.wifiSignalIconId = 0;
                        callbackInfo.wifiSignalContentDescription = null;
                    }
                    wifiTile = WifiTile.this;
                    wifiTile.mDetailAdapter.mInfo = callbackInfo;
                } catch (Throwable th) {
                    throw th;
                }
            }
            wifiTile.refreshState(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WifiTileReceiver extends BroadcastReceiver {
        public WifiTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.samsung.android.server.wifi.softap.smarttethering.collapseQuickPanel")) {
                WifiTile wifiTile = WifiTile.this;
                Intent intent2 = WifiTile.WIFI_SETTINGS;
                if (wifiTile.DEBUG) {
                    Log.d(WifiTile.this.TAG + ".AutoHotspot", "BT Paring dialog shown. Collapsing QuickPanel");
                }
                WifiTile wifiTile2 = WifiTile.this;
                if (!((KeyguardStateControllerImpl) wifiTile2.mKeyguardStateController).mShowing) {
                    wifiTile2.mPanelInteractor.forceCollapsePanels();
                }
            }
            if (QpRune.QUICK_SUBSCREEN_PANEL && intent.getAction().equals("WIFI_STATE_CHANGE")) {
                WifiTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.qs.tiles.WifiTile$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.qs.tiles.WifiTile$1] */
    public WifiTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, SettingsHelper settingsHelper, PanelInteractor panelInteractor, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle, SatelliteModeObserverHelper satelliteModeObserverHelper) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_wifi_on, R.drawable.quick_panel_icon_wifi_on_027);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_wifi_off, R.drawable.quick_panel_icon_wifi_off_019);
        WifiSignalCallback wifiSignalCallback = new WifiSignalCallback();
        this.mSignalCallback = wifiSignalCallback;
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        this.mStateBeforeClick = booleanState;
        this.mSubscreenQsPanelController = null;
        WifiTileReceiver wifiTileReceiver = new WifiTileReceiver();
        this.mSubscreenWifiTileReceiver = wifiTileReceiver;
        this.mIsTransientEnabled = false;
        this.mIsSatelliteModeOn = false;
        this.mSatelliteModeCallback = new SatelliteModeObserver$SatelliteModeCallback() { // from class: com.android.systemui.qs.tiles.WifiTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback
            public final void onSatelliteModeChanged(boolean z) {
                WifiTile wifiTile = WifiTile.this;
                wifiTile.mIsSatelliteModeOn = z;
                wifiTile.mDetailAdapter.mIsSatelliteModeOn = z;
                wifiTile.refreshState(null);
            }
        };
        ?? r10 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.WifiTile.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_SUBSCREEN_PANEL) {
                    return;
                }
                WifiTile wifiTile = WifiTile.this;
                if (z) {
                    wifiTile.mSubscreenQsPanelController.getInstance(1).registerReceiver(false);
                } else {
                    wifiTile.mSubscreenQsPanelController.getInstance(1).unRegisterReceiver(false);
                }
            }
        };
        this.mFoldStateChangedListener = r10;
        this.mHandler = handler;
        this.mController = networkController;
        this.mAccessPointController = accessPointController;
        networkController.observe(((QSTileImpl) this).mLifecycle, wifiSignalCallback);
        booleanState.spec = ImsProfile.PDN_WIFI;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mDevicePolicyManager = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mSettingsHelper = settingsHelper;
        this.mPanelInteractor = panelInteractor;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDetailAdapter = new WifiDetailAdapter(this.mContext, handler, this.mActivityStarter, accessPointController, panelInteractor, networkController, keyguardStateController, keyguardUpdateMonitor, this.mSettingsHelper, this);
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
            if (displayLifecycle != 0) {
                displayLifecycle.addObserver(r10);
            }
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            broadcastDispatcher.registerReceiver(wifiTileReceiver, new IntentFilter("WIFI_STATE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.WIFI_STATE_CHANGE");
        }
        if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
            WifiTileReceiver wifiTileReceiver2 = new WifiTileReceiver();
            this.mReceiver = wifiTileReceiver2;
            broadcastDispatcher.registerReceiver(new IntentFilter("com.samsung.android.server.wifi.softap.smarttethering.collapseQuickPanel"), wifiTileReceiver2);
        }
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        if (str.charAt(i) != '\"') {
            return str;
        }
        try {
            String replaceAll = str.substring(1, i).replaceAll("\\s+$", "");
            if (replaceAll.length() <= 0) {
                return null;
            }
            return replaceAll;
        } catch (NullPointerException unused) {
            return null;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiTileBlocked()) {
            super.showItPolicyToast();
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 126;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final QSTile.State getState() {
        return (QSTile.BooleanState) this.mState;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_sec_wifi_label).trim();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiTileBlocked() || isBlockedByEASPolicy$1()) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                super.showItPolicyToast();
                return;
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        ActivityStarter activityStarter = this.mActivityStarter;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled() && ((QSTile.BooleanState) this.mState).value) {
            if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiTile.this.handleClick(expandable);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "WIFI_STATE_CHANGE");
                return;
            }
        }
        StringBuilder sb = new StringBuilder("isShowing() = ");
        sb.append(keyguardStateControllerImpl.mShowing);
        sb.append(", isSecure() = ");
        sb.append(keyguardUpdateMonitor.isSecure());
        sb.append(", canSkipBouncer() = ");
        sb.append(!keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()));
        sb.append(", isLockFunctionsEnabled() = ");
        sb.append(this.mSettingsHelper.isLockFunctionsEnabled());
        String sb2 = sb.toString();
        String str = this.TAG;
        Log.d(str, sb2);
        if (!((AccessPointControllerImpl) this.mAccessPointController).canConfigWifi()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
            return;
        }
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        if (booleanState.state == 0) {
            Log.d(str, "handleClick pass enabling or disabling ");
            return;
        }
        booleanState.copyTo(this.mStateBeforeClick);
        QSTile.BooleanState booleanState2 = (QSTile.BooleanState) this.mState;
        if (!booleanState2.value && booleanState2.state == 2) {
            booleanState2.value = this.mSignalCallback.mInfo.enabled;
            Log.d(str, "handleClick refresh value ");
        }
        boolean z2 = ((QSTile.BooleanState) this.mState).value;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("handleClick ", str, z2);
        refreshState(z2 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) this.mController;
        networkControllerImpl.getClass();
        networkControllerImpl.new AnonymousClass7(!z2).execute(new Void[0]);
        this.mExpectDisabled = z2;
        if (z2) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTile wifiTile = WifiTile.this;
                    if (wifiTile.mExpectDisabled) {
                        wifiTile.mExpectDisabled = false;
                        wifiTile.refreshState(null);
                    }
                }
            }, 350L);
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_WIFI_COVER);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        boolean z = Utils.SPF_SupportMobileApEnhanced;
        String str = this.TAG;
        if (z || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
            SemWifiManager semWifiManager = ((AccessPointControllerImpl) this.mAccessPointController).mSemWifiManager;
            if (semWifiManager != null) {
                semWifiManager.setWifiSettingsForegroundState(0);
            }
            Log.d(str, "removing wififoreground");
        }
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    WifiTile wifiTile = WifiTile.this;
                    AnonymousClass2 anonymousClass2 = wifiTile.mFoldStateChangedListener;
                    if (anonymousClass2 == null || (displayLifecycle = wifiTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(anonymousClass2);
                }
            });
        } catch (Exception e) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("destroy exception:", Log.getStackTraceString(e), str);
        }
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mSubscreenWifiTileReceiver);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        if (this.mIsSatelliteModeOn) {
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiTileBlocked() || isBlockedByEASPolicy$1()) {
            super.showItPolicyToast();
            return;
        }
        if (!((AccessPointControllerImpl) this.mAccessPointController).canConfigWifi()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
            return;
        }
        showDetail(true);
        if (this.mWifiManager != null) {
            Message message = new Message();
            message.what = 74;
            Bundle bundle = new Bundle();
            bundle.putBoolean("enable", false);
            bundle.putBoolean("lock", true);
            message.obj = bundle;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (z) {
            SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
            AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
            if (z) {
                satelliteModeObserverHelper.addCallback(anonymousClass1);
            } else {
                satelliteModeObserverHelper.removeCallback(anonymousClass1);
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (this.DEBUG) {
            Log.d(this.TAG, "handleUpdateState arg=" + obj);
        }
        CallbackInfo callbackInfo = new CallbackInfo();
        synchronized (this.mSignalCallback.mInfo) {
            this.mSignalCallback.mInfo.copyTo(callbackInfo);
        }
        boolean z = callbackInfo.enabled;
        if (this.mExpectDisabled) {
            if (z) {
                return;
            } else {
                this.mExpectDisabled = false;
            }
        }
        boolean z2 = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean z3 = z && callbackInfo.wifiSignalIconId > 0 && callbackInfo.ssid != null;
        if (callbackInfo.wifiSignalIconId > 0) {
            String str = callbackInfo.ssid;
        }
        String str2 = "";
        if (booleanState.value != z) {
            this.mDetailAdapter.setItemsVisible(z);
            fireToggleStateChanged(z);
        }
        boolean z4 = z2 || callbackInfo.isTransient || this.mIsSatelliteModeOn;
        String str3 = this.TAG;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("handleUpdateState isTransient=", " transientEnabling =", " cb.isTransient=", z4, z2);
        m.append(callbackInfo.isTransient);
        m.append(" state.state = ");
        m.append(booleanState.state);
        m.append(" mStateBeforeClick.value =");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(m, this.mStateBeforeClick.value, " enabled =", z, str3);
        booleanState.dualTarget = true;
        booleanState.value = z;
        if (z4 && (!this.mIsTransientEnabled || this.mIsSatelliteModeOn)) {
            booleanState.icon = this.mDisable;
            booleanState.state = 0;
            booleanState.label = getTileLabel();
            this.mIsTransientEnabled = true;
        } else if (z) {
            booleanState.state = 2;
            if (z3) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_wifi_on_013);
                str2 = removeDoubleQuotes(callbackInfo.ssid);
            } else {
                booleanState.icon = this.mEnable;
                booleanState.label = getTileLabel();
            }
        } else {
            booleanState.state = 1;
            booleanState.icon = this.mDisable;
            booleanState.label = getTileLabel();
            this.mIsTransientEnabled = false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (z3) {
            stringBuffer.append(getTileLabel());
            stringBuffer.append(",");
            stringBuffer.append(removeDoubleQuotes(callbackInfo.ssid));
        } else {
            stringBuffer.append(booleanState.label);
        }
        booleanState.contentDescription = stringBuffer.toString();
        booleanState.secondaryLabel = str2;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBlockedByEASPolicy$1() {
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mDevicePolicyManager;
            if (iDevicePolicyManager != null) {
                return !iDevicePolicyManager.semGetAllowWifi((ComponentName) null, ActivityManager.getCurrentUser());
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
        if (this.mDetailListening == z) {
            return;
        }
        this.mDetailListening = z;
        WifiDetailAdapter wifiDetailAdapter = this.mDetailAdapter;
        AccessPointController accessPointController = this.mAccessPointController;
        if (z) {
            AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) accessPointController;
            accessPointControllerImpl.addAccessPointCallback(wifiDetailAdapter);
            if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
                accessPointControllerImpl.mWifiApBleCallbacks.add(wifiDetailAdapter);
                if (accessPointControllerImpl.mWifiApBleCallbacks.size() == 1) {
                    accessPointControllerImpl.mSemWifiManager.registerWifiApSmartCallback(accessPointControllerImpl.mSemWifiApSmartCallback, accessPointControllerImpl.mWifiPickerTrackerFactory.context.getMainExecutor());
                    return;
                }
                return;
            }
            return;
        }
        AccessPointControllerImpl accessPointControllerImpl2 = (AccessPointControllerImpl) accessPointController;
        accessPointControllerImpl2.removeAccessPointCallback(wifiDetailAdapter);
        if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
            accessPointControllerImpl2.mWifiApBleCallbacks.remove(wifiDetailAdapter);
            if (accessPointControllerImpl2.mWifiApBleCallbacks.size() == 0) {
                accessPointControllerImpl2.mSemWifiManager.unregisterWifiApSmartCallback(accessPointControllerImpl2.mSemWifiApSmartCallback);
            }
        }
    }
}
