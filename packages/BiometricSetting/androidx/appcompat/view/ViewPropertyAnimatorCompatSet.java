package androidx.appcompat.view;

import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ViewPropertyAnimatorCompatSet {
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    ViewPropertyAnimatorListener mListener;
    private long mDuration = -1;
    private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.view.ViewPropertyAnimatorCompatSet.1
        private boolean mProxyStarted = false;
        private int mProxyEndCount = 0;

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationEnd() {
            int i = this.mProxyEndCount + 1;
            this.mProxyEndCount = i;
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet.this;
            if (i == viewPropertyAnimatorCompatSet.mAnimators.size()) {
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = viewPropertyAnimatorCompatSet.mListener;
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd();
                }
                this.mProxyEndCount = 0;
                this.mProxyStarted = false;
                viewPropertyAnimatorCompatSet.onAnimationsEnded();
            }
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationStart() {
            if (this.mProxyStarted) {
                return;
            }
            this.mProxyStarted = true;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = ViewPropertyAnimatorCompatSet.this.mListener;
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationStart();
            }
        }
    };
    final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList<>();

    public final void cancel() {
        if (this.mIsStarted) {
            Iterator<ViewPropertyAnimatorCompat> it = this.mAnimators.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.mIsStarted = false;
        }
    }

    final void onAnimationsEnded() {
        this.mIsStarted = false;
    }

    public final void play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (this.mIsStarted) {
            return;
        }
        this.mAnimators.add(viewPropertyAnimatorCompat);
    }

    public final void playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        ArrayList<ViewPropertyAnimatorCompat> arrayList = this.mAnimators;
        arrayList.add(viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration());
        arrayList.add(viewPropertyAnimatorCompat2);
    }

    public final void setDuration() {
        if (this.mIsStarted) {
            return;
        }
        this.mDuration = 250L;
    }

    public final void setInterpolator(Interpolator interpolator) {
        if (this.mIsStarted) {
            return;
        }
        this.mInterpolator = interpolator;
    }

    public final void setListener(ViewPropertyAnimatorListenerAdapter viewPropertyAnimatorListenerAdapter) {
        if (this.mIsStarted) {
            return;
        }
        this.mListener = viewPropertyAnimatorListenerAdapter;
    }

    public final void start() {
        if (this.mIsStarted) {
            return;
        }
        Iterator<ViewPropertyAnimatorCompat> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            ViewPropertyAnimatorCompat next = it.next();
            long j = this.mDuration;
            if (j >= 0) {
                next.setDuration(j);
            }
            Interpolator interpolator = this.mInterpolator;
            if (interpolator != null) {
                next.setInterpolator(interpolator);
            }
            if (this.mListener != null) {
                next.setListener(this.mProxyListener);
            }
            next.start();
        }
        this.mIsStarted = true;
    }
}
