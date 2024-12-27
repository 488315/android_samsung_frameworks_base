package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.graphics.drawable.Icon;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotificationIconsViewDataKt {
    public static final NotificationIconInfo toIconInfo(ActiveNotificationModel activeNotificationModel, Icon icon) {
        String str;
        if (icon == null || (str = activeNotificationModel.groupKey) == null) {
            return null;
        }
        return new NotificationIconInfo(icon, activeNotificationModel.key, str);
    }
}
