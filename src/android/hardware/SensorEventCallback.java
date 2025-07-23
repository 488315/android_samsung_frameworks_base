package android.hardware;

/* loaded from: classes2.dex */
public abstract class SensorEventCallback implements SensorEventListener2 {
    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener2
    public void onFlushCompleted(Sensor sensor) {
    }

    public void onSensorAdditionalInfo(SensorAdditionalInfo sensorAdditionalInfo) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
    }
}
