package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public interface PooledSupplier<T> extends PooledLambda, Supplier<T>, FunctionalUtils.ThrowingSupplier<T> {

    public interface OfDouble extends DoubleSupplier, PooledLambda {
        @Override // 
        OfDouble recycleOnUse();
    }

    public interface OfInt extends IntSupplier, PooledLambda {
        @Override // com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
        OfInt recycleOnUse();
    }

    public interface OfLong extends LongSupplier, PooledLambda {
        @Override // com.android.internal.util.function.pooled.PooledSupplier.OfDouble
        OfLong recycleOnUse();
    }

    PooledRunnable asRunnable();

    @Override // com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    PooledSupplier<T> recycleOnUse();
}
