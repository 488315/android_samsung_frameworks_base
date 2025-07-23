package com.google.android.material.transformation;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Deprecated
/* loaded from: classes4.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    public float dependencyOriginalTranslationX;
    public float dependencyOriginalTranslationY;
    public final int[] tmpArray;
    public final Rect tmpRect;
    public final RectF tmpRectF1;
    public final RectF tmpRectF2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:62:0x03a5 A[LOOP:0: B:61:0x03a3->B:62:0x03a5, LOOP_END] */
    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.animation.AnimatorSet onCreateExpandedStateChangeAnimation(final android.view.View r25, final android.view.View r26, final boolean r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 948
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.transformation.FabTransformationBehavior.onCreateExpandedStateChangeAnimation(android.view.View, android.view.View, boolean, boolean):android.animation.AnimatorSet");
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
