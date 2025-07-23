package com.android.systemui.notifications.ui.composable;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NotificationsKt$scrollNotificationStack$1 extends ContinuationImpl {
    float F$0;
    float F$1;
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;

    public NotificationsKt$scrollNotificationStack$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationsKt.access$scrollNotificationStack(0.0f, false, null, null, null, this);
    }
}
