package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;

/* loaded from: classes2.dex */
public class QSDetailClipper {
    public Animator mAnimator;
    public final TransitionDrawable mBackground;
    public final View mDetail;
    public final AnonymousClass1 mReverseBackground = new Runnable() { // from class: com.android.systemui.qs.QSDetailClipper.1
        @Override // java.lang.Runnable
        public final void run() {
            QSDetailClipper qSDetailClipper = QSDetailClipper.this;
            if (qSDetailClipper.mAnimator != null) {
                qSDetailClipper.mBackground.reverseTransition((int) (r0.getDuration() * 0.35d));
            }
        }
    };
    public final AnonymousClass2 mVisibleOnStart = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            QSDetailClipper.this.mAnimator = null;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            QSDetailClipper.this.mDetail.setVisibility(0);
        }
    };
    public final AnonymousClass3 mGoneOnEnd = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.3
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            QSDetailClipper.this.mDetail.setVisibility(8);
            QSDetailClipper.this.mBackground.resetTransition();
            QSDetailClipper.this.mAnimator = null;
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.QSDetailClipper$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.QSDetailClipper$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.QSDetailClipper$3] */
    public QSDetailClipper(View view) {
        this.mDetail = view;
        this.mBackground = (TransitionDrawable) view.getBackground();
    }
}
