package com.android.wm.shell.common.pip;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/* loaded from: classes3.dex */
public class PipPinchResizingAlgorithm {
    public final PointF mTmpDownVector = new PointF();
    public final PointF mTmpLastVector = new PointF();
    public final PointF mTmpDownCentroid = new PointF();
    public final PointF mTmpLastCentroid = new PointF();

    public final float calculateBoundsAndAngle(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4, Point point, Point point2, Rect rect, Rect rect2) {
        float f;
        float fHypot = (float) Math.hypot(pointF2.x - pointF.x, pointF2.y - pointF.y);
        float fHypot2 = (float) Math.hypot(pointF4.x - pointF3.x, pointF4.y - pointF3.y);
        float fMax = Math.max(point.x / rect.width(), point.y / rect.height());
        float fMin = Math.min(point2.x / rect.width(), point2.y / rect.height());
        float f2 = fHypot2 / fHypot;
        float f3 = f2 - fMin;
        if (f3 <= 0.0f) {
            f3 = 0.0f;
        }
        float fMax2 = Math.max(fMax - 0.0f, Math.min((f3 * 0.25f) + fMin, f2));
        rect2.set(rect);
        if (fMax2 != 1.0f) {
            int iCenterX = rect2.centerX();
            int iCenterY = rect2.centerY();
            rect2.offset(-iCenterX, -iCenterY);
            rect2.scale(fMax2);
            rect2.offset(iCenterX, iCenterY);
        }
        this.mTmpDownCentroid.set((pointF2.x + pointF.x) / 2.0f, (pointF2.y + pointF.y) / 2.0f);
        this.mTmpLastCentroid.set((pointF4.x + pointF3.x) / 2.0f, (pointF4.y + pointF3.y) / 2.0f);
        PointF pointF5 = this.mTmpLastCentroid;
        float f4 = pointF5.x;
        PointF pointF6 = this.mTmpDownCentroid;
        rect2.offset((int) (f4 - pointF6.x), (int) (pointF5.y - pointF6.y));
        this.mTmpDownVector.set(pointF2.x - pointF.x, pointF2.y - pointF.y);
        this.mTmpLastVector.set(pointF4.x - pointF3.x, pointF4.y - pointF3.y);
        PointF pointF7 = this.mTmpDownVector;
        PointF pointF8 = this.mTmpLastVector;
        float f5 = pointF7.x;
        float f6 = pointF8.y;
        float f7 = pointF7.y;
        float f8 = pointF8.x;
        float degrees = (float) Math.toDegrees((float) Math.atan2((f5 * f6) - (f7 * f8), (f7 * f6) + (f5 * f8)));
        float fSignum = Math.signum(degrees);
        if (Float.compare(degrees, 0.0f) == 0) {
            f = 0.0f;
        } else {
            float f9 = degrees / 45.0f;
            float fAbs = f9 / Math.abs(f9);
            float fAbs2 = Math.abs(f9) - 1.0f;
            float fAbs3 = ((fAbs2 * fAbs2 * fAbs2) + 1.0f) * fAbs;
            if (Math.abs(fAbs3) >= 1.0f) {
                fAbs3 /= Math.abs(fAbs3);
            }
            f = fAbs3 * 0.4f * 45.0f;
        }
        return Math.max(0.0f, Math.abs(f) - 5.0f) * fSignum;
    }
}
