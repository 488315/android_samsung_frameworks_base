package kotlinx.coroutines.channels;

import androidx.room.coroutines.Pool$$ExternalSyntheticLambda0;
import kotlinx.coroutines.channels.Channel;

/* loaded from: classes4.dex */
public abstract class ChannelKt {
    public static BufferedChannel Channel$default(int i, BufferOverflow bufferOverflow, Pool$$ExternalSyntheticLambda0 pool$$ExternalSyntheticLambda0, int i2) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        if ((i2 & 4) != 0) {
            pool$$ExternalSyntheticLambda0 = null;
        }
        if (i == -2) {
            if (bufferOverflow != BufferOverflow.SUSPEND) {
                return new ConflatedBufferedChannel(1, bufferOverflow, pool$$ExternalSyntheticLambda0);
            }
            Channel.Factory.getClass();
            return new BufferedChannel(Channel.Factory.CHANNEL_DEFAULT_CAPACITY, pool$$ExternalSyntheticLambda0);
        }
        if (i != -1) {
            return i != 0 ? i != Integer.MAX_VALUE ? bufferOverflow == BufferOverflow.SUSPEND ? new BufferedChannel(i, pool$$ExternalSyntheticLambda0) : new ConflatedBufferedChannel(i, bufferOverflow, pool$$ExternalSyntheticLambda0) : new BufferedChannel(Integer.MAX_VALUE, pool$$ExternalSyntheticLambda0) : bufferOverflow == BufferOverflow.SUSPEND ? new BufferedChannel(0, pool$$ExternalSyntheticLambda0) : new ConflatedBufferedChannel(1, bufferOverflow, pool$$ExternalSyntheticLambda0);
        }
        if (bufferOverflow == BufferOverflow.SUSPEND) {
            return new ConflatedBufferedChannel(1, BufferOverflow.DROP_OLDEST, pool$$ExternalSyntheticLambda0);
        }
        throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow");
    }
}
