package com.android.internal.app;

import android.util.ArrayMap;
import android.util.SparseArray;

/* loaded from: classes5.dex */
public class ProcessMap<E> {
    final ArrayMap<String, SparseArray<E>> mMap = new ArrayMap<>();

    public E get(String str, int i) {
        SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public SparseArray<E> get(String str) {
        return this.mMap.get(str);
    }

    public E put(String str, int i, E e) {
        SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>(2);
            this.mMap.put(str, sparseArray);
        }
        sparseArray.put(i, e);
        return e;
    }

    public E remove(String str, int i) {
        SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        E removeReturnOld = sparseArray.removeReturnOld(i);
        if (sparseArray.size() == 0) {
            this.mMap.remove(str);
        }
        return removeReturnOld;
    }

    public ArrayMap<String, SparseArray<E>> getMap() {
        return this.mMap;
    }

    public int size() {
        return this.mMap.size();
    }

    public void clear() {
        this.mMap.clear();
    }

    public void putAll(ProcessMap<E> processMap) {
        this.mMap.putAll((ArrayMap<? extends String, ? extends SparseArray<E>>) processMap.mMap);
    }
}
