package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ToolbarWidgetWrapper implements DecorToolbar {
    public ActionMenuPresenter mActionMenuPresenter;
    public View mCustomView;
    public int mDefaultNavigationContentDescription;
    public Drawable mDefaultNavigationIcon;
    public int mDisplayOpts;
    public CharSequence mHomeDescription;
    public Drawable mIcon;
    public Drawable mLogo;
    public boolean mMenuPrepared;
    public Drawable mNavIcon;
    public CharSequence mSubtitle;
    public ScrollingTabContainerView mTabView;
    public CharSequence mTitle;
    public boolean mTitleSet;
    public final Toolbar mToolbar;
    public Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z) {
        this(toolbar, z, R.string.sesl_action_bar_up_description, R.drawable.sesl_ic_ab_back_light);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isOverflowMenuShowPending() {
        boolean z;
        ActionMenuView actionMenuView = this.mToolbar.mMenuView;
        if (actionMenuView == null) {
            return false;
        }
        ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
        if (actionMenuPresenter != null) {
            if (actionMenuPresenter.mPostedOpenRunnable != null || actionMenuPresenter.isOverflowMenuShowing()) {
                z = true;
                return !z;
            }
        }
        z = false;
        if (!z) {
        }
    }

    public final void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOpts ^ i;
        this.mDisplayOpts = i;
        if (i2 != 0) {
            int i3 = i2 & 4;
            Toolbar toolbar = this.mToolbar;
            if (i3 != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility();
                }
                if ((this.mDisplayOpts & 4) != 0) {
                    Drawable drawable = this.mNavIcon;
                    if (drawable == null) {
                        drawable = this.mDefaultNavigationIcon;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon(null);
                }
            }
            if ((i2 & 3) != 0) {
                updateToolbarLogo();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    toolbar.setTitle(this.mTitle);
                    toolbar.setSubtitle(this.mSubtitle);
                } else {
                    toolbar.setTitle(null);
                    toolbar.setSubtitle(null);
                }
            }
            if ((i2 & 16) == 0 || (view = this.mCustomView) == null) {
                return;
            }
            if ((i & 16) != 0) {
                toolbar.addView(view);
            } else {
                toolbar.removeView(view);
            }
        }
    }

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

    public final void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            boolean isEmpty = TextUtils.isEmpty(this.mHomeDescription);
            Toolbar toolbar = this.mToolbar;
            if (!isEmpty) {
                toolbar.setNavigationContentDescription(this.mHomeDescription);
            } else {
                int i = this.mDefaultNavigationContentDescription;
                toolbar.setNavigationContentDescription(i != 0 ? toolbar.getContext().getText(i) : null);
            }
        }
    }

    public final void updateToolbarLogo() {
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

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z, int i, int i2) {
        Drawable drawable;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar;
        CharSequence charSequence = toolbar.mTitleText;
        this.mTitle = charSequence;
        this.mSubtitle = toolbar.mSubtitleText;
        this.mTitleSet = charSequence != null;
        this.mNavIcon = toolbar.getNavigationIcon();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(toolbar.getContext(), null, R$styleable.ActionBar, R.attr.actionBarStyle, 0);
        int i3 = 15;
        this.mDefaultNavigationIcon = obtainStyledAttributes.getDrawable(15);
        if (z) {
            CharSequence text = obtainStyledAttributes.getText(27);
            if (!TextUtils.isEmpty(text)) {
                this.mTitleSet = true;
                this.mTitle = text;
                if ((this.mDisplayOpts & 8) != 0) {
                    toolbar.setTitle(text);
                    if (this.mTitleSet) {
                        ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), text);
                    }
                }
            }
            CharSequence text2 = obtainStyledAttributes.getText(25);
            if (!TextUtils.isEmpty(text2)) {
                this.mSubtitle = text2;
                if ((this.mDisplayOpts & 8) != 0) {
                    toolbar.setSubtitle(text2);
                }
            }
            Drawable drawable2 = obtainStyledAttributes.getDrawable(20);
            if (drawable2 != null) {
                this.mLogo = drawable2;
                updateToolbarLogo();
            }
            Drawable drawable3 = obtainStyledAttributes.getDrawable(17);
            if (drawable3 != null) {
                this.mIcon = drawable3;
                updateToolbarLogo();
            }
            if (this.mNavIcon == null && (drawable = this.mDefaultNavigationIcon) != null) {
                this.mNavIcon = drawable;
                if ((this.mDisplayOpts & 4) != 0) {
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon(null);
                }
            }
            setDisplayOptions(obtainStyledAttributes.getInt(10, 0));
            int resourceId = obtainStyledAttributes.getResourceId(9, 0);
            if (resourceId != 0) {
                View inflate = LayoutInflater.from(toolbar.getContext()).inflate(resourceId, (ViewGroup) toolbar, false);
                View view = this.mCustomView;
                if (view != null && (this.mDisplayOpts & 16) != 0) {
                    toolbar.removeView(view);
                }
                this.mCustomView = inflate;
                if (inflate != null && (this.mDisplayOpts & 16) != 0) {
                    toolbar.addView(inflate);
                }
                setDisplayOptions(this.mDisplayOpts | 16);
            }
            int layoutDimension = obtainStyledAttributes.mWrapped.getLayoutDimension(13, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                toolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(7, -1);
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(3, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                int max = Math.max(dimensionPixelOffset, 0);
                int max2 = Math.max(dimensionPixelOffset2, 0);
                if (toolbar.mContentInsets == null) {
                    toolbar.mContentInsets = new RtlSpacingHelper();
                }
                toolbar.mContentInsets.setRelative(max, max2);
            }
            int resourceId2 = obtainStyledAttributes.getResourceId(28, 0);
            if (resourceId2 != 0) {
                Context context = toolbar.getContext();
                toolbar.mTitleTextAppearance = resourceId2;
                AppCompatTextView appCompatTextView = toolbar.mTitleTextView;
                if (appCompatTextView != null) {
                    appCompatTextView.setTextAppearance(context, resourceId2);
                }
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(26, 0);
            if (resourceId3 != 0) {
                Context context2 = toolbar.getContext();
                toolbar.mSubtitleTextAppearance = resourceId3;
                AppCompatTextView appCompatTextView2 = toolbar.mSubtitleTextView;
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setTextAppearance(context2, resourceId3);
                }
            }
            int resourceId4 = obtainStyledAttributes.getResourceId(22, 0);
            if (resourceId4 != 0 && toolbar.mPopupTheme != resourceId4) {
                toolbar.mPopupTheme = resourceId4;
                if (resourceId4 == 0) {
                    toolbar.mPopupContext = toolbar.getContext();
                } else {
                    toolbar.mPopupContext = new ContextThemeWrapper(toolbar.getContext(), resourceId4);
                }
            }
        } else {
            if (toolbar.getNavigationIcon() != null) {
                this.mDefaultNavigationIcon = toolbar.getNavigationIcon();
            } else {
                i3 = 11;
            }
            this.mDisplayOpts = i3;
        }
        obtainStyledAttributes.recycle();
        if (i != this.mDefaultNavigationContentDescription) {
            this.mDefaultNavigationContentDescription = i;
            AppCompatImageButton appCompatImageButton = toolbar.mNavButtonView;
            if (TextUtils.isEmpty(appCompatImageButton != null ? appCompatImageButton.getContentDescription() : null)) {
                int i4 = this.mDefaultNavigationContentDescription;
                this.mHomeDescription = i4 == 0 ? null : toolbar.getContext().getString(i4);
                updateHomeAccessibility();
            }
        }
        AppCompatImageButton appCompatImageButton2 = toolbar.mNavButtonView;
        this.mHomeDescription = appCompatImageButton2 != null ? appCompatImageButton2.getContentDescription() : null;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: androidx.appcompat.widget.ToolbarWidgetWrapper.1
            public final ActionMenuItem mNavItem;

            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, android.R.id.home, 0, 0, ToolbarWidgetWrapper.this.mTitle);
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
        };
        toolbar.ensureNavButtonView();
        toolbar.mNavButtonView.setOnClickListener(onClickListener);
    }
}
