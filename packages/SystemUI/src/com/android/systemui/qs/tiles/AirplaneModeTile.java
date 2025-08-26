package com.android.systemui.qs.tiles;

import android.app.KeyguardManager;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.sysprop.TelephonyProperties;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
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
import com.android.systemui.qp.util.SubscreenUtil;
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
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteEnabledListener;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.statusbar.policy.SatelliteTrtListener;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
public class AirplaneModeTile extends SQSTileImpl {
    public boolean mAirplaneTileModeChanged;
    public final BroadcastDispatcher mBroadcastDispatcher;
    Job mClickJob;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public final AnonymousClass5 mFoldStateChangedListener;
    public boolean mIsSatelliteEnabled;
    public boolean mIsSatelliteModeOn;
    public boolean mIsSupportedEsos;
    public boolean mIsUsingTerrestrialNetwork;
    public boolean mIsWiFiOnlyDevice;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Lazy mLazyConnectivityManager;
    public boolean mListening;
    public final NetworkController mNetworkController;
    public final AnonymousClass7 mReceiver;
    public final AnonymousClass2 mSatelliteEsosCallback;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    public final AnonymousClass3 mSatelliteTrtCallback;
    public final AnonymousClass4 mSetting;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public SubscreenAirplaneModeTileReceiver mSubscreenAirplaneModeTileReceiver;
    public final SubscreenQsPanelController mSubscreenQsPanelController;

    /* renamed from: com.android.systemui.qs.tiles.AirplaneModeTile$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

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
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.systemui.qs.tiles.AirplaneModeTile$4] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.qs.tiles.AirplaneModeTile$1] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.qs.tiles.AirplaneModeTile$3] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.tiles.AirplaneModeTile$5, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.qs.tiles.AirplaneModeTile$7] */
    public AirplaneModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, Lazy lazy, GlobalSettings globalSettings, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UserTracker userTracker, SatelliteModeObserverHelper satelliteModeObserverHelper, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mStateBeforeClick = new QSTile.BooleanState();
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_on, R.drawable.quick_panel_icon_airplane_mode_on_016);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_off, R.drawable.quick_panel_icon_airplane_mode_on_016);
        QSTileImpl.ResourceIcon.get(android.R.drawable.item_background_material_dark);
        this.mAirplaneTileModeChanged = false;
        this.mIsSatelliteModeOn = false;
        this.mIsSatelliteEnabled = false;
        this.mIsSupportedEsos = false;
        this.mIsUsingTerrestrialNetwork = false;
        this.mSatelliteModeCallback = new SatelliteEnabledListener() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteEnabledListener
            public final void onSatelliteEnabledChanged(boolean z) {
                AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                airplaneModeTile.mIsSatelliteEnabled = z;
                airplaneModeTile.mIsSatelliteModeOn = (z || airplaneModeTile.mIsUsingTerrestrialNetwork) && airplaneModeTile.mIsSupportedEsos;
                airplaneModeTile.refreshState(null);
            }
        };
        this.mSatelliteEsosCallback = new AnonymousClass2();
        this.mSatelliteTrtCallback = new SatelliteTrtListener() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.3
            @Override // com.android.systemui.statusbar.policy.SatelliteTrtListener
            public final void onSatelliteTrtChanged(boolean z) {
                AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                airplaneModeTile.mIsUsingTerrestrialNetwork = z;
                airplaneModeTile.mIsSatelliteModeOn = (airplaneModeTile.mIsSatelliteEnabled || z) && airplaneModeTile.mIsSupportedEsos;
                airplaneModeTile.refreshState(null);
            }
        };
        this.mSubscreenQsPanelController = null;
        ?? r3 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.5
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
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.7
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
        this.mSetting = new SettingObserver(globalSettings, ((SQSTileImpl) this).mHandler, SettingsHelper.INDEX_AIRPLANE_MODE_ON) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.4
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                String str = AirplaneModeTile.this.TAG;
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "handleValueChanged, value = ", ",mSetting.getValue() = ");
                sbM.append(getValue());
                Log.d(str, sbM.toString());
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

    /* JADX WARN: Removed duplicated region for block: B:55:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0102  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleClick(final Expandable expandable) {
        boolean z;
        boolean z2;
        PackageManager packageManager;
        if (this.mIsSatelliteModeOn) {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.sec_satellite_mode_airplane_mode_toast_text), 0).show();
            return;
        }
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        if (this.mSettingsHelper.getEmergencyState() == 1) {
            Context context2 = this.mContext;
            Toast.makeText(context2, context2.getString(R.string.airplane_mode_toast_emergency_sharing_on), 0).show();
            return;
        }
        if (!DeviceState.isTelephonyIdle(this.mContext) && !((QSTile.BooleanState) this.mState).value) {
            Context context3 = this.mContext;
            Toast.makeText(context3, context3.getString(R.string.airplane_mode_toast_impossible_during_call), 0).show();
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAirplaneModeTileBlocked()) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        boolean z3 = ((QSTile.BooleanState) this.mState).value;
        boolean z4 = !z3;
        MetricsLogger.action(this.mContext, 112, z4);
        ActivityStarter activityStarter = this.mActivityStarter;
        if (!z3 && ((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS"), 0);
            return;
        }
        String str = this.TAG;
        Log.d(str, "handleClick");
        if (Operator.QUICK_IS_SKT_BRANDING) {
            KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardSecure() && keyguardManager.inKeyguardRestrictedInputMode()) {
                try {
                    packageManager = this.mContext.getPackageManager();
                } catch (Exception unused) {
                }
                if (packageManager != null) {
                    z2 = packageManager.getApplicationInfo("com.skt.t_smart_charge", 0) != null;
                    if (z2) {
                        Log.d(str, "supportTLockPackage()");
                    }
                } else {
                    z2 = false;
                }
                if (z2 && Settings.System.getInt(this.mContext.getContentResolver(), "off_menu_setting", 0) == 1) {
                    z = true;
                }
                if (z) {
                }
            } else {
                z = false;
                if (z) {
                    Context context4 = this.mContext;
                    Toast.makeText(context4, context4.getString(R.string.airplane_mode_show_popup_safelock), 0).show();
                    return;
                }
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z5 = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z5 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
            if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.handleClick(expandable);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "AIRPLANE_MODE_CHANGE");
                return;
            }
        }
        Log.d(str, "isKeyguardVisible() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + this.mSettingsHelper.isLockFunctionsEnabled());
        if (Operator.QUICK_IS_OJT_BRANDING && !((QSTile.BooleanState) this.mState).value && !DeviceType.isMultiSimSupported()) {
            Context context5 = this.mContext;
            Toast.makeText(context5, context5.getString(R.string.airplane_mode_show_toast_turn_on_wifi_for_wificalling), 0).show();
        }
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z3 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        if (((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(268435456);
            activityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } else {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setEnabled :", str, z4);
            this.mAirplaneTileModeChanged = true;
            ((ConnectivityManager) this.mLazyConnectivityManager.get()).setAirplaneMode(z4);
        }
        if ((!QpRune.QUICK_SUBSCREEN_PANEL && !QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL) || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_AIRPLANEMODE_COVER);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenAirplaneModeTileReceiver subscreenAirplaneModeTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        String str = this.TAG;
        Log.d(str, "handleDestroy");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.6
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    AnonymousClass5 anonymousClass5 = airplaneModeTile.mFoldStateChangedListener;
                    if (anonymousClass5 == null || (displayLifecycle = airplaneModeTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(anonymousClass5);
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
        AnonymousClass3 anonymousClass3 = this.mSatelliteTrtCallback;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        AnonymousClass7 anonymousClass7 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        AnonymousClass2 anonymousClass2 = this.mSatelliteEsosCallback;
        if (z) {
            IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.AIRPLANE_MODE", "android.intent.action.SERVICE_STATE");
            if (satelliteModeObserverHelper != null) {
                satelliteModeObserverHelper.addCallback(anonymousClass1);
                satelliteModeObserverHelper.addCallback(anonymousClass2);
                satelliteModeObserverHelper.addCallback(anonymousClass3);
            }
            broadcastDispatcher.registerReceiver(intentFilterM, anonymousClass7);
        } else {
            broadcastDispatcher.unregisterReceiver(anonymousClass7);
            if (satelliteModeObserverHelper != null) {
                satelliteModeObserverHelper.removeCallback(anonymousClass1);
                satelliteModeObserverHelper.removeCallback(anonymousClass2);
                satelliteModeObserverHelper.removeCallback(anonymousClass3);
            }
        }
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_airplane_mode");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean zIsNoSimState = DeviceState.isNoSimState(this.mContext);
        this.mIsWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean zIsPowerOffServiceState = ((NetworkControllerImpl) this.mNetworkController).isPowerOffServiceState();
        StringBuilder sb = new StringBuilder(" handleUpdateState mIsWiFiOnlyDevice ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mIsWiFiOnlyDevice, " isNoSimState ", zIsNoSimState, " isPowerOffServiceState ");
        sb.append(zIsPowerOffServiceState);
        sb.append(" mSetting.getValue() ");
        AnonymousClass4 anonymousClass4 = this.mSetting;
        sb.append(anonymousClass4.getValue());
        sb.append(" isSatelliteModeOn ");
        sb.append(this.mIsSatelliteModeOn);
        sb.append(" isChinaDevice ");
        boolean z2 = Rune.SYSUI_CHINA_FEATURE;
        sb.append(z2);
        String string = sb.toString();
        String str = this.TAG;
        Log.d(str, string);
        if (z) {
            booleanState.value = ((QSTile.BooleanState) this.mState).value;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
            booleanState.state = 0;
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m(" handleUpdateState:  isTransient  ", "state.value ", z);
            sbM.append(booleanState.value);
            sbM.append("state.state ");
            RecyclerView$$ExternalSyntheticOutline0.m(booleanState.state, str, sbM);
        } else {
            if (anonymousClass4.getValue() == 1 && (this.mIsWiFiOnlyDevice || zIsNoSimState || ((this.mIsSatelliteModeOn && z2) || zIsPowerOffServiceState))) {
                booleanState.value = true;
                booleanState.icon = this.mEnable;
                booleanState.state = 2;
            } else if (anonymousClass4.getValue() == 0 && (this.mIsWiFiOnlyDevice || zIsNoSimState || ((this.mIsSatelliteModeOn && z2) || !zIsPowerOffServiceState))) {
                booleanState.value = false;
                booleanState.icon = this.mDisable;
                booleanState.state = 1;
            } else {
                Log.d(str, "Tile.STATE_UNAVAILABLE");
                booleanState.value = ((QSTile.BooleanState) this.mState).value;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
                booleanState.state = 0;
            }
            StringBuilder sb2 = new StringBuilder(" handleUpdateState:  value = ");
            sb2.append(booleanState.value);
            sb2.append(", state = ");
            RecyclerView$$ExternalSyntheticOutline0.m(booleanState.state, str, sb2);
        }
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_airplane_mode_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
