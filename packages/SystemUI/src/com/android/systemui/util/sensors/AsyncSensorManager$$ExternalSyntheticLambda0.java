package com.android.systemui.util.sensors;

import com.android.systemui.plugins.SensorManagerPlugin;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
