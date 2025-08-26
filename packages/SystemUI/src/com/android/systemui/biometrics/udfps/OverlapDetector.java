package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* loaded from: classes.dex */
public interface OverlapDetector {
    boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2);
}
