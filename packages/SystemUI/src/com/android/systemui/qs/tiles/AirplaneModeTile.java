package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.sysprop.TelephonyProperties;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AirplaneModeTile extends SQSTileImpl {
    public boolean mAirplaneTileModeChanged;
    public SystemUIDialog mAlertDialog;
    public final BroadcastDispatcher mBroadcastDispatcher;
    Job mClickJob;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public boolean mIsSatelliteModeOn;
    public boolean mIsWiFiOnlyDevice;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Lazy mLazyConnectivityManager;
    public boolean mListening;
    public final NetworkController mNetworkController;
    public final AnonymousClass4 mReceiver;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    public final AnonymousClass2 mSetting;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.qs.tiles.AirplaneModeTile$2] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.tiles.AirplaneModeTile$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qs.tiles.AirplaneModeTile$4] */
    public AirplaneModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, Lazy lazy, GlobalSettings globalSettings, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UserTracker userTracker, SatelliteModeObserverHelper satelliteModeObserverHelper, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mStateBeforeClick = new QSTile.BooleanState();
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_on, R.drawable.quick_panel_icon_airplane_mode_on_016);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_off, R.drawable.quick_panel_icon_airplane_mode_on_016);
        QSTileImpl.ResourceIcon.get(android.R.drawable.ic_spinner_caret);
        this.mAirplaneTileModeChanged = false;
        this.mIsSatelliteModeOn = false;
        this.mSatelliteModeCallback = new SatelliteModeObserver$SatelliteModeCallback() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback
            public final void onSatelliteModeChanged(boolean z) {
                AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                airplaneModeTile.mIsSatelliteModeOn = z;
                airplaneModeTile.refreshState(null);
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Log.d(AirplaneModeTile.this.TAG, "onReceive " + intent.getAction());
                if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    if (airplaneModeTile.mAirplaneTileModeChanged) {
                        airplaneModeTile.refreshState(DeviceType.isWiFiOnlyDevice() ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
                        AirplaneModeTile.this.mAirplaneTileModeChanged = false;
                        return;
                    }
                }
                if ("android.intent.action.SERVICE_STATE".equals(intent.getAction())) {
                    AirplaneModeTile.this.refreshState(null);
                }
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mNetworkController = networkController;
        this.mSettingsHelper = settingsHelper;
        this.mIsWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLazyConnectivityManager = lazy;
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            this.mDisplayLifecycle = displayLifecycle;
        }
        this.mSetting = new SettingObserver(globalSettings, ((SQSTileImpl) this).mHandler, SettingsHelper.INDEX_AIRPLANE_MODE_ON) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.2
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                String str = AirplaneModeTile.this.TAG;
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "handleValueChanged, value = ", ",mSetting.getValue() = ");
                m.append(getValue());
                Log.d(str, m.toString());
                AirplaneModeTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (this.mIsSatelliteModeOn) {
            return null;
        }
        if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAirplaneModeTileBlocked()) {
            return new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
        }
        showItPolicyToast();
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 112;
    }

    public final int getStringID(int i) {
        return (i == R.string.airplane_mode_show_popup_summary || i == R.string.quick_settings_flight_mode_detail_summary) ? (DeviceType.isTablet() && DeviceType.isWiFiOnlyDevice()) ? R.string.airplane_mode_show_popup_message_wifi : (!DeviceType.isTablet() || DeviceState.isVoiceCapable(this.mContext)) ? Operator.QUICK_IS_CHM_BRANDING ? R.string.airplane_mode_show_popup_message_cmcc : Operator.isUSAQsTileBranding() ? R.string.airplane_mode_show_popup_message : i : R.string.airplane_mode_show_popup_message_lte_without_call_feature_tablet : i == R.string.airplane_mode_show_popup_title ? Operator.isUSAQsTileBranding() ? R.string.airplane_mode_show_popup_title_vzw : i : i == R.string.ok ? (Operator.QUICK_IS_ATT_BRANDING || Operator.QUICK_IS_SPR_BRANDING || Operator.QUICK_IS_TMB_BRANDING) ? R.string.quick_settings_enable : i : i;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_airplane_mode_label);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e5, code lost:
    
        if (android.provider.Settings.System.getInt(r8.mContext.getContentResolver(), "off_menu_setting", 0) == 1) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d9  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleClick(final com.android.systemui.animation.Expandable r9) {
        /*
            Method dump skipped, instructions count: 495
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.AirplaneModeTile.handleClick(com.android.systemui.animation.Expandable):void");
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        if (this.mIsSatelliteModeOn) {
            return;
        }
        super.handleLongClick(expandable);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        AnonymousClass4 anonymousClass4 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.intent.action.SERVICE_STATE");
            satelliteModeObserverHelper.addCallback(anonymousClass1);
            broadcastDispatcher.registerReceiver(intentFilter, anonymousClass4);
        } else {
            broadcastDispatcher.unregisterReceiver(anonymousClass4);
            satelliteModeObserverHelper.removeCallback(anonymousClass1);
        }
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_airplane_mode");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING || this.mIsSatelliteModeOn;
        boolean isNoSimState = DeviceState.isNoSimState(this.mContext);
        this.mIsWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean isPowerOffServiceState = ((NetworkControllerImpl) this.mNetworkController).isPowerOffServiceState();
        StringBuilder sb = new StringBuilder(" handleUpdateState mIsWiFiOnlyDevice ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mIsWiFiOnlyDevice, " isNoSimState ", isNoSimState, " isPowerOffServiceState ");
        sb.append(isPowerOffServiceState);
        sb.append(" mSetting.getValue() ");
        AnonymousClass2 anonymousClass2 = this.mSetting;
        sb.append(anonymousClass2.getValue());
        String sb2 = sb.toString();
        String str = this.TAG;
        Log.d(str, sb2);
        if (z) {
            booleanState.value = ((QSTile.BooleanState) this.mState).value;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
            booleanState.state = 0;
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m(" handleUpdateState:  isTransient  ", "state.value ", z);
            m.append(booleanState.value);
            m.append("state.state ");
            RecyclerView$$ExternalSyntheticOutline0.m(booleanState.state, str, m);
        } else {
            if (anonymousClass2.getValue() == 1 && (this.mIsWiFiOnlyDevice || isNoSimState || isPowerOffServiceState)) {
                booleanState.value = true;
                booleanState.icon = this.mEnable;
                booleanState.state = 2;
            } else if (anonymousClass2.getValue() == 0 && (this.mIsWiFiOnlyDevice || isNoSimState || !isPowerOffServiceState)) {
                booleanState.value = false;
                booleanState.icon = this.mDisable;
                booleanState.state = 1;
            } else {
                Log.d(str, "Tile.STATE_UNAVAILABLE");
                booleanState.value = ((QSTile.BooleanState) this.mState).value;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
                booleanState.state = 0;
            }
            StringBuilder sb3 = new StringBuilder(" handleUpdateState:  value = ");
            sb3.append(booleanState.value);
            sb3.append(", state = ");
            RecyclerView$$ExternalSyntheticOutline0.m(booleanState.state, str, sb3);
        }
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_airplane_mode_label);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void setEnabled$1(boolean z) {
        if (((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } else {
            Log.d(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setEnabled :", z));
            this.mAirplaneTileModeChanged = true;
            ((ConnectivityManager) this.mLazyConnectivityManager.get()).setAirplaneMode(z);
        }
    }
}
