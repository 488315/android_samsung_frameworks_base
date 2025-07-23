package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.dynamicanimation.animation.DynamicAnimation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SpringAnimatorSet {
    public int animationsRunning;
    public final List animators = new ArrayList();
    public final List springAnimations = new ArrayList();
    public final List nestedAnimationSets = new ArrayList();
    public final List endListeners = new ArrayList();

    public static final void access$checkIfAllAnimationsEnded(SpringAnimatorSet springAnimatorSet) {
        int i = springAnimatorSet.animationsRunning - 1;
        springAnimatorSet.animationsRunning = i;
        if (i == 0) {
            springAnimatorSet.notifyEndListeners();
        }
    }

    public final void addAnimation(Object obj) {
        if (obj instanceof Animator) {
            ((ArrayList) this.animators).add(obj);
        } else if (obj instanceof DynamicAnimation) {
            ((ArrayList) this.springAnimations).add(obj);
        } else {
            if (!(obj instanceof SpringAnimatorSet)) {
                throw new IllegalArgumentException("Only Animator, SpringAnimation, or SpringAnimatorSet are supported");
            }
            ((ArrayList) this.nestedAnimationSets).add(obj);
        }
    }

    public final void addListener(AnimatorListenerAdapter animatorListenerAdapter) {
        ((ArrayList) this.endListeners).add(animatorListenerAdapter);
    }

    public final long getTotalDuration() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(this.animators);
        return animatorSet.getTotalDuration();
    }

    public final void notifyEndListeners() {
        ArrayList arrayList = (ArrayList) this.endListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Animator.AnimatorListener) obj).onAnimationEnd(new AnimatorSet());
        }
    }

    public final void playTogether(Object... objArr) {
        for (Object obj : objArr) {
            addAnimation(obj);
        }
    }

    public final void start() {
        if (((ArrayList) this.animators).isEmpty() && ((ArrayList) this.springAnimations).isEmpty() && ((ArrayList) this.nestedAnimationSets).isEmpty()) {
            notifyEndListeners();
            return;
        }
        this.animationsRunning = ((ArrayList) this.nestedAnimationSets).size() + ((ArrayList) this.springAnimations).size() + ((ArrayList) this.animators).size();
        ArrayList arrayList = (ArrayList) this.animators;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            Animator animator = (Animator) obj;
            animator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.events.SpringAnimatorSet$start$1
                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    SpringAnimatorSet.access$checkIfAllAnimationsEnded(SpringAnimatorSet.this);
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    SpringAnimatorSet.access$checkIfAllAnimationsEnded(SpringAnimatorSet.this);
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator2) {
                }

                @Override // androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator2) {
                }
            });
            animator.start();
        }
        ArrayList arrayList2 = (ArrayList) this.springAnimations;
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            DynamicAnimation dynamicAnimation = (DynamicAnimation) obj2;
            dynamicAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.events.SpringAnimatorSet$start$2
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z, float f, float f2) {
                    SpringAnimatorSet.access$checkIfAllAnimationsEnded(SpringAnimatorSet.this);
                }
            });
            dynamicAnimation.start();
        }
        ArrayList arrayList3 = (ArrayList) this.nestedAnimationSets;
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj3 = arrayList3.get(i);
            i++;
            SpringAnimatorSet springAnimatorSet = (SpringAnimatorSet) obj3;
            springAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SpringAnimatorSet$start$3
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    SpringAnimatorSet.access$checkIfAllAnimationsEnded(SpringAnimatorSet.this);
                }

                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    SpringAnimatorSet.access$checkIfAllAnimationsEnded(SpringAnimatorSet.this);
                }

                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator2) {
                }

                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator2) {
                }
            });
            springAnimatorSet.start();
        }
    }

    public final void playTogether(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            addAnimation(it.next());
        }
    }
}
