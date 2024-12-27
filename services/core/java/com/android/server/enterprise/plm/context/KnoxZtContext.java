package com.android.server.enterprise.plm.context;

import android.app.admin.DevicePolicyManager;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.ProcessStateTracker;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxZtContext extends ProcessContext {
    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getDisplayName() {
        return "KnoxZtService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getPackageName() {
        return EndpointMonitorImpl.KZT_FW_PKG_NAME;
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getServiceName() {
        return "com.samsung.android.knox.zt.framework.core.KnoxZtService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final boolean needToKeepProcessAlive(IStateDelegate iStateDelegate) {
        ProcessStateTracker processStateTracker = (ProcessStateTracker) iStateDelegate;
        boolean isDeviceManaged =
                ((DevicePolicyManager)
                                processStateTracker.mSystemStateTracker.mContext.getSystemService(
                                        "device_policy"))
                        .isDeviceManaged();
        boolean isOrganizationOwnedDeviceWithManagedProfile =
                ((DevicePolicyManager)
                                processStateTracker.mSystemStateTracker.mContext.getSystemService(
                                        "device_policy"))
                        .isOrganizationOwnedDeviceWithManagedProfile();
        boolean isKlmActivated = processStateTracker.isKlmActivated();
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "KnoxZtContext",
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "is device managed: ",
                        isDeviceManaged,
                        ", is device organization owned with managed profile: ",
                        isOrganizationOwnedDeviceWithManagedProfile,
                        ", is klm activated: "),
                isKlmActivated);
        boolean z =
                isDeviceManaged || isOrganizationOwnedDeviceWithManagedProfile || isKlmActivated;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "keep alive ", "KnoxZtContext", z);
        return z;
    }
}
