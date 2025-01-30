package com.android.systemui.statusbar.notification.collection.coalescer;

import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class GroupCoalescer$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        CoalescedEvent coalescedEvent2 = (CoalescedEvent) obj2;
        int compare = Boolean.compare(coalescedEvent2.sbn.getNotification().isGroupSummary(), coalescedEvent.sbn.getNotification().isGroupSummary());
        return compare == 0 ? coalescedEvent.position - coalescedEvent2.position : compare;
    }
}
