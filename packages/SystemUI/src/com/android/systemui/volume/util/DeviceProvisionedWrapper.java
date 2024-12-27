package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DeviceProvisionedWrapper {
    public final DeviceProvisionedController deviceProvisionedController = (DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class);
    public final LogWrapper logWrapper;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DeviceProvisionedWrapper(LogWrapper logWrapper) {
        this.logWrapper = logWrapper;
    }

    public final boolean isDeviceProvisioned() {
        boolean z = ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get();
        this.logWrapper.d("DeviceProvisionedWrapper", "isDeviceProvisioned() : " + z);
        return z;
    }
}
