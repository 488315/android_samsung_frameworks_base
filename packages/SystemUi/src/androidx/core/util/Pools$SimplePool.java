package androidx.core.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public Object acquire() {
        int i = this.mPoolSize;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.mPool;
        Object obj = objArr[i2];
        objArr[i2] = null;
        this.mPoolSize = i - 1;
        return obj;
    }

    public boolean release(Object obj) {
        int i;
        Object[] objArr;
        boolean z;
        int i2 = 0;
        while (true) {
            i = this.mPoolSize;
            objArr = this.mPool;
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
        if (i >= objArr.length) {
            return false;
        }
        objArr[i] = obj;
        this.mPoolSize = i + 1;
        return true;
    }
}
