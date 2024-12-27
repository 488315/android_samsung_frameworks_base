package com.android.server.stats.pull;

import java.util.Arrays;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda10 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = StatsPullAtomService.RANDOM_SEED;
        return Arrays.stream((int[]) obj).anyMatch(new StatsPullAtomService$$ExternalSyntheticLambda20(1));
    }
}
