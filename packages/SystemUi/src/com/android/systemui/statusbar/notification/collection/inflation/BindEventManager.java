package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BindEventManager {
    public final ListenerSet listeners = new ListenerSet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
        void onViewBound(NotificationEntry notificationEntry);
    }
}
