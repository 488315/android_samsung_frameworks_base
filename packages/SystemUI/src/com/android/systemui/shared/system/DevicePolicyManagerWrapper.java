package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DevicePolicyManagerWrapper {
    public static final DevicePolicyManagerWrapper sInstance = new DevicePolicyManagerWrapper();
    public static final DevicePolicyManager sDevicePolicyManager = (DevicePolicyManager) AppGlobals.getInitialApplication().getSystemService(DevicePolicyManager.class);

    private DevicePolicyManagerWrapper() {
    }
}
