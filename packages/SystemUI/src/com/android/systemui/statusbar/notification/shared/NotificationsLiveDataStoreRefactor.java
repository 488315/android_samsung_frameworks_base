package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.flags.RefactorFlagUtils;

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
