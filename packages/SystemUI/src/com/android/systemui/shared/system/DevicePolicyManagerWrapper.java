package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;

/* loaded from: classes3.dex */
public class DevicePolicyManagerWrapper {
    public static final DevicePolicyManagerWrapper sInstance = new DevicePolicyManagerWrapper();
    public static final DevicePolicyManager sDevicePolicyManager = (DevicePolicyManager) AppGlobals.getInitialApplication().getSystemService(DevicePolicyManager.class);

    private DevicePolicyManagerWrapper() {
    }
}
