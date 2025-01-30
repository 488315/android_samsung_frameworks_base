package com.android.wm.shell.windowdecor;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FluidResizeTaskPositioner implements DragPositioningCallback {
    public int mCtrlType;
    public final Rect mDisallowedAreaForEndBounds;
    public final DisplayController mDisplayController;
    public final DragPositioningCallbackUtility.DragStartListener mDragStartListener;
    public boolean mHasDragResized;
    public final PointF mRepositionStartPoint;
    public final Rect mRepositionTaskBounds;
    public final Rect mStableBounds;
    public final Rect mTaskBoundsAtDragStart;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Supplier mTransactionSupplier;
    public final WindowDecoration mWindowDecoration;

    public FluidResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController, Rect rect) {
        this(shellTaskOrganizer, windowDecoration, displayController, rect, new FluidResizeTaskPositioner$$ExternalSyntheticLambda0(), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0());
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningEnd(float f, float f2) {
        int i = this.mCtrlType;
        boolean z = ((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) ? false : true;
        PointF pointF = this.mRepositionStartPoint;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        Rect rect = this.mTaskBoundsAtDragStart;
        Rect rect2 = this.mRepositionTaskBounds;
        WindowDecoration windowDecoration = this.mWindowDecoration;
        if (z && this.mHasDragResized) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, false);
            if (DragPositioningCallbackUtility.changeBounds(this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, new PointF(f - pointF.x, f2 - pointF.y), this.mDisplayController, this.mWindowDecoration)) {
                windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, rect2);
            }
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
        } else if (i == 0) {
            if (!this.mDisallowedAreaForEndBounds.contains((int) f, (int) f2)) {
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                float f3 = f - pointF.x;
                float f4 = f2 - pointF.y;
                rect2.set(rect);
                rect2.offset((int) f3, (int) f4);
                windowContainerTransaction2.setBounds(windowDecoration.mTaskInfo.token, rect2);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
            }
        }
        rect.setEmpty();
        pointF.set(0.0f, 0.0f);
        this.mCtrlType = 0;
        this.mHasDragResized = false;
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningMove(float f, float f2) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        PointF pointF = this.mRepositionStartPoint;
        PointF pointF2 = new PointF(f - pointF.x, f2 - pointF.y);
        int i = this.mCtrlType;
        boolean z = ((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) ? false : true;
        Rect rect = this.mRepositionTaskBounds;
        WindowDecoration windowDecoration = this.mWindowDecoration;
        if (z && DragPositioningCallbackUtility.changeBounds(i, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, pointF2, this.mDisplayController, this.mWindowDecoration)) {
            if (!this.mHasDragResized) {
                windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, true);
            }
            windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, rect);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            this.mHasDragResized = true;
            return;
        }
        if (this.mCtrlType == 0) {
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
            float f3 = f - pointF.x;
            float f4 = f2 - pointF.y;
            rect.set(this.mTaskBoundsAtDragStart);
            rect.offset((int) f3, (int) f4);
            transaction.setPosition(windowDecoration.mTaskSurface, rect.left, rect.top);
            transaction.apply();
        }
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningStart(float f, float f2, int i) {
        this.mCtrlType = i;
        WindowDecoration windowDecoration = this.mWindowDecoration;
        Rect bounds = windowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
        Rect rect = this.mTaskBoundsAtDragStart;
        rect.set(bounds);
        this.mRepositionStartPoint.set(f, f2);
        this.mDragStartListener.onDragStart(windowDecoration.mTaskInfo.taskId);
        if (this.mCtrlType != 0 && !windowDecoration.mTaskInfo.isFocused) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.reorder(windowDecoration.mTaskInfo.token, true);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
        this.mRepositionTaskBounds.set(rect);
    }

    public FluidResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController, Rect rect, DragPositioningCallbackUtility.DragStartListener dragStartListener, Supplier<SurfaceControl.Transaction> supplier) {
        this.mStableBounds = new Rect();
        this.mTaskBoundsAtDragStart = new Rect();
        this.mRepositionStartPoint = new PointF();
        this.mRepositionTaskBounds = new Rect();
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mWindowDecoration = windowDecoration;
        this.mDisplayController = displayController;
        this.mDisallowedAreaForEndBounds = new Rect(rect);
        this.mDragStartListener = dragStartListener;
        this.mTransactionSupplier = supplier;
    }
}
