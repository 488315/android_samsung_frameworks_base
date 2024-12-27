package com.android.systemui.statusbar.notification.row;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final /* synthetic */ class NotificationInlineImageCache$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Set f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !this.f$0.contains(((Map.Entry) obj).getKey());
    }
}
