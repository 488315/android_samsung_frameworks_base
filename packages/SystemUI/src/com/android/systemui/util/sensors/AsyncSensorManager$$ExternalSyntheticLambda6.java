package com.android.systemui.util.sensors;

import android.hardware.Sensor;
import android.hardware.TriggerEventListener;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ TriggerEventListener f$1;
    public final /* synthetic */ Sensor f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda6(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncSensorManager;
        this.f$1 = triggerEventListener;
        this.f$2 = sensor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$cancelTriggerSensorImpl$4(this.f$1, this.f$2);
                break;
            default:
                this.f$0.lambda$requestTriggerSensorImpl$3(this.f$1, this.f$2);
                break;
        }
    }
}
