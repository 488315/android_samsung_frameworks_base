package android.animation;

import android.animation.Keyframe;
import android.animation.Keyframes;
import java.util.List;

/* loaded from: classes.dex */
class FloatKeyframeSet extends KeyframeSet implements Keyframes.FloatKeyframes {
    public FloatKeyframeSet(Keyframe.FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public Object getValue(float f) {
        return Float.valueOf(getFloatValue(f));
    }

    @Override // android.animation.KeyframeSet
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public FloatKeyframeSet mo83clone() {
        List<Keyframe> list = this.mKeyframes;
        int size = this.mKeyframes.size();
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (Keyframe.FloatKeyframe) list.get(i).mo84clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    @Override // android.animation.Keyframes.FloatKeyframes
    public float getFloatValue(float f) {
        float floatValue;
        float floatValue2;
        float f2;
        if (f <= 0.0f) {
            Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            Keyframe.FloatKeyframe floatKeyframe2 = (Keyframe.FloatKeyframe) this.mKeyframes.get(1);
            floatValue = floatKeyframe.getFloatValue();
            floatValue2 = floatKeyframe2.getFloatValue();
            float fraction = floatKeyframe.getFraction();
            float fraction2 = floatKeyframe2.getFraction();
            TimeInterpolator interpolator = floatKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            f2 = (f - fraction) / (fraction2 - fraction);
            if (this.mEvaluator != null) {
                return ((Number) this.mEvaluator.evaluate(f2, Float.valueOf(floatValue), Float.valueOf(floatValue2))).floatValue();
            }
        } else if (f >= 1.0f) {
            Keyframe.FloatKeyframe floatKeyframe3 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.FloatKeyframe floatKeyframe4 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            floatValue = floatKeyframe3.getFloatValue();
            floatValue2 = floatKeyframe4.getFloatValue();
            float fraction3 = floatKeyframe3.getFraction();
            float fraction4 = floatKeyframe4.getFraction();
            TimeInterpolator interpolator2 = floatKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            f2 = (f - fraction3) / (fraction4 - fraction3);
            if (this.mEvaluator != null) {
                return ((Number) this.mEvaluator.evaluate(f2, Float.valueOf(floatValue), Float.valueOf(floatValue2))).floatValue();
            }
        } else {
            Keyframe.FloatKeyframe floatKeyframe5 = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            int i = 1;
            while (i < this.mNumKeyframes) {
                Keyframe.FloatKeyframe floatKeyframe6 = (Keyframe.FloatKeyframe) this.mKeyframes.get(i);
                if (f < floatKeyframe6.getFraction()) {
                    TimeInterpolator interpolator3 = floatKeyframe6.getInterpolator();
                    float fraction5 = (f - floatKeyframe5.getFraction()) / (floatKeyframe6.getFraction() - floatKeyframe5.getFraction());
                    float floatValue3 = floatKeyframe5.getFloatValue();
                    float floatValue4 = floatKeyframe6.getFloatValue();
                    if (interpolator3 != null) {
                        fraction5 = interpolator3.getInterpolation(fraction5);
                    }
                    return this.mEvaluator == null ? floatValue3 + (fraction5 * (floatValue4 - floatValue3)) : ((Number) this.mEvaluator.evaluate(fraction5, Float.valueOf(floatValue3), Float.valueOf(floatValue4))).floatValue();
                }
                i++;
                floatKeyframe5 = floatKeyframe6;
            }
            return ((Number) this.mKeyframes.get(this.mNumKeyframes - 1).getValue()).floatValue();
        }
        return floatValue + (f2 * (floatValue2 - floatValue));
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public Class getType() {
        return Float.class;
    }
}
