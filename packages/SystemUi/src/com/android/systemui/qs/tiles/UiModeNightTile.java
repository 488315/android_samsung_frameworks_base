package com.android.systemui.qs.tiles;

import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.arch.core.util.Function;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.SystemUIAnalytics;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UiModeNightTile extends SQSTileImpl implements ConfigurationController.ConfigurationListener, BatteryController.BatteryStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UiModeDetailAdapter mDetailAdapter;
    public final QSTile.Icon mIcon;
    public boolean mIsNeedToBlockOnClick;
    public final UiModeManager mUiModeManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            textView.setText(getDetailSummary());
            return inflate;
        }

        public final String getDetailSummary() {
            String string;
            int i = UiModeNightTile.$r8$clinit;
            UiModeNightTile uiModeNightTile = UiModeNightTile.this;
            String string2 = uiModeNightTile.mContext.getString(R.string.sec_dark_mode_tile_desc);
            int nightMode = uiModeNightTile.mUiModeManager.getNightMode();
            final Context context = uiModeNightTile.mContext;
            if (nightMode == 0) {
                string = context.getString(R.string.sec_dark_mode_tile_when_to_turn_on, context.getString(R.string.sec_dark_mode_auto_title));
            } else if (nightMode == 2) {
                string = context.getString(R.string.sec_dark_mode_tile_when_to_turn_on, context.getString(R.string.sec_dark_mode_tile_when_to_turn_on_always));
            } else if (nightMode != 3) {
                string = null;
            } else {
                final boolean is24HourFormat = DateFormat.is24HourFormat(context);
                Function function = new Function() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda1
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        LocalTime localTime = (LocalTime) obj;
                        int hour = localTime.getHour();
                        int minute = localTime.getMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(is24HourFormat ? 11 : 10, hour);
                        calendar.set(12, minute);
                        return DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis()));
                    }
                };
                UiModeManager uiModeManager = uiModeNightTile.mUiModeManager;
                LocalTime customNightModeStart = uiModeManager.getCustomNightModeStart();
                LocalTime customNightModeEnd = uiModeManager.getCustomNightModeEnd();
                String str = (String) function.apply(customNightModeStart);
                String str2 = (String) function.apply(customNightModeEnd);
                if (customNightModeStart.toSecondOfDay() >= customNightModeEnd.toSecondOfDay()) {
                    str2 = context.getString(R.string.sec_dark_mode_off_time_next_day_summary_format, str2);
                }
                string = context.getString(R.string.sec_dark_mode_tile_when_to_turn_on_custom, str, str2);
            }
            return !TextUtils.isEmpty(string) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(string2, "\n\n", string) : string2;
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
            this.mDetailSummary.setText(getDetailSummary());
            uiModeNightTile.fireToggleStateChanged(z);
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
        configurationController.observe(((QSTileImpl) this).mLifecycle, this);
        batteryController.observe(((QSTileImpl) this).mLifecycle, this);
    }

    public final boolean canChangeNightMode() {
        Context context = this.mContext;
        String string = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage");
        return (string == null || string.isEmpty() || Settings.System.getInt(context.getContentResolver(), "current_theme_support_night_mode", 0) == 1) && !isMinimalBatteryUseEnabled();
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return ((QSTile.BooleanState) this.mState).label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        int i = ((QSTile.BooleanState) this.mState).state;
        String str = this.TAG;
        if (i == 0) {
            Log.i(str, "onClick is blocked when tile state is unavailable");
            return;
        }
        if (this.mIsNeedToBlockOnClick) {
            Log.i(str, "onClick is blocked when dark mode is updating");
            return;
        }
        if (!canChangeNightMode()) {
            Log.i(str, "onClick is blocked when device does not support dark mode");
            showNightModeMenuDisabledReasonToast();
        } else {
            this.mIsNeedToBlockOnClick = true;
            ((SQSTileImpl) this).mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                    boolean z = !((QSTile.BooleanState) uiModeNightTile.mState).value;
                    Log.i(uiModeNightTile.TAG, AbstractC0866xb1ce8deb.m86m("handleClick: ", z));
                    uiModeNightTile.updateUiModeState(z);
                    uiModeNightTile.refreshState(Boolean.valueOf(z));
                }
            }, 200L);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        SQSTileImpl.SHandler sHandler;
        final QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        Context context = this.mContext;
        boolean z = (context.getResources().getConfiguration().uiMode & 48) == 32;
        booleanState.dualTarget = true;
        booleanState.secondaryLabel = null;
        booleanState.value = z;
        booleanState.label = context.getString(R.string.sec_dark_mode_title);
        booleanState.icon = this.mIcon;
        if (isMinimalBatteryUseEnabled()) {
            booleanState.state = 2;
        } else if (canChangeNightMode()) {
            booleanState.state = this.mIsNeedToBlockOnClick ? 0 : booleanState.value ? 2 : 1;
        } else {
            booleanState.state = 1;
        }
        if (!this.mIsNeedToBlockOnClick || (sHandler = ((SQSTileImpl) this).mHandler) == null) {
            return;
        }
        sHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.1
            @Override // java.lang.Runnable
            public final void run() {
                UiModeNightTile uiModeNightTile = UiModeNightTile.this;
                uiModeNightTile.mIsNeedToBlockOnClick = false;
                uiModeNightTile.refreshState(Boolean.valueOf(booleanState.value));
            }
        }, 300L);
    }

    public final boolean isMinimalBatteryUseEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "minimal_battery_use", 0) == 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        refreshState(null);
    }

    public final void showNightModeMenuDisabledReasonToast() {
        boolean isMinimalBatteryUseEnabled = isMinimalBatteryUseEnabled();
        Context context = this.mContext;
        final String string = isMinimalBatteryUseEnabled ? context.getString(R.string.sec_dark_mode_disabled_by_power_saving_mode) : context.getString(R.string.sec_dark_mode_disabled_by_open_theme);
        new Handler(context.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tiles.UiModeNightTile.2
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

    public final void updateUiModeState(boolean z) {
        UiModeManager uiModeManager = this.mUiModeManager;
        int nightMode = uiModeManager.getNightMode();
        Context context = this.mContext;
        if (nightMode == 0 || uiModeManager.getNightMode() == 3) {
            uiModeManager.setNightModeActivated(z);
            if (z) {
                Toast.makeText(context, context.getString(R.string.sec_dark_mode_scheduled_toast), 0).show();
                return;
            }
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null && Settings.Global.getInt(contentResolver, "ui_night_mode_on_suw", 2) == 1 && !z) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE1051");
            Settings.Global.putInt(contentResolver, "ui_night_mode_on_suw", 2);
        }
        uiModeManager.setNightMode(z ? 2 : 1);
    }
}
