package androidx.core.animation;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FloatKeyframeSet extends KeyframeSet implements Keyframes.FloatKeyframes {
    public FloatKeyframeSet(Keyframe.FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    public final float getFloatValue(float f) {
        if (f <= 0.0f) {
            Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            Keyframe.FloatKeyframe floatKeyframe2 = (Keyframe.FloatKeyframe) this.mKeyframes.get(1);
            float f2 = floatKeyframe.mValue;
            float f3 = floatKeyframe2.mValue;
            float f4 = floatKeyframe.mFraction;
            float f5 = (f - f4) / (floatKeyframe2.mFraction - f4);
            TypeEvaluator typeEvaluator = this.mEvaluator;
            return typeEvaluator == null ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f2, f5, f2) : ((Float) typeEvaluator.evaluate(f5, Float.valueOf(f2), Float.valueOf(f3))).floatValue();
        }
        if (f >= 1.0f) {
            Keyframe.FloatKeyframe floatKeyframe3 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.FloatKeyframe floatKeyframe4 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            float f6 = floatKeyframe3.mValue;
            float f7 = floatKeyframe4.mValue;
            float f8 = floatKeyframe3.mFraction;
            float f9 = (f - f8) / (floatKeyframe4.mFraction - f8);
            TypeEvaluator typeEvaluator2 = this.mEvaluator;
            return typeEvaluator2 == null ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f7, f6, f9, f6) : ((Float) typeEvaluator2.evaluate(f9, Float.valueOf(f6), Float.valueOf(f7))).floatValue();
        }
        Keyframe.FloatKeyframe floatKeyframe5 = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
        int i = 1;
        while (true) {
            int i2 = this.mNumKeyframes;
            if (i >= i2) {
                return ((Float) ((Keyframe) this.mKeyframes.get(i2 - 1)).getValue()).floatValue();
            }
            Keyframe.FloatKeyframe floatKeyframe6 = (Keyframe.FloatKeyframe) this.mKeyframes.get(i);
            float f10 = floatKeyframe6.mFraction;
            if (f < f10) {
                float f11 = floatKeyframe5.mFraction;
                float f12 = (f - f11) / (f10 - f11);
                float f13 = floatKeyframe5.mValue;
                float f14 = floatKeyframe6.mValue;
                TypeEvaluator typeEvaluator3 = this.mEvaluator;
                return typeEvaluator3 == null ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f14, f13, f12, f13) : ((Float) typeEvaluator3.evaluate(f12, Float.valueOf(f13), Float.valueOf(f14))).floatValue();
            }
            i++;
            floatKeyframe5 = floatKeyframe6;
        }
    }

    @Override // androidx.core.animation.KeyframeSet, androidx.core.animation.Keyframes
    public final Object getValue(float f) {
        return Float.valueOf(getFloatValue(f));
    }

    @Override // androidx.core.animation.KeyframeSet
    /* renamed from: clone */
    public final FloatKeyframeSet mo892clone() {
        List list = this.mKeyframes;
        int size = list.size();
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (Keyframe.FloatKeyframe) ((Keyframe) list.get(i)).mo893clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }
}
