package com.android.systemui.lowlightclock;

import android.content.Context;
import android.content.res.Resources;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.google.android.systemui.lowlightclock.LowLightClockDreamService$$ExternalSyntheticLambda0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ChargingStatusProvider {
    public final IBatteryStats mBatteryInfo;
    public final BatteryState mBatteryState = new BatteryState(0);
    public LowLightClockDreamService$$ExternalSyntheticLambda0 mCallback;
    public ChargingStatusCallback mChargingStatusCallback;
    public final Context mContext;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Resources mResources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ChargingStatusCallback extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ ChargingStatusCallback(ChargingStatusProvider chargingStatusProvider, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
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

    /* JADX WARN: Code restructure failed: missing block: B:107:0x014c, code lost:
    
        if (r2 != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00f9, code lost:
    
        if (r2 != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00fb, code lost:
    
        r10 = com.android.systemui.R.string.keyguard_indication_charging_time;
     */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportStatusToCallback() {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lowlightclock.ChargingStatusProvider.reportStatusToCallback():void");
    }

    public final void startUsing(LowLightClockDreamService$$ExternalSyntheticLambda0 lowLightClockDreamService$$ExternalSyntheticLambda0) {
        int i = 0;
        Preconditions.checkState(this.mCallback == null, "ChargingStatusProvider already started!");
        this.mCallback = lowLightClockDreamService$$ExternalSyntheticLambda0;
        ChargingStatusCallback chargingStatusCallback = new ChargingStatusCallback(this, i);
        this.mChargingStatusCallback = chargingStatusCallback;
        this.mKeyguardUpdateMonitor.registerCallback(chargingStatusCallback);
        reportStatusToCallback();
    }
}
