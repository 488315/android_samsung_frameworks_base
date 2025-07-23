package android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.animation.RenderNodeAnimator;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import java.util.ArrayList;

/* loaded from: classes.dex */
class RippleForeground extends RippleComponent {
    private static final int OPACITY_ENTER_DURATION = 75;
    private static final int OPACITY_EXIT_DURATION = 150;
    private static final int OPACITY_HOLD_DURATION = 225;
    private static final int RIPPLE_ENTER_DURATION = 225;
    private static final int RIPPLE_ORIGIN_DURATION = 225;
    private final AnimatorListenerAdapter mAnimationListener;
    private float mClampedStartingX;
    private float mClampedStartingY;
    private long mEnterStartedAtMillis;
    private final boolean mForceSoftware;
    private boolean mHasFinishedExit;
    private float mOpacity;
    private ArrayList<RenderNodeAnimator> mPendingHwAnimators;
    private CanvasProperty<Paint> mPropPaint;
    private CanvasProperty<Float> mPropRadius;
    private CanvasProperty<Float> mPropX;
    private CanvasProperty<Float> mPropY;
    private ArrayList<RenderNodeAnimator> mRunningHwAnimators;
    private ArrayList<Animator> mRunningSwAnimators;
    private float mStartRadius;
    private float mStartingX;
    private float mStartingY;
    private float mTargetX;
    private float mTargetY;
    private float mTweenRadius;
    private float mTweenX;
    private float mTweenY;
    private boolean mUsingProperties;
    private static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final TimeInterpolator DECELERATE_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final FloatProperty<RippleForeground> TWEEN_RADIUS = new FloatProperty<RippleForeground>("tweenRadius") { // from class: android.graphics.drawable.RippleForeground.2
        @Override // android.util.FloatProperty
        public void setValue(RippleForeground rippleForeground, float f) {
            rippleForeground.mTweenRadius = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public Float get(RippleForeground rippleForeground) {
            return Float.valueOf(rippleForeground.mTweenRadius);
        }
    };
    private static final FloatProperty<RippleForeground> TWEEN_ORIGIN = new FloatProperty<RippleForeground>("tweenOrigin") { // from class: android.graphics.drawable.RippleForeground.3
        @Override // android.util.FloatProperty
        public void setValue(RippleForeground rippleForeground, float f) {
            rippleForeground.mTweenX = f;
            rippleForeground.mTweenY = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public Float get(RippleForeground rippleForeground) {
            return Float.valueOf(rippleForeground.mTweenX);
        }
    };
    private static final FloatProperty<RippleForeground> OPACITY = new FloatProperty<RippleForeground>("opacity") { // from class: android.graphics.drawable.RippleForeground.4
        @Override // android.util.FloatProperty
        public void setValue(RippleForeground rippleForeground, float f) {
            rippleForeground.mOpacity = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public Float get(RippleForeground rippleForeground) {
            return Float.valueOf(rippleForeground.mOpacity);
        }
    };

    public RippleForeground(RippleDrawable rippleDrawable, Rect rect, float f, float f2, boolean z) {
        super(rippleDrawable, rect);
        this.mTargetX = 0.0f;
        this.mTargetY = 0.0f;
        this.mOpacity = 0.0f;
        this.mTweenRadius = 0.0f;
        this.mTweenX = 0.0f;
        this.mTweenY = 0.0f;
        this.mPendingHwAnimators = new ArrayList<>();
        this.mRunningHwAnimators = new ArrayList<>();
        this.mRunningSwAnimators = new ArrayList<>();
        this.mStartRadius = 0.0f;
        this.mAnimationListener = new AnimatorListenerAdapter() { // from class: android.graphics.drawable.RippleForeground.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                RippleForeground.this.mHasFinishedExit = true;
                RippleForeground.this.pruneHwFinished();
                RippleForeground.this.pruneSwFinished();
                if (RippleForeground.this.mRunningHwAnimators.isEmpty()) {
                    RippleForeground.this.clearHwProps();
                }
            }
        };
        this.mForceSoftware = z;
        this.mStartingX = f;
        this.mStartingY = f2;
        this.mStartRadius = Math.max(rect.width(), rect.height()) * 0.3f;
        clampStartingPosition();
    }

    @Override // android.graphics.drawable.RippleComponent
    protected void onTargetRadiusChanged(float f) {
        clampStartingPosition();
        switchToUiThreadAnimation();
    }

    private void drawSoftware(Canvas canvas, Paint paint) {
        int alpha = paint.getAlpha();
        int i = (int) ((alpha * this.mOpacity) + 0.5f);
        float currentRadius = getCurrentRadius();
        if (i <= 0 || currentRadius <= 0.0f) {
            return;
        }
        float currentX = getCurrentX();
        float currentY = getCurrentY();
        paint.setAlpha(i);
        canvas.drawCircle(currentX, currentY, currentRadius, paint);
        paint.setAlpha(alpha);
    }

    private void startPending(RecordingCanvas recordingCanvas) {
        if (this.mPendingHwAnimators.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mPendingHwAnimators.size(); i++) {
            RenderNodeAnimator renderNodeAnimator = this.mPendingHwAnimators.get(i);
            renderNodeAnimator.setTarget(recordingCanvas);
            renderNodeAnimator.start();
            this.mRunningHwAnimators.add(renderNodeAnimator);
        }
        this.mPendingHwAnimators.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pruneHwFinished() {
        if (this.mRunningHwAnimators.isEmpty()) {
            return;
        }
        for (int size = this.mRunningHwAnimators.size() - 1; size >= 0; size--) {
            if (!this.mRunningHwAnimators.get(size).isRunning()) {
                this.mRunningHwAnimators.remove(size);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pruneSwFinished() {
        if (this.mRunningSwAnimators.isEmpty()) {
            return;
        }
        for (int size = this.mRunningSwAnimators.size() - 1; size >= 0; size--) {
            if (!this.mRunningSwAnimators.get(size).isRunning()) {
                this.mRunningSwAnimators.remove(size);
            }
        }
    }

    private void drawHardware(RecordingCanvas recordingCanvas, Paint paint) {
        startPending(recordingCanvas);
        pruneHwFinished();
        CanvasProperty<Paint> canvasProperty = this.mPropPaint;
        if (canvasProperty != null) {
            this.mUsingProperties = true;
            recordingCanvas.drawCircle(this.mPropX, this.mPropY, this.mPropRadius, canvasProperty);
        } else {
            this.mUsingProperties = false;
            drawSoftware(recordingCanvas, paint);
        }
    }

    @Override // android.graphics.drawable.RippleComponent
    public void getBounds(Rect rect) {
        int i = (int) this.mTargetX;
        int i2 = (int) this.mTargetY;
        int i3 = ((int) this.mTargetRadius) + 1;
        rect.set(i - i3, i2 - i3, i + i3, i2 + i3);
    }

    public void move(float f, float f2) {
        this.mStartingX = f;
        this.mStartingY = f2;
        clampStartingPosition();
    }

    public boolean hasFinishedExit() {
        return this.mHasFinishedExit;
    }

    private long computeFadeOutDelay() {
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mEnterStartedAtMillis;
        if (currentAnimationTimeMillis <= 0 || currentAnimationTimeMillis >= 225) {
            return 0L;
        }
        return 225 - currentAnimationTimeMillis;
    }

    private void startSoftwareEnter() {
        for (int i = 0; i < this.mRunningSwAnimators.size(); i++) {
            this.mRunningSwAnimators.get(i).cancel();
        }
        this.mRunningSwAnimators.clear();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, TWEEN_RADIUS, 1.0f);
        ofFloat.setDuration(225L);
        TimeInterpolator timeInterpolator = DECELERATE_INTERPOLATOR;
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.start();
        this.mRunningSwAnimators.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, TWEEN_ORIGIN, 1.0f);
        ofFloat2.setDuration(225L);
        ofFloat2.setInterpolator(timeInterpolator);
        ofFloat2.start();
        this.mRunningSwAnimators.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this, OPACITY, 1.0f);
        ofFloat3.setDuration(75L);
        ofFloat3.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat3.start();
        this.mRunningSwAnimators.add(ofFloat3);
    }

    private void startSoftwareExit() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, OPACITY, 0.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat.addListener(this.mAnimationListener);
        ofFloat.setStartDelay(computeFadeOutDelay());
        ofFloat.start();
        this.mRunningSwAnimators.add(ofFloat);
    }

    private void startHardwareEnter() {
        if (this.mForceSoftware) {
            return;
        }
        this.mPropX = CanvasProperty.createFloat(getCurrentX());
        this.mPropY = CanvasProperty.createFloat(getCurrentY());
        this.mPropRadius = CanvasProperty.createFloat(getCurrentRadius());
        this.mPropPaint = CanvasProperty.createPaint(this.mOwner.updateRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPropRadius, this.mTargetRadius);
        renderNodeAnimator.setDuration(225L);
        TimeInterpolator timeInterpolator = DECELERATE_INTERPOLATOR;
        renderNodeAnimator.setInterpolator(timeInterpolator);
        this.mPendingHwAnimators.add(renderNodeAnimator);
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(this.mPropX, this.mTargetX);
        renderNodeAnimator2.setDuration(225L);
        renderNodeAnimator2.setInterpolator(timeInterpolator);
        this.mPendingHwAnimators.add(renderNodeAnimator2);
        RenderNodeAnimator renderNodeAnimator3 = new RenderNodeAnimator(this.mPropY, this.mTargetY);
        renderNodeAnimator3.setDuration(225L);
        renderNodeAnimator3.setInterpolator(timeInterpolator);
        this.mPendingHwAnimators.add(renderNodeAnimator3);
        RenderNodeAnimator renderNodeAnimator4 = new RenderNodeAnimator(this.mPropPaint, 1, r0.getAlpha());
        renderNodeAnimator4.setDuration(75L);
        renderNodeAnimator4.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator4.setStartValue(0.0f);
        this.mPendingHwAnimators.add(renderNodeAnimator4);
        invalidateSelf();
    }

    private void startHardwareExit() {
        if (this.mForceSoftware || this.mPropPaint == null) {
            return;
        }
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPropPaint, 1, 0.0f);
        renderNodeAnimator.setDuration(150L);
        renderNodeAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimationListener);
        renderNodeAnimator.setStartDelay(computeFadeOutDelay());
        renderNodeAnimator.setStartValue(this.mOwner.updateRipplePaint().getAlpha());
        this.mPendingHwAnimators.add(renderNodeAnimator);
        invalidateSelf();
    }

    public final void enter() {
        this.mEnterStartedAtMillis = AnimationUtils.currentAnimationTimeMillis();
        startSoftwareEnter();
        startHardwareEnter();
    }

    public final void exit() {
        startSoftwareExit();
        startHardwareExit();
    }

    private float getCurrentX() {
        return MathUtils.lerp(this.mClampedStartingX - this.mBounds.exactCenterX(), this.mTargetX, this.mTweenX);
    }

    private float getCurrentY() {
        return MathUtils.lerp(this.mClampedStartingY - this.mBounds.exactCenterY(), this.mTargetY, this.mTweenY);
    }

    private float getCurrentRadius() {
        return MathUtils.lerp(this.mStartRadius, this.mTargetRadius, this.mTweenRadius);
    }

    public void draw(Canvas canvas, Paint paint) {
        boolean z = !this.mForceSoftware && (canvas instanceof RecordingCanvas);
        pruneSwFinished();
        if (z) {
            drawHardware((RecordingCanvas) canvas, paint);
        } else {
            drawSoftware(canvas, paint);
        }
    }

    private void clampStartingPosition() {
        float exactCenterX = this.mBounds.exactCenterX();
        float exactCenterY = this.mBounds.exactCenterY();
        float f = this.mStartingX - exactCenterX;
        float f2 = this.mStartingY - exactCenterY;
        float f3 = this.mTargetRadius - this.mStartRadius;
        if ((f * f) + (f2 * f2) > f3 * f3) {
            double atan2 = Math.atan2(f2, f);
            double d = f3;
            this.mClampedStartingX = exactCenterX + ((float) (Math.cos(atan2) * d));
            this.mClampedStartingY = exactCenterY + ((float) (Math.sin(atan2) * d));
            return;
        }
        this.mClampedStartingX = this.mStartingX;
        this.mClampedStartingY = this.mStartingY;
    }

    public void end() {
        for (int i = 0; i < this.mRunningSwAnimators.size(); i++) {
            this.mRunningSwAnimators.get(i).end();
        }
        this.mRunningSwAnimators.clear();
        for (int i2 = 0; i2 < this.mRunningHwAnimators.size(); i2++) {
            this.mRunningHwAnimators.get(i2).end();
        }
        this.mRunningHwAnimators.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationPropertyChanged() {
        if (this.mUsingProperties) {
            return;
        }
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearHwProps() {
        this.mPropPaint = null;
        this.mPropRadius = null;
        this.mPropX = null;
        this.mPropY = null;
        this.mUsingProperties = false;
    }

    private void switchToUiThreadAnimation() {
        for (int i = 0; i < this.mRunningHwAnimators.size(); i++) {
            RenderNodeAnimator renderNodeAnimator = this.mRunningHwAnimators.get(i);
            renderNodeAnimator.removeListener(this.mAnimationListener);
            renderNodeAnimator.end();
        }
        this.mRunningHwAnimators.clear();
        clearHwProps();
        invalidateSelf();
    }
}
