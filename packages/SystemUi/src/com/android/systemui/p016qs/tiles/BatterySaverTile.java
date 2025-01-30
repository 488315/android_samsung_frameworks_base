package com.android.systemui.p016qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.systemui.R;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BatterySaverTile extends SQSTileImpl implements BatteryController.BatteryStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BatteryController mBatteryController;
    public boolean mPluggedIn;
    public boolean mPowerSave;
    protected final SettingObserver mSetting;

    public BatterySaverTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BatteryController batteryController, SecureSettings secureSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mBatteryController = batteryController;
        batteryController.observe(((QSTileImpl) this).mLifecycle, this);
        this.mSetting = new SettingObserver(secureSettings, ((SQSTileImpl) this).mHandler, "low_power_warning_acknowledged", qSHost.getUserContext().getUserId()) { // from class: com.android.systemui.qs.tiles.BatterySaverTile.1
            @Override // com.android.systemui.p016qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                BatterySaverTile batterySaverTile = BatterySaverTile.this;
                int i2 = BatterySaverTile.$r8$clinit;
                batterySaverTile.handleRefreshState(null);
            }
        };
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.BATTERY_SAVER_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 261;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.battery_detail_switch_title);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        boolean z = !this.mPowerSave;
        BatteryControllerImpl batteryControllerImpl = (BatteryControllerImpl) this.mBatteryController;
        if (z) {
            batteryControllerImpl.mPowerSaverStartView.set(new WeakReference(view));
        }
        BatterySaverUtils.setPowerSaveMode(4, batteryControllerImpl.mContext, z, true);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
        if (z) {
            return;
        }
        ((BatteryControllerImpl) this.mBatteryController).mPowerSaverStartView.set(null);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.state = this.mPluggedIn ? 0 : this.mPowerSave ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(this.mPowerSave ? R.drawable.qs_battery_saver_icon_on : R.drawable.qs_battery_saver_icon_off);
        String string = this.mContext.getString(R.string.battery_detail_switch_title);
        booleanState.label = string;
        booleanState.secondaryLabel = "";
        booleanState.contentDescription = string;
        booleanState.value = this.mPowerSave;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.showRippleEffect = this.mSetting.getValue() == 0;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        this.mPluggedIn = z;
        refreshState(Integer.valueOf(i));
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        this.mPowerSave = z;
        refreshState(null);
    }
}
