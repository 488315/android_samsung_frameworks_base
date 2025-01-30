package com.android.p038wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewConfiguration;
import android.window.WindowContainerTransaction;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.desktopmode.DesktopModeController;
import com.android.p038wm.shell.desktopmode.DesktopModeStatus;
import com.android.p038wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.p038wm.shell.desktopmode.DesktopTasksController;
import com.android.p038wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.p038wm.shell.windowdecor.WindowDecoration;
import com.android.p038wm.shell.windowdecor.viewholder.DesktopModeAppControlsWindowDecorationViewHolder;
import com.android.p038wm.shell.windowdecor.viewholder.DesktopModeFocusedWindowDecorationViewHolder;
import com.android.p038wm.shell.windowdecor.viewholder.DesktopModeWindowDecorationViewHolder;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeWindowDecoration extends WindowDecoration {
    public Drawable mAppIcon;
    public CharSequence mAppName;
    public final Choreographer mChoreographer;
    public DesktopModeWindowDecorViewModel.TaskCornersListenerImpl mCornersListener;
    public DragDetector mDragDetector;
    public DragPositioningCallback mDragPositioningCallback;
    public DragResizeInputListener mDragResizeListener;
    public HandleMenu mHandleMenu;
    public final Handler mHandler;
    public View.OnClickListener mOnCaptionButtonClickListener;
    public View.OnTouchListener mOnCaptionTouchListener;
    public final Point mPositionInParent;
    public int mRelayoutBlock;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public ResizeVeil mResizeVeil;
    public final WindowDecoration.RelayoutResult mResult;
    public final Set mTransitionsPausingRelayout;
    public DesktopModeWindowDecorationViewHolder mWindowDecorViewHolder;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
    }

    public DesktopModeWindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue) {
        super(context, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl);
        this.mRelayoutParams = new WindowDecoration.RelayoutParams();
        this.mResult = new WindowDecoration.RelayoutResult();
        this.mPositionInParent = new Point();
        this.mTransitionsPausingRelayout = new HashSet();
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        String packageName = this.mTaskInfo.realActivity.getPackageName();
        PackageManager packageManager = this.mContext.getApplicationContext().getPackageManager();
        try {
            IconProvider iconProvider = new IconProvider(this.mContext);
            this.mAppIcon = iconProvider.getIcon(iconProvider.mContext.getResources().getConfiguration().densityDpi, packageManager.getActivityInfo(this.mTaskInfo.baseActivity, PackageManager.ComponentInfoFlags.of(0L)));
            this.mAppName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0L)));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("DesktopModeWindowDecoration", "Package not found: " + packageName, e);
        }
    }

    public static boolean pointInView(View view, float f, float f2) {
        return view != null && ((float) view.getLeft()) <= f && ((float) view.getRight()) >= f && ((float) view.getTop()) <= f2 && ((float) view.getBottom()) >= f2;
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecoration, java.lang.AutoCloseable
    public final void close() {
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        if (dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        closeHandleMenu();
        DesktopModeWindowDecorViewModel.TaskCornersListenerImpl taskCornersListenerImpl = this.mCornersListener;
        final int i = this.mTaskInfo.taskId;
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
        final int i2 = 0;
        desktopModeWindowDecorViewModel.mDesktopModeController.ifPresent(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$TaskCornersListenerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        int i3 = i;
                        final DesktopModeTaskRepository desktopModeTaskRepository = ((DesktopModeController) obj).mDesktopModeTaskRepository;
                        desktopModeTaskRepository.desktopCorners.delete(i3);
                        Executor executor = desktopModeTaskRepository.desktopGestureExclusionExecutor;
                        if (executor != null) {
                            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$removeTaskCorners$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DesktopModeTaskRepository desktopModeTaskRepository2 = DesktopModeTaskRepository.this;
                                    Consumer consumer = desktopModeTaskRepository2.desktopGestureExclusionListener;
                                    if (consumer != null) {
                                        consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository2));
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        int i4 = i;
                        final DesktopModeTaskRepository desktopModeTaskRepository2 = ((DesktopTasksController) obj).desktopModeTaskRepository;
                        desktopModeTaskRepository2.desktopCorners.delete(i4);
                        Executor executor2 = desktopModeTaskRepository2.desktopGestureExclusionExecutor;
                        if (executor2 != null) {
                            executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$removeTaskCorners$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                    Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                    if (consumer != null) {
                                        consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                    }
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        });
        final int i3 = 1;
        desktopModeWindowDecorViewModel.mDesktopTasksController.ifPresent(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$TaskCornersListenerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i3) {
                    case 0:
                        int i32 = i;
                        final DesktopModeTaskRepository desktopModeTaskRepository = ((DesktopModeController) obj).mDesktopModeTaskRepository;
                        desktopModeTaskRepository.desktopCorners.delete(i32);
                        Executor executor = desktopModeTaskRepository.desktopGestureExclusionExecutor;
                        if (executor != null) {
                            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$removeTaskCorners$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                    Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                    if (consumer != null) {
                                        consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        int i4 = i;
                        final DesktopModeTaskRepository desktopModeTaskRepository2 = ((DesktopTasksController) obj).desktopModeTaskRepository;
                        desktopModeTaskRepository2.desktopCorners.delete(i4);
                        Executor executor2 = desktopModeTaskRepository2.desktopGestureExclusionExecutor;
                        if (executor2 != null) {
                            executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$removeTaskCorners$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                    Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                    if (consumer != null) {
                                        consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                    }
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        });
        ResizeVeil resizeVeil = this.mResizeVeil;
        if (resizeVeil != null) {
            SurfaceControlViewHost surfaceControlViewHost = resizeVeil.mViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.release();
                resizeVeil.mViewHost = null;
            }
            if (resizeVeil.mVeilSurface != null) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil.mSurfaceControlTransactionSupplier.get();
                transaction.remove(resizeVeil.mVeilSurface);
                resizeVeil.mVeilSurface = null;
                transaction.apply();
            }
            this.mResizeVeil = null;
        }
        super.close();
    }

    public final void closeHandleMenu() {
        if (isHandleMenuActive()) {
            HandleMenu handleMenu = this.mHandleMenu;
            handleMenu.mAppInfoPill.releaseView();
            handleMenu.mAppInfoPill = null;
            WindowDecoration.AdditionalWindow additionalWindow = handleMenu.mWindowingPill;
            if (additionalWindow != null) {
                additionalWindow.releaseView();
                handleMenu.mWindowingPill = null;
            }
            handleMenu.mMoreActionsPill.releaseView();
            handleMenu.mMoreActionsPill = null;
            this.mHandleMenu = null;
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecoration
    public final Configuration getConfigurationWithOverrides(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Configuration configuration = runningTaskInfo.getConfiguration();
        DesktopTasksController.Companion.getClass();
        IntRange intRange = DesktopTasksController.DESKTOP_DENSITY_ALLOWED_RANGE;
        int i = intRange.first;
        int i2 = intRange.last;
        int i3 = DesktopTasksController.DESKTOP_DENSITY_OVERRIDE;
        if (i <= i3 && i3 <= i2) {
            configuration.densityDpi = this.mContext.getResources().getConfiguration().densityDpi;
        }
        return configuration;
    }

    public final void hideResizeVeil() {
        final ResizeVeil resizeVeil = this.mResizeVeil;
        resizeVeil.getClass();
        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1.0f, 0.0f);
        valueAnimator.setDuration(100L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ResizeVeil resizeVeil2 = ResizeVeil.this;
                ValueAnimator valueAnimator3 = valueAnimator;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil2.mSurfaceControlTransactionSupplier.get();
                transaction.setAlpha(resizeVeil2.mVeilSurface, 1.0f - valueAnimator3.getAnimatedFraction());
                transaction.apply();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.ResizeVeil.1
            public C41841() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) ResizeVeil.this.mSurfaceControlTransactionSupplier.get();
                transaction.hide(ResizeVeil.this.mVeilSurface);
                transaction.apply();
            }
        });
        valueAnimator.start();
    }

    public final boolean isHandleMenuActive() {
        return this.mHandleMenu != null;
    }

    public final PointF offsetCaptionLocation(MotionEvent motionEvent) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        Point point = this.mTaskOrganizer.getRunningTaskInfo(this.mTaskInfo.taskId).positionInParent;
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        pointF.offset(-relayoutParams.mCaptionX, -relayoutParams.mCaptionY);
        pointF.offset(-point.x, -point.y);
        return pointF;
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mRelayoutBlock > 0) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        relayout(runningTaskInfo, transaction, transaction, true);
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecoration
    public final void releaseViews() {
        closeHandleMenu();
        super.releaseViews();
    }

    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z) {
        int i = runningTaskInfo.isFocused ? R.dimen.freeform_decor_shadow_focused_thickness : R.dimen.freeform_decor_shadow_unfocused_thickness;
        boolean z2 = (runningTaskInfo.getWindowingMode() == 5) && runningTaskInfo.isResizeable;
        if (isHandleMenuActive()) {
            HandleMenu handleMenu = this.mHandleMenu;
            if (handleMenu.mAppInfoPill != null) {
                handleMenu.updateHandleMenuPillPositions();
                SurfaceControl surfaceControl = handleMenu.mAppInfoPill.mWindowSurface;
                PointF pointF = handleMenu.mAppInfoPillPosition;
                transaction.setPosition(surfaceControl, pointF.x, pointF.y);
                if (DesktopModeStatus.IS_PROTO2_ENABLED) {
                    SurfaceControl surfaceControl2 = handleMenu.mWindowingPill.mWindowSurface;
                    PointF pointF2 = handleMenu.mWindowingPillPosition;
                    transaction.setPosition(surfaceControl2, pointF2.x, pointF2.y);
                }
                SurfaceControl surfaceControl3 = handleMenu.mMoreActionsPill.mWindowSurface;
                PointF pointF3 = handleMenu.mMoreActionsPillPosition;
                transaction.setPosition(surfaceControl3, pointF3.x, pointF3.y);
            }
        }
        WindowDecorLinearLayout windowDecorLinearLayout = (WindowDecorLinearLayout) this.mResult.mRootView;
        SurfaceControl surfaceControl4 = this.mDecorationContainerSurface;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        int i2 = (DesktopModeStatus.IS_SUPPORTED || runningTaskInfo.getWindowingMode() == 5) ? R.layout.desktop_mode_app_controls_window_decor : R.layout.desktop_mode_focused_window_decor;
        this.mRelayoutParams.reset();
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        relayoutParams.mRunningTaskInfo = runningTaskInfo;
        relayoutParams.mLayoutResId = i2;
        relayoutParams.mCaptionHeightId = R.dimen.freeform_decor_caption_height;
        relayoutParams.mShadowRadiusId = i;
        relayoutParams.mApplyStartTransactionOnDraw = z;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{android.R.attr.dialogCornerRadius});
        this.mRelayoutParams.mCornerRadius = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        obtainStyledAttributes.recycle();
        relayout(this.mRelayoutParams, transaction, transaction2, windowContainerTransaction, windowDecorLinearLayout, this.mResult, false, false);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        View view = this.mResult.mRootView;
        if (view == null) {
            return;
        }
        if (windowDecorLinearLayout != view) {
            int i3 = this.mRelayoutParams.mLayoutResId;
            if (i3 == R.layout.desktop_mode_focused_window_decor) {
                this.mWindowDecorViewHolder = new DesktopModeFocusedWindowDecorationViewHolder(view, this.mOnCaptionTouchListener, this.mOnCaptionButtonClickListener);
            } else if (i3 == R.layout.desktop_mode_app_controls_window_decor) {
                this.mWindowDecorViewHolder = new DesktopModeAppControlsWindowDecorationViewHolder(this.mResult.mRootView, this.mOnCaptionTouchListener, this.mOnCaptionButtonClickListener, this.mAppName, this.mAppIcon);
            } else {
                throw new IllegalArgumentException("Unexpected layout resource id");
            }
        }
        this.mWindowDecorViewHolder.bindData(this.mTaskInfo);
        if (!this.mTaskInfo.isFocused) {
            closeHandleMenu();
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
        if (surfaceControl4 != this.mDecorationContainerSurface || this.mDragResizeListener == null) {
            DragResizeInputListener dragResizeInputListener2 = this.mDragResizeListener;
            if (dragResizeInputListener2 != null) {
                dragResizeInputListener2.close();
                this.mDragResizeListener = null;
            }
            this.mDragResizeListener = new DragResizeInputListener(this.mContext, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), CoreRune.MW_CAPTION_SHELL ? this.mDragResizeInputSurface : this.mDecorationContainerSurface, this.mDragPositioningCallback, this.mTaskOrganizer);
        }
        int scaledTouchSlop = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
        this.mDragDetector.mTouchSlop = scaledTouchSlop;
        int dimensionPixelSize = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDimensionPixelSize(R.dimen.freeform_resize_handle);
        int dimensionPixelSize2 = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDimensionPixelSize(R.dimen.freeform_resize_corner);
        DragResizeInputListener dragResizeInputListener3 = this.mDragResizeListener;
        WindowDecoration.RelayoutResult relayoutResult = this.mResult;
        if (dragResizeInputListener3.setGeometry(0, 0, 0, 0, relayoutResult.mWidth, relayoutResult.mHeight, dimensionPixelSize, dimensionPixelSize2, scaledTouchSlop, true) || !this.mTaskInfo.positionInParent.equals(this.mPositionInParent)) {
            DesktopModeWindowDecorViewModel.TaskCornersListenerImpl taskCornersListenerImpl = this.mCornersListener;
            final int i4 = this.mTaskInfo.taskId;
            DragResizeInputListener dragResizeInputListener4 = this.mDragResizeListener;
            dragResizeInputListener4.getClass();
            final Region region = new Region();
            region.op(dragResizeInputListener4.mLeftTopCornerRegion, Region.Op.UNION);
            region.op(dragResizeInputListener4.mLeftBottomCornerRegion, Region.Op.UNION);
            region.op(dragResizeInputListener4.mRightTopCornerRegion, Region.Op.UNION);
            region.op(dragResizeInputListener4.mRightBottomCornerRegion, Region.Op.UNION);
            Point point = this.mPositionInParent;
            region.translate(point.x, point.y);
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
            final int i5 = 0;
            desktopModeWindowDecorViewModel.mDesktopModeController.ifPresent(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$TaskCornersListenerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i5) {
                        case 0:
                            int i6 = i4;
                            Region region2 = region;
                            final DesktopModeTaskRepository desktopModeTaskRepository = ((DesktopModeController) obj).mDesktopModeTaskRepository;
                            desktopModeTaskRepository.desktopCorners.put(i6, region2);
                            Executor executor = desktopModeTaskRepository.desktopGestureExclusionExecutor;
                            if (executor != null) {
                                executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$updateTaskCorners$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopModeTaskRepository desktopModeTaskRepository2 = DesktopModeTaskRepository.this;
                                        Consumer consumer = desktopModeTaskRepository2.desktopGestureExclusionListener;
                                        if (consumer != null) {
                                            consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository2));
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                        default:
                            int i7 = i4;
                            Region region3 = region;
                            final DesktopModeTaskRepository desktopModeTaskRepository2 = ((DesktopTasksController) obj).desktopModeTaskRepository;
                            desktopModeTaskRepository2.desktopCorners.put(i7, region3);
                            Executor executor2 = desktopModeTaskRepository2.desktopGestureExclusionExecutor;
                            if (executor2 != null) {
                                executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$updateTaskCorners$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                        Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                        if (consumer != null) {
                                            consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                    }
                }
            });
            final int i6 = 1;
            desktopModeWindowDecorViewModel.mDesktopTasksController.ifPresent(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$TaskCornersListenerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i6) {
                        case 0:
                            int i62 = i4;
                            Region region2 = region;
                            final DesktopModeTaskRepository desktopModeTaskRepository = ((DesktopModeController) obj).mDesktopModeTaskRepository;
                            desktopModeTaskRepository.desktopCorners.put(i62, region2);
                            Executor executor = desktopModeTaskRepository.desktopGestureExclusionExecutor;
                            if (executor != null) {
                                executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$updateTaskCorners$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                        Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                        if (consumer != null) {
                                            consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                        default:
                            int i7 = i4;
                            Region region3 = region;
                            final DesktopModeTaskRepository desktopModeTaskRepository2 = ((DesktopTasksController) obj).desktopModeTaskRepository;
                            desktopModeTaskRepository2.desktopCorners.put(i7, region3);
                            Executor executor2 = desktopModeTaskRepository2.desktopGestureExclusionExecutor;
                            if (executor2 != null) {
                                executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$updateTaskCorners$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopModeTaskRepository desktopModeTaskRepository22 = DesktopModeTaskRepository.this;
                                        Consumer consumer = desktopModeTaskRepository22.desktopGestureExclusionListener;
                                        if (consumer != null) {
                                            consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository22));
                                        }
                                    }
                                });
                                break;
                            }
                            break;
                    }
                }
            });
        }
        this.mPositionInParent.set(this.mTaskInfo.positionInParent);
    }
}
