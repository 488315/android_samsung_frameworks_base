package com.android.systemui.util.kotlin;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class FlowKt$sample$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $other;
    final /* synthetic */ Flow $this_sample;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$flow;
        final /* synthetic */ Flow $other;
        final /* synthetic */ Flow $this_sample;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02791<T> implements FlowCollector {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Object $noVal;
            final /* synthetic */ AtomicReference<Object> $sampledRef;
            final /* synthetic */ Function3 $transform;

            public C02791(AtomicReference<Object> atomicReference, Object obj, FlowCollector flowCollector, Function3 function3) {
                this.$sampledRef = atomicReference;
                this.$noVal = obj;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0066 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                /*
                    r5 = this;
                    boolean r0 = r7 instanceof com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r7
                    com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1
                    r0.<init>(r5, r7)
                L18:
                    java.lang.Object r7 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 2
                    r4 = 1
                    if (r2 == 0) goto L3a
                    if (r2 == r4) goto L32
                    if (r2 != r3) goto L2a
                    kotlin.ResultKt.throwOnFailure(r7)
                    goto L67
                L2a:
                    java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                    java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                    r5.<init>(r6)
                    throw r5
                L32:
                    java.lang.Object r5 = r0.L$0
                    kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                    kotlin.ResultKt.throwOnFailure(r7)
                    goto L5b
                L3a:
                    kotlin.ResultKt.throwOnFailure(r7)
                    java.util.concurrent.atomic.AtomicReference<java.lang.Object> r7 = r5.$sampledRef
                    java.lang.Object r7 = r7.get()
                    java.lang.Object r2 = r5.$noVal
                    boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r2)
                    if (r2 != 0) goto L6a
                    kotlinx.coroutines.flow.FlowCollector r2 = r5.$$this$flow
                    kotlin.jvm.functions.Function3 r5 = r5.$transform
                    r0.L$0 = r2
                    r0.label = r4
                    java.lang.Object r7 = r5.invoke(r6, r7, r0)
                    if (r7 != r1) goto L5a
                    return r1
                L5a:
                    r5 = r2
                L5b:
                    r6 = 0
                    r0.L$0 = r6
                    r0.label = r3
                    java.lang.Object r5 = r5.emit(r7, r0)
                    if (r5 != r1) goto L67
                    return r1
                L67:
                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                    return r5
                L6a:
                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$sample$1.AnonymousClass1.C02791.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Flow flow, Flow flow2, FlowCollector flowCollector, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_sample = flow;
            this.$other = flow2;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_sample, this.$other, this.$$this$flow, this.$transform, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Job job;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Object obj2 = new Object();
                AtomicReference atomicReference = new AtomicReference(obj2);
                Job launch$default = BuildersKt.launch$default(coroutineScope, Dispatchers.Unconfined, null, new FlowKt$sample$1$1$job$1(this.$other, atomicReference, null), 2);
                Flow flow = this.$this_sample;
                C02791 c02791 = new C02791(atomicReference, obj2, this.$$this$flow, this.$transform);
                this.L$0 = launch$default;
                this.label = 1;
                if (flow.collect(c02791, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                job = launch$default;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                job = (Job) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            job.cancel(null);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$sample$1(Flow flow, Flow flow2, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_sample = flow;
        this.$other = flow2;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$sample$1 flowKt$sample$1 = new FlowKt$sample$1(this.$this_sample, this.$other, this.$transform, continuation);
        flowKt$sample$1.L$0 = obj;
        return flowKt$sample$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_sample, this.$other, (FlowCollector) this.L$0, this.$transform, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((FlowKt$sample$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
