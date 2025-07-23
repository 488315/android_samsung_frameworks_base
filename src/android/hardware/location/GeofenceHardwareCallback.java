package android.hardware.location;

import android.annotation.SystemApi;
import android.location.Location;

@SystemApi
/* loaded from: classes2.dex */
public abstract class GeofenceHardwareCallback {
    public void onGeofenceAdd(int i, int i2) {
    }

    public void onGeofencePause(int i, int i2) {
    }

    public void onGeofenceRemove(int i, int i2) {
    }

    public void onGeofenceResume(int i, int i2) {
    }

    public void onGeofenceTransition(int i, int i2, Location location, long j, int i3) {
    }
}
