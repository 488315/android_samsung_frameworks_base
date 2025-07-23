package com.android.internal.widget;

import android.util.Log;
import android.util.Pools;
import android.view.View;

/* loaded from: classes6.dex */
public class MessagingPool<T extends View> implements Pools.Pool<T> {
    private static final boolean ENABLED = false;
    private static final String TAG = "MessagingPool";
    private Pools.SynchronizedPool<T> mCurrentPool;
    private final int mMaxPoolSize;

    @Override // android.util.Pools.Pool
    public T acquire() {
        return null;
    }

    public void clear() {
    }

    public MessagingPool(int i) {
        this.mMaxPoolSize = i;
    }

    @Override // android.util.Pools.Pool
    public boolean release(T t) {
        if (t.getParent() != null) {
            Log.wtf(TAG, "releasing " + t + " with parent " + t.getParent());
        }
        return false;
    }
}
