package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
