package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.Operator;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.EdmMonitor;
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
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.SystemUIDialogUtils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.sec.ims.presence.ServiceTuple;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;

public final class MobileDataTile extends SQSTileImpl implements SignalCallback {
    public static final Intent DATA_SETTINGS = new Intent().setAction("android.settings.DATA_USAGE_SETTINGS");
    public static final Intent DATA_SETTINGS_UPSM = new Intent().setAction("com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS");
    public final ActivityStarter mActivityStarter;
    public final AnonymousClass3 mAirplaneSetting;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public CallAttributesListener mCallAttributesListener;
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final AnonymousClass6 mDataRoamingObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mIsSatelliteModeOn;
    public boolean mIsVoLteCall;
    public boolean mIsVolteVideoCall;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass4 mPhoneStateListener;
    public final AnonymousClass5 mReceiver;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    public final AnonymousClass2 mSetting;
    private final SettingsHelper mSettingsHelper;
    public final Context mSubscreenContext;
    public SubscreenMobileDataTileReceiver mSubscreenMobileDataTileReceiver;
    public final SubscreenUtil mSubscreenUtil;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public TelephonyManager mTelephonyManager;
    public final UserTracker mUserTracker;

    public final class CallAttributesListener extends TelephonyCallback implements TelephonyCallback.CallAttributesListener, TelephonyCallback.ServiceStateListener {
        public /* synthetic */ CallAttributesListener(MobileDataTile mobileDataTile, int i) {
            this();
        }

        public final void onCallStatesChanged(List list) {
            MobileDataTile mobileDataTile = MobileDataTile.this;
            Intent intent = MobileDataTile.DATA_SETTINGS;
            Log.e(mobileDataTile.TAG, "onCallStatesChanged: CallStateList = " + list);
            MobileDataTile mobileDataTile2 = MobileDataTile.this;
            TelephonyManager telephonyManager = mobileDataTile2.mTelephonyManager;
            if (telephonyManager != null) {
                mobileDataTile2.mIsVoLteCall = telephonyManager.hasCall(ServiceTuple.MEDIA_CAP_VIDEO) || (MobileDataTile.this.mTelephonyManager.hasCall("volte") && !MobileDataTile.this.mTelephonyManager.hasCall("epdg"));
                MobileDataTile mobileDataTile3 = MobileDataTile.this;
                mobileDataTile3.mIsVolteVideoCall = mobileDataTile3.mTelephonyManager.hasCall(ServiceTuple.MEDIA_CAP_VIDEO);
                String str = MobileDataTile.this.TAG;
                StringBuilder sb = new StringBuilder("onCallAttributesChanged state changed : ");
                sb.append(list);
                sb.append(" isVOLteCall : ");
                sb.append(MobileDataTile.this.mIsVoLteCall);
                sb.append(" isVolteVideoCall : ");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, MobileDataTile.this.mIsVolteVideoCall, str);
                MobileDataTile.this.refreshState(null);
            }
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            boolean roaming = serviceState.getRoaming();
            MobileDataTile mobileDataTile = MobileDataTile.this;
            Intent intent = MobileDataTile.DATA_SETTINGS;
            Log.d(mobileDataTile.TAG, "service state changed : " + serviceState + ",isRoaming = " + roaming);
            MobileDataTile.this.refreshState(null);
        }

        private CallAttributesListener() {
        }
    }

    public abstract class GlobalSetting extends ContentObserver {
        public final Context mContext;
        public final String mSettingName;

        public GlobalSetting(MobileDataTile mobileDataTile, Context context, Handler handler, String str) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public final int getValue() {
            return Settings.Global.getInt(this.mContext.getContentResolver(), this.mSettingName, 0);
        }

        public abstract void handleValueChanged(int i);

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            handleValueChanged(getValue());
        }
    }

    public final class SubscreenMobileDataTileReceiver extends BroadcastReceiver {
        public SubscreenMobileDataTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("MOBILEDATA_STATE_CHANGE")) {
                MobileDataTile.this.handleClick(null);
            }
        }
    }

    public MobileDataTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, BroadcastDispatcher broadcastDispatcher, PanelInteractor panelInteractor, SatelliteModeObserverHelper satelliteModeObserverHelper, UserTracker userTracker, DisplayLifecycle displayLifecycle, SubscreenUtil subscreenUtil) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSatelliteModeCallback = new SatelliteModeObserver$SatelliteModeCallback() { // from class: com.android.systemui.qs.tiles.MobileDataTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback
            public final void onSatelliteModeChanged(boolean z) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                mobileDataTile.mIsSatelliteModeOn = z;
                mobileDataTile.refreshState(null);
            }
        };
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile.4
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onActiveDataSubscriptionIdChanged,subId = ", mobileDataTile.TAG);
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.MobileDataTile.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent2 = MobileDataTile.DATA_SETTINGS;
                Log.d(mobileDataTile.TAG, "action:" + action);
                if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra(ImsProfile.SERVICE_SS);
                    if ("READY".equals(stringExtra) || "LOADED".equals(stringExtra) || "ABSENT".equals(stringExtra)) {
                        MobileDataTile.this.refreshState(null);
                    }
                }
            }
        };
        this.mDataRoamingObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.qs.tiles.MobileDataTile.6
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mSetting = new GlobalSetting(this.mContext, ((SQSTileImpl) this).mHandler, SettingsHelper.INDEX_MOBILE_DATA) { // from class: com.android.systemui.qs.tiles.MobileDataTile.2
            @Override // com.android.systemui.qs.tiles.MobileDataTile.GlobalSetting
            public final void handleValueChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                String str = mobileDataTile.TAG;
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "mobile data has changed value : ", " is enabled : ");
                m.append(MobileDataTile.this.mDataController.isMobileDataEnabled());
                Log.d(str, m.toString());
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mAirplaneSetting = new GlobalSetting(this.mContext, ((SQSTileImpl) this).mHandler, SettingsHelper.INDEX_AIRPLANE_MODE_ON) { // from class: com.android.systemui.qs.tiles.MobileDataTile.3
            @Override // com.android.systemui.qs.tiles.MobileDataTile.GlobalSetting
            public final void handleValueChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "airplane mode  has changed value : ", mobileDataTile.TAG);
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mController = networkController;
        NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) networkController;
        this.mDataController = networkControllerImpl.mDataUsageController;
        this.mTelephonyListenerManager = networkControllerImpl.mTelephonyListenerManager;
        this.mSettingsHelper = settingsHelper;
        this.mActivityStarter = activityStarter;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mUserTracker = userTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mPanelInteractor = panelInteractor;
        this.mCallAttributesListener = new CallAttributesListener(this, 0);
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenUtil = subscreenUtil;
            this.mSubscreenContext = ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext;
            if (this.mSubscreenMobileDataTileReceiver != null || broadcastDispatcher == null) {
                return;
            }
            SubscreenMobileDataTileReceiver subscreenMobileDataTileReceiver = new SubscreenMobileDataTileReceiver();
            this.mSubscreenMobileDataTileReceiver = subscreenMobileDataTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenMobileDataTileReceiver, new IntentFilter("MOBILEDATA_STATE_CHANGE"), null, UserHandle.ALL, 2, null);
        }
    }

    public final Context getContext$2() {
        DisplayLifecycle displayLifecycle;
        return (!QpRune.QUICK_SUBSCREEN_PANEL || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) ? this.mContext : this.mSubscreenContext;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        EdmMonitor edmMonitor;
        if (this.mIsSatelliteModeOn) {
            return null;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isMobileDataTileBlocked() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isUserMobileDataRestricted()) {
            showItPolicyToast();
            return null;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming$1() && ((edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor) == null || !edmMonitor.mRoamingAllowed)) {
            showItPolicyToast();
            return null;
        }
        if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
            if (this.mDataController.isMobileDataSupported()) {
                return DATA_SETTINGS_UPSM;
            }
            showPopupDialog(this.mContext.getString(R.string.insert_sim_card), this.mContext.getString(R.string.insert_sim_card_message), R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 0), 0, null, null);
            return null;
        }
        if (!isNetworkRoaming$1()) {
            return DATA_SETTINGS;
        }
        Intent action = new Intent().setAction("com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS");
        action.putExtra("root_key", "T_GLOBAL_ROAMING");
        action.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        return action;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 115;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_mobile_data_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        final CheckBox checkBox;
        View view;
        TextView textView;
        boolean z;
        EdmMonitor edmMonitor;
        if (Operator.shouldSupportMobileDataNotDisableVolteCall() && (this.mIsVoLteCall || this.mIsVolteVideoCall)) {
            return;
        }
        if (getValue() == 1) {
            SysUIToast.makeText(getContext$2(), R.string.mobile_data_show_toast_airplane_mode, 0).show();
            return;
        }
        StringBuilder sb = new StringBuilder("handleClick : state ");
        sb.append(((QSTile.BooleanState) this.mState).value);
        sb.append(" is enabled :  ");
        DataUsageController dataUsageController = this.mDataController;
        sb.append(dataUsageController.isMobileDataEnabled());
        String sb2 = sb.toString();
        String str = this.TAG;
        Log.d(str, sb2);
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isMobileDataTileBlocked()) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        if (!dataUsageController.isMobileDataSupported()) {
            showPopupDialog(this.mContext.getString(R.string.insert_sim_card), this.mContext.getString(R.string.insert_sim_card_message), R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 4), 0, null, null);
            return;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming$1() && ((edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor) == null || !edmMonitor.mRoamingAllowed)) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z2 = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z2 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled() && ((z = ((QSTile.BooleanState) this.mState).value) || (!z && Operator.isKoreaQsTileBranding() && this.mSettingsHelper.isMobileDataConnectionPopupShowing()))) {
            if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileDataTile.this.handleClick(expandable);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "MOBILEDATA_STATE_CHANGE");
                return;
            }
        }
        Log.d(str, "isKeyguardVisible() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + this.mSettingsHelper.isLockFunctionsEnabled());
        Intent intent = null;
        if (!Operator.isKoreaQsTileBranding()) {
            boolean isUSAQsTileBranding = Operator.isUSAQsTileBranding();
            int i = R.string.mobile_data_show_popup_disable_jpn;
            int i2 = R.string.mobile_data_show_popup_disable;
            if (isUSAQsTileBranding && dataUsageController.isMobileDataEnabled()) {
                boolean z3 = Settings.System.getInt(this.mContext.getContentResolver(), "mobile_data_off_popup_show_again", 0) != 0;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("handleClick : doNotShowAgainChecked :  ", str, z3);
                if (z3) {
                    dataUsageController.setMobileDataEnabled(false);
                    refreshState(null);
                } else {
                    if (!Operator.isUSAQsTileBranding()) {
                        if (Operator.QUICK_IS_DCM_BRANDING) {
                            i = R.string.mobile_data_show_popup_disable_dcm;
                        }
                        i2 = i;
                    } else if (Operator.QUICK_IS_ATT_BRANDING) {
                        i2 = R.string.mobile_data_show_popup_disable_att;
                    }
                    LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
                    if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                        View inflate = layoutInflater.inflate(R.layout.sec_mobile_data_dont_show_layout, (ViewGroup) null);
                        TextView textView2 = (TextView) inflate.findViewById(R.id.mobile_data_message_text);
                        Typeface create = Typeface.create(Typeface.create("sec", 0), 400, false);
                        textView2.setTypeface(create);
                        checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
                        checkBox.setTypeface(create);
                        view = inflate;
                        textView = textView2;
                    } else {
                        View inflate2 = layoutInflater.inflate(R.layout.subscreen_mobile_data_dont_show_layout, (ViewGroup) null);
                        textView = (TextView) inflate2.findViewById(R.id.mobile_data_message_text);
                        if (textView != null) {
                            FontSizeUtils.updateFontSize(textView, R.dimen.subscreen_dialog_text_size, 0.9f, 1.3f);
                        }
                        checkBox = (CheckBox) inflate2.findViewById(R.id.do_not_show_again);
                        if (checkBox != null) {
                            FontSizeUtils.updateFontSize(checkBox, R.dimen.subscreen_dialog_text_size, 0.9f, 1.3f);
                        }
                        view = inflate2;
                    }
                    textView.setText(this.mContext.getString(i2));
                    checkBox.setOnClickListener(new MobileDataTile$$ExternalSyntheticLambda8());
                    showPopupDialog(this.mContext.getString(R.string.mobile_data_turn_off_title), null, R.string.sec_data_usage_disabled_dialog_turn_off, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda9
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            MobileDataTile mobileDataTile = MobileDataTile.this;
                            Settings.System.putInt(mobileDataTile.mContext.getContentResolver(), "mobile_data_off_popup_show_again", checkBox.isChecked() ? 1 : 0);
                            mobileDataTile.mDataController.setMobileDataEnabled(false);
                            mobileDataTile.refreshState(null);
                        }
                    }, R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 1), view);
                }
            } else {
                boolean z4 = Operator.QUICK_IS_DCM_BRANDING;
                if ((z4 || Operator.QUICK_IS_KDI_BRANDING || Operator.QUICK_IS_SBM_BRANDING || Operator.QUICK_IS_XJP_BRANDING || Operator.QUICK_IS_RKT_BRANDING) && dataUsageController.isMobileDataEnabled()) {
                    if (!Operator.isUSAQsTileBranding()) {
                        if (z4) {
                            i = R.string.mobile_data_show_popup_disable_dcm;
                        }
                        i2 = i;
                    } else if (Operator.QUICK_IS_ATT_BRANDING) {
                        i2 = R.string.mobile_data_show_popup_disable_att;
                    }
                    showPopupDialog(this.mContext.getString(R.string.mobile_data_title), this.mContext.getString(i2), R.string.sec_data_usage_disabled_dialog_turn_off, new MobileDataTile$$ExternalSyntheticLambda0(this, 2), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 5), null);
                } else {
                    dataUsageController.setMobileDataEnabled(!((QSTile.BooleanState) this.mState).value);
                }
            }
        } else if (isNetworkRoaming$1()) {
            boolean z5 = !(Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_DATA_ROAMING, 0) == 1);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setDataRoaming ", str, z5);
            if (z5) {
                this.mPanelInteractor.collapsePanels();
                this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.app.telephonyui.internal"), "roaming_data_popup", (String) null, (Bundle) null);
            } else {
                ((TelephonyManager) this.mContext.getSystemService("phone")).setDataRoamingEnabled(z5);
                if (Operator.QUICK_IS_LGT_BRANDING) {
                    intent = new Intent("kr.co.uplus.RESTRICT_BACKGROUND");
                    intent.putExtra("ENABLED", z5);
                }
                if (intent != null) {
                    this.mContext.sendBroadcast(intent);
                    SysUIToast.makeText(getContext$2(), R.string.mobile_data_roaming_disable_toast_lgt, 0).show();
                }
            }
        } else if (dataUsageController.isMobileDataEnabled()) {
            showPopupDialog(this.mContext.getString(R.string.mobile_data_title), this.mContext.getString(Operator.getMessageIdForMobileDataOnOffPopup(dataUsageController.isMobileDataEnabled())), R.string.sec_data_usage_disabled_dialog_turn_off, new MobileDataTile$$ExternalSyntheticLambda0(this, 6), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 7), null);
        } else {
            showPopupDialog(this.mContext.getString(R.string.mobile_data_title), this.mContext.getString(Operator.getMessageIdForMobileDataOnOffPopup(dataUsageController.isMobileDataEnabled())), R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 8), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 9), null);
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_MOBILEDATA_COVER);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenMobileDataTileReceiver subscreenMobileDataTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        this.mCallAttributesListener = null;
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (subscreenMobileDataTileReceiver = this.mSubscreenMobileDataTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenMobileDataTileReceiver);
        this.mSubscreenMobileDataTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        EdmMonitor edmMonitor;
        if (this.mIsSatelliteModeOn) {
            return;
        }
        if (getValue() == 1) {
            SysUIToast.makeText(getContext$2(), R.string.mobile_data_show_toast_airplane_mode, 0).show();
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isMobileDataTileBlocked() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isUserMobileDataRestricted()) {
            showItPolicyToast();
            return;
        }
        if (!this.mDataController.isMobileDataSupported()) {
            showPopupDialog(this.mContext.getString(R.string.insert_sim_card), this.mContext.getString(R.string.insert_sim_card_message), R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 3), 0, null, null);
            return;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming$1() && ((edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor) == null || !edmMonitor.mRoamingAllowed)) {
            showItPolicyToast();
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        TelephonyManager telephonyManager;
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        AnonymousClass4 anonymousClass4 = this.mPhoneStateListener;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        String str = this.TAG;
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        NetworkController networkController = this.mController;
        if (z) {
            ((NetworkControllerImpl) networkController).addCallback(this);
            satelliteModeObserverHelper.addCallback(anonymousClass1);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
            if (Operator.isKoreaQsTileBranding() || Operator.shouldSupportMobileDataNotDisableVolteCall()) {
                if (telephonyListenerManager != null) {
                    telephonyListenerManager.addActiveDataSubscriptionIdListener(anonymousClass4);
                }
                Log.d(str, "registerPhoneStateListener");
                if (this.mTelephonyManager == null) {
                    this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
                }
                TelephonyManager telephonyManager2 = this.mTelephonyManager;
                if (telephonyManager2 != null) {
                    telephonyManager2.registerTelephonyCallback(new HandlerExecutor(((SQSTileImpl) this).mHandler), this.mCallAttributesListener);
                }
                if (Operator.isKoreaQsTileBranding()) {
                    this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_DATA_ROAMING), false, this.mDataRoamingObserver);
                }
            }
            broadcastDispatcher.registerReceiver(intentFilter, this.mReceiver);
        } else {
            satelliteModeObserverHelper.removeCallback(anonymousClass1);
            ((NetworkControllerImpl) networkController).removeCallback(this);
            if (Operator.isKoreaQsTileBranding() || Operator.shouldSupportMobileDataNotDisableVolteCall()) {
                if (telephonyListenerManager != null) {
                    ((ArrayList) telephonyListenerManager.mTelephonyCallback.mActiveDataSubscriptionIdListeners).remove(anonymousClass4);
                    telephonyListenerManager.updateListening();
                }
                Log.d(str, "unregisterPhoneStateListener");
                CallAttributesListener callAttributesListener = this.mCallAttributesListener;
                if (callAttributesListener != null && (telephonyManager = this.mTelephonyManager) != null) {
                    telephonyManager.unregisterTelephonyCallback(callAttributesListener);
                }
                if (Operator.isKoreaQsTileBranding()) {
                    this.mContext.getContentResolver().unregisterContentObserver(this.mDataRoamingObserver);
                }
            }
            broadcastDispatcher.unregisterReceiver(this.mReceiver);
        }
        AnonymousClass2 anonymousClass2 = this.mSetting;
        if (z) {
            anonymousClass2.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(anonymousClass2.mSettingName), false, anonymousClass2);
        } else {
            anonymousClass2.mContext.getContentResolver().unregisterContentObserver(anonymousClass2);
        }
        AnonymousClass3 anonymousClass3 = this.mAirplaneSetting;
        if (z) {
            anonymousClass3.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(anonymousClass3.mSettingName), false, anonymousClass3);
        } else {
            anonymousClass3.mContext.getContentResolver().unregisterContentObserver(anonymousClass3);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_mobile_networks");
        Resources resources = this.mContext.getResources();
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_data_connection);
        boolean isNetworkRoaming$1 = isNetworkRoaming$1();
        AnonymousClass3 anonymousClass3 = this.mAirplaneSetting;
        if (isNetworkRoaming$1) {
            booleanState.label = resources.getString(R.string.quick_settings_data_roaming_label);
            booleanState.value = anonymousClass3.getValue() != 1 && Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_DATA_ROAMING, 0) == 1;
        } else {
            booleanState.label = resources.getString(R.string.quick_settings_mobile_data_label);
            DataUsageController dataUsageController = this.mDataController;
            booleanState.value = dataUsageController.isMobileDataSupported() && dataUsageController.isMobileDataEnabled() && anonymousClass3.getValue() != 1;
        }
        Log.d(this.TAG, "handleUpdateState : state " + booleanState.value);
        if (this.mIsSatelliteModeOn) {
            booleanState.state = 0;
        } else {
            booleanState.state = booleanState.value ? 2 : 1;
        }
        booleanState.dualTarget = true;
        if (Operator.shouldSupportMobileDataNotDisableVolteCall() && this.mIsVoLteCall) {
            booleanState.state = 0;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (!DeviceType.isWiFiOnlyDevice() && ((UserTrackerImpl) this.mUserTracker).getUserId() == 0) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isNetworkRoaming$1() {
        if (!Operator.isKoreaQsTileBranding()) {
            return false;
        }
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(phoneId, "getDefaultDataPhoneId ");
            String str = this.TAG;
            Log.d(str, m);
            if (phoneId < 0) {
                phoneId = 0;
            } else if (phoneId > 1) {
                phoneId = 1;
            }
            ServiceState semGetServiceState = telephonyManager.semGetServiceState(phoneId);
            r1 = semGetServiceState != null ? semGetServiceState.getRoaming() : false;
            if (r1) {
                Log.d(str, "isNetworkRoaming : Roaming state");
            }
        }
        return r1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setMobileDataEnabled(boolean z) {
        Log.d(this.TAG, "setMobileDataEnabled:" + z);
        refreshState(null);
    }

    public final void showPopupDialog(final CharSequence charSequence, final CharSequence charSequence2, final int i, final DialogInterface.OnClickListener onClickListener, final int i2, final DialogInterface.OnClickListener onClickListener2, final View view) {
        SystemUIDialog systemUIDialog;
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
            if (displayLifecycle != null && !displayLifecycle.mIsFolderOpened) {
                SubscreenUtil subscreenUtil = this.mSubscreenUtil;
                if (subscreenUtil != null) {
                    subscreenUtil.closeSubscreenPanel();
                }
                this.mUiHandler.post(new MobileDataTile$$ExternalSyntheticLambda13());
                new Handler().postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileDataTile mobileDataTile = MobileDataTile.this;
                        CharSequence charSequence3 = charSequence;
                        CharSequence charSequence4 = charSequence2;
                        int i3 = i;
                        DialogInterface.OnClickListener onClickListener3 = onClickListener;
                        int i4 = i2;
                        DialogInterface.OnClickListener onClickListener4 = onClickListener2;
                        View view2 = view;
                        SystemUIDialog createSystemUIDialogUtils = SystemUIDialogUtils.createSystemUIDialogUtils(mobileDataTile.getContext$2(), R.style.Theme_SystemUI_Dialog_Alert);
                        createSystemUIDialogUtils.setTitle(charSequence3);
                        createSystemUIDialogUtils.setMessage(charSequence4);
                        if (view2 != null) {
                            Resources resources = mobileDataTile.getContext$2().getResources();
                            createSystemUIDialogUtils.setView(view2, resources.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0);
                        }
                        if (i3 != 0 && onClickListener3 != null) {
                            createSystemUIDialogUtils.setPositiveButton(i3, onClickListener3);
                        }
                        if (i4 != 0 && onClickListener4 != null) {
                            createSystemUIDialogUtils.setNegativeButton(i4, onClickListener4);
                        }
                        mobileDataTile.mPanelInteractor.collapsePanels();
                        createSystemUIDialogUtils.setOnDismissListener(new MobileDataTile$$ExternalSyntheticLambda15(mobileDataTile, 1));
                        createSystemUIDialogUtils.show();
                    }
                }, 250L);
                return;
            }
            systemUIDialog = SystemUIDialogUtils.createSystemUIDialogUtils(getContext$2(), R.style.Theme_SystemUI_Dialog_Alert);
        } else {
            systemUIDialog = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
        }
        systemUIDialog.setTitle(charSequence);
        systemUIDialog.setMessage(charSequence2);
        if (view != null) {
            Resources resources = this.mContext.getResources();
            systemUIDialog.setView(view, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0);
        }
        if (i != 0) {
            systemUIDialog.setPositiveButton(i, onClickListener);
        }
        if (i2 != 0 && onClickListener2 != null) {
            systemUIDialog.setNegativeButton(i2, onClickListener2);
        }
        this.mPanelInteractor.collapsePanels();
        systemUIDialog.setOnDismissListener(new MobileDataTile$$ExternalSyntheticLambda15(this, 0));
        systemUIDialog.show();
    }
}
