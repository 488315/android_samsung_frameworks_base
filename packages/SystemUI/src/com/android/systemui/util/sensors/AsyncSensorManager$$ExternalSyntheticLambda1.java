package com.android.systemui.util.sensors;

import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda1(AsyncSensorManager asyncSensorManager, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncSensorManager;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$setOperationParameterImpl$7((SensorAdditionalInfo) this.f$1);
                break;
            default:
                this.f$0.lambda$unregisterDynamicSensorCallbackImpl$2((SensorManager.DynamicSensorCallback) this.f$1);
                break;
        }
    }
}
