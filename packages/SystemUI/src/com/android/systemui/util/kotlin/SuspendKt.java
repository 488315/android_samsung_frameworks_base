package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class SuspendKt {

    /* renamed from: com.android.systemui.util.kotlin.SuspendKt$race$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1[] $blocks;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function1[] function1Arr, Continuation continuation) {
            super(2, continuation);
            this.$blocks = function1Arr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$blocks, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Job job;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
                StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new SuspendKt$race$2$raceJob$1(this.$blocks, completableDeferredImplCompletableDeferred$default, null), 7);
                this.L$0 = standaloneCoroutineLaunchTraced$default;
                this.label = 1;
                Object objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(this);
                if (objAwaitInternal == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objAwaitInternal;
                job = standaloneCoroutineLaunchTraced$default;
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
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public static final <R> Object race(Function1[] function1Arr, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(function1Arr, null), continuation);
    }
}
