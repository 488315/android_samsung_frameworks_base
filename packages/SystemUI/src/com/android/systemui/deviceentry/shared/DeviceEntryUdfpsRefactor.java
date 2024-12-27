package com.android.systemui.deviceentry.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

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
