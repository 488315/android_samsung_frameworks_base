package com.android.wm.shell.common;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/* loaded from: classes3.dex */
public final class MultiDisplayDragMoveBoundsCalculator {
    public static final MultiDisplayDragMoveBoundsCalculator INSTANCE = new MultiDisplayDragMoveBoundsCalculator();

    private MultiDisplayDragMoveBoundsCalculator() {
    }

    public static RectF calculateGlobalDpBoundsForDrag(DisplayLayout displayLayout, PointF pointF, Rect rect, DisplayLayout displayLayout2, float f, float f2) {
        PointF pointFLocalPxToGlobalDp = displayLayout.localPxToGlobalDp(Float.valueOf(pointF.x), Float.valueOf(pointF.y));
        PointF pointFLocalPxToGlobalDp2 = displayLayout2.localPxToGlobalDp(Float.valueOf(f), Float.valueOf(f2));
        PointF pointFLocalPxToGlobalDp3 = displayLayout.localPxToGlobalDp(Integer.valueOf(rect.left), Integer.valueOf(rect.top));
        float fPxToDp = displayLayout.pxToDp(Integer.valueOf(rect.width()));
        float fPxToDp2 = displayLayout.pxToDp(Integer.valueOf(rect.height()));
        float f3 = (pointFLocalPxToGlobalDp2.x - pointFLocalPxToGlobalDp.x) + pointFLocalPxToGlobalDp3.x;
        float f4 = (pointFLocalPxToGlobalDp2.y - pointFLocalPxToGlobalDp.y) + pointFLocalPxToGlobalDp3.y;
        return new RectF(f3, f4, fPxToDp + f3, fPxToDp2 + f4);
    }

    public static Rect convertGlobalDpToLocalPxForRect(RectF rectF, DisplayLayout displayLayout) {
        PointF pointFGlobalDpToLocalPx = displayLayout.globalDpToLocalPx(Float.valueOf(rectF.left), Float.valueOf(rectF.top));
        PointF pointFGlobalDpToLocalPx2 = displayLayout.globalDpToLocalPx(Float.valueOf(rectF.right), Float.valueOf(rectF.bottom));
        return new Rect((int) pointFGlobalDpToLocalPx.x, (int) pointFGlobalDpToLocalPx.y, (int) pointFGlobalDpToLocalPx2.x, (int) pointFGlobalDpToLocalPx2.y);
    }
}
