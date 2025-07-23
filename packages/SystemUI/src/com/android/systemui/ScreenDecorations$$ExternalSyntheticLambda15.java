package com.android.systemui;

import com.android.systemui.decor.DecorProvider;
import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda15 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((DecorProvider) obj).getViewId();
    }
}
