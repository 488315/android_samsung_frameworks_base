package com.android.internal.infra;

import android.util.SparseArray;

import com.android.internal.util.Preconditions;

public abstract class PerUser<T> extends SparseArray<T> {
    protected abstract T create(int i);

    public T forUser(int userId) {
        return get(userId);
    }

    @Override // android.util.SparseArray
    public T get(int i) {
        T t = (T) super.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) Preconditions.checkNotNull(create(i));
        put(i, t2);
        return t2;
    }
}
