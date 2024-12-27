package com.android.server.enterprise.adapterlayer;

import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StorageManagerAdapter implements IStorageManagerAdapter {
    public static StorageManagerAdapter mInstance;
    public static StorageManager mStorageManager;

    public final String getExternalSdCardPath() {
        StorageVolume[] volumeList = mStorageManager.getVolumeList();
        if (volumeList == null || volumeList.length <= 1 || volumeList[1].getPath() == null) {
            return null;
        }
        StorageVolume storageVolume = volumeList[1];
        Log.d("StorageManagerAdapter", "Subsystem : " + storageVolume.getSubSystem());
        Log.d("StorageManagerAdapter", "Path : " + storageVolume.getPath());
        return storageVolume.getPath();
    }
}
