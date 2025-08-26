package com.android.launcher3.icons;

import android.content.res.Resources;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public final /* synthetic */ class ClockDrawableWrapper$$ExternalSyntheticLambda0 implements IntFunction {
    public final /* synthetic */ Resources f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ClockDrawableWrapper$$ExternalSyntheticLambda0(Resources resources, int i) {
        this.f$0 = resources;
        this.f$1 = i;
    }

    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        Resources resources = this.f$0;
        int i2 = this.f$1;
        int i3 = ClockDrawableWrapper.$r8$clinit;
        return resources.getDrawableForDensity(i, i2);
    }
}
