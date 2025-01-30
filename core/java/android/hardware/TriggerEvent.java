package android.hardware;

/* loaded from: classes.dex */
public final class TriggerEvent {
  public Sensor sensor;
  public long timestamp;
  public final float[] values;

  TriggerEvent(int size) {
    this.values = new float[size];
  }
}
