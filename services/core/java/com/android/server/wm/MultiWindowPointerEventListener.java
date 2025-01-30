package com.android.server.wm;

import android.R;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.DisplayContent;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class MultiWindowPointerEventListener implements WindowManagerPolicyConstants.PointerEventListener {
    public static final boolean SAFE_DEBUG = Debug.semIsProductDev();
    public int mDefaultMinimalSizeOfResizeableTask;
    public final DisplayContent mDisplayContent;
    public int mFreeformGuideFullscreenDimViewMargin;
    public FreeformResizeGuide mFreeformResizeGuide;
    public int mMinHeight;
    public int mMinWidth;
    public MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public final WindowManagerService mService;
    public SizeCompatDragPolicy mSizeCompatDragPolicy;
    public SizeCompatInfo mSizeCompatInfo;
    public int mStartX;
    public int mStartY;
    public final BroadcastReceiver mStatusBarReceiver;
    public Task mTask;
    public Rect mTaskBoundsAtDragStart;
    public int mTaskId;
    public boolean mTaskResizable;
    public int mTouchSlop;
    public final DisplayContent.TaskIdFromPointSearchResult mTaskIdFromPointSearchResult = new DisplayContent.TaskIdFromPointSearchResult();
    public final Rect mTmpRect = new Rect();
    public final Rect mInitRect = new Rect();
    public final Rect mContentRect = new Rect();
    public final Rect mNotResizeableRect = new Rect();
    public boolean mIsStatusBarShowing = false;
    public boolean mMoving = false;
    public boolean mNotSupport = false;
    public int mTaskOrientation = 0;
    public SemPersonaManager mPersona = null;
    public SemStatusBarManager mStatusBarManager = null;
    public boolean mReadyToFreeform = false;
    public boolean mAdjustedMinimalTaskBounds = false;

    public MultiWindowPointerEventListener(WindowManagerService windowManagerService, DisplayContent displayContent) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.MultiWindowPointerEventListener.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    switch (action) {
                        case "com.samsung.systemui.statusbar.ANIMATING":
                        case "com.samsung.systemui.statusbar.EXPANDED":
                            MultiWindowPointerEventListener.this.mIsStatusBarShowing = true;
                            break;
                        case "com.samsung.systemui.statusbar.COLLAPSED":
                            MultiWindowPointerEventListener.this.mIsStatusBarShowing = false;
                            break;
                    }
                }
            }
        };
        this.mStatusBarReceiver = broadcastReceiver;
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        windowManagerService.mContext.registerReceiver(broadcastReceiver, intentFilter);
        this.mTouchSlop = ViewConfiguration.get(windowManagerService.mContext).getScaledTouchSlop();
    }

    public void onDisplayReady() {
        this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(this.mService.mContext, "Service");
        this.mTaskIdFromPointSearchResult.reset();
        this.mTaskId = -1;
        this.mTask = null;
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG) {
            this.mSizeCompatDragPolicy = null;
        }
        this.mMinHeight = -1;
        this.mMinWidth = -1;
        loadDimens();
        this.mPersona = (SemPersonaManager) this.mService.mContext.getSystemService("persona");
        this.mStatusBarManager = (SemStatusBarManager) this.mService.mContext.getSystemService(SemStatusBarManager.class);
    }

    public void onConfigurationChanged() {
        loadDimens();
        MultiWindowEdgeDetector multiWindowEdgeDetector = this.mMultiWindowEdgeDetector;
        if (multiWindowEdgeDetector != null) {
            multiWindowEdgeDetector.onConfigurationChanged();
        }
    }

    public final void loadDimens() {
        this.mDefaultMinimalSizeOfResizeableTask = this.mService.mContext.getResources().getDimensionPixelSize(R.dimen.dialog_corner_radius);
        this.mFreeformGuideFullscreenDimViewMargin = this.mService.mContext.getResources().getDimensionPixelSize(R.dimen.harmful_app_name_padding_right);
        if (SAFE_DEBUG) {
            Slog.i("MultiWindowPointerEventListener", "mDefaultMinimalSizeOfResizeableTask=" + this.mDefaultMinimalSizeOfResizeableTask);
        }
    }

    public void onPointerEvent(MotionEvent motionEvent) {
        Task task;
        FreeformResizeGuide freeformResizeGuide;
        SizeCompatDragPolicy sizeCompatDragPolicy;
        int actionMasked = motionEvent.getActionMasked();
        boolean isEdge = this.mMultiWindowEdgeDetector.isEdge();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean readyToFreeform = this.mMultiWindowEdgeDetector.readyToFreeform(x, y);
        boolean readyToFreeform2 = setReadyToFreeform(readyToFreeform);
        if (actionMasked == 0) {
            if (isAllowCornerGestureState() && this.mMultiWindowEdgeDetector.onTouchEvent(motionEvent)) {
                DisplayInfo displayInfo = this.mDisplayContent.getDisplayInfo();
                if (displayInfo != null) {
                    this.mNotResizeableRect.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
                }
                if (findTargetTaskBounds(x, y, this.mInitRect)) {
                    this.mTmpRect.set(this.mInitRect);
                } else {
                    reset();
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
                    if (Math.abs(x - this.mStartX) > this.mTouchSlop || Math.abs(y - this.mStartY) > this.mTouchSlop) {
                        if (!this.mMoving && this.mReadyToFreeform && !this.mMultiWindowEdgeDetector.isValidGesture(x - this.mStartX, y - this.mStartY)) {
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
                            showToast();
                            this.mMoving = true;
                            this.mNotSupport = true;
                            return;
                        }
                        if (this.mReadyToFreeform && this.mFreeformResizeGuide == null) {
                            if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && (sizeCompatDragPolicy = this.mSizeCompatDragPolicy) != null) {
                                this.mFreeformResizeGuide = sizeCompatDragPolicy.createCompatResizeGuide();
                            } else {
                                this.mFreeformResizeGuide = new FreeformResizeGuide((Context) null);
                            }
                        }
                        if (this.mPersona == null) {
                            this.mPersona = (SemPersonaManager) this.mService.mContext.getSystemService("persona");
                        }
                        SemPersonaManager semPersonaManager = this.mPersona;
                        if (semPersonaManager != null && semPersonaManager.isKnoxKeyguardShown()) {
                            printFailureLog("onPointerEvent", "KnoxKeyguard is not resizable");
                            FreeformResizeGuide freeformResizeGuide2 = this.mFreeformResizeGuide;
                            if (freeformResizeGuide2 != null) {
                                freeformResizeGuide2.show(this.mNotResizeableRect);
                            }
                            if (this.mMoving) {
                                return;
                            }
                            affordanceAnim(this.mMultiWindowEdgeDetector.getEdgeFlags());
                            showToast();
                            this.mMoving = true;
                            this.mNotSupport = true;
                            return;
                        }
                        if (this.mReadyToFreeform) {
                            int edgeFlags = this.mMultiWindowEdgeDetector.getEdgeFlags();
                            MultiWindowEdgeDetector.Utils.applyResizeRect(this.mTmpRect, edgeFlags, x, y);
                            boolean adjustMinimalTaskBounds = MultiWindowEdgeDetector.Utils.adjustMinimalTaskBounds(this.mTmpRect, edgeFlags, this.mMinWidth, this.mMinHeight);
                            if (this.mTaskOrientation != 0) {
                                adjustAspectRatioIfNeeded(this.mTmpRect, edgeFlags);
                            }
                            FreeformResizeGuide freeformResizeGuide3 = this.mFreeformResizeGuide;
                            if (freeformResizeGuide3 != null) {
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
                            if (!readyToFreeform && readyToFreeform2) {
                                vibrate(49);
                            }
                        }
                        FreeformResizeGuide freeformResizeGuide4 = this.mFreeformResizeGuide;
                        if (freeformResizeGuide4 != null) {
                            if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && freeformResizeGuide4.asSizeCompatResizeGuide() != null && this.mSizeCompatDragPolicy != null) {
                                adjustBoundsIfNeeded(false);
                            }
                            this.mFreeformResizeGuide.show(this.mTmpRect);
                        }
                        hideImeIfPossible();
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
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && (freeformResizeGuide = this.mFreeformResizeGuide) != null && freeformResizeGuide.asSizeCompatResizeGuide() != null && this.mSizeCompatDragPolicy != null) {
            if (adjustBoundsIfNeeded(true)) {
                this.mSizeCompatDragPolicy.ensureDragBounds(this.mTmpRect);
                this.mService.mAtmService.mMultiTaskingController.f1743mH.obtainMessage(0, this.mTaskId, this.mTask.getWindowConfiguration().getRotation(), new Rect(this.mTmpRect)).sendToTarget();
            }
        } else if (!this.mNotSupport && isEdge && this.mReadyToFreeform) {
            int i = this.mTaskId;
            if (i == -1 || (task = this.mTask) == null) {
                printFailureLog("onPointerEvent", "task is invalid");
            } else {
                this.mService.mAtmService.mMultiTaskingController.f1743mH.obtainMessage(0, i, task.getWindowConfiguration().getRotation(), new Rect(this.mTmpRect)).sendToTarget();
            }
        }
        reset();
    }

    public final void affordanceAnim(int i) {
        Task rootTask = this.mDisplayContent.getRootTask(1, 1);
        if (rootTask != null) {
            this.mService.mAtmService.mTaskOrganizerController.requestAffordanceAnim(rootTask, i);
            vibrate(127);
        }
    }

    public boolean isAllowCornerGestureState() {
        if (!MultiWindowCoreState.MW_ENABLED || !MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED || (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY && MultiWindowUtils.isInSubDisplay(this.mService.mContext))) {
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
        int i = windowState.getAttrs().type;
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

    public final void printFailureLog(String str, String str2) {
        Slog.w("MultiWindowPointerEventListener", str + " : " + str2);
    }

    public final void reset() {
        this.mTaskIdFromPointSearchResult.reset();
        this.mTaskId = -1;
        this.mTask = null;
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG) {
            this.mSizeCompatDragPolicy = null;
        }
        this.mTaskResizable = false;
        this.mTaskOrientation = 0;
        this.mMoving = false;
        this.mMinHeight = -1;
        this.mMinWidth = -1;
        this.mAdjustedMinimalTaskBounds = false;
        FreeformResizeGuide freeformResizeGuide = this.mFreeformResizeGuide;
        if (freeformResizeGuide != null) {
            freeformResizeGuide.dismiss();
            this.mFreeformResizeGuide = null;
        }
        this.mMultiWindowEdgeDetector.reset();
    }

    public final boolean findTargetTaskBounds(final int i, final int i2, Rect rect) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDisplayContent.getItemFromTaskDisplayAreas(new Function() { // from class: com.android.server.wm.MultiWindowPointerEventListener$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer lambda$findTargetTaskBounds$0;
                        lambda$findTargetTaskBounds$0 = MultiWindowPointerEventListener.this.lambda$findTargetTaskBounds$0(i, i2, (TaskDisplayArea) obj);
                        return lambda$findTargetTaskBounds$0;
                    }
                });
                Task task = this.mTaskIdFromPointSearchResult.mTask;
                if (task == null) {
                    printFailureLog("findTargetTaskBounds", "task is null");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                Slog.i("MultiWindowPointerEventListener", "findTargetTaskBounds: " + task);
                if (!task.isActivityTypeStandard()) {
                    printFailureLog("findTargetTaskBounds", "activity type is not standard");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (task.isAnimatingByRecents()) {
                    printFailureLog("findTargetTaskBounds", "task animating by recents");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (task.getWindowingMode() == 1) {
                    ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
                    if (topVisibleActivity != null && topVisibleActivity.isRelaunching()) {
                        printFailureLog("findTargetTaskBounds", "task is relaunching, t=" + task.mTaskId);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    task.getBounds(rect);
                    this.mTaskId = task.mTaskId;
                    this.mTask = task;
                    this.mTaskResizable = task.isResizeable();
                    InsetsState rawInsetsState = this.mDisplayContent.getInsetsStateController().getRawInsetsState();
                    this.mContentRect.set(rawInsetsState.getDisplayFrame());
                    Rect rect2 = this.mContentRect;
                    rect2.inset(rawInsetsState.calculateInsets(rect2, WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), false));
                    rect.set(this.mContentRect);
                    Rect rect3 = this.mContentRect;
                    int i3 = rect3.left;
                    int i4 = this.mFreeformGuideFullscreenDimViewMargin;
                    rect.set(i3 + i4, i4, rect3.right - i4, rect3.bottom - i4);
                    updateMinimalSize(task.mMinWidth, task.mMinHeight);
                    if (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && interceptFindTargetTaskBounds(rect)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    if (task.preserveOrientationOnResize()) {
                        int i5 = task.mResizeMode;
                        int i6 = 2;
                        if (i5 == 5) {
                            this.mTaskOrientation = 2;
                        } else if (i5 == 6) {
                            this.mTaskOrientation = 1;
                        } else if (i5 == 7) {
                            if (this.mContentRect.width() <= this.mContentRect.height()) {
                                i6 = 1;
                            }
                            this.mTaskOrientation = i6;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                }
                printFailureLog("findTargetTaskBounds", "task is not docked or full");
                WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$findTargetTaskBounds$0(int i, int i2, TaskDisplayArea taskDisplayArea) {
        return Integer.valueOf(taskDisplayArea.taskIdFromPoint(i, i2, this.mTaskIdFromPointSearchResult));
    }

    public final void vibrate(int i) {
        this.mDisplayContent.mWmService.mPolicy.performHapticFeedback(Process.myUid(), this.mDisplayContent.mWmService.mContext.getOpPackageName(), HapticFeedbackConstants.semGetVibrationIndex(i), false, "Swipe for pop-up view");
    }

    public final void showToast() {
        Toast.makeText(new ContextThemeWrapper(this.mService.mContext, R.style.Theme.DeviceDefault.Light), this.mService.mContext.getString(R.string.httpErrorOk), 0).show();
    }

    public final void adjustAspectRatioIfNeeded(Rect rect, int i) {
        int i2 = this.mTaskOrientation;
        if (i2 == 1) {
            if (rect.height() / rect.width() < 1.2f) {
                rect.top = rect.bottom - Math.round(rect.width() * 1.2f);
                return;
            }
            return;
        }
        if (i2 != 2 || rect.width() / rect.height() >= 1.2f) {
            return;
        }
        int round = Math.round(rect.height() * 1.2f);
        if (i == 5) {
            rect.left = rect.right - round;
        } else {
            if (i != 9) {
                return;
            }
            rect.right = rect.left + round;
        }
    }

    public final void updateMinimalSize(int i, int i2) {
        if (i > 0) {
            this.mMinWidth = i;
        } else {
            this.mMinWidth = this.mDefaultMinimalSizeOfResizeableTask;
        }
        if (i2 > 0) {
            this.mMinHeight = i2;
        } else {
            this.mMinHeight = this.mDefaultMinimalSizeOfResizeableTask;
        }
    }

    public final void hideImeIfPossible() {
        WindowState currentInputMethodWindow = this.mService.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow == null || !currentInputMethodWindow.isVisibleNow()) {
            return;
        }
        this.mService.f1749mH.post(new Runnable() { // from class: com.android.server.wm.MultiWindowPointerEventListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MultiWindowPointerEventListener.lambda$hideImeIfPossible$1();
            }
        });
    }

    public static /* synthetic */ void lambda$hideImeIfPossible$1() {
        Slog.w("MultiWindowPointerEventListener", "Hide Ime");
        InputMethodManagerInternal.get().hideCurrentInputMethod(45);
    }

    public final boolean setReadyToFreeform(boolean z) {
        if (this.mReadyToFreeform == z) {
            return false;
        }
        this.mReadyToFreeform = z;
        return true;
    }

    public boolean isValidCornerGesture(MotionEvent motionEvent) {
        return isAllowCornerGestureState() && this.mMultiWindowEdgeDetector.onTouchEvent(motionEvent);
    }

    public final boolean interceptFindTargetTaskBounds(Rect rect) {
        int edgeFlags = this.mMultiWindowEdgeDetector.getEdgeFlags();
        if (edgeFlags != 5 && edgeFlags != 9) {
            return false;
        }
        SizeCompatPolicy compatPolicy = SizeCompatPolicyManager.get().getCompatPolicy(this.mTask, true);
        SizeCompatDragPolicy sizeCompatDragPolicy = null;
        SizeCompatDragPolicy asSizeCompatDragPolicy = compatPolicy != null ? compatPolicy.asSizeCompatDragPolicy() : null;
        if (asSizeCompatDragPolicy != null && asSizeCompatDragPolicy.supportsToFreeformByCornerGesture()) {
            sizeCompatDragPolicy = asSizeCompatDragPolicy;
        }
        this.mSizeCompatDragPolicy = sizeCompatDragPolicy;
        if (sizeCompatDragPolicy == null) {
            return false;
        }
        this.mTaskResizable = true;
        if (this.mSizeCompatInfo == null) {
            this.mSizeCompatInfo = new SizeCompatInfo();
        }
        this.mSizeCompatDragPolicy.fillSizeCompatInfoForDrag(this.mSizeCompatInfo);
        this.mSizeCompatDragPolicy.getTargetDragBounds(this.mTmpRect, rect, this.mSizeCompatInfo, edgeFlags);
        if (this.mTaskBoundsAtDragStart == null) {
            this.mTaskBoundsAtDragStart = new Rect();
        }
        this.mTaskBoundsAtDragStart.set(this.mTmpRect);
        return true;
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
            cancelAnimation();
            return false;
        }
        if (!this.mReadyToFreeform) {
            this.mTmpRect.set(this.mInitRect);
            return false;
        }
        this.mFreeformResizeGuide.asSizeCompatResizeGuide().adjustBounds(this.mSizeCompatInfo, edgeFlags != 5 ? 4 : 5, this.mTmpRect, this.mTaskBoundsAtDragStart, false, (Consumer) null);
        if (this.mInitRect.contains(this.mTmpRect)) {
            if (!CoreRune.MT_DEX_SIZE_COMPAT_DRAG || !SizeCompatInfo.isDragDexSizeCompatRotatable(this.mSizeCompatInfo)) {
                return true;
            }
            this.mTmpRect.set(this.mTaskBoundsAtDragStart);
            return true;
        }
        if (z) {
            cancelAnimation();
        }
        this.mTmpRect.set(this.mInitRect);
        return false;
    }

    public final void cancelAnimation() {
        this.mFreeformResizeGuide.asSizeCompatResizeGuide().cancelAnimation(this.mTmpRect, this.mDisplayContent.getBounds(), (Consumer) null);
    }

    public MultiWindowEdgeDetector getMultiWindowEdgeDetector() {
        return this.mMultiWindowEdgeDetector;
    }
}
