package androidx.core.animation;

import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.util.List;

/* loaded from: classes.dex */
public class IntKeyframeSet extends KeyframeSet implements Keyframes.IntKeyframes {
    public IntKeyframeSet(Keyframe.IntKeyframe... intKeyframeArr) {
        super(intKeyframeArr);
    }

    public final int getIntValue(float f) {
        int i;
        int i2;
        float f2;
        if (f <= 0.0f) {
            Keyframe.IntKeyframe intKeyframe = (Keyframe.IntKeyframe) this.mKeyframes.get(0);
            Keyframe.IntKeyframe intKeyframe2 = (Keyframe.IntKeyframe) this.mKeyframes.get(1);
            i = intKeyframe.mValue;
            i2 = intKeyframe2.mValue;
            float f3 = intKeyframe.mFraction;
            f2 = (f - f3) / (intKeyframe2.mFraction - f3);
            TypeEvaluator typeEvaluator = this.mEvaluator;
            if (typeEvaluator != null) {
                return ((Integer) typeEvaluator.evaluate(f2, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
            }
        } else if (f >= 1.0f) {
            Keyframe.IntKeyframe intKeyframe3 = (Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.IntKeyframe intKeyframe4 = (Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            i = intKeyframe3.mValue;
            i2 = intKeyframe4.mValue;
            float f4 = intKeyframe3.mFraction;
            f2 = (f - f4) / (intKeyframe4.mFraction - f4);
            TypeEvaluator typeEvaluator2 = this.mEvaluator;
            if (typeEvaluator2 != null) {
                return ((Integer) typeEvaluator2.evaluate(f2, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
            }
        } else {
            Keyframe.IntKeyframe intKeyframe5 = (Keyframe.IntKeyframe) this.mKeyframes.get(0);
            int i3 = 1;
            while (true) {
                int i4 = this.mNumKeyframes;
                if (i3 >= i4) {
                    return ((Integer) ((Keyframe) this.mKeyframes.get(i4 - 1)).getValue()).intValue();
                }
                Keyframe.IntKeyframe intKeyframe6 = (Keyframe.IntKeyframe) this.mKeyframes.get(i3);
                float f5 = intKeyframe6.mFraction;
                if (f < f5) {
                    float f6 = intKeyframe5.mFraction;
                    float f7 = (f - f6) / (f5 - f6);
                    int i5 = intKeyframe5.mValue;
                    int i6 = intKeyframe6.mValue;
                    TypeEvaluator typeEvaluator3 = this.mEvaluator;
                    return typeEvaluator3 == null ? Math.round(f7 * (i6 - i5)) + i5 : ((Integer) typeEvaluator3.evaluate(f7, Integer.valueOf(i5), Integer.valueOf(i6))).intValue();
                }
                i3++;
                intKeyframe5 = intKeyframe6;
            }
        }
        return i + ((int) (f2 * (i2 - i)));
    }

    @Override // androidx.core.animation.KeyframeSet, androidx.core.animation.Keyframes
    public final Object getValue(float f) {
        return Integer.valueOf(getIntValue(f));
    }

    @Override // androidx.core.animation.KeyframeSet
    /* renamed from: clone */
    public final IntKeyframeSet mo894clone() {
        List list = this.mKeyframes;
        int size = list.size();
        Keyframe.IntKeyframe[] intKeyframeArr = new Keyframe.IntKeyframe[size];
        for (int i = 0; i < size; i++) {
            intKeyframeArr[i] = (Keyframe.IntKeyframe) ((Keyframe) list.get(i)).mo895clone();
        }
        return new IntKeyframeSet(intKeyframeArr);
    }
}
