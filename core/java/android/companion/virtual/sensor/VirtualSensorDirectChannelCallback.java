package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.p009os.SharedMemory;

@SystemApi
/* loaded from: classes.dex */
public interface VirtualSensorDirectChannelCallback {
  void onDirectChannelConfigured(int i, VirtualSensor virtualSensor, int i2, int i3);

  void onDirectChannelCreated(int i, SharedMemory sharedMemory);

  void onDirectChannelDestroyed(int i);
}
