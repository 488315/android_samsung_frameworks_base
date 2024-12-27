package com.android.server.companion.datatransfer.contextsync;

import android.companion.AssociationInfo;

import java.util.Set;

public abstract class CrossDeviceSyncControllerCallback {
    public void cleanUpCallIds(Set set) {}

    public abstract void processContextSyncMessage(
            int i, CallMetadataSyncData callMetadataSyncData);

    public void requestCrossDeviceSync(AssociationInfo associationInfo) {}

    public void updateNumberOfActiveSyncAssociations(int i, boolean z) {}
}
