package com.samsung.android.knox;

import android.os.storage.StorageVolume;

/* loaded from: classes4.dex */
public interface ExternalDependencyInjector {
    default String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return null;
    }
}
