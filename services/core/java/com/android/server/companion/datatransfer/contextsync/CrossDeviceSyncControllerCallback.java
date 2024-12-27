package com.android.server.companion.datatransfer.contextsync;

import android.companion.AssociationInfo;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CrossDeviceSyncControllerCallback {
    public void cleanUpCallIds(Set set) {}

    public abstract void processContextSyncMessage(
            int i, CallMetadataSyncData callMetadataSyncData);

    public void requestCrossDeviceSync(AssociationInfo associationInfo) {}

    public void updateNumberOfActiveSyncAssociations(int i, boolean z) {}
}
