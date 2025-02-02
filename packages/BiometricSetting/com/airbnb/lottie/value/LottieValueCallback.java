package com.airbnb.lottie.value;

import com.airbnb.lottie.SimpleColorFilter;

/* loaded from: classes.dex */
public class LottieValueCallback<T> {
    private final LottieFrameInfo<T> frameInfo;
    protected T value;

    public LottieValueCallback() {
        this.frameInfo = new LottieFrameInfo<>();
        this.value = null;
    }

    public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
        return this.value;
    }

    public final T getValueInternal(float f, float f2, T t, T t2, float f3, float f4, float f5) {
        LottieFrameInfo<T> lottieFrameInfo = this.frameInfo;
        lottieFrameInfo.set(t, t2);
        return getValue(lottieFrameInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LottieValueCallback(SimpleColorFilter simpleColorFilter) {
        this.frameInfo = new LottieFrameInfo<>();
        this.value = simpleColorFilter;
    }
}
