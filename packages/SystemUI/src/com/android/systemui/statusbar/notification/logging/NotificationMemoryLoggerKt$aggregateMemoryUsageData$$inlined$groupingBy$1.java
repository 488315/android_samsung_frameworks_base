package com.android.systemui.statusbar.notification.logging;

import java.util.Iterator;
import kotlin.Pair;
import kotlin.collections.Grouping;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1 implements Grouping {
    public final /* synthetic */ Iterable $this_groupingBy;

    public NotificationMemoryLoggerKt$aggregateMemoryUsageData$$inlined$groupingBy$1(Iterable iterable) {
        this.$this_groupingBy = iterable;
    }

    @Override // kotlin.collections.Grouping
    public final Object keyOf(Object obj) {
        NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj;
        return new Pair(notificationMemoryUsage.packageName, Integer.valueOf(notificationMemoryUsage.objectUsage.style));
    }

    @Override // kotlin.collections.Grouping
    public final Iterator sourceIterator() {
        return this.$this_groupingBy.iterator();
    }
}
