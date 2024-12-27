package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.NightDisplayListener;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.dagger.NightDisplayListenerModule$Builder;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NightDisplayTile extends QSTileImpl implements NightDisplayListener.Callback {
    public boolean mIsListening;
    public NightDisplayListener mListener;
    public final LocationController mLocationController;
    public ColorDisplayManager mManager;
    public final NightDisplayListenerModule$Builder mNightDisplayListenerBuilder;

    public NightDisplayTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, ColorDisplayManager colorDisplayManager, NightDisplayListenerModule$Builder nightDisplayListenerModule$Builder) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLocationController = locationController;
        this.mManager = colorDisplayManager;
        this.mNightDisplayListenerBuilder = nightDisplayListenerModule$Builder;
        nightDisplayListenerModule$Builder.mUserId = qSHost.getUserContext().getUserId();
        this.mListener = new NightDisplayListener(nightDisplayListenerModule$Builder.mContext, nightDisplayListenerModule$Builder.mUserId, nightDisplayListenerModule$Builder.mBgHandler);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.NIGHT_DISPLAY_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 491;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_night_display_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        if ("1".equals(Settings.Global.getString(this.mContext.getContentResolver(), "night_display_forced_auto_mode_available")) && this.mManager.getNightDisplayAutoModeRaw() == -1) {
            this.mManager.setNightDisplayAutoMode(1);
            Log.i("NightDisplayTile", "Enrolled in forced night display auto mode");
        }
        this.mManager.setNightDisplayActivated(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mIsListening = z;
        if (!z) {
            this.mListener.setCallback((NightDisplayListener.Callback) null);
        } else {
            this.mListener.setCallback(this);
            refreshState(null);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        LocalTime nightDisplayCustomStartTime;
        int i;
        String string;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.value = this.mManager.isNightDisplayActivated();
        booleanState.label = this.mContext.getString(R.string.quick_settings_night_display_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        boolean z = booleanState.value;
        booleanState.state = z ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(z ? R.drawable.qs_nightlight_icon_on : R.drawable.qs_nightlight_icon_off);
        boolean z2 = booleanState.value;
        int nightDisplayAutoMode = this.mManager.getNightDisplayAutoMode();
        if (nightDisplayAutoMode != 1) {
            string = null;
            if (nightDisplayAutoMode == 2 && ((LocationControllerImpl) this.mLocationController).isLocationEnabled$1()) {
                string = z2 ? this.mContext.getString(R.string.quick_settings_night_secondary_label_until_sunrise) : this.mContext.getString(R.string.quick_settings_night_secondary_label_on_at_sunset);
            }
        } else {
            if (z2) {
                nightDisplayCustomStartTime = this.mManager.getNightDisplayCustomEndTime();
                i = R.string.quick_settings_secondary_label_until;
            } else {
                nightDisplayCustomStartTime = this.mManager.getNightDisplayCustomStartTime();
                i = R.string.quick_settings_night_secondary_label_on_at;
            }
            Calendar calendar = Calendar.getInstance();
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this.mContext);
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTimeZone(timeFormat.getTimeZone());
            calendar.set(11, nightDisplayCustomStartTime.getHour());
            calendar.set(12, nightDisplayCustomStartTime.getMinute());
            calendar.set(13, 0);
            calendar.set(14, 0);
            string = this.mContext.getString(i, timeFormat.format(calendar.getTime()));
        }
        booleanState.secondaryLabel = string;
        booleanState.contentDescription = TextUtils.isEmpty(string) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        if (this.mIsListening) {
            this.mListener.setCallback((NightDisplayListener.Callback) null);
        }
        this.mManager = (ColorDisplayManager) this.mHost.getUserContext().getSystemService(ColorDisplayManager.class);
        NightDisplayListenerModule$Builder nightDisplayListenerModule$Builder = this.mNightDisplayListenerBuilder;
        nightDisplayListenerModule$Builder.mUserId = i;
        NightDisplayListener nightDisplayListener = new NightDisplayListener(nightDisplayListenerModule$Builder.mContext, nightDisplayListenerModule$Builder.mUserId, nightDisplayListenerModule$Builder.mBgHandler);
        this.mListener = nightDisplayListener;
        if (this.mIsListening) {
            nightDisplayListener.setCallback(this);
        }
        handleRefreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return ColorDisplayManager.isNightDisplayAvailable(this.mContext);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void onActivated(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).addTaggedData(1311, Integer.valueOf(this.mManager.getNightDisplayAutoModeRaw()));
    }
}
