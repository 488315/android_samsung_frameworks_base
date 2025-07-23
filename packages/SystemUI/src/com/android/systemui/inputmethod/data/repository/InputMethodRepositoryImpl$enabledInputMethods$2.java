package com.android.systemui.inputmethod.data.repository;

import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class InputMethodRepositoryImpl$enabledInputMethods$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ InputMethodRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InputMethodRepositoryImpl$enabledInputMethods$2(InputMethodRepositoryImpl inputMethodRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = inputMethodRepositoryImpl;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new InputMethodRepositoryImpl$enabledInputMethods$2(this.this$0, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InputMethodRepositoryImpl$enabledInputMethods$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.inputMethodManager.getEnabledInputMethodListAsUser(this.$user);
    }
}
