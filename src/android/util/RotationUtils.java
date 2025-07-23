package android.util;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public class RotationUtils {
    public static int deltaRotation(int i, int i2) {
        int i3 = i2 - i;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public static int reverseRotationDirectionAroundZAxis(int i) {
        if (i == 1) {
            return 3;
        }
        if (i == 3) {
            return 1;
        }
        return i;
    }

    public static Insets rotateInsets(Insets insets, int i) {
        if (insets == null || insets == Insets.NONE || i == 0) {
            return insets;
        }
        if (i == 1) {
            return Insets.of(insets.top, insets.right, insets.bottom, insets.left);
        }
        if (i == 2) {
            return Insets.of(insets.right, insets.bottom, insets.left, insets.top);
        }
        if (i == 3) {
            return Insets.of(insets.bottom, insets.left, insets.top, insets.right);
        }
        throw new IllegalArgumentException("unknown rotation: " + i);
    }

    public static void rotateBounds(Rect rect, Rect rect2, int i, int i2) {
        rotateBounds(rect, rect2, deltaRotation(i, i2));
    }

    public static void rotateBounds(Rect rect, int i, int i2, int i3) {
        int i4 = rect.left;
        int i5 = rect.top;
        if (i3 == 1) {
            rect.left = rect.top;
            rect.top = i - rect.right;
            rect.right = rect.bottom;
            rect.bottom = i - i4;
            return;
        }
        if (i3 == 2) {
            rect.left = i - rect.right;
            rect.right = i - i4;
            rect.top = i2 - rect.bottom;
            rect.bottom = i2 - i5;
            return;
        }
        if (i3 != 3) {
            return;
        }
        rect.left = i2 - rect.bottom;
        rect.bottom = rect.right;
        rect.right = i2 - rect.top;
        rect.top = i4;
    }

    public static void rotateBounds(Rect rect, Rect rect2, int i) {
        rotateBounds(rect, rect2.right, rect2.bottom, i);
    }

    public static void rotateSurface(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        if (i == 0) {
            transaction.setMatrix(surfaceControl, 1.0f, 0.0f, 0.0f, 1.0f);
            return;
        }
        if (i == 1) {
            transaction.setMatrix(surfaceControl, 0.0f, -1.0f, 1.0f, 0.0f);
        } else if (i == 2) {
            transaction.setMatrix(surfaceControl, -1.0f, 0.0f, 0.0f, -1.0f);
        } else {
            if (i != 3) {
                return;
            }
            transaction.setMatrix(surfaceControl, 0.0f, 1.0f, -1.0f, 0.0f);
        }
    }

    public static void rotatePoint(Point point, int i, int i2, int i3) {
        int i4 = point.x;
        if (i == 1) {
            point.x = point.y;
            point.y = i2 - i4;
        } else if (i == 2) {
            point.x = i2 - point.x;
            point.y = i3 - point.y;
        } else {
            if (i != 3) {
                return;
            }
            point.x = i3 - point.y;
            point.y = i4;
        }
    }

    public static void rotatePointF(PointF pointF, int i, float f, float f2) {
        float f3 = pointF.x;
        if (i == 1) {
            pointF.x = pointF.y;
            pointF.y = f - f3;
        } else if (i == 2) {
            pointF.x = f - pointF.x;
            pointF.y = f2 - pointF.y;
        } else {
            if (i != 3) {
                return;
            }
            pointF.x = f2 - pointF.y;
            pointF.y = f3;
        }
    }

    public static void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, Matrix matrix) {
        if (i == 0) {
            matrix.reset();
            return;
        }
        if (i == 1) {
            matrix.setRotate(270.0f);
            matrix.postTranslate(0.0f, i2);
        } else if (i == 2) {
            matrix.setRotate(180.0f);
            matrix.postTranslate(i2, i3);
        } else if (i == 3) {
            matrix.setRotate(90.0f);
            matrix.postTranslate(i3, 0.0f);
        } else {
            throw new IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void fitWithinBounds(Rect rect, Rect rect2, int i, int i2) {
        int i3;
        if (rect2 == null || rect2.isEmpty() || rect2.contains(rect)) {
            return;
        }
        int min = Math.min(i, rect.width());
        int i4 = 0;
        if (rect.right < rect2.left + min) {
            i3 = min - (rect.right - rect2.left);
        } else {
            i3 = rect.left > rect2.right - min ? -(min - (rect2.right - rect.left)) : 0;
        }
        int min2 = Math.min(i2, rect.width());
        if (rect.bottom < rect2.top + min2) {
            i4 = min2 - (rect.bottom - rect2.top);
        } else if (rect.top > rect2.bottom - min2) {
            i4 = -(min2 - (rect2.bottom - rect.top));
        }
        rect.offset(i3, i4);
    }
}
