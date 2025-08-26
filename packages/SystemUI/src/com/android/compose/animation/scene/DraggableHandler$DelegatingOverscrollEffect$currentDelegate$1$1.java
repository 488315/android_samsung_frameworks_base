package com.android.compose.animation.scene;

import com.android.compose.animation.scene.effect.GestureEffect;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ GestureEffect $delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1(GestureEffect gestureEffect, Continuation continuation) {
        super(2, continuation);
        this.$delegate = gestureEffect;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1(this.$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            GestureEffect gestureEffect = this.$delegate;
            this.label = 1;
            if (gestureEffect.ensureApplyToFlingIsCalled(this) == coroutineSingletons) {
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
