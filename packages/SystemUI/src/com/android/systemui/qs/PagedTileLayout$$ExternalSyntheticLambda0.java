package com.android.systemui.qs;

import android.view.animation.Interpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda0 implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }
}
