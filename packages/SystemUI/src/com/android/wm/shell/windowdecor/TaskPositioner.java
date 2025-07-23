package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface TaskPositioner extends DragPositioningCallback {
    void addDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration);

    default int changeFreeformScaleIfNeeded() {
        return -1;
    }

    void close();

    default TaskMotionController getTaskMotionController() {
        return null;
    }

    default boolean isAllowTouches() {
        return false;
    }

    boolean isResizingOrAnimating();

    default boolean isStashedAtNavigationBarPosition() {
        return false;
    }

    void removeDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration);

    default void getImeStartBounds(Rect rect) {
    }

    default void resetStashedFreeform(boolean z) {
    }

    default void setFreeformCaptionTouchState(FreeformCaptionTouchState freeformCaptionTouchState) {
    }

    default void setImeAnimating(boolean z) {
    }

    default void cancelTaskMotion() {
    }

    default void closeFreeformResizeGuide() {
    }

    default void onDragPositioningCancel() {
    }
}
