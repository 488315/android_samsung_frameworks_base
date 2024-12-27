package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.GridView;

@Deprecated
public class SemGridSortAnimator {
    Animator.AnimatorListener mAnimatorListener;
    final GridView mGridView;
    OnSortListener mOnSortListener;
    private static int FADE_OUT_ANIMATION_DURATION = 150;
    private static int FADE_IN_TRANSLATE_ANIMATION_DURATION = 400;
    private static int DELAY_BETWEEN_ANIMATIONS = 70;
    private static final Interpolator FADE_IN_INTERPOLATOR =
            new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
    private static final Interpolator FADE_OUT_INTERPOLATOR = new AccelerateInterpolator();

    public interface OnSortListener {
        void onSort();
    }

    public SemGridSortAnimator(GridView list, OnSortListener onSortListener) {
        if (list == null || onSortListener == null) {
            throw new IllegalArgumentException(
                    "Constructor arguments should be non-null references.");
        }
        this.mGridView = list;
        this.mOnSortListener = onSortListener;
    }

    public void sortTheGrid() {
        int childCount = this.mGridView.getChildCount();
        if (this.mAnimatorListener != null) {
            this.mAnimatorListener.onAnimationStart(null);
        }
        if (childCount == 0) {
            this.mOnSortListener.onSort();
            this.mGridView.invalidate();
            this.mGridView
                    .getViewTreeObserver()
                    .addOnPreDrawListener(
                            new ViewTreeObserver
                                    .OnPreDrawListener() { // from class:
                                                           // com.samsung.android.animation.SemGridSortAnimator.1
                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public boolean onPreDraw() {
                                    SemGridSortAnimator.this
                                            .mGridView
                                            .getViewTreeObserver()
                                            .removeOnPreDrawListener(this);
                                    SemGridSortAnimator.this.startFadeInTranslateAnim();
                                    return true;
                                }
                            });
        } else {
            int i = 0;
            while (i < childCount) {
                final boolean isLastChild = i == childCount + (-1);
                final View child = this.mGridView.getChildAt(i);
                child.animate()
                        .alpha(0.0f)
                        .setDuration(FADE_OUT_ANIMATION_DURATION)
                        .setStartDelay(0L)
                        .setInterpolator(FADE_OUT_INTERPOLATOR)
                        .setListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.samsung.android.animation.SemGridSortAnimator.2
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationStart(Animator animation) {
                                        if (isLastChild) {
                                            SemGridSortAnimator.this.mGridView.setEnabled(false);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        child.setAlpha(1.0f);
                                        if (isLastChild) {
                                            SemGridSortAnimator.this.mOnSortListener.onSort();
                                            SemGridSortAnimator.this.mGridView.invalidate();
                                            SemGridSortAnimator.this
                                                    .mGridView
                                                    .getViewTreeObserver()
                                                    .addOnPreDrawListener(
                                                            new ViewTreeObserver
                                                                    .OnPreDrawListener() { // from
                                                                                           // class:
                                                                                           // com.samsung.android.animation.SemGridSortAnimator.2.1
                                                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                                                public boolean onPreDraw() {
                                                                    SemGridSortAnimator.this
                                                                            .mGridView
                                                                            .getViewTreeObserver()
                                                                            .removeOnPreDrawListener(
                                                                                    this);
                                                                    SemGridSortAnimator.this
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
        int childCount = this.mGridView.getChildCount();
        int columnCount = this.mGridView.getNumColumns();
        int childHeight = 0;
        if (childCount > 0) {
            childHeight = this.mGridView.getChildAt(0).getHeight();
        }
        for (int i = 0; i < childCount; i++) {
            View child = this.mGridView.getChildAt(i);
            int columnIndex = i % columnCount;
            child.setTranslationY((-childHeight) * 0.5f);
            child.setAlpha(0.0f);
            child.animate()
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setListener(null)
                    .setDuration(FADE_IN_TRANSLATE_ANIMATION_DURATION)
                    .setStartDelay(DELAY_BETWEEN_ANIMATIONS * columnIndex)
                    .setInterpolator(FADE_IN_INTERPOLATOR)
                    .withLayer();
            if (i == childCount - 1) {
                child.animate()
                        .setListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.samsung.android.animation.SemGridSortAnimator.3
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        SemGridSortAnimator.this.mGridView.setEnabled(true);
                                        if (SemGridSortAnimator.this.mAnimatorListener != null) {
                                            SemGridSortAnimator.this.mAnimatorListener
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
