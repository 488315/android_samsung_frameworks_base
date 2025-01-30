package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceProvisionedWrapper {
    public final DeviceProvisionedController deviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    public final LogWrapper logWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        boolean isDeviceProvisioned = ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).isDeviceProvisioned();
        this.logWrapper.m98d("DeviceProvisionedWrapper", "isDeviceProvisioned() : " + isDeviceProvisioned);
        return isDeviceProvisioned;
    }
}
