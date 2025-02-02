package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public final class ToolbarWidgetWrapper implements DecorToolbar {
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    boolean mMenuPrepared;
    private Drawable mNavIcon;
    private CharSequence mSubtitle;
    private ScrollingTabContainerView mTabView;
    CharSequence mTitle;
    private boolean mTitleSet;
    Toolbar mToolbar;
    Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar) {
        Drawable drawable;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar;
        this.mTitle = toolbar.getTitle();
        this.mSubtitle = toolbar.getSubtitle();
        this.mTitleSet = this.mTitle != null;
        this.mNavIcon = toolbar.getNavigationIcon();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(toolbar.getContext(), null, R$styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.mDefaultNavigationIcon = obtainStyledAttributes.getDrawable(15);
        CharSequence text = obtainStyledAttributes.getText(27);
        if (!TextUtils.isEmpty(text)) {
            this.mTitleSet = true;
            this.mTitle = text;
            if ((this.mDisplayOpts & 8) != 0) {
                this.mToolbar.setTitle(text);
                if (this.mTitleSet) {
                    ViewCompat.setAccessibilityPaneTitle(this.mToolbar.getRootView(), text);
                }
            }
        }
        CharSequence text2 = obtainStyledAttributes.getText(25);
        if (!TextUtils.isEmpty(text2)) {
            this.mSubtitle = text2;
            if ((this.mDisplayOpts & 8) != 0) {
                this.mToolbar.setSubtitle(text2);
            }
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(20);
        if (drawable2 != null) {
            this.mLogo = drawable2;
            updateToolbarLogo();
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(17);
        if (drawable3 != null) {
            setIcon(drawable3);
        }
        if (this.mNavIcon == null && (drawable = this.mDefaultNavigationIcon) != null) {
            this.mNavIcon = drawable;
            if ((this.mDisplayOpts & 4) != 0) {
                this.mToolbar.setNavigationIcon(drawable);
            } else {
                this.mToolbar.setNavigationIcon((Drawable) null);
            }
        }
        setDisplayOptions(obtainStyledAttributes.getInt(10, 0));
        int resourceId = obtainStyledAttributes.getResourceId(9, 0);
        if (resourceId != 0) {
            View inflate = LayoutInflater.from(this.mToolbar.getContext()).inflate(resourceId, (ViewGroup) this.mToolbar, false);
            View view = this.mCustomView;
            if (view != null && (this.mDisplayOpts & 16) != 0) {
                this.mToolbar.removeView(view);
            }
            this.mCustomView = inflate;
            if (inflate != null && (this.mDisplayOpts & 16) != 0) {
                this.mToolbar.addView(inflate);
            }
            setDisplayOptions(this.mDisplayOpts | 16);
        }
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(13, 0);
        if (layoutDimension > 0) {
            ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams();
            layoutParams.height = layoutDimension;
            this.mToolbar.setLayoutParams(layoutParams);
        }
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(7, -1);
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(3, -1);
        if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
            this.mToolbar.setContentInsetsRelative(Math.max(dimensionPixelOffset, 0), Math.max(dimensionPixelOffset2, 0));
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(28, 0);
        if (resourceId2 != 0) {
            Toolbar toolbar2 = this.mToolbar;
            toolbar2.setTitleTextAppearance(toolbar2.getContext(), resourceId2);
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(26, 0);
        if (resourceId3 != 0) {
            Toolbar toolbar3 = this.mToolbar;
            toolbar3.setSubtitleTextAppearance(toolbar3.getContext(), resourceId3);
        }
        int resourceId4 = obtainStyledAttributes.getResourceId(22, 0);
        if (resourceId4 != 0) {
            this.mToolbar.setPopupTheme(resourceId4);
        }
        obtainStyledAttributes.recycle();
        if (R.string.abc_action_bar_up_description != this.mDefaultNavigationContentDescription) {
            this.mDefaultNavigationContentDescription = R.string.abc_action_bar_up_description;
            if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
                int i = this.mDefaultNavigationContentDescription;
                this.mHomeDescription = i != 0 ? getContext().getString(i) : null;
                updateHomeAccessibility();
            }
        }
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.ToolbarWidgetWrapper.1
            final ActionMenuItem mNavItem;

            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), ToolbarWidgetWrapper.this.mTitle);
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ToolbarWidgetWrapper toolbarWidgetWrapper = ToolbarWidgetWrapper.this;
                Window.Callback callback = toolbarWidgetWrapper.mWindowCallback;
                if (callback == null || !toolbarWidgetWrapper.mMenuPrepared) {
                    return;
                }
                callback.onMenuItemSelected(0, this.mNavItem);
            }
        });
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

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean canShowOverflowMenu() {
        ActionMenuView actionMenuView;
        Toolbar toolbar = this.mToolbar;
        return toolbar.getVisibility() == 0 && (actionMenuView = toolbar.mMenuView) != null && actionMenuView.isOverflowReserved();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void dismissPopupMenus() {
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        if (actionMenuView != null) {
            actionMenuView.dismissPopupMenus();
        }
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean hideOverflowMenu() {
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        return actionMenuView != null && actionMenuView.hideOverflowMenu();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void initIndeterminateProgress() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void initProgress() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean isOverflowMenuShowPending() {
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowPending();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean isOverflowMenuShowing() {
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowing();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setCollapsible(boolean z) {
        this.mToolbar.setCollapsible(z);
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOpts ^ i;
        this.mDisplayOpts = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility();
                }
                if ((this.mDisplayOpts & 4) != 0) {
                    Toolbar toolbar = this.mToolbar;
                    Drawable drawable = this.mNavIcon;
                    if (drawable == null) {
                        drawable = this.mDefaultNavigationIcon;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    this.mToolbar.setNavigationIcon((Drawable) null);
                }
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

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setEmbeddedTabView() {
        ScrollingTabContainerView scrollingTabContainerView = this.mTabView;
        if (scrollingTabContainerView != null) {
            ViewParent parent = scrollingTabContainerView.getParent();
            Toolbar toolbar = this.mToolbar;
            if (parent == toolbar) {
                toolbar.removeView(this.mTabView);
            }
        }
        this.mTabView = null;
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setIcon(int i) {
        setIcon(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setLogo(int i) {
        this.mLogo = i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null;
        updateToolbarLogo();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setMenu(MenuBuilder menuBuilder, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu(menuBuilder, this.mActionMenuPresenter);
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setVisibility(int i) {
        this.mToolbar.setVisibility(i);
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setWindowTitle(CharSequence charSequence) {
        if (this.mTitleSet) {
            return;
        }
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
            if (this.mTitleSet) {
                ViewCompat.setAccessibilityPaneTitle(this.mToolbar.getRootView(), charSequence);
            }
        }
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int i, long j) {
        ViewPropertyAnimatorCompat animate = ViewCompat.animate(this.mToolbar);
        animate.alpha(i == 0 ? 1.0f : 0.0f);
        animate.setDuration(j);
        animate.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.widget.ToolbarWidgetWrapper.2
            private boolean mCanceled = false;

            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public final void onAnimationCancel(View view) {
                this.mCanceled = true;
            }

            @Override // androidx.core.view.ViewPropertyAnimatorListener
            public final void onAnimationEnd() {
                if (this.mCanceled) {
                    return;
                }
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(i);
            }

            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public final void onAnimationStart() {
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
            }
        });
        return animate;
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final boolean showOverflowMenu() {
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        return actionMenuView != null && actionMenuView.showOverflowMenu();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        updateToolbarLogo();
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void getNavigationMode() {
    }

    @Override // androidx.appcompat.widget.DecorToolbar
    public final void setHomeButtonEnabled() {
    }
}
