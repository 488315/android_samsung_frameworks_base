package com.android.server.enterprise;

import com.samsung.android.knox.IEnterpriseDeviceManager;

public abstract class EnterpriseDeviceManagerService extends IEnterpriseDeviceManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;

    public abstract int getOrganizationOwnedProfileUserId();

    public abstract void startDeferredServicesIfNeeded();
}
