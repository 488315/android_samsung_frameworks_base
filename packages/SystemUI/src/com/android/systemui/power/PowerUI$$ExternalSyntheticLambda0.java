package com.android.systemui.power;

import com.android.systemui.power.PowerUI;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerUI$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerUI$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PowerUI powerUI = (PowerUI) obj;
                boolean z = PowerUI.DEBUG;
                powerUI.doSkinThermalEventListenerRegistration();
                powerUI.doUsbThermalEventListenerRegistration();
                return;
            default:
                PowerUI.Receiver receiver = (PowerUI.Receiver) obj;
                if (receiver.this$0.mPowerManager.isPowerSaveMode()) {
                    receiver.this$0.getClass();
                    throw null;
                }
                return;
        }
    }
}
