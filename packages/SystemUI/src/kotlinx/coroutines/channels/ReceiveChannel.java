package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.selects.SelectClause1Impl;

/* loaded from: classes4.dex */
public interface ReceiveChannel {
    void cancel(CancellationException cancellationException);

    SelectClause1Impl getOnReceiveCatching();

    BufferedChannel.BufferedChannelIterator iterator();

    Object receive(Continuation continuation);

    /* renamed from: receiveCatching-JP2dKIU */
    Object mo3472receiveCatchingJP2dKIU(Continuation continuation);

    /* renamed from: tryReceive-PtdJZtk */
    Object mo3474tryReceivePtdJZtk();
}
