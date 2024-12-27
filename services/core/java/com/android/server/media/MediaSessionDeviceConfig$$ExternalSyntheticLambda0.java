package com.android.server.media;

import android.provider.DeviceConfig;

public final /* synthetic */ class MediaSessionDeviceConfig$$ExternalSyntheticLambda0
        implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        properties.getKeyset();
        properties
                .getKeyset()
                .forEach(new MediaSessionDeviceConfig$$ExternalSyntheticLambda1(properties));
    }
}
