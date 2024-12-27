package com.android.systemui.util.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda4(AsyncSensorManager asyncSensorManager, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncSensorManager;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$unregisterListenerImpl$8((Sensor) this.f$1, (SensorEventListener) this.f$2);
                break;
            default:
                this.f$0.lambda$registerDynamicSensorCallbackImpl$1((SensorManager.DynamicSensorCallback) this.f$1, (Handler) this.f$2);
                break;
        }
    }
}
