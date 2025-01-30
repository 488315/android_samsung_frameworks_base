package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Property;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.util.Preconditions;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FloatingActionButtonImpl {
    public BorderDrawable borderDrawable;
    public Drawable contentBackground;
    public Animator currentAnimator;
    public float elevation;
    public boolean ensureMinTouchTargetSize;
    public ArrayList hideListeners;
    public MotionSpec hideMotionSpec;
    public float hoveredFocusedTranslationZ;
    public int maxImageSize;
    public int minTouchTargetSize;
    public ViewTreeObserverOnPreDrawListenerC42936 preDrawListener;
    public float pressedTranslationZ;
    public Drawable rippleDrawable;
    public float rotation;
    public final ShadowViewDelegate shadowViewDelegate;
    public ShapeAppearanceModel shapeAppearance;
    public MaterialShapeDrawable shapeDrawable;
    public ArrayList showListeners;
    public MotionSpec showMotionSpec;
    public final StateListAnimator stateListAnimator;
    public ArrayList transformationCallbacks;
    public final FloatingActionButton view;
    public static final FastOutLinearInInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    public static final int SHOW_ANIM_DURATION_ATTR = R.attr.motionDurationLong2;
    public static final int SHOW_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;
    public static final int HIDE_ANIM_DURATION_ATTR = R.attr.motionDurationMedium1;
    public static final int HIDE_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedAccelerateInterpolator;
    public static final int[] PRESSED_ENABLED_STATE_SET = {android.R.attr.state_pressed, android.R.attr.state_enabled};
    public static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET = {android.R.attr.state_hovered, android.R.attr.state_focused, android.R.attr.state_enabled};
    public static final int[] FOCUSED_ENABLED_STATE_SET = {android.R.attr.state_focused, android.R.attr.state_enabled};
    public static final int[] HOVERED_ENABLED_STATE_SET = {android.R.attr.state_hovered, android.R.attr.state_enabled};
    public static final int[] ENABLED_STATE_SET = {android.R.attr.state_enabled};
    public static final int[] EMPTY_STATE_SET = new int[0];
    public boolean shadowPaddingEnabled = true;
    public float imageMatrixScale = 1.0f;
    public int animState = 0;
    public final Rect tmpRect = new Rect();
    public final RectF tmpRectF1 = new RectF();
    public final RectF tmpRectF2 = new RectF();
    public final Matrix tmpMatrix = new Matrix();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisabledElevationAnimation extends ShadowAnimatorImpl {
        public DisabledElevationAnimation(FloatingActionButtonImpl floatingActionButtonImpl) {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        public final float getTargetShadowSize() {
            return 0.0f;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ElevateToHoveredFocusedTranslationZAnimation extends ShadowAnimatorImpl {
        public ElevateToHoveredFocusedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        public final float getTargetShadowSize() {
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            return floatingActionButtonImpl.elevation + floatingActionButtonImpl.hoveredFocusedTranslationZ;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ElevateToPressedTranslationZAnimation extends ShadowAnimatorImpl {
        public ElevateToPressedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        public final float getTargetShadowSize() {
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            return floatingActionButtonImpl.elevation + floatingActionButtonImpl.pressedTranslationZ;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface InternalVisibilityChangedListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResetElevationAnimation extends ShadowAnimatorImpl {
        public ResetElevationAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        public final float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        public float shadowSizeEnd;
        public float shadowSizeStart;
        public boolean validValues;

        private ShadowAnimatorImpl() {
        }

        public abstract float getTargetShadowSize();

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            float f = (int) this.shadowSizeEnd;
            MaterialShapeDrawable materialShapeDrawable = floatingActionButtonImpl.shapeDrawable;
            if (materialShapeDrawable != null) {
                materialShapeDrawable.setElevation(f);
            }
            this.validValues = false;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.validValues) {
                MaterialShapeDrawable materialShapeDrawable = FloatingActionButtonImpl.this.shapeDrawable;
                this.shadowSizeStart = materialShapeDrawable == null ? 0.0f : materialShapeDrawable.drawableState.elevation;
                this.shadowSizeEnd = getTargetShadowSize();
                this.validValues = true;
            }
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            float f = this.shadowSizeStart;
            float animatedFraction = (int) ((valueAnimator.getAnimatedFraction() * (this.shadowSizeEnd - f)) + f);
            MaterialShapeDrawable materialShapeDrawable2 = floatingActionButtonImpl.shapeDrawable;
            if (materialShapeDrawable2 != null) {
                materialShapeDrawable2.setElevation(animatedFraction);
            }
        }
    }

    public FloatingActionButtonImpl(FloatingActionButton floatingActionButton, ShadowViewDelegate shadowViewDelegate) {
        this.view = floatingActionButton;
        this.shadowViewDelegate = shadowViewDelegate;
        StateListAnimator stateListAnimator = new StateListAnimator();
        this.stateListAnimator = stateListAnimator;
        stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
        stateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(ENABLED_STATE_SET, createElevationAnimator(new ResetElevationAnimation()));
        stateListAnimator.addState(EMPTY_STATE_SET, createElevationAnimator(new DisabledElevationAnimation(this)));
        this.rotation = floatingActionButton.getRotation();
    }

    public static ValueAnimator createElevationAnimator(ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(shadowAnimatorImpl);
        valueAnimator.addUpdateListener(shadowAnimatorImpl);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    public final void calculateImageMatrixFromScale(float f, Matrix matrix) {
        matrix.reset();
        if (this.view.getDrawable() == null || this.maxImageSize == 0) {
            return;
        }
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        rectF.set(0.0f, 0.0f, r0.getIntrinsicWidth(), r0.getIntrinsicHeight());
        int i = this.maxImageSize;
        rectF2.set(0.0f, 0.0f, i, i);
        matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
        int i2 = this.maxImageSize;
        matrix.postScale(f, f, i2 / 2.0f, i2 / 2.0f);
    }

    public final AnimatorSet createAnimator(MotionSpec motionSpec, float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        Property property = View.ALPHA;
        float[] fArr = {f};
        FloatingActionButton floatingActionButton = this.view;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) property, fArr);
        motionSpec.getTiming("opacity").apply(ofFloat);
        arrayList.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.SCALE_X, f2);
        motionSpec.getTiming("scale").apply(ofFloat2);
        arrayList.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.SCALE_Y, f2);
        motionSpec.getTiming("scale").apply(ofFloat3);
        arrayList.add(ofFloat3);
        Matrix matrix = this.tmpMatrix;
        calculateImageMatrixFromScale(f3, matrix);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(floatingActionButton, new ImageMatrixProperty(), new MatrixEvaluator() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.3
            @Override // com.google.android.material.animation.MatrixEvaluator, android.animation.TypeEvaluator
            public final Matrix evaluate(float f4, Matrix matrix2, Matrix matrix3) {
                FloatingActionButtonImpl.this.imageMatrixScale = f4;
                return super.evaluate(f4, matrix2, matrix3);
            }
        }, new Matrix(matrix));
        motionSpec.getTiming("iconScale").apply(ofObject);
        arrayList.add(ofObject);
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }

    public final AnimatorSet createDefaultAnimator(final float f, final float f2, final float f3, int i, int i2) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        FloatingActionButton floatingActionButton = this.view;
        final float alpha = floatingActionButton.getAlpha();
        final float scaleX = floatingActionButton.getScaleX();
        final float scaleY = floatingActionButton.getScaleY();
        final float f4 = this.imageMatrixScale;
        final Matrix matrix = new Matrix(this.tmpMatrix);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                FloatingActionButtonImpl.this.view.setAlpha(AnimationUtils.lerp(alpha, f, 0.0f, 0.2f, floatValue));
                FloatingActionButton floatingActionButton2 = FloatingActionButtonImpl.this.view;
                float f5 = scaleX;
                floatingActionButton2.setScaleX(((f2 - f5) * floatValue) + f5);
                FloatingActionButton floatingActionButton3 = FloatingActionButtonImpl.this.view;
                float f6 = scaleY;
                floatingActionButton3.setScaleY(((f2 - f6) * floatValue) + f6);
                FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
                float f7 = f4;
                float f8 = f3;
                floatingActionButtonImpl.imageMatrixScale = DependencyGraph$$ExternalSyntheticOutline0.m20m(f8, f7, floatValue, f7);
                floatingActionButtonImpl.calculateImageMatrixFromScale(DependencyGraph$$ExternalSyntheticOutline0.m20m(f8, f7, floatValue, f7), matrix);
                FloatingActionButtonImpl.this.view.setImageMatrix(matrix);
            }
        });
        arrayList.add(ofFloat);
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        Context context = floatingActionButton.getContext();
        int integer = floatingActionButton.getContext().getResources().getInteger(R.integer.material_motion_duration_long_1);
        TypedValue resolve = MaterialAttributes.resolve(i, context);
        if (resolve != null && resolve.type == 16) {
            integer = resolve.data;
        }
        animatorSet.setDuration(integer);
        animatorSet.setInterpolator(MotionUtils.resolveThemeInterpolator(floatingActionButton.getContext(), i2, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    public MaterialShapeDrawable createShapeDrawable() {
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearance;
        shapeAppearanceModel.getClass();
        return new MaterialShapeDrawable(shapeAppearanceModel);
    }

    public float getElevation() {
        return this.elevation;
    }

    public void getPadding(Rect rect) {
        int i;
        if (this.ensureMinTouchTargetSize) {
            int i2 = this.minTouchTargetSize;
            FloatingActionButton floatingActionButton = this.view;
            i = (i2 - floatingActionButton.getSizeDimension(floatingActionButton.size)) / 2;
        } else {
            i = 0;
        }
        int max = Math.max(i, (int) Math.ceil(this.shadowPaddingEnabled ? getElevation() + this.pressedTranslationZ : 0.0f));
        int max2 = Math.max(i, (int) Math.ceil(r1 * 1.5f));
        rect.set(max, max2, max, max2);
    }

    public void initializeBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        MaterialShapeDrawable createShapeDrawable = createShapeDrawable();
        this.shapeDrawable = createShapeDrawable;
        createShapeDrawable.setTintList(colorStateList);
        if (mode != null) {
            this.shapeDrawable.setTintMode(mode);
        }
        this.shapeDrawable.setShadowColor();
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        RippleDrawableCompat rippleDrawableCompat = new RippleDrawableCompat(this.shapeDrawable.drawableState.shapeAppearanceModel);
        rippleDrawableCompat.setTintList(RippleUtils.sanitizeRippleDrawableColor(colorStateList2));
        this.rippleDrawable = rippleDrawableCompat;
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        materialShapeDrawable.getClass();
        this.contentBackground = new LayerDrawable(new Drawable[]{materialShapeDrawable, rippleDrawableCompat});
    }

    public void jumpDrawableToCurrentState() {
        StateListAnimator stateListAnimator = this.stateListAnimator;
        ValueAnimator valueAnimator = stateListAnimator.runningAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
            stateListAnimator.runningAnimator = null;
        }
    }

    public void onDrawableStateChanged(int[] iArr) {
        StateListAnimator.Tuple tuple;
        ValueAnimator valueAnimator;
        StateListAnimator stateListAnimator = this.stateListAnimator;
        ArrayList arrayList = stateListAnimator.tuples;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                tuple = null;
                break;
            }
            tuple = (StateListAnimator.Tuple) arrayList.get(i);
            if (StateSet.stateSetMatches(tuple.specs, iArr)) {
                break;
            } else {
                i++;
            }
        }
        StateListAnimator.Tuple tuple2 = stateListAnimator.lastMatch;
        if (tuple == tuple2) {
            return;
        }
        if (tuple2 != null && (valueAnimator = stateListAnimator.runningAnimator) != null) {
            valueAnimator.cancel();
            stateListAnimator.runningAnimator = null;
        }
        stateListAnimator.lastMatch = tuple;
        if (tuple != null) {
            ValueAnimator valueAnimator2 = tuple.animator;
            stateListAnimator.runningAnimator = valueAnimator2;
            valueAnimator2.start();
        }
    }

    public void onElevationsChanged(float f, float f2, float f3) {
        updatePadding();
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(f);
        }
    }

    public final void onScaleChanged() {
        ArrayList arrayList = this.transformationCallbacks;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                FloatingActionButton.TransformationCallbackWrapper transformationCallbackWrapper = (FloatingActionButton.TransformationCallbackWrapper) it.next();
                BottomAppBar.C42222 c42222 = (BottomAppBar.C42222) transformationCallbackWrapper.listener;
                c42222.getClass();
                BottomAppBar bottomAppBar = BottomAppBar.this;
                MaterialShapeDrawable materialShapeDrawable = bottomAppBar.materialShapeDrawable;
                FloatingActionButton floatingActionButton = FloatingActionButton.this;
                materialShapeDrawable.setInterpolation((floatingActionButton.getVisibility() == 0 && bottomAppBar.fabAnchorMode == 1) ? floatingActionButton.getScaleY() : 0.0f);
            }
        }
    }

    public final void onTranslationChanged() {
        ArrayList arrayList = this.transformationCallbacks;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                FloatingActionButton.TransformationCallbackWrapper transformationCallbackWrapper = (FloatingActionButton.TransformationCallbackWrapper) it.next();
                BottomAppBar.C42222 c42222 = (BottomAppBar.C42222) transformationCallbackWrapper.listener;
                c42222.getClass();
                BottomAppBar bottomAppBar = BottomAppBar.this;
                if (bottomAppBar.fabAnchorMode == 1) {
                    FloatingActionButton floatingActionButton = FloatingActionButton.this;
                    float translationX = floatingActionButton.getTranslationX();
                    if (bottomAppBar.getTopEdgeTreatment().horizontalOffset != translationX) {
                        bottomAppBar.getTopEdgeTreatment().horizontalOffset = translationX;
                        bottomAppBar.materialShapeDrawable.invalidateSelf();
                    }
                    float max = Math.max(0.0f, -floatingActionButton.getTranslationY());
                    if (bottomAppBar.getTopEdgeTreatment().cradleVerticalOffset != max) {
                        BottomAppBarTopEdgeTreatment topEdgeTreatment = bottomAppBar.getTopEdgeTreatment();
                        if (max < 0.0f) {
                            topEdgeTreatment.getClass();
                            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
                        }
                        topEdgeTreatment.cradleVerticalOffset = max;
                        bottomAppBar.materialShapeDrawable.invalidateSelf();
                    }
                    bottomAppBar.materialShapeDrawable.setInterpolation(floatingActionButton.getVisibility() == 0 ? floatingActionButton.getScaleY() : 0.0f);
                }
            }
        }
    }

    public final void setShapeAppearance(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearance = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        Object obj = this.rippleDrawable;
        if (obj instanceof Shapeable) {
            ((Shapeable) obj).setShapeAppearanceModel(shapeAppearanceModel);
        }
        BorderDrawable borderDrawable = this.borderDrawable;
        if (borderDrawable != null) {
            borderDrawable.shapeAppearanceModel = shapeAppearanceModel;
            borderDrawable.invalidateSelf();
        }
    }

    public boolean shouldAddPadding() {
        return true;
    }

    public void updateFromViewRotation() {
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShadowCompatRotation((int) this.rotation);
        }
    }

    public final void updatePadding() {
        Rect rect = this.tmpRect;
        getPadding(rect);
        Preconditions.checkNotNull(this.contentBackground, "Didn't initialize content background");
        boolean shouldAddPadding = shouldAddPadding();
        ShadowViewDelegate shadowViewDelegate = this.shadowViewDelegate;
        if (shouldAddPadding) {
            super/*android.widget.ImageButton*/.setBackgroundDrawable(new InsetDrawable(this.contentBackground, rect.left, rect.top, rect.right, rect.bottom));
        } else {
            Drawable drawable = this.contentBackground;
            FloatingActionButton.ShadowDelegateImpl shadowDelegateImpl = (FloatingActionButton.ShadowDelegateImpl) shadowViewDelegate;
            if (drawable != null) {
                super/*android.widget.ImageButton*/.setBackgroundDrawable(drawable);
            } else {
                shadowDelegateImpl.getClass();
            }
        }
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right;
        int i4 = rect.bottom;
        FloatingActionButton floatingActionButton = FloatingActionButton.this;
        floatingActionButton.shadowPadding.set(i, i2, i3, i4);
        int i5 = floatingActionButton.imagePadding;
        floatingActionButton.setPadding(i + i5, i2 + i5, i3 + i5, i4 + i5);
    }
}
