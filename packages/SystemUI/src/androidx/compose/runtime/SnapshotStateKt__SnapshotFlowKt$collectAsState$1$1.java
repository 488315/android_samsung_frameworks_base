package androidx.compose.runtime;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ Flow $this_collectAsState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProduceStateScope<Object> $$this$produceState;
        final /* synthetic */ Flow $this_collectAsState;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Flow flow, ProduceStateScope<Object> produceStateScope, Continuation continuation) {
            super(2, continuation);
            this.$this_collectAsState = flow;
            this.$$this$produceState = produceStateScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$this_collectAsState, this.$$this$produceState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = this.$this_collectAsState;
                final ProduceStateScope<Object> produceStateScope = this.$$this$produceState;
                FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt.collectAsState.1.1.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((ProduceStateScopeImpl) produceStateScope).setValue(obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1(CoroutineContext coroutineContext, Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$context = coroutineContext;
        this.$this_collectAsState = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1 snapshotStateKt__SnapshotFlowKt$collectAsState$1$1 = new SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1(this.$context, this.$this_collectAsState, continuation);
        snapshotStateKt__SnapshotFlowKt$collectAsState$1$1.L$0 = obj;
        return snapshotStateKt__SnapshotFlowKt$collectAsState$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1) create((ProduceStateScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        if (r1.collect(r2, r6) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004a, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6) == r0) goto L17;
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
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L15:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4d
        L19:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            androidx.compose.runtime.ProduceStateScope r7 = (androidx.compose.runtime.ProduceStateScope) r7
            kotlin.coroutines.CoroutineContext r1 = r6.$context
            kotlin.coroutines.EmptyCoroutineContext r4 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r4)
            if (r1 == 0) goto L3a
            kotlinx.coroutines.flow.Flow r1 = r6.$this_collectAsState
            androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1$1 r2 = new androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1$1
            r2.<init>()
            r6.label = r3
            java.lang.Object r6 = r1.collect(r2, r6)
            if (r6 != r0) goto L4d
            goto L4c
        L3a:
            kotlin.coroutines.CoroutineContext r1 = r6.$context
            androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1$2 r3 = new androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1$2
            kotlinx.coroutines.flow.Flow r4 = r6.$this_collectAsState
            r5 = 0
            r3.<init>(r4, r7, r5)
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6)
            if (r6 != r0) goto L4d
        L4c:
            return r0
        L4d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
