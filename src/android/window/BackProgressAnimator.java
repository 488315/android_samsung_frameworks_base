package android.window;

import android.util.FloatProperty;
import android.view.Choreographer;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.FlingAnimation;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;

/* loaded from: classes5.dex */
public class BackProgressAnimator implements DynamicAnimation.OnAnimationUpdateListener {
    private static final float BUTTON_SPRING_STIFFNESS = 100.0f;
    private static final float FLING_FRICTION = 8.0f;
    private static final FloatProperty<BackProgressAnimator> PROGRESS_PROP = new FloatProperty<BackProgressAnimator>("progress") { // from class: android.window.BackProgressAnimator.1
        @Override // android.util.FloatProperty
        public void setValue(BackProgressAnimator backProgressAnimator, float f) {
            backProgressAnimator.setProgress(f);
        }

        @Override // android.util.Property
        public Float get(BackProgressAnimator backProgressAnimator) {
            return Float.valueOf(backProgressAnimator.getProgress());
        }
    };
    private static final float SCALE_FACTOR = 100.0f;
    private Runnable mBackCancelledFinishRunnable;
    private Runnable mBackInvokedFinishRunnable;
    private FlingAnimation mBackInvokedFlingAnim;
    private final SpringForce mButtonSpringForce;
    private ProgressCallback mCallback;
    private final SpringForce mGestureSpringForce;
    private BackMotionEvent mLastBackEvent;
    private final DynamicAnimation.OnAnimationEndListener mOnAnimationEndListener;
    private final DynamicAnimation.OnAnimationUpdateListener mOnBackInvokedFlingUpdateListener;
    private final SpringAnimation mSpring;
    private float mProgress = 0.0f;
    private float mVelocity = 0.0f;
    private boolean mBackAnimationInProgress = false;

    public interface ProgressCallback {
        void onProgressUpdate(BackEvent backEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        if (this.mBackCancelledFinishRunnable != null) {
            invokeBackCancelledRunnable();
        }
        if (this.mBackInvokedFinishRunnable != null) {
            invokeBackInvokedRunnable();
        }
        reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(DynamicAnimation dynamicAnimation, float f, float f2) {
        updateProgressValue(f, f2, dynamicAnimation.getLastFrameTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float f) {
        this.mProgress = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getProgress() {
        return this.mProgress;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
    public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
        if (this.mBackInvokedFinishRunnable == null) {
            updateProgressValue(f, f2, dynamicAnimation.getLastFrameTime());
        }
    }

    public BackProgressAnimator() {
        SpringForce dampingRatio = new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f);
        this.mGestureSpringForce = dampingRatio;
        this.mButtonSpringForce = new SpringForce().setDampingRatio(1.0f);
        this.mOnAnimationEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: android.window.BackProgressAnimator$$ExternalSyntheticLambda0
            @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                BackProgressAnimator.this.lambda$new$0(dynamicAnimation, z, f, f2);
            }
        };
        this.mOnBackInvokedFlingUpdateListener = new DynamicAnimation.OnAnimationUpdateListener() { // from class: android.window.BackProgressAnimator$$ExternalSyntheticLambda1
            @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                BackProgressAnimator.this.lambda$new$1(dynamicAnimation, f, f2);
            }
        };
        SpringAnimation springAnimation = new SpringAnimation(this, PROGRESS_PROP);
        this.mSpring = springAnimation;
        springAnimation.addUpdateListener(this);
        springAnimation.setSpring(dampingRatio);
    }

    public void onBackProgressed(BackMotionEvent backMotionEvent) {
        if (this.mBackAnimationInProgress) {
            if (Flags.predictiveBackSwipeEdgeNoneApi() && backMotionEvent.getSwipeEdge() == 2) {
                return;
            }
            this.mLastBackEvent = backMotionEvent;
            SpringAnimation springAnimation = this.mSpring;
            if (springAnimation == null) {
                return;
            }
            springAnimation.animateToFinalPosition(backMotionEvent.getProgress() * 100.0f);
        }
    }

    public void onBackStarted(BackMotionEvent backMotionEvent, ProgressCallback progressCallback) {
        this.mLastBackEvent = backMotionEvent;
        this.mCallback = progressCallback;
        this.mBackAnimationInProgress = true;
        updateProgressValue(0.0f, 0.0f, System.nanoTime() / 1000000);
        if (Flags.predictiveBackSwipeEdgeNoneApi()) {
            if (backMotionEvent.getSwipeEdge() == 2) {
                this.mButtonSpringForce.setStiffness(100.0f);
                this.mSpring.setSpring(this.mButtonSpringForce);
                this.mSpring.animateToFinalPosition(100.0f);
                return;
            } else {
                this.mSpring.setSpring(this.mGestureSpringForce);
                onBackProgressed(backMotionEvent);
                return;
            }
        }
        onBackProgressed(backMotionEvent);
    }

    public void reset() {
        if (this.mBackCancelledFinishRunnable != null) {
            updateProgressValue(0.0f, 0.0f, System.nanoTime() / 1000000);
            invokeBackCancelledRunnable();
        } else if (this.mBackInvokedFinishRunnable != null) {
            invokeBackInvokedRunnable();
        }
        FlingAnimation flingAnimation = this.mBackInvokedFlingAnim;
        if (flingAnimation != null) {
            flingAnimation.cancel();
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

    public void onBackInvoked(Runnable runnable) {
        this.mBackInvokedFinishRunnable = runnable;
        this.mSpring.animateToFinalPosition(0.0f);
        FlingAnimation maxValue = new FlingAnimation(new FloatValueHolder()).setStartValue(this.mProgress).setFriction(FLING_FRICTION).setStartVelocity(this.mVelocity).setMinValue(0.0f).setMaxValue(100.0f);
        this.mBackInvokedFlingAnim = maxValue;
        maxValue.addUpdateListener(this.mOnBackInvokedFlingUpdateListener);
        this.mBackInvokedFlingAnim.addEndListener(this.mOnAnimationEndListener);
        this.mBackInvokedFlingAnim.start();
        this.mBackInvokedFlingAnim.doAnimationFrame(Choreographer.getInstance().getLastFrameTimeNanos() / 1000000);
    }

    public void onBackCancelled(Runnable runnable) {
        this.mButtonSpringForce.setStiffness(1500.0f);
        this.mBackCancelledFinishRunnable = runnable;
        this.mSpring.addEndListener(this.mOnAnimationEndListener);
        this.mSpring.animateToFinalPosition(0.0f);
    }

    public void removeOnBackCancelledFinishCallback() {
        this.mSpring.removeEndListener(this.mOnAnimationEndListener);
        this.mBackCancelledFinishRunnable = null;
    }

    public void removeOnBackInvokedFinishCallback() {
        FlingAnimation flingAnimation = this.mBackInvokedFlingAnim;
        if (flingAnimation != null) {
            flingAnimation.removeUpdateListener(this.mOnBackInvokedFlingUpdateListener);
            this.mBackInvokedFlingAnim.removeEndListener(this.mOnAnimationEndListener);
        }
        this.mBackInvokedFinishRunnable = null;
    }

    public boolean isBackAnimationInProgress() {
        return this.mBackAnimationInProgress;
    }

    public float getVelocity() {
        return this.mVelocity / 100.0f;
    }

    private void updateProgressValue(float f, float f2, long j) {
        BackEvent backEvent;
        this.mVelocity = f2;
        if (this.mLastBackEvent == null || this.mCallback == null || !this.mBackAnimationInProgress) {
            return;
        }
        if (Flags.predictiveBackTimestampApi()) {
            backEvent = new BackEvent(this.mLastBackEvent.getTouchX(), this.mLastBackEvent.getTouchY(), f / 100.0f, this.mLastBackEvent.getSwipeEdge(), j);
        } else {
            backEvent = new BackEvent(this.mLastBackEvent.getTouchX(), this.mLastBackEvent.getTouchY(), f / 100.0f, this.mLastBackEvent.getSwipeEdge());
        }
        this.mCallback.onProgressUpdate(backEvent);
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
