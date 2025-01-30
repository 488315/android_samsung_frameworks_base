package kotlinx.coroutines.internal;

import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AtomicOp extends OpDescriptor {
    public final AtomicRef _consensus = AtomicFU.atomic(AtomicKt.NO_DECISION);

    public abstract void complete(Object obj, Object obj2);

    public final Object decide(Object obj) {
        Object obj2 = this._consensus.value;
        Symbol symbol = AtomicKt.NO_DECISION;
        return obj2 != symbol ? obj2 : this._consensus.compareAndSet(symbol, obj) ? obj : this._consensus.value;
    }

    public long getOpSequence() {
        return 0L;
    }

    @Override // kotlinx.coroutines.internal.OpDescriptor
    public final Object perform(Object obj) {
        Object obj2 = this._consensus.value;
        if (obj2 == AtomicKt.NO_DECISION) {
            obj2 = decide(prepare(obj));
        }
        complete(obj, obj2);
        return obj2;
    }

    public abstract Object prepare(Object obj);

    @Override // kotlinx.coroutines.internal.OpDescriptor
    public final AtomicOp getAtomicOp() {
        return this;
    }
}
