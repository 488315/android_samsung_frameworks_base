package com.android.internal.util.function.pooled;

import android.os.TraceNameSupplier;
import com.android.internal.util.FunctionalUtils;

/* loaded from: classes5.dex */
public interface PooledRunnable
    extends PooledLambda, Runnable, FunctionalUtils.ThrowingRunnable, TraceNameSupplier {
  @Override // com.android.internal.util.function.pooled.PooledSupplier.OfInt,
            // com.android.internal.util.function.pooled.PooledSupplier.OfLong,
            // com.android.internal.util.function.pooled.PooledSupplier.OfDouble
  PooledRunnable recycleOnUse();
}
