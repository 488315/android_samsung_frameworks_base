package androidx.datastore.core;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SimpleActor {
    public final Function2 consumeMessage;
    public final BufferedChannel messageQueue = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
    public final AtomicInt remainingMessages = new AtomicInt(0);
    public final CoroutineScope scope;

    public SimpleActor(CoroutineScope coroutineScope, final Function1 function1, final Function2 function2, Function2 function22) {
        this.scope = coroutineScope;
        this.consumeMessage = function22;
        Job job = (Job) coroutineScope.getCoroutineContext().get(Job.Key);
        if (job != null) {
            job.invokeOnCompletion(new Function1() { // from class: androidx.datastore.core.SimpleActor$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Throwable th = (Throwable) obj;
                    Function1.this.mo779invoke(th);
                    BufferedChannel bufferedChannel = this.messageQueue;
                    bufferedChannel.closeOrCancelImpl(th, false);
                    while (true) {
                        Object m3459getOrNullimpl = ChannelResult.m3459getOrNullimpl(bufferedChannel.mo3455tryReceivePtdJZtk());
                        if (m3459getOrNullimpl == null) {
                            return Unit.INSTANCE;
                        }
                        function2.invoke(m3459getOrNullimpl, th);
                    }
                }
            });
        }
    }
}
