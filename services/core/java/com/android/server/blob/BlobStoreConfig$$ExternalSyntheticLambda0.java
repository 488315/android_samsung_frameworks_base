package com.android.server.blob;

import android.provider.DeviceConfig;

public final /* synthetic */ class BlobStoreConfig$$ExternalSyntheticLambda0
        implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        float f = BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FRACTION;
        if ("blobstore".equals(properties.getNamespace())) {
            properties
                    .getKeyset()
                    .forEach(
                            new BlobStoreConfig$DeviceConfigProperties$$ExternalSyntheticLambda0(
                                    properties));
        }
    }
}
