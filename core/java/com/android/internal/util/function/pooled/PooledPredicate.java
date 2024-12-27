package com.android.internal.util.function.pooled;

import java.util.function.Predicate;

public interface PooledPredicate<T> extends PooledLambda, Predicate<T> {
    @Override // com.android.internal.util.function.pooled.PooledLambda,
              // com.android.internal.util.function.pooled.PooledSupplier,
              // com.android.internal.util.function.pooled.PooledRunnable,
              // com.android.internal.util.function.pooled.PooledSupplier.OfInt,
              // com.android.internal.util.function.pooled.PooledSupplier.OfLong,
              // com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    PooledPredicate<T> recycleOnUse();
}
