package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class BindEventManager {
    public final ListenerSet listeners = new ListenerSet();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Listener {
        void onViewBound(NotificationEntry notificationEntry);
    }
}
