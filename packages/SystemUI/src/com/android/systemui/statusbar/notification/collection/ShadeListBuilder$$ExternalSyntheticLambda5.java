package com.android.systemui.statusbar.notification.collection;

import java.util.function.Predicate;

public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda5 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        GroupEntry groupEntry = (GroupEntry) obj;
        return groupEntry.mSummary == null && groupEntry.mUnmodifiableChildren.isEmpty();
    }
}
