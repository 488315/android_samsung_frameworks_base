package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class NotificationStackSizeCalculator$showableChildren$1 extends Lambda implements Function1 {
    final /* synthetic */ NotificationStackSizeCalculator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStackSizeCalculator$showableChildren$1(NotificationStackSizeCalculator notificationStackSizeCalculator) {
        super(1);
        this.this$0 = notificationStackSizeCalculator;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Boolean.valueOf(NotificationStackSizeCalculator.isShowable((ExpandableView) obj, this.this$0.onLockscreen()));
    }
}
