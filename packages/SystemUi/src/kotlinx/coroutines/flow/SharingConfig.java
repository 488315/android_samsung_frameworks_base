package kotlinx.coroutines.flow;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.channels.BufferOverflow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
