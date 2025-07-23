package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.Size;
import android.view.Choreographer;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.WindowContainerTransaction;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DragResizeWindowGeometry;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CaptionWindowDecoration extends WindowDecoration {
    public final ShellExecutor mBgExecutor;
    public final Choreographer mChoreographer;
    public final DesktopConfig mDesktopConfig;
    public FluidResizeTaskPositioner mDragPositioningCallback;
    public DragResizeInputListener mDragResizeListener;
    public final Handler mHandler;
    public final ShellExecutor mMainExecutor;
    public CaptionWindowDecorViewModel.CaptionTouchEventListener mOnCaptionButtonClickListener;
    public CaptionWindowDecorViewModel.CaptionTouchEventListener mOnCaptionTouchListener;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public final WindowDecoration.RelayoutResult mResult;

    public CaptionWindowDecoration(Context context, Context context2, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, WindowDecorViewHostSupplier windowDecorViewHostSupplier, DesktopConfig desktopConfig) {
        super(context, context2, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl, windowDecorViewHostSupplier);
        this.mRelayoutParams = new WindowDecoration.RelayoutParams();
        this.mResult = new WindowDecoration.RelayoutResult();
        this.mHandler = handler;
        this.mMainExecutor = shellExecutor;
        this.mBgExecutor = shellExecutor2;
        this.mChoreographer = choreographer;
        this.mDesktopConfig = desktopConfig;
    }

    public static void updateRelayoutParams(WindowDecoration.RelayoutParams relayoutParams, Context context, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3, boolean z4, InsetsState insetsState, boolean z5, Region region, boolean z6) {
        relayoutParams.reset();
        relayoutParams.mRunningTaskInfo = runningTaskInfo;
        relayoutParams.mLayoutResId = R.layout.caption_window_decor;
        runningTaskInfo.getWindowingMode();
        relayoutParams.mCaptionHeightId = R.dimen.freeform_decor_caption_height;
        boolean isTrue = DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX.isTrue();
        int i = R.dimen.freeform_decor_shadow_unfocused_thickness;
        if (isTrue) {
            if (z5) {
                i = R.dimen.freeform_decor_shadow_focused_thickness;
            }
            relayoutParams.mShadowRadiusId = i;
        } else {
            relayoutParams.mShadowRadius = z5 ? context.getResources().getDimensionPixelSize(R.dimen.freeform_decor_shadow_focused_thickness) : context.getResources().getDimensionPixelSize(R.dimen.freeform_decor_shadow_unfocused_thickness);
        }
        relayoutParams.mApplyStartTransactionOnDraw = z;
        relayoutParams.mSetTaskVisibilityPositionAndCrop = z2;
        relayoutParams.mIsCaptionVisible = runningTaskInfo.isFreeform() || (z3 && !z4);
        relayoutParams.mDisplayExclusionRegion.set(region);
        if (TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo)) {
            relayoutParams.mInputFeatures |= 4;
        }
        WindowDecoration.RelayoutParams.OccludingCaptionElement occludingCaptionElement = new WindowDecoration.RelayoutParams.OccludingCaptionElement();
        occludingCaptionElement.mWidthResId = R.dimen.caption_left_buttons_width;
        occludingCaptionElement.mAlignment = WindowDecoration.RelayoutParams.OccludingCaptionElement.Alignment.START;
        ((ArrayList) relayoutParams.mOccludingCaptionElements).add(occludingCaptionElement);
        WindowDecoration.RelayoutParams.OccludingCaptionElement occludingCaptionElement2 = new WindowDecoration.RelayoutParams.OccludingCaptionElement();
        occludingCaptionElement2.mWidthResId = R.dimen.caption_right_buttons_width;
        occludingCaptionElement2.mAlignment = WindowDecoration.RelayoutParams.OccludingCaptionElement.Alignment.END;
        ((ArrayList) relayoutParams.mOccludingCaptionElements).add(occludingCaptionElement2);
        relayoutParams.mCaptionTopPadding = relayoutParams.mRunningTaskInfo.isFreeform() ? 0 : insetsState.calculateInsets(runningTaskInfo.getConfiguration().windowConfiguration.getBounds(), WindowInsets.Type.systemBars() & (~WindowInsets.Type.captionBar()), false).top;
        relayoutParams.mShouldSetBackground = z6;
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
    public final int getCaptionViewId() {
        return R.id.caption;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, Region region) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        relayout(runningTaskInfo, transaction, transaction, true, this.mTaskDragResizer.isResizingOrAnimating(), z, region);
    }

    public final void setCaptionColor(int i) {
        View view = this.mResult.mRootView;
        if (view == null) {
            return;
        }
        View findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption);
        ((GradientDrawable) findViewById.getBackground()).setColor(i);
        ColorStateList colorStateList = findViewById.getResources().getColorStateList(((double) Color.valueOf(i).luminance()) < 0.5d ? R.color.decor_button_light_color : R.color.decor_button_dark_color, null);
        findViewById.findViewById(R.id.back_button).setBackgroundTintList(colorStateList);
        findViewById.findViewById(R.id.minimize_window).setBackgroundTintList(colorStateList);
        findViewById.findViewById(R.id.maximize_window).setBackgroundTintList(colorStateList);
        findViewById.findViewById(R.id.close_window).setBackgroundTintList(colorStateList);
    }

    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z, boolean z2, boolean z3, Region region) {
        boolean z4;
        int dimensionPixelSize;
        boolean z5 = runningTaskInfo.getWindowingMode() == 5;
        if (DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue()) {
            z4 = z5;
        } else {
            z4 = z5 && runningTaskInfo.isResizeable;
        }
        WindowDecorLinearLayout windowDecorLinearLayout = (WindowDecorLinearLayout) this.mResult.mRootView;
        SurfaceControl surfaceControl = this.mDecorationContainerSurface;
        final WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        Context context = this.mContext;
        boolean z6 = this.mIsStatusBarVisible;
        boolean z7 = this.mIsKeyguardVisibleAndOccluded;
        InsetsState insetsState = this.mDisplayController.getInsetsState(runningTaskInfo.displayId);
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) this.mDesktopConfig;
        desktopConfigImpl.getClass();
        updateRelayoutParams(relayoutParams, context, runningTaskInfo, z, z2, z6, z7, insetsState, z3, region, runningTaskInfo.isFreeform() && (!desktopConfigImpl.isVeiledResizeEnabled || DesktopModeFlags.ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS.isTrue()));
        relayout(this.mRelayoutParams, transaction, transaction2, windowContainerTransaction, windowDecorLinearLayout, this.mResult);
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.CaptionWindowDecoration$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CaptionWindowDecoration captionWindowDecoration = CaptionWindowDecoration.this;
                captionWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        });
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
        View view2 = this.mResult.mRootView;
        if (TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo)) {
            setCaptionColor(0);
        } else {
            setCaptionColor(runningTaskInfo.taskDescription.getStatusBarColor());
        }
        view2.findViewById(R.id.maximize_window).setBackgroundResource(runningTaskInfo.getWindowingMode() == 1 ? R.drawable.decor_restore_button_dark : R.drawable.decor_maximize_button_dark);
        if (!z4) {
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
            this.mDragResizeListener = new DragResizeInputListener(this.mContext, WindowManagerGlobal.getWindowSession(), this.mMainExecutor, DesktopModeFlags.ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD.isTrue() ? this.mBgExecutor : this.mMainExecutor, this.mTaskInfo, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), this.mDecorationContainerSurface, this.mDragPositioningCallback, this.mSurfaceControlBuilderSupplier, this.mSurfaceControlTransactionSupplier, this.mDisplayController);
        }
        DragResizeInputListener dragResizeInputListener3 = this.mDragResizeListener;
        final int scaledTouchSlop = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
        Resources resources = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources();
        WindowDecoration.RelayoutResult relayoutResult = this.mResult;
        Size size = new Size(relayoutResult.mWidth, relayoutResult.mHeight);
        if (DesktopModeFlags.ENABLE_WINDOWING_EDGE_DRAG_RESIZE.isTrue()) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.freeform_edge_handle_outset);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.freeform_resize_handle);
        }
        final DragResizeWindowGeometry dragResizeWindowGeometry = new DragResizeWindowGeometry(0, size, dimensionPixelSize, resources.getDimensionPixelSize(R.dimen.freeform_edge_handle_inset), resources.getDimensionPixelSize(R.dimen.freeform_resize_corner), resources.getDimensionPixelSize(R.dimen.desktop_mode_corner_resize_large), DragResizeWindowGeometry.DisabledEdge.NONE);
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.CaptionWindowDecoration$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CaptionWindowDecoration captionWindowDecoration = CaptionWindowDecoration.this;
                captionWindowDecoration.mDragResizeListener.setGeometry(dragResizeWindowGeometry, scaledTouchSlop, true);
            }
        };
        if (dragResizeInputListener3.mInputEventReceiver != null) {
            runnable.run();
        } else {
            ((ArrayList) dragResizeInputListener3.mOnInitializedCallbacks).add(runnable);
        }
    }
}
