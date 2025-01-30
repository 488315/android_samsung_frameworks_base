package androidx.core.animation;

import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IntKeyframeSet extends KeyframeSet implements Keyframes.IntKeyframes {
    public IntKeyframeSet(Keyframe.IntKeyframe... intKeyframeArr) {
        super(intKeyframeArr);
    }

    public final int getIntValue(float f) {
        if (f <= 0.0f) {
            Keyframe.IntKeyframe intKeyframe = (Keyframe.IntKeyframe) this.mKeyframes.get(0);
            Keyframe.IntKeyframe intKeyframe2 = (Keyframe.IntKeyframe) this.mKeyframes.get(1);
            int i = intKeyframe.mValue;
            int i2 = intKeyframe2.mValue;
            float f2 = intKeyframe.mFraction;
            float f3 = intKeyframe2.mFraction;
            Interpolator interpolator = intKeyframe2.mInterpolator;
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f4 = (f - f2) / (f3 - f2);
            TypeEvaluator typeEvaluator = this.mEvaluator;
            return typeEvaluator == null ? i + ((int) (f4 * (i2 - i))) : ((Integer) typeEvaluator.evaluate(f4, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
        }
        if (f >= 1.0f) {
            Keyframe.IntKeyframe intKeyframe3 = (Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.IntKeyframe intKeyframe4 = (Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            int i3 = intKeyframe3.mValue;
            int i4 = intKeyframe4.mValue;
            float f5 = intKeyframe3.mFraction;
            float f6 = intKeyframe4.mFraction;
            Interpolator interpolator2 = intKeyframe4.mInterpolator;
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f7 = (f - f5) / (f6 - f5);
            TypeEvaluator typeEvaluator2 = this.mEvaluator;
            return typeEvaluator2 == null ? i3 + ((int) (f7 * (i4 - i3))) : ((Integer) typeEvaluator2.evaluate(f7, Integer.valueOf(i3), Integer.valueOf(i4))).intValue();
        }
        Keyframe.IntKeyframe intKeyframe5 = (Keyframe.IntKeyframe) this.mKeyframes.get(0);
        int i5 = 1;
        while (true) {
            int i6 = this.mNumKeyframes;
            if (i5 >= i6) {
                return ((Integer) ((Keyframe) this.mKeyframes.get(i6 - 1)).getValue()).intValue();
            }
            Keyframe.IntKeyframe intKeyframe6 = (Keyframe.IntKeyframe) this.mKeyframes.get(i5);
            float f8 = intKeyframe6.mFraction;
            if (f < f8) {
                Interpolator interpolator3 = intKeyframe6.mInterpolator;
                float f9 = intKeyframe5.mFraction;
                float f10 = (f - f9) / (f8 - f9);
                int i7 = intKeyframe5.mValue;
                int i8 = intKeyframe6.mValue;
                if (interpolator3 != null) {
                    f10 = interpolator3.getInterpolation(f10);
                }
                TypeEvaluator typeEvaluator3 = this.mEvaluator;
                return typeEvaluator3 == null ? Math.round(f10 * (i8 - i7)) + i7 : ((Integer) typeEvaluator3.evaluate(f10, Integer.valueOf(i7), Integer.valueOf(i8))).intValue();
            }
            i5++;
            intKeyframe5 = intKeyframe6;
        }
    }

    @Override // androidx.core.animation.KeyframeSet, androidx.core.animation.Keyframes
    public final Object getValue(float f) {
        return Integer.valueOf(getIntValue(f));
    }

    @Override // androidx.core.animation.KeyframeSet
    /* renamed from: clone */
    public final IntKeyframeSet mo307clone() {
        List list = this.mKeyframes;
        int size = list.size();
        Keyframe.IntKeyframe[] intKeyframeArr = new Keyframe.IntKeyframe[size];
        for (int i = 0; i < size; i++) {
            intKeyframeArr[i] = (Keyframe.IntKeyframe) ((Keyframe) list.get(i)).mo308clone();
        }
        return new IntKeyframeSet(intKeyframeArr);
    }
}
