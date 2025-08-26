package kotlinx.coroutines.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.DisposeOnCancel;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public class SharedFlowImpl extends AbstractSharedFlow implements MutableSharedFlow, CancellableFlow, FusibleFlow {
    public Object[] buffer;
    public final int bufferCapacity;
    public int bufferSize;
    public long minCollectorIndex;
    public final BufferOverflow onBufferOverflow;
    public int queueSize;
    public final int replay;
    public long replayIndex;

    public final class Emitter implements DisposableHandle {
        public final Continuation cont;
        public final SharedFlowImpl flow;
        public final long index;
        public final Object value;

        public Emitter(SharedFlowImpl sharedFlowImpl, long j, Object obj, Continuation continuation) {
            this.flow = sharedFlowImpl;
            this.index = j;
            this.value = obj;
            this.cont = continuation;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            SharedFlowImpl sharedFlowImpl = this.flow;
            synchronized (sharedFlowImpl) {
                if (this.index < sharedFlowImpl.getHead()) {
                    return;
                }
                Object[] objArr = sharedFlowImpl.buffer;
                objArr.getClass();
                long j = this.index;
                Symbol symbol = SharedFlowKt.NO_VALUE;
                if (objArr[((int) j) & (objArr.length - 1)] != this) {
                    return;
                }
                SharedFlowKt.access$setBufferAt(objArr, j, SharedFlowKt.NO_VALUE);
                sharedFlowImpl.cleanupTailLocked();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            try {
                iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: kotlinx.coroutines.flow.SharedFlowImpl$collect$1, reason: invalid class name */
    public final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SharedFlowImpl.collect$suspendImpl(SharedFlowImpl.this, null, this);
        }
    }

    public SharedFlowImpl(int i, int i2, BufferOverflow bufferOverflow) {
        this.replay = i;
        this.bufferCapacity = i2;
        this.onBufferOverflow = bufferOverflow;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0090, code lost:
    
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r9).onSubscription(r0) == r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CoroutineSingletons collect$suspendImpl(SharedFlowImpl sharedFlowImpl, FlowCollector flowCollector, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        SharedFlowImpl sharedFlowImpl2;
        Throwable th;
        SharedFlowSlot sharedFlowSlot;
        FlowCollector flowCollector2;
        Job job;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = sharedFlowImpl.new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                sharedFlowSlot = (SharedFlowSlot) anonymousClass1.L$2;
                FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$1;
                SharedFlowImpl sharedFlowImpl3 = (SharedFlowImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    flowCollector2 = flowCollector3;
                    sharedFlowImpl = sharedFlowImpl3;
                    try {
                        job = (Job) anonymousClass1.getContext().get(Job.Key);
                    } catch (Throwable th2) {
                        sharedFlowImpl2 = sharedFlowImpl;
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    sharedFlowImpl2 = sharedFlowImpl3;
                }
            } else {
                if (i2 != 2 && i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Job job2 = (Job) anonymousClass1.L$3;
                sharedFlowSlot = (SharedFlowSlot) anonymousClass1.L$2;
                FlowCollector flowCollector4 = (FlowCollector) anonymousClass1.L$1;
                sharedFlowImpl2 = (SharedFlowImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    flowCollector2 = flowCollector4;
                    job = job2;
                    sharedFlowImpl = sharedFlowImpl2;
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            sharedFlowImpl2.freeSlot(sharedFlowSlot);
            throw th;
        }
        ResultKt.throwOnFailure(obj);
        SharedFlowSlot sharedFlowSlot2 = (SharedFlowSlot) sharedFlowImpl.allocateSlot();
        try {
            if (flowCollector instanceof SubscribedFlowCollector) {
                anonymousClass1.L$0 = sharedFlowImpl;
                anonymousClass1.L$1 = flowCollector;
                anonymousClass1.L$2 = sharedFlowSlot2;
                anonymousClass1.label = 1;
            }
            flowCollector2 = flowCollector;
            sharedFlowSlot = sharedFlowSlot2;
            job = (Job) anonymousClass1.getContext().get(Job.Key);
        } catch (Throwable th5) {
            sharedFlowImpl2 = sharedFlowImpl;
            th = th5;
            sharedFlowSlot = sharedFlowSlot2;
        }
        while (true) {
            Object objTryTakeValue = sharedFlowImpl.tryTakeValue(sharedFlowSlot);
            if (objTryTakeValue == SharedFlowKt.NO_VALUE) {
                anonymousClass1.L$0 = sharedFlowImpl;
                anonymousClass1.L$1 = flowCollector2;
                anonymousClass1.L$2 = sharedFlowSlot;
                anonymousClass1.L$3 = job;
                anonymousClass1.label = 2;
                if (sharedFlowImpl.awaitValue(sharedFlowSlot, anonymousClass1) == coroutineSingletons) {
                    break;
                }
            } else {
                if (job != null && !job.isActive()) {
                    throw job.getCancellationException();
                }
                anonymousClass1.L$0 = sharedFlowImpl;
                anonymousClass1.L$1 = flowCollector2;
                anonymousClass1.L$2 = sharedFlowSlot;
                anonymousClass1.L$3 = job;
                anonymousClass1.label = 3;
                if (flowCollector2.emit(objTryTakeValue, anonymousClass1) == coroutineSingletons) {
                    break;
                }
            }
        }
        return coroutineSingletons;
    }

    public final Object awaitValue(SharedFlowSlot sharedFlowSlot, Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        synchronized (this) {
            if (tryPeekLocked(sharedFlowSlot) < 0) {
                sharedFlowSlot.cont = cancellableContinuationImpl;
            } else {
                int i = Result.$r8$clinit;
                cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
            }
            Unit unit = Unit.INSTANCE;
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    public final void cleanupTailLocked() {
        if (this.bufferCapacity != 0 || this.queueSize > 1) {
            Object[] objArr = this.buffer;
            objArr.getClass();
            while (this.queueSize > 0) {
                long head = getHead();
                int i = this.bufferSize;
                int i2 = this.queueSize;
                if (objArr[((int) ((head + (i + i2)) - 1)) & (objArr.length - 1)] != SharedFlowKt.NO_VALUE) {
                    return;
                }
                this.queueSize = i2 - 1;
                SharedFlowKt.access$setBufferAt(objArr, getHead() + this.bufferSize + this.queueSize, null);
            }
        }
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        return collect$suspendImpl(this, flowCollector, continuation);
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot createSlot() {
        return new SharedFlowSlot();
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot[] createSlotArray() {
        return new SharedFlowSlot[2];
    }

    public final void dropOldestLocked() {
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        Object[] objArr = this.buffer;
        objArr.getClass();
        SharedFlowKt.access$setBufferAt(objArr, getHead(), null);
        this.bufferSize--;
        long head = getHead() + 1;
        if (this.replayIndex < head) {
            this.replayIndex = head;
        }
        if (this.minCollectorIndex < head) {
            if (this.nCollectors != 0 && (abstractSharedFlowSlotArr = this.slots) != null) {
                for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                    if (abstractSharedFlowSlot != null) {
                        SharedFlowSlot sharedFlowSlot = (SharedFlowSlot) abstractSharedFlowSlot;
                        long j = sharedFlowSlot.index;
                        if (j >= 0 && j < head) {
                            sharedFlowSlot.index = head;
                        }
                    }
                }
            }
            this.minCollectorIndex = head;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0081 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0082  */
    @Override // kotlinx.coroutines.flow.MutableSharedFlow, kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) throws Throwable {
        SharedFlowImpl sharedFlowImpl;
        Throwable th;
        Continuation[] continuationArrFindSlotsToResumeLocked;
        Emitter emitter;
        Object result;
        CoroutineSingletons coroutineSingletons;
        if (tryEmit(obj)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Continuation[] continuationArrFindSlotsToResumeLocked2 = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            try {
                if (tryEmitLocked(obj)) {
                    try {
                        int i = Result.$r8$clinit;
                        cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                        continuationArrFindSlotsToResumeLocked = findSlotsToResumeLocked(continuationArrFindSlotsToResumeLocked2);
                        emitter = null;
                        sharedFlowImpl = this;
                        if (emitter != null) {
                            cancellableContinuationImpl.invokeOnCancellationImpl(new DisposeOnCancel(emitter));
                        }
                        for (Continuation continuation2 : continuationArrFindSlotsToResumeLocked) {
                            if (continuation2 != null) {
                                int i2 = Result.$r8$clinit;
                                continuation2.resumeWith(Unit.INSTANCE);
                            }
                        }
                        result = cancellableContinuationImpl.getResult();
                        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (result != coroutineSingletons) {
                            result = Unit.INSTANCE;
                        }
                        return result != coroutineSingletons ? result : Unit.INSTANCE;
                    } catch (Throwable th2) {
                        th = th2;
                        sharedFlowImpl = this;
                        throw th;
                    }
                }
                try {
                    sharedFlowImpl = this;
                    try {
                        Emitter emitter2 = new Emitter(sharedFlowImpl, getHead() + this.bufferSize + this.queueSize, obj, cancellableContinuationImpl);
                        sharedFlowImpl.enqueueLocked(emitter2);
                        sharedFlowImpl.queueSize++;
                        if (sharedFlowImpl.bufferCapacity == 0) {
                            continuationArrFindSlotsToResumeLocked2 = sharedFlowImpl.findSlotsToResumeLocked(continuationArrFindSlotsToResumeLocked2);
                        }
                        continuationArrFindSlotsToResumeLocked = continuationArrFindSlotsToResumeLocked2;
                        emitter = emitter2;
                        if (emitter != null) {
                        }
                        while (i < r7) {
                        }
                        result = cancellableContinuationImpl.getResult();
                        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (result != coroutineSingletons) {
                        }
                        if (result != coroutineSingletons) {
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        th = th;
                        throw th;
                    }
                } catch (Throwable th4) {
                    sharedFlowImpl = this;
                    th = th4;
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                sharedFlowImpl = this;
            }
        }
    }

    public final void enqueueLocked(Object obj) {
        int i = this.bufferSize + this.queueSize;
        Object[] objArrGrowBuffer = this.buffer;
        if (objArrGrowBuffer == null) {
            objArrGrowBuffer = growBuffer(0, 2, null);
        } else if (i >= objArrGrowBuffer.length) {
            objArrGrowBuffer = growBuffer(i, objArrGrowBuffer.length * 2, objArrGrowBuffer);
        }
        SharedFlowKt.access$setBufferAt(objArrGrowBuffer, getHead() + i, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Continuation[] findSlotsToResumeLocked(Continuation[] continuationArr) {
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        SharedFlowSlot sharedFlowSlot;
        CancellableContinuationImpl cancellableContinuationImpl;
        int length = continuationArr.length;
        if (this.nCollectors != 0 && (abstractSharedFlowSlotArr = this.slots) != null) {
            int length2 = abstractSharedFlowSlotArr.length;
            int i = 0;
            continuationArr = continuationArr;
            while (i < length2) {
                AbstractSharedFlowSlot abstractSharedFlowSlot = abstractSharedFlowSlotArr[i];
                if (abstractSharedFlowSlot != null && (cancellableContinuationImpl = (sharedFlowSlot = (SharedFlowSlot) abstractSharedFlowSlot).cont) != null && tryPeekLocked(sharedFlowSlot) >= 0) {
                    int length3 = continuationArr.length;
                    continuationArr = continuationArr;
                    if (length >= length3) {
                        continuationArr = Arrays.copyOf(continuationArr, Math.max(2, continuationArr.length * 2));
                    }
                    continuationArr[length] = cancellableContinuationImpl;
                    sharedFlowSlot.cont = null;
                    length++;
                }
                i++;
                continuationArr = continuationArr;
            }
        }
        return continuationArr;
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        return SharedFlowKt.fuseSharedFlow(this, coroutineContext, i, bufferOverflow);
    }

    public final long getHead() {
        return Math.min(this.minCollectorIndex, this.replayIndex);
    }

    public final Object getLastReplayedLocked() {
        Object[] objArr = this.buffer;
        objArr.getClass();
        long head = (this.replayIndex + ((int) ((getHead() + this.bufferSize) - this.replayIndex))) - 1;
        Symbol symbol = SharedFlowKt.NO_VALUE;
        return objArr[((int) head) & (objArr.length - 1)];
    }

    @Override // kotlinx.coroutines.flow.SharedFlow
    public final List getReplayCache() {
        synchronized (this) {
            int head = (int) ((getHead() + this.bufferSize) - this.replayIndex);
            if (head == 0) {
                return EmptyList.INSTANCE;
            }
            ArrayList arrayList = new ArrayList(head);
            Object[] objArr = this.buffer;
            objArr.getClass();
            for (int i = 0; i < head; i++) {
                long j = this.replayIndex + i;
                Symbol symbol = SharedFlowKt.NO_VALUE;
                arrayList.add(objArr[((int) j) & (objArr.length - 1)]);
            }
            return arrayList;
        }
    }

    public final Object[] growBuffer(int i, int i2, Object[] objArr) {
        if (i2 <= 0) {
            throw new IllegalStateException("Buffer size overflow");
        }
        Object[] objArr2 = new Object[i2];
        this.buffer = objArr2;
        if (objArr != null) {
            long head = getHead();
            for (int i3 = 0; i3 < i; i3++) {
                long j = i3 + head;
                SharedFlowKt.access$setBufferAt(objArr2, j, objArr[((int) j) & (objArr.length - 1)]);
            }
        }
        return objArr2;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final void resetReplayCache() throws Throwable {
        SharedFlowImpl sharedFlowImpl;
        synchronized (this) {
            try {
                sharedFlowImpl = this;
                try {
                    sharedFlowImpl.updateBufferLocked(getHead() + this.bufferSize, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
                sharedFlowImpl = this;
            }
        }
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final boolean tryEmit(Object obj) {
        int i;
        boolean z;
        Continuation[] continuationArrFindSlotsToResumeLocked = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(obj)) {
                continuationArrFindSlotsToResumeLocked = findSlotsToResumeLocked(continuationArrFindSlotsToResumeLocked);
                z = true;
            } else {
                z = false;
            }
        }
        for (Continuation continuation : continuationArrFindSlotsToResumeLocked) {
            if (continuation != null) {
                int i2 = Result.$r8$clinit;
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean tryEmitLocked(Object obj) {
        int i;
        long head;
        long j;
        int i2 = this.nCollectors;
        int i3 = this.replay;
        if (i2 != 0) {
            int i4 = this.bufferSize;
            int i5 = this.bufferCapacity;
            if (i4 < i5 || this.minCollectorIndex > this.replayIndex) {
                enqueueLocked(obj);
                i = this.bufferSize + 1;
                this.bufferSize = i;
                if (i > i5) {
                    dropOldestLocked();
                }
                head = getHead() + this.bufferSize;
                j = this.replayIndex;
                if (((int) (head - j)) > i3) {
                    updateBufferLocked(1 + j, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
                }
            } else {
                int i6 = WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
                if (i6 == 1) {
                    return false;
                }
                if (i6 != 2) {
                    if (i6 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    enqueueLocked(obj);
                    i = this.bufferSize + 1;
                    this.bufferSize = i;
                    if (i > i5) {
                    }
                    head = getHead() + this.bufferSize;
                    j = this.replayIndex;
                    if (((int) (head - j)) > i3) {
                    }
                }
            }
        } else if (i3 != 0) {
            enqueueLocked(obj);
            int i7 = this.bufferSize + 1;
            this.bufferSize = i7;
            if (i7 > i3) {
                dropOldestLocked();
            }
            this.minCollectorIndex = getHead() + this.bufferSize;
            return true;
        }
        return true;
    }

    public final long tryPeekLocked(SharedFlowSlot sharedFlowSlot) {
        long j = sharedFlowSlot.index;
        if (j >= getHead() + this.bufferSize && (this.bufferCapacity > 0 || j > getHead() || this.queueSize == 0)) {
            return -1L;
        }
        return j;
    }

    public final Object tryTakeValue(SharedFlowSlot sharedFlowSlot) {
        Object obj;
        Continuation[] continuationArrUpdateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            try {
                long jTryPeekLocked = tryPeekLocked(sharedFlowSlot);
                if (jTryPeekLocked < 0) {
                    obj = SharedFlowKt.NO_VALUE;
                } else {
                    long j = sharedFlowSlot.index;
                    Object[] objArr = this.buffer;
                    objArr.getClass();
                    Symbol symbol = SharedFlowKt.NO_VALUE;
                    Object obj2 = objArr[((int) jTryPeekLocked) & (objArr.length - 1)];
                    if (obj2 instanceof Emitter) {
                        obj2 = ((Emitter) obj2).value;
                    }
                    sharedFlowSlot.index = jTryPeekLocked + 1;
                    Object obj3 = obj2;
                    continuationArrUpdateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = updateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(j);
                    obj = obj3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (Continuation continuation : continuationArrUpdateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) {
            if (continuation != null) {
                int i = Result.$r8$clinit;
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return obj;
    }

    public final void updateBufferLocked(long j, long j2, long j3, long j4) {
        long jMin = Math.min(j2, j);
        for (long head = getHead(); head < jMin; head++) {
            Object[] objArr = this.buffer;
            objArr.getClass();
            SharedFlowKt.access$setBufferAt(objArr, head, null);
        }
        this.replayIndex = j;
        this.minCollectorIndex = j2;
        this.bufferSize = (int) (j3 - jMin);
        this.queueSize = (int) (j4 - j3);
    }

    public final Continuation[] updateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(long j) {
        int i;
        long j2;
        long j3;
        Continuation[] continuationArr;
        Continuation[] continuationArr2;
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        long j4 = this.minCollectorIndex;
        Continuation[] continuationArr3 = AbstractSharedFlowKt.EMPTY_RESUMES;
        if (j <= j4) {
            long head = getHead();
            long j5 = this.bufferSize + head;
            int i2 = this.bufferCapacity;
            if (i2 == 0 && this.queueSize > 0) {
                j5++;
            }
            int i3 = 0;
            if (this.nCollectors != 0 && (abstractSharedFlowSlotArr = this.slots) != null) {
                for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                    if (abstractSharedFlowSlot != null) {
                        long j6 = ((SharedFlowSlot) abstractSharedFlowSlot).index;
                        if (j6 >= 0 && j6 < j5) {
                            j5 = j6;
                        }
                    }
                }
            }
            if (j5 > this.minCollectorIndex) {
                long head2 = getHead() + this.bufferSize;
                int iMin = this.nCollectors > 0 ? Math.min(this.queueSize, i2 - ((int) (head2 - j5))) : this.queueSize;
                long j7 = this.queueSize + head2;
                if (iMin > 0) {
                    Continuation[] continuationArr4 = new Continuation[iMin];
                    Object[] objArr = this.buffer;
                    objArr.getClass();
                    j3 = 1;
                    long j8 = head2;
                    while (true) {
                        if (head2 >= j7) {
                            i = i2;
                            continuationArr2 = continuationArr4;
                            j2 = head;
                            head2 = j8;
                            break;
                        }
                        i = i2;
                        Object obj = objArr[((int) head2) & (objArr.length - 1)];
                        continuationArr2 = continuationArr4;
                        Symbol symbol = SharedFlowKt.NO_VALUE;
                        if (obj != symbol) {
                            Emitter emitter = (Emitter) obj;
                            j2 = head;
                            int i4 = i3 + 1;
                            continuationArr2[i3] = emitter.cont;
                            SharedFlowKt.access$setBufferAt(objArr, head2, symbol);
                            SharedFlowKt.access$setBufferAt(objArr, j8, emitter.value);
                            long j9 = j8 + 1;
                            if (i4 >= iMin) {
                                head2 = j9;
                                break;
                            }
                            j8 = j9;
                            i3 = i4;
                        } else {
                            j2 = head;
                        }
                        head2++;
                        i2 = i;
                        continuationArr4 = continuationArr2;
                        head = j2;
                    }
                    continuationArr = continuationArr2;
                } else {
                    i = i2;
                    j2 = head;
                    j3 = 1;
                    continuationArr = continuationArr3;
                }
                int i5 = (int) (head2 - j2);
                long j10 = this.nCollectors == 0 ? head2 : j5;
                long jMax = Math.max(this.replayIndex, head2 - Math.min(this.replay, i5));
                if (i == 0 && jMax < j7) {
                    Object[] objArr2 = this.buffer;
                    objArr2.getClass();
                    if (Intrinsics.areEqual(objArr2[((int) jMax) & (objArr2.length - 1)], SharedFlowKt.NO_VALUE)) {
                        head2 += j3;
                        jMax += j3;
                    }
                }
                updateBufferLocked(jMax, j10, head2, j7);
                cleanupTailLocked();
                return continuationArr.length == 0 ? continuationArr : findSlotsToResumeLocked(continuationArr);
            }
        }
        return continuationArr3;
    }
}
