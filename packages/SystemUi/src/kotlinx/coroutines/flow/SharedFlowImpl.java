package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Arrays;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.DisposeOnCancel;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SharedFlowImpl extends AbstractSharedFlow implements MutableSharedFlow, Flow, FusibleFlow {
    public Object[] buffer;
    public final int bufferCapacity;
    public int bufferSize;
    public long minCollectorIndex;
    public final BufferOverflow onBufferOverflow;
    public int queueSize;
    public final int replay;
    public long replayIndex;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Emitter implements DisposableHandle {
        public final Continuation cont;
        public final SharedFlowImpl flow;
        public final long index;
        public final Object value;

        public Emitter(SharedFlowImpl sharedFlowImpl, long j, Object obj, Continuation<? super Unit> continuation) {
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
                Intrinsics.checkNotNull(objArr);
                long j = this.index;
                Symbol symbol = SharedFlowKt.NO_VALUE;
                int i = (int) j;
                if (objArr[(objArr.length - 1) & i] != this) {
                    return;
                }
                objArr[i & (objArr.length - 1)] = SharedFlowKt.NO_VALUE;
                sharedFlowImpl.cleanupTailLocked();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public SharedFlowImpl(int i, int i2, BufferOverflow bufferOverflow) {
        this.replay = i;
        this.bufferCapacity = i2;
        this.onBufferOverflow = bufferOverflow;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CoroutineSingletons collect$suspendImpl(SharedFlowImpl sharedFlowImpl, FlowCollector flowCollector, Continuation continuation) {
        SharedFlowImpl$collect$1 sharedFlowImpl$collect$1;
        int i;
        SharedFlowSlot sharedFlowSlot;
        FlowCollector flowCollector2;
        SharedFlowSlot sharedFlowSlot2;
        SharedFlowImpl sharedFlowImpl2;
        Throwable th;
        Job job;
        FlowCollector flowCollector3;
        Object tryTakeValue;
        try {
            if (continuation instanceof SharedFlowImpl$collect$1) {
                sharedFlowImpl$collect$1 = (SharedFlowImpl$collect$1) continuation;
                int i2 = sharedFlowImpl$collect$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    sharedFlowImpl$collect$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj = sharedFlowImpl$collect$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = sharedFlowImpl$collect$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj);
                        sharedFlowSlot = (SharedFlowSlot) sharedFlowImpl.allocateSlot();
                        try {
                            if (flowCollector instanceof SubscribedFlowCollector) {
                                sharedFlowImpl$collect$1.L$0 = sharedFlowImpl;
                                sharedFlowImpl$collect$1.L$1 = flowCollector;
                                sharedFlowImpl$collect$1.L$2 = sharedFlowSlot;
                                sharedFlowImpl$collect$1.label = 1;
                                if (((SubscribedFlowCollector) flowCollector).onSubscription(sharedFlowImpl$collect$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                            flowCollector2 = flowCollector;
                            sharedFlowSlot2 = sharedFlowSlot;
                        } catch (Throwable th2) {
                            th = th2;
                            sharedFlowImpl.freeSlot(sharedFlowSlot);
                            throw th;
                        }
                    } else if (i == 1) {
                        sharedFlowSlot2 = (SharedFlowSlot) sharedFlowImpl$collect$1.L$2;
                        FlowCollector flowCollector4 = (FlowCollector) sharedFlowImpl$collect$1.L$1;
                        SharedFlowImpl sharedFlowImpl3 = (SharedFlowImpl) sharedFlowImpl$collect$1.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            flowCollector2 = flowCollector4;
                            sharedFlowImpl = sharedFlowImpl3;
                        } catch (Throwable th3) {
                            sharedFlowSlot = sharedFlowSlot2;
                            th = th3;
                            sharedFlowImpl = sharedFlowImpl3;
                            sharedFlowImpl.freeSlot(sharedFlowSlot);
                            throw th;
                        }
                    } else {
                        if (i != 2 && i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        job = (Job) sharedFlowImpl$collect$1.L$3;
                        sharedFlowSlot2 = (SharedFlowSlot) sharedFlowImpl$collect$1.L$2;
                        flowCollector3 = (FlowCollector) sharedFlowImpl$collect$1.L$1;
                        sharedFlowImpl2 = (SharedFlowImpl) sharedFlowImpl$collect$1.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            while (true) {
                                tryTakeValue = sharedFlowImpl2.tryTakeValue(sharedFlowSlot2);
                                if (tryTakeValue == SharedFlowKt.NO_VALUE) {
                                    sharedFlowImpl$collect$1.L$0 = sharedFlowImpl2;
                                    sharedFlowImpl$collect$1.L$1 = flowCollector3;
                                    sharedFlowImpl$collect$1.L$2 = sharedFlowSlot2;
                                    sharedFlowImpl$collect$1.L$3 = job;
                                    sharedFlowImpl$collect$1.label = 2;
                                    if (sharedFlowImpl2.awaitValue(sharedFlowSlot2, sharedFlowImpl$collect$1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (job != null && !job.isActive()) {
                                        throw ((JobSupport) job).getCancellationException();
                                    }
                                    sharedFlowImpl$collect$1.L$0 = sharedFlowImpl2;
                                    sharedFlowImpl$collect$1.L$1 = flowCollector3;
                                    sharedFlowImpl$collect$1.L$2 = sharedFlowSlot2;
                                    sharedFlowImpl$collect$1.L$3 = job;
                                    sharedFlowImpl$collect$1.label = 3;
                                    if (flowCollector3.emit(tryTakeValue, sharedFlowImpl$collect$1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            sharedFlowSlot = sharedFlowSlot2;
                            th = th;
                            sharedFlowImpl = sharedFlowImpl2;
                            sharedFlowImpl.freeSlot(sharedFlowSlot);
                            throw th;
                        }
                    }
                    sharedFlowImpl2 = sharedFlowImpl;
                    job = (Job) sharedFlowImpl$collect$1.getContext().get(Job.Key);
                    flowCollector3 = flowCollector2;
                    while (true) {
                        tryTakeValue = sharedFlowImpl2.tryTakeValue(sharedFlowSlot2);
                        if (tryTakeValue == SharedFlowKt.NO_VALUE) {
                        }
                    }
                }
            }
            sharedFlowImpl2 = sharedFlowImpl;
            job = (Job) sharedFlowImpl$collect$1.getContext().get(Job.Key);
            flowCollector3 = flowCollector2;
            while (true) {
                tryTakeValue = sharedFlowImpl2.tryTakeValue(sharedFlowSlot2);
                if (tryTakeValue == SharedFlowKt.NO_VALUE) {
                }
            }
        } catch (Throwable th5) {
            sharedFlowImpl2 = sharedFlowImpl;
            th = th5;
            sharedFlowSlot = sharedFlowSlot2;
            th = th;
            sharedFlowImpl = sharedFlowImpl2;
            sharedFlowImpl.freeSlot(sharedFlowSlot);
            throw th;
        }
        sharedFlowImpl$collect$1 = new SharedFlowImpl$collect$1(sharedFlowImpl, continuation);
        Object obj2 = sharedFlowImpl$collect$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = sharedFlowImpl$collect$1.label;
        if (i != 0) {
        }
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
            Intrinsics.checkNotNull(objArr);
            while (this.queueSize > 0) {
                long head = getHead();
                int i = this.bufferSize;
                int i2 = this.queueSize;
                if (objArr[((int) ((head + (i + i2)) - 1)) & (objArr.length - 1)] != SharedFlowKt.NO_VALUE) {
                    return;
                }
                this.queueSize = i2 - 1;
                objArr[((int) (getHead() + this.bufferSize + this.queueSize)) & (objArr.length - 1)] = null;
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
        Intrinsics.checkNotNull(objArr);
        long head = getHead();
        Symbol symbol = SharedFlowKt.NO_VALUE;
        objArr[((int) head) & (objArr.length - 1)] = null;
        this.bufferSize--;
        long head2 = getHead() + 1;
        if (this.replayIndex < head2) {
            this.replayIndex = head2;
        }
        if (this.minCollectorIndex < head2) {
            if (this.nCollectors != 0 && (abstractSharedFlowSlotArr = this.slots) != null) {
                for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                    if (abstractSharedFlowSlot != null) {
                        SharedFlowSlot sharedFlowSlot = (SharedFlowSlot) abstractSharedFlowSlot;
                        long j = sharedFlowSlot.index;
                        if (j >= 0 && j < head2) {
                            sharedFlowSlot.index = head2;
                        }
                    }
                }
            }
            this.minCollectorIndex = head2;
        }
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        Continuation[] continuationArr;
        Emitter emitter;
        if (tryEmit(obj)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Continuation[] continuationArr2 = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(obj)) {
                int i = Result.$r8$clinit;
                cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                continuationArr = findSlotsToResumeLocked(continuationArr2);
                emitter = null;
            } else {
                Emitter emitter2 = new Emitter(this, this.bufferSize + this.queueSize + getHead(), obj, cancellableContinuationImpl);
                enqueueLocked(emitter2);
                this.queueSize++;
                if (this.bufferCapacity == 0) {
                    continuationArr2 = findSlotsToResumeLocked(continuationArr2);
                }
                continuationArr = continuationArr2;
                emitter = emitter2;
            }
        }
        if (emitter != null) {
            cancellableContinuationImpl.invokeOnCancellation(new DisposeOnCancel(emitter));
        }
        for (Continuation continuation2 : continuationArr) {
            if (continuation2 != null) {
                int i2 = Result.$r8$clinit;
                continuation2.resumeWith(Unit.INSTANCE);
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        return result == coroutineSingletons ? result : Unit.INSTANCE;
    }

    public final void enqueueLocked(Object obj) {
        int i = this.bufferSize + this.queueSize;
        Object[] objArr = this.buffer;
        if (objArr == null) {
            objArr = growBuffer(0, 2, null);
        } else if (i >= objArr.length) {
            objArr = growBuffer(i, objArr.length * 2, objArr);
        }
        long head = getHead() + i;
        Symbol symbol = SharedFlowKt.NO_VALUE;
        objArr[((int) head) & (objArr.length - 1)] = obj;
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
        Symbol symbol = SharedFlowKt.NO_VALUE;
        return ((i == 0 || i == -3) && bufferOverflow == BufferOverflow.SUSPEND) ? this : new ChannelFlowOperatorImpl(this, coroutineContext, i, bufferOverflow);
    }

    public final long getHead() {
        return Math.min(this.minCollectorIndex, this.replayIndex);
    }

    public final Object[] growBuffer(int i, int i2, Object[] objArr) {
        if (!(i2 > 0)) {
            throw new IllegalStateException("Buffer size overflow".toString());
        }
        Object[] objArr2 = new Object[i2];
        this.buffer = objArr2;
        if (objArr == null) {
            return objArr2;
        }
        long head = getHead();
        for (int i3 = 0; i3 < i; i3++) {
            Symbol symbol = SharedFlowKt.NO_VALUE;
            int i4 = (int) (i3 + head);
            objArr2[i4 & (i2 - 1)] = objArr[(objArr.length - 1) & i4];
        }
        return objArr2;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final void resetReplayCache() {
        synchronized (this) {
            updateBufferLocked(getHead() + this.bufferSize, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final boolean tryEmit(Object obj) {
        int i;
        boolean z;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(obj)) {
                continuationArr = findSlotsToResumeLocked(continuationArr);
                z = true;
            } else {
                z = false;
            }
        }
        for (Continuation continuation : continuationArr) {
            if (continuation != null) {
                int i2 = Result.$r8$clinit;
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return z;
    }

    public final boolean tryEmitLocked(Object obj) {
        int i = this.nCollectors;
        int i2 = this.replay;
        if (i == 0) {
            if (i2 != 0) {
                enqueueLocked(obj);
                int i3 = this.bufferSize + 1;
                this.bufferSize = i3;
                if (i3 > i2) {
                    dropOldestLocked();
                }
                this.minCollectorIndex = getHead() + this.bufferSize;
            }
            return true;
        }
        int i4 = this.bufferSize;
        int i5 = this.bufferCapacity;
        if (i4 >= i5 && this.minCollectorIndex <= this.replayIndex) {
            int i6 = WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
            if (i6 == 1) {
                return false;
            }
            if (i6 == 2) {
                return true;
            }
        }
        enqueueLocked(obj);
        int i7 = this.bufferSize + 1;
        this.bufferSize = i7;
        if (i7 > i5) {
            dropOldestLocked();
        }
        long head = getHead() + this.bufferSize;
        long j = this.replayIndex;
        if (((int) (head - j)) > i2) {
            updateBufferLocked(1 + j, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
        }
        return true;
    }

    public final long tryPeekLocked(SharedFlowSlot sharedFlowSlot) {
        long j = sharedFlowSlot.index;
        if (j < getHead() + this.bufferSize) {
            return j;
        }
        if (this.bufferCapacity <= 0 && j <= getHead() && this.queueSize != 0) {
            return j;
        }
        return -1L;
    }

    public final Object tryTakeValue(SharedFlowSlot sharedFlowSlot) {
        Object obj;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            long tryPeekLocked = tryPeekLocked(sharedFlowSlot);
            if (tryPeekLocked < 0) {
                obj = SharedFlowKt.NO_VALUE;
            } else {
                long j = sharedFlowSlot.index;
                Object[] objArr = this.buffer;
                Intrinsics.checkNotNull(objArr);
                Symbol symbol = SharedFlowKt.NO_VALUE;
                Object obj2 = objArr[((int) tryPeekLocked) & (objArr.length - 1)];
                if (obj2 instanceof Emitter) {
                    obj2 = ((Emitter) obj2).value;
                }
                sharedFlowSlot.index = tryPeekLocked + 1;
                Object obj3 = obj2;
                continuationArr = m296xffe52cb8(j);
                obj = obj3;
            }
        }
        for (Continuation continuation : continuationArr) {
            if (continuation != null) {
                int i = Result.$r8$clinit;
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        return obj;
    }

    public final void updateBufferLocked(long j, long j2, long j3, long j4) {
        long min = Math.min(j2, j);
        for (long head = getHead(); head < min; head++) {
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            Symbol symbol = SharedFlowKt.NO_VALUE;
            objArr[((int) head) & (objArr.length - 1)] = null;
        }
        this.replayIndex = j;
        this.minCollectorIndex = j2;
        this.bufferSize = (int) (j3 - min);
        this.queueSize = (int) (j4 - j3);
    }

    /* renamed from: updateCollectorIndexLocked$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Continuation[] m296xffe52cb8(long j) {
        long j2;
        long j3;
        long j4;
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        long j5 = this.minCollectorIndex;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        if (j > j5) {
            return continuationArr;
        }
        long head = getHead();
        long j6 = this.bufferSize + head;
        int i = this.bufferCapacity;
        if (i == 0 && this.queueSize > 0) {
            j6++;
        }
        if (this.nCollectors != 0 && (abstractSharedFlowSlotArr = this.slots) != null) {
            for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                if (abstractSharedFlowSlot != null) {
                    long j7 = ((SharedFlowSlot) abstractSharedFlowSlot).index;
                    if (j7 >= 0 && j7 < j6) {
                        j6 = j7;
                    }
                }
            }
        }
        if (j6 <= this.minCollectorIndex) {
            return continuationArr;
        }
        long head2 = getHead() + this.bufferSize;
        int min = this.nCollectors > 0 ? Math.min(this.queueSize, i - ((int) (head2 - j6))) : this.queueSize;
        long j8 = this.queueSize + head2;
        if (min > 0) {
            continuationArr = new Continuation[min];
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            long j9 = head2;
            int i2 = 0;
            while (true) {
                if (head2 >= j8) {
                    j2 = j6;
                    j3 = j8;
                    break;
                }
                int i3 = (int) head2;
                j2 = j6;
                Object obj = objArr[(objArr.length - 1) & i3];
                Symbol symbol = SharedFlowKt.NO_VALUE;
                if (obj != symbol) {
                    Emitter emitter = (Emitter) obj;
                    j3 = j8;
                    int i4 = i2 + 1;
                    continuationArr[i2] = emitter.cont;
                    objArr[i3 & (objArr.length - 1)] = symbol;
                    objArr[((int) j9) & (objArr.length - 1)] = emitter.value;
                    j4 = 1;
                    j9++;
                    if (i4 >= min) {
                        break;
                    }
                    i2 = i4;
                } else {
                    j3 = j8;
                    j4 = 1;
                }
                head2 += j4;
                j6 = j2;
                j8 = j3;
            }
            head2 = j9;
        } else {
            j2 = j6;
            j3 = j8;
        }
        Continuation[] continuationArr2 = continuationArr;
        int i5 = (int) (head2 - head);
        long j10 = this.nCollectors == 0 ? head2 : j2;
        long max = Math.max(this.replayIndex, head2 - Math.min(this.replay, i5));
        if (i == 0 && max < j3) {
            Object[] objArr2 = this.buffer;
            Intrinsics.checkNotNull(objArr2);
            if (Intrinsics.areEqual(objArr2[((int) max) & (objArr2.length - 1)], SharedFlowKt.NO_VALUE)) {
                head2++;
                max++;
            }
        }
        updateBufferLocked(max, j10, head2, j3);
        cleanupTailLocked();
        return (continuationArr2.length == 0) ^ true ? findSlotsToResumeLocked(continuationArr2) : continuationArr2;
    }
}
