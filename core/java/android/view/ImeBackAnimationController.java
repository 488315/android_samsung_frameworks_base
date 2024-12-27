package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.util.Log;
import android.view.animation.BackGestureInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.ImeTracker;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;

import java.io.PrintWriter;

public class ImeBackAnimationController implements OnBackAnimationCallback {
    private static final float PEEK_FRACTION = 0.1f;
    private static final int POST_COMMIT_CANCEL_DURATION_MS = 50;
    private static final int POST_COMMIT_DURATION_MS = 200;
    private static final String TAG = "ImeBackAnimationController";
    private final InsetsController mInsetsController;
    private final ViewRootImpl mViewRoot;
    private static final Interpolator BACK_GESTURE = new BackGestureInterpolator();
    private static final Interpolator EMPHASIZED_DECELERATE =
            new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    private static final Interpolator STANDARD_ACCELERATE =
            new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
    private WindowInsetsAnimationController mWindowInsetsAnimationController = null;
    private ValueAnimator mPostCommitAnimator = null;
    private float mLastProgress = 0.0f;
    private boolean mTriggerBack = false;
    private boolean mIsPreCommitAnimationInProgress = false;
    private int mStartRootScrollY = 0;

    public ImeBackAnimationController(ViewRootImpl viewRoot, InsetsController insetsController) {
        this.mInsetsController = insetsController;
        this.mViewRoot = viewRoot;
    }

    @Override // android.window.OnBackAnimationCallback
    public void onBackStarted(BackEvent backEvent) {
        if (!isBackAnimationAllowed()) {
            Log.d(
                    TAG,
                    "onBackStarted -> not playing predictive back animation due to softinput mode"
                        + " adjustResize AND no animation callback registered");
            return;
        }
        if (isHideAnimationInProgress()) {
            return;
        }
        this.mIsPreCommitAnimationInProgress = true;
        if (this.mWindowInsetsAnimationController != null) {
            resetPostCommitAnimator();
            setPreCommitProgress(0.0f);
        } else {
            this.mInsetsController.controlWindowInsetsAnimation(
                    WindowInsets.Type.ime(),
                    null,
                    new WindowInsetsAnimationControlListener() { // from class:
                                                                 // android.view.ImeBackAnimationController.1
                        @Override // android.view.WindowInsetsAnimationControlListener
                        public void onReady(WindowInsetsAnimationController controller, int types) {
                            ImeBackAnimationController.this.mWindowInsetsAnimationController =
                                    controller;
                            if (ImeBackAnimationController.this.isAdjustPan()) {
                                ImeBackAnimationController.this.mStartRootScrollY =
                                        ImeBackAnimationController.this.mViewRoot.mScrollY;
                            }
                            if (ImeBackAnimationController.this.mIsPreCommitAnimationInProgress) {
                                ImeBackAnimationController.this.setPreCommitProgress(
                                        ImeBackAnimationController.this.mLastProgress);
                            } else {
                                ImeBackAnimationController.this.startPostCommitAnim(
                                        ImeBackAnimationController.this.mTriggerBack);
                            }
                        }

                        @Override // android.view.WindowInsetsAnimationControlListener
                        public void onFinished(WindowInsetsAnimationController controller) {
                            ImeBackAnimationController.this.reset();
                        }

                        @Override // android.view.WindowInsetsAnimationControlListener
                        public void onCancelled(WindowInsetsAnimationController controller) {
                            ImeBackAnimationController.this.reset();
                        }
                    },
                    false,
                    -1L,
                    null,
                    2,
                    true);
        }
    }

    @Override // android.window.OnBackAnimationCallback
    public void onBackProgressed(BackEvent backEvent) {
        this.mLastProgress = backEvent.getProgress();
        setPreCommitProgress(this.mLastProgress);
    }

    @Override // android.window.OnBackAnimationCallback
    public void onBackCancelled() {
        if (isBackAnimationAllowed()) {
            startPostCommitAnim(false);
        }
    }

    @Override // android.window.OnBackInvokedCallback
    public void onBackInvoked() {
        if (!isBackAnimationAllowed() || !this.mIsPreCommitAnimationInProgress) {
            this.mInsetsController.hide(WindowInsets.Type.ime());
        } else {
            startPostCommitAnim(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreCommitProgress(float progress) {
        if (!isHideAnimationInProgress() && this.mWindowInsetsAnimationController != null) {
            float hiddenY = this.mWindowInsetsAnimationController.getHiddenStateInsets().bottom;
            float shownY = this.mWindowInsetsAnimationController.getShownStateInsets().bottom;
            float imeHeight = shownY - hiddenY;
            float interpolatedProgress = BACK_GESTURE.getInterpolation(progress);
            int newY = (int) (imeHeight - ((imeHeight * 0.1f) * interpolatedProgress));
            if (this.mStartRootScrollY != 0) {
                this.mViewRoot.setScrollY(
                        (int) (this.mStartRootScrollY * (1.0f - (0.1f * interpolatedProgress))));
            }
            this.mWindowInsetsAnimationController.setInsetsAndAlpha(
                    Insets.of(0, 0, 0, newY), 1.0f, progress);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPostCommitAnim(final boolean triggerBack) {
        int targetBottomInset;
        this.mIsPreCommitAnimationInProgress = false;
        if (this.mWindowInsetsAnimationController == null || isHideAnimationInProgress()) {
            this.mTriggerBack = triggerBack;
            return;
        }
        this.mTriggerBack = triggerBack;
        int currentBottomInset = this.mWindowInsetsAnimationController.getCurrentInsets().bottom;
        if (triggerBack) {
            targetBottomInset = this.mWindowInsetsAnimationController.getHiddenStateInsets().bottom;
        } else {
            targetBottomInset = this.mWindowInsetsAnimationController.getShownStateInsets().bottom;
        }
        this.mPostCommitAnimator = ValueAnimator.ofFloat(currentBottomInset, targetBottomInset);
        this.mPostCommitAnimator.setInterpolator(
                triggerBack ? STANDARD_ACCELERATE : EMPHASIZED_DECELERATE);
        this.mPostCommitAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // android.view.ImeBackAnimationController$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ImeBackAnimationController.this.lambda$startPostCommitAnim$0(valueAnimator);
                    }
                });
        this.mPostCommitAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // android.view.ImeBackAnimationController.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (ImeBackAnimationController.this.mIsPreCommitAnimationInProgress) {
                            return;
                        }
                        if (ImeBackAnimationController.this.mWindowInsetsAnimationController
                                != null) {
                            ImeBackAnimationController.this.mWindowInsetsAnimationController.finish(
                                    !triggerBack);
                        }
                        ImeBackAnimationController.this.reset();
                    }
                });
        this.mPostCommitAnimator.setDuration(triggerBack ? 200L : 50L);
        this.mPostCommitAnimator.start();
        if (triggerBack) {
            this.mInsetsController.setPredictiveBackImeHideAnimInProgress(true);
            notifyHideIme();
        }
        if (this.mStartRootScrollY != 0 && !triggerBack) {
            this.mInsetsController.getHost().notifyInsetsChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPostCommitAnim$0(ValueAnimator animation) {
        int bottomInset = (int) ((Float) animation.getAnimatedValue()).floatValue();
        if (this.mWindowInsetsAnimationController != null) {
            this.mWindowInsetsAnimationController.setInsetsAndAlpha(
                    Insets.of(0, 0, 0, bottomInset), 1.0f, animation.getAnimatedFraction());
        } else {
            reset();
        }
    }

    private void notifyHideIme() {
        ImeTracker.Token statsToken = ImeTracker.forLogging().onStart(2, 5, 52, true);
        this.mInsetsController
                .getHost()
                .getInputMethodManager()
                .notifyImeHidden(this.mInsetsController.getHost().getWindowToken(), statsToken);
        this.mInsetsController.setRequestedVisibleTypes(0, WindowInsets.Type.ime());
        this.mInsetsController.onAnimationStateChanged(WindowInsets.Type.ime(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        this.mWindowInsetsAnimationController = null;
        resetPostCommitAnimator();
        this.mLastProgress = 0.0f;
        this.mTriggerBack = false;
        this.mIsPreCommitAnimationInProgress = false;
        this.mInsetsController.setPredictiveBackImeHideAnimInProgress(false);
        this.mStartRootScrollY = 0;
    }

    private void resetPostCommitAnimator() {
        if (this.mPostCommitAnimator != null) {
            this.mPostCommitAnimator.cancel();
            this.mPostCommitAnimator = null;
        }
    }

    private boolean isBackAnimationAllowed() {
        return (this.mViewRoot.mWindowAttributes.softInputMode & 240) != 16
                || (this.mViewRoot.mView != null
                        && this.mViewRoot.mView.hasWindowInsetsAnimationCallback())
                || this.mViewRoot.mAttachInfo.mContentOnApplyWindowInsetsListener == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAdjustPan() {
        return (this.mViewRoot.mWindowAttributes.softInputMode & 240) == 32;
    }

    private boolean isHideAnimationInProgress() {
        return this.mPostCommitAnimator != null && this.mTriggerBack;
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "    ";
        writer.println(prefix + "ImeBackAnimationController:");
        writer.println(innerPrefix + "mLastProgress=" + this.mLastProgress);
        writer.println(innerPrefix + "mTriggerBack=" + this.mTriggerBack);
        writer.println(
                innerPrefix
                        + "mIsPreCommitAnimationInProgress="
                        + this.mIsPreCommitAnimationInProgress);
        writer.println(innerPrefix + "mStartRootScrollY=" + this.mStartRootScrollY);
        writer.println(innerPrefix + "isBackAnimationAllowed=" + isBackAnimationAllowed());
        writer.println(innerPrefix + "isAdjustPan=" + isAdjustPan());
        writer.println(innerPrefix + "isHideAnimationInProgress=" + isHideAnimationInProgress());
    }
}
