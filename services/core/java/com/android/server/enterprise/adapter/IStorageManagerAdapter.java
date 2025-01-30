package com.android.server.enterprise.adapter;

import android.os.storage.StorageVolume;
import java.util.List;

/* loaded from: classes2.dex */
public interface IStorageManagerAdapter extends IAdapterHandle {
  String getExternalSdCardPath();

  String getExternalSdCardState();

  String getInternalSdCardPath();

  StorageVolume[] getVolumeList();

  List getVolumes();
}
