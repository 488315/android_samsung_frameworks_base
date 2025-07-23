package com.android.tradeinmode.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ENABLE_TRADE_IN_MODE, Flags.FLAG_TRADE_IN_MODE_2025Q4, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.tradeinmode.flags.FeatureFlags
    public boolean enableTradeInMode() {
        return getValue(Flags.FLAG_ENABLE_TRADE_IN_MODE, new Predicate() { // from class: com.android.tradeinmode.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTradeInMode();
            }
        });
    }

    @Override // com.android.tradeinmode.flags.FeatureFlags
    public boolean tradeInMode2025q4() {
        return getValue(Flags.FLAG_TRADE_IN_MODE_2025Q4, new Predicate() { // from class: com.android.tradeinmode.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tradeInMode2025q4();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ENABLE_TRADE_IN_MODE, Flags.FLAG_TRADE_IN_MODE_2025Q4);
    }
}
