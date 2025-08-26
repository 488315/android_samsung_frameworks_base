package com.android.wm.shell.windowdecor;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class FluidResizeTaskPositioner implements TaskPositioner, Transitions.TransitionHandler {
    public int mCtrlType;
    public final DesktopState mDesktopState;
    public final DisplayController mDisplayController;
    public final ArrayList mDragEventListeners;
    public IBinder mDragResizeEndTransition;
    public boolean mHasDragResized;
    public boolean mIsResizingOrAnimatingResize;
    public final PointF mRepositionStartPoint;
    public final Rect mRepositionTaskBounds;
    public int mRotation;
    public final Rect mStableBounds;
    public final Rect mTaskBoundsAtDragStart;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;
    public final WindowDecoration mWindowDecoration;

    public FluidResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, WindowDecoration windowDecoration, DisplayController displayController, DesktopState desktopState) {
        this(shellTaskOrganizer, transitions, windowDecoration, displayController, null, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), desktopState);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void addDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        this.mDragEventListeners.add(desktopTilingWindowDecoration);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final boolean isResizing$2() {
        int i = this.mCtrlType;
        return ((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) ? false : true;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final boolean isResizingOrAnimating() {
        return this.mIsResizingOrAnimatingResize;
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningEnd(float f, float f2, int i) {
        if (isResizing$2() && this.mHasDragResized) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            WindowDecoration windowDecoration = this.mWindowDecoration;
            windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, false);
            PointF pointFCalculateDelta = DragPositioningCallbackUtility.calculateDelta(f, f2, this.mRepositionStartPoint);
            if (DragPositioningCallbackUtility.changeBounds(this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, pointFCalculateDelta, this.mDisplayController, this.mWindowDecoration, ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode)) {
                windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, this.mRepositionTaskBounds);
            }
            this.mDragResizeEndTransition = this.mTransitions.startTransition(6, windowContainerTransaction, this);
        } else if (this.mCtrlType == 0) {
            DragPositioningCallbackUtility.updateTaskBounds(this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mRepositionStartPoint, f, f2);
        }
        this.mTaskBoundsAtDragStart.setEmpty();
        this.mRepositionStartPoint.set(0.0f, 0.0f);
        this.mCtrlType = 0;
        this.mHasDragResized = false;
        return new Rect(this.mRepositionTaskBounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0063  */
    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect onDragPositioningMove(float f, float f2, int i) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        PointF pointFCalculateDelta = DragPositioningCallbackUtility.calculateDelta(f, f2, this.mRepositionStartPoint);
        boolean zIsResizing$2 = isResizing$2();
        WindowDecoration windowDecoration = this.mWindowDecoration;
        if (zIsResizing$2) {
            if (DragPositioningCallbackUtility.changeBounds(this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, pointFCalculateDelta, this.mDisplayController, this.mWindowDecoration, ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode)) {
                if (!this.mHasDragResized) {
                    ArrayList arrayList = this.mDragEventListeners;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((DragPositioningCallbackUtility.DragEventListener) obj).onDragMove(windowDecoration.mTaskInfo.taskId);
                    }
                    windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, true);
                }
                windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, this.mRepositionTaskBounds);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                this.mHasDragResized = true;
                this.mIsResizingOrAnimatingResize = true;
            } else if (this.mCtrlType == 0) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
                DragPositioningCallbackUtility.updateTaskBounds(this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mRepositionStartPoint, f, f2);
                transaction.setPosition(windowDecoration.mTaskSurface, r0.left, r0.top);
                transaction.apply();
            }
        }
        return new Rect(this.mRepositionTaskBounds);
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningStart(int i, float f, float f2, int i2) {
        this.mCtrlType = i;
        Rect rect = this.mTaskBoundsAtDragStart;
        WindowDecoration windowDecoration = this.mWindowDecoration;
        rect.set(windowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
        this.mRepositionStartPoint.set(f, f2);
        ArrayList arrayList = this.mDragEventListeners;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((DragPositioningCallbackUtility.DragEventListener) obj).onDragStart(windowDecoration.mTaskInfo.taskId);
        }
        if (this.mCtrlType != 0 && !windowDecoration.mHasGlobalFocus) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.reorder(windowDecoration.mTaskInfo.token, true, true);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
        this.mRepositionTaskBounds.set(this.mTaskBoundsAtDragStart);
        int displayRotation = windowDecoration.mTaskInfo.configuration.windowConfiguration.getDisplayRotation();
        if (this.mStableBounds.isEmpty() || this.mRotation != displayRotation) {
            this.mRotation = displayRotation;
            this.mDisplayController.getDisplayLayout(windowDecoration.mDisplay.getDisplayId()).getStableBounds(this.mStableBounds, false);
        }
        return new Rect(this.mRepositionTaskBounds);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        if (iBinder.equals(this.mDragResizeEndTransition)) {
            this.mIsResizingOrAnimatingResize = false;
            this.mDragResizeEndTransition = null;
        }
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void removeDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        this.mDragEventListeners.remove(desktopTilingWindowDecoration);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            SurfaceControl leash = change.getLeash();
            Rect endAbsBounds = change.getEndAbsBounds();
            Point endRelOffset = change.getEndRelOffset();
            transaction.setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).setPosition(leash, endRelOffset.x, endRelOffset.y);
            transaction2.setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).setPosition(leash, endRelOffset.x, endRelOffset.y);
        }
        transaction.apply();
        if (iBinder.equals(this.mDragResizeEndTransition)) {
            this.mIsResizingOrAnimatingResize = false;
            this.mDragResizeEndTransition = null;
        }
        transitionFinishCallback.onTransitionFinished(null);
        return true;
    }

    public FluidResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, WindowDecoration windowDecoration, DisplayController displayController, DragPositioningCallbackUtility.DragEventListener dragEventListener, Supplier<SurfaceControl.Transaction> supplier, DesktopState desktopState) {
        ArrayList arrayList = new ArrayList();
        this.mDragEventListeners = arrayList;
        this.mStableBounds = new Rect();
        this.mTaskBoundsAtDragStart = new Rect();
        this.mRepositionStartPoint = new PointF();
        this.mRepositionTaskBounds = new Rect();
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTransitions = transitions;
        this.mWindowDecoration = windowDecoration;
        this.mDisplayController = displayController;
        if (dragEventListener != null) {
            arrayList.add(dragEventListener);
        }
        this.mTransactionSupplier = supplier;
        this.mDesktopState = desktopState;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void close() {
    }
}
