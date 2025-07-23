package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardServiceShowLockscreenInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardServiceShowLockscreenInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardServiceShowLockscreenInteractor$start$1(KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardServiceShowLockscreenInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardServiceShowLockscreenInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardServiceShowLockscreenInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = ((WindowManagerLockscreenVisibilityInteractor) this.this$0.wmLockscreenVisibilityInteractor.get()).lockscreenVisibility;
            final KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardServiceShowLockscreenInteractor$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (((Boolean) obj2).booleanValue()) {
                        KeyguardServiceShowLockscreenInteractor.access$notifyShowLockscreenCallbacks(KeyguardServiceShowLockscreenInteractor.this);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
