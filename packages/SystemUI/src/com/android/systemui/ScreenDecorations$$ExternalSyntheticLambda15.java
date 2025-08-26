package com.android.systemui;

import com.android.systemui.decor.DecorProvider;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda15 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((DecorProvider) obj).getViewId();
    }
}
