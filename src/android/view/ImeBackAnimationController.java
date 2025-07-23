package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.util.Log;
import android.view.WindowInsets;
import android.view.animation.BackGestureInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class ImeBackAnimationController implements OnBackAnimationCallback {
    private static final Interpolator BACK_GESTURE = new BackGestureInterpolator();
    private static final Interpolator EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    private static final float PEEK_FRACTION = 0.1f;
    private static final int POST_COMMIT_CANCEL_DURATION_MS = 50;
    private static final int POST_COMMIT_DURATION_MS = 200;
    private static final String TAG = "ImeBackAnimationController";
    private final InsetsController mInsetsController;
    private final ViewRootImpl mViewRoot;
    private WindowInsetsAnimationController mWindowInsetsAnimationController = null;
    private ValueAnimator mPostCommitAnimator = null;
    private float mLastProgress = 0.0f;
    private boolean mTriggerBack = false;
    private boolean mIsPreCommitAnimationInProgress = false;
    private int mStartRootScrollY = 0;

    public ImeBackAnimationController(ViewRootImpl viewRootImpl, InsetsController insetsController) {
        this.mInsetsController = insetsController;
        this.mViewRoot = viewRootImpl;
    }

    @Override // android.window.OnBackAnimationCallback
    public void onBackStarted(BackEvent backEvent) {
        if (!isBackAnimationAllowed()) {
            Log.d(TAG, "onBackStarted -> not playing predictive back animation due to softinput mode adjustResize AND no animation callback registered");
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
            this.mInsetsController.controlWindowInsetsAnimation(WindowInsets.Type.ime(), null, new WindowInsetsAnimationControlListener() { // from class: android.view.ImeBackAnimationController.1
                @Override // android.view.WindowInsetsAnimationControlListener
                public void onReady(WindowInsetsAnimationController windowInsetsAnimationController, int i) {
                    ImeBackAnimationController.this.mWindowInsetsAnimationController = windowInsetsAnimationController;
                    if (ImeBackAnimationController.this.isAdjustPan()) {
                        ImeBackAnimationController imeBackAnimationController = ImeBackAnimationController.this;
                        imeBackAnimationController.mStartRootScrollY = imeBackAnimationController.mViewRoot.mScrollY;
                    }
                    if (ImeBackAnimationController.this.mIsPreCommitAnimationInProgress) {
                        ImeBackAnimationController imeBackAnimationController2 = ImeBackAnimationController.this;
                        imeBackAnimationController2.setPreCommitProgress(imeBackAnimationController2.mLastProgress);
                    } else {
                        ImeBackAnimationController imeBackAnimationController3 = ImeBackAnimationController.this;
                        imeBackAnimationController3.startPostCommitAnim(imeBackAnimationController3.mTriggerBack);
                    }
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
                    ImeBackAnimationController.this.reset();
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
                    ImeBackAnimationController.this.reset();
                }
            }, false, -1L, null, 2, true);
        }
    }

    @Override // android.window.OnBackAnimationCallback
    public void onBackProgressed(BackEvent backEvent) {
        float progress = backEvent.getProgress();
        this.mLastProgress = progress;
        setPreCommitProgress(progress);
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
            notifyHideIme();
        } else {
            startPostCommitAnim(true);
        }
        if (Flags.refactorInsetsController()) {
            this.mInsetsController.getHost().getInputMethodManager().getImeOnBackInvokedDispatcher().preliminaryClear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreCommitProgress(float f) {
        if (isHideAnimationInProgress()) {
            return;
        }
        setInterpolatedProgress(BACK_GESTURE.getInterpolation(f) * 0.1f);
    }

    private void setInterpolatedProgress(float f) {
        if (this.mWindowInsetsAnimationController != null) {
            float f2 = this.mWindowInsetsAnimationController.getShownStateInsets().bottom - r0.getHiddenStateInsets().bottom;
            int i = (int) (f2 - (f * f2));
            int i2 = this.mStartRootScrollY;
            if (i2 != 0) {
                this.mViewRoot.setScrollY((int) (i2 * (1.0f - f)));
            }
            this.mWindowInsetsAnimationController.setInsetsAndAlpha(Insets.of(0, 0, 0, i), 1.0f, f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPostCommitAnim(final boolean z) {
        Interpolator interpolator;
        long j;
        this.mIsPreCommitAnimationInProgress = false;
        if (this.mWindowInsetsAnimationController == null || isHideAnimationInProgress()) {
            this.mTriggerBack = z;
            return;
        }
        this.mTriggerBack = z;
        this.mPostCommitAnimator = ValueAnimator.ofFloat(BACK_GESTURE.getInterpolation(this.mLastProgress) * 0.1f, z ? 1.0f : 0.0f);
        if (z && this.mViewRoot.mView.hasWindowInsetsAnimationCallback() && this.mWindowInsetsAnimationController.getShownStateInsets().bottom != 0) {
            interpolator = InsetsController.SYNC_IME_INTERPOLATOR;
            j = 285;
        } else if (z) {
            interpolator = InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            j = 200;
        } else {
            interpolator = EMPHASIZED_DECELERATE;
            j = 50;
        }
        this.mPostCommitAnimator.setInterpolator(interpolator);
        this.mPostCommitAnimator.setDuration(j);
        this.mPostCommitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.view.ImeBackAnimationController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ImeBackAnimationController.this.lambda$startPostCommitAnim$0(valueAnimator);
            }
        });
        this.mPostCommitAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.view.ImeBackAnimationController.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ImeBackAnimationController.this.mIsPreCommitAnimationInProgress) {
                    return;
                }
                if (ImeBackAnimationController.this.mWindowInsetsAnimationController != null) {
                    ImeBackAnimationController.this.mWindowInsetsAnimationController.finish(!z);
                }
                ImeBackAnimationController.this.reset();
            }
        });
        this.mPostCommitAnimator.start();
        if (z) {
            this.mInsetsController.setPredictiveBackImeHideAnimInProgress(true);
            notifyHideIme();
            this.mInsetsController.setRequestedVisibleTypes(0, WindowInsets.Type.ime());
            this.mInsetsController.onAnimationStateChanged(WindowInsets.Type.ime(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPostCommitAnim$0(ValueAnimator valueAnimator) {
        if (this.mWindowInsetsAnimationController != null) {
            setInterpolatedProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
        } else {
            reset();
        }
    }

    private void notifyHideIme() {
        this.mInsetsController.getHost().getInputMethodManager().notifyImeHidden(this.mInsetsController.getHost().getWindowToken(), ImeTracker.forLogging().onStart(2, 5, 52, true));
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
        ValueAnimator valueAnimator = this.mPostCommitAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mPostCommitAnimator = null;
        }
    }

    private boolean isBackAnimationAllowed() {
        if (this.mViewRoot.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 6) {
            return false;
        }
        if ((this.mViewRoot.mWindowAttributes.softInputMode & 240) == 16) {
            return (this.mViewRoot.mView != null && this.mViewRoot.mView.hasWindowInsetsAnimationCallback()) || this.mViewRoot.mAttachInfo.mContentOnApplyWindowInsetsListener == null;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAdjustPan() {
        return (this.mViewRoot.mWindowAttributes.softInputMode & 240) == 32;
    }

    private boolean isHideAnimationInProgress() {
        return this.mPostCommitAnimator != null && this.mTriggerBack;
    }

    boolean isAnimationInProgress() {
        return this.mIsPreCommitAnimationInProgress || this.mWindowInsetsAnimationController != null;
    }

    public void dump(String str, PrintWriter printWriter) {
        String str2 = str + "    ";
        printWriter.println(str + "ImeBackAnimationController:");
        printWriter.println(str2 + "mLastProgress=" + this.mLastProgress);
        printWriter.println(str2 + "mTriggerBack=" + this.mTriggerBack);
        printWriter.println(str2 + "mIsPreCommitAnimationInProgress=" + this.mIsPreCommitAnimationInProgress);
        printWriter.println(str2 + "mStartRootScrollY=" + this.mStartRootScrollY);
        printWriter.println(str2 + "isBackAnimationAllowed=" + isBackAnimationAllowed());
        printWriter.println(str2 + "isAdjustPan=" + isAdjustPan());
        printWriter.println(str2 + "isHideAnimationInProgress=" + isHideAnimationInProgress());
    }
}
