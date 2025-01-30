package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ViewOscillator extends KeyCycleOscillator {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AlphaSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setAlpha(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomSet extends ViewOscillator {
        public ConstraintAttribute mCustom;
        public final float[] value = new float[1];

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public final void setCustom(ConstraintAttribute constraintAttribute) {
            this.mCustom = constraintAttribute;
        }

        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            float f2 = get(f);
            float[] fArr = this.value;
            fArr[0] = f2;
            CustomSupport.setInterpolatedValue(this.mCustom, view, fArr);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ElevationSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setElevation(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProgressSet extends ViewOscillator {
        public boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RotationSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setRotation(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RotationXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setRotationX(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RotationYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setRotationY(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScaleXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setScaleX(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScaleYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setScaleY(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TranslationXset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setTranslationX(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TranslationYset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setTranslationY(get(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TranslationZset extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
            view.setTranslationZ(get(f));
        }
    }

    public abstract void setProperty(View view, float f);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PathRotateSet extends ViewOscillator {
        @Override // androidx.constraintlayout.motion.utils.ViewOscillator
        public final void setProperty(View view, float f) {
        }
    }
}
