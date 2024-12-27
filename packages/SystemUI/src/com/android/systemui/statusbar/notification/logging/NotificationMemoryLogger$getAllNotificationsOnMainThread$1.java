package com.android.systemui.statusbar.notification.logging;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class NotificationMemoryLogger$getAllNotificationsOnMainThread$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ NotificationMemoryLogger this$0;

    public NotificationMemoryLogger$getAllNotificationsOnMainThread$1(NotificationMemoryLogger notificationMemoryLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationMemoryLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationMemoryLogger$getAllNotificationsOnMainThread$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationMemoryLogger$getAllNotificationsOnMainThread$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NotificationMemoryLogger notificationMemoryLogger = this.this$0;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NML#getNotifications");
        }
        try {
            return CollectionsKt___CollectionsKt.toList(notificationMemoryLogger.notificationPipeline.getAllNotifs());
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
