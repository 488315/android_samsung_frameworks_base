package com.android.server.sensors;

import android.os.ParcelFileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface SensorManagerInternal$RuntimeSensorCallback {
    int onConfigurationChanged(int i, boolean z, int i2, int i3);

    int onDirectChannelConfigured(int i, int i2, int i3);

    int onDirectChannelCreated(ParcelFileDescriptor parcelFileDescriptor);

    void onDirectChannelDestroyed(int i);
}
