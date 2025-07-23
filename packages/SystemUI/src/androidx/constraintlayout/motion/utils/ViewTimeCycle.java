package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewTimeCycle extends TimeCycleSplineSet {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlphaSet extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setAlpha(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomSet extends ViewTimeCycle {
        public final String mAttributeName;
        public final SparseArray mConstraintAttributeList;
        public float[] mTempValues;
        public final SparseArray mWaveProperties = new SparseArray();

        public CustomSet(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.mAttributeName = str.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public final void setPoint(float f, float f2, float f3, int i, int i2) {
            throw new RuntimeException("Wrong call for custom attribute");
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            this.mCurveFit.getPos(f, this.mTempValues);
            float[] fArr = this.mTempValues;
            float f2 = fArr[fArr.length - 2];
            float f3 = fArr[fArr.length - 1];
            long j2 = j - this.mLastTime;
            if (Float.isNaN(this.mLastCycle)) {
                float floatValue = keyCache.getFloatValue(view, this.mAttributeName);
                this.mLastCycle = floatValue;
                if (Float.isNaN(floatValue)) {
                    this.mLastCycle = 0.0f;
                }
            }
            float f4 = (float) ((((j2 * 1.0E-9d) * f2) + this.mLastCycle) % 1.0d);
            this.mLastCycle = f4;
            this.mLastTime = j;
            float calcWave = calcWave(f4);
            this.mContinue = false;
            int i = 0;
            while (true) {
                float[] fArr2 = this.mCache;
                if (i >= fArr2.length) {
                    break;
                }
                boolean z = this.mContinue;
                float f5 = this.mTempValues[i];
                this.mContinue = z | (((double) f5) != 0.0d);
                fArr2[i] = (f5 * calcWave) + f3;
                i++;
            }
            CustomSupport.setInterpolatedValue((ConstraintAttribute) this.mConstraintAttributeList.valueAt(0), view, this.mCache);
            if (f2 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public final void setup(int i) {
            int size = this.mConstraintAttributeList.size();
            int numberOfInterpolatedValues = ((ConstraintAttribute) this.mConstraintAttributeList.valueAt(0)).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i2 = numberOfInterpolatedValues + 2;
            this.mTempValues = new float[i2];
            this.mCache = new float[numberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i2);
            for (int i3 = 0; i3 < size; i3++) {
                int keyAt = this.mConstraintAttributeList.keyAt(i3);
                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.mConstraintAttributeList.valueAt(i3);
                float[] fArr = (float[]) this.mWaveProperties.valueAt(i3);
                dArr[i3] = keyAt * 0.01d;
                constraintAttribute.getValuesToInterpolate(this.mTempValues);
                int i4 = 0;
                while (true) {
                    if (i4 < this.mTempValues.length) {
                        dArr2[i3][i4] = r8[i4];
                        i4++;
                    }
                }
                double[] dArr3 = dArr2[i3];
                dArr3[numberOfInterpolatedValues] = fArr[0];
                dArr3[numberOfInterpolatedValues + 1] = fArr[1];
            }
            this.mCurveFit = CurveFit.get(i, dArr, dArr2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ElevationSet extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setElevation(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PathRotate extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ProgressSet extends ViewTimeCycle {
        public boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            Method method;
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f, j, view, keyCache));
            } else {
                if (this.mNoMethod) {
                    return false;
                }
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.mNoMethod = true;
                    method = null;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(get(f, j, view, keyCache)));
                    } catch (IllegalAccessException e) {
                        Log.e("ViewTimeCycle", "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e("ViewTimeCycle", "unable to setProgress", e2);
                    }
                }
            }
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationSet extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setRotation(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationXset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setRotationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationYset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setRotationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleXset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setScaleX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleYset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setScaleY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationXset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setTranslationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationYset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setTranslationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationZset extends ViewTimeCycle {
        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public final boolean setProperty(float f, long j, View view, KeyCache keyCache) {
            view.setTranslationZ(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    public final float get(float f, long j, View view, KeyCache keyCache) {
        this.mCurveFit.getPos(f, this.mCache);
        float[] fArr = this.mCache;
        boolean z = true;
        float f2 = fArr[1];
        if (f2 == 0.0f) {
            this.mContinue = false;
            return fArr[2];
        }
        if (Float.isNaN(this.mLastCycle)) {
            float floatValue = keyCache.getFloatValue(view, this.mType);
            this.mLastCycle = floatValue;
            if (Float.isNaN(floatValue)) {
                this.mLastCycle = 0.0f;
            }
        }
        float f3 = (float) (((((j - this.mLastTime) * 1.0E-9d) * f2) + this.mLastCycle) % 1.0d);
        this.mLastCycle = f3;
        String str = this.mType;
        if (keyCache.mMap.containsKey(view)) {
            HashMap hashMap = (HashMap) keyCache.mMap.get(view);
            if (hashMap == null) {
                hashMap = new HashMap();
            }
            if (hashMap.containsKey(str)) {
                float[] fArr2 = (float[]) hashMap.get(str);
                if (fArr2 == null) {
                    fArr2 = new float[0];
                }
                if (fArr2.length <= 0) {
                    fArr2 = Arrays.copyOf(fArr2, 1);
                }
                fArr2[0] = f3;
                hashMap.put(str, fArr2);
            } else {
                hashMap.put(str, new float[]{f3});
                keyCache.mMap.put(view, hashMap);
            }
        } else {
            HashMap hashMap2 = new HashMap();
            hashMap2.put(str, new float[]{f3});
            keyCache.mMap.put(view, hashMap2);
        }
        this.mLastTime = j;
        float f4 = this.mCache[0];
        float calcWave = (calcWave(this.mLastCycle) * f4) + this.mCache[2];
        if (f4 == 0.0f && f2 == 0.0f) {
            z = false;
        }
        this.mContinue = z;
        return calcWave;
    }

    public abstract boolean setProperty(float f, long j, View view, KeyCache keyCache);
}
