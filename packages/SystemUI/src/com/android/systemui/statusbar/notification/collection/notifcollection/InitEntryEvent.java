package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

public final class InitEntryEvent extends NotifEvent {
    public final NotificationEntry entry;

    public InitEntryEvent(NotificationEntry notificationEntry) {
        super("onEntryInit", null);
        this.entry = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryInit(this.entry);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InitEntryEvent) && Intrinsics.areEqual(this.entry, ((InitEntryEvent) obj).entry);
    }

    public final int hashCode() {
        return this.entry.hashCode();
    }

    public final String toString() {
        return "InitEntryEvent(entry=" + this.entry + ")";
    }
}
