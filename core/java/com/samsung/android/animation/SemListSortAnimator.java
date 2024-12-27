package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListView;

@Deprecated
public class SemListSortAnimator {
    Animator.AnimatorListener mAnimatorListener;
    final ListView mListView;
    OnSortListener mOnSortListener;
    private static int FADE_OUT_ANIMATION_DURATION = 150;
    private static int FADE_IN_TRANSLATE_ANIMATION_DURATION = 300;
    private static int DELAY_BETWEEN_ANIMATIONS = 100;
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public interface OnSortListener {
        void onSort();
    }

    public SemListSortAnimator(ListView list, OnSortListener onSortListener) {
        if (list == null || onSortListener == null) {
            throw new IllegalArgumentException(
                    "Constructor arguments should be non-null references.");
        }
        this.mListView = list;
        this.mOnSortListener = onSortListener;
    }

    public void sortTheList() {
        int childCount = this.mListView.getChildCount();
        if (this.mAnimatorListener != null) {
            this.mAnimatorListener.onAnimationStart(null);
        }
        if (childCount == 0) {
            this.mOnSortListener.onSort();
            this.mListView.invalidate();
            this.mListView
                    .getViewTreeObserver()
                    .addOnPreDrawListener(
                            new ViewTreeObserver
                                    .OnPreDrawListener() { // from class:
                                                           // com.samsung.android.animation.SemListSortAnimator.1
                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public boolean onPreDraw() {
                                    SemListSortAnimator.this
                                            .mListView
                                            .getViewTreeObserver()
                                            .removeOnPreDrawListener(this);
                                    SemListSortAnimator.this.startFadeInTranslateAnim();
                                    return true;
                                }
                            });
        } else {
            int i = 0;
            while (i < childCount) {
                final boolean isLastChild = i == childCount + (-1);
                final View child = this.mListView.getChildAt(i);
                child.animate()
                        .alpha(0.0f)
                        .setDuration(FADE_OUT_ANIMATION_DURATION)
                        .setStartDelay(0L)
                        .setInterpolator(ACCELERATE_INTERPOLATOR)
                        .setListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.samsung.android.animation.SemListSortAnimator.2
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationStart(Animator animation) {
                                        if (isLastChild) {
                                            SemListSortAnimator.this.mListView.setEnabled(false);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        child.setAlpha(1.0f);
                                        if (isLastChild) {
                                            SemListSortAnimator.this.mOnSortListener.onSort();
                                            SemListSortAnimator.this.mListView.invalidate();
                                            SemListSortAnimator.this
                                                    .mListView
                                                    .getViewTreeObserver()
                                                    .addOnPreDrawListener(
                                                            new ViewTreeObserver
                                                                    .OnPreDrawListener() { // from
                                                                                           // class:
                                                                                           // com.samsung.android.animation.SemListSortAnimator.2.1
                                                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                                                public boolean onPreDraw() {
                                                                    SemListSortAnimator.this
                                                                            .mListView
                                                                            .getViewTreeObserver()
                                                                            .removeOnPreDrawListener(
                                                                                    this);
                                                                    SemListSortAnimator.this
                                                                            .startFadeInTranslateAnim();
                                                                    return true;
                                                                }
                                                            });
                                        }
                                    }
                                })
                        .withLayer();
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFadeInTranslateAnim() {
        int childCount = this.mListView.getChildCount();
        int childHeight = 0;
        if (childCount > this.mListView.getHeaderViewsCount()) {
            childHeight =
                    this.mListView.getChildAt(this.mListView.getHeaderViewsCount()).getHeight();
        }
        for (int i = 0; i < childCount; i++) {
            View child = this.mListView.getChildAt(i);
            child.setTranslationY((-childHeight) / 2.0f);
            child.setAlpha(0.0f);
            child.animate()
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setListener(null)
                    .setDuration(FADE_IN_TRANSLATE_ANIMATION_DURATION)
                    .setStartDelay(DELAY_BETWEEN_ANIMATIONS * i)
                    .setInterpolator(DECELERATE_INTERPOLATOR)
                    .withLayer();
            if (i == childCount - 1) {
                child.animate()
                        .setListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.samsung.android.animation.SemListSortAnimator.3
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        SemListSortAnimator.this.mListView.setEnabled(true);
                                        if (SemListSortAnimator.this.mAnimatorListener != null) {
                                            SemListSortAnimator.this.mAnimatorListener
                                                    .onAnimationEnd(null);
                                        }
                                    }
                                });
            }
        }
        if (childCount == 0 && this.mAnimatorListener != null) {
            this.mAnimatorListener.onAnimationEnd(null);
        }
    }

    public void setAnimatorListener(Animator.AnimatorListener listener) {
        this.mAnimatorListener = listener;
    }
}
