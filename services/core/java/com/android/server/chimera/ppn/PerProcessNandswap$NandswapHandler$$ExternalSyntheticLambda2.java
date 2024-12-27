package com.android.server.chimera.ppn;

import android.util.Pair;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda2 implements Comparator {
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
