package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayTopology;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.Display;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DismissViewManager$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.FreeformDragPositioningController;
import com.android.wm.shell.common.MultiDisplayDragMoveBoundsCalculator;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorSurface;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.freeform.FreeformAdjustImeController;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.TaskMotionController;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

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
    public final PointF restoreDraggedPoint;
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
        this.restoreDraggedPoint = new PointF();
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int changeFreeformScaleIfNeeded() {
        int i;
        Rect rect = this.repositionTaskBounds;
        TaskMotionController taskMotionController = this.taskMotionController;
        FreeformStashState freeformStashState = taskMotionController.mFreeformStashState;
        if (freeformStashState.isStashed()) {
            int i2 = taskMotionController.mMinVisibleWidth + taskMotionController.mStashMoveThreshold;
            int i3 = freeformStashState.mAnimType;
            i = 0;
            if (freeformStashState.isLeftStashed()) {
                if (rect.right > i2) {
                    if (i3 != 1) {
                        i = 1;
                    }
                } else if (i3 != 1) {
                }
                i = -1;
            } else {
                taskMotionController.mDisplayController.getDisplayLayout(taskMotionController.mWindowDecoration.mTaskInfo.displayId).getStableBounds(taskMotionController.mTmpRect, false);
                if (rect.left < taskMotionController.mTmpRect.right - i2) {
                    if (i3 != 1) {
                    }
                } else if (i3 != 1) {
                }
                i = -1;
            }
        } else {
            i = -1;
        }
        if (taskMotionController.scheduleAnimateScale(i, this.repositionTaskBounds)) {
            return i;
        }
        return -1;
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
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner.closeDragPositioningListener.1
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

    public final void handleFreeformMotion(float f, float f2, boolean z) {
        FreeformStashState freeformStashState;
        if (!FreeformDragPositioningController.getInstance(this.context).mFreeformDragListener.mDismissViewManager.mView.mIsEnterDismissButton) {
            TaskMotionController taskMotionController = this.taskMotionController;
            if (taskMotionController.mFreeformCaptionTouchState != null) {
                if (this.isImeShowing) {
                    taskMotionController.handleFreeformMotion(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, this.stableBoundsWithIme, Boolean.TRUE, f, f2, z);
                    return;
                } else {
                    taskMotionController.handleFreeformMotion(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, this.stableBounds, Boolean.FALSE, f, f2, z);
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
            int iNavigationBarPosition = DisplayLayout.navigationBarPosition(desktopModeWindowDecoration.mDecorWindowContext.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
            if ((desktopModeWindowDecoration.mFreeformStashState.isLeftStashed() && iNavigationBarPosition == 1) || (desktopModeWindowDecoration.mFreeformStashState.mStashType == 2 && iNavigationBarPosition == 2)) {
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

    /* JADX WARN: Removed duplicated region for block: B:42:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01f6  */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v3 */
    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect onDragPositioningEnd(float f, float f2, int i, boolean z) {
        ?? r8;
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        DesktopModeWindowDecoration desktopModeWindowDecoration2;
        boolean z2;
        FreeformResizeGuide freeformResizeGuide;
        FreeformTaskListener freeformTaskListener;
        TaskMotionController taskMotionController = this.taskMotionController;
        if (!taskMotionController.mAllowTouches || !this.isUserInteracting) {
            taskMotionController.mAllowTouches = true;
        }
        PointF pointFCalculateDelta = DragPositioningCallbackUtility.calculateDelta(f, f2, this.repositionStartPoint);
        boolean zIsResizing$1 = isResizing$1();
        DesktopModeWindowDecoration desktopModeWindowDecoration3 = this.desktopWindowDecoration;
        if (zIsResizing$1) {
            boolean zAreEqual = Intrinsics.areEqual(this.taskBoundsAtDragStart, this.repositionTaskBounds);
            ShellTaskOrganizer shellTaskOrganizer = this.taskOrganizer;
            if (zAreEqual) {
                desktopModeWindowDecoration2 = desktopModeWindowDecoration3;
                z2 = false;
                if (this.isResizingOrAnimatingResize) {
                    ResizeVeil resizeVeil = desktopModeWindowDecoration2.mResizeVeil;
                    if (resizeVeil != null) {
                        resizeVeil.hideVeil();
                    }
                    this.isResizingOrAnimatingResize = false;
                }
            } else {
                boolean z3 = CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE;
                if (z3 && (freeformResizeGuide = this.freeformResizeGuide) != null && freeformResizeGuide.canResizeGesture()) {
                    DesktopStateImpl.Companion.getClass();
                    if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                        FreeformResizeGuide freeformResizeGuide2 = this.freeformResizeGuide;
                        if (freeformResizeGuide2 != null) {
                            if (freeformResizeGuide2.readyToMinimize()) {
                                MultiWindowManager multiWindowManager = MultiWindowManager.getInstance();
                                int i2 = desktopModeWindowDecoration3.mTaskInfo.taskId;
                                Rect rect = this.repositionTaskBounds;
                                int iWidth = (rect.width() / 2) + rect.left;
                                Rect rect2 = this.repositionTaskBounds;
                                multiWindowManager.minimizeTaskToSpecificPosition(i2, false, iWidth, (rect2.height() / 2) + rect2.top);
                                if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE_SA_LOGGING) {
                                    CoreSaLogger.logForAdvanced("2016");
                                }
                            } else if (freeformResizeGuide2.needToFullscreenTransition()) {
                                ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration3.mTaskInfo;
                                shellTaskOrganizer.getClass();
                                if (z3 && runningTaskInfo.isFreeform() && (freeformTaskListener = (FreeformTaskListener) shellTaskOrganizer.mTaskListeners.get(-5)) != null) {
                                    WindowDecorViewModel windowDecorViewModel = freeformTaskListener.mWindowDecorationViewModel;
                                    if (windowDecorViewModel instanceof DesktopModeWindowDecorViewModel) {
                                        ((DesktopModeWindowDecorViewModel) windowDecorViewModel).mTaskOperations.maximizeTask(runningTaskInfo, runningTaskInfo.getWindowingMode());
                                    }
                                }
                                if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE_SA_LOGGING) {
                                    CoreSaLogger.logForAdvanced("2015");
                                    CoreSaLogger.logForAdvanced("2090", "From popup resizing");
                                }
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        desktopModeWindowDecoration2 = desktopModeWindowDecoration3;
                        z2 = false;
                    }
                    if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                    }
                } else {
                    desktopModeWindowDecoration2 = desktopModeWindowDecoration3;
                    z2 = false;
                    DragPositioningCallbackUtility.changeBounds(this.ctrlType, this.repositionTaskBounds, this.taskBoundsAtDragStart, this.stableBounds, pointFCalculateDelta, this.displayController, this.desktopWindowDecoration, ((DesktopStateImpl) this.desktopState).canEnterDesktopMode);
                    if (this.isImeShowing) {
                        int i3 = this.repositionTaskBounds.bottom;
                        Rect rect3 = this.stableBoundsWithIme;
                        if (i3 > rect3.bottom && rect3.height() > this.minFreeformHeight) {
                            this.repositionTaskBounds.bottom = this.stableBoundsWithIme.bottom;
                        }
                    }
                    Transitions transitions = this.transitions;
                    if (z3) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, this.repositionTaskBounds);
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration2.mTaskInfo;
                        windowContainerTransaction.setChangeTransitMode(runningTaskInfo2.token, 1, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(runningTaskInfo2.taskId, "freeform_resize(#", ")"));
                        transitions.mChangeTransitProvider.startChangeTransition(windowContainerTransaction);
                    } else {
                        desktopModeWindowDecoration2.updateResizeVeil(this.repositionTaskBounds);
                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, this.repositionTaskBounds);
                        transitions.startTransition(6, windowContainerTransaction2, this);
                    }
                    if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                        DesktopStateImpl.Companion.getClass();
                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash.set(this.repositionTaskBounds);
                        }
                    }
                }
            }
            if (CoreRune.MW_CAPTION_FREEFORM_MOTION && !desktopModeWindowDecoration2.mHasGlobalFocus) {
                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                windowContainerTransaction3.reorder(desktopModeWindowDecoration2.mTaskInfo.token, true, true);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction3);
            }
            this.interactionJankMonitor.end(106);
            r8 = z2;
        } else {
            r8 = 0;
            int i4 = this.startDisplayId;
            DisplayController displayController = this.displayController;
            DisplayLayout displayLayout = displayController.getDisplayLayout(i4);
            DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
            boolean z4 = CoreRune.MW_CAPTION_FREEFORM_MOTION;
            if (!z4 || (taskMotionController.mAllowTouches && this.isUserInteracting)) {
                if (this.startDisplayId == i || displayLayout == null || displayLayout2 == null) {
                    desktopModeWindowDecoration = desktopModeWindowDecoration3;
                    if (z4) {
                        handleFreeformMotion(f, f2, z);
                    } else {
                        DragPositioningCallbackUtility.updateTaskBounds(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, f, f2);
                    }
                } else {
                    if (z4) {
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.getDesktopExternalDisplayMode() != DesktopStateImpl.DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_EXTENDED) {
                            handleFreeformMotion(f, f2, z);
                        }
                    }
                    MultiDisplayDragMoveBoundsCalculator multiDisplayDragMoveBoundsCalculator = MultiDisplayDragMoveBoundsCalculator.INSTANCE;
                    PointF pointF = this.repositionStartPoint;
                    Rect rect4 = this.taskBoundsAtDragStart;
                    multiDisplayDragMoveBoundsCalculator.getClass();
                    desktopModeWindowDecoration = desktopModeWindowDecoration3;
                    this.repositionTaskBounds.set(MultiDisplayDragMoveBoundsCalculator.convertGlobalDpToLocalPxForRect(MultiDisplayDragMoveBoundsCalculator.calculateGlobalDpBoundsForDrag(displayLayout, pointF, rect4, displayLayout2, f, f2), displayLayout2));
                }
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.getDesktopExternalDisplayMode() == DesktopStateImpl.DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_EXTENDED) {
                    final int i5 = desktopModeWindowDecoration.mTaskInfo.taskId;
                    final MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController = this.multiDisplayDragMoveIndicatorController;
                    multiDisplayDragMoveIndicatorController.getClass();
                    final Function0 function0 = this.transactionSupplier;
                    multiDisplayDragMoveIndicatorController.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController$onDragEnd$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Collection<MultiDisplayDragMoveIndicatorSurface> collectionValues;
                            Map map = (Map) multiDisplayDragMoveIndicatorController.dragIndicators.remove(Integer.valueOf(i5));
                            if (map == null || (collectionValues = map.values()) == null) {
                                return;
                            }
                            if (collectionValues.isEmpty()) {
                                collectionValues = null;
                            }
                            if (collectionValues != null) {
                                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) function0.invoke();
                                for (MultiDisplayDragMoveIndicatorSurface multiDisplayDragMoveIndicatorSurface : collectionValues) {
                                    SurfaceControl surfaceControl = multiDisplayDragMoveIndicatorSurface.surface;
                                    if (surfaceControl != null) {
                                        transaction.remove(surfaceControl);
                                    }
                                    multiDisplayDragMoveIndicatorSurface.surface = null;
                                }
                                transaction.apply();
                            }
                        }
                    });
                }
                this.interactionJankMonitor.end(110);
            } else {
                taskMotionController.mAllowTouches = true;
            }
            desktopModeWindowDecoration = desktopModeWindowDecoration3;
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.getDesktopExternalDisplayMode() == DesktopStateImpl.DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_EXTENDED) {
            }
            this.interactionJankMonitor.end(110);
        }
        this.ctrlType = r8;
        this.taskBoundsAtDragStart.setEmpty();
        this.repositionStartPoint.set(0.0f, 0.0f);
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            this.isUserInteracting = r8;
        }
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && isFreeformDragInNonDex()) {
            closeDragPositioningListener();
        }
        if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE) {
            closeFreeformResizeGuide();
        }
        return new Rect(this.repositionTaskBounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x016f  */
    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect onDragPositioningMove(float f, float f2, int i) {
        final int i2;
        FreeformStashState freeformStashState;
        float f3;
        FreeformDragPositioningController.FreeformDragListener freeformDragListener;
        FreeformResizeGuide freeformResizeGuide;
        int i3;
        double dMax;
        int i4;
        double dMax2;
        int i5;
        boolean z;
        int i6;
        float f4 = f;
        float f5 = f2;
        Looper looperMyLooper = Looper.myLooper();
        Handler handler = this.handler;
        if (!Intrinsics.areEqual(looperMyLooper, handler.getLooper())) {
            throw new IllegalStateException("This method must run on the shell main thread.");
        }
        FreeformAdjustImeController freeformAdjustImeController = this.freeformImeController;
        if (freeformAdjustImeController.mIsAdjusted && (isResizing$1() || this.isImeShowing)) {
            freeformAdjustImeController.resetState();
        }
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION && (!this.taskMotionController.mAllowTouches || !this.isUserInteracting)) {
            return new Rect(this.repositionTaskBounds);
        }
        PointF pointFCalculateDelta = DragPositioningCallbackUtility.calculateDelta(f4, f5, this.repositionStartPoint);
        if (!CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE || (freeformResizeGuide = this.freeformResizeGuide) == null) {
            boolean zIsResizing$1 = isResizing$1();
            DesktopModeWindowDecoration desktopModeWindowDecoration = this.desktopWindowDecoration;
            if (zIsResizing$1) {
                if (DragPositioningCallbackUtility.changeBounds(this.ctrlType, this.repositionTaskBounds, this.taskBoundsAtDragStart, this.stableBounds, pointFCalculateDelta, this.displayController, desktopModeWindowDecoration, ((DesktopStateImpl) this.desktopState).canEnterDesktopMode)) {
                    if (this.isResizingOrAnimatingResize) {
                        desktopModeWindowDecoration.updateResizeVeil(this.repositionTaskBounds);
                    } else {
                        ArrayList arrayList = (ArrayList) this.dragEventListeners;
                        int size = arrayList.size();
                        int i7 = 0;
                        while (i7 < size) {
                            Object obj = arrayList.get(i7);
                            i7++;
                            ((DragPositioningCallbackUtility.DragEventListener) obj).onDragMove(desktopModeWindowDecoration.mTaskInfo.taskId);
                        }
                        desktopModeWindowDecoration.showResizeVeil(this.repositionTaskBounds);
                        this.isResizingOrAnimatingResize = true;
                    }
                } else if (this.ctrlType == 0) {
                    InteractionJankMonitor interactionJankMonitor = this.interactionJankMonitor;
                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.desktopWindowDecoration;
                    interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withSurface(110, desktopModeWindowDecoration2.mContext, desktopModeWindowDecoration2.mTaskSurface, handler).setTimeout(LONG_CUJ_TIMEOUT_MS));
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.invoke();
                    int i8 = this.startDisplayId;
                    DisplayController displayController = this.displayController;
                    DisplayLayout displayLayout = displayController.getDisplayLayout(i8);
                    DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
                    boolean zIsTargetTaskImeShowing = this.taskOrganizer.isTargetTaskImeShowing(i);
                    this.isImeShowing = zIsTargetTaskImeShowing;
                    if (zIsTargetTaskImeShowing) {
                        InsetsState insetsState = displayController.getInsetsState(desktopModeWindowDecoration2.mTaskInfo.displayId);
                        if (insetsState != null) {
                            int iStatusBars = WindowInsets.Type.statusBars() | WindowInsets.Type.ime() | WindowInsets.Type.displayCutout();
                            this.stableBoundsWithIme.set(insetsState.getDisplayFrame());
                            this.stableBoundsWithIme.inset(insetsState.calculateInsets(insetsState.getDisplayFrame(), iStatusBars, true));
                        }
                        DragPositioningCallbackUtility.updateTaskBounds(this.stableBoundsWithIme, Boolean.TRUE, this.repositionTaskBounds, this.taskBoundsAtDragStart, desktopModeWindowDecoration.getOutlineCaptionHeight(), this.repositionStartPoint, f, f2);
                        transaction.setPosition(desktopModeWindowDecoration.mTaskSurface, r3.left, r3.top);
                    } else if (displayLayout == null || displayLayout2 == null) {
                        f4 = f;
                        f5 = f2;
                        DragPositioningCallbackUtility.updateTaskBounds(this.repositionTaskBounds, this.taskBoundsAtDragStart, this.repositionStartPoint, f4, f5);
                        transaction.setPosition(desktopModeWindowDecoration2.mTaskSurface, r1.left, r1.top);
                        transaction.setFrameTimeline(Choreographer.getInstance().getVsyncId());
                        transaction.apply();
                    } else {
                        MultiDisplayDragMoveBoundsCalculator multiDisplayDragMoveBoundsCalculator = MultiDisplayDragMoveBoundsCalculator.INSTANCE;
                        PointF pointF = this.repositionStartPoint;
                        Rect rect = this.taskBoundsAtDragStart;
                        multiDisplayDragMoveBoundsCalculator.getClass();
                        final RectF rectFCalculateGlobalDpBoundsForDrag = MultiDisplayDragMoveBoundsCalculator.calculateGlobalDpBoundsForDrag(displayLayout, pointF, rect, displayLayout2, f, f2);
                        boolean z2 = CoreRune.MW_CAPTION_FREEFORM_STASH;
                        if (z2) {
                            Rect rect2 = this.repositionTaskBounds;
                            PointF pointFGlobalDpToLocalPx = displayLayout.globalDpToLocalPx(Float.valueOf(rectFCalculateGlobalDpBoundsForDrag.left), Float.valueOf(rectFCalculateGlobalDpBoundsForDrag.top));
                            PointF pointFGlobalDpToLocalPx2 = displayLayout.globalDpToLocalPx(Float.valueOf(rectFCalculateGlobalDpBoundsForDrag.right), Float.valueOf(rectFCalculateGlobalDpBoundsForDrag.bottom));
                            rect2.set(new Rect((int) Math.rint(pointFGlobalDpToLocalPx.x), (int) Math.rint(pointFGlobalDpToLocalPx.y), (int) Math.rint(pointFGlobalDpToLocalPx2.x), (int) Math.rint(pointFGlobalDpToLocalPx2.y)));
                        } else {
                            this.repositionTaskBounds.set(MultiDisplayDragMoveBoundsCalculator.convertGlobalDpToLocalPxForRect(rectFCalculateGlobalDpBoundsForDrag, displayLayout));
                        }
                        if (CoreRune.MW_CAPTION_FREEFORM) {
                            DesktopStateImpl.Companion.getClass();
                            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                                int iMax = Math.max(this.repositionTaskBounds.top, this.stableBounds.top);
                                Rect rect3 = this.repositionTaskBounds;
                                rect3.offsetTo(rect3.left, iMax);
                            }
                        }
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.getDesktopExternalDisplayMode() == DesktopStateImpl.DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_EXTENDED) {
                            final int i9 = this.startDisplayId;
                            final SurfaceControl surfaceControl = desktopModeWindowDecoration.mTaskSurface;
                            final ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                            final Set set = this.displayIds;
                            final MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController = this.multiDisplayDragMoveIndicatorController;
                            multiDisplayDragMoveIndicatorController.getClass();
                            final Function0 function0 = this.transactionSupplier;
                            i2 = i;
                            multiDisplayDragMoveIndicatorController.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController$onDragMove$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DisplayLayout displayLayout3;
                                    Context displayContext;
                                    DisplayLayout displayLayout4 = multiDisplayDragMoveIndicatorController.displayController.getDisplayLayout(i9);
                                    if (displayLayout4 != null) {
                                        int i10 = displayLayout4.mDensityDpi;
                                        Iterator it = set.iterator();
                                        while (it.hasNext()) {
                                            int iIntValue = ((Number) it.next()).intValue();
                                            if (iIntValue != i9 && (displayLayout3 = multiDisplayDragMoveIndicatorController.displayController.getDisplayLayout(iIntValue)) != null && (displayContext = multiDisplayDragMoveIndicatorController.displayController.getDisplayContext(iIntValue)) != null) {
                                                MultiDisplayDragMoveIndicatorSurface.Visibility visibility = RectF.intersects(new RectF(rectFCalculateGlobalDpBoundsForDrag), displayLayout3.mGlobalBoundsDp) ? iIntValue == i2 ? MultiDisplayDragMoveIndicatorSurface.Visibility.VISIBLE : MultiDisplayDragMoveIndicatorSurface.Visibility.TRANSLUCENT : MultiDisplayDragMoveIndicatorSurface.Visibility.INVISIBLE;
                                                Map map = (Map) ((LinkedHashMap) multiDisplayDragMoveIndicatorController.dragIndicators).get(Integer.valueOf(runningTaskInfo.taskId));
                                                if ((map != null && map.containsKey(Integer.valueOf(iIntValue))) || visibility != MultiDisplayDragMoveIndicatorSurface.Visibility.INVISIBLE) {
                                                    MultiDisplayDragMoveBoundsCalculator multiDisplayDragMoveBoundsCalculator2 = MultiDisplayDragMoveBoundsCalculator.INSTANCE;
                                                    RectF rectF = rectFCalculateGlobalDpBoundsForDrag;
                                                    multiDisplayDragMoveBoundsCalculator2.getClass();
                                                    Rect rectConvertGlobalDpToLocalPxForRect = MultiDisplayDragMoveBoundsCalculator.convertGlobalDpToLocalPxForRect(rectF, displayLayout3);
                                                    Map map2 = multiDisplayDragMoveIndicatorController.dragIndicators;
                                                    Integer numValueOf = Integer.valueOf(runningTaskInfo.taskId);
                                                    LinkedHashMap linkedHashMap = (LinkedHashMap) map2;
                                                    Object linkedHashMap2 = linkedHashMap.get(numValueOf);
                                                    if (linkedHashMap2 == null) {
                                                        linkedHashMap2 = new LinkedHashMap();
                                                        linkedHashMap.put(numValueOf, linkedHashMap2);
                                                    }
                                                    Map map3 = (Map) linkedHashMap2;
                                                    MultiDisplayDragMoveIndicatorSurface multiDisplayDragMoveIndicatorSurface = (MultiDisplayDragMoveIndicatorSurface) map3.get(Integer.valueOf(iIntValue));
                                                    if (multiDisplayDragMoveIndicatorSurface != null) {
                                                        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) function0.invoke();
                                                        multiDisplayDragMoveIndicatorSurface.relayout(rectConvertGlobalDpToLocalPxForRect, transaction2, visibility);
                                                        transaction2.apply();
                                                    } else {
                                                        MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController2 = multiDisplayDragMoveIndicatorController;
                                                        SurfaceControl surfaceControl2 = surfaceControl;
                                                        Function0 function02 = function0;
                                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                                                        multiDisplayDragMoveIndicatorController2.indicatorSurfaceFactory.getClass();
                                                        MultiDisplayDragMoveIndicatorSurface multiDisplayDragMoveIndicatorSurface2 = new MultiDisplayDragMoveIndicatorSurface(displayContext, surfaceControl2);
                                                        SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) function02.invoke();
                                                        float f6 = displayLayout3.mDensityDpi / i10;
                                                        SurfaceControl surfaceControl3 = multiDisplayDragMoveIndicatorSurface2.surface;
                                                        if (surfaceControl3 == null) {
                                                            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "Cannot show drag indicator for Task %d on Display %d because indicator surface is null.", new Object[]{Integer.valueOf(runningTaskInfo2.taskId), Integer.valueOf(iIntValue)});
                                                        } else {
                                                            multiDisplayDragMoveIndicatorController2.rootTaskDisplayAreaOrganizer.reparentToDisplayArea(iIntValue, transaction3, surfaceControl3);
                                                            multiDisplayDragMoveIndicatorSurface2.relayout(rectConvertGlobalDpToLocalPxForRect, transaction3, visibility);
                                                            transaction3.show(surfaceControl3).setLayer(surfaceControl3, VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS).setScale(surfaceControl3, f6, f6);
                                                            transaction3.apply();
                                                        }
                                                        map3.put(Integer.valueOf(iIntValue), multiDisplayDragMoveIndicatorSurface2);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            i2 = i;
                        }
                        if (z2 && (freeformStashState = desktopModeWindowDecoration.mFreeformStashState) != null && freeformStashState.isLeftStashed()) {
                            Rect rect4 = this.repositionTaskBounds;
                            int i10 = rect4.left;
                            FreeformStashState freeformStashState2 = desktopModeWindowDecoration.mFreeformStashState;
                            int iWidth = rect4.width();
                            if (freeformStashState2.isLeftStashed()) {
                                float f6 = iWidth;
                                f3 = f6 - (freeformStashState2.mScale * f6);
                            } else {
                                f3 = 0.0f;
                            }
                            transaction.setPosition(desktopModeWindowDecoration.mTaskSurface, i10 + ((int) f3), this.repositionTaskBounds.top);
                        } else {
                            SurfaceControl surfaceControl2 = desktopModeWindowDecoration.mTaskSurface;
                            Rect rect5 = this.repositionTaskBounds;
                            transaction.setPosition(surfaceControl2, rect5.left, rect5.top);
                        }
                        transaction.setAlpha(desktopModeWindowDecoration.mTaskSurface, this.startDisplayId == i2 ? ALPHA_FOR_WINDOW_ON_DISPLAY_WITH_CURSOR : ALPHA_FOR_WINDOW_ON_NON_CURSOR_DISPLAY).getClass();
                    }
                    f4 = f;
                    f5 = f2;
                    transaction.setFrameTimeline(Choreographer.getInstance().getVsyncId());
                    transaction.apply();
                }
            }
        } else {
            int iRound = Math.round(f4 - this.repositionStartPoint.x);
            int iRound2 = Math.round(f5 - this.repositionStartPoint.y);
            this.repositionTaskBounds.set(this.taskBoundsAtDragStart);
            Rect rect6 = this.repositionTaskBounds;
            int i11 = rect6.left;
            int i12 = rect6.top;
            int i13 = rect6.right;
            int i14 = rect6.bottom;
            int i15 = i13 - i11;
            int i16 = i14 - i12;
            int i17 = this.ctrlType;
            if ((i17 & 1) != 0) {
                i3 = iRound2;
                dMax = Math.max(this.minVisibleWidth, i15 - iRound);
            } else {
                i3 = iRound2;
                if ((i17 & 2) != 0) {
                    dMax = Math.max(this.minVisibleWidth, i15 + iRound);
                }
                i4 = this.ctrlType;
                if ((i4 & 4) == 0) {
                    dMax2 = Math.max(this.minVisibleHeight, i16 - i3);
                } else {
                    if ((i4 & 8) != 0) {
                        dMax2 = Math.max(this.minVisibleHeight, i16 + i3);
                    }
                    i5 = this.ctrlType;
                    if ((i5 & 1) != 0) {
                        i11 = i13 - i15;
                    } else {
                        i13 = i11 + i15;
                    }
                    if ((i5 & 4) != 0) {
                        i12 = i14 - i16;
                    } else {
                        i14 = i12 + i16;
                    }
                    if (this.isImeShowing && this.stableBoundsWithIme.height() > this.minFreeformHeight && i14 > (i6 = this.stableBoundsWithIme.bottom)) {
                        i14 = i6;
                    }
                    this.repositionTaskBounds.set(i11, i12, i13, i14);
                    z = CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE;
                    if (z) {
                        DesktopStateImpl.Companion.getClass();
                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            freeformResizeGuide.setNotAdjustedBounds(this.repositionTaskBounds);
                        }
                    }
                    freeformResizeGuide.adjustMinMaxSize(this.repositionTaskBounds);
                    if (z) {
                        DesktopStateImpl.Companion.getClass();
                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            freeformResizeGuide.handleResizeGesture(this.repositionTaskBounds, (int) f4, (int) f5);
                        }
                    }
                    this.tmpRect.set(this.repositionTaskBounds);
                    freeformResizeGuide.show(this.tmpRect);
                }
                i16 = (int) dMax2;
                i5 = this.ctrlType;
                if ((i5 & 1) != 0) {
                }
                if ((i5 & 4) != 0) {
                }
                if (this.isImeShowing) {
                    i14 = i6;
                }
                this.repositionTaskBounds.set(i11, i12, i13, i14);
                z = CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE;
                if (z) {
                }
                freeformResizeGuide.adjustMinMaxSize(this.repositionTaskBounds);
                if (z) {
                }
                this.tmpRect.set(this.repositionTaskBounds);
                freeformResizeGuide.show(this.tmpRect);
            }
            i15 = (int) dMax;
            i4 = this.ctrlType;
            if ((i4 & 4) == 0) {
            }
            i16 = (int) dMax2;
            i5 = this.ctrlType;
            if ((i5 & 1) != 0) {
            }
            if ((i5 & 4) != 0) {
            }
            if (this.isImeShowing) {
            }
            this.repositionTaskBounds.set(i11, i12, i13, i14);
            z = CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE;
            if (z) {
            }
            freeformResizeGuide.adjustMinMaxSize(this.repositionTaskBounds);
            if (z) {
            }
            this.tmpRect.set(this.repositionTaskBounds);
            freeformResizeGuide.show(this.tmpRect);
        }
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && isFreeformDragInNonDex() && (freeformDragListener = this.dragPositioningListener) != null) {
            freeformDragListener.mTmpPoint.set(f4, f5);
            PointF pointF2 = freeformDragListener.mTmpPoint;
            Insets insets = Insets.NONE;
            DismissViewManager dismissViewManager = freeformDragListener.mDismissViewManager;
            if (!dismissViewManager.isAttachedToWindow()) {
                dismissViewManager.createDismissView();
                dismissViewManager.createOrUpdateWrapper();
            }
            if (dismissViewManager.getVisibility() != 0) {
                dismissViewManager.show();
            }
            dismissViewManager.updateDismissTargetView(pointF2);
        }
        return new Rect(this.repositionTaskBounds);
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
            boolean zIsTargetTaskImeShowing = shellTaskOrganizer.isTargetTaskImeShowing(desktopModeWindowDecoration.mTaskInfo.displayId);
            this.isImeShowing = zIsTargetTaskImeShowing;
            if (zIsTargetTaskImeShowing && (insetsState2 = displayController.getInsetsState(desktopModeWindowDecoration.mTaskInfo.displayId)) != null) {
                int iStatusBars = WindowInsets.Type.statusBars() | WindowInsets.Type.ime() | WindowInsets.Type.displayCutout();
                this.stableBoundsWithIme.set(insetsState2.getDisplayFrame());
                this.stableBoundsWithIme.inset(insetsState2.calculateInsets(insetsState2.getDisplayFrame(), iStatusBars, true));
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
                    boolean zInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i3);
                    FreeformResizeGuide freeformResizeGuide = new FreeformResizeGuide(desktopModeWindowDecoration.mDecorWindowContext, 0, desktopModeWindowDecoration.mTaskInfo.realActivity, zInDesktopWindowing);
                    this.freeformResizeGuide = freeformResizeGuide;
                    freeformResizeGuide.setCtrlType(this.ctrlType);
                    DisplayLayout displayLayout = displayController.getDisplayLayout(i3);
                    if (displayLayout != null) {
                        displayLayout.getStableBounds(this.tmpRect, false);
                        freeformResizeGuide.updateMinMaxSizeIfNeeded(desktopModeWindowDecoration.mTaskInfo, this.tmpRect, this.taskBoundsAtDragStart.width() >= this.taskBoundsAtDragStart.height(), zInDesktopWindowing);
                        this.minFreeformHeight = freeformResizeGuide.getMinHeight();
                        if (CoreRune.MW_CAPTION_FREEFORM_RESIZE_GESTURE && !zInDesktopWindowing && (insetsState = displayController.getInsetsState(i3)) != null) {
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
    public final void playMaximizedTaskRestoreAnimation(Rect rect, final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda3) {
        final Rect rect2 = new Rect(this.repositionTaskBounds);
        final int i = rect.left - rect2.left;
        final int i2 = rect2.right - rect.right;
        final int iHeight = rect2.height() - rect.height();
        final ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(300L);
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner$playMaximizedTaskRestoreAnimation$dragToDesktopAnimator$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                onAnimationFinished();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                onAnimationFinished();
            }

            public final void onAnimationFinished() {
                MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner = this.this$0;
                multiDisplayVeiledResizeTaskPositioner.taskBoundsAtDragStart.set(multiDisplayVeiledResizeTaskPositioner.repositionTaskBounds);
                PointF pointF = multiDisplayVeiledResizeTaskPositioner.repositionStartPoint;
                PointF pointF2 = multiDisplayVeiledResizeTaskPositioner.restoreDraggedPoint;
                pointF.set(pointF2.x, pointF2.y);
                DesktopModeWindowDecoration desktopModeWindowDecoration = multiDisplayVeiledResizeTaskPositioner.desktopWindowDecoration;
                ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
                if (resizeVeil != null) {
                    resizeVeil.hideVeil();
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, multiDisplayVeiledResizeTaskPositioner.repositionTaskBounds);
                multiDisplayVeiledResizeTaskPositioner.taskOrganizer.applyTransaction(windowContainerTransaction);
                desktopModeWindowDecorViewModel$$ExternalSyntheticLambda3.run();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner = this.this$0;
                multiDisplayVeiledResizeTaskPositioner.desktopWindowDecoration.showResizeVeil(multiDisplayVeiledResizeTaskPositioner.repositionTaskBounds);
            }
        });
        final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.invoke();
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MultiDisplayVeiledResizeTaskPositioner$playMaximizedTaskRestoreAnimation$dragToDesktopAnimator$1$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.this$0.desktopWindowDecoration.mTaskSurface.isValid()) {
                    float fFloatValue = ((Float) duration.getAnimatedValue()).floatValue() * i;
                    float fFloatValue2 = ((Float) duration.getAnimatedValue()).floatValue() * i2;
                    float fFloatValue3 = ((Float) duration.getAnimatedValue()).floatValue() * iHeight;
                    Rect rect3 = rect2;
                    MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner = this.this$0;
                    Rect rect4 = multiDisplayVeiledResizeTaskPositioner.taskBoundsAtDragStart;
                    PointF pointF = multiDisplayVeiledResizeTaskPositioner.repositionStartPoint;
                    PointF pointF2 = multiDisplayVeiledResizeTaskPositioner.restoreDraggedPoint;
                    DragPositioningCallbackUtility.updateTaskBounds(rect3, rect4, pointF, pointF2.x, pointF2.y);
                    this.this$0.repositionTaskBounds.set((int) (r3.left + fFloatValue), rect2.top, (int) (r3.right - fFloatValue2), (int) (r3.bottom - fFloatValue3));
                    MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner2 = this.this$0;
                    multiDisplayVeiledResizeTaskPositioner2.desktopWindowDecoration.updateResizeVeil(multiDisplayVeiledResizeTaskPositioner2.repositionTaskBounds);
                    SurfaceControl.Transaction transaction2 = transaction;
                    MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner3 = this.this$0;
                    SurfaceControl surfaceControl = multiDisplayVeiledResizeTaskPositioner3.desktopWindowDecoration.mTaskSurface;
                    Rect rect5 = multiDisplayVeiledResizeTaskPositioner3.repositionTaskBounds;
                    SurfaceControl.Transaction position = transaction2.setPosition(surfaceControl, rect5.left, rect5.top);
                    MultiDisplayVeiledResizeTaskPositioner multiDisplayVeiledResizeTaskPositioner4 = this.this$0;
                    position.setWindowCrop(multiDisplayVeiledResizeTaskPositioner4.desktopWindowDecoration.mTaskSurface, multiDisplayVeiledResizeTaskPositioner4.repositionTaskBounds.width(), this.this$0.repositionTaskBounds.height()).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
                }
            }
        });
        duration.start();
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
                int iWidth = rect4.width() - (taskMotionController.mScreenEdgeInset * 2);
                if (iWidth < rect2.width()) {
                    rect2.right = rect2.left + iWidth;
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
            ResizeVeil resizeVeil = this.desktopWindowDecoration.mResizeVeil;
            if (resizeVeil != null) {
                resizeVeil.hideVeil();
            }
            this.isResizingOrAnimatingResize = false;
        }
        this.ctrlType = 0;
        transitionFinishCallback.onTransitionFinished(null);
        this.isResizingOrAnimatingResize = false;
        this.interactionJankMonitor.end(110);
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.TaskPositioner
    public final void updateRestoreAnimationMotionEvent(MotionEvent motionEvent) {
        this.restoreDraggedPoint.x = motionEvent.getRawX();
        this.restoreDraggedPoint.y = motionEvent.getRawY();
    }

    public MultiDisplayVeiledResizeTaskPositioner(Context context, ShellTaskOrganizer shellTaskOrganizer, DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, DragPositioningCallbackUtility.DragEventListener dragEventListener, Transitions transitions, InteractionJankMonitor interactionJankMonitor, Handler handler, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, DesktopState desktopState, ShellExecutor shellExecutor) {
        this(context, shellTaskOrganizer, desktopModeWindowDecoration, displayController, dragEventListener, new MultiDisplayVeiledResizeTaskPositioner$$ExternalSyntheticLambda0(), transitions, interactionJankMonitor, handler, multiDisplayDragMoveIndicatorController, desktopState, shellExecutor);
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallback
    public final Rect onDragPositioningEnd(float f, float f2, int i) {
        return onDragPositioningEnd(f, f2, i, true);
    }
}
