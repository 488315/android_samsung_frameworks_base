package kotlinx.coroutines.channels;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BufferedChannelIterator implements Waiter {
        public CancellableContinuationImpl continuation;
        public Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        public final Object hasNext(ContinuationImpl continuationImpl) {
            ChannelSegment channelSegment;
            Boolean bool;
            Object obj = this.receiveResult;
            boolean z = true;
            if (obj == BufferedChannelKt.NO_RECEIVE_RESULT || obj == BufferedChannelKt.CHANNEL_CLOSED) {
                BufferedChannel bufferedChannel = BufferedChannel.this;
                ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel.receiveSegment.value;
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
                        if (channelSegment2.id != j2) {
                            channelSegment = bufferedChannel.findSegmentReceive(j2, channelSegment2);
                            if (channelSegment == null) {
                                continue;
                            }
                        } else {
                            channelSegment = channelSegment2;
                        }
                        Object updateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i2, andIncrement, null);
                        Symbol symbol = BufferedChannelKt.SUSPEND;
                        if (updateCellReceive == symbol) {
                            throw new IllegalStateException("unreachable");
                        }
                        Symbol symbol2 = BufferedChannelKt.FAILED;
                        if (updateCellReceive == symbol2) {
                            if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                channelSegment.cleanPrev();
                            }
                            channelSegment2 = channelSegment;
                        } else {
                            if (updateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                BufferedChannel bufferedChannel2 = BufferedChannel.this;
                                CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl));
                                try {
                                    this.continuation = orCreateCancellableContinuation;
                                    Object updateCellReceive2 = bufferedChannel2.updateCellReceive(channelSegment, i2, andIncrement, this);
                                    if (updateCellReceive2 == symbol) {
                                        invokeOnCancellation(channelSegment, i2);
                                    } else {
                                        BufferedChannel$$ExternalSyntheticLambda1 bufferedChannel$$ExternalSyntheticLambda1 = null;
                                        if (updateCellReceive2 == symbol2) {
                                            if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                                channelSegment.cleanPrev();
                                            }
                                            ChannelSegment channelSegment3 = (ChannelSegment) bufferedChannel2.receiveSegment.value;
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
                                                    if (channelSegment3.id != j4) {
                                                        ChannelSegment findSegmentReceive = bufferedChannel2.findSegmentReceive(j4, channelSegment3);
                                                        if (findSegmentReceive != null) {
                                                            channelSegment3 = findSegmentReceive;
                                                        }
                                                    }
                                                    Object updateCellReceive3 = bufferedChannel2.updateCellReceive(channelSegment3, i5, andIncrement2, this);
                                                    if (updateCellReceive3 == BufferedChannelKt.SUSPEND) {
                                                        invokeOnCancellation(channelSegment3, i5);
                                                        break;
                                                    }
                                                    if (updateCellReceive3 == BufferedChannelKt.FAILED) {
                                                        if (andIncrement2 < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                                            channelSegment3.cleanPrev();
                                                        }
                                                    } else {
                                                        if (updateCellReceive3 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                                            throw new IllegalStateException("unexpected");
                                                        }
                                                        channelSegment3.cleanPrev();
                                                        this.receiveResult = updateCellReceive3;
                                                        this.continuation = null;
                                                        bool = Boolean.TRUE;
                                                        Function1 function1 = bufferedChannel2.onUndeliveredElement;
                                                        if (function1 != null) {
                                                            bufferedChannel$$ExternalSyntheticLambda1 = new BufferedChannel$$ExternalSyntheticLambda1(updateCellReceive3, function1);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            channelSegment.cleanPrev();
                                            this.receiveResult = updateCellReceive2;
                                            this.continuation = null;
                                            bool = Boolean.TRUE;
                                            Function1 function12 = bufferedChannel2.onUndeliveredElement;
                                            if (function12 != null) {
                                                bufferedChannel$$ExternalSyntheticLambda1 = new BufferedChannel$$ExternalSyntheticLambda1(updateCellReceive2, function12);
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
                            channelSegment.cleanPrev();
                            this.receiveResult = updateCellReceive;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        AtomicLong atomic = AtomicFU.atomic(i != 0 ? i != Integer.MAX_VALUE ? i : Long.MAX_VALUE : 0L);
        this.bufferEnd = atomic;
        this.completedExpandBuffersAndPauseFlag = AtomicFU.atomic(atomic.value);
        ChannelSegment channelSegment2 = new ChannelSegment(0L, null, this, 3);
        this.sendSegment = AtomicFU.atomic(channelSegment2);
        this.receiveSegment = AtomicFU.atomic(channelSegment2);
        this.bufferEndSegment = AtomicFU.atomic(isRendezvousOrUnlimited() ? BufferedChannelKt.NULL_SEGMENT : channelSegment2);
        this.onUndeliveredElementReceiveCancellationConstructor = function1 != null ? new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, final Object obj3) {
                final SelectInstance selectInstance = (SelectInstance) obj;
                final BufferedChannel bufferedChannel = BufferedChannel.this;
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
        Object findSegmentInternal;
        long j2;
        long j3;
        AtomicRef atomicRef = bufferedChannel.sendSegment;
        ChannelSegment channelSegment2 = BufferedChannelKt.NULL_SEGMENT;
        BufferedChannelKt$createSegmentFunction$1 bufferedChannelKt$createSegmentFunction$1 = BufferedChannelKt$createSegmentFunction$1.INSTANCE;
        loop0: while (true) {
            findSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
            if (!SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
                Segment m3465getSegmentimpl = SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.value;
                    if (segment.id >= m3465getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!m3465getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, m3465getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (m3465getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        m3465getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
            bufferedChannel.isClosedForSend();
            if (channelSegment.id * BufferedChannelKt.SEGMENT_SIZE < bufferedChannel.receivers.value) {
                channelSegment.cleanPrev();
                return null;
            }
        } else {
            ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* renamed from: receiveCatching-JP2dKIU$suspendImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object m3452receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            if (r0 == 0) goto L14
            r0 = r14
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            r0.<init>(r13, r14)
            goto L12
        L1a:
            java.lang.Object r14 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L35
            if (r1 != r2) goto L2d
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.coroutines.channels.ChannelResult r14 = (kotlinx.coroutines.channels.ChannelResult) r14
            java.lang.Object r13 = r14.holder
            return r13
        L2d:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L35:
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.atomicfu.AtomicRef r14 = r13.receiveSegment
            java.lang.Object r14 = r14.value
            kotlinx.coroutines.channels.ChannelSegment r14 = (kotlinx.coroutines.channels.ChannelSegment) r14
        L3e:
            boolean r1 = r13.isClosedForReceive()
            if (r1 == 0) goto L52
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Throwable r13 = r13.getCloseCause()
            r14.getClass()
            kotlinx.coroutines.channels.ChannelResult$Closed r13 = kotlinx.coroutines.channels.ChannelResult.Companion.m3460closedJP2dKIU(r13)
            return r13
        L52:
            kotlinx.atomicfu.AtomicLong r1 = r13.receivers
            long r4 = r1.getAndIncrement()
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r1
            long r9 = r4 / r7
            long r7 = r4 % r7
            int r3 = (int) r7
            long r7 = r14.id
            int r1 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r1 == 0) goto L6f
            kotlinx.coroutines.channels.ChannelSegment r1 = r13.findSegmentReceive(r9, r14)
            if (r1 != 0) goto L6d
            goto L3e
        L6d:
            r8 = r1
            goto L70
        L6f:
            r8 = r14
        L70:
            r12 = 0
            r7 = r13
            r9 = r3
            r10 = r4
            java.lang.Object r13 = r7.updateCellReceive(r8, r9, r10, r12)
            r1 = r7
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND
            if (r13 == r14) goto La7
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED
            if (r13 != r14) goto L8f
            long r13 = r1.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r13 >= 0) goto L8c
            r8.cleanPrev()
        L8c:
            r13 = r1
            r14 = r8
            goto L3e
        L8f:
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND_NO_WAITER
            if (r13 != r14) goto L9e
            r6.label = r2
            r2 = r8
            java.lang.Object r13 = r1.m3454receiveCatchingOnNoWaiterSuspendGKJJFZk(r2, r3, r4, r6)
            if (r13 != r0) goto L9d
            return r0
        L9d:
            return r13
        L9e:
            r8.cleanPrev()
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.Companion
            r14.getClass()
            return r13
        La7:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "unexpected"
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m3452receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        boolean compareAndSet = this._closeCause.compareAndSet(BufferedChannelKt.NO_CLOSE_CAUSE, th);
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
        if (compareAndSet) {
            AtomicRef atomicRef = this.closeHandler;
            do {
                obj = atomicRef.value;
            } while (!atomicRef.compareAndSet(obj, obj == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
            if (obj != null) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, obj);
                ((Function1) obj).mo779invoke(getCloseCause());
            }
        }
        return compareAndSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0083, code lost:
    
        r2 = (kotlinx.coroutines.channels.ChannelSegment) ((kotlinx.coroutines.internal.ConcurrentLinkedListNode) r2._prev.value);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlinx.coroutines.channels.ChannelSegment completeClose(long r13) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.completeClose(long):kotlinx.coroutines.channels.ChannelSegment");
    }

    public final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long j) {
        ChannelSegment channelSegment;
        UndeliveredElementException callUndeliveredElementCatchingException;
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
                    ChannelSegment findSegmentReceive = this.findSegmentReceive(j4, channelSegment2);
                    if (findSegmentReceive != null) {
                        channelSegment = findSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                BufferedChannel bufferedChannel = this;
                Object updateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, j2, null);
                if (updateCellReceive != BufferedChannelKt.FAILED) {
                    channelSegment.cleanPrev();
                    Function1 function1 = bufferedChannel.onUndeliveredElement;
                    if (function1 != null && (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, updateCellReceive, null)) != null) {
                        throw callUndeliveredElementCatchingException;
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

    public final void expandBuffer() {
        Object findSegmentInternal;
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
                    findSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
                    if (!SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
                        Segment m3465getSegmentimpl = SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
                        while (true) {
                            Segment segment = (Segment) atomicRef.value;
                            if (segment.id >= m3465getSegmentimpl.id) {
                                break;
                            }
                            if (!m3465getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                break;
                            }
                            if (atomicRef.compareAndSet(segment, m3465getSegmentimpl)) {
                                if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                    segment.remove();
                                }
                            } else if (m3465getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                m3465getSegmentimpl.remove();
                            }
                        }
                    } else {
                        break;
                    }
                }
                ChannelSegment channelSegment2 = null;
                if (SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
                    isClosedForSend();
                    moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
                    incCompletedExpandBufferAttempts$default(this);
                } else {
                    ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
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
                incCompletedExpandBufferAttempts$default(this);
            } else if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, channelSegment, i)) {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.BUFFERED);
                break;
            } else {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
                channelSegment.onSlotCleaned();
                incCompletedExpandBufferAttempts$default(this);
            }
        }
        incCompletedExpandBufferAttempts$default(this);
    }

    public final ChannelSegment findSegmentReceive(long j, ChannelSegment channelSegment) {
        Object findSegmentInternal;
        long j2;
        AtomicRef atomicRef = this.receiveSegment;
        ChannelSegment channelSegment2 = BufferedChannelKt.NULL_SEGMENT;
        BufferedChannelKt$createSegmentFunction$1 bufferedChannelKt$createSegmentFunction$1 = BufferedChannelKt$createSegmentFunction$1.INSTANCE;
        loop0: while (true) {
            findSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, bufferedChannelKt$createSegmentFunction$1);
            if (!SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
                Segment m3465getSegmentimpl = SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.value;
                    if (segment.id >= m3465getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!m3465getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, m3465getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (m3465getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        m3465getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m3466isClosedimpl(findSegmentInternal)) {
            isClosedForSend();
            if (channelSegment.id * BufferedChannelKt.SEGMENT_SIZE < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        ChannelSegment channelSegment3 = (ChannelSegment) SegmentOrClosed.m3465getSegmentimpl(findSegmentInternal);
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
        produceKt$awaitClose$4$1.mo779invoke(getCloseCause());
    }

    /* JADX WARN: Code restructure failed: missing block: B:129:0x0178, code lost:
    
        r11.receivers.compareAndSet(r13, 1 + r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00b8, code lost:
    
        r12 = (kotlinx.coroutines.channels.ChannelSegment) ((kotlinx.coroutines.internal.ConcurrentLinkedListNode) r12._prev.value);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isClosed(long r12, boolean r14) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.isClosed(long, boolean):boolean");
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

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0011, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void moveSegmentBufferEndToSpecifiedOrLast(long r5, kotlinx.coroutines.channels.ChannelSegment r7) {
        /*
            r4 = this;
        L0:
            long r0 = r7.id
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L11
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r0 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            if (r0 != 0) goto Lf
            goto L11
        Lf:
            r7 = r0
            goto L0
        L11:
            boolean r5 = r7.isRemoved()
            if (r5 == 0) goto L22
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r5 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            if (r5 != 0) goto L20
            goto L22
        L20:
            r7 = r5
            goto L11
        L22:
            kotlinx.atomicfu.AtomicRef r5 = r4.bufferEndSegment
        L24:
            java.lang.Object r6 = r5.value
            kotlinx.coroutines.internal.Segment r6 = (kotlinx.coroutines.internal.Segment) r6
            long r0 = r6.id
            long r2 = r7.id
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L31
            goto L47
        L31:
            boolean r0 = r7.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r0 != 0) goto L38
            goto L11
        L38:
            boolean r0 = r5.compareAndSet(r6, r7)
            if (r0 == 0) goto L48
            boolean r4 = r6.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r4 == 0) goto L47
            r6.remove()
        L47:
            return
        L48:
            boolean r6 = r7.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r6 == 0) goto L24
            r7.remove()
            goto L24
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.moveSegmentBufferEndToSpecifiedOrLast(long, kotlinx.coroutines.channels.ChannelSegment):void");
    }

    public final Object onClosedSend(Object obj, Continuation continuation) {
        UndeliveredElementException callUndeliveredElementCatchingException;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Function1 function1 = this.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) == null) {
            Throwable sendException = getSendException();
            int i = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(sendException));
        } else {
            ExceptionsKt__ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException, getSendException());
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(callUndeliveredElementCatchingException));
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final Object receive(Continuation continuation) {
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
                ChannelSegment findSegmentReceive = this.findSegmentReceive(j2, channelSegment3);
                if (findSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = findSegmentReceive;
                }
            } else {
                channelSegment = channelSegment3;
            }
            BufferedChannel bufferedChannel2 = this;
            Object updateCellReceive = bufferedChannel2.updateCellReceive(channelSegment, i, andIncrement, null);
            Symbol symbol = BufferedChannelKt.SUSPEND;
            if (updateCellReceive == symbol) {
                throw new IllegalStateException("unexpected");
            }
            Symbol symbol2 = BufferedChannelKt.FAILED;
            if (updateCellReceive == symbol2) {
                if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment.cleanPrev();
                }
                this = bufferedChannel2;
                channelSegment3 = channelSegment;
            } else {
                if (updateCellReceive != BufferedChannelKt.SUSPEND_NO_WAITER) {
                    channelSegment.cleanPrev();
                    return updateCellReceive;
                }
                CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                try {
                    Object updateCellReceive2 = bufferedChannel2.updateCellReceive(channelSegment, i, andIncrement, orCreateCancellableContinuation);
                    if (updateCellReceive2 == symbol) {
                        orCreateCancellableContinuation.invokeOnCancellation(channelSegment, i);
                    } else {
                        BufferedChannel$bindCancellationFun$2 bufferedChannel$bindCancellationFun$2 = null;
                        if (updateCellReceive2 == symbol2) {
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
                                        ChannelSegment findSegmentReceive2 = bufferedChannel2.findSegmentReceive(j4, channelSegment4);
                                        if (findSegmentReceive2 == null) {
                                            continue;
                                        } else {
                                            channelSegment2 = findSegmentReceive2;
                                        }
                                    } else {
                                        channelSegment2 = channelSegment4;
                                    }
                                    updateCellReceive2 = bufferedChannel.updateCellReceive(channelSegment2, i3, andIncrement2, cancellableContinuationImpl);
                                    bufferedChannel2 = bufferedChannel;
                                    ChannelSegment channelSegment5 = channelSegment2;
                                    orCreateCancellableContinuation = cancellableContinuationImpl;
                                    if (updateCellReceive2 == BufferedChannelKt.SUSPEND) {
                                        orCreateCancellableContinuation.invokeOnCancellation(channelSegment5, i3);
                                        break;
                                    }
                                    if (updateCellReceive2 == BufferedChannelKt.FAILED) {
                                        if (andIncrement2 < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                            channelSegment5.cleanPrev();
                                        }
                                        channelSegment4 = channelSegment5;
                                    } else {
                                        if (updateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
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
                        orCreateCancellableContinuation.resume(updateCellReceive2, bufferedChannel$bindCancellationFun$2);
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
    public final Object mo3453receiveCatchingJP2dKIU(Continuation continuation) {
        return m3452receiveCatchingJP2dKIU$suspendImpl(this, (ContinuationImpl) continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m3454receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment r9, int r10, long r11, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m3454receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
            cancellableContinuationImpl.resumeWith(ChannelResult.m3457boximpl(ChannelResult.Companion.m3460closedJP2dKIU(closeCause)));
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

    /* JADX WARN: Removed duplicated region for block: B:53:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0138 A[RETURN] */
    @Override // kotlinx.coroutines.channels.SendChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object send(java.lang.Object r21, kotlin.coroutines.Continuation r22) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.send(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x01aa, code lost:
    
        r4 = (kotlinx.coroutines.channels.ChannelSegment) r4.getNext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01b1, code lost:
    
        if (r4 != null) goto L93;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.toString():java.lang.String");
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public final Object mo3455tryReceivePtdJZtk() {
        ChannelSegment channelSegment;
        long j = this.receivers.value;
        long j2 = this.sendersAndCloseStatus.value;
        if (isClosed(j2, true)) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Throwable closeCause = getCloseCause();
            companion.getClass();
            return ChannelResult.Companion.m3460closedJP2dKIU(closeCause);
        }
        if (j >= (j2 & 1152921504606846975L)) {
            ChannelResult.Companion.getClass();
            return ChannelResult.failed;
        }
        Object obj = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment channelSegment2 = (ChannelSegment) this.receiveSegment.value;
        while (!this.isClosedForReceive()) {
            long andIncrement = this.receivers.getAndIncrement();
            long j3 = BufferedChannelKt.SEGMENT_SIZE;
            long j4 = andIncrement / j3;
            int i = (int) (andIncrement % j3);
            if (channelSegment2.id != j4) {
                channelSegment = this.findSegmentReceive(j4, channelSegment2);
                if (channelSegment == null) {
                    continue;
                }
            } else {
                channelSegment = channelSegment2;
            }
            BufferedChannel bufferedChannel = this;
            Object updateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, andIncrement, obj);
            channelSegment2 = channelSegment;
            if (updateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    waiter.invokeOnCancellation(channelSegment2, i);
                }
                bufferedChannel.waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(andIncrement);
                channelSegment2.onSlotCleaned();
                ChannelResult.Companion.getClass();
                return ChannelResult.failed;
            }
            if (updateCellReceive != BufferedChannelKt.FAILED) {
                if (updateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected");
                }
                channelSegment2.cleanPrev();
                ChannelResult.Companion.getClass();
                return updateCellReceive;
            }
            if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment2.cleanPrev();
            }
            this = bufferedChannel;
        }
        ChannelResult.Companion companion2 = ChannelResult.Companion;
        Throwable closeCause2 = this.getCloseCause();
        companion2.getClass();
        return ChannelResult.Companion.m3460closedJP2dKIU(closeCause2);
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
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, ChannelResult.m3457boximpl(obj2), function1 != null ? new BufferedChannel$bindCancellationFunResult$1(this) : null);
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
        int trySelectInternal = ((SelectImplementation) obj).trySelectInternal(this, Unit.INSTANCE);
        SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 selectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 = SelectKt.DUMMY_PROCESS_RESULT_FUNCTION;
        if (trySelectInternal == 0) {
            trySelectDetailedResult = TrySelectDetailedResult.SUCCESSFUL;
        } else if (trySelectInternal == 1) {
            trySelectDetailedResult = TrySelectDetailedResult.REREGISTER;
        } else if (trySelectInternal == 2) {
            trySelectDetailedResult = TrySelectDetailedResult.CANCELLED;
        } else {
            if (trySelectInternal != 3) {
                throw new IllegalStateException(("Unexpected internal result: " + trySelectInternal).toString());
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
    public Object mo3456trySendJP2dKIU(Object obj) {
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
            boolean isClosed = isClosed(andIncrement, false);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = i2;
            long j3 = j / j2;
            int i3 = (int) (j % j2);
            if (channelSegment2.id != j3) {
                ChannelSegment access$findSegmentSend = access$findSegmentSend(this, j3, channelSegment2);
                if (access$findSegmentSend != null) {
                    channelSegment = access$findSegmentSend;
                    obj2 = obj;
                    i = i3;
                    bufferedChannel = this;
                } else if (isClosed) {
                    break;
                }
            } else {
                channelSegment = channelSegment2;
                bufferedChannel = this;
                obj2 = obj;
                i = i3;
            }
            int access$updateCellSend = access$updateCellSend(bufferedChannel, channelSegment, i, obj2, j, obj3, isClosed);
            ChannelSegment channelSegment3 = channelSegment;
            if (access$updateCellSend == 0) {
                channelSegment3.cleanPrev();
                ChannelResult.Companion companion = ChannelResult.Companion;
                Unit unit = Unit.INSTANCE;
                companion.getClass();
                return unit;
            }
            if (access$updateCellSend == 1) {
                ChannelResult.Companion companion2 = ChannelResult.Companion;
                Unit unit2 = Unit.INSTANCE;
                companion2.getClass();
                return unit2;
            }
            if (access$updateCellSend != 2) {
                if (access$updateCellSend == 3) {
                    throw new IllegalStateException("unexpected");
                }
                if (access$updateCellSend != 4) {
                    if (access$updateCellSend == 5) {
                        channelSegment3.cleanPrev();
                    }
                    channelSegment2 = channelSegment3;
                } else if (j < this.receivers.value) {
                    channelSegment3.cleanPrev();
                }
            } else {
                if (!isClosed) {
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
        return ChannelResult.Companion.m3460closedJP2dKIU(sendException);
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
