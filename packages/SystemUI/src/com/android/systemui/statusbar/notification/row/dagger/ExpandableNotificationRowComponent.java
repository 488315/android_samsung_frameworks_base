package com.android.systemui.statusbar.notification.row.dagger;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;

public interface ExpandableNotificationRowComponent {

    public interface Builder {
    }

    ExpandableNotificationRowController getExpandableNotificationRowController();
}
