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
        Drawable drawableUnwrap = DrawableCompat.unwrap(navigationIconButton.getDrawable());
        if (!this.searchView.animatedNavigationIcon) {
            if (drawableUnwrap instanceof DrawerArrowDrawable) {
                DrawerArrowDrawable drawerArrowDrawable = (DrawerArrowDrawable) drawableUnwrap;
                if (drawerArrowDrawable.mProgress != 1.0f) {
                    drawerArrowDrawable.mProgress = 1.0f;
                    drawerArrowDrawable.invalidateSelf();
                }
            }
            if (drawableUnwrap instanceof FadeThroughDrawable) {
                ((FadeThroughDrawable) drawableUnwrap).setProgress(1.0f);
                return;
            }
            return;
        }
        if (drawableUnwrap instanceof DrawerArrowDrawable) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1((DrawerArrowDrawable) drawableUnwrap));
            animatorSet.playTogether(valueAnimatorOfFloat);
        }
        if (drawableUnwrap instanceof FadeThroughDrawable) {
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1((FadeThroughDrawable) drawableUnwrap));
            animatorSet.playTogether(valueAnimatorOfFloat2);
        }
    }

    public final AnimatorSet getButtonsTranslationAnimator(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        MaterialToolbar materialToolbar = this.toolbar;
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(materialToolbar);
        if (navigationIconButton != null) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(getFromTranslationXStart(navigationIconButton), 0.0f);
            valueAnimatorOfFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), navigationIconButton));
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
            valueAnimatorOfFloat2.addUpdateListener(MultiViewUpdateListener.translationYListener(navigationIconButton));
            animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        }
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(materialToolbar);
        if (actionMenuView != null) {
            ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(getFromTranslationXEnd(actionMenuView), 0.0f);
            valueAnimatorOfFloat3.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), actionMenuView));
            ValueAnimator valueAnimatorOfFloat4 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
            valueAnimatorOfFloat4.addUpdateListener(MultiViewUpdateListener.translationYListener(actionMenuView));
            animatorSet.playTogether(valueAnimatorOfFloat3, valueAnimatorOfFloat4);
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(z ? 300L : 250L);
        valueAnimatorOfFloat.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator));
        valueAnimatorOfFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.scrim));
        animatorArr2[0] = valueAnimatorOfFloat;
        MaterialMainContainerBackHelper materialMainContainerBackHelper = this.backHelper;
        Rect rect = materialMainContainerBackHelper.initialHideToClipBounds;
        Rect rectCalculateOffsetRectFromBounds = materialMainContainerBackHelper.initialHideFromClipBounds;
        SearchView searchView = this.searchView;
        if (rect != null) {
            f = 0.0f;
        } else {
            f = 0.0f;
            rect = new Rect(searchView.getLeft(), searchView.getTop(), searchView.getRight(), searchView.getBottom());
        }
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.rootView;
        if (rectCalculateOffsetRectFromBounds == null) {
            rectCalculateOffsetRectFromBounds = ViewUtils.calculateOffsetRectFromBounds(clippableRoundedCornerLayout, this.searchBar);
        }
        final Rect rect2 = new Rect(rectCalculateOffsetRectFromBounds);
        final float topLeftCornerResolvedSize = this.searchBar.backgroundShape.getTopLeftCornerResolvedSize();
        final float fMax = Math.max(clippableRoundedCornerLayout.cornerRadius, materialMainContainerBackHelper.getExpandedCornerSize());
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new RectEvaluator(rect2), rectCalculateOffsetRectFromBounds, rect);
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SearchViewAnimationHelper searchViewAnimationHelper = this.f$0;
                float f2 = topLeftCornerResolvedSize;
                float f3 = fMax;
                Rect rect3 = rect2;
                searchViewAnimationHelper.getClass();
                float fLerp = AnimationUtils.lerp(f2, f3, valueAnimator.getAnimatedFraction());
                ClippableRoundedCornerLayout clippableRoundedCornerLayout2 = searchViewAnimationHelper.rootView;
                clippableRoundedCornerLayout2.getClass();
                clippableRoundedCornerLayout2.updateClipBoundsAndCornerRadius(rect3.left, rect3.top, rect3.right, rect3.bottom, fLerp);
            }
        });
        valueAnimatorOfObject.setDuration(z ? 300L : 250L);
        FastOutSlowInInterpolator fastOutSlowInInterpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        valueAnimatorOfObject.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        animatorArr2[1] = valueAnimatorOfObject;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat2.setDuration(z ? 50L : 42L);
        valueAnimatorOfFloat2.setStartDelay(z ? 250L : 0L);
        TimeInterpolator timeInterpolator2 = AnimationUtils.LINEAR_INTERPOLATOR;
        valueAnimatorOfFloat2.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator2));
        valueAnimatorOfFloat2.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.clearButton));
        animatorArr2[2] = valueAnimatorOfFloat2;
        AnimatorSet animatorSet3 = new AnimatorSet();
        Animator[] animatorArr3 = new Animator[3];
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat3.setDuration(z ? 150L : 83L);
        valueAnimatorOfFloat3.setStartDelay(z ? 75L : 0L);
        valueAnimatorOfFloat3.setInterpolator(ReversableAnimatedValueInterpolator.of(z, timeInterpolator2));
        TouchObserverFrameLayout touchObserverFrameLayout = this.contentContainer;
        valueAnimatorOfFloat3.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(3), this.divider, touchObserverFrameLayout));
        animatorArr3[0] = valueAnimatorOfFloat3;
        ValueAnimator valueAnimatorOfFloat4 = ValueAnimator.ofFloat((touchObserverFrameLayout.getHeight() * 0.050000012f) / 2.0f, f);
        valueAnimatorOfFloat4.setDuration(z ? 300L : 250L);
        valueAnimatorOfFloat4.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        valueAnimatorOfFloat4.addUpdateListener(MultiViewUpdateListener.translationYListener(this.divider));
        animatorArr3[1] = valueAnimatorOfFloat4;
        ValueAnimator valueAnimatorOfFloat5 = ValueAnimator.ofFloat(0.95f, 1.0f);
        valueAnimatorOfFloat5.setDuration(z ? 300L : 250L);
        valueAnimatorOfFloat5.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        valueAnimatorOfFloat5.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(2), touchObserverFrameLayout));
        animatorArr3[2] = valueAnimatorOfFloat5;
        animatorSet3.playTogether(animatorArr3);
        animatorArr2[3] = animatorSet3;
        animatorArr2[4] = getTranslationAnimator(this.headerContainer, z, false);
        Toolbar toolbar = this.dummyToolbar;
        animatorArr2[5] = getTranslationAnimator(toolbar, z, false);
        ValueAnimator valueAnimatorOfFloat6 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat6.setDuration(z ? 300L : 250L);
        valueAnimatorOfFloat6.setInterpolator(ReversableAnimatedValueInterpolator.of(z, fastOutSlowInInterpolator));
        if (searchView.animatedMenuItems) {
            valueAnimatorOfFloat6.addUpdateListener(new FadeThroughUpdateListener(ToolbarUtils.getActionMenuView(toolbar), ToolbarUtils.getActionMenuView(this.toolbar)));
        }
        animatorArr2[6] = valueAnimatorOfFloat6;
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(clippableRoundedCornerLayout.getHeight(), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(MultiViewUpdateListener.translationYListener(clippableRoundedCornerLayout));
        animatorSet.playTogether(valueAnimatorOfFloat);
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(z, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorSet.setDuration(z ? 350L : 300L);
        return animatorSet;
    }

    public final Animator getTranslationAnimator(View view, boolean z, boolean z2) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z2 ? getFromTranslationXStart(view) : getFromTranslationXEnd(view), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new MultiViewUpdateListener(new MultiViewUpdateListener$$ExternalSyntheticLambda0(0), view));
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(getFromTranslationY(), 0.0f);
        valueAnimatorOfFloat2.addUpdateListener(MultiViewUpdateListener.translationYListener(view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
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
