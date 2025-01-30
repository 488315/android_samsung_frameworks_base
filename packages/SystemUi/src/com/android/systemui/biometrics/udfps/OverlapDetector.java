package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface OverlapDetector {
    boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2);
}
