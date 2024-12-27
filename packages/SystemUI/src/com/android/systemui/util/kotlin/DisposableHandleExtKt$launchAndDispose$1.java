package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DisposableHandleExtKt$launchAndDispose$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $onLaunch;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisposableHandleExtKt$launchAndDispose$1(Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$onLaunch = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisposableHandleExtKt$launchAndDispose$1(this.$onLaunch, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DisposableHandle disposableHandle = (DisposableHandle) this.$onLaunch.invoke();
            this.label = 1;
            if (DisposableHandleExtKt.awaitCancellationThenDispose(disposableHandle, this) == coroutineSingletons) {
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

    public final Object invokeSuspend$$forInline(Object obj) {
        DisposableHandleExtKt.awaitCancellationThenDispose((DisposableHandle) this.$onLaunch.invoke(), this);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((DisposableHandleExtKt$launchAndDispose$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
