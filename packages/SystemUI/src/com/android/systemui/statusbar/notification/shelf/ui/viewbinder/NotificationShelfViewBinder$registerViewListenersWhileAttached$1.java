package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class NotificationShelfViewBinder$registerViewListenersWhileAttached$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationShelfViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$registerViewListenersWhileAttached$1(NotificationShelfViewBinder notificationShelfViewBinder, Continuation continuation) {
        super(continuation);
        this.this$0 = notificationShelfViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationShelfViewBinder.access$registerViewListenersWhileAttached(this.this$0, null, null, this);
    }
}
