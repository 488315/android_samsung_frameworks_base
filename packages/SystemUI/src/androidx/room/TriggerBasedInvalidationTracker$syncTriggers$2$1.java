package androidx.room;

import androidx.room.ObservedTableStates;
import androidx.room.Transactor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class TriggerBasedInvalidationTracker$syncTriggers$2$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TriggerBasedInvalidationTracker this$0;

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
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0066, code lost:
        
            r6 = r12;
            r5 = r9;
         */
        /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0087  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0084 -> B:27:0x0085). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            int i;
            ObservedTableStates.ObserveOp[] observeOpArr;
            TriggerBasedInvalidationTracker triggerBasedInvalidationTracker;
            Transactor transactor;
            int i2;
            int i3;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i4 = this.label;
            if (i4 == 0) {
                ResultKt.throwOnFailure(obj);
                ObservedTableStates.ObserveOp[] observeOpArr2 = this.$tablesToSync;
                TriggerBasedInvalidationTracker triggerBasedInvalidationTracker2 = this.this$0;
                Transactor transactor2 = this.$connection;
                int length = observeOpArr2.length;
                i = 0;
                observeOpArr = observeOpArr2;
                triggerBasedInvalidationTracker = triggerBasedInvalidationTracker2;
                transactor = transactor2;
                i2 = length;
                i3 = 0;
                if (i3 >= i2) {
                }
            } else {
                if (i4 != 1 && i4 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = this.I$2;
                i3 = this.I$1;
                int i5 = this.I$0;
                Transactor transactor3 = (Transactor) this.L$2;
                triggerBasedInvalidationTracker = (TriggerBasedInvalidationTracker) this.L$1;
                observeOpArr = (ObservedTableStates.ObserveOp[]) this.L$0;
                ResultKt.throwOnFailure(obj);
                transactor = transactor3;
                i = i5;
                i3++;
                if (i3 >= i2) {
                    int i6 = i + 1;
                    int i7 = WhenMappings.$EnumSwitchMapping$0[observeOpArr[i3].ordinal()];
                    if (i7 == 1) {
                        i = i6;
                        i3++;
                        if (i3 >= i2) {
                            return Unit.INSTANCE;
                        }
                    } else {
                        if (i7 != 2) {
                            if (i7 != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            this.L$0 = observeOpArr;
                            this.L$1 = triggerBasedInvalidationTracker;
                            this.L$2 = transactor;
                            this.I$0 = i6;
                            this.I$1 = i3;
                            this.I$2 = i2;
                            this.label = 2;
                            if (TriggerBasedInvalidationTracker.access$stopTrackingTable(triggerBasedInvalidationTracker, transactor, i, this) != coroutineSingletons) {
                                transactor3 = transactor;
                                i5 = i6;
                                transactor = transactor3;
                                i = i5;
                            }
                            return coroutineSingletons;
                        }
                        this.L$0 = observeOpArr;
                        this.L$1 = triggerBasedInvalidationTracker;
                        this.L$2 = transactor;
                        this.I$0 = i6;
                        this.I$1 = i3;
                        this.I$2 = i2;
                        this.label = 1;
                        i3++;
                        if (i3 >= i2) {
                        }
                    }
                }
            }
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

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a2, code lost:
    
        if (r2.withTransaction(r3, r5, r18) == r1) goto L48;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Transactor transactor;
        Object objInTransaction;
        ObservedTableStates.ObserveOp[] observeOpArr;
        ObservedTableStates.ObserveOp observeOp;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        boolean z = true;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            transactor = (Transactor) this.L$0;
            this.L$0 = transactor;
            this.label = 1;
            objInTransaction = transactor.inTransaction(this);
            if (objInTransaction != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        transactor = (Transactor) this.L$0;
        ResultKt.throwOnFailure(obj);
        objInTransaction = obj;
        if (((Boolean) objInTransaction).booleanValue()) {
            return Unit.INSTANCE;
        }
        ObservedTableStates observedTableStates = this.this$0.observedTableStates;
        long[] jArr = observedTableStates.tableObserversCount;
        ReentrantLock reentrantLock = observedTableStates.lock;
        reentrantLock.lock();
        try {
            if (observedTableStates.needsSync) {
                observedTableStates.needsSync = false;
                int length = jArr.length;
                observeOpArr = new ObservedTableStates.ObserveOp[length];
                int i2 = 0;
                boolean z2 = false;
                while (i2 < length) {
                    boolean z3 = jArr[i2] > 0 ? z : false;
                    boolean[] zArr = observedTableStates.tableObservedState;
                    if (z3 != zArr[i2]) {
                        zArr[i2] = z3;
                        observeOp = z3 ? ObservedTableStates.ObserveOp.ADD : ObservedTableStates.ObserveOp.REMOVE;
                        z2 = true;
                    } else {
                        observeOp = ObservedTableStates.ObserveOp.NO_OP;
                    }
                    observeOpArr[i2] = observeOp;
                    i2++;
                    z = true;
                }
                if (!z2) {
                    observeOpArr = null;
                }
                reentrantLock.unlock();
            } else {
                reentrantLock.unlock();
                observeOpArr = null;
            }
            if (observeOpArr != null) {
                Transactor.SQLiteTransactionType sQLiteTransactionType = Transactor.SQLiteTransactionType.IMMEDIATE;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(observeOpArr, this.this$0, transactor, null);
                this.L$0 = null;
                this.label = 2;
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
