package com.airbnb.lottie.animation.keyframe;

import android.view.animation.Interpolator;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BaseKeyframeAnimation {
    public final KeyframesWrapper keyframesWrapper;
    public LottieValueCallback valueCallback;
    public final List listeners = new ArrayList(1);
    public boolean isDiscrete = false;
    public float progress = 0.0f;
    public Object cachedGetValue = null;
    public float cachedStartDelayProgress = -1.0f;
    public float cachedEndProgress = -1.0f;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface AnimationListener {
        void onValueChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EmptyKeyframeWrapper implements KeyframesWrapper {
        private EmptyKeyframeWrapper() {
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final Keyframe getCurrentKeyframe() {
            throw new IllegalStateException("not implemented");
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getEndProgress() {
            return 1.0f;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getStartDelayProgress() {
            return 0.0f;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isCachedValueEnabled(float f) {
            throw new IllegalStateException("not implemented");
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isEmpty() {
            return true;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isValueChanged(float f) {
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface KeyframesWrapper {
        Keyframe getCurrentKeyframe();

        float getEndProgress();

        float getStartDelayProgress();

        boolean isCachedValueEnabled(float f);

        boolean isEmpty();

        boolean isValueChanged(float f);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyframesWrapperImpl implements KeyframesWrapper {
        public Keyframe cachedCurrentKeyframe = null;
        public float cachedInterpolatedProgress = -1.0f;
        public Keyframe currentKeyframe = findKeyframe(0.0f);
        public final List keyframes;

        public KeyframesWrapperImpl(List<? extends Keyframe> list) {
            this.keyframes = list;
        }

        public final Keyframe findKeyframe(float f) {
            Keyframe keyframe = (Keyframe) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, this.keyframes);
            if (f >= keyframe.getStartProgress()) {
                return keyframe;
            }
            for (int size = this.keyframes.size() - 2; size >= 1; size--) {
                Keyframe keyframe2 = (Keyframe) this.keyframes.get(size);
                if (this.currentKeyframe != keyframe2 && f >= keyframe2.getStartProgress() && f < keyframe2.getEndProgress()) {
                    return keyframe2;
                }
            }
            return (Keyframe) this.keyframes.get(0);
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final Keyframe getCurrentKeyframe() {
            return this.currentKeyframe;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getEndProgress() {
            return ((Keyframe) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, this.keyframes)).getEndProgress();
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getStartDelayProgress() {
            return ((Keyframe) this.keyframes.get(0)).getStartProgress();
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isCachedValueEnabled(float f) {
            Keyframe keyframe = this.cachedCurrentKeyframe;
            Keyframe keyframe2 = this.currentKeyframe;
            if (keyframe == keyframe2 && this.cachedInterpolatedProgress == f) {
                return true;
            }
            this.cachedCurrentKeyframe = keyframe2;
            this.cachedInterpolatedProgress = f;
            return false;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isEmpty() {
            return false;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isValueChanged(float f) {
            Keyframe keyframe = this.currentKeyframe;
            if (f >= keyframe.getStartProgress() && f < keyframe.getEndProgress()) {
                return !this.currentKeyframe.isStatic();
            }
            this.currentKeyframe = findKeyframe(f);
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SingleKeyframeWrapper implements KeyframesWrapper {
        public float cachedInterpolatedProgress = -1.0f;
        public final Keyframe keyframe;

        public SingleKeyframeWrapper(List<? extends Keyframe> list) {
            this.keyframe = list.get(0);
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final Keyframe getCurrentKeyframe() {
            return this.keyframe;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getEndProgress() {
            return this.keyframe.getEndProgress();
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getStartDelayProgress() {
            return this.keyframe.getStartProgress();
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isCachedValueEnabled(float f) {
            if (this.cachedInterpolatedProgress == f) {
                return true;
            }
            this.cachedInterpolatedProgress = f;
            return false;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isEmpty() {
            return false;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final boolean isValueChanged(float f) {
            return !this.keyframe.isStatic();
        }
    }

    public BaseKeyframeAnimation(List<? extends Keyframe> list) {
        KeyframesWrapper singleKeyframeWrapper;
        if (list.isEmpty()) {
            singleKeyframeWrapper = new EmptyKeyframeWrapper();
        } else {
            singleKeyframeWrapper = list.size() == 1 ? new SingleKeyframeWrapper(list) : new KeyframesWrapperImpl(list);
        }
        this.keyframesWrapper = singleKeyframeWrapper;
    }

    public final void addUpdateListener(AnimationListener animationListener) {
        ((ArrayList) this.listeners).add(animationListener);
    }

    public float getEndProgress() {
        if (this.cachedEndProgress == -1.0f) {
            this.cachedEndProgress = this.keyframesWrapper.getEndProgress();
        }
        return this.cachedEndProgress;
    }

    public final float getInterpolatedCurrentKeyframeProgress() {
        Keyframe currentKeyframe = this.keyframesWrapper.getCurrentKeyframe();
        if (currentKeyframe == null || currentKeyframe.isStatic()) {
            return 0.0f;
        }
        return currentKeyframe.interpolator.getInterpolation(getLinearCurrentKeyframeProgress());
    }

    public final float getLinearCurrentKeyframeProgress() {
        if (this.isDiscrete) {
            return 0.0f;
        }
        Keyframe currentKeyframe = this.keyframesWrapper.getCurrentKeyframe();
        if (currentKeyframe.isStatic()) {
            return 0.0f;
        }
        return (this.progress - currentKeyframe.getStartProgress()) / (currentKeyframe.getEndProgress() - currentKeyframe.getStartProgress());
    }

    public Object getValue() {
        float linearCurrentKeyframeProgress = getLinearCurrentKeyframeProgress();
        LottieValueCallback lottieValueCallback = this.valueCallback;
        KeyframesWrapper keyframesWrapper = this.keyframesWrapper;
        if (lottieValueCallback == null && keyframesWrapper.isCachedValueEnabled(linearCurrentKeyframeProgress)) {
            return this.cachedGetValue;
        }
        Keyframe currentKeyframe = keyframesWrapper.getCurrentKeyframe();
        Interpolator interpolator = currentKeyframe.xInterpolator;
        Object value = (interpolator == null || currentKeyframe.yInterpolator == null) ? getValue(currentKeyframe, getInterpolatedCurrentKeyframeProgress()) : getValue(currentKeyframe, linearCurrentKeyframeProgress, interpolator.getInterpolation(linearCurrentKeyframeProgress), currentKeyframe.yInterpolator.getInterpolation(linearCurrentKeyframeProgress));
        this.cachedGetValue = value;
        return value;
    }

    public abstract Object getValue(Keyframe keyframe, float f);

    public void notifyListeners() {
        for (int i = 0; i < ((ArrayList) this.listeners).size(); i++) {
            ((AnimationListener) ((ArrayList) this.listeners).get(i)).onValueChanged();
        }
    }

    public void setProgress(float f) {
        KeyframesWrapper keyframesWrapper = this.keyframesWrapper;
        if (keyframesWrapper.isEmpty()) {
            return;
        }
        if (this.cachedStartDelayProgress == -1.0f) {
            this.cachedStartDelayProgress = keyframesWrapper.getStartDelayProgress();
        }
        float f2 = this.cachedStartDelayProgress;
        if (f < f2) {
            if (f2 == -1.0f) {
                this.cachedStartDelayProgress = keyframesWrapper.getStartDelayProgress();
            }
            f = this.cachedStartDelayProgress;
        } else if (f > getEndProgress()) {
            f = getEndProgress();
        }
        if (f == this.progress) {
            return;
        }
        this.progress = f;
        if (keyframesWrapper.isValueChanged(f)) {
            notifyListeners();
        }
    }

    public final void setValueCallback(LottieValueCallback lottieValueCallback) {
        LottieValueCallback lottieValueCallback2 = this.valueCallback;
        if (lottieValueCallback2 != null) {
            lottieValueCallback2.getClass();
        }
        this.valueCallback = lottieValueCallback;
    }

    public Object getValue(Keyframe keyframe, float f, float f2, float f3) {
        throw new UnsupportedOperationException("This animation does not support split dimensions!");
    }
}
