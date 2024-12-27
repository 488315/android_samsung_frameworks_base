package com.android.systemui.qs;

import android.view.animation.Interpolator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda0 implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }
}
