package androidx.core.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Pools$SynchronizedPool extends Pools$SimplePool {
    public final Object mLock;

    public Pools$SynchronizedPool(int i) {
        super(i);
        this.mLock = new Object();
    }

    @Override // androidx.core.util.Pools$SimplePool
    public final Object acquire() {
        Object acquire;
        synchronized (this.mLock) {
            acquire = super.acquire();
        }
        return acquire;
    }

    @Override // androidx.core.util.Pools$SimplePool
    public final boolean release(Object obj) {
        boolean release;
        synchronized (this.mLock) {
            release = super.release(obj);
        }
        return release;
    }
}
