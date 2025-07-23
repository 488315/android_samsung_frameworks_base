package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BoundingBoxOverlapDetector implements OverlapDetector {
    public final float targetSize;

    public BoundingBoxOverlapDetector(float f) {
        this.targetSize = f;
    }

    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public final boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2) {
        float width = (rect.width() / 2) * this.targetSize;
        return normalizedTouchData.isWithinBounds(new Rect((int) (rect.centerX() - width), (int) (rect.centerY() - width), (int) (rect.centerX() + width), (int) (rect.centerY() + width)));
    }
}
