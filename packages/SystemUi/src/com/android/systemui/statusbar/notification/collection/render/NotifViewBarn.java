package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
