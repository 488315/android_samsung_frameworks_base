package com.android.systemui.devicepolicy;

import android.app.admin.DevicePolicyManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DevicePolicyManagerExtKt {
    public static boolean areKeyguardShortcutsDisabled$default(DevicePolicyManager devicePolicyManager, int i) {
        int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null, i);
        return (keyguardDisabledFeatures & 512) == 512 || (keyguardDisabledFeatures & Integer.MAX_VALUE) == Integer.MAX_VALUE;
    }
}
