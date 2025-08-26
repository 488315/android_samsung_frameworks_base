package com.android.bouncer.ui.composable;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes.dex */
final class SecPasswordBouncerKt$SecPasswordBouncer$4$4$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<VisualTransformation> $currentVisualTransformation$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPasswordBouncerKt$SecPasswordBouncer$4$4$1$1(MutableState<VisualTransformation> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$currentVisualTransformation$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPasswordBouncerKt$SecPasswordBouncer$4$4$1$1(this.$currentVisualTransformation$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPasswordBouncerKt$SecPasswordBouncer$4$4$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(1500L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$currentVisualTransformation$delegate.setValue(new PasswordVisualTransformation((char) 0, 1, null));
        return Unit.INSTANCE;
    }
}
