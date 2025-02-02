package androidx.constraintlayout.core;

/* loaded from: classes.dex */
final class Pools$SimplePool<T> {
    private final Object[] mPool = new Object[256];
    private int mPoolSize;

    Pools$SimplePool() {
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
        int i = this.mPoolSize;
        Object[] objArr = this.mPool;
        if (i < objArr.length) {
            objArr[i] = obj;
            this.mPoolSize = i + 1;
        }
    }

    public final void releaseAll(int i, Object[] objArr) {
        if (i > objArr.length) {
            i = objArr.length;
        }
        for (int i2 = 0; i2 < i; i2++) {
            Object obj = objArr[i2];
            int i3 = this.mPoolSize;
            Object[] objArr2 = this.mPool;
            if (i3 < objArr2.length) {
                objArr2[i3] = obj;
                this.mPoolSize = i3 + 1;
            }
        }
    }
}
