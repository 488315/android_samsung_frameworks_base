package android.graphics.drawable;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.animation.RenderNodeAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class RippleAnimationSession {
    private static final int ENTER_ANIM_DURATION = 450;
    private static final int EXIT_ANIM_DURATION = 375;
    private static final long MAX_NOISE_PHASE = 32;
    private static final long NOISE_ANIMATION_DURATION = 7000;
    private static final String TAG = "RippleAnimationSession";
    private AnimationProperties<CanvasProperty<Float>, CanvasProperty<Paint>> mCanvasProperties;
    private Animator mCurrentAnimation;
    private boolean mForceSoftware;
    private Animator mLoopAnimation;
    private Consumer<RippleAnimationSession> mOnSessionEnd;
    private Runnable mOnUpdate;
    private final AnimationProperties<Float, Paint> mProperties;
    private long mStartTime;
    private static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);

    RippleAnimationSession(AnimationProperties<Float, Paint> animationProperties, boolean z) {
        this.mProperties = animationProperties;
        this.mForceSoftware = z;
    }

    boolean isForceSoftware() {
        return this.mForceSoftware;
    }

    RippleAnimationSession enter(Canvas canvas) {
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        if (useRTAnimations(canvas)) {
            enterHardware((RecordingCanvas) canvas);
            return this;
        }
        enterSoftware();
        return this;
    }

    void end() {
        Animator animator = this.mCurrentAnimation;
        if (animator != null) {
            animator.end();
        }
    }

    RippleAnimationSession exit(Canvas canvas) {
        if (useRTAnimations(canvas)) {
            exitHardware((RecordingCanvas) canvas);
            return this;
        }
        exitSoftware();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationEnd(Animator animator) {
        notifyUpdate();
    }

    RippleAnimationSession setOnSessionEnd(Consumer<RippleAnimationSession> consumer) {
        this.mOnSessionEnd = consumer;
        return this;
    }

    RippleAnimationSession setOnAnimationUpdated(Runnable runnable) {
        this.mOnUpdate = runnable;
        return this;
    }

    private boolean useRTAnimations(Canvas canvas) {
        if (this.mForceSoftware || !canvas.isHardwareAccelerated()) {
            return false;
        }
        RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
        return recordingCanvas.mNode != null && recordingCanvas.mNode.isAttached();
    }

    private void exitSoftware() {
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.5f, 1.0f);
        valueAnimatorOfFloat.setDuration(375L);
        valueAnimatorOfFloat.setStartDelay(computeDelay());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$exitSoftware$0(valueAnimatorOfFloat, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.1
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (RippleAnimationSession.this.mLoopAnimation != null) {
                    RippleAnimationSession.this.mLoopAnimation.cancel();
                }
                Consumer consumer = RippleAnimationSession.this.mOnSessionEnd;
                if (consumer != null) {
                    consumer.accept(RippleAnimationSession.this);
                }
                if (RippleAnimationSession.this.mCurrentAnimation == valueAnimatorOfFloat) {
                    RippleAnimationSession.this.mCurrentAnimation = null;
                }
            }
        });
        valueAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR);
        valueAnimatorOfFloat.start();
        this.mCurrentAnimation = valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exitSoftware$0(ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private long computeDelay() {
        return Math.max(450 - (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime), 0L);
    }

    private void notifyUpdate() {
        Runnable runnable = this.mOnUpdate;
        if (runnable != null) {
            runnable.run();
        }
    }

    RippleAnimationSession setForceSoftwareAnimation(boolean z) {
        this.mForceSoftware = z;
        return this;
    }

    private void exitHardware(RecordingCanvas recordingCanvas) {
        final RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(getCanvasProperties().getProgress(), 1.0f);
        renderNodeAnimator.setDuration(375L);
        renderNodeAnimator.addListener(new AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.2
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (RippleAnimationSession.this.mLoopAnimation != null) {
                    RippleAnimationSession.this.mLoopAnimation.cancel();
                }
                Consumer consumer = RippleAnimationSession.this.mOnSessionEnd;
                if (consumer != null) {
                    consumer.accept(RippleAnimationSession.this);
                }
                if (RippleAnimationSession.this.mCurrentAnimation == renderNodeAnimator) {
                    RippleAnimationSession.this.mCurrentAnimation = null;
                }
            }
        });
        renderNodeAnimator.setTarget(recordingCanvas);
        renderNodeAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator.setStartDelay(computeDelay());
        renderNodeAnimator.start();
        this.mCurrentAnimation = renderNodeAnimator;
    }

    private void enterHardware(RecordingCanvas recordingCanvas) {
        AnimationProperties<CanvasProperty<Float>, CanvasProperty<Paint>> canvasProperties = getCanvasProperties();
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(canvasProperties.getProgress(), 0.5f);
        renderNodeAnimator.setTarget(recordingCanvas);
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(canvasProperties.getNoisePhase(), this.mStartTime + 32);
        renderNodeAnimator2.setTarget(recordingCanvas);
        startAnimation(renderNodeAnimator, renderNodeAnimator2);
        this.mCurrentAnimation = renderNodeAnimator;
    }

    private void startAnimation(Animator animator, Animator animator2) {
        animator.setDuration(450L);
        animator.addListener(new AnimatorListener(this));
        animator.setInterpolator(FAST_OUT_SLOW_IN);
        animator.start();
        animator2.setDuration(NOISE_ANIMATION_DURATION);
        animator2.addListener(new AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.3
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator3) {
                super.onAnimationEnd(animator3);
                RippleAnimationSession.this.mLoopAnimation = null;
            }
        });
        animator2.setInterpolator(LINEAR_INTERPOLATOR);
        animator2.start();
        Animator animator3 = this.mLoopAnimation;
        if (animator3 != null) {
            animator3.cancel();
        }
        this.mLoopAnimation = animator2;
    }

    private void enterSoftware() {
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 0.5f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$enterSoftware$1(valueAnimatorOfFloat, valueAnimator);
            }
        });
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.mStartTime, r2 + 32);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$enterSoftware$2(valueAnimatorOfFloat2, valueAnimator);
            }
        });
        startAnimation(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        this.mCurrentAnimation = valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterSoftware$1(ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterSoftware$2(ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setNoisePhase(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    void setRadius(float f) {
        this.mProperties.setRadius(Float.valueOf(f));
        this.mProperties.getShader().setRadius(f);
        AnimationProperties<CanvasProperty<Float>, CanvasProperty<Paint>> animationProperties = this.mCanvasProperties;
        if (animationProperties != null) {
            animationProperties.setRadius(CanvasProperty.createFloat(f));
            this.mCanvasProperties.getShader().setRadius(f);
        }
    }

    AnimationProperties<Float, Paint> getProperties() {
        return this.mProperties;
    }

    AnimationProperties<CanvasProperty<Float>, CanvasProperty<Paint>> getCanvasProperties() {
        if (this.mCanvasProperties == null) {
            this.mCanvasProperties = new AnimationProperties<>(CanvasProperty.createFloat(this.mProperties.getX().floatValue()), CanvasProperty.createFloat(this.mProperties.getY().floatValue()), CanvasProperty.createFloat(this.mProperties.getMaxRadius().floatValue()), CanvasProperty.createFloat(this.mProperties.getNoisePhase().floatValue()), CanvasProperty.createPaint(this.mProperties.getPaint()), CanvasProperty.createFloat(this.mProperties.getProgress().floatValue()), this.mProperties.getColor(), this.mProperties.getShader());
        }
        return this.mCanvasProperties;
    }

    private static class AnimatorListener implements Animator.AnimatorListener {
        private final RippleAnimationSession mSession;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        AnimatorListener(RippleAnimationSession rippleAnimationSession) {
            this.mSession = rippleAnimationSession;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mSession.onAnimationEnd(animator);
        }
    }

    static class AnimationProperties<FloatType, PaintType> {
        private final int mColor;
        private FloatType mMaxRadius;
        private final FloatType mNoisePhase;
        private final PaintType mPaint;
        private final FloatType mProgress;
        private final RippleShader mShader;
        private FloatType mX;
        private FloatType mY;

        AnimationProperties(FloatType floattype, FloatType floattype2, FloatType floattype3, FloatType floattype4, PaintType painttype, FloatType floattype5, int i, RippleShader rippleShader) {
            this.mY = floattype2;
            this.mX = floattype;
            this.mMaxRadius = floattype3;
            this.mNoisePhase = floattype4;
            this.mPaint = painttype;
            this.mShader = rippleShader;
            this.mProgress = floattype5;
            this.mColor = i;
        }

        FloatType getProgress() {
            return this.mProgress;
        }

        void setRadius(FloatType floattype) {
            this.mMaxRadius = floattype;
        }

        void setOrigin(FloatType floattype, FloatType floattype2) {
            this.mX = floattype;
            this.mY = floattype2;
        }

        FloatType getX() {
            return this.mX;
        }

        FloatType getY() {
            return this.mY;
        }

        FloatType getMaxRadius() {
            return this.mMaxRadius;
        }

        PaintType getPaint() {
            return this.mPaint;
        }

        RippleShader getShader() {
            return this.mShader;
        }

        FloatType getNoisePhase() {
            return this.mNoisePhase;
        }

        int getColor() {
            return this.mColor;
        }
    }
}
