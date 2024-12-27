package com.android.systemui;

import com.android.systemui.decor.DecorProvider;
import java.util.function.ToIntFunction;

public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda11 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((DecorProvider) obj).getViewId();
    }
}
