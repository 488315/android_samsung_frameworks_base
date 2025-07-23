package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.FadeThroughUpdateListener;
import com.google.android.material.internal.MultiViewUpdateListener;
import com.google.android.material.internal.MultiViewUpdateListener$$ExternalSyntheticLambda0;
import com.google.android.material.internal.RectEvaluator;
import com.google.android.material.internal.ReversableAnimatedValueInterpolator;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialMainContainerBackHelper;
import com.google.android.material.search.SearchView;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SearchViewAnimationHelper {
    public final MaterialMainContainerBackHelper backHelper;
    public AnimatorSet backProgressAnimatorSet;
    public final ImageButton clearButton;
    public final TouchObserverFrameLayout contentContainer;
    public final View divider;
    public final Toolbar dummyToolbar;
    public final EditText editText;
    public final FrameLayout headerContainer;
    public final ClippableRoundedCornerLayout rootView;
    public final View scrim;
    public SearchBar searchBar;
    public final TextView searchPrefix;
    public final SearchView searchView;
    public final MaterialToolbar toolbar;
    public final FrameLayout toolbarContainer;

    public SearchViewAnimationHelper(SearchView searchView) {
        this.searchView = searchView;
        this.scrim = searchView.scrim;
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = searchView.rootView;
        this.rootView = clippableRoundedCornerLayout;
        this.headerContainer = searchView.headerContainer;
        this.toolbarContainer = searchView.toolbarContainer;
        this.toolbar = searchView.toolbar;
        this.dummyToolbar = searchView.dummyToolbar;
        this.searchPrefix = searchView.searchPrefix;
        this.editText = searchView.editText;
        this.clearButton = searchView.clearButton;
        this.divider = searchView.divider;
        this.contentContainer = searchView.contentContainer;
        this.backHelper = new MaterialMainContainerBackHelper(clippableRoundedCornerLayout);
    }

    public static void access$200(SearchViewAnimationHelper searchViewAnimationHelper, float f) {
        ActionMenuView actionMenuView;
        searchViewAnimationHelper.clearButton.setAlpha(f);
        searchViewAnimationHelper.divider.setAlpha(f);
        searchViewAnimationHelper.contentContainer.setAlpha(f);
        if (!searchViewAnimationHelper.searchView.animatedMenuItems || (actionMenuView = ToolbarUtils.getActionMenuView(searchViewAnimationHelper.toolbar)) == null) {
            return;
        }
        actionMenuView.setAlpha(f);
    }

    public final void addBackButtonProgressAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (navigationIconButton == null) {
            return;
        }
        Drawable unwrap = DrawableCompat.unwrap(navigationIconButton.getDrawable());
        if (!this.searchView.animatedNavigationIcon) {
            if (unwrap instanceof DrawerArrowDrawable) {
                DrawerArrowDrawable drawerArrowDrawable = (DrawerArrowDrawable) unwrap;
                if (drawerArrowDrawable.mProgress != 1.0f) {
                    drawerArrowDrawable.mProgress = 1.0f;
                    drawerArrowDrawable.invalidateSelf();
                }
            }
            if (unwrap instanceof FadeThroughDrawable) {
                ((FadeThroughDrawable) unwrap).setProgress(1.0f);
                return;
            }
            return;
        }
        if (unwrap instanceof DrawerArrowDrawable) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1((DrawerArrowDrawable) unwrap));
            animatorSet.playTogether(ofFloat);
        }
        if (unwrap instanceof FadeThroughDrawable) {
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1((FadeThroughDrawable) unwrap));
            animatorSet.playTogether(ofFloat2);
        }
    }

    public final AnimatorSet getButtonsTranslationAnimator(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        MaterialToolbar materialToolbar = this.toolbar;
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(materialToolbar);
        if (navigationIconButton != null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(getFromTranslationXStart(navigationIconButton), 0.0f);
            ofFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), navigationIconButton));
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
            ofFloat2.addUpdateListener(MultiViewUpdateListener.translationYListener(navigationIconButton));
            animatorSet.playTogether(ofFloat, ofFloat2);
        }
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(materialToolbar);
        if (actionMenuView != null) {
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(getFromTranslationXEnd(actionMenuView), 0.0f);
            ofFloat3.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), actionMenuView));
            ValueAnimator ofFloat4 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
            ofFloat4.addUpdateListener(MultiViewUpdateListener.translationYListener(actionMenuView));
            animatorSet.playTogether(ofFloat3, ofFloat4);
        }
        animatorSet.setDuration(z ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(z, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    public final AnimatorSet getExpandCollapseAnimatorSet(final boolean z) {
        float f;
        AnimatorSet animatorSet = new AnimatorSet();
        if (this.backProgressAnimatorSet == null) {
            Animator[] animatorArr = new Animator[2];
            AnimatorSet animatorSet2 = new AnimatorSet();
            addBackButtonProgressAnimatorIfNeeded(animatorSet2);
            animatorSet2.setDuration(z ? 300L : 250L);
            animatorSet2.setInterpolator(ReversableAnimatedValueInterpolator.of(z, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            animatorArr[0] = animatorSet2;
            animatorArr[1] = getButtonsTranslationAnimator(z);
            animatorSet.playTogether(animatorArr);
        }
        Animator[] animatorArr2 = new Animator[9];
        TimeInterpolator timeInterpolator = z ? AnimationUtils.LINEAR_INTERPOLATOR : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(z ? 300L : 250L);
        ofFloat.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator));
        ofFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.scrim));
        animatorArr2[0] = ofFloat;
        MaterialMainContainerBackHelper materialMainContainerBackHelper = this.backHelper;
        Rect rect = materialMainContainerBackHelper.initialHideToClipBounds;
        Rect rect2 = materialMainContainerBackHelper.initialHideFromClipBounds;
        SearchView searchView = this.searchView;
        if (rect != null) {
            f = 0.0f;
        } else {
            f = 0.0f;
            rect = new Rect(searchView.getLeft(), searchView.getTop(), searchView.getRight(), searchView.getBottom());
        }
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.rootView;
        if (rect2 == null) {
            rect2 = ViewUtils.calculateOffsetRectFromBounds(clippableRoundedCornerLayout, this.searchBar);
        }
        final Rect rect3 = new Rect(rect2);
        final float topLeftCornerResolvedSize = this.searchBar.backgroundShape.getTopLeftCornerResolvedSize();
        final float max = Math.max(clippableRoundedCornerLayout.cornerRadius, materialMainContainerBackHelper.getExpandedCornerSize());
        ValueAnimator ofObject = ValueAnimator.ofObject(new RectEvaluator(rect3), rect2, rect);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SearchViewAnimationHelper searchViewAnimationHelper = SearchViewAnimationHelper.this;
                float f2 = topLeftCornerResolvedSize;
                float f3 = max;
                Rect rect4 = rect3;
                searchViewAnimationHelper.getClass();
                float lerp = AnimationUtils.lerp(f2, f3, valueAnimator.getAnimatedFraction());
                ClippableRoundedCornerLayout clippableRoundedCornerLayout2 = searchViewAnimationHelper.rootView;
                clippableRoundedCornerLayout2.getClass();
                clippableRoundedCornerLayout2.updateClipBoundsAndCornerRadius(rect4.left, rect4.top, rect4.right, rect4.bottom, lerp);
            }
        });
        ofObject.setDuration(z ? 300L : 250L);
        FastOutSlowInInterpolator fastOutSlowInInterpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        ofObject.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        animatorArr2[1] = ofObject;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(z ? 50L : 42L);
        ofFloat2.setStartDelay(z ? 250L : 0L);
        TimeInterpolator timeInterpolator2 = AnimationUtils.LINEAR_INTERPOLATOR;
        ofFloat2.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator2));
        ofFloat2.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.clearButton));
        animatorArr2[2] = ofFloat2;
        AnimatorSet animatorSet3 = new AnimatorSet();
        Animator[] animatorArr3 = new Animator[3];
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat3.setDuration(z ? 150L : 83L);
        ofFloat3.setStartDelay(z ? 75L : 0L);
        ofFloat3.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator2));
        TouchObserverFrameLayout touchObserverFrameLayout = this.contentContainer;
        ofFloat3.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.divider, touchObserverFrameLayout));
        animatorArr3[0] = ofFloat3;
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat((touchObserverFrameLayout.getHeight() * 0.050000012f) / 2.0f, f);
        ofFloat4.setDuration(z ? 300L : 250L);
        ofFloat4.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        ofFloat4.addUpdateListener(MultiViewUpdateListener.translationYListener(this.divider));
        animatorArr3[1] = ofFloat4;
        ValueAnimator ofFloat5 = ValueAnimator.ofFloat(0.95f, 1.0f);
        ofFloat5.setDuration(z ? 300L : 250L);
        ofFloat5.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        ofFloat5.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(2), touchObserverFrameLayout));
        animatorArr3[2] = ofFloat5;
        animatorSet3.playTogether(animatorArr3);
        animatorArr2[3] = animatorSet3;
        animatorArr2[4] = getTranslationAnimator(this.headerContainer, z, false);
        Toolbar toolbar = this.dummyToolbar;
        animatorArr2[5] = getTranslationAnimator(toolbar, z, false);
        ValueAnimator ofFloat6 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat6.setDuration(z ? 300L : 250L);
        ofFloat6.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        if (searchView.animatedMenuItems) {
            ofFloat6.addUpdateListener(new FadeThroughUpdateListener(ToolbarUtils.getActionMenuView(toolbar), ToolbarUtils.getActionMenuView(this.toolbar)));
        }
        animatorArr2[6] = ofFloat6;
        animatorArr2[7] = getTranslationAnimator(this.editText, z, true);
        animatorArr2[8] = getTranslationAnimator(this.searchPrefix, z, true);
        animatorSet.playTogether(animatorArr2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SearchViewAnimationHelper.access$200(SearchViewAnimationHelper.this, z ? 1.0f : 0.0f);
                ClippableRoundedCornerLayout clippableRoundedCornerLayout2 = SearchViewAnimationHelper.this.rootView;
                clippableRoundedCornerLayout2.path = null;
                clippableRoundedCornerLayout2.cornerRadius = 0.0f;
                clippableRoundedCornerLayout2.invalidate();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SearchViewAnimationHelper.access$200(SearchViewAnimationHelper.this, z ? 0.0f : 1.0f);
            }
        });
        return animatorSet;
    }

    public final int getFromTranslationXEnd(View view) {
        int marginEnd = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).getMarginEnd();
        return ViewUtils.isLayoutRtl(this.searchBar) ? this.searchBar.getLeft() - marginEnd : (this.searchBar.getRight() - this.searchView.getWidth()) + marginEnd;
    }

    public final int getFromTranslationXStart(View view) {
        int marginStart = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).getMarginStart();
        SearchBar searchBar = this.searchBar;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int paddingStart = searchBar.getPaddingStart();
        return ViewUtils.isLayoutRtl(this.searchBar) ? ((this.searchBar.getWidth() - this.searchBar.getRight()) + marginStart) - paddingStart : (this.searchBar.getLeft() - marginStart) + paddingStart;
    }

    public final int getFromTranslationY() {
        return ((this.searchBar.getBottom() + this.searchBar.getTop()) / 2) - ((this.toolbarContainer.getBottom() + this.toolbarContainer.getTop()) / 2);
    }

    public final AnimatorSet getTranslateAnimatorSet(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.rootView;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(clippableRoundedCornerLayout.getHeight(), 0.0f);
        ofFloat.addUpdateListener(MultiViewUpdateListener.translationYListener(clippableRoundedCornerLayout));
        animatorSet.playTogether(ofFloat);
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(z, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorSet.setDuration(z ? 350L : 300L);
        return animatorSet;
    }

    public final Animator getTranslationAnimator(View view, boolean z, boolean z2) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(z2 ? getFromTranslationXStart(view) : getFromTranslationXEnd(view), 0.0f);
        ofFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), view));
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
        ofFloat2.addUpdateListener(MultiViewUpdateListener.translationYListener(view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(z ? 300L : 250L);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(z, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    public final AnimatorSet hide() {
        SearchBar searchBar = this.searchBar;
        SearchView searchView = this.searchView;
        if (searchBar != null) {
            if (searchView.isAdjustNothingSoftInputMode()) {
                searchView.clearFocusAndHideKeyboard();
            }
            AnimatorSet expandCollapseAnimatorSet = getExpandCollapseAnimatorSet(false);
            expandCollapseAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SearchViewAnimationHelper.this.rootView.setVisibility(8);
                    if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                        SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                    }
                    SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN, true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING, true);
                }
            });
            expandCollapseAnimatorSet.start();
            return expandCollapseAnimatorSet;
        }
        if (searchView.isAdjustNothingSoftInputMode()) {
            searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet translateAnimatorSet = getTranslateAnimatorSet(false);
        translateAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN, true);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING, true);
            }
        });
        translateAnimatorSet.start();
        return translateAnimatorSet;
    }
}
