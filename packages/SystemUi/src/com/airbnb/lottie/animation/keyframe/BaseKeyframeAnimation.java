package com.airbnb.lottie.animation.keyframe;

import android.view.animation.Interpolator;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimationListener {
        void onValueChanged();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface KeyframesWrapper {
        Keyframe getCurrentKeyframe();

        float getEndProgress();

        float getStartDelayProgress();

        boolean isCachedValueEnabled(float f);

        boolean isEmpty();

        boolean isValueChanged(float f);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyframesWrapperImpl implements KeyframesWrapper {
        public Keyframe cachedCurrentKeyframe = null;
        public float cachedInterpolatedProgress = -1.0f;
        public Keyframe currentKeyframe = findKeyframe(0.0f);
        public final List keyframes;

        public KeyframesWrapperImpl(List<? extends Keyframe> list) {
            this.keyframes = list;
        }

        public final Keyframe findKeyframe(float f) {
            List list = this.keyframes;
            Keyframe keyframe = (Keyframe) list.get(list.size() - 1);
            if (f >= keyframe.getStartProgress()) {
                return keyframe;
            }
            int size = list.size() - 2;
            while (true) {
                boolean z = false;
                if (size < 1) {
                    return (Keyframe) list.get(0);
                }
                Keyframe keyframe2 = (Keyframe) list.get(size);
                if (this.currentKeyframe != keyframe2) {
                    if (f >= keyframe2.getStartProgress() && f < keyframe2.getEndProgress()) {
                        z = true;
                    }
                    if (z) {
                        return keyframe2;
                    }
                }
                size--;
            }
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final Keyframe getCurrentKeyframe() {
            return this.currentKeyframe;
        }

        @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.KeyframesWrapper
        public final float getEndProgress() {
            return ((Keyframe) this.keyframes.get(r1.size() - 1)).getEndProgress();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final Keyframe getCurrentKeyframe() {
        return this.keyframesWrapper.getCurrentKeyframe();
    }

    public float getEndProgress() {
        if (this.cachedEndProgress == -1.0f) {
            this.cachedEndProgress = this.keyframesWrapper.getEndProgress();
        }
        return this.cachedEndProgress;
    }

    public final float getInterpolatedCurrentKeyframeProgress() {
        Keyframe currentKeyframe = getCurrentKeyframe();
        if (currentKeyframe == null || currentKeyframe.isStatic()) {
            return 0.0f;
        }
        return currentKeyframe.interpolator.getInterpolation(getLinearCurrentKeyframeProgress());
    }

    public final float getLinearCurrentKeyframeProgress() {
        if (this.isDiscrete) {
            return 0.0f;
        }
        Keyframe currentKeyframe = getCurrentKeyframe();
        if (currentKeyframe.isStatic()) {
            return 0.0f;
        }
        return (this.progress - currentKeyframe.getStartProgress()) / (currentKeyframe.getEndProgress() - currentKeyframe.getStartProgress());
    }

    public Object getValue() {
        Interpolator interpolator;
        float linearCurrentKeyframeProgress = getLinearCurrentKeyframeProgress();
        if (this.valueCallback == null && this.keyframesWrapper.isCachedValueEnabled(linearCurrentKeyframeProgress)) {
            return this.cachedGetValue;
        }
        Keyframe currentKeyframe = getCurrentKeyframe();
        Interpolator interpolator2 = currentKeyframe.xInterpolator;
        Object value = (interpolator2 == null || (interpolator = currentKeyframe.yInterpolator) == null) ? getValue(currentKeyframe, getInterpolatedCurrentKeyframeProgress()) : getValue(currentKeyframe, linearCurrentKeyframeProgress, interpolator2.getInterpolation(linearCurrentKeyframeProgress), interpolator.getInterpolation(linearCurrentKeyframeProgress));
        this.cachedGetValue = value;
        return value;
    }

    public abstract Object getValue(Keyframe keyframe, float f);

    public void notifyListeners() {
        int i = 0;
        while (true) {
            List list = this.listeners;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            ((AnimationListener) ((ArrayList) list).get(i)).onValueChanged();
            i++;
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
