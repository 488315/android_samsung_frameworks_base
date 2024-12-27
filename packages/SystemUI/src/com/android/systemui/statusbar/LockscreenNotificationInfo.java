package com.android.systemui.statusbar;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final class LockscreenNotificationInfo {
    public String mKey;
    public ExpandableNotificationRow mRow;
    public StatusBarNotification mSbn;
    public StatusBarIconView mStatusBarIcon;
}
