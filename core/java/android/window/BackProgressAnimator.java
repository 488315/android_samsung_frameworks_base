package android.window;

import android.util.FloatProperty;
import android.view.Choreographer;

import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.FlingAnimation;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;

/* loaded from: classes4.dex */
public class BackProgressAnimator implements DynamicAnimation.OnAnimationUpdateListener {
    private static final float FLING_FRICTION = 8.0f;
    private static final FloatProperty<BackProgressAnimator> PROGRESS_PROP =
            new FloatProperty<BackProgressAnimator>(
                    "progress") { // from class: android.window.BackProgressAnimator.1
                @Override // android.util.FloatProperty
                public void setValue(BackProgressAnimator animator, float value) {
                    animator.setProgress(value);
                }

                @Override // android.util.Property
                public Float get(BackProgressAnimator object) {
                    return Float.valueOf(object.getProgress());
                }
            };
    private static final float SCALE_FACTOR = 100.0f;
    private Runnable mBackCancelledFinishRunnable;
    private Runnable mBackInvokedFinishRunnable;
    private FlingAnimation mBackInvokedFlingAnim;
    private ProgressCallback mCallback;
    private BackMotionEvent mLastBackEvent;
    private float mProgress = 0.0f;
    private float mVelocity = 0.0f;
    private boolean mBackAnimationInProgress = false;
    private final DynamicAnimation.OnAnimationEndListener mOnAnimationEndListener =
            new DynamicAnimation
                    .OnAnimationEndListener() { // from class:
                                                // android.window.BackProgressAnimator$$ExternalSyntheticLambda0
                @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(
                        DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    BackProgressAnimator.this.lambda$new$0(dynamicAnimation, z, f, f2);
                }
            };
    private final DynamicAnimation.OnAnimationUpdateListener mOnBackInvokedFlingUpdateListener =
            new DynamicAnimation
                    .OnAnimationUpdateListener() { // from class:
                                                   // android.window.BackProgressAnimator$$ExternalSyntheticLambda1
                @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                public final void onAnimationUpdate(
                        DynamicAnimation dynamicAnimation, float f, float f2) {
                    BackProgressAnimator.this.lambda$new$1(dynamicAnimation, f, f2);
                }
            };
    private final SpringAnimation mSpring = new SpringAnimation(this, PROGRESS_PROP);

    public interface ProgressCallback {
        void onProgressUpdate(BackEvent backEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(
            DynamicAnimation animation, boolean canceled, float value, float velocity) {
        if (this.mBackCancelledFinishRunnable != null) {
            invokeBackCancelledRunnable();
        }
        if (this.mBackInvokedFinishRunnable != null) {
            invokeBackInvokedRunnable();
        }
        reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(
            DynamicAnimation animation, float progress, float velocity) {
        updateProgressValue(progress, velocity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float progress) {
        this.mProgress = progress;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getProgress() {
        return this.mProgress;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
        if (this.mBackInvokedFinishRunnable == null) {
            updateProgressValue(value, velocity);
        }
    }

    public BackProgressAnimator() {
        this.mSpring.addUpdateListener(this);
        this.mSpring.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
    }

    public void onBackProgressed(BackMotionEvent event) {
        if (!this.mBackAnimationInProgress) {
            return;
        }
        this.mLastBackEvent = event;
        if (this.mSpring == null) {
            return;
        }
        this.mSpring.animateToFinalPosition(event.getProgress() * 100.0f);
    }

    public void onBackStarted(BackMotionEvent event, ProgressCallback callback) {
        this.mLastBackEvent = event;
        this.mCallback = callback;
        this.mBackAnimationInProgress = true;
        updateProgressValue(0.0f, 0.0f);
    }

    public void reset() {
        if (this.mBackCancelledFinishRunnable != null) {
            updateProgressValue(0.0f, 0.0f);
            invokeBackCancelledRunnable();
        } else if (this.mBackInvokedFinishRunnable != null) {
            invokeBackInvokedRunnable();
        }
        if (this.mBackInvokedFlingAnim != null) {
            this.mBackInvokedFlingAnim.cancel();
            this.mBackInvokedFlingAnim = null;
        }
        this.mSpring.animateToFinalPosition(0.0f);
        if (this.mSpring.canSkipToEnd()) {
            this.mSpring.skipToEnd();
        } else {
            this.mSpring.cancel();
        }
        this.mBackAnimationInProgress = false;
        this.mLastBackEvent = null;
        this.mCallback = null;
        this.mProgress = 0.0f;
    }

    public void onBackInvoked(Runnable finishCallback) {
        this.mBackInvokedFinishRunnable = finishCallback;
        this.mSpring.animateToFinalPosition(0.0f);
        this.mBackInvokedFlingAnim =
                new FlingAnimation(new FloatValueHolder())
                        .setStartValue(this.mProgress)
                        .setFriction(FLING_FRICTION)
                        .setStartVelocity(this.mVelocity)
                        .setMinValue(0.0f)
                        .setMaxValue(100.0f);
        this.mBackInvokedFlingAnim.addUpdateListener(this.mOnBackInvokedFlingUpdateListener);
        this.mBackInvokedFlingAnim.addEndListener(this.mOnAnimationEndListener);
        this.mBackInvokedFlingAnim.start();
        this.mBackInvokedFlingAnim.doAnimationFrame(
                Choreographer.getInstance().getLastFrameTimeNanos() / 1000000);
    }

    public void onBackCancelled(Runnable finishCallback) {
        this.mBackCancelledFinishRunnable = finishCallback;
        this.mSpring.addEndListener(this.mOnAnimationEndListener);
        this.mSpring.animateToFinalPosition(0.0f);
    }

    public void removeOnBackCancelledFinishCallback() {
        this.mSpring.removeEndListener(this.mOnAnimationEndListener);
        this.mBackCancelledFinishRunnable = null;
    }

    boolean isBackAnimationInProgress() {
        return this.mBackAnimationInProgress;
    }

    public float getVelocity() {
        return this.mVelocity / 100.0f;
    }

    private void updateProgressValue(float progress, float velocity) {
        this.mVelocity = velocity;
        if (this.mLastBackEvent == null
                || this.mCallback == null
                || !this.mBackAnimationInProgress) {
            return;
        }
        this.mCallback.onProgressUpdate(
                new BackEvent(
                        this.mLastBackEvent.getTouchX(),
                        this.mLastBackEvent.getTouchY(),
                        progress / 100.0f,
                        this.mLastBackEvent.getSwipeEdge()));
    }

    private void invokeBackCancelledRunnable() {
        this.mSpring.removeEndListener(this.mOnAnimationEndListener);
        this.mBackCancelledFinishRunnable.run();
        this.mBackCancelledFinishRunnable = null;
    }

    private void invokeBackInvokedRunnable() {
        this.mBackInvokedFlingAnim.removeUpdateListener(this.mOnBackInvokedFlingUpdateListener);
        this.mBackInvokedFlingAnim.removeEndListener(this.mOnAnimationEndListener);
        this.mBackInvokedFinishRunnable.run();
        this.mBackInvokedFinishRunnable = null;
    }
}
