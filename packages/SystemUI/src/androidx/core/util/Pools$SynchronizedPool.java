package androidx.core.util;

/* loaded from: classes.dex */
public class Pools$SynchronizedPool extends Pools$SimplePool {
    public final Object lock;

    public Pools$SynchronizedPool(int i) {
        super(i);
        this.lock = new Object();
    }

    @Override // androidx.core.util.Pools$SimplePool
    public final Object acquire() {
        Object objAcquire;
        synchronized (this.lock) {
            objAcquire = super.acquire();
        }
        return objAcquire;
    }

    @Override // androidx.core.util.Pools$SimplePool
    public final boolean release(Object obj) {
        boolean zRelease;
        synchronized (this.lock) {
            zRelease = super.release(obj);
        }
        return zRelease;
    }
}
