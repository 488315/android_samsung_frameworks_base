package com.android.server.enterprise.adapterlayer;

import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import com.android.server.enterprise.adapter.IStorageManagerAdapter;

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
