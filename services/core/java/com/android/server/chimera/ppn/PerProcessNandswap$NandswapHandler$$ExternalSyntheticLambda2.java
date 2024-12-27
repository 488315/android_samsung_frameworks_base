package com.android.server.chimera.ppn;

import android.util.Pair;

import java.util.Comparator;

public final /* synthetic */ class PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda2
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Pair pair = (Pair) obj;
        Pair pair2 = (Pair) obj2;
        if (((Integer) pair2.second).intValue() - ((Integer) pair.second).intValue() == 0) {
            return 1;
        }
        return ((Integer) pair2.second).intValue() - ((Integer) pair.second).intValue();
    }
}
