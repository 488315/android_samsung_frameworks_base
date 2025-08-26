package com.android.wm.shell.windowdecor;

import android.graphics.PointF;
import android.graphics.Rect;
import android.window.DesktopModeFlags;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;

/* loaded from: classes3.dex */
public class DragPositioningCallbackUtility {

    public interface DragEventListener {
        void onDragMove(int i);

        void onDragStart(int i);
    }

    public static PointF calculateDelta(float f, float f2, PointF pointF) {
        return new PointF(f - pointF.x, f2 - pointF.y);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean changeBounds(int i, Rect rect, Rect rect2, Rect rect3, PointF pointF, DisplayController displayController, WindowDecoration windowDecoration, boolean z) {
        float defaultMinSize;
        if (i != 0) {
            int i2 = rect.left;
            int i3 = rect.top;
            int i4 = rect.right;
            int i5 = rect.bottom;
            rect.set(rect2);
            if ((i & 1) != 0) {
                int iMax = Math.max(rect.left + ((int) pointF.x), rect3.left);
                rect.left = iMax;
                int i6 = rect3.left;
                boolean z2 = iMax != i6 || iMax + ((int) pointF.x) == i6;
                if ((i & 2) != 0) {
                    int iMin = Math.min(rect.right + ((int) pointF.x), rect3.right);
                    rect.right = iMin;
                    int i7 = rect3.right;
                    if (iMin == i7 && iMin + ((int) pointF.x) != i7) {
                        z2 = false;
                    }
                }
                if ((i & 4) != 0) {
                    int iMax2 = Math.max(rect.top + ((int) pointF.y), rect3.top);
                    rect.top = iMax2;
                    int i8 = rect3.top;
                    if (iMax2 == i8 && iMax2 + ((int) pointF.y) != i8) {
                        z2 = false;
                    }
                }
                if ((i & 8) != 0) {
                    int iMin2 = Math.min(rect.bottom + ((int) pointF.y), rect3.bottom);
                    rect.bottom = iMin2;
                    int i9 = rect3.bottom;
                    boolean z3 = (iMin2 != i9 || iMin2 + ((int) pointF.y) == i9) ? z2 : false;
                    if (isExceedingWidthConstraint(rect.width(), i4 - i2, rect3, displayController, windowDecoration, z, false)) {
                        rect.right = i4;
                        rect.left = i2;
                        z3 = false;
                    }
                    int iHeight = rect.height();
                    boolean z4 = iHeight - (i5 - i3) > 0;
                    float f = iHeight;
                    int iLoadDimensionPixelSize = windowDecoration.mTaskInfo.minHeight;
                    if (iLoadDimensionPixelSize < 0) {
                        if (!isSizeConstraintForDesktopModeEnabled(z)) {
                            defaultMinSize = getDefaultMinSize(displayController, windowDecoration);
                            if (f < defaultMinSize ? !z4 : isSizeConstraintForDesktopModeEnabled(z) && iHeight > rect3.height() && z4) {
                            }
                            if (DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue()) {
                            }
                            if (i2 == rect.left) {
                            }
                            return true;
                        }
                        iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(windowDecoration.mDecorWindowContext.getResources(), R.dimen.desktop_mode_minimum_window_height);
                        defaultMinSize = iLoadDimensionPixelSize;
                        if (f < defaultMinSize ? !z4 : isSizeConstraintForDesktopModeEnabled(z) && iHeight > rect3.height() && z4) {
                        }
                        if (DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue()) {
                            rect.top = i3;
                            rect.bottom = i5;
                            rect.right = i4;
                            rect.left = i2;
                        }
                        if (i2 == rect.left) {
                        }
                        return true;
                    }
                    defaultMinSize = iLoadDimensionPixelSize;
                    if (f < defaultMinSize ? !z4 : isSizeConstraintForDesktopModeEnabled(z) && iHeight > rect3.height() && z4) {
                        rect.top = i3;
                        rect.bottom = i5;
                        z3 = false;
                    }
                    if (DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue() && !z3 && !windowDecoration.mTaskInfo.isResizeable) {
                        rect.top = i3;
                        rect.bottom = i5;
                        rect.right = i4;
                        rect.left = i2;
                    }
                    if (i2 == rect.left || i3 != rect.top || i4 != rect.right || i5 != rect.bottom) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static float getDefaultMinSize(DisplayController displayController, WindowDecoration windowDecoration) {
        float f = displayController.getDisplayLayout(windowDecoration.mTaskInfo.displayId).mDensityDpi * 0.00625f;
        int i = windowDecoration.mTaskInfo.displayId;
        DesktopStateImpl.Companion.getClass();
        return (DesktopStateImpl.Companion.inDesktopWindowing(i) ? windowDecoration.mTaskInfo.desktopDefaultMinSize : windowDecoration.mTaskInfo.defaultMinSize) * f;
    }

    public static float getMinWidth(DisplayController displayController, WindowDecoration windowDecoration, boolean z, boolean z2) {
        int i = windowDecoration.mTaskInfo.minWidth;
        if (i >= 0) {
            return i;
        }
        if (isSizeConstraintForDesktopModeEnabled(z)) {
            return WindowDecoration.loadDimensionPixelSize(windowDecoration.mDecorWindowContext.getResources(), z2 ? R.dimen.dw_tile_minimum_window_width : R.dimen.desktop_mode_minimum_window_width);
        }
        return getDefaultMinSize(displayController, windowDecoration);
    }

    public static boolean isExceedingWidthConstraint(int i, int i2, Rect rect, DisplayController displayController, WindowDecoration windowDecoration, boolean z, boolean z2) {
        boolean z3 = i - i2 > 0;
        if (z2) {
            if (i < getMinWidth(displayController, windowDecoration, z, z2)) {
                return !z3;
            }
        } else if (i < getMinWidth(displayController, windowDecoration, z, false)) {
            return !z3;
        }
        return isSizeConstraintForDesktopModeEnabled(z) && i > rect.width() && z3;
    }

    public static boolean isSizeConstraintForDesktopModeEnabled(boolean z) {
        return z && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS.isTrue();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean snapTaskBoundsIfNecessary(Rect rect, Rect rect2) {
        boolean z;
        int i;
        int i2;
        if (rect2.width() == 0) {
            return false;
        }
        int i3 = rect.left;
        int i4 = rect2.left;
        if (i3 < i4) {
            rect.offset(i4 - i3, 0);
        } else {
            int i5 = rect2.right;
            if (i3 <= i5) {
                z = false;
                i = rect.top;
                i2 = rect2.top;
                if (i >= i2) {
                    rect.offset(0, i2 - i);
                    return true;
                }
                int i6 = rect2.bottom;
                if (i <= i6) {
                    return z;
                }
                rect.offset(0, i6 - i);
                return true;
            }
            rect.offset(i5 - i3, 0);
        }
        z = true;
        i = rect.top;
        i2 = rect2.top;
        if (i >= i2) {
        }
    }

    public static void updateTaskBounds(Rect rect, Rect rect2, PointF pointF, float f, float f2) {
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        rect.set(rect2);
        rect.offset((int) f3, (int) f4);
    }

    public static void updateTaskBounds(Rect rect, Boolean bool, Rect rect2, Rect rect3, int i, PointF pointF, float f, float f2) {
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        rect2.set(rect3);
        rect2.offset((int) f3, (int) f4);
        if (rect != null) {
            if (bool.booleanValue()) {
                int i2 = rect2.bottom;
                int i3 = rect.bottom;
                if (i2 > i3) {
                    rect2.offset(0, i3 - i2);
                }
            } else {
                int i4 = rect2.top + i;
                int i5 = rect.bottom;
                if (i4 > i5) {
                    rect2.offset(0, i5 - i);
                }
            }
            int i6 = rect2.top;
            int i7 = rect.top;
            if (i6 < i7) {
                rect2.offsetTo(rect2.left, i7);
            }
        }
    }
}
