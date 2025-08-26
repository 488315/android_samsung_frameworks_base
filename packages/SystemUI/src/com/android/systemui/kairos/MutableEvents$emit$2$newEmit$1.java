package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.GraphKt;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.TransactionCache;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
final class MutableEvents$emit$2$newEmit$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<Job> $jobOrNull;
    final /* synthetic */ Object $value;
    int label;
    final /* synthetic */ MutableEvents this$0;

    /* renamed from: com.android.systemui.kairos.MutableEvents$emit$2$newEmit$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $value;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MutableEvents this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MutableEvents mutableEvents, Object obj, Continuation continuation) {
            super(2, continuation);
            this.this$0 = mutableEvents;
            this.$value = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$value, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EvalScope evalScope = (EvalScope) this.L$0;
            InputNode inputNode = this.this$0.impl;
            Object obj2 = this.$value;
            TransactionCache transactionCache = inputNode.transactionCache;
            transactionCache.getClass();
            transactionCache.epoch = evalScope.getEpoch();
            evalScope.getTransactionStore().set(transactionCache.key, obj2);
            if (!GraphKt.scheduleAll(inputNode.downstreamSet, evalScope)) {
                evalScope.scheduleDeactivation(inputNode);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableEvents$emit$2$newEmit$1(Ref$ObjectRef<Job> ref$ObjectRef, MutableEvents mutableEvents, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$jobOrNull = ref$ObjectRef;
        this.this$0 = mutableEvents;
        this.$value = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MutableEvents$emit$2$newEmit$1(this.$jobOrNull, this.this$0, this.$value, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutableEvents$emit$2$newEmit$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0048, code lost:
    
        if (r7.awaitInternal(r6) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Job job = this.$jobOrNull.element;
            if (job != null) {
                this.label = 1;
                if (job.join(this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        MutableEvents mutableEvents = this.this$0;
        CompletableDeferredImpl completableDeferredImplTransaction = mutableEvents.network.transaction("MutableEvents.emit", new AnonymousClass1(mutableEvents, this.$value, null));
        this.label = 2;
    }
}
