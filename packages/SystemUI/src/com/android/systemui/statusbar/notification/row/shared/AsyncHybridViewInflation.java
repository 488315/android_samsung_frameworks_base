package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

public final class AsyncHybridViewInflation {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new AsyncHybridViewInflation();
    }

    private AsyncHybridViewInflation() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.notificationAsyncHybridViewInflation();
    }
}
