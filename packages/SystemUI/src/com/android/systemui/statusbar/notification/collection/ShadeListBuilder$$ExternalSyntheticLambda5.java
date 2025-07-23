package com.android.systemui.statusbar.notification.collection;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda5 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        GroupEntry groupEntry = (GroupEntry) obj;
        int i = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
        return groupEntry.mSummary == null && groupEntry.mUnmodifiableChildren.isEmpty();
    }
}
