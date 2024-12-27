package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.ListenerSet;

public class BindEventManager {
    public final ListenerSet listeners = new ListenerSet();

    public interface Listener {
        void onViewBound(NotificationEntry notificationEntry);
    }
}
