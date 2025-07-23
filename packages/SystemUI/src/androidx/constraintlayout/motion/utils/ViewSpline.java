package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewSpline extends SplineSet {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlphaSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setAlpha(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomSet extends ViewSpline {
        public final SparseArray mConstraintAttributeList;
        public float[] mTempValues;

        public CustomSet(String str, SparseArray<ConstraintAttribute> sparseArray) {
            String str2 = str.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public final void setPoint(float f, int i) {
            throw new RuntimeException("call of custom attribute setPoint");
        }

        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            this.mCurveFit.getPos(f, this.mTempValues);
            CustomSupport.setInterpolatedValue((ConstraintAttribute) this.mConstraintAttributeList.valueAt(0), view, this.mTempValues);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public final void setup(int i) {
            int size = this.mConstraintAttributeList.size();
            int numberOfInterpolatedValues = ((ConstraintAttribute) this.mConstraintAttributeList.valueAt(0)).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.mTempValues = new float[numberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, numberOfInterpolatedValues);
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = this.mConstraintAttributeList.keyAt(i2);
                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.mConstraintAttributeList.valueAt(i2);
                dArr[i2] = keyAt * 0.01d;
                constraintAttribute.getValuesToInterpolate(this.mTempValues);
                int i3 = 0;
                while (true) {
                    if (i3 < this.mTempValues.length) {
                        dArr2[i2][i3] = r6[i3];
                        i3++;
                    }
                }
            }
            this.mCurveFit = CurveFit.get(i, dArr, dArr2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ElevationSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setElevation(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PivotXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setPivotX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PivotYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setPivotY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ProgressSet extends ViewSpline {
        public boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            Method method;
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f));
                return;
            }
            if (this.mNoMethod) {
                return;
            }
            try {
                method = view.getClass().getMethod("setProgress", Float.TYPE);
            } catch (NoSuchMethodException unused) {
                this.mNoMethod = true;
                method = null;
            }
            if (method != null) {
                try {
                    method.invoke(view, Float.valueOf(get(f)));
                } catch (IllegalAccessException e) {
                    Log.e("ViewSpline", "unable to setProgress", e);
                } catch (InvocationTargetException e2) {
                    Log.e("ViewSpline", "unable to setProgress", e2);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setRotation(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setRotationX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setRotationY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setScaleX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setScaleY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setTranslationX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setTranslationY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationZset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
            view.setTranslationZ(get(f));
        }
    }

    public abstract void setProperty(float f, View view);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PathRotate extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public final void setProperty(float f, View view) {
        }
    }
}
