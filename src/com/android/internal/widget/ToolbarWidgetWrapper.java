package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ActionMenuPresenter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toolbar;
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPresenter;

/* loaded from: classes6.dex */
public class ToolbarWidgetWrapper implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    private boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    private CharSequence mTitle;
    private boolean mTitleSet;
    private Toolbar mToolbar;
    private Window.Callback mWindowCallback;

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canSplit() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isSplit() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitView(ViewGroup viewGroup) {
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitWhenNarrow(boolean z) {
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z) {
        this(toolbar, z, R.string.action_bar_up_description);
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z, int i) {
        Drawable drawable;
        this.mNavigationMode = 0;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar;
        this.mTitle = toolbar.getTitle();
        this.mSubtitle = toolbar.getSubtitle();
        this.mTitleSet = this.mTitle != null;
        this.mNavIcon = this.mToolbar.getNavigationIcon();
        TypedArray obtainStyledAttributes = toolbar.getContext().obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        this.mDefaultNavigationIcon = obtainStyledAttributes.getDrawable(13);
        if (z) {
            CharSequence text = obtainStyledAttributes.getText(5);
            if (!TextUtils.isEmpty(text)) {
                setTitle(text);
            }
            CharSequence text2 = obtainStyledAttributes.getText(9);
            if (!TextUtils.isEmpty(text2)) {
                setSubtitle(text2);
            }
            Drawable drawable2 = obtainStyledAttributes.getDrawable(6);
            if (drawable2 != null) {
                setLogo(drawable2);
            }
            Drawable drawable3 = obtainStyledAttributes.getDrawable(0);
            if (drawable3 != null) {
                setIcon(drawable3);
            }
            if (this.mNavIcon == null && (drawable = this.mDefaultNavigationIcon) != null) {
                setNavigationIcon(drawable);
            }
            setDisplayOptions(obtainStyledAttributes.getInt(8, 0));
            int resourceId = obtainStyledAttributes.getResourceId(10, 0);
            if (resourceId != 0) {
                setCustomView(LayoutInflater.from(this.mToolbar.getContext()).inflate(resourceId, (ViewGroup) this.mToolbar, false));
                setDisplayOptions(this.mDisplayOpts | 16);
            }
            int layoutDimension = obtainStyledAttributes.getLayoutDimension(4, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                this.mToolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(22, -1);
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(23, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(dimensionPixelOffset, 0), Math.max(dimensionPixelOffset2, 0));
            }
            int resourceId2 = obtainStyledAttributes.getResourceId(11, 0);
            if (resourceId2 != 0) {
                Toolbar toolbar2 = this.mToolbar;
                toolbar2.setTitleTextAppearance(toolbar2.getContext(), resourceId2);
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(12, 0);
            if (resourceId3 != 0) {
                Toolbar toolbar3 = this.mToolbar;
                toolbar3.setSubtitleTextAppearance(toolbar3.getContext(), resourceId3);
            }
            int resourceId4 = obtainStyledAttributes.getResourceId(26, 0);
            if (resourceId4 != 0) {
                this.mToolbar.setPopupTheme(resourceId4);
            }
        } else {
            this.mDisplayOpts = detectDisplayOptions();
        }
        obtainStyledAttributes.recycle();
        setDefaultNavigationContentDescription(i);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.1
            final ActionMenuItem mNavItem;

            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback == null || !ToolbarWidgetWrapper.this.mMenuPrepared) {
                    return;
                }
                ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
            }
        });
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationContentDescription(int i) {
        if (i == this.mDefaultNavigationContentDescription) {
            return;
        }
        this.mDefaultNavigationContentDescription = i;
        if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
            setNavigationContentDescription(this.mDefaultNavigationContentDescription);
        }
    }

    private int detectDisplayOptions() {
        if (this.mToolbar.getNavigationIcon() == null) {
            return 11;
        }
        this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        return 15;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowTitle(CharSequence charSequence) {
        if (this.mTitleSet) {
            return;
        }
        setTitleInt(charSequence);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setTitle(CharSequence charSequence) {
        this.mTitleSet = true;
        setTitleInt(charSequence);
    }

    private void setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initProgress() {
        Log.i(TAG, "Progress display unsupported");
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initIndeterminateProgress() {
        Log.i(TAG, "Progress display unsupported");
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitToolbar(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("Cannot split an android.widget.Toolbar");
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(int i) {
        setIcon(i != 0 ? getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        updateToolbarLogo();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(int i) {
        setLogo(i != 0 ? getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(Drawable drawable) {
        this.mLogo = drawable;
        updateToolbarLogo();
    }

    private void updateToolbarLogo() {
        Drawable drawable;
        int i = this.mDisplayOpts;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) != 0) {
            drawable = this.mLogo;
            if (drawable == null) {
                drawable = this.mIcon;
            }
        } else {
            drawable = this.mIcon;
        }
        this.mToolbar.setLogo(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter = actionMenuPresenter;
            actionMenuPresenter.setId(R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((MenuBuilder) menu, this.mActionMenuPresenter);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOpts ^ i;
        this.mDisplayOpts = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility();
                }
                updateNavigationIcon();
            }
            if ((i2 & 3) != 0) {
                updateToolbarLogo();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle((CharSequence) null);
                    this.mToolbar.setSubtitle((CharSequence) null);
                }
            }
            if ((i2 & 16) == 0 || (view = this.mCustomView) == null) {
                return;
            }
            if ((i & 16) != 0) {
                this.mToolbar.addView(view);
            } else {
                this.mToolbar.removeView(view);
            }
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        View view = this.mTabView;
        if (view != null) {
            ViewParent parent = view.getParent();
            Toolbar toolbar = this.mToolbar;
            if (parent == toolbar) {
                toolbar.removeView(this.mTabView);
            }
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView == null || this.mNavigationMode != 2) {
            return;
        }
        this.mToolbar.addView(scrollingTabContainerView, 0);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 8388691;
        scrollingTabContainerView.setAllowCollapse(true);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasEmbeddedTabs() {
        return this.mTabView != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCollapsible(boolean z) {
        this.mToolbar.setCollapsible(z);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationMode(int i) {
        View view;
        int i2 = this.mNavigationMode;
        if (i != i2) {
            if (i2 == 1) {
                Spinner spinner = this.mSpinner;
                if (spinner != null) {
                    ViewParent parent = spinner.getParent();
                    Toolbar toolbar = this.mToolbar;
                    if (parent == toolbar) {
                        toolbar.removeView(this.mSpinner);
                    }
                }
            } else if (i2 == 2 && (view = this.mTabView) != null) {
                ViewParent parent2 = view.getParent();
                Toolbar toolbar2 = this.mToolbar;
                if (parent2 == toolbar2) {
                    toolbar2.removeView(this.mTabView);
                }
            }
            this.mNavigationMode = i;
            if (i != 0) {
                if (i == 1) {
                    ensureSpinner();
                    this.mToolbar.addView(this.mSpinner, 0);
                    return;
                }
                if (i == 2) {
                    View view2 = this.mTabView;
                    if (view2 != null) {
                        this.mToolbar.addView(view2, 0);
                        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams();
                        layoutParams.width = -2;
                        layoutParams.height = -2;
                        layoutParams.gravity = 8388691;
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Invalid navigation mode " + i);
            }
        }
    }

    private void ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = new Spinner(getContext(), null, 16843479);
            this.mSpinner.setLayoutParams(new Toolbar.LayoutParams(-2, -2, 8388627));
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ensureSpinner();
        this.mSpinner.setAdapter(spinnerAdapter);
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownSelectedPosition(int i) {
        Spinner spinner = this.mSpinner;
        if (spinner == null) {
            throw new IllegalStateException("Can't set dropdown selected position without an adapter");
        }
        spinner.setSelection(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownSelectedPosition() {
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            return spinner.getSelectedItemPosition();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownItemCount() {
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            return spinner.getCount();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCustomView(View view) {
        View view2 = this.mCustomView;
        if (view2 != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.removeView(view2);
        }
        this.mCustomView = view;
        if (view == null || (this.mDisplayOpts & 16) == 0) {
            return;
        }
        this.mToolbar.addView(view);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public View getCustomView() {
        return this.mCustomView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void animateToVisibility(int i) {
        Animator animator = setupAnimatorToVisibility(i, 200L);
        if (animator != null) {
            animator.start();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public Animator setupAnimatorToVisibility(int i, long j) {
        if (i == 8) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToolbar, (Property<Toolbar, Float>) View.ALPHA, 1.0f, 0.0f);
            ofFloat.setDuration(j);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.2
                private boolean mCanceled = false;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (this.mCanceled) {
                        return;
                    }
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility(8);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCanceled = true;
                }
            });
            return ofFloat;
        }
        if (i != 0) {
            return null;
        }
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToolbar, (Property<Toolbar, Float>) View.ALPHA, 0.0f, 1.0f);
        ofFloat2.setDuration(j);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
            }
        });
        return ofFloat2;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(Drawable drawable) {
        this.mNavIcon = drawable;
        updateNavigationIcon();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(int i) {
        setNavigationIcon(i != 0 ? this.mToolbar.getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationIcon(Drawable drawable) {
        if (this.mDefaultNavigationIcon != drawable) {
            this.mDefaultNavigationIcon = drawable;
            updateNavigationIcon();
        }
    }

    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) != 0) {
            Toolbar toolbar = this.mToolbar;
            Drawable drawable = this.mNavIcon;
            if (drawable == null) {
                drawable = this.mDefaultNavigationIcon;
            }
            toolbar.setNavigationIcon(drawable);
            return;
        }
        this.mToolbar.setNavigationIcon((Drawable) null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        updateHomeAccessibility();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i == 0 ? null : getContext().getString(i));
    }

    private void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            if (TextUtils.isEmpty(this.mHomeDescription)) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
            }
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setBackgroundDrawable(Drawable drawable) {
        this.mToolbar.setBackgroundDrawable(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setVisibility(int i) {
        this.mToolbar.setVisibility(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }
}
