package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class NotificationListViewBinder$bindLogger$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationListViewBinder this$0;

    public NotificationListViewBinder$bindLogger$1(NotificationListViewBinder notificationListViewBinder, Continuation continuation) {
        super(continuation);
        this.this$0 = notificationListViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationListViewBinder.access$bindLogger(this.this$0, this);
    }
}
