package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.display.DisplayTopology;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DismissViewManager$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.FreeformDragPositioningController;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.freeform.FreeformAdjustImeController;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.TaskMotionController;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayVeiledResizeTaskPositioner implements TaskPositioner, Transitions.TransitionHandler, DisplayController.OnDisplaysChangedListener {
    public static final float ALPHA_FOR_WINDOW_ON_DISPLAY_WITH_CURSOR;
    public static final float ALPHA_FOR_WINDOW_ON_NON_CURSOR_DISPLAY;
    public static final long LONG_CUJ_TIMEOUT_MS;
    public static final String TAG;
    public final Context context;
    public int ctrlType;
    public final DesktopState desktopState;
    public final DesktopModeWindowDecoration desktopWindowDecoration;
    public final DisplayController displayController;
    public final Set displayIds;
    public final List dragEventListeners;
    public FreeformDragPositioningController.FreeformDragListener dragPositioningListener;
    public final FreeformAdjustImeController freeformImeController;
    public FreeformResizeGuide freeformResizeGuide;
    public final Handler handler;
    public final InteractionJankMonitor interactionJankMonitor;
    public boolean isImeAnimating;
    public boolean isImeShowing;
    public boolean isResizingOrAnimatingResize;
    public boolean isUserInteracting;
    public int minFreeformHeight;
    public int minVisibleHeight;
    public int minVisibleWidth;
    public final MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController;
    public final PointF repositionStartPoint;
    public final Rect repositionTaskBounds;
    public int rotation;
    public final Rect stableBounds;
    public final Rect stableBoundsWithIme;
    public int startDisplayId;
    public final Rect taskBoundsAtDragStart;
    public final TaskMotionController taskMotionController;
    public final ShellTaskOrganizer taskOrganizer;
    public final Rect tmpRect;
    public final Function0 transactionSupplier;
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        LONG_CUJ_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10L);
        ALPHA_FOR_WINDOW_ON_DISPLAY_WITH_CURSOR = 1.0f;
        ALPHA_FOR_WINDOW_ON_NON_CURSOR_DISPLAY = 0.7f;
        TAG = Reflection.getOrCreateKotlinClass(MultiDisplayVeiledResizeTaskPositioner.class).getSimpleName();
    }

    public MultiDisplayVeiledResizeTaskPositioner(Context context, ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, DragPositioningCallbackUtility.DragEventListener dragEventListener, Function0 function0, Transitions transitions, InteractionJankMonitor interactionJankMonitor, Handler handler, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, DesktopState desktopState, ShellExecutor shellExecutor) {
        this.context = context;
        this.taskOrganizer = shellTaskOrganizer;
        this.desktopWindowDecoration = desktopModeWindowDecoration;
        this.displayController = displayController;
        this.transactionSupplier = function0;
        this.transitions = transitions;
        this.interactionJankMonitor = interactionJankMonitor;
        this.handler = handler;
        this.multiDisplayDragMoveIndicatorController = multiDisplayDragMoveIndicatorController;
        this.desktopState = desktopState;
        ArrayList arrayList = new ArrayList();
        this.dragEventListeners = arrayList;
        this.stableBounds = new Rect();
        this.taskBoundsAtDragStart = new Rect();
        this.repositionStartPoint = new PointF();
        this.repositionTaskBounds = new Rect();
        this.displayIds = new LinkedHashSet();
        this.tmpRect = new Rect();
        this.stableBoundsWithIme = new Rect();
        this.taskMotionController = new TaskMotionController(displayController, shellTaskOrganizer, desktopModeWindowDecoration, shellExecutor, handler);
        this.freeformImeController = desktopModeWindowDecoration.mFreeformAdjustImeController;
        arrayList.add(dragEventListener);
        displayController.addDisplayWindowListener(this, -1);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void addDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        ((ArrayList) this.dragEventListeners).add(desktopTilingWindowDecoration);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void cancelTaskMotion() {
        this.taskMotionController.cancelBoundsAnimator(null, "change_transit");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        if (r5 != 1) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
    
        r6 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0026, code lost:
    
        if (r5 == 1) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0043, code lost:
    
        if (r5 != 1) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        if (r5 == 1) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0050 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0051 A[RETURN] */
    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int changeFreeformScaleIfNeeded() {
        /*
            r9 = this;
            android.graphics.Rect r0 = r9.repositionTaskBounds
            com.android.wm.shell.windowdecor.TaskMotionController r1 = r9.taskMotionController
            com.android.wm.shell.windowdecor.FreeformStashState r2 = r1.mFreeformStashState
            boolean r3 = r2.isStashed()
            r4 = -1
            if (r3 != 0) goto Lf
        Ld:
            r6 = r4
            goto L48
        Lf:
            int r3 = r1.mMinVisibleWidth
            int r5 = r1.mStashMoveThreshold
            int r3 = r3 + r5
            int r5 = r2.mAnimType
            boolean r2 = r2.isLeftStashed()
            r6 = 0
            r7 = 1
            if (r2 == 0) goto L29
            int r0 = r0.right
            if (r0 <= r3) goto L26
            if (r5 == r7) goto Ld
        L24:
            r6 = r7
            goto L48
        L26:
            if (r5 != r7) goto Ld
            goto L48
        L29:
            com.android.wm.shell.windowdecor.DesktopModeWindowDecoration r2 = r1.mWindowDecoration
            android.app.ActivityManager$RunningTaskInfo r2 = r2.mTaskInfo
            int r2 = r2.displayId
            com.android.wm.shell.common.DisplayController r8 = r1.mDisplayController
            com.android.wm.shell.common.DisplayLayout r2 = r8.getDisplayLayout(r2)
            android.graphics.Rect r8 = r1.mTmpRect
            r2.getStableBounds(r8, r6)
            int r0 = r0.left
            android.graphics.Rect r2 = r1.mTmpRect
            int r2 = r2.right
            int r2 = r2 - r3
            if (r0 >= r2) goto L46
            if (r5 == r7) goto Ld
            goto L24
        L46:
            if (r5 != r7) goto Ld
        L48:
            android.graphics.Rect r9 = r9.repositionTaskBounds
            boolean r9 = r1.scheduleAnimateScale(r6, r9)
            if (r9 == 0) goto L51
            return r6
        L51:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner.changeFreeformScaleIfNeeded():int");
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void close() {
        this.displayController.removeDisplayWindowListener(this);
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            this.isUserInteracting = false;
        }
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && isFreeformDragInNonDex()) {
            closeDragPositioningListener();
        }
        if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE) {
            closeFreeformResizeGuide();
        }
    }

    public final void closeDragPositioningListener() {
        FreeformDragPositioningController.FreeformDragListener freeformDragListener = this.dragPositioningListener;
        if (freeformDragListener != null) {
            int i = this.desktopWindowDecoration.mTaskInfo.taskId;
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner$closeDragPositioningListener$1
                @Override // java.lang.Runnable
                public final void run() {
                    MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner = MultiDisplayVeiledResizeTaskPositioner.this;
                    ActivityManager.RunningTaskInfo runningTaskInfo = multiDisplayVeiledResizeTaskPositioner.desktopWindowDecoration.mTaskInfo;
                    TaskMotionController taskMotionController = multiDisplayVeiledResizeTaskPositioner.taskMotionController;
                    synchronized (taskMotionController) {
                        try {
                            TaskMotionController.TaskMotionInfo taskMotionInfo = taskMotionController.mTaskMotionInfo;
                            if (taskMotionInfo != null) {
                                taskMotionInfo.clearAnimator();
                                taskMotionController.mTaskMotionInfo = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            DismissViewManager dismissViewManager = freeformDragListener.mDismissViewManager;
            if (dismissViewManager.mView.mIsEnterDismissButton) {
                runnable.run();
                try {
                    ActivityTaskManager.getService().removeTaskWithFlags(i, 144);
                } catch (RemoteException e) {
                    Log.w("FreeformDragPositioningController$FreeformDragListener", "Failed to removeTask in onTaskDragEnd: " + e);
                    e.printStackTrace();
                }
            }
            if (!dismissViewManager.mView.mIsEnterDismissButton) {
                MultiWindowManager.getInstance().saveFreeformBounds(i);
            }
            Objects.requireNonNull(dismissViewManager);
            dismissViewManager.mView.hide(new DismissViewManager$$ExternalSyntheticLambda0(dismissViewManager));
        }
        this.dragPositioningListener = null;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void closeFreeformResizeGuide() {
        FreeformResizeGuide freeformResizeGuide = this.freeformResizeGuide;
        if (freeformResizeGuide != null) {
            freeformResizeGuide.dismiss();
        }
        this.freeformResizeGuide = null;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void getImeStartBounds(Rect rect) {
        TaskMotionController taskMotionController = this.taskMotionController;
        if (taskMotionController.isBoundsAnimating()) {
            taskMotionController.cancelBoundsAnimator(rect, "ime");
        }
        if (!rect.isEmpty()) {
            taskMotionController.mFlingCanceled = true;
            return;
        }
        if (isResizing$1()) {
            rect.set(this.taskBoundsAtDragStart);
        } else {
            if (this.taskBoundsAtDragStart.isEmpty() || Intrinsics.areEqual(this.taskBoundsAtDragStart, this.repositionTaskBounds)) {
                return;
            }
            rect.set(this.repositionTaskBounds);
        }
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final TaskMotionController getTaskMotionController() {
        return this.taskMotionController;
    }

    public final void handleFreeformMotion(float f, float f2) {
        FreeformStashState freeformStashState;
        if (!FreeformDragPositioningController.getInstance(this.context).mFreeformDragListener.mDismissViewManager.mView.mIsEnterDismissButton) {
            TaskMotionController taskMotionController = this.taskMotionController;
            if (taskMotionController.mFreeformCaptionTouchState != null) {
                if (this.isImeShowing) {
                    taskMotionController.handleFreeformMotion(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, this.stableBoundsWithIme, f, f2);
                    return;
                } else {
                    taskMotionController.handleFreeformMotion(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, this.stableBounds, f, f2);
                    return;
                }
            }
            return;
        }
        Rect rect = this.repositionTaskBounds;
        StringBuilder sb = new StringBuilder("reposition(");
        sb.append(rect);
        sb.append(") for dismiss, ");
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.desktopWindowDecoration;
        sb.append(desktopModeWindowDecoration);
        Slog.d(TAG, sb.toString());
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && (freeformStashState = desktopModeWindowDecoration.mFreeformStashState) != null && freeformStashState.isStashed()) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, this.repositionTaskBounds);
            this.taskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final boolean isAllowTouches() {
        return this.taskMotionController.mAllowTouches;
    }

    public final boolean isFreeformDragInNonDex() {
        DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
        int i = this.desktopWindowDecoration.mTaskInfo.displayId;
        companion.getClass();
        return (DesktopStateImpl.Companion.inDesktopWindowing(i) || isResizing$1()) ? false : true;
    }

    public final boolean isResizing$1() {
        if (!CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE || this.desktopWindowDecoration.mIsTaskMaximized) {
            return false;
        }
        int i = this.ctrlType;
        return ((i & 4) == 0 && (i & 8) == 0 && (i & 1) == 0 && (i & 2) == 0) ? false : true;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final boolean isResizingOrAnimating() {
        return this.isResizingOrAnimatingResize;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final boolean isStashedAtNavigationBarPosition() {
        DisplayLayout displayLayout;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.desktopWindowDecoration;
        Display display = desktopModeWindowDecoration.mDisplay;
        if (display != null && desktopModeWindowDecoration.mDecorWindowContext != null && (displayLayout = this.displayController.getDisplayLayout(display.getDisplayId())) != null) {
            int navigationBarPosition = DisplayLayout.navigationBarPosition(desktopModeWindowDecoration.mDecorWindowContext.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
            if ((desktopModeWindowDecoration.mFreeformStashState.isLeftStashed() && navigationBarPosition == 1) || (desktopModeWindowDecoration.mFreeformStashState.mStashType == 2 && navigationBarPosition == 2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void onDragPositioningCancel() {
        FreeformDragPositioningController.FreeformDragListener freeformDragListener;
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && isFreeformDragInNonDex() && (freeformDragListener = this.dragPositioningListener) != null) {
            DismissViewManager dismissViewManager = freeformDragListener.mDismissViewManager;
            Objects.requireNonNull(dismissViewManager);
            dismissViewManager.mView.hide(new DismissViewManager$$ExternalSyntheticLambda0(dismissViewManager));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0134  */
    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Rect onDragPositioningEnd(float r15, float r16, int r17) {
        /*
            Method dump skipped, instructions count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner.onDragPositioningEnd(float, float, int):android.graphics.Rect");
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00bb  */
    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Rect onDragPositioningMove(float r25, float r26, int r27) {
        /*
            Method dump skipped, instructions count: 918
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner.onDragPositioningMove(float, float, int):android.graphics.Rect");
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningStart(int i, float f, float f2, int i2) {
        InsetsState insetsState;
        InsetsState insetsState2;
        boolean z = this.isImeAnimating;
        TaskMotionController taskMotionController = this.taskMotionController;
        if (z && taskMotionController.mAllowTouches) {
            taskMotionController.mAllowTouches = false;
        }
        boolean z2 = CoreRune.MW_CAPTION_FREEFORM_MOTION;
        if (z2) {
            if (!taskMotionController.mAllowTouches) {
                return new Rect(this.repositionTaskBounds);
            }
            this.isUserInteracting = true;
        }
        this.ctrlType = i;
        this.startDisplayId = i2;
        FreeformAdjustImeController freeformAdjustImeController = this.freeformImeController;
        boolean z3 = freeformAdjustImeController.mIsAdjusted;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.desktopWindowDecoration;
        if (z3) {
            this.taskBoundsAtDragStart.set(freeformAdjustImeController.mAdjustingBounds);
        } else if (CoreRune.MW_CAPTION_FREEFORM_STASH && taskMotionController.isBoundsAnimating()) {
            this.tmpRect.setEmpty();
            taskMotionController.cancelBoundsAnimator(this.tmpRect, "drag-touch");
            this.taskBoundsAtDragStart.set(this.tmpRect);
        } else {
            this.taskBoundsAtDragStart.set(desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
        }
        ShellTaskOrganizer shellTaskOrganizer = this.taskOrganizer;
        DisplayController displayController = this.displayController;
        if (z2 && desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
            boolean isTargetTaskImeShowing = shellTaskOrganizer.isTargetTaskImeShowing(desktopModeWindowDecoration.mTaskInfo.displayId);
            this.isImeShowing = isTargetTaskImeShowing;
            if (isTargetTaskImeShowing && (insetsState2 = displayController.getInsetsState(desktopModeWindowDecoration.mTaskInfo.displayId)) != null) {
                int statusBars = WindowInsets.Type.statusBars() | WindowInsets.Type.ime() | WindowInsets.Type.displayCutout();
                this.stableBoundsWithIme.set(insetsState2.getDisplayFrame());
                this.stableBoundsWithIme.inset(insetsState2.calculateInsets(insetsState2.getDisplayFrame(), statusBars, true));
            }
            if (taskMotionController.isBoundsAnimating()) {
                this.tmpRect.setEmpty();
                taskMotionController.cancelBoundsAnimator(this.tmpRect, "drag-touch");
                this.taskBoundsAtDragStart.set(this.tmpRect);
            }
        }
        if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE && desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
            this.minVisibleWidth = MultiWindowUtils.dipToPixel(48, desktopModeWindowDecoration.mDecorWindowContext.getResources().getDisplayMetrics());
            this.minVisibleHeight = MultiWindowUtils.dipToPixel(32, desktopModeWindowDecoration.mDecorWindowContext.getResources().getDisplayMetrics());
            if (isResizing$1()) {
                int i3 = desktopModeWindowDecoration.mTaskInfo.displayId;
                if (displayController.mDisplayManager.getDisplay(i3) == null) {
                    Slog.w(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "The display is not registered in DisplayManager: "));
                } else {
                    DesktopStateImpl.Companion.getClass();
                    boolean inDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i3);
                    FreeformResizeGuide freeformResizeGuide = new FreeformResizeGuide(desktopModeWindowDecoration.mDecorWindowContext, 0, desktopModeWindowDecoration.mTaskInfo.realActivity, inDesktopWindowing);
                    this.freeformResizeGuide = freeformResizeGuide;
                    freeformResizeGuide.setCtrlType(this.ctrlType);
                    DisplayLayout displayLayout = displayController.getDisplayLayout(i3);
                    if (displayLayout != null) {
                        displayLayout.getStableBounds(this.tmpRect, false);
                        freeformResizeGuide.updateMinMaxSizeIfNeeded(desktopModeWindowDecoration.mTaskInfo, this.tmpRect, this.taskBoundsAtDragStart.width() >= this.taskBoundsAtDragStart.height());
                        this.minFreeformHeight = freeformResizeGuide.getMinHeight();
                        if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE && !inDesktopWindowing && (insetsState = displayController.getInsetsState(i3)) != null) {
                            displayLayout.getStableBounds(this.tmpRect, false);
                            freeformResizeGuide.updateResizeGestureInfo(insetsState.getDisplayFrame(), this.tmpRect);
                        }
                    }
                }
            }
        }
        this.repositionStartPoint.set(f, f2);
        if (isResizing$1()) {
            this.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withSurface(106, desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mTaskSurface, this.handler).setTimeout(LONG_CUJ_TIMEOUT_MS));
            if (!z2 && !desktopModeWindowDecoration.mHasGlobalFocus) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.reorder(desktopModeWindowDecoration.mTaskInfo.token, true, true);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        }
        ArrayList arrayList = (ArrayList) this.dragEventListeners;
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            ((DragPositioningCallbackUtility.DragEventListener) obj).onDragStart(desktopModeWindowDecoration.mTaskInfo.taskId);
        }
        this.repositionTaskBounds.set(this.taskBoundsAtDragStart);
        int displayRotation = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getDisplayRotation();
        if (this.stableBounds.isEmpty() || this.rotation != displayRotation) {
            this.rotation = displayRotation;
            DisplayLayout displayLayout2 = displayController.getDisplayLayout(desktopModeWindowDecoration.mDisplay.getDisplayId());
            displayLayout2.getClass();
            displayLayout2.getStableBounds(this.stableBounds, false);
        }
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            if (!isResizing$1()) {
                taskMotionController.addTaskToMotionInfo(desktopModeWindowDecoration.mTaskInfo);
            }
            if (!taskMotionController.mLastReportedTaskBounds.isEmpty() && desktopModeWindowDecoration.mTaskInfo.equals(taskMotionController.mLastTaskInfo)) {
                this.taskBoundsAtDragStart.set(taskMotionController.mLastReportedTaskBounds);
            }
            taskMotionController.mLastReportedTaskBounds.setEmpty();
        }
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && isFreeformDragInNonDex()) {
            FreeformDragPositioningController.FreeformDragListener freeformDragListener = FreeformDragPositioningController.getInstance(this.context).mFreeformDragListener;
            this.dragPositioningListener = freeformDragListener;
            freeformDragListener.mTmpPoint.set(f, f2);
            DismissViewManager dismissViewManager = freeformDragListener.mDismissViewManager;
            dismissViewManager.createDismissView();
            dismissViewManager.createOrUpdateWrapper();
        }
        return new Rect(this.repositionTaskBounds);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onTopologyChanged(DisplayTopology displayTopology) {
        this.displayIds.clear();
        if (displayTopology == null) {
            return;
        }
        SparseArray absoluteBounds = displayTopology.getAbsoluteBounds();
        Set set = this.displayIds;
        int size = absoluteBounds.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(Integer.valueOf(absoluteBounds.keyAt(i)));
        }
        set.addAll(arrayList);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void removeDragEventListener(DesktopTilingWindowDecoration desktopTilingWindowDecoration) {
        ((ArrayList) this.dragEventListeners).remove(desktopTilingWindowDecoration);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void resetStashedFreeform(boolean z) {
        TaskMotionController taskMotionController = this.taskMotionController;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.desktopWindowDecoration;
        if (z) {
            Rect rect = new Rect();
            if (CoreRune.MW_CAPTION_FREEFORM_STASH && taskMotionController.mCanceled) {
                rect.set(this.repositionTaskBounds);
            } else {
                rect.set(desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
            }
            taskMotionController.scheduleAnimateRestore(rect, desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash, false);
        } else {
            Rect rect2 = desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash;
            DisplayLayout displayLayout = taskMotionController.mDisplayController.getDisplayLayout(taskMotionController.mWindowDecoration.mTaskInfo.displayId);
            if (displayLayout != null) {
                Rect rect3 = new Rect();
                Rect rect4 = new Rect();
                rect3.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                displayLayout.getStableBounds(rect4, false);
                int width = rect4.width() - (taskMotionController.mScreenEdgeInset * 2);
                if (width < rect2.width()) {
                    rect2.right = rect2.left + width;
                }
                if (taskMotionController.computeStashState(rect4, rect2, false) != 0) {
                    rect2.offsetTo((rect3.width() - rect2.width()) / 2, (rect3.height() - rect2.height()) / 2);
                }
            }
            desktopModeWindowDecoration.mFreeformStashState.setStashed(0);
            taskMotionController.setStashDim(null, false);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
            this.taskOrganizer.applyTransaction(windowContainerTransaction);
        }
        this.repositionTaskBounds.set(desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void setFreeformCaptionTouchState(FreeformCaptionTouchState freeformCaptionTouchState) {
        this.taskMotionController.mFreeformCaptionTouchState = freeformCaptionTouchState;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void setImeAnimating(boolean z) {
        this.isImeAnimating = z;
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
        if (this.isResizingOrAnimatingResize) {
            this.desktopWindowDecoration.mResizeVeil.hideVeil();
            this.isResizingOrAnimatingResize = false;
        }
        this.ctrlType = 0;
        transitionFinishCallback.onTransitionFinished(null);
        this.isResizingOrAnimatingResize = false;
        this.interactionJankMonitor.end(110);
        return true;
    }

    public MultiDisplayVeiledResizeTaskPositioner(Context context, ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, DragPositioningCallbackUtility.DragEventListener dragEventListener, Transitions transitions, InteractionJankMonitor interactionJankMonitor, Handler handler, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, DesktopState desktopState, ShellExecutor shellExecutor) {
        this(context, shellTaskOrganizer, desktopModeWindowDecoration, displayController, dragEventListener, new MultiDisplayVeiledResizeTaskPositioner$$ExternalSyntheticLambda0(), transitions, interactionJankMonitor, handler, multiDisplayDragMoveIndicatorController, desktopState, shellExecutor);
    }
}
