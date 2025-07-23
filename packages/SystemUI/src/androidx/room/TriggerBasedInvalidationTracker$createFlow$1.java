package androidx.room;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TriggerBasedInvalidationTracker$createFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $emitInitialState;
    final /* synthetic */ String[] $resolvedTableNames;
    final /* synthetic */ int[] $tableIds;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TriggerBasedInvalidationTracker this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.room.TriggerBasedInvalidationTracker$createFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TriggerBasedInvalidationTracker this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Continuation continuation) {
            super(2, continuation);
            this.this$0 = triggerBasedInvalidationTracker;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = this.this$0;
                this.label = 1;
                if (triggerBasedInvalidationTracker.syncTriggers$room_runtime_release(this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ Ref$ObjectRef $currentVersions;
        public final /* synthetic */ boolean $emitInitialState;
        public final /* synthetic */ String[] $resolvedTableNames;
        public final /* synthetic */ int[] $tableIds;

        public AnonymousClass2(Ref$ObjectRef<int[]> ref$ObjectRef, boolean z, FlowCollector flowCollector, String[] strArr, int[] iArr) {
            this.$currentVersions = ref$ObjectRef;
            this.$emitInitialState = z;
            this.$$this$flow = flowCollector;
            this.$resolvedTableNames = strArr;
            this.$tableIds = iArr;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
        
            if (r6.emit(r15, r0) == r1) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x00a1, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x009f, code lost:
        
            if (r6.emit(r15, r0) == r1) goto L37;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:16:0x003c  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(int[] r14, kotlin.coroutines.Continuation r15) {
            /*
                r13 = this;
                boolean r0 = r15 instanceof androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2$emit$1
                if (r0 == 0) goto L13
                r0 = r15
                androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2$emit$1 r0 = (androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2$emit$1 r0 = new androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2$emit$1
                r0.<init>(r13, r15)
            L18:
                java.lang.Object r15 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L3c
                if (r2 == r4) goto L2f
                if (r2 != r3) goto L27
                goto L2f
            L27:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
                r13.<init>(r14)
                throw r13
            L2f:
                java.lang.Object r13 = r0.L$1
                r14 = r13
                int[] r14 = (int[]) r14
                java.lang.Object r13 = r0.L$0
                androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2 r13 = (androidx.room.TriggerBasedInvalidationTracker$createFlow$1.AnonymousClass2) r13
                kotlin.ResultKt.throwOnFailure(r15)
                goto La2
            L3c:
                kotlin.ResultKt.throwOnFailure(r15)
                kotlin.jvm.internal.Ref$ObjectRef r15 = r13.$currentVersions
                T r2 = r15.element
                java.lang.String[] r5 = r13.$resolvedTableNames
                kotlinx.coroutines.flow.FlowCollector r6 = r13.$$this$flow
                if (r2 != 0) goto L5e
                boolean r15 = r13.$emitInitialState
                if (r15 == 0) goto La2
                java.util.Set r15 = kotlin.collections.ArraysKt___ArraysKt.toSet(r5)
                r0.L$0 = r13
                r0.L$1 = r14
                r0.label = r4
                java.lang.Object r15 = r6.emit(r15, r0)
                if (r15 != r1) goto La2
                goto La1
            L5e:
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                int r4 = r5.length
                r7 = 0
                r8 = r7
            L66:
                if (r7 >= r4) goto L8b
                r9 = r5[r7]
                int r10 = r8 + 1
                T r11 = r15.element
                if (r11 == 0) goto L83
                int[] r11 = (int[]) r11
                int[] r12 = r13.$tableIds
                r8 = r12[r8]
                r11 = r11[r8]
                r8 = r14[r8]
                if (r11 == r8) goto L7f
                r2.add(r9)
            L7f:
                int r7 = r7 + 1
                r8 = r10
                goto L66
            L83:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r14 = "Required value was null."
                r13.<init>(r14)
                throw r13
            L8b:
                boolean r15 = r2.isEmpty()
                if (r15 != 0) goto La2
                java.util.Set r15 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
                r0.L$0 = r13
                r0.L$1 = r14
                r0.label = r3
                java.lang.Object r15 = r6.emit(r15, r0)
                if (r15 != r1) goto La2
            La1:
                return r1
            La2:
                kotlin.jvm.internal.Ref$ObjectRef r13 = r13.$currentVersions
                r13.element = r14
                kotlin.Unit r13 = kotlin.Unit.INSTANCE
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker$createFlow$1.AnonymousClass2.emit(int[], kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TriggerBasedInvalidationTracker$createFlow$1(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, int[] iArr, boolean z, String[] strArr, Continuation continuation) {
        super(2, continuation);
        this.this$0 = triggerBasedInvalidationTracker;
        this.$tableIds = iArr;
        this.$emitInitialState = z;
        this.$resolvedTableNames = strArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TriggerBasedInvalidationTracker$createFlow$1 triggerBasedInvalidationTracker$createFlow$1 = new TriggerBasedInvalidationTracker$createFlow$1(this.this$0, this.$tableIds, this.$emitInitialState, this.$resolvedTableNames, continuation);
        triggerBasedInvalidationTracker$createFlow$1.L$0 = obj;
        return triggerBasedInvalidationTracker$createFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TriggerBasedInvalidationTracker$createFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x008a, code lost:
    
        if (r12.collect(r4, r11) != r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0068, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext((kotlin.coroutines.CoroutineContext) r12, r5, r11) == r0) goto L28;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L31
            if (r1 == r5) goto L29
            if (r1 == r4) goto L21
            if (r1 == r3) goto L18
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L18:
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L1d
            goto L8d
        L1d:
            r0 = move-exception
            r12 = r0
            goto L93
        L21:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L6b
        L29:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L57
        L31:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
            androidx.room.TriggerBasedInvalidationTracker r1 = r11.this$0
            androidx.room.ObservedTableStates r1 = r1.observedTableStates
            int[] r6 = r11.$tableIds
            boolean r1 = r1.onObserverAdded$room_runtime_release(r6)
            if (r1 == 0) goto L6d
            androidx.room.TriggerBasedInvalidationTracker r1 = r11.this$0
            androidx.room.RoomDatabase r1 = r1.database
            r11.L$0 = r12
            r11.label = r5
            r5 = 0
            kotlin.coroutines.CoroutineContext r1 = androidx.room.util.DBUtil.getCoroutineContext(r1, r5, r11)
            if (r1 != r0) goto L54
            goto L8c
        L54:
            r10 = r1
            r1 = r12
            r12 = r10
        L57:
            kotlin.coroutines.CoroutineContext r12 = (kotlin.coroutines.CoroutineContext) r12
            androidx.room.TriggerBasedInvalidationTracker$createFlow$1$1 r5 = new androidx.room.TriggerBasedInvalidationTracker$createFlow$1$1
            androidx.room.TriggerBasedInvalidationTracker r6 = r11.this$0
            r5.<init>(r6, r2)
            r11.L$0 = r1
            r11.label = r4
            java.lang.Object r12 = kotlinx.coroutines.BuildersKt.withContext(r12, r5, r11)
            if (r12 != r0) goto L6b
            goto L8c
        L6b:
            r7 = r1
            goto L6e
        L6d:
            r7 = r12
        L6e:
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: java.lang.Throwable -> L1d
            r5.<init>()     // Catch: java.lang.Throwable -> L1d
            androidx.room.TriggerBasedInvalidationTracker r12 = r11.this$0     // Catch: java.lang.Throwable -> L1d
            androidx.room.ObservedTableVersions r12 = r12.observedTableVersions     // Catch: java.lang.Throwable -> L1d
            androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2 r4 = new androidx.room.TriggerBasedInvalidationTracker$createFlow$1$2     // Catch: java.lang.Throwable -> L1d
            boolean r6 = r11.$emitInitialState     // Catch: java.lang.Throwable -> L1d
            java.lang.String[] r8 = r11.$resolvedTableNames     // Catch: java.lang.Throwable -> L1d
            int[] r9 = r11.$tableIds     // Catch: java.lang.Throwable -> L1d
            r4.<init>(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L1d
            r11.L$0 = r2     // Catch: java.lang.Throwable -> L1d
            r11.label = r3     // Catch: java.lang.Throwable -> L1d
            kotlin.coroutines.intrinsics.CoroutineSingletons r12 = r12.collect(r4, r11)     // Catch: java.lang.Throwable -> L1d
            if (r12 != r0) goto L8d
        L8c:
            return r0
        L8d:
            kotlin.KotlinNothingValueException r12 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L1d
            r12.<init>()     // Catch: java.lang.Throwable -> L1d
            throw r12     // Catch: java.lang.Throwable -> L1d
        L93:
            androidx.room.TriggerBasedInvalidationTracker r0 = r11.this$0
            androidx.room.ObservedTableStates r0 = r0.observedTableStates
            int[] r11 = r11.$tableIds
            r0.onObserverRemoved$room_runtime_release(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker$createFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
