package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewPropertyAnimatorCompat {
    public final WeakReference mView;

    public ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference(view);
    }

    public final void alpha(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().alpha(f);
        }
    }

    public final void cancel() {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public final void setDuration(long j) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
    }

    public final void setListener(final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        final View view = (View) this.mView.get();
        if (view != null) {
            if (viewPropertyAnimatorListener != null) {
                view.animate().setListener(new AnimatorListenerAdapter(this) { // from class: androidx.core.view.ViewPropertyAnimatorCompat.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        viewPropertyAnimatorListener.onAnimationCancel();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        viewPropertyAnimatorListener.onAnimationEnd();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        viewPropertyAnimatorListener.onAnimationStart();
                    }
                });
            } else {
                view.animate().setListener(null);
            }
        }
    }

    public final void translationY(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            view.animate().translationY(f);
        }
    }
}
