package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

public final class EntryAddedEvent extends NotifEvent {
    public final NotificationEntry entry;

    public EntryAddedEvent(NotificationEntry notificationEntry) {
        super("onEntryAdded", null);
        this.entry = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryAdded(this.entry);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EntryAddedEvent) && Intrinsics.areEqual(this.entry, ((EntryAddedEvent) obj).entry);
    }

    public final int hashCode() {
        return this.entry.hashCode();
    }

    public final String toString() {
        return "EntryAddedEvent(entry=" + this.entry + ")";
    }
}
