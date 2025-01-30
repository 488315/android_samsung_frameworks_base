package com.android.systemui.statusbar.notification.collection.coalescer;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoalescedEvent {
    public EventBatch batch;
    public final String key;
    public final int position;
    public NotificationListenerService.Ranking ranking;
    public final StatusBarNotification sbn;

    public CoalescedEvent(String str, int i, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, EventBatch eventBatch) {
        this.key = str;
        this.position = i;
        this.sbn = statusBarNotification;
        this.ranking = ranking;
        this.batch = eventBatch;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoalescedEvent)) {
            return false;
        }
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        return Intrinsics.areEqual(this.key, coalescedEvent.key) && this.position == coalescedEvent.position && Intrinsics.areEqual(this.sbn, coalescedEvent.sbn) && Intrinsics.areEqual(this.ranking, coalescedEvent.ranking) && Intrinsics.areEqual(this.batch, coalescedEvent.batch);
    }

    public final int hashCode() {
        int hashCode = (this.ranking.hashCode() + ((this.sbn.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.position, this.key.hashCode() * 31, 31)) * 31)) * 31;
        EventBatch eventBatch = this.batch;
        return hashCode + (eventBatch == null ? 0 : eventBatch.hashCode());
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("CoalescedEvent(key="), this.key, ")");
    }
}
