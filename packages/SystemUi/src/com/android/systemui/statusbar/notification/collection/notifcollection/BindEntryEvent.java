package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BindEntryEvent extends NotifEvent {
    public final NotificationEntry entry;
    public final StatusBarNotification sbn;

    public BindEntryEvent(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        super(null);
        this.entry = notificationEntry;
        this.sbn = statusBarNotification;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryBind(this.entry, this.sbn);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BindEntryEvent)) {
            return false;
        }
        BindEntryEvent bindEntryEvent = (BindEntryEvent) obj;
        return Intrinsics.areEqual(this.entry, bindEntryEvent.entry) && Intrinsics.areEqual(this.sbn, bindEntryEvent.sbn);
    }

    public final int hashCode() {
        return this.sbn.hashCode() + (this.entry.hashCode() * 31);
    }

    public final String toString() {
        return "BindEntryEvent(entry=" + this.entry + ", sbn=" + this.sbn + ")";
    }
}
