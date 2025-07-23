package com.samsung.android.multiwindow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class FreeformResizeGuide {
    private static final long DEFER_DISMISSING_TIMEOUT_MARGIN = 10;
    private static final int INVALID_MAX_SIZE = -1;
    private static final int INVALID_MIN_SIZE = -1;
    public static final int MINIMUM_VISIBLE_HEIGHT_IN_DP = 32;
    public static final int MINIMUM_VISIBLE_WIDTH_IN_DP = 48;
    public static final int STATE_MINIMIZING = 1;
    public static final int STATE_NONE = -1;
    public static final int STATE_RESIZING = 0;
    private static final String TAG = "FreeformResizeGuide";
    private final Rect mBounds;
    private final Context mContext;
    private int mCtrlType;
    private long mDeferDismissingTimeout;
    private boolean mDismissRequested;
    private boolean mDismissed;
    private final Rect mDisplayFrame;
    private int mFreeformGuideViewFullscreenMargin;
    private final H mH;
    private final boolean mInDesktopWindowing;
    private boolean mIsNonResizableInDesktopWindowing;
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

    public @interface FreeformGuideWindowType {
    }

    boolean isDexTaskDocked(int i) {
        return (i == -1 || i == 0) ? false : true;
    }

    public FreeformResizeGuide(Context context) {
        this(context, 0, null);
    }

    public FreeformResizeGuide(Context context, int i, ComponentName componentName) {
        this(context, i, componentName, false);
    }

    public FreeformResizeGuide(Context context, int i, ComponentName componentName, boolean z) {
        this.mState = -1;
        this.mBounds = new Rect();
        this.mLastBounds = new Rect();
        this.mDisplayFrame = new Rect();
        this.mStableBounds = new Rect();
        this.mMinimizeTriggerBounds = new Rect();
        this.mNotAdjustedBounds = new Rect();
        this.mNeedToFullscreenTransition = false;
        this.mReadyToMinimize = false;
        context = context == null ? ActivityThread.currentActivityThread().getSystemUiContext() : context;
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mWindowManager = windowManager;
        this.mH = new H();
        FreeformResizeGuideView freeformResizeGuideView = (FreeformResizeGuideView) LayoutInflater.from(context).inflate(R.layout.freeform_resize_guide, (ViewGroup) null);
        this.mView = freeformResizeGuideView;
        freeformResizeGuideView.update(i, componentName);
        windowManager.addView(freeformResizeGuideView, generateLayoutParam());
        this.mInDesktopWindowing = z;
    }

    private WindowManager.LayoutParams generateLayoutParam() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2016, 24, -2);
        layoutParams.setTitle("FreeformResizeGuideWindow");
        layoutParams.gravity = 8388659;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.privateFlags |= 16;
        layoutParams.samsungFlags |= 131072;
        layoutParams.windowAnimations = R.style.freeform_resize_guide_window_animation;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }

    public void show(Rect rect) {
        if (rect == null) {
            return;
        }
        this.mLastBounds.set(this.mBounds);
        this.mBounds.set(rect);
        TransitionInfo transitionInfo = this.mTransitionInfo;
        if (transitionInfo != null) {
            this.mView.show(this.mLastBounds, this.mBounds, true, this.mNeedToFullscreenTransition, transitionInfo);
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
        long j = this.mDeferDismissingTimeout;
        long j2 = 0;
        this.mDeferDismissingTimeout = 0L;
        H h = this.mH;
        if (!this.mDismissed && j > 0) {
            j2 = 10 + j;
        }
        h.sendEmptyMessageDelayed(0, j2);
    }

    public boolean updateGuideState(int i) {
        if (this.mState == i) {
            return false;
        }
        this.mState = i;
        this.mView.setDimViewVisibility(i == -1 ? 4 : 0);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a2, code lost:
    
        if (r8 != false) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateMinMaxSizeIfNeeded(android.app.ActivityManager.RunningTaskInfo r6, android.graphics.Rect r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.multiwindow.FreeformResizeGuide.updateMinMaxSizeIfNeeded(android.app.ActivityManager$RunningTaskInfo, android.graphics.Rect, boolean):void");
    }

    public void adjustMinMaxSize(Rect rect) {
        boolean z = rect.width() <= this.mMinWidth;
        boolean z2 = rect.height() <= this.mMinHeight;
        boolean z3 = rect.width() >= this.mMaxWidth;
        boolean z4 = rect.height() >= this.mMaxHeight;
        if (this.mIsNonResizableInDesktopWindowing) {
            if (z || z2) {
                z = true;
                z2 = true;
            }
            if (z3 || z4) {
                z3 = true;
                z4 = true;
            }
        }
        if (z) {
            if ((this.mCtrlType & 1) != 0) {
                rect.left = rect.right - this.mMinWidth;
            } else {
                rect.right = rect.left + this.mMinWidth;
            }
        }
        if (z2) {
            if ((this.mCtrlType & 4) != 0) {
                rect.top = rect.bottom - this.mMinHeight;
            } else {
                rect.bottom = rect.top + this.mMinHeight;
            }
        }
        if (z3) {
            if ((this.mCtrlType & 1) != 0) {
                rect.left = rect.right - this.mMaxWidth;
            } else {
                rect.right = rect.left + this.mMaxWidth;
            }
        }
        if (z4) {
            if ((this.mCtrlType & 4) != 0) {
                rect.top = rect.bottom - this.mMaxHeight;
            } else {
                rect.bottom = rect.top + this.mMaxHeight;
            }
        }
        updateGuideState(((z && z2) || (z3 && z4)) ? 1 : 0);
    }

    public void snapToBounds(Rect rect) {
        snapToBounds(rect, 0L, null, -1, -1, false);
    }

    public void snapToBounds(long j) {
        snapToBounds(null, j, null, -1, -1, false);
    }

    public void snapToBounds(Rect rect, long j, TimeInterpolator timeInterpolator, int i, int i2, boolean z) {
        if (z) {
            this.mDeferDismissingTimeout = j;
        }
        TransitionInfo transitionInfo = this.mTmpTransitionInfo;
        if (transitionInfo == null) {
            this.mTmpTransitionInfo = new TransitionInfo();
        } else {
            transitionInfo.reset();
        }
        this.mTmpTransitionInfo.mAnimationDuration = j;
        this.mTmpTransitionInfo.mInterpolator = timeInterpolator;
        this.mTmpTransitionInfo.mFromAlpha = i;
        this.mTmpTransitionInfo.mToAlpha = i2;
        this.mTransitionInfo = this.mTmpTransitionInfo;
        snapToFullscreen(rect);
    }

    public void setCtrlType(int i) {
        this.mCtrlType = i;
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

    private void performHapticFeedback(int i) {
        this.mView.performHapticFeedback(i);
    }

    public void handleResizeGesture(Rect rect, int i, int i2) {
        snapToFullscreenIfNeeded(rect, i2);
        checkIfReadyToMinimize(rect, i, i2);
    }

    public boolean snapToFullscreenIfNeeded(Rect rect, int i) {
        if ((this.mCtrlType & 4) != 0 && i <= this.mStableBounds.top) {
            return snapToFullscreen(rect);
        }
        if (this.mCtrlType == 0 && i <= this.mStableBounds.top) {
            return snapToFullscreen(rect);
        }
        return snapToFullscreen(null);
    }

    private boolean snapToFullscreen(Rect rect) {
        boolean z = rect != null;
        this.mNeedToFullscreenTransition = z;
        if (z) {
            rect.set(this.mStableBounds.left + this.mFreeformGuideViewFullscreenMargin, this.mStableBounds.top + this.mFreeformGuideViewFullscreenMargin, this.mStableBounds.right - this.mFreeformGuideViewFullscreenMargin, this.mStableBounds.bottom - this.mFreeformGuideViewFullscreenMargin);
        }
        return this.mNeedToFullscreenTransition;
    }

    private void checkIfReadyToMinimize(Rect rect, int i, int i2) {
        boolean z = false;
        if (rect.width() > this.mMinWidth || rect.height() > this.mMinHeight) {
            this.mReadyToMinimize = false;
            return;
        }
        this.mMinimizeTriggerBounds.set(rect.left + this.mMinimizeFreeformPadding, rect.top + this.mMinimizeFreeformPadding, rect.right - this.mMinimizeFreeformPadding, rect.bottom - this.mMinimizeFreeformPadding);
        if ((this.mCtrlType & 1) != 0) {
            this.mMinimizeTriggerBounds.right = rect.right;
        }
        if ((this.mCtrlType & 4) != 0) {
            this.mMinimizeTriggerBounds.bottom = rect.bottom;
        }
        if ((this.mCtrlType & 2) != 0) {
            this.mMinimizeTriggerBounds.left = rect.left;
        }
        if ((this.mCtrlType & 8) != 0) {
            this.mMinimizeTriggerBounds.top = rect.top;
        }
        int max = Math.max(rect.width() - this.mNotAdjustedBounds.width(), rect.height() - this.mNotAdjustedBounds.height()) / 4;
        if ((this.mCtrlType & 1) != 0) {
            rect.left += max;
        } else {
            rect.right -= max;
        }
        if ((this.mCtrlType & 4) != 0) {
            rect.top += max;
        } else {
            rect.bottom -= max;
        }
        if (this.mMinimizeTriggerBounds.contains(i, i2)) {
            if (this.mReadyToMinimize) {
                return;
            }
            this.mReadyToMinimize = true;
            if (isShowingAppIcon()) {
                return;
            }
            performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(49));
            showAppIcon();
            return;
        }
        if (this.mReadyToMinimize) {
            this.mReadyToMinimize = false;
            if ((this.mCtrlType & 1) != 0 && this.mMinimizeTriggerBounds.right < i) {
                z = true;
            }
            if ((this.mCtrlType & 4) != 0 && this.mMinimizeTriggerBounds.bottom < i2) {
                z = true;
            }
            if ((this.mCtrlType & 2) != 0 && this.mMinimizeTriggerBounds.left > i) {
                z = true;
            }
            if (((this.mCtrlType & 8) == 0 || this.mMinimizeTriggerBounds.top <= i2) ? z : true) {
                return;
            }
            hideAppIcon();
        }
    }

    public void updateResizeGestureInfo(Rect rect, Rect rect2) {
        this.mDisplayFrame.set(rect);
        this.mStableBounds.set(rect2);
        this.mFreeformGuideViewFullscreenMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_fullscreen_margin);
        this.mMinimizeFreeformPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.minimize_freeform_padding);
    }

    public void setNotAdjustedBounds(Rect rect) {
        this.mNotAdjustedBounds.set(rect);
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

        H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            FreeformResizeGuide.this.mDismissed = true;
            if (FreeformResizeGuide.this.mView != null) {
                FreeformResizeGuide.this.mView.dismiss();
                if (FreeformResizeGuide.this.mView.isAttachedToWindow()) {
                    FreeformResizeGuide.this.mWindowManager.removeViewImmediate(FreeformResizeGuide.this.mView);
                }
            }
            FreeformResizeGuide.this.mState = -1;
        }
    }

    public void adjustDexDockingTaskBounds(int i, Rect rect, int i2) {
        if (i == 1) {
            rect.right -= i2;
        } else if (i == 2) {
            rect.left += i2;
        }
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    class TransitionInfo {
        private long mAnimationDuration;
        private Animator.AnimatorListener mDismissListener = new Animator.AnimatorListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuide.TransitionInfo.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                onAnimationEnd();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                onAnimationEnd();
            }

            private void onAnimationEnd() {
                if (!FreeformResizeGuide.this.mDismissRequested || FreeformResizeGuide.this.mDismissed) {
                    return;
                }
                FreeformResizeGuide.this.dismiss();
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

        long getAnimationDuration(long j) {
            long j2 = this.mAnimationDuration;
            return j2 > 0 ? j2 : j;
        }

        TimeInterpolator getInterpolator(TimeInterpolator timeInterpolator) {
            TimeInterpolator timeInterpolator2 = this.mInterpolator;
            return timeInterpolator2 != null ? timeInterpolator2 : timeInterpolator;
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
