package kotlinx.coroutines.channels;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import kotlinx.coroutines.selects.SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1;
import kotlinx.coroutines.selects.TrySelectDetailedResult;

/* loaded from: classes4.dex */
public class BufferedChannel implements Channel {
    public final AtomicRef _closeCause;
    public final AtomicLong bufferEnd;
    public final AtomicRef bufferEndSegment;
    public final int capacity;
    public final AtomicRef closeHandler;
    public final AtomicLong completedExpandBuffersAndPauseFlag;
    public final Function1 onUndeliveredElement;
    public final BufferedChannel$$ExternalSyntheticLambda0 onUndeliveredElementReceiveCancellationConstructor;
    public final AtomicRef receiveSegment;
    public final AtomicLong receivers;
    public final AtomicRef sendSegment;
    public final AtomicLong sendersAndCloseStatus;

    public final class BufferedChannelIterator implements Waiter {
        public CancellableContinuationImpl continuation;
        public Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        public final Object hasNext(ContinuationImpl continuationImpl) {
            ChannelSegment channelSegmentFindSegmentReceive;
            Boolean bool;
            Object obj = this.receiveResult;
            boolean z = true;
            if (obj == BufferedChannelKt.NO_RECEIVE_RESULT || obj == BufferedChannelKt.CHANNEL_CLOSED) {
                BufferedChannel bufferedChannel = BufferedChannel.this;
                ChannelSegment channelSegment = (ChannelSegment) bufferedChannel.receiveSegment.value;
                while (true) {
                    if (bufferedChannel.isClosedForReceive()) {
                        this.receiveResult = BufferedChannelKt.CHANNEL_CLOSED;
                        Throwable closeCause = BufferedChannel.this.getCloseCause();
                        if (closeCause != null) {
                            int i = StackTraceRecoveryKt.$r8$clinit;
                            throw closeCause;
                        }
                        z = false;
                    } else {
                        long andIncrement = bufferedChannel.receivers.getAndIncrement();
                        long j = BufferedChannelKt.SEGMENT_SIZE;
                        long j2 = andIncrement / j;
                        int i2 = (int) (andIncrement % j);
                        if (channelSegment.id != j2) {
                            channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment);
                            if (channelSegmentFindSegmentReceive == null) {
                                continue;
                            }
                        } else {
                            channelSegmentFindSegmentReceive = channelSegment;
                        }
                        Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegmentFindSegmentReceive, i2, andIncrement, null);
                        Symbol symbol = BufferedChannelKt.SUSPEND;
                        if (objUpdateCellReceive == symbol) {
                            throw new IllegalStateException("unreachable");
                        }
                        Symbol symbol2 = BufferedChannelKt.FAILED;
                        if (objUpdateCellReceive == symbol2) {
                            if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                channelSegmentFindSegmentReceive.cleanPrev();
                            }
                            channelSegment = channelSegmentFindSegmentReceive;
                        } else {
                            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                BufferedChannel bufferedChannel2 = BufferedChannel.this;
                                CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl));
                                try {
                                    this.continuation = orCreateCancellableContinuation;
                                    Object objUpdateCellReceive2 = bufferedChannel2.updateCellReceive(channelSegmentFindSegmentReceive, i2, andIncrement, this);
                                    if (objUpdateCellReceive2 == symbol) {
                                        invokeOnCancellation(channelSegmentFindSegmentReceive, i2);
                                    } else {
                                        BufferedChannel$$ExternalSyntheticLambda1 bufferedChannel$$ExternalSyntheticLambda1 = null;
                                        if (objUpdateCellReceive2 == symbol2) {
                                            if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                                channelSegmentFindSegmentReceive.cleanPrev();
                                            }
                                            ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel2.receiveSegment.value;
                                            while (true) {
                                                if (bufferedChannel2.isClosedForReceive()) {
                                                    CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
                                                    cancellableContinuationImpl.getClass();
                                                    this.continuation = null;
                                                    this.receiveResult = BufferedChannelKt.CHANNEL_CLOSED;
                                                    Throwable closeCause2 = BufferedChannel.this.getCloseCause();
                                                    if (closeCause2 == null) {
                                                        int i3 = Result.$r8$clinit;
                                                        cancellableContinuationImpl.resumeWith(Boolean.FALSE);
                                                    } else {
                                                        int i4 = Result.$r8$clinit;
                                                        cancellableContinuationImpl.resumeWith(new Result.Failure(closeCause2));
                                                    }
                                                } else {
                                                    long andIncrement2 = bufferedChannel2.receivers.getAndIncrement();
                                                    long j3 = BufferedChannelKt.SEGMENT_SIZE;
                                                    long j4 = andIncrement2 / j3;
                                                    int i5 = (int) (andIncrement2 % j3);
                                                    if (channelSegment2.id != j4) {
                                                        ChannelSegment channelSegmentFindSegmentReceive2 = bufferedChannel2.findSegmentReceive(j4, channelSegment2);
                                                        if (channelSegmentFindSegmentReceive2 != null) {
                                                            channelSegment2 = channelSegmentFindSegmentReceive2;
                                                        }
                                                    }
                                                    Object objUpdateCellReceive3 = bufferedChannel2.updateCellReceive(channelSegment2, i5, andIncrement2, this);
                                                    if (objUpdateCellReceive3 == BufferedChannelKt.SUSPEND) {
                                                        invokeOnCancellation(channelSegment2, i5);
                                                        break;
                                                    }
                                                    if (objUpdateCellReceive3 == BufferedChannelKt.FAILED) {
                                                        if (andIncrement2 < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                                            channelSegment2.cleanPrev();
                                                        }
                                                    } else {
                                                        if (objUpdateCellReceive3 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                                            throw new IllegalStateException("unexpected");
                                                        }
                                                        channelSegment2.cleanPrev();
                                                        this.receiveResult = objUpdateCellReceive3;
                                                        this.continuation = null;
                                                        bool = Boolean.TRUE;
                                                        Function1 function1 = bufferedChannel2.onUndeliveredElement;
                                                        if (function1 != null) {
                                                            bufferedChannel$$ExternalSyntheticLambda1 = new BufferedChannel$$ExternalSyntheticLambda1(objUpdateCellReceive3, function1);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            channelSegmentFindSegmentReceive.cleanPrev();
                                            this.receiveResult = objUpdateCellReceive2;
                                            this.continuation = null;
                                            bool = Boolean.TRUE;
                                            Function1 function12 = bufferedChannel2.onUndeliveredElement;
                                            if (function12 != null) {
                                                bufferedChannel$$ExternalSyntheticLambda1 = new BufferedChannel$$ExternalSyntheticLambda1(objUpdateCellReceive2, function12);
                                            }
                                        }
                                        orCreateCancellableContinuation.resume(bool, bufferedChannel$$ExternalSyntheticLambda1);
                                    }
                                    Object result = orCreateCancellableContinuation.getResult();
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    return result;
                                } catch (Throwable th) {
                                    orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                                    throw th;
                                }
                            }
                            channelSegmentFindSegmentReceive.cleanPrev();
                            this.receiveResult = objUpdateCellReceive;
                        }
                    }
                }
            }
            return Boolean.valueOf(z);
        }

        @Override // kotlinx.coroutines.Waiter
        public final void invokeOnCancellation(Segment segment, int i) {
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, i);
            }
        }

        public final Object next() {
            Object obj = this.receiveResult;
            Symbol symbol = BufferedChannelKt.NO_RECEIVE_RESULT;
            if (obj == symbol) {
                throw new IllegalStateException("`hasNext()` has not been invoked");
            }
            this.receiveResult = symbol;
            if (obj != BufferedChannelKt.CHANNEL_CLOSED) {
                return obj;
            }
            Throwable receiveException = BufferedChannel.this.getReceiveException();
            int i = StackTraceRecoveryKt.$r8$clinit;
            throw receiveException;
        }
    }

    public final class SendBroadcast implements Waiter {
        public final /* synthetic */ CancellableContinuationImpl $$delegate_0;
        public final CancellableContinuation cont;

        public SendBroadcast(CancellableContinuation cancellableContinuation) {
            this.$$delegate_0 = (CancellableContinuationImpl) cancellableContinuation;
            this.cont = cancellableContinuation;
        }

        @Override // kotlinx.coroutines.Waiter
        public final void invokeOnCancellation(Segment segment, int i) {
            this.$$delegate_0.invokeOnCancellation(segment, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v8, types: [kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda0] */
    public BufferedChannel(int i, Function1 function1) {
        this.capacity = i;
        this.onUndeliveredElement = function1;
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Invalid channel capacity: ", ", should be >=0").toString());
        }
        this.sendersAndCloseStatus = AtomicFU.atomic(0L);
        this.receivers = AtomicFU.atomic(0L);
        ChannelSegment channelSegment = BufferedChannelKt.NULL_SEGMENT;
        AtomicLong atomicLongAtomic = AtomicFU.atomic(i != 0 ? i != Integer.MAX_VALUE ? i : Long.MAX_VALUE : 0L);
        this.bufferEnd = atomicLongAtomic;
        this.completedExpandBuffersAndPauseFlag = AtomicFU.atomic(atomicLongAtomic.value);
        ChannelSegment channelSegment2 = new ChannelSegment(0L, null, this, 3);
        this.sendSegment = AtomicFU.atomic(channelSegment2);
        this.receiveSegment = AtomicFU.atomic(channelSegment2);
        this.bufferEndSegment = AtomicFU.atomic(isRendezvousOrUnlimited() ? BufferedChannelKt.NULL_SEGMENT : channelSegment2);
        this.onUndeliveredElementReceiveCancellationConstructor = function1 != null ? new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, final Object obj3) {
                final SelectInstance selectInstance = (SelectInstance) obj;
                final BufferedChannel bufferedChannel = this.f$0;
                return new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                        Symbol symbol = BufferedChannelKt.CHANNEL_CLOSED;
                        Object obj7 = obj3;
                        if (obj7 != symbol) {
                            OnUndeliveredElementKt.callUndeliveredElement(bufferedChannel.onUndeliveredElement, obj7, ((SelectImplementation) selectInstance).context);
                        }
                        return Unit.INSTANCE;
                    }
                };
            }
        } : null;
        this._closeCause = AtomicFU.atomic(BufferedChannelKt.NO_CLOSE_CAUSE);
        this.closeHandler = AtomicFU.atomic((Object) null);
    }

    public static final ChannelSegment access$findSegmentSend(BufferedChannel bufferedChannel, long j, ChannelSegment channelSegment) {
        Object objFindSegmentInternal;
        long j2;
        long j3;
        AtomicRef atomicRef = bufferedChannel.sendSegment;
        ChannelSegment channelSegment2 = BufferedChannelKt.NULL_SEGMENT;
        BufferedChannelKt$createSegmentFunction$1 bufferedChannelKt$createSegmentFunction$1 = BufferedChannelKt$createSegmentFunction$1.INSTANCE;
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
            if (!SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM3484getSegmentimpl = SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.value;
                    if (segment.id >= segmentM3484getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM3484getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM3484getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM3484getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM3484getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
            bufferedChannel.isClosedForSend();
            if (channelSegment.id * BufferedChannelKt.SEGMENT_SIZE < bufferedChannel.receivers.value) {
                channelSegment.cleanPrev();
                return null;
            }
        } else {
            ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
            long j4 = channelSegment3.id;
            if (j4 <= j) {
                return channelSegment3;
            }
            long j5 = j4 * BufferedChannelKt.SEGMENT_SIZE;
            AtomicLong atomicLong = bufferedChannel.sendersAndCloseStatus;
            do {
                j2 = atomicLong.value;
                j3 = 1152921504606846975L & j2;
                if (j3 >= j5) {
                    break;
                }
            } while (!bufferedChannel.sendersAndCloseStatus.compareAndSet(j2, (((int) (j2 >> 60)) << 60) + j3));
            if (channelSegment3.id * BufferedChannelKt.SEGMENT_SIZE < bufferedChannel.receivers.value) {
                channelSegment3.cleanPrev();
            }
        }
        return null;
    }

    public static final void access$onClosedSendOnNoWaiterSuspend(BufferedChannel bufferedChannel, Object obj, CancellableContinuationImpl cancellableContinuationImpl) {
        Function1 function1 = bufferedChannel.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, obj, cancellableContinuationImpl.context);
        }
        Throwable sendException = bufferedChannel.getSendException();
        int i = Result.$r8$clinit;
        cancellableContinuationImpl.resumeWith(new Result.Failure(sendException));
    }

    public static final int access$updateCellSend(BufferedChannel bufferedChannel, ChannelSegment channelSegment, int i, Object obj, long j, Object obj2, boolean z) {
        bufferedChannel.getClass();
        channelSegment.setElementLazy(i, obj);
        if (z) {
            return bufferedChannel.updateCellSendSlow(channelSegment, i, obj, j, obj2, z);
        }
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
            if (bufferedChannel.bufferOrRendezvousSend(j)) {
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (obj2 == null) {
                    return 3;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, obj2)) {
                    return 2;
                }
            }
        } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter) {
            channelSegment.setElementLazy(i, null);
            if (bufferedChannel.tryResumeReceiver(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                return 0;
            }
            Symbol symbol = BufferedChannelKt.INTERRUPTED_RCV;
            if (channelSegment.data.array[(i * 2) + 1].getAndSet(symbol) == symbol) {
                return 5;
            }
            channelSegment.onCancelledRequest(i, true);
            return 5;
        }
        return bufferedChannel.updateCellSendSlow(channelSegment, i, obj, j, obj2, z);
    }

    public static void incCompletedExpandBufferAttempts$default(BufferedChannel bufferedChannel) {
        if ((bufferedChannel.completedExpandBuffersAndPauseFlag.addAndGet(1L) & 4611686018427387904L) != 0) {
            while ((bufferedChannel.completedExpandBuffersAndPauseFlag.value & 4611686018427387904L) != 0) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: receiveCatching-JP2dKIU$suspendImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object m3471receiveCatchingJP2dKIU$suspendImpl(BufferedChannel bufferedChannel, ContinuationImpl continuationImpl) {
        BufferedChannel$receiveCatching$1 bufferedChannel$receiveCatching$1;
        ChannelSegment channelSegment;
        if (continuationImpl instanceof BufferedChannel$receiveCatching$1) {
            bufferedChannel$receiveCatching$1 = (BufferedChannel$receiveCatching$1) continuationImpl;
            int i = bufferedChannel$receiveCatching$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                bufferedChannel$receiveCatching$1.label = i - Integer.MIN_VALUE;
            } else {
                bufferedChannel$receiveCatching$1 = new BufferedChannel$receiveCatching$1(bufferedChannel, continuationImpl);
            }
        }
        BufferedChannel$receiveCatching$1 bufferedChannel$receiveCatching$12 = bufferedChannel$receiveCatching$1;
        Object obj = bufferedChannel$receiveCatching$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = bufferedChannel$receiveCatching$12.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return ((ChannelResult) obj).holder;
        }
        ResultKt.throwOnFailure(obj);
        ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel.receiveSegment.value;
        while (!bufferedChannel.isClosedForReceive()) {
            long andIncrement = bufferedChannel.receivers.getAndIncrement();
            long j = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / j;
            int i3 = (int) (andIncrement % j);
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            BufferedChannel bufferedChannel2 = bufferedChannel;
            Object objUpdateCellReceive = bufferedChannel2.updateCellReceive(channelSegment, i3, andIncrement, null);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                throw new IllegalStateException("unexpected");
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    bufferedChannel$receiveCatching$12.label = 1;
                    Object objM3473receiveCatchingOnNoWaiterSuspendGKJJFZk = bufferedChannel2.m3473receiveCatchingOnNoWaiterSuspendGKJJFZk(channelSegment, i3, andIncrement, bufferedChannel$receiveCatching$12);
                    return objM3473receiveCatchingOnNoWaiterSuspendGKJJFZk == coroutineSingletons ? coroutineSingletons : objM3473receiveCatchingOnNoWaiterSuspendGKJJFZk;
                }
                channelSegment.cleanPrev();
                ChannelResult.Companion.getClass();
                return objUpdateCellReceive;
            }
            if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            bufferedChannel = bufferedChannel2;
            channelSegment2 = channelSegment;
        }
        ChannelResult.Companion companion = ChannelResult.Companion;
        Throwable closeCause = bufferedChannel.getCloseCause();
        companion.getClass();
        return ChannelResult.Companion.m3479closedJP2dKIU(closeCause);
    }

    public final boolean bufferOrRendezvousSend(long j) {
        return j < this.bufferEnd.value || j < this.receivers.value + ((long) this.capacity);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new CancellationException("Channel was cancelled");
        }
        closeOrCancelImpl(cancellationException, true);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean close(Throwable th) {
        return closeOrCancelImpl(th, false);
    }

    public final boolean closeOrCancelImpl(Throwable th, boolean z) {
        long j;
        long j2;
        long j3;
        Object obj;
        long j4;
        long j5;
        if (z) {
            AtomicLong atomicLong = this.sendersAndCloseStatus;
            do {
                j5 = atomicLong.value;
                if (((int) (j5 >> 60)) != 0) {
                    break;
                }
                ChannelSegment channelSegment = BufferedChannelKt.NULL_SEGMENT;
            } while (!atomicLong.compareAndSet(j5, (1 << 60) + (j5 & 1152921504606846975L)));
        }
        boolean zCompareAndSet = this._closeCause.compareAndSet(BufferedChannelKt.NO_CLOSE_CAUSE, th);
        if (z) {
            AtomicLong atomicLong2 = this.sendersAndCloseStatus;
            do {
                j4 = atomicLong2.value;
            } while (!atomicLong2.compareAndSet(j4, (3 << 60) + (j4 & 1152921504606846975L)));
        } else {
            AtomicLong atomicLong3 = this.sendersAndCloseStatus;
            do {
                j = atomicLong3.value;
                int i = (int) (j >> 60);
                if (i == 0) {
                    j2 = j & 1152921504606846975L;
                    j3 = 2;
                } else {
                    if (i != 1) {
                        break;
                    }
                    j2 = j & 1152921504606846975L;
                    j3 = 3;
                }
            } while (!atomicLong3.compareAndSet(j, (j3 << 60) + j2));
        }
        isClosedForSend();
        if (zCompareAndSet) {
            AtomicRef atomicRef = this.closeHandler;
            do {
                obj = atomicRef.value;
            } while (!atomicRef.compareAndSet(obj, obj == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
            if (obj != null) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, obj);
                ((Function1) obj).mo781invoke(getCloseCause());
            }
        }
        return zCompareAndSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0083, code lost:
    
        r2 = (kotlinx.coroutines.channels.ChannelSegment) ((kotlinx.coroutines.internal.ConcurrentLinkedListNode) r2._prev.value);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ChannelSegment completeClose(long j) {
        Object objM3483plusFjFbRPM;
        long j2;
        Object obj = this.bufferEndSegment.value;
        ChannelSegment channelSegment = (ChannelSegment) this.sendSegment.value;
        if (channelSegment.id > ((ChannelSegment) obj).id) {
            obj = channelSegment;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) this.receiveSegment.value;
        if (channelSegment2.id > ((ChannelSegment) obj).id) {
            obj = channelSegment2;
        }
        ConcurrentLinkedListNode concurrentLinkedListNode = (ConcurrentLinkedListNode) obj;
        Symbol symbol = ConcurrentLinkedListKt.CLOSED;
        while (true) {
            Object obj2 = concurrentLinkedListNode._next.value;
            Symbol symbol2 = ConcurrentLinkedListKt.CLOSED;
            objM3483plusFjFbRPM = null;
            if (obj2 == symbol2) {
                break;
            }
            ConcurrentLinkedListNode concurrentLinkedListNode2 = (ConcurrentLinkedListNode) obj2;
            if (concurrentLinkedListNode2 == null) {
                if (concurrentLinkedListNode._next.compareAndSet(null, symbol2)) {
                    break;
                }
            } else {
                concurrentLinkedListNode = concurrentLinkedListNode2;
            }
        }
        ChannelSegment channelSegment3 = (ChannelSegment) concurrentLinkedListNode;
        if (isConflatedDropOldest()) {
            ChannelSegment channelSegment4 = channelSegment3;
            loop1: do {
                int i = BufferedChannelKt.SEGMENT_SIZE - 1;
                while (true) {
                    if (-1 >= i) {
                        break;
                    }
                    j2 = (channelSegment4.id * BufferedChannelKt.SEGMENT_SIZE) + i;
                    if (j2 < this.receivers.value) {
                        break loop1;
                    }
                    while (true) {
                        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment4.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED) {
                                break loop1;
                            }
                        } else {
                            if (channelSegment4.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.CHANNEL_CLOSED)) {
                                channelSegment4.onSlotCleaned();
                                break;
                            }
                        }
                    }
                    i--;
                }
            } while (channelSegment4 != null);
            j2 = -1;
            if (j2 != -1) {
                dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(j2);
            }
        }
        loop4: for (ChannelSegment channelSegment5 = channelSegment3; channelSegment5 != null; channelSegment5 = (ChannelSegment) ((ConcurrentLinkedListNode) channelSegment5._prev.value)) {
            for (int i2 = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < i2; i2--) {
                if ((channelSegment5.id * BufferedChannelKt.SEGMENT_SIZE) + i2 < j) {
                    break loop4;
                }
                while (true) {
                    Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = channelSegment5.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2);
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != null && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != BufferedChannelKt.IN_BUFFER) {
                        if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof WaiterEB)) {
                            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof Waiter)) {
                                break;
                            }
                            if (channelSegment5.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                                objM3483plusFjFbRPM = InlineList.m3483plusFjFbRPM(objM3483plusFjFbRPM, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2);
                                channelSegment5.onCancelledRequest(i2, true);
                                break;
                            }
                        } else {
                            if (channelSegment5.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                                objM3483plusFjFbRPM = InlineList.m3483plusFjFbRPM(objM3483plusFjFbRPM, ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2).waiter);
                                channelSegment5.onCancelledRequest(i2, true);
                                break;
                            }
                        }
                    } else {
                        if (channelSegment5.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                            channelSegment5.onSlotCleaned();
                            break;
                        }
                    }
                }
            }
        }
        if (objM3483plusFjFbRPM != null) {
            if (!(objM3483plusFjFbRPM instanceof ArrayList)) {
                resumeWaiterOnClosedChannel((Waiter) objM3483plusFjFbRPM, true);
                return channelSegment3;
            }
            ArrayList arrayList = (ArrayList) objM3483plusFjFbRPM;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                resumeWaiterOnClosedChannel((Waiter) arrayList.get(size), true);
            }
        }
        return channelSegment3;
    }

    public final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long j) {
        ChannelSegment channelSegment;
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException;
        ChannelSegment channelSegment2 = (ChannelSegment) this.receiveSegment.value;
        while (true) {
            long j2 = this.receivers.value;
            if (j < Math.max(this.capacity + j2, this.bufferEnd.value)) {
                return;
            }
            if (this.receivers.compareAndSet(j2, 1 + j2)) {
                long j3 = BufferedChannelKt.SEGMENT_SIZE;
                long j4 = j2 / j3;
                int i = (int) (j2 % j3);
                if (channelSegment2.id != j4) {
                    ChannelSegment channelSegmentFindSegmentReceive = this.findSegmentReceive(j4, channelSegment2);
                    if (channelSegmentFindSegmentReceive != null) {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                BufferedChannel bufferedChannel = this;
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, j2, null);
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    channelSegment.cleanPrev();
                    Function1 function1 = bufferedChannel.onUndeliveredElement;
                    if (function1 != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, objUpdateCellReceive, null)) != null) {
                        throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
                    }
                } else if (j2 < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment.cleanPrev();
                }
                this = bufferedChannel;
                channelSegment2 = channelSegment;
            }
            this = this;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0185, code lost:
    
        incCompletedExpandBufferAttempts$default(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0188, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void expandBuffer() {
        Object objFindSegmentInternal;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        ChannelSegment channelSegment = (ChannelSegment) this.bufferEndSegment.value;
        loop0: while (true) {
            long andIncrement = this.bufferEnd.getAndIncrement();
            long j = andIncrement / BufferedChannelKt.SEGMENT_SIZE;
            if (getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() <= andIncrement) {
                if (channelSegment.id < j && channelSegment.getNext() != null) {
                    moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
                }
                incCompletedExpandBufferAttempts$default(this);
                return;
            }
            if (channelSegment.id != j) {
                AtomicRef atomicRef = this.bufferEndSegment;
                BufferedChannelKt$createSegmentFunction$1 bufferedChannelKt$createSegmentFunction$1 = BufferedChannelKt$createSegmentFunction$1.INSTANCE;
                while (true) {
                    objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
                    if (!SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                        Segment segmentM3484getSegmentimpl = SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                        while (true) {
                            Segment segment = (Segment) atomicRef.value;
                            if (segment.id >= segmentM3484getSegmentimpl.id) {
                                break;
                            }
                            if (!segmentM3484getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                break;
                            }
                            if (atomicRef.compareAndSet(segment, segmentM3484getSegmentimpl)) {
                                if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                    segment.remove();
                                }
                            } else if (segmentM3484getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                segmentM3484getSegmentimpl.remove();
                            }
                        }
                    } else {
                        break;
                    }
                }
                ChannelSegment channelSegment2 = null;
                if (SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                    isClosedForSend();
                    moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
                    incCompletedExpandBufferAttempts$default(this);
                } else {
                    ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                    long j2 = channelSegment3.id;
                    if (j2 > j) {
                        long j3 = BufferedChannelKt.SEGMENT_SIZE;
                        if (this.bufferEnd.compareAndSet(1 + andIncrement, j2 * j3)) {
                            if ((this.completedExpandBuffersAndPauseFlag.addAndGet((channelSegment3.id * j3) - andIncrement) & 4611686018427387904L) != 0) {
                                while ((this.completedExpandBuffersAndPauseFlag.value & 4611686018427387904L) != 0) {
                                }
                            }
                        } else {
                            incCompletedExpandBufferAttempts$default(this);
                        }
                    } else {
                        channelSegment2 = channelSegment3;
                    }
                }
                if (channelSegment2 == null) {
                    continue;
                } else {
                    channelSegment = channelSegment2;
                }
            }
            int i = (int) (andIncrement % BufferedChannelKt.SEGMENT_SIZE);
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter) || andIncrement < this.receivers.value || !channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_EB)) {
                while (true) {
                    Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                    if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof Waiter)) {
                        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != BufferedChannelKt.INTERRUPTED_SEND) {
                            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != null) {
                                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.BUFFERED || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.POISONED || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.DONE_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.INTERRUPTED_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.CHANNEL_CLOSED) {
                                    break loop0;
                                }
                                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != BufferedChannelKt.RESUMING_BY_RCV) {
                                    throw new IllegalStateException(("Unexpected cell state: " + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2).toString());
                                }
                            } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.IN_BUFFER)) {
                                break loop0;
                            }
                        } else {
                            break;
                        }
                    } else if (andIncrement < this.receivers.value) {
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, new WaiterEB((Waiter) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2))) {
                            break loop0;
                        }
                    } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.RESUMING_BY_EB)) {
                        if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, channelSegment, i)) {
                            channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.BUFFERED);
                            break;
                        } else {
                            channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
                            channelSegment.onSlotCleaned();
                        }
                    }
                }
            } else if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, channelSegment, i)) {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.BUFFERED);
                break;
            } else {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
                channelSegment.onSlotCleaned();
                incCompletedExpandBufferAttempts$default(this);
            }
        }
    }

    public final ChannelSegment findSegmentReceive(long j, ChannelSegment channelSegment) {
        Object objFindSegmentInternal;
        long j2;
        AtomicRef atomicRef = this.receiveSegment;
        ChannelSegment channelSegment2 = BufferedChannelKt.NULL_SEGMENT;
        BufferedChannelKt$createSegmentFunction$1 bufferedChannelKt$createSegmentFunction$1 = BufferedChannelKt$createSegmentFunction$1.INSTANCE;
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
            if (!SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM3484getSegmentimpl = SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.value;
                    if (segment.id >= segmentM3484getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM3484getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM3484getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM3484getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM3484getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
            isClosedForSend();
            if (channelSegment.id * BufferedChannelKt.SEGMENT_SIZE < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
        if (!isRendezvousOrUnlimited() && j <= this.bufferEnd.value / BufferedChannelKt.SEGMENT_SIZE) {
            AtomicRef atomicRef2 = this.bufferEndSegment;
            while (true) {
                Segment segment2 = (Segment) atomicRef2.value;
                if (segment2.id >= channelSegment3.id || !channelSegment3.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    break;
                }
                if (atomicRef2.compareAndSet(segment2, channelSegment3)) {
                    if (segment2.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segment2.remove();
                    }
                } else if (channelSegment3.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment3.remove();
                }
            }
        }
        long j3 = channelSegment3.id;
        if (j3 <= j) {
            return channelSegment3;
        }
        long j4 = j3 * BufferedChannelKt.SEGMENT_SIZE;
        AtomicLong atomicLong = this.receivers;
        do {
            j2 = atomicLong.value;
            if (j2 >= j4) {
                break;
            }
        } while (!this.receivers.compareAndSet(j2, j4));
        if (channelSegment3.id * BufferedChannelKt.SEGMENT_SIZE < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
            channelSegment3.cleanPrev();
        }
        return null;
    }

    public final Throwable getCloseCause() {
        return (Throwable) this._closeCause.value;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final SelectClause1Impl getOnReceiveCatching() {
        BufferedChannel$onReceiveCatching$1 bufferedChannel$onReceiveCatching$1 = BufferedChannel$onReceiveCatching$1.INSTANCE;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, bufferedChannel$onReceiveCatching$1);
        BufferedChannel$onReceiveCatching$2 bufferedChannel$onReceiveCatching$2 = BufferedChannel$onReceiveCatching$2.INSTANCE;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, bufferedChannel$onReceiveCatching$2);
        return new SelectClause1Impl(this, bufferedChannel$onReceiveCatching$1, bufferedChannel$onReceiveCatching$2, this.onUndeliveredElementReceiveCancellationConstructor);
    }

    public final Throwable getReceiveException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedReceiveChannelException("Channel was closed") : closeCause;
    }

    public final Throwable getSendException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedSendChannelException("Channel was closed") : closeCause;
    }

    public final long getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.sendersAndCloseStatus.value & 1152921504606846975L;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final void invokeOnClose(ProduceKt$awaitClose$4$1 produceKt$awaitClose$4$1) {
        Symbol symbol;
        if (this.closeHandler.compareAndSet(null, produceKt$awaitClose$4$1)) {
            return;
        }
        AtomicRef atomicRef = this.closeHandler;
        do {
            Object obj = atomicRef.value;
            symbol = BufferedChannelKt.CLOSE_HANDLER_CLOSED;
            if (obj != symbol) {
                if (obj == BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                    throw new IllegalStateException("Another handler was already registered and successfully invoked");
                }
                throw new IllegalStateException(("Another handler is already registered: " + obj).toString());
            }
        } while (!this.closeHandler.compareAndSet(symbol, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        produceKt$awaitClose$4$1.mo781invoke(getCloseCause());
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x0178, code lost:
    
        r11.receivers.compareAndSet(r13, 1 + r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b8, code lost:
    
        r12 = (kotlinx.coroutines.channels.ChannelSegment) ((kotlinx.coroutines.internal.ConcurrentLinkedListNode) r12._prev.value);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isClosed(long j, boolean z) {
        int i = (int) (j >> 60);
        if (i != 0 && i != 1) {
            if (i == 2) {
                completeClose(j & 1152921504606846975L);
                if (z) {
                    while (true) {
                        ChannelSegment channelSegmentFindSegmentReceive = (ChannelSegment) this.receiveSegment.value;
                        long j2 = this.receivers.value;
                        if (getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() <= j2) {
                            break;
                        }
                        long j3 = BufferedChannelKt.SEGMENT_SIZE;
                        long j4 = j2 / j3;
                        if (channelSegmentFindSegmentReceive.id != j4 && (channelSegmentFindSegmentReceive = findSegmentReceive(j4, channelSegmentFindSegmentReceive)) == null) {
                            if (((ChannelSegment) this.receiveSegment.value).id < j4) {
                                break;
                            }
                        } else {
                            channelSegmentFindSegmentReceive.cleanPrev();
                            int i2 = (int) (j2 % j3);
                            while (true) {
                                Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegmentFindSegmentReceive.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2);
                                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED || (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.INTERRUPTED_SEND && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.CHANNEL_CLOSED && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.DONE_RCV && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.POISONED && (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.RESUMING_BY_EB || (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_RCV && j2 == this.receivers.value)))) {
                                        break;
                                    }
                                } else {
                                    if (channelSegmentFindSegmentReceive.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i2, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.POISONED)) {
                                        expandBuffer();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (i != 3) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unexpected close status: ").toString());
                }
                ChannelSegment channelSegmentCompleteClose = completeClose(j & 1152921504606846975L);
                Function1 function1 = this.onUndeliveredElement;
                UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException = null;
                Object objM3483plusFjFbRPM = null;
                loop0: do {
                    int i3 = BufferedChannelKt.SEGMENT_SIZE - 1;
                    while (true) {
                        if (-1 >= i3) {
                            break;
                        }
                        long j5 = (channelSegmentCompleteClose.id * BufferedChannelKt.SEGMENT_SIZE) + i3;
                        while (true) {
                            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = channelSegmentCompleteClose.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3);
                            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.DONE_RCV) {
                                break loop0;
                            }
                            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.BUFFERED) {
                                if (j5 < this.receivers.value) {
                                    break loop0;
                                }
                                if (channelSegmentCompleteClose.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                                    if (function1 != null) {
                                        undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, channelSegmentCompleteClose.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3), undeliveredElementExceptionCallUndeliveredElementCatchingException);
                                    }
                                    channelSegmentCompleteClose.setElementLazy(i3, null);
                                    channelSegmentCompleteClose.onSlotCleaned();
                                }
                            } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.IN_BUFFER || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == null) {
                                if (channelSegmentCompleteClose.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                                    channelSegmentCompleteClose.onSlotCleaned();
                                    break;
                                }
                            } else if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof Waiter) && !(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof WaiterEB)) {
                                Symbol symbol = BufferedChannelKt.RESUMING_BY_EB;
                                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == symbol || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.RESUMING_BY_RCV) {
                                    break loop0;
                                }
                                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != symbol) {
                                    break;
                                }
                            } else {
                                if (j5 < this.receivers.value) {
                                    break loop0;
                                }
                                Waiter waiter = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof WaiterEB ? ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2).waiter : (Waiter) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2;
                                if (channelSegmentCompleteClose.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.CHANNEL_CLOSED)) {
                                    if (function1 != null) {
                                        undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, channelSegmentCompleteClose.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i3), undeliveredElementExceptionCallUndeliveredElementCatchingException);
                                    }
                                    objM3483plusFjFbRPM = InlineList.m3483plusFjFbRPM(objM3483plusFjFbRPM, waiter);
                                    channelSegmentCompleteClose.setElementLazy(i3, null);
                                    channelSegmentCompleteClose.onSlotCleaned();
                                }
                            }
                        }
                        i3--;
                    }
                } while (channelSegmentCompleteClose != null);
                if (objM3483plusFjFbRPM != null) {
                    if (objM3483plusFjFbRPM instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) objM3483plusFjFbRPM;
                        for (int size = arrayList.size() - 1; -1 < size; size--) {
                            resumeWaiterOnClosedChannel((Waiter) arrayList.get(size), false);
                        }
                    } else {
                        resumeWaiterOnClosedChannel((Waiter) objM3483plusFjFbRPM, false);
                    }
                }
                if (undeliveredElementExceptionCallUndeliveredElementCatchingException != null) {
                    throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
                }
            }
            return true;
        }
        return false;
    }

    public final boolean isClosedForReceive() {
        return isClosed(this.sendersAndCloseStatus.value, true);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean isClosedForSend() {
        return isClosed(this.sendersAndCloseStatus.value, false);
    }

    public boolean isConflatedDropOldest() {
        return false;
    }

    public final boolean isRendezvousOrUnlimited() {
        long j = this.bufferEnd.value;
        return j == 0 || j == Long.MAX_VALUE;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final BufferedChannelIterator iterator() {
        return new BufferedChannelIterator();
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0011, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void moveSegmentBufferEndToSpecifiedOrLast(long j, ChannelSegment channelSegment) {
        ChannelSegment channelSegment2;
        ChannelSegment channelSegment3;
        while (channelSegment.id < j && (channelSegment3 = (ChannelSegment) channelSegment.getNext()) != null) {
            channelSegment = channelSegment3;
        }
        while (true) {
            if (!channelSegment.isRemoved() || (channelSegment2 = (ChannelSegment) channelSegment.getNext()) == null) {
                AtomicRef atomicRef = this.bufferEndSegment;
                while (true) {
                    Segment segment = (Segment) atomicRef.value;
                    if (segment.id >= channelSegment.id) {
                        return;
                    }
                    if (!channelSegment.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, channelSegment)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                            return;
                        }
                        return;
                    } else if (channelSegment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        channelSegment.remove();
                    }
                }
            } else {
                channelSegment = channelSegment2;
            }
        }
    }

    public final Object onClosedSend(Object obj, Continuation continuation) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Function1 function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) == null) {
            Throwable sendException = getSendException();
            int i = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(sendException));
        } else {
            ExceptionsKt__ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException, getSendException());
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(undeliveredElementExceptionCallUndeliveredElementCatchingException));
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final Object receive(Continuation continuation) throws Throwable {
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2;
        CancellableContinuationImpl cancellableContinuationImpl;
        BufferedChannel bufferedChannel;
        ChannelSegment channelSegment3 = (ChannelSegment) this.receiveSegment.value;
        while (!this.isClosedForReceive()) {
            long andIncrement = this.receivers.getAndIncrement();
            long j = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / j;
            int i = (int) (andIncrement % j);
            if (channelSegment3.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = this.findSegmentReceive(j2, channelSegment3);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment3;
            }
            BufferedChannel bufferedChannel2 = this;
            Object objUpdateCellReceive = bufferedChannel2.updateCellReceive(channelSegment, i, andIncrement, null);
            Symbol symbol = BufferedChannelKt.SUSPEND;
            if (objUpdateCellReceive == symbol) {
                throw new IllegalStateException("unexpected");
            }
            Symbol symbol2 = BufferedChannelKt.FAILED;
            if (objUpdateCellReceive == symbol2) {
                if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment.cleanPrev();
                }
                this = bufferedChannel2;
                channelSegment3 = channelSegment;
            } else {
                if (objUpdateCellReceive != BufferedChannelKt.SUSPEND_NO_WAITER) {
                    channelSegment.cleanPrev();
                    return objUpdateCellReceive;
                }
                CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                try {
                    Object objUpdateCellReceive2 = bufferedChannel2.updateCellReceive(channelSegment, i, andIncrement, orCreateCancellableContinuation);
                    if (objUpdateCellReceive2 == symbol) {
                        orCreateCancellableContinuation.invokeOnCancellation(channelSegment, i);
                    } else {
                        BufferedChannel$bindCancellationFun$2 bufferedChannel$bindCancellationFun$2 = null;
                        if (objUpdateCellReceive2 == symbol2) {
                            if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                channelSegment.cleanPrev();
                            }
                            ChannelSegment channelSegment4 = (ChannelSegment) bufferedChannel2.receiveSegment.value;
                            while (true) {
                                if (bufferedChannel2.isClosedForReceive()) {
                                    int i2 = Result.$r8$clinit;
                                    orCreateCancellableContinuation.resumeWith(new Result.Failure(bufferedChannel2.getReceiveException()));
                                    break;
                                }
                                long andIncrement2 = bufferedChannel2.receivers.getAndIncrement();
                                long j3 = BufferedChannelKt.SEGMENT_SIZE;
                                long j4 = andIncrement2 / j3;
                                int i3 = (int) (andIncrement2 % j3);
                                try {
                                    if (channelSegment4.id != j4) {
                                        ChannelSegment channelSegmentFindSegmentReceive2 = bufferedChannel2.findSegmentReceive(j4, channelSegment4);
                                        if (channelSegmentFindSegmentReceive2 == null) {
                                            continue;
                                        } else {
                                            channelSegment2 = channelSegmentFindSegmentReceive2;
                                        }
                                    } else {
                                        channelSegment2 = channelSegment4;
                                    }
                                    objUpdateCellReceive2 = bufferedChannel.updateCellReceive(channelSegment2, i3, andIncrement2, cancellableContinuationImpl);
                                    bufferedChannel2 = bufferedChannel;
                                    ChannelSegment channelSegment5 = channelSegment2;
                                    orCreateCancellableContinuation = cancellableContinuationImpl;
                                    if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND) {
                                        orCreateCancellableContinuation.invokeOnCancellation(channelSegment5, i3);
                                        break;
                                    }
                                    if (objUpdateCellReceive2 == BufferedChannelKt.FAILED) {
                                        if (andIncrement2 < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                            channelSegment5.cleanPrev();
                                        }
                                        channelSegment4 = channelSegment5;
                                    } else {
                                        if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                            throw new IllegalStateException("unexpected");
                                        }
                                        channelSegment5.cleanPrev();
                                        if (bufferedChannel2.onUndeliveredElement != null) {
                                            bufferedChannel$bindCancellationFun$2 = new BufferedChannel$bindCancellationFun$2(bufferedChannel2);
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    orCreateCancellableContinuation = cancellableContinuationImpl;
                                    Throwable th2 = th;
                                    orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                                    throw th2;
                                }
                                cancellableContinuationImpl = orCreateCancellableContinuation;
                                bufferedChannel = bufferedChannel2;
                            }
                        } else {
                            channelSegment.cleanPrev();
                            if (bufferedChannel2.onUndeliveredElement != null) {
                                bufferedChannel$bindCancellationFun$2 = new BufferedChannel$bindCancellationFun$2(bufferedChannel2);
                            }
                        }
                        orCreateCancellableContinuation.resume(objUpdateCellReceive2, bufferedChannel$bindCancellationFun$2);
                    }
                    Object result = orCreateCancellableContinuation.getResult();
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return result;
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        }
        Throwable receiveException = this.getReceiveException();
        int i4 = StackTraceRecoveryKt.$r8$clinit;
        throw receiveException;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    public final Object mo3472receiveCatchingJP2dKIU(Continuation continuation) {
        return m3471receiveCatchingJP2dKIU$suspendImpl(this, (ContinuationImpl) continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m3473receiveCatchingOnNoWaiterSuspendGKJJFZk(ChannelSegment channelSegment, int i, long j, ContinuationImpl continuationImpl) {
        BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 bufferedChannel$receiveCatchingOnNoWaiterSuspend$1;
        ChannelResult channelResultM3476boximpl;
        ChannelSegment channelSegment2;
        if (continuationImpl instanceof BufferedChannel$receiveCatchingOnNoWaiterSuspend$1) {
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1 = (BufferedChannel$receiveCatchingOnNoWaiterSuspend$1) continuationImpl;
            int i2 = bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label = i2 - Integer.MIN_VALUE;
            } else {
                bufferedChannel$receiveCatchingOnNoWaiterSuspend$1 = new BufferedChannel$receiveCatchingOnNoWaiterSuspend$1(this, continuationImpl);
            }
        }
        Object result = bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(result);
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$0 = this;
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$1 = channelSegment;
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.I$0 = i;
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.J$0 = j;
            bufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label = 1;
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(bufferedChannel$receiveCatchingOnNoWaiterSuspend$1));
            try {
                ReceiveCatching receiveCatching = new ReceiveCatching(orCreateCancellableContinuation);
                Object objUpdateCellReceive = updateCellReceive(channelSegment, i, j, receiveCatching);
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                    receiveCatching.invokeOnCancellation(channelSegment, i);
                } else {
                    BufferedChannel$bindCancellationFunResult$1 bufferedChannel$bindCancellationFunResult$1 = null;
                    if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                        if (j < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            channelSegment.cleanPrev();
                        }
                        ChannelSegment channelSegment3 = (ChannelSegment) this.receiveSegment.value;
                        while (true) {
                            if (isClosedForReceive()) {
                                int i4 = Result.$r8$clinit;
                                ChannelResult.Companion companion = ChannelResult.Companion;
                                Throwable closeCause = getCloseCause();
                                companion.getClass();
                                orCreateCancellableContinuation.resumeWith(ChannelResult.m3476boximpl(ChannelResult.Companion.m3479closedJP2dKIU(closeCause)));
                                break;
                            }
                            long andIncrement = this.receivers.getAndIncrement();
                            long j2 = BufferedChannelKt.SEGMENT_SIZE;
                            long j3 = andIncrement / j2;
                            int i5 = (int) (andIncrement % j2);
                            if (channelSegment3.id != j3) {
                                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j3, channelSegment3);
                                if (channelSegmentFindSegmentReceive != null) {
                                    channelSegment2 = channelSegmentFindSegmentReceive;
                                }
                            } else {
                                channelSegment2 = channelSegment3;
                            }
                            Object objUpdateCellReceive2 = updateCellReceive(channelSegment2, i5, andIncrement, receiveCatching);
                            ChannelSegment channelSegment4 = channelSegment2;
                            if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND) {
                                receiveCatching.invokeOnCancellation(channelSegment4, i5);
                                break;
                            }
                            if (objUpdateCellReceive2 == BufferedChannelKt.FAILED) {
                                if (andIncrement < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                    channelSegment4.cleanPrev();
                                }
                                channelSegment3 = channelSegment4;
                            } else {
                                if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                    throw new IllegalStateException("unexpected");
                                }
                                channelSegment4.cleanPrev();
                                ChannelResult.Companion.getClass();
                                channelResultM3476boximpl = ChannelResult.m3476boximpl(objUpdateCellReceive2);
                                if (this.onUndeliveredElement != null) {
                                    bufferedChannel$bindCancellationFunResult$1 = new BufferedChannel$bindCancellationFunResult$1(this);
                                }
                            }
                        }
                    } else {
                        channelSegment.cleanPrev();
                        ChannelResult.Companion.getClass();
                        channelResultM3476boximpl = ChannelResult.m3476boximpl(objUpdateCellReceive);
                        if (this.onUndeliveredElement != null) {
                            bufferedChannel$bindCancellationFunResult$1 = new BufferedChannel$bindCancellationFunResult$1(this);
                        }
                    }
                    orCreateCancellableContinuation.resume(channelResultM3476boximpl, bufferedChannel$bindCancellationFunResult$1);
                }
                result = orCreateCancellableContinuation.getResult();
                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (result == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (Throwable th) {
                orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(result);
        }
        return ((ChannelResult) result).holder;
    }

    public final void resumeWaiterOnClosedChannel(Waiter waiter, boolean z) {
        if (waiter instanceof SendBroadcast) {
            CancellableContinuation cancellableContinuation = ((SendBroadcast) waiter).cont;
            int i = Result.$r8$clinit;
            cancellableContinuation.resumeWith(Boolean.FALSE);
            return;
        }
        if (waiter instanceof CancellableContinuation) {
            Continuation continuation = (Continuation) waiter;
            int i2 = Result.$r8$clinit;
            continuation.resumeWith(new Result.Failure(z ? getReceiveException() : getSendException()));
            return;
        }
        if (waiter instanceof ReceiveCatching) {
            CancellableContinuationImpl cancellableContinuationImpl = ((ReceiveCatching) waiter).cont;
            int i3 = Result.$r8$clinit;
            ChannelResult.Companion companion = ChannelResult.Companion;
            Throwable closeCause = getCloseCause();
            companion.getClass();
            cancellableContinuationImpl.resumeWith(ChannelResult.m3476boximpl(ChannelResult.Companion.m3479closedJP2dKIU(closeCause)));
            return;
        }
        if (!(waiter instanceof BufferedChannelIterator)) {
            if (waiter instanceof SelectInstance) {
                ((SelectImplementation) ((SelectInstance) waiter)).trySelectInternal(this, BufferedChannelKt.CHANNEL_CLOSED);
                return;
            } else {
                throw new IllegalStateException(("Unexpected waiter: " + waiter).toString());
            }
        }
        BufferedChannelIterator bufferedChannelIterator = (BufferedChannelIterator) waiter;
        CancellableContinuationImpl cancellableContinuationImpl2 = bufferedChannelIterator.continuation;
        cancellableContinuationImpl2.getClass();
        bufferedChannelIterator.continuation = null;
        bufferedChannelIterator.receiveResult = BufferedChannelKt.CHANNEL_CLOSED;
        Throwable closeCause2 = BufferedChannel.this.getCloseCause();
        if (closeCause2 == null) {
            int i4 = Result.$r8$clinit;
            cancellableContinuationImpl2.resumeWith(Boolean.FALSE);
        } else {
            int i5 = Result.$r8$clinit;
            cancellableContinuationImpl2.resumeWith(new Result.Failure(closeCause2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x00ff A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0138 A[RETURN] */
    @Override // kotlinx.coroutines.channels.SendChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object send(Object obj, Continuation continuation) {
        Unit unit;
        Object result;
        CoroutineSingletons coroutineSingletons;
        int iAccess$updateCellSend;
        ChannelSegment channelSegment = (ChannelSegment) this.sendSegment.value;
        while (true) {
            long andIncrement = this.sendersAndCloseStatus.getAndIncrement();
            long j = andIncrement & 1152921504606846975L;
            boolean z = false;
            boolean zIsClosed = isClosed(andIncrement, false);
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = i;
            long j3 = j / j2;
            int i2 = (int) (j % j2);
            if (channelSegment.id != j3) {
                ChannelSegment channelSegmentAccess$findSegmentSend = access$findSegmentSend(this, j3, channelSegment);
                if (channelSegmentAccess$findSegmentSend != null) {
                    channelSegment = channelSegmentAccess$findSegmentSend;
                } else if (zIsClosed) {
                    Object objOnClosedSend = onClosedSend(obj, continuation);
                    if (objOnClosedSend == CoroutineSingletons.COROUTINE_SUSPENDED) {
                        return objOnClosedSend;
                    }
                }
            }
            int iAccess$updateCellSend2 = access$updateCellSend(this, channelSegment, i2, obj, j, null, zIsClosed);
            if (iAccess$updateCellSend2 == 0) {
                channelSegment.cleanPrev();
                break;
            }
            if (iAccess$updateCellSend2 == 1) {
                break;
            }
            if (iAccess$updateCellSend2 != 2) {
                if (iAccess$updateCellSend2 == 3) {
                    CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                    try {
                        int iAccess$updateCellSend3 = access$updateCellSend(this, channelSegment, i2, obj, j, orCreateCancellableContinuation, false);
                        if (iAccess$updateCellSend3 == 0) {
                            channelSegment.cleanPrev();
                            int i3 = Result.$r8$clinit;
                            unit = Unit.INSTANCE;
                        } else if (iAccess$updateCellSend3 != 1) {
                            if (iAccess$updateCellSend3 != 2) {
                                if (iAccess$updateCellSend3 != 4) {
                                    if (iAccess$updateCellSend3 != 5) {
                                        throw new IllegalStateException("unexpected");
                                    }
                                    channelSegment.cleanPrev();
                                    ChannelSegment channelSegment2 = (ChannelSegment) this.sendSegment.value;
                                    while (true) {
                                        long andIncrement2 = this.sendersAndCloseStatus.getAndIncrement();
                                        long j4 = andIncrement2 & 1152921504606846975L;
                                        boolean zIsClosed2 = isClosed(andIncrement2, z);
                                        int i4 = BufferedChannelKt.SEGMENT_SIZE;
                                        long j5 = i4;
                                        long j6 = j4 / j5;
                                        int i5 = (int) (j4 % j5);
                                        if (channelSegment2.id == j6) {
                                            iAccess$updateCellSend = access$updateCellSend(this, channelSegment2, i5, obj, j4, orCreateCancellableContinuation, zIsClosed2);
                                            if (iAccess$updateCellSend != 0) {
                                                channelSegment2.cleanPrev();
                                                int i6 = Result.$r8$clinit;
                                                unit = Unit.INSTANCE;
                                                break;
                                            }
                                            if (iAccess$updateCellSend == 1) {
                                                int i7 = Result.$r8$clinit;
                                                unit = Unit.INSTANCE;
                                                break;
                                            }
                                            if (iAccess$updateCellSend != 2) {
                                                if (iAccess$updateCellSend == 3) {
                                                    throw new IllegalStateException("unexpected");
                                                }
                                                if (iAccess$updateCellSend != 4) {
                                                    if (iAccess$updateCellSend == 5) {
                                                        channelSegment2.cleanPrev();
                                                    }
                                                    z = false;
                                                } else if (j4 < this.receivers.value) {
                                                    channelSegment2.cleanPrev();
                                                }
                                            } else if (zIsClosed2) {
                                                channelSegment2.onSlotCleaned();
                                            } else {
                                                orCreateCancellableContinuation.invokeOnCancellation(channelSegment2, i5 + i4);
                                            }
                                        } else {
                                            ChannelSegment channelSegmentAccess$findSegmentSend2 = access$findSegmentSend(this, j6, channelSegment2);
                                            if (channelSegmentAccess$findSegmentSend2 != null) {
                                                channelSegment2 = channelSegmentAccess$findSegmentSend2;
                                                iAccess$updateCellSend = access$updateCellSend(this, channelSegment2, i5, obj, j4, orCreateCancellableContinuation, zIsClosed2);
                                                if (iAccess$updateCellSend != 0) {
                                                }
                                            } else {
                                                if (zIsClosed2) {
                                                    break;
                                                }
                                                z = false;
                                            }
                                        }
                                    }
                                } else if (j < this.receivers.value) {
                                    channelSegment.cleanPrev();
                                }
                                access$onClosedSendOnNoWaiterSuspend(this, obj, orCreateCancellableContinuation);
                            } else {
                                orCreateCancellableContinuation.invokeOnCancellation(channelSegment, i2 + i);
                            }
                            result = orCreateCancellableContinuation.getResult();
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (result != coroutineSingletons) {
                                result = Unit.INSTANCE;
                            }
                            if (result == coroutineSingletons) {
                                return result;
                            }
                        } else {
                            int i8 = Result.$r8$clinit;
                            unit = Unit.INSTANCE;
                        }
                        orCreateCancellableContinuation.resumeWith(unit);
                        result = orCreateCancellableContinuation.getResult();
                        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (result != coroutineSingletons) {
                        }
                        if (result == coroutineSingletons) {
                        }
                    } catch (Throwable th) {
                        orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                        throw th;
                    }
                } else if (iAccess$updateCellSend2 == 4) {
                    if (j < this.receivers.value) {
                        channelSegment.cleanPrev();
                    }
                    Object objOnClosedSend2 = onClosedSend(obj, continuation);
                    if (objOnClosedSend2 == CoroutineSingletons.COROUTINE_SUSPENDED) {
                        return objOnClosedSend2;
                    }
                } else if (iAccess$updateCellSend2 == 5) {
                    channelSegment.cleanPrev();
                }
            } else if (zIsClosed) {
                channelSegment.onSlotCleaned();
                Object objOnClosedSend3 = onClosedSend(obj, continuation);
                if (objOnClosedSend3 == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return objOnClosedSend3;
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x01aa, code lost:
    
        r4 = (kotlinx.coroutines.channels.ChannelSegment) r4.getNext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01b1, code lost:
    
        if (r4 != null) goto L93;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String toString() {
        String string;
        StringBuilder sb = new StringBuilder();
        int i = (int) (this.sendersAndCloseStatus.value >> 60);
        if (i == 2) {
            sb.append("closed,");
        } else if (i == 3) {
            sb.append("cancelled,");
        }
        sb.append("capacity=" + this.capacity + ",");
        sb.append("data=[");
        int i2 = 0;
        List listAsList = Arrays.asList(this.receiveSegment.value, this.sendSegment.value, this.bufferEndSegment.value);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAsList) {
            if (((ChannelSegment) obj) != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        Object next = it.next();
        if (it.hasNext()) {
            long j = ((ChannelSegment) next).id;
            do {
                Object next2 = it.next();
                long j2 = ((ChannelSegment) next2).id;
                if (j > j2) {
                    next = next2;
                    j = j2;
                }
            } while (it.hasNext());
        }
        ChannelSegment channelSegment = (ChannelSegment) next;
        long j3 = this.receivers.value;
        long sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        loop2: while (true) {
            int i3 = BufferedChannelKt.SEGMENT_SIZE;
            int i4 = i2;
            while (true) {
                if (i4 >= i3) {
                    break;
                }
                long j4 = (channelSegment.id * BufferedChannelKt.SEGMENT_SIZE) + i4;
                if (j4 >= sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host && j4 >= j3) {
                    break loop2;
                }
                Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i4);
                Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i4);
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CancellableContinuation) {
                    string = (j4 >= j3 || j4 < sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) ? (j4 >= sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host || j4 < j3) ? "cont" : "send" : "receive";
                } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof SelectInstance) {
                    string = (j4 >= j3 || j4 < sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) ? (j4 >= sendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host || j4 < j3) ? "select" : "onSend" : "onReceive";
                } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof ReceiveCatching) {
                    string = "receiveCatching";
                } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof SendBroadcast) {
                    string = "sendBroadcast";
                } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB) {
                    string = "EB(" + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host + ")";
                } else if (Intrinsics.areEqual(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_RCV) || Intrinsics.areEqual(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_EB)) {
                    string = "resuming_sender";
                } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.IN_BUFFER) || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.DONE_RCV) || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.POISONED) || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.INTERRUPTED_RCV) || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.INTERRUPTED_SEND) || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.equals(BufferedChannelKt.CHANNEL_CLOSED)) {
                    i4++;
                } else {
                    string = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.toString();
                }
                if (element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
                    sb.append("(" + string + "," + element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host + "),");
                } else {
                    sb.append(string + ",");
                }
                i4++;
            }
            i2 = 0;
        }
        if (StringsKt___StringsKt.last(sb) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public final Object mo3474tryReceivePtdJZtk() {
        ChannelSegment channelSegmentFindSegmentReceive;
        long j = this.receivers.value;
        long j2 = this.sendersAndCloseStatus.value;
        if (isClosed(j2, true)) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Throwable closeCause = getCloseCause();
            companion.getClass();
            return ChannelResult.Companion.m3479closedJP2dKIU(closeCause);
        }
        if (j >= (j2 & 1152921504606846975L)) {
            ChannelResult.Companion.getClass();
            return ChannelResult.failed;
        }
        Object obj = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment channelSegment = (ChannelSegment) this.receiveSegment.value;
        while (!this.isClosedForReceive()) {
            long andIncrement = this.receivers.getAndIncrement();
            long j3 = BufferedChannelKt.SEGMENT_SIZE;
            long j4 = andIncrement / j3;
            int i = (int) (andIncrement % j3);
            if (channelSegment.id != j4) {
                channelSegmentFindSegmentReceive = this.findSegmentReceive(j4, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                }
            } else {
                channelSegmentFindSegmentReceive = channelSegment;
            }
            BufferedChannel bufferedChannel = this;
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegmentFindSegmentReceive, i, andIncrement, obj);
            channelSegment = channelSegmentFindSegmentReceive;
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    waiter.invokeOnCancellation(channelSegment, i);
                }
                bufferedChannel.waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(andIncrement);
                channelSegment.onSlotCleaned();
                ChannelResult.Companion.getClass();
                return ChannelResult.failed;
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected");
                }
                channelSegment.cleanPrev();
                ChannelResult.Companion.getClass();
                return objUpdateCellReceive;
            }
            if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            this = bufferedChannel;
        }
        ChannelResult.Companion companion2 = ChannelResult.Companion;
        Throwable closeCause2 = this.getCloseCause();
        companion2.getClass();
        return ChannelResult.Companion.m3479closedJP2dKIU(closeCause2);
    }

    public final boolean tryResumeReceiver(Object obj, Object obj2) {
        if (obj instanceof SelectInstance) {
            return ((SelectImplementation) ((SelectInstance) obj)).trySelectInternal(this, obj2) == 0;
        }
        boolean z = obj instanceof ReceiveCatching;
        Function1 function1 = this.onUndeliveredElement;
        if (z) {
            CancellableContinuationImpl cancellableContinuationImpl = ((ReceiveCatching) obj).cont;
            ChannelResult.Companion.getClass();
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, ChannelResult.m3476boximpl(obj2), function1 != null ? new BufferedChannel$bindCancellationFunResult$1(this) : null);
        }
        if (!(obj instanceof BufferedChannelIterator)) {
            if (obj instanceof CancellableContinuation) {
                return BufferedChannelKt.tryResume0((CancellableContinuation) obj, obj2, function1 != null ? new BufferedChannel$bindCancellationFun$2(this) : null);
            }
            throw new IllegalStateException(("Unexpected receiver type: " + obj).toString());
        }
        BufferedChannelIterator bufferedChannelIterator = (BufferedChannelIterator) obj;
        CancellableContinuationImpl cancellableContinuationImpl2 = bufferedChannelIterator.continuation;
        cancellableContinuationImpl2.getClass();
        bufferedChannelIterator.continuation = null;
        bufferedChannelIterator.receiveResult = obj2;
        Boolean bool = Boolean.TRUE;
        Function1 function12 = BufferedChannel.this.onUndeliveredElement;
        return BufferedChannelKt.tryResume0(cancellableContinuationImpl2, bool, function12 != null ? new BufferedChannel$$ExternalSyntheticLambda1(obj2, function12) : null);
    }

    public final boolean tryResumeSender(Object obj, ChannelSegment channelSegment, int i) {
        TrySelectDetailedResult trySelectDetailedResult;
        if (obj instanceof CancellableContinuation) {
            return BufferedChannelKt.tryResume0((CancellableContinuation) obj, Unit.INSTANCE, null);
        }
        if (!(obj instanceof SelectInstance)) {
            if (obj instanceof SendBroadcast) {
                return BufferedChannelKt.tryResume0(((SendBroadcast) obj).cont, Boolean.TRUE, null);
            }
            throw new IllegalStateException(("Unexpected waiter: " + obj).toString());
        }
        int iTrySelectInternal = ((SelectImplementation) obj).trySelectInternal(this, Unit.INSTANCE);
        SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 selectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 = SelectKt.DUMMY_PROCESS_RESULT_FUNCTION;
        if (iTrySelectInternal == 0) {
            trySelectDetailedResult = TrySelectDetailedResult.SUCCESSFUL;
        } else if (iTrySelectInternal == 1) {
            trySelectDetailedResult = TrySelectDetailedResult.REREGISTER;
        } else if (iTrySelectInternal == 2) {
            trySelectDetailedResult = TrySelectDetailedResult.CANCELLED;
        } else {
            if (iTrySelectInternal != 3) {
                throw new IllegalStateException(("Unexpected internal result: " + iTrySelectInternal).toString());
            }
            trySelectDetailedResult = TrySelectDetailedResult.ALREADY_SELECTED;
        }
        if (trySelectDetailedResult == TrySelectDetailedResult.REREGISTER) {
            channelSegment.setElementLazy(i, null);
        }
        return trySelectDetailedResult == TrySelectDetailedResult.SUCCESSFUL;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* renamed from: trySend-JP2dKIU, reason: not valid java name */
    public Object mo3475trySendJP2dKIU(Object obj) {
        ChannelSegment channelSegment;
        BufferedChannel bufferedChannel;
        Object obj2;
        int i;
        if (isClosed(this.sendersAndCloseStatus.value, false) ? false : !bufferOrRendezvousSend(r0 & 1152921504606846975L)) {
            ChannelResult.Companion.getClass();
            return ChannelResult.failed;
        }
        Object obj3 = BufferedChannelKt.INTERRUPTED_SEND;
        ChannelSegment channelSegment2 = (ChannelSegment) this.sendSegment.value;
        while (true) {
            long andIncrement = this.sendersAndCloseStatus.getAndIncrement();
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosed = isClosed(andIncrement, false);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = i2;
            long j3 = j / j2;
            int i3 = (int) (j % j2);
            if (channelSegment2.id != j3) {
                ChannelSegment channelSegmentAccess$findSegmentSend = access$findSegmentSend(this, j3, channelSegment2);
                if (channelSegmentAccess$findSegmentSend != null) {
                    channelSegment = channelSegmentAccess$findSegmentSend;
                    obj2 = obj;
                    i = i3;
                    bufferedChannel = this;
                } else if (zIsClosed) {
                    break;
                }
            } else {
                channelSegment = channelSegment2;
                bufferedChannel = this;
                obj2 = obj;
                i = i3;
            }
            int iAccess$updateCellSend = access$updateCellSend(bufferedChannel, channelSegment, i, obj2, j, obj3, zIsClosed);
            ChannelSegment channelSegment3 = channelSegment;
            if (iAccess$updateCellSend == 0) {
                channelSegment3.cleanPrev();
                ChannelResult.Companion companion = ChannelResult.Companion;
                Unit unit = Unit.INSTANCE;
                companion.getClass();
                return unit;
            }
            if (iAccess$updateCellSend == 1) {
                ChannelResult.Companion companion2 = ChannelResult.Companion;
                Unit unit2 = Unit.INSTANCE;
                companion2.getClass();
                return unit2;
            }
            if (iAccess$updateCellSend != 2) {
                if (iAccess$updateCellSend == 3) {
                    throw new IllegalStateException("unexpected");
                }
                if (iAccess$updateCellSend != 4) {
                    if (iAccess$updateCellSend == 5) {
                        channelSegment3.cleanPrev();
                    }
                    channelSegment2 = channelSegment3;
                } else if (j < this.receivers.value) {
                    channelSegment3.cleanPrev();
                }
            } else {
                if (!zIsClosed) {
                    Waiter waiter = obj3 instanceof Waiter ? (Waiter) obj3 : null;
                    if (waiter != null) {
                        waiter.invokeOnCancellation(channelSegment3, i + i2);
                    }
                    channelSegment3.onSlotCleaned();
                    ChannelResult.Companion.getClass();
                    return ChannelResult.failed;
                }
                channelSegment3.onSlotCleaned();
            }
        }
        ChannelResult.Companion companion3 = ChannelResult.Companion;
        Throwable sendException = getSendException();
        companion3.getClass();
        return ChannelResult.Companion.m3479closedJP2dKIU(sendException);
    }

    public final Object updateCellReceive(ChannelSegment channelSegment, int i, long j, Object obj) {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
            if (j >= (this.sendersAndCloseStatus.value & 1152921504606846975L)) {
                if (obj == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED && channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            channelSegment.setElementLazy(i, null);
            return element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        }
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == null || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.IN_BUFFER) {
                if (j < (this.sendersAndCloseStatus.value & 1152921504606846975L)) {
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else {
                    if (obj == null) {
                        return BufferedChannelKt.SUSPEND_NO_WAITER;
                    }
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, obj)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != BufferedChannelKt.BUFFERED) {
                Symbol symbol = BufferedChannelKt.INTERRUPTED_SEND;
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == symbol) {
                    return BufferedChannelKt.FAILED;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.POISONED) {
                    return BufferedChannelKt.FAILED;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 == BufferedChannelKt.CHANNEL_CLOSED) {
                    expandBuffer();
                    return BufferedChannelKt.FAILED;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 != BufferedChannelKt.RESUMING_BY_EB && channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.RESUMING_BY_RCV)) {
                    boolean z = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof WaiterEB;
                    if (z) {
                        state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2).waiter;
                    }
                    if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, channelSegment, i)) {
                        channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                        expandBuffer();
                        Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = channelSegment.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                        channelSegment.setElementLazy(i, null);
                        return element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2;
                    }
                    channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, symbol);
                    channelSegment.onSlotCleaned();
                    if (z) {
                        expandBuffer();
                    }
                    return BufferedChannelKt.FAILED;
                }
            } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2, BufferedChannelKt.DONE_RCV)) {
                expandBuffer();
                Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3 = channelSegment.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                channelSegment.setElementLazy(i, null);
                return element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3;
            }
        }
    }

    public final int updateCellSendSlow(ChannelSegment channelSegment, int i, Object obj, long j, Object obj2, boolean z) {
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
                if (!bufferOrRendezvousSend(j) || z) {
                    if (z) {
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.INTERRUPTED_SEND)) {
                            channelSegment.onSlotCleaned();
                            return 4;
                        }
                    } else {
                        if (obj2 == null) {
                            return 3;
                        }
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, obj2)) {
                            return 2;
                        }
                    }
                } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.BUFFERED)) {
                    break;
                }
            } else {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                    Symbol symbol = BufferedChannelKt.INTERRUPTED_RCV;
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == symbol) {
                        channelSegment.setElementLazy(i, null);
                        return 5;
                    }
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.POISONED) {
                        channelSegment.setElementLazy(i, null);
                        return 5;
                    }
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.CHANNEL_CLOSED) {
                        channelSegment.setElementLazy(i, null);
                        isClosedForSend();
                        return 4;
                    }
                    channelSegment.setElementLazy(i, null);
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB) {
                        state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).waiter;
                    }
                    if (tryResumeReceiver(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                        channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                        return 0;
                    }
                    if (channelSegment.data.array[(i * 2) + 1].getAndSet(symbol) != symbol) {
                        channelSegment.onCancelledRequest(i, true);
                    }
                    return 5;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.BUFFERED)) {
                    break;
                }
            }
        }
        return 1;
    }

    public final void waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(long j) {
        long j2;
        long j3;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        while (this.bufferEnd.value <= j) {
        }
        int i = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
        for (int i2 = 0; i2 < i; i2++) {
            long j4 = this.bufferEnd.value;
            if (j4 == (4611686018427387903L & this.completedExpandBuffersAndPauseFlag.value) && j4 == this.bufferEnd.value) {
                return;
            }
        }
        AtomicLong atomicLong = this.completedExpandBuffersAndPauseFlag;
        do {
            j2 = atomicLong.value;
        } while (!atomicLong.compareAndSet(j2, (j2 & 4611686018427387903L) + 4611686018427387904L));
        while (true) {
            long j5 = this.bufferEnd.value;
            long j6 = this.completedExpandBuffersAndPauseFlag.value;
            long j7 = j6 & 4611686018427387903L;
            boolean z = (j6 & 4611686018427387904L) != 0;
            if (j5 == j7 && j5 == this.bufferEnd.value) {
                break;
            } else if (!z) {
                this.completedExpandBuffersAndPauseFlag.compareAndSet(j6, j7 + 4611686018427387904L);
            }
        }
        AtomicLong atomicLong2 = this.completedExpandBuffersAndPauseFlag;
        do {
            j3 = atomicLong2.value;
        } while (!atomicLong2.compareAndSet(j3, j3 & 4611686018427387903L));
    }

    public /* synthetic */ BufferedChannel(int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : function1);
    }
}
