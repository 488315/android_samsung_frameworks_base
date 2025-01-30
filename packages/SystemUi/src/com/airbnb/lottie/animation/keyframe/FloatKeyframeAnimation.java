package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FloatKeyframeAnimation extends KeyframeAnimation {
    public FloatKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    public final float getFloatValue(Keyframe keyframe, float f) {
        Float f2;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = keyframe.startValue;
        if (lottieValueCallback != null && (f2 = (Float) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Float) obj, (Float) keyframe.endValue, f, getLinearCurrentKeyframeProgress(), this.progress)) != null) {
            return f2.floatValue();
        }
        if (keyframe.startValueFloat == -3987645.8f) {
            keyframe.startValueFloat = ((Float) obj).floatValue();
        }
        float f3 = keyframe.startValueFloat;
        if (keyframe.endValueFloat == -3987645.8f) {
            keyframe.endValueFloat = ((Float) keyframe.endValue).floatValue();
        }
        float f4 = keyframe.endValueFloat;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        return DependencyGraph$$ExternalSyntheticOutline0.m20m(f4, f3, f, f3);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Float.valueOf(getFloatValue(keyframe, f));
    }

    public final float getFloatValue() {
        return getFloatValue(getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }
}
