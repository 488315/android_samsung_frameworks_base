package com.android.systemui.biometrics.udfps;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.EllipseOverlapDetectorParams;
import kotlin.internal.ProgressionUtilKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class EllipseOverlapDetector implements OverlapDetector {
    public final EllipseOverlapDetectorParams params;

    public EllipseOverlapDetector(EllipseOverlapDetectorParams ellipseOverlapDetectorParams) {
        this.params = ellipseOverlapDetectorParams;
    }

    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public final boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect, Rect rect2) {
        EllipseOverlapDetectorParams ellipseOverlapDetectorParams;
        boolean z;
        int i;
        int i2;
        int i3;
        String str;
        String str2;
        int i4;
        int i5;
        int i6;
        Rect rect3 = rect;
        if (normalizedTouchData.isWithinBounds(rect)) {
            return true;
        }
        if (!normalizedTouchData.isWithinBounds(rect2)) {
            return false;
        }
        int i7 = rect3.top;
        int i8 = rect3.bottom;
        EllipseOverlapDetectorParams ellipseOverlapDetectorParams2 = this.params;
        int i9 = ellipseOverlapDetectorParams2.stepSize;
        String str3 = ".";
        String str4 = "Step must be positive, was: ";
        if (i9 <= 0) {
            throw new IllegalArgumentException(LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i9, "Step must be positive, was: ", "."));
        }
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i7, i8, i9);
        if (i7 <= progressionLastElement) {
            z = false;
            i = 0;
            i2 = 0;
            while (true) {
                int i10 = rect3.left;
                int i11 = rect3.right;
                int i12 = ellipseOverlapDetectorParams2.stepSize;
                if (i12 <= 0) {
                    throw new IllegalArgumentException(LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i12, str4, str3));
                }
                int progressionLastElement2 = ProgressionUtilKt.getProgressionLastElement(i10, i11, i12);
                if (i10 <= progressionLastElement2) {
                    while (true) {
                        int centerX = rect.centerX();
                        int centerY = rect.centerY();
                        int width = rect.width() / 2;
                        int i13 = centerX - i10;
                        int i14 = centerY - i7;
                        int i15 = (i14 * i14) + (i13 * i13);
                        float f = i15;
                        str = str3;
                        str2 = str4;
                        float f2 = width * ellipseOverlapDetectorParams2.targetSize;
                        SensorPixelPosition sensorPixelPosition = f <= f2 * f2 ? SensorPixelPosition.TARGET : i15 <= width * width ? SensorPixelPosition.SENSOR : SensorPixelPosition.OUTSIDE;
                        if (sensorPixelPosition != SensorPixelPosition.OUTSIDE) {
                            int i16 = i + 1;
                            Point point = new Point(i10, i7);
                            double d = normalizedTouchData.orientation;
                            EllipseOverlapDetectorParams ellipseOverlapDetectorParams3 = ellipseOverlapDetectorParams2;
                            i3 = i9;
                            float cos = (float) Math.cos(d);
                            float f3 = point.x;
                            float f4 = normalizedTouchData.x;
                            float f5 = (f3 - f4) * cos;
                            ellipseOverlapDetectorParams = ellipseOverlapDetectorParams3;
                            i4 = progressionLastElement;
                            float sin = (float) Math.sin(d);
                            float f6 = point.y;
                            i5 = i7;
                            float f7 = normalizedTouchData.y;
                            float f8 = (f6 - f7) * sin;
                            int i17 = i10;
                            i6 = progressionLastElement2;
                            float sin2 = (point.x - f4) * ((float) Math.sin(d));
                            float cos2 = (point.y - f7) * ((float) Math.cos(d));
                            float f9 = f5 + f8;
                            float f10 = 2;
                            float f11 = normalizedTouchData.minor / f10;
                            float f12 = sin2 - cos2;
                            float f13 = normalizedTouchData.major / f10;
                            if (((f12 * f12) / (f13 * f13)) + ((f9 * f9) / (f11 * f11)) <= 1.0f) {
                                i2++;
                                z = (sensorPixelPosition == SensorPixelPosition.TARGET) | z;
                            }
                            i = i16;
                            i10 = i17;
                        } else {
                            i5 = i7;
                            ellipseOverlapDetectorParams = ellipseOverlapDetectorParams2;
                            i3 = i9;
                            i6 = progressionLastElement2;
                            i4 = progressionLastElement;
                        }
                        if (i10 == i6) {
                            break;
                        }
                        i10 += i12;
                        progressionLastElement2 = i6;
                        progressionLastElement = i4;
                        str3 = str;
                        str4 = str2;
                        i9 = i3;
                        ellipseOverlapDetectorParams2 = ellipseOverlapDetectorParams;
                        i7 = i5;
                    }
                    i7 = i5;
                } else {
                    ellipseOverlapDetectorParams = ellipseOverlapDetectorParams2;
                    i3 = i9;
                    str = str3;
                    str2 = str4;
                    i4 = progressionLastElement;
                }
                if (i7 == i4) {
                    break;
                }
                i7 += i3;
                rect3 = rect;
                progressionLastElement = i4;
                str3 = str;
                str4 = str2;
                i9 = i3;
                ellipseOverlapDetectorParams2 = ellipseOverlapDetectorParams;
            }
        } else {
            ellipseOverlapDetectorParams = ellipseOverlapDetectorParams2;
            z = false;
            i = 0;
            i2 = 0;
        }
        return ((float) i2) / ((float) i) >= ellipseOverlapDetectorParams.minOverlap && z;
    }
}
