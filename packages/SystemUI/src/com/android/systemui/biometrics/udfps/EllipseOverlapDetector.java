package com.android.systemui.biometrics.udfps;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.EllipseOverlapDetectorParams;
import kotlin.internal.ProgressionUtilKt;

/* loaded from: classes.dex */
public final class EllipseOverlapDetector implements OverlapDetector {
    public final EllipseOverlapDetectorParams params;

    public EllipseOverlapDetector(EllipseOverlapDetectorParams ellipseOverlapDetectorParams) {
        this.params = ellipseOverlapDetectorParams;
    }

    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public final boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        int i2;
        Rect rect3 = rect;
        boolean z4 = true;
        if (normalizedTouchData.isWithinBounds(rect)) {
            return true;
        }
        boolean z5 = false;
        if (!normalizedTouchData.isWithinBounds(rect2)) {
            return false;
        }
        int i3 = rect3.top;
        int i4 = rect3.bottom;
        EllipseOverlapDetectorParams ellipseOverlapDetectorParams = this.params;
        int i5 = ellipseOverlapDetectorParams.stepSize;
        if (i5 <= 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i5, "Step must be positive, was: ", "."));
        }
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i3, i4, i5);
        if (i3 <= progressionLastElement) {
            z3 = false;
            i = 0;
            i2 = 0;
            while (true) {
                int i6 = rect3.left;
                int i7 = rect3.right;
                int i8 = ellipseOverlapDetectorParams.stepSize;
                if (i8 <= 0) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i8, "Step must be positive, was: ", "."));
                }
                int progressionLastElement2 = ProgressionUtilKt.getProgressionLastElement(i6, i7, i8);
                if (i6 <= progressionLastElement2) {
                    while (true) {
                        int iCenterX = rect3.centerX();
                        int iCenterY = rect3.centerY();
                        z = z4;
                        z2 = z5;
                        int iWidth = rect3.width() / 2;
                        int i9 = iCenterX - i6;
                        int i10 = iCenterY - i3;
                        int i11 = (i10 * i10) + (i9 * i9);
                        float f = i11;
                        float f2 = ellipseOverlapDetectorParams.targetSize * iWidth;
                        SensorPixelPosition sensorPixelPosition = f <= f2 * f2 ? SensorPixelPosition.TARGET : i11 <= iWidth * iWidth ? SensorPixelPosition.SENSOR : SensorPixelPosition.OUTSIDE;
                        if (sensorPixelPosition != SensorPixelPosition.OUTSIDE) {
                            int i12 = i + 1;
                            Point point = new Point(i6, i3);
                            float f3 = normalizedTouchData.orientation;
                            boolean z6 = z3;
                            float fCos = (float) Math.cos(f3);
                            float f4 = point.x;
                            float f5 = normalizedTouchData.x;
                            float f6 = (f4 - f5) * fCos;
                            double d = f3;
                            float fSin = (float) Math.sin(d);
                            float f7 = point.y;
                            float f8 = normalizedTouchData.y;
                            float f9 = (f7 - f8) * fSin;
                            float fSin2 = (point.x - f5) * ((float) Math.sin(d));
                            float fCos2 = (point.y - f8) * ((float) Math.cos(d));
                            float f10 = f6 + f9;
                            float f11 = 2;
                            float f12 = normalizedTouchData.minor / f11;
                            float f13 = (f10 * f10) / (f12 * f12);
                            float f14 = fSin2 - fCos2;
                            float f15 = normalizedTouchData.major / f11;
                            if (((f14 * f14) / (f15 * f15)) + f13 <= 1.0f) {
                                i2++;
                                z3 = z6 | (sensorPixelPosition == SensorPixelPosition.TARGET ? z : z2);
                            } else {
                                z3 = z6;
                            }
                            i = i12;
                        }
                        if (i6 == progressionLastElement2) {
                            break;
                        }
                        i6 += i8;
                        rect3 = rect;
                        z5 = z2;
                        z4 = z;
                    }
                } else {
                    z = z4;
                    z2 = z5;
                }
                if (i3 == progressionLastElement) {
                    break;
                }
                i3 += i5;
                rect3 = rect;
                z5 = z2;
                z4 = z;
            }
        } else {
            z = true;
            z2 = false;
            z3 = false;
            i = 0;
            i2 = 0;
        }
        return (((float) i2) / ((float) i) < ellipseOverlapDetectorParams.minOverlap || !z3) ? z2 : z;
    }
}
