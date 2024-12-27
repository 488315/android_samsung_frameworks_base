package com.android.server.wm;

import android.R;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Process;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.DisplayInfo;
import android.view.HapticFeedbackConstants;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerPolicyConstants;
import android.widget.Toast;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;

import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.DexSizeCompatResizeGuide;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiWindowPointerEventListener
        implements WindowManagerPolicyConstants.PointerEventListener {
    public static final boolean SAFE_DEBUG = Debug.semIsProductDev();
    public int mDefaultMinimalSizeOfResizableTask;
    public final DisplayContent mDisplayContent;
    public int mFreeformGuideFullscreenDimViewMargin;
    public FreeformResizeGuide mFreeformResizeGuide;
    public int mMinHeight;
    public int mMinWidth;
    public MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public final WindowManagerService mService;
    public DexSizeCompatController.DexSizeCompatPolicy mSizeCompatDragPolicy;
    public SizeCompatInfo mSizeCompatInfo;
    public int mStartX;
    public int mStartY;
    public final AnonymousClass1 mStatusBarReceiver;
    public Task mTask;
    public Rect mTaskBoundsAtDragStart;
    public int mTaskId;
    public boolean mTaskResizable;
    public final int mTouchSlop;
    public final DisplayContent.TaskFromPointSearchResult mTaskFromPointSearchResult =
            new DisplayContent.TaskFromPointSearchResult();
    public final Rect mTmpRect = new Rect();
    public final Rect mInitRect = new Rect();
    public final Rect mContentRect = new Rect();
    public final Rect mNotResizableRect = new Rect();
    public boolean mIsStatusBarShowing = false;
    public boolean mMoving = false;
    public boolean mNotSupport = false;
    public int mTaskOrientation = 0;
    public SemPersonaManager mPersona = null;
    public SemStatusBarManager mStatusBarManager = null;
    public boolean mReadyToFreeform = false;
    public boolean mAdjustedMinimalTaskBounds = false;

    public MultiWindowPointerEventListener(
            WindowManagerService windowManagerService, DisplayContent displayContent) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.wm.MultiWindowPointerEventListener.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (action != null) {
                            switch (action) {
                                case "com.samsung.systemui.statusbar.ANIMATING":
                                case "com.samsung.systemui.statusbar.EXPANDED":
                                    MultiWindowPointerEventListener.this.mIsStatusBarShowing = true;
                                    break;
                                case "com.samsung.systemui.statusbar.COLLAPSED":
                                    MultiWindowPointerEventListener.this.mIsStatusBarShowing =
                                            false;
                                    break;
                            }
                        }
                    }
                };
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        windowManagerService.mContext.registerReceiver(
                broadcastReceiver,
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "com.samsung.systemui.statusbar.ANIMATING",
                        "com.samsung.systemui.statusbar.EXPANDED",
                        "com.samsung.systemui.statusbar.COLLAPSED"));
        this.mTouchSlop = ViewConfiguration.get(windowManagerService.mContext).getScaledTouchSlop();
    }

    public static void printFailureLog(String str, String str2) {
        Slog.w("MultiWindowPointerEventListener", str + " : " + str2);
    }

    public final boolean adjustBoundsIfNeeded(boolean z) {
        if (this.mTaskId == -1 || this.mTask == null) {
            printFailureLog("adjustBoundsIfNeeded", "Task is invalid");
            return false;
        }
        if (this.mTaskBoundsAtDragStart == null) {
            printFailureLog("adjustBoundsIfNeeded", "TaskBoundsAtDragStart is null");
            return false;
        }
        int edgeFlags = this.mMultiWindowEdgeDetector.getEdgeFlags();
        if (z && (!this.mReadyToFreeform || edgeFlags == 0 || this.mNotSupport)) {
            this.mFreeformResizeGuide
                    .asSizeCompatResizeGuide()
                    .cancelAnimation(
                            this.mTmpRect, this.mDisplayContent.getBounds(), (Consumer) null);
            return false;
        }
        if (!this.mReadyToFreeform) {
            this.mTmpRect.set(this.mInitRect);
            return false;
        }
        this.mFreeformResizeGuide
                .asSizeCompatResizeGuide()
                .adjustBounds(
                        this.mSizeCompatInfo,
                        edgeFlags != 5 ? 4 : 5,
                        this.mTmpRect,
                        this.mTaskBoundsAtDragStart,
                        false,
                        (Consumer) null);
        if (!this.mInitRect.contains(this.mTmpRect)) {
            if (z) {
                this.mFreeformResizeGuide
                        .asSizeCompatResizeGuide()
                        .cancelAnimation(
                                this.mTmpRect, this.mDisplayContent.getBounds(), (Consumer) null);
            }
            this.mTmpRect.set(this.mInitRect);
            return false;
        }
        if (!CoreRune.MT_DEX_SIZE_COMPAT_DRAG
                || !SizeCompatInfo.isDragDexSizeCompatRotatable(this.mSizeCompatInfo)) {
            return true;
        }
        this.mTmpRect.set(this.mTaskBoundsAtDragStart);
        return true;
    }

    public final void affordanceAnim(int i) {
        Task rootTask = this.mDisplayContent.getRootTask(1, 1);
        if (rootTask != null) {
            TaskOrganizerController taskOrganizerController =
                    this.mService.mAtmService.mTaskOrganizerController;
            taskOrganizerController.getClass();
            if (rootTask.isOrganized()) {
                TaskOrganizerController.TaskOrganizerPendingEventsQueue pendingEventsQueue =
                        taskOrganizerController
                                .getTaskOrganizerState(rootTask.mTaskOrganizer.asBinder())
                                .getPendingEventsQueue();
                if (TaskOrganizerController.TaskOrganizerPendingEventsQueue
                                .m1070$$Nest$mgetPendingTaskEvent(pendingEventsQueue, rootTask, 1)
                        == null) {
                    TaskOrganizerController.PendingTaskEvent m1070$$Nest$mgetPendingTaskEvent =
                            TaskOrganizerController.TaskOrganizerPendingEventsQueue
                                    .m1070$$Nest$mgetPendingTaskEvent(
                                            pendingEventsQueue, rootTask, 4);
                    if (m1070$$Nest$mgetPendingTaskEvent == null) {
                        m1070$$Nest$mgetPendingTaskEvent =
                                new TaskOrganizerController.PendingTaskEvent(4, rootTask);
                    } else {
                        pendingEventsQueue.mPendingTaskEvents.remove(
                                m1070$$Nest$mgetPendingTaskEvent);
                    }
                    m1070$$Nest$mgetPendingTaskEvent.mGestureFrom = i;
                    pendingEventsQueue.mPendingTaskEvents.add(m1070$$Nest$mgetPendingTaskEvent);
                    taskOrganizerController.mService.mWindowManager.mWindowPlacerLocked
                            .requestTraversal();
                }
            }
            vibrate(127);
        }
    }

    public final boolean interceptFindTargetTaskBounds(Rect rect) {
        int edgeFlags = this.mMultiWindowEdgeDetector.getEdgeFlags();
        if (edgeFlags != 5 && edgeFlags != 9) {
            return false;
        }
        SizeCompatPolicyManager sizeCompatPolicyManager =
                SizeCompatPolicyManager.LazyHolder.sManager;
        Task task = this.mTask;
        sizeCompatPolicyManager.getClass();
        DexSizeCompatController.DexSizeCompatPolicy compatPolicy =
                SizeCompatPolicyManager.getCompatPolicy(task, true);
        DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy = null;
        if (compatPolicy == null || !CoreRune.MT_DEX_SIZE_COMPAT_DRAG) {
            compatPolicy = null;
        }
        if (compatPolicy != null && compatPolicy.isEnabled()) {
            Task task2 = compatPolicy.mTask;
            if (task2.inFullscreenWindowingMode() && task2.isVisible()) {
                dexSizeCompatPolicy = compatPolicy;
            }
        }
        this.mSizeCompatDragPolicy = dexSizeCompatPolicy;
        if (dexSizeCompatPolicy == null) {
            return false;
        }
        this.mTaskResizable = true;
        if (this.mSizeCompatInfo == null) {
            this.mSizeCompatInfo = new SizeCompatInfo();
        }
        this.mSizeCompatDragPolicy.fillSizeCompatInfoForDrag(this.mSizeCompatInfo);
        DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy2 =
                this.mSizeCompatDragPolicy;
        SizeCompatInfo sizeCompatInfo = this.mSizeCompatInfo;
        dexSizeCompatPolicy2.getClass();
        float f =
                SizeCompatInfo.isDragDexSizeCompatRotatable(sizeCompatInfo)
                        ? DexSizeCompatController.LazyHolder.sInstance.mDefaultScale
                        : DexSizeCompatController.LazyHolder.sInstance.mDefaultScale;
        dexSizeCompatPolicy2.mTmpRect.set(
                0,
                0,
                CompatUtils.applyScale(sizeCompatInfo.getMaxWidth(), f),
                CompatUtils.applyScale(sizeCompatInfo.getMaxHeight(), f));
        dexSizeCompatPolicy2.mTmpRect.offsetTo(rect.left, rect.top);
        if (this.mTaskBoundsAtDragStart == null) {
            this.mTaskBoundsAtDragStart = new Rect();
        }
        this.mTaskBoundsAtDragStart.set(this.mTmpRect);
        return true;
    }

    public final boolean isAllowCornerGestureState() {
        if (!CoreRune.MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW
                || !MultiWindowCoreState.MW_ENABLED
                || !MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED
                || (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY
                        && MultiWindowUtils.isInSubDisplay(this.mService.mContext))) {
            return false;
        }
        if (this.mMultiWindowEdgeDetector == null) {
            printFailureLog("isAllowCornerGestureState", "mMultiWindowEdgeDetector is null");
            return false;
        }
        if (this.mIsStatusBarShowing) {
            printFailureLog("isAllowCornerGestureState", "statusBar is showing");
            return false;
        }
        SemStatusBarManager semStatusBarManager = this.mStatusBarManager;
        if (semStatusBarManager != null && semStatusBarManager.isPanelExpanded()) {
            printFailureLog("isAllowCornerGestureState", "quick panel is expanded");
            return false;
        }
        if (this.mDisplayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
            printFailureLog("isAllowCornerGestureState", "split activated");
            return false;
        }
        if (this.mService.isKeyguardLocked()) {
            printFailureLog("isAllowCornerGestureState", "keyguard is currently locked");
            return false;
        }
        WindowState windowState = this.mDisplayContent.mCurrentFocus;
        if (windowState == null) {
            return true;
        }
        int i = windowState.mAttrs.type;
        if (i == 2226) {
            printFailureLog("isAllowCornerGestureState", "edge panel is expanded");
            return false;
        }
        if (i != 2440) {
            return true;
        }
        printFailureLog("isAllowCornerGestureState", "smart select running");
        return false;
    }

    public final void loadDimens() {
        this.mDefaultMinimalSizeOfResizableTask =
                this.mService
                        .mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.floating_toolbar_vertical_margin);
        this.mFreeformGuideFullscreenDimViewMargin =
                this.mService
                        .mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.indeterminate_progress_alpha_33);
        if (SAFE_DEBUG) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mDefaultMinimalSizeOfResizableTask="),
                    this.mDefaultMinimalSizeOfResizableTask,
                    "MultiWindowPointerEventListener");
        }
    }

    public final void onPointerEvent(MotionEvent motionEvent) {
        boolean z;
        Task task;
        FreeformResizeGuide freeformResizeGuide;
        FreeformResizeGuide freeformResizeGuide2;
        FreeformResizeGuide freeformResizeGuide3;
        FreeformResizeGuide freeformResizeGuide4;
        int actionMasked = motionEvent.getActionMasked();
        boolean isEdge = this.mMultiWindowEdgeDetector.isEdge();
        final int x = (int) motionEvent.getX();
        final int y = (int) motionEvent.getY();
        boolean readyToFreeform = this.mMultiWindowEdgeDetector.readyToFreeform(x, y);
        if (this.mReadyToFreeform != readyToFreeform) {
            this.mReadyToFreeform = readyToFreeform;
            z = true;
        } else {
            z = false;
        }
        if (actionMasked == 0) {
            if (isAllowCornerGestureState()
                    && this.mMultiWindowEdgeDetector.onTouchEvent(motionEvent)) {
                DisplayInfo displayInfo = this.mDisplayContent.mDisplayInfo;
                if (displayInfo != null) {
                    this.mNotResizableRect.set(
                            0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
                }
                Rect rect = this.mInitRect;
                WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        this.mDisplayContent.getItemFromTaskDisplayAreas(
                                new Function() { // from class:
                                                 // com.android.server.wm.MultiWindowPointerEventListener$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        MultiWindowPointerEventListener
                                                multiWindowPointerEventListener =
                                                        MultiWindowPointerEventListener.this;
                                        int i = x;
                                        int i2 = y;
                                        TaskDisplayArea taskDisplayArea = (TaskDisplayArea) obj;
                                        DisplayContent.TaskFromPointSearchResult
                                                taskFromPointSearchResult =
                                                        multiWindowPointerEventListener
                                                                .mTaskFromPointSearchResult;
                                        taskDisplayArea.getBounds(
                                                ((WindowContainer) taskDisplayArea).mTmpRect);
                                        int i3 = -1;
                                        if (((WindowContainer) taskDisplayArea)
                                                .mTmpRect.contains(i, i2)) {
                                            int size = taskDisplayArea.mChildren.size() - 1;
                                            while (true) {
                                                if (size < 0) {
                                                    break;
                                                }
                                                Task asTask =
                                                        ((WindowContainer)
                                                                        taskDisplayArea.mChildren
                                                                                .get(size))
                                                                .asTask();
                                                if (asTask != null
                                                        && asTask.getTopVisibleAppMainWindow(false)
                                                                != null) {
                                                    asTask.getDimBounds(
                                                            ((WindowContainer) taskDisplayArea)
                                                                    .mTmpRect);
                                                    if (((WindowContainer) taskDisplayArea)
                                                            .mTmpRect.contains(i, i2)) {
                                                        if (taskFromPointSearchResult != null) {
                                                            taskFromPointSearchResult.mTask =
                                                                    asTask;
                                                        }
                                                        i3 = asTask.mTaskId;
                                                    }
                                                }
                                                size--;
                                            }
                                        }
                                        return Integer.valueOf(i3);
                                    }
                                });
                        Task task2 = this.mTaskFromPointSearchResult.mTask;
                        if (task2 == null) {
                            printFailureLog("findTargetTaskBounds", "task is null");
                            WindowManagerService.resetPriorityAfterLockedSection();
                        } else {
                            Slog.i(
                                    "MultiWindowPointerEventListener",
                                    "findTargetTaskBounds: " + task2);
                            if (!task2.isActivityTypeStandard()) {
                                printFailureLog(
                                        "findTargetTaskBounds", "activity type is not standard");
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } else if (task2.isAnimatingByRecents()) {
                                printFailureLog(
                                        "findTargetTaskBounds", "task animating by recents");
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } else if (task2.getWindowingMode() == 1) {
                                ActivityRecord topVisibleActivity =
                                        task2.getTopVisibleActivity(true, false);
                                if (topVisibleActivity == null
                                        || !topVisibleActivity.isRelaunching()) {
                                    task2.getBounds(rect);
                                    this.mTaskId = task2.mTaskId;
                                    this.mTask = task2;
                                    this.mTaskResizable = task2.isResizeable(true);
                                    InsetsState insetsState =
                                            this.mDisplayContent.mInsetsStateController.mState;
                                    this.mContentRect.set(insetsState.getDisplayFrame());
                                    Rect rect2 = this.mContentRect;
                                    rect2.inset(
                                            insetsState.calculateInsets(
                                                    rect2,
                                                    WindowInsets.Type.systemBars()
                                                            | WindowInsets.Type.displayCutout(),
                                                    false));
                                    rect.set(this.mContentRect);
                                    Rect rect3 = this.mContentRect;
                                    int i = rect3.left;
                                    int i2 = this.mFreeformGuideFullscreenDimViewMargin;
                                    rect.set(i + i2, i2, rect3.right - i2, rect3.bottom - i2);
                                    int i3 = task2.mMinWidth;
                                    int i4 = task2.mMinHeight;
                                    if (i3 > 0) {
                                        this.mMinWidth = i3;
                                    } else {
                                        this.mMinWidth = this.mDefaultMinimalSizeOfResizableTask;
                                    }
                                    if (i4 > 0) {
                                        this.mMinHeight = i4;
                                    } else {
                                        this.mMinHeight = this.mDefaultMinimalSizeOfResizableTask;
                                    }
                                    if (CoreRune.MT_SIZE_COMPAT_POLICY_DRAG
                                            && interceptFindTargetTaskBounds(rect)) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    } else {
                                        int i5 = task2.mResizeMode;
                                        if (i5 == 6 || i5 == 5 || i5 == 7) {
                                            if (i5 == 5) {
                                                this.mTaskOrientation = 2;
                                            } else if (i5 == 6) {
                                                this.mTaskOrientation = 1;
                                            } else if (i5 == 7) {
                                                this.mTaskOrientation =
                                                        this.mContentRect.width()
                                                                        > this.mContentRect.height()
                                                                ? 2
                                                                : 1;
                                            }
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                    this.mTmpRect.set(this.mInitRect);
                                } else {
                                    printFailureLog(
                                            "findTargetTaskBounds",
                                            "task is relaunching, t=" + task2.mTaskId);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            } else {
                                printFailureLog(
                                        "findTargetTaskBounds", "task is not docked or full");
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        reset();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                this.mStartX = x;
                this.mStartY = y;
                return;
            }
            return;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (isEdge) {
                    if (this.mIsStatusBarShowing) {
                        printFailureLog("onPointerEvent", "statusBar is showing");
                        this.mMultiWindowEdgeDetector.reset();
                        return;
                    }
                    if (Math.abs(x - this.mStartX) > this.mTouchSlop
                            || Math.abs(y - this.mStartY) > this.mTouchSlop) {
                        if (!this.mMoving
                                && this.mReadyToFreeform
                                && !this.mMultiWindowEdgeDetector.isValidGesture(
                                        x - this.mStartX, y - this.mStartY)) {
                            printFailureLog("onPointerEvent", "invalid gesture");
                            this.mMultiWindowEdgeDetector.reset();
                            return;
                        }
                        if (!this.mTaskResizable && this.mReadyToFreeform) {
                            printFailureLog("onPointerEvent", "task is not resizable");
                            if (this.mMoving) {
                                return;
                            }
                            affordanceAnim(this.mMultiWindowEdgeDetector.getEdgeFlags());
                            Toast.makeText(
                                            new ContextThemeWrapper(
                                                    this.mService.mContext,
                                                    R.style.Theme.DeviceDefault.Light),
                                            this.mService.mContext.getString(
                                                    R.string.indeterminate_progress_09),
                                            0)
                                    .show();
                            this.mMoving = true;
                            this.mNotSupport = true;
                            return;
                        }
                        boolean z2 = CoreRune.MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW;
                        if (z2 && this.mReadyToFreeform && this.mFreeformResizeGuide == null) {
                            if (!CoreRune.MT_SIZE_COMPAT_POLICY_DRAG
                                    || this.mSizeCompatDragPolicy == null) {
                                this.mFreeformResizeGuide = new FreeformResizeGuide((Context) null);
                            } else {
                                this.mFreeformResizeGuide =
                                        new DexSizeCompatResizeGuide(
                                                (Context) null, (ComponentName) null);
                            }
                        }
                        if (this.mPersona == null) {
                            this.mPersona =
                                    (SemPersonaManager)
                                            this.mService.mContext.getSystemService("persona");
                        }
                        SemPersonaManager semPersonaManager = this.mPersona;
                        if (semPersonaManager != null && semPersonaManager.isKnoxKeyguardShown()) {
                            printFailureLog("onPointerEvent", "KnoxKeyguard is not resizable");
                            if (z2 && (freeformResizeGuide4 = this.mFreeformResizeGuide) != null) {
                                freeformResizeGuide4.show(this.mNotResizableRect);
                            }
                            if (this.mMoving) {
                                return;
                            }
                            affordanceAnim(this.mMultiWindowEdgeDetector.getEdgeFlags());
                            Toast.makeText(
                                            new ContextThemeWrapper(
                                                    this.mService.mContext,
                                                    R.style.Theme.DeviceDefault.Light),
                                            this.mService.mContext.getString(
                                                    R.string.indeterminate_progress_09),
                                            0)
                                    .show();
                            this.mMoving = true;
                            this.mNotSupport = true;
                            return;
                        }
                        if (this.mReadyToFreeform) {
                            int edgeFlags = this.mMultiWindowEdgeDetector.getEdgeFlags();
                            MultiWindowEdgeDetector.Utils.applyResizeRect(
                                    this.mTmpRect, edgeFlags, x, y);
                            boolean adjustMinimalTaskBounds =
                                    MultiWindowEdgeDetector.Utils.adjustMinimalTaskBounds(
                                            this.mTmpRect,
                                            edgeFlags,
                                            this.mMinWidth,
                                            this.mMinHeight);
                            int i6 = this.mTaskOrientation;
                            if (i6 != 0) {
                                Rect rect4 = this.mTmpRect;
                                if (i6 == 1) {
                                    if (rect4.height() / rect4.width() < 1.2f) {
                                        rect4.top = rect4.bottom - Math.round(rect4.width() * 1.2f);
                                    }
                                } else if (i6 == 2 && rect4.width() / rect4.height() < 1.2f) {
                                    int round = Math.round(rect4.height() * 1.2f);
                                    if (edgeFlags == 5) {
                                        rect4.left = rect4.right - round;
                                    } else if (edgeFlags == 9) {
                                        rect4.right = rect4.left + round;
                                    }
                                }
                            }
                            if (z2 && (freeformResizeGuide3 = this.mFreeformResizeGuide) != null) {
                                if (adjustMinimalTaskBounds) {
                                    if (freeformResizeGuide3.updateGuideState(1)) {
                                        vibrate(41);
                                        this.mAdjustedMinimalTaskBounds = true;
                                    }
                                } else if (freeformResizeGuide3.updateGuideState(0)) {
                                    if (!this.mAdjustedMinimalTaskBounds) {
                                        vibrate(49);
                                    }
                                    this.mAdjustedMinimalTaskBounds = false;
                                }
                            }
                            if (!this.mMoving) {
                                this.mMoving = true;
                                this.mNotSupport = false;
                            }
                        } else {
                            this.mTmpRect.set(this.mInitRect);
                            if (!readyToFreeform && z) {
                                vibrate(49);
                            }
                        }
                        if (z2 && (freeformResizeGuide2 = this.mFreeformResizeGuide) != null) {
                            if (CoreRune.MT_SIZE_COMPAT_POLICY_DRAG
                                    && freeformResizeGuide2.asSizeCompatResizeGuide() != null
                                    && this.mSizeCompatDragPolicy != null) {
                                adjustBoundsIfNeeded(false);
                            }
                            this.mFreeformResizeGuide.show(this.mTmpRect);
                        }
                        WindowState currentInputMethodWindow =
                                this.mService.mRoot.getCurrentInputMethodWindow();
                        if (currentInputMethodWindow == null
                                || !currentInputMethodWindow.isVisibleNow()) {
                            return;
                        }
                        this.mService.mH.post(
                                new Runnable() { // from class:
                                                 // com.android.server.wm.MultiWindowPointerEventListener$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MultiWindowPointerEventListener
                                                multiWindowPointerEventListener =
                                                        MultiWindowPointerEventListener.this;
                                        multiWindowPointerEventListener.getClass();
                                        Slog.w("MultiWindowPointerEventListener", "Hide Ime");
                                        InputMethodManagerInternal inputMethodManagerInternal =
                                                InputMethodManagerInternal.get();
                                        int i7 =
                                                multiWindowPointerEventListener
                                                        .mDisplayContent
                                                        .mDisplayId;
                                        inputMethodManagerInternal.hideAllInputMethods(63);
                                    }
                                });
                        return;
                    }
                    return;
                }
                return;
            }
            if (actionMasked == 3) {
                reset();
                return;
            } else if (actionMasked != 6) {
                return;
            }
        }
        if (isEdge && !this.mReadyToFreeform) {
            printFailureLog("onPointerEvent", "not ready to freeform");
        }
        if (!CoreRune.MT_SIZE_COMPAT_POLICY_DRAG
                || (freeformResizeGuide = this.mFreeformResizeGuide) == null
                || freeformResizeGuide.asSizeCompatResizeGuide() == null
                || this.mSizeCompatDragPolicy == null) {
            if (!this.mNotSupport && isEdge && this.mReadyToFreeform) {
                int i7 = this.mTaskId;
                if (i7 == -1 || (task = this.mTask) == null) {
                    printFailureLog("onPointerEvent", "task is invalid");
                } else {
                    this.mService
                            .mAtmService
                            .mMultiTaskingController
                            .mH
                            .obtainMessage(
                                    0,
                                    i7,
                                    task.getWindowConfiguration().getRotation(),
                                    new Rect(this.mTmpRect))
                            .sendToTarget();
                }
            }
        } else if (adjustBoundsIfNeeded(true)) {
            this.mSizeCompatDragPolicy.ensureDragBounds(this.mTmpRect);
            this.mService
                    .mAtmService
                    .mMultiTaskingController
                    .mH
                    .obtainMessage(
                            0,
                            this.mTaskId,
                            this.mTask.getWindowConfiguration().getRotation(),
                            new Rect(this.mTmpRect))
                    .sendToTarget();
        }
        reset();
    }

    public final void reset() {
        FreeformResizeGuide freeformResizeGuide;
        this.mTaskFromPointSearchResult.mTask = null;
        this.mTaskId = -1;
        this.mTask = null;
        if (CoreRune.MT_SIZE_COMPAT_POLICY_DRAG) {
            this.mSizeCompatDragPolicy = null;
        }
        this.mTaskResizable = false;
        this.mTaskOrientation = 0;
        this.mMoving = false;
        this.mMinHeight = -1;
        this.mMinWidth = -1;
        this.mAdjustedMinimalTaskBounds = false;
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW
                && (freeformResizeGuide = this.mFreeformResizeGuide) != null) {
            freeformResizeGuide.dismiss();
            this.mFreeformResizeGuide = null;
        }
        this.mMultiWindowEdgeDetector.reset();
    }

    public final void vibrate(int i) {
        WindowManagerPolicy windowManagerPolicy = this.mDisplayContent.mWmService.mPolicy;
        int myUid = Process.myUid();
        String opPackageName = this.mDisplayContent.mWmService.mContext.getOpPackageName();
        ((PhoneWindowManager) windowManagerPolicy)
                .mExt.performHapticFeedback(
                        myUid,
                        HapticFeedbackConstants.semGetVibrationIndex(i),
                        opPackageName,
                        "Swipe for pop-up view",
                        false,
                        false);
    }
}
