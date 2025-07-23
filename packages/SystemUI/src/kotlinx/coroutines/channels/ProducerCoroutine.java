package kotlinx.coroutines.channels;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ProducerCoroutine extends ChannelCoroutine implements ProducerScope {
    public ProducerCoroutine(CoroutineContext coroutineContext, Channel channel) {
        super(coroutineContext, channel, true, true);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public final void onCancelled(Throwable th, boolean z) {
        if (this._channel.close(th) || z) {
            return;
        }
        CoroutineExceptionHandlerKt.handleCoroutineException(th, this.context);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public final void onCompleted(Object obj) {
        this._channel.close(null);
    }
}
