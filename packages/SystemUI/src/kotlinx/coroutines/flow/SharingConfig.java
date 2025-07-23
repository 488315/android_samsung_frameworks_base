package kotlinx.coroutines.flow;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.channels.BufferOverflow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SharingConfig {
    public final CoroutineContext context;
    public final int extraBufferCapacity;
    public final BufferOverflow onBufferOverflow;
    public final Flow upstream;

    public SharingConfig(Flow flow, int i, BufferOverflow bufferOverflow, CoroutineContext coroutineContext) {
        this.upstream = flow;
        this.extraBufferCapacity = i;
        this.onBufferOverflow = bufferOverflow;
        this.context = coroutineContext;
    }
}
