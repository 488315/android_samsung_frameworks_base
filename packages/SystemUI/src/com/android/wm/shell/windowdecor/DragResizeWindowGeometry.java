package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import android.graphics.Region;
import android.util.Size;
import android.view.MotionEvent;
import android.window.DesktopModeFlags;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum DisabledEdge {
        LEFT,
        RIGHT,
        NONE
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TaskEdges {
        public final Rect mBottomEdgeBounds;
        public final DisabledEdge mDisabledEdge;
        public final Rect mHandleRect;
        public final Rect mLeftEdgeBounds;
        public final Region mRegion;
        public final Rect mRightEdgeBounds;
        public final Rect mTopEdgeBounds;

        public /* synthetic */ TaskEdges(int i, Size size, int i2, int i3, int i4, int i5, DisabledEdge disabledEdge) {
            this(size, i, disabledEdge, i2, i3, i4);
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
                if (this.mTopEdgeBounds.equals(taskEdges.mTopEdgeBounds) && this.mLeftEdgeBounds.equals(taskEdges.mLeftEdgeBounds) && this.mRightEdgeBounds.equals(taskEdges.mRightEdgeBounds) && this.mBottomEdgeBounds.equals(taskEdges.mBottomEdgeBounds)) {
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
            if (disabledEdge2 != disabledEdge) {
                region.union(this.mRightEdgeBounds);
            }
            if (disabledEdge2 != DisabledEdge.LEFT) {
                region.union(this.mLeftEdgeBounds);
            }
            region.union(this.mTopEdgeBounds);
            region.union(this.mBottomEdgeBounds);
            if (!CoreRune.MW_CAPTION_HANDLE || this.mHandleRect.isEmpty()) {
                return;
            }
            region.op(this.mHandleRect, Region.Op.DIFFERENCE);
        }

        private TaskEdges(Size size, int i, DisabledEdge disabledEdge, int i2, int i3, int i4) {
            this.mDisabledEdge = disabledEdge;
            int i5 = -i;
            this.mTopEdgeBounds = new Rect(i, i5, size.getWidth() - i, 0);
            this.mLeftEdgeBounds = new Rect(i5, i, i4, size.getHeight() - i);
            this.mRightEdgeBounds = new Rect(size.getWidth() - i4, i, size.getWidth() + i, size.getHeight() - i);
            this.mBottomEdgeBounds = new Rect(i, size.getHeight() - i4, size.getWidth() - i, size.getHeight() + i);
            Rect rect = new Rect();
            this.mHandleRect = rect;
            if (i2 != 0) {
                rect.set(i3, i5, i2 + i3, i);
            }
            Region region = new Region();
            this.mRegion = region;
            union(region);
        }
    }

    public DragResizeWindowGeometry(int i, Size size, int i2, int i3, int i4, int i5, DisabledEdge disabledEdge) {
        this(i, size, i2, i3, i4, i5, disabledEdge, 0, 0);
    }

    public static boolean isEdgeResizePermitted(MotionEvent motionEvent) {
        if (CoreRune.MW_CAPTION_FREEFORM) {
            return true;
        }
        return DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue() ? motionEvent.getToolType(0) == 2 || motionEvent.getToolType(0) == 3 || (motionEvent.isFromSource(8194) && motionEvent.getToolType(0) == 1) : motionEvent.getToolType(0) == 3;
    }

    public final int calculateCtrlType(float f, float f2, boolean z, boolean z2) {
        boolean isTrue = DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue();
        TaskCorners taskCorners = this.mFineTaskCorners;
        if (!isTrue) {
            return z ? taskCorners.calculateCornersCtrlType(f, f2) : calculateEdgeResizeCtrlType(f, f2);
        }
        int calculateCornersCtrlType = z ? this.mLargeTaskCorners.calculateCornersCtrlType(f, f2) : taskCorners.calculateCornersCtrlType(f, f2);
        return (calculateCornersCtrlType == 0 && z2) ? calculateEdgeResizeCtrlType(f, f2) : calculateCornersCtrlType;
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

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002d, code lost:
    
        r8 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldHandleEvent(android.view.MotionEvent r7, android.graphics.Point r8) {
        /*
            r6 = this;
            r0 = 0
            float r1 = r7.getX(r0)
            int r2 = r8.x
            float r2 = (float) r2
            float r1 = r1 + r2
            float r2 = r7.getY(r0)
            int r8 = r8.y
            float r8 = (float) r8
            float r2 = r2 + r8
            android.window.DesktopModeFlags r8 = android.window.DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE
            boolean r8 = r8.isTrue()
            r3 = 4098(0x1002, float:5.743E-42)
            r4 = 1
            com.android.wm.shell.windowdecor.DragResizeWindowGeometry$TaskCorners r5 = r6.mFineTaskCorners
            if (r8 == 0) goto L5b
            int r8 = r7.getSource()
            r8 = r8 & r3
            if (r8 != r3) goto L31
            com.android.wm.shell.windowdecor.DragResizeWindowGeometry$TaskCorners r8 = r6.mLargeTaskCorners
            int r8 = r8.calculateCornersCtrlType(r1, r2)
            if (r8 == 0) goto L2f
        L2d:
            r8 = r4
            goto L38
        L2f:
            r8 = r0
            goto L38
        L31:
            int r8 = r5.calculateCornersCtrlType(r1, r2)
            if (r8 == 0) goto L2f
            goto L2d
        L38:
            if (r8 != 0) goto L49
            boolean r7 = isEdgeResizePermitted(r7)
            if (r7 == 0) goto L49
            int r7 = r6.calculateEdgeResizeCtrlType(r1, r2)
            if (r7 == 0) goto L47
            goto L48
        L47:
            r4 = r0
        L48:
            r8 = r4
        L49:
            boolean r7 = com.samsung.android.rune.CoreRune.MW_CAPTION_HANDLE
            if (r7 == 0) goto L5a
            com.android.wm.shell.windowdecor.DragResizeWindowGeometry$TaskEdges r6 = r6.mTaskEdges
            android.graphics.Rect r6 = r6.mHandleRect
            int r7 = (int) r1
            int r1 = (int) r2
            boolean r6 = r6.contains(r7, r1)
            if (r6 == 0) goto L5a
            return r0
        L5a:
            return r8
        L5b:
            int r7 = r7.getSource()
            r7 = r7 & r3
            if (r7 != r3) goto L6a
            int r6 = r5.calculateCornersCtrlType(r1, r2)
            if (r6 == 0) goto L69
            return r4
        L69:
            return r0
        L6a:
            int r6 = r6.calculateEdgeResizeCtrlType(r1, r2)
            if (r6 == 0) goto L71
            return r4
        L71:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DragResizeWindowGeometry.shouldHandleEvent(android.view.MotionEvent, android.graphics.Point):boolean");
    }

    public DragResizeWindowGeometry(int i, Size size, int i2, int i3, int i4, int i5, DisabledEdge disabledEdge, int i6, int i7) {
        this.mTaskCornerRadius = i;
        this.mTaskSize = size;
        this.mResizeHandleEdgeOutset = i2;
        this.mResizeHandleEdgeInset = i3;
        this.mLargeTaskCorners = new TaskCorners(size, i5 * 2, disabledEdge);
        this.mFineTaskCorners = new TaskCorners(size, i4 * 2, disabledEdge);
        int i8 = 0;
        this.mTaskEdges = new TaskEdges(i2, size, i6, i7, i3, i8, disabledEdge);
        this.mPointerTaskEdges = new TaskEdges(i4, size, i6, i7, 0, i8, disabledEdge);
    }
}
