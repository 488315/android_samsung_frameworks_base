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
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.EdmMonitor;
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
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes2.dex */
public class UiModeNightTile extends QSTileImpl implements ConfigurationController.ConfigurationListener, BatteryController.BatteryStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UiModeDetailAdapter mDetailAdapter;
    public final QSTile.Icon mIcon;
    public boolean mIsNeedToBlockOnClick;
    public Snackbar mSnackbar;
    public FrameLayout mSnackbarContainer;
    public final UiModeManager mUiModeManager;
    public WindowManager mWindowManager;

    public class UiModeDetailAdapter implements DetailAdapter {
        public TextView mDetailSummary;

        public UiModeDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            int i = UiModeNightTile.$r8$clinit;
            View viewInflate = LayoutInflater.from(UiModeNightTile.this.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            TextView textView = (TextView) viewInflate.findViewById(R.id.message);
            this.mDetailSummary = textView;
            textView.setText(getDetailSummary$1());
            return viewInflate;
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
                final boolean zIs24HourFormat = DateFormat.is24HourFormat(context3);
                ?? r3 = new Object() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda1
                    public final Object apply(Object obj) {
                        Context context4 = context3;
                        LocalTime localTime = (LocalTime) obj;
                        int i2 = UiModeNightTile.$r8$clinit;
                        int hour = localTime.getHour();
                        int minute = localTime.getMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(zIs24HourFormat ? 11 : 10, hour);
                        calendar.set(12, minute);
                        return DateFormat.getTimeFormat(context4).format(new Date(calendar.getTimeInMillis()));
                    }
                };
                LocalTime customNightModeStart = uiModeNightTile.mUiModeManager.getCustomNightModeStart();
                LocalTime customNightModeEnd = uiModeNightTile.mUiModeManager.getCustomNightModeEnd();
                String str = (String) r3.apply(customNightModeStart);
                String string3 = (String) r3.apply(customNightModeEnd);
                if (customNightModeStart.toSecondOfDay() >= customNightModeEnd.toSecondOfDay()) {
                    string3 = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_off_time_next_day_summary_format, string3);
                }
                string = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_tile_when_to_turn_on_custom, str, string3);
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

    public UiModeNightTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ConfigurationController configurationController, BatteryController batteryController, LocationController locationController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.sec_st_ic_display_night_theme);
        this.mIsNeedToBlockOnClick = false;
        this.mUiModeManager = (UiModeManager) qSHost.getUserContext().getSystemService(UiModeManager.class);
        this.mDetailAdapter = new UiModeDetailAdapter();
        configurationController.observe(this.mLifecycle, this);
        batteryController.observe(this.mLifecycle, this);
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
        return ((QSTile.BooleanState) getState()).label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null && !edmMonitor.mSettingsChangesAllowed) {
            super.showItPolicyToast();
            return;
        }
        int i = ((QSTile.BooleanState) getState()).state;
        String str = this.TAG;
        if (i == 0) {
            Log.i(str, "onClick is blocked when tile state is unavailable");
            return;
        }
        if (isPowerSavingAndTurnOnDarkModeEnabled()) {
            Log.i(str, "onClick is blocked when Power saving mode with turn dark mode option is on ");
            if (this.mSnackbarContainer == null) {
                setupSnackbarContainer();
                showSnackbar();
                return;
            }
            return;
        }
        if (this.mIsNeedToBlockOnClick) {
            Log.i(str, "onClick is blocked when dark mode is updating");
        } else if (canChangeNightMode()) {
            this.mIsNeedToBlockOnClick = true;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UiModeNightTile uiModeNightTile = this.f$0;
                    int i2 = UiModeNightTile.$r8$clinit;
                    boolean z = !((QSTile.BooleanState) uiModeNightTile.mState).value;
                    Log.i(uiModeNightTile.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("handleClick: ", z));
                    uiModeNightTile.updateUiModeState(z);
                    uiModeNightTile.refreshState(Boolean.valueOf(z));
                }
            }, 200L);
        } else {
            Log.i(str, "onClick is blocked when device does not support dark mode");
            showNightModeMenuDisabledReasonToast();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        if (!isPowerSavingAndTurnOnDarkModeEnabled()) {
            super.handleLongClick(expandable);
            return;
        }
        Log.i(this.TAG, "LongClick is blocked when Power saving mode with turn dark mode option is on ");
        if (this.mSnackbarContainer == null) {
            setupSnackbarContainer();
            showSnackbar();
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
        h.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.4
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
        if (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_LOW_POWER_MODE, -1) == 1) {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "pms_settings_dark_mode_enabled", -1) == 1 || isMinimalBatteryUseEnabled();
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean zIsCurrentSnackbarLocked;
        Snackbar snackbar = this.mSnackbar;
        if (snackbar != null) {
            SnackbarManager snackbarManager = SnackbarManager.getInstance();
            BaseTransientBottomBar.AnonymousClass5 anonymousClass5 = snackbar.managerCallback;
            synchronized (snackbarManager.lock) {
                zIsCurrentSnackbarLocked = snackbarManager.isCurrentSnackbarLocked(anonymousClass5);
            }
            if (zIsCurrentSnackbarLocked) {
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

    public final void setupSnackbarContainer() {
        this.mSnackbarContainer = new FrameLayout(this.mContext);
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 2009, 8, -3);
        layoutParams.gravity = 81;
        new Handler(this.mContext.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.1
            @Override // java.lang.Runnable
            public final void run() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                uiModeNightTile.mWindowManager.addView(uiModeNightTile.mSnackbarContainer, layoutParams);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void showItPolicyToast() {
        throw null;
    }

    public final void showNightModeMenuDisabledReasonToast() {
        final String string = isMinimalBatteryUseEnabled() ? this.mContext.getString(R.string.sec_dark_mode_disabled_by_power_saving_mode) : this.mContext.getString(R.string.sec_dark_mode_disabled_by_open_theme);
        new Handler(this.mContext.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.5
            @Override // java.lang.Runnable
            public final void run() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                int i = UiModeNightTile.$r8$clinit;
                Toast toastMakeText = Toast.makeText(uiModeNightTile.mContext, string, 0);
                if (toastMakeText != null) {
                    toastMakeText.show();
                }
            }
        });
    }

    public final void showSnackbar() {
        Snackbar snackbarMakeInternal = Snackbar.makeInternal(new ContextThemeWrapper(this.mContext, 2132018763), this.mSnackbarContainer, this.mContext.getString(R.string.sec_dark_mode_you_can_change_this_in_power_saving), -2, 0);
        this.mSnackbar = snackbarMakeInternal;
        snackbarMakeInternal.setAction(this.mContext.getString(R.string.sec_dark_mode_go_to_settings), new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Uri uri = Uri.parse("content://" + ActivityManager.getCurrentUser() + "@com.samsung.android.sm.dcapi");
                Bundle bundleM = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("key_preference", "dark_mode");
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                int i = UiModeNightTile.$r8$clinit;
                Bundle bundleCall = uiModeNightTile.mContext.getContentResolver().call(uri, "psm_start_power_saving_activity", (String) null, bundleM);
                boolean z = bundleCall.getBoolean("result");
                boolean z2 = bundleCall.getBoolean("changeable");
                if (!z) {
                    Log.i(UiModeNightTile.this.TAG, "API call has failed");
                }
                if (z2) {
                    Log.i(UiModeNightTile.this.TAG, "Enter in power saving");
                }
            }
        });
        this.mSnackbar.addCallback(new Snackbar.Callback() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.3
            @Override // com.google.android.material.snackbar.Snackbar.Callback, com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
            public final /* bridge */ /* synthetic */ void onDismissed(BaseTransientBottomBar baseTransientBottomBar, int i) {
                onDismissed();
            }

            @Override // com.google.android.material.snackbar.Snackbar.Callback
            public final void onDismissed() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                FrameLayout frameLayout = uiModeNightTile.mSnackbarContainer;
                if (frameLayout != null) {
                    uiModeNightTile.mWindowManager.removeView(frameLayout);
                    uiModeNightTile.mSnackbarContainer = null;
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
