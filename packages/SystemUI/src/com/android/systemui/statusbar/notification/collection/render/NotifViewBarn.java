package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.LinkedHashMap;
import java.util.Map;

public final class NotifViewBarn {
    public final Map rowMap = new LinkedHashMap();

    public final NotifViewController requireNodeController(ListEntry listEntry) {
        NotifViewController notifViewController = (NotifViewController) ((LinkedHashMap) this.rowMap).get(listEntry.getKey());
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + listEntry.getKey()).toString());
    }
}
