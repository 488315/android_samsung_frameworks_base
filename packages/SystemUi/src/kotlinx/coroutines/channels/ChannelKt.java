package kotlinx.coroutines.channels;

import kotlinx.coroutines.channels.Channel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ChannelKt {
    public static AbstractChannel Channel$default(int i, BufferOverflow bufferOverflow, int i2) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        int i3 = 1;
        if (i == -2) {
            if (bufferOverflow == BufferOverflow.SUSPEND) {
                Channel.Factory.getClass();
                i3 = Channel.Factory.CHANNEL_DEFAULT_CAPACITY;
            }
            return new ArrayChannel(i3, bufferOverflow, null);
        }
        if (i != -1) {
            return i != 0 ? i != Integer.MAX_VALUE ? (i == 1 && bufferOverflow == BufferOverflow.DROP_OLDEST) ? new ConflatedChannel(null) : new ArrayChannel(i, bufferOverflow, null) : new LinkedListChannel(null) : bufferOverflow == BufferOverflow.SUSPEND ? new RendezvousChannel(null) : new ArrayChannel(1, bufferOverflow, null);
        }
        if (bufferOverflow == BufferOverflow.SUSPEND) {
            return new ConflatedChannel(null);
        }
        throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
    }
}
