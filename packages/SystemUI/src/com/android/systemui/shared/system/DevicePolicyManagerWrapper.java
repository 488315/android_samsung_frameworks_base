package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;

public final class DevicePolicyManagerWrapper {
    public static final DevicePolicyManagerWrapper sInstance = new DevicePolicyManagerWrapper();
    public static final DevicePolicyManager sDevicePolicyManager = (DevicePolicyManager) AppGlobals.getInitialApplication().getSystemService(DevicePolicyManager.class);

    private DevicePolicyManagerWrapper() {
    }
}
