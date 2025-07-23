package com.android.systemui.util.sensors;

import com.android.systemui.plugins.SensorManagerPlugin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ SensorManagerPlugin.Sensor f$1;
    public final /* synthetic */ SensorManagerPlugin.SensorEventListener f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda0(AsyncSensorManager asyncSensorManager, SensorManagerPlugin.Sensor sensor, SensorManagerPlugin.SensorEventListener sensorEventListener, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncSensorManager;
        this.f$1 = sensor;
        this.f$2 = sensorEventListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$unregisterPluginListener$6(this.f$1, this.f$2);
                break;
            default:
                this.f$0.lambda$registerPluginListener$5(this.f$1, this.f$2);
                break;
        }
    }
}
