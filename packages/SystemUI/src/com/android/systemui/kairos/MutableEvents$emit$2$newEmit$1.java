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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MutableEvents$emit$2$newEmit$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<Job> $jobOrNull;
    final /* synthetic */ Object $value;
    int label;
    final /* synthetic */ MutableEvents this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
    
        if (r7.awaitInternal(r6) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002d, code lost:
    
        if (r7.join(r6) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4b
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L30
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r7 = r6.$jobOrNull
            T r7 = r7.element
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            if (r7 == 0) goto L30
            r6.label = r3
            java.lang.Object r7 = r7.join(r6)
            if (r7 != r0) goto L30
            goto L4a
        L30:
            com.android.systemui.kairos.MutableEvents r7 = r6.this$0
            com.android.systemui.kairos.internal.Network r1 = r7.network
            com.android.systemui.kairos.MutableEvents$emit$2$newEmit$1$1 r3 = new com.android.systemui.kairos.MutableEvents$emit$2$newEmit$1$1
            java.lang.Object r4 = r6.$value
            r5 = 0
            r3.<init>(r7, r4, r5)
            java.lang.String r7 = "MutableEvents.emit"
            kotlinx.coroutines.CompletableDeferredImpl r7 = r1.transaction(r7, r3)
            r6.label = r2
            java.lang.Object r6 = r7.awaitInternal(r6)
            if (r6 != r0) goto L4b
        L4a:
            return r0
        L4b:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.MutableEvents$emit$2$newEmit$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
