package com.android.wm.shell.common.pip;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipDoubleTapHelper {
    private PipDoubleTapHelper() {
    }

    public static int nextSizeSpec(PipBoundsState pipBoundsState, Rect rect) {
        boolean z = pipBoundsState.getBounds().width() == pipBoundsState.mMaxSize.x && pipBoundsState.getBounds().height() == pipBoundsState.mMaxSize.y;
        boolean z2 = pipBoundsState.getBounds().width() == pipBoundsState.mNormalBounds.width() && pipBoundsState.getBounds().height() == pipBoundsState.mNormalBounds.height();
        if (z2 && rect.width() == pipBoundsState.mNormalBounds.width() && rect.height() == pipBoundsState.mNormalBounds.height()) {
            return 1;
        }
        if (z && rect.width() == pipBoundsState.mMaxSize.x && rect.height() == pipBoundsState.mMaxSize.y) {
            return 0;
        }
        if (z2 || z) {
            return 2;
        }
        return pipBoundsState.getBounds().width() > (pipBoundsState.mMaxSize.x + pipBoundsState.mMinSize.x) / 2 ? 0 : 1;
    }
}
