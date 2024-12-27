package com.android.server.chimera.ppn;

import android.util.Pair;

import java.util.Comparator;
import java.util.List;

public final /* synthetic */ class QuickSwap$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Pair pair = (Pair) obj;
        Pair pair2 = (Pair) obj2;
        List list = QuickSwap.QUICKSWAP_BLOCKLIST;
        return ((Long) pair2.second).longValue() - ((Long) pair.second).longValue() == 0
                ? ((Integer) pair2.first).intValue() > ((Integer) pair.first).intValue() ? -1 : 1
                : (int) (((Long) pair2.second).longValue() - ((Long) pair.second).longValue());
    }
}
