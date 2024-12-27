package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
