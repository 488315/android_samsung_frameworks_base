package androidx.constraintlayout.core;

/* loaded from: classes.dex */
public class Pools$SimplePool {
    public final Object[] mPool;
    public int mPoolSize;

    public Pools$SimplePool(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.mPool = new Object[i];
    }

    public final void release(ArrayRow arrayRow) {
        int i = this.mPoolSize;
        Object[] objArr = this.mPool;
        if (i < objArr.length) {
            objArr[i] = arrayRow;
            this.mPoolSize = i + 1;
        }
    }
}
