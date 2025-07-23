package com.android.systemui.statusbar.notification.collection;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EntryWithDismissStats {
    public final NotificationEntry entry;
    public final int entryHashCode;
    public final String key;
    public final DismissedByUserStats stats;

    public EntryWithDismissStats(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, String str, int i) {
        this.entry = notificationEntry;
        this.stats = dismissedByUserStats;
        this.key = str;
        this.entryHashCode = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntryWithDismissStats)) {
            return false;
        }
        EntryWithDismissStats entryWithDismissStats = (EntryWithDismissStats) obj;
        return Intrinsics.areEqual(this.entry, entryWithDismissStats.entry) && Intrinsics.areEqual(this.stats, entryWithDismissStats.stats) && Intrinsics.areEqual(this.key, entryWithDismissStats.key) && this.entryHashCode == entryWithDismissStats.entryHashCode;
    }

    public final int hashCode() {
        NotificationEntry notificationEntry = this.entry;
        return Integer.hashCode(this.entryHashCode) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.stats.hashCode() + ((notificationEntry == null ? 0 : notificationEntry.hashCode()) * 31)) * 31, 31, this.key);
    }

    public final String toString() {
        return "EntryWithDismissStats(entry=" + this.entry + ", stats=" + this.stats + ", key=" + this.key + ", entryHashCode=" + this.entryHashCode + ")";
    }
}
