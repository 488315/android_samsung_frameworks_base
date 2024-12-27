package com.android.systemui.statusbar.notification.stack;

import android.widget.TextView;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Predicate;

public final /* synthetic */ class NotificationChildrenContainer$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TextView textView = (TextView) obj;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
        return textView != null;
    }
}
