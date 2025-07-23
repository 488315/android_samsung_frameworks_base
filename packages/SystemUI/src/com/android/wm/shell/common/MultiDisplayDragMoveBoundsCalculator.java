package com.android.wm.shell.common;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayDragMoveBoundsCalculator {
    public static final MultiDisplayDragMoveBoundsCalculator INSTANCE = new MultiDisplayDragMoveBoundsCalculator();

    private MultiDisplayDragMoveBoundsCalculator() {
    }

    public static RectF calculateGlobalDpBoundsForDrag(DisplayLayout displayLayout, PointF pointF, Rect rect, DisplayLayout displayLayout2, float f, float f2) {
        PointF localPxToGlobalDp = displayLayout.localPxToGlobalDp(Float.valueOf(pointF.x), Float.valueOf(pointF.y));
        PointF localPxToGlobalDp2 = displayLayout2.localPxToGlobalDp(Float.valueOf(f), Float.valueOf(f2));
        PointF localPxToGlobalDp3 = displayLayout.localPxToGlobalDp(Integer.valueOf(rect.left), Integer.valueOf(rect.top));
        float pxToDp = displayLayout.pxToDp(Integer.valueOf(rect.width()));
        float pxToDp2 = displayLayout.pxToDp(Integer.valueOf(rect.height()));
        float f3 = (localPxToGlobalDp2.x - localPxToGlobalDp.x) + localPxToGlobalDp3.x;
        float f4 = (localPxToGlobalDp2.y - localPxToGlobalDp.y) + localPxToGlobalDp3.y;
        return new RectF(f3, f4, pxToDp + f3, pxToDp2 + f4);
    }

    public static Rect convertGlobalDpToLocalPxForRect(RectF rectF, DisplayLayout displayLayout) {
        PointF globalDpToLocalPx = displayLayout.globalDpToLocalPx(Float.valueOf(rectF.left), Float.valueOf(rectF.top));
        PointF globalDpToLocalPx2 = displayLayout.globalDpToLocalPx(Float.valueOf(rectF.right), Float.valueOf(rectF.bottom));
        return new Rect((int) globalDpToLocalPx.x, (int) globalDpToLocalPx.y, (int) globalDpToLocalPx2.x, (int) globalDpToLocalPx2.y);
    }
}
