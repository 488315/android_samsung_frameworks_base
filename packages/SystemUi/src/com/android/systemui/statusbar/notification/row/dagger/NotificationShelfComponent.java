package com.android.systemui.statusbar.notification.row.dagger;

import com.android.systemui.statusbar.LegacyNotificationShelfControllerImpl;
import com.android.systemui.statusbar.NotificationShelf;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotificationShelfComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        NotificationShelfComponent build();

        Builder notificationShelf(NotificationShelf notificationShelf);
    }

    LegacyNotificationShelfControllerImpl getNotificationShelfController();
}
