package androidx.appcompat.view;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewPropertyAnimatorCompatSet {
    public Interpolator mInterpolator;
    public boolean mIsStarted;
    public ViewPropertyAnimatorListener mListener;
    public long mDuration = -1;
    public final C00401 mProxyListener = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.view.ViewPropertyAnimatorCompatSet.1
        public boolean mProxyStarted = false;
        public int mProxyEndCount = 0;

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationEnd(View view) {
            int i = this.mProxyEndCount + 1;
            this.mProxyEndCount = i;
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet.this;
            if (i == viewPropertyAnimatorCompatSet.mAnimators.size()) {
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = viewPropertyAnimatorCompatSet.mListener;
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd(null);
                }
                this.mProxyEndCount = 0;
                this.mProxyStarted = false;
                viewPropertyAnimatorCompatSet.mIsStarted = false;
            }
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationStart(View view) {
            if (this.mProxyStarted) {
                return;
            }
            this.mProxyStarted = true;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = ViewPropertyAnimatorCompatSet.this.mListener;
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationStart(null);
            }
        }
    };
    public final ArrayList mAnimators = new ArrayList();

    public final void cancel() {
        if (this.mIsStarted) {
            Iterator it = this.mAnimators.iterator();
            while (it.hasNext()) {
                ((ViewPropertyAnimatorCompat) it.next()).cancel();
            }
            this.mIsStarted = false;
        }
    }

    public final void start() {
        View view;
        if (this.mIsStarted) {
            return;
        }
        Iterator it = this.mAnimators.iterator();
        while (it.hasNext()) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) it.next();
            long j = this.mDuration;
            if (j >= 0) {
                viewPropertyAnimatorCompat.setDuration(j);
            }
            Interpolator interpolator = this.mInterpolator;
            if (interpolator != null && (view = (View) viewPropertyAnimatorCompat.mView.get()) != null) {
                view.animate().setInterpolator(interpolator);
            }
            if (this.mListener != null) {
                viewPropertyAnimatorCompat.setListener(this.mProxyListener);
            }
            View view2 = (View) viewPropertyAnimatorCompat.mView.get();
            if (view2 != null) {
                view2.animate().start();
            }
        }
        this.mIsStarted = true;
    }
}
