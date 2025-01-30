package com.android.p038wm.shell.windowdecor;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VeiledResizeTaskPositioner implements DragPositioningCallback, Transitions.TransitionHandler {
    public int mCtrlType;
    public final DesktopModeWindowDecoration mDesktopWindowDecoration;
    public final Rect mDisallowedAreaForEndBounds;
    public final DisplayController mDisplayController;
    public final DragPositioningCallbackUtility.DragStartListener mDragStartListener;
    public final PointF mRepositionStartPoint;
    public final Rect mRepositionTaskBounds;
    public final Rect mStableBounds;
    public final Rect mTaskBoundsAtDragStart;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;

    public VeiledResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, Rect rect, DragPositioningCallbackUtility.DragStartListener dragStartListener, Transitions transitions) {
        this(shellTaskOrganizer, desktopModeWindowDecoration, displayController, rect, dragStartListener, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), transitions);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final boolean isResizing() {
        int i = this.mCtrlType;
        return ((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) ? false : true;
    }

    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningEnd(float f, float f2) {
        PointF pointF = this.mRepositionStartPoint;
        PointF pointF2 = new PointF(f - pointF.x, f2 - pointF.y);
        boolean isResizing = isResizing();
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        Rect rect = this.mTaskBoundsAtDragStart;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mDesktopWindowDecoration;
        Rect rect2 = this.mRepositionTaskBounds;
        if (!isResizing) {
            if (!this.mDisallowedAreaForEndBounds.contains((int) f, (int) f2)) {
                float f3 = f - pointF.x;
                float f4 = f2 - pointF.y;
                rect2.set(rect);
                rect2.offset((int) f3, (int) f4);
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, rect2);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        } else if (rect.equals(rect2)) {
            desktopModeWindowDecoration.hideResizeVeil();
        } else {
            DragPositioningCallbackUtility.changeBounds(this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, pointF2, this.mDisplayController, this.mDesktopWindowDecoration);
            ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil.mSurfaceControlTransactionSupplier.get();
            resizeVeil.relayout(rect2, transaction);
            resizeVeil.mViewHost.getView().getViewRootImpl().applyTransactionOnDraw(transaction);
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            windowContainerTransaction2.setBounds(desktopModeWindowDecoration.mTaskInfo.token, rect2);
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                this.mTransitions.startTransition(6, windowContainerTransaction2, this);
            } else {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
            }
        }
        this.mCtrlType = 0;
        rect.setEmpty();
        pointF.set(0.0f, 0.0f);
    }

    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningMove(float f, float f2) {
        PointF pointF = this.mRepositionStartPoint;
        PointF pointF2 = new PointF(f - pointF.x, f2 - pointF.y);
        boolean isResizing = isResizing();
        Rect rect = this.mRepositionTaskBounds;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mDesktopWindowDecoration;
        if (isResizing && DragPositioningCallbackUtility.changeBounds(this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, this.mStableBounds, pointF2, this.mDisplayController, this.mDesktopWindowDecoration)) {
            ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil.mSurfaceControlTransactionSupplier.get();
            resizeVeil.relayout(rect, transaction);
            resizeVeil.mViewHost.getView().getViewRootImpl().applyTransactionOnDraw(transaction);
            return;
        }
        if (this.mCtrlType == 0) {
            SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
            float f3 = f - pointF.x;
            float f4 = f2 - pointF.y;
            rect.set(this.mTaskBoundsAtDragStart);
            rect.offset((int) f3, (int) f4);
            transaction2.setPosition(desktopModeWindowDecoration.mTaskSurface, rect.left, rect.top);
            transaction2.apply();
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningStart(float f, float f2, int i) {
        this.mCtrlType = i;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mDesktopWindowDecoration;
        Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
        Rect rect = this.mTaskBoundsAtDragStart;
        rect.set(bounds);
        this.mRepositionStartPoint.set(f, f2);
        if (isResizing()) {
            final ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
            SurfaceControl surfaceControl = desktopModeWindowDecoration.mTaskSurface;
            final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil.mSurfaceControlTransactionSupplier.get();
            if (!surfaceControl.equals(resizeVeil.mParentSurface)) {
                transaction.reparent(resizeVeil.mVeilSurface, surfaceControl);
                resizeVeil.mParentSurface = surfaceControl;
            }
            Context context = resizeVeil.mContext;
            resizeVeil.mViewHost.getView().setBackgroundColor(context.getColor((context.getResources().getConfiguration().uiMode & 48) == 32 ? R.color.desktop_mode_resize_veil_dark : R.color.desktop_mode_resize_veil_light));
            final ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setFloatValues(0.0f, 1.0f);
            valueAnimator.setDuration(100L);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ResizeVeil resizeVeil2 = ResizeVeil.this;
                    SurfaceControl.Transaction transaction2 = transaction;
                    transaction2.setAlpha(resizeVeil2.mVeilSurface, valueAnimator.getAnimatedFraction());
                    transaction2.apply();
                }
            });
            resizeVeil.relayout(rect, transaction);
            transaction.show(resizeVeil.mVeilSurface).addTransactionCommittedListener(context.getMainExecutor(), new SurfaceControl.TransactionCommittedListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$$ExternalSyntheticLambda1
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    valueAnimator.start();
                }
            }).setAlpha(resizeVeil.mVeilSurface, 0.0f);
            resizeVeil.mViewHost.getView().getViewRootImpl().applyTransactionOnDraw(transaction);
            if (!desktopModeWindowDecoration.mTaskInfo.isFocused) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.reorder(desktopModeWindowDecoration.mTaskInfo.token, true);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        }
        this.mDragStartListener.onDragStart(desktopModeWindowDecoration.mTaskInfo.taskId);
        this.mRepositionTaskBounds.set(rect);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        transaction.apply();
        this.mDesktopWindowDecoration.hideResizeVeil();
        this.mCtrlType = 0;
        transitionFinishCallback.onTransitionFinished(null, null);
        return true;
    }

    public VeiledResizeTaskPositioner(ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, Rect rect, DragPositioningCallbackUtility.DragStartListener dragStartListener, Supplier<SurfaceControl.Transaction> supplier, Transitions transitions) {
        this.mStableBounds = new Rect();
        this.mTaskBoundsAtDragStart = new Rect();
        this.mRepositionStartPoint = new PointF();
        this.mRepositionTaskBounds = new Rect();
        Rect rect2 = new Rect();
        this.mDisallowedAreaForEndBounds = rect2;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDesktopWindowDecoration = desktopModeWindowDecoration;
        this.mDisplayController = displayController;
        this.mDragStartListener = dragStartListener;
        rect2.set(rect);
        this.mTransactionSupplier = supplier;
        this.mTransitions = transitions;
    }
}
