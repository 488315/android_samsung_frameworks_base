package androidx.constraintlayout.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
