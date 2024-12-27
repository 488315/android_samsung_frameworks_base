package com.android.server.enterprise;

import com.samsung.android.knox.IEnterpriseDeviceManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class EnterpriseDeviceManagerService extends IEnterpriseDeviceManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;

    public abstract int getOrganizationOwnedProfileUserId();

    public abstract void startDeferredServicesIfNeeded();
}
