package android.companion.virtual.sensor;

import android.annotation.SystemApi;

import java.time.Duration;

@SystemApi
public interface VirtualSensorCallback {
    void onConfigurationChanged(
            VirtualSensor virtualSensor, boolean z, Duration duration, Duration duration2);
}
