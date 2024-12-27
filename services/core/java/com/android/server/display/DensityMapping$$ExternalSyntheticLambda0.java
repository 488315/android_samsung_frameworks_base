package com.android.server.display;

import java.util.function.ToIntFunction;

public final /* synthetic */ class DensityMapping$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((DensityMapping.Entry) obj).squaredDiagonal;
    }
}
