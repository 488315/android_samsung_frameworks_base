package com.android.server.am;

import android.util.Pair;

import java.util.Comparator;

public final /* synthetic */ class ProcessList$$ExternalSyntheticLambda3 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(
                ((ProcessRecord) ((Pair) obj).first).uid,
                ((ProcessRecord) ((Pair) obj2).first).uid);
    }
}
