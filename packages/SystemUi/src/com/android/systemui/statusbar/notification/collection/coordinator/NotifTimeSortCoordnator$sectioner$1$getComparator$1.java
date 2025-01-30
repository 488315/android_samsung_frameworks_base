package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifTimeSortCoordnator$sectioner$1$getComparator$1 extends NotifComparator {
    public final /* synthetic */ NotifTimeSortCoordnator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotifTimeSortCoordnator$sectioner$1$getComparator$1(NotifTimeSortCoordnator notifTimeSortCoordnator) {
        super("TimeOrder");
        this.this$0 = notifTimeSortCoordnator;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
    public final int compare(ListEntry listEntry, ListEntry listEntry2) {
        NotifTimeSortCoordnator notifTimeSortCoordnator = this.this$0;
        return Long.compare(notifTimeSortCoordnator.calculateRepresentativeNotificationTime(listEntry2), notifTimeSortCoordnator.calculateRepresentativeNotificationTime(listEntry));
    }
}
