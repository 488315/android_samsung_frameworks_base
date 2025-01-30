package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.transition.MatrixUtils;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ChangeImageTransform extends Transition {
    public static final String[] sTransitionProperties = {"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};
    public static final C05271 NULL_MATRIX_EVALUATOR = new TypeEvaluator() { // from class: androidx.transition.ChangeImageTransform.1
        @Override // android.animation.TypeEvaluator
        public final /* bridge */ /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
            return null;
        }
    };
    public static final C05282 ANIMATED_TRANSFORM_PROPERTY = new C05282(Matrix.class, "animatedTransform");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.transition.ChangeImageTransform$2 */
    public final class C05282 extends Property {
        public C05282(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            return null;
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ((ImageView) obj).animateTransform((Matrix) obj2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.transition.ChangeImageTransform$3 */
    public abstract /* synthetic */ class AbstractC05293 {
        public static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public ChangeImageTransform() {
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureValues(TransitionValues transitionValues) {
        Matrix matrix;
        View view = transitionValues.view;
        if ((view instanceof ImageView) && view.getVisibility() == 0) {
            ImageView imageView = (ImageView) view;
            if (imageView.getDrawable() == null) {
                return;
            }
            HashMap hashMap = (HashMap) transitionValues.values;
            hashMap.put("android:changeImageTransform:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            Drawable drawable = imageView.getDrawable();
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                matrix = new Matrix(imageView.getImageMatrix());
            } else {
                int i = AbstractC05293.$SwitchMap$android$widget$ImageView$ScaleType[imageView.getScaleType().ordinal()];
                if (i == 1) {
                    Drawable drawable2 = imageView.getDrawable();
                    Matrix matrix2 = new Matrix();
                    matrix2.postScale(imageView.getWidth() / drawable2.getIntrinsicWidth(), imageView.getHeight() / drawable2.getIntrinsicHeight());
                    matrix = matrix2;
                } else if (i != 2) {
                    matrix = new Matrix(imageView.getImageMatrix());
                } else {
                    Drawable drawable3 = imageView.getDrawable();
                    int intrinsicWidth = drawable3.getIntrinsicWidth();
                    float width = imageView.getWidth();
                    float f = intrinsicWidth;
                    int intrinsicHeight = drawable3.getIntrinsicHeight();
                    float height = imageView.getHeight();
                    float f2 = intrinsicHeight;
                    float max = Math.max(width / f, height / f2);
                    int round = Math.round((width - (f * max)) / 2.0f);
                    int round2 = Math.round((height - (f2 * max)) / 2.0f);
                    Matrix matrix3 = new Matrix();
                    matrix3.postScale(max, max);
                    matrix3.postTranslate(round, round2);
                    matrix = matrix3;
                }
            }
            hashMap.put("android:changeImageTransform:matrix", matrix);
        }
    }

    @Override // androidx.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        HashMap hashMap = (HashMap) transitionValues.values;
        Rect rect = (Rect) hashMap.get("android:changeImageTransform:bounds");
        HashMap hashMap2 = (HashMap) transitionValues2.values;
        Rect rect2 = (Rect) hashMap2.get("android:changeImageTransform:bounds");
        if (rect == null || rect2 == null) {
            return null;
        }
        Matrix matrix = (Matrix) hashMap.get("android:changeImageTransform:matrix");
        Matrix matrix2 = (Matrix) hashMap2.get("android:changeImageTransform:matrix");
        boolean z = (matrix == null && matrix2 == null) || (matrix != null && matrix.equals(matrix2));
        if (rect.equals(rect2) && z) {
            return null;
        }
        ImageView imageView = (ImageView) transitionValues2.view;
        Drawable drawable = imageView.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            C05282 c05282 = ANIMATED_TRANSFORM_PROPERTY;
            C05271 c05271 = NULL_MATRIX_EVALUATOR;
            MatrixUtils.C05411 c05411 = MatrixUtils.IDENTITY_MATRIX;
            return ObjectAnimator.ofObject(imageView, c05282, c05271, c05411, c05411);
        }
        if (matrix == null) {
            matrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (matrix2 == null) {
            matrix2 = MatrixUtils.IDENTITY_MATRIX;
        }
        C05282 c052822 = ANIMATED_TRANSFORM_PROPERTY;
        c052822.set(imageView, matrix);
        return ObjectAnimator.ofObject(imageView, c052822, new TypeEvaluator() { // from class: androidx.transition.TransitionUtils$MatrixEvaluator
            public final float[] mTempStartValues = new float[9];
            public final float[] mTempEndValues = new float[9];
            public final Matrix mTempMatrix = new Matrix();

            @Override // android.animation.TypeEvaluator
            public final Object evaluate(float f, Object obj, Object obj2) {
                ((Matrix) obj).getValues(this.mTempStartValues);
                ((Matrix) obj2).getValues(this.mTempEndValues);
                for (int i = 0; i < 9; i++) {
                    float[] fArr = this.mTempEndValues;
                    float f2 = fArr[i];
                    float f3 = this.mTempStartValues[i];
                    fArr[i] = DependencyGraph$$ExternalSyntheticOutline0.m20m(f2, f3, f, f3);
                }
                this.mTempMatrix.setValues(this.mTempEndValues);
                return this.mTempMatrix;
            }
        }, matrix, matrix2);
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
