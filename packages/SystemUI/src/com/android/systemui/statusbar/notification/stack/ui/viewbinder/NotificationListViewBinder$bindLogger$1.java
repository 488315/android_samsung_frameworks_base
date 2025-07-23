package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationListViewBinder$bindLogger$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$bindLogger$1(NotificationListViewBinder notificationListViewBinder, Continuation continuation) {
        super(continuation);
        this.this$0 = notificationListViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationListViewBinder.access$bindLogger(this.this$0, null, this);
    }
}
