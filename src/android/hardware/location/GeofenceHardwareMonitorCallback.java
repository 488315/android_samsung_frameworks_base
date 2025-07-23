package android.hardware.location;

import android.annotation.SystemApi;
import android.location.Location;

@SystemApi
/* loaded from: classes2.dex */
public abstract class GeofenceHardwareMonitorCallback {
    @Deprecated
    public void onMonitoringSystemChange(int i, boolean z, Location location) {
    }

    public void onMonitoringSystemChange(GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) {
    }
}
