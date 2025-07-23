package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewOscillator extends KeyCycleOscillator {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlphaSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setAlpha(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomSet extends ViewOscillator {
        public ConstraintAttribute mCustom;
        public final float[] mValue = new float[1];

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public final void setCustom(ConstraintAttribute constraintAttribute) {
            this.mCustom = constraintAttribute;
        }

        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            float f2 = get(f);
            float[] fArr = this.mValue;
            fArr[0] = f2;
            CustomSupport.setInterpolatedValue(this.mCustom, view, fArr);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ElevationSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setElevation(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ProgressSet extends ViewOscillator {
        public boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
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
                    Log.e("ViewOscillator", "unable to setProgress", e);
                } catch (InvocationTargetException e2) {
                    Log.e("ViewOscillator", "unable to setProgress", e2);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setRotation(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setRotationX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RotationYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setRotationY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setScaleX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setScaleY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setTranslationX(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setTranslationY(get(f));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TranslationZset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
            view.setTranslationZ(get(f));
        }
    }

    public abstract void setProperty(float f, View view);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PathRotateSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(float f, View view) {
        }
    }
}
