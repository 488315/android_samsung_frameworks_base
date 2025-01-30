package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
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
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes2.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    public float dependencyOriginalTranslationX;
    public float dependencyOriginalTranslationY;
    public final int[] tmpArray;
    public final Rect tmpRect;
    public final RectF tmpRectF1;
    public final RectF tmpRectF2;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FabTransformationSpec {
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
        float interpolation = motionTiming.getInterpolator().getInterpolation((((timing.delay + timing.duration) + 17) - j) / motionTiming.duration);
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        return DependencyGraph$$ExternalSyntheticOutline0.m20m(0.0f, f, interpolation, f);
    }

    public final float calculateTranslationX(View view, View view2, Positioning positioning) {
        float centerX;
        float centerX2;
        float f;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        int i = positioning.gravity & 7;
        if (i == 1) {
            centerX = rectF2.centerX();
            centerX2 = rectF.centerX();
        } else if (i == 3) {
            centerX = rectF2.left;
            centerX2 = rectF.left;
        } else {
            if (i != 5) {
                f = 0.0f;
                return f + positioning.xAdjustment;
            }
            centerX = rectF2.right;
            centerX2 = rectF.right;
        }
        f = centerX - centerX2;
        return f + positioning.xAdjustment;
    }

    public final float calculateTranslationY(View view, View view2, Positioning positioning) {
        float centerY;
        float centerY2;
        float f;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        int i = positioning.gravity & 112;
        if (i == 16) {
            centerY = rectF2.centerY();
            centerY2 = rectF.centerY();
        } else if (i == 48) {
            centerY = rectF2.top;
            centerY2 = rectF.top;
        } else {
            if (i != 80) {
                f = 0.0f;
                return f + positioning.yAdjustment;
            }
            centerY = rectF2.bottom;
            centerY2 = rectF.bottom;
        }
        f = centerY - centerY2;
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
        int i;
        if (view.getVisibility() != 8) {
            return (view2 instanceof FloatingActionButton) && ((i = ((FloatingActionButton) view2).expandableWidgetHelper.expandedComponentIdHint) == 0 || i == view.getId());
        }
        throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x03b8 A[LOOP:0: B:50:0x03b6->B:51:0x03b8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a9  */
    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AnimatorSet onCreateExpandedStateChangeAnimation(final View view, final View view2, boolean z, boolean z2) {
        ObjectAnimator ofFloat;
        ArrayList arrayList;
        ObjectAnimator ofFloat2;
        ObjectAnimator ofFloat3;
        ArrayList arrayList2;
        boolean z3;
        FabTransformationSpec fabTransformationSpec;
        ArrayList arrayList3;
        Animator animator;
        ArrayList arrayList4;
        final boolean z4;
        ObjectAnimator ofInt;
        FabTransformationSpec fabTransformationSpec2;
        boolean z5;
        int i;
        int size;
        int i2;
        ObjectAnimator ofFloat4;
        ObjectAnimator ofInt2;
        FabTransformationSpec onCreateMotionSpec = onCreateMotionSpec(view2.getContext(), z);
        if (z) {
            this.dependencyOriginalTranslationX = view.getTranslationX();
            this.dependencyOriginalTranslationY = view.getTranslationY();
        }
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        float elevation = ViewCompat.Api21Impl.getElevation(view2) - ViewCompat.Api21Impl.getElevation(view);
        if (z) {
            if (!z2) {
                view2.setTranslationZ(-elevation);
            }
            ofFloat = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Z, 0.0f);
        } else {
            ofFloat = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Z, -elevation);
        }
        onCreateMotionSpec.timings.getTiming("elevation").apply(ofFloat);
        arrayList5.add(ofFloat);
        RectF rectF = this.tmpRectF1;
        float calculateTranslationX = calculateTranslationX(view, view2, onCreateMotionSpec.positioning);
        float calculateTranslationY = calculateTranslationY(view, view2, onCreateMotionSpec.positioning);
        Pair calculateMotionTiming = calculateMotionTiming(calculateTranslationX, calculateTranslationY, z, onCreateMotionSpec);
        MotionTiming motionTiming = (MotionTiming) calculateMotionTiming.first;
        MotionTiming motionTiming2 = (MotionTiming) calculateMotionTiming.second;
        RectF rectF2 = this.tmpRectF2;
        Rect rect = this.tmpRect;
        if (z) {
            if (!z2) {
                view2.setTranslationX(-calculateTranslationX);
                view2.setTranslationY(-calculateTranslationY);
            }
            arrayList = arrayList6;
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
            ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Y, 0.0f);
            float calculateValueOfAnimationAtEndOfExpansion = calculateValueOfAnimationAtEndOfExpansion(onCreateMotionSpec, motionTiming, -calculateTranslationX);
            float calculateValueOfAnimationAtEndOfExpansion2 = calculateValueOfAnimationAtEndOfExpansion(onCreateMotionSpec, motionTiming2, -calculateTranslationY);
            view2.getWindowVisibleDisplayFrame(rect);
            rectF.set(rect);
            calculateWindowBounds(view2, rectF2);
            rectF2.offset(calculateValueOfAnimationAtEndOfExpansion, calculateValueOfAnimationAtEndOfExpansion2);
            rectF2.intersect(rectF);
            rectF.set(rectF2);
            ofFloat3 = ofFloat6;
            ofFloat2 = ofFloat5;
        } else {
            arrayList = arrayList6;
            ofFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, -calculateTranslationX);
            ofFloat3 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_Y, -calculateTranslationY);
        }
        motionTiming.apply(ofFloat2);
        motionTiming2.apply(ofFloat3);
        arrayList5.add(ofFloat2);
        arrayList5.add(ofFloat3);
        float width = rectF.width();
        float height = rectF.height();
        float calculateTranslationX2 = calculateTranslationX(view, view2, onCreateMotionSpec.positioning);
        float calculateTranslationY2 = calculateTranslationY(view, view2, onCreateMotionSpec.positioning);
        Pair calculateMotionTiming2 = calculateMotionTiming(calculateTranslationX2, calculateTranslationY2, z, onCreateMotionSpec);
        MotionTiming motionTiming3 = (MotionTiming) calculateMotionTiming2.first;
        MotionTiming motionTiming4 = (MotionTiming) calculateMotionTiming2.second;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        fArr[0] = z ? calculateTranslationX2 : this.dependencyOriginalTranslationX;
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr);
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[1];
        fArr2[0] = z ? calculateTranslationY2 : this.dependencyOriginalTranslationY;
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, fArr2);
        motionTiming3.apply(ofFloat7);
        motionTiming4.apply(ofFloat8);
        arrayList5.add(ofFloat7);
        arrayList5.add(ofFloat8);
        boolean z6 = view2 instanceof CircularRevealWidget;
        if (z6 && (view instanceof ImageView)) {
            final CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view2;
            final Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null) {
                drawable.mutate();
                if (z) {
                    if (!z2) {
                        drawable.setAlpha(255);
                    }
                    ofInt2 = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 0);
                } else {
                    ofInt2 = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 255);
                }
                ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view2.invalidate();
                    }
                });
                onCreateMotionSpec.timings.getTiming("iconFade").apply(ofInt2);
                arrayList5.add(ofInt2);
                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(null);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(drawable);
                    }
                };
                arrayList2 = arrayList;
                arrayList2.add(animatorListenerAdapter);
                if (z6) {
                    fabTransformationSpec = onCreateMotionSpec;
                    z3 = z6;
                    arrayList4 = arrayList2;
                } else {
                    final CircularRevealWidget circularRevealWidget2 = (CircularRevealWidget) view2;
                    Positioning positioning = onCreateMotionSpec.positioning;
                    calculateWindowBounds(view, rectF);
                    rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
                    calculateWindowBounds(view2, rectF2);
                    rectF2.offset(-calculateTranslationX(view, view2, positioning), 0.0f);
                    float centerX = rectF.centerX() - rectF2.left;
                    Positioning positioning2 = onCreateMotionSpec.positioning;
                    calculateWindowBounds(view, rectF);
                    z3 = z6;
                    rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
                    calculateWindowBounds(view2, rectF2);
                    rectF2.offset(0.0f, -calculateTranslationY(view, view2, positioning2));
                    float centerY = rectF.centerY() - rectF2.top;
                    FloatingActionButton floatingActionButton = (FloatingActionButton) view;
                    if (ViewCompat.Api19Impl.isLaidOut(floatingActionButton)) {
                        rect.set(0, 0, floatingActionButton.getWidth(), floatingActionButton.getHeight());
                        floatingActionButton.offsetRectWithShadow(rect);
                    }
                    float width2 = rect.width() / 2.0f;
                    MotionTiming timing = onCreateMotionSpec.timings.getTiming("expansion");
                    if (z) {
                        if (!z2) {
                            circularRevealWidget2.setRevealInfo(new CircularRevealWidget.RevealInfo(centerX, centerY, width2));
                        }
                        if (z2) {
                            width2 = circularRevealWidget2.getRevealInfo().radius;
                        }
                        animator = CircularRevealCompat.createCircularReveal(circularRevealWidget2, centerX, centerY, MathUtils.distanceToFurthestCorner(centerX, centerY, width, height));
                        animator.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.4
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator2) {
                                CircularRevealWidget.RevealInfo revealInfo = circularRevealWidget2.getRevealInfo();
                                revealInfo.radius = Float.MAX_VALUE;
                                circularRevealWidget2.setRevealInfo(revealInfo);
                            }
                        });
                        long j = timing.delay;
                        int i3 = (int) centerX;
                        int i4 = (int) centerY;
                        if (j > 0) {
                            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view2, i3, i4, width2, width2);
                            createCircularReveal.setStartDelay(0L);
                            createCircularReveal.setDuration(j);
                            arrayList5.add(createCircularReveal);
                        }
                        fabTransformationSpec = onCreateMotionSpec;
                        arrayList3 = arrayList2;
                    } else {
                        float f = circularRevealWidget2.getRevealInfo().radius;
                        Animator createCircularReveal2 = CircularRevealCompat.createCircularReveal(circularRevealWidget2, centerX, centerY, width2);
                        long j2 = timing.delay;
                        int i5 = (int) centerX;
                        int i6 = (int) centerY;
                        ArrayList arrayList7 = arrayList2;
                        long j3 = 0;
                        if (j2 > 0) {
                            Animator createCircularReveal3 = ViewAnimationUtils.createCircularReveal(view2, i5, i6, f, f);
                            createCircularReveal3.setStartDelay(0L);
                            createCircularReveal3.setDuration(j2);
                            arrayList5.add(createCircularReveal3);
                        }
                        SimpleArrayMap simpleArrayMap = onCreateMotionSpec.timings.timings;
                        int i7 = simpleArrayMap.size;
                        int i8 = 0;
                        while (i8 < i7) {
                            SimpleArrayMap simpleArrayMap2 = simpleArrayMap;
                            MotionTiming motionTiming5 = (MotionTiming) simpleArrayMap.valueAt(i8);
                            j3 = Math.max(j3, motionTiming5.delay + motionTiming5.duration);
                            i8++;
                            arrayList7 = arrayList7;
                            simpleArrayMap = simpleArrayMap2;
                            i7 = i7;
                            onCreateMotionSpec = onCreateMotionSpec;
                        }
                        fabTransformationSpec = onCreateMotionSpec;
                        arrayList3 = arrayList7;
                        long j4 = timing.delay + timing.duration;
                        if (j4 < j3) {
                            Animator createCircularReveal4 = ViewAnimationUtils.createCircularReveal(view2, i5, i6, width2, width2);
                            createCircularReveal4.setStartDelay(j4);
                            createCircularReveal4.setDuration(j3 - j4);
                            arrayList5.add(createCircularReveal4);
                        }
                        animator = createCircularReveal2;
                    }
                    timing.apply(animator);
                    arrayList5.add(animator);
                    arrayList4 = arrayList3;
                    arrayList4.add(new AnimatorListenerAdapter() { // from class: com.google.android.material.circularreveal.CircularRevealCompat.1
                        public C42581() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            CircularRevealWidget.this.destroyCircularRevealCache();
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator2) {
                            CircularRevealWidget.this.buildCircularRevealCache();
                        }
                    });
                }
                if (z3) {
                    z4 = z;
                    fabTransformationSpec2 = fabTransformationSpec;
                } else {
                    CircularRevealWidget circularRevealWidget3 = (CircularRevealWidget) view2;
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ColorStateList backgroundTintList = ViewCompat.Api21Impl.getBackgroundTintList(view);
                    int colorForState = backgroundTintList != null ? backgroundTintList.getColorForState(view.getDrawableState(), backgroundTintList.getDefaultColor()) : 0;
                    int i9 = 16777215 & colorForState;
                    z4 = z;
                    if (z4) {
                        if (!z2) {
                            circularRevealWidget3.setCircularRevealScrimColor(colorForState);
                        }
                        ofInt = ObjectAnimator.ofInt(circularRevealWidget3, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, i9);
                    } else {
                        ofInt = ObjectAnimator.ofInt(circularRevealWidget3, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, colorForState);
                    }
                    ofInt.setEvaluator(ArgbEvaluatorCompat.instance);
                    fabTransformationSpec2 = fabTransformationSpec;
                    fabTransformationSpec2.timings.getTiming("color").apply(ofInt);
                    arrayList5.add(ofInt);
                }
                z5 = view2 instanceof ViewGroup;
                if (z5) {
                    View findViewById = view2.findViewById(R.id.mtrl_child_content_container);
                    ViewGroup viewGroup = null;
                    if (findViewById != null) {
                        if (findViewById instanceof ViewGroup) {
                            viewGroup = (ViewGroup) findViewById;
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
                        if (z4) {
                            if (!z2) {
                                ChildrenAlphaProperty.CHILDREN_ALPHA.set(viewGroup, Float.valueOf(0.0f));
                            }
                            i = 0;
                            ofFloat4 = ObjectAnimator.ofFloat(viewGroup, ChildrenAlphaProperty.CHILDREN_ALPHA, 1.0f);
                        } else {
                            i = 0;
                            ofFloat4 = ObjectAnimator.ofFloat(viewGroup, ChildrenAlphaProperty.CHILDREN_ALPHA, 0.0f);
                        }
                        fabTransformationSpec2.timings.getTiming("contentFade").apply(ofFloat4);
                        arrayList5.add(ofFloat4);
                        AnimatorSet animatorSet = new AnimatorSet();
                        AnimatorSetCompat.playTogether(animatorSet, arrayList5);
                        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator2) {
                                if (z4) {
                                    return;
                                }
                                view2.setVisibility(4);
                                view.setAlpha(1.0f);
                                view.setVisibility(0);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator2) {
                                if (z4) {
                                    view2.setVisibility(0);
                                    view.setAlpha(0.0f);
                                    view.setVisibility(4);
                                }
                            }
                        });
                        size = arrayList4.size();
                        for (i2 = i; i2 < size; i2++) {
                            animatorSet.addListener((Animator.AnimatorListener) arrayList4.get(i2));
                        }
                        return animatorSet;
                    }
                }
                i = 0;
                AnimatorSet animatorSet2 = new AnimatorSet();
                AnimatorSetCompat.playTogether(animatorSet2, arrayList5);
                animatorSet2.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        if (z4) {
                            return;
                        }
                        view2.setVisibility(4);
                        view.setAlpha(1.0f);
                        view.setVisibility(0);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator2) {
                        if (z4) {
                            view2.setVisibility(0);
                            view.setAlpha(0.0f);
                            view.setVisibility(4);
                        }
                    }
                });
                size = arrayList4.size();
                while (i2 < size) {
                }
                return animatorSet2;
            }
        }
        arrayList2 = arrayList;
        if (z6) {
        }
        if (z3) {
        }
        z5 = view2 instanceof ViewGroup;
        if (z5) {
        }
        i = 0;
        AnimatorSet animatorSet22 = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet22, arrayList5);
        animatorSet22.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                if (z4) {
                    return;
                }
                view2.setVisibility(4);
                view.setAlpha(1.0f);
                view.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                if (z4) {
                    view2.setVisibility(0);
                    view.setAlpha(0.0f);
                    view.setVisibility(4);
                }
            }
        });
        size = arrayList4.size();
        while (i2 < size) {
        }
        return animatorSet22;
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
