package com.samsung.android.knox;

import android.os.storage.StorageVolume;

/* loaded from: classes4.dex */
public class ExternalDependencyInjectorImpl implements ExternalDependencyInjector {
    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return storageVolume.getSubSystem();
    }
}
