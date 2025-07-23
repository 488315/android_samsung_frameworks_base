package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationInteractionTracker implements NotifCollectionListener {
    public final Map interactions = new LinkedHashMap();

    public NotificationInteractionTracker(NotificationClickNotifier notificationClickNotifier) {
        notificationClickNotifier.getClass();
        Assert.isMainThread();
        ((ArrayList) notificationClickNotifier.listeners).add(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryAdded(NotificationEntry notificationEntry) {
        this.interactions.put(notificationEntry.mKey, Boolean.FALSE);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryCleanUp(NotificationEntry notificationEntry) {
        this.interactions.remove(notificationEntry.mKey);
    }
}
