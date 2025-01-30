package com.samsung.android.knox;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.storage.StorageVolume;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ExternalDependencyInjectorImpl implements ExternalDependencyInjector {
    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public final Bundle getApplicationRestrictionsMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, String str, int i) {
        return iDevicePolicyManager.getApplicationRestrictionsMDM(componentName, str, i);
    }

    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public final void setApplicationRestrictionsMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, String str, Bundle bundle, int i) {
        iDevicePolicyManager.setApplicationRestrictionsMDM(componentName, str, bundle, i);
    }

    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public final String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return storageVolume.getSubSystem();
    }
}
