package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.graphics.drawable.Icon;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;

public abstract class NotificationIconsViewDataKt {
    public static final NotificationIconInfo toIconInfo(ActiveNotificationModel activeNotificationModel, Icon icon) {
        String str;
        if (icon == null || (str = activeNotificationModel.groupKey) == null) {
            return null;
        }
        return new NotificationIconInfo(icon, activeNotificationModel.key, str);
    }
}
