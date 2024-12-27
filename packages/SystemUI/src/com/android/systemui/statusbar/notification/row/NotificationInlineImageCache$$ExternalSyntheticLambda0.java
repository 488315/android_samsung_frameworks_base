package com.android.systemui.statusbar.notification.row;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationInlineImageCache$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Set f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !this.f$0.contains(((Map.Entry) obj).getKey());
    }
}
