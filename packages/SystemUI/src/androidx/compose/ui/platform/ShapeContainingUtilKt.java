package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import kotlin.NoWhenBranchMatchedException;

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
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        long j2 = roundRect.topRightCornerRadius;
        int i2 = (int) (j2 >> 32);
        if (Float.intBitsToFloat(i2) + fIntBitsToFloat <= roundRect.getWidth()) {
            long j3 = roundRect.bottomLeftCornerRadius;
            int i3 = (int) (j3 >> 32);
            float fIntBitsToFloat2 = Float.intBitsToFloat(i3);
            long j4 = roundRect.bottomRightCornerRadius;
            int i4 = (int) (j4 >> 32);
            if (Float.intBitsToFloat(i4) + fIntBitsToFloat2 <= roundRect.getWidth()) {
                int i5 = (int) (j & 4294967295L);
                int i6 = (int) (j3 & 4294967295L);
                if (Float.intBitsToFloat(i6) + Float.intBitsToFloat(i5) <= roundRect.getHeight()) {
                    int i7 = (int) (j2 & 4294967295L);
                    int i8 = (int) (j4 & 4294967295L);
                    if (Float.intBitsToFloat(i8) + Float.intBitsToFloat(i7) <= roundRect.getHeight()) {
                        float fIntBitsToFloat3 = Float.intBitsToFloat(i);
                        float f6 = roundRect.left;
                        float f7 = fIntBitsToFloat3 + f6;
                        float fIntBitsToFloat4 = Float.intBitsToFloat(i5) + f4;
                        float fIntBitsToFloat5 = f3 - Float.intBitsToFloat(i2);
                        float fIntBitsToFloat6 = Float.intBitsToFloat(i7) + f4;
                        float fIntBitsToFloat7 = f3 - Float.intBitsToFloat(i4);
                        float fIntBitsToFloat8 = f5 - Float.intBitsToFloat(i8);
                        float fIntBitsToFloat9 = f5 - Float.intBitsToFloat(i6);
                        float fIntBitsToFloat10 = Float.intBitsToFloat(i3) + f6;
                        if (f < f7 && f2 < fIntBitsToFloat4) {
                            return m713isWithinEllipseVE1yxkc(f, f2, f7, fIntBitsToFloat4, roundRect.topLeftCornerRadius);
                        }
                        if (f < fIntBitsToFloat10 && f2 > fIntBitsToFloat9) {
                            return m713isWithinEllipseVE1yxkc(f, f2, fIntBitsToFloat10, fIntBitsToFloat9, roundRect.bottomLeftCornerRadius);
                        }
                        if (f > fIntBitsToFloat5 && f2 < fIntBitsToFloat6) {
                            return m713isWithinEllipseVE1yxkc(f, f2, fIntBitsToFloat5, fIntBitsToFloat6, roundRect.topRightCornerRadius);
                        }
                        if (f <= fIntBitsToFloat7 || f2 <= fIntBitsToFloat8) {
                            return true;
                        }
                        return m713isWithinEllipseVE1yxkc(f, f2, fIntBitsToFloat7, fIntBitsToFloat8, roundRect.bottomRightCornerRadius);
                    }
                }
            }
        }
        AndroidPath androidPathPath = AndroidPath_androidKt.Path();
        Path.addRoundRect$default(androidPathPath, roundRect);
        return isInPath(androidPathPath, f, f2);
    }

    public static final boolean isInPath(Path path, float f, float f2) {
        Rect rect = new Rect(f - 0.005f, f2 - 0.005f, f + 0.005f, f2 + 0.005f);
        AndroidPath androidPathPath = AndroidPath_androidKt.Path();
        Path.addRect$default(androidPathPath, rect);
        AndroidPath androidPathPath2 = AndroidPath_androidKt.Path();
        PathOperation.Companion.getClass();
        androidPathPath2.m445opN5in7k0(path, androidPathPath, PathOperation.Intersect);
        boolean zIsEmpty = androidPathPath2.internalPath.isEmpty();
        androidPathPath2.reset();
        androidPathPath.reset();
        return !zIsEmpty;
    }

    /* renamed from: isWithinEllipse-VE1yxkc, reason: not valid java name */
    public static final boolean m713isWithinEllipseVE1yxkc(float f, float f2, float f3, float f4, long j) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        return ((f6 * f6) / (fIntBitsToFloat2 * fIntBitsToFloat2)) + ((f5 * f5) / (fIntBitsToFloat * fIntBitsToFloat)) <= 1.0f;
    }
}
