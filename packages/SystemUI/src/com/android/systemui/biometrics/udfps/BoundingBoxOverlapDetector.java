package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* loaded from: classes.dex */
public final class BoundingBoxOverlapDetector implements OverlapDetector {
    public final float targetSize;

    public BoundingBoxOverlapDetector(float f) {
        this.targetSize = f;
    }

    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public final boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2) {
        float fWidth = (rect.width() / 2) * this.targetSize;
        return normalizedTouchData.isWithinBounds(new Rect((int) (rect.centerX() - fWidth), (int) (rect.centerY() - fWidth), (int) (rect.centerX() + fWidth), (int) (rect.centerY() + fWidth)));
    }
}
