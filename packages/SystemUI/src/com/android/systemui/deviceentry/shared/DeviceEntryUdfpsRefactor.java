package com.android.systemui.deviceentry.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceEntryUdfpsRefactor {
    public static final DeviceEntryUdfpsRefactor INSTANCE = new DeviceEntryUdfpsRefactor();

    private DeviceEntryUdfpsRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.device_entry_udfps_refactor is enabled.".toString());
    }
}
