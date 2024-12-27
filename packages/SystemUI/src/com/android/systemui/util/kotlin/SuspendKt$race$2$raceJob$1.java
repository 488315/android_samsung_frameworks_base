package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SuspendKt$race$2$raceJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1[] $blocks;
    final /* synthetic */ CompletableDeferred $completion;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.util.kotlin.SuspendKt$race$2$raceJob$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ CompletableDeferred $completion;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CompletableDeferred completableDeferred, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$completion = completableDeferred;
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$completion, this.$block, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CompletableDeferred completableDeferred;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CompletableDeferred completableDeferred2 = this.$completion;
                Function1 function1 = this.$block;
                this.L$0 = completableDeferred2;
                this.label = 1;
                Object invoke = function1.invoke(this);
                if (invoke == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = invoke;
                completableDeferred = completableDeferred2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                completableDeferred = (CompletableDeferred) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuspendKt$race$2$raceJob$1(Function1[] function1Arr, CompletableDeferred completableDeferred, Continuation continuation) {
        super(2, continuation);
        this.$blocks = function1Arr;
        this.$completion = completableDeferred;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SuspendKt$race$2$raceJob$1 suspendKt$race$2$raceJob$1 = new SuspendKt$race$2$raceJob$1(this.$blocks, this.$completion, continuation);
        suspendKt$race$2$raceJob$1.L$0 = obj;
        return suspendKt$race$2$raceJob$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        for (Function1 function1 : this.$blocks) {
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$completion, function1, null), 3);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SuspendKt$race$2$raceJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
