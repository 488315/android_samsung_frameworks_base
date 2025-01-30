package com.android.systemui.p016qs.tiles;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.p016qs.tiles.SyncTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SyncTile extends SQSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SystemUIDialog mAlertDialog;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final SyncDetailAdapter mDetailAdapter;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PanelInteractor mPanelInteractor;
    public final C22961 mReceiver;
    public final SettingsHelper mSettingsHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SyncDetailAdapter implements DetailAdapter {
        public TextView mSummary;

        public /* synthetic */ SyncDetailAdapter(SyncTile syncTile, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            int i = SyncTile.$r8$clinit;
            SyncTile syncTile = SyncTile.this;
            if (syncTile.hasUserRestriction()) {
                return null;
            }
            View inflate = LayoutInflater.from(syncTile.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            TextView textView = (TextView) inflate.findViewById(R.id.message);
            this.mSummary = textView;
            textView.setText(getToggleState().booleanValue() ? R.string.data_usage_auto_sync_on_dialog : R.string.data_usage_auto_sync_off_dialog);
            return inflate;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final int getMetricsCategory() {
            return 5008;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return SyncTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = SyncTile.$r8$clinit;
            return SyncTile.this.mContext.getString(R.string.quick_settings_sync_label);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final boolean getToggleEnabled() {
            int i = SyncTile.$r8$clinit;
            return ((QSTile.BooleanState) SyncTile.this.mState).state != 0;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = SyncTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) SyncTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final void setToggleState(boolean z) {
            int i = SyncTile.$r8$clinit;
            SyncTile syncTile = SyncTile.this;
            if (syncTile.isBlockedEdmSettingsChange$1()) {
                syncTile.fireToggleStateChanged(getToggleState().booleanValue());
                syncTile.showItPolicyToast();
                return;
            }
            KeyguardStateController keyguardStateController = syncTile.mKeyguardStateController;
            boolean z2 = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
            SettingsHelper settingsHelper = syncTile.mSettingsHelper;
            KeyguardUpdateMonitor keyguardUpdateMonitor = syncTile.mKeyguardUpdateMonitor;
            if (z2 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled() && !((QSTile.BooleanState) syncTile.mState).value) {
                syncTile.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SyncTile$SyncDetailAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SyncTile.SyncDetailAdapter.this.setToggleState(!r1.getToggleState().booleanValue());
                    }
                });
                syncTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            Log.d(syncTile.TAG, "isKeyguardVisible() = " + ((KeyguardStateControllerImpl) keyguardStateController).mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
            ContentResolver.setMasterSyncAutomaticallyAsUser(z, ActivityManager.getCurrentUser());
            syncTile.handleRefreshState(SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
            syncTile.fireToggleStateChanged(z);
            this.mSummary.setText(z ? R.string.data_usage_auto_sync_on_dialog : R.string.data_usage_auto_sync_off_dialog);
        }

        private SyncDetailAdapter() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.systemui.qs.tiles.SyncTile$1] */
    public SyncTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.SyncTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                SyncTile syncTile = SyncTile.this;
                int i = SyncTile.$r8$clinit;
                AbstractC0000x2c234b15.m3m("action:", action, syncTile.TAG);
                if (action.equals(ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED.getAction())) {
                    Log.d(SyncTile.this.TAG, "ACTION_SYNC_CONN_STATUS_CHANGED");
                    SyncTile.this.refreshState(null);
                }
                if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                    SyncTile.this.refreshState(null);
                }
                Log.w(SyncTile.this.TAG, "Cannot handle received broadcast: " + intent.getAction());
            }
        };
        this.mReceiver = r1;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mDetailAdapter = new SyncDetailAdapter(this, 0);
        this.mSettingsHelper = settingsHelper;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED.getAction());
        intentFilter.addAction("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS");
        broadcastDispatcher.registerReceiver(intentFilter, r1);
        this.mPanelInteractor = panelInteractor;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (hasUserRestriction()) {
            return null;
        }
        return new Intent("android.settings.SYNC_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 5007;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_sync_label);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        String str = "handleClick : " + ((QSTile.BooleanState) this.mState).value;
        String str2 = this.TAG;
        Log.d(str2, str);
        if (hasUserRestriction()) {
            return;
        }
        if (isBlockedEdmSettingsChange$1()) {
            showItPolicyToast();
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled() && !((QSTile.BooleanState) this.mState).value) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SyncTile.this.handleClick(view);
                }
            });
            return;
        }
        Log.d(str2, "isKeyguardVisible() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
        final boolean masterSyncAutomaticallyAsUser = ContentResolver.getMasterSyncAutomaticallyAsUser(ActivityManager.getCurrentUser()) ^ true;
        SystemUIDialog systemUIDialog = this.mAlertDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            SystemUIDialog systemUIDialog2 = new SystemUIDialog(this.mContext, 2132018528);
            this.mAlertDialog = systemUIDialog2;
            systemUIDialog2.setTitle(masterSyncAutomaticallyAsUser ? R.string.data_usage_auto_sync_on_dialog_title : R.string.data_usage_auto_sync_off_dialog_title);
            this.mAlertDialog.setMessage(masterSyncAutomaticallyAsUser ? R.string.data_usage_auto_sync_on_dialog : R.string.data_usage_auto_sync_off_dialog);
            this.mAlertDialog.setPositiveButton(R.string.f789ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SyncTile syncTile = SyncTile.this;
                    boolean z2 = masterSyncAutomaticallyAsUser;
                    syncTile.getClass();
                    ContentResolver.setMasterSyncAutomaticallyAsUser(z2, ActivityManager.getCurrentUser());
                    syncTile.refreshState(null);
                }
            });
            this.mAlertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SyncTile.this.mAlertDialog.dismiss();
                }
            });
            this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SyncTile.this.refreshState(null);
                }
            });
            SystemUIDialog.setWindowOnTop(this.mAlertDialog, keyguardStateControllerImpl.mShowing);
            this.mPanelInteractor.collapsePanels();
            this.mAlertDialog.show();
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
        SystemUIDialog systemUIDialog = this.mAlertDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            return;
        }
        Log.d(this.TAG, "onDestroy(): dismiss the dialog");
        this.mAlertDialog.dismiss();
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        if (hasUserRestriction()) {
            return;
        }
        if (isBlockedEdmSettingsChange$1()) {
            showItPolicyToast();
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.value = ContentResolver.getMasterSyncAutomaticallyAsUser(ActivityManager.getCurrentUser());
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_sync_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_sync);
        if (hasUserRestriction()) {
            booleanState.state = 0;
        } else {
            booleanState.state = booleanState.value ? 2 : 1;
        }
    }

    public final boolean hasUserRestriction() {
        boolean hasBaseUserRestriction = RestrictedLockUtilsInternal.hasBaseUserRestriction(this.mContext, "no_modify_accounts", UserHandle.myUserId());
        Log.d(this.TAG, AbstractC0866xb1ce8deb.m86m("hasUserRestriction: ", hasBaseUserRestriction));
        return hasBaseUserRestriction;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        if (!SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigRemoveQuickSettingItem", "").contains("Sync")) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBlockedEdmSettingsChange$1() {
        String[] strArr = {"false"};
        Context context = this.mContext;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int enterprisePolicyEnabled = Utils.getEnterprisePolicyEnabled(context, "content://com.sec.knox.provider/RestrictionPolicy3", "isSettingsChangesAllowed", strArr);
        int enterprisePolicyEnabled2 = Utils.getEnterprisePolicyEnabled(context, "content://com.sec.knox.provider/RoamingPolicy", "isRoamingSyncEnabled", strArr);
        boolean z = true;
        boolean z2 = enterprisePolicyEnabled2 == 0;
        boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
        boolean isSupportESim = DeviceType.isSupportESim();
        boolean contains = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EMBEDDED_SIM_SLOTSWITCH", "").toLowerCase().contains("tsds");
        Log.d("DeviceType", "supportESIMSlot: " + isSupportESim + " supportSwitingESIM: " + contains);
        if (DeviceType.isMultiSimSupported() && (!isSupportESim || contains)) {
            String semGetTelephonyProperty = TelephonyManager.semGetTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false");
            isNetworkRoaming = !TextUtils.isEmpty(semGetTelephonyProperty) && semGetTelephonyProperty.equals("true");
        }
        if (enterprisePolicyEnabled != 0 && (!z2 || !isNetworkRoaming)) {
            z = false;
        }
        Log.d(this.TAG, AbstractC0866xb1ce8deb.m86m("isBlockedEdmSettingsChange: ", z));
        return z;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
