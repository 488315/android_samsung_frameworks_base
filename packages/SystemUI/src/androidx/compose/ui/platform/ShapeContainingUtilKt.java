package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ShapeContainingUtilKt {
    public static final boolean isInOutline(Outline outline, float f, float f2) {
        if (outline instanceof Outline.Rectangle) {
            Rect rect = ((Outline.Rectangle) outline).rect;
            return rect.left <= f && f < rect.right && rect.top <= f2 && f2 < rect.bottom;
        }
        if (!(outline instanceof Outline.Rounded)) {
            if (outline instanceof Outline.Generic) {
                return isInPath(((Outline.Generic) outline).path, f, f2);
            }
            throw new NoWhenBranchMatchedException();
        }
        RoundRect roundRect = ((Outline.Rounded) outline).roundRect;
        if (f < roundRect.left) {
            return false;
        }
        float f3 = roundRect.right;
        if (f >= f3) {
            return false;
        }
        float f4 = roundRect.top;
        if (f2 < f4) {
            return false;
        }
        float f5 = roundRect.bottom;
        if (f2 >= f5) {
            return false;
        }
        long j = roundRect.topLeftCornerRadius;
        int i = (int) (j >> 32);
        float intBitsToFloat = Float.intBitsToFloat(i);
        long j2 = roundRect.topRightCornerRadius;
        int i2 = (int) (j2 >> 32);
        if (Float.intBitsToFloat(i2) + intBitsToFloat <= roundRect.getWidth()) {
            long j3 = roundRect.bottomLeftCornerRadius;
            int i3 = (int) (j3 >> 32);
            float intBitsToFloat2 = Float.intBitsToFloat(i3);
            long j4 = roundRect.bottomRightCornerRadius;
            int i4 = (int) (j4 >> 32);
            if (Float.intBitsToFloat(i4) + intBitsToFloat2 <= roundRect.getWidth()) {
                int i5 = (int) (j & 4294967295L);
                int i6 = (int) (j3 & 4294967295L);
                if (Float.intBitsToFloat(i6) + Float.intBitsToFloat(i5) <= roundRect.getHeight()) {
                    int i7 = (int) (j2 & 4294967295L);
                    int i8 = (int) (j4 & 4294967295L);
                    if (Float.intBitsToFloat(i8) + Float.intBitsToFloat(i7) <= roundRect.getHeight()) {
                        float intBitsToFloat3 = Float.intBitsToFloat(i);
                        float f6 = roundRect.left;
                        float f7 = intBitsToFloat3 + f6;
                        float intBitsToFloat4 = Float.intBitsToFloat(i5) + f4;
                        float intBitsToFloat5 = f3 - Float.intBitsToFloat(i2);
                        float intBitsToFloat6 = Float.intBitsToFloat(i7) + f4;
                        float intBitsToFloat7 = f3 - Float.intBitsToFloat(i4);
                        float intBitsToFloat8 = f5 - Float.intBitsToFloat(i8);
                        float intBitsToFloat9 = f5 - Float.intBitsToFloat(i6);
                        float intBitsToFloat10 = Float.intBitsToFloat(i3) + f6;
                        if (f < f7 && f2 < intBitsToFloat4) {
                            return m711isWithinEllipseVE1yxkc(f, f2, f7, intBitsToFloat4, roundRect.topLeftCornerRadius);
                        }
                        if (f < intBitsToFloat10 && f2 > intBitsToFloat9) {
                            return m711isWithinEllipseVE1yxkc(f, f2, intBitsToFloat10, intBitsToFloat9, roundRect.bottomLeftCornerRadius);
                        }
                        if (f > intBitsToFloat5 && f2 < intBitsToFloat6) {
                            return m711isWithinEllipseVE1yxkc(f, f2, intBitsToFloat5, intBitsToFloat6, roundRect.topRightCornerRadius);
                        }
                        if (f <= intBitsToFloat7 || f2 <= intBitsToFloat8) {
                            return true;
                        }
                        return m711isWithinEllipseVE1yxkc(f, f2, intBitsToFloat7, intBitsToFloat8, roundRect.bottomRightCornerRadius);
                    }
                }
            }
        }
        AndroidPath Path = AndroidPath_androidKt.Path();
        Path.addRoundRect$default(Path, roundRect);
        return isInPath(Path, f, f2);
    }

    public static final boolean isInPath(Path path, float f, float f2) {
        Rect rect = new Rect(f - 0.005f, f2 - 0.005f, f + 0.005f, f2 + 0.005f);
        AndroidPath Path = AndroidPath_androidKt.Path();
        Path.addRect$default(Path, rect);
        AndroidPath Path2 = AndroidPath_androidKt.Path();
        PathOperation.Companion.getClass();
        Path2.m443opN5in7k0(path, Path, PathOperation.Intersect);
        boolean isEmpty = Path2.internalPath.isEmpty();
        Path2.reset();
        Path.reset();
        return !isEmpty;
    }

    /* renamed from: isWithinEllipse-VE1yxkc, reason: not valid java name */
    public static final boolean m711isWithinEllipseVE1yxkc(float f, float f2, float f3, float f4, long j) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        return ((f6 * f6) / (intBitsToFloat2 * intBitsToFloat2)) + ((f5 * f5) / (intBitsToFloat * intBitsToFloat)) <= 1.0f;
    }
}
