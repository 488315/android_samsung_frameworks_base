package kotlinx.coroutines.channels;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object send(Object obj, Continuation continuation) {
        UndeliveredElementException callUndeliveredElementCatchingException;
        Object m3461trySendImplMj0NB7M = m3461trySendImplMj0NB7M(obj, true);
        if (!(m3461trySendImplMj0NB7M instanceof ChannelResult.Closed)) {
            return Unit.INSTANCE;
        }
        ChannelResult.m3458exceptionOrNullimpl((ChannelResult.Failed) m3461trySendImplMj0NB7M);
        Function1 function1 = this.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) == null) {
            throw getSendException();
        }
        ExceptionsKt__ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException, getSendException());
        throw callUndeliveredElementCatchingException;
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    /* renamed from: trySend-JP2dKIU */
    public final Object mo3456trySendJP2dKIU(Object obj) {
        return m3461trySendImplMj0NB7M(obj, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0061, code lost:
    
        r1 = kotlinx.coroutines.channels.ChannelResult.Companion;
        r0 = getSendException();
        r1.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006e, code lost:
    
        return kotlinx.coroutines.channels.ChannelResult.Companion.m3460closedJP2dKIU(r0);
     */
    /* renamed from: trySendImpl-Mj0NB7M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m3461trySendImplMj0NB7M(java.lang.Object r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ConflatedBufferedChannel.m3461trySendImplMj0NB7M(java.lang.Object, boolean):java.lang.Object");
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
