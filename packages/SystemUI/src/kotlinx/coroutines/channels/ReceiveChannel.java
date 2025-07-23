package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.selects.SelectClause1Impl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ReceiveChannel {
    void cancel(CancellationException cancellationException);

    SelectClause1Impl getOnReceiveCatching();

    BufferedChannel.BufferedChannelIterator iterator();

    Object receive(Continuation continuation);

    /* renamed from: receiveCatching-JP2dKIU */
    Object mo3453receiveCatchingJP2dKIU(Continuation continuation);

    /* renamed from: tryReceive-PtdJZtk */
    Object mo3455tryReceivePtdJZtk();
}
