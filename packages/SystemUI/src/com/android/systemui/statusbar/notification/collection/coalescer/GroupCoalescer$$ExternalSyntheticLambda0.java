package com.android.systemui.statusbar.notification.collection.coalescer;

import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class GroupCoalescer$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        CoalescedEvent coalescedEvent2 = (CoalescedEvent) obj2;
        int compare = Boolean.compare(coalescedEvent2.sbn.getNotification().isGroupSummary(), coalescedEvent.sbn.getNotification().isGroupSummary());
        return compare == 0 ? coalescedEvent.position - coalescedEvent2.position : compare;
    }
}
