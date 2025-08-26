package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import android.view.MotionEvent;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;

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

    default void updateRestoreAnimationMotionEvent(MotionEvent motionEvent) {
    }

    default void cancelTaskMotion() {
    }

    default void closeFreeformResizeGuide() {
    }

    default void onDragPositioningCancel() {
    }

    default void playMaximizedTaskRestoreAnimation(Rect rect, DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda3) {
    }
}
