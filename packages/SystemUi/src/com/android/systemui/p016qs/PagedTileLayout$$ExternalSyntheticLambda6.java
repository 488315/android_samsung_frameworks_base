package com.android.systemui.p016qs;

import android.view.animation.Interpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PagedTileLayout$$ExternalSyntheticLambda6 implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        PagedTileLayout$$ExternalSyntheticLambda6 pagedTileLayout$$ExternalSyntheticLambda6 = PagedTileLayout.SCROLL_CUBIC;
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }
}
