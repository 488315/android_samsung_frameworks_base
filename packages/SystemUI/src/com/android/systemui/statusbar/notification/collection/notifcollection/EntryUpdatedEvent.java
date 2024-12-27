package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EntryUpdatedEvent extends NotifEvent {
    public final NotificationEntry entry;
    public final boolean fromSystem;

    public EntryUpdatedEvent(NotificationEntry notificationEntry, boolean z) {
        super(z ? "onEntryUpdated" : "onEntryUpdated fromSystem=true", null);
        this.entry = notificationEntry;
        this.fromSystem = z;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryUpdated(this.entry, this.fromSystem);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntryUpdatedEvent)) {
            return false;
        }
        EntryUpdatedEvent entryUpdatedEvent = (EntryUpdatedEvent) obj;
        return Intrinsics.areEqual(this.entry, entryUpdatedEvent.entry) && this.fromSystem == entryUpdatedEvent.fromSystem;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.fromSystem) + (this.entry.hashCode() * 31);
    }

    public final String toString() {
        return "EntryUpdatedEvent(entry=" + this.entry + ", fromSystem=" + this.fromSystem + ")";
    }
}
