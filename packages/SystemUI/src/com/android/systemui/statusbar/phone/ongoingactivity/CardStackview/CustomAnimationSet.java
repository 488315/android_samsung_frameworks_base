package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class CustomAnimationSet {
    public final Function0 onAnimationEnd;
    public final AtomicInteger endCount = new AtomicInteger(0);
    public final AtomicBoolean isFinished = new AtomicBoolean(true);
    public final List data = new ArrayList();
    public Function0 additionalEndListener = new CustomAnimationSet$$ExternalSyntheticLambda0();

    public CustomAnimationSet(Function0 function0) {
        this.onAnimationEnd = function0;
    }

    public final void add(View view, DynamicAnimation.ViewProperty viewProperty, float f, float f2, float f3, long j, CardStackView$swipeItemToHorizontal$1$1 cardStackView$swipeItemToHorizontal$1$1) {
        final SpringAnimation springAnimation = new SpringAnimation(view, viewProperty);
        SpringForce springForce = new SpringForce();
        springForce.mFinalPosition = f;
        springForce.setDampingRatio(f2);
        springForce.setStiffness(f3);
        springAnimation.mSpring = springForce;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet$add$springAnimation$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f4, float f5) {
                CustomAnimationSet customAnimationSet = this.this$0;
                if (customAnimationSet.endCount.incrementAndGet() == ((ArrayList) customAnimationSet.data).size()) {
                    customAnimationSet.tryFinish();
                }
            }
        });
        if (cardStackView$swipeItemToHorizontal$1$1 != null) {
            springAnimation.addUpdateListener(cardStackView$swipeItemToHorizontal$1$1);
        }
        if (j == 0) {
            ((ArrayList) this.data).add(springAnimation);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.addListener(new AnimatorListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet.add.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Log.i("{OngoingCustomAnimationSet}", "Spring animator delay is end. Run spring animation");
                springAnimation.start();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }
        });
        ((ArrayList) this.data).add(valueAnimatorOfFloat);
    }

    public final void clear() throws Exception {
        Log.d("{OngoingCustomAnimationSet}", "clear()");
        ArrayList arrayList = new ArrayList(this.data);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (obj instanceof SpringAnimation) {
                ((SpringAnimation) obj).cancel();
            } else {
                if (!(obj instanceof ValueAnimator)) {
                    throw new Exception("Not defined index");
                }
                ((ValueAnimator) obj).cancel();
            }
        }
        tryFinish();
    }

    public final void start() throws Exception {
        Log.d("{OngoingCustomAnimationSet}", "start()");
        this.isFinished.set(false);
        for (Object obj : this.data) {
            if (obj instanceof SpringAnimation) {
                ((SpringAnimation) obj).start();
            } else {
                if (!(obj instanceof ValueAnimator)) {
                    throw new Exception("Not allowed class");
                }
                ((ValueAnimator) obj).start();
            }
        }
    }

    public final void tryFinish() {
        Log.d("{OngoingCustomAnimationSet}", "tryFinish()");
        if (this.isFinished.compareAndSet(false, true)) {
            Log.i("{OngoingCustomAnimationSet}", "tryFinish() : success");
            this.onAnimationEnd.invoke();
            this.additionalEndListener.invoke();
            ((ArrayList) this.data).clear();
            this.endCount.set(0);
        }
    }

    public final void add(long j, final ValueAnimator valueAnimator) {
        valueAnimator.addListener(new AnimatorListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet.add.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (CustomAnimationSet.this.endCount.incrementAndGet() == ((ArrayList) CustomAnimationSet.this.data).size()) {
                    CustomAnimationSet.this.tryFinish();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }
        });
        if (j == 0) {
            ((ArrayList) this.data).add(valueAnimator);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.addListener(new AnimatorListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet.add.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                valueAnimator.start();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }
        });
        ((ArrayList) this.data).add(valueAnimatorOfFloat);
    }
}
