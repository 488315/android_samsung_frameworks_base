package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CustomAnimationSet {
    public final Function0 onAnimationEnd;
    public final AtomicInteger endCount = new AtomicInteger(0);
    public final AtomicBoolean isFinished = new AtomicBoolean(true);
    public final List data = new ArrayList();

    public CustomAnimationSet(Function0 function0) {
        this.onAnimationEnd = function0;
    }

    public final void add(View view, DynamicAnimation.ViewProperty viewProperty, float f, float f2, float f3) {
        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty);
        SpringForce springForce = new SpringForce();
        springForce.mFinalPosition = f;
        springForce.setDampingRatio(f2);
        springForce.setStiffness(f3);
        springAnimation.mSpring = springForce;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet$add$animation$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f4, float f5) {
                CustomAnimationSet customAnimationSet = CustomAnimationSet.this;
                if (customAnimationSet.endCount.incrementAndGet() == ((ArrayList) customAnimationSet.data).size()) {
                    customAnimationSet.tryFinish();
                }
            }
        });
        ((ArrayList) this.data).add(springAnimation);
    }

    public final void clear() {
        Log.d("{OngoingCustomAnimationSet}", "clear()");
        Iterator it = new ArrayList(this.data).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof SpringAnimation) {
                ((SpringAnimation) next).cancel();
            } else {
                if (!(next instanceof ValueAnimator)) {
                    throw new Exception("Not defined index");
                }
                ((ValueAnimator) next).cancel();
            }
        }
        tryFinish();
    }

    public final void start() {
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
            ((ArrayList) this.data).clear();
            this.endCount.set(0);
        }
    }

    public final void add(ValueAnimator valueAnimator) {
        ((ArrayList) this.data).add(valueAnimator);
        valueAnimator.addListener(new AnimatorListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet$add$1
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
        start();
    }
}
