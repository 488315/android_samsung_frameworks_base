package com.android.systemui.statusbar.notification.stack;

import android.widget.TextView;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationChildrenContainer$$ExternalSyntheticLambda4 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TextView textView = (TextView) obj;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
        return textView != null;
    }
}
