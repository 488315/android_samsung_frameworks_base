package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class OpDescriptor {
    public abstract AtomicOp getAtomicOp();

    public final boolean isEarlierThan(OpDescriptor opDescriptor) {
        AtomicOp atomicOp;
        AtomicOp atomicOp2 = getAtomicOp();
        return (atomicOp2 == null || (atomicOp = opDescriptor.getAtomicOp()) == null || atomicOp2.getOpSequence() >= atomicOp.getOpSequence()) ? false : true;
    }

    public abstract Object perform(Object obj);

    public String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(DebugStringsKt.getClassSimpleName(this), "@", DebugStringsKt.getHexAddress(this));
    }
}
