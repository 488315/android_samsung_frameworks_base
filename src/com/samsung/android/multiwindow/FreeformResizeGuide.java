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
import com.android.internal.policy.DesktopModeCompatUtils;
import com.android.internal.policy.SystemBarUtils;

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

    /* JADX WARN: Removed duplicated region for block: B:37:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateMinMaxSizeIfNeeded(ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, boolean z, boolean z2) {
        char c;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int iDipToPixel = MultiWindowUtils.dipToPixel(z2 ? runningTaskInfo.desktopDefaultMinSize : runningTaskInfo.defaultMinSize, displayMetrics);
        this.mMinHeight = iDipToPixel;
        this.mMinWidth = iDipToPixel;
        if (runningTaskInfo.minWidth != -1) {
            this.mMinWidth = runningTaskInfo.minWidth;
        }
        if (runningTaskInfo.minHeight != -1) {
            this.mMinHeight = runningTaskInfo.minHeight;
        }
        int iDipToPixel2 = MultiWindowUtils.dipToPixel(48, displayMetrics);
        int iDipToPixel3 = MultiWindowUtils.dipToPixel(32, displayMetrics);
        this.mMinWidth = Math.max(iDipToPixel2, this.mMinWidth);
        this.mMinHeight = Math.max(iDipToPixel3, this.mMinHeight);
        if (runningTaskInfo.maxWidth == -1 || runningTaskInfo.maxWidth > rect.width()) {
            this.mMaxWidth = rect.width();
        } else {
            this.mMaxWidth = runningTaskInfo.maxWidth;
        }
        if (runningTaskInfo.maxHeight == -1 || runningTaskInfo.maxHeight > rect.height()) {
            this.mMaxHeight = rect.height();
        } else {
            this.mMaxHeight = runningTaskInfo.maxHeight;
        }
        this.mMaxWidth = Math.max(this.mMaxWidth, this.mMinWidth);
        this.mMaxHeight = Math.max(this.mMaxHeight, this.mMinHeight);
        runningTaskInfo.configuration.windowConfiguration.getDexTaskDockingState();
        if (runningTaskInfo.preserveOrientationOnResize()) {
            int i = runningTaskInfo.resizeMode;
            if (i == 5) {
                c = 2;
                if (c != 1) {
                    this.mMinHeight = (int) (this.mMinWidth * 1.2f);
                    this.mMaxWidth = (int) (this.mMaxHeight / 1.2f);
                } else if (c == 2) {
                    this.mMinWidth = (int) (this.mMinHeight * 1.2f);
                    this.mMaxHeight = (int) (this.mMaxWidth / 1.2f);
                }
            } else if (i == 6) {
                c = 1;
                if (c != 1) {
                }
            } else if (i != 7) {
                c = 0;
                if (c != 1) {
                }
            } else {
                if (z) {
                }
                if (c != 1) {
                }
            }
        }
        if (!this.mInDesktopWindowing || runningTaskInfo.isResizeable) {
            return;
        }
        Rect appBounds = runningTaskInfo.appCompatTaskInfo.topActivityAppBounds;
        if (appBounds.isEmpty() && ((appBounds = runningTaskInfo.configuration.windowConfiguration.getAppBounds()) == null || appBounds.isEmpty())) {
            appBounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        }
        int iWidth = appBounds.width();
        int iHeight = appBounds.height();
        if (iWidth <= 0 || iHeight <= 0) {
            return;
        }
        this.mIsNonResizableInDesktopWindowing = true;
        int desktopViewAppHeaderHeightPx = (runningTaskInfo.topActivityInfo == null || !DesktopModeCompatUtils.shouldExcludeCaptionFromAppBounds(runningTaskInfo.topActivityInfo, runningTaskInfo.isResizeable, runningTaskInfo.appCompatTaskInfo.hasOptOutEdgeToEdge())) ? 0 : SystemBarUtils.getDesktopViewAppHeaderHeightPx(this.mContext);
        boolean z3 = iWidth >= iHeight;
        float aspectRatio = MultiWindowUtils.getAspectRatio(iWidth, iHeight);
        if (z3) {
            int iMax = Math.max(iDipToPixel3, this.mMinHeight);
            this.mMinWidth = (int) ((iMax * aspectRatio) + 0.5f);
            this.mMinHeight = iMax + desktopViewAppHeaderHeightPx;
        } else {
            int iMax2 = Math.max(iDipToPixel2, this.mMinWidth);
            this.mMinWidth = iMax2;
            this.mMinHeight = ((int) ((iMax2 * aspectRatio) + 0.5f)) + desktopViewAppHeaderHeightPx;
        }
        int iWidth2 = rect.width() - 10;
        int iHeight2 = (rect.height() - 10) - desktopViewAppHeaderHeightPx;
        if (iWidth2 >= iHeight2) {
            this.mMaxHeight = iHeight2;
            if (z3) {
                this.mMaxWidth = (int) ((iHeight2 * aspectRatio) + 0.5f);
            } else {
                this.mMaxWidth = (int) ((iHeight2 / aspectRatio) + 0.5f);
            }
        } else {
            this.mMaxWidth = iWidth2;
            if (z3) {
                this.mMaxHeight = (int) ((iWidth2 / aspectRatio) + 0.5f);
            } else {
                this.mMaxHeight = (int) ((iWidth2 * aspectRatio) + 0.5f);
            }
        }
        this.mMaxHeight += desktopViewAppHeaderHeightPx;
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
        int iMax = Math.max(rect.width() - this.mNotAdjustedBounds.width(), rect.height() - this.mNotAdjustedBounds.height()) / 4;
        if ((this.mCtrlType & 1) != 0) {
            rect.left += iMax;
        } else {
            rect.right -= iMax;
        }
        if ((this.mCtrlType & 4) != 0) {
            rect.top += iMax;
        } else {
            rect.bottom -= iMax;
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
