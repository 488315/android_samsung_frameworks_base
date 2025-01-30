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
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.GlobalSetting;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
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
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileDataTile extends SQSTileImpl implements SignalCallback {
    public static final Intent DATA_SETTINGS = new Intent().setAction("android.settings.DATA_USAGE_SETTINGS");
    public static final Intent DATA_SETTINGS_UPSM = new Intent().setAction("com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS");
    public final ActivityStarter mActivityStarter;
    public final C22562 mAirplaneSetting;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public CallAttributesListener mCallAttributesListener;
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final C22595 mDataRoamingObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mIsVoLteCall;
    public boolean mIsVolteVideoCall;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final C22573 mPhoneStateListener;
    public final C22584 mReceiver;
    public final C22551 mSetting;
    public final SettingsHelper mSettingsHelper;
    public final Context mSubscreenContext;
    public SubscreenMobileDataTileReceiver mSubscreenMobileDataTileReceiver;
    public final SubscreenUtil mSubscreenUtil;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public TelephonyManager mTelephonyManager;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, MobileDataTile.this.mIsVolteVideoCall, str);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.tiles.MobileDataTile$3] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.MobileDataTile$4] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.qs.tiles.MobileDataTile$5] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.tiles.MobileDataTile$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.qs.tiles.MobileDataTile$2] */
    public MobileDataTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, BroadcastDispatcher broadcastDispatcher, PanelInteractor panelInteractor, UserTracker userTracker, DisplayLifecycle displayLifecycle, SubscreenUtil subscreenUtil) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile.3
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onActiveDataSubscriptionIdChanged,subId = ", i, mobileDataTile.TAG);
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.MobileDataTile.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent2 = MobileDataTile.DATA_SETTINGS;
                Log.d(mobileDataTile.TAG, "action:" + action);
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    intent.getIntExtra("displayId", -1);
                    return;
                }
                if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra(ImsProfile.SERVICE_SS);
                    if ("READY".equals(stringExtra) || "LOADED".equals(stringExtra) || "ABSENT".equals(stringExtra)) {
                        MobileDataTile.this.refreshState(null);
                    }
                }
            }
        };
        this.mDataRoamingObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.qs.tiles.MobileDataTile.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mSetting = new GlobalSetting(this.mContext, ((SQSTileImpl) this).mHandler, "mobile_data") { // from class: com.android.systemui.qs.tiles.MobileDataTile.1
            @Override // com.android.systemui.qs.GlobalSetting
            public final void handleValueChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                String str = mobileDataTile.TAG;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("mobile data has changed value : ", i, " is enabled : ");
                m1m.append(MobileDataTile.this.mDataController.isMobileDataEnabled());
                Log.d(str, m1m.toString());
                MobileDataTile.this.refreshState(null);
            }
        };
        this.mAirplaneSetting = new GlobalSetting(this.mContext, ((SQSTileImpl) this).mHandler, "airplane_mode_on") { // from class: com.android.systemui.qs.tiles.MobileDataTile.2
            @Override // com.android.systemui.qs.GlobalSetting
            public final void handleValueChanged(int i) {
                MobileDataTile mobileDataTile = MobileDataTile.this;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("airplane mode  has changed value : ", i, mobileDataTile.TAG);
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
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenUtil = subscreenUtil;
            ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getClass();
            this.mSubscreenContext = SubscreenQsPanelController.mContext;
            if (this.mSubscreenMobileDataTileReceiver != null || broadcastDispatcher == null) {
                return;
            }
            SubscreenMobileDataTileReceiver subscreenMobileDataTileReceiver = new SubscreenMobileDataTileReceiver();
            this.mSubscreenMobileDataTileReceiver = subscreenMobileDataTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenMobileDataTileReceiver, new IntentFilter("MOBILEDATA_STATE_CHANGE"), null, UserHandle.ALL, 2, null);
        }
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
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isMobileDataTileBlocked() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isUserMobileDataRestricted()) {
            showItPolicyToast();
            return null;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming()) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            if (!(edmMonitor != null && edmMonitor.mRoamingAllowed)) {
                showItPolicyToast();
                return null;
            }
        }
        Context context = this.mContext;
        if (SemEmergencyManager.isEmergencyMode(context)) {
            if (this.mDataController.isMobileDataSupported()) {
                return DATA_SETTINGS_UPSM;
            }
            showPopupDialog(context.getString(R.string.insert_sim_card), context.getString(R.string.insert_sim_card_message), android.R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 9), 0, null, null);
            return null;
        }
        if (!isNetworkRoaming()) {
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_mobile_data_label);
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x011c, code lost:
    
        if ((r9.mItemLists.get("mobile_data_question").getIntValue() == 1) != false) goto L64;
     */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleClick(final View view) {
        final CheckBox checkBox;
        View view2;
        TextView textView;
        if (Operator.shouldSupportMobileDataNotDisableVolteCall() && (this.mIsVoLteCall || this.mIsVolteVideoCall)) {
            return;
        }
        int value = getValue();
        Context context = this.mContext;
        if (value == 1) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                context = getContext();
            }
            SysUIToast.makeText(R.string.mobile_data_show_toast_airplane_mode, context, 0).show();
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
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isMobileDataTileBlocked()) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        if (!dataUsageController.isMobileDataSupported()) {
            showPopupDialog(context.getString(R.string.insert_sim_card), context.getString(R.string.insert_sim_card_message), android.R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 1), 0, null, null);
            return;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming()) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            if (!(edmMonitor != null && edmMonitor.mRoamingAllowed)) {
                if (QpRune.QUICK_PANEL_SUBSCREEN) {
                    showItPolicyToastOnSubScreen(getSubScreenContext());
                    return;
                } else {
                    showItPolicyToast();
                    return;
                }
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled()) {
            boolean z2 = ((QSTile.BooleanState) this.mState).value;
            if (!z2) {
                if (!z2 && Operator.isKoreaQsTileBranding()) {
                }
            }
            if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileDataTile.this.handleClick(view);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).showLockscreenOnCoverScreen(context, "MOBILEDATA_STATE_CHANGE");
                return;
            }
        }
        Log.d(str, "isKeyguardVisible() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
        Intent intent = null;
        if (Operator.isKoreaQsTileBranding()) {
            if (isNetworkRoaming()) {
                boolean z3 = !(Settings.Global.getInt(context.getContentResolver(), "data_roaming", 0) == 1);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setDataRoaming ", z3, str);
                if (z3) {
                    this.mPanelInteractor.collapsePanels();
                    context.getContentResolver().call(Uri.parse("content://com.samsung.android.app.telephonyui.internal"), "roaming_data_popup", (String) null, (Bundle) null);
                } else {
                    ((TelephonyManager) context.getSystemService("phone")).setDataRoamingEnabled(z3);
                    if (Operator.QUICK_IS_LGT_BRANDING) {
                        intent = new Intent("kr.co.uplus.RESTRICT_BACKGROUND");
                        intent.putExtra("ENABLED", z3);
                    }
                    if (intent != null) {
                        context.sendBroadcast(intent);
                        if (QpRune.QUICK_PANEL_SUBSCREEN) {
                            context = getContext();
                        }
                        SysUIToast.makeText(R.string.mobile_data_roaming_disable_toast_lgt, context, 0).show();
                    }
                }
            } else if (dataUsageController.isMobileDataEnabled()) {
                showPopupDialog(context.getString(R.string.mobile_data_title), context.getString(Operator.getMessageIdForMobileDataOnOffPopup(dataUsageController.isMobileDataEnabled())), R.string.sec_data_usage_disabled_dialog_turn_off, new MobileDataTile$$ExternalSyntheticLambda0(this, 3), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 4), null);
            } else {
                showPopupDialog(context.getString(R.string.mobile_data_title), context.getString(Operator.getMessageIdForMobileDataOnOffPopup(dataUsageController.isMobileDataEnabled())), android.R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 5), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 6), null);
            }
        } else if (Operator.isUSAQsTileBranding() && dataUsageController.isMobileDataEnabled()) {
            boolean z4 = Settings.System.getInt(context.getContentResolver(), "mobile_data_off_popup_show_again", 0) != 0;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleClick : doNotShowAgainChecked :  ", z4, str);
            if (z4) {
                dataUsageController.setMobileDataEnabled(false);
                refreshState(null);
            } else {
                int i = Operator.QUICK_IS_ATT_BRANDING ? R.string.mobile_data_turn_off_title_att : R.string.mobile_data_turn_off_title;
                int messageIdMobileDataOff = Operator.getMessageIdMobileDataOff();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
                if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                    View inflate = layoutInflater.inflate(R.layout.sec_mobile_data_dont_show_layout, (ViewGroup) null);
                    TextView textView2 = (TextView) inflate.findViewById(R.id.mobile_data_message_text);
                    Typeface create = Typeface.create(Typeface.create("sec", 0), 400, false);
                    textView2.setTypeface(create);
                    checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
                    checkBox.setTypeface(create);
                    view2 = inflate;
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
                    view2 = inflate2;
                }
                textView.setText(context.getString(messageIdMobileDataOff));
                checkBox.setOnClickListener(new MobileDataTile$$ExternalSyntheticLambda2());
                showPopupDialog(context.getString(i), null, R.string.sec_data_usage_disabled_dialog_turn_off, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        MobileDataTile mobileDataTile = MobileDataTile.this;
                        Settings.System.putInt(mobileDataTile.mContext.getContentResolver(), "mobile_data_off_popup_show_again", checkBox.isChecked() ? 1 : 0);
                        mobileDataTile.mDataController.setMobileDataEnabled(false);
                        mobileDataTile.refreshState(null);
                    }
                }, R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 7), view2);
            }
        } else {
            if ((Operator.QUICK_IS_DCM_BRANDING || Operator.QUICK_IS_KDI_BRANDING || Operator.QUICK_IS_SBM_BRANDING || Operator.QUICK_IS_XJP_BRANDING || Operator.QUICK_IS_RKT_BRANDING) && dataUsageController.isMobileDataEnabled()) {
                showPopupDialog(context.getString(R.string.mobile_data_title), context.getString(Operator.getMessageIdMobileDataOff()), R.string.sec_data_usage_disabled_dialog_turn_off, new MobileDataTile$$ExternalSyntheticLambda0(this, 8), R.string.quick_settings_cancel, new MobileDataTile$$ExternalSyntheticLambda0(this, 2), null);
            } else {
                dataUsageController.setMobileDataEnabled(!((QSTile.BooleanState) this.mState).value);
            }
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2020");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenMobileDataTileReceiver subscreenMobileDataTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        this.mCallAttributesListener = null;
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (subscreenMobileDataTileReceiver = this.mSubscreenMobileDataTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenMobileDataTileReceiver);
        this.mSubscreenMobileDataTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        int value = getValue();
        boolean z = false;
        Context context = this.mContext;
        if (value == 1) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                context = getContext();
            }
            SysUIToast.makeText(R.string.mobile_data_show_toast_airplane_mode, context, 0).show();
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isMobileDataTileBlocked() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isUserMobileDataRestricted()) {
            showItPolicyToast();
            return;
        }
        if (!this.mDataController.isMobileDataSupported()) {
            showPopupDialog(context.getString(R.string.insert_sim_card), context.getString(R.string.insert_sim_card_message), android.R.string.ok, new MobileDataTile$$ExternalSyntheticLambda0(this, 0), 0, null, null);
            return;
        }
        if (Operator.isKoreaQsTileBranding() && isNetworkRoaming()) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            if (edmMonitor != null && edmMonitor.mRoamingAllowed) {
                z = true;
            }
            if (!z) {
                showItPolicyToast();
                return;
            }
        }
        showDetail(true);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        TelephonyManager telephonyManager;
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        C22595 c22595 = this.mDataRoamingObserver;
        C22584 c22584 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        C22573 c22573 = this.mPhoneStateListener;
        String str = this.TAG;
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        NetworkController networkController = this.mController;
        Context context = this.mContext;
        if (z) {
            ((NetworkControllerImpl) networkController).addCallback(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
            if (Operator.isKoreaQsTileBranding() || Operator.shouldSupportMobileDataNotDisableVolteCall()) {
                if (telephonyListenerManager != null) {
                    telephonyListenerManager.addActiveDataSubscriptionIdListener(c22573);
                }
                Log.d(str, "registerPhoneStateListener");
                if (this.mTelephonyManager == null) {
                    this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
                }
                TelephonyManager telephonyManager2 = this.mTelephonyManager;
                if (telephonyManager2 != null) {
                    telephonyManager2.registerTelephonyCallback(new HandlerExecutor(((SQSTileImpl) this).mHandler), this.mCallAttributesListener);
                }
                if (Operator.isKoreaQsTileBranding()) {
                    context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("data_roaming"), false, c22595);
                }
            }
            broadcastDispatcher.registerReceiver(intentFilter, c22584);
        } else {
            ((NetworkControllerImpl) networkController).removeCallback(this);
            if (Operator.isKoreaQsTileBranding() || Operator.shouldSupportMobileDataNotDisableVolteCall()) {
                if (telephonyListenerManager != null) {
                    ((ArrayList) telephonyListenerManager.mTelephonyCallback.mActiveDataSubscriptionIdListeners).remove(c22573);
                    telephonyListenerManager.updateListening();
                }
                Log.d(str, "unregisterPhoneStateListener");
                CallAttributesListener callAttributesListener = this.mCallAttributesListener;
                if (callAttributesListener != null && (telephonyManager = this.mTelephonyManager) != null) {
                    telephonyManager.unregisterTelephonyCallback(callAttributesListener);
                }
                if (Operator.isKoreaQsTileBranding()) {
                    context.getContentResolver().unregisterContentObserver(c22595);
                }
            }
            broadcastDispatcher.unregisterReceiver(c22584);
        }
        setListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_mobile_networks");
        Context context = this.mContext;
        Resources resources = context.getResources();
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_data_connection);
        boolean isNetworkRoaming = isNetworkRoaming();
        C22562 c22562 = this.mAirplaneSetting;
        if (isNetworkRoaming) {
            booleanState.label = resources.getString(R.string.quick_settings_data_roaming_label);
            if (c22562.getValue() != 1) {
                if (Settings.Global.getInt(context.getContentResolver(), "data_roaming", 0) == 1) {
                    z = true;
                    booleanState.value = z;
                }
            }
            z = false;
            booleanState.value = z;
        } else {
            booleanState.label = resources.getString(R.string.quick_settings_mobile_data_label);
            DataUsageController dataUsageController = this.mDataController;
            booleanState.value = dataUsageController.isMobileDataSupported() && dataUsageController.isMobileDataEnabled() && c22562.getValue() != 1;
        }
        Log.d(this.TAG, "handleUpdateState : state " + booleanState.value);
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.dualTarget = true;
        if (Operator.shouldSupportMobileDataNotDisableVolteCall() && this.mIsVoLteCall) {
            booleanState.state = 0;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (!DeviceType.isWiFiOnlyDevice() && ((UserTrackerImpl) this.mUserTracker).getUserId() == 0) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isNetworkRoaming() {
        if (!Operator.isKoreaQsTileBranding()) {
            return false;
        }
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            String m0m = AbstractC0000x2c234b15.m0m("getDefaultDataPhoneId ", phoneId);
            String str = this.TAG;
            Log.d(str, m0m);
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

    public final void showPopupDialog(final CharSequence charSequence, final CharSequence charSequence2, final int i, final DialogInterface.OnClickListener onClickListener, final int i2, final MobileDataTile$$ExternalSyntheticLambda0 mobileDataTile$$ExternalSyntheticLambda0, final View view) {
        SystemUIDialog systemUIDialog;
        boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
        Context context = this.mContext;
        if (z) {
            DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
            if (displayLifecycle != null && !displayLifecycle.mIsFolderOpened) {
                SubscreenUtil subscreenUtil = this.mSubscreenUtil;
                if (subscreenUtil != null) {
                    subscreenUtil.closeSubscreenPanel();
                }
                this.mUiHandler.post(new MobileDataTile$$ExternalSyntheticLambda4());
                new Handler().postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileDataTile mobileDataTile = MobileDataTile.this;
                        CharSequence charSequence3 = charSequence;
                        CharSequence charSequence4 = charSequence2;
                        int i3 = i;
                        DialogInterface.OnClickListener onClickListener2 = onClickListener;
                        int i4 = i2;
                        DialogInterface.OnClickListener onClickListener3 = mobileDataTile$$ExternalSyntheticLambda0;
                        View view2 = view;
                        SystemUIDialog createSystemUIDialogUtils = SystemUIDialogUtils.createSystemUIDialogUtils(2132018528, mobileDataTile.getContext());
                        createSystemUIDialogUtils.setTitle(charSequence3);
                        createSystemUIDialogUtils.setMessage(charSequence4);
                        if (view2 != null) {
                            Resources resources = mobileDataTile.getContext().getResources();
                            createSystemUIDialogUtils.setView(view2, resources.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0);
                        }
                        if (i3 != 0 && onClickListener2 != null) {
                            createSystemUIDialogUtils.setPositiveButton(i3, onClickListener2);
                        }
                        if (i4 != 0 && onClickListener3 != null) {
                            createSystemUIDialogUtils.setNegativeButton(i4, onClickListener3);
                        }
                        mobileDataTile.mPanelInteractor.collapsePanels();
                        createSystemUIDialogUtils.setOnDismissListener(new MobileDataTile$$ExternalSyntheticLambda6(mobileDataTile, 1));
                        createSystemUIDialogUtils.show();
                    }
                }, 250L);
                return;
            }
            systemUIDialog = SystemUIDialogUtils.createSystemUIDialogUtils(2132018528, getContext());
        } else {
            systemUIDialog = new SystemUIDialog(context, 2132018528);
        }
        systemUIDialog.setTitle(charSequence);
        systemUIDialog.setMessage(charSequence2);
        if (view != null) {
            Resources resources = context.getResources();
            systemUIDialog.setView(view, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0);
        }
        if (i != 0) {
            systemUIDialog.setPositiveButton(i, onClickListener);
        }
        if (i2 != 0 && mobileDataTile$$ExternalSyntheticLambda0 != null) {
            systemUIDialog.setNegativeButton(i2, mobileDataTile$$ExternalSyntheticLambda0);
        }
        this.mPanelInteractor.collapsePanels();
        systemUIDialog.setOnDismissListener(new MobileDataTile$$ExternalSyntheticLambda6(this, 0));
        systemUIDialog.show();
    }
}
