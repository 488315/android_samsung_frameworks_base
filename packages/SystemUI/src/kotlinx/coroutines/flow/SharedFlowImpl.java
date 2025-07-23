package kotlinx.coroutines.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.DisposeOnCancel;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|(3:(7:(1:(1:11)(2:46|47))(1:48)|12|13|14|15|(2:16|(3:38|39|(2:41|42)(2:43|37))(4:18|(3:23|24|25)|32|(1:34)(2:36|37)))|35)(4:49|50|51|52)|30|31)(5:58|59|60|(1:62)|65)|53|54|15|(3:16|(0)(0)|37)|35))|68|6|(0)(0)|53|54|15|(3:16|(0)(0)|37)|35) */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c1, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c2, code lost:
    
        r5 = r8;
        r8 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0090, code lost:
    
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r9).onSubscription(r0) == r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00c5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static kotlin.coroutines.intrinsics.CoroutineSingletons collect$suspendImpl(kotlinx.coroutines.flow.SharedFlowImpl r8, kotlinx.coroutines.flow.FlowCollector r9, kotlin.coroutines.Continuation r10) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.collect$suspendImpl(kotlinx.coroutines.flow.SharedFlowImpl, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):kotlin.coroutines.intrinsics.CoroutineSingletons");
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

    @Override // kotlinx.coroutines.flow.MutableSharedFlow, kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        SharedFlowImpl sharedFlowImpl;
        Throwable th;
        Continuation[] findSlotsToResumeLocked;
        Emitter emitter;
        if (tryEmit(obj)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            try {
                if (tryEmitLocked(obj)) {
                    try {
                        int i = Result.$r8$clinit;
                        cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                        findSlotsToResumeLocked = findSlotsToResumeLocked(continuationArr);
                        emitter = null;
                        sharedFlowImpl = this;
                    } catch (Throwable th2) {
                        th = th2;
                        sharedFlowImpl = this;
                        throw th;
                    }
                } else {
                    try {
                        sharedFlowImpl = this;
                    } catch (Throwable th3) {
                        sharedFlowImpl = this;
                        th = th3;
                        throw th;
                    }
                    try {
                        Emitter emitter2 = new Emitter(sharedFlowImpl, getHead() + this.bufferSize + this.queueSize, obj, cancellableContinuationImpl);
                        sharedFlowImpl.enqueueLocked(emitter2);
                        sharedFlowImpl.queueSize++;
                        if (sharedFlowImpl.bufferCapacity == 0) {
                            continuationArr = sharedFlowImpl.findSlotsToResumeLocked(continuationArr);
                        }
                        findSlotsToResumeLocked = continuationArr;
                        emitter = emitter2;
                    } catch (Throwable th4) {
                        th = th4;
                        th = th;
                        throw th;
                    }
                }
                if (emitter != null) {
                    cancellableContinuationImpl.invokeOnCancellationImpl(new DisposeOnCancel(emitter));
                }
                for (Continuation continuation2 : findSlotsToResumeLocked) {
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
            } catch (Throwable th5) {
                th = th5;
                sharedFlowImpl = this;
            }
        }
    }

    public final void enqueueLocked(Object obj) {
        int i = this.bufferSize + this.queueSize;
        Object[] objArr = this.buffer;
        if (objArr == null) {
            objArr = growBuffer(0, 2, null);
        } else if (i >= objArr.length) {
            objArr = growBuffer(i, objArr.length * 2, objArr);
        }
        SharedFlowKt.access$setBufferAt(objArr, getHead() + i, obj);
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
    public final void resetReplayCache() {
        SharedFlowImpl sharedFlowImpl;
        synchronized (this) {
            try {
                sharedFlowImpl = this;
            } catch (Throwable th) {
                th = th;
                sharedFlowImpl = this;
            }
            try {
                sharedFlowImpl.updateBufferLocked(getHead() + this.bufferSize, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                throw th3;
            }
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
        if (i != 0) {
            int i3 = this.bufferSize;
            int i4 = this.bufferCapacity;
            if (i3 >= i4 && this.minCollectorIndex <= this.replayIndex) {
                int i5 = WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
                if (i5 == 1) {
                    return false;
                }
                if (i5 != 2) {
                    if (i5 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
            enqueueLocked(obj);
            int i6 = this.bufferSize + 1;
            this.bufferSize = i6;
            if (i6 > i4) {
                dropOldestLocked();
            }
            long head = getHead() + this.bufferSize;
            long j = this.replayIndex;
            if (((int) (head - j)) > i2) {
                updateBufferLocked(1 + j, this.minCollectorIndex, getHead() + this.bufferSize, getHead() + this.bufferSize + this.queueSize);
            }
        } else if (i2 != 0) {
            enqueueLocked(obj);
            int i7 = this.bufferSize + 1;
            this.bufferSize = i7;
            if (i7 > i2) {
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
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            try {
                long tryPeekLocked = tryPeekLocked(sharedFlowSlot);
                if (tryPeekLocked < 0) {
                    obj = SharedFlowKt.NO_VALUE;
                } else {
                    long j = sharedFlowSlot.index;
                    Object[] objArr = this.buffer;
                    objArr.getClass();
                    Symbol symbol = SharedFlowKt.NO_VALUE;
                    Object obj2 = objArr[((int) tryPeekLocked) & (objArr.length - 1)];
                    if (obj2 instanceof Emitter) {
                        obj2 = ((Emitter) obj2).value;
                    }
                    sharedFlowSlot.index = tryPeekLocked + 1;
                    Object obj3 = obj2;
                    continuationArr = updateCollectorIndexLocked$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(j);
                    obj = obj3;
                }
            } catch (Throwable th) {
                throw th;
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
            objArr.getClass();
            SharedFlowKt.access$setBufferAt(objArr, head, null);
        }
        this.replayIndex = j;
        this.minCollectorIndex = j2;
        this.bufferSize = (int) (j3 - min);
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
                int min = this.nCollectors > 0 ? Math.min(this.queueSize, i2 - ((int) (head2 - j5))) : this.queueSize;
                long j7 = this.queueSize + head2;
                if (min > 0) {
                    Continuation[] continuationArr4 = new Continuation[min];
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
                            if (i4 >= min) {
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
                long max = Math.max(this.replayIndex, head2 - Math.min(this.replay, i5));
                if (i == 0 && max < j7) {
                    Object[] objArr2 = this.buffer;
                    objArr2.getClass();
                    if (Intrinsics.areEqual(objArr2[((int) max) & (objArr2.length - 1)], SharedFlowKt.NO_VALUE)) {
                        head2 += j3;
                        max += j3;
                    }
                }
                updateBufferLocked(max, j10, head2, j7);
                cleanupTailLocked();
                return continuationArr.length == 0 ? continuationArr : findSlotsToResumeLocked(continuationArr);
            }
        }
        return continuationArr3;
    }
}
