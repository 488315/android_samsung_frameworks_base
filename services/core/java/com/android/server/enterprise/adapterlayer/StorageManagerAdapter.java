package com.android.server.enterprise.adapterlayer;

import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import java.util.List;

/* loaded from: classes2.dex */
public class StorageManagerAdapter implements IStorageManagerAdapter {
  public static Context mContext;
  public static StorageManagerAdapter mInstance;
  public static StorageManager mStorageManager;

  public static synchronized StorageManagerAdapter getInstance(Context context) {
    StorageManagerAdapter storageManagerAdapter;
    synchronized (StorageManagerAdapter.class) {
      if (mInstance == null) {
        mContext = context;
        mInstance = new StorageManagerAdapter();
        mStorageManager = (StorageManager) mContext.getSystemService("storage");
      }
      storageManagerAdapter = mInstance;
    }
    return storageManagerAdapter;
  }

  @Override // com.android.server.enterprise.adapter.IStorageManagerAdapter
  public String getExternalSdCardState() {
    String externalSdCardPath = getExternalSdCardPath();
    if (externalSdCardPath == null) {
      return null;
    }
    return mStorageManager.getVolumeState(externalSdCardPath);
  }

  @Override // com.android.server.enterprise.adapter.IStorageManagerAdapter
  public String getExternalSdCardPath() {
    StorageVolume[] volumeList = mStorageManager.getVolumeList();
    if (volumeList == null || volumeList.length <= 1 || volumeList[1].getPath() == null) {
      return null;
    }
    StorageVolume storageVolume = volumeList[1];
    Log.d("StorageManagerAdapter", "Subsystem : " + storageVolume.getSubSystem());
    Log.d("StorageManagerAdapter", "Path : " + storageVolume.getPath());
    return storageVolume.getPath();
  }

  @Override // com.android.server.enterprise.adapter.IStorageManagerAdapter
  public String getInternalSdCardPath() {
    StorageVolume[] volumeList = mStorageManager.getVolumeList();
    if (volumeList == null || volumeList.length <= 0 || volumeList[0].getPath() == null) {
      return null;
    }
    StorageVolume storageVolume = volumeList[0];
    return storageVolume.getSubSystem().equals("fuse") ? "/" : storageVolume.getPath();
  }

  @Override // com.android.server.enterprise.adapter.IStorageManagerAdapter
  public StorageVolume[] getVolumeList() {
    return mStorageManager.getVolumeList();
  }

  @Override // com.android.server.enterprise.adapter.IStorageManagerAdapter
  public List getVolumes() {
    return mStorageManager.getVolumes();
  }
}
