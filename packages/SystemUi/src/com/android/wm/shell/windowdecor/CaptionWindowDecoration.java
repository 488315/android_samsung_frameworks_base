package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptionWindowDecoration extends WindowDecoration {
    public final Choreographer mChoreographer;
    public DragDetector mDragDetector;
    public DragPositioningCallback mDragPositioningCallback;
    public DragResizeInputListener mDragResizeListener;
    public final Handler mHandler;
    public View.OnClickListener mOnCaptionButtonClickListener;
    public View.OnTouchListener mOnCaptionTouchListener;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public final WindowDecoration.RelayoutResult mResult;

    public CaptionWindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue) {
        super(context, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl);
        this.mRelayoutParams = new WindowDecoration.RelayoutParams();
        this.mResult = new WindowDecoration.RelayoutResult();
        this.mHandler = handler;
        this.mChoreographer = choreographer;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration, java.lang.AutoCloseable
    public final void close() {
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        if (dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        super.close();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        relayout(runningTaskInfo, transaction, transaction, true);
    }

    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z) {
        int i = runningTaskInfo.isFocused ? R.dimen.freeform_decor_shadow_focused_thickness : R.dimen.freeform_decor_shadow_unfocused_thickness;
        boolean z2 = (runningTaskInfo.getWindowingMode() == 5) && runningTaskInfo.isResizeable;
        WindowDecorLinearLayout windowDecorLinearLayout = (WindowDecorLinearLayout) this.mResult.mRootView;
        SurfaceControl surfaceControl = this.mDecorationContainerSurface;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        this.mRelayoutParams.reset();
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        relayoutParams.mRunningTaskInfo = runningTaskInfo;
        relayoutParams.mLayoutResId = R.layout.caption_window_decor;
        relayoutParams.mCaptionHeightId = R.dimen.freeform_decor_caption_height;
        relayoutParams.mShadowRadiusId = i;
        relayoutParams.mApplyStartTransactionOnDraw = z;
        relayout(relayoutParams, transaction, transaction2, windowContainerTransaction, windowDecorLinearLayout, this.mResult, false, false);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        View view = this.mResult.mRootView;
        if (view == null) {
            return;
        }
        if (windowDecorLinearLayout != view) {
            View findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption);
            findViewById.setOnTouchListener(this.mOnCaptionTouchListener);
            findViewById.findViewById(R.id.close_window).setOnClickListener(this.mOnCaptionButtonClickListener);
            findViewById.findViewById(R.id.back_button).setOnClickListener(this.mOnCaptionButtonClickListener);
            findViewById.findViewById(R.id.minimize_window).setOnClickListener(this.mOnCaptionButtonClickListener);
            findViewById.findViewById(R.id.maximize_window).setOnClickListener(this.mOnCaptionButtonClickListener);
        }
        if (!z2) {
            DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
            if (dragResizeInputListener == null) {
                return;
            }
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
            return;
        }
        if (surfaceControl != this.mDecorationContainerSurface || this.mDragResizeListener == null) {
            DragResizeInputListener dragResizeInputListener2 = this.mDragResizeListener;
            if (dragResizeInputListener2 != null) {
                dragResizeInputListener2.close();
                this.mDragResizeListener = null;
            }
            this.mDragResizeListener = new DragResizeInputListener(this.mContext, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), this.mDecorationContainerSurface, this.mDragPositioningCallback, this.mTaskOrganizer);
        }
        int scaledTouchSlop = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
        this.mDragDetector.mTouchSlop = scaledTouchSlop;
        int dimensionPixelSize = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDimensionPixelSize(R.dimen.freeform_resize_handle);
        int dimensionPixelSize2 = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDimensionPixelSize(R.dimen.freeform_resize_corner);
        DragResizeInputListener dragResizeInputListener3 = this.mDragResizeListener;
        WindowDecoration.RelayoutResult relayoutResult = this.mResult;
        dragResizeInputListener3.setGeometry(0, 0, 0, 0, relayoutResult.mWidth, relayoutResult.mHeight, dimensionPixelSize, dimensionPixelSize2, scaledTouchSlop, true);
    }
}
