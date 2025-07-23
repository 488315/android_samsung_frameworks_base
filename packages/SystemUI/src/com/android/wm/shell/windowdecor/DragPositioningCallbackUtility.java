package com.android.wm.shell.windowdecor;

import android.graphics.PointF;
import android.graphics.Rect;
import android.window.DesktopModeFlags;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragPositioningCallbackUtility {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DragEventListener {
        void onDragMove(int i);

        void onDragStart(int i);
    }

    public static PointF calculateDelta(float f, float f2, PointF pointF) {
        return new PointF(f - pointF.x, f2 - pointF.y);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean changeBounds(int r13, android.graphics.Rect r14, android.graphics.Rect r15, android.graphics.Rect r16, android.graphics.PointF r17, com.android.wm.shell.common.DisplayController r18, com.android.wm.shell.windowdecor.WindowDecoration r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.changeBounds(int, android.graphics.Rect, android.graphics.Rect, android.graphics.Rect, android.graphics.PointF, com.android.wm.shell.common.DisplayController, com.android.wm.shell.windowdecor.WindowDecoration, boolean):boolean");
    }

    public static float getMinWidth(DisplayController displayController, WindowDecoration windowDecoration, boolean z, boolean z2) {
        int i = windowDecoration.mTaskInfo.minWidth;
        if (i >= 0) {
            return i;
        }
        if (isSizeConstraintForDesktopModeEnabled(z)) {
            return WindowDecoration.loadDimensionPixelSize(windowDecoration.mDecorWindowContext.getResources(), z2 ? R.dimen.dw_tile_minimum_window_width : R.dimen.desktop_mode_minimum_window_width);
        }
        return windowDecoration.mTaskInfo.defaultMinSize * displayController.getDisplayLayout(windowDecoration.mTaskInfo.displayId).mDensityDpi * 0.00625f;
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean snapTaskBoundsIfNecessary(android.graphics.Rect r5, android.graphics.Rect r6) {
        /*
            int r0 = r6.width()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r0 = r5.left
            int r2 = r6.left
            r3 = 1
            if (r0 >= r2) goto L15
            int r2 = r2 - r0
            r5.offset(r2, r1)
        L13:
            r0 = r3
            goto L1f
        L15:
            int r2 = r6.right
            if (r0 <= r2) goto L1e
            int r2 = r2 - r0
            r5.offset(r2, r1)
            goto L13
        L1e:
            r0 = r1
        L1f:
            int r2 = r5.top
            int r4 = r6.top
            if (r2 >= r4) goto L2a
            int r4 = r4 - r2
            r5.offset(r1, r4)
            return r3
        L2a:
            int r6 = r6.bottom
            if (r2 <= r6) goto L33
            int r6 = r6 - r2
            r5.offset(r1, r6)
            return r3
        L33:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(android.graphics.Rect, android.graphics.Rect):boolean");
    }

    public static void updateTaskBounds(Rect rect, Rect rect2, PointF pointF, float f, float f2) {
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        rect.set(rect2);
        rect.offset((int) f3, (int) f4);
    }

    public static void updateTaskBounds(Rect rect, Rect rect2, Rect rect3, int i, PointF pointF, float f, float f2) {
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        rect2.set(rect3);
        rect2.offset((int) f3, (int) f4);
        if (rect != null) {
            int i2 = rect2.bottom;
            int i3 = rect.bottom;
            if (i2 > i3) {
                rect2.offset(0, i3 - i2);
            }
            int i4 = rect2.top + i;
            int i5 = rect.bottom;
            if (i4 > i5) {
                rect2.offset(0, i5 - i);
            }
            int i6 = rect2.top;
            int i7 = rect.top;
            if (i6 < i7) {
                rect2.offsetTo(rect2.left, i7);
            }
        }
    }
}
