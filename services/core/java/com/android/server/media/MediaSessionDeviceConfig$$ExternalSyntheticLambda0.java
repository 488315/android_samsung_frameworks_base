package com.android.server.media;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaSessionDeviceConfig$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        properties.getKeyset();
        properties.getKeyset().forEach(new MediaSessionDeviceConfig$$ExternalSyntheticLambda1(properties));
    }
}
