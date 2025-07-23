package com.android.systemui.kairos;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScopeKt$asyncEffect$job$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ CompletableDeferred $result;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScopeKt$asyncEffect$job$1$1(CompletableDeferred completableDeferred, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$result = completableDeferred;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScopeKt$asyncEffect$job$1$1 buildScopeKt$asyncEffect$job$1$1 = new BuildScopeKt$asyncEffect$job$1$1(this.$result, this.$block, continuation);
        buildScopeKt$asyncEffect$job$1$1.L$0 = obj;
        return buildScopeKt$asyncEffect$job$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScopeKt$asyncEffect$job$1$1) create((KairosCoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CompletableDeferred completableDeferred;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KairosCoroutineScope kairosCoroutineScope = (KairosCoroutineScope) this.L$0;
            CompletableDeferred completableDeferred2 = this.$result;
            Function2 function2 = this.$block;
            this.L$0 = completableDeferred2;
            this.label = 1;
            obj = function2.invoke(kairosCoroutineScope, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
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
}
