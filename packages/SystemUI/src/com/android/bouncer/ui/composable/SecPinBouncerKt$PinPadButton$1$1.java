package com.android.bouncer.ui.composable;

import android.view.View;
import androidx.compose.runtime.MutableState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class SecPinBouncerKt$PinPadButton$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
    final /* synthetic */ View $view;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPinBouncerKt$PinPadButton$1$1(View view, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$view = view;
        this.$isPressed$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPinBouncerKt$PinPadButton$1$1(this.$view, this.$isPressed$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPinBouncerKt$PinPadButton$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState<Boolean> mutableState = this.$isPressed$delegate;
        float f = SecPinBouncerKt.pinButtonErrorShrinkFactor;
        if (((Boolean) mutableState.getValue()).booleanValue()) {
            this.$view.performHapticFeedback(1, 1);
        }
        return Unit.INSTANCE;
    }
}
