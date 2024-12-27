package com.android.server.sensors;

import android.os.ParcelFileDescriptor;

public interface SensorManagerInternal$RuntimeSensorCallback {
    int onConfigurationChanged(int i, boolean z, int i2, int i3);

    int onDirectChannelConfigured(int i, int i2, int i3);

    int onDirectChannelCreated(ParcelFileDescriptor parcelFileDescriptor);

    void onDirectChannelDestroyed(int i);
}
