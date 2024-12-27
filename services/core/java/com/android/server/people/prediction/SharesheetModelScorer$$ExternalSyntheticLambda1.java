package com.android.server.people.prediction;

import android.util.Pair;
import android.util.Range;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SharesheetModelScorer$$ExternalSyntheticLambda1
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((Long) ((Range) ((Pair) obj).second).getUpper()).longValue();
    }
}
