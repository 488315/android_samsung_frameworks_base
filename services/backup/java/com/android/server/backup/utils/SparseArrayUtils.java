package com.android.server.backup.utils;

import android.util.SparseArray;
import java.util.HashSet;

/* loaded from: classes.dex */
public abstract class SparseArrayUtils {
    public static HashSet union(SparseArray sparseArray) {
        HashSet hashSet = new HashSet();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            HashSet hashSet2 = (HashSet) sparseArray.valueAt(i);
            if (hashSet2 != null) {
                hashSet.addAll(hashSet2);
            }
        }
        return hashSet;
    }
}
