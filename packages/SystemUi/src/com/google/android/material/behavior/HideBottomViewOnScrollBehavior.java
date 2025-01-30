package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimationUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public int additionalHiddenOffsetY;
    public ViewPropertyAnimator currentAnimator;
    public int currentState;
    public int height;
    public final LinkedHashSet onScrollStateChangedListeners;

    public HideBottomViewOnScrollBehavior() {
        this.onScrollStateChangedListeners = new LinkedHashSet();
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }

    public final void animateChildTo(View view, int i, long j, TimeInterpolator timeInterpolator) {
        this.currentAnimator = view.animate().translationY(i).setInterpolator(timeInterpolator).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.behavior.HideBottomViewOnScrollBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                HideBottomViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        this.height = view.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin;
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        LinkedHashSet linkedHashSet = this.onScrollStateChangedListeners;
        if (i2 > 0) {
            if (this.currentState == 1) {
                return;
            }
            ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                view.clearAnimation();
            }
            this.currentState = 1;
            Iterator it = linkedHashSet.iterator();
            if (it.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                throw null;
            }
            animateChildTo(view, this.height + this.additionalHiddenOffsetY, 175L, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
            return;
        }
        if (i2 < 0) {
            if (this.currentState == 2) {
                return;
            }
            ViewPropertyAnimator viewPropertyAnimator2 = this.currentAnimator;
            if (viewPropertyAnimator2 != null) {
                viewPropertyAnimator2.cancel();
                view.clearAnimation();
            }
            this.currentState = 2;
            Iterator it2 = linkedHashSet.iterator();
            if (it2.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it2.next());
                throw null;
            }
            animateChildTo(view, 0, 225L, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        return i == 2;
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.onScrollStateChangedListeners = new LinkedHashSet();
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }
}
