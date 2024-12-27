package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class SuspendKt$race$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1[] $blocks;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuspendKt$race$2(Function1[] function1Arr, Continuation continuation) {
        super(2, continuation);
        this.$blocks = function1Arr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SuspendKt$race$2 suspendKt$race$2 = new SuspendKt$race$2(this.$blocks, continuation);
        suspendKt$race$2.L$0 = obj;
        return suspendKt$race$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Job job;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CompletableDeferredImpl CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            Job launch$default = BuildersKt.launch$default(coroutineScope, null, null, new SuspendKt$race$2$raceJob$1(this.$blocks, CompletableDeferred$default, null), 3);
            this.L$0 = launch$default;
            this.label = 1;
            Object awaitInternal = CompletableDeferred$default.awaitInternal(this);
            if (awaitInternal == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = awaitInternal;
            job = launch$default;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            job = (Job) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        job.cancel(null);
        return obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SuspendKt$race$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
