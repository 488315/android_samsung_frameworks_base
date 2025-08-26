package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.collection.SimpleArrayMap;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import java.util.ArrayList;
import java.util.WeakHashMap;

@Deprecated
/* loaded from: classes4.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    public float dependencyOriginalTranslationX;
    public float dependencyOriginalTranslationY;
    public final int[] tmpArray;
    public final Rect tmpRect;
    public final RectF tmpRectF1;
    public final RectF tmpRectF2;

    public class FabTransformationSpec {
        public Positioning positioning;
        public MotionSpec timings;
    }

    public FabTransformationBehavior() {
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    public static Pair calculateMotionTiming(float f, float f2, boolean z, FabTransformationSpec fabTransformationSpec) {
        MotionTiming timing;
        MotionTiming timing2;
        if (f == 0.0f || f2 == 0.0f) {
            timing = fabTransformationSpec.timings.getTiming("translationXLinear");
            timing2 = fabTransformationSpec.timings.getTiming("translationYLinear");
        } else if ((!z || f2 >= 0.0f) && (z || f2 <= 0.0f)) {
            timing = fabTransformationSpec.timings.getTiming("translationXCurveDownwards");
            timing2 = fabTransformationSpec.timings.getTiming("translationYCurveDownwards");
        } else {
            timing = fabTransformationSpec.timings.getTiming("translationXCurveUpwards");
            timing2 = fabTransformationSpec.timings.getTiming("translationYCurveUpwards");
        }
        return new Pair(timing, timing2);
    }

    public static float calculateValueOfAnimationAtEndOfExpansion(FabTransformationSpec fabTransformationSpec, MotionTiming motionTiming, float f) {
        long j = motionTiming.delay;
        MotionTiming timing = fabTransformationSpec.timings.getTiming("expansion");
        return AnimationUtils.lerp(f, 0.0f, motionTiming.getInterpolator().getInterpolation((((timing.delay + timing.duration) + 17) - j) / motionTiming.duration));
    }

    public final float calculateTranslationX(View view, View view2, Positioning positioning) {
        float fCenterX;
        float fCenterX2;
        float f;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        int i = positioning.gravity & 7;
        if (i == 1) {
            fCenterX = rectF2.centerX();
            fCenterX2 = rectF.centerX();
        } else if (i == 3) {
            fCenterX = rectF2.left;
            fCenterX2 = rectF.left;
        } else {
            if (i != 5) {
                f = 0.0f;
                return f + positioning.xAdjustment;
            }
            fCenterX = rectF2.right;
            fCenterX2 = rectF.right;
        }
        f = fCenterX - fCenterX2;
        return f + positioning.xAdjustment;
    }

    public final float calculateTranslationY(View view, View view2, Positioning positioning) {
        float fCenterY;
        float fCenterY2;
        float f;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        int i = positioning.gravity & 112;
        if (i == 16) {
            fCenterY = rectF2.centerY();
            fCenterY2 = rectF.centerY();
        } else if (i == 48) {
            fCenterY = rectF2.top;
            fCenterY2 = rectF.top;
        } else {
            if (i != 80) {
                f = 0.0f;
                return f + positioning.yAdjustment;
            }
            fCenterY = rectF2.bottom;
            fCenterY2 = rectF.bottom;
        }
        f = fCenterY - fCenterY2;
        return f + positioning.yAdjustment;
    }

    public final void calculateWindowBounds(View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        view.getLocationInWindow(this.tmpArray);
        rectF.offsetTo(r3[0], r3[1]);
        rectF.offset((int) (-view.getTranslationX()), (int) (-view.getTranslationY()));
    }

    @Override // com.google.android.material.transformation.ExpandableBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean layoutDependsOn(View view, View view2) {
        if (view.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        }
        if (!(view2 instanceof FloatingActionButton)) {
            return false;
        }
        int i = ((FloatingActionButton) view2).expandableWidgetHelper.expandedComponentIdHint;
        return i == 0 || i == view.getId();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:99:0x034a  */
    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AnimatorSet onCreateExpandedStateChangeAnimation(final View view, final View view2, final boolean z, boolean z2) {
        ObjectAnimator objectAnimatorOfFloat;
        int i;
        float f;
        ObjectAnimator objectAnimatorOfFloat2;
        ObjectAnimator objectAnimatorOfFloat3;
        boolean z3;
        Animator animator;
        Animator animator2;
        int i2;
        ObjectAnimator objectAnimatorOfFloat4;
        ObjectAnimator objectAnimatorOfInt;
        ObjectAnimator objectAnimatorOfInt2;
        FabTransformationSpec fabTransformationSpecOnCreateMotionSpec = onCreateMotionSpec(view2.getContext(), z);
        if (z) {
            this.dependencyOriginalTranslationX = view.getTranslationX();
            this.dependencyOriginalTranslationY = view.getTranslationY();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        float elevation = ViewCompat.Api21Impl.getElevation(view2) - ViewCompat.Api21Impl.getElevation(view);
        if (z) {
            if (!z2) {
                view2.setTranslationZ(-elevation);
            }
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Z, 0.0f);
        } else {
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Z, -elevation);
        }
        fabTransformationSpecOnCreateMotionSpec.timings.getTiming("elevation").apply(objectAnimatorOfFloat);
        arrayList.add(objectAnimatorOfFloat);
        RectF rectF = this.tmpRectF1;
        float fCalculateTranslationX = calculateTranslationX(view, view2, fabTransformationSpecOnCreateMotionSpec.positioning);
        float fCalculateTranslationY = calculateTranslationY(view, view2, fabTransformationSpecOnCreateMotionSpec.positioning);
        Pair pairCalculateMotionTiming = calculateMotionTiming(fCalculateTranslationX, fCalculateTranslationY, z, fabTransformationSpecOnCreateMotionSpec);
        MotionTiming motionTiming = (MotionTiming) pairCalculateMotionTiming.first;
        MotionTiming motionTiming2 = (MotionTiming) pairCalculateMotionTiming.second;
        if (z) {
            if (!z2) {
                view2.setTranslationX(-fCalculateTranslationX);
                view2.setTranslationY(-fCalculateTranslationY);
            }
            i = 0;
            objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
            f = 0.0f;
            objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Y, 0.0f);
            float fCalculateValueOfAnimationAtEndOfExpansion = calculateValueOfAnimationAtEndOfExpansion(fabTransformationSpecOnCreateMotionSpec, motionTiming, -fCalculateTranslationX);
            float fCalculateValueOfAnimationAtEndOfExpansion2 = calculateValueOfAnimationAtEndOfExpansion(fabTransformationSpecOnCreateMotionSpec, motionTiming2, -fCalculateTranslationY);
            Rect rect = this.tmpRect;
            view2.getWindowVisibleDisplayFrame(rect);
            RectF rectF2 = this.tmpRectF1;
            rectF2.set(rect);
            RectF rectF3 = this.tmpRectF2;
            calculateWindowBounds(view2, rectF3);
            rectF3.offset(fCalculateValueOfAnimationAtEndOfExpansion, fCalculateValueOfAnimationAtEndOfExpansion2);
            rectF3.intersect(rectF2);
            rectF.set(rectF3);
        } else {
            i = 0;
            f = 0.0f;
            objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, -fCalculateTranslationX);
            objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Y, -fCalculateTranslationY);
        }
        motionTiming.apply(objectAnimatorOfFloat2);
        motionTiming2.apply(objectAnimatorOfFloat3);
        arrayList.add(objectAnimatorOfFloat2);
        arrayList.add(objectAnimatorOfFloat3);
        float fWidth = rectF.width();
        float fHeight = rectF.height();
        float fCalculateTranslationX2 = calculateTranslationX(view, view2, fabTransformationSpecOnCreateMotionSpec.positioning);
        float fCalculateTranslationY2 = calculateTranslationY(view, view2, fabTransformationSpecOnCreateMotionSpec.positioning);
        Pair pairCalculateMotionTiming2 = calculateMotionTiming(fCalculateTranslationX2, fCalculateTranslationY2, z, fabTransformationSpecOnCreateMotionSpec);
        MotionTiming motionTiming3 = (MotionTiming) pairCalculateMotionTiming2.first;
        MotionTiming motionTiming4 = (MotionTiming) pairCalculateMotionTiming2.second;
        Property property = View.TRANSLATION_X;
        if (!z) {
            fCalculateTranslationX2 = this.dependencyOriginalTranslationX;
        }
        float[] fArr = new float[1];
        fArr[i] = fCalculateTranslationX2;
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr);
        Property property2 = View.TRANSLATION_Y;
        if (!z) {
            fCalculateTranslationY2 = this.dependencyOriginalTranslationY;
        }
        float[] fArr2 = new float[1];
        fArr2[i] = fCalculateTranslationY2;
        ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, fArr2);
        motionTiming3.apply(objectAnimatorOfFloat5);
        motionTiming4.apply(objectAnimatorOfFloat6);
        arrayList.add(objectAnimatorOfFloat5);
        arrayList.add(objectAnimatorOfFloat6);
        boolean z4 = view2 instanceof CircularRevealWidget;
        if (z4 && (view instanceof ImageView)) {
            final CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view2;
            final Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null) {
                drawable.mutate();
                if (z) {
                    if (!z2) {
                        drawable.setAlpha(255);
                    }
                    objectAnimatorOfInt2 = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, i);
                } else {
                    objectAnimatorOfInt2 = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 255);
                }
                objectAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view2.invalidate();
                    }
                });
                fabTransformationSpecOnCreateMotionSpec.timings.getTiming("iconFade").apply(objectAnimatorOfInt2);
                arrayList.add(objectAnimatorOfInt2);
                arrayList2.add(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator3) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(null);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator3) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(drawable);
                    }
                });
            }
        }
        if (z4) {
            final CircularRevealWidget circularRevealWidget2 = (CircularRevealWidget) view2;
            Positioning positioning = fabTransformationSpecOnCreateMotionSpec.positioning;
            RectF rectF4 = this.tmpRectF1;
            RectF rectF5 = this.tmpRectF2;
            calculateWindowBounds(view, rectF4);
            rectF4.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
            calculateWindowBounds(view2, rectF5);
            rectF5.offset(-calculateTranslationX(view, view2, positioning), f);
            float fCenterX = rectF4.centerX() - rectF5.left;
            Positioning positioning2 = fabTransformationSpecOnCreateMotionSpec.positioning;
            RectF rectF6 = this.tmpRectF1;
            RectF rectF7 = this.tmpRectF2;
            calculateWindowBounds(view, rectF6);
            z3 = z4;
            rectF6.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
            calculateWindowBounds(view2, rectF7);
            rectF7.offset(0.0f, -calculateTranslationY(view, view2, positioning2));
            float fCenterY = rectF6.centerY() - rectF7.top;
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            Rect rect2 = this.tmpRect;
            if (floatingActionButton.isLaidOut()) {
                int i3 = i;
                rect2.set(i3, i3, floatingActionButton.getWidth(), floatingActionButton.getHeight());
                floatingActionButton.offsetRectWithShadow(rect2);
            }
            float fWidth2 = this.tmpRect.width() / 2.0f;
            MotionTiming timing = fabTransformationSpecOnCreateMotionSpec.timings.getTiming("expansion");
            if (z) {
                if (!z2) {
                    circularRevealWidget2.setRevealInfo(new CircularRevealWidget.RevealInfo(fCenterX, fCenterY, fWidth2));
                }
                if (z2) {
                    fWidth2 = circularRevealWidget2.getRevealInfo().radius;
                }
                Animator animatorCreateCircularReveal = CircularRevealCompat.createCircularReveal(circularRevealWidget2, fCenterX, fCenterY, MathUtils.distanceToFurthestCorner(fCenterX, fCenterY, fWidth, fHeight));
                animatorCreateCircularReveal.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator3) {
                        CircularRevealWidget.RevealInfo revealInfo = circularRevealWidget2.getRevealInfo();
                        revealInfo.radius = Float.MAX_VALUE;
                        circularRevealWidget2.setRevealInfo(revealInfo);
                    }
                });
                long j = timing.delay;
                int i4 = (int) fCenterX;
                int i5 = (int) fCenterY;
                if (j > 0) {
                    Animator animatorCreateCircularReveal2 = ViewAnimationUtils.createCircularReveal(view2, i4, i5, fWidth2, fWidth2);
                    animator2 = animatorCreateCircularReveal;
                    animatorCreateCircularReveal2.setStartDelay(0L);
                    animatorCreateCircularReveal2.setDuration(j);
                    arrayList.add(animatorCreateCircularReveal2);
                } else {
                    animator2 = animatorCreateCircularReveal;
                }
                animator = animator2;
            } else {
                float f2 = circularRevealWidget2.getRevealInfo().radius;
                Animator animatorCreateCircularReveal3 = CircularRevealCompat.createCircularReveal(circularRevealWidget2, fCenterX, fCenterY, fWidth2);
                long j2 = timing.delay;
                int i6 = (int) fCenterX;
                int i7 = (int) fCenterY;
                long jMax = 0;
                if (j2 > 0) {
                    Animator animatorCreateCircularReveal4 = ViewAnimationUtils.createCircularReveal(view2, i6, i7, f2, f2);
                    animatorCreateCircularReveal4.setStartDelay(0L);
                    animatorCreateCircularReveal4.setDuration(j2);
                    arrayList.add(animatorCreateCircularReveal4);
                }
                SimpleArrayMap simpleArrayMap = fabTransformationSpecOnCreateMotionSpec.timings.timings;
                int i8 = simpleArrayMap.size;
                int i9 = 0;
                while (i9 < i8) {
                    SimpleArrayMap simpleArrayMap2 = simpleArrayMap;
                    MotionTiming motionTiming5 = (MotionTiming) simpleArrayMap.valueAt(i9);
                    jMax = Math.max(jMax, motionTiming5.delay + motionTiming5.duration);
                    i9++;
                    i8 = i8;
                    simpleArrayMap = simpleArrayMap2;
                    animatorCreateCircularReveal3 = animatorCreateCircularReveal3;
                }
                Animator animator3 = animatorCreateCircularReveal3;
                long j3 = timing.delay + timing.duration;
                if (j3 < jMax) {
                    Animator animatorCreateCircularReveal5 = ViewAnimationUtils.createCircularReveal(view2, i6, i7, fWidth2, fWidth2);
                    animatorCreateCircularReveal5.setStartDelay(j3);
                    animatorCreateCircularReveal5.setDuration(jMax - j3);
                    arrayList.add(animatorCreateCircularReveal5);
                }
                animator = animator3;
            }
            timing.apply(animator);
            arrayList.add(animator);
            arrayList2.add(new AnimatorListenerAdapter() { // from class: com.google.android.material.circularreveal.CircularRevealCompat.1
                public AnonymousClass1() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator4) {
                    circularRevealWidget.destroyCircularRevealCache();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator4) {
                    circularRevealWidget.buildCircularRevealCache();
                }
            });
        } else {
            z3 = z4;
        }
        if (z3) {
            CircularRevealWidget circularRevealWidget3 = (CircularRevealWidget) view2;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ColorStateList backgroundTintList = ViewCompat.Api21Impl.getBackgroundTintList(view);
            int colorForState = backgroundTintList != null ? backgroundTintList.getColorForState(view.getDrawableState(), backgroundTintList.getDefaultColor()) : 0;
            int i10 = 16777215 & colorForState;
            if (z) {
                if (!z2) {
                    circularRevealWidget3.setCircularRevealScrimColor(colorForState);
                }
                objectAnimatorOfInt = ObjectAnimator.ofInt(circularRevealWidget3, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, i10);
            } else {
                objectAnimatorOfInt = ObjectAnimator.ofInt(circularRevealWidget3, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, colorForState);
            }
            objectAnimatorOfInt.setEvaluator(ArgbEvaluatorCompat.instance);
            fabTransformationSpecOnCreateMotionSpec.timings.getTiming("color").apply(objectAnimatorOfInt);
            arrayList.add(objectAnimatorOfInt);
        }
        boolean z5 = view2 instanceof ViewGroup;
        if (z5) {
            View viewFindViewById = view2.findViewById(R.id.mtrl_child_content_container);
            ViewGroup viewGroup = null;
            if (viewFindViewById != null) {
                if (viewFindViewById instanceof ViewGroup) {
                    viewGroup = (ViewGroup) viewFindViewById;
                }
            } else if ((view2 instanceof TransformationChildLayout) || (view2 instanceof TransformationChildCard)) {
                View childAt = ((ViewGroup) view2).getChildAt(0);
                if (childAt instanceof ViewGroup) {
                    viewGroup = (ViewGroup) childAt;
                }
            } else if (z5) {
                viewGroup = (ViewGroup) view2;
            }
            if (viewGroup != null) {
                if (z) {
                    if (!z2) {
                        ChildrenAlphaProperty.CHILDREN_ALPHA.set(viewGroup, Float.valueOf(0.0f));
                    }
                    i2 = 0;
                    objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewGroup, ChildrenAlphaProperty.CHILDREN_ALPHA, 1.0f);
                } else {
                    i2 = 0;
                    objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewGroup, ChildrenAlphaProperty.CHILDREN_ALPHA, 0.0f);
                }
                fabTransformationSpecOnCreateMotionSpec.timings.getTiming("contentFade").apply(objectAnimatorOfFloat4);
                arrayList.add(objectAnimatorOfFloat4);
            }
        } else {
            i2 = 0;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator4) {
                if (z) {
                    return;
                }
                view2.setVisibility(4);
                view.setAlpha(1.0f);
                view.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator4) {
                if (z) {
                    view2.setVisibility(0);
                    view.setAlpha(0.0f);
                    view.setVisibility(4);
                }
            }
        });
        int size = arrayList2.size();
        for (int i11 = i2; i11 < size; i11++) {
            animatorSet.addListener((Animator.AnimatorListener) arrayList2.get(i11));
        }
        return animatorSet;
    }

    public abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }
}
