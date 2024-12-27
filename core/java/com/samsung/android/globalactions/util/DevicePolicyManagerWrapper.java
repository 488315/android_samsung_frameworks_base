package com.samsung.android.globalactions.util;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

/* loaded from: classes6.dex */
public class DevicePolicyManagerWrapper {
    private final DevicePolicyManager mDevicePolicyManager;

    public DevicePolicyManagerWrapper(Context mContext) {
        this.mDevicePolicyManager =
                (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    boolean isEncryptionStatusActive() {
        return this.mDevicePolicyManager.getStorageEncryptionStatus() == 3
                || this.mDevicePolicyManager.getStorageEncryptionStatus() == 5;
    }

    boolean isLogoutEnabled() {
        return this.mDevicePolicyManager.isLogoutEnabled();
    }
}
