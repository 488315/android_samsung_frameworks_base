package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

public final class NotificationsHeadsUpRefactor {
    public static final NotificationsHeadsUpRefactor INSTANCE = new NotificationsHeadsUpRefactor();

    private NotificationsHeadsUpRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
    }
}
