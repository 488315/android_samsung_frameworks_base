package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.BackEventCompat;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.ReversableAnimatedValueInterpolator;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBackOrchestrator;
import com.google.android.material.motion.MaterialMainContainerBackHelper;
import com.google.android.material.search.SearchView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SearchView extends FrameLayout implements CoordinatorLayout.AttachedBehavior, MaterialBackHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean animatedMenuItems;
    public final boolean animatedNavigationIcon;
    public final boolean autoShowKeyboard;
    public final boolean backHandlingEnabled;
    public final MaterialBackOrchestrator backOrchestrator;
    public final int backgroundColor;
    public final View backgroundView;
    public Map childImportantForAccessibilityMap;
    public final ImageButton clearButton;
    public final TouchObserverFrameLayout contentContainer;
    public TransitionState currentTransitionState;
    public final View divider;
    public final Toolbar dummyToolbar;
    public final EditText editText;
    public final ElevationOverlayProvider elevationOverlayProvider;
    public final FrameLayout headerContainer;
    public final boolean layoutInflated;
    public final ClippableRoundedCornerLayout rootView;
    public final View scrim;
    public SearchBar searchBar;
    public final TextView searchPrefix;
    public final SearchViewAnimationHelper searchViewAnimationHelper;
    public int softInputMode;
    public final View statusBarSpacer;
    public final MaterialToolbar toolbar;
    public final FrameLayout toolbarContainer;
    public final Set transitionListeners;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Behavior extends CoordinatorLayout.Behavior {
        public Behavior() {
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            SearchView searchView = (SearchView) view;
            if (searchView.searchBar != null || !(view2 instanceof SearchBar)) {
                return false;
            }
            SearchBar searchBar = (SearchBar) view2;
            searchView.searchBar = searchBar;
            searchView.searchViewAnimationHelper.searchBar = searchBar;
            if (searchBar != null) {
                searchBar.setOnClickListener(new SearchView$$ExternalSyntheticLambda3(searchView, 0));
                try {
                    searchBar.setHandwritingDelegatorCallback(new SearchView$$ExternalSyntheticLambda4(searchView, 0));
                    searchView.editText.setIsHandwritingDelegate(true);
                } catch (LinkageError unused) {
                }
            }
            MaterialToolbar materialToolbar = searchView.toolbar;
            if (materialToolbar != null && !(DrawableCompat.unwrap(materialToolbar.getNavigationIcon()) instanceof DrawerArrowDrawable)) {
                if (searchView.searchBar == null) {
                    MaterialToolbar materialToolbar2 = searchView.toolbar;
                    materialToolbar2.setNavigationIcon(AppCompatResources.getDrawable(R.drawable.ic_arrow_back_black_24, materialToolbar2.getContext()));
                } else {
                    Drawable mutate = AppCompatResources.getDrawable(R.drawable.ic_arrow_back_black_24, searchView.getContext()).mutate();
                    Integer num = searchView.toolbar.navigationIconTint;
                    if (num != null) {
                        mutate.setTint(num.intValue());
                    }
                    searchView.toolbar.setNavigationIcon(new FadeThroughDrawable(searchView.searchBar.getNavigationIcon(), mutate));
                    searchView.updateNavigationIconProgressIfNeeded();
                }
            }
            searchView.setUpBackgroundViewElevationOverlay();
            searchView.updateListeningForBackCallbacks(searchView.currentTransitionState);
            return false;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.search.SearchView.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }
        };
        public String text;
        public int visibility;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.text);
            parcel.writeInt(this.visibility);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.text = parcel.readString();
            this.visibility = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum TransitionState {
        HIDING,
        HIDDEN,
        SHOWING,
        SHOWN
    }

    public SearchView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.layoutInflated) {
            this.contentContainer.addView(view, i, layoutParams);
        } else {
            super.addView(view, i, layoutParams);
        }
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void cancelBackProgress() {
        if (isHiddenOrHiding() || this.searchBar == null) {
            return;
        }
        SearchViewAnimationHelper searchViewAnimationHelper = this.searchViewAnimationHelper;
        SearchBar searchBar = searchViewAnimationHelper.searchBar;
        MaterialMainContainerBackHelper materialMainContainerBackHelper = searchViewAnimationHelper.backHelper;
        if (materialMainContainerBackHelper.onCancelBackProgress() != null) {
            AnimatorSet createResetScaleAndTranslationAnimator = materialMainContainerBackHelper.createResetScaleAndTranslationAnimator(searchBar);
            View view = materialMainContainerBackHelper.view;
            if (view instanceof ClippableRoundedCornerLayout) {
                final ClippableRoundedCornerLayout clippableRoundedCornerLayout = (ClippableRoundedCornerLayout) view;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(clippableRoundedCornerLayout.cornerRadius, materialMainContainerBackHelper.getExpandedCornerSize());
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.motion.MaterialMainContainerBackHelper$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ClippableRoundedCornerLayout.this.updateClipBoundsAndCornerRadius(r0.getLeft(), r0.getTop(), r0.getRight(), r0.getBottom(), ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                createResetScaleAndTranslationAnimator.playTogether(ofFloat);
            }
            createResetScaleAndTranslationAnimator.setDuration(materialMainContainerBackHelper.cancelDuration);
            createResetScaleAndTranslationAnimator.start();
            materialMainContainerBackHelper.initialTouchY = 0.0f;
            materialMainContainerBackHelper.initialHideToClipBounds = null;
            materialMainContainerBackHelper.initialHideFromClipBounds = null;
        }
        AnimatorSet animatorSet = searchViewAnimationHelper.backProgressAnimatorSet;
        if (animatorSet != null) {
            animatorSet.reverse();
        }
        searchViewAnimationHelper.backProgressAnimatorSet = null;
    }

    public final void clearFocusAndHideKeyboard() {
        this.editText.post(new SearchView$$ExternalSyntheticLambda4(this, 2));
    }

    public MaterialMainContainerBackHelper getBackHelper() {
        return this.searchViewAnimationHelper.backHelper;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        return new Behavior();
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void handleBackInvoked() {
        if (isHiddenOrHiding()) {
            return;
        }
        SearchViewAnimationHelper searchViewAnimationHelper = this.searchViewAnimationHelper;
        MaterialMainContainerBackHelper materialMainContainerBackHelper = searchViewAnimationHelper.backHelper;
        BackEventCompat backEventCompat = materialMainContainerBackHelper.backEvent;
        materialMainContainerBackHelper.backEvent = null;
        if (this.searchBar == null || backEventCompat == null) {
            if (this.currentTransitionState.equals(TransitionState.HIDDEN) || this.currentTransitionState.equals(TransitionState.HIDING)) {
                return;
            }
            this.searchViewAnimationHelper.hide();
            return;
        }
        long totalDuration = searchViewAnimationHelper.hide().getTotalDuration();
        SearchBar searchBar = searchViewAnimationHelper.searchBar;
        MaterialMainContainerBackHelper materialMainContainerBackHelper2 = searchViewAnimationHelper.backHelper;
        AnimatorSet createResetScaleAndTranslationAnimator = materialMainContainerBackHelper2.createResetScaleAndTranslationAnimator(searchBar);
        createResetScaleAndTranslationAnimator.setDuration(totalDuration);
        createResetScaleAndTranslationAnimator.start();
        materialMainContainerBackHelper2.initialTouchY = 0.0f;
        materialMainContainerBackHelper2.initialHideToClipBounds = null;
        materialMainContainerBackHelper2.initialHideFromClipBounds = null;
        if (searchViewAnimationHelper.backProgressAnimatorSet != null) {
            searchViewAnimationHelper.getButtonsTranslationAnimator(false).start();
            searchViewAnimationHelper.backProgressAnimatorSet.resume();
        }
        searchViewAnimationHelper.backProgressAnimatorSet = null;
    }

    public final boolean isAdjustNothingSoftInputMode() {
        return this.softInputMode == 48;
    }

    public final boolean isHiddenOrHiding() {
        return this.currentTransitionState.equals(TransitionState.HIDDEN) || this.currentTransitionState.equals(TransitionState.HIDING);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        Activity activity;
        super.onFinishInflate();
        Context context = getContext();
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                activity = null;
                break;
            } else {
                if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        Window window = activity != null ? activity.getWindow() : null;
        if (window != null) {
            this.softInputMode = window.getAttributes().softInputMode;
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        this.editText.setText(savedState.text);
        boolean z = savedState.visibility == 0;
        boolean z2 = this.rootView.getVisibility() == 0;
        this.rootView.setVisibility(z ? 0 : 8);
        updateNavigationIconProgressIfNeeded();
        setTransitionState(z ? TransitionState.SHOWN : TransitionState.HIDDEN, z2 != z);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Editable text = this.editText.getText();
        savedState.text = text == null ? null : text.toString();
        savedState.visibility = this.rootView.getVisibility();
        return savedState;
    }

    public final void requestFocusAndShowKeyboardIfNeeded() {
        if (this.autoShowKeyboard) {
            this.editText.postDelayed(new SearchView$$ExternalSyntheticLambda4(this, 1), 100L);
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        ElevationOverlayProvider elevationOverlayProvider = this.elevationOverlayProvider;
        if (elevationOverlayProvider == null || this.backgroundView == null) {
            return;
        }
        this.backgroundView.setBackgroundColor(elevationOverlayProvider.compositeOverlayIfNeeded(f, this.backgroundColor));
    }

    public final void setTransitionState(TransitionState transitionState, boolean z) {
        if (this.currentTransitionState.equals(transitionState)) {
            return;
        }
        if (z) {
            if (transitionState == TransitionState.SHOWN) {
                ViewGroup viewGroup = (ViewGroup) getRootView();
                this.childImportantForAccessibilityMap = new HashMap(viewGroup.getChildCount());
                updateChildImportantForAccessibility(viewGroup, true);
            } else if (transitionState == TransitionState.HIDDEN) {
                updateChildImportantForAccessibility((ViewGroup) getRootView(), false);
                this.childImportantForAccessibilityMap = null;
            }
        }
        this.currentTransitionState = transitionState;
        Iterator it = new LinkedHashSet(this.transitionListeners).iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
        updateListeningForBackCallbacks(transitionState);
    }

    public final void setUpBackgroundViewElevationOverlay() {
        float dimension;
        SearchBar searchBar = this.searchBar;
        if (searchBar != null) {
            MaterialShapeDrawable materialShapeDrawable = searchBar.backgroundShape;
            if (materialShapeDrawable != null) {
                dimension = materialShapeDrawable.drawableState.elevation;
            } else {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                dimension = ViewCompat.Api21Impl.getElevation(searchBar);
            }
        } else {
            dimension = getResources().getDimension(R.dimen.m3_searchview_elevation);
        }
        ElevationOverlayProvider elevationOverlayProvider = this.elevationOverlayProvider;
        if (elevationOverlayProvider == null || this.backgroundView == null) {
            return;
        }
        this.backgroundView.setBackgroundColor(elevationOverlayProvider.compositeOverlayIfNeeded(dimension, this.backgroundColor));
    }

    public final void show() {
        if (this.currentTransitionState.equals(TransitionState.SHOWN)) {
            return;
        }
        TransitionState transitionState = this.currentTransitionState;
        TransitionState transitionState2 = TransitionState.SHOWING;
        if (transitionState.equals(transitionState2)) {
            return;
        }
        final SearchViewAnimationHelper searchViewAnimationHelper = this.searchViewAnimationHelper;
        SearchBar searchBar = searchViewAnimationHelper.searchBar;
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = searchViewAnimationHelper.rootView;
        final SearchView searchView = searchViewAnimationHelper.searchView;
        if (searchBar == null) {
            if (searchView.isAdjustNothingSoftInputMode()) {
                final int i = 2;
                searchView.postDelayed(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        Object obj = searchView;
                        switch (i2) {
                            case 0:
                                SearchViewAnimationHelper searchViewAnimationHelper2 = (SearchViewAnimationHelper) obj;
                                AnimatorSet expandCollapseAnimatorSet = searchViewAnimationHelper2.getExpandCollapseAnimatorSet(true);
                                expandCollapseAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.1
                                    public AnonymousClass1() {
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                            SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                        }
                                        SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                        SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                        SearchBar searchBar2 = SearchViewAnimationHelper.this.searchBar;
                                        searchBar2.searchBarAnimationHelper.getClass();
                                        View view = searchBar2.centerView;
                                        if (view != null) {
                                            view.setAlpha(0.0f);
                                        }
                                    }
                                });
                                expandCollapseAnimatorSet.start();
                                break;
                            case 1:
                                SearchViewAnimationHelper searchViewAnimationHelper3 = (SearchViewAnimationHelper) obj;
                                searchViewAnimationHelper3.rootView.setTranslationY(r0.getHeight());
                                AnimatorSet translateAnimatorSet = searchViewAnimationHelper3.getTranslateAnimatorSet(true);
                                translateAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.3
                                    public AnonymousClass3() {
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                            SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                        }
                                        SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                        SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                        SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING, true);
                                    }
                                });
                                translateAnimatorSet.start();
                                break;
                            default:
                                ((SearchView) obj).requestFocusAndShowKeyboardIfNeeded();
                                break;
                        }
                    }
                }, 150L);
            }
            clippableRoundedCornerLayout.setVisibility(4);
            final int i2 = 1;
            clippableRoundedCornerLayout.post(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    Object obj = searchViewAnimationHelper;
                    switch (i22) {
                        case 0:
                            SearchViewAnimationHelper searchViewAnimationHelper2 = (SearchViewAnimationHelper) obj;
                            AnimatorSet expandCollapseAnimatorSet = searchViewAnimationHelper2.getExpandCollapseAnimatorSet(true);
                            expandCollapseAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.1
                                public AnonymousClass1() {
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                        SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                    }
                                    SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                    SearchBar searchBar2 = SearchViewAnimationHelper.this.searchBar;
                                    searchBar2.searchBarAnimationHelper.getClass();
                                    View view = searchBar2.centerView;
                                    if (view != null) {
                                        view.setAlpha(0.0f);
                                    }
                                }
                            });
                            expandCollapseAnimatorSet.start();
                            break;
                        case 1:
                            SearchViewAnimationHelper searchViewAnimationHelper3 = (SearchViewAnimationHelper) obj;
                            searchViewAnimationHelper3.rootView.setTranslationY(r0.getHeight());
                            AnimatorSet translateAnimatorSet = searchViewAnimationHelper3.getTranslateAnimatorSet(true);
                            translateAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.3
                                public AnonymousClass3() {
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                        SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                    }
                                    SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                    SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING, true);
                                }
                            });
                            translateAnimatorSet.start();
                            break;
                        default:
                            ((SearchView) obj).requestFocusAndShowKeyboardIfNeeded();
                            break;
                    }
                }
            });
            return;
        }
        if (searchView.isAdjustNothingSoftInputMode()) {
            searchView.requestFocusAndShowKeyboardIfNeeded();
        }
        searchView.setTransitionState(transitionState2, true);
        Toolbar toolbar = searchViewAnimationHelper.dummyToolbar;
        MenuBuilder menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        int i3 = searchViewAnimationHelper.searchBar.menuResId;
        if (i3 == -1 || !searchView.animatedMenuItems) {
            toolbar.setVisibility(8);
        } else {
            toolbar.inflateMenu(i3);
            ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(toolbar);
            if (actionMenuView != null) {
                for (int i4 = 0; i4 < actionMenuView.getChildCount(); i4++) {
                    View childAt = actionMenuView.getChildAt(i4);
                    childAt.setClickable(false);
                    childAt.setFocusable(false);
                    childAt.setFocusableInTouchMode(false);
                }
            }
            toolbar.setVisibility(0);
        }
        searchViewAnimationHelper.editText.setText(searchViewAnimationHelper.searchBar.textView.getText());
        EditText editText = searchViewAnimationHelper.editText;
        editText.setSelection(editText.getText().length());
        clippableRoundedCornerLayout.setVisibility(4);
        final int i5 = 0;
        clippableRoundedCornerLayout.post(new Runnable() { // from class: com.google.android.material.search.SearchViewAnimationHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i5;
                Object obj = searchViewAnimationHelper;
                switch (i22) {
                    case 0:
                        SearchViewAnimationHelper searchViewAnimationHelper2 = (SearchViewAnimationHelper) obj;
                        AnimatorSet expandCollapseAnimatorSet = searchViewAnimationHelper2.getExpandCollapseAnimatorSet(true);
                        expandCollapseAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.1
                            public AnonymousClass1() {
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                }
                                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                SearchBar searchBar2 = SearchViewAnimationHelper.this.searchBar;
                                searchBar2.searchBarAnimationHelper.getClass();
                                View view = searchBar2.centerView;
                                if (view != null) {
                                    view.setAlpha(0.0f);
                                }
                            }
                        });
                        expandCollapseAnimatorSet.start();
                        break;
                    case 1:
                        SearchViewAnimationHelper searchViewAnimationHelper3 = (SearchViewAnimationHelper) obj;
                        searchViewAnimationHelper3.rootView.setTranslationY(r0.getHeight());
                        AnimatorSet translateAnimatorSet = searchViewAnimationHelper3.getTranslateAnimatorSet(true);
                        translateAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.search.SearchViewAnimationHelper.3
                            public AnonymousClass3() {
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                                }
                                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN, true);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING, true);
                            }
                        });
                        translateAnimatorSet.start();
                        break;
                    default:
                        ((SearchView) obj).requestFocusAndShowKeyboardIfNeeded();
                        break;
                }
            }
        });
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void startBackProgress(BackEventCompat backEventCompat) {
        if (isHiddenOrHiding() || this.searchBar == null) {
            return;
        }
        SearchViewAnimationHelper searchViewAnimationHelper = this.searchViewAnimationHelper;
        SearchBar searchBar = searchViewAnimationHelper.searchBar;
        MaterialMainContainerBackHelper materialMainContainerBackHelper = searchViewAnimationHelper.backHelper;
        materialMainContainerBackHelper.backEvent = backEventCompat;
        materialMainContainerBackHelper.startBackProgress(backEventCompat.touchY, searchBar);
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void updateBackProgress(BackEventCompat backEventCompat) {
        if (isHiddenOrHiding() || this.searchBar == null) {
            return;
        }
        SearchViewAnimationHelper searchViewAnimationHelper = this.searchViewAnimationHelper;
        searchViewAnimationHelper.getClass();
        float f = backEventCompat.progress;
        if (f <= 0.0f) {
            return;
        }
        SearchBar searchBar = searchViewAnimationHelper.searchBar;
        float topLeftCornerResolvedSize = searchBar.backgroundShape.getTopLeftCornerResolvedSize();
        MaterialMainContainerBackHelper materialMainContainerBackHelper = searchViewAnimationHelper.backHelper;
        if (materialMainContainerBackHelper.backEvent == null) {
            Log.w("MaterialBackHelper", "Must call startBackProgress() before updateBackProgress()");
        }
        BackEventCompat backEventCompat2 = materialMainContainerBackHelper.backEvent;
        materialMainContainerBackHelper.backEvent = backEventCompat;
        if (backEventCompat2 != null) {
            if (searchBar.getVisibility() != 4) {
                searchBar.setVisibility(4);
            }
            materialMainContainerBackHelper.updateBackProgress(f, backEventCompat.swipeEdge == 0, backEventCompat.touchY, topLeftCornerResolvedSize);
        }
        AnimatorSet animatorSet = searchViewAnimationHelper.backProgressAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setCurrentPlayTime((long) (f * animatorSet.getDuration()));
            return;
        }
        SearchView searchView = searchViewAnimationHelper.searchView;
        if (searchView.isAdjustNothingSoftInputMode()) {
            searchView.clearFocusAndHideKeyboard();
        }
        if (searchView.animatedNavigationIcon) {
            AnimatorSet animatorSet2 = new AnimatorSet();
            searchViewAnimationHelper.addBackButtonProgressAnimatorIfNeeded(animatorSet2);
            animatorSet2.setDuration(250L);
            animatorSet2.setInterpolator(ReversableAnimatedValueInterpolator.of(false, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            searchViewAnimationHelper.backProgressAnimatorSet = animatorSet2;
            animatorSet2.start();
            searchViewAnimationHelper.backProgressAnimatorSet.pause();
        }
    }

    public final void updateChildImportantForAccessibility(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != this) {
                if (childAt.findViewById(this.rootView.getId()) != null) {
                    updateChildImportantForAccessibility((ViewGroup) childAt, z);
                } else if (z) {
                    ((HashMap) this.childImportantForAccessibilityMap).put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    childAt.setImportantForAccessibility(4);
                } else {
                    Map map = this.childImportantForAccessibilityMap;
                    if (map != null && ((HashMap) map).containsKey(childAt)) {
                        int intValue = ((Integer) ((HashMap) this.childImportantForAccessibilityMap).get(childAt)).intValue();
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        childAt.setImportantForAccessibility(intValue);
                    }
                }
            }
        }
    }

    public final void updateListeningForBackCallbacks(TransitionState transitionState) {
        MaterialBackOrchestrator materialBackOrchestrator;
        MaterialBackOrchestrator.Api34BackCallbackDelegate api34BackCallbackDelegate;
        if (this.searchBar == null || !this.backHandlingEnabled) {
            return;
        }
        if (!transitionState.equals(TransitionState.SHOWN)) {
            if (!transitionState.equals(TransitionState.HIDDEN) || (api34BackCallbackDelegate = (materialBackOrchestrator = this.backOrchestrator).backCallbackDelegate) == null) {
                return;
            }
            api34BackCallbackDelegate.stopListeningForBackCallbacks(materialBackOrchestrator.view);
            return;
        }
        MaterialBackOrchestrator materialBackOrchestrator2 = this.backOrchestrator;
        MaterialBackOrchestrator.Api34BackCallbackDelegate api34BackCallbackDelegate2 = materialBackOrchestrator2.backCallbackDelegate;
        if (api34BackCallbackDelegate2 != null) {
            api34BackCallbackDelegate2.startListeningForBackCallbacks(materialBackOrchestrator2.backHandler, materialBackOrchestrator2.view, false);
        }
    }

    public final void updateNavigationIconProgressIfNeeded() {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (navigationIconButton == null) {
            return;
        }
        int i = this.rootView.getVisibility() == 0 ? 1 : 0;
        Drawable unwrap = DrawableCompat.unwrap(navigationIconButton.getDrawable());
        if (unwrap instanceof DrawerArrowDrawable) {
            DrawerArrowDrawable drawerArrowDrawable = (DrawerArrowDrawable) unwrap;
            float f = i;
            if (drawerArrowDrawable.mProgress != f) {
                drawerArrowDrawable.mProgress = f;
                drawerArrowDrawable.invalidateSelf();
            }
        }
        if (unwrap instanceof FadeThroughDrawable) {
            ((FadeThroughDrawable) unwrap).setProgress(i);
        }
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialSearchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Material3_SearchView), attributeSet, i);
        int i2;
        this.backOrchestrator = new MaterialBackOrchestrator(this);
        this.transitionListeners = new LinkedHashSet();
        this.softInputMode = 16;
        this.currentTransitionState = TransitionState.HIDDEN;
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.SearchView, i, R.style.Widget_Material3_SearchView, new int[0]);
        this.backgroundColor = obtainStyledAttributes.getColor(11, 0);
        int resourceId = obtainStyledAttributes.getResourceId(16, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        String string = obtainStyledAttributes.getString(3);
        String string2 = obtainStyledAttributes.getString(4);
        String string3 = obtainStyledAttributes.getString(24);
        boolean z = obtainStyledAttributes.getBoolean(27, false);
        this.animatedNavigationIcon = obtainStyledAttributes.getBoolean(8, true);
        this.animatedMenuItems = obtainStyledAttributes.getBoolean(7, true);
        boolean z2 = obtainStyledAttributes.getBoolean(17, false);
        this.autoShowKeyboard = obtainStyledAttributes.getBoolean(9, true);
        this.backHandlingEnabled = obtainStyledAttributes.getBoolean(10, true);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(context2).inflate(R.layout.mtrl_search_view, this);
        this.layoutInflated = true;
        this.scrim = findViewById(R.id.open_search_view_scrim);
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = (ClippableRoundedCornerLayout) findViewById(R.id.open_search_view_root);
        this.rootView = clippableRoundedCornerLayout;
        this.backgroundView = findViewById(R.id.open_search_view_background);
        View findViewById = findViewById(R.id.open_search_view_status_bar_spacer);
        this.statusBarSpacer = findViewById;
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.open_search_view_header_container);
        this.headerContainer = frameLayout;
        this.toolbarContainer = (FrameLayout) findViewById(R.id.open_search_view_toolbar_container);
        MaterialToolbar materialToolbar = (MaterialToolbar) findViewById(R.id.open_search_view_toolbar);
        this.toolbar = materialToolbar;
        this.dummyToolbar = (Toolbar) findViewById(R.id.open_search_view_dummy_toolbar);
        TextView textView = (TextView) findViewById(R.id.open_search_view_search_prefix);
        this.searchPrefix = textView;
        EditText editText = (EditText) findViewById(R.id.open_search_view_edit_text);
        this.editText = editText;
        ImageButton imageButton = (ImageButton) findViewById(R.id.open_search_view_clear_button);
        this.clearButton = imageButton;
        View findViewById2 = findViewById(R.id.open_search_view_divider);
        this.divider = findViewById2;
        TouchObserverFrameLayout touchObserverFrameLayout = (TouchObserverFrameLayout) findViewById(R.id.open_search_view_content_container);
        this.contentContainer = touchObserverFrameLayout;
        this.searchViewAnimationHelper = new SearchViewAnimationHelper(this);
        this.elevationOverlayProvider = new ElevationOverlayProvider(context2);
        clippableRoundedCornerLayout.setOnTouchListener(new SearchView$$ExternalSyntheticLambda5());
        setUpBackgroundViewElevationOverlay();
        if (resourceId != -1) {
            i2 = 0;
            frameLayout.addView(LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) frameLayout, false));
            frameLayout.setVisibility(0);
        } else {
            i2 = 0;
        }
        textView.setText(string3);
        textView.setVisibility(TextUtils.isEmpty(string3) ? 8 : i2);
        if (resourceId2 != -1) {
            editText.setTextAppearance(resourceId2);
        }
        editText.setText(string);
        editText.setHint(string2);
        if (z2) {
            materialToolbar.setNavigationIcon(null);
        } else {
            materialToolbar.setNavigationOnClickListener(new SearchView$$ExternalSyntheticLambda3(this, 1));
            if (z) {
                DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(getContext());
                int color = MaterialColors.getColor(this, R.attr.colorOnSurface);
                if (color != drawerArrowDrawable.mPaint.getColor()) {
                    drawerArrowDrawable.mPaint.setColor(color);
                    drawerArrowDrawable.invalidateSelf();
                }
                materialToolbar.setNavigationIcon(drawerArrowDrawable);
            }
        }
        imageButton.setOnClickListener(new SearchView$$ExternalSyntheticLambda3(this, 2));
        editText.addTextChangedListener(new TextWatcher() { // from class: com.google.android.material.search.SearchView.1
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                SearchView.this.clearButton.setVisibility(charSequence.length() > 0 ? 0 : 8);
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }
        });
        touchObserverFrameLayout.onTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.search.SearchView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SearchView searchView = SearchView.this;
                int i3 = SearchView.$r8$clinit;
                if (!searchView.isAdjustNothingSoftInputMode()) {
                    return false;
                }
                searchView.clearFocusAndHideKeyboard();
                return false;
            }
        };
        ViewUtils.doOnApplyWindowInsets(materialToolbar, new SearchView$$ExternalSyntheticLambda1(this));
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById2.getLayoutParams();
        final int i3 = marginLayoutParams.leftMargin;
        final int i4 = marginLayoutParams.rightMargin;
        OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.google.android.material.search.SearchView$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
                int i5 = SearchView.$r8$clinit;
                marginLayoutParams2.leftMargin = windowInsetsCompat.getSystemWindowInsetLeft() + i3;
                marginLayoutParams2.rightMargin = windowInsetsCompat.getSystemWindowInsetRight() + i4;
                return windowInsetsCompat;
            }
        };
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(findViewById2, onApplyWindowInsetsListener);
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize = identifier > 0 ? getResources().getDimensionPixelSize(identifier) : i2;
        if (findViewById.getLayoutParams().height != dimensionPixelSize) {
            findViewById.getLayoutParams().height = dimensionPixelSize;
            findViewById.requestLayout();
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(findViewById, new SearchView$$ExternalSyntheticLambda1(this));
    }
}
