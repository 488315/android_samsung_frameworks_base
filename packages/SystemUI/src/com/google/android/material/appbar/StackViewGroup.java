package com.google.android.material.appbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.systemui.R;
import java.util.Stack;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StackViewGroup {
    public final FrameLayout rootView;
    public final SceneStack sceneStack = new SceneStack();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SceneStack<T extends View> extends Stack<T> {
        @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final /* bridge */ boolean contains(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.contains((View) obj);
            }
            return false;
        }

        @Override // java.util.Vector, java.util.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.indexOf((View) obj);
            }
            return -1;
        }

        @Override // java.util.Vector, java.util.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.lastIndexOf((View) obj);
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Stack
        public final Object pop() {
            View view;
            synchronized (this) {
                view = (View) super.pop();
                if (super.size() > 0) {
                    ((View) peek()).setVisibility(0);
                }
            }
            return view;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Stack
        public final View push(View view) {
            if (super.size() > 0) {
                T peek = peek();
                peek.getClass();
                ((View) peek).setVisibility(8);
            }
            return (View) super.push((SceneStack<T>) view);
        }

        @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final /* bridge */ boolean remove(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.remove((View) obj);
            }
            return false;
        }
    }

    public StackViewGroup(FrameLayout frameLayout) {
        this.rootView = frameLayout;
        Interpolator loadInterpolator = AnimationUtils.loadInterpolator(frameLayout.getContext(), R.interpolator.sesl_interpolator_0_0_1_1);
        Property property = View.ALPHA;
        final ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) property, 0.0f, 1.0f);
        ofFloat.setInterpolator(loadInterpolator);
        ofFloat.setDuration(200L);
        ofFloat.setStartDelay(100L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.google.android.material.appbar.StackViewGroup$showAnimator$1$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Object target = ofFloat.getTarget();
                View view = target instanceof View ? (View) target : null;
                if (view == null) {
                    return;
                }
                view.setVisibility(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        final ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) property, 1.0f, 0.0f);
        ofFloat2.setInterpolator(loadInterpolator);
        ofFloat2.setDuration(100L);
        ofFloat2.addListener(new Animator.AnimatorListener() { // from class: com.google.android.material.appbar.StackViewGroup$hideAnimator$1$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Object target = ofFloat2.getTarget();
                View view = target instanceof View ? (View) target : null;
                if (view != null) {
                    this.rootView.removeView(view);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        new AnimatorSet().playTogether(ofFloat2, ofFloat);
    }
}
