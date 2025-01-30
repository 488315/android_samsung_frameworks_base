package com.airbnb.lottie.value;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.value = null;
        this.value = obj;
    }
}
