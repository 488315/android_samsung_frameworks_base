package com.samsung.android.knox;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.storage.StorageVolume;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ExternalDependencyInjector {
    default Bundle getApplicationRestrictionsMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, String str, int i) {
        return null;
    }

    default String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return null;
    }

    default void setApplicationRestrictionsMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, String str, Bundle bundle, int i) {
    }
}
