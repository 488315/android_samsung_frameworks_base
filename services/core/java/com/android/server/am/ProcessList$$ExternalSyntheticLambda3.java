package com.android.server.am;

import android.util.Pair;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ProcessList$$ExternalSyntheticLambda3 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(
                ((ProcessRecord) ((Pair) obj).first).uid,
                ((ProcessRecord) ((Pair) obj2).first).uid);
    }
}
