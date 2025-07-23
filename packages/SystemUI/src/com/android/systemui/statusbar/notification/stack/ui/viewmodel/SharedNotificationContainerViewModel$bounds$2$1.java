package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$bounds$2$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public SharedNotificationContainerViewModel$bounds$2$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$bounds$2$1 sharedNotificationContainerViewModel$bounds$2$1 = new SharedNotificationContainerViewModel$bounds$2$1(continuation);
        sharedNotificationContainerViewModel$bounds$2$1.L$0 = obj;
        return sharedNotificationContainerViewModel$bounds$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$bounds$2$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Triple triple = new Triple(new Float(0.0f), Boolean.FALSE, new Float(0.0f));
            this.label = 1;
            if (flowCollector.emit(triple, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
