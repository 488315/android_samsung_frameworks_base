package com.android.systemui.util.sensors;

import android.hardware.Sensor;
import android.hardware.TriggerEventListener;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ TriggerEventListener f$1;
    public final /* synthetic */ Sensor f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda3(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncSensorManager;
        this.f$1 = triggerEventListener;
        this.f$2 = sensor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AsyncSensorManager asyncSensorManager = this.f$0;
                TriggerEventListener triggerEventListener = this.f$1;
                Sensor sensor = this.f$2;
                if (!asyncSensorManager.mInner.cancelTriggerSensor(triggerEventListener, sensor)) {
                    Log.e("AsyncSensorManager", "Canceling " + triggerEventListener + " for " + sensor + " failed.");
                    break;
                }
                break;
            default:
                AsyncSensorManager asyncSensorManager2 = this.f$0;
                TriggerEventListener triggerEventListener2 = this.f$1;
                Sensor sensor2 = this.f$2;
                if (!asyncSensorManager2.mInner.requestTriggerSensor(triggerEventListener2, sensor2)) {
                    Log.e("AsyncSensorManager", "Requesting " + triggerEventListener2 + " for " + sensor2 + " failed.");
                    break;
                }
                break;
        }
    }
}
