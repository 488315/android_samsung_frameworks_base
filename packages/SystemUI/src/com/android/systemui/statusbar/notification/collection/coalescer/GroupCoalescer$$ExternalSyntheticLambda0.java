package com.android.systemui.statusbar.notification.collection.coalescer;

import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class GroupCoalescer$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        CoalescedEvent coalescedEvent2 = (CoalescedEvent) obj2;
        int compare = Boolean.compare(coalescedEvent2.sbn.getNotification().isGroupSummary(), coalescedEvent.sbn.getNotification().isGroupSummary());
        return compare == 0 ? coalescedEvent.position - coalescedEvent2.position : compare;
    }
}
