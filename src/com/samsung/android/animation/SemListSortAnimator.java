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
/* loaded from: classes6.dex */
public class SemListSortAnimator {
    private static int DELAY_BETWEEN_ANIMATIONS = 100;
    private static int FADE_IN_TRANSLATE_ANIMATION_DURATION = 300;
    private static int FADE_OUT_ANIMATION_DURATION = 150;
    Animator.AnimatorListener mAnimatorListener;
    final ListView mListView;
    OnSortListener mOnSortListener;
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public interface OnSortListener {
        void onSort();
    }

    public SemListSortAnimator(ListView listView, OnSortListener onSortListener) {
        if (listView == null || onSortListener == null) {
            throw new IllegalArgumentException("Constructor arguments should be non-null references.");
        }
        this.mListView = listView;
        this.mOnSortListener = onSortListener;
    }

    public void sortTheList() {
        int childCount = this.mListView.getChildCount();
        Animator.AnimatorListener animatorListener = this.mAnimatorListener;
        if (animatorListener != null) {
            animatorListener.onAnimationStart(null);
        }
        if (childCount == 0) {
            this.mOnSortListener.onSort();
            this.mListView.invalidate();
            this.mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemListSortAnimator.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    SemListSortAnimator.this.mListView.getViewTreeObserver().removeOnPreDrawListener(this);
                    SemListSortAnimator.this.startFadeInTranslateAnim();
                    return true;
                }
            });
        } else {
            int i = 0;
            while (i < childCount) {
                final boolean z = i == childCount + (-1);
                final View childAt = this.mListView.getChildAt(i);
                childAt.animate().alpha(0.0f).setDuration(FADE_OUT_ANIMATION_DURATION).setStartDelay(0L).setInterpolator(ACCELERATE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemListSortAnimator.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        if (z) {
                            SemListSortAnimator.this.mListView.setEnabled(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        childAt.setAlpha(1.0f);
                        if (z) {
                            SemListSortAnimator.this.mOnSortListener.onSort();
                            SemListSortAnimator.this.mListView.invalidate();
                            SemListSortAnimator.this.mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemListSortAnimator.2.1
                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public boolean onPreDraw() {
                                    SemListSortAnimator.this.mListView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    SemListSortAnimator.this.startFadeInTranslateAnim();
                                    return true;
                                }
                            });
                        }
                    }
                }).withLayer();
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFadeInTranslateAnim() {
        int height;
        Animator.AnimatorListener animatorListener;
        int childCount = this.mListView.getChildCount();
        if (childCount > this.mListView.getHeaderViewsCount()) {
            ListView listView = this.mListView;
            height = listView.getChildAt(listView.getHeaderViewsCount()).getHeight();
        } else {
            height = 0;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mListView.getChildAt(i);
            childAt.setTranslationY((-height) / 2.0f);
            childAt.setAlpha(0.0f);
            childAt.animate().alpha(1.0f).translationY(0.0f).setListener(null).setDuration(FADE_IN_TRANSLATE_ANIMATION_DURATION).setStartDelay(DELAY_BETWEEN_ANIMATIONS * i).setInterpolator(DECELERATE_INTERPOLATOR).withLayer();
            if (i == childCount - 1) {
                childAt.animate().setListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemListSortAnimator.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemListSortAnimator.this.mListView.setEnabled(true);
                        if (SemListSortAnimator.this.mAnimatorListener != null) {
                            SemListSortAnimator.this.mAnimatorListener.onAnimationEnd(null);
                        }
                    }
                });
            }
        }
        if (childCount != 0 || (animatorListener = this.mAnimatorListener) == null) {
            return;
        }
        animatorListener.onAnimationEnd(null);
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
    }
}
