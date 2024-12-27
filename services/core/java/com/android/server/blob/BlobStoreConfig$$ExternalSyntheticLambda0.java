package com.android.server.blob;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
