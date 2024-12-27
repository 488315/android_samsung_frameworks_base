package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ActivatableNotificationViewBinder$registerListenersWhileAttached$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivatableNotificationViewBinder this$0;

    public ActivatableNotificationViewBinder$registerListenersWhileAttached$1(ActivatableNotificationViewBinder activatableNotificationViewBinder, Continuation continuation) {
        super(continuation);
        this.this$0 = activatableNotificationViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ActivatableNotificationViewBinder.access$registerListenersWhileAttached(this.this$0, null, null, this);
    }
}
