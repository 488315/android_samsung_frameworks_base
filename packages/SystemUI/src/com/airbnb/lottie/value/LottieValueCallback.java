package com.airbnb.lottie.value;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LottieValueCallback {
    public final LottieFrameInfo frameInfo;
    public final Object value;

    public LottieValueCallback() {
        this.frameInfo = new LottieFrameInfo();
        this.value = null;
    }

    public Object getValue(LottieFrameInfo lottieFrameInfo) {
        return this.value;
    }

    public final Object getValueInternal(float f, float f2, Object obj, Object obj2, float f3, float f4, float f5) {
        LottieFrameInfo lottieFrameInfo = this.frameInfo;
        lottieFrameInfo.startFrame = f;
        lottieFrameInfo.endFrame = f2;
        lottieFrameInfo.startValue = obj;
        lottieFrameInfo.endValue = obj2;
        lottieFrameInfo.linearKeyframeProgress = f3;
        lottieFrameInfo.interpolatedKeyframeProgress = f4;
        lottieFrameInfo.overallProgress = f5;
        return getValue(lottieFrameInfo);
    }

    public LottieValueCallback(Object obj) {
        this.frameInfo = new LottieFrameInfo();
        this.value = obj;
    }
}
