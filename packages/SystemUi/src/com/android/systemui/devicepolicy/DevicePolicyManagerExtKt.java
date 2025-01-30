package com.android.systemui.devicepolicy;

import android.app.admin.DevicePolicyManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DevicePolicyManagerExtKt {
    public static boolean areKeyguardShortcutsDisabled$default(DevicePolicyManager devicePolicyManager, int i) {
        int keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null, i);
        return (keyguardDisabledFeatures & 512) == 512 || (keyguardDisabledFeatures & Integer.MAX_VALUE) == Integer.MAX_VALUE;
    }
}
