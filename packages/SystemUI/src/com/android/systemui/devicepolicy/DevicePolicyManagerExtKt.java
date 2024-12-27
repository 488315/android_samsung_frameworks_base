package com.android.systemui.devicepolicy;

import android.app.admin.DevicePolicyManager;

public abstract class DevicePolicyManagerExtKt {
    public static boolean areKeyguardShortcutsDisabled$default(DevicePolicyManager devicePolicyManager, int i) {
        int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null, i);
        return (keyguardDisabledFeatures & 512) == 512 || (keyguardDisabledFeatures & Integer.MAX_VALUE) == Integer.MAX_VALUE;
    }
}
