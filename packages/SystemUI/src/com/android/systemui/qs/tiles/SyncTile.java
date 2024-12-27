package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.SyncTile;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;

public final class SyncTile extends SQSTileImpl {
    public SystemUIDialog mAlertDialog;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final SyncDetailAdapter mDetailAdapter;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass1 mReceiver;
    private final SettingsHelper mSettingsHelper;

    public final class SyncDetailAdapter implements DetailAdapter {
        public TextView mSummary;

        public /* synthetic */ SyncDetailAdapter(SyncTile syncTile, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
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

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 5008;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return SyncTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            return SyncTile.this.mContext.getString(R.string.quick_settings_sync_label);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean getToggleEnabled() {
            return ((QSTile.BooleanState) SyncTile.this.mState).state != 0;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            return Boolean.valueOf(((QSTile.BooleanState) SyncTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            SyncTile syncTile = SyncTile.this;
            if (syncTile.isBlockedEdmSettingsChange$2()) {
                syncTile.fireToggleStateChanged(getToggleState().booleanValue());
                syncTile.showItPolicyToast();
                return;
            }
            boolean z2 = ((KeyguardStateControllerImpl) syncTile.mKeyguardStateController).mShowing;
            KeyguardUpdateMonitor keyguardUpdateMonitor = syncTile.mKeyguardUpdateMonitor;
            if (z2 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && syncTile.mSettingsHelper.isLockFunctionsEnabled() && !((QSTile.BooleanState) syncTile.mState).value) {
                syncTile.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SyncTile$SyncDetailAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SyncTile.SyncDetailAdapter.this.setToggleState(!r1.getToggleState().booleanValue());
                    }
                });
                syncTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            Log.d(syncTile.TAG, "isKeyguardVisible() = " + ((KeyguardStateControllerImpl) syncTile.mKeyguardStateController).mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + syncTile.mSettingsHelper.isLockFunctionsEnabled());
            ContentResolver.setMasterSyncAutomaticallyAsUser(z, ActivityManager.getCurrentUser());
            syncTile.handleRefreshState(SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
            syncTile.fireToggleStateChanged(z);
            this.mSummary.setText(z ? R.string.data_usage_auto_sync_on_dialog : R.string.data_usage_auto_sync_off_dialog);
        }

        private SyncDetailAdapter() {
        }
    }

    public SyncTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.SyncTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("action:", action, SyncTile.this.TAG);
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

    public static int getEnterprisePolicyEnabled(Context context, String str, String str2, String[] strArr) {
        Uri parse = Uri.parse(str);
        int i = -1;
        ?? r7 = -1;
        if (context == null) {
            return -1;
        }
        Cursor query = context.getContentResolver().query(parse, null, str2, strArr, null);
        if (query != null) {
            try {
                query.moveToFirst();
                r7 = query.getString(query.getColumnIndex(str2)).equals("true");
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
            i = r7;
        }
        return i;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (hasUserRestriction()) {
            return null;
        }
        return new Intent("android.settings.SYNC_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5007;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_sync_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        String str = "handleClick : " + ((QSTile.BooleanState) this.mState).value;
        String str2 = this.TAG;
        Log.d(str2, str);
        if (hasUserRestriction()) {
            return;
        }
        if (isBlockedEdmSettingsChange$2()) {
            showItPolicyToast();
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled() && !((QSTile.BooleanState) this.mState).value) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SyncTile.this.handleClick(expandable);
                }
            });
            return;
        }
        Log.d(str2, "isKeyguardVisible() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + this.mSettingsHelper.isLockFunctionsEnabled());
        final boolean masterSyncAutomaticallyAsUser = ContentResolver.getMasterSyncAutomaticallyAsUser(ActivityManager.getCurrentUser()) ^ true;
        SystemUIDialog systemUIDialog = this.mAlertDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            SystemUIDialog systemUIDialog2 = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
            this.mAlertDialog = systemUIDialog2;
            systemUIDialog2.setTitle(masterSyncAutomaticallyAsUser ? R.string.data_usage_auto_sync_on_dialog_title : R.string.data_usage_auto_sync_off_dialog_title);
            this.mAlertDialog.setMessage(masterSyncAutomaticallyAsUser ? R.string.data_usage_auto_sync_on_dialog : R.string.data_usage_auto_sync_off_dialog);
            this.mAlertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.SyncTile$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SyncTile syncTile = SyncTile.this;
                    boolean z2 = masterSyncAutomaticallyAsUser;
                    syncTile.getClass();
                    ContentResolver.setMasterSyncAutomaticallyAsUser(z2, ActivityManager.getCurrentUser());
                    syncTile.refreshState(null);
                    syncTile.mAlertDialog.sendAnnouncementEvent(syncTile.mContext.getString(z2 ? R.string.accessibility_desc_on : R.string.accessibility_desc_off));
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
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

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        if (hasUserRestriction()) {
            return;
        }
        if (isBlockedEdmSettingsChange$2()) {
            showItPolicyToast();
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
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
        Log.d(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("hasUserRestriction: ", hasBaseUserRestriction));
        return hasBaseUserRestriction;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
    }

    public final boolean isBlockedEdmSettingsChange$2() {
        String[] strArr = {"false"};
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        int enterprisePolicyEnabled = getEnterprisePolicyEnabled(this.mContext, "content://com.sec.knox.provider/RestrictionPolicy3", "isSettingsChangesAllowed", strArr);
        boolean z = false;
        boolean z2 = getEnterprisePolicyEnabled(this.mContext, "content://com.sec.knox.provider/RoamingPolicy", "isRoamingSyncEnabled", strArr) == 0;
        boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
        String str = SemSystemProperties.get("persist.radio.multisim.config");
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("isMultiSim : ", str);
        String str2 = this.TAG;
        Log.d(str2, m);
        if ("dsds".equals(str) || "tsts".equals(str) || "dsda".equals(str)) {
            String semGetTelephonyProperty = TelephonyManager.semGetTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false");
            isNetworkRoaming = !TextUtils.isEmpty(semGetTelephonyProperty) && semGetTelephonyProperty.equals("true");
        }
        if (enterprisePolicyEnabled == 0 || (z2 && isNetworkRoaming)) {
            z = true;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isBlockedEdmSettingsChange: ", str2, z);
        return z;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
