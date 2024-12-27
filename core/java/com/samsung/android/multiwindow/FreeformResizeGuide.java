package com.samsung.android.multiwindow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.internal.R;

public class FreeformResizeGuide {
    private static final long DEFER_DISMISSING_TIMEOUT_MARGIN = 10;
    private static final int INVALID_MAX_SIZE = -1;
    private static final int INVALID_MIN_SIZE = -1;
    public static final int MINIMUM_VISIBLE_HEIGHT_IN_DP = 32;
    public static final int MINIMUM_VISIBLE_WIDTH_IN_DP = 48;
    public static final int STATE_MINIMIZING = 1;
    public static final int STATE_NONE = -1;
    public static final int STATE_RESIZING = 0;
    private static final String TAG = FreeformResizeGuide.class.getSimpleName();
    private final Rect mBounds;
    private final Context mContext;
    private int mCtrlType;
    private long mDeferDismissingTimeout;
    private boolean mDismissRequested;
    private boolean mDismissed;
    private final Rect mDisplayFrame;
    private int mFreeformGuideViewFullscreenMargin;
    private final H mH;
    private final Rect mLastBounds;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mMinHeight;
    private int mMinWidth;
    private int mMinimizeFreeformPadding;
    private final Rect mMinimizeTriggerBounds;
    private boolean mNeedToFullscreenTransition;
    private final Rect mNotAdjustedBounds;
    private boolean mReadyToMinimize;
    private final Rect mStableBounds;
    private int mState;
    private TransitionInfo mTmpTransitionInfo;
    private TransitionInfo mTransitionInfo;
    private final FreeformResizeGuideView mView;
    private final WindowManager mWindowManager;

    public @interface FreeformGuideWindowType {}

    public FreeformResizeGuide(Context context) {
        this(context, 0, null);
    }

    public FreeformResizeGuide(Context context, ComponentName componentName) {
        this(context, 0, componentName);
    }

    public FreeformResizeGuide(Context context, int dexDockingState, ComponentName componentName) {
        this.mState = -1;
        this.mBounds = new Rect();
        this.mLastBounds = new Rect();
        this.mDisplayFrame = new Rect();
        this.mStableBounds = new Rect();
        this.mMinimizeTriggerBounds = new Rect();
        this.mNotAdjustedBounds = new Rect();
        this.mNeedToFullscreenTransition = false;
        this.mReadyToMinimize = false;
        this.mContext =
                context != null
                        ? context
                        : ActivityThread.currentActivityThread().getSystemUiContext();
        this.mWindowManager =
                (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mH = new H();
        this.mView =
                (FreeformResizeGuideView)
                        LayoutInflater.from(this.mContext)
                                .inflate(R.layout.freeform_resize_guide, (ViewGroup) null);
        this.mView.update(dexDockingState, componentName);
        this.mWindowManager.addView(this.mView, generateLayoutParam());
    }

    private WindowManager.LayoutParams generateLayoutParam() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1, 2016, 24, -2);
        lp.setTitle("FreeformResizeGuideWindow");
        lp.gravity = 8388659;
        lp.layoutInDisplayCutoutMode = 1;
        lp.privateFlags |= 16;
        lp.samsungFlags |= 131072;
        lp.windowAnimations = R.style.freeform_resize_guide_window_animation;
        lp.setFitInsetsTypes(0);
        return lp;
    }

    public void show(Rect bounds) {
        if (bounds == null) {
            return;
        }
        this.mLastBounds.set(this.mBounds);
        this.mBounds.set(bounds);
        if (this.mTransitionInfo != null) {
            this.mView.show(
                    this.mLastBounds,
                    this.mBounds,
                    true,
                    this.mNeedToFullscreenTransition,
                    this.mTransitionInfo);
            this.mTransitionInfo = null;
        } else {
            this.mView.show(this.mLastBounds, this.mBounds, this.mNeedToFullscreenTransition);
        }
    }

    public void hide() {
        this.mView.hide();
    }

    public void dismiss() {
        this.mDismissRequested = true;
        long timeout = this.mDeferDismissingTimeout;
        long j = 0;
        this.mDeferDismissingTimeout = 0L;
        H h = this.mH;
        if (!this.mDismissed && timeout > 0) {
            j = 10 + timeout;
        }
        h.sendEmptyMessageDelayed(0, j);
    }

    public boolean updateGuideState(int newState) {
        if (this.mState == newState) {
            return false;
        }
        this.mState = newState;
        this.mView.setDimViewVisibility(this.mState == -1 ? 4 : 0);
        return true;
    }

    public void updateMinMaxSizeIfNeeded(
            ActivityManager.RunningTaskInfo taskInfo,
            Rect displaySize,
            boolean startOrientationWasLandscape) {
        int i;
        int dockingMaxWidth;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int dipToPixel = MultiWindowUtils.dipToPixel(taskInfo.defaultMinSize, displayMetrics);
        this.mMinHeight = dipToPixel;
        this.mMinWidth = dipToPixel;
        if (taskInfo.minWidth != -1) {
            this.mMinWidth = taskInfo.minWidth;
        }
        if (taskInfo.minHeight != -1) {
            this.mMinHeight = taskInfo.minHeight;
        }
        int minVisibleWidth = MultiWindowUtils.dipToPixel(48, displayMetrics);
        int minVisibleHeight = MultiWindowUtils.dipToPixel(32, displayMetrics);
        this.mMinWidth = Math.max(minVisibleWidth, this.mMinWidth);
        this.mMinHeight = Math.max(minVisibleHeight, this.mMinHeight);
        if (taskInfo.maxWidth == -1 || taskInfo.maxWidth > displaySize.width()) {
            this.mMaxWidth = displaySize.width();
        } else {
            this.mMaxWidth = taskInfo.maxWidth;
        }
        if (taskInfo.maxHeight == -1 || taskInfo.maxHeight > displaySize.height()) {
            this.mMaxHeight = displaySize.height();
        } else {
            this.mMaxHeight = taskInfo.maxHeight;
        }
        this.mMaxWidth = Math.max(this.mMaxWidth, this.mMinWidth);
        this.mMaxHeight = Math.max(this.mMaxHeight, this.mMinHeight);
        int dexTaskDockingState =
                taskInfo.configuration.windowConfiguration.getDexTaskDockingState();
        if (isDexTaskDocked(dexTaskDockingState)
                && (dockingMaxWidth =
                                MultiWindowManager.getInstance()
                                        .calculateMaxWidth(
                                                dexTaskDockingState,
                                                displaySize.width(),
                                                this.mMinWidth))
                        != -1) {
            this.mMaxWidth = Math.min(this.mMaxWidth, dockingMaxWidth);
            this.mMinWidth = Math.max(this.mMinWidth, displaySize.width() - this.mMaxWidth);
        }
        int taskOrientation = 0;
        if (taskInfo.preserveOrientationOnResize()) {
            switch (taskInfo.resizeMode) {
                case 5:
                    taskOrientation = 2;
                    break;
                case 6:
                    taskOrientation = 1;
                    break;
                case 7:
                    if (startOrientationWasLandscape) {
                        i = 2;
                    } else {
                        i = 1;
                    }
                    taskOrientation = i;
                    break;
            }
            if (taskOrientation == 1) {
                this.mMinHeight = (int) (this.mMinWidth * 1.2f);
                this.mMaxWidth = (int) (this.mMaxHeight / 1.2f);
            } else if (taskOrientation == 2) {
                this.mMinWidth = (int) (this.mMinHeight * 1.2f);
                this.mMaxHeight = (int) (this.mMaxWidth / 1.2f);
            }
        }
    }

    public void adjustMinMaxSize(Rect inOutBounds) {
        boolean adjustMinWidth = inOutBounds.width() <= this.mMinWidth;
        boolean adjustMinHeight = inOutBounds.height() <= this.mMinHeight;
        boolean adjustMaxWidth = inOutBounds.width() >= this.mMaxWidth;
        boolean adjustMaxHeight = inOutBounds.height() >= this.mMaxHeight;
        if (adjustMinWidth) {
            if ((this.mCtrlType & 1) != 0) {
                inOutBounds.left = inOutBounds.right - this.mMinWidth;
            } else {
                inOutBounds.right = inOutBounds.left + this.mMinWidth;
            }
        }
        if (adjustMinHeight) {
            if ((this.mCtrlType & 4) != 0) {
                inOutBounds.top = inOutBounds.bottom - this.mMinHeight;
            } else {
                inOutBounds.bottom = inOutBounds.top + this.mMinHeight;
            }
        }
        if (adjustMaxWidth) {
            if ((this.mCtrlType & 1) != 0) {
                inOutBounds.left = inOutBounds.right - this.mMaxWidth;
            } else {
                inOutBounds.right = inOutBounds.left + this.mMaxWidth;
            }
        }
        if (adjustMaxHeight) {
            if ((this.mCtrlType & 4) != 0) {
                inOutBounds.top = inOutBounds.bottom - this.mMaxHeight;
            } else {
                inOutBounds.bottom = inOutBounds.top + this.mMaxHeight;
            }
        }
        boolean adjustMinSize = adjustMinWidth && adjustMinHeight;
        boolean adjustMaxSize = adjustMaxWidth && adjustMaxHeight;
        int guideState = (adjustMinSize || adjustMaxSize) ? 1 : 0;
        updateGuideState(guideState);
    }

    public void snapToBounds(Rect toFullScreenBounds) {
        snapToBounds(toFullScreenBounds, 0L, null, -1, -1, false);
    }

    public void snapToBounds(long duration) {
        snapToBounds(null, duration, null, -1, -1, false);
    }

    public void snapToBounds(
            Rect toFullScreenBounds,
            long duration,
            TimeInterpolator interpolator,
            int fromAlpha,
            int toAlpha,
            boolean deferForTransition) {
        if (deferForTransition) {
            this.mDeferDismissingTimeout = duration;
        }
        if (this.mTmpTransitionInfo == null) {
            this.mTmpTransitionInfo = new TransitionInfo();
        } else {
            this.mTmpTransitionInfo.reset();
        }
        this.mTmpTransitionInfo.mAnimationDuration = duration;
        this.mTmpTransitionInfo.mInterpolator = interpolator;
        this.mTmpTransitionInfo.mFromAlpha = fromAlpha;
        this.mTmpTransitionInfo.mToAlpha = toAlpha;
        this.mTransitionInfo = this.mTmpTransitionInfo;
        snapToFullscreen(toFullScreenBounds);
    }

    public void setCtrlType(int ctrlType) {
        this.mCtrlType = ctrlType;
    }

    private void showAppIcon() {
        this.mView.startShowAppIconAnimation();
    }

    private void hideAppIcon() {
        this.mView.startHideAppIconAnimation();
    }

    private boolean isShowingAppIcon() {
        return this.mView.isShowingAppIcon();
    }

    private void performHapticFeedback(int feedbackConstant) {
        this.mView.performHapticFeedback(feedbackConstant);
    }

    public void handleResizeGesture(Rect bounds, int x, int y) {
        snapToFullscreenIfNeeded(bounds, y);
        checkIfReadyToMinimize(bounds, x, y);
    }

    public boolean snapToFullscreenIfNeeded(Rect bounds, int posY) {
        if ((this.mCtrlType & 4) != 0 && posY <= this.mStableBounds.top) {
            boolean result = snapToFullscreen(bounds);
            return result;
        }
        if (this.mCtrlType == 0 && posY <= this.mStableBounds.top) {
            boolean result2 = snapToFullscreen(bounds);
            return result2;
        }
        boolean result3 = snapToFullscreen(null);
        return result3;
    }

    private boolean snapToFullscreen(Rect bounds) {
        this.mNeedToFullscreenTransition = bounds != null;
        if (this.mNeedToFullscreenTransition) {
            bounds.set(
                    this.mStableBounds.left + this.mFreeformGuideViewFullscreenMargin,
                    this.mStableBounds.top + this.mFreeformGuideViewFullscreenMargin,
                    this.mStableBounds.right - this.mFreeformGuideViewFullscreenMargin,
                    this.mStableBounds.bottom - this.mFreeformGuideViewFullscreenMargin);
        }
        return this.mNeedToFullscreenTransition;
    }

    private void checkIfReadyToMinimize(Rect bounds, int posX, int posY) {
        boolean adjustMinSize =
                bounds.width() <= this.mMinWidth && bounds.height() <= this.mMinHeight;
        if (!adjustMinSize) {
            this.mReadyToMinimize = false;
            return;
        }
        this.mMinimizeTriggerBounds.set(
                bounds.left + this.mMinimizeFreeformPadding,
                bounds.top + this.mMinimizeFreeformPadding,
                bounds.right - this.mMinimizeFreeformPadding,
                bounds.bottom - this.mMinimizeFreeformPadding);
        if ((this.mCtrlType & 1) != 0) {
            this.mMinimizeTriggerBounds.right = bounds.right;
        }
        if ((this.mCtrlType & 4) != 0) {
            this.mMinimizeTriggerBounds.bottom = bounds.bottom;
        }
        if ((this.mCtrlType & 2) != 0) {
            this.mMinimizeTriggerBounds.left = bounds.left;
        }
        if ((this.mCtrlType & 8) != 0) {
            this.mMinimizeTriggerBounds.top = bounds.top;
        }
        int dx = bounds.width() - this.mNotAdjustedBounds.width();
        int dy = bounds.height() - this.mNotAdjustedBounds.height();
        int delta = Math.max(dx, dy) / 4;
        if ((this.mCtrlType & 1) != 0) {
            bounds.left += delta;
        } else {
            bounds.right -= delta;
        }
        if ((this.mCtrlType & 4) != 0) {
            bounds.top += delta;
        } else {
            bounds.bottom -= delta;
        }
        if (this.mMinimizeTriggerBounds.contains(posX, posY)) {
            if (!this.mReadyToMinimize) {
                this.mReadyToMinimize = true;
                if (!isShowingAppIcon()) {
                    performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(49));
                    showAppIcon();
                    return;
                }
                return;
            }
            return;
        }
        if (this.mReadyToMinimize) {
            this.mReadyToMinimize = false;
            boolean keepShowing = false;
            if ((1 & this.mCtrlType) != 0 && this.mMinimizeTriggerBounds.right < posX) {
                keepShowing = true;
            }
            if ((this.mCtrlType & 4) != 0 && this.mMinimizeTriggerBounds.bottom < posY) {
                keepShowing = true;
            }
            if ((this.mCtrlType & 2) != 0 && this.mMinimizeTriggerBounds.left > posX) {
                keepShowing = true;
            }
            if ((this.mCtrlType & 8) != 0 && this.mMinimizeTriggerBounds.top > posY) {
                keepShowing = true;
            }
            if (!keepShowing) {
                hideAppIcon();
            }
        }
    }

    public void updateResizeGestureInfo(Rect displayFrame, Rect stableBounds) {
        this.mDisplayFrame.set(displayFrame);
        this.mStableBounds.set(stableBounds);
        this.mFreeformGuideViewFullscreenMargin =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.freeform_resize_guide_view_fullscreen_margin);
        this.mMinimizeFreeformPadding =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.minimize_freeform_padding);
    }

    public void setNotAdjustedBounds(Rect bounds) {
        this.mNotAdjustedBounds.set(bounds);
    }

    public boolean canResizeGesture() {
        return needToFullscreenTransition() || readyToMinimize();
    }

    public boolean needToFullscreenTransition() {
        return this.mNeedToFullscreenTransition;
    }

    public boolean readyToMinimize() {
        return this.mReadyToMinimize;
    }

    public void resetGestureState() {
        this.mReadyToMinimize = false;
        this.mNeedToFullscreenTransition = false;
    }

    final class H extends Handler {
        static final int DISMISS_FREEFORM_RESIZE_GUIDE = 0;

        H() {}

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    FreeformResizeGuide.this.mDismissed = true;
                    if (FreeformResizeGuide.this.mView != null) {
                        FreeformResizeGuide.this.mView.dismiss();
                        if (FreeformResizeGuide.this.mView.isAttachedToWindow()) {
                            FreeformResizeGuide.this.mWindowManager.removeViewImmediate(
                                    FreeformResizeGuide.this.mView);
                        }
                    }
                    FreeformResizeGuide.this.mState = -1;
                    break;
            }
        }
    }

    boolean isDexTaskDocked(int dexTaskDockingState) {
        return (dexTaskDockingState == -1 || dexTaskDockingState == 0) ? false : true;
    }

    public void adjustDexDockingTaskBounds(
            int dexTaskDockingState, Rect taskbounds, int freeformThickness) {
        if (dexTaskDockingState == 1) {
            taskbounds.right -= freeformThickness;
        } else if (dexTaskDockingState == 2) {
            taskbounds.left += freeformThickness;
        }
    }

    public SizeCompatResizeGuide asSizeCompatResizeGuide() {
        return null;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    class TransitionInfo {
        private long mAnimationDuration;
        private Animator.AnimatorListener mDismissListener =
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.samsung.android.multiwindow.FreeformResizeGuide.TransitionInfo.1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animation) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        onAnimationEnd();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        onAnimationEnd();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animation) {}

                    private void onAnimationEnd() {
                        if (FreeformResizeGuide.this.mDismissRequested
                                && !FreeformResizeGuide.this.mDismissed) {
                            FreeformResizeGuide.this.dismiss();
                        }
                    }
                };
        private int mFromAlpha;
        private TimeInterpolator mInterpolator;
        private int mToAlpha;

        TransitionInfo() {
            reset();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mAnimationDuration = 0L;
            this.mInterpolator = null;
            this.mToAlpha = -1;
            this.mFromAlpha = -1;
        }

        long getAnimationDuration(long defaultDuration) {
            return this.mAnimationDuration > 0 ? this.mAnimationDuration : defaultDuration;
        }

        TimeInterpolator getInterpolator(TimeInterpolator defaultInterpolator) {
            return this.mInterpolator != null ? this.mInterpolator : defaultInterpolator;
        }

        int getFromAlpha() {
            return this.mFromAlpha;
        }

        int getToAlpha() {
            return this.mToAlpha;
        }

        void addDismissListener(AnimatorSet animatorSet) {
            animatorSet.addListener(this.mDismissListener);
        }
    }
}
