package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
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
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UiModeNightTile extends QSTileImpl implements ConfigurationController.ConfigurationListener, BatteryController.BatteryStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UiModeDetailAdapter mDetailAdapter;
    public final QSTile.Icon mIcon;
    public boolean mIsNeedToBlockOnClick;
    public final PanelInteractor mPanelInteractor;
    public Snackbar mSnackbar;
    public final UiModeManager mUiModeManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UiModeDetailAdapter implements DetailAdapter {
        public TextView mDetailSummary;

        public UiModeDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            int i = UiModeNightTile.$r8$clinit;
            View inflate = LayoutInflater.from(UiModeNightTile.this.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            TextView textView = (TextView) inflate.findViewById(R.id.message);
            this.mDetailSummary = textView;
            textView.setText(getDetailSummary$1());
            return inflate;
        }

        /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda1] */
        public final String getDetailSummary$1() {
            String string;
            int i = UiModeNightTile.$r8$clinit;
            UiModeNightTile uiModeNightTile = UiModeNightTile.this;
            String string2 = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_tile_desc);
            int nightMode = uiModeNightTile.mUiModeManager.getNightMode();
            if (nightMode == 0) {
                Context context = uiModeNightTile.mContext;
                string = context.getString(R.string.sec_dark_mode_tile_when_to_turn_on, context.getString(R.string.sec_dark_mode_auto_title));
            } else if (nightMode == 2) {
                Context context2 = uiModeNightTile.mContext;
                string = context2.getString(R.string.sec_dark_mode_tile_when_to_turn_on, context2.getString(R.string.sec_dark_mode_tile_when_to_turn_on_always));
            } else if (nightMode != 3) {
                string = null;
            } else {
                final Context context3 = uiModeNightTile.mContext;
                final boolean is24HourFormat = DateFormat.is24HourFormat(context3);
                ?? r3 = new Object() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda1
                    public final Object apply(Object obj) {
                        Context context4 = context3;
                        LocalTime localTime = (LocalTime) obj;
                        int hour = localTime.getHour();
                        int minute = localTime.getMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(is24HourFormat ? 11 : 10, hour);
                        calendar.set(12, minute);
                        return DateFormat.getTimeFormat(context4).format(new Date(calendar.getTimeInMillis()));
                    }
                };
                LocalTime customNightModeStart = uiModeNightTile.mUiModeManager.getCustomNightModeStart();
                LocalTime customNightModeEnd = uiModeNightTile.mUiModeManager.getCustomNightModeEnd();
                String str = (String) r3.apply(customNightModeStart);
                String str2 = (String) r3.apply(customNightModeEnd);
                if (customNightModeStart.toSecondOfDay() >= customNightModeEnd.toSecondOfDay()) {
                    str2 = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_off_time_next_day_summary_format, str2);
                }
                string = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_tile_when_to_turn_on_custom, str, str2);
            }
            return !TextUtils.isEmpty(string) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, "\n\n", string) : string2;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 1706;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return UiModeNightTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = UiModeNightTile.$r8$clinit;
            return UiModeNightTile.this.mContext.getString(R.string.sec_dark_mode_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = UiModeNightTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) UiModeNightTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            int i = UiModeNightTile.$r8$clinit;
            UiModeNightTile uiModeNightTile = UiModeNightTile.this;
            if (!uiModeNightTile.canChangeNightMode()) {
                Log.i(uiModeNightTile.TAG, "Tobble is blocked when device does not support dark mode");
                uiModeNightTile.showNightModeMenuDisabledReasonToast();
                return;
            }
            Log.i(uiModeNightTile.TAG, "setToggleState: " + z);
            uiModeNightTile.updateUiModeState(z);
            this.mDetailSummary.setText(getDetailSummary$1());
            uiModeNightTile.mHandler.obtainMessage(100, z ? 1 : 0, 0).sendToTarget();
        }
    }

    static {
        DateTimeFormatter.ofPattern("hh:mm a");
    }

    public UiModeNightTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, PanelInteractor panelInteractor, QSLogger qSLogger, ConfigurationController configurationController, BatteryController batteryController, LocationController locationController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.sec_st_ic_display_night_theme);
        this.mIsNeedToBlockOnClick = false;
        this.mUiModeManager = (UiModeManager) qSHost.getUserContext().getSystemService(UiModeManager.class);
        this.mDetailAdapter = new UiModeDetailAdapter();
        configurationController.observe(this.mLifecycle, this);
        batteryController.observe(this.mLifecycle, this);
        this.mPanelInteractor = panelInteractor;
    }

    public final boolean canChangeNightMode() {
        return (TextUtils.isEmpty(Settings.System.getString(this.mContext.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE)) || Settings.System.getInt(this.mContext.getContentResolver(), "current_theme_support_night_mode", 0) == 1) && !isMinimalBatteryUseEnabled();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        if (canChangeNightMode()) {
            return this.mDetailAdapter;
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (canChangeNightMode()) {
            return new Intent("android.settings.DARK_MODE_SETTINGS");
        }
        showNightModeMenuDisabledReasonToast();
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 1706;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return ((QSTile.BooleanState) this.mState).label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor != null && (!r0.mSettingsChangesAllowed)) {
            super.showItPolicyToast();
            return;
        }
        int i = ((QSTile.BooleanState) this.mState).state;
        String str = this.TAG;
        if (i == 0) {
            Log.i(str, "onClick is blocked when tile state is unavailable");
            return;
        }
        if (isPowerSavingAndTurnOnDarkModeEnabled()) {
            Log.i(str, "onClick is blocked when Power saving mode with turn dark mode option is on ");
            showSnackbar(expandable);
        } else {
            if (this.mIsNeedToBlockOnClick) {
                Log.i(str, "onClick is blocked when dark mode is updating");
                return;
            }
            if (!canChangeNightMode()) {
                Log.i(str, "onClick is blocked when device does not support dark mode");
                showNightModeMenuDisabledReasonToast();
            } else {
                this.mIsNeedToBlockOnClick = true;
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                        boolean z = !((QSTile.BooleanState) uiModeNightTile.mState).value;
                        Log.i(uiModeNightTile.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("handleClick: ", z));
                        uiModeNightTile.updateUiModeState(z);
                        uiModeNightTile.refreshState(Boolean.valueOf(z));
                    }
                }, 200L);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_DARK_MODE_TILE);
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        if (!isPowerSavingAndTurnOnDarkModeEnabled()) {
            super.handleLongClick(expandable);
        } else {
            Log.i(this.TAG, "LongClick is blocked when Power saving mode with turn dark mode option is on ");
            showSnackbar(expandable);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTileImpl.H h;
        final QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean z = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        booleanState.dualTarget = true;
        booleanState.secondaryLabel = null;
        booleanState.value = z;
        booleanState.label = this.mContext.getString(R.string.sec_dark_mode_title);
        booleanState.icon = this.mIcon;
        if (isMinimalBatteryUseEnabled()) {
            booleanState.state = 2;
        } else if (canChangeNightMode()) {
            booleanState.state = this.mIsNeedToBlockOnClick ? 0 : booleanState.value ? 2 : 1;
        } else {
            booleanState.state = 1;
        }
        if (!this.mIsNeedToBlockOnClick || (h = this.mHandler) == null) {
            return;
        }
        h.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.2
            @Override // java.lang.Runnable
            public final void run() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                uiModeNightTile.mIsNeedToBlockOnClick = false;
                uiModeNightTile.refreshState(Boolean.valueOf(booleanState.value));
            }
        }, 300L);
    }

    public final boolean isMinimalBatteryUseEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_MINIMAL_BATTERY_USE, 0) == 1;
    }

    public final boolean isPowerSavingAndTurnOnDarkModeEnabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_LOW_POWER_MODE, -1) == 1 && Settings.Global.getInt(this.mContext.getContentResolver(), "pms_settings_dark_mode_enabled", -1) == 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean isCurrentSnackbarLocked;
        Snackbar snackbar = this.mSnackbar;
        if (snackbar != null) {
            SnackbarManager snackbarManager = SnackbarManager.getInstance();
            BaseTransientBottomBar.AnonymousClass5 anonymousClass5 = snackbar.managerCallback;
            synchronized (snackbarManager.lock) {
                isCurrentSnackbarLocked = snackbarManager.isCurrentSnackbarLocked(anonymousClass5);
            }
            if (isCurrentSnackbarLocked) {
                this.mSnackbar.dispatchDismiss(3);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void showItPolicyToast() {
        throw null;
    }

    public final void showNightModeMenuDisabledReasonToast() {
        final String string = isMinimalBatteryUseEnabled() ? this.mContext.getString(R.string.sec_dark_mode_disabled_by_power_saving_mode) : this.mContext.getString(R.string.sec_dark_mode_disabled_by_open_theme);
        new Handler(this.mContext.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.3
            @Override // java.lang.Runnable
            public final void run() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                int i = UiModeNightTile.$r8$clinit;
                Toast makeText = Toast.makeText(uiModeNightTile.mContext, string, 0);
                if (makeText != null) {
                    makeText.show();
                }
            }
        });
    }

    public final void showSnackbar(Expandable expandable) {
        Snackbar makeInternal = Snackbar.makeInternal(new ContextThemeWrapper(this.mContext, 2132018522), expandable.getView(), this.mContext.getString(R.string.sec_dark_mode_you_can_change_this_in_power_saving), -2, 0);
        this.mSnackbar = makeInternal;
        makeInternal.setAction(this.mContext.getString(R.string.sec_dark_mode_go_to_settings), new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UiModeNightTile.this.mPanelInteractor.collapsePanels();
                Uri parse = Uri.parse("content://" + ActivityManager.getCurrentUser() + "@com.samsung.android.sm.dcapi");
                Bundle bundle = new Bundle();
                UiModeNightTile.this.mContext.getContentResolver().call(parse, "psm_start_power_saving_activity", (String) null, bundle);
                boolean z = bundle.getBoolean("result");
                boolean z2 = bundle.getBoolean("changeable");
                if (!z) {
                    Log.i(UiModeNightTile.this.TAG, "API call has failed");
                }
                if (z2) {
                    Log.i(UiModeNightTile.this.TAG, "Enter in power saving");
                }
            }
        });
        Snackbar snackbar = this.mSnackbar;
        snackbar.duration = 3000;
        snackbar.show();
    }

    public final void updateUiModeState(boolean z) {
        if (this.mUiModeManager.getNightMode() != 0 && this.mUiModeManager.getNightMode() != 3) {
            this.mUiModeManager.setNightMode(z ? 2 : 1);
            return;
        }
        this.mUiModeManager.setNightModeActivated(z);
        if (z) {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.sec_dark_mode_scheduled_toast), 0).show();
        }
    }
}
