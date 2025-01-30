package kotlinx.coroutines.internal;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AtomicDesc {
    public AtomicOp atomicOp;

    public abstract void complete(AtomicOp atomicOp, Object obj);

    public abstract Object prepare(AtomicOp atomicOp);
}
