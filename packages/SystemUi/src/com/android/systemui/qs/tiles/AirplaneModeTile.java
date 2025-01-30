package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.sysprop.TelephonyProperties;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.SystemUIDialogUtils;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AirplaneModeTile extends SQSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAirplaneTileModeChanged;
    public SystemUIDialog mAlertDialog;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public final C22012 mFoldStateChangedListener;
    public boolean mIsWiFiOnlyDevice;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final NetworkController mNetworkController;
    public final C22045 mReceiver;
    public final C22001 mSetting;
    public final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public SubscreenAirplaneModeTileReceiver mSubscreenAirplaneModeTileReceiver;
    public final Context mSubscreenContext;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public final UserManager mUserManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubscreenAirplaneModeTileReceiver extends BroadcastReceiver {
        public SubscreenAirplaneModeTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("AIRPLANE_MODE_CHANGE")) {
                AirplaneModeTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.AirplaneModeTile$1] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.qs.tiles.AirplaneModeTile$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.qs.tiles.AirplaneModeTile$5] */
    public AirplaneModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, UserTracker userTracker, PanelInteractor panelInteractor, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mStateBeforeClick = new QSTile.BooleanState();
        new ArrayList();
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_on, R.drawable.quick_panel_icon_airplane_mode_on_016);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_airplane_mode_off, R.drawable.quick_panel_icon_airplane_mode_on_016);
        this.mAirplaneTileModeChanged = false;
        this.mSubscreenQsPanelController = null;
        ?? r4 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_PANEL_SUBSCREEN) {
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
        this.mFoldStateChangedListener = r4;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Log.d("AirplaneModeTile", "onReceive " + intent.getAction());
                Object obj = null;
                if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    if (airplaneModeTile.mAirplaneTileModeChanged) {
                        if (!DeviceType.isWiFiOnlyDevice()) {
                            int i = AirplaneModeTile.$r8$clinit;
                            obj = SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
                        }
                        airplaneModeTile.refreshState(obj);
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
        this.mBroadcastDispatcher = broadcastDispatcher;
        if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class);
            ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getClass();
            this.mSubscreenContext = SubscreenQsPanelController.mContext;
            displayLifecycle.addObserver(r4);
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN && this.mSubscreenAirplaneModeTileReceiver == null && broadcastDispatcher != null) {
            SubscreenAirplaneModeTileReceiver subscreenAirplaneModeTileReceiver = new SubscreenAirplaneModeTileReceiver();
            this.mSubscreenAirplaneModeTileReceiver = subscreenAirplaneModeTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenAirplaneModeTileReceiver, new IntentFilter("AIRPLANE_MODE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.AIRPLANE_STATE_CHANGE");
        }
        this.mSetting = new SettingObserver(globalSettings, ((SQSTileImpl) this).mHandler, "airplane_mode_on", ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("handleValueChanged, value = ", i, ",mSetting.getValue() = ");
                m1m.append(getValue());
                Log.d("AirplaneModeTile", m1m.toString());
                AirplaneModeTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }

    public final Context getContext() {
        DisplayLifecycle displayLifecycle;
        return (!QpRune.QUICK_PANEL_SUBSCREEN || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) ? this.mContext : this.mSubscreenContext;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isAirplaneModeTileBlocked()) {
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
        return (i == R.string.airplane_mode_show_popup_summary || i == R.string.quick_settings_flight_mode_detail_summary) ? (DeviceType.isTablet() && DeviceType.isWiFiOnlyDevice()) ? R.string.airplane_mode_show_popup_message_wifi : (!DeviceType.isTablet() || DeviceState.isVoiceCapable(this.mContext)) ? Operator.QUICK_IS_CHM_BRANDING ? R.string.airplane_mode_show_popup_message_cmcc : Operator.isUSAQsTileBranding() ? R.string.airplane_mode_show_popup_message : i : R.string.airplane_mode_show_popup_message_lte_without_call_feature_tablet : i == R.string.airplane_mode_show_popup_title ? Operator.isUSAQsTileBranding() ? R.string.airplane_mode_show_popup_title_vzw : i : i == R.string.f789ok ? (Operator.QUICK_IS_ATT_BRANDING || Operator.QUICK_IS_SPR_BRANDING || Operator.QUICK_IS_TMB_BRANDING) ? R.string.quick_settings_enable : i : i;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_airplane_mode_label);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x010c, code lost:
    
        if (android.provider.Settings.System.getInt(r4.getContentResolver(), "off_menu_setting", 0) == 1) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0102  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleClick(final View view) {
        boolean z;
        PackageManager packageManager;
        int i = ((QSTile.BooleanState) this.mState).state;
        if (i == 0) {
            return;
        }
        boolean z2 = true;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        boolean z3 = false;
        Context context = this.mContext;
        if (i == 1 && settingsHelper.mItemLists.get("emergency_message_working_state").getIntValue() == 1) {
            Toast.makeText(QpRune.QUICK_PANEL_SUBSCREEN ? getContext() : context, context.getString(R.string.airplane_mode_toast_emergency_sharing_on), 0).show();
            return;
        }
        Point point = DeviceState.sDisplaySize;
        boolean z4 = ((TelecomManager) context.getSystemService("telecom")) != null ? !r0.isInCall() : true;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isTelephonyIdle() - ", z4, "DeviceState");
        if (!z4 && !((QSTile.BooleanState) this.mState).value) {
            Toast.makeText(QpRune.QUICK_PANEL_SUBSCREEN ? getContext() : context, context.getString(R.string.airplane_mode_toast_impossible_during_call), 0).show();
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isAirplaneModeTileBlocked()) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        boolean z5 = ((QSTile.BooleanState) this.mState).value;
        boolean z6 = !z5;
        MetricsLogger.action(context, 112, z6);
        ActivityStarter activityStarter = this.mActivityStarter;
        if (!z5 && ((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS"), 0);
            return;
        }
        Log.d("AirplaneModeTile", "handleClick");
        if (Operator.QUICK_IS_SKT_BRANDING) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardSecure() && keyguardManager.inKeyguardRestrictedInputMode()) {
                try {
                    packageManager = context.getPackageManager();
                } catch (Exception unused) {
                }
                if (packageManager != null) {
                    z = packageManager.getApplicationInfo("com.skt.t_smart_charge", 0) != null;
                    if (z) {
                        Log.d("AirplaneModeTile", "supportTLockPackage()");
                    }
                    if (z) {
                    }
                }
                z = false;
                if (z) {
                }
            }
            z2 = false;
            if (z2) {
                Toast.makeText(QpRune.QUICK_PANEL_SUBSCREEN ? getContext() : context, context.getString(R.string.airplane_mode_show_popup_safelock), 0).show();
                return;
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z7 = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z7 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled()) {
            if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AirplaneModeTile.this.handleClick(view);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).showLockscreenOnCoverScreen(context, "AIRPLANE_MODE_CHANGE");
                return;
            }
        }
        Log.d("AirplaneModeTile", "isShowing() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
        if (Operator.QUICK_IS_OJT_BRANDING && !((QSTile.BooleanState) this.mState).value && !DeviceType.isMultiSimSupported()) {
            Toast.makeText(QpRune.QUICK_PANEL_SUBSCREEN ? getContext() : context, context.getString(R.string.airplane_mode_show_toast_turn_on_wifi_for_wificalling), 0).show();
        }
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z5 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        if (!QpRune.QUICK_ONE_UI_6_1 && Operator.QUICK_IS_ATT_BRANDING) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("DoNotshowAgainAirplaneModeOn", 0);
            if (sharedPreferences != null) {
                z3 = sharedPreferences.getBoolean("DoNotshowAgainAirplaneModeOn", false);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("doNotShowAgain :", z3, "AirplaneModeTile");
            }
            if (!z3 && !((QSTile.BooleanState) this.mState).value) {
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayLifecycle displayLifecycle2;
                        final AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                        SystemUIDialog systemUIDialog = airplaneModeTile.mAlertDialog;
                        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
                            boolean z8 = QpRune.QUICK_PANEL_SUBSCREEN;
                            Context context2 = airplaneModeTile.mContext;
                            View inflate = (!z8 || (displayLifecycle2 = airplaneModeTile.mDisplayLifecycle) == null || displayLifecycle2.mIsFolderOpened) ? View.inflate(context2, R.layout.quick_settings_airplane_checkbox_alertdialog, null) : View.inflate(airplaneModeTile.getContext(), R.layout.subscreen_airplane_checkbox_alertdialog, null);
                            TextView textView = (TextView) inflate.findViewById(R.id.airplane_message_text);
                            if (Operator.QUICK_IS_VZW_BRANDING) {
                                textView.setText(R.string.airplane_mode_show_popup_message_vzw);
                            } else {
                                textView.setText(airplaneModeTile.getStringID(R.string.airplane_mode_show_popup_summary));
                            }
                            final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.airplane_message_checkbox);
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(airplaneModeTile) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.6
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(CompoundButton compoundButton, boolean z9) {
                                    SystemUIAnalytics.sendEventLog(z9 ? 1000 : 0, SystemUIAnalytics.sCurrentScreenID, "4247");
                                }
                            });
                            if (Operator.QUICK_IS_ATT_BRANDING) {
                                checkBox.setVisibility(0);
                            }
                            if (z8) {
                                airplaneModeTile.mAlertDialog = SystemUIDialogUtils.createSystemUIDialogUtils(2132018528, airplaneModeTile.getContext());
                            } else {
                                airplaneModeTile.mAlertDialog = new SystemUIDialog(context2, 2132018528);
                            }
                            airplaneModeTile.mAlertDialog.setTitle(airplaneModeTile.getStringID(R.string.airplane_mode_show_popup_title));
                            airplaneModeTile.mAlertDialog.setView(inflate);
                            airplaneModeTile.mAlertDialog.setPositiveButton(airplaneModeTile.getStringID(R.string.f789ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.7
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    if (checkBox.isChecked()) {
                                        AirplaneModeTile airplaneModeTile2 = AirplaneModeTile.this;
                                        int i3 = AirplaneModeTile.$r8$clinit;
                                        SharedPreferences sharedPreferences2 = airplaneModeTile2.mContext.getSharedPreferences("DoNotshowAgainAirplaneModeOn", 0);
                                        if (sharedPreferences2 != null) {
                                            SharedPreferences.Editor edit = sharedPreferences2.edit();
                                            edit.putBoolean("DoNotshowAgainAirplaneModeOn", true);
                                            edit.commit();
                                        }
                                    }
                                    AirplaneModeTile airplaneModeTile3 = AirplaneModeTile.this;
                                    int i4 = AirplaneModeTile.$r8$clinit;
                                    airplaneModeTile3.refreshState(SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
                                    AirplaneModeTile.this.setEnabled(true);
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "4248");
                                }
                            });
                            airplaneModeTile.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(airplaneModeTile) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.8
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "4249");
                                    dialogInterface.cancel();
                                }
                            });
                            airplaneModeTile.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.9
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    AirplaneModeTile.this.refreshState(null);
                                }
                            });
                            SystemUIDialog.setWindowOnTop(airplaneModeTile.mAlertDialog, ((KeyguardStateControllerImpl) airplaneModeTile.mKeyguardStateController).mShowing);
                            airplaneModeTile.mAlertDialog.show();
                        }
                    }
                });
                return;
            }
        }
        setEnabled(z6);
        if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2018");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenAirplaneModeTileReceiver subscreenAirplaneModeTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        Log.d("AirplaneModeTile", "handleDestroy");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    C22012 c22012 = airplaneModeTile.mFoldStateChangedListener;
                    if (c22012 == null || (displayLifecycle = airplaneModeTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(c22012);
                }
            });
        } catch (Exception e) {
            AbstractC0000x2c234b15.m3m("destroy exception:", Log.getStackTraceString(e), "AirplaneModeTile");
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (subscreenAirplaneModeTileReceiver = this.mSubscreenAirplaneModeTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenAirplaneModeTileReceiver);
        this.mSubscreenAirplaneModeTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        showDetail(true);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        C22045 c22045 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.intent.action.SERVICE_STATE");
            broadcastDispatcher.registerReceiver(intentFilter, c22045);
        } else {
            broadcastDispatcher.unregisterReceiver(c22045);
        }
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_airplane_mode");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        Context context = this.mContext;
        boolean isNoSimState = DeviceState.isNoSimState(context);
        this.mIsWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean isPowerOffServiceState = ((NetworkControllerImpl) this.mNetworkController).isPowerOffServiceState();
        StringBuilder sb = new StringBuilder(" handleUpdateState mIsWiFiOnlyDevice ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.mIsWiFiOnlyDevice, " isNoSimState ", isNoSimState, " isNoSimState  isPowerOffServiceState ");
        sb.append(isPowerOffServiceState);
        sb.append(" mSetting.getValue() ");
        C22001 c22001 = this.mSetting;
        sb.append(c22001.getValue());
        Log.d("AirplaneModeTile", sb.toString());
        if (z) {
            booleanState.value = ((QSTile.BooleanState) this.mState).value;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
            booleanState.state = 0;
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m(" handleUpdateState:  isTransient  ", z, " state.value ");
            m49m.append(booleanState.value);
            m49m.append(" state.state ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(m49m, booleanState.state, "AirplaneModeTile");
        } else {
            if (c22001.getValue() == 1 && (this.mIsWiFiOnlyDevice || isNoSimState || isPowerOffServiceState)) {
                booleanState.value = true;
                booleanState.icon = this.mEnable;
                booleanState.state = 2;
            } else if (c22001.getValue() == 0 && (this.mIsWiFiOnlyDevice || isNoSimState || !isPowerOffServiceState)) {
                booleanState.value = false;
                booleanState.icon = this.mDisable;
                booleanState.state = 1;
            } else {
                Log.d("AirplaneModeTile", "Tile.STATE_UNAVAILABLE");
                booleanState.value = ((QSTile.BooleanState) this.mState).value;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_airplane_mode_dim);
                booleanState.state = 0;
            }
            StringBuilder sb2 = new StringBuilder(" handleUpdateState:  value = ");
            sb2.append(booleanState.value);
            sb2.append(", state = ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb2, booleanState.state, "AirplaneModeTile");
        }
        booleanState.dualTarget = true;
        booleanState.label = context.getString(R.string.quick_settings_airplane_mode_label);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (this.mUserManager.getUserInfo(ActivityManager.getCurrentUser()).isRestricted()) {
            return false;
        }
        return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void setEnabled(boolean z) {
        if (((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } else {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setEnabled :", z, "AirplaneModeTile");
            this.mAirplaneTileModeChanged = true;
            this.mSettingsHelper.setAirplaneMode(z);
            Intent intent2 = new Intent("android.intent.action.AIRPLANE_MODE");
            intent2.putExtra("state", z);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl
    public final boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.BooleanState) this.mState).value;
    }
}
