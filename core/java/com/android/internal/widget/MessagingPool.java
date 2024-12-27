package com.android.internal.widget;

import android.util.Log;
import android.util.Pools;
import android.view.View;

/* loaded from: classes5.dex */
public class MessagingPool<T extends View> implements Pools.Pool<T> {
    private static final boolean ENABLED = false;
    private static final String TAG = "MessagingPool";
    private Pools.SynchronizedPool<T> mCurrentPool;
    private final int mMaxPoolSize;

    public MessagingPool(int maxPoolSize) {
        this.mMaxPoolSize = maxPoolSize;
    }

    @Override // android.util.Pools.Pool
    public T acquire() {
        return null;
    }

    @Override // android.util.Pools.Pool
    public boolean release(T instance) {
        if (instance.getParent() == null) {
            return false;
        }
        Log.wtf(TAG, "releasing " + instance + " with parent " + instance.getParent());
        return false;
    }

    public void clear() {
    }
}
