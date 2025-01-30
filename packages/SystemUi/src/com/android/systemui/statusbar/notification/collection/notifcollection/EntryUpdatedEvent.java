package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EntryUpdatedEvent extends NotifEvent {
    public final NotificationEntry entry;
    public final boolean fromSystem;

    public EntryUpdatedEvent(NotificationEntry notificationEntry, boolean z) {
        super(null);
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.entry.hashCode() * 31;
        boolean z = this.fromSystem;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "EntryUpdatedEvent(entry=" + this.entry + ", fromSystem=" + this.fromSystem + ")";
    }
}
