package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SendingCollector implements FlowCollector {
    public final SendChannel channel;

    public SendingCollector(SendChannel sendChannel) {
        this.channel = sendChannel;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        Object send = this.channel.send(obj, continuation);
        return send == CoroutineSingletons.COROUTINE_SUSPENDED ? send : Unit.INSTANCE;
    }
}
