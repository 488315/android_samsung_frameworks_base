package android.animation;

import android.animation.Keyframe;
import android.graphics.Path;
import android.util.Log;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class KeyframeSet implements Keyframes {
    TypeEvaluator mEvaluator;
    Keyframe mFirstKeyframe;
    TimeInterpolator mInterpolator;
    List<Keyframe> mKeyframes;
    Keyframe mLastKeyframe;
    int mNumKeyframes;

    public KeyframeSet(Keyframe... keyframeArr) {
        this.mNumKeyframes = keyframeArr.length;
        this.mKeyframes = Arrays.asList(keyframeArr);
        this.mFirstKeyframe = keyframeArr[0];
        Keyframe keyframe = keyframeArr[this.mNumKeyframes - 1];
        this.mLastKeyframe = keyframe;
        this.mInterpolator = keyframe.getInterpolator();
    }

    @Override // android.animation.Keyframes
    public List<Keyframe> getKeyframes() {
        return this.mKeyframes;
    }

    public static KeyframeSet ofInt(int... iArr) {
        int length = iArr.length;
        Keyframe.IntKeyframe[] intKeyframeArr = new Keyframe.IntKeyframe[Math.max(length, 2)];
        if (length == 1) {
            intKeyframeArr[0] = (Keyframe.IntKeyframe) Keyframe.ofInt(0.0f);
            intKeyframeArr[1] = (Keyframe.IntKeyframe) Keyframe.ofInt(1.0f, iArr[0]);
        } else {
            intKeyframeArr[0] = (Keyframe.IntKeyframe) Keyframe.ofInt(0.0f, iArr[0]);
            for (int i = 1; i < length; i++) {
                intKeyframeArr[i] = (Keyframe.IntKeyframe) Keyframe.ofInt(i / (length - 1), iArr[i]);
            }
        }
        return new IntKeyframeSet(intKeyframeArr);
    }

    public static KeyframeSet ofFloat(float... fArr) {
        int length = fArr.length;
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[Math.max(length, 2)];
        boolean z = false;
        if (length == 1) {
            floatKeyframeArr[0] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(0.0f);
            floatKeyframeArr[1] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(1.0f, fArr[0]);
            if (Float.isNaN(fArr[0])) {
                z = true;
            }
        } else {
            floatKeyframeArr[0] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(0.0f, fArr[0]);
            for (int i = 1; i < length; i++) {
                floatKeyframeArr[i] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(i / (length - 1), fArr[i]);
                if (Float.isNaN(fArr[i])) {
                    z = true;
                }
            }
        }
        if (z) {
            Log.w("Animator", "Bad value (NaN) in float animator");
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    public static KeyframeSet ofKeyframe(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (Keyframe keyframe : keyframeArr) {
            if (keyframe instanceof Keyframe.FloatKeyframe) {
                z = true;
            } else if (keyframe instanceof Keyframe.IntKeyframe) {
                z2 = true;
            } else {
                z3 = true;
            }
        }
        if (z && !z2 && !z3) {
            Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[length];
            while (i < length) {
                floatKeyframeArr[i] = (Keyframe.FloatKeyframe) keyframeArr[i];
                i++;
            }
            return new FloatKeyframeSet(floatKeyframeArr);
        }
        if (z2 && !z && !z3) {
            Keyframe.IntKeyframe[] intKeyframeArr = new Keyframe.IntKeyframe[length];
            while (i < length) {
                intKeyframeArr[i] = (Keyframe.IntKeyframe) keyframeArr[i];
                i++;
            }
            return new IntKeyframeSet(intKeyframeArr);
        }
        return new KeyframeSet(keyframeArr);
    }

    public static KeyframeSet ofObject(Object... objArr) {
        int length = objArr.length;
        Keyframe.ObjectKeyframe[] objectKeyframeArr = new Keyframe.ObjectKeyframe[Math.max(length, 2)];
        if (length == 1) {
            objectKeyframeArr[0] = (Keyframe.ObjectKeyframe) Keyframe.ofObject(0.0f);
            objectKeyframeArr[1] = (Keyframe.ObjectKeyframe) Keyframe.ofObject(1.0f, objArr[0]);
        } else {
            objectKeyframeArr[0] = (Keyframe.ObjectKeyframe) Keyframe.ofObject(0.0f, objArr[0]);
            for (int i = 1; i < length; i++) {
                objectKeyframeArr[i] = (Keyframe.ObjectKeyframe) Keyframe.ofObject(i / (length - 1), objArr[i]);
            }
        }
        return new KeyframeSet(objectKeyframeArr);
    }

    public static PathKeyframes ofPath(Path path) {
        return new PathKeyframes(path);
    }

    public static PathKeyframes ofPath(Path path, float f) {
        return new PathKeyframes(path, f);
    }

    @Override // android.animation.Keyframes
    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
    }

    @Override // android.animation.Keyframes
    public Class getType() {
        return this.mFirstKeyframe.getType();
    }

    @Override // 
    /* renamed from: clone */
    public KeyframeSet mo83clone() {
        List<Keyframe> list = this.mKeyframes;
        int size = list.size();
        Keyframe[] keyframeArr = new Keyframe[size];
        for (int i = 0; i < size; i++) {
            keyframeArr[i] = list.get(i).mo84clone();
        }
        return new KeyframeSet(keyframeArr);
    }

    @Override // android.animation.Keyframes
    public Object getValue(float f) {
        int i = this.mNumKeyframes;
        if (i == 2) {
            TimeInterpolator timeInterpolator = this.mInterpolator;
            if (timeInterpolator != null) {
                f = timeInterpolator.getInterpolation(f);
            }
            return this.mEvaluator.evaluate(f, this.mFirstKeyframe.getValue(), this.mLastKeyframe.getValue());
        }
        int i2 = 1;
        if (f <= 0.0f) {
            Keyframe keyframe = this.mKeyframes.get(1);
            TimeInterpolator interpolator = keyframe.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float fraction = this.mFirstKeyframe.getFraction();
            return this.mEvaluator.evaluate((f - fraction) / (keyframe.getFraction() - fraction), this.mFirstKeyframe.getValue(), keyframe.getValue());
        }
        if (f >= 1.0f) {
            Keyframe keyframe2 = this.mKeyframes.get(i - 2);
            TimeInterpolator interpolator2 = this.mLastKeyframe.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float fraction2 = keyframe2.getFraction();
            return this.mEvaluator.evaluate((f - fraction2) / (this.mLastKeyframe.getFraction() - fraction2), keyframe2.getValue(), this.mLastKeyframe.getValue());
        }
        Keyframe keyframe3 = this.mFirstKeyframe;
        while (i2 < this.mNumKeyframes) {
            Keyframe keyframe4 = this.mKeyframes.get(i2);
            if (f < keyframe4.getFraction()) {
                TimeInterpolator interpolator3 = keyframe4.getInterpolator();
                float fraction3 = keyframe3.getFraction();
                float fraction4 = (f - fraction3) / (keyframe4.getFraction() - fraction3);
                if (interpolator3 != null) {
                    fraction4 = interpolator3.getInterpolation(fraction4);
                }
                return this.mEvaluator.evaluate(fraction4, keyframe3.getValue(), keyframe4.getValue());
            }
            i2++;
            keyframe3 = keyframe4;
        }
        return this.mLastKeyframe.getValue();
    }

    public String toString() {
        String str = " ";
        for (int i = 0; i < this.mNumKeyframes; i++) {
            str = str + this.mKeyframes.get(i).getValue() + "  ";
        }
        return str;
    }
}
