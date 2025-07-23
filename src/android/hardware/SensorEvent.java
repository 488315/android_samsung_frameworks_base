package android.hardware;

/* loaded from: classes2.dex */
public class SensorEvent {
    public int accuracy;
    public boolean firstEventAfterDiscontinuity;
    public Sensor sensor;
    public long timestamp;
    public final float[] values;

    SensorEvent(int i) {
        this.values = new float[i];
    }

    public SensorEvent(Sensor sensor, int i, long j, float[] fArr) {
        this.sensor = sensor;
        this.accuracy = i;
        this.timestamp = j;
        this.values = fArr;
    }
}
