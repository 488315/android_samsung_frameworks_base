package com.android.systemui.statusbar.notification.stack.ui.view;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class NotificationStatsLoggerImpl$consumeVisibilityActions$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationStatsLoggerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStatsLoggerImpl$consumeVisibilityActions$1(NotificationStatsLoggerImpl notificationStatsLoggerImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = notificationStatsLoggerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationStatsLoggerImpl.access$consumeVisibilityActions(this.this$0, this);
    }
}
