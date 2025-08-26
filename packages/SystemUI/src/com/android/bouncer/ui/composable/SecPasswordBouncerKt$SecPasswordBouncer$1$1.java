package com.android.bouncer.ui.composable;

import androidx.compose.runtime.State;
import androidx.compose.ui.focus.FocusRequester;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class SecPasswordBouncerKt$SecPasswordBouncer$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FocusRequester $focusRequester;
    final /* synthetic */ State<Boolean> $isTextFieldFocusRequested$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPasswordBouncerKt$SecPasswordBouncer$1$1(FocusRequester focusRequester, State<Boolean> state, Continuation continuation) {
        super(2, continuation);
        this.$focusRequester = focusRequester;
        this.$isTextFieldFocusRequested$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPasswordBouncerKt$SecPasswordBouncer$1$1(this.$focusRequester, this.$isTextFieldFocusRequested$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPasswordBouncerKt$SecPasswordBouncer$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((Boolean) this.$isTextFieldFocusRequested$delegate.getValue()).booleanValue()) {
            FocusRequester.m378requestFocus3ESFkO8$default(this.$focusRequester);
        }
        return Unit.INSTANCE;
    }
}
