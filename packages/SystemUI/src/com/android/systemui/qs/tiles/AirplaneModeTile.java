package com.android.systemui.qs.tiles;

import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class AirplaneModeTile extends SQSTileImpl {
    public boolean mAirplaneTileModeChanged;
    public final BroadcastDispatcher mBroadcastDispatcher;
    Job mClickJob;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public final AnonymousClass3 mFoldStateChangedListener;
    public boolean mIsSatelliteModeOn;
    public boolean mIsWiFiOnlyDevice;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Lazy mLazyConnectivityManager;
    public boolean mListening;
    public final NetworkController mNetworkController;
    public final AnonymousClass5 mReceiver;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    public final AnonymousClass2 mSetting;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public SubscreenAirplaneModeTileReceiver mSubscreenAirplaneModeTileReceiver;
    public final SubscreenQsPanelController mSubscreenQsPanelController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SubscreenAirplaneModeTileReceiver extends BroadcastReceiver {
        public SubscreenAirplaneModeTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("AIRPLANE_MODE_CHANGE")) {
                ((SQSTileImpl) AirplaneModeTile.this).mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile$SubscreenAirplaneModeTileReceiver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) AirplaneModeTile.this.mContext.getSystemService("sem_statusbar");
                        if (semStatusBarManager != null) {
                            semStatusBarManager.expandQuickSettingsPanel();
                        }
                    }
                }, 500L);
                AirplaneModeTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.qs.tiles.AirplaneModeTile$1] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.qs.tiles.AirplaneModeTile$2] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.tiles.AirplaneModeTile$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.qs.tiles.AirplaneModeTile$5] */
    public AirplaneModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, Lazy lazy, GlobalSettings globalSettings, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UserTracker userTracker, SatelliteModeObserverHelper satelliteModeObserverHelper, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mStateBeforeClick = new QSTile.BooleanState();
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_on, R.drawable.quick_panel_icon_airplane_mode_on_016);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_off, R.drawable.quick_panel_icon_airplane_mode_on_016);
        QSTileImpl.ResourceIcon.get(android.R.drawable.item_background_material_dark);
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
        this.mSubscreenQsPanelController = null;
        ?? r3 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.3
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_SUBSCREEN_PANEL) {
                    return;
                }
                AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                if (z) {
                    airplaneModeTile.mSubscreenQsPanelController.getInstance(3).registerReceiver(false);
                } else {
                    airplaneModeTile.mSubscreenQsPanelController.getInstance(3).unRegisterReceiver(false);
                }
            }
        };
        this.mFoldStateChangedListener = r3;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.5
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
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
            displayLifecycle.addObserver(r3);
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (QpRune.QUICK_SUBSCREEN_PANEL && this.mSubscreenAirplaneModeTileReceiver == null && broadcastDispatcher != null) {
            SubscreenAirplaneModeTileReceiver subscreenAirplaneModeTileReceiver = new SubscreenAirplaneModeTileReceiver();
            this.mSubscreenAirplaneModeTileReceiver = subscreenAirplaneModeTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenAirplaneModeTileReceiver, new IntentFilter("AIRPLANE_MODE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.AIRPLANE_STATE_CHANGE");
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
        if (!Rune.SYSUI_CHINA_FEATURE && this.mIsSatelliteModeOn) {
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_airplane_mode_label);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0102  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleClick(final com.android.systemui.animation.Expandable r12) {
        /*
            Method dump skipped, instructions count: 537
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.AirplaneModeTile.handleClick(com.android.systemui.animation.Expandable):void");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenAirplaneModeTileReceiver subscreenAirplaneModeTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        String str = this.TAG;
        Log.d(str, "handleDestroy");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.4
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    AnonymousClass3 anonymousClass3 = airplaneModeTile.mFoldStateChangedListener;
                    if (anonymousClass3 == null || (displayLifecycle = airplaneModeTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(anonymousClass3);
                }
            });
        } catch (Exception e) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("destroy exception:", Log.getStackTraceString(e), str);
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (subscreenAirplaneModeTileReceiver = this.mSubscreenAirplaneModeTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenAirplaneModeTileReceiver);
        this.mSubscreenAirplaneModeTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        if (Rune.SYSUI_CHINA_FEATURE || !this.mIsSatelliteModeOn) {
            super.handleLongClick(expandable);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        AnonymousClass5 anonymousClass5 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (z) {
            IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.AIRPLANE_MODE", "android.intent.action.SERVICE_STATE");
            if (satelliteModeObserverHelper != null) {
                satelliteModeObserverHelper.addCallback(anonymousClass1);
            }
            broadcastDispatcher.registerReceiver(m, anonymousClass5);
        } else {
            broadcastDispatcher.unregisterReceiver(anonymousClass5);
            if (satelliteModeObserverHelper != null) {
                satelliteModeObserverHelper.removeCallback(anonymousClass1);
            }
        }
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_airplane_mode");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean isNoSimState = DeviceState.isNoSimState(this.mContext);
        this.mIsWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean isPowerOffServiceState = ((NetworkControllerImpl) this.mNetworkController).isPowerOffServiceState();
        StringBuilder sb = new StringBuilder(" handleUpdateState mIsWiFiOnlyDevice ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mIsWiFiOnlyDevice, " isNoSimState ", isNoSimState, " isPowerOffServiceState ");
        sb.append(isPowerOffServiceState);
        sb.append(" mSetting.getValue() ");
        AnonymousClass2 anonymousClass2 = this.mSetting;
        sb.append(anonymousClass2.getValue());
        sb.append(" isSatelliteModeOn ");
        sb.append(this.mIsSatelliteModeOn);
        sb.append(" isChinaDevice ");
        boolean z2 = Rune.SYSUI_CHINA_FEATURE;
        sb.append(z2);
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
            if (anonymousClass2.getValue() == 1 && (this.mIsWiFiOnlyDevice || isNoSimState || ((this.mIsSatelliteModeOn && z2) || isPowerOffServiceState))) {
                booleanState.value = true;
                booleanState.icon = this.mEnable;
                booleanState.state = 2;
            } else if (anonymousClass2.getValue() == 0 && (this.mIsWiFiOnlyDevice || isNoSimState || ((this.mIsSatelliteModeOn && z2) || !isPowerOffServiceState))) {
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
