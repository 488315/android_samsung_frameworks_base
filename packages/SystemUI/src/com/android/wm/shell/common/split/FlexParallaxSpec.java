package com.android.wm.shell.common.split;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public class FlexParallaxSpec implements ParallaxSpec {
    public FlexParallaxSpec() {
        new Rect();
    }

    @Override // com.android.wm.shell.common.split.ParallaxSpec
    public final float getDimValue(int i, DividerSnapAlgorithm dividerSnapAlgorithm) {
        return 0.32f;
    }

    @Override // com.android.wm.shell.common.split.ParallaxSpec
    public final int getDimmingSide(int i, DividerSnapAlgorithm dividerSnapAlgorithm, boolean z) {
        int i2 = dividerSnapAlgorithm.mMiddleTarget.position;
        if (i < i2) {
            return z ? 1 : 2;
        }
        if (i > i2) {
            return z ? 3 : 4;
        }
        return -1;
    }
}
