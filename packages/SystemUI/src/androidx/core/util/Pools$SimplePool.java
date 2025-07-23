package androidx.core.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Pools$SimplePool {
    public final Object[] pool;
    public int poolSize;

    public Pools$SimplePool(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.pool = new Object[i];
    }

    public Object acquire() {
        int i = this.poolSize;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.pool;
        Object obj = objArr[i2];
        objArr[i2] = null;
        this.poolSize = i - 1;
        return obj;
    }

    public boolean release(Object obj) {
        Object[] objArr;
        boolean z;
        int i = this.poolSize;
        int i2 = 0;
        while (true) {
            objArr = this.pool;
            if (i2 >= i) {
                z = false;
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
        int i3 = this.poolSize;
        if (i3 >= objArr.length) {
            return false;
        }
        objArr[i3] = obj;
        this.poolSize = i3 + 1;
        return true;
    }
}
