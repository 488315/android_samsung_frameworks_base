package androidx.core.animation;

import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FloatKeyframeSet extends KeyframeSet implements Keyframes.FloatKeyframes {
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
            float f5 = floatKeyframe2.mFraction;
            Interpolator interpolator = floatKeyframe2.mInterpolator;
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f6 = (f - f4) / (f5 - f4);
            TypeEvaluator typeEvaluator = this.mEvaluator;
            return typeEvaluator == null ? DependencyGraph$$ExternalSyntheticOutline0.m20m(f3, f2, f6, f2) : ((Float) typeEvaluator.evaluate(f6, Float.valueOf(f2), Float.valueOf(f3))).floatValue();
        }
        if (f >= 1.0f) {
            Keyframe.FloatKeyframe floatKeyframe3 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.FloatKeyframe floatKeyframe4 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            float f7 = floatKeyframe3.mValue;
            float f8 = floatKeyframe4.mValue;
            float f9 = floatKeyframe3.mFraction;
            float f10 = floatKeyframe4.mFraction;
            Interpolator interpolator2 = floatKeyframe4.mInterpolator;
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f11 = (f - f9) / (f10 - f9);
            TypeEvaluator typeEvaluator2 = this.mEvaluator;
            return typeEvaluator2 == null ? DependencyGraph$$ExternalSyntheticOutline0.m20m(f8, f7, f11, f7) : ((Float) typeEvaluator2.evaluate(f11, Float.valueOf(f7), Float.valueOf(f8))).floatValue();
        }
        Keyframe.FloatKeyframe floatKeyframe5 = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
        int i = 1;
        while (true) {
            int i2 = this.mNumKeyframes;
            if (i >= i2) {
                return ((Float) ((Keyframe) this.mKeyframes.get(i2 - 1)).getValue()).floatValue();
            }
            Keyframe.FloatKeyframe floatKeyframe6 = (Keyframe.FloatKeyframe) this.mKeyframes.get(i);
            float f12 = floatKeyframe6.mFraction;
            if (f < f12) {
                Interpolator interpolator3 = floatKeyframe6.mInterpolator;
                float f13 = floatKeyframe5.mFraction;
                float f14 = (f - f13) / (f12 - f13);
                float f15 = floatKeyframe5.mValue;
                float f16 = floatKeyframe6.mValue;
                if (interpolator3 != null) {
                    f14 = interpolator3.getInterpolation(f14);
                }
                TypeEvaluator typeEvaluator3 = this.mEvaluator;
                return typeEvaluator3 == null ? DependencyGraph$$ExternalSyntheticOutline0.m20m(f16, f15, f14, f15) : ((Float) typeEvaluator3.evaluate(f14, Float.valueOf(f15), Float.valueOf(f16))).floatValue();
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
    public final FloatKeyframeSet mo307clone() {
        List list = this.mKeyframes;
        int size = list.size();
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (Keyframe.FloatKeyframe) ((Keyframe) list.get(i)).mo308clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }
}
