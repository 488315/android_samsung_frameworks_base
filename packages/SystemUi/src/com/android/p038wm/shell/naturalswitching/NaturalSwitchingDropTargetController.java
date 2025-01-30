package com.android.p038wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.util.secutil.Slog;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DismissButtonManager;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.transition.Transitions;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NaturalSwitchingDropTargetController implements GestureDetector.OnGestureListener {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public GestureDetector mGestureDetector;
    public boolean mLayoutChanged;
    public NaturalSwitchingLayout mNaturalSwitchingLayout;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public boolean mIsRunning = false;
    public boolean mAllowInterceptTouch = true;

    public NaturalSwitchingDropTargetController(Context context, final ShellTaskOrganizer shellTaskOrganizer, Handler handler, DisplayController displayController, final Optional<SplitScreenController> optional, final Transitions transitions, final SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        handler.post(new Runnable() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = NaturalSwitchingDropTargetController.this;
                Optional optional2 = optional;
                Transitions transitions2 = transitions;
                ShellTaskOrganizer shellTaskOrganizer2 = shellTaskOrganizer;
                SyncTransactionQueue syncTransactionQueue2 = syncTransactionQueue;
                naturalSwitchingDropTargetController.getClass();
                naturalSwitchingDropTargetController.mGestureDetector = new GestureDetector(naturalSwitchingDropTargetController.mContext, naturalSwitchingDropTargetController);
                naturalSwitchingDropTargetController.mNaturalSwitchingLayout = new NaturalSwitchingLayout(naturalSwitchingDropTargetController.mContext, (SplitScreenController) optional2.get(), transitions2, shellTaskOrganizer2, syncTransactionQueue2);
            }
        });
    }

    public final boolean allowInterceptTouch(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo == null || !runningTaskInfo.supportsMultiWindow) {
            return false;
        }
        int windowingMode = runningTaskInfo.getWindowingMode();
        if (!(windowingMode == 1 ? CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN : windowingMode == 2 ? CoreRune.MW_NATURAL_SWITCHING_PIP : true)) {
            return false;
        }
        if ((CoreRune.MW_NATURAL_SWITCHING_PIP && windowingMode == 2 && runningTaskInfo.supportsPipOnly) || runningTaskInfo.getConfiguration().isDexMode() || MultiWindowManager.getInstance().preventNaturalSwitching(runningTaskInfo.taskId)) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        return !(inputMethodManager != null && inputMethodManager.isInputMethodShown());
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x012d, code lost:
    
        if (r0.mNsWindowingMode == r10) goto L81;
     */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent, int i) {
        int i2;
        int i3;
        int stagePosition;
        int i4;
        int i5;
        boolean z = false;
        if (!this.mAllowInterceptTouch && motionEvent.getAction() != 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            boolean z2 = true;
            if (action != 1) {
                if (action != 2) {
                    if (action == 3 && this.mIsRunning) {
                        this.mIsRunning = false;
                        this.mNaturalSwitchingLayout.hide(false);
                    }
                } else if (this.mIsRunning) {
                    this.mNaturalSwitchingLayout.update(motionEvent);
                    z = true;
                }
            } else if (this.mIsRunning) {
                this.mIsRunning = false;
                NaturalSwitchingLayout naturalSwitchingLayout = this.mNaturalSwitchingLayout;
                naturalSwitchingLayout.mHasDropped = true;
                DismissButtonManager dismissButtonManager = naturalSwitchingLayout.mCancelButtonManager;
                if (dismissButtonManager == null || !dismissButtonManager.mView.mIsEnterDismissButton) {
                    Rect currentDragTargetRect = naturalSwitchingLayout.mDragTargetView.getCurrentDragTargetRect();
                    NaturalSwitchingAlgorithm naturalSwitchingAlgorithm = naturalSwitchingLayout.mNaturalSwitchingAlgorithm;
                    int i6 = naturalSwitchingAlgorithm.mSplitCreateMode;
                    if (naturalSwitchingLayout.mNaturalSwitchingMode == 1) {
                        i2 = naturalSwitchingAlgorithm.mToWindowingMode;
                        stagePosition = naturalSwitchingAlgorithm.mToPosition;
                    } else {
                        NonDragTarget nonDragTarget = naturalSwitchingLayout.mNonDragTargetView.mDropTarget;
                        i2 = nonDragTarget != null ? nonDragTarget.mNsWindowingMode : 0;
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) naturalSwitchingLayout.mTaskVisibility.mRunningTaskInfo.get(i2);
                        if (runningTaskInfo != null) {
                            stagePosition = runningTaskInfo.configuration.windowConfiguration.getStagePosition();
                        } else {
                            i3 = 0;
                            i4 = i2;
                            if (naturalSwitchingLayout.mNsWindowingMode == 5 && i4 == 5) {
                                naturalSwitchingLayout.mDragTargetView.adjustDragTargetViewBoundsIfNeeded();
                            }
                            if (i4 != 0 || (i4 == 5 && naturalSwitchingLayout.mNsWindowingMode == 5)) {
                                naturalSwitchingLayout.mNonDragTargetView.startTransition(false);
                            }
                            naturalSwitchingLayout.mHandler.postDelayed(naturalSwitchingLayout.mHideRunnable, 5000L);
                            if (i4 != 13) {
                                SplitScreenController splitScreenController = naturalSwitchingLayout.mSplitScreenController;
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = naturalSwitchingLayout.mTaskInfo;
                                float rawX = motionEvent.getRawX();
                                float rawY = motionEvent.getRawY();
                                SplitScreenController splitScreenController2 = naturalSwitchingLayout.mSplitScreenController;
                                boolean isVerticalDivision = splitScreenController2.isVerticalDivision();
                                if (!CoreRune.MW_MULTI_SPLIT || MultiWindowUtils.isInSubDisplay(naturalSwitchingLayout.mContext)) {
                                    Rect rect = new Rect();
                                    splitScreenController2.getStageBounds(rect, new Rect());
                                    if (rect.contains((int) rawX, (int) rawY)) {
                                        if (isVerticalDivision) {
                                            i5 = 8;
                                        }
                                        i5 = 16;
                                    } else {
                                        i5 = isVerticalDivision ? 32 : 64;
                                    }
                                    splitScreenController.onFreeformToSplitRequested(runningTaskInfo2, true, i5, false, currentDragTargetRect, false);
                                    naturalSwitchingLayout.hide(true);
                                } else {
                                    NonDragTarget nonDragTarget2 = naturalSwitchingLayout.mNonDragTargetView.mDropTarget;
                                    if (nonDragTarget2 != null) {
                                        i5 = nonDragTarget2.mStagePosition;
                                        splitScreenController.onFreeformToSplitRequested(runningTaskInfo2, true, i5, false, currentDragTargetRect, false);
                                        naturalSwitchingLayout.hide(true);
                                    }
                                    i5 = 16;
                                    splitScreenController.onFreeformToSplitRequested(runningTaskInfo2, true, i5, false, currentDragTargetRect, false);
                                    naturalSwitchingLayout.hide(true);
                                }
                            } else {
                                NaturalSwitchingLayout$$ExternalSyntheticLambda1 naturalSwitchingLayout$$ExternalSyntheticLambda1 = new NaturalSwitchingLayout$$ExternalSyntheticLambda1(naturalSwitchingLayout, i4, i3, i6, currentDragTargetRect, 0);
                                if (naturalSwitchingLayout.mNaturalSwitchingMode == 1) {
                                    NonDragTargetView nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                                    nonDragTargetView.mOnDrawCallback = naturalSwitchingLayout$$ExternalSyntheticLambda1;
                                    nonDragTargetView.mMainView.setBackgroundColor(Color.parseColor("#FF000000"));
                                } else {
                                    naturalSwitchingLayout$$ExternalSyntheticLambda1.run();
                                }
                            }
                        }
                    }
                    i3 = stagePosition;
                    i4 = i2;
                    if (naturalSwitchingLayout.mNsWindowingMode == 5) {
                        naturalSwitchingLayout.mDragTargetView.adjustDragTargetViewBoundsIfNeeded();
                    }
                    if (i4 != 0 || (i4 == 5 && naturalSwitchingLayout.mNsWindowingMode == 5)) {
                    }
                    naturalSwitchingLayout.mHandler.postDelayed(naturalSwitchingLayout.mHideRunnable, 5000L);
                    if (i4 != 13) {
                    }
                } else {
                    naturalSwitchingLayout.hide(false);
                }
                z2 = false;
                this.mLayoutChanged = z2;
                NaturalSwitchingLayout naturalSwitchingLayout2 = this.mNaturalSwitchingLayout;
                if (!naturalSwitchingLayout2.mHasDropped) {
                    naturalSwitchingLayout2.hide(false);
                }
            }
        } else {
            ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskOrganizer.getRunningTaskInfo(i);
            this.mTaskInfo = runningTaskInfo3;
            this.mAllowInterceptTouch = allowInterceptTouch(runningTaskInfo3);
            this.mLayoutChanged = false;
        }
        if (this.mAllowInterceptTouch) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x0415, code lost:
    
        if ((!com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT ? r7.mSupportOnlyTwoUpMode ? r7.isTwoUp() : r7.isMultiSplit() : r7.isTwoUp()) == false) goto L147;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x059b  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x05ac  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03fb  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x00cb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x049b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x04e6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x053b A[EDGE_INSN: B:90:0x053b->B:91:0x053b BREAK  A[LOOP:0: B:79:0x04e2->B:86:0x04e2], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x053f  */
    @Override // android.view.GestureDetector.OnGestureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLongPress(MotionEvent motionEvent) {
        boolean z;
        int i;
        DragTargetView dragTargetView;
        int i2;
        SurfaceFreezerSnapshot surfaceFreezerSnapshot;
        boolean isTaskVisible;
        DragTargetView dragTargetView2;
        DragTargetView dragTargetView3;
        int width;
        int max;
        NonDragTargetView nonDragTargetView;
        int size;
        AudioManager audioManager;
        DismissButtonManager dismissButtonManager;
        this.mIsRunning = true;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        boolean z2 = false;
        boolean z3 = CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && this.mTaskOrganizer.getFreeformCaptionType(this.mTaskInfo) == 0;
        NaturalSwitchingLayout naturalSwitchingLayout = this.mNaturalSwitchingLayout;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        naturalSwitchingLayout.getClass();
        Log.d("NaturalSwitchingLayout", "prepare");
        naturalSwitchingLayout.mReadyToStart = false;
        naturalSwitchingLayout.mHideRequested = false;
        naturalSwitchingLayout.mHasDropped = false;
        TaskVisibility taskVisibility = naturalSwitchingLayout.mTaskVisibility;
        taskVisibility.mDisplayLayout = displayLayout;
        if (CoreRune.MW_MULTI_SPLIT) {
            taskVisibility.mSupportOnlyTwoUpMode = taskVisibility.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
        } else {
            taskVisibility.mSupportOnlyTwoUpMode = true;
        }
        SparseArray sparseArray = taskVisibility.mRunningTaskInfo;
        sparseArray.clear();
        List<ActivityManager.RunningTaskInfo> visibleTasks = MultiWindowManager.getInstance().getVisibleTasks();
        int i3 = 3;
        if (visibleTasks == null) {
            Log.w("TaskVisibility", "initRunningTaskInfos: failed to get list");
        } else {
            for (ActivityManager.RunningTaskInfo runningTaskInfo2 : visibleTasks) {
                if (runningTaskInfo2.isVisible() && runningTaskInfo2.displayId == 0) {
                    WindowConfiguration windowConfiguration = runningTaskInfo2.configuration.windowConfiguration;
                    int activityType = windowConfiguration.getActivityType();
                    int windowingMode = windowConfiguration.getWindowingMode();
                    int stageType = windowConfiguration.getStageType();
                    if ((activityType == 2 || activityType == i3) && !z2) {
                        sparseArray.put(13, runningTaskInfo2);
                    } else if (activityType == 0 || activityType == 1) {
                        int naturalSwitchingWindowingMode = NaturalSwitchingLayout.getNaturalSwitchingWindowingMode(windowingMode, stageType);
                        if (windowingMode != 1) {
                            if (windowingMode != 2) {
                                if (windowingMode != 5) {
                                    if (windowingMode != 6) {
                                    }
                                }
                                if (sparseArray.contains(naturalSwitchingWindowingMode)) {
                                    sparseArray.put(naturalSwitchingWindowingMode, runningTaskInfo2);
                                }
                            } else if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                                sparseArray.put(naturalSwitchingWindowingMode, runningTaskInfo2);
                            }
                        }
                        z2 = true;
                        if (sparseArray.contains(naturalSwitchingWindowingMode)) {
                        }
                    }
                }
                i3 = 3;
            }
            Log.d("TaskVisibility", "initRunningTaskInfos: " + taskVisibility);
        }
        naturalSwitchingLayout.mStatusBarManager.disable(23068672);
        Context context = naturalSwitchingLayout.mContext;
        naturalSwitchingLayout.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 2;
        naturalSwitchingLayout.mPassedInitialSlop = false;
        naturalSwitchingLayout.mTouchGap.set(0, 0);
        naturalSwitchingLayout.mDownPoint.set(-1, -1);
        naturalSwitchingLayout.mTaskInfo = runningTaskInfo;
        int naturalSwitchingWindowingMode2 = NaturalSwitchingLayout.getNaturalSwitchingWindowingMode(runningTaskInfo.getWindowingMode(), naturalSwitchingLayout.mTaskInfo.configuration.windowConfiguration.getStageType());
        naturalSwitchingLayout.mNsWindowingMode = naturalSwitchingWindowingMode2;
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            naturalSwitchingLayout.mIsPipNaturalSwitching = naturalSwitchingWindowingMode2 == 2;
        }
        naturalSwitchingLayout.mIsMwHandlerType = z3;
        NaturalSwitchingAlgorithm naturalSwitchingAlgorithm = naturalSwitchingLayout.mNaturalSwitchingAlgorithm;
        naturalSwitchingAlgorithm.mTaskVisibility = taskVisibility;
        SplitScreenController splitScreenController = naturalSwitchingLayout.mSplitScreenController;
        naturalSwitchingAlgorithm.mSplitScreenController = splitScreenController;
        naturalSwitchingAlgorithm.mDragTargetWindowingMode = naturalSwitchingWindowingMode2;
        if (!NaturalSwitchingLayout.isFloating(naturalSwitchingWindowingMode2) || (!naturalSwitchingAlgorithm.mTaskVisibility.isTaskVisible(1) && !naturalSwitchingAlgorithm.mTaskVisibility.isTaskVisible(13))) {
            int i4 = naturalSwitchingAlgorithm.mDragTargetWindowingMode;
            if (!(i4 == 3 || i4 == 4 || i4 == 12) || !naturalSwitchingAlgorithm.mTaskVisibility.isTwoUp()) {
                z = false;
                naturalSwitchingAlgorithm.mUseSingleNonTarget = z;
                naturalSwitchingAlgorithm.mToWindowingMode = naturalSwitchingWindowingMode2;
                naturalSwitchingAlgorithm.mShrunkWindowingMode = 0;
                naturalSwitchingAlgorithm.mSwapWindowingMode = 0;
                naturalSwitchingAlgorithm.mDropSide = 0;
                naturalSwitchingAlgorithm.mToPosition = 0;
                naturalSwitchingAlgorithm.mPushRegion = 0;
                naturalSwitchingAlgorithm.mNeedToReparentCell = false;
                if (NaturalSwitchingLayout.isFloating(naturalSwitchingLayout.mNsWindowingMode)) {
                    if (CoreRune.MW_MULTI_SPLIT ? taskVisibility.mSupportOnlyTwoUpMode ? taskVisibility.isTwoUp() : taskVisibility.isMultiSplit() : taskVisibility.isTwoUp()) {
                        naturalSwitchingLayout.mNaturalSwitchingMode = 2;
                        NonDragTargetView nonDragTargetView2 = (NonDragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_non_drag_target_view, (ViewGroup) null);
                        naturalSwitchingLayout.mNonDragTargetView = nonDragTargetView2;
                        nonDragTargetView2.init(naturalSwitchingLayout.mNsWindowingMode, naturalSwitchingLayout.mNaturalSwitchingMode, taskVisibility, splitScreenController);
                        NonDragTargetView nonDragTargetView3 = naturalSwitchingLayout.mNonDragTargetView;
                        nonDragTargetView3.getRootView().getViewTreeObserver().addOnDrawListener(nonDragTargetView3.mOnDrawListener);
                        dragTargetView = (DragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_drag_target_view, (ViewGroup) null);
                        naturalSwitchingLayout.mDragTargetView = dragTargetView;
                        i2 = runningTaskInfo.taskId;
                        int i5 = naturalSwitchingLayout.mNsWindowingMode;
                        dragTargetView.getClass();
                        Log.d("DragTargetView", "init: t#" + i2);
                        dragTargetView.mWm = (WindowManager) dragTargetView.getContext().getSystemService("window");
                        dragTargetView.mController = splitScreenController;
                        dragTargetView.mIsDragEndCalled = false;
                        dragTargetView.mTaskVisibility = taskVisibility;
                        dragTargetView.mDragTargetWindowingMode = i5;
                        dragTargetView.mDragTarget = (FrameLayout) dragTargetView.findViewById(R.id.drag_target);
                        dragTargetView.mDragTargetImage = (ImageView) dragTargetView.findViewById(R.id.drag_target_image);
                        dragTargetView.mDragTargetBounds = dragTargetView.mTaskVisibility.getTaskBounds(dragTargetView.mDragTargetWindowingMode);
                        if (CoreRune.MW_NATURAL_SWITCHING_PIP || !dragTargetView.isPipNaturalSwitching()) {
                            dragTargetView.mDragTarget.setClipToOutline(true);
                            dragTargetView.mDragTarget.setOutlineProvider(dragTargetView.mOutlineProvider);
                        } else {
                            dragTargetView.mDragTarget.setZ(0.0f);
                        }
                        TaskVisibility taskVisibility2 = dragTargetView.mTaskVisibility;
                        Rect rect = dragTargetView.mDisplayBounds;
                        DisplayLayout displayLayout2 = taskVisibility2.mDisplayLayout;
                        rect.set(0, 0, displayLayout2.mWidth, displayLayout2.mHeight);
                        dragTargetView.mTaskVisibility.mDisplayLayout.getStableBounds(dragTargetView.mStableRect, true);
                        dragTargetView.mCornerRadius = dragTargetView.getResources().getDimensionPixelOffset(android.R.dimen.text_view_start_margin);
                        dragTargetView.mDividerSize = dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_text_padding_left) - (dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_icon_width) * 2);
                        if (CoreRune.MW_NATURAL_SWITCHING_PIP || !dragTargetView.isPipNaturalSwitching()) {
                            surfaceFreezerSnapshot = MultiWindowManager.getInstance().getSurfaceFreezerSnapshot(i2);
                            if (surfaceFreezerSnapshot != null) {
                                dragTargetView.mHasProtectedContent = surfaceFreezerSnapshot.hasProtectedContent();
                            }
                            if (dragTargetView.mHasProtectedContent) {
                                dragTargetView.mDragTargetImage.setBackgroundColor(dragTargetView.getResources().getColor(R.color.protected_content_bg_color));
                                dragTargetView.mDragTargetImage.setImageDrawable(dragTargetView.getResources().getDrawable(R.drawable.mw_splitview_ic_previewlock_mtrl));
                                dragTargetView.mDragTargetImage.getDrawable().setAlpha(76);
                                dragTargetView.mDragTargetImage.setScaleType(ImageView.ScaleType.CENTER);
                            } else {
                                Bitmap snapshotBitmap = surfaceFreezerSnapshot != null ? surfaceFreezerSnapshot.getSnapshotBitmap() : null;
                                if (snapshotBitmap == null) {
                                    IconCompat$$ExternalSyntheticOutline0.m30m("initThumbnail: failed to get snapshot, task #", i2, "DragTargetView");
                                } else {
                                    dragTargetView.mDragTargetImage.setImageBitmap(snapshotBitmap);
                                }
                                dragTargetView.mDragTargetImage.setScaleType(ImageView.ScaleType.MATRIX);
                            }
                        } else {
                            dragTargetView.mDragTargetImage.setBackgroundColor(NaturalSwitchingLayout.DEBUG_PIP ? 1711341312 : 0);
                        }
                        FrameLayout frameLayout = dragTargetView.mDragTarget;
                        DynamicAnimation.C01934 c01934 = DynamicAnimation.SCALE_X;
                        SpringAnimation springAnimation = new SpringAnimation(frameLayout, c01934);
                        springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
                        dragTargetView.mScaleDownAnimX = springAnimation;
                        FrameLayout frameLayout2 = dragTargetView.mDragTarget;
                        DynamicAnimation.C01945 c01945 = DynamicAnimation.SCALE_Y;
                        SpringAnimation springAnimation2 = new SpringAnimation(frameLayout2, c01945);
                        springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
                        dragTargetView.mScaleDownAnimY = springAnimation2;
                        SpringAnimation springAnimation3 = new SpringAnimation(dragTargetView.mDragTarget, c01934);
                        springAnimation3.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
                        dragTargetView.mScaleUpAnimX = springAnimation3;
                        SpringAnimation springAnimation4 = new SpringAnimation(dragTargetView.mDragTarget, c01945);
                        springAnimation4.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
                        dragTargetView.mScaleUpAnimY = springAnimation4;
                        if (!dragTargetView.mDragTargetBounds.isEmpty()) {
                            int dimensionPixelSize = dragTargetView.getResources().getDimensionPixelSize(R.dimen.natural_switching_scale_delta);
                            int width2 = dragTargetView.mDragTargetBounds.width();
                            int height = dragTargetView.mDragTargetBounds.height();
                            PointF pointF = dragTargetView.mDownScale;
                            float min = Math.min((width2 - dimensionPixelSize) / width2, (height - dimensionPixelSize) / height);
                            pointF.y = min;
                            pointF.x = min;
                            dragTargetView.mUpScale.set(1.015f, 1.015f);
                        }
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2016, 131856, -2);
                        dragTargetView.mLp = layoutParams;
                        layoutParams.setTitle("NS:DragTargetView");
                        WindowManager.LayoutParams layoutParams2 = dragTargetView.mLp;
                        layoutParams2.privateFlags |= 80;
                        layoutParams2.samsungFlags |= 131072;
                        layoutParams2.setFitInsetsTypes(0);
                        WindowManager.LayoutParams layoutParams3 = dragTargetView.mLp;
                        layoutParams3.layoutInDisplayCutoutMode = 3;
                        layoutParams3.y = 0;
                        layoutParams3.x = 0;
                        layoutParams3.width = dragTargetView.mDisplayBounds.width();
                        dragTargetView.mLp.height = dragTargetView.mDisplayBounds.height();
                        WindowManager.LayoutParams layoutParams4 = dragTargetView.mLp;
                        layoutParams4.gravity = 8388659;
                        dragTargetView.mWm.addView(dragTargetView, layoutParams4);
                        naturalSwitchingAlgorithm.mHalfTarget = naturalSwitchingLayout.mNonDragTargetView.mHalfTarget;
                        if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN || naturalSwitchingLayout.mNsWindowingMode != 1) {
                            if (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout.mIsPipNaturalSwitching) {
                            }
                            isTaskVisible = taskVisibility.isTaskVisible(13);
                            if (isTaskVisible) {
                                DismissButtonManager dismissButtonManager2 = new DismissButtonManager(context, 4, 2016);
                                naturalSwitchingLayout.mCancelButtonManager = dismissButtonManager2;
                                dismissButtonManager2.createDismissButtonView();
                                dismissButtonManager2.createOrUpdateWrapper();
                            }
                            dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
                            dragTargetView2.mNonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) dragTargetView2.mDragTarget.getLayoutParams();
                            Drawable drawable = dragTargetView2.mDragTargetImage.getDrawable();
                            if (dragTargetView2.mDragTargetBounds.isEmpty()) {
                                dragTargetView2.mDragTargetBounds.set(0, 0, (dragTargetView2.mHasProtectedContent || drawable == null) ? dragTargetView2.mDragTarget.getWidth() : drawable.getIntrinsicWidth(), (dragTargetView2.mHasProtectedContent || drawable == null) ? dragTargetView2.mDragTarget.getHeight() : drawable.getIntrinsicHeight());
                            }
                            Rect rect2 = dragTargetView2.mDragTargetBounds;
                            marginLayoutParams.leftMargin = rect2.left;
                            marginLayoutParams.topMargin = rect2.top;
                            marginLayoutParams.width = rect2.width();
                            marginLayoutParams.height = dragTargetView2.mDragTargetBounds.height();
                            dragTargetView2.mDragTarget.setVisibility(0);
                            dragTargetView3 = naturalSwitchingLayout.mDragTargetView;
                            dragTargetView3.getClass();
                            if (CoreRune.MW_NATURAL_SWITCHING_PIP || !dragTargetView3.isPipNaturalSwitching()) {
                                Rect rect3 = dragTargetView3.mDragTargetBounds;
                                width = (rect3.width() / 2) + rect3.left;
                                max = Math.max(dragTargetView3.mStableRect.top, dragTargetView3.mDragTargetBounds.top);
                            } else {
                                width = (int) motionEvent.getRawX();
                                max = (int) motionEvent.getRawY();
                            }
                            dragTargetView3.mHandlerPosition.set(width, max);
                            Log.d("DragTargetView", "initHandlerPosition: " + dragTargetView3.mHandlerPosition);
                            nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                            size = nonDragTargetView.mNonTargets.size();
                            while (true) {
                                size--;
                                if (size >= 0) {
                                    break;
                                }
                                NonDragTarget nonDragTarget = (NonDragTarget) nonDragTargetView.mNonTargets.valueAt(size);
                                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) nonDragTarget.mView.getLayoutParams();
                                ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) nonDragTarget.mBlurView.getLayoutParams();
                                int width3 = nonDragTarget.mBaseBounds.width();
                                marginLayoutParams2.width = width3;
                                marginLayoutParams3.width = width3;
                                int height2 = nonDragTarget.mBaseBounds.height();
                                marginLayoutParams2.height = height2;
                                marginLayoutParams3.height = height2;
                                Rect rect4 = nonDragTarget.mBaseBounds;
                                int i6 = rect4.left;
                                marginLayoutParams2.leftMargin = i6;
                                marginLayoutParams3.leftMargin = i6;
                                int i7 = rect4.top;
                                marginLayoutParams2.topMargin = i7;
                                marginLayoutParams3.topMargin = i7;
                                if (nonDragTarget.mHasProtectedContent) {
                                    nonDragTarget.mView.setScaleType(ImageView.ScaleType.CENTER);
                                } else {
                                    nonDragTarget.mView.setScaleType(ImageView.ScaleType.MATRIX);
                                    nonDragTarget.mBlurView.setScaleType(ImageView.ScaleType.MATRIX);
                                }
                            }
                            if (nonDragTargetView.mNaturalSwitchingMode == 2) {
                                NonDragTarget createNonDragTarget = nonDragTargetView.createNonDragTarget();
                                int i8 = nonDragTargetView.mDragTargetWindowingMode;
                                if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
                                    if (i8 == 1) {
                                        i8 = 5;
                                        createNonDragTarget.init(nonDragTargetView, 0, 5, nonDragTargetView.getCenterFreeformBounds(), 0);
                                    }
                                }
                                createNonDragTarget.initForTaskOnly(nonDragTargetView, nonDragTargetView.mContainingBounds, i8);
                                nonDragTargetView.mNonTargets.put(i8, createNonDragTarget);
                            }
                            final DragTargetView dragTargetView4 = naturalSwitchingLayout.mDragTargetView;
                            final NaturalSwitchingLayout$$ExternalSyntheticLambda0 naturalSwitchingLayout$$ExternalSyntheticLambda0 = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 0);
                            final View rootView = dragTargetView4.getRootView();
                            rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(dragTargetView4, rootView, naturalSwitchingLayout$$ExternalSyntheticLambda0) { // from class: com.android.wm.shell.naturalswitching.DragTargetView.5
                                public final /* synthetic */ Runnable val$postRunnable;
                                public final /* synthetic */ View val$rootView;

                                public ViewTreeObserverOnPreDrawListenerC40075(final DragTargetView dragTargetView42, final View rootView2, final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda02) {
                                    this.val$rootView = rootView2;
                                    this.val$postRunnable = naturalSwitchingLayout$$ExternalSyntheticLambda02;
                                }

                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public final boolean onPreDraw() {
                                    this.val$rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    Runnable runnable = this.val$postRunnable;
                                    if (runnable == null) {
                                        return true;
                                    }
                                    runnable.run();
                                    return true;
                                }
                            });
                            NaturalSwitchingLayout naturalSwitchingLayout2 = this.mNaturalSwitchingLayout;
                            naturalSwitchingLayout2.mNonDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                            audioManager = (AudioManager) naturalSwitchingLayout2.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                            if (audioManager != null) {
                                Slog.w("NaturalSwitchingLayout", "Couldn't get audio manager");
                            } else {
                                audioManager.playSoundEffect(106);
                            }
                            dismissButtonManager = this.mNaturalSwitchingLayout.mCancelButtonManager;
                            if (dismissButtonManager != null) {
                                Insets insets = Insets.NONE;
                                dismissButtonManager.show();
                            }
                            this.mNaturalSwitchingLayout.update(motionEvent);
                        }
                        isTaskVisible = true;
                        if (isTaskVisible) {
                        }
                        dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
                        dragTargetView2.mNonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) dragTargetView2.mDragTarget.getLayoutParams();
                        Drawable drawable2 = dragTargetView2.mDragTargetImage.getDrawable();
                        if (dragTargetView2.mDragTargetBounds.isEmpty()) {
                        }
                        Rect rect22 = dragTargetView2.mDragTargetBounds;
                        marginLayoutParams4.leftMargin = rect22.left;
                        marginLayoutParams4.topMargin = rect22.top;
                        marginLayoutParams4.width = rect22.width();
                        marginLayoutParams4.height = dragTargetView2.mDragTargetBounds.height();
                        dragTargetView2.mDragTarget.setVisibility(0);
                        dragTargetView3 = naturalSwitchingLayout.mDragTargetView;
                        dragTargetView3.getClass();
                        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                        }
                        Rect rect32 = dragTargetView3.mDragTargetBounds;
                        width = (rect32.width() / 2) + rect32.left;
                        max = Math.max(dragTargetView3.mStableRect.top, dragTargetView3.mDragTargetBounds.top);
                        dragTargetView3.mHandlerPosition.set(width, max);
                        Log.d("DragTargetView", "initHandlerPosition: " + dragTargetView3.mHandlerPosition);
                        nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                        size = nonDragTargetView.mNonTargets.size();
                        while (true) {
                            size--;
                            if (size >= 0) {
                            }
                        }
                        if (nonDragTargetView.mNaturalSwitchingMode == 2) {
                        }
                        final DragTargetView dragTargetView42 = naturalSwitchingLayout.mDragTargetView;
                        final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda02 = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 0);
                        final View rootView2 = dragTargetView42.getRootView();
                        rootView2.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(dragTargetView42, rootView2, naturalSwitchingLayout$$ExternalSyntheticLambda02) { // from class: com.android.wm.shell.naturalswitching.DragTargetView.5
                            public final /* synthetic */ Runnable val$postRunnable;
                            public final /* synthetic */ View val$rootView;

                            public ViewTreeObserverOnPreDrawListenerC40075(final DragTargetView dragTargetView422, final View rootView22, final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda022) {
                                this.val$rootView = rootView22;
                                this.val$postRunnable = naturalSwitchingLayout$$ExternalSyntheticLambda022;
                            }

                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public final boolean onPreDraw() {
                                this.val$rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                                Runnable runnable = this.val$postRunnable;
                                if (runnable == null) {
                                    return true;
                                }
                                runnable.run();
                                return true;
                            }
                        });
                        NaturalSwitchingLayout naturalSwitchingLayout22 = this.mNaturalSwitchingLayout;
                        naturalSwitchingLayout22.mNonDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                        audioManager = (AudioManager) naturalSwitchingLayout22.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                        if (audioManager != null) {
                        }
                        dismissButtonManager = this.mNaturalSwitchingLayout.mCancelButtonManager;
                        if (dismissButtonManager != null) {
                        }
                        this.mNaturalSwitchingLayout.update(motionEvent);
                    }
                }
                if (taskVisibility.isTaskVisible(13)) {
                    if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
                        i = 1;
                        if (naturalSwitchingLayout.mNsWindowingMode == 1) {
                            naturalSwitchingLayout.mNaturalSwitchingMode = 2;
                        }
                    } else {
                        i = 1;
                    }
                    naturalSwitchingLayout.mNaturalSwitchingMode = i;
                } else {
                    naturalSwitchingLayout.mNaturalSwitchingMode = 2;
                }
                NonDragTargetView nonDragTargetView22 = (NonDragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_non_drag_target_view, (ViewGroup) null);
                naturalSwitchingLayout.mNonDragTargetView = nonDragTargetView22;
                nonDragTargetView22.init(naturalSwitchingLayout.mNsWindowingMode, naturalSwitchingLayout.mNaturalSwitchingMode, taskVisibility, splitScreenController);
                NonDragTargetView nonDragTargetView32 = naturalSwitchingLayout.mNonDragTargetView;
                nonDragTargetView32.getRootView().getViewTreeObserver().addOnDrawListener(nonDragTargetView32.mOnDrawListener);
                dragTargetView = (DragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_drag_target_view, (ViewGroup) null);
                naturalSwitchingLayout.mDragTargetView = dragTargetView;
                i2 = runningTaskInfo.taskId;
                int i52 = naturalSwitchingLayout.mNsWindowingMode;
                dragTargetView.getClass();
                Log.d("DragTargetView", "init: t#" + i2);
                dragTargetView.mWm = (WindowManager) dragTargetView.getContext().getSystemService("window");
                dragTargetView.mController = splitScreenController;
                dragTargetView.mIsDragEndCalled = false;
                dragTargetView.mTaskVisibility = taskVisibility;
                dragTargetView.mDragTargetWindowingMode = i52;
                dragTargetView.mDragTarget = (FrameLayout) dragTargetView.findViewById(R.id.drag_target);
                dragTargetView.mDragTargetImage = (ImageView) dragTargetView.findViewById(R.id.drag_target_image);
                dragTargetView.mDragTargetBounds = dragTargetView.mTaskVisibility.getTaskBounds(dragTargetView.mDragTargetWindowingMode);
                if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                }
                dragTargetView.mDragTarget.setClipToOutline(true);
                dragTargetView.mDragTarget.setOutlineProvider(dragTargetView.mOutlineProvider);
                TaskVisibility taskVisibility22 = dragTargetView.mTaskVisibility;
                Rect rect5 = dragTargetView.mDisplayBounds;
                DisplayLayout displayLayout22 = taskVisibility22.mDisplayLayout;
                rect5.set(0, 0, displayLayout22.mWidth, displayLayout22.mHeight);
                dragTargetView.mTaskVisibility.mDisplayLayout.getStableBounds(dragTargetView.mStableRect, true);
                dragTargetView.mCornerRadius = dragTargetView.getResources().getDimensionPixelOffset(android.R.dimen.text_view_start_margin);
                dragTargetView.mDividerSize = dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_text_padding_left) - (dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_icon_width) * 2);
                if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                }
                surfaceFreezerSnapshot = MultiWindowManager.getInstance().getSurfaceFreezerSnapshot(i2);
                if (surfaceFreezerSnapshot != null) {
                }
                if (dragTargetView.mHasProtectedContent) {
                }
                FrameLayout frameLayout3 = dragTargetView.mDragTarget;
                DynamicAnimation.C01934 c019342 = DynamicAnimation.SCALE_X;
                SpringAnimation springAnimation5 = new SpringAnimation(frameLayout3, c019342);
                springAnimation5.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
                dragTargetView.mScaleDownAnimX = springAnimation5;
                FrameLayout frameLayout22 = dragTargetView.mDragTarget;
                DynamicAnimation.C01945 c019452 = DynamicAnimation.SCALE_Y;
                SpringAnimation springAnimation22 = new SpringAnimation(frameLayout22, c019452);
                springAnimation22.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
                dragTargetView.mScaleDownAnimY = springAnimation22;
                SpringAnimation springAnimation32 = new SpringAnimation(dragTargetView.mDragTarget, c019342);
                springAnimation32.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
                dragTargetView.mScaleUpAnimX = springAnimation32;
                SpringAnimation springAnimation42 = new SpringAnimation(dragTargetView.mDragTarget, c019452);
                springAnimation42.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
                dragTargetView.mScaleUpAnimY = springAnimation42;
                if (!dragTargetView.mDragTargetBounds.isEmpty()) {
                }
                WindowManager.LayoutParams layoutParams5 = new WindowManager.LayoutParams(-1, -1, 2016, 131856, -2);
                dragTargetView.mLp = layoutParams5;
                layoutParams5.setTitle("NS:DragTargetView");
                WindowManager.LayoutParams layoutParams22 = dragTargetView.mLp;
                layoutParams22.privateFlags |= 80;
                layoutParams22.samsungFlags |= 131072;
                layoutParams22.setFitInsetsTypes(0);
                WindowManager.LayoutParams layoutParams32 = dragTargetView.mLp;
                layoutParams32.layoutInDisplayCutoutMode = 3;
                layoutParams32.y = 0;
                layoutParams32.x = 0;
                layoutParams32.width = dragTargetView.mDisplayBounds.width();
                dragTargetView.mLp.height = dragTargetView.mDisplayBounds.height();
                WindowManager.LayoutParams layoutParams42 = dragTargetView.mLp;
                layoutParams42.gravity = 8388659;
                dragTargetView.mWm.addView(dragTargetView, layoutParams42);
                naturalSwitchingAlgorithm.mHalfTarget = naturalSwitchingLayout.mNonDragTargetView.mHalfTarget;
                if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
                }
                if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                }
                isTaskVisible = taskVisibility.isTaskVisible(13);
                if (isTaskVisible) {
                }
                dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
                dragTargetView2.mNonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                ViewGroup.MarginLayoutParams marginLayoutParams42 = (ViewGroup.MarginLayoutParams) dragTargetView2.mDragTarget.getLayoutParams();
                Drawable drawable22 = dragTargetView2.mDragTargetImage.getDrawable();
                if (dragTargetView2.mDragTargetBounds.isEmpty()) {
                }
                Rect rect222 = dragTargetView2.mDragTargetBounds;
                marginLayoutParams42.leftMargin = rect222.left;
                marginLayoutParams42.topMargin = rect222.top;
                marginLayoutParams42.width = rect222.width();
                marginLayoutParams42.height = dragTargetView2.mDragTargetBounds.height();
                dragTargetView2.mDragTarget.setVisibility(0);
                dragTargetView3 = naturalSwitchingLayout.mDragTargetView;
                dragTargetView3.getClass();
                if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                }
                Rect rect322 = dragTargetView3.mDragTargetBounds;
                width = (rect322.width() / 2) + rect322.left;
                max = Math.max(dragTargetView3.mStableRect.top, dragTargetView3.mDragTargetBounds.top);
                dragTargetView3.mHandlerPosition.set(width, max);
                Log.d("DragTargetView", "initHandlerPosition: " + dragTargetView3.mHandlerPosition);
                nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                size = nonDragTargetView.mNonTargets.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                    }
                }
                if (nonDragTargetView.mNaturalSwitchingMode == 2) {
                }
                final DragTargetView dragTargetView422 = naturalSwitchingLayout.mDragTargetView;
                final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda022 = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 0);
                final View rootView22 = dragTargetView422.getRootView();
                rootView22.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(dragTargetView422, rootView22, naturalSwitchingLayout$$ExternalSyntheticLambda022) { // from class: com.android.wm.shell.naturalswitching.DragTargetView.5
                    public final /* synthetic */ Runnable val$postRunnable;
                    public final /* synthetic */ View val$rootView;

                    public ViewTreeObserverOnPreDrawListenerC40075(final DragTargetView dragTargetView4222, final View rootView222, final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda0222) {
                        this.val$rootView = rootView222;
                        this.val$postRunnable = naturalSwitchingLayout$$ExternalSyntheticLambda0222;
                    }

                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        this.val$rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                        Runnable runnable = this.val$postRunnable;
                        if (runnable == null) {
                            return true;
                        }
                        runnable.run();
                        return true;
                    }
                });
                NaturalSwitchingLayout naturalSwitchingLayout222 = this.mNaturalSwitchingLayout;
                naturalSwitchingLayout222.mNonDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                audioManager = (AudioManager) naturalSwitchingLayout222.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                if (audioManager != null) {
                }
                dismissButtonManager = this.mNaturalSwitchingLayout.mCancelButtonManager;
                if (dismissButtonManager != null) {
                }
                this.mNaturalSwitchingLayout.update(motionEvent);
            }
        }
        z = true;
        naturalSwitchingAlgorithm.mUseSingleNonTarget = z;
        naturalSwitchingAlgorithm.mToWindowingMode = naturalSwitchingWindowingMode2;
        naturalSwitchingAlgorithm.mShrunkWindowingMode = 0;
        naturalSwitchingAlgorithm.mSwapWindowingMode = 0;
        naturalSwitchingAlgorithm.mDropSide = 0;
        naturalSwitchingAlgorithm.mToPosition = 0;
        naturalSwitchingAlgorithm.mPushRegion = 0;
        naturalSwitchingAlgorithm.mNeedToReparentCell = false;
        if (NaturalSwitchingLayout.isFloating(naturalSwitchingLayout.mNsWindowingMode)) {
        }
        if (taskVisibility.isTaskVisible(13)) {
        }
        NonDragTargetView nonDragTargetView222 = (NonDragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_non_drag_target_view, (ViewGroup) null);
        naturalSwitchingLayout.mNonDragTargetView = nonDragTargetView222;
        nonDragTargetView222.init(naturalSwitchingLayout.mNsWindowingMode, naturalSwitchingLayout.mNaturalSwitchingMode, taskVisibility, splitScreenController);
        NonDragTargetView nonDragTargetView322 = naturalSwitchingLayout.mNonDragTargetView;
        nonDragTargetView322.getRootView().getViewTreeObserver().addOnDrawListener(nonDragTargetView322.mOnDrawListener);
        dragTargetView = (DragTargetView) LayoutInflater.from(context).inflate(R.layout.ns_drag_target_view, (ViewGroup) null);
        naturalSwitchingLayout.mDragTargetView = dragTargetView;
        i2 = runningTaskInfo.taskId;
        int i522 = naturalSwitchingLayout.mNsWindowingMode;
        dragTargetView.getClass();
        Log.d("DragTargetView", "init: t#" + i2);
        dragTargetView.mWm = (WindowManager) dragTargetView.getContext().getSystemService("window");
        dragTargetView.mController = splitScreenController;
        dragTargetView.mIsDragEndCalled = false;
        dragTargetView.mTaskVisibility = taskVisibility;
        dragTargetView.mDragTargetWindowingMode = i522;
        dragTargetView.mDragTarget = (FrameLayout) dragTargetView.findViewById(R.id.drag_target);
        dragTargetView.mDragTargetImage = (ImageView) dragTargetView.findViewById(R.id.drag_target_image);
        dragTargetView.mDragTargetBounds = dragTargetView.mTaskVisibility.getTaskBounds(dragTargetView.mDragTargetWindowingMode);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
        }
        dragTargetView.mDragTarget.setClipToOutline(true);
        dragTargetView.mDragTarget.setOutlineProvider(dragTargetView.mOutlineProvider);
        TaskVisibility taskVisibility222 = dragTargetView.mTaskVisibility;
        Rect rect52 = dragTargetView.mDisplayBounds;
        DisplayLayout displayLayout222 = taskVisibility222.mDisplayLayout;
        rect52.set(0, 0, displayLayout222.mWidth, displayLayout222.mHeight);
        dragTargetView.mTaskVisibility.mDisplayLayout.getStableBounds(dragTargetView.mStableRect, true);
        dragTargetView.mCornerRadius = dragTargetView.getResources().getDimensionPixelOffset(android.R.dimen.text_view_start_margin);
        dragTargetView.mDividerSize = dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_text_padding_left) - (dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_icon_width) * 2);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
        }
        surfaceFreezerSnapshot = MultiWindowManager.getInstance().getSurfaceFreezerSnapshot(i2);
        if (surfaceFreezerSnapshot != null) {
        }
        if (dragTargetView.mHasProtectedContent) {
        }
        FrameLayout frameLayout32 = dragTargetView.mDragTarget;
        DynamicAnimation.C01934 c0193422 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation52 = new SpringAnimation(frameLayout32, c0193422);
        springAnimation52.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
        dragTargetView.mScaleDownAnimX = springAnimation52;
        FrameLayout frameLayout222 = dragTargetView.mDragTarget;
        DynamicAnimation.C01945 c0194522 = DynamicAnimation.SCALE_Y;
        SpringAnimation springAnimation222 = new SpringAnimation(frameLayout222, c0194522);
        springAnimation222.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(200.0f, 0.99f);
        dragTargetView.mScaleDownAnimY = springAnimation222;
        SpringAnimation springAnimation322 = new SpringAnimation(dragTargetView.mDragTarget, c0193422);
        springAnimation322.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
        dragTargetView.mScaleUpAnimX = springAnimation322;
        SpringAnimation springAnimation422 = new SpringAnimation(dragTargetView.mDragTarget, c0194522);
        springAnimation422.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.47f);
        dragTargetView.mScaleUpAnimY = springAnimation422;
        if (!dragTargetView.mDragTargetBounds.isEmpty()) {
        }
        WindowManager.LayoutParams layoutParams52 = new WindowManager.LayoutParams(-1, -1, 2016, 131856, -2);
        dragTargetView.mLp = layoutParams52;
        layoutParams52.setTitle("NS:DragTargetView");
        WindowManager.LayoutParams layoutParams222 = dragTargetView.mLp;
        layoutParams222.privateFlags |= 80;
        layoutParams222.samsungFlags |= 131072;
        layoutParams222.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams322 = dragTargetView.mLp;
        layoutParams322.layoutInDisplayCutoutMode = 3;
        layoutParams322.y = 0;
        layoutParams322.x = 0;
        layoutParams322.width = dragTargetView.mDisplayBounds.width();
        dragTargetView.mLp.height = dragTargetView.mDisplayBounds.height();
        WindowManager.LayoutParams layoutParams422 = dragTargetView.mLp;
        layoutParams422.gravity = 8388659;
        dragTargetView.mWm.addView(dragTargetView, layoutParams422);
        naturalSwitchingAlgorithm.mHalfTarget = naturalSwitchingLayout.mNonDragTargetView.mHalfTarget;
        if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
        }
        isTaskVisible = taskVisibility.isTaskVisible(13);
        if (isTaskVisible) {
        }
        dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
        dragTargetView2.mNonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
        ViewGroup.MarginLayoutParams marginLayoutParams422 = (ViewGroup.MarginLayoutParams) dragTargetView2.mDragTarget.getLayoutParams();
        Drawable drawable222 = dragTargetView2.mDragTargetImage.getDrawable();
        if (dragTargetView2.mDragTargetBounds.isEmpty()) {
        }
        Rect rect2222 = dragTargetView2.mDragTargetBounds;
        marginLayoutParams422.leftMargin = rect2222.left;
        marginLayoutParams422.topMargin = rect2222.top;
        marginLayoutParams422.width = rect2222.width();
        marginLayoutParams422.height = dragTargetView2.mDragTargetBounds.height();
        dragTargetView2.mDragTarget.setVisibility(0);
        dragTargetView3 = naturalSwitchingLayout.mDragTargetView;
        dragTargetView3.getClass();
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
        }
        Rect rect3222 = dragTargetView3.mDragTargetBounds;
        width = (rect3222.width() / 2) + rect3222.left;
        max = Math.max(dragTargetView3.mStableRect.top, dragTargetView3.mDragTargetBounds.top);
        dragTargetView3.mHandlerPosition.set(width, max);
        Log.d("DragTargetView", "initHandlerPosition: " + dragTargetView3.mHandlerPosition);
        nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
        size = nonDragTargetView.mNonTargets.size();
        while (true) {
            size--;
            if (size >= 0) {
            }
        }
        if (nonDragTargetView.mNaturalSwitchingMode == 2) {
        }
        final DragTargetView dragTargetView4222 = naturalSwitchingLayout.mDragTargetView;
        final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda0222 = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 0);
        final View rootView222 = dragTargetView4222.getRootView();
        rootView222.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(dragTargetView4222, rootView222, naturalSwitchingLayout$$ExternalSyntheticLambda0222) { // from class: com.android.wm.shell.naturalswitching.DragTargetView.5
            public final /* synthetic */ Runnable val$postRunnable;
            public final /* synthetic */ View val$rootView;

            public ViewTreeObserverOnPreDrawListenerC40075(final DragTargetView dragTargetView42222, final View rootView2222, final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda02222) {
                this.val$rootView = rootView2222;
                this.val$postRunnable = naturalSwitchingLayout$$ExternalSyntheticLambda02222;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                this.val$rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                Runnable runnable = this.val$postRunnable;
                if (runnable == null) {
                    return true;
                }
                runnable.run();
                return true;
            }
        });
        NaturalSwitchingLayout naturalSwitchingLayout2222 = this.mNaturalSwitchingLayout;
        naturalSwitchingLayout2222.mNonDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
        audioManager = (AudioManager) naturalSwitchingLayout2222.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (audioManager != null) {
        }
        dismissButtonManager = this.mNaturalSwitchingLayout.mCancelButtonManager;
        if (dismissButtonManager != null) {
        }
        this.mNaturalSwitchingLayout.update(motionEvent);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final void onShowPress(MotionEvent motionEvent) {
    }
}
