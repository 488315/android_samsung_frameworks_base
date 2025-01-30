package kotlinx.coroutines.flow;

import android.support.v4.media.AbstractC0000x2c234b15;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SharedFlowKt {
    public static final Symbol NO_VALUE = new Symbol("NO_VALUE");

    public static final SharedFlowImpl MutableSharedFlow(int i, int i2, BufferOverflow bufferOverflow) {
        boolean z = true;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("replay cannot be negative, but was ", i).toString());
        }
        if (!(i2 >= 0)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("extraBufferCapacity cannot be negative, but was ", i2).toString());
        }
        if (i <= 0 && i2 <= 0 && bufferOverflow != BufferOverflow.SUSPEND) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(("replay or extraBufferCapacity must be positive with non-default onBufferOverflow strategy " + bufferOverflow).toString());
        }
        int i3 = i2 + i;
        if (i3 < 0) {
            i3 = Integer.MAX_VALUE;
        }
        return new SharedFlowImpl(i, i3, bufferOverflow);
    }

    public static /* synthetic */ SharedFlowImpl MutableSharedFlow$default(int i, int i2, BufferOverflow bufferOverflow, int i3) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        return MutableSharedFlow(i, i2, bufferOverflow);
    }
}
