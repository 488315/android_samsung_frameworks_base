package com.samsung.android.knox;

import android.os.storage.StorageVolume;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExternalDependencyInjectorImpl implements ExternalDependencyInjector {
    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return storageVolume.getSubSystem();
    }
}
