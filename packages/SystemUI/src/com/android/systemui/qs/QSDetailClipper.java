package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;

public final class QSDetailClipper {
    public Animator mAnimator;
    public final TransitionDrawable mBackground;
    public final View mDetail;
    public final AnonymousClass3 mGoneOnEnd;
    public final AnonymousClass1 mReverseBackground;
    public final AnonymousClass2 mVisibleOnStart;

    public QSDetailClipper(View view) {
        new Runnable() { // from class: com.android.systemui.qs.QSDetailClipper.1
            @Override // java.lang.Runnable
            public final void run() {
                QSDetailClipper qSDetailClipper = QSDetailClipper.this;
                if (qSDetailClipper.mAnimator != null) {
                    qSDetailClipper.mBackground.reverseTransition((int) (r0.getDuration() * 0.35d));
                }
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSDetailClipper.this.mAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                QSDetailClipper.this.mDetail.setVisibility(0);
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSDetailClipper.this.mDetail.setVisibility(8);
                QSDetailClipper.this.mBackground.resetTransition();
                QSDetailClipper.this.mAnimator = null;
            }
        };
        this.mDetail = view;
        this.mBackground = (TransitionDrawable) view.getBackground();
    }
}
