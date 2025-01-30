package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ValueCallbackKeyframeAnimation extends BaseKeyframeAnimation {
    public final Object valueCallbackValue;

    public ValueCallbackKeyframeAnimation(LottieValueCallback lottieValueCallback) {
        this(lottieValueCallback, null);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final float getEndProgress() {
        return 1.0f;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue() {
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = this.valueCallbackValue;
        float f = this.progress;
        return lottieValueCallback.getValueInternal(0.0f, 0.0f, obj, obj, f, f, f);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final void notifyListeners() {
        if (this.valueCallback != null) {
            super.notifyListeners();
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final void setProgress(float f) {
        this.progress = f;
    }

    public ValueCallbackKeyframeAnimation(LottieValueCallback lottieValueCallback, Object obj) {
        super(Collections.emptyList());
        setValueCallback(lottieValueCallback);
        this.valueCallbackValue = obj;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return getValue();
    }
}
