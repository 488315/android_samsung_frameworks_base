package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesChecker;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BluetoothController;

/* loaded from: classes2.dex */
public class HearingDevicesTile extends QSTileImpl {
    public final AnonymousClass1 mCallback;
    public final HearingDevicesChecker mDevicesChecker;
    public final HearingDevicesDialogManager mDialogManager;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.HearingDevicesTile$1, java.lang.Object] */
    public HearingDevicesTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HearingDevicesDialogManager hearingDevicesDialogManager, HearingDevicesChecker hearingDevicesChecker, BluetoothController bluetoothController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        ?? r1 = new BluetoothController.Callback() { // from class: com.android.systemui.qs.tiles.HearingDevicesTile.1
            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothDevicesChanged() {
                HearingDevicesTile.this.refreshState(null);
            }

            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothStateChange(boolean z) {
                HearingDevicesTile.this.refreshState(null);
            }
        };
        this.mCallback = r1;
        this.mDialogManager = hearingDevicesDialogManager;
        this.mDevicesChecker = hearingDevicesChecker;
        bluetoothController.observe(this.mLifecycle, r1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.HEARING_DEVICES_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_hearing_devices_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.HearingDevicesTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HearingDevicesTile hearingDevicesTile = this.f$0;
                hearingDevicesTile.mDialogManager.showDialog(expandable, 2);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_bluetooth");
        booleanState.label = this.mContext.getString(R.string.quick_settings_hearing_devices_label);
        int i = QsInCompose.$r8$clinit;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.qs_hearing_devices_icon);
        booleanState.forceExpandIcon = true;
        HearingDevicesChecker hearingDevicesChecker = this.mDevicesChecker;
        boolean zIsAnyPairedHearingDevice = hearingDevicesChecker.isAnyPairedHearingDevice();
        if (hearingDevicesChecker.isAnyActiveHearingDevice()) {
            booleanState.state = 2;
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_hearing_devices_connected);
        } else if (zIsAnyPairedHearingDevice) {
            booleanState.state = 1;
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_hearing_devices_disconnected);
        } else {
            booleanState.state = 1;
            booleanState.secondaryLabel = "";
        }
        booleanState.expandedAccessibilityClassName = Button.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
