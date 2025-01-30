package com.android.p038wm.shell.pip.phone;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipPinchResizingAlgorithm {
    public final PointF mTmpDownVector = new PointF();
    public final PointF mTmpLastVector = new PointF();
    public final PointF mTmpDownCentroid = new PointF();
    public final PointF mTmpLastCentroid = new PointF();

    public final float calculateBoundsAndAngle(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4, Point point, Point point2, Rect rect, Rect rect2) {
        float f;
        float hypot = (float) Math.hypot(pointF2.x - pointF.x, pointF2.y - pointF.y);
        float hypot2 = (float) Math.hypot(pointF4.x - pointF3.x, pointF4.y - pointF3.y);
        float max = Math.max(point.x / rect.width(), point.y / rect.height());
        float min = Math.min(point2.x / rect.width(), point2.y / rect.height());
        float f2 = hypot2 / hypot;
        float f3 = f2 - min;
        if (f3 <= 0.0f) {
            f3 = 0.0f;
        }
        float max2 = Math.max(max - 0.0f, Math.min((f3 * 0.25f) + min, f2));
        rect2.set(rect);
        if (max2 != 1.0f) {
            int centerX = rect2.centerX();
            int centerY = rect2.centerY();
            rect2.offset(-centerX, -centerY);
            rect2.scale(max2);
            rect2.offset(centerX, centerY);
        }
        PointF pointF5 = this.mTmpDownCentroid;
        pointF5.set((pointF2.x + pointF.x) / 2.0f, (pointF2.y + pointF.y) / 2.0f);
        PointF pointF6 = this.mTmpLastCentroid;
        pointF6.set((pointF4.x + pointF3.x) / 2.0f, (pointF4.y + pointF3.y) / 2.0f);
        rect2.offset((int) (pointF6.x - pointF5.x), (int) (pointF6.y - pointF5.y));
        PointF pointF7 = this.mTmpDownVector;
        pointF7.set(pointF2.x - pointF.x, pointF2.y - pointF.y);
        PointF pointF8 = this.mTmpLastVector;
        pointF8.set(pointF4.x - pointF3.x, pointF4.y - pointF3.y);
        float f4 = pointF7.x;
        float f5 = pointF8.y;
        float f6 = pointF7.y;
        float f7 = pointF8.x;
        float degrees = (float) Math.toDegrees((float) Math.atan2((f4 * f5) - (f6 * f7), (f6 * f5) + (f4 * f7)));
        float signum = Math.signum(degrees);
        if (Float.compare(degrees, 0.0f) == 0) {
            f = 0.0f;
        } else {
            float f8 = degrees / 45.0f;
            float abs = f8 / Math.abs(f8);
            float abs2 = Math.abs(f8) - 1.0f;
            float f9 = ((abs2 * abs2 * abs2) + 1.0f) * abs;
            if (Math.abs(f9) >= 1.0f) {
                f9 /= Math.abs(f9);
            }
            f = f9 * 0.4f * 45.0f;
        }
        return Math.max(0.0f, Math.abs(f) - 5.0f) * signum;
    }
}
