package com.android.systemui.biometrics;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class UdfpsControllerOverlay$addViewNowOrLater$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsControllerOverlay this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsControllerOverlay$addViewNowOrLater$2(UdfpsControllerOverlay udfpsControllerOverlay, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsControllerOverlay;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsControllerOverlay$addViewNowOrLater$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsControllerOverlay$addViewNowOrLater$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final UdfpsControllerOverlay udfpsControllerOverlay = this.this$0;
            UdfpsControllerOverlay$special$$inlined$map$1 udfpsControllerOverlay$special$$inlined$map$1 = udfpsControllerOverlay.currentStateUpdatedToOffAodOrDozing;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$addViewNowOrLater$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                    UdfpsControllerOverlay$addViewNowOrLater$1 udfpsControllerOverlay$addViewNowOrLater$1 = udfpsControllerOverlay2.addViewRunnable;
                    if (udfpsControllerOverlay$addViewNowOrLater$1 != null) {
                        StandaloneCoroutine standaloneCoroutine = udfpsControllerOverlay2.listenForCurrentKeyguardState;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        udfpsControllerOverlay$addViewNowOrLater$1.run();
                    }
                    udfpsControllerOverlay2.addViewRunnable = null;
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (udfpsControllerOverlay$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
