package com.android.server.people.prediction;

import android.util.Pair;
import android.util.Range;

import java.util.function.ToLongFunction;

public final /* synthetic */ class SharesheetModelScorer$$ExternalSyntheticLambda1
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((Long) ((Range) ((Pair) obj).second).getUpper()).longValue();
    }
}
