package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class EntryUpdatedEvent extends NotifEvent {
    public final NotificationEntry entry;
    public final UpdateSource source;

    public EntryUpdatedEvent(NotificationEntry notificationEntry, UpdateSource updateSource) {
        super(updateSource == UpdateSource.SystemUi ? "onEntryUpdated" : "onEntryUpdated fromSystem=true", null);
        this.entry = notificationEntry;
        this.source = updateSource;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryUpdated(this.entry, this.source);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntryUpdatedEvent)) {
            return false;
        }
        EntryUpdatedEvent entryUpdatedEvent = (EntryUpdatedEvent) obj;
        return Intrinsics.areEqual(this.entry, entryUpdatedEvent.entry) && this.source == entryUpdatedEvent.source;
    }

    public final int hashCode() {
        return this.source.hashCode() + (this.entry.hashCode() * 31);
    }

    public final String toString() {
        return "EntryUpdatedEvent(entry=" + this.entry + ", source=" + this.source + ")";
    }
}
