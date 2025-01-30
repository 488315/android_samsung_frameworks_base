package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InitEntryEvent extends NotifEvent {
    public final NotificationEntry entry;

    public InitEntryEvent(NotificationEntry notificationEntry) {
        super(null);
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
