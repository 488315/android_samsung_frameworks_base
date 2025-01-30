package androidx.core.animation;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyframeSet implements Keyframes {
    public TypeEvaluator mEvaluator;
    public final Keyframe mFirstKeyframe;
    public final Interpolator mInterpolator;
    public final List mKeyframes;
    public final Keyframe mLastKeyframe;
    public final int mNumKeyframes;

    @SafeVarargs
    public KeyframeSet(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        this.mNumKeyframes = length;
        this.mKeyframes = Arrays.asList(keyframeArr);
        this.mFirstKeyframe = keyframeArr[0];
        Keyframe keyframe = keyframeArr[length - 1];
        this.mLastKeyframe = keyframe;
        this.mInterpolator = keyframe.mInterpolator;
    }

    @Override // androidx.core.animation.Keyframes
    public Object getValue(float f) {
        int i = this.mNumKeyframes;
        if (i == 2) {
            Interpolator interpolator = this.mInterpolator;
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            return this.mEvaluator.evaluate(f, this.mFirstKeyframe.getValue(), this.mLastKeyframe.getValue());
        }
        int i2 = 1;
        if (f <= 0.0f) {
            Keyframe keyframe = (Keyframe) this.mKeyframes.get(1);
            Interpolator interpolator2 = keyframe.mInterpolator;
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            Keyframe keyframe2 = this.mFirstKeyframe;
            float f2 = keyframe2.mFraction;
            return this.mEvaluator.evaluate((f - f2) / (keyframe.mFraction - f2), keyframe2.getValue(), keyframe.getValue());
        }
        if (f >= 1.0f) {
            Keyframe keyframe3 = (Keyframe) this.mKeyframes.get(i - 2);
            Interpolator interpolator3 = this.mLastKeyframe.mInterpolator;
            if (interpolator3 != null) {
                f = interpolator3.getInterpolation(f);
            }
            float f3 = keyframe3.mFraction;
            return this.mEvaluator.evaluate((f - f3) / (this.mLastKeyframe.mFraction - f3), keyframe3.getValue(), this.mLastKeyframe.getValue());
        }
        Keyframe keyframe4 = this.mFirstKeyframe;
        while (i2 < this.mNumKeyframes) {
            Keyframe keyframe5 = (Keyframe) this.mKeyframes.get(i2);
            float f4 = keyframe5.mFraction;
            if (f < f4) {
                Interpolator interpolator4 = keyframe5.mInterpolator;
                float f5 = keyframe4.mFraction;
                float f6 = (f - f5) / (f4 - f5);
                if (interpolator4 != null) {
                    f6 = interpolator4.getInterpolation(f6);
                }
                return this.mEvaluator.evaluate(f6, keyframe4.getValue(), keyframe5.getValue());
            }
            i2++;
            keyframe4 = keyframe5;
        }
        return this.mLastKeyframe.getValue();
    }

    public final String toString() {
        String str = " ";
        for (int i = 0; i < this.mNumKeyframes; i++) {
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
            m18m.append(((Keyframe) this.mKeyframes.get(i)).getValue());
            m18m.append("  ");
            str = m18m.toString();
        }
        return str;
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public KeyframeSet mo307clone() {
        List list = this.mKeyframes;
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(((Keyframe) list.get(i)).mo308clone());
        }
        return new KeyframeSet(arrayList);
    }

    public KeyframeSet(List<Keyframe> list) {
        this.mKeyframes = list;
        int size = list.size();
        this.mNumKeyframes = size;
        this.mFirstKeyframe = list.get(0);
        Keyframe keyframe = list.get(size - 1);
        this.mLastKeyframe = keyframe;
        this.mInterpolator = keyframe.mInterpolator;
    }
}
