package kotlinx.coroutines.scheduling;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ResizableAtomicArray;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum WorkerState {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        DORMANT,
        TERMINATED
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
        if (!(i >= 1)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Core pool size ", i, " should be at least 1").toString());
        }
        if (!(i2 >= i)) {
            throw new IllegalArgumentException(AbstractC0970x34f4116a.m94m("Max pool size ", i2, " should be greater than or equals to core pool size ", i).toString());
        }
        if (!(i2 <= 2097150)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Max pool size ", i2, " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (!(j > 0)) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
        }
        this.globalCpuQueue = new GlobalQueue();
        this.globalBlockingQueue = new GlobalQueue();
        TraceBase.None none = TraceBase.None.INSTANCE;
        this.parkedWorkersStack = new AtomicLong(0L, none);
        this.workers = new ResizableAtomicArray(i + 1);
        this.controlState = new AtomicLong(i << 42, none);
        this._isTerminated = AtomicFU.atomic(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0096, code lost:
    
        if (r1 == null) goto L46;
     */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void close() {
        int i;
        Task task;
        boolean z;
        if (this._isTerminated.compareAndSet()) {
            Thread currentThread = Thread.currentThread();
            Worker worker = currentThread instanceof Worker ? (Worker) currentThread : null;
            if (worker == null || !Intrinsics.areEqual(CoroutineScheduler.this, this)) {
                worker = null;
            }
            synchronized (this.workers) {
                i = (int) (this.controlState.value & 2097151);
            }
            if (1 <= i) {
                int i2 = 1;
                while (true) {
                    Object obj = this.workers.get(i2);
                    Intrinsics.checkNotNull(obj);
                    Worker worker2 = (Worker) obj;
                    if (worker2 != worker) {
                        while (worker2.isAlive()) {
                            LockSupport.unpark(worker2);
                            worker2.join(10000L);
                        }
                        WorkQueue workQueue = worker2.localQueue;
                        GlobalQueue globalQueue = this.globalBlockingQueue;
                        Task task2 = (Task) workQueue.lastScheduledTask.getAndSet(null);
                        if (task2 != null) {
                            globalQueue.addLast(task2);
                        }
                        do {
                            Task pollBuffer = workQueue.pollBuffer();
                            if (pollBuffer == null) {
                                z = false;
                            } else {
                                globalQueue.addLast(pollBuffer);
                                z = true;
                            }
                        } while (z);
                    }
                    if (i2 == i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            GlobalQueue globalQueue2 = this.globalBlockingQueue;
            AtomicRef atomicRef = globalQueue2._cur;
            while (true) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
                if (lockFreeTaskQueueCore.close()) {
                    break;
                } else {
                    globalQueue2._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
                }
            }
            GlobalQueue globalQueue3 = this.globalCpuQueue;
            AtomicRef atomicRef2 = globalQueue3._cur;
            while (true) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = (LockFreeTaskQueueCore) atomicRef2.value;
                if (lockFreeTaskQueueCore2.close()) {
                    break;
                } else {
                    globalQueue3._cur.compareAndSet(lockFreeTaskQueueCore2, lockFreeTaskQueueCore2.next());
                }
            }
            while (true) {
                if (worker != null) {
                    task = worker.findTask(true);
                }
                task = (Task) this.globalCpuQueue.removeFirstOrNull();
                if (task == null && (task = (Task) this.globalBlockingQueue.removeFirstOrNull()) == null) {
                    break;
                }
                try {
                    task.run();
                } catch (Throwable th) {
                    Thread currentThread2 = Thread.currentThread();
                    currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th);
                }
            }
            if (worker != null) {
                worker.tryReleaseCpu(WorkerState.TERMINATED);
            }
            AtomicLong atomicLong = this.parkedWorkersStack;
            atomicLong.value = 0L;
            TraceBase traceBase = atomicLong.trace;
            TraceBase.None none = TraceBase.None.INSTANCE;
            if (traceBase != none) {
                traceBase.getClass();
            }
            AtomicLong atomicLong2 = this.controlState;
            atomicLong2.value = 0L;
            TraceBase traceBase2 = atomicLong2.trace;
            if (traceBase2 != none) {
                traceBase2.getClass();
            }
        }
    }

    public final int createNewWorker() {
        synchronized (this.workers) {
            if (this._isTerminated._value != 0) {
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
            if (!(i3 > 0 && this.workers.get(i3) == null)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            Worker worker = new Worker(this, i3);
            this.workers.setSynchronized(i3, worker);
            AtomicLong atomicLong = this.controlState;
            atomicLong.getClass();
            long incrementAndGet = AtomicLong.f664FU.incrementAndGet(atomicLong);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicLong.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
            if (!(i3 == ((int) (2097151 & incrementAndGet)))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            worker.start();
            return i2 + 1;
        }
    }

    public final void dispatch(Runnable runnable, TaskContext taskContext, boolean z) {
        Task taskImpl;
        Task task;
        WorkerState workerState;
        TasksKt.schedulerTimeSource.getClass();
        long nanoTime = System.nanoTime();
        if (runnable instanceof Task) {
            taskImpl = (Task) runnable;
            taskImpl.submissionTime = nanoTime;
            taskImpl.taskContext = taskContext;
        } else {
            taskImpl = new TaskImpl(runnable, nanoTime, taskContext);
        }
        Thread currentThread = Thread.currentThread();
        Worker worker = null;
        Worker worker2 = currentThread instanceof Worker ? (Worker) currentThread : null;
        if (worker2 != null && Intrinsics.areEqual(CoroutineScheduler.this, this)) {
            worker = worker2;
        }
        if (worker == null || (workerState = worker.state) == WorkerState.TERMINATED || (((TaskContextImpl) taskImpl.taskContext).taskMode == 0 && workerState == WorkerState.BLOCKING)) {
            task = taskImpl;
        } else {
            worker.mayHaveLocalTasks = true;
            task = worker.localQueue.add(taskImpl, z);
        }
        if (task != null) {
            if (!(((TaskContextImpl) task.taskContext).taskMode == 1 ? this.globalBlockingQueue.addLast(task) : this.globalCpuQueue.addLast(task))) {
                throw new RejectedExecutionException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(this.schedulerName, " was terminated"));
            }
        }
        boolean z2 = z && worker != null;
        if (((TaskContextImpl) taskImpl.taskContext).taskMode == 0) {
            if (z2 || tryUnpark() || tryCreateWorker(this.controlState.value)) {
                return;
            }
            tryUnpark();
            return;
        }
        AtomicLong atomicLong = this.controlState;
        atomicLong.getClass();
        long addAndGet = AtomicLong.f664FU.addAndGet(atomicLong, 2097152L);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = atomicLong.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        if (z2 || tryUnpark() || tryCreateWorker(addAndGet)) {
            return;
        }
        tryUnpark();
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        dispatch(runnable, TasksKt.NonBlockingContext, false);
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
                Object obj = workQueue.lastScheduledTask.value;
                int m298x84356e59 = workQueue.m298x84356e59();
                if (obj != null) {
                    m298x84356e59++;
                }
                int i7 = WhenMappings.$EnumSwitchMapping$0[worker.state.ordinal()];
                if (i7 == 1) {
                    i3++;
                } else if (i7 == 2) {
                    i2++;
                    arrayList.add(m298x84356e59 + "b");
                } else if (i7 == 3) {
                    i++;
                    arrayList.add(m298x84356e59 + "c");
                } else if (i7 == 4) {
                    i4++;
                    if (m298x84356e59 > 0) {
                        arrayList.add(m298x84356e59 + "d");
                    }
                } else if (i7 == 5) {
                    i5++;
                }
            }
        }
        long j = this.controlState.value;
        return this.schedulerName + "@" + DebugStringsKt.getHexAddress(this) + "[Pool Size {core = " + this.corePoolSize + ", max = " + this.maxPoolSize + "}, Worker States {CPU = " + i + ", blocking = " + i2 + ", parked = " + i3 + ", dormant = " + i4 + ", terminated = " + i5 + "}, running workers queues = " + arrayList + ", global CPU queue size = " + this.globalCpuQueue.getSize() + ", global blocking queue size = " + this.globalBlockingQueue.getSize() + ", Control State {created workers= " + ((int) (j & 2097151)) + ", blocking tasks = " + ((int) ((4398044413952L & j) >> 21)) + ", CPUs acquired = " + (this.corePoolSize - ((int) ((j & 9223367638808264704L) >> 42))) + "}]";
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Worker extends Thread {
        private volatile int indexInArray;
        public final WorkQueue localQueue;
        public boolean mayHaveLocalTasks;
        public long minDelayUntilStealableTaskNs;
        private volatile Object nextParkedWorker;
        public int rngState;
        public WorkerState state;
        public long terminationDeadline;
        public final AtomicInt workerCtl;

        private Worker() {
            setDaemon(true);
            this.localQueue = new WorkQueue();
            this.state = WorkerState.DORMANT;
            this.workerCtl = AtomicFU.atomic();
            this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
            this.rngState = Random.Default.nextInt();
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Task findTask(boolean z) {
            boolean z2;
            Task task;
            Task pollGlobalQueues;
            Task pollGlobalQueues2;
            boolean z3;
            if (this.state != WorkerState.CPU_ACQUIRED) {
                CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
                AtomicLong atomicLong = coroutineScheduler.controlState;
                while (true) {
                    long j = atomicLong.value;
                    if (((int) ((9223367638808264704L & j) >> 42)) == 0) {
                        z3 = false;
                        break;
                    }
                    if (coroutineScheduler.controlState.compareAndSet(j, j - SemWallpaperColorsWrapper.LOCKSCREEN_FINGERPRINT)) {
                        z3 = true;
                        break;
                    }
                }
                if (!z3) {
                    z2 = false;
                    if (z2) {
                        if (z) {
                            WorkQueue workQueue = this.localQueue;
                            task = (Task) workQueue.lastScheduledTask.getAndSet(null);
                            if (task == null) {
                                task = workQueue.pollBuffer();
                            }
                            if (task == null) {
                                task = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
                            }
                        } else {
                            task = (Task) CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
                        }
                        return task == null ? trySteal(true) : task;
                    }
                    if (z) {
                        boolean z4 = nextInt(CoroutineScheduler.this.corePoolSize * 2) == 0;
                        if (z4 && (pollGlobalQueues2 = pollGlobalQueues()) != null) {
                            return pollGlobalQueues2;
                        }
                        WorkQueue workQueue2 = this.localQueue;
                        Task task2 = (Task) workQueue2.lastScheduledTask.getAndSet(null);
                        Task pollBuffer = task2 == null ? workQueue2.pollBuffer() : task2;
                        if (pollBuffer != null) {
                            return pollBuffer;
                        }
                        if (!z4 && (pollGlobalQueues = pollGlobalQueues()) != null) {
                            return pollGlobalQueues;
                        }
                    } else {
                        Task pollGlobalQueues3 = pollGlobalQueues();
                        if (pollGlobalQueues3 != null) {
                            return pollGlobalQueues3;
                        }
                    }
                    return trySteal(false);
                }
                this.state = WorkerState.CPU_ACQUIRED;
            }
            z2 = true;
            if (z2) {
            }
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
        /* JADX WARN: Type inference failed for: r0v0 */
        /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r0v19 */
        /* JADX WARN: Type inference failed for: r0v26 */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v6 */
        /* JADX WARN: Type inference failed for: r12v7 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v22 */
        /* JADX WARN: Type inference failed for: r3v23 */
        /* JADX WARN: Type inference failed for: r3v32 */
        /* JADX WARN: Type inference failed for: r3v33 */
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            long j;
            int i;
            loop0: while (true) {
                ?? r0 = 0;
                boolean z = false;
                while (true) {
                    if (!(CoroutineScheduler.this._isTerminated._value != 0 ? true : r0 == true ? 1 : 0)) {
                        WorkerState workerState = this.state;
                        WorkerState workerState2 = WorkerState.TERMINATED;
                        if (workerState == workerState2) {
                            break loop0;
                        }
                        Task findTask = findTask(this.mayHaveLocalTasks);
                        if (findTask != null) {
                            this.minDelayUntilStealableTaskNs = 0L;
                            int i2 = ((TaskContextImpl) findTask.taskContext).taskMode;
                            this.terminationDeadline = 0L;
                            if (this.state == WorkerState.PARKING) {
                                this.state = WorkerState.BLOCKING;
                            }
                            if (i2 != 0 && tryReleaseCpu(WorkerState.BLOCKING)) {
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
                            if (i2 != 0) {
                                AtomicLong atomicLong = CoroutineScheduler.this.controlState;
                                atomicLong.getClass();
                                AtomicLong.f664FU.addAndGet(atomicLong, -2097152L);
                                TraceBase.None none = TraceBase.None.INSTANCE;
                                TraceBase traceBase = atomicLong.trace;
                                if (traceBase != none) {
                                    traceBase.getClass();
                                }
                                if (this.state != workerState2) {
                                    this.state = WorkerState.DORMANT;
                                }
                            }
                        } else {
                            this.mayHaveLocalTasks = r0;
                            if (this.minDelayUntilStealableTaskNs == 0) {
                                Object obj = this.nextParkedWorker;
                                Symbol symbol = CoroutineScheduler.NOT_IN_STACK;
                                if (obj != symbol ? true : r0 == true ? 1 : 0) {
                                    this.workerCtl.setValue(-1);
                                    int i3 = r0;
                                    while (true) {
                                        if ((this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK ? true : i3) != true || this.workerCtl.value != -1) {
                                            break;
                                        }
                                        if ((CoroutineScheduler.this._isTerminated._value != 0 ? true : i3) == true) {
                                            break;
                                        }
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
                                                if ((coroutineScheduler2._isTerminated._value != 0 ? true : i3) == false) {
                                                    if (((int) (coroutineScheduler2.controlState.value & 2097151)) > coroutineScheduler2.corePoolSize) {
                                                        if (this.workerCtl.compareAndSet(-1, 1)) {
                                                            int i4 = this.indexInArray;
                                                            setIndexInArray(i3);
                                                            coroutineScheduler2.parkedWorkersStackTopUpdate(this, i4, i3);
                                                            AtomicLong atomicLong2 = coroutineScheduler2.controlState;
                                                            atomicLong2.getClass();
                                                            long andDecrement = AtomicLong.f664FU.getAndDecrement(atomicLong2);
                                                            TraceBase.None none2 = TraceBase.None.INSTANCE;
                                                            TraceBase traceBase2 = atomicLong2.trace;
                                                            if (traceBase2 != none2) {
                                                                traceBase2.getClass();
                                                            }
                                                            int i5 = (int) (andDecrement & 2097151);
                                                            if (i5 != i4) {
                                                                Object obj2 = coroutineScheduler2.workers.get(i5);
                                                                Intrinsics.checkNotNull(obj2);
                                                                Worker worker = (Worker) obj2;
                                                                coroutineScheduler2.workers.setSynchronized(i4, worker);
                                                                worker.setIndexInArray(i4);
                                                                coroutineScheduler2.parkedWorkersStackTopUpdate(worker, i5, i4);
                                                            }
                                                            coroutineScheduler2.workers.setSynchronized(i5, null);
                                                            Unit unit = Unit.INSTANCE;
                                                            this.state = workerState4;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        i3 = 0;
                                    }
                                } else {
                                    CoroutineScheduler coroutineScheduler3 = CoroutineScheduler.this;
                                    coroutineScheduler3.getClass();
                                    if (this.nextParkedWorker == symbol) {
                                        AtomicLong atomicLong3 = coroutineScheduler3.parkedWorkersStack;
                                        do {
                                            j = atomicLong3.value;
                                            i = this.indexInArray;
                                            this.nextParkedWorker = coroutineScheduler3.workers.get((int) (j & 2097151));
                                        } while (!coroutineScheduler3.parkedWorkersStack.compareAndSet(j, ((2097152 + j) & (-2097152)) | i));
                                    }
                                }
                                r0 = 0;
                            } else {
                                if (z) {
                                    tryReleaseCpu(WorkerState.PARKING);
                                    Thread.interrupted();
                                    LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                                    this.minDelayUntilStealableTaskNs = 0L;
                                    break;
                                }
                                z = true;
                            }
                        }
                    } else {
                        break loop0;
                    }
                }
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
                AtomicLong atomicLong = CoroutineScheduler.this.controlState;
                atomicLong.getClass();
                AtomicLong.f664FU.addAndGet(atomicLong, SemWallpaperColorsWrapper.LOCKSCREEN_FINGERPRINT);
                TraceBase.None none = TraceBase.None.INSTANCE;
                TraceBase traceBase = atomicLong.trace;
                if (traceBase != none) {
                    traceBase.getClass();
                }
            }
            if (workerState2 != workerState) {
                this.state = workerState;
            }
            return z;
        }

        public final Task trySteal(boolean z) {
            long tryStealLastScheduled;
            int i = (int) (CoroutineScheduler.this.controlState.value & 2097151);
            if (i < 2) {
                return null;
            }
            int nextInt = nextInt(i);
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            long j = Long.MAX_VALUE;
            for (int i2 = 0; i2 < i; i2++) {
                nextInt++;
                if (nextInt > i) {
                    nextInt = 1;
                }
                Worker worker = (Worker) coroutineScheduler.workers.get(nextInt);
                if (worker != null && worker != this) {
                    if (z) {
                        WorkQueue workQueue = this.localQueue;
                        WorkQueue workQueue2 = worker.localQueue;
                        workQueue.getClass();
                        int i3 = workQueue2.producerIndex.value;
                        AtomicReferenceArray atomicReferenceArray = workQueue2.buffer;
                        for (int i4 = workQueue2.consumerIndex.value; i4 != i3; i4++) {
                            int i5 = i4 & 127;
                            if (workQueue2.blockingTasksInBuffer.value == 0) {
                                break;
                            }
                            Task task = (Task) atomicReferenceArray.get(i5);
                            if (task != null) {
                                if ((((TaskContextImpl) task.taskContext).taskMode == 1) && atomicReferenceArray.compareAndSet(i5, task, null)) {
                                    AtomicInt atomicInt = workQueue2.blockingTasksInBuffer;
                                    atomicInt.getClass();
                                    AtomicInt.f663FU.decrementAndGet(atomicInt);
                                    TraceBase.None none = TraceBase.None.INSTANCE;
                                    TraceBase traceBase = atomicInt.trace;
                                    if (traceBase != none) {
                                        traceBase.getClass();
                                    }
                                    workQueue.add(task, false);
                                    tryStealLastScheduled = -1;
                                }
                            }
                        }
                        tryStealLastScheduled = workQueue.tryStealLastScheduled(workQueue2, true);
                    } else {
                        WorkQueue workQueue3 = this.localQueue;
                        WorkQueue workQueue4 = worker.localQueue;
                        workQueue3.getClass();
                        Task pollBuffer = workQueue4.pollBuffer();
                        if (pollBuffer != null) {
                            workQueue3.add(pollBuffer, false);
                            tryStealLastScheduled = -1;
                        } else {
                            tryStealLastScheduled = workQueue3.tryStealLastScheduled(workQueue4, false);
                        }
                    }
                    if (tryStealLastScheduled == -1) {
                        WorkQueue workQueue5 = this.localQueue;
                        Task task2 = (Task) workQueue5.lastScheduledTask.getAndSet(null);
                        return task2 == null ? workQueue5.pollBuffer() : task2;
                    }
                    if (tryStealLastScheduled > 0) {
                        j = Math.min(j, tryStealLastScheduled);
                    }
                }
            }
            if (j == Long.MAX_VALUE) {
                j = 0;
            }
            this.minDelayUntilStealableTaskNs = j;
            return null;
        }

        public Worker(CoroutineScheduler coroutineScheduler, int i) {
            this();
            setIndexInArray(i);
        }
    }

    public /* synthetic */ CoroutineScheduler(int i, int i2, long j, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? TasksKt.IDLE_WORKER_KEEP_ALIVE_NS : j, (i3 & 8) != 0 ? "DefaultDispatcher" : str);
    }
}
