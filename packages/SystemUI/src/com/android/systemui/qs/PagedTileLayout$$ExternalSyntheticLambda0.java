package com.android.systemui.qs;

import android.view.animation.Interpolator;

public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda0 implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }
}
