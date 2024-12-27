package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationStackSizeCalculator$childrenSequence$1 extends Lambda implements Function1 {
    public static final NotificationStackSizeCalculator$childrenSequence$1 INSTANCE = new NotificationStackSizeCalculator$childrenSequence$1();

    public NotificationStackSizeCalculator$childrenSequence$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return (ExpandableView) ((View) obj);
    }
}
