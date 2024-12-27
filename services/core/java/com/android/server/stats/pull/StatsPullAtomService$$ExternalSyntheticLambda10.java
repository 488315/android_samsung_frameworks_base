package com.android.server.stats.pull;

import java.util.Arrays;
import java.util.function.Predicate;

public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda10
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = StatsPullAtomService.RANDOM_SEED;
        return Arrays.stream((int[]) obj)
                .anyMatch(new StatsPullAtomService$$ExternalSyntheticLambda20(1));
    }
}
