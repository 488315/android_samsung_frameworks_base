package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardInteractor$topClippingBounds$2$3 extends SuspendLambda implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardInteractor$topClippingBounds$2$3(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        KeyguardInteractor$topClippingBounds$2$3 keyguardInteractor$topClippingBounds$2$3 = new KeyguardInteractor$topClippingBounds$2$3((Continuation) obj4);
        keyguardInteractor$topClippingBounds$2$3.L$0 = (FlowCollector) obj;
        keyguardInteractor$topClippingBounds$2$3.Z$0 = booleanValue;
        keyguardInteractor$topClippingBounds$2$3.L$1 = (Integer) obj3;
        return keyguardInteractor$topClippingBounds$2$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            boolean z = this.Z$0;
            Integer num = (Integer) this.L$1;
            if (!z) {
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(num, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
