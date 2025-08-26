package com.google.android.material.appbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.systemui.R;
import java.util.Stack;

/* loaded from: classes4.dex */
public final class StackViewGroup {
    public final FrameLayout rootView;
    public final SceneStack sceneStack = new SceneStack();

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

        @Override // java.util.Stack
        public final Object pop() {
            View view;
            synchronized (this) {
                view = (View) super.pop();
                if (super.size() > 0) {
                    peek().setVisibility(0);
                }
            }
            return view;
        }

        @Override // java.util.Stack
        public final View push(View view) {
            if (super.size() > 0) {
                T tPeek = peek();
                tPeek.getClass();
                tPeek.setVisibility(8);
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

    public StackViewGroup(FrameLayout frameLayout) throws Resources.NotFoundException {
        this.rootView = frameLayout;
        Interpolator interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(frameLayout.getContext(), R.interpolator.sesl_interpolator_0_0_1_1);
        Property property = View.ALPHA;
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) property, 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(interpolatorLoadInterpolator);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.setStartDelay(100L);
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.google.android.material.appbar.StackViewGroup$showAnimator$1$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Object target = objectAnimatorOfFloat.getTarget();
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
        final ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) property, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setInterpolator(interpolatorLoadInterpolator);
        objectAnimatorOfFloat2.setDuration(100L);
        objectAnimatorOfFloat2.addListener(new Animator.AnimatorListener() { // from class: com.google.android.material.appbar.StackViewGroup$hideAnimator$1$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Object target = objectAnimatorOfFloat2.getTarget();
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
        new AnimatorSet().playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
    }
}
