package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MultiViewUpdateListener implements ValueAnimator.AnimatorUpdateListener {
    public final Listener listener;
    public final View[] views;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Listener {
        void onAnimationUpdate(ValueAnimator valueAnimator, View view);
    }

    public MultiViewUpdateListener(Listener listener, View... viewArr) {
        this.listener = listener;
        this.views = viewArr;
    }

    public static MultiViewUpdateListener translationYListener(View... viewArr) {
        return new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(1), viewArr);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        for (View view : this.views) {
            this.listener.onAnimationUpdate(valueAnimator, view);
        }
    }

    public MultiViewUpdateListener(Listener listener, Collection<View> collection) {
        this.listener = listener;
        this.views = (View[]) collection.toArray(new View[0]);
    }
}
