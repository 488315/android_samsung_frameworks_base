package kotlinx.coroutines.channels;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* loaded from: classes4.dex */
public class ConflatedBufferedChannel extends BufferedChannel {
    public final BufferOverflow onBufferOverflow;

    public /* synthetic */ ConflatedBufferedChannel(int i, BufferOverflow bufferOverflow, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, bufferOverflow, (i2 & 4) != 0 ? null : function1);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public final boolean isConflatedDropOldest() {
        return this.onBufferOverflow == BufferOverflow.DROP_OLDEST;
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    public final Object send(Object obj, Continuation continuation) throws Throwable {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException;
        Object objM3481trySendImplMj0NB7M = m3481trySendImplMj0NB7M(obj, true);
        if (!(objM3481trySendImplMj0NB7M instanceof ChannelResult.Closed)) {
            return Unit.INSTANCE;
        }
        ChannelResult.m3478exceptionOrNullimpl((ChannelResult.Failed) objM3481trySendImplMj0NB7M);
        Function1 function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) == null) {
            throw getSendException();
        }
        ExceptionsKt__ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException, getSendException());
        throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    /* renamed from: trySend-JP2dKIU */
    public final Object mo3476trySendJP2dKIU(Object obj) {
        return m3481trySendImplMj0NB7M(obj, false);
    }

    /* renamed from: trySendImpl-Mj0NB7M, reason: not valid java name */
    public final Object m3481trySendImplMj0NB7M(Object obj, boolean z) {
        Function1 function1;
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException;
        if (this.onBufferOverflow == BufferOverflow.DROP_LATEST) {
            Object objMo3476trySendJP2dKIU = super.mo3476trySendJP2dKIU(obj);
            ChannelResult.Companion companion = ChannelResult.Companion;
            if (!(objMo3476trySendJP2dKIU instanceof ChannelResult.Failed) || (objMo3476trySendJP2dKIU instanceof ChannelResult.Closed)) {
                return objMo3476trySendJP2dKIU;
            }
            if (z && (function1 = this.onUndeliveredElement) != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) != null) {
                throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
            }
            ChannelResult.Companion companion2 = ChannelResult.Companion;
            Unit unit = Unit.INSTANCE;
            companion2.getClass();
            return unit;
        }
        Object obj2 = obj;
        Object obj3 = BufferedChannelKt.BUFFERED;
        ChannelSegment channelSegment = (ChannelSegment) this.sendSegment.value;
        while (true) {
            long andIncrement = this.sendersAndCloseStatus.getAndIncrement();
            long j = 1152921504606846975L & andIncrement;
            boolean zIsClosed = isClosed(andIncrement, false);
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = i;
            long j3 = j / j2;
            int i2 = (int) (j % j2);
            if (channelSegment.id != j3) {
                ChannelSegment channelSegmentAccess$findSegmentSend = BufferedChannel.access$findSegmentSend(this, j3, channelSegment);
                if (channelSegmentAccess$findSegmentSend != null) {
                    channelSegment = channelSegmentAccess$findSegmentSend;
                } else if (zIsClosed) {
                    break;
                }
            }
            int iAccess$updateCellSend = BufferedChannel.access$updateCellSend(this, channelSegment, i2, obj2, j, obj3, zIsClosed);
            if (iAccess$updateCellSend == 0) {
                channelSegment.cleanPrev();
                ChannelResult.Companion companion3 = ChannelResult.Companion;
                Unit unit2 = Unit.INSTANCE;
                companion3.getClass();
                return unit2;
            }
            if (iAccess$updateCellSend == 1) {
                ChannelResult.Companion companion4 = ChannelResult.Companion;
                Unit unit3 = Unit.INSTANCE;
                companion4.getClass();
                return unit3;
            }
            if (iAccess$updateCellSend != 2) {
                if (iAccess$updateCellSend == 3) {
                    throw new IllegalStateException("unexpected");
                }
                if (iAccess$updateCellSend != 4) {
                    if (iAccess$updateCellSend == 5) {
                        channelSegment.cleanPrev();
                    }
                    obj2 = obj;
                } else if (j < this.receivers.value) {
                    channelSegment.cleanPrev();
                }
            } else {
                if (!zIsClosed) {
                    Waiter waiter = obj3 instanceof Waiter ? (Waiter) obj3 : null;
                    if (waiter != null) {
                        waiter.invokeOnCancellation(channelSegment, i2 + i);
                    }
                    dropFirstElementUntilTheSpecifiedCellIsInTheBuffer((channelSegment.id * j2) + i2);
                    ChannelResult.Companion companion5 = ChannelResult.Companion;
                    Unit unit4 = Unit.INSTANCE;
                    companion5.getClass();
                    return unit4;
                }
                channelSegment.onSlotCleaned();
            }
        }
        ChannelResult.Companion companion6 = ChannelResult.Companion;
        Throwable sendException = getSendException();
        companion6.getClass();
        return ChannelResult.Companion.m3480closedJP2dKIU(sendException);
    }

    public ConflatedBufferedChannel(int i, BufferOverflow bufferOverflow, Function1 function1) {
        super(i, function1);
        this.onBufferOverflow = bufferOverflow;
        if (bufferOverflow == BufferOverflow.SUSPEND) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("This implementation does not support suspension for senders, use ", Reflection.getOrCreateKotlinClass(BufferedChannel.class).getSimpleName(), " instead").toString());
        }
        if (i < 1) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Buffered channel capacity must be at least 1, but ", " was specified").toString());
        }
    }
}
