package com.android.systemui.statusbar.notification.stack;

import android.widget.TextView;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationChildrenContainer$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TextView textView = (TextView) obj;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
        return textView != null;
    }
}
