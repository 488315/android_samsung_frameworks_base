package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsLiveDataStoreRefactor {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new NotificationsLiveDataStoreRefactor();
    }

    private NotificationsLiveDataStoreRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.notifications_live_data_store_refactor is enabled.".toString());
    }
}
