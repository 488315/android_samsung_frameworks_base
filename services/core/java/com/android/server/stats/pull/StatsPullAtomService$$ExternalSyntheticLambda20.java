package com.android.server.stats.pull;

import java.util.function.IntPredicate;

public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda20
        implements IntPredicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        switch (this.$r8$classId) {
            case 0:
                int i2 = StatsPullAtomService.RANDOM_SEED;
                if (i == 58) {
                    break;
                }
                break;
            default:
                int i3 = StatsPullAtomService.RANDOM_SEED;
                if (i == 1) {
                    break;
                }
                break;
        }
        return true;
    }
}
