package com.android.p038wm.shell.windowdecor;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import com.android.p038wm.shell.common.DismissButtonManager;
import com.android.p038wm.shell.common.DismissButtonManager$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.FreeformDragPositioningController;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.freeform.DexSnappingGuide;
import com.android.p038wm.shell.freeform.DexSnappingGuideView;
import com.android.p038wm.shell.freeform.FreeformTaskListener;
import com.android.p038wm.shell.shortcut.DexCompatRestartDialogUtils;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.windowdecor.MultitaskingWindowDecoration;
import com.android.p038wm.shell.windowdecor.TaskMotionController;
import com.android.p038wm.shell.windowdecor.widget.HandleView;
import com.android.p038wm.shell.windowdecor.widget.OutlineView;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.multiwindow.DexSizeCompatResizeGuide;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskPositioner implements DragPositioningCallback {
    public int mCtrlType;
    public DexSnappingGuide mDexSnappingGuide;
    public final DisplayController mDisplayController;
    public final FreeformDragPositioningController.FreeformDragListener mDragPositioningListener;
    public final DragStartListener mDragStartListener;
    public boolean mFlingCanceled;
    public FreeformCaptionTouchState mFreeformCaptionTouchState;
    public FreeformResizeGuide mFreeformResizeGuide;
    public boolean mHasMoved;
    public boolean mImeAnimating;
    public boolean mImeShowing;
    public boolean mIsOnlyPositionChanged;
    public boolean mIsUserInteracting;
    public boolean mLastFreeformTaskSurfaceOverlappingWithNavBar;
    public int mLastSnapType;
    public int mMinVisibleHeight;
    public final MultitaskingWindowDecoration mMultiTaskingDecor;
    public final PointF mRepositionStartPoint;
    public final Rect mRepositionTaskBounds;
    public boolean mResizing;
    public final Rect mTaskBoundsAtDragStart;
    public final TaskMotionController mTaskMotionController;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTempBounds;
    public final Rect mTmpRect;
    public final Rect mTmpRect2;
    public int mToolType;
    public final WindowDecoration mWindowDecoration;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DragStartListener {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController) {
        this(shellTaskOrganizer, windowDecoration, displayController, new DragStartListener(r1) { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda0
        });
        final int i = 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0212, code lost:
    
        r12 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean changeBounds(WindowContainerTransaction windowContainerTransaction, float f, float f2, boolean z) {
        InsetsState insetsState;
        FreeformResizeGuide freeformResizeGuide;
        boolean z2 = this.mHasMoved;
        Rect rect = this.mTaskBoundsAtDragStart;
        Rect rect2 = this.mRepositionTaskBounds;
        int i = z2 ? rect2.left : rect.left;
        int i2 = z2 ? rect2.top : rect.top;
        int i3 = z2 ? rect2.right : rect.right;
        int i4 = z2 ? rect2.bottom : rect.bottom;
        PointF pointF = this.mRepositionStartPoint;
        float f3 = f - pointF.x;
        float f4 = f2 - pointF.y;
        rect2.set(rect);
        WindowDecoration windowDecoration = this.mWindowDecoration;
        int displayId = windowDecoration.mDisplay.getDisplayId();
        DisplayController displayController = this.mDisplayController;
        DisplayLayout displayLayout = displayController.getDisplayLayout(displayId);
        Rect rect3 = this.mTempBounds;
        boolean z3 = false;
        displayLayout.getStableBounds(rect3, false);
        int i5 = this.mCtrlType;
        if ((i5 & 1) != 0) {
            int i6 = rect2.left + ((int) f3);
            if (i6 <= rect3.left) {
                i6 = i;
            }
            rect2.left = i6;
        }
        if ((i5 & 2) != 0) {
            int i7 = rect2.right + ((int) f3);
            if (i7 >= rect3.right) {
                i7 = i3;
            }
            rect2.right = i7;
        }
        if ((i5 & 4) != 0) {
            int i8 = rect2.top + ((int) f4);
            if (i8 <= rect3.top) {
                i8 = i2;
            }
            rect2.top = i8;
        }
        if ((i5 & 8) != 0) {
            int i9 = rect2.bottom + ((int) f4);
            if (i9 >= rect3.bottom) {
                i9 = i4;
            }
            rect2.bottom = i9;
        }
        if (i5 == 0) {
            rect2.offset((int) f3, (int) f4);
        }
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && this.mResizing && (freeformResizeGuide = this.mFreeformResizeGuide) != null && freeformResizeGuide.asSizeCompatResizeGuide() != null && SizeCompatInfo.isDragResizable(windowDecoration.mTaskInfo.sizeCompatInfo)) {
            this.mFreeformResizeGuide.asSizeCompatResizeGuide().adjustBounds(windowDecoration.mTaskInfo.sizeCompatInfo, this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, z, new Consumer() { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MultitaskingWindowDecoration multitaskingWindowDecoration;
                    TaskPositioner taskPositioner = TaskPositioner.this;
                    Rect rect4 = (Rect) obj;
                    taskPositioner.getClass();
                    if (!CoreRune.MW_CAPTION_SHELL || (multitaskingWindowDecoration = taskPositioner.mMultiTaskingDecor) == null) {
                        return;
                    }
                    rect4.top -= multitaskingWindowDecoration.getCaptionVisibleHeight();
                }
            });
        }
        if (this.mImeShowing && (insetsState = displayController.getInsetsState(windowDecoration.mTaskInfo.displayId)) != null) {
            Rect displayFrame = insetsState.getDisplayFrame();
            Rect rect4 = this.mTmpRect2;
            rect4.set(displayFrame);
            rect4.inset(insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.ime(), false));
            int i10 = rect2.bottom;
            int i11 = rect4.bottom;
            if (i10 > i11) {
                rect2.offset(0, i11 - i10);
            }
        }
        int width = rect2.width();
        int i12 = windowDecoration.mTaskInfo.minWidth;
        if (width < ((int) (i12 < 0 ? windowDecoration.mTaskInfo.defaultMinSize * displayController.getDisplayLayout(windowDecoration.mTaskInfo.displayId).mDensityDpi * 0.00625f : i12))) {
            rect2.right = i3;
            rect2.left = i;
        }
        int height = rect2.height();
        int i13 = windowDecoration.mTaskInfo.minHeight;
        if (height < ((int) (i13 < 0 ? windowDecoration.mTaskInfo.defaultMinSize * displayController.getDisplayLayout(windowDecoration.mTaskInfo.displayId).mDensityDpi * 0.00625f : i13))) {
            rect2.top = i2;
            rect2.bottom = i4;
        }
        if (this.mResizing) {
            FreeformResizeGuide freeformResizeGuide2 = this.mFreeformResizeGuide;
            if (freeformResizeGuide2 != null) {
                freeformResizeGuide2.adjustMinMaxSize(rect2);
            }
        } else {
            rect2.offsetTo(rect2.left, Math.max(getFreeformThickness$1() + getCaptionVisibleHeight() + rect3.top, Math.min(rect2.top, rect3.bottom - this.mMinVisibleHeight)));
        }
        if (this.mFreeformResizeGuide != null && this.mResizing && WindowConfiguration.isDexTaskDocking(getDexTaskDockingState())) {
            this.mFreeformResizeGuide.adjustMinMaxSize(rect2);
            this.mFreeformResizeGuide.adjustDexDockingTaskBounds(getDexTaskDockingState(), rect2, getFreeformThickness$1());
        }
        if (i == rect2.left && i2 == rect2.top && i3 == rect2.right && i4 == rect2.bottom && (!CoreRune.MW_FREEFORM_MOTION_ANIMATION || !z)) {
            return false;
        }
        windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, rect2);
        MultitaskingWindowDecoration multitaskingWindowDecoration = this.mMultiTaskingDecor;
        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
        if (adjustState.mIsAdjusted && !adjustState.mOriginBounds.isEmpty()) {
            multitaskingWindowDecoration.mAdjustState.setOriginBounds(null);
            MultitaskingWindowDecoration.AdjustState adjustState2 = multitaskingWindowDecoration.mAdjustState;
            if (adjustState2.mIsAdjusted) {
                adjustState2.mIsAdjusted = false;
            }
        }
        if (!windowDecoration.mIsDexMode) {
            DisplayLayout displayLayout2 = displayController.getDisplayLayout(windowDecoration.mDisplay.getDisplayId());
            if (displayLayout2 != null) {
                if ((r1 = DisplayLayout.navigationBarPosition(windowDecoration.mDecorWindowContext.getResources(), displayLayout2.mWidth, displayLayout2.mHeight, displayLayout2.mRotation)) == 4) {
                }
            }
            if (z3 != this.mLastFreeformTaskSurfaceOverlappingWithNavBar) {
                this.mLastFreeformTaskSurfaceOverlappingWithNavBar = z3;
                this.mTaskOrganizer.setFreeformTaskSurfaceOverlappedWithNavi(windowDecoration.mTaskInfo.token, z3);
            }
        }
        return true;
    }

    public final int getCaptionVisibleHeight() {
        if (this.mMultiTaskingDecor != null) {
            return ((MultitaskingWindowDecoration) this.mWindowDecoration).getCaptionVisibleHeight();
        }
        return 0;
    }

    public final int getDexTaskDockingState() {
        return this.mWindowDecoration.mTaskInfo.configuration.windowConfiguration.getDexTaskDockingState();
    }

    public final int getFreeformThickness$1() {
        if (this.mMultiTaskingDecor != null) {
            return ((MultitaskingWindowDecoration) this.mWindowDecoration).getFreeformThickness$1();
        }
        return 0;
    }

    public final int getUpdatedCaptionHeight() {
        if (this.mMultiTaskingDecor == null) {
            return 0;
        }
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecoration;
        return WindowDecoration.loadDimensionPixelSize(multitaskingWindowDecoration.mTaskInfo.getConfiguration().isNewDexMode() ? R.dimen.toast_y_offset : com.android.systemui.R.dimen.sec_decor_caption_height_desktop_freeform, multitaskingWindowDecoration.mDecorWindowContext.getResources());
    }

    public final boolean isDexSnappingInNonFreeform() {
        WindowDecoration windowDecoration = this.mWindowDecoration;
        if (windowDecoration.mIsDexEnabled) {
            if (windowDecoration.mTaskInfo.getWindowingMode() == 1) {
                return true;
            }
            if (windowDecoration.mIsNewDexMode && windowDecoration.mTaskInfo.getConfiguration().windowConfiguration.isSplitScreen()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:242:0x036a, code lost:
    
        if (r12.right < (r2.left + r0)) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x0378, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x0451, code lost:
    
        if (r2.isTargetTaskImeShowing(r13.mTaskInfo.displayId) != false) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x0376, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x0374, code lost:
    
        if (r12.left < (r2.left - r0)) goto L190;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x045a  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x046b  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x04e2  */
    /* JADX WARN: Type inference failed for: r0v90, types: [com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v93, types: [com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v69 */
    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDragPositioningEnd(float f, float f2) {
        ShellTaskOrganizer shellTaskOrganizer;
        ?? r3;
        boolean z;
        FreeformDragPositioningController.FreeformDragListener freeformDragListener;
        MultitaskingWindowDecoration.FreeformOutlineWrapper freeformOutlineWrapper;
        OutlineView outlineView;
        FreeformCaptionTouchState freeformCaptionTouchState;
        int width;
        ShellTaskOrganizer shellTaskOrganizer2;
        boolean z2;
        boolean z3;
        Rect rect;
        boolean z4;
        int width2;
        boolean z5;
        boolean z6;
        boolean z7;
        int i;
        boolean z8;
        boolean z9;
        FreeformTaskListener freeformTaskListener;
        boolean z10;
        FreeformResizeGuide freeformResizeGuide;
        int dexTaskDockingState;
        FreeformTaskListener freeformTaskListener2;
        if (f == -1.0f && f2 == -1.0f) {
            this.mHasMoved = false;
        }
        TaskMotionController taskMotionController = this.mTaskMotionController;
        if (!taskMotionController.mAllowTouches) {
            taskMotionController.mAllowTouches = true;
            this.mHasMoved = false;
        }
        boolean z11 = this.mHasMoved;
        final MultitaskingWindowDecoration multitaskingWindowDecoration = this.mMultiTaskingDecor;
        Rect rect2 = this.mTaskBoundsAtDragStart;
        ShellTaskOrganizer shellTaskOrganizer3 = this.mTaskOrganizer;
        Rect rect3 = this.mRepositionTaskBounds;
        WindowDecoration windowDecoration = this.mWindowDecoration;
        if (z11) {
            if (multitaskingWindowDecoration != null) {
                multitaskingWindowDecoration.mFreeformStashState.mAnimType = -1;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, false);
            if (!this.mResizing || multitaskingWindowDecoration == null) {
                boolean supportDexSnapping = supportDexSnapping();
                DisplayController displayController = this.mDisplayController;
                if (supportDexSnapping) {
                    if (multitaskingWindowDecoration != null) {
                        SplitScreenController splitScreenController = multitaskingWindowDecoration.mSplitScreenController;
                        if (splitScreenController != null) {
                            BooleanSupplier booleanSupplier = splitScreenController.mIsKeyguardOccludedAndShowingSupplier;
                            if (booleanSupplier != null ? booleanSupplier.getAsBoolean() : false) {
                                z10 = true;
                                if (z10) {
                                    z5 = true;
                                    if (z5) {
                                        if (f == -2.0f && f2 == -2.0f) {
                                            this.mLastSnapType = 0;
                                            DexSnappingGuide dexSnappingGuide = this.mDexSnappingGuide;
                                            if (dexSnappingGuide != null) {
                                                dexSnappingGuide.mSnappingBounds.setEmpty();
                                                this.mDexSnappingGuide.mSnappingOtherBounds.setEmpty();
                                            }
                                        }
                                        ActivityManager.RunningTaskInfo runningTaskInfo = windowDecoration.mTaskInfo;
                                        if (multitaskingWindowDecoration != null) {
                                            multitaskingWindowDecoration.mDexCompatRestartDialogUtils.getClass();
                                            if (DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo)) {
                                                int i2 = runningTaskInfo.getConfiguration().dexCompatUiMode;
                                                if (i2 == 3) {
                                                    multitaskingWindowDecoration.mDexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(runningTaskInfo.taskId);
                                                    z9 = false;
                                                } else {
                                                    if ((i2 == 1 || i2 == 2) && this.mLastSnapType == 2) {
                                                        multitaskingWindowDecoration.mDexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(runningTaskInfo.taskId);
                                                    }
                                                    z9 = true;
                                                }
                                                z8 = false;
                                                if (z9) {
                                                    windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, rect3);
                                                    if (!this.mDexSnappingGuide.mSnappingOtherBounds.isEmpty()) {
                                                        boolean z12 = this.mLastSnapType == 1;
                                                        Rect rect4 = z12 ? rect3 : this.mDexSnappingGuide.mSnappingOtherBounds;
                                                        Rect rect5 = z12 ? this.mDexSnappingGuide.mSnappingOtherBounds : rect3;
                                                        InsetsState insetsState = displayController.getInsetsState(windowDecoration.mTaskInfo.displayId);
                                                        Rect displayFrame = insetsState.getDisplayFrame();
                                                        Rect rect6 = this.mTmpRect2;
                                                        rect6.set(displayFrame);
                                                        rect6.inset(insetsState.calculateInsets(rect6, WindowInsets.Type.systemBars() | WindowInsets.Type.ime(), false));
                                                        if (z8) {
                                                            rect4.top = getUpdatedCaptionHeight() + rect4.top;
                                                            rect5.top = getUpdatedCaptionHeight() + rect5.top;
                                                        } else {
                                                            rect4.top = getCaptionVisibleHeight() + rect4.top;
                                                            rect5.top = getCaptionVisibleHeight() + rect5.top;
                                                        }
                                                        windowContainerTransaction.setBounds(windowDecoration.mTaskInfo.token, z12 ? rect4 : rect5);
                                                        MultiWindowManager.getInstance().initDockingBounds(rect4, rect5, rect6.width());
                                                        MultiWindowManager.getInstance().setCandidateTask(windowDecoration.mTaskInfo.taskId);
                                                        MultiWindowManager.getInstance().scheduleNotifyDexSnappingCallback(windowDecoration.mTaskInfo.taskId, this.mDexSnappingGuide.mSnappingOtherBounds);
                                                    }
                                                }
                                                if (this.mIsOnlyPositionChanged) {
                                                    new SurfaceControl.Transaction().setPosition(windowDecoration.mTaskSurface, rect3.left, rect3.top).apply();
                                                    MultiWindowManager.getInstance().clearAllDockingTasks("mIsOnlyPositionChanged");
                                                }
                                            }
                                        }
                                        if (runningTaskInfo.getWindowingMode() == 1 || (runningTaskInfo.getConfiguration().windowConfiguration.isSplitScreen() && runningTaskInfo.getConfiguration().isNewDexMode())) {
                                            this.mDexSnappingGuide.mPointerPosition = 0;
                                            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 5);
                                            rect3.set(this.mDexSnappingGuide.mSnappingBounds);
                                            z6 = true;
                                        } else {
                                            int i3 = this.mLastSnapType;
                                            if (i3 == 2) {
                                                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 1);
                                                z6 = false;
                                            } else {
                                                if (i3 != 0) {
                                                    if (windowDecoration.mIsNewDexMode) {
                                                        int i4 = i3 == 4 ? 32 : 0;
                                                        shellTaskOrganizer3.getClass();
                                                        if (runningTaskInfo.getWindowingMode() == 5 && (freeformTaskListener = (FreeformTaskListener) shellTaskOrganizer3.mTaskListeners.get(-5)) != null) {
                                                            TaskOperations taskOperations = ((MultitaskingWindowDecorViewModel) freeformTaskListener.mWindowDecorationViewModel).mTaskOperations;
                                                            taskOperations.getClass();
                                                            taskOperations.mSplitScreenController.ifPresent(new TaskOperations$$ExternalSyntheticLambda0(runningTaskInfo, i4));
                                                        }
                                                        if (this.mIsOnlyPositionChanged) {
                                                        }
                                                    } else {
                                                        Rect rect7 = this.mDexSnappingGuide.mSnappingBounds;
                                                        this.mIsOnlyPositionChanged = rect7.width() == rect3.width() && rect7.height() == getCaptionVisibleHeight() + rect3.height();
                                                        rect3.set(rect7);
                                                    }
                                                }
                                                z6 = false;
                                                z7 = true;
                                                i = this.mLastSnapType;
                                                if (i != 3 || i == 6 || i == 9 || i == 12) {
                                                    if (z6) {
                                                        rect3.top = getFreeformThickness$1() + getCaptionVisibleHeight() + rect3.top;
                                                    } else {
                                                        rect3.top = getFreeformThickness$1() + getUpdatedCaptionHeight() + rect3.top;
                                                    }
                                                    rect3.bottom -= getFreeformThickness$1();
                                                }
                                                z8 = z6;
                                                z9 = z7;
                                                if (z9) {
                                                }
                                                if (this.mIsOnlyPositionChanged) {
                                                }
                                            }
                                        }
                                        z7 = z6;
                                        i = this.mLastSnapType;
                                        if (i != 3) {
                                        }
                                        if (z6) {
                                        }
                                        rect3.bottom -= getFreeformThickness$1();
                                        z8 = z6;
                                        z9 = z7;
                                        if (z9) {
                                        }
                                        if (this.mIsOnlyPositionChanged) {
                                        }
                                    } else {
                                        changeBounds(windowContainerTransaction, f, f2, true);
                                    }
                                }
                            }
                        }
                        z10 = false;
                        if (z10) {
                        }
                    }
                    z5 = false;
                    if (z5) {
                    }
                } else {
                    if (!CoreRune.MW_FREEFORM_MOTION_ANIMATION || (freeformCaptionTouchState = this.mFreeformCaptionTouchState) == null) {
                        shellTaskOrganizer = shellTaskOrganizer3;
                        changeBounds(windowContainerTransaction, f, f2, false);
                    } else {
                        PointF pointF = freeformCaptionTouchState.mVelocity;
                        float f3 = pointF.x;
                        boolean z13 = f3 < -12000.0f;
                        boolean z14 = f3 > 12000.0f;
                        boolean isStashed = multitaskingWindowDecoration.mFreeformStashState.isStashed();
                        if (isStashed) {
                            width = taskMotionController.mStashMoveThreshold + taskMotionController.mMinVisibleWidth;
                        } else {
                            int freeformCaptionType = shellTaskOrganizer3.getFreeformCaptionType(windowDecoration.mTaskInfo);
                            if (freeformCaptionType == 0) {
                                HandleView handleView = multitaskingWindowDecoration.getHandleView();
                                width = handleView != null ? (rect3.width() - handleView.getWidth()) / 2 : 0;
                            } else {
                                if (freeformCaptionType == 1) {
                                    width = rect3.width() / 2;
                                }
                            }
                        }
                        DisplayLayout displayLayout = displayController.getDisplayLayout(windowDecoration.mTaskInfo.displayId);
                        Rect rect8 = this.mTmpRect;
                        displayLayout.getDisplayBounds(rect8);
                        if (isStashed) {
                            shellTaskOrganizer2 = shellTaskOrganizer3;
                        } else {
                            shellTaskOrganizer2 = shellTaskOrganizer3;
                        }
                        boolean z15 = !isStashed ? rect3.right <= rect8.right + width : rect3.left <= rect8.right - width;
                        FreeformStashState freeformStashState = multitaskingWindowDecoration.mFreeformStashState;
                        int i5 = freeformStashState.mStashType;
                        if (((z13 && i5 != 2) || (z14 && i5 != 1)) || (z2 || z15)) {
                            final int i6 = 0;
                            taskMotionController.mAllowTouches = false;
                            freeformStashState.mLastFreeformBoundsBeforeStash.set(rect2);
                            taskMotionController.setStashDim(windowContainerTransaction, true);
                            taskMotionController.moveToTarget(rect3, pointF, new Runnable(this) { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda1
                                public final /* synthetic */ TaskPositioner f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i6) {
                                        case 0:
                                            TaskPositioner taskPositioner = this.f$0;
                                            WindowDecoration windowDecoration2 = taskPositioner.mWindowDecoration;
                                            taskPositioner.mDisplayController.getDisplayLayout(windowDecoration2.mDisplay.getDisplayId()).getStableBounds(taskPositioner.mTempBounds, false);
                                            Rect rect9 = taskPositioner.mRepositionTaskBounds;
                                            taskPositioner.mMultiTaskingDecor.mFreeformStashState.mFreeformStashYFraction = rect9.top / r3.height();
                                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                            windowContainerTransaction2.setChangeFreeformStashMode(windowDecoration2.mTaskInfo.token, 2);
                                            windowContainerTransaction2.setChangeFreeformStashScale(windowDecoration2.mTaskInfo.token, taskPositioner.mTaskMotionController.mScaledFreeformHeight / rect9.height());
                                            windowContainerTransaction2.setBounds(windowDecoration2.mTaskInfo.token, rect9);
                                            taskPositioner.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
                                            break;
                                        case 1:
                                            TaskPositioner taskPositioner2 = this.f$0;
                                            if (taskPositioner2.mFlingCanceled && !taskPositioner2.mImeShowing) {
                                                taskPositioner2.mFlingCanceled = false;
                                            }
                                            if (!taskPositioner2.mFlingCanceled) {
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                windowContainerTransaction3.setBounds(taskPositioner2.mWindowDecoration.mTaskInfo.token, taskPositioner2.mRepositionTaskBounds);
                                                taskPositioner2.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                            }
                                            taskPositioner2.mFlingCanceled = false;
                                            break;
                                        default:
                                            TaskPositioner taskPositioner3 = this.f$0;
                                            Rect rect10 = taskPositioner3.mTmpRect;
                                            rect10.setEmpty();
                                            taskPositioner3.mTaskMotionController.cancelBoundsAnimator(rect10, "freeform dismiss");
                                            boolean isEmpty = rect10.isEmpty();
                                            ShellTaskOrganizer shellTaskOrganizer4 = taskPositioner3.mTaskOrganizer;
                                            WindowDecoration windowDecoration3 = taskPositioner3.mWindowDecoration;
                                            if (!isEmpty) {
                                                taskPositioner3.mFlingCanceled = true;
                                                WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                                                windowContainerTransaction4.setBounds(windowDecoration3.mTaskInfo.token, taskPositioner3.mRepositionTaskBounds);
                                                shellTaskOrganizer4.applyTransaction(windowContainerTransaction4);
                                            }
                                            if (taskPositioner3.mMultiTaskingDecor != null && !windowDecoration3.mIsRemoving) {
                                                CoreSaLogger.logForAdvanced("2003", shellTaskOrganizer4.getFreeformCaptionType(windowDecoration3.mTaskInfo) == 0 ? "Bottom option_Handle type" : "Bottom option_Header type");
                                            }
                                            taskPositioner3.removeTaskToMotionInfo(windowDecoration3.mTaskInfo, false);
                                            windowDecoration3.mIsRemoving = true;
                                            break;
                                    }
                                }
                            }, true);
                            shellTaskOrganizer = shellTaskOrganizer2;
                        } else {
                            FreeformCaptionTouchState freeformCaptionTouchState2 = this.mFreeformCaptionTouchState;
                            PointF pointF2 = new PointF(freeformCaptionTouchState2.mVelocity);
                            float f4 = pointF2.x;
                            if (f4 != 0.0f || pointF2.y != 0.0f) {
                                float abs = Math.abs(f4);
                                float f5 = freeformCaptionTouchState2.mMinimumFlingVelocity;
                                if (abs > f5 || Math.abs(pointF2.y) > f5) {
                                    DisplayLayout displayLayout2 = displayController.getDisplayLayout(windowDecoration.mDisplay.getDisplayId());
                                    Rect rect9 = this.mTempBounds;
                                    displayLayout2.getStableBounds(rect9, false);
                                    float f6 = pointF2.x;
                                    boolean z16 = f6 < 0.0f;
                                    if ((!z16 || rect2.left >= rect9.left) && ((z16 || rect2.right <= rect9.right) && (Math.abs(f6) >= 700.0f || ((!z16 || rect3.left >= rect9.left - 30) && (z16 || rect3.right <= rect9.right + 30))))) {
                                        if (pointF2.y > 0.0f) {
                                            shellTaskOrganizer = shellTaskOrganizer2;
                                        } else {
                                            shellTaskOrganizer = shellTaskOrganizer2;
                                        }
                                        z3 = true;
                                        if (z3) {
                                            MultitaskingWindowDecoration multitaskingWindowDecoration2 = taskMotionController.mWindowDecoration;
                                            HandleView handleView2 = multitaskingWindowDecoration2.getHandleView();
                                            boolean z17 = multitaskingWindowDecoration2.mCaptionType == 0;
                                            if (taskMotionController.mTaskMotionInfo != null && (!z17 || handleView2 != null)) {
                                                int width3 = (rect3.width() - multitaskingWindowDecoration2.getHandleViewWidth()) / 2;
                                                int i7 = rect3.left;
                                                int i8 = i7 + width3;
                                                Rect rect10 = taskMotionController.mTaskMotionInfo.mDisplayBounds;
                                                int i9 = rect10.left;
                                                if (i8 <= i9 || rect3.right - width3 >= rect10.right) {
                                                    if (i9 - i7 > width3) {
                                                        width2 = i9 - width3;
                                                    } else {
                                                        int i10 = rect3.right;
                                                        int i11 = rect10.right;
                                                        width2 = i10 - i11 > width3 ? (i11 - rect3.width()) + width3 : 0;
                                                    }
                                                    rect = new Rect(width2, rect3.top, rect3.width() + width2, rect3.bottom);
                                                    if (rect != rect3 || FreeformDragPositioningController.getInstance(windowDecoration.mContext).mFreeformDragListener.mDismissButtonManager.mView.mIsEnterDismissButton) {
                                                        z4 = false;
                                                    } else {
                                                        taskMotionController.scheduleAnimateRestore(rect3, rect, true);
                                                        z4 = true;
                                                    }
                                                    if (!z4) {
                                                        if (multitaskingWindowDecoration.mFreeformStashState.isStashed()) {
                                                            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                                            transaction.setMatrix(windowDecoration.mTaskSurface, 1.0f, 0.0f, 0.0f, 1.0f);
                                                            transaction.apply();
                                                            multitaskingWindowDecoration.mFreeformStashState.setStashed(0);
                                                            taskMotionController.setStashDim(windowContainerTransaction, false);
                                                            windowContainerTransaction.setChangeFreeformStashScale(windowDecoration.mTaskInfo.token, 1.0f);
                                                        }
                                                        changeBounds(windowContainerTransaction, f, f2, true);
                                                    }
                                                }
                                            }
                                            rect = rect3;
                                            if (rect != rect3) {
                                            }
                                            z4 = false;
                                            if (!z4) {
                                            }
                                        } else {
                                            taskMotionController.mAllowTouches = false;
                                            taskMotionController.setStashDim(windowContainerTransaction, false);
                                            final int i12 = 1;
                                            taskMotionController.moveToTarget(rect3, pointF, new Runnable(this) { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda1
                                                public final /* synthetic */ TaskPositioner f$0;

                                                {
                                                    this.f$0 = this;
                                                }

                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i12) {
                                                        case 0:
                                                            TaskPositioner taskPositioner = this.f$0;
                                                            WindowDecoration windowDecoration2 = taskPositioner.mWindowDecoration;
                                                            taskPositioner.mDisplayController.getDisplayLayout(windowDecoration2.mDisplay.getDisplayId()).getStableBounds(taskPositioner.mTempBounds, false);
                                                            Rect rect92 = taskPositioner.mRepositionTaskBounds;
                                                            taskPositioner.mMultiTaskingDecor.mFreeformStashState.mFreeformStashYFraction = rect92.top / r3.height();
                                                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                                            windowContainerTransaction2.setChangeFreeformStashMode(windowDecoration2.mTaskInfo.token, 2);
                                                            windowContainerTransaction2.setChangeFreeformStashScale(windowDecoration2.mTaskInfo.token, taskPositioner.mTaskMotionController.mScaledFreeformHeight / rect92.height());
                                                            windowContainerTransaction2.setBounds(windowDecoration2.mTaskInfo.token, rect92);
                                                            taskPositioner.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
                                                            break;
                                                        case 1:
                                                            TaskPositioner taskPositioner2 = this.f$0;
                                                            if (taskPositioner2.mFlingCanceled && !taskPositioner2.mImeShowing) {
                                                                taskPositioner2.mFlingCanceled = false;
                                                            }
                                                            if (!taskPositioner2.mFlingCanceled) {
                                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                                windowContainerTransaction3.setBounds(taskPositioner2.mWindowDecoration.mTaskInfo.token, taskPositioner2.mRepositionTaskBounds);
                                                                taskPositioner2.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                                            }
                                                            taskPositioner2.mFlingCanceled = false;
                                                            break;
                                                        default:
                                                            TaskPositioner taskPositioner3 = this.f$0;
                                                            Rect rect102 = taskPositioner3.mTmpRect;
                                                            rect102.setEmpty();
                                                            taskPositioner3.mTaskMotionController.cancelBoundsAnimator(rect102, "freeform dismiss");
                                                            boolean isEmpty = rect102.isEmpty();
                                                            ShellTaskOrganizer shellTaskOrganizer4 = taskPositioner3.mTaskOrganizer;
                                                            WindowDecoration windowDecoration3 = taskPositioner3.mWindowDecoration;
                                                            if (!isEmpty) {
                                                                taskPositioner3.mFlingCanceled = true;
                                                                WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                                                                windowContainerTransaction4.setBounds(windowDecoration3.mTaskInfo.token, taskPositioner3.mRepositionTaskBounds);
                                                                shellTaskOrganizer4.applyTransaction(windowContainerTransaction4);
                                                            }
                                                            if (taskPositioner3.mMultiTaskingDecor != null && !windowDecoration3.mIsRemoving) {
                                                                CoreSaLogger.logForAdvanced("2003", shellTaskOrganizer4.getFreeformCaptionType(windowDecoration3.mTaskInfo) == 0 ? "Bottom option_Handle type" : "Bottom option_Header type");
                                                            }
                                                            taskPositioner3.removeTaskToMotionInfo(windowDecoration3.mTaskInfo, false);
                                                            windowDecoration3.mIsRemoving = true;
                                                            break;
                                                    }
                                                }
                                            }, false);
                                        }
                                    }
                                }
                            }
                            shellTaskOrganizer = shellTaskOrganizer2;
                            z3 = false;
                            if (z3) {
                            }
                        }
                    }
                    if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && CoreRune.MW_SHELL_FREEFORM_TASK_POSITIONER && this.mResizing && !this.mFreeformResizeGuide.readyToMinimize()) {
                        windowContainerTransaction.setChangeTransitMode(windowDecoration.mTaskInfo.token, 1, "resize_freeform");
                    }
                    if (multitaskingWindowDecoration != null && !multitaskingWindowDecoration.mFreeformStashState.isStashed() && ((freeformResizeGuide = this.mFreeformResizeGuide) == null || !freeformResizeGuide.readyToMinimize())) {
                        windowContainerTransaction.setChangeFreeformStashMode(windowDecoration.mTaskInfo.token, 1);
                    }
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    r3 = 0;
                }
            } else if (!this.mFreeformResizeGuide.canResizeGesture() || windowDecoration.mIsDexMode) {
                changeBounds(windowContainerTransaction, f, f2, true);
                if (!windowDecoration.mIsDexMode) {
                    multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash.set(rect3);
                }
                if (windowDecoration.mIsDexMode && (dexTaskDockingState = getDexTaskDockingState()) != -1 && dexTaskDockingState != 0) {
                    MultiWindowManager.getInstance().resizeOtherTaskIfNeeded(windowDecoration.mTaskInfo.taskId, rect3);
                }
            } else if (this.mFreeformResizeGuide.readyToMinimize()) {
                MultiWindowManager.getInstance().minimizeTaskToSpecificPosition(windowDecoration.mTaskInfo.taskId, false, (rect3.width() / 2) + rect3.left, (rect3.height() / 2) + rect3.top);
                if (CoreRune.MW_FREEFORM_RESIZE_GESTURE_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("2016");
                }
            } else if (this.mFreeformResizeGuide.needToFullscreenTransition()) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = windowDecoration.mTaskInfo;
                shellTaskOrganizer3.getClass();
                if (runningTaskInfo2.getWindowingMode() == 5 && (freeformTaskListener2 = (FreeformTaskListener) shellTaskOrganizer3.mTaskListeners.get(-5)) != null) {
                    ((MultitaskingWindowDecorViewModel) freeformTaskListener2.mWindowDecorationViewModel).mTaskOperations.maximizeTask(runningTaskInfo2);
                }
                if (CoreRune.MW_FREEFORM_RESIZE_GESTURE_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("2015");
                    CoreSaLogger.logForAdvanced("2090", "From popup resizing");
                }
            }
            shellTaskOrganizer = shellTaskOrganizer3;
            if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                windowContainerTransaction.setChangeTransitMode(windowDecoration.mTaskInfo.token, 1, "resize_freeform");
            }
            if (multitaskingWindowDecoration != null) {
                windowContainerTransaction.setChangeFreeformStashMode(windowDecoration.mTaskInfo.token, 1);
            }
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            r3 = 0;
        } else {
            shellTaskOrganizer = shellTaskOrganizer3;
            r3 = 0;
        }
        this.mCtrlType = r3;
        rect2.setEmpty();
        this.mRepositionStartPoint.set(0.0f, 0.0f);
        this.mHasMoved = r3;
        this.mIsOnlyPositionChanged = r3;
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            this.mIsUserInteracting = r3;
        }
        FreeformResizeGuide freeformResizeGuide2 = this.mFreeformResizeGuide;
        if (freeformResizeGuide2 != null) {
            freeformResizeGuide2.dismiss();
            this.mFreeformResizeGuide = null;
            this.mResizing = r3;
            if (CoreRune.MW_CAPTION_SHELL_SHADOW_ANIM && multitaskingWindowDecoration != null && (freeformOutlineWrapper = multitaskingWindowDecoration.mFreeformOutlineWrapper) != null && (outlineView = freeformOutlineWrapper.getOutlineView()) != null) {
                ObjectAnimator objectAnimator = multitaskingWindowDecoration.mDragShadowAnimator;
                if (objectAnimator != null) {
                    objectAnimator.cancel();
                }
                float calculateElevation = multitaskingWindowDecoration.mFreeformOutlineWrapper.calculateElevation();
                multitaskingWindowDecoration.mWindowElevation = calculateElevation;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(outlineView, "elevation", 0.0f, calculateElevation);
                multitaskingWindowDecoration.mDragShadowAnimator = ofFloat;
                ofFloat.setStartDelay(400L);
                multitaskingWindowDecoration.mDragShadowAnimator.setDuration(200L).start();
                multitaskingWindowDecoration.mDragShadowAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.10
                    public C417710() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        OutlineView outlineView2;
                        super.onAnimationCancel(animator);
                        FreeformOutlineWrapper freeformOutlineWrapper2 = MultitaskingWindowDecoration.this.mFreeformOutlineWrapper;
                        if (freeformOutlineWrapper2 == null || (outlineView2 = freeformOutlineWrapper2.getOutlineView()) == null) {
                            return;
                        }
                        outlineView2.setElevation(MultitaskingWindowDecoration.this.mWindowElevation);
                    }
                });
            }
        }
        if (CoreRune.MW_FREEFORM_DISMISS_VIEW && !windowDecoration.mIsDexMode && !this.mResizing && (freeformDragListener = this.mDragPositioningListener) != null && !isDexSnappingInNonFreeform()) {
            int i13 = windowDecoration.mTaskInfo.taskId;
            final int i14 = 2;
            Runnable runnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda1
                public final /* synthetic */ TaskPositioner f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i14) {
                        case 0:
                            TaskPositioner taskPositioner = this.f$0;
                            WindowDecoration windowDecoration2 = taskPositioner.mWindowDecoration;
                            taskPositioner.mDisplayController.getDisplayLayout(windowDecoration2.mDisplay.getDisplayId()).getStableBounds(taskPositioner.mTempBounds, false);
                            Rect rect92 = taskPositioner.mRepositionTaskBounds;
                            taskPositioner.mMultiTaskingDecor.mFreeformStashState.mFreeformStashYFraction = rect92.top / r3.height();
                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                            windowContainerTransaction2.setChangeFreeformStashMode(windowDecoration2.mTaskInfo.token, 2);
                            windowContainerTransaction2.setChangeFreeformStashScale(windowDecoration2.mTaskInfo.token, taskPositioner.mTaskMotionController.mScaledFreeformHeight / rect92.height());
                            windowContainerTransaction2.setBounds(windowDecoration2.mTaskInfo.token, rect92);
                            taskPositioner.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
                            break;
                        case 1:
                            TaskPositioner taskPositioner2 = this.f$0;
                            if (taskPositioner2.mFlingCanceled && !taskPositioner2.mImeShowing) {
                                taskPositioner2.mFlingCanceled = false;
                            }
                            if (!taskPositioner2.mFlingCanceled) {
                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                windowContainerTransaction3.setBounds(taskPositioner2.mWindowDecoration.mTaskInfo.token, taskPositioner2.mRepositionTaskBounds);
                                taskPositioner2.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                            }
                            taskPositioner2.mFlingCanceled = false;
                            break;
                        default:
                            TaskPositioner taskPositioner3 = this.f$0;
                            Rect rect102 = taskPositioner3.mTmpRect;
                            rect102.setEmpty();
                            taskPositioner3.mTaskMotionController.cancelBoundsAnimator(rect102, "freeform dismiss");
                            boolean isEmpty = rect102.isEmpty();
                            ShellTaskOrganizer shellTaskOrganizer4 = taskPositioner3.mTaskOrganizer;
                            WindowDecoration windowDecoration3 = taskPositioner3.mWindowDecoration;
                            if (!isEmpty) {
                                taskPositioner3.mFlingCanceled = true;
                                WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                                windowContainerTransaction4.setBounds(windowDecoration3.mTaskInfo.token, taskPositioner3.mRepositionTaskBounds);
                                shellTaskOrganizer4.applyTransaction(windowContainerTransaction4);
                            }
                            if (taskPositioner3.mMultiTaskingDecor != null && !windowDecoration3.mIsRemoving) {
                                CoreSaLogger.logForAdvanced("2003", shellTaskOrganizer4.getFreeformCaptionType(windowDecoration3.mTaskInfo) == 0 ? "Bottom option_Handle type" : "Bottom option_Header type");
                            }
                            taskPositioner3.removeTaskToMotionInfo(windowDecoration3.mTaskInfo, false);
                            windowDecoration3.mIsRemoving = true;
                            break;
                    }
                }
            };
            DismissButtonManager dismissButtonManager = freeformDragListener.mDismissButtonManager;
            if (dismissButtonManager.mView.mIsEnterDismissButton) {
                runnable.run();
                try {
                    ActivityTaskManager.getService().removeTaskWithFlags(i13, 144);
                } catch (RemoteException e) {
                    Log.w("FreeformDragPositioningController$FreeformDragListener", "Failed to removeTask in onTaskDragEnd: " + e);
                    e.printStackTrace();
                }
            }
            if (!dismissButtonManager.mView.mIsEnterDismissButton) {
                MultiWindowManager.getInstance().saveFreeformBounds(i13);
            }
            DismissButtonManager dismissButtonManager2 = freeformDragListener.mDismissButtonManager;
            Objects.requireNonNull(dismissButtonManager2);
            dismissButtonManager2.hide(new DismissButtonManager$$ExternalSyntheticLambda0(dismissButtonManager2));
            if (CoreRune.MW_CAPTION_SHELL_SHADOW_ANIM && multitaskingWindowDecoration != null) {
                multitaskingWindowDecoration.playShadowAnimation(rect3, true);
            }
        }
        DexSnappingGuide dexSnappingGuide2 = this.mDexSnappingGuide;
        if (dexSnappingGuide2 != null) {
            if (dexSnappingGuide2.mIsAttached) {
                DexSnappingGuideView dexSnappingGuideView = dexSnappingGuide2.mView;
                dexSnappingGuideView.removeAllViews();
                dexSnappingGuide2.mWindowManager.removeViewImmediate(dexSnappingGuideView);
                z = false;
                dexSnappingGuide2.mIsAttached = false;
            } else {
                z = false;
            }
            this.mDexSnappingGuide = null;
        } else {
            z = false;
        }
        if (this.mLastFreeformTaskSurfaceOverlappingWithNavBar) {
            this.mLastFreeformTaskSurfaceOverlappingWithNavBar = z;
            shellTaskOrganizer.setFreeformTaskSurfaceOverlappedWithNavi(windowDecoration.mTaskInfo.token, z);
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    public final void onDragPositioningMove(float f, float f2) {
        FreeformDragPositioningController.FreeformDragListener freeformDragListener;
        boolean z = this.mImeAnimating;
        TaskMotionController taskMotionController = this.mTaskMotionController;
        if (z && taskMotionController.mAllowTouches) {
            taskMotionController.mAllowTouches = false;
        }
        if (!CoreRune.MW_FREEFORM_MOTION_ANIMATION || (taskMotionController.mAllowTouches && this.mIsUserInteracting)) {
            boolean z2 = this.mImeShowing;
            MultitaskingWindowDecoration multitaskingWindowDecoration = this.mMultiTaskingDecor;
            if (z2) {
                MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
                if (adjustState.mIsAdjusted) {
                    adjustState.reset();
                }
            }
            FreeformResizeGuide freeformResizeGuide = this.mFreeformResizeGuide;
            Rect rect = this.mRepositionTaskBounds;
            WindowDecoration windowDecoration = this.mWindowDecoration;
            if (freeformResizeGuide != null && this.mResizing) {
                PointF pointF = this.mRepositionStartPoint;
                int round = Math.round(f - pointF.x);
                int round2 = Math.round(f2 - pointF.y);
                rect.set(this.mTaskBoundsAtDragStart);
                int i = rect.left;
                int i2 = rect.top;
                int i3 = rect.right;
                int i4 = rect.bottom;
                int i5 = i3 - i;
                int i6 = i4 - i2;
                int i7 = this.mCtrlType;
                if ((i7 & 1) != 0) {
                    i5 = Math.max(taskMotionController.mMinVisibleWidth, i5 - round);
                } else if ((i7 & 2) != 0) {
                    i5 = Math.max(taskMotionController.mMinVisibleWidth, i5 + round);
                }
                int i8 = this.mCtrlType;
                if ((i8 & 4) != 0) {
                    i6 = Math.max(this.mMinVisibleHeight, i6 - round2);
                } else if ((i8 & 8) != 0) {
                    i6 = Math.max(this.mMinVisibleHeight, i6 + round2);
                }
                int i9 = this.mCtrlType;
                if ((i9 & 1) != 0) {
                    i = i3 - i5;
                } else {
                    i3 = i + i5;
                }
                if ((i9 & 4) != 0) {
                    i2 = i4 - i6;
                } else {
                    i4 = i2 + i6;
                }
                rect.set(i, i2, i3, i4);
                if (!windowDecoration.mIsDexMode) {
                    this.mFreeformResizeGuide.setNotAdjustedBounds(rect);
                }
                this.mFreeformResizeGuide.adjustMinMaxSize(rect);
                if (!windowDecoration.mIsDexMode) {
                    this.mFreeformResizeGuide.handleResizeGesture(rect, (int) f, (int) f2);
                }
                if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && this.mFreeformResizeGuide.asSizeCompatResizeGuide() != null && SizeCompatInfo.isDragResizable(windowDecoration.mTaskInfo.sizeCompatInfo)) {
                    this.mFreeformResizeGuide.asSizeCompatResizeGuide().adjustBounds(windowDecoration.mTaskInfo.sizeCompatInfo, this.mCtrlType, this.mRepositionTaskBounds, this.mTaskBoundsAtDragStart, false, (Consumer) null);
                }
                Rect rect2 = this.mTmpRect2;
                rect2.set(rect);
                if (CoreRune.MW_CAPTION_SHELL && multitaskingWindowDecoration != null && !this.mFreeformResizeGuide.needToFullscreenTransition()) {
                    rect2.top -= multitaskingWindowDecoration.getCaptionVisibleHeight();
                }
                this.mFreeformResizeGuide.show(rect2);
                this.mHasMoved = true;
            } else if (isDexSnappingInNonFreeform()) {
                DexSnappingGuide dexSnappingGuide = this.mDexSnappingGuide;
                if (dexSnappingGuide != null) {
                    this.mLastSnapType = dexSnappingGuide.show(f, f2, windowDecoration.mTaskInfo, this.mToolType, getFreeformThickness$1());
                    this.mHasMoved = true;
                } else {
                    this.mHasMoved = false;
                }
            } else {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                if (changeBounds(windowContainerTransaction, f, f2, false)) {
                    if (!this.mHasMoved && this.mCtrlType != 0) {
                        windowContainerTransaction.setDragResizing(windowDecoration.mTaskInfo.token, true);
                    }
                    if (multitaskingWindowDecoration != null) {
                        multitaskingWindowDecoration.setupCaptionColor();
                    }
                    if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        int i10 = rect.left;
                        if (multitaskingWindowDecoration != null) {
                            i10 = (int) (multitaskingWindowDecoration.mFreeformStashState.getStashScaleOffsetX(rect.width()) + i10);
                        }
                        transaction.setPosition(windowDecoration.mTaskSurface, i10, rect.top).apply();
                        if (this.mDexSnappingGuide != null && supportDexSnapping()) {
                            this.mLastSnapType = this.mDexSnappingGuide.show(f, f2, windowDecoration.mTaskInfo, this.mToolType, getFreeformThickness$1());
                        }
                    } else {
                        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                    }
                    this.mHasMoved = true;
                }
            }
            if (!CoreRune.MW_FREEFORM_DISMISS_VIEW || windowDecoration.mIsDexMode || this.mResizing || (freeformDragListener = this.mDragPositioningListener) == null || isDexSnappingInNonFreeform()) {
                return;
            }
            PointF pointF2 = freeformDragListener.mTmpPoint;
            pointF2.set(f, f2);
            freeformDragListener.mDismissButtonManager.updateDismissTargetView(pointF2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:0x0229, code lost:
    
        if (r3 == false) goto L135;
     */
    @Override // com.android.p038wm.shell.windowdecor.DragPositioningCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDragPositioningStart(float f, float f2, int i) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        boolean z;
        MultitaskingWindowDecoration multitaskingWindowDecoration2;
        if (this.mImeAnimating) {
            this.mTaskMotionController.mAllowTouches = false;
        }
        boolean z2 = true;
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            if (!this.mTaskMotionController.mAllowTouches) {
                return;
            } else {
                this.mIsUserInteracting = true;
            }
        }
        this.mHasMoved = false;
        DragStartListener dragStartListener = this.mDragStartListener;
        int i2 = this.mWindowDecoration.mTaskInfo.taskId;
        dragStartListener.getClass();
        this.mCtrlType = i;
        MultitaskingWindowDecoration.AdjustState adjustState = this.mMultiTaskingDecor.mAdjustState;
        if (adjustState.mIsAdjusted) {
            this.mTaskBoundsAtDragStart.set(adjustState.mAdjustingBounds);
        } else {
            PhysicsAnimator physicsAnimator = this.mTaskMotionController.mTemporaryBoundsPhysicsAnimator;
            if (physicsAnimator != null && physicsAnimator.isRunning()) {
                this.mTmpRect.setEmpty();
                this.mTaskMotionController.cancelBoundsAnimator(this.mTmpRect, "drag-touch");
                this.mTaskBoundsAtDragStart.set(this.mTmpRect);
            } else {
                this.mTaskBoundsAtDragStart.set(this.mWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
            }
        }
        this.mRepositionStartPoint.set(f, f2);
        int i3 = this.mWindowDecoration.mTaskInfo.displayId;
        Context displayContext = this.mDisplayController.getDisplayContext(i3);
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            if (!this.mWindowDecoration.mTaskInfo.configuration.isDexMode()) {
                Point point = new Point();
                TaskMotionController taskMotionController = this.mTaskMotionController;
                Rect rect = taskMotionController.mTmpRect;
                rect.setEmpty();
                synchronized (taskMotionController) {
                    try {
                        TaskMotionController.TaskMotionInfo taskMotionInfo = taskMotionController.mTaskMotionInfo;
                        if (taskMotionInfo != null) {
                            int i4 = 2;
                            while (true) {
                                if (i4 <= 3) {
                                    TaskMotionAnimator taskMotionAnimator = (TaskMotionAnimator) taskMotionInfo.mMotionAnimators.get(Integer.valueOf(i4));
                                    if (taskMotionAnimator != null && taskMotionAnimator.mAnimation.isAnimating()) {
                                        taskMotionAnimator.mAnimation.getDragBounds(rect);
                                        break;
                                    }
                                    i4++;
                                } else {
                                    break;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                synchronized (taskMotionController) {
                    TaskMotionController.TaskMotionInfo taskMotionInfo2 = taskMotionController.mTaskMotionInfo;
                    if (taskMotionInfo2 != null) {
                        taskMotionInfo2.clearAnimator(false);
                    }
                }
                Rect rect2 = taskMotionController.mTmpRect;
                point.x = rect2.left;
                point.y = rect2.top;
                if (!rect2.isEmpty()) {
                    this.mTaskBoundsAtDragStart.offsetTo(point.x, point.y);
                }
            }
            if (!this.mResizing) {
                this.mTaskMotionController.addTaskToMotionInfo(this.mWindowDecoration.mTaskInfo);
            }
            if (!this.mTaskMotionController.mLastReportedTaskBounds.isEmpty() && this.mWindowDecoration.mTaskInfo.equals(this.mTaskMotionController.mLastTaskInfo)) {
                this.mTaskBoundsAtDragStart.set(this.mTaskMotionController.mLastReportedTaskBounds);
            }
            this.mTaskMotionController.mLastReportedTaskBounds.setEmpty();
        }
        if (this.mWindowDecoration.mTaskInfo.getWindowingMode() == 5) {
            boolean z3 = this.mCtrlType != 0;
            this.mResizing = z3;
            if (z3) {
                if (CoreRune.MW_CAPTION_SHELL_DEX && (multitaskingWindowDecoration2 = this.mMultiTaskingDecor) != null && multitaskingWindowDecoration2.mIsDexEnabled) {
                    if (multitaskingWindowDecoration2.mSliderPopup != null && multitaskingWindowDecoration2.mIsSliderPopupShowing) {
                        multitaskingWindowDecoration2.closeSliderPopup();
                    }
                }
                WindowDecoration windowDecoration = this.mWindowDecoration;
                ComponentName componentName = windowDecoration.mTaskInfo.realActivity;
                String packageName = componentName != null ? componentName.getPackageName() : null;
                if (CoreRune.MT_DEX_SIZE_COMPAT_DRAG && SizeCompatInfo.isDragDexSizeCompat(windowDecoration.mTaskInfo.sizeCompatInfo)) {
                    this.mFreeformResizeGuide = new DexSizeCompatResizeGuide(displayContext, packageName);
                } else if (windowDecoration.mIsDexMode) {
                    this.mFreeformResizeGuide = new FreeformResizeGuide(displayContext, getDexTaskDockingState(), packageName);
                } else {
                    this.mFreeformResizeGuide = new FreeformResizeGuide(displayContext, packageName);
                }
                this.mFreeformResizeGuide.setCtrlType(this.mCtrlType);
                int i5 = windowDecoration.mTaskInfo.displayId;
                DisplayController displayController = this.mDisplayController;
                DisplayLayout displayLayout = displayController.getDisplayLayout(i5);
                Rect rect3 = this.mTmpRect;
                displayLayout.getDisplayBounds(rect3);
                Rect rect4 = this.mTaskBoundsAtDragStart;
                this.mFreeformResizeGuide.updateMinMaxSizeIfNeeded(windowDecoration.mTaskInfo, rect3, rect4.width() >= rect4.height());
                if (!windowDecoration.mIsDexMode) {
                    InsetsState insetsState = displayController.getInsetsState(i5);
                    displayController.getDisplayLayout(i5).getStableBounds(rect3, false);
                    this.mFreeformResizeGuide.updateResizeGestureInfo(insetsState.getDisplayFrame(), rect3);
                }
            }
            this.mImeShowing = this.mTaskOrganizer.isTargetTaskImeShowing(this.mWindowDecoration.mTaskInfo.displayId);
        }
        if ((this.mWindowDecoration.mTaskInfo.getWindowingMode() == 5) && !this.mResizing) {
            this.mMinVisibleHeight = MultiWindowUtils.dipToPixel(32, displayContext.getResources().getDisplayMetrics());
        }
        if (supportDexSnapping()) {
            MultitaskingWindowDecoration multitaskingWindowDecoration3 = this.mMultiTaskingDecor;
            if (multitaskingWindowDecoration3 != null) {
                SplitScreenController splitScreenController = multitaskingWindowDecoration3.mSplitScreenController;
                if (splitScreenController != null) {
                    BooleanSupplier booleanSupplier = splitScreenController.mIsKeyguardOccludedAndShowingSupplier;
                    if (booleanSupplier != null ? booleanSupplier.getAsBoolean() : false) {
                        z = true;
                    }
                }
                z = false;
            }
            z2 = false;
            if (!z2) {
                InsetsState insetsState2 = this.mDisplayController.getInsetsState(i3);
                this.mTmpRect2.set(insetsState2.getDisplayFrame());
                Rect rect5 = this.mTmpRect2;
                rect5.inset(insetsState2.calculateInsets(rect5, WindowInsets.Type.systemBars() | WindowInsets.Type.ime(), false));
                DexSnappingGuide dexSnappingGuide = new DexSnappingGuide(displayContext);
                this.mDexSnappingGuide = dexSnappingGuide;
                Rect rect6 = this.mTmpRect2;
                int i6 = this.mLastSnapType;
                dexSnappingGuide.mVisibleFrame.set(rect6);
                dexSnappingGuide.mLastSnapType = i6;
            }
        }
        if (!CoreRune.MW_FREEFORM_DISMISS_VIEW || this.mWindowDecoration.mIsDexMode || this.mResizing || this.mDragPositioningListener == null || isDexSnappingInNonFreeform()) {
            return;
        }
        FreeformDragPositioningController.FreeformDragListener freeformDragListener = this.mDragPositioningListener;
        PointF pointF = freeformDragListener.mTmpPoint;
        pointF.set(f, f2);
        Insets insets = Insets.NONE;
        DismissButtonManager dismissButtonManager = freeformDragListener.mDismissButtonManager;
        if (!dismissButtonManager.isAttachedToWindow()) {
            dismissButtonManager.createDismissButtonView();
            dismissButtonManager.createOrUpdateWrapper();
        }
        dismissButtonManager.setVisibility(0);
        dismissButtonManager.show();
        dismissButtonManager.updateDismissTargetView(pointF);
        if (!CoreRune.MW_CAPTION_SHELL_SHADOW_ANIM || (multitaskingWindowDecoration = this.mMultiTaskingDecor) == null) {
            return;
        }
        multitaskingWindowDecoration.playShadowAnimation(null, false);
    }

    public final void removeTaskToMotionInfo(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        TaskMotionController taskMotionController = this.mTaskMotionController;
        synchronized (taskMotionController) {
            TaskMotionController.TaskMotionInfo taskMotionInfo = taskMotionController.mTaskMotionInfo;
            if (taskMotionInfo != null) {
                taskMotionInfo.clearAnimator(z);
                taskMotionController.mTaskMotionInfo = null;
                if (TaskMotionController.DEBUG) {
                    Slog.d("TaskMotionController", "removeTaskToMotionInfo: taskInfo=" + runningTaskInfo);
                }
            }
        }
    }

    public final void resetStashedFreeform(boolean z) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = this.mMultiTaskingDecor;
        if (multitaskingWindowDecoration == null) {
            return;
        }
        Rect rect = this.mRepositionTaskBounds;
        TaskMotionController taskMotionController = this.mTaskMotionController;
        if (z) {
            Rect rect2 = new Rect();
            if (taskMotionController.mCanceled) {
                rect2.set(rect);
            } else {
                rect2.set(multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
            }
            taskMotionController.scheduleAnimateRestore(rect2, multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash, false);
        } else {
            Rect rect3 = multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash;
            DisplayLayout displayLayout = taskMotionController.mDisplayController.getDisplayLayout(taskMotionController.mWindowDecoration.mTaskInfo.displayId);
            if (displayLayout != null) {
                Rect rect4 = new Rect();
                Rect rect5 = new Rect();
                rect4.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                displayLayout.getStableBounds(rect5, false);
                int width = rect5.width() - (taskMotionController.mScreenEdgeInset * 2);
                if (width < rect3.width()) {
                    rect3.right = rect3.left + width;
                }
                if (taskMotionController.computeStashState(rect5, rect3, false) != 0) {
                    rect3.offsetTo((rect4.width() - rect3.width()) / 2, (rect4.height() - rect3.height()) / 2);
                }
            }
            multitaskingWindowDecoration.mFreeformStashState.setStashed(0);
            taskMotionController.setStashDim(null, false);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(multitaskingWindowDecoration.mTaskInfo.token, multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
        rect.set(multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
    }

    public final boolean supportDexSnapping() {
        if (this.mWindowDecoration.mIsDexEnabled) {
            return true;
        }
        if (MultiWindowUtils.isTablet()) {
            if (this.mToolType == 3) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController, ShellExecutor shellExecutor, Handler handler) {
        this(shellTaskOrganizer, windowDecoration, displayController, new DragStartListener(r0) { // from class: com.android.wm.shell.windowdecor.TaskPositioner$$ExternalSyntheticLambda0
        }, shellExecutor, handler);
        final int i = 0;
    }

    public TaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController, DragStartListener dragStartListener) {
        this(shellTaskOrganizer, windowDecoration, displayController, dragStartListener, null, null);
    }

    public TaskPositioner(ShellTaskOrganizer shellTaskOrganizer, WindowDecoration windowDecoration, DisplayController displayController, DragStartListener dragStartListener, ShellExecutor shellExecutor, Handler handler) {
        this.mTempBounds = new Rect();
        this.mTaskBoundsAtDragStart = new Rect();
        this.mRepositionStartPoint = new PointF();
        this.mRepositionTaskBounds = new Rect();
        this.mHasMoved = false;
        this.mTmpRect = new Rect();
        this.mIsUserInteracting = false;
        this.mTmpRect2 = new Rect();
        this.mIsOnlyPositionChanged = false;
        this.mLastSnapType = 0;
        this.mToolType = 1;
        this.mFlingCanceled = false;
        this.mImeAnimating = false;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mWindowDecoration = windowDecoration;
        this.mDisplayController = displayController;
        this.mDragStartListener = dragStartListener;
        if (CoreRune.MW_CAPTION_SHELL && windowDecoration != null) {
            this.mMultiTaskingDecor = windowDecoration.asMultitaskingWindowDecoration();
        }
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            this.mTaskMotionController = new TaskMotionController(displayController, shellTaskOrganizer, shellExecutor, handler, this.mMultiTaskingDecor);
        }
        if (!CoreRune.MW_FREEFORM_DISMISS_VIEW || windowDecoration.mIsDexMode) {
            return;
        }
        this.mDragPositioningListener = FreeformDragPositioningController.getInstance(windowDecoration.mContext).mFreeformDragListener;
    }
}
