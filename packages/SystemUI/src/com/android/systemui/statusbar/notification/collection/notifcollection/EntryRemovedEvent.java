package com.android.systemui.statusbar.notification.collection.notifcollection;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EntryRemovedEvent extends NotifEvent {
    public final NotificationEntry entry;
    public final int reason;

    public EntryRemovedEvent(NotificationEntry notificationEntry, int i) {
        super(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onEntryRemoved ", NotifCollectionLoggerKt.cancellationReasonDebugString(i)), null);
        this.entry = notificationEntry;
        this.reason = i;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onEntryRemoved(this.entry, this.reason);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntryRemovedEvent)) {
            return false;
        }
        EntryRemovedEvent entryRemovedEvent = (EntryRemovedEvent) obj;
        return Intrinsics.areEqual(this.entry, entryRemovedEvent.entry) && this.reason == entryRemovedEvent.reason;
    }

    public final int hashCode() {
        return Integer.hashCode(this.reason) + (this.entry.hashCode() * 31);
    }

    public final String toString() {
        return "EntryRemovedEvent(entry=" + this.entry + ", reason=" + this.reason + ")";
    }
}
