package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface OverlapDetector {
    boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2);
}
