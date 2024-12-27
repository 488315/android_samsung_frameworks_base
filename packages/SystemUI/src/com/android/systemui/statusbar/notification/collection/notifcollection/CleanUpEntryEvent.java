package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

public final class CleanUpEntryEvent extends NotifEvent {
    public final NotificationEntry entry;

    public CleanUpEntryEvent(NotificationEntry notificationEntry) {
        super("onEntryCleanUp", null);
        this.entry = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryCleanUp(this.entry);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CleanUpEntryEvent) && Intrinsics.areEqual(this.entry, ((CleanUpEntryEvent) obj).entry);
    }

    public final int hashCode() {
        return this.entry.hashCode();
    }

    public final String toString() {
        return "CleanUpEntryEvent(entry=" + this.entry + ")";
    }
}
