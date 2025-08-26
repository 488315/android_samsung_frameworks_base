package com.android.wm.shell.windowdecor;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Size;
import android.view.MotionEvent;
import android.window.DesktopModeFlags;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class DragResizeWindowGeometry {
    public final TaskCorners mFineTaskCorners;
    public boolean mIsPointerInput;
    public final TaskCorners mLargeTaskCorners;
    public final TaskEdges mPointerTaskEdges;
    public final int mResizeHandleEdgeInset;
    public final int mResizeHandleEdgeOutset;
    public final int mTaskCornerRadius;
    public final TaskEdges mTaskEdges;
    public final Size mTaskSize;

    public enum DisabledEdge {
        LEFT,
        RIGHT,
        NONE
    }

    public class TaskCorners {
        public final int mCornerSize;
        public final DisabledEdge mDisabledEdge;
        public final Rect mLeftBottomCornerBounds;
        public final Rect mLeftTopCornerBounds;
        public final Rect mRightBottomCornerBounds;
        public final Rect mRightTopCornerBounds;

        public TaskCorners(Size size, int i, DisabledEdge disabledEdge) {
            Rect rect;
            this.mCornerSize = i;
            this.mDisabledEdge = disabledEdge;
            int i2 = i / 2;
            DisabledEdge disabledEdge2 = DisabledEdge.LEFT;
            if (disabledEdge == disabledEdge2) {
                rect = new Rect();
            } else {
                int i3 = -i2;
                rect = new Rect(i3, i3, i2, i2);
            }
            this.mLeftTopCornerBounds = rect;
            DisabledEdge disabledEdge3 = DisabledEdge.RIGHT;
            this.mRightTopCornerBounds = disabledEdge == disabledEdge3 ? new Rect() : new Rect(size.getWidth() - i2, -i2, size.getWidth() + i2, i2);
            this.mLeftBottomCornerBounds = disabledEdge == disabledEdge2 ? new Rect() : new Rect(-i2, size.getHeight() - i2, i2, size.getHeight() + i2);
            this.mRightBottomCornerBounds = disabledEdge == disabledEdge3 ? new Rect() : new Rect(size.getWidth() - i2, size.getHeight() - i2, size.getWidth() + i2, size.getHeight() + i2);
        }

        public final int calculateCornersCtrlType(float f, float f2) {
            int i = (int) f;
            int i2 = (int) f2;
            if (this.mLeftTopCornerBounds.contains(i, i2)) {
                return 5;
            }
            if (this.mLeftBottomCornerBounds.contains(i, i2)) {
                return 9;
            }
            if (this.mRightTopCornerBounds.contains(i, i2)) {
                return 6;
            }
            return this.mRightBottomCornerBounds.contains(i, i2) ? 10 : 0;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof TaskCorners) {
                TaskCorners taskCorners = (TaskCorners) obj;
                if (this.mCornerSize == taskCorners.mCornerSize && this.mLeftTopCornerBounds.equals(taskCorners.mLeftTopCornerBounds) && this.mRightTopCornerBounds.equals(taskCorners.mRightTopCornerBounds) && this.mLeftBottomCornerBounds.equals(taskCorners.mLeftBottomCornerBounds) && this.mRightBottomCornerBounds.equals(taskCorners.mRightBottomCornerBounds)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mCornerSize), this.mLeftTopCornerBounds, this.mRightTopCornerBounds, this.mLeftBottomCornerBounds, this.mRightBottomCornerBounds);
        }

        public final String toString() {
            return "TaskCorners of size " + this.mCornerSize + " for the top left " + this.mLeftTopCornerBounds + " top right " + this.mRightTopCornerBounds + " bottom left " + this.mLeftBottomCornerBounds + " bottom right " + this.mRightBottomCornerBounds;
        }

        public final void union(Region region) {
            DisabledEdge disabledEdge = DisabledEdge.RIGHT;
            DisabledEdge disabledEdge2 = this.mDisabledEdge;
            if (disabledEdge2 != disabledEdge) {
                region.union(this.mRightTopCornerBounds);
                region.union(this.mRightBottomCornerBounds);
            }
            if (disabledEdge2 != DisabledEdge.LEFT) {
                region.union(this.mLeftTopCornerBounds);
                region.union(this.mLeftBottomCornerBounds);
            }
        }
    }

    public class TaskEdges {
        public final Rect mBottomEdgeBounds;
        public final DisabledEdge mDisabledEdge;
        public final Rect mHandleRect;
        public final boolean mIsLeftHintGestureOverlapped;
        public final boolean mIsRightHintGestureOverlapped;
        public final Rect mLeftEdgeBounds;
        public final Region mRegion;
        public final Rect mRightEdgeBounds;
        public final Rect mTopEdgeBounds;

        public /* synthetic */ TaskEdges(Size size, int i, DisabledEdge disabledEdge, int i2, int i3, int i4, Rect rect, Rect rect2, Point point, int i5) {
            this(size, i, disabledEdge, i2, i3, i4, rect, rect2, point);
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof TaskEdges) {
                TaskEdges taskEdges = (TaskEdges) obj;
                if (this.mTopEdgeBounds.equals(taskEdges.mTopEdgeBounds) && this.mLeftEdgeBounds.equals(taskEdges.mLeftEdgeBounds) && this.mRightEdgeBounds.equals(taskEdges.mRightEdgeBounds) && this.mBottomEdgeBounds.equals(taskEdges.mBottomEdgeBounds) && this.mIsLeftHintGestureOverlapped == taskEdges.mIsLeftHintGestureOverlapped && this.mIsRightHintGestureOverlapped == taskEdges.mIsRightHintGestureOverlapped) {
                    return true;
                }
            }
            return false;
        }

        public final int getCtrlType(int i, int i2) {
            int i3 = this.mTopEdgeBounds.contains(i, i2) ? 4 : 0;
            if (this.mRightEdgeBounds.contains(i, i2)) {
                i3 |= 2;
            }
            if (this.mLeftEdgeBounds.contains(i, i2)) {
                i3 |= 1;
            }
            return this.mBottomEdgeBounds.contains(i, i2) ? i3 | 8 : i3;
        }

        public final int hashCode() {
            return Objects.hash(this.mTopEdgeBounds, this.mLeftEdgeBounds, this.mRightEdgeBounds, this.mBottomEdgeBounds);
        }

        public final String toString() {
            return "TaskEdges for the top " + this.mTopEdgeBounds + " left " + this.mLeftEdgeBounds + " right " + this.mRightEdgeBounds + " bottom " + this.mBottomEdgeBounds + " handle " + this.mHandleRect;
        }

        public final void union(Region region) {
            DisabledEdge disabledEdge = DisabledEdge.RIGHT;
            DisabledEdge disabledEdge2 = this.mDisabledEdge;
            if (disabledEdge2 != disabledEdge && !this.mIsRightHintGestureOverlapped) {
                region.union(this.mRightEdgeBounds);
            }
            if (disabledEdge2 != DisabledEdge.LEFT && !this.mIsLeftHintGestureOverlapped) {
                region.union(this.mLeftEdgeBounds);
            }
            region.union(this.mTopEdgeBounds);
            region.union(this.mBottomEdgeBounds);
            if (!CoreRune.MW_CAPTION_HANDLE || this.mHandleRect.isEmpty()) {
                return;
            }
            region.op(this.mHandleRect, Region.Op.DIFFERENCE);
        }

        private TaskEdges(Size size, int i, DisabledEdge disabledEdge, int i2, int i3, int i4, Rect rect, Rect rect2, Point point) {
            boolean zIntersect;
            Rect rect3 = new Rect();
            this.mDisabledEdge = disabledEdge;
            int i5 = -i;
            boolean zIntersect2 = false;
            this.mTopEdgeBounds = new Rect(i, i5, size.getWidth() - i, 0);
            Rect rect4 = new Rect(i5, i, i4, size.getHeight() - i);
            this.mLeftEdgeBounds = rect4;
            Rect rect5 = new Rect(size.getWidth() - i4, i, size.getWidth() + i, size.getHeight() - i);
            this.mRightEdgeBounds = rect5;
            this.mBottomEdgeBounds = new Rect(i, size.getHeight() - i4, size.getWidth() - i, size.getHeight() + i);
            Rect rect6 = new Rect();
            this.mHandleRect = rect6;
            if (i2 != 0) {
                rect6.set(i3, i5, i3 + i2, i);
            }
            if (rect == null || rect.isEmpty()) {
                zIntersect = false;
            } else {
                rect3.set(rect4);
                rect3.offset(point.x, point.y);
                zIntersect = rect3.intersect(rect);
            }
            this.mIsLeftHintGestureOverlapped = zIntersect;
            if (rect2 != null && !rect2.isEmpty()) {
                rect3.set(rect5);
                rect3.offset(point.x, point.y);
                zIntersect2 = rect3.intersect(rect2);
            }
            this.mIsRightHintGestureOverlapped = zIntersect2;
            Region region = new Region();
            this.mRegion = region;
            union(region);
        }
    }

    public DragResizeWindowGeometry(int i, Size size, int i2, int i3, int i4, int i5, DisabledEdge disabledEdge) {
        this(i, size, i2, i3, i4, i5, disabledEdge, 0, 0, null, null, new Point());
    }

    public static boolean isEdgeResizePermitted(MotionEvent motionEvent) {
        if (CoreRune.MW_CAPTION_FREEFORM) {
            return true;
        }
        return DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue() ? motionEvent.getToolType(0) == 2 || motionEvent.getToolType(0) == 3 || (motionEvent.isFromSource(8194) && motionEvent.getToolType(0) == 1) : motionEvent.getToolType(0) == 3;
    }

    public final int calculateCtrlType(float f, float f2, boolean z, boolean z2) {
        boolean zIsTrue = DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue();
        TaskCorners taskCorners = this.mFineTaskCorners;
        if (!zIsTrue) {
            return z ? taskCorners.calculateCornersCtrlType(f, f2) : calculateEdgeResizeCtrlType(f, f2);
        }
        int iCalculateCornersCtrlType = z ? this.mLargeTaskCorners.calculateCornersCtrlType(f, f2) : taskCorners.calculateCornersCtrlType(f, f2);
        return (iCalculateCornersCtrlType == 0 && z2) ? calculateEdgeResizeCtrlType(f, f2) : iCalculateCornersCtrlType;
    }

    public final int calculateEdgeResizeCtrlType(float f, float f2) {
        if (this.mIsPointerInput) {
            TaskEdges taskEdges = this.mPointerTaskEdges;
            int i = (int) f;
            int i2 = (int) f2;
            if (taskEdges.mRegion.contains(i, i2)) {
                return taskEdges.getCtrlType(i, i2);
            }
            return 0;
        }
        TaskEdges taskEdges2 = this.mTaskEdges;
        int i3 = (int) f;
        int i4 = (int) f2;
        if (taskEdges2.mRegion.contains(i3, i4)) {
            return taskEdges2.getCtrlType(i3, i4);
        }
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof DragResizeWindowGeometry) {
            DragResizeWindowGeometry dragResizeWindowGeometry = (DragResizeWindowGeometry) obj;
            if (this.mTaskCornerRadius == dragResizeWindowGeometry.mTaskCornerRadius && this.mTaskSize.equals(dragResizeWindowGeometry.mTaskSize) && this.mResizeHandleEdgeOutset == dragResizeWindowGeometry.mResizeHandleEdgeOutset && this.mResizeHandleEdgeInset == dragResizeWindowGeometry.mResizeHandleEdgeInset && this.mFineTaskCorners.equals(dragResizeWindowGeometry.mFineTaskCorners) && this.mLargeTaskCorners.equals(dragResizeWindowGeometry.mLargeTaskCorners) && this.mTaskEdges.equals(dragResizeWindowGeometry.mTaskEdges)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTaskCornerRadius), this.mTaskSize, Integer.valueOf(this.mResizeHandleEdgeOutset), Integer.valueOf(this.mResizeHandleEdgeInset), this.mFineTaskCorners, this.mLargeTaskCorners, this.mTaskEdges);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x002d, code lost:
    
        r8 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldHandleEvent(MotionEvent motionEvent, Point point) {
        float x = motionEvent.getX(0) + point.x;
        float y = motionEvent.getY(0) + point.y;
        boolean zIsTrue = DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue();
        TaskCorners taskCorners = this.mFineTaskCorners;
        if (!zIsTrue) {
            return (motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL) == 4098 ? taskCorners.calculateCornersCtrlType(x, y) != 0 : calculateEdgeResizeCtrlType(x, y) != 0;
        }
        boolean z = (motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL) == 4098 ? false : false;
        if (!z && isEdgeResizePermitted(motionEvent)) {
            z = calculateEdgeResizeCtrlType(x, y) != 0;
        }
        if (CoreRune.MW_CAPTION_HANDLE && this.mTaskEdges.mHandleRect.contains((int) x, (int) y)) {
            return false;
        }
        return z;
    }

    public DragResizeWindowGeometry(int i, Size size, int i2, int i3, int i4, int i5, DisabledEdge disabledEdge, int i6, int i7, Rect rect, Rect rect2, Point point) {
        this.mTaskCornerRadius = i;
        this.mTaskSize = size;
        this.mResizeHandleEdgeOutset = i2;
        this.mResizeHandleEdgeInset = i3;
        this.mLargeTaskCorners = new TaskCorners(size, i5 * 2, disabledEdge);
        this.mFineTaskCorners = new TaskCorners(size, i4 * 2, disabledEdge);
        int i8 = 0;
        this.mTaskEdges = new TaskEdges(size, i2, disabledEdge, i6, i7, i3, rect, rect2, point, i8);
        this.mPointerTaskEdges = new TaskEdges(size, i4, disabledEdge, i6, i7, 0, rect, rect2, point, i8);
    }
}
