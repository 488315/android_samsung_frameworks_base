package com.android.systemui.power;

import android.content.DialogInterface;
import com.android.systemui.volume.Events;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda2 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda2(PowerNotificationWarnings powerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = powerNotificationWarnings;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        switch (this.$r8$classId) {
            case 0:
                PowerNotificationWarnings powerNotificationWarnings = this.f$0;
                powerNotificationWarnings.mUsbHighTempDialog = null;
                Events.writeEvent(20, 9, Boolean.valueOf(powerNotificationWarnings.mKeyguard.isKeyguardLocked()));
                break;
            case 1:
                this.f$0.mThermalShutdownDialog = null;
                break;
            case 2:
                PowerNotificationWarnings powerNotificationWarnings2 = this.f$0;
                powerNotificationWarnings2.mSaverConfirmation = null;
                powerNotificationWarnings2.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_DISMISS);
                break;
            default:
                this.f$0.mHighTempDialog = null;
                break;
        }
    }
}
