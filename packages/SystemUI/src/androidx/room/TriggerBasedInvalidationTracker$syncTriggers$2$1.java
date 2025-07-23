package androidx.room;

import androidx.room.ObservedTableStates;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TriggerBasedInvalidationTracker$syncTriggers$2$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TriggerBasedInvalidationTracker this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Transactor $connection;
        final /* synthetic */ ObservedTableStates.ObserveOp[] $tablesToSync;
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ TriggerBasedInvalidationTracker this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ObservedTableStates.ObserveOp.values().length];
                try {
                    iArr[ObservedTableStates.ObserveOp.NO_OP.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ObservedTableStates.ObserveOp.ADD.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ObservedTableStates.ObserveOp.REMOVE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ObservedTableStates.ObserveOp[] observeOpArr, TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Transactor transactor, Continuation continuation) {
            super(2, continuation);
            this.$tablesToSync = observeOpArr;
            this.this$0 = triggerBasedInvalidationTracker;
            this.$connection = transactor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$tablesToSync, this.this$0, this.$connection, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((TransactionScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0081, code lost:
        
            if (androidx.room.TriggerBasedInvalidationTracker.access$startTrackingTable(r7, r12, r6, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0066, code lost:
        
            r6 = r12;
            r5 = r9;
         */
        /* JADX WARN: Removed duplicated region for block: B:12:0x003c  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0084 -> B:10:0x0085). Please report as a decompilation issue!!! */
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
                r2 = 1
                r3 = 2
                if (r1 == 0) goto L2a
                if (r1 == r2) goto Lc
                if (r1 != r3) goto L22
            Lc:
                int r1 = r11.I$2
                int r4 = r11.I$1
                int r5 = r11.I$0
                java.lang.Object r6 = r11.L$2
                androidx.room.Transactor r6 = (androidx.room.Transactor) r6
                java.lang.Object r7 = r11.L$1
                androidx.room.TriggerBasedInvalidationTracker r7 = (androidx.room.TriggerBasedInvalidationTracker) r7
                java.lang.Object r8 = r11.L$0
                androidx.room.ObservedTableStates$ObserveOp[] r8 = (androidx.room.ObservedTableStates.ObserveOp[]) r8
                kotlin.ResultKt.throwOnFailure(r12)
                goto L66
            L22:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r12)
                throw r11
            L2a:
                kotlin.ResultKt.throwOnFailure(r12)
                androidx.room.ObservedTableStates$ObserveOp[] r12 = r11.$tablesToSync
                androidx.room.TriggerBasedInvalidationTracker r1 = r11.this$0
                androidx.room.Transactor r4 = r11.$connection
                int r5 = r12.length
                r6 = 0
                r8 = r12
                r7 = r1
                r12 = r4
                r1 = r5
                r4 = r6
            L3a:
                if (r4 >= r1) goto L87
                r5 = r8[r4]
                int r9 = r6 + 1
                int[] r10 = androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1.AnonymousClass1.WhenMappings.$EnumSwitchMapping$0
                int r5 = r5.ordinal()
                r5 = r10[r5]
                if (r5 == r2) goto L84
                if (r5 == r3) goto L6f
                r10 = 3
                if (r5 != r10) goto L69
                r11.L$0 = r8
                r11.L$1 = r7
                r11.L$2 = r12
                r11.I$0 = r9
                r11.I$1 = r4
                r11.I$2 = r1
                r11.label = r3
                java.lang.Object r5 = androidx.room.TriggerBasedInvalidationTracker.access$stopTrackingTable(r7, r12, r6, r11)
                if (r5 != r0) goto L64
                goto L83
            L64:
                r6 = r12
                r5 = r9
            L66:
                r12 = r6
                r6 = r5
                goto L85
            L69:
                kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                r11.<init>()
                throw r11
            L6f:
                r11.L$0 = r8
                r11.L$1 = r7
                r11.L$2 = r12
                r11.I$0 = r9
                r11.I$1 = r4
                r11.I$2 = r1
                r11.label = r2
                java.lang.Object r5 = androidx.room.TriggerBasedInvalidationTracker.access$startTrackingTable(r7, r12, r6, r11)
                if (r5 != r0) goto L64
            L83:
                return r0
            L84:
                r6 = r9
            L85:
                int r4 = r4 + r2
                goto L3a
            L87:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TriggerBasedInvalidationTracker$syncTriggers$2$1(TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = triggerBasedInvalidationTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TriggerBasedInvalidationTracker$syncTriggers$2$1 triggerBasedInvalidationTracker$syncTriggers$2$1 = new TriggerBasedInvalidationTracker$syncTriggers$2$1(this.this$0, continuation);
        triggerBasedInvalidationTracker$syncTriggers$2$1.L$0 = obj;
        return triggerBasedInvalidationTracker$syncTriggers$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TriggerBasedInvalidationTracker$syncTriggers$2$1) create((Transactor) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a2, code lost:
    
        if (r2.withTransaction(r3, r5, r18) == r1) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a4, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0034, code lost:
    
        if (r5 == r1) goto L48;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            r18 = this;
            r0 = r18
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L25
            if (r2 == r3) goto L1b
            if (r2 != r4) goto L13
            kotlin.ResultKt.throwOnFailure(r19)
            goto La5
        L13:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L1b:
            java.lang.Object r2 = r0.L$0
            androidx.room.Transactor r2 = (androidx.room.Transactor) r2
            kotlin.ResultKt.throwOnFailure(r19)
            r5 = r19
            goto L38
        L25:
            kotlin.ResultKt.throwOnFailure(r19)
            java.lang.Object r2 = r0.L$0
            androidx.room.Transactor r2 = (androidx.room.Transactor) r2
            r0.L$0 = r2
            r0.label = r3
            java.lang.Object r5 = r2.inTransaction(r0)
            if (r5 != r1) goto L38
            goto La4
        L38:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L43
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L43:
            androidx.room.TriggerBasedInvalidationTracker r5 = r0.this$0
            androidx.room.ObservedTableStates r5 = r5.observedTableStates
            long[] r6 = r5.tableObserversCount
            java.util.concurrent.locks.ReentrantLock r7 = r5.lock
            r7.lock()
            boolean r8 = r5.needsSync     // Catch: java.lang.Throwable -> L7b
            r9 = 0
            if (r8 != 0) goto L58
            r7.unlock()
            r11 = r9
            goto L8f
        L58:
            r8 = 0
            r5.needsSync = r8     // Catch: java.lang.Throwable -> L7b
            int r10 = r6.length     // Catch: java.lang.Throwable -> L7b
            androidx.room.ObservedTableStates$ObserveOp[] r11 = new androidx.room.ObservedTableStates.ObserveOp[r10]     // Catch: java.lang.Throwable -> L7b
            r12 = r8
            r13 = r12
        L60:
            if (r12 >= r10) goto L88
            r14 = r6[r12]     // Catch: java.lang.Throwable -> L7b
            r16 = 0
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 <= 0) goto L6c
            r14 = r3
            goto L6d
        L6c:
            r14 = r8
        L6d:
            boolean[] r15 = r5.tableObservedState     // Catch: java.lang.Throwable -> L7b
            boolean r3 = r15[r12]     // Catch: java.lang.Throwable -> L7b
            if (r14 == r3) goto L80
            r15[r12] = r14     // Catch: java.lang.Throwable -> L7b
            if (r14 == 0) goto L7d
            androidx.room.ObservedTableStates$ObserveOp r3 = androidx.room.ObservedTableStates.ObserveOp.ADD     // Catch: java.lang.Throwable -> L7b
        L79:
            r13 = 1
            goto L82
        L7b:
            r0 = move-exception
            goto La8
        L7d:
            androidx.room.ObservedTableStates$ObserveOp r3 = androidx.room.ObservedTableStates.ObserveOp.REMOVE     // Catch: java.lang.Throwable -> L7b
            goto L79
        L80:
            androidx.room.ObservedTableStates$ObserveOp r3 = androidx.room.ObservedTableStates.ObserveOp.NO_OP     // Catch: java.lang.Throwable -> L7b
        L82:
            r11[r12] = r3     // Catch: java.lang.Throwable -> L7b
            int r12 = r12 + 1
            r3 = 1
            goto L60
        L88:
            if (r13 == 0) goto L8b
            goto L8c
        L8b:
            r11 = r9
        L8c:
            r7.unlock()
        L8f:
            if (r11 == 0) goto La5
            androidx.room.Transactor$SQLiteTransactionType r3 = androidx.room.Transactor.SQLiteTransactionType.IMMEDIATE
            androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1 r5 = new androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1
            androidx.room.TriggerBasedInvalidationTracker r6 = r0.this$0
            r5.<init>(r11, r6, r2, r9)
            r0.L$0 = r9
            r0.label = r4
            java.lang.Object r0 = r2.withTransaction(r3, r5, r0)
            if (r0 != r1) goto La5
        La4:
            return r1
        La5:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        La8:
            r7.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
