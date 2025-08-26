package com.android.systemui.lowlightclock;

import android.content.Context;
import android.content.res.Resources;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.R;
import com.google.android.systemui.lowlightclock.LowLightClockDreamService;
import com.google.android.systemui.lowlightclock.LowLightClockDreamService$$ExternalSyntheticLambda0;
import java.text.NumberFormat;

/* loaded from: classes2.dex */
public class ChargingStatusProvider {
    public final IBatteryStats mBatteryInfo;
    public final BatteryState mBatteryState = new BatteryState(0);
    public LowLightClockDreamService$$ExternalSyntheticLambda0 mCallback;
    public ChargingStatusCallback mChargingStatusCallback;
    public final Context mContext;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Resources mResources;

    public class BatteryState {
        public BatteryStatus mBatteryStatus;

        public /* synthetic */ BatteryState(int i) {
            this();
        }

        public final boolean isChargingOrFull() {
            if (!isValid()) {
                return false;
            }
            BatteryStatus batteryStatus = this.mBatteryStatus;
            return batteryStatus.status == 2 || batteryStatus.isCharged();
        }

        public final boolean isValid() {
            return this.mBatteryStatus != null;
        }

        private BatteryState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public class ChargingStatusCallback extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ ChargingStatusCallback(ChargingStatusProvider chargingStatusProvider, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshBatteryInfo(BatteryStatus batteryStatus) throws Resources.NotFoundException {
            ChargingStatusProvider chargingStatusProvider = ChargingStatusProvider.this;
            chargingStatusProvider.mBatteryState.mBatteryStatus = batteryStatus;
            chargingStatusProvider.reportStatusToCallback();
        }

        private ChargingStatusCallback() {
        }
    }

    public ChargingStatusProvider(Context context, Resources resources, IBatteryStats iBatteryStats, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mResources = resources;
        this.mBatteryInfo = iBatteryStats;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reportStatusToCallback() throws Resources.NotFoundException {
        int i;
        String string;
        if (this.mCallback != null) {
            BatteryState batteryState = this.mBatteryState;
            boolean z = (batteryState.isValid() && batteryState.mBatteryStatus.isPluggedIn() && batteryState.isChargingOrFull()) || (batteryState.isValid() && batteryState.mBatteryStatus.isPluggedIn() && batteryState.isValid() && batteryState.mBatteryStatus.chargingStatus == 4);
            LowLightClockDreamService$$ExternalSyntheticLambda0 lowLightClockDreamService$$ExternalSyntheticLambda0 = this.mCallback;
            if (!batteryState.isValid()) {
                string = null;
            } else if (batteryState.isValid() && batteryState.mBatteryStatus.chargingStatus == 4) {
                string = this.mResources.getString(R.string.keyguard_plugged_in_charging_limited, NumberFormat.getPercentInstance().format((batteryState.isValid() ? batteryState.mBatteryStatus.level : 0) / 100.0f));
            } else if (batteryState.isValid() && batteryState.mBatteryStatus.isCharged()) {
                string = this.mResources.getString(R.string.keyguard_charged);
            } else {
                IBatteryStats iBatteryStats = this.mBatteryInfo;
                long jComputeChargeTimeRemaining = -1;
                try {
                    if (batteryState.isValid() && batteryState.mBatteryStatus.isPluggedIn() && batteryState.isChargingOrFull()) {
                        jComputeChargeTimeRemaining = iBatteryStats.computeChargeTimeRemaining();
                    }
                } catch (RemoteException e) {
                    Log.e("ChargingStatusProvider", "Error calling IBatteryStats: ", e);
                }
                boolean z2 = jComputeChargeTimeRemaining > 0;
                boolean zIsValid = batteryState.isValid();
                int i2 = R.string.keyguard_plugged_in;
                if (zIsValid && batteryState.mBatteryStatus.isPluggedInWired() && batteryState.isChargingOrFull()) {
                    int chargingSpeed = batteryState.isValid() ? batteryState.mBatteryStatus.getChargingSpeed(this.mContext) : 0;
                    if (chargingSpeed == 0) {
                        i = z2 ? R.string.keyguard_indication_charging_time_slowly : R.string.keyguard_plugged_in_charging_slowly;
                    } else if (chargingSpeed != 2) {
                        if (z2) {
                        }
                        String str = NumberFormat.getPercentInstance().format((!batteryState.isValid() ? batteryState.mBatteryStatus.level : 0) / 100.0f);
                        if (!z2) {
                        }
                    } else {
                        i = z2 ? R.string.keyguard_indication_charging_time_fast : R.string.keyguard_plugged_in_charging_fast;
                    }
                    i2 = i;
                    String str2 = NumberFormat.getPercentInstance().format((!batteryState.isValid() ? batteryState.mBatteryStatus.level : 0) / 100.0f);
                    if (!z2) {
                    }
                } else {
                    if (batteryState.isValid() && batteryState.mBatteryStatus.plugged == 4 && batteryState.isChargingOrFull()) {
                        i = z2 ? R.string.keyguard_indication_charging_time_wireless : R.string.keyguard_plugged_in_wireless;
                    } else if (batteryState.isValid() && batteryState.mBatteryStatus.plugged == 8 && batteryState.isChargingOrFull()) {
                        i = z2 ? R.string.keyguard_indication_charging_time_dock : R.string.keyguard_plugged_in_dock;
                    } else {
                        if (z2) {
                            i2 = R.string.keyguard_indication_charging_time;
                        }
                        String str22 = NumberFormat.getPercentInstance().format((!batteryState.isValid() ? batteryState.mBatteryStatus.level : 0) / 100.0f);
                        string = !z2 ? this.mResources.getString(i2, Formatter.formatShortElapsedTimeRoundingUpToMinutes(this.mContext, jComputeChargeTimeRemaining), str22) : this.mResources.getString(i2, str22);
                    }
                    i2 = i;
                    String str222 = NumberFormat.getPercentInstance().format((!batteryState.isValid() ? batteryState.mBatteryStatus.level : 0) / 100.0f);
                    if (!z2) {
                    }
                }
            }
            LowLightClockDreamService lowLightClockDreamService = lowLightClockDreamService$$ExternalSyntheticLambda0.f$0;
            lowLightClockDreamService.mChargingStatusTextView.setText(string);
            lowLightClockDreamService.mChargingStatusTextView.setVisibility(z ? 0 : 4);
        }
    }

    public final void startUsing(LowLightClockDreamService$$ExternalSyntheticLambda0 lowLightClockDreamService$$ExternalSyntheticLambda0) throws Resources.NotFoundException {
        int i = 0;
        Preconditions.checkState(this.mCallback == null, "ChargingStatusProvider already started!");
        this.mCallback = lowLightClockDreamService$$ExternalSyntheticLambda0;
        ChargingStatusCallback chargingStatusCallback = new ChargingStatusCallback(this, i);
        this.mChargingStatusCallback = chargingStatusCallback;
        this.mKeyguardUpdateMonitor.registerCallback(chargingStatusCallback);
        reportStatusToCallback();
    }
}
