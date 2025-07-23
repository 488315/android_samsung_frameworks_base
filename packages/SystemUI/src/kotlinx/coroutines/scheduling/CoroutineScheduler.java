package kotlinx.coroutines.scheduling;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.LockSupport;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.ResizableAtomicArray;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CoroutineScheduler implements Executor, Closeable {
    public static final Symbol NOT_IN_STACK;
    public final AtomicBoolean _isTerminated;
    public final AtomicLong controlState;
    public final int corePoolSize;
    public final GlobalQueue globalBlockingQueue;
    public final GlobalQueue globalCpuQueue;
    public final long idleWorkerKeepAliveNs;
    public final int maxPoolSize;
    public final AtomicLong parkedWorkersStack;
    public final String schedulerName;
    public final ResizableAtomicArray workers;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WorkerState.values().length];
            try {
                iArr[WorkerState.PARKING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WorkerState.BLOCKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WorkerState.CPU_ACQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WorkerState.DORMANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[WorkerState.TERMINATED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WorkerState {
        public static final /* synthetic */ WorkerState[] $VALUES;
        public static final WorkerState BLOCKING;
        public static final WorkerState CPU_ACQUIRED;
        public static final WorkerState DORMANT;
        public static final WorkerState PARKING;
        public static final WorkerState TERMINATED;

        static {
            WorkerState workerState = new WorkerState("CPU_ACQUIRED", 0);
            CPU_ACQUIRED = workerState;
            WorkerState workerState2 = new WorkerState("BLOCKING", 1);
            BLOCKING = workerState2;
            WorkerState workerState3 = new WorkerState("PARKING", 2);
            PARKING = workerState3;
            WorkerState workerState4 = new WorkerState("DORMANT", 3);
            DORMANT = workerState4;
            WorkerState workerState5 = new WorkerState("TERMINATED", 4);
            TERMINATED = workerState5;
            WorkerState[] workerStateArr = {workerState, workerState2, workerState3, workerState4, workerState5};
            $VALUES = workerStateArr;
            EnumEntriesKt.enumEntries(workerStateArr);
        }

        private WorkerState(String str, int i) {
        }

        public static WorkerState valueOf(String str) {
            return (WorkerState) Enum.valueOf(WorkerState.class, str);
        }

        public static WorkerState[] values() {
            return (WorkerState[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
        NOT_IN_STACK = new Symbol("NOT_IN_STACK");
    }

    public CoroutineScheduler(int i, int i2, long j, String str) {
        this.corePoolSize = i;
        this.maxPoolSize = i2;
        this.idleWorkerKeepAliveNs = j;
        this.schedulerName = str;
        if (i < 1) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Core pool size ", " should be at least 1").toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "Max pool size ", " should be greater than or equals to core pool size ").toString());
        }
        if (i2 > 2097150) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i2, "Max pool size ", " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (j <= 0) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
        }
        this.globalCpuQueue = new GlobalQueue();
        this.globalBlockingQueue = new GlobalQueue();
        this.parkedWorkersStack = AtomicFU.atomic(0L);
        this.workers = new ResizableAtomicArray((i + 1) * 2);
        this.controlState = AtomicFU.atomic(i << 42);
        this._isTerminated = AtomicFU.atomic(false);
    }

    public static /* synthetic */ void dispatch$default(CoroutineScheduler coroutineScheduler, Runnable runnable, int i) {
        coroutineScheduler.dispatch(false, (i & 4) == 0, runnable);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0095, code lost:
    
        if (r1 == null) goto L45;
     */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void close() {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.close():void");
    }

    public final int createNewWorker() {
        synchronized (this.workers) {
            try {
                if (this._isTerminated.getValue()) {
                    return -1;
                }
                long j = this.controlState.value;
                int i = (int) (j & 2097151);
                int i2 = i - ((int) ((j & 4398044413952L) >> 21));
                if (i2 < 0) {
                    i2 = 0;
                }
                if (i2 >= this.corePoolSize) {
                    return 0;
                }
                if (i >= this.maxPoolSize) {
                    return 0;
                }
                int i3 = ((int) (this.controlState.value & 2097151)) + 1;
                if (i3 <= 0 || this.workers.get(i3) != null) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                Worker worker = new Worker(this, i3);
                this.workers.setSynchronized(i3, worker);
                AtomicLong atomicLong = this.controlState;
                atomicLong.getClass();
                long incrementAndGet = AtomicLong.FU.incrementAndGet(atomicLong);
                TraceBase.None none = TraceBase.None.INSTANCE;
                TraceBase traceBase = atomicLong.trace;
                if (traceBase != none) {
                    traceBase.getClass();
                }
                if (i3 != ((int) (2097151 & incrementAndGet))) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                int i4 = i2 + 1;
                worker.start();
                return i4;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dispatch(boolean z, boolean z2, Runnable runnable) {
        Task taskImpl;
        WorkerState workerState;
        TasksKt.schedulerTimeSource.getClass();
        long nanoTime = System.nanoTime();
        if (runnable instanceof Task) {
            taskImpl = (Task) runnable;
            taskImpl.submissionTime = nanoTime;
            taskImpl.taskContext = z;
        } else {
            taskImpl = new TaskImpl(runnable, nanoTime, z);
        }
        boolean z3 = taskImpl.taskContext;
        long addAndGet = z3 ? this.controlState.addAndGet(2097152L) : 0L;
        Thread currentThread = Thread.currentThread();
        Worker worker = currentThread instanceof Worker ? (Worker) currentThread : null;
        if (worker == null || !Intrinsics.areEqual(CoroutineScheduler.this, this)) {
            worker = null;
        }
        if (worker != null && (workerState = worker.state) != WorkerState.TERMINATED && (taskImpl.taskContext || workerState != WorkerState.BLOCKING)) {
            worker.mayHaveLocalTasks = true;
            WorkQueue workQueue = worker.localQueue;
            if (z2) {
                taskImpl = workQueue.addLast(taskImpl);
            } else {
                Task task = (Task) workQueue.lastScheduledTask.getAndSet(taskImpl);
                taskImpl = task == null ? null : workQueue.addLast(task);
            }
        }
        if (taskImpl != null) {
            if (!(taskImpl.taskContext ? this.globalBlockingQueue.addLast(taskImpl) : this.globalCpuQueue.addLast(taskImpl))) {
                throw new RejectedExecutionException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.schedulerName, " was terminated"));
            }
        }
        if (z3) {
            if (tryUnpark() || tryCreateWorker(addAndGet)) {
                return;
            }
            tryUnpark();
            return;
        }
        if (tryUnpark() || tryCreateWorker(this.controlState.value)) {
            return;
        }
        tryUnpark();
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        dispatch$default(this, runnable, 6);
    }

    public final void parkedWorkersStackTopUpdate(Worker worker, int i, int i2) {
        AtomicLong atomicLong = this.parkedWorkersStack;
        while (true) {
            long j = atomicLong.value;
            int i3 = (int) (2097151 & j);
            long j2 = (2097152 + j) & (-2097152);
            if (i3 == i) {
                if (i2 == 0) {
                    Object nextParkedWorker = worker.getNextParkedWorker();
                    while (true) {
                        if (nextParkedWorker == NOT_IN_STACK) {
                            i3 = -1;
                            break;
                        }
                        if (nextParkedWorker == null) {
                            i3 = 0;
                            break;
                        }
                        Worker worker2 = (Worker) nextParkedWorker;
                        int indexInArray = worker2.getIndexInArray();
                        if (indexInArray != 0) {
                            i3 = indexInArray;
                            break;
                        }
                        nextParkedWorker = worker2.getNextParkedWorker();
                    }
                } else {
                    i3 = i2;
                }
            }
            if (i3 >= 0 && this.parkedWorkersStack.compareAndSet(j, j2 | i3)) {
                return;
            }
        }
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        int currentLength = this.workers.currentLength();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 1; i6 < currentLength; i6++) {
            Worker worker = (Worker) this.workers.get(i6);
            if (worker != null) {
                WorkQueue workQueue = worker.localQueue;
                int i7 = workQueue.lastScheduledTask.value != null ? (workQueue.producerIndex.value - workQueue.consumerIndex.value) + 1 : workQueue.producerIndex.value - workQueue.consumerIndex.value;
                int i8 = WhenMappings.$EnumSwitchMapping$0[worker.state.ordinal()];
                if (i8 == 1) {
                    i3++;
                } else if (i8 == 2) {
                    i2++;
                    arrayList.add(i7 + "b");
                } else if (i8 == 3) {
                    i++;
                    arrayList.add(i7 + "c");
                } else if (i8 == 4) {
                    i4++;
                    if (i7 > 0) {
                        arrayList.add(i7 + "d");
                    }
                } else {
                    if (i8 != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i5++;
                }
            }
        }
        long j = this.controlState.value;
        return this.schedulerName + "@" + DebugStringsKt.getHexAddress(this) + "[Pool Size {core = " + this.corePoolSize + ", max = " + this.maxPoolSize + "}, Worker States {CPU = " + i + ", blocking = " + i2 + ", parked = " + i3 + ", dormant = " + i4 + ", terminated = " + i5 + "}, running workers queues = " + arrayList + ", global CPU queue size = " + this.globalCpuQueue.getSize() + ", global blocking queue size = " + this.globalBlockingQueue.getSize() + ", Control State {created workers= " + ((int) (j & 2097151)) + ", blocking tasks = " + ((int) ((j & 4398044413952L) >> 21)) + ", CPUs acquired = " + (this.corePoolSize - ((int) ((j & 9223367638808264704L) >> 42))) + "}]";
    }

    public final boolean tryCreateWorker(long j) {
        int i = ((int) (2097151 & j)) - ((int) ((j & 4398044413952L) >> 21));
        if (i < 0) {
            i = 0;
        }
        if (i < this.corePoolSize) {
            int createNewWorker = createNewWorker();
            if (createNewWorker == 1 && this.corePoolSize > 1) {
                createNewWorker();
            }
            if (createNewWorker > 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean tryUnpark() {
        Worker worker;
        Symbol symbol;
        int i;
        do {
            AtomicLong atomicLong = this.parkedWorkersStack;
            while (true) {
                long j = atomicLong.value;
                worker = (Worker) this.workers.get((int) (2097151 & j));
                if (worker != null) {
                    long j2 = (2097152 + j) & (-2097152);
                    Object nextParkedWorker = worker.getNextParkedWorker();
                    while (true) {
                        symbol = NOT_IN_STACK;
                        if (nextParkedWorker == symbol) {
                            i = -1;
                            break;
                        }
                        if (nextParkedWorker == null) {
                            i = 0;
                            break;
                        }
                        Worker worker2 = (Worker) nextParkedWorker;
                        i = worker2.getIndexInArray();
                        if (i != 0) {
                            break;
                        }
                        nextParkedWorker = worker2.getNextParkedWorker();
                    }
                    if (i >= 0 && this.parkedWorkersStack.compareAndSet(j, j2 | i)) {
                        worker.setNextParkedWorker(symbol);
                        break;
                    }
                } else {
                    worker = null;
                    break;
                }
            }
            if (worker == null) {
                return false;
            }
        } while (!worker.workerCtl.compareAndSet(-1, 0));
        LockSupport.unpark(worker);
        return true;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Worker extends Thread {
        private volatile int indexInArray;
        public final WorkQueue localQueue;
        public boolean mayHaveLocalTasks;
        public long minDelayUntilStealableTaskNs;
        private volatile Object nextParkedWorker;
        public int rngState;
        public WorkerState state;
        public final Ref$ObjectRef stolenTask;
        public long terminationDeadline;
        public final AtomicInt workerCtl;

        private Worker() {
            setDaemon(true);
            setContextClassLoader(CoroutineScheduler.this.getClass().getClassLoader());
            this.localQueue = new WorkQueue();
            this.stolenTask = new Ref$ObjectRef();
            this.state = WorkerState.DORMANT;
            this.workerCtl = AtomicFU.atomic(0);
            this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
            int nanoTime = (int) System.nanoTime();
            this.rngState = nanoTime == 0 ? 42 : nanoTime;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0063 A[RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final kotlinx.coroutines.scheduling.Task findTask(boolean r10) {
            /*
                r9 = this;
                kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r0 = r9.state
                kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r1 = kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState.CPU_ACQUIRED
                r2 = 0
                r3 = 1
                if (r0 != r1) goto La
                goto L77
            La:
                kotlinx.coroutines.scheduling.CoroutineScheduler r0 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                kotlinx.atomicfu.AtomicLong r1 = r0.controlState
            Le:
                long r4 = r1.value
                r6 = 9223367638808264704(0x7ffffc0000000000, double:NaN)
                long r6 = r6 & r4
                r8 = 42
                long r6 = r6 >> r8
                int r6 = (int) r6
                if (r6 != 0) goto L64
                kotlinx.coroutines.scheduling.WorkQueue r10 = r9.localQueue
            L1e:
                kotlinx.atomicfu.AtomicRef r0 = r10.lastScheduledTask
                java.lang.Object r0 = r0.value
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
                if (r0 != 0) goto L27
                goto L35
            L27:
                boolean r1 = r0.taskContext
                if (r1 != r3) goto L35
                kotlinx.atomicfu.AtomicRef r1 = r10.lastScheduledTask
                boolean r1 = r1.compareAndSet(r0, r2)
                if (r1 == 0) goto L1e
                r2 = r0
                goto L4f
            L35:
                kotlinx.atomicfu.AtomicInt r0 = r10.consumerIndex
                int r0 = r0.value
                kotlinx.atomicfu.AtomicInt r1 = r10.producerIndex
                int r1 = r1.value
            L3d:
                if (r0 == r1) goto L4f
                kotlinx.atomicfu.AtomicInt r4 = r10.blockingTasksInBuffer
                int r4 = r4.value
                if (r4 != 0) goto L46
                goto L4f
            L46:
                int r1 = r1 + (-1)
                kotlinx.coroutines.scheduling.Task r4 = r10.tryExtractFromTheMiddle(r1, r3)
                if (r4 == 0) goto L3d
                r2 = r4
            L4f:
                if (r2 != 0) goto L63
                kotlinx.coroutines.scheduling.CoroutineScheduler r10 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                kotlinx.coroutines.scheduling.GlobalQueue r10 = r10.globalBlockingQueue
                java.lang.Object r10 = r10.removeFirstOrNull()
                kotlinx.coroutines.scheduling.Task r10 = (kotlinx.coroutines.scheduling.Task) r10
                if (r10 != 0) goto L62
                kotlinx.coroutines.scheduling.Task r9 = r9.trySteal(r3)
                return r9
            L62:
                return r10
            L63:
                return r2
            L64:
                r6 = 4398046511104(0x40000000000, double:2.1729236899484E-311)
                long r6 = r4 - r6
                kotlinx.atomicfu.AtomicLong r8 = r0.controlState
                boolean r4 = r8.compareAndSet(r4, r6)
                if (r4 == 0) goto Le
                kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r0 = kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState.CPU_ACQUIRED
                r9.state = r0
            L77:
                if (r10 == 0) goto Lac
                kotlinx.coroutines.scheduling.CoroutineScheduler r10 = kotlinx.coroutines.scheduling.CoroutineScheduler.this
                int r10 = r10.corePoolSize
                int r10 = r10 * 2
                int r10 = r9.nextInt(r10)
                if (r10 != 0) goto L86
                goto L87
            L86:
                r3 = 0
            L87:
                if (r3 == 0) goto L90
                kotlinx.coroutines.scheduling.Task r10 = r9.pollGlobalQueues()
                if (r10 == 0) goto L90
                return r10
            L90:
                kotlinx.coroutines.scheduling.WorkQueue r10 = r9.localQueue
                kotlinx.atomicfu.AtomicRef r0 = r10.lastScheduledTask
                java.lang.Object r0 = r0.getAndSet(r2)
                kotlinx.coroutines.scheduling.Task r0 = (kotlinx.coroutines.scheduling.Task) r0
                if (r0 != 0) goto La0
                kotlinx.coroutines.scheduling.Task r0 = r10.pollBuffer()
            La0:
                if (r0 == 0) goto La3
                return r0
            La3:
                if (r3 != 0) goto Lb3
                kotlinx.coroutines.scheduling.Task r10 = r9.pollGlobalQueues()
                if (r10 == 0) goto Lb3
                return r10
            Lac:
                kotlinx.coroutines.scheduling.Task r10 = r9.pollGlobalQueues()
                if (r10 == 0) goto Lb3
                return r10
            Lb3:
                r10 = 3
                kotlinx.coroutines.scheduling.Task r9 = r9.trySteal(r10)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker.findTask(boolean):kotlinx.coroutines.scheduling.Task");
        }

        public final int getIndexInArray() {
            return this.indexInArray;
        }

        public final Object getNextParkedWorker() {
            return this.nextParkedWorker;
        }

        public final int nextInt(int i) {
            int i2 = this.rngState;
            int i3 = i2 ^ (i2 << 13);
            int i4 = i3 ^ (i3 >> 17);
            int i5 = i4 ^ (i4 << 5);
            this.rngState = i5;
            int i6 = i - 1;
            return (i6 & i) == 0 ? i6 & i5 : (Integer.MAX_VALUE & i5) % i;
        }

        public final Task pollGlobalQueues() {
            if (nextInt(2) == 0) {
                Task task = (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
                return task != null ? task : (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            }
            Task task2 = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            return task2 != null ? task2 : (Task) CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v2, types: [boolean] */
        /* JADX WARN: Type inference failed for: r2v3 */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r2v9 */
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            long j;
            int i;
            int i2 = 0;
            loop0: while (true) {
                boolean z = i2;
                while (!CoroutineScheduler.this._isTerminated.getValue()) {
                    WorkerState workerState = this.state;
                    WorkerState workerState2 = WorkerState.TERMINATED;
                    if (workerState == workerState2) {
                        break loop0;
                    }
                    Task findTask = findTask(this.mayHaveLocalTasks);
                    if (findTask != null) {
                        this.minDelayUntilStealableTaskNs = 0L;
                        this.terminationDeadline = 0L;
                        if (this.state == WorkerState.PARKING) {
                            this.state = WorkerState.BLOCKING;
                        }
                        if (findTask.taskContext) {
                            if (tryReleaseCpu(WorkerState.BLOCKING)) {
                                CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
                                if (!coroutineScheduler.tryUnpark() && !coroutineScheduler.tryCreateWorker(coroutineScheduler.controlState.value)) {
                                    coroutineScheduler.tryUnpark();
                                }
                            }
                            CoroutineScheduler.this.getClass();
                            try {
                                findTask.run();
                            } catch (Throwable th) {
                                Thread currentThread = Thread.currentThread();
                                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th);
                            }
                            CoroutineScheduler.this.controlState.addAndGet(-2097152L);
                            if (this.state != workerState2) {
                                this.state = WorkerState.DORMANT;
                            }
                        } else {
                            CoroutineScheduler.this.getClass();
                            try {
                                findTask.run();
                            } catch (Throwable th2) {
                                Thread currentThread2 = Thread.currentThread();
                                currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th2);
                            }
                        }
                    } else {
                        this.mayHaveLocalTasks = i2;
                        if (this.minDelayUntilStealableTaskNs == 0) {
                            Object obj = this.nextParkedWorker;
                            Symbol symbol = CoroutineScheduler.NOT_IN_STACK;
                            if ((obj != symbol ? true : i2 == true ? 1 : 0) == true) {
                                AtomicInt atomicInt = this.workerCtl;
                                atomicInt.value = -1;
                                TraceBase traceBase = atomicInt.trace;
                                if (traceBase != TraceBase.None.INSTANCE) {
                                    traceBase.getClass();
                                }
                                while (this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK && this.workerCtl.value == -1 && !CoroutineScheduler.this._isTerminated.getValue()) {
                                    WorkerState workerState3 = this.state;
                                    WorkerState workerState4 = WorkerState.TERMINATED;
                                    if (workerState3 == workerState4) {
                                        break;
                                    }
                                    tryReleaseCpu(WorkerState.PARKING);
                                    Thread.interrupted();
                                    if (this.terminationDeadline == 0) {
                                        this.terminationDeadline = System.nanoTime() + CoroutineScheduler.this.idleWorkerKeepAliveNs;
                                    }
                                    LockSupport.parkNanos(CoroutineScheduler.this.idleWorkerKeepAliveNs);
                                    if (System.nanoTime() - this.terminationDeadline >= 0) {
                                        this.terminationDeadline = 0L;
                                        CoroutineScheduler coroutineScheduler2 = CoroutineScheduler.this;
                                        synchronized (coroutineScheduler2.workers) {
                                            try {
                                                if (!coroutineScheduler2._isTerminated.getValue()) {
                                                    if (((int) (coroutineScheduler2.controlState.value & 2097151)) > coroutineScheduler2.corePoolSize) {
                                                        if (this.workerCtl.compareAndSet(-1, 1)) {
                                                            int i3 = this.indexInArray;
                                                            setIndexInArray(i2);
                                                            coroutineScheduler2.parkedWorkersStackTopUpdate(this, i3, i2);
                                                            AtomicLong atomicLong = coroutineScheduler2.controlState;
                                                            atomicLong.getClass();
                                                            long andDecrement = AtomicLong.FU.getAndDecrement(atomicLong);
                                                            TraceBase.None none = TraceBase.None.INSTANCE;
                                                            TraceBase traceBase2 = atomicLong.trace;
                                                            if (traceBase2 != none) {
                                                                traceBase2.getClass();
                                                            }
                                                            int i4 = (int) (andDecrement & 2097151);
                                                            if (i4 != i3) {
                                                                Object obj2 = coroutineScheduler2.workers.get(i4);
                                                                obj2.getClass();
                                                                Worker worker = (Worker) obj2;
                                                                coroutineScheduler2.workers.setSynchronized(i3, worker);
                                                                worker.setIndexInArray(i3);
                                                                coroutineScheduler2.parkedWorkersStackTopUpdate(worker, i4, i3);
                                                            }
                                                            coroutineScheduler2.workers.setSynchronized(i4, null);
                                                            Unit unit = Unit.INSTANCE;
                                                            this.state = workerState4;
                                                        }
                                                    }
                                                }
                                            } catch (Throwable th3) {
                                                throw th3;
                                            }
                                        }
                                    }
                                    i2 = 0;
                                }
                            } else {
                                CoroutineScheduler coroutineScheduler3 = CoroutineScheduler.this;
                                coroutineScheduler3.getClass();
                                if (this.nextParkedWorker == symbol) {
                                    AtomicLong atomicLong2 = coroutineScheduler3.parkedWorkersStack;
                                    do {
                                        j = atomicLong2.value;
                                        i = this.indexInArray;
                                        this.nextParkedWorker = coroutineScheduler3.workers.get((int) (j & 2097151));
                                    } while (!coroutineScheduler3.parkedWorkersStack.compareAndSet(j, ((2097152 + j) & (-2097152)) | i));
                                }
                            }
                            i2 = 0;
                        } else if (z) {
                            tryReleaseCpu(WorkerState.PARKING);
                            Thread.interrupted();
                            LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                            this.minDelayUntilStealableTaskNs = 0L;
                        } else {
                            z = true;
                        }
                    }
                }
                break loop0;
            }
            tryReleaseCpu(WorkerState.TERMINATED);
        }

        public final void setIndexInArray(int i) {
            setName(CoroutineScheduler.this.schedulerName + "-worker-" + (i == 0 ? "TERMINATED" : String.valueOf(i)));
            this.indexInArray = i;
        }

        public final void setNextParkedWorker(Object obj) {
            this.nextParkedWorker = obj;
        }

        public final boolean tryReleaseCpu(WorkerState workerState) {
            WorkerState workerState2 = this.state;
            boolean z = workerState2 == WorkerState.CPU_ACQUIRED;
            if (z) {
                CoroutineScheduler.this.controlState.addAndGet(4398046511104L);
            }
            if (workerState2 != workerState) {
                this.state = workerState;
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x0088, code lost:
        
            r7 = -2;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00bc  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00b3 A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r8v10, types: [T, java.lang.Object, kotlinx.coroutines.scheduling.Task] */
        /* JADX WARN: Type inference failed for: r8v11, types: [kotlinx.coroutines.scheduling.Task] */
        /* JADX WARN: Type inference failed for: r8v4 */
        /* JADX WARN: Type inference failed for: r8v5, types: [kotlinx.coroutines.scheduling.Task] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final kotlinx.coroutines.scheduling.Task trySteal(int r25) {
            /*
                Method dump skipped, instructions count: 224
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker.trySteal(int):kotlinx.coroutines.scheduling.Task");
        }

        public Worker(CoroutineScheduler coroutineScheduler, int i) {
            this();
            setIndexInArray(i);
        }
    }

    public /* synthetic */ CoroutineScheduler(int i, int i2, long j, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? TasksKt.IDLE_WORKER_KEEP_ALIVE_NS : j, (i3 & 8) != 0 ? TasksKt.DEFAULT_SCHEDULER_NAME : str);
    }
}
