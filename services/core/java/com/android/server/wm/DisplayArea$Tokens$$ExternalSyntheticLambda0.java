package com.android.server.wm;

import java.util.function.ToIntFunction;

public final /* synthetic */ class DisplayArea$Tokens$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((WindowToken) obj).getWindowLayerFromType();
    }
}
