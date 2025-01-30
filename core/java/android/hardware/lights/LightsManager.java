package android.hardware.lights;

import android.annotation.SystemApi;
import android.p009os.Binder;
import android.p009os.IBinder;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class LightsManager {

  @SystemApi @Deprecated public static final int LIGHT_TYPE_MICROPHONE = 8;
  private static final String TAG = "LightsManager";

  @Retention(RetentionPolicy.SOURCE)
  public @interface LightType {}

  public abstract LightState getLightState(Light light);

  public abstract List<Light> getLights();

  public abstract LightsSession openSession();

  public abstract LightsSession openSession(int i);

  public abstract static class LightsSession implements AutoCloseable {
    private final IBinder mToken = new Binder();

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract void requestLights(LightsRequest lightsRequest);

    public IBinder getToken() {
      return this.mToken;
    }
  }
}
