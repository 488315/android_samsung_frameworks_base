package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewModel$isNotifIconContainerVisible$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public KeyguardRootViewModel$isNotifIconContainerVisible$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRootViewModel$isNotifIconContainerVisible$2 keyguardRootViewModel$isNotifIconContainerVisible$2 = new KeyguardRootViewModel$isNotifIconContainerVisible$2(continuation);
        keyguardRootViewModel$isNotifIconContainerVisible$2.L$0 = obj;
        return keyguardRootViewModel$isNotifIconContainerVisible$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRootViewModel$isNotifIconContainerVisible$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean bool = Boolean.FALSE;
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
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
