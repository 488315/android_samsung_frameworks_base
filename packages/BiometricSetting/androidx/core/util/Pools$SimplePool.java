package androidx.core.util;

/* loaded from: classes.dex */
public final class Pools$SimplePool<T> {
    private final Object[] mPool;
    private int mPoolSize;

    public Pools$SimplePool(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.mPool = new Object[i];
    }

    public final T acquire() {
        int i = this.mPoolSize;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.mPool;
        T t = (T) objArr[i2];
        objArr[i2] = null;
        this.mPoolSize = i - 1;
        return t;
    }

    public final void release(Object obj) {
        int i;
        Object[] objArr;
        boolean z = false;
        int i2 = 0;
        while (true) {
            i = this.mPoolSize;
            objArr = this.mPool;
            if (i2 >= i) {
                break;
            }
            if (objArr[i2] == obj) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            throw new IllegalStateException("Already in the pool!");
        }
        if (i < objArr.length) {
            objArr[i] = obj;
            this.mPoolSize = i + 1;
        }
    }
}
