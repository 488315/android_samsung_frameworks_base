package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class NotificationStackSizeCalculator$showableChildren$1 extends Lambda implements Function1 {
    final /* synthetic */ NotificationStackSizeCalculator this$0;

    public NotificationStackSizeCalculator$showableChildren$1(NotificationStackSizeCalculator notificationStackSizeCalculator) {
        super(1);
        this.this$0 = notificationStackSizeCalculator;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Boolean.valueOf(NotificationStackSizeCalculator.isShowable((ExpandableView) obj, this.this$0.onLockscreen()));
    }
}
