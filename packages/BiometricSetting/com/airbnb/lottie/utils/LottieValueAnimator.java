package com.airbnb.lottie.utils;

import android.view.Choreographer;
import com.airbnb.lottie.C0179L;
import com.airbnb.lottie.LottieComposition;

/* loaded from: classes.dex */
public final class LottieValueAnimator extends BaseLottieAnimator implements Choreographer.FrameCallback {
    private LottieComposition composition;
    private float speed = 1.0f;
    private boolean speedReversedForRepeatMode = false;
    private long lastFrameTimeNs = 0;
    private float frame = 0.0f;
    private int repeatCount = 0;
    private float minFrame = -2.1474836E9f;
    private float maxFrame = 2.1474836E9f;
    protected boolean running = false;

    private boolean isReversed() {
        return this.speed < 0.0f;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public final void cancel() {
        notifyCancel();
        removeFrameCallback(true);
    }

    public final void clearComposition() {
        this.composition = null;
        this.minFrame = -2.1474836E9f;
        this.maxFrame = 2.1474836E9f;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        boolean z = false;
        if (this.running) {
            removeFrameCallback(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null || !this.running) {
            return;
        }
        float frameRate = (this.lastFrameTimeNs != 0 ? j - r2 : 0L) / ((1.0E9f / lottieComposition.getFrameRate()) / Math.abs(this.speed));
        float f = this.frame;
        if (isReversed()) {
            frameRate = -frameRate;
        }
        float f2 = f + frameRate;
        this.frame = f2;
        float minFrame = getMinFrame();
        float maxFrame = getMaxFrame();
        int i = MiscUtils.$r8$clinit;
        if (f2 >= minFrame && f2 <= maxFrame) {
            z = true;
        }
        boolean z2 = !z;
        this.frame = MiscUtils.clamp(this.frame, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = j;
        notifyUpdate();
        if (z2) {
            if (getRepeatCount() == -1 || this.repeatCount < getRepeatCount()) {
                notifyRepeat();
                this.repeatCount++;
                if (getRepeatMode() == 2) {
                    this.speedReversedForRepeatMode = !this.speedReversedForRepeatMode;
                    this.speed = -this.speed;
                } else {
                    this.frame = isReversed() ? getMaxFrame() : getMinFrame();
                }
                this.lastFrameTimeNs = j;
            } else {
                this.frame = this.speed < 0.0f ? getMinFrame() : getMaxFrame();
                removeFrameCallback(true);
                notifyEnd(isReversed());
            }
        }
        if (this.composition != null) {
            float f3 = this.frame;
            if (f3 < this.minFrame || f3 > this.maxFrame) {
                throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", Float.valueOf(this.minFrame), Float.valueOf(this.maxFrame), Float.valueOf(this.frame)));
            }
        }
        C0179L.endSection();
    }

    public final void endAnimation() {
        removeFrameCallback(true);
        notifyEnd(isReversed());
    }

    @Override // android.animation.ValueAnimator
    public final float getAnimatedFraction() {
        float minFrame;
        float maxFrame;
        float minFrame2;
        if (this.composition == null) {
            return 0.0f;
        }
        if (isReversed()) {
            minFrame = getMaxFrame() - this.frame;
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        } else {
            minFrame = this.frame - getMinFrame();
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        }
        return minFrame / (maxFrame - minFrame2);
    }

    @Override // android.animation.ValueAnimator
    public final Object getAnimatedValue() {
        return Float.valueOf(getAnimatedValueAbsolute());
    }

    public final float getAnimatedValueAbsolute() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        return (this.frame - lottieComposition.getStartFrame()) / (this.composition.getEndFrame() - this.composition.getStartFrame());
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public final long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0L;
        }
        return (long) lottieComposition.getDuration();
    }

    public final float getFrame() {
        return this.frame;
    }

    public final float getMaxFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        float f = this.maxFrame;
        return f == 2.1474836E9f ? lottieComposition.getEndFrame() : f;
    }

    public final float getMinFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        float f = this.minFrame;
        return f == -2.1474836E9f ? lottieComposition.getStartFrame() : f;
    }

    public final float getSpeed() {
        return this.speed;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public final boolean isRunning() {
        return this.running;
    }

    public final void pauseAnimation() {
        removeFrameCallback(true);
    }

    public final void playAnimation() {
        this.running = true;
        notifyStart(isReversed());
        setFrame((int) (isReversed() ? getMaxFrame() : getMinFrame()));
        this.lastFrameTimeNs = 0L;
        this.repeatCount = 0;
        if (this.running) {
            removeFrameCallback(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    protected final void removeFrameCallback(boolean z) {
        Choreographer.getInstance().removeFrameCallback(this);
        if (z) {
            this.running = false;
        }
    }

    public final void resumeAnimation() {
        this.running = true;
        removeFrameCallback(false);
        Choreographer.getInstance().postFrameCallback(this);
        this.lastFrameTimeNs = 0L;
        if (isReversed() && this.frame == getMinFrame()) {
            this.frame = getMaxFrame();
        } else {
            if (isReversed() || this.frame != getMaxFrame()) {
                return;
            }
            this.frame = getMinFrame();
        }
    }

    public final void setComposition(LottieComposition lottieComposition) {
        boolean z = this.composition == null;
        this.composition = lottieComposition;
        if (z) {
            setMinAndMaxFrames((int) Math.max(this.minFrame, lottieComposition.getStartFrame()), (int) Math.min(this.maxFrame, lottieComposition.getEndFrame()));
        } else {
            setMinAndMaxFrames((int) lottieComposition.getStartFrame(), (int) lottieComposition.getEndFrame());
        }
        float f = this.frame;
        this.frame = 0.0f;
        setFrame((int) f);
        notifyUpdate();
    }

    public final void setFrame(float f) {
        if (this.frame == f) {
            return;
        }
        this.frame = MiscUtils.clamp(f, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = 0L;
        notifyUpdate();
    }

    public final void setMaxFrame(float f) {
        setMinAndMaxFrames(this.minFrame, f);
    }

    public final void setMinAndMaxFrames(float f, float f2) {
        if (f > f2) {
            throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", Float.valueOf(f), Float.valueOf(f2)));
        }
        LottieComposition lottieComposition = this.composition;
        float startFrame = lottieComposition == null ? -3.4028235E38f : lottieComposition.getStartFrame();
        LottieComposition lottieComposition2 = this.composition;
        float endFrame = lottieComposition2 == null ? Float.MAX_VALUE : lottieComposition2.getEndFrame();
        this.minFrame = MiscUtils.clamp(f, startFrame, endFrame);
        this.maxFrame = MiscUtils.clamp(f2, startFrame, endFrame);
        setFrame((int) MiscUtils.clamp(this.frame, f, f2));
    }

    public final void setMinFrame(int i) {
        setMinAndMaxFrames(i, (int) this.maxFrame);
    }

    @Override // android.animation.ValueAnimator
    public final void setRepeatMode(int i) {
        super.setRepeatMode(i);
        if (i == 2 || !this.speedReversedForRepeatMode) {
            return;
        }
        this.speedReversedForRepeatMode = false;
        this.speed = -this.speed;
    }

    public final void setSpeed(float f) {
        this.speed = f;
    }
}
