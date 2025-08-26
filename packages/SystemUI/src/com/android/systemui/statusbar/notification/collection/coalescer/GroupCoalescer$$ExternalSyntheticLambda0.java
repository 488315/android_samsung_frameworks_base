package com.android.systemui.statusbar.notification.collection.coalescer;

import java.util.Comparator;

/* loaded from: classes3.dex */
public final /* synthetic */ class GroupCoalescer$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        CoalescedEvent coalescedEvent2 = (CoalescedEvent) obj2;
        int iCompare = Boolean.compare(coalescedEvent2.sbn.getNotification().isGroupSummary(), coalescedEvent.sbn.getNotification().isGroupSummary());
        return iCompare == 0 ? coalescedEvent.position - coalescedEvent2.position : iCompare;
    }
}
