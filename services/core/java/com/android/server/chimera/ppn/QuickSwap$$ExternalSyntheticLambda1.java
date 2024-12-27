package com.android.server.chimera.ppn;

import android.util.Pair;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class QuickSwap$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Pair pair = (Pair) obj;
        Pair pair2 = (Pair) obj2;
        List list = QuickSwap.QUICKSWAP_BLOCKLIST;
        return ((Long) pair2.second).longValue() - ((Long) pair.second).longValue() == 0 ? ((Integer) pair2.first).intValue() > ((Integer) pair.first).intValue() ? -1 : 1 : (int) (((Long) pair2.second).longValue() - ((Long) pair.second).longValue());
    }
}
