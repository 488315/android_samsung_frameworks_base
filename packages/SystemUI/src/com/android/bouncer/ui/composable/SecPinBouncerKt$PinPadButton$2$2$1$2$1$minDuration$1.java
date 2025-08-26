package com.android.bouncer.ui.composable;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes.dex */
final class SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1 extends SuspendLambda implements Function2 {
    int label;

    public SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return new SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long jM3461plusLRDsOJo = Duration.m3461plusLRDsOJo(SecPinBouncerKt.pinButtonPressedDuration, SecPinBouncerKt.pinButtonHoldTime);
            this.label = 1;
            if (DelayKt.m3469delayVtjQ1oo(jM3461plusLRDsOJo, this) == coroutineSingletons) {
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
