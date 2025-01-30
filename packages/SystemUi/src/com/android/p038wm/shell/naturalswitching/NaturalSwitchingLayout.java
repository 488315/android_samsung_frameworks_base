package com.android.p038wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemProperties;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DismissButtonManager;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.draganddrop.DropTargetView;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NaturalSwitchingLayout {
    public static final boolean DEBUG_DEV = SystemProperties.getBoolean("persist.debug.ns.dev", false);
    public static final boolean DEBUG_PIP = SystemProperties.getBoolean("persist.debug.ns.pip", false);
    public DismissButtonManager mCancelButtonManager;
    public final Context mContext;
    public DragTargetView mDragTargetView;
    public boolean mHasDropped;
    public boolean mHideRequested;
    public boolean mIsMwHandlerType;
    public boolean mIsPipNaturalSwitching;
    public NonDragTargetView mNonDragTargetView;
    public int mNsWindowingMode;
    public boolean mPassedInitialSlop;
    public boolean mReadyToStart;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final SplitScreenController mSplitScreenController;
    public final StatusBarManager mStatusBarManager;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final TaskVisibility mTaskVisibility;
    public int mTouchSlop;
    public int mNaturalSwitchingMode = 0;
    public boolean mNaturalSwitchingStartReported = false;
    public final Handler mHandler = new Handler(Looper.myLooper());
    public final NaturalSwitchingLayout$$ExternalSyntheticLambda0 mHideRunnable = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(this, 1);
    public final Binder mBinder = new Binder();
    public final Point mDownPoint = new Point();
    public final Point mMovePoint = new Point();
    public final Point mTouchGap = new Point();
    public final ArrayList mHideTasks = new ArrayList();
    public NaturalSwitchingChanger mLastChanger = null;
    public final NaturalSwitchingAlgorithm mNaturalSwitchingAlgorithm = new NaturalSwitchingAlgorithm();

    public NaturalSwitchingLayout(Context context, SplitScreenController splitScreenController, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mSplitScreenController = splitScreenController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        this.mTaskVisibility = new TaskVisibility(context);
        transitions.mObservers.add(new Transitions.TransitionObserver() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout.1
            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionStarting() {
                NaturalSwitchingLayout naturalSwitchingLayout = NaturalSwitchingLayout.this;
                NaturalSwitchingChanger naturalSwitchingChanger = naturalSwitchingLayout.mLastChanger;
                if (naturalSwitchingChanger != null) {
                    RunnableC4009x244370c2 runnableC4009x244370c2 = naturalSwitchingChanger.mRunAfterTransitionStarted;
                    if (runnableC4009x244370c2 != null) {
                        runnableC4009x244370c2.run();
                        naturalSwitchingChanger.mRunAfterTransitionStarted = null;
                    }
                    naturalSwitchingLayout.mLastChanger = null;
                }
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionFinished(IBinder iBinder) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            }
        });
    }

    public static int getNaturalSwitchingWindowingMode(int i, int i2) {
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                if (i == 5) {
                    return 5;
                }
                if (i != 6) {
                    return 0;
                }
                if ((i2 & 1) != 0) {
                    return 3;
                }
                if ((i2 & 2) != 0) {
                    return 4;
                }
                if ((i2 & 4) != 0) {
                    return 12;
                }
            }
        }
        return i3;
    }

    public static boolean isFloating(int i) {
        return (CoreRune.MW_NATURAL_SWITCHING_PIP && i == 2) || i == 5;
    }

    public final void hide(boolean z) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(2, new StringBuilder("hide: callers="), "NaturalSwitchingLayout");
        Handler handler = this.mHandler;
        NaturalSwitchingLayout$$ExternalSyntheticLambda0 naturalSwitchingLayout$$ExternalSyntheticLambda0 = this.mHideRunnable;
        handler.removeCallbacks(naturalSwitchingLayout$$ExternalSyntheticLambda0);
        if (this.mHideRequested) {
            return;
        }
        ArrayList arrayList = this.mHideTasks;
        if (!arrayList.isEmpty()) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            arrayList.forEach(new NaturalSwitchingLayout$$ExternalSyntheticLambda3(transaction, 0));
            arrayList.clear();
            transaction.apply();
        }
        if (z) {
            handler.postDelayed(naturalSwitchingLayout$$ExternalSyntheticLambda0, 5000L);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    NaturalSwitchingLayout naturalSwitchingLayout = NaturalSwitchingLayout.this;
                    naturalSwitchingLayout.getClass();
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    naturalSwitchingLayout.mDragTargetView.mDragTarget.setAlpha(floatValue);
                    naturalSwitchingLayout.mNonDragTargetView.mMainView.setAlpha(floatValue);
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    NaturalSwitchingLayout.this.hide(false);
                }
            });
            ofFloat.start();
            return;
        }
        this.mHideRequested = true;
        NonDragTargetView nonDragTargetView = this.mNonDragTargetView;
        nonDragTargetView.mWm.removeView(nonDragTargetView);
        DragTargetView dragTargetView = this.mDragTargetView;
        dragTargetView.mIsDragEndCalled = true;
        dragTargetView.mWm.removeView(dragTargetView);
        this.mStatusBarManager.disable(0);
        if (this.mNaturalSwitchingStartReported) {
            Log.d("NaturalSwitchingLayout", "finishNaturalSwitchingIfNeeded");
            this.mNaturalSwitchingStartReported = false;
            if (this.mNaturalSwitchingMode == 1 && !this.mTaskVisibility.isTaskVisible(1)) {
                this.mSplitScreenController.setDividerVisibilityFromNS(true);
            }
            MultiWindowManager.getInstance().finishNaturalSwitching();
        }
        DismissButtonManager dismissButtonManager = this.mCancelButtonManager;
        if (dismissButtonManager != null) {
            dismissButtonManager.hide(new NaturalSwitchingLayout$$ExternalSyntheticLambda0(dismissButtonManager, 3));
        }
        this.mDragTargetView = null;
        NonDragTargetView nonDragTargetView2 = this.mNonDragTargetView;
        nonDragTargetView2.getRootView().getViewTreeObserver().removeOnDrawListener(nonDragTargetView2.mOnDrawListener);
        this.mNonDragTargetView = null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{WindowingMode=");
        sb.append(this.mNsWindowingMode);
        sb.append(", NsMode=");
        sb.append(this.mNaturalSwitchingMode);
        sb.append(", dragToken=");
        DragTargetView dragTargetView = this.mDragTargetView;
        sb.append(dragTargetView != null ? dragTargetView.getWindowToken() : null);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:241:0x0289, code lost:
    
        if (r13 != 0) goto L164;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x030d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(MotionEvent motionEvent) {
        boolean z;
        NonDragTargetView nonDragTargetView;
        NonDragTarget targetUnderPoint;
        DropTargetView dropTargetView;
        DropTargetView dropTargetView2;
        int i;
        int i2;
        int i3;
        boolean z2;
        boolean z3;
        NonDragTarget nonDragTarget;
        int i4;
        int i5;
        final int i6;
        final int i7;
        if (this.mDragTargetView == null) {
            Log.i("NaturalSwitchingLayout", "drag target view is already null.");
            return;
        }
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        DismissButtonManager dismissButtonManager = this.mCancelButtonManager;
        if (dismissButtonManager != null) {
            dismissButtonManager.mView.updateView(new Rect(rawX, rawY, rawX, rawY));
        }
        Point point = this.mDownPoint;
        int i8 = point.x;
        final Point point2 = this.mTouchGap;
        int i9 = 2;
        if (i8 == -1) {
            point.set(rawX, rawY);
            final DragTargetView dragTargetView = this.mDragTargetView;
            boolean z4 = this.mIsMwHandlerType;
            dragTargetView.getClass();
            if (!CoreRune.MW_NATURAL_SWITCHING_PIP || !dragTargetView.isPipNaturalSwitching()) {
                if (z4) {
                    i4 = point.x;
                    i5 = dragTargetView.mHandlerPosition.x;
                } else {
                    i4 = point.x;
                    Rect rect = dragTargetView.mDragTargetBounds;
                    int i10 = rect.left;
                    if (i4 < i10) {
                        i6 = i10 - i4;
                    } else {
                        i5 = rect.right;
                        if (i4 <= i5) {
                            i6 = 0;
                        }
                    }
                    i7 = point.y - dragTargetView.mHandlerPosition.y;
                    if (Math.abs(i6) < 100 || Math.abs(i7) >= 100) {
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda0
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                DragTargetView dragTargetView2 = DragTargetView.this;
                                Point point3 = point2;
                                int i11 = i6;
                                int i12 = i7;
                                RectEvaluator rectEvaluator = DragTargetView.RECT_EVALUATOR;
                                dragTargetView2.getClass();
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                int i13 = point3.x;
                                int i14 = point3.y;
                                point3.x = (int) (i11 * floatValue);
                                point3.y = (int) (i12 * floatValue);
                                float translationX = (dragTargetView2.mDragTarget.getTranslationX() + point3.x) - i13;
                                float translationY = (dragTargetView2.mDragTarget.getTranslationY() + point3.y) - i14;
                                dragTargetView2.mDragTarget.setTranslationX(translationX);
                                dragTargetView2.mDragTarget.setTranslationY(translationY);
                                dragTargetView2.mDragTarget.invalidate();
                            }
                        });
                        ofFloat.setDuration(100L);
                        ofFloat.start();
                    }
                }
                i6 = i4 - i5;
                i7 = point.y - dragTargetView.mHandlerPosition.y;
                if (Math.abs(i6) < 100) {
                }
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        DragTargetView dragTargetView2 = DragTargetView.this;
                        Point point3 = point2;
                        int i11 = i6;
                        int i12 = i7;
                        RectEvaluator rectEvaluator = DragTargetView.RECT_EVALUATOR;
                        dragTargetView2.getClass();
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        int i13 = point3.x;
                        int i14 = point3.y;
                        point3.x = (int) (i11 * floatValue);
                        point3.y = (int) (i12 * floatValue);
                        float translationX = (dragTargetView2.mDragTarget.getTranslationX() + point3.x) - i13;
                        float translationY = (dragTargetView2.mDragTarget.getTranslationY() + point3.y) - i14;
                        dragTargetView2.mDragTarget.setTranslationX(translationX);
                        dragTargetView2.mDragTarget.setTranslationY(translationY);
                        dragTargetView2.mDragTarget.invalidate();
                    }
                });
                ofFloat2.setDuration(100L);
                ofFloat2.start();
            }
        }
        Point point3 = this.mMovePoint;
        point3.set((rawX - point.x) + point2.x, (rawY - point.y) + point2.y);
        DragTargetView dragTargetView2 = this.mDragTargetView;
        dragTargetView2.mDragTarget.setTranslationX(point3.x);
        dragTargetView2.mDragTarget.setTranslationY(point3.y);
        dragTargetView2.invalidate();
        boolean z5 = Math.abs(point3.x) > this.mTouchSlop || Math.abs(point3.y) > this.mTouchSlop;
        boolean z6 = this.mPassedInitialSlop;
        int i11 = 4;
        TaskVisibility taskVisibility = this.mTaskVisibility;
        if (!z6 && this.mReadyToStart && (z5 || taskVisibility.isTaskVisible(13))) {
            this.mPassedInitialSlop = true;
            this.mDragTargetView.adjustDragTargetViewBoundsIfNeeded();
            this.mNonDragTargetView.initPushRegion(this.mDragTargetView.getMinimumDragTargetViewBounds());
            if (!this.mDragTargetView.isFloatingDragTarget()) {
                NonDragTargetView nonDragTargetView2 = this.mNonDragTargetView;
                int dropSide = this.mDragTargetView.getDropSide();
                nonDragTargetView2.mIsInitialExpanded = true;
                if (nonDragTargetView2.mTaskVisibility.isMultiSplit() && nonDragTargetView2.isQuarter(nonDragTargetView2.mDragTargetWindowingMode)) {
                    NonDragTarget nonDragTarget2 = (NonDragTarget) nonDragTargetView2.mNonTargets.get(nonDragTargetView2.mQuarterTarget);
                    if (nonDragTarget2 != null) {
                        Rect rect2 = new Rect();
                        if (nonDragTarget2.mAnimator != null) {
                            rect2.set(nonDragTarget2.mEndBounds);
                        } else {
                            nonDragTarget2.getCurrentLayoutBounds(rect2);
                        }
                        if (nonDragTargetView2.mSplitScreenController.isVerticalDivision()) {
                            Rect rect3 = nonDragTargetView2.mContainingBounds;
                            rect2.top = rect3.top;
                            rect2.bottom = rect3.bottom;
                        } else {
                            Rect rect4 = nonDragTargetView2.mContainingBounds;
                            rect2.left = rect4.left;
                            rect2.right = rect4.right;
                        }
                        if (dropSide == 1) {
                            nonDragTarget2.animate(rect2);
                        } else {
                            nonDragTargetView2.mShrunkTarget = nonDragTarget2;
                        }
                        nonDragTarget2.mBaseBounds.set(rect2);
                    }
                } else if (nonDragTargetView2.mTaskVisibility.isTaskVisible(4) && (nonDragTarget = (NonDragTarget) nonDragTargetView2.mNonTargets.get(nonDragTargetView2.getReverseWindowingMode(nonDragTargetView2.mDragTargetWindowingMode, false))) != null) {
                    if (dropSide == 1) {
                        nonDragTarget.animate(nonDragTargetView2.mContainingBounds);
                    } else {
                        nonDragTargetView2.mShrunkTarget = nonDragTarget;
                    }
                    nonDragTarget.mBaseBounds.set(nonDragTargetView2.mContainingBounds);
                }
            }
        }
        if (this.mPassedInitialSlop) {
            int i12 = this.mNaturalSwitchingMode;
            if (i12 == 1) {
                int dropSide2 = this.mDragTargetView.getDropSide();
                NonDragTargetView nonDragTargetView3 = this.mNonDragTargetView;
                int i13 = nonDragTargetView3.mDropSide;
                NonDragTarget nonDragTarget3 = nonDragTargetView3.mShrunkTarget;
                if (nonDragTargetView3.mTaskVisibility.isMultiSplit() || (nonDragTargetView3.mTaskVisibility.isTwoUp() && nonDragTargetView3.mIsFloatingDragTarget)) {
                    int i14 = 0;
                    while (true) {
                        if (i14 >= nonDragTargetView3.mPushRegions.size()) {
                            i = 0;
                            break;
                        }
                        Rect rect5 = (Rect) nonDragTargetView3.mPushRegions.valueAt(i14);
                        if (rect5 != null && rect5.contains(rawX, rawY)) {
                            i = nonDragTargetView3.mPushRegions.keyAt(i14);
                            break;
                        }
                        i14++;
                    }
                    if (nonDragTargetView3.mPushRegion != i) {
                        nonDragTargetView3.mPushRegion = i;
                        if (i != 0) {
                            nonDragTargetView3.mPushed = true;
                            if (nonDragTargetView3.mShrunkTarget != null) {
                                nonDragTargetView3.mShrunkTarget = null;
                            }
                            if (nonDragTargetView3.mSwapTarget != null) {
                                nonDragTargetView3.mSwapTarget = null;
                            }
                            int size = nonDragTargetView3.mNonTargets.size();
                            int i15 = 0;
                            while (i15 < size) {
                                NonDragTarget nonDragTarget4 = (NonDragTarget) nonDragTargetView3.mNonTargets.valueAt(i15);
                                nonDragTargetView3.mPushedNonTargets.add(nonDragTarget4);
                                Rect rect6 = new Rect();
                                rect6.set(nonDragTarget4.mBaseBounds);
                                int width = nonDragTargetView3.mDragTargetWindowingMode == nonDragTargetView3.mHalfTarget ? rect6.width() : (nonDragTargetView3.mStableRect.width() - nonDragTargetView3.mDividerSize) / i9;
                                int height = nonDragTargetView3.mDragTargetWindowingMode == nonDragTargetView3.mHalfTarget ? rect6.height() : (nonDragTargetView3.mStableRect.height() - nonDragTargetView3.mDividerSize) / i9;
                                int i16 = nonDragTargetView3.mPushRegion;
                                if (i16 == 1) {
                                    i3 = 4;
                                    int i17 = nonDragTargetView3.mStableRect.right;
                                    rect6.left = i17 - width;
                                    rect6.right = i17;
                                } else if (i16 == i9) {
                                    i3 = 4;
                                    int i18 = nonDragTargetView3.mStableRect.bottom;
                                    rect6.top = i18 - height;
                                    rect6.bottom = i18;
                                } else if (i16 != 3) {
                                    i3 = 4;
                                    if (i16 == 4) {
                                        Rect rect7 = nonDragTargetView3.mStableRect;
                                        rect6.top = rect7.top;
                                        rect6.bottom = rect7.top + height;
                                    }
                                } else {
                                    i3 = 4;
                                    Rect rect8 = nonDragTargetView3.mStableRect;
                                    rect6.left = rect8.left;
                                    rect6.right = rect8.left + width;
                                }
                                nonDragTarget4.animate(rect6);
                                i15++;
                                i11 = i3;
                                i9 = 2;
                            }
                            i2 = i11;
                        }
                    } else {
                        i2 = 4;
                    }
                    boolean z7 = i13 == nonDragTargetView3.mDropSide || nonDragTarget3 != nonDragTargetView3.mShrunkTarget;
                    int i19 = this.mNonDragTargetView.mPushRegion;
                    z2 = i19 != 0;
                    NaturalSwitchingAlgorithm naturalSwitchingAlgorithm = this.mNaturalSwitchingAlgorithm;
                    if (z2) {
                        naturalSwitchingAlgorithm.updateForPush(i19);
                    } else if (taskVisibility.isMultiSplit()) {
                        int i20 = naturalSwitchingAlgorithm.mToWindowingMode;
                        NonDragTargetView nonDragTargetView4 = this.mNonDragTargetView;
                        NonDragTarget nonDragTarget5 = nonDragTargetView4.mSwapTarget;
                        NonDragTarget nonDragTarget6 = nonDragTargetView4.mShrunkTarget;
                        int i21 = nonDragTarget6 != null ? nonDragTarget6.mNsWindowingMode : 0;
                        Rect currentDragTargetRect = this.mDragTargetView.getCurrentDragTargetRect();
                        int i22 = nonDragTarget5 != null ? nonDragTarget5.mNsWindowingMode : 0;
                        if (i21 != 0) {
                            naturalSwitchingAlgorithm.mSwapWindowingMode = 0;
                            naturalSwitchingAlgorithm.update(dropSide2, i21, true);
                        } else {
                            naturalSwitchingAlgorithm.mShrunkWindowingMode = 0;
                            if (naturalSwitchingAlgorithm.mDropSide != dropSide2 || naturalSwitchingAlgorithm.mSwapWindowingMode != i22 || i22 == 0) {
                                naturalSwitchingAlgorithm.updateForPush(0);
                                naturalSwitchingAlgorithm.mSwapWindowingMode = i22;
                                naturalSwitchingAlgorithm.mDropSide = dropSide2;
                                int i23 = naturalSwitchingAlgorithm.mDragTargetWindowingMode;
                                if (i23 == 12 || i22 == 12) {
                                    naturalSwitchingAlgorithm.mNeedToReparentCell = true;
                                }
                                naturalSwitchingAlgorithm.mSplitCreateMode = -1;
                                if (i22 != 0) {
                                    naturalSwitchingAlgorithm.mToWindowingMode = i22;
                                    naturalSwitchingAlgorithm.mToPosition = nonDragTarget5.mStagePosition;
                                } else if (i23 == naturalSwitchingAlgorithm.mHalfTarget) {
                                    Rect taskBounds = naturalSwitchingAlgorithm.mTaskVisibility.getTaskBounds(12);
                                    Rect taskBounds2 = naturalSwitchingAlgorithm.mTaskVisibility.getTaskBounds(naturalSwitchingAlgorithm.mDragTargetWindowingMode == 3 ? i2 : 3);
                                    if (Rect.intersects(currentDragTargetRect, taskBounds) || Rect.intersects(currentDragTargetRect, taskBounds2)) {
                                        naturalSwitchingAlgorithm.mToWindowingMode = (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingAlgorithm.mDragTargetWindowingMode == 2) ? 2 : 5;
                                    } else {
                                        naturalSwitchingAlgorithm.mToWindowingMode = naturalSwitchingAlgorithm.mDragTargetWindowingMode;
                                    }
                                } else {
                                    naturalSwitchingAlgorithm.mToWindowingMode = (CoreRune.MW_NATURAL_SWITCHING_PIP && i23 == 2) ? 2 : 5;
                                }
                            }
                        }
                        if (i20 != naturalSwitchingAlgorithm.mToWindowingMode) {
                            this.mDragTargetView.adjustDragTargetViewBoundsIfNeeded();
                        }
                    } else {
                        NonDragTargetView nonDragTargetView5 = this.mNonDragTargetView;
                        NonDragTarget nonDragTarget7 = nonDragTargetView5.mShrunkTarget;
                        int i24 = nonDragTarget7 != null ? nonDragTarget7.mNsWindowingMode : 0;
                        if (nonDragTarget7 == null) {
                            z3 = false;
                        } else {
                            Rect rect9 = new Rect();
                            NonDragTarget nonDragTarget8 = nonDragTargetView5.mShrunkTarget;
                            if (nonDragTarget8.mAnimator != null) {
                                rect9.set(nonDragTarget8.mEndBounds);
                            } else {
                                nonDragTarget8.getCurrentLayoutBounds(rect9);
                            }
                            z3 = !nonDragTargetView5.mShrunkTarget.mOriginBounds.equals(rect9);
                        }
                        naturalSwitchingAlgorithm.update(dropSide2, i24, z3);
                    }
                    z = z7;
                }
                i2 = 4;
                if (nonDragTargetView3.mDropSide != dropSide2) {
                    nonDragTargetView3.mDropSide = dropSide2;
                    if (!nonDragTargetView3.mTaskVisibility.isMultiSplit() || nonDragTargetView3.mSwapTarget == null || nonDragTargetView3.mDropSide == 1) {
                        if (dropSide2 == 1 || dropSide2 == 32) {
                            nonDragTargetView3.undoNonTarget();
                        } else if (nonDragTargetView3.mIsInitialExpanded) {
                            nonDragTargetView3.mIsInitialExpanded = false;
                        } else {
                            nonDragTargetView3.swapOrShrinkNonTarget(rawX, rawY, dropSide2);
                        }
                    }
                    if (i13 == nonDragTargetView3.mDropSide) {
                    }
                    int i192 = this.mNonDragTargetView.mPushRegion;
                    if (i192 != 0) {
                    }
                    NaturalSwitchingAlgorithm naturalSwitchingAlgorithm2 = this.mNaturalSwitchingAlgorithm;
                    if (z2) {
                    }
                    z = z7;
                } else if (dropSide2 != 1) {
                    NonDragTarget targetUnderPoint2 = nonDragTargetView3.getTargetUnderPoint(rawX, rawY);
                    if (targetUnderPoint2 != null && targetUnderPoint2 != nonDragTargetView3.mShrunkTarget) {
                        nonDragTargetView3.swapOrShrinkNonTarget(rawX, rawY, dropSide2);
                    }
                } else if (nonDragTargetView3.mPushed && dropSide2 == 1) {
                    nonDragTargetView3.mPushed = false;
                }
                if (!nonDragTargetView3.mPushed) {
                    while (!nonDragTargetView3.mPushedNonTargets.isEmpty()) {
                        NonDragTarget nonDragTarget9 = (NonDragTarget) nonDragTargetView3.mPushedNonTargets.valueAt(0);
                        nonDragTarget9.animate(nonDragTarget9.mBaseBounds);
                        nonDragTargetView3.mPushedNonTargets.remove(nonDragTarget9);
                    }
                }
                if (i13 == nonDragTargetView3.mDropSide) {
                }
                int i1922 = this.mNonDragTargetView.mPushRegion;
                if (i1922 != 0) {
                }
                NaturalSwitchingAlgorithm naturalSwitchingAlgorithm22 = this.mNaturalSwitchingAlgorithm;
                if (z2) {
                }
                z = z7;
            } else if (i12 != 2 || nonDragTargetView.mDropTarget == (targetUnderPoint = (nonDragTargetView = this.mNonDragTargetView).getTargetUnderPoint(rawX, rawY))) {
                z = false;
            } else {
                if (targetUnderPoint != null && (dropTargetView2 = targetUnderPoint.mDropTargetView) != null) {
                    dropTargetView2.setVisibility(0);
                }
                NonDragTarget nonDragTarget10 = nonDragTargetView.mDropTarget;
                if (nonDragTarget10 != null && (dropTargetView = nonDragTarget10.mDropTargetView) != null) {
                    dropTargetView.setVisibility(8);
                }
                if (DEBUG_DEV) {
                    Log.d("NonDragTargetView", "handleDropTargets: new=" + targetUnderPoint + ", old=" + nonDragTargetView.mDropTarget);
                }
                nonDragTargetView.mDropTarget = targetUnderPoint;
                z = true;
            }
            if (z) {
                this.mDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
        }
    }
}
